# $outputDir = "app\src\main\res\raw"
#
# # Ensure output directory exists
# if (!(Test-Path -Path $outputDir)) {
#     New-Item -ItemType Directory -Path $outputDir | Out-Null
# }
#
# function Write-WavFile {
#     param (
#         [string]$Filename,
#         [double[]]$Samples,
#         [int]$SampleRate = 44100
#     )
#
#     $filepath = Join-Path $outputDir $Filename
#     # Make sure we use absolute path to avoid ambiguity
#     $filepath = $ExecutionContext.SessionState.Path.GetUnresolvedProviderPathFromPSPath($filepath)
#
#     Write-Host "Generating: $filepath"
#     $byteCount = $Samples.Count * 2
#
#     $stream = $null
#     $writer = $null
#
#     try {
#         $stream = [System.IO.File]::Open($filepath, [System.IO.FileMode]::Create)
#         $writer = New-Object System.IO.BinaryWriter($stream)
#
#         # RIFF header
#         $writer.Write([System.Text.Encoding]::ASCII.GetBytes("RIFF"))
#         $writer.Write([int]($byteCount + 36))
#         $writer.Write([System.Text.Encoding]::ASCII.GetBytes("WAVE"))
#
#         # fmt chunk
#         $writer.Write([System.Text.Encoding]::ASCII.GetBytes("fmt "))
#         $writer.Write([int]16) # Chunk size
#         $writer.Write([int16]1) # Audio format (PCM)
#         $writer.Write([int16]1) # Num channels (Mono)
#         $writer.Write([int]$SampleRate)
#         $writer.Write([int]($SampleRate * 2)) # Byte rate
#         $writer.Write([int16]2) # Block align
#         $writer.Write([int16]16) # Bits per sample
#
#         # data chunk
#         $writer.Write([System.Text.Encoding]::ASCII.GetBytes("data"))
#         $writer.Write([int]$byteCount)
#
#         # Write data
#         foreach ($sample in $Samples) {
#             # Clip to -1.0 to 1.0
#             if ($sample -gt 1.0) { $sample = 1.0 }
#             if ($sample -lt -1.0) { $sample = -1.0 }
#
#             $shortSample = [int16]($sample * 32767)
#             $writer.Write($shortSample)
#         }
#         Write-Host "Successfully wrote $Filename"
#     }
#     catch {
#         Write-Error "Error writing $Filename : $_"
#     }
#     finally {
#         if ($writer) { $writer.Close() }
#         if ($stream) { $stream.Dispose() }
#     }
# }
#
# function Generate-Tone {
#     param(
#         [string]$Type,
#         [double]$DurationSeconds
#     )
#
#     $sampleRate = 44100
#     $numSamples = [int]($DurationSeconds * $sampleRate)
#     $samples = New-Object double[] $numSamples
#
#     for ($i = 0; $i -lt $numSamples; $i++) {
#         $t = $i / $sampleRate
#
#         # Envelope (Fade in/out) for warmth
#         $envelope = 1.0
#         if ($t -lt 0.5) { $envelope = $t / 0.5 }
#         if ($t -gt ($DurationSeconds - 0.5)) { $envelope = ($DurationSeconds - $t) / 0.5 }
#
#         switch ($Type) {
#             "Bell" {
#                 # Warm Bell: Lower freq (A3 = 220Hz), slower decay, rich harmonics
#                 $freq = 240.0
#                 # Slower decay for longer sustain (was 2.0)
#                 $decay = [Math]::Exp(-0.8 * $t)
#
#                 # Fundamental
#                 $val = [Math]::Sin(2 * [Math]::PI * $freq * $t)
#                 # First harmonic (Octave) 0.5x amplitude
#                 $val += 0.5 * [Math]::Sin(2 * [Math]::PI * ($freq * 2) * $t)
#                 # Sub-harmonic (Octave down) for body 0.3x
#                 $val += 0.3 * [Math]::Sin(2 * [Math]::PI * ($freq * 0.5) * $t)
#                 # Third harmonic (Fifth of octave) for color 0.2x
#                 $val += 0.2 * [Math]::Sin(2 * [Math]::PI * ($freq * 3) * $t)
#
#                 $samples[$i] = $val * $decay * 0.5
#             }
#             "Wind" {
#                 # Soft "Pink Noise" Rumble approximation
#                 # Sum of many low frequency sines with random components to avoid patterns
#                 $val = 0.0
#                 $baseFreqs = 40, 45, 50, 60, 70, 80, 100, 120, 150, 200
#                 foreach ($f in $baseFreqs) {
#                     # Slight freq drift and random phase
#                     $drift = [Math]::Sin($t * 0.5) * 2.0
#                     $val += ([Math]::Sin(2 * [Math]::PI * ($f + $drift) * $t + ($f * 0.1)) / $f) * 40.0
#                 }
#                 # Add some "air" (higher freq noise approx)
#                 $val += 0.05 * [Math]::Sin(2 * [Math]::PI * 400 * $t + 50 * [Math]::Sin($t * 50))
#
#                 $samples[$i] = $val * $envelope * 0.4
#             }
#             "Water" {
#                 # Bubbling Brook / Trickling
#                 # Randomized amplitude modulation to simulate droplets/bubbles
#                 # Base tone is low
#                 $val = [Math]::Sin(2 * [Math]::PI * 180 * $t)
#
#                 # Fast random-like modulation (approximated with sum of fast sines)
#                 $mod = [Math]::Sin(2 * [Math]::PI * 8 * $t) + [Math]::Sin(2 * [Math]::PI * 13 * $t) + [Math]::Sin(2 * [Math]::PI * 23 * $t)
#
#                 # Additional "droplet" high pitch moments
#                 $drop = 0.0
#                 if ([Math]::Sin(2 * [Math]::PI * 3 * $t) -gt 0.9) {
#                      $drop = 0.3 * [Math]::Sin(2 * [Math]::PI * 800 * $t) * [Math]::Exp(-20 * ($t % 0.33))
#                 }
#
#                 $samples[$i] = ($val * 0.3 + $mod * 0.1 + $drop) * $envelope * 0.8
#             }
#             "Brief" { # Breath - Deep Calm Breath
#                  # Slow swell (Inhale/Exhale handled by envelope, here we need texture)
#                  # Low "chest" resonance + "Air" hiss
#
#                  # Chest resonance (Low sine)
#                  $chest = [Math]::Sin(2 * [Math]::PI * 80 * $t)
#
#                  # "Air" hiss (High partials/FM)
#                  # Carrier 300, Modulator 700 (High index for noise-like)
#                  $air = [Math]::Sin(2 * [Math]::PI * 300 * $t + 5 * [Math]::Sin(2 * [Math]::PI * 700 * $t))
#
#                  # Breath is mostly air, some chest
#                  $val = ($chest * 0.4) + ($air * 0.15)
#
#                  $samples[$i] = $val * $envelope * 0.8
#             }
#             "Warm" { # sound1.wav
#                 # Simple major chord: C3, E3, G3 (Low and warm)
#                 # C3 = 130.81, E3 = 164.81, G3 = 196.00
#                 $val = [Math]::Sin(2 * [Math]::PI * 130.81 * $t)
#                 $val += [Math]::Sin(2 * [Math]::PI * 164.81 * $t)
#                 $val += [Math]::Sin(2 * [Math]::PI * 196.00 * $t)
#                 $samples[$i] = ($val / 3.0) * $envelope * 0.7
#             }
#             "Rain" {
#                 # Rain simulation: Pink noise-ish + random droplets
#                 # We need a random source. Since we can't easily persist state across loop in this structure efficiently without setup
#                 # We'll use a simple pseudo-random LCG here or just System.Random if initialized outside
#
#                 # Simple white noise
#                 $noise = ($script:rng.NextDouble() * 2.0) - 1.0
#
#                 # Simple Low Pass Filter approximation (Moving average)
#                 # We need state for this, so we'll just use the previous sample if we had access, but we don't easily here in this loop structure.
#                 # Let's just use white noise with lower amplitude for background rain hiss.
#
#                 $val = $noise * 0.15
#
#                 # Occasional louder "drops"
#                 if ($script:rng.NextDouble() -gt 0.98) {
#                     $val += ($script:rng.NextDouble() * 0.4)
#                 }
#
#                 $samples[$i] = $val * $envelope
#             }
#         }
#     }
#     return $samples
# }
#
# # Initialize Random Number Generator
# $script:rng = New-Object System.Random
#
# # Generate files
# # User asked for "more longer than 1 second and two second" -> Let's do 5.0s for bells
# Write-Host "Generating sounds..."
#
# Write-WavFile -Filename "wind.wav" -Samples (Generate-Tone -Type "Wind" -DurationSeconds 3.0)
# Write-WavFile -Filename "water.wav" -Samples (Generate-Tone -Type "Water" -DurationSeconds 2.5)
# Write-WavFile -Filename "breath.wav" -Samples (Generate-Tone -Type "Brief" -DurationSeconds 2.5)
# Write-WavFile -Filename "sound1.wav" -Samples (Generate-Tone -Type "Warm" -DurationSeconds 2.5)
# Write-WavFile -Filename "bell.wav" -Samples (Generate-Tone -Type "Bell" -DurationSeconds 5.0)
# Write-WavFile -Filename "rain.wav" -Samples (Generate-Tone -Type "Rain" -DurationSeconds 4.0)
#
# Write-Host "Done generating sounds."

package com.example.secondapp.ui.theme

import android.content.Context
import android.media.MediaPlayer
import android.util.Log


object SoundManager {

    private val mediaPlayers = mutableMapOf<Int, MediaPlayer>()

    fun load(context: Context, resId: Int) {
        if (mediaPlayers[resId] == null) {
            val player = MediaPlayer.create(context, resId)
            if (player != null) {
                mediaPlayers[resId] = player
            } else {
                Log.d("SOUND", "Failed to create MediaPlayer for $resId")
            }
        }
    }

    fun play(resId: Int) {
        mediaPlayers[resId]?.let { player ->
            if (!player.isPlaying) {
                player.start()
            } else {
                player.seekTo(0)
            }
        } ?: Log.d("SOUND", "Sound not loaded: $resId")
    }

    fun stop(resId: Int) {
        mediaPlayers[resId]?.let { player ->
            if (player.isPlaying) {
                player.pause()
                player.seekTo(0)
            }
        }
    }

    fun pause(resId: Int) {
        mediaPlayers[resId]?.let { player ->
            if (player.isPlaying) {
                player.pause()
            }
        }
    }

    fun resume(resId: Int) {
        mediaPlayers[resId]?.let { player ->
            if (!player.isPlaying) {
                player.start()
            }
        }
    }

    fun isPlaying(resId: Int): Boolean {
        return mediaPlayers[resId]?.isPlaying == true
    }

    fun forward(resId: Int, milliseconds: Int) {
        mediaPlayers[resId]?.let { player ->
            val newPosition = (player.currentPosition + milliseconds).coerceAtMost(player.duration)
            player.seekTo(newPosition)
        }
    }

    fun rewind(resId: Int, milliseconds: Int) {
        mediaPlayers[resId]?.let { player ->
            val newPosition = (player.currentPosition - milliseconds).coerceAtLeast(0)
            player.seekTo(newPosition)
        }
    }

    fun release() {
        mediaPlayers.values.forEach { player ->
            player.stop()
            player.release()
        }
        mediaPlayers.clear()
    }
}

/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.dspvolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

public class VolumeListenerReceiver extends BroadcastReceiver {

    private static final String TAG = "VolumeListenerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Ensure the context is not null
        if (context == null) {
            Log.e(TAG, "Received broadcast with null context");
            return;
        }

        // Check if the intent action matches the expected volume change
        if (intent.hasExtra("android.media.EXTRA_VOLUME_STREAM_TYPE") && 
            intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == AudioManager.STREAM_MUSIC) {

            AudioManager audioManager = context.getSystemService(AudioManager.class);
            if (audioManager == null) {
                Log.e(TAG, "AudioManager is null");
                return;
            }

            int currentVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            // Set the audio parameters for volume change
            audioManager.setParameters("volume_change=" + currentVolume + ";flags=8");
            Log.d(TAG, "Volume changed to: " + currentVolume);
        }
    }
}

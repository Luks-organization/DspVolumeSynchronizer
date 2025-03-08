/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.dspvolume;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class VolumeListenerService extends Service {

    private static final String TAG = "VolumeListenerService";
    private VolumeListenerReceiver volumeListenerReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // This service is not meant to be bound
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerVolumeReceiver();
        updateCurrentVolume();
        return START_STICKY; // Service will be restarted if it gets terminated
    }

    private void registerVolumeReceiver() {
        if (volumeListenerReceiver == null) {
            volumeListenerReceiver = new VolumeListenerReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            registerReceiver(volumeListenerReceiver, intentFilter);
            Log.d(TAG, "VolumeListenerReceiver registered");
        }
    }

    private void updateCurrentVolume() {
        AudioManager audioManager = getSystemService(AudioManager.class);
        if (audioManager != null) {
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setParameters("volume_change=" + currentVolume + ";flags=8");
            Log.d(TAG, "Current volume set to: " + currentVolume);
        } else {
            Log.e(TAG, "AudioManager is null");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (volumeListenerReceiver != null) {
            unregisterReceiver(volumeListenerReceiver);
            Log.d(TAG, "VolumeListenerReceiver unregistered");
        }
    }
}

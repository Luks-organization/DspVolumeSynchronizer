/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.dspvolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // Check if the received intent action is for boot completion
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Ensure context is not null
            if (context != null) {
                // Start the VolumeListenerService
                context.startService(new Intent(context, VolumeListenerService.class));
            }
        }
    }
}

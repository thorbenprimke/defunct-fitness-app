package com.femlite.app.misc;

import android.content.Context;
import android.widget.Toast;

import rx.functions.Action1;

/**
 * Helper class for anything related to RX actions.
 */
public class ActionHelper {

    public static Action1<Throwable> getDefaultErrorAction(Context context) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        };
    }
}

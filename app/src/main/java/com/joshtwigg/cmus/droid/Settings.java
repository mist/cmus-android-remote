package com.joshtwigg.cmus.droid;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by josh on 08/02/14.
 */
public class Settings implements Serializable{
    private static final String KEY_POLL_MILLS = "KEY_POLL_MILLS";
    private static final String KEY_FETCHARTWORK = "KEY_FETCHARTWORK";
    private static final String KEY_VOLUME_TIMEOUT = "KEY_VOLUME_TIMEOUT";
    private static final String KEY_VOLUME_STEP = "KEY_VOLUME_STEP";

    public int POLL_DURATION_MILLS;
    public boolean FETCH_ARTWORK;
    public int VOLUME_STEP;
    public long VOLUME_DIALOG_TIMEOUT;

    private int original_POLL_DURATION_MILLS;
    private boolean original_FETCH_ARTWORK;
    private int original_VOLUME_STEP;
    private long original_VOLUME_DIALOG_TIMEOUT;

    public Settings(final Context context, final SharedPreferences prefs) {
        original_POLL_DURATION_MILLS = POLL_DURATION_MILLS = prefs.getInt(KEY_POLL_MILLS, context.getResources().getInteger(R.integer.default_poll_mills));
        original_FETCH_ARTWORK = FETCH_ARTWORK = prefs.getBoolean(KEY_FETCHARTWORK, context.getResources().getBoolean(R.bool.default_fetch_artwork));
        original_VOLUME_DIALOG_TIMEOUT = VOLUME_DIALOG_TIMEOUT = prefs.getLong(KEY_VOLUME_TIMEOUT, context.getResources().getInteger(R.integer.default_volume_timeout));
        original_VOLUME_STEP = VOLUME_STEP = prefs.getInt(KEY_VOLUME_STEP, context.getResources().getInteger(R.integer.default_volume_step));
    }

    public boolean saveChanges(final Context context) {
        SharedPreferences prefs = Storage.getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_POLL_MILLS, POLL_DURATION_MILLS);
        editor.putBoolean(KEY_FETCHARTWORK, FETCH_ARTWORK);
        editor.putInt(KEY_VOLUME_STEP, VOLUME_STEP);
        return editor.commit();
    }

    public boolean hasChanged() {
        return original_FETCH_ARTWORK != FETCH_ARTWORK
                || original_POLL_DURATION_MILLS != POLL_DURATION_MILLS
                || original_VOLUME_DIALOG_TIMEOUT != VOLUME_DIALOG_TIMEOUT
                || original_VOLUME_STEP != VOLUME_STEP;
    }
}

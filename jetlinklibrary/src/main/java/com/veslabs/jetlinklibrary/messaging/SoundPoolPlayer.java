package com.veslabs.jetlinklibrary.messaging;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.veslabs.jetlinklibrary.R;

import java.util.HashMap;

/**
 * Created by Burhan Aras on 10/31/2016.
 */
public class SoundPoolPlayer {
    private static final String TAG = SoundPoolPlayer.class.getSimpleName();
    private SoundPool mShortPlayer = null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext, final int music) {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mShortPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                playShortResource(music);
            }
        });

        mSounds.put(R.raw.incoming, this.mShortPlayer.load(pContext, R.raw.incoming, 1));
        mSounds.put(R.raw.send_message, this.mShortPlayer.load(pContext, R.raw.send_message, 2));

    }

    public void playShortResource(int piResource) {
        Log.d(TAG, "playShortResource()");
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }

}
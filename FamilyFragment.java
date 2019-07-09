package com.example.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {
    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate( R.layout.word_list_item, container, false );
        mAudioManager= (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Words> addNumbers = new ArrayList<>();
        addNumbers.add( new Words( "father","әpә",R.mipmap.family_father,R.raw.family_father  ) );
        addNumbers.add( new Words( "mother","әṭa" ,R.mipmap.family_mother,R.raw.family_mother ) );
        addNumbers.add( new Words( "son","angsi",R.mipmap.family_son,R.raw.family_son  ) );
        addNumbers.add( new Words( "daughter","tune",R.mipmap.family_daughter,R.raw.family_daughter  ) );
        addNumbers.add( new Words( "older brother","taachi",R.mipmap.family_older_brother,R.raw.family_older_brother  ) );
        addNumbers.add( new Words( "younger brother","chalitti",R.mipmap.family_younger_brother,R.raw.family_younger_brother ) );
        addNumbers.add( new Words( "older sister","teṭe",R.mipmap.family_older_sister,R.raw.family_older_sister  ) );
        addNumbers.add( new Words( "younger sister","kolliti",R.mipmap.family_younger_sister,R.raw.family_younger_sister  ) );
        addNumbers.add( new Words( "grandmother","ama",R.mipmap.family_grandmother ,R.raw.family_grandmother ) );
        addNumbers.add( new Words( "grandfather","paapa",R.mipmap.family_grandfather,R.raw.family_grandfather  ) );

        ListView listView = rootView.findViewById( R.id.word_item );
        WordAdapter itemsAdapter = new WordAdapter( getActivity(), addNumbers,R.color.category_family );
        listView.setAdapter( itemsAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words words=addNumbers.get( position );
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    // Start playback

                    mMediaPlayer=MediaPlayer.create( getActivity(),words.getmAudioResourceId() );
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener( mCompletionListener );
                }
            }
        } );
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}

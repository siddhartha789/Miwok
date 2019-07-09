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

import java.util.ArrayList;

public class NumberFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file */
    AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        //       OR ||
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.

                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo( 0 );
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.
                        releaseMediaPlayer();
                        //mAudioManager.abandonAudioFocus(afChangeListener);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        mMediaPlayer.start();
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


    public NumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate( R.layout.word_list_item, container, false );

        mAudioManager= (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        // Creating a ArrayList

        final ArrayList<Words> addNumbers = new ArrayList<>();


        addNumbers.add( new Words( "one", "lutti", R.mipmap.number_one, R.raw.number_one ) );
        addNumbers.add( new Words( "two", "otiiko", R.mipmap.number_two, R.raw.number_two ) );
        addNumbers.add( new Words( "three", "tolookosu", R.mipmap.number_three, R.raw.number_three ) );
        addNumbers.add( new Words( "four", "oyyisa", R.mipmap.number_four, R.raw.number_four ) );
        addNumbers.add( new Words( "five", "massokka", R.mipmap.number_five, R.raw.number_five ) );
        addNumbers.add( new Words( "six", "temmokka", R.mipmap.number_six, R.raw.number_six ) );
        addNumbers.add( new Words( "seven", "kenekaku", R.mipmap.number_seven, R.raw.number_seven ) );
        addNumbers.add( new Words( "eight", "kawinta", R.mipmap.number_eight, R.raw.number_eight ) );
        addNumbers.add( new Words( "nine", "wo’e", R.mipmap.number_nine, R.raw.number_nine ) );
        addNumbers.add( new Words( "ten", "na’aacha", R.mipmap.number_ten, R.raw.number_ten ) );

        ListView listView = rootView.findViewById( R.id.word_item );
        WordAdapter itemsAdapter = new WordAdapter( getActivity(), addNumbers, R.color.category_number );
        listView.setAdapter( itemsAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Words word = addNumbers.get( position );
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    // Start playback

                    mMediaPlayer=MediaPlayer.create( getContext(),word.getmAudioResourceId() );
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




    /**
     * Clean up the media player by releasing its resources.
     */
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
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }








}

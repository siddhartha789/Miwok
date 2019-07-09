package com.example.miwok;

/**
 * Creating a Custom Class
 */
public class Words {

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */

    private static final int NO_IMAGE_PROVIDE=-1;

    private int mImagenumbers=NO_IMAGE_PROVIDE;

    private String mMiwokTranslation;

    private int mAudioResourceId;




    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */
    public Words(String defaultTranslation, String miwokTranslation,int mAudioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        this.mAudioResourceId=mAudioResourceId;

    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     * @param mImagenumbers is the image in miwok language
     */
    public Words(String defaultTranslation, String miwokTranslation,int mImagenumbers,int mAudioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        this.mImagenumbers=mImagenumbers;
        this.mAudioResourceId=mAudioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getmImagenumbers() {
        return mImagenumbers; }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

        public boolean hasImage()
        {
            return mImagenumbers!=NO_IMAGE_PROVIDE;
        }


}



package project.com.maktab.musicplayer.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Song {
    private String artist;
    private String title;
    private String data;
    private int duration;
    private Uri art;
    private Bitmap mBitmap;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Uri getArt() {
        return art;
    }

    public void setArt(Uri art) {
        this.art = art;
    }
}

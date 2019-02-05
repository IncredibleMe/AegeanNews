package com.example.jack.aegeannews;


import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Date;

public class News {
    private int id;
    private String title;
    private String image;
    private String content;
    private Date imerominia;
    private Bitmap imageBit;
    private String link;
    private String sitename;

    public News() {
    }

    public News(int id, String title, String image, String content, Date imerominia, String link, String sitename) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.imerominia = imerominia;
        this.link = link;
        this.sitename = sitename;
    }

    public Bitmap getImageBit() {
        return imageBit;
    }

    public void setImageBit(Bitmap imageBit) {
        this.imageBit = imageBit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getImerominia() {
        return imerominia;
    }

    public void setImerominia(Date imerominia) {
        this.imerominia = imerominia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
}

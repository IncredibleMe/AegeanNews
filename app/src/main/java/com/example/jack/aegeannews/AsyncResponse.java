package com.example.jack.aegeannews;

import com.example.jack.aegeannews.NewsGrabber.News;

import java.util.ArrayList;

/**
 * Created by Jack on 10/12/2018.
 */

public interface AsyncResponse {
    void processFinish(ArrayList<News> news, String site, boolean allNews);
}

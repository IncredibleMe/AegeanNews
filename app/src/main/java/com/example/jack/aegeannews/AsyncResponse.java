package com.example.jack.aegeannews;

import java.util.ArrayList;

/**
 * Created by Jack on 10/12/2018.
 */

public interface AsyncResponse {
    void processFinish(ArrayList<News> news, String site, boolean allNews);
}

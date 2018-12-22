package com.example.jack.aegeannews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GrabNews extends AsyncTask<Void, Void, ArrayList<News> > {
    public AsyncResponse delegate = null;
    public void grabSamos24() {

    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        delegate.processFinish(news);
    }

    @Override
    protected ArrayList<News> doInBackground(Void... voids) {
        String URL = "https://www.samos24.gr/";
        ArrayList<News> news = new ArrayList<>();
        try {
            int i = 0;
            //2. Fetch the HTML code
            Document document = Jsoup.connect(URL).get();
            //3. Parse the HTML to extract links to other URLs
            Elements linksOnPage = document.getElementsByClass("mvp-feat1-list-cont");

            //query for getting the news title
            for (Element page : linksOnPage) {
                Elements titles = page.select("h2");
                news.add(new News());
                //System.out.println(titles.html());

                news.get(i++).setTitle(titles.html());

            }
            //query for getting the news image
            i = 0;
            for (Element element : document.getElementsByClass("mvp-feat1-list-img")) {
                //System.out.println(element.html());
                //System.out.println( element.select("[src]").attr("abs:src"));
                String imageUrl = (element.select("[src]").attr("abs:src"));
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(imageUrl).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                news.get(i++).setImageBit(mIcon11);
            }
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }

        return news;
    }
}

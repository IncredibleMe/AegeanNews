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

public class GrabNewsSamos extends AsyncTask<Void, Void, ArrayList<News> > {
    public AsyncResponse delegate = null;
    private boolean allNews;

    public GrabNewsSamos(boolean allNews) {
        this.allNews = allNews;
    }

    public void grabSamos24(boolean allNews ) {
        this.allNews = allNews;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        delegate.processFinish(news, "Samos", allNews);
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
                news.get(i).setSitename("www.samos24.gr");
                news.get(i++).setTitle(titles.html());
                if (i==10)
                    break;
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
                if (i==10)
                    break;
            }

            Element column = document.getElementById("mvp-feat-tab-col1");
            //query for getting the news title
            i = 0;
            for (Element link : column.select("a[href]")) {
                //gets all the links of each new and set them to the approriate new object
                news.get(i++).setLink(link.attr("abs:href"));
            }
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }

        return news;
    }
}

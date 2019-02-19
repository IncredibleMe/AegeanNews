package com.example.jack.aegeannews;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jack.aegeannews.NewsGrabber.News;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> newsList;
    private PopupWindow pw;
    private ProgressBar mPbar = null;




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView title, source;
        public ImageView thumbnail, overflow;
        public String link;
        public View layout;
        public WebView myWebView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            source =  view.findViewById(R.id.source);
            thumbnail = view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
            view.setOnClickListener(this);
            title.setOnClickListener(this);
            thumbnail.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            try {
                //We need to get the instance of the LayoutInflater, use the context of this activity
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                layout = inflater.inflate(R.layout.popup, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                // create a 300px width and 470px height PopupWindow
                pw = new PopupWindow(layout, width, height, true);
                // display the popup in the center
                pw.showAtLocation(view, Gravity.CENTER, 0, 0);



                myWebView = layout.findViewById(R.id.myWebView);
                myWebView.setVisibility(View.INVISIBLE);
                mPbar = layout.findViewById(R.id.web_view_progress);

                myWebView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        mPbar.setVisibility(View.VISIBLE);
                    }

                    public void onPageFinished(WebView view, String url) {
                        myWebView.setVisibility(View.VISIBLE);
                        mPbar.setVisibility(View.GONE);
                    }
                });
                Log.e("URL", link);
                myWebView.loadUrl(link);

                //Button cancelButton = (Button) layout.findViewById(R.id.end_data_send_button);
                //cancelButton.setOnClickListener(cancel_button_click_listener);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public NewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.thumbnail.setImageBitmap(news.getImageBit());
        holder.link = news.getLink();
        holder.source.setText(news.getSitename());
//
//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);
    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
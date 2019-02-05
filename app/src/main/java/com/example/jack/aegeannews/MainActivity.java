package com.example.jack.aegeannews;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    private ShimmerRecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newslist; //i lista pou xrisimopoieitai gia na emfanizontai ta dedomena sta views
    private ArrayList<News> allNews;
    private ArrayList<News> samosNews;
    private ArrayList<News> lesvosNews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        Intent intent = new Intent(this, SplashScreen.class);
//        startActivity(intent);
//        finish();

        setSupportActionBar(toolbar);
        newslist = new ArrayList<>();
        samosNews = new ArrayList<>();
        lesvosNews = new ArrayList<>();
        allNews = new ArrayList<>();

        recyclerView = (ShimmerRecyclerView) findViewById(R.id.recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsAdapter(this, newslist);
        recyclerView.setAdapter(adapter);

        recyclerView.showShimmerAdapter();
        adapter.notifyDataSetChanged();

        GrabNewsSamos grabNews = new GrabNewsSamos(false);
        grabNews.delegate = this;
        grabNews.execute();


        try {
            // Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setChecked(true);
        int id = item.getItemId();

        if (id == R.id.allnews) {
            if (samosNews.isEmpty()) {
                recyclerView.showShimmerAdapter();
                Log.e("ALL", "Samos news is empty");
                GrabNewsSamos grabNews = new GrabNewsSamos(true);
                grabNews.delegate = this;
                grabNews.execute();
            }
            if (lesvosNews.isEmpty()){
                recyclerView.showShimmerAdapter();
                Log.e("ALL", "Lesvos news is empty");
                GrabNewsLesvos grabNews2 = new GrabNewsLesvos(true);
                grabNews2.delegate = this;
                grabNews2.execute();
            }
            else{
                this.newslist.clear();
                this.newslist.addAll(allNews);
                Collections.shuffle(this.newslist);
            }
            recyclerView.hideShimmerAdapter();
            adapter.notifyDataSetChanged();

        } else if (id == R.id.samosnews) {
            if (samosNews.isEmpty()) {
                recyclerView.showShimmerAdapter();
                adapter.notifyDataSetChanged();

                GrabNewsSamos grabNews = new GrabNewsSamos(false);
                grabNews.delegate = this;
                grabNews.execute();
            }
            else{
                this.newslist.clear();
                this.newslist.addAll(samosNews);
                adapter.notifyDataSetChanged();
            }

        } else if (id == R.id.chiosnews) {

        } else if (id == R.id.mitilininews) {
            if (lesvosNews.isEmpty()) {
                recyclerView.showShimmerAdapter();
                adapter.notifyDataSetChanged();

                GrabNewsLesvos grabNews = new GrabNewsLesvos(false);
                grabNews.delegate = this;
                grabNews.execute();
            }
            else{
                this.newslist.clear();
                this.newslist.addAll(lesvosNews);
                adapter.notifyDataSetChanged();
            }
        } else if (id == R.id.limnosnews) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void processFinish(ArrayList<News> news, String site, boolean allNews) {
        recyclerView.hideShimmerAdapter();
        if (site.equals("Samos")) {
            this.samosNews.addAll(news);
            this.allNews.addAll(samosNews);
            this.newslist.clear();
            this.newslist.addAll(samosNews);
        }
        else if (site.equals("Lesvos")) {
            this.lesvosNews.addAll(news);
            this.allNews.addAll(lesvosNews);
            this.newslist.clear();
            this.newslist.addAll(lesvosNews);
        }
        if (allNews){
            this.newslist.clear();
            this.newslist.addAll(samosNews);
            this.newslist.addAll(lesvosNews);
            Collections.shuffle(this.newslist);
        }
        adapter.notifyDataSetChanged();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

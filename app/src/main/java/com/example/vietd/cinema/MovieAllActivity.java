package com.example.vietd.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieAllActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout navigation_draw;
    private ListView lv_menu;
    private  item_menu_adpter menu_adpter;
    private ArrayList<item_menu_info> arrayMenu;
    private ListView listview;
    private MovieAdapter adapter;
    private ArrayList<MovieInfo> arrayList;
    private Socket mSocket;
    private JSONArray jsonarray;
    private Emitter.Listener getData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        arrayList = new ArrayList<MovieInfo>();
                        jsonarray = data.getJSONArray("listmovie");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            MovieInfo movies = new MovieInfo(
                                    jsonarray.getJSONObject(i).getString("name"),
                                    jsonarray.getJSONObject(i).getString("startday"),
                                    jsonarray.getJSONObject(i).getDouble("imdb"),
                                    jsonarray.getJSONObject(i).getInt("duration"),
                                    "http://192.168.16.109:3000" + jsonarray.getJSONObject(i).getString("image"),
                                    jsonarray.getJSONObject(i).getInt("ages"),
                                    jsonarray.getJSONObject(i).getInt("format")
                            );
                            arrayList.add(movies);
                        }
                        setMoviesList();
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    {
        try {
            mSocket = IO.socket("http://192.168.16.109:3000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_all);

        listview = (ListView) findViewById(R.id.movie_list_view);
        mSocket.connect();
        mSocket.on("data", getData);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        createDraw();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MovieAllActivity.this, MovieDetail.class);

                try {
                    i.putExtra("name", jsonarray.getJSONObject(position).getString("name"));
                    i.putExtra("duration", String.valueOf(jsonarray.getJSONObject(position).getInt("duration")));
                    i.putExtra("director", jsonarray.getJSONObject(position).getString("director"));
                    i.putExtra("actornactress", jsonarray.getJSONObject(position).getString("actornactress"));
                    i.putExtra("nation", jsonarray.getJSONObject(position).getString("nation"));
                    i.putExtra("language", jsonarray.getJSONObject(position).getString("language"));
                    i.putExtra("category", jsonarray.getJSONObject(position).getString("category"));
                    i.putExtra("startday", jsonarray.getJSONObject(position).getString("startday"));
                    i.putExtra("format", jsonarray.getJSONObject(position).getString("format"));
                    i.putExtra("imdb", String.valueOf(jsonarray.getJSONObject(position).getDouble("imdb")));
                    i.putExtra("urltrailer", jsonarray.getJSONObject(position).getString("urltrailer"));
                    i.putExtra("content", jsonarray.getJSONObject(position).getString("content"));
                    i.putExtra("poster", "http://192.168.16.109:3000" + jsonarray.getJSONObject(position).getString("image"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });

    }

    private void setMoviesList() {
        adapter = new MovieAdapter(this, R.layout.movie_item, arrayList);
        listview.setAdapter(adapter);
    }

    public void createDraw(){
        lv_menu = (ListView)findViewById(R.id.lv_menu);
        arrayMenu = new ArrayList<item_menu_info>();
        item_menu_info item = new item_menu_info(R.drawable.ic_exit_to_app_black_24dp,"Login");
        item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp,"Home");
        item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "List movies");
        item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
        item_menu_info item4 = new item_menu_info(R.drawable.ic_power_settings_new_black_24dp, "Logout");
        arrayMenu.add(item);
        arrayMenu.add(item1);
        arrayMenu.add(item2);
        arrayMenu.add(item3);
        arrayMenu.add(item4);
        menu_adpter = new item_menu_adpter(this,R.layout.item_menu_layout,arrayMenu);
        lv_menu.setAdapter(menu_adpter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.action_menu_bar ){
            if(mDrawerLayout.isDrawerOpen(navigation_draw)){
                mDrawerLayout.closeDrawer(navigation_draw);
            }else{
                mDrawerLayout.openDrawer(Gravity.END);
            }

        }
        return  super.onOptionsItemSelected(item);
    }
}


package com.example.vietd.cinema;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieAllActivity extends AppCompatActivity {

    Config mConfig = new Config();
    Socket mSocket = mConfig.mSocket;
    String link = mConfig.link;
    private UserSessionManager userSessionManager;
    private String username = "";
    private TextView tv_name_user;
    private DrawerLayout mDrawerLayout;
    private LinearLayout navigation_draw;
    private ListView lv_menu;
    private item_menu_adpter menu_adpter;
    private ArrayList<item_menu_info> arrayMenu;
    private ListView listview;
    private MovieAdapter adapter;
    private ArrayList<MovieInfo> arrayList;
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
                                    link + jsonarray.getJSONObject(i).getString("image"),
                                    jsonarray.getJSONObject(i).getInt("ages"),
                                    jsonarray.getJSONObject(i).getInt("format"),
                                    jsonarray.getJSONObject(i).getInt("status")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_all);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.movie_list_view);
        mSocket.connect();
        mSocket.emit("laydulieu");
        mSocket.on("data", getData);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        tv_name_user = (TextView)findViewById(R.id.tv_name_user);

        userSessionManager = new UserSessionManager(getApplicationContext());
        if (userSessionManager.checkLogin()) {
            HashMap<String, String> user = userSessionManager.getUserDetails();
            username = user.get(userSessionManager.KEY_USERNAME);
            tv_name_user.setText(username);
        } else {
            tv_name_user.setText("Vui lòng đăng nhập!");
        }
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = (TextView) view.findViewById(R.id.tv_item_menu);
                String item_menu = tv_item.getText().toString();

                if (item_menu.equals("Trang chủ")) {
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                }

                if (item_menu.equals("Danh sách phim")) {
                    mDrawerLayout.closeDrawer(navigation_draw);
                }

                if (item_menu.equals("Đăng nhập/Đăng ký")) {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
                if (item_menu.equals("Thông tin")) {
                    Dialog d = new Dialog(MovieAllActivity.this);
                    d.setContentView(R.layout.about);
                    d.setTitle("THÔNG TIN");
                    d.show();
                }
                if (item_menu.equals("Đăng xuất")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(MovieAllActivity.this);

                    b.setTitle("Bạn thực sự muốn đăng xuất ?");

                    b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userSessionManager.logoutUser();
                            createDraw();
                            tv_name_user.setText("Vui lòng đăng nhập!");
                        }
                    });

                    b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    b.create().show();
                }

                mDrawerLayout.closeDrawer(navigation_draw);
            }
        });
        createDraw();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MovieAllActivity.this, MovieDetail.class);
                try {
                    i.putExtra("idmovie", jsonarray.getJSONObject(position).getString("id"));
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
                    i.putExtra("poster", link + jsonarray.getJSONObject(position).getString("image"));
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

    public void createDraw() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        arrayMenu = new ArrayList<item_menu_info>();
        if (!userSessionManager.checkLogin()) {
            item_menu_info item = new item_menu_info(R.drawable.ic_exit_to_app_black_24dp, "Đăng nhập/Đăng ký");
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Trang chủ");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "Danh sách phim");
            item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "Thông tin");
            arrayMenu.add(item);
            arrayMenu.add(item1);
            arrayMenu.add(item2);
            arrayMenu.add(item3);
        } else {
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Trang chủ");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "Danh sách phim");
            item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "Thông tin");
            item_menu_info item4 = new item_menu_info(R.drawable.ic_power_settings_new_black_24dp, "Đăng xuất");
            arrayMenu.add(item1);
            arrayMenu.add(item2);
            arrayMenu.add(item3);
            arrayMenu.add(item4);
        }
        menu_adpter = new item_menu_adpter(this, R.layout.item_menu_layout, arrayMenu);
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
        if (id == R.id.action_menu_bar) {
            if (mDrawerLayout.isDrawerOpen(navigation_draw)) {
                mDrawerLayout.closeDrawer(navigation_draw);
            } else {
                mDrawerLayout.openDrawer(Gravity.END);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


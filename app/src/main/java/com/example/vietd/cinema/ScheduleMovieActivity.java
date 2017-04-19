package com.example.vietd.cinema;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ScheduleMovieActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private LinearLayout navigation_draw;
    private ListView lv_menu;
    private item_menu_adpter menu_adpter;
    private ArrayList<item_menu_info> arrayMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_movie);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        createDraw();

    }

    public void createDraw() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        arrayMenu = new ArrayList<item_menu_info>();
        item_menu_info item = new item_menu_info(R.drawable.ic_exit_to_app_black_24dp, "Login");
        item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Home");
        item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "List movies");
        item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
        item_menu_info item4 = new item_menu_info(R.drawable.ic_power_settings_new_black_24dp, "Logout");
        arrayMenu.add(item);
        arrayMenu.add(item1);
        arrayMenu.add(item2);
        arrayMenu.add(item3);
        arrayMenu.add(item4);
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

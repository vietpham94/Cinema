package com.example.vietd.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;
    private LinearLayout navigation_draw;
    private ListView lv_menu;
    private item_menu_adpter menu_adpter;
    private ArrayList<item_menu_info> arrayMenu;
    private UserSessionManager userSessionManager;
    private String username = "";
    private TextView tv_name_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        tv_name_user = (TextView)findViewById(R.id.tv_name_user);

        userSessionManager = new UserSessionManager(getApplicationContext());
        if(userSessionManager.checkLogin()) {
            HashMap<String, String> user = userSessionManager.getUserDetails();
            username = user.get(userSessionManager.KEY_USERNAME);
            tv_name_user.setText(username);
        }else{
            tv_name_user.setText("Plsease login!");
        }

        createDraw();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.conent_frame, new TextTabhostFragment())
                .addToBackStack(null)
                .commit();

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = (TextView) view.findViewById(R.id.tv_item_menu);
                String item_menu = tv_item.getText().toString();

                if (item_menu.equals("Home")) {
                    fragmentManager.beginTransaction().replace(R.id.conent_frame, new TextTabhostFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item_menu.equals("List movies")) {
                    Intent i = new Intent(MainActivity.this, MovieAllActivity.class);
                    startActivity(i);
                }

                if (item_menu.equals("Login")) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.conent_frame, new SigninFragment())
                            .addToBackStack(null)
                            .commit();
                }

                mDrawerLayout.closeDrawer(navigation_draw);
            }
        });
    }

    public void createDraw() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        arrayMenu = new ArrayList<item_menu_info>();
        if(username.equals("")){
            item_menu_info item = new item_menu_info(R.drawable.ic_exit_to_app_black_24dp, "Login");
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Home");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "List movies");
            item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
            arrayMenu.add(item);
            arrayMenu.add(item1);
            arrayMenu.add(item2);
            arrayMenu.add(item3);
        }else{
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Home");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "List movies");
            item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
            item_menu_info item4 = new item_menu_info(R.drawable.ic_power_settings_new_black_24dp, "Logout");
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

    @Override
    protected void onPause() {
        if (mDrawerLayout.isDrawerOpen(navigation_draw)) {
            mDrawerLayout.closeDrawer(navigation_draw);
        }
        super.onPause();
    }
}

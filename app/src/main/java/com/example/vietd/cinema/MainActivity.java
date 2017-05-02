package com.example.vietd.cinema;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

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
    private String username = "", result = null;
    private TextView tv_name_user;
    private HashMap<String, String> user;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.conent_frame, new SigninFragment())
                    .addToBackStack(null)
                    .commit();
        }
        if(resultCode == 2){
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.conent_frame, new TextTabhostFragment())
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        tv_name_user = (TextView) findViewById(R.id.tv_name_user);

        userSessionManager = new UserSessionManager(getApplicationContext());

        if (userSessionManager.checkLogin()) {
            user = userSessionManager.getUserDetails();
            username = user.get(userSessionManager.KEY_fullname);
            tv_name_user.setText(username);
        } else {
            tv_name_user.setText("Vui lòng đăng nhập!");
        }

        createDraw();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.conent_frame, new TextTabhostFragment())
                .commit();

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = (TextView) view.findViewById(R.id.tv_item_menu);
                String item_menu = tv_item.getText().toString();

                if (item_menu.equals("Trang chủ")) {
                    fragmentManager.beginTransaction().replace(R.id.conent_frame, new TextTabhostFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item_menu.equals("Danh sách phim")) {
                    Intent i = new Intent(MainActivity.this, MovieAllActivity.class);
                    startActivityForResult(i, 1);
                }

                if (item_menu.equals("Đăng nhập/Đăng ký")) {
                    fragmentManager.beginTransaction().replace(R.id.conent_frame, new SigninFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item_menu.equals("Đăng xuất")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

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
    }

    public void createDraw() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        arrayMenu = new ArrayList<item_menu_info>();
        if (!userSessionManager.checkLogin()) {
            item_menu_info item = new item_menu_info(R.drawable.ic_exit_to_app_black_24dp, "Đăng nhập/Đăng ký");
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Trang chủ");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "Danh sách phim");
            //item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
            arrayMenu.add(item);
            arrayMenu.add(item1);
            arrayMenu.add(item2);
            //arrayMenu.add(item3);
        } else {
            item_menu_info item1 = new item_menu_info(R.drawable.ic_home_black_24dp, "Trang chủ");
            item_menu_info item2 = new item_menu_info(R.drawable.ic_view_list_black_24dp, "Danh sách phim");
            //item_menu_info item3 = new item_menu_info(R.drawable.ic_info_outline_black_24dp, "CGV info");
            item_menu_info item4 = new item_menu_info(R.drawable.ic_power_settings_new_black_24dp, "Đăng xuất");
            arrayMenu.add(item1);
            arrayMenu.add(item2);
            //arrayMenu.add(item3);
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

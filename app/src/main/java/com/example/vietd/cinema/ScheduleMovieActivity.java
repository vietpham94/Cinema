package com.example.vietd.cinema;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleMovieActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout navigation_draw;
    private ListView lv_menu;
    private item_menu_adpter menu_adpter;
    private ArrayList<item_menu_info> arrayMenu;
    private TextView day1, day2, day3;

    private RecyclerView recyclerView, recyclerView1, recyclerView2;
    private List<ScheduleInfo> scheduleInfos = new ArrayList<>();
    private List<ScheduleInfo1> scheduleInfos1 = new ArrayList<>();
    private List<ScheduleInfo2> scheduleInfos2 = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;
    private ScheduleAdapter1 scheduleAdapter1;
    private ScheduleAdapter2 scheduleAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_movie);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        navigation_draw = (LinearLayout) findViewById(R.id.navigation_draw);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        createDraw();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_schedule1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_schedule2);

        day1 = (TextView) findViewById(R.id.txt_schedule_day);
        day2 = (TextView) findViewById(R.id.txt_schedule_day1);
        day3 = (TextView) findViewById(R.id.txt_schedule_day2);

        scheduleAdapter = new ScheduleAdapter(scheduleInfos);
        scheduleAdapter1 = new ScheduleAdapter1(scheduleInfos1);
        scheduleAdapter2 = new ScheduleAdapter2(scheduleInfos2);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_YEAR, 1);
        String strTomorrow = mdformat.format(calendar1.getTime());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_YEAR, 2);
        String strTomorrow2 = mdformat.format(calendar2.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Date d1 = new Date();
        d1.setDate(d.getDate() + 1);
        String dayOfTheWeek1 = sdf.format(d1);
        Date d2 = new Date();
        d2.setDate(d1.getDate() + 1);
        String dayOfTheWeek2 = sdf.format(d2);

        day1.setText(strDate + " - " + dayOfTheWeek);
        day2.setText(strTomorrow + " - " + dayOfTheWeek1);
        day3.setText(strTomorrow2 + " - " + dayOfTheWeek2);

        int numberOfColumns = 5;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);*/
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scheduleAdapter);

      /*RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);*/
        recyclerView1.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(scheduleAdapter1);

        /*RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);*/
        recyclerView2.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(scheduleAdapter2);

        quaylen();

    }

/*    private String dayOfTheWeek(String dayOfTheWeek) {
        switch (dayOfTheWeek) {
            case "Monday":
                dayOfTheWeek = "Thứ 2";
                break;
            case "Tuesday":
                dayOfTheWeek = "Thứ 3";
                break;
            case "Wednesday":
                dayOfTheWeek = "Thứ 4";
                break;
            case "Thursday":
                dayOfTheWeek = "Thứ 5";
                break;
            case "Friday":
                dayOfTheWeek = "Thứ 6";
                break;
            case "Saturday":
                dayOfTheWeek = "Thứ 7";
                break;
            default:
                dayOfTheWeek = "Chủ nhật";
                break;
        }
        return dayOfTheWeek;
    }*/

    private void quaylen() {
        String[] a = {"11661", "22662", "22366", "36533"};

        ScheduleInfo item = new ScheduleInfo(a[2]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[3]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[0]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);
        item = new ScheduleInfo(a[1]);
        scheduleInfos.add(item);


        ScheduleInfo1 item1 = new ScheduleInfo1(a[0]);
        scheduleInfos1.add(item1);
        item1 = new ScheduleInfo1(a[1]);
        scheduleInfos1.add(item1);
        item1 = new ScheduleInfo1(a[1]);
        scheduleInfos1.add(item1);
        item1 = new ScheduleInfo1(a[1]);
        scheduleInfos1.add(item1);
        item1 = new ScheduleInfo1(a[1]);
        scheduleInfos1.add(item1);

        ScheduleInfo2 item2 = new ScheduleInfo2(a[0]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);
        item2 = new ScheduleInfo2(a[1]);
        scheduleInfos2.add(item2);


        scheduleAdapter.notifyDataSetChanged();
        scheduleAdapter1.notifyDataSetChanged();
        scheduleAdapter2.notifyDataSetChanged();
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

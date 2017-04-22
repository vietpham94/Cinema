package com.example.vietd.cinema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleMovieActivity extends AppCompatActivity {

    Config mConfig = new Config();
    Socket mSocket = mConfig.mSocket;
    private TextView day1, day2, day3, tv_name_movie;
    private RecyclerView recyclerView, recyclerView1, recyclerView2;
    private List<ScheduleInfo> scheduleInfos = new ArrayList<>();
    private List<ScheduleInfo1> scheduleInfos1 = new ArrayList<>();
    private List<ScheduleInfo2> scheduleInfos2 = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;
    private ScheduleAdapter1 scheduleAdapter1;
    private ScheduleAdapter2 scheduleAdapter2;
    private String strDate, strTomorrow, strTomorrow2;
    private JSONArray jsonarray;
    private String idmovie, name, duration, director, actornactress, nation, language, category, startday, format, imdb, urltrailer, content, poster;

    private Emitter.Listener scheduleList = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        jsonarray = data.getJSONArray("scheduleList");
                        quaylen();

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
        setContentView(R.layout.activity_schedule_movie);

        Bundle bd = getIntent().getExtras();
        idmovie = bd.getString("idmovie");
        name = bd.getString("name");
        duration = bd.getString("duration");
        director = bd.getString("director");
        actornactress = bd.getString("actornactress");
        nation = bd.getString("nation");
        language = bd.getString("language");
        category = bd.getString("category");
        startday = bd.getString("startday");
        format = bd.getString("format");
        imdb = bd.getString("imdb");
        urltrailer = bd.getString("urltrailer");
        content = bd.getString("content");
        poster = bd.getString("poster");
        MovieInfoForBooking movieInfo = new MovieInfoForBooking(Integer.parseInt(idmovie), name, Double.parseDouble(imdb), poster, Integer.parseInt(duration), director, actornactress, language, nation, startday, Integer.parseInt(format), urltrailer, category, content);

        mSocket.connect();
        mSocket.emit("getAllScheduleByIdMovie", idmovie);
        mSocket.on("scheduleList", scheduleList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_schedule1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_schedule2);

        day1 = (TextView) findViewById(R.id.txt_schedule_day);
        day2 = (TextView) findViewById(R.id.txt_schedule_day1);
        day3 = (TextView) findViewById(R.id.txt_schedule_day2);
        tv_name_movie = (TextView) findViewById(R.id.txt_movie_schedule_name);

        tv_name_movie.setText(name);

        scheduleAdapter = new ScheduleAdapter(scheduleInfos, movieInfo);
        scheduleAdapter1 = new ScheduleAdapter1(scheduleInfos1, movieInfo);
        scheduleAdapter2 = new ScheduleAdapter2(scheduleInfos2, movieInfo);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        strDate = mdformat.format(calendar.getTime());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_YEAR, 1);
        strTomorrow = mdformat.format(calendar1.getTime());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_YEAR, 2);
        strTomorrow2 = mdformat.format(calendar2.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Date d1 = new Date();
        d1.setDate(d.getDate() + 1);
        String dayOfTheWeek1 = sdf.format(d1);
        Date d2 = new Date();
        d2.setDate(d1.getDate() + 1);
        String dayOfTheWeek2 = sdf.format(d2);

        day1.setText(dayOfTheWeek + " - " + strDate);
        day2.setText(dayOfTheWeek1 + " - " + strTomorrow);
        day3.setText(dayOfTheWeek2 + " - " + strTomorrow2);

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


    }

    private void quaylen() {

        ScheduleInfo item;
        ScheduleInfo1 item1;
        ScheduleInfo2 item2;

        for (int i = 0; i < jsonarray.length(); i++) {

            try {
                if (jsonarray.getJSONObject(i).getString("showday").equals(strDate.toString())) {
                    switch (jsonarray.getJSONObject(i).getString("time")) {
                        case "1":
                            item = new ScheduleInfo(1, "09h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        case "2":
                            item = new ScheduleInfo(2, "11h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        case "3":
                            item = new ScheduleInfo(3, "14h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        case "4":
                            item = new ScheduleInfo(4, "17h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        case "5":
                            item = new ScheduleInfo(5, "19h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        case "6":
                            item = new ScheduleInfo(6, "21h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos.add(item);
                            break;
                        default:
                            item = new ScheduleInfo(0, "00h00", "0", 0);
                            scheduleInfos.add(item);
                            break;
                    }

                }

                if (jsonarray.getJSONObject(i).getString("showday").equals(strTomorrow.toString())) {
                    switch (jsonarray.getJSONObject(i).getString("time")) {
                        case "1":
                            item1 = new ScheduleInfo1(1, "09h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        case "2":
                            item1 = new ScheduleInfo1(2, "11h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        case "3":
                            item1 = new ScheduleInfo1(3, "14h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        case "4":
                            item1 = new ScheduleInfo1(4, "17h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        case "5":
                            item1 = new ScheduleInfo1(5, "19h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        case "6":
                            item1 = new ScheduleInfo1(6, "21h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos1.add(item1);
                            break;
                        default:
                            item1 = new ScheduleInfo1(0, "00h00", "0", 0);
                            scheduleInfos1.add(item1);
                            break;
                    }

                }

                if (jsonarray.getJSONObject(i).getString("showday").equals(strTomorrow2.toString())) {
                    switch (jsonarray.getJSONObject(i).getString("time")) {
                        case "1":
                            item2 = new ScheduleInfo2(1, "09h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        case "2":
                            item2 = new ScheduleInfo2(2, "11h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        case "3":
                            item2 = new ScheduleInfo2(3, "14h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        case "4":
                            item2 = new ScheduleInfo2(4, "17h00", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        case "5":
                            item2 = new ScheduleInfo2(5, "19h40", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        case "6":
                            item2 = new ScheduleInfo2(6, "21h20", jsonarray.getJSONObject(i).getString("showday"), jsonarray.getJSONObject(i).getInt("idroom"));
                            scheduleInfos2.add(item2);
                            break;
                        default:
                            item2 = new ScheduleInfo2(0, "00h00", "0", 0);
                            scheduleInfos2.add(item2);
                            break;
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        scheduleAdapter.notifyDataSetChanged();
        scheduleAdapter1.notifyDataSetChanged();
        scheduleAdapter2.notifyDataSetChanged();
    }

}

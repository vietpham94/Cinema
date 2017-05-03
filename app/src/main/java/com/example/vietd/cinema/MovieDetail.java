package com.example.vietd.cinema;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;
//YouTubeBaseActivity

public class MovieDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public String API_KEY = "AIzaSyBZj3624Oh1C0-sib0dqQn0xPGqdEA5LCk";
    public String VIDEO_ID = "rJdTUnrrISk";

    private TextView name, duration, director, actor, country, language, genres, date, imdb, format, content;
    private ImageView poster;
    private YouTubePlayerView trailer;
    private UserSessionManager userSessionManager;
    private Button btn_Booking;
    private int Count = 0;
    private String dateCompare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        name = (TextView) findViewById(R.id.txt_movie_detail_name);
        duration = (TextView) findViewById(R.id.txt_movie_detail_duration);
        director = (TextView) findViewById(R.id.txt_movie_detail_director);
        actor = (TextView) findViewById(R.id.txt_movie_detail_actor);
        country = (TextView) findViewById(R.id.txt_movie_detail_country);
        language = (TextView) findViewById(R.id.txt_movie_detail_language);
        genres = (TextView) findViewById(R.id.txt_movie_detail_genres);
        date = (TextView) findViewById(R.id.txt_movie_detail_date_release);
        imdb = (TextView) findViewById(R.id.txt_movie_detail_imdb_point);
        format = (TextView) findViewById(R.id.txt_movie_detail_format);
        poster = (ImageView) findViewById(R.id.img_movie_detail_poster);
        trailer = (YouTubePlayerView) findViewById(R.id.vid_movie_detail_trailer);
        content = (TextView) findViewById(R.id.txt_movie_detail_content);
        btn_Booking = (Button) findViewById(R.id.btn_movie_detail_booking);

        final Intent i = getIntent();
        Resources res = getResources();

        name.setText(i.getStringExtra("name"));

        duration.setText(Html.fromHtml(res.getString(R.string.duration)) + i.getStringExtra("duration") + " phút");
        director.setText(Html.fromHtml(res.getString(R.string.director)) + i.getStringExtra("director"));
        actor.setText(Html.fromHtml(res.getString(R.string.actor)) + i.getStringExtra("actornactress"));
        country.setText(Html.fromHtml(res.getString(R.string.nation)) + i.getStringExtra("nation"));
        language.setText(Html.fromHtml(res.getString(R.string.language)) + i.getStringExtra("language"));
        genres.setText(Html.fromHtml(res.getString(R.string.genre)) + i.getStringExtra("category"));
        date.setText(Html.fromHtml(res.getString(R.string.release)) + i.getStringExtra("startday"));
        imdb.setText(Html.fromHtml(res.getString(R.string.imdb)) + i.getStringExtra("imdb") + "/10");
        if (i.getStringExtra("format") == "1") {
            format.setText(Html.fromHtml(res.getString(R.string.format)) + "2D");
        } else {
            format.setText(Html.fromHtml(res.getString(R.string.format)) + "3D");
        }


        VIDEO_ID = i.getStringExtra("urltrailer");
        VIDEO_ID = VIDEO_ID.split("v=")[1];

        content.setText(i.getStringExtra("content"));
        trailer.initialize(API_KEY, MovieDetail.this);

        Picasso.with(MovieDetail.this).load(i.getStringExtra("poster")).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(poster);

        btn_Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSessionManager = new UserSessionManager(getApplicationContext());
                if (userSessionManager.checkLogin()) {
                    Intent intent = new Intent(MovieDetail.this, ScheduleMovieActivity.class);
                    intent.putExtra("idmovie", i.getStringExtra("idmovie"));
                    intent.putExtra("name", i.getStringExtra("name"));
                    intent.putExtra("duration", i.getStringExtra("duration"));
                    intent.putExtra("director", i.getStringExtra("director"));
                    intent.putExtra("actornactress", i.getStringExtra("actornactress"));
                    intent.putExtra("nation", i.getStringExtra("nation"));
                    intent.putExtra("language", i.getStringExtra("language"));
                    intent.putExtra("category", i.getStringExtra("category"));
                    intent.putExtra("startday", i.getStringExtra("startday"));
                    intent.putExtra("format", i.getStringExtra("format"));
                    intent.putExtra("imdb", i.getStringExtra("imdb"));
                    intent.putExtra("urltrailer", i.getStringExtra("urltrailer"));
                    intent.putExtra("content", i.getStringExtra("content"));
                    intent.putExtra("poster", i.getStringExtra("poster"));
                    dateCompare = i.getStringExtra("startday");
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = mdformat.format(calendar.getTime());
                    Date dateNow = null;
                    try {
                        dateNow = mdformat.parse(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date dateCom = null;
                    try {
                        dateCom = mdformat.parse(dateCompare);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dateCom.after(dateNow)) {
                        Count = 1;
                    }
                    if (Count == 1) {
                        Toast.makeText(getApplicationContext(), "Hiện tại chưa có lịch chiếu cho phim này!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Kien!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MovieDetail.this, "Vui lòng đăng nhập trước khi đặt phim", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Lỗi tải Video", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (null == player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }
}

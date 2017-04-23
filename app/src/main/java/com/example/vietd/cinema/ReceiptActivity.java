package com.example.vietd.cinema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ReceiptActivity extends AppCompatActivity {

    private MovieInfoForBooking data;
    private int id_time;
    private String date;
    private ArrayList<String> seat = new ArrayList<String>();
    private ImageView poster;
    private TextView movie_name, date_schedule, format, timeframe, qtynormal, qtyvip, qtycouple, subnormal, subvip, subcouple, quantity, total;
    private Button place_order;
    private ArrayList<Integer> listPrice = new ArrayList<Integer>();
    private int numbNormal = 0, numbVip = 0, numbCouple = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        poster = (ImageView) findViewById(R.id.movie_poster);
        movie_name = (TextView) findViewById(R.id.movie_name);
        date_schedule = (TextView) findViewById(R.id.movie_date);
        format = (TextView) findViewById(R.id.movie_format);
        timeframe = (TextView) findViewById(R.id.movie_time);
        qtynormal = (TextView) findViewById(R.id.qty_normal);
        qtycouple = (TextView) findViewById(R.id.qty_couple);
        qtyvip = (TextView) findViewById(R.id.qty_VIP);
        subnormal = (TextView) findViewById(R.id.sub_normal);
        subcouple = (TextView) findViewById(R.id.sub_couple);
        subvip = (TextView) findViewById(R.id.sub_VIP);
        quantity = (TextView) findViewById(R.id.qty_total);
        total = (TextView) findViewById(R.id.sub_total);
        place_order = (Button) findViewById(R.id.btn_place_order);

        Bundle bn = getIntent().getBundleExtra("bundle");
        data = (MovieInfoForBooking) bn.getSerializable("data");
        id_time = bn.getInt("id_time");
        date = bn.getString("date");
        seat = bn.getStringArrayList("seat");
        numbNormal = bn.getInt("numbNormal");
        numbCouple = bn.getInt("numbCouple");
        numbVip = bn.getInt("numbVip");
        listPrice = bn.getIntegerArrayList("listPrice");

        subnormal.setText("" + numbNormal * listPrice.get(0));
        subvip.setText("" + numbVip * listPrice.get(1));
        subcouple.setText("" + numbCouple * listPrice.get(2));
        total.setText("" + (numbNormal * listPrice.get(0) + numbVip * listPrice.get(1) + (numbCouple * listPrice.get(2))));
        qtynormal.setText("" + numbNormal);
        qtyvip.setText("" + numbVip);
        qtycouple.setText("" + numbCouple);
        quantity.setText("" + (numbCouple + numbVip + numbNormal));
        movie_name.setText(data.getName());
        if (data.getFormat() == 1) {
            format.setText("Định dạng: 2D");
        } else {
            format.setText("Định dạng: 3D");
        }
        date_schedule.setText("Ngày chiếu:" + date);

        switch (id_time) {
            case 1:
                timeframe.setText("Giờ chiếu: 09h00");
                break;
            case 2:
                timeframe.setText("Giờ chiếu: 11h40");
                break;
            case 3:
                timeframe.setText("Giờ chiếu: 14h20");
                break;
            case 4:
                timeframe.setText("Giờ chiếu: 17h00");
                break;
            case 5:
                timeframe.setText("Giờ chiếu: 19h40");
                break;
            case 6:
                timeframe.setText("Giờ chiếu: 21h20");
                break;
            default:
                timeframe.setText("Giờ chiếu: 00h00");
                break;
        }
        Picasso.with(ReceiptActivity.this).load(data.getImg()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(poster);

    }
}

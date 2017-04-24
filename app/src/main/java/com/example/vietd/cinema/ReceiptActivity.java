package com.example.vietd.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ReceiptActivity extends AppCompatActivity {

    Config config = new Config();
    private MovieInfoForBooking data_movie;
    private int id_time, room, typeOfDay, typeOfMovie;
    private String date;
    private ArrayList<String> seat = new ArrayList<String>();
    private ArrayList<Integer> typeOfSeat = new ArrayList<Integer>();
    private ImageView poster;
    private TextView movie_name, date_schedule, format, timeframe, qtynormal, qtyvip, qtycouple, subnormal, subvip, subcouple, quantity, total;
    private Button place_order;
    private ArrayList<Integer> listPrice = new ArrayList<Integer>();
    private int numbNormal = 0, numbVip = 0, numbCouple = 0;
    private UserSessionManager userSessionManager;
    private HashMap<String, String> user;
    private String idcustomer = null;
    private Socket mSocket = config.mSocket;
    private Emitter.Listener resultReceipt = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ReceiptActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ReceiptActivity.this, MainActivity.class);
                    startActivityForResult(i, 1);
                    finish();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mSocket.connect();
        mSocket.on("resultReceipt", resultReceipt);

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

        userSessionManager = new UserSessionManager(getApplicationContext());
        if (userSessionManager.checkLogin()) {
            user = userSessionManager.getUserDetails();
            idcustomer = user.get(userSessionManager.KEY_idcustomer);
        }

        Bundle bn = getIntent().getBundleExtra("bundle");
        data_movie = (MovieInfoForBooking) bn.getSerializable("data");
        id_time = bn.getInt("id_time");
        date = bn.getString("date");
        seat = bn.getStringArrayList("seat");
        room = bn.getInt("room");
        typeOfDay = bn.getInt("typeOfDay");
        typeOfMovie = bn.getInt("typeOfMovie");
        numbNormal = bn.getInt("numbNormal");
        numbCouple = bn.getInt("numbCouple");
        numbVip = bn.getInt("numbVip");
        listPrice = bn.getIntegerArrayList("listPrice");

        for (int i = 0; i < seat.size(); i++) {
            switch (seat.get(i)) {
                case "C3":
                    typeOfSeat.add(2);
                    break;
                case "C4":
                    typeOfSeat.add(2);
                    break;
                case "C5":
                    typeOfSeat.add(2);
                    break;
                case "C6":
                    typeOfSeat.add(2);
                    break;
                case "D3":
                    typeOfSeat.add(2);
                    break;
                case "D4":
                    typeOfSeat.add(2);
                    break;
                case "D5":
                    typeOfSeat.add(2);
                    break;
                case "D6":
                    typeOfSeat.add(2);
                    break;
                case "E1":
                    typeOfSeat.add(3);
                    break;
                case "E2":
                    typeOfSeat.add(3);
                    break;
                case "E3":
                    typeOfSeat.add(3);
                    break;
                case "E4":
                    typeOfSeat.add(3);
                    break;
                case "E5":
                    typeOfSeat.add(3);
                    break;
                case "E6":
                    typeOfSeat.add(3);
                    break;
                case "E7":
                    typeOfSeat.add(3);
                    break;
                case "E8":
                    typeOfSeat.add(3);
                    break;
                default:
                    typeOfSeat.add(1);
                    break;
            }
        }

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray nameseat = new JSONArray();
                JSONArray type = new JSONArray();
                for (int i = 0; i < seat.size(); i++) {
                    JSONObject seat1 = new JSONObject();
                    JSONObject typeSeat1 = new JSONObject();
                    try {
                        seat1.put("nameChair", seat.get(i));
                        nameseat.put(seat1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        typeSeat1.put("typeSeat", typeOfSeat.get(i));
                        type.put(typeSeat1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                mSocket.emit("Receipt", data_movie.getIdmovie(), nameseat, type, id_time, date, idcustomer, room, typeOfDay, typeOfMovie);
            }
        });

        subnormal.setText("" + numbNormal * listPrice.get(0));
        subvip.setText("" + numbVip * listPrice.get(1));
        subcouple.setText("" + numbCouple * listPrice.get(2));
        total.setText("" + (numbNormal * listPrice.get(0) + numbVip * listPrice.get(1) + (numbCouple * listPrice.get(2))));
        qtynormal.setText("" + numbNormal);
        qtyvip.setText("" + numbVip);
        qtycouple.setText("" + numbCouple);
        quantity.setText("" + (numbCouple + numbVip + numbNormal));
        movie_name.setText(data_movie.getName());
        if (data_movie.getFormat() == 1) {
            format.setText("Định dạng: 2D");
        } else {
            format.setText("Định dạng: 3D");
        }
        date_schedule.setText("Ngày chiếu: " + date);

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
        Picasso.with(ReceiptActivity.this).load(data_movie.getImg()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(poster);

    }
}

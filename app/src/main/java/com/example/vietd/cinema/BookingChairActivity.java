package com.example.vietd.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Optimus on 4/12/2017.
 */

public class BookingChairActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView numbchair;
    Config config = new Config();
    private Button a1, a2, a3, a4, a5, a6, a7, a8, b1, b2, b3, b4, b5, b6, b7, b8, c1, c2, c3, c4, c5, c6, c7, c8, d1, d2, d3, d4, d5, d6, d7, d8, s1, s2, s3, s4, s5, s6, s7, s8, next;
    private ArrayList<String> seat = new ArrayList<String>();
    private ArrayList<Integer> seatType = new ArrayList<Integer>();
    private int numbNormal = 0, numbVip = 0, numbCouple = 0;
    private TextView totalMoney;
    private Socket mSocket = config.mSocket;
    private int id_time;
    private MovieInfoForBooking data;
    private String strc = null;
    private ArrayList<Integer> listPrice = new ArrayList<Integer>();
    //lấy giá của ghế
    private Emitter.Listener priceOfSeat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        int total = 0;
                        int tg = Integer.parseInt(totalMoney.getText().toString());
                        JSONObject priceS = data.getJSONObject("price");
                        int price = priceS.getInt("price");
                        if (seat.contains(strc)) {
                            total = tg + price;
                        } else {
                            total = tg - price;
                        }
                        totalMoney.setText(String.valueOf(total));
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener TicketInfo = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject namechair = data.getJSONObject("namechair");
                        Toast.makeText(BookingChairActivity.this, namechair + "", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener ListpriceOfSeat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject priceS = data.getJSONObject("price");
                        int price = priceS.getInt("price");
                        listPrice.add(price);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketbooking_chairs);

        mSocket.connect();
        mSocket.on("priceOfSeat", priceOfSeat);
        mSocket.on("TicketInfo", TicketInfo);

        totalMoney = (TextView) findViewById(R.id.totalMoney);

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        a4 = (Button) findViewById(R.id.A4);
        a5 = (Button) findViewById(R.id.A5);
        a6 = (Button) findViewById(R.id.A6);
        a7 = (Button) findViewById(R.id.A7);
        a8 = (Button) findViewById(R.id.A8);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        b4 = (Button) findViewById(R.id.B4);
        b5 = (Button) findViewById(R.id.B5);
        b6 = (Button) findViewById(R.id.B6);
        b7 = (Button) findViewById(R.id.B7);
        b8 = (Button) findViewById(R.id.B8);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);
        c4 = (Button) findViewById(R.id.C4);
        c5 = (Button) findViewById(R.id.C5);
        c6 = (Button) findViewById(R.id.C6);
        c7 = (Button) findViewById(R.id.C7);
        c8 = (Button) findViewById(R.id.C8);
        d1 = (Button) findViewById(R.id.D1);
        d2 = (Button) findViewById(R.id.D2);
        d3 = (Button) findViewById(R.id.D3);
        d4 = (Button) findViewById(R.id.D4);
        d5 = (Button) findViewById(R.id.D5);
        d6 = (Button) findViewById(R.id.D6);
        d7 = (Button) findViewById(R.id.D7);
        d8 = (Button) findViewById(R.id.D8);
        s1 = (Button) findViewById(R.id.S1);
        s2 = (Button) findViewById(R.id.S2);
        s3 = (Button) findViewById(R.id.S3);
        s4 = (Button) findViewById(R.id.S4);
        s5 = (Button) findViewById(R.id.S5);
        s6 = (Button) findViewById(R.id.S6);
        s7 = (Button) findViewById(R.id.S7);
        s8 = (Button) findViewById(R.id.S8);
        numbchair = (TextView) findViewById(R.id.numbTicket);
        next = (Button) findViewById(R.id.button_next);

        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        a4.setOnClickListener(this);
        a5.setOnClickListener(this);
        a6.setOnClickListener(this);
        a7.setOnClickListener(this);
        a8.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);
        d1.setOnClickListener(this);
        d2.setOnClickListener(this);
        d3.setOnClickListener(this);
        d4.setOnClickListener(this);
        d5.setOnClickListener(this);
        d6.setOnClickListener(this);
        d7.setOnClickListener(this);
        d8.setOnClickListener(this);
        s1.setOnClickListener(this);
        s2.setOnClickListener(this);
        s3.setOnClickListener(this);
        s4.setOnClickListener(this);
        s5.setOnClickListener(this);
        s6.setOnClickListener(this);
        s7.setOnClickListener(this);
        s8.setOnClickListener(this);


//        Intent intent = getIntent();
        data = (MovieInfoForBooking) getIntent().getExtras().getSerializable("DATA");
        id_time = getIntent().getExtras().getInt("id_time");
        final String date = getIntent().getExtras().getString("date");
        final int room = getIntent().getExtras().getInt("room");
        getNameChair(data.idmovie, id_time, date);

        int timeFrame = 1;
        if (id_time >= 4) {
            timeFrame = 2;
        }

        getListPrice(1, timeFrame, data.getFormat());
        getListPrice(2, timeFrame, data.getFormat());
        getListPrice(3, timeFrame, data.getFormat());

        mSocket.on("ListpriceOfSeat", ListpriceOfSeat);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numbCouple == 0 && numbNormal == 0 && numbVip == 0) {
                    Toast.makeText(BookingChairActivity.this, "Bạn chưa chọn ghế nào!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(BookingChairActivity.this, ReceiptActivity.class);
                    Bundle bn = new Bundle();
                    bn.putSerializable("data", data);
                    bn.putInt("id_time", id_time);
                    bn.putString("date", date);
                    bn.putStringArrayList("seat", seat);
                    bn.putInt("numbNormal", numbNormal);
                    bn.putInt("numbVip", numbVip);
                    bn.putInt("numbCouple", numbCouple);
                    bn.putIntegerArrayList("listPrice", listPrice);
                    i.putExtra("bundle", bn);
                    startActivity(i);
                }// mSocket.emit("placeOrder", data, id_time, date, room, seat);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.A1:
                strc = "A1";
                if (!seat.contains("A1")) {
                    if (seat.size() < 8) {
                        seat.add("A1");
                        numbNormal++;
                        a1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A1");
                    numbNormal--;
                    a1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A2:
                strc = "A2";
                if (!seat.contains("A2")) {
                    if (seat.size() < 8) {
                        seat.add("A2");
                        numbNormal++;
                        seatType.add(1);
                        a2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A2");
                    numbNormal--;
                    a2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A3:
                strc = "A3";
                if (!seat.contains("A3")) {
                    if (seat.size() < 8) {
                        seat.add("A3");
                        numbNormal++;
                        a3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A3");
                    numbNormal--;
                    a3.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A4:
                strc = "A4";
                if (!seat.contains("A4")) {
                    if (seat.size() < 8) {
                        seat.add("A4");
                        numbNormal++;
                        a4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A4");
                    numbNormal--;
                    a4.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A5:
                strc = "A5";
                if (!seat.contains("A5")) {
                    if (seat.size() < 8) {
                        seat.add("A5");
                        numbNormal++;
                        a5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A5");
                    numbNormal--;
                    a5.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A6:
                strc = "A6";
                if (!seat.contains("A6")) {
                    if (seat.size() < 8) {
                        seat.add("A6");
                        numbNormal++;
                        a6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A6");
                    numbNormal--;
                    a6.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A7:
                strc = "A7";
                if (!seat.contains("A7")) {
                    if (seat.size() < 8) {
                        seat.add("A7");
                        a7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A7");
                    a7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.A8:
                strc = "A8";
                if (!seat.contains("A8")) {
                    if (seat.size() < 8) {
                        seat.add("A8");
                        numbNormal++;
                        a8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A8");
                    numbNormal--;
                    a8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B1:
                strc = "B1";
                if (!seat.contains("B1")) {
                    if (seat.size() < 8) {
                        seat.add("B1");
                        numbNormal++;
                        b1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B1");
                    numbNormal--;
                    b1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B2:
                strc = "B2";
                if (!seat.contains("B2")) {
                    if (seat.size() < 8) {
                        seat.add("B2");
                        numbNormal++;
                        b2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B2");
                    numbNormal--;
                    b2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B3:
                strc = "B3";
                if (!seat.contains("B3")) {
                    if (seat.size() < 8) {
                        seat.add("B3");
                        numbNormal++;
                        b3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B3");
                    numbNormal--;
                    b3.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B4:
                strc = "B4";
                if (!seat.contains("B4")) {
                    if (seat.size() < 8) {
                        seat.add("B4");
                        numbNormal++;
                        b4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B4");
                    numbNormal--;
                    b4.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B5:
                strc = "B5";
                if (!seat.contains("B5")) {
                    if (seat.size() < 8) {
                        seat.add("B5");
                        numbNormal++;
                        b5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B5");
                    numbNormal--;
                    b5.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B6:
                strc = "B6";
                if (!seat.contains("B6")) {
                    if (seat.size() < 8) {
                        seat.add("B6");
                        numbNormal++;
                        b6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B6");
                    numbNormal--;
                    b6.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B7:
                strc = "B7";
                if (!seat.contains("B7")) {
                    if (seat.size() < 8) {
                        seat.add("B7");
                        numbNormal++;
                        b7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B7");
                    numbNormal--;
                    b7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.B8:
                strc = "B8";
                if (!seat.contains("B8")) {
                    if (seat.size() < 8) {
                        seat.add("B8");
                        numbNormal++;
                        b8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B8");
                    numbNormal--;
                    b8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.C1:
                strc = "C1";
                if (!seat.contains("C1")) {
                    if (seat.size() < 8) {
                        seat.add("C1");
                        numbNormal++;
                        c1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C1");
                    numbNormal--;
                    c1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.C2:
                strc = "C2";
                if (!seat.contains("C2")) {
                    if (seat.size() < 8) {
                        seat.add("C2");
                        numbNormal++;
                        c2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C2");
                    numbNormal--;
                    c2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.C3:
                strc = "C3";
                if (!seat.contains("C3")) {
                    if (seat.size() < 8) {
                        seat.add("C3");
                        numbVip++;
                        c3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C3");
                    numbVip--;
                    c3.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.C4:
                strc = "C4";
                if (!seat.contains("C4")) {
                    if (seat.size() < 8) {
                        seat.add("C4");
                        numbVip++;
                        c4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C4");
                    numbVip--;
                    c4.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.C5:
                strc = "C5";
                if (!seat.contains("C5")) {
                    if (seat.size() < 8) {
                        seat.add("C5");
                        numbVip++;
                        c5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C5");
                    numbVip--;
                    c5.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.C6:
                strc = "C6";
                if (!seat.contains("C6")) {
                    if (seat.size() < 8) {
                        seat.add("C6");
                        numbVip++;
                        c6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C6");
                    numbVip--;
                    c6.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.C7:
                strc = "C7";
                if (!seat.contains("C7")) {
                    if (seat.size() < 8) {
                        seat.add("C7");
                        numbNormal++;
                        c7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C7");
                    numbNormal--;
                    c7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.C8:
                strc = "C8";
                if (!seat.contains("C8")) {
                    if (seat.size() < 8) {
                        seat.add("C8");
                        numbNormal++;
                        c8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C8");
                    numbNormal--;
                    c8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.D1:
                strc = "D1";
                if (!seat.contains("D1")) {
                    if (seat.size() < 8) {
                        seat.add("D1");
                        numbNormal++;
                        d1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D1");
                    numbNormal--;
                    d1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.D2:
                strc = "D2";
                if (!seat.contains("D2")) {
                    if (seat.size() < 8) {
                        seat.add("D2");
                        numbNormal++;
                        d2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D2");
                    numbNormal--;
                    d2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.D3:
                strc = "D3";
                if (!seat.contains("D3")) {
                    if (seat.size() < 8) {
                        seat.add("D3");
                        numbVip++;
                        d3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D3");
                    numbVip--;
                    d3.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.D4:
                strc = "D4";
                if (!seat.contains("D4")) {
                    if (seat.size() < 8) {
                        seat.add("D4");
                        numbVip++;
                        d4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D4");
                    numbVip--;
                    d4.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.D5:
                strc = "D5";
                if (!seat.contains("D5")) {
                    if (seat.size() < 8) {
                        seat.add("D5");
                        numbVip++;
                        d5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D5");
                    numbVip--;
                    d5.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.D6:
                strc = "D6";
                if (!seat.contains("D6")) {
                    if (seat.size() < 8) {
                        seat.add("D6");
                        numbVip++;
                        d6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D6");
                    numbVip--;
                    d6.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, data.getFormat());
                }
                break;
            case R.id.D7:
                strc = "D7";
                if (!seat.contains("D7")) {
                    if (seat.size() < 8) {
                        seat.add("D7");
                        numbNormal++;
                        d7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D7");
                    numbNormal--;
                    d7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.D8:
                strc = "D8";
                if (!seat.contains("D8")) {
                    if (seat.size() < 7) {
                        seat.add("D8");
                        numbNormal++;
                        d8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D8");
                    numbNormal--;
                    d8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, data.getFormat());
                }
                break;
            case R.id.S1:
                strc = "S1";
                if (!seat.contains("S1")) {
                    if (seat.size() < 7) {
                        seat.add("S1");
                        s1.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S2");
                        s2.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S1");
                    s1.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S2");
                    s2.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S2:
                strc = "S2";
                if (!seat.contains("S2")) {
                    if (seat.size() < 7) {
                        seat.add("S2");
                        s2.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S1");
                        s1.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S2");
                    s2.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S1");
                    s1.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S3:
                strc = "S3";
                if (!seat.contains("S3")) {
                    if (seat.size() < 7) {
                        seat.add("S3");
                        s3.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S4");
                        s4.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S3");
                    s3.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S4");
                    s4.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S4:
                strc = "S4";
                if (!seat.contains("S4")) {
                    if (seat.size() < 7) {
                        seat.add("S4");
                        s4.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S3");
                        s3.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S4");
                    s4.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S3");
                    s3.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S5:
                strc = "S5";
                if (!seat.contains("S5")) {
                    if (seat.size() < 7) {
                        seat.add("S5");
                        s5.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S6");
                        s6.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S5");
                    s5.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S6");
                    s6.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S6:
                strc = "S6";
                if (!seat.contains("S6")) {
                    if (seat.size() < 7) {
                        seat.add("S6");
                        s6.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S5");
                        s5.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S6");
                    s6.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S5");
                    s5.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S7:
                strc = "S7";
                if (!seat.contains("S7")) {
                    if (seat.size() < 7) {
                        seat.add("S7");
                        s7.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S8");
                        s8.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S7");
                    s7.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S8");
                    s8.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            case R.id.S8:
                strc = "S8";
                if (!seat.contains("S8")) {
                    if (seat.size() < 7) {
                        seat.add("S8");
                        s8.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("S7");
                        s7.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("S8");
                    s8.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("S7");
                    s7.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, data.getFormat());
                }
                break;
            default:
                break;
        }
    }

    public void getPrice(int typeOfChair, int TimeFrame, int typeOfMovie) {
        mSocket.emit("getPrice", typeOfMovie, TimeFrame, typeOfChair);
    }

    public void getListPrice(int typeOfChair, int TimeFrame, int typeOfMovie) {
        mSocket.emit("getListPrice", typeOfMovie, TimeFrame, typeOfChair);
    }

    public void getNameChair(int IdMovie, int TimeFrame, String date) {
        mSocket.emit("getOneTicketInfo", IdMovie, TimeFrame, date);
    }
}

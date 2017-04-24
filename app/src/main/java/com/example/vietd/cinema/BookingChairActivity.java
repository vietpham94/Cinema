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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Optimus on 4/12/2017.
 */

public class BookingChairActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView numbchair;
    Config config = new Config();
    private int[] idbtn = {R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8, R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8, R.id.C1, R.id.C2, R.id.C3, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8, R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8, R.id.S1, R.id.S2, R.id.S3, R.id.S4, R.id.S5, R.id.S6, R.id.S7, R.id.S8};
    private String[] btnArray = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8"};
    private Button A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, C1, C2, C3, C4, C5, C6, C7, C8, D1, D2, D3, D4, D5, D6, D7, D8, E1, E2, E3, E4, E5, E6, E7, E8, next;
    private String weekDay;
    private ArrayList<String> seat = new ArrayList<String>();
    private ArrayList<Integer> seatType = new ArrayList<Integer>();
    private int numbNormal = 0, numbVip = 0, numbCouple = 0, typeOfDay;
    private TextView totalMoney;
    private Socket mSocket = config.mSocket;
    private int id_time;
    private JSONArray ticketinfoList;
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
                            switch (strc) {
                                case "E1":
                                    total = tg + 2 * price;
                                    break;
                                case "E2":
                                    total = tg + 2 * price;
                                    break;
                                case "E3":
                                    total = tg + 2 * price;
                                    break;
                                case "E4":
                                    total = tg + 2 * price;
                                    break;
                                case "E5":
                                    total = tg + 2 * price;
                                    break;
                                case "E6":
                                    total = tg + 2 * price;
                                    break;
                                case "E7":
                                    total = tg + 2 * price;
                                    break;
                                case "E8":
                                    total = tg + 2 * price;
                                    break;
                                default:
                                    total = tg + price;
                                    break;
                            }
                        } else {
                            switch (strc) {
                                case "E1":
                                    total = tg - 2 * price;
                                    break;
                                case "E2":
                                    total = tg - 2 * price;
                                    break;
                                case "E3":
                                    total = tg - 2 * price;
                                    break;
                                case "E4":
                                    total = tg - 2 * price;
                                    break;
                                case "E5":
                                    total = tg - 2 * price;
                                    break;
                                case "E6":
                                    total = tg - 2 * price;
                                    break;
                                case "E7":
                                    total = tg - 2 * price;
                                    break;
                                case "E8":
                                    total = tg - 2 * price;
                                    break;
                                default:
                                    total = tg - price;
                                    break;
                            }
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

    private Emitter.Listener resultOccupiedSeat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        ArrayList<String> Occupied = new ArrayList<String>();
                        ticketinfoList = data.getJSONArray("ticketinfoList");
                        String chair = ticketinfoList.getJSONObject(0).getString("namechair");

                        for (int i = 0; i < ticketinfoList.length(); i++) {
                            for(int j = 0; j < btnArray.length; j++){
                                if(btnArray[j].contains(ticketinfoList.getJSONObject(i).getString("namechair"))){
                                    findViewById(idbtn[j]).setEnabled(false);
                                    findViewById(idbtn[j]).setBackgroundResource(R.drawable.chairoccupied);
                                }
                            }

                        }
                        for (int j = 0; j < idbtn.length; j++){
                            findViewById(idbtn[j]).setOnClickListener(BookingChairActivity.this);
                        }


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

        A1 = (Button) findViewById(R.id.A1);
        A2 = (Button) findViewById(R.id.A2);
        A3 = (Button) findViewById(R.id.A3);
        A4 = (Button) findViewById(R.id.A4);
        A5 = (Button) findViewById(R.id.A5);
        A6 = (Button) findViewById(R.id.A6);
        A7 = (Button) findViewById(R.id.A7);
        A8 = (Button) findViewById(R.id.A8);
        B1 = (Button) findViewById(R.id.B1);
        B2 = (Button) findViewById(R.id.B2);
        B3 = (Button) findViewById(R.id.B3);
        B4 = (Button) findViewById(R.id.B4);
        B5 = (Button) findViewById(R.id.B5);
        B6 = (Button) findViewById(R.id.B6);
        B7 = (Button) findViewById(R.id.B7);
        B8 = (Button) findViewById(R.id.B8);
        C1 = (Button) findViewById(R.id.C1);
        C2 = (Button) findViewById(R.id.C2);
        C3 = (Button) findViewById(R.id.C3);
        C4 = (Button) findViewById(R.id.C4);
        C5 = (Button) findViewById(R.id.C5);
        C6 = (Button) findViewById(R.id.C6);
        C7 = (Button) findViewById(R.id.C7);
        C8 = (Button) findViewById(R.id.C8);
        D1 = (Button) findViewById(R.id.D1);
        D2 = (Button) findViewById(R.id.D2);
        D3 = (Button) findViewById(R.id.D3);
        D4 = (Button) findViewById(R.id.D4);
        D5 = (Button) findViewById(R.id.D5);
        D6 = (Button) findViewById(R.id.D6);
        D7 = (Button) findViewById(R.id.D7);
        D8 = (Button) findViewById(R.id.D8);
        E1 = (Button) findViewById(R.id.S1);
        E2 = (Button) findViewById(R.id.S2);
        E3 = (Button) findViewById(R.id.S3);
        E4 = (Button) findViewById(R.id.S4);
        E5 = (Button) findViewById(R.id.S5);
        E6 = (Button) findViewById(R.id.S6);
        E7 = (Button) findViewById(R.id.S7);
        E8 = (Button) findViewById(R.id.S8);
        numbchair = (TextView) findViewById(R.id.numbTicket);
        next = (Button) findViewById(R.id.button_next);

        A1.setOnClickListener(this);
        A2.setOnClickListener(this);
        A3.setOnClickListener(this);
        A4.setOnClickListener(this);
        A5.setOnClickListener(this);
        A6.setOnClickListener(this);
        A7.setOnClickListener(this);
        A8.setOnClickListener(this);
        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);
        B5.setOnClickListener(this);
        B6.setOnClickListener(this);
        B7.setOnClickListener(this);
        B8.setOnClickListener(this);
        C1.setOnClickListener(this);
        C2.setOnClickListener(this);
        C3.setOnClickListener(this);
        C4.setOnClickListener(this);
        C5.setOnClickListener(this);
        C6.setOnClickListener(this);
        C7.setOnClickListener(this);
        C8.setOnClickListener(this);
        D1.setOnClickListener(this);
        D2.setOnClickListener(this);
        D3.setOnClickListener(this);
        D4.setOnClickListener(this);
        D5.setOnClickListener(this);
        D6.setOnClickListener(this);
        D7.setOnClickListener(this);
        D8.setOnClickListener(this);
        E1.setOnClickListener(this);
        E2.setOnClickListener(this);
        E3.setOnClickListener(this);
        E4.setOnClickListener(this);
        E5.setOnClickListener(this);
        E6.setOnClickListener(this);
        E7.setOnClickListener(this);
        E8.setOnClickListener(this);


        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        switch (String.valueOf(weekDay)) {
            case "Tuesday":
                typeOfDay = 1;
                break;
            case "Monday":
                typeOfDay = 1;
                break;
            case "Wednesday":
                typeOfDay = 1;
                break;
            case "Thursday":
                typeOfDay = 1;
                break;
            case "Friday":
                typeOfDay = 1;
                break;
            case "Saturday":
                typeOfDay = 2;
                break;
            case "Sunday":
                typeOfDay = 2;
                break;
        }


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

        getListPrice(1, timeFrame, typeOfDay, data.getFormat());
        getListPrice(2, timeFrame, typeOfDay, data.getFormat());
        getListPrice(3, timeFrame, typeOfDay, data.getFormat());

        mSocket.on("ListpriceOfSeat", ListpriceOfSeat);
        mSocket.emit("getOccupiedSeat", date, room, id_time);
        mSocket.on("resultOccupiedSeat", resultOccupiedSeat);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numbCouple == 0 && numbNormal == 0 && numbVip == 0) {
                    Toast.makeText(BookingChairActivity.this, "Bạn chưa chọn ghế nào!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(BookingChairActivity.this, ReceiptActivity.class);
                    Bundle bn = new Bundle();
                    bn.putSerializable("data", data);
                    bn.putInt("id_time", id_time);
                    bn.putString("date", date);
                    bn.putStringArrayList("seat", seat);
                    bn.putInt("room", room);
                    bn.putInt("typeOfDay", typeOfDay);
                    bn.putInt("typeOfMovie", data.getFormat());
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
                        A1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A1");
                    numbNormal--;
                    A1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A2:
                strc = "A2";
                if (!seat.contains("A2")) {
                    if (seat.size() < 8) {
                        seat.add("A2");
                        numbNormal++;
                        seatType.add(1);
                        A2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A2");
                    numbNormal--;
                    A2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A3:
                strc = "A3";
                if (!seat.contains("A3")) {
                    if (seat.size() < 8) {
                        seat.add("A3");
                        numbNormal++;
                        A3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A3");
                    numbNormal--;
                    A3.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A4:
                strc = "A4";
                if (!seat.contains("A4")) {
                    if (seat.size() < 8) {
                        seat.add("A4");
                        numbNormal++;
                        A4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A4");
                    numbNormal--;
                    A4.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A5:
                strc = "A5";
                if (!seat.contains("A5")) {
                    if (seat.size() < 8) {
                        seat.add("A5");
                        numbNormal++;
                        A5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A5");
                    numbNormal--;
                    A5.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A6:
                strc = "A6";
                if (!seat.contains("A6")) {
                    if (seat.size() < 8) {
                        seat.add("A6");
                        numbNormal++;
                        A6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A6");
                    numbNormal--;
                    A6.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A7:
                strc = "A7";
                if (!seat.contains("A7")) {
                    if (seat.size() < 8) {
                        seat.add("A7");
                        A7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A7");
                    A7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.A8:
                strc = "A8";
                if (!seat.contains("A8")) {
                    if (seat.size() < 8) {
                        seat.add("A8");
                        numbNormal++;
                        A8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("A8");
                    numbNormal--;
                    A8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B1:
                strc = "B1";
                if (!seat.contains("B1")) {
                    if (seat.size() < 8) {
                        seat.add("B1");
                        numbNormal++;
                        B1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B1");
                    numbNormal--;
                    B1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B2:
                strc = "B2";
                if (!seat.contains("B2")) {
                    if (seat.size() < 8) {
                        seat.add("B2");
                        numbNormal++;
                        B2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B2");
                    numbNormal--;
                    B2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B3:
                strc = "B3";
                if (!seat.contains("B3")) {
                    if (seat.size() < 8) {
                        seat.add("B3");
                        numbNormal++;
                        B3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B3");
                    numbNormal--;
                    B3.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B4:
                strc = "B4";
                if (!seat.contains("B4")) {
                    if (seat.size() < 8) {
                        seat.add("B4");
                        numbNormal++;
                        B4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B4");
                    numbNormal--;
                    B4.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B5:
                strc = "B5";
                if (!seat.contains("B5")) {
                    if (seat.size() < 8) {
                        seat.add("B5");
                        numbNormal++;
                        B5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B5");
                    numbNormal--;
                    B5.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B6:
                strc = "B6";
                if (!seat.contains("B6")) {
                    if (seat.size() < 8) {
                        seat.add("B6");
                        numbNormal++;
                        B6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B6");
                    numbNormal--;
                    B6.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B7:
                strc = "B7";
                if (!seat.contains("B7")) {
                    if (seat.size() < 8) {
                        seat.add("B7");
                        numbNormal++;
                        B7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B7");
                    numbNormal--;
                    B7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.B8:
                strc = "B8";
                if (!seat.contains("B8")) {
                    if (seat.size() < 8) {
                        seat.add("B8");
                        numbNormal++;
                        B8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("B8");
                    numbNormal--;
                    B8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C1:
                strc = "C1";
                if (!seat.contains("C1")) {
                    if (seat.size() < 8) {
                        seat.add("C1");
                        numbNormal++;
                        C1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C1");
                    numbNormal--;
                    C1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C2:
                strc = "C2";
                if (!seat.contains("C2")) {
                    if (seat.size() < 8) {
                        seat.add("C2");
                        numbNormal++;
                        C2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C2");
                    numbNormal--;
                    C2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C3:
                strc = "C3";
                if (!seat.contains("C3")) {
                    if (seat.size() < 8) {
                        seat.add("C3");
                        numbVip++;
                        C3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C3");
                    numbVip--;
                    C3.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C4:
                strc = "C4";
                if (!seat.contains("C4")) {
                    if (seat.size() < 8) {
                        seat.add("C4");
                        numbVip++;
                        C4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C4");
                    numbVip--;
                    C4.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C5:
                strc = "C5";
                if (!seat.contains("C5")) {
                    if (seat.size() < 8) {
                        seat.add("C5");
                        numbVip++;
                        C5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C5");
                    numbVip--;
                    C5.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C6:
                strc = "C6";
                if (!seat.contains("C6")) {
                    if (seat.size() < 8) {
                        seat.add("C6");
                        numbVip++;
                        C6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C6");
                    numbVip--;
                    C6.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C7:
                strc = "C7";
                if (!seat.contains("C7")) {
                    if (seat.size() < 8) {
                        seat.add("C7");
                        numbNormal++;
                        C7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C7");
                    numbNormal--;
                    C7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.C8:
                strc = "C8";
                if (!seat.contains("C8")) {
                    if (seat.size() < 8) {
                        seat.add("C8");
                        numbNormal++;
                        C8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("C8");
                    numbNormal--;
                    C8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D1:
                strc = "D1";
                if (!seat.contains("D1")) {
                    if (seat.size() < 8) {
                        seat.add("D1");
                        numbNormal++;
                        D1.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D1");
                    numbNormal--;
                    D1.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D2:
                strc = "D2";
                if (!seat.contains("D2")) {
                    if (seat.size() < 8) {
                        seat.add("D2");
                        numbNormal++;
                        D2.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D2");
                    numbNormal--;
                    D2.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D3:
                strc = "D3";
                if (!seat.contains("D3")) {
                    if (seat.size() < 8) {
                        seat.add("D3");
                        numbVip++;
                        D3.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D3");
                    numbVip--;
                    D3.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D4:
                strc = "D4";
                if (!seat.contains("D4")) {
                    if (seat.size() < 8) {
                        seat.add("D4");
                        numbVip++;
                        D4.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D4");
                    numbVip--;
                    D4.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D5:
                strc = "D5";
                if (!seat.contains("D5")) {
                    if (seat.size() < 8) {
                        seat.add("D5");
                        numbVip++;
                        D5.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D5");
                    numbVip--;
                    D5.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D6:
                strc = "D6";
                if (!seat.contains("D6")) {
                    if (seat.size() < 8) {
                        seat.add("D6");
                        numbVip++;
                        D6.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(2, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D6");
                    numbVip--;
                    D6.setBackgroundResource(R.drawable.chairs);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(2, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D7:
                strc = "D7";
                if (!seat.contains("D7")) {
                    if (seat.size() < 8) {
                        seat.add("D7");
                        numbNormal++;
                        D7.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D7");
                    numbNormal--;
                    D7.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.D8:
                strc = "D8";
                if (!seat.contains("D8")) {
                    if (seat.size() < 7) {
                        seat.add("D8");
                        numbNormal++;
                        D8.setBackgroundResource(R.drawable.chairchoosing);
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(1, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("D8");
                    numbNormal--;
                    D8.setBackgroundResource(R.drawable.chairnormal);
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(1, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S1:
                strc = "E1";
                if (!seat.contains("E1")) {
                    if (seat.size() < 7) {
                        seat.add("E1");
                        E1.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E2");
                        E2.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E1");
                    E1.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E2");
                    E2.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S2:
                strc = "E2";
                if (!seat.contains("E2")) {
                    if (seat.size() < 7) {
                        seat.add("E2");
                        E2.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E1");
                        E1.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E2");
                    E2.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E1");
                    E1.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S3:
                strc = "E3";
                if (!seat.contains("E3")) {
                    if (seat.size() < 7) {
                        seat.add("E3");
                        E3.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E4");
                        E4.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E3");
                    E3.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E4");
                    E4.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S4:
                strc = "E4";
                if (!seat.contains("E4")) {
                    if (seat.size() < 7) {
                        seat.add("E4");
                        E4.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E3");
                        E3.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E4");
                    E4.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E3");
                    E3.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S5:
                strc = "E5";
                if (!seat.contains("E5")) {
                    if (seat.size() < 7) {
                        seat.add("E5");
                        E5.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E6");
                        E6.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E5");
                    E5.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E6");
                    E6.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S6:
                strc = "E6";
                if (!seat.contains("E6")) {
                    if (seat.size() < 7) {
                        seat.add("E6");
                        E6.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E5");
                        E5.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E6");
                    E6.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E5");
                    E5.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S7:
                strc = "E7";
                if (!seat.contains("E7")) {
                    if (seat.size() < 7) {
                        seat.add("E7");
                        E7.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E8");
                        E8.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E7");
                    E7.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E8");
                    E8.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            case R.id.S8:
                strc = "E8";
                if (!seat.contains("E8")) {
                    if (seat.size() < 7) {
                        seat.add("E8");
                        E8.setBackgroundResource(R.drawable.chairchoosing);
                        seat.add("E7");
                        E7.setBackgroundResource(R.drawable.chairchoosing);
                        numbCouple += 2;
                        numbchair.setText("" + seat.size() + "");
                        int timeFrame = 1;
                        if (id_time >= 4) {
                            timeFrame = 2;
                        }
                        getPrice(3, timeFrame, typeOfDay, data.getFormat());
                    } else {
                        Toast.makeText(this, "Bạn chỉ được chọn tối đa 8 ghế", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    seat.remove("E8");
                    E8.setBackgroundResource(R.drawable.chairsweet);
                    seat.remove("E7");
                    E7.setBackgroundResource(R.drawable.chairsweet);
                    numbCouple -= 2;
                    numbchair.setText("" + seat.size() + "");
                    int timeFrame = 1;
                    if (id_time >= 4) {
                        timeFrame = 2;
                    }
                    getPrice(3, timeFrame, typeOfDay, data.getFormat());
                }
                break;
            default:
                break;
        }
    }

    public void getPrice(int typeOfChair, int TimeFrame, int TypeOfDay, int typeOfMovie) {
        mSocket.emit("getPrice", typeOfMovie, TimeFrame, TypeOfDay, typeOfChair);
    }

    public void getListPrice(int typeOfChair, int TimeFrame, int TypeOfDay, int typeOfMovie) {
        mSocket.emit("getListPrice", typeOfMovie, TimeFrame, TypeOfDay, typeOfChair);
    }

    public void getNameChair(int IdMovie, int TimeFrame, String date) {
        mSocket.emit("getOneTicketInfo", IdMovie, TimeFrame, date);
    }
}

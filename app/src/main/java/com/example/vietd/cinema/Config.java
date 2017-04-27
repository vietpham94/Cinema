package com.example.vietd.cinema;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Optimus on 4/19/2017.
 */

public class Config {

    public String link = "http://192.168.0.30:3000/";
    public Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.30:3000/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

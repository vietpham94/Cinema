package com.example.vietd.cinema;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Optimus on 4/19/2017.
 */

public class Config {

    //public String link = "http://192.168.43.85:3000/";
    public String link = "http://192.168.1.8:3000/";
    public Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.8:3000/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

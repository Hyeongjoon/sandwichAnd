package com.example.admin.sandwich;

import android.app.Activity;
import android.os.Bundle;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/*
 * Created by admin on 2016-04-28.
 */

public class SocketIOActivity extends Activity {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.25:3001");
        } catch (URISyntaxException e) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();
        mSocket.emit("loginChk", "뿌잉뿌잉");
    }
}

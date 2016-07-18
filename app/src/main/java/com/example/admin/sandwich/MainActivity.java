package com.example.admin.sandwich;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;



public class MainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    Handler mHandler = null;
    AlertDialog dialog;

/*    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.43:3001");
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }*/
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

   private Emitter.Listener resultLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject data = (JSONObject) args[0];
            String result;
            try{
                result = data.getString("result");
            } catch (JSONException e){
                System.out.println(e);
                return;
            }
            if(result.equals("true")){
                System.out.println("true");
                pDialog.cancel();
            } else{
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        mHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                // 알림창 띄우기
                                pDialog.cancel();
                                dialog.show();
                            }
                        });
                    }
                });
                t.start();
            }
        }
    };

    /*private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("로그인 오류")        // 제목 설정
                .setMessage("아이디나 비밀번호가 잘못됐습니다.")        // 메세지 설정
                .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        mHandler = new Handler();
        dialog = builder.create();    // 알림창 객체 생성
        // 알림창 띄우기
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //createDialog();
        /*mSocket.on("resultLogin" , resultLogin);
        mSocket.connect();*/
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void MainButtonClick(View v){
        switch(v.getId()) {
            case R.id.signIn_login: {
                EditText emailText = (EditText)findViewById(R.id.LoginEmail);
                EditText passwordText = (EditText)findViewById(R.id.LoginPassword);
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext() , "invalid email" ,Toast.LENGTH_SHORT).show();
                } else if(password.length()==0){
                    Toast.makeText(getApplicationContext() , "password is empty" ,Toast.LENGTH_SHORT).show();
                } else{
                    JSONObject inform = new JSONObject();
                    try{
                        inform.put("email" , email);
                        inform.put("password" , password);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                    //        mSocket.emit("loginChk", inform);
                    pDialog = ProgressDialog.show(this, "Login.....", "Please wait", true, true);

            }
        } case R.id.signIn_signUp : {
                Intent temp = new Intent();
                Intent intent = new Intent(getApplicationContext() , SignUpActivity.class);
                startActivity(intent);
                return;
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.sandwich/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.sandwich/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

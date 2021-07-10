package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyIncomingHandler incomingHandler = new MyIncomingHandler();
    boolean isBoundIO = false;
    Messenger requestForIO;
    Messenger replyFromIO = new Messenger(incomingHandler);

    ServiceConnection connection = new MyServiceConnection();

    // Overview:
    // This activity is the point of entry for the app. When it is loaded, it initializes the app,
    // binds the IO service (which starts the underlying logic for the rest of the app), and
    // displays the title to the user, as well as two buttons, asking whether the user wants to go to goals,
    // or go to the schedule

    // OnCreate needs to:
    // 1.) Bind io Service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start IO service
        //Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        //startService(intentIO);
    }

    public void messageToIO(){
        if(isBoundIO)
        {
            // Message structure:
            // [<key>               |<value>                    ]
            // [source              |calling activity           ]
            // [request             |request code               ]
            //
            // Request codes:
            // 100 - Request for acknowledgement
            // 101 - Request to read data
            // 102 - Request to write data
            // 103 - Request for update to AppWebConnect
            // 104 - Request to update notifications

            Bundle data = new Bundle();
            data.putString("source", "MainActivity");
            data.putInt("request", 100);

            Message message = Message.obtain();
            message.replyTo = replyFromIO;
            message.setData(data);
            try {
                requestForIO.send(message);
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT);
        }
    }

    protected boolean bindIO(){
        Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        return bindService(intentIO, connection, Context.BIND_AUTO_CREATE);

    }

    class MyIncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            // Do some stuff with the variables and the reply codes
        }
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            requestForIO = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            requestForIO = null;
        }
    }
    // To do:
    //  1.) User Data function
    //  2.) Check IO Service state
    //  3.) onClick for Goals button starts app in Goals mode
    //  4.) onClick for schedule mode starts app in Schedules mode, reads from IO base settings, and starts default screen
}
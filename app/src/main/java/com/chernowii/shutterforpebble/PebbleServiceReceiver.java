package com.chernowii.shutterforpebble;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.getpebble.android.kit.Constants;
import com.getpebble.android.kit.PebbleKit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.prefs.Preferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by konrad on 8/29/16.
 */
public class PebbleServiceReceiver extends WakefulBroadcastReceiver
{
    private static final String TAG = PebbleServiceReceiver.class.getSimpleName();

    public void takePictureDown(Context c){
        String x_coord = "0";
        String y_coord = "0";
        SharedPreferences prefs = c.getSharedPreferences("PREFS", MODE_PRIVATE);
        x_coord = prefs.getString("xcoord", "600");
        y_coord = prefs.getString("ycoord", "600");

        Toast.makeText(c, "picture shot!", Toast.LENGTH_SHORT).show();
        String command = "input tap " + x_coord + " " + y_coord + "\n";
        try{
        Process p = null;
        p = Runtime.getRuntime().exec("su");
        OutputStream os = p.getOutputStream();
        os.write(command.getBytes());
        os.flush();
    } catch (IOException e) {
    e.printStackTrace();
}
    }
    public void onReceive(final Context context, final Intent intent )
    {
        if( Constants.INTENT_APP_RECEIVE.equals( intent.getAction() ) )
        {
            Log.i( TAG, "Received messaged from Pebble App." );
            UUID puuid = UUID.fromString("0667d489-5dd3-4a0a-a078-661460d9370e");
            final UUID receivedUuid = (UUID) intent.getSerializableExtra( Constants.APP_UUID );
            if( !puuid.equals( receivedUuid ) )
            {
                Log.i( TAG, "not my UUID" );
                return;
            }

            final int transactionId = intent.getIntExtra( Constants.TRANSACTION_ID, -1 );
            final String jsonData = intent.getStringExtra( Constants.MSG_DATA );
            if( jsonData == null || jsonData.isEmpty() )
            {
                Log.w( TAG, "jsonData null" );
                PebbleKit.sendNackToPebble( context, transactionId );
                return;
            }

            Log.w( TAG, "Sending ACK to Pebble. " + jsonData );
            PebbleKit.sendAckToPebble( context, transactionId );
            takePictureDown(context);

        }
    }
}
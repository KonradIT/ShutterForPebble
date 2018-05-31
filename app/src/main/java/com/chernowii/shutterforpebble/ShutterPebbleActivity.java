package com.chernowii.shutterforpebble;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.chernowii.shutterforpebble.R;
import java.util.UUID;

public class ShutterPebbleActivity extends AppCompatActivity {
    private PebbleKit.PebbleDataReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shutter_pebble);
        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        final EditText YCoords = (EditText)findViewById(R.id.y_coord);
        final EditText XCoords = (EditText)findViewById(R.id.x_coord);
        String x_coord = prefs.getString("xcoord", "X Coordinate");
        String y_coord = prefs.getString("ycoord", "Y Coordinate");
        YCoords.setHint(y_coord);
        XCoords.setHint(x_coord);
        Button setCoords = (Button)findViewById(R.id.setcoords);
        setCoords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("xcoord", XCoords.getText().toString());
                editor.putString("ycoord", YCoords.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Coordinates set!", Toast.LENGTH_SHORT).show();

            }
        });



    }


}




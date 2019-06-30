package gw.example.drone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;

public class DroneSettings extends Activity {
    String speed, altitude;
    EditText maxSpeed, maxAltitude;
    TextView maxSpeedDisplay, maxAltitudeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_settings);
        maxSpeed = findViewById(R.id.editmaxSpeed);
        maxAltitude = findViewById(R.id.editmaxAltitude);
        maxSpeedDisplay = findViewById(R.id.tv_Speed);
        maxAltitudeDisplay = findViewById(R.id.tv_Altitude);
    }

    public void saveSettings_onClick(View v){
        SharedPreferences sharedpreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        speed = maxSpeed.getText().toString();
        altitude = maxAltitude.getText().toString();
        maxSpeedDisplay.setText(speed);
        maxAltitudeDisplay.setText(altitude);

        int speedValue;
        int altValue;
        try {
            speedValue = Integer.parseInt(speed);
            altValue = Integer.parseInt(altitude);
        }
        catch (NumberFormatException e)
        {
            speedValue = 0;
            altValue = 0;
        }

        editor.putInt("droneSpeed", speedValue);
        editor.putInt("droneAltitude", altValue);
        editor.commit();

    }

}

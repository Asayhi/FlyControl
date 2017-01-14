package gw.example.drone;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

public class MainActivity extends Activity {

    IARDrone drone;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        final TextView text = (TextView) findViewById(R.id.textView);
        text.append("\nConnected to " + wifi.getConnectionInfo().getSSID());

        drone = new ARDrone();
        drone.start();

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drone.takeOff();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}

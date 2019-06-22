package gw.example.drone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.widget.TextView;


import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.navdata.*;



public class MainActivity extends Activity implements SensorEventListener{

    IARDrone drone;


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private boolean resetFlag = false;
    private boolean initFlag = false;

    enum dStatus{fliegend,stehend};
    dStatus dstatus;
    Button b;
    Button up;
    Button down;
    Button left;
    Button right;
    TextView viewStatus;


    /**
     * Called when activity is created
     * <p>
     *     Initailizes the sensorManager aswell as the Accelrometer
     * </p>
     * @param savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        drone  = new dummyDrone();
        drone.start();

        viewStatus = (TextView) findViewById(R.id.viewStatus);

        dstatus = dStatus.stehend;

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                if(dstatus == dStatus.stehend) {

                    drone.takeOff();
                    dstatus = dStatus.fliegend;
                    viewStatus.setText("Flying");
                    b.setText("Landing");
                    initFlag = true;
                }
                else{
                    drone.landing();
                    viewStatus.setText("Landing");
                    dstatus = dStatus.stehend;
                    b.setText("Take Off");
                    initFlag = false;
                }
            }
        });

        up = (Button) findViewById(R.id.btn_up);
        up.setOnTouchListener(new View.OnTouchListener(){
            /**
             *
             * @param v
             * @param event
             * @return
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.up();
                        viewStatus.setText("Ascending");
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText("Hovering");
                        break;
                }
                return true;
            }
        });

        down = (Button) findViewById(R.id.btn_down);
        down.setOnTouchListener(new View.OnTouchListener(){
            /**
             *
             * @param v
             * @param event
             * @return
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        drone.down();
                        viewStatus.setText("Descending");
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText("Hovering");
                        break;
                }
                return true;
            }
        });

        left = (Button) findViewById(R.id.btn_left);
        left.setOnTouchListener(new View.OnTouchListener() {
            /**
             *
             * @param view
             * @param event
             * @return
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.spinLeft();
                        viewStatus.setText("Spinning Left");
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText("Hovering");
                        break;
                }
                return true;
            }
        });

        right = (Button) findViewById(R.id.btn_right);
        right.setOnTouchListener(new View.OnTouchListener() {
            /**
             *
             * @param view
             * @param event
             * @return
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.spinRight();
                        viewStatus.setText("Spinning Right");
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText("Hovering");
                        break;
                }
                return true;
            }
        });


    }

    /**
     * Methode that gets called when the sensor receives a new set of values
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100){
                lastUpdate = curTime;

                if (initFlag) {

                    if (x < 8.5 && x > 0) {       /**detect rotation around horizontal */
                        if (z > 1) {
                            drone.forward();
                            viewStatus.setText("Forwards");
                            resetFlag = true;
                        }
                        if (z < -1) {
                            drone.backward();
                            viewStatus.setText("Backwards");
                            resetFlag = true;
                        }
                    } else if (y < -1) {
                        drone.goLeft();
                        viewStatus.setText("Going Left");
                        resetFlag = true;
                    } else if (y > 1) {
                        drone.goRight();
                        viewStatus.setText("Going Right");
                        resetFlag = true;
                    } else {
                        if (resetFlag) {
                            drone.hover();
                            viewStatus.setText("Hovering");
                            resetFlag = false;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Method that gets called on pause of the activity
     * It unregisters the listener of the sensor manager
     */
    protected  void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

}

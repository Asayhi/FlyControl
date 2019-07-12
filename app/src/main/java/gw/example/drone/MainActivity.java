package gw.example.drone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
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

import gw.example.drone.Accelerometer;


/**
 * Class that implements the main part of the user interface and handles the setup of all the
 * important objects. It handles all the onClick and onTouch events of the main UI and also creates
 * the option bar with the s
 * @author Amesberger
 * @version 0.2.4
 */
public class MainActivity extends Activity{

    IARDrone drone;

    Accelerometer senAccelerometer;

    enum dStatus{fliegend,stehend};
    dStatus dstatus;
    Button inital;
    Button up;
    Button down;
    Button left;
    Button right;
    TextView viewStatus;

    /**
     * Called when activity is created
     * <p>
     * Creates the Accelerometer object
     *
     *
     * @param savedInstanceState A saved state of the instance
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senAccelerometer = new Accelerometer();
    }

    /**
     * Called on resume of the activity
     * <p>
     *     Initializes the startup sequence of the drone.
     * </p>
     *
     * <p>
     *     Initializes the onTouch and onClick methods for the buttons
     * </p>
     */
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_main);
        drone  = new ARDrone();
        drone.start();

        viewStatus = (TextView) findViewById(R.id.viewStatus);

        dstatus = dStatus.stehend;

        up = (Button) findViewById(R.id.btn_up);
        up.setOnTouchListener(new View.OnTouchListener(){
            /**
             * Method that implements an onTouchListener to enable the user to
             * let the drone ascend.
             * A listener awaits a touch event.
             * On ACTION_DOWN the up-function of the drone is called
             * On ACTION_UP the hover-function of the drone is called
             * @param v View
             * @param event MotionEvent
             * @return bool to check on the method
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.up();
                        viewStatus.setText(R.string.tv_Ascending);
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText(R.string.tv_Hovering);
                        break;
                }
                return true;
            }
        });

        down = (Button) findViewById(R.id.btn_down);
        down.setOnTouchListener(new View.OnTouchListener(){
            /**
             * Method that implements an onTouchListener to enable the user to
             * let the drone descend.
             * A listener awaits a touch event.
             * On ACTION_DOWN the down-function of the drone is called
             * On ACTION_UP the hover-function of the drone is called
             * @param v View
             * @param event MotionEvent
             * @return
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        drone.down();
                        viewStatus.setText(R.string.tv_Descending);
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText(R.string.tv_Hovering);
                        break;
                }
                return true;
            }
        });

        left = (Button) findViewById(R.id.btn_left);
        left.setOnTouchListener(new View.OnTouchListener() {
            /**
             * Method that implements an onTouchListener to enable the user to
             * rotate the drone counter clock wise.
             * A listener awaits a touch event.
             * On ACTION_DOWN the spinLeft-function of the drone is called
             * On ACTION_UP the hover-function of the drone is called
             * @param view View
             * @param event MotionEvent
             * @return
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.spinLeft();
                        viewStatus.setText(R.string.tv_spinLeft);
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText(R.string.tv_Hovering);
                        break;
                }
                return true;
            }
        });

        right = (Button) findViewById(R.id.btn_right);
        right.setOnTouchListener(new View.OnTouchListener() {
            /**
             * Method that implements an onTouchListener to enable the user to
             * rotate the drone clock wise.
             * A listener awaits a touch event.
             * On ACTION_DOWN the spinRight-function of the drone is called
             * On ACTION_UP the hover-function of the drone is called
             * @param view View
             * @param event View
             * @return
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        drone.spinRight();
                        viewStatus.setText(R.string.tv_spinRight);
                        break;
                    case MotionEvent.ACTION_UP:
                        drone.hover();
                        viewStatus.setText(R.string.tv_Hovering);
                        break;
                }
                return true;
            }
        });


    }


    /**
     * Method that awaits a click event and calls, depending on the current status of the drone
     * the function for take off or the function for landing.
     * <p>
     *     It also sets the Text of the TextView viewStatus to the new State
     *     and enables (when the drone takes off) or disables (when the drone lands) the usage of the
     *     accelerometer for direction control
     * </p>
     * @param v View
     */

    public void btnInitial_onClick (View v) {
        inital = (Button) findViewById(R.id.btn_initial);
        if(dstatus == dStatus.stehend) {

            drone.takeOff();
            dstatus = dStatus.fliegend;
            viewStatus.setText(R.string.tv_Flying);
            inital.setText(R.string.btn_Landing);
            senAccelerometer.initSenControl(MainActivity.this, drone, viewStatus);
        }
        else{
            drone.landing();
            viewStatus.setText(R.string.tv_Landing);
            dstatus = dStatus.stehend;
            inital.setText(R.string.btn_Initial);
            senAccelerometer.disableSenControl();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}

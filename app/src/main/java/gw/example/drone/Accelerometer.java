package gw.example.drone;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.navdata.*;

/**
 *
 */
public class Accelerometer implements SensorEventListener {

    private IARDrone drone;
    private TextView viewStatus;
    private Context accelContext;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private boolean initFlag;
    private boolean resetFlag;
    private double accel_zBackward;
    private double accel_zForward;
    private double accel_x1;
    private double accel_x2;
    private double accel_yLeft;
    private double accel_yRight;


    /**
     *
     */
    public Accelerometer(){
        lastUpdate = 0;
        initFlag = false;
        resetFlag = false;
        accel_zBackward = -2.5;
        accel_zForward = 2.5;
        accel_x1 = 8.5;
        accel_x2 = 0;
        accel_yLeft = -2;
        accel_yRight = 2;


    }


    /**
     *
     * @param senContext
     * @param foreignDrone
     * @param status
     */
    public void initSenControl(Context senContext, IARDrone foreignDrone, TextView status){
        accelContext = senContext;
        senSensorManager = (SensorManager) accelContext.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        initFlag = true;
        drone = foreignDrone;
        viewStatus = status;

    }

    /**
     *
     */
    public void disableSenControl() {
        initFlag = false;
    }

    /**
     * Methode that gets called when the sensor receives a new set of values
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100){
                lastUpdate = curTime;

                if (initFlag) {

                    if (x < accel_x1 && x > accel_x2) {       /**detect rotation around horizontal */
                        if (z > accel_zForward) {
                            drone.forward();
                            viewStatus.setText("Forwards");
                            resetFlag = true;
                        }
                        if (z < accel_zBackward) {
                            drone.backward();
                            viewStatus.setText("Backwards");
                            resetFlag = true;
                        }
                    } else if (y < accel_yLeft) {
                        drone.goLeft();
                        viewStatus.setText("Going Left");
                        resetFlag = true;
                    } else if (y > accel_yRight) {
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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

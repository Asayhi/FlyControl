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
 * A SensorEventListener that is specifically designed for use with the accelerometersensor.
 * Accelerometerobjects can be created, SensorListening initiatedd and orientation can be queried.
 *
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
     * Standard constructor of the Accelerometer-Class
     * <p>
     * Initializes the Accelerometer with default values.
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
     * Customizable constructor of the Accelerometer-Class
     * @param zBackward float-Value for the acceleration along z axis needed to trigger the backwards-command
     * @param zForward float-Value for the acceleration along z axis needed to trigger the forwards-command
     * @param x1 float-Value for the acceleration along x axis needed to trigger comparision of z-accel
     * @param x2 float-Value for the acceleration along x axis needed to trigger comparison of z-accel
     * @param yLeft float-Value for the acceleration along y axis needed to trigger the left-command
     * @param yRight float-Value for the acceleration along y axis needed to trigger the right-command
     */
    public Accelerometer(double zBackward, double zForward, double x1, double x2, double yLeft, double yRight){
        lastUpdate = 0;
        initFlag = false;
        resetFlag = false;
        accel_zBackward = zBackward;
        accel_zForward = zForward;
        accel_x1 = x1;
        accel_x2 = x2;
        accel_yLeft = yLeft;
        accel_yRight = yRight;

    }


    /**
     * Method that is called to enable the Accelerometer-object to act as a listener for the
     * accerometer sensor and hand over important objects from the main activity for use in this
     * class.
     *
     * @param senContext The cotnext of the main activity
     * @param foreignDrone The IARDrone object that was initialised in the main activity
     * @param status The TextView status that show the curretn state of the drone
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
     * A method that stops the sensor from interfering with the drone or textView object
     */
    public void disableSenControl() {
        initFlag = false;
    }

    /**
     * Methode that gets called when the sensor receives a new set of value.
     * On a change of the sensors state it retrieves a new set of values, which are
     * used to first determine the orientation of the device around its x axis.
     * A further comparison of the z-value determines if the device is leaned back or forwards
     * If the x vlaue does not trigger the comparison the y values are evaluated to determine if the
     * device is leaned lef or right
     * @param sensorEvent A sensor event
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
                            viewStatus.setText(R.string.tv_Forwards);
                            resetFlag = true;
                        }
                        if (z < accel_zBackward) {
                            drone.backward();
                            viewStatus.setText(R.string.tv_Backwards);
                            resetFlag = true;
                        }
                    } else if (y < accel_yLeft) {
                        drone.goLeft();
                        viewStatus.setText(R.string.tv_Left);
                        resetFlag = true;
                    } else if (y > accel_yRight) {
                        drone.goRight();
                        viewStatus.setText(R.string.tv_Right);
                        resetFlag = true;
                    } else {
                        if (resetFlag) {
                            drone.hover();
                            viewStatus.setText(R.string.tv_Hovering);
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

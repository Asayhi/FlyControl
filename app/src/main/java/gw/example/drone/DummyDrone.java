package gw.example.drone;

import android.util.Log;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.configuration.ConfigurationManager;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.video.VideoManager;

public class DummyDrone implements IARDrone {



    @Override
    public CommandManager getCommandManager() {
        return null;
    }

    @Override
    public NavDataManager getNavDataManager() {
        return null;
    }

    @Override
    public VideoManager getVideoManager() {
        return null;
    }

    @Override
    public ConfigurationManager getConfigurationManager() {
        return null;
    }

    @Override
    public void start() {
        Log.e("Drone:","started");
    }

    @Override
    public void stop() {
        Log.e("Drone","stopped");

    }

    @Override
    public void setHorizontalCamera() {

    }

    @Override
    public void setVerticalCamera() {

    }

    @Override
    public void setHorizontalCameraWithVertical() {

    }

    @Override
    public void setVerticalCameraWithHorizontal() {

    }

    @Override
    public void toggleCamera() {

    }

    @Override
    public void landing() {
        Log.d("Drone", "landing");
    }

    @Override
    public void takeOff() {
        Log.e("Drone","take off");

    }

    @Override
    public void reset() {

    }

    @Override
    public void forward() {

    }

    @Override
    public void backward() {

    }

    @Override
    public void spinRight() {

    }

    @Override
    public void spinLeft() {

    }

    @Override
    public void up() {
        Log.d("Drone:","UP");
    }

    @Override
    public void down() {
        Log.d("Drone:","DOWN");
    }

    @Override
    public void goRight() {

    }

    @Override
    public void goLeft() {

    }

    @Override
    public void freeze() {

    }

    @Override
    public void hover() {
        Log.d("Drone","hover");

    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {

    }

    @Override
    public void addSpeedListener(ARDrone.ISpeedListener speedListener) {

    }

    @Override
    public void removeSpeedListener(ARDrone.ISpeedListener speedListener) {

    }

    @Override
    public void setMaxAltitude(int altitude) {

    }

    @Override
    public void setMinAltitude(int altitude) {

    }

    @Override
    public void move3D(int speedX, int speedY, int speedZ, int speedSpin) {

    }
}

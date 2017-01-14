package gw.example.drone;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.configuration.ConfigurationManager;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.video.VideoManager;

/**
 * Created by gotzwinterfeldt on 14.01.17.
 */
public class DummyDrone implements IARDrone {

    int newSpeed;

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

    }

    @Override
    public void stop() {

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

    }

    @Override
    public void takeOff() {

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

    }

    @Override
    public void down() {

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

    }

    @Override
    public int getSpeed() {
        return newSpeed;
    }

    @Override
    public void setSpeed(int speed) {
        newSpeed=speed;
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

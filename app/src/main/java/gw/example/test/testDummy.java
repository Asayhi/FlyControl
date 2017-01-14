package gw.example.test;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;

import gw.example.drone.DummyDrone;

/**
 * Created by gotzwinterfeldt on 14.01.17.
 */
public class testDummy extends TestCase{

    DummyDrone drone;
    @Before
    public void setUp(){
        drone = new DummyDrone();
    }

    //Alt Ret add Junit to classpath
    @Test
    public void testSpeed(){
        drone.setSpeed(200);
        Assert.assertEquals("Speed", 200, drone.getSpeed());
    }
}

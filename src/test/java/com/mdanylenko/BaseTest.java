package com.mdanylenko;

import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 20.12.2015<br/>
 * Time: 18:12<br/>
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest extends Assert {

    protected void executiveTime(String name,  long startTime, long endTime ){
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Time execution " + name + ": " + String.format("%02d min, %02d sec, %03d milliseconds",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)),
                duration - TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(duration))
                        - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))
        ));
    }
}

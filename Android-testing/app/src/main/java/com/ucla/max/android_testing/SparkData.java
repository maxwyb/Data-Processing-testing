package com.ucla.max.android_testing;

import android.util.Log;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 5/7/16.
 */
public class SparkData {
    public static void piEstimation() {

        // Spark initialization
        int NUM_SAMPLES = 1000;
        SparkConf conf = new SparkConf().setAppName("SparkData").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> l = new ArrayList<Integer>(NUM_SAMPLES);
        for (int i = 0; i < NUM_SAMPLES; i++) {
            l.add(i);
        }

        long count = sc.parallelize(l).filter(new Function<Integer, Boolean>() {
            public Boolean call(Integer i) {
                double x = Math.random();
                double y = Math.random();
                return x*x + y*y < 1;
            }
        }).count();
        // System.out.println("Pi is roughly " + 4.0 * count / NUM_SAMPLES);
        String output = "Pi is roughly " + 4.0 * count / NUM_SAMPLES;
        Log.d("sunnyDay", output);
    }
}

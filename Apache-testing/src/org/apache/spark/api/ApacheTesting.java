package org.apache.spark.api;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;

import java.util.ArrayList;
import java.util.List;

public class ApacheTesting {
	public static void main(String[] args) {
		
		// setting path to load native-hadoop library on Mac OS
		System.setProperty("java.library.path", "/Downloads/hadoop-2.6.4/lib/native/Linux-amd64-64/");
		
		// a testing program in Java to calculate Pi provided by Apache Spark
		SparkConf sparkConf = new SparkConf().setAppName("JavaSparkPi");
	    JavaSparkContext jsc = new JavaSparkContext(sparkConf);

	    int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
	    int n = 100000 * slices;
	    List<Integer> l = new ArrayList<Integer>(n);
	    for (int i = 0; i < n; i++) {
	      l.add(i);
	    }

	    JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

	    int count = dataSet.map(new Function<Integer, Integer>() {
	      @Override
	      public Integer call(Integer integer) {
	        double x = Math.random() * 2 - 1;
	        double y = Math.random() * 2 - 1;
	        return (x * x + y * y < 1) ? 1 : 0;
	      }
	    }).reduce(new Function2<Integer, Integer, Integer>() {
	      @Override
	      public Integer call(Integer integer, Integer integer2) {
	        return integer + integer2;
	      }
	    });

	    System.out.println("Pi is roughly " + 4.0 * count / n);

	    jsc.stop();

		System.out.println("Program finished; Hello Java!");	
		
//		int[] temperature = {50, 68, 86, 92, 100, 32, 80, 103};
//		int[] filteredTemp = temperature.filter(new Function<Integer, Boolean>() {
//			@Override
//			public Boolean call(Integer num) throws Exception {
//				if (num.intValue() >= 90) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
//		
	}
	
//	public static boolean traverseTemp(int num) {
//		if (num >= 90)
//			return true;
//		else
//			return false;
//	}
}

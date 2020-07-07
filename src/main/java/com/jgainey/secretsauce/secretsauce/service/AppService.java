package com.jgainey.secretsauce.secretsauce.service;

import com.google.gson.Gson;
import com.jgainey.secretsauce.secretsauce.beans.Metric;
import com.jgainey.secretsauce.secretsauce.beans.RLPMetric;
import com.jgainey.secretsauce.secretsauce.db.AppDBHelper;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AppService {

    Gson gson = new Gson();
    String fullPathStringName;
    AppDBHelper appDBHelper = new AppDBHelper();


    public enum MetricName {
        THROTTLENOZZLE_INSTANCE_COUNT,
        GONOZZLEV2_INSTANCE_COUNT;
    }

    //this gets called in
    public void handle(String fullPathStringNameIn) {
        this.fullPathStringName = fullPathStringNameIn;
        insertNozzleStats(fullPathStringName);
    }

    private void insertNozzleStats(String fullPathStringName) {
        ArrayList<Metric> list = new ArrayList<>();

        //starts at 1593199800 and increments 3600 every entry
        long startingTimestampThrottleNozzle = 1593199800;
        long startingTimestampGoNozzleV2 = 1593199800;


        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fullPathStringName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //parse the log line by line and extract the number beside the keyword
        if(scanner.hasNext()){
            do{
                String line = scanner.nextLine();

                if(line.contains("ThrottleNozzle")){
                    Metric metric = new Metric();
                    metric.setTimestamp(startingTimestampThrottleNozzle);
                    startingTimestampThrottleNozzle =+ startingTimestampThrottleNozzle + 3600;
                    metric.setMetricName("THROTTLENOZZLE_INSTANCE_COUNT");
                    int firstSpace = line.indexOf(" ");
                    metric.setValue(Float.parseFloat(Character.toString(line.charAt(firstSpace+1))));
                    list.add(metric);
                }else{
                    Metric metric = new Metric();
                    metric.setTimestamp(startingTimestampGoNozzleV2);
                    startingTimestampGoNozzleV2 =+ startingTimestampGoNozzleV2 + 3600;
                    metric.setMetricName("GONOZZLEV2_INSTANCE_COUNT");
                    int firstSpace = line.indexOf(" ");
                    metric.setValue(Float.parseFloat(Character.toString(line.charAt(firstSpace+1))));
                    list.add(metric);
                }
            }while(scanner.hasNextLine());

            for (Metric met : list) {
                Utils.logDebug("\t\t " + met.toString());
                appDBHelper.insert(met);
            }
        }
    }

}

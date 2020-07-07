package com.jgainey.secretsauce.secretsauce.service;

import com.google.gson.Gson;
import com.jgainey.secretsauce.secretsauce.beans.Metric;
import com.jgainey.secretsauce.secretsauce.db.VmTypeDBHelper;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class VmTypeSetupService {

    VmTypeDBHelper VmTypeDBHelper = new VmTypeDBHelper();


    public enum MetricName {
        DOPPLER_CPU_COUNT,
        TC_CPU_COUNT,
        DOPPLER_MEM_GB,
        TC_MEM_GB,
        LOG_CACHE_SYSLOG_EGRESSED;
    }


    public void fillData() {
        //will hardcode this since it is few situations
        ArrayList<Metric> list = new ArrayList<>();

        for(long l = 1593007200L; l <= 1593720000L; l += 3600){

            //doppler cpu
            Metric dopmetcpu = new Metric();
            dopmetcpu.setMetricName(MetricName.DOPPLER_CPU_COUNT.toString());
            dopmetcpu.setTimestamp(l);
            if(l <= 1593525600){
                dopmetcpu.setValue(1f);
            }else if(l < 1593608400){
                dopmetcpu.setValue(2f);
            }else{
                dopmetcpu.setValue(4f);
            }

            //doppler mem
            Metric dopmetmem = new Metric();
            dopmetmem.setMetricName(MetricName.DOPPLER_MEM_GB.toString());
            dopmetmem.setTimestamp(l);
            dopmetmem.setValue(8f);

            //tc cpu
            Metric tcmetcpu = new Metric();
            tcmetcpu.setMetricName(MetricName.TC_CPU_COUNT.toString());
            tcmetcpu.setTimestamp(l);
            if(l <= 1593432000){
                tcmetcpu.setValue(1f);
            }else{
                tcmetcpu.setValue(4f);
            }

            //tc mem
            Metric tcmetmem = new Metric();
            tcmetmem.setMetricName(MetricName.TC_MEM_GB.toString());
            tcmetmem.setTimestamp(l);
            if(l <= 1593432000){
                tcmetmem.setValue(1f);
            }else if(l < 1593694800){
                tcmetmem.setValue(2f);
            }else{
                tcmetmem.setValue(4f);
            }

            //log-cache-syslog-egressed
            Metric logCacheMetSyslog = new Metric();
            logCacheMetSyslog.setMetricName(MetricName.LOG_CACHE_SYSLOG_EGRESSED.toString());
            logCacheMetSyslog.setTimestamp(l);
            if(l <= 1593345600){
                logCacheMetSyslog.setValue(0f);
            }else if(l < 1593655200){
                logCacheMetSyslog.setValue(1f);
            }else{
                logCacheMetSyslog.setValue(0f);
            }

            list.add(dopmetmem);
            list.add(dopmetcpu);
            list.add(tcmetcpu);
            list.add(tcmetmem);
            list.add(logCacheMetSyslog);

        }

        //insert into db
        for (Metric m : list) {
            VmTypeDBHelper.insert(m);
        }
    }

}

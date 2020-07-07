package com.jgainey.secretsauce.secretsauce;

import com.jgainey.secretsauce.secretsauce.service.AppService;
import com.jgainey.secretsauce.secretsauce.service.DopplerService;
import com.jgainey.secretsauce.secretsauce.service.RLPService;
import com.jgainey.secretsauce.secretsauce.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SS {


    DopplerService dopplerService = new DopplerService();
    RLPService rlpService = new RLPService();
    AppService appService = new AppService();


    public enum Source {
        DOPPLER,
        TC,
        RLP,
        RLPGATEWAY,
        VMS,
        NOZZLE;
    }



    public void listAllFiles(String path){
        AtomicInteger i = new AtomicInteger();
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach(path1 -> {
                        i.getAndIncrement();
                        Utils.logDebug(path1.toString());
                    });
        } catch (IOException e) {
            Utils.logDebug("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        Utils.logDebug("\t\t\t\t\t\tthe number of files is "+i);
    }

    public void listAllFilesByType(String path){
        try {
        Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .forEach(path1 -> {
                    Utils.logDebug(associateFileSource(path1) + " " + path1);
                });
        } catch (IOException e) {
            Utils.logDebug("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Source associateFileSource(Path path1) {
        if(path1.toString().contains("doppler")){
            return Source.DOPPLER;
        }else if(path1.toString().contains("traffic")){
            return Source.TC;
        }else if(path1.toString().contains("reverse")){
            if(path1.toString().contains("gateway")){
                return Source.RLPGATEWAY;
            }
            return Source.RLP;
        }else{
            //TODO
            return Source.VMS;
        }
    }

    public void extractAllFiles(String pathIn){
        AtomicInteger k = new AtomicInteger();
        try {
            Files.walk(Paths.get(pathIn))
                    .filter(Files::isRegularFile)
                    .forEach(path1 -> {
                        k.getAndIncrement();
                        Utils.logInfo("extracting file # "+ k.get() +" - " + path1);
                        switch (associateFileSource(path1)){
                            case RLP:
                                Utils.logDebug("case RLP");
                                rlpService.handle(path1.toString());
                                break;
                            case DOPPLER:
                                Utils.logDebug("case DOPPLER");
                                dopplerService.handle(path1.toString());
                                break;
                            case TC:
                                Utils.logDebug("case TC");
                                break;
                            case RLPGATEWAY:
                                Utils.logDebug("case RLPgateway");
                                break;
                            case VMS:
                                Utils.logDebug("case vms");
                                break;
                            case NOZZLE:
                                Utils.logDebug("case nozzle");
                                appService.handle(path1.toString());
                                break;
                            default:
                                Utils.logDebug("case default");
                        }

                    });
        } catch (IOException e) {
            Utils.logDebug("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listAllHours(String path) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach(path1 -> {
                        Utils.logInfo("1 "+path1);
                        if(path1.toString().contains("2020")){
                            String hour = path1.toString().substring(0,13);
                            if (!list.contains(hour)) {
                                Utils.logInfo("Adding " + hour);
                                list.add(hour);
                            }
                        }

                    });
        } catch (IOException e) {
            Utils.logDebug("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void extractNozzleScalingHistory(String s) {
        appService.handle(s);
    }

    public void listEveryMetricNameInEachTable(){

    }

}

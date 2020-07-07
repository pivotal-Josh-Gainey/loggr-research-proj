package com.jgainey.secretsauce.secretsauce;

import com.jgainey.secretsauce.secretsauce.db.*;
import com.jgainey.secretsauce.secretsauce.service.VmTypeSetupService;

public class Main {

    SS ss = new SS();
    DopplerDBHelper dopplerDBHelper = new DopplerDBHelper();
    RLPDBHelper rlpdbHelper = new RLPDBHelper();
    AppDBHelper appDBHelper = new AppDBHelper();
    VmTypeDBHelper vmTypeDBHelper = new VmTypeDBHelper();
    DifferenceDBHelper differenceDBHelper = new DifferenceDBHelper();

    public void start(){
//        list all files
//        ss.listAllFiles("/Users/jgainey/Documents/239407/realdata");


        /*
        Doppler
         */
//        dopplerDBHelper.drop();
//        dopplerDBHelper.create();


        /*
        RLP
         */
//        rlpdbHelper.drop();
//        rlpdbHelper.create();


        /*
        APP
         */
////        appDBHelper.drop();
//        appDBHelper.create();


        /*
        VM-TYPE
         */
//        vmTypeDBHelper.drop();
//        vmTypeDBHelper.create();
//        new VmTypeSetupService().fillData();

        /*
        DIFFERENCE
         */
//        differenceDBHelper.drop();
//        differenceDBHelper.create();
//        differenceDBHelper.fillDataDoppler();
//        differenceDBHelper.fillDataRLP();


        /*
        TEST FOLDER CALLS
         */
//        ss.extractAllFiles("assets");
//        ss.extractNozzleScalingHistory("/Users/jgainey/Documents/239407/realdata/ubuntu/ubuntu/nozzlescalinghistory.txt");
//        ss.listAllHours("/Users/jgainey/Documents/239407/realdata/");



        /*
        REAL FOLDER CALLS
         */
////        ss.extractAllFiles("/Users/jgainey/Documents/239407/realdata");
//        ss.extractNozzleScalingHistory("/Users/jgainey/Documents/239407/realdata/ubuntu/ubuntu/nozzlescalinghistory.txt");

    }

}

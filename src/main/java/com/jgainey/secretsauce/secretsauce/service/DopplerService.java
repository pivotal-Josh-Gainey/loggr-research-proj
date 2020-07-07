package com.jgainey.secretsauce.secretsauce.service;

import com.google.gson.Gson;
import com.jgainey.secretsauce.secretsauce.extractors.DopplerCounterExtractor;
import com.jgainey.secretsauce.secretsauce.utils.Utils;


public class DopplerService {

    Gson gson = new Gson();
    DopplerCounterExtractor dopplerCounterExtractor = new DopplerCounterExtractor();


    public enum MetricName {
        DOPPLER_CURRENT_EGRESS_COUNTER_TOTAL,
        DOPPLER_CURRENT_INGRESS_COUNTER_TOTAL,
        DOPPLER_SUBSCRIPTIONS,
        DOPPLER_EGRESS_DROP_1M,
        DOPPLER_EGRESS_RATE_1M,
        DOPPLER_INGRESS_DROP_1M,
        DOPPLER_INGRESS_RATE_1M;
    }

    public void handle(String fullPathStringNameIn) {
        String fullPathStringName = fullPathStringNameIn;
        MetricName fullMetricName = classify(fullPathStringName);
            process(fullMetricName, fullPathStringName);


    }

    private void process(MetricName fullMetricName, String fullPathStringName){
        switch (fullMetricName){
            case DOPPLER_CURRENT_EGRESS_COUNTER_TOTAL:
                Utils.logDebug("\tcase DOPPLER_CURRENT_EGRESS_COUNTER_TOTAL");
                dopplerCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_CURRENT_EGRESS_COUNTER_TOTAL);
                break;
            case DOPPLER_CURRENT_INGRESS_COUNTER_TOTAL:
                Utils.logDebug("\tcase DOPPLER_CURRENT_INGRESS_COUNTER_TOTAL");
                dopplerCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_CURRENT_INGRESS_COUNTER_TOTAL);
                break;
            case DOPPLER_EGRESS_DROP_1M:
                Utils.logDebug("\tcase DOPPLER_EGRESS_DROP_1M");
                dopplerCounterExtractor.processValueArraysOfArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_EGRESS_DROP_1M);
                break;
            case DOPPLER_EGRESS_RATE_1M:
                Utils.logDebug("\tcase DOPPLER_EGRESS_RATE_1M");
                dopplerCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_EGRESS_RATE_1M);
                break;
            case DOPPLER_INGRESS_DROP_1M:
                Utils.logDebug("\tcase DOPPLER_INGRESS_DROP_1M");
                dopplerCounterExtractor.processValueArraysOfArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_INGRESS_DROP_1M);
                break;
            case DOPPLER_INGRESS_RATE_1M:
                Utils.logDebug("\tcase DOPPLER_INGRESS_RATE_1M");
                dopplerCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_INGRESS_RATE_1M);
                break;
            case DOPPLER_SUBSCRIPTIONS:
                Utils.logDebug("\tcase DOPPLER_SUBSCRIPTIONS");
                dopplerCounterExtractor.processValueArraysOfArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.DOPPLER_SUBSCRIPTIONS);
                break;
            default:
                // code block
                Utils.logDebug("\tcase DOPPLER_default");
        }
    }


    private MetricName classify(String fullPathStringName) {
        if(fullPathStringName.contains("current_egress")){
            return MetricName.DOPPLER_CURRENT_EGRESS_COUNTER_TOTAL;
        }else if(fullPathStringName.contains("current_ingress")){
            return MetricName.DOPPLER_CURRENT_INGRESS_COUNTER_TOTAL;
        }else if(fullPathStringName.contains("subscriptions")){
            return MetricName.DOPPLER_SUBSCRIPTIONS;
        }else if(fullPathStringName.contains("egress_drop")){
            return MetricName.DOPPLER_EGRESS_DROP_1M;
        }else if(fullPathStringName.contains("egress_rate")){
            return MetricName.DOPPLER_EGRESS_RATE_1M;
        }else if(fullPathStringName.contains("ingress_drop")){
            return MetricName.DOPPLER_INGRESS_DROP_1M;
        }else{
            return MetricName.DOPPLER_INGRESS_RATE_1M;
        }
    }


}

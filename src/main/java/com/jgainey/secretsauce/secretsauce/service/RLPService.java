package com.jgainey.secretsauce.secretsauce.service;

import com.google.gson.Gson;
import com.jgainey.secretsauce.secretsauce.extractors.RLPCounterExtractor;
import com.jgainey.secretsauce.secretsauce.utils.Utils;


public class RLPService {

    Gson gson = new Gson();
    RLPCounterExtractor RLPCounterExtractor = new RLPCounterExtractor();


    public enum MetricName {
        RLP_CURRENT_EGRESS_COUNTER_TOTAL,
        RLP_CURRENT_INGRESS_COUNTER_TOTAL,
        RLP_EGRESS_DROP_1M,
        RLP_LOG_ROUTER_DISCONNECTS_1M,
        RLP_REJECTED_STREAMS_1M;
    }

    public void handle(String fullPathStringNameIn) {
        String fullPathStringName = fullPathStringNameIn;
        MetricName fullMetricName = classify(fullPathStringName);
            process(fullMetricName, fullPathStringName);


    }

    private void process(MetricName fullMetricName, String fullPathStringName){
        switch (fullMetricName){
            case RLP_CURRENT_EGRESS_COUNTER_TOTAL:
                Utils.logDebug("\tcase RLP_CURRENT_EGRESS_COUNTER_TOTAL");
                RLPCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.RLP_CURRENT_EGRESS_COUNTER_TOTAL);
                break;
            case RLP_CURRENT_INGRESS_COUNTER_TOTAL:
                Utils.logDebug("\tcase RLP_CURRENT_INGRESS_COUNTER_TOTAL");
                RLPCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.RLP_CURRENT_INGRESS_COUNTER_TOTAL);
                break;
            case RLP_EGRESS_DROP_1M:
                Utils.logDebug("\tcase RLP_EGRESS_DROP_1M");
                RLPCounterExtractor.processValueArraysOfArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.RLP_EGRESS_DROP_1M);
                break;
            case RLP_LOG_ROUTER_DISCONNECTS_1M:
                Utils.logDebug("\tcase RLP_LOG_ROUTER_DISCONNECTS_1M");
                RLPCounterExtractor.processValueArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.RLP_LOG_ROUTER_DISCONNECTS_1M);
                break;
            case RLP_REJECTED_STREAMS_1M:
                Utils.logDebug("\tcase RLP_REJECTED_STREAMS_1M");
                RLPCounterExtractor.processValueArraysOfArray(Utils.getAsJsonString(fullPathStringName),
                        MetricName.RLP_REJECTED_STREAMS_1M);
                break;
            default:
                // code block
                Utils.logDebug("\tcase RLP_default");
        }
    }


    private MetricName classify(String fullPathStringName) {
        if(fullPathStringName.contains("current_egress")){
            return MetricName.RLP_CURRENT_EGRESS_COUNTER_TOTAL;
        }else if(fullPathStringName.contains("current_ingress")){
            return MetricName.RLP_CURRENT_INGRESS_COUNTER_TOTAL;
        }else if(fullPathStringName.contains("egress_drop")){
            return MetricName.RLP_EGRESS_DROP_1M;
        }else if(fullPathStringName.contains("log_router_disconnects")){
            return MetricName.RLP_LOG_ROUTER_DISCONNECTS_1M;
        }else{
            return MetricName.RLP_REJECTED_STREAMS_1M;
        }
    }


}

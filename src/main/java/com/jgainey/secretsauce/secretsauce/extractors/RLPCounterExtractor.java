package com.jgainey.secretsauce.secretsauce.extractors;
import com.google.gson.*;
import com.jgainey.secretsauce.secretsauce.beans.DopplerMetric;
import com.jgainey.secretsauce.secretsauce.beans.RLPMetric;
import com.jgainey.secretsauce.secretsauce.db.RLPDBHelper;
import com.jgainey.secretsauce.secretsauce.service.RLPService;
import com.jgainey.secretsauce.secretsauce.utils.Utils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class RLPCounterExtractor {

    Gson gson = new Gson();
    RLPDBHelper RLPDbHelper = new RLPDBHelper();
    Utils utils = new Utils();


    public void processValueArray(String entireFileAsJson, RLPService.MetricName metricName){

        //list to hold RLPMetric objects
        ArrayList<RLPMetric> RLPMetrics = new ArrayList<>();

        //get the results
        JsonArray results;
        try{
            results = new JsonParser().parse(entireFileAsJson)
                    .getAsJsonObject()
                    .getAsJsonObject("data")
                    .getAsJsonArray("result");
        }catch (Exception e){
            Utils.logError("Error in processValueArray getting jsonObject");
            return;
        }

        //iterate over each result and build a RLPMetric object
        for (int i = 0; i < results.size(); i++) {
            JsonObject metric = results.get(i).getAsJsonObject();
            JsonObject metricinfo = metric.getAsJsonObject("metric");
            JsonArray valueArray = metric.getAsJsonArray("value");

            //build the metric

            Float value = valueArray.get(1).getAsFloat();

            RLPMetric RLPMetric = new RLPMetric(valueArray.get(0).getAsLong(),
                    metricName.toString(),
                    value, "");
            RLPMetric.setIndex(metricinfo.get("index").getAsString());

            //add object to list
            RLPMetrics.add(RLPMetric);

        }

        for (RLPMetric rlpmet : RLPMetrics) {
            Utils.logDebug("Inserting " + rlpmet.toString());

            //add to db
            if (!rlpmet.getValue().isNaN()){
                RLPDbHelper.insert(rlpmet);
            }
        }
    }


    public void processValueArraysOfArray(@NonNull String entireFileAsJson, RLPService.MetricName metricName) {

        //list to hold RLPMetric objects
        ArrayList<RLPMetric> RLPMetrics = new ArrayList<>();

        JsonArray results;
        try{
            results = new JsonParser().parse(entireFileAsJson)
                    .getAsJsonObject()
                    .getAsJsonObject("data")
                    .getAsJsonArray("result");
        }catch (Exception e){
            Utils.logError("Error in processValueArraysOfArray getting jsonObject");
            return;
        }

        //iterate over each result and build a RLPMetric object
        for (int i = 0; i < results.size(); i++) {
            JsonObject metric = results.get(i).getAsJsonObject();
            JsonObject metricinfo = metric.getAsJsonObject("metric");
            JsonArray valuesArray = metric.getAsJsonArray("values");

            for (JsonElement element : valuesArray) {
                JsonArray valueArray = element.getAsJsonArray();

                //build the metric

                Float value = valueArray.get(1).getAsFloat();
                RLPMetric RLPMetric = new RLPMetric(valueArray.get(0).getAsLong(),
                        metricName.toString(),
                        value, "");
                RLPMetric.setIndex(metricinfo.get("index").getAsString());

                //add object to list
                RLPMetrics.add(RLPMetric);

            }

        }

        for (RLPMetric rlpmet : RLPMetrics) {
            Utils.logDebug("Inserting " + rlpmet.toString());

            //add to db
            if (!rlpmet.getValue().isNaN()){
                RLPDbHelper.insert(rlpmet);
            }
        }
    }
}

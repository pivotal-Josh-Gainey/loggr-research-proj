package com.jgainey.secretsauce.secretsauce.extractors;
import com.google.gson.*;
import com.jgainey.secretsauce.secretsauce.beans.DopplerMetric;
import com.jgainey.secretsauce.secretsauce.db.DopplerDBHelper;
import com.jgainey.secretsauce.secretsauce.service.DopplerService;
import com.jgainey.secretsauce.secretsauce.utils.Utils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
public class DopplerCounterExtractor {

    Gson gson = new Gson();
    DopplerDBHelper dopplerDbHelper = new DopplerDBHelper();
    Utils utils = new Utils();


    public void processValueArray(String entireFileAsJson, DopplerService.MetricName metricName){

        //list to hold DopplerMetric objects
        ArrayList<DopplerMetric> dopplerMetrics = new ArrayList<>();

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

        //iterate over each result and build a DopplerMetric object
        for (int i = 0; i < results.size(); i++) {
            JsonObject metric = results.get(i).getAsJsonObject();
            JsonObject metricinfo = metric.getAsJsonObject("metric");
            JsonArray valueArray = metric.getAsJsonArray("value");

            //build the metric

            Float value = valueArray.get(1).getAsFloat();

            DopplerMetric dopplerMetric = new DopplerMetric(valueArray.get(0).getAsLong(),
                    metricName.toString(),
                    value, "");
            dopplerMetric.setIndex(metricinfo.get("index").getAsString());

            //add object to list
            dopplerMetrics.add(dopplerMetric);

        }

        for (DopplerMetric dopmet : dopplerMetrics) {
            Utils.logDebug("Inserting " + dopmet.toString());

            //add to db
            if (!dopmet.getValue().isNaN()){
                dopplerDbHelper.insert(dopmet);
            }
        }
    }


    public void processValueArraysOfArray(@NonNull String entireFileAsJson, DopplerService.MetricName metricName) {

        //list to hold DopplerMetric objects
        ArrayList<DopplerMetric> dopplerMetrics = new ArrayList<>();

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

        //iterate over each result and build a DopplerMetric object
        for (int i = 0; i < results.size(); i++) {
            JsonObject metric = results.get(i).getAsJsonObject();
            JsonObject metricinfo = metric.getAsJsonObject("metric");
            JsonArray valuesArray = metric.getAsJsonArray("values");

            for (JsonElement element : valuesArray) {
                JsonArray valueArray = element.getAsJsonArray();

                //build the metric

                Float value = valueArray.get(1).getAsFloat();
                DopplerMetric dopplerMetric = new DopplerMetric(valueArray.get(0).getAsLong(),
                        metricName.toString(),
                        value, "");
                dopplerMetric.setIndex(metricinfo.get("index").getAsString());

                //add object to list
                dopplerMetrics.add(dopplerMetric);

            }

        }

        for (DopplerMetric dopmet : dopplerMetrics) {
            Utils.logDebug("\t\t " + dopmet.toString());

            //add to db
            if (!dopmet.getValue().isNaN()){
                dopplerDbHelper.insert(dopmet);
            }

        }
    }
}

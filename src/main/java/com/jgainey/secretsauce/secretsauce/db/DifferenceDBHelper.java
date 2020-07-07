package com.jgainey.secretsauce.secretsauce.db;

import com.jgainey.secretsauce.secretsauce.beans.DopplerMetric;
import com.jgainey.secretsauce.secretsauce.beans.Metric;
import com.jgainey.secretsauce.secretsauce.beans.RLPMetric;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DifferenceDBHelper {

    Connection c = null;

    public DifferenceDBHelper() {

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/db1",
                            "sauce", "password");
        } catch (Exception e) {
            e.printStackTrace();
            Utils.logError(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }


    public void create() {
        Statement stmt = null;
        try {
            String sql = "CREATE TABLE if not exists differencemetrics " +
                    "(id SERIAL PRIMARY KEY," +
                    " index           TEXT    NOT NULL, " +
                    " value            FLOAT     NOT NULL, " +
                    " difference            FLOAT, " +
                    " metricname        TEXT, " +
                    " time         BIGINT);";
            stmt = c.createStatement();
            Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        Statement stmt = null;
        try {
            String sql = "DROP TABLE differencemetrics;";
            stmt = c.createStatement();
            Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillDataDoppler(){
        Statement stmt = null;
        List<DopplerMetric> allDopMets = new ArrayList<>();
        try {
            String sql = "select * from dopplermetrics;";

            stmt = c.createStatement();
            ResultSet allDopResultSet = stmt.executeQuery(sql);
            while (allDopResultSet.next()){
                allDopMets.add(new DopplerMetric(allDopResultSet.getLong("time"),
                        allDopResultSet.getString("metricname"),
                        allDopResultSet.getFloat("value"),
                        allDopResultSet.getString("index")));
            }


            ArrayList<String> dopplerIndexes = new ArrayList<>();
            ResultSet indexResultSet = stmt.executeQuery("select distinct index from dopplermetrics");
            while (indexResultSet.next()){
                dopplerIndexes.add(indexResultSet.getString("index"));
            }
            ArrayList<String> metricNames = new ArrayList<>();
            ResultSet metricNameResultSet = stmt.executeQuery("select distinct metricname from dopplermetrics");
            while (metricNameResultSet.next()){
                metricNames.add(metricNameResultSet.getString("metricname"));
            }

            for(int i = 0; i<dopplerIndexes.size(); i++){
                final int repOfI = i;

                for(int j = 0 ; j< metricNames.size(); j++){
                    final int repOfJ = j;
                    List<DopplerMetric> list = allDopMets.stream()
                            .filter(dopmet -> dopmet.getMetricName().equals(metricNames.get(repOfJ)))
                            .filter(dopmet -> dopmet.getIndex().equals(dopplerIndexes.get(repOfI)))
                            .sorted(Comparator.comparing(Metric::getTimestamp))
                            .collect(Collectors.toList());

                    //Utils.logDebug("metricname: "+ metricNames.get(repOfJ) + " index: " +dopplerIndexes.get(repOfI) + " list size: " + list.size());
                    float lastRecordedValue = 0f;
                    for (DopplerMetric dopplerMetric : list) {
                        float currentValue = dopplerMetric.getValue();
                        float difference = currentValue - lastRecordedValue;
                        lastRecordedValue = currentValue;
                        Utils.logDebug("\tdifference: "+difference);
                        DopplerMetric dopplerMetric1 = new DopplerMetric(dopplerMetric.getTimestamp(),
                                dopplerMetric.getMetricName(),
                                difference);
                        Utils.logDebug(dopplerMetric.toString());
                        insertDopplerMetric(stmt, dopplerMetric1, dopplerIndexes.get(i));
                    }
                }

             }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void fillDataRLP(){
        Statement stmt = null;
        List<RLPMetric> allrlpMets = new ArrayList<>();
        try {
            String sql = "select * from rlpmetrics;";

            stmt = c.createStatement();
            ResultSet allRlpResultSet = stmt.executeQuery(sql);
            while (allRlpResultSet.next()){
                allrlpMets.add(new RLPMetric(allRlpResultSet.getLong("time"),
                        allRlpResultSet.getString("metricname"),
                        allRlpResultSet.getFloat("value"),
                        allRlpResultSet.getString("index")));
            }


            ArrayList<String> rlpIndexes = new ArrayList<>();
            ResultSet indexResultSet = stmt.executeQuery("select distinct index from rlpmetrics");
            while (indexResultSet.next()){
                rlpIndexes.add(indexResultSet.getString("index"));
            }
            ArrayList<String> metricNames = new ArrayList<>();
            ResultSet metricNameResultSet = stmt.executeQuery("select distinct metricname from rlpmetrics");
            while (metricNameResultSet.next()){
                metricNames.add(metricNameResultSet.getString("metricname"));
            }

            for(int i = 0; i<rlpIndexes.size(); i++){
                final int repOfI = i;

                for(int j = 0 ; j< metricNames.size(); j++){
                    final int repOfJ = j;
                    List<RLPMetric> list = allrlpMets.stream()
                            .filter(rlpmet -> rlpmet.getMetricName().equals(metricNames.get(repOfJ)))
                            .filter(rlpmet -> rlpmet.getIndex().equals(rlpIndexes.get(repOfI)))
                            .sorted(Comparator.comparing(Metric::getTimestamp))
                            .collect(Collectors.toList());

                    //Utils.logDebug("metricname: "+ metricNames.get(repOfJ) + " index: " +dopplerIndexes.get(repOfI) + " list size: " + list.size());
                    float lastRecordedValue = 0f;
                    for (RLPMetric rlpMetric : list) {
                        float currentValue = rlpMetric.getValue();
                        float difference = currentValue - lastRecordedValue;
                        lastRecordedValue = currentValue;
                        Utils.logDebug("\tdifference: "+difference);
                        RLPMetric rlpMetric1 = new RLPMetric(rlpMetric.getTimestamp(),
                                rlpMetric.getMetricName(),
                                difference);
                        Utils.logDebug(rlpMetric.toString());
                        insertRlpMetric(stmt, rlpMetric1, rlpIndexes.get(i));
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertDopplerMetric(Statement stmt, DopplerMetric metric, String index){
        try {
            String sql = "INSERT INTO differencemetrics (time,value,index,metricname) "
                    + "VALUES ("+metric.getTimestamp()+"," +
                    " "+metric.getValue()+"," +
                    "'"+index+"'," +
                    "'"+metric.getMetricName()+"');";
            Utils.logInfo(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertRlpMetric(Statement stmt, RLPMetric metric, String index){
        try {
            String sql = "INSERT INTO differencemetrics (time,value,index,metricname) "
                    + "VALUES ("+metric.getTimestamp()+"," +
                    " "+metric.getValue()+"," +
                    "'"+index+"'," +
                    "'"+metric.getMetricName()+"');";
            Utils.logInfo(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

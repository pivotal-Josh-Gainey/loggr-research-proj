package com.jgainey.secretsauce.secretsauce.db;

import com.jgainey.secretsauce.secretsauce.beans.DopplerMetric;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DopplerDBHelper {

    Connection c = null;

    public DopplerDBHelper() {

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

    public void insert(DopplerMetric dopplerMetric){
        Statement stmt = null;
        try {
            Utils.logDebug("Attempting to insert: " + dopplerMetric.toString());
            String sql = "INSERT INTO dopplermetrics (time,value,index,metricname) "
                    + "VALUES ("+dopplerMetric.getTimestamp()+"," +
                    " "+dopplerMetric.getValue()+"," +
                    "'"+dopplerMetric.getIndex()+"'," +
                    "'"+dopplerMetric.getMetricName()+"');";

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//DopplerMetric{index='a5b44223-20fe-4897-b5ac-aa6f63b9bfa5', timestamp=1593091701, metricName='DOPPLER_EGRESS_DROP_1M', value=0.0, message=''}

    }

    public void create() {
        Statement stmt = null;
        try {
            String sql = "CREATE TABLE dopplermetrics " +
                    "(id SERIAL PRIMARY KEY," +
                    " index           TEXT    NOT NULL, " +
                    " value            FLOAT     NOT NULL, " +
                    " difference            FLOAT, " +
                    " metricname        TEXT, " +
                    " time         BIGINT);";
            stmt = c.createStatement();
            //Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        Statement stmt = null;
        try {
            String sql = "DROP TABLE dopplermetrics;";
            stmt = c.createStatement();
            //Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.jgainey.secretsauce.secretsauce.db;

import com.jgainey.secretsauce.secretsauce.beans.Metric;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDBHelper {

    Connection c = null;

    public AppDBHelper() {

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

    public void insert(Metric metric){
        Statement stmt = null;
        try {
            Utils.logDebug("Attempting to insert: " + metric.toString());
            String sql = "INSERT INTO nozzlescalinghistory (time,value,metricname) "
                    + "VALUES ("+metric.getTimestamp()+"," +
                    " "+metric.getValue()+"," +
                    "'"+metric.getMetricName()+"');";

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void create() {
        Statement stmt = null;
        try {
            String sql = "CREATE TABLE nozzlescalinghistory " +
                    "(id SERIAL PRIMARY KEY," +
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
            String sql = "DROP TABLE nozzlescalinghistory;";
            stmt = c.createStatement();
            Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

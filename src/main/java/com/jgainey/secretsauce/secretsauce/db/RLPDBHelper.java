package com.jgainey.secretsauce.secretsauce.db;

import com.jgainey.secretsauce.secretsauce.beans.DopplerMetric;
import com.jgainey.secretsauce.secretsauce.beans.RLPMetric;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RLPDBHelper {

    Connection c = null;

    public RLPDBHelper() {

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

    public void insert(RLPMetric rlpMetric){
        Statement stmt = null;
        try {
            Utils.logDebug("Attempting to insert: " + rlpMetric.toString());
            String sql = "INSERT INTO rlpmetrics (time,value,index,metricname) "
                    + "VALUES ("+rlpMetric.getTimestamp()+"," +
                    " "+rlpMetric.getValue()+"," +
                    "'"+rlpMetric.getIndex()+"'," +
                    "'"+rlpMetric.getMetricName()+"');";

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void create() {
        Statement stmt = null;
        try {
            String sql = "CREATE TABLE rlpmetrics " +
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
            String sql = "DROP TABLE rlpmetrics;";
            stmt = c.createStatement();
            Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.jgainey.secretsauce.secretsauce.db;

import com.jgainey.secretsauce.secretsauce.beans.Metric;
import com.jgainey.secretsauce.secretsauce.service.VmTypeSetupService;
import com.jgainey.secretsauce.secretsauce.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VmTypeDBHelper {

    Connection c = null;

    public VmTypeDBHelper() {

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
            String sql = "INSERT INTO vmtypes (time,value,metricname) "
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
            String sql = "CREATE TABLE vmtypes " +
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
            String sql = "DROP TABLE vmtypes;";
            stmt = c.createStatement();
            Utils.logDebug(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

package com.jgainey.secretsauce.secretsauce.beans;

public class Metric {

    Long timestamp;
    String metricName;
    Float value;

    public Metric(Long timestamp, String metricName, Float value) {
        this.timestamp = timestamp;
        this.metricName = metricName;
        this.value = value;
    }

    public Metric() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

}

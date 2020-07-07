package com.jgainey.secretsauce.secretsauce.beans;

public class DopplerMetric extends Metric {

    String index;

    public DopplerMetric(Long timestamp, String metricName, Float value, String index) {
        super(timestamp, metricName, value);
        this.index = index;
    }

    public DopplerMetric(Long timestamp, String metricName, Float value) {
        super(timestamp, metricName, value);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "DopplerMetric{" +
                "index='" + index + '\'' +
                ", timestamp=" + timestamp +
                ", metricName='" + metricName + '\'' +
                ", value=" + value +
                '}';
    }
}

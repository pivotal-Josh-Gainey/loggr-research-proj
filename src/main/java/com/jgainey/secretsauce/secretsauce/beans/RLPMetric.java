package com.jgainey.secretsauce.secretsauce.beans;

public class RLPMetric extends Metric {

    String index;

    public RLPMetric(Long timestamp, String metricName, Float value, String index) {
        super(timestamp, metricName, value);
        this.index = index;
    }

    public RLPMetric(Long timestamp, String metricName, Float value) {
        super(timestamp, metricName, value);
    }

    public RLPMetric(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "RLPMetric{" +
                "index='" + index + '\'' +
                ", timestamp=" + timestamp +
                ", metricName='" + metricName + '\'' +
                ", value=" + value +
                '}';
    }
}

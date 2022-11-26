package com.codahale.metrics;

@FunctionalInterface
public interface Gauge extends Metric {
   Object getValue();
}

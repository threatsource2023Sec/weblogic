package com.codahale.metrics;

import java.util.Map;

public interface MetricSet extends Metric {
   Map getMetrics();
}

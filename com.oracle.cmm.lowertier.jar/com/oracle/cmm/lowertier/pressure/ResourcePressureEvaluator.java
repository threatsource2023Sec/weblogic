package com.oracle.cmm.lowertier.pressure;

public interface ResourcePressureEvaluator {
   void initialize(String var1);

   int evaluateMemoryPressure();
}

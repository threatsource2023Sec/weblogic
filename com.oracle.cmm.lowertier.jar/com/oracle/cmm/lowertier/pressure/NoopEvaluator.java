package com.oracle.cmm.lowertier.pressure;

public class NoopEvaluator implements ResourcePressureEvaluator {
   public void initialize(String initialValue) {
   }

   public int evaluateMemoryPressure() {
      return 0;
   }
}

package com.oracle.cmm.lowertier.pressure;

public class NoopAdjustmentDamper implements ResourcePressureAdjustmentDamper {
   public void initialize(String initialValue) {
   }

   public int adjustMemoryPressure(int currentPressure) {
      return currentPressure;
   }
}

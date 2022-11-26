package com.oracle.cmm.lowertier.pressure;

public interface ResourcePressureAdjustmentDamper {
   void initialize(String var1);

   int adjustMemoryPressure(int var1);
}

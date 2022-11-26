package com.oracle.cmm.agent.internal;

import java.io.Serializable;

public class MemoryPressure implements Serializable {
   private static final long serialVersionUID = 8296255282143483817L;
   private static final int MIN_PRESSURE_LEVEL = 0;
   private static final int MAX_PRESSURE_LEVEL = 10;
   private int pressureLevel = 0;

   public synchronized int getPressureLevel() {
      return this.pressureLevel;
   }

   public synchronized void setPressureLevel(int pressureLevel) {
      if (pressureLevel >= 0 && pressureLevel <= 10) {
         this.pressureLevel = pressureLevel;
      } else {
         throw new IllegalArgumentException("Invalid pressure level: " + pressureLevel);
      }
   }
}

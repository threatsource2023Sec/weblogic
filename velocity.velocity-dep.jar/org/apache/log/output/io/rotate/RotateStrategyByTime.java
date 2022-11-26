package org.apache.log.output.io.rotate;

import java.io.File;

public class RotateStrategyByTime implements RotateStrategy {
   private long m_timeInterval;
   private long m_startingTime;
   private long m_currentRotation;

   public RotateStrategyByTime() {
      this(86400000L);
   }

   public RotateStrategyByTime(long timeInterval) {
      this.m_startingTime = System.currentTimeMillis();
      this.m_currentRotation = 0L;
      this.m_timeInterval = timeInterval;
   }

   public void reset() {
      this.m_startingTime = System.currentTimeMillis();
      this.m_currentRotation = 0L;
   }

   public boolean isRotationNeeded(String data, File file) {
      long newRotation = (System.currentTimeMillis() - this.m_startingTime) / this.m_timeInterval;
      if (newRotation > this.m_currentRotation) {
         this.m_currentRotation = newRotation;
         return true;
      } else {
         return false;
      }
   }
}

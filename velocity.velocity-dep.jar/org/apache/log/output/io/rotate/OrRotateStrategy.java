package org.apache.log.output.io.rotate;

import java.io.File;

public class OrRotateStrategy implements RotateStrategy {
   private RotateStrategy[] m_strategies;
   private int m_usedRotation = -1;

   public OrRotateStrategy(RotateStrategy[] strategies) {
      this.m_strategies = strategies;
   }

   public void reset() {
      if (-1 != this.m_usedRotation) {
         this.m_strategies[this.m_usedRotation].reset();
         this.m_usedRotation = -1;
      }

   }

   public boolean isRotationNeeded(String data, File file) {
      this.m_usedRotation = -1;
      if (null != this.m_strategies) {
         int length = this.m_strategies.length;

         for(int i = 0; i < length; ++i) {
            if (this.m_strategies[i].isRotationNeeded(data, file)) {
               this.m_usedRotation = i;
               return true;
            }
         }
      }

      return false;
   }
}

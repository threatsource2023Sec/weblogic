package weblogic.apache.org.apache.log.output.io.rotate;

import java.io.File;

public class RotateStrategyBySize implements RotateStrategy {
   private long m_maxSize;
   private long m_currentSize;

   public RotateStrategyBySize() {
      this(1048576L);
   }

   public RotateStrategyBySize(long maxSize) {
      this.m_currentSize = 0L;
      this.m_maxSize = maxSize;
   }

   public void reset() {
      this.m_currentSize = 0L;
   }

   public boolean isRotationNeeded(String data, File file) {
      this.m_currentSize += (long)data.length();
      if (this.m_currentSize >= this.m_maxSize) {
         this.m_currentSize = 0L;
         return true;
      } else {
         return false;
      }
   }
}

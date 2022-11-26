package weblogic.utils.time;

public class JDK15NanoTimer extends Timer {
   public boolean isNative() {
      return true;
   }

   public long timestamp() {
      return System.nanoTime();
   }
}

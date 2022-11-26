package weblogic.utils.time;

public class NativeTimer extends Timer {
   public NativeTimer() {
      System.loadLibrary("precisiontime");
      this.init();
   }

   public boolean isNative() {
      return true;
   }

   public long timestamp() {
      return this.timestamp0();
   }

   private native void init();

   private native long timestamp0();
}

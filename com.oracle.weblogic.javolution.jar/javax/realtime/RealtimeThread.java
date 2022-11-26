package javax.realtime;

public class RealtimeThread extends Thread {
   public static MemoryArea getCurrentMemoryArea() {
      return MemoryArea.DEFAULT;
   }
}

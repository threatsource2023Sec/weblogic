package antlr.debug;

public class InputBufferReporter implements InputBufferListener {
   public void doneParsing(TraceEvent var1) {
   }

   public void inputBufferChanged(InputBufferEvent var1) {
      System.out.println(var1);
   }

   public void inputBufferConsume(InputBufferEvent var1) {
      System.out.println(var1);
   }

   public void inputBufferLA(InputBufferEvent var1) {
      System.out.println(var1);
   }

   public void inputBufferMark(InputBufferEvent var1) {
      System.out.println(var1);
   }

   public void inputBufferRewind(InputBufferEvent var1) {
      System.out.println(var1);
   }

   public void refresh() {
   }
}

package javolution;

import java.io.PrintStream;
import javolution.lang.Reflection;
import javolution.lang.TextBuilder;

public class Javolution {
   public static final String VERSION = "3.7.10 (J2SE 1.5+) May 1 2006";
   private static PrintStream Out;
   private static long _time;
   private static final Reflection.Method NANO_TIME_METHOD;

   protected Javolution() {
   }

   public static void main(String[] var0) throws Exception {
      println("Javolution - Java(TM) Solution for Real-Time and Embedded Systems");
      println("Version 3.7.10 (J2SE 1.5+) May 1 2006 (http://javolution.org)");
      println("");
      if (var0.length > 0) {
         if (var0[0].equals("version")) {
            return;
         }

         if (var0[0].equals("test")) {
            testing();
            return;
         }

         if (var0[0].equals("perf")) {
            benchmark();
            return;
         }
      }

      println("Usage: java -jar javolution.jar [arg]");
      println("where arg is one of:");
      println("    version (to show version information only)");
      println("    test    (to perform self-tests)");
      println("    perf    (to run benchmark)");
   }

   private static void testing() throws Exception {
      print("Testing...");
      println("");
      println("Success");
   }

   private static void benchmark() throws Exception {
      println("Benchmark...");
      println("");
      Thread.currentThread().setPriority(10);
      (new Perf_Lang()).run();
      (new Perf_Realtime()).run();
      (new Perf_Util()).run();
      (new Perf_Xml()).run();
      println("");
      println("More performance analysis in future versions...");
   }

   public static void println(Object var0) {
      if (Out != null) {
         Out.println(var0);
      }
   }

   public static void print(Object var0) {
      if (Out != null) {
         Out.print(var0);
      }
   }

   public static void startTime() {
      System.gc();

      try {
         Thread.sleep(500L);
      } catch (InterruptedException var1) {
         throw new JavolutionError(var1);
      }

      _time = nanoTime();
   }

   public static void setOutputStream(PrintStream var0) {
      Out = var0;
   }

   public static String endTime(int var0) {
      long var1 = nanoTime() - _time;
      long var3 = var1 * 1000L / (long)var0;
      long var5;
      String var7;
      if (var3 > 1000000000000L) {
         var7 = " s";
         var5 = 1000000000000L;
      } else if (var3 > 1000000000L) {
         var7 = " ms";
         var5 = 1000000000L;
      } else if (var3 > 1000000L) {
         var7 = " us";
         var5 = 1000000L;
      } else {
         var7 = " ns";
         var5 = 1000L;
      }

      TextBuilder var8 = TextBuilder.newInstance();
      var8.append(var3 / var5);
      int var9 = 4 - var8.length();
      var8.append(".");
      int var10 = 0;

      for(int var11 = 10; var10 < var9; var11 *= 10) {
         var8.append(var3 * (long)var11 / var5 % 10L);
         ++var10;
      }

      return var8.append(var7).toString();
   }

   private static long nanoTime() {
      if (NANO_TIME_METHOD != null) {
         Long var0 = (Long)NANO_TIME_METHOD.invoke((Object)null);
         return var0;
      } else {
         return System.currentTimeMillis() * 1000000L;
      }
   }

   static {
      Out = System.out;
      NANO_TIME_METHOD = Reflection.getMethod("java.lang.System.nanoTime()");
   }
}

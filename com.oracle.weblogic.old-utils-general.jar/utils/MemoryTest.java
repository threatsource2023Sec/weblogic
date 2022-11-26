package utils;

import java.util.Vector;

public class MemoryTest {
   public static void main(String[] args) {
      int chunkSize = 1048576;
      int amountToAlloc = -1;
      if (args[0].equals("-m")) {
         amountToAlloc = Integer.parseInt(args[1]);
      }

      System.out.println("Timing GC");
      Vector v = new Vector();

      try {
         if (amountToAlloc == -1) {
            while(true) {
               v.addElement(new byte[chunkSize]);
            }
         }

         for(int i = 0; i < amountToAlloc; ++i) {
            v.addElement(new byte[chunkSize]);
         }
      } catch (OutOfMemoryError var8) {
      }

      v = null;
      long start = System.currentTimeMillis();
      Runtime.getRuntime().gc();
      double elapsed = (double)(System.currentTimeMillis() - start) / 1000.0;
      System.out.println("GC took " + elapsed + " seconds");
   }

   private void printUsage() {
      System.out.println("java -mx<maxHeap> utils.MemoryTest -mx<maxHeap>");
   }
}

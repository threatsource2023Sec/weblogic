package weblogic.store.io.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.internal.StoreStatisticsImpl;

public class FailureSimulator {
   private String storeName;
   private String directoryName;

   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: CorruptLastGeneration <store name> <store directory>");
      } else {
         FailureSimulator corrupter = new FailureSimulator(args[0], args[1]);
         corrupter.run();
      }
   }

   FailureSimulator(String storeName, String directoryName) {
      this.storeName = storeName;
      this.directoryName = directoryName;
   }

   void run() {
      long highestGeneration = 0L;
      ArrayList lastList = new ArrayList();
      System.out.println("Opening the heap");

      Heap heap;
      try {
         heap = new Heap(this.storeName, this.directoryName);
         heap.setSynchronousWritePolicy(StoreWritePolicy.CACHE_FLUSH);
         heap.open();
      } catch (PersistentStoreException var19) {
         var19.printStackTrace(System.err);
         return;
      }

      System.out.println("Reading all records from the heap");

      Heap.HeapRecord scribbleRec;
      try {
         do {
            scribbleRec = heap.recover();
            if (scribbleRec != null) {
               ByteBuffer buf = scribbleRec.getBody();
               if (buf.remaining() < 12) {
                  System.err.println("WARNING: A record was too short");
               } else {
                  long generation = buf.getLong();
                  if (generation == highestGeneration) {
                     lastList.add(scribbleRec);
                  } else if (generation > highestGeneration) {
                     highestGeneration = generation;
                     lastList.clear();
                     lastList.add(scribbleRec);
                  }
               }
            }
         } while(scribbleRec != null);
      } catch (PersistentStoreException var20) {
         var20.printStackTrace(System.err);
         return;
      }

      System.out.println("The last generation is " + highestGeneration);
      System.out.println("The contents are:");
      scribbleRec = null;
      Iterator i = lastList.iterator();

      while(i.hasNext()) {
         Heap.HeapRecord rec = (Heap.HeapRecord)i.next();
         scribbleRec = rec;
         System.out.println(Heap.handleToString(rec.getHandle()) + "  " + rec.getBody());
      }

      System.out.println("Closing the heap");

      try {
         heap.close();
      } catch (PersistentStoreException var18) {
         var18.printStackTrace(System.err);
         return;
      }

      System.out.println("Re-opening store files");
      StoreDir dir = new StoreDir(this.directoryName, this.storeName, "dat");

      try {
         DirectBufferPool pool = new DirectBufferPool(1048576, (StoreStatisticsImpl)null);
         dir.open(pool, false);
         short fileNum = StoreHeap.handleToFileNum(scribbleRec.getHandle());
         System.out.println("Accessing file " + fileNum);
         StoreFile file = dir.get(fileNum);
         file.open(StoreWritePolicy.CACHE_FLUSH);
         long startPos = (long)(StoreHeap.handleToFileBlock(scribbleRec.getHandle()) * heap.getInternalBlockSize());
         int length = StoreHeap.handleToNumBlocks(scribbleRec.getHandle()) * heap.getInternalBlockSize();
         long writePos = (long)((length - 50) / 2) + startPos;
         ByteBuffer scribbleBuf = ByteBuffer.allocate(4);
         scribbleBuf.putInt(57005);
         scribbleBuf.flip();
         System.out.println("Writing four bytes to file at position " + writePos);
         file.write(writePos, scribbleBuf);
         file.flush();
         System.out.println("Closing files.");
         dir.close();
      } catch (IOException var17) {
         var17.printStackTrace(System.err);
         return;
      }

      System.out.println("Success!");
   }
}

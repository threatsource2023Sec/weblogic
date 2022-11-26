package weblogic.store.io.file;

import java.nio.ByteBuffer;
import weblogic.store.StoreWritePolicy;
import weblogic.utils.Hex;

public final class ScanStore {
   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: ScanStore <store name> <store directory>");
      } else {
         try {
            Heap heap = new Heap(args[0], args[1]);
            heap.setSynchronousWritePolicy(StoreWritePolicy.DISABLED);
            heap.open();
            int version = heap.getHeapVersion();
            if (version == 1) {
               System.out.println("Opened pre-Diablo heap file");
            } else if (version == 2) {
               System.out.println("Opened Diablo or later version heap file but before 12.2.1");
            } else if (version == 3) {
               System.out.println("Opened a heap file from 12.2.1 or later");
            } else {
               System.out.println("Opened unknown heap version " + version);
            }

            Heap.HeapRecord rec;
            do {
               rec = heap.recover();
               if (rec != null) {
                  printRecord(rec);
               }
            } while(rec != null);

            heap.close();
         } catch (Exception var4) {
            var4.printStackTrace(System.err);
         }

      }
   }

   private static void printRecord(Heap.HeapRecord rec) {
      try {
         ByteBuffer body = rec.getBody();
         StringBuffer buf = new StringBuffer();
         buf.append(Heap.handleToString(rec.getHandle()));
         buf.append(": generation=");
         buf.append(body.getLong());
         buf.append(" numOps=");
         buf.append(body.getShort());
         System.out.println(buf.toString());

         while(true) {
            BaseStoreIO.Operation op;
            do {
               if (body.remaining() <= 0) {
                  return;
               }

               op = BaseStoreIO.Operation.read(body, true);
               System.out.println("  " + op);
               System.out.println("  typeCode = " + op.typeCode);
            } while(op.data == null);

            for(int i = 0; i < op.data.length; ++i) {
               printBuffer("Data[" + i + "]", op.data[i]);
            }
         }
      } catch (Exception var5) {
         var5.printStackTrace(System.err);
      }
   }

   private static void printBuffer(String hdr, ByteBuffer buf) {
      if (buf != null && buf.remaining() != 0) {
         byte[] array;
         int offset;
         int length;
         if (buf.hasArray()) {
            array = buf.array();
            offset = buf.arrayOffset();
            length = buf.remaining();
         } else {
            array = new byte[buf.remaining()];
            offset = 0;
            length = buf.remaining();
            ByteBuffer copyBuf = buf.duplicate();

            for(int inc = 0; inc < length; array[inc++] = copyBuf.get()) {
            }
         }

         System.out.println(hdr + ": (length = " + length + ")");
         System.out.println(Hex.dump(array, offset, length));
      }
   }
}

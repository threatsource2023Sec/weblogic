package weblogic.jms.common;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataInputStream;

public class PayloadFactoryImpl {
   static boolean REPLACE_STOLEN_CHUNKS = true;
   static int SHIFT_REPLACEMENT_STOLEN_CHUNK_COUNT = 0;
   static int CHUNK_LINK_THRESHOLD;

   static BufferOutputStream createOutputStream() {
      return new BufferOutputStreamChunked((ObjectIOBypass)null);
   }

   public static Payload createPayload(InputStream inputStream) throws IOException {
      int len = ((DataInput)inputStream).readInt();
      if (len > CHUNK_LINK_THRESHOLD) {
         ChunkedDataInputStream cdis;
         if (inputStream instanceof ChunkedDataInputStream) {
            cdis = (ChunkedDataInputStream)inputStream;
            return new PayloadChunkBase(PayloadChunkBase.linkAndCopyChunksWithoutWastedMemory(cdis, len));
         }

         if (inputStream instanceof BufferInputStreamChunked) {
            cdis = ((BufferInputStreamChunked)inputStream).getInternalCDIS();
            return new PayloadChunkBase(PayloadChunkBase.linkAndCopyChunksWithoutWastedMemory(cdis, len));
         }
      }

      return copyPayloadFromStream(inputStream, len);
   }

   static final Payload copyPayloadFromStream(InputStream inputStream, int length) throws IOException {
      Chunk first = PayloadChunkBase.copyIntoSharedChunks(inputStream, length);
      return new PayloadChunkBase(first);
   }

   static final Payload convertObjectToPayload(Object object) throws IOException {
      BufferOutputStream bos = createOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(object);
      bos.flush();
      return bos.moveToPayload();
   }

   static {
      CHUNK_LINK_THRESHOLD = Chunk.CHUNK_SIZE * 9;

      String prop;
      Integer i;
      int val;
      String display;
      try {
         prop = "weblogic.jms.CHUNK_LINK_THRESHOLD";
         i = Integer.getInteger(prop);
         if (i != null && CHUNK_LINK_THRESHOLD != (val = i)) {
            CHUNK_LINK_THRESHOLD = val;
            display = prop + "=" + CHUNK_LINK_THRESHOLD;
            System.out.println(display);
         }
      } catch (Throwable var6) {
      }

      try {
         prop = "weblogic.jms.addChunkPool";
         String noPool = "noPool";
         String tc = System.getProperty("weblogic.jms.addChunkPool");
         if (tc != null && "noPool".equals(tc)) {
            display = "PayloadFactoryImpl weblogic.jms.addChunkPool";
            REPLACE_STOLEN_CHUNKS = false;
            display = display + " do NOT proactively refill chunk pool!!";
            System.out.println(display);
         }
      } catch (Throwable var5) {
      }

      try {
         prop = "weblogic.jms.ReplaceChunkPoolShift";
         i = Integer.getInteger("weblogic.jms.ReplaceChunkPoolShift");
         if (i != null && SHIFT_REPLACEMENT_STOLEN_CHUNK_COUNT != (val = i)) {
            SHIFT_REPLACEMENT_STOLEN_CHUNK_COUNT = val;
            display = "PayloadFactoryImpl weblogic.jms.ReplaceChunkPoolShift";
            display = display + "= " + SHIFT_REPLACEMENT_STOLEN_CHUNK_COUNT + " value";
            System.out.println(display);
         }
      } catch (Throwable var4) {
      }

   }
}

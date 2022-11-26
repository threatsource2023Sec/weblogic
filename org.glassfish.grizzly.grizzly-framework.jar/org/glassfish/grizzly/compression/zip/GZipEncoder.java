package org.glassfish.grizzly.compression.zip;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.MemoryManager;

public class GZipEncoder extends AbstractTransformer {
   private static final int GZIP_MAGIC = 35615;
   private static final int TRAILER_SIZE = 8;
   private final int bufferSize;
   private static final Buffer header;

   public GZipEncoder() {
      this(512);
   }

   public GZipEncoder(int bufferSize) {
      this.bufferSize = bufferSize;
   }

   public String getName() {
      return "gzip-encoder";
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input.hasRemaining();
   }

   protected GZipOutputState createStateObject() {
      return new GZipOutputState();
   }

   protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
      MemoryManager memoryManager = this.obtainMemoryManager(storage);
      GZipOutputState state = (GZipOutputState)this.obtainStateObject(storage);
      if (!state.isInitialized) {
         state.initialize();
      }

      Buffer encodedBuffer = null;
      if (input != null && input.hasRemaining()) {
         encodedBuffer = this.encodeBuffer(input, state, memoryManager);
      }

      if (encodedBuffer == null) {
         return TransformationResult.createIncompletedResult((Object)null);
      } else {
         if (!state.isHeaderWritten) {
            state.isHeaderWritten = true;
            encodedBuffer = Buffers.appendBuffers(memoryManager, this.getHeader(), encodedBuffer);
         }

         return TransformationResult.createCompletedResult(encodedBuffer, (Object)null);
      }
   }

   public Buffer finish(AttributeStorage storage) {
      MemoryManager memoryManager = this.obtainMemoryManager(storage);
      GZipOutputState state = (GZipOutputState)this.obtainStateObject(storage);
      Buffer resultBuffer = null;
      if (state.isInitialized) {
         Deflater deflater = state.deflater;
         if (!deflater.finished()) {
            deflater.finish();

            while(!deflater.finished()) {
               resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, this.deflate(deflater, memoryManager));
            }

            if (!state.isHeaderWritten) {
               state.isHeaderWritten = true;
               resultBuffer = Buffers.appendBuffers(memoryManager, this.getHeader(), resultBuffer);
            }

            Buffer trailer = memoryManager.allocate(8);
            CRC32 crc32 = state.crc32;
            putUInt(trailer, (int)crc32.getValue());
            putUInt(trailer, deflater.getTotalIn());
            trailer.flip();
            resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, trailer);
         }

         state.reset();
      }

      return resultBuffer;
   }

   private Buffer getHeader() {
      Buffer headerToWrite = header.duplicate();
      headerToWrite.allowBufferDispose(false);
      return headerToWrite;
   }

   private Buffer encodeBuffer(Buffer buffer, GZipOutputState state, MemoryManager memoryManager) {
      CRC32 crc32 = state.crc32;
      Deflater deflater = state.deflater;
      if (deflater.finished()) {
         throw new IllegalStateException("write beyond end of stream");
      } else {
         int stride = this.bufferSize;
         Buffer resultBuffer = null;
         ByteBufferArray byteBufferArray = buffer.toByteBufferArray();
         ByteBuffer[] buffers = (ByteBuffer[])byteBufferArray.getArray();
         int size = byteBufferArray.size();

         for(int i = 0; i < size; ++i) {
            ByteBuffer byteBuffer = buffers[i];
            int len = byteBuffer.remaining();
            if (len > 0) {
               byte[] buf;
               int off;
               if (byteBuffer.hasArray()) {
                  buf = byteBuffer.array();
                  off = byteBuffer.arrayOffset() + byteBuffer.position();
               } else {
                  buf = new byte[len];
                  off = 0;
                  byteBuffer.get(buf);
                  byteBuffer.position(byteBuffer.position() - len);
               }

               for(int j = 0; j < len; j += stride) {
                  deflater.setInput(buf, off + j, Math.min(stride, len - j));

                  while(!deflater.needsInput()) {
                     Buffer deflated = this.deflate(deflater, memoryManager);
                     if (deflated != null) {
                        resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, deflated);
                     }
                  }
               }

               crc32.update(buf, off, len);
            }
         }

         byteBufferArray.restore();
         byteBufferArray.recycle();
         buffer.position(buffer.limit());
         return resultBuffer;
      }
   }

   protected Buffer deflate(Deflater deflater, MemoryManager memoryManager) {
      Buffer buffer = memoryManager.allocate(this.bufferSize);
      ByteBuffer byteBuffer = buffer.toByteBuffer();
      byte[] array = byteBuffer.array();
      int offset = byteBuffer.arrayOffset() + byteBuffer.position();
      int len = deflater.deflate(array, offset, this.bufferSize);
      if (len <= 0) {
         buffer.dispose();
         return null;
      } else {
         buffer.position(len);
         buffer.trim();
         return buffer;
      }
   }

   private static void putUInt(Buffer buffer, int value) {
      putUShort(buffer, value & '\uffff');
      putUShort(buffer, value >> 16 & '\uffff');
   }

   private static void putUShort(Buffer buffer, int value) {
      buffer.put((byte)(value & 255));
      buffer.put((byte)(value >> 8 & 255));
   }

   static {
      header = MemoryManager.DEFAULT_MEMORY_MANAGER.allocate(10);
      header.put((byte)31);
      header.put((byte)-117);
      header.put((byte)8);
      header.put((byte)0);
      header.put((byte)0);
      header.put((byte)0);
      header.put((byte)0);
      header.put((byte)0);
      header.put((byte)0);
      header.put((byte)0);
      header.flip();
   }

   protected static final class GZipOutputState extends AbstractTransformer.LastResultAwareState {
      private boolean isInitialized;
      private boolean isHeaderWritten;
      private CRC32 crc32;
      private Deflater deflater;

      private void initialize() {
         Deflater newDeflater = new Deflater(-1, true);
         CRC32 newCrc32 = new CRC32();
         newCrc32.reset();
         this.deflater = newDeflater;
         this.crc32 = newCrc32;
         this.isInitialized = true;
      }

      private void reset() {
         this.isInitialized = false;
         this.isHeaderWritten = false;
         this.deflater.end();
         this.crc32 = null;
         this.deflater = null;
      }
   }
}

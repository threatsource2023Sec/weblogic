package org.glassfish.grizzly.compression.zip;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.MemoryManager;

public class GZipDecoder extends AbstractTransformer {
   private static final int GZIP_MAGIC = 35615;
   private static final int FTEXT = 1;
   private static final int FHCRC = 2;
   private static final int FEXTRA = 4;
   private static final int FNAME = 8;
   private static final int FCOMMENT = 16;
   private final int bufferSize;

   public GZipDecoder() {
      this(512);
   }

   public GZipDecoder(int bufferSize) {
      this.bufferSize = bufferSize;
   }

   public String getName() {
      return "gzip-decoder";
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input.hasRemaining();
   }

   protected GZipInputState createStateObject() {
      return new GZipInputState();
   }

   protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
      MemoryManager memoryManager = this.obtainMemoryManager(storage);
      GZipInputState state = (GZipInputState)this.obtainStateObject(storage);
      if (!state.isInitialized() && !this.initializeInput(input, state)) {
         return TransformationResult.createIncompletedResult(input);
      } else {
         Buffer decodedBuffer = null;
         if (state.getDecodeStatus() == GZipDecoder.DecodeStatus.PAYLOAD && input.hasRemaining()) {
            decodedBuffer = this.decodeBuffer(memoryManager, input, state);
         }

         if (state.getDecodeStatus() == GZipDecoder.DecodeStatus.TRAILER && input.hasRemaining() && this.decodeTrailer(input, state)) {
            state.setDecodeStatus(GZipDecoder.DecodeStatus.DONE);
            state.setInitialized(false);
         }

         boolean hasRemainder = input.hasRemaining();
         return decodedBuffer != null && decodedBuffer.hasRemaining() ? TransformationResult.createCompletedResult(decodedBuffer, hasRemainder ? input : null) : TransformationResult.createIncompletedResult(hasRemainder ? input : null);
      }
   }

   private Buffer decodeBuffer(MemoryManager memoryManager, Buffer buffer, GZipInputState state) {
      Inflater inflater = state.getInflater();
      CRC32 inCrc32 = state.getCrc32();
      ByteBufferArray byteBufferArray = buffer.toByteBufferArray();
      ByteBuffer[] byteBuffers = (ByteBuffer[])byteBufferArray.getArray();
      int size = byteBufferArray.size();
      Buffer resultBuffer = null;

      for(int i = 0; i < size; ++i) {
         ByteBuffer byteBuffer = byteBuffers[i];
         int len = byteBuffer.remaining();
         byte[] array;
         int offset;
         if (byteBuffer.hasArray()) {
            array = byteBuffer.array();
            offset = byteBuffer.arrayOffset() + byteBuffer.position();
         } else {
            array = new byte[len];
            offset = 0;
            byteBuffer.get(array);
            byteBuffer.position(byteBuffer.position() - len);
         }

         inflater.setInput(array, offset, len);

         int lastInflated;
         do {
            Buffer decodedBuffer = memoryManager.allocate(this.bufferSize);
            ByteBuffer decodedBB = decodedBuffer.toByteBuffer();
            byte[] decodedArray = decodedBB.array();
            int decodedArrayOffs = decodedBB.arrayOffset() + decodedBB.position();

            try {
               lastInflated = inflater.inflate(decodedArray, decodedArrayOffs, this.bufferSize);
            } catch (DataFormatException var22) {
               decodedBuffer.dispose();
               String s = var22.getMessage();
               throw new IllegalStateException(s != null ? s : "Invalid ZLIB data format");
            }

            if (lastInflated > 0) {
               inCrc32.update(decodedArray, decodedArrayOffs, lastInflated);
               decodedBuffer.position(lastInflated);
               decodedBuffer.trim();
               resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, decodedBuffer);
            } else {
               decodedBuffer.dispose();
               if (inflater.finished() || inflater.needsDictionary()) {
                  int remainder = inflater.getRemaining();
                  int remaining = byteBuffer.remaining();
                  byteBufferArray.restore();
                  byteBufferArray.recycle();
                  buffer.position(buffer.position() + remaining - remainder);
                  state.setDecodeStatus(GZipDecoder.DecodeStatus.TRAILER);
                  return resultBuffer;
               }
            }
         } while(lastInflated > 0);

         int remaining = byteBuffer.remaining();
         byteBufferArray.restore();
         byteBufferArray.recycle();
         buffer.position(buffer.position() + remaining);
      }

      return resultBuffer;
   }

   private boolean initializeInput(Buffer buffer, GZipInputState state) {
      Inflater inflater = state.getInflater();
      if (inflater == null) {
         inflater = new Inflater(true);
         CRC32 crc32 = new CRC32();
         crc32.reset();
         state.setInflater(inflater);
         state.setCrc32(crc32);
      } else if (state.getDecodeStatus() == GZipDecoder.DecodeStatus.DONE) {
         state.setDecodeStatus(GZipDecoder.DecodeStatus.INITIAL);
         inflater.reset();
         state.getCrc32().reset();
      }

      if (!this.parseHeader(buffer, state)) {
         return false;
      } else {
         state.getCrc32().reset();
         state.setInitialized(true);
         return true;
      }
   }

   private boolean parseHeader(Buffer buffer, GZipInputState state) {
      CRC32 crc32 = state.getCrc32();

      while(true) {
         DecodeStatus decodeStatus;
         while((decodeStatus = state.getDecodeStatus()) != GZipDecoder.DecodeStatus.PAYLOAD) {
            int myCrc;
            boolean found;
            switch (decodeStatus) {
               case INITIAL:
                  if (buffer.remaining() < 10) {
                     return false;
                  }

                  if (getUShort(buffer, crc32) != 35615) {
                     throw new IllegalStateException("Not in GZIP format");
                  }

                  if (getUByte(buffer, crc32) != 8) {
                     throw new IllegalStateException("Unsupported compression method");
                  }

                  myCrc = getUByte(buffer, crc32);
                  state.setHeaderFlag(myCrc);
                  skipBytes(buffer, 6, crc32);
                  state.setDecodeStatus(GZipDecoder.DecodeStatus.FEXTRA1);
               case FEXTRA1:
                  if ((state.getHeaderFlag() & 4) != 4) {
                     state.setDecodeStatus(GZipDecoder.DecodeStatus.FNAME);
                     continue;
                  }

                  if (buffer.remaining() < 2) {
                     return false;
                  }

                  state.setHeaderParseStateValue(getUShort(buffer, crc32));
                  state.setDecodeStatus(GZipDecoder.DecodeStatus.FEXTRA2);
               case FEXTRA2:
                  myCrc = state.getHeaderParseStateValue();
                  if (buffer.remaining() < myCrc) {
                     return false;
                  }

                  skipBytes(buffer, myCrc, crc32);
                  state.setHeaderParseStateValue(0);
                  state.setDecodeStatus(GZipDecoder.DecodeStatus.FNAME);
               case FNAME:
                  if ((state.getHeaderFlag() & 8) == 8) {
                     found = false;

                     while(buffer.hasRemaining()) {
                        if (getUByte(buffer, crc32) == 0) {
                           found = true;
                           break;
                        }
                     }

                     if (!found) {
                        return false;
                     }
                  }

                  state.setDecodeStatus(GZipDecoder.DecodeStatus.FCOMMENT);
               case FCOMMENT:
                  if ((state.getHeaderFlag() & 16) == 16) {
                     found = false;

                     while(buffer.hasRemaining()) {
                        if (getUByte(buffer, crc32) == 0) {
                           found = true;
                           break;
                        }
                     }

                     if (!found) {
                        return false;
                     }
                  }

                  state.setDecodeStatus(GZipDecoder.DecodeStatus.FHCRC);
               case FHCRC:
                  break;
               default:
                  continue;
            }

            if ((state.getHeaderFlag() & 2) == 2) {
               if (buffer.remaining() < 2) {
                  return false;
               }

               myCrc = (int)state.getCrc32().getValue() & '\uffff';
               int passedCrc = getUShort(buffer, crc32);
               if (myCrc != passedCrc) {
                  throw new IllegalStateException("Corrupt GZIP header");
               }
            }

            state.setDecodeStatus(GZipDecoder.DecodeStatus.PAYLOAD);
         }

         return true;
      }
   }

   private boolean decodeTrailer(Buffer buffer, GZipInputState state) throws TransformationException {
      if (buffer.remaining() < 8) {
         return false;
      } else {
         Inflater inflater = state.getInflater();
         CRC32 crc32 = state.getCrc32();
         long inCrc32Value = crc32.getValue();
         if (getUInt(buffer, crc32) == inCrc32Value && getUInt(buffer, crc32) == (inflater.getBytesWritten() & 4294967295L)) {
            return true;
         } else {
            throw new TransformationException("Corrupt GZIP trailer");
         }
      }
   }

   private static long getUInt(Buffer buffer, CRC32 crc32) {
      int short1 = getUShort(buffer, crc32);
      int short2 = getUShort(buffer, crc32);
      return (long)short2 << 16 | (long)short1;
   }

   private static int getUShort(Buffer buffer, CRC32 crc32) {
      int b1 = getUByte(buffer, crc32);
      int b2 = getUByte(buffer, crc32);
      return b2 << 8 | b1;
   }

   private static int getUByte(Buffer buffer, CRC32 crc32) {
      byte b = buffer.get();
      crc32.update(b);
      return b & 255;
   }

   private static void skipBytes(Buffer buffer, int num, CRC32 crc32) {
      for(int i = 0; i < num; ++i) {
         getUByte(buffer, crc32);
      }

   }

   protected static final class GZipInputState extends AbstractTransformer.LastResultAwareState {
      private boolean isInitialized;
      private CRC32 crc32;
      private Inflater inflater;
      private DecodeStatus decodeStatus;
      private int headerFlag;
      private int headerParseStateValue;

      protected GZipInputState() {
         this.decodeStatus = GZipDecoder.DecodeStatus.INITIAL;
      }

      public boolean isInitialized() {
         return this.isInitialized;
      }

      public void setInitialized(boolean isInitialized) {
         this.isInitialized = isInitialized;
      }

      public Inflater getInflater() {
         return this.inflater;
      }

      public void setInflater(Inflater inflater) {
         this.inflater = inflater;
      }

      public CRC32 getCrc32() {
         return this.crc32;
      }

      public void setCrc32(CRC32 crc32) {
         this.crc32 = crc32;
      }

      public DecodeStatus getDecodeStatus() {
         return this.decodeStatus;
      }

      public void setDecodeStatus(DecodeStatus decodeStatus) {
         this.decodeStatus = decodeStatus;
      }

      public int getHeaderFlag() {
         return this.headerFlag;
      }

      public void setHeaderFlag(int headerFlag) {
         this.headerFlag = headerFlag;
      }

      public int getHeaderParseStateValue() {
         return this.headerParseStateValue;
      }

      public void setHeaderParseStateValue(int headerParseStateValue) {
         this.headerParseStateValue = headerParseStateValue;
      }
   }

   protected static enum DecodeStatus {
      INITIAL,
      FEXTRA1,
      FEXTRA2,
      FNAME,
      FCOMMENT,
      FHCRC,
      PAYLOAD,
      TRAILER,
      DONE;
   }
}

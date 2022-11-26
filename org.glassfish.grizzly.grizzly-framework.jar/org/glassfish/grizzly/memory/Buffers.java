package org.glassfish.grizzly.memory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;

public class Buffers {
   private static final Logger LOGGER = Grizzly.logger(Buffers.class);
   private static final Appender APPENDER_DISPOSABLE = new BuffersAppender(true);
   private static final Appender APPENDER_NOT_DISPOSABLE = new BuffersAppender(false);
   public static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0);
   public static final ByteBuffer[] EMPTY_BYTE_BUFFER_ARRAY = new ByteBuffer[0];
   public static final Buffer EMPTY_BUFFER = new ByteBufferWrapper(ByteBuffer.allocate(0)) {
      public void dispose() {
      }
   };

   public static Appender getBufferAppender(boolean isCompositeBufferDisposable) {
      return isCompositeBufferDisposable ? APPENDER_DISPOSABLE : APPENDER_NOT_DISPOSABLE;
   }

   public static Buffer wrap(MemoryManager memoryManager, String s) {
      return wrap(memoryManager, s, Charset.defaultCharset());
   }

   public static Buffer wrap(MemoryManager memoryManager, String s, Charset charset) {
      try {
         byte[] byteRepresentation = s.getBytes(charset.name());
         return wrap(memoryManager, byteRepresentation);
      } catch (UnsupportedEncodingException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public static Buffer wrap(MemoryManager memoryManager, byte[] array) {
      return wrap(memoryManager, array, 0, array.length);
   }

   public static Buffer wrap(MemoryManager memoryManager, byte[] array, int offset, int length) {
      if (memoryManager == null) {
         memoryManager = getDefaultMemoryManager();
      }

      if (memoryManager instanceof WrapperAware) {
         return ((WrapperAware)memoryManager).wrap(array, offset, length);
      } else {
         Buffer buffer = memoryManager.allocate(length);
         buffer.put(array, offset, length);
         buffer.flip();
         return buffer;
      }
   }

   public static Buffer wrap(MemoryManager memoryManager, ByteBuffer byteBuffer) {
      if (memoryManager instanceof WrapperAware) {
         return ((WrapperAware)memoryManager).wrap(byteBuffer);
      } else if (byteBuffer.hasArray()) {
         return wrap(memoryManager, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
      } else {
         throw new IllegalStateException("Can not wrap ByteBuffer");
      }
   }

   public static ByteBuffer slice(ByteBuffer chunk, int size) {
      chunk.limit(chunk.position() + size);
      ByteBuffer view = chunk.slice();
      chunk.position(chunk.limit());
      chunk.limit(chunk.capacity());
      return view;
   }

   public static ByteBuffer slice(ByteBuffer byteBuffer, int position, int limit) {
      int oldPos = byteBuffer.position();
      int oldLimit = byteBuffer.limit();
      setPositionLimit(byteBuffer, position, limit);
      ByteBuffer slice = byteBuffer.slice();
      setPositionLimit(byteBuffer, oldPos, oldLimit);
      return slice;
   }

   public static String toStringContent(ByteBuffer byteBuffer, Charset charset, int position, int limit) {
      if (charset == null) {
         charset = Charset.defaultCharset();
      }

      int oldPosition = byteBuffer.position();
      int oldLimit = byteBuffer.limit();
      setPositionLimit(byteBuffer, position, limit);

      String var6;
      try {
         var6 = charset.decode(byteBuffer).toString();
      } finally {
         setPositionLimit(byteBuffer, oldPosition, oldLimit);
      }

      return var6;
   }

   public static void setPositionLimit(Buffer buffer, int position, int limit) {
      buffer.limit(limit);
      buffer.position(position);
   }

   public static void setPositionLimit(ByteBuffer buffer, int position, int limit) {
      buffer.limit(limit);
      buffer.position(position);
   }

   public static void put(ByteBuffer srcBuffer, int srcOffset, int length, ByteBuffer dstBuffer) {
      if (dstBuffer.remaining() < length) {
         LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_BUFFERS_OVERFLOW_EXCEPTION(srcBuffer, srcOffset, length, dstBuffer));
         throw new BufferOverflowException();
      } else {
         if (srcBuffer.hasArray() && dstBuffer.hasArray()) {
            System.arraycopy(srcBuffer.array(), srcBuffer.arrayOffset() + srcOffset, dstBuffer.array(), dstBuffer.arrayOffset() + dstBuffer.position(), length);
            dstBuffer.position(dstBuffer.position() + length);
         } else {
            for(int i = srcOffset; i < srcOffset + length; ++i) {
               dstBuffer.put(srcBuffer.get(i));
            }
         }

      }
   }

   public static void put(Buffer src, int position, int length, Buffer dstBuffer) {
      if (dstBuffer.remaining() < length) {
         throw new BufferOverflowException();
      } else {
         if (!src.isComposite()) {
            ByteBuffer srcByteBuffer = src.toByteBuffer();
            if (srcByteBuffer.hasArray()) {
               dstBuffer.put(srcByteBuffer.array(), srcByteBuffer.arrayOffset() + position, length);
            } else {
               for(int i = 0; i < length; ++i) {
                  dstBuffer.put(srcByteBuffer.get(position + i));
               }
            }
         } else {
            ByteBufferArray array = src.toByteBufferArray(position, position + length);
            ByteBuffer[] srcByteBuffers = (ByteBuffer[])array.getArray();

            for(int i = 0; i < array.size(); ++i) {
               ByteBuffer srcByteBuffer = srcByteBuffers[i];
               int initialPosition = srcByteBuffer.position();
               int srcByteBufferLen = srcByteBuffer.remaining();
               if (srcByteBuffer.hasArray()) {
                  dstBuffer.put(srcByteBuffer.array(), srcByteBuffer.arrayOffset() + initialPosition, srcByteBufferLen);
               } else {
                  for(int j = 0; j < srcByteBufferLen; ++i) {
                     dstBuffer.put(srcByteBuffer.get(initialPosition + j));
                  }
               }
            }

            array.restore();
            array.recycle();
         }

      }
   }

   public static void get(ByteBuffer srcBuffer, byte[] dstBytes, int dstOffset, int length) {
      if (srcBuffer.hasArray()) {
         if (length > srcBuffer.remaining()) {
            throw new BufferUnderflowException();
         }

         System.arraycopy(srcBuffer.array(), srcBuffer.arrayOffset() + srcBuffer.position(), dstBytes, dstOffset, length);
         srcBuffer.position(srcBuffer.position() + length);
      } else {
         srcBuffer.get(dstBytes, dstOffset, length);
      }

   }

   public static void put(byte[] srcBytes, int srcOffset, int length, ByteBuffer dstBuffer) {
      if (dstBuffer.hasArray()) {
         if (length > dstBuffer.remaining()) {
            throw new BufferOverflowException();
         }

         System.arraycopy(srcBytes, srcOffset, dstBuffer.array(), dstBuffer.arrayOffset() + dstBuffer.position(), length);
         dstBuffer.position(dstBuffer.position() + length);
      } else {
         dstBuffer.put(srcBytes, srcOffset, length);
      }

   }

   public static Buffer appendBuffers(MemoryManager memoryManager, Buffer buffer1, Buffer buffer2) {
      return appendBuffers(memoryManager, buffer1, buffer2, false);
   }

   public static Buffer appendBuffers(MemoryManager memoryManager, Buffer buffer1, Buffer buffer2, boolean isCompositeBufferDisposable) {
      if (buffer1 == null) {
         return buffer2;
      } else if (buffer2 == null) {
         return buffer1;
      } else {
         if (buffer1.order() != buffer2.order()) {
            LOGGER.fine("Appending buffers with different ByteOrder.The result Buffer's order will be the same as the first Buffer's ByteOrder");
            buffer2.order(buffer1.order());
         }

         if (buffer1.isComposite() && buffer1.capacity() == buffer1.limit()) {
            ((CompositeBuffer)buffer1).append(buffer2);
            return buffer1;
         } else if (buffer2.isComposite() && buffer2.position() == 0) {
            ((CompositeBuffer)buffer2).prepend(buffer1);
            return buffer2;
         } else {
            CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer(memoryManager);
            compositeBuffer.order(buffer1.order());
            compositeBuffer.append(buffer1);
            compositeBuffer.append(buffer2);
            compositeBuffer.allowBufferDispose(isCompositeBufferDisposable);
            return compositeBuffer;
         }
      }
   }

   public static void fill(Buffer buffer, byte b) {
      fill(buffer, buffer.position(), buffer.limit(), b);
   }

   public static void fill(Buffer buffer, int position, int limit, byte b) {
      if (!buffer.isComposite()) {
         ByteBuffer byteBuffer = buffer.toByteBuffer();
         fill(byteBuffer, position, limit, b);
      } else {
         ByteBufferArray array = buffer.toByteBufferArray(position, limit);
         ByteBuffer[] byteBuffers = (ByteBuffer[])array.getArray();
         int size = array.size();

         for(int i = 0; i < size; ++i) {
            ByteBuffer byteBuffer = byteBuffers[i];
            fill(byteBuffer, b);
         }

         array.restore();
         array.recycle();
      }

   }

   public static void fill(ByteBuffer byteBuffer, byte b) {
      fill(byteBuffer, byteBuffer.position(), byteBuffer.limit(), b);
   }

   public static void fill(ByteBuffer byteBuffer, int position, int limit, byte b) {
      int i;
      if (byteBuffer.hasArray()) {
         i = byteBuffer.arrayOffset();
         Arrays.fill(byteBuffer.array(), i + position, i + limit, b);
      } else {
         for(i = position; i < limit; ++i) {
            byteBuffer.put(i, b);
         }
      }

   }

   public static Buffer cloneBuffer(Buffer srcBuffer) {
      return cloneBuffer(srcBuffer, srcBuffer.position(), srcBuffer.limit());
   }

   public static Buffer cloneBuffer(Buffer srcBuffer, int position, int limit) {
      int srcLength = limit - position;
      if (srcLength == 0) {
         return wrap(getDefaultMemoryManager(), EMPTY_BYTE_BUFFER);
      } else {
         Buffer clone = getDefaultMemoryManager().allocate(srcLength);
         clone.put(srcBuffer, position, srcLength);
         clone.order(srcBuffer.order());
         return clone.flip();
      }
   }

   public static long readFromFileChannel(FileChannel fileChannel, Buffer buffer) throws IOException {
      long bytesRead;
      if (!buffer.isComposite()) {
         ByteBuffer bb = buffer.toByteBuffer();
         int oldPos = bb.position();
         bytesRead = (long)fileChannel.read(bb);
         bb.position(oldPos);
      } else {
         ByteBufferArray array = buffer.toByteBufferArray();
         bytesRead = fileChannel.read((ByteBuffer[])array.getArray(), 0, array.size());
         array.restore();
         array.recycle();
      }

      if (bytesRead > 0L) {
         buffer.position(buffer.position() + (int)bytesRead);
      }

      return bytesRead;
   }

   public static long writeToFileChannel(FileChannel fileChannel, Buffer buffer) throws IOException {
      long bytesWritten;
      if (!buffer.isComposite()) {
         ByteBuffer bb = buffer.toByteBuffer();
         int oldPos = bb.position();
         bytesWritten = (long)fileChannel.write(bb);
         bb.position(oldPos);
      } else {
         ByteBufferArray array = buffer.toByteBufferArray();
         bytesWritten = fileChannel.write((ByteBuffer[])array.getArray(), 0, array.size());
         array.restore();
         array.recycle();
      }

      if (bytesWritten > 0L) {
         buffer.position(buffer.position() + (int)bytesWritten);
      }

      return bytesWritten;
   }

   public String toStringContent(Buffer buffer, int headBytesCount, int tailBytesCount) {
      return buffer == null ? null : this.toStringContent(buffer, headBytesCount, tailBytesCount, Charset.defaultCharset());
   }

   public String toStringContent(Buffer buffer, int headBytesCount, int tailBytesCount, Charset charset) {
      if (buffer == null) {
         return null;
      } else if (headBytesCount >= 0 && tailBytesCount >= 0) {
         String toString = buffer.toString();
         StringBuilder sb = new StringBuilder(toString.length() + headBytesCount + tailBytesCount + 5);
         sb.append(toString);
         if (buffer.remaining() <= headBytesCount + tailBytesCount) {
            sb.append('[').append(buffer.toStringContent(charset)).append(']');
         } else {
            sb.append('[');
            if (headBytesCount > 0) {
               sb.append(buffer.toStringContent(charset, buffer.position(), buffer.position() + headBytesCount));
            }

            sb.append("...");
            if (tailBytesCount > 0) {
               sb.append(buffer.toStringContent(charset, buffer.limit() - tailBytesCount, buffer.limit()));
            }

            sb.append(']');
         }

         return sb.toString();
      } else {
         throw new IllegalArgumentException("count can't be negative");
      }
   }

   public static void dumpBuffer(Appendable appendable, Buffer buffer) {
      Formatter formatter = new Formatter(appendable);
      dumpBuffer0(formatter, appendable, buffer);
   }

   private static void dumpBuffer0(Formatter formatter, Appendable appendable, Buffer buffer) {
      if (buffer.isComposite()) {
         BufferArray bufferArray = buffer.toBufferArray();
         int size = bufferArray.size();
         Buffer[] buffers = (Buffer[])bufferArray.getArray();
         formatter.format("%s\n", buffer.toString());

         for(int i = 0; i < size; ++i) {
            dumpBuffer0(formatter, appendable, buffers[i]);
         }

         formatter.format("End CompositeBuffer (%d)", System.identityHashCode(buffer));
      } else {
         dumpBuffer0(formatter, buffer);
      }

   }

   private static void dumpBuffer0(Formatter formatter, Buffer buffer) {
      formatter.format("%s\n", buffer.toString());
      int line = 0;
      int remaining = 0;

      int i;
      int aIdx;
      byte b;
      for(int len = buffer.remaining() / 16; remaining < len; line += 16) {
         i = buffer.get(line);
         aIdx = buffer.get(line + 1);
         b = buffer.get(line + 2);
         byte b3 = buffer.get(line + 3);
         byte b4 = buffer.get(line + 4);
         byte b5 = buffer.get(line + 5);
         byte b6 = buffer.get(line + 6);
         byte b7 = buffer.get(line + 7);
         byte b8 = buffer.get(line + 8);
         byte b9 = buffer.get(line + 9);
         byte b10 = buffer.get(line + 10);
         byte b11 = buffer.get(line + 11);
         byte b12 = buffer.get(line + 12);
         byte b13 = buffer.get(line + 13);
         byte b14 = buffer.get(line + 14);
         byte b15 = buffer.get(line + 15);
         formatter.format(Buffers.DumpStrings.DUMP_STRINGS[15], line, Byte.valueOf((byte)i), Byte.valueOf((byte)aIdx), b, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, getChar(i), getChar(aIdx), getChar(b), getChar(b3), getChar(b4), getChar(b5), getChar(b6), getChar(b7), getChar(b8), getChar(b9), getChar(b10), getChar(b11), getChar(b12), getChar(b13), getChar(b14), getChar(b15));
         ++remaining;
      }

      remaining = buffer.remaining() % 16;
      if (remaining > 0) {
         Object[] args = new Object[(remaining << 1) + 1];
         args[0] = remaining + line;
         i = 0;

         for(aIdx = 1; i < remaining; ++aIdx) {
            b = buffer.get(line + i);
            args[aIdx] = b & 255;
            args[aIdx + remaining] = getChar(b);
            ++i;
         }

         formatter.format(Buffers.DumpStrings.DUMP_STRINGS[remaining - 1], args);
      }

   }

   private static char getChar(int val) {
      char c = (char)val;
      return !Character.isWhitespace(c) && !Character.isISOControl(c) ? c : '.';
   }

   private static MemoryManager getDefaultMemoryManager() {
      return MemoryManager.DEFAULT_MEMORY_MANAGER;
   }

   private static final class DumpStrings {
      private static final String[] DUMP_STRINGS = new String[]{"%10d   %02x                                                         %c\n", "%10d   %02x %02x                                                      %c%c\n", "%10d   %02x %02x  %02x                                                  %c%c%c\n", "%10d   %02x %02x  %02x %02x                                               %c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x                                           %c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x                                        %c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x                                    %c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x                                 %c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x                           %c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x                        %c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x                    %c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x                 %c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x             %c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x          %c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x  %02x      %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x  %02x %02x   %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n"};
   }

   private static class BuffersAppender implements Appender {
      private final boolean isCompositeBufferDisposable;

      public BuffersAppender(boolean isCompositeBufferDisposable) {
         this.isCompositeBufferDisposable = isCompositeBufferDisposable;
      }

      public Buffer append(Buffer element1, Buffer element2) {
         return Buffers.appendBuffers((MemoryManager)null, element1, element2, this.isCompositeBufferDisposable);
      }
   }
}

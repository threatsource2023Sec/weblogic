package org.python.netty.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.python.netty.util.internal.PlatformDependent;

public final class Unpooled {
   private static final ByteBufAllocator ALLOC;
   public static final ByteOrder BIG_ENDIAN;
   public static final ByteOrder LITTLE_ENDIAN;
   public static final ByteBuf EMPTY_BUFFER;

   public static ByteBuf buffer() {
      return ALLOC.heapBuffer();
   }

   public static ByteBuf directBuffer() {
      return ALLOC.directBuffer();
   }

   public static ByteBuf buffer(int initialCapacity) {
      return ALLOC.heapBuffer(initialCapacity);
   }

   public static ByteBuf directBuffer(int initialCapacity) {
      return ALLOC.directBuffer(initialCapacity);
   }

   public static ByteBuf buffer(int initialCapacity, int maxCapacity) {
      return ALLOC.heapBuffer(initialCapacity, maxCapacity);
   }

   public static ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
      return ALLOC.directBuffer(initialCapacity, maxCapacity);
   }

   public static ByteBuf wrappedBuffer(byte[] array) {
      return (ByteBuf)(array.length == 0 ? EMPTY_BUFFER : new UnpooledHeapByteBuf(ALLOC, array, array.length));
   }

   public static ByteBuf wrappedBuffer(byte[] array, int offset, int length) {
      if (length == 0) {
         return EMPTY_BUFFER;
      } else {
         return offset == 0 && length == array.length ? wrappedBuffer(array) : wrappedBuffer(array).slice(offset, length);
      }
   }

   public static ByteBuf wrappedBuffer(ByteBuffer buffer) {
      if (!buffer.hasRemaining()) {
         return EMPTY_BUFFER;
      } else if (!buffer.isDirect() && buffer.hasArray()) {
         return wrappedBuffer(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining()).order(buffer.order());
      } else if (PlatformDependent.hasUnsafe()) {
         if (buffer.isReadOnly()) {
            return (ByteBuf)(buffer.isDirect() ? new ReadOnlyUnsafeDirectByteBuf(ALLOC, buffer) : new ReadOnlyByteBufferBuf(ALLOC, buffer));
         } else {
            return new UnpooledUnsafeDirectByteBuf(ALLOC, buffer, buffer.remaining());
         }
      } else {
         return (ByteBuf)(buffer.isReadOnly() ? new ReadOnlyByteBufferBuf(ALLOC, buffer) : new UnpooledDirectByteBuf(ALLOC, buffer, buffer.remaining()));
      }
   }

   public static ByteBuf wrappedBuffer(long memoryAddress, int size, boolean doFree) {
      return new WrappedUnpooledUnsafeDirectByteBuf(ALLOC, memoryAddress, size, doFree);
   }

   public static ByteBuf wrappedBuffer(ByteBuf buffer) {
      if (buffer.isReadable()) {
         return buffer.slice();
      } else {
         buffer.release();
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf wrappedBuffer(byte[]... arrays) {
      return wrappedBuffer(16, (byte[][])arrays);
   }

   public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
      return wrappedBuffer(16, (ByteBuf[])buffers);
   }

   public static ByteBuf wrappedBuffer(ByteBuffer... buffers) {
      return wrappedBuffer(16, (ByteBuffer[])buffers);
   }

   public static ByteBuf wrappedBuffer(int maxNumComponents, byte[]... arrays) {
      switch (arrays.length) {
         case 0:
            break;
         case 1:
            if (arrays[0].length != 0) {
               return wrappedBuffer(arrays[0]);
            }
            break;
         default:
            List components = new ArrayList(arrays.length);
            byte[][] var3 = arrays;
            int var4 = arrays.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               byte[] a = var3[var5];
               if (a == null) {
                  break;
               }

               if (a.length > 0) {
                  components.add(wrappedBuffer(a));
               }
            }

            if (!components.isEmpty()) {
               return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);
            }
      }

      return EMPTY_BUFFER;
   }

   public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers) {
      switch (buffers.length) {
         case 0:
            break;
         case 1:
            ByteBuf buffer = buffers[0];
            if (buffer.isReadable()) {
               return wrappedBuffer(buffer.order(BIG_ENDIAN));
            }

            buffer.release();
            break;
         default:
            for(int i = 0; i < buffers.length; ++i) {
               ByteBuf buf = buffers[i];
               if (buf.isReadable()) {
                  return new CompositeByteBuf(ALLOC, false, maxNumComponents, buffers, i, buffers.length);
               }

               buf.release();
            }
      }

      return EMPTY_BUFFER;
   }

   public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuffer... buffers) {
      switch (buffers.length) {
         case 0:
            break;
         case 1:
            if (buffers[0].hasRemaining()) {
               return wrappedBuffer(buffers[0].order(BIG_ENDIAN));
            }
            break;
         default:
            List components = new ArrayList(buffers.length);
            ByteBuffer[] var3 = buffers;
            int var4 = buffers.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ByteBuffer b = var3[var5];
               if (b == null) {
                  break;
               }

               if (b.remaining() > 0) {
                  components.add(wrappedBuffer(b.order(BIG_ENDIAN)));
               }
            }

            if (!components.isEmpty()) {
               return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);
            }
      }

      return EMPTY_BUFFER;
   }

   public static CompositeByteBuf compositeBuffer() {
      return compositeBuffer(16);
   }

   public static CompositeByteBuf compositeBuffer(int maxNumComponents) {
      return new CompositeByteBuf(ALLOC, false, maxNumComponents);
   }

   public static ByteBuf copiedBuffer(byte[] array) {
      return array.length == 0 ? EMPTY_BUFFER : wrappedBuffer((byte[])array.clone());
   }

   public static ByteBuf copiedBuffer(byte[] array, int offset, int length) {
      if (length == 0) {
         return EMPTY_BUFFER;
      } else {
         byte[] copy = new byte[length];
         System.arraycopy(array, offset, copy, 0, length);
         return wrappedBuffer(copy);
      }
   }

   public static ByteBuf copiedBuffer(ByteBuffer buffer) {
      int length = buffer.remaining();
      if (length == 0) {
         return EMPTY_BUFFER;
      } else {
         byte[] copy = new byte[length];
         ByteBuffer duplicate = buffer.duplicate();
         duplicate.get(copy);
         return wrappedBuffer(copy).order(duplicate.order());
      }
   }

   public static ByteBuf copiedBuffer(ByteBuf buffer) {
      int readable = buffer.readableBytes();
      if (readable > 0) {
         ByteBuf copy = buffer(readable);
         copy.writeBytes(buffer, buffer.readerIndex(), readable);
         return copy;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copiedBuffer(byte[]... arrays) {
      switch (arrays.length) {
         case 0:
            return EMPTY_BUFFER;
         case 1:
            if (arrays[0].length == 0) {
               return EMPTY_BUFFER;
            }

            return copiedBuffer(arrays[0]);
         default:
            int length = 0;
            byte[][] var2 = arrays;
            int i = arrays.length;

            int j;
            byte[] a;
            for(j = 0; j < i; ++j) {
               a = var2[j];
               if (Integer.MAX_VALUE - length < a.length) {
                  throw new IllegalArgumentException("The total length of the specified arrays is too big.");
               }

               length += a.length;
            }

            if (length == 0) {
               return EMPTY_BUFFER;
            } else {
               byte[] mergedArray = new byte[length];
               i = 0;

               for(j = 0; i < arrays.length; ++i) {
                  a = arrays[i];
                  System.arraycopy(a, 0, mergedArray, j, a.length);
                  j += a.length;
               }

               return wrappedBuffer(mergedArray);
            }
      }
   }

   public static ByteBuf copiedBuffer(ByteBuf... buffers) {
      switch (buffers.length) {
         case 0:
            return EMPTY_BUFFER;
         case 1:
            return copiedBuffer(buffers[0]);
         default:
            ByteOrder order = null;
            int length = 0;
            ByteBuf[] var3 = buffers;
            int i = buffers.length;
            int j = 0;

            ByteBuf b;
            int bLen;
            for(; j < i; ++j) {
               b = var3[j];
               bLen = b.readableBytes();
               if (bLen > 0) {
                  if (Integer.MAX_VALUE - length < bLen) {
                     throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                  }

                  length += bLen;
                  if (order != null) {
                     if (!order.equals(b.order())) {
                        throw new IllegalArgumentException("inconsistent byte order");
                     }
                  } else {
                     order = b.order();
                  }
               }
            }

            if (length == 0) {
               return EMPTY_BUFFER;
            } else {
               byte[] mergedArray = new byte[length];
               i = 0;

               for(j = 0; i < buffers.length; ++i) {
                  b = buffers[i];
                  bLen = b.readableBytes();
                  b.getBytes(b.readerIndex(), mergedArray, j, bLen);
                  j += bLen;
               }

               return wrappedBuffer(mergedArray).order(order);
            }
      }
   }

   public static ByteBuf copiedBuffer(ByteBuffer... buffers) {
      switch (buffers.length) {
         case 0:
            return EMPTY_BUFFER;
         case 1:
            return copiedBuffer(buffers[0]);
         default:
            ByteOrder order = null;
            int length = 0;
            ByteBuffer[] var3 = buffers;
            int i = buffers.length;
            int j = 0;

            ByteBuffer b;
            int bLen;
            for(; j < i; ++j) {
               b = var3[j];
               bLen = b.remaining();
               if (bLen > 0) {
                  if (Integer.MAX_VALUE - length < bLen) {
                     throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                  }

                  length += bLen;
                  if (order != null) {
                     if (!order.equals(b.order())) {
                        throw new IllegalArgumentException("inconsistent byte order");
                     }
                  } else {
                     order = b.order();
                  }
               }
            }

            if (length == 0) {
               return EMPTY_BUFFER;
            } else {
               byte[] mergedArray = new byte[length];
               i = 0;

               for(j = 0; i < buffers.length; ++i) {
                  b = buffers[i].duplicate();
                  bLen = b.remaining();
                  b.get(mergedArray, j, bLen);
                  j += bLen;
               }

               return wrappedBuffer(mergedArray).order(order);
            }
      }
   }

   public static ByteBuf copiedBuffer(CharSequence string, Charset charset) {
      if (string == null) {
         throw new NullPointerException("string");
      } else {
         return string instanceof CharBuffer ? copiedBuffer((CharBuffer)string, charset) : copiedBuffer(CharBuffer.wrap(string), charset);
      }
   }

   public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset) {
      if (string == null) {
         throw new NullPointerException("string");
      } else if (length == 0) {
         return EMPTY_BUFFER;
      } else if (string instanceof CharBuffer) {
         CharBuffer buf = (CharBuffer)string;
         if (buf.hasArray()) {
            return copiedBuffer(buf.array(), buf.arrayOffset() + buf.position() + offset, length, charset);
         } else {
            buf = buf.slice();
            buf.limit(length);
            buf.position(offset);
            return copiedBuffer(buf, charset);
         }
      } else {
         return copiedBuffer(CharBuffer.wrap(string, offset, offset + length), charset);
      }
   }

   public static ByteBuf copiedBuffer(char[] array, Charset charset) {
      if (array == null) {
         throw new NullPointerException("array");
      } else {
         return copiedBuffer((char[])array, 0, array.length, charset);
      }
   }

   public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset) {
      if (array == null) {
         throw new NullPointerException("array");
      } else {
         return length == 0 ? EMPTY_BUFFER : copiedBuffer(CharBuffer.wrap(array, offset, length), charset);
      }
   }

   private static ByteBuf copiedBuffer(CharBuffer buffer, Charset charset) {
      return ByteBufUtil.encodeString0(ALLOC, true, buffer, charset, 0);
   }

   /** @deprecated */
   @Deprecated
   public static ByteBuf unmodifiableBuffer(ByteBuf buffer) {
      ByteOrder endianness = buffer.order();
      return (ByteBuf)(endianness == BIG_ENDIAN ? new ReadOnlyByteBuf(buffer) : (new ReadOnlyByteBuf(buffer.order(BIG_ENDIAN))).order(LITTLE_ENDIAN));
   }

   public static ByteBuf copyInt(int value) {
      ByteBuf buf = buffer(4);
      buf.writeInt(value);
      return buf;
   }

   public static ByteBuf copyInt(int... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 4);
         int[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int v = var2[var4];
            buffer.writeInt(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyShort(int value) {
      ByteBuf buf = buffer(2);
      buf.writeShort(value);
      return buf;
   }

   public static ByteBuf copyShort(short... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 2);
         short[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int v = var2[var4];
            buffer.writeShort(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyShort(int... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 2);
         int[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int v = var2[var4];
            buffer.writeShort(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyMedium(int value) {
      ByteBuf buf = buffer(3);
      buf.writeMedium(value);
      return buf;
   }

   public static ByteBuf copyMedium(int... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 3);
         int[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int v = var2[var4];
            buffer.writeMedium(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyLong(long value) {
      ByteBuf buf = buffer(8);
      buf.writeLong(value);
      return buf;
   }

   public static ByteBuf copyLong(long... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 8);
         long[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            long v = var2[var4];
            buffer.writeLong(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyBoolean(boolean value) {
      ByteBuf buf = buffer(1);
      buf.writeBoolean(value);
      return buf;
   }

   public static ByteBuf copyBoolean(boolean... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length);
         boolean[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            boolean v = var2[var4];
            buffer.writeBoolean(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyFloat(float value) {
      ByteBuf buf = buffer(4);
      buf.writeFloat(value);
      return buf;
   }

   public static ByteBuf copyFloat(float... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 4);
         float[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            float v = var2[var4];
            buffer.writeFloat(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf copyDouble(double value) {
      ByteBuf buf = buffer(8);
      buf.writeDouble(value);
      return buf;
   }

   public static ByteBuf copyDouble(double... values) {
      if (values != null && values.length != 0) {
         ByteBuf buffer = buffer(values.length * 8);
         double[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            double v = var2[var4];
            buffer.writeDouble(v);
         }

         return buffer;
      } else {
         return EMPTY_BUFFER;
      }
   }

   public static ByteBuf unreleasableBuffer(ByteBuf buf) {
      return new UnreleasableByteBuf(buf);
   }

   public static ByteBuf unmodifiableBuffer(ByteBuf... buffers) {
      return new FixedCompositeByteBuf(ALLOC, buffers);
   }

   private Unpooled() {
   }

   static {
      ALLOC = UnpooledByteBufAllocator.DEFAULT;
      BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
      LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
      EMPTY_BUFFER = ALLOC.buffer(0, 0);

      assert EMPTY_BUFFER instanceof EmptyByteBuf : "EMPTY_BUFFER must be an EmptyByteBuf.";

   }
}

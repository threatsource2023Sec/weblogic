package com.bea.core.repackaged.springframework.core.io.buffer;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.IntPredicate;

public class DefaultDataBuffer implements DataBuffer {
   private static final int MAX_CAPACITY = Integer.MAX_VALUE;
   private static final int CAPACITY_THRESHOLD = 4194304;
   private final DefaultDataBufferFactory dataBufferFactory;
   private ByteBuffer byteBuffer;
   private int capacity;
   private int readPosition;
   private int writePosition;

   private DefaultDataBuffer(DefaultDataBufferFactory dataBufferFactory, ByteBuffer byteBuffer) {
      Assert.notNull(dataBufferFactory, (String)"DefaultDataBufferFactory must not be null");
      Assert.notNull(byteBuffer, (String)"ByteBuffer must not be null");
      this.dataBufferFactory = dataBufferFactory;
      ByteBuffer slice = byteBuffer.slice();
      this.byteBuffer = slice;
      this.capacity = slice.remaining();
   }

   static DefaultDataBuffer fromFilledByteBuffer(DefaultDataBufferFactory dataBufferFactory, ByteBuffer byteBuffer) {
      DefaultDataBuffer dataBuffer = new DefaultDataBuffer(dataBufferFactory, byteBuffer);
      dataBuffer.writePosition(byteBuffer.remaining());
      return dataBuffer;
   }

   static DefaultDataBuffer fromEmptyByteBuffer(DefaultDataBufferFactory dataBufferFactory, ByteBuffer byteBuffer) {
      return new DefaultDataBuffer(dataBufferFactory, byteBuffer);
   }

   public ByteBuffer getNativeBuffer() {
      return this.byteBuffer;
   }

   private void setNativeBuffer(ByteBuffer byteBuffer) {
      this.byteBuffer = byteBuffer;
      this.capacity = byteBuffer.remaining();
   }

   public DefaultDataBufferFactory factory() {
      return this.dataBufferFactory;
   }

   public int indexOf(IntPredicate predicate, int fromIndex) {
      Assert.notNull(predicate, (String)"IntPredicate must not be null");
      if (fromIndex < 0) {
         fromIndex = 0;
      } else if (fromIndex >= this.writePosition) {
         return -1;
      }

      for(int i = fromIndex; i < this.writePosition; ++i) {
         byte b = this.byteBuffer.get(i);
         if (predicate.test(b)) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(IntPredicate predicate, int fromIndex) {
      Assert.notNull(predicate, (String)"IntPredicate must not be null");

      for(int i = Math.min(fromIndex, this.writePosition - 1); i >= 0; --i) {
         byte b = this.byteBuffer.get(i);
         if (predicate.test(b)) {
            return i;
         }
      }

      return -1;
   }

   public int readableByteCount() {
      return this.writePosition - this.readPosition;
   }

   public int writableByteCount() {
      return this.capacity - this.writePosition;
   }

   public int readPosition() {
      return this.readPosition;
   }

   public DefaultDataBuffer readPosition(int readPosition) {
      this.assertIndex(readPosition >= 0, "'readPosition' %d must be >= 0", readPosition);
      this.assertIndex(readPosition <= this.writePosition, "'readPosition' %d must be <= %d", readPosition, this.writePosition);
      this.readPosition = readPosition;
      return this;
   }

   public int writePosition() {
      return this.writePosition;
   }

   public DefaultDataBuffer writePosition(int writePosition) {
      this.assertIndex(writePosition >= this.readPosition, "'writePosition' %d must be >= %d", writePosition, this.readPosition);
      this.assertIndex(writePosition <= this.capacity, "'writePosition' %d must be <= %d", writePosition, this.capacity);
      this.writePosition = writePosition;
      return this;
   }

   public int capacity() {
      return this.capacity;
   }

   public DefaultDataBuffer capacity(int newCapacity) {
      if (newCapacity <= 0) {
         throw new IllegalArgumentException(String.format("'newCapacity' %d must be higher than 0", newCapacity));
      } else {
         int readPosition = this.readPosition();
         int writePosition = this.writePosition();
         int oldCapacity = this.capacity();
         ByteBuffer oldBuffer;
         ByteBuffer newBuffer;
         if (newCapacity > oldCapacity) {
            oldBuffer = this.byteBuffer;
            newBuffer = allocate(newCapacity, oldBuffer.isDirect());
            oldBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.put(oldBuffer);
            newBuffer.clear();
            this.setNativeBuffer(newBuffer);
         } else if (newCapacity < oldCapacity) {
            oldBuffer = this.byteBuffer;
            newBuffer = allocate(newCapacity, oldBuffer.isDirect());
            if (readPosition < newCapacity) {
               if (writePosition > newCapacity) {
                  writePosition = newCapacity;
                  this.writePosition(newCapacity);
               }

               oldBuffer.position(readPosition).limit(writePosition);
               newBuffer.position(readPosition).limit(writePosition);
               newBuffer.put(oldBuffer);
               newBuffer.clear();
            } else {
               this.readPosition(newCapacity);
               this.writePosition(newCapacity);
            }

            this.setNativeBuffer(newBuffer);
         }

         return this;
      }
   }

   public DataBuffer ensureCapacity(int length) {
      if (length > this.writableByteCount()) {
         int newCapacity = this.calculateCapacity(this.writePosition + length);
         this.capacity(newCapacity);
      }

      return this;
   }

   private static ByteBuffer allocate(int capacity, boolean direct) {
      return direct ? ByteBuffer.allocateDirect(capacity) : ByteBuffer.allocate(capacity);
   }

   public byte getByte(int index) {
      this.assertIndex(index >= 0, "index %d must be >= 0", index);
      this.assertIndex(index <= this.writePosition - 1, "index %d must be <= %d", index, this.writePosition - 1);
      return this.byteBuffer.get(index);
   }

   public byte read() {
      this.assertIndex(this.readPosition <= this.writePosition - 1, "readPosition %d must be <= %d", this.readPosition, this.writePosition - 1);
      int pos = this.readPosition;
      byte b = this.byteBuffer.get(pos);
      this.readPosition = pos + 1;
      return b;
   }

   public DefaultDataBuffer read(byte[] destination) {
      Assert.notNull(destination, (String)"Byte array must not be null");
      this.read(destination, 0, destination.length);
      return this;
   }

   public DefaultDataBuffer read(byte[] destination, int offset, int length) {
      Assert.notNull(destination, (String)"Byte array must not be null");
      this.assertIndex(this.readPosition <= this.writePosition - length, "readPosition %d and length %d should be smaller than writePosition %d", this.readPosition, length, this.writePosition);
      ByteBuffer tmp = this.byteBuffer.duplicate();
      int limit = this.readPosition + length;
      tmp.clear().position(this.readPosition).limit(limit);
      tmp.get(destination, offset, length);
      this.readPosition += length;
      return this;
   }

   public DefaultDataBuffer write(byte b) {
      this.ensureCapacity(1);
      int pos = this.writePosition;
      this.byteBuffer.put(pos, b);
      this.writePosition = pos + 1;
      return this;
   }

   public DefaultDataBuffer write(byte[] source) {
      Assert.notNull(source, (String)"Byte array must not be null");
      this.write(source, 0, source.length);
      return this;
   }

   public DefaultDataBuffer write(byte[] source, int offset, int length) {
      Assert.notNull(source, (String)"Byte array must not be null");
      this.ensureCapacity(length);
      ByteBuffer tmp = this.byteBuffer.duplicate();
      int limit = this.writePosition + length;
      tmp.clear().position(this.writePosition).limit(limit);
      tmp.put(source, offset, length);
      this.writePosition += length;
      return this;
   }

   public DefaultDataBuffer write(DataBuffer... buffers) {
      if (!ObjectUtils.isEmpty((Object[])buffers)) {
         this.write((ByteBuffer[])Arrays.stream(buffers).map(DataBuffer::asByteBuffer).toArray((x$0) -> {
            return new ByteBuffer[x$0];
         }));
      }

      return this;
   }

   public DefaultDataBuffer write(ByteBuffer... buffers) {
      if (!ObjectUtils.isEmpty((Object[])buffers)) {
         int capacity = Arrays.stream(buffers).mapToInt(Buffer::remaining).sum();
         this.ensureCapacity(capacity);
         Arrays.stream(buffers).forEach(this::write);
      }

      return this;
   }

   private void write(ByteBuffer source) {
      int length = source.remaining();
      ByteBuffer tmp = this.byteBuffer.duplicate();
      int limit = this.writePosition + source.remaining();
      tmp.clear().position(this.writePosition).limit(limit);
      tmp.put(source);
      this.writePosition += length;
   }

   public DefaultDataBuffer slice(int index, int length) {
      this.checkIndex(index, length);
      int oldPosition = this.byteBuffer.position();
      Buffer buffer = this.byteBuffer;

      SlicedDefaultDataBuffer var6;
      try {
         buffer.position(index);
         ByteBuffer slice = this.byteBuffer.slice();
         slice.limit(length);
         var6 = new SlicedDefaultDataBuffer(slice, this.dataBufferFactory, length);
      } finally {
         buffer.position(oldPosition);
      }

      return var6;
   }

   public ByteBuffer asByteBuffer() {
      return this.asByteBuffer(this.readPosition, this.readableByteCount());
   }

   public ByteBuffer asByteBuffer(int index, int length) {
      this.checkIndex(index, length);
      ByteBuffer duplicate = this.byteBuffer.duplicate();
      duplicate.position(index);
      duplicate.limit(index + length);
      return duplicate.slice();
   }

   public InputStream asInputStream() {
      return new DefaultDataBufferInputStream();
   }

   public InputStream asInputStream(boolean releaseOnClose) {
      return new DefaultDataBufferInputStream();
   }

   public OutputStream asOutputStream() {
      return new DefaultDataBufferOutputStream();
   }

   private int calculateCapacity(int neededCapacity) {
      Assert.isTrue(neededCapacity >= 0, "'neededCapacity' must >= 0");
      if (neededCapacity == 4194304) {
         return 4194304;
      } else {
         int newCapacity;
         if (neededCapacity > 4194304) {
            newCapacity = neededCapacity / 4194304 * 4194304;
            if (newCapacity > 2143289343) {
               newCapacity = Integer.MAX_VALUE;
            } else {
               newCapacity += 4194304;
            }

            return newCapacity;
         } else {
            for(newCapacity = 64; newCapacity < neededCapacity; newCapacity <<= 1) {
            }

            return Math.min(newCapacity, Integer.MAX_VALUE);
         }
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DefaultDataBuffer)) {
         return false;
      } else {
         DefaultDataBuffer otherBuffer = (DefaultDataBuffer)other;
         return this.readPosition == otherBuffer.readPosition && this.writePosition == otherBuffer.writePosition && this.byteBuffer.equals(otherBuffer.byteBuffer);
      }
   }

   public int hashCode() {
      return this.byteBuffer.hashCode();
   }

   public String toString() {
      return String.format("DefaultDataBuffer (r: %d, w: %d, c: %d)", this.readPosition, this.writePosition, this.capacity);
   }

   private void checkIndex(int index, int length) {
      this.assertIndex(index >= 0, "index %d must be >= 0", index);
      this.assertIndex(length >= 0, "length %d must be >= 0", index);
      this.assertIndex(index <= this.capacity, "index %d must be <= %d", index, this.capacity);
      this.assertIndex(length <= this.capacity, "length %d must be <= %d", index, this.capacity);
   }

   private void assertIndex(boolean expression, String format, Object... args) {
      if (!expression) {
         String message = String.format(format, args);
         throw new IndexOutOfBoundsException(message);
      }
   }

   // $FF: synthetic method
   DefaultDataBuffer(DefaultDataBufferFactory x0, ByteBuffer x1, Object x2) {
      this(x0, x1);
   }

   private static class SlicedDefaultDataBuffer extends DefaultDataBuffer {
      SlicedDefaultDataBuffer(ByteBuffer byteBuffer, DefaultDataBufferFactory dataBufferFactory, int length) {
         super(dataBufferFactory, byteBuffer, null);
         this.writePosition(length);
      }

      public DefaultDataBuffer capacity(int newCapacity) {
         throw new UnsupportedOperationException("Changing the capacity of a sliced buffer is not supported");
      }
   }

   private class DefaultDataBufferOutputStream extends OutputStream {
      private DefaultDataBufferOutputStream() {
      }

      public void write(int b) throws IOException {
         DefaultDataBuffer.this.write((byte)b);
      }

      public void write(byte[] bytes, int off, int len) throws IOException {
         DefaultDataBuffer.this.write(bytes, off, len);
      }

      // $FF: synthetic method
      DefaultDataBufferOutputStream(Object x1) {
         this();
      }
   }

   private class DefaultDataBufferInputStream extends InputStream {
      private DefaultDataBufferInputStream() {
      }

      public int available() {
         return DefaultDataBuffer.this.readableByteCount();
      }

      public int read() {
         return this.available() > 0 ? DefaultDataBuffer.this.read() & 255 : -1;
      }

      public int read(byte[] bytes, int off, int len) throws IOException {
         int available = this.available();
         if (available > 0) {
            len = Math.min(len, available);
            DefaultDataBuffer.this.read(bytes, off, len);
            return len;
         } else {
            return -1;
         }
      }

      // $FF: synthetic method
      DefaultDataBufferInputStream(Object x1) {
         this();
      }
   }
}

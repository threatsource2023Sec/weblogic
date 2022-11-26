package org.glassfish.grizzly.memory;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.utils.ArrayUtils;

public final class BuffersBuffer extends CompositeBuffer {
   public static volatile boolean DEBUG_MODE = false;
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(BuffersBuffer.class, Integer.getInteger(BuffersBuffer.class.getName() + ".bb-cache-size", 5));
   protected Exception disposeStackTrace;
   private MemoryManager memoryManager;
   private ByteOrder byteOrder;
   private boolean bigEndian;
   private boolean allowBufferDispose;
   private boolean allowInternalBuffersDispose;
   private boolean isDisposed;
   private boolean isReadOnly;
   private int mark;
   private int position;
   private int limit;
   private int capacity;
   private int[] bufferBounds;
   private Buffer[] buffers;
   private int buffersSize;
   private int lastSegmentIndex;
   private int lowerBound;
   private int upperBound;
   private int activeBufferLowerBound;
   private Buffer activeBuffer;
   private final SetterImpl setter;

   public static BuffersBuffer create() {
      return create(MemoryManager.DEFAULT_MEMORY_MANAGER, (Buffer[])null, 0, false);
   }

   public static BuffersBuffer create(MemoryManager memoryManager) {
      return create(memoryManager, (Buffer[])null, 0, false);
   }

   public static BuffersBuffer create(MemoryManager memoryManager, Buffer... buffers) {
      return create(memoryManager, buffers, buffers.length, false);
   }

   public static BuffersBuffer create(MemoryManager memoryManager, Buffer[] buffers, boolean isReadOnly) {
      return create(memoryManager, buffers, buffers.length, isReadOnly);
   }

   private static BuffersBuffer create(MemoryManager memoryManager, Buffer[] buffers, int buffersSize, boolean isReadOnly) {
      return create(memoryManager, buffers, buffersSize, ByteOrder.BIG_ENDIAN, isReadOnly);
   }

   private static BuffersBuffer create(MemoryManager memoryManager, Buffer[] buffers, int buffersSize, ByteOrder byteOrder, boolean isReadOnly) {
      BuffersBuffer buffer = (BuffersBuffer)ThreadCache.takeFromCache(CACHE_IDX);
      if (buffer != null) {
         buffer.isDisposed = false;
         buffer.order(byteOrder);
         buffer.set(memoryManager, buffers, buffersSize, isReadOnly);
         return buffer;
      } else {
         return new BuffersBuffer(memoryManager, buffers, buffersSize, isReadOnly);
      }
   }

   protected BuffersBuffer(MemoryManager memoryManager, Buffer[] buffers, int buffersSize, boolean isReadOnly) {
      this.byteOrder = ByteOrder.BIG_ENDIAN;
      this.bigEndian = true;
      this.allowBufferDispose = false;
      this.allowInternalBuffersDispose = true;
      this.mark = -1;
      this.setter = new SetterImpl();
      this.set(memoryManager, buffers, buffersSize, isReadOnly);
   }

   private void set(MemoryManager memoryManager, Buffer[] buffers, int buffersSize, boolean isReadOnly) {
      this.memoryManager = memoryManager != null ? memoryManager : MemoryManager.DEFAULT_MEMORY_MANAGER;
      if (buffers != null || this.buffers == null) {
         this.initBuffers(buffers, buffersSize);
         this.refreshBuffers();
         this.limit = this.capacity;
      }

      this.isReadOnly = isReadOnly;
   }

   private void initBuffers(Buffer[] buffers, int bufferSize) {
      this.buffers = buffers != null ? buffers : new Buffer[4];
      this.buffersSize = bufferSize;
      if (this.bufferBounds == null || this.bufferBounds.length < this.buffers.length) {
         this.bufferBounds = new int[this.buffers.length];
      }

   }

   private BuffersBuffer duplicateFrom(BuffersBuffer that) {
      this.memoryManager = that.memoryManager;
      Buffer[] ba = new Buffer[that.buffers.length];
      int i = 0;

      for(int len = that.buffersSize; i < len; ++i) {
         ba[i] = that.buffers[i].duplicate();
      }

      this.initBuffers(ba, that.buffersSize);
      System.arraycopy(that.bufferBounds, 0, this.bufferBounds, 0, that.buffersSize);
      this.position = that.position;
      this.limit = that.limit;
      this.capacity = that.capacity;
      this.isReadOnly = that.isReadOnly;
      this.byteOrder = that.byteOrder;
      return this;
   }

   public final boolean tryDispose() {
      if (this.allowBufferDispose) {
         this.dispose();
         return true;
      } else {
         if (this.allowInternalBuffersDispose) {
            this.removeAndDisposeBuffers();
         }

         return false;
      }
   }

   public void dispose() {
      this.checkDispose();
      this.isDisposed = true;
      this.removeAndDisposeBuffers();
      if (DEBUG_MODE) {
         BuffersBuffer.DebugLogic.doDebug(this);
      }

      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public final boolean isComposite() {
      return true;
   }

   public BuffersBuffer append(Buffer buffer) {
      if (buffer == this) {
         throw new IllegalArgumentException("CompositeBuffer can not append itself");
      } else {
         this.checkDispose();
         this.checkReadOnly();
         this.ensureBuffersCapacity(1);
         buffer.order(this.byteOrder);
         this.capacity += buffer.remaining();
         this.bufferBounds[this.buffersSize] = this.capacity;
         this.buffers[this.buffersSize++] = buffer;
         this.limit = this.capacity;
         this.resetLastLocation();
         return this;
      }
   }

   public BuffersBuffer prepend(Buffer buffer) {
      if (buffer == this) {
         throw new IllegalArgumentException("CompositeBuffer can not append itself");
      } else {
         this.checkDispose();
         this.checkReadOnly();
         this.ensureBuffersCapacity(1);
         buffer.order(this.byteOrder);
         System.arraycopy(this.buffers, 0, this.buffers, 1, this.buffersSize);
         this.buffers[0] = buffer;
         ++this.buffersSize;
         this.refreshBuffers();
         this.position = 0;
         this.limit += buffer.remaining();
         this.resetLastLocation();
         return this;
      }
   }

   public boolean replace(Buffer oldBuffer, Buffer newBuffer) {
      if (newBuffer == this) {
         throw new IllegalArgumentException("CompositeBuffer can not append itself");
      } else {
         for(int i = 0; i < this.buffersSize; ++i) {
            Buffer b = this.buffers[i];
            if (b == oldBuffer) {
               this.buffers[i] = newBuffer;
               this.refreshBuffers();
               this.limit = this.capacity;
               if (this.position > this.limit) {
                  this.position = this.limit;
               }

               this.resetLastLocation();
               return true;
            }

            if (b.isComposite() && ((CompositeBuffer)b).replace(oldBuffer, newBuffer)) {
               break;
            }
         }

         return false;
      }
   }

   private void ensureBuffersCapacity(int newElementsNum) {
      int newSize = this.buffersSize + newElementsNum;
      if (newSize > this.buffers.length) {
         int newCapacity = Math.max(newSize, this.buffers.length * 3 / 2 + 1);
         this.buffers = (Buffer[])Arrays.copyOf(this.buffers, newCapacity);
         this.bufferBounds = Arrays.copyOf(this.bufferBounds, newCapacity);
      }

   }

   public Buffer[] underlying() {
      this.checkDispose();
      return this.buffers;
   }

   public int position() {
      this.checkDispose();
      return this.position;
   }

   public BuffersBuffer position(int newPosition) {
      this.checkDispose();
      this.setPosLim(newPosition, this.limit);
      if (this.mark > this.position) {
         this.mark = -1;
      }

      return this;
   }

   public int limit() {
      this.checkDispose();
      return this.limit;
   }

   public BuffersBuffer limit(int newLimit) {
      this.checkDispose();
      this.setPosLim(this.position <= newLimit ? this.position : newLimit, newLimit);
      if (this.mark > this.limit) {
         this.mark = -1;
      }

      return this;
   }

   public int capacity() {
      this.checkDispose();
      return this.capacity;
   }

   public BuffersBuffer mark() {
      this.mark = this.position;
      return this;
   }

   public BuffersBuffer reset() {
      int m = this.mark;
      if (m < 0) {
         throw new InvalidMarkException();
      } else {
         this.position = m;
         return this;
      }
   }

   public boolean isDirect() {
      return this.buffers[0].isDirect();
   }

   public BuffersBuffer clear() {
      this.checkDispose();
      this.refreshBuffers();
      this.setPosLim(0, this.capacity);
      this.mark = -1;
      return this;
   }

   public BuffersBuffer flip() {
      this.checkDispose();
      this.setPosLim(0, this.position);
      this.mark = -1;
      return this;
   }

   public BuffersBuffer rewind() {
      this.checkDispose();
      this.setPosLim(0, this.limit);
      this.mark = -1;
      return this;
   }

   public int remaining() {
      this.checkDispose();
      return this.limit - this.position;
   }

   public boolean hasRemaining() {
      this.checkDispose();
      return this.limit > this.position;
   }

   public boolean isReadOnly() {
      this.checkDispose();
      return this.isReadOnly;
   }

   public BuffersBuffer asReadOnlyBuffer() {
      this.checkDispose();
      BuffersBuffer buffer = create().duplicateFrom(this);
      buffer.isReadOnly = true;
      return buffer;
   }

   public Buffer split(int splitPosition) {
      this.checkDispose();
      if (splitPosition >= 0 && splitPosition <= this.capacity) {
         if (this.mark >= splitPosition) {
            this.mark = -1;
         }

         int oldPosition = this.position;
         int oldLimit = this.limit;
         if (splitPosition == this.capacity) {
            return Buffers.EMPTY_BUFFER;
         } else if (splitPosition == 0) {
            BuffersBuffer slice2Buffer = create(this.memoryManager, this.buffers, this.buffersSize, this.byteOrder, this.isReadOnly);
            slice2Buffer.setPosLim(this.position, this.limit);
            this.initBuffers((Buffer[])null, 0);
            this.position = 0;
            this.limit = 0;
            this.capacity = 0;
            this.resetLastLocation();
            return slice2Buffer;
         } else {
            this.checkIndex(splitPosition);
            int splitBufferIdx = this.lastSegmentIndex;
            int splitBufferPos = this.toActiveBufferPos(splitPosition);
            BuffersBuffer slice2Buffer = create(this.memoryManager, (Buffer[])null, 0, this.byteOrder, false);
            Buffer splitBuffer = this.activeBuffer;
            int newSize = splitBufferIdx + 1;
            if (splitBufferPos == 0) {
               slice2Buffer.append(splitBuffer);
               this.buffers[splitBufferIdx] = null;
               newSize = splitBufferIdx;
            } else if (splitBufferPos < splitBuffer.limit()) {
               Buffer splitBuffer2 = splitBuffer.split(splitBufferPos);
               slice2Buffer.append(splitBuffer2);
            }

            for(int i = splitBufferIdx + 1; i < this.buffersSize; ++i) {
               slice2Buffer.append(this.buffers[i]);
               this.buffers[i] = null;
            }

            this.buffersSize = newSize;
            this.refreshBuffers();
            if (oldPosition < splitPosition) {
               this.position = oldPosition;
            } else {
               this.position = this.capacity;
               slice2Buffer.position(oldPosition - splitPosition);
            }

            if (oldLimit < splitPosition) {
               this.limit = oldLimit;
               slice2Buffer.limit(0);
            } else {
               slice2Buffer.limit(oldLimit - splitPosition);
               this.limit = this.capacity;
            }

            this.resetLastLocation();
            return slice2Buffer;
         }
      } else {
         throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
      }
   }

   public void shrink() {
      this.checkDispose();
      if (this.position == this.limit) {
         this.removeAndDisposeBuffers();
      } else {
         this.checkIndex(this.position);
         int posBufferIndex = this.lastSegmentIndex;
         int posBufferPosition = this.toActiveBufferPos(this.position);
         this.checkIndex(this.limit - 1);
         int limitBufferIndex = this.lastSegmentIndex;
         int rightTrim = this.buffersSize - limitBufferIndex - 1;
         int shift = 0;

         for(int i = 0; i < posBufferIndex; ++i) {
            Buffer buffer = this.buffers[i];
            shift += buffer.remaining();
            if (this.allowInternalBuffersDispose) {
               buffer.tryDispose();
            }
         }

         Buffer posBuffer = this.buffers[posBufferIndex];
         int diff = posBufferPosition - posBuffer.position();
         if (diff > 0) {
            posBuffer.position(posBufferPosition);
            posBuffer.shrink();
            shift += diff;
         }

         this.setPosLim(this.position - shift, this.limit - shift);
         if (this.mark > this.position) {
            this.mark = -1;
         }

         for(int i = 0; i < rightTrim; ++i) {
            int idx = this.buffersSize - i - 1;
            Buffer buffer = this.buffers[idx];
            this.buffers[idx] = null;
            if (this.allowInternalBuffersDispose) {
               buffer.tryDispose();
            }
         }

         this.buffersSize -= posBufferIndex + rightTrim;
         if (posBufferIndex > 0) {
            System.arraycopy(this.buffers, posBufferIndex, this.buffers, 0, this.buffersSize);
            Arrays.fill(this.buffers, this.buffersSize, this.buffersSize + posBufferIndex, (Object)null);
         }

         this.refreshBuffers();
         this.resetLastLocation();
      }
   }

   public void trim() {
      this.flip();
      if (this.limit == 0) {
         this.removeRightBuffers(0);
         this.capacity = 0;
      } else {
         this.checkIndex(this.limit - 1);
         this.capacity -= this.removeRightBuffers(this.lastSegmentIndex + 1);
      }

      this.resetLastLocation();
   }

   protected int removeRightBuffers(int startIndex) {
      int removedBytes = 0;

      for(int i = startIndex; i < this.buffersSize; ++i) {
         Buffer buffer = this.buffers[i];
         this.buffers[i] = null;
         removedBytes += buffer.remaining();
         if (this.allowInternalBuffersDispose) {
            buffer.tryDispose();
         }
      }

      this.buffersSize = startIndex;
      return removedBytes;
   }

   public Buffer slice() {
      return this.slice(this.position, this.limit);
   }

   public Buffer slice(int position, int limit) {
      this.checkDispose();
      if (this.buffersSize != 0 && position != limit) {
         if (this.buffersSize == 1) {
            return this.buffers[0].slice(position, limit);
         } else {
            this.checkIndex(position);
            int posBufferIndex = this.lastSegmentIndex;
            int posBufferPosition = this.toActiveBufferPos(position);
            this.checkIndex(limit - 1);
            int limitBufferIndex = this.lastSegmentIndex;
            int limitBufferPosition = this.toActiveBufferPos(limit);
            if (posBufferIndex == limitBufferIndex) {
               return this.buffers[posBufferIndex].slice(posBufferPosition, limitBufferPosition);
            } else {
               Buffer[] newList = new Buffer[limitBufferIndex - posBufferIndex + 1];
               Buffer posBuffer = this.buffers[posBufferIndex];
               newList[0] = posBuffer.slice(posBufferPosition, posBuffer.limit());
               int index = 1;

               for(int i = posBufferIndex + 1; i < limitBufferIndex; ++i) {
                  newList[index++] = this.buffers[i].slice();
               }

               Buffer limitBuffer = this.buffers[limitBufferIndex];
               newList[index] = limitBuffer.slice(limitBuffer.position(), limitBufferPosition);
               return create(this.memoryManager, newList, newList.length, this.byteOrder, this.isReadOnly);
            }
         }
      } else {
         return Buffers.EMPTY_BUFFER;
      }
   }

   public BuffersBuffer duplicate() {
      this.checkDispose();
      return create().duplicateFrom(this);
   }

   public BuffersBuffer compact() {
      this.checkDispose();
      if (this.buffersSize == 0) {
         return this;
      } else {
         if (this.buffersSize == 1) {
            Buffer buffer = this.buffers[0];
            Buffers.setPositionLimit(buffer, buffer.position() + this.position, buffer.position() + this.limit);
            buffer.compact();
         } else {
            this.checkIndex(this.position);
            int posBufferIndex = this.lastSegmentIndex;
            this.activeBuffer.position(this.toActiveBufferPos(this.position));
            this.checkIndex(this.limit - 1);
            int limitBufferIndex = this.lastSegmentIndex;
            this.activeBuffer.limit(this.toActiveBufferPos(this.limit));

            for(int i = posBufferIndex; i <= limitBufferIndex; ++i) {
               Buffer b1 = this.buffers[i - posBufferIndex];
               this.buffers[i - posBufferIndex] = this.buffers[i];
               this.buffers[i] = b1;
            }
         }

         this.setPosLim(0, this.position);
         this.refreshBuffers();
         this.resetLastLocation();
         return this;
      }
   }

   public ByteOrder order() {
      this.checkDispose();
      return this.byteOrder;
   }

   public BuffersBuffer order(ByteOrder bo) {
      this.checkDispose();
      if (bo != this.byteOrder) {
         this.byteOrder = bo;
         this.bigEndian = bo == ByteOrder.BIG_ENDIAN;

         for(int i = 0; i < this.buffersSize; ++i) {
            this.buffers[i].order(bo);
         }
      }

      return this;
   }

   public boolean allowBufferDispose() {
      return this.allowBufferDispose;
   }

   public void allowBufferDispose(boolean allow) {
      this.allowBufferDispose = allow;
   }

   public boolean allowInternalBuffersDispose() {
      return this.allowInternalBuffersDispose;
   }

   public void allowInternalBuffersDispose(boolean allowInternalBuffersDispose) {
      this.allowInternalBuffersDispose = allowInternalBuffersDispose;
   }

   public byte get() {
      if (!this.hasRemaining()) {
         throw new BufferUnderflowException();
      } else {
         return this.get(this.position++);
      }
   }

   public BuffersBuffer put(byte b) {
      return this.put(this.position++, b);
   }

   public byte get(int index) {
      this.checkDispose();
      this.checkIndex(index);
      return this.activeBuffer.get(this.toActiveBufferPos(index));
   }

   public BuffersBuffer put(int index, byte b) {
      this.checkDispose();
      this.checkReadOnly();
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), b);
      return this;
   }

   private void checkIndex(int index) {
      if (!(index >= this.lowerBound & index < this.upperBound)) {
         this.recalcIndex(index);
      }
   }

   private void recalcIndex(int index) {
      int idx = index < this.bufferBounds[0] ? 0 : ArrayUtils.binarySearch(this.bufferBounds, 0, this.buffersSize - 1, index + 1);
      this.activeBuffer = this.buffers[idx];
      this.upperBound = this.bufferBounds[idx];
      this.lowerBound = this.upperBound - this.activeBuffer.remaining();
      this.lastSegmentIndex = idx;
      this.activeBufferLowerBound = this.lowerBound - this.activeBuffer.position();
   }

   private int toActiveBufferPos(int index) {
      return index - this.activeBufferLowerBound;
   }

   public BuffersBuffer get(byte[] dst) {
      return this.get((byte[])dst, 0, dst.length);
   }

   public BuffersBuffer get(byte[] dst, int offset, int length) {
      this.checkDispose();
      if (length == 0) {
         return this;
      } else if (this.remaining() < length) {
         throw new BufferUnderflowException();
      } else {
         this.checkIndex(this.position);
         int bufferIdx = this.lastSegmentIndex;
         Buffer buffer = this.activeBuffer;
         int bufferPosition = this.toActiveBufferPos(this.position);

         while(true) {
            int oldPos = buffer.position();
            buffer.position(bufferPosition);
            int bytesToCopy = Math.min(buffer.remaining(), length);
            buffer.get(dst, offset, bytesToCopy);
            buffer.position(oldPos);
            length -= bytesToCopy;
            offset += bytesToCopy;
            this.position += bytesToCopy;
            if (length == 0) {
               return this;
            }

            ++bufferIdx;
            buffer = this.buffers[bufferIdx];
            bufferPosition = buffer.position();
         }
      }
   }

   public BuffersBuffer put(byte[] src) {
      return this.put((byte[])src, 0, src.length);
   }

   public BuffersBuffer put(byte[] src, int offset, int length) {
      this.checkDispose();
      this.checkReadOnly();
      if (this.remaining() < length) {
         throw new BufferOverflowException();
      } else {
         this.checkIndex(this.position);
         int bufferIdx = this.lastSegmentIndex;
         Buffer buffer = this.activeBuffer;
         int bufferPosition = this.toActiveBufferPos(this.position);

         while(true) {
            int oldPos = buffer.position();
            buffer.position(bufferPosition);
            int bytesToCopy = Math.min(buffer.remaining(), length);
            buffer.put(src, offset, bytesToCopy);
            buffer.position(oldPos);
            length -= bytesToCopy;
            offset += bytesToCopy;
            this.position += bytesToCopy;
            if (length == 0) {
               return this;
            }

            ++bufferIdx;
            buffer = this.buffers[bufferIdx];
            bufferPosition = buffer.position();
         }
      }
   }

   public BuffersBuffer put8BitString(String s) {
      int len = s.length();
      if (this.remaining() < len) {
         throw new BufferOverflowException();
      } else {
         for(int i = 0; i < len; ++i) {
            this.put((byte)s.charAt(i));
         }

         return this;
      }
   }

   public BuffersBuffer get(ByteBuffer dst) {
      this.get(dst, dst.position(), dst.remaining());
      dst.position(dst.limit());
      return this;
   }

   public BuffersBuffer get(ByteBuffer dst, int offset, int length) {
      if (length == 0) {
         return this;
      } else {
         this.checkDispose();
         this.checkReadOnly();
         if (this.remaining() < length) {
            throw new BufferOverflowException();
         } else {
            this.checkIndex(this.position);
            int bufferIdx = this.lastSegmentIndex;
            Buffer buffer = this.activeBuffer;
            int bufferPosition = this.toActiveBufferPos(this.position);

            while(true) {
               int oldPos = buffer.position();
               buffer.position(bufferPosition);
               int bytesToCopy = Math.min(buffer.remaining(), length);
               buffer.get(dst, offset, bytesToCopy);
               buffer.position(oldPos);
               length -= bytesToCopy;
               offset += bytesToCopy;
               this.position += bytesToCopy;
               if (length == 0) {
                  return this;
               }

               ++bufferIdx;
               buffer = this.buffers[bufferIdx];
               bufferPosition = buffer.position();
            }
         }
      }
   }

   public BuffersBuffer put(ByteBuffer src) {
      this.put((ByteBuffer)src, 0, src.remaining());
      src.position(src.limit());
      return this;
   }

   public BuffersBuffer put(ByteBuffer src, int offset, int length) {
      this.checkDispose();
      this.checkReadOnly();
      if (this.remaining() < length) {
         throw new BufferOverflowException();
      } else {
         this.checkIndex(this.position);
         int bufferIdx = this.lastSegmentIndex;
         Buffer buffer = this.activeBuffer;
         int bufferPosition = this.toActiveBufferPos(this.position);

         while(true) {
            int oldPos = buffer.position();
            buffer.position(bufferPosition);
            int bytesToCopy = Math.min(buffer.remaining(), length);
            buffer.put(src, offset, bytesToCopy);
            buffer.position(oldPos);
            length -= bytesToCopy;
            offset += bytesToCopy;
            this.position += bytesToCopy;
            if (length == 0) {
               return this;
            }

            ++bufferIdx;
            buffer = this.buffers[bufferIdx];
            bufferPosition = buffer.position();
         }
      }
   }

   public BuffersBuffer put(Buffer src) {
      this.put(src, src.position(), src.remaining());
      src.position(src.limit());
      return this;
   }

   public Buffer put(Buffer src, int position, int length) {
      this.checkDispose();
      this.checkReadOnly();
      Buffers.put((Buffer)src, position, length, (Buffer)this);
      return this;
   }

   public char getChar() {
      char value = this.getChar(this.position);
      this.position += 2;
      return value;
   }

   public BuffersBuffer putChar(char value) {
      this.putChar(this.position, value);
      this.position += 2;
      return this;
   }

   public char getChar(int index) {
      this.checkDispose();
      this.checkIndex(index);
      if (this.upperBound - index >= 2) {
         return this.activeBuffer.getChar(this.toActiveBufferPos(index));
      } else {
         return this.bigEndian ? this.makeCharB(index) : this.makeCharL(index);
      }
   }

   public BuffersBuffer putChar(int index, char value) {
      this.checkDispose();
      this.checkReadOnly();
      this.checkIndex(index);
      if (this.upperBound - index >= 2) {
         this.activeBuffer.putChar(this.toActiveBufferPos(index), value);
      } else if (this.bigEndian) {
         this.putCharB(index, value);
      } else {
         this.putCharL(index, value);
      }

      return this;
   }

   public short getShort() {
      short value = this.getShort(this.position);
      this.position += 2;
      return value;
   }

   public BuffersBuffer putShort(short value) {
      this.putShort(this.position, value);
      this.position += 2;
      return this;
   }

   public short getShort(int index) {
      this.checkDispose();
      this.checkIndex(index);
      if (this.upperBound - index >= 2) {
         return this.activeBuffer.getShort(this.toActiveBufferPos(index));
      } else {
         return this.bigEndian ? this.makeShortB(index) : this.makeShortL(index);
      }
   }

   public BuffersBuffer putShort(int index, short value) {
      this.checkDispose();
      this.checkReadOnly();
      this.checkIndex(index);
      if (this.upperBound - index >= 2) {
         this.activeBuffer.putShort(this.toActiveBufferPos(index), value);
      } else if (this.bigEndian) {
         this.putShortB(index, value);
      } else {
         this.putShortL(index, value);
      }

      return this;
   }

   public int getInt() {
      int value = this.getInt(this.position);
      this.position += 4;
      return value;
   }

   public BuffersBuffer putInt(int value) {
      this.putInt(this.position, value);
      this.position += 4;
      return this;
   }

   public int getInt(int index) {
      this.checkDispose();
      this.checkIndex(index);
      if (this.upperBound - index >= 4) {
         return this.activeBuffer.getInt(this.toActiveBufferPos(index));
      } else {
         return this.bigEndian ? this.makeIntB(index) : this.makeIntL(index);
      }
   }

   public BuffersBuffer putInt(int index, int value) {
      this.checkDispose();
      this.checkReadOnly();
      this.checkIndex(index);
      if (this.upperBound - index >= 4) {
         this.activeBuffer.putInt(this.toActiveBufferPos(index), value);
      } else if (this.bigEndian) {
         this.putIntB(index, value);
      } else {
         this.putIntL(index, value);
      }

      return this;
   }

   public long getLong() {
      long value = this.getLong(this.position);
      this.position += 8;
      return value;
   }

   public BuffersBuffer putLong(long value) {
      this.putLong(this.position, value);
      this.position += 8;
      return this;
   }

   public long getLong(int index) {
      this.checkDispose();
      this.checkIndex(index);
      if (this.upperBound - index >= 8) {
         return this.activeBuffer.getLong(this.toActiveBufferPos(index));
      } else {
         return this.bigEndian ? this.makeLongB(index) : this.makeLongL(index);
      }
   }

   public BuffersBuffer putLong(int index, long value) {
      this.checkDispose();
      this.checkReadOnly();
      this.checkIndex(index);
      if (this.upperBound - index >= 8) {
         this.activeBuffer.putLong(this.toActiveBufferPos(index), value);
      } else if (this.bigEndian) {
         this.putLongB(index, value);
      } else {
         this.putLongL(index, value);
      }

      return this;
   }

   public float getFloat() {
      return Float.intBitsToFloat(this.getInt());
   }

   public BuffersBuffer putFloat(float value) {
      return this.putInt(Float.floatToIntBits(value));
   }

   public float getFloat(int index) {
      return Float.intBitsToFloat(this.getInt(index));
   }

   public BuffersBuffer putFloat(int index, float value) {
      return this.putInt(index, Float.floatToIntBits(value));
   }

   public double getDouble() {
      return Double.longBitsToDouble(this.getLong());
   }

   public BuffersBuffer putDouble(double value) {
      return this.putLong(Double.doubleToLongBits(value));
   }

   public double getDouble(int index) {
      return Double.longBitsToDouble(this.getLong(index));
   }

   public BuffersBuffer putDouble(int index, double value) {
      return this.putLong(index, Double.doubleToLongBits(value));
   }

   public int bulk(CompositeBuffer.BulkOperation operation) {
      return this.bulk(operation, this.position, this.limit);
   }

   public int bulk(CompositeBuffer.BulkOperation operation, int position, int limit) {
      this.checkDispose();
      int length = limit - position;
      if (length == 0) {
         return -1;
      } else {
         int offset = position;
         this.checkIndex(position);
         int bufferIdx = this.lastSegmentIndex;
         Buffer buffer = this.activeBuffer;
         int bufferPosition = this.toActiveBufferPos(position);

         while(true) {
            int bytesToProcess = Math.min(buffer.limit() - bufferPosition, length);
            int i;
            if (buffer.isComposite()) {
               i = ((CompositeBuffer)buffer).bulk(operation, bufferPosition, bufferPosition + bytesToProcess);
               if (i != -1) {
                  return offset + (i - bufferPosition);
               }
            } else {
               this.setter.buffer = buffer;

               for(i = bufferPosition; i < bufferPosition + bytesToProcess; ++i) {
                  this.setter.position = i;
                  if (operation.processByte(buffer.get(i), this.setter)) {
                     return offset + (i - bufferPosition);
                  }
               }
            }

            length -= bytesToProcess;
            if (length == 0) {
               return -1;
            }

            offset += bytesToProcess;
            ++bufferIdx;
            buffer = this.buffers[bufferIdx];
            bufferPosition = buffer.position();
         }
      }
   }

   public int compareTo(Buffer that) {
      this.checkDispose();
      int n = this.position() + Math.min(this.remaining(), that.remaining());
      int i = this.position();

      for(int j = that.position(); i < n; ++j) {
         byte v1 = this.get(i);
         byte v2 = that.get(j);
         if (v1 != v2) {
            if (v1 < v2) {
               return -1;
            }

            return 1;
         }

         ++i;
      }

      return this.remaining() - that.remaining();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("BuffersBuffer (" + System.identityHashCode(this) + ") [");
      sb.append("pos=").append(this.position);
      sb.append(" lim=").append(this.limit);
      sb.append(" cap=").append(this.capacity);
      sb.append(" bufferSize=").append(this.buffersSize);
      sb.append(" buffers=").append(Arrays.toString(this.buffers));
      sb.append(']');
      return sb.toString();
   }

   public String toStringContent() {
      return this.toStringContent((Charset)null, this.position, this.limit);
   }

   public String toStringContent(Charset charset) {
      return this.toStringContent(charset, this.position, this.limit);
   }

   public String toStringContent(Charset charset, int position, int limit) {
      this.checkDispose();
      if (charset == null) {
         charset = Charset.defaultCharset();
      }

      byte[] tmpBuffer = new byte[limit - position];
      int oldPosition = this.position;
      int oldLimit = this.limit;
      this.setPosLim(position, limit);
      this.get(tmpBuffer);
      this.setPosLim(oldPosition, oldLimit);

      try {
         return new String(tmpBuffer, charset.name());
      } catch (UnsupportedEncodingException var8) {
         throw new IllegalStateException("We took charset name from Charset, why it's not unsupported?", var8);
      }
   }

   public void dumpHex(Appendable appendable) {
      Buffers.dumpBuffer(appendable, this);
   }

   public ByteBuffer toByteBuffer() {
      return this.toByteBuffer(this.position, this.limit);
   }

   public ByteBuffer toByteBuffer(int position, int limit) {
      if (position >= 0 && position <= this.capacity && limit >= 0 && limit <= this.capacity) {
         if (this.buffersSize != 0 && position != limit) {
            int bufPosition;
            if (this.buffersSize == 1) {
               Buffer buffer = this.buffers[0];
               bufPosition = buffer.position();
               return buffer.toByteBuffer(bufPosition + position, bufPosition + limit);
            } else {
               this.checkIndex(position);
               int pos1 = this.lastSegmentIndex;
               bufPosition = this.toActiveBufferPos(position);
               this.checkIndex(limit - 1);
               int pos2 = this.lastSegmentIndex;
               int bufLimit = this.toActiveBufferPos(limit);
               if (pos1 == pos2) {
                  Buffer buffer = this.buffers[pos1];
                  return buffer.toByteBuffer(bufPosition, bufLimit);
               } else {
                  ByteBuffer resultByteBuffer = MemoryUtils.allocateByteBuffer(this.memoryManager, limit - position);
                  Buffer startBuffer = this.buffers[pos1];
                  ByteBufferArray array = ByteBufferArray.create();
                  this.fillByteBuffer(resultByteBuffer, startBuffer.toByteBufferArray(array, bufPosition, startBuffer.limit()));

                  for(int i = pos1 + 1; i < pos2; ++i) {
                     this.fillByteBuffer(resultByteBuffer, this.buffers[i].toByteBufferArray(array));
                  }

                  Buffer endBuffer = this.buffers[pos2];
                  this.fillByteBuffer(resultByteBuffer, endBuffer.toByteBufferArray(array, endBuffer.position(), bufLimit));
                  array.restore();
                  array.recycle();
                  return (ByteBuffer)resultByteBuffer.flip();
               }
            }
         } else {
            return Buffers.EMPTY_BYTE_BUFFER;
         }
      } else {
         throw new IndexOutOfBoundsException("position=" + position + " limit=" + limit + "on " + this.toString());
      }
   }

   public final ByteBufferArray toByteBufferArray() {
      return this.toByteBufferArray(this.position, this.limit);
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array) {
      if (this.position == 0 && this.limit == this.capacity) {
         for(int i = 0; i < this.buffersSize; ++i) {
            this.buffers[i].toByteBufferArray(array);
         }

         return array;
      } else {
         return this.toByteBufferArray(array, this.position, this.limit);
      }
   }

   public final ByteBufferArray toByteBufferArray(int position, int limit) {
      ByteBufferArray array = ByteBufferArray.create();
      if (position == 0 && limit == this.capacity) {
         for(int i = 0; i < this.buffersSize; ++i) {
            this.buffers[i].toByteBufferArray(array);
         }

         return array;
      } else {
         return this.toByteBufferArray(array, position, limit);
      }
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array, int position, int limit) {
      if (position >= 0 && position <= this.capacity && limit >= 0 && limit <= this.capacity) {
         if (this.buffersSize != 0 && position != limit) {
            int bufPosition;
            if (this.buffersSize == 1) {
               Buffer b = this.buffers[0];
               bufPosition = b.position();
               return b.toByteBufferArray(array, position + bufPosition, limit + bufPosition);
            } else {
               int pos1;
               if (position == 0 && limit == this.capacity) {
                  for(pos1 = 0; pos1 < this.buffersSize; ++pos1) {
                     this.buffers[pos1].toByteBufferArray(array);
                  }

                  return array;
               } else {
                  this.checkIndex(position);
                  pos1 = this.lastSegmentIndex;
                  bufPosition = this.toActiveBufferPos(position);
                  this.checkIndex(limit - 1);
                  int pos2 = this.lastSegmentIndex;
                  int bufLimit = this.toActiveBufferPos(limit);
                  Buffer startBuffer;
                  if (pos1 == pos2) {
                     startBuffer = this.buffers[pos1];
                     return startBuffer.toByteBufferArray(array, bufPosition, bufLimit);
                  } else {
                     startBuffer = this.buffers[pos1];
                     startBuffer.toByteBufferArray(array, bufPosition, startBuffer.limit());

                     for(int i = pos1 + 1; i < pos2; ++i) {
                        Buffer srcBuffer = this.buffers[i];
                        srcBuffer.toByteBufferArray(array);
                     }

                     Buffer endBuffer = this.buffers[pos2];
                     endBuffer.toByteBufferArray(array, endBuffer.position(), bufLimit);
                     return array;
                  }
               }
            }
         } else {
            return array;
         }
      } else {
         throw new IndexOutOfBoundsException("position=" + position + " limit=" + limit + "on " + this.toString());
      }
   }

   public final BufferArray toBufferArray() {
      return this.toBufferArray(this.position, this.limit);
   }

   public final BufferArray toBufferArray(BufferArray array) {
      if (this.position == 0 && this.limit == this.capacity) {
         for(int i = 0; i < this.buffersSize; ++i) {
            this.buffers[i].toBufferArray(array);
         }

         return array;
      } else {
         return this.toBufferArray(array, this.position, this.limit);
      }
   }

   public final BufferArray toBufferArray(int position, int limit) {
      BufferArray array = BufferArray.create();
      if (position == 0 && limit == this.capacity) {
         for(int i = 0; i < this.buffersSize; ++i) {
            this.buffers[i].toBufferArray(array);
         }

         return array;
      } else {
         return this.toBufferArray(array, position, limit);
      }
   }

   public final BufferArray toBufferArray(BufferArray array, int position, int limit) {
      if (position >= 0 && position <= this.capacity && limit >= 0 && limit <= this.capacity) {
         if (this.buffersSize != 0 && position != limit) {
            int bufPosition;
            if (this.buffersSize == 1) {
               Buffer b = this.buffers[0];
               bufPosition = b.position();
               return b.toBufferArray(array, position + bufPosition, limit + bufPosition);
            } else {
               int pos1;
               if (position == 0 && limit == this.capacity) {
                  for(pos1 = 0; pos1 < this.buffersSize; ++pos1) {
                     this.buffers[pos1].toBufferArray(array);
                  }

                  return array;
               } else {
                  this.checkIndex(position);
                  pos1 = this.lastSegmentIndex;
                  bufPosition = this.toActiveBufferPos(position);
                  this.checkIndex(limit - 1);
                  int pos2 = this.lastSegmentIndex;
                  int bufLimit = this.toActiveBufferPos(limit);
                  Buffer startBuffer;
                  if (pos1 == pos2) {
                     startBuffer = this.buffers[pos1];
                     return startBuffer.toBufferArray(array, bufPosition, bufLimit);
                  } else {
                     startBuffer = this.buffers[pos1];
                     startBuffer.toBufferArray(array, bufPosition, startBuffer.limit());

                     for(int i = pos1 + 1; i < pos2; ++i) {
                        Buffer srcBuffer = this.buffers[i];
                        srcBuffer.toBufferArray(array);
                     }

                     Buffer endBuffer = this.buffers[pos2];
                     endBuffer.toBufferArray(array, endBuffer.position(), bufLimit);
                     return array;
                  }
               }
            }
         } else {
            return array;
         }
      } else {
         throw new IndexOutOfBoundsException("position=" + position + " limit=" + limit + "on " + this.toString());
      }
   }

   public void removeAll() {
      this.position = 0;
      this.limit = 0;
      this.capacity = 0;
      Arrays.fill(this.buffers, 0, this.buffersSize, (Object)null);
      this.buffersSize = 0;
      this.resetLastLocation();
   }

   public boolean equals(Object obj) {
      if (obj instanceof Buffer) {
         Buffer that = (Buffer)obj;
         if (this.remaining() != that.remaining()) {
            return false;
         } else {
            int p = this.position();
            int i = this.limit() - 1;

            for(int j = that.limit() - 1; i >= p; --j) {
               byte v1 = this.get(i);
               byte v2 = that.get(j);
               if (v1 != v2) {
                  return false;
               }

               --i;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean hasArray() {
      return false;
   }

   public byte[] array() {
      throw new UnsupportedOperationException();
   }

   public int arrayOffset() {
      throw new UnsupportedOperationException();
   }

   public int hashCode() {
      int h = 1;
      int p = this.position();

      for(int i = this.limit() - 1; i >= p; --i) {
         h = 31 * h + this.get(i);
      }

      h = 31 * h + this.mark;
      return h;
   }

   public boolean release() {
      return this.tryDispose();
   }

   public boolean isExternal() {
      return false;
   }

   private void fillByteBuffer(ByteBuffer bb, ByteBufferArray array) {
      ByteBuffer[] bbs = (ByteBuffer[])array.getArray();
      int size = array.size();

      for(int i = 0; i < size; ++i) {
         ByteBuffer srcByteBuffer = bbs[i];
         bb.put(srcByteBuffer);
      }

   }

   private void removeAndDisposeBuffers() {
      boolean isNulled = false;
      if (this.allowInternalBuffersDispose) {
         int i;
         Buffer buffer;
         if (this.disposeOrder != CompositeBuffer.DisposeOrder.FIRST_TO_LAST) {
            for(i = this.buffersSize - 1; i >= 0; --i) {
               buffer = this.buffers[i];
               buffer.tryDispose();
               this.buffers[i] = null;
            }
         } else {
            for(i = 0; i < this.buffersSize; ++i) {
               buffer = this.buffers[i];
               buffer.tryDispose();
               this.buffers[i] = null;
            }
         }

         isNulled = true;
      }

      this.position = 0;
      this.limit = 0;
      this.capacity = 0;
      this.mark = -1;
      if (!isNulled) {
         Arrays.fill(this.buffers, 0, this.buffersSize, (Object)null);
      }

      this.buffersSize = 0;
      this.disposeOrder = CompositeBuffer.DisposeOrder.LAST_TO_FIRST;
      this.allowBufferDispose = false;
      this.allowInternalBuffersDispose = true;
      this.resetLastLocation();
   }

   private void setPosLim(int position, int limit) {
      if (position > limit) {
         throw new IllegalArgumentException("Position exceeds a limit: " + position + '>' + limit);
      } else {
         this.position = position;
         this.limit = limit;
      }
   }

   private void checkDispose() {
      if (this.isDisposed) {
         throw new IllegalStateException("CompositeBuffer has already been disposed", this.disposeStackTrace);
      }
   }

   private void checkReadOnly() {
      if (this.isReadOnly) {
         throw new IllegalStateException("Buffer is in read-only mode");
      }
   }

   private void refreshBuffers() {
      int currentCapacity = 0;

      for(int i = 0; i < this.buffersSize; ++i) {
         Buffer buffer = this.buffers[i];
         currentCapacity += buffer.remaining();
         this.bufferBounds[i] = currentCapacity;
         buffer.order(this.byteOrder);
      }

      this.capacity = currentCapacity;
   }

   private void resetLastLocation() {
      this.lowerBound = 0;
      this.upperBound = 0;
      this.activeBuffer = null;
   }

   private long makeLongL(int index) {
      index += 7;
      this.checkIndex(index);
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b3 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b4 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b5 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b6 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b7 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b8 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeLong(b1, b2, b3, b4, b5, b6, b7, b8);
   }

   private long makeLongB(int index) {
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b3 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b4 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b5 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b6 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b7 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b8 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeLong(b1, b2, b3, b4, b5, b6, b7, b8);
   }

   private void putLongL(int index, long value) {
      index += 7;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long7(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long6(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long5(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long4(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long3(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long2(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long1(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long0(value));
   }

   private void putLongB(int index, long value) {
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long7(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long6(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long5(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long4(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long3(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long2(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long1(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.long0(value));
   }

   private void putIntL(int index, int value) {
      index += 3;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int3(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int2(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int1(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int0(value));
   }

   private void putIntB(int index, int value) {
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int3(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int2(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int1(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.int0(value));
   }

   private int makeIntL(int index) {
      index += 3;
      this.checkIndex(index);
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b3 = this.activeBuffer.get(this.toActiveBufferPos(index));
      --index;
      this.checkIndex(index);
      byte b4 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeInt(b1, b2, b3, b4);
   }

   private int makeIntB(int index) {
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b3 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b4 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeInt(b1, b2, b3, b4);
   }

   private void putShortL(int index, short value) {
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.short0(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.short1(value));
   }

   private void putShortB(int index, short value) {
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.short1(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.short0(value));
   }

   private short makeShortL(int index) {
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeShort(b2, b1);
   }

   private short makeShortB(int index) {
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeShort(b1, b2);
   }

   private void putCharL(int index, char value) {
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.char0(value));
      --index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.char1(value));
   }

   private void putCharB(int index, char value) {
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.char1(value));
      ++index;
      this.checkIndex(index);
      this.activeBuffer.put(this.toActiveBufferPos(index), Bits.char0(value));
   }

   private char makeCharL(int index) {
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeChar(b2, b1);
   }

   private char makeCharB(int index) {
      byte b1 = this.activeBuffer.get(this.toActiveBufferPos(index));
      ++index;
      this.checkIndex(index);
      byte b2 = this.activeBuffer.get(this.toActiveBufferPos(index));
      return Bits.makeChar(b1, b2);
   }

   private static class DebugLogic {
      static void doDebug(BuffersBuffer buffersBuffer) {
         buffersBuffer.disposeStackTrace = new Exception("BuffersBuffer was disposed from: ");
      }
   }

   private static final class SetterImpl implements CompositeBuffer.Setter {
      private Buffer buffer;
      private int position;

      private SetterImpl() {
      }

      public void set(byte value) {
         this.buffer.put(this.position, value);
      }

      // $FF: synthetic method
      SetterImpl(Object x0) {
         this();
      }
   }
}

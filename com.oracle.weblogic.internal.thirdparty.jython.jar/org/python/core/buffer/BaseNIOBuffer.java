package org.python.core.buffer;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public abstract class BaseNIOBuffer extends Base1DBuffer {
   protected ByteBuffer storage;

   protected BaseNIOBuffer(ByteBuffer storage, int featureFlags, int index0, int size, int stride) {
      super(featureFlags & -268435458, index0, size, stride);
      this.storage = storage;
      if (!storage.isReadOnly()) {
         this.addFeatureFlags(1);
      }

      if (storage.hasArray()) {
         this.addFeatureFlags(268435456);
      }

   }

   protected byte byteAtImpl(int byteIndex) throws IndexOutOfBoundsException {
      return this.storage.get(byteIndex);
   }

   protected void storeAtImpl(byte value, int byteIndex) throws PyException {
      try {
         this.storage.put(byteIndex, value);
      } catch (ReadOnlyBufferException var4) {
         throw notWritable();
      }
   }

   public int byteIndex(int... indices) throws IndexOutOfBoundsException {
      this.checkDimension(indices.length);
      return this.byteIndex(indices[0]);
   }

   public void copyTo(int srcIndex, byte[] dest, int destPos, int count) throws IndexOutOfBoundsException {
      ByteBuffer destBuf = ByteBuffer.wrap(dest, destPos, count * this.getItemsize());
      this.copyTo(srcIndex, destBuf, count);
   }

   public void copyTo(ByteBuffer dest) throws BufferOverflowException, ReadOnlyBufferException {
      this.copyTo(0, dest, this.shape[0]);
   }

   protected void copyTo(int srcIndex, ByteBuffer dest, int count) throws BufferOverflowException, ReadOnlyBufferException, IndexOutOfBoundsException {
      if (count > 0) {
         ByteBuffer src = this.getNIOByteBuffer();
         int pos = this.byteIndex(srcIndex);
         int itemsize = this.getItemsize();
         int stride = this.getStrides()[0];
         if (stride == itemsize) {
            src.limit(pos + count * itemsize).position(pos);
            dest.put(src);
         } else {
            int i;
            if (itemsize == 1) {
               for(i = 0; i < count; ++i) {
                  src.position(pos);
                  dest.put(src.get());
                  pos += stride;
               }
            } else {
               for(i = 0; i < count; ++i) {
                  src.limit(pos + itemsize).position(pos);
                  dest.put(src);
                  pos += stride;
               }
            }
         }
      }

   }

   public void copyFrom(byte[] src, int srcPos, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      ByteBuffer srcBuf = ByteBuffer.wrap(src, srcPos, count * this.getItemsize());
      this.copyFrom(srcBuf, destIndex, count);
   }

   protected void copyFrom(ByteBuffer src, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      this.checkWritable();
      if (count > 0) {
         ByteBuffer dest = this.getNIOByteBuffer();
         int pos = this.byteIndex(destIndex);
         int itemsize = this.getItemsize();
         int stride = this.getStrides()[0];
         int skip = stride - itemsize;
         int size = this.getSize();
         if ((destIndex | count | size - (destIndex + count)) < 0) {
            throw new IndexOutOfBoundsException();
         }

         if (skip == 0) {
            dest.position(pos);
            dest.put(src);
         } else {
            int i;
            if (itemsize == 1) {
               for(i = 0; i < count; ++i) {
                  dest.position(pos);
                  dest.put(src.get());
                  pos += stride;
               }
            } else {
               for(i = 0; i < count; ++i) {
                  dest.position(pos);
                  src.limit(src.position() + itemsize);
                  dest.put(src);
                  pos += stride;
               }
            }
         }
      }

   }

   protected ByteBuffer getNIOByteBufferImpl() {
      return this.storage.duplicate();
   }

   public PyBuffer.Pointer getBuf() {
      this.checkHasArray();
      return new PyBuffer.Pointer(this.storage.array(), this.index0);
   }
}

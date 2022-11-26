package org.python.core.buffer;

import java.nio.ByteBuffer;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public abstract class BaseArrayBuffer extends Base1DBuffer {
   protected byte[] storage;

   protected BaseArrayBuffer(byte[] storage, int featureFlags, int index0, int size, int stride) {
      super(featureFlags | 268435456, index0, size, stride);
      this.storage = storage;
   }

   protected byte byteAtImpl(int byteIndex) throws IndexOutOfBoundsException {
      return this.storage[byteIndex];
   }

   protected void storeAtImpl(byte value, int byteIndex) throws IndexOutOfBoundsException, PyException {
      this.checkWritable();
      this.storage[byteIndex] = value;
   }

   public int byteIndex(int... indices) throws IndexOutOfBoundsException {
      this.checkDimension(indices.length);
      return this.byteIndex(indices[0]);
   }

   public void copyTo(int srcIndex, byte[] dest, int destPos, int count) throws IndexOutOfBoundsException {
      if (count > 0) {
         int itemsize = this.getItemsize();
         int stride = this.getStrides()[0];
         int skip = stride - itemsize;
         int s = this.byteIndex(srcIndex);
         if (skip == 0) {
            System.arraycopy(this.storage, s, dest, destPos, count * itemsize);
         } else {
            int limit = s + count * stride;
            int d = destPos;
            if (itemsize == 1) {
               while(s != limit) {
                  dest[d++] = this.storage[s];
                  s += stride;
               }
            } else {
               while(s != limit) {
                  for(int t = s + itemsize; s < t; dest[d++] = this.storage[s++]) {
                  }

                  s += skip;
               }
            }
         }
      }

   }

   public void copyFrom(byte[] src, int srcPos, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      this.copyFrom(src, srcPos, 1, destIndex, count);
   }

   protected void copyFrom(byte[] src, int srcPos, int srcStride, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      this.checkWritable();
      if (count > 0) {
         int itemsize = this.getItemsize();
         int stride = this.getStrides()[0];
         int skip = stride - itemsize;
         int d = this.byteIndex(destIndex);
         int srcSkip = srcStride - itemsize;
         if (skip == 0 && srcSkip == 0) {
            System.arraycopy(src, srcPos, this.storage, d, count * itemsize);
         } else {
            int limit = d + count * stride;
            int s = srcPos;
            if (itemsize == 1) {
               while(d != limit) {
                  this.storage[d] = src[s];
                  s += srcStride;
                  d += stride;
               }
            } else {
               while(d != limit) {
                  for(int t = d + itemsize; d < t; this.storage[d++] = src[s++]) {
                  }

                  s += srcSkip;
                  d += skip;
               }
            }
         }
      }

   }

   public void copyFrom(PyBuffer src) throws IndexOutOfBoundsException, PyException {
      if (src instanceof BaseArrayBuffer && !this.overlaps((BaseArrayBuffer)src)) {
         this.copyFromArrayBuffer((BaseArrayBuffer)src);
      } else {
         super.copyFrom(src);
      }

   }

   private boolean overlaps(BaseArrayBuffer src) {
      if (src.storage != this.storage) {
         return false;
      } else {
         int low = this.calcLeastIndex();
         int high = this.calcGreatestIndex();
         int srcLow = src.calcLeastIndex();
         int srcHigh = src.calcGreatestIndex();
         return srcHigh >= low && high >= srcLow;
      }
   }

   private void copyFromArrayBuffer(BaseArrayBuffer src) throws IndexOutOfBoundsException, PyException {
      src.checkDimension(1);
      int itemsize = this.getItemsize();
      int count = this.getSize();
      if (src.getItemsize() == itemsize && src.getSize() == count) {
         this.copyFrom(src.storage, src.index0, src.strides[0], 0, count);
      } else {
         throw differentStructure();
      }
   }

   protected ByteBuffer getNIOByteBufferImpl() {
      ByteBuffer b = ByteBuffer.wrap(this.storage);
      return this.isReadonly() ? b.asReadOnlyBuffer() : b;
   }

   public PyBuffer.Pointer getBuf() {
      return new PyBuffer.Pointer(this.storage, this.index0);
   }
}

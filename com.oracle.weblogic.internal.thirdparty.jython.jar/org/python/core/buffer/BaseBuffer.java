package org.python.core.buffer;

import java.nio.ByteBuffer;
import org.python.core.BufferProtocol;
import org.python.core.Py;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public abstract class BaseBuffer implements PyBuffer {
   protected BufferProtocol obj;
   protected int[] shape;
   protected int[] strides;
   protected int index0;
   protected int exports = 1;
   private int gFeatureFlags = -281;

   protected BaseBuffer(int featureFlags, int index0, int[] shape, int[] strides) {
      this.setFeatureFlags(featureFlags | 4);
      this.index0 = index0;
      this.shape = shape;
      this.strides = strides;
   }

   protected final int getFeatureFlags() {
      return 280 ^ ~this.gFeatureFlags;
   }

   protected final void setFeatureFlags(int flags) {
      this.gFeatureFlags = -281 ^ flags;
   }

   protected final void addFeatureFlags(int flags) {
      this.setFeatureFlags(flags | this.getFeatureFlags());
   }

   protected final void removeFeatureFlags(int flags) {
      this.setFeatureFlags(~flags & this.getFeatureFlags());
   }

   protected void checkRequestFlags(int flags) throws PyException {
      int syndrome = this.gFeatureFlags & (flags ^ 280);
      if (syndrome != 0) {
         throw bufferErrorFromSyndrome(syndrome);
      }
   }

   public boolean isReadonly() {
      return (this.gFeatureFlags & 1) != 0;
   }

   public int getNdim() {
      return this.shape.length;
   }

   public int[] getShape() {
      return this.shape;
   }

   protected int getSize() {
      int N = this.shape.length;
      int size = this.shape[0];

      for(int k = 1; k < N; ++k) {
         size *= this.shape[k];
      }

      return size;
   }

   public int getLen() {
      int N = this.shape.length;
      int len = this.getItemsize();

      for(int k = 0; k < N; ++k) {
         len *= this.shape[k];
      }

      return len;
   }

   public final BufferProtocol getObj() {
      return this.obj;
   }

   protected abstract byte byteAtImpl(int var1) throws IndexOutOfBoundsException;

   protected abstract void storeAtImpl(byte var1, int var2) throws IndexOutOfBoundsException, PyException;

   public byte byteAt(int index) throws IndexOutOfBoundsException {
      return this.byteAtImpl(this.byteIndex(index));
   }

   public int intAt(int index) throws IndexOutOfBoundsException {
      return 255 & this.byteAtImpl(this.byteIndex(index));
   }

   public void storeAt(byte value, int index) throws IndexOutOfBoundsException, PyException {
      this.storeAtImpl(value, this.byteIndex(index));
   }

   public byte byteAt(int... indices) throws IndexOutOfBoundsException {
      return this.byteAtImpl(this.byteIndex(indices));
   }

   public int intAt(int... indices) throws IndexOutOfBoundsException {
      return 255 & this.byteAt(indices);
   }

   public void storeAt(byte value, int... indices) throws IndexOutOfBoundsException, PyException {
      this.storeAtImpl(value, this.byteIndex(indices));
   }

   public int byteIndex(int index) throws IndexOutOfBoundsException {
      if (index >= 0 && index < this.shape[0]) {
         return this.index0 + index * this.strides[0];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int byteIndex(int... indices) throws IndexOutOfBoundsException {
      int N = this.checkDimension(indices);
      int index = this.index0;

      for(int k = 0; k < N; ++k) {
         int ik = indices[k];
         if (ik < 0 || ik >= this.shape[k]) {
            throw new IndexOutOfBoundsException();
         }

         index += ik * this.strides[k];
      }

      return index;
   }

   protected int calcGreatestIndex() {
      int N = this.shape.length;
      int index = this.index0;
      int[] strides = this.getStrides();

      for(int k = 0; k < N; ++k) {
         int stride = strides[k];
         if (stride > 0) {
            index += (this.shape[k] - 1) * stride;
         }
      }

      return index;
   }

   protected int calcLeastIndex() {
      int N = this.shape.length;
      int index = this.index0;
      int[] strides = this.getStrides();

      for(int k = 0; k < N; ++k) {
         int stride = strides[k];
         if (stride < 0) {
            index += (this.shape[k] - 1) * stride;
         }
      }

      return index;
   }

   public void copyTo(byte[] dest, int destPos) throws IndexOutOfBoundsException {
      this.copyTo(0, dest, destPos, this.getSize());
   }

   public void copyTo(int srcIndex, byte[] dest, int destPos, int count) throws IndexOutOfBoundsException, PyException {
      this.checkDimension(1);
      int itemsize = this.getItemsize();
      int s = srcIndex;
      int d = destPos;
      int i;
      if (itemsize == 1) {
         for(i = 0; i < count; ++i) {
            dest[d++] = this.byteAt(s++);
         }
      } else {
         for(i = 0; i < count; ++i) {
            int p = this.byteIndex(s++);

            for(int j = 0; j < itemsize; ++j) {
               dest[d++] = this.byteAtImpl(p + j);
            }
         }
      }

   }

   public void copyFrom(byte[] src, int srcPos, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      this.checkDimension(1);
      this.checkWritable();
      int itemsize = this.getItemsize();
      int d = destIndex;
      int s = srcPos;
      int i;
      if (itemsize == 1) {
         for(i = 0; i < count; ++i) {
            this.storeAt(src[s++], d++);
         }
      } else {
         for(i = 0; i < count; ++i) {
            int p = this.byteIndex(d++);

            for(int j = 0; j < itemsize; ++j) {
               this.storeAtImpl(src[s++], p++);
            }
         }
      }

   }

   public void copyFrom(PyBuffer src) throws IndexOutOfBoundsException, PyException {
      this.checkDimension(1);
      this.checkWritable();
      int itemsize = this.getItemsize();
      int count = this.getSize();
      int byteLen = src.getLen();
      if (src.getItemsize() == itemsize && byteLen == count * itemsize) {
         byte[] t = new byte[byteLen];
         src.copyTo(t, 0);
         this.copyFrom(t, 0, 0, count);
      } else {
         throw differentStructure();
      }
   }

   public synchronized PyBuffer getBuffer(int flags) {
      if (this.exports > 0) {
         return this.getBufferAgain(flags);
      } else {
         throw bufferReleased("getBuffer");
      }
   }

   public synchronized BaseBuffer getBufferAgain(int flags) {
      this.checkRequestFlags(flags);
      ++this.exports;
      return this;
   }

   public void release() {
      if (--this.exports == 0) {
         this.releaseAction();
         PyBuffer root = this.getRoot();
         if (root != this) {
            root.release();
         }
      } else if (this.exports < 0) {
         this.exports = 0;
         throw bufferReleased("release");
      }

   }

   public void close() {
      this.release();
   }

   public boolean isReleased() {
      return this.exports <= 0;
   }

   public PyBuffer getBufferSlice(int flags, int start, int count) {
      return this.getBufferSlice(flags, start, count, 1);
   }

   protected abstract ByteBuffer getNIOByteBufferImpl();

   public ByteBuffer getNIOByteBuffer() {
      ByteBuffer b = this.getNIOByteBufferImpl();
      if (this.shape.length == 1 && this.isContiguous('A')) {
         int stride = this.strides[0];
         if (this.getItemsize() == stride) {
            b.limit(this.index0 + this.shape[0] * stride);
         }
      }

      b.position(this.index0);
      return b;
   }

   public boolean hasArray() {
      return (this.gFeatureFlags & 268435456) == 0;
   }

   public PyBuffer.Pointer getBuf() {
      this.checkHasArray();
      return new PyBuffer.Pointer(this.getNIOByteBuffer().array(), this.index0);
   }

   public PyBuffer.Pointer getPointer(int index) throws IndexOutOfBoundsException {
      PyBuffer.Pointer p = this.getBuf();
      p.offset = this.byteIndex(index);
      return p;
   }

   public PyBuffer.Pointer getPointer(int... indices) throws IndexOutOfBoundsException {
      PyBuffer.Pointer p = this.getBuf();
      p.offset = this.byteIndex(indices);
      return p;
   }

   public int[] getStrides() {
      return this.strides;
   }

   public int[] getSuboffsets() {
      return null;
   }

   private boolean isCContiguous() {
      int N = this.shape.length;
      int size = this.getItemsize();

      for(int k = N - 1; k >= 0; --k) {
         int nk = this.shape[k];
         if (nk > 1) {
            if (this.strides[k] != size) {
               return false;
            }

            size *= nk;
         }
      }

      return true;
   }

   private boolean isFortranContiguous() {
      int N = this.shape.length;
      int size = this.getItemsize();

      for(int k = 0; k < N; ++k) {
         int nk = this.shape[k];
         if (nk > 1) {
            if (this.strides[k] != size) {
               return false;
            }

            size *= nk;
         }
      }

      return true;
   }

   public boolean isContiguous(char order) {
      if (this.getSuboffsets() != null) {
         return false;
      } else {
         switch (order) {
            case 'A':
               return this.isCContiguous() || this.isFortranContiguous();
            case 'C':
               return this.isCContiguous();
            case 'F':
               return this.isFortranContiguous();
            default:
               return false;
         }
      }
   }

   public String getFormat() {
      return "B";
   }

   public int getItemsize() {
      return 1;
   }

   protected void releaseAction() {
   }

   protected PyBuffer getRoot() {
      return this;
   }

   public String toString() {
      int n = this.getLen();
      StringBuilder sb = new StringBuilder(n);

      for(int i = 0; i < n; ++i) {
         sb.appendCodePoint(this.intAt(i));
      }

      return sb.toString();
   }

   int checkDimension(int[] indices) throws PyException {
      int n = indices.length;
      this.checkDimension(n);
      return n;
   }

   void checkDimension(int n) throws PyException {
      int ndim = this.getNdim();
      if (n != ndim) {
         String fmt = "buffer with %d dimension%s accessed as having %d dimension%s";
         String msg = String.format(fmt, ndim, ndim == 1 ? "" : "s", n, n, n == 1 ? "" : "s");
         throw Py.BufferError(msg);
      }
   }

   protected void checkWritable() throws PyException {
      if (this.isReadonly()) {
         throw notWritable();
      }
   }

   protected void checkHasArray() throws PyException {
      if (!this.hasArray()) {
         throw bufferIsNot("accessible as a Java array");
      }
   }

   private static PyException bufferErrorFromSyndrome(int syndrome) {
      if ((syndrome & 8) != 0) {
         return bufferRequires("shape array");
      } else if ((syndrome & 1) != 0) {
         return bufferIsNot("writable");
      } else if ((syndrome & 268435456) != 0) {
         return bufferIsNot("accessible as a Java array");
      } else if ((syndrome & 56) != 0) {
         return bufferIsNot("C-contiguous");
      } else if ((syndrome & 88) != 0) {
         return bufferIsNot("Fortran-contiguous");
      } else if ((syndrome & 152) != 0) {
         return bufferIsNot("contiguous");
      } else if ((syndrome & 24) != 0) {
         return bufferRequires("strides array");
      } else {
         return (syndrome & 280) != 0 ? bufferRequires("suboffsets array") : bufferIsNot("capable of matching request");
      }
   }

   protected static PyException notWritable() {
      return Py.TypeError("cannot modify read-only memory");
   }

   protected static PyException bufferIsNot(String property) {
      return Py.BufferError("underlying buffer is not " + property);
   }

   protected static PyException differentStructure() {
      return Py.ValueError("buffer assignment: lvalue and rvalue have different structures");
   }

   protected static PyException bufferRequires(String feature) {
      return Py.BufferError("buffer structure requires consumer to use " + feature);
   }

   protected static PyException bufferReleased(String operation) {
      String op = operation == null ? "" : operation + " ";
      return Py.BufferError(op + "operation forbidden on released buffer object");
   }
}

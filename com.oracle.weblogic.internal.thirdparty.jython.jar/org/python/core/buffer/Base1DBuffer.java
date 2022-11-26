package org.python.core.buffer;

public abstract class Base1DBuffer extends BaseBuffer {
   protected static final int[] ONE = new int[]{1};
   protected static final int[] ZERO = new int[]{0};

   protected Base1DBuffer(int featureFlags, int index0, int size, int[] strides) {
      super(featureFlags, index0, size == 0 ? ZERO : new int[]{size}, strides);
   }

   protected Base1DBuffer(int featureFlags, int index0, int size, int stride) {
      this(featureFlags, index0, size, stride == 1 ? ONE : new int[]{stride});
   }

   protected int getSize() {
      return this.shape[0];
   }

   public int getLen() {
      return this.shape[0] * this.getItemsize();
   }

   protected int calcGreatestIndex() {
      int stride = this.strides[0];
      if (stride == 1) {
         return this.index0 + this.shape[0] - 1;
      } else {
         return stride > 0 ? this.index0 + (this.shape[0] - 1) * stride : this.index0;
      }
   }

   protected int calcLeastIndex() {
      int stride = this.strides[0];
      return stride < 0 ? this.index0 + (this.shape[0] - 1) * stride : this.index0;
   }

   public boolean isContiguous(char order) {
      if ("CFA".indexOf(order) < 0) {
         return false;
      } else if (this.getShape()[0] < 2) {
         return true;
      } else {
         return this.getStrides()[0] == this.getItemsize();
      }
   }
}

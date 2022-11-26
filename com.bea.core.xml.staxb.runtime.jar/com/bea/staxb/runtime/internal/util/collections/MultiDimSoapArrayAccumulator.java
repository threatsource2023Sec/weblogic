package com.bea.staxb.runtime.internal.util.collections;

import java.lang.reflect.Array;

public class MultiDimSoapArrayAccumulator implements Accumulator {
   private final int[] dims;
   private final int[] mults;
   private int size = 0;
   private Object store;

   public MultiDimSoapArrayAccumulator(Class container_type, Class component_type, int[] dims) {
      this.dims = dims;
      int[] ms = new int[dims.length];
      int curr_mult = 1;

      for(int i = dims.length - 1; i > 0; --i) {
         int dim = dims[i];
         if (dim <= 0) {
            throw new IllegalArgumentException("multi dim soap arrays require all dimensions to be specified");
         }

         curr_mult *= dim;

         assert curr_mult > 0;

         ms[i - 1] = curr_mult;
      }

      ms[ms.length - 1] = -1;
      this.mults = ms;

      assert this.mults.length == dims.length;

      this.store = createStore(component_type, dims, container_type);
   }

   private static Object createStore(Class component_type, int[] dims, Class container_type) {
      Object st = Array.newInstance(component_type, dims);
      if (!container_type.isInstance(st)) {
         String msg = "type mismatch.  expected " + container_type + " but constructed " + st.getClass();
         throw new IllegalArgumentException(msg);
      } else {
         return st;
      }
   }

   public void append(Object elem) {
      this.setAtSingleIndex(this.size, elem);
      ++this.size;
   }

   private void setAtSingleIndex(int index, Object elem) {
      int rem = index;
      int curr = true;
      Object curr_target = this.store;
      int dim = 0;

      for(int alen = this.dims.length - 1; dim < alen; ++dim) {
         int m = this.mults[dim];
         int curr = rem / m;
         rem %= m;
         curr_target = Array.get(curr_target, curr);
      }

      Array.set(curr_target, rem, elem);
   }

   public void appendDefault() {
      ++this.size;
   }

   public void set(int index, Object value) {
      this.rangeCheck(index);
      this.setAtSingleIndex(index, value);
   }

   private void rangeCheck(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index " + index + " out of range.  size is " + this.size);
      }
   }

   public int size() {
      return this.size;
   }

   public Object getFinalArray() {
      return this.store;
   }
}

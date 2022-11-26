package org.python.core;

import java.io.Serializable;

public abstract class SequenceIndexDelegate implements Serializable {
   public abstract int len();

   public abstract PyObject getItem(int var1);

   public abstract void setItem(int var1, PyObject var2);

   public abstract void delItem(int var1);

   public abstract PyObject getSlice(int var1, int var2, int var3);

   public abstract void setSlice(int var1, int var2, int var3, PyObject var4);

   public abstract void delItems(int var1, int var2);

   public abstract String getTypeName();

   public void checkIdxAndSetItem(PyObject idx, PyObject value) {
      if (idx.isIndex()) {
         this.checkIdxAndSetItem(idx.asIndex(Py.IndexError), value);
      } else {
         if (!(idx instanceof PySlice)) {
            throw Py.TypeError(this.getTypeName() + " indices must be integers");
         }

         this.checkIdxAndSetSlice((PySlice)idx, value);
      }

   }

   public void checkIdxAndSetSlice(PySlice slice, PyObject value) {
      int[] indices = slice.indicesEx(this.len());
      if (slice.step != Py.None && value.__len__() != indices[3]) {
         throw Py.ValueError(String.format("attempt to assign sequence of size %d to extended slice of size %d", value.__len__(), indices[3]));
      } else {
         this.setSlice(indices[0], indices[1], indices[2], value);
      }
   }

   public void checkIdxAndSetItem(int idx, PyObject value) {
      this.setItem(this.checkIdx(idx), value);
   }

   public void checkIdxAndDelItem(PyObject idx) {
      if (idx.isIndex()) {
         this.delItem(this.checkIdx(idx.asIndex(Py.IndexError)));
      } else {
         if (!(idx instanceof PySlice)) {
            throw Py.TypeError(this.getTypeName() + " indices must be integers");
         }

         PySlice slice = (PySlice)idx;
         this.delSlice(slice.indicesEx(this.len()));
      }

   }

   public PyObject checkIdxAndGetItem(PyObject idx) {
      PyObject res = this.checkIdxAndFindItem(idx);
      if (res == null) {
         throw Py.IndexError("index out of range: " + idx);
      } else {
         return res;
      }
   }

   public PyObject checkIdxAndFindItem(PyObject idx) {
      if (idx.isIndex()) {
         return this.checkIdxAndFindItem(idx.asIndex(Py.IndexError));
      } else if (idx instanceof PySlice) {
         return this.getSlice((PySlice)idx);
      } else {
         throw Py.TypeError(this.getTypeName() + " indices must be integers");
      }
   }

   public PyObject getSlice(PySlice slice) {
      int[] indices = slice.indicesEx(this.len());
      return this.getSlice(indices[0], indices[1], indices[2]);
   }

   public PyObject checkIdxAndFindItem(int idx) {
      idx = this.fixindex(idx);
      return idx == -1 ? null : this.getItem(idx);
   }

   private int checkIdx(int idx) {
      int i = this.fixindex(idx);
      if (i == -1) {
         throw Py.IndexError(this.getTypeName() + " assignment index out of range");
      } else {
         return i;
      }
   }

   int fixindex(int index) {
      int l = this.len();
      if (index < 0) {
         index += l;
      }

      return index >= 0 && index < l ? index : -1;
   }

   protected void delSlice(int[] indices) {
      int p = indices[0];
      int step = indices[2];
      int count = indices[3];
      if (step > 1) {
         --step;

         while(count > 0) {
            this.delItem(p);
            --count;
            p += step;
         }
      } else if (step < 1) {
         while(count > 0) {
            this.delItem(p);
            --count;
            p += step;
         }
      } else if (count > 0) {
         this.delItems(p, p + count);
      }

   }
}

package org.python.core;

public abstract class PySequence extends PyObject {
   protected SequenceIndexDelegate delegator;

   protected PySequence(PyType type) {
      super(type);
      this.delegator = new DefaultIndexDelegate();
   }

   protected PySequence(PyType type, SequenceIndexDelegate behaviour) {
      super(type);
      this.delegator = behaviour;
   }

   protected abstract PyObject pyget(int var1);

   protected abstract PyObject getslice(int var1, int var2, int var3);

   protected abstract PyObject repeat(int var1);

   protected void pyset(int index, PyObject value) {
      throw Py.TypeError("can't assign to immutable object");
   }

   protected void setslice(int start, int stop, int step, PyObject value) {
      throw Py.TypeError(String.format("'%s' object does not support item assignment", this.getType().fastGetName()));
   }

   protected void del(int index) {
      this.delslice(index, index, 1, 1);
   }

   protected void delRange(int start, int stop) {
      this.delslice(start, stop, 1, Math.abs(stop - start));
   }

   protected void delslice(int start, int stop, int step, int n) {
      throw Py.TypeError(String.format("'%s' object does not support item deletion", this.getType().fastGetName()));
   }

   public boolean __nonzero__() {
      return this.seq___nonzero__();
   }

   final boolean seq___nonzero__() {
      return this.__len__() != 0;
   }

   public PyObject __iter__() {
      return this.seq___iter__();
   }

   final PyObject seq___iter__() {
      return new PySequenceIter(this);
   }

   public PyObject __eq__(PyObject o) {
      return this.seq___eq__(o);
   }

   final PyObject seq___eq__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int tl = this.__len__();
         int ol = o.__len__();
         if (tl != ol) {
            return Py.False;
         } else {
            int i = cmp(this, tl, o, ol);
            return i < 0 ? Py.True : Py.False;
         }
      } else {
         return null;
      }
   }

   public PyObject __ne__(PyObject o) {
      return this.seq___ne__(o);
   }

   final PyObject seq___ne__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int tl = this.__len__();
         int ol = o.__len__();
         if (tl != ol) {
            return Py.True;
         } else {
            int i = cmp(this, tl, o, ol);
            return i < 0 ? Py.False : Py.True;
         }
      } else {
         return null;
      }
   }

   public PyObject __lt__(PyObject o) {
      return this.seq___lt__(o);
   }

   final PyObject seq___lt__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int i = cmp(this, -1, o, -1);
         if (i < 0) {
            return i == -1 ? Py.True : Py.False;
         } else {
            return this.__finditem__(i)._lt(o.__finditem__(i));
         }
      } else {
         return null;
      }
   }

   public PyObject __le__(PyObject o) {
      return this.seq___le__(o);
   }

   final PyObject seq___le__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int i = cmp(this, -1, o, -1);
         if (i >= 0) {
            return this.__finditem__(i)._le(o.__finditem__(i));
         } else {
            return i != -1 && i != -2 ? Py.False : Py.True;
         }
      } else {
         return null;
      }
   }

   public PyObject __gt__(PyObject o) {
      return this.seq___gt__(o);
   }

   final PyObject seq___gt__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int i = cmp(this, -1, o, -1);
         if (i < 0) {
            return i == -3 ? Py.True : Py.False;
         } else {
            return this.__finditem__(i)._gt(o.__finditem__(i));
         }
      } else {
         return null;
      }
   }

   public PyObject __ge__(PyObject o) {
      return this.seq___ge__(o);
   }

   final PyObject seq___ge__(PyObject o) {
      if (this.isSubType(o) && o.getType() != PyObject.TYPE) {
         int i = cmp(this, -1, o, -1);
         if (i >= 0) {
            return this.__finditem__(i)._ge(o.__finditem__(i));
         } else {
            return i != -3 && i != -2 ? Py.False : Py.True;
         }
      } else {
         return null;
      }
   }

   protected boolean isSubType(PyObject other) {
      PyType type = this.getType();
      PyType otherType = other.getType();
      return type == otherType || type.isSubType(otherType);
   }

   protected static int cmp(PyObject o1, int ol1, PyObject o2, int ol2) {
      if (ol1 < 0) {
         ol1 = o1.__len__();
      }

      if (ol2 < 0) {
         ol2 = o2.__len__();
      }

      for(int i = 0; i < ol1 && i < ol2; ++i) {
         if (!o1.__getitem__(i).equals(o2.__getitem__(i))) {
            return i;
         }
      }

      if (ol1 == ol2) {
         return -2;
      } else {
         return ol1 < ol2 ? -1 : -3;
      }
   }

   protected static PySequence fastSequence(PyObject seq, String msg) {
      if (seq instanceof PySequence) {
         return (PySequence)seq;
      } else {
         PyList list = new PyList();
         PyObject iter = Py.iter(seq, msg);
         PyObject item = null;

         while((item = iter.__iternext__()) != null) {
            list.append(item);
         }

         return list;
      }
   }

   protected static final int sliceLength(int start, int stop, long step) {
      int ret;
      if (step > 0L) {
         ret = (int)(((long)(stop - start) + step - 1L) / step);
      } else {
         ret = (int)(((long)(stop - start) + step + 1L) / step);
      }

      return ret < 0 ? 0 : ret;
   }

   protected int boundToSequence(int index) {
      int length = this.__len__();
      if (index < 0) {
         index += length;
         if (index < 0) {
            index = 0;
         }
      } else if (index > length) {
         index = length;
      }

      return index;
   }

   public PyObject __finditem__(int index) {
      return this.seq___finditem__(index);
   }

   final PyObject seq___finditem__(int index) {
      return this.delegator.checkIdxAndFindItem(index);
   }

   public PyObject __finditem__(PyObject index) {
      return this.seq___finditem__(index);
   }

   final PyObject seq___finditem__(PyObject index) {
      return this.delegator.checkIdxAndFindItem(index);
   }

   public PyObject __getitem__(PyObject index) {
      return this.seq___getitem__(index);
   }

   final PyObject seq___getitem__(PyObject index) {
      return this.delegator.checkIdxAndGetItem(index);
   }

   public boolean isMappingType() throws PyIgnoreMethodTag {
      return false;
   }

   public boolean isNumberType() throws PyIgnoreMethodTag {
      return false;
   }

   public PyObject __getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   final PyObject seq___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.delegator.getSlice(new PySlice(start, stop, step));
   }

   public void __setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      this.seq___setslice__(start, stop, step, value);
   }

   final void seq___setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      if (value == null) {
         value = step;
         step = null;
      }

      this.delegator.checkIdxAndSetSlice(new PySlice(start, stop, step), value);
   }

   public void __delslice__(PyObject start, PyObject stop, PyObject step) {
      this.seq___delslice__(start, stop, step);
   }

   final void seq___delslice__(PyObject start, PyObject stop, PyObject step) {
      this.delegator.checkIdxAndDelItem(new PySlice(start, stop, step));
   }

   public void __setitem__(int index, PyObject value) {
      this.delegator.checkIdxAndSetItem(index, value);
   }

   public void __setitem__(PyObject index, PyObject value) {
      this.seq___setitem__(index, value);
   }

   final void seq___setitem__(PyObject index, PyObject value) {
      this.delegator.checkIdxAndSetItem(index, value);
   }

   public void __delitem__(PyObject index) {
      this.seq___delitem__(index);
   }

   final void seq___delitem__(PyObject index) {
      this.delegator.checkIdxAndDelItem(index);
   }

   public synchronized Object __tojava__(Class c) throws PyIgnoreMethodTag {
      if (c.isArray()) {
         Class component = c.getComponentType();

         try {
            int n = this.__len__();
            PyArray array = new PyArray(component, n);

            for(int i = 0; i < n; ++i) {
               PyObject o = this.pyget(i);
               array.set(i, o);
            }

            return array.getArray();
         } catch (Throwable var7) {
         }
      }

      return super.__tojava__(c);
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return op.equals("*") ? "can''t multiply sequence by non-int of type ''{2}''" : null;
   }

   protected String runsupportedopMessage(String op, PyObject o2) {
      return op.equals("*") ? "can''t multiply sequence by non-int of type ''{1}''" : null;
   }

   public boolean isSequenceType() {
      return true;
   }

   protected class DefaultIndexDelegate extends SequenceIndexDelegate {
      public String getTypeName() {
         return PySequence.this.getType().fastGetName();
      }

      public void setItem(int idx, PyObject value) {
         PySequence.this.pyset(idx, value);
      }

      public void setSlice(int start, int stop, int step, PyObject value) {
         PySequence.this.setslice(start, stop, step, value);
      }

      public int len() {
         return PySequence.this.__len__();
      }

      public void delItem(int idx) {
         PySequence.this.del(idx);
      }

      public void delItems(int start, int stop) {
         PySequence.this.delRange(start, stop);
      }

      public PyObject getItem(int idx) {
         return PySequence.this.pyget(idx);
      }

      public PyObject getSlice(int start, int stop, int step) {
         return PySequence.this.getslice(start, stop, step);
      }
   }
}

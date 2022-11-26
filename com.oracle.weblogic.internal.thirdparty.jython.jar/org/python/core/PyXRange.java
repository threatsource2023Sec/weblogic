package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "xrange",
   base = PyObject.class,
   isBaseType = false,
   doc = "xrange([start,] stop[, step]) -> xrange object\n\nLike range(), but instead of returning a list, returns an object that\ngenerates the numbers in the range on demand.  For looping, this is \nslightly faster than range() and more memory efficient."
)
public class PyXRange extends PySequence {
   public static final PyType TYPE;
   private final long start;
   private final long step;
   private final long stop;
   private final long len;

   public PyXRange(int ihigh) {
      this(0, ihigh, 1);
   }

   public PyXRange(int ilow, int ihigh) {
      this(ilow, ihigh, 1);
   }

   public PyXRange(int ilow, int ihigh, int istep) {
      super(TYPE);
      if (istep == 0) {
         throw Py.ValueError("xrange() arg 3 must not be zero");
      } else {
         long listep = (long)istep;
         int n;
         if (listep > 0L) {
            n = getLenOfRange((long)ilow, (long)ihigh, listep);
         } else {
            n = getLenOfRange((long)ihigh, (long)ilow, -listep);
         }

         if (n < 0) {
            throw Py.OverflowError("xrange() result has too many items");
         } else {
            this.start = (long)ilow;
            this.len = (long)n;
            this.step = (long)istep;
            this.stop = (long)ihigh;
         }
      }
   }

   @ExposedNew
   static final PyObject xrange___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("xrange", args, keywords, new String[]{"ilow", "ihigh", "istep"}, 1);
      ap.noKeywords();
      int ilow = 0;
      int istep = 1;
      int ihigh;
      if (args.length == 1) {
         ihigh = ap.getInt(0);
      } else {
         ilow = ap.getInt(0);
         ihigh = ap.getInt(1);
         istep = ap.getInt(2, 1);
      }

      return new PyXRange(ilow, ihigh, istep);
   }

   static int getLenOfRange(long lo, long hi, long step) {
      if (lo < hi) {
         long diff = hi - lo - 1L;
         return (int)(diff / step + 1L);
      } else {
         return 0;
      }
   }

   public int __len__() {
      return this.xrange___len__();
   }

   final int xrange___len__() {
      return (int)this.len;
   }

   public PyObject __getitem__(PyObject index) {
      return this.xrange___getitem__(index);
   }

   final PyObject xrange___getitem__(PyObject index) {
      PyObject ret = this.seq___finditem__(index);
      if (ret == null) {
         throw Py.IndexError("xrange object index out of range");
      } else {
         return ret;
      }
   }

   public PyObject __iter__() {
      return this.xrange___iter__();
   }

   public PyObject xrange___iter__() {
      return this.range_iter();
   }

   public PyObject xrange___reversed__() {
      return this.range_reverse();
   }

   private final PyXRangeIter range_iter() {
      return new PyXRangeIter(0L, this.start, this.step, this.len);
   }

   private final PyXRangeIter range_reverse() {
      return new PyXRangeIter(0L, this.start + (this.len - 1L) * this.step, 0L - this.step, this.len);
   }

   public PyObject xrange___reduce__() {
      return new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{Py.newInteger(this.start), Py.newInteger(this.stop), Py.newInteger(this.step)})});
   }

   public PyObject __reduce__() {
      return this.xrange___reduce__();
   }

   protected PyObject pyget(int i) {
      return Py.newInteger(this.start + (long)i % this.len * this.step);
   }

   protected PyObject getslice(int start, int stop, int step) {
      throw Py.TypeError("xrange index must be integer, not 'slice'");
   }

   protected PyObject repeat(int howmany) {
      return null;
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return null;
   }

   public String toString() {
      long lstop = this.start + this.len * this.step;
      if (lstop > 2147483647L) {
         lstop = 2147483647L;
      } else if (lstop < -2147483648L) {
         lstop = -2147483648L;
      }

      int stop = (int)lstop;
      if (this.start == 0L && this.step == 1L) {
         return String.format("xrange(%d)", stop);
      } else {
         return this.step == 1L ? String.format("xrange(%d, %d)", this.start, stop) : String.format("xrange(%d, %d, %d)", this.start, stop, this.step);
      }
   }

   public Object __tojava__(Class c) {
      if (c.isAssignableFrom(Iterable.class)) {
         return new JavaIterator(this.range_iter());
      } else if (c.isAssignableFrom(Iterator.class)) {
         return (new JavaIterator(this.range_iter())).iterator();
      } else if (!c.isAssignableFrom(Collection.class)) {
         if (c.isArray()) {
            PyArray array = new PyArray(c.getComponentType(), this);
            return array.__tojava__(c);
         } else {
            return super.__tojava__(c);
         }
      } else {
         List list = new ArrayList();
         Iterator var3 = (new JavaIterator(this.range_iter())).iterator();

         while(var3.hasNext()) {
            Object obj = var3.next();
            list.add(obj);
         }

         return list;
      }
   }

   static {
      PyType.addBuilder(PyXRange.class, new PyExposer());
      TYPE = PyType.fromClass(PyXRange.class);
   }

   private static class xrange___len___exposer extends PyBuiltinMethodNarrow {
      public xrange___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public xrange___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new xrange___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyXRange)this.self).xrange___len__());
      }
   }

   private static class xrange___getitem___exposer extends PyBuiltinMethodNarrow {
      public xrange___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public xrange___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new xrange___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyXRange)this.self).xrange___getitem__(var1);
      }
   }

   private static class xrange___iter___exposer extends PyBuiltinMethodNarrow {
      public xrange___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public xrange___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new xrange___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyXRange)this.self).xrange___iter__();
      }
   }

   private static class xrange___reversed___exposer extends PyBuiltinMethodNarrow {
      public xrange___reversed___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns a reverse iterator.";
      }

      public xrange___reversed___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns a reverse iterator.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new xrange___reversed___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyXRange)this.self).xrange___reversed__();
      }
   }

   private static class xrange___reduce___exposer extends PyBuiltinMethodNarrow {
      public xrange___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public xrange___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new xrange___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyXRange)this.self).xrange___reduce__();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyXRange.xrange___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new xrange___len___exposer("__len__"), new xrange___getitem___exposer("__getitem__"), new xrange___iter___exposer("__iter__"), new xrange___reversed___exposer("__reversed__"), new xrange___reduce___exposer("__reduce__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("xrange", PyXRange.class, PyObject.class, (boolean)0, "xrange([start,] stop[, step]) -> xrange object\n\nLike range(), but instead of returning a list, returns an object that\ngenerates the numbers in the range on demand.  For looping, this is \nslightly faster than range() and more memory efficient.", var1, var2, new exposed___new__());
      }
   }
}

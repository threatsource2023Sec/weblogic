package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "slice",
   isBaseType = false,
   doc = "slice([start,] stop[, step])\n\nCreate a slice object.  This is used for extended slicing (e.g. a[0:10:2])."
)
public class PySlice extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyObject start;
   public PyObject stop;
   public PyObject step;

   public PySlice() {
      super(TYPE);
      this.start = Py.None;
      this.stop = Py.None;
      this.step = Py.None;
   }

   public PySlice(PyObject start, PyObject stop, PyObject step) {
      super(TYPE);
      this.start = Py.None;
      this.stop = Py.None;
      this.step = Py.None;
      if (start != null) {
         this.start = start;
      }

      if (stop != null) {
         this.stop = stop;
      }

      if (step != null) {
         this.step = step;
      }

   }

   @ExposedNew
   static PyObject slice_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length == 0) {
         throw Py.TypeError("slice expected at least 1 arguments, got " + args.length);
      } else if (args.length > 3) {
         throw Py.TypeError("slice expected at most 3 arguments, got " + args.length);
      } else {
         ArgParser ap = new ArgParser("slice", args, keywords, "start", "stop", "step");
         PySlice slice = new PySlice();
         if (args.length == 1) {
            slice.stop = ap.getPyObject(0);
         } else if (args.length == 2) {
            slice.start = ap.getPyObject(0);
            slice.stop = ap.getPyObject(1);
         } else if (args.length == 3) {
            slice.start = ap.getPyObject(0);
            slice.stop = ap.getPyObject(1);
            slice.step = ap.getPyObject(2);
         }

         return slice;
      }
   }

   public int hashCode() {
      return this.slice___hash__();
   }

   final int slice___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   public PyObject __eq__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else if (this == o) {
         return Py.True;
      } else {
         PySlice oSlice = (PySlice)o;
         return Py.newBoolean(eq(this.getStart(), oSlice.getStart()) && eq(this.getStop(), oSlice.getStop()) && eq(this.getStep(), oSlice.getStep()));
      }
   }

   private static final boolean eq(PyObject o1, PyObject o2) {
      return o1._cmp(o2) == 0;
   }

   public PyObject __ne__(PyObject o) {
      return this.__eq__(o).__not__();
   }

   public PyObject indices(PyObject len) {
      return this.slice_indices(len);
   }

   final PyObject slice_indices(PyObject len) {
      int[] indices = this.indicesEx(len.asIndex(Py.OverflowError));
      return new PyTuple(new PyObject[]{Py.newInteger(indices[0]), Py.newInteger(indices[1]), Py.newInteger(indices[2])});
   }

   public int[] indicesEx(int length) {
      int result_step;
      if (this.step == Py.None) {
         result_step = 1;
      } else {
         result_step = calculateSliceIndex(this.step);
         if (result_step == 0) {
            throw Py.ValueError("slice step cannot be zero");
         }
      }

      int defstart = result_step < 0 ? length - 1 : 0;
      int defstop = result_step < 0 ? -1 : length;
      int result_start;
      if (this.start == Py.None) {
         result_start = defstart;
      } else {
         result_start = calculateSliceIndex(this.start);
         if (result_start < 0) {
            result_start += length;
         }

         if (result_start < 0) {
            result_start = result_step < 0 ? -1 : 0;
         }

         if (result_start >= length) {
            result_start = result_step < 0 ? length - 1 : length;
         }
      }

      int result_stop;
      if (this.stop == Py.None) {
         result_stop = defstop;
      } else {
         result_stop = calculateSliceIndex(this.stop);
         if (result_stop < 0) {
            result_stop += length;
         }

         if (result_stop < 0) {
            result_stop = result_step < 0 ? -1 : 0;
         }

         if (result_stop >= length) {
            result_stop = result_step < 0 ? length - 1 : length;
         }
      }

      int result_slicelength;
      if (result_step < 0 && result_stop >= result_start || result_step > 0 && result_start >= result_stop) {
         result_slicelength = 0;
      } else if (result_step < 0) {
         result_slicelength = (result_stop - result_start + 1) / result_step + 1;
      } else {
         result_slicelength = (result_stop - result_start - 1) / result_step + 1;
      }

      return new int[]{result_start, result_stop, result_step, result_slicelength};
   }

   public static PyObject[] indices2(PyObject obj, PyObject start, PyObject stop) {
      PyObject[] indices = new PyObject[2];
      int istart = start != null && start != Py.None ? calculateSliceIndex(start) : 0;
      int istop = stop != null && stop != Py.None ? calculateSliceIndex(stop) : Integer.MAX_VALUE;
      if (istart < 0 || istop < 0) {
         try {
            int len = obj.__len__();
            if (istart < 0) {
               istart += len;
            }

            if (istop < 0) {
               istop += len;
            }
         } catch (PyException var7) {
            if (!var7.match(Py.TypeError)) {
               throw var7;
            }
         }
      }

      indices[0] = Py.newInteger(istart);
      indices[1] = Py.newInteger(istop);
      return indices;
   }

   public static int calculateSliceIndex(PyObject v) {
      if (v.isIndex()) {
         return v.asIndex();
      } else {
         throw Py.TypeError("slice indices must be integers or None or have an __index__ method");
      }
   }

   public String toString() {
      return this.slice_toString();
   }

   final String slice_toString() {
      return String.format("slice(%s, %s, %s)", this.getStart(), this.getStop(), this.getStep());
   }

   public final PyObject getStart() {
      return this.start;
   }

   public final PyObject getStop() {
      return this.stop;
   }

   public final PyObject getStep() {
      return this.step;
   }

   final PyObject slice___reduce__() {
      return new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{this.start, this.stop, this.step})});
   }

   final PyObject slice___reduce_ex__(PyObject protocol) {
      return new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{this.start, this.stop, this.step})});
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = visit.visit(this.start, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         retVal = visit.visit(this.stop, arg);
         return retVal != 0 ? retVal : visit.visit(this.step, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob == this.start || ob == this.stop || ob == this.step;
   }

   static {
      PyType.addBuilder(PySlice.class, new PyExposer());
      TYPE = PyType.fromClass(PySlice.class);
   }

   private static class slice___hash___exposer extends PyBuiltinMethodNarrow {
      public slice___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public slice___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new slice___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PySlice)this.self).slice___hash__());
      }
   }

   private static class slice_indices_exposer extends PyBuiltinMethodNarrow {
      public slice_indices_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.indices(len) -> (start, stop, stride)\n\nAssuming a sequence of length len, calculate the start and stop\nindices, and the stride length of the extended slice described by\nS. Out of bounds indices are clipped in a manner consistent with the\nhandling of normal slices.";
      }

      public slice_indices_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.indices(len) -> (start, stop, stride)\n\nAssuming a sequence of length len, calculate the start and stop\nindices, and the stride length of the extended slice described by\nS. Out of bounds indices are clipped in a manner consistent with the\nhandling of normal slices.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new slice_indices_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySlice)this.self).slice_indices(var1);
      }
   }

   private static class slice_toString_exposer extends PyBuiltinMethodNarrow {
      public slice_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public slice_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new slice_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PySlice)this.self).slice_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class slice___reduce___exposer extends PyBuiltinMethodNarrow {
      public slice___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public slice___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new slice___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PySlice)this.self).slice___reduce__();
      }
   }

   private static class slice___reduce_ex___exposer extends PyBuiltinMethodNarrow {
      public slice___reduce_ex___exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public slice___reduce_ex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new slice___reduce_ex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySlice)this.self).slice___reduce_ex__(var1);
      }

      public PyObject __call__() {
         return ((PySlice)this.self).slice___reduce_ex__(Py.None);
      }
   }

   private static class stop_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public stop_descriptor() {
         super("stop", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySlice)var1).stop;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class start_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public start_descriptor() {
         super("start", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySlice)var1).start;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class step_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public step_descriptor() {
         super("step", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySlice)var1).step;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PySlice.slice_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new slice___hash___exposer("__hash__"), new slice_indices_exposer("indices"), new slice_toString_exposer("__repr__"), new slice___reduce___exposer("__reduce__"), new slice___reduce_ex___exposer("__reduce_ex__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new stop_descriptor(), new start_descriptor(), new step_descriptor()};
         super("slice", PySlice.class, Object.class, (boolean)0, "slice([start,] stop[, step])\n\nCreate a slice object.  This is used for extended slicing (e.g. a[0:10:2]).", var1, var2, new exposed___new__());
      }
   }
}

package org.python.core;

import org.python.core.buffer.BaseBuffer;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "memoryview",
   doc = "memoryview(object)\n\nCreate a new memoryview object which references the given object.",
   base = PyObject.class,
   isBaseType = false
)
public class PyMemoryView extends PySequence implements BufferProtocol, Traverseproc {
   public static final PyType TYPE;
   private PyBuffer backing;
   private boolean released;
   private PyObject shape;
   private PyObject strides;
   private PyObject suboffsets;
   private int hashCache;
   private boolean hashCacheValid = false;
   private static final String cast_doc = "M.cast(format[, shape]) -> memoryview\n\nCast a memoryview to a new format or shape.";
   private static final String release_doc = "M.release() -> None\n\nRelease the underlying buffer exposed by the memoryview object.";
   private static final String tobytes_doc = "M.tobytes() -> bytes\n\nReturn the data in the buffer as a bytestring (an object of class str).";
   private static final String tolist_doc = "M.tolist() -> list\n\nReturn the data in the buffer as a list of elements.";
   private static final String c_contiguous_doc = "c_contiguous\nA bool indicating whether the memory is C contiguous.";
   private static final String contiguous_doc = "contiguous\nA bool indicating whether the memory is contiguous.";
   private static final String f_contiguous_doc = "c_contiguous\nA bool indicating whether the memory is Fortran contiguous.";
   private static final String format_doc = "format\nA string containing the format (in struct module style)\n for each element in the view.";
   private static final String itemsize_doc = "itemsize\nThe size in bytes of each element of the memoryview.";
   private static final String nbytes_doc = "nbytes\nThe amount of space in bytes that the array would use in\na contiguous representation.";
   private static final String ndim_doc = "ndim\nAn integer indicating how many dimensions of a multi-dimensional\narray the memory represents.";
   private static final String obj_doc = "obj\nThe underlying object of the memoryview.";
   private static final String readonly_doc = "readonly\nA bool indicating whether the memory is read only.";
   private static final String shape_doc = "shape\nA tuple of ndim integers giving the shape of the memory\nas an N-dimensional array.";
   private static final String strides_doc = "strides\nA tuple of ndim integers giving the size in bytes to access\neach element for each dimension of the array.";
   private static final String suboffsets_doc = "suboffsets\nA tuple of ndim integers used internally for PIL-style arrays\nor None.";
   // $FF: synthetic field
   static final boolean $assertionsDisabled;

   public PyMemoryView(BufferProtocol pybuf) {
      super(TYPE);
      this.backing = pybuf.getBuffer(284);
   }

   @ExposedNew
   static PyObject memoryview_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length != 1) {
         throw Py.TypeError("memoryview() takes exactly one argument");
      } else {
         ArgParser ap = new ArgParser("memoryview", args, keywords, "object");
         PyObject obj = ap.getPyObject(0);
         if (obj instanceof BufferProtocol) {
            return new PyMemoryView((BufferProtocol)obj);
         } else {
            throw Py.TypeError("cannot make memory view because object does not have the buffer interface");
         }
      }
   }

   public PyObject obj() {
      this.checkNotReleased();
      BufferProtocol obj = this.backing.getObj();
      return obj instanceof PyObject ? (PyObject)obj : Py.None;
   }

   public String format() {
      this.checkNotReleased();
      return this.backing.getFormat();
   }

   public int itemsize() {
      this.checkNotReleased();
      return this.backing.getItemsize();
   }

   public PyObject shape() {
      this.checkNotReleased();
      if (this.shape == null) {
         this.shape = this.tupleOf(this.backing.getShape());
      }

      return this.shape;
   }

   public int ndim() {
      this.checkNotReleased();
      return this.backing.getNdim();
   }

   public PyObject strides() {
      this.checkNotReleased();
      if (this.strides == null) {
         this.strides = this.tupleOf(this.backing.getStrides());
      }

      return this.strides;
   }

   public PyObject suboffsets() {
      this.checkNotReleased();
      if (this.suboffsets == null) {
         this.suboffsets = this.tupleOf(this.backing.getSuboffsets());
      }

      return this.suboffsets;
   }

   public boolean readonly() {
      this.checkNotReleased();
      return this.backing.isReadonly();
   }

   public PyString tobytes() {
      return this.memoryview_tobytes();
   }

   final PyString memoryview_tobytes() {
      this.checkNotReleased();
      if (this.backing instanceof BaseBuffer) {
         return new PyString(this.backing.toString());
      } else {
         String s = StringUtil.fromBytes(this.backing);
         return new PyString(s);
      }
   }

   public PyList tolist() {
      return this.memoryview_tolist();
   }

   final PyList memoryview_tolist() {
      this.checkNotReleased();
      int n = this.backing.getLen();
      PyList list = new PyList();

      for(int i = 0; i < n; ++i) {
         list.add(new PyInteger(this.backing.intAt(i)));
      }

      return list;
   }

   private PyObject tupleOf(int[] x) {
      if (x == null) {
         return Py.None;
      } else {
         PyLong[] pyx = new PyLong[x.length];

         for(int k = 0; k < x.length; ++k) {
            pyx[k] = new PyLong((long)x[k]);
         }

         return new PyTuple(pyx, false);
      }
   }

   public int __len__() {
      this.checkNotReleased();
      return this.backing.getLen();
   }

   public int hashCode() {
      return this.memoryview___hash__();
   }

   final int memoryview___hash__() {
      if (!this.hashCacheValid) {
         this.checkNotReleased();
         if (!this.backing.isReadonly()) {
            throw Py.ValueError("cannot hash writable memoryview object");
         }

         this.hashCache = this.backing.toString().hashCode();
         this.hashCacheValid = true;
      }

      return this.hashCache;
   }

   public PyObject __eq__(PyObject other) {
      return this.memoryview___eq__(other);
   }

   public PyObject __ne__(PyObject other) {
      return this.memoryview___ne__(other);
   }

   public PyObject __lt__(PyObject other) {
      return this.memoryview___lt__(other);
   }

   public PyObject __le__(PyObject other) {
      return this.memoryview___le__(other);
   }

   public PyObject __ge__(PyObject other) {
      return this.memoryview___ge__(other);
   }

   public PyObject __gt__(PyObject other) {
      return this.memoryview___gt__(other);
   }

   private static int compare(PyBuffer a, PyBuffer b) {
      int ap = 0;
      int aEnd = ap + a.getLen();
      int bp = 0;
      int bEnd = b.getLen();

      int diff;
      do {
         if (ap >= aEnd) {
            if (bp < bEnd) {
               return -1;
            }

            return 0;
         }

         if (bp >= bEnd) {
            return 1;
         }

         int aVal = a.intAt(ap++);
         int bVal = b.intAt(bp++);
         diff = aVal - bVal;
      } while(diff == 0);

      return diff < 0 ? -1 : 1;
   }

   private int memoryview_cmp(PyObject b) {
      this.checkNotReleased();
      PyBuffer bv = BaseBytes.getView(b);
      if (bv == null) {
         return -2;
      } else {
         int var3;
         try {
            if (bv == this.backing) {
               byte var7 = 0;
               return var7;
            }

            var3 = compare(this.backing, bv);
         } finally {
            bv.release();
         }

         return var3;
      }
   }

   private int memoryview_cmpeq(PyObject b) {
      if (this == b) {
         return 0;
      } else if (this.released) {
         return -1;
      } else if (b instanceof PyMemoryView && ((PyMemoryView)b).released) {
         return 1;
      } else {
         PyBuffer bv = BaseBytes.getView(b);
         if (bv == null) {
            return -2;
         } else {
            int var3;
            try {
               if (bv == this.backing) {
                  byte var7 = 0;
                  return var7;
               }

               if (bv.getLen() == this.backing.getLen()) {
                  var3 = compare(this.backing, bv);
                  return var3;
               }

               var3 = 1;
            } finally {
               bv.release();
            }

            return var3;
         }
      }
   }

   final PyObject memoryview___eq__(PyObject other) {
      int cmp = this.memoryview_cmpeq(other);
      if (cmp == 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   final PyObject memoryview___ne__(PyObject other) {
      int cmp = this.memoryview_cmpeq(other);
      if (cmp == 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject memoryview___lt__(PyObject other) {
      int cmp = this.memoryview_cmp(other);
      if (cmp >= 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject memoryview___le__(PyObject other) {
      int cmp = this.memoryview_cmp(other);
      if (cmp > 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject memoryview___ge__(PyObject other) {
      int cmp = this.memoryview_cmp(other);
      if (cmp >= 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   final PyObject memoryview___gt__(PyObject other) {
      int cmp = this.memoryview_cmp(other);
      if (cmp > 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   public PyObject __enter__() {
      return this.memoryview___enter__();
   }

   final PyObject memoryview___enter__() {
      this.checkNotReleased();
      return this;
   }

   public boolean __exit__(PyObject type, PyObject value, PyObject traceback) {
      return this.memoryview___exit__(type, value, traceback);
   }

   final boolean memoryview___exit__(PyObject type, PyObject value, PyObject traceback) {
      this.memoryview_release();
      return false;
   }

   public synchronized PyBuffer getBuffer(int flags) {
      this.checkNotReleased();
      return this.backing.getBuffer(flags);
   }

   public synchronized void release() {
      this.memoryview_release();
   }

   public final synchronized void memoryview_release() {
      if (!this.released) {
         this.backing.release();
         this.released = true;
      }

   }

   protected void checkNotReleased() {
      if (this.released) {
         throw Py.ValueError("operation forbidden on released memoryview object");
      } else if (!$assertionsDisabled && this.backing.isReleased()) {
         throw new AssertionError();
      }
   }

   protected PyString pyget(int index) {
      this.checkNotReleased();
      return new PyString(String.valueOf((char)this.backing.intAt(index)));
   }

   protected synchronized PyMemoryView getslice(int start, int stop, int step) {
      this.checkNotReleased();
      int n = sliceLength(start, stop, (long)step);
      PyBuffer view = this.backing.getBufferSlice(284, start, n, step);
      PyMemoryView ret = new PyMemoryView(view);
      view.release();
      return ret;
   }

   protected synchronized PyMemoryView repeat(int count) throws PyException {
      throw Py.NotImplementedError("memoryview.repeat()");
   }

   public synchronized void pyset(int index, PyObject value) throws PyException {
      this.checkNotReleased();
      PyBuffer valueBuf = BaseBytes.getViewOrError(value);

      try {
         if (valueBuf.getLen() != 1) {
            throw Py.ValueError("cannot modify size of memoryview object");
         }

         this.backing.storeAt(valueBuf.byteAt(0), index);
      } finally {
         valueBuf.release();
      }

   }

   protected synchronized void setslice(int start, int stop, int step, PyObject value) {
      this.checkNotReleased();
      if (step == 1 && stop < start) {
         stop = start;
      }

      PyBuffer valueBuf = BaseBytes.getViewOrError(value);
      PyBuffer backingSlice = null;

      try {
         int n = sliceLength(start, stop, (long)step);
         if (n != valueBuf.getLen()) {
            throw Py.ValueError("cannot modify size of memoryview object");
         }

         backingSlice = this.backing.getBufferSlice(284, start, n, step);
         backingSlice.copyFrom(valueBuf);
      } finally {
         if (backingSlice != null) {
            backingSlice.release();
         }

         valueBuf.release();
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.backing != null) {
         if (this.backing instanceof PyObject) {
            retVal = visit.visit((PyObject)this.backing, arg);
            if (retVal != 0) {
               return retVal;
            }
         } else if (this.backing instanceof Traverseproc) {
            retVal = ((Traverseproc)this.backing).traverse(visit, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      if (this.shape != null) {
         retVal = visit.visit(this.shape, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.strides != null) {
         retVal = visit.visit(this.strides, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.suboffsets == null ? 0 : visit.visit(this.suboffsets, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null || ob != this.backing && ob != this.shape && ob != this.strides && ob != this.suboffsets) {
         return this.suboffsets instanceof Traverseproc ? ((Traverseproc)this.suboffsets).refersDirectlyTo(ob) : false;
      } else {
         return true;
      }
   }

   static {
      PyType.addBuilder(PyMemoryView.class, new PyExposer());
      $assertionsDisabled = !PyMemoryView.class.desiredAssertionStatus();
      TYPE = PyType.fromClass(PyMemoryView.class);
   }

   private static class memoryview_tobytes_exposer extends PyBuiltinMethodNarrow {
      public memoryview_tobytes_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "M.tobytes() -> bytes\n\nReturn the data in the buffer as a bytestring (an object of class str).";
      }

      public memoryview_tobytes_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "M.tobytes() -> bytes\n\nReturn the data in the buffer as a bytestring (an object of class str).";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview_tobytes_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyMemoryView)this.self).memoryview_tobytes();
      }
   }

   private static class memoryview_tolist_exposer extends PyBuiltinMethodNarrow {
      public memoryview_tolist_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "M.tolist() -> list\n\nReturn the data in the buffer as a list of elements.";
      }

      public memoryview_tolist_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "M.tolist() -> list\n\nReturn the data in the buffer as a list of elements.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview_tolist_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyMemoryView)this.self).memoryview_tolist();
      }
   }

   private static class memoryview___hash___exposer extends PyBuiltinMethodNarrow {
      public memoryview___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public memoryview___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyMemoryView)this.self).memoryview___hash__());
      }
   }

   private static class memoryview___eq___exposer extends PyBuiltinMethodNarrow {
      public memoryview___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public memoryview___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___ne___exposer extends PyBuiltinMethodNarrow {
      public memoryview___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public memoryview___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___lt___exposer extends PyBuiltinMethodNarrow {
      public memoryview___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public memoryview___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___le___exposer extends PyBuiltinMethodNarrow {
      public memoryview___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public memoryview___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___ge___exposer extends PyBuiltinMethodNarrow {
      public memoryview___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public memoryview___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___gt___exposer extends PyBuiltinMethodNarrow {
      public memoryview___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public memoryview___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyMemoryView)this.self).memoryview___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class memoryview___enter___exposer extends PyBuiltinMethodNarrow {
      public memoryview___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public memoryview___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyMemoryView)this.self).memoryview___enter__();
      }
   }

   private static class memoryview___exit___exposer extends PyBuiltinMethodNarrow {
      public memoryview___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public memoryview___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyMemoryView)this.self).memoryview___exit__(var1, var2, var3));
      }
   }

   private static class memoryview_release_exposer extends PyBuiltinMethodNarrow {
      public memoryview_release_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "M.release() -> None\n\nRelease the underlying buffer exposed by the memoryview object.";
      }

      public memoryview_release_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "M.release() -> None\n\nRelease the underlying buffer exposed by the memoryview object.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new memoryview_release_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyMemoryView)this.self).memoryview_release();
         return Py.None;
      }
   }

   private static class shape_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public shape_descriptor() {
         super("shape", PyObject.class, "shape\nA tuple of ndim integers giving the shape of the memory\nas an N-dimensional array.");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMemoryView)var1).shape();
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

   private static class readonly_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public readonly_descriptor() {
         super("readonly", Boolean.class, "readonly\nA bool indicating whether the memory is read only.");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyMemoryView)var1).readonly());
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

   private static class format_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public format_descriptor() {
         super("format", String.class, "format\nA string containing the format (in struct module style)\n for each element in the view.");
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyMemoryView)var1).format();
         return var10000 == null ? Py.None : Py.newString(var10000);
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

   private static class itemsize_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public itemsize_descriptor() {
         super("itemsize", Integer.class, "itemsize\nThe size in bytes of each element of the memoryview.");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyMemoryView)var1).itemsize());
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

   private static class ndim_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public ndim_descriptor() {
         super("ndim", Integer.class, "ndim\nAn integer indicating how many dimensions of a multi-dimensional\narray the memory represents.");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyMemoryView)var1).ndim());
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

   private static class strides_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public strides_descriptor() {
         super("strides", PyObject.class, "strides\nA tuple of ndim integers giving the size in bytes to access\neach element for each dimension of the array.");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMemoryView)var1).strides();
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

   private static class suboffsets_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public suboffsets_descriptor() {
         super("suboffsets", PyObject.class, "suboffsets\nA tuple of ndim integers used internally for PIL-style arrays\nor None.");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMemoryView)var1).suboffsets();
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
         return PyMemoryView.memoryview_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new memoryview_tobytes_exposer("tobytes"), new memoryview_tolist_exposer("tolist"), new memoryview___hash___exposer("__hash__"), new memoryview___eq___exposer("__eq__"), new memoryview___ne___exposer("__ne__"), new memoryview___lt___exposer("__lt__"), new memoryview___le___exposer("__le__"), new memoryview___ge___exposer("__ge__"), new memoryview___gt___exposer("__gt__"), new memoryview___enter___exposer("__enter__"), new memoryview___exit___exposer("__exit__"), new memoryview_release_exposer("release")};
         PyDataDescr[] var2 = new PyDataDescr[]{new shape_descriptor(), new readonly_descriptor(), new format_descriptor(), new itemsize_descriptor(), new ndim_descriptor(), new strides_descriptor(), new suboffsets_descriptor()};
         super("memoryview", PyMemoryView.class, PyObject.class, (boolean)0, "memoryview(object)\n\nCreate a new memoryview object which references the given object.", var1, var2, new exposed___new__());
      }
   }
}

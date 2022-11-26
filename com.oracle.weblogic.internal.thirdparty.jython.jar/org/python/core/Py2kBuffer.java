package org.python.core;

import org.python.core.buffer.BaseBuffer;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "buffer",
   doc = "buffer(object [, offset[, size]])\n\nCreate a new buffer object which references the given object.\nThe buffer will reference a slice of the target object from the\nstart of the object (or at the specified offset). The slice will\nextend to the end of the target object (or with the specified size).",
   base = PyObject.class,
   isBaseType = false
)
public class Py2kBuffer extends PySequence implements BufferProtocol {
   public static final PyType TYPE;
   private final BufferProtocol object;
   private final int offset;
   private final int size;
   private static String[] paramNames;
   private static final String tobytes_doc = "M.tobytes() -> bytes\n\nReturn the data in the buffer as a bytestring (an object of class str).";
   private static final String tolist_doc = "M.tolist() -> list\n\nReturn the data in the buffer as a list of elements.";

   public Py2kBuffer(BufferProtocol object, int offset, int size) {
      super(TYPE);
      if (object instanceof Py2kBuffer) {
         Py2kBuffer source = (Py2kBuffer)object;
         offset += source.offset;
         if (source.size >= 0) {
            int end = source.offset + source.size;
            if (size < 0 || offset + size > end) {
               size = end - offset;
            }
         }

         object = source.object;
      }

      this.object = object;
      this.offset = offset;
      this.size = size;
   }

   private PyBuffer getBuffer() {
      int flags = false;
      PyBuffer buf = this.object.getBuffer(0);
      if (this.offset > 0 || this.size >= 0) {
         PyBuffer first = buf;
         int start = this.offset;
         int length = buf.getLen() - start;
         if (length <= 0) {
            length = 0;
            int start = false;
         } else if (this.size >= 0 && this.size < length) {
            length = this.size;
         }

         buf = buf.getBufferSlice(0, this.offset, length);
         first.release();
      }

      return buf;
   }

   private static BufferProtocol asBufferableOrNull(PyObject obj) {
      if (obj instanceof PyUnicode) {
         String bytes = codecs.encode((PyString)obj, "UTF-16BE", "replace");
         return new PyString(bytes);
      } else {
         return obj instanceof BufferProtocol ? (BufferProtocol)obj : null;
      }
   }

   @ExposedNew
   static PyObject buffer_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("buffer", args, keywords, paramNames, 1);
      PyObject obj = ap.getPyObject(0);
      int offset = ap.getIndex(1, 0);
      int size = ap.getInt(2, -1);
      BufferProtocol object = asBufferableOrNull(obj);
      if (object == null) {
         throw Py.TypeError("buffer object expected (or unicode)");
      } else if (offset < 0) {
         throw Py.ValueError("offset must be zero or positive");
      } else if (size < -1) {
         throw Py.ValueError("size must be zero or positive");
      } else {
         return new Py2kBuffer(object, offset, size);
      }
   }

   public int __len__() {
      PyBuffer buf = this.getBuffer();

      int var2;
      try {
         var2 = buf.getLen();
      } finally {
         buf.release();
      }

      return var2;
   }

   public PyString __repr__() {
      String fmt = "<read-only buffer for %s, size %d, offset %d at 0x%s>";
      String ret = String.format(fmt, Py.idstr((PyObject)this.object), this.size, this.offset, Py.idstr(this));
      return new PyString(ret);
   }

   public PyString __str__() {
      PyBuffer buf = this.getBuffer();

      PyString var3;
      try {
         if (buf instanceof BaseBuffer) {
            PyString var7 = new PyString(buf.toString());
            return var7;
         }

         String s = StringUtil.fromBytes(buf);
         var3 = new PyString(s);
      } finally {
         buf.release();
      }

      return var3;
   }

   public PyObject __add__(PyObject other) {
      return this.buffer___add__(other);
   }

   final PyObject buffer___add__(PyObject other) {
      BufferProtocol bp = asBufferableOrNull(other);
      if (bp == null) {
         return null;
      } else {
         PyBuffer buf = this.getBuffer();

         PyString var5;
         try {
            PyBuffer otherBuf = bp.getBuffer(0);

            try {
               var5 = new PyString(buf.toString().concat(otherBuf.toString()));
            } finally {
               otherBuf.release();
            }
         } finally {
            buf.release();
         }

         return var5;
      }
   }

   public PyObject __mul__(PyObject o) {
      return this.buffer___mul__(o);
   }

   final PyObject buffer___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.buffer___rmul__(o);
   }

   final PyObject buffer___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
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

   private int buffer_cmp(PyObject b) {
      PyBuffer buf = this.getBuffer();

      int var4;
      try {
         PyBuffer bv = BaseBytes.getView(b);
         if (bv == null) {
            byte var13 = -2;
            return var13;
         }

         try {
            if (bv != buf) {
               var4 = compare(buf, bv);
               return var4;
            }

            var4 = 0;
         } finally {
            bv.release();
         }
      } finally {
         buf.release();
      }

      return var4;
   }

   private int buffer_cmpeq(PyObject b) {
      PyBuffer buf = this.getBuffer();

      int var4;
      try {
         PyBuffer bv = BaseBytes.getView(b);
         if (bv != null) {
            try {
               if (bv == buf) {
                  var4 = 0;
                  return var4;
               }

               if (bv.getLen() != buf.getLen()) {
                  var4 = 1;
                  return var4;
               }

               var4 = compare(buf, bv);
               return var4;
            } finally {
               bv.release();
            }
         }

         var4 = -2;
      } finally {
         buf.release();
      }

      return var4;
   }

   public PyBuffer getBuffer(int flags) {
      PyBuffer buf = this.object.getBuffer(flags);
      if (this.offset > 0 || this.size >= 0) {
         PyBuffer first = buf;
         int start = this.offset;
         int length = buf.getLen() - start;
         if (length <= 0) {
            length = 0;
            int start = false;
         } else if (this.size >= 0 && this.size < length) {
            length = this.size;
         }

         buf = buf.getBufferSlice(flags, this.offset, length);
         first.release();
      }

      return buf;
   }

   protected PyString pyget(int index) {
      PyBuffer buf = this.getBuffer();

      PyString var3;
      try {
         var3 = new PyString(String.valueOf((char)buf.intAt(index)));
      } finally {
         buf.release();
      }

      return var3;
   }

   protected synchronized PyString getslice(int start, int stop, int step) {
      PyBuffer buf = this.getBuffer();

      PyString var8;
      try {
         int n = sliceLength(start, stop, (long)step);
         PyBuffer first = buf;
         buf = buf.getBufferSlice(284, start, n, step);
         first.release();
         PyString ret = Py.newString(buf.toString());
         var8 = ret;
      } finally {
         buf.release();
      }

      return var8;
   }

   protected synchronized PyString repeat(int count) {
      PyBuffer buf = this.getBuffer();

      PyString var4;
      try {
         PyString ret = Py.newString(buf.toString());
         var4 = (PyString)ret.repeat(count);
      } finally {
         buf.release();
      }

      return var4;
   }

   public synchronized void pyset(int index, PyObject value) throws PyException {
      PyBuffer buf = this.getBuffer();

      try {
         PyBuffer valueBuf = BaseBytes.getViewOrError(value);

         try {
            if (valueBuf.getLen() != 1) {
               throw Py.ValueError("cannot modify size of buffer object");
            }

            buf.storeAt(valueBuf.byteAt(0), index);
         } finally {
            valueBuf.release();
         }
      } finally {
         buf.release();
      }

   }

   protected synchronized void setslice(int start, int stop, int step, PyObject value) {
      PyBuffer buf = this.getBuffer();

      try {
         if (step == 1 && stop < start) {
            stop = start;
         }

         PyBuffer valueBuf = BaseBytes.getViewOrError(value);
         PyBuffer bufSlice = null;

         try {
            int n = sliceLength(start, stop, (long)step);
            if (n != valueBuf.getLen()) {
               throw Py.ValueError("cannot modify size of buffer object");
            }

            bufSlice = buf.getBufferSlice(284, start, n, step);
            bufSlice.copyFrom(valueBuf);
         } finally {
            if (bufSlice != null) {
               bufSlice.release();
            }

            valueBuf.release();
         }
      } finally {
         buf.release();
      }

   }

   static {
      PyType.addBuilder(Py2kBuffer.class, new PyExposer());
      TYPE = PyType.fromClass(Py2kBuffer.class);
      paramNames = new String[]{"object", "offset", "size"};
   }

   private static class buffer___add___exposer extends PyBuiltinMethodNarrow {
      public buffer___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public buffer___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new buffer___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((Py2kBuffer)this.self).buffer___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class buffer___mul___exposer extends PyBuiltinMethodNarrow {
      public buffer___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public buffer___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new buffer___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((Py2kBuffer)this.self).buffer___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class buffer___rmul___exposer extends PyBuiltinMethodNarrow {
      public buffer___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public buffer___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new buffer___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((Py2kBuffer)this.self).buffer___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return Py2kBuffer.buffer_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new buffer___add___exposer("__add__"), new buffer___mul___exposer("__mul__"), new buffer___rmul___exposer("__rmul__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("buffer", Py2kBuffer.class, PyObject.class, (boolean)0, "buffer(object [, offset[, size]])\n\nCreate a new buffer object which references the given object.\nThe buffer will reference a slice of the target object from the\nstart of the object (or at the specified offset). The slice will\nextend to the end of the target object (or with the specified size).", var1, var2, new exposed___new__());
      }
   }
}

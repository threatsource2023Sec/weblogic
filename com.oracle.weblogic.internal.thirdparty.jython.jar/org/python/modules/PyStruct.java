package org.python.modules;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "struct.Struct",
   base = PyObject.class
)
public class PyStruct extends PyObject {
   public static final PyType TYPE;
   public final String format;
   public final int size;
   private final struct.FormatDef[] format_def;

   public PyType getType() {
      return TYPE;
   }

   public PyStruct(PyString format) {
      this(TYPE, format);
   }

   public PyStruct(PyType type, PyString format) {
      super(type);
      this.format = format.toString();
      this.format_def = struct.whichtable(this.format);
      this.size = struct.calcsize(this.format, this.format_def);
   }

   @ExposedNew
   static final PyObject Struct___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Struct", args, keywords, new String[]{"format"}, 1);
      PyObject format = ap.getPyObject(0);
      if (!(format instanceof PyString)) {
         throw Py.TypeError("coercing to Unicode: need string, '" + format.getType().fastGetName() + "' type found");
      } else {
         return new PyStruct(TYPE, (PyString)format);
      }
   }

   public String pack(PyObject[] args, String[] kwds) {
      return struct.pack(this.format, this.format_def, this.size, 0, args).toString();
   }

   final void pack_into(PyObject[] args, String[] kwds) {
      struct.pack_into(this.format, this.format_def, this.size, 0, args);
   }

   public PyTuple unpack(PyObject source) {
      String s;
      if (source instanceof PyString) {
         s = source.toString();
      } else {
         if (!(source instanceof PyArray)) {
            throw Py.TypeError("unpack of a str or array");
         }

         s = ((PyArray)source).tostring();
      }

      if (this.size != s.length()) {
         throw struct.StructError("unpack str size does not match format");
      } else {
         return struct.unpack(this.format_def, this.size, this.format, new struct.ByteStream(s));
      }
   }

   public PyTuple unpack_from(PyObject string, int offset) {
      String s = string.toString();
      if (this.size >= s.length() - offset + 1) {
         throw struct.StructError("unpack_from str size does not match format");
      } else {
         return struct.unpack(this.format_def, this.size, this.format, new struct.ByteStream(s, offset));
      }
   }

   static {
      PyType.addBuilder(PyStruct.class, new PyExposer());
      TYPE = PyType.fromClass(PyStruct.class);
   }

   private static class pack_exposer extends PyBuiltinMethod {
      public pack_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public pack_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new pack_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         String var10000 = ((PyStruct)this.self).pack(var1, var2);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class pack_into_exposer extends PyBuiltinMethod {
      public pack_into_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public pack_into_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new pack_into_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyStruct)this.self).pack_into(var1, var2);
         return Py.None;
      }
   }

   private static class unpack_exposer extends PyBuiltinMethodNarrow {
      public unpack_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public unpack_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unpack_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStruct)this.self).unpack(var1);
      }
   }

   private static class unpack_from_exposer extends PyBuiltinMethodNarrow {
      public unpack_from_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public unpack_from_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unpack_from_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyStruct)this.self).unpack_from(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStruct)this.self).unpack_from(var1, 0);
      }
   }

   private static class size_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public size_descriptor() {
         super("size", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyStruct)var1).size);
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
         super("format", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyStruct)var1).format;
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

   private static class __class___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __class___descriptor() {
         super("__class__", PyType.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStruct)var1).getType();
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
         return PyStruct.Struct___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new pack_exposer("pack"), new pack_into_exposer("pack_into"), new unpack_exposer("unpack"), new unpack_from_exposer("unpack_from")};
         PyDataDescr[] var2 = new PyDataDescr[]{new size_descriptor(), new format_descriptor(), new __class___descriptor()};
         super("struct.Struct", PyStruct.class, PyObject.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

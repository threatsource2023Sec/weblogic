package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "sys.version_info",
   isBaseType = false
)
public class PyVersionInfo extends PyTuple {
   public static final PyType TYPE;
   public PyObject major;
   public PyObject minor;
   public PyObject micro;
   public PyObject releaselevel;
   public PyObject serial;
   public static final int n_sequence_fields = 5;
   public static final int n_fields = 5;
   public static final int n_unnamed_fields = 5;

   PyVersionInfo(PyObject... vals) {
      super(TYPE, vals);
      this.major = vals[0];
      this.minor = vals[1];
      this.micro = vals[2];
      this.releaselevel = vals[3];
      this.serial = vals[4];
   }

   @ExposedNew
   static final PyObject version_info_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("version_info", args, keywords, new String[]{"tuple"}, 1);
      PyObject obj = ap.getPyObject(0);
      if (obj instanceof PyTuple) {
         if (obj.__len__() != 5) {
            String msg = String.format("version_info() takes a %s-sequence (%s-sequence given)", 5, obj.__len__());
            throw Py.TypeError(msg);
         } else {
            return new PyVersionInfo(((PyTuple)obj).getArray());
         }
      } else {
         PyList seq = new PyList(obj);
         if (seq.__len__() != 5) {
            String msg = String.format("version_info() takes a %s-sequence (%s-sequence given)", 5, obj.__len__());
            throw Py.TypeError(msg);
         } else {
            return new PyVersionInfo((PyObject[])((PyObject[])seq.__tojava__(PyObject[].class)));
         }
      }
   }

   public PyObject __reduce__() {
      return this.version_info___reduce__();
   }

   final PyObject version_info___reduce__() {
      PyTuple newargs = this.__getnewargs__();
      return new PyTuple(new PyObject[]{this.getType(), newargs});
   }

   public PyTuple __getnewargs__() {
      return new PyTuple(new PyObject[]{new PyList(this.getArray())});
   }

   public PyString __repr__() {
      return (PyString)Py.newString(TYPE.fastGetName() + "(" + "major=%r, minor=%r, micro=%r, releaselevel=%r, serial=%r)").__mod__(this);
   }

   static {
      PyType.addBuilder(PyVersionInfo.class, new PyExposer());
      TYPE = PyType.fromClass(PyVersionInfo.class);
   }

   private static class version_info___reduce___exposer extends PyBuiltinMethodNarrow {
      public version_info___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public version_info___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new version_info___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyVersionInfo)this.self).version_info___reduce__();
      }
   }

   private static class major_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public major_descriptor() {
         super("major", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyVersionInfo)var1).major;
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

   private static class minor_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public minor_descriptor() {
         super("minor", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyVersionInfo)var1).minor;
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

   private static class n_sequence_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_sequence_fields_descriptor() {
         super("n_sequence_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyVersionInfo)var1).n_sequence_fields);
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

   private static class micro_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public micro_descriptor() {
         super("micro", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyVersionInfo)var1).micro;
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

   private static class serial_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public serial_descriptor() {
         super("serial", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyVersionInfo)var1).serial;
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

   private static class releaselevel_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public releaselevel_descriptor() {
         super("releaselevel", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyVersionInfo)var1).releaselevel;
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

   private static class n_unnamed_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_unnamed_fields_descriptor() {
         super("n_unnamed_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyVersionInfo)var1).n_unnamed_fields);
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

   private static class n_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_fields_descriptor() {
         super("n_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyVersionInfo)var1).n_fields);
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
         return PyVersionInfo.version_info_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new version_info___reduce___exposer("__reduce__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new major_descriptor(), new minor_descriptor(), new n_sequence_fields_descriptor(), new micro_descriptor(), new serial_descriptor(), new releaselevel_descriptor(), new n_unnamed_fields_descriptor(), new n_fields_descriptor()};
         super("sys.version_info", PyVersionInfo.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}

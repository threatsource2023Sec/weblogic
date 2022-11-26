package org.python.core;

import java.io.Serializable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "NoneType",
   isBaseType = false
)
public class PyNone extends PyObject implements Serializable {
   public static final PyType TYPE;

   PyNone() {
      super(TYPE);
   }

   public boolean __nonzero__() {
      return false;
   }

   public Object __tojava__(Class c) {
      if (c == PyObject.class) {
         return this;
      } else {
         return c.isPrimitive() ? Py.NoConversion : null;
      }
   }

   public String toString() {
      return this.NoneType_toString();
   }

   final String NoneType_toString() {
      return "None";
   }

   public String asStringOrNull(int index) {
      return null;
   }

   public String asStringOrNull() {
      return null;
   }

   private Object writeReplace() {
      return new Py.SingletonResolver("None");
   }

   static {
      PyType.addBuilder(PyNone.class, new PyExposer());
      TYPE = PyType.fromClass(PyNone.class);
   }

   private static class NoneType_toString_exposer extends PyBuiltinMethodNarrow {
      public NoneType_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public NoneType_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new NoneType_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyNone)this.self).NoneType_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new NoneType_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("NoneType", PyNone.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}

package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinClassMethodNarrow;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.StringCData",
   base = CData.class
)
public class StringCData extends AbstractMemoryCData {
   public static final PyType TYPE;

   public StringCData(PyType pytype, CType ctype, DirectMemory m) {
      super(pytype, ctype, m);
   }

   @ExposedNew
   public static PyObject StringCData_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length == 0) {
         return new StringCData(subtype, CType.typeOf(subtype), NullMemory.INSTANCE);
      } else {
         byte[] str = args[0].asString().getBytes();
         DirectMemory m = AllocatedNativeMemory.allocate(str.length + 1, false);
         m.putZeroTerminatedByteArray(0L, str, 0, str.length);
         return new StringCData(subtype, CType.typeOf(subtype), m);
      }
   }

   public static final PyObject from_address(PyType subtype, PyObject address) {
      DirectMemory m = Util.getMemoryForAddress(address);
      StringCData cdata = new StringCData(subtype, CType.typeOf(subtype), m.getMemory(0L));
      cdata.setReferenceMemory(m);
      return cdata;
   }

   public PyObject getValue() {
      Memory m = this.getMemory();
      return (PyObject)(!m.isNull() ? Py.newString(new String(m.getZeroTerminatedByteArray(0L))) : Py.None);
   }

   public void setValue(PyObject value) {
      byte[] str = value.asString().getBytes();
      DirectMemory m = AllocatedNativeMemory.allocate(str.length + 1, false);
      m.putZeroTerminatedByteArray(0L, str, 0, str.length);
      this.memory = m;
      if (this.hasReferenceMemory()) {
         this.getReferenceMemory().putAddress(0L, m);
      }

   }

   public final String toString() {
      return this.getType().getName() + "(" + this.getValue().toString() + ")";
   }

   public String asString() {
      Memory m = this.getMemory();
      return !m.isNull() ? new String(m.getZeroTerminatedByteArray(0L)) : null;
   }

   static {
      PyType.addBuilder(StringCData.class, new PyExposer());
      TYPE = PyType.fromClass(StringCData.class);
   }

   private static class from_address_exposer extends PyBuiltinClassMethodNarrow {
      public from_address_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public from_address_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new from_address_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return StringCData.from_address((PyType)this.self, var1);
      }
   }

   private static class value_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public value_descriptor() {
         super("value", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((StringCData)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((StringCData)var1).setValue((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return StringCData.StringCData_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_address_exposer("from_address")};
         PyDataDescr[] var2 = new PyDataDescr[]{new value_descriptor()};
         super("jffi.StringCData", StringCData.class, CData.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

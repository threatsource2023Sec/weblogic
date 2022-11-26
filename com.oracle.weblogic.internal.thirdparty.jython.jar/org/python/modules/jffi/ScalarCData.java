package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinClassMethodNarrow;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyFloat;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.ScalarCData",
   base = CData.class
)
public class ScalarCData extends CData {
   public static final PyType TYPE;
   private PyObject value;

   @ExposedNew
   public static PyObject ScalarCData_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ScalarCData cdata = new ScalarCData(subtype, getScalarType(subtype));
      cdata.setValue((PyObject)(args.length > 0 ? args[0] : Py.newInteger(0)));
      return cdata;
   }

   public static final PyObject from_address(PyType subtype, PyObject address) {
      return new ScalarCData(subtype, getScalarType(subtype), Util.getMemoryForAddress(address));
   }

   static final CType.Builtin getScalarType(PyType subtype) {
      PyObject jffi_type = subtype.__getattr__("_jffi_type");
      if (!(jffi_type instanceof CType.Builtin)) {
         throw Py.TypeError("invalid _jffi_type for " + subtype.getName());
      } else {
         return (CType.Builtin)jffi_type;
      }
   }

   ScalarCData(PyType pytype, CType.Builtin ctype) {
      super(pytype, ctype);
   }

   ScalarCData(PyType pytype, CType.Builtin ctype, DirectMemory m) {
      super(pytype, ctype, m);
   }

   protected final void initReferenceMemory(Memory m) {
      this.getMemoryOp().put(m, 0L, this.value);
   }

   public PyObject getValue() {
      if (this.hasReferenceMemory()) {
         return this.getMemoryOp().get(this.getReferenceMemory(), 0L);
      } else {
         return this.value != null ? this.value : Py.None;
      }
   }

   public void setValue(PyObject value) {
      this.value = value;
      if (this.hasReferenceMemory()) {
         this.getMemoryOp().put(this.getReferenceMemory(), 0L, value);
      }

   }

   public int asInt() {
      return this.getValue().asInt();
   }

   public long asLong(int index) throws PyObject.ConversionException {
      return this.getValue().asLong(index);
   }

   public long asLong() {
      return this.getValue().asLong();
   }

   public PyObject __int__() {
      return this.getValue().__int__();
   }

   public PyObject __long__() {
      return this.getValue().__long__();
   }

   public PyFloat __float__() {
      return this.getValue().__float__();
   }

   public final String toString() {
      return this.getType().getName() + "(" + this.getValue().toString() + ")";
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.value != null) {
         int res = visit.visit(this.value, arg);
         if (res != 0) {
            return res;
         }
      }

      return super.traverse(visit, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.value ? true : super.refersDirectlyTo(ob);
   }

   static {
      PyType.addBuilder(ScalarCData.class, new PyExposer());
      TYPE = PyType.fromClass(ScalarCData.class);
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
         return ScalarCData.from_address((PyType)this.self, var1);
      }
   }

   private static class __int___exposer extends PyBuiltinMethodNarrow {
      public __int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((ScalarCData)this.self).__int__();
      }
   }

   private static class __long___exposer extends PyBuiltinMethodNarrow {
      public __long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((ScalarCData)this.self).__long__();
      }
   }

   private static class __float___exposer extends PyBuiltinMethodNarrow {
      public __float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((ScalarCData)this.self).__float__();
      }
   }

   private static class value_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public value_descriptor() {
         super("value", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ScalarCData)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ScalarCData)var1).setValue((PyObject)var2);
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
         return ScalarCData.ScalarCData_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_address_exposer("from_address"), new __int___exposer("__int__"), new __long___exposer("__long__"), new __float___exposer("__float__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new value_descriptor()};
         super("jffi.ScalarCData", ScalarCData.class, CData.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

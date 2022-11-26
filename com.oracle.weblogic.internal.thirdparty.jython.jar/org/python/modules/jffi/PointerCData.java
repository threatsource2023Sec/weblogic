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
   name = "jffi.PointerCData",
   base = CData.class
)
public class PointerCData extends AbstractMemoryCData implements Pointer {
   public static final PyType TYPE;
   final MemoryOp componentMemoryOp;

   PointerCData(PyType subtype, CType type, DirectMemory memory, MemoryOp componentMemoryOp) {
      super(subtype, type, memory);
      this.componentMemoryOp = componentMemoryOp;
   }

   @ExposedNew
   public static PyObject PointerCData_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      CType.Pointer pointerType = getPointerType(subtype);
      if (args.length == 0) {
         return new PointerCData(subtype, pointerType, NullMemory.INSTANCE, pointerType.componentMemoryOp);
      } else {
         PyObject value = args[0];
         if (value instanceof CData && value.getType().isSubType(pointerType.pyComponentType)) {
            return new PointerCData(subtype, pointerType, ((CData)value).getReferenceMemory(), pointerType.componentMemoryOp);
         } else {
            throw Py.TypeError("expected " + pointerType.pyComponentType.getName() + " instead of " + value.getType().getName());
         }
      }
   }

   static final CType.Pointer getPointerType(PyType subtype) {
      PyObject jffi_type = subtype.__getattr__("_jffi_type");
      if (!(jffi_type instanceof CType.Pointer)) {
         throw Py.TypeError("invalid _jffi_type for " + subtype.getName());
      } else {
         return (CType.Pointer)jffi_type;
      }
   }

   public static final PyObject from_address(PyType subtype, PyObject address) {
      CType.Pointer pointerType = getPointerType(subtype);
      DirectMemory m = Util.getMemoryForAddress(address);
      PointerCData cdata = new PointerCData(subtype, pointerType, m.getMemory(0L), pointerType.componentMemoryOp);
      cdata.setReferenceMemory(m);
      return cdata;
   }

   public PyObject getContents() {
      return this.componentMemoryOp.get(this.getMemory(), 0L);
   }

   public void setContents(PyObject value) {
      this.componentMemoryOp.put(this.getMemory(), 0L, value);
   }

   static {
      PyType.addBuilder(PointerCData.class, new PyExposer());
      TYPE = PyType.fromClass(PointerCData.class);
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
         return PointerCData.from_address((PyType)this.self, var1);
      }
   }

   private static class contents_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public contents_descriptor() {
         super("contents", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PointerCData)var1).getContents();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PointerCData)var1).setContents((PyObject)var2);
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
         return PointerCData.PointerCData_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_address_exposer("from_address")};
         PyDataDescr[] var2 = new PyDataDescr[]{new contents_descriptor()};
         super("jffi.PointerCData", PointerCData.class, CData.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

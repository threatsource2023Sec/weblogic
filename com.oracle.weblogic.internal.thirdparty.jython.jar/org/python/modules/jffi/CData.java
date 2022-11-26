package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.CData",
   base = PyObject.class
)
public abstract class CData extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   private final CType ctype;
   private DirectMemory referenceMemory;

   CData(PyType subtype, CType ctype) {
      this(subtype, ctype, (DirectMemory)null);
   }

   CData(PyType subtype, CType ctype, DirectMemory memory) {
      super(subtype);
      this.ctype = ctype;
      this.referenceMemory = memory;
   }

   public PyObject byref(PyObject offset) {
      return new ByReference(this.ctype, (DirectMemory)this.getReferenceMemory().slice((long)offset.asInt()));
   }

   public PyObject pointer(PyObject pytype) {
      if (!(pytype instanceof PyType)) {
         throw Py.TypeError("expected type");
      } else {
         return new PointerCData((PyType)pytype, CType.typeOf(pytype), this.getReferenceMemory(), this.getMemoryOp());
      }
   }

   public PyObject address() {
      return Py.newInteger(this.getReferenceMemory().getAddress());
   }

   final CType getCType() {
      return this.ctype;
   }

   MemoryOp getMemoryOp() {
      return this.getCType().getMemoryOp();
   }

   final boolean hasReferenceMemory() {
      return this.referenceMemory != null;
   }

   final void setReferenceMemory(Memory memory) {
      if (!(memory instanceof DirectMemory)) {
         throw Py.TypeError("invalid memory");
      } else {
         this.referenceMemory = (DirectMemory)memory;
      }
   }

   public final DirectMemory getReferenceMemory() {
      return this.referenceMemory != null ? this.referenceMemory : this.allocateReferenceMemory();
   }

   protected DirectMemory allocateReferenceMemory() {
      DirectMemory m = AllocatedNativeMemory.allocate(this.getCType().size(), false);
      this.initReferenceMemory(m);
      this.referenceMemory = m;
      return m;
   }

   public Memory getContentMemory() {
      return this.getReferenceMemory();
   }

   protected abstract void initReferenceMemory(Memory var1);

   protected static final DirectMemory findInDll(PyObject lib, PyObject name) {
      if (!(lib instanceof DynamicLibrary)) {
         throw Py.TypeError("expected library, not " + lib.getType().fastGetName());
      } else {
         DynamicLibrary.Symbol sym = (DynamicLibrary.Symbol)((DynamicLibrary)lib).find_symbol(name);
         return sym.getMemory();
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.ctype != null ? visit.visit(this.ctype, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.ctype;
   }

   static {
      PyType.addBuilder(CData.class, new PyExposer());
      TYPE = PyType.fromClass(CData.class);
   }

   private static class byref_exposer extends PyBuiltinMethodNarrow {
      public byref_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public byref_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new byref_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((CData)this.self).byref(var1);
      }
   }

   private static class pointer_exposer extends PyBuiltinMethodNarrow {
      public pointer_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public pointer_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new pointer_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((CData)this.self).pointer(var1);
      }
   }

   private static class address_exposer extends PyBuiltinMethodNarrow {
      public address_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public address_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new address_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((CData)this.self).address();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new byref_exposer("byref"), new pointer_exposer("pointer"), new address_exposer("address")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.CData", CData.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}

package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinClassMethodNarrow;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PySequenceList;
import org.python.core.PyType;
import org.python.core.SequenceIndexDelegate;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.ArrayCData",
   base = CData.class
)
public class ArrayCData extends CData implements Pointer {
   public static final PyType TYPE;
   final CType.Array arrayType;
   final CType componentType;
   final MemoryOp componentMemoryOp;
   protected final SequenceIndexDelegate delegator = new SequenceIndexDelegate() {
      public String getTypeName() {
         return ArrayCData.this.getType().fastGetName();
      }

      public void setItem(int idx, PyObject value) {
         ArrayCData.this.componentMemoryOp.put(ArrayCData.this.getReferenceMemory(), (long)(idx * ArrayCData.this.componentType.size()), value);
      }

      public void setSlice(int start, int stop, int step, PyObject value) {
         if (!(value instanceof PySequenceList)) {
            throw Py.TypeError("expected list or tuple");
         } else {
            PySequenceList list = (PySequenceList)value;

            for(int i = 0; i < stop - start; ++i) {
               this.setItem(start + i, list.pyget(i));
            }

         }
      }

      public int len() {
         return ArrayCData.this.arrayType.length;
      }

      public void delItem(int idx) {
         throw Py.TypeError("Array does not support item deletion");
      }

      public void delItems(int start, int stop) {
         throw Py.TypeError("Array does not support item deletion");
      }

      public PyObject getItem(int idx) {
         return ArrayCData.this.componentMemoryOp.get(ArrayCData.this.getReferenceMemory(), (long)(idx * ArrayCData.this.componentType.size()));
      }

      public PyObject getSlice(int start, int stop, int step) {
         PyObject[] result = new PyObject[stop - start];

         for(int i = 0; i < result.length; ++i) {
            result[i] = this.getItem(start + i);
         }

         return new PyList(result);
      }
   };

   ArrayCData(PyType subtype, CType.Array arrayType, DirectMemory memory, MemoryOp componentMemoryOp) {
      super(subtype, arrayType, memory);
      this.arrayType = arrayType;
      this.componentType = arrayType.componentType;
      this.componentMemoryOp = componentMemoryOp;
   }

   @ExposedNew
   public static PyObject ArrayCData_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      CType.Array arrayType = getArrayType(subtype);
      boolean clear = args.length < arrayType.length;
      DirectMemory memory = AllocatedNativeMemory.allocateAligned(arrayType.componentType.size() * arrayType.length, arrayType.componentType.alignment(), clear);
      int offset = 0;
      PyObject[] var9 = args;
      int var10 = args.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         PyObject value = var9[var11];
         arrayType.componentMemoryOp.put(memory, (long)offset, value);
         offset += arrayType.componentType.size();
      }

      return new ArrayCData(subtype, arrayType, memory, arrayType.componentMemoryOp);
   }

   static final CType.Array getArrayType(PyType subtype) {
      PyObject jffi_type = subtype.__getattr__("_jffi_type");
      if (!(jffi_type instanceof CType.Array)) {
         throw Py.TypeError("invalid _jffi_type for " + subtype.getName());
      } else {
         return (CType.Array)jffi_type;
      }
   }

   public static final PyObject from_address(PyType subtype, PyObject address) {
      CType.Array arrayType = getArrayType(subtype);
      DirectMemory m = Util.getMemoryForAddress(address);
      PointerCData cdata = new PointerCData(subtype, arrayType, m.getMemory(0L), arrayType.componentMemoryOp);
      cdata.setReferenceMemory(m);
      return cdata;
   }

   public final DirectMemory getMemory() {
      return this.getReferenceMemory();
   }

   protected final void initReferenceMemory(Memory m) {
   }

   public PyObject __finditem__(int index) {
      return this.delegator.checkIdxAndFindItem(index);
   }

   public PyObject __getitem__(PyObject index) {
      return this.delegator.checkIdxAndGetItem(index);
   }

   public void __setitem__(int index, PyObject value) {
      this.delegator.checkIdxAndSetItem(index, value);
   }

   public void __setitem__(PyObject index, PyObject value) {
      this.delegator.checkIdxAndSetItem(index, value);
   }

   public void __delitem__(PyObject key) {
      throw Py.TypeError("Array does not support item deletion");
   }

   public PyObject __iter__() {
      return new ArrayIter();
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         return this.componentType != null ? visit.visit(this.componentType, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.componentType == ob || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(ArrayCData.class, new PyExposer());
      TYPE = PyType.fromClass(ArrayCData.class);
   }

   public class ArrayIter extends PyIterator {
      private int index = 0;

      public PyObject __iternext__() {
         return this.index >= ArrayCData.this.arrayType.length ? null : ArrayCData.this.componentMemoryOp.get(ArrayCData.this.getReferenceMemory(), (long)(this.index++ * ArrayCData.this.componentType.size()));
      }
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
         return ArrayCData.from_address((PyType)this.self, var1);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return ArrayCData.ArrayCData_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_address_exposer("from_address")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.ArrayCData", ArrayCData.class, CData.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

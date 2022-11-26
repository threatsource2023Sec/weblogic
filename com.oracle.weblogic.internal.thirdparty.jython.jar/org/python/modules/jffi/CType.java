package org.python.modules.jffi;

import com.kenai.jffi.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Untraversable;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.Type",
   base = PyObject.class
)
public abstract class CType extends PyObject {
   public static final PyType TYPE;
   public static final CType VOID;
   public static final CType BOOL;
   public static final CType BYTE;
   public static final CType UBYTE;
   public static final CType SHORT;
   public static final CType USHORT;
   public static final CType INT;
   public static final CType UINT;
   public static final CType LONGLONG;
   public static final CType ULONGLONG;
   public static final CType LONG;
   public static final CType ULONG;
   public static final CType FLOAT;
   public static final CType DOUBLE;
   public static final CType POINTER;
   public static final CType STRING;
   final NativeType nativeType;
   private final MemoryOp memoryOp;

   CType(NativeType type, MemoryOp memoryOp) {
      this.nativeType = type;
      this.memoryOp = memoryOp;
   }

   public NativeType getNativeType() {
      return this.nativeType;
   }

   abstract Type jffiType();

   MemoryOp getMemoryOp() {
      return this.memoryOp;
   }

   public final int alignment() {
      return this.jffiType().alignment();
   }

   public final int size() {
      return this.jffiType().size();
   }

   public final PyObject pysize() {
      return Py.newInteger(this.size());
   }

   public final PyObject pyalignment() {
      return Py.newInteger(this.alignment());
   }

   static final CType primitive(NativeType type) {
      CType t = new Builtin(type);
      TYPE.fastGetDict().__setitem__((String)type.name(), t);
      return t;
   }

   static CType typeOf(PyObject obj) {
      if (obj instanceof CType) {
         return (CType)obj;
      } else if (obj == Py.None) {
         return VOID;
      } else {
         PyObject jffi_type = obj.__getattr__("_jffi_type");
         if (!(jffi_type instanceof CType)) {
            throw Py.TypeError("invalid _jffi_type");
         } else {
            return (CType)jffi_type;
         }
      }
   }

   static {
      PyType.addBuilder(CType.class, new PyExposer());
      TYPE = PyType.fromClass(CType.class);
      TYPE.fastGetDict().__setitem__((String)"Array", CType.Array.TYPE);
      TYPE.fastGetDict().__setitem__((String)"Pointer", CType.Pointer.TYPE);
      VOID = primitive(NativeType.VOID);
      BOOL = primitive(NativeType.BOOL);
      BYTE = primitive(NativeType.BYTE);
      UBYTE = primitive(NativeType.UBYTE);
      SHORT = primitive(NativeType.SHORT);
      USHORT = primitive(NativeType.USHORT);
      INT = primitive(NativeType.INT);
      UINT = primitive(NativeType.UINT);
      LONGLONG = primitive(NativeType.LONGLONG);
      ULONGLONG = primitive(NativeType.ULONGLONG);
      LONG = primitive(NativeType.LONG);
      ULONG = primitive(NativeType.ULONG);
      FLOAT = primitive(NativeType.FLOAT);
      DOUBLE = primitive(NativeType.DOUBLE);
      POINTER = primitive(NativeType.POINTER);
      STRING = primitive(NativeType.STRING);
   }

   @ExposedType(
      name = "jffi.Type.Pointer",
      base = CType.class
   )
   static final class Pointer extends Custom implements Traverseproc {
      public static final PyType TYPE;
      private static final ConcurrentMap typeCache;
      final CType componentType;
      final PyType pyComponentType;
      final MemoryOp componentMemoryOp;

      Pointer(PyType subtype, PyType pyComponentType, CType componentType) {
         super(NativeType.POINTER, Type.POINTER, new MemoryOp.PointerOp(subtype, CType.POINTER));
         this.pyComponentType = pyComponentType;
         this.componentType = componentType;
         if (pyComponentType.isSubType(ScalarCData.TYPE)) {
            this.componentMemoryOp = new ScalarOp(componentType.getMemoryOp(), pyComponentType);
         } else {
            if (!pyComponentType.isSubType(Structure.TYPE)) {
               throw Py.TypeError("pointer only supported for scalar types");
            }

            this.componentMemoryOp = new MemoryOp.StructOp(pyComponentType);
         }

      }

      @ExposedNew
      public static PyObject Pointer_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         if (args.length != 1) {
            throw Py.TypeError(String.format("__init__() takes exactly 1 argument (%d given)", args.length));
         } else {
            Pointer p = (Pointer)typeCache.get(args[0]);
            if (p != null) {
               return p;
            } else if (!(args[0] instanceof PyType)) {
               throw Py.TypeError("expected ctypes class");
            } else {
               p = new Pointer(subtype, (PyType)args[0], typeOf(args[0]));
               typeCache.put(args[0], p);
               return p;
            }
         }
      }

      public final String toString() {
         return String.format("<jffi.Type.Pointer component_type=%s>", this.componentType.toString());
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.componentType != null) {
            int res = visit.visit(this.componentType, arg);
            if (res != 0) {
               return res;
            }
         }

         return this.pyComponentType != null ? visit.visit(this.pyComponentType, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.componentType || ob == this.pyComponentType);
      }

      static {
         PyType.addBuilder(Pointer.class, new PyExposer());
         TYPE = PyType.fromClass(Pointer.class);
         typeCache = new ConcurrentHashMap();
      }

      private static final class ScalarOp extends MemoryOp {
         private final MemoryOp op;
         private final PyType type;

         public ScalarOp(MemoryOp op, PyType type) {
            this.op = op;
            this.type = type;
         }

         public final void put(Memory mem, long offset, PyObject value) {
            this.op.put(mem, offset, value);
         }

         public final PyObject get(Memory mem, long offset) {
            PyObject result = this.type.__call__(this.op.get(mem, offset));
            if (result instanceof ScalarCData) {
               ((ScalarCData)result).setReferenceMemory(mem);
            }

            return result;
         }
      }

      private static class exposed___new__ extends PyNewWrapper {
         public exposed___new__() {
         }

         public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
            return CType.Pointer.Pointer_new(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("jffi.Type.Pointer", Pointer.class, CType.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
         }
      }
   }

   @ExposedType(
      name = "jffi.Type.Array",
      base = CType.class
   )
   static final class Array extends Custom implements Traverseproc {
      public static final PyType TYPE;
      final CType componentType;
      final PyType pyComponentType;
      final MemoryOp componentMemoryOp;
      public final int length;

      public Array(PyType pyComponentType, CType componentType, int length) {
         super(NativeType.ARRAY, new com.kenai.jffi.Array(Util.jffiType(componentType), length), (MemoryOp)null);
         this.pyComponentType = pyComponentType;
         this.componentType = componentType;
         this.componentMemoryOp = getComponentMemoryOp(pyComponentType, componentType);
         this.length = length;
      }

      @ExposedNew
      public static PyObject Array_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         if (args.length != 2) {
            throw Py.TypeError(String.format("__init__() takes exactly 2 arguments (%d given)", args.length));
         } else if (!(args[0] instanceof PyType)) {
            throw Py.TypeError("invalid component type");
         } else {
            return new Array((PyType)args[0], typeOf(args[0]), args[1].asInt());
         }
      }

      public int __len__() {
         return this.length;
      }

      public final String toString() {
         return String.format("<ctypes.Array elem_type=%s length=%d>", this.pyComponentType.toString(), this.length);
      }

      static final MemoryOp getComponentMemoryOp(PyType pyComponentType, CType componentType) {
         if (pyComponentType.isSubType(ScalarCData.TYPE)) {
            return componentType.getMemoryOp();
         } else if (pyComponentType.isSubType(Structure.TYPE)) {
            return new MemoryOp.StructOp(pyComponentType);
         } else {
            throw Py.TypeError("only scalar and struct types supported");
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.componentType != null) {
            int res = visit.visit(this.componentType, arg);
            if (res != 0) {
               return res;
            }
         }

         return this.pyComponentType != null ? visit.visit(this.pyComponentType, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.componentType || ob == this.pyComponentType);
      }

      static {
         PyType.addBuilder(Array.class, new PyExposer());
         TYPE = PyType.fromClass(Array.class);
      }

      private static class length_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public length_descriptor() {
            super("length", Integer.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return Py.newInteger(((Array)var1).length);
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
            return CType.Array.Array_new(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
            PyDataDescr[] var2 = new PyDataDescr[]{new length_descriptor()};
            super("jffi.Type.Array", Array.class, CType.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
         }
      }
   }

   @Untraversable
   @ExposedType(
      name = "jffi.Type.Custom",
      base = CType.class
   )
   static class Custom extends CType {
      final Type jffiType;

      public Custom(NativeType type, Type jffiType, MemoryOp op) {
         super(type, op);
         this.jffiType = jffiType;
      }

      final Type jffiType() {
         return this.jffiType;
      }
   }

   @Untraversable
   static final class Builtin extends CType implements ExposeAsSuperclass {
      public Builtin(NativeType type) {
         super(type, MemoryOp.getMemoryOp(type));
      }

      public final String toString() {
         return "<jffi.Type." + this.nativeType.name() + ">";
      }

      final Type jffiType() {
         return NativeType.jffiType(this.nativeType);
      }
   }

   private static class pysize_exposer extends PyBuiltinMethodNarrow {
      public pysize_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public pysize_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new pysize_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((CType)this.self).pysize();
      }
   }

   private static class pyalignment_exposer extends PyBuiltinMethodNarrow {
      public pyalignment_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public pyalignment_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new pyalignment_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((CType)this.self).pyalignment();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new pysize_exposer("size"), new pyalignment_exposer("alignment")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.Type", CType.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}

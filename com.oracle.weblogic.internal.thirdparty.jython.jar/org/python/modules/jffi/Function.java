package org.python.modules.jffi;

import com.kenai.jffi.CallingConvention;
import com.kenai.jffi.Type;
import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PySequenceList;
import org.python.core.PyStringMap;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.Function",
   base = PyObject.class
)
public class Function extends BasePointer implements Traverseproc {
   public static final PyType TYPE;
   private final Pointer pointer;
   private final DynamicLibrary library;
   private final PyStringMap dict = new PyStringMap();
   private volatile PyObject restype;
   private volatile PyObject[] argtypes;
   private Invoker defaultInvoker;
   private Invoker compiledInvoker;
   private volatile JITHandle jitHandle;
   private volatile com.kenai.jffi.Function jffiFunction;
   public PyObject errcheck;
   public final String name;

   Function(PyType type, Pointer address) {
      super(type);
      this.restype = CType.INT;
      this.argtypes = null;
      this.errcheck = Py.None;
      this.library = null;
      this.name = "<anonymous>";
      this.pointer = address;
      this.restype = type.__getattr__("_restype");
   }

   Function(PyType type, DynamicLibrary.Symbol sym) {
      super(type);
      this.restype = CType.INT;
      this.argtypes = null;
      this.errcheck = Py.None;
      this.library = sym.library;
      this.name = sym.name;
      this.pointer = sym;
      this.restype = type.__getattr__("_restype");
   }

   @ExposedNew
   public static PyObject Function_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args[0] instanceof Pointer) {
         return args[0] instanceof DynamicLibrary.Symbol ? new Function(subtype, (DynamicLibrary.Symbol)args[0]) : new Function(subtype, (Pointer)args[0]);
      } else {
         throw Py.TypeError("expected memory address");
      }
   }

   public DirectMemory getMemory() {
      return this.pointer.getMemory();
   }

   public PyObject fastGetDict() {
      return this.dict;
   }

   public PyObject getDict() {
      return this.dict;
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.getInvoker().invoke(args);
   }

   public PyObject __call__() {
      return this.getInvoker().invoke();
   }

   public PyObject __call__(PyObject arg0) {
      return this.getInvoker().invoke(arg0);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1) {
      return this.getInvoker().invoke(arg0, arg1);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2) {
      return this.getInvoker().invoke(arg0, arg1, arg2);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.getInvoker().invoke(arg0, arg1, arg2, arg3);
   }

   public PyObject getResultType() {
      return this.restype;
   }

   public void setResultType(PyObject restype) {
      this.invalidateInvoker();
      this.restype = restype;
   }

   public PyObject getArgTypes() {
      return new PyList(this.argtypes != null ? this.argtypes : new PyObject[0]);
   }

   public void setArgTypes(PyObject parameterTypes) {
      this.invalidateInvoker();
      if (parameterTypes == Py.None) {
         this.argtypes = null;
      } else if (!(parameterTypes instanceof PySequenceList)) {
         throw Py.TypeError("wrong argument type (expected list or tuple)");
      } else {
         PySequenceList paramList = (PySequenceList)parameterTypes;
         this.argtypes = new PyObject[paramList.size()];

         for(int i = 0; i < this.argtypes.length; ++i) {
            this.argtypes[i] = paramList.pyget(i);
         }

      }
   }

   public void errcheck(PyObject errcheck) {
      this.invalidateInvoker();
      this.errcheck = errcheck;
   }

   public boolean __nonzero__() {
      return !this.getMemory().isNull();
   }

   protected final Invoker getInvoker() {
      return this.compiledInvoker != null ? this.compiledInvoker : this.tryCompilation();
   }

   private synchronized Invoker tryCompilation() {
      if (this.compiledInvoker != null) {
         return this.compiledInvoker;
      } else if (this.argtypes == null) {
         throw Py.NotImplementedError("variadic functions not supported yet;  specify a parameter list");
      } else {
         CType cResultType = CType.typeOf(this.restype);
         CType[] cParameterTypes = new CType[this.argtypes.length];

         for(int i = 0; i < cParameterTypes.length; ++i) {
            cParameterTypes[i] = CType.typeOf(this.argtypes[i]);
         }

         if (this.jitHandle == null) {
            this.jitHandle = JITCompiler.getInstance().getHandle(cResultType, cParameterTypes, CallingConvention.DEFAULT, false);
         }

         if (this.jffiFunction == null) {
            Type jffiReturnType = Util.jffiType(cResultType);
            Type[] jffiParamTypes = new Type[this.argtypes.length];

            for(int i = 0; i < jffiParamTypes.length; ++i) {
               jffiParamTypes[i] = Util.jffiType(cParameterTypes[i]);
            }

            this.jffiFunction = new com.kenai.jffi.Function(this.getMemory().getAddress(), jffiReturnType, jffiParamTypes);
         }

         Invoker invoker;
         if (this.defaultInvoker == null) {
            invoker = DefaultInvokerFactory.getFactory().createInvoker(this.jffiFunction, this.restype, this.argtypes);
            this.defaultInvoker = (Invoker)(this.errcheck != Py.None ? new ErrCheckInvoker(invoker, this.errcheck) : invoker);
         }

         invoker = this.jitHandle.compile(this.jffiFunction, (NativeDataConverter)null, new NativeDataConverter[0]);
         if (invoker != null) {
            return this.compiledInvoker = (Invoker)(this.errcheck != Py.None ? new ErrCheckInvoker(invoker, this.errcheck) : invoker);
         } else {
            if (this.jitHandle.compilationFailed()) {
               this.compiledInvoker = this.defaultInvoker;
            }

            return this.defaultInvoker;
         }
      }
   }

   private synchronized void invalidateInvoker() {
      this.defaultInvoker = null;
      this.compiledInvoker = null;
      this.jitHandle = null;
      this.jffiFunction = null;
   }

   public int traverse(Visitproc visit, Object arg) {
      int res = false;
      int res;
      if (this.pointer != null && this.pointer instanceof PyObject) {
         res = visit.visit((PyObject)this.pointer, arg);
         if (res != 0) {
            return res;
         }
      }

      if (this.dict != null) {
         res = visit.visit(this.dict, arg);
         if (res != 0) {
            return res;
         }
      }

      if (this.restype != null) {
         res = visit.visit(this.restype, arg);
         if (res != 0) {
            return res;
         }
      }

      if (this.argtypes != null) {
         PyObject[] var4 = this.argtypes;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PyObject obj = var4[var6];
            res = visit.visit(obj, arg);
            if (res != 0) {
               return res;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null || this.pointer != ob && this.dict != ob && this.restype != ob) {
         if (this.argtypes != null) {
            PyObject[] var2 = this.argtypes;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   static {
      PyType.addBuilder(Function.class, new PyExposer());
      TYPE = PyType.fromClass(Function.class);
   }

   private static final class ErrCheckInvoker extends Invoker {
      private final Invoker invoker;
      private final PyObject errcheck;

      public ErrCheckInvoker(Invoker invoker, PyObject errcheck) {
         this.invoker = invoker;
         this.errcheck = errcheck;
      }

      public PyObject invoke(PyObject[] args) {
         return this.errcheck.__call__(this.invoker.invoke(args));
      }

      public PyObject invoke() {
         return this.errcheck.__call__(this.invoker.invoke());
      }

      public PyObject invoke(PyObject arg1) {
         return this.errcheck.__call__(this.invoker.invoke(arg1));
      }

      public PyObject invoke(PyObject arg1, PyObject arg2) {
         return this.errcheck.__call__(this.invoker.invoke(arg1, arg2));
      }

      public PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3) {
         return this.errcheck.__call__(this.invoker.invoke(arg1, arg2, arg3));
      }

      public PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
         return this.errcheck.__call__(this.invoker.invoke(arg1, arg2, arg3, arg4));
      }

      public PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject arg5) {
         return this.errcheck.__call__(this.invoker.invoke(arg1, arg2, arg3, arg4, arg5));
      }

      public PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject arg5, PyObject arg6) {
         return this.errcheck.__call__(this.invoker.invoke(arg1, arg2, arg3, arg4, arg5, arg6));
      }
   }

   private static class restype_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public restype_descriptor() {
         super("restype", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Function)var1).getResultType();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Function)var1).setResultType((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class argtypes_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public argtypes_descriptor() {
         super("argtypes", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Function)var1).getArgTypes();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Function)var1).setArgTypes((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public name_descriptor() {
         super("name", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Function)var1).name;
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

   private static class errcheck_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public errcheck_descriptor() {
         super("errcheck", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Function)var1).errcheck;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Function)var1).errcheck((PyObject)var2);
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
         return Function.Function_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new restype_descriptor(), new argtypes_descriptor(), new name_descriptor(), new errcheck_descriptor()};
         super("jffi.Function", Function.class, PyObject.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}

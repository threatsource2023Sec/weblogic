import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_rawffi.py")
public class _rawffi$py extends PyFunctionTable implements PyRunnable {
   static _rawffi$py self;
   static final PyCode f$0;
   static final PyCode get_libc$1;
   static final PyCode Array$2;
   static final PyCode __init__$3;
   static final PyCode __call__$4;
   static final PyCode ArrayInstance$5;
   static final PyCode __init__$6;
   static final PyCode __setitem__$7;
   static final PyCode __getitem__$8;
   static final PyCode FuncPtr$9;
   static final PyCode __init__$10;
   static final PyCode __call__$11;
   static final PyCode CDLL$12;
   static final PyCode __init__$13;
   static final PyCode ptr$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOneAs("com.sun.jna", var1, -1);
      var1.setlocal("jna", var3);
      var3 = null;
      var1.setline(3);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, get_libc$1, (PyObject)null);
      var1.setlocal("get_libc", var6);
      var3 = null;
      var1.setline(6);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("h"), Py.newInteger(2), PyString.fromInterned("H"), Py.newInteger(2)});
      var1.setlocal("typecode_map", var7);
      var3 = null;
      var1.setline(8);
      var5 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("Array", var5, Array$2);
      var1.setlocal("Array", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ArrayInstance", var5, ArrayInstance$5);
      var1.setlocal("ArrayInstance", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(29);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FuncPtr", var5, FuncPtr$9);
      var1.setlocal("FuncPtr", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(41);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("CDLL", var5, CDLL$12);
      var1.setlocal("CDLL", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_libc$1(PyFrame var1, ThreadState var2) {
      var1.setline(4);
      PyObject var3 = var1.getglobal("CDLL").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("c"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Array$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(9);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(13);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$4, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("typecode", var3);
      var3 = null;
      var1.setline(11);
      var3 = var1.getglobal("typecode_map").__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("itemsize", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$4(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(15);
         throw Py.makeException(var1.getglobal("Exception"));
      } else {
         var1.setline(16);
         PyObject var3 = var1.getglobal("ArrayInstance").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ArrayInstance$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$7, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$8, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("shape", var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getglobal("jna").__getattr__("Memory").__call__(var2, var1.getlocal(1).__getattr__("itemsize")._mul(var1.getlocal(2)));
      var1.getlocal(0).__setattr__("alloc", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$7(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      var1.getlocal(0).__getattr__("alloc").__getattr__("setShort").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$8(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(0).__getattr__("alloc").__getattr__("getShort").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FuncPtr$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(30);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$11, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fn", var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("argtypes", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("restype", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$11(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var10000 = var1.getglobal("Array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("H"));
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), var1.getglobal("True")};
      String[] var4 = new String[]{"autofree"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(38);
      var10000 = var1.getlocal(0).__getattr__("fn").__getattr__("invokeInt");
      PyList var10002 = new PyList();
      var5 = var10002.__getattr__("append");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(38);
      var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(38);
         PyObject var6 = var5.__iternext__();
         if (var6 == null) {
            var1.setline(38);
            var1.dellocal(3);
            var5 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.getlocal(2).__setitem__((PyObject)Py.newInteger(0), var5);
            var3 = null;
            var1.setline(39);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var6);
         var1.setline(38);
         var1.getlocal(3).__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)));
      }
   }

   public PyObject CDLL$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ptr$14, (PyObject)null);
      var1.setlocal("ptr", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = var1.getglobal("jna").__getattr__("NativeLibrary").__getattr__("getInstance").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("lib", var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getglobal("dict").__call__(var2);
      var1.getlocal(0).__setattr__("cache", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ptr$14(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)), var1.getlocal(3)});
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var7;
      try {
         var1.setline(49);
         var7 = var1.getlocal(0).__getattr__("cache").__getitem__(var1.getlocal(4));
         var1.f_lasti = -1;
         return var7;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(51);
            PyObject var5 = var1.getlocal(0).__getattr__("lib").__getattr__("getFunction").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(52);
            var5 = var1.getglobal("FuncPtr").__call__(var2, var1.getlocal(5), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(53);
            var5 = var1.getlocal(6);
            var1.getlocal(0).__getattr__("cache").__setitem__(var1.getlocal(4), var5);
            var5 = null;
            var1.setline(54);
            var7 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var7;
         } else {
            throw var4;
         }
      }
   }

   public _rawffi$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      get_libc$1 = Py.newCode(0, var2, var1, "get_libc", 3, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Array$2 = Py.newCode(0, var2, var1, "Array", 8, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "typecode"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 9, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "autofree"};
      __call__$4 = Py.newCode(3, var2, var1, "__call__", 13, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArrayInstance$5 = Py.newCode(0, var2, var1, "ArrayInstance", 18, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "shape", "size"};
      __init__$6 = Py.newCode(3, var2, var1, "__init__", 19, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "value"};
      __setitem__$7 = Py.newCode(3, var2, var1, "__setitem__", 23, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$8 = Py.newCode(2, var2, var1, "__getitem__", 26, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FuncPtr$9 = Py.newCode(0, var2, var1, "FuncPtr", 29, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fn", "name", "argtypes", "restype"};
      __init__$10 = Py.newCode(5, var2, var1, "__init__", 30, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "container", "_[38_42]", "i"};
      __call__$11 = Py.newCode(2, var2, var1, "__call__", 36, true, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CDLL$12 = Py.newCode(0, var2, var1, "CDLL", 41, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "libname"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 42, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "argtypes", "restype", "key", "fn", "fnp"};
      ptr$14 = Py.newCode(4, var2, var1, "ptr", 46, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _rawffi$py("_rawffi$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_rawffi$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.get_libc$1(var2, var3);
         case 2:
            return this.Array$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__call__$4(var2, var3);
         case 5:
            return this.ArrayInstance$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.__setitem__$7(var2, var3);
         case 8:
            return this.__getitem__$8(var2, var3);
         case 9:
            return this.FuncPtr$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.__call__$11(var2, var3);
         case 12:
            return this.CDLL$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.ptr$14(var2, var3);
         default:
            return null;
      }
   }
}

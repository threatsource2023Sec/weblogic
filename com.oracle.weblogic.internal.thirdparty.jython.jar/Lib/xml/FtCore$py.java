package xml;

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
@Filename("xml/FtCore.py")
public class FtCore$py extends PyFunctionTable implements PyRunnable {
   static FtCore$py self;
   static final PyCode f$0;
   static final PyCode FtException$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode _$4;
   static final PyCode get_translator$5;
   static final PyCode get_translator$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nContains various definitions common to modules acquired from 4Suite\n"));
      var1.setline(3);
      PyString.fromInterned("\nContains various definitions common to modules acquired from 4Suite\n");
      var1.setline(5);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("FtException"), PyString.fromInterned("get_translator")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(8);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("FtException", var6, FtException$1);
      var1.setlocal("FtException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(34);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _$4, (PyObject)null);
      var1.setlocal("_", var7);
      var3 = null;

      label19: {
         PyObject[] var8;
         PyFunction var10;
         try {
            var1.setline(38);
            PyObject var11 = imp.importOne("gettext", var1, -1);
            var1.setlocal("gettext", var11);
            var3 = null;
         } catch (Throwable var5) {
            PyException var9 = Py.setException(var5, var1);
            if (var9.match(new PyTuple(new PyObject[]{var1.getname("ImportError"), var1.getname("IOError")}))) {
               var1.setline(41);
               var8 = Py.EmptyObjects;
               var10 = new PyFunction(var1.f_globals, var8, get_translator$5, (PyObject)null);
               var1.setlocal("get_translator", var10);
               var4 = null;
               break label19;
            }

            throw var9;
         }

         var1.setline(45);
         var4 = imp.importOne("os", var1, -1);
         var1.setlocal("os", var4);
         var4 = null;
         var1.setline(47);
         PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal("_cache", var12);
         var4 = null;
         var1.setline(48);
         var4 = var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getname("__file__")));
         var1.setlocal("_top", var4);
         var4 = null;
         var1.setline(50);
         var8 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var8, get_translator$6, (PyObject)null);
         var1.setlocal("get_translator", var10);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FtException$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(9);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("params", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errorCode", var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getlocal(2).__getitem__(var1.getlocal(1))._mod(var1.getlocal(3));
      var1.getlocal(0).__setattr__("message", var3);
      var3 = null;
      var1.setline(15);
      var1.getglobal("Exception").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("message"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(0).__getattr__("message");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _$4(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_translator$5(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("_");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_translator$6(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3;
      if (var1.getglobal("_cache").__getattr__("has_key").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(52);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("_top"), var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)var1.getglobal("os").__getattr__("sep")));
         var1.setlocal(1, var3);
         var3 = null;

         try {
            var1.setline(54);
            var3 = var1.getglobal("gettext").__getattr__("translation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("4Suite"), (PyObject)var1.getlocal(1)).__getattr__("gettext");
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("IOError"))) {
               throw var6;
            }

            var1.setline(56);
            PyObject var4 = var1.getglobal("_");
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(57);
         var3 = var1.getlocal(2);
         var1.getglobal("_cache").__setitem__(var1.getlocal(0), var3);
         var3 = null;
      }

      var1.setline(58);
      var3 = var1.getglobal("_cache").__getitem__(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public FtCore$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FtException$1 = Py.newCode(0, var2, var1, "FtException", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errorCode", "messages", "args"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 9, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg"};
      _$4 = Py.newCode(1, var2, var1, "_", 34, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pkg"};
      get_translator$5 = Py.newCode(1, var2, var1, "get_translator", 41, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pkg", "locale_dir", "f"};
      get_translator$6 = Py.newCode(1, var2, var1, "get_translator", 50, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new FtCore$py("xml/FtCore$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(FtCore$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FtException$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this._$4(var2, var3);
         case 5:
            return this.get_translator$5(var2, var3);
         case 6:
            return this.get_translator$6(var2, var3);
         default:
            return null;
      }
   }
}

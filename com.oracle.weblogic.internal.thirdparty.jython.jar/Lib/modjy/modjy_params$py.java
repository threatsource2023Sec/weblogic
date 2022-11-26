package modjy;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_params.py")
public class modjy_params$py extends PyFunctionTable implements PyRunnable {
   static modjy_params$py self;
   static final PyCode f$0;
   static final PyCode modjy_param_mgr$1;
   static final PyCode __init__$2;
   static final PyCode __getitem__$3;
   static final PyCode __setitem__$4;
   static final PyCode _convert_value$5;
   static final PyCode _get_defaulted_value$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      String[] var3 = new String[]{"UserDict"};
      PyObject[] var5 = imp.importFrom("UserDict", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("UserDict", var4);
      var4 = null;
      var1.setline(23);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("boolean"), var1.getname("int")});
      var1.setlocal("BOOLEAN", var6);
      var3 = null;
      var1.setline(24);
      var6 = new PyTuple(new PyObject[]{PyString.fromInterned("integer"), var1.getname("int")});
      var1.setlocal("INTEGER", var6);
      var3 = null;
      var1.setline(25);
      var6 = new PyTuple(new PyObject[]{PyString.fromInterned("float"), var1.getname("float")});
      var1.setlocal("FLOAT", var6);
      var3 = null;
      var1.setline(26);
      var6 = new PyTuple(new PyObject[]{PyString.fromInterned("string"), var1.getname("None")});
      var1.setlocal("STRING", var6);
      var3 = null;
      var1.setline(28);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("multithread"), new PyTuple(new PyObject[]{var1.getname("BOOLEAN"), Py.newInteger(1)}), PyString.fromInterned("cache_callables"), new PyTuple(new PyObject[]{var1.getname("BOOLEAN"), Py.newInteger(1)}), PyString.fromInterned("reload_on_mod"), new PyTuple(new PyObject[]{var1.getname("BOOLEAN"), Py.newInteger(0)}), PyString.fromInterned("app_import_name"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("app_directory"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("app_filename"), new PyTuple(new PyObject[]{var1.getname("STRING"), PyString.fromInterned("application.py")}), PyString.fromInterned("app_callable_name"), new PyTuple(new PyObject[]{var1.getname("STRING"), PyString.fromInterned("handler")}), PyString.fromInterned("callable_query_name"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("exc_handler"), new PyTuple(new PyObject[]{var1.getname("STRING"), PyString.fromInterned("standard")}), PyString.fromInterned("log_level"), new PyTuple(new PyObject[]{var1.getname("STRING"), PyString.fromInterned("info")}), PyString.fromInterned("packages"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("classdirs"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("extdirs"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")}), PyString.fromInterned("initial_env"), new PyTuple(new PyObject[]{var1.getname("STRING"), var1.getname("None")})});
      var1.setlocal("modjy_servlet_params", var7);
      var3 = null;
      var1.setline(52);
      var5 = new PyObject[]{var1.getname("UserDict")};
      var4 = Py.makeClass("modjy_param_mgr", var5, modjy_param_mgr$1);
      var1.setlocal("modjy_param_mgr", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_param_mgr$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(54);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$3, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$4, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _convert_value$5, (PyObject)null);
      var1.setlocal("_convert_value", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_defaulted_value$6, (PyObject)null);
      var1.setlocal("_get_defaulted_value", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.getglobal("UserDict").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(56);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("param_types", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(0).__getattr__("param_types").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(57);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(58);
         PyObject var5 = var1.getlocal(0).__getattr__("param_types").__getitem__(var1.getlocal(2));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
         var1.setline(59);
         var1.getlocal(0).__getattr__("__setitem__").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      }
   }

   public PyObject __getitem__$3(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_defaulted_value").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("_convert_value").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.getlocal(0).__getattr__("data").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _convert_value$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("param_types").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(69);
         var3 = var1.getlocal(0).__getattr__("param_types").__getitem__(var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(70);
         var3 = var1.getlocal(3);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(71);
         if (var1.getlocal(6).__nonzero__()) {
            try {
               var1.setline(73);
               var3 = var1.getlocal(6).__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var6) {
               PyException var7 = Py.setException(var6, var1);
               if (var7.match(var1.getglobal("ValueError"))) {
                  var1.setline(75);
                  throw Py.makeException(var1.getglobal("BadParameter").__call__(var2, PyString.fromInterned("Illegal value for %s parameter '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)}))));
               }

               throw var7;
            }
         }
      }

      var1.setline(76);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_defaulted_value$6(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("data").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(80);
         var3 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(81);
         if (var1.getlocal(0).__getattr__("param_types").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(82);
            PyObject var4 = var1.getlocal(0).__getattr__("param_types").__getitem__(var1.getlocal(1));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var4 = null;
            var1.setline(83);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(84);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         }
      }
   }

   public modjy_params$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_param_mgr$1 = Py.newCode(0, var2, var1, "modjy_param_mgr", 52, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "param_types", "pname", "typ", "default"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$3 = Py.newCode(2, var2, var1, "__getitem__", 61, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      __setitem__$4 = Py.newCode(3, var2, var1, "__setitem__", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "typ", "default", "typ_str", "typ_func"};
      _convert_value$5 = Py.newCode(3, var2, var1, "_convert_value", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "typ", "default"};
      _get_defaulted_value$6 = Py.newCode(2, var2, var1, "_get_defaulted_value", 78, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_params$py("modjy/modjy_params$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_params$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_param_mgr$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__getitem__$3(var2, var3);
         case 4:
            return this.__setitem__$4(var2, var3);
         case 5:
            return this._convert_value$5(var2, var3);
         case 6:
            return this._get_defaulted_value$6(var2, var3);
         default:
            return null;
      }
   }
}

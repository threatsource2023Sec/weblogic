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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_log.py")
public class modjy_log$py extends PyFunctionTable implements PyRunnable {
   static modjy_log$py self;
   static final PyCode f$0;
   static final PyCode modjy_logger$1;
   static final PyCode __init__$2;
   static final PyCode _log$3;
   static final PyCode debug$4;
   static final PyCode info$5;
   static final PyCode warn$6;
   static final PyCode error$7;
   static final PyCode fatal$8;
   static final PyCode set_log_level$9;
   static final PyCode set_log_format$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("java", var1, -1);
      var1.setlocal("java", var3);
      var3 = null;
      var1.setline(23);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(25);
      PyString var6 = PyString.fromInterned("debug");
      var1.setlocal("DEBUG", var6);
      var3 = null;
      var1.setline(26);
      var6 = PyString.fromInterned("info");
      var1.setlocal("INFO", var6);
      var3 = null;
      var1.setline(27);
      var6 = PyString.fromInterned("warn");
      var1.setlocal("WARN", var6);
      var3 = null;
      var1.setline(28);
      var6 = PyString.fromInterned("error");
      var1.setlocal("ERROR", var6);
      var3 = null;
      var1.setline(29);
      var6 = PyString.fromInterned("fatal");
      var1.setlocal("FATAL", var6);
      var3 = null;
      var1.setline(31);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("levels_dict", var7);
      var3 = null;
      var1.setline(32);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal("ix", var8);
      var3 = null;
      var1.setline(33);
      var3 = (new PyList(new PyObject[]{var1.getname("DEBUG"), var1.getname("INFO"), var1.getname("WARN"), var1.getname("ERROR"), var1.getname("FATAL")})).__iter__();

      while(true) {
         var1.setline(33);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(37);
            PyObject[] var9 = Py.EmptyObjects;
            var4 = Py.makeClass("modjy_logger", var9, modjy_logger$1);
            var1.setlocal("modjy_logger", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("level", var4);
         var1.setline(34);
         PyObject var5 = var1.getname("ix");
         var1.getname("levels_dict").__setitem__(var1.getname("level"), var5);
         var5 = null;
         var1.setline(35);
         var5 = var1.getname("ix");
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal("ix", var5);
      }
   }

   public PyObject modjy_logger$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(39);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _log$3, (PyObject)null);
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(54);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, debug$4, (PyObject)null);
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(57);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, info$5, (PyObject)null);
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(60);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, warn$6, (PyObject)null);
      var1.setlocal("warn", var4);
      var3 = null;
      var1.setline(63);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, error$7, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(66);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fatal$8, (PyObject)null);
      var1.setlocal("fatal", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_log_level$9, (PyObject)null);
      var1.setlocal("set_log_level", var4);
      var3 = null;
      var1.setline(75);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_log_format$10, (PyObject)null);
      var1.setlocal("set_log_format", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("log_ctx", var3);
      var3 = null;
      var1.setline(41);
      PyString var4 = PyString.fromInterned("%(lvl)s:\t%(msg)s");
      var1.getlocal(0).__setattr__((String)"format_str", var4);
      var3 = null;
      var1.setline(42);
      var3 = var1.getglobal("levels_dict").__getitem__(var1.getglobal("DEBUG"));
      var1.getlocal(0).__setattr__("log_level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _log$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("log_level"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(46);
         var3 = var1.getlocal(0).__getattr__("format_str")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("lvl"), var1.getlocal(2), PyString.fromInterned("msg"), var1.getlocal(3)}));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(47);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(49);
            var1.getlocal(0).__getattr__("log_ctx").__getattr__("log").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         } else {
            var1.setline(52);
            var1.getlocal(0).__getattr__("log_ctx").__getattr__("log").__call__(var2, var1.getlocal(3));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug$4(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.getlocal(0).__getattr__("_log").__call__(var2, Py.newInteger(0), var1.getglobal("DEBUG"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      var1.getlocal(0).__getattr__("_log").__call__(var2, Py.newInteger(1), var1.getglobal("INFO"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warn$6(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      var1.getlocal(0).__getattr__("_log").__call__(var2, Py.newInteger(2), var1.getglobal("WARN"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$7(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      var1.getlocal(0).__getattr__("_log").__call__(var2, Py.newInteger(3), var1.getglobal("ERROR"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatal$8(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      var1.getlocal(0).__getattr__("_log").__call__(var2, Py.newInteger(4), var1.getglobal("FATAL"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_log_level$9(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(71);
         PyObject var5 = var1.getglobal("levels_dict").__getitem__(var1.getlocal(1));
         var1.getlocal(0).__setattr__("log_level", var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(73);
            throw Py.makeException(var1.getglobal("BadParameter").__call__(var2, PyString.fromInterned("Invalid log level: '%s'")._mod(var1.getlocal(1))));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_log_format$10(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(78);
         var1.getlocal(0).__getattr__("_log").__call__((ThreadState)var2, var1.getglobal("debug"), (PyObject)PyString.fromInterned("This is a log formatting test"), (PyObject)var1.getglobal("None"));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(80);
            throw Py.makeException(var1.getglobal("BadParameter").__call__(var2, PyString.fromInterned("Bad format string: '%s'")._mod(var1.getlocal(1))));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public modjy_log$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_logger$1 = Py.newCode(0, var2, var1, "modjy_logger", 37, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 39, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "level_str", "msg", "exc"};
      _log$3 = Py.newCode(5, var2, var1, "_log", 44, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "exc"};
      debug$4 = Py.newCode(3, var2, var1, "debug", 54, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "exc"};
      info$5 = Py.newCode(3, var2, var1, "info", 57, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "exc"};
      warn$6 = Py.newCode(3, var2, var1, "warn", 60, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "exc"};
      error$7 = Py.newCode(3, var2, var1, "error", 63, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "exc"};
      fatal$8 = Py.newCode(3, var2, var1, "fatal", 66, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level_string"};
      set_log_level$9 = Py.newCode(2, var2, var1, "set_log_level", 69, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format_string"};
      set_log_format$10 = Py.newCode(2, var2, var1, "set_log_format", 75, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_log$py("modjy/modjy_log$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_log$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_logger$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._log$3(var2, var3);
         case 4:
            return this.debug$4(var2, var3);
         case 5:
            return this.info$5(var2, var3);
         case 6:
            return this.warn$6(var2, var3);
         case 7:
            return this.error$7(var2, var3);
         case 8:
            return this.fatal$8(var2, var3);
         case 9:
            return this.set_log_level$9(var2, var3);
         case 10:
            return this.set_log_format$10(var2, var3);
         default:
            return null;
      }
   }
}

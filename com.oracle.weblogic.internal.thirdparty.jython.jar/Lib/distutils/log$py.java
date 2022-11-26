package distutils;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/log.py")
public class log$py extends PyFunctionTable implements PyRunnable {
   static log$py self;
   static final PyCode f$0;
   static final PyCode Log$1;
   static final PyCode __init__$2;
   static final PyCode _log$3;
   static final PyCode log$4;
   static final PyCode debug$5;
   static final PyCode info$6;
   static final PyCode warn$7;
   static final PyCode error$8;
   static final PyCode fatal$9;
   static final PyCode set_threshold$10;
   static final PyCode set_verbosity$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A simple log mechanism styled after PEP 282."));
      var1.setline(1);
      PyString.fromInterned("A simple log mechanism styled after PEP 282.");
      var1.setline(6);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("DEBUG", var3);
      var3 = null;
      var1.setline(7);
      var3 = Py.newInteger(2);
      var1.setlocal("INFO", var3);
      var3 = null;
      var1.setline(8);
      var3 = Py.newInteger(3);
      var1.setlocal("WARN", var3);
      var3 = null;
      var1.setline(9);
      var3 = Py.newInteger(4);
      var1.setlocal("ERROR", var3);
      var3 = null;
      var1.setline(10);
      var3 = Py.newInteger(5);
      var1.setlocal("FATAL", var3);
      var3 = null;
      var1.setline(12);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(14);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Log", var6, Log$1);
      var1.setlocal("Log", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(51);
      var5 = var1.getname("Log").__call__(var2);
      var1.setlocal("_global_log", var5);
      var3 = null;
      var1.setline(52);
      var5 = var1.getname("_global_log").__getattr__("log");
      var1.setlocal("log", var5);
      var3 = null;
      var1.setline(53);
      var5 = var1.getname("_global_log").__getattr__("debug");
      var1.setlocal("debug", var5);
      var3 = null;
      var1.setline(54);
      var5 = var1.getname("_global_log").__getattr__("info");
      var1.setlocal("info", var5);
      var3 = null;
      var1.setline(55);
      var5 = var1.getname("_global_log").__getattr__("warn");
      var1.setlocal("warn", var5);
      var3 = null;
      var1.setline(56);
      var5 = var1.getname("_global_log").__getattr__("error");
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(57);
      var5 = var1.getname("_global_log").__getattr__("fatal");
      var1.setlocal("fatal", var5);
      var3 = null;
      var1.setline(59);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, set_threshold$10, (PyObject)null);
      var1.setlocal("set_threshold", var7);
      var3 = null;
      var1.setline(65);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_verbosity$11, (PyObject)null);
      var1.setlocal("set_verbosity", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Log$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{var1.getname("WARN")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _log$3, (PyObject)null);
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log$4, (PyObject)null);
      var1.setlocal("log", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$5, (PyObject)null);
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$6, (PyObject)null);
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warn$7, (PyObject)null);
      var1.setlocal("warn", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$8, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatal$9, (PyObject)null);
      var1.setlocal("fatal", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("threshold", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _log$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("DEBUG"), var1.getglobal("INFO"), var1.getglobal("WARN"), var1.getglobal("ERROR"), var1.getglobal("FATAL")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(21);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s wrong log level")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(23);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getlocal(0).__getattr__("threshold"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(24);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(25);
               var3 = var1.getlocal(2)._mod(var1.getlocal(3));
               var1.setlocal(2, var3);
               var3 = null;
            }

            var1.setline(26);
            var3 = var1.getlocal(1);
            var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("WARN"), var1.getglobal("ERROR"), var1.getglobal("FATAL")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(27);
               var3 = var1.getglobal("sys").__getattr__("stderr");
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(29);
               var3 = var1.getglobal("sys").__getattr__("stdout");
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(30);
            var1.getlocal(4).__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getlocal(2)));
            var1.setline(31);
            var1.getlocal(4).__getattr__("flush").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject log$4(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug$5(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getglobal("DEBUG"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$6(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getglobal("INFO"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warn$7(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getglobal("WARN"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$8(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getglobal("ERROR"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatal$9(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      var1.getlocal(0).__getattr__("_log").__call__(var2, var1.getglobal("FATAL"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_threshold$10(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getglobal("_global_log").__getattr__("threshold");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(0);
      var1.getglobal("_global_log").__setattr__("threshold", var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_verbosity$11(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         var1.getglobal("set_threshold").__call__(var2, var1.getglobal("WARN"));
      } else {
         var1.setline(68);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(69);
            var1.getglobal("set_threshold").__call__(var2, var1.getglobal("INFO"));
         } else {
            var1.setline(70);
            var3 = var1.getlocal(0);
            var10000 = var3._ge(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(71);
               var1.getglobal("set_threshold").__call__(var2, var1.getglobal("DEBUG"));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public log$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Log$1 = Py.newCode(0, var2, var1, "Log", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "threshold"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args", "stream"};
      _log$3 = Py.newCode(4, var2, var1, "_log", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args"};
      log$4 = Py.newCode(4, var2, var1, "log", 33, true, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      debug$5 = Py.newCode(3, var2, var1, "debug", 36, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      info$6 = Py.newCode(3, var2, var1, "info", 39, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      warn$7 = Py.newCode(3, var2, var1, "warn", 42, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      error$8 = Py.newCode(3, var2, var1, "error", 45, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      fatal$9 = Py.newCode(3, var2, var1, "fatal", 48, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level", "old"};
      set_threshold$10 = Py.newCode(1, var2, var1, "set_threshold", 59, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      set_verbosity$11 = Py.newCode(1, var2, var1, "set_verbosity", 65, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new log$py("distutils/log$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(log$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Log$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._log$3(var2, var3);
         case 4:
            return this.log$4(var2, var3);
         case 5:
            return this.debug$5(var2, var3);
         case 6:
            return this.info$6(var2, var3);
         case 7:
            return this.warn$7(var2, var3);
         case 8:
            return this.error$8(var2, var3);
         case 9:
            return this.fatal$9(var2, var3);
         case 10:
            return this.set_threshold$10(var2, var3);
         case 11:
            return this.set_verbosity$11(var2, var3);
         default:
            return null;
      }
   }
}

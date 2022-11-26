package unittest;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/signals.py")
public class signals$py extends PyFunctionTable implements PyRunnable {
   static signals$py self;
   static final PyCode f$0;
   static final PyCode _InterruptHandler$1;
   static final PyCode __init__$2;
   static final PyCode default_handler$3;
   static final PyCode __call__$4;
   static final PyCode registerResult$5;
   static final PyCode removeResult$6;
   static final PyCode installHandler$7;
   static final PyCode removeHandler$8;
   static final PyCode inner$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("signal", var1, -1);
      var1.setlocal("signal", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("weakref", var1, -1);
      var1.setlocal("weakref", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"wraps"};
      PyObject[] var6 = imp.importFrom("functools", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("wraps", var4);
      var4 = null;
      var1.setline(6);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_InterruptHandler", var6, _InterruptHandler$1);
      var1.setlocal("_InterruptHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(41);
      var3 = var1.getname("weakref").__getattr__("WeakKeyDictionary").__call__(var2);
      var1.setlocal("_results", var3);
      var3 = null;
      var1.setline(42);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, registerResult$5, (PyObject)null);
      var1.setlocal("registerResult", var7);
      var3 = null;
      var1.setline(45);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, removeResult$6, (PyObject)null);
      var1.setlocal("removeResult", var7);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("None");
      var1.setlocal("_interrupt_handler", var3);
      var3 = null;
      var1.setline(49);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, installHandler$7, (PyObject)null);
      var1.setlocal("installHandler", var7);
      var3 = null;
      var1.setline(57);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, removeHandler$8, (PyObject)null);
      var1.setlocal("removeHandler", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _InterruptHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$4, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("called", var3);
      var3 = null;
      var1.setline(12);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("original_handler", var3);
      var3 = null;
      var1.setline(13);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__nonzero__()) {
         var1.setline(14);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(var1.getglobal("signal").__getattr__("SIG_DFL"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(16);
            var3 = var1.getglobal("signal").__getattr__("default_int_handler");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(17);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getglobal("signal").__getattr__("SIG_IGN"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(23);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected SIGINT signal handler to be signal.SIG_IGN, signal.SIG_DFL, or a callable object")));
            }

            var1.setline(20);
            PyObject[] var4 = Py.EmptyObjects;
            PyFunction var5 = new PyFunction(var1.f_globals, var4, default_handler$3, (PyObject)null);
            var1.setlocal(1, var5);
            var3 = null;
         }
      }

      var1.setline(26);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("default_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default_handler$3(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$4(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(33);
         var1.getlocal(0).__getattr__("default_handler").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(35);
      if (var1.getlocal(0).__getattr__("called").__nonzero__()) {
         var1.setline(36);
         var1.getlocal(0).__getattr__("default_handler").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(37);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("called", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("_results").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(38);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(39);
         var1.getlocal(4).__getattr__("stop").__call__(var2);
      }
   }

   public PyObject registerResult$5(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyInteger var3 = Py.newInteger(1);
      var1.getglobal("_results").__setitem__((PyObject)var1.getlocal(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeResult$6(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getglobal("_results").__getattr__("pop").__call__(var2, var1.getlocal(0), var1.getglobal("None")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject installHandler$7(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = var1.getglobal("_interrupt_handler");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(52);
         var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(53);
         var3 = var1.getglobal("_InterruptHandler").__call__(var2, var1.getlocal(0));
         var1.setglobal("_interrupt_handler", var3);
         var3 = null;
         var1.setline(54);
         var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getglobal("_interrupt_handler"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeHandler$8(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(58);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(59);
         PyObject[] var5 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var5;
         PyCode var10004 = inner$9;
         var5 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
         var3 = var1.getglobal("wraps").__call__(var2, var1.getderef(0)).__call__((ThreadState)var2, (PyObject)var6);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(67);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(70);
         PyObject var4 = var1.getglobal("_interrupt_handler");
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(71);
            var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getglobal("_interrupt_handler").__getattr__("original_handler"));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject inner$9(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(62);
      var1.getglobal("removeHandler").__call__(var2);
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var9;
         try {
            var1.setline(64);
            PyObject var10 = var1.getderef(0);
            PyObject[] var4 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10 = var10._callextra(var4, var5, var1.getlocal(0), var1.getlocal(1));
            var4 = null;
            var9 = var10;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label25;
         }

         var1.setline(66);
         var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getlocal(2));

         try {
            var1.f_lasti = -1;
            return var9;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      Throwable var8 = var10000;
      Py.addTraceback(var8, var1);
      var1.setline(66);
      var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getlocal(2));
      throw (Throwable)var8;
   }

   public signals$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _InterruptHandler$1 = Py.newCode(0, var2, var1, "_InterruptHandler", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "default_handler"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unused_signum", "unused_frame"};
      default_handler$3 = Py.newCode(2, var2, var1, "default_handler", 20, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "signum", "frame", "installed_handler", "result"};
      __call__$4 = Py.newCode(3, var2, var1, "__call__", 28, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"result"};
      registerResult$5 = Py.newCode(1, var2, var1, "registerResult", 42, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"result"};
      removeResult$6 = Py.newCode(1, var2, var1, "removeResult", 45, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"default_handler"};
      installHandler$7 = Py.newCode(0, var2, var1, "installHandler", 49, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"method", "inner"};
      String[] var10001 = var2;
      signals$py var10007 = self;
      var2 = new String[]{"method"};
      removeHandler$8 = Py.newCode(1, var10001, var1, "removeHandler", 57, false, false, var10007, 8, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs", "initial"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"method"};
      inner$9 = Py.newCode(2, var10001, var1, "inner", 59, true, true, var10007, 9, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new signals$py("unittest/signals$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(signals$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._InterruptHandler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.default_handler$3(var2, var3);
         case 4:
            return this.__call__$4(var2, var3);
         case 5:
            return this.registerResult$5(var2, var3);
         case 6:
            return this.removeResult$6(var2, var3);
         case 7:
            return this.installHandler$7(var2, var3);
         case 8:
            return this.removeHandler$8(var2, var3);
         case 9:
            return this.inner$9(var2, var3);
         default:
            return null;
      }
   }
}

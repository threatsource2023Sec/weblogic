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
@MTime(1498849383000L)
@Filename("dummy_thread.py")
public class dummy_thread$py extends PyFunctionTable implements PyRunnable {
   static dummy_thread$py self;
   static final PyCode f$0;
   static final PyCode error$1;
   static final PyCode __init__$2;
   static final PyCode start_new_thread$3;
   static final PyCode exit$4;
   static final PyCode get_ident$5;
   static final PyCode allocate_lock$6;
   static final PyCode stack_size$7;
   static final PyCode LockType$8;
   static final PyCode __init__$9;
   static final PyCode acquire$10;
   static final PyCode __exit__$11;
   static final PyCode release$12;
   static final PyCode locked$13;
   static final PyCode interrupt_main$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Drop-in replacement for the thread module.\n\nMeant to be used as a brain-dead substitute so that threaded code does\nnot need to be rewritten for when the thread module is not present.\n\nSuggested usage is::\n\n    try:\n        import thread\n    except ImportError:\n        import dummy_thread as thread\n\n"));
      var1.setline(13);
      PyString.fromInterned("Drop-in replacement for the thread module.\n\nMeant to be used as a brain-dead substitute so that threaded code does\nnot need to be rewritten for when the thread module is not present.\n\nSuggested usage is::\n\n    try:\n        import thread\n    except ImportError:\n        import dummy_thread as thread\n\n");
      var1.setline(16);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("error"), PyString.fromInterned("start_new_thread"), PyString.fromInterned("exit"), PyString.fromInterned("get_ident"), PyString.fromInterned("allocate_lock"), PyString.fromInterned("interrupt_main"), PyString.fromInterned("LockType")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(19);
      PyObject var5 = imp.importOneAs("traceback", var1, -1);
      var1.setlocal("_traceback", var5);
      var3 = null;
      var1.setline(21);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("error", var6, error$1);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(27);
      var6 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, start_new_thread$3, PyString.fromInterned("Dummy implementation of thread.start_new_thread().\n\n    Compatibility is maintained by making sure that ``args`` is a\n    tuple and ``kwargs`` is a dictionary.  If an exception is raised\n    and it is SystemExit (which can be done by thread.exit()) it is\n    caught and nothing is done; all other exceptions are printed out\n    by using traceback.print_exc().\n\n    If the executed function calls interrupt_main the KeyboardInterrupt will be\n    raised when the function returns.\n\n    "));
      var1.setlocal("start_new_thread", var7);
      var3 = null;
      var1.setline(58);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, exit$4, PyString.fromInterned("Dummy implementation of thread.exit()."));
      var1.setlocal("exit", var7);
      var3 = null;
      var1.setline(62);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_ident$5, PyString.fromInterned("Dummy implementation of thread.get_ident().\n\n    Since this module should only be used when threadmodule is not\n    available, it is safe to assume that the current process is the\n    only thread.  Thus a constant can be safely returned.\n    "));
      var1.setlocal("get_ident", var7);
      var3 = null;
      var1.setline(71);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, allocate_lock$6, PyString.fromInterned("Dummy implementation of thread.allocate_lock()."));
      var1.setlocal("allocate_lock", var7);
      var3 = null;
      var1.setline(75);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, stack_size$7, PyString.fromInterned("Dummy implementation of thread.stack_size()."));
      var1.setlocal("stack_size", var7);
      var3 = null;
      var1.setline(81);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LockType", var6, LockType$8);
      var1.setlocal("LockType", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(134);
      var5 = var1.getname("False");
      var1.setlocal("_interrupt", var5);
      var3 = null;
      var1.setline(136);
      var5 = var1.getname("True");
      var1.setlocal("_main", var5);
      var3 = null;
      var1.setline(138);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, interrupt_main$14, PyString.fromInterned("Set _interrupt flag to True to have start_new_thread raise\n    KeyboardInterrupt upon exiting."));
      var1.setlocal("interrupt_main", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dummy implementation of thread.error."));
      var1.setline(22);
      PyString.fromInterned("Dummy implementation of thread.error.");
      var1.setline(24);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("args", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_new_thread$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Dummy implementation of thread.start_new_thread().\n\n    Compatibility is maintained by making sure that ``args`` is a\n    tuple and ``kwargs`` is a dictionary.  If an exception is raised\n    and it is SystemExit (which can be done by thread.exit()) it is\n    caught and nothing is done; all other exceptions are printed out\n    by using traceback.print_exc().\n\n    If the executed function calls interrupt_main the KeyboardInterrupt will be\n    raised when the function returns.\n\n    ");
      var1.setline(40);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ne(var1.getglobal("type").__call__(var2, var1.getglobal("tuple").__call__(var2)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(41);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("2nd arg must be a tuple")));
      } else {
         var1.setline(42);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10000 = var3._ne(var1.getglobal("type").__call__(var2, var1.getglobal("dict").__call__(var2)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(43);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("3rd arg must be a dict")));
         } else {
            var1.setline(45);
            var3 = var1.getglobal("False");
            var1.setglobal("_main", var3);
            var3 = null;

            try {
               var1.setline(47);
               var10000 = var1.getlocal(0);
               PyObject[] var7 = Py.EmptyObjects;
               String[] var4 = new String[0];
               var10000._callextra(var7, var4, var1.getlocal(1), var1.getlocal(2));
               var3 = null;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getglobal("SystemExit"))) {
                  var1.setline(49);
               } else {
                  var1.setline(51);
                  var1.getglobal("_traceback").__getattr__("print_exc").__call__(var2);
               }
            }

            var1.setline(52);
            var3 = var1.getglobal("True");
            var1.setglobal("_main", var3);
            var3 = null;
            var1.setline(54);
            if (var1.getglobal("_interrupt").__nonzero__()) {
               var1.setline(55);
               var3 = var1.getglobal("False");
               var1.setglobal("_interrupt", var3);
               var3 = null;
               var1.setline(56);
               throw Py.makeException(var1.getglobal("KeyboardInterrupt"));
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject exit$4(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Dummy implementation of thread.exit().");
      var1.setline(60);
      throw Py.makeException(var1.getglobal("SystemExit"));
   }

   public PyObject get_ident$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Dummy implementation of thread.get_ident().\n\n    Since this module should only be used when threadmodule is not\n    available, it is safe to assume that the current process is the\n    only thread.  Thus a constant can be safely returned.\n    ");
      var1.setline(69);
      PyInteger var3 = Py.newInteger(-1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject allocate_lock$6(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Dummy implementation of thread.allocate_lock().");
      var1.setline(73);
      PyObject var3 = var1.getglobal("LockType").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stack_size$7(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Dummy implementation of thread.stack_size().");
      var1.setline(77);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setting thread stack size not supported")));
      } else {
         var1.setline(79);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject LockType$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class implementing dummy implementation of thread.LockType.\n\n    Compatibility is maintained by maintaining self.locked_status\n    which is a boolean that stores the state of the lock.  Pickling of\n    the lock, though, should not be done since if the thread module is\n    then used with an unpickled ``lock()`` from here problems could\n    occur from this class not having atomic methods.\n\n    "));
      var1.setline(90);
      PyString.fromInterned("Class implementing dummy implementation of thread.LockType.\n\n    Compatibility is maintained by maintaining self.locked_status\n    which is a boolean that stores the state of the lock.  Pickling of\n    the lock, though, should not be done since if the thread module is\n    then used with an unpickled ``lock()`` from here problems could\n    occur from this class not having atomic methods.\n\n    ");
      var1.setline(92);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(95);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, acquire$10, PyString.fromInterned("Dummy implementation of acquire().\n\n        For blocking calls, self.locked_status is automatically set to\n        True and returned appropriately based on value of\n        ``waitflag``.  If it is non-blocking, then the value is\n        actually checked and not set if it is already acquired.  This\n        is all done so that threading.Condition's assert statements\n        aren't triggered and throw a little fit.\n\n        "));
      var1.setlocal("acquire", var4);
      var3 = null;
      var1.setline(116);
      PyObject var5 = var1.getname("acquire");
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$11, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      var1.setline(121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, release$12, PyString.fromInterned("Release the dummy lock."));
      var1.setlocal("release", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, locked$13, (PyObject)null);
      var1.setlocal("locked", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("locked_status", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject acquire$10(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Dummy implementation of acquire().\n\n        For blocking calls, self.locked_status is automatically set to\n        True and returned appropriately based on value of\n        ``waitflag``.  If it is non-blocking, then the value is\n        actually checked and not set if it is already acquired.  This\n        is all done so that threading.Condition's assert statements\n        aren't triggered and throw a little fit.\n\n        ");
      var1.setline(106);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      if (var10000.__nonzero__()) {
         var1.setline(107);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("locked_status", var3);
         var3 = null;
         var1.setline(108);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(110);
         if (var1.getlocal(0).__getattr__("locked_status").__not__().__nonzero__()) {
            var1.setline(111);
            PyObject var4 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("locked_status", var4);
            var4 = null;
            var1.setline(112);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(114);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __exit__$11(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject release$12(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Release the dummy lock.");
      var1.setline(125);
      if (var1.getlocal(0).__getattr__("locked_status").__not__().__nonzero__()) {
         var1.setline(126);
         throw Py.makeException(var1.getglobal("error"));
      } else {
         var1.setline(127);
         PyObject var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("locked_status", var3);
         var3 = null;
         var1.setline(128);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject locked$13(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(0).__getattr__("locked_status");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject interrupt_main$14(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyString.fromInterned("Set _interrupt flag to True to have start_new_thread raise\n    KeyboardInterrupt upon exiting.");
      var1.setline(141);
      if (var1.getglobal("_main").__nonzero__()) {
         var1.setline(142);
         throw Py.makeException(var1.getglobal("KeyboardInterrupt"));
      } else {
         var1.setline(145);
         PyObject var3 = var1.getglobal("True");
         var1.setglobal("_interrupt", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public dummy_thread$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error$1 = Py.newCode(0, var2, var1, "error", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 24, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"function", "args", "kwargs"};
      start_new_thread$3 = Py.newCode(3, var2, var1, "start_new_thread", 27, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      exit$4 = Py.newCode(0, var2, var1, "exit", 58, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_ident$5 = Py.newCode(0, var2, var1, "get_ident", 62, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      allocate_lock$6 = Py.newCode(0, var2, var1, "allocate_lock", 71, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"size"};
      stack_size$7 = Py.newCode(1, var2, var1, "stack_size", 75, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LockType$8 = Py.newCode(0, var2, var1, "LockType", 81, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$9 = Py.newCode(1, var2, var1, "__init__", 92, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "waitflag"};
      acquire$10 = Py.newCode(2, var2, var1, "acquire", 95, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typ", "val", "tb"};
      __exit__$11 = Py.newCode(4, var2, var1, "__exit__", 118, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      release$12 = Py.newCode(1, var2, var1, "release", 121, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      locked$13 = Py.newCode(1, var2, var1, "locked", 130, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      interrupt_main$14 = Py.newCode(0, var2, var1, "interrupt_main", 138, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dummy_thread$py("dummy_thread$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dummy_thread$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.error$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.start_new_thread$3(var2, var3);
         case 4:
            return this.exit$4(var2, var3);
         case 5:
            return this.get_ident$5(var2, var3);
         case 6:
            return this.allocate_lock$6(var2, var3);
         case 7:
            return this.stack_size$7(var2, var3);
         case 8:
            return this.LockType$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.acquire$10(var2, var3);
         case 11:
            return this.__exit__$11(var2, var3);
         case 12:
            return this.release$12(var2, var3);
         case 13:
            return this.locked$13(var2, var3);
         case 14:
            return this.interrupt_main$14(var2, var3);
         default:
            return null;
      }
   }
}

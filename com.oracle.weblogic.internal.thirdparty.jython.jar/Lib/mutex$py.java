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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("mutex.py")
public class mutex$py extends PyFunctionTable implements PyRunnable {
   static mutex$py self;
   static final PyCode f$0;
   static final PyCode mutex$1;
   static final PyCode __init__$2;
   static final PyCode test$3;
   static final PyCode testandset$4;
   static final PyCode lock$5;
   static final PyCode unlock$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Mutual exclusion -- for use with module sched\n\nA mutex has two pieces of state -- a 'locked' bit and a queue.\nWhen the mutex is not locked, the queue is empty.\nOtherwise, the queue contains 0 or more (function, argument) pairs\nrepresenting functions (or methods) waiting to acquire the lock.\nWhen the mutex is unlocked while the queue is not empty,\nthe first queue entry is removed and its function(argument) pair called,\nimplying it now has the lock.\n\nOf course, no multi-threading is implied -- hence the funny interface\nfor lock, where a function is called once the lock is aquired.\n"));
      var1.setline(13);
      PyString.fromInterned("Mutual exclusion -- for use with module sched\n\nA mutex has two pieces of state -- a 'locked' bit and a queue.\nWhen the mutex is not locked, the queue is empty.\nOtherwise, the queue contains 0 or more (function, argument) pairs\nrepresenting functions (or methods) waiting to acquire the lock.\nWhen the mutex is unlocked while the queue is not empty,\nthe first queue entry is removed and its function(argument) pair called,\nimplying it now has the lock.\n\nOf course, no multi-threading is implied -- hence the funny interface\nfor lock, where a function is called once the lock is aquired.\n");
      var1.setline(14);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(15);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the mutex module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var6 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(16);
      var1.dellocal("warnpy3k");
      var1.setline(18);
      var3 = new String[]{"deque"};
      var5 = imp.importFrom("collections", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("deque", var4);
      var4 = null;
      var1.setline(20);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("mutex", var5, mutex$1);
      var1.setlocal("mutex", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mutex$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create a new mutex -- initially unlocked."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$3, PyString.fromInterned("Test the locked bit of the mutex."));
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testandset$4, PyString.fromInterned("Atomic test-and-set -- grab the lock if it is not set,\n        return True if it succeeded."));
      var1.setlocal("testandset", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lock$5, PyString.fromInterned("Lock a mutex, call the function with supplied argument\n        when it is acquired.  If the mutex is already locked, place\n        function and argument in the queue."));
      var1.setlocal("lock", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unlock$6, PyString.fromInterned("Unlock a mutex.  If the queue is not empty, call the next\n        function with its argument."));
      var1.setlocal("unlock", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyString.fromInterned("Create a new mutex -- initially unlocked.");
      var1.setline(23);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("locked", var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getglobal("deque").__call__(var2);
      var1.getlocal(0).__setattr__("queue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$3(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Test the locked bit of the mutex.");
      var1.setline(28);
      PyObject var3 = var1.getlocal(0).__getattr__("locked");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testandset$4(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyString.fromInterned("Atomic test-and-set -- grab the lock if it is not set,\n        return True if it succeeded.");
      var1.setline(33);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("locked").__not__().__nonzero__()) {
         var1.setline(34);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("locked", var3);
         var3 = null;
         var1.setline(35);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(37);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lock$5(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyString.fromInterned("Lock a mutex, call the function with supplied argument\n        when it is acquired.  If the mutex is already locked, place\n        function and argument in the queue.");
      var1.setline(43);
      if (var1.getlocal(0).__getattr__("testandset").__call__(var2).__nonzero__()) {
         var1.setline(44);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      } else {
         var1.setline(46);
         var1.getlocal(0).__getattr__("queue").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlock$6(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyString.fromInterned("Unlock a mutex.  If the queue is not empty, call the next\n        function with its argument.");
      var1.setline(51);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("queue").__nonzero__()) {
         var1.setline(52);
         var3 = var1.getlocal(0).__getattr__("queue").__getattr__("popleft").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(53);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      } else {
         var1.setline(55);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("locked", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public mutex$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      mutex$1 = Py.newCode(0, var2, var1, "mutex", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$3 = Py.newCode(1, var2, var1, "test", 26, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testandset$4 = Py.newCode(1, var2, var1, "testandset", 30, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "function", "argument"};
      lock$5 = Py.newCode(3, var2, var1, "lock", 39, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "function", "argument"};
      unlock$6 = Py.newCode(1, var2, var1, "unlock", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mutex$py("mutex$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mutex$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.mutex$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.test$3(var2, var3);
         case 4:
            return this.testandset$4(var2, var3);
         case 5:
            return this.lock$5(var2, var3);
         case 6:
            return this.unlock$6(var2, var3);
         default:
            return null;
      }
   }
}

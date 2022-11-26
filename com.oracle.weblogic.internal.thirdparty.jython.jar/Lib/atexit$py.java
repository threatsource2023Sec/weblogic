import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@MTime(1498849383000L)
@Filename("atexit.py")
public class atexit$py extends PyFunctionTable implements PyRunnable {
   static atexit$py self;
   static final PyCode f$0;
   static final PyCode _run_exitfuncs$1;
   static final PyCode register$2;
   static final PyCode x1$3;
   static final PyCode x2$4;
   static final PyCode x3$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\natexit.py - allow programmer to define multiple exit functions to be executed\nupon normal program termination.\n\nOne public function, register, is defined.\n"));
      var1.setline(6);
      PyString.fromInterned("\natexit.py - allow programmer to define multiple exit functions to be executed\nupon normal program termination.\n\nOne public function, register, is defined.\n");
      var1.setline(8);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("register")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(10);
      PyObject var4 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var4);
      var3 = null;
      var1.setline(12);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("_exithandlers", var3);
      var3 = null;
      var1.setline(13);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, _run_exitfuncs$1, PyString.fromInterned("run any registered exit functions\n\n    _exithandlers is traversed in reverse order so functions are executed\n    last in, first out.\n    "));
      var1.setlocal("_run_exitfuncs", var6);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, register$2, PyString.fromInterned("register a function to be executed upon normal program termination\n\n    func - function to be called at exit\n    targs - optional arguments to pass to func\n    kargs - optional keyword arguments to pass to func\n\n    func is returned to facilitate usage as a decorator.\n    "));
      var1.setlocal("register", var6);
      var3 = null;
      var1.setline(49);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("sys"), (PyObject)PyString.fromInterned("exitfunc")).__nonzero__()) {
         var1.setline(51);
         var1.getname("register").__call__(var2, var1.getname("sys").__getattr__("exitfunc"));
      }

      var1.setline(52);
      var4 = var1.getname("_run_exitfuncs");
      var1.getname("sys").__setattr__("exitfunc", var4);
      var3 = null;
      var1.setline(54);
      var4 = var1.getname("__name__");
      PyObject var10000 = var4._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, x1$3, (PyObject)null);
         var1.setlocal("x1", var6);
         var3 = null;
         var1.setline(57);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, x2$4, (PyObject)null);
         var1.setlocal("x2", var6);
         var3 = null;
         var1.setline(59);
         var5 = new PyObject[]{var1.getname("None")};
         var6 = new PyFunction(var1.f_globals, var5, x3$5, (PyObject)null);
         var1.setlocal("x3", var6);
         var3 = null;
         var1.setline(62);
         var1.getname("register").__call__(var2, var1.getname("x1"));
         var1.setline(63);
         var1.getname("register").__call__((ThreadState)var2, (PyObject)var1.getname("x2"), (PyObject)Py.newInteger(12));
         var1.setline(64);
         var1.getname("register").__call__((ThreadState)var2, var1.getname("x3"), (PyObject)Py.newInteger(5), (PyObject)PyString.fromInterned("bar"));
         var1.setline(65);
         var1.getname("register").__call__((ThreadState)var2, (PyObject)var1.getname("x3"), (PyObject)PyString.fromInterned("no kwd args"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _run_exitfuncs$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyString.fromInterned("run any registered exit functions\n\n    _exithandlers is traversed in reverse order so functions are executed\n    last in, first out.\n    ");
      var1.setline(20);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(0, var3);
      var3 = null;

      while(true) {
         var1.setline(21);
         PyObject var10000;
         if (!var1.getglobal("_exithandlers").__nonzero__()) {
            var1.setline(33);
            var3 = var1.getlocal(0);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(34);
               throw Py.makeException(var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(0).__getitem__(Py.newInteger(2)));
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(22);
         var3 = var1.getglobal("_exithandlers").__getattr__("pop").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;

         try {
            var1.setline(24);
            var10000 = var1.getlocal(1);
            PyObject[] var9 = Py.EmptyObjects;
            String[] var10 = new String[0];
            var10000._callextra(var9, var10, var1.getlocal(2), var1.getlocal(3));
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            PyObject var8;
            if (var7.match(var1.getglobal("SystemExit"))) {
               var1.setline(26);
               var8 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
               var1.setlocal(0, var8);
               var4 = null;
            } else {
               var1.setline(28);
               var8 = imp.importOne("traceback", var1, -1);
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(29);
               var8 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var8, PyString.fromInterned("Error in atexit._run_exitfuncs:"));
               var1.setline(30);
               var1.getlocal(4).__getattr__("print_exc").__call__(var2);
               var1.setline(31);
               var8 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
               var1.setlocal(0, var8);
               var4 = null;
            }
         }
      }
   }

   public PyObject register$2(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("register a function to be executed upon normal program termination\n\n    func - function to be called at exit\n    targs - optional arguments to pass to func\n    kargs - optional keyword arguments to pass to func\n\n    func is returned to facilitate usage as a decorator.\n    ");
      var1.setline(46);
      var1.getglobal("_exithandlers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(47);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject x1$3(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      Py.println(PyString.fromInterned("running x1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject x2$4(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      Py.println(PyString.fromInterned("running x2(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject x3$5(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      Py.println(PyString.fromInterned("running x3(%r, kwd=%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public atexit$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"exc_info", "func", "targs", "kargs", "traceback"};
      _run_exitfuncs$1 = Py.newCode(0, var2, var1, "_run_exitfuncs", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "targs", "kargs"};
      register$2 = Py.newCode(3, var2, var1, "register", 37, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      x1$3 = Py.newCode(0, var2, var1, "x1", 55, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n"};
      x2$4 = Py.newCode(1, var2, var1, "x2", 57, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "kwd"};
      x3$5 = Py.newCode(2, var2, var1, "x3", 59, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new atexit$py("atexit$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(atexit$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._run_exitfuncs$1(var2, var3);
         case 2:
            return this.register$2(var2, var3);
         case 3:
            return this.x1$3(var2, var3);
         case 4:
            return this.x2$4(var2, var3);
         case 5:
            return this.x3$5(var2, var3);
         default:
            return null;
      }
   }
}

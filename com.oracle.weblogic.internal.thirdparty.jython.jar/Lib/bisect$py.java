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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("bisect.py")
public class bisect$py extends PyFunctionTable implements PyRunnable {
   static bisect$py self;
   static final PyCode f$0;
   static final PyCode insort_right$1;
   static final PyCode bisect_right$2;
   static final PyCode insort_left$3;
   static final PyCode bisect_left$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Bisection algorithms."));
      var1.setline(1);
      PyString.fromInterned("Bisection algorithms.");
      var1.setline(3);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, insort_right$1, PyString.fromInterned("Insert item x in list a, and keep it sorted assuming a is sorted.\n\n    If x is already in a, insert it to the right of the rightmost x.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    "));
      var1.setlocal("insort_right", var5);
      var3 = null;
      var1.setline(22);
      PyObject var6 = var1.getname("insort_right");
      var1.setlocal("insort", var6);
      var3 = null;
      var1.setline(24);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, bisect_right$2, PyString.fromInterned("Return the index where to insert item x in list a, assuming a is sorted.\n\n    The return value i is such that all e in a[:i] have e <= x, and all e in\n    a[i:] have e > x.  So if x already appears in the list, a.insert(x) will\n    insert just after the rightmost x already there.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    "));
      var1.setlocal("bisect_right", var5);
      var3 = null;
      var1.setline(45);
      var6 = var1.getname("bisect_right");
      var1.setlocal("bisect", var6);
      var3 = null;
      var1.setline(47);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, insort_left$3, PyString.fromInterned("Insert item x in list a, and keep it sorted assuming a is sorted.\n\n    If x is already in a, insert it to the left of the leftmost x.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    "));
      var1.setlocal("insort_left", var5);
      var3 = null;
      var1.setline(67);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, bisect_left$4, PyString.fromInterned("Return the index where to insert item x in list a, assuming a is sorted.\n\n    The return value i is such that all e in a[:i] have e < x, and all e in\n    a[i:] have e >= x.  So if x already appears in the list, a.insert(x) will\n    insert just before the leftmost x already there.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    "));
      var1.setlocal("bisect_left", var5);
      var3 = null;

      try {
         var1.setline(90);
         imp.importAll("_bisect", var1, -1);
      } catch (Throwable var4) {
         PyException var7 = Py.setException(var4, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(92);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insort_right$1(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyString.fromInterned("Insert item x in list a, and keep it sorted assuming a is sorted.\n\n    If x is already in a, insert it to the right of the rightmost x.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    ");
      var1.setline(12);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(13);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lo must be non-negative")));
      } else {
         var1.setline(14);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(15);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var3);
            var3 = null;
         }

         while(true) {
            var1.setline(16);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(20);
               var1.getlocal(0).__getattr__("insert").__call__(var2, var1.getlocal(2), var1.getlocal(1));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(17);
            var3 = var1.getlocal(2)._add(var1.getlocal(3))._floordiv(Py.newInteger(2));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(18);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(var1.getlocal(0).__getitem__(var1.getlocal(4)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(18);
               var3 = var1.getlocal(4);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(19);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject bisect_right$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Return the index where to insert item x in list a, assuming a is sorted.\n\n    The return value i is such that all e in a[:i] have e <= x, and all e in\n    a[i:] have e > x.  So if x already appears in the list, a.insert(x) will\n    insert just after the rightmost x already there.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    ");
      var1.setline(35);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(36);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lo must be non-negative")));
      } else {
         var1.setline(37);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(38);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var3);
            var3 = null;
         }

         while(true) {
            var1.setline(39);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(43);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(40);
            var3 = var1.getlocal(2)._add(var1.getlocal(3))._floordiv(Py.newInteger(2));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(41);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(var1.getlocal(0).__getitem__(var1.getlocal(4)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(41);
               var3 = var1.getlocal(4);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(42);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject insort_left$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Insert item x in list a, and keep it sorted assuming a is sorted.\n\n    If x is already in a, insert it to the left of the leftmost x.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    ");
      var1.setline(56);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(57);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lo must be non-negative")));
      } else {
         var1.setline(58);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(59);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var3);
            var3 = null;
         }

         while(true) {
            var1.setline(60);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(64);
               var1.getlocal(0).__getattr__("insert").__call__(var2, var1.getlocal(2), var1.getlocal(1));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(61);
            var3 = var1.getlocal(2)._add(var1.getlocal(3))._floordiv(Py.newInteger(2));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(62);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
            var10000 = var3._lt(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(62);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(63);
               var3 = var1.getlocal(4);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject bisect_left$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Return the index where to insert item x in list a, assuming a is sorted.\n\n    The return value i is such that all e in a[:i] have e < x, and all e in\n    a[i:] have e >= x.  So if x already appears in the list, a.insert(x) will\n    insert just before the leftmost x already there.\n\n    Optional args lo (default 0) and hi (default len(a)) bound the\n    slice of a to be searched.\n    ");
      var1.setline(78);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(79);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lo must be non-negative")));
      } else {
         var1.setline(80);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(81);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var3);
            var3 = null;
         }

         while(true) {
            var1.setline(82);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(86);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(83);
            var3 = var1.getlocal(2)._add(var1.getlocal(3))._floordiv(Py.newInteger(2));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(84);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
            var10000 = var3._lt(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(84);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(85);
               var3 = var1.getlocal(4);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }
   }

   public bisect$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"a", "x", "lo", "hi", "mid"};
      insort_right$1 = Py.newCode(4, var2, var1, "insort_right", 3, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "x", "lo", "hi", "mid"};
      bisect_right$2 = Py.newCode(4, var2, var1, "bisect_right", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "x", "lo", "hi", "mid"};
      insort_left$3 = Py.newCode(4, var2, var1, "insort_left", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "x", "lo", "hi", "mid"};
      bisect_left$4 = Py.newCode(4, var2, var1, "bisect_left", 67, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bisect$py("bisect$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bisect$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.insort_right$1(var2, var3);
         case 2:
            return this.bisect_right$2(var2, var3);
         case 3:
            return this.insort_left$3(var2, var3);
         case 4:
            return this.bisect_left$4(var2, var3);
         default:
            return null;
      }
   }
}

package distutils;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/__init__.py")
public class tests$py extends PyFunctionTable implements PyRunnable {
   static tests$py self;
   static final PyCode f$0;
   static final PyCode test_suite$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Test suite for distutils.\n\nThis test suite consists of a collection of test modules in the\ndistutils.tests package.  Each test module has a name starting with\n'test' and contains a function test_suite().  The function is expected\nto return an initialized unittest.TestSuite instance.\n\nTests for the command classes in the distutils.command package are\nincluded in distutils.tests as well, instead of using a separate\ndistutils.command.tests package, since command identification is done\nby import rather than matching pre-defined names.\n\n"));
      var1.setline(13);
      PyString.fromInterned("Test suite for distutils.\n\nThis test suite consists of a collection of test modules in the\ndistutils.tests package.  Each test module has a name starting with\n'test' and contains a function test_suite().  The function is expected\nto return an initialized unittest.TestSuite instance.\n\nTests for the command classes in the distutils.command package are\nincluded in distutils.tests as well, instead of using a separate\ndistutils.command.tests package, since command identification is done\nby import rather than matching pre-defined names.\n\n");
      var1.setline(15);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(18);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(21);
      PyObject var10000 = var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("__file__"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getname("os").__getattr__("curdir");
      }

      var3 = var10000;
      var1.setlocal("here", var3);
      var3 = null;
      var1.setline(24);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$1, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(35);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(36);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$1(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("here")).__iter__();

      while(true) {
         var1.setline(26);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(32);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(27);
         PyObject var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(28);
            PyObject var5 = PyString.fromInterned("distutils.tests.")._add(var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(29);
            var1.getglobal("__import__").__call__(var2, var1.getlocal(2));
            var1.setline(30);
            var5 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(31);
            var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getlocal(3).__getattr__("test_suite").__call__(var2));
         }
      }
   }

   public tests$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"suite", "fn", "modname", "module"};
      test_suite$1 = Py.newCode(0, var2, var1, "test_suite", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tests$py("distutils/tests$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tests$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test_suite$1(var2, var3);
         default:
            return null;
      }
   }
}

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("keyword.py")
public class keyword$py extends PyFunctionTable implements PyRunnable {
   static keyword$py self;
   static final PyCode f$0;
   static final PyCode main$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Keywords (from \"graminit.c\")\n\nThis file is automatically generated; please don't muck it up!\n\nTo update the symbols in this file, 'cd' to the top directory of\nthe python source tree after building the interpreter and run:\n\n    ./python Lib/keyword.py\n"));
      var1.setline(11);
      PyString.fromInterned("Keywords (from \"graminit.c\")\n\nThis file is automatically generated; please don't muck it up!\n\nTo update the symbols in this file, 'cd' to the top directory of\nthe python source tree after building the interpreter and run:\n\n    ./python Lib/keyword.py\n");
      var1.setline(13);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("iskeyword"), PyString.fromInterned("kwlist")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(15);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("and"), PyString.fromInterned("as"), PyString.fromInterned("assert"), PyString.fromInterned("break"), PyString.fromInterned("class"), PyString.fromInterned("continue"), PyString.fromInterned("def"), PyString.fromInterned("del"), PyString.fromInterned("elif"), PyString.fromInterned("else"), PyString.fromInterned("except"), PyString.fromInterned("exec"), PyString.fromInterned("finally"), PyString.fromInterned("for"), PyString.fromInterned("from"), PyString.fromInterned("global"), PyString.fromInterned("if"), PyString.fromInterned("import"), PyString.fromInterned("in"), PyString.fromInterned("is"), PyString.fromInterned("lambda"), PyString.fromInterned("not"), PyString.fromInterned("or"), PyString.fromInterned("pass"), PyString.fromInterned("print"), PyString.fromInterned("raise"), PyString.fromInterned("return"), PyString.fromInterned("try"), PyString.fromInterned("while"), PyString.fromInterned("with"), PyString.fromInterned("yield")});
      var1.setlocal("kwlist", var3);
      var3 = null;
      var1.setline(51);
      PyObject var4 = var1.getname("frozenset").__call__(var2, var1.getname("kwlist")).__getattr__("__contains__");
      var1.setlocal("iskeyword", var4);
      var3 = null;
      var1.setline(53);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, main$1, (PyObject)null);
      var1.setlocal("main", var6);
      var3 = null;
      var1.setline(92);
      var4 = var1.getname("__name__");
      PyObject var10000 = var4._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(93);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$1(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(57);
      Object var10000 = var1.getlocal(2);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("Python/graminit.c");
      }

      Object var7 = var10000;
      var1.setlocal(3, (PyObject)var7);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var12 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var12.__nonzero__()) {
         var1.setline(58);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(59);
         PyString var9 = PyString.fromInterned("Lib/keyword.py");
         var1.setlocal(4, var9);
         var3 = null;
      }

      var1.setline(62);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"([^\"]+)\""));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(64);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(7, var10);
      var3 = null;
      var1.setline(65);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(65);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(70);
            var1.getlocal(5).__getattr__("close").__call__(var2);
            var1.setline(71);
            var1.getlocal(7).__getattr__("sort").__call__(var2);
            var1.setline(74);
            var3 = var1.getglobal("open").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(75);
            var3 = var1.getlocal(5).__getattr__("readlines").__call__(var2);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(76);
            var1.getlocal(5).__getattr__("close").__call__(var2);

            try {
               var1.setline(80);
               var3 = var1.getlocal(10).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#--start keywords--\n"))._add(Py.newInteger(1));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(81);
               var3 = var1.getlocal(10).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#--end keywords--\n"));
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(82);
               var3 = var1.getlocal(7);
               var1.getlocal(10).__setslice__(var1.getlocal(11), var1.getlocal(12), (PyObject)null, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var11 = Py.setException(var6, var1);
               if (!var11.match(var1.getglobal("ValueError"))) {
                  throw var11;
               }

               var1.setline(84);
               var1.getlocal(0).__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("target does not contain format markers\n"));
               var1.setline(85);
               var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var1.setline(88);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(89);
            var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(10)));
            var1.setline(90);
            var1.getlocal(5).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(8, var4);
         var1.setline(66);
         PyString var5 = PyString.fromInterned("{1, \"");
         var12 = var5._in(var1.getlocal(8));
         var5 = null;
         if (var12.__nonzero__()) {
            var1.setline(67);
            PyObject var8 = var1.getlocal(6).__getattr__("search").__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var8);
            var5 = null;
            var1.setline(68);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(69);
               var1.getlocal(7).__getattr__("append").__call__(var2, PyString.fromInterned("        '")._add(var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)))._add(PyString.fromInterned("',\n")));
            }
         }
      }
   }

   public keyword$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"sys", "re", "args", "iptfile", "optfile", "fp", "strprog", "lines", "line", "match", "format", "start", "end"};
      main$1 = Py.newCode(0, var2, var1, "main", 53, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new keyword$py("keyword$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(keyword$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.main$1(var2, var3);
         default:
            return null;
      }
   }
}

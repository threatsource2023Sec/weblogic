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
@Filename("importlib/__init__.py")
public class importlib$py extends PyFunctionTable implements PyRunnable {
   static importlib$py self;
   static final PyCode f$0;
   static final PyCode _resolve_name$1;
   static final PyCode import_module$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Backport of importlib.import_module from 3.x."));
      var1.setline(1);
      PyString.fromInterned("Backport of importlib.import_module from 3.x.");
      var1.setline(4);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(6);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _resolve_name$1, PyString.fromInterned("Return the absolute name of the module to be imported."));
      var1.setlocal("_resolve_name", var5);
      var3 = null;
      var1.setline(20);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, import_module$2, PyString.fromInterned("Import a module.\n\n    The 'package' argument is required when performing a relative import. It\n    specifies the package to use as the anchor point from which to resolve the\n    relative import to an absolute import.\n\n    "));
      var1.setlocal("import_module", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _resolve_name$1(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyString.fromInterned("Return the absolute name of the module to be imported.");
      var1.setline(8);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rindex")).__not__().__nonzero__()) {
         var1.setline(9);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'package' not set to a string")));
      } else {
         var1.setline(10);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(11);
         var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(-1)).__iter__();

         while(true) {
            var1.setline(11);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(17);
               var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getlocal(0)}));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var4);

            PyException var5;
            try {
               var1.setline(13);
               PyObject var7 = var1.getlocal(1).__getattr__("rindex").__call__((ThreadState)var2, PyString.fromInterned("."), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(3));
               var1.setlocal(3, var7);
               var5 = null;
            } catch (Throwable var6) {
               var5 = Py.setException(var6, var1);
               if (var5.match(var1.getglobal("ValueError"))) {
                  var1.setline(15);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempted relative import beyond top-level package")));
               }

               throw var5;
            }
         }
      }
   }

   public PyObject import_module$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Import a module.\n\n    The 'package' argument is required when performing a relative import. It\n    specifies the package to use as the anchor point from which to resolve the\n    relative import to an absolute import.\n\n    ");
      var1.setline(28);
      PyObject var6;
      if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
         var1.setline(29);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(30);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("relative imports require the 'package' argument")));
         }

         var1.setline(31);
         PyInteger var3 = Py.newInteger(0);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(32);
         var6 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(32);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(33);
            PyObject var5 = var1.getlocal(3);
            PyObject var10000 = var5._ne(PyString.fromInterned("."));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(35);
            var5 = var1.getlocal(2);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(2, var5);
         }

         var1.setline(36);
         var6 = var1.getglobal("_resolve_name").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null), var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(0, var6);
         var3 = null;
      }

      var1.setline(37);
      var1.getglobal("__import__").__call__(var2, var1.getlocal(0));
      var1.setline(38);
      var6 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(0));
      var1.f_lasti = -1;
      return var6;
   }

   public importlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "package", "level", "dot", "x"};
      _resolve_name$1 = Py.newCode(3, var2, var1, "_resolve_name", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "package", "level", "character"};
      import_module$2 = Py.newCode(2, var2, var1, "import_module", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new importlib$py("importlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(importlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._resolve_name$1(var2, var3);
         case 2:
            return this.import_module$2(var2, var3);
         default:
            return null;
      }
   }
}

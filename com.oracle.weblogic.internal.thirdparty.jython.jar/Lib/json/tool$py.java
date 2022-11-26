package json;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tool.py")
public class tool$py extends PyFunctionTable implements PyRunnable {
   static tool$py self;
   static final PyCode f$0;
   static final PyCode main$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Command-line tool to validate and pretty-print JSON\n\nUsage::\n\n    $ echo '{\"json\":\"obj\"}' | python -m json.tool\n    {\n        \"json\": \"obj\"\n    }\n    $ echo '{ 1.2:3.4}' | python -m json.tool\n    Expecting property name enclosed in double quotes: line 1 column 3 (char 2)\n\n"));
      var1.setline(12);
      PyString.fromInterned("Command-line tool to validate and pretty-print JSON\n\nUsage::\n\n    $ echo '{\"json\":\"obj\"}' | python -m json.tool\n    {\n        \"json\": \"obj\"\n    }\n    $ echo '{ 1.2:3.4}' | python -m json.tool\n    Expecting property name enclosed in double quotes: line 1 column 3 (char 2)\n\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("json", var1, -1);
      var1.setlocal("json", var3);
      var3 = null;
      var1.setline(16);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, main$1, (PyObject)null);
      var1.setlocal("main", var5);
      var3 = null;
      var1.setline(39);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$1(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(17);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("sys").__getattr__("argv"));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(18);
         var3 = var1.getglobal("sys").__getattr__("stdin");
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(19);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(20);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("sys").__getattr__("argv"));
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(21);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(22);
            var3 = var1.getglobal("sys").__getattr__("stdout");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(23);
            var3 = var1.getglobal("len").__call__(var2, var1.getglobal("sys").__getattr__("argv"));
            var10000 = var3._eq(Py.newInteger(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(27);
               throw Py.makeException(var1.getglobal("SystemExit").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0))._add(PyString.fromInterned(" [infile [outfile]]"))));
            }

            var1.setline(24);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(25);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(2)), (PyObject)PyString.fromInterned("wb"));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      ContextManager var12;
      PyObject var4 = (var12 = ContextGuard.getManager(var1.getlocal(0))).__enter__(var2);

      label52: {
         try {
            try {
               var1.setline(30);
               var4 = var1.getglobal("json").__getattr__("load").__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var4);
               var4 = null;
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (var9.match(var1.getglobal("ValueError"))) {
                  PyObject var5 = var9.value;
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(32);
                  throw Py.makeException(var1.getglobal("SystemExit").__call__(var2, var1.getlocal(3)));
               }

               throw var9;
            }
         } catch (Throwable var8) {
            if (var12.__exit__(var2, Py.setException(var8, var1))) {
               break label52;
            }

            throw (Throwable)Py.makeException();
         }

         var12.__exit__(var2, (PyException)null);
      }

      var4 = (var12 = ContextGuard.getManager(var1.getlocal(1))).__enter__(var2);

      label41: {
         try {
            var1.setline(34);
            var10000 = var1.getglobal("json").__getattr__("dump");
            PyObject[] var10 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getglobal("True"), Py.newInteger(4), new PyTuple(new PyObject[]{PyString.fromInterned(","), PyString.fromInterned(": ")})};
            String[] var11 = new String[]{"sort_keys", "indent", "separators"};
            var10000.__call__(var2, var10, var11);
            var4 = null;
            var1.setline(36);
            var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         } catch (Throwable var6) {
            if (var12.__exit__(var2, Py.setException(var6, var1))) {
               break label41;
            }

            throw (Throwable)Py.makeException();
         }

         var12.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public tool$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"infile", "outfile", "obj", "e"};
      main$1 = Py.newCode(0, var2, var1, "main", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tool$py("json/tool$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tool$py.class);
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

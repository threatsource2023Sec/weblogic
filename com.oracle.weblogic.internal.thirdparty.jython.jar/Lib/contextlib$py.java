import java.util.Arrays;
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
@Filename("contextlib.py")
public class contextlib$py extends PyFunctionTable implements PyRunnable {
   static contextlib$py self;
   static final PyCode f$0;
   static final PyCode GeneratorContextManager$1;
   static final PyCode __init__$2;
   static final PyCode __enter__$3;
   static final PyCode __exit__$4;
   static final PyCode contextmanager$5;
   static final PyCode helper$6;
   static final PyCode nested$7;
   static final PyCode closing$8;
   static final PyCode __init__$9;
   static final PyCode __enter__$10;
   static final PyCode __exit__$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities for with-statement contexts.  See PEP 343."));
      var1.setline(1);
      PyString.fromInterned("Utilities for with-statement contexts.  See PEP 343.");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"wraps"};
      PyObject[] var6 = imp.importFrom("functools", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("wraps", var4);
      var4 = null;
      var1.setline(5);
      var5 = new String[]{"warn"};
      var6 = imp.importFrom("warnings", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(7);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("contextmanager"), PyString.fromInterned("nested"), PyString.fromInterned("closing")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("GeneratorContextManager", var6, GeneratorContextManager$1);
      var1.setlocal("GeneratorContextManager", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(54);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, contextmanager$5, PyString.fromInterned("@contextmanager decorator.\n\n    Typical usage:\n\n        @contextmanager\n        def some_generator(<arguments>):\n            <setup>\n            try:\n                yield <value>\n            finally:\n                <cleanup>\n\n    This makes this:\n\n        with some_generator(<arguments>) as <variable>:\n            <body>\n\n    equivalent to this:\n\n        <setup>\n        try:\n            <variable> = <value>\n            <body>\n        finally:\n            <cleanup>\n\n    "));
      var1.setlocal("contextmanager", var8);
      var3 = null;
      var1.setline(88);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, nested$7, PyString.fromInterned("Combine multiple context managers into a single nested context manager.\n\n   This function has been deprecated in favour of the multiple manager form\n   of the with statement.\n\n   The one advantage of this function over the multiple manager form of the\n   with statement is that argument unpacking allows it to be\n   used with a variable number of context managers as follows:\n\n      with nested(*managers):\n          do_something()\n\n    "));
      var3 = var1.getname("contextmanager").__call__((ThreadState)var2, (PyObject)var8);
      var1.setlocal("nested", var3);
      var3 = null;
      var1.setline(132);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("closing", var6, closing$8);
      var1.setlocal("closing", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject GeneratorContextManager$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Helper for @contextmanager decorator."));
      var1.setline(10);
      PyString.fromInterned("Helper for @contextmanager decorator.");
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(15);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$3, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$4, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("gen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(17);
         PyObject var3 = var1.getlocal(0).__getattr__("gen").__getattr__("next").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("StopIteration"))) {
            var1.setline(19);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("generator didn't yield")));
         } else {
            throw var4;
         }
      }
   }

   public PyObject __exit__$4(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyException var8;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(24);
            var1.getlocal(0).__getattr__("gen").__getattr__("next").__call__(var2);
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (var8.match(var1.getglobal("StopIteration"))) {
               var1.setline(26);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var8;
         }

         var1.setline(28);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("generator didn't stop")));
      } else {
         var1.setline(30);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(33);
            var3 = var1.getlocal(1).__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
         }

         try {
            var1.setline(35);
            var1.getlocal(0).__getattr__("gen").__getattr__("throw").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.setline(36);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("generator didn't stop after throw()")));
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("StopIteration"))) {
               PyObject var4 = var8.value;
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(41);
               var4 = var1.getlocal(4);
               var10000 = var4._isnot(var1.getlocal(2));
               var4 = null;
               var4 = var10000;
               var1.f_lasti = -1;
               return var4;
            } else {
               var1.setline(50);
               PyObject var5 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1));
               var10000 = var5._isnot(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(51);
                  throw Py.makeException();
               } else {
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject contextmanager$5(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(81);
      PyString.fromInterned("@contextmanager decorator.\n\n    Typical usage:\n\n        @contextmanager\n        def some_generator(<arguments>):\n            <setup>\n            try:\n                yield <value>\n            finally:\n                <cleanup>\n\n    This makes this:\n\n        with some_generator(<arguments>) as <variable>:\n            <body>\n\n    equivalent to this:\n\n        <setup>\n        try:\n            <variable> = <value>\n            <body>\n        finally:\n            <cleanup>\n\n    ");
      var1.setline(82);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = helper$6;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getglobal("wraps").__call__(var2, var1.getderef(0)).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(85);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject helper$6(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var10000 = var1.getglobal("GeneratorContextManager");
      PyObject var10002 = var1.getderef(0);
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject nested$7(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject closing$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Context to automatically close something at the end of a block.\n\n    Code like this:\n\n        with closing(<module>.open(<arguments>)) as f:\n            <block>\n\n    is equivalent to this:\n\n        f = <module>.open(<arguments>)\n        try:\n            <block>\n        finally:\n            f.close()\n\n    "));
      var1.setline(148);
      PyString.fromInterned("Context to automatically close something at the end of a block.\n\n    Code like this:\n\n        with closing(<module>.open(<arguments>)) as f:\n            <block>\n\n    is equivalent to this:\n\n        f = <module>.open(<arguments>)\n        try:\n            <block>\n        finally:\n            f.close()\n\n    ");
      var1.setline(149);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(151);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$10, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$11, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("thing", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$10(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getlocal(0).__getattr__("thing");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$11(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      var1.getlocal(0).__getattr__("thing").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public contextlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      GeneratorContextManager$1 = Py.newCode(0, var2, var1, "GeneratorContextManager", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "gen"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$3 = Py.newCode(1, var2, var1, "__enter__", 15, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "traceback", "exc"};
      __exit__$4 = Py.newCode(4, var2, var1, "__exit__", 21, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "helper"};
      String[] var10001 = var2;
      contextlib$py var10007 = self;
      var2 = new String[]{"func"};
      contextmanager$5 = Py.newCode(1, var10001, var1, "contextmanager", 54, false, false, var10007, 5, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwds"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      helper$6 = Py.newCode(2, var10001, var1, "helper", 82, true, true, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[]{"managers", "exits", "vars", "exc", "mgr", "exit", "enter"};
      nested$7 = Py.newCode(1, var2, var1, "nested", 88, true, false, self, 7, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      closing$8 = Py.newCode(0, var2, var1, "closing", 132, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "thing"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 149, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$10 = Py.newCode(1, var2, var1, "__enter__", 151, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc_info"};
      __exit__$11 = Py.newCode(2, var2, var1, "__exit__", 153, true, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new contextlib$py("contextlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(contextlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.GeneratorContextManager$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__enter__$3(var2, var3);
         case 4:
            return this.__exit__$4(var2, var3);
         case 5:
            return this.contextmanager$5(var2, var3);
         case 6:
            return this.helper$6(var2, var3);
         case 7:
            return this.nested$7(var2, var3);
         case 8:
            return this.closing$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.__enter__$10(var2, var3);
         case 11:
            return this.__exit__$11(var2, var3);
         default:
            return null;
      }
   }
}

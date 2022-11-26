package compiler;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("compiler/future.py")
public class future$py extends PyFunctionTable implements PyRunnable {
   static future$py self;
   static final PyCode f$0;
   static final PyCode is_future$1;
   static final PyCode FutureParser$2;
   static final PyCode __init__$3;
   static final PyCode visitModule$4;
   static final PyCode check_stmt$5;
   static final PyCode get_features$6;
   static final PyCode BadFutureParser$7;
   static final PyCode visitFrom$8;
   static final PyCode find_futures$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parser for future statements\n\n"));
      var1.setline(3);
      PyString.fromInterned("Parser for future statements\n\n");
      var1.setline(5);
      String[] var3 = new String[]{"ast", "walk"};
      PyObject[] var6 = imp.importFrom("compiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("ast", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("walk", var4);
      var4 = null;
      var1.setline(7);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, is_future$1, PyString.fromInterned("Return true if statement is a well-formed future statement"));
      var1.setlocal("is_future", var7);
      var3 = null;
      var1.setline(16);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("FutureParser", var6, FutureParser$2);
      var1.setlocal("FutureParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(47);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("BadFutureParser", var6, BadFutureParser$7);
      var1.setlocal("BadFutureParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(57);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_futures$9, (PyObject)null);
      var1.setlocal("find_futures", var7);
      var3 = null;
      var1.setline(64);
      PyObject var8 = var1.getname("__name__");
      PyObject var10000 = var8._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var8 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var8);
         var3 = null;
         var1.setline(66);
         var3 = new String[]{"parseFile", "walk"};
         var6 = imp.importFrom("compiler", var3, var1, -1);
         var4 = var6[0];
         var1.setlocal("parseFile", var4);
         var4 = null;
         var4 = var6[1];
         var1.setlocal("walk", var4);
         var4 = null;
         var1.setline(68);
         var8 = var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(68);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal("file", var4);
            var1.setline(69);
            Py.println(var1.getname("file"));
            var1.setline(70);
            PyObject var5 = var1.getname("parseFile").__call__(var2, var1.getname("file"));
            var1.setlocal("tree", var5);
            var5 = null;
            var1.setline(71);
            var5 = var1.getname("FutureParser").__call__(var2);
            var1.setlocal("v", var5);
            var5 = null;
            var1.setline(72);
            var1.getname("walk").__call__(var2, var1.getname("tree"), var1.getname("v"));
            var1.setline(73);
            Py.println(var1.getname("v").__getattr__("found"));
            var1.setline(74);
            Py.println();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_future$1(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyString.fromInterned("Return true if statement is a well-formed future statement");
      var1.setline(9);
      PyInteger var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("ast").__getattr__("From")).__not__().__nonzero__()) {
         var1.setline(10);
         var3 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(11);
         PyObject var4 = var1.getlocal(0).__getattr__("modname");
         PyObject var10000 = var4._eq(PyString.fromInterned("__future__"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(12);
            var3 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(14);
            var3 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject FutureParser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("nested_scopes"), PyString.fromInterned("generators"), PyString.fromInterned("division"), PyString.fromInterned("absolute_import"), PyString.fromInterned("with_statement"), PyString.fromInterned("print_function"), PyString.fromInterned("unicode_literals")});
      var1.setlocal("features", var3);
      var3 = null;
      var1.setline(22);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(25);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, visitModule$4, (PyObject)null);
      var1.setlocal("visitModule", var5);
      var3 = null;
      var1.setline(31);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_stmt$5, (PyObject)null);
      var1.setlocal("check_stmt", var5);
      var3 = null;
      var1.setline(43);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_features$6, PyString.fromInterned("Return list of features enabled by future statements"));
      var1.setlocal("get_features", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"found", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitModule$4(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getlocal(1).__getattr__("node");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getlocal(2).__getattr__("nodes").__iter__();

      do {
         var1.setline(27);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(28);
      } while(!var1.getlocal(0).__getattr__("check_stmt").__call__(var2, var1.getlocal(3)).__not__().__nonzero__());

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_stmt$5(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyInteger var7;
      if (!var1.getglobal("is_future").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(41);
         var7 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(33);
         PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

         while(true) {
            var1.setline(33);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(39);
               var7 = Py.newInteger(1);
               var1.getlocal(1).__setattr__((String)"valid_future", var7);
               var3 = null;
               var1.setline(40);
               var7 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var7;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(34);
            PyObject var8 = var1.getlocal(2);
            PyObject var10000 = var8._in(var1.getlocal(0).__getattr__("features"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(37);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("future feature %s is not defined")._mod(var1.getlocal(2)));
            }

            var1.setline(35);
            PyInteger var9 = Py.newInteger(1);
            var1.getlocal(0).__getattr__("found").__setitem__((PyObject)var1.getlocal(2), var9);
            var5 = null;
         }
      }
   }

   public PyObject get_features$6(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyString.fromInterned("Return list of features enabled by future statements");
      var1.setline(45);
      PyObject var3 = var1.getlocal(0).__getattr__("found").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BadFutureParser$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Check for invalid future statements"));
      var1.setline(48);
      PyString.fromInterned("Check for invalid future statements");
      var1.setline(50);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, visitFrom$8, (PyObject)null);
      var1.setlocal("visitFrom", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject visitFrom$8(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("valid_future")).__nonzero__()) {
         var1.setline(52);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(53);
         PyObject var3 = var1.getlocal(1).__getattr__("modname");
         PyObject var10000 = var3._ne(PyString.fromInterned("__future__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(54);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(55);
            throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("invalid future statement ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         }
      }
   }

   public PyObject find_futures$9(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getglobal("FutureParser").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("BadFutureParser").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(60);
      var1.getglobal("walk").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(61);
      var1.getglobal("walk").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(62);
      var3 = var1.getlocal(1).__getattr__("get_features").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public future$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"stmt"};
      is_future$1 = Py.newCode(1, var2, var1, "is_future", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FutureParser$2 = Py.newCode(0, var2, var1, "FutureParser", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$3 = Py.newCode(1, var2, var1, "__init__", 22, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "stmt", "s"};
      visitModule$4 = Py.newCode(2, var2, var1, "visitModule", 25, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stmt", "name", "asname"};
      check_stmt$5 = Py.newCode(2, var2, var1, "check_stmt", 31, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_features$6 = Py.newCode(1, var2, var1, "get_features", 43, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BadFutureParser$7 = Py.newCode(0, var2, var1, "BadFutureParser", 47, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node"};
      visitFrom$8 = Py.newCode(2, var2, var1, "visitFrom", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "p1", "p2"};
      find_futures$9 = Py.newCode(1, var2, var1, "find_futures", 57, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new future$py("compiler/future$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(future$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.is_future$1(var2, var3);
         case 2:
            return this.FutureParser$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.visitModule$4(var2, var3);
         case 5:
            return this.check_stmt$5(var2, var3);
         case 6:
            return this.get_features$6(var2, var3);
         case 7:
            return this.BadFutureParser$7(var2, var3);
         case 8:
            return this.visitFrom$8(var2, var3);
         case 9:
            return this.find_futures$9(var2, var3);
         default:
            return null;
      }
   }
}

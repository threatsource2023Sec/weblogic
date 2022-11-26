package lib2to3.fixes;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_exitfunc.py")
public class fix_exitfunc$py extends PyFunctionTable implements PyRunnable {
   static fix_exitfunc$py self;
   static final PyCode f$0;
   static final PyCode FixExitfunc$1;
   static final PyCode __init__$2;
   static final PyCode start_tree$3;
   static final PyCode transform$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nConvert use of sys.exitfunc to use the atexit module.\n"));
      var1.setline(3);
      PyString.fromInterned("\nConvert use of sys.exitfunc to use the atexit module.\n");
      var1.setline(7);
      String[] var3 = new String[]{"pytree", "fixer_base"};
      PyObject[] var5 = imp.importFrom("lib2to3", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"Name", "Attr", "Call", "Comma", "Newline", "syms"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("Newline", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(11);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixExitfunc", var5, FixExitfunc$1);
      var1.setlocal("FixExitfunc", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixExitfunc$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject var3 = var1.getname("True");
      var1.setlocal("keep_line_order", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(15);
      PyString var4 = PyString.fromInterned("\n              (\n                  sys_import=import_name<'import'\n                      ('sys'\n                      |\n                      dotted_as_names< (any ',')* 'sys' (',' any)* >\n                      )\n                  >\n              |\n                  expr_stmt<\n                      power< 'sys' trailer< '.' 'exitfunc' > >\n                  '=' func=any >\n              )\n              ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(30);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(33);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_tree$3, (PyObject)null);
      var1.setlocal("start_tree", var6);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$4, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("FixExitfunc"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_tree$3(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixExitfunc"), var1.getlocal(0)).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(35);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sys_import", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$4(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString var3 = PyString.fromInterned("sys_import");
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         var4 = var1.getlocal(0).__getattr__("sys_import");
         var10000 = var4._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(41);
            var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("sys_import"));
            var1.getlocal(0).__setattr__("sys_import", var4);
            var3 = null;
         }

         var1.setline(42);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(44);
         var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("func")).__getattr__("clone").__call__(var2);
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(45);
         PyUnicode var5 = PyUnicode.fromInterned("");
         var1.getlocal(3).__setattr__((String)"prefix", var5);
         var3 = null;
         var1.setline(46);
         var4 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getglobal("syms").__getattr__("power"), var1.getglobal("Attr").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("atexit")), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("register"))));
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(49);
         var4 = var1.getglobal("Call").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})), (PyObject)var1.getlocal(1).__getattr__("prefix"));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(50);
         var1.getlocal(1).__getattr__("replace").__call__(var2, var1.getlocal(5));
         var1.setline(52);
         var4 = var1.getlocal(0).__getattr__("sys_import");
         var10000 = var4._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(54);
            var1.getlocal(0).__getattr__("warning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Can't find sys import; Please add an atexit import at the top of your file."));
            var1.setline(56);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(59);
            var4 = var1.getlocal(0).__getattr__("sys_import").__getattr__("children").__getitem__(Py.newInteger(1));
            var1.setlocal(6, var4);
            var3 = null;
            var1.setline(60);
            var4 = var1.getlocal(6).__getattr__("type");
            var10000 = var4._eq(var1.getglobal("syms").__getattr__("dotted_as_names"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(61);
               var1.getlocal(6).__getattr__("append_child").__call__(var2, var1.getglobal("Comma").__call__(var2));
               var1.setline(62);
               var1.getlocal(6).__getattr__("append_child").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("atexit"), (PyObject)PyUnicode.fromInterned(" ")));
            } else {
               var1.setline(64);
               var4 = var1.getlocal(0).__getattr__("sys_import").__getattr__("parent");
               var1.setlocal(7, var4);
               var3 = null;
               var1.setline(65);
               var4 = var1.getlocal(7).__getattr__("children").__getattr__("index").__call__(var2, var1.getlocal(0).__getattr__("sys_import"));
               var1.setlocal(8, var4);
               var3 = null;
               var1.setline(66);
               var4 = var1.getlocal(7).__getattr__("parent");
               var1.setlocal(9, var4);
               var3 = null;
               var1.setline(67);
               var4 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("import_name"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("import")), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("atexit"), (PyObject)PyUnicode.fromInterned(" "))})));
               var1.setlocal(10, var4);
               var3 = null;
               var1.setline(70);
               var4 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("simple_stmt"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(10)})));
               var1.setlocal(11, var4);
               var3 = null;
               var1.setline(71);
               var1.getlocal(7).__getattr__("insert_child").__call__(var2, var1.getlocal(8)._add(Py.newInteger(1)), var1.getglobal("Newline").__call__(var2));
               var1.setline(72);
               var1.getlocal(7).__getattr__("insert_child").__call__(var2, var1.getlocal(8)._add(Py.newInteger(2)), var1.getlocal(11));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public fix_exitfunc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixExitfunc$1 = Py.newCode(0, var2, var1, "FixExitfunc", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 30, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "filename"};
      start_tree$3 = Py.newCode(3, var2, var1, "start_tree", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "func", "register", "call", "names", "containing_stmt", "position", "stmt_container", "new_import", "new"};
      transform$4 = Py.newCode(3, var2, var1, "transform", 37, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_exitfunc$py("lib2to3/fixes/fix_exitfunc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_exitfunc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixExitfunc$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.start_tree$3(var2, var3);
         case 4:
            return this.transform$4(var2, var3);
         default:
            return null;
      }
   }
}

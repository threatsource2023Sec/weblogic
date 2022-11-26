package lib2to3.fixes;

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
@Filename("lib2to3/fixes/fix_xrange.py")
public class fix_xrange$py extends PyFunctionTable implements PyRunnable {
   static fix_xrange$py self;
   static final PyCode f$0;
   static final PyCode FixXrange$1;
   static final PyCode start_tree$2;
   static final PyCode finish_tree$3;
   static final PyCode transform$4;
   static final PyCode transform_xrange$5;
   static final PyCode transform_range$6;
   static final PyCode in_special_context$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes xrange(...) into range(...)."));
      var1.setline(4);
      PyString.fromInterned("Fixer that changes xrange(...) into range(...).");
      var1.setline(7);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"Name", "Call", "consuming_calls"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("consuming_calls", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"patcomp"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("patcomp", var4);
      var4 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixXrange", var5, FixXrange$1);
      var1.setlocal("FixXrange", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixXrange$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(14);
      PyString var4 = PyString.fromInterned("\n              power<\n                 (name='range'|name='xrange') trailer< '(' args=any ')' >\n              rest=any* >\n              ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(20);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, start_tree$2, (PyObject)null);
      var1.setlocal("start_tree", var6);
      var3 = null;
      var1.setline(24);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finish_tree$3, (PyObject)null);
      var1.setlocal("finish_tree", var6);
      var3 = null;
      var1.setline(27);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$4, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      var1.setline(36);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform_xrange$5, (PyObject)null);
      var1.setlocal("transform_xrange", var6);
      var3 = null;
      var1.setline(42);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform_range$6, (PyObject)null);
      var1.setlocal("transform_range", var6);
      var3 = null;
      var1.setline(54);
      var4 = PyString.fromInterned("power< func=NAME trailer< '(' node=any ')' > any* >");
      var1.setlocal("P1", var4);
      var3 = null;
      var1.setline(55);
      var3 = var1.getname("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getname("P1"));
      var1.setlocal("p1", var3);
      var3 = null;
      var1.setline(57);
      var4 = PyString.fromInterned("for_stmt< 'for' any 'in' node=any ':' any* >\n            | comp_for< 'for' any 'in' node=any any* >\n            | comparison< any 'in' node=any any*>\n         ");
      var1.setlocal("P2", var4);
      var3 = null;
      var1.setline(61);
      var3 = var1.getname("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getname("P2"));
      var1.setlocal("p2", var3);
      var3 = null;
      var1.setline(63);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, in_special_context$7, (PyObject)null);
      var1.setlocal("in_special_context", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject start_tree$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixXrange"), var1.getlocal(0)).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(22);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.getlocal(0).__setattr__("transformed_xranges", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_tree$3(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("transformed_xranges", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$4(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("name"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getlocal(3).__getattr__("value");
      PyObject var10000 = var3._eq(PyUnicode.fromInterned("xrange"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var3 = var1.getlocal(0).__getattr__("transform_xrange").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(31);
         PyObject var4 = var1.getlocal(3).__getattr__("value");
         var10000 = var4._eq(PyUnicode.fromInterned("range"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(32);
            var3 = var1.getlocal(0).__getattr__("transform_range").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(34);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(3))));
         }
      }
   }

   public PyObject transform_xrange$5(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("name"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(38);
      PyObject var10000 = var1.getlocal(3).__getattr__("replace");
      PyObject var10002 = var1.getglobal("Name");
      PyObject[] var5 = new PyObject[]{PyUnicode.fromInterned("range"), var1.getlocal(3).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(40);
      var1.getlocal(0).__getattr__("transformed_xranges").__getattr__("add").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform_range$6(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("transformed_xranges"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("in_special_context").__call__(var2, var1.getlocal(1)).__not__();
      }

      if (!var10000.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(45);
         var3 = var1.getglobal("Call").__call__((ThreadState)var2, (PyObject)var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("range")), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__getitem__(PyString.fromInterned("args")).__getattr__("clone").__call__(var2)})));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(47);
         var10000 = var1.getglobal("Call");
         PyObject[] var5 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("list")), new PyList(new PyObject[]{var1.getlocal(3)}), var1.getlocal(1).__getattr__("prefix")};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(50);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("rest")).__iter__();

         while(true) {
            var1.setline(50);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(52);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(5, var6);
            var1.setline(51);
            var1.getlocal(4).__getattr__("append_child").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject in_special_context$7(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getlocal(1).__getattr__("parent");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(66);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(67);
         PyObject var5 = var1.getlocal(1).__getattr__("parent").__getattr__("parent");
         var10000 = var5._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("p1").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent").__getattr__("parent"), var1.getlocal(2));
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2).__getitem__(PyString.fromInterned("node"));
               var10000 = var5._is(var1.getlocal(1));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(71);
            var5 = var1.getlocal(2).__getitem__(PyString.fromInterned("func")).__getattr__("value");
            var10000 = var5._in(var1.getglobal("consuming_calls"));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(73);
            var10000 = var1.getlocal(0).__getattr__("p2").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent"), var1.getlocal(2));
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2).__getitem__(PyString.fromInterned("node"));
               var10000 = var5._is(var1.getlocal(1));
               var4 = null;
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public fix_xrange$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixXrange$1 = Py.newCode(0, var2, var1, "FixXrange", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree", "filename"};
      start_tree$2 = Py.newCode(3, var2, var1, "start_tree", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "filename"};
      finish_tree$3 = Py.newCode(3, var2, var1, "finish_tree", 24, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "name"};
      transform$4 = Py.newCode(3, var2, var1, "transform", 27, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "name"};
      transform_xrange$5 = Py.newCode(3, var2, var1, "transform_xrange", 36, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "range_call", "list_call", "n"};
      transform_range$6 = Py.newCode(3, var2, var1, "transform_range", 42, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      in_special_context$7 = Py.newCode(2, var2, var1, "in_special_context", 63, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_xrange$py("lib2to3/fixes/fix_xrange$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_xrange$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixXrange$1(var2, var3);
         case 2:
            return this.start_tree$2(var2, var3);
         case 3:
            return this.finish_tree$3(var2, var3);
         case 4:
            return this.transform$4(var2, var3);
         case 5:
            return this.transform_xrange$5(var2, var3);
         case 6:
            return this.transform_range$6(var2, var3);
         case 7:
            return this.in_special_context$7(var2, var3);
         default:
            return null;
      }
   }
}

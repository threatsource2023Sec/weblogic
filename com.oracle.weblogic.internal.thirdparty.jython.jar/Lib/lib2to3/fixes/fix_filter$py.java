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
@Filename("lib2to3/fixes/fix_filter.py")
public class fix_filter$py extends PyFunctionTable implements PyRunnable {
   static fix_filter$py self;
   static final PyCode f$0;
   static final PyCode FixFilter$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes filter(F, X) into list(filter(F, X)).\n\nWe avoid the transformation if the filter() call is directly contained\nin iter(<>), list(<>), tuple(<>), sorted(<>), ...join(<>), or\nfor V in <>:.\n\nNOTE: This is still not correct if the original code was depending on\nfilter(F, X) to return a string if X is a string and a tuple if X is a\ntuple.  That would require type inference, which we don't do.  Let\nPython 2.6 figure it out.\n"));
      var1.setline(14);
      PyString.fromInterned("Fixer that changes filter(F, X) into list(filter(F, X)).\n\nWe avoid the transformation if the filter() call is directly contained\nin iter(<>), list(<>), tuple(<>), sorted(<>), ...join(<>), or\nfor V in <>:.\n\nNOTE: This is still not correct if the original code was depending on\nfilter(F, X) to return a string if X is a string and a tuple if X is a\ntuple.  That would require type inference, which we don't do.  Let\nPython 2.6 figure it out.\n");
      var1.setline(17);
      String[] var3 = new String[]{"token"};
      PyObject[] var5 = imp.importFrom("pgen2", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(18);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(19);
      var3 = new String[]{"Name", "Call", "ListComp", "in_special_context"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("ListComp", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("in_special_context", var4);
      var4 = null;
      var1.setline(21);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("ConditionalFix")};
      var4 = Py.makeClass("FixFilter", var5, FixFilter$1);
      var1.setlocal("FixFilter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixFilter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(24);
      PyString var4 = PyString.fromInterned("\n    filter_lambda=power<\n        'filter'\n        trailer<\n            '('\n            arglist<\n                lambdef< 'lambda'\n                         (fp=NAME | vfpdef< '(' fp=NAME ')'> ) ':' xp=any\n                >\n                ','\n                it=any\n            >\n            ')'\n        >\n    >\n    |\n    power<\n        'filter'\n        trailer< '(' arglist< none='None' ',' seq=any > ')' >\n    >\n    |\n    power<\n        'filter'\n        args=trailer< '(' [any] ')' >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(51);
      var4 = PyString.fromInterned("future_builtins.filter");
      var1.setlocal("skip_on", var4);
      var3 = null;
      var1.setline(53);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      if (var1.getlocal(0).__getattr__("should_skip").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(55);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(57);
         PyString var3 = PyString.fromInterned("filter_lambda");
         PyObject var10000 = var3._in(var1.getlocal(2));
         var3 = null;
         PyObject var4;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(58);
            var5 = var1.getglobal("ListComp").__call__(var2, var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fp")).__getattr__("clone").__call__(var2), var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fp")).__getattr__("clone").__call__(var2), var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("it")).__getattr__("clone").__call__(var2), var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xp")).__getattr__("clone").__call__(var2));
            var1.setlocal(3, var5);
            var3 = null;
         } else {
            var1.setline(63);
            var3 = PyString.fromInterned("none");
            var10000 = var3._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(64);
               var5 = var1.getglobal("ListComp").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("_f")), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("_f")), var1.getlocal(2).__getitem__(PyString.fromInterned("seq")).__getattr__("clone").__call__(var2), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("_f")));
               var1.setlocal(3, var5);
               var3 = null;
            } else {
               var1.setline(70);
               if (var1.getglobal("in_special_context").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(71);
                  var5 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setline(72);
               var4 = var1.getlocal(1).__getattr__("clone").__call__(var2);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(73);
               PyUnicode var6 = PyUnicode.fromInterned("");
               var1.getlocal(3).__setattr__((String)"prefix", var6);
               var4 = null;
               var1.setline(74);
               var4 = var1.getglobal("Call").__call__((ThreadState)var2, (PyObject)var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("list")), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
               var1.setlocal(3, var4);
               var4 = null;
            }
         }

         var1.setline(75);
         var4 = var1.getlocal(1).__getattr__("prefix");
         var1.getlocal(3).__setattr__("prefix", var4);
         var4 = null;
         var1.setline(76);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public fix_filter$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixFilter$1 = Py.newCode(0, var2, var1, "FixFilter", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 53, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_filter$py("lib2to3/fixes/fix_filter$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_filter$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixFilter$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

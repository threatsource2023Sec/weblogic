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
@Filename("lib2to3/fixes/fix_map.py")
public class fix_map$py extends PyFunctionTable implements PyRunnable {
   static fix_map$py self;
   static final PyCode f$0;
   static final PyCode FixMap$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes map(F, ...) into list(map(F, ...)) unless there\nexists a 'from future_builtins import map' statement in the top-level\nnamespace.\n\nAs a special case, map(None, X) is changed into list(X).  (This is\nnecessary because the semantics are changed in this case -- the new\nmap(None, X) is equivalent to [(x,) for x in X].)\n\nWe avoid the transformation (except for the special case mentioned\nabove) if the map() call is directly contained in iter(<>), list(<>),\ntuple(<>), sorted(<>), ...join(<>), or for V in <>:.\n\nNOTE: This is still not correct if the original code was depending on\nmap(F, X, Y, ...) to go on until the longest argument is exhausted,\nsubstituting None for missing values -- like zip(), it now stops as\nsoon as the shortest argument is exhausted.\n"));
      var1.setline(20);
      PyString.fromInterned("Fixer that changes map(F, ...) into list(map(F, ...)) unless there\nexists a 'from future_builtins import map' statement in the top-level\nnamespace.\n\nAs a special case, map(None, X) is changed into list(X).  (This is\nnecessary because the semantics are changed in this case -- the new\nmap(None, X) is equivalent to [(x,) for x in X].)\n\nWe avoid the transformation (except for the special case mentioned\nabove) if the map() call is directly contained in iter(<>), list(<>),\ntuple(<>), sorted(<>), ...join(<>), or for V in <>:.\n\nNOTE: This is still not correct if the original code was depending on\nmap(F, X, Y, ...) to go on until the longest argument is exhausted,\nsubstituting None for missing values -- like zip(), it now stops as\nsoon as the shortest argument is exhausted.\n");
      var1.setline(23);
      String[] var3 = new String[]{"token"};
      PyObject[] var5 = imp.importFrom("pgen2", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(24);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(25);
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
      var1.setline(26);
      var3 = new String[]{"python_symbols"};
      var5 = imp.importFrom("pygram", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(28);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("ConditionalFix")};
      var4 = Py.makeClass("FixMap", var5, FixMap$1);
      var1.setlocal("FixMap", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixMap$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(31);
      PyString var4 = PyString.fromInterned("\n    map_none=power<\n        'map'\n        trailer< '(' arglist< 'None' ',' arg=any [','] > ')' >\n    >\n    |\n    map_lambda=power<\n        'map'\n        trailer<\n            '('\n            arglist<\n                lambdef< 'lambda'\n                         (fp=NAME | vfpdef< '(' fp=NAME ')'> ) ':' xp=any\n                >\n                ','\n                it=any\n            >\n            ')'\n        >\n    >\n    |\n    power<\n        'map' trailer< '(' [arglist=any] ')' >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(57);
      var4 = PyString.fromInterned("future_builtins.map");
      var1.setlocal("skip_on", var4);
      var3 = null;
      var1.setline(59);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      if (var1.getlocal(0).__getattr__("should_skip").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(61);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(63);
         PyObject var3 = var1.getlocal(1).__getattr__("parent").__getattr__("type");
         PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
         var3 = null;
         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(64);
            var1.getlocal(0).__getattr__("warning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("You should use a for loop here"));
            var1.setline(65);
            var3 = var1.getlocal(1).__getattr__("clone").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(66);
            PyUnicode var5 = PyUnicode.fromInterned("");
            var1.getlocal(3).__setattr__((String)"prefix", var5);
            var3 = null;
            var1.setline(67);
            var3 = var1.getglobal("Call").__call__((ThreadState)var2, (PyObject)var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("list")), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(68);
            PyString var7 = PyString.fromInterned("map_lambda");
            var10000 = var7._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(69);
               var3 = var1.getglobal("ListComp").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("xp")).__getattr__("clone").__call__(var2), var1.getlocal(2).__getitem__(PyString.fromInterned("fp")).__getattr__("clone").__call__(var2), var1.getlocal(2).__getitem__(PyString.fromInterned("it")).__getattr__("clone").__call__(var2));
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(73);
               var7 = PyString.fromInterned("map_none");
               var10000 = var7._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(74);
                  var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("arg")).__getattr__("clone").__call__(var2);
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(76);
                  var7 = PyString.fromInterned("arglist");
                  var10000 = var7._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(77);
                     var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("arglist"));
                     var1.setlocal(4, var3);
                     var3 = null;
                     var1.setline(78);
                     var3 = var1.getlocal(4).__getattr__("type");
                     var10000 = var3._eq(var1.getglobal("syms").__getattr__("arglist"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("type");
                        var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                           var10000 = var3._eq(PyString.fromInterned("None"));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(81);
                        var1.getlocal(0).__getattr__("warning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("cannot convert map(None, ...) with multiple arguments because map() now truncates to the shortest sequence"));
                        var1.setline(84);
                        var1.f_lasti = -1;
                        return Py.None;
                     }
                  }

                  var1.setline(85);
                  if (var1.getglobal("in_special_context").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                     var1.setline(86);
                     var3 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(87);
                  var4 = var1.getlocal(1).__getattr__("clone").__call__(var2);
                  var1.setlocal(3, var4);
                  var4 = null;
               }

               var1.setline(88);
               PyUnicode var6 = PyUnicode.fromInterned("");
               var1.getlocal(3).__setattr__((String)"prefix", var6);
               var4 = null;
               var1.setline(89);
               var4 = var1.getglobal("Call").__call__((ThreadState)var2, (PyObject)var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("list")), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
               var1.setlocal(3, var4);
               var4 = null;
            }
         }

         var1.setline(90);
         var4 = var1.getlocal(1).__getattr__("prefix");
         var1.getlocal(3).__setattr__("prefix", var4);
         var4 = null;
         var1.setline(91);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_map$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixMap$1 = Py.newCode(0, var2, var1, "FixMap", 28, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new", "args"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_map$py("lib2to3/fixes/fix_map$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_map$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixMap$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

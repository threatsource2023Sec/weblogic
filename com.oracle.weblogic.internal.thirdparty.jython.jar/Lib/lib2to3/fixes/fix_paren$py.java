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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_paren.py")
public class fix_paren$py extends PyFunctionTable implements PyRunnable {
   static fix_paren$py self;
   static final PyCode f$0;
   static final PyCode FixParen$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that addes parentheses where they are required\n\nThis converts ``[x for x in 1, 2]`` to ``[x for x in (1, 2)]``."));
      var1.setline(3);
      PyString.fromInterned("Fixer that addes parentheses where they are required\n\nThis converts ``[x for x in 1, 2]`` to ``[x for x in (1, 2)]``.");
      var1.setline(8);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"LParen", "RParen"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("LParen", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("RParen", var4);
      var4 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixParen", var5, FixParen$1);
      var1.setlocal("FixParen", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixParen$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(15);
      PyString var4 = PyString.fromInterned("\n        atom< ('[' | '(')\n            (listmaker< any\n                comp_for<\n                    'for' NAME 'in'\n                    target=testlist_safe< any (',' any)+ [',']\n                     >\n                    [any]\n                >\n            >\n            |\n            testlist_gexp< any\n                comp_for<\n                    'for' NAME 'in'\n                    target=testlist_safe< any (',' any)+ [',']\n                     >\n                    [any]\n                >\n            >)\n        (']' | ')') >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(37);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("target"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getglobal("LParen").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getlocal(3).__getattr__("prefix");
      var1.getlocal(4).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(42);
      PyUnicode var4 = PyUnicode.fromInterned("");
      var1.getlocal(3).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(43);
      var1.getlocal(3).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4));
      var1.setline(44);
      var1.getlocal(3).__getattr__("append_child").__call__(var2, var1.getglobal("RParen").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_paren$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixParen$1 = Py.newCode(0, var2, var1, "FixParen", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "target", "lparen"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_paren$py("lib2to3/fixes/fix_paren$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_paren$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixParen$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

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
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_ws_comma.py")
public class fix_ws_comma$py extends PyFunctionTable implements PyRunnable {
   static fix_ws_comma$py self;
   static final PyCode f$0;
   static final PyCode FixWsComma$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes 'a ,b' into 'a, b'.\n\nThis also changes '{a :b}' into '{a: b}', but does not touch other\nuses of colons.  It does not touch other uses of whitespace.\n\n"));
      var1.setline(6);
      PyString.fromInterned("Fixer that changes 'a ,b' into 'a, b'.\n\nThis also changes '{a :b}' into '{a: b}', but does not touch other\nuses of colons.  It does not touch other uses of whitespace.\n\n");
      var1.setline(8);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixWsComma", var5, FixWsComma$1);
      var1.setlocal("FixWsComma", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixWsComma$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject var3 = var1.getname("True");
      var1.setlocal("explicit", var3);
      var3 = null;
      var1.setline(16);
      PyString var4 = PyString.fromInterned("\n    any<(not(',') any)+ ',' ((not(',') any)+ ',')* [not(',') any]>\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(20);
      var3 = var1.getname("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getname("token").__getattr__("COMMA"), (PyObject)PyUnicode.fromInterned(","));
      var1.setlocal("COMMA", var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getname("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getname("token").__getattr__("COLON"), (PyObject)PyUnicode.fromInterned(":"));
      var1.setlocal("COLON", var3);
      var3 = null;
      var1.setline(22);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getname("COMMA"), var1.getname("COLON")});
      var1.setlocal("SEPS", var5);
      var3 = null;
      var1.setline(24);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$2, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getlocal(1).__getattr__("clone").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("False");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getlocal(3).__getattr__("children").__iter__();

      while(true) {
         var1.setline(27);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(39);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(28);
         PyObject var5 = var1.getlocal(5);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("SEPS"));
         var5 = null;
         PyUnicode var6;
         if (var10000.__nonzero__()) {
            var1.setline(29);
            var5 = var1.getlocal(5).__getattr__("prefix");
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(30);
            var10000 = var1.getlocal(6).__getattr__("isspace").__call__(var2);
            if (var10000.__nonzero__()) {
               var6 = PyUnicode.fromInterned("\n");
               var10000 = var6._notin(var1.getlocal(6));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(31);
               var6 = PyUnicode.fromInterned("");
               var1.getlocal(5).__setattr__((String)"prefix", var6);
               var5 = null;
            }

            var1.setline(32);
            var5 = var1.getglobal("True");
            var1.setlocal(4, var5);
            var5 = null;
         } else {
            var1.setline(34);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(35);
               var5 = var1.getlocal(5).__getattr__("prefix");
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(36);
               if (var1.getlocal(6).__not__().__nonzero__()) {
                  var1.setline(37);
                  var6 = PyUnicode.fromInterned(" ");
                  var1.getlocal(5).__setattr__((String)"prefix", var6);
                  var5 = null;
               }
            }

            var1.setline(38);
            var5 = var1.getglobal("False");
            var1.setlocal(4, var5);
            var5 = null;
         }
      }
   }

   public fix_ws_comma$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixWsComma$1 = Py.newCode(0, var2, var1, "FixWsComma", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new", "comma", "child", "prefix"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_ws_comma$py("lib2to3/fixes/fix_ws_comma$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_ws_comma$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixWsComma$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

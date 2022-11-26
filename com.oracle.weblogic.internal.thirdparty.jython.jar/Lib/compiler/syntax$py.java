package compiler;

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
@Filename("compiler/syntax.py")
public class syntax$py extends PyFunctionTable implements PyRunnable {
   static syntax$py self;
   static final PyCode f$0;
   static final PyCode check$1;
   static final PyCode SyntaxErrorChecker$2;
   static final PyCode __init__$3;
   static final PyCode error$4;
   static final PyCode visitAssign$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Check for errs in the AST.\n\nThe Python parser does not catch all syntax errors.  Others, like\nassignments with invalid targets, are caught in the code generation\nphase.\n\nThe compiler package catches some errors in the transformer module.\nBut it seems clearer to write checkers that use the AST to detect\nerrors.\n"));
      var1.setline(10);
      PyString.fromInterned("Check for errs in the AST.\n\nThe Python parser does not catch all syntax errors.  Others, like\nassignments with invalid targets, are caught in the code generation\nphase.\n\nThe compiler package catches some errors in the transformer module.\nBut it seems clearer to write checkers that use the AST to detect\nerrors.\n");
      var1.setline(12);
      String[] var3 = new String[]{"ast", "walk"};
      PyObject[] var5 = imp.importFrom("compiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ast", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("walk", var4);
      var4 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, check$1, (PyObject)null);
      var1.setlocal("check", var6);
      var3 = null;
      var1.setline(19);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("SyntaxErrorChecker", var5, SyntaxErrorChecker$2);
      var1.setlocal("SyntaxErrorChecker", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check$1(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getglobal("SyntaxErrorChecker").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(16);
      var1.getglobal("walk").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(17);
      var3 = var1.getlocal(2).__getattr__("errors");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SyntaxErrorChecker$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A visitor to find syntax errors in the AST."));
      var1.setline(20);
      PyString.fromInterned("A visitor to find syntax errors in the AST.");
      var1.setline(22);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Create new visitor object.\n\n        If optional argument multi is not None, then print messages\n        for each error rather than raising a SyntaxError for the\n        first.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$4, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitAssign$5, (PyObject)null);
      var1.setlocal("visitAssign", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Create new visitor object.\n\n        If optional argument multi is not None, then print messages\n        for each error rather than raising a SyntaxError for the\n        first.\n        ");
      var1.setline(29);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("multi", var3);
      var3 = null;
      var1.setline(30);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"errors", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$4(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(0).__getattr__("errors")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getlocal(0).__getattr__("multi");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(35);
         Py.println(PyString.fromInterned("%s:%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno"), var1.getlocal(2)})));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(37);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("%s (%s:%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
      }
   }

   public PyObject visitAssign$5(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      var1.f_lasti = -1;
      return Py.None;
   }

   public syntax$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"tree", "multi", "v"};
      check$1 = Py.newCode(2, var2, var1, "check", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SyntaxErrorChecker$2 = Py.newCode(0, var2, var1, "SyntaxErrorChecker", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "multi"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 22, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "msg"};
      error$4 = Py.newCode(3, var2, var1, "error", 32, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssign$5 = Py.newCode(2, var2, var1, "visitAssign", 39, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new syntax$py("compiler/syntax$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(syntax$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.check$1(var2, var3);
         case 2:
            return this.SyntaxErrorChecker$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.error$4(var2, var3);
         case 5:
            return this.visitAssign$5(var2, var3);
         default:
            return null;
      }
   }
}

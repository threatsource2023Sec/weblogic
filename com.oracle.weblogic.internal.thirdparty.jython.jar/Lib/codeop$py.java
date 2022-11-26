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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("codeop.py")
public class codeop$py extends PyFunctionTable implements PyRunnable {
   static codeop$py self;
   static final PyCode f$0;
   static final PyCode compile_command$1;
   static final PyCode Compile$2;
   static final PyCode __init__$3;
   static final PyCode __call__$4;
   static final PyCode CommandCompiler$5;
   static final PyCode __init__$6;
   static final PyCode __call__$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities to compile possibly incomplete Python source code.\n\nThis module provides two interfaces, broadly similar to the builtin\nfunction compile(), that take progam text, a filename and a 'mode'\nand:\n\n- Return a code object if the command is complete and valid\n- Return None if the command is incomplete\n- Raise SyntaxError, ValueError or OverflowError if the command is a\n  syntax error (OverflowError and ValueError can be produced by\n  malformed literals).\n\nApproach:\n\nFirst, check if the source consists entirely of blank lines and\ncomments; if so, replace it with 'pass', because the built-in\nparser doesn't always do the right thing for these.\n\nCompile three times: as is, with \\n, and with \\n\\n appended.  If it\ncompiles as is, it's complete.  If it compiles with one \\n appended,\nwe expect more.  If it doesn't compile either way, we compare the\nerror we get when compiling with \\n or \\n\\n appended.  If the errors\nare the same, the code is broken.  But if the errors are different, we\nexpect more.  Not intuitive; not even guaranteed to hold in future\nreleases; but this matches the compiler's behavior from Python 1.4\nthrough 2.2, at least.\n\nCaveat:\n\nIt is possible (but not likely) that the parser stops parsing with a\nsuccessful outcome before reaching the end of the source; in this\ncase, trailing symbols may be ignored instead of causing an error.\nFor example, a backslash followed by two newlines may be followed by\narbitrary garbage.  This will be fixed once the API for the parser is\nbetter.\n\nThe two interfaces are:\n\ncompile_command(source, filename, symbol):\n\n    Compiles a single command in the manner described above.\n\nCommandCompiler():\n\n    Instances of this class have __call__ methods identical in\n    signature to compile_command; the difference is that if the\n    instance compiles program text containing a __future__ statement,\n    the instance 'remembers' and compiles all subsequent program texts\n    with the statement in force.\n\nThe module also provides another class:\n\nCompile():\n\n    Instances of this class act like the built-in function compile,\n    but with 'memory' in the sense described above.\n"));
      var1.setline(57);
      PyString.fromInterned("Utilities to compile possibly incomplete Python source code.\n\nThis module provides two interfaces, broadly similar to the builtin\nfunction compile(), that take progam text, a filename and a 'mode'\nand:\n\n- Return a code object if the command is complete and valid\n- Return None if the command is incomplete\n- Raise SyntaxError, ValueError or OverflowError if the command is a\n  syntax error (OverflowError and ValueError can be produced by\n  malformed literals).\n\nApproach:\n\nFirst, check if the source consists entirely of blank lines and\ncomments; if so, replace it with 'pass', because the built-in\nparser doesn't always do the right thing for these.\n\nCompile three times: as is, with \\n, and with \\n\\n appended.  If it\ncompiles as is, it's complete.  If it compiles with one \\n appended,\nwe expect more.  If it doesn't compile either way, we compare the\nerror we get when compiling with \\n or \\n\\n appended.  If the errors\nare the same, the code is broken.  But if the errors are different, we\nexpect more.  Not intuitive; not even guaranteed to hold in future\nreleases; but this matches the compiler's behavior from Python 1.4\nthrough 2.2, at least.\n\nCaveat:\n\nIt is possible (but not likely) that the parser stops parsing with a\nsuccessful outcome before reaching the end of the source; in this\ncase, trailing symbols may be ignored instead of causing an error.\nFor example, a backslash followed by two newlines may be followed by\narbitrary garbage.  This will be fixed once the API for the parser is\nbetter.\n\nThe two interfaces are:\n\ncompile_command(source, filename, symbol):\n\n    Compiles a single command in the manner described above.\n\nCommandCompiler():\n\n    Instances of this class have __call__ methods identical in\n    signature to compile_command; the difference is that if the\n    instance compiles program text containing a __future__ statement,\n    the instance 'remembers' and compiles all subsequent program texts\n    with the statement in force.\n\nThe module also provides another class:\n\nCompile():\n\n    Instances of this class act like the built-in function compile,\n    but with 'memory' in the sense described above.\n");
      var1.setline(60);
      String[] var3 = new String[]{"Py", "CompilerFlags", "CompileMode"};
      PyObject[] var5 = imp.importFrom("org.python.core", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("Py", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CompilerFlags", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("CompileMode", var4);
      var4 = null;
      var1.setline(61);
      var3 = new String[]{"PyCF_DONT_IMPLY_DEDENT"};
      var5 = imp.importFrom("org.python.core.CompilerFlags", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("PyCF_DONT_IMPLY_DEDENT", var4);
      var4 = null;
      var1.setline(65);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("compile_command"), PyString.fromInterned("Compile"), PyString.fromInterned("CommandCompiler")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(67);
      var5 = new PyObject[]{PyString.fromInterned("<input>"), PyString.fromInterned("single")};
      PyFunction var7 = new PyFunction(var1.f_globals, var5, compile_command$1, PyString.fromInterned("Compile a command and determine whether it is incomplete.\n\n    Arguments:\n\n    source -- the source string; may contain \\n characters\n    filename -- optional filename from which source was read; default\n                \"<input>\"\n    symbol -- optional grammar start symbol; \"single\" (default) or \"eval\"\n\n    Return value / exceptions raised:\n\n    - Return a code object if the command is complete and valid\n    - Return None if the command is incomplete\n    - Raise SyntaxError, ValueError or OverflowError if the command is a\n      syntax error (OverflowError and ValueError can be produced by\n      malformed literals).\n    "));
      var1.setlocal("compile_command", var7);
      var3 = null;
      var1.setline(90);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Compile", var5, Compile$2);
      var1.setlocal("Compile", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(102);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("CommandCompiler", var5, CommandCompiler$5);
      var1.setlocal("CommandCompiler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile_command$1(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Compile a command and determine whether it is incomplete.\n\n    Arguments:\n\n    source -- the source string; may contain \\n characters\n    filename -- optional filename from which source was read; default\n                \"<input>\"\n    symbol -- optional grammar start symbol; \"single\" (default) or \"eval\"\n\n    Return value / exceptions raised:\n\n    - Return a code object if the command is complete and valid\n    - Return None if the command is incomplete\n    - Raise SyntaxError, ValueError or OverflowError if the command is a\n      syntax error (OverflowError and ValueError can be produced by\n      malformed literals).\n    ");
      var1.setline(85);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("single"), PyString.fromInterned("eval")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("symbol arg must be either single or eval"));
      } else {
         var1.setline(87);
         var3 = var1.getglobal("CompileMode").__getattr__("getMode").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(88);
         var10000 = var1.getglobal("Py").__getattr__("compile_command_flags");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("Py").__getattr__("getCompilerFlags").__call__(var2), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Compile$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Instances of this class behave much like the built-in compile\n    function, but if one is used to compile text containing a future\n    statement, it \"remembers\" and compiles all subsequent program texts\n    with the statement in force."));
      var1.setline(94);
      PyString.fromInterned("Instances of this class behave much like the built-in compile\n    function, but if one is used to compile text containing a future\n    statement, it \"remembers\" and compiles all subsequent program texts\n    with the statement in force.");
      var1.setline(95);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(98);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$4, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getglobal("CompilerFlags").__call__(var2);
      var1.getlocal(0).__setattr__("_cflags", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getglobal("CompileMode").__getattr__("getMode").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("Py").__getattr__("compile_flags").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("_cflags"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CommandCompiler$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Instances of this class have __call__ methods identical in\n    signature to compile_command; the difference is that if the\n    instance compiles program text containing a __future__ statement,\n    the instance 'remembers' and compiles all subsequent program texts\n    with the statement in force."));
      var1.setline(107);
      PyString.fromInterned("Instances of this class have __call__ methods identical in\n    signature to compile_command; the difference is that if the\n    instance compiles program text containing a __future__ statement,\n    the instance 'remembers' and compiles all subsequent program texts\n    with the statement in force.");
      var1.setline(109);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(112);
      var3 = new PyObject[]{PyString.fromInterned("<input>"), PyString.fromInterned("single")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$7, PyString.fromInterned("Compile a command and determine whether it is incomplete.\n\n        Arguments:\n\n        source -- the source string; may contain \\n characters\n        filename -- optional filename from which source was read;\n                    default \"<input>\"\n        symbol -- optional grammar start symbol; \"single\" (default) or\n                  \"eval\"\n\n        Return value / exceptions raised:\n\n        - Return a code object if the command is complete and valid\n        - Return None if the command is incomplete\n        - Raise SyntaxError, ValueError or OverflowError if the command is a\n          syntax error (OverflowError and ValueError can be produced by\n          malformed literals).\n        "));
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getglobal("CompilerFlags").__call__(var2);
      var1.getlocal(0).__setattr__("_cflags", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$7(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyString.fromInterned("Compile a command and determine whether it is incomplete.\n\n        Arguments:\n\n        source -- the source string; may contain \\n characters\n        filename -- optional filename from which source was read;\n                    default \"<input>\"\n        symbol -- optional grammar start symbol; \"single\" (default) or\n                  \"eval\"\n\n        Return value / exceptions raised:\n\n        - Return a code object if the command is complete and valid\n        - Return None if the command is incomplete\n        - Raise SyntaxError, ValueError or OverflowError if the command is a\n          syntax error (OverflowError and ValueError can be produced by\n          malformed literals).\n        ");
      var1.setline(131);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("single"), PyString.fromInterned("eval")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("symbol arg must be either single or eval"));
      } else {
         var1.setline(133);
         var3 = var1.getglobal("CompileMode").__getattr__("getMode").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(134);
         var10000 = var1.getglobal("Py").__getattr__("compile_command_flags");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("_cflags"), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public codeop$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"source", "filename", "symbol"};
      compile_command$1 = Py.newCode(3, var2, var1, "compile_command", 67, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Compile$2 = Py.newCode(0, var2, var1, "Compile", 90, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$3 = Py.newCode(1, var2, var1, "__init__", 95, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "filename", "symbol"};
      __call__$4 = Py.newCode(4, var2, var1, "__call__", 98, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CommandCompiler$5 = Py.newCode(0, var2, var1, "CommandCompiler", 102, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$6 = Py.newCode(1, var2, var1, "__init__", 109, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "filename", "symbol"};
      __call__$7 = Py.newCode(4, var2, var1, "__call__", 112, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new codeop$py("codeop$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(codeop$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.compile_command$1(var2, var3);
         case 2:
            return this.Compile$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__call__$4(var2, var3);
         case 5:
            return this.CommandCompiler$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.__call__$7(var2, var3);
         default:
            return null;
      }
   }
}

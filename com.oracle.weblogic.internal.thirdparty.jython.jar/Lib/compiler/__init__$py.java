import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/__init__.py")
public class compiler$py extends PyFunctionTable implements PyRunnable {
   static compiler$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Package for parsing and compiling Python source code\n\nThere are several functions defined at the top level that are imported\nfrom modules contained in the package.\n\nparse(buf, mode=\"exec\") -> AST\n    Converts a string containing Python source code to an abstract\n    syntax tree (AST).  The AST is defined in compiler.ast.\n\nparseFile(path) -> AST\n    The same as parse(open(path))\n\nwalk(ast, visitor, verbose=None)\n    Does a pre-order walk over the ast using the visitor instance.\n    See compiler.visitor for details.\n\ncompile(source, filename, mode, flags=None, dont_inherit=None)\n    Returns a code object.  A replacement for the builtin compile() function.\n\ncompileFile(filename)\n    Generates a .pyc file by compiling filename.\n"));
      var1.setline(22);
      PyString.fromInterned("Package for parsing and compiling Python source code\n\nThere are several functions defined at the top level that are imported\nfrom modules contained in the package.\n\nparse(buf, mode=\"exec\") -> AST\n    Converts a string containing Python source code to an abstract\n    syntax tree (AST).  The AST is defined in compiler.ast.\n\nparseFile(path) -> AST\n    The same as parse(open(path))\n\nwalk(ast, visitor, verbose=None)\n    Does a pre-order walk over the ast using the visitor instance.\n    See compiler.visitor for details.\n\ncompile(source, filename, mode, flags=None, dont_inherit=None)\n    Returns a code object.  A replacement for the builtin compile() function.\n\ncompileFile(filename)\n    Generates a .pyc file by compiling filename.\n");
      var1.setline(24);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(26);
      PyObject var10000 = var1.getname("warnings").__getattr__("warn");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("The compiler package is deprecated and removed in Python 3.x."), var1.getname("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(29);
      String[] var6 = new String[]{"parse", "parseFile"};
      var5 = imp.importFrom("compiler.transformer", var6, var1, -1);
      PyObject var7 = var5[0];
      var1.setlocal("parse", var7);
      var4 = null;
      var7 = var5[1];
      var1.setlocal("parseFile", var7);
      var4 = null;
      var1.setline(30);
      var6 = new String[]{"walk"};
      var5 = imp.importFrom("compiler.visitor", var6, var1, -1);
      var7 = var5[0];
      var1.setlocal("walk", var7);
      var4 = null;
      var1.setline(31);
      var6 = new String[]{"compile", "compileFile"};
      var5 = imp.importFrom("compiler.pycodegen", var6, var1, -1);
      var7 = var5[0];
      var1.setlocal("compile", var7);
      var4 = null;
      var7 = var5[1];
      var1.setlocal("compileFile", var7);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public compiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new compiler$py("compiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(compiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}

package lib2to3;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/pygram.py")
public class pygram$py extends PyFunctionTable implements PyRunnable {
   static pygram$py self;
   static final PyCode f$0;
   static final PyCode Symbols$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Export the Python grammar and symbols."));
      var1.setline(4);
      PyString.fromInterned("Export the Python grammar and symbols.");
      var1.setline(7);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(10);
      String[] var5 = new String[]{"token"};
      PyObject[] var6 = imp.importFrom("pgen2", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"driver"};
      var6 = imp.importFrom("pgen2", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("driver", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"pytree"};
      var6 = imp.importFrom("", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(15);
      var3 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("__file__")), (PyObject)PyString.fromInterned("Grammar.txt"));
      var1.setlocal("_GRAMMAR_FILE", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("__file__")), (PyObject)PyString.fromInterned("PatternGrammar.txt"));
      var1.setlocal("_PATTERN_GRAMMAR_FILE", var3);
      var3 = null;
      var1.setline(20);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Symbols", var6, Symbols$1);
      var1.setlocal("Symbols", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(32);
      var3 = var1.getname("driver").__getattr__("load_grammar").__call__(var2, var1.getname("_GRAMMAR_FILE"));
      var1.setlocal("python_grammar", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getname("Symbols").__call__(var2, var1.getname("python_grammar"));
      var1.setlocal("python_symbols", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getname("python_grammar").__getattr__("copy").__call__(var2);
      var1.setlocal("python_grammar_no_print_statement", var3);
      var3 = null;
      var1.setline(37);
      var1.getname("python_grammar_no_print_statement").__getattr__("keywords").__delitem__((PyObject)PyString.fromInterned("print"));
      var1.setline(39);
      var3 = var1.getname("driver").__getattr__("load_grammar").__call__(var2, var1.getname("_PATTERN_GRAMMAR_FILE"));
      var1.setlocal("pattern_grammar", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getname("Symbols").__call__(var2, var1.getname("pattern_grammar"));
      var1.setlocal("pattern_symbols", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Symbols$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Initializer.\n\n        Creates an attribute for each grammar symbol (nonterminal),\n        whose value is the symbol's type (an int >= 256).\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Initializer.\n\n        Creates an attribute for each grammar symbol (nonterminal),\n        whose value is the symbol's type (an int >= 256).\n        ");
      var1.setline(28);
      PyObject var3 = var1.getlocal(1).__getattr__("symbol2number").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(28);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(29);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
      }
   }

   public pygram$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Symbols$1 = Py.newCode(0, var2, var1, "Symbols", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "grammar", "name", "symbol"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 22, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pygram$py("lib2to3/pygram$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pygram$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Symbols$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

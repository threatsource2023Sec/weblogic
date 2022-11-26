package lib2to3.pgen2;

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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/pgen2/token.py")
public class token$py extends PyFunctionTable implements PyRunnable {
   static lib2to3.pgen2.token$py self;
   static final PyCode f$0;
   static final PyCode ISTERMINAL$1;
   static final PyCode ISNONTERMINAL$2;
   static final PyCode ISEOF$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Token constants (from \"token.h\")."));
      var1.setline(3);
      PyString.fromInterned("Token constants (from \"token.h\").");
      var1.setline(9);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("ENDMARKER", var3);
      var3 = null;
      var1.setline(10);
      var3 = Py.newInteger(1);
      var1.setlocal("NAME", var3);
      var3 = null;
      var1.setline(11);
      var3 = Py.newInteger(2);
      var1.setlocal("NUMBER", var3);
      var3 = null;
      var1.setline(12);
      var3 = Py.newInteger(3);
      var1.setlocal("STRING", var3);
      var3 = null;
      var1.setline(13);
      var3 = Py.newInteger(4);
      var1.setlocal("NEWLINE", var3);
      var3 = null;
      var1.setline(14);
      var3 = Py.newInteger(5);
      var1.setlocal("INDENT", var3);
      var3 = null;
      var1.setline(15);
      var3 = Py.newInteger(6);
      var1.setlocal("DEDENT", var3);
      var3 = null;
      var1.setline(16);
      var3 = Py.newInteger(7);
      var1.setlocal("LPAR", var3);
      var3 = null;
      var1.setline(17);
      var3 = Py.newInteger(8);
      var1.setlocal("RPAR", var3);
      var3 = null;
      var1.setline(18);
      var3 = Py.newInteger(9);
      var1.setlocal("LSQB", var3);
      var3 = null;
      var1.setline(19);
      var3 = Py.newInteger(10);
      var1.setlocal("RSQB", var3);
      var3 = null;
      var1.setline(20);
      var3 = Py.newInteger(11);
      var1.setlocal("COLON", var3);
      var3 = null;
      var1.setline(21);
      var3 = Py.newInteger(12);
      var1.setlocal("COMMA", var3);
      var3 = null;
      var1.setline(22);
      var3 = Py.newInteger(13);
      var1.setlocal("SEMI", var3);
      var3 = null;
      var1.setline(23);
      var3 = Py.newInteger(14);
      var1.setlocal("PLUS", var3);
      var3 = null;
      var1.setline(24);
      var3 = Py.newInteger(15);
      var1.setlocal("MINUS", var3);
      var3 = null;
      var1.setline(25);
      var3 = Py.newInteger(16);
      var1.setlocal("STAR", var3);
      var3 = null;
      var1.setline(26);
      var3 = Py.newInteger(17);
      var1.setlocal("SLASH", var3);
      var3 = null;
      var1.setline(27);
      var3 = Py.newInteger(18);
      var1.setlocal("VBAR", var3);
      var3 = null;
      var1.setline(28);
      var3 = Py.newInteger(19);
      var1.setlocal("AMPER", var3);
      var3 = null;
      var1.setline(29);
      var3 = Py.newInteger(20);
      var1.setlocal("LESS", var3);
      var3 = null;
      var1.setline(30);
      var3 = Py.newInteger(21);
      var1.setlocal("GREATER", var3);
      var3 = null;
      var1.setline(31);
      var3 = Py.newInteger(22);
      var1.setlocal("EQUAL", var3);
      var3 = null;
      var1.setline(32);
      var3 = Py.newInteger(23);
      var1.setlocal("DOT", var3);
      var3 = null;
      var1.setline(33);
      var3 = Py.newInteger(24);
      var1.setlocal("PERCENT", var3);
      var3 = null;
      var1.setline(34);
      var3 = Py.newInteger(25);
      var1.setlocal("BACKQUOTE", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(26);
      var1.setlocal("LBRACE", var3);
      var3 = null;
      var1.setline(36);
      var3 = Py.newInteger(27);
      var1.setlocal("RBRACE", var3);
      var3 = null;
      var1.setline(37);
      var3 = Py.newInteger(28);
      var1.setlocal("EQEQUAL", var3);
      var3 = null;
      var1.setline(38);
      var3 = Py.newInteger(29);
      var1.setlocal("NOTEQUAL", var3);
      var3 = null;
      var1.setline(39);
      var3 = Py.newInteger(30);
      var1.setlocal("LESSEQUAL", var3);
      var3 = null;
      var1.setline(40);
      var3 = Py.newInteger(31);
      var1.setlocal("GREATEREQUAL", var3);
      var3 = null;
      var1.setline(41);
      var3 = Py.newInteger(32);
      var1.setlocal("TILDE", var3);
      var3 = null;
      var1.setline(42);
      var3 = Py.newInteger(33);
      var1.setlocal("CIRCUMFLEX", var3);
      var3 = null;
      var1.setline(43);
      var3 = Py.newInteger(34);
      var1.setlocal("LEFTSHIFT", var3);
      var3 = null;
      var1.setline(44);
      var3 = Py.newInteger(35);
      var1.setlocal("RIGHTSHIFT", var3);
      var3 = null;
      var1.setline(45);
      var3 = Py.newInteger(36);
      var1.setlocal("DOUBLESTAR", var3);
      var3 = null;
      var1.setline(46);
      var3 = Py.newInteger(37);
      var1.setlocal("PLUSEQUAL", var3);
      var3 = null;
      var1.setline(47);
      var3 = Py.newInteger(38);
      var1.setlocal("MINEQUAL", var3);
      var3 = null;
      var1.setline(48);
      var3 = Py.newInteger(39);
      var1.setlocal("STAREQUAL", var3);
      var3 = null;
      var1.setline(49);
      var3 = Py.newInteger(40);
      var1.setlocal("SLASHEQUAL", var3);
      var3 = null;
      var1.setline(50);
      var3 = Py.newInteger(41);
      var1.setlocal("PERCENTEQUAL", var3);
      var3 = null;
      var1.setline(51);
      var3 = Py.newInteger(42);
      var1.setlocal("AMPEREQUAL", var3);
      var3 = null;
      var1.setline(52);
      var3 = Py.newInteger(43);
      var1.setlocal("VBAREQUAL", var3);
      var3 = null;
      var1.setline(53);
      var3 = Py.newInteger(44);
      var1.setlocal("CIRCUMFLEXEQUAL", var3);
      var3 = null;
      var1.setline(54);
      var3 = Py.newInteger(45);
      var1.setlocal("LEFTSHIFTEQUAL", var3);
      var3 = null;
      var1.setline(55);
      var3 = Py.newInteger(46);
      var1.setlocal("RIGHTSHIFTEQUAL", var3);
      var3 = null;
      var1.setline(56);
      var3 = Py.newInteger(47);
      var1.setlocal("DOUBLESTAREQUAL", var3);
      var3 = null;
      var1.setline(57);
      var3 = Py.newInteger(48);
      var1.setlocal("DOUBLESLASH", var3);
      var3 = null;
      var1.setline(58);
      var3 = Py.newInteger(49);
      var1.setlocal("DOUBLESLASHEQUAL", var3);
      var3 = null;
      var1.setline(59);
      var3 = Py.newInteger(50);
      var1.setlocal("AT", var3);
      var3 = null;
      var1.setline(60);
      var3 = Py.newInteger(51);
      var1.setlocal("OP", var3);
      var3 = null;
      var1.setline(61);
      var3 = Py.newInteger(52);
      var1.setlocal("COMMENT", var3);
      var3 = null;
      var1.setline(62);
      var3 = Py.newInteger(53);
      var1.setlocal("NL", var3);
      var3 = null;
      var1.setline(63);
      var3 = Py.newInteger(54);
      var1.setlocal("RARROW", var3);
      var3 = null;
      var1.setline(64);
      var3 = Py.newInteger(55);
      var1.setlocal("ERRORTOKEN", var3);
      var3 = null;
      var1.setline(65);
      var3 = Py.newInteger(56);
      var1.setlocal("N_TOKENS", var3);
      var3 = null;
      var1.setline(66);
      var3 = Py.newInteger(256);
      var1.setlocal("NT_OFFSET", var3);
      var3 = null;
      var1.setline(69);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("tok_name", var8);
      var3 = null;
      var1.setline(70);
      PyObject var9 = var1.getname("globals").__call__(var2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(70);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(75);
            PyObject[] var10 = Py.EmptyObjects;
            PyFunction var11 = new PyFunction(var1.f_globals, var10, ISTERMINAL$1, (PyObject)null);
            var1.setlocal("ISTERMINAL", var11);
            var3 = null;
            var1.setline(78);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, ISNONTERMINAL$2, (PyObject)null);
            var1.setlocal("ISNONTERMINAL", var11);
            var3 = null;
            var1.setline(81);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, ISEOF$3, (PyObject)null);
            var1.setlocal("ISEOF", var11);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal("_name", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("_value", var6);
         var6 = null;
         var1.setline(71);
         PyObject var7 = var1.getname("type").__call__(var2, var1.getname("_value"));
         PyObject var10000 = var7._is(var1.getname("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(72);
            var7 = var1.getname("_name");
            var1.getname("tok_name").__setitem__(var1.getname("_value"), var7);
            var5 = null;
         }
      }
   }

   public PyObject ISTERMINAL$1(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(var1.getglobal("NT_OFFSET"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ISNONTERMINAL$2(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ge(var1.getglobal("NT_OFFSET"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ISEOF$3(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getglobal("ENDMARKER"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public token$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x"};
      ISTERMINAL$1 = Py.newCode(1, var2, var1, "ISTERMINAL", 75, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ISNONTERMINAL$2 = Py.newCode(1, var2, var1, "ISNONTERMINAL", 78, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ISEOF$3 = Py.newCode(1, var2, var1, "ISEOF", 81, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new lib2to3.pgen2.token$py("lib2to3/pgen2/token$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(lib2to3.pgen2.token$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ISTERMINAL$1(var2, var3);
         case 2:
            return this.ISNONTERMINAL$2(var2, var3);
         case 3:
            return this.ISEOF$3(var2, var3);
         default:
            return null;
      }
   }
}

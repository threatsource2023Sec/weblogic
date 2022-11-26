import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("token.py")
public class token$py extends PyFunctionTable implements PyRunnable {
   static token$py self;
   static final PyCode f$0;
   static final PyCode ISTERMINAL$1;
   static final PyCode ISNONTERMINAL$2;
   static final PyCode ISEOF$3;
   static final PyCode main$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Token constants (from \"token.h\")."));
      var1.setline(3);
      PyString.fromInterned("Token constants (from \"token.h\").");
      var1.setline(13);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("ENDMARKER", var3);
      var3 = null;
      var1.setline(14);
      var3 = Py.newInteger(1);
      var1.setlocal("NAME", var3);
      var3 = null;
      var1.setline(15);
      var3 = Py.newInteger(2);
      var1.setlocal("NUMBER", var3);
      var3 = null;
      var1.setline(16);
      var3 = Py.newInteger(3);
      var1.setlocal("STRING", var3);
      var3 = null;
      var1.setline(17);
      var3 = Py.newInteger(4);
      var1.setlocal("NEWLINE", var3);
      var3 = null;
      var1.setline(18);
      var3 = Py.newInteger(5);
      var1.setlocal("INDENT", var3);
      var3 = null;
      var1.setline(19);
      var3 = Py.newInteger(6);
      var1.setlocal("DEDENT", var3);
      var3 = null;
      var1.setline(20);
      var3 = Py.newInteger(7);
      var1.setlocal("LPAR", var3);
      var3 = null;
      var1.setline(21);
      var3 = Py.newInteger(8);
      var1.setlocal("RPAR", var3);
      var3 = null;
      var1.setline(22);
      var3 = Py.newInteger(9);
      var1.setlocal("LSQB", var3);
      var3 = null;
      var1.setline(23);
      var3 = Py.newInteger(10);
      var1.setlocal("RSQB", var3);
      var3 = null;
      var1.setline(24);
      var3 = Py.newInteger(11);
      var1.setlocal("COLON", var3);
      var3 = null;
      var1.setline(25);
      var3 = Py.newInteger(12);
      var1.setlocal("COMMA", var3);
      var3 = null;
      var1.setline(26);
      var3 = Py.newInteger(13);
      var1.setlocal("SEMI", var3);
      var3 = null;
      var1.setline(27);
      var3 = Py.newInteger(14);
      var1.setlocal("PLUS", var3);
      var3 = null;
      var1.setline(28);
      var3 = Py.newInteger(15);
      var1.setlocal("MINUS", var3);
      var3 = null;
      var1.setline(29);
      var3 = Py.newInteger(16);
      var1.setlocal("STAR", var3);
      var3 = null;
      var1.setline(30);
      var3 = Py.newInteger(17);
      var1.setlocal("SLASH", var3);
      var3 = null;
      var1.setline(31);
      var3 = Py.newInteger(18);
      var1.setlocal("VBAR", var3);
      var3 = null;
      var1.setline(32);
      var3 = Py.newInteger(19);
      var1.setlocal("AMPER", var3);
      var3 = null;
      var1.setline(33);
      var3 = Py.newInteger(20);
      var1.setlocal("LESS", var3);
      var3 = null;
      var1.setline(34);
      var3 = Py.newInteger(21);
      var1.setlocal("GREATER", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(22);
      var1.setlocal("EQUAL", var3);
      var3 = null;
      var1.setline(36);
      var3 = Py.newInteger(23);
      var1.setlocal("DOT", var3);
      var3 = null;
      var1.setline(37);
      var3 = Py.newInteger(24);
      var1.setlocal("PERCENT", var3);
      var3 = null;
      var1.setline(38);
      var3 = Py.newInteger(25);
      var1.setlocal("BACKQUOTE", var3);
      var3 = null;
      var1.setline(39);
      var3 = Py.newInteger(26);
      var1.setlocal("LBRACE", var3);
      var3 = null;
      var1.setline(40);
      var3 = Py.newInteger(27);
      var1.setlocal("RBRACE", var3);
      var3 = null;
      var1.setline(41);
      var3 = Py.newInteger(28);
      var1.setlocal("EQEQUAL", var3);
      var3 = null;
      var1.setline(42);
      var3 = Py.newInteger(29);
      var1.setlocal("NOTEQUAL", var3);
      var3 = null;
      var1.setline(43);
      var3 = Py.newInteger(30);
      var1.setlocal("LESSEQUAL", var3);
      var3 = null;
      var1.setline(44);
      var3 = Py.newInteger(31);
      var1.setlocal("GREATEREQUAL", var3);
      var3 = null;
      var1.setline(45);
      var3 = Py.newInteger(32);
      var1.setlocal("TILDE", var3);
      var3 = null;
      var1.setline(46);
      var3 = Py.newInteger(33);
      var1.setlocal("CIRCUMFLEX", var3);
      var3 = null;
      var1.setline(47);
      var3 = Py.newInteger(34);
      var1.setlocal("LEFTSHIFT", var3);
      var3 = null;
      var1.setline(48);
      var3 = Py.newInteger(35);
      var1.setlocal("RIGHTSHIFT", var3);
      var3 = null;
      var1.setline(49);
      var3 = Py.newInteger(36);
      var1.setlocal("DOUBLESTAR", var3);
      var3 = null;
      var1.setline(50);
      var3 = Py.newInteger(37);
      var1.setlocal("PLUSEQUAL", var3);
      var3 = null;
      var1.setline(51);
      var3 = Py.newInteger(38);
      var1.setlocal("MINEQUAL", var3);
      var3 = null;
      var1.setline(52);
      var3 = Py.newInteger(39);
      var1.setlocal("STAREQUAL", var3);
      var3 = null;
      var1.setline(53);
      var3 = Py.newInteger(40);
      var1.setlocal("SLASHEQUAL", var3);
      var3 = null;
      var1.setline(54);
      var3 = Py.newInteger(41);
      var1.setlocal("PERCENTEQUAL", var3);
      var3 = null;
      var1.setline(55);
      var3 = Py.newInteger(42);
      var1.setlocal("AMPEREQUAL", var3);
      var3 = null;
      var1.setline(56);
      var3 = Py.newInteger(43);
      var1.setlocal("VBAREQUAL", var3);
      var3 = null;
      var1.setline(57);
      var3 = Py.newInteger(44);
      var1.setlocal("CIRCUMFLEXEQUAL", var3);
      var3 = null;
      var1.setline(58);
      var3 = Py.newInteger(45);
      var1.setlocal("LEFTSHIFTEQUAL", var3);
      var3 = null;
      var1.setline(59);
      var3 = Py.newInteger(46);
      var1.setlocal("RIGHTSHIFTEQUAL", var3);
      var3 = null;
      var1.setline(60);
      var3 = Py.newInteger(47);
      var1.setlocal("DOUBLESTAREQUAL", var3);
      var3 = null;
      var1.setline(61);
      var3 = Py.newInteger(48);
      var1.setlocal("DOUBLESLASH", var3);
      var3 = null;
      var1.setline(62);
      var3 = Py.newInteger(49);
      var1.setlocal("DOUBLESLASHEQUAL", var3);
      var3 = null;
      var1.setline(63);
      var3 = Py.newInteger(50);
      var1.setlocal("AT", var3);
      var3 = null;
      var1.setline(64);
      var3 = Py.newInteger(51);
      var1.setlocal("OP", var3);
      var3 = null;
      var1.setline(65);
      var3 = Py.newInteger(52);
      var1.setlocal("ERRORTOKEN", var3);
      var3 = null;
      var1.setline(66);
      var3 = Py.newInteger(53);
      var1.setlocal("N_TOKENS", var3);
      var3 = null;
      var1.setline(67);
      var3 = Py.newInteger(256);
      var1.setlocal("NT_OFFSET", var3);
      var3 = null;
      var1.setline(70);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("tok_name", var8);
      var3 = null;
      var1.setline(71);
      PyObject var9 = var1.getname("globals").__call__(var2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(71);
         PyObject var4 = var9.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(74);
            var1.dellocal("_name");
            var1.dellocal("_value");
            var1.setline(77);
            PyObject[] var10 = Py.EmptyObjects;
            PyFunction var11 = new PyFunction(var1.f_globals, var10, ISTERMINAL$1, (PyObject)null);
            var1.setlocal("ISTERMINAL", var11);
            var3 = null;
            var1.setline(80);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, ISNONTERMINAL$2, (PyObject)null);
            var1.setlocal("ISNONTERMINAL", var11);
            var3 = null;
            var1.setline(83);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, ISEOF$3, (PyObject)null);
            var1.setlocal("ISEOF", var11);
            var3 = null;
            var1.setline(87);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, main$4, (PyObject)null);
            var1.setlocal("main", var11);
            var3 = null;
            var1.setline(141);
            var9 = var1.getname("__name__");
            var10000 = var9._eq(PyString.fromInterned("__main__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(142);
               var1.getname("main").__call__(var2);
            }

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
         var1.setline(72);
         PyObject var7 = var1.getname("type").__call__(var2, var1.getname("_value"));
         var10000 = var7._is(var1.getname("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(73);
            var7 = var1.getname("_name");
            var1.getname("tok_name").__setitem__(var1.getname("_value"), var7);
            var5 = null;
         }
      }
   }

   public PyObject ISTERMINAL$1(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(var1.getglobal("NT_OFFSET"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ISNONTERMINAL$2(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ge(var1.getglobal("NT_OFFSET"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ISEOF$3(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getglobal("ENDMARKER"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject main$4(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(89);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(1).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(91);
      Object var10000 = var1.getlocal(2);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("Include/token.h");
      }

      Object var12 = var10000;
      var1.setlocal(3, (PyObject)var12);
      var3 = null;
      var1.setline(92);
      PyString var13 = PyString.fromInterned("Lib/token.py");
      var1.setlocal(4, var13);
      var3 = null;
      var1.setline(93);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var17 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var17.__nonzero__()) {
         var1.setline(94);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      }

      PyObject var4;
      PyException var14;
      try {
         var1.setline(96);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var11) {
         var14 = Py.setException(var11, var1);
         if (!var14.match(var1.getglobal("IOError"))) {
            throw var14;
         }

         var4 = var14.value;
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(98);
         var1.getlocal(1).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("I/O error: %s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(6))));
         var1.setline(99);
         var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(100);
      var3 = var1.getlocal(5).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(101);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(102);
      var3 = var1.getlocal(0).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#define[ \t][ \t]*([A-Z0-9][A-Z0-9_]*)[ \t][ \t]*([0-9][0-9]*)"), (PyObject)var1.getlocal(0).__getattr__("IGNORECASE"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(105);
      PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(9, var15);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(7).__iter__();

      while(true) {
         var1.setline(106);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(112);
            var3 = var1.getlocal(9).__getattr__("keys").__call__(var2);
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(113);
            var1.getlocal(14).__getattr__("sort").__call__(var2);

            try {
               var1.setline(116);
               var3 = var1.getglobal("open").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var3);
               var3 = null;
            } catch (Throwable var10) {
               var14 = Py.setException(var10, var1);
               if (!var14.match(var1.getglobal("IOError"))) {
                  throw var14;
               }

               var4 = var14.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(118);
               var1.getlocal(1).__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("I/O error: %s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(6))));
               var1.setline(119);
               var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            }

            var1.setline(120);
            var3 = var1.getlocal(5).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setlocal(15, var3);
            var3 = null;
            var1.setline(121);
            var1.getlocal(5).__getattr__("close").__call__(var2);

            try {
               var1.setline(123);
               var3 = var1.getlocal(15).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#--start constants--"))._add(Py.newInteger(1));
               var1.setlocal(16, var3);
               var3 = null;
               var1.setline(124);
               var3 = var1.getlocal(15).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#--end constants--"));
               var1.setlocal(17, var3);
               var3 = null;
            } catch (Throwable var9) {
               var14 = Py.setException(var9, var1);
               if (!var14.match(var1.getglobal("ValueError"))) {
                  throw var14;
               }

               var1.setline(126);
               var1.getlocal(1).__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("target does not contain format markers"));
               var1.setline(127);
               var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
            }

            var1.setline(128);
            PyList var16 = new PyList(Py.EmptyObjects);
            var1.setlocal(7, var16);
            var3 = null;
            var1.setline(129);
            var3 = var1.getlocal(14).__iter__();

            while(true) {
               var1.setline(129);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(131);
                  var3 = var1.getlocal(7);
                  var1.getlocal(15).__setslice__(var1.getlocal(16), var1.getlocal(17), (PyObject)null, var3);
                  var3 = null;

                  try {
                     var1.setline(133);
                     var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w"));
                     var1.setlocal(5, var3);
                     var3 = null;
                  } catch (Throwable var8) {
                     var14 = Py.setException(var8, var1);
                     if (!var14.match(var1.getglobal("IOError"))) {
                        throw var14;
                     }

                     var4 = var14.value;
                     var1.setlocal(6, var4);
                     var4 = null;
                     var1.setline(135);
                     var1.getlocal(1).__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("I/O error: %s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(6))));
                     var1.setline(136);
                     var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
                  }

                  var1.setline(137);
                  var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(15)));
                  var1.setline(138);
                  var1.getlocal(5).__getattr__("close").__call__(var2);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(13, var4);
               var1.setline(130);
               var1.getlocal(7).__getattr__("append").__call__(var2, PyString.fromInterned("%s = %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(9).__getitem__(var1.getlocal(13)), var1.getlocal(13)})));
            }
         }

         var1.setlocal(10, var4);
         var1.setline(107);
         PyObject var5 = var1.getlocal(8).__getattr__("match").__call__(var2, var1.getlocal(10));
         var1.setlocal(11, var5);
         var5 = null;
         var1.setline(108);
         if (var1.getlocal(11).__nonzero__()) {
            var1.setline(109);
            var5 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(12, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(13, var7);
            var7 = null;
            var5 = null;
            var1.setline(110);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(13));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(111);
            var5 = var1.getlocal(12);
            var1.getlocal(9).__setitem__(var1.getlocal(13), var5);
            var5 = null;
         }
      }
   }

   public token$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x"};
      ISTERMINAL$1 = Py.newCode(1, var2, var1, "ISTERMINAL", 77, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ISNONTERMINAL$2 = Py.newCode(1, var2, var1, "ISNONTERMINAL", 80, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ISEOF$3 = Py.newCode(1, var2, var1, "ISEOF", 83, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"re", "sys", "args", "inFileName", "outFileName", "fp", "err", "lines", "prog", "tokens", "line", "match", "name", "val", "keys", "format", "start", "end"};
      main$4 = Py.newCode(0, var2, var1, "main", 87, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new token$py("token$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(token$py.class);
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
         case 4:
            return this.main$4(var2, var3);
         default:
            return null;
      }
   }
}

package lib2to3.pgen2;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/pgen2/literals.py")
public class literals$py extends PyFunctionTable implements PyRunnable {
   static literals$py self;
   static final PyCode f$0;
   static final PyCode escape$1;
   static final PyCode evalString$2;
   static final PyCode test$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Safely evaluate Python string literals without using eval()."));
      var1.setline(4);
      PyString.fromInterned("Safely evaluate Python string literals without using eval().");
      var1.setline(6);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(8);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("\u0007"), PyString.fromInterned("b"), PyString.fromInterned("\b"), PyString.fromInterned("f"), PyString.fromInterned("\f"), PyString.fromInterned("n"), PyString.fromInterned("\n"), PyString.fromInterned("r"), PyString.fromInterned("\r"), PyString.fromInterned("t"), PyString.fromInterned("\t"), PyString.fromInterned("v"), PyString.fromInterned("\u000b"), PyString.fromInterned("'"), PyString.fromInterned("'"), PyString.fromInterned("\""), PyString.fromInterned("\""), PyString.fromInterned("\\"), PyString.fromInterned("\\")});
      var1.setlocal("simple_escapes", var4);
      var3 = null;
      var1.setline(19);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, escape$1, (PyObject)null);
      var1.setlocal("escape", var6);
      var3 = null;
      var1.setline(40);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, evalString$2, (PyObject)null);
      var1.setlocal("evalString", var6);
      var3 = null;
      var1.setline(50);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test$3, (PyObject)null);
      var1.setlocal("test", var6);
      var3 = null;
      var1.setline(59);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject escape$1(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(21);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(22);
         var3 = var1.getglobal("simple_escapes").__getattr__("get").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(23);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(24);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(25);
            PyObject var8;
            PyException var9;
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("x")).__nonzero__()) {
               var1.setline(26);
               var8 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(27);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var8._lt(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(28);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid hex string escape ('\\%s')")._mod(var1.getlocal(2))));
               }

               try {
                  var1.setline(30);
                  var8 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(16));
                  var1.setlocal(5, var8);
                  var4 = null;
               } catch (Throwable var7) {
                  var9 = Py.setException(var7, var1);
                  if (var9.match(var1.getglobal("ValueError"))) {
                     var1.setline(32);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid hex string escape ('\\%s')")._mod(var1.getlocal(2))));
                  }

                  throw var9;
               }
            } else {
               try {
                  var1.setline(35);
                  var8 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(8));
                  var1.setlocal(5, var8);
                  var4 = null;
               } catch (Throwable var6) {
                  var9 = Py.setException(var6, var1);
                  if (var9.match(var1.getglobal("ValueError"))) {
                     var1.setline(37);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid octal string escape ('\\%s')")._mod(var1.getlocal(2))));
                  }

                  throw var9;
               }
            }

            var1.setline(38);
            var3 = var1.getglobal("chr").__call__(var2, var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject evalString$2(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null)));
         }
      }

      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var10000 = var3._eq(var1.getlocal(1)._mul(Py.newInteger(3)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(44);
         var3 = var1.getlocal(1)._mul(Py.newInteger(3));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(45);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("endswith").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1)).__neg__(), (PyObject)null, (PyObject)null)));
      } else {
         var1.setline(46);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._ge(Py.newInteger(2)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1))));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(47);
         var3 = var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1)), var1.getglobal("len").__call__(var2, var1.getlocal(1)).__neg__(), (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(48);
         var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("\\\\(\\'|\\\"|\\\\|[abfnrtv]|x.{0,2}|[0-7]{1,3})"), (PyObject)var1.getglobal("escape"), (PyObject)var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject test$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(51);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(52);
         PyObject var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(53);
         var5 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(54);
         var5 = var1.getglobal("evalString").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(55);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._ne(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(56);
            Py.printComma(var1.getlocal(0));
            Py.printComma(var1.getlocal(1));
            Py.printComma(var1.getlocal(2));
            Py.println(var1.getlocal(3));
         }
      }
   }

   public literals$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"m", "all", "tail", "esc", "hexes", "i"};
      escape$1 = Py.newCode(1, var2, var1, "escape", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "q"};
      evalString$2 = Py.newCode(1, var2, var1, "evalString", 40, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"i", "c", "s", "e"};
      test$3 = Py.newCode(0, var2, var1, "test", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new literals$py("lib2to3/pgen2/literals$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(literals$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.escape$1(var2, var3);
         case 2:
            return this.evalString$2(var2, var3);
         case 3:
            return this.test$3(var2, var3);
         default:
            return null;
      }
   }
}

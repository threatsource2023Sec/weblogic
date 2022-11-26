package compiler;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/consts.py")
public class consts$py extends PyFunctionTable implements PyRunnable {
   static consts$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(2);
      PyString var3 = PyString.fromInterned("OP_ASSIGN");
      var1.setlocal("OP_ASSIGN", var3);
      var3 = null;
      var1.setline(3);
      var3 = PyString.fromInterned("OP_DELETE");
      var1.setlocal("OP_DELETE", var3);
      var3 = null;
      var1.setline(4);
      var3 = PyString.fromInterned("OP_APPLY");
      var1.setlocal("OP_APPLY", var3);
      var3 = null;
      var1.setline(6);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal("SC_LOCAL", var4);
      var3 = null;
      var1.setline(7);
      var4 = Py.newInteger(2);
      var1.setlocal("SC_GLOBAL_IMPLICIT", var4);
      var3 = null;
      var1.setline(8);
      var4 = Py.newInteger(3);
      var1.setlocal("SC_GLOBAL_EXPLICIT", var4);
      var3 = null;
      var1.setline(9);
      var4 = Py.newInteger(4);
      var1.setlocal("SC_FREE", var4);
      var3 = null;
      var1.setline(10);
      var4 = Py.newInteger(5);
      var1.setlocal("SC_CELL", var4);
      var3 = null;
      var1.setline(11);
      var4 = Py.newInteger(6);
      var1.setlocal("SC_UNKNOWN", var4);
      var3 = null;
      var1.setline(13);
      var4 = Py.newInteger(1);
      var1.setlocal("CO_OPTIMIZED", var4);
      var3 = null;
      var1.setline(14);
      var4 = Py.newInteger(2);
      var1.setlocal("CO_NEWLOCALS", var4);
      var3 = null;
      var1.setline(15);
      var4 = Py.newInteger(4);
      var1.setlocal("CO_VARARGS", var4);
      var3 = null;
      var1.setline(16);
      var4 = Py.newInteger(8);
      var1.setlocal("CO_VARKEYWORDS", var4);
      var3 = null;
      var1.setline(17);
      var4 = Py.newInteger(16);
      var1.setlocal("CO_NESTED", var4);
      var3 = null;
      var1.setline(18);
      var4 = Py.newInteger(32);
      var1.setlocal("CO_GENERATOR", var4);
      var3 = null;
      var1.setline(19);
      var4 = Py.newInteger(0);
      var1.setlocal("CO_GENERATOR_ALLOWED", var4);
      var3 = null;
      var1.setline(20);
      var4 = Py.newInteger(8192);
      var1.setlocal("CO_FUTURE_DIVISION", var4);
      var3 = null;
      var1.setline(21);
      var4 = Py.newInteger(16384);
      var1.setlocal("CO_FUTURE_ABSIMPORT", var4);
      var3 = null;
      var1.setline(22);
      var4 = Py.newInteger(32768);
      var1.setlocal("CO_FUTURE_WITH_STATEMENT", var4);
      var3 = null;
      var1.setline(23);
      var4 = Py.newInteger(65536);
      var1.setlocal("CO_FUTURE_PRINT_FUNCTION", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public consts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new consts$py("compiler/consts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(consts$py.class);
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

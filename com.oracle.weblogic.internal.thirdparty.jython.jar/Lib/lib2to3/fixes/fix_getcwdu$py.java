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
@Filename("lib2to3/fixes/fix_getcwdu.py")
public class fix_getcwdu$py extends PyFunctionTable implements PyRunnable {
   static fix_getcwdu$py self;
   static final PyCode f$0;
   static final PyCode FixGetcwdu$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nFixer that changes os.getcwdu() to os.getcwd().\n"));
      var1.setline(3);
      PyString.fromInterned("\nFixer that changes os.getcwdu() to os.getcwd().\n");
      var1.setline(7);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"Name"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var1.setline(10);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixGetcwdu", var5, FixGetcwdu$1);
      var1.setlocal("FixGetcwdu", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixGetcwdu$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(13);
      PyString var4 = PyString.fromInterned("\n              power< 'os' trailer< dot='.' name='getcwdu' > any* >\n              ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(17);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("name"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(19);
      PyObject var10000 = var1.getlocal(3).__getattr__("replace");
      PyObject var10002 = var1.getglobal("Name");
      PyObject[] var5 = new PyObject[]{PyUnicode.fromInterned("getcwd"), var1.getlocal(3).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_getcwdu$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixGetcwdu$1 = Py.newCode(0, var2, var1, "FixGetcwdu", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "name"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_getcwdu$py("lib2to3/fixes/fix_getcwdu$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_getcwdu$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixGetcwdu$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

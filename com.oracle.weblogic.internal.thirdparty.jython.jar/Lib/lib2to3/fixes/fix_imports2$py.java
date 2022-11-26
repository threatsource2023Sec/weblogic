package lib2to3.fixes;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_imports2.py")
public class fix_imports2$py extends PyFunctionTable implements PyRunnable {
   static fix_imports2$py self;
   static final PyCode f$0;
   static final PyCode FixImports2$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fix incompatible imports and module references that must be fixed after\nfix_imports."));
      var1.setline(2);
      PyString.fromInterned("Fix incompatible imports and module references that must be fixed after\nfix_imports.");
      var1.setline(3);
      String[] var3 = new String[]{"fix_imports"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 1);
      PyObject var4 = var5[0];
      var1.setlocal("fix_imports", var4);
      var4 = null;
      var1.setline(6);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("whichdb"), PyString.fromInterned("dbm"), PyString.fromInterned("anydbm"), PyString.fromInterned("dbm")});
      var1.setlocal("MAPPING", var6);
      var3 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fix_imports").__getattr__("FixImports")};
      var4 = Py.makeClass("FixImports2", var5, FixImports2$1);
      var1.setlocal("FixImports2", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixImports2$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyInteger var3 = Py.newInteger(7);
      var1.setlocal("run_order", var3);
      var3 = null;
      var1.setline(16);
      PyObject var4 = var1.getname("MAPPING");
      var1.setlocal("mapping", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public fix_imports2$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixImports2$1 = Py.newCode(0, var2, var1, "FixImports2", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_imports2$py("lib2to3/fixes/fix_imports2$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_imports2$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixImports2$1(var2, var3);
         default:
            return null;
      }
   }
}

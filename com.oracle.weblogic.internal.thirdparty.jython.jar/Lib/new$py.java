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
@MTime(1498849384000L)
@Filename("new.py")
public class new$py extends PyFunctionTable implements PyRunnable {
   static new$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Create new objects of various types.  Deprecated.\n\nThis module is no longer required except for backward compatibility.\nObjects of most types can now be created by calling the type object.\n"));
      var1.setline(5);
      PyString.fromInterned("Create new objects of various types.  Deprecated.\n\nThis module is no longer required except for backward compatibility.\nObjects of most types can now be created by calling the type object.\n");
      var1.setline(6);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(7);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("The 'new' module has been removed in Python 3.0; use the 'types' module instead."), Py.newInteger(2)};
      String[] var6 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(9);
      var1.dellocal("warnpy3k");
      var1.setline(11);
      var3 = new String[]{"ClassType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("classobj", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"FunctionType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("function", var4);
      var4 = null;
      var1.setline(13);
      var3 = new String[]{"InstanceType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("instance", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"MethodType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("instancemethod", var4);
      var4 = null;
      var1.setline(15);
      var3 = new String[]{"ModuleType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("module", var4);
      var4 = null;
      var1.setline(19);
      var3 = new String[]{"PyBytecode"};
      var5 = imp.importFrom("org.python.core", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("code", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public new$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new new$py("new$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(new$py.class);
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

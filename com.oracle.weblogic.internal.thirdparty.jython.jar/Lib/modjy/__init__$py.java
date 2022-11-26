import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/__init__.py")
public class modjy$py extends PyFunctionTable implements PyRunnable {
   static modjy$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("modjy"), PyString.fromInterned("modjy_exceptions"), PyString.fromInterned("modjy_impl"), PyString.fromInterned("modjy_log"), PyString.fromInterned("modjy_params"), PyString.fromInterned("modjy_publish"), PyString.fromInterned("modjy_response"), PyString.fromInterned("modjy_write"), PyString.fromInterned("modjy_wsgi")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public modjy$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy$py("modjy$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy$py.class);
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

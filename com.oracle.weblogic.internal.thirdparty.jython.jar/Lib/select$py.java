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
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("select.py")
public class select$py extends PyFunctionTable implements PyRunnable {
   static select$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(3);
      String[] var3 = new String[]{"POLLIN", "POLLOUT", "POLLPRI", "POLLERR", "POLLHUP", "POLLNVAL", "error", "poll", "select"};
      PyObject[] var5 = imp.importFrom("_socket", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("POLLIN", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("POLLOUT", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("POLLPRI", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("POLLERR", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("POLLHUP", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("POLLNVAL", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("error", var4);
      var4 = null;
      var4 = var5[7];
      var1.setlocal("poll", var4);
      var4 = null;
      var4 = var5[8];
      var1.setlocal("select", var4);
      var4 = null;
      var1.setline(15);
      PyObject var6 = var1.getname("select");
      var1.setlocal("cpython_compatible_select", var6);
      var3 = null;
      var1.setline(17);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("POLLIN"), PyString.fromInterned("POLLOUT"), PyString.fromInterned("POLLPRI"), PyString.fromInterned("POLLERR"), PyString.fromInterned("POLLHUP"), PyString.fromInterned("POLLNVAL"), PyString.fromInterned("error"), PyString.fromInterned("poll"), PyString.fromInterned("select"), PyString.fromInterned("cpython_compatible_select")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public select$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new select$py("select$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(select$py.class);
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

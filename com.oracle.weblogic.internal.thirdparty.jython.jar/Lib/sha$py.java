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
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("sha.py")
public class sha$py extends PyFunctionTable implements PyRunnable {
   static sha$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(6);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(7);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("the sha module is deprecated; use the hashlib module instead"), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(10);
      String[] var5 = new String[]{"sha1"};
      PyObject[] var6 = imp.importFrom("hashlib", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("sha", var4);
      var4 = null;
      var1.setline(11);
      var3 = var1.getname("sha");
      var1.setlocal("new", var3);
      var3 = null;
      var1.setline(13);
      PyInteger var7 = Py.newInteger(1);
      var1.setlocal("blocksize", var7);
      var3 = null;
      var1.setline(14);
      var7 = Py.newInteger(20);
      var1.setlocal("digest_size", var7);
      var3 = null;
      var1.setline(15);
      var7 = Py.newInteger(20);
      var1.setlocal("digestsize", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public sha$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sha$py("sha$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sha$py.class);
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

package xml;

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
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("xml/etree/__init__.py")
public class etree$py extends PyFunctionTable implements PyRunnable {
   static etree$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_lasti = -1;
      return Py.None;
   }

   public etree$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new etree$py("xml/etree$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(etree$py.class);
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

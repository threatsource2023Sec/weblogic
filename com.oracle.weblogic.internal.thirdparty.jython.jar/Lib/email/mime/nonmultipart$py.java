package email.mime;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/mime/nonmultipart.py")
public class nonmultipart$py extends PyFunctionTable implements PyRunnable {
   static nonmultipart$py self;
   static final PyCode f$0;
   static final PyCode MIMENonMultipart$1;
   static final PyCode attach$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base class for MIME type messages that are not multipart."));
      var1.setline(5);
      PyString.fromInterned("Base class for MIME type messages that are not multipart.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMENonMultipart")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"errors"};
      PyObject[] var6 = imp.importFrom("email", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("errors", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"MIMEBase"};
      var6 = imp.importFrom("email.mime.base", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEBase", var4);
      var4 = null;
      var1.setline(14);
      var6 = new PyObject[]{var1.getname("MIMEBase")};
      var4 = Py.makeClass("MIMENonMultipart", var6, MIMENonMultipart$1);
      var1.setlocal("MIMENonMultipart", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMENonMultipart$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for MIME multipart/* type messages."));
      var1.setline(15);
      PyString.fromInterned("Base class for MIME multipart/* type messages.");
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, attach$2, (PyObject)null);
      var1.setlocal("attach", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject attach$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      throw Py.makeException(var1.getglobal("errors").__getattr__("MultipartConversionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot attach additional subparts to non-multipart/*")));
   }

   public nonmultipart$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMENonMultipart$1 = Py.newCode(0, var2, var1, "MIMENonMultipart", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "payload"};
      attach$2 = Py.newCode(2, var2, var1, "attach", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new nonmultipart$py("email/mime/nonmultipart$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(nonmultipart$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMENonMultipart$1(var2, var3);
         case 2:
            return this.attach$2(var2, var3);
         default:
            return null;
      }
   }
}

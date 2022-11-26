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
@Filename("email/mime/multipart.py")
public class multipart$py extends PyFunctionTable implements PyRunnable {
   static multipart$py self;
   static final PyCode f$0;
   static final PyCode MIMEMultipart$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base class for MIME multipart/* type messages."));
      var1.setline(5);
      PyString.fromInterned("Base class for MIME multipart/* type messages.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEMultipart")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"MIMEBase"};
      PyObject[] var6 = imp.importFrom("email.mime.base", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("MIMEBase", var4);
      var4 = null;
      var1.setline(13);
      var6 = new PyObject[]{var1.getname("MIMEBase")};
      var4 = Py.makeClass("MIMEMultipart", var6, MIMEMultipart$1);
      var1.setlocal("MIMEMultipart", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMEMultipart$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for MIME multipart/* type messages."));
      var1.setline(14);
      PyString.fromInterned("Base class for MIME multipart/* type messages.");
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("mixed"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Creates a multipart/* type message.\n\n        By default, creates a multipart/mixed message, with proper\n        Content-Type and MIME-Version headers.\n\n        _subtype is the subtype of the multipart content type, defaulting to\n        `mixed'.\n\n        boundary is the multipart boundary string.  By default it is\n        calculated as needed.\n\n        _subparts is a sequence of initial subparts for the payload.  It\n        must be an iterable object, such as a list.  You can always\n        attach new subparts to the message by using the attach() method.\n\n        Additional parameters for the Content-Type header are taken from the\n        keyword arguments (or passed into the _params argument).\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyString.fromInterned("Creates a multipart/* type message.\n\n        By default, creates a multipart/mixed message, with proper\n        Content-Type and MIME-Version headers.\n\n        _subtype is the subtype of the multipart content type, defaulting to\n        `mixed'.\n\n        boundary is the multipart boundary string.  By default it is\n        calculated as needed.\n\n        _subparts is a sequence of initial subparts for the payload.  It\n        must be an iterable object, such as a list.  You can always\n        attach new subparts to the message by using the attach() method.\n\n        Additional parameters for the Content-Type header are taken from the\n        keyword arguments (or passed into the _params argument).\n        ");
      var1.setline(36);
      PyObject var10000 = var1.getglobal("MIMEBase").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("multipart"), var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(4));
      var3 = null;
      var1.setline(41);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_payload", var5);
      var3 = null;
      var1.setline(43);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(44);
         PyObject var6 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(44);
            PyObject var7 = var6.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(5, var7);
            var1.setline(45);
            var1.getlocal(0).__getattr__("attach").__call__(var2, var1.getlocal(5));
         }
      }

      var1.setline(46);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(47);
         var1.getlocal(0).__getattr__("set_boundary").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public multipart$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMEMultipart$1 = Py.newCode(0, var2, var1, "MIMEMultipart", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_subtype", "boundary", "_subparts", "_params", "p"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 16, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new multipart$py("email/mime/multipart$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(multipart$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMEMultipart$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

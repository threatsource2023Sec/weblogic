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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/mime/base.py")
public class base$py extends PyFunctionTable implements PyRunnable {
   static base$py self;
   static final PyCode f$0;
   static final PyCode MIMEBase$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base class for MIME specializations."));
      var1.setline(5);
      PyString.fromInterned("Base class for MIME specializations.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEBase")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"message"};
      PyObject[] var6 = imp.importFrom("email", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("message", var4);
      var4 = null;
      var1.setline(13);
      var6 = new PyObject[]{var1.getname("message").__getattr__("Message")};
      var4 = Py.makeClass("MIMEBase", var6, MIMEBase$1);
      var1.setlocal("MIMEBase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMEBase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for MIME specializations."));
      var1.setline(14);
      PyString.fromInterned("Base class for MIME specializations.");
      var1.setline(16);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("This constructor adds a Content-Type: and a MIME-Version: header.\n\n        The Content-Type: header is taken from the _maintype and _subtype\n        arguments.  Additional parameters for this header are taken from the\n        keyword arguments.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyString.fromInterned("This constructor adds a Content-Type: and a MIME-Version: header.\n\n        The Content-Type: header is taken from the _maintype and _subtype\n        arguments.  Additional parameters for this header are taken from the\n        keyword arguments.\n        ");
      var1.setline(23);
      var1.getglobal("message").__getattr__("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(24);
      PyObject var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(25);
      PyObject var10000 = var1.getlocal(0).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Content-Type"), var1.getlocal(4)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(3));
      var3 = null;
      var1.setline(26);
      PyString var6 = PyString.fromInterned("1.0");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("MIME-Version"), var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public base$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMEBase$1 = Py.newCode(0, var2, var1, "MIMEBase", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_maintype", "_subtype", "_params", "ctype"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 16, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new base$py("email/mime/base$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(base$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMEBase$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

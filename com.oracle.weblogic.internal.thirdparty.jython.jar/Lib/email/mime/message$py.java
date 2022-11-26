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
@Filename("email/mime/message.py")
public class message$py extends PyFunctionTable implements PyRunnable {
   static message$py self;
   static final PyCode f$0;
   static final PyCode MIMEMessage$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class representing message/* MIME documents."));
      var1.setline(5);
      PyString.fromInterned("Class representing message/* MIME documents.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEMessage")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"message"};
      PyObject[] var6 = imp.importFrom("email", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("message", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"MIMENonMultipart"};
      var6 = imp.importFrom("email.mime.nonmultipart", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMENonMultipart", var4);
      var4 = null;
      var1.setline(14);
      var6 = new PyObject[]{var1.getname("MIMENonMultipart")};
      var4 = Py.makeClass("MIMEMessage", var6, MIMEMessage$1);
      var1.setlocal("MIMEMessage", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMEMessage$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class representing message/* MIME documents."));
      var1.setline(15);
      PyString.fromInterned("Class representing message/* MIME documents.");
      var1.setline(17);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("rfc822")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create a message/* type MIME document.\n\n        _msg is a message object and must be an instance of Message, or a\n        derived class of Message, otherwise a TypeError is raised.\n\n        Optional _subtype defines the subtype of the contained message.  The\n        default is \"rfc822\" (this is defined by the MIME standard, even though\n        the term \"rfc822\" is technically outdated by RFC 2822).\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyString.fromInterned("Create a message/* type MIME document.\n\n        _msg is a message object and must be an instance of Message, or a\n        derived class of Message, otherwise a TypeError is raised.\n\n        Optional _subtype defines the subtype of the contained message.  The\n        default is \"rfc822\" (this is defined by the MIME standard, even though\n        the term \"rfc822\" is technically outdated by RFC 2822).\n        ");
      var1.setline(27);
      var1.getglobal("MIMENonMultipart").__getattr__("__init__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("message"), (PyObject)var1.getlocal(2));
      var1.setline(28);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("message").__getattr__("Message")).__not__().__nonzero__()) {
         var1.setline(29);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Argument is not an instance of Message")));
      } else {
         var1.setline(32);
         var1.getglobal("message").__getattr__("Message").__getattr__("attach").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(34);
         var1.getlocal(0).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public message$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMEMessage$1 = Py.newCode(0, var2, var1, "MIMEMessage", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_msg", "_subtype"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new message$py("email/mime/message$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(message$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMEMessage$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

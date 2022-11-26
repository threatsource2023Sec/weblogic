package email.mime;

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
@Filename("email/mime/text.py")
public class text$py extends PyFunctionTable implements PyRunnable {
   static text$py self;
   static final PyCode f$0;
   static final PyCode MIMEText$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class representing text/* type MIME documents."));
      var1.setline(5);
      PyString.fromInterned("Class representing text/* type MIME documents.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEText")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"encode_7or8bit"};
      PyObject[] var6 = imp.importFrom("email.encoders", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("encode_7or8bit", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"MIMENonMultipart"};
      var6 = imp.importFrom("email.mime.nonmultipart", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMENonMultipart", var4);
      var4 = null;
      var1.setline(14);
      var6 = new PyObject[]{var1.getname("MIMENonMultipart")};
      var4 = Py.makeClass("MIMEText", var6, MIMEText$1);
      var1.setlocal("MIMEText", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMEText$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for generating text/* type MIME documents."));
      var1.setline(15);
      PyString.fromInterned("Class for generating text/* type MIME documents.");
      var1.setline(17);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("plain"), PyString.fromInterned("us-ascii")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create a text/* type MIME document.\n\n        _text is the string for this message object.\n\n        _subtype is the MIME sub content type, defaulting to \"plain\".\n\n        _charset is the character set parameter added to the Content-Type\n        header.  This defaults to \"us-ascii\".  Note that as a side-effect, the\n        Content-Transfer-Encoding header will also be set.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Create a text/* type MIME document.\n\n        _text is the string for this message object.\n\n        _subtype is the MIME sub content type, defaulting to \"plain\".\n\n        _charset is the character set parameter added to the Content-Type\n        header.  This defaults to \"us-ascii\".  Note that as a side-effect, the\n        Content-Transfer-Encoding header will also be set.\n        ");
      var1.setline(28);
      PyObject var10000 = var1.getglobal("MIMENonMultipart").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("text"), var1.getlocal(2)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, (PyObject)null, new PyDictionary(new PyObject[]{PyString.fromInterned("charset"), var1.getlocal(3)}));
      var3 = null;
      var1.setline(30);
      var1.getlocal(0).__getattr__("set_payload").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public text$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMEText$1 = Py.newCode(0, var2, var1, "MIMEText", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_text", "_subtype", "_charset"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new text$py("email/mime/text$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(text$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMEText$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

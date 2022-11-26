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
@Filename("email/mime/image.py")
public class image$py extends PyFunctionTable implements PyRunnable {
   static image$py self;
   static final PyCode f$0;
   static final PyCode MIMEImage$1;
   static final PyCode __init__$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class representing image/* type MIME documents."));
      var1.setline(5);
      PyString.fromInterned("Class representing image/* type MIME documents.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEImage")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("imghdr", var1, -1);
      var1.setlocal("imghdr", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"encoders"};
      PyObject[] var7 = imp.importFrom("email", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("encoders", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"MIMENonMultipart"};
      var7 = imp.importFrom("email.mime.nonmultipart", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("MIMENonMultipart", var4);
      var4 = null;
      var1.setline(16);
      var7 = new PyObject[]{var1.getname("MIMENonMultipart")};
      var4 = Py.makeClass("MIMEImage", var7, MIMEImage$1);
      var1.setlocal("MIMEImage", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MIMEImage$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for generating image/* type MIME documents."));
      var1.setline(17);
      PyString.fromInterned("Class for generating image/* type MIME documents.");
      var1.setline(19);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("encoders").__getattr__("encode_base64")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create an image/* type MIME document.\n\n        _imagedata is a string containing the raw image data.  If this data\n        can be decoded by the standard Python `imghdr' module, then the\n        subtype will be automatically included in the Content-Type header.\n        Otherwise, you can specify the specific image subtype via the _subtype\n        parameter.\n\n        _encoder is a function which will perform the actual encoding for\n        transport of the image data.  It takes one argument, which is this\n        Image instance.  It should use get_payload() and set_payload() to\n        change the payload to the encoded form.  It should also add any\n        Content-Transfer-Encoding or other headers to the message as\n        necessary.  The default encoding is Base64.\n\n        Any additional keyword arguments are passed to the base class\n        constructor, which turns them into parameters on the Content-Type\n        header.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Create an image/* type MIME document.\n\n        _imagedata is a string containing the raw image data.  If this data\n        can be decoded by the standard Python `imghdr' module, then the\n        subtype will be automatically included in the Content-Type header.\n        Otherwise, you can specify the specific image subtype via the _subtype\n        parameter.\n\n        _encoder is a function which will perform the actual encoding for\n        transport of the image data.  It takes one argument, which is this\n        Image instance.  It should use get_payload() and set_payload() to\n        change the payload to the encoded form.  It should also add any\n        Content-Transfer-Encoding or other headers to the message as\n        necessary.  The default encoding is Base64.\n\n        Any additional keyword arguments are passed to the base class\n        constructor, which turns them into parameters on the Content-Type\n        header.\n        ");
      var1.setline(40);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(41);
         var3 = var1.getglobal("imghdr").__getattr__("what").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(42);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Could not guess image MIME subtype")));
      } else {
         var1.setline(44);
         var10000 = var1.getglobal("MIMENonMultipart").__getattr__("__init__");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("image"), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(4));
         var3 = null;
         var1.setline(45);
         var1.getlocal(0).__getattr__("set_payload").__call__(var2, var1.getlocal(1));
         var1.setline(46);
         var1.getlocal(3).__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public image$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MIMEImage$1 = Py.newCode(0, var2, var1, "MIMEImage", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_imagedata", "_subtype", "_encoder", "_params"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 19, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new image$py("email/mime/image$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(image$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MIMEImage$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         default:
            return null;
      }
   }
}

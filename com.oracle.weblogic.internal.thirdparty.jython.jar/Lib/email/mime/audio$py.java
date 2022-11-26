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
@Filename("email/mime/audio.py")
public class audio$py extends PyFunctionTable implements PyRunnable {
   static audio$py self;
   static final PyCode f$0;
   static final PyCode _whatsnd$1;
   static final PyCode MIMEAudio$2;
   static final PyCode __init__$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class representing audio/* type MIME documents."));
      var1.setline(5);
      PyString.fromInterned("Class representing audio/* type MIME documents.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("MIMEAudio")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("sndhdr", var1, -1);
      var1.setlocal("sndhdr", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"encoders"};
      var7 = imp.importFrom("email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("encoders", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"MIMENonMultipart"};
      var7 = imp.importFrom("email.mime.nonmultipart", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("MIMENonMultipart", var4);
      var4 = null;
      var1.setline(17);
      PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("au"), PyString.fromInterned("basic"), PyString.fromInterned("wav"), PyString.fromInterned("x-wav"), PyString.fromInterned("aiff"), PyString.fromInterned("x-aiff"), PyString.fromInterned("aifc"), PyString.fromInterned("x-aiff")});
      var1.setlocal("_sndhdr_MIMEmap", var8);
      var3 = null;
      var1.setline(25);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, _whatsnd$1, PyString.fromInterned("Try to identify a sound file type.\n\n    sndhdr.what() has a pretty cruddy interface, unfortunately.  This is why\n    we re-do it here.  It would be easier to reverse engineer the Unix 'file'\n    command and use the standard 'magic' file, as shipped with a modern Unix.\n    "));
      var1.setlocal("_whatsnd", var9);
      var3 = null;
      var1.setline(42);
      var7 = new PyObject[]{var1.getname("MIMENonMultipart")};
      var4 = Py.makeClass("MIMEAudio", var7, MIMEAudio$2);
      var1.setlocal("MIMEAudio", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _whatsnd$1(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Try to identify a sound file type.\n\n    sndhdr.what() has a pretty cruddy interface, unfortunately.  This is why\n    we re-do it here.  It would be easier to reverse engineer the Unix 'file'\n    command and use the standard 'magic' file, as shipped with a modern Unix.\n    ");
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(512), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("sndhdr").__getattr__("tests").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(34);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(38);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(35);
         var5 = var1.getlocal(3).__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(36);
         var5 = var1.getlocal(4);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(37);
      var5 = var1.getglobal("_sndhdr_MIMEmap").__getattr__("get").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject MIMEAudio$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for generating audio/* MIME documents."));
      var1.setline(43);
      PyString.fromInterned("Class for generating audio/* MIME documents.");
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("encoders").__getattr__("encode_base64")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Create an audio/* type MIME document.\n\n        _audiodata is a string containing the raw audio data.  If this data\n        can be decoded by the standard Python `sndhdr' module, then the\n        subtype will be automatically included in the Content-Type header.\n        Otherwise, you can specify  the specific audio subtype via the\n        _subtype parameter.  If _subtype is not given, and no subtype can be\n        guessed, a TypeError is raised.\n\n        _encoder is a function which will perform the actual encoding for\n        transport of the image data.  It takes one argument, which is this\n        Image instance.  It should use get_payload() and set_payload() to\n        change the payload to the encoded form.  It should also add any\n        Content-Transfer-Encoding or other headers to the message as\n        necessary.  The default encoding is Base64.\n\n        Any additional keyword arguments are passed to the base class\n        constructor, which turns them into parameters on the Content-Type\n        header.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Create an audio/* type MIME document.\n\n        _audiodata is a string containing the raw audio data.  If this data\n        can be decoded by the standard Python `sndhdr' module, then the\n        subtype will be automatically included in the Content-Type header.\n        Otherwise, you can specify  the specific audio subtype via the\n        _subtype parameter.  If _subtype is not given, and no subtype can be\n        guessed, a TypeError is raised.\n\n        _encoder is a function which will perform the actual encoding for\n        transport of the image data.  It takes one argument, which is this\n        Image instance.  It should use get_payload() and set_payload() to\n        change the payload to the encoded form.  It should also add any\n        Content-Transfer-Encoding or other headers to the message as\n        necessary.  The default encoding is Base64.\n\n        Any additional keyword arguments are passed to the base class\n        constructor, which turns them into parameters on the Content-Type\n        header.\n        ");
      var1.setline(67);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var3 = var1.getglobal("_whatsnd").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(69);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(70);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Could not find audio MIME subtype")));
      } else {
         var1.setline(71);
         var10000 = var1.getglobal("MIMENonMultipart").__getattr__("__init__");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("audio"), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(4));
         var3 = null;
         var1.setline(72);
         var1.getlocal(0).__getattr__("set_payload").__call__(var2, var1.getlocal(1));
         var1.setline(73);
         var1.getlocal(3).__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public audio$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"data", "hdr", "fakefile", "testfn", "res"};
      _whatsnd$1 = Py.newCode(1, var2, var1, "_whatsnd", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MIMEAudio$2 = Py.newCode(0, var2, var1, "MIMEAudio", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_audiodata", "_subtype", "_encoder", "_params"};
      __init__$3 = Py.newCode(5, var2, var1, "__init__", 45, false, true, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new audio$py("email/mime/audio$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(audio$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._whatsnd$1(var2, var3);
         case 2:
            return this.MIMEAudio$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         default:
            return null;
      }
   }
}

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
@Filename("email/__init__.py")
public class email$py extends PyFunctionTable implements PyRunnable {
   static email$py self;
   static final PyCode f$0;
   static final PyCode message_from_string$1;
   static final PyCode message_from_file$2;
   static final PyCode LazyImporter$3;
   static final PyCode __init__$4;
   static final PyCode __getattr__$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A package for parsing, handling, and generating email messages."));
      var1.setline(5);
      PyString.fromInterned("A package for parsing, handling, and generating email messages.");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("4.0.3");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(9);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("base64MIME"), PyString.fromInterned("Charset"), PyString.fromInterned("Encoders"), PyString.fromInterned("Errors"), PyString.fromInterned("Generator"), PyString.fromInterned("Header"), PyString.fromInterned("Iterators"), PyString.fromInterned("Message"), PyString.fromInterned("MIMEAudio"), PyString.fromInterned("MIMEBase"), PyString.fromInterned("MIMEImage"), PyString.fromInterned("MIMEMessage"), PyString.fromInterned("MIMEMultipart"), PyString.fromInterned("MIMENonMultipart"), PyString.fromInterned("MIMEText"), PyString.fromInterned("Parser"), PyString.fromInterned("quopriMIME"), PyString.fromInterned("Utils"), PyString.fromInterned("message_from_string"), PyString.fromInterned("message_from_file"), PyString.fromInterned("base64mime"), PyString.fromInterned("charset"), PyString.fromInterned("encoders"), PyString.fromInterned("errors"), PyString.fromInterned("generator"), PyString.fromInterned("header"), PyString.fromInterned("iterators"), PyString.fromInterned("message"), PyString.fromInterned("mime"), PyString.fromInterned("parser"), PyString.fromInterned("quoprimime"), PyString.fromInterned("utils")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(51);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, message_from_string$1, PyString.fromInterned("Parse a string into a Message object model.\n\n    Optional _class and strict are passed to the Parser constructor.\n    "));
      var1.setlocal("message_from_string", var8);
      var3 = null;
      var1.setline(60);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, message_from_file$2, PyString.fromInterned("Read a file and parse its contents into a Message object model.\n\n    Optional _class and strict are passed to the Parser constructor.\n    "));
      var1.setlocal("message_from_file", var8);
      var3 = null;
      var1.setline(72);
      PyObject var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(74);
      var7 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("LazyImporter", var7, LazyImporter$3);
      var1.setlocal("LazyImporter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(85);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("Charset"), PyString.fromInterned("Encoders"), PyString.fromInterned("Errors"), PyString.fromInterned("FeedParser"), PyString.fromInterned("Generator"), PyString.fromInterned("Header"), PyString.fromInterned("Iterators"), PyString.fromInterned("Message"), PyString.fromInterned("Parser"), PyString.fromInterned("Utils"), PyString.fromInterned("base64MIME"), PyString.fromInterned("quopriMIME")});
      var1.setlocal("_LOWERNAMES", var6);
      var3 = null;
      var1.setline(101);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("Audio"), PyString.fromInterned("Base"), PyString.fromInterned("Image"), PyString.fromInterned("Message"), PyString.fromInterned("Multipart"), PyString.fromInterned("NonMultipart"), PyString.fromInterned("Text")});
      var1.setlocal("_MIMENAMES", var6);
      var3 = null;
      var1.setline(112);
      var9 = var1.getname("_LOWERNAMES").__iter__();

      while(true) {
         var1.setline(112);
         var4 = var9.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(118);
            var9 = imp.importOne("email.mime", var1, -1);
            var1.setlocal("email", var9);
            var3 = null;
            var1.setline(119);
            var9 = var1.getname("_MIMENAMES").__iter__();

            while(true) {
               var1.setline(119);
               var4 = var9.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("_name", var4);
               var1.setline(120);
               var5 = var1.getname("LazyImporter").__call__(var2, PyString.fromInterned("mime.")._add(var1.getname("_name").__getattr__("lower").__call__(var2)));
               var1.setlocal("importer", var5);
               var5 = null;
               var1.setline(121);
               var5 = var1.getname("importer");
               var1.getname("sys").__getattr__("modules").__setitem__(PyString.fromInterned("email.MIME")._add(var1.getname("_name")), var5);
               var5 = null;
               var1.setline(122);
               var1.getname("setattr").__call__(var2, var1.getname("sys").__getattr__("modules").__getitem__(PyString.fromInterned("email")), PyString.fromInterned("MIME")._add(var1.getname("_name")), var1.getname("importer"));
               var1.setline(123);
               var1.getname("setattr").__call__(var2, var1.getname("sys").__getattr__("modules").__getitem__(PyString.fromInterned("email.mime")), var1.getname("_name"), var1.getname("importer"));
            }
         }

         var1.setlocal("_name", var4);
         var1.setline(113);
         var5 = var1.getname("LazyImporter").__call__(var2, var1.getname("_name").__getattr__("lower").__call__(var2));
         var1.setlocal("importer", var5);
         var5 = null;
         var1.setline(114);
         var5 = var1.getname("importer");
         var1.getname("sys").__getattr__("modules").__setitem__(PyString.fromInterned("email.")._add(var1.getname("_name")), var5);
         var5 = null;
         var1.setline(115);
         var1.getname("setattr").__call__(var2, var1.getname("sys").__getattr__("modules").__getitem__(PyString.fromInterned("email")), var1.getname("_name"), var1.getname("importer"));
      }
   }

   public PyObject message_from_string$1(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Parse a string into a Message object model.\n\n    Optional _class and strict are passed to the Parser constructor.\n    ");
      var1.setline(56);
      String[] var3 = new String[]{"Parser"};
      PyObject[] var5 = imp.importFrom("email.parser", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(57);
      PyObject var10000 = var1.getlocal(3);
      var5 = Py.EmptyObjects;
      String[] var7 = new String[0];
      var10000 = var10000._callextra(var5, var7, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var6 = var10000.__getattr__("parsestr").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject message_from_file$2(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("Read a file and parse its contents into a Message object model.\n\n    Optional _class and strict are passed to the Parser constructor.\n    ");
      var1.setline(65);
      String[] var3 = new String[]{"Parser"};
      PyObject[] var5 = imp.importFrom("email.parser", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(66);
      PyObject var10000 = var1.getlocal(3);
      var5 = Py.EmptyObjects;
      String[] var7 = new String[0];
      var10000 = var10000._callextra(var5, var7, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var6 = var10000.__getattr__("parse").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject LazyImporter$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$5, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = PyString.fromInterned("email.")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("__name__", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$5(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      var1.getglobal("__import__").__call__(var2, var1.getlocal(0).__getattr__("__name__"));
      var1.setline(80);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(0).__getattr__("__name__"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(81);
      var1.getlocal(0).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(2).__getattr__("__dict__"));
      var1.setline(82);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public email$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "args", "kws", "Parser"};
      message_from_string$1 = Py.newCode(3, var2, var1, "message_from_string", 51, true, true, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "args", "kws", "Parser"};
      message_from_file$2 = Py.newCode(3, var2, var1, "message_from_file", 60, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LazyImporter$3 = Py.newCode(0, var2, var1, "LazyImporter", 74, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module_name"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 75, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "mod"};
      __getattr__$5 = Py.newCode(2, var2, var1, "__getattr__", 78, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new email$py("email$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(email$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.message_from_string$1(var2, var3);
         case 2:
            return this.message_from_file$2(var2, var3);
         case 3:
            return this.LazyImporter$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__getattr__$5(var2, var3);
         default:
            return null;
      }
   }
}

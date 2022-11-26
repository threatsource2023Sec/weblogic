package email;

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
@Filename("email/parser.py")
public class parser$py extends PyFunctionTable implements PyRunnable {
   static parser$py self;
   static final PyCode f$0;
   static final PyCode Parser$1;
   static final PyCode __init__$2;
   static final PyCode parse$3;
   static final PyCode parsestr$4;
   static final PyCode HeaderParser$5;
   static final PyCode parse$6;
   static final PyCode parsestr$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A parser of RFC 2822 and MIME email messages."));
      var1.setline(5);
      PyString.fromInterned("A parser of RFC 2822 and MIME email messages.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Parser"), PyString.fromInterned("HeaderParser")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"FeedParser"};
      var7 = imp.importFrom("email.feedparser", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("FeedParser", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"Message"};
      var7 = imp.importFrom("email.message", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Message", var4);
      var4 = null;
      var1.setline(17);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Parser", var7, Parser$1);
      var1.setlocal("Parser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(86);
      var7 = new PyObject[]{var1.getname("Parser")};
      var4 = Py.makeClass("HeaderParser", var7, HeaderParser$5);
      var1.setlocal("HeaderParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Parser$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Parser of RFC 2822 and MIME email messages.\n\n        Creates an in-memory object tree representing the email message, which\n        can then be manipulated and turned over to a Generator to return the\n        textual representation of the message.\n\n        The string must be formatted as a block of RFC 2822 headers and header\n        continuation lines, optionally preceeded by a `Unix-from' header.  The\n        header block is terminated either by the end of the string or by a\n        blank line.\n\n        _class is the class to instantiate for new message objects when they\n        must be created.  This class must have a constructor that can take\n        zero arguments.  Default is Message.Message.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(56);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse$3, PyString.fromInterned("Create a message structure from the data in a file.\n\n        Reads all the data from the file and returns the root of the message\n        structure.  Optional headersonly is a flag specifying whether to stop\n        parsing after reading the headers or not.  The default is False,\n        meaning it parses the entire contents of the file.\n        "));
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(74);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parsestr$4, PyString.fromInterned("Create a message structure from a string.\n\n        Returns the root of the message structure.  Optional headersonly is a\n        flag specifying whether to stop parsing after reading the headers or\n        not.  The default is False, meaning it parses the entire contents of\n        the file.\n        "));
      var1.setlocal("parsestr", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Parser of RFC 2822 and MIME email messages.\n\n        Creates an in-memory object tree representing the email message, which\n        can then be manipulated and turned over to a Generator to return the\n        textual representation of the message.\n\n        The string must be formatted as a block of RFC 2822 headers and header\n        continuation lines, optionally preceeded by a `Unix-from' header.  The\n        header block is terminated either by the end of the string or by a\n        blank line.\n\n        _class is the class to instantiate for new message objects when they\n        must be created.  This class must have a constructor that can take\n        zero arguments.  Default is Message.Message.\n        ");
      var1.setline(34);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(35);
         var4 = PyString.fromInterned("_class");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(36);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Multiple values for keyword arg '_class'")));
         }

         var1.setline(37);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("_class"), var3);
         var3 = null;
      }

      var1.setline(38);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(39);
         var4 = PyString.fromInterned("strict");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(40);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Multiple values for keyword arg 'strict'")));
         }

         var1.setline(41);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("strict"), var3);
         var3 = null;
      }

      var1.setline(42);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._gt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Too many arguments")));
      } else {
         var1.setline(44);
         var4 = PyString.fromInterned("_class");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(45);
            var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("_class"));
            var1.getlocal(0).__setattr__("_class", var3);
            var3 = null;
            var1.setline(46);
            var1.getlocal(2).__delitem__((PyObject)PyString.fromInterned("_class"));
         } else {
            var1.setline(48);
            var3 = var1.getglobal("Message");
            var1.getlocal(0).__setattr__("_class", var3);
            var3 = null;
         }

         var1.setline(49);
         var4 = PyString.fromInterned("strict");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(50);
            var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("'strict' argument is deprecated (and ignored)"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
            var1.setline(52);
            var1.getlocal(2).__delitem__((PyObject)PyString.fromInterned("strict"));
         }

         var1.setline(53);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(54);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unexpected keyword arguments")));
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject parse$3(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("Create a message structure from the data in a file.\n\n        Reads all the data from the file and returns the root of the message\n        structure.  Optional headersonly is a flag specifying whether to stop\n        parsing after reading the headers or not.  The default is False,\n        meaning it parses the entire contents of the file.\n        ");
      var1.setline(64);
      PyObject var3 = var1.getglobal("FeedParser").__call__(var2, var1.getlocal(0).__getattr__("_class"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(65);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(66);
         var1.getlocal(3).__getattr__("_set_headersonly").__call__(var2);
      }

      while(true) {
         var1.setline(67);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(68);
         var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8192));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(69);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(71);
         var1.getlocal(3).__getattr__("feed").__call__(var2, var1.getlocal(4));
      }

      var1.setline(72);
      var3 = var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parsestr$4(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Create a message structure from a string.\n\n        Returns the root of the message structure.  Optional headersonly is a\n        flag specifying whether to stop parsing after reading the headers or\n        not.  The default is False, meaning it parses the entire contents of\n        the file.\n        ");
      var1.setline(82);
      PyObject var10000 = var1.getlocal(0).__getattr__("parse");
      PyObject[] var3 = new PyObject[]{var1.getglobal("StringIO").__call__(var2, var1.getlocal(1)), var1.getlocal(2)};
      String[] var4 = new String[]{"headersonly"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject HeaderParser$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(87);
      PyObject[] var3 = new PyObject[]{var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, parse$6, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(90);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, parsestr$7, (PyObject)null);
      var1.setlocal("parsestr", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parse$6(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getglobal("Parser").__getattr__("parse").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parsestr$7(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getglobal("Parser").__getattr__("parsestr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public parser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Parser$1 = Py.newCode(0, var2, var1, "Parser", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kws"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 18, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "headersonly", "feedparser", "data"};
      parse$3 = Py.newCode(3, var2, var1, "parse", 56, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "headersonly"};
      parsestr$4 = Py.newCode(3, var2, var1, "parsestr", 74, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HeaderParser$5 = Py.newCode(0, var2, var1, "HeaderParser", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "headersonly"};
      parse$6 = Py.newCode(3, var2, var1, "parse", 87, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "headersonly"};
      parsestr$7 = Py.newCode(3, var2, var1, "parsestr", 90, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new parser$py("email/parser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(parser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Parser$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.parse$3(var2, var3);
         case 4:
            return this.parsestr$4(var2, var3);
         case 5:
            return this.HeaderParser$5(var2, var3);
         case 6:
            return this.parse$6(var2, var3);
         case 7:
            return this.parsestr$7(var2, var3);
         default:
            return null;
      }
   }
}

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/errors.py")
public class errors$py extends PyFunctionTable implements PyRunnable {
   static errors$py self;
   static final PyCode f$0;
   static final PyCode MessageError$1;
   static final PyCode MessageParseError$2;
   static final PyCode HeaderParseError$3;
   static final PyCode BoundaryError$4;
   static final PyCode MultipartConversionError$5;
   static final PyCode CharsetError$6;
   static final PyCode MessageDefect$7;
   static final PyCode __init__$8;
   static final PyCode NoBoundaryInMultipartDefect$9;
   static final PyCode StartBoundaryNotFoundDefect$10;
   static final PyCode FirstHeaderLineIsContinuationDefect$11;
   static final PyCode MisplacedEnvelopeHeaderDefect$12;
   static final PyCode MalformedHeaderDefect$13;
   static final PyCode MultipartInvariantViolationDefect$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("email package exception classes."));
      var1.setline(5);
      PyString.fromInterned("email package exception classes.");
      var1.setline(9);
      PyObject[] var3 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("MessageError", var3, MessageError$1);
      var1.setlocal("MessageError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(13);
      var3 = new PyObject[]{var1.getname("MessageError")};
      var4 = Py.makeClass("MessageParseError", var3, MessageParseError$2);
      var1.setlocal("MessageParseError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(17);
      var3 = new PyObject[]{var1.getname("MessageParseError")};
      var4 = Py.makeClass("HeaderParseError", var3, HeaderParseError$3);
      var1.setlocal("HeaderParseError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(21);
      var3 = new PyObject[]{var1.getname("MessageParseError")};
      var4 = Py.makeClass("BoundaryError", var3, BoundaryError$4);
      var1.setlocal("BoundaryError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(25);
      var3 = new PyObject[]{var1.getname("MessageError"), var1.getname("TypeError")};
      var4 = Py.makeClass("MultipartConversionError", var3, MultipartConversionError$5);
      var1.setlocal("MultipartConversionError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(29);
      var3 = new PyObject[]{var1.getname("MessageError")};
      var4 = Py.makeClass("CharsetError", var3, CharsetError$6);
      var1.setlocal("CharsetError", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(35);
      var3 = Py.EmptyObjects;
      var4 = Py.makeClass("MessageDefect", var3, MessageDefect$7);
      var1.setlocal("MessageDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(41);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("NoBoundaryInMultipartDefect", var3, NoBoundaryInMultipartDefect$9);
      var1.setlocal("NoBoundaryInMultipartDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(44);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("StartBoundaryNotFoundDefect", var3, StartBoundaryNotFoundDefect$10);
      var1.setlocal("StartBoundaryNotFoundDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(47);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("FirstHeaderLineIsContinuationDefect", var3, FirstHeaderLineIsContinuationDefect$11);
      var1.setlocal("FirstHeaderLineIsContinuationDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(50);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("MisplacedEnvelopeHeaderDefect", var3, MisplacedEnvelopeHeaderDefect$12);
      var1.setlocal("MisplacedEnvelopeHeaderDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(53);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("MalformedHeaderDefect", var3, MalformedHeaderDefect$13);
      var1.setlocal("MalformedHeaderDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(56);
      var3 = new PyObject[]{var1.getname("MessageDefect")};
      var4 = Py.makeClass("MultipartInvariantViolationDefect", var3, MultipartInvariantViolationDefect$14);
      var1.setlocal("MultipartInvariantViolationDefect", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MessageError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for errors in the email package."));
      var1.setline(10);
      PyString.fromInterned("Base class for errors in the email package.");
      return var1.getf_locals();
   }

   public PyObject MessageParseError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for message parsing errors."));
      var1.setline(14);
      PyString.fromInterned("Base class for message parsing errors.");
      return var1.getf_locals();
   }

   public PyObject HeaderParseError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Error while parsing headers."));
      var1.setline(18);
      PyString.fromInterned("Error while parsing headers.");
      return var1.getf_locals();
   }

   public PyObject BoundaryError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Couldn't find terminating boundary."));
      var1.setline(22);
      PyString.fromInterned("Couldn't find terminating boundary.");
      return var1.getf_locals();
   }

   public PyObject MultipartConversionError$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Conversion to a multipart is prohibited."));
      var1.setline(26);
      PyString.fromInterned("Conversion to a multipart is prohibited.");
      return var1.getf_locals();
   }

   public PyObject CharsetError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An illegal charset was given."));
      var1.setline(30);
      PyString.fromInterned("An illegal charset was given.");
      return var1.getf_locals();
   }

   public PyObject MessageDefect$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for a message defect."));
      var1.setline(36);
      PyString.fromInterned("Base class for a message defect.");
      var1.setline(38);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("line", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NoBoundaryInMultipartDefect$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A message claimed to be a multipart but had no boundary parameter."));
      var1.setline(42);
      PyString.fromInterned("A message claimed to be a multipart but had no boundary parameter.");
      return var1.getf_locals();
   }

   public PyObject StartBoundaryNotFoundDefect$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The claimed start boundary was never found."));
      var1.setline(45);
      PyString.fromInterned("The claimed start boundary was never found.");
      return var1.getf_locals();
   }

   public PyObject FirstHeaderLineIsContinuationDefect$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A message had a continuation line as its first header line."));
      var1.setline(48);
      PyString.fromInterned("A message had a continuation line as its first header line.");
      return var1.getf_locals();
   }

   public PyObject MisplacedEnvelopeHeaderDefect$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A 'Unix-from' header was found in the middle of a header block."));
      var1.setline(51);
      PyString.fromInterned("A 'Unix-from' header was found in the middle of a header block.");
      return var1.getf_locals();
   }

   public PyObject MalformedHeaderDefect$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Found a header that was missing a colon, or was otherwise malformed."));
      var1.setline(54);
      PyString.fromInterned("Found a header that was missing a colon, or was otherwise malformed.");
      return var1.getf_locals();
   }

   public PyObject MultipartInvariantViolationDefect$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A message claimed to be a multipart but no subparts were found."));
      var1.setline(57);
      PyString.fromInterned("A message claimed to be a multipart but no subparts were found.");
      return var1.getf_locals();
   }

   public errors$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MessageError$1 = Py.newCode(0, var2, var1, "MessageError", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MessageParseError$2 = Py.newCode(0, var2, var1, "MessageParseError", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HeaderParseError$3 = Py.newCode(0, var2, var1, "HeaderParseError", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BoundaryError$4 = Py.newCode(0, var2, var1, "BoundaryError", 21, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MultipartConversionError$5 = Py.newCode(0, var2, var1, "MultipartConversionError", 25, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CharsetError$6 = Py.newCode(0, var2, var1, "CharsetError", 29, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MessageDefect$7 = Py.newCode(0, var2, var1, "MessageDefect", 35, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line"};
      __init__$8 = Py.newCode(2, var2, var1, "__init__", 38, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NoBoundaryInMultipartDefect$9 = Py.newCode(0, var2, var1, "NoBoundaryInMultipartDefect", 41, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StartBoundaryNotFoundDefect$10 = Py.newCode(0, var2, var1, "StartBoundaryNotFoundDefect", 44, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FirstHeaderLineIsContinuationDefect$11 = Py.newCode(0, var2, var1, "FirstHeaderLineIsContinuationDefect", 47, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MisplacedEnvelopeHeaderDefect$12 = Py.newCode(0, var2, var1, "MisplacedEnvelopeHeaderDefect", 50, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MalformedHeaderDefect$13 = Py.newCode(0, var2, var1, "MalformedHeaderDefect", 53, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MultipartInvariantViolationDefect$14 = Py.newCode(0, var2, var1, "MultipartInvariantViolationDefect", 56, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new errors$py("email/errors$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(errors$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MessageError$1(var2, var3);
         case 2:
            return this.MessageParseError$2(var2, var3);
         case 3:
            return this.HeaderParseError$3(var2, var3);
         case 4:
            return this.BoundaryError$4(var2, var3);
         case 5:
            return this.MultipartConversionError$5(var2, var3);
         case 6:
            return this.CharsetError$6(var2, var3);
         case 7:
            return this.MessageDefect$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.NoBoundaryInMultipartDefect$9(var2, var3);
         case 10:
            return this.StartBoundaryNotFoundDefect$10(var2, var3);
         case 11:
            return this.FirstHeaderLineIsContinuationDefect$11(var2, var3);
         case 12:
            return this.MisplacedEnvelopeHeaderDefect$12(var2, var3);
         case 13:
            return this.MalformedHeaderDefect$13(var2, var3);
         case 14:
            return this.MultipartInvariantViolationDefect$14(var2, var3);
         default:
            return null;
      }
   }
}

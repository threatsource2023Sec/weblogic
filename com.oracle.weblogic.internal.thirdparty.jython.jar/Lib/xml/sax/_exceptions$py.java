package xml.sax;

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
import org.python.core.PyTuple;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/sax/_exceptions.py")
public class _exceptions$py extends PyFunctionTable implements PyRunnable {
   static _exceptions$py self;
   static final PyCode f$0;
   static final PyCode SAXException$1;
   static final PyCode __init__$2;
   static final PyCode getMessage$3;
   static final PyCode getException$4;
   static final PyCode __str__$5;
   static final PyCode __getitem__$6;
   static final PyCode SAXParseException$7;
   static final PyCode __init__$8;
   static final PyCode getColumnNumber$9;
   static final PyCode getLineNumber$10;
   static final PyCode getPublicId$11;
   static final PyCode getSystemId$12;
   static final PyCode __str__$13;
   static final PyCode SAXNotRecognizedException$14;
   static final PyCode SAXNotSupportedException$15;
   static final PyCode SAXReaderNotAvailable$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Different kinds of SAX Exceptions"));
      var1.setline(1);
      PyString.fromInterned("Different kinds of SAX Exceptions");
      var1.setline(5);
      PyObject[] var3 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("SAXException", var3, SAXException$1);
      var1.setlocal("SAXException", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(42);
      var3 = new PyObject[]{var1.getname("SAXException")};
      var4 = Py.makeClass("SAXParseException", var3, SAXParseException$7);
      var1.setlocal("SAXParseException", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(101);
      var3 = new PyObject[]{var1.getname("SAXException")};
      var4 = Py.makeClass("SAXNotRecognizedException", var3, SAXNotRecognizedException$14);
      var1.setlocal("SAXNotRecognizedException", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(111);
      var3 = new PyObject[]{var1.getname("SAXException")};
      var4 = Py.makeClass("SAXNotSupportedException", var3, SAXNotSupportedException$15);
      var1.setlocal("SAXNotSupportedException", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(121);
      var3 = new PyObject[]{var1.getname("SAXNotSupportedException")};
      var4 = Py.makeClass("SAXReaderNotAvailable", var3, SAXReaderNotAvailable$16);
      var1.setlocal("SAXReaderNotAvailable", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SAXException$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Encapsulate an XML error or warning. This class can contain\n    basic error or warning information from either the XML parser or\n    the application: you can subclass it to provide additional\n    functionality, or to add localization. Note that although you will\n    receive a SAXException as the argument to the handlers in the\n    ErrorHandler interface, you are not actually required to throw\n    the exception; instead, you can simply read the information in\n    it."));
      var1.setline(13);
      PyString.fromInterned("Encapsulate an XML error or warning. This class can contain\n    basic error or warning information from either the XML parser or\n    the application: you can subclass it to provide additional\n    functionality, or to add localization. Note that although you will\n    receive a SAXException as the argument to the handlers in the\n    ErrorHandler interface, you are not actually required to throw\n    the exception; instead, you can simply read the information in\n    it.");
      var1.setline(15);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Creates an exception. The message is required, but the exception\n        is optional."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getMessage$3, PyString.fromInterned("Return a message for this exception."));
      var1.setlocal("getMessage", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getException$4, PyString.fromInterned("Return the embedded exception, or None if there was none."));
      var1.setlocal("getException", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$5, PyString.fromInterned("Create a string representation of the exception."));
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$6, PyString.fromInterned("Avoids weird error messages if someone does exception[ix] by\n        mistake, since Exception has __getitem__ defined."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyString.fromInterned("Creates an exception. The message is required, but the exception\n        is optional.");
      var1.setline(18);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_msg", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_exception", var3);
      var3 = null;
      var1.setline(20);
      var1.getglobal("Exception").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getMessage$3(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyString.fromInterned("Return a message for this exception.");
      var1.setline(24);
      PyObject var3 = var1.getlocal(0).__getattr__("_msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getException$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Return the embedded exception, or None if there was none.");
      var1.setline(28);
      PyObject var3 = var1.getlocal(0).__getattr__("_exception");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$5(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Create a string representation of the exception.");
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getattr__("_msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$6(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Avoids weird error messages if someone does exception[ix] by\n        mistake, since Exception has __getitem__ defined.");
      var1.setline(37);
      throw Py.makeException(var1.getglobal("AttributeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__getitem__")));
   }

   public PyObject SAXParseException$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Encapsulate an XML parse error or warning.\n\n    This exception will include information for locating the error in\n    the original XML document. Note that although the application will\n    receive a SAXParseException as the argument to the handlers in the\n    ErrorHandler interface, the application is not actually required\n    to throw the exception; instead, it can simply read the\n    information in it and take a different action.\n\n    Since this exception is a subclass of SAXException, it inherits\n    the ability to wrap another exception."));
      var1.setline(53);
      PyString.fromInterned("Encapsulate an XML parse error or warning.\n\n    This exception will include information for locating the error in\n    the original XML document. Note that although the application will\n    receive a SAXParseException as the argument to the handlers in the\n    ErrorHandler interface, the application is not actually required\n    to throw the exception; instead, it can simply read the\n    information in it and take a different action.\n\n    Since this exception is a subclass of SAXException, it inherits\n    the ability to wrap another exception.");
      var1.setline(55);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, PyString.fromInterned("Creates the exception. The exception parameter is allowed to be None."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getColumnNumber$9, PyString.fromInterned("The column number of the end of the text where the exception\n        occurred."));
      var1.setlocal("getColumnNumber", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLineNumber$10, PyString.fromInterned("The line number of the end of the text where the exception occurred."));
      var1.setlocal("getLineNumber", var4);
      var3 = null;
      var1.setline(77);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPublicId$11, PyString.fromInterned("Get the public identifier of the entity where the exception occurred."));
      var1.setlocal("getPublicId", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSystemId$12, PyString.fromInterned("Get the system identifier of the entity where the exception occurred."));
      var1.setlocal("getSystemId", var4);
      var3 = null;
      var1.setline(85);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$13, PyString.fromInterned("Create a string representation of the exception."));
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString.fromInterned("Creates the exception. The exception parameter is allowed to be None.");
      var1.setline(57);
      var1.getglobal("SAXException").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(58);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_locator", var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(0).__getattr__("_locator").__getattr__("getSystemId").__call__(var2);
      var1.getlocal(0).__setattr__("_systemId", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getlocal(0).__getattr__("_locator").__getattr__("getColumnNumber").__call__(var2);
      var1.getlocal(0).__setattr__("_colnum", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(0).__getattr__("_locator").__getattr__("getLineNumber").__call__(var2);
      var1.getlocal(0).__setattr__("_linenum", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getColumnNumber$9(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyString.fromInterned("The column number of the end of the text where the exception\n        occurred.");
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("_colnum");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getLineNumber$10(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("The line number of the end of the text where the exception occurred.");
      var1.setline(75);
      PyObject var3 = var1.getlocal(0).__getattr__("_linenum");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getPublicId$11(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("Get the public identifier of the entity where the exception occurred.");
      var1.setline(79);
      PyObject var3 = var1.getlocal(0).__getattr__("_locator").__getattr__("getPublicId").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getSystemId$12(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Get the system identifier of the entity where the exception occurred.");
      var1.setline(83);
      PyObject var3 = var1.getlocal(0).__getattr__("_systemId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$13(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyString.fromInterned("Create a string representation of the exception.");
      var1.setline(87);
      PyObject var3 = var1.getlocal(0).__getattr__("getSystemId").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(89);
         var4 = PyString.fromInterned("<unknown>");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(90);
      var3 = var1.getlocal(0).__getattr__("getLineNumber").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(92);
         var4 = PyString.fromInterned("?");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(93);
      var3 = var1.getlocal(0).__getattr__("getColumnNumber").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(95);
         var4 = PyString.fromInterned("?");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(96);
      var3 = PyString.fromInterned("%s:%s:%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("_msg")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SAXNotRecognizedException$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception class for an unrecognized identifier.\n\n    An XMLReader will raise this exception when it is confronted with an\n    unrecognized feature or property. SAX applications and extensions may\n    use this class for similar purposes."));
      var1.setline(106);
      PyString.fromInterned("Exception class for an unrecognized identifier.\n\n    An XMLReader will raise this exception when it is confronted with an\n    unrecognized feature or property. SAX applications and extensions may\n    use this class for similar purposes.");
      return var1.getf_locals();
   }

   public PyObject SAXNotSupportedException$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception class for an unsupported operation.\n\n    An XMLReader will raise this exception when a service it cannot\n    perform is requested (specifically setting a state or value). SAX\n    applications and extensions may use this class for similar\n    purposes."));
      var1.setline(117);
      PyString.fromInterned("Exception class for an unsupported operation.\n\n    An XMLReader will raise this exception when a service it cannot\n    perform is requested (specifically setting a state or value). SAX\n    applications and extensions may use this class for similar\n    purposes.");
      return var1.getf_locals();
   }

   public PyObject SAXReaderNotAvailable$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception class for a missing driver.\n\n    An XMLReader module (driver) should raise this exception when it\n    is first imported, e.g. when a support module cannot be imported.\n    It also may be raised during parsing, e.g. if executing an external\n    program is not permitted."));
      var1.setline(127);
      PyString.fromInterned("Exception class for a missing driver.\n\n    An XMLReader module (driver) should raise this exception when it\n    is first imported, e.g. when a support module cannot be imported.\n    It also may be raised during parsing, e.g. if executing an external\n    program is not permitted.");
      return var1.getf_locals();
   }

   public _exceptions$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SAXException$1 = Py.newCode(0, var2, var1, "SAXException", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "exception"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getMessage$3 = Py.newCode(1, var2, var1, "getMessage", 22, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getException$4 = Py.newCode(1, var2, var1, "getException", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$5 = Py.newCode(1, var2, var1, "__str__", 30, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ix"};
      __getitem__$6 = Py.newCode(2, var2, var1, "__getitem__", 34, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SAXParseException$7 = Py.newCode(0, var2, var1, "SAXParseException", 42, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "exception", "locator"};
      __init__$8 = Py.newCode(4, var2, var1, "__init__", 55, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getColumnNumber$9 = Py.newCode(1, var2, var1, "getColumnNumber", 68, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLineNumber$10 = Py.newCode(1, var2, var1, "getLineNumber", 73, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getPublicId$11 = Py.newCode(1, var2, var1, "getPublicId", 77, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getSystemId$12 = Py.newCode(1, var2, var1, "getSystemId", 81, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sysid", "linenum", "colnum"};
      __str__$13 = Py.newCode(1, var2, var1, "__str__", 85, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SAXNotRecognizedException$14 = Py.newCode(0, var2, var1, "SAXNotRecognizedException", 101, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SAXNotSupportedException$15 = Py.newCode(0, var2, var1, "SAXNotSupportedException", 111, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SAXReaderNotAvailable$16 = Py.newCode(0, var2, var1, "SAXReaderNotAvailable", 121, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _exceptions$py("xml/sax/_exceptions$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_exceptions$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SAXException$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.getMessage$3(var2, var3);
         case 4:
            return this.getException$4(var2, var3);
         case 5:
            return this.__str__$5(var2, var3);
         case 6:
            return this.__getitem__$6(var2, var3);
         case 7:
            return this.SAXParseException$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.getColumnNumber$9(var2, var3);
         case 10:
            return this.getLineNumber$10(var2, var3);
         case 11:
            return this.getPublicId$11(var2, var3);
         case 12:
            return this.getSystemId$12(var2, var3);
         case 13:
            return this.__str__$13(var2, var3);
         case 14:
            return this.SAXNotRecognizedException$14(var2, var3);
         case 15:
            return this.SAXNotSupportedException$15(var2, var3);
         case 16:
            return this.SAXReaderNotAvailable$16(var2, var3);
         default:
            return null;
      }
   }
}

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
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
@Filename("xmlrpclib.py")
public class xmlrpclib$py extends PyFunctionTable implements PyRunnable {
   static xmlrpclib$py self;
   static final PyCode f$0;
   static final PyCode _decode$1;
   static final PyCode escape$2;
   static final PyCode _stringify$3;
   static final PyCode _stringify$4;
   static final PyCode Error$5;
   static final PyCode __str__$6;
   static final PyCode ProtocolError$7;
   static final PyCode __init__$8;
   static final PyCode __repr__$9;
   static final PyCode ResponseError$10;
   static final PyCode Fault$11;
   static final PyCode __init__$12;
   static final PyCode __repr__$13;
   static final PyCode Boolean$14;
   static final PyCode __init__$15;
   static final PyCode encode$16;
   static final PyCode __cmp__$17;
   static final PyCode __repr__$18;
   static final PyCode __int__$19;
   static final PyCode __nonzero__$20;
   static final PyCode boolean$21;
   static final PyCode _strftime$22;
   static final PyCode DateTime$23;
   static final PyCode __init__$24;
   static final PyCode make_comparable$25;
   static final PyCode __lt__$26;
   static final PyCode __le__$27;
   static final PyCode __gt__$28;
   static final PyCode __ge__$29;
   static final PyCode __eq__$30;
   static final PyCode __ne__$31;
   static final PyCode timetuple$32;
   static final PyCode __cmp__$33;
   static final PyCode __str__$34;
   static final PyCode __repr__$35;
   static final PyCode decode$36;
   static final PyCode encode$37;
   static final PyCode _datetime$38;
   static final PyCode _datetime_type$39;
   static final PyCode Binary$40;
   static final PyCode __init__$41;
   static final PyCode __str__$42;
   static final PyCode __cmp__$43;
   static final PyCode decode$44;
   static final PyCode encode$45;
   static final PyCode _binary$46;
   static final PyCode ExpatParser$47;
   static final PyCode __init__$48;
   static final PyCode feed$49;
   static final PyCode close$50;
   static final PyCode SlowParser$51;
   static final PyCode __init__$52;
   static final PyCode Marshaller$53;
   static final PyCode __init__$54;
   static final PyCode dumps$55;
   static final PyCode _Marshaller__dump$56;
   static final PyCode dump_nil$57;
   static final PyCode dump_int$58;
   static final PyCode dump_bool$59;
   static final PyCode dump_long$60;
   static final PyCode dump_double$61;
   static final PyCode dump_string$62;
   static final PyCode dump_unicode$63;
   static final PyCode dump_array$64;
   static final PyCode dump_struct$65;
   static final PyCode dump_datetime$66;
   static final PyCode dump_instance$67;
   static final PyCode Unmarshaller$68;
   static final PyCode __init__$69;
   static final PyCode close$70;
   static final PyCode getmethodname$71;
   static final PyCode xml$72;
   static final PyCode start$73;
   static final PyCode data$74;
   static final PyCode end$75;
   static final PyCode end_dispatch$76;
   static final PyCode end_nil$77;
   static final PyCode end_boolean$78;
   static final PyCode end_int$79;
   static final PyCode end_double$80;
   static final PyCode end_string$81;
   static final PyCode end_array$82;
   static final PyCode end_struct$83;
   static final PyCode end_base64$84;
   static final PyCode end_dateTime$85;
   static final PyCode end_value$86;
   static final PyCode end_params$87;
   static final PyCode end_fault$88;
   static final PyCode end_methodName$89;
   static final PyCode _MultiCallMethod$90;
   static final PyCode __init__$91;
   static final PyCode __getattr__$92;
   static final PyCode __call__$93;
   static final PyCode MultiCallIterator$94;
   static final PyCode __init__$95;
   static final PyCode __getitem__$96;
   static final PyCode MultiCall$97;
   static final PyCode __init__$98;
   static final PyCode __repr__$99;
   static final PyCode __getattr__$100;
   static final PyCode __call__$101;
   static final PyCode getparser$102;
   static final PyCode dumps$103;
   static final PyCode loads$104;
   static final PyCode gzip_encode$105;
   static final PyCode gzip_decode$106;
   static final PyCode GzipDecodedResponse$107;
   static final PyCode __init__$108;
   static final PyCode close$109;
   static final PyCode _Method$110;
   static final PyCode __init__$111;
   static final PyCode __getattr__$112;
   static final PyCode __call__$113;
   static final PyCode Transport$114;
   static final PyCode __init__$115;
   static final PyCode request$116;
   static final PyCode single_request$117;
   static final PyCode getparser$118;
   static final PyCode get_host_info$119;
   static final PyCode make_connection$120;
   static final PyCode close$121;
   static final PyCode send_request$122;
   static final PyCode send_host$123;
   static final PyCode send_user_agent$124;
   static final PyCode send_content$125;
   static final PyCode parse_response$126;
   static final PyCode SafeTransport$127;
   static final PyCode make_connection$128;
   static final PyCode ServerProxy$129;
   static final PyCode __init__$130;
   static final PyCode _ServerProxy__close$131;
   static final PyCode _ServerProxy__request$132;
   static final PyCode __repr__$133;
   static final PyCode __getattr__$134;
   static final PyCode __call__$135;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nAn XML-RPC client interface for Python.\n\nThe marshalling and response parser code can also be used to\nimplement XML-RPC servers.\n\nExported exceptions:\n\n  Error          Base class for client errors\n  ProtocolError  Indicates an HTTP protocol error\n  ResponseError  Indicates a broken response package\n  Fault          Indicates an XML-RPC fault package\n\nExported classes:\n\n  ServerProxy    Represents a logical connection to an XML-RPC server\n\n  MultiCall      Executor of boxcared xmlrpc requests\n  Boolean        boolean wrapper to generate a \"boolean\" XML-RPC value\n  DateTime       dateTime wrapper for an ISO 8601 string or time tuple or\n                 localtime integer value to generate a \"dateTime.iso8601\"\n                 XML-RPC value\n  Binary         binary data wrapper\n\n  SlowParser     Slow but safe standard parser (based on xmllib)\n  Marshaller     Generate an XML-RPC params chunk from a Python data structure\n  Unmarshaller   Unmarshal an XML-RPC response from incoming XML event message\n  Transport      Handles an HTTP transaction to an XML-RPC server\n  SafeTransport  Handles an HTTPS transaction to an XML-RPC server\n\nExported constants:\n\n  True\n  False\n\nExported functions:\n\n  boolean        Convert any Python value to an XML-RPC boolean\n  getparser      Create instance of the fastest available parser & attach\n                 to an unmarshalling object\n  dumps          Convert an argument tuple or a Fault instance to an XML-RPC\n                 request (or response, if the methodresponse option is used).\n  loads          Convert an XML-RPC packet to unmarshalled data plus a method\n                 name (None if not present).\n"));
      var1.setline(137);
      PyString.fromInterned("\nAn XML-RPC client interface for Python.\n\nThe marshalling and response parser code can also be used to\nimplement XML-RPC servers.\n\nExported exceptions:\n\n  Error          Base class for client errors\n  ProtocolError  Indicates an HTTP protocol error\n  ResponseError  Indicates a broken response package\n  Fault          Indicates an XML-RPC fault package\n\nExported classes:\n\n  ServerProxy    Represents a logical connection to an XML-RPC server\n\n  MultiCall      Executor of boxcared xmlrpc requests\n  Boolean        boolean wrapper to generate a \"boolean\" XML-RPC value\n  DateTime       dateTime wrapper for an ISO 8601 string or time tuple or\n                 localtime integer value to generate a \"dateTime.iso8601\"\n                 XML-RPC value\n  Binary         binary data wrapper\n\n  SlowParser     Slow but safe standard parser (based on xmllib)\n  Marshaller     Generate an XML-RPC params chunk from a Python data structure\n  Unmarshaller   Unmarshal an XML-RPC response from incoming XML event message\n  Transport      Handles an HTTP transaction to an XML-RPC server\n  SafeTransport  Handles an HTTPS transaction to an XML-RPC server\n\nExported constants:\n\n  True\n  False\n\nExported functions:\n\n  boolean        Convert any Python value to an XML-RPC boolean\n  getparser      Create instance of the fastest available parser & attach\n                 to an unmarshalling object\n  dumps          Convert an argument tuple or a Fault instance to an XML-RPC\n                 request (or response, if the methodresponse option is used).\n  loads          Convert an XML-RPC packet to unmarshalled data plus a method\n                 name (None if not present).\n");
      var1.setline(139);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var3);
      var3 = null;
      var1.setline(141);
      imp.importAll("types", var1, -1);
      var1.setline(142);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(143);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(144);
      var3 = imp.importOne("httplib", var1, -1);
      var1.setlocal("httplib", var3);
      var3 = null;

      PyObject var4;
      PyException var17;
      try {
         var1.setline(146);
         var3 = imp.importOne("gzip", var1, -1);
         var1.setlocal("gzip", var3);
         var3 = null;
      } catch (Throwable var15) {
         var17 = Py.setException(var15, var1);
         if (!var17.match(var1.getname("ImportError"))) {
            throw var17;
         }

         var1.setline(148);
         var4 = var1.getname("None");
         var1.setlocal("gzip", var4);
         var4 = null;
      }

      try {
         var1.setline(154);
         var1.getname("unicode");
      } catch (Throwable var14) {
         var17 = Py.setException(var14, var1);
         if (!var17.match(var1.getname("NameError"))) {
            throw var17;
         }

         var1.setline(156);
         var4 = var1.getname("None");
         var1.setlocal("unicode", var4);
         var4 = null;
      }

      try {
         var1.setline(159);
         var3 = imp.importOne("datetime", var1, -1);
         var1.setlocal("datetime", var3);
         var3 = null;
      } catch (Throwable var13) {
         var17 = Py.setException(var13, var1);
         if (!var17.match(var1.getname("ImportError"))) {
            throw var17;
         }

         var1.setline(161);
         var4 = var1.getname("None");
         var1.setlocal("datetime", var4);
         var4 = null;
      }

      PyObject var10000;
      try {
         var1.setline(164);
         var3 = var1.getname("False").__getattr__("__class__").__getattr__("__name__");
         var10000 = var3._eq(PyString.fromInterned("bool"));
         var3 = null;
         var3 = var10000;
         var1.setlocal("_bool_is_builtin", var3);
         var3 = null;
      } catch (Throwable var12) {
         var17 = Py.setException(var12, var1);
         if (!var17.match(var1.getname("NameError"))) {
            throw var17;
         }

         var1.setline(166);
         PyInteger var16 = Py.newInteger(0);
         var1.setlocal("_bool_is_builtin", var16);
         var4 = null;
      }

      var1.setline(168);
      PyObject[] var18 = new PyObject[]{var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\u0080-ÿ]")).__getattr__("search")};
      PyFunction var19 = new PyFunction(var1.f_globals, var18, _decode$1, (PyObject)null);
      var1.setlocal("_decode", var19);
      var3 = null;
      var1.setline(174);
      var18 = new PyObject[]{var1.getname("string").__getattr__("replace")};
      var19 = new PyFunction(var1.f_globals, var18, escape$2, (PyObject)null);
      var1.setlocal("escape", var19);
      var3 = null;
      var1.setline(179);
      if (var1.getname("unicode").__nonzero__()) {
         var1.setline(180);
         var18 = Py.EmptyObjects;
         var19 = new PyFunction(var1.f_globals, var18, _stringify$3, (PyObject)null);
         var1.setlocal("_stringify", var19);
         var3 = null;
      } else {
         var1.setline(187);
         var18 = Py.EmptyObjects;
         var19 = new PyFunction(var1.f_globals, var18, _stringify$4, (PyObject)null);
         var1.setlocal("_stringify", var19);
         var3 = null;
      }

      var1.setline(190);
      PyString var21 = PyString.fromInterned("1.0.1");
      var1.setlocal("__version__", var21);
      var3 = null;
      var1.setline(193);
      var3 = Py.newLong("2")._pow(Py.newInteger(31))._sub(Py.newInteger(1));
      var1.setlocal("MAXINT", var3);
      var3 = null;
      var1.setline(194);
      var3 = Py.newLong("2")._pow(Py.newInteger(31)).__neg__();
      var1.setlocal("MININT", var3);
      var3 = null;
      var1.setline(201);
      PyInteger var22 = Py.newInteger(-32700);
      var1.setlocal("PARSE_ERROR", var22);
      var3 = null;
      var1.setline(202);
      var22 = Py.newInteger(-32600);
      var1.setlocal("SERVER_ERROR", var22);
      var3 = null;
      var1.setline(203);
      var22 = Py.newInteger(-32500);
      var1.setlocal("APPLICATION_ERROR", var22);
      var3 = null;
      var1.setline(204);
      var22 = Py.newInteger(-32400);
      var1.setlocal("SYSTEM_ERROR", var22);
      var3 = null;
      var1.setline(205);
      var22 = Py.newInteger(-32300);
      var1.setlocal("TRANSPORT_ERROR", var22);
      var3 = null;
      var1.setline(208);
      var22 = Py.newInteger(-32700);
      var1.setlocal("NOT_WELLFORMED_ERROR", var22);
      var3 = null;
      var1.setline(209);
      var22 = Py.newInteger(-32701);
      var1.setlocal("UNSUPPORTED_ENCODING", var22);
      var3 = null;
      var1.setline(210);
      var22 = Py.newInteger(-32702);
      var1.setlocal("INVALID_ENCODING_CHAR", var22);
      var3 = null;
      var1.setline(211);
      var22 = Py.newInteger(-32600);
      var1.setlocal("INVALID_XMLRPC", var22);
      var3 = null;
      var1.setline(212);
      var22 = Py.newInteger(-32601);
      var1.setlocal("METHOD_NOT_FOUND", var22);
      var3 = null;
      var1.setline(213);
      var22 = Py.newInteger(-32602);
      var1.setlocal("INVALID_METHOD_PARAMS", var22);
      var3 = null;
      var1.setline(214);
      var22 = Py.newInteger(-32603);
      var1.setlocal("INTERNAL_ERROR", var22);
      var3 = null;
      var1.setline(222);
      var18 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var18, Error$5);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(237);
      var18 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("ProtocolError", var18, ProtocolError$7);
      var1.setlocal("ProtocolError", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(256);
      var18 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("ResponseError", var18, ResponseError$10);
      var1.setlocal("ResponseError", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(269);
      var18 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("Fault", var18, Fault$11);
      var1.setlocal("Fault", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(292);
      String[] var23 = new String[]{"modules"};
      var18 = imp.importFrom("sys", var23, var1, -1);
      var4 = var18[0];
      var1.setlocal("modules", var4);
      var4 = null;
      var1.setline(293);
      var3 = var1.getname("modules").__getitem__(var1.getname("__name__")).__getattr__("__dict__");
      var1.setlocal("mod_dict", var3);
      var3 = null;
      var1.setline(294);
      if (var1.getname("_bool_is_builtin").__nonzero__()) {
         var1.setline(295);
         var3 = var1.getname("bool");
         var1.setlocal("boolean", var3);
         var1.setlocal("Boolean", var3);
         var1.setline(297);
         var3 = var1.getname("True");
         var1.getname("mod_dict").__setitem__((PyObject)PyString.fromInterned("True"), var3);
         var3 = null;
         var1.setline(298);
         var3 = var1.getname("False");
         var1.getname("mod_dict").__setitem__((PyObject)PyString.fromInterned("False"), var3);
         var3 = null;
      } else {
         var1.setline(300);
         var18 = Py.EmptyObjects;
         var4 = Py.makeClass("Boolean", var18, Boolean$14);
         var1.setlocal("Boolean", var4);
         var4 = null;
         Arrays.fill(var18, (Object)null);
         var1.setline(329);
         var3 = var1.getname("Boolean").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.getname("mod_dict").__setitem__((PyObject)PyString.fromInterned("True"), var3);
         var3 = null;
         var1.setline(330);
         var3 = var1.getname("Boolean").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.getname("mod_dict").__setitem__((PyObject)PyString.fromInterned("False"), var3);
         var3 = null;
         var1.setline(343);
         var18 = new PyObject[]{new PyTuple(new PyObject[]{var1.getname("False"), var1.getname("True")})};
         var19 = new PyFunction(var1.f_globals, var18, boolean$21, PyString.fromInterned("Convert any Python value to XML-RPC 'boolean'."));
         var1.setlocal("boolean", var19);
         var3 = null;
      }

      var1.setline(347);
      var1.dellocal("modules");
      var1.dellocal("mod_dict");
      var1.setline(362);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, _strftime$22, (PyObject)null);
      var1.setlocal("_strftime", var19);
      var3 = null;
      var1.setline(376);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("DateTime", var18, DateTime$23);
      var1.setlocal("DateTime", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(460);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, _datetime$38, (PyObject)null);
      var1.setlocal("_datetime", var19);
      var3 = null;
      var1.setline(466);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, _datetime_type$39, (PyObject)null);
      var1.setlocal("_datetime_type", var19);
      var3 = null;
      var1.setline(476);
      var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;

      try {
         var1.setline(478);
         var3 = imp.importOneAs("cStringIO", var1, -1);
         var1.setlocal("StringIO", var3);
         var3 = null;
      } catch (Throwable var11) {
         var17 = Py.setException(var11, var1);
         if (!var17.match(var1.getname("ImportError"))) {
            throw var17;
         }

         var1.setline(480);
         var4 = imp.importOne("StringIO", var1, -1);
         var1.setlocal("StringIO", var4);
         var4 = null;
      }

      var1.setline(482);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("Binary", var18, Binary$40);
      var1.setlocal("Binary", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(509);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, _binary$46, (PyObject)null);
      var1.setlocal("_binary", var19);
      var3 = null;
      var1.setline(515);
      PyTuple var24 = new PyTuple(new PyObject[]{var1.getname("DateTime"), var1.getname("Binary")});
      var1.setlocal("WRAPPERS", var24);
      var3 = null;
      var1.setline(516);
      if (var1.getname("_bool_is_builtin").__not__().__nonzero__()) {
         var1.setline(517);
         var3 = var1.getname("WRAPPERS")._add(new PyTuple(new PyObject[]{var1.getname("Boolean")}));
         var1.setlocal("WRAPPERS", var3);
         var3 = null;
      }

      try {
         var1.setline(524);
         var3 = imp.importOne("_xmlrpclib", var1, -1);
         var1.setlocal("_xmlrpclib", var3);
         var3 = null;
         var1.setline(525);
         var3 = var1.getname("_xmlrpclib").__getattr__("Parser");
         var1.setlocal("FastParser", var3);
         var3 = null;
         var1.setline(526);
         var3 = var1.getname("_xmlrpclib").__getattr__("Unmarshaller");
         var1.setlocal("FastUnmarshaller", var3);
         var3 = null;
      } catch (Throwable var10) {
         var17 = Py.setException(var10, var1);
         if (!var17.match(new PyTuple(new PyObject[]{var1.getname("AttributeError"), var1.getname("ImportError")}))) {
            throw var17;
         }

         var1.setline(528);
         var4 = var1.getname("None");
         var1.setlocal("FastParser", var4);
         var1.setlocal("FastUnmarshaller", var4);
      }

      try {
         var1.setline(531);
         var3 = imp.importOne("_xmlrpclib", var1, -1);
         var1.setlocal("_xmlrpclib", var3);
         var3 = null;
         var1.setline(532);
         var3 = var1.getname("_xmlrpclib").__getattr__("Marshaller");
         var1.setlocal("FastMarshaller", var3);
         var3 = null;
      } catch (Throwable var9) {
         var17 = Py.setException(var9, var1);
         if (!var17.match(new PyTuple(new PyObject[]{var1.getname("AttributeError"), var1.getname("ImportError")}))) {
            throw var17;
         }

         var1.setline(534);
         var4 = var1.getname("None");
         var1.setlocal("FastMarshaller", var4);
         var4 = null;
      }

      label122: {
         try {
            var1.setline(537);
            var23 = new String[]{"expat"};
            var18 = imp.importFrom("xml.parsers", var23, var1, -1);
            var4 = var18[0];
            var1.setlocal("expat", var4);
            var4 = null;
            var1.setline(538);
            if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("expat"), (PyObject)PyString.fromInterned("ParserCreate")).__not__().__nonzero__()) {
               var1.setline(539);
               throw Py.makeException(var1.getname("ImportError"));
            }
         } catch (Throwable var8) {
            var17 = Py.setException(var8, var1);
            if (var17.match(var1.getname("ImportError"))) {
               var1.setline(541);
               var4 = var1.getname("None");
               var1.setlocal("ExpatParser", var4);
               var4 = null;
               break label122;
            }

            throw var17;
         }

         var1.setline(543);
         PyObject[] var20 = Py.EmptyObjects;
         PyObject var5 = Py.makeClass("ExpatParser", var20, ExpatParser$47);
         var1.setlocal("ExpatParser", var5);
         var5 = null;
         Arrays.fill(var20, (Object)null);
      }

      var1.setline(563);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("SlowParser", var18, SlowParser$51);
      var1.setlocal("SlowParser", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(590);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("Marshaller", var18, Marshaller$53);
      var1.setlocal("Marshaller", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(764);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("Unmarshaller", var18, Unmarshaller$68);
      var1.setlocal("Unmarshaller", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(935);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("_MultiCallMethod", var18, _MultiCallMethod$90);
      var1.setlocal("_MultiCallMethod", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(946);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("MultiCallIterator", var18, MultiCallIterator$94);
      var1.setlocal("MultiCallIterator", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(963);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("MultiCall", var18, MultiCall$97);
      var1.setlocal("MultiCall", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1008);
      var18 = new PyObject[]{Py.newInteger(0)};
      var19 = new PyFunction(var1.f_globals, var18, getparser$102, PyString.fromInterned("getparser() -> parser, unmarshaller\n\n    Create an instance of the fastest available parser, and attach it\n    to an unmarshalling object.  Return both objects.\n    "));
      var1.setlocal("getparser", var19);
      var3 = null;
      var1.setline(1046);
      var18 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      var19 = new PyFunction(var1.f_globals, var18, dumps$103, PyString.fromInterned("data [,options] -> marshalled data\n\n    Convert an argument tuple or a Fault instance to an XML-RPC\n    request (or response, if the methodresponse option is used).\n\n    In addition to the data object, the following options can be given\n    as keyword arguments:\n\n        methodname: the method name for a methodCall packet\n\n        methodresponse: true to create a methodResponse packet.\n        If this option is used with a tuple, the tuple must be\n        a singleton (i.e. it can contain only one element).\n\n        encoding: the packet encoding (default is UTF-8)\n\n    All 8-bit strings in the data structure are assumed to use the\n    packet encoding.  Unicode strings are automatically converted,\n    where necessary.\n    "));
      var1.setlocal("dumps", var19);
      var3 = null;
      var1.setline(1125);
      var18 = new PyObject[]{Py.newInteger(0)};
      var19 = new PyFunction(var1.f_globals, var18, loads$104, PyString.fromInterned("data -> unmarshalled data, method name\n\n    Convert an XML-RPC packet to unmarshalled data plus a method\n    name (None if not present).\n\n    If the XML-RPC packet represents a fault condition, this function\n    raises a Fault exception.\n    "));
      var1.setlocal("loads", var19);
      var3 = null;
      var1.setline(1147);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, gzip_encode$105, PyString.fromInterned("data -> gzip encoded data\n\n    Encode data using the gzip content encoding as described in RFC 1952\n    "));
      var1.setlocal("gzip_encode", var19);
      var3 = null;
      var1.setline(1171);
      var18 = Py.EmptyObjects;
      var19 = new PyFunction(var1.f_globals, var18, gzip_decode$106, PyString.fromInterned("gzip encoded data -> unencoded data\n\n    Decode data using the gzip content encoding as described in RFC 1952\n    "));
      var1.setlocal("gzip_decode", var19);
      var3 = null;
      var1.setline(1195);
      var18 = new PyObject[1];
      var1.setline(1195);
      var18[0] = var1.getname("gzip").__nonzero__() ? var1.getname("gzip").__getattr__("GzipFile") : var1.getname("object");
      var4 = Py.makeClass("GzipDecodedResponse", var18, GzipDecodedResponse$107);
      var1.setlocal("GzipDecodedResponse", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1215);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("_Method", var18, _Method$110);
      var1.setlocal("_Method", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1232);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("Transport", var18, Transport$114);
      var1.setlocal("Transport", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1478);
      var18 = new PyObject[]{var1.getname("Transport")};
      var4 = Py.makeClass("SafeTransport", var18, SafeTransport$127);
      var1.setlocal("SafeTransport", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1516);
      var18 = Py.EmptyObjects;
      var4 = Py.makeClass("ServerProxy", var18, ServerProxy$129);
      var1.setlocal("ServerProxy", var4);
      var4 = null;
      Arrays.fill(var18, (Object)null);
      var1.setline(1613);
      var3 = var1.getname("ServerProxy");
      var1.setlocal("Server", var3);
      var3 = null;
      var1.setline(1618);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1623);
         var3 = var1.getname("ServerProxy").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://time.xmlrpc.com/RPC2"));
         var1.setlocal("server", var3);
         var3 = null;
         var1.setline(1625);
         Py.println(var1.getname("server"));

         try {
            var1.setline(1628);
            Py.println(var1.getname("server").__getattr__("currentTime").__getattr__("getCurrentTime").__call__(var2));
         } catch (Throwable var7) {
            var17 = Py.setException(var7, var1);
            if (!var17.match(var1.getname("Error"))) {
               throw var17;
            }

            var4 = var17.value;
            var1.setlocal("v", var4);
            var4 = null;
            var1.setline(1630);
            Py.printComma(PyString.fromInterned("ERROR"));
            Py.println(var1.getname("v"));
         }

         var1.setline(1632);
         var3 = var1.getname("MultiCall").__call__(var2, var1.getname("server"));
         var1.setlocal("multi", var3);
         var3 = null;
         var1.setline(1633);
         var1.getname("multi").__getattr__("currentTime").__getattr__("getCurrentTime").__call__(var2);
         var1.setline(1634);
         var1.getname("multi").__getattr__("currentTime").__getattr__("getCurrentTime").__call__(var2);

         try {
            var1.setline(1636);
            var3 = var1.getname("multi").__call__(var2).__iter__();

            while(true) {
               var1.setline(1636);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal("response", var4);
               var1.setline(1637);
               Py.println(var1.getname("response"));
            }
         } catch (Throwable var6) {
            var17 = Py.setException(var6, var1);
            if (!var17.match(var1.getname("Error"))) {
               throw var17;
            }

            var4 = var17.value;
            var1.setlocal("v", var4);
            var4 = null;
            var1.setline(1639);
            Py.printComma(PyString.fromInterned("ERROR"));
            Py.println(var1.getname("v"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _decode$1(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var10000 = var1.getglobal("unicode");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
         }
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(171);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(172);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject escape$2(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = var1.getlocal(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getlocal(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(177);
      var3 = var1.getlocal(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _stringify$3(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(183);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("UnicodeError"))) {
            var1.setline(185);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _stringify$4(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Error$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for client errors."));
      var1.setline(223);
      PyString.fromInterned("Base class for client errors.");
      var1.setline(224);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __str__$6, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __str__$6(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ProtocolError$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Indicates an HTTP protocol error."));
      var1.setline(238);
      PyString.fromInterned("Indicates an HTTP protocol error.");
      var1.setline(239);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(245);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$9, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(241);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("url", var3);
      var3 = null;
      var1.setline(242);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("errcode", var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("errmsg", var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("headers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$9(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyObject var3 = PyString.fromInterned("<ProtocolError for %s: %s %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("url"), var1.getlocal(0).__getattr__("errcode"), var1.getlocal(0).__getattr__("errmsg")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ResponseError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Indicates a broken response package."));
      var1.setline(257);
      PyString.fromInterned("Indicates a broken response package.");
      var1.setline(258);
      return var1.getf_locals();
   }

   public PyObject Fault$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Indicates an XML-RPC fault package."));
      var1.setline(270);
      PyString.fromInterned("Indicates an XML-RPC fault package.");
      var1.setline(271);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$13, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(273);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("faultCode", var3);
      var3 = null;
      var1.setline(274);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("faultString", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$13(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var3 = PyString.fromInterned("<Fault %s: %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("faultCode"), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("faultString"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Boolean$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Boolean-value wrapper.\n\n        Use True or False to generate a \"boolean\" XML-RPC value.\n        "));
      var1.setline(304);
      PyString.fromInterned("Boolean-value wrapper.\n\n        Use True or False to generate a \"boolean\" XML-RPC value.\n        ");
      var1.setline(306);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encode$16, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(312);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$17, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$18, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __int__$19, (PyObject)null);
      var1.setlocal("__int__", var4);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __nonzero__$20, (PyObject)null);
      var1.setlocal("__nonzero__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getglobal("operator").__getattr__("truth").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$16(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("<value><boolean>%d</boolean></value>\n")._mod(var1.getlocal(0).__getattr__("value")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __cmp__$17(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Boolean")).__nonzero__()) {
         var1.setline(314);
         var3 = var1.getlocal(1).__getattr__("value");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(315);
      var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("value"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$18(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("value").__nonzero__()) {
         var1.setline(319);
         var3 = PyString.fromInterned("<Boolean True at %x>")._mod(var1.getglobal("id").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(321);
         var3 = PyString.fromInterned("<Boolean False at %x>")._mod(var1.getglobal("id").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __int__$19(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getlocal(0).__getattr__("value");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$20(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getlocal(0).__getattr__("value");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject boolean$21(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned("Convert any Python value to XML-RPC 'boolean'.");
      var1.setline(345);
      PyObject var3 = var1.getlocal(1).__getitem__(var1.getglobal("operator").__getattr__("truth").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _strftime$22(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3;
      if (var1.getglobal("datetime").__nonzero__()) {
         var1.setline(364);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("datetime").__getattr__("datetime")).__nonzero__()) {
            var1.setline(365);
            var3 = PyString.fromInterned("%04d%02d%02dT%02d:%02d:%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month"), var1.getlocal(0).__getattr__("day"), var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second")}));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(369);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("TupleType"), var1.getglobal("time").__getattr__("struct_time")}))).__not__().__nonzero__()) {
         var1.setline(370);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(371);
            var4 = var1.getglobal("time").__getattr__("time").__call__(var2);
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(372);
         var4 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var4);
         var4 = null;
      }

      var1.setline(374);
      var3 = PyString.fromInterned("%04d%02d%02dT%02d:%02d:%02d")._mod(var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DateTime$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("DateTime wrapper for an ISO 8601 string or time tuple or\n    localtime integer value to generate 'dateTime.iso8601' XML-RPC\n    value.\n    "));
      var1.setline(380);
      PyString.fromInterned("DateTime wrapper for an ISO 8601 string or time tuple or\n    localtime integer value to generate 'dateTime.iso8601' XML-RPC\n    value.\n    ");
      var1.setline(382);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$24, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_comparable$25, (PyObject)null);
      var1.setlocal("make_comparable", var4);
      var3 = null;
      var1.setline(409);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __lt__$26, (PyObject)null);
      var1.setlocal("__lt__", var4);
      var3 = null;
      var1.setline(413);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __le__$27, (PyObject)null);
      var1.setlocal("__le__", var4);
      var3 = null;
      var1.setline(417);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __gt__$28, (PyObject)null);
      var1.setlocal("__gt__", var4);
      var3 = null;
      var1.setline(421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ge__$29, (PyObject)null);
      var1.setlocal("__ge__", var4);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$30, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(429);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$31, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(433);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, timetuple$32, (PyObject)null);
      var1.setlocal("timetuple", var4);
      var3 = null;
      var1.setline(436);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$33, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(445);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$34, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$35, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decode$36, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encode$37, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType")).__nonzero__()) {
         var1.setline(384);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("value", var3);
         var3 = null;
      } else {
         var1.setline(386);
         var3 = var1.getglobal("_strftime").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("value", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_comparable$25(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("DateTime")).__nonzero__()) {
         var1.setline(390);
         var3 = var1.getlocal(0).__getattr__("value");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(391);
         var3 = var1.getlocal(1).__getattr__("value");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(392);
         PyObject var10000 = var1.getglobal("datetime");
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime").__getattr__("datetime"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(393);
            var3 = var1.getlocal(0).__getattr__("value");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(394);
            var3 = var1.getlocal(1).__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y%m%dT%H:%M:%S"));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(395);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__nonzero__()) {
               var1.setline(396);
               var3 = var1.getlocal(0).__getattr__("value");
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(397);
               var3 = var1.getlocal(1);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(398);
               if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
                  var1.setline(402);
                  var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__class__"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1).__getattr__("__class__").__getattr__("__name__");
                  }

                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
                  }

                  var3 = var10000;
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(405);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Can't compare %s and %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(4)}))));
               }

               var1.setline(399);
               var3 = var1.getlocal(0).__getattr__("timetuple").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(400);
               var3 = var1.getlocal(1).__getattr__("timetuple").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }

      var1.setline(407);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __lt__$26(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(411);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __le__$27(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(415);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._le(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __gt__$28(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(419);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._gt(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ge__$29(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(423);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ge(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$30(PyFrame var1, ThreadState var2) {
      var1.setline(426);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(427);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ne__$31(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(431);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject timetuple$32(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyObject var3 = var1.getglobal("time").__getattr__("strptime").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("value"), (PyObject)PyString.fromInterned("%Y%m%dT%H:%M:%S"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$33(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      PyObject var3 = var1.getlocal(0).__getattr__("make_comparable").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(438);
      var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$34(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("value");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$35(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = PyString.fromInterned("<DateTime %s at %x>")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value")), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$36(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(453);
      var3 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$37(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><dateTime.iso8601>"));
      var1.setline(457);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("value"));
      var1.setline(458);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</dateTime.iso8601></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _datetime$38(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getglobal("DateTime").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(463);
      var1.getlocal(1).__getattr__("decode").__call__(var2, var1.getlocal(0));
      var1.setline(464);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _datetime_type$39(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = var1.getglobal("time").__getattr__("strptime").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("%Y%m%dT%H:%M:%S"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(468);
      PyObject var10000 = var1.getglobal("datetime").__getattr__("datetime");
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getglobal("tuple").__call__(var2, var1.getlocal(1)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Binary$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wrapper for binary data."));
      var1.setline(483);
      PyString.fromInterned("Wrapper for binary data.");
      var1.setline(485);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(493);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$42, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$43, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(501);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decode$44, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(504);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encode$45, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(486);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$42(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      Object var10000 = var1.getlocal(0).__getattr__("data");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject __cmp__$43(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Binary")).__nonzero__()) {
         var1.setline(498);
         var3 = var1.getlocal(1).__getattr__("data");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(499);
      var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$44(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      PyObject var3 = var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$45(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><base64>\n"));
      var1.setline(506);
      var1.getglobal("base64").__getattr__("encode").__call__(var2, var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0).__getattr__("data")), var1.getlocal(1));
      var1.setline(507);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</base64></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _binary$46(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyObject var3 = var1.getglobal("Binary").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(512);
      var1.getlocal(1).__getattr__("decode").__call__(var2, var1.getlocal(0));
      var1.setline(513);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ExpatParser$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(545);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$48, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(556);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, feed$49, (PyObject)null);
      var1.setlocal("feed", var4);
      var3 = null;
      var1.setline(559);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$50, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$48(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyObject var3 = var1.getglobal("expat").__getattr__("ParserCreate").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.getlocal(0).__setattr__("_parser", var3);
      var1.setlocal(2, var3);
      var1.setline(547);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_target", var3);
      var3 = null;
      var1.setline(548);
      var3 = var1.getlocal(1).__getattr__("start");
      var1.getlocal(2).__setattr__("StartElementHandler", var3);
      var3 = null;
      var1.setline(549);
      var3 = var1.getlocal(1).__getattr__("end");
      var1.getlocal(2).__setattr__("EndElementHandler", var3);
      var3 = null;
      var1.setline(550);
      var3 = var1.getlocal(1).__getattr__("data");
      var1.getlocal(2).__setattr__("CharacterDataHandler", var3);
      var3 = null;
      var1.setline(551);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(552);
      if (var1.getlocal(2).__getattr__("returns_unicode").__not__().__nonzero__()) {
         var1.setline(553);
         PyString var4 = PyString.fromInterned("utf-8");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(554);
      var1.getlocal(1).__getattr__("xml").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$49(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      var1.getlocal(0).__getattr__("_parser").__getattr__("Parse").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$50(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      var1.getlocal(0).__getattr__("_parser").__getattr__("Parse").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(1));
      var1.setline(561);
      var1.getlocal(0).__delattr__("_target");
      var1.getlocal(0).__delattr__("_parser");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SlowParser$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Default XML parser (based on xmllib.XMLParser)."));
      var1.setline(564);
      PyString.fromInterned("Default XML parser (based on xmllib.XMLParser).");
      var1.setline(566);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$52, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$52(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyObject var3 = imp.importOne("xmllib", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(568);
      var3 = var1.getlocal(2).__getattr__("XMLParser");
      PyObject var10000 = var3._notin(var1.getglobal("SlowParser").__getattr__("__bases__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(569);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("XMLParser")});
         var1.getglobal("SlowParser").__setattr__((String)"__bases__", var6);
         var3 = null;
      }

      var1.setline(570);
      var3 = var1.getlocal(1).__getattr__("xml");
      var1.getlocal(0).__setattr__("handle_xml", var3);
      var3 = null;
      var1.setline(571);
      var3 = var1.getlocal(1).__getattr__("start");
      var1.getlocal(0).__setattr__("unknown_starttag", var3);
      var3 = null;
      var1.setline(572);
      var3 = var1.getlocal(1).__getattr__("data");
      var1.getlocal(0).__setattr__("handle_data", var3);
      var3 = null;
      var1.setline(573);
      var3 = var1.getlocal(1).__getattr__("data");
      var1.getlocal(0).__setattr__("handle_cdata", var3);
      var3 = null;
      var1.setline(574);
      var3 = var1.getlocal(1).__getattr__("end");
      var1.getlocal(0).__setattr__("unknown_endtag", var3);
      var3 = null;

      try {
         var1.setline(576);
         var10000 = var1.getlocal(2).__getattr__("XMLParser").__getattr__("__init__");
         PyObject[] var8 = new PyObject[]{var1.getlocal(0), Py.newInteger(1)};
         String[] var4 = new String[]{"accept_utf8"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getglobal("TypeError"))) {
            throw var7;
         }

         var1.setline(578);
         var1.getlocal(2).__getattr__("XMLParser").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Marshaller$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generate an XML-RPC params chunk from a Python data structure.\n\n    Create a Marshaller instance for each set of parameters, and use\n    the \"dumps\" method to convert your data (represented as a tuple)\n    to an XML-RPC params chunk.  To write a fault response, pass a\n    Fault instance instead.  You may prefer to use the \"dumps\" module\n    function for this purpose.\n    "));
      var1.setline(598);
      PyString.fromInterned("Generate an XML-RPC params chunk from a Python data structure.\n\n    Create a Marshaller instance for each set of parameters, and use\n    the \"dumps\" method to convert your data (represented as a tuple)\n    to an XML-RPC params chunk.  To write a fault response, pass a\n    Fault instance instead.  You may prefer to use the \"dumps\" module\n    function for this purpose.\n    ");
      var1.setline(603);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$54, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(609);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("dispatch", var5);
      var3 = null;
      var1.setline(611);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dumps$55, (PyObject)null);
      var1.setlocal("dumps", var4);
      var3 = null;
      var1.setline(638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Marshaller__dump$56, (PyObject)null);
      var1.setlocal("_Marshaller__dump", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_nil$57, (PyObject)null);
      var1.setlocal("dump_nil", var4);
      var3 = null;
      var1.setline(660);
      PyObject var6 = var1.getname("dump_nil");
      var1.getname("dispatch").__setitem__(var1.getname("NoneType"), var6);
      var3 = null;
      var1.setline(662);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_int$58, (PyObject)null);
      var1.setlocal("dump_int", var4);
      var3 = null;
      var1.setline(669);
      var6 = var1.getname("dump_int");
      var1.getname("dispatch").__setitem__(var1.getname("IntType"), var6);
      var3 = null;
      var1.setline(671);
      if (var1.getname("_bool_is_builtin").__nonzero__()) {
         var1.setline(672);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, dump_bool$59, (PyObject)null);
         var1.setlocal("dump_bool", var4);
         var3 = null;
         var1.setline(676);
         var6 = var1.getname("dump_bool");
         var1.getname("dispatch").__setitem__(var1.getname("bool"), var6);
         var3 = null;
      }

      var1.setline(678);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_long$60, (PyObject)null);
      var1.setlocal("dump_long", var4);
      var3 = null;
      var1.setline(684);
      var6 = var1.getname("dump_long");
      var1.getname("dispatch").__setitem__(var1.getname("LongType"), var6);
      var3 = null;
      var1.setline(686);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_double$61, (PyObject)null);
      var1.setlocal("dump_double", var4);
      var3 = null;
      var1.setline(690);
      var6 = var1.getname("dump_double");
      var1.getname("dispatch").__setitem__(var1.getname("FloatType"), var6);
      var3 = null;
      var1.setline(692);
      var3 = new PyObject[]{var1.getname("escape")};
      var4 = new PyFunction(var1.f_globals, var3, dump_string$62, (PyObject)null);
      var1.setlocal("dump_string", var4);
      var3 = null;
      var1.setline(696);
      var6 = var1.getname("dump_string");
      var1.getname("dispatch").__setitem__(var1.getname("StringType"), var6);
      var3 = null;
      var1.setline(698);
      if (var1.getname("unicode").__nonzero__()) {
         var1.setline(699);
         var3 = new PyObject[]{var1.getname("escape")};
         var4 = new PyFunction(var1.f_globals, var3, dump_unicode$63, (PyObject)null);
         var1.setlocal("dump_unicode", var4);
         var3 = null;
         var1.setline(704);
         var6 = var1.getname("dump_unicode");
         var1.getname("dispatch").__setitem__(var1.getname("UnicodeType"), var6);
         var3 = null;
      }

      var1.setline(706);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_array$64, (PyObject)null);
      var1.setlocal("dump_array", var4);
      var3 = null;
      var1.setline(717);
      var6 = var1.getname("dump_array");
      var1.getname("dispatch").__setitem__(var1.getname("TupleType"), var6);
      var3 = null;
      var1.setline(718);
      var6 = var1.getname("dump_array");
      var1.getname("dispatch").__setitem__(var1.getname("ListType"), var6);
      var3 = null;
      var1.setline(720);
      var3 = new PyObject[]{var1.getname("escape")};
      var4 = new PyFunction(var1.f_globals, var3, dump_struct$65, (PyObject)null);
      var1.setlocal("dump_struct", var4);
      var3 = null;
      var1.setline(739);
      var6 = var1.getname("dump_struct");
      var1.getname("dispatch").__setitem__(var1.getname("DictType"), var6);
      var3 = null;
      var1.setline(741);
      if (var1.getname("datetime").__nonzero__()) {
         var1.setline(742);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, dump_datetime$66, (PyObject)null);
         var1.setlocal("dump_datetime", var4);
         var3 = null;
         var1.setline(746);
         var6 = var1.getname("dump_datetime");
         var1.getname("dispatch").__setitem__(var1.getname("datetime").__getattr__("datetime"), var6);
         var3 = null;
      }

      var1.setline(748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_instance$67, (PyObject)null);
      var1.setlocal("dump_instance", var4);
      var3 = null;
      var1.setline(757);
      var6 = var1.getname("dump_instance");
      var1.getname("dispatch").__setitem__(var1.getname("InstanceType"), var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$54(PyFrame var1, ThreadState var2) {
      var1.setline(604);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"memo", var3);
      var3 = null;
      var1.setline(605);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("data", var4);
      var3 = null;
      var1.setline(606);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("encoding", var4);
      var3 = null;
      var1.setline(607);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("allow_none", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dumps$55(PyFrame var1, ThreadState var2) {
      var1.setline(612);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(613);
      PyObject var5 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(614);
      var5 = var1.getlocal(0).__getattr__("_Marshaller__dump");
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(615);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Fault")).__nonzero__()) {
         var1.setline(617);
         var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<fault>\n"));
         var1.setline(618);
         var1.getlocal(4).__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("faultCode"), var1.getlocal(1).__getattr__("faultCode"), PyString.fromInterned("faultString"), var1.getlocal(1).__getattr__("faultString")})), (PyObject)var1.getlocal(3));
         var1.setline(621);
         var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</fault>\n"));
      } else {
         var1.setline(629);
         var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<params>\n"));
         var1.setline(630);
         var5 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(630);
            PyObject var4 = var5.__iternext__();
            if (var4 == null) {
               var1.setline(634);
               var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</params>\n"));
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(631);
            var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<param>\n"));
            var1.setline(632);
            var1.getlocal(4).__call__(var2, var1.getlocal(5), var1.getlocal(3));
            var1.setline(633);
            var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</param>\n"));
         }
      }

      var1.setline(635);
      var5 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(""));
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(636);
      var5 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _Marshaller__dump$56(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(640);
         PyObject var9 = var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getglobal("type").__call__(var2, var1.getlocal(1)));
         var1.setlocal(3, var9);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         try {
            var1.setline(644);
            var1.getlocal(1).__getattr__("__dict__");
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(646);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("cannot marshal %s objects")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1))));
         }

         var1.setline(650);
         PyObject var4 = var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__mro__").__iter__();

         while(true) {
            var1.setline(650);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(653);
               var4 = var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getglobal("InstanceType"));
               var1.setlocal(3, var4);
               var4 = null;
               break;
            }

            var1.setlocal(4, var5);
            var1.setline(651);
            PyObject var6 = var1.getlocal(4);
            PyObject var10000 = var6._in(var1.getlocal(0).__getattr__("dispatch").__getattr__("keys").__call__(var2));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(652);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("cannot marshal %s objects")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1))));
            }
         }
      }

      var1.setline(654);
      var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_nil$57(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      if (var1.getlocal(0).__getattr__("allow_none").__not__().__nonzero__()) {
         var1.setline(658);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("cannot marshal None unless allow_none is enabled"));
      } else {
         var1.setline(659);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><nil/></value>"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject dump_int$58(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getglobal("MAXINT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(var1.getglobal("MININT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(665);
         throw Py.makeException(var1.getglobal("OverflowError"), PyString.fromInterned("int exceeds XML-RPC limits"));
      } else {
         var1.setline(666);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><int>"));
         var1.setline(667);
         var1.getlocal(2).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)));
         var1.setline(668);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</int></value>\n"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject dump_bool$59(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><boolean>"));
      var1.setline(674);
      PyObject var10000 = var1.getlocal(2);
      Object var10002 = var1.getlocal(1);
      if (((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("1");
      }

      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("0");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(675);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</boolean></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_long$60(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getglobal("MAXINT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(var1.getglobal("MININT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(680);
         throw Py.makeException(var1.getglobal("OverflowError"), PyString.fromInterned("long int exceeds XML-RPC limits"));
      } else {
         var1.setline(681);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><int>"));
         var1.setline(682);
         var1.getlocal(2).__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(1))));
         var1.setline(683);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</int></value>\n"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject dump_double$61(PyFrame var1, ThreadState var2) {
      var1.setline(687);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><double>"));
      var1.setline(688);
      var1.getlocal(2).__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      var1.setline(689);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</double></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_string$62(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><string>"));
      var1.setline(694);
      var1.getlocal(2).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(1)));
      var1.setline(695);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</string></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_unicode$63(PyFrame var1, ThreadState var2) {
      var1.setline(700);
      PyObject var3 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(701);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><string>"));
      var1.setline(702);
      var1.getlocal(2).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(1)));
      var1.setline(703);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</string></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_array$64(PyFrame var1, ThreadState var2) {
      var1.setline(707);
      PyObject var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(708);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("memo"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(709);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("cannot marshal recursive sequences"));
      } else {
         var1.setline(710);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("memo").__setitem__(var1.getlocal(3), var3);
         var3 = null;
         var1.setline(711);
         var3 = var1.getlocal(0).__getattr__("_Marshaller__dump");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(712);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><array><data>\n"));
         var1.setline(713);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(713);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(715);
               var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</data></array></value>\n"));
               var1.setline(716);
               var1.getlocal(0).__getattr__("memo").__delitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(5, var4);
            var1.setline(714);
            var1.getlocal(4).__call__(var2, var1.getlocal(5), var1.getlocal(2));
         }
      }
   }

   public PyObject dump_struct$65(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyObject var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(722);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("memo"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(723);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("cannot marshal recursive dictionaries"));
      } else {
         var1.setline(724);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("memo").__setitem__(var1.getlocal(4), var3);
         var3 = null;
         var1.setline(725);
         var3 = var1.getlocal(0).__getattr__("_Marshaller__dump");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(726);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><struct>\n"));
         var1.setline(727);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(727);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(737);
               var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</struct></value>\n"));
               var1.setline(738);
               var1.getlocal(0).__getattr__("memo").__delitem__(var1.getlocal(4));
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(728);
            var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<member>\n"));
            var1.setline(729);
            PyObject var7 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
            var10000 = var7._isnot(var1.getglobal("StringType"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(730);
               var10000 = var1.getglobal("unicode");
               if (var10000.__nonzero__()) {
                  var7 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
                  var10000 = var7._is(var1.getglobal("UnicodeType"));
                  var5 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(733);
                  throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("dictionary key must be string"));
               }

               var1.setline(731);
               var7 = var1.getlocal(6).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
               var1.setlocal(6, var7);
               var5 = null;
            }

            var1.setline(734);
            var1.getlocal(2).__call__(var2, PyString.fromInterned("<name>%s</name>\n")._mod(var1.getlocal(3).__call__(var2, var1.getlocal(6))));
            var1.setline(735);
            var1.getlocal(5).__call__(var2, var1.getlocal(7), var1.getlocal(2));
            var1.setline(736);
            var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</member>\n"));
         }
      }
   }

   public PyObject dump_datetime$66(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<value><dateTime.iso8601>"));
      var1.setline(744);
      var1.getlocal(2).__call__(var2, var1.getglobal("_strftime").__call__(var2, var1.getlocal(1)));
      var1.setline(745);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</dateTime.iso8601></value>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_instance$67(PyFrame var1, ThreadState var2) {
      var1.setline(750);
      PyObject var3 = var1.getlocal(1).__getattr__("__class__");
      PyObject var10000 = var3._in(var1.getglobal("WRAPPERS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(751);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("write", var3);
         var3 = null;
         var1.setline(752);
         var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(0));
         var1.setline(753);
         var1.getlocal(0).__delattr__("write");
      } else {
         var1.setline(756);
         var1.getlocal(0).__getattr__("dump_struct").__call__(var2, var1.getlocal(1).__getattr__("__dict__"), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Unmarshaller$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Unmarshal an XML-RPC response, based on incoming XML event\n    messages (start, data, end).  Call close() to get the resulting\n    data structure.\n\n    Note that this reader is fairly tolerant, and gladly accepts bogus\n    XML-RPC data without complaining (but not bogus XML).\n    "));
      var1.setline(771);
      PyString.fromInterned("Unmarshal an XML-RPC response, based on incoming XML event\n    messages (start, data, end).  Call close() to get the resulting\n    data structure.\n\n    Note that this reader is fairly tolerant, and gladly accepts bogus\n    XML-RPC data without complaining (but not bogus XML).\n    ");
      var1.setline(776);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$69, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(788);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$70, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmethodname$71, (PyObject)null);
      var1.setlocal("getmethodname", var4);
      var3 = null;
      var1.setline(802);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, xml$72, (PyObject)null);
      var1.setlocal("xml", var4);
      var3 = null;
      var1.setline(806);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start$73, (PyObject)null);
      var1.setlocal("start", var4);
      var3 = null;
      var1.setline(813);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, data$74, (PyObject)null);
      var1.setlocal("data", var4);
      var3 = null;
      var1.setline(816);
      var3 = new PyObject[]{var1.getname("string").__getattr__("join")};
      var4 = new PyFunction(var1.f_globals, var3, end$75, (PyObject)null);
      var1.setlocal("end", var4);
      var3 = null;
      var1.setline(828);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_dispatch$76, (PyObject)null);
      var1.setlocal("end_dispatch", var4);
      var3 = null;
      var1.setline(840);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("dispatch", var5);
      var3 = null;
      var1.setline(842);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_nil$77, (PyObject)null);
      var1.setlocal("end_nil", var4);
      var3 = null;
      var1.setline(845);
      PyObject var6 = var1.getname("end_nil");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("nil"), var6);
      var3 = null;
      var1.setline(847);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_boolean$78, (PyObject)null);
      var1.setlocal("end_boolean", var4);
      var3 = null;
      var1.setline(855);
      var6 = var1.getname("end_boolean");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("boolean"), var6);
      var3 = null;
      var1.setline(857);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_int$79, (PyObject)null);
      var1.setlocal("end_int", var4);
      var3 = null;
      var1.setline(860);
      var6 = var1.getname("end_int");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("i4"), var6);
      var3 = null;
      var1.setline(861);
      var6 = var1.getname("end_int");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("i8"), var6);
      var3 = null;
      var1.setline(862);
      var6 = var1.getname("end_int");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("int"), var6);
      var3 = null;
      var1.setline(864);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_double$80, (PyObject)null);
      var1.setlocal("end_double", var4);
      var3 = null;
      var1.setline(867);
      var6 = var1.getname("end_double");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("double"), var6);
      var3 = null;
      var1.setline(869);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_string$81, (PyObject)null);
      var1.setlocal("end_string", var4);
      var3 = null;
      var1.setline(874);
      var6 = var1.getname("end_string");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("string"), var6);
      var3 = null;
      var1.setline(875);
      var6 = var1.getname("end_string");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("name"), var6);
      var3 = null;
      var1.setline(877);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_array$82, (PyObject)null);
      var1.setlocal("end_array", var4);
      var3 = null;
      var1.setline(882);
      var6 = var1.getname("end_array");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("array"), var6);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_struct$83, (PyObject)null);
      var1.setlocal("end_struct", var4);
      var3 = null;
      var1.setline(893);
      var6 = var1.getname("end_struct");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("struct"), var6);
      var3 = null;
      var1.setline(895);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_base64$84, (PyObject)null);
      var1.setlocal("end_base64", var4);
      var3 = null;
      var1.setline(900);
      var6 = var1.getname("end_base64");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("base64"), var6);
      var3 = null;
      var1.setline(902);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_dateTime$85, (PyObject)null);
      var1.setlocal("end_dateTime", var4);
      var3 = null;
      var1.setline(908);
      var6 = var1.getname("end_dateTime");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("dateTime.iso8601"), var6);
      var3 = null;
      var1.setline(910);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_value$86, (PyObject)null);
      var1.setlocal("end_value", var4);
      var3 = null;
      var1.setline(915);
      var6 = var1.getname("end_value");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("value"), var6);
      var3 = null;
      var1.setline(917);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_params$87, (PyObject)null);
      var1.setlocal("end_params", var4);
      var3 = null;
      var1.setline(919);
      var6 = var1.getname("end_params");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("params"), var6);
      var3 = null;
      var1.setline(921);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_fault$88, (PyObject)null);
      var1.setlocal("end_fault", var4);
      var3 = null;
      var1.setline(923);
      var6 = var1.getname("end_fault");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("fault"), var6);
      var3 = null;
      var1.setline(925);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_methodName$89, (PyObject)null);
      var1.setlocal("end_methodName", var4);
      var3 = null;
      var1.setline(930);
      var6 = var1.getname("end_methodName");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned("methodName"), var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(777);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_type", var3);
      var3 = null;
      var1.setline(778);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_stack", var4);
      var3 = null;
      var1.setline(779);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_marks", var4);
      var3 = null;
      var1.setline(780);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_data", var4);
      var3 = null;
      var1.setline(781);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_methodname", var3);
      var3 = null;
      var1.setline(782);
      PyString var5 = PyString.fromInterned("utf-8");
      var1.getlocal(0).__setattr__((String)"_encoding", var5);
      var3 = null;
      var1.setline(783);
      var3 = var1.getlocal(0).__getattr__("_stack").__getattr__("append");
      var1.getlocal(0).__setattr__("append", var3);
      var3 = null;
      var1.setline(784);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_use_datetime", var3);
      var3 = null;
      var1.setline(785);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("datetime").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(786);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("the datetime module is not available"));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$70(PyFrame var1, ThreadState var2) {
      var1.setline(790);
      PyObject var3 = var1.getlocal(0).__getattr__("_type");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_marks");
      }

      if (var10000.__nonzero__()) {
         var1.setline(791);
         throw Py.makeException(var1.getglobal("ResponseError").__call__(var2));
      } else {
         var1.setline(792);
         var3 = var1.getlocal(0).__getattr__("_type");
         var10000 = var3._eq(PyString.fromInterned("fault"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(793);
            var10000 = var1.getglobal("Fault");
            PyObject[] var5 = Py.EmptyObjects;
            String[] var4 = new String[0];
            var10000 = var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(0).__getattr__("_stack").__getitem__(Py.newInteger(0)));
            var3 = null;
            throw Py.makeException(var10000);
         } else {
            var1.setline(794);
            var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("_stack"));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getmethodname$71(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      PyObject var3 = var1.getlocal(0).__getattr__("_methodname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject xml$72(PyFrame var1, ThreadState var2) {
      var1.setline(803);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start$73(PyFrame var1, ThreadState var2) {
      var1.setline(808);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("array"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("struct"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(809);
         var1.getlocal(0).__getattr__("_marks").__getattr__("append").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_stack")));
      }

      var1.setline(810);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_data", var4);
      var3 = null;
      var1.setline(811);
      var3 = var1.getlocal(1);
      var10000 = var3._eq(PyString.fromInterned("value"));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("_value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject data$74(PyFrame var1, ThreadState var2) {
      var1.setline(814);
      var1.getlocal(0).__getattr__("_data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end$75(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(819);
         PyObject var6 = var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(1));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(821);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(823);
      PyObject var4 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_data"), (PyObject)PyString.fromInterned("")));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject end_dispatch$76(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(831);
         PyObject var6 = var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(1));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(833);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(835);
      PyObject var4 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject end_nil$77(PyFrame var1, ThreadState var2) {
      var1.setline(843);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("None"));
      var1.setline(844);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_boolean$78(PyFrame var1, ThreadState var2) {
      var1.setline(848);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("0"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(849);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("False"));
      } else {
         var1.setline(850);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("1"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(853);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("bad boolean value"));
         }

         var1.setline(851);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("True"));
      }

      var1.setline(854);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_int$79(PyFrame var1, ThreadState var2) {
      var1.setline(858);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(1)));
      var1.setline(859);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_double$80(PyFrame var1, ThreadState var2) {
      var1.setline(865);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(1)));
      var1.setline(866);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_string$81(PyFrame var1, ThreadState var2) {
      var1.setline(870);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_encoding").__nonzero__()) {
         var1.setline(871);
         var3 = var1.getglobal("_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_encoding"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(872);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("_stringify").__call__(var2, var1.getlocal(1)));
      var1.setline(873);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_array$82(PyFrame var1, ThreadState var2) {
      var1.setline(878);
      PyObject var3 = var1.getlocal(0).__getattr__("_marks").__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(880);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_stack").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__getattr__("_stack").__setslice__(var1.getlocal(2), (PyObject)null, (PyObject)null, var4);
      var3 = null;
      var1.setline(881);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_struct$83(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyObject var3 = var1.getlocal(0).__getattr__("_marks").__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(887);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(888);
      var3 = var1.getlocal(0).__getattr__("_stack").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(889);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(889);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(891);
            PyList var7 = new PyList(new PyObject[]{var1.getlocal(3)});
            var1.getlocal(0).__getattr__("_stack").__setslice__(var1.getlocal(2), (PyObject)null, (PyObject)null, var7);
            var3 = null;
            var1.setline(892);
            PyInteger var8 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_value", var8);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(890);
         PyObject var5 = var1.getlocal(4).__getitem__(var1.getlocal(5)._add(Py.newInteger(1)));
         var1.getlocal(3).__setitem__(var1.getglobal("_stringify").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(5))), var5);
         var5 = null;
      }
   }

   public PyObject end_base64$84(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyObject var3 = var1.getglobal("Binary").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(897);
      var1.getlocal(2).__getattr__("decode").__call__(var2, var1.getlocal(1));
      var1.setline(898);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.setline(899);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_value", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_dateTime$85(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyObject var3 = var1.getglobal("DateTime").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(904);
      var1.getlocal(2).__getattr__("decode").__call__(var2, var1.getlocal(1));
      var1.setline(905);
      if (var1.getlocal(0).__getattr__("_use_datetime").__nonzero__()) {
         var1.setline(906);
         var3 = var1.getglobal("_datetime_type").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(907);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_value$86(PyFrame var1, ThreadState var2) {
      var1.setline(913);
      if (var1.getlocal(0).__getattr__("_value").__nonzero__()) {
         var1.setline(914);
         var1.getlocal(0).__getattr__("end_string").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_params$87(PyFrame var1, ThreadState var2) {
      var1.setline(918);
      PyString var3 = PyString.fromInterned("params");
      var1.getlocal(0).__setattr__((String)"_type", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_fault$88(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyString var3 = PyString.fromInterned("fault");
      var1.getlocal(0).__setattr__((String)"_type", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_methodName$89(PyFrame var1, ThreadState var2) {
      var1.setline(926);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_encoding").__nonzero__()) {
         var1.setline(927);
         var3 = var1.getglobal("_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_encoding"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(928);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_methodname", var3);
      var3 = null;
      var1.setline(929);
      PyString var4 = PyString.fromInterned("methodName");
      var1.getlocal(0).__setattr__((String)"_type", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _MultiCallMethod$90(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(938);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$91, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(941);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$92, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(943);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$93, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$91(PyFrame var1, ThreadState var2) {
      var1.setline(939);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_MultiCallMethod__call_list", var3);
      var3 = null;
      var1.setline(940);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_MultiCallMethod__name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$92(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3 = var1.getglobal("_MultiCallMethod").__call__(var2, var1.getlocal(0).__getattr__("_MultiCallMethod__call_list"), PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_MultiCallMethod__name"), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$93(PyFrame var1, ThreadState var2) {
      var1.setline(944);
      var1.getlocal(0).__getattr__("_MultiCallMethod__call_list").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_MultiCallMethod__name"), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MultiCallIterator$94(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Iterates over the results of a multicall. Exceptions are\n    raised in response to xmlrpc faults."));
      var1.setline(948);
      PyString.fromInterned("Iterates over the results of a multicall. Exceptions are\n    raised in response to xmlrpc faults.");
      var1.setline(950);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$95, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(953);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$96, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$95(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("results", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$96(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      PyObject var3 = var1.getlocal(0).__getattr__("results").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(955);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects))));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(956);
         throw Py.makeException(var1.getglobal("Fault").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("faultCode")), var1.getlocal(2).__getitem__(PyString.fromInterned("faultString"))));
      } else {
         var1.setline(957);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(958);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(960);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpected type in multicall result"));
         }
      }
   }

   public PyObject MultiCall$97(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("server -> a object used to boxcar method calls\n\n    server should be a ServerProxy object.\n\n    Methods can be added to the MultiCall using normal\n    method call syntax e.g.:\n\n    multicall = MultiCall(server_proxy)\n    multicall.add(2,3)\n    multicall.get_address(\"Guido\")\n\n    To execute the multicall, call the MultiCall object e.g.:\n\n    add_result, address = multicall()\n    "));
      var1.setline(978);
      PyString.fromInterned("server -> a object used to boxcar method calls\n\n    server should be a ServerProxy object.\n\n    Methods can be added to the MultiCall using normal\n    method call syntax e.g.:\n\n    multicall = MultiCall(server_proxy)\n    multicall.add(2,3)\n    multicall.get_address(\"Guido\")\n\n    To execute the multicall, call the MultiCall object e.g.:\n\n    add_result, address = multicall()\n    ");
      var1.setline(980);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$98, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(984);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$99, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(987);
      PyObject var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(989);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$100, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(992);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$101, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$98(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_MultiCall__server", var3);
      var3 = null;
      var1.setline(982);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_MultiCall__call_list", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$99(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      PyObject var3 = PyString.fromInterned("<MultiCall at %x>")._mod(var1.getglobal("id").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$100(PyFrame var1, ThreadState var2) {
      var1.setline(990);
      PyObject var3 = var1.getglobal("_MultiCallMethod").__call__(var2, var1.getlocal(0).__getattr__("_MultiCall__call_list"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$101(PyFrame var1, ThreadState var2) {
      var1.setline(993);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(994);
      PyObject var7 = var1.getlocal(0).__getattr__("_MultiCall__call_list").__iter__();

      while(true) {
         var1.setline(994);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(997);
            var7 = var1.getglobal("MultiCallIterator").__call__(var2, var1.getlocal(0).__getattr__("_MultiCall__server").__getattr__("system").__getattr__("multicall").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(995);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("methodName"), var1.getlocal(2), PyString.fromInterned("params"), var1.getlocal(3)})));
      }
   }

   public PyObject getparser$102(PyFrame var1, ThreadState var2) {
      var1.setline(1013);
      PyString.fromInterned("getparser() -> parser, unmarshaller\n\n    Create an instance of the fastest available parser, and attach it\n    to an unmarshalling object.  Return both objects.\n    ");
      var1.setline(1014);
      PyObject var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("datetime").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1015);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("the datetime module is not available"));
      } else {
         var1.setline(1016);
         var10000 = var1.getglobal("FastParser");
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("FastUnmarshaller");
         }

         PyObject var3;
         PyObject[] var5;
         if (var10000.__nonzero__()) {
            var1.setline(1017);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(1018);
               var3 = var1.getglobal("_datetime_type");
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(1020);
               var3 = var1.getglobal("_datetime");
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1021);
            var10000 = var1.getglobal("FastUnmarshaller");
            var5 = new PyObject[]{var1.getglobal("True"), var1.getglobal("False"), var1.getglobal("_binary"), var1.getlocal(1), var1.getglobal("Fault")};
            var3 = var10000.__call__(var2, var5);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1022);
            var3 = var1.getglobal("FastParser").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(1024);
            var10000 = var1.getglobal("Unmarshaller");
            var5 = new PyObject[]{var1.getlocal(0)};
            String[] var4 = new String[]{"use_datetime"};
            var10000 = var10000.__call__(var2, var5, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1025);
            if (var1.getglobal("FastParser").__nonzero__()) {
               var1.setline(1026);
               var3 = var1.getglobal("FastParser").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(1027);
               if (var1.getglobal("ExpatParser").__nonzero__()) {
                  var1.setline(1028);
                  var3 = var1.getglobal("ExpatParser").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(1030);
                  var3 = var1.getglobal("SlowParser").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var3);
                  var3 = null;
               }
            }
         }

         var1.setline(1031);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject dumps$103(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      PyString.fromInterned("data [,options] -> marshalled data\n\n    Convert an argument tuple or a Fault instance to an XML-RPC\n    request (or response, if the methodresponse option is used).\n\n    In addition to the data object, the following options can be given\n    as keyword arguments:\n\n        methodname: the method name for a methodCall packet\n\n        methodresponse: true to create a methodResponse packet.\n        If this option is used with a tuple, the tuple must be\n        a singleton (i.e. it can contain only one element).\n\n        encoding: the packet encoding (default is UTF-8)\n\n    All 8-bit strings in the data structure are assumed to use the\n    packet encoding.  Unicode strings are automatically converted,\n    where necessary.\n    ");
      var1.setline(1069);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("TupleType"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Fault"));
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("argument must be tuple or Fault instance"));
         }
      }

      var1.setline(1072);
      PyInteger var3;
      PyObject var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Fault")).__nonzero__()) {
         var1.setline(1073);
         var3 = Py.newInteger(1);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(1074);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("TupleType"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(1075);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var10000 = var4._eq(Py.newInteger(1));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("response tuple must be a singleton"));
               }
            }
         }
      }

      var1.setline(1077);
      PyString var5;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(1078);
         var5 = PyString.fromInterned("utf-8");
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(1080);
      if (var1.getglobal("FastMarshaller").__nonzero__()) {
         var1.setline(1081);
         var4 = var1.getglobal("FastMarshaller").__call__(var2, var1.getlocal(3));
         var1.setlocal(5, var4);
         var3 = null;
      } else {
         var1.setline(1083);
         var4 = var1.getglobal("Marshaller").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(5, var4);
         var3 = null;
      }

      var1.setline(1085);
      var4 = var1.getlocal(5).__getattr__("dumps").__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(1087);
      var4 = var1.getlocal(3);
      var10000 = var4._ne(PyString.fromInterned("utf-8"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1088);
         var4 = PyString.fromInterned("<?xml version='1.0' encoding='%s'?>\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(3)));
         var1.setlocal(7, var4);
         var3 = null;
      } else {
         var1.setline(1090);
         var5 = PyString.fromInterned("<?xml version='1.0'?>\n");
         var1.setlocal(7, var5);
         var3 = null;
      }

      var1.setline(1093);
      PyTuple var6;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1095);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType")).__not__().__nonzero__()) {
            var1.setline(1096);
            var4 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(3));
            var1.setlocal(1, var4);
            var3 = null;
         }

         var1.setline(1097);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned("<methodCall>\n<methodName>"), var1.getlocal(1), PyString.fromInterned("</methodName>\n"), var1.getlocal(6), PyString.fromInterned("</methodCall>\n")});
         var1.setlocal(6, var6);
         var3 = null;
      } else {
         var1.setline(1104);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(1113);
            var4 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(1106);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned("<methodResponse>\n"), var1.getlocal(6), PyString.fromInterned("</methodResponse>\n")});
         var1.setlocal(6, var6);
         var3 = null;
      }

      var1.setline(1114);
      var4 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject loads$104(PyFrame var1, ThreadState var2) {
      var1.setline(1133);
      PyString.fromInterned("data -> unmarshalled data, method name\n\n    Convert an XML-RPC packet to unmarshalled data plus a method\n    name (None if not present).\n\n    If the XML-RPC packet represents a fault condition, this function\n    raises a Fault exception.\n    ");
      var1.setline(1134);
      PyObject var10000 = var1.getglobal("getparser");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"use_datetime"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1135);
      var1.getlocal(2).__getattr__("feed").__call__(var2, var1.getlocal(0));
      var1.setline(1136);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1137);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("close").__call__(var2), var1.getlocal(3).__getattr__("getmethodname").__call__(var2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject gzip_encode$105(PyFrame var1, ThreadState var2) {
      var1.setline(1151);
      PyString.fromInterned("data -> gzip encoded data\n\n    Encode data using the gzip content encoding as described in RFC 1952\n    ");
      var1.setline(1152);
      if (var1.getglobal("gzip").__not__().__nonzero__()) {
         var1.setline(1153);
         throw Py.makeException(var1.getglobal("NotImplementedError"));
      } else {
         var1.setline(1154);
         PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1155);
         PyObject var10000 = var1.getglobal("gzip").__getattr__("GzipFile");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("wb"), var1.getlocal(1), Py.newInteger(1)};
         String[] var4 = new String[]{"mode", "fileobj", "compresslevel"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1156);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(0));
         var1.setline(1157);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         var1.setline(1158);
         var3 = var1.getlocal(1).__getattr__("getvalue").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1159);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(1160);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject gzip_decode$106(PyFrame var1, ThreadState var2) {
      var1.setline(1175);
      PyString.fromInterned("gzip encoded data -> unencoded data\n\n    Decode data using the gzip content encoding as described in RFC 1952\n    ");
      var1.setline(1176);
      if (var1.getglobal("gzip").__not__().__nonzero__()) {
         var1.setline(1177);
         throw Py.makeException(var1.getglobal("NotImplementedError"));
      } else {
         var1.setline(1178);
         PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1179);
         PyObject var10000 = var1.getglobal("gzip").__getattr__("GzipFile");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("rb"), var1.getlocal(1)};
         String[] var4 = new String[]{"mode", "fileobj"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;

         try {
            var1.setline(1181);
            var3 = var1.getlocal(2).__getattr__("read").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("IOError"))) {
               var1.setline(1183);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid data")));
            }

            throw var7;
         }

         var1.setline(1184);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(1185);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         var1.setline(1186);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject GzipDecodedResponse$107(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("a file-like object to decode a response encoded with the gzip\n    method, as described in RFC 1952.\n    "));
      var1.setline(1198);
      PyString.fromInterned("a file-like object to decode a response encoded with the gzip\n    method, as described in RFC 1952.\n    ");
      var1.setline(1199);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$108, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$109, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$108(PyFrame var1, ThreadState var2) {
      var1.setline(1202);
      if (var1.getglobal("gzip").__not__().__nonzero__()) {
         var1.setline(1203);
         throw Py.makeException(var1.getglobal("NotImplementedError"));
      } else {
         var1.setline(1204);
         PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(1).__getattr__("read").__call__(var2));
         var1.getlocal(0).__setattr__("stringio", var3);
         var3 = null;
         var1.setline(1205);
         PyObject var10000 = var1.getglobal("gzip").__getattr__("GzipFile").__getattr__("__init__");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("rb"), var1.getlocal(0).__getattr__("stringio")};
         String[] var4 = new String[]{"mode", "fileobj"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$109(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      var1.getglobal("gzip").__getattr__("GzipFile").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.setline(1209);
      var1.getlocal(0).__getattr__("stringio").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Method$110(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1218);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$111, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$112, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(1223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$113, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$111(PyFrame var1, ThreadState var2) {
      var1.setline(1219);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Method__send", var3);
      var3 = null;
      var1.setline(1220);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_Method__name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$112(PyFrame var1, ThreadState var2) {
      var1.setline(1222);
      PyObject var3 = var1.getglobal("_Method").__call__(var2, var1.getlocal(0).__getattr__("_Method__send"), PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_Method__name"), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$113(PyFrame var1, ThreadState var2) {
      var1.setline(1224);
      PyObject var3 = var1.getlocal(0).__getattr__("_Method__send").__call__(var2, var1.getlocal(0).__getattr__("_Method__name"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Transport$114(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handles an HTTP transaction to an XML-RPC server."));
      var1.setline(1233);
      PyString.fromInterned("Handles an HTTP transaction to an XML-RPC server.");
      var1.setline(1236);
      PyObject var3 = PyString.fromInterned("xmlrpclib.py/%s (by www.pythonware.com)")._mod(var1.getname("__version__"));
      var1.setlocal("user_agent", var3);
      var3 = null;
      var1.setline(1239);
      var3 = var1.getname("True");
      var1.setlocal("accept_gzip_encoding", var3);
      var3 = null;
      var1.setline(1244);
      var3 = var1.getname("None");
      var1.setlocal("encode_threshold", var3);
      var3 = null;
      var1.setline(1246);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$115, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1260);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, request$116, (PyObject)null);
      var1.setlocal("request", var5);
      var3 = null;
      var1.setline(1281);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, single_request$117, (PyObject)null);
      var1.setlocal("single_request", var5);
      var3 = null;
      var1.setline(1320);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getparser$118, (PyObject)null);
      var1.setlocal("getparser", var5);
      var3 = null;
      var1.setline(1334);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_host_info$119, (PyObject)null);
      var1.setlocal("get_host_info", var5);
      var3 = null;
      var1.setline(1361);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, make_connection$120, (PyObject)null);
      var1.setlocal("make_connection", var5);
      var3 = null;
      var1.setline(1377);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$121, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(1389);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send_request$122, (PyObject)null);
      var1.setlocal("send_request", var5);
      var3 = null;
      var1.setline(1406);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send_host$123, (PyObject)null);
      var1.setlocal("send_host", var5);
      var3 = null;
      var1.setline(1419);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send_user_agent$124, (PyObject)null);
      var1.setlocal("send_user_agent", var5);
      var3 = null;
      var1.setline(1428);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send_content$125, (PyObject)null);
      var1.setlocal("send_content", var5);
      var3 = null;
      var1.setline(1447);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_response$126, (PyObject)null);
      var1.setlocal("parse_response", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$115(PyFrame var1, ThreadState var2) {
      var1.setline(1247);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_use_datetime", var3);
      var3 = null;
      var1.setline(1248);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var1.getlocal(0).__setattr__((String)"_connection", var4);
      var3 = null;
      var1.setline(1249);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_extra_headers", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject request$116(PyFrame var1, ThreadState var2) {
      var1.setline(1262);
      PyObject var3 = (new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1)})).__iter__();

      while(true) {
         var1.setline(1262);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);

         try {
            var1.setline(1264);
            PyObject var5 = var1.getlocal(0).__getattr__("single_request").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
            var1.f_lasti = -1;
            return var5;
         } catch (Throwable var8) {
            PyException var6 = Py.setException(var8, var1);
            if (var6.match(var1.getglobal("socket").__getattr__("error"))) {
               PyObject var7 = var6.value;
               var1.setlocal(6, var7);
               var7 = null;
               var1.setline(1266);
               PyObject var10000 = var1.getlocal(5);
               if (!var10000.__nonzero__()) {
                  var7 = var1.getlocal(6).__getattr__("errno");
                  var10000 = var7._notin(new PyTuple(new PyObject[]{var1.getglobal("errno").__getattr__("ECONNRESET"), var1.getglobal("errno").__getattr__("ECONNABORTED"), var1.getglobal("errno").__getattr__("EPIPE")}));
                  var7 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1267);
                  throw Py.makeException();
               }
            } else {
               if (!var6.match(var1.getglobal("httplib").__getattr__("BadStatusLine"))) {
                  throw var6;
               }

               var1.setline(1269);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(1270);
                  throw Py.makeException();
               }
            }
         }
      }
   }

   public PyObject single_request$117(PyFrame var1, ThreadState var2) {
      var1.setline(1284);
      PyObject var3 = var1.getlocal(0).__getattr__("make_connection").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1285);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1286);
         var1.getlocal(5).__getattr__("set_debuglevel").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      try {
         var1.setline(1289);
         var1.getlocal(0).__getattr__("send_request").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(3));
         var1.setline(1290);
         var1.getlocal(0).__getattr__("send_host").__call__(var2, var1.getlocal(5), var1.getlocal(1));
         var1.setline(1291);
         var1.getlocal(0).__getattr__("send_user_agent").__call__(var2, var1.getlocal(5));
         var1.setline(1292);
         var1.getlocal(0).__getattr__("send_content").__call__(var2, var1.getlocal(5), var1.getlocal(3));
         var1.setline(1294);
         PyObject var10000 = var1.getlocal(5).__getattr__("getresponse");
         PyObject[] var6 = new PyObject[]{var1.getglobal("True")};
         String[] var7 = new String[]{"buffering"};
         var10000 = var10000.__call__(var2, var6, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1295);
         var3 = var1.getlocal(6).__getattr__("status");
         var10000 = var3._eq(Py.newInteger(200));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1296);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("verbose", var3);
            var3 = null;
            var1.setline(1297);
            var3 = var1.getlocal(0).__getattr__("parse_response").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("Fault"))) {
            var1.setline(1299);
            throw Py.makeException();
         }

         if (var4.match(var1.getglobal("Exception"))) {
            var1.setline(1303);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(1304);
            throw Py.makeException();
         }

         throw var4;
      }

      var1.setline(1307);
      if (var1.getlocal(6).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-length"), (PyObject)Py.newInteger(0)).__nonzero__()) {
         var1.setline(1308);
         var1.getlocal(6).__getattr__("read").__call__(var2);
      }

      var1.setline(1309);
      throw Py.makeException(var1.getglobal("ProtocolError").__call__(var2, var1.getlocal(1)._add(var1.getlocal(2)), var1.getlocal(6).__getattr__("status"), var1.getlocal(6).__getattr__("reason"), var1.getlocal(6).__getattr__("msg")));
   }

   public PyObject getparser$118(PyFrame var1, ThreadState var2) {
      var1.setline(1322);
      PyObject var10000 = var1.getglobal("getparser");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("_use_datetime")};
      String[] var4 = new String[]{"use_datetime"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject get_host_info$119(PyFrame var1, ThreadState var2) {
      var1.setline(1336);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1337);
      PyObject[] var4;
      PyObject var5;
      PyObject var6;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("TupleType")).__nonzero__()) {
         var1.setline(1338);
         var6 = var1.getlocal(1);
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(1340);
      var6 = imp.importOne("urllib", var1, -1);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1341);
      var6 = var1.getlocal(3).__getattr__("splituser").__call__(var2, var1.getlocal(1));
      var4 = Py.unpackSequence(var6, 2);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1343);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1344);
         var6 = imp.importOne("base64", var1, -1);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(1345);
         var6 = var1.getlocal(5).__getattr__("encodestring").__call__(var2, var1.getlocal(3).__getattr__("unquote").__call__(var2, var1.getlocal(4)));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(1346);
         var6 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("string").__getattr__("split").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned(""));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(1347);
         PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Authorization"), PyString.fromInterned("Basic ")._add(var1.getlocal(4))})});
         var1.setlocal(6, var7);
         var3 = null;
      } else {
         var1.setline(1351);
         var6 = var1.getglobal("None");
         var1.setlocal(6, var6);
         var3 = null;
      }

      var1.setline(1353);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject make_connection$120(PyFrame var1, ThreadState var2) {
      var1.setline(1364);
      PyObject var10000 = var1.getlocal(0).__getattr__("_connection");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(0)));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1365);
         var3 = var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1368);
         PyObject var4 = var1.getlocal(0).__getattr__("get_host_info").__call__(var2, var1.getlocal(1));
         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.getlocal(0).__setattr__("_extra_headers", var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(1370);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("httplib").__getattr__("HTTPConnection").__call__(var2, var1.getlocal(2))});
         var1.getlocal(0).__setattr__((String)"_connection", var7);
         var4 = null;
         var1.setline(1371);
         var3 = var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject close$121(PyFrame var1, ThreadState var2) {
      var1.setline(1378);
      if (var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1)).__nonzero__()) {
         var1.setline(1379);
         var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1)).__getattr__("close").__call__(var2);
         var1.setline(1380);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         var1.getlocal(0).__setattr__((String)"_connection", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_request$122(PyFrame var1, ThreadState var2) {
      var1.setline(1390);
      PyObject var10000 = var1.getlocal(0).__getattr__("accept_gzip_encoding");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("gzip");
      }

      if (var10000.__nonzero__()) {
         var1.setline(1391);
         var10000 = var1.getlocal(1).__getattr__("putrequest");
         PyObject[] var3 = new PyObject[]{PyString.fromInterned("POST"), var1.getlocal(2), var1.getglobal("True")};
         String[] var4 = new String[]{"skip_accept_encoding"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(1392);
         var1.getlocal(1).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Accept-Encoding"), (PyObject)PyString.fromInterned("gzip"));
      } else {
         var1.setline(1394);
         var1.getlocal(1).__getattr__("putrequest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POST"), (PyObject)var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_host$123(PyFrame var1, ThreadState var2) {
      var1.setline(1407);
      PyObject var3 = var1.getlocal(0).__getattr__("_extra_headers");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1408);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1409);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("DictType")).__nonzero__()) {
            var1.setline(1410);
            var3 = var1.getlocal(3).__getattr__("items").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(1411);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(1411);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(1412);
            var1.getlocal(1).__getattr__("putheader").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_user_agent$124(PyFrame var1, ThreadState var2) {
      var1.setline(1420);
      var1.getlocal(1).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("User-Agent"), (PyObject)var1.getlocal(0).__getattr__("user_agent"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_content$125(PyFrame var1, ThreadState var2) {
      var1.setline(1429);
      var1.getlocal(1).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type"), (PyObject)PyString.fromInterned("text/xml"));
      var1.setline(1432);
      PyObject var3 = var1.getlocal(0).__getattr__("encode_threshold");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("encode_threshold");
         var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("gzip");
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1435);
         var1.getlocal(1).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Encoding"), (PyObject)PyString.fromInterned("gzip"));
         var1.setline(1436);
         var3 = var1.getglobal("gzip_encode").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1438);
      var1.getlocal(1).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))));
      var1.setline(1439);
      var1.getlocal(1).__getattr__("endheaders").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_response$126(PyFrame var1, ThreadState var2) {
      var1.setline(1451);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("getheader")).__nonzero__()) {
         var1.setline(1452);
         var3 = var1.getlocal(1).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Encoding"), (PyObject)PyString.fromInterned(""));
         var10000 = var3._eq(PyString.fromInterned("gzip"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1453);
            var3 = var1.getglobal("GzipDecodedResponse").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(1455);
            var3 = var1.getlocal(1);
            var1.setlocal(2, var3);
            var3 = null;
         }
      } else {
         var1.setline(1457);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1459);
      var3 = var1.getlocal(0).__getattr__("getparser").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(1461);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(1462);
         var3 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1463);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(1465);
         if (var1.getlocal(0).__getattr__("verbose").__nonzero__()) {
            var1.setline(1466);
            Py.printComma(PyString.fromInterned("body:"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(5)));
         }

         var1.setline(1467);
         var1.getlocal(3).__getattr__("feed").__call__(var2, var1.getlocal(5));
      }

      var1.setline(1469);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1470);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      var1.setline(1471);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(1473);
      var3 = var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SafeTransport$127(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handles an HTTPS transaction to an XML-RPC server."));
      var1.setline(1479);
      PyString.fromInterned("Handles an HTTPS transaction to an XML-RPC server.");
      var1.setline(1483);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, make_connection$128, (PyObject)null);
      var1.setlocal("make_connection", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject make_connection$128(PyFrame var1, ThreadState var2) {
      var1.setline(1484);
      PyObject var10000 = var1.getlocal(0).__getattr__("_connection");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(0)));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1485);
         var3 = var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         try {
            var1.setline(1489);
            PyObject var9 = var1.getglobal("httplib").__getattr__("HTTPSConnection");
            var1.setlocal(2, var9);
            var4 = null;
         } catch (Throwable var8) {
            var4 = Py.setException(var8, var1);
            if (var4.match(var1.getglobal("AttributeError"))) {
               var1.setline(1491);
               throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("your version of httplib doesn't support HTTPS")));
            }

            throw var4;
         }

         var1.setline(1495);
         PyObject var5 = var1.getlocal(0).__getattr__("get_host_info").__call__(var2, var1.getlocal(1));
         PyObject[] var6 = Py.unpackSequence(var5, 3);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.getlocal(0).__setattr__("_extra_headers", var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
         var1.setline(1496);
         PyTuple var13 = new PyTuple;
         PyObject[] var10002 = new PyObject[]{var1.getlocal(1), null};
         PyObject var10005 = var1.getlocal(2);
         PyObject[] var10 = new PyObject[]{var1.getlocal(3), var1.getglobal("None")};
         String[] var11 = new String[0];
         Object var10007 = var1.getlocal(4);
         if (!((PyObject)var10007).__nonzero__()) {
            var10007 = new PyDictionary(Py.EmptyObjects);
         }

         var10005 = var10005._callextra(var10, var11, (PyObject)null, (PyObject)var10007);
         var5 = null;
         var10002[1] = var10005;
         var13.<init>(var10002);
         PyTuple var12 = var13;
         var1.getlocal(0).__setattr__((String)"_connection", var12);
         var5 = null;
         var1.setline(1497);
         var3 = var1.getlocal(0).__getattr__("_connection").__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ServerProxy$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("uri [,options] -> a logical connection to an XML-RPC server\n\n    uri is the connection point on the server, given as\n    scheme://host/target.\n\n    The standard implementation always supports the \"http\" scheme.  If\n    SSL socket support is available (Python 2.0), it also supports\n    \"https\".\n\n    If the target part and the slash preceding it are both omitted,\n    \"/RPC2\" is assumed.\n\n    The following options can be given as keyword arguments:\n\n        transport: a transport factory\n        encoding: the request encoding (default is UTF-8)\n\n    All 8-bit strings passed to the server proxy are assumed to use\n    the given encoding.\n    "));
      var1.setline(1536);
      PyString.fromInterned("uri [,options] -> a logical connection to an XML-RPC server\n\n    uri is the connection point on the server, given as\n    scheme://host/target.\n\n    The standard implementation always supports the \"http\" scheme.  If\n    SSL socket support is available (Python 2.0), it also supports\n    \"https\".\n\n    If the target part and the slash preceding it are both omitted,\n    \"/RPC2\" is assumed.\n\n    The following options can be given as keyword arguments:\n\n        transport: a transport factory\n        encoding: the request encoding (default is UTF-8)\n\n    All 8-bit strings passed to the server proxy are assumed to use\n    the given encoding.\n    ");
      var1.setline(1538);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$130, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1565);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ServerProxy__close$131, (PyObject)null);
      var1.setlocal("_ServerProxy__close", var4);
      var3 = null;
      var1.setline(1568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ServerProxy__request$132, (PyObject)null);
      var1.setlocal("_ServerProxy__request", var4);
      var3 = null;
      var1.setline(1586);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$133, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(1592);
      PyObject var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(1594);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$134, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(1601);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$135, PyString.fromInterned("A workaround to get special attributes on the ServerProxy\n           without interfering with the magic __getattr__\n        "));
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$130(PyFrame var1, ThreadState var2) {
      var1.setline(1542);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(1543);
         var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ISO-8859-1"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1546);
      var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1547);
      var3 = var1.getlocal(7).__getattr__("splittype").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1548);
      var3 = var1.getlocal(8);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("http"), PyString.fromInterned("https")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1549);
         throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("unsupported XML-RPC protocol"));
      } else {
         var1.setline(1550);
         var3 = var1.getlocal(7).__getattr__("splithost").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.getlocal(0).__setattr__("_ServerProxy__host", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_ServerProxy__handler", var5);
         var5 = null;
         var3 = null;
         var1.setline(1551);
         if (var1.getlocal(0).__getattr__("_ServerProxy__handler").__not__().__nonzero__()) {
            var1.setline(1552);
            PyString var7 = PyString.fromInterned("/RPC2");
            var1.getlocal(0).__setattr__((String)"_ServerProxy__handler", var7);
            var3 = null;
         }

         var1.setline(1554);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1555);
            var3 = var1.getlocal(8);
            var10000 = var3._eq(PyString.fromInterned("https"));
            var3 = null;
            String[] var6;
            PyObject[] var8;
            if (var10000.__nonzero__()) {
               var1.setline(1556);
               var10000 = var1.getglobal("SafeTransport");
               var8 = new PyObject[]{var1.getlocal(6)};
               var6 = new String[]{"use_datetime"};
               var10000 = var10000.__call__(var2, var8, var6);
               var3 = null;
               var3 = var10000;
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1558);
               var10000 = var1.getglobal("Transport");
               var8 = new PyObject[]{var1.getlocal(6)};
               var6 = new String[]{"use_datetime"};
               var10000 = var10000.__call__(var2, var8, var6);
               var3 = null;
               var3 = var10000;
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(1559);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_ServerProxy__transport", var3);
         var3 = null;
         var1.setline(1561);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("_ServerProxy__encoding", var3);
         var3 = null;
         var1.setline(1562);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("_ServerProxy__verbose", var3);
         var3 = null;
         var1.setline(1563);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("_ServerProxy__allow_none", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _ServerProxy__close$131(PyFrame var1, ThreadState var2) {
      var1.setline(1566);
      var1.getlocal(0).__getattr__("_ServerProxy__transport").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ServerProxy__request$132(PyFrame var1, ThreadState var2) {
      var1.setline(1571);
      PyObject var10000 = var1.getglobal("dumps");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(0).__getattr__("_ServerProxy__encoding"), var1.getlocal(0).__getattr__("_ServerProxy__allow_none")};
      String[] var4 = new String[]{"encoding", "allow_none"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1574);
      var10000 = var1.getlocal(0).__getattr__("_ServerProxy__transport").__getattr__("request");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("_ServerProxy__host"), var1.getlocal(0).__getattr__("_ServerProxy__handler"), var1.getlocal(3), var1.getlocal(0).__getattr__("_ServerProxy__verbose")};
      var4 = new String[]{"verbose"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(1581);
      var5 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
      var10000 = var5._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1582);
         var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(1584);
      var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __repr__$133(PyFrame var1, ThreadState var2) {
      var1.setline(1587);
      PyObject var3 = PyString.fromInterned("<ServerProxy for %s%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_ServerProxy__host"), var1.getlocal(0).__getattr__("_ServerProxy__handler")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$134(PyFrame var1, ThreadState var2) {
      var1.setline(1596);
      PyObject var3 = var1.getglobal("_Method").__call__(var2, var1.getlocal(0).__getattr__("_ServerProxy__request"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$135(PyFrame var1, ThreadState var2) {
      var1.setline(1604);
      PyString.fromInterned("A workaround to get special attributes on the ServerProxy\n           without interfering with the magic __getattr__\n        ");
      var1.setline(1605);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("close"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1606);
         var3 = var1.getlocal(0).__getattr__("_ServerProxy__close");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1607);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("transport"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1608);
            var3 = var1.getlocal(0).__getattr__("_ServerProxy__transport");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1609);
            throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("Attribute %r not found")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
         }
      }
   }

   public xmlrpclib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"data", "encoding", "is8bit"};
      _decode$1 = Py.newCode(3, var2, var1, "_decode", 168, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "replace"};
      escape$2 = Py.newCode(2, var2, var1, "escape", 174, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string"};
      _stringify$3 = Py.newCode(1, var2, var1, "_stringify", 180, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string"};
      _stringify$4 = Py.newCode(1, var2, var1, "_stringify", 187, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Error$5 = Py.newCode(0, var2, var1, "Error", 222, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __str__$6 = Py.newCode(1, var2, var1, "__str__", 224, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProtocolError$7 = Py.newCode(0, var2, var1, "ProtocolError", 237, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url", "errcode", "errmsg", "headers"};
      __init__$8 = Py.newCode(5, var2, var1, "__init__", 239, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$9 = Py.newCode(1, var2, var1, "__repr__", 245, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ResponseError$10 = Py.newCode(0, var2, var1, "ResponseError", 256, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Fault$11 = Py.newCode(0, var2, var1, "Fault", 269, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "faultCode", "faultString", "extra"};
      __init__$12 = Py.newCode(4, var2, var1, "__init__", 271, false, true, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$13 = Py.newCode(1, var2, var1, "__repr__", 275, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Boolean$14 = Py.newCode(0, var2, var1, "Boolean", 300, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$15 = Py.newCode(2, var2, var1, "__init__", 306, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out"};
      encode$16 = Py.newCode(2, var2, var1, "encode", 309, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$17 = Py.newCode(2, var2, var1, "__cmp__", 312, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$18 = Py.newCode(1, var2, var1, "__repr__", 317, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __int__$19 = Py.newCode(1, var2, var1, "__int__", 323, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __nonzero__$20 = Py.newCode(1, var2, var1, "__nonzero__", 326, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value", "_truefalse"};
      boolean$21 = Py.newCode(2, var2, var1, "boolean", 343, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      _strftime$22 = Py.newCode(1, var2, var1, "_strftime", 362, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DateTime$23 = Py.newCode(0, var2, var1, "DateTime", 376, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$24 = Py.newCode(2, var2, var1, "__init__", 382, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o", "otype"};
      make_comparable$25 = Py.newCode(2, var2, var1, "make_comparable", 388, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __lt__$26 = Py.newCode(2, var2, var1, "__lt__", 409, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __le__$27 = Py.newCode(2, var2, var1, "__le__", 413, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __gt__$28 = Py.newCode(2, var2, var1, "__gt__", 417, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __ge__$29 = Py.newCode(2, var2, var1, "__ge__", 421, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __eq__$30 = Py.newCode(2, var2, var1, "__eq__", 425, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __ne__$31 = Py.newCode(2, var2, var1, "__ne__", 429, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      timetuple$32 = Py.newCode(1, var2, var1, "timetuple", 433, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      __cmp__$33 = Py.newCode(2, var2, var1, "__cmp__", 436, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$34 = Py.newCode(1, var2, var1, "__str__", 445, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$35 = Py.newCode(1, var2, var1, "__repr__", 448, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      decode$36 = Py.newCode(2, var2, var1, "decode", 451, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out"};
      encode$37 = Py.newCode(2, var2, var1, "encode", 455, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "value"};
      _datetime$38 = Py.newCode(1, var2, var1, "_datetime", 460, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "t"};
      _datetime_type$39 = Py.newCode(1, var2, var1, "_datetime_type", 466, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Binary$40 = Py.newCode(0, var2, var1, "Binary", 482, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data"};
      __init__$41 = Py.newCode(2, var2, var1, "__init__", 485, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$42 = Py.newCode(1, var2, var1, "__str__", 493, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$43 = Py.newCode(2, var2, var1, "__cmp__", 496, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      decode$44 = Py.newCode(2, var2, var1, "decode", 501, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out"};
      encode$45 = Py.newCode(2, var2, var1, "encode", 504, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "value"};
      _binary$46 = Py.newCode(1, var2, var1, "_binary", 509, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExpatParser$47 = Py.newCode(0, var2, var1, "ExpatParser", 543, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "target", "parser", "encoding"};
      __init__$48 = Py.newCode(2, var2, var1, "__init__", 545, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$49 = Py.newCode(2, var2, var1, "feed", 556, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$50 = Py.newCode(1, var2, var1, "close", 559, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SlowParser$51 = Py.newCode(0, var2, var1, "SlowParser", 563, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "target", "xmllib"};
      __init__$52 = Py.newCode(2, var2, var1, "__init__", 566, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Marshaller$53 = Py.newCode(0, var2, var1, "Marshaller", 590, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "encoding", "allow_none"};
      __init__$54 = Py.newCode(3, var2, var1, "__init__", 603, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "values", "out", "write", "dump", "v", "result"};
      dumps$55 = Py.newCode(2, var2, var1, "dumps", 611, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write", "f", "type_"};
      _Marshaller__dump$56 = Py.newCode(3, var2, var1, "_Marshaller__dump", 638, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_nil$57 = Py.newCode(3, var2, var1, "dump_nil", 656, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_int$58 = Py.newCode(3, var2, var1, "dump_int", 662, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_bool$59 = Py.newCode(3, var2, var1, "dump_bool", 672, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_long$60 = Py.newCode(3, var2, var1, "dump_long", 678, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_double$61 = Py.newCode(3, var2, var1, "dump_double", 686, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write", "escape"};
      dump_string$62 = Py.newCode(4, var2, var1, "dump_string", 692, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write", "escape"};
      dump_unicode$63 = Py.newCode(4, var2, var1, "dump_unicode", 699, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write", "i", "dump", "v"};
      dump_array$64 = Py.newCode(3, var2, var1, "dump_array", 706, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write", "escape", "i", "dump", "k", "v"};
      dump_struct$65 = Py.newCode(4, var2, var1, "dump_struct", 720, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_datetime$66 = Py.newCode(3, var2, var1, "dump_datetime", 742, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "write"};
      dump_instance$67 = Py.newCode(3, var2, var1, "dump_instance", 748, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Unmarshaller$68 = Py.newCode(0, var2, var1, "Unmarshaller", 764, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "use_datetime"};
      __init__$69 = Py.newCode(2, var2, var1, "__init__", 776, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$70 = Py.newCode(1, var2, var1, "close", 788, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getmethodname$71 = Py.newCode(1, var2, var1, "getmethodname", 796, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "standalone"};
      xml$72 = Py.newCode(3, var2, var1, "xml", 802, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      start$73 = Py.newCode(3, var2, var1, "start", 806, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      data$74 = Py.newCode(2, var2, var1, "data", 813, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "join", "f"};
      end$75 = Py.newCode(3, var2, var1, "end", 816, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "data", "f"};
      end_dispatch$76 = Py.newCode(3, var2, var1, "end_dispatch", 828, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_nil$77 = Py.newCode(2, var2, var1, "end_nil", 842, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_boolean$78 = Py.newCode(2, var2, var1, "end_boolean", 847, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_int$79 = Py.newCode(2, var2, var1, "end_int", 857, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_double$80 = Py.newCode(2, var2, var1, "end_double", 864, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_string$81 = Py.newCode(2, var2, var1, "end_string", 869, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "mark"};
      end_array$82 = Py.newCode(2, var2, var1, "end_array", 877, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "mark", "dict", "items", "i"};
      end_struct$83 = Py.newCode(2, var2, var1, "end_struct", 884, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "value"};
      end_base64$84 = Py.newCode(2, var2, var1, "end_base64", 895, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "value"};
      end_dateTime$85 = Py.newCode(2, var2, var1, "end_dateTime", 902, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_value$86 = Py.newCode(2, var2, var1, "end_value", 910, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_params$87 = Py.newCode(2, var2, var1, "end_params", 917, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_fault$88 = Py.newCode(2, var2, var1, "end_fault", 921, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      end_methodName$89 = Py.newCode(2, var2, var1, "end_methodName", 925, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _MultiCallMethod$90 = Py.newCode(0, var2, var1, "_MultiCallMethod", 935, false, false, self, 90, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "call_list", "name"};
      __init__$91 = Py.newCode(3, var2, var1, "__init__", 938, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$92 = Py.newCode(2, var2, var1, "__getattr__", 941, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __call__$93 = Py.newCode(2, var2, var1, "__call__", 943, true, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MultiCallIterator$94 = Py.newCode(0, var2, var1, "MultiCallIterator", 946, false, false, self, 94, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "results"};
      __init__$95 = Py.newCode(2, var2, var1, "__init__", 950, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "item"};
      __getitem__$96 = Py.newCode(2, var2, var1, "__getitem__", 953, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MultiCall$97 = Py.newCode(0, var2, var1, "MultiCall", 963, false, false, self, 97, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "server"};
      __init__$98 = Py.newCode(2, var2, var1, "__init__", 980, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$99 = Py.newCode(1, var2, var1, "__repr__", 984, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$100 = Py.newCode(2, var2, var1, "__getattr__", 989, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "marshalled_list", "name", "args"};
      __call__$101 = Py.newCode(1, var2, var1, "__call__", 992, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"use_datetime", "mkdatetime", "target", "parser"};
      getparser$102 = Py.newCode(1, var2, var1, "getparser", 1008, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"params", "methodname", "methodresponse", "encoding", "allow_none", "m", "data", "xmlheader"};
      dumps$103 = Py.newCode(5, var2, var1, "dumps", 1046, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "use_datetime", "p", "u"};
      loads$104 = Py.newCode(2, var2, var1, "loads", 1125, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "f", "gzf", "encoded"};
      gzip_encode$105 = Py.newCode(1, var2, var1, "gzip_encode", 1147, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "f", "gzf", "decoded"};
      gzip_decode$106 = Py.newCode(1, var2, var1, "gzip_decode", 1171, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GzipDecodedResponse$107 = Py.newCode(0, var2, var1, "GzipDecodedResponse", 1195, false, false, self, 107, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "response"};
      __init__$108 = Py.newCode(2, var2, var1, "__init__", 1199, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$109 = Py.newCode(1, var2, var1, "close", 1207, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Method$110 = Py.newCode(0, var2, var1, "_Method", 1215, false, false, self, 110, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "send", "name"};
      __init__$111 = Py.newCode(3, var2, var1, "__init__", 1218, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$112 = Py.newCode(2, var2, var1, "__getattr__", 1221, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __call__$113 = Py.newCode(2, var2, var1, "__call__", 1223, true, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Transport$114 = Py.newCode(0, var2, var1, "Transport", 1232, false, false, self, 114, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "use_datetime"};
      __init__$115 = Py.newCode(2, var2, var1, "__init__", 1246, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "handler", "request_body", "verbose", "i", "e"};
      request$116 = Py.newCode(5, var2, var1, "request", 1260, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "handler", "request_body", "verbose", "h", "response"};
      single_request$117 = Py.newCode(5, var2, var1, "single_request", 1281, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getparser$118 = Py.newCode(1, var2, var1, "getparser", 1320, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "x509", "urllib", "auth", "base64", "extra_headers"};
      get_host_info$119 = Py.newCode(2, var2, var1, "get_host_info", 1334, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "chost", "x509"};
      make_connection$120 = Py.newCode(2, var2, var1, "make_connection", 1361, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$121 = Py.newCode(1, var2, var1, "close", 1377, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "connection", "handler", "request_body"};
      send_request$122 = Py.newCode(4, var2, var1, "send_request", 1389, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "connection", "host", "extra_headers", "key", "value"};
      send_host$123 = Py.newCode(3, var2, var1, "send_host", 1406, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "connection"};
      send_user_agent$124 = Py.newCode(2, var2, var1, "send_user_agent", 1419, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "connection", "request_body"};
      send_content$125 = Py.newCode(3, var2, var1, "send_content", 1428, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "response", "stream", "p", "u", "data"};
      parse_response$126 = Py.newCode(2, var2, var1, "parse_response", 1447, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SafeTransport$127 = Py.newCode(0, var2, var1, "SafeTransport", 1478, false, false, self, 127, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "HTTPS", "chost", "x509"};
      make_connection$128 = Py.newCode(2, var2, var1, "make_connection", 1483, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ServerProxy$129 = Py.newCode(0, var2, var1, "ServerProxy", 1516, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "uri", "transport", "encoding", "verbose", "allow_none", "use_datetime", "urllib", "type"};
      __init__$130 = Py.newCode(7, var2, var1, "__init__", 1538, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _ServerProxy__close$131 = Py.newCode(1, var2, var1, "_ServerProxy__close", 1565, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "methodname", "params", "request", "response"};
      _ServerProxy__request$132 = Py.newCode(3, var2, var1, "_ServerProxy__request", 1568, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$133 = Py.newCode(1, var2, var1, "__repr__", 1586, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$134 = Py.newCode(2, var2, var1, "__getattr__", 1594, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __call__$135 = Py.newCode(2, var2, var1, "__call__", 1601, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xmlrpclib$py("xmlrpclib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xmlrpclib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._decode$1(var2, var3);
         case 2:
            return this.escape$2(var2, var3);
         case 3:
            return this._stringify$3(var2, var3);
         case 4:
            return this._stringify$4(var2, var3);
         case 5:
            return this.Error$5(var2, var3);
         case 6:
            return this.__str__$6(var2, var3);
         case 7:
            return this.ProtocolError$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.__repr__$9(var2, var3);
         case 10:
            return this.ResponseError$10(var2, var3);
         case 11:
            return this.Fault$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.__repr__$13(var2, var3);
         case 14:
            return this.Boolean$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this.encode$16(var2, var3);
         case 17:
            return this.__cmp__$17(var2, var3);
         case 18:
            return this.__repr__$18(var2, var3);
         case 19:
            return this.__int__$19(var2, var3);
         case 20:
            return this.__nonzero__$20(var2, var3);
         case 21:
            return this.boolean$21(var2, var3);
         case 22:
            return this._strftime$22(var2, var3);
         case 23:
            return this.DateTime$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.make_comparable$25(var2, var3);
         case 26:
            return this.__lt__$26(var2, var3);
         case 27:
            return this.__le__$27(var2, var3);
         case 28:
            return this.__gt__$28(var2, var3);
         case 29:
            return this.__ge__$29(var2, var3);
         case 30:
            return this.__eq__$30(var2, var3);
         case 31:
            return this.__ne__$31(var2, var3);
         case 32:
            return this.timetuple$32(var2, var3);
         case 33:
            return this.__cmp__$33(var2, var3);
         case 34:
            return this.__str__$34(var2, var3);
         case 35:
            return this.__repr__$35(var2, var3);
         case 36:
            return this.decode$36(var2, var3);
         case 37:
            return this.encode$37(var2, var3);
         case 38:
            return this._datetime$38(var2, var3);
         case 39:
            return this._datetime_type$39(var2, var3);
         case 40:
            return this.Binary$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.__str__$42(var2, var3);
         case 43:
            return this.__cmp__$43(var2, var3);
         case 44:
            return this.decode$44(var2, var3);
         case 45:
            return this.encode$45(var2, var3);
         case 46:
            return this._binary$46(var2, var3);
         case 47:
            return this.ExpatParser$47(var2, var3);
         case 48:
            return this.__init__$48(var2, var3);
         case 49:
            return this.feed$49(var2, var3);
         case 50:
            return this.close$50(var2, var3);
         case 51:
            return this.SlowParser$51(var2, var3);
         case 52:
            return this.__init__$52(var2, var3);
         case 53:
            return this.Marshaller$53(var2, var3);
         case 54:
            return this.__init__$54(var2, var3);
         case 55:
            return this.dumps$55(var2, var3);
         case 56:
            return this._Marshaller__dump$56(var2, var3);
         case 57:
            return this.dump_nil$57(var2, var3);
         case 58:
            return this.dump_int$58(var2, var3);
         case 59:
            return this.dump_bool$59(var2, var3);
         case 60:
            return this.dump_long$60(var2, var3);
         case 61:
            return this.dump_double$61(var2, var3);
         case 62:
            return this.dump_string$62(var2, var3);
         case 63:
            return this.dump_unicode$63(var2, var3);
         case 64:
            return this.dump_array$64(var2, var3);
         case 65:
            return this.dump_struct$65(var2, var3);
         case 66:
            return this.dump_datetime$66(var2, var3);
         case 67:
            return this.dump_instance$67(var2, var3);
         case 68:
            return this.Unmarshaller$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this.close$70(var2, var3);
         case 71:
            return this.getmethodname$71(var2, var3);
         case 72:
            return this.xml$72(var2, var3);
         case 73:
            return this.start$73(var2, var3);
         case 74:
            return this.data$74(var2, var3);
         case 75:
            return this.end$75(var2, var3);
         case 76:
            return this.end_dispatch$76(var2, var3);
         case 77:
            return this.end_nil$77(var2, var3);
         case 78:
            return this.end_boolean$78(var2, var3);
         case 79:
            return this.end_int$79(var2, var3);
         case 80:
            return this.end_double$80(var2, var3);
         case 81:
            return this.end_string$81(var2, var3);
         case 82:
            return this.end_array$82(var2, var3);
         case 83:
            return this.end_struct$83(var2, var3);
         case 84:
            return this.end_base64$84(var2, var3);
         case 85:
            return this.end_dateTime$85(var2, var3);
         case 86:
            return this.end_value$86(var2, var3);
         case 87:
            return this.end_params$87(var2, var3);
         case 88:
            return this.end_fault$88(var2, var3);
         case 89:
            return this.end_methodName$89(var2, var3);
         case 90:
            return this._MultiCallMethod$90(var2, var3);
         case 91:
            return this.__init__$91(var2, var3);
         case 92:
            return this.__getattr__$92(var2, var3);
         case 93:
            return this.__call__$93(var2, var3);
         case 94:
            return this.MultiCallIterator$94(var2, var3);
         case 95:
            return this.__init__$95(var2, var3);
         case 96:
            return this.__getitem__$96(var2, var3);
         case 97:
            return this.MultiCall$97(var2, var3);
         case 98:
            return this.__init__$98(var2, var3);
         case 99:
            return this.__repr__$99(var2, var3);
         case 100:
            return this.__getattr__$100(var2, var3);
         case 101:
            return this.__call__$101(var2, var3);
         case 102:
            return this.getparser$102(var2, var3);
         case 103:
            return this.dumps$103(var2, var3);
         case 104:
            return this.loads$104(var2, var3);
         case 105:
            return this.gzip_encode$105(var2, var3);
         case 106:
            return this.gzip_decode$106(var2, var3);
         case 107:
            return this.GzipDecodedResponse$107(var2, var3);
         case 108:
            return this.__init__$108(var2, var3);
         case 109:
            return this.close$109(var2, var3);
         case 110:
            return this._Method$110(var2, var3);
         case 111:
            return this.__init__$111(var2, var3);
         case 112:
            return this.__getattr__$112(var2, var3);
         case 113:
            return this.__call__$113(var2, var3);
         case 114:
            return this.Transport$114(var2, var3);
         case 115:
            return this.__init__$115(var2, var3);
         case 116:
            return this.request$116(var2, var3);
         case 117:
            return this.single_request$117(var2, var3);
         case 118:
            return this.getparser$118(var2, var3);
         case 119:
            return this.get_host_info$119(var2, var3);
         case 120:
            return this.make_connection$120(var2, var3);
         case 121:
            return this.close$121(var2, var3);
         case 122:
            return this.send_request$122(var2, var3);
         case 123:
            return this.send_host$123(var2, var3);
         case 124:
            return this.send_user_agent$124(var2, var3);
         case 125:
            return this.send_content$125(var2, var3);
         case 126:
            return this.parse_response$126(var2, var3);
         case 127:
            return this.SafeTransport$127(var2, var3);
         case 128:
            return this.make_connection$128(var2, var3);
         case 129:
            return this.ServerProxy$129(var2, var3);
         case 130:
            return this.__init__$130(var2, var3);
         case 131:
            return this._ServerProxy__close$131(var2, var3);
         case 132:
            return this._ServerProxy__request$132(var2, var3);
         case 133:
            return this.__repr__$133(var2, var3);
         case 134:
            return this.__getattr__$134(var2, var3);
         case 135:
            return this.__call__$135(var2, var3);
         default:
            return null;
      }
   }
}

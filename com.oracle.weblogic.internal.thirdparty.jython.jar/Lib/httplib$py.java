import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
import org.python.core.PySet;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("httplib.py")
public class httplib$py extends PyFunctionTable implements PyRunnable {
   static httplib$py self;
   static final PyCode f$0;
   static final PyCode HTTPMessage$1;
   static final PyCode addheader$2;
   static final PyCode addcontinue$3;
   static final PyCode readheaders$4;
   static final PyCode HTTPResponse$5;
   static final PyCode __init__$6;
   static final PyCode _read_status$7;
   static final PyCode begin$8;
   static final PyCode _check_close$9;
   static final PyCode close$10;
   static final PyCode isclosed$11;
   static final PyCode read$12;
   static final PyCode _read_chunked$13;
   static final PyCode _safe_read$14;
   static final PyCode fileno$15;
   static final PyCode getheader$16;
   static final PyCode getheaders$17;
   static final PyCode HTTPConnection$18;
   static final PyCode __init__$19;
   static final PyCode set_tunnel$20;
   static final PyCode _get_hostport$21;
   static final PyCode set_debuglevel$22;
   static final PyCode _tunnel$23;
   static final PyCode connect$24;
   static final PyCode close$25;
   static final PyCode send$26;
   static final PyCode _output$27;
   static final PyCode _send_output$28;
   static final PyCode putrequest$29;
   static final PyCode putheader$30;
   static final PyCode endheaders$31;
   static final PyCode request$32;
   static final PyCode _set_content_length$33;
   static final PyCode _send_request$34;
   static final PyCode getresponse$35;
   static final PyCode HTTP$36;
   static final PyCode __init__$37;
   static final PyCode _setup$38;
   static final PyCode connect$39;
   static final PyCode getfile$40;
   static final PyCode getreply$41;
   static final PyCode close$42;
   static final PyCode HTTPSConnection$43;
   static final PyCode __init__$44;
   static final PyCode connect$45;
   static final PyCode HTTPS$46;
   static final PyCode __init__$47;
   static final PyCode FakeSocket$48;
   static final PyCode HTTPException$49;
   static final PyCode NotConnected$50;
   static final PyCode InvalidURL$51;
   static final PyCode UnknownProtocol$52;
   static final PyCode __init__$53;
   static final PyCode UnknownTransferEncoding$54;
   static final PyCode UnimplementedFileMode$55;
   static final PyCode IncompleteRead$56;
   static final PyCode __init__$57;
   static final PyCode __repr__$58;
   static final PyCode __str__$59;
   static final PyCode ImproperConnectionState$60;
   static final PyCode CannotSendRequest$61;
   static final PyCode CannotSendHeader$62;
   static final PyCode ResponseNotReady$63;
   static final PyCode BadStatusLine$64;
   static final PyCode __init__$65;
   static final PyCode LineTooLong$66;
   static final PyCode __init__$67;
   static final PyCode LineAndFileWrapper$68;
   static final PyCode __init__$69;
   static final PyCode __getattr__$70;
   static final PyCode _done$71;
   static final PyCode read$72;
   static final PyCode readline$73;
   static final PyCode readlines$74;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setglobal("__doc__", PyString.fromInterned("HTTP/1.1 client library\n\n<intro stuff goes here>\n<other stuff, too>\n\nHTTPConnection goes through a number of \"states\", which define when a client\nmay legally make another request or fetch the response for a particular\nrequest. This diagram details these state transitions:\n\n    (null)\n      |\n      | HTTPConnection()\n      v\n    Idle\n      |\n      | putrequest()\n      v\n    Request-started\n      |\n      | ( putheader() )*  endheaders()\n      v\n    Request-sent\n      |\n      | response = getresponse()\n      v\n    Unread-response   [Response-headers-read]\n      |\\____________________\n      |                     |\n      | response.read()     | putrequest()\n      v                     v\n    Idle                  Req-started-unread-response\n                     ______/|\n                   /        |\n   response.read() |        | ( putheader() )*  endheaders()\n                   v        v\n       Request-started    Req-sent-unread-response\n                            |\n                            | response.read()\n                            v\n                          Request-sent\n\nThis diagram presents the following rules:\n  -- a second request may not be started until {response-headers-read}\n  -- a response [object] cannot be retrieved until {request-sent}\n  -- there is no differentiation between an unread response body and a\n     partially read response body\n\nNote: this enforcement is applied by the HTTPConnection class. The\n      HTTPResponse class does not enforce this state machine, which\n      implies sophisticated clients may accelerate the request/response\n      pipeline. Caution should be taken, though: accelerating the states\n      beyond the above pattern may imply knowledge of the server's\n      connection-close behavior for certain requests. For example, it\n      is impossible to tell whether the server will close the connection\n      UNTIL the response headers have been read; this means that further\n      requests cannot be placed into the pipeline until it is known that\n      the server will NOT be closing the connection.\n\nLogical State                  __state            __response\n-------------                  -------            ----------\nIdle                           _CS_IDLE           None\nRequest-started                _CS_REQ_STARTED    None\nRequest-sent                   _CS_REQ_SENT       None\nUnread-response                _CS_IDLE           <response_class>\nReq-started-unread-response    _CS_REQ_STARTED    <response_class>\nReq-sent-unread-response       _CS_REQ_SENT       <response_class>\n"));
      var1.setline(67);
      PyString.fromInterned("HTTP/1.1 client library\n\n<intro stuff goes here>\n<other stuff, too>\n\nHTTPConnection goes through a number of \"states\", which define when a client\nmay legally make another request or fetch the response for a particular\nrequest. This diagram details these state transitions:\n\n    (null)\n      |\n      | HTTPConnection()\n      v\n    Idle\n      |\n      | putrequest()\n      v\n    Request-started\n      |\n      | ( putheader() )*  endheaders()\n      v\n    Request-sent\n      |\n      | response = getresponse()\n      v\n    Unread-response   [Response-headers-read]\n      |\\____________________\n      |                     |\n      | response.read()     | putrequest()\n      v                     v\n    Idle                  Req-started-unread-response\n                     ______/|\n                   /        |\n   response.read() |        | ( putheader() )*  endheaders()\n                   v        v\n       Request-started    Req-sent-unread-response\n                            |\n                            | response.read()\n                            v\n                          Request-sent\n\nThis diagram presents the following rules:\n  -- a second request may not be started until {response-headers-read}\n  -- a response [object] cannot be retrieved until {request-sent}\n  -- there is no differentiation between an unread response body and a\n     partially read response body\n\nNote: this enforcement is applied by the HTTPConnection class. The\n      HTTPResponse class does not enforce this state machine, which\n      implies sophisticated clients may accelerate the request/response\n      pipeline. Caution should be taken, though: accelerating the states\n      beyond the above pattern may imply knowledge of the server's\n      connection-close behavior for certain requests. For example, it\n      is impossible to tell whether the server will close the connection\n      UNTIL the response headers have been read; this means that further\n      requests cannot be placed into the pipeline until it is known that\n      the server will NOT be closing the connection.\n\nLogical State                  __state            __response\n-------------                  -------            ----------\nIdle                           _CS_IDLE           None\nRequest-started                _CS_REQ_STARTED    None\nRequest-sent                   _CS_REQ_SENT       None\nUnread-response                _CS_IDLE           <response_class>\nReq-started-unread-response    _CS_REQ_STARTED    <response_class>\nReq-sent-unread-response       _CS_REQ_SENT       <response_class>\n");
      var1.setline(69);
      String[] var3 = new String[]{"array"};
      PyObject[] var9 = imp.importFrom("array", var3, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("array", var4);
      var4 = null;
      var1.setline(70);
      PyObject var10 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var10);
      var3 = null;
      var1.setline(71);
      var10 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var10);
      var3 = null;
      var1.setline(72);
      var10 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var10);
      var3 = null;
      var1.setline(73);
      var3 = new String[]{"py3kwarning"};
      var9 = imp.importFrom("sys", var3, var1, -1);
      var4 = var9[0];
      var1.setlocal("py3kwarning", var4);
      var4 = null;
      var1.setline(74);
      var3 = new String[]{"urlsplit"};
      var9 = imp.importFrom("urlparse", var3, var1, -1);
      var4 = var9[0];
      var1.setlocal("urlsplit", var4);
      var4 = null;
      var1.setline(75);
      var10 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var10);
      var3 = null;
      ContextManager var13;
      var4 = (var13 = ContextGuard.getManager(var1.getname("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      label50: {
         try {
            var1.setline(77);
            if (var1.getname("py3kwarning").__nonzero__()) {
               var1.setline(78);
               var1.getname("warnings").__getattr__("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*mimetools has been removed"), (PyObject)var1.getname("DeprecationWarning"));
            }

            var1.setline(80);
            var4 = imp.importOne("mimetools", var1, -1);
            var1.setlocal("mimetools", var4);
            var4 = null;
         } catch (Throwable var8) {
            if (var13.__exit__(var2, Py.setException(var8, var1))) {
               break label50;
            }

            throw (Throwable)Py.makeException();
         }

         var13.__exit__(var2, (PyException)null);
      }

      PyObject var5;
      PyObject[] var12;
      PyException var14;
      try {
         var1.setline(83);
         var3 = new String[]{"StringIO"};
         var9 = imp.importFrom("cStringIO", var3, var1, -1);
         var4 = var9[0];
         var1.setlocal("StringIO", var4);
         var4 = null;
      } catch (Throwable var7) {
         var14 = Py.setException(var7, var1);
         if (!var14.match(var1.getname("ImportError"))) {
            throw var14;
         }

         var1.setline(85);
         String[] var11 = new String[]{"StringIO"};
         var12 = imp.importFrom("StringIO", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(87);
      PyList var15 = new PyList(new PyObject[]{PyString.fromInterned("HTTP"), PyString.fromInterned("HTTPResponse"), PyString.fromInterned("HTTPConnection"), PyString.fromInterned("HTTPException"), PyString.fromInterned("NotConnected"), PyString.fromInterned("UnknownProtocol"), PyString.fromInterned("UnknownTransferEncoding"), PyString.fromInterned("UnimplementedFileMode"), PyString.fromInterned("IncompleteRead"), PyString.fromInterned("InvalidURL"), PyString.fromInterned("ImproperConnectionState"), PyString.fromInterned("CannotSendRequest"), PyString.fromInterned("CannotSendHeader"), PyString.fromInterned("ResponseNotReady"), PyString.fromInterned("BadStatusLine"), PyString.fromInterned("error"), PyString.fromInterned("responses")});
      var1.setlocal("__all__", var15);
      var3 = null;
      var1.setline(94);
      PyInteger var16 = Py.newInteger(80);
      var1.setlocal("HTTP_PORT", var16);
      var3 = null;
      var1.setline(95);
      var16 = Py.newInteger(443);
      var1.setlocal("HTTPS_PORT", var16);
      var3 = null;
      var1.setline(97);
      PyString var17 = PyString.fromInterned("UNKNOWN");
      var1.setlocal("_UNKNOWN", var17);
      var3 = null;
      var1.setline(100);
      var17 = PyString.fromInterned("Idle");
      var1.setlocal("_CS_IDLE", var17);
      var3 = null;
      var1.setline(101);
      var17 = PyString.fromInterned("Request-started");
      var1.setlocal("_CS_REQ_STARTED", var17);
      var3 = null;
      var1.setline(102);
      var17 = PyString.fromInterned("Request-sent");
      var1.setlocal("_CS_REQ_SENT", var17);
      var3 = null;
      var1.setline(106);
      var16 = Py.newInteger(100);
      var1.setlocal("CONTINUE", var16);
      var3 = null;
      var1.setline(107);
      var16 = Py.newInteger(101);
      var1.setlocal("SWITCHING_PROTOCOLS", var16);
      var3 = null;
      var1.setline(108);
      var16 = Py.newInteger(102);
      var1.setlocal("PROCESSING", var16);
      var3 = null;
      var1.setline(111);
      var16 = Py.newInteger(200);
      var1.setlocal("OK", var16);
      var3 = null;
      var1.setline(112);
      var16 = Py.newInteger(201);
      var1.setlocal("CREATED", var16);
      var3 = null;
      var1.setline(113);
      var16 = Py.newInteger(202);
      var1.setlocal("ACCEPTED", var16);
      var3 = null;
      var1.setline(114);
      var16 = Py.newInteger(203);
      var1.setlocal("NON_AUTHORITATIVE_INFORMATION", var16);
      var3 = null;
      var1.setline(115);
      var16 = Py.newInteger(204);
      var1.setlocal("NO_CONTENT", var16);
      var3 = null;
      var1.setline(116);
      var16 = Py.newInteger(205);
      var1.setlocal("RESET_CONTENT", var16);
      var3 = null;
      var1.setline(117);
      var16 = Py.newInteger(206);
      var1.setlocal("PARTIAL_CONTENT", var16);
      var3 = null;
      var1.setline(118);
      var16 = Py.newInteger(207);
      var1.setlocal("MULTI_STATUS", var16);
      var3 = null;
      var1.setline(119);
      var16 = Py.newInteger(226);
      var1.setlocal("IM_USED", var16);
      var3 = null;
      var1.setline(122);
      var16 = Py.newInteger(300);
      var1.setlocal("MULTIPLE_CHOICES", var16);
      var3 = null;
      var1.setline(123);
      var16 = Py.newInteger(301);
      var1.setlocal("MOVED_PERMANENTLY", var16);
      var3 = null;
      var1.setline(124);
      var16 = Py.newInteger(302);
      var1.setlocal("FOUND", var16);
      var3 = null;
      var1.setline(125);
      var16 = Py.newInteger(303);
      var1.setlocal("SEE_OTHER", var16);
      var3 = null;
      var1.setline(126);
      var16 = Py.newInteger(304);
      var1.setlocal("NOT_MODIFIED", var16);
      var3 = null;
      var1.setline(127);
      var16 = Py.newInteger(305);
      var1.setlocal("USE_PROXY", var16);
      var3 = null;
      var1.setline(128);
      var16 = Py.newInteger(307);
      var1.setlocal("TEMPORARY_REDIRECT", var16);
      var3 = null;
      var1.setline(131);
      var16 = Py.newInteger(400);
      var1.setlocal("BAD_REQUEST", var16);
      var3 = null;
      var1.setline(132);
      var16 = Py.newInteger(401);
      var1.setlocal("UNAUTHORIZED", var16);
      var3 = null;
      var1.setline(133);
      var16 = Py.newInteger(402);
      var1.setlocal("PAYMENT_REQUIRED", var16);
      var3 = null;
      var1.setline(134);
      var16 = Py.newInteger(403);
      var1.setlocal("FORBIDDEN", var16);
      var3 = null;
      var1.setline(135);
      var16 = Py.newInteger(404);
      var1.setlocal("NOT_FOUND", var16);
      var3 = null;
      var1.setline(136);
      var16 = Py.newInteger(405);
      var1.setlocal("METHOD_NOT_ALLOWED", var16);
      var3 = null;
      var1.setline(137);
      var16 = Py.newInteger(406);
      var1.setlocal("NOT_ACCEPTABLE", var16);
      var3 = null;
      var1.setline(138);
      var16 = Py.newInteger(407);
      var1.setlocal("PROXY_AUTHENTICATION_REQUIRED", var16);
      var3 = null;
      var1.setline(139);
      var16 = Py.newInteger(408);
      var1.setlocal("REQUEST_TIMEOUT", var16);
      var3 = null;
      var1.setline(140);
      var16 = Py.newInteger(409);
      var1.setlocal("CONFLICT", var16);
      var3 = null;
      var1.setline(141);
      var16 = Py.newInteger(410);
      var1.setlocal("GONE", var16);
      var3 = null;
      var1.setline(142);
      var16 = Py.newInteger(411);
      var1.setlocal("LENGTH_REQUIRED", var16);
      var3 = null;
      var1.setline(143);
      var16 = Py.newInteger(412);
      var1.setlocal("PRECONDITION_FAILED", var16);
      var3 = null;
      var1.setline(144);
      var16 = Py.newInteger(413);
      var1.setlocal("REQUEST_ENTITY_TOO_LARGE", var16);
      var3 = null;
      var1.setline(145);
      var16 = Py.newInteger(414);
      var1.setlocal("REQUEST_URI_TOO_LONG", var16);
      var3 = null;
      var1.setline(146);
      var16 = Py.newInteger(415);
      var1.setlocal("UNSUPPORTED_MEDIA_TYPE", var16);
      var3 = null;
      var1.setline(147);
      var16 = Py.newInteger(416);
      var1.setlocal("REQUESTED_RANGE_NOT_SATISFIABLE", var16);
      var3 = null;
      var1.setline(148);
      var16 = Py.newInteger(417);
      var1.setlocal("EXPECTATION_FAILED", var16);
      var3 = null;
      var1.setline(149);
      var16 = Py.newInteger(422);
      var1.setlocal("UNPROCESSABLE_ENTITY", var16);
      var3 = null;
      var1.setline(150);
      var16 = Py.newInteger(423);
      var1.setlocal("LOCKED", var16);
      var3 = null;
      var1.setline(151);
      var16 = Py.newInteger(424);
      var1.setlocal("FAILED_DEPENDENCY", var16);
      var3 = null;
      var1.setline(152);
      var16 = Py.newInteger(426);
      var1.setlocal("UPGRADE_REQUIRED", var16);
      var3 = null;
      var1.setline(155);
      var16 = Py.newInteger(500);
      var1.setlocal("INTERNAL_SERVER_ERROR", var16);
      var3 = null;
      var1.setline(156);
      var16 = Py.newInteger(501);
      var1.setlocal("NOT_IMPLEMENTED", var16);
      var3 = null;
      var1.setline(157);
      var16 = Py.newInteger(502);
      var1.setlocal("BAD_GATEWAY", var16);
      var3 = null;
      var1.setline(158);
      var16 = Py.newInteger(503);
      var1.setlocal("SERVICE_UNAVAILABLE", var16);
      var3 = null;
      var1.setline(159);
      var16 = Py.newInteger(504);
      var1.setlocal("GATEWAY_TIMEOUT", var16);
      var3 = null;
      var1.setline(160);
      var16 = Py.newInteger(505);
      var1.setlocal("HTTP_VERSION_NOT_SUPPORTED", var16);
      var3 = null;
      var1.setline(161);
      var16 = Py.newInteger(507);
      var1.setlocal("INSUFFICIENT_STORAGE", var16);
      var3 = null;
      var1.setline(162);
      var16 = Py.newInteger(510);
      var1.setlocal("NOT_EXTENDED", var16);
      var3 = null;
      var1.setline(165);
      PyDictionary var19 = new PyDictionary(new PyObject[]{Py.newInteger(100), PyString.fromInterned("Continue"), Py.newInteger(101), PyString.fromInterned("Switching Protocols"), Py.newInteger(200), PyString.fromInterned("OK"), Py.newInteger(201), PyString.fromInterned("Created"), Py.newInteger(202), PyString.fromInterned("Accepted"), Py.newInteger(203), PyString.fromInterned("Non-Authoritative Information"), Py.newInteger(204), PyString.fromInterned("No Content"), Py.newInteger(205), PyString.fromInterned("Reset Content"), Py.newInteger(206), PyString.fromInterned("Partial Content"), Py.newInteger(300), PyString.fromInterned("Multiple Choices"), Py.newInteger(301), PyString.fromInterned("Moved Permanently"), Py.newInteger(302), PyString.fromInterned("Found"), Py.newInteger(303), PyString.fromInterned("See Other"), Py.newInteger(304), PyString.fromInterned("Not Modified"), Py.newInteger(305), PyString.fromInterned("Use Proxy"), Py.newInteger(306), PyString.fromInterned("(Unused)"), Py.newInteger(307), PyString.fromInterned("Temporary Redirect"), Py.newInteger(400), PyString.fromInterned("Bad Request"), Py.newInteger(401), PyString.fromInterned("Unauthorized"), Py.newInteger(402), PyString.fromInterned("Payment Required"), Py.newInteger(403), PyString.fromInterned("Forbidden"), Py.newInteger(404), PyString.fromInterned("Not Found"), Py.newInteger(405), PyString.fromInterned("Method Not Allowed"), Py.newInteger(406), PyString.fromInterned("Not Acceptable"), Py.newInteger(407), PyString.fromInterned("Proxy Authentication Required"), Py.newInteger(408), PyString.fromInterned("Request Timeout"), Py.newInteger(409), PyString.fromInterned("Conflict"), Py.newInteger(410), PyString.fromInterned("Gone"), Py.newInteger(411), PyString.fromInterned("Length Required"), Py.newInteger(412), PyString.fromInterned("Precondition Failed"), Py.newInteger(413), PyString.fromInterned("Request Entity Too Large"), Py.newInteger(414), PyString.fromInterned("Request-URI Too Long"), Py.newInteger(415), PyString.fromInterned("Unsupported Media Type"), Py.newInteger(416), PyString.fromInterned("Requested Range Not Satisfiable"), Py.newInteger(417), PyString.fromInterned("Expectation Failed"), Py.newInteger(500), PyString.fromInterned("Internal Server Error"), Py.newInteger(501), PyString.fromInterned("Not Implemented"), Py.newInteger(502), PyString.fromInterned("Bad Gateway"), Py.newInteger(503), PyString.fromInterned("Service Unavailable"), Py.newInteger(504), PyString.fromInterned("Gateway Timeout"), Py.newInteger(505), PyString.fromInterned("HTTP Version Not Supported")});
      var1.setlocal("responses", var19);
      var3 = null;
      var1.setline(214);
      var16 = Py.newInteger(1048576);
      var1.setlocal("MAXAMOUNT", var16);
      var3 = null;
      var1.setline(217);
      var16 = Py.newInteger(65536);
      var1.setlocal("_MAXLINE", var16);
      var3 = null;
      var1.setline(220);
      var16 = Py.newInteger(100);
      var1.setlocal("_MAXHEADERS", var16);
      var3 = null;
      var1.setline(247);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\A[^:\\s][^:\\r\\n]*\\Z")).__getattr__("match");
      var1.setlocal("_is_legal_header_name", var10);
      var3 = null;
      var1.setline(248);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\n(?![ \\t])|\\r(?![ \\t\\n])")).__getattr__("search");
      var1.setlocal("_is_illegal_header_value", var10);
      var3 = null;
      var1.setline(252);
      PySet var20 = new PySet(new PyObject[]{PyString.fromInterned("PATCH"), PyString.fromInterned("POST"), PyString.fromInterned("PUT")});
      var1.setlocal("_METHODS_EXPECTING_BODY", var20);
      var3 = null;
      var1.setline(255);
      var9 = new PyObject[]{var1.getname("mimetools").__getattr__("Message")};
      var4 = Py.makeClass("HTTPMessage", var9, HTTPMessage$1);
      var1.setlocal("HTTPMessage", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(354);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("HTTPResponse", var9, HTTPResponse$5);
      var1.setlocal("HTTPResponse", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(710);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("HTTPConnection", var9, HTTPConnection$18);
      var1.setlocal("HTTPConnection", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1138);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("HTTP", var9, HTTP$36);
      var1.setlocal("HTTP", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);

      label33: {
         try {
            var1.setline(1230);
            var10 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var10);
            var3 = null;
         } catch (Throwable var6) {
            var14 = Py.setException(var6, var1);
            if (var14.match(var1.getname("ImportError"))) {
               var1.setline(1232);
               break label33;
            }

            throw var14;
         }

         var1.setline(1234);
         var12 = new PyObject[]{var1.getname("HTTPConnection")};
         var5 = Py.makeClass("HTTPSConnection", var12, HTTPSConnection$43);
         var1.setlocal("HTTPSConnection", var5);
         var5 = null;
         Arrays.fill(var12, (Object)null);
         var1.setline(1265);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTPSConnection"));
         var1.setline(1267);
         var12 = new PyObject[]{var1.getname("HTTP")};
         var5 = Py.makeClass("HTTPS", var12, HTTPS$46);
         var1.setlocal("HTTPS", var5);
         var5 = null;
         Arrays.fill(var12, (Object)null);
         var1.setline(1294);
         var12 = Py.EmptyObjects;
         PyFunction var18 = new PyFunction(var1.f_globals, var12, FakeSocket$48, (PyObject)null);
         var1.setlocal("FakeSocket", var18);
         var4 = null;
      }

      var1.setline(1301);
      var9 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("HTTPException", var9, HTTPException$49);
      var1.setlocal("HTTPException", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1306);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("NotConnected", var9, NotConnected$50);
      var1.setlocal("NotConnected", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1309);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("InvalidURL", var9, InvalidURL$51);
      var1.setlocal("InvalidURL", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1312);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("UnknownProtocol", var9, UnknownProtocol$52);
      var1.setlocal("UnknownProtocol", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1317);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("UnknownTransferEncoding", var9, UnknownTransferEncoding$54);
      var1.setlocal("UnknownTransferEncoding", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1320);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("UnimplementedFileMode", var9, UnimplementedFileMode$55);
      var1.setlocal("UnimplementedFileMode", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1323);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("IncompleteRead", var9, IncompleteRead$56);
      var1.setlocal("IncompleteRead", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1337);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("ImproperConnectionState", var9, ImproperConnectionState$60);
      var1.setlocal("ImproperConnectionState", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1340);
      var9 = new PyObject[]{var1.getname("ImproperConnectionState")};
      var4 = Py.makeClass("CannotSendRequest", var9, CannotSendRequest$61);
      var1.setlocal("CannotSendRequest", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1343);
      var9 = new PyObject[]{var1.getname("ImproperConnectionState")};
      var4 = Py.makeClass("CannotSendHeader", var9, CannotSendHeader$62);
      var1.setlocal("CannotSendHeader", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1346);
      var9 = new PyObject[]{var1.getname("ImproperConnectionState")};
      var4 = Py.makeClass("ResponseNotReady", var9, ResponseNotReady$63);
      var1.setlocal("ResponseNotReady", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1349);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("BadStatusLine", var9, BadStatusLine$64);
      var1.setlocal("BadStatusLine", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1356);
      var9 = new PyObject[]{var1.getname("HTTPException")};
      var4 = Py.makeClass("LineTooLong", var9, LineTooLong$66);
      var1.setlocal("LineTooLong", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1362);
      var10 = var1.getname("HTTPException");
      var1.setlocal("error", var10);
      var3 = null;
      var1.setline(1364);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("LineAndFileWrapper", var9, LineAndFileWrapper$68);
      var1.setlocal("LineAndFileWrapper", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTTPMessage$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(257);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, addheader$2, PyString.fromInterned("Add header for field key handling repeats."));
      var1.setlocal("addheader", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addcontinue$3, PyString.fromInterned("Add more field data from a continuation line."));
      var1.setlocal("addcontinue", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readheaders$4, PyString.fromInterned("Read header lines.\n\n        Read header lines up to the entirely blank line that terminates them.\n        The (normally blank) line that ends the headers is skipped, but not\n        included in the returned list.  If an invalid line is found in the\n        header section, it is skipped, and further lines are processed.\n\n        The variable self.status is set to the empty string if all went well,\n        otherwise it is an error message.  The variable self.headers is a\n        completely uninterpreted list of lines contained in the header (so\n        printing them will reproduce the header exactly as it appears in the\n        file).\n\n        If multiple header fields with the same name occur, they are combined\n        according to the rules in RFC 2616 sec 4.2:\n\n        Appending each subsequent field-value to the first, each separated\n        by a comma. The order in which header fields with the same field-name\n        are received is significant to the interpretation of the combined\n        field value.\n        "));
      var1.setlocal("readheaders", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject addheader$2(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyString.fromInterned("Add header for field key handling repeats.");
      var1.setline(259);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(260);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(261);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      } else {
         var1.setline(263);
         var3 = PyString.fromInterned(", ").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)})));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(264);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addcontinue$3(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyString.fromInterned("Add more field data from a continuation line.");
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(269);
      var3 = var1.getlocal(3)._add(PyString.fromInterned("\n "))._add(var1.getlocal(2));
      var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readheaders$4(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyString.fromInterned("Read header lines.\n\n        Read header lines up to the entirely blank line that terminates them.\n        The (normally blank) line that ends the headers is skipped, but not\n        included in the returned list.  If an invalid line is found in the\n        header section, it is skipped, and further lines are processed.\n\n        The variable self.status is set to the empty string if all went well,\n        otherwise it is an error message.  The variable self.headers is a\n        completely uninterpreted list of lines contained in the header (so\n        printing them will reproduce the header exactly as it appears in the\n        file).\n\n        If multiple header fields with the same name occur, they are combined\n        according to the rules in RFC 2616 sec 4.2:\n\n        Appending each subsequent field-value to the first, each separated\n        by a comma. The order in which header fields with the same field-name\n        are received is significant to the interpretation of the combined\n        field value.\n        ");
      var1.setline(298);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"dict", var3);
      var3 = null;
      var1.setline(299);
      PyString var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"unixfrom", var6);
      var3 = null;
      var1.setline(300);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"headers", var7);
      var1.setlocal(1, var7);
      var1.setline(301);
      var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"status", var6);
      var3 = null;
      var1.setline(302);
      var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(303);
      PyInteger var9 = Py.newInteger(1);
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(304);
      PyObject var10 = var1.getglobal("None");
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(305);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("unread")).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("seekable");
      }

      if (var10000.__nonzero__()) {
         var1.setline(306);
         var10 = var1.getlocal(0).__getattr__("fp").__getattr__("tell");
         var1.setlocal(4, var10);
         var3 = null;
      }

      while(true) {
         var1.setline(307);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(308);
         var10 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var10._gt(var1.getglobal("_MAXHEADERS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(309);
            throw Py.makeException(var1.getglobal("HTTPException").__call__(var2, PyString.fromInterned("got more than %d headers")._mod(var1.getglobal("_MAXHEADERS"))));
         }

         var1.setline(310);
         if (var1.getlocal(4).__nonzero__()) {
            try {
               var1.setline(312);
               var1.getlocal(4).__call__(var2);
            } catch (Throwable var5) {
               PyException var11 = Py.setException(var5, var1);
               if (!var11.match(var1.getglobal("IOError"))) {
                  throw var11;
               }

               var1.setline(314);
               PyObject var4 = var1.getglobal("None");
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(315);
               PyInteger var8 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"seekable", var8);
               var4 = null;
            }
         }

         var1.setline(316);
         var10 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
         var1.setlocal(5, var10);
         var3 = null;
         var1.setline(317);
         var10 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var10000 = var10._gt(var1.getglobal("_MAXLINE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(318);
            throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header line")));
         }

         var1.setline(319);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(320);
            var6 = PyString.fromInterned("EOF in headers");
            var1.getlocal(0).__setattr__((String)"status", var6);
            var3 = null;
            break;
         }

         var1.setline(323);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From "));
         }

         if (var10000.__nonzero__()) {
            var1.setline(324);
            var10 = var1.getlocal(0).__getattr__("unixfrom")._add(var1.getlocal(5));
            var1.getlocal(0).__setattr__("unixfrom", var10);
            var3 = null;
         } else {
            var1.setline(326);
            var9 = Py.newInteger(0);
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(327);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10 = var1.getlocal(5).__getitem__(Py.newInteger(0));
               var10000 = var10._in(PyString.fromInterned(" \t"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(331);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(5));
               var1.setline(332);
               var1.getlocal(0).__getattr__("addcontinue").__call__(var2, var1.getlocal(2), var1.getlocal(5).__getattr__("strip").__call__(var2));
            } else {
               var1.setline(334);
               if (!var1.getlocal(0).__getattr__("iscomment").__call__(var2, var1.getlocal(5)).__nonzero__()) {
                  var1.setline(337);
                  if (var1.getlocal(0).__getattr__("islast").__call__(var2, var1.getlocal(5)).__nonzero__()) {
                     break;
                  }

                  var1.setline(340);
                  var10 = var1.getlocal(0).__getattr__("isheader").__call__(var2, var1.getlocal(5));
                  var1.setlocal(2, var10);
                  var3 = null;
                  var1.setline(341);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(343);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(5));
                     var1.setline(344);
                     var1.getlocal(0).__getattr__("addheader").__call__(var2, var1.getlocal(2), var1.getlocal(5).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2));
                  } else {
                     var1.setline(345);
                     var10 = var1.getlocal(2);
                     var10000 = var10._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(349);
                     } else {
                        var1.setline(352);
                        var6 = PyString.fromInterned("Non-header line where header expected");
                        var1.getlocal(0).__setattr__((String)"status", var6);
                        var3 = null;
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTTPResponse$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(364);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read_status$7, (PyObject)null);
      var1.setlocal("_read_status", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, begin$8, (PyObject)null);
      var1.setlocal("begin", var4);
      var3 = null;
      var1.setline(517);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_close$9, (PyObject)null);
      var1.setlocal("_check_close", var4);
      var3 = null;
      var1.setline(547);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$10, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(553);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isclosed$11, (PyObject)null);
      var1.setlocal("isclosed", var4);
      var3 = null;
      var1.setline(564);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$12, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(609);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read_chunked$13, (PyObject)null);
      var1.setlocal("_read_chunked", var4);
      var3 = null;
      var1.setline(667);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _safe_read$14, PyString.fromInterned("Read the number of bytes requested, compensating for partial reads.\n\n        Normally, we have a blocking socket, but a read() can be interrupted\n        by a signal (resulting in a partial read).\n\n        Note that we cannot distinguish between EOF and an interrupt when zero\n        bytes have been read. IncompleteRead() will be raised in this\n        situation.\n\n        This function should be used when <amt> bytes \"should\" be present for\n        reading. If the bytes are truly not available (due to EOF), then the\n        IncompleteRead exception can be used to detect the problem.\n        "));
      var1.setlocal("_safe_read", var4);
      var3 = null;
      var1.setline(695);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$15, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(698);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getheader$16, (PyObject)null);
      var1.setlocal("getheader", var4);
      var3 = null;
      var1.setline(703);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getheaders$17, PyString.fromInterned("Return list of (header, value) tuples."));
      var1.setlocal("getheaders", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      PyObject var3;
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(368);
         var3 = var1.getlocal(1).__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
         var1.getlocal(0).__setattr__("fp", var3);
         var3 = null;
      } else {
         var1.setline(375);
         var3 = var1.getlocal(1).__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"), (PyObject)Py.newInteger(0));
         var1.getlocal(0).__setattr__("fp", var3);
         var3 = null;
      }

      var1.setline(376);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("debuglevel", var3);
      var3 = null;
      var1.setline(377);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("strict", var3);
      var3 = null;
      var1.setline(378);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_method", var3);
      var3 = null;
      var1.setline(380);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(383);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("version", var3);
      var3 = null;
      var1.setline(384);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("status", var3);
      var3 = null;
      var1.setline(385);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("reason", var3);
      var3 = null;
      var1.setline(387);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("chunked", var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("chunk_left", var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("length", var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getglobal("_UNKNOWN");
      var1.getlocal(0).__setattr__("will_close", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read_status$7(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(395);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getglobal("_MAXLINE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(396);
         throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header line")));
      } else {
         var1.setline(397);
         var3 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(398);
            Py.printComma(PyString.fromInterned("reply:"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(399);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(402);
            throw Py.makeException(var1.getglobal("BadStatusLine").__call__(var2, var1.getlocal(1)));
         } else {
            PyException var4;
            PyString var5;
            PyObject var10;
            try {
               var1.setline(404);
               var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(2));
               PyObject[] var13 = Py.unpackSequence(var3, 3);
               PyObject var15 = var13[0];
               var1.setlocal(2, var15);
               var5 = null;
               var15 = var13[1];
               var1.setlocal(3, var15);
               var5 = null;
               var15 = var13[2];
               var1.setlocal(4, var15);
               var5 = null;
               var3 = null;
            } catch (Throwable var9) {
               PyException var11 = Py.setException(var9, var1);
               if (!var11.match(var1.getglobal("ValueError"))) {
                  throw var11;
               }

               try {
                  var1.setline(407);
                  var10 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1));
                  PyObject[] var14 = Py.unpackSequence(var10, 2);
                  PyObject var6 = var14[0];
                  var1.setlocal(2, var6);
                  var6 = null;
                  var6 = var14[1];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(408);
                  PyString var12 = PyString.fromInterned("");
                  var1.setlocal(4, var12);
                  var4 = null;
               } catch (Throwable var8) {
                  var4 = Py.setException(var8, var1);
                  if (!var4.match(var1.getglobal("ValueError"))) {
                     throw var4;
                  }

                  var1.setline(412);
                  var5 = PyString.fromInterned("");
                  var1.setlocal(2, var5);
                  var5 = null;
               }
            }

            var1.setline(413);
            PyTuple var16;
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTP/")).__not__().__nonzero__()) {
               var1.setline(414);
               if (var1.getlocal(0).__getattr__("strict").__nonzero__()) {
                  var1.setline(415);
                  var1.getlocal(0).__getattr__("close").__call__(var2);
                  var1.setline(416);
                  throw Py.makeException(var1.getglobal("BadStatusLine").__call__(var2, var1.getlocal(1)));
               } else {
                  var1.setline(419);
                  var3 = var1.getglobal("LineAndFileWrapper").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("fp"));
                  var1.getlocal(0).__setattr__("fp", var3);
                  var3 = null;
                  var1.setline(420);
                  var16 = new PyTuple(new PyObject[]{PyString.fromInterned("HTTP/0.9"), Py.newInteger(200), PyString.fromInterned("")});
                  var1.f_lasti = -1;
                  return var16;
               }
            } else {
               try {
                  var1.setline(424);
                  var10 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
                  var1.setlocal(3, var10);
                  var4 = null;
                  var1.setline(425);
                  var10 = var1.getlocal(3);
                  var10000 = var10._lt(Py.newInteger(100));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var10 = var1.getlocal(3);
                     var10000 = var10._gt(Py.newInteger(999));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(426);
                     throw Py.makeException(var1.getglobal("BadStatusLine").__call__(var2, var1.getlocal(1)));
                  }
               } catch (Throwable var7) {
                  var4 = Py.setException(var7, var1);
                  if (var4.match(var1.getglobal("ValueError"))) {
                     var1.setline(428);
                     throw Py.makeException(var1.getglobal("BadStatusLine").__call__(var2, var1.getlocal(1)));
                  }

                  throw var4;
               }

               var1.setline(429);
               var16 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
               var1.f_lasti = -1;
               return var16;
            }
         }
      }
   }

   public PyObject begin$8(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(434);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var4;
         while(true) {
            var1.setline(437);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(438);
            var3 = var1.getlocal(0).__getattr__("_read_status").__call__(var2);
            var4 = Py.unpackSequence(var3, 3);
            PyObject var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
            var1.setline(439);
            var3 = var1.getlocal(2);
            var10000 = var3._ne(var1.getglobal("CONTINUE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            while(true) {
               var1.setline(442);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(443);
               var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(444);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var3._gt(var1.getglobal("_MAXLINE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(445);
                  throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header line")));
               }

               var1.setline(446);
               var3 = var1.getlocal(4).__getattr__("strip").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(447);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(449);
               var3 = var1.getlocal(0).__getattr__("debuglevel");
               var10000 = var3._gt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(450);
                  Py.printComma(PyString.fromInterned("header:"));
                  Py.println(var1.getlocal(4));
               }
            }
         }

         var1.setline(452);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("status", var3);
         var3 = null;
         var1.setline(453);
         var3 = var1.getlocal(3).__getattr__("strip").__call__(var2);
         var1.getlocal(0).__setattr__("reason", var3);
         var3 = null;
         var1.setline(454);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("HTTP/1.0"));
         var3 = null;
         PyInteger var8;
         if (var10000.__nonzero__()) {
            var1.setline(455);
            var8 = Py.newInteger(10);
            var1.getlocal(0).__setattr__((String)"version", var8);
            var3 = null;
         } else {
            var1.setline(456);
            if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTP/1.")).__nonzero__()) {
               var1.setline(457);
               var8 = Py.newInteger(11);
               var1.getlocal(0).__setattr__((String)"version", var8);
               var3 = null;
            } else {
               var1.setline(458);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(PyString.fromInterned("HTTP/0.9"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(461);
                  throw Py.makeException(var1.getglobal("UnknownProtocol").__call__(var2, var1.getlocal(1)));
               }

               var1.setline(459);
               var8 = Py.newInteger(9);
               var1.getlocal(0).__setattr__((String)"version", var8);
               var3 = null;
            }
         }

         var1.setline(463);
         var3 = var1.getlocal(0).__getattr__("version");
         var10000 = var3._eq(Py.newInteger(9));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(464);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("length", var3);
            var3 = null;
            var1.setline(465);
            var8 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"chunked", var8);
            var3 = null;
            var1.setline(466);
            var8 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"will_close", var8);
            var3 = null;
            var1.setline(467);
            var3 = var1.getglobal("HTTPMessage").__call__(var2, var1.getglobal("StringIO").__call__(var2));
            var1.getlocal(0).__setattr__("msg", var3);
            var3 = null;
            var1.setline(468);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(470);
            var3 = var1.getglobal("HTTPMessage").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)Py.newInteger(0));
            var1.getlocal(0).__setattr__("msg", var3);
            var3 = null;
            var1.setline(471);
            var3 = var1.getlocal(0).__getattr__("debuglevel");
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            PyObject var7;
            if (var10000.__nonzero__()) {
               var1.setline(472);
               var3 = var1.getlocal(0).__getattr__("msg").__getattr__("headers").__iter__();

               while(true) {
                  var1.setline(472);
                  var7 = var3.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  var1.setlocal(5, var7);
                  var1.setline(473);
                  Py.printComma(PyString.fromInterned("header:"));
                  Py.printComma(var1.getlocal(5));
               }
            }

            var1.setline(476);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__getattr__("msg").__setattr__("fp", var3);
            var3 = null;
            var1.setline(479);
            var3 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("transfer-encoding"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(480);
            var10000 = var1.getlocal(6);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(6).__getattr__("lower").__call__(var2);
               var10000 = var3._eq(PyString.fromInterned("chunked"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(481);
               var8 = Py.newInteger(1);
               var1.getlocal(0).__setattr__((String)"chunked", var8);
               var3 = null;
               var1.setline(482);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("chunk_left", var3);
               var3 = null;
            } else {
               var1.setline(484);
               var8 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"chunked", var8);
               var3 = null;
            }

            var1.setline(487);
            var3 = var1.getlocal(0).__getattr__("_check_close").__call__(var2);
            var1.getlocal(0).__setattr__("will_close", var3);
            var3 = null;
            var1.setline(491);
            var3 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-length"));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(492);
            var10000 = var1.getlocal(7);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("chunked").__not__();
            }

            if (var10000.__nonzero__()) {
               label98: {
                  try {
                     var1.setline(494);
                     var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
                     var1.getlocal(0).__setattr__("length", var3);
                     var3 = null;
                  } catch (Throwable var6) {
                     PyException var9 = Py.setException(var6, var1);
                     if (var9.match(var1.getglobal("ValueError"))) {
                        var1.setline(496);
                        var7 = var1.getglobal("None");
                        var1.getlocal(0).__setattr__("length", var7);
                        var4 = null;
                        break label98;
                     }

                     throw var9;
                  }

                  var1.setline(498);
                  var7 = var1.getlocal(0).__getattr__("length");
                  var10000 = var7._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(499);
                     var7 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("length", var7);
                     var4 = null;
                  }
               }
            } else {
               var1.setline(501);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("length", var3);
               var3 = null;
            }

            var1.setline(504);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("NO_CONTENT"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("NOT_MODIFIED"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var8 = Py.newInteger(100);
                  PyObject var10001 = var1.getlocal(2);
                  PyInteger var10 = var8;
                  var3 = var10001;
                  if ((var7 = var10._le(var10001)).__nonzero__()) {
                     var7 = var3._lt(Py.newInteger(200));
                  }

                  var10000 = var7;
                  var3 = null;
                  if (!var7.__nonzero__()) {
                     var3 = var1.getlocal(0).__getattr__("_method");
                     var10000 = var3._eq(PyString.fromInterned("HEAD"));
                     var3 = null;
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(507);
               var8 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"length", var8);
               var3 = null;
            }

            var1.setline(512);
            var10000 = var1.getlocal(0).__getattr__("will_close").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("chunked").__not__();
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("length");
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(515);
               var8 = Py.newInteger(1);
               var1.getlocal(0).__setattr__((String)"will_close", var8);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _check_close$9(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      PyObject var3 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("connection"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(519);
      var3 = var1.getlocal(0).__getattr__("version");
      PyObject var10000 = var3._eq(Py.newInteger(11));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(522);
         var3 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("connection"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(523);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            PyString var6 = PyString.fromInterned("close");
            var10000 = var6._in(var1.getlocal(1).__getattr__("lower").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(524);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(525);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(531);
         if (var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("keep-alive")).__nonzero__()) {
            var1.setline(532);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(536);
            var10000 = var1.getlocal(1);
            PyString var4;
            if (var10000.__nonzero__()) {
               var4 = PyString.fromInterned("keep-alive");
               var10000 = var4._in(var1.getlocal(1).__getattr__("lower").__call__(var2));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(537);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(540);
               PyObject var5 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("proxy-connection"));
               var1.setlocal(2, var5);
               var4 = null;
               var1.setline(541);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var4 = PyString.fromInterned("keep-alive");
                  var10000 = var4._in(var1.getlocal(2).__getattr__("lower").__call__(var2));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(542);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(545);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject close$10(PyFrame var1, ThreadState var2) {
      var1.setline(548);
      PyObject var3 = var1.getlocal(0).__getattr__("fp");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(549);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(550);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("fp", var3);
         var3 = null;
         var1.setline(551);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isclosed$11(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyObject var3 = var1.getlocal(0).__getattr__("fp");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$12(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      PyObject var3 = var1.getlocal(0).__getattr__("fp");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var8;
      if (var10000.__nonzero__()) {
         var1.setline(566);
         var8 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(568);
         PyObject var4 = var1.getlocal(0).__getattr__("_method");
         var10000 = var4._eq(PyString.fromInterned("HEAD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(569);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(570);
            var8 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(572);
            if (var1.getlocal(0).__getattr__("chunked").__nonzero__()) {
               var1.setline(573);
               var3 = var1.getlocal(0).__getattr__("_read_chunked").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(575);
               var4 = var1.getlocal(1);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(589);
                  var4 = var1.getlocal(0).__getattr__("length");
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(590);
                     var4 = var1.getlocal(1);
                     var10000 = var4._gt(var1.getlocal(0).__getattr__("length"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(592);
                        var4 = var1.getlocal(0).__getattr__("length");
                        var1.setlocal(1, var4);
                        var4 = null;
                     }
                  }

                  var1.setline(597);
                  var4 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2, var1.getlocal(1));
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(598);
                  var10000 = var1.getlocal(2).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(601);
                     var1.getlocal(0).__getattr__("close").__call__(var2);
                  }

                  var1.setline(602);
                  var4 = var1.getlocal(0).__getattr__("length");
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(603);
                     var10000 = var1.getlocal(0);
                     String var11 = "length";
                     PyObject var5 = var10000;
                     PyObject var6 = var5.__getattr__(var11);
                     var6 = var6._isub(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
                     var5.__setattr__(var11, var6);
                     var1.setline(604);
                     if (var1.getlocal(0).__getattr__("length").__not__().__nonzero__()) {
                        var1.setline(605);
                        var1.getlocal(0).__getattr__("close").__call__(var2);
                     }
                  }

                  var1.setline(607);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(577);
                  var4 = var1.getlocal(0).__getattr__("length");
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(578);
                     var4 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2);
                     var1.setlocal(2, var4);
                     var4 = null;
                  } else {
                     try {
                        var1.setline(581);
                        var4 = var1.getlocal(0).__getattr__("_safe_read").__call__(var2, var1.getlocal(0).__getattr__("length"));
                        var1.setlocal(2, var4);
                        var4 = null;
                     } catch (Throwable var7) {
                        PyException var9 = Py.setException(var7, var1);
                        if (var9.match(var1.getglobal("IncompleteRead"))) {
                           var1.setline(583);
                           var1.getlocal(0).__getattr__("close").__call__(var2);
                           var1.setline(584);
                           throw Py.makeException();
                        }

                        throw var9;
                     }

                     var1.setline(585);
                     PyInteger var10 = Py.newInteger(0);
                     var1.getlocal(0).__setattr__((String)"length", var10);
                     var4 = null;
                  }

                  var1.setline(586);
                  var1.getlocal(0).__getattr__("close").__call__(var2);
                  var1.setline(587);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _read_chunked$13(PyFrame var1, ThreadState var2) {
      var1.setline(610);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("chunked");
         var10000 = var3._ne(var1.getglobal("_UNKNOWN"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(611);
      var3 = var1.getlocal(0).__getattr__("chunk_left");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(612);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(613);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(614);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(615);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(616);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._gt(var1.getglobal("_MAXLINE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(617);
               throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("chunk size")));
            }

            var1.setline(618);
            var3 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(619);
            var3 = var1.getlocal(5);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(620);
               var3 = var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
               var1.setlocal(4, var3);
               var3 = null;
            }

            try {
               var1.setline(622);
               var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(16));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var7 = Py.setException(var5, var1);
               if (var7.match(var1.getglobal("ValueError"))) {
                  var1.setline(626);
                  var1.getlocal(0).__getattr__("close").__call__(var2);
                  var1.setline(627);
                  throw Py.makeException(var1.getglobal("IncompleteRead").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3))));
               }

               throw var7;
            }

            var1.setline(628);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }
         }

         var1.setline(630);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(631);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_safe_read").__call__(var2, var1.getlocal(2)));
         } else {
            var1.setline(632);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(633);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_safe_read").__call__(var2, var1.getlocal(1)));
               var1.setline(634);
               var3 = var1.getlocal(2)._sub(var1.getlocal(1));
               var1.getlocal(0).__setattr__("chunk_left", var3);
               var3 = null;
               var1.setline(635);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(636);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(637);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_safe_read").__call__(var2, var1.getlocal(1)));
               var1.setline(638);
               var1.getlocal(0).__getattr__("_safe_read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
               var1.setline(639);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("chunk_left", var4);
               var4 = null;
               var1.setline(640);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(642);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_safe_read").__call__(var2, var1.getlocal(2)));
            var1.setline(643);
            var4 = var1.getlocal(1);
            var4 = var4._isub(var1.getlocal(2));
            var1.setlocal(1, var4);
         }

         var1.setline(646);
         var1.getlocal(0).__getattr__("_safe_read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         var1.setline(647);
         var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var4 = null;
      }

      do {
         var1.setline(651);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(652);
         var4 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(653);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var4._gt(var1.getglobal("_MAXLINE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(654);
            throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("trailer line")));
         }

         var1.setline(655);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(659);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(PyString.fromInterned("\r\n"));
         var4 = null;
      } while(!var10000.__nonzero__());

      var1.setline(663);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.setline(665);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _safe_read$14(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyString.fromInterned("Read the number of bytes requested, compensating for partial reads.\n\n        Normally, we have a blocking socket, but a read() can be interrupted\n        by a signal (resulting in a partial read).\n\n        Note that we cannot distinguish between EOF and an interrupt when zero\n        bytes have been read. IncompleteRead() will be raised in this\n        situation.\n\n        This function should be used when <amt> bytes \"should\" be present for\n        reading. If the bytes are truly not available (due to EOF), then the\n        IncompleteRead exception can be used to detect the problem.\n        ");
      var1.setline(686);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(687);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(693);
            var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(688);
         var4 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getglobal("MAXAMOUNT")));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(689);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(690);
            throw Py.makeException(var1.getglobal("IncompleteRead").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2)), var1.getlocal(1)));
         }

         var1.setline(691);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         var1.setline(692);
         var4 = var1.getlocal(1);
         var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
         var1.setlocal(1, var4);
      }
   }

   public PyObject fileno$15(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getheader$16(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(700);
         throw Py.makeException(var1.getglobal("ResponseNotReady").__call__(var2));
      } else {
         var1.setline(701);
         var3 = var1.getlocal(0).__getattr__("msg").__getattr__("getheader").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getheaders$17(PyFrame var1, ThreadState var2) {
      var1.setline(704);
      PyString.fromInterned("Return list of (header, value) tuples.");
      var1.setline(705);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(706);
         throw Py.makeException(var1.getglobal("ResponseNotReady").__call__(var2));
      } else {
         var1.setline(707);
         var3 = var1.getlocal(0).__getattr__("msg").__getattr__("items").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject HTTPConnection$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(712);
      PyInteger var3 = Py.newInteger(11);
      var1.setlocal("_http_vsn", var3);
      var3 = null;
      var1.setline(713);
      PyString var4 = PyString.fromInterned("HTTP/1.1");
      var1.setlocal("_http_vsn_str", var4);
      var3 = null;
      var1.setline(715);
      PyObject var5 = var1.getname("HTTPResponse");
      var1.setlocal("response_class", var5);
      var3 = null;
      var1.setline(716);
      var5 = var1.getname("HTTP_PORT");
      var1.setlocal("default_port", var5);
      var3 = null;
      var1.setline(717);
      var3 = Py.newInteger(1);
      var1.setlocal("auto_open", var3);
      var3 = null;
      var1.setline(718);
      var3 = Py.newInteger(0);
      var1.setlocal("debuglevel", var3);
      var3 = null;
      var1.setline(719);
      var3 = Py.newInteger(0);
      var1.setlocal("strict", var3);
      var3 = null;
      var1.setline(721);
      PyObject[] var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(742);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, set_tunnel$20, PyString.fromInterned(" Set up host and port for HTTP CONNECT tunnelling.\n\n        In a connection that uses HTTP Connect tunneling, the host passed to the\n        constructor is used as proxy server that relays all communication to the\n        endpoint passed to set_tunnel. This is done by sending a HTTP CONNECT\n        request to the proxy server when the connection is established.\n\n        This method must be called before the HTTP connection has been\n        established.\n\n        The headers argument should be a mapping of extra HTTP headers\n        to send with the CONNECT request.\n        "));
      var1.setlocal("set_tunnel", var7);
      var3 = null;
      var1.setline(766);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_hostport$21, (PyObject)null);
      var1.setlocal("_get_hostport", var7);
      var3 = null;
      var1.setline(785);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_debuglevel$22, (PyObject)null);
      var1.setlocal("set_debuglevel", var7);
      var3 = null;
      var1.setline(788);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _tunnel$23, (PyObject)null);
      var1.setlocal("_tunnel", var7);
      var3 = null;
      var1.setline(818);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, connect$24, PyString.fromInterned("Connect to the host and port specified in __init__."));
      var1.setlocal("connect", var7);
      var3 = null;
      var1.setline(826);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, close$25, PyString.fromInterned("Close the connection to the HTTP server."));
      var1.setlocal("close", var7);
      var3 = null;
      var1.setline(840);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, send$26, PyString.fromInterned("Send `data' to the server."));
      var1.setlocal("send", var7);
      var3 = null;
      var1.setline(860);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _output$27, PyString.fromInterned("Add a line of output to the current request buffer.\n\n        Assumes that the line does *not* end with \\r\\n.\n        "));
      var1.setlocal("_output", var7);
      var3 = null;
      var1.setline(867);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, _send_output$28, PyString.fromInterned("Send the currently buffered request and clear the buffer.\n\n        Appends an extra \\r\\n to the buffer.\n        A message_body may be specified, to be appended to the request.\n        "));
      var1.setlocal("_send_output", var7);
      var3 = null;
      var1.setline(888);
      var6 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var7 = new PyFunction(var1.f_globals, var6, putrequest$29, PyString.fromInterned("Send a request to the server.\n\n        `method' specifies an HTTP request method, e.g. 'GET'.\n        `url' specifies the object being requested, e.g. '/index.html'.\n        `skip_host' if True does not add automatically a 'Host:' header\n        `skip_accept_encoding' if True does not add automatically an\n           'Accept-Encoding:' header\n        "));
      var1.setlocal("putrequest", var7);
      var3 = null;
      var1.setline(1005);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, putheader$30, PyString.fromInterned("Send a request header line to the server.\n\n        For example: h.putheader('Accept', 'text/html')\n        "));
      var1.setlocal("putheader", var7);
      var3 = null;
      var1.setline(1025);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, endheaders$31, PyString.fromInterned("Indicate that the last header line has been sent to the server.\n\n        This method sends the request to the server.  The optional\n        message_body argument can be used to pass a message body\n        associated with the request.  The message body will be sent in\n        the same packet as the message headers if it is string, otherwise it is\n        sent as a separate packet.\n        "));
      var1.setlocal("endheaders", var7);
      var3 = null;
      var1.setline(1040);
      var6 = new PyObject[]{var1.getname("None"), new PyDictionary(Py.EmptyObjects)};
      var7 = new PyFunction(var1.f_globals, var6, request$32, PyString.fromInterned("Send a complete request to the server."));
      var1.setlocal("request", var7);
      var3 = null;
      var1.setline(1044);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _set_content_length$33, (PyObject)null);
      var1.setlocal("_set_content_length", var7);
      var3 = null;
      var1.setline(1067);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _send_request$34, (PyObject)null);
      var1.setlocal("_send_request", var7);
      var3 = null;
      var1.setline(1084);
      var6 = new PyObject[]{var1.getname("False")};
      var7 = new PyFunction(var1.f_globals, var6, getresponse$35, PyString.fromInterned("Get the response from the server."));
      var1.setlocal("getresponse", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyObject var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(724);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("source_address", var3);
      var3 = null;
      var1.setline(725);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(726);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_buffer", var6);
      var3 = null;
      var1.setline(727);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_HTTPConnection__response", var3);
      var3 = null;
      var1.setline(728);
      var3 = var1.getglobal("_CS_IDLE");
      var1.getlocal(0).__setattr__("_HTTPConnection__state", var3);
      var3 = null;
      var1.setline(729);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_method", var3);
      var3 = null;
      var1.setline(730);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_tunnel_host", var3);
      var3 = null;
      var1.setline(731);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_tunnel_port", var3);
      var3 = null;
      var1.setline(732);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_tunnel_headers", var7);
      var3 = null;
      var1.setline(733);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(734);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("strict", var3);
         var3 = null;
      }

      var1.setline(736);
      var3 = var1.getlocal(0).__getattr__("_get_hostport").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("host", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("port", var5);
      var5 = null;
      var3 = null;
      var1.setline(740);
      var3 = var1.getglobal("socket").__getattr__("create_connection");
      var1.getlocal(0).__setattr__("_create_connection", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_tunnel$20(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      PyString.fromInterned(" Set up host and port for HTTP CONNECT tunnelling.\n\n        In a connection that uses HTTP Connect tunneling, the host passed to the\n        constructor is used as proxy server that relays all communication to the\n        endpoint passed to set_tunnel. This is done by sending a HTTP CONNECT\n        request to the proxy server when the connection is established.\n\n        This method must be called before the HTTP connection has been\n        established.\n\n        The headers argument should be a mapping of extra HTTP headers\n        to send with the CONNECT request.\n        ");
      var1.setline(757);
      if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
         var1.setline(758);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't setup tunnel for established connection.")));
      } else {
         var1.setline(760);
         PyObject var3 = var1.getlocal(0).__getattr__("_get_hostport").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("_tunnel_host", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_tunnel_port", var5);
         var5 = null;
         var3 = null;
         var1.setline(761);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(762);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("_tunnel_headers", var3);
            var3 = null;
         } else {
            var1.setline(764);
            var1.getlocal(0).__getattr__("_tunnel_headers").__getattr__("clear").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_hostport$21(PyFrame var1, ThreadState var2) {
      var1.setline(767);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(768);
         var3 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(769);
         var3 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(770);
         var3 = var1.getlocal(3);
         var10000 = var3._gt(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(772);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (!var6.match(var1.getglobal("ValueError"))) {
                  throw var6;
               }

               var1.setline(774);
               PyObject var4 = var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned(""));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(777);
                  throw Py.makeException(var1.getglobal("InvalidURL").__call__(var2, PyString.fromInterned("nonnumeric port: '%s'")._mod(var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null))));
               }

               var1.setline(775);
               var4 = var1.getlocal(0).__getattr__("default_port");
               var1.setlocal(2, var4);
               var4 = null;
            }

            var1.setline(778);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(780);
            var3 = var1.getlocal(0).__getattr__("default_port");
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(781);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("["));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
               var10000 = var3._eq(PyString.fromInterned("]"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(782);
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(783);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject set_debuglevel$22(PyFrame var1, ThreadState var2) {
      var1.setline(786);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debuglevel", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _tunnel$23(PyFrame var1, ThreadState var2) {
      var1.setline(789);
      var1.getlocal(0).__getattr__("send").__call__(var2, PyString.fromInterned("CONNECT %s:%d HTTP/1.0\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_tunnel_host"), var1.getlocal(0).__getattr__("_tunnel_port")})));
      var1.setline(791);
      PyObject var3 = var1.getlocal(0).__getattr__("_tunnel_headers").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(791);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         if (var4 == null) {
            var1.setline(793);
            var1.getlocal(0).__getattr__("send").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
            var1.setline(794);
            PyObject var10000 = var1.getlocal(0).__getattr__("response_class");
            PyObject[] var7 = new PyObject[]{var1.getlocal(0).__getattr__("sock"), var1.getlocal(0).__getattr__("strict"), var1.getlocal(0).__getattr__("_method")};
            String[] var8 = new String[]{"strict", "method"};
            var10000 = var10000.__call__(var2, var7, var8);
            var3 = null;
            var3 = var10000;
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(796);
            var3 = var1.getlocal(3).__getattr__("_read_status").__call__(var2);
            PyObject[] var9 = Py.unpackSequence(var3, 3);
            PyObject var10 = var9[0];
            var1.setlocal(4, var10);
            var5 = null;
            var10 = var9[1];
            var1.setlocal(5, var10);
            var5 = null;
            var10 = var9[2];
            var1.setlocal(6, var10);
            var5 = null;
            var3 = null;
            var1.setline(798);
            var3 = var1.getlocal(4);
            var10000 = var3._eq(PyString.fromInterned("HTTP/0.9"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(801);
               var1.getlocal(0).__getattr__("close").__call__(var2);
               var1.setline(802);
               throw Py.makeException(var1.getglobal("socket").__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid response from tunnel request")));
            } else {
               var1.setline(803);
               var3 = var1.getlocal(5);
               var10000 = var3._ne(Py.newInteger(200));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(804);
                  var1.getlocal(0).__getattr__("close").__call__(var2);
                  var1.setline(805);
                  throw Py.makeException(var1.getglobal("socket").__getattr__("error").__call__(var2, PyString.fromInterned("Tunnel connection failed: %d %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6).__getattr__("strip").__call__(var2)}))));
               } else {
                  do {
                     var1.setline(807);
                     if (!var1.getglobal("True").__nonzero__()) {
                        break;
                     }

                     var1.setline(808);
                     var3 = var1.getlocal(3).__getattr__("fp").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(809);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                     var10000 = var3._gt(var1.getglobal("_MAXLINE"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(810);
                        throw Py.makeException(var1.getglobal("LineTooLong").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header line")));
                     }

                     var1.setline(811);
                     if (var1.getlocal(7).__not__().__nonzero__()) {
                        break;
                     }

                     var1.setline(814);
                     var3 = var1.getlocal(7);
                     var10000 = var3._eq(PyString.fromInterned("\r\n"));
                     var3 = null;
                  } while(!var10000.__nonzero__());

                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(792);
         var1.getlocal(0).__getattr__("send").__call__(var2, PyString.fromInterned("%s: %s\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      }
   }

   public PyObject connect$24(PyFrame var1, ThreadState var2) {
      var1.setline(819);
      PyString.fromInterned("Connect to the host and port specified in __init__.");
      var1.setline(820);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_connection").__call__((ThreadState)var2, new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port")}), (PyObject)var1.getlocal(0).__getattr__("timeout"), (PyObject)var1.getlocal(0).__getattr__("source_address"));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(823);
      if (var1.getlocal(0).__getattr__("_tunnel_host").__nonzero__()) {
         var1.setline(824);
         var1.getlocal(0).__getattr__("_tunnel").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$25(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyString.fromInterned("Close the connection to the HTTP server.");
      var1.setline(828);
      PyObject var3 = var1.getglobal("_CS_IDLE");
      var1.getlocal(0).__setattr__("_HTTPConnection__state", var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(830);
         var4 = var1.getlocal(0).__getattr__("sock");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(831);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(832);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("sock", var4);
            var4 = null;
            var1.setline(833);
            var1.getlocal(1).__getattr__("close").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(835);
         var4 = var1.getlocal(0).__getattr__("_HTTPConnection__response");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(836);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(837);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_HTTPConnection__response", var4);
            var4 = null;
            var1.setline(838);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         }

         throw (Throwable)var5;
      }

      var1.setline(835);
      var4 = var1.getlocal(0).__getattr__("_HTTPConnection__response");
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(836);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(837);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_HTTPConnection__response", var4);
         var4 = null;
         var1.setline(838);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send$26(PyFrame var1, ThreadState var2) {
      var1.setline(841);
      PyString.fromInterned("Send `data' to the server.");
      var1.setline(842);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(843);
         if (!var1.getlocal(0).__getattr__("auto_open").__nonzero__()) {
            var1.setline(846);
            throw Py.makeException(var1.getglobal("NotConnected").__call__(var2));
         }

         var1.setline(844);
         var1.getlocal(0).__getattr__("connect").__call__(var2);
      }

      var1.setline(848);
      var3 = var1.getlocal(0).__getattr__("debuglevel");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(849);
         Py.printComma(PyString.fromInterned("send:"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(850);
      PyInteger var4 = Py.newInteger(8192);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(851);
      var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("read"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("array")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(852);
         var3 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(852);
            Py.println(PyString.fromInterned("sendIng a read()able"));
         }

         var1.setline(853);
         var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;

         while(true) {
            var1.setline(854);
            if (!var1.getlocal(3).__nonzero__()) {
               break;
            }

            var1.setline(855);
            var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(3));
            var1.setline(856);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
         }
      } else {
         var1.setline(858);
         var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _output$27(PyFrame var1, ThreadState var2) {
      var1.setline(864);
      PyString.fromInterned("Add a line of output to the current request buffer.\n\n        Assumes that the line does *not* end with \\r\\n.\n        ");
      var1.setline(865);
      var1.getlocal(0).__getattr__("_buffer").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _send_output$28(PyFrame var1, ThreadState var2) {
      var1.setline(872);
      PyString.fromInterned("Send the currently buffered request and clear the buffer.\n\n        Appends an extra \\r\\n to the buffer.\n        A message_body may be specified, to be appended to the request.\n        ");
      var1.setline(873);
      var1.getlocal(0).__getattr__("_buffer").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})));
      var1.setline(874);
      PyObject var3 = PyString.fromInterned("\r\n").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(875);
      var1.getlocal(0).__getattr__("_buffer").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setline(879);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(880);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(1));
         var1.setlocal(2, var3);
         var1.setline(881);
         var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(882);
      var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(2));
      var1.setline(883);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(886);
         var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject putrequest$29(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyString.fromInterned("Send a request to the server.\n\n        `method' specifies an HTTP request method, e.g. 'GET'.\n        `url' specifies the object being requested, e.g. '/index.html'.\n        `skip_host' if True does not add automatically a 'Host:' header\n        `skip_accept_encoding' if True does not add automatically an\n           'Accept-Encoding:' header\n        ");
      var1.setline(899);
      PyObject var10000 = var1.getlocal(0).__getattr__("_HTTPConnection__response");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_HTTPConnection__response").__getattr__("isclosed").__call__(var2);
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(900);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_HTTPConnection__response", var3);
         var3 = null;
      }

      var1.setline(921);
      var3 = var1.getlocal(0).__getattr__("_HTTPConnection__state");
      var10000 = var3._eq(var1.getglobal("_CS_IDLE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(922);
         var3 = var1.getglobal("_CS_REQ_STARTED");
         var1.getlocal(0).__setattr__("_HTTPConnection__state", var3);
         var3 = null;
         var1.setline(927);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_method", var3);
         var3 = null;
         var1.setline(928);
         PyString var9;
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(929);
            var9 = PyString.fromInterned("/");
            var1.setlocal(2, var9);
            var3 = null;
         }

         var1.setline(930);
         var3 = PyString.fromInterned("%s %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("_http_vsn_str")}));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(932);
         var1.getlocal(0).__getattr__("_output").__call__(var2, var1.getlocal(5));
         var1.setline(934);
         var3 = var1.getlocal(0).__getattr__("_http_vsn");
         var10000 = var3._eq(Py.newInteger(11));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1003);
         } else {
            var1.setline(937);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(952);
               var9 = PyString.fromInterned("");
               var1.setlocal(6, var9);
               var3 = null;
               var1.setline(953);
               PyObject[] var4;
               if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http")).__nonzero__()) {
                  var1.setline(954);
                  var3 = var1.getglobal("urlsplit").__call__(var2, var1.getlocal(2));
                  var4 = Py.unpackSequence(var3, 5);
                  PyObject var5 = var4[0];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var4[3];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var4[4];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var3 = null;
               }

               var1.setline(956);
               PyObject var8;
               PyException var10;
               if (var1.getlocal(6).__nonzero__()) {
                  try {
                     var1.setline(958);
                     var3 = var1.getlocal(6).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
                     var1.setlocal(8, var3);
                     var3 = null;
                  } catch (Throwable var6) {
                     var10 = Py.setException(var6, var1);
                     if (!var10.match(var1.getglobal("UnicodeEncodeError"))) {
                        throw var10;
                     }

                     var1.setline(960);
                     var8 = var1.getlocal(6).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("idna"));
                     var1.setlocal(8, var8);
                     var4 = null;
                  }

                  var1.setline(961);
                  var1.getlocal(0).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(8));
               } else {
                  var1.setline(963);
                  if (var1.getlocal(0).__getattr__("_tunnel_host").__nonzero__()) {
                     var1.setline(964);
                     var3 = var1.getlocal(0).__getattr__("_tunnel_host");
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(965);
                     var3 = var1.getlocal(0).__getattr__("_tunnel_port");
                     var1.setlocal(10, var3);
                     var3 = null;
                  } else {
                     var1.setline(967);
                     var3 = var1.getlocal(0).__getattr__("host");
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(968);
                     var3 = var1.getlocal(0).__getattr__("port");
                     var1.setlocal(10, var3);
                     var3 = null;
                  }

                  try {
                     var1.setline(971);
                     var3 = var1.getlocal(9).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
                     var1.setlocal(11, var3);
                     var3 = null;
                  } catch (Throwable var7) {
                     var10 = Py.setException(var7, var1);
                     if (!var10.match(var1.getglobal("UnicodeEncodeError"))) {
                        throw var10;
                     }

                     var1.setline(973);
                     var8 = var1.getlocal(9).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("idna"));
                     var1.setlocal(11, var8);
                     var4 = null;
                  }

                  var1.setline(975);
                  var3 = var1.getlocal(11).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
                  var10000 = var3._ge(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(976);
                     var3 = PyString.fromInterned("[")._add(var1.getlocal(11))._add(PyString.fromInterned("]"));
                     var1.setlocal(11, var3);
                     var3 = null;
                  }

                  var1.setline(977);
                  var3 = var1.getlocal(10);
                  var10000 = var3._eq(var1.getlocal(0).__getattr__("default_port"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(978);
                     var1.getlocal(0).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(11));
                  } else {
                     var1.setline(980);
                     var1.getlocal(0).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(10)})));
                  }
               }
            }

            var1.setline(990);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(991);
               var1.getlocal(0).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Accept-Encoding"), (PyObject)PyString.fromInterned("identity"));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(924);
         throw Py.makeException(var1.getglobal("CannotSendRequest").__call__(var2));
      }
   }

   public PyObject putheader$30(PyFrame var1, ThreadState var2) {
      var1.setline(1009);
      PyString.fromInterned("Send a request header line to the server.\n\n        For example: h.putheader('Accept', 'text/html')\n        ");
      var1.setline(1010);
      PyObject var3 = var1.getlocal(0).__getattr__("_HTTPConnection__state");
      PyObject var10000 = var3._ne(var1.getglobal("_CS_REQ_STARTED"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1011);
         throw Py.makeException(var1.getglobal("CannotSendHeader").__call__(var2));
      } else {
         var1.setline(1013);
         var3 = PyString.fromInterned("%s")._mod(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1014);
         if (var1.getglobal("_is_legal_header_name").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(1015);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid header name %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
         } else {
            var1.setline(1017);
            PyList var6 = new PyList();
            var3 = var6.__getattr__("append");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1017);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(1017);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1017);
                  var1.dellocal(3);
                  PyList var5 = var6;
                  var1.setlocal(2, var5);
                  var3 = null;
                  var1.setline(1018);
                  var3 = var1.getlocal(2).__iter__();

                  do {
                     var1.setline(1018);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(1022);
                        var3 = PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("\r\n\t").__getattr__("join").__call__(var2, var1.getlocal(2))}));
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(1023);
                        var1.getlocal(0).__getattr__("_output").__call__(var2, var1.getlocal(6));
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(1019);
                  } while(!var1.getglobal("_is_illegal_header_value").__call__(var2, var1.getlocal(5)).__nonzero__());

                  var1.setline(1020);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid header value %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)}))));
               }

               var1.setlocal(4, var4);
               var1.setline(1017);
               var1.getlocal(3).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4)));
            }
         }
      }
   }

   public PyObject endheaders$31(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      PyString.fromInterned("Indicate that the last header line has been sent to the server.\n\n        This method sends the request to the server.  The optional\n        message_body argument can be used to pass a message body\n        associated with the request.  The message body will be sent in\n        the same packet as the message headers if it is string, otherwise it is\n        sent as a separate packet.\n        ");
      var1.setline(1034);
      PyObject var3 = var1.getlocal(0).__getattr__("_HTTPConnection__state");
      PyObject var10000 = var3._eq(var1.getglobal("_CS_REQ_STARTED"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1035);
         var3 = var1.getglobal("_CS_REQ_SENT");
         var1.getlocal(0).__setattr__("_HTTPConnection__state", var3);
         var3 = null;
         var1.setline(1038);
         var1.getlocal(0).__getattr__("_send_output").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1037);
         throw Py.makeException(var1.getglobal("CannotSendHeader").__call__(var2));
      }
   }

   public PyObject request$32(PyFrame var1, ThreadState var2) {
      var1.setline(1041);
      PyString.fromInterned("Send a complete request to the server.");
      var1.setline(1042);
      var1.getlocal(0).__getattr__("_send_request").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_content_length$33(PyFrame var1, ThreadState var2) {
      var1.setline(1049);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1050);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getattr__("upper").__call__(var2);
         var10000 = var3._in(var1.getglobal("_METHODS_EXPECTING_BODY"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1051);
         PyString var10 = PyString.fromInterned("0");
         var1.setlocal(3, var10);
         var3 = null;
      } else {
         var1.setline(1052);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(1054);
               var3 = var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var1.setlocal(3, var3);
               var3 = null;
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (!var9.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
                  throw var9;
               }

               PyException var4;
               try {
                  var1.setline(1059);
                  PyObject var8 = var1.getglobal("str").__call__(var2, var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(1).__getattr__("fileno").__call__(var2)).__getattr__("st_size"));
                  var1.setlocal(3, var8);
                  var4 = null;
               } catch (Throwable var6) {
                  var4 = Py.setException(var6, var1);
                  if (!var4.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("OSError")}))) {
                     throw var4;
                  }

                  var1.setline(1062);
                  PyObject var5 = var1.getlocal(0).__getattr__("debuglevel");
                  var10000 = var5._gt(Py.newInteger(0));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1062);
                     Py.println(PyString.fromInterned("Cannot stat!!"));
                  }
               }
            }
         }
      }

      var1.setline(1064);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1065);
         var1.getlocal(0).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _send_request$34(PyFrame var1, ThreadState var2) {
      var1.setline(1069);
      PyObject var10000 = var1.getglobal("dict").__getattr__("fromkeys");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1069);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(1069);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1069);
            var1.dellocal(6);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1070);
            PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var8);
            var3 = null;
            var1.setline(1071);
            PyString var9 = PyString.fromInterned("host");
            var10000 = var9._in(var1.getlocal(5));
            var3 = null;
            PyInteger var10;
            if (var10000.__nonzero__()) {
               var1.setline(1072);
               var10 = Py.newInteger(1);
               var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("skip_host"), var10);
               var3 = null;
            }

            var1.setline(1073);
            var9 = PyString.fromInterned("accept-encoding");
            var10000 = var9._in(var1.getlocal(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1074);
               var10 = Py.newInteger(1);
               var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("skip_accept_encoding"), var10);
               var3 = null;
            }

            var1.setline(1076);
            var10000 = var1.getlocal(0).__getattr__("putrequest");
            PyObject[] var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
            String[] var7 = new String[0];
            var10000._callextra(var11, var7, (PyObject)null, var1.getlocal(8));
            var3 = null;
            var1.setline(1078);
            var9 = PyString.fromInterned("content-length");
            var10000 = var9._notin(var1.getlocal(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1079);
               var1.getlocal(0).__getattr__("_set_content_length").__call__(var2, var1.getlocal(3), var1.getlocal(1));
            }

            var1.setline(1080);
            var3 = var1.getlocal(4).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(1080);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1082);
                  var1.getlocal(0).__getattr__("endheaders").__call__(var2, var1.getlocal(3));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(1081);
               var1.getlocal(0).__getattr__("putheader").__call__(var2, var1.getlocal(9), var1.getlocal(10));
            }
         }

         var1.setlocal(7, var4);
         var1.setline(1069);
         var1.getlocal(6).__call__(var2, var1.getlocal(7).__getattr__("lower").__call__(var2));
      }
   }

   public PyObject getresponse$35(PyFrame var1, ThreadState var2) {
      var1.setline(1085);
      PyString.fromInterned("Get the response from the server.");
      var1.setline(1088);
      PyObject var10000 = var1.getlocal(0).__getattr__("_HTTPConnection__response");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_HTTPConnection__response").__getattr__("isclosed").__call__(var2);
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(1089);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_HTTPConnection__response", var3);
         var3 = null;
      }

      var1.setline(1107);
      var3 = var1.getlocal(0).__getattr__("_HTTPConnection__state");
      var10000 = var3._ne(var1.getglobal("_CS_REQ_SENT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_HTTPConnection__response");
      }

      if (var10000.__nonzero__()) {
         var1.setline(1108);
         throw Py.makeException(var1.getglobal("ResponseNotReady").__call__(var2));
      } else {
         var1.setline(1110);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("sock")});
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(1111);
         PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("strict"), var1.getlocal(0).__getattr__("strict"), PyString.fromInterned("method"), var1.getlocal(0).__getattr__("_method")});
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(1112);
         var3 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1113);
            var3 = var1.getlocal(2);
            var3 = var3._iadd(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("debuglevel")}));
            var1.setlocal(2, var3);
         }

         var1.setline(1114);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1117);
            var3 = var1.getglobal("True");
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("buffering"), var3);
            var3 = null;
         }

         var1.setline(1118);
         var10000 = var1.getlocal(0).__getattr__("response_class");
         PyObject[] var8 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var8, var4, var1.getlocal(2), var1.getlocal(3));
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;

         try {
            var1.setline(1121);
            var1.getlocal(4).__getattr__("begin").__call__(var2);
            var1.setline(1122);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(4).__getattr__("will_close");
               var10000 = var3._ne(var1.getglobal("_UNKNOWN"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1123);
            var3 = var1.getglobal("_CS_IDLE");
            var1.getlocal(0).__setattr__("_HTTPConnection__state", var3);
            var3 = null;
            var1.setline(1125);
            if (var1.getlocal(4).__getattr__("will_close").__nonzero__()) {
               var1.setline(1127);
               var1.getlocal(0).__getattr__("close").__call__(var2);
            } else {
               var1.setline(1130);
               var3 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("_HTTPConnection__response", var3);
               var3 = null;
            }

            var1.setline(1132);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            Py.setException(var5, var1);
            var1.setline(1134);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            var1.setline(1135);
            throw Py.makeException();
         }
      }
   }

   public PyObject HTTP$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Compatibility class with httplib.py from 1.5."));
      var1.setline(1139);
      PyString.fromInterned("Compatibility class with httplib.py from 1.5.");
      var1.setline(1141);
      PyInteger var3 = Py.newInteger(10);
      var1.setlocal("_http_vsn", var3);
      var3 = null;
      var1.setline(1142);
      PyString var4 = PyString.fromInterned("HTTP/1.0");
      var1.setlocal("_http_vsn_str", var4);
      var3 = null;
      var1.setline(1144);
      var3 = Py.newInteger(0);
      var1.setlocal("debuglevel", var3);
      var3 = null;
      var1.setline(1146);
      PyObject var5 = var1.getname("HTTPConnection");
      var1.setlocal("_connection_class", var5);
      var3 = null;
      var1.setline(1148);
      PyObject[] var6 = new PyObject[]{PyString.fromInterned(""), var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$37, PyString.fromInterned("Provide a default host, since the superclass requires one."));
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(1160);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _setup$38, (PyObject)null);
      var1.setlocal("_setup", var7);
      var3 = null;
      var1.setline(1175);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, connect$39, PyString.fromInterned("Accept arguments to set the host/port, since the superclass doesn't."));
      var1.setlocal("connect", var7);
      var3 = null;
      var1.setline(1182);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getfile$40, PyString.fromInterned("Provide a getfile, since the superclass' does not use this concept."));
      var1.setlocal("getfile", var7);
      var3 = null;
      var1.setline(1186);
      var6 = new PyObject[]{var1.getname("False")};
      var7 = new PyFunction(var1.f_globals, var6, getreply$41, PyString.fromInterned("Compat definition since superclass does not define it.\n\n        Returns a tuple consisting of:\n        - server status code (e.g. '200' if all goes well)\n        - server \"reason\" corresponding to status code\n        - any RFC822 headers in the response from the server\n        "));
      var1.setlocal("getreply", var7);
      var3 = null;
      var1.setline(1219);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, close$42, (PyObject)null);
      var1.setlocal("close", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$37(PyFrame var1, ThreadState var2) {
      var1.setline(1149);
      PyString.fromInterned("Provide a default host, since the superclass requires one.");
      var1.setline(1152);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1153);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1158);
      var1.getlocal(0).__getattr__("_setup").__call__(var2, var1.getlocal(0).__getattr__("_connection_class").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setup$38(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_conn", var3);
      var3 = null;
      var1.setline(1164);
      var3 = var1.getlocal(1).__getattr__("send");
      var1.getlocal(0).__setattr__("send", var3);
      var3 = null;
      var1.setline(1165);
      var3 = var1.getlocal(1).__getattr__("putrequest");
      var1.getlocal(0).__setattr__("putrequest", var3);
      var3 = null;
      var1.setline(1166);
      var3 = var1.getlocal(1).__getattr__("putheader");
      var1.getlocal(0).__setattr__("putheader", var3);
      var3 = null;
      var1.setline(1167);
      var3 = var1.getlocal(1).__getattr__("endheaders");
      var1.getlocal(0).__setattr__("endheaders", var3);
      var3 = null;
      var1.setline(1168);
      var3 = var1.getlocal(1).__getattr__("set_debuglevel");
      var1.getlocal(0).__setattr__("set_debuglevel", var3);
      var3 = null;
      var1.setline(1170);
      var3 = var1.getlocal(0).__getattr__("_http_vsn");
      var1.getlocal(1).__setattr__("_http_vsn", var3);
      var3 = null;
      var1.setline(1171);
      var3 = var1.getlocal(0).__getattr__("_http_vsn_str");
      var1.getlocal(1).__setattr__("_http_vsn_str", var3);
      var3 = null;
      var1.setline(1173);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$39(PyFrame var1, ThreadState var2) {
      var1.setline(1176);
      PyString.fromInterned("Accept arguments to set the host/port, since the superclass doesn't.");
      var1.setline(1178);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1179);
         var3 = var1.getlocal(0).__getattr__("_conn").__getattr__("_get_hostport").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__getattr__("_conn").__setattr__("host", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__getattr__("_conn").__setattr__("port", var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(1180);
      var1.getlocal(0).__getattr__("_conn").__getattr__("connect").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getfile$40(PyFrame var1, ThreadState var2) {
      var1.setline(1183);
      PyString.fromInterned("Provide a getfile, since the superclass' does not use this concept.");
      var1.setline(1184);
      PyObject var3 = var1.getlocal(0).__getattr__("file");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getreply$41(PyFrame var1, ThreadState var2) {
      var1.setline(1193);
      PyString.fromInterned("Compat definition since superclass does not define it.\n\n        Returns a tuple consisting of:\n        - server status code (e.g. '200' if all goes well)\n        - server \"reason\" corresponding to status code\n        - any RFC822 headers in the response from the server\n        ");

      PyException var3;
      PyObject var6;
      PyTuple var7;
      try {
         var1.setline(1195);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(1196);
            var6 = var1.getlocal(0).__getattr__("_conn").__getattr__("getresponse").__call__(var2);
            var1.setlocal(2, var6);
            var3 = null;
         } else {
            var1.setline(1200);
            var6 = var1.getlocal(0).__getattr__("_conn").__getattr__("getresponse").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var6);
            var3 = null;
         }
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("BadStatusLine"))) {
            PyObject var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1207);
            var4 = var1.getlocal(0).__getattr__("_conn").__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"), (PyObject)Py.newInteger(0));
            var1.getlocal(0).__setattr__("file", var4);
            var4 = null;
            var1.setline(1210);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(1212);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("headers", var4);
            var4 = null;
            var1.setline(1213);
            var7 = new PyTuple(new PyObject[]{Py.newInteger(-1), var1.getlocal(3).__getattr__("line"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var7;
         }

         throw var3;
      }

      var1.setline(1215);
      var6 = var1.getlocal(2).__getattr__("msg");
      var1.getlocal(0).__setattr__("headers", var6);
      var3 = null;
      var1.setline(1216);
      var6 = var1.getlocal(2).__getattr__("fp");
      var1.getlocal(0).__setattr__("file", var6);
      var3 = null;
      var1.setline(1217);
      var7 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("status"), var1.getlocal(2).__getattr__("reason"), var1.getlocal(2).__getattr__("msg")});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject close$42(PyFrame var1, ThreadState var2) {
      var1.setline(1220);
      var1.getlocal(0).__getattr__("_conn").__getattr__("close").__call__(var2);
      var1.setline(1227);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTTPSConnection$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class allows communication via SSL."));
      var1.setline(1235);
      PyString.fromInterned("This class allows communication via SSL.");
      var1.setline(1237);
      PyObject var3 = var1.getname("HTTPS_PORT");
      var1.setlocal("default_port", var3);
      var3 = null;
      var1.setline(1239);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$44, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1252);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, connect$45, PyString.fromInterned("Connect to a host on a given (SSL) port."));
      var1.setlocal("connect", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$44(PyFrame var1, ThreadState var2) {
      var1.setline(1242);
      PyObject var10000 = var1.getglobal("HTTPConnection").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
      var10000.__call__(var2, var3);
      var1.setline(1244);
      PyObject var4 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("key_file", var4);
      var3 = null;
      var1.setline(1245);
      var4 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("cert_file", var4);
      var3 = null;
      var1.setline(1246);
      var4 = var1.getlocal(8);
      var10000 = var4._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1247);
         var4 = var1.getglobal("ssl").__getattr__("_create_default_https_context").__call__(var2);
         var1.setlocal(8, var4);
         var3 = null;
      }

      var1.setline(1248);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
      }

      if (var10000.__nonzero__()) {
         var1.setline(1249);
         var1.getlocal(8).__getattr__("load_cert_chain").__call__(var2, var1.getlocal(4), var1.getlocal(3));
      }

      var1.setline(1250);
      var4 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("_context", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$45(PyFrame var1, ThreadState var2) {
      var1.setline(1253);
      PyString.fromInterned("Connect to a host on a given (SSL) port.");
      var1.setline(1255);
      var1.getglobal("HTTPConnection").__getattr__("connect").__call__(var2, var1.getlocal(0));
      var1.setline(1257);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_tunnel_host").__nonzero__()) {
         var1.setline(1258);
         var3 = var1.getlocal(0).__getattr__("_tunnel_host");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1260);
         var3 = var1.getlocal(0).__getattr__("host");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1262);
      PyObject var10000 = var1.getlocal(0).__getattr__("_context").__getattr__("wrap_socket");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("sock"), var1.getlocal(1)};
      String[] var4 = new String[]{"server_hostname"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTTPS$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Compatibility with 1.5 httplib interface\n\n        Python 1.5.2 did not have an HTTPS class, but it defined an\n        interface for sending http requests that is also useful for\n        https.\n        "));
      var1.setline(1273);
      PyString.fromInterned("Compatibility with 1.5 httplib interface\n\n        Python 1.5.2 did not have an HTTPS class, but it defined an\n        interface for sending http requests that is also useful for\n        https.\n        ");
      var1.setline(1275);
      PyObject var3 = var1.getname("HTTPSConnection");
      var1.setlocal("_connection_class", var3);
      var3 = null;
      var1.setline(1277);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned(""), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(1282);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1283);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1284);
      var10000 = var1.getlocal(0).__getattr__("_setup");
      PyObject var10002 = var1.getlocal(0).__getattr__("_connection_class");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      String[] var4 = new String[]{"context"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1290);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("key_file", var3);
      var3 = null;
      var1.setline(1291);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("cert_file", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeSocket$48(PyFrame var1, ThreadState var2) {
      var1.setline(1295);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("FakeSocket is deprecated, and won't be in 3.x.  ")._add(PyString.fromInterned("Use the result of ssl.wrap_socket() directly instead.")), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1298);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject HTTPException$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1304);
      return var1.getf_locals();
   }

   public PyObject NotConnected$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1307);
      return var1.getf_locals();
   }

   public PyObject InvalidURL$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1310);
      return var1.getf_locals();
   }

   public PyObject UnknownProtocol$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1313);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$53, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$53(PyFrame var1, ThreadState var2) {
      var1.setline(1314);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var3);
      var3 = null;
      var1.setline(1315);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("version", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UnknownTransferEncoding$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1318);
      return var1.getf_locals();
   }

   public PyObject UnimplementedFileMode$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1321);
      return var1.getf_locals();
   }

   public PyObject IncompleteRead$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1324);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$57, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$58, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(1334);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$59, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$57(PyFrame var1, ThreadState var2) {
      var1.setline(1325);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var3);
      var3 = null;
      var1.setline(1326);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("partial", var4);
      var3 = null;
      var1.setline(1327);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("expected", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$58(PyFrame var1, ThreadState var2) {
      var1.setline(1329);
      PyObject var3 = var1.getlocal(0).__getattr__("expected");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1330);
         var3 = PyString.fromInterned(", %i more expected")._mod(var1.getlocal(0).__getattr__("expected"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1332);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1333);
      var3 = PyString.fromInterned("IncompleteRead(%i bytes read%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("partial")), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$59(PyFrame var1, ThreadState var2) {
      var1.setline(1335);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ImproperConnectionState$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1338);
      return var1.getf_locals();
   }

   public PyObject CannotSendRequest$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1341);
      return var1.getf_locals();
   }

   public PyObject CannotSendHeader$62(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1344);
      return var1.getf_locals();
   }

   public PyObject ResponseNotReady$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1347);
      return var1.getf_locals();
   }

   public PyObject BadStatusLine$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1350);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$65, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$65(PyFrame var1, ThreadState var2) {
      var1.setline(1351);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1352);
         var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1353);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.setline(1354);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("line", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LineTooLong$66(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1357);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$67, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$67(PyFrame var1, ThreadState var2) {
      var1.setline(1358);
      var1.getglobal("HTTPException").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("got more than %d bytes when reading %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("_MAXLINE"), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LineAndFileWrapper$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A limited file-like object for HTTP/0.9 responses."));
      var1.setline(1365);
      PyString.fromInterned("A limited file-like object for HTTP/0.9 responses.");
      var1.setline(1372);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$69, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1379);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$70, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(1382);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _done$71, (PyObject)null);
      var1.setlocal("_done", var4);
      var3 = null;
      var1.setline(1391);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$72, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1413);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$73, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(1421);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, readlines$74, (PyObject)null);
      var1.setlocal("readlines", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(1373);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_line", var3);
      var3 = null;
      var1.setline(1374);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_file", var3);
      var3 = null;
      var1.setline(1375);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_line_consumed", var4);
      var3 = null;
      var1.setline(1376);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_line_offset", var4);
      var3 = null;
      var1.setline(1377);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_line_left", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$70(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _done$71(PyFrame var1, ThreadState var2) {
      var1.setline(1386);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_line_consumed", var3);
      var3 = null;
      var1.setline(1387);
      PyObject var4 = var1.getlocal(0).__getattr__("_file").__getattr__("read");
      var1.getlocal(0).__setattr__("read", var4);
      var3 = null;
      var1.setline(1388);
      var4 = var1.getlocal(0).__getattr__("_file").__getattr__("readline");
      var1.getlocal(0).__setattr__("readline", var4);
      var3 = null;
      var1.setline(1389);
      var4 = var1.getlocal(0).__getattr__("_file").__getattr__("readlines");
      var1.getlocal(0).__setattr__("readlines", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$72(PyFrame var1, ThreadState var2) {
      var1.setline(1392);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_line_consumed").__nonzero__()) {
         var1.setline(1393);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1394);
         PyObject var10000;
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("_line_left").__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(1395);
            PyObject var4 = var1.getlocal(1);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(1);
               var10000 = var4._gt(var1.getlocal(0).__getattr__("_line_left"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1396);
               var4 = var1.getlocal(0).__getattr__("_line").__getslice__(var1.getlocal(0).__getattr__("_line_offset"), (PyObject)null, (PyObject)null);
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1397);
               var1.getlocal(0).__getattr__("_done").__call__(var2);
               var1.setline(1398);
               var4 = var1.getlocal(1);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1399);
                  var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1401);
                  var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)))));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1403);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var4 = var1.getlocal(1);
                  var10000 = var4._le(var1.getlocal(0).__getattr__("_line_left"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(1404);
               var4 = var1.getlocal(0).__getattr__("_line_offset");
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(1405);
               var4 = var1.getlocal(3)._add(var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1406);
               var4 = var1.getlocal(0).__getattr__("_line").__getslice__(var1.getlocal(3), var1.getlocal(4), (PyObject)null);
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1407);
               var4 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("_line_offset", var4);
               var4 = null;
               var1.setline(1408);
               var10000 = var1.getlocal(0);
               String var7 = "_line_left";
               PyObject var5 = var10000;
               PyObject var6 = var5.__getattr__(var7);
               var6 = var6._isub(var1.getlocal(1));
               var5.__setattr__(var7, var6);
               var1.setline(1409);
               var4 = var1.getlocal(0).__getattr__("_line_left");
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1410);
                  var1.getlocal(0).__getattr__("_done").__call__(var2);
               }

               var1.setline(1411);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject readline$73(PyFrame var1, ThreadState var2) {
      var1.setline(1414);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_line_consumed").__nonzero__()) {
         var1.setline(1415);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1416);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("_line_left").__nonzero__()) {
            PyObject var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(1417);
            PyObject var4 = var1.getlocal(0).__getattr__("_line").__getslice__(var1.getlocal(0).__getattr__("_line_offset"), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1418);
            var1.getlocal(0).__getattr__("_done").__call__(var2);
            var1.setline(1419);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject readlines$74(PyFrame var1, ThreadState var2) {
      var1.setline(1422);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_line_consumed").__nonzero__()) {
         var1.setline(1423);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readlines").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1424);
         PyObject var10000;
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("_line_left").__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(1425);
            PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_line").__getslice__(var1.getlocal(0).__getattr__("_line_offset"), (PyObject)null, (PyObject)null)});
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1426);
            var1.getlocal(0).__getattr__("_done").__call__(var2);
            var1.setline(1427);
            PyObject var5 = var1.getlocal(1);
            var10000 = var5._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1428);
               var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_file").__getattr__("readlines").__call__(var2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1430);
               var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_file").__getattr__("readlines").__call__(var2, var1.getlocal(1)));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public httplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HTTPMessage$1 = Py.newCode(0, var2, var1, "HTTPMessage", 255, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value", "prev", "combined"};
      addheader$2 = Py.newCode(3, var2, var1, "addheader", 257, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "more", "prev"};
      addcontinue$3 = Py.newCode(3, var2, var1, "addcontinue", 266, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hlist", "headerseen", "firstline", "tell", "line"};
      readheaders$4 = Py.newCode(1, var2, var1, "readheaders", 271, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPResponse$5 = Py.newCode(0, var2, var1, "HTTPResponse", 354, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "debuglevel", "strict", "method", "buffering"};
      __init__$6 = Py.newCode(6, var2, var1, "__init__", 364, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "version", "status", "reason"};
      _read_status$7 = Py.newCode(1, var2, var1, "_read_status", 392, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "version", "status", "reason", "skip", "hdr", "tr_enc", "length"};
      begin$8 = Py.newCode(1, var2, var1, "begin", 431, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "conn", "pconn"};
      _check_close$9 = Py.newCode(1, var2, var1, "_check_close", 517, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp"};
      close$10 = Py.newCode(1, var2, var1, "close", 547, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isclosed$11 = Py.newCode(1, var2, var1, "isclosed", 553, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amt", "s"};
      read$12 = Py.newCode(2, var2, var1, "read", 564, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amt", "chunk_left", "value", "line", "i"};
      _read_chunked$13 = Py.newCode(2, var2, var1, "_read_chunked", 609, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amt", "s", "chunk"};
      _safe_read$14 = Py.newCode(2, var2, var1, "_safe_read", 667, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$15 = Py.newCode(1, var2, var1, "fileno", 695, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "default"};
      getheader$16 = Py.newCode(3, var2, var1, "getheader", 698, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getheaders$17 = Py.newCode(1, var2, var1, "getheaders", 703, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPConnection$18 = Py.newCode(0, var2, var1, "HTTPConnection", 710, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "strict", "timeout", "source_address"};
      __init__$19 = Py.newCode(6, var2, var1, "__init__", 721, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "headers"};
      set_tunnel$20 = Py.newCode(4, var2, var1, "set_tunnel", 742, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "i", "j"};
      _get_hostport$21 = Py.newCode(3, var2, var1, "_get_hostport", 766, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      set_debuglevel$22 = Py.newCode(2, var2, var1, "set_debuglevel", 785, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "value", "response", "version", "code", "message", "line"};
      _tunnel$23 = Py.newCode(1, var2, var1, "_tunnel", 788, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      connect$24 = Py.newCode(1, var2, var1, "connect", 818, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "response"};
      close$25 = Py.newCode(1, var2, var1, "close", 826, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "blocksize", "datablock"};
      send$26 = Py.newCode(2, var2, var1, "send", 840, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      _output$27 = Py.newCode(2, var2, var1, "_output", 860, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_body", "msg"};
      _send_output$28 = Py.newCode(2, var2, var1, "_send_output", 867, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method", "url", "skip_host", "skip_accept_encoding", "hdr", "netloc", "nil", "netloc_enc", "host", "port", "host_enc"};
      putrequest$29 = Py.newCode(5, var2, var1, "putrequest", 888, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "values", "_[1017_18]", "v", "one_value", "hdr"};
      putheader$30 = Py.newCode(3, var2, var1, "putheader", 1005, true, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_body"};
      endheaders$31 = Py.newCode(2, var2, var1, "endheaders", 1025, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method", "url", "body", "headers"};
      request$32 = Py.newCode(5, var2, var1, "request", 1040, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "method", "thelen"};
      _set_content_length$33 = Py.newCode(3, var2, var1, "_set_content_length", 1044, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method", "url", "body", "headers", "header_names", "_[1069_38]", "k", "skips", "hdr", "value"};
      _send_request$34 = Py.newCode(5, var2, var1, "_send_request", 1067, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffering", "args", "kwds", "response"};
      getresponse$35 = Py.newCode(2, var2, var1, "getresponse", 1084, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTP$36 = Py.newCode(0, var2, var1, "HTTP", 1138, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "strict"};
      __init__$37 = Py.newCode(4, var2, var1, "__init__", 1148, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "conn"};
      _setup$38 = Py.newCode(2, var2, var1, "_setup", 1160, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      connect$39 = Py.newCode(3, var2, var1, "connect", 1175, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getfile$40 = Py.newCode(1, var2, var1, "getfile", 1182, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffering", "response", "e"};
      getreply$41 = Py.newCode(2, var2, var1, "getreply", 1186, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$42 = Py.newCode(1, var2, var1, "close", 1219, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPSConnection$43 = Py.newCode(0, var2, var1, "HTTPSConnection", 1234, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "key_file", "cert_file", "strict", "timeout", "source_address", "context"};
      __init__$44 = Py.newCode(9, var2, var1, "__init__", 1239, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_hostname"};
      connect$45 = Py.newCode(1, var2, var1, "connect", 1252, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPS$46 = Py.newCode(0, var2, var1, "HTTPS", 1267, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "key_file", "cert_file", "strict", "context"};
      __init__$47 = Py.newCode(7, var2, var1, "__init__", 1277, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sock", "sslobj"};
      FakeSocket$48 = Py.newCode(2, var2, var1, "FakeSocket", 1294, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPException$49 = Py.newCode(0, var2, var1, "HTTPException", 1301, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotConnected$50 = Py.newCode(0, var2, var1, "NotConnected", 1306, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidURL$51 = Py.newCode(0, var2, var1, "InvalidURL", 1309, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnknownProtocol$52 = Py.newCode(0, var2, var1, "UnknownProtocol", 1312, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version"};
      __init__$53 = Py.newCode(2, var2, var1, "__init__", 1313, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnknownTransferEncoding$54 = Py.newCode(0, var2, var1, "UnknownTransferEncoding", 1317, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnimplementedFileMode$55 = Py.newCode(0, var2, var1, "UnimplementedFileMode", 1320, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IncompleteRead$56 = Py.newCode(0, var2, var1, "IncompleteRead", 1323, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "partial", "expected"};
      __init__$57 = Py.newCode(3, var2, var1, "__init__", 1324, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "e"};
      __repr__$58 = Py.newCode(1, var2, var1, "__repr__", 1328, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$59 = Py.newCode(1, var2, var1, "__str__", 1334, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ImproperConnectionState$60 = Py.newCode(0, var2, var1, "ImproperConnectionState", 1337, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CannotSendRequest$61 = Py.newCode(0, var2, var1, "CannotSendRequest", 1340, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CannotSendHeader$62 = Py.newCode(0, var2, var1, "CannotSendHeader", 1343, false, false, self, 62, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ResponseNotReady$63 = Py.newCode(0, var2, var1, "ResponseNotReady", 1346, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BadStatusLine$64 = Py.newCode(0, var2, var1, "BadStatusLine", 1349, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line"};
      __init__$65 = Py.newCode(2, var2, var1, "__init__", 1350, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LineTooLong$66 = Py.newCode(0, var2, var1, "LineTooLong", 1356, false, false, self, 66, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line_type"};
      __init__$67 = Py.newCode(2, var2, var1, "__init__", 1357, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LineAndFileWrapper$68 = Py.newCode(0, var2, var1, "LineAndFileWrapper", 1364, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line", "file"};
      __init__$69 = Py.newCode(3, var2, var1, "__init__", 1372, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$70 = Py.newCode(2, var2, var1, "__getattr__", 1379, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _done$71 = Py.newCode(1, var2, var1, "_done", 1382, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amt", "s", "i", "j"};
      read$72 = Py.newCode(2, var2, var1, "read", 1391, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      readline$73 = Py.newCode(1, var2, var1, "readline", 1413, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "L"};
      readlines$74 = Py.newCode(2, var2, var1, "readlines", 1421, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new httplib$py("httplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(httplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.HTTPMessage$1(var2, var3);
         case 2:
            return this.addheader$2(var2, var3);
         case 3:
            return this.addcontinue$3(var2, var3);
         case 4:
            return this.readheaders$4(var2, var3);
         case 5:
            return this.HTTPResponse$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this._read_status$7(var2, var3);
         case 8:
            return this.begin$8(var2, var3);
         case 9:
            return this._check_close$9(var2, var3);
         case 10:
            return this.close$10(var2, var3);
         case 11:
            return this.isclosed$11(var2, var3);
         case 12:
            return this.read$12(var2, var3);
         case 13:
            return this._read_chunked$13(var2, var3);
         case 14:
            return this._safe_read$14(var2, var3);
         case 15:
            return this.fileno$15(var2, var3);
         case 16:
            return this.getheader$16(var2, var3);
         case 17:
            return this.getheaders$17(var2, var3);
         case 18:
            return this.HTTPConnection$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.set_tunnel$20(var2, var3);
         case 21:
            return this._get_hostport$21(var2, var3);
         case 22:
            return this.set_debuglevel$22(var2, var3);
         case 23:
            return this._tunnel$23(var2, var3);
         case 24:
            return this.connect$24(var2, var3);
         case 25:
            return this.close$25(var2, var3);
         case 26:
            return this.send$26(var2, var3);
         case 27:
            return this._output$27(var2, var3);
         case 28:
            return this._send_output$28(var2, var3);
         case 29:
            return this.putrequest$29(var2, var3);
         case 30:
            return this.putheader$30(var2, var3);
         case 31:
            return this.endheaders$31(var2, var3);
         case 32:
            return this.request$32(var2, var3);
         case 33:
            return this._set_content_length$33(var2, var3);
         case 34:
            return this._send_request$34(var2, var3);
         case 35:
            return this.getresponse$35(var2, var3);
         case 36:
            return this.HTTP$36(var2, var3);
         case 37:
            return this.__init__$37(var2, var3);
         case 38:
            return this._setup$38(var2, var3);
         case 39:
            return this.connect$39(var2, var3);
         case 40:
            return this.getfile$40(var2, var3);
         case 41:
            return this.getreply$41(var2, var3);
         case 42:
            return this.close$42(var2, var3);
         case 43:
            return this.HTTPSConnection$43(var2, var3);
         case 44:
            return this.__init__$44(var2, var3);
         case 45:
            return this.connect$45(var2, var3);
         case 46:
            return this.HTTPS$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.FakeSocket$48(var2, var3);
         case 49:
            return this.HTTPException$49(var2, var3);
         case 50:
            return this.NotConnected$50(var2, var3);
         case 51:
            return this.InvalidURL$51(var2, var3);
         case 52:
            return this.UnknownProtocol$52(var2, var3);
         case 53:
            return this.__init__$53(var2, var3);
         case 54:
            return this.UnknownTransferEncoding$54(var2, var3);
         case 55:
            return this.UnimplementedFileMode$55(var2, var3);
         case 56:
            return this.IncompleteRead$56(var2, var3);
         case 57:
            return this.__init__$57(var2, var3);
         case 58:
            return this.__repr__$58(var2, var3);
         case 59:
            return this.__str__$59(var2, var3);
         case 60:
            return this.ImproperConnectionState$60(var2, var3);
         case 61:
            return this.CannotSendRequest$61(var2, var3);
         case 62:
            return this.CannotSendHeader$62(var2, var3);
         case 63:
            return this.ResponseNotReady$63(var2, var3);
         case 64:
            return this.BadStatusLine$64(var2, var3);
         case 65:
            return this.__init__$65(var2, var3);
         case 66:
            return this.LineTooLong$66(var2, var3);
         case 67:
            return this.__init__$67(var2, var3);
         case 68:
            return this.LineAndFileWrapper$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this.__getattr__$70(var2, var3);
         case 71:
            return this._done$71(var2, var3);
         case 72:
            return this.read$72(var2, var3);
         case 73:
            return this.readline$73(var2, var3);
         case 74:
            return this.readlines$74(var2, var3);
         default:
            return null;
      }
   }
}

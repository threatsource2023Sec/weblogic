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
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("BaseHTTPServer.py")
public class BaseHTTPServer$py extends PyFunctionTable implements PyRunnable {
   static BaseHTTPServer$py self;
   static final PyCode f$0;
   static final PyCode _quote_html$1;
   static final PyCode HTTPServer$2;
   static final PyCode server_bind$3;
   static final PyCode server_activate$4;
   static final PyCode BaseHTTPRequestHandler$5;
   static final PyCode parse_request$6;
   static final PyCode handle_one_request$7;
   static final PyCode handle$8;
   static final PyCode send_error$9;
   static final PyCode send_response$10;
   static final PyCode send_header$11;
   static final PyCode end_headers$12;
   static final PyCode log_request$13;
   static final PyCode log_error$14;
   static final PyCode log_message$15;
   static final PyCode version_string$16;
   static final PyCode date_time_string$17;
   static final PyCode log_date_time_string$18;
   static final PyCode address_string$19;
   static final PyCode test$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setglobal("__doc__", PyString.fromInterned("HTTP server base class.\n\nNote: the class in this module doesn't implement any HTTP request; see\nSimpleHTTPServer for simple implementations of GET, HEAD and POST\n(including CGI scripts).  It does, however, optionally implement HTTP/1.1\npersistent connections, as of version 0.3.\n\nContents:\n\n- BaseHTTPRequestHandler: HTTP request handler base class\n- test: test function\n\nXXX To do:\n\n- log requests even later (to capture byte count)\n- log user-agent header and other interesting goodies\n- send error log to separate file\n"));
      var1.setline(18);
      PyString.fromInterned("HTTP server base class.\n\nNote: the class in this module doesn't implement any HTTP request; see\nSimpleHTTPServer for simple implementations of GET, HEAD and POST\n(including CGI scripts).  It does, however, optionally implement HTTP/1.1\npersistent connections, as of version 0.3.\n\nContents:\n\n- BaseHTTPRequestHandler: HTTP request handler base class\n- test: test function\n\nXXX To do:\n\n- log requests even later (to capture byte count)\n- log user-agent header and other interesting goodies\n- send error log to separate file\n");
      var1.setline(69);
      PyString var3 = PyString.fromInterned("0.3");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(71);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("HTTPServer"), PyString.fromInterned("BaseHTTPRequestHandler")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(73);
      PyObject var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(74);
      var7 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var7);
      var3 = null;
      var1.setline(75);
      var7 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var7);
      var3 = null;
      var1.setline(76);
      String[] var8 = new String[]{"filterwarnings", "catch_warnings"};
      PyObject[] var9 = imp.importFrom("warnings", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("filterwarnings", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("catch_warnings", var4);
      var4 = null;
      ContextManager var10;
      var4 = (var10 = ContextGuard.getManager(var1.getname("catch_warnings").__call__(var2))).__enter__(var2);

      label26: {
         try {
            var1.setline(78);
            if (var1.getname("sys").__getattr__("py3kwarning").__nonzero__()) {
               var1.setline(79);
               var1.getname("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*mimetools has been removed"), (PyObject)var1.getname("DeprecationWarning"));
            }

            var1.setline(81);
            var4 = imp.importOne("mimetools", var1, -1);
            var1.setlocal("mimetools", var4);
            var4 = null;
         } catch (Throwable var5) {
            if (var10.__exit__(var2, Py.setException(var5, var1))) {
               break label26;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(82);
      var7 = imp.importOne("SocketServer", var1, -1);
      var1.setlocal("SocketServer", var7);
      var3 = null;
      var1.setline(85);
      var3 = PyString.fromInterned("<head>\n<title>Error response</title>\n</head>\n<body>\n<h1>Error response</h1>\n<p>Error code %(code)d.\n<p>Message: %(message)s.\n<p>Error code explanation: %(code)s = %(explain)s.\n</body>\n");
      var1.setlocal("DEFAULT_ERROR_MESSAGE", var3);
      var3 = null;
      var1.setline(97);
      var3 = PyString.fromInterned("text/html");
      var1.setlocal("DEFAULT_ERROR_CONTENT_TYPE", var3);
      var3 = null;
      var1.setline(99);
      var9 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var9, _quote_html$1, (PyObject)null);
      var1.setlocal("_quote_html", var11);
      var3 = null;
      var1.setline(102);
      var9 = new PyObject[]{var1.getname("SocketServer").__getattr__("TCPServer")};
      var4 = Py.makeClass("HTTPServer", var9, HTTPServer$2);
      var1.setlocal("HTTPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(125);
      var9 = new PyObject[]{var1.getname("SocketServer").__getattr__("StreamRequestHandler")};
      var4 = Py.makeClass("BaseHTTPRequestHandler", var9, BaseHTTPRequestHandler$5);
      var1.setlocal("BaseHTTPRequestHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(590);
      var9 = new PyObject[]{var1.getname("BaseHTTPRequestHandler"), var1.getname("HTTPServer"), PyString.fromInterned("HTTP/1.0")};
      var11 = new PyFunction(var1.f_globals, var9, test$20, PyString.fromInterned("Test the HTTP request handler class.\n\n    This runs an HTTP server on port 8000 (or the first command line\n    argument).\n\n    "));
      var1.setlocal("test", var11);
      var3 = null;
      var1.setline(613);
      var7 = var1.getname("__name__");
      PyObject var10000 = var7._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(614);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _quote_html$1(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HTTPServer$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(104);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("allow_reuse_address", var3);
      var3 = null;
      var1.setline(106);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, server_bind$3, PyString.fromInterned("Override server_bind to store the server name."));
      var1.setlocal("server_bind", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, server_activate$4, (PyObject)null);
      var1.setlocal("server_activate", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject server_bind$3(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Override server_bind to store the server name.");
      var1.setline(108);
      var1.getglobal("SocketServer").__getattr__("TCPServer").__getattr__("server_bind").__call__(var2, var1.getlocal(0));

      PyException var3;
      try {
         var1.setline(110);
         PyObject var7 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockname").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(111);
         var7 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("server_name", var7);
         var3 = null;
         var1.setline(112);
         var7 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("server_port", var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var3;
         }

         var1.setline(114);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_activate$4(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      var1.getglobal("SocketServer").__getattr__("TCPServer").__getattr__("server_activate").__call__(var2, var1.getlocal(0));
      var1.setline(120);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockname").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("server_name", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("server_port", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseHTTPRequestHandler$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("HTTP request handler base class.\n\n    The following explanation of HTTP serves to guide you through the\n    code as well as to expose any misunderstandings I may have about\n    HTTP (so you don't need to read the code to figure out I'm wrong\n    :-).\n\n    HTTP (HyperText Transfer Protocol) is an extensible protocol on\n    top of a reliable stream transport (e.g. TCP/IP).  The protocol\n    recognizes three parts to a request:\n\n    1. One line identifying the request type and path\n    2. An optional set of RFC-822-style headers\n    3. An optional data part\n\n    The headers and data are separated by a blank line.\n\n    The first line of the request has the form\n\n    <command> <path> <version>\n\n    where <command> is a (case-sensitive) keyword such as GET or POST,\n    <path> is a string containing path information for the request,\n    and <version> should be the string \"HTTP/1.0\" or \"HTTP/1.1\".\n    <path> is encoded using the URL encoding scheme (using %xx to signify\n    the ASCII character with hex code xx).\n\n    The specification specifies that lines are separated by CRLF but\n    for compatibility with the widest range of clients recommends\n    servers also handle LF.  Similarly, whitespace in the request line\n    is treated sensibly (allowing multiple spaces between components\n    and allowing trailing whitespace).\n\n    Similarly, for output, lines ought to be separated by CRLF pairs\n    but most clients grok LF characters just fine.\n\n    If the first line of the request has the form\n\n    <command> <path>\n\n    (i.e. <version> is left out) then this is assumed to be an HTTP\n    0.9 request; this form has no optional headers and data part and\n    the reply consists of just the data.\n\n    The reply form of the HTTP 1.x protocol again has three parts:\n\n    1. One line giving the response code\n    2. An optional set of RFC-822-style headers\n    3. The data\n\n    Again, the headers and data are separated by a blank line.\n\n    The response code line has the form\n\n    <version> <responsecode> <responsestring>\n\n    where <version> is the protocol version (\"HTTP/1.0\" or \"HTTP/1.1\"),\n    <responsecode> is a 3-digit response code indicating success or\n    failure of the request, and <responsestring> is an optional\n    human-readable string explaining what the response code means.\n\n    This server parses the request and the headers, and then calls a\n    function specific to the request type (<command>).  Specifically,\n    a request SPAM will be handled by a method do_SPAM().  If no\n    such method exists the server sends an error response to the\n    client.  If it exists, it is called with no arguments:\n\n    do_SPAM()\n\n    Note that the request name is case sensitive (i.e. SPAM and spam\n    are different requests).\n\n    The various request details are stored in instance variables:\n\n    - client_address is the client IP address in the form (host,\n    port);\n\n    - command, path and version are the broken-down request line;\n\n    - headers is an instance of mimetools.Message (or a derived\n    class) containing the header information;\n\n    - rfile is a file object open for reading positioned at the\n    start of the optional input data part;\n\n    - wfile is a file object open for writing.\n\n    IT IS IMPORTANT TO ADHERE TO THE PROTOCOL FOR WRITING!\n\n    The first thing to be written must be the response line.  Then\n    follow 0 or more header lines, then a blank line, and then the\n    actual data (if any).  The meaning of the header lines depends on\n    the command executed by the server; in most cases, when data is\n    returned, there should be at least one header line of the form\n\n    Content-type: <type>/<subtype>\n\n    where <type> and <subtype> should be registered MIME types,\n    e.g. \"text/html\" or \"text/plain\".\n\n    "));
      var1.setline(227);
      PyString.fromInterned("HTTP request handler base class.\n\n    The following explanation of HTTP serves to guide you through the\n    code as well as to expose any misunderstandings I may have about\n    HTTP (so you don't need to read the code to figure out I'm wrong\n    :-).\n\n    HTTP (HyperText Transfer Protocol) is an extensible protocol on\n    top of a reliable stream transport (e.g. TCP/IP).  The protocol\n    recognizes three parts to a request:\n\n    1. One line identifying the request type and path\n    2. An optional set of RFC-822-style headers\n    3. An optional data part\n\n    The headers and data are separated by a blank line.\n\n    The first line of the request has the form\n\n    <command> <path> <version>\n\n    where <command> is a (case-sensitive) keyword such as GET or POST,\n    <path> is a string containing path information for the request,\n    and <version> should be the string \"HTTP/1.0\" or \"HTTP/1.1\".\n    <path> is encoded using the URL encoding scheme (using %xx to signify\n    the ASCII character with hex code xx).\n\n    The specification specifies that lines are separated by CRLF but\n    for compatibility with the widest range of clients recommends\n    servers also handle LF.  Similarly, whitespace in the request line\n    is treated sensibly (allowing multiple spaces between components\n    and allowing trailing whitespace).\n\n    Similarly, for output, lines ought to be separated by CRLF pairs\n    but most clients grok LF characters just fine.\n\n    If the first line of the request has the form\n\n    <command> <path>\n\n    (i.e. <version> is left out) then this is assumed to be an HTTP\n    0.9 request; this form has no optional headers and data part and\n    the reply consists of just the data.\n\n    The reply form of the HTTP 1.x protocol again has three parts:\n\n    1. One line giving the response code\n    2. An optional set of RFC-822-style headers\n    3. The data\n\n    Again, the headers and data are separated by a blank line.\n\n    The response code line has the form\n\n    <version> <responsecode> <responsestring>\n\n    where <version> is the protocol version (\"HTTP/1.0\" or \"HTTP/1.1\"),\n    <responsecode> is a 3-digit response code indicating success or\n    failure of the request, and <responsestring> is an optional\n    human-readable string explaining what the response code means.\n\n    This server parses the request and the headers, and then calls a\n    function specific to the request type (<command>).  Specifically,\n    a request SPAM will be handled by a method do_SPAM().  If no\n    such method exists the server sends an error response to the\n    client.  If it exists, it is called with no arguments:\n\n    do_SPAM()\n\n    Note that the request name is case sensitive (i.e. SPAM and spam\n    are different requests).\n\n    The various request details are stored in instance variables:\n\n    - client_address is the client IP address in the form (host,\n    port);\n\n    - command, path and version are the broken-down request line;\n\n    - headers is an instance of mimetools.Message (or a derived\n    class) containing the header information;\n\n    - rfile is a file object open for reading positioned at the\n    start of the optional input data part;\n\n    - wfile is a file object open for writing.\n\n    IT IS IMPORTANT TO ADHERE TO THE PROTOCOL FOR WRITING!\n\n    The first thing to be written must be the response line.  Then\n    follow 0 or more header lines, then a blank line, and then the\n    actual data (if any).  The meaning of the header lines depends on\n    the command executed by the server; in most cases, when data is\n    returned, there should be at least one header line of the form\n\n    Content-type: <type>/<subtype>\n\n    where <type> and <subtype> should be registered MIME types,\n    e.g. \"text/html\" or \"text/plain\".\n\n    ");
      var1.setline(230);
      PyObject var3 = PyString.fromInterned("Python/")._add(var1.getname("sys").__getattr__("version").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0)));
      var1.setlocal("sys_version", var3);
      var3 = null;
      var1.setline(235);
      var3 = PyString.fromInterned("BaseHTTP/")._add(var1.getname("__version__"));
      var1.setlocal("server_version", var3);
      var3 = null;
      var1.setline(241);
      PyString var4 = PyString.fromInterned("HTTP/0.9");
      var1.setlocal("default_request_version", var4);
      var3 = null;
      var1.setline(243);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, parse_request$6, PyString.fromInterned("Parse a request (internal).\n\n        The request should be stored in self.raw_requestline; the results\n        are in self.command, self.path, self.request_version and\n        self.headers.\n\n        Return True for success, False for failure; on failure, an\n        error is sent back.\n\n        "));
      var1.setlocal("parse_request", var6);
      var3 = null;
      var1.setline(312);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_one_request$7, PyString.fromInterned("Handle a single HTTP request.\n\n        You normally don't need to override this method; see the class\n        __doc__ string for information on how to handle specific HTTP\n        commands such as GET and POST.\n\n        "));
      var1.setlocal("handle_one_request", var6);
      var3 = null;
      var1.setline(347);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle$8, PyString.fromInterned("Handle multiple requests if necessary."));
      var1.setlocal("handle", var6);
      var3 = null;
      var1.setline(355);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, send_error$9, PyString.fromInterned("Send and log an error reply.\n\n        Arguments are the error code, and a detailed message.\n        The detailed message defaults to the short entry matching the\n        response code.\n\n        This sends an error response (so it must be called before any\n        output has been generated), logs the error, and finally sends\n        a piece of HTML explaining the error to the user.\n\n        "));
      var1.setlocal("send_error", var6);
      var3 = null;
      var1.setline(386);
      var3 = var1.getname("DEFAULT_ERROR_MESSAGE");
      var1.setlocal("error_message_format", var3);
      var3 = null;
      var1.setline(387);
      var3 = var1.getname("DEFAULT_ERROR_CONTENT_TYPE");
      var1.setlocal("error_content_type", var3);
      var3 = null;
      var1.setline(389);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, send_response$10, PyString.fromInterned("Send the response header and log the response code.\n\n        Also send two standard headers with the server software\n        version and the current date.\n\n        "));
      var1.setlocal("send_response", var6);
      var3 = null;
      var1.setline(409);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, send_header$11, PyString.fromInterned("Send a MIME header."));
      var1.setlocal("send_header", var6);
      var3 = null;
      var1.setline(420);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_headers$12, PyString.fromInterned("Send the blank line ending the MIME headers."));
      var1.setlocal("end_headers", var6);
      var3 = null;
      var1.setline(425);
      var5 = new PyObject[]{PyString.fromInterned("-"), PyString.fromInterned("-")};
      var6 = new PyFunction(var1.f_globals, var5, log_request$13, PyString.fromInterned("Log an accepted request.\n\n        This is called by send_response().\n\n        "));
      var1.setlocal("log_request", var6);
      var3 = null;
      var1.setline(435);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_error$14, PyString.fromInterned("Log an error.\n\n        This is called when a request cannot be fulfilled.  By\n        default it passes the message on to log_message().\n\n        Arguments are the same as for log_message().\n\n        XXX This should go to the separate error log.\n\n        "));
      var1.setlocal("log_error", var6);
      var3 = null;
      var1.setline(449);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_message$15, PyString.fromInterned("Log an arbitrary message.\n\n        This is used by all other logging functions.  Override\n        it if you have specific logging wishes.\n\n        The first argument, FORMAT, is a format string for the\n        message to be logged.  If the format string contains\n        any % escapes requiring parameters, they should be\n        specified as subsequent arguments (it's just like\n        printf!).\n\n        The client ip address and current date/time are prefixed to every\n        message.\n\n        "));
      var1.setlocal("log_message", var6);
      var3 = null;
      var1.setline(471);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, version_string$16, PyString.fromInterned("Return the server software version string."));
      var1.setlocal("version_string", var6);
      var3 = null;
      var1.setline(475);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, date_time_string$17, PyString.fromInterned("Return the current date and time formatted for a message header."));
      var1.setlocal("date_time_string", var6);
      var3 = null;
      var1.setline(486);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_date_time_string$18, PyString.fromInterned("Return the current time formatted for logging."));
      var1.setlocal("log_date_time_string", var6);
      var3 = null;
      var1.setline(494);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")});
      var1.setlocal("weekdayname", var7);
      var3 = null;
      var1.setline(496);
      var7 = new PyList(new PyObject[]{var1.getname("None"), PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
      var1.setlocal("monthname", var7);
      var3 = null;
      var1.setline(500);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, address_string$19, PyString.fromInterned("Return the client address formatted for logging.\n\n        This version looks up the full hostname using gethostbyaddr(),\n        and tries to find a name that contains at least one dot.\n\n        "));
      var1.setlocal("address_string", var6);
      var3 = null;
      var1.setline(515);
      var4 = PyString.fromInterned("HTTP/1.0");
      var1.setlocal("protocol_version", var4);
      var3 = null;
      var1.setline(518);
      var3 = var1.getname("mimetools").__getattr__("Message");
      var1.setlocal("MessageClass", var3);
      var3 = null;
      var1.setline(523);
      PyDictionary var8 = new PyDictionary(new PyObject[]{Py.newInteger(100), new PyTuple(new PyObject[]{PyString.fromInterned("Continue"), PyString.fromInterned("Request received, please continue")}), Py.newInteger(101), new PyTuple(new PyObject[]{PyString.fromInterned("Switching Protocols"), PyString.fromInterned("Switching to new protocol; obey Upgrade header")}), Py.newInteger(200), new PyTuple(new PyObject[]{PyString.fromInterned("OK"), PyString.fromInterned("Request fulfilled, document follows")}), Py.newInteger(201), new PyTuple(new PyObject[]{PyString.fromInterned("Created"), PyString.fromInterned("Document created, URL follows")}), Py.newInteger(202), new PyTuple(new PyObject[]{PyString.fromInterned("Accepted"), PyString.fromInterned("Request accepted, processing continues off-line")}), Py.newInteger(203), new PyTuple(new PyObject[]{PyString.fromInterned("Non-Authoritative Information"), PyString.fromInterned("Request fulfilled from cache")}), Py.newInteger(204), new PyTuple(new PyObject[]{PyString.fromInterned("No Content"), PyString.fromInterned("Request fulfilled, nothing follows")}), Py.newInteger(205), new PyTuple(new PyObject[]{PyString.fromInterned("Reset Content"), PyString.fromInterned("Clear input form for further input.")}), Py.newInteger(206), new PyTuple(new PyObject[]{PyString.fromInterned("Partial Content"), PyString.fromInterned("Partial content follows.")}), Py.newInteger(300), new PyTuple(new PyObject[]{PyString.fromInterned("Multiple Choices"), PyString.fromInterned("Object has several resources -- see URI list")}), Py.newInteger(301), new PyTuple(new PyObject[]{PyString.fromInterned("Moved Permanently"), PyString.fromInterned("Object moved permanently -- see URI list")}), Py.newInteger(302), new PyTuple(new PyObject[]{PyString.fromInterned("Found"), PyString.fromInterned("Object moved temporarily -- see URI list")}), Py.newInteger(303), new PyTuple(new PyObject[]{PyString.fromInterned("See Other"), PyString.fromInterned("Object moved -- see Method and URL list")}), Py.newInteger(304), new PyTuple(new PyObject[]{PyString.fromInterned("Not Modified"), PyString.fromInterned("Document has not changed since given time")}), Py.newInteger(305), new PyTuple(new PyObject[]{PyString.fromInterned("Use Proxy"), PyString.fromInterned("You must use proxy specified in Location to access this resource.")}), Py.newInteger(307), new PyTuple(new PyObject[]{PyString.fromInterned("Temporary Redirect"), PyString.fromInterned("Object moved temporarily -- see URI list")}), Py.newInteger(400), new PyTuple(new PyObject[]{PyString.fromInterned("Bad Request"), PyString.fromInterned("Bad request syntax or unsupported method")}), Py.newInteger(401), new PyTuple(new PyObject[]{PyString.fromInterned("Unauthorized"), PyString.fromInterned("No permission -- see authorization schemes")}), Py.newInteger(402), new PyTuple(new PyObject[]{PyString.fromInterned("Payment Required"), PyString.fromInterned("No payment -- see charging schemes")}), Py.newInteger(403), new PyTuple(new PyObject[]{PyString.fromInterned("Forbidden"), PyString.fromInterned("Request forbidden -- authorization will not help")}), Py.newInteger(404), new PyTuple(new PyObject[]{PyString.fromInterned("Not Found"), PyString.fromInterned("Nothing matches the given URI")}), Py.newInteger(405), new PyTuple(new PyObject[]{PyString.fromInterned("Method Not Allowed"), PyString.fromInterned("Specified method is invalid for this resource.")}), Py.newInteger(406), new PyTuple(new PyObject[]{PyString.fromInterned("Not Acceptable"), PyString.fromInterned("URI not available in preferred format.")}), Py.newInteger(407), new PyTuple(new PyObject[]{PyString.fromInterned("Proxy Authentication Required"), PyString.fromInterned("You must authenticate with this proxy before proceeding.")}), Py.newInteger(408), new PyTuple(new PyObject[]{PyString.fromInterned("Request Timeout"), PyString.fromInterned("Request timed out; try again later.")}), Py.newInteger(409), new PyTuple(new PyObject[]{PyString.fromInterned("Conflict"), PyString.fromInterned("Request conflict.")}), Py.newInteger(410), new PyTuple(new PyObject[]{PyString.fromInterned("Gone"), PyString.fromInterned("URI no longer exists and has been permanently removed.")}), Py.newInteger(411), new PyTuple(new PyObject[]{PyString.fromInterned("Length Required"), PyString.fromInterned("Client must specify Content-Length.")}), Py.newInteger(412), new PyTuple(new PyObject[]{PyString.fromInterned("Precondition Failed"), PyString.fromInterned("Precondition in headers is false.")}), Py.newInteger(413), new PyTuple(new PyObject[]{PyString.fromInterned("Request Entity Too Large"), PyString.fromInterned("Entity is too large.")}), Py.newInteger(414), new PyTuple(new PyObject[]{PyString.fromInterned("Request-URI Too Long"), PyString.fromInterned("URI is too long.")}), Py.newInteger(415), new PyTuple(new PyObject[]{PyString.fromInterned("Unsupported Media Type"), PyString.fromInterned("Entity body in unsupported format.")}), Py.newInteger(416), new PyTuple(new PyObject[]{PyString.fromInterned("Requested Range Not Satisfiable"), PyString.fromInterned("Cannot satisfy request range.")}), Py.newInteger(417), new PyTuple(new PyObject[]{PyString.fromInterned("Expectation Failed"), PyString.fromInterned("Expect condition could not be satisfied.")}), Py.newInteger(500), new PyTuple(new PyObject[]{PyString.fromInterned("Internal Server Error"), PyString.fromInterned("Server got itself in trouble")}), Py.newInteger(501), new PyTuple(new PyObject[]{PyString.fromInterned("Not Implemented"), PyString.fromInterned("Server does not support this operation")}), Py.newInteger(502), new PyTuple(new PyObject[]{PyString.fromInterned("Bad Gateway"), PyString.fromInterned("Invalid responses from another server/proxy.")}), Py.newInteger(503), new PyTuple(new PyObject[]{PyString.fromInterned("Service Unavailable"), PyString.fromInterned("The server cannot process the request due to a high load")}), Py.newInteger(504), new PyTuple(new PyObject[]{PyString.fromInterned("Gateway Timeout"), PyString.fromInterned("The gateway server did not receive a timely response")}), Py.newInteger(505), new PyTuple(new PyObject[]{PyString.fromInterned("HTTP Version Not Supported"), PyString.fromInterned("Cannot fulfill request.")})});
      var1.setlocal("responses", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parse_request$6(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Parse a request (internal).\n\n        The request should be stored in self.raw_requestline; the results\n        are in self.command, self.path, self.request_version and\n        self.headers.\n\n        Return True for success, False for failure; on failure, an\n        error is sent back.\n\n        ");
      var1.setline(254);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("command", var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getlocal(0).__getattr__("default_request_version");
      var1.getlocal(0).__setattr__("request_version", var3);
      var1.setlocal(1, var3);
      var1.setline(256);
      PyInteger var8 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"close_connection", var8);
      var3 = null;
      var1.setline(257);
      var3 = var1.getlocal(0).__getattr__("raw_requestline");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(258);
      var3 = var1.getlocal(2).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(259);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("requestline", var3);
      var3 = null;
      var1.setline(260);
      var3 = var1.getlocal(2).__getattr__("split").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      PyInteger var10;
      PyTuple var13;
      if (var10000.__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(3);
         PyObject[] var11 = Py.unpackSequence(var3, 3);
         PyObject var9 = var11[0];
         var1.setlocal(4, var9);
         var5 = null;
         var9 = var11[1];
         var1.setlocal(5, var9);
         var5 = null;
         var9 = var11[2];
         var1.setlocal(1, var9);
         var5 = null;
         var3 = null;
         var1.setline(263);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("HTTP/"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(264);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(400), (PyObject)PyString.fromInterned("Bad request version (%r)")._mod(var1.getlocal(1)));
            var1.setline(265);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         try {
            var1.setline(267);
            var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(268);
            var4 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(275);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var10000 = var4._ne(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(276);
               throw Py.makeException(var1.getglobal("ValueError"));
            }

            var1.setline(277);
            var13 = new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0))), var1.getglobal("int").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(1)))});
            var1.setlocal(7, var13);
            var4 = null;
         } catch (Throwable var7) {
            PyException var12 = Py.setException(var7, var1);
            if (var12.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("IndexError")}))) {
               var1.setline(279);
               var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(400), (PyObject)PyString.fromInterned("Bad request version (%r)")._mod(var1.getlocal(1)));
               var1.setline(280);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            throw var12;
         }

         var1.setline(281);
         var4 = var1.getlocal(7);
         var10000 = var4._ge(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(1)}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("protocol_version");
            var10000 = var4._ge(PyString.fromInterned("HTTP/1.1"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(282);
            var10 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"close_connection", var10);
            var4 = null;
         }

         var1.setline(283);
         var4 = var1.getlocal(7);
         var10000 = var4._ge(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(0)}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(284);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(505), (PyObject)PyString.fromInterned("Invalid HTTP Version (%s)")._mod(var1.getlocal(6)));
            var1.setline(286);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(287);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var4._eq(Py.newInteger(2));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(294);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(295);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(297);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(400), (PyObject)PyString.fromInterned("Bad request syntax (%r)")._mod(var1.getlocal(2)));
            var1.setline(298);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(288);
         var4 = var1.getlocal(3);
         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var4 = null;
         var1.setline(289);
         var10 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"close_connection", var10);
         var4 = null;
         var1.setline(290);
         var4 = var1.getlocal(4);
         var10000 = var4._ne(PyString.fromInterned("GET"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(291);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(400), (PyObject)PyString.fromInterned("Bad HTTP/0.9 request type (%r)")._mod(var1.getlocal(4)));
            var1.setline(293);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(299);
      var13 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(1)});
      var5 = Py.unpackSequence(var13, 3);
      var6 = var5[0];
      var1.getlocal(0).__setattr__("command", var6);
      var6 = null;
      var6 = var5[1];
      var1.getlocal(0).__setattr__("path", var6);
      var6 = null;
      var6 = var5[2];
      var1.getlocal(0).__setattr__("request_version", var6);
      var6 = null;
      var4 = null;
      var1.setline(302);
      var4 = var1.getlocal(0).__getattr__("MessageClass").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("rfile"), (PyObject)Py.newInteger(0));
      var1.getlocal(0).__setattr__("headers", var4);
      var4 = null;
      var1.setline(304);
      var4 = var1.getlocal(0).__getattr__("headers").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Connection"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(305);
      var4 = var1.getlocal(8).__getattr__("lower").__call__(var2);
      var10000 = var4._eq(PyString.fromInterned("close"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(306);
         var10 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"close_connection", var10);
         var4 = null;
      } else {
         var1.setline(307);
         var4 = var1.getlocal(8).__getattr__("lower").__call__(var2);
         var10000 = var4._eq(PyString.fromInterned("keep-alive"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("protocol_version");
            var10000 = var4._ge(PyString.fromInterned("HTTP/1.1"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(309);
            var10 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"close_connection", var10);
            var4 = null;
         }
      }

      var1.setline(310);
      var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle_one_request$7(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("Handle a single HTTP request.\n\n        You normally don't need to override this method; see the class\n        __doc__ string for information on how to handle specific HTTP\n        commands such as GET and POST.\n\n        ");

      PyException var3;
      try {
         var1.setline(321);
         PyObject var6 = var1.getlocal(0).__getattr__("rfile").__getattr__("readline").__call__((ThreadState)var2, (PyObject)Py.newInteger(65537));
         var1.getlocal(0).__setattr__("raw_requestline", var6);
         var3 = null;
         var1.setline(322);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("raw_requestline"));
         PyObject var10000 = var6._gt(Py.newInteger(65536));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(323);
            PyString var9 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"requestline", var9);
            var3 = null;
            var1.setline(324);
            var9 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"request_version", var9);
            var3 = null;
            var1.setline(325);
            var9 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"command", var9);
            var3 = null;
            var1.setline(326);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(414));
            var1.setline(327);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(328);
         if (var1.getlocal(0).__getattr__("raw_requestline").__not__().__nonzero__()) {
            var1.setline(329);
            PyInteger var8 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"close_connection", var8);
            var3 = null;
            var1.setline(330);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(331);
         if (var1.getlocal(0).__getattr__("parse_request").__call__(var2).__not__().__nonzero__()) {
            var1.setline(333);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(334);
         var6 = PyString.fromInterned("do_")._add(var1.getlocal(0).__getattr__("command"));
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(335);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(336);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(501), (PyObject)PyString.fromInterned("Unsupported method (%r)")._mod(var1.getlocal(0).__getattr__("command")));
            var1.setline(337);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(338);
         var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(339);
         var1.getlocal(2).__call__(var2);
         var1.setline(340);
         var1.getlocal(0).__getattr__("wfile").__getattr__("flush").__call__(var2);
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("socket").__getattr__("timeout"))) {
            PyObject var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(343);
            var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Request timed out: %r"), (PyObject)var1.getlocal(3));
            var1.setline(344);
            PyInteger var7 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"close_connection", var7);
            var4 = null;
            var1.setline(345);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle$8(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Handle multiple requests if necessary.");
      var1.setline(349);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"close_connection", var3);
      var3 = null;
      var1.setline(351);
      var1.getlocal(0).__getattr__("handle_one_request").__call__(var2);

      while(true) {
         var1.setline(352);
         if (!var1.getlocal(0).__getattr__("close_connection").__not__().__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(353);
         var1.getlocal(0).__getattr__("handle_one_request").__call__(var2);
      }
   }

   public PyObject send_error$9(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyString.fromInterned("Send and log an error reply.\n\n        Arguments are the error code, and a detailed message.\n        The detailed message defaults to the short entry matching the\n        response code.\n\n        This sends an error response (so it must be called before any\n        output has been generated), logs the error, and finally sends\n        a piece of HTML explaining the error to the user.\n\n        ");

      PyException var3;
      PyObject[] var5;
      PyObject var8;
      try {
         var1.setline(369);
         var8 = var1.getlocal(0).__getattr__("responses").__getitem__(var1.getlocal(1));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var10 = var9[0];
         var1.setlocal(3, var10);
         var5 = null;
         var10 = var9[1];
         var1.setlocal(4, var10);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(371);
         PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("???"), PyString.fromInterned("???")});
         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(372);
      var8 = var1.getlocal(2);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(373);
         var8 = var1.getlocal(3);
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(374);
      var8 = var1.getlocal(4);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(375);
      var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, PyString.fromInterned("code %d, message %s"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
      var1.setline(377);
      var8 = var1.getlocal(0).__getattr__("error_message_format")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("code"), var1.getlocal(1), PyString.fromInterned("message"), var1.getglobal("_quote_html").__call__(var2, var1.getlocal(2)), PyString.fromInterned("explain"), var1.getlocal(5)}));
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(379);
      var1.getlocal(0).__getattr__("send_response").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(380);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type"), (PyObject)var1.getlocal(0).__getattr__("error_content_type"));
      var1.setline(381);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Connection"), (PyObject)PyString.fromInterned("close"));
      var1.setline(382);
      var1.getlocal(0).__getattr__("end_headers").__call__(var2);
      var1.setline(383);
      var8 = var1.getlocal(0).__getattr__("command");
      var10000 = var8._ne(PyString.fromInterned("HEAD"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var8 = var1.getlocal(1);
         var10000 = var8._ge(Py.newInteger(200));
         var3 = null;
         if (var10000.__nonzero__()) {
            var8 = var1.getlocal(1);
            var10000 = var8._notin(new PyTuple(new PyObject[]{Py.newInteger(204), Py.newInteger(304)}));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(384);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_response$10(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyString.fromInterned("Send the response header and log the response code.\n\n        Also send two standard headers with the server software\n        version and the current date.\n\n        ");
      var1.setline(396);
      var1.getlocal(0).__getattr__("log_request").__call__(var2, var1.getlocal(1));
      var1.setline(397);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(398);
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("responses"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(399);
            var3 = var1.getlocal(0).__getattr__("responses").__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(401);
            PyString var4 = PyString.fromInterned("");
            var1.setlocal(2, var4);
            var3 = null;
         }
      }

      var1.setline(402);
      var3 = var1.getlocal(0).__getattr__("request_version");
      var10000 = var3._ne(PyString.fromInterned("HTTP/0.9"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(403);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, PyString.fromInterned("%s %d %s\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("protocol_version"), var1.getlocal(1), var1.getlocal(2)})));
      }

      var1.setline(406);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Server"), (PyObject)var1.getlocal(0).__getattr__("version_string").__call__(var2));
      var1.setline(407);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Date"), (PyObject)var1.getlocal(0).__getattr__("date_time_string").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_header$11(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("Send a MIME header.");
      var1.setline(411);
      PyObject var3 = var1.getlocal(0).__getattr__("request_version");
      PyObject var10000 = var3._ne(PyString.fromInterned("HTTP/0.9"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(412);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      }

      var1.setline(414);
      var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var10000 = var3._eq(PyString.fromInterned("connection"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(415);
         var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("close"));
         var3 = null;
         PyInteger var4;
         if (var10000.__nonzero__()) {
            var1.setline(416);
            var4 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"close_connection", var4);
            var3 = null;
         } else {
            var1.setline(417);
            var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("keep-alive"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(418);
               var4 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"close_connection", var4);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_headers$12(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyString.fromInterned("Send the blank line ending the MIME headers.");
      var1.setline(422);
      PyObject var3 = var1.getlocal(0).__getattr__("request_version");
      PyObject var10000 = var3._ne(PyString.fromInterned("HTTP/0.9"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(423);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_request$13(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyString.fromInterned("Log an accepted request.\n\n        This is called by send_response().\n\n        ");
      var1.setline(432);
      var1.getlocal(0).__getattr__("log_message").__call__(var2, PyString.fromInterned("\"%s\" %s %s"), var1.getlocal(0).__getattr__("requestline"), var1.getglobal("str").__call__(var2, var1.getlocal(1)), var1.getglobal("str").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_error$14(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyString.fromInterned("Log an error.\n\n        This is called when a request cannot be fulfilled.  By\n        default it passes the message on to log_message().\n\n        Arguments are the same as for log_message().\n\n        XXX This should go to the separate error log.\n\n        ");
      var1.setline(447);
      PyObject var10000 = var1.getlocal(0).__getattr__("log_message");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_message$15(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyString.fromInterned("Log an arbitrary message.\n\n        This is used by all other logging functions.  Override\n        it if you have specific logging wishes.\n\n        The first argument, FORMAT, is a format string for the\n        message to be logged.  If the format string contains\n        any % escapes requiring parameters, they should be\n        specified as subsequent arguments (it's just like\n        printf!).\n\n        The client ip address and current date/time are prefixed to every\n        message.\n\n        ");
      var1.setline(466);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("%s - - [%s] %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("client_address").__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("log_date_time_string").__call__(var2), var1.getlocal(1)._mod(var1.getlocal(2))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject version_string$16(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString.fromInterned("Return the server software version string.");
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("server_version")._add(PyString.fromInterned(" "))._add(var1.getlocal(0).__getattr__("sys_version"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject date_time_string$17(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyString.fromInterned("Return the current date and time formatted for a message header.");
      var1.setline(477);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(478);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(479);
      var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 9);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(480);
      var3 = PyString.fromInterned("%s, %02d %3s %4d %02d:%02d:%02d GMT")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("weekdayname").__getitem__(var1.getlocal(8)), var1.getlocal(4), var1.getlocal(0).__getattr__("monthname").__getitem__(var1.getlocal(3)), var1.getlocal(2), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)}));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(484);
      var3 = var1.getlocal(11);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject log_date_time_string$18(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyString.fromInterned("Return the current time formatted for logging.");
      var1.setline(488);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(489);
      var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 9);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(490);
      var3 = PyString.fromInterned("%02d/%3s/%04d %02d:%02d:%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("monthname").__getitem__(var1.getlocal(3)), var1.getlocal(2), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)}));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(492);
      var3 = var1.getlocal(11);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject address_string$19(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      PyString.fromInterned("Return the client address formatted for logging.\n\n        This version looks up the full hostname using gethostbyaddr(),\n        and tries to find a name that contains at least one dot.\n\n        ");
      var1.setline(508);
      PyObject var3 = var1.getlocal(0).__getattr__("client_address").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(509);
      var3 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$20(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyString.fromInterned("Test the HTTP request handler class.\n\n    This runs an HTTP server on port 8000 (or the first command line\n    argument).\n\n    ");
      var1.setline(599);
      PyObject var3;
      if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
         var1.setline(600);
         var3 = var1.getglobal("int").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(602);
         PyInteger var4 = Py.newInteger(8000);
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(603);
      PyTuple var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(3)});
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(605);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("protocol_version", var3);
      var3 = null;
      var1.setline(606);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getlocal(5).__getattr__("socket").__getattr__("getsockname").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(609);
      Py.printComma(PyString.fromInterned("Serving HTTP on"));
      Py.printComma(var1.getlocal(6).__getitem__(Py.newInteger(0)));
      Py.printComma(PyString.fromInterned("port"));
      Py.printComma(var1.getlocal(6).__getitem__(Py.newInteger(1)));
      Py.println(PyString.fromInterned("..."));
      var1.setline(610);
      var1.getlocal(5).__getattr__("serve_forever").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public BaseHTTPServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"html"};
      _quote_html$1 = Py.newCode(1, var2, var1, "_quote_html", 99, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPServer$2 = Py.newCode(0, var2, var1, "HTTPServer", 102, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port"};
      server_bind$3 = Py.newCode(1, var2, var1, "server_bind", 106, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      server_activate$4 = Py.newCode(1, var2, var1, "server_activate", 116, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseHTTPRequestHandler$5 = Py.newCode(0, var2, var1, "BaseHTTPRequestHandler", 125, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version", "requestline", "words", "command", "path", "base_version_number", "version_number", "conntype"};
      parse_request$6 = Py.newCode(1, var2, var1, "parse_request", 243, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mname", "method", "e"};
      handle_one_request$7 = Py.newCode(1, var2, var1, "handle_one_request", 312, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle$8 = Py.newCode(1, var2, var1, "handle", 347, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "message", "short", "long", "explain", "content"};
      send_error$9 = Py.newCode(3, var2, var1, "send_error", 355, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "message"};
      send_response$10 = Py.newCode(3, var2, var1, "send_response", 389, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "keyword", "value"};
      send_header$11 = Py.newCode(3, var2, var1, "send_header", 409, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_headers$12 = Py.newCode(1, var2, var1, "end_headers", 420, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "size"};
      log_request$13 = Py.newCode(3, var2, var1, "log_request", 425, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "args"};
      log_error$14 = Py.newCode(3, var2, var1, "log_error", 435, true, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "args"};
      log_message$15 = Py.newCode(3, var2, var1, "log_message", 449, true, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version_string$16 = Py.newCode(1, var2, var1, "version_string", 471, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timestamp", "year", "month", "day", "hh", "mm", "ss", "wd", "y", "z", "s"};
      date_time_string$17 = Py.newCode(2, var2, var1, "date_time_string", 475, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now", "year", "month", "day", "hh", "mm", "ss", "x", "y", "z", "s"};
      log_date_time_string$18 = Py.newCode(1, var2, var1, "log_date_time_string", 486, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      address_string$19 = Py.newCode(1, var2, var1, "address_string", 500, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"HandlerClass", "ServerClass", "protocol", "port", "server_address", "httpd", "sa"};
      test$20 = Py.newCode(3, var2, var1, "test", 590, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new BaseHTTPServer$py("BaseHTTPServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(BaseHTTPServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._quote_html$1(var2, var3);
         case 2:
            return this.HTTPServer$2(var2, var3);
         case 3:
            return this.server_bind$3(var2, var3);
         case 4:
            return this.server_activate$4(var2, var3);
         case 5:
            return this.BaseHTTPRequestHandler$5(var2, var3);
         case 6:
            return this.parse_request$6(var2, var3);
         case 7:
            return this.handle_one_request$7(var2, var3);
         case 8:
            return this.handle$8(var2, var3);
         case 9:
            return this.send_error$9(var2, var3);
         case 10:
            return this.send_response$10(var2, var3);
         case 11:
            return this.send_header$11(var2, var3);
         case 12:
            return this.end_headers$12(var2, var3);
         case 13:
            return this.log_request$13(var2, var3);
         case 14:
            return this.log_error$14(var2, var3);
         case 15:
            return this.log_message$15(var2, var3);
         case 16:
            return this.version_string$16(var2, var3);
         case 17:
            return this.date_time_string$17(var2, var3);
         case 18:
            return this.log_date_time_string$18(var2, var3);
         case 19:
            return this.address_string$19(var2, var3);
         case 20:
            return this.test$20(var2, var3);
         default:
            return null;
      }
   }
}

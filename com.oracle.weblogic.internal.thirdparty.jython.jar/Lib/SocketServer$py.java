import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("SocketServer.py")
public class SocketServer$py extends PyFunctionTable implements PyRunnable {
   static SocketServer$py self;
   static final PyCode f$0;
   static final PyCode _eintr_retry$1;
   static final PyCode BaseServer$2;
   static final PyCode __init__$3;
   static final PyCode server_activate$4;
   static final PyCode serve_forever$5;
   static final PyCode shutdown$6;
   static final PyCode handle_request$7;
   static final PyCode _handle_request_noblock$8;
   static final PyCode handle_timeout$9;
   static final PyCode verify_request$10;
   static final PyCode process_request$11;
   static final PyCode server_close$12;
   static final PyCode finish_request$13;
   static final PyCode shutdown_request$14;
   static final PyCode close_request$15;
   static final PyCode handle_error$16;
   static final PyCode TCPServer$17;
   static final PyCode __init__$18;
   static final PyCode server_bind$19;
   static final PyCode server_activate$20;
   static final PyCode server_close$21;
   static final PyCode fileno$22;
   static final PyCode get_request$23;
   static final PyCode shutdown_request$24;
   static final PyCode close_request$25;
   static final PyCode UDPServer$26;
   static final PyCode get_request$27;
   static final PyCode server_activate$28;
   static final PyCode shutdown_request$29;
   static final PyCode close_request$30;
   static final PyCode ForkingMixIn$31;
   static final PyCode collect_children$32;
   static final PyCode handle_timeout$33;
   static final PyCode process_request$34;
   static final PyCode ThreadingMixIn$35;
   static final PyCode process_request_thread$36;
   static final PyCode process_request$37;
   static final PyCode ForkingUDPServer$38;
   static final PyCode ForkingTCPServer$39;
   static final PyCode ThreadingUDPServer$40;
   static final PyCode ThreadingTCPServer$41;
   static final PyCode UnixStreamServer$42;
   static final PyCode UnixDatagramServer$43;
   static final PyCode ThreadingUnixStreamServer$44;
   static final PyCode ThreadingUnixDatagramServer$45;
   static final PyCode BaseRequestHandler$46;
   static final PyCode __init__$47;
   static final PyCode setup$48;
   static final PyCode handle$49;
   static final PyCode finish$50;
   static final PyCode StreamRequestHandler$51;
   static final PyCode setup$52;
   static final PyCode finish$53;
   static final PyCode DatagramRequestHandler$54;
   static final PyCode setup$55;
   static final PyCode finish$56;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generic socket server classes.\n\nThis module tries to capture the various aspects of defining a server:\n\nFor socket-based servers:\n\n- address family:\n        - AF_INET{,6}: IP (Internet Protocol) sockets (default)\n        - AF_UNIX: Unix domain sockets\n        - others, e.g. AF_DECNET are conceivable (see <socket.h>\n- socket type:\n        - SOCK_STREAM (reliable stream, e.g. TCP)\n        - SOCK_DGRAM (datagrams, e.g. UDP)\n\nFor request-based servers (including socket-based):\n\n- client address verification before further looking at the request\n        (This is actually a hook for any processing that needs to look\n         at the request before anything else, e.g. logging)\n- how to handle multiple requests:\n        - synchronous (one request is handled at a time)\n        - forking (each request is handled by a new process)\n        - threading (each request is handled by a new thread)\n\nThe classes in this module favor the server type that is simplest to\nwrite: a synchronous TCP/IP server.  This is bad class design, but\nsave some typing.  (There's also the issue that a deep class hierarchy\nslows down method lookups.)\n\nThere are five classes in an inheritance diagram, four of which represent\nsynchronous servers of four types:\n\n        +------------+\n        | BaseServer |\n        +------------+\n              |\n              v\n        +-----------+        +------------------+\n        | TCPServer |------->| UnixStreamServer |\n        +-----------+        +------------------+\n              |\n              v\n        +-----------+        +--------------------+\n        | UDPServer |------->| UnixDatagramServer |\n        +-----------+        +--------------------+\n\nNote that UnixDatagramServer derives from UDPServer, not from\nUnixStreamServer -- the only difference between an IP and a Unix\nstream server is the address family, which is simply repeated in both\nunix server classes.\n\nForking and threading versions of each type of server can be created\nusing the ForkingMixIn and ThreadingMixIn mix-in classes.  For\ninstance, a threading UDP server class is created as follows:\n\n        class ThreadingUDPServer(ThreadingMixIn, UDPServer): pass\n\nThe Mix-in class must come first, since it overrides a method defined\nin UDPServer! Setting the various member variables also changes\nthe behavior of the underlying server mechanism.\n\nTo implement a service, you must derive a class from\nBaseRequestHandler and redefine its handle() method.  You can then run\nvarious versions of the service by combining one of the server classes\nwith your request handler class.\n\nThe request handler class must be different for datagram or stream\nservices.  This can be hidden by using the request handler\nsubclasses StreamRequestHandler or DatagramRequestHandler.\n\nOf course, you still have to use your head!\n\nFor instance, it makes no sense to use a forking server if the service\ncontains state in memory that can be modified by requests (since the\nmodifications in the child process would never reach the initial state\nkept in the parent process and passed to each child).  In this case,\nyou can use a threading server, but you will probably have to use\nlocks to avoid two requests that come in nearly simultaneous to apply\nconflicting changes to the server state.\n\nOn the other hand, if you are building e.g. an HTTP server, where all\ndata is stored externally (e.g. in the file system), a synchronous\nclass will essentially render the service \"deaf\" while one request is\nbeing handled -- which may be for a very long time if a client is slow\nto read all the data it has requested.  Here a threading or forking\nserver is appropriate.\n\nIn some cases, it may be appropriate to process part of a request\nsynchronously, but to finish processing in a forked child depending on\nthe request data.  This can be implemented by using a synchronous\nserver and doing an explicit fork in the request handler class\nhandle() method.\n\nAnother approach to handling multiple simultaneous requests in an\nenvironment that supports neither threads nor fork (or where these are\ntoo expensive or inappropriate for the service) is to maintain an\nexplicit table of partially finished requests and to use select() to\ndecide which request to work on next (or whether to handle a new\nincoming request).  This is particularly important for stream services\nwhere each client can potentially be connected for a long time (if\nthreads or subprocesses cannot be used).\n\nFuture work:\n- Standard classes for Sun RPC (which uses either UDP or TCP)\n- Standard mix-in classes to implement various authentication\n  and encryption schemes\n- Standard framework for select-based multiplexing\n\nXXX Open problems:\n- What to do with out-of-band data?\n\nBaseServer:\n- split generic \"request\" functionality out into BaseServer class.\n  Copyright (C) 2000  Luke Kenneth Casson Leighton <lkcl@samba.org>\n\n  example: read entries from a SQL database (requires overriding\n  get_request() to return a table entry from the database).\n  entry is processed by a RequestHandlerClass.\n\n"));
      var1.setline(120);
      PyString.fromInterned("Generic socket server classes.\n\nThis module tries to capture the various aspects of defining a server:\n\nFor socket-based servers:\n\n- address family:\n        - AF_INET{,6}: IP (Internet Protocol) sockets (default)\n        - AF_UNIX: Unix domain sockets\n        - others, e.g. AF_DECNET are conceivable (see <socket.h>\n- socket type:\n        - SOCK_STREAM (reliable stream, e.g. TCP)\n        - SOCK_DGRAM (datagrams, e.g. UDP)\n\nFor request-based servers (including socket-based):\n\n- client address verification before further looking at the request\n        (This is actually a hook for any processing that needs to look\n         at the request before anything else, e.g. logging)\n- how to handle multiple requests:\n        - synchronous (one request is handled at a time)\n        - forking (each request is handled by a new process)\n        - threading (each request is handled by a new thread)\n\nThe classes in this module favor the server type that is simplest to\nwrite: a synchronous TCP/IP server.  This is bad class design, but\nsave some typing.  (There's also the issue that a deep class hierarchy\nslows down method lookups.)\n\nThere are five classes in an inheritance diagram, four of which represent\nsynchronous servers of four types:\n\n        +------------+\n        | BaseServer |\n        +------------+\n              |\n              v\n        +-----------+        +------------------+\n        | TCPServer |------->| UnixStreamServer |\n        +-----------+        +------------------+\n              |\n              v\n        +-----------+        +--------------------+\n        | UDPServer |------->| UnixDatagramServer |\n        +-----------+        +--------------------+\n\nNote that UnixDatagramServer derives from UDPServer, not from\nUnixStreamServer -- the only difference between an IP and a Unix\nstream server is the address family, which is simply repeated in both\nunix server classes.\n\nForking and threading versions of each type of server can be created\nusing the ForkingMixIn and ThreadingMixIn mix-in classes.  For\ninstance, a threading UDP server class is created as follows:\n\n        class ThreadingUDPServer(ThreadingMixIn, UDPServer): pass\n\nThe Mix-in class must come first, since it overrides a method defined\nin UDPServer! Setting the various member variables also changes\nthe behavior of the underlying server mechanism.\n\nTo implement a service, you must derive a class from\nBaseRequestHandler and redefine its handle() method.  You can then run\nvarious versions of the service by combining one of the server classes\nwith your request handler class.\n\nThe request handler class must be different for datagram or stream\nservices.  This can be hidden by using the request handler\nsubclasses StreamRequestHandler or DatagramRequestHandler.\n\nOf course, you still have to use your head!\n\nFor instance, it makes no sense to use a forking server if the service\ncontains state in memory that can be modified by requests (since the\nmodifications in the child process would never reach the initial state\nkept in the parent process and passed to each child).  In this case,\nyou can use a threading server, but you will probably have to use\nlocks to avoid two requests that come in nearly simultaneous to apply\nconflicting changes to the server state.\n\nOn the other hand, if you are building e.g. an HTTP server, where all\ndata is stored externally (e.g. in the file system), a synchronous\nclass will essentially render the service \"deaf\" while one request is\nbeing handled -- which may be for a very long time if a client is slow\nto read all the data it has requested.  Here a threading or forking\nserver is appropriate.\n\nIn some cases, it may be appropriate to process part of a request\nsynchronously, but to finish processing in a forked child depending on\nthe request data.  This can be implemented by using a synchronous\nserver and doing an explicit fork in the request handler class\nhandle() method.\n\nAnother approach to handling multiple simultaneous requests in an\nenvironment that supports neither threads nor fork (or where these are\ntoo expensive or inappropriate for the service) is to maintain an\nexplicit table of partially finished requests and to use select() to\ndecide which request to work on next (or whether to handle a new\nincoming request).  This is particularly important for stream services\nwhere each client can potentially be connected for a long time (if\nthreads or subprocesses cannot be used).\n\nFuture work:\n- Standard classes for Sun RPC (which uses either UDP or TCP)\n- Standard mix-in classes to implement various authentication\n  and encryption schemes\n- Standard framework for select-based multiplexing\n\nXXX Open problems:\n- What to do with out-of-band data?\n\nBaseServer:\n- split generic \"request\" functionality out into BaseServer class.\n  Copyright (C) 2000  Luke Kenneth Casson Leighton <lkcl@samba.org>\n\n  example: read entries from a SQL database (requires overriding\n  get_request() to return a table entry from the database).\n  entry is processed by a RequestHandlerClass.\n\n");
      var1.setline(129);
      PyString var3 = PyString.fromInterned("0.4");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(132);
      PyObject var6 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var6);
      var3 = null;
      var1.setline(133);
      var6 = imp.importOne("select", var1, -1);
      var1.setlocal("select", var6);
      var3 = null;
      var1.setline(134);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(135);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(136);
      var6 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var6);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(138);
         var6 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(140);
         var4 = imp.importOneAs("dummy_threading", var1, -1);
         var1.setlocal("threading", var4);
         var4 = null;
      }

      var1.setline(142);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("TCPServer"), PyString.fromInterned("UDPServer"), PyString.fromInterned("ForkingUDPServer"), PyString.fromInterned("ForkingTCPServer"), PyString.fromInterned("ThreadingUDPServer"), PyString.fromInterned("ThreadingTCPServer"), PyString.fromInterned("BaseRequestHandler"), PyString.fromInterned("StreamRequestHandler"), PyString.fromInterned("DatagramRequestHandler"), PyString.fromInterned("ThreadingMixIn"), PyString.fromInterned("ForkingMixIn")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(146);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("socket"), (PyObject)PyString.fromInterned("AF_UNIX")).__nonzero__()) {
         var1.setline(147);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("UnixStreamServer"), PyString.fromInterned("UnixDatagramServer"), PyString.fromInterned("ThreadingUnixStreamServer"), PyString.fromInterned("ThreadingUnixDatagramServer")})));
      }

      var1.setline(151);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, _eintr_retry$1, PyString.fromInterned("restart a system call interrupted by EINTR"));
      var1.setlocal("_eintr_retry", var10);
      var3 = null;
      var1.setline(160);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("BaseServer", var9, BaseServer$2);
      var1.setlocal("BaseServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(358);
      var9 = new PyObject[]{var1.getname("BaseServer")};
      var4 = Py.makeClass("TCPServer", var9, TCPServer$17);
      var1.setlocal("TCPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(488);
      var9 = new PyObject[]{var1.getname("TCPServer")};
      var4 = Py.makeClass("UDPServer", var9, UDPServer$26);
      var1.setlocal("UDPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(514);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("ForkingMixIn", var9, ForkingMixIn$31);
      var1.setlocal("ForkingMixIn", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(587);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("ThreadingMixIn", var9, ThreadingMixIn$35);
      var1.setlocal("ThreadingMixIn", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(615);
      var9 = new PyObject[]{var1.getname("ForkingMixIn"), var1.getname("UDPServer")};
      var4 = Py.makeClass("ForkingUDPServer", var9, ForkingUDPServer$38);
      var1.setlocal("ForkingUDPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(616);
      var9 = new PyObject[]{var1.getname("ForkingMixIn"), var1.getname("TCPServer")};
      var4 = Py.makeClass("ForkingTCPServer", var9, ForkingTCPServer$39);
      var1.setlocal("ForkingTCPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(618);
      var9 = new PyObject[]{var1.getname("ThreadingMixIn"), var1.getname("UDPServer")};
      var4 = Py.makeClass("ThreadingUDPServer", var9, ThreadingUDPServer$40);
      var1.setlocal("ThreadingUDPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(619);
      var9 = new PyObject[]{var1.getname("ThreadingMixIn"), var1.getname("TCPServer")};
      var4 = Py.makeClass("ThreadingTCPServer", var9, ThreadingTCPServer$41);
      var1.setlocal("ThreadingTCPServer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(621);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("socket"), (PyObject)PyString.fromInterned("AF_UNIX")).__nonzero__()) {
         var1.setline(623);
         var9 = new PyObject[]{var1.getname("TCPServer")};
         var4 = Py.makeClass("UnixStreamServer", var9, UnixStreamServer$42);
         var1.setlocal("UnixStreamServer", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(626);
         var9 = new PyObject[]{var1.getname("UDPServer")};
         var4 = Py.makeClass("UnixDatagramServer", var9, UnixDatagramServer$43);
         var1.setlocal("UnixDatagramServer", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(629);
         var9 = new PyObject[]{var1.getname("ThreadingMixIn"), var1.getname("UnixStreamServer")};
         var4 = Py.makeClass("ThreadingUnixStreamServer", var9, ThreadingUnixStreamServer$44);
         var1.setlocal("ThreadingUnixStreamServer", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(631);
         var9 = new PyObject[]{var1.getname("ThreadingMixIn"), var1.getname("UnixDatagramServer")};
         var4 = Py.makeClass("ThreadingUnixDatagramServer", var9, ThreadingUnixDatagramServer$45);
         var1.setlocal("ThreadingUnixDatagramServer", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
      }

      var1.setline(633);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("BaseRequestHandler", var9, BaseRequestHandler$46);
      var1.setlocal("BaseRequestHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(679);
      var9 = new PyObject[]{var1.getname("BaseRequestHandler")};
      var4 = Py.makeClass("StreamRequestHandler", var9, StreamRequestHandler$51);
      var1.setlocal("StreamRequestHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(722);
      var9 = new PyObject[]{var1.getname("BaseRequestHandler")};
      var4 = Py.makeClass("DatagramRequestHandler", var9, DatagramRequestHandler$54);
      var1.setlocal("DatagramRequestHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _eintr_retry$1(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("restart a system call interrupted by EINTR");

      while(true) {
         var1.setline(153);
         if (!var1.getglobal("True").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject var10000;
         try {
            var1.setline(155);
            var10000 = var1.getlocal(0);
            PyObject[] var3 = Py.EmptyObjects;
            String[] var8 = new String[0];
            var10000 = var10000._callextra(var3, var8, var1.getlocal(1), (PyObject)null);
            var3 = null;
            PyObject var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         } catch (Throwable var6) {
            PyException var4 = Py.setException(var6, var1);
            if (!var4.match(new PyTuple(new PyObject[]{var1.getglobal("OSError"), var1.getglobal("select").__getattr__("error")}))) {
               throw var4;
            }

            PyObject var5 = var4.value;
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(157);
            var5 = var1.getlocal(2).__getattr__("args").__getitem__(Py.newInteger(0));
            var10000 = var5._ne(var1.getglobal("errno").__getattr__("EINTR"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(158);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject BaseServer$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for server classes.\n\n    Methods for the caller:\n\n    - __init__(server_address, RequestHandlerClass)\n    - serve_forever(poll_interval=0.5)\n    - shutdown()\n    - handle_request()  # if you do not use serve_forever()\n    - fileno() -> int   # for select()\n\n    Methods that may be overridden:\n\n    - server_bind()\n    - server_activate()\n    - get_request() -> request, client_address\n    - handle_timeout()\n    - verify_request(request, client_address)\n    - server_close()\n    - process_request(request, client_address)\n    - shutdown_request(request)\n    - close_request(request)\n    - handle_error()\n\n    Methods for derived classes:\n\n    - finish_request(request, client_address)\n\n    Class variables that may be overridden by derived classes or\n    instances:\n\n    - timeout\n    - address_family\n    - socket_type\n    - allow_reuse_address\n\n    Instance variables:\n\n    - RequestHandlerClass\n    - socket\n\n    "));
      var1.setline(202);
      PyString.fromInterned("Base class for server classes.\n\n    Methods for the caller:\n\n    - __init__(server_address, RequestHandlerClass)\n    - serve_forever(poll_interval=0.5)\n    - shutdown()\n    - handle_request()  # if you do not use serve_forever()\n    - fileno() -> int   # for select()\n\n    Methods that may be overridden:\n\n    - server_bind()\n    - server_activate()\n    - get_request() -> request, client_address\n    - handle_timeout()\n    - verify_request(request, client_address)\n    - server_close()\n    - process_request(request, client_address)\n    - shutdown_request(request)\n    - close_request(request)\n    - handle_error()\n\n    Methods for derived classes:\n\n    - finish_request(request, client_address)\n\n    Class variables that may be overridden by derived classes or\n    instances:\n\n    - timeout\n    - address_family\n    - socket_type\n    - allow_reuse_address\n\n    Instance variables:\n\n    - RequestHandlerClass\n    - socket\n\n    ");
      var1.setline(204);
      PyObject var3 = var1.getname("None");
      var1.setlocal("timeout", var3);
      var3 = null;
      var1.setline(206);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, PyString.fromInterned("Constructor.  May be extended, do not override."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(213);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, server_activate$4, PyString.fromInterned("Called by constructor to activate the server.\n\n        May be overridden.\n\n        "));
      var1.setlocal("server_activate", var5);
      var3 = null;
      var1.setline(221);
      var4 = new PyObject[]{Py.newFloat(0.5)};
      var5 = new PyFunction(var1.f_globals, var4, serve_forever$5, PyString.fromInterned("Handle one request at a time until shutdown.\n\n        Polls for shutdown every poll_interval seconds. Ignores\n        self.timeout. If you need to do periodic tasks, do them in\n        another thread.\n        "));
      var1.setlocal("serve_forever", var5);
      var3 = null;
      var1.setline(243);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, shutdown$6, PyString.fromInterned("Stops the serve_forever loop.\n\n        Blocks until the loop has finished. This must be called while\n        serve_forever() is running in another thread, or it will\n        deadlock.\n        "));
      var1.setlocal("shutdown", var5);
      var3 = null;
      var1.setline(264);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_request$7, PyString.fromInterned("Handle one request, possibly blocking.\n\n        Respects self.timeout.\n        "));
      var1.setlocal("handle_request", var5);
      var3 = null;
      var1.setline(282);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _handle_request_noblock$8, PyString.fromInterned("Handle one request, without blocking.\n\n        I assume that select.select has returned that the socket is\n        readable before this function was called, so there should be\n        no risk of blocking in get_request().\n        "));
      var1.setlocal("_handle_request_noblock", var5);
      var3 = null;
      var1.setline(300);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_timeout$9, PyString.fromInterned("Called if no new request arrives within self.timeout.\n\n        Overridden by ForkingMixIn.\n        "));
      var1.setlocal("handle_timeout", var5);
      var3 = null;
      var1.setline(307);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, verify_request$10, PyString.fromInterned("Verify the request.  May be overridden.\n\n        Return True if we should proceed with this request.\n\n        "));
      var1.setlocal("verify_request", var5);
      var3 = null;
      var1.setline(315);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, process_request$11, PyString.fromInterned("Call finish_request.\n\n        Overridden by ForkingMixIn and ThreadingMixIn.\n\n        "));
      var1.setlocal("process_request", var5);
      var3 = null;
      var1.setline(324);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, server_close$12, PyString.fromInterned("Called to clean-up the server.\n\n        May be overridden.\n\n        "));
      var1.setlocal("server_close", var5);
      var3 = null;
      var1.setline(332);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finish_request$13, PyString.fromInterned("Finish one request by instantiating RequestHandlerClass."));
      var1.setlocal("finish_request", var5);
      var3 = null;
      var1.setline(336);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, shutdown_request$14, PyString.fromInterned("Called to shutdown and close an individual request."));
      var1.setlocal("shutdown_request", var5);
      var3 = null;
      var1.setline(340);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close_request$15, PyString.fromInterned("Called to clean up an individual request."));
      var1.setlocal("close_request", var5);
      var3 = null;
      var1.setline(344);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_error$16, PyString.fromInterned("Handle an error gracefully.  May be overridden.\n\n        The default is to print a traceback and continue.\n\n        "));
      var1.setlocal("handle_error", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyString.fromInterned("Constructor.  May be extended, do not override.");
      var1.setline(208);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("server_address", var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("RequestHandlerClass", var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getglobal("threading").__getattr__("Event").__call__(var2);
      var1.getlocal(0).__setattr__("_BaseServer__is_shut_down", var3);
      var3 = null;
      var1.setline(211);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_BaseServer__shutdown_request", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_activate$4(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyString.fromInterned("Called by constructor to activate the server.\n\n        May be overridden.\n\n        ");
      var1.setline(219);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject serve_forever$5(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyString.fromInterned("Handle one request at a time until shutdown.\n\n        Polls for shutdown every poll_interval seconds. Ignores\n        self.timeout. If you need to do periodic tasks, do them in\n        another thread.\n        ");
      var1.setline(228);
      var1.getlocal(0).__getattr__("_BaseServer__is_shut_down").__getattr__("clear").__call__(var2);
      Object var3 = null;

      PyObject var4;
      try {
         while(true) {
            var1.setline(230);
            if (!var1.getlocal(0).__getattr__("_BaseServer__shutdown_request").__not__().__nonzero__()) {
               break;
            }

            var1.setline(235);
            PyObject var10000 = var1.getglobal("_eintr_retry");
            PyObject[] var8 = new PyObject[]{var1.getglobal("select").__getattr__("select"), new PyList(new PyObject[]{var1.getlocal(0)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), var1.getlocal(1)};
            var4 = var10000.__call__(var2, var8);
            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(237);
            var4 = var1.getlocal(0);
            var10000 = var4._in(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(238);
               var1.getlocal(0).__getattr__("_handle_request_noblock").__call__(var2);
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(240);
         var4 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_BaseServer__shutdown_request", var4);
         var4 = null;
         var1.setline(241);
         var1.getlocal(0).__getattr__("_BaseServer__is_shut_down").__getattr__("set").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(240);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_BaseServer__shutdown_request", var4);
      var4 = null;
      var1.setline(241);
      var1.getlocal(0).__getattr__("_BaseServer__is_shut_down").__getattr__("set").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$6(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyString.fromInterned("Stops the serve_forever loop.\n\n        Blocks until the loop has finished. This must be called while\n        serve_forever() is running in another thread, or it will\n        deadlock.\n        ");
      var1.setline(250);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_BaseServer__shutdown_request", var3);
      var3 = null;
      var1.setline(251);
      var1.getlocal(0).__getattr__("_BaseServer__is_shut_down").__getattr__("wait").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_request$7(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("Handle one request, possibly blocking.\n\n        Respects self.timeout.\n        ");
      var1.setline(271);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("gettimeout").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(272);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(273);
         var3 = var1.getlocal(0).__getattr__("timeout");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(274);
         var3 = var1.getlocal(0).__getattr__("timeout");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(275);
            var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("timeout"));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(276);
      var10000 = var1.getglobal("_eintr_retry");
      PyObject[] var4 = new PyObject[]{var1.getglobal("select").__getattr__("select"), new PyList(new PyObject[]{var1.getlocal(0)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), var1.getlocal(1)};
      var3 = var10000.__call__(var2, var4);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(277);
      if (var1.getlocal(2).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
         var1.setline(278);
         var1.getlocal(0).__getattr__("handle_timeout").__call__(var2);
         var1.setline(279);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(280);
         var1.getlocal(0).__getattr__("_handle_request_noblock").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _handle_request_noblock$8(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyString.fromInterned("Handle one request, without blocking.\n\n        I assume that select.select has returned that the socket is\n        readable before this function was called, so there should be\n        no risk of blocking in get_request().\n        ");

      PyException var3;
      try {
         var1.setline(290);
         PyObject var8 = var1.getlocal(0).__getattr__("get_request").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var8, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("socket").__getattr__("error"))) {
            var1.setline(292);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(293);
      if (var1.getlocal(0).__getattr__("verify_request").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
         try {
            var1.setline(295);
            var1.getlocal(0).__getattr__("process_request").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(297);
            var1.getlocal(0).__getattr__("handle_error").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setline(298);
            var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_timeout$9(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyString.fromInterned("Called if no new request arrives within self.timeout.\n\n        Overridden by ForkingMixIn.\n        ");
      var1.setline(305);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject verify_request$10(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyString.fromInterned("Verify the request.  May be overridden.\n\n        Return True if we should proceed with this request.\n\n        ");
      var1.setline(313);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject process_request$11(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyString.fromInterned("Call finish_request.\n\n        Overridden by ForkingMixIn and ThreadingMixIn.\n\n        ");
      var1.setline(321);
      var1.getlocal(0).__getattr__("finish_request").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(322);
      var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_close$12(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyString.fromInterned("Called to clean-up the server.\n\n        May be overridden.\n\n        ");
      var1.setline(330);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_request$13(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      PyString.fromInterned("Finish one request by instantiating RequestHandlerClass.");
      var1.setline(334);
      var1.getlocal(0).__getattr__("RequestHandlerClass").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown_request$14(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyString.fromInterned("Called to shutdown and close an individual request.");
      var1.setline(338);
      var1.getlocal(0).__getattr__("close_request").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close_request$15(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyString.fromInterned("Called to clean up an individual request.");
      var1.setline(342);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_error$16(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyString.fromInterned("Handle an error gracefully.  May be overridden.\n\n        The default is to print a traceback and continue.\n\n        ");
      var1.setline(350);
      Py.println(PyString.fromInterned("-")._mul(Py.newInteger(40)));
      var1.setline(351);
      Py.printComma(PyString.fromInterned("Exception happened during processing of request from"));
      var1.setline(352);
      Py.println(var1.getlocal(2));
      var1.setline(353);
      PyObject var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(354);
      var1.getlocal(3).__getattr__("print_exc").__call__(var2);
      var1.setline(355);
      Py.println(PyString.fromInterned("-")._mul(Py.newInteger(40)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TCPServer$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for various socket-based server classes.\n\n    Defaults to synchronous IP stream (i.e., TCP).\n\n    Methods for the caller:\n\n    - __init__(server_address, RequestHandlerClass, bind_and_activate=True)\n    - serve_forever(poll_interval=0.5)\n    - shutdown()\n    - handle_request()  # if you don't use serve_forever()\n    - fileno() -> int   # for select()\n\n    Methods that may be overridden:\n\n    - server_bind()\n    - server_activate()\n    - get_request() -> request, client_address\n    - handle_timeout()\n    - verify_request(request, client_address)\n    - process_request(request, client_address)\n    - shutdown_request(request)\n    - close_request(request)\n    - handle_error()\n\n    Methods for derived classes:\n\n    - finish_request(request, client_address)\n\n    Class variables that may be overridden by derived classes or\n    instances:\n\n    - timeout\n    - address_family\n    - socket_type\n    - request_queue_size (only for stream sockets)\n    - allow_reuse_address\n\n    Instance variables:\n\n    - server_address\n    - RequestHandlerClass\n    - socket\n\n    "));
      var1.setline(403);
      PyString.fromInterned("Base class for various socket-based server classes.\n\n    Defaults to synchronous IP stream (i.e., TCP).\n\n    Methods for the caller:\n\n    - __init__(server_address, RequestHandlerClass, bind_and_activate=True)\n    - serve_forever(poll_interval=0.5)\n    - shutdown()\n    - handle_request()  # if you don't use serve_forever()\n    - fileno() -> int   # for select()\n\n    Methods that may be overridden:\n\n    - server_bind()\n    - server_activate()\n    - get_request() -> request, client_address\n    - handle_timeout()\n    - verify_request(request, client_address)\n    - process_request(request, client_address)\n    - shutdown_request(request)\n    - close_request(request)\n    - handle_error()\n\n    Methods for derived classes:\n\n    - finish_request(request, client_address)\n\n    Class variables that may be overridden by derived classes or\n    instances:\n\n    - timeout\n    - address_family\n    - socket_type\n    - request_queue_size (only for stream sockets)\n    - allow_reuse_address\n\n    Instance variables:\n\n    - server_address\n    - RequestHandlerClass\n    - socket\n\n    ");
      var1.setline(405);
      PyObject var3 = var1.getname("socket").__getattr__("AF_INET");
      var1.setlocal("address_family", var3);
      var3 = null;
      var1.setline(407);
      var3 = var1.getname("socket").__getattr__("SOCK_STREAM");
      var1.setlocal("socket_type", var3);
      var3 = null;
      var1.setline(409);
      PyInteger var4 = Py.newInteger(5);
      var1.setlocal("request_queue_size", var4);
      var3 = null;
      var1.setline(411);
      var3 = var1.getname("False");
      var1.setlocal("allow_reuse_address", var3);
      var3 = null;
      var1.setline(413);
      PyObject[] var5 = new PyObject[]{var1.getname("True")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$18, PyString.fromInterned("Constructor.  May be extended, do not override."));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(422);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, server_bind$19, PyString.fromInterned("Called by constructor to bind the socket.\n\n        May be overridden.\n\n        "));
      var1.setlocal("server_bind", var6);
      var3 = null;
      var1.setline(438);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, server_activate$20, PyString.fromInterned("Called by constructor to activate the server.\n\n        May be overridden.\n\n        "));
      var1.setlocal("server_activate", var6);
      var3 = null;
      var1.setline(449);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, server_close$21, PyString.fromInterned("Called to clean-up the server.\n\n        May be overridden.\n\n        "));
      var1.setlocal("server_close", var6);
      var3 = null;
      var1.setline(457);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fileno$22, PyString.fromInterned("Return socket file number.\n\n        Interface required by select().\n\n        "));
      var1.setlocal("fileno", var6);
      var3 = null;
      var1.setline(465);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_request$23, PyString.fromInterned("Get the request and client address from the socket.\n\n        May be overridden.\n\n        "));
      var1.setlocal("get_request", var6);
      var3 = null;
      var1.setline(473);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, shutdown_request$24, PyString.fromInterned("Called to shutdown and close an individual request."));
      var1.setlocal("shutdown_request", var6);
      var3 = null;
      var1.setline(483);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close_request$25, PyString.fromInterned("Called to clean up an individual request."));
      var1.setlocal("close_request", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyString.fromInterned("Constructor.  May be extended, do not override.");
      var1.setline(415);
      var1.getglobal("BaseServer").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(416);
      PyObject var3 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getlocal(0).__getattr__("address_family"), var1.getlocal(0).__getattr__("socket_type"));
      var1.getlocal(0).__setattr__("socket", var3);
      var3 = null;
      var1.setline(418);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(419);
         var1.getlocal(0).__getattr__("server_bind").__call__(var2);
         var1.setline(420);
         var1.getlocal(0).__getattr__("server_activate").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_bind$19(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyString.fromInterned("Called by constructor to bind the socket.\n\n        May be overridden.\n\n        ");
      var1.setline(428);
      if (var1.getlocal(0).__getattr__("allow_reuse_address").__nonzero__()) {
         var1.setline(429);
         var1.getlocal(0).__getattr__("socket").__getattr__("setsockopt").__call__((ThreadState)var2, var1.getglobal("socket").__getattr__("SOL_SOCKET"), (PyObject)var1.getglobal("socket").__getattr__("SO_REUSEADDR"), (PyObject)Py.newInteger(1));
      }

      var1.setline(430);
      var1.getlocal(0).__getattr__("socket").__getattr__("bind").__call__(var2, var1.getlocal(0).__getattr__("server_address"));

      PyException var3;
      try {
         var1.setline(432);
         PyObject var5 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockname").__call__(var2);
         var1.getlocal(0).__setattr__("server_address", var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var3;
         }

         var1.setline(436);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_activate$20(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyString.fromInterned("Called by constructor to activate the server.\n\n        May be overridden.\n\n        ");
      var1.setline(444);
      var1.getlocal(0).__getattr__("socket").__getattr__("listen").__call__(var2, var1.getlocal(0).__getattr__("request_queue_size"));
      var1.setline(447);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockname").__call__(var2);
      var1.getlocal(0).__setattr__("server_address", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject server_close$21(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyString.fromInterned("Called to clean-up the server.\n\n        May be overridden.\n\n        ");
      var1.setline(455);
      var1.getlocal(0).__getattr__("socket").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$22(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyString.fromInterned("Return socket file number.\n\n        Interface required by select().\n\n        ");
      var1.setline(463);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_request$23(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("Get the request and client address from the socket.\n\n        May be overridden.\n\n        ");
      var1.setline(471);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("accept").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shutdown_request$24(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyString.fromInterned("Called to shutdown and close an individual request.");

      try {
         var1.setline(478);
         var1.getlocal(1).__getattr__("shutdown").__call__(var2, var1.getglobal("socket").__getattr__("SHUT_WR"));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var3;
         }

         var1.setline(480);
      }

      var1.setline(481);
      var1.getlocal(0).__getattr__("close_request").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close_request$25(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyString.fromInterned("Called to clean up an individual request.");
      var1.setline(485);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UDPServer$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("UDP server class."));
      var1.setline(490);
      PyString.fromInterned("UDP server class.");
      var1.setline(492);
      PyObject var3 = var1.getname("False");
      var1.setlocal("allow_reuse_address", var3);
      var3 = null;
      var1.setline(494);
      var3 = var1.getname("socket").__getattr__("SOCK_DGRAM");
      var1.setlocal("socket_type", var3);
      var3 = null;
      var1.setline(496);
      PyInteger var4 = Py.newInteger(8192);
      var1.setlocal("max_packet_size", var4);
      var3 = null;
      var1.setline(498);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, get_request$27, (PyObject)null);
      var1.setlocal("get_request", var6);
      var3 = null;
      var1.setline(502);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, server_activate$28, (PyObject)null);
      var1.setlocal("server_activate", var6);
      var3 = null;
      var1.setline(506);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, shutdown_request$29, (PyObject)null);
      var1.setlocal("shutdown_request", var6);
      var3 = null;
      var1.setline(510);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close_request$30, (PyObject)null);
      var1.setlocal("close_request", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get_request$27(PyFrame var1, ThreadState var2) {
      var1.setline(499);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("recvfrom").__call__(var2, var1.getlocal(0).__getattr__("max_packet_size"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(500);
      PyTuple var6 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("socket")}), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject server_activate$28(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown_request$29(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      var1.getlocal(0).__getattr__("close_request").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close_request$30(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ForkingMixIn$31(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class to handle each request in a new process."));
      var1.setline(516);
      PyString.fromInterned("Mix-in class to handle each request in a new process.");
      var1.setline(518);
      PyInteger var3 = Py.newInteger(300);
      var1.setlocal("timeout", var3);
      var3 = null;
      var1.setline(519);
      PyObject var4 = var1.getname("None");
      var1.setlocal("active_children", var4);
      var3 = null;
      var1.setline(520);
      var3 = Py.newInteger(40);
      var1.setlocal("max_children", var3);
      var3 = null;
      var1.setline(522);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, collect_children$32, PyString.fromInterned("Internal routine to wait for children that have exited."));
      var1.setlocal("collect_children", var6);
      var3 = null;
      var1.setline(554);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_timeout$33, PyString.fromInterned("Wait for zombies after self.timeout seconds of inactivity.\n\n        May be extended, do not override.\n        "));
      var1.setlocal("handle_timeout", var6);
      var3 = null;
      var1.setline(561);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, process_request$34, PyString.fromInterned("Fork a new subprocess to process the request."));
      var1.setlocal("process_request", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject collect_children$32(PyFrame var1, ThreadState var2) {
      var1.setline(523);
      PyString.fromInterned("Internal routine to wait for children that have exited.");
      var1.setline(524);
      PyObject var3 = var1.getlocal(0).__getattr__("active_children");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(524);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         while(true) {
            var1.setline(525);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("active_children"));
            var10000 = var3._ge(var1.getlocal(0).__getattr__("max_children"));
            var3 = null;
            PyObject var4;
            PyObject var5;
            if (!var10000.__nonzero__()) {
               var1.setline(542);
               var3 = var1.getlocal(0).__getattr__("active_children").__iter__();

               while(true) {
                  var1.setline(542);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(3, var4);

                  PyObject var6;
                  PyException var13;
                  try {
                     var1.setline(544);
                     var5 = var1.getglobal("os").__getattr__("waitpid").__call__(var2, var1.getlocal(3), var1.getglobal("os").__getattr__("WNOHANG"));
                     PyObject[] var14 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var14[0];
                     var1.setlocal(1, var7);
                     var7 = null;
                     var7 = var14[1];
                     var1.setlocal(2, var7);
                     var7 = null;
                     var5 = null;
                  } catch (Throwable var9) {
                     var13 = Py.setException(var9, var1);
                     if (!var13.match(var1.getglobal("os").__getattr__("error"))) {
                        throw var13;
                     }

                     var1.setline(546);
                     var6 = var1.getglobal("None");
                     var1.setlocal(1, var6);
                     var6 = null;
                  }

                  var1.setline(547);
                  if (!var1.getlocal(1).__not__().__nonzero__()) {
                     try {
                        var1.setline(549);
                        var1.getlocal(0).__getattr__("active_children").__getattr__("remove").__call__(var2, var1.getlocal(1));
                     } catch (Throwable var8) {
                        var13 = Py.setException(var8, var1);
                        if (var13.match(var1.getglobal("ValueError"))) {
                           var6 = var13.value;
                           var1.setlocal(4, var6);
                           var6 = null;
                           var1.setline(551);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s. x=%d and list=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("message"), var1.getlocal(1), var1.getlocal(0).__getattr__("active_children")}))));
                        }

                        throw var13;
                     }
                  }
               }
            }

            try {
               var1.setline(531);
               var3 = var1.getglobal("os").__getattr__("waitpid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(0));
               PyObject[] var12 = Py.unpackSequence(var3, 2);
               var5 = var12[0];
               var1.setlocal(1, var5);
               var5 = null;
               var5 = var12[1];
               var1.setlocal(2, var5);
               var5 = null;
               var3 = null;
            } catch (Throwable var10) {
               PyException var11 = Py.setException(var10, var1);
               if (!var11.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var11;
               }

               var1.setline(533);
               var4 = var1.getglobal("None");
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(534);
            var3 = var1.getlocal(1);
            var10000 = var3._notin(var1.getlocal(0).__getattr__("active_children"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(535);
               var1.getlocal(0).__getattr__("active_children").__getattr__("remove").__call__(var2, var1.getlocal(1));
            }
         }
      }
   }

   public PyObject handle_timeout$33(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyString.fromInterned("Wait for zombies after self.timeout seconds of inactivity.\n\n        May be extended, do not override.\n        ");
      var1.setline(559);
      var1.getlocal(0).__getattr__("collect_children").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process_request$34(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyString.fromInterned("Fork a new subprocess to process the request.");
      var1.setline(563);
      var1.getlocal(0).__getattr__("collect_children").__call__(var2);
      var1.setline(564);
      PyObject var3 = var1.getglobal("os").__getattr__("fork").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(565);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(567);
         var3 = var1.getlocal(0).__getattr__("active_children");
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(568);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"active_children", var7);
            var3 = null;
         }

         var1.setline(569);
         var1.getlocal(0).__getattr__("active_children").__getattr__("append").__call__(var2, var1.getlocal(3));
         var1.setline(570);
         var1.getlocal(0).__getattr__("close_request").__call__(var2, var1.getlocal(1));
         var1.setline(571);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         try {
            var1.setline(576);
            var1.getlocal(0).__getattr__("finish_request").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setline(577);
            var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
            var1.setline(578);
            var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            Object var4 = null;

            try {
               var1.setline(581);
               var1.getlocal(0).__getattr__("handle_error").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.setline(582);
               var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
            } catch (Throwable var5) {
               Py.addTraceback(var5, var1);
               var1.setline(584);
               var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               throw (Throwable)var5;
            }

            var1.setline(584);
            var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject ThreadingMixIn$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class to handle each request in a new thread."));
      var1.setline(588);
      PyString.fromInterned("Mix-in class to handle each request in a new thread.");
      var1.setline(592);
      PyObject var3 = var1.getname("False");
      var1.setlocal("daemon_threads", var3);
      var3 = null;
      var1.setline(594);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, process_request_thread$36, PyString.fromInterned("Same as in BaseServer but as a thread.\n\n        In addition, exception handling is done here.\n\n        "));
      var1.setlocal("process_request_thread", var5);
      var3 = null;
      var1.setline(607);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, process_request$37, PyString.fromInterned("Start a new thread to process the request."));
      var1.setlocal("process_request", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject process_request_thread$36(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      PyString.fromInterned("Same as in BaseServer but as a thread.\n\n        In addition, exception handling is done here.\n\n        ");

      try {
         var1.setline(601);
         var1.getlocal(0).__getattr__("finish_request").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(602);
         var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(604);
         var1.getlocal(0).__getattr__("handle_error").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(605);
         var1.getlocal(0).__getattr__("shutdown_request").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process_request$37(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyString.fromInterned("Start a new thread to process the request.");
      var1.setline(609);
      PyObject var10000 = var1.getglobal("threading").__getattr__("Thread");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("process_request_thread"), new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})};
      String[] var4 = new String[]{"target", "args"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(611);
      var5 = var1.getlocal(0).__getattr__("daemon_threads");
      var1.getlocal(3).__setattr__("daemon", var5);
      var3 = null;
      var1.setline(612);
      var1.getlocal(3).__getattr__("start").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ForkingUDPServer$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(615);
      return var1.getf_locals();
   }

   public PyObject ForkingTCPServer$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(616);
      return var1.getf_locals();
   }

   public PyObject ThreadingUDPServer$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(618);
      return var1.getf_locals();
   }

   public PyObject ThreadingTCPServer$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(619);
      return var1.getf_locals();
   }

   public PyObject UnixStreamServer$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(624);
      PyObject var3 = var1.getname("socket").__getattr__("AF_UNIX");
      var1.setlocal("address_family", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject UnixDatagramServer$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(627);
      PyObject var3 = var1.getname("socket").__getattr__("AF_UNIX");
      var1.setlocal("address_family", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ThreadingUnixStreamServer$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(629);
      return var1.getf_locals();
   }

   public PyObject ThreadingUnixDatagramServer$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(631);
      return var1.getf_locals();
   }

   public PyObject BaseRequestHandler$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for request handler classes.\n\n    This class is instantiated for each request to be handled.  The\n    constructor sets the instance variables request, client_address\n    and server, and then calls the handle() method.  To implement a\n    specific service, all you need to do is to derive a class which\n    defines a handle() method.\n\n    The handle() method can find the request as self.request, the\n    client address as self.client_address, and the server (in case it\n    needs access to per-server information) as self.server.  Since a\n    separate instance is created for each request, the handle() method\n    can define arbitrary other instance variariables.\n\n    "));
      var1.setline(649);
      PyString.fromInterned("Base class for request handler classes.\n\n    This class is instantiated for each request to be handled.  The\n    constructor sets the instance variables request, client_address\n    and server, and then calls the handle() method.  To implement a\n    specific service, all you need to do is to derive a class which\n    defines a handle() method.\n\n    The handle() method can find the request as self.request, the\n    client address as self.client_address, and the server (in case it\n    needs access to per-server information) as self.server.  Since a\n    separate instance is created for each request, the handle() method\n    can define arbitrary other instance variariables.\n\n    ");
      var1.setline(651);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(661);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setup$48, (PyObject)null);
      var1.setlocal("setup", var4);
      var3 = null;
      var1.setline(664);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle$49, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      var1.setline(667);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, finish$50, (PyObject)null);
      var1.setlocal("finish", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("request", var3);
      var3 = null;
      var1.setline(653);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("client_address", var3);
      var3 = null;
      var1.setline(654);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("server", var3);
      var3 = null;
      var1.setline(655);
      var1.getlocal(0).__getattr__("setup").__call__(var2);
      var3 = null;

      try {
         var1.setline(657);
         var1.getlocal(0).__getattr__("handle").__call__(var2);
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(659);
         var1.getlocal(0).__getattr__("finish").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(659);
      var1.getlocal(0).__getattr__("finish").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup$48(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle$49(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish$50(PyFrame var1, ThreadState var2) {
      var1.setline(668);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamRequestHandler$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Define self.rfile and self.wfile for stream sockets."));
      var1.setline(681);
      PyString.fromInterned("Define self.rfile and self.wfile for stream sockets.");
      var1.setline(690);
      PyInteger var3 = Py.newInteger(-1);
      var1.setlocal("rbufsize", var3);
      var3 = null;
      var1.setline(691);
      var3 = Py.newInteger(0);
      var1.setlocal("wbufsize", var3);
      var3 = null;
      var1.setline(694);
      PyObject var4 = var1.getname("None");
      var1.setlocal("timeout", var4);
      var3 = null;
      var1.setline(698);
      var4 = var1.getname("False");
      var1.setlocal("disable_nagle_algorithm", var4);
      var3 = null;
      var1.setline(700);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, setup$52, (PyObject)null);
      var1.setlocal("setup", var6);
      var3 = null;
      var1.setline(710);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finish$53, (PyObject)null);
      var1.setlocal("finish", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setup$52(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyObject var3 = var1.getlocal(0).__getattr__("request");
      var1.getlocal(0).__setattr__("connection", var3);
      var3 = null;
      var1.setline(702);
      var3 = var1.getlocal(0).__getattr__("timeout");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(703);
         var1.getlocal(0).__getattr__("connection").__getattr__("settimeout").__call__(var2, var1.getlocal(0).__getattr__("timeout"));
      }

      var1.setline(704);
      if (var1.getlocal(0).__getattr__("disable_nagle_algorithm").__nonzero__()) {
         var1.setline(705);
         var1.getlocal(0).__getattr__("connection").__getattr__("setsockopt").__call__(var2, var1.getglobal("socket").__getattr__("IPPROTO_TCP"), var1.getglobal("socket").__getattr__("TCP_NODELAY"), var1.getglobal("True"));
      }

      var1.setline(707);
      var3 = var1.getlocal(0).__getattr__("connection").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"), (PyObject)var1.getlocal(0).__getattr__("rbufsize"));
      var1.getlocal(0).__setattr__("rfile", var3);
      var3 = null;
      var1.setline(708);
      var3 = var1.getlocal(0).__getattr__("connection").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wb"), (PyObject)var1.getlocal(0).__getattr__("wbufsize"));
      var1.getlocal(0).__setattr__("wfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish$53(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      if (var1.getlocal(0).__getattr__("wfile").__getattr__("closed").__not__().__nonzero__()) {
         try {
            var1.setline(713);
            var1.getlocal(0).__getattr__("wfile").__getattr__("flush").__call__(var2);
         } catch (Throwable var4) {
            PyException var3 = Py.setException(var4, var1);
            if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var3;
            }

            var1.setline(717);
         }
      }

      var1.setline(718);
      var1.getlocal(0).__getattr__("wfile").__getattr__("close").__call__(var2);
      var1.setline(719);
      var1.getlocal(0).__getattr__("rfile").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DatagramRequestHandler$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Define self.rfile and self.wfile for datagram sockets."));
      var1.setline(727);
      PyString.fromInterned("Define self.rfile and self.wfile for datagram sockets.");
      var1.setline(729);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setup$55, (PyObject)null);
      var1.setlocal("setup", var4);
      var3 = null;
      var1.setline(738);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, finish$56, (PyObject)null);
      var1.setlocal("finish", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setup$55(PyFrame var1, ThreadState var2) {
      PyException var3;
      String[] var4;
      PyObject var5;
      PyObject[] var10;
      try {
         var1.setline(731);
         String[] var7 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("cStringIO", var7, var1, -1);
         PyObject var11 = var8[0];
         var1.setlocal(1, var11);
         var4 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(733);
         var4 = new String[]{"StringIO"};
         var10 = imp.importFrom("StringIO", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal(1, var5);
         var5 = null;
      }

      var1.setline(734);
      PyObject var9 = var1.getlocal(0).__getattr__("request");
      var10 = Py.unpackSequence(var9, 2);
      var5 = var10[0];
      var1.getlocal(0).__setattr__("packet", var5);
      var5 = null;
      var5 = var10[1];
      var1.getlocal(0).__setattr__("socket", var5);
      var5 = null;
      var3 = null;
      var1.setline(735);
      var9 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("packet"));
      var1.getlocal(0).__setattr__("rfile", var9);
      var3 = null;
      var1.setline(736);
      var9 = var1.getlocal(1).__call__(var2);
      var1.getlocal(0).__setattr__("wfile", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish$56(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      var1.getlocal(0).__getattr__("socket").__getattr__("sendto").__call__(var2, var1.getlocal(0).__getattr__("wfile").__getattr__("getvalue").__call__(var2), var1.getlocal(0).__getattr__("client_address"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public SocketServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"func", "args", "e"};
      _eintr_retry$1 = Py.newCode(2, var2, var1, "_eintr_retry", 151, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseServer$2 = Py.newCode(0, var2, var1, "BaseServer", 160, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "server_address", "RequestHandlerClass"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 206, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_activate$4 = Py.newCode(1, var2, var1, "server_activate", 213, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "poll_interval", "r", "w", "e"};
      serve_forever$5 = Py.newCode(2, var2, var1, "serve_forever", 221, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shutdown$6 = Py.newCode(1, var2, var1, "shutdown", 243, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout", "fd_sets"};
      handle_request$7 = Py.newCode(1, var2, var1, "handle_request", 264, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address"};
      _handle_request_noblock$8 = Py.newCode(1, var2, var1, "_handle_request_noblock", 282, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_timeout$9 = Py.newCode(1, var2, var1, "handle_timeout", 300, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address"};
      verify_request$10 = Py.newCode(3, var2, var1, "verify_request", 307, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address"};
      process_request$11 = Py.newCode(3, var2, var1, "process_request", 315, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_close$12 = Py.newCode(1, var2, var1, "server_close", 324, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address"};
      finish_request$13 = Py.newCode(3, var2, var1, "finish_request", 332, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      shutdown_request$14 = Py.newCode(2, var2, var1, "shutdown_request", 336, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      close_request$15 = Py.newCode(2, var2, var1, "close_request", 340, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address", "traceback"};
      handle_error$16 = Py.newCode(3, var2, var1, "handle_error", 344, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TCPServer$17 = Py.newCode(0, var2, var1, "TCPServer", 358, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "server_address", "RequestHandlerClass", "bind_and_activate"};
      __init__$18 = Py.newCode(4, var2, var1, "__init__", 413, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_bind$19 = Py.newCode(1, var2, var1, "server_bind", 422, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_activate$20 = Py.newCode(1, var2, var1, "server_activate", 438, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_close$21 = Py.newCode(1, var2, var1, "server_close", 449, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$22 = Py.newCode(1, var2, var1, "fileno", 457, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_request$23 = Py.newCode(1, var2, var1, "get_request", 465, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      shutdown_request$24 = Py.newCode(2, var2, var1, "shutdown_request", 473, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      close_request$25 = Py.newCode(2, var2, var1, "close_request", 483, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UDPServer$26 = Py.newCode(0, var2, var1, "UDPServer", 488, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data", "client_addr"};
      get_request$27 = Py.newCode(1, var2, var1, "get_request", 498, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_activate$28 = Py.newCode(1, var2, var1, "server_activate", 502, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      shutdown_request$29 = Py.newCode(2, var2, var1, "shutdown_request", 506, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      close_request$30 = Py.newCode(2, var2, var1, "close_request", 510, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ForkingMixIn$31 = Py.newCode(0, var2, var1, "ForkingMixIn", 514, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pid", "status", "child", "e"};
      collect_children$32 = Py.newCode(1, var2, var1, "collect_children", 522, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_timeout$33 = Py.newCode(1, var2, var1, "handle_timeout", 554, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address", "pid"};
      process_request$34 = Py.newCode(3, var2, var1, "process_request", 561, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ThreadingMixIn$35 = Py.newCode(0, var2, var1, "ThreadingMixIn", 587, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "request", "client_address"};
      process_request_thread$36 = Py.newCode(3, var2, var1, "process_request_thread", 594, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "client_address", "t"};
      process_request$37 = Py.newCode(3, var2, var1, "process_request", 607, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ForkingUDPServer$38 = Py.newCode(0, var2, var1, "ForkingUDPServer", 615, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ForkingTCPServer$39 = Py.newCode(0, var2, var1, "ForkingTCPServer", 616, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ThreadingUDPServer$40 = Py.newCode(0, var2, var1, "ThreadingUDPServer", 618, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ThreadingTCPServer$41 = Py.newCode(0, var2, var1, "ThreadingTCPServer", 619, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnixStreamServer$42 = Py.newCode(0, var2, var1, "UnixStreamServer", 623, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnixDatagramServer$43 = Py.newCode(0, var2, var1, "UnixDatagramServer", 626, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ThreadingUnixStreamServer$44 = Py.newCode(0, var2, var1, "ThreadingUnixStreamServer", 629, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ThreadingUnixDatagramServer$45 = Py.newCode(0, var2, var1, "ThreadingUnixDatagramServer", 631, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BaseRequestHandler$46 = Py.newCode(0, var2, var1, "BaseRequestHandler", 633, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "request", "client_address", "server"};
      __init__$47 = Py.newCode(4, var2, var1, "__init__", 651, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setup$48 = Py.newCode(1, var2, var1, "setup", 661, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle$49 = Py.newCode(1, var2, var1, "handle", 664, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish$50 = Py.newCode(1, var2, var1, "finish", 667, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamRequestHandler$51 = Py.newCode(0, var2, var1, "StreamRequestHandler", 679, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setup$52 = Py.newCode(1, var2, var1, "setup", 700, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish$53 = Py.newCode(1, var2, var1, "finish", 710, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DatagramRequestHandler$54 = Py.newCode(0, var2, var1, "DatagramRequestHandler", 722, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "StringIO"};
      setup$55 = Py.newCode(1, var2, var1, "setup", 729, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish$56 = Py.newCode(1, var2, var1, "finish", 738, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new SocketServer$py("SocketServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(SocketServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._eintr_retry$1(var2, var3);
         case 2:
            return this.BaseServer$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.server_activate$4(var2, var3);
         case 5:
            return this.serve_forever$5(var2, var3);
         case 6:
            return this.shutdown$6(var2, var3);
         case 7:
            return this.handle_request$7(var2, var3);
         case 8:
            return this._handle_request_noblock$8(var2, var3);
         case 9:
            return this.handle_timeout$9(var2, var3);
         case 10:
            return this.verify_request$10(var2, var3);
         case 11:
            return this.process_request$11(var2, var3);
         case 12:
            return this.server_close$12(var2, var3);
         case 13:
            return this.finish_request$13(var2, var3);
         case 14:
            return this.shutdown_request$14(var2, var3);
         case 15:
            return this.close_request$15(var2, var3);
         case 16:
            return this.handle_error$16(var2, var3);
         case 17:
            return this.TCPServer$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.server_bind$19(var2, var3);
         case 20:
            return this.server_activate$20(var2, var3);
         case 21:
            return this.server_close$21(var2, var3);
         case 22:
            return this.fileno$22(var2, var3);
         case 23:
            return this.get_request$23(var2, var3);
         case 24:
            return this.shutdown_request$24(var2, var3);
         case 25:
            return this.close_request$25(var2, var3);
         case 26:
            return this.UDPServer$26(var2, var3);
         case 27:
            return this.get_request$27(var2, var3);
         case 28:
            return this.server_activate$28(var2, var3);
         case 29:
            return this.shutdown_request$29(var2, var3);
         case 30:
            return this.close_request$30(var2, var3);
         case 31:
            return this.ForkingMixIn$31(var2, var3);
         case 32:
            return this.collect_children$32(var2, var3);
         case 33:
            return this.handle_timeout$33(var2, var3);
         case 34:
            return this.process_request$34(var2, var3);
         case 35:
            return this.ThreadingMixIn$35(var2, var3);
         case 36:
            return this.process_request_thread$36(var2, var3);
         case 37:
            return this.process_request$37(var2, var3);
         case 38:
            return this.ForkingUDPServer$38(var2, var3);
         case 39:
            return this.ForkingTCPServer$39(var2, var3);
         case 40:
            return this.ThreadingUDPServer$40(var2, var3);
         case 41:
            return this.ThreadingTCPServer$41(var2, var3);
         case 42:
            return this.UnixStreamServer$42(var2, var3);
         case 43:
            return this.UnixDatagramServer$43(var2, var3);
         case 44:
            return this.ThreadingUnixStreamServer$44(var2, var3);
         case 45:
            return this.ThreadingUnixDatagramServer$45(var2, var3);
         case 46:
            return this.BaseRequestHandler$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.setup$48(var2, var3);
         case 49:
            return this.handle$49(var2, var3);
         case 50:
            return this.finish$50(var2, var3);
         case 51:
            return this.StreamRequestHandler$51(var2, var3);
         case 52:
            return this.setup$52(var2, var3);
         case 53:
            return this.finish$53(var2, var3);
         case 54:
            return this.DatagramRequestHandler$54(var2, var3);
         case 55:
            return this.setup$55(var2, var3);
         case 56:
            return this.finish$56(var2, var3);
         default:
            return null;
      }
   }
}

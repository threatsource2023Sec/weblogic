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
@Filename("SimpleXMLRPCServer.py")
public class SimpleXMLRPCServer$py extends PyFunctionTable implements PyRunnable {
   static SimpleXMLRPCServer$py self;
   static final PyCode f$0;
   static final PyCode resolve_dotted_attribute$1;
   static final PyCode list_public_methods$2;
   static final PyCode remove_duplicates$3;
   static final PyCode SimpleXMLRPCDispatcher$4;
   static final PyCode __init__$5;
   static final PyCode register_instance$6;
   static final PyCode register_function$7;
   static final PyCode register_introspection_functions$8;
   static final PyCode register_multicall_functions$9;
   static final PyCode _marshaled_dispatch$10;
   static final PyCode system_listMethods$11;
   static final PyCode system_methodSignature$12;
   static final PyCode system_methodHelp$13;
   static final PyCode system_multicall$14;
   static final PyCode _dispatch$15;
   static final PyCode SimpleXMLRPCRequestHandler$16;
   static final PyCode accept_encodings$17;
   static final PyCode is_rpc_path_valid$18;
   static final PyCode do_POST$19;
   static final PyCode decode_request_content$20;
   static final PyCode report_404$21;
   static final PyCode log_request$22;
   static final PyCode SimpleXMLRPCServer$23;
   static final PyCode __init__$24;
   static final PyCode MultiPathXMLRPCServer$25;
   static final PyCode __init__$26;
   static final PyCode add_dispatcher$27;
   static final PyCode get_dispatcher$28;
   static final PyCode _marshaled_dispatch$29;
   static final PyCode CGIXMLRPCRequestHandler$30;
   static final PyCode __init__$31;
   static final PyCode handle_xmlrpc$32;
   static final PyCode handle_get$33;
   static final PyCode handle_request$34;
   static final PyCode f$35;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Simple XML-RPC Server.\n\nThis module can be used to create simple XML-RPC servers\nby creating a server and either installing functions, a\nclass instance, or by extending the SimpleXMLRPCServer\nclass.\n\nIt can also be used to handle XML-RPC requests in a CGI\nenvironment using CGIXMLRPCRequestHandler.\n\nA list of possible usage patterns follows:\n\n1. Install functions:\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_function(pow)\nserver.register_function(lambda x,y: x+y, 'add')\nserver.serve_forever()\n\n2. Install an instance:\n\nclass MyFuncs:\n    def __init__(self):\n        # make all of the string functions available through\n        # string.func_name\n        import string\n        self.string = string\n    def _listMethods(self):\n        # implement this method so that system.listMethods\n        # knows to advertise the strings methods\n        return list_public_methods(self) + \\\n                ['string.' + method for method in list_public_methods(self.string)]\n    def pow(self, x, y): return pow(x, y)\n    def add(self, x, y) : return x + y\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_introspection_functions()\nserver.register_instance(MyFuncs())\nserver.serve_forever()\n\n3. Install an instance with custom dispatch method:\n\nclass Math:\n    def _listMethods(self):\n        # this method must be present for system.listMethods\n        # to work\n        return ['add', 'pow']\n    def _methodHelp(self, method):\n        # this method must be present for system.methodHelp\n        # to work\n        if method == 'add':\n            return \"add(2,3) => 5\"\n        elif method == 'pow':\n            return \"pow(x, y[, z]) => number\"\n        else:\n            # By convention, return empty\n            # string if no help is available\n            return \"\"\n    def _dispatch(self, method, params):\n        if method == 'pow':\n            return pow(*params)\n        elif method == 'add':\n            return params[0] + params[1]\n        else:\n            raise 'bad method'\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_introspection_functions()\nserver.register_instance(Math())\nserver.serve_forever()\n\n4. Subclass SimpleXMLRPCServer:\n\nclass MathServer(SimpleXMLRPCServer):\n    def _dispatch(self, method, params):\n        try:\n            # We are forcing the 'export_' prefix on methods that are\n            # callable through XML-RPC to prevent potential security\n            # problems\n            func = getattr(self, 'export_' + method)\n        except AttributeError:\n            raise Exception('method \"%s\" is not supported' % method)\n        else:\n            return func(*params)\n\n    def export_add(self, x, y):\n        return x + y\n\nserver = MathServer((\"localhost\", 8000))\nserver.serve_forever()\n\n5. CGI script:\n\nserver = CGIXMLRPCRequestHandler()\nserver.register_function(pow)\nserver.handle_request()\n"));
      var1.setline(97);
      PyString.fromInterned("Simple XML-RPC Server.\n\nThis module can be used to create simple XML-RPC servers\nby creating a server and either installing functions, a\nclass instance, or by extending the SimpleXMLRPCServer\nclass.\n\nIt can also be used to handle XML-RPC requests in a CGI\nenvironment using CGIXMLRPCRequestHandler.\n\nA list of possible usage patterns follows:\n\n1. Install functions:\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_function(pow)\nserver.register_function(lambda x,y: x+y, 'add')\nserver.serve_forever()\n\n2. Install an instance:\n\nclass MyFuncs:\n    def __init__(self):\n        # make all of the string functions available through\n        # string.func_name\n        import string\n        self.string = string\n    def _listMethods(self):\n        # implement this method so that system.listMethods\n        # knows to advertise the strings methods\n        return list_public_methods(self) + \\\n                ['string.' + method for method in list_public_methods(self.string)]\n    def pow(self, x, y): return pow(x, y)\n    def add(self, x, y) : return x + y\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_introspection_functions()\nserver.register_instance(MyFuncs())\nserver.serve_forever()\n\n3. Install an instance with custom dispatch method:\n\nclass Math:\n    def _listMethods(self):\n        # this method must be present for system.listMethods\n        # to work\n        return ['add', 'pow']\n    def _methodHelp(self, method):\n        # this method must be present for system.methodHelp\n        # to work\n        if method == 'add':\n            return \"add(2,3) => 5\"\n        elif method == 'pow':\n            return \"pow(x, y[, z]) => number\"\n        else:\n            # By convention, return empty\n            # string if no help is available\n            return \"\"\n    def _dispatch(self, method, params):\n        if method == 'pow':\n            return pow(*params)\n        elif method == 'add':\n            return params[0] + params[1]\n        else:\n            raise 'bad method'\n\nserver = SimpleXMLRPCServer((\"localhost\", 8000))\nserver.register_introspection_functions()\nserver.register_instance(Math())\nserver.serve_forever()\n\n4. Subclass SimpleXMLRPCServer:\n\nclass MathServer(SimpleXMLRPCServer):\n    def _dispatch(self, method, params):\n        try:\n            # We are forcing the 'export_' prefix on methods that are\n            # callable through XML-RPC to prevent potential security\n            # problems\n            func = getattr(self, 'export_' + method)\n        except AttributeError:\n            raise Exception('method \"%s\" is not supported' % method)\n        else:\n            return func(*params)\n\n    def export_add(self, x, y):\n        return x + y\n\nserver = MathServer((\"localhost\", 8000))\nserver.serve_forever()\n\n5. CGI script:\n\nserver = CGIXMLRPCRequestHandler()\nserver.register_function(pow)\nserver.handle_request()\n");
      var1.setline(102);
      PyObject var3 = imp.importOne("xmlrpclib", var1, -1);
      var1.setlocal("xmlrpclib", var3);
      var3 = null;
      var1.setline(103);
      String[] var6 = new String[]{"Fault"};
      PyObject[] var7 = imp.importFrom("xmlrpclib", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Fault", var4);
      var4 = null;
      var1.setline(104);
      var3 = imp.importOne("SocketServer", var1, -1);
      var1.setlocal("SocketServer", var3);
      var3 = null;
      var1.setline(105);
      var3 = imp.importOne("BaseHTTPServer", var1, -1);
      var1.setlocal("BaseHTTPServer", var3);
      var3 = null;
      var1.setline(106);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(107);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(108);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(109);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;

      try {
         var1.setline(111);
         var3 = imp.importOne("fcntl", var1, -1);
         var1.setlocal("fcntl", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(113);
         var4 = var1.getname("None");
         var1.setlocal("fcntl", var4);
         var4 = null;
      }

      var1.setline(115);
      var7 = new PyObject[]{var1.getname("True")};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, resolve_dotted_attribute$1, PyString.fromInterned("resolve_dotted_attribute(a, 'b.c.d') => a.b.c.d\n\n    Resolves a dotted attribute name to an object.  Raises\n    an AttributeError if any attribute in the chain starts with a '_'.\n\n    If the optional allow_dotted_names argument is false, dots are not\n    supported and this function operates similar to getattr(obj, attr).\n    "));
      var1.setlocal("resolve_dotted_attribute", var9);
      var3 = null;
      var1.setline(139);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, list_public_methods$2, PyString.fromInterned("Returns a list of attribute strings, found in the specified\n    object, which represent callable attributes"));
      var1.setlocal("list_public_methods", var9);
      var3 = null;
      var1.setline(147);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, remove_duplicates$3, PyString.fromInterned("remove_duplicates([2,2,2,1,3,3]) => [3,1,2]\n\n    Returns a copy of a list without duplicates. Every list\n    item must be hashable and the order of the items in the\n    resulting list is not defined.\n    "));
      var1.setlocal("remove_duplicates", var9);
      var3 = null;
      var1.setline(160);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("SimpleXMLRPCDispatcher", var7, SimpleXMLRPCDispatcher$4);
      var1.setlocal("SimpleXMLRPCDispatcher", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(424);
      var7 = new PyObject[]{var1.getname("BaseHTTPServer").__getattr__("BaseHTTPRequestHandler")};
      var4 = Py.makeClass("SimpleXMLRPCRequestHandler", var7, SimpleXMLRPCRequestHandler$16);
      var1.setlocal("SimpleXMLRPCRequestHandler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(569);
      var7 = new PyObject[]{var1.getname("SocketServer").__getattr__("TCPServer"), var1.getname("SimpleXMLRPCDispatcher")};
      var4 = Py.makeClass("SimpleXMLRPCServer", var7, SimpleXMLRPCServer$23);
      var1.setlocal("SimpleXMLRPCServer", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(603);
      var7 = new PyObject[]{var1.getname("SimpleXMLRPCServer")};
      var4 = Py.makeClass("MultiPathXMLRPCServer", var7, MultiPathXMLRPCServer$25);
      var1.setlocal("MultiPathXMLRPCServer", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(641);
      var7 = new PyObject[]{var1.getname("SimpleXMLRPCDispatcher")};
      var4 = Py.makeClass("CGIXMLRPCRequestHandler", var7, CGIXMLRPCRequestHandler$30);
      var1.setlocal("CGIXMLRPCRequestHandler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(702);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(703);
         Py.println(PyString.fromInterned("Running XML-RPC server on port 8000"));
         var1.setline(704);
         var3 = var1.getname("SimpleXMLRPCServer").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("localhost"), Py.newInteger(8000)})));
         var1.setlocal("server", var3);
         var3 = null;
         var1.setline(705);
         var1.getname("server").__getattr__("register_function").__call__(var2, var1.getname("pow"));
         var1.setline(706);
         var10000 = var1.getname("server").__getattr__("register_function");
         var1.setline(706);
         var7 = Py.EmptyObjects;
         var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var7, f$35)), (PyObject)PyString.fromInterned("add"));
         var1.setline(707);
         var1.getname("server").__getattr__("serve_forever").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resolve_dotted_attribute$1(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("resolve_dotted_attribute(a, 'b.c.d') => a.b.c.d\n\n    Resolves a dotted attribute name to an object.  Raises\n    an AttributeError if any attribute in the chain starts with a '_'.\n\n    If the optional allow_dotted_names argument is false, dots are not\n    supported and this function operates similar to getattr(obj, attr).\n    ");
      var1.setline(125);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(126);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(128);
         PyList var6 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(130);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(130);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(137);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(131);
         if (var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__nonzero__()) {
            var1.setline(132);
            throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("attempt to access private attribute \"%s\"")._mod(var1.getlocal(4))));
         }

         var1.setline(136);
         PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(0, var5);
         var5 = null;
      }
   }

   public PyObject list_public_methods$2(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Returns a list of attribute strings, found in the specified\n    object, which represent callable attributes");
      var1.setline(143);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(143);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(143);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(144);
         PyObject var10001 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__();
         if (var10001.__nonzero__()) {
            var10001 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)), (PyObject)PyString.fromInterned("__call__"));
         }

         if (var10001.__nonzero__()) {
            var1.setline(143);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject remove_duplicates$3(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("remove_duplicates([2,2,2,1,3,3]) => [3,1,2]\n\n    Returns a copy of a list without duplicates. Every list\n    item must be hashable and the order of the items in the\n    resulting list is not defined.\n    ");
      var1.setline(154);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(155);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(158);
            var6 = var1.getlocal(1).__getattr__("keys").__call__(var2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(156);
         PyInteger var5 = Py.newInteger(1);
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject SimpleXMLRPCDispatcher$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class that dispatches XML-RPC requests.\n\n    This class is used to register XML-RPC method handlers\n    and then to dispatch them. This class doesn't need to be\n    instanced directly when used by SimpleXMLRPCServer but it\n    can be instanced when used by the MultiPathXMLRPCServer.\n    "));
      var1.setline(167);
      PyString.fromInterned("Mix-in class that dispatches XML-RPC requests.\n\n    This class is used to register XML-RPC method handlers\n    and then to dispatch them. This class doesn't need to be\n    instanced directly when used by SimpleXMLRPCServer but it\n    can be instanced when used by the MultiPathXMLRPCServer.\n    ");
      var1.setline(169);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(175);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, register_instance$6, PyString.fromInterned("Registers an instance to respond to XML-RPC requests.\n\n        Only one instance can be installed at a time.\n\n        If the registered instance has a _dispatch method then that\n        method will be called with the name of the XML-RPC method and\n        its parameters as a tuple\n        e.g. instance._dispatch('add',(2,3))\n\n        If the registered instance does not have a _dispatch method\n        then the instance will be searched to find a matching method\n        and, if found, will be called. Methods beginning with an '_'\n        are considered private and will not be called by\n        SimpleXMLRPCServer.\n\n        If a registered function matches a XML-RPC request, then it\n        will be called instead of the registered instance.\n\n        If the optional allow_dotted_names argument is true and the\n        instance does not have a _dispatch method, method names\n        containing dots are supported and resolved, as long as none of\n        the name segments start with an '_'.\n\n            *** SECURITY WARNING: ***\n\n            Enabling the allow_dotted_names options allows intruders\n            to access your module's global variables and may allow\n            intruders to execute arbitrary code on your machine.  Only\n            use this option on a secure, closed network.\n\n        "));
      var1.setlocal("register_instance", var4);
      var3 = null;
      var1.setline(211);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, register_function$7, PyString.fromInterned("Registers a function to respond to XML-RPC requests.\n\n        The optional name argument can be used to set a Unicode name\n        for the function.\n        "));
      var1.setlocal("register_function", var4);
      var3 = null;
      var1.setline(222);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, register_introspection_functions$8, PyString.fromInterned("Registers the XML-RPC introspection methods in the system\n        namespace.\n\n        see http://xmlrpc.usefulinc.com/doc/reserved.html\n        "));
      var1.setlocal("register_introspection_functions", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, register_multicall_functions$9, PyString.fromInterned("Registers the XML-RPC multicall method in the system\n        namespace.\n\n        see http://www.xmlrpc.com/discuss/msgReader$1208"));
      var1.setlocal("register_multicall_functions", var4);
      var3 = null;
      var1.setline(241);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _marshaled_dispatch$10, PyString.fromInterned("Dispatches an XML-RPC method from marshalled (XML) data.\n\n        XML-RPC methods are dispatched from the marshalled (XML) data\n        using the _dispatch method and the result is returned as\n        marshalled data. For backwards compatibility, a dispatch\n        function can be provided as an argument (see comment in\n        SimpleXMLRPCRequestHandler.do_POST) but overriding the\n        existing method through subclassing is the preferred means\n        of changing method dispatch behavior.\n        "));
      var1.setlocal("_marshaled_dispatch", var4);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, system_listMethods$11, PyString.fromInterned("system.listMethods() => ['add', 'subtract', 'multiple']\n\n        Returns a list of the methods supported by the server."));
      var1.setlocal("system_listMethods", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, system_methodSignature$12, PyString.fromInterned("system.methodSignature('add') => [double, int, int]\n\n        Returns a list describing the signature of the method. In the\n        above example, the add method takes two integers as arguments\n        and returns a double result.\n\n        This server does NOT support system.methodSignature."));
      var1.setlocal("system_methodSignature", var4);
      var3 = null;
      var1.setline(314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, system_methodHelp$13, PyString.fromInterned("system.methodHelp('add') => \"Adds two integers together\"\n\n        Returns a string containing documentation for the specified method."));
      var1.setlocal("system_methodHelp", var4);
      var3 = null;
      var1.setline(346);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, system_multicall$14, PyString.fromInterned("system.multicall([{'methodName': 'add', 'params': [2, 2]}, ...]) => [[4], ...]\n\n        Allows the caller to package multiple XML-RPC calls into a single\n        request.\n\n        See http://www.xmlrpc.com/discuss/msgReader$1208\n        "));
      var1.setlocal("system_multicall", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dispatch$15, PyString.fromInterned("Dispatches the XML-RPC method.\n\n        XML-RPC calls are forwarded to a registered function that\n        matches the called XML-RPC method name. If no such function\n        exists then the call is forwarded to the registered instance,\n        if available.\n\n        If the registered instance has a _dispatch method then that\n        method will be called with the name of the XML-RPC method and\n        its parameters as a tuple\n        e.g. instance._dispatch('add',(2,3))\n\n        If the registered instance does not have a _dispatch method\n        then the instance will be searched to find a matching method\n        and, if found, will be called.\n\n        Methods beginning with an '_' are considered private and will\n        not be called.\n        "));
      var1.setlocal("_dispatch", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"funcs", var3);
      var3 = null;
      var1.setline(171);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("instance", var4);
      var3 = null;
      var1.setline(172);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("allow_none", var4);
      var3 = null;
      var1.setline(173);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("encoding", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register_instance$6(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("Registers an instance to respond to XML-RPC requests.\n\n        Only one instance can be installed at a time.\n\n        If the registered instance has a _dispatch method then that\n        method will be called with the name of the XML-RPC method and\n        its parameters as a tuple\n        e.g. instance._dispatch('add',(2,3))\n\n        If the registered instance does not have a _dispatch method\n        then the instance will be searched to find a matching method\n        and, if found, will be called. Methods beginning with an '_'\n        are considered private and will not be called by\n        SimpleXMLRPCServer.\n\n        If a registered function matches a XML-RPC request, then it\n        will be called instead of the registered instance.\n\n        If the optional allow_dotted_names argument is true and the\n        instance does not have a _dispatch method, method names\n        containing dots are supported and resolved, as long as none of\n        the name segments start with an '_'.\n\n            *** SECURITY WARNING: ***\n\n            Enabling the allow_dotted_names options allows intruders\n            to access your module's global variables and may allow\n            intruders to execute arbitrary code on your machine.  Only\n            use this option on a secure, closed network.\n\n        ");
      var1.setline(208);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("instance", var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("allow_dotted_names", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register_function$7(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("Registers a function to respond to XML-RPC requests.\n\n        The optional name argument can be used to set a Unicode name\n        for the function.\n        ");
      var1.setline(218);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(219);
         var3 = var1.getlocal(1).__getattr__("__name__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(220);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("funcs").__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register_introspection_functions$8(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyString.fromInterned("Registers the XML-RPC introspection methods in the system\n        namespace.\n\n        see http://xmlrpc.usefulinc.com/doc/reserved.html\n        ");
      var1.setline(229);
      var1.getlocal(0).__getattr__("funcs").__getattr__("update").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("system.listMethods"), var1.getlocal(0).__getattr__("system_listMethods"), PyString.fromInterned("system.methodSignature"), var1.getlocal(0).__getattr__("system_methodSignature"), PyString.fromInterned("system.methodHelp"), var1.getlocal(0).__getattr__("system_methodHelp")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register_multicall_functions$9(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Registers the XML-RPC multicall method in the system\n        namespace.\n\n        see http://www.xmlrpc.com/discuss/msgReader$1208");
      var1.setline(239);
      var1.getlocal(0).__getattr__("funcs").__getattr__("update").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("system.multicall"), var1.getlocal(0).__getattr__("system_multicall")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _marshaled_dispatch$10(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyString.fromInterned("Dispatches an XML-RPC method from marshalled (XML) data.\n\n        XML-RPC methods are dispatched from the marshalled (XML) data\n        using the _dispatch method and the result is returned as\n        marshalled data. For backwards compatibility, a dispatch\n        function can be provided as an argument (see comment in\n        SimpleXMLRPCRequestHandler.do_POST) but overriding the\n        existing method through subclassing is the preferred means\n        of changing method dispatch behavior.\n        ");

      PyException var3;
      PyObject[] var5;
      PyObject var8;
      PyObject[] var9;
      PyObject var10000;
      try {
         var1.setline(254);
         var8 = var1.getglobal("xmlrpclib").__getattr__("loads").__call__(var2, var1.getlocal(1));
         var9 = Py.unpackSequence(var8, 2);
         PyObject var11 = var9[0];
         var1.setlocal(4, var11);
         var5 = null;
         var11 = var9[1];
         var1.setlocal(5, var11);
         var5 = null;
         var3 = null;
         var1.setline(257);
         var8 = var1.getlocal(2);
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(258);
            var8 = var1.getlocal(2).__call__(var2, var1.getlocal(5), var1.getlocal(4));
            var1.setlocal(6, var8);
            var3 = null;
         } else {
            var1.setline(260);
            var8 = var1.getlocal(0).__getattr__("_dispatch").__call__(var2, var1.getlocal(5), var1.getlocal(4));
            var1.setlocal(6, var8);
            var3 = null;
         }

         var1.setline(262);
         PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(6)});
         var1.setlocal(6, var12);
         var3 = null;
         var1.setline(263);
         var10000 = var1.getglobal("xmlrpclib").__getattr__("dumps");
         PyObject[] var13 = new PyObject[]{var1.getlocal(6), Py.newInteger(1), var1.getlocal(0).__getattr__("allow_none"), var1.getlocal(0).__getattr__("encoding")};
         String[] var14 = new String[]{"methodresponse", "allow_none", "encoding"};
         var10000 = var10000.__call__(var2, var13, var14);
         var3 = null;
         var8 = var10000;
         var1.setlocal(6, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         PyObject var4;
         String[] var10;
         if (var3.match(var1.getglobal("Fault"))) {
            var4 = var3.value;
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(266);
            var10000 = var1.getglobal("xmlrpclib").__getattr__("dumps");
            var9 = new PyObject[]{var1.getlocal(7), var1.getlocal(0).__getattr__("allow_none"), var1.getlocal(0).__getattr__("encoding")};
            var10 = new String[]{"allow_none", "encoding"};
            var10000 = var10000.__call__(var2, var9, var10);
            var4 = null;
            var4 = var10000;
            var1.setlocal(6, var4);
            var4 = null;
         } else {
            var1.setline(270);
            var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
            var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(10, var6);
            var6 = null;
            var4 = null;
            var1.setline(271);
            var10000 = var1.getglobal("xmlrpclib").__getattr__("dumps");
            var9 = new PyObject[]{var1.getglobal("xmlrpclib").__getattr__("Fault").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)}))), var1.getlocal(0).__getattr__("encoding"), var1.getlocal(0).__getattr__("allow_none")};
            var10 = new String[]{"encoding", "allow_none"};
            var10000 = var10000.__call__(var2, var9, var10);
            var4 = null;
            var4 = var10000;
            var1.setlocal(6, var4);
            var4 = null;
         }
      }

      var1.setline(276);
      var8 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject system_listMethods$11(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("system.listMethods() => ['add', 'subtract', 'multiple']\n\n        Returns a list of the methods supported by the server.");
      var1.setline(283);
      PyObject var3 = var1.getlocal(0).__getattr__("funcs").__getattr__("keys").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(284);
      var3 = var1.getlocal(0).__getattr__("instance");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(287);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_listMethods")).__nonzero__()) {
            var1.setline(288);
            var3 = var1.getglobal("remove_duplicates").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getattr__("instance").__getattr__("_listMethods").__call__(var2)));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(294);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_dispatch")).__not__().__nonzero__()) {
               var1.setline(295);
               var3 = var1.getglobal("remove_duplicates").__call__(var2, var1.getlocal(1)._add(var1.getglobal("list_public_methods").__call__(var2, var1.getlocal(0).__getattr__("instance"))));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(298);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(299);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject system_methodSignature$12(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyString.fromInterned("system.methodSignature('add') => [double, int, int]\n\n        Returns a list describing the signature of the method. In the\n        above example, the add method takes two integers as arguments\n        and returns a double result.\n\n        This server does NOT support system.methodSignature.");
      var1.setline(312);
      PyString var3 = PyString.fromInterned("signatures not supported");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject system_methodHelp$13(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned("system.methodHelp('add') => \"Adds two integers together\"\n\n        Returns a string containing documentation for the specified method.");
      var1.setline(319);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(320);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("funcs"));
      var3 = null;
      PyException var4;
      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(321);
         var3 = var1.getlocal(0).__getattr__("funcs").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(322);
         var3 = var1.getlocal(0).__getattr__("instance");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(324);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_methodHelp")).__nonzero__()) {
               var1.setline(325);
               var3 = var1.getlocal(0).__getattr__("instance").__getattr__("_methodHelp").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(328);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_dispatch")).__not__().__nonzero__()) {
               try {
                  var1.setline(330);
                  var6 = var1.getglobal("resolve_dotted_attribute").__call__(var2, var1.getlocal(0).__getattr__("instance"), var1.getlocal(1), var1.getlocal(0).__getattr__("allow_dotted_names"));
                  var1.setlocal(2, var6);
                  var4 = null;
               } catch (Throwable var5) {
                  var4 = Py.setException(var5, var1);
                  if (!var4.match(var1.getglobal("AttributeError"))) {
                     throw var4;
                  }

                  var1.setline(336);
               }
            }
         }
      }

      var1.setline(340);
      var6 = var1.getlocal(2);
      var10000 = var6._is(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(341);
         PyString var7 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(343);
         var6 = imp.importOne("pydoc", var1, -1);
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(344);
         var3 = var1.getlocal(3).__getattr__("getdoc").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject system_multicall$14(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyString.fromInterned("system.multicall([{'methodName': 'add', 'params': [2, 2]}, ...]) => [[4], ...]\n\n        Allows the caller to package multiple XML-RPC calls into a single\n        request.\n\n        See http://www.xmlrpc.com/discuss/msgReader$1208\n        ");
      var1.setline(356);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(357);
      PyObject var10 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(357);
         PyObject var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(376);
            var10 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var10;
         }

         var1.setlocal(3, var4);
         var1.setline(358);
         PyObject var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("methodName"));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(359);
         var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("params"));
         var1.setlocal(5, var5);
         var5 = null;

         try {
            var1.setline(364);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_dispatch").__call__(var2, var1.getlocal(4), var1.getlocal(5))})));
         } catch (Throwable var9) {
            PyException var11 = Py.setException(var9, var1);
            PyObject var6;
            if (var11.match(var1.getglobal("Fault"))) {
               var6 = var11.value;
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(366);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("faultCode"), var1.getlocal(6).__getattr__("faultCode"), PyString.fromInterned("faultString"), var1.getlocal(6).__getattr__("faultString")})));
            } else {
               var1.setline(371);
               var6 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
               PyObject[] var7 = Py.unpackSequence(var6, 3);
               PyObject var8 = var7[0];
               var1.setlocal(7, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(8, var8);
               var8 = null;
               var8 = var7[2];
               var1.setlocal(9, var8);
               var8 = null;
               var6 = null;
               var1.setline(372);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("faultCode"), Py.newInteger(1), PyString.fromInterned("faultString"), PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)}))})));
            }
         }
      }
   }

   public PyObject _dispatch$15(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyString.fromInterned("Dispatches the XML-RPC method.\n\n        XML-RPC calls are forwarded to a registered function that\n        matches the called XML-RPC method name. If no such function\n        exists then the call is forwarded to the registered instance,\n        if available.\n\n        If the registered instance has a _dispatch method then that\n        method will be called with the name of the XML-RPC method and\n        its parameters as a tuple\n        e.g. instance._dispatch('add',(2,3))\n\n        If the registered instance does not have a _dispatch method\n        then the instance will be searched to find a matching method\n        and, if found, will be called.\n\n        Methods beginning with an '_' are considered private and will\n        not be called.\n        ");
      var1.setline(399);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var10000;
      PyObject var4;
      try {
         var1.setline(402);
         var3 = var1.getlocal(0).__getattr__("funcs").__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getglobal("KeyError"))) {
            throw var8;
         }

         var1.setline(404);
         var4 = var1.getlocal(0).__getattr__("instance");
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(406);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_dispatch")).__nonzero__()) {
               var1.setline(407);
               var4 = var1.getlocal(0).__getattr__("instance").__getattr__("_dispatch").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var4;
            }

            PyException var5;
            try {
               var1.setline(411);
               PyObject var10 = var1.getglobal("resolve_dotted_attribute").__call__(var2, var1.getlocal(0).__getattr__("instance"), var1.getlocal(1), var1.getlocal(0).__getattr__("allow_dotted_names"));
               var1.setlocal(3, var10);
               var5 = null;
            } catch (Throwable var6) {
               var5 = Py.setException(var6, var1);
               if (!var5.match(var1.getglobal("AttributeError"))) {
                  throw var5;
               }

               var1.setline(417);
            }
         }
      }

      var1.setline(419);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(420);
         var10000 = var1.getlocal(3);
         PyObject[] var9 = Py.EmptyObjects;
         String[] var11 = new String[0];
         var10000 = var10000._callextra(var9, var11, var1.getlocal(2), (PyObject)null);
         var3 = null;
         var4 = var10000;
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(422);
         throw Py.makeException(var1.getglobal("Exception").__call__(var2, PyString.fromInterned("method \"%s\" is not supported")._mod(var1.getlocal(1))));
      }
   }

   public PyObject SimpleXMLRPCRequestHandler$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple XML-RPC request handler class.\n\n    Handles all HTTP POST requests and attempts to decode them as\n    XML-RPC requests.\n    "));
      var1.setline(429);
      PyString.fromInterned("Simple XML-RPC request handler class.\n\n    Handles all HTTP POST requests and attempts to decode them as\n    XML-RPC requests.\n    ");
      var1.setline(433);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("/"), PyString.fromInterned("/RPC2")});
      var1.setlocal("rpc_paths", var3);
      var3 = null;
      var1.setline(436);
      PyInteger var4 = Py.newInteger(1400);
      var1.setlocal("encode_threshold", var4);
      var3 = null;
      var1.setline(440);
      var4 = Py.newInteger(-1);
      var1.setlocal("wbufsize", var4);
      var3 = null;
      var1.setline(441);
      PyObject var5 = var1.getname("True");
      var1.setlocal("disable_nagle_algorithm", var5);
      var3 = null;
      var1.setline(444);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n                            \\s* ([^\\s;]+) \\s*            #content-coding\n                            (;\\s* q \\s*=\\s* ([0-9\\.]+))? #q\n                            "), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("IGNORECASE")));
      var1.setlocal("aepattern", var5);
      var3 = null;
      var1.setline(449);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, accept_encodings$17, (PyObject)null);
      var1.setlocal("accept_encodings", var7);
      var3 = null;
      var1.setline(460);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, is_rpc_path_valid$18, (PyObject)null);
      var1.setlocal("is_rpc_path_valid", var7);
      var3 = null;
      var1.setline(467);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, do_POST$19, PyString.fromInterned("Handles the HTTP POST request.\n\n        Attempts to interpret all HTTP POST requests as XML-RPC calls,\n        which are forwarded to the server's _dispatch method for handling.\n        "));
      var1.setlocal("do_POST", var7);
      var3 = null;
      var1.setline(537);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, decode_request_content$20, (PyObject)null);
      var1.setlocal("decode_request_content", var7);
      var3 = null;
      var1.setline(554);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, report_404$21, (PyObject)null);
      var1.setlocal("report_404", var7);
      var3 = null;
      var1.setline(563);
      var6 = new PyObject[]{PyString.fromInterned("-"), PyString.fromInterned("-")};
      var7 = new PyFunction(var1.f_globals, var6, log_request$22, PyString.fromInterned("Selectively log an accepted request."));
      var1.setlocal("log_request", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject accept_encodings$17(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(451);
      PyObject var6 = var1.getlocal(0).__getattr__("headers").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Accept-Encoding"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(452);
      var6 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

      while(true) {
         var1.setline(452);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(458);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(453);
         PyObject var5 = var1.getlocal(0).__getattr__("aepattern").__getattr__("match").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(454);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(455);
            var5 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(456);
            var1.setline(456);
            Object var7 = var1.getlocal(5).__nonzero__() ? var1.getglobal("float").__call__(var2, var1.getlocal(5)) : Py.newFloat(1.0);
            var1.setlocal(5, (PyObject)var7);
            var5 = null;
            var1.setline(457);
            var5 = var1.getlocal(5);
            var1.getlocal(1).__setitem__(var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var5);
            var5 = null;
         }
      }
   }

   public PyObject is_rpc_path_valid$18(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("rpc_paths").__nonzero__()) {
         var1.setline(462);
         var3 = var1.getlocal(0).__getattr__("path");
         PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("rpc_paths"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(465);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject do_POST$19(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString.fromInterned("Handles the HTTP POST request.\n\n        Attempts to interpret all HTTP POST requests as XML-RPC calls,\n        which are forwarded to the server's _dispatch method for handling.\n        ");
      var1.setline(475);
      if (var1.getlocal(0).__getattr__("is_rpc_path_valid").__call__(var2).__not__().__nonzero__()) {
         var1.setline(476);
         var1.getlocal(0).__getattr__("report_404").__call__(var2);
         var1.setline(477);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         label89: {
            PyObject var10000;
            PyException var3;
            PyObject var4;
            try {
               var1.setline(484);
               PyObject var7 = Py.newInteger(10)._mul(Py.newInteger(1024))._mul(Py.newInteger(1024));
               var1.setlocal(1, var7);
               var3 = null;
               var1.setline(485);
               var7 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("headers").__getitem__(PyString.fromInterned("content-length")));
               var1.setlocal(2, var7);
               var3 = null;
               var1.setline(486);
               PyList var8 = new PyList(Py.EmptyObjects);
               var1.setlocal(3, var8);
               var3 = null;

               while(true) {
                  var1.setline(487);
                  if (!var1.getlocal(2).__nonzero__()) {
                     break;
                  }

                  var1.setline(488);
                  var7 = var1.getglobal("min").__call__(var2, var1.getlocal(2), var1.getlocal(1));
                  var1.setlocal(4, var7);
                  var3 = null;
                  var1.setline(489);
                  var7 = var1.getlocal(0).__getattr__("rfile").__getattr__("read").__call__(var2, var1.getlocal(4));
                  var1.setlocal(5, var7);
                  var3 = null;
                  var1.setline(490);
                  if (var1.getlocal(5).__not__().__nonzero__()) {
                     break;
                  }

                  var1.setline(492);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
                  var1.setline(493);
                  var7 = var1.getlocal(2);
                  var7 = var7._isub(var1.getglobal("len").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1))));
                  var1.setlocal(2, var7);
               }

               var1.setline(494);
               var7 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
               var1.setlocal(6, var7);
               var3 = null;
               var1.setline(496);
               var7 = var1.getlocal(0).__getattr__("decode_request_content").__call__(var2, var1.getlocal(6));
               var1.setlocal(6, var7);
               var3 = null;
               var1.setline(497);
               var7 = var1.getlocal(6);
               var10000 = var7._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(498);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(505);
               var7 = var1.getlocal(0).__getattr__("server").__getattr__("_marshaled_dispatch").__call__(var2, var1.getlocal(6), var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("_dispatch"), (PyObject)var1.getglobal("None")), var1.getlocal(0).__getattr__("path"));
               var1.setlocal(7, var7);
               var3 = null;
            } catch (Throwable var6) {
               var3 = Py.setException(var6, var1);
               if (var3.match(var1.getglobal("Exception"))) {
                  var4 = var3.value;
                  var1.setlocal(8, var4);
                  var4 = null;
                  var1.setline(510);
                  var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(500));
                  var1.setline(513);
                  var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("server"), (PyObject)PyString.fromInterned("_send_traceback_header"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("server").__getattr__("_send_traceback_header");
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(515);
                     var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-exception"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(8)));
                     var1.setline(516);
                     var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-traceback"), (PyObject)var1.getglobal("traceback").__getattr__("format_exc").__call__(var2));
                  }

                  var1.setline(518);
                  var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)PyString.fromInterned("0"));
                  var1.setline(519);
                  var1.getlocal(0).__getattr__("end_headers").__call__(var2);
                  break label89;
               }

               throw var3;
            }

            var1.setline(522);
            var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200));
            var1.setline(523);
            var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("text/xml"));
            var1.setline(524);
            var4 = var1.getlocal(0).__getattr__("encode_threshold");
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(525);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var4._gt(var1.getlocal(0).__getattr__("encode_threshold"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(526);
                  var4 = var1.getlocal(0).__getattr__("accept_encodings").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gzip"), (PyObject)Py.newInteger(0));
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(527);
                  if (var1.getlocal(9).__nonzero__()) {
                     try {
                        var1.setline(529);
                        var4 = var1.getglobal("xmlrpclib").__getattr__("gzip_encode").__call__(var2, var1.getlocal(7));
                        var1.setlocal(7, var4);
                        var4 = null;
                        var1.setline(530);
                        var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Encoding"), (PyObject)PyString.fromInterned("gzip"));
                     } catch (Throwable var5) {
                        PyException var9 = Py.setException(var5, var1);
                        if (!var9.match(var1.getglobal("NotImplementedError"))) {
                           throw var9;
                        }

                        var1.setline(532);
                     }
                  }
               }
            }

            var1.setline(533);
            var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(7))));
            var1.setline(534);
            var1.getlocal(0).__getattr__("end_headers").__call__(var2);
            var1.setline(535);
            var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getlocal(7));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject decode_request_content$20(PyFrame var1, ThreadState var2) {
      var1.setline(539);
      PyObject var3 = var1.getlocal(0).__getattr__("headers").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-encoding"), (PyObject)PyString.fromInterned("identity")).__getattr__("lower").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(540);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("identity"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(541);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(542);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("gzip"));
         var4 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(544);
               var3 = var1.getglobal("xmlrpclib").__getattr__("gzip_decode").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getglobal("NotImplementedError"))) {
                  var1.setline(546);
                  var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(501), (PyObject)PyString.fromInterned("encoding %r not supported")._mod(var1.getlocal(2)));
               } else {
                  if (!var6.match(var1.getglobal("ValueError"))) {
                     throw var6;
                  }

                  var1.setline(548);
                  var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(400), (PyObject)PyString.fromInterned("error decoding gzip content"));
               }
            }
         } else {
            var1.setline(550);
            var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(501), (PyObject)PyString.fromInterned("encoding %r not supported")._mod(var1.getlocal(2)));
         }

         var1.setline(551);
         var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)PyString.fromInterned("0"));
         var1.setline(552);
         var1.getlocal(0).__getattr__("end_headers").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject report_404$21(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(404));
      var1.setline(557);
      PyString var3 = PyString.fromInterned("No such page");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(558);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(559);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))));
      var1.setline(560);
      var1.getlocal(0).__getattr__("end_headers").__call__(var2);
      var1.setline(561);
      var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_request$22(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyString.fromInterned("Selectively log an accepted request.");
      var1.setline(566);
      if (var1.getlocal(0).__getattr__("server").__getattr__("logRequests").__nonzero__()) {
         var1.setline(567);
         var1.getglobal("BaseHTTPServer").__getattr__("BaseHTTPRequestHandler").__getattr__("log_request").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SimpleXMLRPCServer$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple XML-RPC server.\n\n    Simple XML-RPC server that allows functions and a single instance\n    to be installed to handle requests. The default implementation\n    attempts to dispatch XML-RPC calls to the functions or instance\n    installed in the server. Override the _dispatch method inhereted\n    from SimpleXMLRPCDispatcher to change this behavior.\n    "));
      var1.setline(578);
      PyString.fromInterned("Simple XML-RPC server.\n\n    Simple XML-RPC server that allows functions and a single instance\n    to be installed to handle requests. The default implementation\n    attempts to dispatch XML-RPC calls to the functions or instance\n    installed in the server. Override the _dispatch method inhereted\n    from SimpleXMLRPCDispatcher to change this behavior.\n    ");
      var1.setline(580);
      PyObject var3 = var1.getname("True");
      var1.setlocal("allow_reuse_address", var3);
      var3 = null;
      var1.setline(586);
      var3 = var1.getname("False");
      var1.setlocal("_send_traceback_header", var3);
      var3 = null;
      var1.setline(588);
      PyObject[] var4 = new PyObject[]{var1.getname("SimpleXMLRPCRequestHandler"), var1.getname("True"), var1.getname("False"), var1.getname("None"), var1.getname("True")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$24, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(590);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("logRequests", var3);
      var3 = null;
      var1.setline(592);
      var1.getglobal("SimpleXMLRPCDispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(5));
      var1.setline(593);
      var1.getglobal("SocketServer").__getattr__("TCPServer").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(6));
      var1.setline(598);
      var3 = var1.getglobal("fcntl");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("fcntl"), (PyObject)PyString.fromInterned("FD_CLOEXEC"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(599);
         var3 = var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(0).__getattr__("fileno").__call__(var2), var1.getglobal("fcntl").__getattr__("F_GETFD"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(600);
         var3 = var1.getlocal(7);
         var3 = var3._ior(var1.getglobal("fcntl").__getattr__("FD_CLOEXEC"));
         var1.setlocal(7, var3);
         var1.setline(601);
         var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(0).__getattr__("fileno").__call__(var2), var1.getglobal("fcntl").__getattr__("F_SETFD"), var1.getlocal(7));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MultiPathXMLRPCServer$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Multipath XML-RPC Server\n    This specialization of SimpleXMLRPCServer allows the user to create\n    multiple Dispatcher instances and assign them to different\n    HTTP request paths.  This makes it possible to run two or more\n    'virtual XML-RPC servers' at the same port.\n    Make sure that the requestHandler accepts the paths in question.\n    "));
      var1.setline(610);
      PyString.fromInterned("Multipath XML-RPC Server\n    This specialization of SimpleXMLRPCServer allows the user to create\n    multiple Dispatcher instances and assign them to different\n    HTTP request paths.  This makes it possible to run two or more\n    'virtual XML-RPC servers' at the same port.\n    Make sure that the requestHandler accepts the paths in question.\n    ");
      var1.setline(611);
      PyObject[] var3 = new PyObject[]{var1.getname("SimpleXMLRPCRequestHandler"), var1.getname("True"), var1.getname("False"), var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$26, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_dispatcher$27, (PyObject)null);
      var1.setlocal("add_dispatcher", var4);
      var3 = null;
      var1.setline(624);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_dispatcher$28, (PyObject)null);
      var1.setlocal("get_dispatcher", var4);
      var3 = null;
      var1.setline(627);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _marshaled_dispatch$29, (PyObject)null);
      var1.setlocal("_marshaled_dispatch", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$26(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyObject var10000 = var1.getglobal("SimpleXMLRPCServer").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      var10000.__call__(var2, var3);
      var1.setline(616);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"dispatchers", var4);
      var3 = null;
      var1.setline(617);
      PyObject var5 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("allow_none", var5);
      var3 = null;
      var1.setline(618);
      var5 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("encoding", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_dispatcher$27(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("dispatchers").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(622);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_dispatcher$28(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyObject var3 = var1.getlocal(0).__getattr__("dispatchers").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _marshaled_dispatch$29(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(629);
         var3 = var1.getlocal(0).__getattr__("dispatchers").__getitem__(var1.getlocal(3)).__getattr__("_marshaled_dispatch").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var7) {
         Py.setException(var7, var1);
         var1.setline(635);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var4 = null;
         var1.setline(636);
         PyObject var10000 = var1.getglobal("xmlrpclib").__getattr__("dumps");
         PyObject[] var8 = new PyObject[]{var1.getglobal("xmlrpclib").__getattr__("Fault").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)}))), var1.getlocal(0).__getattr__("encoding"), var1.getlocal(0).__getattr__("allow_none")};
         String[] var9 = new String[]{"encoding", "allow_none"};
         var10000 = var10000.__call__(var2, var8, var9);
         var4 = null;
         var4 = var10000;
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(639);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CGIXMLRPCRequestHandler$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple handler for XML-RPC data passed through CGI."));
      var1.setline(642);
      PyString.fromInterned("Simple handler for XML-RPC data passed through CGI.");
      var1.setline(644);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$31, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_xmlrpc$32, PyString.fromInterned("Handle a single XML-RPC request"));
      var1.setlocal("handle_xmlrpc", var4);
      var3 = null;
      var1.setline(657);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_get$33, PyString.fromInterned("Handle a single HTTP GET request.\n\n        Default implementation indicates an error because\n        XML-RPC uses the POST method.\n        "));
      var1.setlocal("handle_get", var4);
      var3 = null;
      var1.setline(680);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, handle_request$34, PyString.fromInterned("Handle a single XML-RPC request passed through a CGI post method.\n\n        If no XML data is given then it is read from stdin. The resulting\n        XML-RPC response is printed to stdout along with the correct HTTP\n        headers.\n        "));
      var1.setlocal("handle_request", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$31(PyFrame var1, ThreadState var2) {
      var1.setline(645);
      var1.getglobal("SimpleXMLRPCDispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_xmlrpc$32(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyString.fromInterned("Handle a single XML-RPC request");
      var1.setline(650);
      PyObject var3 = var1.getlocal(0).__getattr__("_marshaled_dispatch").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(652);
      Py.println(PyString.fromInterned("Content-Type: text/xml"));
      var1.setline(653);
      Py.println(PyString.fromInterned("Content-Length: %d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
      var1.setline(654);
      Py.println();
      var1.setline(655);
      var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_get$33(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      PyString.fromInterned("Handle a single HTTP GET request.\n\n        Default implementation indicates an error because\n        XML-RPC uses the POST method.\n        ");
      var1.setline(664);
      PyInteger var3 = Py.newInteger(400);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(665);
      PyObject var6 = var1.getglobal("BaseHTTPServer").__getattr__("BaseHTTPRequestHandler").__getattr__("responses").__getitem__(var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(668);
      var6 = var1.getglobal("BaseHTTPServer").__getattr__("DEFAULT_ERROR_MESSAGE")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("code"), var1.getlocal(1), PyString.fromInterned("message"), var1.getlocal(2), PyString.fromInterned("explain"), var1.getlocal(3)}));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(674);
      Py.println(PyString.fromInterned("Status: %d %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(675);
      Py.println(PyString.fromInterned("Content-Type: %s")._mod(var1.getglobal("BaseHTTPServer").__getattr__("DEFAULT_ERROR_CONTENT_TYPE")));
      var1.setline(676);
      Py.println(PyString.fromInterned("Content-Length: %d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(4))));
      var1.setline(677);
      Py.println();
      var1.setline(678);
      var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_request$34(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyString.fromInterned("Handle a single XML-RPC request passed through a CGI post method.\n\n        If no XML data is given then it is read from stdin. The resulting\n        XML-RPC response is printed to stdout along with the correct HTTP\n        headers.\n        ");
      var1.setline(688);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REQUEST_METHOD"), (PyObject)var1.getglobal("None"));
         var10000 = var3._eq(PyString.fromInterned("GET"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(690);
         var1.getlocal(0).__getattr__("handle_get").__call__(var2);
      } else {
         try {
            var1.setline(694);
            var3 = var1.getglobal("int").__call__(var2, var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONTENT_LENGTH"), (PyObject)var1.getglobal("None")));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("ValueError")}))) {
               throw var6;
            }

            var1.setline(696);
            PyInteger var4 = Py.newInteger(-1);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(697);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(698);
            var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("read").__call__(var2, var1.getlocal(2));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(700);
         var1.getlocal(0).__getattr__("handle_xmlrpc").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$35(PyFrame var1, ThreadState var2) {
      var1.setline(706);
      PyObject var3 = var1.getlocal(0)._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public SimpleXMLRPCServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj", "attr", "allow_dotted_names", "attrs", "i"};
      resolve_dotted_attribute$1 = Py.newCode(3, var2, var1, "resolve_dotted_attribute", 115, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "_[143_12]", "member"};
      list_public_methods$2 = Py.newCode(1, var2, var1, "list_public_methods", 139, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lst", "u", "x"};
      remove_duplicates$3 = Py.newCode(1, var2, var1, "remove_duplicates", 147, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleXMLRPCDispatcher$4 = Py.newCode(0, var2, var1, "SimpleXMLRPCDispatcher", 160, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "allow_none", "encoding"};
      __init__$5 = Py.newCode(3, var2, var1, "__init__", 169, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "instance", "allow_dotted_names"};
      register_instance$6 = Py.newCode(3, var2, var1, "register_instance", 175, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "function", "name"};
      register_function$7 = Py.newCode(3, var2, var1, "register_function", 211, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      register_introspection_functions$8 = Py.newCode(1, var2, var1, "register_introspection_functions", 222, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      register_multicall_functions$9 = Py.newCode(1, var2, var1, "register_multicall_functions", 233, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "dispatch_method", "path", "params", "method", "response", "fault", "exc_type", "exc_value", "exc_tb"};
      _marshaled_dispatch$10 = Py.newCode(4, var2, var1, "_marshaled_dispatch", 241, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "methods"};
      system_listMethods$11 = Py.newCode(1, var2, var1, "system_listMethods", 278, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method_name"};
      system_methodSignature$12 = Py.newCode(2, var2, var1, "system_methodSignature", 301, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method_name", "method", "pydoc"};
      system_methodHelp$13 = Py.newCode(2, var2, var1, "system_methodHelp", 314, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "call_list", "results", "call", "method_name", "params", "fault", "exc_type", "exc_value", "exc_tb"};
      system_multicall$14 = Py.newCode(2, var2, var1, "system_multicall", 346, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "method", "params", "func"};
      _dispatch$15 = Py.newCode(3, var2, var1, "_dispatch", 378, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleXMLRPCRequestHandler$16 = Py.newCode(0, var2, var1, "SimpleXMLRPCRequestHandler", 424, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "r", "ae", "e", "match", "v"};
      accept_encodings$17 = Py.newCode(1, var2, var1, "accept_encodings", 449, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_rpc_path_valid$18 = Py.newCode(1, var2, var1, "is_rpc_path_valid", 460, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "max_chunk_size", "size_remaining", "L", "chunk_size", "chunk", "data", "response", "e", "q"};
      do_POST$19 = Py.newCode(1, var2, var1, "do_POST", 467, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "encoding"};
      decode_request_content$20 = Py.newCode(2, var2, var1, "decode_request_content", 537, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "response"};
      report_404$21 = Py.newCode(1, var2, var1, "report_404", 554, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "size"};
      log_request$22 = Py.newCode(3, var2, var1, "log_request", 563, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleXMLRPCServer$23 = Py.newCode(0, var2, var1, "SimpleXMLRPCServer", 569, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "addr", "requestHandler", "logRequests", "allow_none", "encoding", "bind_and_activate", "flags"};
      __init__$24 = Py.newCode(7, var2, var1, "__init__", 588, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MultiPathXMLRPCServer$25 = Py.newCode(0, var2, var1, "MultiPathXMLRPCServer", 603, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "addr", "requestHandler", "logRequests", "allow_none", "encoding", "bind_and_activate"};
      __init__$26 = Py.newCode(7, var2, var1, "__init__", 611, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "dispatcher"};
      add_dispatcher$27 = Py.newCode(3, var2, var1, "add_dispatcher", 620, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      get_dispatcher$28 = Py.newCode(2, var2, var1, "get_dispatcher", 624, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "dispatch_method", "path", "response", "exc_type", "exc_value"};
      _marshaled_dispatch$29 = Py.newCode(4, var2, var1, "_marshaled_dispatch", 627, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CGIXMLRPCRequestHandler$30 = Py.newCode(0, var2, var1, "CGIXMLRPCRequestHandler", 641, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "allow_none", "encoding"};
      __init__$31 = Py.newCode(3, var2, var1, "__init__", 644, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request_text", "response"};
      handle_xmlrpc$32 = Py.newCode(2, var2, var1, "handle_xmlrpc", 647, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "message", "explain", "response"};
      handle_get$33 = Py.newCode(1, var2, var1, "handle_get", 657, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request_text", "length"};
      handle_request$34 = Py.newCode(2, var2, var1, "handle_request", 680, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$35 = Py.newCode(2, var2, var1, "<lambda>", 706, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new SimpleXMLRPCServer$py("SimpleXMLRPCServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(SimpleXMLRPCServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.resolve_dotted_attribute$1(var2, var3);
         case 2:
            return this.list_public_methods$2(var2, var3);
         case 3:
            return this.remove_duplicates$3(var2, var3);
         case 4:
            return this.SimpleXMLRPCDispatcher$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.register_instance$6(var2, var3);
         case 7:
            return this.register_function$7(var2, var3);
         case 8:
            return this.register_introspection_functions$8(var2, var3);
         case 9:
            return this.register_multicall_functions$9(var2, var3);
         case 10:
            return this._marshaled_dispatch$10(var2, var3);
         case 11:
            return this.system_listMethods$11(var2, var3);
         case 12:
            return this.system_methodSignature$12(var2, var3);
         case 13:
            return this.system_methodHelp$13(var2, var3);
         case 14:
            return this.system_multicall$14(var2, var3);
         case 15:
            return this._dispatch$15(var2, var3);
         case 16:
            return this.SimpleXMLRPCRequestHandler$16(var2, var3);
         case 17:
            return this.accept_encodings$17(var2, var3);
         case 18:
            return this.is_rpc_path_valid$18(var2, var3);
         case 19:
            return this.do_POST$19(var2, var3);
         case 20:
            return this.decode_request_content$20(var2, var3);
         case 21:
            return this.report_404$21(var2, var3);
         case 22:
            return this.log_request$22(var2, var3);
         case 23:
            return this.SimpleXMLRPCServer$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.MultiPathXMLRPCServer$25(var2, var3);
         case 26:
            return this.__init__$26(var2, var3);
         case 27:
            return this.add_dispatcher$27(var2, var3);
         case 28:
            return this.get_dispatcher$28(var2, var3);
         case 29:
            return this._marshaled_dispatch$29(var2, var3);
         case 30:
            return this.CGIXMLRPCRequestHandler$30(var2, var3);
         case 31:
            return this.__init__$31(var2, var3);
         case 32:
            return this.handle_xmlrpc$32(var2, var3);
         case 33:
            return this.handle_get$33(var2, var3);
         case 34:
            return this.handle_request$34(var2, var3);
         case 35:
            return this.f$35(var2, var3);
         default:
            return null;
      }
   }
}

package modjy;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_wsgi.py")
public class modjy_wsgi$py extends PyFunctionTable implements PyRunnable {
   static modjy_wsgi$py self;
   static final PyCode f$0;
   static final PyCode modjy_wsgi$1;
   static final PyCode set_string_envvar$2;
   static final PyCode set_string_envvar_optional$3;
   static final PyCode set_int_envvar$4;
   static final PyCode set_container_specific_wsgi_vars$5;
   static final PyCode set_j2ee_specific_wsgi_vars$6;
   static final PyCode set_required_cgi_environ$7;
   static final PyCode set_other_cgi_environ$8;
   static final PyCode set_http_header_environ$9;
   static final PyCode set_required_wsgi_vars$10;
   static final PyCode set_wsgi_streams$11;
   static final PyCode set_wsgi_classes$12;
   static final PyCode set_user_specified_environment$13;
   static final PyCode set_wsgi_environment$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      String[] var3 = new String[]{"System"};
      PyObject[] var7 = imp.importFrom("java.lang", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("System", var4);
      var4 = null;

      PyObject var9;
      try {
         var1.setline(24);
         var3 = new String[]{"FileUtil"};
         var7 = imp.importFrom("org.python.core.util", var3, var1, -1);
         var4 = var7[0];
         var1.setlocal("FileUtil", var4);
         var4 = null;
         var1.setline(25);
         var9 = var1.getname("FileUtil").__getattr__("wrap");
         var1.setlocal("create_py_file", var9);
         var3 = null;
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(27);
         String[] var10 = new String[]{"PyFile"};
         PyObject[] var11 = imp.importFrom("org.python.core", var10, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("PyFile", var5);
         var5 = null;
         var1.setline(28);
         var4 = var1.getname("PyFile");
         var1.setlocal("create_py_file", var4);
         var4 = null;
      }

      var1.setline(30);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(31);
      var3 = new String[]{"modjy_input_object"};
      var7 = imp.importFrom("modjy_input", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("modjy_input_object", var4);
      var4 = null;
      var1.setline(33);
      PyString var12 = PyString.fromInterned("modjy");
      var1.setlocal("server_name", var12);
      var3 = null;
      var1.setline(34);
      var9 = PyString.fromInterned("%s.param")._mod(var1.getname("server_name"));
      var1.setlocal("server_param_prefix", var9);
      var3 = null;
      var1.setline(35);
      var12 = PyString.fromInterned("j2ee");
      var1.setlocal("j2ee_ns_prefix", var12);
      var3 = null;
      var1.setline(37);
      var12 = PyString.fromInterned("iso-8859-1");
      var1.setlocal("cgi_var_char_encoding", var12);
      var3 = null;
      var1.setline(39);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("modjy_wsgi", var7, modjy_wsgi$1);
      var1.setlocal("modjy_wsgi", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_wsgi$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      PyUnicode var3 = PyUnicode.fromInterned("");
      var1.setlocal("empty_pystring", var3);
      var3 = null;
      var1.setline(46);
      PyTuple var4 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0)});
      var1.setlocal("wsgi_version", var4);
      var3 = null;
      var1.setline(52);
      var4 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(25), Py.newInteger(3)});
      var1.setlocal("modjy_version", var4);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, set_string_envvar$2, (PyObject)null);
      var1.setlocal("set_string_envvar", var6);
      var3 = null;
      var1.setline(60);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_string_envvar_optional$3, (PyObject)null);
      var1.setlocal("set_string_envvar_optional", var6);
      var3 = null;
      var1.setline(64);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_int_envvar$4, (PyObject)null);
      var1.setlocal("set_int_envvar", var6);
      var3 = null;
      var1.setline(71);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_container_specific_wsgi_vars$5, (PyObject)null);
      var1.setlocal("set_container_specific_wsgi_vars", var6);
      var3 = null;
      var1.setline(76);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_j2ee_specific_wsgi_vars$6, (PyObject)null);
      var1.setlocal("set_j2ee_specific_wsgi_vars", var6);
      var3 = null;
      var1.setline(80);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_required_cgi_environ$7, (PyObject)null);
      var1.setlocal("set_required_cgi_environ", var6);
      var3 = null;
      var1.setline(88);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_other_cgi_environ$8, (PyObject)null);
      var1.setlocal("set_other_cgi_environ", var6);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_http_header_environ$9, (PyObject)null);
      var1.setlocal("set_http_header_environ", var6);
      var3 = null;
      var1.setline(117);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_required_wsgi_vars$10, (PyObject)null);
      var1.setlocal("set_required_wsgi_vars", var6);
      var3 = null;
      var1.setline(127);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_wsgi_streams$11, (PyObject)null);
      var1.setlocal("set_wsgi_streams", var6);
      var3 = null;
      var1.setline(134);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_wsgi_classes$12, (PyObject)null);
      var1.setlocal("set_wsgi_classes", var6);
      var3 = null;
      var1.setline(138);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_user_specified_environment$13, (PyObject)null);
      var1.setlocal("set_user_specified_environment", var6);
      var3 = null;
      var1.setline(148);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_wsgi_environment$14, (PyObject)null);
      var1.setlocal("set_wsgi_environment", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject set_string_envvar$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         var3 = var1.getlocal(0).__getattr__("empty_pystring");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(57);
      var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getglobal("cgi_var_char_encoding"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(3);
      var1.getlocal(1).__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_string_envvar_optional$3(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._ne(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(62);
         var1.getlocal(0).__getattr__("set_string_envvar").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_int_envvar$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(66);
         var3 = var1.getlocal(0).__getattr__("empty_pystring");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(68);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(69);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_container_specific_wsgi_vars$5(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(0).__getattr__("modjy_version");
      var1.getlocal(3).__setitem__(PyString.fromInterned("%s.version")._mod(var1.getglobal("server_name")), var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getlocal(4).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(73);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(74);
         PyObject var5 = var1.getlocal(4).__getitem__(var1.getlocal(5));
         var1.getlocal(3).__setitem__(PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("server_param_prefix"), var1.getlocal(5)})), var5);
         var5 = null;
      }
   }

   public PyObject set_j2ee_specific_wsgi_vars$6(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(2).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(77);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(78);
         PyObject var5 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var1.getlocal(1).__setitem__(PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("j2ee_ns_prefix"), var1.getlocal(3)})), var5);
         var5 = null;
      }
   }

   public PyObject set_required_cgi_environ$7(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("REQUEST_METHOD"), (PyObject)var1.getlocal(1).__getattr__("getMethod").__call__(var2));
      var1.setline(82);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("QUERY_STRING"), (PyObject)var1.getlocal(1).__getattr__("getQueryString").__call__(var2));
      var1.setline(83);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("CONTENT_TYPE"), (PyObject)var1.getlocal(1).__getattr__("getContentType").__call__(var2));
      var1.setline(84);
      var1.getlocal(0).__getattr__("set_int_envvar").__call__(var2, var1.getlocal(3), PyString.fromInterned("CONTENT_LENGTH"), var1.getlocal(1).__getattr__("getContentLength").__call__(var2), Py.newInteger(-1));
      var1.setline(85);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("SERVER_NAME"), (PyObject)var1.getlocal(1).__getattr__("getLocalName").__call__(var2));
      var1.setline(86);
      var1.getlocal(0).__getattr__("set_int_envvar").__call__(var2, var1.getlocal(3), PyString.fromInterned("SERVER_PORT"), var1.getlocal(1).__getattr__("getLocalPort").__call__(var2), Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_other_cgi_environ$8(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      if (var1.getlocal(1).__getattr__("isSecure").__call__(var2).__nonzero__()) {
         var1.setline(90);
         var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("HTTPS"), (PyObject)PyString.fromInterned("on"));
      } else {
         var1.setline(92);
         var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("HTTPS"), (PyObject)PyString.fromInterned("off"));
      }

      var1.setline(93);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("SERVER_PROTOCOL"), (PyObject)var1.getlocal(1).__getattr__("getProtocol").__call__(var2));
      var1.setline(94);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("REMOTE_HOST"), (PyObject)var1.getlocal(1).__getattr__("getRemoteHost").__call__(var2));
      var1.setline(95);
      var1.getlocal(0).__getattr__("set_string_envvar").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("REMOTE_ADDR"), (PyObject)var1.getlocal(1).__getattr__("getRemoteAddr").__call__(var2));
      var1.setline(96);
      var1.getlocal(0).__getattr__("set_int_envvar").__call__(var2, var1.getlocal(3), PyString.fromInterned("REMOTE_PORT"), var1.getlocal(1).__getattr__("getRemotePort").__call__(var2), Py.newInteger(-1));
      var1.setline(97);
      var1.getlocal(0).__getattr__("set_string_envvar_optional").__call__(var2, var1.getlocal(3), PyString.fromInterned("AUTH_TYPE"), var1.getlocal(1).__getattr__("getAuthType").__call__(var2), var1.getglobal("None"));
      var1.setline(98);
      var1.getlocal(0).__getattr__("set_string_envvar_optional").__call__(var2, var1.getlocal(3), PyString.fromInterned("REMOTE_USER"), var1.getlocal(1).__getattr__("getRemoteUser").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_http_header_environ$9(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(1).__getattr__("getHeaderNames").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(102);
         if (!var1.getlocal(4).__getattr__("hasMoreElements").__call__(var2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(103);
         var3 = var1.getlocal(4).__getattr__("nextElement").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(104);
         var3 = var1.getglobal("None");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(105);
         var3 = var1.getlocal(1).__getattr__("getHeaders").__call__(var2, var1.getlocal(5));
         var1.setlocal(7, var3);
         var3 = null;

         while(true) {
            var1.setline(106);
            if (!var1.getlocal(7).__getattr__("hasMoreElements").__call__(var2).__nonzero__()) {
               var1.setline(115);
               var3 = var1.getlocal(6);
               var1.getlocal(3).__setitem__(PyString.fromInterned("HTTP_%s")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(5)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_")).__getattr__("upper").__call__(var2)), var3);
               var3 = null;
               break;
            }

            var1.setline(107);
            var3 = var1.getlocal(7).__getattr__("nextElement").__call__(var2).__getattr__("encode").__call__(var2, var1.getglobal("cgi_var_char_encoding"));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(108);
            var3 = var1.getlocal(6);
            PyObject var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(109);
               var3 = var1.getlocal(8);
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(111);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("list")).__nonzero__()) {
                  var1.setline(112);
                  var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
               } else {
                  var1.setline(114);
                  PyList var4 = new PyList(new PyObject[]{var1.getlocal(6)});
                  var1.setlocal(6, var4);
                  var3 = null;
               }
            }
         }
      }
   }

   public PyObject set_required_wsgi_vars$10(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getattr__("wsgi_version");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.version"), var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(1).__getattr__("getScheme").__call__(var2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.url_scheme"), var3);
      var3 = null;
      var1.setline(120);
      PyObject var10000 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(PyString.fromInterned("%s.cache_callables")._mod(var1.getglobal("server_param_prefix"))));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(PyString.fromInterned("%s.multithread")._mod(var1.getglobal("server_param_prefix"))));
      }

      var3 = var10000;
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.multithread"), var3);
      var3 = null;
      var1.setline(124);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.multiprocess"), var4);
      var1.getlocal(0).__setattr__((String)"wsgi_multiprocess", var4);
      var1.setline(125);
      var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("%s.cache_callables")._mod(var1.getglobal("server_param_prefix"))).__not__();
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.run_once"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_wsgi_streams$11(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(129);
         PyObject var6 = var1.getglobal("modjy_input_object").__call__(var2, var1.getlocal(1).__getattr__("getInputStream").__call__(var2));
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.input"), var6);
         var3 = null;
         var1.setline(130);
         var6 = var1.getglobal("create_py_file").__call__(var2, var1.getglobal("System").__getattr__("err"));
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("wsgi.errors"), var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOException"))) {
            PyObject var4 = var3.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(132);
            throw Py.makeException(var1.getglobal("ModjyIOException").__call__(var2, var1.getlocal(4)));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_wsgi_classes$12(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_user_specified_environment$13(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyObject var10000 = var1.getlocal(4).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("initial_env")).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4).__getitem__(PyString.fromInterned("initial_env")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(140);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(141);
         PyObject var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("initial_env"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(142);
         var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(142);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(6, var4);
            var1.setline(143);
            PyObject var5 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(144);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(145);
               var5 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(7, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(8, var7);
               var7 = null;
               var5 = null;
               var1.setline(146);
               var5 = var1.getlocal(8).__getattr__("strip").__call__(var2);
               var1.getlocal(3).__setitem__(var1.getlocal(7).__getattr__("strip").__call__(var2), var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject set_wsgi_environment$14(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      var1.getlocal(0).__getattr__("set_container_specific_wsgi_vars").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setline(150);
      var1.getlocal(0).__getattr__("set_j2ee_specific_wsgi_vars").__call__(var2, var1.getlocal(3), var1.getlocal(5));
      var1.setline(151);
      var1.getlocal(0).__getattr__("set_required_cgi_environ").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(152);
      var1.getlocal(0).__getattr__("set_other_cgi_environ").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(153);
      var1.getlocal(0).__getattr__("set_http_header_environ").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(154);
      var1.getlocal(0).__getattr__("set_required_wsgi_vars").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(155);
      var1.getlocal(0).__getattr__("set_wsgi_streams").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(156);
      var1.getlocal(0).__getattr__("set_wsgi_classes").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(157);
      var1.getlocal(0).__getattr__("set_user_specified_environment").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public modjy_wsgi$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_wsgi$1 = Py.newCode(0, var2, var1, "modjy_wsgi", 39, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dict", "name", "value"};
      set_string_envvar$2 = Py.newCode(4, var2, var1, "set_string_envvar", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "name", "value", "default_value"};
      set_string_envvar_optional$3 = Py.newCode(5, var2, var1, "set_string_envvar_optional", 60, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "name", "value", "default_value"};
      set_int_envvar$4 = Py.newCode(5, var2, var1, "set_int_envvar", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict", "params", "pname"};
      set_container_specific_wsgi_vars$5 = Py.newCode(5, var2, var1, "set_container_specific_wsgi_vars", 71, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "j2ee_ns", "p"};
      set_j2ee_specific_wsgi_vars$6 = Py.newCode(3, var2, var1, "set_j2ee_specific_wsgi_vars", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict"};
      set_required_cgi_environ$7 = Py.newCode(4, var2, var1, "set_required_cgi_environ", 80, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict"};
      set_other_cgi_environ$8 = Py.newCode(4, var2, var1, "set_other_cgi_environ", 88, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict", "header_name_enum", "curr_header_name", "values", "values_enum", "next_value"};
      set_http_header_environ$9 = Py.newCode(4, var2, var1, "set_http_header_environ", 100, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict"};
      set_required_wsgi_vars$10 = Py.newCode(4, var2, var1, "set_required_wsgi_vars", 117, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict", "iox"};
      set_wsgi_streams$11 = Py.newCode(4, var2, var1, "set_wsgi_streams", 127, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "dict"};
      set_wsgi_classes$12 = Py.newCode(4, var2, var1, "set_wsgi_classes", 134, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "wsgi_environ", "params", "user_env_string", "l", "name", "value"};
      set_user_specified_environment$13 = Py.newCode(5, var2, var1, "set_user_specified_environment", 138, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "wsgi_environ", "params", "j2ee_ns"};
      set_wsgi_environment$14 = Py.newCode(6, var2, var1, "set_wsgi_environment", 148, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_wsgi$py("modjy/modjy_wsgi$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_wsgi$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_wsgi$1(var2, var3);
         case 2:
            return this.set_string_envvar$2(var2, var3);
         case 3:
            return this.set_string_envvar_optional$3(var2, var3);
         case 4:
            return this.set_int_envvar$4(var2, var3);
         case 5:
            return this.set_container_specific_wsgi_vars$5(var2, var3);
         case 6:
            return this.set_j2ee_specific_wsgi_vars$6(var2, var3);
         case 7:
            return this.set_required_cgi_environ$7(var2, var3);
         case 8:
            return this.set_other_cgi_environ$8(var2, var3);
         case 9:
            return this.set_http_header_environ$9(var2, var3);
         case 10:
            return this.set_required_wsgi_vars$10(var2, var3);
         case 11:
            return this.set_wsgi_streams$11(var2, var3);
         case 12:
            return this.set_wsgi_classes$12(var2, var3);
         case 13:
            return this.set_user_specified_environment$13(var2, var3);
         case 14:
            return this.set_wsgi_environment$14(var2, var3);
         default:
            return null;
      }
   }
}

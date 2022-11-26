package modjy;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy.py")
public class modjy$py extends PyFunctionTable implements PyRunnable {
   static modjy.modjy$py self;
   static final PyCode f$0;
   static final PyCode modjy_servlet$1;
   static final PyCode __init__$2;
   static final PyCode do_param$3;
   static final PyCode process_param_container$4;
   static final PyCode get_params$5;
   static final PyCode init$6;
   static final PyCode service$7;
   static final PyCode get_j2ee_ns$8;
   static final PyCode dispatch_to_application$9;
   static final PyCode call_application$10;
   static final PyCode expand_relative_path$11;
   static final PyCode raise_exc$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("jarray", var1, -1);
      var1.setlocal("jarray", var3);
      var3 = null;
      var1.setline(22);
      var3 = imp.importOne("synchronize", var1, -1);
      var1.setlocal("synchronize", var3);
      var3 = null;
      var1.setline(23);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(24);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(26);
      var1.getname("sys").__getattr__("add_package").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("javax.servlet"));
      var1.setline(27);
      var1.getname("sys").__getattr__("add_package").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("javax.servlet.http"));
      var1.setline(28);
      var1.getname("sys").__getattr__("add_package").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("org.python.core"));
      var1.setline(30);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(31);
      imp.importAll("modjy_log", var1, -1);
      var1.setline(32);
      String[] var5 = new String[]{"modjy_param_mgr", "modjy_servlet_params"};
      PyObject[] var6 = imp.importFrom("modjy_params", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("modjy_param_mgr", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("modjy_servlet_params", var4);
      var4 = null;
      var1.setline(33);
      var5 = new String[]{"modjy_wsgi"};
      var6 = imp.importFrom("modjy_wsgi", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("modjy_wsgi", var4);
      var4 = null;
      var1.setline(34);
      var5 = new String[]{"start_response_object"};
      var6 = imp.importFrom("modjy_response", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("start_response_object", var4);
      var4 = null;
      var1.setline(35);
      var5 = new String[]{"modjy_impl"};
      var6 = imp.importFrom("modjy_impl", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("modjy_impl", var4);
      var4 = null;
      var1.setline(36);
      var5 = new String[]{"modjy_publisher"};
      var6 = imp.importFrom("modjy_publish", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("modjy_publisher", var4);
      var4 = null;
      var1.setline(38);
      var5 = new String[]{"HttpServlet"};
      var6 = imp.importFrom("javax.servlet.http", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("HttpServlet", var4);
      var4 = null;
      var1.setline(40);
      var6 = new PyObject[]{var1.getname("HttpServlet"), var1.getname("modjy_publisher"), var1.getname("modjy_wsgi"), var1.getname("modjy_impl")};
      var4 = Py.makeClass("modjy_servlet", var6, modjy_servlet$1);
      var1.setlocal("modjy_servlet", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_servlet$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_param$3, (PyObject)null);
      var1.setlocal("do_param", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process_param_container$4, (PyObject)null);
      var1.setlocal("process_param_container", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_params$5, (PyObject)null);
      var1.setlocal("get_params", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init$6, (PyObject)null);
      var1.setlocal("init", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, service$7, (PyObject)null);
      var1.setlocal("service", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_j2ee_ns$8, (PyObject)null);
      var1.setlocal("get_j2ee_ns", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dispatch_to_application$9, (PyObject)null);
      var1.setlocal("dispatch_to_application", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, call_application$10, (PyObject)null);
      var1.setlocal("call_application", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, expand_relative_path$11, (PyObject)null);
      var1.setlocal("expand_relative_path", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, raise_exc$12, (PyObject)null);
      var1.setlocal("raise_exc", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.getglobal("HttpServlet").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_param$3(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("log"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("log"), PyString.fromInterned("set_%s")._mod(var1.getlocal(1))).__call__(var2, var1.getlocal(2));
      } else {
         var1.setline(49);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("params").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process_param_container$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(1).__getattr__("getInitParameterNames").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(53);
         if (!var1.getlocal(2).__getattr__("hasMoreElements").__call__(var2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(54);
         var3 = var1.getlocal(2).__getattr__("nextElement").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(55);
         var1.getlocal(0).__getattr__("do_param").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getattr__("getInitParameter").__call__(var2, var1.getlocal(3)));
      }
   }

   public PyObject get_params$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      var1.getlocal(0).__getattr__("process_param_container").__call__(var2, var1.getlocal(0).__getattr__("servlet_context"));
      var1.setline(59);
      var1.getlocal(0).__getattr__("process_param_container").__call__(var2, var1.getlocal(0).__getattr__("servlet"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject init$6(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("servlet", var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(0).__getattr__("servlet").__getattr__("getServletContext").__call__(var2);
      var1.getlocal(0).__setattr__("servlet_context", var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(0).__getattr__("servlet").__getattr__("getServletConfig").__call__(var2);
      var1.getlocal(0).__setattr__("servlet_config", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("modjy_logger").__call__(var2, var1.getlocal(0).__getattr__("servlet_context"));
      var1.getlocal(0).__setattr__("log", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getglobal("modjy_param_mgr").__call__(var2, var1.getglobal("modjy_servlet_params"));
      var1.getlocal(0).__setattr__("params", var3);
      var3 = null;
      var1.setline(67);
      var1.getlocal(0).__getattr__("get_params").__call__(var2);
      var1.setline(68);
      var1.getlocal(0).__getattr__("init_impl").__call__(var2);
      var1.setline(69);
      var1.getlocal(0).__getattr__("init_publisher").__call__(var2);
      var1.setline(70);
      var3 = imp.importOne("modjy_exceptions", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), PyString.fromInterned("%s_handler")._mod(var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("exc_handler")))).__call__(var2);
      var1.getlocal(0).__setattr__("exc_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject service$7(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(76);
         var1.getlocal(0).__getattr__("dispatch_to_application").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getglobal("ModjyException"))) {
            throw var8;
         }

         PyObject var4 = var8.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(78);
         var1.getlocal(0).__getattr__("log").__getattr__("error").__call__(var2, PyString.fromInterned("Exception servicing request: %s")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(4))));
         var1.setline(79);
         var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(7, var6);
         var6 = null;
         var4 = null;
         var1.setline(80);
         PyObject var10000 = var1.getlocal(0).__getattr__("exc_handler").__getattr__("handle");
         PyObject[] var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)})};
         var10000.__call__(var2, var9);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_j2ee_ns$8(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("servlet"), var1.getlocal(0).__getattr__("servlet"), PyString.fromInterned("servlet_context"), var1.getlocal(0).__getattr__("servlet_context"), PyString.fromInterned("servlet_config"), var1.getlocal(0).__getattr__("servlet_config"), PyString.fromInterned("request"), var1.getlocal(1), PyString.fromInterned("response"), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dispatch_to_application$9(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getlocal(0).__getattr__("get_app_object").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(93);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_wsgi_environment");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("params"), var1.getlocal(0).__getattr__("get_j2ee_ns").__call__(var2, var1.getlocal(1), var1.getlocal(2))};
      var10000.__call__(var2, var6);
      var1.setline(94);
      var3 = var1.getglobal("start_response_object").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;

      try {
         var1.setline(96);
         var3 = var1.getlocal(0).__getattr__("call_application").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(97);
         var3 = var1.getlocal(6);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(98);
            throw Py.makeException(var1.getglobal("ReturnNotIterable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Application returned None: must return an iterable")));
         }

         var1.setline(99);
         var1.getlocal(0).__getattr__("deal_with_app_return").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getlocal(6));
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         PyObject var4;
         if (var7.match(var1.getglobal("ModjyException"))) {
            var4 = var7.value;
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(101);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getlocal(7).__getattr__("__class__"), var1.getglobal("str").__call__(var2, var1.getlocal(7)));
         } else {
            if (!var7.match(var1.getglobal("Exception"))) {
               throw var7;
            }

            var4 = var7.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(103);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("ApplicationException"), var1.getglobal("str").__call__(var2, var1.getlocal(8)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject call_application$10(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("multithread")).__nonzero__()) {
         var1.setline(107);
         var3 = var1.getlocal(1).__getattr__("__call__").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(109);
         var3 = var1.getglobal("synchronize").__getattr__("apply_synchronized").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject expand_relative_path$11(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$")).__nonzero__()) {
         var1.setline(116);
         var3 = var1.getlocal(0).__getattr__("servlet").__getattr__("getServletContext").__call__(var2).__getattr__("getRealPath").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(117);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject raise_exc$12(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      var1.getlocal(0).__getattr__("log").__getattr__("error").__call__(var2, var1.getlocal(2));
      var1.setline(121);
      throw Py.makeException(var1.getlocal(1).__call__(var2, var1.getlocal(2)));
   }

   public modjy$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_servlet$1 = Py.newCode(0, var2, var1, "modjy_servlet", 40, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      do_param$3 = Py.newCode(3, var2, var1, "do_param", 45, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "param_container", "param_enum", "param_name"};
      process_param_container$4 = Py.newCode(2, var2, var1, "process_param_container", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_params$5 = Py.newCode(1, var2, var1, "get_params", 57, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "delegator", "modjy_exceptions"};
      init$6 = Py.newCode(2, var2, var1, "init", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "wsgi_environ", "mx", "typ", "value", "tb"};
      service$7 = Py.newCode(3, var2, var1, "service", 73, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp"};
      get_j2ee_ns$8 = Py.newCode(3, var2, var1, "get_j2ee_ns", 82, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "environ", "app_callable", "response_callable", "app_return", "mx", "x"};
      dispatch_to_application$9 = Py.newCode(4, var2, var1, "dispatch_to_application", 91, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "app_callable", "environ", "response_callable"};
      call_application$10 = Py.newCode(4, var2, var1, "call_application", 105, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      expand_relative_path$11 = Py.newCode(2, var2, var1, "expand_relative_path", 114, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc_class", "message"};
      raise_exc$12 = Py.newCode(3, var2, var1, "raise_exc", 119, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy.modjy$py("modjy/modjy$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy.modjy$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_servlet$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.do_param$3(var2, var3);
         case 4:
            return this.process_param_container$4(var2, var3);
         case 5:
            return this.get_params$5(var2, var3);
         case 6:
            return this.init$6(var2, var3);
         case 7:
            return this.service$7(var2, var3);
         case 8:
            return this.get_j2ee_ns$8(var2, var3);
         case 9:
            return this.dispatch_to_application$9(var2, var3);
         case 10:
            return this.call_application$10(var2, var3);
         case 11:
            return this.expand_relative_path$11(var2, var3);
         case 12:
            return this.raise_exc$12(var2, var3);
         default:
            return null;
      }
   }
}

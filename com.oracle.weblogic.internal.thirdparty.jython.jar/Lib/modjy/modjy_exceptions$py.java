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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_exceptions.py")
public class modjy_exceptions$py extends PyFunctionTable implements PyRunnable {
   static modjy_exceptions$py self;
   static final PyCode f$0;
   static final PyCode ModjyException$1;
   static final PyCode ModjyIOException$2;
   static final PyCode ConfigException$3;
   static final PyCode BadParameter$4;
   static final PyCode ApplicationNotFound$5;
   static final PyCode NoCallable$6;
   static final PyCode RequestException$7;
   static final PyCode ApplicationException$8;
   static final PyCode StartResponseNotCalled$9;
   static final PyCode StartResponseCalledTwice$10;
   static final PyCode ResponseCommitted$11;
   static final PyCode HopByHopHeaderSet$12;
   static final PyCode WrongLength$13;
   static final PyCode BadArgument$14;
   static final PyCode ReturnNotIterable$15;
   static final PyCode NonStringOutput$16;
   static final PyCode exception_handler$17;
   static final PyCode handle$18;
   static final PyCode get_status_and_message$19;
   static final PyCode testing_handler$20;
   static final PyCode handle$21;
   static final PyCode standard_handler$22;
   static final PyCode handle$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(22);
      var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;
      var1.setline(23);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(25);
      String[] var5 = new String[]{"IllegalStateException"};
      PyObject[] var6 = imp.importFrom("java.lang", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("IllegalStateException", var4);
      var4 = null;
      var1.setline(26);
      var5 = new String[]{"IOException"};
      var6 = imp.importFrom("java.io", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("IOException", var4);
      var4 = null;
      var1.setline(27);
      var5 = new String[]{"ServletException"};
      var6 = imp.importFrom("javax.servlet", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("ServletException", var4);
      var4 = null;
      var1.setline(29);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ModjyException", var6, ModjyException$1);
      var1.setlocal("ModjyException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(31);
      var6 = new PyObject[]{var1.getname("ModjyException")};
      var4 = Py.makeClass("ModjyIOException", var6, ModjyIOException$2);
      var1.setlocal("ModjyIOException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(33);
      var6 = new PyObject[]{var1.getname("ModjyException")};
      var4 = Py.makeClass("ConfigException", var6, ConfigException$3);
      var1.setlocal("ConfigException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(34);
      var6 = new PyObject[]{var1.getname("ConfigException")};
      var4 = Py.makeClass("BadParameter", var6, BadParameter$4);
      var1.setlocal("BadParameter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(35);
      var6 = new PyObject[]{var1.getname("ConfigException")};
      var4 = Py.makeClass("ApplicationNotFound", var6, ApplicationNotFound$5);
      var1.setlocal("ApplicationNotFound", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(36);
      var6 = new PyObject[]{var1.getname("ConfigException")};
      var4 = Py.makeClass("NoCallable", var6, NoCallable$6);
      var1.setlocal("NoCallable", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(38);
      var6 = new PyObject[]{var1.getname("ModjyException")};
      var4 = Py.makeClass("RequestException", var6, RequestException$7);
      var1.setlocal("RequestException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(40);
      var6 = new PyObject[]{var1.getname("ModjyException")};
      var4 = Py.makeClass("ApplicationException", var6, ApplicationException$8);
      var1.setlocal("ApplicationException", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(41);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("StartResponseNotCalled", var6, StartResponseNotCalled$9);
      var1.setlocal("StartResponseNotCalled", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(42);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("StartResponseCalledTwice", var6, StartResponseCalledTwice$10);
      var1.setlocal("StartResponseCalledTwice", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(43);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("ResponseCommitted", var6, ResponseCommitted$11);
      var1.setlocal("ResponseCommitted", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(44);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("HopByHopHeaderSet", var6, HopByHopHeaderSet$12);
      var1.setlocal("HopByHopHeaderSet", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(45);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("WrongLength", var6, WrongLength$13);
      var1.setlocal("WrongLength", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(46);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("BadArgument", var6, BadArgument$14);
      var1.setlocal("BadArgument", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(47);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("ReturnNotIterable", var6, ReturnNotIterable$15);
      var1.setlocal("ReturnNotIterable", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(48);
      var6 = new PyObject[]{var1.getname("ApplicationException")};
      var4 = Py.makeClass("NonStringOutput", var6, NonStringOutput$16);
      var1.setlocal("NonStringOutput", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(50);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("exception_handler", var6, exception_handler$17);
      var1.setlocal("exception_handler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(62);
      var6 = new PyObject[]{var1.getname("exception_handler")};
      var4 = Py.makeClass("testing_handler", var6, testing_handler$20);
      var1.setlocal("testing_handler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(88);
      var6 = new PyObject[]{var1.getname("exception_handler")};
      var4 = Py.makeClass("standard_handler", var6, standard_handler$22);
      var1.setlocal("standard_handler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ModjyException$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      return var1.getf_locals();
   }

   public PyObject ModjyIOException$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      return var1.getf_locals();
   }

   public PyObject ConfigException$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(33);
      return var1.getf_locals();
   }

   public PyObject BadParameter$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      return var1.getf_locals();
   }

   public PyObject ApplicationNotFound$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(35);
      return var1.getf_locals();
   }

   public PyObject NoCallable$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      return var1.getf_locals();
   }

   public PyObject RequestException$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(38);
      return var1.getf_locals();
   }

   public PyObject ApplicationException$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(40);
      return var1.getf_locals();
   }

   public PyObject StartResponseNotCalled$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(41);
      return var1.getf_locals();
   }

   public PyObject StartResponseCalledTwice$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      return var1.getf_locals();
   }

   public PyObject ResponseCommitted$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      return var1.getf_locals();
   }

   public PyObject HopByHopHeaderSet$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(44);
      return var1.getf_locals();
   }

   public PyObject WrongLength$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      return var1.getf_locals();
   }

   public PyObject BadArgument$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(46);
      return var1.getf_locals();
   }

   public PyObject ReturnNotIterable$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(47);
      return var1.getf_locals();
   }

   public PyObject NonStringOutput$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(48);
      return var1.getf_locals();
   }

   public PyObject exception_handler$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(52);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$18, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_status_and_message$19, (PyObject)null);
      var1.setlocal("get_status_and_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$18(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_status_and_message$19(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("SC_INTERNAL_SERVER_ERROR"), PyString.fromInterned("Server configuration error")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testing_handler$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(64);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$21, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$21(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(5);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(66);
      var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(67);
      var1.getlocal(9).__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)})));
      var1.setline(68);
      var1.getlocal(9).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">Environment\n"));
      var1.setline(69);
      var3 = var1.getlocal(3).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(69);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(71);
            var1.getlocal(9).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Environment\n"));
            var1.setline(72);
            var1.getlocal(9).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">TraceBack\n"));
            var1.setline(73);
            var3 = var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)).__iter__();

            while(true) {
               var1.setline(73);
               var7 = var3.__iternext__();
               if (var7 == null) {
                  var1.setline(75);
                  var1.getlocal(9).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<TraceBack\n"));

                  try {
                     var1.setline(77);
                     var3 = var1.getlocal(0).__getattr__("get_status_and_message").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
                     var4 = Py.unpackSequence(var3, 2);
                     var5 = var4[0];
                     var1.setlocal(12, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(13, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(78);
                     var1.getlocal(2).__getattr__("setStatus").__call__(var2, var1.getlocal(12));
                     var1.setline(79);
                     var1.getlocal(2).__getattr__("setContentLength").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(9).__getattr__("getvalue").__call__(var2)));
                     var1.setline(80);
                     var1.getlocal(2).__getattr__("getOutputStream").__call__(var2).__getattr__("write").__call__(var2, var1.getlocal(9).__getattr__("getvalue").__call__(var2));
                  } catch (Throwable var6) {
                     PyException var8 = Py.setException(var6, var1);
                     if (var8.match(var1.getglobal("IllegalStateException"))) {
                        var7 = var8.value;
                        var1.setlocal(14, var7);
                        var4 = null;
                        var1.setline(82);
                        throw Py.makeException(var1.getlocal(4));
                     }

                     throw var8;
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(11, var7);
               var1.setline(74);
               var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(11));
            }
         }

         var1.setlocal(10, var7);
         var1.setline(70);
         var1.getlocal(9).__getattr__("write").__call__(var2, PyString.fromInterned("%s=%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getglobal("repr").__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(10)))})));
      }
   }

   public PyObject standard_handler$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(90);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$23, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$23(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      throw Py.makeException(var1.getlocal(5).__getitem__(Py.newInteger(0)), var1.getlocal(5).__getitem__(Py.newInteger(1)), var1.getlocal(5).__getitem__(Py.newInteger(2)));
   }

   public modjy_exceptions$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ModjyException$1 = Py.newCode(0, var2, var1, "ModjyException", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ModjyIOException$2 = Py.newCode(0, var2, var1, "ModjyIOException", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ConfigException$3 = Py.newCode(0, var2, var1, "ConfigException", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BadParameter$4 = Py.newCode(0, var2, var1, "BadParameter", 34, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ApplicationNotFound$5 = Py.newCode(0, var2, var1, "ApplicationNotFound", 35, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NoCallable$6 = Py.newCode(0, var2, var1, "NoCallable", 36, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      RequestException$7 = Py.newCode(0, var2, var1, "RequestException", 38, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ApplicationException$8 = Py.newCode(0, var2, var1, "ApplicationException", 40, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StartResponseNotCalled$9 = Py.newCode(0, var2, var1, "StartResponseNotCalled", 41, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StartResponseCalledTwice$10 = Py.newCode(0, var2, var1, "StartResponseCalledTwice", 42, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ResponseCommitted$11 = Py.newCode(0, var2, var1, "ResponseCommitted", 43, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HopByHopHeaderSet$12 = Py.newCode(0, var2, var1, "HopByHopHeaderSet", 44, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WrongLength$13 = Py.newCode(0, var2, var1, "WrongLength", 45, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BadArgument$14 = Py.newCode(0, var2, var1, "BadArgument", 46, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ReturnNotIterable$15 = Py.newCode(0, var2, var1, "ReturnNotIterable", 47, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NonStringOutput$16 = Py.newCode(0, var2, var1, "NonStringOutput", 48, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      exception_handler$17 = Py.newCode(0, var2, var1, "exception_handler", 50, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "resp", "environ", "exc", "exc_info"};
      handle$18 = Py.newCode(6, var2, var1, "handle", 52, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "resp", "exc"};
      get_status_and_message$19 = Py.newCode(4, var2, var1, "get_status_and_message", 55, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      testing_handler$20 = Py.newCode(0, var2, var1, "testing_handler", 62, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "resp", "environ", "exc", "exc_info", "typ", "value", "tb", "err_msg", "k", "line", "status", "message", "ise"};
      handle$21 = Py.newCode(6, var2, var1, "handle", 64, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      standard_handler$22 = Py.newCode(0, var2, var1, "standard_handler", 88, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "resp", "environ", "exc", "exc_info"};
      handle$23 = Py.newCode(6, var2, var1, "handle", 90, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_exceptions$py("modjy/modjy_exceptions$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_exceptions$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ModjyException$1(var2, var3);
         case 2:
            return this.ModjyIOException$2(var2, var3);
         case 3:
            return this.ConfigException$3(var2, var3);
         case 4:
            return this.BadParameter$4(var2, var3);
         case 5:
            return this.ApplicationNotFound$5(var2, var3);
         case 6:
            return this.NoCallable$6(var2, var3);
         case 7:
            return this.RequestException$7(var2, var3);
         case 8:
            return this.ApplicationException$8(var2, var3);
         case 9:
            return this.StartResponseNotCalled$9(var2, var3);
         case 10:
            return this.StartResponseCalledTwice$10(var2, var3);
         case 11:
            return this.ResponseCommitted$11(var2, var3);
         case 12:
            return this.HopByHopHeaderSet$12(var2, var3);
         case 13:
            return this.WrongLength$13(var2, var3);
         case 14:
            return this.BadArgument$14(var2, var3);
         case 15:
            return this.ReturnNotIterable$15(var2, var3);
         case 16:
            return this.NonStringOutput$16(var2, var3);
         case 17:
            return this.exception_handler$17(var2, var3);
         case 18:
            return this.handle$18(var2, var3);
         case 19:
            return this.get_status_and_message$19(var2, var3);
         case 20:
            return this.testing_handler$20(var2, var3);
         case 21:
            return this.handle$21(var2, var3);
         case 22:
            return this.standard_handler$22(var2, var3);
         case 23:
            return this.handle$23(var2, var3);
         default:
            return null;
      }
   }
}

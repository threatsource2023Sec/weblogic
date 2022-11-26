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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_response.py")
public class modjy_response$py extends PyFunctionTable implements PyRunnable {
   static modjy_response$py self;
   static final PyCode f$0;
   static final PyCode start_response_object$1;
   static final PyCode __init__$2;
   static final PyCode __call__$3;
   static final PyCode set_content_length$4;
   static final PyCode make_write_object$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(23);
      String[] var5 = new String[]{"System"};
      PyObject[] var6 = imp.importFrom("java.lang", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("System", var4);
      var4 = null;
      var1.setline(25);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(26);
      var5 = new String[]{"write_object"};
      var6 = imp.importFrom("modjy_write", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("write_object", var4);
      var4 = null;
      var1.setline(30);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("connection"), var1.getname("None"), PyString.fromInterned("keep-alive"), var1.getname("None"), PyString.fromInterned("proxy-authenticate"), var1.getname("None"), PyString.fromInterned("proxy-authorization"), var1.getname("None"), PyString.fromInterned("te"), var1.getname("None"), PyString.fromInterned("trailers"), var1.getname("None"), PyString.fromInterned("transfer-encoding"), var1.getname("None"), PyString.fromInterned("upgrade"), var1.getname("None")});
      var1.setlocal("hop_by_hop_headers", var7);
      var3 = null;
      var1.setline(41);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("start_response_object", var6, start_response_object$1);
      var1.setlocal("start_response_object", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_response_object$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$3, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_content_length$4, (PyObject)null);
      var1.setlocal("set_content_length", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_write_object$5, (PyObject)null);
      var1.setlocal("make_write_object", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("http_req", var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("http_resp", var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("write_callable", var3);
      var3 = null;
      var1.setline(47);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"called", var4);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("content_length", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$3(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._lt(Py.newInteger(2));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(3));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(53);
         throw Py.makeException(var1.getglobal("BadArgument").__call__(var2, PyString.fromInterned("Start response callback requires either two or three arguments: got %s")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(54);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(3));
         var3 = null;
         PyObject var4;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(55);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.setlocal(3, var3);
            var3 = null;
            var3 = null;

            try {
               try {
                  var1.setline(58);
                  var1.getlocal(0).__getattr__("http_resp").__getattr__("reset").__call__(var2);
               } catch (Throwable var8) {
                  PyException var12 = Py.setException(var8, var1);
                  if (var12.match(var1.getglobal("IllegalStateException"))) {
                     var5 = var12.value;
                     var1.setlocal(4, var5);
                     var5 = null;
                     var1.setline(60);
                     throw Py.makeException(var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(1)), var1.getlocal(3).__getitem__(Py.newInteger(2)));
                  }

                  throw var12;
               }
            } catch (Throwable var9) {
               Py.addTraceback(var9, var1);
               var1.setline(62);
               var4 = var1.getglobal("None");
               var1.setlocal(3, var4);
               var4 = null;
               throw (Throwable)var9;
            }

            var1.setline(62);
            var4 = var1.getglobal("None");
            var1.setlocal(3, var4);
            var4 = null;
         } else {
            var1.setline(64);
            var3 = var1.getlocal(0).__getattr__("called");
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(65);
               throw Py.makeException(var1.getglobal("StartResponseCalledTwice").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Start response callback may only be called once, without exception information.")));
            }
         }

         var1.setline(66);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(67);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(68);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("types").__getattr__("StringType")).__not__().__nonzero__()) {
            var1.setline(69);
            throw Py.makeException(var1.getglobal("BadArgument").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Start response callback requires string as first argument")));
         } else {
            var1.setline(70);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("types").__getattr__("ListType")).__not__().__nonzero__()) {
               var1.setline(71);
               throw Py.makeException(var1.getglobal("BadArgument").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Start response callback requires list as second argument")));
            } else {
               PyException var15;
               try {
                  var1.setline(73);
                  var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1));
                  PyObject[] var13 = Py.unpackSequence(var3, 2);
                  var5 = var13[0];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var13[1];
                  var1.setlocal(8, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(74);
                  var1.getlocal(0).__getattr__("http_resp").__getattr__("setStatus").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(7)));
               } catch (Throwable var10) {
                  var15 = Py.setException(var10, var1);
                  if (var15.match(var1.getglobal("ValueError"))) {
                     var1.setline(76);
                     throw Py.makeException(var1.getglobal("BadArgument").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Status string must be of the form '<int> <string>'")));
                  }

                  throw var15;
               }

               var1.setline(77);
               var1.getlocal(0).__getattr__("make_write_object").__call__(var2);

               try {
                  var1.setline(79);
                  var3 = var1.getlocal(6).__iter__();

                  while(true) {
                     var1.setline(79);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     PyObject[] var14 = Py.unpackSequence(var4, 2);
                     PyObject var6 = var14[0];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var6 = var14[1];
                     var1.setlocal(10, var6);
                     var6 = null;
                     var1.setline(80);
                     var5 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                     var1.setlocal(11, var5);
                     var5 = null;
                     var1.setline(81);
                     if (var1.getglobal("hop_by_hop_headers").__getattr__("has_key").__call__(var2, var1.getlocal(11)).__nonzero__()) {
                        var1.setline(82);
                        throw Py.makeException(var1.getglobal("HopByHopHeaderSet").__call__(var2, PyString.fromInterned("Under WSGI, it is illegal to set hop-by-hop headers, i.e. '%s'")._mod(var1.getlocal(9))));
                     }

                     var1.setline(83);
                     var5 = var1.getlocal(11);
                     var10000 = var5._eq(PyString.fromInterned("content-length"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        try {
                           var1.setline(85);
                           var1.getlocal(0).__getattr__("set_content_length").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(10)));
                        } catch (Throwable var7) {
                           PyException var16 = Py.setException(var7, var1);
                           if (var16.match(var1.getglobal("ValueError"))) {
                              var6 = var16.value;
                              var1.setlocal(12, var6);
                              var6 = null;
                              var1.setline(87);
                              throw Py.makeException(var1.getglobal("BadArgument").__call__(var2, PyString.fromInterned("Content-Length header value must be a string containing an integer, not '%s'")._mod(var1.getlocal(10))));
                           }

                           throw var16;
                        }
                     } else {
                        var1.setline(89);
                        var5 = var1.getlocal(10).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin-1"));
                        var1.setlocal(13, var5);
                        var5 = null;
                        var1.setline(91);
                        var1.getlocal(0).__getattr__("http_resp").__getattr__("addHeader").__call__(var2, var1.getlocal(9), var1.getlocal(13));
                     }
                  }
               } catch (Throwable var11) {
                  var15 = Py.setException(var11, var1);
                  if (var15.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("TypeError")}))) {
                     var4 = var15.value;
                     var1.setlocal(14, var4);
                     var4 = null;
                     var1.setline(93);
                     throw Py.makeException(var1.getglobal("BadArgument").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Start response callback headers must contain a list of (<string>,<string>) tuples")));
                  }

                  if (var15.match(var1.getglobal("UnicodeError"))) {
                     var4 = var15.value;
                     var1.setlocal(15, var4);
                     var4 = null;
                     var1.setline(95);
                     throw Py.makeException(var1.getglobal("BadArgument").__call__(var2, PyString.fromInterned("Encoding error: header values may only contain latin-1 characters, not '%s'")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(10)))));
                  }

                  if (var15.match(var1.getglobal("ValueError"))) {
                     var4 = var15.value;
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(97);
                     throw Py.makeException(var1.getglobal("BadArgument").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Headers list must contain 2-tuples")));
                  }

                  throw var15;
               }

               var1.setline(98);
               var10000 = var1.getlocal(0);
               String var17 = "called";
               var4 = var10000;
               var5 = var4.__getattr__(var17);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var17, var5);
               var1.setline(99);
               var3 = var1.getlocal(0).__getattr__("write_callable");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject set_content_length$4(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("write_callable").__getattr__("num_writes");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("content_length", var3);
         var3 = null;
         var1.setline(104);
         var1.getlocal(0).__getattr__("http_resp").__getattr__("setContentLength").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(106);
         throw Py.makeException(var1.getglobal("ResponseCommitted").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot set content-length: response is already commited.")));
      }
   }

   public PyObject make_write_object$5(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(110);
         var6 = var1.getglobal("write_object").__call__(var2, var1.getlocal(0).__getattr__("http_resp").__getattr__("getOutputStream").__call__(var2));
         var1.getlocal(0).__setattr__("write_callable", var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOException"))) {
            PyObject var4 = var3.value;
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(112);
            throw Py.makeException(var1.getglobal("IOError").__call__(var2, var1.getlocal(1)));
         }

         throw var3;
      }

      var1.setline(113);
      var6 = var1.getlocal(0).__getattr__("write_callable");
      var1.f_lasti = -1;
      return var6;
   }

   public modjy_response$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      start_response_object$1 = Py.newCode(0, var2, var1, "start_response_object", 41, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "resp"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 43, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "keywords", "exc_info", "isx", "status_str", "headers_list", "status_code", "status_message_str", "header_name", "header_value", "header_name_lower", "v", "final_value", "t", "u"};
      __call__$3 = Py.newCode(3, var2, var1, "__call__", 51, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "length"};
      set_content_length$4 = Py.newCode(2, var2, var1, "set_content_length", 101, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iox"};
      make_write_object$5 = Py.newCode(1, var2, var1, "make_write_object", 108, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_response$py("modjy/modjy_response$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_response$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.start_response_object$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__call__$3(var2, var3);
         case 4:
            return this.set_content_length$4(var2, var3);
         case 5:
            return this.make_write_object$5(var2, var3);
         default:
            return null;
      }
   }
}

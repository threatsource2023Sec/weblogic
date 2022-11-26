package email;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/encoders.py")
public class encoders$py extends PyFunctionTable implements PyRunnable {
   static encoders$py self;
   static final PyCode f$0;
   static final PyCode _qencode$1;
   static final PyCode _bencode$2;
   static final PyCode encode_base64$3;
   static final PyCode encode_quopri$4;
   static final PyCode encode_7or8bit$5;
   static final PyCode encode_noop$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Encodings and related functions."));
      var1.setline(5);
      PyString.fromInterned("Encodings and related functions.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("encode_7or8bit"), PyString.fromInterned("encode_base64"), PyString.fromInterned("encode_noop"), PyString.fromInterned("encode_quopri")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(14);
      PyObject var5 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var5);
      var3 = null;
      var1.setline(16);
      String[] var6 = new String[]{"encodestring"};
      PyObject[] var7 = imp.importFrom("quopri", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("_encodestring", var4);
      var4 = null;
      var1.setline(20);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _qencode$1, (PyObject)null);
      var1.setlocal("_qencode", var8);
      var3 = null;
      var1.setline(26);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _bencode$2, (PyObject)null);
      var1.setlocal("_bencode", var8);
      var3 = null;
      var1.setline(39);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, encode_base64$3, PyString.fromInterned("Encode the message's payload in Base64.\n\n    Also, add an appropriate Content-Transfer-Encoding header.\n    "));
      var1.setlocal("encode_base64", var8);
      var3 = null;
      var1.setline(51);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, encode_quopri$4, PyString.fromInterned("Encode the message's payload in quoted-printable.\n\n    Also, add an appropriate Content-Transfer-Encoding header.\n    "));
      var1.setlocal("encode_quopri", var8);
      var3 = null;
      var1.setline(63);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, encode_7or8bit$5, PyString.fromInterned("Set the Content-Transfer-Encoding header to 7bit or 8bit."));
      var1.setlocal("encode_7or8bit", var8);
      var3 = null;
      var1.setline(81);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, encode_noop$6, PyString.fromInterned("Do nothing."));
      var1.setlocal("encode_noop", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _qencode$1(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var10000 = var1.getglobal("_encodestring");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("True")};
      String[] var4 = new String[]{"quotetabs"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(23);
      var5 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("=20"));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _bencode$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(30);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(31);
         PyObject var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
         PyObject var10000 = var4._eq(PyString.fromInterned("\n"));
         var4 = null;
         var4 = var10000;
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(32);
         var4 = var1.getglobal("base64").__getattr__("encodestring").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(33);
         var10000 = var1.getlocal(1).__not__();
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
            var10000 = var4._eq(PyString.fromInterned("\n"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(34);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(35);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject encode_base64$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Encode the message's payload in Base64.\n\n    Also, add an appropriate Content-Transfer-Encoding header.\n    ");
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("_bencode").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(46);
      var1.getlocal(0).__getattr__("set_payload").__call__(var2, var1.getlocal(2));
      var1.setline(47);
      PyString var4 = PyString.fromInterned("base64");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode_quopri$4(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Encode the message's payload in quoted-printable.\n\n    Also, add an appropriate Content-Transfer-Encoding header.\n    ");
      var1.setline(56);
      PyObject var3 = var1.getlocal(0).__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("_qencode").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(58);
      var1.getlocal(0).__getattr__("set_payload").__call__(var2, var1.getlocal(2));
      var1.setline(59);
      PyString var4 = PyString.fromInterned("quoted-printable");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode_7or8bit$5(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("Set the Content-Transfer-Encoding header to 7bit or 8bit.");
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         PyString var7 = PyString.fromInterned("7bit");
         var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var7);
         var3 = null;
         var1.setline(69);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         label20: {
            PyString var4;
            try {
               var1.setline(73);
               var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getglobal("UnicodeError"))) {
                  var1.setline(75);
                  var4 = PyString.fromInterned("8bit");
                  var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
                  var4 = null;
                  break label20;
               }

               throw var6;
            }

            var1.setline(77);
            var4 = PyString.fromInterned("7bit");
            var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
            var4 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject encode_noop$6(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Do nothing.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public encoders$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "enc"};
      _qencode$1 = Py.newCode(1, var2, var1, "_qencode", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "hasnewline", "value"};
      _bencode$2 = Py.newCode(1, var2, var1, "_bencode", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "orig", "encdata"};
      encode_base64$3 = Py.newCode(1, var2, var1, "encode_base64", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "orig", "encdata"};
      encode_quopri$4 = Py.newCode(1, var2, var1, "encode_quopri", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "orig"};
      encode_7or8bit$5 = Py.newCode(1, var2, var1, "encode_7or8bit", 63, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg"};
      encode_noop$6 = Py.newCode(1, var2, var1, "encode_noop", 81, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new encoders$py("email/encoders$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(encoders$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._qencode$1(var2, var3);
         case 2:
            return this._bencode$2(var2, var3);
         case 3:
            return this.encode_base64$3(var2, var3);
         case 4:
            return this.encode_quopri$4(var2, var3);
         case 5:
            return this.encode_7or8bit$5(var2, var3);
         case 6:
            return this.encode_noop$6(var2, var3);
         default:
            return null;
      }
   }
}

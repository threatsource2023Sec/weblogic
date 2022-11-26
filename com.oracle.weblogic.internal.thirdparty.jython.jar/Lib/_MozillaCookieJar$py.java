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
@Filename("_MozillaCookieJar.py")
public class _MozillaCookieJar$py extends PyFunctionTable implements PyRunnable {
   static _MozillaCookieJar$py self;
   static final PyCode f$0;
   static final PyCode MozillaCookieJar$1;
   static final PyCode _really_load$2;
   static final PyCode save$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Mozilla / Netscape cookie loading / saving."));
      var1.setline(1);
      PyString.fromInterned("Mozilla / Netscape cookie loading / saving.");
      var1.setline(3);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"_warn_unhandled_exception", "FileCookieJar", "LoadError", "Cookie", "MISSING_FILENAME_TEXT"};
      PyObject[] var6 = imp.importFrom("cookielib", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_warn_unhandled_exception", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("FileCookieJar", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("LoadError", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("Cookie", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("MISSING_FILENAME_TEXT", var4);
      var4 = null;
      var1.setline(8);
      var6 = new PyObject[]{var1.getname("FileCookieJar")};
      var4 = Py.makeClass("MozillaCookieJar", var6, MozillaCookieJar$1);
      var1.setlocal("MozillaCookieJar", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MozillaCookieJar$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n\n    WARNING: you may want to backup your browser's cookies file if you use\n    this class to save cookies.  I *think* it works, but there have been\n    bugs in the past!\n\n    This class differs from CookieJar only in the format it uses to save and\n    load cookies to and from a file.  This class uses the Mozilla/Netscape\n    `cookies.txt' format.  lynx uses this file format, too.\n\n    Don't expect cookies saved while the browser is running to be noticed by\n    the browser (in fact, Mozilla on unix will overwrite your saved cookies if\n    you change them on disk while it's running; on Windows, you probably can't\n    save at all while the browser is running).\n\n    Note that the Mozilla/Netscape format will downgrade RFC2965 cookies to\n    Netscape cookies on saving.\n\n    In particular, the cookie version and port number information is lost,\n    together with information about whether or not Path, Port and Discard were\n    specified by the Set-Cookie2 (or Set-Cookie) header, and whether or not the\n    domain as set in the HTTP header started with a dot (yes, I'm aware some\n    domains in Netscape files start with a dot and some don't -- trust me, you\n    really don't want to know any more about this).\n\n    Note that though Mozilla and Netscape use the same format, they use\n    slightly different headers.  The class saves cookies using the Netscape\n    header by default (Mozilla can cope with that).\n\n    "));
      var1.setline(38);
      PyString.fromInterned("\n\n    WARNING: you may want to backup your browser's cookies file if you use\n    this class to save cookies.  I *think* it works, but there have been\n    bugs in the past!\n\n    This class differs from CookieJar only in the format it uses to save and\n    load cookies to and from a file.  This class uses the Mozilla/Netscape\n    `cookies.txt' format.  lynx uses this file format, too.\n\n    Don't expect cookies saved while the browser is running to be noticed by\n    the browser (in fact, Mozilla on unix will overwrite your saved cookies if\n    you change them on disk while it's running; on Windows, you probably can't\n    save at all while the browser is running).\n\n    Note that the Mozilla/Netscape format will downgrade RFC2965 cookies to\n    Netscape cookies on saving.\n\n    In particular, the cookie version and port number information is lost,\n    together with information about whether or not Path, Port and Discard were\n    specified by the Set-Cookie2 (or Set-Cookie) header, and whether or not the\n    domain as set in the HTTP header started with a dot (yes, I'm aware some\n    domains in Netscape files start with a dot and some don't -- trust me, you\n    really don't want to know any more about this).\n\n    Note that though Mozilla and Netscape use the same format, they use\n    slightly different headers.  The class saves cookies using the Netscape\n    header by default (Mozilla can cope with that).\n\n    ");
      var1.setline(39);
      PyString var3 = PyString.fromInterned("#( Netscape)? HTTP Cookie File");
      var1.setlocal("magic_re", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned("# Netscape HTTP Cookie File\n# http://www.netscape.com/newsref/std/cookie_spec.html\n# This is a generated file!  Do not edit.\n\n");
      var1.setlocal("header", var3);
      var3 = null;
      var1.setline(47);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _really_load$2, (PyObject)null);
      var1.setlocal("_really_load", var5);
      var3 = null;
      var1.setline(113);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, save$3, (PyObject)null);
      var1.setlocal("save", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _really_load$2(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(51);
      if (var1.getglobal("re").__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("magic_re"), var1.getlocal(6)).__not__().__nonzero__()) {
         var1.setline(52);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(53);
         throw Py.makeException(var1.getglobal("LoadError").__call__(var2, PyString.fromInterned("%r does not look like a Netscape format cookies file")._mod(var1.getlocal(2))));
      } else {
         try {
            while(true) {
               var1.setline(58);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(59);
               var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(60);
               var3 = var1.getlocal(7);
               PyObject var10000 = var3._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(63);
               if (var1.getlocal(7).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__nonzero__()) {
                  var1.setline(63);
                  var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(7, var3);
                  var3 = null;
               }

               var1.setline(66);
               var10000 = var1.getlocal(7).__getattr__("strip").__call__(var2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("#"), PyString.fromInterned("$")})));
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(7).__getattr__("strip").__call__(var2);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(70);
                  var3 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"));
                  PyObject[] var4 = Py.unpackSequence(var3, 7);
                  PyObject var5 = var4[0];
                  var1.setlocal(8, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(9, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(10, var5);
                  var5 = null;
                  var5 = var4[3];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var5 = var4[4];
                  var1.setlocal(12, var5);
                  var5 = null;
                  var5 = var4[5];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[6];
                  var1.setlocal(14, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(72);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("TRUE"));
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(73);
                  var3 = var1.getlocal(9);
                  var10000 = var3._eq(PyString.fromInterned("TRUE"));
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(74);
                  var3 = var1.getlocal(13);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(78);
                     var3 = var1.getlocal(14);
                     var1.setlocal(13, var3);
                     var3 = null;
                     var1.setline(79);
                     var3 = var1.getglobal("None");
                     var1.setlocal(14, var3);
                     var3 = null;
                  }

                  var1.setline(81);
                  var3 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  var1.setlocal(15, var3);
                  var3 = null;
                  var1.setline(82);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var3 = var1.getlocal(9);
                     var10000 = var3._eq(var1.getlocal(15));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(84);
                  var3 = var1.getglobal("False");
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(85);
                  var3 = var1.getlocal(12);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(86);
                     var3 = var1.getglobal("None");
                     var1.setlocal(12, var3);
                     var3 = null;
                     var1.setline(87);
                     var3 = var1.getglobal("True");
                     var1.setlocal(16, var3);
                     var3 = null;
                  }

                  var1.setline(90);
                  var10000 = var1.getglobal("Cookie");
                  PyObject[] var8 = new PyObject[]{Py.newInteger(0), var1.getlocal(13), var1.getlocal(14), var1.getglobal("None"), var1.getglobal("False"), var1.getlocal(8), var1.getlocal(9), var1.getlocal(15), var1.getlocal(10), var1.getglobal("False"), var1.getlocal(11), var1.getlocal(12), var1.getlocal(16), var1.getglobal("None"), var1.getglobal("None"), new PyDictionary(Py.EmptyObjects)};
                  var3 = var10000.__call__(var2, var8);
                  var1.setlocal(17, var3);
                  var3 = null;
                  var1.setline(100);
                  var10000 = var1.getlocal(3).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(17).__getattr__("discard");
                  }

                  if (!var10000.__nonzero__()) {
                     var1.setline(102);
                     var10000 = var1.getlocal(4).__not__();
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(17).__getattr__("is_expired").__call__(var2, var1.getlocal(5));
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(104);
                        var1.getlocal(0).__getattr__("set_cookie").__call__(var2, var1.getlocal(17));
                     }
                  }
               }
            }
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("IOError"))) {
               var1.setline(107);
               throw Py.makeException();
            }

            if (var7.match(var1.getglobal("Exception"))) {
               var1.setline(109);
               var1.getglobal("_warn_unhandled_exception").__call__(var2);
               var1.setline(110);
               throw Py.makeException(var1.getglobal("LoadError").__call__(var2, PyString.fromInterned("invalid Netscape format cookies file %r: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7)}))));
            }

            throw var7;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject save$3(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var3 = var1.getlocal(0).__getattr__("filename");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(116);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("MISSING_FILENAME_TEXT")));
         }

         var1.setline(115);
         var3 = var1.getlocal(0).__getattr__("filename");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(118);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(120);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("header"));
         var1.setline(121);
         PyObject var4 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(122);
         var4 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(122);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(123);
            var10000 = var1.getlocal(2).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6).__getattr__("discard");
            }

            if (!var10000.__nonzero__()) {
               var1.setline(125);
               var10000 = var1.getlocal(3).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6).__getattr__("is_expired").__call__(var2, var1.getlocal(5));
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(127);
                  PyString var6;
                  if (var1.getlocal(6).__getattr__("secure").__nonzero__()) {
                     var1.setline(127);
                     var6 = PyString.fromInterned("TRUE");
                     var1.setlocal(7, var6);
                     var6 = null;
                  } else {
                     var1.setline(128);
                     var6 = PyString.fromInterned("FALSE");
                     var1.setlocal(7, var6);
                     var6 = null;
                  }

                  var1.setline(129);
                  if (var1.getlocal(6).__getattr__("domain").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
                     var1.setline(129);
                     var6 = PyString.fromInterned("TRUE");
                     var1.setlocal(8, var6);
                     var6 = null;
                  } else {
                     var1.setline(130);
                     var6 = PyString.fromInterned("FALSE");
                     var1.setlocal(8, var6);
                     var6 = null;
                  }

                  var1.setline(131);
                  PyObject var8 = var1.getlocal(6).__getattr__("expires");
                  var10000 = var8._isnot(var1.getglobal("None"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(132);
                     var8 = var1.getglobal("str").__call__(var2, var1.getlocal(6).__getattr__("expires"));
                     var1.setlocal(9, var8);
                     var6 = null;
                  } else {
                     var1.setline(134);
                     var6 = PyString.fromInterned("");
                     var1.setlocal(9, var6);
                     var6 = null;
                  }

                  var1.setline(135);
                  var8 = var1.getlocal(6).__getattr__("value");
                  var10000 = var8._is(var1.getglobal("None"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(139);
                     var6 = PyString.fromInterned("");
                     var1.setlocal(10, var6);
                     var6 = null;
                     var1.setline(140);
                     var8 = var1.getlocal(6).__getattr__("name");
                     var1.setlocal(11, var8);
                     var6 = null;
                  } else {
                     var1.setline(142);
                     var8 = var1.getlocal(6).__getattr__("name");
                     var1.setlocal(10, var8);
                     var6 = null;
                     var1.setline(143);
                     var8 = var1.getlocal(6).__getattr__("value");
                     var1.setlocal(11, var8);
                     var6 = null;
                  }

                  var1.setline(144);
                  var1.getlocal(4).__getattr__("write").__call__(var2, PyString.fromInterned("\t").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(6).__getattr__("domain"), var1.getlocal(8), var1.getlocal(6).__getattr__("path"), var1.getlocal(7), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)})))._add(PyString.fromInterned("\n")));
               }
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(149);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(149);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public _MozillaCookieJar$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MozillaCookieJar$1 = Py.newCode(0, var2, var1, "MozillaCookieJar", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "filename", "ignore_discard", "ignore_expires", "now", "magic", "line", "domain", "domain_specified", "path", "secure", "expires", "name", "value", "initial_dot", "discard", "c"};
      _really_load$2 = Py.newCode(5, var2, var1, "_really_load", 47, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "ignore_discard", "ignore_expires", "f", "now", "cookie", "secure", "initial_dot", "expires", "name", "value"};
      save$3 = Py.newCode(4, var2, var1, "save", 113, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _MozillaCookieJar$py("_MozillaCookieJar$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_MozillaCookieJar$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MozillaCookieJar$1(var2, var3);
         case 2:
            return this._really_load$2(var2, var3);
         case 3:
            return this.save$3(var2, var3);
         default:
            return null;
      }
   }
}

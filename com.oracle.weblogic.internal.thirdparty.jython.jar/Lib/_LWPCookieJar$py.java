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
@Filename("_LWPCookieJar.py")
public class _LWPCookieJar$py extends PyFunctionTable implements PyRunnable {
   static _LWPCookieJar$py self;
   static final PyCode f$0;
   static final PyCode lwp_cookie_str$1;
   static final PyCode LWPCookieJar$2;
   static final PyCode as_lwp_str$3;
   static final PyCode save$4;
   static final PyCode _really_load$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Load / save to libwww-perl (LWP) format files.\n\nActually, the format is slightly extended from that used by LWP's\n(libwww-perl's) HTTP::Cookies, to avoid losing some RFC 2965 information\nnot recorded by LWP.\n\nIt uses the version string \"2.0\", though really there isn't an LWP Cookies\n2.0 format.  This indicates that there is extra information in here\n(domain_dot and # port_spec) while still being compatible with\nlibwww-perl, I hope.\n\n"));
      var1.setline(12);
      PyString.fromInterned("Load / save to libwww-perl (LWP) format files.\n\nActually, the format is slightly extended from that used by LWP's\n(libwww-perl's) HTTP::Cookies, to avoid losing some RFC 2965 information\nnot recorded by LWP.\n\nIt uses the version string \"2.0\", though really there isn't an LWP Cookies\n2.0 format.  This indicates that there is extra information in here\n(domain_dot and # port_spec) while still being compatible with\nlibwww-perl, I hope.\n\n");
      var1.setline(14);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(15);
      String[] var5 = new String[]{"_warn_unhandled_exception", "FileCookieJar", "LoadError", "Cookie", "MISSING_FILENAME_TEXT", "join_header_words", "split_header_words", "iso2time", "time2isoz"};
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
      var4 = var6[5];
      var1.setlocal("join_header_words", var4);
      var4 = null;
      var4 = var6[6];
      var1.setlocal("split_header_words", var4);
      var4 = null;
      var4 = var6[7];
      var1.setlocal("iso2time", var4);
      var4 = null;
      var4 = var6[8];
      var1.setlocal("time2isoz", var4);
      var4 = null;
      var1.setline(20);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, lwp_cookie_str$1, PyString.fromInterned("Return string representation of Cookie in an the LWP cookie file format.\n\n    Actually, the format is extended a bit -- see module docstring.\n\n    "));
      var1.setlocal("lwp_cookie_str", var7);
      var3 = null;
      var1.setline(49);
      var6 = new PyObject[]{var1.getname("FileCookieJar")};
      var4 = Py.makeClass("LWPCookieJar", var6, LWPCookieJar$2);
      var1.setlocal("LWPCookieJar", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject lwp_cookie_str$1(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyString.fromInterned("Return string representation of Cookie in an the LWP cookie file format.\n\n    Actually, the format is extended a bit -- see module docstring.\n\n    ");
      var1.setline(26);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("value")}), new PyTuple(new PyObject[]{PyString.fromInterned("path"), var1.getlocal(0).__getattr__("path")}), new PyTuple(new PyObject[]{PyString.fromInterned("domain"), var1.getlocal(0).__getattr__("domain")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(29);
      PyObject var5 = var1.getlocal(0).__getattr__("port");
      PyObject var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(29);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("port"), var1.getlocal(0).__getattr__("port")})));
      }

      var1.setline(30);
      if (var1.getlocal(0).__getattr__("path_specified").__nonzero__()) {
         var1.setline(30);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("path_spec"), var1.getglobal("None")})));
      }

      var1.setline(31);
      if (var1.getlocal(0).__getattr__("port_specified").__nonzero__()) {
         var1.setline(31);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("port_spec"), var1.getglobal("None")})));
      }

      var1.setline(32);
      if (var1.getlocal(0).__getattr__("domain_initial_dot").__nonzero__()) {
         var1.setline(32);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("domain_dot"), var1.getglobal("None")})));
      }

      var1.setline(33);
      if (var1.getlocal(0).__getattr__("secure").__nonzero__()) {
         var1.setline(33);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("secure"), var1.getglobal("None")})));
      }

      var1.setline(34);
      if (var1.getlocal(0).__getattr__("expires").__nonzero__()) {
         var1.setline(34);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("expires"), var1.getglobal("time2isoz").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0).__getattr__("expires")))})));
      }

      var1.setline(36);
      if (var1.getlocal(0).__getattr__("discard").__nonzero__()) {
         var1.setline(36);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("discard"), var1.getglobal("None")})));
      }

      var1.setline(37);
      if (var1.getlocal(0).__getattr__("comment").__nonzero__()) {
         var1.setline(37);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("comment"), var1.getlocal(0).__getattr__("comment")})));
      }

      var1.setline(38);
      if (var1.getlocal(0).__getattr__("comment_url").__nonzero__()) {
         var1.setline(38);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("commenturl"), var1.getlocal(0).__getattr__("comment_url")})));
      }

      var1.setline(40);
      var5 = var1.getlocal(0).__getattr__("_rest").__getattr__("keys").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(41);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(42);
      var5 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(42);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(45);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("version"), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("version"))})));
            var1.setline(47);
            var5 = var1.getglobal("join_header_words").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(43);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_rest").__getitem__(var1.getlocal(3)))})));
      }
   }

   public PyObject LWPCookieJar$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    The LWPCookieJar saves a sequence of \"Set-Cookie3\" lines.\n    \"Set-Cookie3\" is the format used by the libwww-perl libary, not known\n    to be compatible with any browser, but which is easy to read and\n    doesn't lose information about RFC 2965 cookies.\n\n    Additional methods\n\n    as_lwp_str(ignore_discard=True, ignore_expired=True)\n\n    "));
      var1.setline(60);
      PyString.fromInterned("\n    The LWPCookieJar saves a sequence of \"Set-Cookie3\" lines.\n    \"Set-Cookie3\" is the format used by the libwww-perl libary, not known\n    to be compatible with any browser, but which is easy to read and\n    doesn't lose information about RFC 2965 cookies.\n\n    Additional methods\n\n    as_lwp_str(ignore_discard=True, ignore_expired=True)\n\n    ");
      var1.setline(62);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, as_lwp_str$3, PyString.fromInterned("Return cookies as a string of \"\\n\"-separated \"Set-Cookie3\" headers.\n\n        ignore_discard and ignore_expires: see docstring for FileCookieJar.save\n\n        "));
      var1.setlocal("as_lwp_str", var4);
      var3 = null;
      var1.setline(78);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, save$4, (PyObject)null);
      var1.setlocal("save", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _really_load$5, (PyObject)null);
      var1.setlocal("_really_load", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject as_lwp_str$3(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Return cookies as a string of \"\\n\"-separated \"Set-Cookie3\" headers.\n\n        ignore_discard and ignore_expires: see docstring for FileCookieJar.save\n\n        ");
      var1.setline(68);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(69);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(70);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(70);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(76);
            var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(4)._add(new PyList(new PyObject[]{PyString.fromInterned("")})));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(71);
         PyObject var10000 = var1.getlocal(1).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5).__getattr__("discard");
         }

         if (!var10000.__nonzero__()) {
            var1.setline(73);
            var10000 = var1.getlocal(2).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(5).__getattr__("is_expired").__call__(var2, var1.getlocal(3));
            }

            if (!var10000.__nonzero__()) {
               var1.setline(75);
               var1.getlocal(4).__getattr__("append").__call__(var2, PyString.fromInterned("Set-Cookie3: %s")._mod(var1.getglobal("lwp_cookie_str").__call__(var2, var1.getlocal(5))));
            }
         }
      }
   }

   public PyObject save$4(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var3 = var1.getlocal(0).__getattr__("filename");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(81);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("MISSING_FILENAME_TEXT")));
         }

         var1.setline(80);
         var3 = var1.getlocal(0).__getattr__("filename");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(83);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(88);
         var1.getlocal(4).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#LWP-Cookies-2.0\n"));
         var1.setline(89);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("as_lwp_str").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(91);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(91);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _really_load$5(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(95);
      if (var1.getglobal("re").__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("magic_re"), var1.getlocal(5)).__not__().__nonzero__()) {
         var1.setline(96);
         var3 = PyString.fromInterned("%r does not look like a Set-Cookie3 (LWP) format file")._mod(var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(98);
         throw Py.makeException(var1.getglobal("LoadError").__call__(var2, var1.getlocal(6)));
      } else {
         var1.setline(100);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(102);
         PyString var10 = PyString.fromInterned("Set-Cookie3:");
         var1.setlocal(8, var10);
         var3 = null;
         var1.setline(103);
         PyTuple var11 = new PyTuple(new PyObject[]{PyString.fromInterned("port_spec"), PyString.fromInterned("path_spec"), PyString.fromInterned("domain_dot"), PyString.fromInterned("secure"), PyString.fromInterned("discard")});
         var1.setlocal(9, var11);
         var3 = null;
         var1.setline(105);
         var11 = new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("port"), PyString.fromInterned("path"), PyString.fromInterned("domain"), PyString.fromInterned("expires"), PyString.fromInterned("comment"), PyString.fromInterned("commenturl")});
         var1.setlocal(10, var11);
         var3 = null;

         try {
            while(true) {
               var1.setline(111);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(112);
               var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(113);
               var3 = var1.getlocal(11);
               PyObject var10000 = var3._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(114);
               if (!var1.getlocal(11).__getattr__("startswith").__call__(var2, var1.getlocal(8)).__not__().__nonzero__()) {
                  var1.setline(116);
                  var3 = var1.getlocal(11).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(8)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(118);
                  var3 = var1.getglobal("split_header_words").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(11)}))).__iter__();

                  label108:
                  while(true) {
                     var1.setline(118);
                     PyObject var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(12, var4);
                     var1.setline(119);
                     PyObject var5 = var1.getlocal(12).__getitem__(Py.newInteger(0));
                     PyObject[] var6 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var6[0];
                     var1.setlocal(13, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(14, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(120);
                     PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
                     var1.setlocal(15, var12);
                     var5 = null;
                     var1.setline(121);
                     var12 = new PyDictionary(Py.EmptyObjects);
                     var1.setlocal(16, var12);
                     var5 = null;
                     var1.setline(122);
                     var5 = var1.getlocal(9).__iter__();

                     while(true) {
                        var1.setline(122);
                        PyObject var14 = var5.__iternext__();
                        if (var14 == null) {
                           var1.setline(124);
                           var5 = var1.getlocal(12).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

                           while(true) {
                              var1.setline(124);
                              var14 = var5.__iternext__();
                              if (var14 == null) {
                                 var1.setline(140);
                                 var5 = var1.getlocal(15).__getattr__("get");
                                 var1.setlocal(20, var5);
                                 var5 = null;
                                 var1.setline(141);
                                 var5 = var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expires"));
                                 var1.setlocal(21, var5);
                                 var5 = null;
                                 var1.setline(142);
                                 var5 = var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("discard"));
                                 var1.setlocal(22, var5);
                                 var5 = null;
                                 var1.setline(143);
                                 var5 = var1.getlocal(21);
                                 var10000 = var5._isnot(var1.getglobal("None"));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(144);
                                    var5 = var1.getglobal("iso2time").__call__(var2, var1.getlocal(21));
                                    var1.setlocal(21, var5);
                                    var5 = null;
                                 }

                                 var1.setline(145);
                                 var5 = var1.getlocal(21);
                                 var10000 = var5._is(var1.getglobal("None"));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(146);
                                    var5 = var1.getglobal("True");
                                    var1.setlocal(22, var5);
                                    var5 = null;
                                 }

                                 var1.setline(147);
                                 var5 = var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("domain"));
                                 var1.setlocal(23, var5);
                                 var5 = null;
                                 var1.setline(148);
                                 var5 = var1.getlocal(23).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                                 var1.setlocal(24, var5);
                                 var5 = null;
                                 var1.setline(149);
                                 var10000 = var1.getglobal("Cookie");
                                 PyObject[] var16 = new PyObject[]{var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("version")), var1.getlocal(13), var1.getlocal(14), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("port")), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("port_spec")), var1.getlocal(23), var1.getlocal(24), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("domain_dot")), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("path")), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("path_spec")), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("secure")), var1.getlocal(21), var1.getlocal(22), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("comment")), var1.getlocal(20).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("commenturl")), var1.getlocal(16)};
                                 var5 = var10000.__call__(var2, var16);
                                 var1.setlocal(25, var5);
                                 var5 = null;
                                 var1.setline(159);
                                 var10000 = var1.getlocal(3).__not__();
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(25).__getattr__("discard");
                                 }

                                 if (!var10000.__nonzero__()) {
                                    var1.setline(161);
                                    var10000 = var1.getlocal(4).__not__();
                                    if (var10000.__nonzero__()) {
                                       var10000 = var1.getlocal(25).__getattr__("is_expired").__call__(var2, var1.getlocal(7));
                                    }

                                    if (!var10000.__nonzero__()) {
                                       var1.setline(163);
                                       var1.getlocal(0).__getattr__("set_cookie").__call__(var2, var1.getlocal(25));
                                    }
                                 }
                                 continue label108;
                              }

                              PyObject[] var15 = Py.unpackSequence(var14, 2);
                              PyObject var8 = var15[0];
                              var1.setlocal(17, var8);
                              var8 = null;
                              var8 = var15[1];
                              var1.setlocal(18, var8);
                              var8 = null;
                              var1.setline(125);
                              var7 = var1.getlocal(17);
                              var10000 = var7._isnot(var1.getglobal("None"));
                              var7 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(126);
                                 var7 = var1.getlocal(17).__getattr__("lower").__call__(var2);
                                 var1.setlocal(19, var7);
                                 var7 = null;
                              } else {
                                 var1.setline(128);
                                 var7 = var1.getglobal("None");
                                 var1.setlocal(19, var7);
                                 var7 = null;
                              }

                              var1.setline(130);
                              var7 = var1.getlocal(19);
                              var10000 = var7._in(var1.getlocal(10));
                              var7 = null;
                              if (!var10000.__nonzero__()) {
                                 var7 = var1.getlocal(19);
                                 var10000 = var7._in(var1.getlocal(9));
                                 var7 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(131);
                                 var7 = var1.getlocal(19);
                                 var1.setlocal(17, var7);
                                 var7 = null;
                              }

                              var1.setline(132);
                              var7 = var1.getlocal(17);
                              var10000 = var7._in(var1.getlocal(9));
                              var7 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(133);
                                 var7 = var1.getlocal(18);
                                 var10000 = var7._is(var1.getglobal("None"));
                                 var7 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(133);
                                    var7 = var1.getglobal("True");
                                    var1.setlocal(18, var7);
                                    var7 = null;
                                 }

                                 var1.setline(134);
                                 var7 = var1.getlocal(18);
                                 var1.getlocal(15).__setitem__(var1.getlocal(17), var7);
                                 var7 = null;
                              } else {
                                 var1.setline(135);
                                 var7 = var1.getlocal(17);
                                 var10000 = var7._in(var1.getlocal(10));
                                 var7 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(136);
                                    var7 = var1.getlocal(18);
                                    var1.getlocal(15).__setitem__(var1.getlocal(17), var7);
                                    var7 = null;
                                 } else {
                                    var1.setline(138);
                                    var7 = var1.getlocal(18);
                                    var1.getlocal(16).__setitem__(var1.getlocal(17), var7);
                                    var7 = null;
                                 }
                              }
                           }
                        }

                        var1.setlocal(17, var14);
                        var1.setline(123);
                        var7 = var1.getglobal("False");
                        var1.getlocal(15).__setitem__(var1.getlocal(17), var7);
                        var7 = null;
                     }
                  }
               }
            }
         } catch (Throwable var9) {
            PyException var13 = Py.setException(var9, var1);
            if (var13.match(var1.getglobal("IOError"))) {
               var1.setline(166);
               throw Py.makeException();
            }

            if (var13.match(var1.getglobal("Exception"))) {
               var1.setline(168);
               var1.getglobal("_warn_unhandled_exception").__call__(var2);
               var1.setline(169);
               throw Py.makeException(var1.getglobal("LoadError").__call__(var2, PyString.fromInterned("invalid Set-Cookie3 format file %r: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(11)}))));
            }

            throw var13;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public _LWPCookieJar$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cookie", "h", "keys", "k"};
      lwp_cookie_str$1 = Py.newCode(1, var2, var1, "lwp_cookie_str", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LWPCookieJar$2 = Py.newCode(0, var2, var1, "LWPCookieJar", 49, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ignore_discard", "ignore_expires", "now", "r", "cookie"};
      as_lwp_str$3 = Py.newCode(3, var2, var1, "as_lwp_str", 62, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "ignore_discard", "ignore_expires", "f"};
      save$4 = Py.newCode(4, var2, var1, "save", 78, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "filename", "ignore_discard", "ignore_expires", "magic", "msg", "now", "header", "boolean_attrs", "value_attrs", "line", "data", "name", "value", "standard", "rest", "k", "v", "lc", "h", "expires", "discard", "domain", "domain_specified", "c"};
      _really_load$5 = Py.newCode(5, var2, var1, "_really_load", 93, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _LWPCookieJar$py("_LWPCookieJar$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_LWPCookieJar$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.lwp_cookie_str$1(var2, var3);
         case 2:
            return this.LWPCookieJar$2(var2, var3);
         case 3:
            return this.as_lwp_str$3(var2, var3);
         case 4:
            return this.save$4(var2, var3);
         case 5:
            return this._really_load$5(var2, var3);
         default:
            return null;
      }
   }
}

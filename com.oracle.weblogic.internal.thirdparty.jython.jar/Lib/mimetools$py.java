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
@MTime(1498849383000L)
@Filename("mimetools.py")
public class mimetools$py extends PyFunctionTable implements PyRunnable {
   static mimetools$py self;
   static final PyCode f$0;
   static final PyCode Message$1;
   static final PyCode __init__$2;
   static final PyCode parsetype$3;
   static final PyCode parseplist$4;
   static final PyCode getplist$5;
   static final PyCode getparam$6;
   static final PyCode getparamnames$7;
   static final PyCode getencoding$8;
   static final PyCode gettype$9;
   static final PyCode getmaintype$10;
   static final PyCode getsubtype$11;
   static final PyCode _get_next_counter$12;
   static final PyCode choose_boundary$13;
   static final PyCode decode$14;
   static final PyCode encode$15;
   static final PyCode pipeto$16;
   static final PyCode pipethrough$17;
   static final PyCode copyliteral$18;
   static final PyCode copybinary$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setglobal("__doc__", PyString.fromInterned("Various tools used by MIME-reading or MIME-writing programs."));
      var1.setline(1);
      PyString.fromInterned("Various tools used by MIME-reading or MIME-writing programs.");
      var1.setline(4);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(7);
      String[] var7 = new String[]{"filterwarnings", "catch_warnings"};
      PyObject[] var8 = imp.importFrom("warnings", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("filterwarnings", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("catch_warnings", var4);
      var4 = null;
      ContextManager var9;
      var4 = (var9 = ContextGuard.getManager(var1.getname("catch_warnings").__call__(var2))).__enter__(var2);

      label36: {
         try {
            var1.setline(9);
            if (var1.getname("sys").__getattr__("py3kwarning").__nonzero__()) {
               var1.setline(10);
               var1.getname("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*rfc822 has been removed"), (PyObject)var1.getname("DeprecationWarning"));
            }

            var1.setline(11);
            var4 = imp.importOne("rfc822", var1, -1);
            var1.setlocal("rfc822", var4);
            var4 = null;
         } catch (Throwable var6) {
            if (var9.__exit__(var2, Py.setException(var6, var1))) {
               break label36;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.setline(13);
      var7 = new String[]{"warnpy3k"};
      var8 = imp.importFrom("warnings", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(14);
      PyObject var10000 = var1.getname("warnpy3k");
      var8 = new PyObject[]{PyString.fromInterned("in 3.x, mimetools has been removed in favor of the email package"), Py.newInteger(2)};
      String[] var10 = new String[]{"stacklevel"};
      var10000.__call__(var2, var8, var10);
      var3 = null;
      var1.setline(17);
      PyList var11 = new PyList(new PyObject[]{PyString.fromInterned("Message"), PyString.fromInterned("choose_boundary"), PyString.fromInterned("encode"), PyString.fromInterned("decode"), PyString.fromInterned("copyliteral"), PyString.fromInterned("copybinary")});
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(20);
      var8 = new PyObject[]{var1.getname("rfc822").__getattr__("Message")};
      var4 = Py.makeClass("Message", var8, Message$1);
      var1.setlocal("Message", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);

      try {
         var1.setline(108);
         var3 = imp.importOne("thread", var1, -1);
         var1.setlocal("thread", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var12 = Py.setException(var5, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(110);
         var4 = imp.importOneAs("dummy_thread", var1, -1);
         var1.setlocal("thread", var4);
         var4 = null;
      }

      var1.setline(111);
      var3 = var1.getname("thread").__getattr__("allocate_lock").__call__(var2);
      var1.setlocal("_counter_lock", var3);
      var3 = null;
      var1.setline(112);
      var1.dellocal("thread");
      var1.setline(114);
      PyInteger var13 = Py.newInteger(0);
      var1.setlocal("_counter", var13);
      var3 = null;
      var1.setline(115);
      var8 = Py.EmptyObjects;
      PyFunction var14 = new PyFunction(var1.f_globals, var8, _get_next_counter$12, (PyObject)null);
      var1.setlocal("_get_next_counter", var14);
      var3 = null;
      var1.setline(123);
      var3 = var1.getname("None");
      var1.setlocal("_prefix", var3);
      var3 = null;
      var1.setline(125);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, choose_boundary$13, PyString.fromInterned("Return a string usable as a multipart boundary.\n\n    The string chosen is unique within a single program run, and\n    incorporates the user id (if available), process id (if available),\n    and current time.  So it's very unlikely the returned string appears\n    in message text, but there's no guarantee.\n\n    The boundary contains dots so you have to quote it in the header."));
      var1.setlocal("choose_boundary", var14);
      var3 = null;
      var1.setline(157);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, decode$14, PyString.fromInterned("Decode common content-transfer-encodings (base64, quopri, uuencode)."));
      var1.setlocal("decode", var14);
      var3 = null;
      var1.setline(176);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, encode$15, PyString.fromInterned("Encode common content-transfer-encodings (base64, quopri, uuencode)."));
      var1.setlocal("encode", var14);
      var3 = null;
      var1.setline(199);
      PyString var15 = PyString.fromInterned("(\nTEMP=/tmp/@uu.$$\nsed \"s%^begin [0-7][0-7]* .*%begin 600 $TEMP%\" | uudecode\ncat $TEMP\nrm $TEMP\n)");
      var1.setlocal("uudecode_pipe", var15);
      var3 = null;
      var1.setline(206);
      PyDictionary var16 = new PyDictionary(new PyObject[]{PyString.fromInterned("uuencode"), var1.getname("uudecode_pipe"), PyString.fromInterned("x-uuencode"), var1.getname("uudecode_pipe"), PyString.fromInterned("uue"), var1.getname("uudecode_pipe"), PyString.fromInterned("x-uue"), var1.getname("uudecode_pipe"), PyString.fromInterned("quoted-printable"), PyString.fromInterned("mmencode -u -q"), PyString.fromInterned("base64"), PyString.fromInterned("mmencode -u -b")});
      var1.setlocal("decodetab", var16);
      var3 = null;
      var1.setline(215);
      var16 = new PyDictionary(new PyObject[]{PyString.fromInterned("x-uuencode"), PyString.fromInterned("uuencode tempfile"), PyString.fromInterned("uuencode"), PyString.fromInterned("uuencode tempfile"), PyString.fromInterned("x-uue"), PyString.fromInterned("uuencode tempfile"), PyString.fromInterned("uue"), PyString.fromInterned("uuencode tempfile"), PyString.fromInterned("quoted-printable"), PyString.fromInterned("mmencode -q"), PyString.fromInterned("base64"), PyString.fromInterned("mmencode -b")});
      var1.setlocal("encodetab", var16);
      var3 = null;
      var1.setline(224);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, pipeto$16, (PyObject)null);
      var1.setlocal("pipeto", var14);
      var3 = null;
      var1.setline(229);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, pipethrough$17, (PyObject)null);
      var1.setlocal("pipethrough", var14);
      var3 = null;
      var1.setline(239);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, copyliteral$18, (PyObject)null);
      var1.setlocal("copyliteral", var14);
      var3 = null;
      var1.setline(245);
      var8 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var8, copybinary$19, (PyObject)null);
      var1.setlocal("copybinary", var14);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Message$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A derived class of rfc822.Message that knows about MIME headers and\n    contains some hooks for decoding encoded and multipart messages."));
      var1.setline(22);
      PyString.fromInterned("A derived class of rfc822.Message that knows about MIME headers and\n    contains some hooks for decoding encoded and multipart messages.");
      var1.setline(24);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parsetype$3, (PyObject)null);
      var1.setlocal("parsetype", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseplist$4, (PyObject)null);
      var1.setlocal("parseplist", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getplist$5, (PyObject)null);
      var1.setlocal("getplist", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getparam$6, (PyObject)null);
      var1.setlocal("getparam", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getparamnames$7, (PyObject)null);
      var1.setlocal("getparamnames", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getencoding$8, (PyObject)null);
      var1.setlocal("getencoding", var4);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gettype$9, (PyObject)null);
      var1.setlocal("gettype", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmaintype$10, (PyObject)null);
      var1.setlocal("getmaintype", var4);
      var3 = null;
      var1.setline(98);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsubtype$11, (PyObject)null);
      var1.setlocal("getsubtype", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.getglobal("rfc822").__getattr__("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(26);
      PyObject var3 = var1.getlocal(0).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-transfer-encoding"));
      var1.getlocal(0).__setattr__("encodingheader", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getlocal(0).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-type"));
      var1.getlocal(0).__setattr__("typeheader", var3);
      var3 = null;
      var1.setline(30);
      var1.getlocal(0).__getattr__("parsetype").__call__(var2);
      var1.setline(31);
      var1.getlocal(0).__getattr__("parseplist").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parsetype$3(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var3 = var1.getlocal(0).__getattr__("typeheader");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var6;
      if (var10000.__nonzero__()) {
         var1.setline(36);
         var6 = PyString.fromInterned("text/plain");
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(37);
      var6 = PyString.fromInterned(";");
      var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var3 = var1.getlocal(1).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(39);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("plisttext", var3);
         var3 = null;
         var1.setline(40);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(42);
         var6 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"plisttext", var6);
         var3 = null;
      }

      var1.setline(43);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

      while(true) {
         var1.setline(44);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(46);
            var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.getlocal(0).__setattr__("type", var3);
            var3 = null;
            var1.setline(47);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var1.getlocal(0).__setattr__("maintype", var3);
            var3 = null;
            var1.setline(48);
            var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.getlocal(0).__setattr__("subtype", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(45);
         PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(2)).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
         var1.getlocal(3).__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject parseplist$4(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = var1.getlocal(0).__getattr__("plisttext");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(52);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"plist", var4);
      var3 = null;

      while(true) {
         var1.setline(53);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         PyObject var10000 = var3._eq(PyString.fromInterned(";"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(54);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(55);
         PyString var5 = PyString.fromInterned(";");
         var10000 = var5._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(57);
            var3 = var1.getlocal(1).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(59);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(60);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(61);
         var5 = PyString.fromInterned("=");
         var10000 = var5._in(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var3 = var1.getlocal(3).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(63);
            var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2)._add(PyString.fromInterned("="))._add(var1.getlocal(3).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(65);
         var1.getlocal(0).__getattr__("plist").__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("strip").__call__(var2));
         var1.setline(66);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject getplist$5(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("plist");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getparam$6(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2)._add(PyString.fromInterned("="));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getlocal(0).__getattr__("plist").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(74);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(77);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(75);
         var5 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(76);
      var5 = var1.getglobal("rfc822").__getattr__("unquote").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getparamnames$7(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(81);
      PyObject var6 = var1.getlocal(0).__getattr__("plist").__iter__();

      while(true) {
         var1.setline(81);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(85);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(82);
         PyObject var5 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(83);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(84);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null).__getattr__("lower").__call__(var2));
         }
      }
   }

   public PyObject getencoding$8(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getlocal(0).__getattr__("encodingheader");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(89);
         PyString var4 = PyString.fromInterned("7bit");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(90);
         var3 = var1.getlocal(0).__getattr__("encodingheader").__getattr__("lower").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject gettype$9(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmaintype$10(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("maintype");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsubtype$11(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("subtype");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_next_counter$12(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      var1.getglobal("_counter_lock").__getattr__("acquire").__call__(var2);
      var1.setline(118);
      PyObject var3 = var1.getglobal("_counter");
      var3 = var3._iadd(Py.newInteger(1));
      var1.setglobal("_counter", var3);
      var1.setline(119);
      var3 = var1.getglobal("_counter");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(120);
      var1.getglobal("_counter_lock").__getattr__("release").__call__(var2);
      var1.setline(121);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject choose_boundary$13(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Return a string usable as a multipart boundary.\n\n    The string chosen is unique within a single program run, and\n    incorporates the user id (if available), process id (if available),\n    and current time.  So it's very unlikely the returned string appears\n    in message text, but there's no guarantee.\n\n    The boundary contains dots so you have to quote it in the header.");
      var1.setline(136);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getglobal("_prefix");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(138);
         var3 = imp.importOne("socket", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;

         PyString var4;
         PyException var8;
         try {
            var1.setline(140);
            var3 = var1.getlocal(1).__getattr__("gethostbyname").__call__(var2, var1.getlocal(1).__getattr__("gethostname").__call__(var2));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getlocal(1).__getattr__("gaierror"))) {
               throw var8;
            }

            var1.setline(142);
            var4 = PyString.fromInterned("127.0.0.1");
            var1.setlocal(2, var4);
            var4 = null;
         }

         try {
            var1.setline(144);
            var3 = var1.getglobal("repr").__call__(var2, var1.getglobal("os").__getattr__("getuid").__call__(var2));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (!var8.match(var1.getglobal("AttributeError"))) {
               throw var8;
            }

            var1.setline(146);
            var4 = PyString.fromInterned("1");
            var1.setlocal(3, var4);
            var4 = null;
         }

         try {
            var1.setline(148);
            var3 = var1.getglobal("repr").__call__(var2, var1.getglobal("os").__getattr__("getpid").__call__(var2));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var5) {
            var8 = Py.setException(var5, var1);
            if (!var8.match(var1.getglobal("AttributeError"))) {
               throw var8;
            }

            var1.setline(150);
            var4 = PyString.fromInterned("1");
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(151);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("."))._add(var1.getlocal(3))._add(PyString.fromInterned("."))._add(var1.getlocal(4));
         var1.setglobal("_prefix", var3);
         var3 = null;
      }

      var1.setline(152);
      var3 = PyString.fromInterned("%s.%.3f.%d")._mod(new PyTuple(new PyObject[]{var1.getglobal("_prefix"), var1.getlocal(0).__getattr__("time").__call__(var2), var1.getglobal("_get_next_counter").__call__(var2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$14(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("Decode common content-transfer-encodings (base64, quopri, uuencode).");
      var1.setline(159);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("base64"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(160);
         var3 = imp.importOne("base64", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(161);
         var3 = var1.getlocal(3).__getattr__("decode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(162);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("quoted-printable"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(163);
            var4 = imp.importOne("quopri", var1, -1);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(164);
            var3 = var1.getlocal(4).__getattr__("decode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(165);
            var4 = var1.getlocal(2);
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("uuencode"), PyString.fromInterned("x-uuencode"), PyString.fromInterned("uue"), PyString.fromInterned("x-uue")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(166);
               var4 = imp.importOne("uu", var1, -1);
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(167);
               var3 = var1.getlocal(5).__getattr__("decode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(168);
               var4 = var1.getlocal(2);
               var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("7bit"), PyString.fromInterned("8bit")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(169);
                  var3 = var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(170);
                  var4 = var1.getlocal(2);
                  var10000 = var4._in(var1.getglobal("decodetab"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(171);
                     var1.getglobal("pipethrough").__call__(var2, var1.getlocal(0), var1.getglobal("decodetab").__getitem__(var1.getlocal(2)), var1.getlocal(1));
                     var1.f_lasti = -1;
                     return Py.None;
                  } else {
                     var1.setline(173);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown Content-Transfer-Encoding: %s")._mod(var1.getlocal(2)));
                  }
               }
            }
         }
      }
   }

   public PyObject encode$15(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyString.fromInterned("Encode common content-transfer-encodings (base64, quopri, uuencode).");
      var1.setline(178);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("base64"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(179);
         var3 = imp.importOne("base64", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(180);
         var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(181);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("quoted-printable"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(182);
            var4 = imp.importOne("quopri", var1, -1);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(183);
            var3 = var1.getlocal(4).__getattr__("encode").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(184);
            var4 = var1.getlocal(2);
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("uuencode"), PyString.fromInterned("x-uuencode"), PyString.fromInterned("uue"), PyString.fromInterned("x-uue")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(185);
               var4 = imp.importOne("uu", var1, -1);
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(186);
               var3 = var1.getlocal(5).__getattr__("encode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(187);
               var4 = var1.getlocal(2);
               var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("7bit"), PyString.fromInterned("8bit")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(188);
                  var3 = var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(189);
                  var4 = var1.getlocal(2);
                  var10000 = var4._in(var1.getglobal("encodetab"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(190);
                     var1.getglobal("pipethrough").__call__(var2, var1.getlocal(0), var1.getglobal("encodetab").__getitem__(var1.getlocal(2)), var1.getlocal(1));
                     var1.f_lasti = -1;
                     return Py.None;
                  } else {
                     var1.setline(192);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown Content-Transfer-Encoding: %s")._mod(var1.getlocal(2)));
                  }
               }
            }
         }
      }
   }

   public PyObject pipeto$16(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(226);
      var1.getglobal("copyliteral").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(227);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pipethrough$17(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getglobal("tempfile").__getattr__("mkstemp").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(231);
      var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(232);
      var1.getglobal("copyliteral").__call__(var2, var1.getlocal(0), var1.getlocal(5));
      var1.setline(233);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(234);
      var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._add(PyString.fromInterned(" <"))._add(var1.getlocal(4)), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(235);
      var1.getglobal("copybinary").__call__(var2, var1.getlocal(6), var1.getlocal(2));
      var1.setline(236);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(237);
      var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copyliteral$18(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(240);
         if (Py.newInteger(1).__nonzero__()) {
            var1.setline(241);
            PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(242);
            if (!var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(243);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(2));
               continue;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject copybinary$19(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyInteger var3 = Py.newInteger(8192);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(247);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(248);
         PyObject var4 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(249);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            break;
         }

         var1.setline(250);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public mimetools$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Message$1 = Py.newCode(0, var2, var1, "Message", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "seekable"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str", "i", "fields"};
      parsetype$3 = Py.newCode(1, var2, var1, "parsetype", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str", "end", "f", "i"};
      parseplist$4 = Py.newCode(1, var2, var1, "parseplist", 50, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getplist$5 = Py.newCode(1, var2, var1, "getplist", 68, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n", "p"};
      getparam$6 = Py.newCode(2, var2, var1, "getparam", 71, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "p", "i"};
      getparamnames$7 = Py.newCode(1, var2, var1, "getparamnames", 79, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getencoding$8 = Py.newCode(1, var2, var1, "getencoding", 87, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      gettype$9 = Py.newCode(1, var2, var1, "gettype", 92, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getmaintype$10 = Py.newCode(1, var2, var1, "getmaintype", 95, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getsubtype$11 = Py.newCode(1, var2, var1, "getsubtype", 98, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"result"};
      _get_next_counter$12 = Py.newCode(0, var2, var1, "_get_next_counter", 115, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"time", "socket", "hostid", "uid", "pid"};
      choose_boundary$13 = Py.newCode(0, var2, var1, "choose_boundary", 125, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "encoding", "base64", "quopri", "uu"};
      decode$14 = Py.newCode(3, var2, var1, "decode", 157, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "encoding", "base64", "quopri", "uu"};
      encode$15 = Py.newCode(3, var2, var1, "encode", 176, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "command", "pipe"};
      pipeto$16 = Py.newCode(2, var2, var1, "pipeto", 224, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "command", "output", "fd", "tempname", "temp", "pipe"};
      pipethrough$17 = Py.newCode(3, var2, var1, "pipethrough", 229, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "line"};
      copyliteral$18 = Py.newCode(2, var2, var1, "copyliteral", 239, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "BUFSIZE", "line"};
      copybinary$19 = Py.newCode(2, var2, var1, "copybinary", 245, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mimetools$py("mimetools$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mimetools$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Message$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.parsetype$3(var2, var3);
         case 4:
            return this.parseplist$4(var2, var3);
         case 5:
            return this.getplist$5(var2, var3);
         case 6:
            return this.getparam$6(var2, var3);
         case 7:
            return this.getparamnames$7(var2, var3);
         case 8:
            return this.getencoding$8(var2, var3);
         case 9:
            return this.gettype$9(var2, var3);
         case 10:
            return this.getmaintype$10(var2, var3);
         case 11:
            return this.getsubtype$11(var2, var3);
         case 12:
            return this._get_next_counter$12(var2, var3);
         case 13:
            return this.choose_boundary$13(var2, var3);
         case 14:
            return this.decode$14(var2, var3);
         case 15:
            return this.encode$15(var2, var3);
         case 16:
            return this.pipeto$16(var2, var3);
         case 17:
            return this.pipethrough$17(var2, var3);
         case 18:
            return this.copyliteral$18(var2, var3);
         case 19:
            return this.copybinary$19(var2, var3);
         default:
            return null;
      }
   }
}

package email;

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
@Filename("email/generator.py")
public class generator$py extends PyFunctionTable implements PyRunnable {
   static generator$py self;
   static final PyCode f$0;
   static final PyCode _is8bitstring$1;
   static final PyCode Generator$2;
   static final PyCode __init__$3;
   static final PyCode write$4;
   static final PyCode flatten$5;
   static final PyCode clone$6;
   static final PyCode _write$7;
   static final PyCode _dispatch$8;
   static final PyCode _write_headers$9;
   static final PyCode _handle_text$10;
   static final PyCode _handle_multipart$11;
   static final PyCode _handle_multipart_signed$12;
   static final PyCode _handle_message_delivery_status$13;
   static final PyCode _handle_message$14;
   static final PyCode DecodedGenerator$15;
   static final PyCode __init__$16;
   static final PyCode _dispatch$17;
   static final PyCode _make_boundary$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Classes to generate plain text from a message object tree."));
      var1.setline(4);
      PyString.fromInterned("Classes to generate plain text from a message object tree.");
      var1.setline(6);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Generator"), PyString.fromInterned("DecodedGenerator")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(9);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(10);
      var5 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var5);
      var3 = null;
      var1.setline(11);
      var5 = imp.importOne("random", var1, -1);
      var1.setlocal("random", var5);
      var3 = null;
      var1.setline(12);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(14);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"Header"};
      var7 = imp.importFrom("email.header", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Header", var4);
      var4 = null;
      var1.setline(17);
      PyString var8 = PyString.fromInterned("_");
      var1.setlocal("UNDERSCORE", var8);
      var3 = null;
      var1.setline(18);
      var8 = PyString.fromInterned("\n");
      var1.setlocal("NL", var8);
      var3 = null;
      var1.setline(20);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^From "), (PyObject)var1.getname("re").__getattr__("MULTILINE"));
      var1.setlocal("fcre", var5);
      var3 = null;
      var1.setline(22);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, _is8bitstring$1, (PyObject)null);
      var1.setlocal("_is8bitstring", var9);
      var3 = null;
      var1.setline(32);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Generator", var7, Generator$2);
      var1.setlocal("Generator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(295);
      var8 = PyString.fromInterned("[Non-text (%(type)s) part of message omitted, filename %(filename)s]");
      var1.setlocal("_FMT", var8);
      var3 = null;
      var1.setline(297);
      var7 = new PyObject[]{var1.getname("Generator")};
      var4 = Py.makeClass("DecodedGenerator", var7, DecodedGenerator$15);
      var1.setlocal("DecodedGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(354);
      var5 = var1.getname("len").__call__(var2, var1.getname("repr").__call__(var2, var1.getname("sys").__getattr__("maxint")._sub(Py.newInteger(1))));
      var1.setlocal("_width", var5);
      var3 = null;
      var1.setline(355);
      var5 = PyString.fromInterned("%%0%dd")._mod(var1.getname("_width"));
      var1.setlocal("_fmt", var5);
      var3 = null;
      var1.setline(357);
      var7 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var7, _make_boundary$18, (PyObject)null);
      var1.setlocal("_make_boundary", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _is8bitstring$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
         try {
            var1.setline(25);
            var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("us-ascii"));
         } catch (Throwable var5) {
            PyException var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("UnicodeError"))) {
               var1.setline(27);
               var4 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var4;
            }

            throw var3;
         }
      }

      var1.setline(28);
      var4 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Generator$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generates output from a Message object tree.\n\n    This basic generator writes the message to the given file object as plain\n    text.\n    "));
      var1.setline(37);
      PyString.fromInterned("Generates output from a Message object tree.\n\n    This basic generator writes the message to the given file object as plain\n    text.\n    ");
      var1.setline(42);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), Py.newInteger(78)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Create the generator for message flattening.\n\n        outfp is the output file-like object for writing the message to.  It\n        must have a write() method.\n\n        Optional mangle_from_ is a flag that, when True (the default), escapes\n        From_ lines in the body of the message by putting a `>' in front of\n        them.\n\n        Optional maxheaderlen specifies the longest length for a non-continued\n        header.  When a header line is longer (in characters, with tabs\n        expanded to 8 spaces) than maxheaderlen, the header will split as\n        defined in the Header class.  Set maxheaderlen to zero to disable\n        header wrapping.  The default is 78, as recommended (but not required)\n        by RFC 2822.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$4, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(67);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, flatten$5, PyString.fromInterned("Print the message object tree rooted at msg to the output file\n        specified when the Generator instance was created.\n\n        unixfrom is a flag that forces the printing of a Unix From_ delimiter\n        before the first object in the message tree.  If the original message\n        has no From_ delimiter, a `standard' one is crafted.  By default, this\n        is False to inhibit the printing of any From_ delimiter.\n\n        Note that for subobjects, no From_ line is printed.\n        "));
      var1.setlocal("flatten", var4);
      var3 = null;
      var1.setline(85);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clone$6, PyString.fromInterned("Clone this generator with the exact same options."));
      var1.setlocal("clone", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write$7, (PyObject)null);
      var1.setlocal("_write", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dispatch$8, (PyObject)null);
      var1.setlocal("_dispatch", var4);
      var3 = null;
      var1.setline(140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write_headers$9, (PyObject)null);
      var1.setlocal("_write_headers", var4);
      var3 = null;
      var1.setline(172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_text$10, (PyObject)null);
      var1.setlocal("_handle_text", var4);
      var3 = null;
      var1.setline(183);
      PyObject var5 = var1.getname("_handle_text");
      var1.setlocal("_writeBody", var5);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_multipart$11, (PyObject)null);
      var1.setlocal("_handle_multipart", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_multipart_signed$12, (PyObject)null);
      var1.setlocal("_handle_multipart_signed", var4);
      var3 = null;
      var1.setline(254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_message_delivery_status$13, (PyObject)null);
      var1.setlocal("_handle_message_delivery_status", var4);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_message$14, (PyObject)null);
      var1.setlocal("_handle_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Create the generator for message flattening.\n\n        outfp is the output file-like object for writing the message to.  It\n        must have a write() method.\n\n        Optional mangle_from_ is a flag that, when True (the default), escapes\n        From_ lines in the body of the message by putting a `>' in front of\n        them.\n\n        Optional maxheaderlen specifies the longest length for a non-continued\n        header.  When a header line is longer (in characters, with tabs\n        expanded to 8 spaces) than maxheaderlen, the header will split as\n        defined in the Header class.  Set maxheaderlen to zero to disable\n        header wrapping.  The default is 78, as recommended (but not required)\n        by RFC 2822.\n        ");
      var1.setline(59);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_fp", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_mangle_from_", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_maxheaderlen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flatten$5(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Print the message object tree rooted at msg to the output file\n        specified when the Generator instance was created.\n\n        unixfrom is a flag that forces the printing of a Unix From_ delimiter\n        before the first object in the message tree.  If the original message\n        has no From_ delimiter, a `standard' one is crafted.  By default, this\n        is False to inhibit the printing of any From_ delimiter.\n\n        Note that for subobjects, no From_ line is printed.\n        ");
      var1.setline(78);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(79);
         PyObject var3 = var1.getlocal(1).__getattr__("get_unixfrom").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(80);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(81);
            var3 = PyString.fromInterned("From nobody ")._add(var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(82);
         var3 = var1.getlocal(0).__getattr__("_fp");
         Py.println(var3, var1.getlocal(3));
      }

      var1.setline(83);
      var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clone$6(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyString.fromInterned("Clone this generator with the exact same options.");
      var1.setline(87);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_mangle_from_"), var1.getlocal(0).__getattr__("_maxheaderlen"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _write$7(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getlocal(0).__getattr__("_fp");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(107);
         var4 = var1.getglobal("StringIO").__call__(var2);
         var1.getlocal(0).__setattr__("_fp", var4);
         var1.setlocal(3, var4);
         var1.setline(108);
         var1.getlocal(0).__getattr__("_dispatch").__call__(var2, var1.getlocal(1));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(110);
         var4 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_fp", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(110);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_fp", var4);
      var4 = null;
      var1.setline(113);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("_write_headers"), (PyObject)var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(114);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var1.getlocal(0).__getattr__("_write_headers").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(117);
         var1.getlocal(4).__call__(var2, var1.getlocal(0));
      }

      var1.setline(118);
      var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("getvalue").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dispatch$8(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getglobal("UNDERSCORE").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}))).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_handle_")._add(var1.getlocal(4)), var1.getglobal("None"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(130);
         var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(131);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_handle_")._add(var1.getlocal(6)), var1.getglobal("None"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(132);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(133);
            var3 = var1.getlocal(0).__getattr__("_writeBody");
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(134);
      var1.getlocal(5).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_headers$9(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(141);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(166);
            var3 = var1.getlocal(0).__getattr__("_fp");
            Py.printlnv(var3);
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(142);
         PyObject var8 = var1.getlocal(0).__getattr__("_fp");
         Py.printComma(var8, PyString.fromInterned("%s:")._mod(var1.getlocal(2)));
         var1.setline(143);
         var8 = var1.getlocal(0).__getattr__("_maxheaderlen");
         PyObject var10000 = var8._eq(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(145);
            var8 = var1.getlocal(0).__getattr__("_fp");
            Py.println(var8, var1.getlocal(3));
         } else {
            var1.setline(146);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Header")).__nonzero__()) {
               var1.setline(148);
               var8 = var1.getlocal(0).__getattr__("_fp");
               Py.println(var8, var1.getlocal(3).__getattr__("encode").__call__(var2));
            } else {
               var1.setline(149);
               if (var1.getglobal("_is8bitstring").__call__(var2, var1.getlocal(3)).__nonzero__()) {
                  var1.setline(156);
                  var8 = var1.getlocal(0).__getattr__("_fp");
                  Py.println(var8, var1.getlocal(3));
               } else {
                  var1.setline(163);
                  var8 = var1.getlocal(0).__getattr__("_fp");
                  PyObject var10001 = var1.getglobal("Header");
                  PyObject[] var9 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("_maxheaderlen"), var1.getlocal(2)};
                  String[] var7 = new String[]{"maxlinelen", "header_name"};
                  var10001 = var10001.__call__(var2, var9, var7);
                  var6 = null;
                  Py.println(var8, var10001.__getattr__("encode").__call__(var2));
               }
            }
         }
      }
   }

   public PyObject _handle_text$10(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyObject var3 = var1.getlocal(1).__getattr__("get_payload").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(175);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(176);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__not__().__nonzero__()) {
            var1.setline(177);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("string payload expected: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(2)))));
         } else {
            var1.setline(178);
            if (var1.getlocal(0).__getattr__("_mangle_from_").__nonzero__()) {
               var1.setline(179);
               var3 = var1.getglobal("fcre").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">From "), (PyObject)var1.getlocal(2));
               var1.setlocal(2, var3);
               var3 = null;
            }

            var1.setline(180);
            var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _handle_multipart$11(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(190);
      PyObject var7 = var1.getlocal(1).__getattr__("get_payload").__call__(var2);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(191);
      var7 = var1.getlocal(3);
      PyObject var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(192);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(193);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(195);
            var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(3));
            var1.setline(196);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(197);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("list")).__not__().__nonzero__()) {
            var1.setline(199);
            var3 = new PyList(new PyObject[]{var1.getlocal(3)});
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(200);
      var7 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(200);
         PyObject var4 = var7.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(206);
            var7 = var1.getlocal(1).__getattr__("get_boundary").__call__(var2);
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(207);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(210);
               var7 = var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(2));
               var1.setlocal(8, var7);
               var3 = null;
               var1.setline(211);
               var7 = var1.getglobal("_make_boundary").__call__(var2, var1.getlocal(8));
               var1.setlocal(7, var7);
               var3 = null;
               var1.setline(212);
               var1.getlocal(1).__getattr__("set_boundary").__call__(var2, var1.getlocal(7));
            }

            var1.setline(214);
            var7 = var1.getlocal(1).__getattr__("preamble");
            var10000 = var7._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(215);
               if (var1.getlocal(0).__getattr__("_mangle_from_").__nonzero__()) {
                  var1.setline(216);
                  var7 = var1.getglobal("fcre").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">From "), (PyObject)var1.getlocal(1).__getattr__("preamble"));
                  var1.setlocal(9, var7);
                  var3 = null;
               } else {
                  var1.setline(218);
                  var7 = var1.getlocal(1).__getattr__("preamble");
                  var1.setlocal(9, var7);
                  var3 = null;
               }

               var1.setline(219);
               var7 = var1.getlocal(0).__getattr__("_fp");
               Py.println(var7, var1.getlocal(9));
            }

            var1.setline(221);
            var7 = var1.getlocal(0).__getattr__("_fp");
            Py.println(var7, PyString.fromInterned("--")._add(var1.getlocal(7)));
            var1.setline(223);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(224);
               var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
            }

            var1.setline(228);
            var7 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(228);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(234);
                  var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, PyString.fromInterned("\n--")._add(var1.getlocal(7))._add(PyString.fromInterned("--")));
                  var1.setline(235);
                  var7 = var1.getlocal(1).__getattr__("epilogue");
                  var10000 = var7._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(236);
                     var7 = var1.getlocal(0).__getattr__("_fp");
                     Py.printlnv(var7);
                     var1.setline(237);
                     if (var1.getlocal(0).__getattr__("_mangle_from_").__nonzero__()) {
                        var1.setline(238);
                        var7 = var1.getglobal("fcre").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">From "), (PyObject)var1.getlocal(1).__getattr__("epilogue"));
                        var1.setlocal(11, var7);
                        var3 = null;
                     } else {
                        var1.setline(240);
                        var7 = var1.getlocal(1).__getattr__("epilogue");
                        var1.setlocal(11, var7);
                        var3 = null;
                     }

                     var1.setline(241);
                     var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(11));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(10, var4);
               var1.setline(230);
               var5 = var1.getlocal(0).__getattr__("_fp");
               Py.println(var5, PyString.fromInterned("\n--")._add(var1.getlocal(7)));
               var1.setline(232);
               var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(10));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(201);
         var5 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(202);
         var5 = var1.getlocal(0).__getattr__("clone").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(203);
         var10000 = var1.getlocal(6).__getattr__("flatten");
         PyObject[] var8 = new PyObject[]{var1.getlocal(4), var1.getglobal("False")};
         String[] var6 = new String[]{"unixfrom"};
         var10000.__call__(var2, var8, var6);
         var5 = null;
         var1.setline(204);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5).__getattr__("getvalue").__call__(var2));
      }
   }

   public PyObject _handle_multipart_signed$12(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyObject var3 = var1.getlocal(0).__getattr__("_maxheaderlen");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(249);
         PyInteger var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_maxheaderlen", var6);
         var4 = null;
         var1.setline(250);
         var1.getlocal(0).__getattr__("_handle_multipart").__call__(var2, var1.getlocal(1));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(252);
         var4 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_maxheaderlen", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(252);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_maxheaderlen", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handle_message_delivery_status$13(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(259);
      PyObject var7 = var1.getlocal(1).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(259);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(273);
            var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(2)));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(260);
         PyObject var5 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(261);
         var5 = var1.getlocal(0).__getattr__("clone").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(262);
         PyObject var10000 = var1.getlocal(5).__getattr__("flatten");
         PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getglobal("False")};
         String[] var6 = new String[]{"unixfrom"};
         var10000.__call__(var2, var8, var6);
         var5 = null;
         var1.setline(263);
         var5 = var1.getlocal(4).__getattr__("getvalue").__call__(var2);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(264);
         var5 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(266);
         var10000 = var1.getlocal(7);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
            var10000 = var5._eq(PyString.fromInterned(""));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(267);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)));
         } else {
            var1.setline(269);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(6));
         }
      }
   }

   public PyObject _handle_message$14(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(277);
      var3 = var1.getlocal(0).__getattr__("clone").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(287);
      var3 = var1.getlocal(1).__getattr__("get_payload").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(288);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")).__nonzero__()) {
         var1.setline(289);
         PyObject var10000 = var1.getlocal(3).__getattr__("flatten");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getglobal("False")};
         String[] var4 = new String[]{"unixfrom"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(290);
         var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(291);
      var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DecodedGenerator$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generates a text representation of a message.\n\n    Like the Generator base class, except that non-text parts are substituted\n    with a format string representing the part.\n    "));
      var1.setline(302);
      PyString.fromInterned("Generates a text representation of a message.\n\n    Like the Generator base class, except that non-text parts are substituted\n    with a format string representing the part.\n    ");
      var1.setline(303);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), Py.newInteger(78), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, PyString.fromInterned("Like Generator.__init__() except that an additional optional\n        argument is allowed.\n\n        Walks through all subparts of a message.  If the subpart is of main\n        type `text', then it prints the decoded payload of the subpart.\n\n        Otherwise, fmt is a format string that is used instead of the message\n        payload.  fmt is expanded with the following keywords (in\n        %(keyword)s format):\n\n        type       : Full MIME type of the non-text part\n        maintype   : Main MIME type of the non-text part\n        subtype    : Sub-MIME type of the non-text part\n        filename   : Filename of the non-text part\n        description: Description associated with the non-text part\n        encoding   : Content transfer encoding of the non-text part\n\n        The default value for fmt is None, meaning\n\n        [Non-text (%(type)s) part of message omitted, filename %(filename)s]\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dispatch$17, (PyObject)null);
      var1.setlocal("_dispatch", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyString.fromInterned("Like Generator.__init__() except that an additional optional\n        argument is allowed.\n\n        Walks through all subparts of a message.  If the subpart is of main\n        type `text', then it prints the decoded payload of the subpart.\n\n        Otherwise, fmt is a format string that is used instead of the message\n        payload.  fmt is expanded with the following keywords (in\n        %(keyword)s format):\n\n        type       : Full MIME type of the non-text part\n        maintype   : Main MIME type of the non-text part\n        subtype    : Sub-MIME type of the non-text part\n        filename   : Filename of the non-text part\n        description: Description associated with the non-text part\n        encoding   : Content transfer encoding of the non-text part\n\n        The default value for fmt is None, meaning\n\n        [Non-text (%(type)s) part of message omitted, filename %(filename)s]\n        ");
      var1.setline(325);
      var1.getglobal("Generator").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(326);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(327);
         var3 = var1.getglobal("_FMT");
         var1.getlocal(0).__setattr__("_fmt", var3);
         var3 = null;
      } else {
         var1.setline(329);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("_fmt", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dispatch$17(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = var1.getlocal(1).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(332);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(333);
         PyObject var5 = var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2);
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(334);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._eq(PyString.fromInterned("text"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(335);
            var5 = var1.getlocal(0);
            PyObject var10001 = var1.getlocal(2).__getattr__("get_payload");
            PyObject[] var6 = new PyObject[]{var1.getglobal("True")};
            String[] var7 = new String[]{"decode"};
            var10001 = var10001.__call__(var2, var6, var7);
            var6 = null;
            Py.println(var5, var10001);
         } else {
            var1.setline(336);
            var5 = var1.getlocal(3);
            var10000 = var5._eq(PyString.fromInterned("multipart"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(338);
            } else {
               var1.setline(340);
               var5 = var1.getlocal(0);
               Py.println(var5, var1.getlocal(0).__getattr__("_fmt")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("type"), var1.getlocal(2).__getattr__("get_content_type").__call__(var2), PyString.fromInterned("maintype"), var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2), PyString.fromInterned("subtype"), var1.getlocal(2).__getattr__("get_content_subtype").__call__(var2), PyString.fromInterned("filename"), var1.getlocal(2).__getattr__("get_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[no filename]")), PyString.fromInterned("description"), var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Description"), (PyObject)PyString.fromInterned("[no description]")), PyString.fromInterned("encoding"), var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Transfer-Encoding"), (PyObject)PyString.fromInterned("[no encoding]"))})));
            }
         }
      }
   }

   public PyObject _make_boundary$18(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyObject var3 = var1.getglobal("random").__getattr__("randrange").__call__(var2, var1.getglobal("sys").__getattr__("maxint"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(361);
      var3 = PyString.fromInterned("=")._mul(Py.newInteger(15))._add(var1.getglobal("_fmt")._mod(var1.getlocal(1)))._add(PyString.fromInterned("=="));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(362);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(363);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(364);
         PyObject var4 = var1.getlocal(2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(365);
         PyInteger var5 = Py.newInteger(0);
         var1.setlocal(4, var5);
         var4 = null;

         while(true) {
            var1.setline(366);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(367);
            var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("^--")._add(var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(3)))._add(PyString.fromInterned("(--)?$")), var1.getglobal("re").__getattr__("MULTILINE"));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(368);
            if (var1.getlocal(5).__getattr__("search").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
               break;
            }

            var1.setline(370);
            var4 = var1.getlocal(2)._add(PyString.fromInterned("."))._add(var1.getglobal("str").__call__(var2, var1.getlocal(4)));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(371);
            var4 = var1.getlocal(4);
            var4 = var4._iadd(Py.newInteger(1));
            var1.setlocal(4, var4);
         }

         var1.setline(372);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public generator$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      _is8bitstring$1 = Py.newCode(1, var2, var1, "_is8bitstring", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Generator$2 = Py.newCode(0, var2, var1, "Generator", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "outfp", "mangle_from_", "maxheaderlen"};
      __init__$3 = Py.newCode(4, var2, var1, "__init__", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$4 = Py.newCode(2, var2, var1, "write", 63, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "unixfrom", "ufrom"};
      flatten$5 = Py.newCode(3, var2, var1, "flatten", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp"};
      clone$6 = Py.newCode(2, var2, var1, "clone", 85, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "oldfp", "sfp", "meth"};
      _write$7 = Py.newCode(2, var2, var1, "_write", 93, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "main", "sub", "specific", "meth", "generic"};
      _dispatch$8 = Py.newCode(2, var2, var1, "_dispatch", 120, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "h", "v"};
      _write_headers$9 = Py.newCode(2, var2, var1, "_write_headers", 140, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "payload"};
      _handle_text$10 = Py.newCode(2, var2, var1, "_handle_text", 172, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "msgtexts", "subparts", "part", "s", "g", "boundary", "alltext", "preamble", "body_part", "epilogue"};
      _handle_multipart$11 = Py.newCode(2, var2, var1, "_handle_multipart", 185, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "old_maxheaderlen"};
      _handle_multipart_signed$12 = Py.newCode(2, var2, var1, "_handle_multipart_signed", 243, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "blocks", "part", "s", "g", "text", "lines"};
      _handle_message_delivery_status$13 = Py.newCode(2, var2, var1, "_handle_message_delivery_status", 254, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "s", "g", "payload"};
      _handle_message$14 = Py.newCode(2, var2, var1, "_handle_message", 275, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DecodedGenerator$15 = Py.newCode(0, var2, var1, "DecodedGenerator", 297, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "outfp", "mangle_from_", "maxheaderlen", "fmt"};
      __init__$16 = Py.newCode(5, var2, var1, "__init__", 303, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "part", "maintype"};
      _dispatch$17 = Py.newCode(2, var2, var1, "_dispatch", 331, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "token", "boundary", "b", "counter", "cre"};
      _make_boundary$18 = Py.newCode(1, var2, var1, "_make_boundary", 357, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new generator$py("email/generator$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(generator$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._is8bitstring$1(var2, var3);
         case 2:
            return this.Generator$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.write$4(var2, var3);
         case 5:
            return this.flatten$5(var2, var3);
         case 6:
            return this.clone$6(var2, var3);
         case 7:
            return this._write$7(var2, var3);
         case 8:
            return this._dispatch$8(var2, var3);
         case 9:
            return this._write_headers$9(var2, var3);
         case 10:
            return this._handle_text$10(var2, var3);
         case 11:
            return this._handle_multipart$11(var2, var3);
         case 12:
            return this._handle_multipart_signed$12(var2, var3);
         case 13:
            return this._handle_message_delivery_status$13(var2, var3);
         case 14:
            return this._handle_message$14(var2, var3);
         case 15:
            return this.DecodedGenerator$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this._dispatch$17(var2, var3);
         case 18:
            return this._make_boundary$18(var2, var3);
         default:
            return null;
      }
   }
}

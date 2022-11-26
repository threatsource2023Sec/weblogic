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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("mimify.py")
public class mimify$py extends PyFunctionTable implements PyRunnable {
   static mimify$py self;
   static final PyCode f$0;
   static final PyCode File$1;
   static final PyCode __init__$2;
   static final PyCode readline$3;
   static final PyCode HeaderFile$4;
   static final PyCode __init__$5;
   static final PyCode readline$6;
   static final PyCode mime_decode$7;
   static final PyCode mime_decode_header$8;
   static final PyCode unmimify_part$9;
   static final PyCode unmimify$10;
   static final PyCode mime_encode$11;
   static final PyCode mime_encode_header$12;
   static final PyCode mimify_part$13;
   static final PyCode mimify$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Mimification and unmimification of mail messages.\n\nDecode quoted-printable parts of a mail message or encode using\nquoted-printable.\n\nUsage:\n        mimify(input, output)\n        unmimify(input, output, decode_base64 = 0)\nto encode and decode respectively.  Input and output may be the name\nof a file or an open file object.  Only a readline() method is used\non the input file, only a write() method is used on the output file.\nWhen using file names, the input and output file names may be the\nsame.\n\nInteractive usage:\n        mimify.py -e [infile [outfile]]\n        mimify.py -d [infile [outfile]]\nto encode and decode respectively.  Infile defaults to standard\ninput and outfile to standard output.\n"));
      var1.setline(22);
      PyString.fromInterned("Mimification and unmimification of mail messages.\n\nDecode quoted-printable parts of a mail message or encode using\nquoted-printable.\n\nUsage:\n        mimify(input, output)\n        unmimify(input, output, decode_base64 = 0)\nto encode and decode respectively.  Input and output may be the name\nof a file or an open file object.  Only a readline() method is used\non the input file, only a write() method is used on the output file.\nWhen using file names, the input and output file names may be the\nsame.\n\nInteractive usage:\n        mimify.py -e [infile [outfile]]\n        mimify.py -d [infile [outfile]]\nto encode and decode respectively.  Infile defaults to standard\ninput and outfile to standard output.\n");
      var1.setline(25);
      PyInteger var3 = Py.newInteger(200);
      var1.setlocal("MAXLEN", var3);
      var3 = null;
      var1.setline(26);
      PyString var8 = PyString.fromInterned("ISO-8859-1");
      var1.setlocal("CHARSET", var8);
      var3 = null;
      var1.setline(27);
      var8 = PyString.fromInterned("> ");
      var1.setlocal("QUOTE", var8);
      var3 = null;
      var1.setline(30);
      PyObject var9 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var9);
      var3 = null;
      var1.setline(32);
      var9 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var9);
      var3 = null;
      var1.setline(33);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("the mimify module is deprecated; use the email package instead"), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(36);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("mimify"), PyString.fromInterned("unmimify"), PyString.fromInterned("mime_encode_header"), PyString.fromInterned("mime_decode_header")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(38);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^content-transfer-encoding:\\s*quoted-printable"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("qp", var9);
      var3 = null;
      var1.setline(39);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^content-transfer-encoding:\\s*base64"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("base64_re", var9);
      var3 = null;
      var1.setline(40);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^content-type:.*multipart/.*boundary=\"?([^;\"\n]*)"), (PyObject)var1.getname("re").__getattr__("I")._or(var1.getname("re").__getattr__("S")));
      var1.setlocal("mp", var9);
      var3 = null;
      var1.setline(41);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(content-type:.*charset=\")(us-ascii|iso-8859-[0-9]+)(\".*)"), (PyObject)var1.getname("re").__getattr__("I")._or(var1.getname("re").__getattr__("S")));
      var1.setlocal("chrset", var9);
      var3 = null;
      var1.setline(42);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^-*\n"));
      var1.setlocal("he", var9);
      var3 = null;
      var1.setline(43);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=([0-9a-f][0-9a-f])"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("mime_code", var9);
      var3 = null;
      var1.setline(44);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=\\?iso-8859-1\\?q\\?([^? \t\n]+)\\?="), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("mime_head", var9);
      var3 = null;
      var1.setline(45);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^subject:\\s+re: "), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("repl", var9);
      var3 = null;
      var1.setline(47);
      PyObject[] var17 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("File", var17, File$1);
      var1.setlocal("File", var4);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(71);
      var17 = Py.EmptyObjects;
      var4 = Py.makeClass("HeaderFile", var17, HeaderFile$4);
      var1.setlocal("HeaderFile", var4);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(94);
      var17 = Py.EmptyObjects;
      PyFunction var18 = new PyFunction(var1.f_globals, var17, mime_decode$7, PyString.fromInterned("Decode a single line of quoted-printable text to 8bit."));
      var1.setlocal("mime_decode", var18);
      var3 = null;
      var1.setline(107);
      var17 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var17, mime_decode_header$8, PyString.fromInterned("Decode a header line to 8bit."));
      var1.setlocal("mime_decode_header", var18);
      var3 = null;
      var1.setline(122);
      var17 = new PyObject[]{Py.newInteger(0)};
      var18 = new PyFunction(var1.f_globals, var17, unmimify_part$9, PyString.fromInterned("Convert a quoted-printable part of a MIME mail message to 8bit."));
      var1.setlocal("unmimify_part", var18);
      var3 = null;
      var1.setline(207);
      var17 = new PyObject[]{Py.newInteger(0)};
      var18 = new PyFunction(var1.f_globals, var17, unmimify$10, PyString.fromInterned("Convert quoted-printable parts of a MIME mail message to 8bit."));
      var1.setlocal("unmimify", var18);
      var3 = null;
      var1.setline(225);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[=\u007f-ÿ]"));
      var1.setlocal("mime_char", var9);
      var3 = null;
      var1.setline(226);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[=?\u007f-ÿ]"));
      var1.setlocal("mime_header_char", var9);
      var3 = null;
      var1.setline(228);
      var17 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var17, mime_encode$11, PyString.fromInterned("Code a single line as quoted-printable.\n    If header is set, quote some extra characters."));
      var1.setlocal("mime_encode", var18);
      var3 = null;
      var1.setline(260);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([ \t(]|^)([-a-zA-Z0-9_+]*[\u007f-ÿ][-a-zA-Z0-9_+\u007f-ÿ]*)(?=[ \t)]|\n)"));
      var1.setlocal("mime_header", var9);
      var3 = null;
      var1.setline(262);
      var17 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var17, mime_encode_header$12, PyString.fromInterned("Code a single header line as quoted-printable."));
      var1.setlocal("mime_encode_header", var18);
      var3 = null;
      var1.setline(276);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^mime-version:"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("mv", var9);
      var3 = null;
      var1.setline(277);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^content-transfer-encoding:"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("cte", var9);
      var3 = null;
      var1.setline(278);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\u007f-ÿ]"));
      var1.setlocal("iso_char", var9);
      var3 = null;
      var1.setline(280);
      var17 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var17, mimify_part$13, PyString.fromInterned("Convert an 8bit part of a MIME mail message to quoted-printable."));
      var1.setlocal("mimify_part", var18);
      var3 = null;
      var1.setline(415);
      var17 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var17, mimify$14, PyString.fromInterned("Convert 8bit parts of a MIME mail message to quoted-printable."));
      var1.setlocal("mimify", var18);
      var3 = null;
      var1.setline(433);
      var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(434);
      var9 = var1.getname("__name__");
      PyObject var10000 = var9._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var9 = var1.getname("len").__call__(var2, var1.getname("sys").__getattr__("argv"));
         var10000 = var9._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var9 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0));
            var10000 = var9._eq(PyString.fromInterned("mimify"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(435);
         var9 = imp.importOne("getopt", var1, -1);
         var1.setlocal("getopt", var9);
         var3 = null;
         var1.setline(436);
         var8 = PyString.fromInterned("Usage: mimify [-l len] -[ed] [infile [outfile]]");
         var1.setlocal("usage", var8);
         var3 = null;
         var1.setline(438);
         var3 = Py.newInteger(0);
         var1.setlocal("decode_base64", var3);
         var3 = null;
         var1.setline(439);
         var9 = var1.getname("getopt").__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("l:edb"));
         PyObject[] var10 = Py.unpackSequence(var9, 2);
         PyObject var5 = var10[0];
         var1.setlocal("opts", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("args", var5);
         var5 = null;
         var3 = null;
         var1.setline(440);
         var9 = var1.getname("len").__call__(var2, var1.getname("args"));
         var10000 = var9._notin(new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1), Py.newInteger(2)}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(441);
            Py.println(var1.getname("usage"));
            var1.setline(442);
            var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }

         var1.setline(443);
         PyTuple var13 = new PyTuple(new PyObject[]{PyString.fromInterned("-e"), PyString.fromInterned("")});
         var10000 = var13._in(var1.getname("opts"));
         var5 = null;
         var9 = var10000;
         var13 = new PyTuple(new PyObject[]{PyString.fromInterned("-d"), PyString.fromInterned("")});
         var10000 = var13._in(var1.getname("opts"));
         var5 = null;
         var10000 = var9._eq(var10000);
         var3 = null;
         PyTuple var19;
         if (!var10000.__nonzero__()) {
            var19 = new PyTuple(new PyObject[]{PyString.fromInterned("-b"), PyString.fromInterned("")});
            var10000 = var19._in(var1.getname("opts"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var19 = new PyTuple(new PyObject[]{PyString.fromInterned("-d"), PyString.fromInterned("")});
               var10000 = var19._notin(var1.getname("opts"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(445);
            Py.println(var1.getname("usage"));
            var1.setline(446);
            var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }

         var1.setline(447);
         var9 = var1.getname("opts").__iter__();

         label74:
         while(true) {
            while(true) {
               var1.setline(447);
               var4 = var9.__iternext__();
               if (var4 == null) {
                  var1.setline(460);
                  var9 = var1.getname("len").__call__(var2, var1.getname("args"));
                  var10000 = var9._eq(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(461);
                     var19 = new PyTuple(new PyObject[]{var1.getname("sys").__getattr__("stdin"), var1.getname("sys").__getattr__("stdout")});
                     var1.setlocal("encode_args", var19);
                     var3 = null;
                  } else {
                     var1.setline(462);
                     var9 = var1.getname("len").__call__(var2, var1.getname("args"));
                     var10000 = var9._eq(Py.newInteger(1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(463);
                        var19 = new PyTuple(new PyObject[]{var1.getname("args").__getitem__(Py.newInteger(0)), var1.getname("sys").__getattr__("stdout")});
                        var1.setlocal("encode_args", var19);
                        var3 = null;
                     } else {
                        var1.setline(465);
                        var19 = new PyTuple(new PyObject[]{var1.getname("args").__getitem__(Py.newInteger(0)), var1.getname("args").__getitem__(Py.newInteger(1))});
                        var1.setlocal("encode_args", var19);
                        var3 = null;
                     }
                  }

                  var1.setline(466);
                  if (var1.getname("decode_base64").__nonzero__()) {
                     var1.setline(467);
                     var9 = var1.getname("encode_args")._add(new PyTuple(new PyObject[]{var1.getname("decode_base64")}));
                     var1.setlocal("encode_args", var9);
                     var3 = null;
                  }

                  var1.setline(468);
                  var10000 = var1.getname("encode");
                  var17 = Py.EmptyObjects;
                  String[] var11 = new String[0];
                  var10000._callextra(var17, var11, var1.getname("encode_args"), (PyObject)null);
                  var3 = null;
                  break label74;
               }

               PyObject[] var14 = Py.unpackSequence(var4, 2);
               PyObject var6 = var14[0];
               var1.setlocal("o", var6);
               var6 = null;
               var6 = var14[1];
               var1.setlocal("a", var6);
               var6 = null;
               var1.setline(448);
               var5 = var1.getname("o");
               var10000 = var5._eq(PyString.fromInterned("-e"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(449);
                  var5 = var1.getname("mimify");
                  var1.setlocal("encode", var5);
                  var5 = null;
               } else {
                  var1.setline(450);
                  var5 = var1.getname("o");
                  var10000 = var5._eq(PyString.fromInterned("-d"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(451);
                     var5 = var1.getname("unmimify");
                     var1.setlocal("encode", var5);
                     var5 = null;
                  } else {
                     var1.setline(452);
                     var5 = var1.getname("o");
                     var10000 = var5._eq(PyString.fromInterned("-l"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        try {
                           var1.setline(454);
                           var5 = var1.getname("int").__call__(var2, var1.getname("a"));
                           var1.setlocal("MAXLEN", var5);
                           var5 = null;
                        } catch (Throwable var7) {
                           PyException var16 = Py.setException(var7, var1);
                           if (!var16.match(new PyTuple(new PyObject[]{var1.getname("ValueError"), var1.getname("OverflowError")}))) {
                              throw var16;
                           }

                           var1.setline(456);
                           Py.println(var1.getname("usage"));
                           var1.setline(457);
                           var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                        }
                     } else {
                        var1.setline(458);
                        var5 = var1.getname("o");
                        var10000 = var5._eq(PyString.fromInterned("-b"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(459);
                           PyInteger var15 = Py.newInteger(1);
                           var1.setlocal("decode_base64", var15);
                           var5 = null;
                        }
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject File$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A simple fake file object that knows about limited read-ahead and\n    boundaries.  The only supported method is readline()."));
      var1.setline(49);
      PyString.fromInterned("A simple fake file object that knows about limited read-ahead and\n    boundaries.  The only supported method is readline().");
      var1.setline(51);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$3, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("boundary", var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("peek", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$3(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getlocal(0).__getattr__("peek");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(58);
         var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(59);
         PyObject var4 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(60);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(61);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(62);
            if (var1.getlocal(0).__getattr__("boundary").__nonzero__()) {
               var1.setline(63);
               var4 = var1.getlocal(1);
               var10000 = var4._eq(var1.getlocal(0).__getattr__("boundary")._add(PyString.fromInterned("\n")));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(64);
                  var4 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("peek", var4);
                  var4 = null;
                  var1.setline(65);
                  var5 = PyString.fromInterned("");
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setline(66);
               var4 = var1.getlocal(1);
               var10000 = var4._eq(var1.getlocal(0).__getattr__("boundary")._add(PyString.fromInterned("--\n")));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(67);
                  var4 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("peek", var4);
                  var4 = null;
                  var1.setline(68);
                  var5 = PyString.fromInterned("");
                  var1.f_lasti = -1;
                  return var5;
               }
            }

            var1.setline(69);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject HeaderFile$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(72);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$6, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("peek", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$6(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(0).__getattr__("peek");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var3 = var1.getlocal(0).__getattr__("peek");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(79);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("peek", var3);
         var3 = null;
      } else {
         var1.setline(81);
         var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(82);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(83);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(84);
         if (var1.getglobal("he").__getattr__("match").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(85);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            while(true) {
               var1.setline(86);
               if (!Py.newInteger(1).__nonzero__()) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(87);
               PyObject var4 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2);
               var1.getlocal(0).__setattr__("peek", var4);
               var4 = null;
               var1.setline(88);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("peek"));
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("peek").__getitem__(Py.newInteger(0));
                  var10000 = var4._ne(PyString.fromInterned(" "));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("peek").__getitem__(Py.newInteger(0));
                     var10000 = var4._ne(PyString.fromInterned("\t"));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(90);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(91);
               var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("peek"));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(92);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("peek", var4);
               var4 = null;
            }
         }
      }
   }

   public PyObject mime_decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyString.fromInterned("Decode a single line of quoted-printable text to 8bit.");
      var1.setline(96);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(97);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(2, var4);
      var3 = null;

      PyObject var5;
      while(true) {
         var1.setline(98);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(99);
         var5 = var1.getglobal("mime_code").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(100);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(102);
         var5 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(var1.getlocal(2), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null))._add(var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), (PyObject)Py.newInteger(16))));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(104);
         var5 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(105);
      var5 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject mime_decode_header$8(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyString.fromInterned("Decode a header line to 8bit.");
      var1.setline(109);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(110);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(2, var4);
      var3 = null;

      PyObject var5;
      while(true) {
         var1.setline(111);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(112);
         var5 = var1.getglobal("mime_head").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(113);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(115);
         var5 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(117);
         var5 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")));
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(118);
         var5 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(var1.getlocal(2), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null))._add(var1.getglobal("mime_decode").__call__(var2, var1.getlocal(4)));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(119);
         var5 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(120);
      var5 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject unmimify_part$9(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Convert a quoted-printable part of a MIME mail message to 8bit.");
      var1.setline(124);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(125);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(126);
      var4 = Py.newInteger(0);
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(127);
      var4 = Py.newInteger(0);
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(128);
      PyObject var10000 = var1.getlocal(0).__getattr__("boundary");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("boundary").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var10000 = var3._eq(var1.getglobal("QUOTE"));
         var3 = null;
      }

      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(129);
         var3 = var1.getglobal("QUOTE");
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(131);
         var5 = PyString.fromInterned("");
         var1.setlocal(7, var5);
         var3 = null;
      }

      var1.setline(134);
      var3 = var1.getglobal("HeaderFile").__call__(var2, var1.getlocal(0));
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(135);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(136);
         var3 = var1.getlocal(8).__getattr__("readline").__call__(var2);
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(137);
         if (var1.getlocal(9).__not__().__nonzero__()) {
            var1.setline(138);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(139);
         var10000 = var1.getlocal(7);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(9).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)null);
            var10000 = var3._eq(var1.getlocal(7));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(140);
            var3 = var1.getlocal(9).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)null, (PyObject)null);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(141);
            var3 = var1.getlocal(7);
            var1.setlocal(10, var3);
            var3 = null;
         } else {
            var1.setline(143);
            var5 = PyString.fromInterned("");
            var1.setlocal(10, var5);
            var3 = null;
         }

         var1.setline(144);
         var3 = var1.getglobal("mime_decode_header").__call__(var2, var1.getlocal(9));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(145);
         if (var1.getglobal("qp").__getattr__("match").__call__(var2, var1.getlocal(9)).__nonzero__()) {
            var1.setline(146);
            var4 = Py.newInteger(1);
            var1.setlocal(4, var4);
            var3 = null;
         } else {
            var1.setline(148);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("base64_re").__getattr__("match").__call__(var2, var1.getlocal(9));
            }

            if (var10000.__nonzero__()) {
               var1.setline(149);
               var4 = Py.newInteger(1);
               var1.setlocal(5, var4);
               var3 = null;
            } else {
               var1.setline(151);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(10)._add(var1.getlocal(9)));
               var1.setline(152);
               var10000 = var1.getlocal(7).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("repl").__getattr__("match").__call__(var2, var1.getlocal(9));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(154);
                  var4 = Py.newInteger(1);
                  var1.setlocal(6, var4);
                  var3 = null;
               }

               var1.setline(155);
               var3 = var1.getglobal("mp").__getattr__("match").__call__(var2, var1.getlocal(9));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(156);
               if (var1.getlocal(11).__nonzero__()) {
                  var1.setline(157);
                  var3 = PyString.fromInterned("--")._add(var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                  var1.setlocal(3, var3);
                  var3 = null;
               }

               var1.setline(158);
               if (var1.getglobal("he").__getattr__("match").__call__(var2, var1.getlocal(9)).__nonzero__()) {
                  break;
               }
            }
         }
      }

      var1.setline(160);
      var10000 = var1.getlocal(6);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(3);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(161);
         var4 = Py.newInteger(0);
         var1.setlocal(6, var4);
         var3 = null;
      }

      while(true) {
         var1.setline(164);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(165);
         var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(166);
         if (var1.getlocal(9).__not__().__nonzero__()) {
            var1.setline(167);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(168);
         var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, var1.getglobal("mime_head"), (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(9));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(169);
         var10000 = var1.getlocal(7);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(9).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)null);
            var10000 = var3._eq(var1.getlocal(7));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(170);
            var3 = var1.getlocal(9).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)null, (PyObject)null);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(171);
            var3 = var1.getlocal(7);
            var1.setlocal(10, var3);
            var3 = null;
         } else {
            var1.setline(173);
            var5 = PyString.fromInterned("");
            var1.setlocal(10, var5);
            var3 = null;
         }

         do {
            var1.setline(176);
            if (!var1.getlocal(3).__nonzero__()) {
               break;
            }

            var1.setline(177);
            var3 = var1.getlocal(9);
            var10000 = var3._eq(var1.getlocal(3)._add(PyString.fromInterned("--\n")));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(178);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(10)._add(var1.getlocal(9)));
               var1.setline(179);
               var3 = var1.getglobal("None");
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(180);
               var3 = var1.getglobal("None");
               var1.setlocal(9, var3);
               var3 = null;
               break;
            }

            var1.setline(182);
            var3 = var1.getlocal(9);
            var10000 = var3._eq(var1.getlocal(3)._add(PyString.fromInterned("\n")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(183);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(10)._add(var1.getlocal(9)));
            var1.setline(184);
            var3 = var1.getglobal("File").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(185);
            var1.getglobal("unmimify_part").__call__(var2, var1.getlocal(12), var1.getlocal(1), var1.getlocal(2));
            var1.setline(186);
            var3 = var1.getlocal(12).__getattr__("peek");
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(187);
         } while(!var1.getlocal(9).__not__().__nonzero__());

         var1.setline(193);
         var10000 = var1.getlocal(9);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            while(true) {
               var1.setline(194);
               var3 = var1.getlocal(9).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
               var10000 = var3._eq(PyString.fromInterned("=\n"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(200);
                  var3 = var1.getglobal("mime_decode").__call__(var2, var1.getlocal(9));
                  var1.setlocal(9, var3);
                  var3 = null;
                  break;
               }

               var1.setline(195);
               var3 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(196);
               var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
               var1.setlocal(13, var3);
               var3 = null;
               var1.setline(197);
               var3 = var1.getlocal(13).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getglobal("QUOTE")), (PyObject)null);
               var10000 = var3._eq(var1.getglobal("QUOTE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(198);
                  var3 = var1.getlocal(13).__getslice__(var1.getglobal("len").__call__(var2, var1.getglobal("QUOTE")), (PyObject)null, (PyObject)null);
                  var1.setlocal(13, var3);
                  var3 = null;
               }

               var1.setline(199);
               var3 = var1.getlocal(9)._add(var1.getlocal(13));
               var1.setlocal(9, var3);
               var3 = null;
            }
         }

         var1.setline(201);
         var10000 = var1.getlocal(9);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(10).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(202);
            var3 = imp.importOne("base64", var1, -1);
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(203);
            var3 = var1.getlocal(14).__getattr__("decodestring").__call__(var2, var1.getlocal(9));
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(204);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(205);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(10)._add(var1.getlocal(9)));
         }
      }
   }

   public PyObject unmimify$10(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyString.fromInterned("Convert quoted-printable parts of a MIME mail message to 8bit.");
      var1.setline(209);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(211);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(212);
            var3 = imp.importOne("os", var1, -1);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(213);
            var3 = var1.getlocal(4).__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(214);
            var1.getlocal(4).__getattr__("rename").__call__(var2, var1.getlocal(0), var1.getlocal(4).__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), PyString.fromInterned(",")._add(var1.getlocal(6))));
         }
      } else {
         var1.setline(216);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(217);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(220);
         var3 = var1.getlocal(1);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(221);
      var3 = var1.getglobal("File").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(222);
      var1.getglobal("unmimify_part").__call__(var2, var1.getlocal(8), var1.getlocal(7), var1.getlocal(2));
      var1.setline(223);
      var1.getlocal(7).__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mime_encode$11(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Code a single line as quoted-printable.\n    If header is set, quote some extra characters.");
      var1.setline(231);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(232);
         var3 = var1.getglobal("mime_header_char");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(234);
         var3 = var1.getglobal("mime_char");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(235);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(236);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(237);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ge(Py.newInteger(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("From "));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(239);
         var3 = PyString.fromInterned("=%02x")._mod(var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"))).__getattr__("upper").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(240);
         var5 = Py.newInteger(1);
         var1.setlocal(4, var5);
         var3 = null;
      }

      while(true) {
         var1.setline(241);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(242);
         var3 = var1.getlocal(2).__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(243);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(245);
         var3 = var1.getlocal(3)._add(var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(5).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null))._add(PyString.fromInterned("=%02x")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)))).__getattr__("upper").__call__(var2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(247);
         var3 = var1.getlocal(5).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(248);
      var3 = var1.getlocal(3)._add(var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(250);
      var4 = PyString.fromInterned("");
      var1.setlocal(3, var4);
      var3 = null;

      while(true) {
         var1.setline(251);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._ge(Py.newInteger(75));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(258);
            var3 = var1.getlocal(3)._add(var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(252);
         var5 = Py.newInteger(73);
         var1.setlocal(6, var5);
         var3 = null;

         while(true) {
            var1.setline(253);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(6));
            var10000 = var3._eq(PyString.fromInterned("="));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(var1.getlocal(6)._sub(Py.newInteger(1)));
               var10000 = var3._eq(PyString.fromInterned("="));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(255);
               var3 = var1.getlocal(6)._add(Py.newInteger(1));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(256);
               var3 = var1.getlocal(3)._add(var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null))._add(PyString.fromInterned("=\n"));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(257);
               var3 = var1.getlocal(0).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var3);
               var3 = null;
               break;
            }

            var1.setline(254);
            var3 = var1.getlocal(6)._sub(Py.newInteger(1));
            var1.setlocal(6, var3);
            var3 = null;
         }
      }
   }

   public PyObject mime_encode_header$12(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyString.fromInterned("Code a single header line as quoted-printable.");
      var1.setline(264);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(265);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(2, var4);
      var3 = null;

      PyObject var5;
      while(true) {
         var1.setline(266);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(267);
         var5 = var1.getglobal("mime_header").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(268);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(270);
         var5 = PyString.fromInterned("%s%s%s=?%s?Q?%s?=")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getslice__(var1.getlocal(2), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null), var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getglobal("CHARSET"), var1.getglobal("mime_encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)), (PyObject)Py.newInteger(1))}));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(273);
         var5 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(274);
      var5 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject mimify_part$13(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("Convert an 8bit part of a MIME mail message to quoted-printable.");
      var1.setline(282);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var1.setlocal(4, var3);
      var1.setlocal(5, var3);
      var1.setline(283);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(284);
      var3 = Py.newInteger(0);
      var1.setlocal(7, var3);
      var1.setlocal(8, var3);
      var1.setlocal(9, var3);
      var1.setline(286);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(10, var7);
      var3 = null;
      var1.setline(287);
      PyString var8 = PyString.fromInterned("");
      var1.setlocal(11, var8);
      var3 = null;
      var1.setline(288);
      var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(12, var7);
      var3 = null;
      var1.setline(289);
      var8 = PyString.fromInterned("");
      var1.setlocal(13, var8);
      var3 = null;
      var1.setline(291);
      var6 = var1.getglobal("HeaderFile").__call__(var2, var1.getlocal(0));
      var1.setlocal(14, var6);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(292);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(293);
         var6 = var1.getlocal(14).__getattr__("readline").__call__(var2);
         var1.setlocal(15, var6);
         var3 = null;
         var1.setline(294);
         if (var1.getlocal(15).__not__().__nonzero__()) {
            break;
         }

         var1.setline(296);
         var10000 = var1.getlocal(8).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("iso_char").__getattr__("search").__call__(var2, var1.getlocal(15));
         }

         if (var10000.__nonzero__()) {
            var1.setline(297);
            var3 = Py.newInteger(1);
            var1.setlocal(8, var3);
            var3 = null;
         }

         var1.setline(298);
         if (var1.getglobal("mv").__getattr__("match").__call__(var2, var1.getlocal(15)).__nonzero__()) {
            var1.setline(299);
            var3 = Py.newInteger(1);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(300);
         if (var1.getglobal("cte").__getattr__("match").__call__(var2, var1.getlocal(15)).__nonzero__()) {
            var1.setline(301);
            var3 = Py.newInteger(1);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(302);
            if (var1.getglobal("qp").__getattr__("match").__call__(var2, var1.getlocal(15)).__nonzero__()) {
               var1.setline(303);
               var3 = Py.newInteger(1);
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(304);
               if (var1.getglobal("base64_re").__getattr__("match").__call__(var2, var1.getlocal(15)).__nonzero__()) {
                  var1.setline(305);
                  var3 = Py.newInteger(1);
                  var1.setlocal(5, var3);
                  var3 = null;
               }
            }
         }

         var1.setline(306);
         var6 = var1.getglobal("mp").__getattr__("match").__call__(var2, var1.getlocal(15));
         var1.setlocal(16, var6);
         var3 = null;
         var1.setline(307);
         if (var1.getlocal(16).__nonzero__()) {
            var1.setline(308);
            var6 = PyString.fromInterned("--")._add(var1.getlocal(16).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(6, var6);
            var3 = null;
         }

         var1.setline(309);
         if (var1.getglobal("he").__getattr__("match").__call__(var2, var1.getlocal(15)).__nonzero__()) {
            var1.setline(310);
            var6 = var1.getlocal(15);
            var1.setlocal(11, var6);
            var3 = null;
            break;
         }

         var1.setline(312);
         var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(15));
      }

      while(true) {
         var1.setline(315);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(316);
         var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(15, var6);
         var3 = null;
         var1.setline(317);
         if (var1.getlocal(15).__not__().__nonzero__()) {
            break;
         }

         var1.setline(319);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(320);
            var6 = var1.getlocal(15);
            var10000 = var6._eq(var1.getlocal(6)._add(PyString.fromInterned("--\n")));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(321);
               var6 = var1.getlocal(15);
               var1.setlocal(13, var6);
               var3 = null;
               break;
            }

            var1.setline(323);
            var6 = var1.getlocal(15);
            var10000 = var6._eq(var1.getlocal(6)._add(PyString.fromInterned("\n")));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(324);
               var6 = var1.getlocal(15);
               var1.setlocal(13, var6);
               var3 = null;
               break;
            }
         }

         var1.setline(326);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(327);
            var1.getlocal(12).__getattr__("append").__call__(var2, var1.getlocal(15));
         } else {
            var1.setline(329);
            if (var1.getlocal(4).__nonzero__()) {
               while(true) {
                  var1.setline(330);
                  var6 = var1.getlocal(15).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
                  var10000 = var6._eq(PyString.fromInterned("=\n"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(336);
                     var6 = var1.getglobal("mime_decode").__call__(var2, var1.getlocal(15));
                     var1.setlocal(15, var6);
                     var3 = null;
                     break;
                  }

                  var1.setline(331);
                  var6 = var1.getlocal(15).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
                  var1.setlocal(15, var6);
                  var3 = null;
                  var1.setline(332);
                  var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
                  var1.setlocal(17, var6);
                  var3 = null;
                  var1.setline(333);
                  var6 = var1.getlocal(17).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getglobal("QUOTE")), (PyObject)null);
                  var10000 = var6._eq(var1.getglobal("QUOTE"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(334);
                     var6 = var1.getlocal(17).__getslice__(var1.getglobal("len").__call__(var2, var1.getglobal("QUOTE")), (PyObject)null, (PyObject)null);
                     var1.setlocal(17, var6);
                     var3 = null;
                  }

                  var1.setline(335);
                  var6 = var1.getlocal(15)._add(var1.getlocal(17));
                  var1.setlocal(15, var6);
                  var3 = null;
               }
            }

            var1.setline(337);
            var1.getlocal(12).__getattr__("append").__call__(var2, var1.getlocal(15));
            var1.setline(338);
            if (var1.getlocal(9).__not__().__nonzero__()) {
               var1.setline(339);
               if (var1.getglobal("iso_char").__getattr__("search").__call__(var2, var1.getlocal(15)).__nonzero__()) {
                  var1.setline(340);
                  var3 = Py.newInteger(1);
                  var1.setlocal(9, var3);
                  var1.setlocal(7, var3);
               }
            }

            var1.setline(341);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(342);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(15));
               var10000 = var6._gt(var1.getglobal("MAXLEN"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(343);
                  var3 = Py.newInteger(1);
                  var1.setlocal(7, var3);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(346);
      var6 = var1.getlocal(10).__iter__();

      while(true) {
         var1.setline(346);
         PyObject var4 = var6.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(369);
            var10000 = var1.getlocal(8);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(7);
            }

            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(370);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Mime-Version: 1.0\n"));
               var1.setline(371);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: text/plain; "));
               var1.setline(372);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(373);
                  var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("charset=\"%s\"\n")._mod(var1.getglobal("CHARSET")));
               } else {
                  var1.setline(375);
                  var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset=\"us-ascii\"\n"));
               }
            }

            var1.setline(376);
            var10000 = var1.getlocal(7);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(377);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Transfer-Encoding: quoted-printable\n"));
            }

            var1.setline(378);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(11));
            var1.setline(380);
            var6 = var1.getlocal(12).__iter__();

            while(true) {
               var1.setline(380);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(384);
                  var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(13));
                  var1.setline(386);
                  var6 = var1.getlocal(13);
                  var1.setlocal(15, var6);
                  var3 = null;

                  while(true) {
                     var1.setline(387);
                     if (!var1.getlocal(6).__nonzero__()) {
                        break;
                     }

                     var1.setline(388);
                     var6 = var1.getlocal(15);
                     var10000 = var6._eq(var1.getlocal(6)._add(PyString.fromInterned("--\n")));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        while(true) {
                           var1.setline(390);
                           if (!Py.newInteger(1).__nonzero__()) {
                              break;
                           }

                           var1.setline(391);
                           var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
                           var1.setlocal(15, var6);
                           var3 = null;
                           var1.setline(392);
                           if (var1.getlocal(15).__not__().__nonzero__()) {
                              var1.setline(393);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setline(394);
                           if (var1.getlocal(7).__nonzero__()) {
                              var1.setline(395);
                              var6 = var1.getglobal("mime_encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)Py.newInteger(0));
                              var1.setlocal(15, var6);
                              var3 = null;
                           }

                           var1.setline(396);
                           var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(15));
                        }
                     }

                     var1.setline(397);
                     var6 = var1.getlocal(15);
                     var10000 = var6._eq(var1.getlocal(6)._add(PyString.fromInterned("\n")));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(398);
                        var6 = var1.getglobal("File").__call__(var2, var1.getlocal(0), var1.getlocal(6));
                        var1.setlocal(19, var6);
                        var3 = null;
                        var1.setline(399);
                        var1.getglobal("mimify_part").__call__((ThreadState)var2, var1.getlocal(19), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
                        var1.setline(400);
                        var6 = var1.getlocal(19).__getattr__("peek");
                        var1.setlocal(15, var6);
                        var3 = null;
                        var1.setline(401);
                        if (var1.getlocal(15).__not__().__nonzero__()) {
                           break;
                        }

                        var1.setline(404);
                        var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(15));
                     } else {
                        while(true) {
                           var1.setline(407);
                           if (!Py.newInteger(1).__nonzero__()) {
                              break;
                           }

                           var1.setline(408);
                           var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
                           var1.setlocal(15, var6);
                           var3 = null;
                           var1.setline(409);
                           if (var1.getlocal(15).__not__().__nonzero__()) {
                              var1.setline(410);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setline(411);
                           if (var1.getlocal(7).__nonzero__()) {
                              var1.setline(412);
                              var6 = var1.getglobal("mime_encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)Py.newInteger(0));
                              var1.setlocal(15, var6);
                              var3 = null;
                           }

                           var1.setline(413);
                           var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(15));
                        }
                     }
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(15, var4);
               var1.setline(381);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(382);
                  var5 = var1.getglobal("mime_encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)Py.newInteger(0));
                  var1.setlocal(15, var5);
                  var5 = null;
               }

               var1.setline(383);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(15));
            }
         }

         var1.setlocal(15, var4);
         var1.setline(347);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(348);
            var5 = var1.getglobal("mime_encode_header").__call__(var2, var1.getlocal(15));
            var1.setlocal(15, var5);
            var5 = null;
         }

         var1.setline(349);
         var5 = var1.getglobal("chrset").__getattr__("match").__call__(var2, var1.getlocal(15));
         var1.setlocal(18, var5);
         var5 = null;
         var1.setline(350);
         if (var1.getlocal(18).__nonzero__()) {
            var1.setline(351);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(353);
               var5 = var1.getlocal(18).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)).__getattr__("lower").__call__(var2);
               var10000 = var5._eq(PyString.fromInterned("us-ascii"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(354);
                  var5 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(18).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getglobal("CHARSET"), var1.getlocal(18).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3))}));
                  var1.setlocal(15, var5);
                  var5 = null;
               }
            } else {
               var1.setline(359);
               var5 = PyString.fromInterned("%sus-ascii%s")._mod(var1.getlocal(18).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(3)));
               var1.setlocal(15, var5);
               var5 = null;
            }
         }

         var1.setline(360);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("cte").__getattr__("match").__call__(var2, var1.getlocal(15));
         }

         if (var10000.__nonzero__()) {
            var1.setline(361);
            PyString var9 = PyString.fromInterned("Content-Transfer-Encoding: ");
            var1.setlocal(15, var9);
            var5 = null;
            var1.setline(362);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(363);
               var5 = var1.getlocal(15)._add(PyString.fromInterned("base64\n"));
               var1.setlocal(15, var5);
               var5 = null;
            } else {
               var1.setline(364);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(365);
                  var5 = var1.getlocal(15)._add(PyString.fromInterned("quoted-printable\n"));
                  var1.setlocal(15, var5);
                  var5 = null;
               } else {
                  var1.setline(367);
                  var5 = var1.getlocal(15)._add(PyString.fromInterned("7bit\n"));
                  var1.setlocal(15, var5);
                  var5 = null;
               }
            }
         }

         var1.setline(368);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(15));
      }
   }

   public PyObject mimify$14(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyString.fromInterned("Convert 8bit parts of a MIME mail message to quoted-printable.");
      var1.setline(417);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(418);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(419);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(420);
            var3 = imp.importOne("os", var1, -1);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(421);
            var3 = var1.getlocal(3).__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(422);
            var1.getlocal(3).__getattr__("rename").__call__(var2, var1.getlocal(0), var1.getlocal(3).__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), PyString.fromInterned(",")._add(var1.getlocal(5))));
         }
      } else {
         var1.setline(424);
         var3 = var1.getlocal(0);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(425);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(426);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(428);
         var3 = var1.getlocal(1);
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(429);
      var3 = var1.getglobal("File").__call__(var2, var1.getlocal(2), var1.getglobal("None"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(430);
      var1.getglobal("mimify_part").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0));
      var1.setline(431);
      var1.getlocal(6).__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public mimify$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      File$1 = Py.newCode(0, var2, var1, "File", 47, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "boundary"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      readline$3 = Py.newCode(1, var2, var1, "readline", 56, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HeaderFile$4 = Py.newCode(0, var2, var1, "HeaderFile", 71, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 72, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      readline$6 = Py.newCode(1, var2, var1, "readline", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "newline", "pos", "res"};
      mime_decode$7 = Py.newCode(1, var2, var1, "mime_decode", 94, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "newline", "pos", "res", "match"};
      mime_decode_header$8 = Py.newCode(1, var2, var1, "mime_decode_header", 107, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ifile", "ofile", "decode_base64", "multipart", "quoted_printable", "is_base64", "is_repl", "prefix", "hfile", "line", "pref", "mp_res", "nifile", "newline", "base64"};
      unmimify_part$9 = Py.newCode(3, var2, var1, "unmimify_part", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"infile", "outfile", "decode_base64", "ifile", "os", "d", "f", "ofile", "nifile"};
      unmimify$10 = Py.newCode(3, var2, var1, "unmimify", 207, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "header", "reg", "newline", "pos", "res", "i"};
      mime_encode$11 = Py.newCode(2, var2, var1, "mime_encode", 228, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "newline", "pos", "res"};
      mime_encode_header$12 = Py.newCode(1, var2, var1, "mime_encode_header", 262, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ifile", "ofile", "is_mime", "has_cte", "is_qp", "is_base64", "multipart", "must_quote_body", "must_quote_header", "has_iso_chars", "header", "header_end", "message", "message_end", "hfile", "line", "mp_res", "newline", "chrset_res", "nifile"};
      mimify_part$13 = Py.newCode(3, var2, var1, "mimify_part", 280, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"infile", "outfile", "ifile", "os", "d", "f", "ofile", "nifile"};
      mimify$14 = Py.newCode(2, var2, var1, "mimify", 415, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mimify$py("mimify$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mimify$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.File$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.readline$3(var2, var3);
         case 4:
            return this.HeaderFile$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.readline$6(var2, var3);
         case 7:
            return this.mime_decode$7(var2, var3);
         case 8:
            return this.mime_decode_header$8(var2, var3);
         case 9:
            return this.unmimify_part$9(var2, var3);
         case 10:
            return this.unmimify$10(var2, var3);
         case 11:
            return this.mime_encode$11(var2, var3);
         case 12:
            return this.mime_encode_header$12(var2, var3);
         case 13:
            return this.mimify_part$13(var2, var3);
         case 14:
            return this.mimify$14(var2, var3);
         default:
            return null;
      }
   }
}

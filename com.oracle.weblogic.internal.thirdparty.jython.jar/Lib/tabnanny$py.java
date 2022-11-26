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
@Filename("tabnanny.py")
public class tabnanny$py extends PyFunctionTable implements PyRunnable {
   static tabnanny$py self;
   static final PyCode f$0;
   static final PyCode errprint$1;
   static final PyCode main$2;
   static final PyCode NannyNag$3;
   static final PyCode __init__$4;
   static final PyCode get_lineno$5;
   static final PyCode get_msg$6;
   static final PyCode get_line$7;
   static final PyCode check$8;
   static final PyCode Whitespace$9;
   static final PyCode __init__$10;
   static final PyCode longest_run_of_spaces$11;
   static final PyCode indent_level$12;
   static final PyCode equal$13;
   static final PyCode not_equal_witness$14;
   static final PyCode less$15;
   static final PyCode not_less_witness$16;
   static final PyCode format_witnesses$17;
   static final PyCode f$18;
   static final PyCode process_tokens$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("The Tab Nanny despises ambiguous indentation.  She knows no mercy.\n\ntabnanny -- Detection of ambiguous indentation\n\nFor the time being this module is intended to be called as a script.\nHowever it is possible to import it into an IDE and use the function\ncheck() described below.\n\nWarning: The API provided by this module is likely to change in future\nreleases; such changes may not be backward compatible.\n"));
      var1.setline(13);
      PyString.fromInterned("The Tab Nanny despises ambiguous indentation.  She knows no mercy.\n\ntabnanny -- Detection of ambiguous indentation\n\nFor the time being this module is intended to be called as a script.\nHowever it is possible to import it into an IDE and use the function\ncheck() described below.\n\nWarning: The API provided by this module is likely to change in future\nreleases; such changes may not be backward compatible.\n");
      var1.setline(21);
      PyString var3 = PyString.fromInterned("6");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(23);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(24);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(25);
      var5 = imp.importOne("getopt", var1, -1);
      var1.setlocal("getopt", var5);
      var3 = null;
      var1.setline(26);
      var5 = imp.importOne("tokenize", var1, -1);
      var1.setlocal("tokenize", var5);
      var3 = null;
      var1.setline(27);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("tokenize"), (PyObject)PyString.fromInterned("NL")).__not__().__nonzero__()) {
         var1.setline(28);
         throw Py.makeException(var1.getname("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tokenize.NL doesn't exist -- tokenize module too old")));
      } else {
         var1.setline(30);
         PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("check"), PyString.fromInterned("NannyNag"), PyString.fromInterned("process_tokens")});
         var1.setlocal("__all__", var6);
         var3 = null;
         var1.setline(32);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal("verbose", var7);
         var3 = null;
         var1.setline(33);
         var7 = Py.newInteger(0);
         var1.setlocal("filename_only", var7);
         var3 = null;
         var1.setline(35);
         PyObject[] var8 = Py.EmptyObjects;
         PyFunction var9 = new PyFunction(var1.f_globals, var8, errprint$1, (PyObject)null);
         var1.setlocal("errprint", var9);
         var3 = null;
         var1.setline(42);
         var8 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var8, main$2, (PyObject)null);
         var1.setlocal("main", var9);
         var3 = null;
         var1.setline(60);
         var8 = new PyObject[]{var1.getname("Exception")};
         PyObject var4 = Py.makeClass("NannyNag", var8, NannyNag$3);
         var1.setlocal("NannyNag", var4);
         var4 = null;
         Arrays.fill(var8, (Object)null);
         var1.setline(74);
         var8 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var8, check$8, PyString.fromInterned("check(file_or_dir)\n\n    If file_or_dir is a directory and not a symbolic link, then recursively\n    descend the directory tree named by file_or_dir, checking all .py files\n    along the way. If file_or_dir is an ordinary Python source file, it is\n    checked for whitespace related problems. The diagnostic messages are\n    written to standard output using the print statement.\n    "));
         var1.setlocal("check", var9);
         var3 = null;
         var1.setline(132);
         var8 = Py.EmptyObjects;
         var4 = Py.makeClass("Whitespace", var8, Whitespace$9);
         var1.setlocal("Whitespace", var4);
         var4 = null;
         Arrays.fill(var8, (Object)null);
         var1.setline(266);
         var8 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var8, format_witnesses$17, (PyObject)null);
         var1.setlocal("format_witnesses", var9);
         var3 = null;
         var1.setline(273);
         var8 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var8, process_tokens$19, (PyObject)null);
         var1.setlocal("process_tokens", var9);
         var3 = null;
         var1.setline(328);
         var5 = var1.getname("__name__");
         PyObject var10000 = var5._eq(PyString.fromInterned("__main__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(329);
            var1.getname("main").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject errprint$1(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(37);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(37);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(40);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(38);
         var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(1)._add(var1.getglobal("str").__call__(var2, var1.getlocal(2))));
         var1.setline(39);
         PyString var5 = PyString.fromInterned(" ");
         var1.setlocal(1, var5);
         var5 = null;
      }
   }

   public PyObject main$2(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var5;
      PyObject var8;
      try {
         var1.setline(45);
         var8 = var1.getglobal("getopt").__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("qv"));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         var5 = var9[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var9[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("getopt").__getattr__("error"))) {
            var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(47);
            var1.getglobal("errprint").__call__(var2, var1.getlocal(2));
            var1.setline(48);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(49);
      var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(49);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(54);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(55);
               var1.getglobal("errprint").__call__((ThreadState)var2, PyString.fromInterned("Usage:"), (PyObject)var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("[-v] file_or_directory ..."));
               var1.setline(56);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(57);
               var8 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(57);
                  var4 = var8.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(58);
                  var1.getglobal("check").__call__(var2, var1.getlocal(5));
               }
            }
         }

         PyObject[] var10 = Py.unpackSequence(var4, 2);
         PyObject var6 = var10[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(50);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._eq(PyString.fromInterned("-q"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(51);
            var5 = var1.getglobal("filename_only")._add(Py.newInteger(1));
            var1.setglobal("filename_only", var5);
            var5 = null;
         }

         var1.setline(52);
         var5 = var1.getlocal(3);
         var10000 = var5._eq(PyString.fromInterned("-v"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(53);
            var5 = var1.getglobal("verbose")._add(Py.newInteger(1));
            var1.setglobal("verbose", var5);
            var5 = null;
         }
      }
   }

   public PyObject NannyNag$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised by tokeneater() if detecting an ambiguous indent.\n    Captured and handled in check().\n    "));
      var1.setline(64);
      PyString.fromInterned("\n    Raised by tokeneater() if detecting an ambiguous indent.\n    Captured and handled in check().\n    ");
      var1.setline(65);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_lineno$5, (PyObject)null);
      var1.setlocal("get_lineno", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_msg$6, (PyObject)null);
      var1.setlocal("get_msg", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_line$7, (PyObject)null);
      var1.setlocal("get_line", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("lineno", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("msg", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("line", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_lineno$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getlocal(0).__getattr__("lineno");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_msg$6(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_line$7(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(0).__getattr__("line");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject check$8(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("check(file_or_dir)\n\n    If file_or_dir is a directory and not a symbolic link, then recursively\n    descend the directory tree named by file_or_dir, checking all .py files\n    along the way. If file_or_dir is an ordinary Python source file, it is\n    checked for whitespace related problems. The diagnostic messages are\n    written to standard output using the print statement.\n    ");
      var1.setline(84);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(0)).__not__();
      }

      PyException var3;
      PyObject var4;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(85);
         if (var1.getglobal("verbose").__nonzero__()) {
            var1.setline(86);
            Py.println(PyString.fromInterned("%r: listing directory")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
         }

         var1.setline(87);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var8);
         var3 = null;
         var1.setline(88);
         var8 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(88);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(94);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(89);
            PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(90);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(3));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(3)).__not__();
            }

            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null));
               var10000 = var5._eq(PyString.fromInterned(".py"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(93);
               var1.getglobal("check").__call__(var2, var1.getlocal(3));
            }
         }
      } else {
         try {
            var1.setline(97);
            var8 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var8);
            var3 = null;
         } catch (Throwable var6) {
            var3 = Py.setException(var6, var1);
            if (var3.match(var1.getglobal("IOError"))) {
               var4 = var3.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(99);
               var1.getglobal("errprint").__call__(var2, PyString.fromInterned("%r: I/O Error: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
               var1.setline(100);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var3;
         }

         var1.setline(102);
         var8 = var1.getglobal("verbose");
         var10000 = var8._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(103);
            Py.println(PyString.fromInterned("checking %r ...")._mod(var1.getlocal(0)));
         }

         try {
            var1.setline(106);
            var1.getglobal("process_tokens").__call__(var2, var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(4).__getattr__("readline")));
         } catch (Throwable var7) {
            var3 = Py.setException(var7, var1);
            if (var3.match(var1.getglobal("tokenize").__getattr__("TokenError"))) {
               var4 = var3.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(109);
               var1.getglobal("errprint").__call__(var2, PyString.fromInterned("%r: Token Error: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
               var1.setline(110);
               var1.f_lasti = -1;
               return Py.None;
            }

            if (var3.match(var1.getglobal("IndentationError"))) {
               var4 = var3.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(113);
               var1.getglobal("errprint").__call__(var2, PyString.fromInterned("%r: Indentation Error: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
               var1.setline(114);
               var1.f_lasti = -1;
               return Py.None;
            }

            if (var3.match(var1.getglobal("NannyNag"))) {
               var4 = var3.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(117);
               var4 = var1.getlocal(6).__getattr__("get_lineno").__call__(var2);
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(118);
               var4 = var1.getlocal(6).__getattr__("get_line").__call__(var2);
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(119);
               if (var1.getglobal("verbose").__nonzero__()) {
                  var1.setline(120);
                  Py.println(PyString.fromInterned("%r: *** Line %d: trouble in tab city! ***")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(7)})));
                  var1.setline(121);
                  Py.println(PyString.fromInterned("offending line: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(8)})));
                  var1.setline(122);
                  Py.println(var1.getlocal(6).__getattr__("get_msg").__call__(var2));
               } else {
                  var1.setline(124);
                  PyString var9 = PyString.fromInterned(" ");
                  var10000 = var9._in(var1.getlocal(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(124);
                     var4 = PyString.fromInterned("\"")._add(var1.getlocal(0))._add(PyString.fromInterned("\""));
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(125);
                  if (var1.getglobal("filename_only").__nonzero__()) {
                     var1.setline(125);
                     Py.println(var1.getlocal(0));
                  } else {
                     var1.setline(126);
                     Py.printComma(var1.getlocal(0));
                     Py.printComma(var1.getlocal(7));
                     Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(8)));
                  }
               }

               var1.setline(127);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var3;
         }

         var1.setline(129);
         if (var1.getglobal("verbose").__nonzero__()) {
            var1.setline(130);
            Py.println(PyString.fromInterned("%r: Clean bill of health.")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject Whitespace$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(134);
      PyString var3 = PyString.fromInterned(" \t");
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal("S", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("T", var5);
      var5 = null;
      var3 = null;
      var1.setline(155);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(180);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, longest_run_of_spaces$11, (PyObject)null);
      var1.setlocal("longest_run_of_spaces", var7);
      var3 = null;
      var1.setline(184);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, indent_level$12, (PyObject)null);
      var1.setlocal("indent_level", var7);
      var3 = null;
      var1.setline(207);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, equal$13, (PyObject)null);
      var1.setlocal("equal", var7);
      var3 = null;
      var1.setline(214);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, not_equal_witness$14, (PyObject)null);
      var1.setlocal("not_equal_witness", var7);
      var3 = null;
      var1.setline(238);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, less$15, (PyObject)null);
      var1.setlocal("less", var7);
      var3 = null;
      var1.setline(255);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, not_less_witness$16, (PyObject)null);
      var1.setlocal("not_less_witness", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("raw", var3);
      var3 = null;
      var1.setline(157);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("Whitespace").__getattr__("S"), var1.getglobal("Whitespace").__getattr__("T")});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(158);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(159);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(5, var9);
      var1.setlocal(6, var9);
      var1.setlocal(7, var9);
      var1.setline(160);
      var3 = var1.getlocal(0).__getattr__("raw").__iter__();

      PyObject var10000;
      while(true) {
         var1.setline(160);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            break;
         }

         var1.setlocal(8, var8);
         var1.setline(161);
         var5 = var1.getlocal(8);
         var10000 = var5._eq(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(162);
            var5 = var1.getlocal(6)._add(Py.newInteger(1));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(163);
            var5 = var1.getlocal(5)._add(Py.newInteger(1));
            var1.setlocal(5, var5);
            var5 = null;
         } else {
            var1.setline(164);
            var5 = var1.getlocal(8);
            var10000 = var5._eq(var1.getlocal(3));
            var5 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(165);
            var5 = var1.getlocal(6)._add(Py.newInteger(1));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(166);
            var5 = var1.getlocal(7)._add(Py.newInteger(1));
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(167);
            var5 = var1.getlocal(5);
            var10000 = var5._ge(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(168);
               var5 = var1.getlocal(4)._add((new PyList(new PyObject[]{Py.newInteger(0)}))._mul(var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(4)))._add(Py.newInteger(1))));
               var1.setlocal(4, var5);
               var5 = null;
            }

            var1.setline(169);
            var5 = var1.getlocal(4).__getitem__(var1.getlocal(5))._add(Py.newInteger(1));
            var1.getlocal(4).__setitem__(var1.getlocal(5), var5);
            var5 = null;
            var1.setline(170);
            PyInteger var10 = Py.newInteger(0);
            var1.setlocal(5, var10);
            var5 = null;
         }
      }

      var1.setline(173);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("n", var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("nt", var3);
      var3 = null;
      var1.setline(175);
      var6 = new PyTuple(new PyObject[]{var1.getglobal("tuple").__call__(var2, var1.getlocal(4)), var1.getlocal(5)});
      var1.getlocal(0).__setattr__((String)"norm", var6);
      var3 = null;
      var1.setline(176);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
      var10000 = var3._le(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("is_simple", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject longest_run_of_spaces$11(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(0).__getattr__("norm");
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("max").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject indent_level$12(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getlocal(0).__getattr__("norm");
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(200);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(201);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2))).__iter__();

      while(true) {
         var1.setline(201);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(203);
            var3 = var1.getlocal(3)._add(var1.getlocal(1)._mul(var1.getlocal(4)._add(var1.getlocal(0).__getattr__("nt"))));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var7);
         var1.setline(202);
         var5 = var1.getlocal(4)._add(var1.getlocal(5)._div(var1.getlocal(1))._mul(var1.getlocal(2).__getitem__(var1.getlocal(5))));
         var1.setlocal(4, var5);
         var5 = null;
      }
   }

   public PyObject equal$13(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getlocal(0).__getattr__("norm");
      PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("norm"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject not_equal_witness$14(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("longest_run_of_spaces").__call__(var2), var1.getlocal(1).__getattr__("longest_run_of_spaces").__call__(var2))._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(217);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(218);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(2)._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(218);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(223);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(219);
         PyObject var5 = var1.getlocal(0).__getattr__("indent_level").__call__(var2, var1.getlocal(4));
         PyObject var10000 = var5._ne(var1.getlocal(1).__getattr__("indent_level").__call__(var2, var1.getlocal(4)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(220);
            var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("indent_level").__call__(var2, var1.getlocal(4)), var1.getlocal(1).__getattr__("indent_level").__call__(var2, var1.getlocal(4))})));
         }
      }
   }

   public PyObject less$15(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getlocal(0).__getattr__("n");
      PyObject var10000 = var3._ge(var1.getlocal(1).__getattr__("n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(241);
         var10000 = var1.getlocal(0).__getattr__("is_simple");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("is_simple");
         }

         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(242);
            var4 = var1.getlocal(0).__getattr__("nt");
            var10000 = var4._le(var1.getlocal(1).__getattr__("nt"));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(243);
            var4 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("longest_run_of_spaces").__call__(var2), var1.getlocal(1).__getattr__("longest_run_of_spaces").__call__(var2))._add(Py.newInteger(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(246);
            var4 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(2)._add(Py.newInteger(1))).__iter__();

            do {
               var1.setline(246);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(249);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(247);
               PyObject var6 = var1.getlocal(0).__getattr__("indent_level").__call__(var2, var1.getlocal(3));
               var10000 = var6._ge(var1.getlocal(1).__getattr__("indent_level").__call__(var2, var1.getlocal(3)));
               var6 = null;
            } while(!var10000.__nonzero__());

            var1.setline(248);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject not_less_witness$16(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyObject var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("longest_run_of_spaces").__call__(var2), var1.getlocal(1).__getattr__("longest_run_of_spaces").__call__(var2))._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(258);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(259);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(2)._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(259);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(264);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(260);
         PyObject var5 = var1.getlocal(0).__getattr__("indent_level").__call__(var2, var1.getlocal(4));
         PyObject var10000 = var5._ge(var1.getlocal(1).__getattr__("indent_level").__call__(var2, var1.getlocal(4)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(261);
            var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("indent_level").__call__(var2, var1.getlocal(4)), var1.getlocal(1).__getattr__("indent_level").__call__(var2, var1.getlocal(4))})));
         }
      }
   }

   public PyObject format_witnesses$17(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(267);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$18)), (PyObject)var1.getlocal(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(268);
      PyString var5 = PyString.fromInterned("at tab size");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(269);
      var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var4._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(270);
         var4 = var1.getlocal(2)._add(PyString.fromInterned("s"));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(271);
      var4 = var1.getlocal(2)._add(PyString.fromInterned(" "))._add(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject process_tokens$19(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = var1.getglobal("tokenize").__getattr__("INDENT");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("tokenize").__getattr__("DEDENT");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(276);
      var3 = var1.getglobal("tokenize").__getattr__("NEWLINE");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(277);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getglobal("tokenize").__getattr__("COMMENT"), var1.getglobal("tokenize").__getattr__("NL")});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(278);
      PyList var9 = new PyList(new PyObject[]{var1.getglobal("Whitespace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))});
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(279);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(281);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(281);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 5);
         PyObject var6 = var5[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var5[4];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(282);
         PyObject var8 = var1.getlocal(7);
         PyObject var10000 = var8._eq(var1.getlocal(3));
         var5 = null;
         PyInteger var10;
         if (var10000.__nonzero__()) {
            var1.setline(288);
            var10 = Py.newInteger(1);
            var1.setlocal(6, var10);
            var5 = null;
         } else {
            var1.setline(290);
            var8 = var1.getlocal(7);
            var10000 = var8._eq(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(291);
               var10 = Py.newInteger(0);
               var1.setlocal(6, var10);
               var5 = null;
               var1.setline(292);
               var8 = var1.getglobal("Whitespace").__call__(var2, var1.getlocal(8));
               var1.setlocal(12, var8);
               var5 = null;
               var1.setline(293);
               if (var1.getlocal(5).__getitem__(Py.newInteger(-1)).__getattr__("less").__call__(var2, var1.getlocal(12)).__not__().__nonzero__()) {
                  var1.setline(294);
                  var8 = var1.getlocal(5).__getitem__(Py.newInteger(-1)).__getattr__("not_less_witness").__call__(var2, var1.getlocal(12));
                  var1.setlocal(13, var8);
                  var5 = null;
                  var1.setline(295);
                  var8 = PyString.fromInterned("indent not greater e.g. ")._add(var1.getglobal("format_witnesses").__call__(var2, var1.getlocal(13)));
                  var1.setlocal(14, var8);
                  var5 = null;
                  var1.setline(296);
                  throw Py.makeException(var1.getglobal("NannyNag").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)), var1.getlocal(14), var1.getlocal(11)));
               }

               var1.setline(297);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(12));
            } else {
               var1.setline(299);
               var8 = var1.getlocal(7);
               var10000 = var8._eq(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(309);
                  var10 = Py.newInteger(1);
                  var1.setlocal(6, var10);
                  var5 = null;
                  var1.setline(311);
                  var1.getlocal(5).__delitem__((PyObject)Py.newInteger(-1));
               } else {
                  var1.setline(313);
                  var10000 = var1.getlocal(6);
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(7);
                     var10000 = var8._notin(var1.getlocal(4));
                     var5 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(320);
                     var10 = Py.newInteger(0);
                     var1.setlocal(6, var10);
                     var5 = null;
                     var1.setline(321);
                     var8 = var1.getglobal("Whitespace").__call__(var2, var1.getlocal(11));
                     var1.setlocal(12, var8);
                     var5 = null;
                     var1.setline(322);
                     if (var1.getlocal(5).__getitem__(Py.newInteger(-1)).__getattr__("equal").__call__(var2, var1.getlocal(12)).__not__().__nonzero__()) {
                        var1.setline(323);
                        var8 = var1.getlocal(5).__getitem__(Py.newInteger(-1)).__getattr__("not_equal_witness").__call__(var2, var1.getlocal(12));
                        var1.setlocal(13, var8);
                        var5 = null;
                        var1.setline(324);
                        var8 = PyString.fromInterned("indent not equal e.g. ")._add(var1.getglobal("format_witnesses").__call__(var2, var1.getlocal(13)));
                        var1.setlocal(14, var8);
                        var5 = null;
                        var1.setline(325);
                        throw Py.makeException(var1.getglobal("NannyNag").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)), var1.getlocal(14), var1.getlocal(11)));
                     }
                  }
               }
            }
         }
      }
   }

   public tabnanny$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args", "sep", "arg"};
      errprint$1 = Py.newCode(1, var2, var1, "errprint", 35, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opts", "args", "msg", "o", "a", "arg"};
      main$2 = Py.newCode(0, var2, var1, "main", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NannyNag$3 = Py.newCode(0, var2, var1, "NannyNag", 60, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lineno", "msg", "line"};
      __init__$4 = Py.newCode(4, var2, var1, "__init__", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_lineno$5 = Py.newCode(1, var2, var1, "get_lineno", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_msg$6 = Py.newCode(1, var2, var1, "get_msg", 69, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_line$7 = Py.newCode(1, var2, var1, "get_line", 71, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "names", "name", "fullname", "f", "msg", "nag", "badline", "line"};
      check$8 = Py.newCode(1, var2, var1, "check", 74, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Whitespace$9 = Py.newCode(0, var2, var1, "Whitespace", 132, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ws", "S", "T", "count", "b", "n", "nt", "ch"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 155, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count", "trailing"};
      longest_run_of_spaces$11 = Py.newCode(1, var2, var1, "longest_run_of_spaces", 180, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tabsize", "count", "trailing", "il", "i"};
      indent_level$12 = Py.newCode(2, var2, var1, "indent_level", 184, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      equal$13 = Py.newCode(2, var2, var1, "equal", 207, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "n", "a", "ts"};
      not_equal_witness$14 = Py.newCode(2, var2, var1, "not_equal_witness", 214, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "n", "ts"};
      less$15 = Py.newCode(2, var2, var1, "less", 238, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "n", "a", "ts"};
      not_less_witness$16 = Py.newCode(2, var2, var1, "not_less_witness", 255, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"w", "firsts", "prefix"};
      format_witnesses$17 = Py.newCode(1, var2, var1, "format_witnesses", 266, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tup"};
      f$18 = Py.newCode(1, var2, var1, "<lambda>", 267, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tokens", "INDENT", "DEDENT", "NEWLINE", "JUNK", "indents", "check_equal", "type", "token", "start", "end", "line", "thisguy", "witness", "msg"};
      process_tokens$19 = Py.newCode(1, var2, var1, "process_tokens", 273, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tabnanny$py("tabnanny$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tabnanny$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.errprint$1(var2, var3);
         case 2:
            return this.main$2(var2, var3);
         case 3:
            return this.NannyNag$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.get_lineno$5(var2, var3);
         case 6:
            return this.get_msg$6(var2, var3);
         case 7:
            return this.get_line$7(var2, var3);
         case 8:
            return this.check$8(var2, var3);
         case 9:
            return this.Whitespace$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.longest_run_of_spaces$11(var2, var3);
         case 12:
            return this.indent_level$12(var2, var3);
         case 13:
            return this.equal$13(var2, var3);
         case 14:
            return this.not_equal_witness$14(var2, var3);
         case 15:
            return this.less$15(var2, var3);
         case 16:
            return this.not_less_witness$16(var2, var3);
         case 17:
            return this.format_witnesses$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         case 19:
            return this.process_tokens$19(var2, var3);
         default:
            return null;
      }
   }
}

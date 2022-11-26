package distutils;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("distutils/text_file.py")
public class text_file$py extends PyFunctionTable implements PyRunnable {
   static text_file$py self;
   static final PyCode f$0;
   static final PyCode TextFile$1;
   static final PyCode __init__$2;
   static final PyCode open$3;
   static final PyCode close$4;
   static final PyCode gen_error$5;
   static final PyCode error$6;
   static final PyCode warn$7;
   static final PyCode readline$8;
   static final PyCode readlines$9;
   static final PyCode unreadline$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("text_file\n\nprovides the TextFile class, which gives an interface to text files\nthat (optionally) takes care of stripping comments, ignoring blank\nlines, and joining lines with backslashes."));
      var1.setline(5);
      PyString.fromInterned("text_file\n\nprovides the TextFile class, which gives an interface to text files\nthat (optionally) takes care of stripping comments, ignoring blank\nlines, and joining lines with backslashes.");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(12);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("TextFile", var6, TextFile$1);
      var1.setlocal("TextFile", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TextFile$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Provides a file-like object that takes care of all the things you\n       commonly want to do when processing a text file that has some\n       line-by-line syntax: strip comments (as long as \"#\" is your\n       comment character), skip blank lines, join adjacent lines by\n       escaping the newline (ie. backslash at end of line), strip\n       leading and/or trailing whitespace.  All of these are optional\n       and independently controllable.\n\n       Provides a 'warn()' method so you can generate warning messages that\n       report physical line number, even if the logical line in question\n       spans multiple physical lines.  Also provides 'unreadline()' for\n       implementing line-at-a-time lookahead.\n\n       Constructor is called as:\n\n           TextFile (filename=None, file=None, **options)\n\n       It bombs (RuntimeError) if both 'filename' and 'file' are None;\n       'filename' should be a string, and 'file' a file object (or\n       something that provides 'readline()' and 'close()' methods).  It is\n       recommended that you supply at least 'filename', so that TextFile\n       can include it in warning messages.  If 'file' is not supplied,\n       TextFile creates its own using the 'open()' builtin.\n\n       The options are all boolean, and affect the value returned by\n       'readline()':\n         strip_comments [default: true]\n           strip from \"#\" to end-of-line, as well as any whitespace\n           leading up to the \"#\" -- unless it is escaped by a backslash\n         lstrip_ws [default: false]\n           strip leading whitespace from each line before returning it\n         rstrip_ws [default: true]\n           strip trailing whitespace (including line terminator!) from\n           each line before returning it\n         skip_blanks [default: true}\n           skip lines that are empty *after* stripping comments and\n           whitespace.  (If both lstrip_ws and rstrip_ws are false,\n           then some lines may consist of solely whitespace: these will\n           *not* be skipped, even if 'skip_blanks' is true.)\n         join_lines [default: false]\n           if a backslash is the last non-newline character on a line\n           after stripping comments and whitespace, join the following line\n           to it to form one \"logical line\"; if N consecutive lines end\n           with a backslash, then N+1 physical lines will be joined to\n           form one logical line.\n         collapse_join [default: false]\n           strip leading whitespace from lines that are joined to their\n           predecessor; only matters if (join_lines and not lstrip_ws)\n\n       Note that since 'rstrip_ws' can strip the trailing newline, the\n       semantics of 'readline()' must differ from those of the builtin file\n       object's 'readline()' method!  In particular, 'readline()' returns\n       None for end-of-file: an empty string might just be a blank line (or\n       an all-whitespace line), if 'rstrip_ws' is true but 'skip_blanks' is\n       not."));
      var1.setline(68);
      PyString.fromInterned("Provides a file-like object that takes care of all the things you\n       commonly want to do when processing a text file that has some\n       line-by-line syntax: strip comments (as long as \"#\" is your\n       comment character), skip blank lines, join adjacent lines by\n       escaping the newline (ie. backslash at end of line), strip\n       leading and/or trailing whitespace.  All of these are optional\n       and independently controllable.\n\n       Provides a 'warn()' method so you can generate warning messages that\n       report physical line number, even if the logical line in question\n       spans multiple physical lines.  Also provides 'unreadline()' for\n       implementing line-at-a-time lookahead.\n\n       Constructor is called as:\n\n           TextFile (filename=None, file=None, **options)\n\n       It bombs (RuntimeError) if both 'filename' and 'file' are None;\n       'filename' should be a string, and 'file' a file object (or\n       something that provides 'readline()' and 'close()' methods).  It is\n       recommended that you supply at least 'filename', so that TextFile\n       can include it in warning messages.  If 'file' is not supplied,\n       TextFile creates its own using the 'open()' builtin.\n\n       The options are all boolean, and affect the value returned by\n       'readline()':\n         strip_comments [default: true]\n           strip from \"#\" to end-of-line, as well as any whitespace\n           leading up to the \"#\" -- unless it is escaped by a backslash\n         lstrip_ws [default: false]\n           strip leading whitespace from each line before returning it\n         rstrip_ws [default: true]\n           strip trailing whitespace (including line terminator!) from\n           each line before returning it\n         skip_blanks [default: true}\n           skip lines that are empty *after* stripping comments and\n           whitespace.  (If both lstrip_ws and rstrip_ws are false,\n           then some lines may consist of solely whitespace: these will\n           *not* be skipped, even if 'skip_blanks' is true.)\n         join_lines [default: false]\n           if a backslash is the last non-newline character on a line\n           after stripping comments and whitespace, join the following line\n           to it to form one \"logical line\"; if N consecutive lines end\n           with a backslash, then N+1 physical lines will be joined to\n           form one logical line.\n         collapse_join [default: false]\n           strip leading whitespace from lines that are joined to their\n           predecessor; only matters if (join_lines and not lstrip_ws)\n\n       Note that since 'rstrip_ws' can strip the trailing newline, the\n       semantics of 'readline()' must differ from those of the builtin file\n       object's 'readline()' method!  In particular, 'readline()' returns\n       None for end-of-file: an empty string might just be a blank line (or\n       an all-whitespace line), if 'rstrip_ws' is true but 'skip_blanks' is\n       not.");
      var1.setline(70);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("strip_comments"), Py.newInteger(1), PyString.fromInterned("skip_blanks"), Py.newInteger(1), PyString.fromInterned("lstrip_ws"), Py.newInteger(0), PyString.fromInterned("rstrip_ws"), Py.newInteger(1), PyString.fromInterned("join_lines"), Py.newInteger(0), PyString.fromInterned("collapse_join"), Py.newInteger(0)});
      var1.setlocal("default_options", var3);
      var3 = null;
      var1.setline(78);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("Construct a new TextFile object.  At least one of 'filename'\n           (a string) and 'file' (a file-like object) must be supplied.\n           They keyword argument options are described above and affect\n           the values returned by 'readline()'."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(115);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, open$3, PyString.fromInterned("Open a new file named 'filename'.  This overrides both the\n           'filename' and 'file' arguments to the constructor."));
      var1.setlocal("open", var5);
      var3 = null;
      var1.setline(124);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$4, PyString.fromInterned("Close the current file and forget everything we know about it\n           (filename, current line number)."));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(134);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, gen_error$5, (PyObject)null);
      var1.setlocal("gen_error", var5);
      var3 = null;
      var1.setline(147);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, error$6, (PyObject)null);
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(150);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, warn$7, PyString.fromInterned("Print (to stderr) a warning message tied to the current logical\n           line in the current file.  If the current logical line in the\n           file spans multiple physical lines, the warning refers to the\n           whole range, eg. \"lines 3-5\".  If 'line' supplied, it overrides\n           the current line number; it may be a list or tuple to indicate a\n           range of physical lines, or an integer for a single physical\n           line."));
      var1.setlocal("warn", var5);
      var3 = null;
      var1.setline(161);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readline$8, PyString.fromInterned("Read and return a single logical line from the current file (or\n           from an internal buffer if lines have previously been \"unread\"\n           with 'unreadline()').  If the 'join_lines' option is true, this\n           may involve reading multiple physical lines concatenated into a\n           single string.  Updates the current line number, so calling\n           'warn()' after 'readline()' emits a warning about the physical\n           line(s) just read.  Returns None on end-of-file, since the empty\n           string can occur if 'rstrip_ws' is true but 'strip_blanks' is\n           not."));
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(287);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readlines$9, PyString.fromInterned("Read and return the list of all logical lines remaining in the\n           current file."));
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(299);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unreadline$10, PyString.fromInterned("Push 'line' (a string) onto an internal buffer that will be\n           checked by future 'readline()' calls.  Handy for implementing\n           a parser with line-at-a-time lookahead."));
      var1.setlocal("unreadline", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Construct a new TextFile object.  At least one of 'filename'\n           (a string) and 'file' (a file-like object) must be supplied.\n           They keyword argument options are described above and affect\n           the values returned by 'readline()'.");
      var1.setline(84);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(85);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("you must supply either or both of 'filename' and 'file'"));
      } else {
         var1.setline(90);
         var3 = var1.getlocal(0).__getattr__("default_options").__getattr__("keys").__call__(var2).__iter__();

         while(true) {
            var1.setline(90);
            PyObject var4 = var3.__iternext__();
            PyObject var5;
            if (var4 == null) {
               var1.setline(98);
               var3 = var1.getlocal(3).__getattr__("keys").__call__(var2).__iter__();

               do {
                  var1.setline(98);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(102);
                     var3 = var1.getlocal(2);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(103);
                        var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(1));
                     } else {
                        var1.setline(105);
                        var3 = var1.getlocal(1);
                        var1.getlocal(0).__setattr__("filename", var3);
                        var3 = null;
                        var1.setline(106);
                        var3 = var1.getlocal(2);
                        var1.getlocal(0).__setattr__("file", var3);
                        var3 = null;
                        var1.setline(107);
                        PyInteger var6 = Py.newInteger(0);
                        var1.getlocal(0).__setattr__((String)"current_line", var6);
                        var3 = null;
                     }

                     var1.setline(112);
                     PyList var7 = new PyList(Py.EmptyObjects);
                     var1.getlocal(0).__setattr__((String)"linebuf", var7);
                     var3 = null;
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(99);
                  var5 = var1.getlocal(4);
                  var10000 = var5._notin(var1.getlocal(0).__getattr__("default_options"));
                  var5 = null;
               } while(!var10000.__nonzero__());

               var1.setline(100);
               throw Py.makeException(var1.getglobal("KeyError"), PyString.fromInterned("invalid TextFile option '%s'")._mod(var1.getlocal(4)));
            }

            var1.setlocal(4, var4);
            var1.setline(91);
            var5 = var1.getlocal(4);
            var10000 = var5._in(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(92);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(3).__getitem__(var1.getlocal(4)));
            } else {
               var1.setline(95);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(0).__getattr__("default_options").__getitem__(var1.getlocal(4)));
            }
         }
      }
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Open a new file named 'filename'.  This overrides both the\n           'filename' and 'file' arguments to the constructor.");
      var1.setline(119);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("filename"), (PyObject)PyString.fromInterned("r"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(121);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"current_line", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$4(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Close the current file and forget everything we know about it\n           (filename, current line number).");
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("file");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("current_line", var3);
      var3 = null;
      var1.setline(131);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gen_error$5(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(136);
      PyObject var4 = var1.getlocal(2);
      PyObject var10000 = var4._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(137);
         var4 = var1.getlocal(0).__getattr__("current_line");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(138);
      var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("filename")._add(PyString.fromInterned(", ")));
      var1.setline(139);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
         var1.setline(140);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("lines %d-%d: ")._mod(var1.getglobal("tuple").__call__(var2, var1.getlocal(2))));
      } else {
         var1.setline(142);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("line %d: ")._mod(var1.getlocal(2)));
      }

      var1.setline(143);
      var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.setline(144);
      var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject error$6(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("error: ")._add(var1.getlocal(0).__getattr__("gen_error").__call__(var2, var1.getlocal(1), var1.getlocal(2))));
   }

   public PyObject warn$7(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyString.fromInterned("Print (to stderr) a warning message tied to the current logical\n           line in the current file.  If the current logical line in the\n           file spans multiple physical lines, the warning refers to the\n           whole range, eg. \"lines 3-5\".  If 'line' supplied, it overrides\n           the current line number; it may be a list or tuple to indicate a\n           range of physical lines, or an integer for a single physical\n           line.");
      var1.setline(158);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("warning: ")._add(var1.getlocal(0).__getattr__("gen_error").__call__(var2, var1.getlocal(1), var1.getlocal(2)))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$8(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Read and return a single logical line from the current file (or\n           from an internal buffer if lines have previously been \"unread\"\n           with 'unreadline()').  If the 'join_lines' option is true, this\n           may involve reading multiple physical lines concatenated into a\n           single string.  Updates the current line number, so calling\n           'warn()' after 'readline()' emits a warning about the physical\n           line(s) just read.  Returns None on end-of-file, since the empty\n           string can occur if 'rstrip_ws' is true but 'strip_blanks' is\n           not.");
      var1.setline(176);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("linebuf").__nonzero__()) {
         var1.setline(177);
         var3 = var1.getlocal(0).__getattr__("linebuf").__getitem__(Py.newInteger(-1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(178);
         var1.getlocal(0).__getattr__("linebuf").__delitem__((PyObject)Py.newInteger(-1));
         var1.setline(179);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(181);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var4 = null;

         while(true) {
            PyObject var10000;
            PyObject var5;
            do {
               while(true) {
                  var1.setline(183);
                  if (!Py.newInteger(1).__nonzero__()) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(185);
                  var5 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2);
                  var1.setlocal(1, var5);
                  var4 = null;
                  var1.setline(186);
                  var5 = var1.getlocal(1);
                  var10000 = var5._eq(PyString.fromInterned(""));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(186);
                     var5 = var1.getglobal("None");
                     var1.setlocal(1, var5);
                     var4 = null;
                  }

                  var1.setline(188);
                  var10000 = var1.getlocal(0).__getattr__("strip_comments");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1);
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(198);
                  var5 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
                  var1.setlocal(3, var5);
                  var4 = null;
                  var1.setline(199);
                  var5 = var1.getlocal(3);
                  var10000 = var5._eq(Py.newInteger(-1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(200);
                     break;
                  }

                  var1.setline(204);
                  var5 = var1.getlocal(3);
                  var10000 = var5._eq(Py.newInteger(0));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                     var10000 = var5._ne(PyString.fromInterned("\\"));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(211);
                     var5 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
                     Object var8 = var5._eq(PyString.fromInterned("\n"));
                     var4 = null;
                     if (((PyObject)var8).__nonzero__()) {
                        var8 = PyString.fromInterned("\n");
                     }

                     if (!((PyObject)var8).__nonzero__()) {
                        var8 = PyString.fromInterned("");
                     }

                     Object var6 = var8;
                     var1.setlocal(4, (PyObject)var6);
                     var4 = null;
                     var1.setline(212);
                     var5 = var1.getlocal(1).__getslice__(Py.newInteger(0), var1.getlocal(3), (PyObject)null)._add(var1.getlocal(4));
                     var1.setlocal(1, var5);
                     var4 = null;
                     var1.setline(221);
                     var5 = var1.getlocal(1).__getattr__("strip").__call__(var2);
                     var10000 = var5._eq(PyString.fromInterned(""));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        continue;
                     }
                     break;
                  }

                  var1.setline(225);
                  var5 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\#"), (PyObject)PyString.fromInterned("#"));
                  var1.setlocal(1, var5);
                  var4 = null;
                  break;
               }

               var1.setline(229);
               var10000 = var1.getlocal(0).__getattr__("join_lines");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(231);
                  var5 = var1.getlocal(1);
                  var10000 = var5._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(232);
                     var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("continuation line immediately precedes end-of-file"));
                     var1.setline(234);
                     var3 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(236);
                  if (var1.getlocal(0).__getattr__("collapse_join").__nonzero__()) {
                     var1.setline(237);
                     var5 = var1.getlocal(1).__getattr__("lstrip").__call__(var2);
                     var1.setlocal(1, var5);
                     var4 = null;
                  }

                  var1.setline(238);
                  var5 = var1.getlocal(2)._add(var1.getlocal(1));
                  var1.setlocal(1, var5);
                  var4 = null;
                  var1.setline(241);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("current_line"), var1.getglobal("list")).__nonzero__()) {
                     var1.setline(242);
                     var5 = var1.getlocal(0).__getattr__("current_line").__getitem__(Py.newInteger(1))._add(Py.newInteger(1));
                     var1.getlocal(0).__getattr__("current_line").__setitem__((PyObject)Py.newInteger(1), var5);
                     var4 = null;
                  } else {
                     var1.setline(244);
                     PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("current_line"), var1.getlocal(0).__getattr__("current_line")._add(Py.newInteger(1))});
                     var1.getlocal(0).__setattr__((String)"current_line", var7);
                     var4 = null;
                  }
               } else {
                  var1.setline(248);
                  var5 = var1.getlocal(1);
                  var10000 = var5._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(249);
                     var3 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(252);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("current_line"), var1.getglobal("list")).__nonzero__()) {
                     var1.setline(253);
                     var5 = var1.getlocal(0).__getattr__("current_line").__getitem__(Py.newInteger(1))._add(Py.newInteger(1));
                     var1.getlocal(0).__setattr__("current_line", var5);
                     var4 = null;
                  } else {
                     var1.setline(255);
                     var5 = var1.getlocal(0).__getattr__("current_line")._add(Py.newInteger(1));
                     var1.getlocal(0).__setattr__("current_line", var5);
                     var4 = null;
                  }
               }

               var1.setline(260);
               var10000 = var1.getlocal(0).__getattr__("lstrip_ws");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("rstrip_ws");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(261);
                  var5 = var1.getlocal(1).__getattr__("strip").__call__(var2);
                  var1.setlocal(1, var5);
                  var4 = null;
               } else {
                  var1.setline(262);
                  if (var1.getlocal(0).__getattr__("lstrip_ws").__nonzero__()) {
                     var1.setline(263);
                     var5 = var1.getlocal(1).__getattr__("lstrip").__call__(var2);
                     var1.setlocal(1, var5);
                     var4 = null;
                  } else {
                     var1.setline(264);
                     if (var1.getlocal(0).__getattr__("rstrip_ws").__nonzero__()) {
                        var1.setline(265);
                        var5 = var1.getlocal(1).__getattr__("rstrip").__call__(var2);
                        var1.setlocal(1, var5);
                        var4 = null;
                     }
                  }
               }

               var1.setline(269);
               var5 = var1.getlocal(1);
               var10000 = var5._eq(PyString.fromInterned(""));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var5 = var1.getlocal(1);
                  var10000 = var5._eq(PyString.fromInterned("\n"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("skip_blanks");
               }
            } while(var10000.__nonzero__());

            var1.setline(272);
            if (!var1.getlocal(0).__getattr__("join_lines").__nonzero__()) {
               break;
            }

            var1.setline(273);
            var5 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
            var10000 = var5._eq(PyString.fromInterned("\\"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(274);
               var5 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(2, var5);
               var4 = null;
            } else {
               var1.setline(277);
               var5 = var1.getlocal(1).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
               var10000 = var5._eq(PyString.fromInterned("\\\n"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(278);
               var5 = var1.getlocal(1).__getslice__(Py.newInteger(0), Py.newInteger(-2), (PyObject)null)._add(PyString.fromInterned("\n"));
               var1.setlocal(2, var5);
               var4 = null;
            }
         }

         var1.setline(282);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readlines$9(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyString.fromInterned("Read and return the list of all logical lines remaining in the\n           current file.");
      var1.setline(291);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(292);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(293);
         PyObject var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(294);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(295);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(296);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject unreadline$10(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyString.fromInterned("Push 'line' (a string) onto an internal buffer that will be\n           checked by future 'readline()' calls.  Handy for implementing\n           a parser with line-at-a-time lookahead.");
      var1.setline(304);
      var1.getlocal(0).__getattr__("linebuf").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public text_file$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TextFile$1 = Py.newCode(0, var2, var1, "TextFile", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "file", "options", "opt"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 78, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      open$3 = Py.newCode(2, var2, var1, "open", 115, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      close$4 = Py.newCode(1, var2, var1, "close", 124, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "line", "outmsg"};
      gen_error$5 = Py.newCode(3, var2, var1, "gen_error", 134, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "line"};
      error$6 = Py.newCode(3, var2, var1, "error", 147, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "line"};
      warn$7 = Py.newCode(3, var2, var1, "warn", 150, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "buildup_line", "pos", "eol"};
      readline$8 = Py.newCode(1, var2, var1, "readline", 161, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "line"};
      readlines$9 = Py.newCode(1, var2, var1, "readlines", 287, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      unreadline$10 = Py.newCode(2, var2, var1, "unreadline", 299, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new text_file$py("distutils/text_file$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(text_file$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TextFile$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.open$3(var2, var3);
         case 4:
            return this.close$4(var2, var3);
         case 5:
            return this.gen_error$5(var2, var3);
         case 6:
            return this.error$6(var2, var3);
         case 7:
            return this.warn$7(var2, var3);
         case 8:
            return this.readline$8(var2, var3);
         case 9:
            return this.readlines$9(var2, var3);
         case 10:
            return this.unreadline$10(var2, var3);
         default:
            return null;
      }
   }
}

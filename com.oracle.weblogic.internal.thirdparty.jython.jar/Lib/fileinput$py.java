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
@MTime(1498849384000L)
@Filename("fileinput.py")
public class fileinput$py extends PyFunctionTable implements PyRunnable {
   static fileinput$py self;
   static final PyCode f$0;
   static final PyCode input$1;
   static final PyCode close$2;
   static final PyCode nextfile$3;
   static final PyCode filename$4;
   static final PyCode lineno$5;
   static final PyCode filelineno$6;
   static final PyCode fileno$7;
   static final PyCode isfirstline$8;
   static final PyCode isstdin$9;
   static final PyCode FileInput$10;
   static final PyCode __init__$11;
   static final PyCode __del__$12;
   static final PyCode close$13;
   static final PyCode __iter__$14;
   static final PyCode next$15;
   static final PyCode __getitem__$16;
   static final PyCode nextfile$17;
   static final PyCode readline$18;
   static final PyCode filename$19;
   static final PyCode lineno$20;
   static final PyCode filelineno$21;
   static final PyCode fileno$22;
   static final PyCode isfirstline$23;
   static final PyCode isstdin$24;
   static final PyCode hook_compressed$25;
   static final PyCode hook_encoded$26;
   static final PyCode openhook$27;
   static final PyCode _test$28;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Helper class to quickly write a loop over all standard input files.\n\nTypical use is:\n\n    import fileinput\n    for line in fileinput.input():\n        process(line)\n\nThis iterates over the lines of all files listed in sys.argv[1:],\ndefaulting to sys.stdin if the list is empty.  If a filename is '-' it\nis also replaced by sys.stdin.  To specify an alternative list of\nfilenames, pass it as the argument to input().  A single file name is\nalso allowed.\n\nFunctions filename(), lineno() return the filename and cumulative line\nnumber of the line that has just been read; filelineno() returns its\nline number in the current file; isfirstline() returns true iff the\nline just read is the first line of its file; isstdin() returns true\niff the line was read from sys.stdin.  Function nextfile() closes the\ncurrent file so that the next iteration will read the first line from\nthe next file (if any); lines not read from the file will not count\ntowards the cumulative line count; the filename is not changed until\nafter the first line of the next file has been read.  Function close()\ncloses the sequence.\n\nBefore any lines have been read, filename() returns None and both line\nnumbers are zero; nextfile() has no effect.  After all lines have been\nread, filename() and the line number functions return the values\npertaining to the last line read; nextfile() has no effect.\n\nAll files are opened in text mode by default, you can override this by\nsetting the mode parameter to input() or FileInput.__init__().\nIf an I/O error occurs during opening or reading a file, the IOError\nexception is raised.\n\nIf sys.stdin is used more than once, the second and further use will\nreturn no lines, except perhaps for interactive use, or if it has been\nexplicitly reset (e.g. using sys.stdin.seek(0)).\n\nEmpty files are opened and immediately closed; the only time their\npresence in the list of filenames is noticeable at all is when the\nlast file opened is empty.\n\nIt is possible that the last line of a file doesn't end in a newline\ncharacter; otherwise lines are returned including the trailing\nnewline.\n\nClass FileInput is the implementation; its methods filename(),\nlineno(), fileline(), isfirstline(), isstdin(), nextfile() and close()\ncorrespond to the functions in the module.  In addition it has a\nreadline() method which returns the next input line, and a\n__getitem__() method which implements the sequence behavior.  The\nsequence must be accessed in strictly sequential order; sequence\naccess and readline() cannot be mixed.\n\nOptional in-place filtering: if the keyword argument inplace=1 is\npassed to input() or to the FileInput constructor, the file is moved\nto a backup file and standard output is directed to the input file.\nThis makes it possible to write a filter that rewrites its input file\nin place.  If the keyword argument backup=\".<some extension>\" is also\ngiven, it specifies the extension for the backup file, and the backup\nfile remains around; by default, the extension is \".bak\" and it is\ndeleted when the output file is closed.  In-place filtering is\ndisabled when standard input is read.  XXX The current implementation\ndoes not work for MS-DOS 8+3 filesystems.\n\nPerformance: this module is unfortunately one of the slower ways of\nprocessing large numbers of input lines.  Nevertheless, a significant\nspeed-up has been obtained by using readlines(bufsize) instead of\nreadline().  A new keyword argument, bufsize=N, is present on the\ninput() function and the FileInput() class to override the default\nbuffer size.\n\nXXX Possible additions:\n\n- optional getopt argument processing\n- isatty()\n- read(), read(size), even readlines()\n\n"));
      var1.setline(80);
      PyString.fromInterned("Helper class to quickly write a loop over all standard input files.\n\nTypical use is:\n\n    import fileinput\n    for line in fileinput.input():\n        process(line)\n\nThis iterates over the lines of all files listed in sys.argv[1:],\ndefaulting to sys.stdin if the list is empty.  If a filename is '-' it\nis also replaced by sys.stdin.  To specify an alternative list of\nfilenames, pass it as the argument to input().  A single file name is\nalso allowed.\n\nFunctions filename(), lineno() return the filename and cumulative line\nnumber of the line that has just been read; filelineno() returns its\nline number in the current file; isfirstline() returns true iff the\nline just read is the first line of its file; isstdin() returns true\niff the line was read from sys.stdin.  Function nextfile() closes the\ncurrent file so that the next iteration will read the first line from\nthe next file (if any); lines not read from the file will not count\ntowards the cumulative line count; the filename is not changed until\nafter the first line of the next file has been read.  Function close()\ncloses the sequence.\n\nBefore any lines have been read, filename() returns None and both line\nnumbers are zero; nextfile() has no effect.  After all lines have been\nread, filename() and the line number functions return the values\npertaining to the last line read; nextfile() has no effect.\n\nAll files are opened in text mode by default, you can override this by\nsetting the mode parameter to input() or FileInput.__init__().\nIf an I/O error occurs during opening or reading a file, the IOError\nexception is raised.\n\nIf sys.stdin is used more than once, the second and further use will\nreturn no lines, except perhaps for interactive use, or if it has been\nexplicitly reset (e.g. using sys.stdin.seek(0)).\n\nEmpty files are opened and immediately closed; the only time their\npresence in the list of filenames is noticeable at all is when the\nlast file opened is empty.\n\nIt is possible that the last line of a file doesn't end in a newline\ncharacter; otherwise lines are returned including the trailing\nnewline.\n\nClass FileInput is the implementation; its methods filename(),\nlineno(), fileline(), isfirstline(), isstdin(), nextfile() and close()\ncorrespond to the functions in the module.  In addition it has a\nreadline() method which returns the next input line, and a\n__getitem__() method which implements the sequence behavior.  The\nsequence must be accessed in strictly sequential order; sequence\naccess and readline() cannot be mixed.\n\nOptional in-place filtering: if the keyword argument inplace=1 is\npassed to input() or to the FileInput constructor, the file is moved\nto a backup file and standard output is directed to the input file.\nThis makes it possible to write a filter that rewrites its input file\nin place.  If the keyword argument backup=\".<some extension>\" is also\ngiven, it specifies the extension for the backup file, and the backup\nfile remains around; by default, the extension is \".bak\" and it is\ndeleted when the output file is closed.  In-place filtering is\ndisabled when standard input is read.  XXX The current implementation\ndoes not work for MS-DOS 8+3 filesystems.\n\nPerformance: this module is unfortunately one of the slower ways of\nprocessing large numbers of input lines.  Nevertheless, a significant\nspeed-up has been obtained by using readlines(bufsize) instead of\nreadline().  A new keyword argument, bufsize=N, is present on the\ninput() function and the FileInput() class to override the default\nbuffer size.\n\nXXX Possible additions:\n\n- optional getopt argument processing\n- isatty()\n- read(), read(size), even readlines()\n\n");
      var1.setline(82);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(84);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("input"), PyString.fromInterned("close"), PyString.fromInterned("nextfile"), PyString.fromInterned("filename"), PyString.fromInterned("lineno"), PyString.fromInterned("filelineno"), PyString.fromInterned("isfirstline"), PyString.fromInterned("isstdin"), PyString.fromInterned("FileInput")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(87);
      var3 = var1.getname("None");
      var1.setlocal("_state", var3);
      var3 = null;
      var1.setline(89);
      var3 = Py.newInteger(8)._mul(Py.newInteger(1024));
      var1.setlocal("DEFAULT_BUFSIZE", var3);
      var3 = null;
      var1.setline(91);
      PyObject[] var6 = new PyObject[]{var1.getname("None"), Py.newInteger(0), PyString.fromInterned(""), Py.newInteger(0), PyString.fromInterned("r"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, input$1, PyString.fromInterned("input([files[, inplace[, backup[, mode[, openhook]]]]])\n\n    Create an instance of the FileInput class. The instance will be used\n    as global state for the functions of this module, and is also returned\n    to use during iteration. The parameters to this function will be passed\n    along to the constructor of the FileInput class.\n    "));
      var1.setlocal("input", var7);
      var3 = null;
      var1.setline(106);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, close$2, PyString.fromInterned("Close the sequence."));
      var1.setlocal("close", var7);
      var3 = null;
      var1.setline(114);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, nextfile$3, PyString.fromInterned("\n    Close the current file so that the next iteration will read the first\n    line from the next file (if any); lines not read from the file will\n    not count towards the cumulative line count. The filename is not\n    changed until after the first line of the next file has been read.\n    Before the first line has been read, this function has no effect;\n    it cannot be used to skip the first file. After the last line of the\n    last file has been read, this function has no effect.\n    "));
      var1.setlocal("nextfile", var7);
      var3 = null;
      var1.setline(128);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, filename$4, PyString.fromInterned("\n    Return the name of the file currently being read.\n    Before the first line has been read, returns None.\n    "));
      var1.setlocal("filename", var7);
      var3 = null;
      var1.setline(137);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, lineno$5, PyString.fromInterned("\n    Return the cumulative line number of the line that has just been read.\n    Before the first line has been read, returns 0. After the last line\n    of the last file has been read, returns the line number of that line.\n    "));
      var1.setlocal("lineno", var7);
      var3 = null;
      var1.setline(147);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, filelineno$6, PyString.fromInterned("\n    Return the line number in the current file. Before the first line\n    has been read, returns 0. After the last line of the last file has\n    been read, returns the line number of that line within the file.\n    "));
      var1.setlocal("filelineno", var7);
      var3 = null;
      var1.setline(157);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, fileno$7, PyString.fromInterned("\n    Return the file number of the current file. When no file is currently\n    opened, returns -1.\n    "));
      var1.setlocal("fileno", var7);
      var3 = null;
      var1.setline(166);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isfirstline$8, PyString.fromInterned("\n    Returns true the line just read is the first line of its file,\n    otherwise returns false.\n    "));
      var1.setlocal("isfirstline", var7);
      var3 = null;
      var1.setline(175);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isstdin$9, PyString.fromInterned("\n    Returns true if the last line was read from sys.stdin,\n    otherwise returns false.\n    "));
      var1.setlocal("isstdin", var7);
      var3 = null;
      var1.setline(184);
      var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("FileInput", var6, FileInput$10);
      var1.setlocal("FileInput", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(380);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, hook_compressed$25, (PyObject)null);
      var1.setlocal("hook_compressed", var7);
      var3 = null;
      var1.setline(392);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, hook_encoded$26, (PyObject)null);
      var1.setlocal("hook_encoded", var7);
      var3 = null;
      var1.setline(399);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _test$28, (PyObject)null);
      var1.setlocal("_test", var7);
      var3 = null;
      var1.setline(414);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(415);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject input$1(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("input([files[, inplace[, backup[, mode[, openhook]]]]])\n\n    Create an instance of the FileInput class. The instance will be used\n    as global state for the functions of this module, and is also returned\n    to use during iteration. The parameters to this function will be passed\n    along to the constructor of the FileInput class.\n    ");
      var1.setline(101);
      PyObject var10000 = var1.getglobal("_state");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("_state").__getattr__("_file");
      }

      if (var10000.__nonzero__()) {
         var1.setline(102);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("input() already active"));
      } else {
         var1.setline(103);
         var10000 = var1.getglobal("FileInput");
         PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         PyObject var4 = var10000.__call__(var2, var3);
         var1.setglobal("_state", var4);
         var3 = null;
         var1.setline(104);
         var4 = var1.getglobal("_state");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject close$2(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Close the sequence.");
      var1.setline(109);
      PyObject var3 = var1.getglobal("_state");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getglobal("None");
      var1.setglobal("_state", var3);
      var3 = null;
      var1.setline(111);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(112);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject nextfile$3(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("\n    Close the current file so that the next iteration will read the first\n    line from the next file (if any); lines not read from the file will\n    not count towards the cumulative line count. The filename is not\n    changed until after the first line of the next file has been read.\n    Before the first line has been read, this function has no effect;\n    it cannot be used to skip the first file. After the last line of the\n    last file has been read, this function has no effect.\n    ");
      var1.setline(124);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(125);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(126);
         PyObject var3 = var1.getglobal("_state").__getattr__("nextfile").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject filename$4(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("\n    Return the name of the file currently being read.\n    Before the first line has been read, returns None.\n    ");
      var1.setline(133);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(134);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(135);
         PyObject var3 = var1.getglobal("_state").__getattr__("filename").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lineno$5(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyString.fromInterned("\n    Return the cumulative line number of the line that has just been read.\n    Before the first line has been read, returns 0. After the last line\n    of the last file has been read, returns the line number of that line.\n    ");
      var1.setline(143);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(144);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(145);
         PyObject var3 = var1.getglobal("_state").__getattr__("lineno").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject filelineno$6(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("\n    Return the line number in the current file. Before the first line\n    has been read, returns 0. After the last line of the last file has\n    been read, returns the line number of that line within the file.\n    ");
      var1.setline(153);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(154);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(155);
         PyObject var3 = var1.getglobal("_state").__getattr__("filelineno").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject fileno$7(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("\n    Return the file number of the current file. When no file is currently\n    opened, returns -1.\n    ");
      var1.setline(162);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(163);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(164);
         PyObject var3 = var1.getglobal("_state").__getattr__("fileno").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject isfirstline$8(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("\n    Returns true the line just read is the first line of its file,\n    otherwise returns false.\n    ");
      var1.setline(171);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(172);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(173);
         PyObject var3 = var1.getglobal("_state").__getattr__("isfirstline").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject isstdin$9(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyString.fromInterned("\n    Returns true if the last line was read from sys.stdin,\n    otherwise returns false.\n    ");
      var1.setline(180);
      if (var1.getglobal("_state").__not__().__nonzero__()) {
         var1.setline(181);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no active input()"));
      } else {
         var1.setline(182);
         PyObject var3 = var1.getglobal("_state").__getattr__("isstdin").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject FileInput$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("class FileInput([files[, inplace[, backup[, mode[, openhook]]]]])\n\n    Class FileInput is the implementation of the module; its methods\n    filename(), lineno(), fileline(), isfirstline(), isstdin(), fileno(),\n    nextfile() and close() correspond to the functions of the same name\n    in the module.\n    In addition it has a readline() method which returns the next\n    input line, and a __getitem__() method which implements the\n    sequence behavior. The sequence must be accessed in strictly\n    sequential order; random access and readline() cannot be mixed.\n    "));
      var1.setline(195);
      PyString.fromInterned("class FileInput([files[, inplace[, backup[, mode[, openhook]]]]])\n\n    Class FileInput is the implementation of the module; its methods\n    filename(), lineno(), fileline(), isfirstline(), isstdin(), fileno(),\n    nextfile() and close() correspond to the functions of the same name\n    in the module.\n    In addition it has a readline() method which returns the next\n    input line, and a __getitem__() method which implements the\n    sequence behavior. The sequence must be accessed in strictly\n    sequential order; random access and readline() cannot be mixed.\n    ");
      var1.setline(197);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), PyString.fromInterned(""), Py.newInteger(0), PyString.fromInterned("r"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$12, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$13, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$14, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$15, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(258);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$16, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, nextfile$17, (PyObject)null);
      var1.setlocal("nextfile", var4);
      var3 = null;
      var1.setline(292);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$18, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, filename$19, (PyObject)null);
      var1.setlocal("filename", var4);
      var3 = null;
      var1.setline(358);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lineno$20, (PyObject)null);
      var1.setlocal("lineno", var4);
      var3 = null;
      var1.setline(361);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, filelineno$21, (PyObject)null);
      var1.setlocal("filelineno", var4);
      var3 = null;
      var1.setline(364);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$22, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(373);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isfirstline$23, (PyObject)null);
      var1.setlocal("isfirstline", var4);
      var3 = null;
      var1.setline(376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isstdin$24, (PyObject)null);
      var1.setlocal("isstdin", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var10000;
      PyTuple var3;
      PyObject var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(200);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(202);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(203);
            var4 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var3 = null;
         }

         var1.setline(204);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(205);
            var3 = new PyTuple(new PyObject[]{PyString.fromInterned("-")});
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(207);
            var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var4);
            var3 = null;
         }
      }

      var1.setline(208);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_files", var4);
      var3 = null;
      var1.setline(209);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_inplace", var4);
      var3 = null;
      var1.setline(210);
      var4 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_backup", var4);
      var3 = null;
      var1.setline(211);
      var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("DEFAULT_BUFSIZE");
      }

      var4 = var10000;
      var1.getlocal(0).__setattr__("_bufsize", var4);
      var3 = null;
      var1.setline(212);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_savestdout", var4);
      var3 = null;
      var1.setline(213);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_output", var4);
      var3 = null;
      var1.setline(214);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_filename", var4);
      var3 = null;
      var1.setline(215);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_lineno", var5);
      var3 = null;
      var1.setline(216);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_filelineno", var5);
      var3 = null;
      var1.setline(217);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_file", var4);
      var3 = null;
      var1.setline(218);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_isstdin", var4);
      var3 = null;
      var1.setline(219);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_backupfilename", var4);
      var3 = null;
      var1.setline(220);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_buffer", var6);
      var3 = null;
      var1.setline(221);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_bufindex", var5);
      var3 = null;
      var1.setline(223);
      var4 = var1.getlocal(5);
      var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("rU"), PyString.fromInterned("U"), PyString.fromInterned("rb")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(224);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FileInput opening mode must be one of 'r', 'rU', 'U' and 'rb'")));
      } else {
         var1.setline(226);
         var4 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("_mode", var4);
         var3 = null;
         var1.setline(227);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(6);
         }

         if (var10000.__nonzero__()) {
            var1.setline(228);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FileInput cannot use an opening hook in inplace mode")));
         } else {
            var1.setline(229);
            var10000 = var1.getlocal(6);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("__call__")).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(230);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FileInput openhook must be callable")));
            } else {
               var1.setline(231);
               var4 = var1.getlocal(6);
               var1.getlocal(0).__setattr__("_openhook", var4);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject __del__$12(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$13(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      var1.getlocal(0).__getattr__("nextfile").__call__(var2);
      var1.setline(238);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_files", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$14(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$15(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var8;
      try {
         var1.setline(245);
         var8 = var1.getlocal(0).__getattr__("_buffer").__getitem__(var1.getlocal(0).__getattr__("_bufindex"));
         var1.setlocal(1, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("IndexError"))) {
            var1.setline(247);
            var1.setline(253);
            var8 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(1, var8);
            var3 = null;
            var1.setline(254);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(255);
               throw Py.makeException(var1.getglobal("StopIteration"));
            }

            var1.setline(256);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(249);
      PyObject var10000 = var1.getlocal(0);
      String var9 = "_bufindex";
      PyObject var5 = var10000;
      PyObject var6 = var5.__getattr__(var9);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var9, var6);
      var1.setline(250);
      var10000 = var1.getlocal(0);
      var9 = "_lineno";
      var5 = var10000;
      var6 = var5.__getattr__(var9);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var9, var6);
      var1.setline(251);
      var10000 = var1.getlocal(0);
      var9 = "_filelineno";
      var5 = var10000;
      var6 = var5.__getattr__(var9);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var9, var6);
      var1.setline(252);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __getitem__$16(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("_lineno"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(260);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("accessing lines out of order"));
      } else {
         try {
            var1.setline(262);
            var3 = var1.getlocal(0).__getattr__("next").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("StopIteration"))) {
               var1.setline(264);
               throw Py.makeException(var1.getglobal("IndexError"), PyString.fromInterned("end of input reached"));
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject nextfile$17(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyObject var3 = var1.getlocal(0).__getattr__("_savestdout");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(268);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_savestdout", var5);
      var3 = null;
      var1.setline(269);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(270);
         var3 = var1.getlocal(1);
         var1.getglobal("sys").__setattr__("stdout", var3);
         var3 = null;
      }

      var1.setline(272);
      var3 = var1.getlocal(0).__getattr__("_output");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(273);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_output", var5);
      var3 = null;
      var1.setline(274);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(275);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      var1.setline(277);
      var3 = var1.getlocal(0).__getattr__("_file");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(278);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_file", var5);
      var3 = null;
      var1.setline(279);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_isstdin").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(280);
         var1.getlocal(3).__getattr__("close").__call__(var2);
      }

      var1.setline(282);
      var3 = var1.getlocal(0).__getattr__("_backupfilename");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(283);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_backupfilename", var5);
      var3 = null;
      var1.setline(284);
      var10000 = var1.getlocal(4);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_backup").__not__();
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(285);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4));
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (!var6.match(var1.getglobal("OSError"))) {
               throw var6;
            }

            var1.setline(286);
         }
      }

      var1.setline(288);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_isstdin", var3);
      var3 = null;
      var1.setline(289);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_buffer", var7);
      var3 = null;
      var1.setline(290);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_bufindex", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$18(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var5;
      PyObject var11;
      PyObject var10000;
      try {
         var1.setline(294);
         var11 = var1.getlocal(0).__getattr__("_buffer").__getitem__(var1.getlocal(0).__getattr__("_bufindex"));
         var1.setlocal(1, var11);
         var3 = null;
      } catch (Throwable var10) {
         var3 = Py.setException(var10, var1);
         if (var3.match(var1.getglobal("IndexError"))) {
            var1.setline(296);
            var1.setline(302);
            PyInteger var14;
            if (var1.getlocal(0).__getattr__("_file").__not__().__nonzero__()) {
               var1.setline(303);
               if (var1.getlocal(0).__getattr__("_files").__not__().__nonzero__()) {
                  var1.setline(304);
                  PyString var12 = PyString.fromInterned("");
                  var1.f_lasti = -1;
                  return var12;
               }

               var1.setline(305);
               var11 = var1.getlocal(0).__getattr__("_files").__getitem__(Py.newInteger(0));
               var1.getlocal(0).__setattr__("_filename", var11);
               var3 = null;
               var1.setline(306);
               var11 = var1.getlocal(0).__getattr__("_files").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("_files", var11);
               var3 = null;
               var1.setline(307);
               var14 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"_filelineno", var14);
               var3 = null;
               var1.setline(308);
               var11 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_file", var11);
               var3 = null;
               var1.setline(309);
               var11 = var1.getglobal("False");
               var1.getlocal(0).__setattr__("_isstdin", var11);
               var3 = null;
               var1.setline(310);
               var14 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"_backupfilename", var14);
               var3 = null;
               var1.setline(311);
               var11 = var1.getlocal(0).__getattr__("_filename");
               var10000 = var11._eq(PyString.fromInterned("-"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(312);
                  PyString var16 = PyString.fromInterned("<stdin>");
                  var1.getlocal(0).__setattr__((String)"_filename", var16);
                  var3 = null;
                  var1.setline(313);
                  var11 = var1.getglobal("sys").__getattr__("stdin");
                  var1.getlocal(0).__setattr__("_file", var11);
                  var3 = null;
                  var1.setline(314);
                  var11 = var1.getglobal("True");
                  var1.getlocal(0).__setattr__("_isstdin", var11);
                  var3 = null;
               } else {
                  var1.setline(316);
                  if (!var1.getlocal(0).__getattr__("_inplace").__nonzero__()) {
                     var1.setline(344);
                     if (var1.getlocal(0).__getattr__("_openhook").__nonzero__()) {
                        var1.setline(345);
                        var11 = var1.getlocal(0).__getattr__("_openhook").__call__(var2, var1.getlocal(0).__getattr__("_filename"), var1.getlocal(0).__getattr__("_mode"));
                        var1.getlocal(0).__setattr__("_file", var11);
                        var3 = null;
                     } else {
                        var1.setline(347);
                        var11 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("_filename"), var1.getlocal(0).__getattr__("_mode"));
                        var1.getlocal(0).__setattr__("_file", var11);
                        var3 = null;
                     }
                  } else {
                     var1.setline(317);
                     var10000 = var1.getlocal(0).__getattr__("_filename");
                     PyObject var10001 = var1.getlocal(0).__getattr__("_backup");
                     if (!var10001.__nonzero__()) {
                        var10001 = var1.getglobal("os").__getattr__("extsep")._add(PyString.fromInterned("bak"));
                     }

                     var11 = var10000._add(var10001);
                     var1.getlocal(0).__setattr__("_backupfilename", var11);
                     var3 = null;

                     try {
                        var1.setline(319);
                        var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(0).__getattr__("_backupfilename"));
                     } catch (Throwable var9) {
                        var3 = Py.setException(var9, var1);
                        if (!var3.match(var1.getglobal("os").__getattr__("error"))) {
                           throw var3;
                        }

                        var1.setline(320);
                     }

                     var1.setline(322);
                     var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0).__getattr__("_filename"), var1.getlocal(0).__getattr__("_backupfilename"));
                     var1.setline(323);
                     var11 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("_backupfilename"), var1.getlocal(0).__getattr__("_mode"));
                     var1.getlocal(0).__setattr__("_file", var11);
                     var3 = null;

                     label93: {
                        try {
                           var1.setline(325);
                           var11 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(0).__getattr__("_file").__getattr__("fileno").__call__(var2)).__getattr__("st_mode");
                           var1.setlocal(2, var11);
                           var3 = null;
                        } catch (Throwable var8) {
                           var3 = Py.setException(var8, var1);
                           if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("OSError")}))) {
                              var1.setline(329);
                              var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_filename"), (PyObject)PyString.fromInterned("w"));
                              var1.getlocal(0).__setattr__("_output", var5);
                              var5 = null;
                              break label93;
                           }

                           throw var3;
                        }

                        var1.setline(331);
                        var5 = var1.getglobal("os").__getattr__("open").__call__(var2, var1.getlocal(0).__getattr__("_filename"), var1.getglobal("os").__getattr__("O_CREAT")._or(var1.getglobal("os").__getattr__("O_WRONLY"))._or(var1.getglobal("os").__getattr__("O_TRUNC")), var1.getlocal(2));
                        var1.setlocal(3, var5);
                        var5 = null;
                        var1.setline(334);
                        var5 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
                        var1.getlocal(0).__setattr__("_output", var5);
                        var5 = null;

                        try {
                           var1.setline(336);
                           if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("chmod")).__nonzero__()) {
                              var1.setline(337);
                              var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(0).__getattr__("_filename"), var1.getlocal(2));
                           }
                        } catch (Throwable var7) {
                           PyException var15 = Py.setException(var7, var1);
                           if (!var15.match(var1.getglobal("OSError"))) {
                              throw var15;
                           }

                           var1.setline(339);
                        }
                     }

                     var1.setline(340);
                     var11 = var1.getglobal("sys").__getattr__("stdout");
                     var1.getlocal(0).__setattr__("_savestdout", var11);
                     var3 = null;
                     var1.setline(341);
                     var11 = var1.getlocal(0).__getattr__("_output");
                     var1.getglobal("sys").__setattr__("stdout", var11);
                     var3 = null;
                  }
               }
            }

            var1.setline(348);
            var11 = var1.getlocal(0).__getattr__("_file").__getattr__("readlines").__call__(var2, var1.getlocal(0).__getattr__("_bufsize"));
            var1.getlocal(0).__setattr__("_buffer", var11);
            var3 = null;
            var1.setline(349);
            var14 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_bufindex", var14);
            var3 = null;
            var1.setline(350);
            if (var1.getlocal(0).__getattr__("_buffer").__not__().__nonzero__()) {
               var1.setline(351);
               var1.getlocal(0).__getattr__("nextfile").__call__(var2);
            }

            var1.setline(353);
            var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(298);
      var10000 = var1.getlocal(0);
      String var13 = "_bufindex";
      var5 = var10000;
      PyObject var6 = var5.__getattr__(var13);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var13, var6);
      var1.setline(299);
      var10000 = var1.getlocal(0);
      var13 = "_lineno";
      var5 = var10000;
      var6 = var5.__getattr__(var13);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var13, var6);
      var1.setline(300);
      var10000 = var1.getlocal(0);
      var13 = "_filelineno";
      var5 = var10000;
      var6 = var5.__getattr__(var13);
      var6 = var6._iadd(Py.newInteger(1));
      var5.__setattr__(var13, var6);
      var1.setline(301);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject filename$19(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getlocal(0).__getattr__("_filename");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lineno$20(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyObject var3 = var1.getlocal(0).__getattr__("_lineno");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject filelineno$21(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = var1.getlocal(0).__getattr__("_filelineno");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileno$22(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      PyInteger var3;
      if (var1.getlocal(0).__getattr__("_file").__nonzero__()) {
         try {
            var1.setline(367);
            PyObject var6 = var1.getlocal(0).__getattr__("_file").__getattr__("fileno").__call__(var2);
            var1.f_lasti = -1;
            return var6;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("ValueError"))) {
               var1.setline(369);
               var3 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      } else {
         var1.setline(371);
         var3 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject isfirstline$23(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyObject var3 = var1.getlocal(0).__getattr__("_filelineno");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isstdin$24(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3 = var1.getlocal(0).__getattr__("_isstdin");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hook_compressed$25(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(382);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned(".gz"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var3 = imp.importOne("gzip", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(384);
         var3 = var1.getlocal(3).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(385);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned(".bz2"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(386);
            var4 = imp.importOne("bz2", var1, -1);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(387);
            var3 = var1.getlocal(4).__getattr__("BZ2File").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(389);
            var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject hook_encoded$26(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 1);
      var1.setline(393);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(394);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = openhook$27;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(396);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject openhook$27(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = var1.getderef(0).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getderef(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$28(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(401);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(402);
      var7 = Py.newInteger(0);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(403);
      var3 = var1.getlocal(0).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("ib:"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(404);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(404);
         PyObject var8 = var3.__iternext__();
         PyObject var10000;
         if (var8 == null) {
            var1.setline(407);
            var10000 = var1.getglobal("input");
            PyObject[] var10 = new PyObject[]{var1.getlocal(4), var1.getlocal(1), var1.getlocal(2)};
            String[] var9 = new String[]{"inplace", "backup"};
            var10000 = var10000.__call__(var2, var10, var9);
            var3 = null;
            var3 = var10000.__iter__();

            while(true) {
               var1.setline(407);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  var1.setline(412);
                  Py.println(PyString.fromInterned("%d: %s[%d]")._mod(new PyTuple(new PyObject[]{var1.getglobal("lineno").__call__(var2), var1.getglobal("filename").__call__(var2), var1.getglobal("filelineno").__call__(var2)})));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var8);
               var1.setline(408);
               var5 = var1.getlocal(7).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
               var10000 = var5._eq(PyString.fromInterned("\n"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(408);
                  var5 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(7, var5);
                  var5 = null;
               }

               var1.setline(409);
               var5 = var1.getlocal(7).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
               var10000 = var5._eq(PyString.fromInterned("\r"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(409);
                  var5 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(7, var5);
                  var5 = null;
               }

               var1.setline(410);
               PyString var13 = PyString.fromInterned("%d: %s[%d]%s %s");
               PyTuple var10001 = new PyTuple;
               PyObject[] var10003 = new PyObject[]{var1.getglobal("lineno").__call__(var2), var1.getglobal("filename").__call__(var2), var1.getglobal("filelineno").__call__(var2), null, null};
               Object var10006 = var1.getglobal("isfirstline").__call__(var2);
               if (((PyObject)var10006).__nonzero__()) {
                  var10006 = PyString.fromInterned("*");
               }

               if (!((PyObject)var10006).__nonzero__()) {
                  var10006 = PyString.fromInterned("");
               }

               var10003[3] = (PyObject)var10006;
               var10003[4] = var1.getlocal(7);
               var10001.<init>(var10003);
               Py.println(var13._mod(var10001));
            }
         }

         PyObject[] var11 = Py.unpackSequence(var8, 2);
         PyObject var6 = var11[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(405);
         var5 = var1.getlocal(5);
         var10000 = var5._eq(PyString.fromInterned("-i"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(405);
            PyInteger var12 = Py.newInteger(1);
            var1.setlocal(1, var12);
            var5 = null;
         }

         var1.setline(406);
         var5 = var1.getlocal(5);
         var10000 = var5._eq(PyString.fromInterned("-b"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(406);
            var5 = var1.getlocal(6);
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public fileinput$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"files", "inplace", "backup", "bufsize", "mode", "openhook"};
      input$1 = Py.newCode(6, var2, var1, "input", 91, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"state"};
      close$2 = Py.newCode(0, var2, var1, "close", 106, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      nextfile$3 = Py.newCode(0, var2, var1, "nextfile", 114, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      filename$4 = Py.newCode(0, var2, var1, "filename", 128, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      lineno$5 = Py.newCode(0, var2, var1, "lineno", 137, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      filelineno$6 = Py.newCode(0, var2, var1, "filelineno", 147, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      fileno$7 = Py.newCode(0, var2, var1, "fileno", 157, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      isfirstline$8 = Py.newCode(0, var2, var1, "isfirstline", 166, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      isstdin$9 = Py.newCode(0, var2, var1, "isstdin", 175, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FileInput$10 = Py.newCode(0, var2, var1, "FileInput", 184, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "files", "inplace", "backup", "bufsize", "mode", "openhook"};
      __init__$11 = Py.newCode(7, var2, var1, "__init__", 197, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$12 = Py.newCode(1, var2, var1, "__del__", 233, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$13 = Py.newCode(1, var2, var1, "close", 236, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$14 = Py.newCode(1, var2, var1, "__iter__", 240, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$15 = Py.newCode(1, var2, var1, "next", 243, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __getitem__$16 = Py.newCode(2, var2, var1, "__getitem__", 258, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "savestdout", "output", "file", "backupfilename"};
      nextfile$17 = Py.newCode(1, var2, var1, "nextfile", 266, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "perm", "fd"};
      readline$18 = Py.newCode(1, var2, var1, "readline", 292, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      filename$19 = Py.newCode(1, var2, var1, "filename", 355, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lineno$20 = Py.newCode(1, var2, var1, "lineno", 358, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      filelineno$21 = Py.newCode(1, var2, var1, "filelineno", 361, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$22 = Py.newCode(1, var2, var1, "fileno", 364, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isfirstline$23 = Py.newCode(1, var2, var1, "isfirstline", 373, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isstdin$24 = Py.newCode(1, var2, var1, "isstdin", 376, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "mode", "ext", "gzip", "bz2"};
      hook_compressed$25 = Py.newCode(2, var2, var1, "hook_compressed", 380, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "openhook", "codecs"};
      String[] var10001 = var2;
      fileinput$py var10007 = self;
      var2 = new String[]{"codecs", "encoding"};
      hook_encoded$26 = Py.newCode(1, var10001, var1, "hook_encoded", 392, false, false, var10007, 26, var2, (String[])null, 1, 4097);
      var2 = new String[]{"filename", "mode"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"codecs", "encoding"};
      openhook$27 = Py.newCode(2, var10001, var1, "openhook", 394, false, false, var10007, 27, (String[])null, var2, 0, 4097);
      var2 = new String[]{"getopt", "inplace", "backup", "opts", "args", "o", "a", "line"};
      _test$28 = Py.newCode(0, var2, var1, "_test", 399, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fileinput$py("fileinput$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fileinput$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.input$1(var2, var3);
         case 2:
            return this.close$2(var2, var3);
         case 3:
            return this.nextfile$3(var2, var3);
         case 4:
            return this.filename$4(var2, var3);
         case 5:
            return this.lineno$5(var2, var3);
         case 6:
            return this.filelineno$6(var2, var3);
         case 7:
            return this.fileno$7(var2, var3);
         case 8:
            return this.isfirstline$8(var2, var3);
         case 9:
            return this.isstdin$9(var2, var3);
         case 10:
            return this.FileInput$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.__del__$12(var2, var3);
         case 13:
            return this.close$13(var2, var3);
         case 14:
            return this.__iter__$14(var2, var3);
         case 15:
            return this.next$15(var2, var3);
         case 16:
            return this.__getitem__$16(var2, var3);
         case 17:
            return this.nextfile$17(var2, var3);
         case 18:
            return this.readline$18(var2, var3);
         case 19:
            return this.filename$19(var2, var3);
         case 20:
            return this.lineno$20(var2, var3);
         case 21:
            return this.filelineno$21(var2, var3);
         case 22:
            return this.fileno$22(var2, var3);
         case 23:
            return this.isfirstline$23(var2, var3);
         case 24:
            return this.isstdin$24(var2, var3);
         case 25:
            return this.hook_compressed$25(var2, var3);
         case 26:
            return this.hook_encoded$26(var2, var3);
         case 27:
            return this.openhook$27(var2, var3);
         case 28:
            return this._test$28(var2, var3);
         default:
            return null;
      }
   }
}

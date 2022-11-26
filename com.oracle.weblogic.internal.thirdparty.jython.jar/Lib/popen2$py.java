import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("popen2.py")
public class popen2$py extends PyFunctionTable implements PyRunnable {
   static popen2$py self;
   static final PyCode f$0;
   static final PyCode Popen3$1;
   static final PyCode __init__$2;
   static final PyCode _setup$3;
   static final PyCode __del__$4;
   static final PyCode poll$5;
   static final PyCode wait$6;
   static final PyCode Popen4$7;
   static final PyCode __init__$8;
   static final PyCode popen2$9;
   static final PyCode popen3$10;
   static final PyCode popen4$11;
   static final PyCode popen2$12;
   static final PyCode popen3$13;
   static final PyCode popen4$14;
   static final PyCode _test$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Spawn a command with pipes to its stdin, stdout, and optionally stderr.\n\nThe normal os.popen(cmd, mode) call spawns a shell command and provides a\nfile interface to just the input or output of the process depending on\nwhether mode is 'r' or 'w'.  This module provides the functions popen2(cmd)\nand popen3(cmd) which return two or three pipes to the spawned command.\n"));
      var1.setline(7);
      PyString.fromInterned("Spawn a command with pipes to its stdin, stdout, and optionally stderr.\n\nThe normal os.popen(cmd, mode) call spawns a shell command and provides a\nfile interface to just the input or output of the process depending on\nwhether mode is 'r' or 'w'.  This module provides the functions popen2(cmd)\nand popen3(cmd) which return two or three pipes to the spawned command.\n");
      var1.setline(9);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal("subprocess", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(13);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("popen2"), PyString.fromInterned("popen3"), PyString.fromInterned("popen4")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(15);
      var3 = var1.getname("subprocess").__getattr__("MAXFD");
      var1.setlocal("MAXFD", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("subprocess").__getattr__("_active");
      var1.setlocal("_active", var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getname("subprocess").__getattr__("_cleanup");
      var1.setlocal("_cleanup", var3);
      var3 = null;
      var1.setline(19);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Popen3", var6, Popen3$1);
      var1.setlocal("Popen3", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(72);
      var6 = new PyObject[]{var1.getname("Popen3")};
      var4 = Py.makeClass("Popen4", var6, Popen4$7);
      var1.setlocal("Popen4", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(84);
      var3 = var1.getname("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("win"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getname("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("os2emx"));
         var3 = null;
      }

      PyFunction var7;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var1.dellocal("Popen3");
         var1.dellocal("Popen4");
         var1.setline(88);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen2$9, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin) are returned."));
         var1.setlocal("popen2", var7);
         var3 = null;
         var1.setline(98);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen3$10, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin, child_stderr) are returned."));
         var1.setlocal("popen3", var7);
         var3 = null;
         var1.setline(108);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen4$11, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout_stderr, child_stdin) are returned."));
         var1.setlocal("popen4", var7);
         var3 = null;
      } else {
         var1.setline(118);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen2$12, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin) are returned."));
         var1.setlocal("popen2", var7);
         var3 = null;
         var1.setline(128);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen3$13, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin, child_stderr) are returned."));
         var1.setlocal("popen3", var7);
         var3 = null;
         var1.setline(138);
         var6 = new PyObject[]{Py.newInteger(-1), PyString.fromInterned("t")};
         var7 = new PyFunction(var1.f_globals, var6, popen4$14, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout_stderr, child_stdin) are returned."));
         var1.setlocal("popen4", var7);
         var3 = null;
         var1.setline(148);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Popen3"), PyString.fromInterned("Popen4")})));
      }

      var1.setline(150);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _test$15, (PyObject)null);
      var1.setlocal("_test", var7);
      var3 = null;
      var1.setline(189);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(190);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Popen3$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class representing a child process.  Normally instances are created\n    by the factory functions popen2() and popen3()."));
      var1.setline(21);
      PyString.fromInterned("Class representing a child process.  Normally instances are created\n    by the factory functions popen2() and popen3().");
      var1.setline(23);
      PyInteger var3 = Py.newInteger(-1);
      var1.setlocal("sts", var3);
      var3 = null;
      var1.setline(25);
      PyObject[] var4 = new PyObject[]{var1.getname("False"), Py.newInteger(-1)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("The parameter 'cmd' is the shell command to execute in a\n        sub-process.  On UNIX, 'cmd' may be a sequence, in which case arguments\n        will be passed directly to the program without shell intervention (as\n        with os.spawnv()).  If 'cmd' is a string it will be passed to the shell\n        (as with os.system()).   The 'capturestderr' flag, if true, specifies\n        that the object should capture standard error output of the child\n        process.  The default is false.  If the 'bufsize' parameter is\n        specified, it specifies the size of the I/O buffers to/from the child\n        process."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(42);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _setup$3, PyString.fromInterned("Setup the Popen attributes."));
      var1.setlocal("_setup", var5);
      var3 = null;
      var1.setline(50);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __del__$4, (PyObject)null);
      var1.setlocal("__del__", var5);
      var3 = null;
      var1.setline(56);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, poll$5, PyString.fromInterned("Return the exit status of the child process if it has finished,\n        or -1 if it hasn't finished yet."));
      var1.setlocal("poll", var5);
      var3 = null;
      var1.setline(65);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, wait$6, PyString.fromInterned("Wait for and return the exit status of the child process."));
      var1.setlocal("wait", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyString.fromInterned("The parameter 'cmd' is the shell command to execute in a\n        sub-process.  On UNIX, 'cmd' may be a sequence, in which case arguments\n        will be passed directly to the program without shell intervention (as\n        with os.spawnv()).  If 'cmd' is a string it will be passed to the shell\n        (as with os.system()).   The 'capturestderr' flag, if true, specifies\n        that the object should capture standard error output of the child\n        process.  The default is false.  If the 'bufsize' parameter is\n        specified, it specifies the size of the I/O buffers to/from the child\n        process.");
      var1.setline(35);
      var1.setline(35);
      PyObject var3 = var1.getlocal(2).__nonzero__() ? var1.getglobal("subprocess").__getattr__("PIPE") : var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("subprocess").__getattr__("PIPE");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(37);
      PyObject var10000 = var1.getglobal("subprocess").__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")), var1.getlocal(5), var1.getlocal(5), var1.getlocal(4)};
      String[] var4 = new String[]{"bufsize", "shell", "stdin", "stdout", "stderr"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("_popen", var3);
      var3 = null;
      var1.setline(40);
      var1.getlocal(0).__getattr__("_setup").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setup$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Setup the Popen attributes.");
      var1.setline(44);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("cmd", var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("pid");
      var1.getlocal(0).__setattr__("pid", var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("stdin");
      var1.getlocal(0).__setattr__("tochild", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("stdout");
      var1.getlocal(0).__setattr__("fromchild", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("stderr");
      var1.getlocal(0).__setattr__("childerr", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$4(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_popen")).__nonzero__()) {
         var1.setline(54);
         var1.getlocal(0).__getattr__("_popen").__getattr__("__del__").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject poll$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Return the exit status of the child process if it has finished,\n        or -1 if it hasn't finished yet.");
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("sts");
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("poll").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(61);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("sts", var3);
            var3 = null;
         }
      }

      var1.setline(63);
      var3 = var1.getlocal(0).__getattr__("sts");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wait$6(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Wait for and return the exit status of the child process.");
      var1.setline(67);
      PyObject var3 = var1.getlocal(0).__getattr__("sts");
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var3 = var1.getlocal(0).__getattr__("_popen").__getattr__("wait").__call__(var2);
         var1.getlocal(0).__setattr__("sts", var3);
         var3 = null;
      }

      var1.setline(69);
      var3 = var1.getlocal(0).__getattr__("sts");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Popen4$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(73);
      PyObject var3 = var1.getname("None");
      var1.setlocal("childerr", var3);
      var3 = null;
      var1.setline(75);
      PyObject[] var4 = new PyObject[]{Py.newInteger(-1)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getglobal("subprocess").__getattr__("PIPE");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(77);
      PyObject var10000 = var1.getglobal("subprocess").__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")), var1.getlocal(3), var1.getlocal(3), var1.getglobal("subprocess").__getattr__("STDOUT")};
      String[] var4 = new String[]{"bufsize", "shell", "stdin", "stdout", "stderr"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("_popen", var3);
      var3 = null;
      var1.setline(81);
      var1.getlocal(0).__getattr__("_setup").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject popen2$9(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin) are returned.");
      var1.setline(95);
      PyObject var3 = var1.getglobal("os").__getattr__("popen2").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(96);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject popen3$10(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin, child_stderr) are returned.");
      var1.setline(105);
      PyObject var3 = var1.getglobal("os").__getattr__("popen3").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(106);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject popen4$11(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout_stderr, child_stdin) are returned.");
      var1.setline(115);
      PyObject var3 = var1.getglobal("os").__getattr__("popen4").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(116);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject popen2$12(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin) are returned.");
      var1.setline(125);
      PyObject var3 = var1.getglobal("Popen3").__call__(var2, var1.getlocal(0), var1.getglobal("False"), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(126);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("fromchild"), var1.getlocal(3).__getattr__("tochild")});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject popen3$13(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout, child_stdin, child_stderr) are returned.");
      var1.setline(135);
      PyObject var3 = var1.getglobal("Popen3").__call__(var2, var1.getlocal(0), var1.getglobal("True"), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(136);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("fromchild"), var1.getlocal(3).__getattr__("tochild"), var1.getlocal(3).__getattr__("childerr")});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject popen4$14(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process. On UNIX, 'cmd' may\n        be a sequence, in which case arguments will be passed directly to the\n        program without shell intervention (as with os.spawnv()). If 'cmd' is a\n        string it will be passed to the shell (as with os.system()). If\n        'bufsize' is specified, it sets the buffer size for the I/O pipes. The\n        file objects (child_stdout_stderr, child_stdin) are returned.");
      var1.setline(145);
      PyObject var3 = var1.getglobal("Popen4").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(146);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("fromchild"), var1.getlocal(3).__getattr__("tochild")});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _test$15(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      var1.getglobal("_cleanup").__call__(var2);
      var1.setline(153);
      PyString var3;
      PyObject var8;
      PyObject var9;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("_active").__not__().__nonzero__()) {
         PyString var11 = PyString.fromInterned("Active pipes when test starts ");
         PyObject var10001 = var1.getglobal("repr");
         PyList var10003 = new PyList();
         var8 = var10003.__getattr__("append");
         var1.setlocal(0, var8);
         var3 = null;
         var1.setline(153);
         var8 = var1.getglobal("_active").__iter__();

         while(true) {
            var1.setline(153);
            var9 = var8.__iternext__();
            if (var9 == null) {
               var1.setline(153);
               var1.dellocal(0);
               throw Py.makeException(var1.getglobal("AssertionError"), var11._add(var10001.__call__((ThreadState)var2, (PyObject)var10003)));
            }

            var1.setlocal(1, var9);
            var1.setline(153);
            var1.getlocal(0).__call__(var2, var1.getlocal(1).__getattr__("cmd"));
         }
      } else {
         var1.setline(154);
         var3 = PyString.fromInterned("cat");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(155);
         var3 = PyString.fromInterned("ab cd\n");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(156);
         var8 = var1.getglobal("os").__getattr__("name");
         PyObject var10000 = var8._in(new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("java")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(157);
            var3 = PyString.fromInterned("more");
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(161);
         var8 = var1.getlocal(3).__getattr__("strip").__call__(var2);
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(162);
         Py.println(PyString.fromInterned("testing popen2..."));
         var1.setline(163);
         var8 = var1.getglobal("popen2").__call__(var2, var1.getlocal(2));
         PyObject[] var4 = Py.unpackSequence(var8, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(164);
         var1.getlocal(6).__getattr__("write").__call__(var2, var1.getlocal(3));
         var1.setline(165);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         var1.setline(166);
         var8 = var1.getlocal(5).__getattr__("read").__call__(var2);
         var1.setlocal(7, var8);
         var3 = null;
         var1.setline(167);
         var8 = var1.getlocal(7).__getattr__("strip").__call__(var2);
         var10000 = var8._ne(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(168);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("wrote %r read %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(7)}))));
         } else {
            var1.setline(169);
            Py.println(PyString.fromInterned("testing popen3..."));

            try {
               var1.setline(171);
               var8 = var1.getglobal("popen3").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
               var4 = Py.unpackSequence(var8, 3);
               var5 = var4[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(6, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(8, var5);
               var5 = null;
               var3 = null;
            } catch (Throwable var7) {
               Py.setException(var7, var1);
               var1.setline(173);
               var9 = var1.getglobal("popen3").__call__(var2, var1.getlocal(2));
               PyObject[] var10 = Py.unpackSequence(var9, 3);
               PyObject var6 = var10[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var10[2];
               var1.setlocal(8, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(174);
            var1.getlocal(6).__getattr__("write").__call__(var2, var1.getlocal(3));
            var1.setline(175);
            var1.getlocal(6).__getattr__("close").__call__(var2);
            var1.setline(176);
            var8 = var1.getlocal(5).__getattr__("read").__call__(var2);
            var1.setlocal(7, var8);
            var3 = null;
            var1.setline(177);
            var8 = var1.getlocal(7).__getattr__("strip").__call__(var2);
            var10000 = var8._ne(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(178);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("wrote %r read %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(7)}))));
            } else {
               var1.setline(179);
               var8 = var1.getlocal(8).__getattr__("read").__call__(var2);
               var1.setlocal(7, var8);
               var3 = null;
               var1.setline(180);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(181);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unexpected %r on stderr")._mod(new PyTuple(new PyObject[]{var1.getlocal(7)}))));
               } else {
                  var1.setline(182);
                  var8 = var1.getglobal("_active").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

                  while(true) {
                     var1.setline(182);
                     var9 = var8.__iternext__();
                     if (var9 == null) {
                        var1.setline(184);
                        var1.getglobal("_cleanup").__call__(var2);
                        var1.setline(185);
                        if (var1.getglobal("_active").__nonzero__()) {
                           var1.setline(186);
                           throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_active not empty")));
                        } else {
                           var1.setline(187);
                           Py.println(PyString.fromInterned("All OK"));
                           var1.f_lasti = -1;
                           return Py.None;
                        }
                     }

                     var1.setlocal(9, var9);
                     var1.setline(183);
                     var1.getlocal(9).__getattr__("wait").__call__(var2);
                  }
               }
            }
         }
      }
   }

   public popen2$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Popen3$1 = Py.newCode(0, var2, var1, "Popen3", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cmd", "capturestderr", "bufsize", "stderr", "PIPE"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      _setup$3 = Py.newCode(2, var2, var1, "_setup", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$4 = Py.newCode(1, var2, var1, "__del__", 50, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_deadstate", "result"};
      poll$5 = Py.newCode(2, var2, var1, "poll", 56, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      wait$6 = Py.newCode(1, var2, var1, "wait", 65, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Popen4$7 = Py.newCode(0, var2, var1, "Popen4", 72, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cmd", "bufsize", "PIPE"};
      __init__$8 = Py.newCode(3, var2, var1, "__init__", 75, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "w", "r"};
      popen2$9 = Py.newCode(3, var2, var1, "popen2", 88, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "w", "r", "e"};
      popen3$10 = Py.newCode(3, var2, var1, "popen3", 98, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "w", "r"};
      popen4$11 = Py.newCode(3, var2, var1, "popen4", 108, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "inst"};
      popen2$12 = Py.newCode(3, var2, var1, "popen2", 118, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "inst"};
      popen3$13 = Py.newCode(3, var2, var1, "popen3", 128, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "bufsize", "mode", "inst"};
      popen4$14 = Py.newCode(3, var2, var1, "popen4", 138, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_[153_65]", "c", "cmd", "teststr", "expected", "r", "w", "got", "e", "inst"};
      _test$15 = Py.newCode(0, var2, var1, "_test", 150, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new popen2$py("popen2$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(popen2$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Popen3$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._setup$3(var2, var3);
         case 4:
            return this.__del__$4(var2, var3);
         case 5:
            return this.poll$5(var2, var3);
         case 6:
            return this.wait$6(var2, var3);
         case 7:
            return this.Popen4$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.popen2$9(var2, var3);
         case 10:
            return this.popen3$10(var2, var3);
         case 11:
            return this.popen4$11(var2, var3);
         case 12:
            return this.popen2$12(var2, var3);
         case 13:
            return this.popen3$13(var2, var3);
         case 14:
            return this.popen4$14(var2, var3);
         case 15:
            return this._test$15(var2, var3);
         default:
            return null;
      }
   }
}

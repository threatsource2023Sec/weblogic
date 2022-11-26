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
@Filename("pty.py")
public class pty$py extends PyFunctionTable implements PyRunnable {
   static pty$py self;
   static final PyCode f$0;
   static final PyCode openpty$1;
   static final PyCode master_open$2;
   static final PyCode _open_terminal$3;
   static final PyCode slave_open$4;
   static final PyCode fork$5;
   static final PyCode _writen$6;
   static final PyCode _read$7;
   static final PyCode _copy$8;
   static final PyCode spawn$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Pseudo terminal utilities."));
      var1.setline(1);
      PyString.fromInterned("Pseudo terminal utilities.");
      var1.setline(9);
      String[] var3 = new String[]{"select"};
      PyObject[] var5 = imp.importFrom("select", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("select", var4);
      var4 = null;
      var1.setline(10);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(11);
      var6 = imp.importOne("tty", var1, -1);
      var1.setlocal("tty", var6);
      var3 = null;
      var1.setline(13);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("openpty"), PyString.fromInterned("fork"), PyString.fromInterned("spawn")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(15);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal("STDIN_FILENO", var8);
      var3 = null;
      var1.setline(16);
      var8 = Py.newInteger(1);
      var1.setlocal("STDOUT_FILENO", var8);
      var3 = null;
      var1.setline(17);
      var8 = Py.newInteger(2);
      var1.setlocal("STDERR_FILENO", var8);
      var3 = null;
      var1.setline(19);
      var8 = Py.newInteger(0);
      var1.setlocal("CHILD", var8);
      var3 = null;
      var1.setline(21);
      var5 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var5, openpty$1, PyString.fromInterned("openpty() -> (master_fd, slave_fd)\n    Open a pty master/slave pair, using os.openpty() if possible."));
      var1.setlocal("openpty", var9);
      var3 = null;
      var1.setline(33);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, master_open$2, PyString.fromInterned("master_open() -> (master_fd, slave_name)\n    Open a pty master and return the fd, and the filename of the slave end.\n    Deprecated, use openpty() instead."));
      var1.setlocal("master_open", var9);
      var3 = null;
      var1.setline(49);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, _open_terminal$3, PyString.fromInterned("Open pty master and return (master_fd, tty_name).\n    SGI and generic BSD version, for when openpty() fails."));
      var1.setlocal("_open_terminal", var9);
      var3 = null;
      var1.setline(72);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, slave_open$4, PyString.fromInterned("slave_open(tty_name) -> slave_fd\n    Open the pty slave and acquire the controlling terminal, returning\n    opened filedescriptor.\n    Deprecated, use openpty() instead."));
      var1.setlocal("slave_open", var9);
      var3 = null;
      var1.setline(90);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, fork$5, PyString.fromInterned("fork() -> (pid, master_fd)\n    Fork and make the child a session leader with a controlling terminal."));
      var1.setlocal("fork", var9);
      var3 = null;
      var1.setline(130);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, _writen$6, PyString.fromInterned("Write all the data to a descriptor."));
      var1.setlocal("_writen", var9);
      var3 = null;
      var1.setline(136);
      var5 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var5, _read$7, PyString.fromInterned("Default read function."));
      var1.setlocal("_read", var9);
      var3 = null;
      var1.setline(140);
      var5 = new PyObject[]{var1.getname("_read"), var1.getname("_read")};
      var9 = new PyFunction(var1.f_globals, var5, _copy$8, PyString.fromInterned("Parent copy loop.\n    Copies\n            pty master -> standard output   (master_read)\n            standard input -> pty master    (stdin_read)"));
      var1.setlocal("_copy", var9);
      var3 = null;
      var1.setline(161);
      var5 = new PyObject[]{var1.getname("_read"), var1.getname("_read")};
      var9 = new PyFunction(var1.f_globals, var5, spawn$9, PyString.fromInterned("Create a spawned process."));
      var1.setlocal("spawn", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject openpty$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyString.fromInterned("openpty() -> (master_fd, slave_fd)\n    Open a pty master/slave pair, using os.openpty() if possible.");

      try {
         var1.setline(26);
         PyObject var8 = var1.getglobal("os").__getattr__("openpty").__call__(var2);
         var1.f_lasti = -1;
         return var8;
      } catch (Throwable var7) {
         PyException var4 = Py.setException(var7, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("OSError")}))) {
            var1.setline(28);
            var1.setline(29);
            PyObject var9 = var1.getglobal("_open_terminal").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var9, 2);
            PyObject var6 = var5[0];
            var1.setlocal(0, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(1, var6);
            var6 = null;
            var4 = null;
            var1.setline(30);
            var9 = var1.getglobal("slave_open").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var9);
            var4 = null;
            var1.setline(31);
            PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject master_open$2(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("master_open() -> (master_fd, slave_name)\n    Open a pty master and return the fd, and the filename of the slave end.\n    Deprecated, use openpty() instead.");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(39);
         PyObject var7 = var1.getglobal("os").__getattr__("openpty").__call__(var2);
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var5 = var8[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("OSError")}))) {
            var1.setline(41);
            var1.setline(47);
            var4 = var1.getglobal("_open_terminal").__call__(var2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(43);
      var4 = var1.getglobal("os").__getattr__("ttyname").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(44);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(1));
      var1.setline(45);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject _open_terminal$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Open pty master and return (master_fd, tty_name).\n    SGI and generic BSD version, for when openpty() fails.");

      PyException var3;
      PyTuple var4;
      PyObject var5;
      PyObject var6;
      PyObject var12;
      try {
         var1.setline(53);
         var12 = imp.importOne("sgi", var1, -1);
         var1.setlocal(0, var12);
         var3 = null;
      } catch (Throwable var11) {
         var3 = Py.setException(var11, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(55);
            var1.setline(62);
            var12 = PyString.fromInterned("pqrstuvwxyzPQRST").__iter__();

            label51:
            while(true) {
               var1.setline(62);
               var5 = var12.__iternext__();
               if (var5 == null) {
                  var1.setline(70);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), PyString.fromInterned("out of pty devices"));
               }

               var1.setlocal(4, var5);
               var1.setline(63);
               var6 = PyString.fromInterned("0123456789abcdef").__iter__();

               while(true) {
                  var1.setline(63);
                  PyObject var7 = var6.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  var1.setlocal(5, var7);
                  var1.setline(64);
                  PyObject var8 = PyString.fromInterned("/dev/pty")._add(var1.getlocal(4))._add(var1.getlocal(5));
                  var1.setlocal(6, var8);
                  var8 = null;

                  try {
                     var1.setline(66);
                     var8 = var1.getglobal("os").__getattr__("open").__call__(var2, var1.getlocal(6), var1.getglobal("os").__getattr__("O_RDWR"));
                     var1.setlocal(7, var8);
                     var8 = null;
                     break label51;
                  } catch (Throwable var10) {
                     PyException var16 = Py.setException(var10, var1);
                     if (!var16.match(var1.getglobal("os").__getattr__("error"))) {
                        throw var16;
                     }
                  }
               }
            }

            var1.setline(69);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned("/dev/tty")._add(var1.getlocal(4))._add(var1.getlocal(5))});
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      try {
         var1.setline(58);
         PyObject var14 = var1.getlocal(0).__getattr__("_getpty").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("O_RDWR"), (PyObject)Py.newInteger(438), (PyObject)Py.newInteger(0));
         PyObject[] var15 = Py.unpackSequence(var14, 2);
         var6 = var15[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var15[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
      } catch (Throwable var9) {
         PyException var13 = Py.setException(var9, var1);
         if (var13.match(var1.getglobal("IOError"))) {
            var5 = var13.value;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(60);
            throw Py.makeException(var1.getglobal("os").__getattr__("error"), var1.getlocal(3));
         }

         throw var13;
      }

      var1.setline(61);
      var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject slave_open$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("slave_open(tty_name) -> slave_fd\n    Open the pty slave and acquire the controlling terminal, returning\n    opened filedescriptor.\n    Deprecated, use openpty() instead.");
      var1.setline(78);
      PyObject var3 = var1.getglobal("os").__getattr__("open").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("O_RDWR"));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyException var7;
      try {
         var1.setline(80);
         String[] var8 = new String[]{"ioctl", "I_PUSH"};
         PyObject[] var9 = imp.importFrom("fcntl", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal(2, var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("ImportError"))) {
            var1.setline(82);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var7;
      }

      try {
         var1.setline(84);
         var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ptem"));
         var1.setline(85);
         var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ldterm"));
      } catch (Throwable var6) {
         var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("IOError"))) {
            throw var7;
         }

         var1.setline(87);
      }

      var1.setline(88);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject fork$5(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("fork() -> (pid, master_fd)\n    Fork and make the child a session leader with a controlling terminal.");

      PyException var3;
      PyTuple var4;
      PyObject[] var5;
      PyObject var9;
      PyObject var10000;
      try {
         var1.setline(95);
         var9 = var1.getglobal("os").__getattr__("forkpty").__call__(var2);
         PyObject[] var10 = Py.unpackSequence(var9, 2);
         PyObject var12 = var10[0];
         var1.setlocal(0, var12);
         var5 = null;
         var12 = var10[1];
         var1.setlocal(1, var12);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("OSError")}))) {
            var1.setline(97);
            var1.setline(107);
            var9 = var1.getglobal("openpty").__call__(var2);
            var5 = Py.unpackSequence(var9, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var3 = null;
            var1.setline(108);
            var9 = var1.getglobal("os").__getattr__("fork").__call__(var2);
            var1.setlocal(0, var9);
            var3 = null;
            var1.setline(109);
            var9 = var1.getlocal(0);
            var10000 = var9._eq(var1.getglobal("CHILD"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(111);
               var1.getglobal("os").__getattr__("setsid").__call__(var2);
               var1.setline(112);
               var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(2));
               var1.setline(115);
               var1.getglobal("os").__getattr__("dup2").__call__(var2, var1.getlocal(3), var1.getglobal("STDIN_FILENO"));
               var1.setline(116);
               var1.getglobal("os").__getattr__("dup2").__call__(var2, var1.getlocal(3), var1.getglobal("STDOUT_FILENO"));
               var1.setline(117);
               var1.getglobal("os").__getattr__("dup2").__call__(var2, var1.getlocal(3), var1.getglobal("STDERR_FILENO"));
               var1.setline(118);
               var9 = var1.getlocal(3);
               var10000 = var9._gt(var1.getglobal("STDERR_FILENO"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(119);
                  var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(3));
               }

               var1.setline(122);
               var9 = var1.getglobal("os").__getattr__("open").__call__(var2, var1.getglobal("os").__getattr__("ttyname").__call__(var2, var1.getglobal("STDOUT_FILENO")), var1.getglobal("os").__getattr__("O_RDWR"));
               var1.setlocal(4, var9);
               var3 = null;
               var1.setline(123);
               var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(4));
            } else {
               var1.setline(125);
               var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(3));
            }

            var1.setline(128);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(99);
      PyObject var11 = var1.getlocal(0);
      var10000 = var11._eq(var1.getglobal("CHILD"));
      var4 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(101);
            var1.getglobal("os").__getattr__("setsid").__call__(var2);
         } catch (Throwable var8) {
            PyException var13 = Py.setException(var8, var1);
            if (!var13.match(var1.getglobal("OSError"))) {
               throw var13;
            }

            var1.setline(104);
         }
      }

      var1.setline(105);
      var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _writen$6(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("Write all the data to a descriptor.");

      while(true) {
         var1.setline(132);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._ne(PyString.fromInterned(""));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(133);
         var3 = var1.getglobal("os").__getattr__("write").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(134);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject _read$7(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Default read function.");
      var1.setline(138);
      PyObject var3 = var1.getglobal("os").__getattr__("read").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(1024));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _copy$8(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyString.fromInterned("Parent copy loop.\n    Copies\n            pty master -> standard output   (master_read)\n            standard input -> pty master    (stdin_read)");
      var1.setline(145);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("STDIN_FILENO")});
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(146);
         if (!var1.getglobal("True").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(147);
         PyObject var6 = var1.getglobal("select").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)(new PyList(Py.EmptyObjects)));
         PyObject[] var4 = Py.unpackSequence(var6, 3);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(148);
         var6 = var1.getlocal(0);
         PyObject var10000 = var6._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(149);
            var6 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
            var1.setlocal(7, var6);
            var3 = null;
            var1.setline(150);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(151);
               var1.getlocal(3).__getattr__("remove").__call__(var2, var1.getlocal(0));
            } else {
               var1.setline(153);
               var1.getglobal("os").__getattr__("write").__call__(var2, var1.getglobal("STDOUT_FILENO"), var1.getlocal(7));
            }
         }

         var1.setline(154);
         var6 = var1.getglobal("STDIN_FILENO");
         var10000 = var6._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            var6 = var1.getlocal(2).__call__(var2, var1.getglobal("STDIN_FILENO"));
            var1.setlocal(7, var6);
            var3 = null;
            var1.setline(156);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(157);
               var1.getlocal(3).__getattr__("remove").__call__(var2, var1.getglobal("STDIN_FILENO"));
            } else {
               var1.setline(159);
               var1.getglobal("_writen").__call__(var2, var1.getlocal(0), var1.getlocal(7));
            }
         }
      }
   }

   public PyObject spawn$9(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("Create a spawned process.");
      var1.setline(163);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(164);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(0)});
         var1.setlocal(0, var8);
         var3 = null;
      }

      var1.setline(165);
      var3 = var1.getglobal("fork").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(166);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(var1.getglobal("CHILD"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(167);
         var10000 = var1.getglobal("os").__getattr__("execlp");
         PyObject[] var11 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0))};
         String[] var9 = new String[0];
         var10000._callextra(var11, var9, var1.getlocal(0), (PyObject)null);
         var3 = null;
      }

      PyException var12;
      try {
         var1.setline(169);
         var3 = var1.getglobal("tty").__getattr__("tcgetattr").__call__(var2, var1.getglobal("STDIN_FILENO"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(170);
         var1.getglobal("tty").__getattr__("setraw").__call__(var2, var1.getglobal("STDIN_FILENO"));
         var1.setline(171);
         PyInteger var13 = Py.newInteger(1);
         var1.setlocal(6, var13);
         var3 = null;
      } catch (Throwable var7) {
         var12 = Py.setException(var7, var1);
         if (!var12.match(var1.getglobal("tty").__getattr__("error"))) {
            throw var12;
         }

         var1.setline(173);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(6, var10);
         var4 = null;
      }

      try {
         var1.setline(175);
         var1.getglobal("_copy").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
      } catch (Throwable var6) {
         var12 = Py.setException(var6, var1);
         if (!var12.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("OSError")}))) {
            throw var12;
         }

         var1.setline(177);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(178);
            var1.getglobal("tty").__getattr__("tcsetattr").__call__(var2, var1.getglobal("STDIN_FILENO"), var1.getglobal("tty").__getattr__("TCSAFLUSH"), var1.getlocal(5));
         }
      }

      var1.setline(180);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public pty$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"master_fd", "slave_name", "slave_fd"};
      openpty$1 = Py.newCode(0, var2, var1, "openpty", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"master_fd", "slave_fd", "slave_name"};
      master_open$2 = Py.newCode(0, var2, var1, "master_open", 33, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sgi", "tty_name", "master_fd", "msg", "x", "y", "pty_name", "fd"};
      _open_terminal$3 = Py.newCode(0, var2, var1, "_open_terminal", 49, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tty_name", "result", "ioctl", "I_PUSH"};
      slave_open$4 = Py.newCode(1, var2, var1, "slave_open", 72, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pid", "fd", "master_fd", "slave_fd", "tmp_fd"};
      fork$5 = Py.newCode(0, var2, var1, "fork", 90, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fd", "data", "n"};
      _writen$6 = Py.newCode(2, var2, var1, "_writen", 130, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fd"};
      _read$7 = Py.newCode(1, var2, var1, "_read", 136, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"master_fd", "master_read", "stdin_read", "fds", "rfds", "wfds", "xfds", "data"};
      _copy$8 = Py.newCode(3, var2, var1, "_copy", 140, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"argv", "master_read", "stdin_read", "pid", "master_fd", "mode", "restore"};
      spawn$9 = Py.newCode(3, var2, var1, "spawn", 161, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pty$py("pty$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pty$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.openpty$1(var2, var3);
         case 2:
            return this.master_open$2(var2, var3);
         case 3:
            return this._open_terminal$3(var2, var3);
         case 4:
            return this.slave_open$4(var2, var3);
         case 5:
            return this.fork$5(var2, var3);
         case 6:
            return this._writen$6(var2, var3);
         case 7:
            return this._read$7(var2, var3);
         case 8:
            return this._copy$8(var2, var3);
         case 9:
            return this.spawn$9(var2, var3);
         default:
            return null;
      }
   }
}

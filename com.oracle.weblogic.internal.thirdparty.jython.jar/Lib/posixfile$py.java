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
@MTime(1498849383000L)
@Filename("posixfile.py")
public class posixfile$py extends PyFunctionTable implements PyRunnable {
   static posixfile$py self;
   static final PyCode f$0;
   static final PyCode _posixfile_$1;
   static final PyCode __repr__$2;
   static final PyCode open$3;
   static final PyCode fileopen$4;
   static final PyCode file$5;
   static final PyCode dup$6;
   static final PyCode dup2$7;
   static final PyCode flags$8;
   static final PyCode lock$9;
   static final PyCode open$10;
   static final PyCode fileopen$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Extended file operations available in POSIX.\n\nf = posixfile.open(filename, [mode, [bufsize]])\n      will create a new posixfile object\n\nf = posixfile.fileopen(fileobject)\n      will create a posixfile object from a builtin file object\n\nf.file()\n      will return the original builtin file object\n\nf.dup()\n      will return a new file object based on a new filedescriptor\n\nf.dup2(fd)\n      will return a new file object based on the given filedescriptor\n\nf.flags(mode)\n      will turn on the associated flag (merge)\n      mode can contain the following characters:\n\n  (character representing a flag)\n      a       append only flag\n      c       close on exec flag\n      n       no delay flag\n      s       synchronization flag\n  (modifiers)\n      !       turn flags 'off' instead of default 'on'\n      =       copy flags 'as is' instead of default 'merge'\n      ?       return a string in which the characters represent the flags\n              that are set\n\n      note: - the '!' and '=' modifiers are mutually exclusive.\n            - the '?' modifier will return the status of the flags after they\n              have been changed by other characters in the mode string\n\nf.lock(mode [, len [, start [, whence]]])\n      will (un)lock a region\n      mode can contain the following characters:\n\n  (character representing type of lock)\n      u       unlock\n      r       read lock\n      w       write lock\n  (modifiers)\n      |       wait until the lock can be granted\n      ?       return the first lock conflicting with the requested lock\n              or 'None' if there is no conflict. The lock returned is in the\n              format (mode, len, start, whence, pid) where mode is a\n              character representing the type of lock ('r' or 'w')\n\n      note: - the '?' modifier prevents a region from being locked; it is\n              query only\n"));
      var1.setline(54);
      PyString.fromInterned("Extended file operations available in POSIX.\n\nf = posixfile.open(filename, [mode, [bufsize]])\n      will create a new posixfile object\n\nf = posixfile.fileopen(fileobject)\n      will create a posixfile object from a builtin file object\n\nf.file()\n      will return the original builtin file object\n\nf.dup()\n      will return a new file object based on a new filedescriptor\n\nf.dup2(fd)\n      will return a new file object based on the given filedescriptor\n\nf.flags(mode)\n      will turn on the associated flag (merge)\n      mode can contain the following characters:\n\n  (character representing a flag)\n      a       append only flag\n      c       close on exec flag\n      n       no delay flag\n      s       synchronization flag\n  (modifiers)\n      !       turn flags 'off' instead of default 'on'\n      =       copy flags 'as is' instead of default 'merge'\n      ?       return a string in which the characters represent the flags\n              that are set\n\n      note: - the '!' and '=' modifiers are mutually exclusive.\n            - the '?' modifier will return the status of the flags after they\n              have been changed by other characters in the mode string\n\nf.lock(mode [, len [, start [, whence]]])\n      will (un)lock a region\n      mode can contain the following characters:\n\n  (character representing type of lock)\n      u       unlock\n      r       read lock\n      w       write lock\n  (modifiers)\n      |       wait until the lock can be granted\n      ?       return the first lock conflicting with the requested lock\n              or 'None' if there is no conflict. The lock returned is in the\n              format (mode, len, start, whence, pid) where mode is a\n              character representing the type of lock ('r' or 'w')\n\n      note: - the '?' modifier prevents a region from being locked; it is\n              query only\n");
      var1.setline(55);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(56);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("The posixfile module is deprecated; fcntl.lockf() provides better locking"), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(59);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("_posixfile_", var5, _posixfile_$1);
      var1.setlocal("_posixfile_", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(220);
      var5 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, open$10, PyString.fromInterned("Public routine to open a file as a posixfile object."));
      var1.setlocal("open", var6);
      var3 = null;
      var1.setline(224);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fileopen$11, PyString.fromInterned("Public routine to get a posixfile object from a Python file object."));
      var1.setlocal("fileopen", var6);
      var3 = null;
      var1.setline(231);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal("SEEK_SET", var7);
      var3 = null;
      var1.setline(232);
      var7 = Py.newInteger(1);
      var1.setlocal("SEEK_CUR", var7);
      var3 = null;
      var1.setline(233);
      var7 = Py.newInteger(2);
      var1.setlocal("SEEK_END", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _posixfile_$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("File wrapper class that provides extra POSIX file routines."));
      var1.setline(60);
      PyString.fromInterned("File wrapper class that provides extra POSIX file routines.");
      var1.setline(62);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("open"), PyString.fromInterned("closed")});
      var1.setlocal("states", var3);
      var3 = null;
      var1.setline(67);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __repr__$2, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(76);
      var4 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, open$3, (PyObject)null);
      var1.setlocal("open", var5);
      var3 = null;
      var1.setline(80);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileopen$4, (PyObject)null);
      var1.setlocal("fileopen", var5);
      var3 = null;
      var1.setline(96);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, file$5, (PyObject)null);
      var1.setlocal("file", var5);
      var3 = null;
      var1.setline(99);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dup$6, (PyObject)null);
      var1.setlocal("dup", var5);
      var3 = null;
      var1.setline(107);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dup2$7, (PyObject)null);
      var1.setlocal("dup2", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flags$8, (PyObject)null);
      var1.setlocal("flags", var5);
      var3 = null;
      var1.setline(153);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, lock$9, (PyObject)null);
      var1.setlocal("lock", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __repr__$2(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getlocal(0).__getattr__("_file_");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(69);
      var3 = PyString.fromInterned("<%s posixfile '%s', mode '%s' at %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("states").__getitem__(var1.getlocal(1).__getattr__("closed")), var1.getlocal(1).__getattr__("name"), var1.getlocal(1).__getattr__("mode"), var1.getglobal("hex").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(0))).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(0).__getattr__("fileopen").__call__(var2, var1.getlocal(4).__getattr__("open").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileopen$4(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getglobal("repr").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)));
      PyObject var10000 = var3._ne(PyString.fromInterned("<type 'file'>"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(83);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("posixfile.fileopen() arg must be file object"));
      } else {
         var1.setline(84);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_file_", var3);
         var3 = null;
         var1.setline(86);
         var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(86);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(91);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var4);
            var1.setline(87);
            if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__().__nonzero__()) {
               var1.setline(88);
               PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(3));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(89);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("BuiltinMethodType")).__nonzero__()) {
                  var1.setline(90);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject file$5(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("_file_");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dup$6(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = imp.importOne("posix", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(102);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("fdopen")).__not__().__nonzero__()) {
         var1.setline(103);
         throw Py.makeException(var1.getglobal("AttributeError"), PyString.fromInterned("dup() method unavailable"));
      } else {
         var1.setline(105);
         var3 = var1.getlocal(1).__getattr__("fdopen").__call__(var2, var1.getlocal(1).__getattr__("dup").__call__(var2, var1.getlocal(0).__getattr__("_file_").__getattr__("fileno").__call__(var2)), var1.getlocal(0).__getattr__("_file_").__getattr__("mode"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject dup2$7(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyObject var3 = imp.importOne("posix", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(110);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("fdopen")).__not__().__nonzero__()) {
         var1.setline(111);
         throw Py.makeException(var1.getglobal("AttributeError"), PyString.fromInterned("dup() method unavailable"));
      } else {
         var1.setline(113);
         var1.getlocal(2).__getattr__("dup2").__call__(var2, var1.getlocal(0).__getattr__("_file_").__getattr__("fileno").__call__(var2), var1.getlocal(1));
         var1.setline(114);
         var3 = var1.getlocal(2).__getattr__("fdopen").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_file_").__getattr__("mode"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject flags$8(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = imp.importOne("fcntl", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(119);
      PyObject var10000;
      PyString var4;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(120);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(121);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("Too many arguments"));
         }

         var1.setline(122);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(123);
         var4 = PyString.fromInterned("?");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(125);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(126);
      var4 = PyString.fromInterned("n");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         var3 = var1.getlocal(4)._or(var1.getlocal(3).__getattr__("O_NDELAY"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(127);
      var4 = PyString.fromInterned("a");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var3 = var1.getlocal(4)._or(var1.getlocal(3).__getattr__("O_APPEND"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(128);
      var4 = PyString.fromInterned("s");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(128);
         var3 = var1.getlocal(4)._or(var1.getlocal(3).__getattr__("O_SYNC"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(130);
      var3 = var1.getlocal(0).__getattr__("_file_");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(132);
      var4 = PyString.fromInterned("=");
      var10000 = var4._notin(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(133);
         var3 = var1.getlocal(2).__getattr__("fcntl").__call__((ThreadState)var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), (PyObject)var1.getlocal(2).__getattr__("F_GETFL"), (PyObject)Py.newInteger(0));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(134);
         var4 = PyString.fromInterned("!");
         var10000 = var4._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(134);
            var3 = var1.getlocal(6)._and(var1.getlocal(4).__invert__());
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(135);
            var3 = var1.getlocal(6)._or(var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
         }
      }

      var1.setline(137);
      var3 = var1.getlocal(2).__getattr__("fcntl").__call__(var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), var1.getlocal(2).__getattr__("F_SETFL"), var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(139);
      var4 = PyString.fromInterned("c");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(140);
         var4 = PyString.fromInterned("!");
         var10000 = var4._notin(var1.getlocal(1));
         var3 = null;
         var3 = var10000;
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(141);
         var3 = var1.getlocal(2).__getattr__("fcntl").__call__(var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), var1.getlocal(2).__getattr__("F_SETFD"), var1.getlocal(7));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(143);
      var4 = PyString.fromInterned("?");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(144);
         var4 = PyString.fromInterned("");
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(145);
         var3 = var1.getlocal(2).__getattr__("fcntl").__call__((ThreadState)var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), (PyObject)var1.getlocal(2).__getattr__("F_GETFL"), (PyObject)Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(146);
         if (var1.getlocal(3).__getattr__("O_APPEND")._and(var1.getlocal(4)).__nonzero__()) {
            var1.setline(146);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("a"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(147);
         if (var1.getlocal(2).__getattr__("fcntl").__call__((ThreadState)var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), (PyObject)var1.getlocal(2).__getattr__("F_GETFD"), (PyObject)Py.newInteger(0))._and(Py.newInteger(1)).__nonzero__()) {
            var1.setline(148);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("c"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(149);
         if (var1.getlocal(3).__getattr__("O_NDELAY")._and(var1.getlocal(4)).__nonzero__()) {
            var1.setline(149);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("n"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(150);
         if (var1.getlocal(3).__getattr__("O_SYNC")._and(var1.getlocal(4)).__nonzero__()) {
            var1.setline(150);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("s"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(151);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject lock$9(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var3 = imp.importOne("fcntl", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(156);
      PyString var6 = PyString.fromInterned("w");
      PyObject var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var3 = var1.getlocal(4).__getattr__("F_WRLCK");
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(157);
         var6 = PyString.fromInterned("r");
         var10000 = var6._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(157);
            var3 = var1.getlocal(4).__getattr__("F_RDLCK");
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(158);
            var6 = PyString.fromInterned("u");
            var10000 = var6._in(var1.getlocal(1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(159);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("no type of lock specified"));
            }

            var1.setline(158);
            var3 = var1.getlocal(4).__getattr__("F_UNLCK");
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(161);
      var6 = PyString.fromInterned("|");
      var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var3 = var1.getlocal(4).__getattr__("F_SETLKW");
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(162);
         var6 = PyString.fromInterned("?");
         var10000 = var6._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(162);
            var3 = var1.getlocal(4).__getattr__("F_GETLK");
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(163);
            var3 = var1.getlocal(4).__getattr__("F_SETLK");
            var1.setlocal(6, var3);
            var3 = null;
         }
      }

      var1.setline(165);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(166);
      var7 = Py.newInteger(0);
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(167);
      var7 = Py.newInteger(0);
      var1.setlocal(9, var7);
      var3 = null;
      var1.setline(169);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(170);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(171);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var3 = var1.getlocal(2);
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(173);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(174);
               var3 = var1.getlocal(2);
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(8, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(7, var5);
               var5 = null;
               var3 = null;
            } else {
               var1.setline(175);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._gt(Py.newInteger(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(176);
                  throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("too many arguments"));
               }
            }
         }
      }

      var1.setline(180);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(10, var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(181);
      var3 = var1.getlocal(10).__getattr__("platform");
      var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("netbsd1"), PyString.fromInterned("openbsd2"), PyString.fromInterned("freebsd2"), PyString.fromInterned("freebsd3"), PyString.fromInterned("freebsd4"), PyString.fromInterned("freebsd5"), PyString.fromInterned("freebsd6"), PyString.fromInterned("freebsd7"), PyString.fromInterned("freebsd8"), PyString.fromInterned("bsdos2"), PyString.fromInterned("bsdos3"), PyString.fromInterned("bsdos4")}));
      var3 = null;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         var10000 = var1.getlocal(3).__getattr__("pack");
         var8 = new PyObject[]{PyString.fromInterned("lxxxxlxxxxlhh"), var1.getlocal(8), var1.getlocal(9), var1.getlocal(11).__getattr__("getpid").__call__(var2), var1.getlocal(5), var1.getlocal(7)};
         var3 = var10000.__call__(var2, var8);
         var1.setlocal(12, var3);
         var3 = null;
      } else {
         var1.setline(188);
         var3 = var1.getlocal(10).__getattr__("platform");
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("aix3"), PyString.fromInterned("aix4")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(189);
            var10000 = var1.getlocal(3).__getattr__("pack");
            var8 = new PyObject[]{PyString.fromInterned("hhlllii"), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
            var3 = var10000.__call__(var2, var8);
            var1.setlocal(12, var3);
            var3 = null;
         } else {
            var1.setline(192);
            var10000 = var1.getlocal(3).__getattr__("pack");
            var8 = new PyObject[]{PyString.fromInterned("hhllhh"), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), Py.newInteger(0), Py.newInteger(0)};
            var3 = var10000.__call__(var2, var8);
            var1.setlocal(12, var3);
            var3 = null;
         }
      }

      var1.setline(195);
      var3 = var1.getlocal(4).__getattr__("fcntl").__call__(var2, var1.getlocal(0).__getattr__("_file_").__getattr__("fileno").__call__(var2), var1.getlocal(6), var1.getlocal(12));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(197);
      var6 = PyString.fromInterned("?");
      var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(198);
         var3 = var1.getlocal(10).__getattr__("platform");
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("netbsd1"), PyString.fromInterned("openbsd2"), PyString.fromInterned("freebsd2"), PyString.fromInterned("freebsd3"), PyString.fromInterned("freebsd4"), PyString.fromInterned("freebsd5"), PyString.fromInterned("bsdos2"), PyString.fromInterned("bsdos3"), PyString.fromInterned("bsdos4")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var3 = var1.getlocal(3).__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxxxxlxxxxlhh"), (PyObject)var1.getlocal(12));
            var4 = Py.unpackSequence(var3, 5);
            var5 = var4[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(13, var5);
            var5 = null;
            var5 = var4[3];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var4[4];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(204);
            var3 = var1.getlocal(10).__getattr__("platform");
            var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("aix3"), PyString.fromInterned("aix4")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(205);
               var3 = var1.getlocal(3).__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hhlllii"), (PyObject)var1.getlocal(12));
               var4 = Py.unpackSequence(var3, 7);
               var5 = var4[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(7, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(8, var5);
               var5 = null;
               var5 = var4[3];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[4];
               var1.setlocal(14, var5);
               var5 = null;
               var5 = var4[5];
               var1.setlocal(13, var5);
               var5 = null;
               var5 = var4[6];
               var1.setlocal(15, var5);
               var5 = null;
               var3 = null;
            } else {
               var1.setline(207);
               var3 = var1.getlocal(10).__getattr__("platform");
               var10000 = var3._eq(PyString.fromInterned("linux2"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(208);
                  var3 = var1.getlocal(3).__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hhllhh"), (PyObject)var1.getlocal(12));
                  var4 = Py.unpackSequence(var3, 6);
                  var5 = var4[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(8, var5);
                  var5 = null;
                  var5 = var4[3];
                  var1.setlocal(9, var5);
                  var5 = null;
                  var5 = var4[4];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[5];
                  var1.setlocal(14, var5);
                  var5 = null;
                  var3 = null;
               } else {
                  var1.setline(211);
                  var3 = var1.getlocal(3).__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hhllhh"), (PyObject)var1.getlocal(12));
                  var4 = Py.unpackSequence(var3, 6);
                  var5 = var4[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(7, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(8, var5);
                  var5 = null;
                  var5 = var4[3];
                  var1.setlocal(9, var5);
                  var5 = null;
                  var5 = var4[4];
                  var1.setlocal(14, var5);
                  var5 = null;
                  var5 = var4[5];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var3 = null;
               }
            }
         }

         var1.setline(214);
         var3 = var1.getlocal(5);
         var10000 = var3._ne(var1.getlocal(4).__getattr__("F_UNLCK"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(var1.getlocal(4).__getattr__("F_RDLCK"));
            var3 = null;
            PyTuple var9;
            if (var10000.__nonzero__()) {
               var1.setline(216);
               var9 = new PyTuple(new PyObject[]{PyString.fromInterned("r"), var1.getlocal(9), var1.getlocal(8), var1.getlocal(7), var1.getlocal(13)});
               var1.f_lasti = -1;
               return var9;
            }

            var1.setline(218);
            var9 = new PyTuple(new PyObject[]{PyString.fromInterned("w"), var1.getlocal(9), var1.getlocal(8), var1.getlocal(7), var1.getlocal(13)});
            var1.f_lasti = -1;
            return var9;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$10(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Public routine to open a file as a posixfile object.");
      var1.setline(222);
      PyObject var3 = var1.getglobal("_posixfile_").__call__(var2).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileopen$11(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyString.fromInterned("Public routine to get a posixfile object from a Python file object.");
      var1.setline(226);
      PyObject var3 = var1.getglobal("_posixfile_").__call__(var2).__getattr__("fileopen").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public posixfile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _posixfile_$1 = Py.newCode(0, var2, var1, "_posixfile_", 59, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file"};
      __repr__$2 = Py.newCode(1, var2, var1, "__repr__", 67, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "mode", "bufsize", "__builtin__"};
      open$3 = Py.newCode(4, var2, var1, "open", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "types", "maybemethod", "attr"};
      fileopen$4 = Py.newCode(2, var2, var1, "fileopen", 80, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      file$5 = Py.newCode(1, var2, var1, "file", 96, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "posix"};
      dup$6 = Py.newCode(1, var2, var1, "dup", 99, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "posix"};
      dup2$7 = Py.newCode(2, var2, var1, "dup2", 107, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which", "fcntl", "os", "l_flags", "file", "cur_fl", "arg"};
      flags$8 = Py.newCode(2, var2, var1, "flags", 116, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "how", "args", "struct", "fcntl", "l_type", "cmd", "l_whence", "l_start", "l_len", "sys", "os", "flock", "l_pid", "l_sysid", "l_vfs"};
      lock$9 = Py.newCode(3, var2, var1, "lock", 153, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "mode", "bufsize"};
      open$10 = Py.newCode(3, var2, var1, "open", 220, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file"};
      fileopen$11 = Py.newCode(1, var2, var1, "fileopen", 224, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new posixfile$py("posixfile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(posixfile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._posixfile_$1(var2, var3);
         case 2:
            return this.__repr__$2(var2, var3);
         case 3:
            return this.open$3(var2, var3);
         case 4:
            return this.fileopen$4(var2, var3);
         case 5:
            return this.file$5(var2, var3);
         case 6:
            return this.dup$6(var2, var3);
         case 7:
            return this.dup2$7(var2, var3);
         case 8:
            return this.flags$8(var2, var3);
         case 9:
            return this.lock$9(var2, var3);
         case 10:
            return this.open$10(var2, var3);
         case 11:
            return this.fileopen$11(var2, var3);
         default:
            return null;
      }
   }
}

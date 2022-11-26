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
@Filename("genericpath.py")
public class genericpath$py extends PyFunctionTable implements PyRunnable {
   static genericpath$py self;
   static final PyCode f$0;
   static final PyCode exists$1;
   static final PyCode isfile$2;
   static final PyCode isdir$3;
   static final PyCode getsize$4;
   static final PyCode getmtime$5;
   static final PyCode getatime$6;
   static final PyCode getctime$7;
   static final PyCode commonprefix$8;
   static final PyCode _splitext$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nPath operations common to more than one OS\nDo not use directly.  The OS specific modules import the appropriate\nfunctions from this module themselves.\n"));
      var1.setline(5);
      PyString.fromInterned("\nPath operations common to more than one OS\nDo not use directly.  The OS specific modules import the appropriate\nfunctions from this module themselves.\n");
      var1.setline(6);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(9);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("commonprefix"), PyString.fromInterned("exists"), PyString.fromInterned("getatime"), PyString.fromInterned("getctime"), PyString.fromInterned("getmtime"), PyString.fromInterned("getsize"), PyString.fromInterned("isdir"), PyString.fromInterned("isfile")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(15);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, exists$1, PyString.fromInterned("Test whether a path exists.  Returns False for broken symbolic links"));
      var1.setlocal("exists", var6);
      var3 = null;
      var1.setline(26);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, isfile$2, PyString.fromInterned("Test whether a path is a regular file"));
      var1.setlocal("isfile", var6);
      var3 = null;
      var1.setline(38);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, isdir$3, PyString.fromInterned("Return true if the pathname refers to an existing directory."));
      var1.setlocal("isdir", var6);
      var3 = null;
      var1.setline(47);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getsize$4, PyString.fromInterned("Return the size of a file, reported by os.stat()."));
      var1.setlocal("getsize", var6);
      var3 = null;
      var1.setline(52);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getmtime$5, PyString.fromInterned("Return the last modification time of a file, reported by os.stat()."));
      var1.setlocal("getmtime", var6);
      var3 = null;
      var1.setline(57);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getatime$6, PyString.fromInterned("Return the last access time of a file, reported by os.stat()."));
      var1.setlocal("getatime", var6);
      var3 = null;
      var1.setline(62);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getctime$7, PyString.fromInterned("Return the metadata change time of a file, reported by os.stat()."));
      var1.setlocal("getctime", var6);
      var3 = null;
      var1.setline(68);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, commonprefix$8, PyString.fromInterned("Given a list of pathnames, returns the longest common leading component"));
      var1.setlocal("commonprefix", var6);
      var3 = null;
      var1.setline(85);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _splitext$9, PyString.fromInterned("Split the extension from a pathname.\n\n    Extension is everything from the last dot to the end, ignoring\n    leading dots.  Returns \"(root, ext)\"; ext may be empty."));
      var1.setlocal("_splitext", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exists$1(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyString.fromInterned("Test whether a path exists.  Returns False for broken symbolic links");

      PyObject var4;
      try {
         var1.setline(18);
         var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(20);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(21);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject isfile$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Test whether a path is a regular file");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(29);
         PyObject var6 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(31);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(32);
      var4 = var1.getglobal("stat").__getattr__("S_ISREG").__call__(var2, var1.getlocal(1).__getattr__("st_mode"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject isdir$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Return true if the pathname refers to an existing directory.");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(41);
         PyObject var6 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(43);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(44);
      var4 = var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(1).__getattr__("st_mode"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getsize$4(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyString.fromInterned("Return the size of a file, reported by os.stat().");
      var1.setline(49);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_size");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmtime$5(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("Return the last modification time of a file, reported by os.stat().");
      var1.setline(54);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_mtime");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getatime$6(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Return the last access time of a file, reported by os.stat().");
      var1.setline(59);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_atime");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getctime$7(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("Return the metadata change time of a file, reported by os.stat().");
      var1.setline(64);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_ctime");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject commonprefix$8(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Given a list of pathnames, returns the longest common leading component");
      var1.setline(70);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(70);
         PyString var8 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(71);
         PyObject var4 = var1.getglobal("min").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(72);
         var4 = var1.getglobal("max").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(73);
         var4 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

         PyObject var10000;
         PyObject var3;
         do {
            var1.setline(73);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(76);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(74);
            PyObject var9 = var1.getlocal(4);
            var10000 = var9._ne(var1.getlocal(2).__getitem__(var1.getlocal(3)));
            var6 = null;
         } while(!var10000.__nonzero__());

         var1.setline(75);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _splitext$9(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Split the extension from a pathname.\n\n    Extension is everything from the last dot to the end, ignoring\n    leading dots.  Returns \"(root, ext)\"; ext may be empty.");
      var1.setline(91);
      PyObject var3 = var1.getlocal(0).__getattr__("rfind").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(92);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(93);
         var3 = var1.getlocal(0).__getattr__("rfind").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(94);
         var3 = var1.getglobal("max").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(96);
      var3 = var1.getlocal(0).__getattr__("rfind").__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(97);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._gt(var1.getlocal(4));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(99);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(7, var3);
         var3 = null;

         while(true) {
            var1.setline(100);
            PyObject var4 = var1.getlocal(7);
            var10000 = var4._lt(var1.getlocal(6));
            var4 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(101);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(7));
            var10000 = var3._ne(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(102);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null)});
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(103);
            var4 = var1.getlocal(7);
            var4 = var4._iadd(Py.newInteger(1));
            var1.setlocal(7, var4);
         }
      }

      var1.setline(105);
      var5 = new PyTuple(new PyObject[]{var1.getlocal(0), PyString.fromInterned("")});
      var1.f_lasti = -1;
      return var5;
   }

   public genericpath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"path"};
      exists$1 = Py.newCode(1, var2, var1, "exists", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "st"};
      isfile$2 = Py.newCode(1, var2, var1, "isfile", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "st"};
      isdir$3 = Py.newCode(1, var2, var1, "isdir", 38, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename"};
      getsize$4 = Py.newCode(1, var2, var1, "getsize", 47, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename"};
      getmtime$5 = Py.newCode(1, var2, var1, "getmtime", 52, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename"};
      getatime$6 = Py.newCode(1, var2, var1, "getatime", 57, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename"};
      getctime$7 = Py.newCode(1, var2, var1, "getctime", 62, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m", "s1", "s2", "i", "c"};
      commonprefix$8 = Py.newCode(1, var2, var1, "commonprefix", 68, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "sep", "altsep", "extsep", "sepIndex", "altsepIndex", "dotIndex", "filenameIndex"};
      _splitext$9 = Py.newCode(4, var2, var1, "_splitext", 85, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new genericpath$py("genericpath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(genericpath$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.exists$1(var2, var3);
         case 2:
            return this.isfile$2(var2, var3);
         case 3:
            return this.isdir$3(var2, var3);
         case 4:
            return this.getsize$4(var2, var3);
         case 5:
            return this.getmtime$5(var2, var3);
         case 6:
            return this.getatime$6(var2, var3);
         case 7:
            return this.getctime$7(var2, var3);
         case 8:
            return this.commonprefix$8(var2, var3);
         case 9:
            return this._splitext$9(var2, var3);
         default:
            return null;
      }
   }
}

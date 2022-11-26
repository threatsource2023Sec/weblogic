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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("stat.py")
public class stat$py extends PyFunctionTable implements PyRunnable {
   static stat$py self;
   static final PyCode f$0;
   static final PyCode S_IMODE$1;
   static final PyCode S_IFMT$2;
   static final PyCode S_ISDIR$3;
   static final PyCode S_ISCHR$4;
   static final PyCode S_ISBLK$5;
   static final PyCode S_ISREG$6;
   static final PyCode S_ISFIFO$7;
   static final PyCode S_ISLNK$8;
   static final PyCode S_ISSOCK$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Constants/functions for interpreting results of os.stat() and os.lstat().\n\nSuggested usage: from stat import *\n"));
      var1.setline(4);
      PyString.fromInterned("Constants/functions for interpreting results of os.stat() and os.lstat().\n\nSuggested usage: from stat import *\n");
      var1.setline(8);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("ST_MODE", var3);
      var3 = null;
      var1.setline(9);
      var3 = Py.newInteger(1);
      var1.setlocal("ST_INO", var3);
      var3 = null;
      var1.setline(10);
      var3 = Py.newInteger(2);
      var1.setlocal("ST_DEV", var3);
      var3 = null;
      var1.setline(11);
      var3 = Py.newInteger(3);
      var1.setlocal("ST_NLINK", var3);
      var3 = null;
      var1.setline(12);
      var3 = Py.newInteger(4);
      var1.setlocal("ST_UID", var3);
      var3 = null;
      var1.setline(13);
      var3 = Py.newInteger(5);
      var1.setlocal("ST_GID", var3);
      var3 = null;
      var1.setline(14);
      var3 = Py.newInteger(6);
      var1.setlocal("ST_SIZE", var3);
      var3 = null;
      var1.setline(15);
      var3 = Py.newInteger(7);
      var1.setlocal("ST_ATIME", var3);
      var3 = null;
      var1.setline(16);
      var3 = Py.newInteger(8);
      var1.setlocal("ST_MTIME", var3);
      var3 = null;
      var1.setline(17);
      var3 = Py.newInteger(9);
      var1.setlocal("ST_CTIME", var3);
      var3 = null;
      var1.setline(21);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, S_IMODE$1, (PyObject)null);
      var1.setlocal("S_IMODE", var5);
      var3 = null;
      var1.setline(24);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_IFMT$2, (PyObject)null);
      var1.setlocal("S_IFMT", var5);
      var3 = null;
      var1.setline(30);
      var3 = Py.newInteger(16384);
      var1.setlocal("S_IFDIR", var3);
      var3 = null;
      var1.setline(31);
      var3 = Py.newInteger(8192);
      var1.setlocal("S_IFCHR", var3);
      var3 = null;
      var1.setline(32);
      var3 = Py.newInteger(24576);
      var1.setlocal("S_IFBLK", var3);
      var3 = null;
      var1.setline(33);
      var3 = Py.newInteger(32768);
      var1.setlocal("S_IFREG", var3);
      var3 = null;
      var1.setline(34);
      var3 = Py.newInteger(4096);
      var1.setlocal("S_IFIFO", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(40960);
      var1.setlocal("S_IFLNK", var3);
      var3 = null;
      var1.setline(36);
      var3 = Py.newInteger(49152);
      var1.setlocal("S_IFSOCK", var3);
      var3 = null;
      var1.setline(40);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISDIR$3, (PyObject)null);
      var1.setlocal("S_ISDIR", var5);
      var3 = null;
      var1.setline(43);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISCHR$4, (PyObject)null);
      var1.setlocal("S_ISCHR", var5);
      var3 = null;
      var1.setline(46);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISBLK$5, (PyObject)null);
      var1.setlocal("S_ISBLK", var5);
      var3 = null;
      var1.setline(49);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISREG$6, (PyObject)null);
      var1.setlocal("S_ISREG", var5);
      var3 = null;
      var1.setline(52);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISFIFO$7, (PyObject)null);
      var1.setlocal("S_ISFIFO", var5);
      var3 = null;
      var1.setline(55);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISLNK$8, (PyObject)null);
      var1.setlocal("S_ISLNK", var5);
      var3 = null;
      var1.setline(58);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, S_ISSOCK$9, (PyObject)null);
      var1.setlocal("S_ISSOCK", var5);
      var3 = null;
      var1.setline(63);
      var3 = Py.newInteger(2048);
      var1.setlocal("S_ISUID", var3);
      var3 = null;
      var1.setline(64);
      var3 = Py.newInteger(1024);
      var1.setlocal("S_ISGID", var3);
      var3 = null;
      var1.setline(65);
      PyObject var6 = var1.getname("S_ISGID");
      var1.setlocal("S_ENFMT", var6);
      var3 = null;
      var1.setline(66);
      var3 = Py.newInteger(512);
      var1.setlocal("S_ISVTX", var3);
      var3 = null;
      var1.setline(67);
      var3 = Py.newInteger(256);
      var1.setlocal("S_IREAD", var3);
      var3 = null;
      var1.setline(68);
      var3 = Py.newInteger(128);
      var1.setlocal("S_IWRITE", var3);
      var3 = null;
      var1.setline(69);
      var3 = Py.newInteger(64);
      var1.setlocal("S_IEXEC", var3);
      var3 = null;
      var1.setline(70);
      var3 = Py.newInteger(448);
      var1.setlocal("S_IRWXU", var3);
      var3 = null;
      var1.setline(71);
      var3 = Py.newInteger(256);
      var1.setlocal("S_IRUSR", var3);
      var3 = null;
      var1.setline(72);
      var3 = Py.newInteger(128);
      var1.setlocal("S_IWUSR", var3);
      var3 = null;
      var1.setline(73);
      var3 = Py.newInteger(64);
      var1.setlocal("S_IXUSR", var3);
      var3 = null;
      var1.setline(74);
      var3 = Py.newInteger(56);
      var1.setlocal("S_IRWXG", var3);
      var3 = null;
      var1.setline(75);
      var3 = Py.newInteger(32);
      var1.setlocal("S_IRGRP", var3);
      var3 = null;
      var1.setline(76);
      var3 = Py.newInteger(16);
      var1.setlocal("S_IWGRP", var3);
      var3 = null;
      var1.setline(77);
      var3 = Py.newInteger(8);
      var1.setlocal("S_IXGRP", var3);
      var3 = null;
      var1.setline(78);
      var3 = Py.newInteger(7);
      var1.setlocal("S_IRWXO", var3);
      var3 = null;
      var1.setline(79);
      var3 = Py.newInteger(4);
      var1.setlocal("S_IROTH", var3);
      var3 = null;
      var1.setline(80);
      var3 = Py.newInteger(2);
      var1.setlocal("S_IWOTH", var3);
      var3 = null;
      var1.setline(81);
      var3 = Py.newInteger(1);
      var1.setlocal("S_IXOTH", var3);
      var3 = null;
      var1.setline(85);
      var3 = Py.newInteger(1);
      var1.setlocal("UF_NODUMP", var3);
      var3 = null;
      var1.setline(86);
      var3 = Py.newInteger(2);
      var1.setlocal("UF_IMMUTABLE", var3);
      var3 = null;
      var1.setline(87);
      var3 = Py.newInteger(4);
      var1.setlocal("UF_APPEND", var3);
      var3 = null;
      var1.setline(88);
      var3 = Py.newInteger(8);
      var1.setlocal("UF_OPAQUE", var3);
      var3 = null;
      var1.setline(89);
      var3 = Py.newInteger(16);
      var1.setlocal("UF_NOUNLINK", var3);
      var3 = null;
      var1.setline(90);
      var3 = Py.newInteger(32);
      var1.setlocal("UF_COMPRESSED", var3);
      var3 = null;
      var1.setline(91);
      var3 = Py.newInteger(32768);
      var1.setlocal("UF_HIDDEN", var3);
      var3 = null;
      var1.setline(92);
      var3 = Py.newInteger(65536);
      var1.setlocal("SF_ARCHIVED", var3);
      var3 = null;
      var1.setline(93);
      var3 = Py.newInteger(131072);
      var1.setlocal("SF_IMMUTABLE", var3);
      var3 = null;
      var1.setline(94);
      var3 = Py.newInteger(262144);
      var1.setlocal("SF_APPEND", var3);
      var3 = null;
      var1.setline(95);
      var3 = Py.newInteger(1048576);
      var1.setlocal("SF_NOUNLINK", var3);
      var3 = null;
      var1.setline(96);
      var3 = Py.newInteger(2097152);
      var1.setlocal("SF_SNAPSHOT", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject S_IMODE$1(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(0)._and(Py.newInteger(4095));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_IFMT$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getlocal(0)._and(Py.newInteger(61440));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISDIR$3(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFDIR"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISCHR$4(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFCHR"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISBLK$5(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFBLK"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISREG$6(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFREG"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISFIFO$7(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFIFO"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISLNK$8(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFLNK"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject S_ISSOCK$9(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("S_IFMT").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("S_IFSOCK"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public stat$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"mode"};
      S_IMODE$1 = Py.newCode(1, var2, var1, "S_IMODE", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_IFMT$2 = Py.newCode(1, var2, var1, "S_IFMT", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISDIR$3 = Py.newCode(1, var2, var1, "S_ISDIR", 40, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISCHR$4 = Py.newCode(1, var2, var1, "S_ISCHR", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISBLK$5 = Py.newCode(1, var2, var1, "S_ISBLK", 46, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISREG$6 = Py.newCode(1, var2, var1, "S_ISREG", 49, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISFIFO$7 = Py.newCode(1, var2, var1, "S_ISFIFO", 52, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISLNK$8 = Py.newCode(1, var2, var1, "S_ISLNK", 55, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode"};
      S_ISSOCK$9 = Py.newCode(1, var2, var1, "S_ISSOCK", 58, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new stat$py("stat$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(stat$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.S_IMODE$1(var2, var3);
         case 2:
            return this.S_IFMT$2(var2, var3);
         case 3:
            return this.S_ISDIR$3(var2, var3);
         case 4:
            return this.S_ISCHR$4(var2, var3);
         case 5:
            return this.S_ISBLK$5(var2, var3);
         case 6:
            return this.S_ISREG$6(var2, var3);
         case 7:
            return this.S_ISFIFO$7(var2, var3);
         case 8:
            return this.S_ISLNK$8(var2, var3);
         case 9:
            return this.S_ISSOCK$9(var2, var3);
         default:
            return null;
      }
   }
}

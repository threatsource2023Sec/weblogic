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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("anydbm.py")
public class anydbm$py extends PyFunctionTable implements PyRunnable {
   static anydbm$py self;
   static final PyCode f$0;
   static final PyCode error$1;
   static final PyCode open$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generic interface to all dbm clones.\n\nInstead of\n\n        import dbm\n        d = dbm.open(file, 'w', 0666)\n\nuse\n\n        import anydbm\n        d = anydbm.open(file, 'w')\n\nThe returned object is a dbhash, gdbm, dbm or dumbdbm object,\ndependent on the type of database being opened (determined by whichdb\nmodule) in the case of an existing dbm. If the dbm does not exist and\nthe create or new flag ('c' or 'n') was specified, the dbm type will\nbe determined by the availability of the modules (tested in the above\norder).\n\nIt has the following interface (key and data are strings):\n\n        d[key] = data   # store data at key (may override data at\n                        # existing key)\n        data = d[key]   # retrieve data at key (raise KeyError if no\n                        # such key)\n        del d[key]      # delete data stored at key (raises KeyError\n                        # if no such key)\n        flag = key in d   # true if the key exists\n        list = d.keys() # return a list of all existing keys (slow!)\n\nFuture versions may change the order in which implementations are\ntested for existence, and add interfaces to other dbm-like\nimplementations.\n"));
      var1.setline(34);
      PyString.fromInterned("Generic interface to all dbm clones.\n\nInstead of\n\n        import dbm\n        d = dbm.open(file, 'w', 0666)\n\nuse\n\n        import anydbm\n        d = anydbm.open(file, 'w')\n\nThe returned object is a dbhash, gdbm, dbm or dumbdbm object,\ndependent on the type of database being opened (determined by whichdb\nmodule) in the case of an existing dbm. If the dbm does not exist and\nthe create or new flag ('c' or 'n') was specified, the dbm type will\nbe determined by the availability of the modules (tested in the above\norder).\n\nIt has the following interface (key and data are strings):\n\n        d[key] = data   # store data at key (may override data at\n                        # existing key)\n        data = d[key]   # retrieve data at key (raise KeyError if no\n                        # such key)\n        del d[key]      # delete data stored at key (raises KeyError\n                        # if no such key)\n        flag = key in d   # true if the key exists\n        list = d.keys() # return a list of all existing keys (slow!)\n\nFuture versions may change the order in which implementations are\ntested for existence, and add interfaces to other dbm-like\nimplementations.\n");
      var1.setline(36);
      PyObject[] var3 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("error", var3, error$1);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(39);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("dbhash"), PyString.fromInterned("gdbm"), PyString.fromInterned("dbm"), PyString.fromInterned("dumbdbm")});
      var1.setlocal("_names", var7);
      var3 = null;
      var1.setline(40);
      var7 = new PyList(new PyObject[]{var1.getname("error")});
      var1.setlocal("_errors", var7);
      var3 = null;
      var1.setline(41);
      PyObject var8 = var1.getname("None");
      var1.setlocal("_defaultmod", var8);
      var3 = null;
      var1.setline(43);
      var8 = var1.getname("_names").__iter__();

      while(true) {
         var1.setline(43);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(52);
            if (var1.getname("_defaultmod").__not__().__nonzero__()) {
               var1.setline(53);
               throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("no dbm clone found; tried %s")._mod(var1.getname("_names")));
            } else {
               var1.setline(55);
               var8 = var1.getname("tuple").__call__(var2, var1.getname("_errors"));
               var1.setlocal("error", var8);
               var3 = null;
               var1.setline(57);
               var3 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(438)};
               PyFunction var10 = new PyFunction(var1.f_globals, var3, open$2, PyString.fromInterned("Open or create database at path given by *file*.\n\n    Optional argument *flag* can be 'r' (default) for read-only access, 'w'\n    for read-write access of an existing database, 'c' for read-write access\n    to a new or existing database, and 'n' for read-write access to a new\n    database.\n\n    Note: 'r' and 'w' fail if the database doesn't exist; 'c' creates it\n    only if it doesn't exist; and 'n' always creates a new database.\n    "));
               var1.setlocal("open", var10);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setlocal("_name", var4);

         PyException var5;
         PyObject var9;
         try {
            var1.setline(45);
            var9 = var1.getname("__import__").__call__(var2, var1.getname("_name"));
            var1.setlocal("_mod", var9);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getname("ImportError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(48);
         if (var1.getname("_defaultmod").__not__().__nonzero__()) {
            var1.setline(49);
            var9 = var1.getname("_mod");
            var1.setlocal("_defaultmod", var9);
            var5 = null;
         }

         var1.setline(50);
         var1.getname("_errors").__getattr__("append").__call__(var2, var1.getname("_mod").__getattr__("error"));
      }
   }

   public PyObject error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      return var1.getf_locals();
   }

   public PyObject open$2(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Open or create database at path given by *file*.\n\n    Optional argument *flag* can be 'r' (default) for read-only access, 'w'\n    for read-write access of an existing database, 'c' for read-write access\n    to a new or existing database, and 'n' for read-write access to a new\n    database.\n\n    Note: 'r' and 'w' fail if the database doesn't exist; 'c' creates it\n    only if it doesn't exist; and 'n' always creates a new database.\n    ");
      var1.setline(70);
      String[] var3 = new String[]{"whichdb"};
      PyObject[] var5 = imp.importFrom("whichdb", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(71);
      PyObject var6 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(72);
      var6 = var1.getlocal(4);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(74);
         PyString var7 = PyString.fromInterned("c");
         var10000 = var7._in(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var7 = PyString.fromInterned("n");
            var10000 = var7._in(var1.getlocal(1));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(79);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("need 'c' or 'n' flag to open new db"));
         }

         var1.setline(77);
         var6 = var1.getglobal("_defaultmod");
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(80);
         var6 = var1.getlocal(4);
         var10000 = var6._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(82);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("db type could not be determined"));
         }

         var1.setline(84);
         var6 = var1.getglobal("__import__").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var6);
         var3 = null;
      }

      var1.setline(85);
      var6 = var1.getlocal(5).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var6;
   }

   public anydbm$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error$1 = Py.newCode(0, var2, var1, "error", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "flag", "mode", "whichdb", "result", "mod"};
      open$2 = Py.newCode(3, var2, var1, "open", 57, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new anydbm$py("anydbm$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(anydbm$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.error$1(var2, var3);
         case 2:
            return this.open$2(var2, var3);
         default:
            return null;
      }
   }
}

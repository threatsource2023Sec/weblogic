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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("whichdb.py")
public class whichdb$py extends PyFunctionTable implements PyRunnable {
   static whichdb$py self;
   static final PyCode f$0;
   static final PyCode whichdb$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Guess which db package to use to open a db file."));
      var1.setline(2);
      PyString.fromInterned("Guess which db package to use to open a db file.");
      var1.setline(4);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(9);
         var3 = imp.importOne("dbm", var1, -1);
         var1.setlocal("dbm", var3);
         var3 = null;
         var1.setline(10);
         var3 = var1.getname("dbm").__getattr__("error");
         var1.setlocal("_dbmerror", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getname("ImportError"))) {
            throw var6;
         }

         var1.setline(12);
         var4 = var1.getname("None");
         var1.setlocal("dbm", var4);
         var4 = null;
         var1.setline(15);
         var4 = var1.getname("IOError");
         var1.setlocal("_dbmerror", var4);
         var4 = null;
      }

      var1.setline(17);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, whichdb$1, PyString.fromInterned("Guess which db package to use to open a db file.\n\n    Return values:\n\n    - None if the database file can't be read;\n    - empty string if the file can be read but can't be recognized\n    - the module name (e.g. \"dbm\" or \"gdbm\") if recognized.\n\n    Importing the given module may still fail, and opening the\n    database using that module may still fail.\n    "));
      var1.setlocal("whichdb", var8);
      var3 = null;
      var1.setline(115);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(116);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal("filename", var4);
            var1.setline(117);
            Object var9 = var1.getname("whichdb").__call__(var2, var1.getname("filename"));
            if (!((PyObject)var9).__nonzero__()) {
               var9 = PyString.fromInterned("UNKNOWN");
            }

            Py.printComma((PyObject)var9);
            Py.println(var1.getname("filename"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject whichdb$1(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public whichdb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "f", "d", "size", "s16", "s", "magic"};
      whichdb$1 = Py.newCode(1, var2, var1, "whichdb", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new whichdb$py("whichdb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(whichdb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.whichdb$1(var2, var3);
         default:
            return null;
      }
   }
}

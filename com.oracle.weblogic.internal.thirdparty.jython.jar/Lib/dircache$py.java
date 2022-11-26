import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("dircache.py")
public class dircache$py extends PyFunctionTable implements PyRunnable {
   static dircache$py self;
   static final PyCode f$0;
   static final PyCode reset$1;
   static final PyCode listdir$2;
   static final PyCode annotate$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Read and cache directory listings.\n\nThe listdir() routine returns a sorted list of the files in a directory,\nusing a cache to avoid reading the directory more often than necessary.\nThe annotate() routine appends slashes to directories."));
      var1.setline(5);
      PyString.fromInterned("Read and cache directory listings.\n\nThe listdir() routine returns a sorted list of the files in a directory,\nusing a cache to avoid reading the directory more often than necessary.\nThe annotate() routine appends slashes to directories.");
      var1.setline(6);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(7);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the dircache module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(8);
      var1.dellocal("warnpy3k");
      var1.setline(10);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(12);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("listdir"), PyString.fromInterned("opendir"), PyString.fromInterned("annotate"), PyString.fromInterned("reset")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(14);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("cache", var9);
      var3 = null;
      var1.setline(16);
      var5 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var5, reset$1, PyString.fromInterned("Reset the cache completely."));
      var1.setlocal("reset", var10);
      var3 = null;
      var1.setline(21);
      var5 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var5, listdir$2, PyString.fromInterned("List directory contents, using cache."));
      var1.setlocal("listdir", var10);
      var3 = null;
      var1.setline(35);
      var6 = var1.getname("listdir");
      var1.setlocal("opendir", var6);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var5, annotate$3, PyString.fromInterned("Add '/' suffixes to directories."));
      var1.setlocal("annotate", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$1(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyString.fromInterned("Reset the cache completely.");
      var1.setline(19);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setglobal("cache", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listdir$2(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyString.fromInterned("List directory contents, using cache.");

      PyException var3;
      PyObject[] var5;
      PyObject var8;
      try {
         var1.setline(24);
         var8 = var1.getglobal("cache").__getitem__(var1.getlocal(0));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var10 = var9[0];
         var1.setlocal(1, var10);
         var5 = null;
         var10 = var9[1];
         var1.setlocal(2, var10);
         var5 = null;
         var3 = null;
         var1.setline(25);
         var1.getglobal("cache").__delitem__(var1.getlocal(0));
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(27);
         PyTuple var4 = new PyTuple(new PyObject[]{Py.newInteger(-1), new PyList(Py.EmptyObjects)});
         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(28);
      var8 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_mtime");
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(29);
      var8 = var1.getlocal(3);
      PyObject var10000 = var8._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(31);
         var1.getlocal(2).__getattr__("sort").__call__(var2);
      }

      var1.setline(32);
      PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)});
      var1.getglobal("cache").__setitem__((PyObject)var1.getlocal(0), var11);
      var3 = null;
      var1.setline(33);
      var8 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject annotate$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("Add '/' suffixes to directories.");
      var1.setline(39);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(39);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(40);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getitem__(var1.getlocal(2)))).__nonzero__()) {
            var1.setline(41);
            PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(2))._add(PyString.fromInterned("/"));
            var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
            var5 = null;
         }
      }
   }

   public dircache$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      reset$1 = Py.newCode(0, var2, var1, "reset", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "cached_mtime", "list", "mtime"};
      listdir$2 = Py.newCode(1, var2, var1, "listdir", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"head", "list", "i"};
      annotate$3 = Py.newCode(2, var2, var1, "annotate", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dircache$py("dircache$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dircache$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.reset$1(var2, var3);
         case 2:
            return this.listdir$2(var2, var3);
         case 3:
            return this.annotate$3(var2, var3);
         default:
            return null;
      }
   }
}

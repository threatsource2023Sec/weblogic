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
@Filename("glob.py")
public class glob$py extends PyFunctionTable implements PyRunnable {
   static glob$py self;
   static final PyCode f$0;
   static final PyCode _unicode$1;
   static final PyCode glob$2;
   static final PyCode iglob$3;
   static final PyCode glob1$4;
   static final PyCode f$5;
   static final PyCode glob0$6;
   static final PyCode has_magic$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Filename globbing utility."));
      var1.setline(1);
      PyString.fromInterned("Filename globbing utility.");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("fnmatch", var1, -1);
      var1.setlocal("fnmatch", var3);
      var3 = null;

      try {
         var1.setline(9);
         var3 = var1.getname("unicode");
         var1.setlocal("_unicode", var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("NameError"))) {
            throw var7;
         }

         var1.setline(13);
         PyObject[] var4 = new PyObject[]{var1.getname("object")};
         PyObject var5 = Py.makeClass("_unicode", var4, _unicode$1);
         var1.setlocal("_unicode", var5);
         var5 = null;
         Arrays.fill(var4, (Object)null);
      }

      var1.setline(16);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("glob"), PyString.fromInterned("iglob")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(18);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, glob$2, PyString.fromInterned("Return a list of paths matching a pathname pattern.\n\n    The pattern may contain simple shell-style wildcards a la\n    fnmatch. However, unlike fnmatch, filenames starting with a\n    dot are special cases that are not matched by '*' and '?'\n    patterns.\n\n    "));
      var1.setlocal("glob", var10);
      var3 = null;
      var1.setline(29);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, iglob$3, PyString.fromInterned("Return an iterator which yields the paths matching a pathname pattern.\n\n    The pattern may contain simple shell-style wildcards a la\n    fnmatch. However, unlike fnmatch, filenames starting with a\n    dot are special cases that are not matched by '*' and '?'\n    patterns.\n\n    "));
      var1.setlocal("iglob", var10);
      var3 = null;
      var1.setline(66);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, glob1$4, (PyObject)null);
      var1.setlocal("glob1", var10);
      var3 = null;
      var1.setline(80);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, glob0$6, (PyObject)null);
      var1.setlocal("glob0", var10);
      var3 = null;
      var1.setline(92);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[*?[]"));
      var1.setlocal("magic_check", var3);
      var3 = null;
      var1.setline(94);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, has_magic$7, (PyObject)null);
      var1.setlocal("has_magic", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      return var1.getf_locals();
   }

   public PyObject glob$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyString.fromInterned("Return a list of paths matching a pathname pattern.\n\n    The pattern may contain simple shell-style wildcards a la\n    fnmatch. However, unlike fnmatch, filenames starting with a\n    dot are special cases that are not matched by '*' and '?'\n    patterns.\n\n    ");
      var1.setline(27);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getglobal("iglob").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iglob$3(PyFrame var1, ThreadState var2) {
      label77: {
         PyObject var3;
         PyObject var4;
         Object[] var10;
         PyObject var13;
         label74: {
            PyObject var5;
            PyObject var6;
            Object[] var7;
            Object[] var8;
            Object var10000;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(37);
                  PyString.fromInterned("Return an iterator which yields the paths matching a pathname pattern.\n\n    The pattern may contain simple shell-style wildcards a la\n    fnmatch. However, unlike fnmatch, filenames starting with a\n    dot are special cases that are not matched by '*' and '?'\n    patterns.\n\n    ");
                  var1.setline(38);
                  if (var1.getglobal("has_magic").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
                     var1.setline(39);
                     if (var1.getglobal("os").__getattr__("path").__getattr__("lexists").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                        var1.setline(40);
                        var1.setline(40);
                        var13 = var1.getlocal(0);
                        var1.f_lasti = 1;
                        var8 = new Object[3];
                        var1.f_savedlocals = var8;
                        return var13;
                     }
                     break label77;
                  }

                  var1.setline(42);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
                  PyObject[] var9 = Py.unpackSequence(var3, 2);
                  var5 = var9[0];
                  var1.setlocal(1, var5);
                  var5 = null;
                  var5 = var9[1];
                  var1.setlocal(2, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(43);
                  if (var1.getlocal(1).__not__().__nonzero__()) {
                     var1.setline(44);
                     var3 = var1.getglobal("glob1").__call__(var2, var1.getglobal("os").__getattr__("curdir"), var1.getlocal(2)).__iter__();
                     break label74;
                  }

                  var1.setline(50);
                  var3 = var1.getlocal(1);
                  var13 = var3._ne(var1.getlocal(0));
                  var3 = null;
                  if (var13.__nonzero__()) {
                     var13 = var1.getglobal("has_magic").__call__(var2, var1.getlocal(1));
                  }

                  if (var13.__nonzero__()) {
                     var1.setline(51);
                     var3 = var1.getglobal("iglob").__call__(var2, var1.getlocal(1));
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(53);
                     PyObject[] var11 = new PyObject[]{var1.getlocal(1)};
                     PyList var14 = new PyList(var11);
                     Arrays.fill(var11, (Object)null);
                     PyList var12 = var14;
                     var1.setlocal(4, var12);
                     var3 = null;
                  }

                  var1.setline(54);
                  if (var1.getglobal("has_magic").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                     var1.setline(55);
                     var3 = var1.getglobal("glob1");
                     var1.setlocal(5, var3);
                     var3 = null;
                  } else {
                     var1.setline(57);
                     var3 = var1.getglobal("glob0");
                     var1.setlocal(5, var3);
                     var3 = null;
                  }

                  var1.setline(58);
                  var3 = var1.getlocal(4).__iter__();
                  var1.setline(58);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(1, var4);
                  var1.setline(59);
                  var5 = var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();
                  break;
               case 1:
                  var8 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break label77;
               case 2:
                  var10 = var1.f_savedlocals;
                  var3 = (PyObject)var10[3];
                  var4 = (PyObject)var10[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break label74;
               case 3:
                  var7 = var1.f_savedlocals;
                  var3 = (PyObject)var7[3];
                  var4 = (PyObject)var7[4];
                  var5 = (PyObject)var7[5];
                  var6 = (PyObject)var7[6];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
            }

            while(true) {
               var1.setline(59);
               var6 = var5.__iternext__();
               if (var6 != null) {
                  var1.setlocal(3, var6);
                  var1.setline(60);
                  var1.setline(60);
                  var13 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                  var1.f_lasti = 3;
                  var7 = new Object[]{null, null, null, var3, var4, var5, var6};
                  var1.f_savedlocals = var7;
                  return var13;
               }

               var1.setline(58);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(59);
               var5 = var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();
            }
         }

         var1.setline(44);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(46);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(45);
         var1.setline(45);
         var13 = var1.getlocal(3);
         var1.f_lasti = 2;
         var10 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var10;
         return var13;
      }

      var1.setline(41);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject glob1$4(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(68);
         var3 = var1.getglobal("os").__getattr__("curdir");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(69);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_unicode"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(70);
         var10000 = var1.getglobal("unicode");
         PyObject var10002 = var1.getlocal(0);
         PyObject var10003 = var1.getglobal("sys").__getattr__("getfilesystemencoding").__call__(var2);
         if (!var10003.__nonzero__()) {
            var10003 = var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2);
         }

         var3 = var10000.__call__(var2, var10002, var10003);
         var1.setlocal(0, var3);
         var3 = null;
      }

      try {
         var1.setline(73);
         var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(75);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.f_lasti = -1;
            return var4;
         }

         throw var6;
      }

      var1.setline(76);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var10000 = var3._ne(PyString.fromInterned("."));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         var10000 = var1.getglobal("filter");
         var1.setline(77);
         PyObject[] var8 = Py.EmptyObjects;
         var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var8, f$5)), (PyObject)var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(78);
      PyObject var7 = var1.getglobal("fnmatch").__getattr__("filter").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(PyString.fromInterned("."));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject glob0$6(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      PyList var4;
      if (var10000.__nonzero__()) {
         var1.setline(84);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(85);
            var4 = new PyList(new PyObject[]{var1.getlocal(1)});
            var1.f_lasti = -1;
            return var4;
         }
      } else {
         var1.setline(87);
         if (var1.getglobal("os").__getattr__("path").__getattr__("lexists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1))).__nonzero__()) {
            var1.setline(88);
            var4 = new PyList(new PyObject[]{var1.getlocal(1)});
            var1.f_lasti = -1;
            return var4;
         }
      }

      var1.setline(89);
      var4 = new PyList(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject has_magic$7(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getglobal("magic_check").__getattr__("search").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public glob$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _unicode$1 = Py.newCode(0, var2, var1, "_unicode", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pathname"};
      glob$2 = Py.newCode(1, var2, var1, "glob", 18, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pathname", "dirname", "basename", "name", "dirs", "glob_in_dir"};
      iglob$3 = Py.newCode(1, var2, var1, "iglob", 29, false, false, self, 3, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"dirname", "pattern", "names"};
      glob1$4 = Py.newCode(2, var2, var1, "glob1", 66, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$5 = Py.newCode(1, var2, var1, "<lambda>", 77, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dirname", "basename"};
      glob0$6 = Py.newCode(2, var2, var1, "glob0", 80, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      has_magic$7 = Py.newCode(1, var2, var1, "has_magic", 94, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new glob$py("glob$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(glob$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._unicode$1(var2, var3);
         case 2:
            return this.glob$2(var2, var3);
         case 3:
            return this.iglob$3(var2, var3);
         case 4:
            return this.glob1$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.glob0$6(var2, var3);
         case 7:
            return this.has_magic$7(var2, var3);
         default:
            return null;
      }
   }
}

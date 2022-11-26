package lib2to3.fixes;

import java.util.Arrays;
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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_renames.py")
public class fix_renames$py extends PyFunctionTable implements PyRunnable {
   static fix_renames$py self;
   static final PyCode f$0;
   static final PyCode alternates$1;
   static final PyCode build_pattern$2;
   static final PyCode FixRenames$3;
   static final PyCode match$4;
   static final PyCode f$5;
   static final PyCode transform$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fix incompatible renames\n\nFixes:\n  * sys.maxint -> sys.maxsize\n"));
      var1.setline(5);
      PyString.fromInterned("Fix incompatible renames\n\nFixes:\n  * sys.maxint -> sys.maxsize\n");
      var1.setline(10);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"Name", "attr_chain"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("attr_chain", var4);
      var4 = null;
      var1.setline(13);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("sys"), new PyDictionary(new PyObject[]{PyString.fromInterned("maxint"), PyString.fromInterned("maxsize")})});
      var1.setlocal("MAPPING", var6);
      var3 = null;
      var1.setline(15);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("LOOKUP", var6);
      var3 = null;
      var1.setline(17);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, alternates$1, (PyObject)null);
      var1.setlocal("alternates", var7);
      var3 = null;
      var1.setline(21);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, build_pattern$2, (PyObject)null);
      var1.setlocal("build_pattern", var7);
      var3 = null;
      var1.setline(42);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixRenames", var5, FixRenames$3);
      var1.setlocal("FixRenames", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject alternates$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = PyString.fromInterned("(")._add(PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(0))))._add(PyString.fromInterned(")"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject build_pattern$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject[] var9;
      PyObject[] var10;
      PyObject var13;
      PyString var14;
      Object var10000;
      PyTuple var10001;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(23);
            var3 = var1.getglobal("MAPPING").__getattr__("items").__call__(var2).__iter__();
            var1.setline(23);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var9 = Py.unpackSequence(var4, 2);
            var6 = var9[0];
            var1.setlocal(0, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(1, var6);
            var6 = null;
            var1.setline(24);
            var5 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var13 = (PyObject)var10000;
               var1.setline(36);
               var1.setline(36);
               var14 = PyString.fromInterned("\n                  power< module_name=%r trailer< '.' attr_name=%r > any* >\n                  ");
               var10 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
               var10001 = new PyTuple(var10);
               Arrays.fill(var10, (Object)null);
               var13 = var14._mod(var10001);
               var1.f_lasti = 2;
               var7 = new Object[9];
               var7[3] = var3;
               var7[4] = var4;
               var7[5] = var5;
               var7[6] = var6;
               var1.f_savedlocals = var7;
               return var13;
            }
         case 2:
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
         var1.setline(24);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var10 = Py.unpackSequence(var6, 2);
            PyObject var8 = var10[0];
            var1.setlocal(2, var8);
            var8 = null;
            var8 = var10[1];
            var1.setlocal(3, var8);
            var8 = null;
            var1.setline(25);
            PyObject var11 = var1.getlocal(3);
            var13 = var1.getglobal("LOOKUP");
            PyObject[] var12 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
            var10001 = new PyTuple(var12);
            Arrays.fill(var12, (Object)null);
            var13.__setitem__((PyObject)var10001, var11);
            var7 = null;
            var1.setline(32);
            var1.setline(32);
            var14 = PyString.fromInterned("\n                  import_from< 'from' module_name=%r 'import'\n                      ( attr_name=%r | import_as_name< attr_name=%r 'as' any >) >\n                  ");
            var10 = new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getlocal(2)};
            var10001 = new PyTuple(var10);
            Arrays.fill(var10, (Object)null);
            var13 = var14._mod(var10001);
            var1.f_lasti = 1;
            var7 = new Object[9];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var13;
         }

         var1.setline(23);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var9 = Py.unpackSequence(var4, 2);
         var6 = var9[0];
         var1.setlocal(0, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(1, var6);
         var6 = null;
         var1.setline(24);
         var5 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();
      }
   }

   public PyObject FixRenames$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(44);
      var3 = PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getname("build_pattern").__call__(var2));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(46);
      PyString var4 = PyString.fromInterned("pre");
      var1.setlocal("order", var4);
      var3 = null;
      var1.setline(49);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, match$4, (PyObject)null);
      var1.setlocal("match", var6);
      var3 = null;
      var1.setline(62);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$6, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject match$4(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("FixRenames"), var1.getlocal(0)).__getattr__("match");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getderef(0).__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(52);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(53);
         PyObject var10000 = var1.getglobal("any");
         var1.setline(53);
         PyObject var10004 = var1.f_globals;
         PyObject[] var6 = Py.EmptyObjects;
         PyCode var10006 = f$5;
         PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
         PyFunction var5 = new PyFunction(var10004, var6, var10006, (PyObject)null, var4);
         PyObject var10002 = var5.__call__(var2, var1.getglobal("attr_chain").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("parent")).__iter__());
         Arrays.fill(var6, (Object)null);
         if (var10000.__call__(var2, var10002).__nonzero__()) {
            var1.setline(54);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(55);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(56);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(53);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(53);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(53);
         var1.setline(53);
         var6 = var1.getderef(0).__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject transform$6(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module_name"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attr_name"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(68);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
      }

      if (var10000.__nonzero__()) {
         var1.setline(69);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getglobal("LOOKUP").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("value"), var1.getlocal(4).__getattr__("value")})));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(70);
         var10000 = var1.getlocal(4).__getattr__("replace");
         PyObject var10002 = var1.getglobal("Name");
         PyObject[] var5 = new PyObject[]{var1.getlocal(5), var1.getlocal(4).__getattr__("prefix")};
         String[] var4 = new String[]{"prefix"};
         var10002 = var10002.__call__(var2, var5, var4);
         var3 = null;
         var10000.__call__(var2, var10002);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_renames$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"members"};
      alternates$1 = Py.newCode(1, var2, var1, "alternates", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "replace", "old_attr", "new_attr"};
      build_pattern$2 = Py.newCode(0, var2, var1, "build_pattern", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      FixRenames$3 = Py.newCode(0, var2, var1, "FixRenames", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "_(53_19)", "match"};
      String[] var10001 = var2;
      fix_renames$py var10007 = self;
      var2 = new String[]{"match"};
      match$4 = Py.newCode(2, var10001, var1, "match", 49, false, false, var10007, 4, var2, (String[])null, 1, 4097);
      var2 = new String[]{"_(x)", "obj"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"match"};
      f$5 = Py.newCode(1, var10001, var1, "<genexpr>", 53, false, false, var10007, 5, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "node", "results", "mod_name", "attr_name", "new_attr"};
      transform$6 = Py.newCode(3, var2, var1, "transform", 62, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_renames$py("lib2to3/fixes/fix_renames$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_renames$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.alternates$1(var2, var3);
         case 2:
            return this.build_pattern$2(var2, var3);
         case 3:
            return this.FixRenames$3(var2, var3);
         case 4:
            return this.match$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.transform$6(var2, var3);
         default:
            return null;
      }
   }
}

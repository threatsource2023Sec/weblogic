package lib2to3.fixes;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_import.py")
public class fix_import$py extends PyFunctionTable implements PyRunnable {
   static fix_import$py self;
   static final PyCode f$0;
   static final PyCode traverse_imports$1;
   static final PyCode FixImport$2;
   static final PyCode start_tree$3;
   static final PyCode transform$4;
   static final PyCode probably_a_local_import$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for import statements.\nIf spam is being imported from the local directory, this import:\n    from spam import eggs\nBecomes:\n    from .spam import eggs\n\nAnd this import:\n    import spam\nBecomes:\n    from . import spam\n"));
      var1.setline(11);
      PyString.fromInterned("Fixer for import statements.\nIf spam is being imported from the local directory, this import:\n    from spam import eggs\nBecomes:\n    from .spam import eggs\n\nAnd this import:\n    import spam\nBecomes:\n    from . import spam\n");
      var1.setline(14);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(15);
      var3 = new String[]{"dirname", "join", "exists", "sep"};
      var5 = imp.importFrom("os.path", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("dirname", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("join", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("exists", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("sep", var4);
      var4 = null;
      var1.setline(16);
      var3 = new String[]{"FromImport", "syms", "token"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("FromImport", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("syms", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(19);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, traverse_imports$1, PyString.fromInterned("\n    Walks over all the names imported in a dotted_as_names node.\n    "));
      var1.setlocal("traverse_imports", var6);
      var3 = null;
      var1.setline(38);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixImport", var5, FixImport$2);
      var1.setlocal("FixImport", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject traverse_imports$1(PyFrame var1, ThreadState var2) {
      Object var10000;
      Object[] var3;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(22);
            PyString.fromInterned("\n    Walks over all the names imported in a dotted_as_names node.\n    ");
            var1.setline(23);
            PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
            PyList var9 = new PyList(var5);
            Arrays.fill(var5, (Object)null);
            PyList var6 = var9;
            var1.setlocal(1, var6);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
            break;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(24);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(25);
         PyObject var7 = var1.getlocal(1).__getattr__("pop").__call__(var2);
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(26);
         var7 = var1.getlocal(2).__getattr__("type");
         var8 = var7._eq(var1.getglobal("token").__getattr__("NAME"));
         var3 = null;
         if (var8.__nonzero__()) {
            var1.setline(27);
            var1.setline(27);
            var8 = var1.getlocal(2).__getattr__("value");
            var1.f_lasti = 1;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var8;
         }

         var1.setline(28);
         var7 = var1.getlocal(2).__getattr__("type");
         var8 = var7._eq(var1.getglobal("syms").__getattr__("dotted_name"));
         var3 = null;
         if (var8.__nonzero__()) {
            var1.setline(29);
            var1.setline(29);
            var8 = PyString.fromInterned("").__getattr__("join");
            PyList var10002 = new PyList();
            var7 = var10002.__getattr__("append");
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(29);
            var7 = var1.getlocal(2).__getattr__("children").__iter__();

            while(true) {
               var1.setline(29);
               PyObject var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(29);
                  var1.dellocal(3);
                  var8 = var8.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.f_lasti = 2;
                  var3 = new Object[5];
                  var1.f_savedlocals = var3;
                  return var8;
               }

               var1.setlocal(4, var4);
               var1.setline(29);
               var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("value"));
            }
         }

         var1.setline(30);
         var7 = var1.getlocal(2).__getattr__("type");
         var8 = var7._eq(var1.getglobal("syms").__getattr__("dotted_as_name"));
         var3 = null;
         if (var8.__nonzero__()) {
            var1.setline(31);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0)));
         } else {
            var1.setline(32);
            var7 = var1.getlocal(2).__getattr__("type");
            var8 = var7._eq(var1.getglobal("syms").__getattr__("dotted_as_names"));
            var3 = null;
            if (!var8.__nonzero__()) {
               var1.setline(35);
               throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unkown node type")));
            }

            var1.setline(33);
            var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("children").__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-2)));
         }
      }
   }

   public PyObject FixImport$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(39);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(41);
      PyString var4 = PyString.fromInterned("\n    import_from< 'from' imp=any 'import' ['('] any [')'] >\n    |\n    import_name< 'import' imp=any >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(47);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, start_tree$3, (PyObject)null);
      var1.setlocal("start_tree", var6);
      var3 = null;
      var1.setline(51);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$4, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      var1.setline(85);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, probably_a_local_import$5, (PyObject)null);
      var1.setlocal("probably_a_local_import", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject start_tree$3(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixImport"), var1.getlocal(0)).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(49);
      PyString var3 = PyString.fromInterned("absolute_import");
      PyObject var10000 = var3._in(var1.getlocal(1).__getattr__("future_features"));
      var3 = null;
      PyObject var4 = var10000;
      var1.getlocal(0).__setattr__("skip", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      if (var1.getlocal(0).__getattr__("skip").__nonzero__()) {
         var1.setline(53);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(54);
         PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("imp"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(56);
         var3 = var1.getlocal(1).__getattr__("type");
         PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_from"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(67);
            var3 = var1.getglobal("False");
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(68);
            var3 = var1.getglobal("False");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(69);
            var3 = var1.getglobal("traverse_imports").__call__(var2, var1.getlocal(3)).__iter__();

            while(true) {
               var1.setline(69);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(74);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(75);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(78);
                        var1.getlocal(0).__getattr__("warning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("absolute and local imports together"));
                     }

                     var1.setline(79);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(81);
                  var3 = var1.getglobal("FromImport").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("."), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(82);
                  var3 = var1.getlocal(1).__getattr__("prefix");
                  var1.getlocal(7).__setattr__("prefix", var3);
                  var3 = null;
                  var1.setline(83);
                  var3 = var1.getlocal(7);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(6, var4);
               var1.setline(70);
               PyObject var5;
               if (var1.getlocal(0).__getattr__("probably_a_local_import").__call__(var2, var1.getlocal(6)).__nonzero__()) {
                  var1.setline(71);
                  var5 = var1.getglobal("True");
                  var1.setlocal(4, var5);
                  var5 = null;
               } else {
                  var1.setline(73);
                  var5 = var1.getglobal("True");
                  var1.setlocal(5, var5);
                  var5 = null;
               }
            }
         } else {
            while(true) {
               var1.setline(61);
               if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("value")).__not__().__nonzero__()) {
                  var1.setline(63);
                  if (var1.getlocal(0).__getattr__("probably_a_local_import").__call__(var2, var1.getlocal(3).__getattr__("value")).__nonzero__()) {
                     var1.setline(64);
                     var3 = PyUnicode.fromInterned(".")._add(var1.getlocal(3).__getattr__("value"));
                     var1.getlocal(3).__setattr__("value", var3);
                     var3 = null;
                     var1.setline(65);
                     var1.getlocal(3).__getattr__("changed").__call__(var2);
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(62);
               var3 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(0));
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject probably_a_local_import$5(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(".")).__nonzero__()) {
         var1.setline(88);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(89);
         PyObject var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("."), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(90);
         var4 = var1.getglobal("dirname").__call__(var2, var1.getlocal(0).__getattr__("filename"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(91);
         var4 = var1.getglobal("join").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(94);
         if (var1.getglobal("exists").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("dirname").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("__init__.py"))).__not__().__nonzero__()) {
            var1.setline(95);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(96);
            var4 = (new PyList(new PyObject[]{PyString.fromInterned(".py"), var1.getglobal("sep"), PyString.fromInterned(".pyc"), PyString.fromInterned(".so"), PyString.fromInterned(".sl"), PyString.fromInterned(".pyd")})).__iter__();

            do {
               var1.setline(96);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(99);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(97);
            } while(!var1.getglobal("exists").__call__(var2, var1.getlocal(2)._add(var1.getlocal(3))).__nonzero__());

            var1.setline(98);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public fix_import$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"names", "pending", "node", "_[29_27]", "ch"};
      traverse_imports$1 = Py.newCode(1, var2, var1, "traverse_imports", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      FixImport$2 = Py.newCode(0, var2, var1, "FixImport", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree", "name"};
      start_tree$3 = Py.newCode(3, var2, var1, "start_tree", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "imp", "have_local", "have_absolute", "mod_name", "new"};
      transform$4 = Py.newCode(3, var2, var1, "transform", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "imp_name", "base_path", "ext"};
      probably_a_local_import$5 = Py.newCode(2, var2, var1, "probably_a_local_import", 85, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_import$py("lib2to3/fixes/fix_import$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_import$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.traverse_imports$1(var2, var3);
         case 2:
            return this.FixImport$2(var2, var3);
         case 3:
            return this.start_tree$3(var2, var3);
         case 4:
            return this.transform$4(var2, var3);
         case 5:
            return this.probably_a_local_import$5(var2, var3);
         default:
            return null;
      }
   }
}

package lib2to3.fixes;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_itertools_imports.py")
public class fix_itertools_imports$py extends PyFunctionTable implements PyRunnable {
   static fix_itertools_imports$py self;
   static final PyCode f$0;
   static final PyCode FixItertoolsImports$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Fixer for imports of itertools.(imap|ifilter|izip|ifilterfalse) "));
      var1.setline(1);
      PyString.fromInterned(" Fixer for imports of itertools.(imap|ifilter|izip|ifilterfalse) ");
      var1.setline(4);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("lib2to3", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(5);
      var3 = new String[]{"BlankLine", "syms", "token"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("BlankLine", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("syms", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(8);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixItertoolsImports", var5, FixItertoolsImports$1);
      var1.setlocal("FixItertoolsImports", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixItertoolsImports$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(9);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(10);
      var3 = PyString.fromInterned("\n              import_from< 'from' 'itertools' 'import' imports=any >\n              ")._mod(var1.getname("locals").__call__(var2));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(14);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, transform$2, (PyObject)null);
      var1.setlocal("transform", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("imports"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getlocal(3).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_as_name"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__getattr__("children").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(17);
         PyList var6 = new PyList(new PyObject[]{var1.getlocal(3)});
         var1.setlocal(4, var6);
         var3 = null;
      } else {
         var1.setline(19);
         var3 = var1.getlocal(3).__getattr__("children");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(20);
      var3 = var1.getlocal(4).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(20);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(40);
            Object var9 = var1.getlocal(3).__getattr__("children").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
            if (!((PyObject)var9).__nonzero__()) {
               var9 = new PyList(new PyObject[]{var1.getlocal(3)});
            }

            Object var7 = var9;
            var1.setlocal(4, (PyObject)var7);
            var3 = null;
            var1.setline(41);
            var3 = var1.getglobal("True");
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(42);
            var3 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(42);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  while(true) {
                     var1.setline(48);
                     var10000 = var1.getlocal(4);
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(4).__getitem__(Py.newInteger(-1)).__getattr__("type");
                        var10000 = var3._eq(var1.getglobal("token").__getattr__("COMMA"));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(52);
                        var10000 = var1.getlocal(3).__getattr__("children");
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("value"), (PyObject)var1.getglobal("None"));
                        }

                        var10000 = var10000.__not__();
                        if (!var10000.__nonzero__()) {
                           var3 = var1.getlocal(3).__getattr__("parent");
                           var10000 = var3._is(var1.getglobal("None"));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(54);
                           var3 = var1.getlocal(1).__getattr__("prefix");
                           var1.setlocal(10, var3);
                           var3 = null;
                           var1.setline(55);
                           var3 = var1.getglobal("BlankLine").__call__(var2);
                           var1.setlocal(1, var3);
                           var3 = null;
                           var1.setline(56);
                           var3 = var1.getlocal(10);
                           var1.getlocal(1).__setattr__("prefix", var3);
                           var3 = null;
                           var1.setline(57);
                           var3 = var1.getlocal(1);
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setline(49);
                     var1.getlocal(4).__getattr__("pop").__call__(var2).__getattr__("remove").__call__(var2);
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(43);
               var10000 = var1.getlocal(9);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(5).__getattr__("type");
                  var10000 = var5._eq(var1.getglobal("token").__getattr__("COMMA"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(44);
                  var1.getlocal(5).__getattr__("remove").__call__(var2);
               } else {
                  var1.setline(46);
                  var5 = var1.getlocal(9);
                  var5 = var5._ixor(var1.getglobal("True"));
                  var1.setlocal(9, var5);
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(21);
         var5 = var1.getlocal(5).__getattr__("type");
         var10000 = var5._eq(var1.getglobal("token").__getattr__("NAME"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(22);
            var5 = var1.getlocal(5).__getattr__("value");
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(23);
            var5 = var1.getlocal(5);
            var1.setlocal(7, var5);
            var5 = null;
         } else {
            var1.setline(24);
            var5 = var1.getlocal(5).__getattr__("type");
            var10000 = var5._eq(var1.getglobal("token").__getattr__("STAR"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(26);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(28);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var5 = var1.getlocal(5).__getattr__("type");
               var10000 = var5._eq(var1.getglobal("syms").__getattr__("import_as_name"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(29);
            var5 = var1.getlocal(5).__getattr__("children").__getitem__(Py.newInteger(0));
            var1.setlocal(7, var5);
            var5 = null;
         }

         var1.setline(30);
         var5 = var1.getlocal(7).__getattr__("value");
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(31);
         var5 = var1.getlocal(8);
         var10000 = var5._in(new PyTuple(new PyObject[]{PyUnicode.fromInterned("imap"), PyUnicode.fromInterned("izip"), PyUnicode.fromInterned("ifilter")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(32);
            var5 = var1.getglobal("None");
            var1.getlocal(5).__setattr__("value", var5);
            var5 = null;
            var1.setline(33);
            var1.getlocal(5).__getattr__("remove").__call__(var2);
         } else {
            var1.setline(34);
            var5 = var1.getlocal(8);
            var10000 = var5._in(new PyTuple(new PyObject[]{PyUnicode.fromInterned("ifilterfalse"), PyUnicode.fromInterned("izip_longest")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(35);
               var1.getlocal(1).__getattr__("changed").__call__(var2);
               var1.setline(36);
               var1.setline(36);
               var5 = var1.getlocal(8).__getitem__(Py.newInteger(1));
               var10000 = var5._eq(PyUnicode.fromInterned("f"));
               var5 = null;
               PyUnicode var8 = var10000.__nonzero__() ? PyUnicode.fromInterned("filterfalse") : PyUnicode.fromInterned("zip_longest");
               var1.getlocal(7).__setattr__((String)"value", var8);
               var5 = null;
            }
         }
      }
   }

   public fix_itertools_imports$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixItertoolsImports$1 = Py.newCode(0, var2, var1, "FixItertoolsImports", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "imports", "children", "child", "member", "name_node", "member_name", "remove_comma", "p"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_itertools_imports$py("lib2to3/fixes/fix_itertools_imports$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_itertools_imports$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixItertoolsImports$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}

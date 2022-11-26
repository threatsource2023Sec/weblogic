package compiler;

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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/misc.py")
public class misc$py extends PyFunctionTable implements PyRunnable {
   static misc$py self;
   static final PyCode f$0;
   static final PyCode flatten$1;
   static final PyCode Set$2;
   static final PyCode __init__$3;
   static final PyCode __len__$4;
   static final PyCode __contains__$5;
   static final PyCode add$6;
   static final PyCode elements$7;
   static final PyCode has_elt$8;
   static final PyCode remove$9;
   static final PyCode copy$10;
   static final PyCode Stack$11;
   static final PyCode __init__$12;
   static final PyCode __len__$13;
   static final PyCode push$14;
   static final PyCode top$15;
   static final PyCode __getitem__$16;
   static final PyCode mangle$17;
   static final PyCode set_filename$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(2);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, flatten$1, (PyObject)null);
      var1.setlocal("flatten", var5);
      var3 = null;
      var1.setline(11);
      var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Set", var3, Set$2);
      var1.setlocal("Set", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = Py.makeClass("Stack", var3, Stack$11);
      var1.setlocal("Stack", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(44);
      PyInteger var6 = Py.newInteger(256);
      var1.setlocal("MANGLE_LEN", var6);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, mangle$17, (PyObject)null);
      var1.setlocal("mangle", var5);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, set_filename$18, PyString.fromInterned("Set the filename attribute to filename on every node in tree"));
      var1.setlocal("set_filename", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flatten$1(PyFrame var1, ThreadState var2) {
      var1.setline(3);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(4);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(4);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(9);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(5);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(6);
            PyObject var5 = var1.getlocal(1)._add(var1.getglobal("flatten").__call__(var2, var1.getlocal(2)));
            var1.setlocal(1, var5);
            var5 = null;
         } else {
            var1.setline(8);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject Set$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(14);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$4, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(16);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$5, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$6, (PyObject)null);
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(20);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, elements$7, (PyObject)null);
      var1.setlocal("elements", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_elt$8, (PyObject)null);
      var1.setlocal("has_elt", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$9, (PyObject)null);
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$10, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"elts", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$4(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("elts"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$5(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("elts"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add$6(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("elts").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject elements$7(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getlocal(0).__getattr__("elts").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_elt$8(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("elts"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$9(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.getlocal(0).__getattr__("elts").__delitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$10(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getglobal("Set").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(28);
      var1.getlocal(1).__getattr__("elts").__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("elts"));
      var1.setline(29);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Stack$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(35);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$13, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push$14, (PyObject)null);
      var1.setlocal("push", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, top$15, (PyObject)null);
      var1.setlocal("top", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$16, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var3);
      var3 = null;
      var1.setline(34);
      PyObject var4 = var1.getlocal(0).__getattr__("stack").__getattr__("pop");
      var1.getlocal(0).__setattr__("pop", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$13(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject push$14(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject top$15(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$16(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mangle$17(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__")).__not__().__nonzero__()) {
         var1.setline(48);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(49);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0))._add(Py.newInteger(2));
         PyObject var10000 = var4._ge(var1.getglobal("MANGLE_LEN"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(50);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(51);
            if (var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__")).__nonzero__()) {
               var1.setline(52);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            } else {
               try {
                  var1.setline(54);
                  PyInteger var7 = Py.newInteger(0);
                  var1.setlocal(2, var7);
                  var4 = null;

                  while(true) {
                     var1.setline(55);
                     var4 = var1.getlocal(1).__getitem__(var1.getlocal(2));
                     var10000 = var4._eq(PyString.fromInterned("_"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(56);
                     var4 = var1.getlocal(2)._add(Py.newInteger(1));
                     var1.setlocal(2, var4);
                     var4 = null;
                  }
               } catch (Throwable var5) {
                  PyException var6 = Py.setException(var5, var1);
                  if (var6.match(var1.getglobal("IndexError"))) {
                     var1.setline(58);
                     var3 = var1.getlocal(0);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  throw var6;
               }

               var1.setline(59);
               var4 = var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(61);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(62);
               var4 = var1.getlocal(3);
               var10000 = var4._gt(var1.getglobal("MANGLE_LEN"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(63);
                  var4 = var1.getlocal(1).__getslice__((PyObject)null, var1.getglobal("MANGLE_LEN")._sub(var1.getlocal(3)), (PyObject)null);
                  var1.setlocal(1, var4);
                  var4 = null;
               }

               var1.setline(65);
               var3 = PyString.fromInterned("_%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0)}));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject set_filename$18(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Set the filename attribute to filename on every node in tree");
      var1.setline(69);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(70);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(71);
         PyObject var4 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(72);
         var4 = var1.getlocal(0);
         var1.getlocal(3).__setattr__("filename", var4);
         var3 = null;
         var1.setline(73);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(3).__getattr__("getChildNodes").__call__(var2));
      }
   }

   public misc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"tup", "elts", "elt"};
      flatten$1 = Py.newCode(1, var2, var1, "flatten", 2, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Set$2 = Py.newCode(0, var2, var1, "Set", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$3 = Py.newCode(1, var2, var1, "__init__", 12, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$4 = Py.newCode(1, var2, var1, "__len__", 14, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      __contains__$5 = Py.newCode(2, var2, var1, "__contains__", 16, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      add$6 = Py.newCode(2, var2, var1, "add", 18, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      elements$7 = Py.newCode(1, var2, var1, "elements", 20, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      has_elt$8 = Py.newCode(2, var2, var1, "has_elt", 22, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      remove$9 = Py.newCode(2, var2, var1, "remove", 24, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      copy$10 = Py.newCode(1, var2, var1, "copy", 26, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Stack$11 = Py.newCode(0, var2, var1, "Stack", 31, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$12 = Py.newCode(1, var2, var1, "__init__", 32, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$13 = Py.newCode(1, var2, var1, "__len__", 35, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      push$14 = Py.newCode(2, var2, var1, "push", 37, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      top$15 = Py.newCode(1, var2, var1, "top", 39, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$16 = Py.newCode(2, var2, var1, "__getitem__", 41, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "klass", "i", "tlen"};
      mangle$17 = Py.newCode(2, var2, var1, "mangle", 46, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "tree", "worklist", "node"};
      set_filename$18 = Py.newCode(2, var2, var1, "set_filename", 67, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new misc$py("compiler/misc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(misc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.flatten$1(var2, var3);
         case 2:
            return this.Set$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__len__$4(var2, var3);
         case 5:
            return this.__contains__$5(var2, var3);
         case 6:
            return this.add$6(var2, var3);
         case 7:
            return this.elements$7(var2, var3);
         case 8:
            return this.has_elt$8(var2, var3);
         case 9:
            return this.remove$9(var2, var3);
         case 10:
            return this.copy$10(var2, var3);
         case 11:
            return this.Stack$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.__len__$13(var2, var3);
         case 14:
            return this.push$14(var2, var3);
         case 15:
            return this.top$15(var2, var3);
         case 16:
            return this.__getitem__$16(var2, var3);
         case 17:
            return this.mangle$17(var2, var3);
         case 18:
            return this.set_filename$18(var2, var3);
         default:
            return null;
      }
   }
}

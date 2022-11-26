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
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/symbols.py")
public class symbols$py extends PyFunctionTable implements PyRunnable {
   static symbols$py self;
   static final PyCode f$0;
   static final PyCode Scope$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode mangle$4;
   static final PyCode add_def$5;
   static final PyCode add_use$6;
   static final PyCode add_global$7;
   static final PyCode add_param$8;
   static final PyCode get_names$9;
   static final PyCode add_child$10;
   static final PyCode get_children$11;
   static final PyCode DEBUG$12;
   static final PyCode check_name$13;
   static final PyCode get_free_vars$14;
   static final PyCode handle_children$15;
   static final PyCode force_global$16;
   static final PyCode add_frees$17;
   static final PyCode get_cell_vars$18;
   static final PyCode ModuleScope$19;
   static final PyCode __init__$20;
   static final PyCode FunctionScope$21;
   static final PyCode GenExprScope$22;
   static final PyCode __init__$23;
   static final PyCode get_names$24;
   static final PyCode LambdaScope$25;
   static final PyCode __init__$26;
   static final PyCode ClassScope$27;
   static final PyCode __init__$28;
   static final PyCode SymbolVisitor$29;
   static final PyCode __init__$30;
   static final PyCode visitModule$31;
   static final PyCode visitFunction$32;
   static final PyCode visitGenExpr$33;
   static final PyCode visitGenExprInner$34;
   static final PyCode visitGenExprFor$35;
   static final PyCode visitGenExprIf$36;
   static final PyCode visitLambda$37;
   static final PyCode _do_args$38;
   static final PyCode handle_free_vars$39;
   static final PyCode visitClass$40;
   static final PyCode visitName$41;
   static final PyCode visitFor$42;
   static final PyCode visitFrom$43;
   static final PyCode visitImport$44;
   static final PyCode visitGlobal$45;
   static final PyCode visitAssign$46;
   static final PyCode visitAssName$47;
   static final PyCode visitAssAttr$48;
   static final PyCode visitSubscript$49;
   static final PyCode visitSlice$50;
   static final PyCode visitAugAssign$51;
   static final PyCode visitIf$52;
   static final PyCode visitYield$53;
   static final PyCode list_eq$54;
   static final PyCode get_names$55;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Module symbol-table generator"));
      var1.setline(1);
      PyString.fromInterned("Module symbol-table generator");
      var1.setline(3);
      String[] var3 = new String[]{"ast"};
      PyObject[] var10 = imp.importFrom("compiler", var3, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal("ast", var4);
      var4 = null;
      var1.setline(4);
      var3 = new String[]{"SC_LOCAL", "SC_GLOBAL_IMPLICIT", "SC_GLOBAL_EXPLICIT", "SC_FREE", "SC_CELL", "SC_UNKNOWN"};
      var10 = imp.importFrom("compiler.consts", var3, var1, -1);
      var4 = var10[0];
      var1.setlocal("SC_LOCAL", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("SC_GLOBAL_IMPLICIT", var4);
      var4 = null;
      var4 = var10[2];
      var1.setlocal("SC_GLOBAL_EXPLICIT", var4);
      var4 = null;
      var4 = var10[3];
      var1.setlocal("SC_FREE", var4);
      var4 = null;
      var4 = var10[4];
      var1.setlocal("SC_CELL", var4);
      var4 = null;
      var4 = var10[5];
      var1.setlocal("SC_UNKNOWN", var4);
      var4 = null;
      var1.setline(6);
      var3 = new String[]{"mangle"};
      var10 = imp.importFrom("compiler.misc", var3, var1, -1);
      var4 = var10[0];
      var1.setlocal("mangle", var4);
      var4 = null;
      var1.setline(7);
      PyObject var11 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var11);
      var3 = null;
      var1.setline(10);
      var11 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var11);
      var3 = null;
      var1.setline(12);
      PyInteger var12 = Py.newInteger(256);
      var1.setlocal("MANGLE_LEN", var12);
      var3 = null;
      var1.setline(14);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Scope", var10, Scope$1);
      var1.setlocal("Scope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(172);
      var10 = new PyObject[]{var1.getname("Scope")};
      var4 = Py.makeClass("ModuleScope", var10, ModuleScope$19);
      var1.setlocal("ModuleScope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(178);
      var10 = new PyObject[]{var1.getname("Scope")};
      var4 = Py.makeClass("FunctionScope", var10, FunctionScope$21);
      var1.setlocal("FunctionScope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(181);
      var10 = new PyObject[]{var1.getname("Scope")};
      var4 = Py.makeClass("GenExprScope", var10, GenExprScope$22);
      var1.setlocal("GenExprScope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(196);
      var10 = new PyObject[]{var1.getname("FunctionScope")};
      var4 = Py.makeClass("LambdaScope", var10, LambdaScope$25);
      var1.setlocal("LambdaScope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(206);
      var10 = new PyObject[]{var1.getname("Scope")};
      var4 = Py.makeClass("ClassScope", var10, ClassScope$27);
      var1.setlocal("ClassScope", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(212);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("SymbolVisitor", var10, SymbolVisitor$29);
      var1.setlocal("SymbolVisitor", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(411);
      var10 = Py.EmptyObjects;
      PyFunction var14 = new PyFunction(var1.f_globals, var10, list_eq$54, (PyObject)null);
      var1.setlocal("list_eq", var14);
      var3 = null;
      var1.setline(414);
      var11 = var1.getname("__name__");
      PyObject var10000 = var11._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(415);
         var11 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var11);
         var3 = null;
         var1.setline(416);
         var3 = new String[]{"parseFile", "walk"};
         var10 = imp.importFrom("compiler", var3, var1, -1);
         var4 = var10[0];
         var1.setlocal("parseFile", var4);
         var4 = null;
         var4 = var10[1];
         var1.setlocal("walk", var4);
         var4 = null;
         var1.setline(417);
         var11 = imp.importOne("symtable", var1, -1);
         var1.setlocal("symtable", var11);
         var3 = null;
         var1.setline(419);
         var10 = Py.EmptyObjects;
         var14 = new PyFunction(var1.f_globals, var10, get_names$55, (PyObject)null);
         var1.setlocal("get_names", var14);
         var3 = null;
         var1.setline(423);
         var11 = var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         label46:
         while(true) {
            var1.setline(423);
            var4 = var11.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal("file", var4);
            var1.setline(424);
            Py.println(var1.getname("file"));
            var1.setline(425);
            PyObject var5 = var1.getname("open").__call__(var2, var1.getname("file"));
            var1.setlocal("f", var5);
            var5 = null;
            var1.setline(426);
            var5 = var1.getname("f").__getattr__("read").__call__(var2);
            var1.setlocal("buf", var5);
            var5 = null;
            var1.setline(427);
            var1.getname("f").__getattr__("close").__call__(var2);
            var1.setline(428);
            var5 = var1.getname("symtable").__getattr__("symtable").__call__((ThreadState)var2, var1.getname("buf"), (PyObject)var1.getname("file"), (PyObject)PyString.fromInterned("exec"));
            var1.setlocal("syms", var5);
            var5 = null;
            var1.setline(429);
            var5 = var1.getname("get_names").__call__(var2, var1.getname("syms"));
            var1.setlocal("mod_names", var5);
            var5 = null;
            var1.setline(430);
            var5 = var1.getname("parseFile").__call__(var2, var1.getname("file"));
            var1.setlocal("tree", var5);
            var5 = null;
            var1.setline(431);
            var5 = var1.getname("SymbolVisitor").__call__(var2);
            var1.setlocal("s", var5);
            var5 = null;
            var1.setline(432);
            var1.getname("walk").__call__(var2, var1.getname("tree"), var1.getname("s"));
            var1.setline(435);
            var5 = var1.getname("s").__getattr__("scopes").__getitem__(var1.getname("tree")).__getattr__("get_names").__call__(var2);
            var1.setlocal("names2", var5);
            var5 = null;
            var1.setline(437);
            if (var1.getname("list_eq").__call__(var2, var1.getname("mod_names"), var1.getname("names2")).__not__().__nonzero__()) {
               var1.setline(438);
               Py.println();
               var1.setline(439);
               Py.printComma(PyString.fromInterned("oops"));
               Py.println(var1.getname("file"));
               var1.setline(440);
               Py.println(var1.getname("sorted").__call__(var2, var1.getname("mod_names")));
               var1.setline(441);
               Py.println(var1.getname("sorted").__call__(var2, var1.getname("names2")));
               var1.setline(442);
               var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1));
            }

            var1.setline(444);
            PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal("d", var15);
            var5 = null;
            var1.setline(445);
            var1.getname("d").__getattr__("update").__call__(var2, var1.getname("s").__getattr__("scopes"));
            var1.setline(446);
            var1.getname("d").__delitem__(var1.getname("tree"));
            var1.setline(447);
            var5 = var1.getname("d").__getattr__("values").__call__(var2);
            var1.setlocal("scopes", var5);
            var5 = null;
            var1.setline(448);
            var1.dellocal("d");
            var1.setline(450);
            var5 = var1.getname("syms").__getattr__("get_symbols").__call__(var2).__iter__();

            while(true) {
               do {
                  var1.setline(450);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     continue label46;
                  }

                  var1.setlocal("s", var6);
                  var1.setline(451);
               } while(!var1.getname("s").__getattr__("is_namespace").__call__(var2).__nonzero__());

               var1.setline(452);
               PyList var16 = new PyList();
               PyObject var7 = var16.__getattr__("append");
               var1.setlocal("_[452_21]", var7);
               var7 = null;
               var1.setline(452);
               var7 = var1.getname("scopes").__iter__();

               while(true) {
                  var1.setline(452);
                  PyObject var8 = var7.__iternext__();
                  if (var8 == null) {
                     var1.setline(452);
                     var1.dellocal("_[452_21]");
                     PyList var13 = var16;
                     var1.setlocal("l", var13);
                     var7 = null;
                     var1.setline(454);
                     var7 = var1.getname("len").__call__(var2, var1.getname("l"));
                     var10000 = var7._gt(Py.newInteger(1));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(455);
                        Py.printComma(PyString.fromInterned("skipping"));
                        Py.println(var1.getname("s").__getattr__("get_name").__call__(var2));
                     } else {
                        var1.setline(457);
                        if (var1.getname("list_eq").__call__(var2, var1.getname("get_names").__call__(var2, var1.getname("s").__getattr__("get_namespace").__call__(var2)), var1.getname("l").__getitem__(Py.newInteger(0)).__getattr__("get_names").__call__(var2)).__not__().__nonzero__()) {
                           var1.setline(459);
                           Py.println(var1.getname("s").__getattr__("get_name").__call__(var2));
                           var1.setline(460);
                           Py.println(var1.getname("sorted").__call__(var2, var1.getname("get_names").__call__(var2, var1.getname("s").__getattr__("get_namespace").__call__(var2))));
                           var1.setline(461);
                           Py.println(var1.getname("sorted").__call__(var2, var1.getname("l").__getitem__(Py.newInteger(0)).__getattr__("get_names").__call__(var2)));
                           var1.setline(462);
                           var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1));
                        }
                     }
                     break;
                  }

                  var1.setlocal("sc", var8);
                  var1.setline(453);
                  PyObject var9 = var1.getname("sc").__getattr__("name");
                  PyObject var10001 = var9._eq(var1.getname("s").__getattr__("get_name").__call__(var2));
                  var9 = null;
                  if (var10001.__nonzero__()) {
                     var1.setline(452);
                     var1.getname("_[452_21]").__call__(var2, var1.getname("sc"));
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Scope$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mangle$4, (PyObject)null);
      var1.setlocal("mangle", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_def$5, (PyObject)null);
      var1.setlocal("add_def", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_use$6, (PyObject)null);
      var1.setlocal("add_use", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_global$7, (PyObject)null);
      var1.setlocal("add_global", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_param$8, (PyObject)null);
      var1.setlocal("add_param", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_names$9, (PyObject)null);
      var1.setlocal("get_names", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_child$10, (PyObject)null);
      var1.setlocal("add_child", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_children$11, (PyObject)null);
      var1.setlocal("get_children", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, DEBUG$12, (PyObject)null);
      var1.setlocal("DEBUG", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, check_name$13, PyString.fromInterned("Return scope of name.\n\n        The scope of a name could be LOCAL, GLOBAL, FREE, or CELL.\n        "));
      var1.setlocal("check_name", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_free_vars$14, (PyObject)null);
      var1.setlocal("get_free_vars", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_children$15, (PyObject)null);
      var1.setlocal("handle_children", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, force_global$16, PyString.fromInterned("Force name to be global in scope.\n\n        Some child of the current node had a free reference to name.\n        When the child was processed, it was labelled a free\n        variable.  Now that all its enclosing scope have been\n        processed, the name is known to be a global or builtin.  So\n        walk back down the child chain and set the name to be global\n        rather than free.\n\n        Be careful to stop if a child does not think the name is\n        free.\n        "));
      var1.setlocal("force_global", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_frees$17, PyString.fromInterned("Process list of free vars from nested scope.\n\n        Returns a list of names that are either 1) declared global in the\n        parent or 2) undefined in a top-level parent.  In either case,\n        the nested scope should treat them as globals.\n        "));
      var1.setlocal("add_frees", var4);
      var3 = null;
      var1.setline(169);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_cell_vars$18, (PyObject)null);
      var1.setlocal("get_cell_vars", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(18);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(19);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"defs", var6);
      var3 = null;
      var1.setline(20);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"uses", var6);
      var3 = null;
      var1.setline(21);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"globals", var6);
      var3 = null;
      var1.setline(22);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"params", var6);
      var3 = null;
      var1.setline(23);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"frees", var6);
      var3 = null;
      var1.setline(24);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"cells", var6);
      var3 = null;
      var1.setline(25);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"children", var7);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("nested", var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("generator", var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("klass", var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(32);
         var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

         while(true) {
            var1.setline(32);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(33);
            PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(4));
            var10000 = var5._ne(PyString.fromInterned("_"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(34);
               var5 = var1.getlocal(3).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("klass", var5);
               var5 = null;
               break;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = PyString.fromInterned("<%s: %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("name")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mangle$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(0).__getattr__("klass");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(43);
         var3 = var1.getglobal("mangle").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("klass"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject add_def$5(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__getattr__("defs").__setitem__((PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_use$6(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__getattr__("uses").__setitem__((PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_global$7(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("uses"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("defs"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(54);
      }

      var1.setline(55);
      var3 = var1.getlocal(1);
      var10000 = var3._in(var1.getlocal(0).__getattr__("params"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("%s in %s is global and parameter")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("name")})));
      } else {
         var1.setline(58);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("globals").__setitem__((PyObject)var1.getlocal(1), var4);
         var3 = null;
         var1.setline(59);
         var1.getlocal(0).__getattr__("module").__getattr__("add_def").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject add_param$8(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(63);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__getattr__("defs").__setitem__((PyObject)var1.getlocal(1), var4);
      var3 = null;
      var1.setline(64);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__getattr__("params").__setitem__((PyObject)var1.getlocal(1), var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_names$9(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(68);
      var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("defs"));
      var1.setline(69);
      var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("uses"));
      var1.setline(70);
      var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("globals"));
      var1.setline(71);
      PyObject var4 = var1.getlocal(1).__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject add_child$10(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      var1.getlocal(0).__getattr__("children").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_children$11(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(0).__getattr__("children");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DEBUG$12(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, var1.getlocal(0).__getattr__("name"));
      Object var10001 = var1.getlocal(0).__getattr__("nested");
      if (((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("nested");
      }

      if (!((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("");
      }

      Py.println(var3, (PyObject)var10001);
      var1.setline(81);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, PyString.fromInterned("\tglobals: "));
      Py.println(var3, var1.getlocal(0).__getattr__("globals"));
      var1.setline(82);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, PyString.fromInterned("\tcells: "));
      Py.println(var3, var1.getlocal(0).__getattr__("cells"));
      var1.setline(83);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, PyString.fromInterned("\tdefs: "));
      Py.println(var3, var1.getlocal(0).__getattr__("defs"));
      var1.setline(84);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, PyString.fromInterned("\tuses: "));
      Py.println(var3, var1.getlocal(0).__getattr__("uses"));
      var1.setline(85);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.printComma(var3, PyString.fromInterned("\tfrees:"));
      Py.println(var3, var1.getlocal(0).__getattr__("frees"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_name$13(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Return scope of name.\n\n        The scope of a name could be LOCAL, GLOBAL, FREE, or CELL.\n        ");
      var1.setline(92);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("globals"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(93);
         var3 = var1.getglobal("SC_GLOBAL_EXPLICIT");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(94);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._in(var1.getlocal(0).__getattr__("cells"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(95);
            var3 = var1.getglobal("SC_CELL");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(96);
            var4 = var1.getlocal(1);
            var10000 = var4._in(var1.getlocal(0).__getattr__("defs"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(97);
               var3 = var1.getglobal("SC_LOCAL");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(98);
               var10000 = var1.getlocal(0).__getattr__("nested");
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(1);
                  var10000 = var4._in(var1.getlocal(0).__getattr__("frees"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(1);
                     var10000 = var4._in(var1.getlocal(0).__getattr__("uses"));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(99);
                  var3 = var1.getglobal("SC_FREE");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(100);
                  if (var1.getlocal(0).__getattr__("nested").__nonzero__()) {
                     var1.setline(101);
                     var3 = var1.getglobal("SC_UNKNOWN");
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(103);
                     var3 = var1.getglobal("SC_GLOBAL_IMPLICIT");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject get_free_vars$14(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      if (var1.getlocal(0).__getattr__("nested").__not__().__nonzero__()) {
         var1.setline(107);
         PyTuple var7 = new PyTuple(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(108);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(109);
         var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("frees"));
         var1.setline(110);
         PyObject var8 = var1.getlocal(0).__getattr__("uses").__getattr__("keys").__call__(var2).__iter__();

         while(true) {
            var1.setline(110);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(113);
               PyObject var3 = var1.getlocal(1).__getattr__("keys").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var5);
            var1.setline(111);
            PyObject var6 = var1.getlocal(2);
            PyObject var10000 = var6._notin(var1.getlocal(0).__getattr__("defs"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(2);
               var10000 = var6._notin(var1.getlocal(0).__getattr__("globals"));
               var6 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(112);
               PyInteger var9 = Py.newInteger(1);
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var9);
               var6 = null;
            }
         }
      }
   }

   public PyObject handle_children$15(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("children").__iter__();

      while(true) {
         var1.setline(116);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(117);
         PyObject var5 = var1.getlocal(1).__getattr__("get_free_vars").__call__(var2);
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(118);
         var5 = var1.getlocal(0).__getattr__("add_frees").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(119);
         var5 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(119);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(120);
            var1.getlocal(1).__getattr__("force_global").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject force_global$16(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("Force name to be global in scope.\n\n        Some child of the current node had a free reference to name.\n        When the child was processed, it was labelled a free\n        variable.  Now that all its enclosing scope have been\n        processed, the name is known to be a global or builtin.  So\n        walk back down the child chain and set the name to be global\n        rather than free.\n\n        Be careful to stop if a child does not think the name is\n        free.\n        ");
      var1.setline(135);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__getattr__("globals").__setitem__((PyObject)var1.getlocal(1), var3);
      var3 = null;
      var1.setline(136);
      PyObject var6 = var1.getlocal(1);
      PyObject var10000 = var6._in(var1.getlocal(0).__getattr__("frees"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(137);
         var1.getlocal(0).__getattr__("frees").__delitem__(var1.getlocal(1));
      }

      var1.setline(138);
      var6 = var1.getlocal(0).__getattr__("children").__iter__();

      while(true) {
         var1.setline(138);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(139);
         PyObject var5 = var1.getlocal(2).__getattr__("check_name").__call__(var2, var1.getlocal(1));
         var10000 = var5._eq(var1.getglobal("SC_FREE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(140);
            var1.getlocal(2).__getattr__("force_global").__call__(var2, var1.getlocal(1));
         }
      }
   }

   public PyObject add_frees$17(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Process list of free vars from nested scope.\n\n        Returns a list of names that are either 1) declared global in the\n        parent or 2) undefined in a top-level parent.  In either case,\n        the nested scope should treat them as globals.\n        ");
      var1.setline(149);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(150);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(150);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(167);
            var6 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(151);
         PyObject var5 = var1.getlocal(0).__getattr__("check_name").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(152);
         PyObject var10000;
         PyInteger var7;
         if (var1.getlocal(0).__getattr__("nested").__nonzero__()) {
            var1.setline(153);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(var1.getglobal("SC_UNKNOWN"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(4);
               var10000 = var5._eq(var1.getglobal("SC_FREE"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("ClassScope"));
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(155);
               var7 = Py.newInteger(1);
               var1.getlocal(0).__getattr__("frees").__setitem__((PyObject)var1.getlocal(3), var7);
               var5 = null;
            } else {
               var1.setline(156);
               var5 = var1.getlocal(4);
               var10000 = var5._eq(var1.getglobal("SC_GLOBAL_IMPLICIT"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(157);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
               } else {
                  var1.setline(158);
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("FunctionScope"));
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(4);
                     var10000 = var5._eq(var1.getglobal("SC_LOCAL"));
                     var5 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(159);
                     var7 = Py.newInteger(1);
                     var1.getlocal(0).__getattr__("cells").__setitem__((PyObject)var1.getlocal(3), var7);
                     var5 = null;
                  } else {
                     var1.setline(160);
                     var5 = var1.getlocal(4);
                     var10000 = var5._ne(var1.getglobal("SC_CELL"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(161);
                        var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
                     }
                  }
               }
            }
         } else {
            var1.setline(163);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(var1.getglobal("SC_LOCAL"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(164);
               var7 = Py.newInteger(1);
               var1.getlocal(0).__getattr__("cells").__setitem__((PyObject)var1.getlocal(3), var7);
               var5 = null;
            } else {
               var1.setline(165);
               var5 = var1.getlocal(4);
               var10000 = var5._ne(var1.getglobal("SC_CELL"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(166);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
               }
            }
         }
      }
   }

   public PyObject get_cell_vars$18(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getlocal(0).__getattr__("cells").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ModuleScope$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(173);
      PyObject var3 = var1.getname("Scope").__getattr__("__init__");
      var1.setlocal("_ModuleScope__super_init", var3);
      var3 = null;
      var1.setline(175);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      var1.getlocal(0).__getattr__("_ModuleScope__super_init").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FunctionScope$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(179);
      return var1.getf_locals();
   }

   public PyObject GenExprScope$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(182);
      PyObject var3 = var1.getname("Scope").__getattr__("__init__");
      var1.setlocal("_GenExprScope__super_init", var3);
      var3 = null;
      var1.setline(184);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal("_GenExprScope__counter", var4);
      var3 = null;
      var1.setline(186);
      PyObject[] var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(192);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_names$24, (PyObject)null);
      var1.setlocal("get_names", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyObject var3 = var1.getlocal(0).__getattr__("_GenExprScope__counter");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(188);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_GenExprScope__counter";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(189);
      var1.getlocal(0).__getattr__("_GenExprScope__super_init").__call__(var2, PyString.fromInterned("generator expression<%d>")._mod(var1.getlocal(3)), var1.getlocal(1), var1.getlocal(2));
      var1.setline(190);
      var1.getlocal(0).__getattr__("add_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".0"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_names$24(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getglobal("Scope").__getattr__("get_names").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LambdaScope$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(197);
      PyObject var3 = var1.getname("Scope").__getattr__("__init__");
      var1.setlocal("_LambdaScope__super_init", var3);
      var3 = null;
      var1.setline(199);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal("_LambdaScope__counter", var4);
      var3 = null;
      var1.setline(201);
      PyObject[] var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$26, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$26(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getlocal(0).__getattr__("_LambdaScope__counter");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(203);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_LambdaScope__counter";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(204);
      var1.getlocal(0).__getattr__("_LambdaScope__super_init").__call__(var2, PyString.fromInterned("lambda.%d")._mod(var1.getlocal(3)), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ClassScope$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(207);
      PyObject var3 = var1.getname("Scope").__getattr__("__init__");
      var1.setlocal("_ClassScope__super_init", var3);
      var3 = null;
      var1.setline(209);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$28, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      var1.getlocal(0).__getattr__("_ClassScope__super_init").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SymbolVisitor$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(213);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitModule$31, (PyObject)null);
      var1.setlocal("visitModule", var4);
      var3 = null;
      var1.setline(223);
      PyObject var5 = var1.getname("visitModule");
      var1.setlocal("visitExpression", var5);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitFunction$32, (PyObject)null);
      var1.setlocal("visitFunction", var4);
      var3 = null;
      var1.setline(239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGenExpr$33, (PyObject)null);
      var1.setlocal("visitGenExpr", var4);
      var3 = null;
      var1.setline(250);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGenExprInner$34, (PyObject)null);
      var1.setlocal("visitGenExprInner", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGenExprFor$35, (PyObject)null);
      var1.setlocal("visitGenExprFor", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGenExprIf$36, (PyObject)null);
      var1.setlocal("visitGenExprIf", var4);
      var3 = null;
      var1.setline(265);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, visitLambda$37, (PyObject)null);
      var1.setlocal("visitLambda", var4);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _do_args$38, (PyObject)null);
      var1.setlocal("_do_args", var4);
      var3 = null;
      var1.setline(288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_free_vars$39, (PyObject)null);
      var1.setlocal("handle_free_vars", var4);
      var3 = null;
      var1.setline(292);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitClass$40, (PyObject)null);
      var1.setlocal("visitClass", var4);
      var3 = null;
      var1.setline(315);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, visitName$41, (PyObject)null);
      var1.setlocal("visitName", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitFor$42, (PyObject)null);
      var1.setlocal("visitFor", var4);
      var3 = null;
      var1.setline(330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitFrom$43, (PyObject)null);
      var1.setlocal("visitFrom", var4);
      var3 = null;
      var1.setline(336);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitImport$44, (PyObject)null);
      var1.setlocal("visitImport", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGlobal$45, (PyObject)null);
      var1.setlocal("visitGlobal", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitAssign$46, PyString.fromInterned("Propagate assignment flag down to child nodes.\n\n        The Assign node doesn't itself contains the variables being\n        assigned to.  Instead, the children in node.nodes are visited\n        with the assign flag set to true.  When the names occur in\n        those nodes, they are marked as defs.\n\n        Some names that occur in an assignment target are not bound by\n        the assignment, e.g. a name occurring inside a slice.  The\n        visitor handles these nodes specially; they do not propagate\n        the assign flag to their children.\n        "));
      var1.setlocal("visitAssign", var4);
      var3 = null;
      var1.setline(364);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, visitAssName$47, (PyObject)null);
      var1.setlocal("visitAssName", var4);
      var3 = null;
      var1.setline(367);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, visitAssAttr$48, (PyObject)null);
      var1.setlocal("visitAssAttr", var4);
      var3 = null;
      var1.setline(370);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, visitSubscript$49, (PyObject)null);
      var1.setlocal("visitSubscript", var4);
      var3 = null;
      var1.setline(375);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, visitSlice$50, (PyObject)null);
      var1.setlocal("visitSlice", var4);
      var3 = null;
      var1.setline(382);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitAugAssign$51, (PyObject)null);
      var1.setlocal("visitAugAssign", var4);
      var3 = null;
      var1.setline(392);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getname("types").__getattr__("StringType"), var1.getname("types").__getattr__("IntType"), var1.getname("types").__getattr__("FloatType")});
      var1.setlocal("_const_types", var6);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitIf$52, (PyObject)null);
      var1.setlocal("visitIf", var4);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitYield$53, (PyObject)null);
      var1.setlocal("visitYield", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"scopes", var3);
      var3 = null;
      var1.setline(215);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("klass", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitModule$31(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3 = var1.getglobal("ModuleScope").__call__(var2);
      var1.setlocal(2, var3);
      var1.getlocal(0).__setattr__("module", var3);
      var1.getlocal(0).__getattr__("scopes").__setitem__(var1.getlocal(1), var3);
      var1.setline(221);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("node"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitFunction$32(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      if (var1.getlocal(1).__getattr__("decorators").__nonzero__()) {
         var1.setline(227);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("decorators"), var1.getlocal(2));
      }

      var1.setline(228);
      var1.getlocal(2).__getattr__("add_def").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.setline(229);
      PyObject var3 = var1.getlocal(1).__getattr__("defaults").__iter__();

      while(true) {
         var1.setline(229);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(231);
            var3 = var1.getglobal("FunctionScope").__call__(var2, var1.getlocal(1).__getattr__("name"), var1.getlocal(0).__getattr__("module"), var1.getlocal(0).__getattr__("klass"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(232);
            PyObject var10000 = var1.getlocal(2).__getattr__("nested");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("FunctionScope"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(233);
               PyInteger var5 = Py.newInteger(1);
               var1.getlocal(4).__setattr__((String)"nested", var5);
               var3 = null;
            }

            var1.setline(234);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__getattr__("scopes").__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.setline(235);
            var1.getlocal(0).__getattr__("_do_args").__call__(var2, var1.getlocal(4), var1.getlocal(1).__getattr__("argnames"));
            var1.setline(236);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(4));
            var1.setline(237);
            var1.getlocal(0).__getattr__("handle_free_vars").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(230);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      }
   }

   public PyObject visitGenExpr$33(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getglobal("GenExprScope").__call__(var2, var1.getlocal(0).__getattr__("module"), var1.getlocal(0).__getattr__("klass"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(241);
      PyObject var10000 = var1.getlocal(2).__getattr__("nested");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("FunctionScope"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("GenExprScope"));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(243);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(3).__setattr__((String)"nested", var4);
         var3 = null;
      }

      var1.setline(245);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__getattr__("scopes").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(246);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(3));
      var1.setline(248);
      var1.getlocal(0).__getattr__("handle_free_vars").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitGenExprInner$34(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(1).__getattr__("quals").__iter__();

      while(true) {
         var1.setline(251);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(254);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"), var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(252);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      }
   }

   public PyObject visitGenExprFor$35(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("assign"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      var1.setline(258);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("iter"), var1.getlocal(2));
      var1.setline(259);
      PyObject var3 = var1.getlocal(1).__getattr__("ifs").__iter__();

      while(true) {
         var1.setline(259);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(260);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      }
   }

   public PyObject visitGenExprIf$36(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitLambda$37(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(3).__not__().__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(271);
         PyObject var3 = var1.getlocal(1).__getattr__("defaults").__iter__();

         while(true) {
            var1.setline(271);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(273);
               var3 = var1.getglobal("LambdaScope").__call__(var2, var1.getlocal(0).__getattr__("module"), var1.getlocal(0).__getattr__("klass"));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(274);
               var10000 = var1.getlocal(2).__getattr__("nested");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("FunctionScope"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(275);
                  PyInteger var5 = Py.newInteger(1);
                  var1.getlocal(5).__setattr__((String)"nested", var5);
                  var3 = null;
               }

               var1.setline(276);
               var3 = var1.getlocal(5);
               var1.getlocal(0).__getattr__("scopes").__setitem__(var1.getlocal(1), var3);
               var3 = null;
               var1.setline(277);
               var1.getlocal(0).__getattr__("_do_args").__call__(var2, var1.getlocal(5), var1.getlocal(1).__getattr__("argnames"));
               var1.setline(278);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(5));
               var1.setline(279);
               var1.getlocal(0).__getattr__("handle_free_vars").__call__(var2, var1.getlocal(5), var1.getlocal(2));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(272);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4), var1.getlocal(2));
         }
      }
   }

   public PyObject _do_args$38(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(282);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(283);
         PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var5._eq(var1.getglobal("types").__getattr__("TupleType"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(284);
            var1.getlocal(0).__getattr__("_do_args").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         } else {
            var1.setline(286);
            var1.getlocal(1).__getattr__("add_param").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject handle_free_vars$39(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      var1.getlocal(2).__getattr__("add_child").__call__(var2, var1.getlocal(1));
      var1.setline(290);
      var1.getlocal(1).__getattr__("handle_children").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitClass$40(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      var1.getlocal(2).__getattr__("add_def").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.setline(294);
      PyObject var3 = var1.getlocal(1).__getattr__("bases").__iter__();

      while(true) {
         var1.setline(294);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(296);
            var3 = var1.getglobal("ClassScope").__call__(var2, var1.getlocal(1).__getattr__("name"), var1.getlocal(0).__getattr__("module"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(297);
            PyObject var10000 = var1.getlocal(2).__getattr__("nested");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("FunctionScope"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(298);
               PyInteger var5 = Py.newInteger(1);
               var1.getlocal(4).__setattr__((String)"nested", var5);
               var3 = null;
            }

            var1.setline(299);
            var3 = var1.getlocal(1).__getattr__("doc");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(300);
               var1.getlocal(4).__getattr__("add_def").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__doc__"));
            }

            var1.setline(301);
            var1.getlocal(4).__getattr__("add_def").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__module__"));
            var1.setline(302);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__getattr__("scopes").__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.setline(303);
            var3 = var1.getlocal(0).__getattr__("klass");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(304);
            var3 = var1.getlocal(1).__getattr__("name");
            var1.getlocal(0).__setattr__("klass", var3);
            var3 = null;
            var1.setline(305);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(4));
            var1.setline(306);
            var3 = var1.getlocal(5);
            var1.getlocal(0).__setattr__("klass", var3);
            var3 = null;
            var1.setline(307);
            var1.getlocal(0).__getattr__("handle_free_vars").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(295);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      }
   }

   public PyObject visitName$41(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(317);
         var1.getlocal(2).__getattr__("add_def").__call__(var2, var1.getlocal(1).__getattr__("name"));
      } else {
         var1.setline(319);
         var1.getlocal(2).__getattr__("add_use").__call__(var2, var1.getlocal(1).__getattr__("name"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitFor$42(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("assign"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      var1.setline(325);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("list"), var1.getlocal(2));
      var1.setline(326);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"), var1.getlocal(2));
      var1.setline(327);
      if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
         var1.setline(328);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitFrom$43(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(331);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(332);
         PyObject var7 = var1.getlocal(3);
         PyObject var10000 = var7._eq(PyString.fromInterned("*"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(334);
            var10000 = var1.getlocal(2).__getattr__("add_def");
            PyObject var10002 = var1.getlocal(4);
            if (!var10002.__nonzero__()) {
               var10002 = var1.getlocal(3);
            }

            var10000.__call__(var2, var10002);
         }
      }
   }

   public PyObject visitImport$44(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(337);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(338);
         PyObject var7 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(5, var7);
         var5 = null;
         var1.setline(339);
         var7 = var1.getlocal(5);
         PyObject var10000 = var7._gt(Py.newInteger(-1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(340);
            var7 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
            var1.setlocal(3, var7);
            var5 = null;
         }

         var1.setline(341);
         var10000 = var1.getlocal(2).__getattr__("add_def");
         PyObject var10002 = var1.getlocal(4);
         if (!var10002.__nonzero__()) {
            var10002 = var1.getlocal(3);
         }

         var10000.__call__(var2, var10002);
      }
   }

   public PyObject visitGlobal$45(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(344);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(345);
         var1.getlocal(2).__getattr__("add_global").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject visitAssign$46(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyString.fromInterned("Propagate assignment flag down to child nodes.\n\n        The Assign node doesn't itself contains the variables being\n        assigned to.  Instead, the children in node.nodes are visited\n        with the assign flag set to true.  When the names occur in\n        those nodes, they are marked as defs.\n\n        Some names that occur in an assignment target are not bound by\n        the assignment, e.g. a name occurring inside a slice.  The\n        visitor handles these nodes specially; they do not propagate\n        the assign flag to their children.\n        ");
      var1.setline(360);
      PyObject var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(360);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(362);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"), var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(361);
         var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      }
   }

   public PyObject visitAssName$47(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      var1.getlocal(2).__getattr__("add_def").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssAttr$48(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("expr"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitSubscript$49(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("expr"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setline(372);
      PyObject var3 = var1.getlocal(1).__getattr__("subs").__iter__();

      while(true) {
         var1.setline(372);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(373);
         var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      }
   }

   public PyObject visitSlice$50(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("expr"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setline(377);
      if (var1.getlocal(1).__getattr__("lower").__nonzero__()) {
         var1.setline(378);
         var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("lower"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      }

      var1.setline(379);
      if (var1.getlocal(1).__getattr__("upper").__nonzero__()) {
         var1.setline(380);
         var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("upper"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugAssign$51(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("node"), var1.getlocal(2));
      var1.setline(386);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getattr__("node"), var1.getglobal("ast").__getattr__("Name")).__nonzero__()) {
         var1.setline(387);
         var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("node"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      }

      var1.setline(388);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitIf$52(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = var1.getlocal(1).__getattr__("tests").__iter__();

      while(true) {
         do {
            var1.setline(395);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(402);
               if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
                  var1.setline(403);
                  var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"), var1.getlocal(2));
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(396);
            if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("ast").__getattr__("Const")).__nonzero__()) {
               break;
            }

            var1.setline(397);
            PyObject var7 = var1.getglobal("type").__call__(var2, var1.getlocal(3).__getattr__("value"));
            PyObject var10000 = var7._in(var1.getlocal(0).__getattr__("_const_types"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(398);
         } while(var1.getlocal(3).__getattr__("value").__not__().__nonzero__());

         var1.setline(400);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         var1.setline(401);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      }
   }

   public PyObject visitYield$53(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(2).__setattr__((String)"generator", var3);
      var3 = null;
      var1.setline(409);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("value"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject list_eq$54(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyObject var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("sorted").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_names$55(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(420);
      PyList var10001 = new PyList();
      var3 = var10001.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(420);
      var3 = var1.getlocal(0).__getattr__("get_symbols").__call__(var2).__iter__();

      while(true) {
         var1.setline(420);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(420);
            var1.dellocal(3);
            var3 = var10001.__iter__();

            while(true) {
               var1.setline(420);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(420);
                  var1.dellocal(1);
                  PyList var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(2, var4);
               var1.setline(421);
               PyObject var6 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_["));
               if (!var6.__nonzero__()) {
                  var6 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               }

               if (var6.__not__().__nonzero__()) {
                  var1.setline(420);
                  var1.getlocal(1).__call__(var2, var1.getlocal(2));
               }
            }
         }

         var1.setlocal(2, var4);
         var1.setline(420);
         var1.getlocal(3).__call__(var2, var1.getlocal(2).__getattr__("get_name").__call__(var2));
      }
   }

   public symbols$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Scope$1 = Py.newCode(0, var2, var1, "Scope", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "module", "klass", "i"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      mangle$4 = Py.newCode(2, var2, var1, "mangle", 40, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      add_def$5 = Py.newCode(2, var2, var1, "add_def", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      add_use$6 = Py.newCode(2, var2, var1, "add_use", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      add_global$7 = Py.newCode(2, var2, var1, "add_global", 51, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      add_param$8 = Py.newCode(2, var2, var1, "add_param", 61, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      get_names$9 = Py.newCode(1, var2, var1, "get_names", 66, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child"};
      add_child$10 = Py.newCode(2, var2, var1, "add_child", 73, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_children$11 = Py.newCode(1, var2, var1, "get_children", 76, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      DEBUG$12 = Py.newCode(1, var2, var1, "DEBUG", 79, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      check_name$13 = Py.newCode(2, var2, var1, "check_name", 87, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "free", "name"};
      get_free_vars$14 = Py.newCode(1, var2, var1, "get_free_vars", 105, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "frees", "globals", "name"};
      handle_children$15 = Py.newCode(1, var2, var1, "handle_children", 115, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "child"};
      force_global$16 = Py.newCode(2, var2, var1, "force_global", 122, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "child_globals", "name", "sc"};
      add_frees$17 = Py.newCode(2, var2, var1, "add_frees", 142, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_cell_vars$18 = Py.newCode(1, var2, var1, "get_cell_vars", 169, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ModuleScope$19 = Py.newCode(0, var2, var1, "ModuleScope", 172, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$20 = Py.newCode(1, var2, var1, "__init__", 175, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FunctionScope$21 = Py.newCode(0, var2, var1, "FunctionScope", 178, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      GenExprScope$22 = Py.newCode(0, var2, var1, "GenExprScope", 181, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "klass", "i"};
      __init__$23 = Py.newCode(3, var2, var1, "__init__", 186, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "keys"};
      get_names$24 = Py.newCode(1, var2, var1, "get_names", 192, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LambdaScope$25 = Py.newCode(0, var2, var1, "LambdaScope", 196, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "klass", "i"};
      __init__$26 = Py.newCode(3, var2, var1, "__init__", 201, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ClassScope$27 = Py.newCode(0, var2, var1, "ClassScope", 206, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "module"};
      __init__$28 = Py.newCode(3, var2, var1, "__init__", 209, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SymbolVisitor$29 = Py.newCode(0, var2, var1, "SymbolVisitor", 212, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$30 = Py.newCode(1, var2, var1, "__init__", 213, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope"};
      visitModule$31 = Py.newCode(2, var2, var1, "visitModule", 219, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "parent", "n", "scope"};
      visitFunction$32 = Py.newCode(3, var2, var1, "visitFunction", 225, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "parent", "scope"};
      visitGenExpr$33 = Py.newCode(3, var2, var1, "visitGenExpr", 239, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "genfor"};
      visitGenExprInner$34 = Py.newCode(3, var2, var1, "visitGenExprInner", 250, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "if_"};
      visitGenExprFor$35 = Py.newCode(3, var2, var1, "visitGenExprFor", 256, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope"};
      visitGenExprIf$36 = Py.newCode(3, var2, var1, "visitGenExprIf", 262, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "parent", "assign", "n", "scope"};
      visitLambda$37 = Py.newCode(4, var2, var1, "visitLambda", 265, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "scope", "args", "name"};
      _do_args$38 = Py.newCode(3, var2, var1, "_do_args", 281, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "scope", "parent"};
      handle_free_vars$39 = Py.newCode(3, var2, var1, "handle_free_vars", 288, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "parent", "n", "scope", "prev"};
      visitClass$40 = Py.newCode(3, var2, var1, "visitClass", 292, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "assign"};
      visitName$41 = Py.newCode(4, var2, var1, "visitName", 315, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope"};
      visitFor$42 = Py.newCode(3, var2, var1, "visitFor", 323, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "name", "asname"};
      visitFrom$43 = Py.newCode(3, var2, var1, "visitFrom", 330, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "name", "asname", "i"};
      visitImport$44 = Py.newCode(3, var2, var1, "visitImport", 336, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "name"};
      visitGlobal$45 = Py.newCode(3, var2, var1, "visitGlobal", 343, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "n"};
      visitAssign$46 = Py.newCode(3, var2, var1, "visitAssign", 347, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "assign"};
      visitAssName$47 = Py.newCode(4, var2, var1, "visitAssName", 364, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "assign"};
      visitAssAttr$48 = Py.newCode(4, var2, var1, "visitAssAttr", 367, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "assign", "n"};
      visitSubscript$49 = Py.newCode(4, var2, var1, "visitSubscript", 370, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "assign"};
      visitSlice$50 = Py.newCode(4, var2, var1, "visitSlice", 375, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope"};
      visitAugAssign$51 = Py.newCode(3, var2, var1, "visitAugAssign", 382, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope", "test", "body"};
      visitIf$52 = Py.newCode(3, var2, var1, "visitIf", 394, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "scope"};
      visitYield$53 = Py.newCode(3, var2, var1, "visitYield", 407, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"l1", "l2"};
      list_eq$54 = Py.newCode(2, var2, var1, "list_eq", 411, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"syms", "_[420_16]", "s", "_[420_28]"};
      get_names$55 = Py.newCode(1, var2, var1, "get_names", 419, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new symbols$py("compiler/symbols$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(symbols$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Scope$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.mangle$4(var2, var3);
         case 5:
            return this.add_def$5(var2, var3);
         case 6:
            return this.add_use$6(var2, var3);
         case 7:
            return this.add_global$7(var2, var3);
         case 8:
            return this.add_param$8(var2, var3);
         case 9:
            return this.get_names$9(var2, var3);
         case 10:
            return this.add_child$10(var2, var3);
         case 11:
            return this.get_children$11(var2, var3);
         case 12:
            return this.DEBUG$12(var2, var3);
         case 13:
            return this.check_name$13(var2, var3);
         case 14:
            return this.get_free_vars$14(var2, var3);
         case 15:
            return this.handle_children$15(var2, var3);
         case 16:
            return this.force_global$16(var2, var3);
         case 17:
            return this.add_frees$17(var2, var3);
         case 18:
            return this.get_cell_vars$18(var2, var3);
         case 19:
            return this.ModuleScope$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.FunctionScope$21(var2, var3);
         case 22:
            return this.GenExprScope$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.get_names$24(var2, var3);
         case 25:
            return this.LambdaScope$25(var2, var3);
         case 26:
            return this.__init__$26(var2, var3);
         case 27:
            return this.ClassScope$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this.SymbolVisitor$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this.visitModule$31(var2, var3);
         case 32:
            return this.visitFunction$32(var2, var3);
         case 33:
            return this.visitGenExpr$33(var2, var3);
         case 34:
            return this.visitGenExprInner$34(var2, var3);
         case 35:
            return this.visitGenExprFor$35(var2, var3);
         case 36:
            return this.visitGenExprIf$36(var2, var3);
         case 37:
            return this.visitLambda$37(var2, var3);
         case 38:
            return this._do_args$38(var2, var3);
         case 39:
            return this.handle_free_vars$39(var2, var3);
         case 40:
            return this.visitClass$40(var2, var3);
         case 41:
            return this.visitName$41(var2, var3);
         case 42:
            return this.visitFor$42(var2, var3);
         case 43:
            return this.visitFrom$43(var2, var3);
         case 44:
            return this.visitImport$44(var2, var3);
         case 45:
            return this.visitGlobal$45(var2, var3);
         case 46:
            return this.visitAssign$46(var2, var3);
         case 47:
            return this.visitAssName$47(var2, var3);
         case 48:
            return this.visitAssAttr$48(var2, var3);
         case 49:
            return this.visitSubscript$49(var2, var3);
         case 50:
            return this.visitSlice$50(var2, var3);
         case 51:
            return this.visitAugAssign$51(var2, var3);
         case 52:
            return this.visitIf$52(var2, var3);
         case 53:
            return this.visitYield$53(var2, var3);
         case 54:
            return this.list_eq$54(var2, var3);
         case 55:
            return this.get_names$55(var2, var3);
         default:
            return null;
      }
   }
}

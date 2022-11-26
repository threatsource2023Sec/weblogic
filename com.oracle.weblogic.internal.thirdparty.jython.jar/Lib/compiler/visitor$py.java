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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/visitor.py")
public class visitor$py extends PyFunctionTable implements PyRunnable {
   static visitor$py self;
   static final PyCode f$0;
   static final PyCode ASTVisitor$1;
   static final PyCode __init__$2;
   static final PyCode default$3;
   static final PyCode dispatch$4;
   static final PyCode preorder$5;
   static final PyCode ExampleASTVisitor$6;
   static final PyCode dispatch$7;
   static final PyCode walk$8;
   static final PyCode dumpNode$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"ast"};
      PyObject[] var5 = imp.importFrom("compiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ast", var4);
      var4 = null;
      var1.setline(6);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("ASTVisitor", var5, ASTVisitor$1);
      var1.setlocal("ASTVisitor", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(65);
      var5 = new PyObject[]{var1.getname("ASTVisitor")};
      var4 = Py.makeClass("ExampleASTVisitor", var5, ExampleASTVisitor$6);
      var1.setlocal("ExampleASTVisitor", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(100);
      PyObject var6 = var1.getname("ASTVisitor");
      var1.setlocal("_walker", var6);
      var3 = null;
      var1.setline(101);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var5, walk$8, (PyObject)null);
      var1.setlocal("walk", var7);
      var3 = null;
      var1.setline(109);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, dumpNode$9, (PyObject)null);
      var1.setlocal("dumpNode", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ASTVisitor$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Performs a depth-first walk of the AST\n\n    The ASTVisitor will walk the AST, performing either a preorder or\n    postorder traversal depending on which method is called.\n\n    methods:\n    preorder(tree, visitor)\n    postorder(tree, visitor)\n        tree: an instance of ast.Node\n        visitor: an instance with visitXXX methods\n\n    The ASTVisitor is responsible for walking over the tree in the\n    correct order.  For each node, it checks the visitor argument for\n    a method named 'visitNodeType' where NodeType is the name of the\n    node's class, e.g. Class.  If the method exists, it is called\n    with the node as its sole argument.\n\n    The visitor method for a particular node type can control how\n    child nodes are visited during a preorder walk.  (It can't control\n    the order during a postorder walk, because it is called _after_\n    the walk has occurred.)  The ASTVisitor modifies the visitor\n    argument by adding a visit method to the visitor; this method can\n    be used to visit a child node of arbitrary type.\n    "));
      var1.setline(30);
      PyString.fromInterned("Performs a depth-first walk of the AST\n\n    The ASTVisitor will walk the AST, performing either a preorder or\n    postorder traversal depending on which method is called.\n\n    methods:\n    preorder(tree, visitor)\n    postorder(tree, visitor)\n        tree: an instance of ast.Node\n        visitor: an instance with visitXXX methods\n\n    The ASTVisitor is responsible for walking over the tree in the\n    correct order.  For each node, it checks the visitor argument for\n    a method named 'visitNodeType' where NodeType is the name of the\n    node's class, e.g. Class.  If the method exists, it is called\n    with the node as its sole argument.\n\n    The visitor method for a particular node type can control how\n    child nodes are visited during a preorder walk.  (It can't control\n    the order during a postorder walk, because it is called _after_\n    the walk has occurred.)  The ASTVisitor modifies the visitor\n    argument by adding a visit method to the visitor; this method can\n    be used to visit a child node of arbitrary type.\n    ");
      var1.setline(32);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("VERBOSE", var3);
      var3 = null;
      var1.setline(34);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(38);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, default$3, (PyObject)null);
      var1.setlocal("default", var5);
      var3 = null;
      var1.setline(42);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dispatch$4, (PyObject)null);
      var1.setlocal("dispatch", var5);
      var3 = null;
      var1.setline(59);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, preorder$5, PyString.fromInterned("Do preorder walk of tree using visitor"));
      var1.setlocal("preorder", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(36);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_cache", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(1).__getattr__("getChildNodes").__call__(var2).__iter__();

      while(true) {
         var1.setline(39);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(40);
         PyObject var10000 = var1.getlocal(0).__getattr__("dispatch");
         PyObject[] var5 = new PyObject[]{var1.getlocal(3)};
         String[] var6 = new String[0];
         var10000._callextra(var5, var6, var1.getlocal(2), (PyObject)null);
         var5 = null;
      }
   }

   public PyObject dispatch$4(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(1).__getattr__("__class__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getlocal(0).__getattr__("_cache").__getattr__("get").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         var3 = var1.getlocal(3).__getattr__("__name__");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(48);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("visitor"), PyString.fromInterned("visit")._add(var1.getlocal(5)), var1.getlocal(0).__getattr__("default"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(49);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("_cache").__setitem__(var1.getlocal(3), var3);
         var3 = null;
      }

      var1.setline(57);
      var10000 = var1.getlocal(4);
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject preorder$5(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Do preorder walk of tree using visitor");
      var1.setline(61);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("visitor", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(0).__getattr__("dispatch");
      var1.getlocal(2).__setattr__("visit", var3);
      var3 = null;
      var1.setline(63);
      PyObject var10000 = var1.getlocal(0).__getattr__("dispatch");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(3), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ExampleASTVisitor$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Prints examples of the nodes that aren't visited\n\n    This visitor-driver is only useful for development, when it's\n    helpful to develop a visitor incrementally, and get feedback on what\n    you still have to do.\n    "));
      var1.setline(71);
      PyString.fromInterned("Prints examples of the nodes that aren't visited\n\n    This visitor-driver is only useful for development, when it's\n    helpful to develop a visitor incrementally, and get feedback on what\n    you still have to do.\n    ");
      var1.setline(72);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("examples", var3);
      var3 = null;
      var1.setline(74);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, dispatch$7, (PyObject)null);
      var1.setlocal("dispatch", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject dispatch$7(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getlocal(0).__getattr__("_cache").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("__class__"), var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(1).__getattr__("__class__").__getattr__("__name__");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(79);
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("visitor"), (PyObject)PyString.fromInterned("visit")._add(var1.getlocal(4)), (PyObject)Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(80);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__getattr__("_cache").__setitem__(var1.getlocal(1).__getattr__("__class__"), var3);
         var3 = null;
      }

      var1.setline(81);
      var3 = var1.getlocal(0).__getattr__("VERBOSE");
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         Py.printComma(PyString.fromInterned("dispatch"));
         Py.printComma(var1.getlocal(4));
         Object var8 = var1.getlocal(3);
         if (((PyObject)var8).__nonzero__()) {
            var8 = var1.getlocal(3).__getattr__("__name__");
         }

         if (!((PyObject)var8).__nonzero__()) {
            var8 = PyString.fromInterned("");
         }

         Py.println((PyObject)var8);
      }

      var1.setline(83);
      String[] var4;
      PyObject[] var7;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(84);
         var10000 = var1.getlocal(3);
         var7 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[0];
         var10000._callextra(var7, var4, var1.getlocal(2), (PyObject)null);
         var3 = null;
      } else {
         var1.setline(85);
         var3 = var1.getlocal(0).__getattr__("VERBOSE");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(86);
            var3 = var1.getlocal(1).__getattr__("__class__");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(87);
            var3 = var1.getlocal(5);
            var10000 = var3._notin(var1.getlocal(0).__getattr__("examples"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(88);
               var3 = var1.getlocal(5);
               var1.getlocal(0).__getattr__("examples").__setitem__(var1.getlocal(5), var3);
               var3 = null;
               var1.setline(89);
               Py.println();
               var1.setline(90);
               Py.println(var1.getlocal(0).__getattr__("visitor"));
               var1.setline(91);
               Py.println(var1.getlocal(5));
               var1.setline(92);
               var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(1)).__iter__();

               while(true) {
                  var1.setline(92);
                  PyObject var6 = var3.__iternext__();
                  if (var6 == null) {
                     var1.setline(95);
                     Py.println();
                     break;
                  }

                  var1.setlocal(6, var6);
                  var1.setline(93);
                  PyObject var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                  var10000 = var5._ne(PyString.fromInterned("_"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(94);
                     Py.printComma(PyString.fromInterned("\t"));
                     Py.printComma(PyString.fromInterned("%-12.12s")._mod(var1.getlocal(6)));
                     Py.println(var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
                  }
               }
            }

            var1.setline(96);
            var10000 = var1.getlocal(0).__getattr__("default");
            var7 = new PyObject[]{var1.getlocal(1)};
            var4 = new String[0];
            var10000 = var10000._callextra(var7, var4, var1.getlocal(2), (PyObject)null);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject walk$8(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var3 = var1.getglobal("_walker").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(104);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(105);
         var3 = var1.getlocal(3);
         var1.getlocal(2).__setattr__("VERBOSE", var3);
         var3 = null;
      }

      var1.setline(106);
      var1.getlocal(2).__getattr__("preorder").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(107);
      var3 = var1.getlocal(2).__getattr__("visitor");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dumpNode$9(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      Py.println(var1.getlocal(0).__getattr__("__class__"));
      var1.setline(111);
      PyObject var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(111);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(112);
         PyObject var5 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._ne(PyString.fromInterned("_"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(113);
            Py.printComma(PyString.fromInterned("\t"));
            Py.printComma(PyString.fromInterned("%-10.10s")._mod(var1.getlocal(1)));
            Py.println(var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
         }
      }
   }

   public visitor$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ASTVisitor$1 = Py.newCode(0, var2, var1, "ASTVisitor", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "args", "child"};
      default$3 = Py.newCode(3, var2, var1, "default", 38, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "args", "klass", "meth", "className"};
      dispatch$4 = Py.newCode(3, var2, var1, "dispatch", 42, true, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "visitor", "args"};
      preorder$5 = Py.newCode(4, var2, var1, "preorder", 59, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExampleASTVisitor$6 = Py.newCode(0, var2, var1, "ExampleASTVisitor", 65, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "args", "meth", "className", "klass", "attr"};
      dispatch$7 = Py.newCode(3, var2, var1, "dispatch", 74, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tree", "visitor", "walker", "verbose"};
      walk$8 = Py.newCode(4, var2, var1, "walk", 101, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "attr"};
      dumpNode$9 = Py.newCode(1, var2, var1, "dumpNode", 109, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new visitor$py("compiler/visitor$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(visitor$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ASTVisitor$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.default$3(var2, var3);
         case 4:
            return this.dispatch$4(var2, var3);
         case 5:
            return this.preorder$5(var2, var3);
         case 6:
            return this.ExampleASTVisitor$6(var2, var3);
         case 7:
            return this.dispatch$7(var2, var3);
         case 8:
            return this.walk$8(var2, var3);
         case 9:
            return this.dumpNode$9(var2, var3);
         default:
            return null;
      }
   }
}

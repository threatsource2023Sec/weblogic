package lib2to3;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/pytree.py")
public class pytree$py extends PyFunctionTable implements PyRunnable {
   static pytree$py self;
   static final PyCode f$0;
   static final PyCode type_repr$1;
   static final PyCode Base$2;
   static final PyCode __new__$3;
   static final PyCode __eq__$4;
   static final PyCode __ne__$5;
   static final PyCode _eq$6;
   static final PyCode clone$7;
   static final PyCode post_order$8;
   static final PyCode pre_order$9;
   static final PyCode set_prefix$10;
   static final PyCode get_prefix$11;
   static final PyCode replace$12;
   static final PyCode get_lineno$13;
   static final PyCode changed$14;
   static final PyCode remove$15;
   static final PyCode next_sibling$16;
   static final PyCode prev_sibling$17;
   static final PyCode leaves$18;
   static final PyCode depth$19;
   static final PyCode get_suffix$20;
   static final PyCode __str__$21;
   static final PyCode Node$22;
   static final PyCode __init__$23;
   static final PyCode __repr__$24;
   static final PyCode __unicode__$25;
   static final PyCode _eq$26;
   static final PyCode clone$27;
   static final PyCode post_order$28;
   static final PyCode pre_order$29;
   static final PyCode _prefix_getter$30;
   static final PyCode _prefix_setter$31;
   static final PyCode set_child$32;
   static final PyCode insert_child$33;
   static final PyCode append_child$34;
   static final PyCode Leaf$35;
   static final PyCode __init__$36;
   static final PyCode __repr__$37;
   static final PyCode __unicode__$38;
   static final PyCode _eq$39;
   static final PyCode clone$40;
   static final PyCode leaves$41;
   static final PyCode post_order$42;
   static final PyCode pre_order$43;
   static final PyCode _prefix_getter$44;
   static final PyCode _prefix_setter$45;
   static final PyCode convert$46;
   static final PyCode BasePattern$47;
   static final PyCode __new__$48;
   static final PyCode __repr__$49;
   static final PyCode optimize$50;
   static final PyCode match$51;
   static final PyCode match_seq$52;
   static final PyCode generate_matches$53;
   static final PyCode LeafPattern$54;
   static final PyCode __init__$55;
   static final PyCode match$56;
   static final PyCode _submatch$57;
   static final PyCode NodePattern$58;
   static final PyCode __init__$59;
   static final PyCode _submatch$60;
   static final PyCode WildcardPattern$61;
   static final PyCode __init__$62;
   static final PyCode optimize$63;
   static final PyCode match$64;
   static final PyCode match_seq$65;
   static final PyCode generate_matches$66;
   static final PyCode _iterative_matches$67;
   static final PyCode _bare_name_matches$68;
   static final PyCode _recursive_matches$69;
   static final PyCode NegatedPattern$70;
   static final PyCode __init__$71;
   static final PyCode match$72;
   static final PyCode match_seq$73;
   static final PyCode generate_matches$74;
   static final PyCode generate_matches$75;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nPython parse tree definitions.\n\nThis is a very concrete parse tree; we need to keep every token and\neven the comments and whitespace between tokens.\n\nThere's also a pattern matching implementation here.\n"));
      var1.setline(11);
      PyString.fromInterned("\nPython parse tree definitions.\n\nThis is a very concrete parse tree; we need to keep every token and\neven the comments and whitespace between tokens.\n\nThere's also a pattern matching implementation here.\n");
      var1.setline(13);
      PyString var3 = PyString.fromInterned("Guido van Rossum <guido@python.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(15);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(16);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(17);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("StringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(19);
      PyInteger var8 = Py.newInteger(Integer.MAX_VALUE);
      var1.setlocal("HUGE", var8);
      var3 = null;
      var1.setline(21);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_type_reprs", var9);
      var3 = null;
      var1.setline(22);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, type_repr$1, (PyObject)null);
      var1.setlocal("type_repr", var10);
      var3 = null;
      var1.setline(32);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Base", var7, Base$2);
      var1.setlocal("Base", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(239);
      var7 = new PyObject[]{var1.getname("Base")};
      var4 = Py.makeClass("Node", var7, Node$22);
      var1.setlocal("Node", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(351);
      var7 = new PyObject[]{var1.getname("Base")};
      var4 = Py.makeClass("Leaf", var7, Leaf$35);
      var1.setlocal("Leaf", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(429);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, convert$46, PyString.fromInterned("\n    Convert raw node information to a Node or Leaf instance.\n\n    This is passed to the parser driver which calls it whenever a reduction of a\n    grammar rule produces a new complete node, so that the tree is build\n    strictly bottom-up.\n    "));
      var1.setlocal("convert", var10);
      var3 = null;
      var1.setline(448);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BasePattern", var7, BasePattern$47);
      var1.setlocal("BasePattern", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(534);
      var7 = new PyObject[]{var1.getname("BasePattern")};
      var4 = Py.makeClass("LeafPattern", var7, LeafPattern$54);
      var1.setlocal("LeafPattern", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(578);
      var7 = new PyObject[]{var1.getname("BasePattern")};
      var4 = Py.makeClass("NodePattern", var7, NodePattern$58);
      var1.setlocal("NodePattern", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(639);
      var7 = new PyObject[]{var1.getname("BasePattern")};
      var4 = Py.makeClass("WildcardPattern", var7, WildcardPattern$61);
      var1.setlocal("WildcardPattern", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(827);
      var7 = new PyObject[]{var1.getname("BasePattern")};
      var4 = Py.makeClass("NegatedPattern", var7, NegatedPattern$70);
      var1.setlocal("NegatedPattern", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(862);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, generate_matches$75, PyString.fromInterned("\n    Generator yielding matches for a sequence of patterns and nodes.\n\n    Args:\n        patterns: a sequence of patterns\n        nodes: a sequence of nodes\n\n    Yields:\n        (count, results) tuples where:\n        count: the entire sequence of patterns matches nodes[:count];\n        results: dict containing named submatches.\n        "));
      var1.setlocal("generate_matches", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject type_repr$1(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var8;
      if (var1.getglobal("_type_reprs").__not__().__nonzero__()) {
         var1.setline(25);
         String[] var3 = new String[]{"python_symbols"};
         PyObject[] var7 = imp.importFrom("pygram", var3, var1, 1);
         PyObject var4 = var7[0];
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(28);
         var8 = var1.getlocal(1).__getattr__("__dict__").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(28);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(29);
            PyObject var9 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
            PyObject var10000 = var9._eq(var1.getglobal("int"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(29);
               var9 = var1.getlocal(2);
               var1.getglobal("_type_reprs").__setitem__(var1.getlocal(3), var9);
               var5 = null;
            }
         }
      }

      var1.setline(30);
      var8 = var1.getglobal("_type_reprs").__getattr__("setdefault").__call__(var2, var1.getlocal(0), var1.getlocal(0));
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject Base$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Abstract base class for Node and Leaf.\n\n    This provides some default functionality and boilerplate using the\n    template pattern.\n\n    A node may be a subnode of at most one parent.\n    "));
      var1.setline(41);
      PyString.fromInterned("\n    Abstract base class for Node and Leaf.\n\n    This provides some default functionality and boilerplate using the\n    template pattern.\n\n    A node may be a subnode of at most one parent.\n    ");
      var1.setline(44);
      PyObject var3 = var1.getname("None");
      var1.setlocal("type", var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getname("None");
      var1.setlocal("parent", var3);
      var3 = null;
      var1.setline(46);
      PyTuple var4 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("children", var4);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("False");
      var1.setlocal("was_changed", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("False");
      var1.setlocal("was_checked", var3);
      var3 = null;
      var1.setline(50);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __new__$3, PyString.fromInterned("Constructor that prevents Base from being instantiated."));
      var1.setlocal("__new__", var6);
      var3 = null;
      var1.setline(55);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __eq__$4, PyString.fromInterned("\n        Compare two nodes for equality.\n\n        This calls the method _eq().\n        "));
      var1.setlocal("__eq__", var6);
      var3 = null;
      var1.setline(65);
      var3 = var1.getname("None");
      var1.setlocal("__hash__", var3);
      var3 = null;
      var1.setline(67);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __ne__$5, PyString.fromInterned("\n        Compare two nodes for inequality.\n\n        This calls the method _eq().\n        "));
      var1.setlocal("__ne__", var6);
      var3 = null;
      var1.setline(77);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _eq$6, PyString.fromInterned("\n        Compare two nodes for equality.\n\n        This is called by __eq__ and __ne__.  It is only called if the two nodes\n        have the same type.  This must be implemented by the concrete subclass.\n        Nodes should be considered equal if they have the same structure,\n        ignoring the prefix string and other context information.\n        "));
      var1.setlocal("_eq", var6);
      var3 = null;
      var1.setline(88);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, clone$7, PyString.fromInterned("\n        Return a cloned (deep) copy of self.\n\n        This must be implemented by the concrete subclass.\n        "));
      var1.setlocal("clone", var6);
      var3 = null;
      var1.setline(96);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, post_order$8, PyString.fromInterned("\n        Return a post-order iterator for the tree.\n\n        This must be implemented by the concrete subclass.\n        "));
      var1.setlocal("post_order", var6);
      var3 = null;
      var1.setline(104);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, pre_order$9, PyString.fromInterned("\n        Return a pre-order iterator for the tree.\n\n        This must be implemented by the concrete subclass.\n        "));
      var1.setlocal("pre_order", var6);
      var3 = null;
      var1.setline(112);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_prefix$10, PyString.fromInterned("\n        Set the prefix for the node (see Leaf class).\n\n        DEPRECATED; use the prefix property directly.\n        "));
      var1.setlocal("set_prefix", var6);
      var3 = null;
      var1.setline(122);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_prefix$11, PyString.fromInterned("\n        Return the prefix for the node (see Leaf class).\n\n        DEPRECATED; use the prefix property directly.\n        "));
      var1.setlocal("get_prefix", var6);
      var3 = null;
      var1.setline(132);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, replace$12, PyString.fromInterned("Replace this node with a new one in the parent."));
      var1.setlocal("replace", var6);
      var3 = null;
      var1.setline(155);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_lineno$13, PyString.fromInterned("Return the line number which generated the invocant node."));
      var1.setlocal("get_lineno", var6);
      var3 = null;
      var1.setline(164);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, changed$14, (PyObject)null);
      var1.setlocal("changed", var6);
      var3 = null;
      var1.setline(169);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, remove$15, PyString.fromInterned("\n        Remove the node from the tree. Returns the position of the node in its\n        parent's children before it was removed.\n        "));
      var1.setlocal("remove", var6);
      var3 = null;
      var1.setline(182);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, next_sibling$16, PyString.fromInterned("\n        The node immediately following the invocant in their parent's children\n        list. If the invocant does not have a next sibling, it is None\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("next_sibling", var3);
      var3 = null;
      var1.setline(199);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prev_sibling$17, PyString.fromInterned("\n        The node immediately preceding the invocant in their parent's children\n        list. If the invocant does not have a previous sibling, it is None.\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("prev_sibling", var3);
      var3 = null;
      var1.setline(215);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, leaves$18, (PyObject)null);
      var1.setlocal("leaves", var6);
      var3 = null;
      var1.setline(220);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, depth$19, (PyObject)null);
      var1.setlocal("depth", var6);
      var3 = null;
      var1.setline(225);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_suffix$20, PyString.fromInterned("\n        Return the string immediately following the invocant node. This is\n        effectively equivalent to node.next_sibling.prefix\n        "));
      var1.setlocal("get_suffix", var6);
      var3 = null;
      var1.setline(235);
      var3 = var1.getname("sys").__getattr__("version_info");
      PyObject var10000 = var3._lt(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(236);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, __str__$21, (PyObject)null);
         var1.setlocal("__str__", var6);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __new__$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Constructor that prevents Base from being instantiated.");
      var1.setline(52);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0);
         PyObject var10000 = var3._isnot(var1.getglobal("Base"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Cannot instantiate Base"));
         }
      }

      var1.setline(53);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$4(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("\n        Compare two nodes for equality.\n\n        This calls the method _eq().\n        ");
      var1.setline(61);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._isnot(var1.getlocal(1).__getattr__("__class__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(62);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(63);
         var3 = var1.getlocal(0).__getattr__("_eq").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$5(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("\n        Compare two nodes for inequality.\n\n        This calls the method _eq().\n        ");
      var1.setline(73);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._isnot(var1.getlocal(1).__getattr__("__class__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(74);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(75);
         var3 = var1.getlocal(0).__getattr__("_eq").__call__(var2, var1.getlocal(1)).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _eq$6(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("\n        Compare two nodes for equality.\n\n        This is called by __eq__ and __ne__.  It is only called if the two nodes\n        have the same type.  This must be implemented by the concrete subclass.\n        Nodes should be considered equal if they have the same structure,\n        ignoring the prefix string and other context information.\n        ");
      var1.setline(86);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject clone$7(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("\n        Return a cloned (deep) copy of self.\n\n        This must be implemented by the concrete subclass.\n        ");
      var1.setline(94);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject post_order$8(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("\n        Return a post-order iterator for the tree.\n\n        This must be implemented by the concrete subclass.\n        ");
      var1.setline(102);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject pre_order$9(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("\n        Return a pre-order iterator for the tree.\n\n        This must be implemented by the concrete subclass.\n        ");
      var1.setline(110);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject set_prefix$10(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("\n        Set the prefix for the node (see Leaf class).\n\n        DEPRECATED; use the prefix property directly.\n        ");
      var1.setline(118);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("set_prefix() is deprecated; use the prefix property"), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(120);
      PyObject var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("prefix", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_prefix$11(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("\n        Return the prefix for the node (see Leaf class).\n\n        DEPRECATED; use the prefix property directly.\n        ");
      var1.setline(128);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("get_prefix() is deprecated; use the prefix property"), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(130);
      PyObject var5 = var1.getlocal(0).__getattr__("prefix");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject replace$12(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Replace this node with a new one in the parent.");
      var1.setline(134);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("parent");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         }
      }

      var1.setline(135);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(136);
      PyList var6;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(137);
         var6 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(138);
      var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(139);
      var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("parent").__getattr__("children").__iter__();

      while(true) {
         var1.setline(140);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(148);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(3).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("children"), var1.getlocal(0), var1.getlocal(1)}));
            } else {
               var1.setline(149);
               var1.getlocal(0).__getattr__("parent").__getattr__("changed").__call__(var2);
               var1.setline(150);
               var3 = var1.getlocal(2);
               var1.getlocal(0).__getattr__("parent").__setattr__("children", var3);
               var3 = null;
               var1.setline(151);
               var3 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(151);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(153);
                     var3 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("parent", var3);
                     var3 = null;
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(152);
                  var5 = var1.getlocal(0).__getattr__("parent");
                  var1.getlocal(5).__setattr__("parent", var5);
                  var5 = null;
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(141);
         var5 = var1.getlocal(4);
         var10000 = var5._is(var1.getlocal(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(142);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(3).__not__().__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("parent").__getattr__("children"), var1.getlocal(0), var1.getlocal(1)}));
            }

            var1.setline(143);
            var5 = var1.getlocal(1);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(144);
               var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
            }

            var1.setline(145);
            var5 = var1.getglobal("True");
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(147);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject get_lineno$13(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyString.fromInterned("Return the line number which generated the invocant node.");
      var1.setline(157);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(158);
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Leaf")).__not__().__nonzero__()) {
            var1.setline(162);
            var3 = var1.getlocal(1).__getattr__("lineno");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(159);
         if (var1.getlocal(1).__getattr__("children").__not__().__nonzero__()) {
            var1.setline(160);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(161);
         var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject changed$14(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      if (var1.getlocal(0).__getattr__("parent").__nonzero__()) {
         var1.setline(166);
         var1.getlocal(0).__getattr__("parent").__getattr__("changed").__call__(var2);
      }

      var1.setline(167);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("was_changed", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject remove$15(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("\n        Remove the node from the tree. Returns the position of the node in its\n        parent's children before it was removed.\n        ");
      var1.setline(174);
      if (var1.getlocal(0).__getattr__("parent").__nonzero__()) {
         var1.setline(175);
         PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("parent").__getattr__("children")).__iter__();

         while(true) {
            var1.setline(175);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(176);
            PyObject var7 = var1.getlocal(2);
            PyObject var10000 = var7._is(var1.getlocal(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(177);
               var1.getlocal(0).__getattr__("parent").__getattr__("changed").__call__(var2);
               var1.setline(178);
               var1.getlocal(0).__getattr__("parent").__getattr__("children").__delitem__(var1.getlocal(1));
               var1.setline(179);
               var7 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("parent", var7);
               var5 = null;
               var1.setline(180);
               var7 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var7;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject next_sibling$16(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("\n        The node immediately following the invocant in their parent's children\n        list. If the invocant does not have a next sibling, it is None\n        ");
      var1.setline(188);
      PyObject var3 = var1.getlocal(0).__getattr__("parent");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(189);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(192);
         PyObject var4 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("parent").__getattr__("children")).__iter__();

         do {
            var1.setline(192);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(1, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(2, var7);
            var7 = null;
            var1.setline(193);
            PyObject var9 = var1.getlocal(2);
            var10000 = var9._is(var1.getlocal(0));
            var6 = null;
         } while(!var10000.__nonzero__());

         try {
            var1.setline(195);
            var3 = var1.getlocal(0).__getattr__("parent").__getattr__("children").__getitem__(var1.getlocal(1)._add(Py.newInteger(1)));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (var10.match(var1.getglobal("IndexError"))) {
               var1.setline(197);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var10;
            }
         }
      }
   }

   public PyObject prev_sibling$17(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyString.fromInterned("\n        The node immediately preceding the invocant in their parent's children\n        list. If the invocant does not have a previous sibling, it is None.\n        ");
      var1.setline(205);
      PyObject var3 = var1.getlocal(0).__getattr__("parent");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(206);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(209);
         PyObject var4 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("parent").__getattr__("children")).__iter__();

         PyObject[] var6;
         PyObject var8;
         do {
            var1.setline(209);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(1, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(2, var7);
            var7 = null;
            var1.setline(210);
            var8 = var1.getlocal(2);
            var10000 = var8._is(var1.getlocal(0));
            var6 = null;
         } while(!var10000.__nonzero__());

         var1.setline(211);
         var8 = var1.getlocal(1);
         var10000 = var8._eq(Py.newInteger(0));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(212);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(213);
            var3 = var1.getlocal(0).__getattr__("parent").__getattr__("children").__getitem__(var1.getlocal(1)._sub(Py.newInteger(1)));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject leaves$18(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(216);
            var3 = var1.getlocal(0).__getattr__("children").__iter__();
            var1.setline(216);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var4);
            var1.setline(217);
            var5 = var1.getlocal(1).__getattr__("leaves").__call__(var2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(217);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(2, var6);
            var1.setline(218);
            var1.setline(218);
            var8 = var1.getlocal(2);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var8;
         }

         var1.setline(216);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(217);
         var5 = var1.getlocal(1).__getattr__("leaves").__call__(var2).__iter__();
      }
   }

   public PyObject depth$19(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyObject var3 = var1.getlocal(0).__getattr__("parent");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(222);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(223);
         var3 = Py.newInteger(1)._add(var1.getlocal(0).__getattr__("parent").__getattr__("depth").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_suffix$20(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyString.fromInterned("\n        Return the string immediately following the invocant node. This is\n        effectively equivalent to node.next_sibling.prefix\n        ");
      var1.setline(230);
      PyObject var3 = var1.getlocal(0).__getattr__("next_sibling");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(231);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(232);
         PyUnicode var4 = PyUnicode.fromInterned("");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(233);
         var3 = var1.getlocal(1).__getattr__("prefix");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __str__$21(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(0)).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Node$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete implementation for interior nodes."));
      var1.setline(241);
      PyString.fromInterned("Concrete implementation for interior nodes.");
      var1.setline(243);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, PyString.fromInterned("\n        Initializer.\n\n        Takes a type constant (a symbol number >= 256), a sequence of\n        child nodes, and an optional context keyword argument.\n\n        As a side effect, the parent pointers of the children are updated.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$24, PyString.fromInterned("Return a canonical string representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(274);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __unicode__$25, PyString.fromInterned("\n        Return a pretty string representation.\n\n        This reproduces the input source exactly.\n        "));
      var1.setlocal("__unicode__", var4);
      var3 = null;
      var1.setline(282);
      PyObject var5 = var1.getname("sys").__getattr__("version_info");
      PyObject var10000 = var5._gt(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(283);
         var5 = var1.getname("__unicode__");
         var1.setlocal("__str__", var5);
         var3 = null;
      }

      var1.setline(285);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _eq$26, PyString.fromInterned("Compare two nodes for equality."));
      var1.setlocal("_eq", var4);
      var3 = null;
      var1.setline(289);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clone$27, PyString.fromInterned("Return a cloned (deep) copy of self."));
      var1.setlocal("clone", var4);
      var3 = null;
      var1.setline(294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, post_order$28, PyString.fromInterned("Return a post-order iterator for the tree."));
      var1.setlocal("post_order", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pre_order$29, PyString.fromInterned("Return a pre-order iterator for the tree."));
      var1.setlocal("pre_order", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _prefix_getter$30, PyString.fromInterned("\n        The whitespace and comments preceding this node in the input.\n        "));
      var1.setlocal("_prefix_getter", var4);
      var3 = null;
      var1.setline(316);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _prefix_setter$31, (PyObject)null);
      var1.setlocal("_prefix_setter", var4);
      var3 = null;
      var1.setline(320);
      var5 = var1.getname("property").__call__(var2, var1.getname("_prefix_getter"), var1.getname("_prefix_setter"));
      var1.setlocal("prefix", var5);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_child$32, PyString.fromInterned("\n        Equivalent to 'node.children[i] = child'. This method also sets the\n        child's parent attribute appropriately.\n        "));
      var1.setlocal("set_child", var4);
      var3 = null;
      var1.setline(332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insert_child$33, PyString.fromInterned("\n        Equivalent to 'node.children.insert(i, child)'. This method also sets\n        the child's parent attribute appropriately.\n        "));
      var1.setlocal("insert_child", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append_child$34, PyString.fromInterned("\n        Equivalent to 'node.children.append(child)'. This method also sets the\n        child's parent attribute appropriately.\n        "));
      var1.setlocal("append_child", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyString.fromInterned("\n        Initializer.\n\n        Takes a type constant (a symbol number >= 256), a sequence of\n        child nodes, and an optional context keyword argument.\n\n        As a side effect, the parent pointers of the children are updated.\n        ");
      var1.setline(255);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ge(Py.newInteger(256));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
         }
      }

      var1.setline(256);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(257);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("children", var3);
      var3 = null;
      var1.setline(258);
      var3 = var1.getlocal(0).__getattr__("children").__iter__();

      while(true) {
         var1.setline(258);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(261);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(262);
               var3 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("prefix", var3);
               var3 = null;
            }

            var1.setline(263);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(264);
               var3 = var1.getlocal(5).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("fixers_applied", var3);
               var3 = null;
            } else {
               var1.setline(266);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("fixers_applied", var3);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(259);
         PyObject var5;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var5 = var1.getlocal(6).__getattr__("parent");
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(6)));
            }
         }

         var1.setline(260);
         var5 = var1.getlocal(0);
         var1.getlocal(6).__setattr__("parent", var5);
         var5 = null;
      }
   }

   public PyObject __repr__$24(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyString.fromInterned("Return a canonical string representation.");
      var1.setline(270);
      PyObject var3 = PyString.fromInterned("%s(%s, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("type_repr").__call__(var2, var1.getlocal(0).__getattr__("type")), var1.getlocal(0).__getattr__("children")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __unicode__$25(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyString.fromInterned("\n        Return a pretty string representation.\n\n        This reproduces the input source exactly.\n        ");
      var1.setline(280);
      PyObject var3 = PyUnicode.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("unicode"), var1.getlocal(0).__getattr__("children")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _eq$26(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyString.fromInterned("Compare two nodes for equality.");
      var1.setline(287);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("children")});
      PyObject var10000 = var3._eq(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("type"), var1.getlocal(1).__getattr__("children")}));
      var3 = null;
      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject clone$27(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("Return a cloned (deep) copy of self.");
      var1.setline(291);
      PyObject var10000 = var1.getglobal("Node");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("type"), null, null};
      PyList var10002 = new PyList();
      PyObject var4 = var10002.__getattr__("append");
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(291);
      var4 = var1.getlocal(0).__getattr__("children").__iter__();

      while(true) {
         var1.setline(291);
         PyObject var5 = var4.__iternext__();
         if (var5 == null) {
            var1.setline(291);
            var1.dellocal(1);
            var3[1] = var10002;
            var3[2] = var1.getlocal(0).__getattr__("fixers_applied");
            String[] var7 = new String[]{"fixers_applied"};
            var10000 = var10000.__call__(var2, var3, var7);
            var3 = null;
            PyObject var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var5);
         var1.setline(291);
         var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("clone").__call__(var2));
      }
   }

   public PyObject post_order$28(PyFrame var1, ThreadState var2) {
      Object var10000;
      Object[] var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(295);
            PyString.fromInterned("Return a post-order iterator for the tree.");
            var1.setline(296);
            var8 = var1.getlocal(0).__getattr__("children").__iter__();
            var1.setline(296);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(299);
               var1.setline(299);
               var9 = var1.getlocal(0);
               var1.f_lasti = 2;
               var3 = new Object[8];
               var1.f_savedlocals = var3;
               return var9;
            }

            var1.setlocal(1, var4);
            var1.setline(297);
            var5 = var1.getlocal(1).__getattr__("post_order").__call__(var2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var8 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
            break;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var9 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }

      while(true) {
         var1.setline(297);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(2, var6);
            var1.setline(298);
            var1.setline(298);
            var9 = var1.getlocal(2);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var8, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var9;
         }

         var1.setline(296);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(299);
            var1.setline(299);
            var9 = var1.getlocal(0);
            var1.f_lasti = 2;
            var3 = new Object[8];
            var1.f_savedlocals = var3;
            return var9;
         }

         var1.setlocal(1, var4);
         var1.setline(297);
         var5 = var1.getlocal(1).__getattr__("post_order").__call__(var2).__iter__();
      }
   }

   public PyObject pre_order$29(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      Object[] var8;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(302);
            PyString.fromInterned("Return a pre-order iterator for the tree.");
            var1.setline(303);
            var1.setline(303);
            var9 = var1.getlocal(0);
            var1.f_lasti = 1;
            var8 = new Object[3];
            var1.f_savedlocals = var8;
            return var9;
         case 1:
            var8 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
            var1.setline(304);
            var3 = var1.getlocal(0).__getattr__("children").__iter__();
            var1.setline(304);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var4);
            var1.setline(305);
            var5 = var1.getlocal(1).__getattr__("pre_order").__call__(var2).__iter__();
            break;
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

            var9 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(305);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(2, var6);
            var1.setline(306);
            var1.setline(306);
            var9 = var1.getlocal(2);
            var1.f_lasti = 2;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var9;
         }

         var1.setline(304);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(305);
         var5 = var1.getlocal(1).__getattr__("pre_order").__call__(var2).__iter__();
      }
   }

   public PyObject _prefix_getter$30(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyString.fromInterned("\n        The whitespace and comments preceding this node in the input.\n        ");
      var1.setline(312);
      if (var1.getlocal(0).__getattr__("children").__not__().__nonzero__()) {
         var1.setline(313);
         PyString var4 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(314);
         PyObject var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("prefix");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _prefix_setter$31(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      if (var1.getlocal(0).__getattr__("children").__nonzero__()) {
         var1.setline(318);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__setattr__("prefix", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_child$32(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned("\n        Equivalent to 'node.children[i] = child'. This method also sets the\n        child's parent attribute appropriately.\n        ");
      var1.setline(327);
      PyObject var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("parent", var3);
      var3 = null;
      var1.setline(328);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__getattr__("children").__getitem__(var1.getlocal(1)).__setattr__("parent", var3);
      var3 = null;
      var1.setline(329);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("children").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(330);
      var1.getlocal(0).__getattr__("changed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insert_child$33(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyString.fromInterned("\n        Equivalent to 'node.children.insert(i, child)'. This method also sets\n        the child's parent attribute appropriately.\n        ");
      var1.setline(337);
      PyObject var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("parent", var3);
      var3 = null;
      var1.setline(338);
      var1.getlocal(0).__getattr__("children").__getattr__("insert").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(339);
      var1.getlocal(0).__getattr__("changed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append_child$34(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyString.fromInterned("\n        Equivalent to 'node.children.append(child)'. This method also sets the\n        child's parent attribute appropriately.\n        ");
      var1.setline(346);
      PyObject var3 = var1.getlocal(0);
      var1.getlocal(1).__setattr__("parent", var3);
      var3 = null;
      var1.setline(347);
      var1.getlocal(0).__getattr__("children").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(348);
      var1.getlocal(0).__getattr__("changed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Leaf$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete implementation for leaf nodes."));
      var1.setline(353);
      PyString.fromInterned("Concrete implementation for leaf nodes.");
      var1.setline(356);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("_prefix", var3);
      var3 = null;
      var1.setline(357);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("lineno", var4);
      var3 = null;
      var1.setline(358);
      var4 = Py.newInteger(0);
      var1.setlocal("column", var4);
      var3 = null;
      var1.setline(360);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), new PyList(Py.EmptyObjects)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$36, PyString.fromInterned("\n        Initializer.\n\n        Takes a type constant (a token number < 256), a string value, and an\n        optional context keyword argument.\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(379);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __repr__$37, PyString.fromInterned("Return a canonical string representation."));
      var1.setlocal("__repr__", var6);
      var3 = null;
      var1.setline(385);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __unicode__$38, PyString.fromInterned("\n        Return a pretty string representation.\n\n        This reproduces the input source exactly.\n        "));
      var1.setlocal("__unicode__", var6);
      var3 = null;
      var1.setline(393);
      PyObject var7 = var1.getname("sys").__getattr__("version_info");
      PyObject var10000 = var7._gt(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(394);
         var7 = var1.getname("__unicode__");
         var1.setlocal("__str__", var7);
         var3 = null;
      }

      var1.setline(396);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _eq$39, PyString.fromInterned("Compare two nodes for equality."));
      var1.setlocal("_eq", var6);
      var3 = null;
      var1.setline(400);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, clone$40, PyString.fromInterned("Return a cloned (deep) copy of self."));
      var1.setlocal("clone", var6);
      var3 = null;
      var1.setline(406);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, leaves$41, (PyObject)null);
      var1.setlocal("leaves", var6);
      var3 = null;
      var1.setline(409);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, post_order$42, PyString.fromInterned("Return a post-order iterator for the tree."));
      var1.setlocal("post_order", var6);
      var3 = null;
      var1.setline(413);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, pre_order$43, PyString.fromInterned("Return a pre-order iterator for the tree."));
      var1.setlocal("pre_order", var6);
      var3 = null;
      var1.setline(417);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _prefix_getter$44, PyString.fromInterned("\n        The whitespace and comments preceding this token in the input.\n        "));
      var1.setlocal("_prefix_getter", var6);
      var3 = null;
      var1.setline(423);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _prefix_setter$45, (PyObject)null);
      var1.setlocal("_prefix_setter", var6);
      var3 = null;
      var1.setline(427);
      var7 = var1.getname("property").__call__(var2, var1.getname("_prefix_getter"), var1.getname("_prefix_setter"));
      var1.setlocal("prefix", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyString.fromInterned("\n        Initializer.\n\n        Takes a type constant (a token number < 256), a string value, and an\n        optional context keyword argument.\n        ");
      var1.setline(370);
      PyInteger var3;
      PyObject var8;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(1);
         PyInteger var10000 = var3;
         var8 = var10001;
         PyObject var4;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var8._lt(Py.newInteger(256));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
         }
      }

      var1.setline(371);
      var8 = var1.getlocal(3);
      PyObject var10 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10.__nonzero__()) {
         var1.setline(372);
         var8 = var1.getlocal(3);
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var5 = var9[0];
         var1.getlocal(0).__setattr__("_prefix", var5);
         var5 = null;
         var5 = var9[1];
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.getlocal(0).__setattr__("lineno", var7);
         var7 = null;
         var7 = var6[1];
         var1.getlocal(0).__setattr__("column", var7);
         var7 = null;
         var5 = null;
         var3 = null;
      }

      var1.setline(373);
      var8 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("type", var8);
      var3 = null;
      var1.setline(374);
      var8 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("value", var8);
      var3 = null;
      var1.setline(375);
      var8 = var1.getlocal(4);
      var10 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10.__nonzero__()) {
         var1.setline(376);
         var8 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("_prefix", var8);
         var3 = null;
      }

      var1.setline(377);
      var8 = var1.getlocal(5).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("fixers_applied", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$37(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyString.fromInterned("Return a canonical string representation.");
      var1.setline(381);
      PyObject var3 = PyString.fromInterned("%s(%r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("value")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __unicode__$38(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyString.fromInterned("\n        Return a pretty string representation.\n\n        This reproduces the input source exactly.\n        ");
      var1.setline(391);
      PyObject var3 = var1.getlocal(0).__getattr__("prefix")._add(var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("value")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _eq$39(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyString.fromInterned("Compare two nodes for equality.");
      var1.setline(398);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("value")});
      PyObject var10000 = var3._eq(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("type"), var1.getlocal(1).__getattr__("value")}));
      var3 = null;
      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject clone$40(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyString.fromInterned("Return a cloned (deep) copy of self.");
      var1.setline(402);
      PyObject var10000 = var1.getglobal("Leaf");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("value"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("prefix"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("lineno"), var1.getlocal(0).__getattr__("column")})}), var1.getlocal(0).__getattr__("fixers_applied")};
      String[] var4 = new String[]{"fixers_applied"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject leaves$41(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(407);
            var1.setline(407);
            var4 = var1.getlocal(0);
            var1.f_lasti = 1;
            var3 = new Object[3];
            var1.f_savedlocals = var3;
            return var4;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var4 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject post_order$42(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(410);
            PyString.fromInterned("Return a post-order iterator for the tree.");
            var1.setline(411);
            var1.setline(411);
            var4 = var1.getlocal(0);
            var1.f_lasti = 1;
            var3 = new Object[3];
            var1.f_savedlocals = var3;
            return var4;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var4 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject pre_order$43(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(414);
            PyString.fromInterned("Return a pre-order iterator for the tree.");
            var1.setline(415);
            var1.setline(415);
            var4 = var1.getlocal(0);
            var1.f_lasti = 1;
            var3 = new Object[3];
            var1.f_savedlocals = var3;
            return var4;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var4 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject _prefix_getter$44(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyString.fromInterned("\n        The whitespace and comments preceding this token in the input.\n        ");
      var1.setline(421);
      PyObject var3 = var1.getlocal(0).__getattr__("_prefix");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _prefix_setter$45(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      var1.getlocal(0).__getattr__("changed").__call__(var2);
      var1.setline(425);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_prefix", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject convert$46(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyString.fromInterned("\n    Convert raw node information to a Node or Leaf instance.\n\n    This is passed to the parser driver which calls it whenever a reduction of a\n    grammar rule produces a new complete node, so that the tree is build\n    strictly bottom-up.\n    ");
      var1.setline(437);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(438);
      PyObject var10000 = var1.getlocal(5);
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("number2symbol"));
         var3 = null;
      }

      String[] var6;
      if (var10000.__nonzero__()) {
         var1.setline(441);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(442);
            var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(443);
            var10000 = var1.getglobal("Node");
            var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(5), var1.getlocal(4)};
            var6 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var4, var6);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(445);
         var10000 = var1.getglobal("Leaf");
         var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
         var6 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var6);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject BasePattern$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A pattern is a tree matching pattern.\n\n    It looks for a specific node type (token or symbol), and\n    optionally for a specific content.\n\n    This is an abstract base class.  There are three concrete\n    subclasses:\n\n    - LeafPattern matches a single leaf node;\n    - NodePattern matches a single node (usually non-leaf);\n    - WildcardPattern matches a sequence of nodes of variable length.\n    "));
      var1.setline(462);
      PyString.fromInterned("\n    A pattern is a tree matching pattern.\n\n    It looks for a specific node type (token or symbol), and\n    optionally for a specific content.\n\n    This is an abstract base class.  There are three concrete\n    subclasses:\n\n    - LeafPattern matches a single leaf node;\n    - NodePattern matches a single node (usually non-leaf);\n    - WildcardPattern matches a sequence of nodes of variable length.\n    ");
      var1.setline(465);
      PyObject var3 = var1.getname("None");
      var1.setlocal("type", var3);
      var3 = null;
      var1.setline(466);
      var3 = var1.getname("None");
      var1.setlocal("content", var3);
      var3 = null;
      var1.setline(467);
      var3 = var1.getname("None");
      var1.setlocal("name", var3);
      var3 = null;
      var1.setline(469);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$48, PyString.fromInterned("Constructor that prevents BasePattern from being instantiated."));
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(474);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$49, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(480);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, optimize$50, PyString.fromInterned("\n        A subclass can define this as a hook for optimizations.\n\n        Returns either self or another node with the same effect.\n        "));
      var1.setlocal("optimize", var5);
      var3 = null;
      var1.setline(488);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, match$51, PyString.fromInterned("\n        Does this pattern exactly match a node?\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        Default implementation for non-wildcard patterns.\n        "));
      var1.setlocal("match", var5);
      var3 = null;
      var1.setline(513);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, match_seq$52, PyString.fromInterned("\n        Does this pattern exactly match a sequence of nodes?\n\n        Default implementation for non-wildcard patterns.\n        "));
      var1.setlocal("match_seq", var5);
      var3 = null;
      var1.setline(523);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, generate_matches$53, PyString.fromInterned("\n        Generator yielding all matches for this pattern.\n\n        Default implementation for non-wildcard patterns.\n        "));
      var1.setlocal("generate_matches", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$48(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("Constructor that prevents BasePattern from being instantiated.");
      var1.setline(471);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0);
         PyObject var10000 = var3._isnot(var1.getglobal("BasePattern"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Cannot instantiate BasePattern"));
         }
      }

      var1.setline(472);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$49(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyList var3 = new PyList(new PyObject[]{var1.getglobal("type_repr").__call__(var2, var1.getlocal(0).__getattr__("type")), var1.getlocal(0).__getattr__("content"), var1.getlocal(0).__getattr__("name")});
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(476);
         PyObject var10000 = var1.getlocal(1);
         PyObject var4;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
            var10000 = var4._is(var1.getglobal("None"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(478);
            var4 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(1)))}));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(477);
         var1.getlocal(1).__delitem__((PyObject)Py.newInteger(-1));
      }
   }

   public PyObject optimize$50(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyString.fromInterned("\n        A subclass can define this as a hook for optimizations.\n\n        Returns either self or another node with the same effect.\n        ");
      var1.setline(486);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject match$51(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned("\n        Does this pattern exactly match a node?\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        Default implementation for non-wildcard patterns.\n        ");
      var1.setline(499);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("type");
         var10000 = var3._ne(var1.getlocal(0).__getattr__("type"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(500);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(501);
         PyObject var4 = var1.getlocal(0).__getattr__("content");
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(502);
            var4 = var1.getglobal("None");
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(503);
            var4 = var1.getlocal(2);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(504);
               PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(3, var5);
               var4 = null;
            }

            var1.setline(505);
            if (var1.getlocal(0).__getattr__("_submatch").__call__(var2, var1.getlocal(1), var1.getlocal(3)).__not__().__nonzero__()) {
               var1.setline(506);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(507);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(508);
               var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(3));
            }
         }

         var1.setline(509);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("name");
         }

         if (var10000.__nonzero__()) {
            var1.setline(510);
            var4 = var1.getlocal(1);
            var1.getlocal(2).__setitem__(var1.getlocal(0).__getattr__("name"), var4);
            var4 = null;
         }

         var1.setline(511);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject match_seq$52(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      PyString.fromInterned("\n        Does this pattern exactly match a sequence of nodes?\n\n        Default implementation for non-wildcard patterns.\n        ");
      var1.setline(519);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ne(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(520);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(521);
         var3 = var1.getlocal(0).__getattr__("match").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject generate_matches$53(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(528);
            PyString.fromInterned("\n        Generator yielding all matches for this pattern.\n\n        Default implementation for non-wildcard patterns.\n        ");
            var1.setline(529);
            PyObject[] var4 = Py.EmptyObjects;
            PyDictionary var7 = new PyDictionary(var4);
            Arrays.fill(var4, (Object)null);
            PyDictionary var5 = var7;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(530);
            var6 = var1.getlocal(1);
            if (var6.__nonzero__()) {
               var6 = var1.getlocal(0).__getattr__("match").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(2));
            }

            if (var6.__nonzero__()) {
               var1.setline(531);
               var1.setline(531);
               var4 = new PyObject[]{Py.newInteger(1), var1.getlocal(2)};
               PyTuple var8 = new PyTuple(var4);
               Arrays.fill(var4, (Object)null);
               var1.f_lasti = 1;
               var3 = new Object[4];
               var1.f_savedlocals = var3;
               return var8;
            }
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LeafPattern$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(536);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$55, PyString.fromInterned("\n        Initializer.  Takes optional type, content, and name.\n\n        The type, if given must be a token type (< 256).  If not given,\n        this matches any *leaf* node; the content may still be required.\n\n        The content, if given, must be a string.\n\n        If a name is given, the matching node is stored in the results\n        dict under that key.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(556);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, match$56, PyString.fromInterned("Override match() to insist on a leaf node."));
      var1.setlocal("match", var4);
      var3 = null;
      var1.setline(562);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _submatch$57, PyString.fromInterned("\n        Match the pattern's content to the node's children.\n\n        This assumes the node type matches and self.content is not None.\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        When returning False, the results dict may still be updated.\n        "));
      var1.setlocal("_submatch", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyString.fromInterned("\n        Initializer.  Takes optional type, content, and name.\n\n        The type, if given must be a token type (< 256).  If not given,\n        this matches any *leaf* node; the content may still be required.\n\n        The content, if given, must be a string.\n\n        If a name is given, the matching node is stored in the results\n        dict under that key.\n        ");
      var1.setline(548);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(549);
         if (var1.getglobal("__debug__").__nonzero__()) {
            PyInteger var5 = Py.newInteger(0);
            PyObject var10001 = var1.getlocal(1);
            PyInteger var6 = var5;
            var3 = var10001;
            PyObject var4;
            if ((var4 = var6._le(var10001)).__nonzero__()) {
               var4 = var3._lt(Py.newInteger(256));
            }

            var3 = null;
            if (!var4.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
            }
         }
      }

      var1.setline(550);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(551);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
         }
      }

      var1.setline(552);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(553);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("content", var3);
      var3 = null;
      var1.setline(554);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject match$56(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      PyString.fromInterned("Override match() to insist on a leaf node.");
      var1.setline(558);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Leaf")).__not__().__nonzero__()) {
         var1.setline(559);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(560);
         var3 = var1.getglobal("BasePattern").__getattr__("match").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _submatch$57(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyString.fromInterned("\n        Match the pattern's content to the node's children.\n\n        This assumes the node type matches and self.content is not None.\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        When returning False, the results dict may still be updated.\n        ");
      var1.setline(575);
      PyObject var3 = var1.getlocal(0).__getattr__("content");
      PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("value"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject NodePattern$58(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(580);
      PyObject var3 = var1.getname("False");
      var1.setlocal("wildcards", var3);
      var3 = null;
      var1.setline(582);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$59, PyString.fromInterned("\n        Initializer.  Takes optional type, content, and name.\n\n        The type, if given, must be a symbol type (>= 256).  If the\n        type is None this matches *any* single node (leaf or not),\n        except if content is not None, in which it only matches\n        non-leaf nodes that also match the content pattern.\n\n        The content, if not None, must be a sequence of Patterns that\n        must match the node's children exactly.  If the content is\n        given, the type must not be None.\n\n        If a name is given, the matching node is stored in the results\n        dict under that key.\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(611);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _submatch$60, PyString.fromInterned("\n        Match the pattern's content to the node's children.\n\n        This assumes the node type matches and self.content is not None.\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        When returning False, the results dict may still be updated.\n        "));
      var1.setlocal("_submatch", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$59(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyString.fromInterned("\n        Initializer.  Takes optional type, content, and name.\n\n        The type, if given, must be a symbol type (>= 256).  If the\n        type is None this matches *any* single node (leaf or not),\n        except if content is not None, in which it only matches\n        non-leaf nodes that also match the content pattern.\n\n        The content, if not None, must be a sequence of Patterns that\n        must match the node's children exactly.  If the content is\n        given, the type must not be None.\n\n        If a name is given, the matching node is stored in the results\n        dict under that key.\n        ");
      var1.setline(598);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(599);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._ge(Py.newInteger(256));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
            }
         }
      }

      var1.setline(600);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(601);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__not__().__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
         }

         var1.setline(602);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(603);
         var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(603);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(604);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("BasePattern")).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
            }

            var1.setline(605);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("WildcardPattern")).__nonzero__()) {
               var1.setline(606);
               PyObject var7 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("wildcards", var7);
               var5 = null;
            }
         }
      }

      var1.setline(607);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("content", var3);
      var3 = null;
      var1.setline(609);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _submatch$60(PyFrame var1, ThreadState var2) {
      var1.setline(623);
      PyString.fromInterned("\n        Match the pattern's content to the node's children.\n\n        This assumes the node type matches and self.content is not None.\n\n        Returns True if it matches, False if not.\n\n        If results is not None, it must be a dict which will be\n        updated with the nodes matching named subpatterns.\n\n        When returning False, the results dict may still be updated.\n        ");
      var1.setline(624);
      PyObject var10000;
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject[] var6;
      if (var1.getlocal(0).__getattr__("wildcards").__nonzero__()) {
         var1.setline(625);
         var3 = var1.getglobal("generate_matches").__call__(var2, var1.getlocal(0).__getattr__("content"), var1.getlocal(1).__getattr__("children")).__iter__();

         do {
            var1.setline(625);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(630);
               var5 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var5;
            }

            PyObject[] var8 = Py.unpackSequence(var4, 2);
            PyObject var9 = var8[0];
            var1.setlocal(3, var9);
            var6 = null;
            var9 = var8[1];
            var1.setlocal(4, var9);
            var6 = null;
            var1.setline(626);
            var5 = var1.getlocal(3);
            var10000 = var5._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children")));
            var5 = null;
         } while(!var10000.__nonzero__());

         var1.setline(627);
         var5 = var1.getlocal(2);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(628);
            var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(4));
         }

         var1.setline(629);
         var5 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(631);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("content"));
         var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(632);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(633);
            var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(0).__getattr__("content"), var1.getlocal(1).__getattr__("children")).__iter__();

            do {
               var1.setline(633);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(636);
                  var5 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var5;
               }

               var6 = Py.unpackSequence(var4, 2);
               PyObject var7 = var6[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var1.setline(634);
            } while(!var1.getlocal(5).__getattr__("match").__call__(var2, var1.getlocal(6), var1.getlocal(2)).__not__().__nonzero__());

            var1.setline(635);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject WildcardPattern$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A wildcard pattern can match zero or more nodes.\n\n    This has all the flexibility needed to implement patterns like:\n\n    .*      .+      .?      .{m,n}\n    (a b c | d e | f)\n    (...)*  (...)+  (...)?  (...){m,n}\n\n    except it always uses non-greedy matching.\n    "));
      var1.setline(651);
      PyString.fromInterned("\n    A wildcard pattern can match zero or more nodes.\n\n    This has all the flexibility needed to implement patterns like:\n\n    .*      .+      .?      .{m,n}\n    (a b c | d e | f)\n    (...)*  (...)+  (...)?  (...){m,n}\n\n    except it always uses non-greedy matching.\n    ");
      var1.setline(653);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("HUGE"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$62, PyString.fromInterned("\n        Initializer.\n\n        Args:\n            content: optional sequence of subsequences of patterns;\n                     if absent, matches one node;\n                     if present, each subsequence is an alternative [*]\n            min: optional minimum number of times to match, default 0\n            max: optional maximum number of times to match, default HUGE\n            name: optional name assigned to this match\n\n        [*] Thus, if content is [[a, b, c], [d, e], [f, g, h]] this is\n            equivalent to (a b c | d e | f g h); if content is None,\n            this is equivalent to '.' in regular expression terms.\n            The min and max parameters work as follows:\n                min=0, max=maxint: .*\n                min=1, max=maxint: .+\n                min=0, max=1: .?\n                min=1, max=1: .\n            If content is not None, replace the dot with the parenthesized\n            list of alternatives, e.g. (a b c | d e | f g h)*\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(688);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, optimize$63, PyString.fromInterned("Optimize certain stacked wildcard patterns."));
      var1.setlocal("optimize", var4);
      var3 = null;
      var1.setline(707);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, match$64, PyString.fromInterned("Does this pattern exactly match a node?"));
      var1.setlocal("match", var4);
      var3 = null;
      var1.setline(711);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, match_seq$65, PyString.fromInterned("Does this pattern exactly match a sequence of nodes?"));
      var1.setlocal("match_seq", var4);
      var3 = null;
      var1.setline(722);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, generate_matches$66, PyString.fromInterned("\n        Generator yielding matches for a sequence of nodes.\n\n        Args:\n            nodes: sequence of nodes\n\n        Yields:\n            (count, results) tuples where:\n            count: the match comprises nodes[:count];\n            results: dict containing named submatches.\n        "));
      var1.setlocal("generate_matches", var4);
      var3 = null;
      var1.setline(767);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _iterative_matches$67, PyString.fromInterned("Helper to iteratively yield the matches."));
      var1.setlocal("_iterative_matches", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _bare_name_matches$68, PyString.fromInterned("Special optimized matcher for bare_name."));
      var1.setlocal("_bare_name_matches", var4);
      var3 = null;
      var1.setline(812);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _recursive_matches$69, PyString.fromInterned("Helper to recursively yield the matches."));
      var1.setlocal("_recursive_matches", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$62(PyFrame var1, ThreadState var2) {
      var1.setline(675);
      PyString.fromInterned("\n        Initializer.\n\n        Args:\n            content: optional sequence of subsequences of patterns;\n                     if absent, matches one node;\n                     if present, each subsequence is an alternative [*]\n            min: optional minimum number of times to match, default 0\n            max: optional maximum number of times to match, default HUGE\n            name: optional name assigned to this match\n\n        [*] Thus, if content is [[a, b, c], [d, e], [f, g, h]] this is\n            equivalent to (a b c | d e | f g h); if content is None,\n            this is equivalent to '.' in regular expression terms.\n            The min and max parameters work as follows:\n                min=0, max=maxint: .*\n                min=1, max=maxint: .+\n                min=0, max=1: .?\n                min=1, max=1: .\n            If content is not None, replace the dot with the parenthesized\n            list of alternatives, e.g. (a b c | d e | f g h)*\n        ");
      var1.setline(676);
      PyInteger var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var10000 = var3;
         var5 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var10001 = var1.getlocal(3);
            var6 = var5;
            var5 = var10001;
            if ((var4 = var6._le(var10001)).__nonzero__()) {
               var4 = var5._le(var1.getglobal("HUGE"));
            }
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}));
         }
      }

      var1.setline(677);
      var5 = var1.getlocal(1);
      var6 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var6.__nonzero__()) {
         var1.setline(678);
         var5 = var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("tuple"), var1.getlocal(1)));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(680);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("len").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(681);
         var5 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(681);
            var4 = var5.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(682);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("len").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(5)));
            }
         }
      }

      var1.setline(683);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("content", var5);
      var3 = null;
      var1.setline(684);
      var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("min", var5);
      var3 = null;
      var1.setline(685);
      var5 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("max", var5);
      var3 = null;
      var1.setline(686);
      var5 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("name", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject optimize$63(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      PyString.fromInterned("Optimize certain stacked wildcard patterns.");
      var1.setline(690);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(691);
      var3 = var1.getlocal(0).__getattr__("content");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("content"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("content").__getitem__(Py.newInteger(0)));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(693);
         var3 = var1.getlocal(0).__getattr__("content").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(694);
      var3 = var1.getlocal(0).__getattr__("min");
      var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("max");
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(695);
         var3 = var1.getlocal(0).__getattr__("content");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(696);
            var10000 = var1.getglobal("NodePattern");
            PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("name")};
            String[] var5 = new String[]{"name"};
            var10000 = var10000.__call__(var2, var6, var5);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(697);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("name");
            var10000 = var4._eq(var1.getlocal(1).__getattr__("name"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(698);
            var3 = var1.getlocal(1).__getattr__("optimize").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(699);
      var4 = var1.getlocal(0).__getattr__("min");
      var10000 = var4._le(Py.newInteger(1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("WildcardPattern"));
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(1).__getattr__("min");
            var10000 = var4._le(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("name");
               var10000 = var4._eq(var1.getlocal(1).__getattr__("name"));
               var4 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(701);
         var3 = var1.getglobal("WildcardPattern").__call__(var2, var1.getlocal(1).__getattr__("content"), var1.getlocal(0).__getattr__("min")._mul(var1.getlocal(1).__getattr__("min")), var1.getlocal(0).__getattr__("max")._mul(var1.getlocal(1).__getattr__("max")), var1.getlocal(1).__getattr__("name"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(705);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject match$64(PyFrame var1, ThreadState var2) {
      var1.setline(708);
      PyString.fromInterned("Does this pattern exactly match a node?");
      var1.setline(709);
      PyObject var3 = var1.getlocal(0).__getattr__("match_seq").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject match_seq$65(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyString.fromInterned("Does this pattern exactly match a sequence of nodes?");
      var1.setline(713);
      PyObject var3 = var1.getlocal(0).__getattr__("generate_matches").__call__(var2, var1.getlocal(1)).__iter__();

      PyObject var10000;
      PyObject[] var5;
      PyObject var7;
      do {
         var1.setline(713);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(720);
            var7 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var7;
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(714);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(715);
      var7 = var1.getlocal(2);
      var10000 = var7._isnot(var1.getglobal("None"));
      var5 = null;
      if (var10000.__nonzero__()) {
         var1.setline(716);
         var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(4));
         var1.setline(717);
         if (var1.getlocal(0).__getattr__("name").__nonzero__()) {
            var1.setline(718);
            var7 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
            var1.getlocal(2).__setitem__(var1.getlocal(0).__getattr__("name"), var7);
            var5 = null;
         }
      }

      var1.setline(719);
      var7 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject generate_matches$66(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _iterative_matches$67(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _bare_name_matches$68(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      PyString.fromInterned("Special optimized matcher for bare_name.");
      var1.setline(798);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(799);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(800);
      PyObject var7 = var1.getglobal("False");
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(801);
      var7 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var7);
      var3 = null;

      while(true) {
         label24:
         while(true) {
            var1.setline(802);
            PyObject var10000 = var1.getlocal(4).__not__();
            if (var10000.__nonzero__()) {
               var7 = var1.getlocal(2);
               var10000 = var7._lt(var1.getlocal(5));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(809);
               var7 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
               var1.getlocal(3).__setitem__(var1.getlocal(0).__getattr__("name"), var7);
               var3 = null;
               var1.setline(810);
               PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(803);
            var7 = var1.getglobal("True");
            var1.setlocal(4, var7);
            var3 = null;
            var1.setline(804);
            var7 = var1.getlocal(0).__getattr__("content").__iter__();

            do {
               var1.setline(804);
               PyObject var4 = var7.__iternext__();
               if (var4 == null) {
                  continue label24;
               }

               var1.setlocal(6, var4);
               var1.setline(805);
            } while(!var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("match").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)), var1.getlocal(3)).__nonzero__());

            var1.setline(806);
            PyObject var5 = var1.getlocal(2);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(2, var5);
            var1.setline(807);
            var5 = var1.getglobal("False");
            var1.setlocal(4, var5);
            var5 = null;
         }
      }
   }

   public PyObject _recursive_matches$69(PyFrame var1, ThreadState var2) {
      label77: {
         PyObject var3;
         PyObject var4;
         PyObject var5;
         PyObject var6;
         PyObject var7;
         PyObject var8;
         Object[] var9;
         PyObject var10;
         Object[] var11;
         PyObject[] var15;
         Object var10000;
         PyDictionary var16;
         PyObject var17;
         PyDictionary var18;
         PyTuple var19;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(813);
               PyString.fromInterned("Helper to recursively yield the matches.");
               var1.setline(814);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("content");
                  var17 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (!var17.__nonzero__()) {
                     var17 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var17);
                  }
               }

               var1.setline(815);
               var3 = var1.getlocal(2);
               var17 = var3._ge(var1.getlocal(0).__getattr__("min"));
               var3 = null;
               if (var17.__nonzero__()) {
                  var1.setline(816);
                  var1.setline(816);
                  PyObject[] var14 = new PyObject[]{Py.newInteger(0), null};
                  PyObject[] var12 = Py.EmptyObjects;
                  var18 = new PyDictionary(var12);
                  Arrays.fill(var12, (Object)null);
                  var14[1] = var18;
                  var19 = new PyTuple(var14);
                  Arrays.fill(var14, (Object)null);
                  var1.f_lasti = 1;
                  var11 = new Object[5];
                  var1.f_savedlocals = var11;
                  return var19;
               }

               var1.setline(817);
               var3 = var1.getlocal(2);
               var17 = var3._lt(var1.getlocal(0).__getattr__("max"));
               var3 = null;
               if (!var17.__nonzero__()) {
                  break label77;
               }

               var1.setline(818);
               var3 = var1.getlocal(0).__getattr__("content").__iter__();
               var1.setline(818);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break label77;
               }

               var1.setlocal(3, var4);
               var1.setline(819);
               var5 = var1.getglobal("generate_matches").__call__(var2, var1.getlocal(3), var1.getlocal(1)).__iter__();
               break;
            case 1:
               var11 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(817);
               var3 = var1.getlocal(2);
               var17 = var3._lt(var1.getlocal(0).__getattr__("max"));
               var3 = null;
               if (!var17.__nonzero__()) {
                  break label77;
               }

               var1.setline(818);
               var3 = var1.getlocal(0).__getattr__("content").__iter__();
               var1.setline(818);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break label77;
               }

               var1.setlocal(3, var4);
               var1.setline(819);
               var5 = var1.getglobal("generate_matches").__call__(var2, var1.getlocal(3), var1.getlocal(1)).__iter__();
               break;
            case 2:
               var9 = var1.f_savedlocals;
               var3 = (PyObject)var9[3];
               var4 = (PyObject)var9[4];
               var5 = (PyObject)var9[5];
               var6 = (PyObject)var9[6];
               var7 = (PyObject)var9[7];
               var8 = (PyObject)var9[8];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(820);
               var8 = var7.__iternext__();
               if (var8 != null) {
                  var15 = Py.unpackSequence(var8, 2);
                  var10 = var15[0];
                  var1.setlocal(6, var10);
                  var10 = null;
                  var10 = var15[1];
                  var1.setlocal(7, var10);
                  var10 = null;
                  var1.setline(821);
                  var15 = Py.EmptyObjects;
                  var18 = new PyDictionary(var15);
                  Arrays.fill(var15, (Object)null);
                  var16 = var18;
                  var1.setlocal(8, var16);
                  var9 = null;
                  var1.setline(822);
                  var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(5));
                  var1.setline(823);
                  var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(7));
                  var1.setline(824);
                  var1.setline(824);
                  var15 = new PyObject[]{var1.getlocal(4)._add(var1.getlocal(6)), var1.getlocal(8)};
                  var19 = new PyTuple(var15);
                  Arrays.fill(var15, (Object)null);
                  var1.f_lasti = 2;
                  var9 = new Object[11];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var19;
               }
         }

         label76:
         while(true) {
            while(true) {
               var1.setline(819);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(818);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label76;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(819);
                  var5 = var1.getglobal("generate_matches").__call__(var2, var1.getlocal(3), var1.getlocal(1)).__iter__();
               } else {
                  PyObject[] var13 = Py.unpackSequence(var6, 2);
                  var8 = var13[0];
                  var1.setlocal(4, var8);
                  var8 = null;
                  var8 = var13[1];
                  var1.setlocal(5, var8);
                  var8 = null;
                  var1.setline(820);
                  var7 = var1.getlocal(0).__getattr__("_recursive_matches").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null), var1.getlocal(2)._add(Py.newInteger(1))).__iter__();
                  var1.setline(820);
                  var8 = var7.__iternext__();
                  if (var8 != null) {
                     var15 = Py.unpackSequence(var8, 2);
                     var10 = var15[0];
                     var1.setlocal(6, var10);
                     var10 = null;
                     var10 = var15[1];
                     var1.setlocal(7, var10);
                     var10 = null;
                     var1.setline(821);
                     var15 = Py.EmptyObjects;
                     var18 = new PyDictionary(var15);
                     Arrays.fill(var15, (Object)null);
                     var16 = var18;
                     var1.setlocal(8, var16);
                     var9 = null;
                     var1.setline(822);
                     var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(5));
                     var1.setline(823);
                     var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(7));
                     var1.setline(824);
                     var1.setline(824);
                     var15 = new PyObject[]{var1.getlocal(4)._add(var1.getlocal(6)), var1.getlocal(8)};
                     var19 = new PyTuple(var15);
                     Arrays.fill(var15, (Object)null);
                     var1.f_lasti = 2;
                     var9 = new Object[11];
                     var9[3] = var3;
                     var9[4] = var4;
                     var9[5] = var5;
                     var9[6] = var6;
                     var9[7] = var7;
                     var9[8] = var8;
                     var1.f_savedlocals = var9;
                     return var19;
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NegatedPattern$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(829);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$71, PyString.fromInterned("\n        Initializer.\n\n        The argument is either a pattern or None.  If it is None, this\n        only matches an empty sequence (effectively '$' in regex\n        lingo).  If it is not None, this matches whenever the argument\n        pattern doesn't have any matches.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(842);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, match$72, (PyObject)null);
      var1.setlocal("match", var4);
      var3 = null;
      var1.setline(846);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, match_seq$73, (PyObject)null);
      var1.setlocal("match_seq", var4);
      var3 = null;
      var1.setline(850);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, generate_matches$74, (PyObject)null);
      var1.setlocal("generate_matches", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$71(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      PyString.fromInterned("\n        Initializer.\n\n        The argument is either a pattern or None.  If it is None, this\n        only matches an empty sequence (effectively '$' in regex\n        lingo).  If it is not None, this matches whenever the argument\n        pattern doesn't have any matches.\n        ");
      var1.setline(838);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(839);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BasePattern")).__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
         }
      }

      var1.setline(840);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("content", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject match$72(PyFrame var1, ThreadState var2) {
      var1.setline(844);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject match_seq$73(PyFrame var1, ThreadState var2) {
      var1.setline(848);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject generate_matches$74(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var10;
      Object var10000;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(851);
            PyObject var7 = var1.getlocal(0).__getattr__("content");
            var10 = var7._is(var1.getglobal("None"));
            var3 = null;
            PyObject[] var4;
            PyObject[] var9;
            PyDictionary var11;
            PyTuple var12;
            if (!var10.__nonzero__()) {
               var1.setline(857);
               var7 = var1.getlocal(0).__getattr__("content").__getattr__("generate_matches").__call__(var2, var1.getlocal(1)).__iter__();
               var1.setline(857);
               PyObject var8 = var7.__iternext__();
               if (var8 == null) {
                  var1.setline(859);
                  var1.setline(859);
                  var9 = new PyObject[]{Py.newInteger(0), null};
                  var4 = Py.EmptyObjects;
                  var11 = new PyDictionary(var4);
                  Arrays.fill(var4, (Object)null);
                  var9[1] = var11;
                  var12 = new PyTuple(var9);
                  Arrays.fill(var9, (Object)null);
                  var1.f_lasti = 2;
                  var3 = new Object[7];
                  var1.f_savedlocals = var3;
                  return var12;
               }

               PyObject[] var5 = Py.unpackSequence(var8, 2);
               PyObject var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(858);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(853);
            var7 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10 = var7._eq(Py.newInteger(0));
            var3 = null;
            if (var10.__nonzero__()) {
               var1.setline(854);
               var1.setline(854);
               var9 = new PyObject[]{Py.newInteger(0), null};
               var4 = Py.EmptyObjects;
               var11 = new PyDictionary(var4);
               Arrays.fill(var4, (Object)null);
               var9[1] = var11;
               var12 = new PyTuple(var9);
               Arrays.fill(var9, (Object)null);
               var1.f_lasti = 1;
               var3 = new Object[5];
               var1.f_savedlocals = var3;
               return var12;
            }
            break;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
            break;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject generate_matches$75(PyFrame var1, ThreadState var2) {
      label48: {
         PyObject var3;
         PyObject var4;
         PyObject var5;
         PyObject var6;
         Object[] var7;
         PyObject var8;
         Object[] var9;
         Object[] var13;
         PyObject[] var15;
         Object var10000;
         PyDictionary var16;
         PyObject var17;
         PyDictionary var18;
         PyTuple var19;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(874);
               PyString.fromInterned("\n    Generator yielding matches for a sequence of patterns and nodes.\n\n    Args:\n        patterns: a sequence of patterns\n        nodes: a sequence of nodes\n\n    Yields:\n        (count, results) tuples where:\n        count: the entire sequence of patterns matches nodes[:count];\n        results: dict containing named submatches.\n        ");
               var1.setline(875);
               PyObject[] var10;
               PyObject[] var12;
               if (var1.getlocal(0).__not__().__nonzero__()) {
                  var1.setline(876);
                  var1.setline(876);
                  var10 = new PyObject[]{Py.newInteger(0), null};
                  var12 = Py.EmptyObjects;
                  var18 = new PyDictionary(var12);
                  Arrays.fill(var12, (Object)null);
                  var10[1] = var18;
                  var19 = new PyTuple(var10);
                  Arrays.fill(var10, (Object)null);
                  var1.f_lasti = 1;
                  var9 = new Object[5];
                  var1.f_savedlocals = var9;
                  return var19;
               }

               var1.setline(878);
               var10 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)};
               var19 = new PyTuple(var10);
               Arrays.fill(var10, (Object)null);
               PyTuple var11 = var19;
               var12 = Py.unpackSequence(var11, 2);
               var5 = var12[0];
               var1.setlocal(2, var5);
               var5 = null;
               var5 = var12[1];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
               var1.setline(879);
               var3 = var1.getlocal(2).__getattr__("generate_matches").__call__(var2, var1.getlocal(1)).__iter__();
               break;
            case 1:
               var9 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               break label48;
            case 2:
               var13 = var1.f_savedlocals;
               var3 = (PyObject)var13[3];
               var4 = (PyObject)var13[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               break;
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

               var17 = (PyObject)var10000;
               var1.setline(883);
               var6 = var5.__iternext__();
               if (var6 != null) {
                  var15 = Py.unpackSequence(var6, 2);
                  var8 = var15[0];
                  var1.setlocal(6, var8);
                  var8 = null;
                  var8 = var15[1];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var1.setline(884);
                  var15 = Py.EmptyObjects;
                  var18 = new PyDictionary(var15);
                  Arrays.fill(var15, (Object)null);
                  var16 = var18;
                  var1.setlocal(8, var16);
                  var7 = null;
                  var1.setline(885);
                  var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(5));
                  var1.setline(886);
                  var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(7));
                  var1.setline(887);
                  var1.setline(887);
                  var15 = new PyObject[]{var1.getlocal(4)._add(var1.getlocal(6)), var1.getlocal(8)};
                  var19 = new PyTuple(var15);
                  Arrays.fill(var15, (Object)null);
                  var1.f_lasti = 3;
                  var7 = new Object[9];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var5;
                  var7[6] = var6;
                  var1.f_savedlocals = var7;
                  return var19;
               }
         }

         while(true) {
            var1.setline(879);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var14 = Py.unpackSequence(var4, 2);
            var6 = var14[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var14[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(880);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(881);
               var1.setline(881);
               var14 = new PyObject[]{var1.getlocal(4), var1.getlocal(5)};
               var19 = new PyTuple(var14);
               Arrays.fill(var14, (Object)null);
               var1.f_lasti = 2;
               var13 = new Object[7];
               var13[3] = var3;
               var13[4] = var4;
               var1.f_savedlocals = var13;
               return var19;
            }

            var1.setline(883);
            var5 = var1.getglobal("generate_matches").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null)).__iter__();
            var1.setline(883);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var15 = Py.unpackSequence(var6, 2);
               var8 = var15[0];
               var1.setlocal(6, var8);
               var8 = null;
               var8 = var15[1];
               var1.setlocal(7, var8);
               var8 = null;
               var1.setline(884);
               var15 = Py.EmptyObjects;
               var18 = new PyDictionary(var15);
               Arrays.fill(var15, (Object)null);
               var16 = var18;
               var1.setlocal(8, var16);
               var7 = null;
               var1.setline(885);
               var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(5));
               var1.setline(886);
               var1.getlocal(8).__getattr__("update").__call__(var2, var1.getlocal(7));
               var1.setline(887);
               var1.setline(887);
               var15 = new PyObject[]{var1.getlocal(4)._add(var1.getlocal(6)), var1.getlocal(8)};
               var19 = new PyTuple(var15);
               Arrays.fill(var15, (Object)null);
               var1.f_lasti = 3;
               var7 = new Object[9];
               var7[3] = var3;
               var7[4] = var4;
               var7[5] = var5;
               var7[6] = var6;
               var1.f_savedlocals = var7;
               return var19;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public pytree$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"type_num", "python_symbols", "name", "val"};
      type_repr$1 = Py.newCode(1, var2, var1, "type_repr", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Base$2 = Py.newCode(0, var2, var1, "Base", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kwds"};
      __new__$3 = Py.newCode(3, var2, var1, "__new__", 50, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$4 = Py.newCode(2, var2, var1, "__eq__", 55, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$5 = Py.newCode(2, var2, var1, "__ne__", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      _eq$6 = Py.newCode(2, var2, var1, "_eq", 77, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clone$7 = Py.newCode(1, var2, var1, "clone", 88, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      post_order$8 = Py.newCode(1, var2, var1, "post_order", 96, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pre_order$9 = Py.newCode(1, var2, var1, "pre_order", 104, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      set_prefix$10 = Py.newCode(2, var2, var1, "set_prefix", 112, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_prefix$11 = Py.newCode(1, var2, var1, "get_prefix", 122, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new", "l_children", "found", "ch", "x"};
      replace$12 = Py.newCode(2, var2, var1, "replace", 132, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      get_lineno$13 = Py.newCode(1, var2, var1, "get_lineno", 155, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      changed$14 = Py.newCode(1, var2, var1, "changed", 164, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "node"};
      remove$15 = Py.newCode(1, var2, var1, "remove", 169, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "child"};
      next_sibling$16 = Py.newCode(1, var2, var1, "next_sibling", 182, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "child"};
      prev_sibling$17 = Py.newCode(1, var2, var1, "prev_sibling", 199, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "x"};
      leaves$18 = Py.newCode(1, var2, var1, "leaves", 215, false, false, self, 18, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      depth$19 = Py.newCode(1, var2, var1, "depth", 220, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "next_sib"};
      get_suffix$20 = Py.newCode(1, var2, var1, "get_suffix", 225, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$21 = Py.newCode(1, var2, var1, "__str__", 236, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Node$22 = Py.newCode(0, var2, var1, "Node", 239, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type", "children", "context", "prefix", "fixers_applied", "ch"};
      __init__$23 = Py.newCode(6, var2, var1, "__init__", 243, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$24 = Py.newCode(1, var2, var1, "__repr__", 268, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __unicode__$25 = Py.newCode(1, var2, var1, "__unicode__", 274, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      _eq$26 = Py.newCode(2, var2, var1, "_eq", 285, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[291_32]", "ch"};
      clone$27 = Py.newCode(1, var2, var1, "clone", 289, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "node"};
      post_order$28 = Py.newCode(1, var2, var1, "post_order", 294, false, false, self, 28, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "child", "node"};
      pre_order$29 = Py.newCode(1, var2, var1, "pre_order", 301, false, false, self, 29, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      _prefix_getter$30 = Py.newCode(1, var2, var1, "_prefix_getter", 308, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      _prefix_setter$31 = Py.newCode(2, var2, var1, "_prefix_setter", 316, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "child"};
      set_child$32 = Py.newCode(3, var2, var1, "set_child", 322, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "child"};
      insert_child$33 = Py.newCode(3, var2, var1, "insert_child", 332, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child"};
      append_child$34 = Py.newCode(2, var2, var1, "append_child", 341, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Leaf$35 = Py.newCode(0, var2, var1, "Leaf", 351, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type", "value", "context", "prefix", "fixers_applied"};
      __init__$36 = Py.newCode(6, var2, var1, "__init__", 360, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$37 = Py.newCode(1, var2, var1, "__repr__", 379, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __unicode__$38 = Py.newCode(1, var2, var1, "__unicode__", 385, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      _eq$39 = Py.newCode(2, var2, var1, "_eq", 396, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clone$40 = Py.newCode(1, var2, var1, "clone", 400, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      leaves$41 = Py.newCode(1, var2, var1, "leaves", 406, false, false, self, 41, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      post_order$42 = Py.newCode(1, var2, var1, "post_order", 409, false, false, self, 42, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      pre_order$43 = Py.newCode(1, var2, var1, "pre_order", 413, false, false, self, 43, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      _prefix_getter$44 = Py.newCode(1, var2, var1, "_prefix_getter", 417, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      _prefix_setter$45 = Py.newCode(2, var2, var1, "_prefix_setter", 423, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"gr", "raw_node", "type", "value", "context", "children"};
      convert$46 = Py.newCode(2, var2, var1, "convert", 429, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BasePattern$47 = Py.newCode(0, var2, var1, "BasePattern", 448, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kwds"};
      __new__$48 = Py.newCode(3, var2, var1, "__new__", 469, true, true, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __repr__$49 = Py.newCode(1, var2, var1, "__repr__", 474, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      optimize$50 = Py.newCode(1, var2, var1, "optimize", 480, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "r"};
      match$51 = Py.newCode(3, var2, var1, "match", 488, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "results"};
      match_seq$52 = Py.newCode(3, var2, var1, "match_seq", 513, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "r"};
      generate_matches$53 = Py.newCode(2, var2, var1, "generate_matches", 523, false, false, self, 53, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      LeafPattern$54 = Py.newCode(0, var2, var1, "LeafPattern", 534, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type", "content", "name"};
      __init__$55 = Py.newCode(4, var2, var1, "__init__", 536, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      match$56 = Py.newCode(3, var2, var1, "match", 556, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _submatch$57 = Py.newCode(3, var2, var1, "_submatch", 562, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NodePattern$58 = Py.newCode(0, var2, var1, "NodePattern", 578, false, false, self, 58, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type", "content", "name", "i", "item"};
      __init__$59 = Py.newCode(4, var2, var1, "__init__", 582, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "c", "r", "subpattern", "child"};
      _submatch$60 = Py.newCode(3, var2, var1, "_submatch", 611, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WildcardPattern$61 = Py.newCode(0, var2, var1, "WildcardPattern", 639, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "content", "min", "max", "name", "alt"};
      __init__$62 = Py.newCode(5, var2, var1, "__init__", 653, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "subpattern"};
      optimize$63 = Py.newCode(1, var2, var1, "optimize", 688, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      match$64 = Py.newCode(3, var2, var1, "match", 707, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "results", "c", "r"};
      match_seq$65 = Py.newCode(3, var2, var1, "match_seq", 711, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "count", "r", "save_stderr"};
      generate_matches$66 = Py.newCode(2, var2, var1, "generate_matches", 722, false, false, self, 66, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "nodes", "nodelen", "results", "alt", "c", "r", "new_results", "c0", "r0", "c1", "r1"};
      _iterative_matches$67 = Py.newCode(2, var2, var1, "_iterative_matches", 767, false, false, self, 67, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "nodes", "count", "r", "done", "max", "leaf"};
      _bare_name_matches$68 = Py.newCode(2, var2, var1, "_bare_name_matches", 796, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "count", "alt", "c0", "r0", "c1", "r1", "r"};
      _recursive_matches$69 = Py.newCode(3, var2, var1, "_recursive_matches", 812, false, false, self, 69, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      NegatedPattern$70 = Py.newCode(0, var2, var1, "NegatedPattern", 827, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "content"};
      __init__$71 = Py.newCode(2, var2, var1, "__init__", 829, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      match$72 = Py.newCode(2, var2, var1, "match", 842, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes"};
      match_seq$73 = Py.newCode(2, var2, var1, "match_seq", 846, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "c", "r"};
      generate_matches$74 = Py.newCode(2, var2, var1, "generate_matches", 850, false, false, self, 74, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"patterns", "nodes", "p", "rest", "c0", "r0", "c1", "r1", "r"};
      generate_matches$75 = Py.newCode(2, var2, var1, "generate_matches", 862, false, false, self, 75, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pytree$py("lib2to3/pytree$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pytree$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.type_repr$1(var2, var3);
         case 2:
            return this.Base$2(var2, var3);
         case 3:
            return this.__new__$3(var2, var3);
         case 4:
            return this.__eq__$4(var2, var3);
         case 5:
            return this.__ne__$5(var2, var3);
         case 6:
            return this._eq$6(var2, var3);
         case 7:
            return this.clone$7(var2, var3);
         case 8:
            return this.post_order$8(var2, var3);
         case 9:
            return this.pre_order$9(var2, var3);
         case 10:
            return this.set_prefix$10(var2, var3);
         case 11:
            return this.get_prefix$11(var2, var3);
         case 12:
            return this.replace$12(var2, var3);
         case 13:
            return this.get_lineno$13(var2, var3);
         case 14:
            return this.changed$14(var2, var3);
         case 15:
            return this.remove$15(var2, var3);
         case 16:
            return this.next_sibling$16(var2, var3);
         case 17:
            return this.prev_sibling$17(var2, var3);
         case 18:
            return this.leaves$18(var2, var3);
         case 19:
            return this.depth$19(var2, var3);
         case 20:
            return this.get_suffix$20(var2, var3);
         case 21:
            return this.__str__$21(var2, var3);
         case 22:
            return this.Node$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.__repr__$24(var2, var3);
         case 25:
            return this.__unicode__$25(var2, var3);
         case 26:
            return this._eq$26(var2, var3);
         case 27:
            return this.clone$27(var2, var3);
         case 28:
            return this.post_order$28(var2, var3);
         case 29:
            return this.pre_order$29(var2, var3);
         case 30:
            return this._prefix_getter$30(var2, var3);
         case 31:
            return this._prefix_setter$31(var2, var3);
         case 32:
            return this.set_child$32(var2, var3);
         case 33:
            return this.insert_child$33(var2, var3);
         case 34:
            return this.append_child$34(var2, var3);
         case 35:
            return this.Leaf$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         case 37:
            return this.__repr__$37(var2, var3);
         case 38:
            return this.__unicode__$38(var2, var3);
         case 39:
            return this._eq$39(var2, var3);
         case 40:
            return this.clone$40(var2, var3);
         case 41:
            return this.leaves$41(var2, var3);
         case 42:
            return this.post_order$42(var2, var3);
         case 43:
            return this.pre_order$43(var2, var3);
         case 44:
            return this._prefix_getter$44(var2, var3);
         case 45:
            return this._prefix_setter$45(var2, var3);
         case 46:
            return this.convert$46(var2, var3);
         case 47:
            return this.BasePattern$47(var2, var3);
         case 48:
            return this.__new__$48(var2, var3);
         case 49:
            return this.__repr__$49(var2, var3);
         case 50:
            return this.optimize$50(var2, var3);
         case 51:
            return this.match$51(var2, var3);
         case 52:
            return this.match_seq$52(var2, var3);
         case 53:
            return this.generate_matches$53(var2, var3);
         case 54:
            return this.LeafPattern$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this.match$56(var2, var3);
         case 57:
            return this._submatch$57(var2, var3);
         case 58:
            return this.NodePattern$58(var2, var3);
         case 59:
            return this.__init__$59(var2, var3);
         case 60:
            return this._submatch$60(var2, var3);
         case 61:
            return this.WildcardPattern$61(var2, var3);
         case 62:
            return this.__init__$62(var2, var3);
         case 63:
            return this.optimize$63(var2, var3);
         case 64:
            return this.match$64(var2, var3);
         case 65:
            return this.match_seq$65(var2, var3);
         case 66:
            return this.generate_matches$66(var2, var3);
         case 67:
            return this._iterative_matches$67(var2, var3);
         case 68:
            return this._bare_name_matches$68(var2, var3);
         case 69:
            return this._recursive_matches$69(var2, var3);
         case 70:
            return this.NegatedPattern$70(var2, var3);
         case 71:
            return this.__init__$71(var2, var3);
         case 72:
            return this.match$72(var2, var3);
         case 73:
            return this.match_seq$73(var2, var3);
         case 74:
            return this.generate_matches$74(var2, var3);
         case 75:
            return this.generate_matches$75(var2, var3);
         default:
            return null;
      }
   }
}

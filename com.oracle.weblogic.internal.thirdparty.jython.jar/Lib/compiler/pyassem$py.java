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
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("compiler/pyassem.py")
public class pyassem$py extends PyFunctionTable implements PyRunnable {
   static pyassem$py self;
   static final PyCode f$0;
   static final PyCode FlowGraph$1;
   static final PyCode __init__$2;
   static final PyCode startBlock$3;
   static final PyCode nextBlock$4;
   static final PyCode newBlock$5;
   static final PyCode startExitBlock$6;
   static final PyCode _enable_debug$7;
   static final PyCode _disable_debug$8;
   static final PyCode emit$9;
   static final PyCode getBlocksInOrder$10;
   static final PyCode getBlocks$11;
   static final PyCode getRoot$12;
   static final PyCode getContainedGraphs$13;
   static final PyCode order_blocks$14;
   static final PyCode find_next$15;
   static final PyCode Block$16;
   static final PyCode __init__$17;
   static final PyCode __repr__$18;
   static final PyCode __str__$19;
   static final PyCode emit$20;
   static final PyCode getInstructions$21;
   static final PyCode addOutEdge$22;
   static final PyCode addNext$23;
   static final PyCode has_unconditional_transfer$24;
   static final PyCode get_children$25;
   static final PyCode get_followers$26;
   static final PyCode getContainedGraphs$27;
   static final PyCode PyFlowGraph$28;
   static final PyCode __init__$29;
   static final PyCode setDocstring$30;
   static final PyCode setFlag$31;
   static final PyCode checkFlag$32;
   static final PyCode setFreeVars$33;
   static final PyCode setCellVars$34;
   static final PyCode getCode$35;
   static final PyCode dump$36;
   static final PyCode computeStackDepth$37;
   static final PyCode max_depth$38;
   static final PyCode flattenGraph$39;
   static final PyCode convertArgs$40;
   static final PyCode sort_cellvars$41;
   static final PyCode _lookupName$42;
   static final PyCode _convert_LOAD_CONST$43;
   static final PyCode _convert_LOAD_FAST$44;
   static final PyCode _convert_LOAD_NAME$45;
   static final PyCode _convert_NAME$46;
   static final PyCode _convert_DEREF$47;
   static final PyCode _convert_LOAD_CLOSURE$48;
   static final PyCode _convert_COMPARE_OP$49;
   static final PyCode makeByteCode$50;
   static final PyCode newCodeObject$51;
   static final PyCode getConsts$52;
   static final PyCode isJump$53;
   static final PyCode TupleArg$54;
   static final PyCode __init__$55;
   static final PyCode __repr__$56;
   static final PyCode getName$57;
   static final PyCode getArgCount$58;
   static final PyCode twobyte$59;
   static final PyCode LineAddrTable$60;
   static final PyCode __init__$61;
   static final PyCode addCode$62;
   static final PyCode nextLine$63;
   static final PyCode getCode$64;
   static final PyCode getTable$65;
   static final PyCode StackDepthTracker$66;
   static final PyCode findDepth$67;
   static final PyCode UNPACK_SEQUENCE$68;
   static final PyCode BUILD_TUPLE$69;
   static final PyCode BUILD_LIST$70;
   static final PyCode BUILD_SET$71;
   static final PyCode CALL_FUNCTION$72;
   static final PyCode CALL_FUNCTION_VAR$73;
   static final PyCode CALL_FUNCTION_KW$74;
   static final PyCode CALL_FUNCTION_VAR_KW$75;
   static final PyCode MAKE_FUNCTION$76;
   static final PyCode MAKE_CLOSURE$77;
   static final PyCode BUILD_SLICE$78;
   static final PyCode DUP_TOPX$79;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A flow graph representation for Python bytecode"));
      var1.setline(1);
      PyString.fromInterned("A flow graph representation for Python bytecode");
      var1.setline(3);
      PyObject var3 = imp.importOne("dis", var1, -1);
      var1.setlocal("dis", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"misc"};
      PyObject[] var6 = imp.importFrom("compiler", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("misc", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"CO_OPTIMIZED", "CO_NEWLOCALS", "CO_VARARGS", "CO_VARKEYWORDS"};
      var6 = imp.importFrom("compiler.consts", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("CO_OPTIMIZED", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CO_NEWLOCALS", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("CO_VARARGS", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("CO_VARKEYWORDS", var4);
      var4 = null;
      var1.setline(11);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("FlowGraph", var6, FlowGraph$1);
      var1.setlocal("FlowGraph", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(98);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, order_blocks$14, PyString.fromInterned("Order blocks so that they are emitted in the right order"));
      var1.setlocal("order_blocks", var7);
      var3 = null;
      var1.setline(165);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Block", var6, Block$16);
      var1.setlocal("Block", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(249);
      PyString var8 = PyString.fromInterned("RAW");
      var1.setlocal("RAW", var8);
      var3 = null;
      var1.setline(250);
      var8 = PyString.fromInterned("FLAT");
      var1.setlocal("FLAT", var8);
      var3 = null;
      var1.setline(251);
      var8 = PyString.fromInterned("CONV");
      var1.setlocal("CONV", var8);
      var3 = null;
      var1.setline(252);
      var8 = PyString.fromInterned("DONE");
      var1.setlocal("DONE", var8);
      var3 = null;
      var1.setline(254);
      var6 = new PyObject[]{var1.getname("FlowGraph")};
      var4 = Py.makeClass("PyFlowGraph", var6, PyFlowGraph$28);
      var1.setlocal("PyFlowGraph", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(559);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isJump$53, (PyObject)null);
      var1.setlocal("isJump", var7);
      var3 = null;
      var1.setline(563);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("TupleArg", var6, TupleArg$54);
      var1.setlocal("TupleArg", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(573);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getArgCount$58, (PyObject)null);
      var1.setlocal("getArgCount", var7);
      var3 = null;
      var1.setline(582);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, twobyte$59, PyString.fromInterned("Convert an int argument into high and low bytes"));
      var1.setlocal("twobyte", var7);
      var3 = null;
      var1.setline(587);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("LineAddrTable", var6, LineAddrTable$60);
      var1.setlocal("LineAddrTable", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(653);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("StackDepthTracker", var6, StackDepthTracker$66);
      var1.setlocal("StackDepthTracker", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(763);
      var3 = var1.getname("StackDepthTracker").__call__(var2).__getattr__("findDepth");
      var1.setlocal("findDepth", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FlowGraph$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startBlock$3, (PyObject)null);
      var1.setlocal("startBlock", var4);
      var3 = null;
      var1.setline(29);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, nextBlock$4, (PyObject)null);
      var1.setlocal("nextBlock", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, newBlock$5, (PyObject)null);
      var1.setlocal("newBlock", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startExitBlock$6, (PyObject)null);
      var1.setlocal("startExitBlock", var4);
      var3 = null;
      var1.setline(61);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal("_debug", var5);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _enable_debug$7, (PyObject)null);
      var1.setlocal("_enable_debug", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _disable_debug$8, (PyObject)null);
      var1.setlocal("_disable_debug", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$9, (PyObject)null);
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getBlocksInOrder$10, PyString.fromInterned("Return the blocks in reverse postorder\n\n        i.e. each node appears before all of its successors\n        "));
      var1.setlocal("getBlocksInOrder", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getBlocks$11, (PyObject)null);
      var1.setlocal("getBlocks", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getRoot$12, PyString.fromInterned("Return nodes appropriate for use with dominator"));
      var1.setlocal("getRoot", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getContainedGraphs$13, (PyObject)null);
      var1.setlocal("getContainedGraphs", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getglobal("Block").__call__(var2);
      var1.getlocal(0).__setattr__("current", var3);
      var1.getlocal(0).__setattr__("entry", var3);
      var1.setline(14);
      var3 = var1.getglobal("Block").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exit"));
      var1.getlocal(0).__setattr__("exit", var3);
      var3 = null;
      var1.setline(15);
      var3 = var1.getglobal("misc").__getattr__("Set").__call__(var2);
      var1.getlocal(0).__setattr__("blocks", var3);
      var3 = null;
      var1.setline(16);
      var1.getlocal(0).__getattr__("blocks").__getattr__("add").__call__(var2, var1.getlocal(0).__getattr__("entry"));
      var1.setline(17);
      var1.getlocal(0).__getattr__("blocks").__getattr__("add").__call__(var2, var1.getlocal(0).__getattr__("exit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startBlock$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      if (var1.getlocal(0).__getattr__("_debug").__nonzero__()) {
         var1.setline(21);
         if (var1.getlocal(0).__getattr__("current").__nonzero__()) {
            var1.setline(22);
            Py.printComma(PyString.fromInterned("end"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("current")));
            var1.setline(23);
            Py.printComma(PyString.fromInterned("    next"));
            Py.println(var1.getlocal(0).__getattr__("current").__getattr__("next"));
            var1.setline(24);
            Py.printComma(PyString.fromInterned("    prev"));
            Py.println(var1.getlocal(0).__getattr__("current").__getattr__("prev"));
            var1.setline(25);
            Py.printComma(PyString.fromInterned("   "));
            Py.println(var1.getlocal(0).__getattr__("current").__getattr__("get_children").__call__(var2));
         }

         var1.setline(26);
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(27);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("current", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject nextBlock$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(50);
      var1.getlocal(0).__getattr__("current").__getattr__("addNext").__call__(var2, var1.getlocal(1));
      var1.setline(51);
      var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject newBlock$5(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = var1.getglobal("Block").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(55);
      var1.getlocal(0).__getattr__("blocks").__getattr__("add").__call__(var2, var1.getlocal(1));
      var1.setline(56);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startExitBlock$6(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(0).__getattr__("exit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _enable_debug$7(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_debug", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _disable_debug$8(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_debug", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$9(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      if (var1.getlocal(0).__getattr__("_debug").__nonzero__()) {
         var1.setline(71);
         Py.printComma(PyString.fromInterned("\t"));
         Py.println(var1.getlocal(1));
      }

      var1.setline(72);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getglobal("Block"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(73);
         var1.getlocal(0).__getattr__("current").__getattr__("addOutEdge").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      }

      var1.setline(74);
      var1.getlocal(0).__getattr__("current").__getattr__("emit").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getBlocksInOrder$10(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Return the blocks in reverse postorder\n\n        i.e. each node appears before all of its successors\n        ");
      var1.setline(81);
      PyObject var3 = var1.getglobal("order_blocks").__call__(var2, var1.getlocal(0).__getattr__("entry"), var1.getlocal(0).__getattr__("exit"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getBlocks$11(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getlocal(0).__getattr__("blocks").__getattr__("elements").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getRoot$12(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Return nodes appropriate for use with dominator");
      var1.setline(89);
      PyObject var3 = var1.getlocal(0).__getattr__("entry");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getContainedGraphs$13(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(93);
      PyObject var5 = var1.getlocal(0).__getattr__("getBlocks").__call__(var2).__iter__();

      while(true) {
         var1.setline(93);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(95);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(94);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("getContainedGraphs").__call__(var2));
      }
   }

   public PyObject order_blocks$14(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Order blocks so that they are emitted in the right order");
      var1.setline(105);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(108);
      PyObject var8 = var1.getglobal("set").__call__(var2);
      var1.setderef(1, var8);
      var3 = null;
      var1.setline(109);
      var3 = new PyList(new PyObject[]{var1.getlocal(0)});
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         PyObject var4;
         PyObject var5;
         PyObject var10000;
         do {
            var1.setline(110);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(121);
               PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
               var1.setderef(0, var9);
               var3 = null;
               var1.setline(122);
               var8 = var1.getderef(1).__iter__();

               while(true) {
                  var1.setline(122);
                  var4 = var8.__iternext__();
                  if (var4 == null) {
                     var1.setline(140);
                     PyObject[] var10 = Py.EmptyObjects;
                     PyObject var10002 = var1.f_globals;
                     PyObject[] var10003 = var10;
                     PyCode var10004 = find_next$15;
                     var10 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
                     PyFunction var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
                     var1.setlocal(6, var11);
                     var3 = null;
                     var1.setline(150);
                     var8 = var1.getlocal(0);
                     var1.setlocal(4, var8);
                     var3 = null;

                     while(true) {
                        var1.setline(151);
                        if (!Py.newInteger(1).__nonzero__()) {
                           break;
                        }

                        var1.setline(152);
                        var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                        var1.setline(153);
                        var1.getderef(1).__getattr__("discard").__call__(var2, var1.getlocal(4));
                        var1.setline(154);
                        if (var1.getlocal(4).__getattr__("next").__nonzero__()) {
                           var1.setline(155);
                           var8 = var1.getlocal(4).__getattr__("next").__getitem__(Py.newInteger(0));
                           var1.setlocal(4, var8);
                           var3 = null;
                        } else {
                           var1.setline(157);
                           var8 = var1.getlocal(4);
                           var10000 = var8._isnot(var1.getlocal(1));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getlocal(4).__getattr__("has_unconditional_transfer").__call__(var2).__not__();
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(158);
                              var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1));
                           }

                           var1.setline(159);
                           if (var1.getderef(1).__not__().__nonzero__()) {
                              break;
                           }

                           var1.setline(161);
                           var8 = var1.getlocal(6).__call__(var2);
                           var1.setlocal(4, var8);
                           var3 = null;
                        }
                     }

                     var1.setline(162);
                     var8 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(123);
                  var10000 = var1.getglobal("__debug__");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(4).__getattr__("next");
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(124);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var5 = var1.getlocal(4);
                        var10000 = var5._is(var1.getlocal(4).__getattr__("next").__getitem__(Py.newInteger(0)).__getattr__("prev").__getitem__(Py.newInteger(0)));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(4).__getattr__("next")}));
                        }
                     }
                  }

                  var1.setline(127);
                  var1.getderef(0).__getattr__("setdefault").__call__(var2, var1.getlocal(4), var1.getglobal("set").__call__(var2));
                  var1.setline(129);
                  var5 = var1.getlocal(4).__getattr__("get_followers").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(129);
                     PyObject var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(5, var6);

                     while(true) {
                        var1.setline(130);
                        if (!Py.newInteger(1).__nonzero__()) {
                           break;
                        }

                        var1.setline(131);
                        var1.getderef(0).__getattr__("setdefault").__call__(var2, var1.getlocal(5), var1.getglobal("set").__call__(var2)).__getattr__("add").__call__(var2, var1.getlocal(4));
                        var1.setline(135);
                        var10000 = var1.getlocal(5).__getattr__("prev");
                        PyObject var7;
                        if (var10000.__nonzero__()) {
                           var7 = var1.getlocal(5).__getattr__("prev").__getitem__(Py.newInteger(0));
                           var10000 = var7._isnot(var1.getlocal(4));
                           var7 = null;
                        }

                        if (!var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(136);
                        var7 = var1.getlocal(5).__getattr__("prev").__getitem__(Py.newInteger(0));
                        var1.setlocal(5, var7);
                        var7 = null;
                     }
                  }
               }
            }

            var1.setline(111);
            var8 = var1.getlocal(3).__getattr__("pop").__call__(var2);
            var1.setlocal(4, var8);
            var3 = null;
            var1.setline(112);
            var8 = var1.getlocal(4);
            var10000 = var8._in(var1.getderef(1));
            var3 = null;
         } while(var10000.__nonzero__());

         var1.setline(114);
         var1.getderef(1).__getattr__("add").__call__(var2, var1.getlocal(4));
         var1.setline(115);
         var8 = var1.getlocal(4).__getattr__("get_children").__call__(var2).__iter__();

         while(true) {
            var1.setline(115);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(116);
            var5 = var1.getlocal(5);
            var10000 = var5._notin(var1.getderef(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(117);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject find_next$15(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getderef(0).__iter__();

      while(true) {
         var1.setline(142);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(148);
            if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("circular dependency, cannot find next block"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(143);
         PyObject var5 = var1.getderef(1).__getitem__(var1.getlocal(0)).__iter__();

         while(true) {
            var1.setline(143);
            PyObject var6 = var5.__iternext__();
            PyObject var7;
            if (var6 == null) {
               var1.setline(147);
               var7 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(1, var6);
            var1.setline(144);
            var7 = var1.getlocal(1);
            PyObject var10000 = var7._in(var1.getderef(0));
            var7 = null;
            if (var10000.__nonzero__()) {
               break;
            }
         }
      }
   }

   public PyObject Block$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(166);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("_count", var3);
      var3 = null;
      var1.setline(168);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(177);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$18, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(183);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$19, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(188);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, emit$20, (PyObject)null);
      var1.setlocal("emit", var5);
      var3 = null;
      var1.setline(192);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getInstructions$21, (PyObject)null);
      var1.setlocal("getInstructions", var5);
      var3 = null;
      var1.setline(195);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addOutEdge$22, (PyObject)null);
      var1.setlocal("addOutEdge", var5);
      var3 = null;
      var1.setline(198);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addNext$23, (PyObject)null);
      var1.setlocal("addNext", var5);
      var3 = null;
      var1.setline(204);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("RETURN_VALUE"), PyString.fromInterned("RAISE_VARARGS"), PyString.fromInterned("JUMP_ABSOLUTE"), PyString.fromInterned("JUMP_FORWARD"), PyString.fromInterned("CONTINUE_LOOP")});
      var1.setlocal("_uncond_transfer", var6);
      var3 = null;
      var1.setline(208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, has_unconditional_transfer$24, PyString.fromInterned("Returns True if there is an unconditional transfer to an other block\n        at the end of this block. This means there is no risk for the bytecode\n        executer to go past this block's bytecode."));
      var1.setlocal("has_unconditional_transfer", var5);
      var3 = null;
      var1.setline(218);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_children$25, (PyObject)null);
      var1.setlocal("get_children", var5);
      var3 = null;
      var1.setline(221);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_followers$26, PyString.fromInterned("Get the whole list of followers, including the next block."));
      var1.setlocal("get_followers", var5);
      var3 = null;
      var1.setline(231);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getContainedGraphs$27, PyString.fromInterned("Return all graphs contained within this block.\n\n        For example, a MAKE_FUNCTION block will contain a reference to\n        the graph for the function body.\n        "));
      var1.setlocal("getContainedGraphs", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"insts", var3);
      var3 = null;
      var1.setline(170);
      PyObject var4 = var1.getglobal("set").__call__(var2);
      var1.getlocal(0).__setattr__("outEdges", var4);
      var3 = null;
      var1.setline(171);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("label", var4);
      var3 = null;
      var1.setline(172);
      var4 = var1.getglobal("Block").__getattr__("_count");
      var1.getlocal(0).__setattr__("bid", var4);
      var3 = null;
      var1.setline(173);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"next", var3);
      var3 = null;
      var1.setline(174);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"prev", var3);
      var3 = null;
      var1.setline(175);
      var4 = var1.getglobal("Block").__getattr__("_count")._add(Py.newInteger(1));
      var1.getglobal("Block").__setattr__("_count", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$18(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("label").__nonzero__()) {
         var1.setline(179);
         var3 = PyString.fromInterned("<block %s id=%d>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("label"), var1.getlocal(0).__getattr__("bid")}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(181);
         var3 = PyString.fromInterned("<block id=%d>")._mod(var1.getlocal(0).__getattr__("bid"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __str__$19(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(0).__getattr__("insts"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(185);
      var3 = PyString.fromInterned("<block %s %d:\n%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("label"), var1.getlocal(0).__getattr__("bid"), PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(1))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$20(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(190);
      var1.getlocal(0).__getattr__("insts").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getInstructions$21(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getlocal(0).__getattr__("insts");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject addOutEdge$22(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      var1.getlocal(0).__getattr__("outEdges").__getattr__("add").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addNext$23(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      var1.getlocal(0).__getattr__("next").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(200);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("next"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(0).__getattr__("next")));
         }
      }

      var1.setline(201);
      var1.getlocal(1).__getattr__("prev").__getattr__("append").__call__(var2, var1.getlocal(0));
      var1.setline(202);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("prev"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(1).__getattr__("prev")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_unconditional_transfer$24(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyString.fromInterned("Returns True if there is an unconditional transfer to an other block\n        at the end of this block. This means there is no risk for the bytecode\n        executer to go past this block's bytecode.");

      PyException var3;
      PyObject var7;
      try {
         var1.setline(213);
         var7 = var1.getlocal(0).__getattr__("insts").__getitem__(Py.newInteger(-1));
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("IndexError"), var1.getglobal("ValueError")}))) {
            var1.setline(215);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(216);
      var7 = var1.getlocal(1);
      PyObject var10000 = var7._in(var1.getlocal(0).__getattr__("_uncond_transfer"));
      var3 = null;
      var7 = var10000;
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject get_children$25(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("outEdges"))._add(var1.getlocal(0).__getattr__("next"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_followers$26(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyString.fromInterned("Get the whole list of followers, including the next block.");
      var1.setline(223);
      PyObject var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("next"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getlocal(0).__getattr__("insts").__iter__();

      while(true) {
         var1.setline(226);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(229);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(227);
         PyObject var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._in(var1.getglobal("PyFlowGraph").__getattr__("hasjrel"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(228);
            var1.getlocal(1).__getattr__("add").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
         }
      }
   }

   public PyObject getContainedGraphs$27(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyString.fromInterned("Return all graphs contained within this block.\n\n        For example, a MAKE_FUNCTION block will contain a reference to\n        the graph for the function body.\n        ");
      var1.setline(237);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(238);
      PyObject var6 = var1.getlocal(0).__getattr__("insts").__iter__();

      while(true) {
         var1.setline(238);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(244);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(239);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(241);
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(242);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("graph")).__nonzero__()) {
               var1.setline(243);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("graph"));
            }
         }
      }
   }

   public PyObject PyFlowGraph$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(255);
      PyObject var3 = var1.getname("FlowGraph").__getattr__("__init__");
      var1.setlocal("super_init", var3);
      var3 = null;
      var1.setline(257);
      PyObject[] var7 = new PyObject[]{new PyTuple(Py.EmptyObjects), Py.newInteger(0), var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(287);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, setDocstring$30, (PyObject)null);
      var1.setlocal("setDocstring", var8);
      var3 = null;
      var1.setline(290);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, setFlag$31, (PyObject)null);
      var1.setlocal("setFlag", var8);
      var3 = null;
      var1.setline(295);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, checkFlag$32, (PyObject)null);
      var1.setlocal("checkFlag", var8);
      var3 = null;
      var1.setline(299);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, setFreeVars$33, (PyObject)null);
      var1.setlocal("setFreeVars", var8);
      var3 = null;
      var1.setline(302);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, setCellVars$34, (PyObject)null);
      var1.setlocal("setCellVars", var8);
      var3 = null;
      var1.setline(305);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, getCode$35, PyString.fromInterned("Get a Python code object"));
      var1.setlocal("getCode", var8);
      var3 = null;
      var1.setline(317);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, dump$36, (PyObject)null);
      var1.setlocal("dump", var8);
      var3 = null;
      var1.setline(335);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, computeStackDepth$37, PyString.fromInterned("Compute the max stack depth.\n\n        Approach is to compute the stack effect of each basic block.\n        Then find the path through the code with the largest total\n        effect.\n        "));
      var1.setlocal("computeStackDepth", var8);
      var3 = null;
      var1.setline(365);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, flattenGraph$39, PyString.fromInterned("Arrange the blocks in order and resolve jumps"));
      var1.setlocal("flattenGraph", var8);
      var3 = null;
      var1.setline(398);
      var3 = var1.getname("set").__call__(var2);
      var1.setlocal("hasjrel", var3);
      var3 = null;
      var1.setline(399);
      var3 = var1.getname("dis").__getattr__("hasjrel").__iter__();

      while(true) {
         var1.setline(399);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(401);
            var3 = var1.getname("set").__call__(var2);
            var1.setlocal("hasjabs", var3);
            var3 = null;
            var1.setline(402);
            var3 = var1.getname("dis").__getattr__("hasjabs").__iter__();

            while(true) {
               var1.setline(402);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(405);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, convertArgs$40, PyString.fromInterned("Convert arguments from symbolic to concrete form"));
                  var1.setlocal("convertArgs", var8);
                  var3 = null;
                  var1.setline(419);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, sort_cellvars$41, PyString.fromInterned("Sort cellvars in the order of varnames and prune from freevars.\n        "));
                  var1.setlocal("sort_cellvars", var8);
                  var3 = null;
                  var1.setline(432);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _lookupName$42, PyString.fromInterned("Return index of name in list, appending if necessary\n\n        This routine uses a list instead of a dictionary, because a\n        dictionary can't store two different keys if the keys have the\n        same value but different types, e.g. 2 and 2L.  The compiler\n        must treat these two separately, so it does an explicit type\n        comparison before comparing the values.\n        "));
                  var1.setlocal("_lookupName", var8);
                  var3 = null;
                  var1.setline(449);
                  PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
                  var1.setlocal("_converters", var10);
                  var3 = null;
                  var1.setline(450);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_LOAD_CONST$43, (PyObject)null);
                  var1.setlocal("_convert_LOAD_CONST", var8);
                  var3 = null;
                  var1.setline(455);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_LOAD_FAST$44, (PyObject)null);
                  var1.setlocal("_convert_LOAD_FAST", var8);
                  var3 = null;
                  var1.setline(458);
                  var3 = var1.getname("_convert_LOAD_FAST");
                  var1.setlocal("_convert_STORE_FAST", var3);
                  var3 = null;
                  var1.setline(459);
                  var3 = var1.getname("_convert_LOAD_FAST");
                  var1.setlocal("_convert_DELETE_FAST", var3);
                  var3 = null;
                  var1.setline(461);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_LOAD_NAME$45, (PyObject)null);
                  var1.setlocal("_convert_LOAD_NAME", var8);
                  var3 = null;
                  var1.setline(466);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_NAME$46, (PyObject)null);
                  var1.setlocal("_convert_NAME", var8);
                  var3 = null;
                  var1.setline(470);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_STORE_NAME", var3);
                  var3 = null;
                  var1.setline(471);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_DELETE_NAME", var3);
                  var3 = null;
                  var1.setline(472);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_IMPORT_NAME", var3);
                  var3 = null;
                  var1.setline(473);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_IMPORT_FROM", var3);
                  var3 = null;
                  var1.setline(474);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_STORE_ATTR", var3);
                  var3 = null;
                  var1.setline(475);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_LOAD_ATTR", var3);
                  var3 = null;
                  var1.setline(476);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_DELETE_ATTR", var3);
                  var3 = null;
                  var1.setline(477);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_LOAD_GLOBAL", var3);
                  var3 = null;
                  var1.setline(478);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_STORE_GLOBAL", var3);
                  var3 = null;
                  var1.setline(479);
                  var3 = var1.getname("_convert_NAME");
                  var1.setlocal("_convert_DELETE_GLOBAL", var3);
                  var3 = null;
                  var1.setline(481);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_DEREF$47, (PyObject)null);
                  var1.setlocal("_convert_DEREF", var8);
                  var3 = null;
                  var1.setline(485);
                  var3 = var1.getname("_convert_DEREF");
                  var1.setlocal("_convert_LOAD_DEREF", var3);
                  var3 = null;
                  var1.setline(486);
                  var3 = var1.getname("_convert_DEREF");
                  var1.setlocal("_convert_STORE_DEREF", var3);
                  var3 = null;
                  var1.setline(488);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_LOAD_CLOSURE$48, (PyObject)null);
                  var1.setlocal("_convert_LOAD_CLOSURE", var8);
                  var3 = null;
                  var1.setline(492);
                  var3 = var1.getname("list").__call__(var2, var1.getname("dis").__getattr__("cmp_op"));
                  var1.setlocal("_cmp", var3);
                  var3 = null;
                  var1.setline(493);
                  var7 = Py.EmptyObjects;
                  var8 = new PyFunction(var1.f_globals, var7, _convert_COMPARE_OP$49, (PyObject)null);
                  var1.setlocal("_convert_COMPARE_OP", var8);
                  var3 = null;
                  var1.setline(498);
                  var3 = var1.getname("locals").__call__(var2).__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(498);
                     var4 = var3.__iternext__();
                     PyObject[] var5;
                     PyObject var9;
                     if (var4 == null) {
                        var1.setline(502);
                        var1.dellocal("name");
                        var1.dellocal("obj");
                        var1.dellocal("opname");
                        var1.setline(504);
                        var7 = Py.EmptyObjects;
                        var8 = new PyFunction(var1.f_globals, var7, makeByteCode$50, (PyObject)null);
                        var1.setlocal("makeByteCode", var8);
                        var3 = null;
                        var1.setline(525);
                        var10 = new PyDictionary(Py.EmptyObjects);
                        var1.setlocal("opnum", var10);
                        var3 = null;
                        var1.setline(526);
                        var3 = var1.getname("range").__call__(var2, var1.getname("len").__call__(var2, var1.getname("dis").__getattr__("opname"))).__iter__();

                        while(true) {
                           var1.setline(526);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(528);
                              var1.dellocal("num");
                              var1.setline(530);
                              var7 = Py.EmptyObjects;
                              var8 = new PyFunction(var1.f_globals, var7, newCodeObject$51, (PyObject)null);
                              var1.setlocal("newCodeObject", var8);
                              var3 = null;
                              var1.setline(546);
                              var7 = Py.EmptyObjects;
                              var8 = new PyFunction(var1.f_globals, var7, getConsts$52, PyString.fromInterned("Return a tuple for the const slot of the code object\n\n        Must convert references to code (MAKE_FUNCTION) to code\n        objects recursively.\n        "));
                              var1.setlocal("getConsts", var8);
                              var3 = null;
                              return var1.getf_locals();
                           }

                           var1.setlocal("num", var4);
                           var1.setline(527);
                           var9 = var1.getname("num");
                           var1.getname("opnum").__setitem__(var1.getname("dis").__getattr__("opname").__getitem__(var1.getname("num")), var9);
                           var5 = null;
                        }
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     PyObject var6 = var5[0];
                     var1.setlocal("name", var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal("obj", var6);
                     var6 = null;
                     var1.setline(499);
                     var9 = var1.getname("name").__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null);
                     PyObject var10000 = var9._eq(PyString.fromInterned("_convert_"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(500);
                        var9 = var1.getname("name").__getslice__(Py.newInteger(9), (PyObject)null, (PyObject)null);
                        var1.setlocal("opname", var9);
                        var5 = null;
                        var1.setline(501);
                        var9 = var1.getname("obj");
                        var1.getname("_converters").__setitem__(var1.getname("opname"), var9);
                        var5 = null;
                     }
                  }
               }

               var1.setlocal("i", var4);
               var1.setline(403);
               var1.getname("hasjabs").__getattr__("add").__call__(var2, var1.getname("dis").__getattr__("opname").__getitem__(var1.getname("i")));
            }
         }

         var1.setlocal("i", var4);
         var1.setline(400);
         var1.getname("hasjrel").__getattr__("add").__call__(var2, var1.getname("dis").__getattr__("opname").__getitem__(var1.getname("i")));
      }
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      var1.getlocal(0).__getattr__("super_init").__call__(var2);
      var1.setline(259);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(260);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("docstring", var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("args", var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getglobal("getArgCount").__call__(var2, var1.getlocal(3));
      var1.getlocal(0).__setattr__("argcount", var3);
      var3 = null;
      var1.setline(264);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("klass", var3);
      var3 = null;
      var1.setline(265);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(266);
         var3 = var1.getglobal("CO_OPTIMIZED")._or(var1.getglobal("CO_NEWLOCALS"));
         var1.getlocal(0).__setattr__("flags", var3);
         var3 = null;
      } else {
         var1.setline(268);
         PyInteger var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"flags", var6);
         var3 = null;
      }

      var1.setline(269);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"consts", var7);
      var3 = null;
      var1.setline(270);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"names", var7);
      var3 = null;
      var1.setline(273);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"freevars", var7);
      var3 = null;
      var1.setline(274);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"cellvars", var7);
      var3 = null;
      var1.setline(279);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"closure", var7);
      var3 = null;
      var1.setline(280);
      Object var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var8 = var10000;
      var1.getlocal(0).__setattr__((String)"varnames", (PyObject)var8);
      var3 = null;
      var1.setline(281);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("varnames"))).__iter__();

      while(true) {
         var1.setline(281);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(285);
            var3 = var1.getglobal("RAW");
            var1.getlocal(0).__setattr__("stage", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(282);
         PyObject var5 = var1.getlocal(0).__getattr__("varnames").__getitem__(var1.getlocal(6));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(283);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("TupleArg")).__nonzero__()) {
            var1.setline(284);
            var5 = var1.getlocal(7).__getattr__("getName").__call__(var2);
            var1.getlocal(0).__getattr__("varnames").__setitem__(var1.getlocal(6), var5);
            var5 = null;
         }
      }
   }

   public PyObject setDocstring$30(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("docstring", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setFlag$31(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getlocal(0).__getattr__("flags")._or(var1.getlocal(1));
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(292);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("CO_VARARGS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(293);
         var3 = var1.getlocal(0).__getattr__("argcount")._sub(Py.newInteger(1));
         var1.getlocal(0).__setattr__("argcount", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject checkFlag$32(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      if (var1.getlocal(0).__getattr__("flags")._and(var1.getlocal(1)).__nonzero__()) {
         var1.setline(297);
         PyInteger var3 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject setFreeVars$33(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("freevars", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setCellVars$34(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("cellvars", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getCode$35(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Get a Python code object");
      var1.setline(307);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("RAW"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(308);
      var1.getlocal(0).__getattr__("computeStackDepth").__call__(var2);
      var1.setline(309);
      var1.getlocal(0).__getattr__("flattenGraph").__call__(var2);
      var1.setline(310);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("FLAT"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(311);
      var1.getlocal(0).__getattr__("convertArgs").__call__(var2);
      var1.setline(312);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("CONV"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(313);
      var1.getlocal(0).__getattr__("makeByteCode").__call__(var2);
      var1.setline(314);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("DONE"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(315);
      var3 = var1.getlocal(0).__getattr__("newCodeObject").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dump$36(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(319);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(320);
         var3 = var1.getlocal(1);
         var1.getglobal("sys").__setattr__("stdout", var3);
         var3 = null;
      }

      var1.setline(321);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(322);
      var3 = var1.getlocal(0).__getattr__("insts").__iter__();

      while(true) {
         var1.setline(322);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(332);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(333);
               var3 = var1.getlocal(2);
               var1.getglobal("sys").__setattr__("stdout", var3);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(323);
         PyObject var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(324);
         var5 = var1.getlocal(5);
         PyObject var10000 = var5._eq(PyString.fromInterned("SET_LINENO"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(325);
            Py.println();
         }

         var1.setline(326);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(327);
            Py.printComma(PyString.fromInterned("\t"));
            Py.printComma(PyString.fromInterned("%3d")._mod(var1.getlocal(3)));
            Py.println(var1.getlocal(5));
            var1.setline(328);
            var5 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(330);
            Py.printComma(PyString.fromInterned("\t"));
            Py.printComma(PyString.fromInterned("%3d")._mod(var1.getlocal(3)));
            Py.printComma(var1.getlocal(5));
            Py.println(var1.getlocal(4).__getitem__(Py.newInteger(1)));
            var1.setline(331);
            var5 = var1.getlocal(3)._add(Py.newInteger(3));
            var1.setlocal(3, var5);
            var5 = null;
         }
      }
   }

   public PyObject computeStackDepth$37(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 1);
      var1.setline(341);
      PyString.fromInterned("Compute the max stack depth.\n\n        Approach is to compute the stack effect of each basic block.\n        Then find the path through the code with the largest total\n        effect.\n        ");
      var1.setline(342);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(343);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(344);
      var6 = var1.getderef(1).__getattr__("getBlocks").__call__(var2).__iter__();

      while(true) {
         var1.setline(344);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(347);
            var3 = new PyDictionary(Py.EmptyObjects);
            var1.setderef(2, var3);
            var3 = null;
            var1.setline(349);
            PyObject[] var7 = Py.EmptyObjects;
            PyObject var10002 = var1.f_globals;
            PyObject[] var10003 = var7;
            PyCode var10004 = max_depth$38;
            var7 = new PyObject[]{var1.getclosure(2), var1.getclosure(3), var1.getclosure(0), var1.getclosure(1)};
            PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
            var1.setderef(0, var8);
            var3 = null;
            var1.setline(363);
            var6 = var1.getderef(0).__call__((ThreadState)var2, (PyObject)var1.getderef(1).__getattr__("entry"), (PyObject)Py.newInteger(0));
            var1.getderef(1).__setattr__("stacksize", var6);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(345);
         PyObject var5 = var1.getglobal("findDepth").__call__(var2, var1.getlocal(2).__getattr__("getInstructions").__call__(var2));
         var1.getderef(3).__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject max_depth$38(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getderef(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(351);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(352);
         PyInteger var4 = Py.newInteger(1);
         var1.getderef(0).__setitem__((PyObject)var1.getlocal(0), var4);
         var4 = null;
         var1.setline(353);
         PyObject var6 = var1.getlocal(1)._add(var1.getderef(1).__getitem__(var1.getlocal(0)));
         var1.setlocal(1, var6);
         var4 = null;
         var1.setline(354);
         var6 = var1.getlocal(0).__getattr__("get_children").__call__(var2);
         var1.setlocal(2, var6);
         var4 = null;
         var1.setline(355);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(358);
            var6 = var1.getlocal(0).__getattr__("label");
            var10000 = var6._eq(PyString.fromInterned("exit"));
            var4 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(359);
               var3 = var1.getderef(2).__call__(var2, var1.getderef(3).__getattr__("exit"), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(361);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(356);
            var10000 = var1.getglobal("max");
            PyList var10002 = new PyList();
            var6 = var10002.__getattr__("append");
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(356);
            var6 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(356);
               PyObject var5 = var6.__iternext__();
               if (var5 == null) {
                  var1.setline(356);
                  var1.dellocal(3);
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(356);
               var1.getlocal(3).__call__(var2, var1.getderef(2).__call__(var2, var1.getlocal(4), var1.getlocal(1)));
            }
         }
      }
   }

   public PyObject flattenGraph$39(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyString.fromInterned("Arrange the blocks in order and resolve jumps");
      var1.setline(367);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("RAW"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(368);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"insts", var8);
      var1.setlocal(1, var8);
      var1.setline(369);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(370);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(371);
      var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(372);
      var3 = var1.getlocal(0).__getattr__("getBlocksInOrder").__call__(var2).__iter__();

      while(true) {
         var1.setline(372);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(382);
            var9 = Py.newInteger(0);
            var1.setlocal(2, var9);
            var3 = null;
            var1.setline(383);
            var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

            while(true) {
               var1.setline(383);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(396);
                  var3 = var1.getglobal("FLAT");
                  var1.getlocal(0).__setattr__("stage", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(384);
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(7));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(385);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var10000 = var5._eq(Py.newInteger(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(386);
                  var5 = var1.getlocal(2)._add(Py.newInteger(1));
                  var1.setlocal(2, var5);
                  var5 = null;
               } else {
                  var1.setline(387);
                  var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                  var10000 = var5._ne(PyString.fromInterned("SET_LINENO"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(388);
                     var5 = var1.getlocal(2)._add(Py.newInteger(3));
                     var1.setlocal(2, var5);
                     var5 = null;
                  }
               }

               var1.setline(389);
               var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(390);
               var5 = var1.getlocal(8);
               var10000 = var5._in(var1.getlocal(0).__getattr__("hasjrel"));
               var5 = null;
               PyTuple var11;
               if (var10000.__nonzero__()) {
                  var1.setline(391);
                  var5 = var1.getlocal(6).__getitem__(Py.newInteger(1));
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(392);
                  var5 = var1.getlocal(3).__getitem__(var1.getlocal(9))._sub(var1.getlocal(2));
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(393);
                  var11 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(10)});
                  var1.getlocal(1).__setitem__((PyObject)var1.getlocal(7), var11);
                  var5 = null;
               } else {
                  var1.setline(394);
                  var5 = var1.getlocal(8);
                  var10000 = var5._in(var1.getlocal(0).__getattr__("hasjabs"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(395);
                     var11 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(3).__getitem__(var1.getlocal(6).__getitem__(Py.newInteger(1)))});
                     var1.getlocal(1).__setitem__((PyObject)var1.getlocal(7), var11);
                     var5 = null;
                  }
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(373);
         var5 = var1.getlocal(2);
         var1.getlocal(3).__setitem__(var1.getlocal(5), var5);
         var5 = null;
         var1.setline(374);
         var5 = var1.getlocal(5).__getattr__("getInstructions").__call__(var2).__iter__();

         while(true) {
            var1.setline(374);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(381);
               var5 = var1.getlocal(2);
               var1.getlocal(4).__setitem__(var1.getlocal(5), var5);
               var5 = null;
               break;
            }

            var1.setlocal(6, var6);
            var1.setline(375);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(6));
            var1.setline(376);
            PyObject var7 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var10000 = var7._eq(Py.newInteger(1));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(377);
               var7 = var1.getlocal(2)._add(Py.newInteger(1));
               var1.setlocal(2, var7);
               var7 = null;
            } else {
               var1.setline(378);
               var7 = var1.getlocal(6).__getitem__(Py.newInteger(0));
               var10000 = var7._ne(PyString.fromInterned("SET_LINENO"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(380);
                  var7 = var1.getlocal(2)._add(Py.newInteger(3));
                  var1.setlocal(2, var7);
                  var7 = null;
               }
            }
         }
      }
   }

   public PyObject convertArgs$40(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyString.fromInterned("Convert arguments from symbolic to concrete form");
      var1.setline(407);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("FLAT"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(408);
      var1.getlocal(0).__getattr__("consts").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("docstring"));
      var1.setline(409);
      var1.getlocal(0).__getattr__("sort_cellvars").__call__(var2);
      var1.setline(410);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("insts"))).__iter__();

      while(true) {
         var1.setline(410);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(417);
            var3 = var1.getglobal("CONV");
            var1.getlocal(0).__setattr__("stage", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(411);
         PyObject var5 = var1.getlocal(0).__getattr__("insts").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(412);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var5._eq(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(413);
            var5 = var1.getlocal(2);
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var5 = null;
            var1.setline(414);
            var5 = var1.getlocal(0).__getattr__("_converters").__getattr__("get").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(415);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(416);
               PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5).__call__(var2, var1.getlocal(0), var1.getlocal(4))});
               var1.getlocal(0).__getattr__("insts").__setitem__((PyObject)var1.getlocal(1), var8);
               var5 = null;
            }
         }
      }
   }

   public PyObject sort_cellvars$41(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyString.fromInterned("Sort cellvars in the order of varnames and prune from freevars.\n        ");
      var1.setline(422);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(423);
      PyObject var6 = var1.getlocal(0).__getattr__("cellvars").__iter__();

      while(true) {
         var1.setline(423);
         PyObject var4 = var6.__iternext__();
         PyInteger var5;
         if (var4 == null) {
            var1.setline(425);
            PyList var10000 = new PyList();
            var6 = var10000.__getattr__("append");
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(425);
            var6 = var1.getlocal(0).__getattr__("varnames").__iter__();

            while(true) {
               var1.setline(425);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(425);
                  var1.dellocal(3);
                  PyList var7 = var10000;
                  var1.getlocal(0).__setattr__((String)"cellvars", var7);
                  var3 = null;
                  var1.setline(427);
                  var6 = var1.getlocal(0).__getattr__("cellvars").__iter__();

                  while(true) {
                     var1.setline(427);
                     var4 = var6.__iternext__();
                     if (var4 == null) {
                        var1.setline(429);
                        var6 = var1.getlocal(0).__getattr__("cellvars")._add(var1.getlocal(1).__getattr__("keys").__call__(var2));
                        var1.getlocal(0).__setattr__("cellvars", var6);
                        var3 = null;
                        var1.setline(430);
                        var6 = var1.getlocal(0).__getattr__("cellvars")._add(var1.getlocal(0).__getattr__("freevars"));
                        var1.getlocal(0).__setattr__("closure", var6);
                        var3 = null;
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(2, var4);
                     var1.setline(428);
                     var1.getlocal(1).__delitem__(var1.getlocal(2));
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(426);
               PyObject var8 = var1.getlocal(2);
               PyObject var10001 = var8._in(var1.getlocal(1));
               var5 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(425);
                  var1.getlocal(3).__call__(var2, var1.getlocal(2));
               }
            }
         }

         var1.setlocal(2, var4);
         var1.setline(424);
         var5 = Py.newInteger(1);
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject _lookupName$42(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyString.fromInterned("Return index of name in list, appending if necessary\n\n        This routine uses a list instead of a dictionary, because a\n        dictionary can't store two different keys if the keys have the\n        same value but different types, e.g. 2 and 2L.  The compiler\n        must treat these two separately, so it does an explicit type\n        comparison before comparing the values.\n        ");
      var1.setline(441);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(442);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(442);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(445);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(446);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1));
            var1.setline(447);
            var5 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(443);
         var5 = var1.getlocal(3);
         var10000 = var5._eq(var1.getglobal("type").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(4))));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2).__getitem__(var1.getlocal(4));
            var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(444);
      var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _convert_LOAD_CONST$43(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("getCode")).__nonzero__()) {
         var1.setline(452);
         var3 = var1.getlocal(1).__getattr__("getCode").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(453);
      var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("consts"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_LOAD_FAST$44(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("names"));
      var1.setline(457);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("varnames"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_LOAD_NAME$45(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getlocal(0).__getattr__("klass");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(463);
         var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("varnames"));
      }

      var1.setline(464);
      var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("names"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_NAME$46(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = var1.getlocal(0).__getattr__("klass");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(468);
         var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("varnames"));
      }

      var1.setline(469);
      var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("names"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_DEREF$47(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("names"));
      var1.setline(483);
      var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("varnames"));
      var1.setline(484);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("closure"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_LOAD_CLOSURE$48(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("varnames"));
      var1.setline(490);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookupName").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("closure"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _convert_COMPARE_OP$49(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__getattr__("index").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeByteCode$50(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("CONV"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(506);
      var3 = var1.getglobal("LineAddrTable").__call__(var2);
      var1.getlocal(0).__setattr__("lnotab", var3);
      var1.setlocal(1, var3);
      var1.setline(507);
      var3 = var1.getlocal(0).__getattr__("insts").__iter__();

      while(true) {
         var1.setline(507);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(523);
            var3 = var1.getglobal("DONE");
            var1.getlocal(0).__setattr__("stage", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(508);
         PyObject var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(509);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(510);
            var1.getlocal(1).__getattr__("addCode").__call__(var2, var1.getlocal(0).__getattr__("opnum").__getitem__(var1.getlocal(3)));
         } else {
            var1.setline(512);
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(513);
            var5 = var1.getlocal(3);
            var10000 = var5._eq(PyString.fromInterned("SET_LINENO"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(514);
               var1.getlocal(1).__getattr__("nextLine").__call__(var2, var1.getlocal(4));
            } else {
               var1.setline(516);
               var5 = var1.getglobal("twobyte").__call__(var2, var1.getlocal(4));
               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;

               try {
                  var1.setline(518);
                  var1.getlocal(1).__getattr__("addCode").__call__(var2, var1.getlocal(0).__getattr__("opnum").__getitem__(var1.getlocal(3)), var1.getlocal(6), var1.getlocal(5));
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (var9.match(var1.getglobal("ValueError"))) {
                     var1.setline(520);
                     Py.printComma(var1.getlocal(3));
                     Py.println(var1.getlocal(4));
                     var1.setline(521);
                     Py.printComma(var1.getlocal(0).__getattr__("opnum").__getitem__(var1.getlocal(3)));
                     Py.printComma(var1.getlocal(6));
                     Py.println(var1.getlocal(5));
                     var1.setline(522);
                     throw Py.makeException();
                  }

                  throw var9;
               }
            }
         }
      }
   }

   public PyObject newCodeObject$51(PyFrame var1, ThreadState var2) {
      var1.setline(531);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stage");
         var10000 = var3._eq(var1.getglobal("DONE"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(532);
      var3 = var1.getlocal(0).__getattr__("flags")._and(var1.getglobal("CO_NEWLOCALS"));
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(533);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(535);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("varnames"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(536);
      var3 = var1.getlocal(0).__getattr__("argcount");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(537);
      if (var1.getlocal(0).__getattr__("flags")._and(var1.getglobal("CO_VARKEYWORDS")).__nonzero__()) {
         var1.setline(538);
         var3 = var1.getlocal(2)._sub(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(539);
      var10000 = var1.getglobal("types").__getattr__("CodeType");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(0).__getattr__("stacksize"), var1.getlocal(0).__getattr__("flags"), var1.getlocal(0).__getattr__("lnotab").__getattr__("getCode").__call__(var2), var1.getlocal(0).__getattr__("getConsts").__call__(var2), var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("names")), var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("varnames")), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("lnotab").__getattr__("firstline"), var1.getlocal(0).__getattr__("lnotab").__getattr__("getTable").__call__(var2), var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("freevars")), var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("cellvars"))};
      var3 = var10000.__call__(var2, var5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getConsts$52(PyFrame var1, ThreadState var2) {
      var1.setline(551);
      PyString.fromInterned("Return a tuple for the const slot of the code object\n\n        Must convert references to code (MAKE_FUNCTION) to code\n        objects recursively.\n        ");
      var1.setline(552);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(553);
      PyObject var6 = var1.getlocal(0).__getattr__("consts").__iter__();

      while(true) {
         var1.setline(553);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(557);
            var6 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(554);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("PyFlowGraph")).__nonzero__()) {
            var1.setline(555);
            PyObject var5 = var1.getlocal(2).__getattr__("getCode").__call__(var2);
            var1.setlocal(2, var5);
            var5 = null;
         }

         var1.setline(556);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject isJump$53(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("JUMP"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(561);
         PyInteger var4 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject TupleArg$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Helper for marking func defs with nested tuples in arglist"));
      var1.setline(564);
      PyString.fromInterned("Helper for marking func defs with nested tuples in arglist");
      var1.setline(565);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$55, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$56, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(570);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getName$57, (PyObject)null);
      var1.setlocal("getName", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("count", var3);
      var3 = null;
      var1.setline(567);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("names", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$56(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      PyObject var3 = PyString.fromInterned("TupleArg(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("count"), var1.getlocal(0).__getattr__("names")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getName$57(PyFrame var1, ThreadState var2) {
      var1.setline(571);
      PyObject var3 = PyString.fromInterned(".%d")._mod(var1.getlocal(0).__getattr__("count"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getArgCount$58(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(575);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(576);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(576);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(577);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("TupleArg")).__nonzero__()) {
               var1.setline(578);
               PyObject var5 = var1.getglobal("len").__call__(var2, var1.getglobal("misc").__getattr__("flatten").__call__(var2, var1.getlocal(2).__getattr__("names")));
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(579);
               var5 = var1.getlocal(1)._sub(var1.getlocal(3));
               var1.setlocal(1, var5);
               var5 = null;
            }
         }
      }

      var1.setline(580);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject twobyte$59(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyString.fromInterned("Convert an int argument into high and low bytes");
      var1.setline(584);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("int")).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(585);
         PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(256));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject LineAddrTable$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("lnotab\n\n    This class builds the lnotab, which is documented in compile.c.\n    Here's a brief recap:\n\n    For each SET_LINENO instruction after the first one, two bytes are\n    added to lnotab.  (In some cases, multiple two-byte entries are\n    added.)  The first byte is the distance in bytes between the\n    instruction for the last SET_LINENO and the current SET_LINENO.\n    The second byte is offset in line numbers.  If either offset is\n    greater than 255, multiple two-byte entries are added -- see\n    compile.c for the delicate details.\n    "));
      var1.setline(600);
      PyString.fromInterned("lnotab\n\n    This class builds the lnotab, which is documented in compile.c.\n    Here's a brief recap:\n\n    For each SET_LINENO instruction after the first one, two bytes are\n    added to lnotab.  (In some cases, multiple two-byte entries are\n    added.)  The first byte is the distance in bytes between the\n    instruction for the last SET_LINENO and the current SET_LINENO.\n    The second byte is offset in line numbers.  If either offset is\n    greater than 255, multiple two-byte entries are added -- see\n    compile.c for the delicate details.\n    ");
      var1.setline(602);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$61, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addCode$62, (PyObject)null);
      var1.setlocal("addCode", var4);
      var3 = null;
      var1.setline(615);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, nextLine$63, (PyObject)null);
      var1.setlocal("nextLine", var4);
      var3 = null;
      var1.setline(647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getCode$64, (PyObject)null);
      var1.setlocal("getCode", var4);
      var3 = null;
      var1.setline(650);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getTable$65, (PyObject)null);
      var1.setlocal("getTable", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$61(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"code", var3);
      var3 = null;
      var1.setline(604);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"codeOffset", var4);
      var3 = null;
      var1.setline(605);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"firstline", var4);
      var3 = null;
      var1.setline(606);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"lastline", var4);
      var3 = null;
      var1.setline(607);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"lastoff", var4);
      var3 = null;
      var1.setline(608);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"lnotab", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addCode$62(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(611);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(613);
            var3 = var1.getlocal(0).__getattr__("codeOffset")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var1.getlocal(0).__setattr__("codeOffset", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(612);
         var1.getlocal(0).__getattr__("code").__getattr__("append").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject nextLine$63(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyObject var3 = var1.getlocal(0).__getattr__("firstline");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(617);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("firstline", var3);
         var3 = null;
         var1.setline(618);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("lastline", var3);
         var3 = null;
      } else {
         var1.setline(621);
         var3 = var1.getlocal(0).__getattr__("codeOffset")._sub(var1.getlocal(0).__getattr__("lastoff"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(622);
         var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("lastline"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(633);
         var3 = var1.getlocal(3);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(634);
            var3 = var1.getlocal(0).__getattr__("lnotab").__getattr__("append");
            var1.setlocal(4, var3);
            var3 = null;

            label26:
            while(true) {
               var1.setline(635);
               var3 = var1.getlocal(2);
               var10000 = var3._gt(Py.newInteger(255));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(638);
                     var3 = var1.getlocal(3);
                     var10000 = var3._gt(Py.newInteger(255));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(642);
                        var3 = var1.getlocal(2);
                        var10000 = var3._gt(Py.newInteger(0));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var3 = var1.getlocal(3);
                           var10000 = var3._gt(Py.newInteger(0));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(643);
                           var1.getlocal(4).__call__(var2, var1.getlocal(2));
                           var1.setline(643);
                           var1.getlocal(4).__call__(var2, var1.getlocal(3));
                        }

                        var1.setline(644);
                        var3 = var1.getlocal(1);
                        var1.getlocal(0).__setattr__("lastline", var3);
                        var3 = null;
                        var1.setline(645);
                        var3 = var1.getlocal(0).__getattr__("codeOffset");
                        var1.getlocal(0).__setattr__("lastoff", var3);
                        var3 = null;
                        break label26;
                     }

                     var1.setline(639);
                     var1.getlocal(4).__call__(var2, var1.getlocal(2));
                     var1.setline(639);
                     var1.getlocal(4).__call__((ThreadState)var2, (PyObject)Py.newInteger(255));
                     var1.setline(640);
                     var3 = var1.getlocal(3);
                     var3 = var3._isub(Py.newInteger(255));
                     var1.setlocal(3, var3);
                     var1.setline(641);
                     PyInteger var4 = Py.newInteger(0);
                     var1.setlocal(2, var4);
                     var3 = null;
                  }
               }

               var1.setline(636);
               var1.getlocal(4).__call__((ThreadState)var2, (PyObject)Py.newInteger(255));
               var1.setline(636);
               var1.getlocal(4).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setline(637);
               var3 = var1.getlocal(2);
               var3 = var3._isub(Py.newInteger(255));
               var1.setlocal(2, var3);
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getCode$64(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getTable$65(PyFrame var1, ThreadState var2) {
      var1.setline(651);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("chr"), var1.getlocal(0).__getattr__("lnotab")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StackDepthTracker$66(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(657);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, findDepth$67, (PyObject)null);
      var1.setlocal("findDepth", var4);
      var3 = null;
      var1.setline(685);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("POP_TOP"), Py.newInteger(-1), PyString.fromInterned("DUP_TOP"), Py.newInteger(1), PyString.fromInterned("LIST_APPEND"), Py.newInteger(-1), PyString.fromInterned("SET_ADD"), Py.newInteger(-1), PyString.fromInterned("MAP_ADD"), Py.newInteger(-2), PyString.fromInterned("SLICE+1"), Py.newInteger(-1), PyString.fromInterned("SLICE+2"), Py.newInteger(-1), PyString.fromInterned("SLICE+3"), Py.newInteger(-2), PyString.fromInterned("STORE_SLICE+0"), Py.newInteger(-1), PyString.fromInterned("STORE_SLICE+1"), Py.newInteger(-2), PyString.fromInterned("STORE_SLICE+2"), Py.newInteger(-2), PyString.fromInterned("STORE_SLICE+3"), Py.newInteger(-3), PyString.fromInterned("DELETE_SLICE+0"), Py.newInteger(-1), PyString.fromInterned("DELETE_SLICE+1"), Py.newInteger(-2), PyString.fromInterned("DELETE_SLICE+2"), Py.newInteger(-2), PyString.fromInterned("DELETE_SLICE+3"), Py.newInteger(-3), PyString.fromInterned("STORE_SUBSCR"), Py.newInteger(-3), PyString.fromInterned("DELETE_SUBSCR"), Py.newInteger(-2), PyString.fromInterned("PRINT_ITEM"), Py.newInteger(-1), PyString.fromInterned("RETURN_VALUE"), Py.newInteger(-1), PyString.fromInterned("YIELD_VALUE"), Py.newInteger(-1), PyString.fromInterned("EXEC_STMT"), Py.newInteger(-3), PyString.fromInterned("BUILD_CLASS"), Py.newInteger(-2), PyString.fromInterned("STORE_NAME"), Py.newInteger(-1), PyString.fromInterned("STORE_ATTR"), Py.newInteger(-2), PyString.fromInterned("DELETE_ATTR"), Py.newInteger(-1), PyString.fromInterned("STORE_GLOBAL"), Py.newInteger(-1), PyString.fromInterned("BUILD_MAP"), Py.newInteger(1), PyString.fromInterned("COMPARE_OP"), Py.newInteger(-1), PyString.fromInterned("STORE_FAST"), Py.newInteger(-1), PyString.fromInterned("IMPORT_STAR"), Py.newInteger(-1), PyString.fromInterned("IMPORT_NAME"), Py.newInteger(-1), PyString.fromInterned("IMPORT_FROM"), Py.newInteger(1), PyString.fromInterned("LOAD_ATTR"), Py.newInteger(0), PyString.fromInterned("SETUP_EXCEPT"), Py.newInteger(3), PyString.fromInterned("SETUP_FINALLY"), Py.newInteger(3), PyString.fromInterned("FOR_ITER"), Py.newInteger(1), PyString.fromInterned("WITH_CLEANUP"), Py.newInteger(-1)});
      var1.setlocal("effect", var5);
      var3 = null;
      var1.setline(728);
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("BINARY_"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("LOAD_"), Py.newInteger(1)})});
      var1.setlocal("patterns", var6);
      var3 = null;
      var1.setline(733);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, UNPACK_SEQUENCE$68, (PyObject)null);
      var1.setlocal("UNPACK_SEQUENCE", var4);
      var3 = null;
      var1.setline(735);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, BUILD_TUPLE$69, (PyObject)null);
      var1.setlocal("BUILD_TUPLE", var4);
      var3 = null;
      var1.setline(737);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, BUILD_LIST$70, (PyObject)null);
      var1.setlocal("BUILD_LIST", var4);
      var3 = null;
      var1.setline(739);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, BUILD_SET$71, (PyObject)null);
      var1.setlocal("BUILD_SET", var4);
      var3 = null;
      var1.setline(741);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, CALL_FUNCTION$72, (PyObject)null);
      var1.setlocal("CALL_FUNCTION", var4);
      var3 = null;
      var1.setline(744);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, CALL_FUNCTION_VAR$73, (PyObject)null);
      var1.setlocal("CALL_FUNCTION_VAR", var4);
      var3 = null;
      var1.setline(746);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, CALL_FUNCTION_KW$74, (PyObject)null);
      var1.setlocal("CALL_FUNCTION_KW", var4);
      var3 = null;
      var1.setline(748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, CALL_FUNCTION_VAR_KW$75, (PyObject)null);
      var1.setlocal("CALL_FUNCTION_VAR_KW", var4);
      var3 = null;
      var1.setline(750);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, MAKE_FUNCTION$76, (PyObject)null);
      var1.setlocal("MAKE_FUNCTION", var4);
      var3 = null;
      var1.setline(752);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, MAKE_CLOSURE$77, (PyObject)null);
      var1.setlocal("MAKE_CLOSURE", var4);
      var3 = null;
      var1.setline(755);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, BUILD_SLICE$78, (PyObject)null);
      var1.setlocal("BUILD_SLICE", var4);
      var3 = null;
      var1.setline(760);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, DUP_TOPX$79, (PyObject)null);
      var1.setlocal("DUP_TOPX", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject findDepth$67(PyFrame var1, ThreadState var2) {
      var1.setline(658);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(659);
      var3 = Py.newInteger(0);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(660);
      PyObject var9 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(660);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(683);
            var9 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(5, var4);
         var1.setline(661);
         PyObject var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(662);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(663);
            Py.printComma(var1.getlocal(5));
         }

         var1.setline(664);
         var5 = var1.getlocal(0).__getattr__("effect").__getattr__("get").__call__(var2, var1.getlocal(6), var1.getglobal("None"));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(665);
         var5 = var1.getlocal(7);
         PyObject var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(666);
            var5 = var1.getlocal(3)._add(var1.getlocal(7));
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(669);
            var5 = var1.getlocal(0).__getattr__("patterns").__iter__();

            while(true) {
               var1.setline(669);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               PyObject[] var7 = Py.unpackSequence(var6, 2);
               PyObject var8 = var7[0];
               var1.setlocal(8, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(9, var8);
               var8 = null;
               var1.setline(670);
               PyObject var10 = var1.getlocal(6).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(8)), (PyObject)null);
               var10000 = var10._eq(var1.getlocal(8));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(671);
                  var10 = var1.getlocal(9);
                  var1.setlocal(7, var10);
                  var7 = null;
                  var1.setline(672);
                  var10 = var1.getlocal(3)._add(var1.getlocal(7));
                  var1.setlocal(3, var10);
                  var7 = null;
                  break;
               }
            }

            var1.setline(675);
            var5 = var1.getlocal(7);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(676);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(6), var1.getglobal("None"));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(677);
               var5 = var1.getlocal(10);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(678);
                  var5 = var1.getlocal(3)._add(var1.getlocal(10).__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1))));
                  var1.setlocal(3, var5);
                  var5 = null;
               }
            }
         }

         var1.setline(679);
         var5 = var1.getlocal(3);
         var10000 = var5._gt(var1.getlocal(4));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(680);
            var5 = var1.getlocal(3);
            var1.setlocal(4, var5);
            var5 = null;
         }

         var1.setline(681);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(682);
            Py.printComma(var1.getlocal(3));
            Py.println(var1.getlocal(4));
         }
      }
   }

   public PyObject UNPACK_SEQUENCE$68(PyFrame var1, ThreadState var2) {
      var1.setline(734);
      PyObject var3 = var1.getlocal(1)._sub(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BUILD_TUPLE$69(PyFrame var1, ThreadState var2) {
      var1.setline(736);
      PyObject var3 = var1.getlocal(1).__neg__()._add(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BUILD_LIST$70(PyFrame var1, ThreadState var2) {
      var1.setline(738);
      PyObject var3 = var1.getlocal(1).__neg__()._add(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BUILD_SET$71(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyObject var3 = var1.getlocal(1).__neg__()._add(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CALL_FUNCTION$72(PyFrame var1, ThreadState var2) {
      var1.setline(742);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(256));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(743);
      var3 = var1.getlocal(3)._add(var1.getlocal(2)._mul(Py.newInteger(2))).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CALL_FUNCTION_VAR$73(PyFrame var1, ThreadState var2) {
      var1.setline(745);
      PyObject var3 = var1.getlocal(0).__getattr__("CALL_FUNCTION").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CALL_FUNCTION_KW$74(PyFrame var1, ThreadState var2) {
      var1.setline(747);
      PyObject var3 = var1.getlocal(0).__getattr__("CALL_FUNCTION").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CALL_FUNCTION_VAR_KW$75(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      PyObject var3 = var1.getlocal(0).__getattr__("CALL_FUNCTION").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MAKE_FUNCTION$76(PyFrame var1, ThreadState var2) {
      var1.setline(751);
      PyObject var3 = var1.getlocal(1).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MAKE_CLOSURE$77(PyFrame var1, ThreadState var2) {
      var1.setline(754);
      PyObject var3 = var1.getlocal(1).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BUILD_SLICE$78(PyFrame var1, ThreadState var2) {
      var1.setline(756);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(757);
         var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(758);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(Py.newInteger(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(759);
            var5 = Py.newInteger(-2);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject DUP_TOPX$79(PyFrame var1, ThreadState var2) {
      var1.setline(761);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public pyassem$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FlowGraph$1 = Py.newCode(0, var2, var1, "FlowGraph", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block"};
      startBlock$3 = Py.newCode(2, var2, var1, "startBlock", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block"};
      nextBlock$4 = Py.newCode(2, var2, var1, "nextBlock", 29, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      newBlock$5 = Py.newCode(1, var2, var1, "newBlock", 53, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startExitBlock$6 = Py.newCode(1, var2, var1, "startExitBlock", 58, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _enable_debug$7 = Py.newCode(1, var2, var1, "_enable_debug", 63, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _disable_debug$8 = Py.newCode(1, var2, var1, "_disable_debug", 66, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inst"};
      emit$9 = Py.newCode(2, var2, var1, "emit", 69, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "order"};
      getBlocksInOrder$10 = Py.newCode(1, var2, var1, "getBlocksInOrder", 76, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getBlocks$11 = Py.newCode(1, var2, var1, "getBlocks", 84, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getRoot$12 = Py.newCode(1, var2, var1, "getRoot", 87, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "b"};
      getContainedGraphs$13 = Py.newCode(1, var2, var1, "getContainedGraphs", 91, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"start_block", "exit_block", "order", "todo", "b", "c", "find_next", "dominators", "remaining"};
      String[] var10001 = var2;
      pyassem$py var10007 = self;
      var2 = new String[]{"dominators", "remaining"};
      order_blocks$14 = Py.newCode(2, var10001, var1, "order_blocks", 98, false, false, var10007, 14, var2, (String[])null, 2, 4097);
      var2 = new String[]{"b", "c"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"remaining", "dominators"};
      find_next$15 = Py.newCode(0, var10001, var1, "find_next", 140, false, false, var10007, 15, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Block$16 = Py.newCode(0, var2, var1, "Block", 165, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "label"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 168, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$18 = Py.newCode(1, var2, var1, "__repr__", 177, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "insts"};
      __str__$19 = Py.newCode(1, var2, var1, "__str__", 183, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inst", "op"};
      emit$20 = Py.newCode(2, var2, var1, "emit", 188, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getInstructions$21 = Py.newCode(1, var2, var1, "getInstructions", 192, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block"};
      addOutEdge$22 = Py.newCode(2, var2, var1, "addOutEdge", 195, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block"};
      addNext$23 = Py.newCode(2, var2, var1, "addNext", 198, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "op", "arg"};
      has_unconditional_transfer$24 = Py.newCode(1, var2, var1, "has_unconditional_transfer", 208, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_children$25 = Py.newCode(1, var2, var1, "get_children", 218, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "followers", "inst"};
      get_followers$26 = Py.newCode(1, var2, var1, "get_followers", 221, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "contained", "inst", "op"};
      getContainedGraphs$27 = Py.newCode(1, var2, var1, "getContainedGraphs", 231, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PyFlowGraph$28 = Py.newCode(0, var2, var1, "PyFlowGraph", 254, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "filename", "args", "optimized", "klass", "i", "var"};
      __init__$29 = Py.newCode(6, var2, var1, "__init__", 257, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doc"};
      setDocstring$30 = Py.newCode(2, var2, var1, "setDocstring", 287, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      setFlag$31 = Py.newCode(2, var2, var1, "setFlag", 290, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      checkFlag$32 = Py.newCode(2, var2, var1, "checkFlag", 295, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names"};
      setFreeVars$33 = Py.newCode(2, var2, var1, "setFreeVars", 299, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names"};
      setCellVars$34 = Py.newCode(2, var2, var1, "setCellVars", 302, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getCode$35 = Py.newCode(1, var2, var1, "getCode", 305, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "io", "save", "pc", "t", "opname"};
      dump$36 = Py.newCode(2, var2, var1, "dump", 317, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exit", "b", "max_depth", "seen", "depth"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"max_depth", "self", "seen", "depth"};
      computeStackDepth$37 = Py.newCode(1, var10001, var1, "computeStackDepth", 335, false, false, var10007, 37, var2, (String[])null, 3, 4097);
      var2 = new String[]{"b", "d", "children", "_[356_28]", "c"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"seen", "depth", "max_depth", "self"};
      max_depth$38 = Py.newCode(2, var10001, var1, "max_depth", 349, false, false, var10007, 38, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "insts", "pc", "begin", "end", "b", "inst", "i", "opname", "oparg", "offset"};
      flattenGraph$39 = Py.newCode(1, var2, var1, "flattenGraph", 365, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "t", "opname", "oparg", "conv"};
      convertArgs$40 = Py.newCode(1, var2, var1, "convertArgs", 405, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cells", "name", "_[425_25]"};
      sort_cellvars$41 = Py.newCode(1, var2, var1, "sort_cellvars", 419, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "list", "t", "i", "end"};
      _lookupName$42 = Py.newCode(3, var2, var1, "_lookupName", 432, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_LOAD_CONST$43 = Py.newCode(2, var2, var1, "_convert_LOAD_CONST", 450, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_LOAD_FAST$44 = Py.newCode(2, var2, var1, "_convert_LOAD_FAST", 455, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_LOAD_NAME$45 = Py.newCode(2, var2, var1, "_convert_LOAD_NAME", 461, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_NAME$46 = Py.newCode(2, var2, var1, "_convert_NAME", 466, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_DEREF$47 = Py.newCode(2, var2, var1, "_convert_DEREF", 481, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_LOAD_CLOSURE$48 = Py.newCode(2, var2, var1, "_convert_LOAD_CLOSURE", 488, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _convert_COMPARE_OP$49 = Py.newCode(2, var2, var1, "_convert_COMPARE_OP", 493, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lnotab", "t", "opname", "oparg", "hi", "lo"};
      makeByteCode$50 = Py.newCode(1, var2, var1, "makeByteCode", 504, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nlocals", "argcount"};
      newCodeObject$51 = Py.newCode(1, var2, var1, "newCodeObject", 530, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "elt"};
      getConsts$52 = Py.newCode(1, var2, var1, "getConsts", 546, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opname"};
      isJump$53 = Py.newCode(1, var2, var1, "isJump", 559, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TupleArg$54 = Py.newCode(0, var2, var1, "TupleArg", 563, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "count", "names"};
      __init__$55 = Py.newCode(3, var2, var1, "__init__", 565, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$56 = Py.newCode(1, var2, var1, "__repr__", 568, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getName$57 = Py.newCode(1, var2, var1, "getName", 570, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "argcount", "arg", "numNames"};
      getArgCount$58 = Py.newCode(1, var2, var1, "getArgCount", 573, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val"};
      twobyte$59 = Py.newCode(1, var2, var1, "twobyte", 582, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LineAddrTable$60 = Py.newCode(0, var2, var1, "LineAddrTable", 587, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$61 = Py.newCode(1, var2, var1, "__init__", 602, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "arg"};
      addCode$62 = Py.newCode(2, var2, var1, "addCode", 610, true, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lineno", "addr", "line", "push"};
      nextLine$63 = Py.newCode(2, var2, var1, "nextLine", 615, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getCode$64 = Py.newCode(1, var2, var1, "getCode", 647, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getTable$65 = Py.newCode(1, var2, var1, "getTable", 650, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StackDepthTracker$66 = Py.newCode(0, var2, var1, "StackDepthTracker", 653, false, false, self, 66, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "insts", "debug", "depth", "maxDepth", "i", "opname", "delta", "pat", "pat_delta", "meth"};
      findDepth$67 = Py.newCode(3, var2, var1, "findDepth", 657, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count"};
      UNPACK_SEQUENCE$68 = Py.newCode(2, var2, var1, "UNPACK_SEQUENCE", 733, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count"};
      BUILD_TUPLE$69 = Py.newCode(2, var2, var1, "BUILD_TUPLE", 735, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count"};
      BUILD_LIST$70 = Py.newCode(2, var2, var1, "BUILD_LIST", 737, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count"};
      BUILD_SET$71 = Py.newCode(2, var2, var1, "BUILD_SET", 739, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc", "hi", "lo"};
      CALL_FUNCTION$72 = Py.newCode(2, var2, var1, "CALL_FUNCTION", 741, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      CALL_FUNCTION_VAR$73 = Py.newCode(2, var2, var1, "CALL_FUNCTION_VAR", 744, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      CALL_FUNCTION_KW$74 = Py.newCode(2, var2, var1, "CALL_FUNCTION_KW", 746, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      CALL_FUNCTION_VAR_KW$75 = Py.newCode(2, var2, var1, "CALL_FUNCTION_VAR_KW", 748, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      MAKE_FUNCTION$76 = Py.newCode(2, var2, var1, "MAKE_FUNCTION", 750, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      MAKE_CLOSURE$77 = Py.newCode(2, var2, var1, "MAKE_CLOSURE", 752, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      BUILD_SLICE$78 = Py.newCode(2, var2, var1, "BUILD_SLICE", 755, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argc"};
      DUP_TOPX$79 = Py.newCode(2, var2, var1, "DUP_TOPX", 760, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pyassem$py("compiler/pyassem$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pyassem$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FlowGraph$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.startBlock$3(var2, var3);
         case 4:
            return this.nextBlock$4(var2, var3);
         case 5:
            return this.newBlock$5(var2, var3);
         case 6:
            return this.startExitBlock$6(var2, var3);
         case 7:
            return this._enable_debug$7(var2, var3);
         case 8:
            return this._disable_debug$8(var2, var3);
         case 9:
            return this.emit$9(var2, var3);
         case 10:
            return this.getBlocksInOrder$10(var2, var3);
         case 11:
            return this.getBlocks$11(var2, var3);
         case 12:
            return this.getRoot$12(var2, var3);
         case 13:
            return this.getContainedGraphs$13(var2, var3);
         case 14:
            return this.order_blocks$14(var2, var3);
         case 15:
            return this.find_next$15(var2, var3);
         case 16:
            return this.Block$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.__repr__$18(var2, var3);
         case 19:
            return this.__str__$19(var2, var3);
         case 20:
            return this.emit$20(var2, var3);
         case 21:
            return this.getInstructions$21(var2, var3);
         case 22:
            return this.addOutEdge$22(var2, var3);
         case 23:
            return this.addNext$23(var2, var3);
         case 24:
            return this.has_unconditional_transfer$24(var2, var3);
         case 25:
            return this.get_children$25(var2, var3);
         case 26:
            return this.get_followers$26(var2, var3);
         case 27:
            return this.getContainedGraphs$27(var2, var3);
         case 28:
            return this.PyFlowGraph$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.setDocstring$30(var2, var3);
         case 31:
            return this.setFlag$31(var2, var3);
         case 32:
            return this.checkFlag$32(var2, var3);
         case 33:
            return this.setFreeVars$33(var2, var3);
         case 34:
            return this.setCellVars$34(var2, var3);
         case 35:
            return this.getCode$35(var2, var3);
         case 36:
            return this.dump$36(var2, var3);
         case 37:
            return this.computeStackDepth$37(var2, var3);
         case 38:
            return this.max_depth$38(var2, var3);
         case 39:
            return this.flattenGraph$39(var2, var3);
         case 40:
            return this.convertArgs$40(var2, var3);
         case 41:
            return this.sort_cellvars$41(var2, var3);
         case 42:
            return this._lookupName$42(var2, var3);
         case 43:
            return this._convert_LOAD_CONST$43(var2, var3);
         case 44:
            return this._convert_LOAD_FAST$44(var2, var3);
         case 45:
            return this._convert_LOAD_NAME$45(var2, var3);
         case 46:
            return this._convert_NAME$46(var2, var3);
         case 47:
            return this._convert_DEREF$47(var2, var3);
         case 48:
            return this._convert_LOAD_CLOSURE$48(var2, var3);
         case 49:
            return this._convert_COMPARE_OP$49(var2, var3);
         case 50:
            return this.makeByteCode$50(var2, var3);
         case 51:
            return this.newCodeObject$51(var2, var3);
         case 52:
            return this.getConsts$52(var2, var3);
         case 53:
            return this.isJump$53(var2, var3);
         case 54:
            return this.TupleArg$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this.__repr__$56(var2, var3);
         case 57:
            return this.getName$57(var2, var3);
         case 58:
            return this.getArgCount$58(var2, var3);
         case 59:
            return this.twobyte$59(var2, var3);
         case 60:
            return this.LineAddrTable$60(var2, var3);
         case 61:
            return this.__init__$61(var2, var3);
         case 62:
            return this.addCode$62(var2, var3);
         case 63:
            return this.nextLine$63(var2, var3);
         case 64:
            return this.getCode$64(var2, var3);
         case 65:
            return this.getTable$65(var2, var3);
         case 66:
            return this.StackDepthTracker$66(var2, var3);
         case 67:
            return this.findDepth$67(var2, var3);
         case 68:
            return this.UNPACK_SEQUENCE$68(var2, var3);
         case 69:
            return this.BUILD_TUPLE$69(var2, var3);
         case 70:
            return this.BUILD_LIST$70(var2, var3);
         case 71:
            return this.BUILD_SET$71(var2, var3);
         case 72:
            return this.CALL_FUNCTION$72(var2, var3);
         case 73:
            return this.CALL_FUNCTION_VAR$73(var2, var3);
         case 74:
            return this.CALL_FUNCTION_KW$74(var2, var3);
         case 75:
            return this.CALL_FUNCTION_VAR_KW$75(var2, var3);
         case 76:
            return this.MAKE_FUNCTION$76(var2, var3);
         case 77:
            return this.MAKE_CLOSURE$77(var2, var3);
         case 78:
            return this.BUILD_SLICE$78(var2, var3);
         case 79:
            return this.DUP_TOPX$79(var2, var3);
         default:
            return null;
      }
   }
}

package xml.dom;

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
@Filename("xml/dom/minicompat.py")
public class minicompat$py extends PyFunctionTable implements PyRunnable {
   static minicompat$py self;
   static final PyCode f$0;
   static final PyCode NodeList$1;
   static final PyCode item$2;
   static final PyCode _get_length$3;
   static final PyCode _set_length$4;
   static final PyCode __setstate__$5;
   static final PyCode EmptyNodeList$6;
   static final PyCode __add__$7;
   static final PyCode __radd__$8;
   static final PyCode item$9;
   static final PyCode _get_length$10;
   static final PyCode _set_length$11;
   static final PyCode defproperty$12;
   static final PyCode set$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Python version compatibility support for minidom."));
      var1.setline(1);
      PyString.fromInterned("Python version compatibility support for minidom.");
      var1.setline(39);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("NodeList"), PyString.fromInterned("EmptyNodeList"), PyString.fromInterned("StringTypes"), PyString.fromInterned("defproperty")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(41);
      PyObject var6 = imp.importOne("xml.dom", var1, -1);
      var1.setlocal("xml", var6);
      var3 = null;

      PyTuple var4;
      label19: {
         try {
            var1.setline(44);
            var1.getname("unicode");
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getname("NameError"))) {
               var1.setline(46);
               var4 = new PyTuple(new PyObject[]{var1.getname("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))});
               var1.setlocal("StringTypes", var4);
               var4 = null;
               break label19;
            }

            throw var7;
         }

         var1.setline(48);
         var4 = new PyTuple(new PyObject[]{var1.getname("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), var1.getname("type").__call__(var2, var1.getname("unicode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")))});
         var1.setlocal("StringTypes", var4);
         var4 = null;
      }

      var1.setline(51);
      PyObject[] var8 = new PyObject[]{var1.getname("list")};
      PyObject var9 = Py.makeClass("NodeList", var8, NodeList$1);
      var1.setlocal("NodeList", var9);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(75);
      var8 = new PyObject[]{var1.getname("tuple")};
      var9 = Py.makeClass("EmptyNodeList", var8, EmptyNodeList$6);
      var1.setlocal("EmptyNodeList", var9);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(102);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, defproperty$12, (PyObject)null);
      var1.setlocal("defproperty", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NodeList$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(52);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, item$2, (PyObject)null);
      var1.setlocal("item", var6);
      var3 = null;
      var1.setline(58);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_length$3, (PyObject)null);
      var1.setlocal("_get_length", var6);
      var3 = null;
      var1.setline(61);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _set_length$4, (PyObject)null);
      var1.setlocal("_set_length", var6);
      var3 = null;
      var1.setline(65);
      PyObject var10000 = var1.getname("property");
      var5 = new PyObject[]{var1.getname("_get_length"), var1.getname("_set_length"), PyString.fromInterned("The number of nodes in the NodeList.")};
      String[] var4 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal("length", var7);
      var3 = null;
      var1.setline(69);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __setstate__$5, (PyObject)null);
      var1.setlocal("__setstate__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject item$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
      }

      var3 = null;
      if (var4.__nonzero__()) {
         var1.setline(56);
         var5 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_length$3(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_length$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempt to modify read-only attribute 'length'")));
   }

   public PyObject __setstate__$5(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(71);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(72);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EmptyNodeList$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(78);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __add__$7, (PyObject)null);
      var1.setlocal("__add__", var6);
      var3 = null;
      var1.setline(83);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __radd__$8, (PyObject)null);
      var1.setlocal("__radd__", var6);
      var3 = null;
      var1.setline(88);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, item$9, (PyObject)null);
      var1.setlocal("item", var6);
      var3 = null;
      var1.setline(91);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_length$10, (PyObject)null);
      var1.setlocal("_get_length", var6);
      var3 = null;
      var1.setline(94);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _set_length$11, (PyObject)null);
      var1.setlocal("_set_length", var6);
      var3 = null;
      var1.setline(98);
      PyObject var10000 = var1.getname("property");
      var5 = new PyObject[]{var1.getname("_get_length"), var1.getname("_set_length"), PyString.fromInterned("The number of nodes in the NodeList.")};
      String[] var4 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal("length", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __add__$7(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getglobal("NodeList").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(80);
      var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.setline(81);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __radd__$8(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getglobal("NodeList").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(85);
      var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.setline(86);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject item$9(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_length$10(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_length$11(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempt to modify read-only attribute 'length'")));
   }

   public PyObject defproperty$12(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_get_")._add(var1.getlocal(1))).__getattr__("im_func");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(104);
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, set$13, (PyObject)null);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(107);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_set_")._add(var1.getlocal(1))).__not__().__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("expected not to find _set_")._add(var1.getlocal(1)));
      } else {
         var1.setline(109);
         PyObject var10000 = var1.getglobal("property");
         var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(2)};
         String[] var4 = new String[]{"doc"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(110);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(5));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject set$13(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__(var2, PyString.fromInterned("attempt to modify read-only attribute ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(2)))));
   }

   public minicompat$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NodeList$1 = Py.newCode(0, var2, var1, "NodeList", 51, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "index"};
      item$2 = Py.newCode(2, var2, var1, "item", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_length$3 = Py.newCode(1, var2, var1, "_get_length", 58, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _set_length$4 = Py.newCode(2, var2, var1, "_set_length", 61, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$5 = Py.newCode(2, var2, var1, "__setstate__", 69, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EmptyNodeList$6 = Py.newCode(0, var2, var1, "EmptyNodeList", 75, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "other", "NL"};
      __add__$7 = Py.newCode(2, var2, var1, "__add__", 78, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "NL"};
      __radd__$8 = Py.newCode(2, var2, var1, "__radd__", 83, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      item$9 = Py.newCode(2, var2, var1, "item", 88, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_length$10 = Py.newCode(1, var2, var1, "_get_length", 91, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _set_length$11 = Py.newCode(2, var2, var1, "_set_length", 94, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"klass", "name", "doc", "get", "set", "prop"};
      defproperty$12 = Py.newCode(3, var2, var1, "defproperty", 102, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "name"};
      set$13 = Py.newCode(3, var2, var1, "set", 104, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new minicompat$py("xml/dom/minicompat$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(minicompat$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NodeList$1(var2, var3);
         case 2:
            return this.item$2(var2, var3);
         case 3:
            return this._get_length$3(var2, var3);
         case 4:
            return this._set_length$4(var2, var3);
         case 5:
            return this.__setstate__$5(var2, var3);
         case 6:
            return this.EmptyNodeList$6(var2, var3);
         case 7:
            return this.__add__$7(var2, var3);
         case 8:
            return this.__radd__$8(var2, var3);
         case 9:
            return this.item$9(var2, var3);
         case 10:
            return this._get_length$10(var2, var3);
         case 11:
            return this._set_length$11(var2, var3);
         case 12:
            return this.defproperty$12(var2, var3);
         case 13:
            return this.set$13(var2, var3);
         default:
            return null;
      }
   }
}

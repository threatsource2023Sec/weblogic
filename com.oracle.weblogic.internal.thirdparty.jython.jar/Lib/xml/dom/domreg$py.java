package xml.dom;

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
@Filename("xml/dom/domreg.py")
public class domreg$py extends PyFunctionTable implements PyRunnable {
   static domreg$py self;
   static final PyCode f$0;
   static final PyCode registerDOMImplementation$1;
   static final PyCode _good_enough$2;
   static final PyCode getDOMImplementation$3;
   static final PyCode _parse_feature_string$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Registration facilities for DOM. This module should not be used\ndirectly. Instead, the functions getDOMImplementation and\nregisterDOMImplementation should be imported from xml.dom."));
      var1.setline(3);
      PyString.fromInterned("Registration facilities for DOM. This module should not be used\ndirectly. Instead, the functions getDOMImplementation and\nregisterDOMImplementation should be imported from xml.dom.");
      var1.setline(5);
      imp.importAll("xml.dom.minicompat", var1, -1);
      var1.setline(11);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("minidom"), PyString.fromInterned("xml.dom.minidom"), PyString.fromInterned("4DOM"), PyString.fromInterned("xml.dom.DOMImplementation")});
      var1.setlocal("well_known_implementations", var3);
      var3 = null;
      var1.setline(19);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("registered", var3);
      var3 = null;
      var1.setline(21);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, registerDOMImplementation$1, PyString.fromInterned("registerDOMImplementation(name, factory)\n\n    Register the factory function with the name. The factory function\n    should return an object which implements the DOMImplementation\n    interface. The factory function can either return the same object,\n    or a new one (e.g. if that implementation supports some\n    customization)."));
      var1.setlocal("registerDOMImplementation", var5);
      var3 = null;
      var1.setline(32);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _good_enough$2, PyString.fromInterned("_good_enough(dom, features) -> Return 1 if the dom offers the features"));
      var1.setlocal("_good_enough", var5);
      var3 = null;
      var1.setline(39);
      var4 = new PyObject[]{var1.getname("None"), new PyTuple(Py.EmptyObjects)};
      var5 = new PyFunction(var1.f_globals, var4, getDOMImplementation$3, PyString.fromInterned("getDOMImplementation(name = None, features = ()) -> DOM implementation.\n\n    Return a suitable DOM implementation. The name is either\n    well-known, the module name of a DOM implementation, or None. If\n    it is not None, imports the corresponding module and returns\n    DOMImplementation object if the import succeeds.\n\n    If name is not given, consider the available implementations to\n    find one with the required feature set. If no implementation can\n    be found, raise an ImportError. The features list must be a sequence\n    of (feature, version) pairs which are passed to hasFeature."));
      var1.setlocal("getDOMImplementation", var5);
      var3 = null;
      var1.setline(82);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _parse_feature_string$4, (PyObject)null);
      var1.setlocal("_parse_feature_string", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject registerDOMImplementation$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("registerDOMImplementation(name, factory)\n\n    Register the factory function with the name. The factory function\n    should return an object which implements the DOMImplementation\n    interface. The factory function can either return the same object,\n    or a new one (e.g. if that implementation supports some\n    customization).");
      var1.setline(30);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("registered").__setitem__(var1.getlocal(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _good_enough$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("_good_enough(dom, features) -> Return 1 if the dom offers the features");
      var1.setline(34);
      PyObject var3 = var1.getlocal(1).__iter__();

      PyInteger var7;
      do {
         var1.setline(34);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(37);
            var7 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(35);
      } while(!var1.getlocal(0).__getattr__("hasFeature").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__not__().__nonzero__());

      var1.setline(36);
      var7 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject getDOMImplementation$3(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyString.fromInterned("getDOMImplementation(name = None, features = ()) -> DOM implementation.\n\n    Return a suitable DOM implementation. The name is either\n    well-known, the module name of a DOM implementation, or None. If\n    it is not None, imports the corresponding module and returns\n    DOMImplementation object if the import succeeds.\n\n    If name is not given, consider the available implementations to\n    find one with the required feature set. If no implementation can\n    be found, raise an ImportError. The features list must be a sequence\n    of (feature, version) pairs which are passed to hasFeature.");
      var1.setline(52);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("well_known_implementations").__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(55);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(56);
         var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(4), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyList(new PyObject[]{PyString.fromInterned("getDOMImplementation")}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(57);
         var3 = var1.getlocal(4).__getattr__("getDOMImplementation").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(58);
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(59);
            var3 = var1.getglobal("registered").__getitem__(var1.getlocal(0)).__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(60);
            PyString var4 = PyString.fromInterned("PYTHON_DOM");
            PyObject var10000 = var4._in(var1.getlocal(2).__getattr__("environ"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(61);
               var10000 = var1.getglobal("getDOMImplementation");
               PyObject[] var11 = new PyObject[]{var1.getlocal(2).__getattr__("environ").__getitem__(PyString.fromInterned("PYTHON_DOM"))};
               String[] var10 = new String[]{"name"};
               var10000 = var10000.__call__(var2, var11, var10);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(65);
               PyObject var9;
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringTypes")).__nonzero__()) {
                  var1.setline(66);
                  var9 = var1.getglobal("_parse_feature_string").__call__(var2, var1.getlocal(1));
                  var1.setlocal(1, var9);
                  var4 = null;
               }

               var1.setline(67);
               var9 = var1.getglobal("registered").__getattr__("values").__call__(var2).__iter__();

               do {
                  var1.setline(67);
                  PyObject var5 = var9.__iternext__();
                  PyObject var6;
                  if (var5 == null) {
                     var1.setline(72);
                     var9 = var1.getglobal("well_known_implementations").__getattr__("keys").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(72);
                        var5 = var9.__iternext__();
                        if (var5 == null) {
                           var1.setline(80);
                           throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("no suitable DOM implementation found"));
                        }

                        var1.setlocal(3, var5);

                        try {
                           var1.setline(74);
                           var10000 = var1.getglobal("getDOMImplementation");
                           PyObject[] var13 = new PyObject[]{var1.getlocal(3)};
                           String[] var7 = new String[]{"name"};
                           var10000 = var10000.__call__(var2, var13, var7);
                           var6 = null;
                           var6 = var10000;
                           var1.setlocal(5, var6);
                           var6 = null;
                        } catch (Throwable var8) {
                           PyException var12 = Py.setException(var8, var1);
                           if (var12.match(var1.getglobal("StandardError"))) {
                              continue;
                           }

                           throw var12;
                        }

                        var1.setline(77);
                        if (var1.getglobal("_good_enough").__call__(var2, var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                           var1.setline(78);
                           var3 = var1.getlocal(5);
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }
                  }

                  var1.setlocal(3, var5);
                  var1.setline(68);
                  var6 = var1.getlocal(3).__call__(var2);
                  var1.setlocal(5, var6);
                  var6 = null;
                  var1.setline(69);
               } while(!var1.getglobal("_good_enough").__call__(var2, var1.getlocal(5), var1.getlocal(1)).__nonzero__());

               var1.setline(70);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _parse_feature_string$4(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(84);
      PyObject var4 = var1.getlocal(0).__getattr__("split").__call__(var2);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(85);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(86);
      var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var4);
      var3 = null;

      while(true) {
         var1.setline(87);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._lt(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(99);
            var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(88);
         var4 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(89);
         var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var4._in(PyString.fromInterned("0123456789"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(90);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad feature name: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)})));
         }

         var1.setline(91);
         var4 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(92);
         var4 = var1.getglobal("None");
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(93);
         var4 = var1.getlocal(3);
         var10000 = var4._lt(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(94);
            var4 = var1.getlocal(2).__getitem__(var1.getlocal(3));
            var1.setlocal(7, var4);
            var3 = null;
            var1.setline(95);
            var4 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var4._in(PyString.fromInterned("0123456789"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(96);
               var4 = var1.getlocal(3)._add(Py.newInteger(1));
               var1.setlocal(3, var4);
               var3 = null;
               var1.setline(97);
               var4 = var1.getlocal(7);
               var1.setlocal(6, var4);
               var3 = null;
            }
         }

         var1.setline(98);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      }
   }

   public domreg$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "factory"};
      registerDOMImplementation$1 = Py.newCode(2, var2, var1, "registerDOMImplementation", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dom", "features", "f", "v"};
      _good_enough$2 = Py.newCode(2, var2, var1, "_good_enough", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "features", "os", "creator", "mod", "dom"};
      getDOMImplementation$3 = Py.newCode(2, var2, var1, "getDOMImplementation", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "features", "parts", "i", "length", "feature", "version", "v"};
      _parse_feature_string$4 = Py.newCode(1, var2, var1, "_parse_feature_string", 82, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new domreg$py("xml/dom/domreg$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(domreg$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.registerDOMImplementation$1(var2, var3);
         case 2:
            return this._good_enough$2(var2, var3);
         case 3:
            return this.getDOMImplementation$3(var2, var3);
         case 4:
            return this._parse_feature_string$4(var2, var3);
         default:
            return null;
      }
   }
}

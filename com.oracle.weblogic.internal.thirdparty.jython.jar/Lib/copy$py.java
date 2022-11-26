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
@Filename("copy.py")
public class copy$py extends PyFunctionTable implements PyRunnable {
   static copy$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode copy$2;
   static final PyCode _copy_immutable$3;
   static final PyCode _copy_with_constructor$4;
   static final PyCode _copy_with_copy_method$5;
   static final PyCode _copy_inst$6;
   static final PyCode deepcopy$7;
   static final PyCode _deepcopy_atomic$8;
   static final PyCode _deepcopy_list$9;
   static final PyCode _deepcopy_tuple$10;
   static final PyCode _deepcopy_dict$11;
   static final PyCode _deepcopy_method$12;
   static final PyCode _keep_alive$13;
   static final PyCode _deepcopy_inst$14;
   static final PyCode _reconstruct$15;
   static final PyCode _EmptyClass$16;
   static final PyCode _test$17;
   static final PyCode C$18;
   static final PyCode __init__$19;
   static final PyCode __getstate__$20;
   static final PyCode __setstate__$21;
   static final PyCode __deepcopy__$22;
   static final PyCode odict$23;
   static final PyCode __init__$24;
   static final PyCode __setitem__$25;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generic (shallow and deep) copying operations.\n\nInterface summary:\n\n        import copy\n\n        x = copy.copy(y)        # make a shallow copy of y\n        x = copy.deepcopy(y)    # make a deep copy of y\n\nFor module specific errors, copy.Error is raised.\n\nThe difference between shallow and deep copying is only relevant for\ncompound objects (objects that contain other objects, like lists or\nclass instances).\n\n- A shallow copy constructs a new compound object and then (to the\n  extent possible) inserts *the same objects* into it that the\n  original contains.\n\n- A deep copy constructs a new compound object and then, recursively,\n  inserts *copies* into it of the objects found in the original.\n\nTwo problems often exist with deep copy operations that don't exist\nwith shallow copy operations:\n\n a) recursive objects (compound objects that, directly or indirectly,\n    contain a reference to themselves) may cause a recursive loop\n\n b) because deep copy copies *everything* it may copy too much, e.g.\n    administrative data structures that should be shared even between\n    copies\n\nPython's deep copy operation avoids these problems by:\n\n a) keeping a table of objects already copied during the current\n    copying pass\n\n b) letting user-defined classes override the copying operation or the\n    set of components copied\n\nThis version does not copy types like module, class, function, method,\nnor stack trace, stack frame, nor file, socket, window, nor array, nor\nany similar types.\n\nClasses can use the same interfaces to control copying that they use\nto control pickling: they can define methods called __getinitargs__(),\n__getstate__() and __setstate__().  See the documentation for module\n\"pickle\" for information on these methods.\n"));
      var1.setline(49);
      PyString.fromInterned("Generic (shallow and deep) copying operations.\n\nInterface summary:\n\n        import copy\n\n        x = copy.copy(y)        # make a shallow copy of y\n        x = copy.deepcopy(y)    # make a deep copy of y\n\nFor module specific errors, copy.Error is raised.\n\nThe difference between shallow and deep copying is only relevant for\ncompound objects (objects that contain other objects, like lists or\nclass instances).\n\n- A shallow copy constructs a new compound object and then (to the\n  extent possible) inserts *the same objects* into it that the\n  original contains.\n\n- A deep copy constructs a new compound object and then, recursively,\n  inserts *copies* into it of the objects found in the original.\n\nTwo problems often exist with deep copy operations that don't exist\nwith shallow copy operations:\n\n a) recursive objects (compound objects that, directly or indirectly,\n    contain a reference to themselves) may cause a recursive loop\n\n b) because deep copy copies *everything* it may copy too much, e.g.\n    administrative data structures that should be shared even between\n    copies\n\nPython's deep copy operation avoids these problems by:\n\n a) keeping a table of objects already copied during the current\n    copying pass\n\n b) letting user-defined classes override the copying operation or the\n    set of components copied\n\nThis version does not copy types like module, class, function, method,\nnor stack trace, stack frame, nor file, socket, window, nor array, nor\nany similar types.\n\nClasses can use the same interfaces to control copying that they use\nto control pickling: they can define methods called __getinitargs__(),\n__getstate__() and __setstate__().  See the documentation for module\n\"pickle\" for information on these methods.\n");
      var1.setline(51);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(52);
      var3 = imp.importOne("weakref", var1, -1);
      var1.setlocal("weakref", var3);
      var3 = null;
      var1.setline(53);
      String[] var10 = new String[]{"dispatch_table"};
      PyObject[] var11 = imp.importFrom("copy_reg", var10, var1, -1);
      PyObject var4 = var11[0];
      var1.setlocal("dispatch_table", var4);
      var4 = null;
      var1.setline(55);
      var11 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var11, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(57);
      var3 = var1.getname("Error");
      var1.setlocal("error", var3);
      var3 = null;

      PyException var12;
      try {
         var1.setline(60);
         var10 = new String[]{"PyStringMap"};
         var11 = imp.importFrom("org.python.core", var10, var1, -1);
         var4 = var11[0];
         var1.setlocal("PyStringMap", var4);
         var4 = null;
      } catch (Throwable var9) {
         var12 = Py.setException(var9, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(62);
         var4 = var1.getname("None");
         var1.setlocal("PyStringMap", var4);
         var4 = null;
      }

      var1.setline(64);
      PyList var13 = new PyList(new PyObject[]{PyString.fromInterned("Error"), PyString.fromInterned("copy"), PyString.fromInterned("deepcopy")});
      var1.setlocal("__all__", var13);
      var3 = null;
      var1.setline(66);
      var11 = Py.EmptyObjects;
      PyFunction var14 = new PyFunction(var1.f_globals, var11, copy$2, PyString.fromInterned("Shallow copy operation on arbitrary Python objects.\n\n    See the module's __doc__ string for more info.\n    "));
      var1.setlocal("copy", var14);
      var3 = null;
      var1.setline(99);
      PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_copy_dispatch", var15);
      var1.setlocal("d", var15);
      var1.setline(101);
      var11 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var11, _copy_immutable$3, (PyObject)null);
      var1.setlocal("_copy_immutable", var14);
      var3 = null;
      var1.setline(103);
      var3 = (new PyTuple(new PyObject[]{var1.getname("type").__call__(var2, var1.getname("None")), var1.getname("int"), var1.getname("long"), var1.getname("float"), var1.getname("bool"), var1.getname("str"), var1.getname("tuple"), var1.getname("frozenset"), var1.getname("type"), var1.getname("xrange"), var1.getname("types").__getattr__("ClassType"), var1.getname("types").__getattr__("BuiltinFunctionType"), var1.getname("type").__call__(var2, var1.getname("Ellipsis")), var1.getname("types").__getattr__("FunctionType"), var1.getname("weakref").__getattr__("ref")})).__iter__();

      while(true) {
         var1.setline(103);
         var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(108);
            var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("ComplexType"), PyString.fromInterned("UnicodeType"), PyString.fromInterned("CodeType")})).__iter__();

            while(true) {
               var1.setline(108);
               var4 = var3.__iternext__();
               PyObject var10000;
               if (var4 == null) {
                  var1.setline(113);
                  var11 = Py.EmptyObjects;
                  var14 = new PyFunction(var1.f_globals, var11, _copy_with_constructor$4, (PyObject)null);
                  var1.setlocal("_copy_with_constructor", var14);
                  var3 = null;
                  var1.setline(115);
                  var3 = (new PyTuple(new PyObject[]{var1.getname("list"), var1.getname("dict"), var1.getname("set")})).__iter__();

                  while(true) {
                     var1.setline(115);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(118);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _copy_with_copy_method$5, (PyObject)null);
                        var1.setlocal("_copy_with_copy_method", var14);
                        var3 = null;
                        var1.setline(120);
                        var3 = var1.getname("PyStringMap");
                        var10000 = var3._isnot(var1.getname("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(121);
                           var3 = var1.getname("_copy_with_copy_method");
                           var1.getname("d").__setitem__(var1.getname("PyStringMap"), var3);
                           var3 = null;
                        }

                        var1.setline(123);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _copy_inst$6, (PyObject)null);
                        var1.setlocal("_copy_inst", var14);
                        var3 = null;
                        var1.setline(141);
                        var3 = var1.getname("_copy_inst");
                        var1.getname("d").__setitem__(var1.getname("types").__getattr__("InstanceType"), var3);
                        var3 = null;
                        var1.setline(143);
                        var1.dellocal("d");
                        var1.setline(145);
                        var11 = new PyObject[]{var1.getname("None"), new PyList(Py.EmptyObjects)};
                        var14 = new PyFunction(var1.f_globals, var11, deepcopy$7, PyString.fromInterned("Deep copy operation on arbitrary Python objects.\n\n    See the module's __doc__ string for more info.\n    "));
                        var1.setlocal("deepcopy", var14);
                        var3 = null;
                        var1.setline(196);
                        var15 = new PyDictionary(Py.EmptyObjects);
                        var1.setlocal("_deepcopy_dispatch", var15);
                        var1.setlocal("d", var15);
                        var1.setline(198);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_atomic$8, (PyObject)null);
                        var1.setlocal("_deepcopy_atomic", var14);
                        var3 = null;
                        var1.setline(200);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("type").__call__(var2, var1.getname("None")), var3);
                        var3 = null;
                        var1.setline(201);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("type").__call__(var2, var1.getname("Ellipsis")), var3);
                        var3 = null;
                        var1.setline(202);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("int"), var3);
                        var3 = null;
                        var1.setline(203);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("long"), var3);
                        var3 = null;
                        var1.setline(204);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("float"), var3);
                        var3 = null;
                        var1.setline(205);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("bool"), var3);
                        var3 = null;

                        try {
                           var1.setline(207);
                           var3 = var1.getname("_deepcopy_atomic");
                           var1.getname("d").__setitem__(var1.getname("complex"), var3);
                           var3 = null;
                        } catch (Throwable var8) {
                           var12 = Py.setException(var8, var1);
                           if (!var12.match(var1.getname("NameError"))) {
                              throw var12;
                           }

                           var1.setline(209);
                        }

                        var1.setline(210);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("str"), var3);
                        var3 = null;

                        try {
                           var1.setline(212);
                           var3 = var1.getname("_deepcopy_atomic");
                           var1.getname("d").__setitem__(var1.getname("unicode"), var3);
                           var3 = null;
                        } catch (Throwable var7) {
                           var12 = Py.setException(var7, var1);
                           if (!var12.match(var1.getname("NameError"))) {
                              throw var12;
                           }

                           var1.setline(214);
                        }

                        try {
                           var1.setline(216);
                           var3 = var1.getname("_deepcopy_atomic");
                           var1.getname("d").__setitem__(var1.getname("types").__getattr__("CodeType"), var3);
                           var3 = null;
                        } catch (Throwable var6) {
                           var12 = Py.setException(var6, var1);
                           if (!var12.match(var1.getname("AttributeError"))) {
                              throw var12;
                           }

                           var1.setline(218);
                        }

                        var1.setline(219);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("type"), var3);
                        var3 = null;
                        var1.setline(220);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("xrange"), var3);
                        var3 = null;
                        var1.setline(221);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("types").__getattr__("ClassType"), var3);
                        var3 = null;
                        var1.setline(222);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("types").__getattr__("BuiltinFunctionType"), var3);
                        var3 = null;
                        var1.setline(223);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("types").__getattr__("FunctionType"), var3);
                        var3 = null;
                        var1.setline(224);
                        var3 = var1.getname("_deepcopy_atomic");
                        var1.getname("d").__setitem__(var1.getname("weakref").__getattr__("ref"), var3);
                        var3 = null;
                        var1.setline(226);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_list$9, (PyObject)null);
                        var1.setlocal("_deepcopy_list", var14);
                        var3 = null;
                        var1.setline(232);
                        var3 = var1.getname("_deepcopy_list");
                        var1.getname("d").__setitem__(var1.getname("list"), var3);
                        var3 = null;
                        var1.setline(234);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_tuple$10, (PyObject)null);
                        var1.setlocal("_deepcopy_tuple", var14);
                        var3 = null;
                        var1.setline(251);
                        var3 = var1.getname("_deepcopy_tuple");
                        var1.getname("d").__setitem__(var1.getname("tuple"), var3);
                        var3 = null;
                        var1.setline(253);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_dict$11, (PyObject)null);
                        var1.setlocal("_deepcopy_dict", var14);
                        var3 = null;
                        var1.setline(259);
                        var3 = var1.getname("_deepcopy_dict");
                        var1.getname("d").__setitem__(var1.getname("dict"), var3);
                        var3 = null;
                        var1.setline(260);
                        var3 = var1.getname("PyStringMap");
                        var10000 = var3._isnot(var1.getname("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(261);
                           var3 = var1.getname("_deepcopy_dict");
                           var1.getname("d").__setitem__(var1.getname("PyStringMap"), var3);
                           var3 = null;
                        }

                        var1.setline(263);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_method$12, (PyObject)null);
                        var1.setlocal("_deepcopy_method", var14);
                        var3 = null;
                        var1.setline(265);
                        var3 = var1.getname("_deepcopy_method");
                        var1.getname("_deepcopy_dispatch").__setitem__(var1.getname("types").__getattr__("MethodType"), var3);
                        var3 = null;
                        var1.setline(267);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _keep_alive$13, PyString.fromInterned("Keeps a reference to the object x in the memo.\n\n    Because we remember objects by their id, we have\n    to assure that possibly temporary objects are kept\n    alive by referencing them.\n    We store a reference at the id of the memo, which should\n    normally not be used unless someone tries to deepcopy\n    the memo itself...\n    "));
                        var1.setlocal("_keep_alive", var14);
                        var3 = null;
                        var1.setline(283);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _deepcopy_inst$14, (PyObject)null);
                        var1.setlocal("_deepcopy_inst", var14);
                        var3 = null;
                        var1.setline(304);
                        var3 = var1.getname("_deepcopy_inst");
                        var1.getname("d").__setitem__(var1.getname("types").__getattr__("InstanceType"), var3);
                        var3 = null;
                        var1.setline(306);
                        var11 = new PyObject[]{var1.getname("None")};
                        var14 = new PyFunction(var1.f_globals, var11, _reconstruct$15, (PyObject)null);
                        var1.setlocal("_reconstruct", var14);
                        var3 = null;
                        var1.setline(361);
                        var1.dellocal("d");
                        var1.setline(363);
                        var1.dellocal("types");
                        var1.setline(366);
                        var11 = Py.EmptyObjects;
                        var4 = Py.makeClass("_EmptyClass", var11, _EmptyClass$16);
                        var1.setlocal("_EmptyClass", var4);
                        var4 = null;
                        Arrays.fill(var11, (Object)null);
                        var1.setline(369);
                        var11 = Py.EmptyObjects;
                        var14 = new PyFunction(var1.f_globals, var11, _test$17, (PyObject)null);
                        var1.setlocal("_test", var14);
                        var3 = null;
                        var1.setline(432);
                        var3 = var1.getname("__name__");
                        var10000 = var3._eq(PyString.fromInterned("__main__"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(433);
                           var1.getname("_test").__call__(var2);
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal("t", var4);
                     var1.setline(116);
                     var5 = var1.getname("_copy_with_constructor");
                     var1.getname("d").__setitem__(var1.getname("t"), var5);
                     var5 = null;
                  }
               }

               var1.setlocal("name", var4);
               var1.setline(109);
               var5 = var1.getname("getattr").__call__(var2, var1.getname("types"), var1.getname("name"), var1.getname("None"));
               var1.setlocal("t", var5);
               var5 = null;
               var1.setline(110);
               var5 = var1.getname("t");
               var10000 = var5._isnot(var1.getname("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(111);
                  var5 = var1.getname("_copy_immutable");
                  var1.getname("d").__setitem__(var1.getname("t"), var5);
                  var5 = null;
               }
            }
         }

         var1.setlocal("t", var4);
         var1.setline(107);
         var5 = var1.getname("_copy_immutable");
         var1.getname("d").__setitem__(var1.getname("t"), var5);
         var5 = null;
      }
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(56);
      return var1.getf_locals();
   }

   public PyObject copy$2(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyString.fromInterned("Shallow copy operation on arbitrary Python objects.\n\n    See the module's __doc__ string for more info.\n    ");
      var1.setline(72);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("_copy_dispatch").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(75);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(76);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(78);
         PyObject var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__copy__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(79);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(80);
            var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(82);
            var4 = var1.getglobal("dispatch_table").__getattr__("get").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(83);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(84);
               var4 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(86);
               var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__reduce_ex__"), (PyObject)var1.getglobal("None"));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(87);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(88);
                  var4 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
                  var1.setlocal(4, var4);
                  var4 = null;
               } else {
                  var1.setline(90);
                  var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__reduce__"), (PyObject)var1.getglobal("None"));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(91);
                  if (!var1.getlocal(3).__nonzero__()) {
                     var1.setline(94);
                     throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("un(shallow)copyable object of type %s")._mod(var1.getlocal(1))));
                  }

                  var1.setline(92);
                  var4 = var1.getlocal(3).__call__(var2);
                  var1.setlocal(4, var4);
                  var4 = null;
               }
            }

            var1.setline(96);
            var3 = var1.getglobal("_reconstruct").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _copy_immutable$3(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _copy_with_constructor$4(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0)).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _copy_with_copy_method$5(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__getattr__("copy").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _copy_inst$6(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__copy__")).__nonzero__()) {
         var1.setline(125);
         var3 = var1.getlocal(0).__getattr__("__copy__").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(126);
         PyObject var4;
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__getinitargs__")).__nonzero__()) {
            var1.setline(127);
            var4 = var1.getlocal(0).__getattr__("__getinitargs__").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(128);
            PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
            PyObject[] var6 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var6, var5, var1.getlocal(1), (PyObject)null);
            var4 = null;
            var4 = var10000;
            var1.setlocal(2, var4);
            var4 = null;
         } else {
            var1.setline(130);
            var4 = var1.getglobal("_EmptyClass").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(131);
            var4 = var1.getlocal(0).__getattr__("__class__");
            var1.getlocal(2).__setattr__("__class__", var4);
            var4 = null;
         }

         var1.setline(132);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__getstate__")).__nonzero__()) {
            var1.setline(133);
            var4 = var1.getlocal(0).__getattr__("__getstate__").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
         } else {
            var1.setline(135);
            var4 = var1.getlocal(0).__getattr__("__dict__");
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(136);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__setstate__")).__nonzero__()) {
            var1.setline(137);
            var1.getlocal(2).__getattr__("__setstate__").__call__(var2, var1.getlocal(3));
         } else {
            var1.setline(139);
            var1.getlocal(2).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(3));
         }

         var1.setline(140);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject deepcopy$7(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyString.fromInterned("Deep copy operation on arbitrary Python objects.\n\n    See the module's __doc__ string for more info.\n    ");
      var1.setline(151);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(154);
      var3 = var1.getglobal("id").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(4);
      var10000 = var3._isnot(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(157);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(159);
         PyObject var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(161);
         var4 = var1.getglobal("_deepcopy_dispatch").__getattr__("get").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(162);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(163);
            var4 = var1.getlocal(6).__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            try {
               var1.setline(166);
               var4 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(5), var1.getglobal("type"));
               var1.setlocal(7, var4);
               var4 = null;
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (!var8.match(var1.getglobal("TypeError"))) {
                  throw var8;
               }

               var1.setline(168);
               PyInteger var5 = Py.newInteger(0);
               var1.setlocal(7, var5);
               var5 = null;
            }

            var1.setline(169);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(170);
               var4 = var1.getglobal("_deepcopy_atomic").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(172);
               var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__deepcopy__"), (PyObject)var1.getglobal("None"));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(173);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(174);
                  var4 = var1.getlocal(6).__call__(var2, var1.getlocal(1));
                  var1.setlocal(4, var4);
                  var4 = null;
               } else {
                  var1.setline(176);
                  var4 = var1.getglobal("dispatch_table").__getattr__("get").__call__(var2, var1.getlocal(5));
                  var1.setlocal(8, var4);
                  var4 = null;
                  var1.setline(177);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(178);
                     var4 = var1.getlocal(8).__call__(var2, var1.getlocal(0));
                     var1.setlocal(9, var4);
                     var4 = null;
                  } else {
                     var1.setline(180);
                     var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__reduce_ex__"), (PyObject)var1.getglobal("None"));
                     var1.setlocal(8, var4);
                     var4 = null;
                     var1.setline(181);
                     if (var1.getlocal(8).__nonzero__()) {
                        var1.setline(182);
                        var4 = var1.getlocal(8).__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
                        var1.setlocal(9, var4);
                        var4 = null;
                     } else {
                        var1.setline(184);
                        var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__reduce__"), (PyObject)var1.getglobal("None"));
                        var1.setlocal(8, var4);
                        var4 = null;
                        var1.setline(185);
                        if (!var1.getlocal(8).__nonzero__()) {
                           var1.setline(188);
                           throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("un(deep)copyable object of type %s")._mod(var1.getlocal(5))));
                        }

                        var1.setline(186);
                        var4 = var1.getlocal(8).__call__(var2);
                        var1.setlocal(9, var4);
                        var4 = null;
                     }
                  }

                  var1.setline(190);
                  var4 = var1.getglobal("_reconstruct").__call__(var2, var1.getlocal(0), var1.getlocal(9), Py.newInteger(1), var1.getlocal(1));
                  var1.setlocal(4, var4);
                  var4 = null;
               }
            }
         }

         var1.setline(192);
         var4 = var1.getlocal(4);
         var1.getlocal(1).__setitem__(var1.getlocal(3), var4);
         var4 = null;
         var1.setline(193);
         var1.getglobal("_keep_alive").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(194);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _deepcopy_atomic$8(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _deepcopy_list$9(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(228);
      PyObject var5 = var1.getlocal(2);
      var1.getlocal(1).__setitem__(var1.getglobal("id").__call__(var2, var1.getlocal(0)), var5);
      var3 = null;
      var1.setline(229);
      var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(229);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(231);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(230);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("deepcopy").__call__(var2, var1.getlocal(3), var1.getlocal(1)));
      }
   }

   public PyObject _deepcopy_tuple$10(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(236);
      PyObject var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(236);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(238);
            var8 = var1.getglobal("id").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var8);
            var3 = null;

            try {
               var1.setline(240);
               var8 = var1.getlocal(1).__getitem__(var1.getlocal(4));
               var1.f_lasti = -1;
               return var8;
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (var9.match(var1.getglobal("KeyError"))) {
                  var1.setline(242);
                  var1.setline(243);
                  var4 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

                  while(true) {
                     var1.setline(243);
                     PyObject var5 = var4.__iternext__();
                     PyObject var6;
                     if (var5 == null) {
                        var1.setline(248);
                        var6 = var1.getlocal(0);
                        var1.setlocal(2, var6);
                        var6 = null;
                        break;
                     }

                     var1.setlocal(5, var5);
                     var1.setline(244);
                     var6 = var1.getlocal(0).__getitem__(var1.getlocal(5));
                     PyObject var10000 = var6._isnot(var1.getlocal(2).__getitem__(var1.getlocal(5)));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(245);
                        var6 = var1.getglobal("tuple").__call__(var2, var1.getlocal(2));
                        var1.setlocal(2, var6);
                        var6 = null;
                        break;
                     }
                  }

                  var1.setline(249);
                  var4 = var1.getlocal(2);
                  var1.getlocal(1).__setitem__(var1.getlocal(4), var4);
                  var4 = null;
                  var1.setline(250);
                  var8 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  throw var9;
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(237);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("deepcopy").__call__(var2, var1.getlocal(3), var1.getlocal(1)));
      }
   }

   public PyObject _deepcopy_dict$11(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(255);
      PyObject var7 = var1.getlocal(2);
      var1.getlocal(1).__setitem__(var1.getglobal("id").__call__(var2, var1.getlocal(0)), var7);
      var3 = null;
      var1.setline(256);
      var7 = var1.getlocal(0).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(256);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(258);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(257);
         PyObject var8 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(4), var1.getlocal(1));
         var1.getlocal(2).__setitem__(var1.getglobal("deepcopy").__call__(var2, var1.getlocal(3), var1.getlocal(1)), var8);
         var5 = null;
      }
   }

   public PyObject _deepcopy_method$12(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0)).__call__(var2, var1.getlocal(0).__getattr__("im_func"), var1.getglobal("deepcopy").__call__(var2, var1.getlocal(0).__getattr__("im_self"), var1.getlocal(1)), var1.getlocal(0).__getattr__("im_class"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _keep_alive$13(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyString.fromInterned("Keeps a reference to the object x in the memo.\n\n    Because we remember objects by their id, we have\n    to assure that possibly temporary objects are kept\n    alive by referencing them.\n    We store a reference at the id of the memo, which should\n    normally not be used unless someone tries to deepcopy\n    the memo itself...\n    ");

      try {
         var1.setline(278);
         var1.getlocal(1).__getitem__(var1.getglobal("id").__call__(var2, var1.getlocal(1))).__getattr__("append").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(281);
         PyList var4 = new PyList(new PyObject[]{var1.getlocal(0)});
         var1.getlocal(1).__setitem__((PyObject)var1.getglobal("id").__call__(var2, var1.getlocal(1)), var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _deepcopy_inst$14(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__deepcopy__")).__nonzero__()) {
         var1.setline(285);
         var3 = var1.getlocal(0).__getattr__("__deepcopy__").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(286);
         PyObject var4;
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__getinitargs__")).__nonzero__()) {
            var1.setline(287);
            var4 = var1.getlocal(0).__getattr__("__getinitargs__").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(288);
            var4 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(289);
            PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
            PyObject[] var6 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var6, var5, var1.getlocal(2), (PyObject)null);
            var4 = null;
            var4 = var10000;
            var1.setlocal(3, var4);
            var4 = null;
         } else {
            var1.setline(291);
            var4 = var1.getglobal("_EmptyClass").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(292);
            var4 = var1.getlocal(0).__getattr__("__class__");
            var1.getlocal(3).__setattr__("__class__", var4);
            var4 = null;
         }

         var1.setline(293);
         var4 = var1.getlocal(3);
         var1.getlocal(1).__setitem__(var1.getglobal("id").__call__(var2, var1.getlocal(0)), var4);
         var4 = null;
         var1.setline(294);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__getstate__")).__nonzero__()) {
            var1.setline(295);
            var4 = var1.getlocal(0).__getattr__("__getstate__").__call__(var2);
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            var1.setline(297);
            var4 = var1.getlocal(0).__getattr__("__dict__");
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(298);
         var4 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(4), var1.getlocal(1));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(299);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("__setstate__")).__nonzero__()) {
            var1.setline(300);
            var1.getlocal(3).__getattr__("__setstate__").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(302);
            var1.getlocal(3).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(4));
         }

         var1.setline(303);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _reconstruct$15(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(308);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(309);
         PyObject var10000;
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(310);
            PyObject var4 = var1.getlocal(3);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            PyDictionary var8;
            if (var10000.__nonzero__()) {
               var1.setline(311);
               var8 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(3, var8);
               var4 = null;
            }

            var1.setline(312);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(313);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getlocal(4);
               var10000 = var4._in(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(3), Py.newInteger(4), Py.newInteger(5)}));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(314);
            var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
            var1.setline(315);
            var4 = var1.getlocal(4);
            var10000 = var4._gt(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(316);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
               var1.setlocal(7, var4);
               var4 = null;
            } else {
               var1.setline(318);
               var8 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(7, var8);
               var4 = null;
            }

            var1.setline(319);
            var4 = var1.getlocal(4);
            var10000 = var4._gt(Py.newInteger(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(320);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(3));
               var1.setlocal(8, var4);
               var4 = null;
            } else {
               var1.setline(322);
               var4 = var1.getglobal("None");
               var1.setlocal(8, var4);
               var4 = null;
            }

            var1.setline(323);
            var4 = var1.getlocal(4);
            var10000 = var4._gt(Py.newInteger(4));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(324);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(4));
               var1.setlocal(9, var4);
               var4 = null;
            } else {
               var1.setline(326);
               var4 = var1.getglobal("None");
               var1.setlocal(9, var4);
               var4 = null;
            }

            var1.setline(327);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(328);
               var4 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(6), var1.getlocal(3));
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(329);
            var10000 = var1.getlocal(5);
            PyObject[] var12 = Py.EmptyObjects;
            String[] var9 = new String[0];
            var10000 = var10000._callextra(var12, var9, var1.getlocal(6), (PyObject)null);
            var4 = null;
            var4 = var10000;
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(330);
            var4 = var1.getlocal(10);
            var1.getlocal(3).__setitem__(var1.getglobal("id").__call__(var2, var1.getlocal(0)), var4);
            var4 = null;
            var1.setline(332);
            PyObject var7;
            PyObject var10;
            PyObject[] var11;
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(333);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(334);
                  var4 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(7), var1.getlocal(3));
                  var1.setlocal(7, var4);
                  var4 = null;
               }

               var1.setline(335);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned("__setstate__")).__nonzero__()) {
                  var1.setline(336);
                  var1.getlocal(10).__getattr__("__setstate__").__call__(var2, var1.getlocal(7));
               } else {
                  var1.setline(338);
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("tuple"));
                  if (var10000.__nonzero__()) {
                     var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                     var10000 = var4._eq(Py.newInteger(2));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(339);
                     var4 = var1.getlocal(7);
                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(7, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(11, var6);
                     var6 = null;
                     var4 = null;
                  } else {
                     var1.setline(341);
                     var4 = var1.getglobal("None");
                     var1.setlocal(11, var4);
                     var4 = null;
                  }

                  var1.setline(342);
                  var4 = var1.getlocal(7);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(343);
                     var1.getlocal(10).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(7));
                  }

                  var1.setline(344);
                  var4 = var1.getlocal(11);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(345);
                     var4 = var1.getlocal(11).__getattr__("iteritems").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(345);
                        var10 = var4.__iternext__();
                        if (var10 == null) {
                           break;
                        }

                        var11 = Py.unpackSequence(var10, 2);
                        var7 = var11[0];
                        var1.setlocal(12, var7);
                        var7 = null;
                        var7 = var11[1];
                        var1.setlocal(13, var7);
                        var7 = null;
                        var1.setline(346);
                        var1.getglobal("setattr").__call__(var2, var1.getlocal(10), var1.getlocal(12), var1.getlocal(13));
                     }
                  }
               }
            }

            var1.setline(348);
            var4 = var1.getlocal(8);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(349);
               var4 = var1.getlocal(8).__iter__();

               while(true) {
                  var1.setline(349);
                  var10 = var4.__iternext__();
                  if (var10 == null) {
                     break;
                  }

                  var1.setlocal(14, var10);
                  var1.setline(350);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(351);
                     var6 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(14), var1.getlocal(3));
                     var1.setlocal(14, var6);
                     var6 = null;
                  }

                  var1.setline(352);
                  var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(14));
               }
            }

            var1.setline(353);
            var4 = var1.getlocal(9);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(354);
               var4 = var1.getlocal(9).__iter__();

               while(true) {
                  var1.setline(354);
                  var10 = var4.__iternext__();
                  if (var10 == null) {
                     break;
                  }

                  var11 = Py.unpackSequence(var10, 2);
                  var7 = var11[0];
                  var1.setlocal(12, var7);
                  var7 = null;
                  var7 = var11[1];
                  var1.setlocal(13, var7);
                  var7 = null;
                  var1.setline(355);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(356);
                     var6 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(12), var1.getlocal(3));
                     var1.setlocal(12, var6);
                     var6 = null;
                     var1.setline(357);
                     var6 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(13), var1.getlocal(3));
                     var1.setlocal(13, var6);
                     var6 = null;
                  }

                  var1.setline(358);
                  var6 = var1.getlocal(13);
                  var1.getlocal(10).__setitem__(var1.getlocal(12), var6);
                  var6 = null;
               }
            }

            var1.setline(359);
            var3 = var1.getlocal(10);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _EmptyClass$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(367);
      return var1.getf_locals();
   }

   public PyObject _test$17(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyList var3 = new PyList(new PyObject[]{var1.getglobal("None"), Py.newInteger(1), Py.newLong("2"), Py.newFloat(3.14), PyString.fromInterned("xyzzy"), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newLong("2")}), new PyList(new PyObject[]{Py.newFloat(3.14), PyString.fromInterned("abc")}), new PyDictionary(new PyObject[]{PyString.fromInterned("abc"), PyString.fromInterned("ABC")}), new PyTuple(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(372);
      PyObject var5 = var1.getglobal("copy").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(373);
      var5 = var1.getlocal(1);
      PyObject var10000 = var5._eq(var1.getlocal(0));
      var3 = null;
      Py.println(var10000);
      var1.setline(374);
      var5 = var1.getglobal("map").__call__(var2, var1.getglobal("copy"), var1.getlocal(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(375);
      var5 = var1.getlocal(1);
      var10000 = var5._eq(var1.getlocal(0));
      var3 = null;
      Py.println(var10000);
      var1.setline(376);
      var5 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(377);
      var5 = var1.getlocal(1);
      var10000 = var5._eq(var1.getlocal(0));
      var3 = null;
      Py.println(var10000);
      var1.setline(378);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("C", var6, C$18);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(398);
      var5 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument sketch"));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(399);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(400);
      var5 = var1.getglobal("copy").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(401);
      var5 = var1.getlocal(0);
      var10000 = var5._eq(var1.getlocal(4));
      var3 = null;
      Py.println(var10000);
      var1.setline(402);
      Py.println(var1.getlocal(0));
      var1.setline(403);
      Py.println(var1.getlocal(4));
      var1.setline(404);
      var5 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(405);
      var5 = var1.getlocal(0);
      var10000 = var5._eq(var1.getlocal(4));
      var3 = null;
      Py.println(var10000);
      var1.setline(406);
      Py.println(var1.getlocal(0));
      var1.setline(407);
      Py.println(var1.getlocal(4));
      var1.setline(408);
      var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(0), PyString.fromInterned("xyz"), var1.getlocal(0).__getitem__(Py.newInteger(2))})));
      var1.setline(409);
      var5 = var1.getglobal("copy").__call__(var2, var1.getlocal(0));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(410);
      var5 = imp.importOne("repr", var1, -1);
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(411);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(0)));
      var1.setline(412);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(1)));
      var1.setline(413);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(4)));
      var1.setline(414);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(5)));
      var1.setline(415);
      var5 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(0));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(416);
      var5 = imp.importOne("repr", var1, -1);
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(417);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(0)));
      var1.setline(418);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(1)));
      var1.setline(419);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(4)));
      var1.setline(420);
      Py.println(var1.getglobal("map").__call__(var2, var1.getlocal(6).__getattr__("repr"), var1.getlocal(5)));
      var1.setline(421);
      var6 = new PyObject[]{var1.getglobal("dict")};
      var4 = Py.makeClass("odict", var6, odict$23);
      var1.setlocal(7, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(428);
      var5 = var1.getlocal(7).__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("A"), PyString.fromInterned("B")})));
      var1.setlocal(8, var5);
      var3 = null;
      var1.setline(429);
      var5 = var1.getglobal("deepcopy").__call__(var2, var1.getlocal(8));
      var1.setlocal(9, var5);
      var3 = null;
      var1.setline(430);
      Py.println(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)}));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject C$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(379);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getstate__$20, (PyObject)null);
      var1.setlocal("__getstate__", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setstate__$21, (PyObject)null);
      var1.setlocal("__setstate__", var4);
      var3 = null;
      var1.setline(394);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __deepcopy__$22, (PyObject)null);
      var1.setlocal("__deepcopy__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"a", var3);
      var3 = null;
      var1.setline(381);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("arg", var4);
      var3 = null;
      var1.setline(382);
      var4 = var1.getglobal("__name__");
      PyObject var10000 = var4._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var4 = imp.importOne("sys", var1, -1);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(384);
         var4 = var1.getlocal(2).__getattr__("argv").__getitem__(Py.newInteger(0));
         var1.setlocal(3, var4);
         var3 = null;
      } else {
         var1.setline(386);
         var4 = var1.getglobal("__file__");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(387);
      var4 = var1.getglobal("open").__call__(var2, var1.getlocal(3));
      var1.getlocal(0).__setattr__("fp", var4);
      var3 = null;
      var1.setline(388);
      var1.getlocal(0).__getattr__("fp").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$20(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("a"), var1.getlocal(0).__getattr__("a"), PyString.fromInterned("arg"), var1.getlocal(0).__getattr__("arg")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$21(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(392);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(393);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
      }
   }

   public PyObject __deepcopy__$22(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("deepcopy").__call__(var2, var1.getlocal(0).__getattr__("arg"), var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(396);
      var3 = var1.getlocal(0).__getattr__("a");
      var1.getlocal(2).__setattr__("a", var3);
      var3 = null;
      var1.setline(397);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject odict$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(422);
      PyObject[] var3 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$24, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$25, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyInteger var3 = Py.newInteger(99);
      var1.getlocal(0).__setattr__((String)"a", var3);
      var3 = null;
      var1.setline(424);
      var1.getglobal("dict").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$25(PyFrame var1, ThreadState var2) {
      var1.setline(426);
      var1.getglobal("dict").__getattr__("__setitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(427);
      var1.getlocal(0).__getattr__("a");
      var1.f_lasti = -1;
      return Py.None;
   }

   public copy$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 55, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x", "cls", "copier", "reductor", "rv"};
      copy$2 = Py.newCode(1, var2, var1, "copy", 66, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _copy_immutable$3 = Py.newCode(1, var2, var1, "_copy_immutable", 101, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _copy_with_constructor$4 = Py.newCode(1, var2, var1, "_copy_with_constructor", 113, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _copy_with_copy_method$5 = Py.newCode(1, var2, var1, "_copy_with_copy_method", 118, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "args", "y", "state"};
      _copy_inst$6 = Py.newCode(1, var2, var1, "_copy_inst", 123, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo", "_nil", "d", "y", "cls", "copier", "issc", "reductor", "rv"};
      deepcopy$7 = Py.newCode(3, var2, var1, "deepcopy", 145, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo"};
      _deepcopy_atomic$8 = Py.newCode(2, var2, var1, "_deepcopy_atomic", 198, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo", "y", "a"};
      _deepcopy_list$9 = Py.newCode(2, var2, var1, "_deepcopy_list", 226, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo", "y", "a", "d", "i"};
      _deepcopy_tuple$10 = Py.newCode(2, var2, var1, "_deepcopy_tuple", 234, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo", "y", "key", "value"};
      _deepcopy_dict$11 = Py.newCode(2, var2, var1, "_deepcopy_dict", 253, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo"};
      _deepcopy_method$12 = Py.newCode(2, var2, var1, "_deepcopy_method", 263, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo"};
      _keep_alive$13 = Py.newCode(2, var2, var1, "_keep_alive", 267, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo", "args", "y", "state"};
      _deepcopy_inst$14 = Py.newCode(2, var2, var1, "_deepcopy_inst", 283, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "info", "deep", "memo", "n", "callable", "args", "state", "listiter", "dictiter", "y", "slotstate", "key", "value", "item"};
      _reconstruct$15 = Py.newCode(4, var2, var1, "_reconstruct", 306, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _EmptyClass$16 = Py.newCode(0, var2, var1, "_EmptyClass", 366, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"l", "l1", "C", "c", "l2", "l3", "repr", "odict", "o", "x"};
      _test$17 = Py.newCode(0, var2, var1, "_test", 369, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      C$18 = Py.newCode(0, var2, var1, "C", 378, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "arg", "sys", "file"};
      __init__$19 = Py.newCode(2, var2, var1, "__init__", 379, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$20 = Py.newCode(1, var2, var1, "__getstate__", 389, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "key", "value"};
      __setstate__$21 = Py.newCode(2, var2, var1, "__setstate__", 391, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "memo", "new"};
      __deepcopy__$22 = Py.newCode(2, var2, var1, "__deepcopy__", 394, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      odict$23 = Py.newCode(0, var2, var1, "odict", 421, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "d"};
      __init__$24 = Py.newCode(2, var2, var1, "__init__", 422, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k", "i"};
      __setitem__$25 = Py.newCode(3, var2, var1, "__setitem__", 425, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new copy$py("copy$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(copy$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.copy$2(var2, var3);
         case 3:
            return this._copy_immutable$3(var2, var3);
         case 4:
            return this._copy_with_constructor$4(var2, var3);
         case 5:
            return this._copy_with_copy_method$5(var2, var3);
         case 6:
            return this._copy_inst$6(var2, var3);
         case 7:
            return this.deepcopy$7(var2, var3);
         case 8:
            return this._deepcopy_atomic$8(var2, var3);
         case 9:
            return this._deepcopy_list$9(var2, var3);
         case 10:
            return this._deepcopy_tuple$10(var2, var3);
         case 11:
            return this._deepcopy_dict$11(var2, var3);
         case 12:
            return this._deepcopy_method$12(var2, var3);
         case 13:
            return this._keep_alive$13(var2, var3);
         case 14:
            return this._deepcopy_inst$14(var2, var3);
         case 15:
            return this._reconstruct$15(var2, var3);
         case 16:
            return this._EmptyClass$16(var2, var3);
         case 17:
            return this._test$17(var2, var3);
         case 18:
            return this.C$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.__getstate__$20(var2, var3);
         case 21:
            return this.__setstate__$21(var2, var3);
         case 22:
            return this.__deepcopy__$22(var2, var3);
         case 23:
            return this.odict$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.__setitem__$25(var2, var3);
         default:
            return null;
      }
   }
}

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
@Filename("pyclbr.py")
public class pyclbr$py extends PyFunctionTable implements PyRunnable {
   static pyclbr$py self;
   static final PyCode f$0;
   static final PyCode Class$1;
   static final PyCode __init__$2;
   static final PyCode _addmethod$3;
   static final PyCode Function$4;
   static final PyCode __init__$5;
   static final PyCode readmodule$6;
   static final PyCode readmodule_ex$7;
   static final PyCode _readmodule$8;
   static final PyCode _getnamelist$9;
   static final PyCode _getname$10;
   static final PyCode _main$11;
   static final PyCode f$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parse a Python module and describe its classes and methods.\n\nParse enough of a Python file to recognize imports and class and\nmethod definitions, and to find out the superclasses of a class.\n\nThe interface consists of a single function:\n        readmodule_ex(module [, path])\nwhere module is the name of a Python module, and path is an optional\nlist of directories where the module is to be searched.  If present,\npath is prepended to the system search path sys.path.  The return\nvalue is a dictionary.  The keys of the dictionary are the names of\nthe classes defined in the module (including classes that are defined\nvia the from XXX import YYY construct).  The values are class\ninstances of the class Class defined here.  One special key/value pair\nis present for packages: the key '__path__' has a list as its value\nwhich contains the package search path.\n\nA class is described by the class Class in this module.  Instances\nof this class have the following instance variables:\n        module -- the module name\n        name -- the name of the class\n        super -- a list of super classes (Class instances)\n        methods -- a dictionary of methods\n        file -- the file in which the class was defined\n        lineno -- the line in the file on which the class statement occurred\nThe dictionary of methods uses the method names as keys and the line\nnumbers on which the method was defined as values.\nIf the name of a super class is not recognized, the corresponding\nentry in the list of super classes is not a class instance but a\nstring giving the name of the super class.  Since import statements\nare recognized and imported modules are scanned as well, this\nshouldn't happen often.\n\nA function is described by the class Function in this module.\nInstances of this class have the following instance variables:\n        module -- the module name\n        name -- the name of the class\n        file -- the file in which the class was defined\n        lineno -- the line in the file on which the class statement occurred\n"));
      var1.setline(40);
      PyString.fromInterned("Parse a Python module and describe its classes and methods.\n\nParse enough of a Python file to recognize imports and class and\nmethod definitions, and to find out the superclasses of a class.\n\nThe interface consists of a single function:\n        readmodule_ex(module [, path])\nwhere module is the name of a Python module, and path is an optional\nlist of directories where the module is to be searched.  If present,\npath is prepended to the system search path sys.path.  The return\nvalue is a dictionary.  The keys of the dictionary are the names of\nthe classes defined in the module (including classes that are defined\nvia the from XXX import YYY construct).  The values are class\ninstances of the class Class defined here.  One special key/value pair\nis present for packages: the key '__path__' has a list as its value\nwhich contains the package search path.\n\nA class is described by the class Class in this module.  Instances\nof this class have the following instance variables:\n        module -- the module name\n        name -- the name of the class\n        super -- a list of super classes (Class instances)\n        methods -- a dictionary of methods\n        file -- the file in which the class was defined\n        lineno -- the line in the file on which the class statement occurred\nThe dictionary of methods uses the method names as keys and the line\nnumbers on which the method was defined as values.\nIf the name of a super class is not recognized, the corresponding\nentry in the list of super classes is not a class instance but a\nstring giving the name of the super class.  Since import statements\nare recognized and imported modules are scanned as well, this\nshouldn't happen often.\n\nA function is described by the class Function in this module.\nInstances of this class have the following instance variables:\n        module -- the module name\n        name -- the name of the class\n        file -- the file in which the class was defined\n        lineno -- the line in the file on which the class statement occurred\n");
      var1.setline(42);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(43);
      var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(44);
      var3 = imp.importOne("tokenize", var1, -1);
      var1.setlocal("tokenize", var3);
      var3 = null;
      var1.setline(45);
      String[] var5 = new String[]{"NAME", "DEDENT", "OP"};
      PyObject[] var6 = imp.importFrom("token", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("NAME", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DEDENT", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("OP", var4);
      var4 = null;
      var1.setline(46);
      var5 = new String[]{"itemgetter"};
      var6 = imp.importFrom("operator", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("itemgetter", var4);
      var4 = null;
      var1.setline(48);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("readmodule"), PyString.fromInterned("readmodule_ex"), PyString.fromInterned("Class"), PyString.fromInterned("Function")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(50);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_modules", var8);
      var3 = null;
      var1.setline(53);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Class", var6, Class$1);
      var1.setlocal("Class", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(68);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Function", var6, Function$4);
      var1.setlocal("Function", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(76);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var6, readmodule$6, PyString.fromInterned("Backwards compatible interface.\n\n    Call readmodule_ex() and then only keep Class objects from the\n    resulting dictionary."));
      var1.setlocal("readmodule", var9);
      var3 = null;
      var1.setline(88);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, readmodule_ex$7, PyString.fromInterned("Read a module file and return a dictionary of classes.\n\n    Search for MODULE in PATH and sys.path, read and parse the\n    module and return a dictionary with one entry for each class\n    found in the module.\n    "));
      var1.setlocal("readmodule_ex", var9);
      var3 = null;
      var1.setline(97);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, _readmodule$8, PyString.fromInterned("Do the hard work for readmodule[_ex].\n\n    If INPACKAGE is given, it must be the dotted name of the package in\n    which we are searching for a submodule, and then PATH must be the\n    package search path; otherwise, we are searching for a top-level\n    module, and PATH is combined with sys.path.\n    "));
      var1.setlocal("_readmodule", var9);
      var3 = null;
      var1.setline(279);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _getnamelist$9, (PyObject)null);
      var1.setlocal("_getnamelist", var9);
      var3 = null;
      var1.setline(299);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _getname$10, (PyObject)null);
      var1.setlocal("_getname", var9);
      var3 = null;
      var1.setline(318);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _main$11, (PyObject)null);
      var1.setlocal("_main", var9);
      var3 = null;
      var1.setline(343);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(344);
         var1.getname("_main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Class$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to represent a Python class."));
      var1.setline(54);
      PyString.fromInterned("Class to represent a Python class.");
      var1.setline(55);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _addmethod$3, (PyObject)null);
      var1.setlocal("_addmethod", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(59);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(60);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("super", var3);
      var3 = null;
      var1.setline(61);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"methods", var5);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _addmethod$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("methods").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Function$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to represent a top-level Python function"));
      var1.setline(69);
      PyString.fromInterned("Class to represent a top-level Python function");
      var1.setline(70);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readmodule$6(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Backwards compatible interface.\n\n    Call readmodule_ex() and then only keep Class objects from the\n    resulting dictionary.");
      var1.setline(82);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(83);
      PyObject var10000 = var1.getglobal("_readmodule");
      PyObject var10002 = var1.getlocal(0);
      Object var10003 = var1.getlocal(1);
      if (!((PyObject)var10003).__nonzero__()) {
         var10003 = new PyList(Py.EmptyObjects);
      }

      PyObject var7 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(83);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(86);
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
         var1.setline(84);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("Class")).__nonzero__()) {
            var1.setline(85);
            PyObject var8 = var1.getlocal(4);
            var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
            var5 = null;
         }
      }
   }

   public PyObject readmodule_ex$7(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Read a module file and return a dictionary of classes.\n\n    Search for MODULE in PATH and sys.path, read and parse the\n    module and return a dictionary with one entry for each class\n    found in the module.\n    ");
      var1.setline(95);
      PyObject var10000 = var1.getglobal("_readmodule");
      PyObject var10002 = var1.getlocal(0);
      Object var10003 = var1.getlocal(1);
      if (!((PyObject)var10003).__nonzero__()) {
         var10003 = new PyList(Py.EmptyObjects);
      }

      PyObject var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _readmodule$8(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Do the hard work for readmodule[_ex].\n\n    If INPACKAGE is given, it must be the dotted name of the package in\n    which we are searching for a submodule, and then PATH must be the\n    package search path; otherwise, we are searching for a top-level\n    module, and PATH is combined with sys.path.\n    ");
      var1.setline(106);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0)}));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(109);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(112);
      var3 = var1.getlocal(3);
      var10000 = var3._in(var1.getglobal("_modules"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(113);
         var3 = var1.getglobal("_modules").__getitem__(var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(116);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(119);
         PyObject var15 = var1.getlocal(0);
         var10000 = var15._in(var1.getglobal("sys").__getattr__("builtin_module_names"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var15 = var1.getlocal(2);
            var10000 = var15._is(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(120);
            var15 = var1.getlocal(4);
            var1.getglobal("_modules").__setitem__(var1.getlocal(0), var15);
            var4 = null;
            var1.setline(121);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(124);
            var15 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setlocal(5, var15);
            var4 = null;
            var1.setline(125);
            var15 = var1.getlocal(5);
            var10000 = var15._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(126);
               var15 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
               var1.setlocal(6, var15);
               var4 = null;
               var1.setline(127);
               var15 = var1.getlocal(0).__getslice__(var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(7, var15);
               var4 = null;
               var1.setline(128);
               var15 = var1.getglobal("_readmodule").__call__(var2, var1.getlocal(6), var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(8, var15);
               var4 = null;
               var1.setline(129);
               var15 = var1.getlocal(2);
               var10000 = var15._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(130);
                  var15 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(6)}));
                  var1.setlocal(6, var15);
                  var4 = null;
               }

               var1.setline(131);
               PyString var23 = PyString.fromInterned("__path__");
               var10000 = var23._in(var1.getlocal(8));
               var4 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(132);
                  throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("No package named {}").__getattr__("format").__call__(var2, var1.getlocal(6))));
               } else {
                  var1.setline(133);
                  var3 = var1.getglobal("_readmodule").__call__(var2, var1.getlocal(7), var1.getlocal(8).__getitem__(PyString.fromInterned("__path__")), var1.getlocal(6));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(136);
               var15 = var1.getglobal("None");
               var1.setlocal(9, var15);
               var4 = null;
               var1.setline(137);
               var15 = var1.getlocal(2);
               var10000 = var15._isnot(var1.getglobal("None"));
               var4 = null;
               PyObject[] var5;
               PyObject var6;
               PyObject[] var7;
               PyObject var8;
               if (var10000.__nonzero__()) {
                  var1.setline(138);
                  var15 = var1.getglobal("imp").__getattr__("find_module").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                  var5 = Py.unpackSequence(var15, 3);
                  var6 = var5[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[2];
                  var7 = Py.unpackSequence(var6, 3);
                  var8 = var7[0];
                  var1.setlocal(11, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(12, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(13, var8);
                  var8 = null;
                  var6 = null;
                  var4 = null;
               } else {
                  var1.setline(140);
                  var15 = var1.getglobal("imp").__getattr__("find_module").__call__(var2, var1.getlocal(0), var1.getlocal(1)._add(var1.getglobal("sys").__getattr__("path")));
                  var5 = Py.unpackSequence(var15, 3);
                  var6 = var5[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[2];
                  var7 = Py.unpackSequence(var6, 3);
                  var8 = var7[0];
                  var1.setlocal(11, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(12, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(13, var8);
                  var8 = null;
                  var6 = null;
                  var4 = null;
               }

               var1.setline(141);
               var15 = var1.getlocal(13);
               var10000 = var15._eq(var1.getglobal("imp").__getattr__("PKG_DIRECTORY"));
               var4 = null;
               PyList var18;
               if (var10000.__nonzero__()) {
                  var1.setline(142);
                  var18 = new PyList(new PyObject[]{var1.getlocal(10)});
                  var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("__path__"), var18);
                  var4 = null;
                  var1.setline(143);
                  var15 = (new PyList(new PyObject[]{var1.getlocal(10)}))._add(var1.getlocal(1));
                  var1.setlocal(1, var15);
                  var4 = null;
                  var1.setline(144);
                  var15 = var1.getglobal("imp").__getattr__("find_module").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__init__"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(10)})));
                  var5 = Py.unpackSequence(var15, 3);
                  var6 = var5[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[2];
                  var7 = Py.unpackSequence(var6, 3);
                  var8 = var7[0];
                  var1.setlocal(11, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(12, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(13, var8);
                  var8 = null;
                  var6 = null;
                  var4 = null;
               }

               var1.setline(145);
               var15 = var1.getlocal(4);
               var1.getglobal("_modules").__setitem__(var1.getlocal(3), var15);
               var4 = null;
               var1.setline(146);
               var15 = var1.getlocal(13);
               var10000 = var15._ne(var1.getglobal("imp").__getattr__("PY_SOURCE"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(148);
                  var1.getlocal(9).__getattr__("close").__call__(var2);
                  var1.setline(149);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(151);
                  var18 = new PyList(Py.EmptyObjects);
                  var1.setlocal(14, var18);
                  var4 = null;
                  var1.setline(153);
                  var15 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(9).__getattr__("readline"));
                  var1.setlocal(15, var15);
                  var4 = null;

                  try {
                     var1.setline(155);
                     var15 = var1.getlocal(15).__iter__();

                     label270:
                     while(true) {
                        label262:
                        while(true) {
                           var1.setline(155);
                           PyObject var16 = var15.__iternext__();
                           if (var16 == null) {
                              break label270;
                           }

                           PyObject[] var19 = Py.unpackSequence(var16, 5);
                           PyObject var17 = var19[0];
                           var1.setlocal(16, var17);
                           var7 = null;
                           var17 = var19[1];
                           var1.setlocal(17, var17);
                           var7 = null;
                           var17 = var19[2];
                           var1.setlocal(18, var17);
                           var7 = null;
                           var17 = var19[3];
                           var1.setlocal(19, var17);
                           var7 = null;
                           var17 = var19[4];
                           var1.setlocal(20, var17);
                           var7 = null;
                           var1.setline(156);
                           var6 = var1.getlocal(16);
                           var10000 = var6._eq(var1.getglobal("DEDENT"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(157);
                              var6 = var1.getlocal(18);
                              var7 = Py.unpackSequence(var6, 2);
                              var8 = var7[0];
                              var1.setlocal(21, var8);
                              var8 = null;
                              var8 = var7[1];
                              var1.setlocal(22, var8);
                              var8 = null;
                              var6 = null;

                              while(true) {
                                 var1.setline(159);
                                 var10000 = var1.getlocal(14);
                                 if (var10000.__nonzero__()) {
                                    var6 = var1.getlocal(14).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                                    var10000 = var6._ge(var1.getlocal(22));
                                    var6 = null;
                                 }

                                 if (!var10000.__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(160);
                                 var1.getlocal(14).__delitem__((PyObject)Py.newInteger(-1));
                              }
                           } else {
                              var1.setline(161);
                              var6 = var1.getlocal(17);
                              var10000 = var6._eq(PyString.fromInterned("def"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(162);
                                 var6 = var1.getlocal(18);
                                 var7 = Py.unpackSequence(var6, 2);
                                 var8 = var7[0];
                                 var1.setlocal(21, var8);
                                 var8 = null;
                                 var8 = var7[1];
                                 var1.setlocal(22, var8);
                                 var8 = null;
                                 var6 = null;

                                 while(true) {
                                    var1.setline(164);
                                    var10000 = var1.getlocal(14);
                                    if (var10000.__nonzero__()) {
                                       var6 = var1.getlocal(14).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                                       var10000 = var6._ge(var1.getlocal(22));
                                       var6 = null;
                                    }

                                    if (!var10000.__nonzero__()) {
                                       var1.setline(166);
                                       var6 = var1.getlocal(15).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
                                       var7 = Py.unpackSequence(var6, 3);
                                       var8 = var7[0];
                                       var1.setlocal(16, var8);
                                       var8 = null;
                                       var8 = var7[1];
                                       var1.setlocal(23, var8);
                                       var8 = null;
                                       var8 = var7[2];
                                       var1.setlocal(18, var8);
                                       var8 = null;
                                       var6 = null;
                                       var1.setline(167);
                                       var6 = var1.getlocal(16);
                                       var10000 = var6._ne(var1.getglobal("NAME"));
                                       var6 = null;
                                       if (!var10000.__nonzero__()) {
                                          var1.setline(169);
                                          if (var1.getlocal(14).__nonzero__()) {
                                             var1.setline(170);
                                             var6 = var1.getlocal(14).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
                                             var1.setlocal(24, var6);
                                             var6 = null;
                                             var1.setline(171);
                                             if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(24), var1.getglobal("Class")).__nonzero__()) {
                                                var1.setline(173);
                                                var1.getlocal(24).__getattr__("_addmethod").__call__(var2, var1.getlocal(23), var1.getlocal(21));
                                             }
                                          } else {
                                             var1.setline(177);
                                             var6 = var1.getglobal("Function").__call__(var2, var1.getlocal(3), var1.getlocal(23), var1.getlocal(10), var1.getlocal(21));
                                             var1.getlocal(4).__setitem__(var1.getlocal(23), var6);
                                             var6 = null;
                                          }

                                          var1.setline(179);
                                          var1.getlocal(14).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(22)})));
                                       }
                                       break;
                                    }

                                    var1.setline(165);
                                    var1.getlocal(14).__delitem__((PyObject)Py.newInteger(-1));
                                 }
                              } else {
                                 var1.setline(180);
                                 var6 = var1.getlocal(17);
                                 var10000 = var6._eq(PyString.fromInterned("class"));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(181);
                                    var6 = var1.getlocal(18);
                                    var7 = Py.unpackSequence(var6, 2);
                                    var8 = var7[0];
                                    var1.setlocal(21, var8);
                                    var8 = null;
                                    var8 = var7[1];
                                    var1.setlocal(22, var8);
                                    var8 = null;
                                    var6 = null;

                                    while(true) {
                                       var1.setline(183);
                                       var10000 = var1.getlocal(14);
                                       if (var10000.__nonzero__()) {
                                          var6 = var1.getlocal(14).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                                          var10000 = var6._ge(var1.getlocal(22));
                                          var6 = null;
                                       }

                                       if (!var10000.__nonzero__()) {
                                          var1.setline(185);
                                          var6 = var1.getlocal(15).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
                                          var7 = Py.unpackSequence(var6, 3);
                                          var8 = var7[0];
                                          var1.setlocal(16, var8);
                                          var8 = null;
                                          var8 = var7[1];
                                          var1.setlocal(25, var8);
                                          var8 = null;
                                          var8 = var7[2];
                                          var1.setlocal(18, var8);
                                          var8 = null;
                                          var6 = null;
                                          var1.setline(186);
                                          var6 = var1.getlocal(16);
                                          var10000 = var6._ne(var1.getglobal("NAME"));
                                          var6 = null;
                                          if (var10000.__nonzero__()) {
                                             break;
                                          }

                                          var1.setline(189);
                                          var6 = var1.getlocal(15).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
                                          var7 = Py.unpackSequence(var6, 3);
                                          var8 = var7[0];
                                          var1.setlocal(16, var8);
                                          var8 = null;
                                          var8 = var7[1];
                                          var1.setlocal(17, var8);
                                          var8 = null;
                                          var8 = var7[2];
                                          var1.setlocal(18, var8);
                                          var8 = null;
                                          var6 = null;
                                          var1.setline(190);
                                          var6 = var1.getglobal("None");
                                          var1.setlocal(26, var6);
                                          var6 = null;
                                          var1.setline(191);
                                          var6 = var1.getlocal(17);
                                          var10000 = var6._eq(PyString.fromInterned("("));
                                          var6 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(192);
                                             PyList var24 = new PyList(Py.EmptyObjects);
                                             var1.setlocal(27, var24);
                                             var6 = null;
                                             var1.setline(194);
                                             PyInteger var25 = Py.newInteger(1);
                                             var1.setlocal(28, var25);
                                             var6 = null;
                                             var1.setline(195);
                                             var24 = new PyList(Py.EmptyObjects);
                                             var1.setlocal(29, var24);
                                             var6 = null;

                                             while(true) {
                                                var1.setline(196);
                                                if (!var1.getglobal("True").__nonzero__()) {
                                                   break;
                                                }

                                                var1.setline(197);
                                                var6 = var1.getlocal(15).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
                                                var7 = Py.unpackSequence(var6, 3);
                                                var8 = var7[0];
                                                var1.setlocal(16, var8);
                                                var8 = null;
                                                var8 = var7[1];
                                                var1.setlocal(17, var8);
                                                var8 = null;
                                                var8 = var7[2];
                                                var1.setlocal(18, var8);
                                                var8 = null;
                                                var6 = null;
                                                var1.setline(198);
                                                var6 = var1.getlocal(17);
                                                var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned(")"), PyString.fromInterned(",")}));
                                                var6 = null;
                                                if (var10000.__nonzero__()) {
                                                   var6 = var1.getlocal(28);
                                                   var10000 = var6._eq(Py.newInteger(1));
                                                   var6 = null;
                                                }

                                                if (var10000.__nonzero__()) {
                                                   var1.setline(199);
                                                   var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(29));
                                                   var1.setlocal(30, var6);
                                                   var6 = null;
                                                   var1.setline(200);
                                                   var6 = var1.getlocal(30);
                                                   var10000 = var6._in(var1.getlocal(4));
                                                   var6 = null;
                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(202);
                                                      var6 = var1.getlocal(4).__getitem__(var1.getlocal(30));
                                                      var1.setlocal(30, var6);
                                                      var6 = null;
                                                   } else {
                                                      var1.setline(204);
                                                      var6 = var1.getlocal(30).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                                                      var1.setlocal(31, var6);
                                                      var6 = null;
                                                      var1.setline(205);
                                                      var6 = var1.getglobal("len").__call__(var2, var1.getlocal(31));
                                                      var10000 = var6._gt(Py.newInteger(1));
                                                      var6 = null;
                                                      if (var10000.__nonzero__()) {
                                                         var1.setline(209);
                                                         var6 = var1.getlocal(31).__getitem__(Py.newInteger(-2));
                                                         var1.setlocal(32, var6);
                                                         var6 = null;
                                                         var1.setline(210);
                                                         var6 = var1.getlocal(31).__getitem__(Py.newInteger(-1));
                                                         var1.setlocal(31, var6);
                                                         var6 = null;
                                                         var1.setline(211);
                                                         var6 = var1.getlocal(32);
                                                         var10000 = var6._in(var1.getglobal("_modules"));
                                                         var6 = null;
                                                         if (var10000.__nonzero__()) {
                                                            var1.setline(212);
                                                            var6 = var1.getglobal("_modules").__getitem__(var1.getlocal(32));
                                                            var1.setlocal(33, var6);
                                                            var6 = null;
                                                            var1.setline(213);
                                                            var6 = var1.getlocal(31);
                                                            var10000 = var6._in(var1.getlocal(33));
                                                            var6 = null;
                                                            if (var10000.__nonzero__()) {
                                                               var1.setline(214);
                                                               var6 = var1.getlocal(33).__getitem__(var1.getlocal(31));
                                                               var1.setlocal(30, var6);
                                                               var6 = null;
                                                            }
                                                         }
                                                      }
                                                   }

                                                   var1.setline(215);
                                                   var1.getlocal(27).__getattr__("append").__call__(var2, var1.getlocal(30));
                                                   var1.setline(216);
                                                   var24 = new PyList(Py.EmptyObjects);
                                                   var1.setlocal(29, var24);
                                                   var6 = null;
                                                }

                                                var1.setline(217);
                                                var6 = var1.getlocal(17);
                                                var10000 = var6._eq(PyString.fromInterned("("));
                                                var6 = null;
                                                if (var10000.__nonzero__()) {
                                                   var1.setline(218);
                                                   var6 = var1.getlocal(28);
                                                   var6 = var6._iadd(Py.newInteger(1));
                                                   var1.setlocal(28, var6);
                                                } else {
                                                   var1.setline(219);
                                                   var6 = var1.getlocal(17);
                                                   var10000 = var6._eq(PyString.fromInterned(")"));
                                                   var6 = null;
                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(220);
                                                      var6 = var1.getlocal(28);
                                                      var6 = var6._isub(Py.newInteger(1));
                                                      var1.setlocal(28, var6);
                                                      var1.setline(221);
                                                      var6 = var1.getlocal(28);
                                                      var10000 = var6._eq(Py.newInteger(0));
                                                      var6 = null;
                                                      if (var10000.__nonzero__()) {
                                                         break;
                                                      }
                                                   } else {
                                                      var1.setline(223);
                                                      var6 = var1.getlocal(17);
                                                      var10000 = var6._eq(PyString.fromInterned(","));
                                                      var6 = null;
                                                      if (var10000.__nonzero__()) {
                                                         var6 = var1.getlocal(28);
                                                         var10000 = var6._eq(Py.newInteger(1));
                                                         var6 = null;
                                                      }

                                                      if (var10000.__nonzero__()) {
                                                         var1.setline(224);
                                                      } else {
                                                         var1.setline(226);
                                                         var6 = var1.getlocal(16);
                                                         var10000 = var6._in(new PyTuple(new PyObject[]{var1.getglobal("NAME"), var1.getglobal("OP")}));
                                                         var6 = null;
                                                         if (var10000.__nonzero__()) {
                                                            var6 = var1.getlocal(28);
                                                            var10000 = var6._eq(Py.newInteger(1));
                                                            var6 = null;
                                                         }

                                                         if (var10000.__nonzero__()) {
                                                            var1.setline(227);
                                                            var1.getlocal(29).__getattr__("append").__call__(var2, var1.getlocal(17));
                                                         }
                                                      }
                                                   }
                                                }
                                             }

                                             var1.setline(229);
                                             var6 = var1.getlocal(27);
                                             var1.setlocal(26, var6);
                                             var6 = null;
                                          }

                                          var1.setline(230);
                                          var10000 = var1.getglobal("Class");
                                          var19 = new PyObject[]{var1.getlocal(3), var1.getlocal(25), var1.getlocal(26), var1.getlocal(10), var1.getlocal(21)};
                                          var6 = var10000.__call__(var2, var19);
                                          var1.setlocal(24, var6);
                                          var6 = null;
                                          var1.setline(232);
                                          if (var1.getlocal(14).__not__().__nonzero__()) {
                                             var1.setline(233);
                                             var6 = var1.getlocal(24);
                                             var1.getlocal(4).__setitem__(var1.getlocal(25), var6);
                                             var6 = null;
                                          }

                                          var1.setline(234);
                                          var1.getlocal(14).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(24), var1.getlocal(22)})));
                                          break;
                                       }

                                       var1.setline(184);
                                       var1.getlocal(14).__delitem__((PyObject)Py.newInteger(-1));
                                    }
                                 } else {
                                    var1.setline(235);
                                    var6 = var1.getlocal(17);
                                    var10000 = var6._eq(PyString.fromInterned("import"));
                                    var6 = null;
                                    if (var10000.__nonzero__()) {
                                       var6 = var1.getlocal(18).__getitem__(Py.newInteger(1));
                                       var10000 = var6._eq(Py.newInteger(0));
                                       var6 = null;
                                    }

                                    PyObject var9;
                                    PyObject[] var21;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(236);
                                       var6 = var1.getglobal("_getnamelist").__call__(var2, var1.getlocal(15));
                                       var1.setlocal(34, var6);
                                       var6 = null;
                                       var1.setline(237);
                                       var6 = var1.getlocal(34).__iter__();

                                       while(true) {
                                          var1.setline(237);
                                          var17 = var6.__iternext__();
                                          if (var17 == null) {
                                             break;
                                          }

                                          var21 = Py.unpackSequence(var17, 2);
                                          var9 = var21[0];
                                          var1.setlocal(35, var9);
                                          var9 = null;
                                          var9 = var21[1];
                                          var1.setlocal(36, var9);
                                          var9 = null;

                                          try {
                                             var1.setline(240);
                                             var8 = var1.getlocal(2);
                                             var10000 = var8._is(var1.getglobal("None"));
                                             var8 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(241);
                                                var1.getglobal("_readmodule").__call__(var2, var1.getlocal(35), var1.getlocal(1));
                                             } else {
                                                try {
                                                   var1.setline(244);
                                                   var1.getglobal("_readmodule").__call__(var2, var1.getlocal(35), var1.getlocal(1), var1.getlocal(2));
                                                } catch (Throwable var12) {
                                                   PyException var22 = Py.setException(var12, var1);
                                                   if (!var22.match(var1.getglobal("ImportError"))) {
                                                      throw var22;
                                                   }

                                                   var1.setline(246);
                                                   var1.getglobal("_readmodule").__call__((ThreadState)var2, (PyObject)var1.getlocal(35), (PyObject)(new PyList(Py.EmptyObjects)));
                                                }
                                             }
                                          } catch (Throwable var13) {
                                             Py.setException(var13, var1);
                                             var1.setline(250);
                                          }
                                       }
                                    } else {
                                       var1.setline(251);
                                       var6 = var1.getlocal(17);
                                       var10000 = var6._eq(PyString.fromInterned("from"));
                                       var6 = null;
                                       if (var10000.__nonzero__()) {
                                          var6 = var1.getlocal(18).__getitem__(Py.newInteger(1));
                                          var10000 = var6._eq(Py.newInteger(0));
                                          var6 = null;
                                       }

                                       if (var10000.__nonzero__()) {
                                          var1.setline(252);
                                          var6 = var1.getglobal("_getname").__call__(var2, var1.getlocal(15));
                                          var7 = Py.unpackSequence(var6, 2);
                                          var8 = var7[0];
                                          var1.setlocal(35, var8);
                                          var8 = null;
                                          var8 = var7[1];
                                          var1.setlocal(17, var8);
                                          var8 = null;
                                          var6 = null;
                                          var1.setline(253);
                                          var10000 = var1.getlocal(35).__not__();
                                          if (!var10000.__nonzero__()) {
                                             var6 = var1.getlocal(17);
                                             var10000 = var6._ne(PyString.fromInterned("import"));
                                             var6 = null;
                                          }

                                          if (!var10000.__nonzero__()) {
                                             var1.setline(255);
                                             var6 = var1.getglobal("_getnamelist").__call__(var2, var1.getlocal(15));
                                             var1.setlocal(27, var6);
                                             var6 = null;

                                             try {
                                                var1.setline(258);
                                                var6 = var1.getglobal("_readmodule").__call__(var2, var1.getlocal(35), var1.getlocal(1), var1.getlocal(2));
                                                var1.setlocal(33, var6);
                                                var6 = null;
                                             } catch (Throwable var11) {
                                                Py.setException(var11, var1);
                                                continue;
                                             }

                                             var1.setline(265);
                                             var6 = var1.getlocal(27).__iter__();

                                             while(true) {
                                                while(true) {
                                                   var1.setline(265);
                                                   var17 = var6.__iternext__();
                                                   if (var17 == null) {
                                                      continue label262;
                                                   }

                                                   var21 = Py.unpackSequence(var17, 2);
                                                   var9 = var21[0];
                                                   var1.setlocal(30, var9);
                                                   var9 = null;
                                                   var9 = var21[1];
                                                   var1.setlocal(37, var9);
                                                   var9 = null;
                                                   var1.setline(266);
                                                   var8 = var1.getlocal(30);
                                                   var10000 = var8._in(var1.getlocal(33));
                                                   var8 = null;
                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(267);
                                                      var8 = var1.getlocal(33).__getitem__(var1.getlocal(30));
                                                      var10000 = var1.getlocal(4);
                                                      PyObject var10001 = var1.getlocal(37);
                                                      if (!var10001.__nonzero__()) {
                                                         var10001 = var1.getlocal(30);
                                                      }

                                                      var10000.__setitem__(var10001, var8);
                                                      var8 = null;
                                                   } else {
                                                      var1.setline(268);
                                                      var8 = var1.getlocal(30);
                                                      var10000 = var8._eq(PyString.fromInterned("*"));
                                                      var8 = null;
                                                      if (var10000.__nonzero__()) {
                                                         var1.setline(270);
                                                         var8 = var1.getlocal(33).__iter__();

                                                         while(true) {
                                                            var1.setline(270);
                                                            var9 = var8.__iternext__();
                                                            if (var9 == null) {
                                                               break;
                                                            }

                                                            var1.setlocal(30, var9);
                                                            var1.setline(271);
                                                            PyObject var10 = var1.getlocal(30).__getitem__(Py.newInteger(0));
                                                            var10000 = var10._ne(PyString.fromInterned("_"));
                                                            var10 = null;
                                                            if (var10000.__nonzero__()) {
                                                               var1.setline(272);
                                                               var10 = var1.getlocal(33).__getitem__(var1.getlocal(30));
                                                               var1.getlocal(4).__setitem__(var1.getlocal(30), var10);
                                                               var10 = null;
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  } catch (Throwable var14) {
                     PyException var20 = Py.setException(var14, var1);
                     if (!var20.match(var1.getglobal("StopIteration"))) {
                        throw var20;
                     }

                     var1.setline(274);
                  }

                  var1.setline(276);
                  var1.getlocal(9).__getattr__("close").__call__(var2);
                  var1.setline(277);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _getnamelist$9(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      label35:
      while(true) {
         var1.setline(284);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(285);
         var6 = var1.getglobal("_getname").__call__(var2, var1.getlocal(0));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(286);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(288);
         var6 = var1.getlocal(3);
         PyObject var10000 = var6._eq(PyString.fromInterned("as"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(289);
            var6 = var1.getglobal("_getname").__call__(var2, var1.getlocal(0));
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(291);
            var6 = var1.getglobal("None");
            var1.setlocal(4, var6);
            var3 = null;
         }

         var1.setline(292);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));

         while(true) {
            var1.setline(293);
            var6 = var1.getlocal(3);
            var10000 = var6._ne(PyString.fromInterned(","));
            var3 = null;
            if (var10000.__nonzero__()) {
               PyString var7 = PyString.fromInterned("\n");
               var10000 = var7._notin(var1.getlocal(3));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(295);
               var6 = var1.getlocal(3);
               var10000 = var6._ne(PyString.fromInterned(","));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break label35;
               }
               break;
            }

            var1.setline(294);
            var6 = var1.getlocal(0).__getattr__("next").__call__(var2).__getitem__(Py.newInteger(1));
            var1.setlocal(3, var6);
            var3 = null;
         }
      }

      var1.setline(297);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _getname$10(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(304);
      PyObject var7 = var1.getlocal(0).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(305);
      var7 = var1.getlocal(2);
      PyObject var10000 = var7._ne(var1.getglobal("NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(3);
         var10000 = var7._ne(PyString.fromInterned("*"));
         var3 = null;
      }

      PyTuple var9;
      if (var10000.__nonzero__()) {
         var1.setline(306);
         var9 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(307);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));

         while(true) {
            var1.setline(308);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(309);
            PyObject var8 = var1.getlocal(0).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
            PyObject[] var10 = Py.unpackSequence(var8, 2);
            PyObject var6 = var10[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(3, var6);
            var6 = null;
            var4 = null;
            var1.setline(310);
            var8 = var1.getlocal(3);
            var10000 = var8._ne(PyString.fromInterned("."));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(312);
            var8 = var1.getlocal(0).__getattr__("next").__call__(var2).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
            var10 = Py.unpackSequence(var8, 2);
            var6 = var10[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(3, var6);
            var6 = null;
            var4 = null;
            var1.setline(313);
            var8 = var1.getlocal(2);
            var10000 = var8._ne(var1.getglobal("NAME"));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(315);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         }

         var1.setline(316);
         var9 = new PyTuple(new PyObject[]{PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(1)), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject _main$11(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(321);
      var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(322);
      PyList var9;
      if (var1.getlocal(0).__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(323);
         var9 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(1))});
         var1.setlocal(2, var9);
         var3 = null;
         var1.setline(324);
         var3 = var1.getlocal(0).__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(325);
         if (var1.getlocal(1).__getattr__("lower").__call__(var2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py")).__nonzero__()) {
            var1.setline(326);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         }
      } else {
         var1.setline(328);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var9);
         var3 = null;
      }

      var1.setline(329);
      var3 = var1.getglobal("readmodule_ex").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getlocal(3).__getattr__("values").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(331);
      PyObject var10000 = var1.getlocal(4).__getattr__("sort");
      var1.setline(331);
      PyObject[] var13 = Py.EmptyObjects;
      var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var13, f$12)));
      var1.setline(333);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         while(true) {
            var1.setline(333);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(5, var4);
            var1.setline(334);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Class")).__nonzero__()) {
               var1.setline(335);
               Py.printComma(PyString.fromInterned("class"));
               Py.printComma(var1.getlocal(5).__getattr__("name"));
               Py.printComma(var1.getlocal(5).__getattr__("super"));
               Py.println(var1.getlocal(5).__getattr__("lineno"));
               var1.setline(336);
               var10000 = var1.getglobal("sorted");
               PyObject[] var5 = new PyObject[]{var1.getlocal(5).__getattr__("methods").__getattr__("iteritems").__call__(var2), var1.getglobal("itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))};
               String[] var6 = new String[]{"key"};
               var10000 = var10000.__call__(var2, var5, var6);
               var5 = null;
               PyObject var10 = var10000;
               var1.setlocal(6, var10);
               var5 = null;
               var1.setline(337);
               var10 = var1.getlocal(6).__iter__();

               while(true) {
                  var1.setline(337);
                  PyObject var11 = var10.__iternext__();
                  if (var11 == null) {
                     break;
                  }

                  PyObject[] var7 = Py.unpackSequence(var11, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var1.setline(338);
                  PyObject var12 = var1.getlocal(7);
                  var10000 = var12._ne(PyString.fromInterned("__path__"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(339);
                     Py.printComma(PyString.fromInterned("  def"));
                     Py.printComma(var1.getlocal(7));
                     Py.println(var1.getlocal(8));
                  }
               }
            } else {
               var1.setline(340);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Function")).__nonzero__()) {
                  var1.setline(341);
                  Py.printComma(PyString.fromInterned("def"));
                  Py.printComma(var1.getlocal(5).__getattr__("name"));
                  Py.println(var1.getlocal(5).__getattr__("lineno"));
               }
            }
         }
      }
   }

   public PyObject f$12(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("lineno"), (PyObject)Py.newInteger(0)), var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("lineno"), (PyObject)Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public pyclbr$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Class$1 = Py.newCode(0, var2, var1, "Class", 53, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "name", "super", "file", "lineno"};
      __init__$2 = Py.newCode(6, var2, var1, "__init__", 55, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "lineno"};
      _addmethod$3 = Py.newCode(3, var2, var1, "_addmethod", 65, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Function$4 = Py.newCode(0, var2, var1, "Function", 68, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "name", "file", "lineno"};
      __init__$5 = Py.newCode(5, var2, var1, "__init__", 70, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "path", "res", "key", "value"};
      readmodule$6 = Py.newCode(2, var2, var1, "readmodule", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "path"};
      readmodule_ex$7 = Py.newCode(2, var2, var1, "readmodule_ex", 88, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "path", "inpackage", "fullmodule", "dict", "i", "package", "submodule", "parent", "f", "fname", "_s", "_m", "ty", "stack", "g", "tokentype", "token", "start", "_end", "_line", "lineno", "thisindent", "meth_name", "cur_class", "class_name", "inherit", "names", "level", "super", "n", "c", "m", "d", "modules", "mod", "_mod2", "n2"};
      _readmodule$8 = Py.newCode(3, var2, var1, "_readmodule", 97, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"g", "names", "name", "token", "name2"};
      _getnamelist$9 = Py.newCode(1, var2, var1, "_getnamelist", 279, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"g", "parts", "tokentype", "token"};
      _getname$10 = Py.newCode(1, var2, var1, "_getname", 299, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"os", "mod", "path", "dict", "objs", "obj", "methods", "name", "lineno"};
      _main$11 = Py.newCode(0, var2, var1, "_main", 318, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      f$12 = Py.newCode(2, var2, var1, "<lambda>", 331, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pyclbr$py("pyclbr$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pyclbr$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Class$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._addmethod$3(var2, var3);
         case 4:
            return this.Function$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.readmodule$6(var2, var3);
         case 7:
            return this.readmodule_ex$7(var2, var3);
         case 8:
            return this._readmodule$8(var2, var3);
         case 9:
            return this._getnamelist$9(var2, var3);
         case 10:
            return this._getname$10(var2, var3);
         case 11:
            return this._main$11(var2, var3);
         case 12:
            return this.f$12(var2, var3);
         default:
            return null;
      }
   }
}

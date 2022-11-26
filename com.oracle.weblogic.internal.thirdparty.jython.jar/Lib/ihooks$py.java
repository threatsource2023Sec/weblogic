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
@Filename("ihooks.py")
public class ihooks$py extends PyFunctionTable implements PyRunnable {
   static ihooks$py self;
   static final PyCode f$0;
   static final PyCode _Verbose$1;
   static final PyCode __init__$2;
   static final PyCode get_verbose$3;
   static final PyCode set_verbose$4;
   static final PyCode note$5;
   static final PyCode message$6;
   static final PyCode BasicModuleLoader$7;
   static final PyCode find_module$8;
   static final PyCode default_path$9;
   static final PyCode find_module_in_dir$10;
   static final PyCode find_builtin_module$11;
   static final PyCode load_module$12;
   static final PyCode Hooks$13;
   static final PyCode get_suffixes$14;
   static final PyCode new_module$15;
   static final PyCode is_builtin$16;
   static final PyCode init_builtin$17;
   static final PyCode is_frozen$18;
   static final PyCode init_frozen$19;
   static final PyCode get_frozen_object$20;
   static final PyCode load_source$21;
   static final PyCode load_compiled$22;
   static final PyCode load_dynamic$23;
   static final PyCode load_package$24;
   static final PyCode add_module$25;
   static final PyCode modules_dict$26;
   static final PyCode default_path$27;
   static final PyCode path_split$28;
   static final PyCode path_join$29;
   static final PyCode path_isabs$30;
   static final PyCode path_exists$31;
   static final PyCode path_isdir$32;
   static final PyCode path_isfile$33;
   static final PyCode path_islink$34;
   static final PyCode openfile$35;
   static final PyCode listdir$36;
   static final PyCode ModuleLoader$37;
   static final PyCode __init__$38;
   static final PyCode default_path$39;
   static final PyCode modules_dict$40;
   static final PyCode get_hooks$41;
   static final PyCode set_hooks$42;
   static final PyCode find_builtin_module$43;
   static final PyCode find_module_in_dir$44;
   static final PyCode load_module$45;
   static final PyCode FancyModuleLoader$46;
   static final PyCode load_module$47;
   static final PyCode BasicModuleImporter$48;
   static final PyCode __init__$49;
   static final PyCode get_loader$50;
   static final PyCode set_loader$51;
   static final PyCode get_hooks$52;
   static final PyCode set_hooks$53;
   static final PyCode import_module$54;
   static final PyCode reload$55;
   static final PyCode unload$56;
   static final PyCode install$57;
   static final PyCode uninstall$58;
   static final PyCode ModuleImporter$59;
   static final PyCode import_module$60;
   static final PyCode determine_parent$61;
   static final PyCode find_head_package$62;
   static final PyCode load_tail$63;
   static final PyCode ensure_fromlist$64;
   static final PyCode import_it$65;
   static final PyCode reload$66;
   static final PyCode install$67;
   static final PyCode uninstall$68;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Import hook support.\n\nConsistent use of this module will make it possible to change the\ndifferent mechanisms involved in loading modules independently.\n\nWhile the built-in module imp exports interfaces to the built-in\nmodule searching and loading algorithm, and it is possible to replace\nthe built-in function __import__ in order to change the semantics of\nthe import statement, until now it has been difficult to combine the\neffect of different __import__ hacks, like loading modules from URLs\nby rimport.py, or restricted execution by rexec.py.\n\nThis module defines three new concepts:\n\n1) A \"file system hooks\" class provides an interface to a filesystem.\n\nOne hooks class is defined (Hooks), which uses the interface provided\nby standard modules os and os.path.  It should be used as the base\nclass for other hooks classes.\n\n2) A \"module loader\" class provides an interface to search for a\nmodule in a search path and to load it.  It defines a method which\nsearches for a module in a single directory; by overriding this method\none can redefine the details of the search.  If the directory is None,\nbuilt-in and frozen modules are searched instead.\n\nTwo module loader class are defined, both implementing the search\nstrategy used by the built-in __import__ function: ModuleLoader uses\nthe imp module's find_module interface, while HookableModuleLoader\nuses a file system hooks class to interact with the file system.  Both\nuse the imp module's load_* interfaces to actually load the module.\n\n3) A \"module importer\" class provides an interface to import a\nmodule, as well as interfaces to reload and unload a module.  It also\nprovides interfaces to install and uninstall itself instead of the\ndefault __import__ and reload (and unload) functions.\n\nOne module importer class is defined (ModuleImporter), which uses a\nmodule loader instance passed in (by default HookableModuleLoader is\ninstantiated).\n\nThe classes defined here should be used as base classes for extended\nfunctionality along those lines.\n\nIf a module importer class supports dotted names, its import_module()\nmust return a different value depending on whether it is called on\nbehalf of a \"from ... import ...\" statement or not.  (This is caused\nby the way the __import__ hook is used by the Python interpreter.)  It\nwould also do wise to install a different version of reload().\n\n"));
      var1.setline(51);
      PyString.fromInterned("Import hook support.\n\nConsistent use of this module will make it possible to change the\ndifferent mechanisms involved in loading modules independently.\n\nWhile the built-in module imp exports interfaces to the built-in\nmodule searching and loading algorithm, and it is possible to replace\nthe built-in function __import__ in order to change the semantics of\nthe import statement, until now it has been difficult to combine the\neffect of different __import__ hacks, like loading modules from URLs\nby rimport.py, or restricted execution by rexec.py.\n\nThis module defines three new concepts:\n\n1) A \"file system hooks\" class provides an interface to a filesystem.\n\nOne hooks class is defined (Hooks), which uses the interface provided\nby standard modules os and os.path.  It should be used as the base\nclass for other hooks classes.\n\n2) A \"module loader\" class provides an interface to search for a\nmodule in a search path and to load it.  It defines a method which\nsearches for a module in a single directory; by overriding this method\none can redefine the details of the search.  If the directory is None,\nbuilt-in and frozen modules are searched instead.\n\nTwo module loader class are defined, both implementing the search\nstrategy used by the built-in __import__ function: ModuleLoader uses\nthe imp module's find_module interface, while HookableModuleLoader\nuses a file system hooks class to interact with the file system.  Both\nuse the imp module's load_* interfaces to actually load the module.\n\n3) A \"module importer\" class provides an interface to import a\nmodule, as well as interfaces to reload and unload a module.  It also\nprovides interfaces to install and uninstall itself instead of the\ndefault __import__ and reload (and unload) functions.\n\nOne module importer class is defined (ModuleImporter), which uses a\nmodule loader instance passed in (by default HookableModuleLoader is\ninstantiated).\n\nThe classes defined here should be used as base classes for extended\nfunctionality along those lines.\n\nIf a module importer class supports dotted names, its import_module()\nmust return a different value depending on whether it is called on\nbehalf of a \"from ... import ...\" statement or not.  (This is caused\nby the way the __import__ hook is used by the Python interpreter.)  It\nwould also do wise to install a different version of reload().\n\n");
      var1.setline(52);
      String[] var3 = new String[]{"warnpy3k", "warn"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(53);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the ihooks module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(54);
      var1.dellocal("warnpy3k");
      var1.setline(56);
      PyObject var6 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var6);
      var3 = null;
      var1.setline(57);
      var6 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var6);
      var3 = null;
      var1.setline(58);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(59);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(61);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("BasicModuleLoader"), PyString.fromInterned("Hooks"), PyString.fromInterned("ModuleLoader"), PyString.fromInterned("FancyModuleLoader"), PyString.fromInterned("BasicModuleImporter"), PyString.fromInterned("ModuleImporter"), PyString.fromInterned("install"), PyString.fromInterned("uninstall")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(64);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal("VERBOSE", var9);
      var3 = null;
      var1.setline(67);
      var3 = new String[]{"C_EXTENSION", "PY_SOURCE", "PY_COMPILED"};
      var5 = imp.importFrom("imp", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("C_EXTENSION", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("PY_SOURCE", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("PY_COMPILED", var4);
      var4 = null;
      var1.setline(68);
      var3 = new String[]{"C_BUILTIN", "PY_FROZEN", "PKG_DIRECTORY"};
      var5 = imp.importFrom("imp", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("C_BUILTIN", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("PY_FROZEN", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("PKG_DIRECTORY", var4);
      var4 = null;
      var1.setline(69);
      var6 = var1.getname("C_BUILTIN");
      var1.setlocal("BUILTIN_MODULE", var6);
      var3 = null;
      var1.setline(70);
      var6 = var1.getname("PY_FROZEN");
      var1.setlocal("FROZEN_MODULE", var6);
      var3 = null;
      var1.setline(73);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("_Verbose", var5, _Verbose$1);
      var1.setlocal("_Verbose", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(97);
      var5 = new PyObject[]{var1.getname("_Verbose")};
      var4 = Py.makeClass("BasicModuleLoader", var5, BasicModuleLoader$7);
      var1.setlocal("BasicModuleLoader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(150);
      var5 = new PyObject[]{var1.getname("_Verbose")};
      var4 = Py.makeClass("Hooks", var5, Hooks$13);
      var1.setlocal("Hooks", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(206);
      var5 = new PyObject[]{var1.getname("BasicModuleLoader")};
      var4 = Py.makeClass("ModuleLoader", var5, ModuleLoader$37);
      var1.setlocal("ModuleLoader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(286);
      var5 = new PyObject[]{var1.getname("ModuleLoader")};
      var4 = Py.makeClass("FancyModuleLoader", var5, FancyModuleLoader$46);
      var1.setlocal("FancyModuleLoader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(337);
      var5 = new PyObject[]{var1.getname("_Verbose")};
      var4 = Py.makeClass("BasicModuleImporter", var5, BasicModuleImporter$48);
      var1.setlocal("BasicModuleImporter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(400);
      var5 = new PyObject[]{var1.getname("BasicModuleImporter")};
      var4 = Py.makeClass("ModuleImporter", var5, ModuleImporter$59);
      var1.setlocal("ModuleImporter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(544);
      var6 = var1.getname("None");
      var1.setlocal("default_importer", var6);
      var3 = null;
      var1.setline(545);
      var6 = var1.getname("None");
      var1.setlocal("current_importer", var6);
      var3 = null;
      var1.setline(547);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var10 = new PyFunction(var1.f_globals, var5, install$67, (PyObject)null);
      var1.setlocal("install", var10);
      var3 = null;
      var1.setline(552);
      var5 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var5, uninstall$68, (PyObject)null);
      var1.setlocal("uninstall", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Verbose$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = new PyObject[]{var1.getname("VERBOSE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_verbose$3, (PyObject)null);
      var1.setlocal("get_verbose", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_verbose$4, (PyObject)null);
      var1.setlocal("set_verbose", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, note$5, (PyObject)null);
      var1.setlocal("note", var4);
      var3 = null;
      var1.setline(90);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, message$6, (PyObject)null);
      var1.setlocal("message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("verbose", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_verbose$3(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getlocal(0).__getattr__("verbose");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_verbose$4(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("verbose", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject note$5(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      if (var1.getlocal(0).__getattr__("verbose").__nonzero__()) {
         var1.setline(88);
         PyObject var10000 = var1.getlocal(0).__getattr__("message");
         PyObject[] var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject message$6(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(92);
         Py.println(var1.getlocal(1)._mod(var1.getlocal(2)));
      } else {
         var1.setline(94);
         Py.println(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BasicModuleLoader$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic module loader.\n\n    This provides the same functionality as built-in import.  It\n    doesn't deal with checking sys.modules -- all it provides is\n    find_module() and a load_module(), as well as find_module_in_dir()\n    which searches just one directory, and can be overridden by a\n    derived class to change the module search algorithm when the basic\n    dependency on sys.path is unchanged.\n\n    The interface is a little more convenient than imp's:\n    find_module(name, [path]) returns None or 'stuff', and\n    load_module(name, stuff) loads the module.\n\n    "));
      var1.setline(112);
      PyString.fromInterned("Basic module loader.\n\n    This provides the same functionality as built-in import.  It\n    doesn't deal with checking sys.modules -- all it provides is\n    find_module() and a load_module(), as well as find_module_in_dir()\n    which searches just one directory, and can be overridden by a\n    derived class to change the module search algorithm when the basic\n    dependency on sys.path is unchanged.\n\n    The interface is a little more convenient than imp's:\n    find_module(name, [path]) returns None or 'stuff', and\n    load_module(name, stuff) loads the module.\n\n    ");
      var1.setline(114);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, find_module$8, (PyObject)null);
      var1.setlocal("find_module", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, default_path$9, (PyObject)null);
      var1.setlocal("default_path", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_module_in_dir$10, (PyObject)null);
      var1.setlocal("find_module_in_dir", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_builtin_module$11, (PyObject)null);
      var1.setlocal("find_builtin_module", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_module$12, (PyObject)null);
      var1.setlocal("load_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject find_module$8(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._add(var1.getlocal(0).__getattr__("default_path").__call__(var2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(117);
      var3 = var1.getlocal(2).__iter__();

      PyObject var5;
      do {
         var1.setline(117);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(120);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(118);
         var5 = var1.getlocal(0).__getattr__("find_module_in_dir").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(119);
      } while(!var1.getlocal(4).__nonzero__());

      var1.setline(119);
      var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject default_path$9(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("sys").__getattr__("path");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_module_in_dir$10(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var3 = var1.getlocal(0).__getattr__("find_builtin_module").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(130);
            var3 = var1.getglobal("imp").__getattr__("find_module").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("ImportError"))) {
               var1.setline(132);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject find_builtin_module$11(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyTuple var4;
      if (var1.getglobal("imp").__getattr__("is_builtin").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(137);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("BUILTIN_MODULE")})});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(138);
         if (var1.getglobal("imp").__getattr__("is_frozen").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(139);
            var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("FROZEN_MODULE")})});
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(140);
            PyObject var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject load_module$12(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = var1.getlocal(2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var3 = null;

      Throwable var10000;
      label34: {
         boolean var10001;
         PyObject var9;
         try {
            var1.setline(145);
            var9 = var1.getglobal("imp").__getattr__("load_module").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label34;
         }

         var1.setline(147);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(147);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         }

         try {
            var1.f_lasti = -1;
            return var9;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      Throwable var8 = var10000;
      Py.addTraceback(var8, var1);
      var1.setline(147);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(147);
         var1.getlocal(3).__getattr__("close").__call__(var2);
      }

      throw (Throwable)var8;
   }

   public PyObject Hooks$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Hooks into the filesystem and interpreter.\n\n    By deriving a subclass you can redefine your filesystem interface,\n    e.g. to merge it with the URL space.\n\n    This base class behaves just like the native filesystem.\n\n    "));
      var1.setline(159);
      PyString.fromInterned("Hooks into the filesystem and interpreter.\n\n    By deriving a subclass you can redefine your filesystem interface,\n    e.g. to merge it with the URL space.\n\n    This base class behaves just like the native filesystem.\n\n    ");
      var1.setline(162);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, get_suffixes$14, (PyObject)null);
      var1.setlocal("get_suffixes", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_module$15, (PyObject)null);
      var1.setlocal("new_module", var4);
      var3 = null;
      var1.setline(164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_builtin$16, (PyObject)null);
      var1.setlocal("is_builtin", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init_builtin$17, (PyObject)null);
      var1.setlocal("init_builtin", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_frozen$18, (PyObject)null);
      var1.setlocal("is_frozen", var4);
      var3 = null;
      var1.setline(167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init_frozen$19, (PyObject)null);
      var1.setlocal("init_frozen", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_frozen_object$20, (PyObject)null);
      var1.setlocal("get_frozen_object", var4);
      var3 = null;
      var1.setline(169);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, load_source$21, (PyObject)null);
      var1.setlocal("load_source", var4);
      var3 = null;
      var1.setline(171);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, load_compiled$22, (PyObject)null);
      var1.setlocal("load_compiled", var4);
      var3 = null;
      var1.setline(173);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, load_dynamic$23, (PyObject)null);
      var1.setlocal("load_dynamic", var4);
      var3 = null;
      var1.setline(175);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, load_package$24, (PyObject)null);
      var1.setlocal("load_package", var4);
      var3 = null;
      var1.setline(178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_module$25, (PyObject)null);
      var1.setlocal("add_module", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, modules_dict$26, (PyObject)null);
      var1.setlocal("modules_dict", var4);
      var3 = null;
      var1.setline(186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, default_path$27, (PyObject)null);
      var1.setlocal("default_path", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_split$28, (PyObject)null);
      var1.setlocal("path_split", var4);
      var3 = null;
      var1.setline(189);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_join$29, (PyObject)null);
      var1.setlocal("path_join", var4);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_isabs$30, (PyObject)null);
      var1.setlocal("path_isabs", var4);
      var3 = null;
      var1.setline(193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_exists$31, (PyObject)null);
      var1.setlocal("path_exists", var4);
      var3 = null;
      var1.setline(194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_isdir$32, (PyObject)null);
      var1.setlocal("path_isdir", var4);
      var3 = null;
      var1.setline(195);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_isfile$33, (PyObject)null);
      var1.setlocal("path_isfile", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_islink$34, (PyObject)null);
      var1.setlocal("path_islink", var4);
      var3 = null;
      var1.setline(199);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, openfile$35, (PyObject)null);
      var1.setlocal("openfile", var4);
      var3 = null;
      var1.setline(200);
      PyObject var5 = var1.getname("IOError");
      var1.setlocal("openfile_error", var5);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listdir$36, (PyObject)null);
      var1.setlocal("listdir", var4);
      var3 = null;
      var1.setline(202);
      var5 = var1.getname("os").__getattr__("error");
      var1.setlocal("listdir_error", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get_suffixes$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getglobal("imp").__getattr__("get_suffixes").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject new_module$15(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getglobal("imp").__getattr__("new_module").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_builtin$16(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getglobal("imp").__getattr__("is_builtin").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject init_builtin$17(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getglobal("imp").__getattr__("init_builtin").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_frozen$18(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyObject var3 = var1.getglobal("imp").__getattr__("is_frozen").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject init_frozen$19(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject var3 = var1.getglobal("imp").__getattr__("init_frozen").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_frozen_object$20(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyObject var3 = var1.getglobal("imp").__getattr__("get_frozen_object").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_source$21(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getglobal("imp").__getattr__("load_source").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_compiled$22(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getglobal("imp").__getattr__("load_compiled").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_dynamic$23(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getglobal("imp").__getattr__("load_dynamic").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_package$24(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyObject var3 = var1.getglobal("imp").__getattr__("load_module").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("PKG_DIRECTORY")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_module$25(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyObject var3 = var1.getlocal(0).__getattr__("modules_dict").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(180);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(180);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(181);
         PyObject var4 = var1.getlocal(0).__getattr__("new_module").__call__(var2, var1.getlocal(1));
         var1.getlocal(2).__setitem__(var1.getlocal(1), var4);
         var1.setlocal(3, var4);
         var1.setline(182);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject modules_dict$26(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject default_path$27(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getglobal("sys").__getattr__("path");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_split$28(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_join$29(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_isabs$30(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_exists$31(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_isdir$32(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_isfile$33(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_islink$34(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject openfile$35(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var10000 = var1.getglobal("open");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject listdir$36(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ModuleLoader$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Default module loader; uses file system hooks.\n\n    By defining suitable hooks, you might be able to load modules from\n    other sources than the file system, e.g. from compressed or\n    encrypted files, tar files or (if you're brave!) URLs.\n\n    "));
      var1.setline(214);
      PyString.fromInterned("Default module loader; uses file system hooks.\n\n    By defining suitable hooks, you might be able to load modules from\n    other sources than the file system, e.g. from compressed or\n    encrypted files, tar files or (if you're brave!) URLs.\n\n    ");
      var1.setline(216);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("VERBOSE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$38, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, default_path$39, (PyObject)null);
      var1.setlocal("default_path", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, modules_dict$40, (PyObject)null);
      var1.setlocal("modules_dict", var4);
      var3 = null;
      var1.setline(226);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_hooks$41, (PyObject)null);
      var1.setlocal("get_hooks", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_hooks$42, (PyObject)null);
      var1.setlocal("set_hooks", var4);
      var3 = null;
      var1.setline(232);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_builtin_module$43, (PyObject)null);
      var1.setlocal("find_builtin_module", var4);
      var3 = null;
      var1.setline(240);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, find_module_in_dir$44, (PyObject)null);
      var1.setlocal("find_module_in_dir", var4);
      var3 = null;
      var1.setline(261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_module$45, (PyObject)null);
      var1.setlocal("load_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$38(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      var1.getglobal("BasicModuleLoader").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(218);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("Hooks").__call__(var2, var1.getlocal(2));
      }

      PyObject var3 = var10000;
      var1.getlocal(0).__setattr__("hooks", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default_path$39(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyObject var3 = var1.getlocal(0).__getattr__("hooks").__getattr__("default_path").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject modules_dict$40(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject var3 = var1.getlocal(0).__getattr__("hooks").__getattr__("modules_dict").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_hooks$41(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyObject var3 = var1.getlocal(0).__getattr__("hooks");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_hooks$42(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("hooks", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_builtin_module$43(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyTuple var4;
      if (var1.getlocal(0).__getattr__("hooks").__getattr__("is_builtin").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(235);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("BUILTIN_MODULE")})});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(236);
         if (var1.getlocal(0).__getattr__("hooks").__getattr__("is_frozen").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(237);
            var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("FROZEN_MODULE")})});
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(238);
            PyObject var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject find_module_in_dir$44(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(242);
         var3 = var1.getlocal(0).__getattr__("find_builtin_module").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(243);
         PyObject var4;
         PyTuple var10;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(244);
            var4 = var1.getlocal(0).__getattr__("hooks").__getattr__("path_join").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(245);
            if (var1.getlocal(0).__getattr__("hooks").__getattr__("path_isdir").__call__(var2, var1.getlocal(4)).__nonzero__()) {
               var1.setline(246);
               var4 = var1.getlocal(0).__getattr__("find_module_in_dir").__call__((ThreadState)var2, PyString.fromInterned("__init__"), (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(247);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(248);
                  var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(249);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(249);
                     var1.getlocal(6).__getattr__("close").__call__(var2);
                  }

                  var1.setline(250);
                  var10 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(4), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getglobal("PKG_DIRECTORY")})});
                  var1.f_lasti = -1;
                  return var10;
               }
            }
         }

         var1.setline(251);
         var4 = var1.getlocal(0).__getattr__("hooks").__getattr__("get_suffixes").__call__(var2).__iter__();

         while(true) {
            var1.setline(251);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(259);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(7, var5);
            var1.setline(252);
            PyObject var6 = var1.getlocal(7);
            PyObject[] var7 = Py.unpackSequence(var6, 3);
            PyObject var8 = var7[0];
            var1.setlocal(8, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(9, var8);
            var8 = null;
            var8 = var7[2];
            var1.setlocal(10, var8);
            var8 = null;
            var6 = null;
            var1.setline(253);
            var6 = var1.getlocal(0).__getattr__("hooks").__getattr__("path_join").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(var1.getlocal(8)));
            var1.setlocal(4, var6);
            var6 = null;

            try {
               var1.setline(255);
               var6 = var1.getlocal(0).__getattr__("hooks").__getattr__("openfile").__call__(var2, var1.getlocal(4), var1.getlocal(9));
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(256);
               var10 = new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(4), var1.getlocal(7)});
               var1.f_lasti = -1;
               return var10;
            } catch (Throwable var9) {
               PyException var11 = Py.setException(var9, var1);
               if (!var11.match(var1.getlocal(0).__getattr__("hooks").__getattr__("openfile_error"))) {
                  throw var11;
               }

               var1.setline(258);
            }
         }
      }
   }

   public PyObject load_module$45(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyObject var3 = var1.getlocal(2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(263);
      var3 = var1.getlocal(5);
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var3 = null;

      Throwable var10000;
      label82: {
         PyObject var11;
         PyObject var13;
         boolean var10001;
         label85: {
            try {
               var1.setline(265);
               var11 = var1.getlocal(8);
               var13 = var11._eq(var1.getglobal("BUILTIN_MODULE"));
               var4 = null;
               if (!var13.__nonzero__()) {
                  break label85;
               }

               var1.setline(266);
               var11 = var1.getlocal(0).__getattr__("hooks").__getattr__("init_builtin").__call__(var2, var1.getlocal(1));
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break label82;
            }

            var1.setline(281);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(281);
               var1.getlocal(3).__getattr__("close").__call__(var2);
            }

            try {
               var1.f_lasti = -1;
               return var11;
            } catch (Throwable var6) {
               var10000 = var6;
               var10001 = false;
               break label82;
            }
         }

         label86: {
            try {
               var1.setline(267);
               var5 = var1.getlocal(8);
               var13 = var5._eq(var1.getglobal("FROZEN_MODULE"));
               var5 = null;
               if (!var13.__nonzero__()) {
                  break label86;
               }

               var1.setline(268);
               var11 = var1.getlocal(0).__getattr__("hooks").__getattr__("init_frozen").__call__(var2, var1.getlocal(1));
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label82;
            }

            var1.setline(281);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(281);
               var1.getlocal(3).__getattr__("close").__call__(var2);
            }

            try {
               var1.f_lasti = -1;
               return var11;
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label82;
            }
         }

         try {
            var1.setline(269);
            var5 = var1.getlocal(8);
            var13 = var5._eq(var1.getglobal("C_EXTENSION"));
            var5 = null;
            if (var13.__nonzero__()) {
               var1.setline(270);
               var5 = var1.getlocal(0).__getattr__("hooks").__getattr__("load_dynamic").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
               var1.setlocal(9, var5);
               var5 = null;
            } else {
               var1.setline(271);
               var5 = var1.getlocal(8);
               var13 = var5._eq(var1.getglobal("PY_SOURCE"));
               var5 = null;
               if (var13.__nonzero__()) {
                  var1.setline(272);
                  var5 = var1.getlocal(0).__getattr__("hooks").__getattr__("load_source").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
                  var1.setlocal(9, var5);
                  var5 = null;
               } else {
                  var1.setline(273);
                  var5 = var1.getlocal(8);
                  var13 = var5._eq(var1.getglobal("PY_COMPILED"));
                  var5 = null;
                  if (var13.__nonzero__()) {
                     var1.setline(274);
                     var5 = var1.getlocal(0).__getattr__("hooks").__getattr__("load_compiled").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
                     var1.setlocal(9, var5);
                     var5 = null;
                  } else {
                     var1.setline(275);
                     var5 = var1.getlocal(8);
                     var13 = var5._eq(var1.getglobal("PKG_DIRECTORY"));
                     var5 = null;
                     if (!var13.__nonzero__()) {
                        var1.setline(278);
                        throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("Unrecognized module type (%r) for %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(1)})));
                     }

                     var1.setline(276);
                     var5 = var1.getlocal(0).__getattr__("hooks").__getattr__("load_package").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
                     var1.setlocal(9, var5);
                     var5 = null;
                  }
               }
            }
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label82;
         }

         var1.setline(281);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(281);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         }

         var1.setline(282);
         var3 = var1.getlocal(4);
         var1.getlocal(9).__setattr__("__file__", var3);
         var3 = null;
         var1.setline(283);
         var11 = var1.getlocal(9);
         var1.f_lasti = -1;
         return var11;
      }

      Throwable var12 = var10000;
      Py.addTraceback(var12, var1);
      var1.setline(281);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(281);
         var1.getlocal(3).__getattr__("close").__call__(var2);
      }

      throw (Throwable)var12;
   }

   public PyObject FancyModuleLoader$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Fancy module loader -- parses and execs the code itself."));
      var1.setline(288);
      PyString.fromInterned("Fancy module loader -- parses and execs the code itself.");
      var1.setline(290);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, load_module$47, (PyObject)null);
      var1.setlocal("load_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject load_module$47(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getlocal(2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      PyObject[] var6 = Py.unpackSequence(var5, 3);
      PyObject var7 = var6[0];
      var1.setlocal(5, var7);
      var7 = null;
      var7 = var6[1];
      var1.setlocal(6, var7);
      var7 = null;
      var7 = var6[2];
      var1.setlocal(7, var7);
      var7 = null;
      var5 = null;
      var3 = null;
      var1.setline(292);
      var3 = var1.getlocal(4);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(293);
      var3 = var1.getname("None");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(295);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._eq(var1.getname("PKG_DIRECTORY"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(296);
         var3 = var1.getlocal(0).__getattr__("find_module_in_dir").__call__((ThreadState)var2, PyString.fromInterned("__init__"), (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(297);
         if (var1.getlocal(10).__not__().__nonzero__()) {
            var1.setline(298);
            throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("No __init__ module in package %s")._mod(var1.getlocal(1)));
         }

         var1.setline(299);
         var3 = var1.getlocal(10);
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(13, var5);
         var5 = null;
         var3 = null;
         var1.setline(300);
         var3 = var1.getlocal(13);
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(14, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(16, var5);
         var5 = null;
         var3 = null;
         var1.setline(301);
         var3 = var1.getlocal(16);
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getname("PY_COMPILED"), var1.getname("PY_SOURCE")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(302);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(302);
               var1.getlocal(11).__getattr__("close").__call__(var2);
            }

            var1.setline(303);
            throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("Bad type (%r) for __init__ module in package %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(1)})));
         }

         var1.setline(306);
         PyList var10 = new PyList(new PyObject[]{var1.getlocal(4)});
         var1.setlocal(9, var10);
         var3 = null;
         var1.setline(307);
         var3 = var1.getlocal(11);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(308);
         var3 = var1.getlocal(12);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(309);
         var3 = var1.getlocal(16);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(311);
      var3 = var1.getlocal(7);
      var10000 = var3._eq(var1.getname("FROZEN_MODULE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(312);
         var3 = var1.getlocal(0).__getattr__("hooks").__getattr__("get_frozen_object").__call__(var2, var1.getlocal(1));
         var1.setlocal(17, var3);
         var3 = null;
      } else {
         var1.setline(313);
         var3 = var1.getlocal(7);
         var10000 = var3._eq(var1.getname("PY_COMPILED"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(314);
            var3 = imp.importOne("marshal", var1, -1);
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(315);
            var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
            var1.setline(316);
            var3 = var1.getlocal(18).__getattr__("load").__call__(var2, var1.getlocal(3));
            var1.setlocal(17, var3);
            var3 = null;
         } else {
            var1.setline(317);
            var3 = var1.getlocal(7);
            var10000 = var3._eq(var1.getname("PY_SOURCE"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(321);
               var3 = var1.getname("ModuleLoader").__getattr__("load_module").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(318);
            var3 = var1.getlocal(3).__getattr__("read").__call__(var2);
            var1.setlocal(19, var3);
            var3 = null;
            var1.setline(319);
            var3 = var1.getname("compile").__call__((ThreadState)var2, var1.getlocal(19), (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("exec"));
            var1.setlocal(17, var3);
            var3 = null;
         }
      }

      var1.setline(323);
      PyObject var9 = var1.getlocal(0).__getattr__("hooks").__getattr__("add_module").__call__(var2, var1.getlocal(1));
      var1.setlocal(20, var9);
      var4 = null;
      var1.setline(324);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(325);
         var9 = var1.getlocal(9);
         var1.getlocal(20).__setattr__("__path__", var9);
         var4 = null;
      }

      var1.setline(326);
      var9 = var1.getlocal(4);
      var1.getlocal(20).__setattr__("__file__", var9);
      var4 = null;

      try {
         var1.setline(328);
         Py.exec(var1.getlocal(17), var1.getlocal(20).__getattr__("__dict__"), (PyObject)null);
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(330);
         var5 = var1.getlocal(0).__getattr__("hooks").__getattr__("modules_dict").__call__(var2);
         var1.setlocal(21, var5);
         var5 = null;
         var1.setline(331);
         var5 = var1.getlocal(1);
         var10000 = var5._in(var1.getlocal(21));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(332);
            var1.getlocal(21).__delitem__(var1.getlocal(1));
         }

         var1.setline(333);
         throw Py.makeException();
      }

      var1.setline(334);
      var3 = var1.getlocal(20);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BasicModuleImporter$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic module importer; uses module loader.\n\n    This provides basic import facilities but no package imports.\n\n    "));
      var1.setline(343);
      PyString.fromInterned("Basic module importer; uses module loader.\n\n    This provides basic import facilities but no package imports.\n\n    ");
      var1.setline(345);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("VERBOSE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$49, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_loader$50, (PyObject)null);
      var1.setlocal("get_loader", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_loader$51, (PyObject)null);
      var1.setlocal("set_loader", var4);
      var3 = null;
      var1.setline(356);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_hooks$52, (PyObject)null);
      var1.setlocal("get_hooks", var4);
      var3 = null;
      var1.setline(359);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_hooks$53, (PyObject)null);
      var1.setlocal("set_hooks", var4);
      var3 = null;
      var1.setline(362);
      var3 = new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyList(Py.EmptyObjects)};
      var4 = new PyFunction(var1.f_globals, var3, import_module$54, (PyObject)null);
      var1.setlocal("import_module", var4);
      var3 = null;
      var1.setline(371);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, reload$55, (PyObject)null);
      var1.setlocal("reload", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unload$56, (PyObject)null);
      var1.setlocal("unload", var4);
      var3 = null;
      var1.setline(382);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, install$57, (PyObject)null);
      var1.setlocal("install", var4);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, uninstall$58, (PyObject)null);
      var1.setlocal("uninstall", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      var1.getglobal("_Verbose").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(347);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("ModuleLoader").__call__(var2, var1.getglobal("None"), var1.getlocal(2));
      }

      PyObject var3 = var10000;
      var1.getlocal(0).__setattr__("loader", var3);
      var3 = null;
      var1.setline(348);
      var3 = var1.getlocal(0).__getattr__("loader").__getattr__("modules_dict").__call__(var2);
      var1.getlocal(0).__setattr__("modules", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_loader$50(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      PyObject var3 = var1.getlocal(0).__getattr__("loader");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_loader$51(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("loader", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_hooks$52(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      PyObject var3 = var1.getlocal(0).__getattr__("loader").__getattr__("get_hooks").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_hooks$53(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyObject var3 = var1.getlocal(0).__getattr__("loader").__getattr__("set_hooks").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject import_module$54(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(364);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("modules"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(365);
         var3 = var1.getlocal(0).__getattr__("modules").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(366);
         PyObject var4 = var1.getlocal(0).__getattr__("loader").__getattr__("find_module").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(367);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(368);
            throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("No module named %s")._mod(var1.getlocal(1)));
         } else {
            var1.setline(369);
            var3 = var1.getlocal(0).__getattr__("loader").__getattr__("load_module").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject reload$55(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__name__"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getlocal(0).__getattr__("loader").__getattr__("find_module").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(374);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(375);
         throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("Module %s not found for reload")._mod(var1.getlocal(3)));
      } else {
         var1.setline(376);
         var3 = var1.getlocal(0).__getattr__("loader").__getattr__("load_module").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unload$56(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      var1.getlocal(0).__getattr__("modules").__delitem__(var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__name__")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$57(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      PyObject var3 = var1.getglobal("__builtin__").__getattr__("__import__");
      var1.getlocal(0).__setattr__("save_import_module", var3);
      var3 = null;
      var1.setline(384);
      var3 = var1.getglobal("__builtin__").__getattr__("reload");
      var1.getlocal(0).__setattr__("save_reload", var3);
      var3 = null;
      var1.setline(385);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("__builtin__"), (PyObject)PyString.fromInterned("unload")).__not__().__nonzero__()) {
         var1.setline(386);
         var3 = var1.getglobal("None");
         var1.getglobal("__builtin__").__setattr__("unload", var3);
         var3 = null;
      }

      var1.setline(387);
      var3 = var1.getglobal("__builtin__").__getattr__("unload");
      var1.getlocal(0).__setattr__("save_unload", var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getlocal(0).__getattr__("import_module");
      var1.getglobal("__builtin__").__setattr__("__import__", var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getlocal(0).__getattr__("reload");
      var1.getglobal("__builtin__").__setattr__("reload", var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getlocal(0).__getattr__("unload");
      var1.getglobal("__builtin__").__setattr__("unload", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject uninstall$58(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("save_import_module");
      var1.getglobal("__builtin__").__setattr__("__import__", var3);
      var3 = null;
      var1.setline(394);
      var3 = var1.getlocal(0).__getattr__("save_reload");
      var1.getglobal("__builtin__").__setattr__("reload", var3);
      var3 = null;
      var1.setline(395);
      var3 = var1.getlocal(0).__getattr__("save_unload");
      var1.getglobal("__builtin__").__setattr__("unload", var3);
      var3 = null;
      var1.setline(396);
      if (var1.getglobal("__builtin__").__getattr__("unload").__not__().__nonzero__()) {
         var1.setline(397);
         var1.getglobal("__builtin__").__delattr__("unload");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ModuleImporter$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A module importer that supports packages."));
      var1.setline(402);
      PyString.fromInterned("A module importer that supports packages.");
      var1.setline(404);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(-1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, import_module$60, (PyObject)null);
      var1.setlocal("import_module", var4);
      var3 = null;
      var1.setline(415);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, determine_parent$61, (PyObject)null);
      var1.setlocal("determine_parent", var4);
      var3 = null;
      var1.setline(460);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_head_package$62, (PyObject)null);
      var1.setlocal("find_head_package", var4);
      var3 = null;
      var1.setline(481);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_tail$63, (PyObject)null);
      var1.setlocal("load_tail", var4);
      var3 = null;
      var1.setline(493);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, ensure_fromlist$64, (PyObject)null);
      var1.setlocal("ensure_fromlist", var4);
      var3 = null;
      var1.setline(510);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, import_it$65, (PyObject)null);
      var1.setlocal("import_it", var4);
      var3 = null;
      var1.setline(534);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reload$66, (PyObject)null);
      var1.setlocal("reload", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject import_module$60(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyObject var3 = var1.getlocal(0).__getattr__("determine_parent").__call__(var2, var1.getlocal(2), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(407);
      var3 = var1.getlocal(0).__getattr__("find_head_package").__call__(var2, var1.getlocal(6), var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(408);
      var3 = var1.getlocal(0).__getattr__("load_tail").__call__(var2, var1.getlocal(7), var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(409);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(410);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(411);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("__path__")).__nonzero__()) {
            var1.setline(412);
            var1.getlocal(0).__getattr__("ensure_fromlist").__call__(var2, var1.getlocal(9), var1.getlocal(4));
         }

         var1.setline(413);
         var3 = var1.getlocal(9);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject determine_parent$61(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(417);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(418);
         PyObject var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__package__"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(419);
         var4 = var1.getlocal(3);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(420);
            var10000 = var1.getlocal(3).__not__();
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(2);
               var10000 = var4._gt(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(421);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Attempted relative import in non-package"));
            }
         } else {
            var1.setline(424);
            var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__name__"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(425);
            var4 = var1.getlocal(4);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(426);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(427);
            PyString var10 = PyString.fromInterned("__path__");
            var10000 = var10._in(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(429);
               var4 = var1.getlocal(4);
               var1.setlocal(3, var4);
               var4 = null;
            } else {
               var1.setline(432);
               var10 = PyString.fromInterned(".");
               var10000 = var10._notin(var1.getlocal(4));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(433);
                  var4 = var1.getlocal(2);
                  var10000 = var4._gt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(434);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Attempted relative import in non-package"));
                  }

                  var1.setline(436);
                  var4 = var1.getglobal("None");
                  var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("__package__"), var4);
                  var4 = null;
                  var1.setline(437);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(438);
               var4 = var1.getlocal(4).__getattr__("rpartition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(439);
            var4 = var1.getlocal(3);
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("__package__"), var4);
            var4 = null;
         }

         var1.setline(440);
         var4 = var1.getlocal(2);
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(441);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(442);
            var4 = var1.getglobal("range").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(-1)).__iter__();

            while(true) {
               var1.setline(442);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(448);
                  var4 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
                  var1.setlocal(3, var4);
                  var4 = null;
                  break;
               }

               var1.setlocal(6, var5);

               PyException var6;
               try {
                  var1.setline(444);
                  PyObject var9 = var1.getlocal(3).__getattr__("rindex").__call__((ThreadState)var2, PyString.fromInterned("."), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(5));
                  var1.setlocal(5, var9);
                  var6 = null;
               } catch (Throwable var8) {
                  var6 = Py.setException(var8, var1);
                  if (var6.match(var1.getglobal("ValueError"))) {
                     var1.setline(446);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempted relative import beyond top-level package")));
                  }

                  throw var6;
               }
            }
         }

         try {
            var1.setline(450);
            var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("KeyError"))) {
               var1.setline(452);
               var5 = var1.getlocal(2);
               var10000 = var5._lt(Py.newInteger(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(453);
                  var1.getglobal("warn").__call__((ThreadState)var2, PyString.fromInterned("Parent module '%s' not found while handling absolute import")._mod(var1.getlocal(3)), (PyObject)var1.getglobal("RuntimeWarning"), (PyObject)Py.newInteger(1));
                  var1.setline(455);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(457);
                  throw Py.makeException(var1.getglobal("SystemError"), PyString.fromInterned("Parent module '%s' not loaded, cannot perform relative import")._mod(var1.getlocal(3)));
               }
            } else {
               throw var11;
            }
         }
      }
   }

   public PyObject find_head_package$62(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      PyString var3 = PyString.fromInterned(".");
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(462);
         var5 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(463);
         var5 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(464);
         var5 = var1.getlocal(2).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(5, var5);
         var3 = null;
      } else {
         var1.setline(466);
         var5 = var1.getlocal(2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(467);
         var3 = PyString.fromInterned("");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(468);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(469);
         var5 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__name__"), var1.getlocal(4)}));
         var1.setlocal(6, var5);
         var3 = null;
      } else {
         var1.setline(471);
         var5 = var1.getlocal(4);
         var1.setlocal(6, var5);
         var3 = null;
      }

      var1.setline(472);
      var5 = var1.getlocal(0).__getattr__("import_it").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getlocal(1));
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(473);
      PyTuple var6;
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(473);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(474);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(475);
            PyObject var4 = var1.getlocal(4);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(476);
            var4 = var1.getglobal("None");
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(477);
            var4 = var1.getlocal(0).__getattr__("import_it").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getlocal(1));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(478);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(478);
               var6 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)});
               var1.f_lasti = -1;
               return var6;
            }
         }

         var1.setline(479);
         throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("No module named '%s'")._mod(var1.getlocal(6)));
      }
   }

   public PyObject load_tail$63(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(3, var3);
      var3 = null;

      do {
         var1.setline(483);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(491);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(484);
         var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(485);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(485);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(486);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null), var1.getlocal(2).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(487);
         var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("__name__"), var1.getlocal(5)}));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(488);
         var3 = var1.getlocal(0).__getattr__("import_it").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(489);
      } while(!var1.getlocal(3).__not__().__nonzero__());

      var1.setline(490);
      throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("No module named '%s'")._mod(var1.getlocal(6)));
   }

   public PyObject ensure_fromlist$64(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         while(true) {
            PyObject var5;
            do {
               while(true) {
                  var1.setline(494);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(495);
                  var5 = var1.getlocal(4);
                  PyObject var10000 = var5._eq(PyString.fromInterned("*"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(496);
                     break;
                  }

                  var1.setline(504);
                  var5 = var1.getlocal(4);
                  var10000 = var5._ne(PyString.fromInterned("*"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(1), var1.getlocal(4)).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(505);
                     var5 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__name__"), var1.getlocal(4)}));
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(506);
                     var5 = var1.getlocal(0).__getattr__("import_it").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getlocal(1));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(507);
                     if (var1.getlocal(7).__not__().__nonzero__()) {
                        var1.setline(508);
                        throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("No module named '%s'")._mod(var1.getlocal(6)));
                     }
                  }
               }
            } while(!var1.getlocal(3).__not__().__nonzero__());

            try {
               var1.setline(498);
               var5 = var1.getlocal(1).__getattr__("__all__");
               var1.setlocal(5, var5);
               var5 = null;
               break;
            } catch (Throwable var6) {
               PyException var7 = Py.setException(var6, var1);
               if (!var7.match(var1.getglobal("AttributeError"))) {
                  throw var7;
               }

               var1.setline(500);
            }
         }

         var1.setline(502);
         var1.getlocal(0).__getattr__("ensure_fromlist").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(1));
      }
   }

   public PyObject import_it$65(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(514);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(515);
         PyException var4;
         if (var1.getlocal(4).__not__().__nonzero__()) {
            try {
               var1.setline(517);
               var3 = var1.getlocal(0).__getattr__("modules").__getitem__(var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var6) {
               var4 = Py.setException(var6, var1);
               if (!var4.match(var1.getglobal("KeyError"))) {
                  throw var4;
               }
            }

            var1.setline(519);
         }

         PyObject var7;
         try {
            var1.setline(521);
            PyObject var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3).__getattr__("__path__");
            }

            var7 = var10000;
            var1.setlocal(5, var7);
            var4 = null;
         } catch (Throwable var5) {
            var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("AttributeError"))) {
               var1.setline(523);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(524);
         var7 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var7);
         var4 = null;
         var1.setline(525);
         var7 = var1.getlocal(0).__getattr__("loader").__getattr__("find_module").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setlocal(6, var7);
         var4 = null;
         var1.setline(526);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            var1.setline(527);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(528);
            var7 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var7);
            var4 = null;
            var1.setline(529);
            var7 = var1.getlocal(0).__getattr__("loader").__getattr__("load_module").__call__(var2, var1.getlocal(2), var1.getlocal(6));
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(530);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(531);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(7));
            }

            var1.setline(532);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject reload$66(PyFrame var1, ThreadState var2) {
      var1.setline(535);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__name__"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(536);
      PyString var6 = PyString.fromInterned(".");
      PyObject var10000 = var6._notin(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(537);
         var10000 = var1.getlocal(0).__getattr__("import_it");
         PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(2), var1.getglobal("None"), Py.newInteger(1)};
         String[] var9 = new String[]{"force_load"};
         var10000 = var10000.__call__(var2, var7, var9);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(538);
         PyObject var4 = var1.getlocal(2).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(539);
         var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(540);
         var4 = var1.getlocal(0).__getattr__("modules").__getitem__(var1.getlocal(4));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(541);
         var10000 = var1.getlocal(0).__getattr__("import_it");
         PyObject[] var8 = new PyObject[]{var1.getlocal(2).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null), var1.getlocal(2), var1.getlocal(5), Py.newInteger(1)};
         String[] var5 = new String[]{"force_load"};
         var10000 = var10000.__call__(var2, var8, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject install$67(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyObject var10000 = var1.getlocal(0);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("default_importer");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("ModuleImporter").__call__(var2);
         }
      }

      PyObject var3 = var10000;
      var1.setglobal("current_importer", var3);
      var3 = null;
      var1.setline(550);
      var1.getglobal("current_importer").__getattr__("install").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject uninstall$68(PyFrame var1, ThreadState var2) {
      var1.setline(554);
      var1.getglobal("current_importer").__getattr__("uninstall").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public ihooks$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Verbose$1 = Py.newCode(0, var2, var1, "_Verbose", 73, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 75, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_verbose$3 = Py.newCode(1, var2, var1, "get_verbose", 78, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "verbose"};
      set_verbose$4 = Py.newCode(2, var2, var1, "set_verbose", 81, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      note$5 = Py.newCode(2, var2, var1, "note", 86, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "args"};
      message$6 = Py.newCode(3, var2, var1, "message", 90, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BasicModuleLoader$7 = Py.newCode(0, var2, var1, "BasicModuleLoader", 97, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "path", "dir", "stuff"};
      find_module$8 = Py.newCode(3, var2, var1, "find_module", 114, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      default_path$9 = Py.newCode(1, var2, var1, "default_path", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dir"};
      find_module_in_dir$10 = Py.newCode(3, var2, var1, "find_module_in_dir", 125, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      find_builtin_module$11 = Py.newCode(2, var2, var1, "find_builtin_module", 134, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "stuff", "file", "filename", "info"};
      load_module$12 = Py.newCode(3, var2, var1, "load_module", 142, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Hooks$13 = Py.newCode(0, var2, var1, "Hooks", 150, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      get_suffixes$14 = Py.newCode(1, var2, var1, "get_suffixes", 162, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      new_module$15 = Py.newCode(2, var2, var1, "new_module", 163, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      is_builtin$16 = Py.newCode(2, var2, var1, "is_builtin", 164, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      init_builtin$17 = Py.newCode(2, var2, var1, "init_builtin", 165, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      is_frozen$18 = Py.newCode(2, var2, var1, "is_frozen", 166, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      init_frozen$19 = Py.newCode(2, var2, var1, "init_frozen", 167, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      get_frozen_object$20 = Py.newCode(2, var2, var1, "get_frozen_object", 168, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "filename", "file"};
      load_source$21 = Py.newCode(4, var2, var1, "load_source", 169, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "filename", "file"};
      load_compiled$22 = Py.newCode(4, var2, var1, "load_compiled", 171, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "filename", "file"};
      load_dynamic$23 = Py.newCode(4, var2, var1, "load_dynamic", 173, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "filename", "file"};
      load_package$24 = Py.newCode(4, var2, var1, "load_package", 175, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "d", "m"};
      add_module$25 = Py.newCode(2, var2, var1, "add_module", 178, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      modules_dict$26 = Py.newCode(1, var2, var1, "modules_dict", 185, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      default_path$27 = Py.newCode(1, var2, var1, "default_path", 186, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_split$28 = Py.newCode(2, var2, var1, "path_split", 188, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "y"};
      path_join$29 = Py.newCode(3, var2, var1, "path_join", 189, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_isabs$30 = Py.newCode(2, var2, var1, "path_isabs", 190, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_exists$31 = Py.newCode(2, var2, var1, "path_exists", 193, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_isdir$32 = Py.newCode(2, var2, var1, "path_isdir", 194, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_isfile$33 = Py.newCode(2, var2, var1, "path_isfile", 195, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      path_islink$34 = Py.newCode(2, var2, var1, "path_islink", 196, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      openfile$35 = Py.newCode(2, var2, var1, "openfile", 199, true, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      listdir$36 = Py.newCode(2, var2, var1, "listdir", 201, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ModuleLoader$37 = Py.newCode(0, var2, var1, "ModuleLoader", 206, false, false, self, 37, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "hooks", "verbose"};
      __init__$38 = Py.newCode(3, var2, var1, "__init__", 216, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      default_path$39 = Py.newCode(1, var2, var1, "default_path", 220, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      modules_dict$40 = Py.newCode(1, var2, var1, "modules_dict", 223, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_hooks$41 = Py.newCode(1, var2, var1, "get_hooks", 226, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hooks"};
      set_hooks$42 = Py.newCode(2, var2, var1, "set_hooks", 229, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      find_builtin_module$43 = Py.newCode(2, var2, var1, "find_builtin_module", 232, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dir", "allow_packages", "fullname", "stuff", "file", "info", "suff", "mode", "type", "fp"};
      find_module_in_dir$44 = Py.newCode(4, var2, var1, "find_module_in_dir", 240, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "stuff", "file", "filename", "info", "suff", "mode", "type", "m"};
      load_module$45 = Py.newCode(3, var2, var1, "load_module", 261, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FancyModuleLoader$46 = Py.newCode(0, var2, var1, "FancyModuleLoader", 286, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "stuff", "file", "filename", "suff", "mode", "type", "realfilename", "path", "initstuff", "initfile", "initfilename", "initinfo", "initsuff", "initmode", "inittype", "code", "marshal", "data", "m", "d"};
      load_module$47 = Py.newCode(3, var2, var1, "load_module", 290, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BasicModuleImporter$48 = Py.newCode(0, var2, var1, "BasicModuleImporter", 337, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "loader", "verbose"};
      __init__$49 = Py.newCode(3, var2, var1, "__init__", 345, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_loader$50 = Py.newCode(1, var2, var1, "get_loader", 350, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      set_loader$51 = Py.newCode(2, var2, var1, "set_loader", 353, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_hooks$52 = Py.newCode(1, var2, var1, "get_hooks", 356, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hooks"};
      set_hooks$53 = Py.newCode(2, var2, var1, "set_hooks", 359, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "globals", "locals", "fromlist", "stuff"};
      import_module$54 = Py.newCode(5, var2, var1, "import_module", 362, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "path", "name", "stuff"};
      reload$55 = Py.newCode(3, var2, var1, "reload", 371, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module"};
      unload$56 = Py.newCode(2, var2, var1, "unload", 378, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      install$57 = Py.newCode(1, var2, var1, "install", 382, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      uninstall$58 = Py.newCode(1, var2, var1, "uninstall", 392, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ModuleImporter$59 = Py.newCode(0, var2, var1, "ModuleImporter", 400, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "globals", "locals", "fromlist", "level", "parent", "q", "tail", "m"};
      import_module$60 = Py.newCode(6, var2, var1, "import_module", 404, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "globals", "level", "pkgname", "modname", "dot", "x"};
      determine_parent$61 = Py.newCode(3, var2, var1, "determine_parent", 415, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parent", "name", "i", "head", "tail", "qname", "q"};
      find_head_package$62 = Py.newCode(3, var2, var1, "find_head_package", 460, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "q", "tail", "m", "i", "head", "mname"};
      load_tail$63 = Py.newCode(3, var2, var1, "load_tail", 481, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "fromlist", "recursive", "sub", "all", "subname", "submod"};
      ensure_fromlist$64 = Py.newCode(4, var2, var1, "ensure_fromlist", 493, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "partname", "fqname", "parent", "force_load", "path", "stuff", "m"};
      import_it$65 = Py.newCode(5, var2, var1, "import_it", 510, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "name", "i", "pname", "parent"};
      reload$66 = Py.newCode(2, var2, var1, "reload", 534, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"importer"};
      install$67 = Py.newCode(1, var2, var1, "install", 547, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      uninstall$68 = Py.newCode(0, var2, var1, "uninstall", 552, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ihooks$py("ihooks$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ihooks$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._Verbose$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.get_verbose$3(var2, var3);
         case 4:
            return this.set_verbose$4(var2, var3);
         case 5:
            return this.note$5(var2, var3);
         case 6:
            return this.message$6(var2, var3);
         case 7:
            return this.BasicModuleLoader$7(var2, var3);
         case 8:
            return this.find_module$8(var2, var3);
         case 9:
            return this.default_path$9(var2, var3);
         case 10:
            return this.find_module_in_dir$10(var2, var3);
         case 11:
            return this.find_builtin_module$11(var2, var3);
         case 12:
            return this.load_module$12(var2, var3);
         case 13:
            return this.Hooks$13(var2, var3);
         case 14:
            return this.get_suffixes$14(var2, var3);
         case 15:
            return this.new_module$15(var2, var3);
         case 16:
            return this.is_builtin$16(var2, var3);
         case 17:
            return this.init_builtin$17(var2, var3);
         case 18:
            return this.is_frozen$18(var2, var3);
         case 19:
            return this.init_frozen$19(var2, var3);
         case 20:
            return this.get_frozen_object$20(var2, var3);
         case 21:
            return this.load_source$21(var2, var3);
         case 22:
            return this.load_compiled$22(var2, var3);
         case 23:
            return this.load_dynamic$23(var2, var3);
         case 24:
            return this.load_package$24(var2, var3);
         case 25:
            return this.add_module$25(var2, var3);
         case 26:
            return this.modules_dict$26(var2, var3);
         case 27:
            return this.default_path$27(var2, var3);
         case 28:
            return this.path_split$28(var2, var3);
         case 29:
            return this.path_join$29(var2, var3);
         case 30:
            return this.path_isabs$30(var2, var3);
         case 31:
            return this.path_exists$31(var2, var3);
         case 32:
            return this.path_isdir$32(var2, var3);
         case 33:
            return this.path_isfile$33(var2, var3);
         case 34:
            return this.path_islink$34(var2, var3);
         case 35:
            return this.openfile$35(var2, var3);
         case 36:
            return this.listdir$36(var2, var3);
         case 37:
            return this.ModuleLoader$37(var2, var3);
         case 38:
            return this.__init__$38(var2, var3);
         case 39:
            return this.default_path$39(var2, var3);
         case 40:
            return this.modules_dict$40(var2, var3);
         case 41:
            return this.get_hooks$41(var2, var3);
         case 42:
            return this.set_hooks$42(var2, var3);
         case 43:
            return this.find_builtin_module$43(var2, var3);
         case 44:
            return this.find_module_in_dir$44(var2, var3);
         case 45:
            return this.load_module$45(var2, var3);
         case 46:
            return this.FancyModuleLoader$46(var2, var3);
         case 47:
            return this.load_module$47(var2, var3);
         case 48:
            return this.BasicModuleImporter$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this.get_loader$50(var2, var3);
         case 51:
            return this.set_loader$51(var2, var3);
         case 52:
            return this.get_hooks$52(var2, var3);
         case 53:
            return this.set_hooks$53(var2, var3);
         case 54:
            return this.import_module$54(var2, var3);
         case 55:
            return this.reload$55(var2, var3);
         case 56:
            return this.unload$56(var2, var3);
         case 57:
            return this.install$57(var2, var3);
         case 58:
            return this.uninstall$58(var2, var3);
         case 59:
            return this.ModuleImporter$59(var2, var3);
         case 60:
            return this.import_module$60(var2, var3);
         case 61:
            return this.determine_parent$61(var2, var3);
         case 62:
            return this.find_head_package$62(var2, var3);
         case 63:
            return this.load_tail$63(var2, var3);
         case 64:
            return this.ensure_fromlist$64(var2, var3);
         case 65:
            return this.import_it$65(var2, var3);
         case 66:
            return this.reload$66(var2, var3);
         case 67:
            return this.install$67(var2, var3);
         case 68:
            return this.uninstall$68(var2, var3);
         default:
            return null;
      }
   }
}

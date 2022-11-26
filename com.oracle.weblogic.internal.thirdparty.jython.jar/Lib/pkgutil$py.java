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
@MTime(1498849384000L)
@Filename("pkgutil.py")
public class pkgutil$py extends PyFunctionTable implements PyRunnable {
   static pkgutil$py self;
   static final PyCode f$0;
   static final PyCode read_jython_code$1;
   static final PyCode read_code$2;
   static final PyCode simplegeneric$3;
   static final PyCode wrapper$4;
   static final PyCode cls$5;
   static final PyCode register$6;
   static final PyCode f$7;
   static final PyCode walk_packages$8;
   static final PyCode seen$9;
   static final PyCode iter_modules$10;
   static final PyCode iter_importer_modules$11;
   static final PyCode ImpImporter$12;
   static final PyCode __init__$13;
   static final PyCode find_module$14;
   static final PyCode iter_modules$15;
   static final PyCode ImpLoader$16;
   static final PyCode __init__$17;
   static final PyCode load_module$18;
   static final PyCode get_data$19;
   static final PyCode _reopen$20;
   static final PyCode _fix_name$21;
   static final PyCode is_package$22;
   static final PyCode get_code$23;
   static final PyCode get_source$24;
   static final PyCode _get_delegate$25;
   static final PyCode get_filename$26;
   static final PyCode iter_zipimport_modules$27;
   static final PyCode get_importer$28;
   static final PyCode iter_importers$29;
   static final PyCode get_loader$30;
   static final PyCode find_loader$31;
   static final PyCode extend_path$32;
   static final PyCode get_data$33;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities to support packages."));
      var1.setline(1);
      PyString.fromInterned("Utilities to support packages.");
      var1.setline(6);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"ModuleType"};
      PyObject[] var7 = imp.importFrom("types", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("ModuleType", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"IllegalArgumentException"};
      var7 = imp.importFrom("java.lang", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("IllegalArgumentException", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"imp", "BytecodeLoader"};
      var7 = imp.importFrom("org.python.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_imp", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("BytecodeLoader", var4);
      var4 = null;
      var1.setline(14);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("get_importer"), PyString.fromInterned("iter_importers"), PyString.fromInterned("get_loader"), PyString.fromInterned("find_loader"), PyString.fromInterned("walk_packages"), PyString.fromInterned("iter_modules"), PyString.fromInterned("ImpImporter"), PyString.fromInterned("ImpLoader"), PyString.fromInterned("read_code"), PyString.fromInterned("extend_path")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(25);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, read_jython_code$1, (PyObject)null);
      var1.setlocal("read_jython_code", var9);
      var3 = null;
      var1.setline(32);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, read_code$2, (PyObject)null);
      var1.setlocal("read_code", var9);
      var3 = null;
      var1.setline(37);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, simplegeneric$3, PyString.fromInterned("Make a trivial single-dispatch generic function"));
      var1.setlocal("simplegeneric", var9);
      var3 = null;
      var1.setline(77);
      var7 = new PyObject[]{var1.getname("None"), PyString.fromInterned(""), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var7, walk_packages$8, PyString.fromInterned("Yields (module_loader, name, ispkg) for all modules recursively\n    on path, or, if path is None, all accessible modules.\n\n    'path' should be either None or a list of paths to look for\n    modules in.\n\n    'prefix' is a string to output on the front of every module name\n    on output.\n\n    Note that this function must import all *packages* (NOT all\n    modules!) on the given path, in order to access the __path__\n    attribute to find submodules.\n\n    'onerror' is a function which gets called with one argument (the\n    name of the package which was being imported) if any exception\n    occurs while trying to import a package.  If no onerror function is\n    supplied, ImportErrors are caught and ignored, while all other\n    exceptions are propagated, terminating the search.\n\n    Examples:\n\n    # list all modules python can access\n    walk_packages()\n\n    # list all submodules of ctypes\n    walk_packages(ctypes.__path__, ctypes.__name__+'.')\n    "));
      var1.setlocal("walk_packages", var9);
      var3 = null;
      var1.setline(135);
      var7 = new PyObject[]{var1.getname("None"), PyString.fromInterned("")};
      var9 = new PyFunction(var1.f_globals, var7, iter_modules$10, PyString.fromInterned("Yields (module_loader, name, ispkg) for all submodules on path,\n    or, if path is None, all top-level modules on sys.path.\n\n    'path' should be either None or a list of paths to look for\n    modules in.\n\n    'prefix' is a string to output on the front of every module name\n    on output.\n    "));
      var1.setlocal("iter_modules", var9);
      var3 = null;
      var1.setline(160);
      var7 = new PyObject[]{PyString.fromInterned("")};
      var9 = new PyFunction(var1.f_globals, var7, iter_importer_modules$11, (PyObject)null);
      var1.setlocal("iter_importer_modules", var9);
      var3 = null;
      var1.setline(165);
      var3 = var1.getname("simplegeneric").__call__(var2, var1.getname("iter_importer_modules"));
      var1.setlocal("iter_importer_modules", var3);
      var3 = null;
      var1.setline(168);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("ImpImporter", var7, ImpImporter$12);
      var1.setlocal("ImpImporter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(235);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("ImpLoader", var7, ImpLoader$16);
      var1.setlocal("ImpLoader", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);

      try {
         var1.setline(337);
         var3 = imp.importOne("zipimport", var1, -1);
         var1.setlocal("zipimport", var3);
         var3 = null;
         var1.setline(338);
         var6 = new String[]{"zipimporter"};
         var7 = imp.importFrom("zipimport", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("zipimporter", var4);
         var4 = null;
         var1.setline(340);
         var7 = new PyObject[]{PyString.fromInterned("")};
         var9 = new PyFunction(var1.f_globals, var7, iter_zipimport_modules$27, (PyObject)null);
         var1.setlocal("iter_zipimport_modules", var9);
         var3 = null;
         var1.setline(369);
         var1.getname("iter_importer_modules").__getattr__("register").__call__(var2, var1.getname("zipimporter"), var1.getname("iter_zipimport_modules"));
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(372);
      }

      var1.setline(375);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, get_importer$28, PyString.fromInterned("Retrieve a PEP 302 importer for the given path item\n\n    The returned importer is cached in sys.path_importer_cache\n    if it was newly created by a path hook.\n\n    If there is no importer, a wrapper around the basic import\n    machinery is returned. This wrapper is never inserted into\n    the importer cache (None is inserted instead).\n\n    The cache (or part of it) can be cleared manually if a\n    rescan of sys.path_hooks is necessary.\n    "));
      var1.setlocal("get_importer", var9);
      var3 = null;
      var1.setline(409);
      var7 = new PyObject[]{PyString.fromInterned("")};
      var9 = new PyFunction(var1.f_globals, var7, iter_importers$29, PyString.fromInterned("Yield PEP 302 importers for the given module name\n\n    If fullname contains a '.', the importers will be for the package\n    containing fullname, otherwise they will be importers for sys.meta_path,\n    sys.path, and Python's \"classic\" import machinery, in that order.  If\n    the named module is in a package, that package is imported as a side\n    effect of invoking this function.\n\n    Non PEP 302 mechanisms (e.g. the Windows registry) used by the\n    standard import machinery to find files in alternative locations\n    are partially supported, but are searched AFTER sys.path. Normally,\n    these locations are searched BEFORE sys.path, preventing sys.path\n    entries from shadowing them.\n\n    For this to cause a visible difference in behaviour, there must\n    be a module or package name that is accessible via both sys.path\n    and one of the non PEP 302 file system mechanisms. In this case,\n    the emulation will find the former version, while the builtin\n    import mechanism will find the latter.\n\n    Items of the following types can be affected by this discrepancy:\n        imp.C_EXTENSION, imp.PY_SOURCE, imp.PY_COMPILED, imp.PKG_DIRECTORY\n    "));
      var1.setlocal("iter_importers", var9);
      var3 = null;
      var1.setline(450);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, get_loader$30, PyString.fromInterned("Get a PEP 302 \"loader\" object for module_or_name\n\n    If the module or package is accessible via the normal import\n    mechanism, a wrapper around the relevant part of that machinery\n    is returned.  Returns None if the module cannot be found or imported.\n    If the named module is not already imported, its containing package\n    (if any) is imported, in order to establish the package __path__.\n\n    This function uses iter_importers(), and is thus subject to the same\n    limitations regarding platform-specific special import locations such\n    as the Windows registry.\n    "));
      var1.setlocal("get_loader", var9);
      var3 = null;
      var1.setline(480);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, find_loader$31, PyString.fromInterned("Find a PEP 302 \"loader\" object for fullname\n\n    If fullname contains dots, path must be the containing package's __path__.\n    Returns None if the module cannot be found or imported. This function uses\n    iter_importers(), and is thus subject to the same limitations regarding\n    platform-specific special import locations such as the Windows registry.\n    "));
      var1.setlocal("find_loader", var9);
      var3 = null;
      var1.setline(496);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, extend_path$32, PyString.fromInterned("Extend a package's path.\n\n    Intended use is to place the following code in a package's __init__.py:\n\n        from pkgutil import extend_path\n        __path__ = extend_path(__path__, __name__)\n\n    This will add to the package's __path__ all subdirectories of\n    directories on sys.path named after the package.  This is useful\n    if one wants to distribute different parts of a single logical\n    package as multiple directories.\n\n    It also looks for *.pkg files beginning where * matches the name\n    argument.  This feature is similar to *.pth files (see site.py),\n    except that it doesn't special-case lines starting with 'import'.\n    A *.pkg file is trusted at face value: apart from checking for\n    duplicates, all entries found in a *.pkg file are added to the\n    path, regardless of whether they are exist the filesystem.  (This\n    is a feature.)\n\n    If the input path is not a list (as is the case for frozen\n    packages) it is returned unchanged.  The input path is not\n    modified; an extended copy is returned.  Items are only appended\n    to the copy at the end.\n\n    It is assumed that sys.path is a sequence.  Items of sys.path that\n    are not (unicode or 8-bit) strings referring to existing\n    directories are ignored.  Unicode items of sys.path that cause\n    errors when used as filenames may cause this function to raise an\n    exception (in line with os.path.isdir() behavior).\n    "));
      var1.setlocal("extend_path", var9);
      var3 = null;
      var1.setline(572);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, get_data$33, PyString.fromInterned("Get a resource from a package.\n\n    This is a wrapper round the PEP 302 loader get_data API. The package\n    argument should be the name of a package, in standard module format\n    (foo.bar). The resource argument should be in the form of a relative\n    filename, using '/' as the path separator. The parent directory name '..'\n    is not allowed, and nor is a rooted name (starting with a '/').\n\n    The function returns a binary string, which is the contents of the\n    specified resource.\n\n    For packages located in the filesystem, which have already been imported,\n    this is the rough equivalent of\n\n        d = os.path.dirname(sys.modules[package].__file__)\n        data = open(os.path.join(d, resource), 'rb').read()\n\n    If the package cannot be located or loaded, or it uses a PEP 302 loader\n    which does not support get_data(), then None is returned.\n    "));
      var1.setlocal("get_data", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_jython_code$1(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(27);
         var3 = var1.getglobal("_imp").__getattr__("readCode").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getglobal("False"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(28);
         var3 = var1.getglobal("BytecodeLoader").__getattr__("makeCode").__call__(var2, var1.getlocal(0)._add(PyString.fromInterned("$py")), var1.getlocal(3), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("IllegalArgumentException"))) {
            var1.setline(30);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject read_code$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(0).__getattr__("name");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(1))).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("read_jython_code").__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject simplegeneric$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 2);
      var1.setline(38);
      PyString.fromInterned("Make a trivial single-dispatch generic function");
      var1.setline(39);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(40);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = wrapper$4;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(2)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(1, var6);
      var3 = null;

      PyObject var8;
      try {
         var1.setline(61);
         var8 = var1.getderef(2).__getattr__("__name__");
         var1.getlocal(1).__setattr__("__name__", var8);
         var3 = null;
      } catch (Throwable var4) {
         PyException var7 = Py.setException(var4, var1);
         if (!var7.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
            throw var7;
         }

         var1.setline(63);
      }

      var1.setline(65);
      var5 = new PyObject[]{var1.getglobal("None")};
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = register$6;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setderef(1, var6);
      var3 = null;
      var1.setline(71);
      var8 = var1.getderef(2).__getattr__("__dict__");
      var1.getlocal(1).__setattr__("__dict__", var8);
      var3 = null;
      var1.setline(72);
      var8 = var1.getderef(2).__getattr__("__doc__");
      var1.getlocal(1).__setattr__("__doc__", var8);
      var3 = null;
      var1.setline(73);
      var8 = var1.getderef(1);
      var1.getlocal(1).__setattr__("register", var8);
      var3 = null;
      var1.setline(74);
      var8 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject wrapper$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      PyException var11;
      try {
         var1.setline(43);
         var3 = var1.getlocal(2).__getattr__("__class__");
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var10) {
         var11 = Py.setException(var10, var1);
         if (!var11.match(var1.getglobal("AttributeError"))) {
            throw var11;
         }

         var1.setline(45);
         var4 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      }

      PyTuple var5;
      PyObject var14;
      try {
         var1.setline(47);
         var3 = var1.getlocal(3).__getattr__("__mro__");
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var9) {
         var11 = Py.setException(var9, var1);
         if (!var11.match(var1.getglobal("AttributeError"))) {
            throw var11;
         }

         try {
            var1.setline(50);
            PyObject[] var13 = new PyObject[]{var1.getlocal(3), var1.getglobal("object")};
            var14 = Py.makeClass("cls", var13, cls$5);
            var1.setlocal(3, var14);
            var5 = null;
            Arrays.fill(var13, (Object)null);
            var1.setline(52);
            var4 = var1.getlocal(3).__getattr__("__mro__").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var4);
            var4 = null;
         } catch (Throwable var8) {
            PyException var12 = Py.setException(var8, var1);
            if (!var12.match(var1.getglobal("TypeError"))) {
               throw var12;
            }

            var1.setline(54);
            var5 = new PyTuple(new PyObject[]{var1.getglobal("object")});
            var1.setlocal(4, var5);
            var5 = null;
         }
      }

      var1.setline(55);
      var3 = var1.getlocal(4).__iter__();

      String[] var6;
      PyObject var10000;
      do {
         var1.setline(55);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(59);
            var10000 = var1.getderef(1);
            PyObject[] var15 = Py.EmptyObjects;
            String[] var7 = new String[0];
            var10000 = var10000._callextra(var15, var7, var1.getlocal(0), var1.getlocal(1));
            var6 = null;
            var14 = var10000;
            var1.f_lasti = -1;
            return var14;
         }

         var1.setlocal(5, var4);
         var1.setline(56);
         var14 = var1.getlocal(5);
         var10000 = var14._in(var1.getderef(0));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(57);
      var10000 = var1.getderef(0).__getitem__(var1.getlocal(5));
      PyObject[] var16 = Py.EmptyObjects;
      var6 = new String[0];
      var10000 = var10000._callextra(var16, var6, var1.getlocal(0), var1.getlocal(1));
      var5 = null;
      var14 = var10000;
      var1.f_lasti = -1;
      return var14;
   }

   public PyObject cls$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(51);
      return var1.getf_locals();
   }

   public PyObject register$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(66);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         var1.setline(67);
         PyObject[] var5 = Py.EmptyObjects;
         PyObject[] var10003 = var5;
         PyObject var10002 = var1.f_globals;
         PyCode var10004 = f$7;
         var5 = new PyObject[]{var1.getclosure(2), var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10002, var10003, var10004, var5);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(68);
         PyObject var4 = var1.getlocal(1);
         var1.getderef(1).__setitem__(var1.getderef(0), var4);
         var4 = null;
         var1.setline(69);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getderef(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject walk_packages$8(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      label70: {
         PyObject var7;
         Object[] var8;
         PyObject var18;
         Object var10000;
         label69:
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(104);
               PyString.fromInterned("Yields (module_loader, name, ispkg) for all modules recursively\n    on path, or, if path is None, all accessible modules.\n\n    'path' should be either None or a list of paths to look for\n    modules in.\n\n    'prefix' is a string to output on the front of every module name\n    on output.\n\n    Note that this function must import all *packages* (NOT all\n    modules!) on the given path, in order to access the __path__\n    attribute to find submodules.\n\n    'onerror' is a function which gets called with one argument (the\n    name of the package which was being imported) if any exception\n    occurs while trying to import a package.  If no onerror function is\n    supplied, ImportErrors are caught and ignored, while all other\n    exceptions are propagated, terminating the search.\n\n    Examples:\n\n    # list all modules python can access\n    walk_packages()\n\n    # list all submodules of ctypes\n    walk_packages(ctypes.__path__, ctypes.__name__+'.')\n    ");
               var1.setline(106);
               PyObject[] var10 = new PyObject[1];
               PyObject[] var12 = Py.EmptyObjects;
               PyDictionary var20 = new PyDictionary(var12);
               Arrays.fill(var12, (Object)null);
               var10[0] = var20;
               PyFunction var11 = new PyFunction(var1.f_globals, var10, seen$9, (PyObject)null);
               var1.setlocal(3, var11);
               var3 = null;
               var1.setline(111);
               var3 = var1.getglobal("iter_modules").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__iter__();
               break label70;
            case 1:
               var5 = var1.f_savedlocals;
               var3 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               var1.setline(114);
               if (!var1.getlocal(6).__nonzero__()) {
                  break label70;
               }

               try {
                  var1.setline(116);
                  var1.getglobal("__import__").__call__(var2, var1.getlocal(5));
               } catch (Throwable var9) {
                  PyException var13 = Py.setException(var9, var1);
                  if (var13.match(var1.getglobal("ImportError"))) {
                     var1.setline(118);
                     var6 = var1.getlocal(2);
                     var18 = var6._isnot(var1.getglobal("None"));
                     var6 = null;
                     if (var18.__nonzero__()) {
                        var1.setline(119);
                        var1.getlocal(2).__call__(var2, var1.getlocal(5));
                     }
                  } else {
                     if (!var13.match(var1.getglobal("Exception"))) {
                        throw var13;
                     }

                     var1.setline(121);
                     var6 = var1.getlocal(2);
                     var18 = var6._isnot(var1.getglobal("None"));
                     var6 = null;
                     if (!var18.__nonzero__()) {
                        var1.setline(124);
                        throw Py.makeException();
                     }

                     var1.setline(122);
                     var1.getlocal(2).__call__(var2, var1.getlocal(5));
                  }
                  break label70;
               }

               var1.setline(126);
               var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(5)), (PyObject)PyString.fromInterned("__path__"), (PyObject)var1.getglobal("None"));
               if (!((PyObject)var10000).__nonzero__()) {
                  PyObject[] var15 = Py.EmptyObjects;
                  var10000 = new PyList(var15);
                  Arrays.fill(var15, (Object)null);
               }

               Object var16 = var10000;
               var1.setlocal(0, (PyObject)var16);
               var6 = null;
               var1.setline(129);
               PyList var19 = new PyList();
               var6 = var19.__getattr__("append");
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(129);
               var6 = var1.getlocal(0).__iter__();

               while(true) {
                  var1.setline(129);
                  var7 = var6.__iternext__();
                  if (var7 == null) {
                     var1.setline(129);
                     var1.dellocal(7);
                     PyList var17 = var19;
                     var1.setlocal(0, var17);
                     var6 = null;
                     var1.setline(131);
                     var6 = var1.getglobal("walk_packages").__call__(var2, var1.getlocal(0), var1.getlocal(5)._add(PyString.fromInterned(".")), var1.getlocal(2)).__iter__();
                     break label69;
                  }

                  var1.setlocal(8, var7);
                  var1.setline(129);
                  if (var1.getlocal(3).__call__(var2, var1.getlocal(8)).__not__().__nonzero__()) {
                     var1.setline(129);
                     var1.getlocal(7).__call__(var2, var1.getlocal(8));
                  }
               }
            case 2:
               var8 = var1.f_savedlocals;
               var3 = (PyObject)var8[3];
               var4 = (PyObject)var8[4];
               var6 = (PyObject)var8[6];
               var7 = (PyObject)var8[7];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
         }

         var1.setline(131);
         var7 = var6.__iternext__();
         if (var7 != null) {
            var1.setlocal(9, var7);
            var1.setline(132);
            var1.setline(132);
            var18 = var1.getlocal(9);
            var1.f_lasti = 2;
            var8 = new Object[8];
            var8[3] = var3;
            var8[4] = var4;
            var8[6] = var6;
            var8[7] = var7;
            var1.f_savedlocals = var8;
            return var18;
         }
      }

      var1.setline(111);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var14 = Py.unpackSequence(var4, 3);
         var6 = var14[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var14[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var14[2];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(112);
         var1.setline(112);
         var14 = new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         PyTuple var21 = new PyTuple(var14);
         Arrays.fill(var14, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var21;
      }
   }

   public PyObject seen$9(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(108);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(109);
         PyObject var4 = var1.getglobal("True");
         var1.getlocal(1).__setitem__(var1.getlocal(0), var4);
         var4 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject iter_modules$10(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var14;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(144);
            PyString.fromInterned("Yields (module_loader, name, ispkg) for all submodules on path,\n    or, if path is None, all top-level modules on sys.path.\n\n    'path' should be either None or a list of paths to look for\n    modules in.\n\n    'prefix' is a string to output on the front of every module name\n    on output.\n    ");
            var1.setline(146);
            var3 = var1.getlocal(0);
            var14 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var14.__nonzero__()) {
               var1.setline(147);
               var3 = var1.getglobal("iter_importers").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(149);
               var3 = var1.getglobal("map").__call__(var2, var1.getglobal("get_importer"), var1.getlocal(0));
               var1.setlocal(2, var3);
               var3 = null;
            }

            var1.setline(151);
            PyObject[] var9 = Py.EmptyObjects;
            PyDictionary var15 = new PyDictionary(var9);
            Arrays.fill(var9, (Object)null);
            PyDictionary var10 = var15;
            var1.setlocal(3, var10);
            var3 = null;
            var1.setline(152);
            var3 = var1.getlocal(2).__iter__();
            var1.setline(152);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(153);
            var5 = var1.getglobal("iter_importer_modules").__call__(var2, var1.getlocal(4), var1.getlocal(1)).__iter__();
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

            var14 = (PyObject)var10000;
      }

      while(true) {
         while(true) {
            var1.setline(153);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(152);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(153);
               var5 = var1.getglobal("iter_importer_modules").__call__(var2, var1.getlocal(4), var1.getlocal(1)).__iter__();
            } else {
               PyObject[] var11 = Py.unpackSequence(var6, 2);
               PyObject var8 = var11[0];
               var1.setlocal(5, var8);
               var8 = null;
               var8 = var11[1];
               var1.setlocal(6, var8);
               var8 = null;
               var1.setline(154);
               PyObject var12 = var1.getlocal(5);
               var14 = var12._notin(var1.getlocal(3));
               var7 = null;
               if (var14.__nonzero__()) {
                  var1.setline(155);
                  PyInteger var13 = Py.newInteger(1);
                  var1.getlocal(3).__setitem__((PyObject)var1.getlocal(5), var13);
                  var7 = null;
                  var1.setline(156);
                  var1.setline(156);
                  var11 = new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
                  PyTuple var16 = new PyTuple(var11);
                  Arrays.fill(var11, (Object)null);
                  var1.f_lasti = 1;
                  var7 = new Object[9];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var5;
                  var7[6] = var6;
                  var1.f_savedlocals = var7;
                  return var16;
               }
            }
         }
      }
   }

   public PyObject iter_importer_modules$11(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("iter_modules")).__not__().__nonzero__()) {
         var1.setline(162);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(163);
         PyObject var3 = var1.getlocal(0).__getattr__("iter_modules").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ImpImporter$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("PEP 302 Importer that wraps Python's \"classic\" import algorithm\n\n    ImpImporter(dirname) produces a PEP 302 importer that searches that\n    directory.  ImpImporter(None) produces a PEP 302 importer that searches\n    the current sys.path, plus any modules that are frozen or built-in.\n\n    Note that ImpImporter does not currently support being used by placement\n    on sys.meta_path.\n    "));
      var1.setline(177);
      PyString.fromInterned("PEP 302 Importer that wraps Python's \"classic\" import algorithm\n\n    ImpImporter(dirname) produces a PEP 302 importer that searches that\n    directory.  ImpImporter(None) produces a PEP 302 importer that searches\n    the current sys.path, plus any modules that are frozen or built-in.\n\n    Note that ImpImporter does not currently support being used by placement\n    on sys.meta_path.\n    ");
      var1.setline(179);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(182);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, find_module$14, (PyObject)null);
      var1.setlocal("find_module", var4);
      var3 = null;
      var1.setline(197);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, iter_modules$15, (PyObject)null);
      var1.setlocal("iter_modules", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("path", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_module$14(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("path");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(186);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(187);
         PyObject var4 = var1.getlocal(0).__getattr__("path");
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(188);
            var4 = var1.getglobal("None");
            var1.setlocal(2, var4);
            var4 = null;
         } else {
            var1.setline(190);
            PyList var8 = new PyList(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getlocal(0).__getattr__("path"))});
            var1.setlocal(2, var8);
            var4 = null;
         }

         try {
            var1.setline(192);
            var4 = var1.getglobal("imp").__getattr__("find_module").__call__(var2, var1.getlocal(3), var1.getlocal(2));
            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (var9.match(var1.getglobal("ImportError"))) {
               var1.setline(194);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            throw var9;
         }

         var1.setline(195);
         var3 = var1.getglobal("ImpLoader").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject iter_modules$15(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var18;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(198);
            var3 = var1.getlocal(0).__getattr__("path");
            var18 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var18.__nonzero__()) {
               var18 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0).__getattr__("path")).__not__();
            }

            if (var18.__nonzero__()) {
               var1.setline(199);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(201);
            PyObject[] var9 = Py.EmptyObjects;
            PyDictionary var19 = new PyDictionary(var9);
            Arrays.fill(var9, (Object)null);
            PyDictionary var10 = var19;
            var1.setlocal(2, var10);
            var3 = null;
            var1.setline(202);
            var3 = imp.importOne("inspect", var1, -1);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(204);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("path"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(205);
            var1.getlocal(4).__getattr__("sort").__call__(var2);
            var1.setline(207);
            var3 = var1.getlocal(4).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var18 = (PyObject)var10000;
      }

      do {
         PyString var14;
         label79:
         while(true) {
            PyObject var11;
            do {
               var1.setline(207);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(5, var4);
               var1.setline(208);
               var11 = var1.getlocal(3).__getattr__("getmodulename").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var11);
               var5 = null;
               var1.setline(209);
               var11 = var1.getlocal(6);
               var18 = var11._eq(PyString.fromInterned("__init__"));
               var5 = null;
               if (!var18.__nonzero__()) {
                  var11 = var1.getlocal(6);
                  var18 = var11._in(var1.getlocal(2));
                  var5 = null;
               }
            } while(var18.__nonzero__());

            var1.setline(212);
            var11 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("path"), var1.getlocal(5));
            var1.setlocal(7, var11);
            var5 = null;
            var1.setline(213);
            var11 = var1.getglobal("False");
            var1.setlocal(8, var11);
            var5 = null;
            var1.setline(215);
            var18 = var1.getlocal(6).__not__();
            if (var18.__nonzero__()) {
               var18 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(7));
               if (var18.__nonzero__()) {
                  var14 = PyString.fromInterned(".");
                  var18 = var14._notin(var1.getlocal(5));
                  var5 = null;
               }
            }

            if (!var18.__nonzero__()) {
               break;
            }

            var1.setline(216);
            var11 = var1.getlocal(5);
            var1.setlocal(6, var11);
            var5 = null;

            try {
               var1.setline(218);
               var11 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(7));
               var1.setlocal(9, var11);
               var5 = null;
            } catch (Throwable var8) {
               PyException var15 = Py.setException(var8, var1);
               if (!var15.match(var1.getglobal("OSError"))) {
                  throw var15;
               }

               var1.setline(221);
               PyObject[] var6 = Py.EmptyObjects;
               PyList var20 = new PyList(var6);
               Arrays.fill(var6, (Object)null);
               PyList var12 = var20;
               var1.setlocal(9, var12);
               var6 = null;
            }

            var1.setline(222);
            var11 = var1.getlocal(9).__iter__();

            PyObject var7;
            do {
               var1.setline(222);
               PyObject var13 = var11.__iternext__();
               if (var13 == null) {
                  continue label79;
               }

               var1.setlocal(5, var13);
               var1.setline(223);
               var7 = var1.getlocal(3).__getattr__("getmodulename").__call__(var2, var1.getlocal(5));
               var1.setlocal(10, var7);
               var7 = null;
               var1.setline(224);
               var7 = var1.getlocal(10);
               var18 = var7._eq(PyString.fromInterned("__init__"));
               var7 = null;
            } while(!var18.__nonzero__());

            var1.setline(225);
            var7 = var1.getglobal("True");
            var1.setlocal(8, var7);
            var7 = null;
            break;
         }

         var1.setline(230);
         var18 = var1.getlocal(6);
         if (var18.__nonzero__()) {
            var14 = PyString.fromInterned(".");
            var18 = var14._notin(var1.getlocal(6));
            var5 = null;
         }
      } while(!var18.__nonzero__());

      var1.setline(231);
      PyInteger var16 = Py.newInteger(1);
      var1.getlocal(2).__setitem__((PyObject)var1.getlocal(6), var16);
      var5 = null;
      var1.setline(232);
      var1.setline(232);
      PyObject[] var17 = new PyObject[]{var1.getlocal(1)._add(var1.getlocal(6)), var1.getlocal(8)};
      PyTuple var21 = new PyTuple(var17);
      Arrays.fill(var17, (Object)null);
      var1.f_lasti = 1;
      var5 = new Object[9];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var21;
   }

   public PyObject ImpLoader$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("PEP 302 Loader that wraps Python's \"classic\" import algorithm\n    "));
      var1.setline(237);
      PyString.fromInterned("PEP 302 Loader that wraps Python's \"classic\" import algorithm\n    ");
      var1.setline(238);
      PyObject var3 = var1.getname("None");
      var1.setlocal("code", var3);
      var1.setlocal("source", var3);
      var1.setline(240);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(246);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, load_module$18, (PyObject)null);
      var1.setlocal("load_module", var5);
      var3 = null;
      var1.setline(257);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_data$19, (PyObject)null);
      var1.setlocal("get_data", var5);
      var3 = null;
      var1.setline(264);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _reopen$20, (PyObject)null);
      var1.setlocal("_reopen", var5);
      var3 = null;
      var1.setline(272);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _fix_name$21, (PyObject)null);
      var1.setlocal("_fix_name", var5);
      var3 = null;
      var1.setline(280);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_package$22, (PyObject)null);
      var1.setlocal("is_package", var5);
      var3 = null;
      var1.setline(284);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, get_code$23, (PyObject)null);
      var1.setlocal("get_code", var5);
      var3 = null;
      var1.setline(301);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, get_source$24, (PyObject)null);
      var1.setlocal("get_source", var5);
      var3 = null;
      var1.setline(323);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_delegate$25, (PyObject)null);
      var1.setlocal("_get_delegate", var5);
      var3 = null;
      var1.setline(326);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, get_filename$26, (PyObject)null);
      var1.setlocal("get_filename", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(242);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fullname", var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("etc", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_module$18(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      var1.getlocal(0).__getattr__("_reopen").__call__(var2);
      PyObject var3 = null;

      try {
         var1.setline(249);
         PyObject var4 = var1.getglobal("imp").__getattr__("load_module").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("file"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("etc"));
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(251);
         if (var1.getlocal(0).__getattr__("file").__nonzero__()) {
            var1.setline(252);
            var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
         }

         throw (Throwable)var5;
      }

      var1.setline(251);
      if (var1.getlocal(0).__getattr__("file").__nonzero__()) {
         var1.setline(252);
         var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      }

      var1.setline(255);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_data$19(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(260);
            var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(262);
         var1.getlocal(2).__getattr__("close").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(262);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject _reopen$20(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var10000 = var1.getlocal(0).__getattr__("file");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("file").__getattr__("closed");
      }

      if (var10000.__nonzero__()) {
         var1.setline(266);
         PyObject var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(267);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("imp").__getattr__("PY_SOURCE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(268);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("filename"), (PyObject)PyString.fromInterned("rU"));
            var1.getlocal(0).__setattr__("file", var3);
            var3 = null;
         } else {
            var1.setline(269);
            var3 = var1.getlocal(1);
            var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("imp").__getattr__("PY_COMPILED"), var1.getglobal("imp").__getattr__("C_EXTENSION")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(270);
               var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("filename"), (PyObject)PyString.fromInterned("rb"));
               var1.getlocal(0).__setattr__("file", var3);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _fix_name$21(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(274);
         var3 = var1.getlocal(0).__getattr__("fullname");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(275);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("fullname"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(276);
            throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("Loader for module %s cannot handle module %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("fullname"), var1.getlocal(1)}))));
         }
      }

      var1.setline(278);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_package$22(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_name").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(282);
      var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
      PyObject var10000 = var3._eq(var1.getglobal("imp").__getattr__("PKG_DIRECTORY"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_code$23(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_name").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(286);
      var3 = var1.getlocal(0).__getattr__("code");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(287);
         var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(288);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("imp").__getattr__("PY_SOURCE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(289);
            var3 = var1.getlocal(0).__getattr__("get_source").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(290);
            var3 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getlocal(0).__getattr__("filename"), (PyObject)PyString.fromInterned("exec"));
            var1.getlocal(0).__setattr__("code", var3);
            var3 = null;
         } else {
            var1.setline(291);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("imp").__getattr__("PY_COMPILED"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(292);
               var1.getlocal(0).__getattr__("_reopen").__call__(var2);
               var3 = null;

               try {
                  var1.setline(294);
                  PyObject var4 = var1.getglobal("read_jython_code").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("file"), var1.getlocal(0).__getattr__("filename"));
                  var1.getlocal(0).__setattr__("code", var4);
                  var4 = null;
               } catch (Throwable var5) {
                  Py.addTraceback(var5, var1);
                  var1.setline(296);
                  var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
                  throw (Throwable)var5;
               }

               var1.setline(296);
               var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
            } else {
               var1.setline(297);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("imp").__getattr__("PKG_DIRECTORY"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(298);
                  var3 = var1.getlocal(0).__getattr__("_get_delegate").__call__(var2).__getattr__("get_code").__call__(var2);
                  var1.getlocal(0).__setattr__("code", var3);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(299);
      var3 = var1.getlocal(0).__getattr__("code");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_source$24(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_name").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(303);
      var3 = var1.getlocal(0).__getattr__("source");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(304);
         var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(305);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("imp").__getattr__("PY_SOURCE"));
         var3 = null;
         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(306);
            var1.getlocal(0).__getattr__("_reopen").__call__(var2);
            var3 = null;

            try {
               var1.setline(308);
               var4 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__(var2);
               var1.getlocal(0).__setattr__("source", var4);
               var4 = null;
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(310);
               var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
               throw (Throwable)var6;
            }

            var1.setline(310);
            var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
         } else {
            var1.setline(311);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("imp").__getattr__("PY_COMPILED"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(312);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("filename").__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)).__nonzero__()) {
                  var1.setline(313);
                  var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("filename").__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), (PyObject)PyString.fromInterned("rU"));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var3 = null;

                  try {
                     var1.setline(315);
                     var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
                     var1.getlocal(0).__setattr__("source", var4);
                     var4 = null;
                  } catch (Throwable var5) {
                     Py.addTraceback(var5, var1);
                     var1.setline(317);
                     var1.getlocal(3).__getattr__("close").__call__(var2);
                     throw (Throwable)var5;
                  }

                  var1.setline(317);
                  var1.getlocal(3).__getattr__("close").__call__(var2);
               }
            } else {
               var1.setline(318);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("imp").__getattr__("PKG_DIRECTORY"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(319);
                  var3 = var1.getlocal(0).__getattr__("_get_delegate").__call__(var2).__getattr__("get_source").__call__(var2);
                  var1.getlocal(0).__setattr__("source", var3);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(320);
      var3 = var1.getlocal(0).__getattr__("source");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_delegate$25(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getglobal("ImpImporter").__call__(var2, var1.getlocal(0).__getattr__("filename")).__getattr__("find_module").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__init__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_filename$26(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_name").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(328);
      var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(329);
      var3 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
      PyObject var10000 = var3._eq(var1.getglobal("imp").__getattr__("PKG_DIRECTORY"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(330);
         var3 = var1.getlocal(0).__getattr__("_get_delegate").__call__(var2).__getattr__("get_filename").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(331);
         PyObject var4 = var1.getlocal(0).__getattr__("etc").__getitem__(Py.newInteger(2));
         var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("imp").__getattr__("PY_SOURCE"), var1.getglobal("imp").__getattr__("PY_COMPILED"), var1.getglobal("imp").__getattr__("C_EXTENSION")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(332);
            var3 = var1.getlocal(0).__getattr__("filename");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(333);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject iter_zipimport_modules$27(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      PyString var9;
      PyInteger var10;
      PyObject[] var11;
      PyObject var12;
      PyTuple var13;
      Object var10000;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(341);
            var3 = var1.getglobal("zipimport").__getattr__("_zip_directory_cache").__getitem__(var1.getlocal(0).__getattr__("archive")).__getattr__("keys").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(342);
            var1.getlocal(2).__getattr__("sort").__call__(var2);
            var1.setline(343);
            var3 = var1.getlocal(0).__getattr__("prefix");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(344);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(345);
            PyObject[] var6 = Py.EmptyObjects;
            PyDictionary var14 = new PyDictionary(var6);
            Arrays.fill(var6, (Object)null);
            PyDictionary var8 = var14;
            var1.setlocal(5, var8);
            var3 = null;
            var1.setline(346);
            var3 = imp.importOne("inspect", var1, -1);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(347);
            var3 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var12 = (PyObject)var10000;
            var1.setline(358);
            var7 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var12 = var7._ne(Py.newInteger(1));
            var5 = null;
            if (!var12.__nonzero__()) {
               var1.setline(361);
               var7 = var1.getlocal(6).__getattr__("getmodulename").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0)));
               var1.setlocal(8, var7);
               var5 = null;
               var1.setline(362);
               var7 = var1.getlocal(8);
               var12 = var7._eq(PyString.fromInterned("__init__"));
               var5 = null;
               if (!var12.__nonzero__()) {
                  var1.setline(365);
                  var12 = var1.getlocal(8);
                  if (var12.__nonzero__()) {
                     var9 = PyString.fromInterned(".");
                     var12 = var9._notin(var1.getlocal(8));
                     var5 = null;
                     if (var12.__nonzero__()) {
                        var7 = var1.getlocal(8);
                        var12 = var7._notin(var1.getlocal(5));
                        var5 = null;
                     }
                  }

                  if (var12.__nonzero__()) {
                     var1.setline(366);
                     var10 = Py.newInteger(1);
                     var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var10);
                     var5 = null;
                     var1.setline(367);
                     var1.setline(367);
                     var11 = new PyObject[]{var1.getlocal(1)._add(var1.getlocal(8)), var1.getglobal("False")};
                     var13 = new PyTuple(var11);
                     Arrays.fill(var11, (Object)null);
                     var1.f_lasti = 2;
                     var5 = new Object[7];
                     var5[3] = var3;
                     var5[4] = var4;
                     var1.f_savedlocals = var5;
                     return var13;
                  }
               }
            }
            break;
         case 2:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var12 = (PyObject)var10000;
      }

      while(true) {
         do {
            var1.setline(347);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(7, var4);
            var1.setline(348);
         } while(var1.getlocal(7).__getattr__("startswith").__call__(var2, var1.getlocal(3)).__not__().__nonzero__());

         var1.setline(351);
         var7 = var1.getlocal(7).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("sep"));
         var1.setlocal(7, var7);
         var5 = null;
         var1.setline(353);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var12 = var7._eq(Py.newInteger(2));
         var5 = null;
         if (var12.__nonzero__()) {
            var12 = var1.getlocal(7).__getitem__(Py.newInteger(1)).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__init__.py"));
         }

         if (var12.__nonzero__()) {
            var1.setline(354);
            var7 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var12 = var7._notin(var1.getlocal(5));
            var5 = null;
            if (var12.__nonzero__()) {
               var1.setline(355);
               var10 = Py.newInteger(1);
               var1.getlocal(5).__setitem__((PyObject)var1.getlocal(7).__getitem__(Py.newInteger(0)), var10);
               var5 = null;
               var1.setline(356);
               var1.setline(356);
               var11 = new PyObject[]{var1.getlocal(7).__getitem__(Py.newInteger(0)), var1.getglobal("True")};
               var13 = new PyTuple(var11);
               Arrays.fill(var11, (Object)null);
               var1.f_lasti = 1;
               var5 = new Object[7];
               var5[3] = var3;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var13;
            }
         }

         var1.setline(358);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var12 = var7._ne(Py.newInteger(1));
         var5 = null;
         if (!var12.__nonzero__()) {
            var1.setline(361);
            var7 = var1.getlocal(6).__getattr__("getmodulename").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0)));
            var1.setlocal(8, var7);
            var5 = null;
            var1.setline(362);
            var7 = var1.getlocal(8);
            var12 = var7._eq(PyString.fromInterned("__init__"));
            var5 = null;
            if (!var12.__nonzero__()) {
               var1.setline(365);
               var12 = var1.getlocal(8);
               if (var12.__nonzero__()) {
                  var9 = PyString.fromInterned(".");
                  var12 = var9._notin(var1.getlocal(8));
                  var5 = null;
                  if (var12.__nonzero__()) {
                     var7 = var1.getlocal(8);
                     var12 = var7._notin(var1.getlocal(5));
                     var5 = null;
                  }
               }

               if (var12.__nonzero__()) {
                  var1.setline(366);
                  var10 = Py.newInteger(1);
                  var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var10);
                  var5 = null;
                  var1.setline(367);
                  var1.setline(367);
                  var11 = new PyObject[]{var1.getlocal(1)._add(var1.getlocal(8)), var1.getglobal("False")};
                  var13 = new PyTuple(var11);
                  Arrays.fill(var11, (Object)null);
                  var1.f_lasti = 2;
                  var5 = new Object[7];
                  var5[3] = var3;
                  var5[4] = var4;
                  var1.f_savedlocals = var5;
                  return var13;
               }
            }
         }
      }
   }

   public PyObject get_importer$28(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyString.fromInterned("Retrieve a PEP 302 importer for the given path item\n\n    The returned importer is cached in sys.path_importer_cache\n    if it was newly created by a path hook.\n\n    If there is no importer, a wrapper around the basic import\n    machinery is returned. This wrapper is never inserted into\n    the importer cache (None is inserted instead).\n\n    The cache (or part of it) can be cleared manually if a\n    rescan of sys.path_hooks is necessary.\n    ");

      PyException var3;
      PyObject var4;
      PyObject var10;
      try {
         var1.setline(389);
         var10 = var1.getglobal("sys").__getattr__("path_importer_cache").__getitem__(var1.getlocal(0));
         var1.setlocal(1, var10);
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(391);
         var4 = var1.getglobal("sys").__getattr__("path_hooks").__iter__();

         while(true) {
            var1.setline(391);
            PyObject var5 = var4.__iternext__();
            PyException var6;
            PyObject var11;
            if (var5 == null) {
               var1.setline(398);
               var11 = var1.getglobal("None");
               var1.setlocal(1, var11);
               var6 = null;
               break;
            }

            var1.setlocal(2, var5);

            try {
               var1.setline(393);
               var11 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
               var1.setlocal(1, var11);
               var6 = null;
               break;
            } catch (Throwable var8) {
               var6 = Py.setException(var8, var1);
               if (!var6.match(var1.getglobal("ImportError"))) {
                  throw var6;
               }

               var1.setline(396);
            }
         }

         var1.setline(399);
         var1.getglobal("sys").__getattr__("path_importer_cache").__getattr__("setdefault").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      }

      var1.setline(401);
      var10 = var1.getlocal(1);
      PyObject var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(403);
            var10 = var1.getglobal("ImpImporter").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var10);
            var3 = null;
         } catch (Throwable var7) {
            var3 = Py.setException(var7, var1);
            if (!var3.match(var1.getglobal("ImportError"))) {
               throw var3;
            }

            var1.setline(405);
            var4 = var1.getglobal("None");
            var1.setlocal(1, var4);
            var4 = null;
         }
      }

      var1.setline(406);
      var10 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject iter_importers$29(PyFrame var1, ThreadState var2) {
      label65: {
         Object[] var3;
         PyObject var4;
         Object[] var5;
         PyObject var6;
         PyString var7;
         PyObject var10;
         label60: {
            label66: {
               Object var10000;
               switch (var1.f_lasti) {
                  case 0:
                  default:
                     var1.setline(432);
                     PyString.fromInterned("Yield PEP 302 importers for the given module name\n\n    If fullname contains a '.', the importers will be for the package\n    containing fullname, otherwise they will be importers for sys.meta_path,\n    sys.path, and Python's \"classic\" import machinery, in that order.  If\n    the named module is in a package, that package is imported as a side\n    effect of invoking this function.\n\n    Non PEP 302 mechanisms (e.g. the Windows registry) used by the\n    standard import machinery to find files in alternative locations\n    are partially supported, but are searched AFTER sys.path. Normally,\n    these locations are searched BEFORE sys.path, preventing sys.path\n    entries from shadowing them.\n\n    For this to cause a visible difference in behaviour, there must\n    be a module or package name that is accessible via both sys.path\n    and one of the non PEP 302 file system mechanisms. In this case,\n    the emulation will find the former version, while the builtin\n    import mechanism will find the latter.\n\n    Items of the following types can be affected by this discrepancy:\n        imp.C_EXTENSION, imp.PY_SOURCE, imp.PY_COMPILED, imp.PKG_DIRECTORY\n    ");
                     var1.setline(433);
                     if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
                        var1.setline(434);
                        throw Py.makeException(var1.getglobal("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Relative module names not supported")));
                     }

                     var1.setline(435);
                     var7 = PyString.fromInterned(".");
                     var10 = var7._in(var1.getlocal(0));
                     var3 = null;
                     if (var10.__nonzero__()) {
                        var1.setline(437);
                        var6 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
                        var1.setlocal(1, var6);
                        var3 = null;
                        var1.setline(438);
                        var6 = var1.getlocal(1);
                        var10 = var6._notin(var1.getglobal("sys").__getattr__("modules"));
                        var3 = null;
                        if (var10.__nonzero__()) {
                           var1.setline(439);
                           var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
                        }

                        var1.setline(440);
                        var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1)), (PyObject)PyString.fromInterned("__path__"), (PyObject)var1.getglobal("None"));
                        if (!((PyObject)var10000).__nonzero__()) {
                           PyObject[] var8 = Py.EmptyObjects;
                           var10000 = new PyList(var8);
                           Arrays.fill(var8, (Object)null);
                        }

                        Object var9 = var10000;
                        var1.setlocal(2, (PyObject)var9);
                        var3 = null;
                        break label66;
                     }

                     var1.setline(442);
                     var6 = var1.getglobal("sys").__getattr__("meta_path").__iter__();
                     break;
                  case 1:
                     var5 = var1.f_savedlocals;
                     var6 = (PyObject)var5[3];
                     var4 = (PyObject)var5[4];
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var10 = (PyObject)var10000;
                     break;
                  case 2:
                     var5 = var1.f_savedlocals;
                     var6 = (PyObject)var5[3];
                     var4 = (PyObject)var5[4];
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var10 = (PyObject)var10000;
                     break label60;
                  case 3:
                     var3 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var10 = (PyObject)var10000;
                     break label65;
               }

               var1.setline(442);
               var4 = var6.__iternext__();
               if (var4 != null) {
                  var1.setlocal(3, var4);
                  var1.setline(443);
                  var1.setline(443);
                  var10 = var1.getlocal(3);
                  var1.f_lasti = 1;
                  var5 = new Object[]{null, null, null, var6, var4};
                  var1.f_savedlocals = var5;
                  return var10;
               }

               var1.setline(444);
               var6 = var1.getglobal("sys").__getattr__("path");
               var1.setlocal(2, var6);
               var3 = null;
            }

            var1.setline(445);
            var6 = var1.getlocal(2).__iter__();
         }

         var1.setline(445);
         var4 = var6.__iternext__();
         if (var4 != null) {
            var1.setlocal(4, var4);
            var1.setline(446);
            var1.setline(446);
            var10 = var1.getglobal("get_importer").__call__(var2, var1.getlocal(4));
            var1.f_lasti = 2;
            var5 = new Object[]{null, null, null, var6, var4, null};
            var1.f_savedlocals = var5;
            return var10;
         }

         var1.setline(447);
         var7 = PyString.fromInterned(".");
         var10 = var7._notin(var1.getlocal(0));
         var3 = null;
         if (var10.__nonzero__()) {
            var1.setline(448);
            var1.setline(448);
            var10 = var1.getglobal("ImpImporter").__call__(var2);
            var1.f_lasti = 3;
            var3 = new Object[6];
            var1.f_savedlocals = var3;
            return var10;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_loader$30(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyString.fromInterned("Get a PEP 302 \"loader\" object for module_or_name\n\n    If the module or package is accessible via the normal import\n    mechanism, a wrapper around the relevant part of that machinery\n    is returned.  Returns None if the module cannot be found or imported.\n    If the named module is not already imported, its containing package\n    (if any) is imported, in order to establish the package __path__.\n\n    This function uses iter_importers(), and is thus subject to the same\n    limitations regarding platform-specific special import locations such\n    as the Windows registry.\n    ");
      var1.setline(463);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("sys").__getattr__("modules"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(465);
      PyObject var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("ModuleType")).__nonzero__()) {
         var1.setline(466);
         var3 = var1.getlocal(0);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(467);
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__loader__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(468);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(469);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(470);
         var4 = var1.getlocal(1).__getattr__("__name__");
         var1.setlocal(3, var4);
         var4 = null;
      } else {
         var1.setline(471);
         var4 = var1.getlocal(0);
         var10000 = var4._eq(var1.getglobal("sys"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(475);
            PyString var5 = PyString.fromInterned("sys");
            var1.setlocal(3, var5);
            var4 = null;
         } else {
            var1.setline(477);
            var4 = var1.getlocal(0);
            var1.setlocal(3, var4);
            var4 = null;
         }
      }

      var1.setline(478);
      var3 = var1.getglobal("find_loader").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_loader$31(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyString.fromInterned("Find a PEP 302 \"loader\" object for fullname\n\n    If fullname contains dots, path must be the containing package's __path__.\n    Returns None if the module cannot be found or imported. This function uses\n    iter_importers(), and is thus subject to the same limitations regarding\n    platform-specific special import locations such as the Windows registry.\n    ");
      var1.setline(488);
      PyObject var3 = var1.getglobal("iter_importers").__call__(var2, var1.getlocal(0)).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(488);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(493);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(1, var4);
         var1.setline(489);
         var5 = var1.getlocal(1).__getattr__("find_module").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(490);
         var5 = var1.getlocal(2);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(491);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject extend_path$32(PyFrame var1, ThreadState var2) {
      var1.setline(527);
      PyString.fromInterned("Extend a package's path.\n\n    Intended use is to place the following code in a package's __init__.py:\n\n        from pkgutil import extend_path\n        __path__ = extend_path(__path__, __name__)\n\n    This will add to the package's __path__ all subdirectories of\n    directories on sys.path named after the package.  This is useful\n    if one wants to distribute different parts of a single logical\n    package as multiple directories.\n\n    It also looks for *.pkg files beginning where * matches the name\n    argument.  This feature is similar to *.pth files (see site.py),\n    except that it doesn't special-case lines starting with 'import'.\n    A *.pkg file is trusted at face value: apart from checking for\n    duplicates, all entries found in a *.pkg file are added to the\n    path, regardless of whether they are exist the filesystem.  (This\n    is a feature.)\n\n    If the input path is not a list (as is the case for frozen\n    packages) it is returned unchanged.  The input path is not\n    modified; an extended copy is returned.  Items are only appended\n    to the copy at the end.\n\n    It is assumed that sys.path is a sequence.  Items of sys.path that\n    are not (unicode or 8-bit) strings referring to existing\n    directories are ignored.  Unicode items of sys.path that cause\n    errors when used as filenames may cause this function to raise an\n    exception (in line with os.path.isdir() behavior).\n    ");
      var1.setline(529);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(532);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(534);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         PyObject[] var4 = Py.EmptyObjects;
         String[] var5 = new String[0];
         var10000 = var10000._callextra(var4, var5, var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")), (PyObject)null);
         var4 = null;
         PyObject var13 = var10000;
         var1.setlocal(2, var13);
         var4 = null;
         var1.setline(536);
         var13 = var1.getglobal("os").__getattr__("extsep").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")));
         var1.setlocal(3, var13);
         var4 = null;
         var1.setline(537);
         var13 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("extsep"))._add(PyString.fromInterned("pkg"));
         var1.setlocal(4, var13);
         var4 = null;
         var1.setline(538);
         var13 = PyString.fromInterned("__init__")._add(var1.getglobal("os").__getattr__("extsep"))._add(PyString.fromInterned("py"));
         var1.setlocal(5, var13);
         var4 = null;
         var1.setline(540);
         var13 = var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(0, var13);
         var4 = null;
         var1.setline(542);
         var13 = var1.getglobal("sys").__getattr__("path").__iter__();

         while(true) {
            PyObject var7;
            while(true) {
               PyObject var6;
               do {
                  do {
                     var1.setline(542);
                     PyObject var14 = var13.__iternext__();
                     if (var14 == null) {
                        var1.setline(570);
                        var3 = var1.getlocal(0);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(6, var14);
                     var1.setline(543);
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("basestring")).__not__();
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(6)).__not__();
                     }
                  } while(var10000.__nonzero__());

                  var1.setline(545);
                  var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(2));
                  var1.setlocal(7, var6);
                  var6 = null;
                  var1.setline(548);
                  var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(7), var1.getlocal(5));
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(549);
                  var6 = var1.getlocal(7);
                  var10000 = var6._notin(var1.getlocal(0));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(8));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(550);
                     var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(7));
                  }

                  var1.setline(553);
                  var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(4));
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(554);
               } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(9)).__nonzero__());

               try {
                  var1.setline(556);
                  var6 = var1.getglobal("open").__call__(var2, var1.getlocal(9));
                  var1.setlocal(10, var6);
                  var6 = null;
                  break;
               } catch (Throwable var11) {
                  PyException var15 = Py.setException(var11, var1);
                  if (!var15.match(var1.getglobal("IOError"))) {
                     throw var15;
                  }

                  var7 = var15.value;
                  var1.setlocal(11, var7);
                  var7 = null;
                  var1.setline(558);
                  var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("Can't open %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(11)})));
               }
            }

            var7 = null;

            try {
               var1.setline(562);
               PyObject var8 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(562);
                  PyObject var9 = var8.__iternext__();
                  if (var9 == null) {
                     break;
                  }

                  var1.setlocal(12, var9);
                  var1.setline(563);
                  PyObject var10 = var1.getlocal(12).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
                  var1.setlocal(12, var10);
                  var10 = null;
                  var1.setline(564);
                  var10000 = var1.getlocal(12).__not__();
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(12).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
                  }

                  if (!var10000.__nonzero__()) {
                     var1.setline(566);
                     var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(12));
                  }
               }
            } catch (Throwable var12) {
               Py.addTraceback(var12, var1);
               var1.setline(568);
               var1.getlocal(10).__getattr__("close").__call__(var2);
               throw (Throwable)var12;
            }

            var1.setline(568);
            var1.getlocal(10).__getattr__("close").__call__(var2);
         }
      }
   }

   public PyObject get_data$33(PyFrame var1, ThreadState var2) {
      var1.setline(592);
      PyString.fromInterned("Get a resource from a package.\n\n    This is a wrapper round the PEP 302 loader get_data API. The package\n    argument should be the name of a package, in standard module format\n    (foo.bar). The resource argument should be in the form of a relative\n    filename, using '/' as the path separator. The parent directory name '..'\n    is not allowed, and nor is a rooted name (starting with a '/').\n\n    The function returns a binary string, which is the contents of the\n    specified resource.\n\n    For packages located in the filesystem, which have already been imported,\n    this is the rough equivalent of\n\n        d = os.path.dirname(sys.modules[package].__file__)\n        data = open(os.path.join(d, resource), 'rb').read()\n\n    If the package cannot be located or loaded, or it uses a PEP 302 loader\n    which does not support get_data(), then None is returned.\n    ");
      var1.setline(594);
      PyObject var3 = var1.getglobal("get_loader").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(595);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("get_data")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(596);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(597);
         var10000 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getattr__("load_module").__call__(var2, var1.getlocal(0));
         }

         PyObject var4 = var10000;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(598);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("__file__")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(599);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(604);
            var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(605);
            var1.getlocal(4).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3).__getattr__("__file__")));
            var1.setline(606);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var6 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var6, var5, var1.getlocal(4), (PyObject)null);
            var4 = null;
            var4 = var10000;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(607);
            var3 = var1.getlocal(2).__getattr__("get_data").__call__(var2, var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public pkgutil$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fullname", "stream", "filename", "data"};
      read_jython_code$1 = Py.newCode(3, var2, var1, "read_jython_code", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream", "filename", "fullname"};
      read_code$2 = Py.newCode(1, var2, var1, "read_code", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "wrapper", "registry", "register"};
      String[] var10001 = var2;
      pkgutil$py var10007 = self;
      var2 = new String[]{"registry", "register", "func"};
      simplegeneric$3 = Py.newCode(1, var10001, var1, "simplegeneric", 37, false, false, var10007, 3, var2, (String[])null, 2, 4097);
      var2 = new String[]{"args", "kw", "ob", "cls", "mro", "t"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"registry", "func"};
      wrapper$4 = Py.newCode(2, var10001, var1, "wrapper", 40, true, true, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      cls$5 = Py.newCode(0, var2, var1, "cls", 50, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"typ", "func"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"typ"};
      String[] var10009 = var2;
      var2 = new String[]{"registry", "register"};
      register$6 = Py.newCode(2, var10001, var1, "register", 65, false, false, var10007, 6, var10009, var2, 0, 4097);
      var2 = new String[]{"f"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"register", "typ"};
      f$7 = Py.newCode(1, var10001, var1, "<lambda>", 67, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path", "prefix", "onerror", "seen", "importer", "name", "ispkg", "_[129_24]", "p", "item"};
      walk_packages$8 = Py.newCode(3, var2, var1, "walk_packages", 77, false, false, self, 8, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"p", "m"};
      seen$9 = Py.newCode(2, var2, var1, "seen", 106, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "prefix", "importers", "yielded", "i", "name", "ispkg"};
      iter_modules$10 = Py.newCode(2, var2, var1, "iter_modules", 135, false, false, self, 10, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"importer", "prefix"};
      iter_importer_modules$11 = Py.newCode(2, var2, var1, "iter_importer_modules", 160, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ImpImporter$12 = Py.newCode(0, var2, var1, "ImpImporter", 168, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 179, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "path", "subname", "file", "filename", "etc"};
      find_module$14 = Py.newCode(3, var2, var1, "find_module", 182, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "yielded", "inspect", "filenames", "fn", "modname", "path", "ispkg", "dircontents", "subname"};
      iter_modules$15 = Py.newCode(2, var2, var1, "iter_modules", 197, false, false, self, 15, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      ImpLoader$16 = Py.newCode(0, var2, var1, "ImpLoader", 235, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fullname", "file", "filename", "etc"};
      __init__$17 = Py.newCode(5, var2, var1, "__init__", 240, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "mod"};
      load_module$18 = Py.newCode(2, var2, var1, "load_module", 246, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pathname", "f"};
      get_data$19 = Py.newCode(2, var2, var1, "get_data", 257, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mod_type"};
      _reopen$20 = Py.newCode(1, var2, var1, "_reopen", 264, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname"};
      _fix_name$21 = Py.newCode(2, var2, var1, "_fix_name", 272, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname"};
      is_package$22 = Py.newCode(2, var2, var1, "is_package", 280, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "mod_type", "source"};
      get_code$23 = Py.newCode(2, var2, var1, "get_code", 284, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "mod_type", "f"};
      get_source$24 = Py.newCode(2, var2, var1, "get_source", 301, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_delegate$25 = Py.newCode(1, var2, var1, "_get_delegate", 323, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "mod_type"};
      get_filename$26 = Py.newCode(2, var2, var1, "get_filename", 326, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"importer", "prefix", "dirlist", "_prefix", "plen", "yielded", "inspect", "fn", "modname"};
      iter_zipimport_modules$27 = Py.newCode(2, var2, var1, "iter_zipimport_modules", 340, false, false, self, 27, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"path_item", "importer", "path_hook"};
      get_importer$28 = Py.newCode(1, var2, var1, "get_importer", 375, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fullname", "pkg", "path", "importer", "item"};
      iter_importers$29 = Py.newCode(1, var2, var1, "iter_importers", 409, false, false, self, 29, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"module_or_name", "module", "loader", "fullname"};
      get_loader$30 = Py.newCode(1, var2, var1, "get_loader", 450, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fullname", "importer", "loader"};
      find_loader$31 = Py.newCode(1, var2, var1, "find_loader", 480, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "name", "pname", "sname", "sname_pkg", "init_py", "dir", "subdir", "initfile", "pkgfile", "f", "msg", "line"};
      extend_path$32 = Py.newCode(2, var2, var1, "extend_path", 496, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"package", "resource", "loader", "mod", "parts", "resource_name"};
      get_data$33 = Py.newCode(2, var2, var1, "get_data", 572, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pkgutil$py("pkgutil$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pkgutil$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.read_jython_code$1(var2, var3);
         case 2:
            return this.read_code$2(var2, var3);
         case 3:
            return this.simplegeneric$3(var2, var3);
         case 4:
            return this.wrapper$4(var2, var3);
         case 5:
            return this.cls$5(var2, var3);
         case 6:
            return this.register$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.walk_packages$8(var2, var3);
         case 9:
            return this.seen$9(var2, var3);
         case 10:
            return this.iter_modules$10(var2, var3);
         case 11:
            return this.iter_importer_modules$11(var2, var3);
         case 12:
            return this.ImpImporter$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.find_module$14(var2, var3);
         case 15:
            return this.iter_modules$15(var2, var3);
         case 16:
            return this.ImpLoader$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.load_module$18(var2, var3);
         case 19:
            return this.get_data$19(var2, var3);
         case 20:
            return this._reopen$20(var2, var3);
         case 21:
            return this._fix_name$21(var2, var3);
         case 22:
            return this.is_package$22(var2, var3);
         case 23:
            return this.get_code$23(var2, var3);
         case 24:
            return this.get_source$24(var2, var3);
         case 25:
            return this._get_delegate$25(var2, var3);
         case 26:
            return this.get_filename$26(var2, var3);
         case 27:
            return this.iter_zipimport_modules$27(var2, var3);
         case 28:
            return this.get_importer$28(var2, var3);
         case 29:
            return this.iter_importers$29(var2, var3);
         case 30:
            return this.get_loader$30(var2, var3);
         case 31:
            return this.find_loader$31(var2, var3);
         case 32:
            return this.extend_path$32(var2, var3);
         case 33:
            return this.get_data$33(var2, var3);
         default:
            return null;
      }
   }
}

package lib2to3.fixes;

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
@Filename("lib2to3/fixes/fix_imports.py")
public class fix_imports$py extends PyFunctionTable implements PyRunnable {
   static fix_imports$py self;
   static final PyCode f$0;
   static final PyCode alternates$1;
   static final PyCode build_pattern$2;
   static final PyCode FixImports$3;
   static final PyCode build_pattern$4;
   static final PyCode compile_pattern$5;
   static final PyCode match$6;
   static final PyCode f$7;
   static final PyCode start_tree$8;
   static final PyCode transform$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fix incompatible imports and module references."));
      var1.setline(1);
      PyString.fromInterned("Fix incompatible imports and module references.");
      var1.setline(5);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(6);
      var3 = new String[]{"Name", "attr_chain"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("attr_chain", var4);
      var4 = null;
      var1.setline(8);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("StringIO"), PyString.fromInterned("io"), PyString.fromInterned("cStringIO"), PyString.fromInterned("io"), PyString.fromInterned("cPickle"), PyString.fromInterned("pickle"), PyString.fromInterned("__builtin__"), PyString.fromInterned("builtins"), PyString.fromInterned("copy_reg"), PyString.fromInterned("copyreg"), PyString.fromInterned("Queue"), PyString.fromInterned("queue"), PyString.fromInterned("SocketServer"), PyString.fromInterned("socketserver"), PyString.fromInterned("ConfigParser"), PyString.fromInterned("configparser"), PyString.fromInterned("repr"), PyString.fromInterned("reprlib"), PyString.fromInterned("FileDialog"), PyString.fromInterned("tkinter.filedialog"), PyString.fromInterned("tkFileDialog"), PyString.fromInterned("tkinter.filedialog"), PyString.fromInterned("SimpleDialog"), PyString.fromInterned("tkinter.simpledialog"), PyString.fromInterned("tkSimpleDialog"), PyString.fromInterned("tkinter.simpledialog"), PyString.fromInterned("tkColorChooser"), PyString.fromInterned("tkinter.colorchooser"), PyString.fromInterned("tkCommonDialog"), PyString.fromInterned("tkinter.commondialog"), PyString.fromInterned("Dialog"), PyString.fromInterned("tkinter.dialog"), PyString.fromInterned("Tkdnd"), PyString.fromInterned("tkinter.dnd"), PyString.fromInterned("tkFont"), PyString.fromInterned("tkinter.font"), PyString.fromInterned("tkMessageBox"), PyString.fromInterned("tkinter.messagebox"), PyString.fromInterned("ScrolledText"), PyString.fromInterned("tkinter.scrolledtext"), PyString.fromInterned("Tkconstants"), PyString.fromInterned("tkinter.constants"), PyString.fromInterned("Tix"), PyString.fromInterned("tkinter.tix"), PyString.fromInterned("ttk"), PyString.fromInterned("tkinter.ttk"), PyString.fromInterned("Tkinter"), PyString.fromInterned("tkinter"), PyString.fromInterned("markupbase"), PyString.fromInterned("_markupbase"), PyString.fromInterned("_winreg"), PyString.fromInterned("winreg"), PyString.fromInterned("thread"), PyString.fromInterned("_thread"), PyString.fromInterned("dummy_thread"), PyString.fromInterned("_dummy_thread"), PyString.fromInterned("dbhash"), PyString.fromInterned("dbm.bsd"), PyString.fromInterned("dumbdbm"), PyString.fromInterned("dbm.dumb"), PyString.fromInterned("dbm"), PyString.fromInterned("dbm.ndbm"), PyString.fromInterned("gdbm"), PyString.fromInterned("dbm.gnu"), PyString.fromInterned("xmlrpclib"), PyString.fromInterned("xmlrpc.client"), PyString.fromInterned("DocXMLRPCServer"), PyString.fromInterned("xmlrpc.server"), PyString.fromInterned("SimpleXMLRPCServer"), PyString.fromInterned("xmlrpc.server"), PyString.fromInterned("httplib"), PyString.fromInterned("http.client"), PyString.fromInterned("htmlentitydefs"), PyString.fromInterned("html.entities"), PyString.fromInterned("HTMLParser"), PyString.fromInterned("html.parser"), PyString.fromInterned("Cookie"), PyString.fromInterned("http.cookies"), PyString.fromInterned("cookielib"), PyString.fromInterned("http.cookiejar"), PyString.fromInterned("BaseHTTPServer"), PyString.fromInterned("http.server"), PyString.fromInterned("SimpleHTTPServer"), PyString.fromInterned("http.server"), PyString.fromInterned("CGIHTTPServer"), PyString.fromInterned("http.server"), PyString.fromInterned("commands"), PyString.fromInterned("subprocess"), PyString.fromInterned("UserString"), PyString.fromInterned("collections"), PyString.fromInterned("UserList"), PyString.fromInterned("collections"), PyString.fromInterned("urlparse"), PyString.fromInterned("urllib.parse"), PyString.fromInterned("robotparser"), PyString.fromInterned("urllib.robotparser")});
      var1.setlocal("MAPPING", var6);
      var3 = null;
      var1.setline(61);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, alternates$1, (PyObject)null);
      var1.setlocal("alternates", var7);
      var3 = null;
      var1.setline(65);
      var5 = new PyObject[]{var1.getname("MAPPING")};
      var7 = new PyFunction(var1.f_globals, var5, build_pattern$2, (PyObject)null);
      var1.setlocal("build_pattern", var7);
      var3 = null;
      var1.setline(85);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixImports", var5, FixImports$3);
      var1.setlocal("FixImports", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject alternates$1(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = PyString.fromInterned("(")._add(PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(0))))._add(PyString.fromInterned(")"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject build_pattern$2(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyTuple var10001;
      Object[] var3;
      PyObject[] var5;
      PyObject var7;
      PyString var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(66);
            var7 = PyString.fromInterned(" | ").__getattr__("join");
            PyList var10002 = new PyList();
            PyObject var6 = var10002.__getattr__("append");
            var1.setlocal(2, var6);
            var3 = null;
            var1.setline(66);
            var6 = var1.getlocal(0).__iter__();

            while(true) {
               var1.setline(66);
               PyObject var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(66);
                  var1.dellocal(2);
                  var6 = var7.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.setlocal(1, var6);
                  var3 = null;
                  var1.setline(67);
                  var6 = var1.getglobal("alternates").__call__(var2, var1.getlocal(0).__getattr__("keys").__call__(var2));
                  var1.setlocal(4, var6);
                  var3 = null;
                  var1.setline(69);
                  var1.setline(69);
                  var8 = PyString.fromInterned("name_import=import_name< 'import' ((%s) |\n               multiple_imports=dotted_as_names< any* (%s) any* >) >\n          ");
                  var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(1)};
                  var10001 = new PyTuple(var5);
                  Arrays.fill(var5, (Object)null);
                  var7 = var8._mod(var10001);
                  var1.f_lasti = 1;
                  var3 = new Object[5];
                  var1.f_savedlocals = var3;
                  return var7;
               }

               var1.setlocal(3, var4);
               var1.setline(66);
               var1.getlocal(2).__call__(var2, PyString.fromInterned("module_name='%s'")._mod(var1.getlocal(3)));
            }
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
            var1.setline(72);
            var1.setline(72);
            var7 = PyString.fromInterned("import_from< 'from' (%s) 'import' ['(']\n              ( any | import_as_name< any 'as' any > |\n                import_as_names< any* >)  [')'] >\n          ")._mod(var1.getlocal(1));
            var1.f_lasti = 2;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var7;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
            var1.setline(76);
            var1.setline(76);
            var8 = PyString.fromInterned("import_name< 'import' (dotted_as_name< (%s) 'as' any > |\n               multiple_imports=dotted_as_names<\n                 any* dotted_as_name< (%s) 'as' any > any* >) >\n          ");
            var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(1)};
            var10001 = new PyTuple(var5);
            Arrays.fill(var5, (Object)null);
            var7 = var8._mod(var10001);
            var1.f_lasti = 3;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var7;
         case 3:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
            var1.setline(82);
            var1.setline(82);
            var7 = PyString.fromInterned("power< bare_with_attr=(%s) trailer<'.' any > any* >")._mod(var1.getlocal(4));
            var1.f_lasti = 4;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var7;
         case 4:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var7 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject FixImports$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(87);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getname("True");
      var1.setlocal("keep_line_order", var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getname("MAPPING");
      var1.setlocal("mapping", var3);
      var3 = null;
      var1.setline(94);
      PyInteger var4 = Py.newInteger(6);
      var1.setlocal("run_order", var4);
      var3 = null;
      var1.setline(96);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, build_pattern$4, (PyObject)null);
      var1.setlocal("build_pattern", var6);
      var3 = null;
      var1.setline(99);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, compile_pattern$5, (PyObject)null);
      var1.setlocal("compile_pattern", var6);
      var3 = null;
      var1.setline(106);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, match$6, (PyObject)null);
      var1.setlocal("match", var6);
      var3 = null;
      var1.setline(118);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_tree$8, (PyObject)null);
      var1.setlocal("start_tree", var6);
      var3 = null;
      var1.setline(122);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$9, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject build_pattern$4(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getglobal("build_pattern").__call__(var2, var1.getlocal(0).__getattr__("mapping")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile_pattern$5(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("build_pattern").__call__(var2);
      var1.getlocal(0).__setattr__("PATTERN", var3);
      var3 = null;
      var1.setline(103);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixImports"), var1.getlocal(0)).__getattr__("compile_pattern").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject match$6(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("FixImports"), var1.getlocal(0)).__getattr__("match");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getderef(0).__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(109);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(112);
         PyString var6 = PyString.fromInterned("bare_with_attr");
         PyObject var10000 = var6._notin(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("any");
            var1.setline(113);
            PyObject var10004 = var1.f_globals;
            PyObject[] var7 = Py.EmptyObjects;
            PyCode var10006 = f$7;
            PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
            PyFunction var5 = new PyFunction(var10004, var7, var10006, (PyObject)null, var4);
            PyObject var10002 = var5.__call__(var2, var1.getglobal("attr_chain").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("parent")).__iter__());
            Arrays.fill(var7, (Object)null);
            var10000 = var10000.__call__(var2, var10002);
         }

         if (var10000.__nonzero__()) {
            var1.setline(114);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(115);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(116);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(113);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(113);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(113);
         var1.setline(113);
         var6 = var1.getderef(0).__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject start_tree$8(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixImports"), var1.getlocal(0)).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(120);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"replace", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$9(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module_name"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(124);
      PyObject var10000;
      PyObject var10002;
      String[] var4;
      PyObject[] var5;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(125);
         var3 = var1.getlocal(3).__getattr__("value");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(126);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("mapping").__getitem__(var1.getlocal(4)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(127);
         var10000 = var1.getlocal(3).__getattr__("replace");
         var10002 = var1.getglobal("Name");
         var5 = new PyObject[]{var1.getlocal(5), var1.getlocal(3).__getattr__("prefix")};
         var4 = new String[]{"prefix"};
         var10002 = var10002.__call__(var2, var5, var4);
         var3 = null;
         var10000.__call__(var2, var10002);
         var1.setline(128);
         PyString var6 = PyString.fromInterned("name_import");
         var10000 = var6._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var3 = var1.getlocal(5);
            var1.getlocal(0).__getattr__("replace").__setitem__(var1.getlocal(4), var3);
            var3 = null;
         }

         var1.setline(132);
         var6 = PyString.fromInterned("multiple_imports");
         var10000 = var6._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(137);
            var3 = var1.getlocal(0).__getattr__("match").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(138);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(139);
               var1.getlocal(0).__getattr__("transform").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            }
         }
      } else {
         var1.setline(142);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("bare_with_attr")).__getitem__(Py.newInteger(0));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(143);
         var3 = var1.getlocal(0).__getattr__("replace").__getattr__("get").__call__(var2, var1.getlocal(6).__getattr__("value"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(144);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(145);
            var10000 = var1.getlocal(6).__getattr__("replace");
            var10002 = var1.getglobal("Name");
            var5 = new PyObject[]{var1.getlocal(5), var1.getlocal(6).__getattr__("prefix")};
            var4 = new String[]{"prefix"};
            var10002 = var10002.__call__(var2, var5, var4);
            var3 = null;
            var10000.__call__(var2, var10002);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_imports$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"members"};
      alternates$1 = Py.newCode(1, var2, var1, "alternates", 61, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mapping", "mod_list", "_[66_27]", "key", "bare_names"};
      build_pattern$2 = Py.newCode(1, var2, var1, "build_pattern", 65, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      FixImports$3 = Py.newCode(0, var2, var1, "FixImports", 85, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      build_pattern$4 = Py.newCode(1, var2, var1, "build_pattern", 96, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      compile_pattern$5 = Py.newCode(1, var2, var1, "compile_pattern", 99, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "_(113_24)", "match"};
      String[] var10001 = var2;
      fix_imports$py var10007 = self;
      var2 = new String[]{"match"};
      match$6 = Py.newCode(2, var10001, var1, "match", 106, false, false, var10007, 6, var2, (String[])null, 1, 4097);
      var2 = new String[]{"_(x)", "obj"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"match"};
      f$7 = Py.newCode(1, var10001, var1, "<genexpr>", 113, false, false, var10007, 7, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "tree", "filename"};
      start_tree$8 = Py.newCode(3, var2, var1, "start_tree", 118, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "import_mod", "mod_name", "new_name", "bare_name"};
      transform$9 = Py.newCode(3, var2, var1, "transform", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_imports$py("lib2to3/fixes/fix_imports$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_imports$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.alternates$1(var2, var3);
         case 2:
            return this.build_pattern$2(var2, var3);
         case 3:
            return this.FixImports$3(var2, var3);
         case 4:
            return this.build_pattern$4(var2, var3);
         case 5:
            return this.compile_pattern$5(var2, var3);
         case 6:
            return this.match$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.start_tree$8(var2, var3);
         case 9:
            return this.transform$9(var2, var3);
         default:
            return null;
      }
   }
}

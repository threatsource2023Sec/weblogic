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
@Filename("pycimport.py")
public class pycimport$py extends PyFunctionTable implements PyRunnable {
   static pycimport$py self;
   static final PyCode f$0;
   static final PyCode __readPycHeader$1;
   static final PyCode read$2;
   static final PyCode __makeModule$3;
   static final PyCode __Importer$4;
   static final PyCode __init__$5;
   static final PyCode find_module$6;
   static final PyCode load_module$7;
   static final PyCode __MetaImporter$8;
   static final PyCode __init__$9;
   static final PyCode find_module$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"Unmarshaller"};
      PyObject[] var6 = imp.importFrom("marshal", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Unmarshaller", var4);
      var4 = null;
      var1.setline(6);
      var3 = var1.getname("False");
      var1.setlocal("__debugging__", var3);
      var3 = null;
      var1.setline(9);
      PyInteger var7 = Py.newInteger(62211);
      var1.setlocal("supported_magic", var7);
      var3 = null;
      var1.setline(11);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, __readPycHeader$1, (PyObject)null);
      var1.setlocal("__readPycHeader", var8);
      var3 = null;
      var1.setline(20);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, __makeModule$3, (PyObject)null);
      var1.setlocal("__makeModule", var8);
      var3 = null;
      var1.setline(28);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("__Importer", var6, __Importer$4);
      var1.setlocal("__Importer", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(74);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("__MetaImporter", var6, __MetaImporter$8);
      var1.setlocal("__MetaImporter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(95);
      var1.getname("sys").__getattr__("meta_path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getname("__MetaImporter").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __readPycHeader$1(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = read$2;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(14);
      PyObject var5 = var1.getlocal(1).__call__(var2)._or(var1.getlocal(1).__call__(var2)._lshift(Py.newInteger(8)));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(15);
      var5 = var1.getderef(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      PyObject var10000 = var5._eq(PyString.fromInterned("\r"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var5 = var1.getderef(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var10000 = var5._eq(PyString.fromInterned("\n"));
         var3 = null;
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(16);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not valid pyc-file")));
      } else {
         var1.setline(17);
         var5 = var1.getlocal(1).__call__(var2)._or(var1.getlocal(1).__call__(var2)._lshift(Py.newInteger(8)))._or(var1.getlocal(1).__call__(var2)._lshift(Py.newInteger(16)))._or(var1.getlocal(1).__call__(var2)._lshift(Py.newInteger(24)));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(18);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject read$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getderef(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __makeModule$3(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getname("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(22);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(23);
         var3 = var1.getname("imp").__getattr__("new_module").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var1.getname("sys").__getattr__("modules").__setitem__(var1.getlocal(0), var3);
      }

      var1.setline(24);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setattr__("__file__", var3);
      var3 = null;
      var1.setline(25);
      Py.exec(var1.getlocal(1), var1.getlocal(3).__getattr__("__dict__"), (PyObject)null);
      var1.setline(26);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __Importer$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(33);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, find_module$6, (PyObject)null);
      var1.setlocal("find_module", var4);
      var3 = null;
      var1.setline(60);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_module$7, (PyObject)null);
      var1.setlocal("load_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      if (var1.getglobal("__debugging__").__nonzero__()) {
         var1.setline(30);
         Py.println(PyString.fromInterned("Importer invoked"));
      }

      var1.setline(31);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Importer__path", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_module$6(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      if (var1.getglobal("__debugging__").__nonzero__()) {
         var1.setline(35);
         Py.println(PyString.fromInterned("Importer.find_module(fullname=%s, path=%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(2))})));
      }

      var1.setline(37);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(40);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var8 = new PyObject[]{var1.getlocal(0).__getattr__("_Importer__path")};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var8, var4, var1.getlocal(2)._add(new PyList(new PyObject[]{var1.getlocal(3)._add(PyString.fromInterned(".pyc"))})), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(41);
      var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      var8 = new PyObject[]{var1.getlocal(0).__getattr__("_Importer__path")};
      var4 = new String[0];
      var10000 = var10000._callextra(var8, var4, var1.getlocal(2)._add(new PyList(new PyObject[]{var1.getlocal(3)._add(PyString.fromInterned(".py"))})), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(42);
      PyObject var7;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4)).__nonzero__()) {
         var1.setline(43);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(6, var3);
         var3 = null;

         try {
            var1.setline(45);
            var3 = var1.getglobal("__readPycHeader").__call__(var2, var1.getlocal(6));
            PyObject[] var9 = Py.unpackSequence(var3, 2);
            PyObject var5 = var9[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var9[1];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(47);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(48);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         var1.setline(50);
         var3 = var1.getlocal(7);
         var10000 = var3._ne(var1.getglobal("supported_magic"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(51);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(52);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(53);
               var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(5)).__getattr__("st_mtime");
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(54);
               var3 = var1.getlocal(9);
               var10000 = var3._gt(var1.getlocal(8));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(55);
                  var7 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var7;
               }
            }

            var1.setline(56);
            var7 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var7;
         }
      } else {
         var1.setline(58);
         var7 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject load_module$7(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(62);
      PyObject var10000 = var1.getlocal(2);
      PyInteger var6 = Py.newInteger(-1);
      PyObject var4 = var10000;
      PyObject var5 = var4.__getitem__(var6);
      var5 = var5._iadd(PyString.fromInterned(".pyc"));
      var4.__setitem__((PyObject)var6, var5);
      var1.setline(63);
      var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var7 = new PyObject[]{var1.getlocal(0).__getattr__("_Importer__path")};
      String[] var8 = new String[0];
      var10000 = var10000._callextra(var7, var8, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("__readPycHeader").__call__(var2, var1.getlocal(4));
      PyObject[] var9 = Py.unpackSequence(var3, 2);
      var5 = var9[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(5);
      var10000 = var3._ne(var1.getglobal("supported_magic"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         if (var1.getglobal("__debugging__").__nonzero__()) {
            var1.setline(67);
            Py.printComma(PyString.fromInterned("Unsupported bytecode version:"));
            Py.println(var1.getlocal(1));
         }

         var1.setline(68);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(70);
         var4 = var1.getglobal("Unmarshaller").__call__(var2, var1.getlocal(4)).__getattr__("load").__call__(var2);
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(71);
         if (var1.getglobal("__debugging__").__nonzero__()) {
            var1.setline(71);
            Py.printComma(PyString.fromInterned("Successfully loaded:"));
            Py.println(var1.getlocal(1));
         }

         var1.setline(72);
         var3 = var1.getglobal("__makeModule").__call__(var2, var1.getlocal(1), var1.getlocal(7), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __MetaImporter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_module$10, (PyObject)null);
      var1.setlocal("find_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_MetaImporter__importers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_module$10(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      if (var1.getglobal("__debugging__").__nonzero__()) {
         var1.setline(79);
         Py.println(PyString.fromInterned("MetaImporter.find_module(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(2))})));
      }

      var1.setline(81);
      PyObject var3 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(81);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(93);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(82);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._notin(var1.getlocal(0).__getattr__("_MetaImporter__importers"));
         var5 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(84);
               var5 = var1.getglobal("__Importer").__call__(var2, var1.getlocal(3));
               var1.getlocal(0).__getattr__("_MetaImporter__importers").__setitem__(var1.getlocal(3), var5);
               var5 = null;
            } catch (Throwable var7) {
               Py.setException(var7, var1);
               var1.setline(86);
               PyObject var6 = var1.getglobal("None");
               var1.getlocal(0).__getattr__("_MetaImporter__importers").__setitem__(var1.getlocal(3), var6);
               var6 = null;
            }
         }

         var1.setline(87);
         var5 = var1.getlocal(0).__getattr__("_MetaImporter__importers").__getitem__(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(88);
         var5 = var1.getlocal(4);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(89);
            var5 = var1.getlocal(4).__getattr__("find_module").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(90);
            var5 = var1.getlocal(5);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(91);
               var5 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var5;
            }
         }
      }
   }

   public pycimport$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "read", "magic", "mtime"};
      String[] var10001 = var2;
      pycimport$py var10007 = self;
      var2 = new String[]{"file"};
      __readPycHeader$1 = Py.newCode(1, var10001, var1, "__readPycHeader", 11, false, false, var10007, 1, var2, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"file"};
      read$2 = Py.newCode(0, var10001, var1, "read", 12, false, false, var10007, 2, (String[])null, var2, 0, 4097);
      var2 = new String[]{"name", "code", "path", "module"};
      __makeModule$3 = Py.newCode(3, var2, var1, "__makeModule", 20, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      __Importer$4 = Py.newCode(0, var2, var1, "__Importer", 28, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "path", "filename", "pycfile", "pyfile", "f", "magic", "mtime", "pytime"};
      find_module$6 = Py.newCode(3, var2, var1, "find_module", 33, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "path", "filename", "f", "magic", "mtime", "code"};
      load_module$7 = Py.newCode(2, var2, var1, "load_module", 60, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      __MetaImporter$8 = Py.newCode(0, var2, var1, "__MetaImporter", 74, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$9 = Py.newCode(1, var2, var1, "__init__", 75, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "path", "_path", "importer", "loader"};
      find_module$10 = Py.newCode(3, var2, var1, "find_module", 78, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pycimport$py("pycimport$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pycimport$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.__readPycHeader$1(var2, var3);
         case 2:
            return this.read$2(var2, var3);
         case 3:
            return this.__makeModule$3(var2, var3);
         case 4:
            return this.__Importer$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.find_module$6(var2, var3);
         case 7:
            return this.load_module$7(var2, var3);
         case 8:
            return this.__MetaImporter$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.find_module$10(var2, var3);
         default:
            return null;
      }
   }
}

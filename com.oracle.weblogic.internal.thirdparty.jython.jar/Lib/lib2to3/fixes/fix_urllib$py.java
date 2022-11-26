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
@Filename("lib2to3/fixes/fix_urllib.py")
public class fix_urllib$py extends PyFunctionTable implements PyRunnable {
   static fix_urllib$py self;
   static final PyCode f$0;
   static final PyCode build_pattern$1;
   static final PyCode FixUrllib$2;
   static final PyCode build_pattern$3;
   static final PyCode transform_import$4;
   static final PyCode transform_member$5;
   static final PyCode handle_name$6;
   static final PyCode transform_dot$7;
   static final PyCode transform$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fix changes imports of urllib which are now incompatible.\n   This is rather similar to fix_imports, but because of the more\n   complex nature of the fixing for urllib, it has its own fixer.\n"));
      var1.setline(4);
      PyString.fromInterned("Fix changes imports of urllib which are now incompatible.\n   This is rather similar to fix_imports, but because of the more\n   complex nature of the fixing for urllib, it has its own fixer.\n");
      var1.setline(8);
      String[] var3 = new String[]{"alternates", "FixImports"};
      PyObject[] var5 = imp.importFrom("lib2to3.fixes.fix_imports", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("alternates", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("FixImports", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("lib2to3", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"Name", "Comma", "FromImport", "Newline", "find_indentation", "Node", "syms"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("FromImport", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Newline", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("find_indentation", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("Node", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(13);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("urllib"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("urllib.request"), new PyList(new PyObject[]{PyString.fromInterned("URLopener"), PyString.fromInterned("FancyURLopener"), PyString.fromInterned("urlretrieve"), PyString.fromInterned("_urlopener"), PyString.fromInterned("urlopen"), PyString.fromInterned("urlcleanup"), PyString.fromInterned("pathname2url"), PyString.fromInterned("url2pathname")})}), new PyTuple(new PyObject[]{PyString.fromInterned("urllib.parse"), new PyList(new PyObject[]{PyString.fromInterned("quote"), PyString.fromInterned("quote_plus"), PyString.fromInterned("unquote"), PyString.fromInterned("unquote_plus"), PyString.fromInterned("urlencode"), PyString.fromInterned("splitattr"), PyString.fromInterned("splithost"), PyString.fromInterned("splitnport"), PyString.fromInterned("splitpasswd"), PyString.fromInterned("splitport"), PyString.fromInterned("splitquery"), PyString.fromInterned("splittag"), PyString.fromInterned("splittype"), PyString.fromInterned("splituser"), PyString.fromInterned("splitvalue")})}), new PyTuple(new PyObject[]{PyString.fromInterned("urllib.error"), new PyList(new PyObject[]{PyString.fromInterned("ContentTooShortError")})})}), PyString.fromInterned("urllib2"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("urllib.request"), new PyList(new PyObject[]{PyString.fromInterned("urlopen"), PyString.fromInterned("install_opener"), PyString.fromInterned("build_opener"), PyString.fromInterned("Request"), PyString.fromInterned("OpenerDirector"), PyString.fromInterned("BaseHandler"), PyString.fromInterned("HTTPDefaultErrorHandler"), PyString.fromInterned("HTTPRedirectHandler"), PyString.fromInterned("HTTPCookieProcessor"), PyString.fromInterned("ProxyHandler"), PyString.fromInterned("HTTPPasswordMgr"), PyString.fromInterned("HTTPPasswordMgrWithDefaultRealm"), PyString.fromInterned("AbstractBasicAuthHandler"), PyString.fromInterned("HTTPBasicAuthHandler"), PyString.fromInterned("ProxyBasicAuthHandler"), PyString.fromInterned("AbstractDigestAuthHandler"), PyString.fromInterned("HTTPDigestAuthHandler"), PyString.fromInterned("ProxyDigestAuthHandler"), PyString.fromInterned("HTTPHandler"), PyString.fromInterned("HTTPSHandler"), PyString.fromInterned("FileHandler"), PyString.fromInterned("FTPHandler"), PyString.fromInterned("CacheFTPHandler"), PyString.fromInterned("UnknownHandler")})}), new PyTuple(new PyObject[]{PyString.fromInterned("urllib.error"), new PyList(new PyObject[]{PyString.fromInterned("URLError"), PyString.fromInterned("HTTPError")})})})});
      var1.setlocal("MAPPING", var6);
      var3 = null;
      var1.setline(46);
      var1.getname("MAPPING").__getitem__(PyString.fromInterned("urllib2")).__getattr__("append").__call__(var2, var1.getname("MAPPING").__getitem__(PyString.fromInterned("urllib")).__getitem__(Py.newInteger(1)));
      var1.setline(49);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, build_pattern$1, (PyObject)null);
      var1.setlocal("build_pattern", var7);
      var3 = null;
      var1.setline(72);
      var5 = new PyObject[]{var1.getname("FixImports")};
      var4 = Py.makeClass("FixUrllib", var5, FixUrllib$2);
      var1.setlocal("FixUrllib", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build_pattern$1(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject[] var10;
      PyObject[] var11;
      PyObject var13;
      PyString var14;
      Object var10000;
      PyTuple var10001;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(50);
            var3 = var1.getglobal("set").__call__(var2);
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(51);
            var3 = var1.getglobal("MAPPING").__getattr__("items").__call__(var2).__iter__();
            var1.setline(51);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var10 = Py.unpackSequence(var4, 2);
            var6 = var10[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(52);
            var5 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var13 = (PyObject)var10000;
            var1.setline(58);
            var1.setline(58);
            var14 = PyString.fromInterned("import_from< 'from' mod_member=%r 'import'\n                       ( member=%s | import_as_name< member=%s 'as' any > |\n                         import_as_names< members=any*  >) >\n                  ");
            var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(5)};
            var10001 = new PyTuple(var11);
            Arrays.fill(var11, (Object)null);
            var13 = var14._mod(var10001);
            var1.f_lasti = 2;
            var7 = new Object[10];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var13;
         case 2:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var13 = (PyObject)var10000;
               var1.setline(62);
               var1.setline(62);
               var13 = PyString.fromInterned("import_from< 'from' module_star=%r 'import' star='*' >\n                  ")._mod(var1.getlocal(1));
               var1.f_lasti = 3;
               var7 = new Object[10];
               var7[3] = var3;
               var7[4] = var4;
               var7[5] = var5;
               var7[6] = var6;
               var1.f_savedlocals = var7;
               return var13;
            }
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

            var13 = (PyObject)var10000;
            var1.setline(64);
            var1.setline(64);
            var13 = PyString.fromInterned("import_name< 'import'\n                                  dotted_as_name< module_as=%r 'as' any > >\n                  ")._mod(var1.getlocal(1));
            var1.f_lasti = 4;
            var7 = new Object[10];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var13;
         case 4:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var13 = (PyObject)var10000;
            var1.setline(68);
            var1.setline(68);
            var14 = PyString.fromInterned("power< bare_with_attr=%r trailer< '.' member=%s > any* >\n                  ");
            var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(5)};
            var10001 = new PyTuple(var11);
            Arrays.fill(var11, (Object)null);
            var13 = var14._mod(var10001);
            var1.f_lasti = 5;
            var7 = new Object[10];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var13;
         case 5:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var13 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(52);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(3, var6);
            var1.setline(53);
            PyObject var12 = var1.getlocal(3);
            PyObject[] var8 = Py.unpackSequence(var12, 2);
            PyObject var9 = var8[0];
            var1.setlocal(4, var9);
            var9 = null;
            var9 = var8[1];
            var1.setlocal(5, var9);
            var9 = null;
            var7 = null;
            var1.setline(54);
            var12 = var1.getglobal("alternates").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var12);
            var7 = null;
            var1.setline(55);
            var1.setline(55);
            var14 = PyString.fromInterned("import_name< 'import' (module=%r\n                                  | dotted_as_names< any* module=%r any* >) >\n                  ");
            var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(1)};
            var10001 = new PyTuple(var11);
            Arrays.fill(var11, (Object)null);
            var13 = var14._mod(var10001);
            var1.f_lasti = 1;
            var7 = new Object[10];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var13;
         }

         var1.setline(51);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var10 = Py.unpackSequence(var4, 2);
         var6 = var10[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(52);
         var5 = var1.getlocal(2).__iter__();
      }
   }

   public PyObject FixUrllib$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(74);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, build_pattern$3, (PyObject)null);
      var1.setlocal("build_pattern", var4);
      var3 = null;
      var1.setline(77);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, transform_import$4, PyString.fromInterned("Transform for the basic import case. Replaces the old\n           import name with a comma separated list of its\n           replacements.\n        "));
      var1.setlocal("transform_import", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, transform_member$5, PyString.fromInterned("Transform for imports of specific module elements. Replaces\n           the module to be imported from with the appropriate new\n           module.\n        "));
      var1.setlocal("transform_member", var4);
      var3 = null;
      var1.setline(169);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, transform_dot$7, PyString.fromInterned("Transform for calls to module members in code."));
      var1.setlocal("transform_dot", var4);
      var3 = null;
      var1.setline(186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, transform$8, (PyObject)null);
      var1.setlocal("transform", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject build_pattern$3(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyObject var3 = PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getglobal("build_pattern").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject transform_import$4(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Transform for the basic import case. Replaces the old\n           import name with a comma separated list of its\n           replacements.\n        ");
      var1.setline(82);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(3).__getattr__("prefix");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(85);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(88);
      var3 = var1.getglobal("MAPPING").__getitem__(var1.getlocal(3).__getattr__("value")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

      while(true) {
         var1.setline(88);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(90);
            var10000 = var1.getlocal(5).__getattr__("append");
            PyObject var10002 = var1.getglobal("Name");
            PyObject[] var9 = new PyObject[]{var1.getglobal("MAPPING").__getitem__(var1.getlocal(3).__getattr__("value")).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0)), var1.getlocal(4)};
            String[] var8 = new String[]{"prefix"};
            var10002 = var10002.__call__(var2, var9, var8);
            var3 = null;
            var10000.__call__(var2, var10002);
            var1.setline(91);
            var1.getlocal(3).__getattr__("replace").__call__(var2, var1.getlocal(5));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(89);
         var10000 = var1.getlocal(5).__getattr__("extend");
         PyObject[] var10004 = new PyObject[2];
         PyObject var10007 = var1.getglobal("Name");
         PyObject[] var5 = new PyObject[]{var1.getlocal(6).__getitem__(Py.newInteger(0)), var1.getlocal(4)};
         String[] var6 = new String[]{"prefix"};
         var10007 = var10007.__call__(var2, var5, var6);
         var5 = null;
         var10004[0] = var10007;
         var10004[1] = var1.getglobal("Comma").__call__(var2);
         var10000.__call__((ThreadState)var2, (PyObject)(new PyList(var10004)));
      }
   }

   public PyObject transform_member$5(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Transform for imports of specific module elements. Replaces\n           the module to be imported from with the appropriate new\n           module.\n        ");
      var1.setline(98);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mod_member"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getlocal(3).__getattr__("prefix");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("member"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(103);
      PyObject var4;
      PyObject var5;
      PyObject[] var9;
      PyObject var10000;
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(105);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("list")).__nonzero__()) {
            var1.setline(106);
            var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(107);
         var3 = var1.getglobal("None");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(108);
         var3 = var1.getglobal("MAPPING").__getitem__(var1.getlocal(3).__getattr__("value")).__iter__();

         while(true) {
            var1.setline(108);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(109);
            var5 = var1.getlocal(5).__getattr__("value");
            var10000 = var5._in(var1.getlocal(7).__getitem__(Py.newInteger(1)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(110);
               var5 = var1.getlocal(7).__getitem__(Py.newInteger(0));
               var1.setlocal(6, var5);
               var5 = null;
               break;
            }
         }

         var1.setline(112);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(113);
            var10000 = var1.getlocal(3).__getattr__("replace");
            PyObject var10002 = var1.getglobal("Name");
            var9 = new PyObject[]{var1.getlocal(6), var1.getlocal(4)};
            String[] var8 = new String[]{"prefix"};
            var10002 = var10002.__call__(var2, var9, var8);
            var3 = null;
            var10000.__call__(var2, var10002);
         } else {
            var1.setline(115);
            var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("This is an invalid module element"));
         }
      } else {
         var1.setline(120);
         PyList var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var10);
         var3 = null;
         var1.setline(121);
         PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(9, var11);
         var3 = null;
         var1.setline(122);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("members"));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(123);
         var3 = var1.getlocal(10).__iter__();

         label96:
         while(true) {
            PyObject var6;
            do {
               var1.setline(123);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(138);
                  var10 = new PyList(Py.EmptyObjects);
                  var1.setlocal(13, var10);
                  var3 = null;
                  var1.setline(139);
                  var3 = var1.getglobal("find_indentation").__call__(var2, var1.getlocal(1));
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(140);
                  var3 = var1.getglobal("True");
                  var1.setlocal(15, var3);
                  var3 = null;
                  var1.setline(141);
                  var9 = Py.EmptyObjects;
                  PyFunction var13 = new PyFunction(var1.f_globals, var9, handle_name$6, (PyObject)null);
                  var1.setlocal(16, var13);
                  var3 = null;
                  var1.setline(148);
                  var3 = var1.getlocal(8).__iter__();

                  while(true) {
                     var1.setline(148);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(160);
                        if (var1.getlocal(13).__nonzero__()) {
                           var1.setline(161);
                           var10 = new PyList(Py.EmptyObjects);
                           var1.setlocal(22, var10);
                           var3 = null;
                           var1.setline(162);
                           var3 = var1.getlocal(13).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

                           while(true) {
                              var1.setline(162);
                              var4 = var3.__iternext__();
                              if (var4 == null) {
                                 var1.setline(164);
                                 var1.getlocal(22).__getattr__("append").__call__(var2, var1.getlocal(13).__getitem__(Py.newInteger(-1)));
                                 var1.setline(165);
                                 var1.getlocal(1).__getattr__("replace").__call__(var2, var1.getlocal(22));
                                 break label96;
                              }

                              var1.setlocal(23, var4);
                              var1.setline(163);
                              var1.getlocal(22).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(23), var1.getglobal("Newline").__call__(var2)})));
                           }
                        } else {
                           var1.setline(167);
                           var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("All module elements are invalid"));
                           break label96;
                        }
                     }

                     var1.setlocal(17, var4);
                     var1.setline(149);
                     var5 = var1.getlocal(9).__getitem__(var1.getlocal(17));
                     var1.setlocal(18, var5);
                     var5 = null;
                     var1.setline(150);
                     PyList var12 = new PyList(Py.EmptyObjects);
                     var1.setlocal(19, var12);
                     var5 = null;
                     var1.setline(151);
                     var5 = var1.getlocal(18).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

                     while(true) {
                        var1.setline(151);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           var1.setline(154);
                           var1.getlocal(19).__getattr__("extend").__call__(var2, var1.getlocal(16).__call__(var2, var1.getlocal(18).__getitem__(Py.newInteger(-1)), var1.getlocal(4)));
                           var1.setline(155);
                           var5 = var1.getglobal("FromImport").__call__(var2, var1.getlocal(17), var1.getlocal(19));
                           var1.setlocal(21, var5);
                           var5 = null;
                           var1.setline(156);
                           var10000 = var1.getlocal(15).__not__();
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(1).__getattr__("parent").__getattr__("prefix").__getattr__("endswith").__call__(var2, var1.getlocal(14));
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(157);
                              var5 = var1.getlocal(14);
                              var1.getlocal(21).__setattr__("prefix", var5);
                              var5 = null;
                           }

                           var1.setline(158);
                           var1.getlocal(13).__getattr__("append").__call__(var2, var1.getlocal(21));
                           var1.setline(159);
                           var5 = var1.getglobal("False");
                           var1.setlocal(15, var5);
                           var5 = null;
                           break;
                        }

                        var1.setlocal(20, var6);
                        var1.setline(152);
                        var1.getlocal(19).__getattr__("extend").__call__(var2, var1.getlocal(16).__call__(var2, var1.getlocal(20), var1.getlocal(4)));
                        var1.setline(153);
                        var1.getlocal(19).__getattr__("append").__call__(var2, var1.getglobal("Comma").__call__(var2));
                     }
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(125);
               var5 = var1.getlocal(5).__getattr__("type");
               var10000 = var5._eq(var1.getglobal("syms").__getattr__("import_as_name"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(126);
                  var5 = var1.getlocal(5).__getattr__("children").__getitem__(Py.newInteger(2)).__getattr__("value");
                  var1.setlocal(11, var5);
                  var5 = null;
                  var1.setline(127);
                  var5 = var1.getlocal(5).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                  var1.setlocal(12, var5);
                  var5 = null;
               } else {
                  var1.setline(129);
                  var5 = var1.getlocal(5).__getattr__("value");
                  var1.setlocal(12, var5);
                  var5 = null;
                  var1.setline(130);
                  var5 = var1.getglobal("None");
                  var1.setlocal(11, var5);
                  var5 = null;
               }

               var1.setline(131);
               var5 = var1.getlocal(12);
               var10000 = var5._ne(PyUnicode.fromInterned(","));
               var5 = null;
            } while(!var10000.__nonzero__());

            var1.setline(132);
            var5 = var1.getglobal("MAPPING").__getitem__(var1.getlocal(3).__getattr__("value")).__iter__();

            while(true) {
               var1.setline(132);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(7, var6);
               var1.setline(133);
               PyObject var7 = var1.getlocal(12);
               var10000 = var7._in(var1.getlocal(7).__getitem__(Py.newInteger(1)));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(134);
                  var7 = var1.getlocal(7).__getitem__(Py.newInteger(0));
                  var10000 = var7._notin(var1.getlocal(9));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(135);
                     var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0)));
                  }

                  var1.setline(136);
                  var1.getlocal(9).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getitem__(Py.newInteger(0)), (PyObject)(new PyList(Py.EmptyObjects))).__getattr__("append").__call__(var2, var1.getlocal(5));
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_name$6(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_as_name"));
      var3 = null;
      PyObject[] var10002;
      PyObject var10005;
      PyList var6;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         var10002 = new PyObject[3];
         var10005 = var1.getglobal("Name");
         PyObject[] var7 = new PyObject[]{var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value"), var1.getlocal(1)};
         String[] var8 = new String[]{"prefix"};
         var10005 = var10005.__call__(var2, var7, var8);
         var3 = null;
         var10002[0] = var10005;
         var10002[1] = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("clone").__call__(var2);
         var10002[2] = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(2)).__getattr__("clone").__call__(var2);
         var6 = new PyList(var10002);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(146);
         var6 = new PyList(new PyObject[]{var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("import_as_name"), var1.getlocal(2))});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(147);
         var10002 = new PyObject[1];
         var10005 = var1.getglobal("Name");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("value"), var1.getlocal(1)};
         String[] var5 = new String[]{"prefix"};
         var10005 = var10005.__call__(var2, var4, var5);
         var4 = null;
         var10002[0] = var10005;
         var6 = new PyList(var10002);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject transform_dot$7(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Transform for calls to module members in code.");
      var1.setline(171);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bare_with_attr"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("member"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(174);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")).__nonzero__()) {
         var1.setline(175);
         var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(176);
      var3 = var1.getglobal("MAPPING").__getitem__(var1.getlocal(3).__getattr__("value")).__iter__();

      PyObject var10000;
      while(true) {
         var1.setline(176);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(6, var4);
         var1.setline(177);
         PyObject var5 = var1.getlocal(4).__getattr__("value");
         var10000 = var5._in(var1.getlocal(6).__getitem__(Py.newInteger(1)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(178);
            var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var1.setlocal(5, var5);
            var5 = null;
            break;
         }
      }

      var1.setline(180);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(181);
         var10000 = var1.getlocal(3).__getattr__("replace");
         PyObject var10002 = var1.getglobal("Name");
         PyObject[] var7 = new PyObject[]{var1.getlocal(5), var1.getlocal(3).__getattr__("prefix")};
         String[] var6 = new String[]{"prefix"};
         var10002 = var10002.__call__(var2, var7, var6);
         var3 = null;
         var10000.__call__(var2, var10002);
      } else {
         var1.setline(184);
         var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("This is an invalid module element"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$8(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      if (var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module")).__nonzero__()) {
         var1.setline(188);
         var1.getlocal(0).__getattr__("transform_import").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      } else {
         var1.setline(189);
         if (var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mod_member")).__nonzero__()) {
            var1.setline(190);
            var1.getlocal(0).__getattr__("transform_member").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } else {
            var1.setline(191);
            if (var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bare_with_attr")).__nonzero__()) {
               var1.setline(192);
               var1.getlocal(0).__getattr__("transform_dot").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            } else {
               var1.setline(194);
               if (var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module_star")).__nonzero__()) {
                  var1.setline(195);
                  var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Cannot handle star imports."));
               } else {
                  var1.setline(196);
                  if (var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module_as")).__nonzero__()) {
                     var1.setline(197);
                     var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("This module is now multiple modules"));
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_urllib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"bare", "old_module", "changes", "change", "new_module", "members"};
      build_pattern$1 = Py.newCode(0, var2, var1, "build_pattern", 49, false, false, self, 1, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      FixUrllib$2 = Py.newCode(0, var2, var1, "FixUrllib", 72, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      build_pattern$3 = Py.newCode(1, var2, var1, "build_pattern", 74, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "import_mod", "pref", "names", "name"};
      transform_import$4 = Py.newCode(3, var2, var1, "transform_import", 77, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "mod_member", "pref", "member", "new_name", "change", "modules", "mod_dict", "members", "as_name", "member_name", "new_nodes", "indentation", "first", "handle_name", "module", "elts", "names", "elt", "new", "nodes", "new_node"};
      transform_member$5 = Py.newCode(3, var2, var1, "transform_member", 93, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "prefix", "kids"};
      handle_name$6 = Py.newCode(2, var2, var1, "handle_name", 141, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "module_dot", "member", "new_name", "change"};
      transform_dot$7 = Py.newCode(3, var2, var1, "transform_dot", 169, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      transform$8 = Py.newCode(3, var2, var1, "transform", 186, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_urllib$py("lib2to3/fixes/fix_urllib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_urllib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.build_pattern$1(var2, var3);
         case 2:
            return this.FixUrllib$2(var2, var3);
         case 3:
            return this.build_pattern$3(var2, var3);
         case 4:
            return this.transform_import$4(var2, var3);
         case 5:
            return this.transform_member$5(var2, var3);
         case 6:
            return this.handle_name$6(var2, var3);
         case 7:
            return this.transform_dot$7(var2, var3);
         case 8:
            return this.transform$8(var2, var3);
         default:
            return null;
      }
   }
}

package distutils;

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
@Filename("distutils/version.py")
public class version$py extends PyFunctionTable implements PyRunnable {
   static version$py self;
   static final PyCode f$0;
   static final PyCode Version$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode StrictVersion$4;
   static final PyCode parse$5;
   static final PyCode __str__$6;
   static final PyCode __cmp__$7;
   static final PyCode LooseVersion$8;
   static final PyCode __init__$9;
   static final PyCode parse$10;
   static final PyCode f$11;
   static final PyCode __str__$12;
   static final PyCode __repr__$13;
   static final PyCode __cmp__$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Provides classes to represent module version numbers (one class for\neach style of version numbering).  There are currently two such classes\nimplemented: StrictVersion and LooseVersion.\n\nEvery version number class implements the following interface:\n  * the 'parse' method takes a string and parses it to some internal\n    representation; if the string is an invalid version number,\n    'parse' raises a ValueError exception\n  * the class constructor takes an optional string argument which,\n    if supplied, is passed to 'parse'\n  * __str__ reconstructs the string that was passed to 'parse' (or\n    an equivalent string -- ie. one that will generate an equivalent\n    version number instance)\n  * __repr__ generates Python code to recreate the version number instance\n  * __cmp__ compares the current instance with either another instance\n    of the same class or a string (which will be parsed to an instance\n    of the same class, thus must follow the same rules)\n"));
      var1.setline(27);
      PyString.fromInterned("Provides classes to represent module version numbers (one class for\neach style of version numbering).  There are currently two such classes\nimplemented: StrictVersion and LooseVersion.\n\nEvery version number class implements the following interface:\n  * the 'parse' method takes a string and parses it to some internal\n    representation; if the string is an invalid version number,\n    'parse' raises a ValueError exception\n  * the class constructor takes an optional string argument which,\n    if supplied, is passed to 'parse'\n  * __str__ reconstructs the string that was passed to 'parse' (or\n    an equivalent string -- ie. one that will generate an equivalent\n    version number instance)\n  * __repr__ generates Python code to recreate the version number instance\n  * __cmp__ compares the current instance with either another instance\n    of the same class or a string (which will be parsed to an instance\n    of the same class, thus must follow the same rules)\n");
      var1.setline(29);
      PyObject var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(30);
      String[] var5 = new String[]{"StringType"};
      PyObject[] var6 = imp.importFrom("types", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringType", var4);
      var4 = null;
      var1.setline(32);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Version", var6, Version$1);
      var1.setlocal("Version", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(63);
      var6 = new PyObject[]{var1.getname("Version")};
      var4 = Py.makeClass("StrictVersion", var6, StrictVersion$4);
      var1.setlocal("StrictVersion", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(228);
      var6 = new PyObject[]{var1.getname("Version")};
      var4 = Py.makeClass("LooseVersion", var6, LooseVersion$8);
      var1.setlocal("LooseVersion", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Version$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class for version numbering classes.  Just provides\n    constructor (__init__) and reproducer (__repr__), because those\n    seem to be the same for all version numbering classes.\n    "));
      var1.setline(36);
      PyString.fromInterned("Abstract base class for version numbering classes.  Just provides\n    constructor (__init__) and reproducer (__repr__), because those\n    seem to be the same for all version numbering classes.\n    ");
      var1.setline(38);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(40);
         var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = PyString.fromInterned("%s ('%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("str").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StrictVersion$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Version numbering for anal retentives and software idealists.\n    Implements the standard interface for version number classes as\n    described above.  A version number consists of two or three\n    dot-separated numeric components, with an optional \"pre-release\" tag\n    on the end.  The pre-release tag consists of the letter 'a' or 'b'\n    followed by a number.  If the numeric components of two version\n    numbers are equal, then one with a pre-release tag will always\n    be deemed earlier (lesser) than one without.\n\n    The following are valid version numbers (shown in the order that\n    would be obtained by sorting according to the supplied cmp function):\n\n        0.4       0.4.0  (these two are equivalent)\n        0.4.1\n        0.5a1\n        0.5b3\n        0.5\n        0.9.6\n        1.0\n        1.0.4a3\n        1.0.4b1\n        1.0.4\n\n    The following are examples of invalid version numbers:\n\n        1\n        2.7.2.2\n        1.3.a4\n        1.3pl1\n        1.3c4\n\n    The rationale for this version numbering system will be explained\n    in the distutils documentation.\n    "));
      var1.setline(98);
      PyString.fromInterned("Version numbering for anal retentives and software idealists.\n    Implements the standard interface for version number classes as\n    described above.  A version number consists of two or three\n    dot-separated numeric components, with an optional \"pre-release\" tag\n    on the end.  The pre-release tag consists of the letter 'a' or 'b'\n    followed by a number.  If the numeric components of two version\n    numbers are equal, then one with a pre-release tag will always\n    be deemed earlier (lesser) than one without.\n\n    The following are valid version numbers (shown in the order that\n    would be obtained by sorting according to the supplied cmp function):\n\n        0.4       0.4.0  (these two are equivalent)\n        0.4.1\n        0.5a1\n        0.5b3\n        0.5\n        0.9.6\n        1.0\n        1.0.4a3\n        1.0.4b1\n        1.0.4\n\n    The following are examples of invalid version numbers:\n\n        1\n        2.7.2.2\n        1.3.a4\n        1.3pl1\n        1.3c4\n\n    The rationale for this version numbering system will be explained\n    in the distutils documentation.\n    ");
      var1.setline(100);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(\\d+) \\. (\\d+) (\\. (\\d+))? ([ab](\\d+))?$"), (PyObject)var1.getname("re").__getattr__("VERBOSE"));
      var1.setlocal("version_re", var3);
      var3 = null;
      var1.setline(104);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, parse$5, (PyObject)null);
      var1.setlocal("parse", var5);
      var3 = null;
      var1.setline(123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$6, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(136);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __cmp__$7, (PyObject)null);
      var1.setlocal("__cmp__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parse$5(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getlocal(0).__getattr__("version_re").__getattr__("match").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(106);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(107);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid version number '%s'")._mod(var1.getlocal(1)));
      } else {
         var1.setline(109);
         PyObject var10000 = var1.getlocal(2).__getattr__("group");
         PyObject[] var6 = new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(4), Py.newInteger(5), Py.newInteger(6)};
         var3 = var10000.__call__(var2, var6);
         PyObject[] var4 = Py.unpackSequence(var3, 5);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(112);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(113);
            var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__((ThreadState)var2, (PyObject)var1.getglobal("string").__getattr__("atoi"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)}))));
            var1.getlocal(0).__setattr__("version", var3);
            var3 = null;
         } else {
            var1.setline(115);
            var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__((ThreadState)var2, (PyObject)var1.getglobal("string").__getattr__("atoi"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})))._add(new PyList(new PyObject[]{Py.newInteger(0)})));
            var1.getlocal(0).__setattr__("version", var3);
            var3 = null;
         }

         var1.setline(117);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(118);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(6).__getitem__(Py.newInteger(0)), var1.getglobal("string").__getattr__("atoi").__call__(var2, var1.getlocal(7))});
            var1.getlocal(0).__setattr__((String)"prerelease", var7);
            var3 = null;
         } else {
            var1.setline(120);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("prerelease", var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __str__$6(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getlocal(0).__getattr__("version").__getitem__(Py.newInteger(2));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(0).__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null)), (PyObject)PyString.fromInterned("."));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(128);
         var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(0).__getattr__("version")), (PyObject)PyString.fromInterned("."));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(130);
      if (var1.getlocal(0).__getattr__("prerelease").__nonzero__()) {
         var1.setline(131);
         var3 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("prerelease").__getitem__(Py.newInteger(0)))._add(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("prerelease").__getitem__(Py.newInteger(1))));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(133);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$7(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType")).__nonzero__()) {
         var1.setline(138);
         var3 = var1.getglobal("StrictVersion").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(140);
      var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("version"), var1.getlocal(1).__getattr__("version"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(141);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(148);
         var10000 = var1.getlocal(0).__getattr__("prerelease").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("prerelease").__not__();
         }

         PyInteger var4;
         if (var10000.__nonzero__()) {
            var1.setline(149);
            var4 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(150);
            var10000 = var1.getlocal(0).__getattr__("prerelease");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("prerelease").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(151);
               var4 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var4;
            } else {
               var1.setline(152);
               var10000 = var1.getlocal(0).__getattr__("prerelease").__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getattr__("prerelease");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(153);
                  var4 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var4;
               } else {
                  var1.setline(154);
                  var10000 = var1.getlocal(0).__getattr__("prerelease");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1).__getattr__("prerelease");
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(155);
                     var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("prerelease"), var1.getlocal(1).__getattr__("prerelease"));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.f_lasti = -1;
                     return Py.None;
                  }
               }
            }
         }
      } else {
         var1.setline(158);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject LooseVersion$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Version numbering for anarchists and software realists.\n    Implements the standard interface for version number classes as\n    described above.  A version number consists of a series of numbers,\n    separated by either periods or strings of letters.  When comparing\n    version numbers, the numeric components will be compared\n    numerically, and the alphabetic components lexically.  The following\n    are all valid version numbers, in no particular order:\n\n        1.5.1\n        1.5.2b2\n        161\n        3.10a\n        8.02\n        3.4j\n        1996.07.12\n        3.2.pl0\n        3.1.1.6\n        2g6\n        11g\n        0.960923\n        2.2beta29\n        1.13++\n        5.5.kw\n        2.0b1pl0\n\n    In fact, there is no such thing as an invalid version number under\n    this scheme; the rules for comparison are simple and predictable,\n    but may not always give the results you want (for some definition\n    of \"want\").\n    "));
      var1.setline(259);
      PyString.fromInterned("Version numbering for anarchists and software realists.\n    Implements the standard interface for version number classes as\n    described above.  A version number consists of a series of numbers,\n    separated by either periods or strings of letters.  When comparing\n    version numbers, the numeric components will be compared\n    numerically, and the alphabetic components lexically.  The following\n    are all valid version numbers, in no particular order:\n\n        1.5.1\n        1.5.2b2\n        161\n        3.10a\n        8.02\n        3.4j\n        1996.07.12\n        3.2.pl0\n        3.1.1.6\n        2g6\n        11g\n        0.960923\n        2.2beta29\n        1.13++\n        5.5.kw\n        2.0b1pl0\n\n    In fact, there is no such thing as an invalid version number under\n    this scheme; the rules for comparison are simple and predictable,\n    but may not always give the results you want (for some definition\n    of \"want\").\n    ");
      var1.setline(261);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+ | [a-z]+ | \\.)"), (PyObject)var1.getname("re").__getattr__("VERBOSE"));
      var1.setlocal("component_re", var3);
      var3 = null;
      var1.setline(263);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(268);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse$10, (PyObject)null);
      var1.setlocal("parse", var5);
      var3 = null;
      var1.setline(284);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$12, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(288);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$13, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(292);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __cmp__$14, (PyObject)null);
      var1.setlocal("__cmp__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(265);
         var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$10(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("vstring", var3);
      var3 = null;
      var1.setline(273);
      PyObject var10000 = var1.getglobal("filter");
      var1.setline(273);
      PyObject[] var7 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var7, f$11)), (PyObject)var1.getlocal(0).__getattr__("component_re").__getattr__("split").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))).__iter__();

      while(true) {
         var1.setline(275);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(281);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("version", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);

         PyException var5;
         try {
            var1.setline(277);
            PyObject var8 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
            var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (!var5.match(var1.getglobal("ValueError"))) {
               throw var5;
            }

            var1.setline(279);
         }
      }
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyObject var10000 = var1.getlocal(0);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._ne(PyString.fromInterned("."));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$12(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyObject var3 = var1.getlocal(0).__getattr__("vstring");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$13(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyObject var3 = PyString.fromInterned("LooseVersion ('%s')")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$14(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType")).__nonzero__()) {
         var1.setline(294);
         var3 = var1.getglobal("LooseVersion").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(296);
      var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("version"), var1.getlocal(1).__getattr__("version"));
      var1.f_lasti = -1;
      return var3;
   }

   public version$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Version$1 = Py.newCode(0, var2, var1, "Version", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "vstring"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StrictVersion$4 = Py.newCode(0, var2, var1, "StrictVersion", 63, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "vstring", "match", "major", "minor", "patch", "prerelease", "prerelease_num"};
      parse$5 = Py.newCode(2, var2, var1, "parse", 104, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "vstring"};
      __str__$6 = Py.newCode(1, var2, var1, "__str__", 123, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "compare"};
      __cmp__$7 = Py.newCode(2, var2, var1, "__cmp__", 136, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LooseVersion$8 = Py.newCode(0, var2, var1, "LooseVersion", 228, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "vstring"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 263, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "vstring", "components", "i"};
      parse$10 = Py.newCode(2, var2, var1, "parse", 268, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$11 = Py.newCode(1, var2, var1, "<lambda>", 273, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$12 = Py.newCode(1, var2, var1, "__str__", 284, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$13 = Py.newCode(1, var2, var1, "__repr__", 288, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$14 = Py.newCode(2, var2, var1, "__cmp__", 292, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new version$py("distutils/version$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(version$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Version$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.StrictVersion$4(var2, var3);
         case 5:
            return this.parse$5(var2, var3);
         case 6:
            return this.__str__$6(var2, var3);
         case 7:
            return this.__cmp__$7(var2, var3);
         case 8:
            return this.LooseVersion$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.parse$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.__str__$12(var2, var3);
         case 13:
            return this.__repr__$13(var2, var3);
         case 14:
            return this.__cmp__$14(var2, var3);
         default:
            return null;
      }
   }
}

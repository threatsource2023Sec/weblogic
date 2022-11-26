package distutils;

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
@Filename("distutils/versionpredicate.py")
public class versionpredicate$py extends PyFunctionTable implements PyRunnable {
   static versionpredicate$py self;
   static final PyCode f$0;
   static final PyCode splitUp$1;
   static final PyCode VersionPredicate$2;
   static final PyCode __init__$3;
   static final PyCode __str__$4;
   static final PyCode satisfied_by$5;
   static final PyCode split_provision$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Module for parsing and testing package version predicate strings.\n"));
      var1.setline(2);
      PyString.fromInterned("Module for parsing and testing package version predicate strings.\n");
      var1.setline(3);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("distutils.version", var1, -1);
      var1.setlocal("distutils", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var3);
      var3 = null;
      var1.setline(8);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?i)^\\s*([a-z_]\\w*(?:\\.[a-z_]\\w*)*)(.*)"));
      var1.setlocal("re_validPackage", var3);
      var3 = null;
      var1.setline(11);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*\\((.*)\\)\\s*$"));
      var1.setlocal("re_paren", var3);
      var3 = null;
      var1.setline(12);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*(<=|>=|<|>|!=|==)\\s*([^\\s,]+)\\s*$"));
      var1.setlocal("re_splitComparison", var3);
      var3 = null;
      var1.setline(16);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, splitUp$1, PyString.fromInterned("Parse a single version comparison.\n\n    Return (comparison string, StrictVersion)\n    "));
      var1.setlocal("splitUp", var6);
      var3 = null;
      var1.setline(27);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("<"), var1.getname("operator").__getattr__("lt"), PyString.fromInterned("<="), var1.getname("operator").__getattr__("le"), PyString.fromInterned("=="), var1.getname("operator").__getattr__("eq"), PyString.fromInterned(">"), var1.getname("operator").__getattr__("gt"), PyString.fromInterned(">="), var1.getname("operator").__getattr__("ge"), PyString.fromInterned("!="), var1.getname("operator").__getattr__("ne")});
      var1.setlocal("compmap", var7);
      var3 = null;
      var1.setline(30);
      var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("VersionPredicate", var5, VersionPredicate$2);
      var1.setlocal("VersionPredicate", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(140);
      var3 = var1.getname("None");
      var1.setlocal("_provision_rx", var3);
      var3 = null;
      var1.setline(142);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, split_provision$6, PyString.fromInterned("Return the name and optional version number of a provision.\n\n    The version number, if given, will be returned as a `StrictVersion`\n    instance, otherwise it will be `None`.\n\n    >>> split_provision('mypkg')\n    ('mypkg', None)\n    >>> split_provision(' mypkg( 1.2 ) ')\n    ('mypkg', StrictVersion ('1.2'))\n    "));
      var1.setlocal("split_provision", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject splitUp$1(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyString.fromInterned("Parse a single version comparison.\n\n    Return (comparison string, StrictVersion)\n    ");
      var1.setline(21);
      PyObject var3 = var1.getglobal("re_splitComparison").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(22);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(23);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("bad package restriction syntax: %r")._mod(var1.getlocal(0))));
      } else {
         var1.setline(24);
         var3 = var1.getlocal(1).__getattr__("groups").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(25);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("distutils").__getattr__("version").__getattr__("StrictVersion").__call__(var2, var1.getlocal(3))});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject VersionPredicate$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Parse and test package version predicates.\n\n    >>> v = VersionPredicate('pyepat.abc (>1.0, <3333.3a1, !=1555.1b3)')\n\n    The `name` attribute provides the full dotted name that is given::\n\n    >>> v.name\n    'pyepat.abc'\n\n    The str() of a `VersionPredicate` provides a normalized\n    human-readable version of the expression::\n\n    >>> print v\n    pyepat.abc (> 1.0, < 3333.3a1, != 1555.1b3)\n\n    The `satisfied_by()` method can be used to determine with a given\n    version number is included in the set described by the version\n    restrictions::\n\n    >>> v.satisfied_by('1.1')\n    True\n    >>> v.satisfied_by('1.4')\n    True\n    >>> v.satisfied_by('1.0')\n    False\n    >>> v.satisfied_by('4444.4')\n    False\n    >>> v.satisfied_by('1555.1b3')\n    False\n\n    `VersionPredicate` is flexible in accepting extra whitespace::\n\n    >>> v = VersionPredicate(' pat( ==  0.1  )  ')\n    >>> v.name\n    'pat'\n    >>> v.satisfied_by('0.1')\n    True\n    >>> v.satisfied_by('0.2')\n    False\n\n    If any version numbers passed in do not conform to the\n    restrictions of `StrictVersion`, a `ValueError` is raised::\n\n    >>> v = VersionPredicate('p1.p2.p3.p4(>=1.0, <=1.3a1, !=1.2zb3)')\n    Traceback (most recent call last):\n      ...\n    ValueError: invalid version number '1.2zb3'\n\n    It the module or package name given does not conform to what's\n    allowed as a legal module or package name, `ValueError` is\n    raised::\n\n    >>> v = VersionPredicate('foo-bar')\n    Traceback (most recent call last):\n      ...\n    ValueError: expected parenthesized list: '-bar'\n\n    >>> v = VersionPredicate('foo bar (12.21)')\n    Traceback (most recent call last):\n      ...\n    ValueError: expected parenthesized list: 'bar (12.21)'\n\n    "));
      var1.setline(93);
      PyString.fromInterned("Parse and test package version predicates.\n\n    >>> v = VersionPredicate('pyepat.abc (>1.0, <3333.3a1, !=1555.1b3)')\n\n    The `name` attribute provides the full dotted name that is given::\n\n    >>> v.name\n    'pyepat.abc'\n\n    The str() of a `VersionPredicate` provides a normalized\n    human-readable version of the expression::\n\n    >>> print v\n    pyepat.abc (> 1.0, < 3333.3a1, != 1555.1b3)\n\n    The `satisfied_by()` method can be used to determine with a given\n    version number is included in the set described by the version\n    restrictions::\n\n    >>> v.satisfied_by('1.1')\n    True\n    >>> v.satisfied_by('1.4')\n    True\n    >>> v.satisfied_by('1.0')\n    False\n    >>> v.satisfied_by('4444.4')\n    False\n    >>> v.satisfied_by('1555.1b3')\n    False\n\n    `VersionPredicate` is flexible in accepting extra whitespace::\n\n    >>> v = VersionPredicate(' pat( ==  0.1  )  ')\n    >>> v.name\n    'pat'\n    >>> v.satisfied_by('0.1')\n    True\n    >>> v.satisfied_by('0.2')\n    False\n\n    If any version numbers passed in do not conform to the\n    restrictions of `StrictVersion`, a `ValueError` is raised::\n\n    >>> v = VersionPredicate('p1.p2.p3.p4(>=1.0, <=1.3a1, !=1.2zb3)')\n    Traceback (most recent call last):\n      ...\n    ValueError: invalid version number '1.2zb3'\n\n    It the module or package name given does not conform to what's\n    allowed as a legal module or package name, `ValueError` is\n    raised::\n\n    >>> v = VersionPredicate('foo-bar')\n    Traceback (most recent call last):\n      ...\n    ValueError: expected parenthesized list: '-bar'\n\n    >>> v = VersionPredicate('foo bar (12.21)')\n    Traceback (most recent call last):\n      ...\n    ValueError: expected parenthesized list: 'bar (12.21)'\n\n    ");
      var1.setline(95);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Parse a version predicate string.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$4, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, satisfied_by$5, PyString.fromInterned("True if version is compatible with all the predicates in self.\n        The parameter version must be acceptable to the StrictVersion\n        constructor.  It may be either a string or StrictVersion.\n        "));
      var1.setlocal("satisfied_by", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Parse a version predicate string.\n        ");
      var1.setline(102);
      PyObject var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(103);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(104);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("empty package restriction")));
      } else {
         var1.setline(105);
         var3 = var1.getglobal("re_validPackage").__getattr__("match").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(106);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(107);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("bad package name in %r")._mod(var1.getlocal(1))));
         } else {
            var1.setline(108);
            var3 = var1.getlocal(2).__getattr__("groups").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.getlocal(0).__setattr__("name", var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
            var1.setline(109);
            var3 = var1.getlocal(3).__getattr__("strip").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(110);
            PyList var7;
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(111);
               var3 = var1.getglobal("re_paren").__getattr__("match").__call__(var2, var1.getlocal(3));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(112);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(113);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("expected parenthesized list: %r")._mod(var1.getlocal(3))));
               }

               var1.setline(114);
               var3 = var1.getlocal(2).__getattr__("groups").__call__(var2).__getitem__(Py.newInteger(0));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(115);
               PyList var10000 = new PyList();
               var3 = var10000.__getattr__("append");
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(115);
               var3 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

               while(true) {
                  var1.setline(115);
                  PyObject var6 = var3.__iternext__();
                  if (var6 == null) {
                     var1.setline(115);
                     var1.dellocal(5);
                     var7 = var10000;
                     var1.getlocal(0).__setattr__((String)"pred", var7);
                     var3 = null;
                     var1.setline(116);
                     if (var1.getlocal(0).__getattr__("pred").__not__().__nonzero__()) {
                        var1.setline(117);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("empty parenthesized list in %r")._mod(var1.getlocal(1))));
                     }
                     break;
                  }

                  var1.setlocal(6, var6);
                  var1.setline(115);
                  var1.getlocal(5).__call__(var2, var1.getglobal("splitUp").__call__(var2, var1.getlocal(6)));
               }
            } else {
               var1.setline(120);
               var7 = new PyList(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"pred", var7);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __str__$4(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3;
      if (!var1.getlocal(0).__getattr__("pred").__nonzero__()) {
         var1.setline(127);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(124);
         PyList var10000 = new PyList();
         var3 = var10000.__getattr__("append");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(124);
         var3 = var1.getlocal(0).__getattr__("pred").__iter__();

         while(true) {
            var1.setline(124);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(124);
               var1.dellocal(2);
               PyList var7 = var10000;
               var1.setlocal(1, var7);
               var3 = null;
               var1.setline(125);
               var3 = var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(" ("))._add(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned(")"));
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(124);
            var1.getlocal(2).__call__(var2, var1.getlocal(3)._add(PyString.fromInterned(" "))._add(var1.getglobal("str").__call__(var2, var1.getlocal(4))));
         }
      }
   }

   public PyObject satisfied_by$5(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("True if version is compatible with all the predicates in self.\n        The parameter version must be acceptable to the StrictVersion\n        constructor.  It may be either a string or StrictVersion.\n        ");
      var1.setline(134);
      PyObject var3 = var1.getlocal(0).__getattr__("pred").__iter__();

      PyObject var7;
      do {
         var1.setline(134);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(137);
            var7 = var1.getglobal("True");
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
         var1.setline(135);
      } while(!var1.getglobal("compmap").__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(1), var1.getlocal(3)).__not__().__nonzero__());

      var1.setline(136);
      var7 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject split_provision$6(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("Return the name and optional version number of a provision.\n\n    The version number, if given, will be returned as a `StrictVersion`\n    instance, otherwise it will be `None`.\n\n    >>> split_provision('mypkg')\n    ('mypkg', None)\n    >>> split_provision(' mypkg( 1.2 ) ')\n    ('mypkg', StrictVersion ('1.2'))\n    ");
      var1.setline(154);
      PyObject var3 = var1.getglobal("_provision_rx");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(155);
         var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z_]\\w*(?:\\.[a-zA-Z_]\\w*)*)(?:\\s*\\(\\s*([^)\\s]+)\\s*\\))?$"));
         var1.setglobal("_provision_rx", var3);
         var3 = null;
      }

      var1.setline(157);
      var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getglobal("_provision_rx").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(159);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(160);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("illegal provides specification: %r")._mod(var1.getlocal(0))));
      } else {
         var1.setline(161);
         var10000 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("None");
         }

         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(162);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(163);
            var3 = var1.getglobal("distutils").__getattr__("version").__getattr__("StrictVersion").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(164);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public versionpredicate$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pred", "res", "comp", "verStr"};
      splitUp$1 = Py.newCode(1, var2, var1, "splitUp", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      VersionPredicate$2 = Py.newCode(0, var2, var1, "VersionPredicate", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "versionPredicateStr", "match", "paren", "str", "_[115_25]", "aPred"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 95, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq", "_[124_19]", "cond", "ver"};
      __str__$4 = Py.newCode(1, var2, var1, "__str__", 122, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "version", "cond", "ver"};
      satisfied_by$5 = Py.newCode(2, var2, var1, "satisfied_by", 129, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value", "m", "ver"};
      split_provision$6 = Py.newCode(1, var2, var1, "split_provision", 142, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new versionpredicate$py("distutils/versionpredicate$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(versionpredicate$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.splitUp$1(var2, var3);
         case 2:
            return this.VersionPredicate$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__str__$4(var2, var3);
         case 5:
            return this.satisfied_by$5(var2, var3);
         case 6:
            return this.split_provision$6(var2, var3);
         default:
            return null;
      }
   }
}

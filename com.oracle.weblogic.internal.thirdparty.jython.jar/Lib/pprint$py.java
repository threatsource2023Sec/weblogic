import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("pprint.py")
public class pprint$py extends PyFunctionTable implements PyRunnable {
   static pprint$py self;
   static final PyCode f$0;
   static final PyCode pprint$1;
   static final PyCode pformat$2;
   static final PyCode saferepr$3;
   static final PyCode isreadable$4;
   static final PyCode isrecursive$5;
   static final PyCode _sorted$6;
   static final PyCode PrettyPrinter$7;
   static final PyCode __init__$8;
   static final PyCode pprint$9;
   static final PyCode pformat$10;
   static final PyCode isrecursive$11;
   static final PyCode isreadable$12;
   static final PyCode _format$13;
   static final PyCode _repr$14;
   static final PyCode format$15;
   static final PyCode _safe_repr$16;
   static final PyCode _recursion$17;
   static final PyCode _perfcheck$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Support to pretty-print lists, tuples, & dictionaries recursively.\n\nVery simple, but useful, especially in debugging data structures.\n\nClasses\n-------\n\nPrettyPrinter()\n    Handle pretty-printing operations onto a stream using a configured\n    set of formatting parameters.\n\nFunctions\n---------\n\npformat()\n    Format a Python object into a pretty-printed representation.\n\npprint()\n    Pretty-print a Python object to a stream [default is sys.stdout].\n\nsaferepr()\n    Generate a 'standard' repr()-like value, but protect against recursive\n    data structures.\n\n"));
      var1.setline(35);
      PyString.fromInterned("Support to pretty-print lists, tuples, & dictionaries recursively.\n\nVery simple, but useful, especially in debugging data structures.\n\nClasses\n-------\n\nPrettyPrinter()\n    Handle pretty-printing operations onto a stream using a configured\n    set of formatting parameters.\n\nFunctions\n---------\n\npformat()\n    Format a Python object into a pretty-printed representation.\n\npprint()\n    Pretty-print a Python object to a stream [default is sys.stdout].\n\nsaferepr()\n    Generate a 'standard' repr()-like value, but protect against recursive\n    data structures.\n\n");
      var1.setline(37);
      PyObject var3 = imp.importOneAs("sys", var1, -1);
      var1.setlocal("_sys", var3);
      var3 = null;
      var1.setline(38);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(40);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_StringIO", var4);
      var4 = null;
      var1.setline(42);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("pprint"), PyString.fromInterned("pformat"), PyString.fromInterned("isreadable"), PyString.fromInterned("isrecursive"), PyString.fromInterned("saferepr"), PyString.fromInterned("PrettyPrinter")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(46);
      var3 = PyString.fromInterned(", ").__getattr__("join");
      var1.setlocal("_commajoin", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("id");
      var1.setlocal("_id", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("len");
      var1.setlocal("_len", var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getname("type");
      var1.setlocal("_type", var3);
      var3 = null;
      var1.setline(52);
      var6 = new PyObject[]{var1.getname("None"), Py.newInteger(1), Py.newInteger(80), var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, pprint$1, PyString.fromInterned("Pretty-print a Python object to a stream [default is sys.stdout]."));
      var1.setlocal("pprint", var8);
      var3 = null;
      var1.setline(58);
      var6 = new PyObject[]{Py.newInteger(1), Py.newInteger(80), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, pformat$2, PyString.fromInterned("Format a Python object into a pretty-printed representation."));
      var1.setlocal("pformat", var8);
      var3 = null;
      var1.setline(62);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, saferepr$3, PyString.fromInterned("Version of repr() which can handle recursive data structures."));
      var1.setlocal("saferepr", var8);
      var3 = null;
      var1.setline(66);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, isreadable$4, PyString.fromInterned("Determine if saferepr(object) is readable by eval()."));
      var1.setlocal("isreadable", var8);
      var3 = null;
      var1.setline(70);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, isrecursive$5, PyString.fromInterned("Determine if object requires a recursive representation."));
      var1.setlocal("isrecursive", var8);
      var3 = null;
      var1.setline(74);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _sorted$6, (PyObject)null);
      var1.setlocal("_sorted", var8);
      var3 = null;
      var1.setline(81);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("PrettyPrinter", var6, PrettyPrinter$7);
      var1.setlocal("PrettyPrinter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(247);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _safe_repr$16, (PyObject)null);
      var1.setlocal("_safe_repr", var8);
      var3 = null;
      var1.setline(331);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _recursion$17, (PyObject)null);
      var1.setlocal("_recursion", var8);
      var3 = null;
      var1.setline(336);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, _perfcheck$18, (PyObject)null);
      var1.setlocal("_perfcheck", var8);
      var3 = null;
      var1.setline(349);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(350);
         var1.getname("_perfcheck").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pprint$1(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("Pretty-print a Python object to a stream [default is sys.stdout].");
      var1.setline(54);
      PyObject var10000 = var1.getglobal("PrettyPrinter");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      String[] var4 = new String[]{"stream", "indent", "width", "depth"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(56);
      var1.getlocal(5).__getattr__("pprint").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pformat$2(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Format a Python object into a pretty-printed representation.");
      var1.setline(60);
      PyObject var10000 = var1.getglobal("PrettyPrinter");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"indent", "width", "depth"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000.__getattr__("pformat").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject saferepr$3(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("Version of repr() which can handle recursive data structures.");
      var1.setline(64);
      PyObject var3 = var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(0), new PyDictionary(Py.EmptyObjects), var1.getglobal("None"), Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isreadable$4(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Determine if saferepr(object) is readable by eval().");
      var1.setline(68);
      PyObject var3 = var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(0), new PyDictionary(Py.EmptyObjects), var1.getglobal("None"), Py.newInteger(0)).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isrecursive$5(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyString.fromInterned("Determine if object requires a recursive representation.");
      var1.setline(72);
      PyObject var3 = var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(0), new PyDictionary(Py.EmptyObjects), var1.getglobal("None"), Py.newInteger(0)).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _sorted$6(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      Throwable var10000;
      label34: {
         boolean var10001;
         try {
            var1.setline(76);
            if (var1.getglobal("_sys").__getattr__("py3kwarning").__nonzero__()) {
               var1.setline(77);
               var1.getglobal("warnings").__getattr__("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned("comparing unequal types not supported"), (PyObject)var1.getglobal("DeprecationWarning"));
            }

            var1.setline(79);
            var4 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label34;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject PrettyPrinter$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(82);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), Py.newInteger(80), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, PyString.fromInterned("Handle pretty printing operations onto a stream using a set of\n        configured parameters.\n\n        indent\n            Number of spaces to indent for each level of nesting.\n\n        width\n            Attempted maximum number of columns in the output.\n\n        depth\n            The maximum depth to print out nested structures.\n\n        stream\n            The desired output stream.  If omitted (or false), the standard\n            output stream available at construction will be used.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pprint$9, (PyObject)null);
      var1.setlocal("pprint", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pformat$10, (PyObject)null);
      var1.setlocal("pformat", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isrecursive$11, (PyObject)null);
      var1.setlocal("isrecursive", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isreadable$12, (PyObject)null);
      var1.setlocal("isreadable", var4);
      var3 = null;
      var1.setline(129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _format$13, (PyObject)null);
      var1.setlocal("_format", var4);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _repr$14, (PyObject)null);
      var1.setlocal("_repr", var4);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format$15, PyString.fromInterned("Format object for a specific context, returning a string\n        and flags indicating whether the representation is 'readable'\n        and whether the object represents a recursive construct.\n        "));
      var1.setlocal("format", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Handle pretty printing operations onto a stream using a set of\n        configured parameters.\n\n        indent\n            Number of spaces to indent for each level of nesting.\n\n        width\n            Attempted maximum number of columns in the output.\n\n        depth\n            The maximum depth to print out nested structures.\n\n        stream\n            The desired output stream.  If omitted (or false), the standard\n            output stream available at construction will be used.\n\n        ");
      var1.setline(100);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(102);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("indent must be >= 0"));
         }
      }

      var1.setline(103);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("depth must be > 0"));
         }
      }

      var1.setline(104);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("width must be != 0"));
      } else {
         var1.setline(105);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("_depth", var3);
         var3 = null;
         var1.setline(106);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_indent_per_level", var3);
         var3 = null;
         var1.setline(107);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_width", var3);
         var3 = null;
         var1.setline(108);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(109);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("_stream", var3);
            var3 = null;
         } else {
            var1.setline(111);
            var3 = var1.getglobal("_sys").__getattr__("stdout");
            var1.getlocal(0).__setattr__("_stream", var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject pprint$9(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var10000 = var1.getlocal(0).__getattr__("_format");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_stream"), Py.newInteger(0), Py.newInteger(0), new PyDictionary(Py.EmptyObjects), Py.newInteger(0)};
      var10000.__call__(var2, var3);
      var1.setline(115);
      var1.getlocal(0).__getattr__("_stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pformat$10(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getglobal("_StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(119);
      PyObject var10000 = var1.getlocal(0).__getattr__("_format");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), Py.newInteger(0), new PyDictionary(Py.EmptyObjects), Py.newInteger(0)};
      var10000.__call__(var2, var4);
      var1.setline(120);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isrecursive$11(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1), new PyDictionary(Py.EmptyObjects), Py.newInteger(0), Py.newInteger(0)).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isreadable$12(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1), new PyDictionary(Py.EmptyObjects), Py.newInteger(0), Py.newInteger(0));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(127);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(4).__not__();
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _format$13(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getlocal(6)._add(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getglobal("_id").__call__(var2, var1.getlocal(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(133);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getglobal("_recursion").__call__(var2, var1.getlocal(1)));
         var1.setline(134);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_recursive", var3);
         var3 = null;
         var1.setline(135);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_readable", var3);
         var3 = null;
         var1.setline(136);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(137);
         var3 = var1.getlocal(0).__getattr__("_repr").__call__(var2, var1.getlocal(1), var1.getlocal(5), var1.getlocal(6)._sub(Py.newInteger(1)));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(138);
         var3 = var1.getglobal("_type").__call__(var2, var1.getlocal(1));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(139);
         var3 = var1.getglobal("_len").__call__(var2, var1.getlocal(8));
         var10000 = var3._gt(var1.getlocal(0).__getattr__("_width")._sub(Py.newInteger(1))._sub(var1.getlocal(3))._sub(var1.getlocal(4)));
         var3 = null;
         var3 = var10000;
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(140);
         var3 = var1.getlocal(2).__getattr__("write");
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(142);
         var10000 = var1.getlocal(0).__getattr__("_depth");
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(6);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_depth"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(143);
            var1.getlocal(11).__call__(var2, var1.getlocal(8));
            var1.setline(144);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(146);
            var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(9), (PyObject)PyString.fromInterned("__repr__"), (PyObject)var1.getglobal("None"));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(147);
            var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("dict"));
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(12);
               var10000 = var3._is(var1.getglobal("dict").__getattr__("__repr__"));
               var3 = null;
            }

            PyObject var4;
            PyObject[] var5;
            PyInteger var10;
            PyObject[] var11;
            if (var10000.__nonzero__()) {
               var1.setline(148);
               var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{"));
               var1.setline(149);
               var3 = var1.getlocal(0).__getattr__("_indent_per_level");
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(150);
                  var1.getlocal(11).__call__(var2, var1.getlocal(0).__getattr__("_indent_per_level")._sub(Py.newInteger(1))._mul(PyString.fromInterned(" ")));
               }

               var1.setline(151);
               var3 = var1.getglobal("_len").__call__(var2, var1.getlocal(1));
               var1.setlocal(13, var3);
               var3 = null;
               var1.setline(152);
               if (var1.getlocal(13).__nonzero__()) {
                  var1.setline(153);
                  var10 = Py.newInteger(1);
                  var1.getlocal(5).__setitem__((PyObject)var1.getlocal(7), var10);
                  var3 = null;
                  var1.setline(154);
                  var3 = var1.getlocal(3)._add(var1.getlocal(0).__getattr__("_indent_per_level"));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(155);
                  var3 = var1.getglobal("_sorted").__call__(var2, var1.getlocal(1).__getattr__("items").__call__(var2));
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(156);
                  var3 = var1.getlocal(14).__getitem__(Py.newInteger(0));
                  PyObject[] var7 = Py.unpackSequence(var3, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(15, var8);
                  var5 = null;
                  var8 = var7[1];
                  var1.setlocal(16, var8);
                  var5 = null;
                  var3 = null;
                  var1.setline(157);
                  var3 = var1.getlocal(0).__getattr__("_repr").__call__(var2, var1.getlocal(15), var1.getlocal(5), var1.getlocal(6));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(158);
                  var1.getlocal(11).__call__(var2, var1.getlocal(8));
                  var1.setline(159);
                  var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(": "));
                  var1.setline(160);
                  var10000 = var1.getlocal(0).__getattr__("_format");
                  var11 = new PyObject[]{var1.getlocal(16), var1.getlocal(2), var1.getlocal(3)._add(var1.getglobal("_len").__call__(var2, var1.getlocal(8)))._add(Py.newInteger(2)), var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(5), var1.getlocal(6)};
                  var10000.__call__(var2, var11);
                  var1.setline(162);
                  var3 = var1.getlocal(13);
                  var10000 = var3._gt(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(163);
                     var3 = var1.getlocal(14).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

                     while(true) {
                        var1.setline(163);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var5 = Py.unpackSequence(var4, 2);
                        PyObject var6 = var5[0];
                        var1.setlocal(15, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(16, var6);
                        var6 = null;
                        var1.setline(164);
                        var8 = var1.getlocal(0).__getattr__("_repr").__call__(var2, var1.getlocal(15), var1.getlocal(5), var1.getlocal(6));
                        var1.setlocal(8, var8);
                        var5 = null;
                        var1.setline(165);
                        if (var1.getlocal(10).__nonzero__()) {
                           var1.setline(166);
                           var1.getlocal(11).__call__(var2, PyString.fromInterned(",\n%s%s: ")._mod(new PyTuple(new PyObject[]{PyString.fromInterned(" ")._mul(var1.getlocal(3)), var1.getlocal(8)})));
                        } else {
                           var1.setline(168);
                           var1.getlocal(11).__call__(var2, PyString.fromInterned(", %s: ")._mod(var1.getlocal(8)));
                        }

                        var1.setline(169);
                        var10000 = var1.getlocal(0).__getattr__("_format");
                        var5 = new PyObject[]{var1.getlocal(16), var1.getlocal(2), var1.getlocal(3)._add(var1.getglobal("_len").__call__(var2, var1.getlocal(8)))._add(Py.newInteger(2)), var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(5), var1.getlocal(6)};
                        var10000.__call__(var2, var5);
                     }
                  }

                  var1.setline(171);
                  var3 = var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("_indent_per_level"));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(172);
                  var1.getlocal(5).__delitem__(var1.getlocal(7));
               }

               var1.setline(173);
               var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"));
               var1.setline(174);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(176);
               var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("list"));
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(12);
                  var10000 = var3._is(var1.getglobal("list").__getattr__("__repr__"));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("tuple"));
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(12);
                     var10000 = var3._is(var1.getglobal("tuple").__getattr__("__repr__"));
                     var3 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("set"));
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(12);
                        var10000 = var3._is(var1.getglobal("set").__getattr__("__repr__"));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("frozenset"));
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(12);
                           var10000 = var3._is(var1.getglobal("frozenset").__getattr__("__repr__"));
                           var3 = null;
                        }
                     }
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(181);
                  var3 = var1.getglobal("_len").__call__(var2, var1.getlocal(1));
                  var1.setlocal(13, var3);
                  var3 = null;
                  var1.setline(182);
                  PyString var9;
                  if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("list")).__nonzero__()) {
                     var1.setline(183);
                     var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("["));
                     var1.setline(184);
                     var9 = PyString.fromInterned("]");
                     var1.setlocal(17, var9);
                     var3 = null;
                  } else {
                     var1.setline(185);
                     if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("set")).__nonzero__()) {
                        var1.setline(186);
                        if (var1.getlocal(13).__not__().__nonzero__()) {
                           var1.setline(187);
                           var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("set()"));
                           var1.setline(188);
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setline(189);
                        var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("set(["));
                        var1.setline(190);
                        var9 = PyString.fromInterned("])");
                        var1.setlocal(17, var9);
                        var3 = null;
                        var1.setline(191);
                        var3 = var1.getglobal("_sorted").__call__(var2, var1.getlocal(1));
                        var1.setlocal(1, var3);
                        var3 = null;
                        var1.setline(192);
                        var3 = var1.getlocal(3);
                        var3 = var3._iadd(Py.newInteger(4));
                        var1.setlocal(3, var3);
                     } else {
                        var1.setline(193);
                        if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("frozenset")).__nonzero__()) {
                           var1.setline(194);
                           if (var1.getlocal(13).__not__().__nonzero__()) {
                              var1.setline(195);
                              var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("frozenset()"));
                              var1.setline(196);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setline(197);
                           var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("frozenset(["));
                           var1.setline(198);
                           var9 = PyString.fromInterned("])");
                           var1.setlocal(17, var9);
                           var3 = null;
                           var1.setline(199);
                           var3 = var1.getglobal("_sorted").__call__(var2, var1.getlocal(1));
                           var1.setlocal(1, var3);
                           var3 = null;
                           var1.setline(200);
                           var3 = var1.getlocal(3);
                           var3 = var3._iadd(Py.newInteger(10));
                           var1.setlocal(3, var3);
                        } else {
                           var1.setline(202);
                           var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("("));
                           var1.setline(203);
                           var9 = PyString.fromInterned(")");
                           var1.setlocal(17, var9);
                           var3 = null;
                        }
                     }
                  }

                  var1.setline(204);
                  var3 = var1.getlocal(0).__getattr__("_indent_per_level");
                  var10000 = var3._gt(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(10);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(205);
                     var1.getlocal(11).__call__(var2, var1.getlocal(0).__getattr__("_indent_per_level")._sub(Py.newInteger(1))._mul(PyString.fromInterned(" ")));
                  }

                  var1.setline(206);
                  if (var1.getlocal(13).__nonzero__()) {
                     var1.setline(207);
                     var10 = Py.newInteger(1);
                     var1.getlocal(5).__setitem__((PyObject)var1.getlocal(7), var10);
                     var3 = null;
                     var1.setline(208);
                     var3 = var1.getlocal(3)._add(var1.getlocal(0).__getattr__("_indent_per_level"));
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(209);
                     var10000 = var1.getlocal(0).__getattr__("_format");
                     var11 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(5), var1.getlocal(6)};
                     var10000.__call__(var2, var11);
                     var1.setline(211);
                     var3 = var1.getlocal(13);
                     var10000 = var3._gt(Py.newInteger(1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(212);
                        var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

                        while(true) {
                           var1.setline(212);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              break;
                           }

                           var1.setlocal(16, var4);
                           var1.setline(213);
                           if (var1.getlocal(10).__nonzero__()) {
                              var1.setline(214);
                              var1.getlocal(11).__call__(var2, PyString.fromInterned(",\n")._add(PyString.fromInterned(" ")._mul(var1.getlocal(3))));
                           } else {
                              var1.setline(216);
                              var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", "));
                           }

                           var1.setline(217);
                           var10000 = var1.getlocal(0).__getattr__("_format");
                           var5 = new PyObject[]{var1.getlocal(16), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(5), var1.getlocal(6)};
                           var10000.__call__(var2, var5);
                        }
                     }

                     var1.setline(219);
                     var3 = var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("_indent_per_level"));
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(220);
                     var1.getlocal(5).__delitem__(var1.getlocal(7));
                  }

                  var1.setline(221);
                  var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(9), var1.getglobal("tuple"));
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(13);
                     var10000 = var3._eq(Py.newInteger(1));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(222);
                     var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
                  }

                  var1.setline(223);
                  var1.getlocal(11).__call__(var2, var1.getlocal(17));
                  var1.setline(224);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(226);
                  var1.getlocal(11).__call__(var2, var1.getlocal(8));
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject _repr$14(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getattr__("copy").__call__(var2), var1.getlocal(0).__getattr__("_depth"), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(231);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(232);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_readable", var3);
         var3 = null;
      }

      var1.setline(233);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(234);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_recursive", var3);
         var3 = null;
      }

      var1.setline(235);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format$15(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("Format object for a specific context, returning a string\n        and flags indicating whether the representation is 'readable'\n        and whether the object represents a recursive construct.\n        ");
      var1.setline(242);
      PyObject var3 = var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _safe_repr$16(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyObject var3 = var1.getglobal("_type").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(249);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("str"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      PyTuple var9;
      PyString var10;
      if (var10000.__nonzero__()) {
         var1.setline(250);
         PyString var12 = PyString.fromInterned("locale");
         var10000 = var12._notin(var1.getglobal("_sys").__getattr__("modules"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(251);
            var9 = new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0)), var1.getglobal("True"), var1.getglobal("False")});
            var1.f_lasti = -1;
            return var9;
         } else {
            var1.setline(252);
            var10 = PyString.fromInterned("'");
            var10000 = var10._in(var1.getlocal(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10 = PyString.fromInterned("\"");
               var10000 = var10._notin(var1.getlocal(0));
               var4 = null;
            }

            PyDictionary var16;
            if (var10000.__nonzero__()) {
               var1.setline(253);
               var10 = PyString.fromInterned("\"");
               var1.setlocal(5, var10);
               var4 = null;
               var1.setline(254);
               var16 = new PyDictionary(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("\\\"")});
               var1.setlocal(6, var16);
               var4 = null;
            } else {
               var1.setline(256);
               var10 = PyString.fromInterned("'");
               var1.setlocal(5, var10);
               var4 = null;
               var1.setline(257);
               var16 = new PyDictionary(new PyObject[]{PyString.fromInterned("'"), PyString.fromInterned("\\'")});
               var1.setlocal(6, var16);
               var4 = null;
            }

            var1.setline(258);
            var4 = var1.getlocal(6).__getattr__("get");
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(259);
            var4 = var1.getglobal("_StringIO").__call__(var2);
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(260);
            var4 = var1.getlocal(8).__getattr__("write");
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(261);
            var4 = var1.getlocal(0).__iter__();

            while(true) {
               var1.setline(261);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(266);
                  var9 = new PyTuple(new PyObject[]{PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8).__getattr__("getvalue").__call__(var2), var1.getlocal(5)})), var1.getglobal("True"), var1.getglobal("False")});
                  var1.f_lasti = -1;
                  return var9;
               }

               var1.setlocal(10, var5);
               var1.setline(262);
               if (var1.getlocal(10).__getattr__("isalpha").__call__(var2).__nonzero__()) {
                  var1.setline(263);
                  var1.getlocal(9).__call__(var2, var1.getlocal(10));
               } else {
                  var1.setline(265);
                  var1.getlocal(9).__call__(var2, var1.getlocal(7).__call__(var2, var1.getlocal(10), var1.getglobal("repr").__call__(var2, var1.getlocal(10)).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null)));
               }
            }
         }
      } else {
         var1.setline(268);
         var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("__repr__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(11, var4);
         var4 = null;
         var1.setline(269);
         var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(4), var1.getglobal("dict"));
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(11);
            var10000 = var4._is(var1.getglobal("dict").__getattr__("__repr__"));
            var4 = null;
         }

         PyObject var6;
         PyObject[] var7;
         PyObject var8;
         PyInteger var14;
         PyList var15;
         PyObject[] var10002;
         PyObject var10005;
         if (var10000.__nonzero__()) {
            var1.setline(270);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(271);
               var9 = new PyTuple(new PyObject[]{PyString.fromInterned("{}"), var1.getglobal("True"), var1.getglobal("False")});
               var1.f_lasti = -1;
               return var9;
            } else {
               var1.setline(272);
               var4 = var1.getglobal("_id").__call__(var2, var1.getlocal(0));
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(273);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(3);
                  var10000 = var4._ge(var1.getlocal(2));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(274);
                  var10002 = new PyObject[]{PyString.fromInterned("{...}"), var1.getglobal("False"), null};
                  var4 = var1.getlocal(12);
                  var10005 = var4._in(var1.getlocal(1));
                  var4 = null;
                  var10002[2] = var10005;
                  var9 = new PyTuple(var10002);
                  var1.f_lasti = -1;
                  return var9;
               } else {
                  var1.setline(275);
                  var4 = var1.getlocal(12);
                  var10000 = var4._in(var1.getlocal(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(276);
                     var9 = new PyTuple(new PyObject[]{var1.getglobal("_recursion").__call__(var2, var1.getlocal(0)), var1.getglobal("False"), var1.getglobal("True")});
                     var1.f_lasti = -1;
                     return var9;
                  } else {
                     var1.setline(277);
                     var14 = Py.newInteger(1);
                     var1.getlocal(1).__setitem__((PyObject)var1.getlocal(12), var14);
                     var4 = null;
                     var1.setline(278);
                     var4 = var1.getglobal("True");
                     var1.setlocal(13, var4);
                     var4 = null;
                     var1.setline(279);
                     var4 = var1.getglobal("False");
                     var1.setlocal(14, var4);
                     var4 = null;
                     var1.setline(280);
                     var15 = new PyList(Py.EmptyObjects);
                     var1.setlocal(15, var15);
                     var4 = null;
                     var1.setline(281);
                     var4 = var1.getlocal(15).__getattr__("append");
                     var1.setlocal(16, var4);
                     var4 = null;
                     var1.setline(282);
                     var4 = var1.getlocal(3);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(3, var4);
                     var1.setline(283);
                     var4 = var1.getglobal("_safe_repr");
                     var1.setlocal(17, var4);
                     var4 = null;
                     var1.setline(284);
                     var4 = var1.getglobal("_sorted").__call__(var2, var1.getlocal(0).__getattr__("items").__call__(var2)).__iter__();

                     while(true) {
                        var1.setline(284);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(291);
                           var1.getlocal(1).__delitem__(var1.getlocal(12));
                           var1.setline(292);
                           var9 = new PyTuple(new PyObject[]{PyString.fromInterned("{%s}")._mod(var1.getglobal("_commajoin").__call__(var2, var1.getlocal(15))), var1.getlocal(13), var1.getlocal(14)});
                           var1.f_lasti = -1;
                           return var9;
                        }

                        PyObject[] var13 = Py.unpackSequence(var5, 2);
                        PyObject var11 = var13[0];
                        var1.setlocal(18, var11);
                        var7 = null;
                        var11 = var13[1];
                        var1.setlocal(19, var11);
                        var7 = null;
                        var1.setline(285);
                        var6 = var1.getlocal(17).__call__(var2, var1.getlocal(18), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                        var7 = Py.unpackSequence(var6, 3);
                        var8 = var7[0];
                        var1.setlocal(20, var8);
                        var8 = null;
                        var8 = var7[1];
                        var1.setlocal(21, var8);
                        var8 = null;
                        var8 = var7[2];
                        var1.setlocal(22, var8);
                        var8 = null;
                        var6 = null;
                        var1.setline(286);
                        var6 = var1.getlocal(17).__call__(var2, var1.getlocal(19), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                        var7 = Py.unpackSequence(var6, 3);
                        var8 = var7[0];
                        var1.setlocal(23, var8);
                        var8 = null;
                        var8 = var7[1];
                        var1.setlocal(24, var8);
                        var8 = null;
                        var8 = var7[2];
                        var1.setlocal(25, var8);
                        var8 = null;
                        var6 = null;
                        var1.setline(287);
                        var1.getlocal(16).__call__(var2, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(20), var1.getlocal(23)})));
                        var1.setline(288);
                        var10000 = var1.getlocal(13);
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(21);
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getlocal(24);
                           }
                        }

                        var6 = var10000;
                        var1.setlocal(13, var6);
                        var6 = null;
                        var1.setline(289);
                        var10000 = var1.getlocal(22);
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(25);
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(290);
                           var6 = var1.getglobal("True");
                           var1.setlocal(14, var6);
                           var6 = null;
                        }
                     }
                  }
               }
            }
         } else {
            var1.setline(294);
            var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(4), var1.getglobal("list"));
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(11);
               var10000 = var4._is(var1.getglobal("list").__getattr__("__repr__"));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(4), var1.getglobal("tuple"));
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(11);
                  var10000 = var4._is(var1.getglobal("tuple").__getattr__("__repr__"));
                  var4 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(296);
               if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(4), var1.getglobal("list")).__nonzero__()) {
                  var1.setline(297);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(298);
                     var9 = new PyTuple(new PyObject[]{PyString.fromInterned("[]"), var1.getglobal("True"), var1.getglobal("False")});
                     var1.f_lasti = -1;
                     return var9;
                  }

                  var1.setline(299);
                  var10 = PyString.fromInterned("[%s]");
                  var1.setlocal(26, var10);
                  var4 = null;
               } else {
                  var1.setline(300);
                  var4 = var1.getglobal("_len").__call__(var2, var1.getlocal(0));
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(301);
                     var10 = PyString.fromInterned("(%s,)");
                     var1.setlocal(26, var10);
                     var4 = null;
                  } else {
                     var1.setline(303);
                     if (var1.getlocal(0).__not__().__nonzero__()) {
                        var1.setline(304);
                        var9 = new PyTuple(new PyObject[]{PyString.fromInterned("()"), var1.getglobal("True"), var1.getglobal("False")});
                        var1.f_lasti = -1;
                        return var9;
                     }

                     var1.setline(305);
                     var10 = PyString.fromInterned("(%s)");
                     var1.setlocal(26, var10);
                     var4 = null;
                  }
               }

               var1.setline(306);
               var4 = var1.getglobal("_id").__call__(var2, var1.getlocal(0));
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(307);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(3);
                  var10000 = var4._ge(var1.getlocal(2));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(308);
                  var10002 = new PyObject[]{var1.getlocal(26)._mod(PyString.fromInterned("...")), var1.getglobal("False"), null};
                  var4 = var1.getlocal(12);
                  var10005 = var4._in(var1.getlocal(1));
                  var4 = null;
                  var10002[2] = var10005;
                  var9 = new PyTuple(var10002);
                  var1.f_lasti = -1;
                  return var9;
               } else {
                  var1.setline(309);
                  var4 = var1.getlocal(12);
                  var10000 = var4._in(var1.getlocal(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(310);
                     var9 = new PyTuple(new PyObject[]{var1.getglobal("_recursion").__call__(var2, var1.getlocal(0)), var1.getglobal("False"), var1.getglobal("True")});
                     var1.f_lasti = -1;
                     return var9;
                  } else {
                     var1.setline(311);
                     var14 = Py.newInteger(1);
                     var1.getlocal(1).__setitem__((PyObject)var1.getlocal(12), var14);
                     var4 = null;
                     var1.setline(312);
                     var4 = var1.getglobal("True");
                     var1.setlocal(13, var4);
                     var4 = null;
                     var1.setline(313);
                     var4 = var1.getglobal("False");
                     var1.setlocal(14, var4);
                     var4 = null;
                     var1.setline(314);
                     var15 = new PyList(Py.EmptyObjects);
                     var1.setlocal(15, var15);
                     var4 = null;
                     var1.setline(315);
                     var4 = var1.getlocal(15).__getattr__("append");
                     var1.setlocal(16, var4);
                     var4 = null;
                     var1.setline(316);
                     var4 = var1.getlocal(3);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(3, var4);
                     var1.setline(317);
                     var4 = var1.getlocal(0).__iter__();

                     while(true) {
                        var1.setline(317);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(324);
                           var1.getlocal(1).__delitem__(var1.getlocal(12));
                           var1.setline(325);
                           var9 = new PyTuple(new PyObject[]{var1.getlocal(26)._mod(var1.getglobal("_commajoin").__call__(var2, var1.getlocal(15))), var1.getlocal(13), var1.getlocal(14)});
                           var1.f_lasti = -1;
                           return var9;
                        }

                        var1.setlocal(27, var5);
                        var1.setline(318);
                        var6 = var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(27), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                        var7 = Py.unpackSequence(var6, 3);
                        var8 = var7[0];
                        var1.setlocal(28, var8);
                        var8 = null;
                        var8 = var7[1];
                        var1.setlocal(29, var8);
                        var8 = null;
                        var8 = var7[2];
                        var1.setlocal(30, var8);
                        var8 = null;
                        var6 = null;
                        var1.setline(319);
                        var1.getlocal(16).__call__(var2, var1.getlocal(28));
                        var1.setline(320);
                        if (var1.getlocal(29).__not__().__nonzero__()) {
                           var1.setline(321);
                           var6 = var1.getglobal("False");
                           var1.setlocal(13, var6);
                           var6 = null;
                        }

                        var1.setline(322);
                        if (var1.getlocal(30).__nonzero__()) {
                           var1.setline(323);
                           var6 = var1.getglobal("True");
                           var1.setlocal(14, var6);
                           var6 = null;
                        }
                     }
                  }
               }
            } else {
               var1.setline(327);
               var4 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
               var1.setlocal(31, var4);
               var4 = null;
               var1.setline(328);
               PyTuple var17 = new PyTuple;
               var10002 = new PyObject[]{var1.getlocal(31), null, null};
               var10005 = var1.getlocal(31);
               if (var10005.__nonzero__()) {
                  var10005 = var1.getlocal(31).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<")).__not__();
               }

               var10002[1] = var10005;
               var10002[2] = var1.getglobal("False");
               var17.<init>(var10002);
               var9 = var17;
               var1.f_lasti = -1;
               return var9;
            }
         }
      }
   }

   public PyObject _recursion$17(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = PyString.fromInterned("<Recursion on %s with id=%s>")._mod(new PyTuple(new PyObject[]{var1.getglobal("_type").__call__(var2, var1.getlocal(0)).__getattr__("__name__"), var1.getglobal("_id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _perfcheck$18(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(338);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(339);
         var3 = (new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("string"), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)}), new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(4)}), new PyDictionary(new PyObject[]{Py.newInteger(5), Py.newInteger(6), Py.newInteger(7), Py.newInteger(8)})})}))._mul(Py.newInteger(100000));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(340);
      var3 = var1.getglobal("PrettyPrinter").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getlocal(1).__getattr__("time").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(342);
      var1.getglobal("_safe_repr").__call__(var2, var1.getlocal(0), new PyDictionary(Py.EmptyObjects), var1.getglobal("None"), Py.newInteger(0));
      var1.setline(343);
      var3 = var1.getlocal(1).__getattr__("time").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(344);
      var1.getlocal(2).__getattr__("pformat").__call__(var2, var1.getlocal(0));
      var1.setline(345);
      var3 = var1.getlocal(1).__getattr__("time").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(346);
      Py.printComma(PyString.fromInterned("_safe_repr:"));
      Py.println(var1.getlocal(4)._sub(var1.getlocal(3)));
      var1.setline(347);
      Py.printComma(PyString.fromInterned("pformat:"));
      Py.println(var1.getlocal(5)._sub(var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public pprint$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"object", "stream", "indent", "width", "depth", "printer"};
      pprint$1 = Py.newCode(5, var2, var1, "pprint", 52, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "indent", "width", "depth"};
      pformat$2 = Py.newCode(4, var2, var1, "pformat", 58, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      saferepr$3 = Py.newCode(1, var2, var1, "saferepr", 62, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isreadable$4 = Py.newCode(1, var2, var1, "isreadable", 66, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isrecursive$5 = Py.newCode(1, var2, var1, "isrecursive", 70, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterable"};
      _sorted$6 = Py.newCode(1, var2, var1, "_sorted", 74, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PrettyPrinter$7 = Py.newCode(0, var2, var1, "PrettyPrinter", 81, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "indent", "width", "depth", "stream"};
      __init__$8 = Py.newCode(5, var2, var1, "__init__", 82, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      pprint$9 = Py.newCode(2, var2, var1, "pprint", 113, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "sio"};
      pformat$10 = Py.newCode(2, var2, var1, "pformat", 117, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      isrecursive$11 = Py.newCode(2, var2, var1, "isrecursive", 122, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "s", "readable", "recursive"};
      isreadable$12 = Py.newCode(2, var2, var1, "isreadable", 125, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "stream", "indent", "allowance", "context", "level", "objid", "rep", "typ", "sepLines", "write", "r", "length", "items", "key", "ent", "endchar"};
      _format$13 = Py.newCode(7, var2, var1, "_format", 129, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "context", "level", "repr", "readable", "recursive"};
      _repr$14 = Py.newCode(4, var2, var1, "_repr", 228, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "context", "maxlevels", "level"};
      format$15 = Py.newCode(5, var2, var1, "format", 237, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "context", "maxlevels", "level", "typ", "closure", "quotes", "qget", "sio", "write", "char", "r", "objid", "readable", "recursive", "components", "append", "saferepr", "k", "v", "krepr", "kreadable", "krecur", "vrepr", "vreadable", "vrecur", "format", "o", "orepr", "oreadable", "orecur", "rep"};
      _safe_repr$16 = Py.newCode(4, var2, var1, "_safe_repr", 247, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      _recursion$17 = Py.newCode(1, var2, var1, "_recursion", 331, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "time", "p", "t1", "t2", "t3"};
      _perfcheck$18 = Py.newCode(1, var2, var1, "_perfcheck", 336, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pprint$py("pprint$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pprint$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.pprint$1(var2, var3);
         case 2:
            return this.pformat$2(var2, var3);
         case 3:
            return this.saferepr$3(var2, var3);
         case 4:
            return this.isreadable$4(var2, var3);
         case 5:
            return this.isrecursive$5(var2, var3);
         case 6:
            return this._sorted$6(var2, var3);
         case 7:
            return this.PrettyPrinter$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.pprint$9(var2, var3);
         case 10:
            return this.pformat$10(var2, var3);
         case 11:
            return this.isrecursive$11(var2, var3);
         case 12:
            return this.isreadable$12(var2, var3);
         case 13:
            return this._format$13(var2, var3);
         case 14:
            return this._repr$14(var2, var3);
         case 15:
            return this.format$15(var2, var3);
         case 16:
            return this._safe_repr$16(var2, var3);
         case 17:
            return this._recursion$17(var2, var3);
         case 18:
            return this._perfcheck$18(var2, var3);
         default:
            return null;
      }
   }
}

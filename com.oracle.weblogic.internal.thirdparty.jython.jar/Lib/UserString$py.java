import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("UserString.py")
public class UserString$py extends PyFunctionTable implements PyRunnable {
   static UserString$py self;
   static final PyCode f$0;
   static final PyCode UserString$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode __repr__$4;
   static final PyCode __int__$5;
   static final PyCode __long__$6;
   static final PyCode __float__$7;
   static final PyCode __complex__$8;
   static final PyCode __hash__$9;
   static final PyCode __cmp__$10;
   static final PyCode __contains__$11;
   static final PyCode __len__$12;
   static final PyCode __getitem__$13;
   static final PyCode __getslice__$14;
   static final PyCode __add__$15;
   static final PyCode __radd__$16;
   static final PyCode __mul__$17;
   static final PyCode __mod__$18;
   static final PyCode capitalize$19;
   static final PyCode center$20;
   static final PyCode count$21;
   static final PyCode decode$22;
   static final PyCode encode$23;
   static final PyCode endswith$24;
   static final PyCode expandtabs$25;
   static final PyCode find$26;
   static final PyCode index$27;
   static final PyCode isalpha$28;
   static final PyCode isalnum$29;
   static final PyCode isdecimal$30;
   static final PyCode isdigit$31;
   static final PyCode islower$32;
   static final PyCode isnumeric$33;
   static final PyCode isspace$34;
   static final PyCode istitle$35;
   static final PyCode isupper$36;
   static final PyCode join$37;
   static final PyCode ljust$38;
   static final PyCode lower$39;
   static final PyCode lstrip$40;
   static final PyCode partition$41;
   static final PyCode replace$42;
   static final PyCode rfind$43;
   static final PyCode rindex$44;
   static final PyCode rjust$45;
   static final PyCode rpartition$46;
   static final PyCode rstrip$47;
   static final PyCode split$48;
   static final PyCode rsplit$49;
   static final PyCode splitlines$50;
   static final PyCode startswith$51;
   static final PyCode strip$52;
   static final PyCode swapcase$53;
   static final PyCode title$54;
   static final PyCode translate$55;
   static final PyCode upper$56;
   static final PyCode zfill$57;
   static final PyCode MutableString$58;
   static final PyCode __init__$59;
   static final PyCode __setitem__$60;
   static final PyCode __delitem__$61;
   static final PyCode __setslice__$62;
   static final PyCode __delslice__$63;
   static final PyCode immutable$64;
   static final PyCode __iadd__$65;
   static final PyCode __imul__$66;
   static final PyCode insert$67;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A user-defined wrapper around string objects\n\nNote: string objects have grown methods in Python 1.6\nThis module requires Python 1.6 or later.\n"));
      var1.setline(7);
      PyString.fromInterned("A user-defined wrapper around string objects\n\nNote: string objects have grown methods in Python 1.6\nThis module requires Python 1.6 or later.\n");
      var1.setline(8);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("collections", var1, -1);
      var1.setlocal("collections", var3);
      var3 = null;
      var1.setline(11);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("UserString"), PyString.fromInterned("MutableString")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(13);
      PyObject[] var7 = new PyObject[]{var1.getname("collections").__getattr__("Sequence")};
      PyObject var4 = Py.makeClass("UserString", var7, UserString$1);
      var1.setlocal("UserString", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(133);
      var7 = new PyObject[]{var1.getname("UserString"), var1.getname("collections").__getattr__("MutableSequence")};
      var4 = Py.makeClass("MutableString", var7, MutableString$58);
      var1.setlocal("MutableString", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(220);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(222);
         var3 = imp.importOne("os", var1, -1);
         var1.setlocal("os", var3);
         var3 = null;
         var1.setline(223);
         var3 = var1.getname("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         PyObject[] var8 = Py.unpackSequence(var3, 2);
         PyObject var5 = var8[0];
         var1.setlocal("called_in_dir", var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal("called_as", var5);
         var5 = null;
         var3 = null;
         var1.setline(224);
         var3 = var1.getname("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getname("called_as"));
         var8 = Py.unpackSequence(var3, 2);
         var5 = var8[0];
         var1.setlocal("called_as", var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal("py", var5);
         var5 = null;
         var3 = null;
         var1.setline(225);
         PyString var9 = PyString.fromInterned("-q");
         var10000 = var9._in(var1.getname("sys").__getattr__("argv"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(226);
            String[] var10 = new String[]{"test_support"};
            var7 = imp.importFrom("test", var10, var1, -1);
            var4 = var7[0];
            var1.setlocal("test_support", var4);
            var4 = null;
            var1.setline(227);
            PyInteger var11 = Py.newInteger(0);
            var1.getname("test_support").__setattr__((String)"verbose", var11);
            var3 = null;
         }

         var1.setline(228);
         var1.getname("__import__").__call__(var2, PyString.fromInterned("test.test_")._add(var1.getname("called_as").__getattr__("lower").__call__(var2)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UserString$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$4, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __int__$5, (PyObject)null);
      var1.setlocal("__int__", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __long__$6, (PyObject)null);
      var1.setlocal("__long__", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __float__$7, (PyObject)null);
      var1.setlocal("__float__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __complex__$8, (PyObject)null);
      var1.setlocal("__complex__", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$9, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$10, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$11, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$12, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(38);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$13, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getslice__$14, (PyObject)null);
      var1.setlocal("__getslice__", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$15, (PyObject)null);
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __radd__$16, (PyObject)null);
      var1.setlocal("__radd__", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __mul__$17, (PyObject)null);
      var1.setlocal("__mul__", var4);
      var3 = null;
      var1.setline(57);
      PyObject var5 = var1.getname("__mul__");
      var1.setlocal("__rmul__", var5);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __mod__$18, (PyObject)null);
      var1.setlocal("__mod__", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, capitalize$19, (PyObject)null);
      var1.setlocal("capitalize", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, center$20, (PyObject)null);
      var1.setlocal("center", var4);
      var3 = null;
      var1.setline(65);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, count$21, (PyObject)null);
      var1.setlocal("count", var4);
      var3 = null;
      var1.setline(67);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, decode$22, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(75);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, encode$23, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(83);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, endswith$24, (PyObject)null);
      var1.setlocal("endswith", var4);
      var3 = null;
      var1.setline(85);
      var3 = new PyObject[]{Py.newInteger(8)};
      var4 = new PyFunction(var1.f_globals, var3, expandtabs$25, (PyObject)null);
      var1.setlocal("expandtabs", var4);
      var3 = null;
      var1.setline(87);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, find$26, (PyObject)null);
      var1.setlocal("find", var4);
      var3 = null;
      var1.setline(89);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, index$27, (PyObject)null);
      var1.setlocal("index", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isalpha$28, (PyObject)null);
      var1.setlocal("isalpha", var4);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isalnum$29, (PyObject)null);
      var1.setlocal("isalnum", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isdecimal$30, (PyObject)null);
      var1.setlocal("isdecimal", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isdigit$31, (PyObject)null);
      var1.setlocal("isdigit", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, islower$32, (PyObject)null);
      var1.setlocal("islower", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isnumeric$33, (PyObject)null);
      var1.setlocal("isnumeric", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isspace$34, (PyObject)null);
      var1.setlocal("isspace", var4);
      var3 = null;
      var1.setline(98);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, istitle$35, (PyObject)null);
      var1.setlocal("istitle", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isupper$36, (PyObject)null);
      var1.setlocal("isupper", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, join$37, (PyObject)null);
      var1.setlocal("join", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ljust$38, (PyObject)null);
      var1.setlocal("ljust", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lower$39, (PyObject)null);
      var1.setlocal("lower", var4);
      var3 = null;
      var1.setline(104);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, lstrip$40, (PyObject)null);
      var1.setlocal("lstrip", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, partition$41, (PyObject)null);
      var1.setlocal("partition", var4);
      var3 = null;
      var1.setline(107);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, replace$42, (PyObject)null);
      var1.setlocal("replace", var4);
      var3 = null;
      var1.setline(109);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, rfind$43, (PyObject)null);
      var1.setlocal("rfind", var4);
      var3 = null;
      var1.setline(111);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, rindex$44, (PyObject)null);
      var1.setlocal("rindex", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rjust$45, (PyObject)null);
      var1.setlocal("rjust", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rpartition$46, (PyObject)null);
      var1.setlocal("rpartition", var4);
      var3 = null;
      var1.setline(117);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, rstrip$47, (PyObject)null);
      var1.setlocal("rstrip", var4);
      var3 = null;
      var1.setline(118);
      var3 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, split$48, (PyObject)null);
      var1.setlocal("split", var4);
      var3 = null;
      var1.setline(120);
      var3 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, rsplit$49, (PyObject)null);
      var1.setlocal("rsplit", var4);
      var3 = null;
      var1.setline(122);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, splitlines$50, (PyObject)null);
      var1.setlocal("splitlines", var4);
      var3 = null;
      var1.setline(123);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("maxint")};
      var4 = new PyFunction(var1.f_globals, var3, startswith$51, (PyObject)null);
      var1.setlocal("startswith", var4);
      var3 = null;
      var1.setline(125);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, strip$52, (PyObject)null);
      var1.setlocal("strip", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, swapcase$53, (PyObject)null);
      var1.setlocal("swapcase", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, title$54, (PyObject)null);
      var1.setlocal("title", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, translate$55, (PyObject)null);
      var1.setlocal("translate", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, upper$56, (PyObject)null);
      var1.setlocal("upper", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, zfill$57, (PyObject)null);
      var1.setlocal("zfill", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(16);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      } else {
         var1.setline(17);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserString")).__nonzero__()) {
            var1.setline(18);
            var3 = var1.getlocal(1).__getattr__("data").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("data", var3);
            var3 = null;
         } else {
            var1.setline(20);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("data", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$4(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __int__$5(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __long__$6(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("long").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __float__$7(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("float").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __complex__$8(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("complex").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$9(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$10(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserString")).__nonzero__()) {
         var1.setline(31);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1).__getattr__("data"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(33);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __contains__$11(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$12(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$13(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getslice__$14(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __add__$15(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserString")).__nonzero__()) {
         var1.setline(45);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1).__getattr__("data")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(46);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(47);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(49);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getglobal("str").__call__(var2, var1.getlocal(1))));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __radd__$16(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(52);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getattr__("data")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(54);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))._add(var1.getlocal(0).__getattr__("data")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __mul__$17(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._mul(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __mod__$18(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject capitalize$19(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("capitalize").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject center$20(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10002 = var1.getlocal(0).__getattr__("data").__getattr__("center");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject count$21(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("count").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$22(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(69);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(70);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("decode").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(72);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("decode").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(74);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("decode").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject encode$23(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(77);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(78);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(80);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("encode").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(82);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("encode").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject endswith$24(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("endswith").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expandtabs$25(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("expandtabs").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find$26(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("find").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject index$27(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("index").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isalpha$28(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isalpha").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isalnum$29(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isalnum").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdecimal$30(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isdecimal").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdigit$31(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isdigit").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject islower$32(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("islower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isnumeric$33(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isnumeric").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isspace$34(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isspace").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject istitle$35(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("istitle").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isupper$36(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("isupper").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject join$37(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ljust$38(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10002 = var1.getlocal(0).__getattr__("data").__getattr__("ljust");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject lower$39(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lstrip$40(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("lstrip").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject partition$41(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("partition").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject replace$42(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("replace").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rfind$43(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("rfind").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rindex$44(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("rindex").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rjust$45(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10002 = var1.getlocal(0).__getattr__("data").__getattr__("rjust");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rpartition$46(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("rpartition").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rstrip$47(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("rstrip").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject split$48(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("split").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rsplit$49(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("rsplit").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject splitlines$50(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("splitlines").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startswith$51(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("startswith").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject strip$52(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("strip").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject swapcase$53(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("swapcase").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject title$54(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("title").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject translate$55(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var10000 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10002 = var1.getlocal(0).__getattr__("data").__getattr__("translate");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject upper$56(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject zfill$57(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("zfill").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MutableString$58(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("mutable string objects\n\n    Python strings are immutable objects.  This has the advantage, that\n    strings may be used as dictionary keys.  If this property isn't needed\n    and you insist on changing string values in place instead, you may cheat\n    and use MutableString.\n\n    But the purpose of this class is an educational one: to prevent\n    people from inventing their own mutable string class derived\n    from UserString and than forget thereby to remove (override) the\n    __hash__ method inherited from UserString.  This would lead to\n    errors that would be very hard to track down.\n\n    A faster and better solution is to rewrite your program using lists."));
      var1.setline(147);
      PyString.fromInterned("mutable string objects\n\n    Python strings are immutable objects.  This has the advantage, that\n    strings may be used as dictionary keys.  If this property isn't needed\n    and you insist on changing string values in place instead, you may cheat\n    and use MutableString.\n\n    But the purpose of this class is an educational one: to prevent\n    people from inventing their own mutable string class derived\n    from UserString and than forget thereby to remove (override) the\n    __hash__ method inherited from UserString.  This would lead to\n    errors that would be very hard to track down.\n\n    A faster and better solution is to rewrite your program using lists.");
      var1.setline(148);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$59, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(155);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$60, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$61, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setslice__$62, (PyObject)null);
      var1.setlocal("__setslice__", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delslice__$63, (PyObject)null);
      var1.setlocal("__delslice__", var4);
      var3 = null;
      var1.setline(204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, immutable$64, (PyObject)null);
      var1.setlocal("immutable", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iadd__$65, (PyObject)null);
      var1.setlocal("__iadd__", var4);
      var3 = null;
      var1.setline(214);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __imul__$66, (PyObject)null);
      var1.setlocal("__imul__", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insert$67, (PyObject)null);
      var1.setlocal("insert", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$59(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(150);
      PyObject var10000 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("the class UserString.MutableString has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(152);
      PyObject var6 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$60(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("slice")).__nonzero__()) {
         var1.setline(159);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("UserString")).__nonzero__()) {
            var1.setline(160);
            var3 = var1.getlocal(2).__getattr__("data");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(161);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__not__().__nonzero__()) {
               var1.setline(162);
               var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(163);
         var3 = var1.getlocal(1).__getattr__("indices").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
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
         var1.setline(164);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(165);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(3)._add(Py.newInteger(1))});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
            var1.setline(166);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(167);
            var3 = var1.getlocal(5);
            var10000 = var3._ne(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(170);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("invalid step in slicing assignment"));
            }
         }

         var1.setline(171);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(172);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null)._add(var1.getlocal(2))._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      } else {
         var1.setline(174);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(175);
            var3 = var1.getlocal(1);
            var3 = var3._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var1.setlocal(1, var3);
         }

         var1.setline(176);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(176);
            throw Py.makeException(var1.getglobal("IndexError"));
         }

         var1.setline(177);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(2))._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$61(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("slice")).__nonzero__()) {
         var1.setline(180);
         var3 = var1.getlocal(1).__getattr__("indices").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
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
         var1.setline(181);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(182);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3)._add(Py.newInteger(1)), var1.getlocal(2)._add(Py.newInteger(1))});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(183);
            var3 = var1.getlocal(4);
            var10000 = var3._ne(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(185);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("invalid step in slicing deletion"));
            }
         }

         var1.setline(186);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(187);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      } else {
         var1.setline(189);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var3 = var1.getlocal(1);
            var3 = var3._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var1.setlocal(1, var3);
         }

         var1.setline(191);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("IndexError"));
         }

         var1.setline(192);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setslice__$62(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(195);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("UserString")).__nonzero__()) {
         var1.setline(196);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(3).__getattr__("data"))._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
      } else {
         var1.setline(197);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(198);
            var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(3))._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
            var1.getlocal(0).__setattr__("data", var3);
            var3 = null;
         } else {
            var1.setline(200);
            var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getglobal("str").__call__(var2, var1.getlocal(3)))._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
            var1.getlocal(0).__setattr__("data", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delslice__$63(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(203);
      var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject immutable$64(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyObject var3 = var1.getglobal("UserString").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iadd__$65(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyObject var10000;
      String var3;
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserString")).__nonzero__()) {
         var1.setline(208);
         var10000 = var1.getlocal(0);
         var3 = "data";
         var4 = var10000;
         var5 = var4.__getattr__(var3);
         var5 = var5._iadd(var1.getlocal(1).__getattr__("data"));
         var4.__setattr__(var3, var5);
      } else {
         var1.setline(209);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(210);
            var10000 = var1.getlocal(0);
            var3 = "data";
            var4 = var10000;
            var5 = var4.__getattr__(var3);
            var5 = var5._iadd(var1.getlocal(1));
            var4.__setattr__(var3, var5);
         } else {
            var1.setline(212);
            var10000 = var1.getlocal(0);
            var3 = "data";
            var4 = var10000;
            var5 = var4.__getattr__(var3);
            var5 = var5._iadd(var1.getglobal("str").__call__(var2, var1.getlocal(1)));
            var4.__setattr__(var3, var5);
         }
      }

      var1.setline(213);
      PyObject var6 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject __imul__$66(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "data";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._imul(var1.getlocal(1));
      var4.__setattr__(var3, var5);
      var1.setline(216);
      PyObject var6 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject insert$67(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setslice__(var1.getlocal(1), var1.getlocal(1), (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public UserString$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UserString$1 = Py.newCode(0, var2, var1, "UserString", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "seq"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$4 = Py.newCode(1, var2, var1, "__repr__", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __int__$5 = Py.newCode(1, var2, var1, "__int__", 23, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __long__$6 = Py.newCode(1, var2, var1, "__long__", 24, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __float__$7 = Py.newCode(1, var2, var1, "__float__", 25, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __complex__$8 = Py.newCode(1, var2, var1, "__complex__", 26, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$9 = Py.newCode(1, var2, var1, "__hash__", 27, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string"};
      __cmp__$10 = Py.newCode(2, var2, var1, "__cmp__", 29, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char"};
      __contains__$11 = Py.newCode(2, var2, var1, "__contains__", 34, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$12 = Py.newCode(1, var2, var1, "__len__", 37, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$13 = Py.newCode(2, var2, var1, "__getitem__", 38, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "end"};
      __getslice__$14 = Py.newCode(3, var2, var1, "__getslice__", 39, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __add__$15 = Py.newCode(2, var2, var1, "__add__", 43, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __radd__$16 = Py.newCode(2, var2, var1, "__radd__", 50, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      __mul__$17 = Py.newCode(2, var2, var1, "__mul__", 55, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __mod__$18 = Py.newCode(2, var2, var1, "__mod__", 58, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      capitalize$19 = Py.newCode(1, var2, var1, "capitalize", 62, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "width", "args"};
      center$20 = Py.newCode(3, var2, var1, "center", 63, true, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sub", "start", "end"};
      count$21 = Py.newCode(4, var2, var1, "count", 65, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "errors"};
      decode$22 = Py.newCode(3, var2, var1, "decode", 67, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "errors"};
      encode$23 = Py.newCode(3, var2, var1, "encode", 75, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suffix", "start", "end"};
      endswith$24 = Py.newCode(4, var2, var1, "endswith", 83, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tabsize"};
      expandtabs$25 = Py.newCode(2, var2, var1, "expandtabs", 85, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sub", "start", "end"};
      find$26 = Py.newCode(4, var2, var1, "find", 87, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sub", "start", "end"};
      index$27 = Py.newCode(4, var2, var1, "index", 89, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isalpha$28 = Py.newCode(1, var2, var1, "isalpha", 91, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isalnum$29 = Py.newCode(1, var2, var1, "isalnum", 92, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isdecimal$30 = Py.newCode(1, var2, var1, "isdecimal", 93, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isdigit$31 = Py.newCode(1, var2, var1, "isdigit", 94, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      islower$32 = Py.newCode(1, var2, var1, "islower", 95, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isnumeric$33 = Py.newCode(1, var2, var1, "isnumeric", 96, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isspace$34 = Py.newCode(1, var2, var1, "isspace", 97, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      istitle$35 = Py.newCode(1, var2, var1, "istitle", 98, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isupper$36 = Py.newCode(1, var2, var1, "isupper", 99, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq"};
      join$37 = Py.newCode(2, var2, var1, "join", 100, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "width", "args"};
      ljust$38 = Py.newCode(3, var2, var1, "ljust", 101, true, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lower$39 = Py.newCode(1, var2, var1, "lower", 103, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      lstrip$40 = Py.newCode(2, var2, var1, "lstrip", 104, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep"};
      partition$41 = Py.newCode(2, var2, var1, "partition", 105, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old", "new", "maxsplit"};
      replace$42 = Py.newCode(4, var2, var1, "replace", 107, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sub", "start", "end"};
      rfind$43 = Py.newCode(4, var2, var1, "rfind", 109, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sub", "start", "end"};
      rindex$44 = Py.newCode(4, var2, var1, "rindex", 111, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "width", "args"};
      rjust$45 = Py.newCode(3, var2, var1, "rjust", 113, true, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep"};
      rpartition$46 = Py.newCode(2, var2, var1, "rpartition", 115, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      rstrip$47 = Py.newCode(2, var2, var1, "rstrip", 117, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep", "maxsplit"};
      split$48 = Py.newCode(3, var2, var1, "split", 118, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep", "maxsplit"};
      rsplit$49 = Py.newCode(3, var2, var1, "rsplit", 120, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "keepends"};
      splitlines$50 = Py.newCode(2, var2, var1, "splitlines", 122, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "start", "end"};
      startswith$51 = Py.newCode(4, var2, var1, "startswith", 123, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      strip$52 = Py.newCode(2, var2, var1, "strip", 125, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      swapcase$53 = Py.newCode(1, var2, var1, "swapcase", 126, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      title$54 = Py.newCode(1, var2, var1, "title", 127, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      translate$55 = Py.newCode(2, var2, var1, "translate", 128, true, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      upper$56 = Py.newCode(1, var2, var1, "upper", 130, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "width"};
      zfill$57 = Py.newCode(2, var2, var1, "zfill", 131, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MutableString$58 = Py.newCode(0, var2, var1, "MutableString", 133, false, false, self, 58, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "string", "warnpy3k"};
      __init__$59 = Py.newCode(2, var2, var1, "__init__", 148, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "sub", "start", "stop", "step"};
      __setitem__$60 = Py.newCode(3, var2, var1, "__setitem__", 157, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "start", "stop", "step"};
      __delitem__$61 = Py.newCode(2, var2, var1, "__delitem__", 178, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "end", "sub"};
      __setslice__$62 = Py.newCode(4, var2, var1, "__setslice__", 193, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "end"};
      __delslice__$63 = Py.newCode(3, var2, var1, "__delslice__", 201, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      immutable$64 = Py.newCode(1, var2, var1, "immutable", 204, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __iadd__$65 = Py.newCode(2, var2, var1, "__iadd__", 206, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      __imul__$66 = Py.newCode(2, var2, var1, "__imul__", 214, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "value"};
      insert$67 = Py.newCode(3, var2, var1, "insert", 217, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new UserString$py("UserString$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(UserString$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UserString$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.__repr__$4(var2, var3);
         case 5:
            return this.__int__$5(var2, var3);
         case 6:
            return this.__long__$6(var2, var3);
         case 7:
            return this.__float__$7(var2, var3);
         case 8:
            return this.__complex__$8(var2, var3);
         case 9:
            return this.__hash__$9(var2, var3);
         case 10:
            return this.__cmp__$10(var2, var3);
         case 11:
            return this.__contains__$11(var2, var3);
         case 12:
            return this.__len__$12(var2, var3);
         case 13:
            return this.__getitem__$13(var2, var3);
         case 14:
            return this.__getslice__$14(var2, var3);
         case 15:
            return this.__add__$15(var2, var3);
         case 16:
            return this.__radd__$16(var2, var3);
         case 17:
            return this.__mul__$17(var2, var3);
         case 18:
            return this.__mod__$18(var2, var3);
         case 19:
            return this.capitalize$19(var2, var3);
         case 20:
            return this.center$20(var2, var3);
         case 21:
            return this.count$21(var2, var3);
         case 22:
            return this.decode$22(var2, var3);
         case 23:
            return this.encode$23(var2, var3);
         case 24:
            return this.endswith$24(var2, var3);
         case 25:
            return this.expandtabs$25(var2, var3);
         case 26:
            return this.find$26(var2, var3);
         case 27:
            return this.index$27(var2, var3);
         case 28:
            return this.isalpha$28(var2, var3);
         case 29:
            return this.isalnum$29(var2, var3);
         case 30:
            return this.isdecimal$30(var2, var3);
         case 31:
            return this.isdigit$31(var2, var3);
         case 32:
            return this.islower$32(var2, var3);
         case 33:
            return this.isnumeric$33(var2, var3);
         case 34:
            return this.isspace$34(var2, var3);
         case 35:
            return this.istitle$35(var2, var3);
         case 36:
            return this.isupper$36(var2, var3);
         case 37:
            return this.join$37(var2, var3);
         case 38:
            return this.ljust$38(var2, var3);
         case 39:
            return this.lower$39(var2, var3);
         case 40:
            return this.lstrip$40(var2, var3);
         case 41:
            return this.partition$41(var2, var3);
         case 42:
            return this.replace$42(var2, var3);
         case 43:
            return this.rfind$43(var2, var3);
         case 44:
            return this.rindex$44(var2, var3);
         case 45:
            return this.rjust$45(var2, var3);
         case 46:
            return this.rpartition$46(var2, var3);
         case 47:
            return this.rstrip$47(var2, var3);
         case 48:
            return this.split$48(var2, var3);
         case 49:
            return this.rsplit$49(var2, var3);
         case 50:
            return this.splitlines$50(var2, var3);
         case 51:
            return this.startswith$51(var2, var3);
         case 52:
            return this.strip$52(var2, var3);
         case 53:
            return this.swapcase$53(var2, var3);
         case 54:
            return this.title$54(var2, var3);
         case 55:
            return this.translate$55(var2, var3);
         case 56:
            return this.upper$56(var2, var3);
         case 57:
            return this.zfill$57(var2, var3);
         case 58:
            return this.MutableString$58(var2, var3);
         case 59:
            return this.__init__$59(var2, var3);
         case 60:
            return this.__setitem__$60(var2, var3);
         case 61:
            return this.__delitem__$61(var2, var3);
         case 62:
            return this.__setslice__$62(var2, var3);
         case 63:
            return this.__delslice__$63(var2, var3);
         case 64:
            return this.immutable$64(var2, var3);
         case 65:
            return this.__iadd__$65(var2, var3);
         case 66:
            return this.__imul__$66(var2, var3);
         case 67:
            return this.insert$67(var2, var3);
         default:
            return null;
      }
   }
}

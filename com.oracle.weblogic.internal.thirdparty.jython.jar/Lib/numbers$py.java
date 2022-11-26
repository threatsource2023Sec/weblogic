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
@Filename("numbers.py")
public class numbers$py extends PyFunctionTable implements PyRunnable {
   static numbers$py self;
   static final PyCode f$0;
   static final PyCode Number$1;
   static final PyCode Complex$2;
   static final PyCode __complex__$3;
   static final PyCode __nonzero__$4;
   static final PyCode real$5;
   static final PyCode imag$6;
   static final PyCode __add__$7;
   static final PyCode __radd__$8;
   static final PyCode __neg__$9;
   static final PyCode __pos__$10;
   static final PyCode __sub__$11;
   static final PyCode __rsub__$12;
   static final PyCode __mul__$13;
   static final PyCode __rmul__$14;
   static final PyCode __div__$15;
   static final PyCode __rdiv__$16;
   static final PyCode __truediv__$17;
   static final PyCode __rtruediv__$18;
   static final PyCode __pow__$19;
   static final PyCode __rpow__$20;
   static final PyCode __abs__$21;
   static final PyCode conjugate$22;
   static final PyCode __eq__$23;
   static final PyCode __ne__$24;
   static final PyCode Real$25;
   static final PyCode __float__$26;
   static final PyCode __trunc__$27;
   static final PyCode __divmod__$28;
   static final PyCode __rdivmod__$29;
   static final PyCode __floordiv__$30;
   static final PyCode __rfloordiv__$31;
   static final PyCode __mod__$32;
   static final PyCode __rmod__$33;
   static final PyCode __lt__$34;
   static final PyCode __le__$35;
   static final PyCode __complex__$36;
   static final PyCode real$37;
   static final PyCode imag$38;
   static final PyCode conjugate$39;
   static final PyCode Rational$40;
   static final PyCode numerator$41;
   static final PyCode denominator$42;
   static final PyCode __float__$43;
   static final PyCode Integral$44;
   static final PyCode __long__$45;
   static final PyCode __index__$46;
   static final PyCode __pow__$47;
   static final PyCode __lshift__$48;
   static final PyCode __rlshift__$49;
   static final PyCode __rshift__$50;
   static final PyCode __rrshift__$51;
   static final PyCode __and__$52;
   static final PyCode __rand__$53;
   static final PyCode __xor__$54;
   static final PyCode __rxor__$55;
   static final PyCode __or__$56;
   static final PyCode __ror__$57;
   static final PyCode __invert__$58;
   static final PyCode __float__$59;
   static final PyCode numerator$60;
   static final PyCode denominator$61;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Abstract Base Classes (ABCs) for numbers, according to PEP 3141.\n\nTODO: Fill out more detailed documentation on the operators."));
      var1.setline(6);
      PyString.fromInterned("Abstract Base Classes (ABCs) for numbers, according to PEP 3141.\n\nTODO: Fill out more detailed documentation on the operators.");
      var1.setline(8);
      String[] var3 = new String[]{"division"};
      PyObject[] var5 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("division", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"ABCMeta", "abstractmethod", "abstractproperty"};
      var5 = imp.importFrom("abc", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("ABCMeta", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("abstractmethod", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("abstractproperty", var4);
      var4 = null;
      var1.setline(11);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("Number"), PyString.fromInterned("Complex"), PyString.fromInterned("Real"), PyString.fromInterned("Rational"), PyString.fromInterned("Integral")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(13);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Number", var5, Number$1);
      var1.setlocal("Number", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(34);
      var5 = new PyObject[]{var1.getname("Number")};
      var4 = Py.makeClass("Complex", var5, Complex$2);
      var1.setlocal("Complex", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(166);
      var1.getname("Complex").__getattr__("register").__call__(var2, var1.getname("complex"));
      var1.setline(169);
      var5 = new PyObject[]{var1.getname("Complex")};
      var4 = Py.makeClass("Real", var5, Real$25);
      var1.setlocal("Real", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(267);
      var1.getname("Real").__getattr__("register").__call__(var2, var1.getname("float"));
      var1.setline(270);
      var5 = new PyObject[]{var1.getname("Real")};
      var4 = Py.makeClass("Rational", var5, Rational$40);
      var1.setlocal("Rational", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(295);
      var5 = new PyObject[]{var1.getname("Rational")};
      var4 = Py.makeClass("Integral", var5, Integral$44);
      var1.setlocal("Integral", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(390);
      var1.getname("Integral").__getattr__("register").__call__(var2, var1.getname("int"));
      var1.setline(391);
      var1.getname("Integral").__getattr__("register").__call__(var2, var1.getname("long"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Number$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("All numbers inherit from this class.\n\n    If you just want to check if an argument x is a number, without\n    caring what kind, use isinstance(x, Number).\n    "));
      var1.setline(18);
      PyString.fromInterned("All numbers inherit from this class.\n\n    If you just want to check if an argument x is a number, without\n    caring what kind, use isinstance(x, Number).\n    ");
      var1.setline(19);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(20);
      PyTuple var4 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var4);
      var3 = null;
      var1.setline(23);
      var3 = var1.getname("None");
      var1.setlocal("__hash__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Complex$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Complex defines the operations that work on the builtin complex type.\n\n    In short, those are: a conversion to complex, .real, .imag, +, -,\n    *, /, abs(), .conjugate, ==, and !=.\n\n    If it is given heterogenous arguments, and doesn't have special\n    knowledge about them, it should fall back to the builtin complex\n    type as described below.\n    "));
      var1.setline(43);
      PyString.fromInterned("Complex defines the operations that work on the builtin complex type.\n\n    In short, those are: a conversion to complex, .real, .imag, +, -,\n    *, /, abs(), .conjugate, ==, and !=.\n\n    If it is given heterogenous arguments, and doesn't have special\n    knowledge about them, it should fall back to the builtin complex\n    type as described below.\n    ");
      var1.setline(45);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(47);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __complex__$3, PyString.fromInterned("Return a builtin complex instance. Called for complex(self)."));
      PyObject var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__complex__", var6);
      var3 = null;
      var1.setline(52);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __nonzero__$4, PyString.fromInterned("True if self != 0. Called for bool(self)."));
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(56);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, real$5, PyString.fromInterned("Retrieve the real component of this number.\n\n        This should subclass Real.\n        "));
      var6 = var1.getname("abstractproperty").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("real", var6);
      var3 = null;
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, imag$6, PyString.fromInterned("Retrieve the imaginary component of this number.\n\n        This should subclass Real.\n        "));
      var6 = var1.getname("abstractproperty").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("imag", var6);
      var3 = null;
      var1.setline(72);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __add__$7, PyString.fromInterned("self + other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__add__", var6);
      var3 = null;
      var1.setline(77);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __radd__$8, PyString.fromInterned("other + self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__radd__", var6);
      var3 = null;
      var1.setline(82);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __neg__$9, PyString.fromInterned("-self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__neg__", var6);
      var3 = null;
      var1.setline(87);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __pos__$10, PyString.fromInterned("+self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__pos__", var6);
      var3 = null;
      var1.setline(92);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __sub__$11, PyString.fromInterned("self - other"));
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(96);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rsub__$12, PyString.fromInterned("other - self"));
      var1.setlocal("__rsub__", var5);
      var3 = null;
      var1.setline(100);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __mul__$13, PyString.fromInterned("self * other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__mul__", var6);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rmul__$14, PyString.fromInterned("other * self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rmul__", var6);
      var3 = null;
      var1.setline(110);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __div__$15, PyString.fromInterned("self / other without __future__ division\n\n        May promote to float.\n        "));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__div__", var6);
      var3 = null;
      var1.setline(118);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rdiv__$16, PyString.fromInterned("other / self without __future__ division"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rdiv__", var6);
      var3 = null;
      var1.setline(123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __truediv__$17, PyString.fromInterned("self / other with __future__ division.\n\n        Should promote to float when necessary.\n        "));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__truediv__", var6);
      var3 = null;
      var1.setline(131);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rtruediv__$18, PyString.fromInterned("other / self with __future__ division"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rtruediv__", var6);
      var3 = null;
      var1.setline(136);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __pow__$19, PyString.fromInterned("self**exponent; should promote to float or complex when necessary."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__pow__", var6);
      var3 = null;
      var1.setline(141);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rpow__$20, PyString.fromInterned("base ** self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rpow__", var6);
      var3 = null;
      var1.setline(146);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __abs__$21, PyString.fromInterned("Returns the Real distance from 0. Called for abs(self)."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__abs__", var6);
      var3 = null;
      var1.setline(151);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, conjugate$22, PyString.fromInterned("(x+y*i).conjugate() returns (x-y*i)."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("conjugate", var6);
      var3 = null;
      var1.setline(156);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$23, PyString.fromInterned("self == other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__eq__", var6);
      var3 = null;
      var1.setline(161);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$24, PyString.fromInterned("self != other"));
      var1.setlocal("__ne__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __complex__$3(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Return a builtin complex instance. Called for complex(self).");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __nonzero__$4(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("True if self != 0. Called for bool(self).");
      var1.setline(54);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject real$5(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("Retrieve the real component of this number.\n\n        This should subclass Real.\n        ");
      var1.setline(62);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject imag$6(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Retrieve the imaginary component of this number.\n\n        This should subclass Real.\n        ");
      var1.setline(70);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __add__$7(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("self + other");
      var1.setline(75);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __radd__$8(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyString.fromInterned("other + self");
      var1.setline(80);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __neg__$9(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("-self");
      var1.setline(85);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __pos__$10(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("+self");
      var1.setline(90);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __sub__$11(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("self - other");
      var1.setline(94);
      PyObject var3 = var1.getlocal(0)._add(var1.getlocal(1).__neg__());
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __rsub__$12(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("other - self");
      var1.setline(98);
      PyObject var3 = var1.getlocal(0).__neg__()._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __mul__$13(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyString.fromInterned("self * other");
      var1.setline(103);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rmul__$14(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("other * self");
      var1.setline(108);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __div__$15(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned("self / other without __future__ division\n\n        May promote to float.\n        ");
      var1.setline(116);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rdiv__$16(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("other / self without __future__ division");
      var1.setline(121);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __truediv__$17(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString.fromInterned("self / other with __future__ division.\n\n        Should promote to float when necessary.\n        ");
      var1.setline(129);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rtruediv__$18(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("other / self with __future__ division");
      var1.setline(134);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __pow__$19(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("self**exponent; should promote to float or complex when necessary.");
      var1.setline(139);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rpow__$20(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("base ** self");
      var1.setline(144);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __abs__$21(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Returns the Real distance from 0. Called for abs(self).");
      var1.setline(149);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject conjugate$22(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("(x+y*i).conjugate() returns (x-y*i).");
      var1.setline(154);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __eq__$23(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("self == other");
      var1.setline(159);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __ne__$24(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("self != other");
      var1.setline(164);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Real$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("To Complex, Real adds the operations that work on real numbers.\n\n    In short, those are: a conversion to float, trunc(), divmod,\n    %, <, <=, >, and >=.\n\n    Real also provides defaults for the derived operations.\n    "));
      var1.setline(176);
      PyString.fromInterned("To Complex, Real adds the operations that work on real numbers.\n\n    In short, those are: a conversion to float, trunc(), divmod,\n    %, <, <=, >, and >=.\n\n    Real also provides defaults for the derived operations.\n    ");
      var1.setline(178);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(180);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __float__$26, PyString.fromInterned("Any Real can be converted to a native float object.\n\n        Called for float(self)."));
      PyObject var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__float__", var6);
      var3 = null;
      var1.setline(187);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __trunc__$27, PyString.fromInterned("trunc(self): Truncates self to an Integral.\n\n        Returns an Integral i such that:\n          * i>0 iff self>0;\n          * abs(i) <= abs(self);\n          * for any Integral j satisfying the first two conditions,\n            abs(i) >= abs(j) [i.e. i has \"maximal\" abs among those].\n        i.e. \"truncate towards 0\".\n        "));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__trunc__", var6);
      var3 = null;
      var1.setline(200);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __divmod__$28, PyString.fromInterned("divmod(self, other): The pair (self // other, self % other).\n\n        Sometimes this can be computed faster than the pair of\n        operations.\n        "));
      var1.setlocal("__divmod__", var5);
      var3 = null;
      var1.setline(208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rdivmod__$29, PyString.fromInterned("divmod(other, self): The pair (self // other, self % other).\n\n        Sometimes this can be computed faster than the pair of\n        operations.\n        "));
      var1.setlocal("__rdivmod__", var5);
      var3 = null;
      var1.setline(216);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __floordiv__$30, PyString.fromInterned("self // other: The floor() of self/other."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__floordiv__", var6);
      var3 = null;
      var1.setline(221);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rfloordiv__$31, PyString.fromInterned("other // self: The floor() of other/self."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rfloordiv__", var6);
      var3 = null;
      var1.setline(226);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __mod__$32, PyString.fromInterned("self % other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__mod__", var6);
      var3 = null;
      var1.setline(231);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rmod__$33, PyString.fromInterned("other % self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rmod__", var6);
      var3 = null;
      var1.setline(236);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$34, PyString.fromInterned("self < other\n\n        < on Reals defines a total ordering, except perhaps for NaN."));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__lt__", var6);
      var3 = null;
      var1.setline(243);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __le__$35, PyString.fromInterned("self <= other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__le__", var6);
      var3 = null;
      var1.setline(249);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __complex__$36, PyString.fromInterned("complex(self) == complex(float(self), 0)"));
      var1.setlocal("__complex__", var5);
      var3 = null;
      var1.setline(253);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, real$37, PyString.fromInterned("Real numbers are their real component."));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("real", var6);
      var3 = null;
      var1.setline(258);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, imag$38, PyString.fromInterned("Real numbers have no imaginary component."));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("imag", var6);
      var3 = null;
      var1.setline(263);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, conjugate$39, PyString.fromInterned("Conjugate is a no-op for Reals."));
      var1.setlocal("conjugate", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __float__$26(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Any Real can be converted to a native float object.\n\n        Called for float(self).");
      var1.setline(185);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __trunc__$27(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("trunc(self): Truncates self to an Integral.\n\n        Returns an Integral i such that:\n          * i>0 iff self>0;\n          * abs(i) <= abs(self);\n          * for any Integral j satisfying the first two conditions,\n            abs(i) >= abs(j) [i.e. i has \"maximal\" abs among those].\n        i.e. \"truncate towards 0\".\n        ");
      var1.setline(198);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __divmod__$28(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned("divmod(self, other): The pair (self // other, self % other).\n\n        Sometimes this can be computed faster than the pair of\n        operations.\n        ");
      var1.setline(206);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0)._floordiv(var1.getlocal(1)), var1.getlocal(0)._mod(var1.getlocal(1))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __rdivmod__$29(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyString.fromInterned("divmod(other, self): The pair (self // other, self % other).\n\n        Sometimes this can be computed faster than the pair of\n        operations.\n        ");
      var1.setline(214);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1)._floordiv(var1.getlocal(0)), var1.getlocal(1)._mod(var1.getlocal(0))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __floordiv__$30(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyString.fromInterned("self // other: The floor() of self/other.");
      var1.setline(219);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rfloordiv__$31(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("other // self: The floor() of other/self.");
      var1.setline(224);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __mod__$32(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("self % other");
      var1.setline(229);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rmod__$33(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyString.fromInterned("other % self");
      var1.setline(234);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __lt__$34(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyString.fromInterned("self < other\n\n        < on Reals defines a total ordering, except perhaps for NaN.");
      var1.setline(241);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __le__$35(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyString.fromInterned("self <= other");
      var1.setline(246);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __complex__$36(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("complex(self) == complex(float(self), 0)");
      var1.setline(251);
      PyObject var3 = var1.getglobal("complex").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject real$37(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyString.fromInterned("Real numbers are their real component.");
      var1.setline(256);
      PyObject var3 = var1.getlocal(0).__pos__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject imag$38(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Real numbers have no imaginary component.");
      var1.setline(261);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject conjugate$39(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyString.fromInterned("Conjugate is a no-op for Reals.");
      var1.setline(265);
      PyObject var3 = var1.getlocal(0).__pos__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Rational$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(".numerator and .denominator should be in lowest terms."));
      var1.setline(271);
      PyString.fromInterned(".numerator and .denominator should be in lowest terms.");
      var1.setline(273);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(275);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, numerator$41, (PyObject)null);
      PyObject var6 = var1.getname("abstractproperty").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("numerator", var6);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, denominator$42, (PyObject)null);
      var6 = var1.getname("abstractproperty").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("denominator", var6);
      var3 = null;
      var1.setline(284);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __float__$43, PyString.fromInterned("float(self) = self.numerator / self.denominator\n\n        It's important that this conversion use the integer's \"true\"\n        division rather than casting one side to float before dividing\n        so that ratios of huge integers convert without overflowing.\n\n        "));
      var1.setlocal("__float__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject numerator$41(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject denominator$42(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __float__$43(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("float(self) = self.numerator / self.denominator\n\n        It's important that this conversion use the integer's \"true\"\n        division rather than casting one side to float before dividing\n        so that ratios of huge integers convert without overflowing.\n\n        ");
      var1.setline(292);
      PyObject var3 = var1.getlocal(0).__getattr__("numerator")._truediv(var1.getlocal(0).__getattr__("denominator"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Integral$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Integral adds a conversion to long and the bit-string operations."));
      var1.setline(296);
      PyString.fromInterned("Integral adds a conversion to long and the bit-string operations.");
      var1.setline(298);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(300);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __long__$45, PyString.fromInterned("long(self)"));
      PyObject var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__long__", var6);
      var3 = null;
      var1.setline(305);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __index__$46, PyString.fromInterned("Called whenever an index is needed, such as in slicing"));
      var1.setlocal("__index__", var5);
      var3 = null;
      var1.setline(309);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __pow__$47, PyString.fromInterned("self ** exponent % modulus, but maybe faster.\n\n        Accept the modulus argument if you want to support the\n        3-argument version of pow(). Raise a TypeError if exponent < 0\n        or any argument isn't Integral. Otherwise, just implement the\n        2-argument version described in Complex.\n        "));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__pow__", var6);
      var3 = null;
      var1.setline(320);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lshift__$48, PyString.fromInterned("self << other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__lshift__", var6);
      var3 = null;
      var1.setline(325);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rlshift__$49, PyString.fromInterned("other << self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rlshift__", var6);
      var3 = null;
      var1.setline(330);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rshift__$50, PyString.fromInterned("self >> other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rshift__", var6);
      var3 = null;
      var1.setline(335);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rrshift__$51, PyString.fromInterned("other >> self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rrshift__", var6);
      var3 = null;
      var1.setline(340);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __and__$52, PyString.fromInterned("self & other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__and__", var6);
      var3 = null;
      var1.setline(345);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rand__$53, PyString.fromInterned("other & self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rand__", var6);
      var3 = null;
      var1.setline(350);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __xor__$54, PyString.fromInterned("self ^ other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__xor__", var6);
      var3 = null;
      var1.setline(355);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rxor__$55, PyString.fromInterned("other ^ self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__rxor__", var6);
      var3 = null;
      var1.setline(360);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __or__$56, PyString.fromInterned("self | other"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__or__", var6);
      var3 = null;
      var1.setline(365);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ror__$57, PyString.fromInterned("other | self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__ror__", var6);
      var3 = null;
      var1.setline(370);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __invert__$58, PyString.fromInterned("~self"));
      var6 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__invert__", var6);
      var3 = null;
      var1.setline(376);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __float__$59, PyString.fromInterned("float(self) == float(long(self))"));
      var1.setlocal("__float__", var5);
      var3 = null;
      var1.setline(380);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, numerator$60, PyString.fromInterned("Integers are their own numerators."));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("numerator", var6);
      var3 = null;
      var1.setline(385);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, denominator$61, PyString.fromInterned("Integers have a denominator of 1."));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("denominator", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __long__$45(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyString.fromInterned("long(self)");
      var1.setline(303);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __index__$46(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Called whenever an index is needed, such as in slicing");
      var1.setline(307);
      PyObject var3 = var1.getglobal("long").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __pow__$47(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned("self ** exponent % modulus, but maybe faster.\n\n        Accept the modulus argument if you want to support the\n        3-argument version of pow(). Raise a TypeError if exponent < 0\n        or any argument isn't Integral. Otherwise, just implement the\n        2-argument version described in Complex.\n        ");
      var1.setline(318);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __lshift__$48(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("self << other");
      var1.setline(323);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rlshift__$49(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyString.fromInterned("other << self");
      var1.setline(328);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rshift__$50(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyString.fromInterned("self >> other");
      var1.setline(333);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rrshift__$51(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyString.fromInterned("other >> self");
      var1.setline(338);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __and__$52(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("self & other");
      var1.setline(343);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rand__$53(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyString.fromInterned("other & self");
      var1.setline(348);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __xor__$54(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned("self ^ other");
      var1.setline(353);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __rxor__$55(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      PyString.fromInterned("other ^ self");
      var1.setline(358);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __or__$56(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyString.fromInterned("self | other");
      var1.setline(363);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __ror__$57(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyString.fromInterned("other | self");
      var1.setline(368);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __invert__$58(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyString.fromInterned("~self");
      var1.setline(373);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject __float__$59(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyString.fromInterned("float(self) == float(long(self))");
      var1.setline(378);
      PyObject var3 = var1.getglobal("float").__call__(var2, var1.getglobal("long").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject numerator$60(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyString.fromInterned("Integers are their own numerators.");
      var1.setline(383);
      PyObject var3 = var1.getlocal(0).__pos__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject denominator$61(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyString.fromInterned("Integers have a denominator of 1.");
      var1.setline(388);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public numbers$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 12288);
      var2 = new String[0];
      Number$1 = Py.newCode(0, var2, var1, "Number", 13, false, false, self, 1, (String[])null, (String[])null, 0, 12288);
      var2 = new String[0];
      Complex$2 = Py.newCode(0, var2, var1, "Complex", 34, false, false, self, 2, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self"};
      __complex__$3 = Py.newCode(1, var2, var1, "__complex__", 47, false, false, self, 3, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __nonzero__$4 = Py.newCode(1, var2, var1, "__nonzero__", 52, false, false, self, 4, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      real$5 = Py.newCode(1, var2, var1, "real", 56, false, false, self, 5, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      imag$6 = Py.newCode(1, var2, var1, "imag", 64, false, false, self, 6, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __add__$7 = Py.newCode(2, var2, var1, "__add__", 72, false, false, self, 7, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __radd__$8 = Py.newCode(2, var2, var1, "__radd__", 77, false, false, self, 8, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __neg__$9 = Py.newCode(1, var2, var1, "__neg__", 82, false, false, self, 9, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __pos__$10 = Py.newCode(1, var2, var1, "__pos__", 87, false, false, self, 10, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __sub__$11 = Py.newCode(2, var2, var1, "__sub__", 92, false, false, self, 11, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rsub__$12 = Py.newCode(2, var2, var1, "__rsub__", 96, false, false, self, 12, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __mul__$13 = Py.newCode(2, var2, var1, "__mul__", 100, false, false, self, 13, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rmul__$14 = Py.newCode(2, var2, var1, "__rmul__", 105, false, false, self, 14, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __div__$15 = Py.newCode(2, var2, var1, "__div__", 110, false, false, self, 15, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rdiv__$16 = Py.newCode(2, var2, var1, "__rdiv__", 118, false, false, self, 16, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __truediv__$17 = Py.newCode(2, var2, var1, "__truediv__", 123, false, false, self, 17, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rtruediv__$18 = Py.newCode(2, var2, var1, "__rtruediv__", 131, false, false, self, 18, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "exponent"};
      __pow__$19 = Py.newCode(2, var2, var1, "__pow__", 136, false, false, self, 19, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "base"};
      __rpow__$20 = Py.newCode(2, var2, var1, "__rpow__", 141, false, false, self, 20, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __abs__$21 = Py.newCode(1, var2, var1, "__abs__", 146, false, false, self, 21, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      conjugate$22 = Py.newCode(1, var2, var1, "conjugate", 151, false, false, self, 22, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __eq__$23 = Py.newCode(2, var2, var1, "__eq__", 156, false, false, self, 23, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ne__$24 = Py.newCode(2, var2, var1, "__ne__", 161, false, false, self, 24, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      Real$25 = Py.newCode(0, var2, var1, "Real", 169, false, false, self, 25, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self"};
      __float__$26 = Py.newCode(1, var2, var1, "__float__", 180, false, false, self, 26, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __trunc__$27 = Py.newCode(1, var2, var1, "__trunc__", 187, false, false, self, 27, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __divmod__$28 = Py.newCode(2, var2, var1, "__divmod__", 200, false, false, self, 28, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rdivmod__$29 = Py.newCode(2, var2, var1, "__rdivmod__", 208, false, false, self, 29, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __floordiv__$30 = Py.newCode(2, var2, var1, "__floordiv__", 216, false, false, self, 30, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rfloordiv__$31 = Py.newCode(2, var2, var1, "__rfloordiv__", 221, false, false, self, 31, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __mod__$32 = Py.newCode(2, var2, var1, "__mod__", 226, false, false, self, 32, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rmod__$33 = Py.newCode(2, var2, var1, "__rmod__", 231, false, false, self, 33, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lt__$34 = Py.newCode(2, var2, var1, "__lt__", 236, false, false, self, 34, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __le__$35 = Py.newCode(2, var2, var1, "__le__", 243, false, false, self, 35, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __complex__$36 = Py.newCode(1, var2, var1, "__complex__", 249, false, false, self, 36, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      real$37 = Py.newCode(1, var2, var1, "real", 253, false, false, self, 37, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      imag$38 = Py.newCode(1, var2, var1, "imag", 258, false, false, self, 38, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      conjugate$39 = Py.newCode(1, var2, var1, "conjugate", 263, false, false, self, 39, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      Rational$40 = Py.newCode(0, var2, var1, "Rational", 270, false, false, self, 40, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self"};
      numerator$41 = Py.newCode(1, var2, var1, "numerator", 275, false, false, self, 41, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      denominator$42 = Py.newCode(1, var2, var1, "denominator", 279, false, false, self, 42, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __float__$43 = Py.newCode(1, var2, var1, "__float__", 284, false, false, self, 43, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      Integral$44 = Py.newCode(0, var2, var1, "Integral", 295, false, false, self, 44, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self"};
      __long__$45 = Py.newCode(1, var2, var1, "__long__", 300, false, false, self, 45, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __index__$46 = Py.newCode(1, var2, var1, "__index__", 305, false, false, self, 46, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "exponent", "modulus"};
      __pow__$47 = Py.newCode(3, var2, var1, "__pow__", 309, false, false, self, 47, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lshift__$48 = Py.newCode(2, var2, var1, "__lshift__", 320, false, false, self, 48, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rlshift__$49 = Py.newCode(2, var2, var1, "__rlshift__", 325, false, false, self, 49, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rshift__$50 = Py.newCode(2, var2, var1, "__rshift__", 330, false, false, self, 50, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rrshift__$51 = Py.newCode(2, var2, var1, "__rrshift__", 335, false, false, self, 51, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __and__$52 = Py.newCode(2, var2, var1, "__and__", 340, false, false, self, 52, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rand__$53 = Py.newCode(2, var2, var1, "__rand__", 345, false, false, self, 53, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __xor__$54 = Py.newCode(2, var2, var1, "__xor__", 350, false, false, self, 54, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rxor__$55 = Py.newCode(2, var2, var1, "__rxor__", 355, false, false, self, 55, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __or__$56 = Py.newCode(2, var2, var1, "__or__", 360, false, false, self, 56, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ror__$57 = Py.newCode(2, var2, var1, "__ror__", 365, false, false, self, 57, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __invert__$58 = Py.newCode(1, var2, var1, "__invert__", 370, false, false, self, 58, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __float__$59 = Py.newCode(1, var2, var1, "__float__", 376, false, false, self, 59, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      numerator$60 = Py.newCode(1, var2, var1, "numerator", 380, false, false, self, 60, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      denominator$61 = Py.newCode(1, var2, var1, "denominator", 385, false, false, self, 61, (String[])null, (String[])null, 0, 12289);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new numbers$py("numbers$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(numbers$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Number$1(var2, var3);
         case 2:
            return this.Complex$2(var2, var3);
         case 3:
            return this.__complex__$3(var2, var3);
         case 4:
            return this.__nonzero__$4(var2, var3);
         case 5:
            return this.real$5(var2, var3);
         case 6:
            return this.imag$6(var2, var3);
         case 7:
            return this.__add__$7(var2, var3);
         case 8:
            return this.__radd__$8(var2, var3);
         case 9:
            return this.__neg__$9(var2, var3);
         case 10:
            return this.__pos__$10(var2, var3);
         case 11:
            return this.__sub__$11(var2, var3);
         case 12:
            return this.__rsub__$12(var2, var3);
         case 13:
            return this.__mul__$13(var2, var3);
         case 14:
            return this.__rmul__$14(var2, var3);
         case 15:
            return this.__div__$15(var2, var3);
         case 16:
            return this.__rdiv__$16(var2, var3);
         case 17:
            return this.__truediv__$17(var2, var3);
         case 18:
            return this.__rtruediv__$18(var2, var3);
         case 19:
            return this.__pow__$19(var2, var3);
         case 20:
            return this.__rpow__$20(var2, var3);
         case 21:
            return this.__abs__$21(var2, var3);
         case 22:
            return this.conjugate$22(var2, var3);
         case 23:
            return this.__eq__$23(var2, var3);
         case 24:
            return this.__ne__$24(var2, var3);
         case 25:
            return this.Real$25(var2, var3);
         case 26:
            return this.__float__$26(var2, var3);
         case 27:
            return this.__trunc__$27(var2, var3);
         case 28:
            return this.__divmod__$28(var2, var3);
         case 29:
            return this.__rdivmod__$29(var2, var3);
         case 30:
            return this.__floordiv__$30(var2, var3);
         case 31:
            return this.__rfloordiv__$31(var2, var3);
         case 32:
            return this.__mod__$32(var2, var3);
         case 33:
            return this.__rmod__$33(var2, var3);
         case 34:
            return this.__lt__$34(var2, var3);
         case 35:
            return this.__le__$35(var2, var3);
         case 36:
            return this.__complex__$36(var2, var3);
         case 37:
            return this.real$37(var2, var3);
         case 38:
            return this.imag$38(var2, var3);
         case 39:
            return this.conjugate$39(var2, var3);
         case 40:
            return this.Rational$40(var2, var3);
         case 41:
            return this.numerator$41(var2, var3);
         case 42:
            return this.denominator$42(var2, var3);
         case 43:
            return this.__float__$43(var2, var3);
         case 44:
            return this.Integral$44(var2, var3);
         case 45:
            return this.__long__$45(var2, var3);
         case 46:
            return this.__index__$46(var2, var3);
         case 47:
            return this.__pow__$47(var2, var3);
         case 48:
            return this.__lshift__$48(var2, var3);
         case 49:
            return this.__rlshift__$49(var2, var3);
         case 50:
            return this.__rshift__$50(var2, var3);
         case 51:
            return this.__rrshift__$51(var2, var3);
         case 52:
            return this.__and__$52(var2, var3);
         case 53:
            return this.__rand__$53(var2, var3);
         case 54:
            return this.__xor__$54(var2, var3);
         case 55:
            return this.__rxor__$55(var2, var3);
         case 56:
            return this.__or__$56(var2, var3);
         case 57:
            return this.__ror__$57(var2, var3);
         case 58:
            return this.__invert__$58(var2, var3);
         case 59:
            return this.__float__$59(var2, var3);
         case 60:
            return this.numerator$60(var2, var3);
         case 61:
            return this.denominator$61(var2, var3);
         default:
            return null;
      }
   }
}

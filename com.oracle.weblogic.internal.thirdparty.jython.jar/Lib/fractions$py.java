import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFloat;
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
@Filename("fractions.py")
public class fractions$py extends PyFunctionTable implements PyRunnable {
   static fractions$py self;
   static final PyCode f$0;
   static final PyCode gcd$1;
   static final PyCode Fraction$2;
   static final PyCode __new__$3;
   static final PyCode from_float$4;
   static final PyCode from_decimal$5;
   static final PyCode limit_denominator$6;
   static final PyCode numerator$7;
   static final PyCode denominator$8;
   static final PyCode __repr__$9;
   static final PyCode __str__$10;
   static final PyCode _operator_fallbacks$11;
   static final PyCode forward$12;
   static final PyCode reverse$13;
   static final PyCode _add$14;
   static final PyCode _sub$15;
   static final PyCode _mul$16;
   static final PyCode _div$17;
   static final PyCode __floordiv__$18;
   static final PyCode __rfloordiv__$19;
   static final PyCode __mod__$20;
   static final PyCode __rmod__$21;
   static final PyCode __pow__$22;
   static final PyCode __rpow__$23;
   static final PyCode __pos__$24;
   static final PyCode __neg__$25;
   static final PyCode __abs__$26;
   static final PyCode __trunc__$27;
   static final PyCode __hash__$28;
   static final PyCode __eq__$29;
   static final PyCode _richcmp$30;
   static final PyCode __lt__$31;
   static final PyCode __gt__$32;
   static final PyCode __le__$33;
   static final PyCode __ge__$34;
   static final PyCode __nonzero__$35;
   static final PyCode __reduce__$36;
   static final PyCode __copy__$37;
   static final PyCode __deepcopy__$38;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Rational, infinite-precision, real numbers."));
      var1.setline(4);
      PyString.fromInterned("Rational, infinite-precision, real numbers.");
      var1.setline(6);
      String[] var3 = new String[]{"division"};
      PyObject[] var5 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("division", var4);
      var4 = null;
      var1.setline(7);
      var3 = new String[]{"Decimal"};
      var5 = imp.importFrom("decimal", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Decimal", var4);
      var4 = null;
      var1.setline(8);
      PyObject var6 = imp.importOne("math", var1, -1);
      var1.setlocal("math", var6);
      var3 = null;
      var1.setline(9);
      var6 = imp.importOne("numbers", var1, -1);
      var1.setlocal("numbers", var6);
      var3 = null;
      var1.setline(10);
      var6 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var6);
      var3 = null;
      var1.setline(11);
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(13);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Fraction"), PyString.fromInterned("gcd")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(15);
      var6 = var1.getname("numbers").__getattr__("Rational");
      var1.setlocal("Rational", var6);
      var3 = null;
      var1.setline(18);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, gcd$1, PyString.fromInterned("Calculate the Greatest Common Divisor of a and b.\n\n    Unless b==0, the result will have the same sign as b (so that when\n    b is divided by it, the result comes out positive).\n    "));
      var1.setlocal("gcd", var8);
      var3 = null;
      var1.setline(29);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n    \\A\\s*                      # optional whitespace at the start, then\n    (?P<sign>[-+]?)            # an optional sign, then\n    (?=\\d|\\.\\d)                # lookahead for digit or .digit\n    (?P<num>\\d*)               # numerator (possibly empty)\n    (?:                        # followed by\n       (?:/(?P<denom>\\d+))?    # an optional denominator\n    |                          # or\n       (?:\\.(?P<decimal>\\d*))? # an optional fractional part\n       (?:E(?P<exp>[-+]?\\d+))? # and optional exponent\n    )\n    \\s*\\Z                      # and optional whitespace to finish\n"), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("IGNORECASE")));
      var1.setlocal("_RATIONAL_FORMAT", var6);
      var3 = null;
      var1.setline(44);
      var5 = new PyObject[]{var1.getname("Rational")};
      var4 = Py.makeClass("Fraction", var5, Fraction$2);
      var1.setlocal("Fraction", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gcd$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyString.fromInterned("Calculate the Greatest Common Divisor of a and b.\n\n    Unless b==0, the result will have the same sign as b (so that when\n    b is divided by it, the result comes out positive).\n    ");

      while(true) {
         var1.setline(24);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(26);
            PyObject var6 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(25);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0)._mod(var1.getlocal(1))});
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
      }
   }

   public PyObject Fraction$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class implements rational numbers.\n\n    In the two-argument form of the constructor, Fraction(8, 6) will\n    produce a rational number equivalent to 4/3. Both arguments must\n    be Rational. The numerator defaults to 0 and the denominator\n    defaults to 1 so that Fraction(3) == 3 and Fraction() == 0.\n\n    Fractions can also be constructed from:\n\n      - numeric strings similar to those accepted by the\n        float constructor (for example, '-2.3' or '1e10')\n\n      - strings of the form '123/456'\n\n      - float and Decimal instances\n\n      - other Rational instances (including integers)\n\n    "));
      var1.setline(63);
      PyString.fromInterned("This class implements rational numbers.\n\n    In the two-argument form of the constructor, Fraction(8, 6) will\n    produce a rational number equivalent to 4/3. Both arguments must\n    be Rational. The numerator defaults to 0 and the denominator\n    defaults to 1 so that Fraction(3) == 3 and Fraction() == 0.\n\n    Fractions can also be constructed from:\n\n      - numeric strings similar to those accepted by the\n        float constructor (for example, '-2.3' or '1e10')\n\n      - strings of the form '123/456'\n\n      - float and Decimal instances\n\n      - other Rational instances (including integers)\n\n    ");
      var1.setline(65);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_numerator"), PyString.fromInterned("_denominator")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(68);
      PyObject[] var6 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __new__$3, PyString.fromInterned("Constructs a Fraction.\n\n        Takes a string like '3/2' or '1.5', another Rational instance, a\n        numerator/denominator pair, or a float.\n\n        Examples\n        --------\n\n        >>> Fraction(10, -8)\n        Fraction(-5, 4)\n        >>> Fraction(Fraction(1, 7), 5)\n        Fraction(1, 35)\n        >>> Fraction(Fraction(1, 7), Fraction(2, 3))\n        Fraction(3, 14)\n        >>> Fraction('314')\n        Fraction(314, 1)\n        >>> Fraction('-35/4')\n        Fraction(-35, 4)\n        >>> Fraction('3.1415') # conversion from numeric string\n        Fraction(6283, 2000)\n        >>> Fraction('-47e-2') # string may include a decimal exponent\n        Fraction(-47, 100)\n        >>> Fraction(1.47)  # direct construction from float (exact conversion)\n        Fraction(6620291452234629, 4503599627370496)\n        >>> Fraction(2.25)\n        Fraction(9, 4)\n        >>> Fraction(Decimal('1.47'))\n        Fraction(147, 100)\n\n        "));
      var1.setlocal("__new__", var7);
      var3 = null;
      var1.setline(168);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, from_float$4, PyString.fromInterned("Converts a finite float to a rational number, exactly.\n\n        Beware that Fraction.from_float(0.3) != Fraction(3, 10).\n\n        "));
      PyObject var8 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("from_float", var8);
      var3 = null;
      var1.setline(184);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, from_decimal$5, PyString.fromInterned("Converts a finite Decimal instance to a rational number, exactly."));
      var8 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("from_decimal", var8);
      var3 = null;
      var1.setline(206);
      var6 = new PyObject[]{Py.newInteger(1000000)};
      var7 = new PyFunction(var1.f_globals, var6, limit_denominator$6, PyString.fromInterned("Closest Fraction to self with denominator at most max_denominator.\n\n        >>> Fraction('3.141592653589793').limit_denominator(10)\n        Fraction(22, 7)\n        >>> Fraction('3.141592653589793').limit_denominator(100)\n        Fraction(311, 99)\n        >>> Fraction(4321, 8765).limit_denominator(10000)\n        Fraction(4321, 8765)\n\n        "));
      var1.setlocal("limit_denominator", var7);
      var3 = null;
      var1.setline(261);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, numerator$7, (PyObject)null);
      var8 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("numerator", var8);
      var3 = null;
      var1.setline(265);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, denominator$8, (PyObject)null);
      var8 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("denominator", var8);
      var3 = null;
      var1.setline(269);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __repr__$9, PyString.fromInterned("repr(self)"));
      var1.setlocal("__repr__", var7);
      var3 = null;
      var1.setline(273);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __str__$10, PyString.fromInterned("str(self)"));
      var1.setlocal("__str__", var7);
      var3 = null;
      var1.setline(280);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _operator_fallbacks$11, PyString.fromInterned("Generates forward and reverse operators given a purely-rational\n        operator and a function from the operator module.\n\n        Use this like:\n        __op__, __rop__ = _operator_fallbacks(just_rational_op, operator.op)\n\n        In general, we want to implement the arithmetic operations so\n        that mixed-mode operations either call an implementation whose\n        author knew about the types of both arguments, or convert both\n        to the nearest built in type and do the operation there. In\n        Fraction, that means that we define __add__ and __radd__ as:\n\n            def __add__(self, other):\n                # Both types have numerators/denominator attributes,\n                # so do the operation directly\n                if isinstance(other, (int, long, Fraction)):\n                    return Fraction(self.numerator * other.denominator +\n                                    other.numerator * self.denominator,\n                                    self.denominator * other.denominator)\n                # float and complex don't have those operations, but we\n                # know about those types, so special case them.\n                elif isinstance(other, float):\n                    return float(self) + other\n                elif isinstance(other, complex):\n                    return complex(self) + other\n                # Let the other type take over.\n                return NotImplemented\n\n            def __radd__(self, other):\n                # radd handles more types than add because there's\n                # nothing left to fall back to.\n                if isinstance(other, Rational):\n                    return Fraction(self.numerator * other.denominator +\n                                    other.numerator * self.denominator,\n                                    self.denominator * other.denominator)\n                elif isinstance(other, Real):\n                    return float(other) + float(self)\n                elif isinstance(other, Complex):\n                    return complex(other) + complex(self)\n                return NotImplemented\n\n\n        There are 5 different cases for a mixed-type addition on\n        Fraction. I'll refer to all of the above code that doesn't\n        refer to Fraction, float, or complex as \"boilerplate\". 'r'\n        will be an instance of Fraction, which is a subtype of\n        Rational (r : Fraction <: Rational), and b : B <:\n        Complex. The first three involve 'r + b':\n\n            1. If B <: Fraction, int, float, or complex, we handle\n               that specially, and all is well.\n            2. If Fraction falls back to the boilerplate code, and it\n               were to return a value from __add__, we'd miss the\n               possibility that B defines a more intelligent __radd__,\n               so the boilerplate should return NotImplemented from\n               __add__. In particular, we don't handle Rational\n               here, even though we could get an exact answer, in case\n               the other type wants to do something special.\n            3. If B <: Fraction, Python tries B.__radd__ before\n               Fraction.__add__. This is ok, because it was\n               implemented with knowledge of Fraction, so it can\n               handle those instances before delegating to Real or\n               Complex.\n\n        The next two situations describe 'b + r'. We assume that b\n        didn't know about Fraction in its implementation, and that it\n        uses similar boilerplate code:\n\n            4. If B <: Rational, then __radd_ converts both to the\n               builtin rational type (hey look, that's us) and\n               proceeds.\n            5. Otherwise, __radd__ tries to find the nearest common\n               base ABC, and fall back to its builtin type. Since this\n               class doesn't subclass a concrete type, there's no\n               implementation to fall back to, so we need to try as\n               hard as possible to return an actual value, or the user\n               will get a TypeError.\n\n        "));
      var1.setlocal("_operator_fallbacks", var7);
      var3 = null;
      var1.setline(387);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _add$14, PyString.fromInterned("a + b"));
      var1.setlocal("_add", var7);
      var3 = null;
      var1.setline(393);
      var8 = var1.getname("_operator_fallbacks").__call__(var2, var1.getname("_add"), var1.getname("operator").__getattr__("add"));
      PyObject[] var4 = Py.unpackSequence(var8, 2);
      PyObject var5 = var4[0];
      var1.setlocal("__add__", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("__radd__", var5);
      var5 = null;
      var3 = null;
      var1.setline(395);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _sub$15, PyString.fromInterned("a - b"));
      var1.setlocal("_sub", var7);
      var3 = null;
      var1.setline(401);
      var8 = var1.getname("_operator_fallbacks").__call__(var2, var1.getname("_sub"), var1.getname("operator").__getattr__("sub"));
      var4 = Py.unpackSequence(var8, 2);
      var5 = var4[0];
      var1.setlocal("__sub__", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("__rsub__", var5);
      var5 = null;
      var3 = null;
      var1.setline(403);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _mul$16, PyString.fromInterned("a * b"));
      var1.setlocal("_mul", var7);
      var3 = null;
      var1.setline(407);
      var8 = var1.getname("_operator_fallbacks").__call__(var2, var1.getname("_mul"), var1.getname("operator").__getattr__("mul"));
      var4 = Py.unpackSequence(var8, 2);
      var5 = var4[0];
      var1.setlocal("__mul__", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("__rmul__", var5);
      var5 = null;
      var3 = null;
      var1.setline(409);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _div$17, PyString.fromInterned("a / b"));
      var1.setlocal("_div", var7);
      var3 = null;
      var1.setline(414);
      var8 = var1.getname("_operator_fallbacks").__call__(var2, var1.getname("_div"), var1.getname("operator").__getattr__("truediv"));
      var4 = Py.unpackSequence(var8, 2);
      var5 = var4[0];
      var1.setlocal("__truediv__", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("__rtruediv__", var5);
      var5 = null;
      var3 = null;
      var1.setline(415);
      var8 = var1.getname("_operator_fallbacks").__call__(var2, var1.getname("_div"), var1.getname("operator").__getattr__("div"));
      var4 = Py.unpackSequence(var8, 2);
      var5 = var4[0];
      var1.setlocal("__div__", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("__rdiv__", var5);
      var5 = null;
      var3 = null;
      var1.setline(417);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __floordiv__$18, PyString.fromInterned("a // b"));
      var1.setlocal("__floordiv__", var7);
      var3 = null;
      var1.setline(429);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __rfloordiv__$19, PyString.fromInterned("a // b"));
      var1.setlocal("__rfloordiv__", var7);
      var3 = null;
      var1.setline(441);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __mod__$20, PyString.fromInterned("a % b"));
      var1.setlocal("__mod__", var7);
      var3 = null;
      var1.setline(446);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __rmod__$21, PyString.fromInterned("a % b"));
      var1.setlocal("__rmod__", var7);
      var3 = null;
      var1.setline(451);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __pow__$22, PyString.fromInterned("a ** b\n\n        If b is not an integer, the result will be a float or complex\n        since roots are generally irrational. If b is an integer, the\n        result will be rational.\n\n        "));
      var1.setlocal("__pow__", var7);
      var3 = null;
      var1.setline(475);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __rpow__$23, PyString.fromInterned("a ** b"));
      var1.setlocal("__rpow__", var7);
      var3 = null;
      var1.setline(489);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __pos__$24, PyString.fromInterned("+a: Coerces a subclass instance to Fraction"));
      var1.setlocal("__pos__", var7);
      var3 = null;
      var1.setline(493);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __neg__$25, PyString.fromInterned("-a"));
      var1.setlocal("__neg__", var7);
      var3 = null;
      var1.setline(497);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __abs__$26, PyString.fromInterned("abs(a)"));
      var1.setlocal("__abs__", var7);
      var3 = null;
      var1.setline(501);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __trunc__$27, PyString.fromInterned("trunc(a)"));
      var1.setlocal("__trunc__", var7);
      var3 = null;
      var1.setline(508);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __hash__$28, PyString.fromInterned("hash(self)\n\n        Tricky because values that are exactly representable as a\n        float must have the same hash as that float.\n\n        "));
      var1.setlocal("__hash__", var7);
      var3 = null;
      var1.setline(527);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __eq__$29, PyString.fromInterned("a == b"));
      var1.setlocal("__eq__", var7);
      var3 = null;
      var1.setline(546);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _richcmp$30, PyString.fromInterned("Helper for comparison operators, for internal use only.\n\n        Implement comparison between a Rational instance `self`, and\n        either another Rational instance or a float `other`.  If\n        `other` is not a Rational instance or a float, return\n        NotImplemented. `op` should be one of the six standard\n        comparison operators.\n\n        "));
      var1.setlocal("_richcmp", var7);
      var3 = null;
      var1.setline(572);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __lt__$31, PyString.fromInterned("a < b"));
      var1.setlocal("__lt__", var7);
      var3 = null;
      var1.setline(576);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __gt__$32, PyString.fromInterned("a > b"));
      var1.setlocal("__gt__", var7);
      var3 = null;
      var1.setline(580);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __le__$33, PyString.fromInterned("a <= b"));
      var1.setlocal("__le__", var7);
      var3 = null;
      var1.setline(584);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __ge__$34, PyString.fromInterned("a >= b"));
      var1.setlocal("__ge__", var7);
      var3 = null;
      var1.setline(588);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __nonzero__$35, PyString.fromInterned("a != 0"));
      var1.setlocal("__nonzero__", var7);
      var3 = null;
      var1.setline(594);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __reduce__$36, (PyObject)null);
      var1.setlocal("__reduce__", var7);
      var3 = null;
      var1.setline(597);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __copy__$37, (PyObject)null);
      var1.setlocal("__copy__", var7);
      var3 = null;
      var1.setline(602);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __deepcopy__$38, (PyObject)null);
      var1.setlocal("__deepcopy__", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$3(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("Constructs a Fraction.\n\n        Takes a string like '3/2' or '1.5', another Rational instance, a\n        numerator/denominator pair, or a float.\n\n        Examples\n        --------\n\n        >>> Fraction(10, -8)\n        Fraction(-5, 4)\n        >>> Fraction(Fraction(1, 7), 5)\n        Fraction(1, 35)\n        >>> Fraction(Fraction(1, 7), Fraction(2, 3))\n        Fraction(3, 14)\n        >>> Fraction('314')\n        Fraction(314, 1)\n        >>> Fraction('-35/4')\n        Fraction(-35, 4)\n        >>> Fraction('3.1415') # conversion from numeric string\n        Fraction(6283, 2000)\n        >>> Fraction('-47e-2') # string may include a decimal exponent\n        Fraction(-47, 100)\n        >>> Fraction(1.47)  # direct construction from float (exact conversion)\n        Fraction(6620291452234629, 4503599627370496)\n        >>> Fraction(2.25)\n        Fraction(9, 4)\n        >>> Fraction(Decimal('1.47'))\n        Fraction(147, 100)\n\n        ");
      var1.setline(99);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("Fraction"), var1.getlocal(0)).__getattr__("__new__").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(102);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
            var1.setline(103);
            var3 = var1.getlocal(1).__getattr__("numerator");
            var1.getlocal(3).__setattr__("_numerator", var3);
            var3 = null;
            var1.setline(104);
            var3 = var1.getlocal(1).__getattr__("denominator");
            var1.getlocal(3).__setattr__("_denominator", var3);
            var3 = null;
            var1.setline(105);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(107);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
            var1.setline(109);
            var4 = var1.getglobal("Fraction").__getattr__("from_float").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(110);
            var4 = var1.getlocal(4).__getattr__("_numerator");
            var1.getlocal(3).__setattr__("_numerator", var4);
            var4 = null;
            var1.setline(111);
            var4 = var1.getlocal(4).__getattr__("_denominator");
            var1.getlocal(3).__setattr__("_denominator", var4);
            var4 = null;
            var1.setline(112);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(114);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Decimal")).__nonzero__()) {
            var1.setline(115);
            var4 = var1.getglobal("Fraction").__getattr__("from_decimal").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(116);
            var4 = var1.getlocal(4).__getattr__("_numerator");
            var1.getlocal(3).__setattr__("_numerator", var4);
            var4 = null;
            var1.setline(117);
            var4 = var1.getlocal(4).__getattr__("_denominator");
            var1.getlocal(3).__setattr__("_denominator", var4);
            var4 = null;
            var1.setline(118);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(120);
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(148);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument should be a string or a Rational instance")));
         }

         var1.setline(122);
         var4 = var1.getglobal("_RATIONAL_FORMAT").__getattr__("match").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(123);
         var4 = var1.getlocal(5);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(124);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid literal for Fraction: %r")._mod(var1.getlocal(1))));
         }

         var1.setline(126);
         var10000 = var1.getglobal("int");
         Object var10002 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("num"));
         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = PyString.fromInterned("0");
         }

         var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(127);
         var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("denom"));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(128);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(129);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(6));
            var1.setlocal(2, var4);
            var4 = null;
         } else {
            var1.setline(131);
            PyInteger var7 = Py.newInteger(1);
            var1.setlocal(2, var7);
            var4 = null;
            var1.setline(132);
            var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("decimal"));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(133);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(134);
               var4 = Py.newInteger(10)._pow(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(135);
               var4 = var1.getlocal(1)._mul(var1.getlocal(8))._add(var1.getglobal("int").__call__(var2, var1.getlocal(7)));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(136);
               var4 = var1.getlocal(2);
               var4 = var4._imul(var1.getlocal(8));
               var1.setlocal(2, var4);
            }

            var1.setline(137);
            var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exp"));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(138);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(139);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(140);
               var4 = var1.getlocal(9);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(141);
                  var4 = var1.getlocal(1);
                  var4 = var4._imul(Py.newInteger(10)._pow(var1.getlocal(9)));
                  var1.setlocal(1, var4);
               } else {
                  var1.setline(143);
                  var4 = var1.getlocal(2);
                  var4 = var4._imul(Py.newInteger(10)._pow(var1.getlocal(9).__neg__()));
                  var1.setlocal(2, var4);
               }
            }
         }

         var1.setline(144);
         var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sign"));
         var10000 = var4._eq(PyString.fromInterned("-"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(145);
            var4 = var1.getlocal(1).__neg__();
            var1.setlocal(1, var4);
            var4 = null;
         }
      } else {
         var1.setline(151);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Rational"));
         }

         if (!var10000.__nonzero__()) {
            var1.setline(158);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("both arguments should be Rational instances")));
         }

         var1.setline(153);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("numerator")._mul(var1.getlocal(2).__getattr__("denominator")), var1.getlocal(2).__getattr__("numerator")._mul(var1.getlocal(1).__getattr__("denominator"))});
         PyObject[] var5 = Py.unpackSequence(var8, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(161);
      var4 = var1.getlocal(2);
      var10000 = var4._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         throw Py.makeException(var1.getglobal("ZeroDivisionError").__call__(var2, PyString.fromInterned("Fraction(%s, 0)")._mod(var1.getlocal(1))));
      } else {
         var1.setline(163);
         var4 = var1.getglobal("gcd").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(164);
         var4 = var1.getlocal(1)._floordiv(var1.getlocal(10));
         var1.getlocal(3).__setattr__("_numerator", var4);
         var4 = null;
         var1.setline(165);
         var4 = var1.getlocal(2)._floordiv(var1.getlocal(10));
         var1.getlocal(3).__setattr__("_denominator", var4);
         var4 = null;
         var1.setline(166);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject from_float$4(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyString.fromInterned("Converts a finite float to a rational number, exactly.\n\n        Beware that Fraction.from_float(0.3) != Fraction(3, 10).\n\n        ");
      var1.setline(175);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Integral")).__nonzero__()) {
         var1.setline(176);
         var3 = var1.getlocal(0).__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(177);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__not__().__nonzero__()) {
            var1.setline(178);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s.from_float() only takes floats, not %r (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__name__"), var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__")}))));
         } else {
            var1.setline(180);
            PyObject var10000 = var1.getglobal("math").__getattr__("isnan").__call__(var2, var1.getlocal(1));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("math").__getattr__("isinf").__call__(var2, var1.getlocal(1));
            }

            if (var10000.__nonzero__()) {
               var1.setline(181);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert %r to %s.")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("__name__")}))));
            } else {
               var1.setline(182);
               var10000 = var1.getlocal(0);
               PyObject[] var4 = Py.EmptyObjects;
               String[] var5 = new String[0];
               var10000 = var10000._callextra(var4, var5, var1.getlocal(1).__getattr__("as_integer_ratio").__call__(var2), (PyObject)null);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject from_decimal$5(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Converts a finite Decimal instance to a rational number, exactly.");
      var1.setline(187);
      String[] var3 = new String[]{"Decimal"};
      PyObject[] var6 = imp.importFrom("decimal", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(188);
      PyObject var7;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Integral")).__nonzero__()) {
         var1.setline(189);
         var7 = var1.getlocal(2).__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(1)));
         var1.setlocal(1, var7);
         var3 = null;
      } else {
         var1.setline(190);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s.from_decimal() only takes Decimals, not %r (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__name__"), var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__")}))));
         }
      }

      var1.setline(194);
      if (var1.getlocal(1).__getattr__("is_finite").__call__(var2).__not__().__nonzero__()) {
         var1.setline(196);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert %s to %s.")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("__name__")}))));
      } else {
         var1.setline(197);
         var7 = var1.getlocal(1).__getattr__("as_tuple").__call__(var2);
         PyObject[] var8 = Py.unpackSequence(var7, 3);
         PyObject var5 = var8[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var8[2];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(198);
         var7 = var1.getglobal("int").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(4))));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(199);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(200);
            var7 = var1.getlocal(4).__neg__();
            var1.setlocal(4, var7);
            var3 = null;
         }

         var1.setline(201);
         var7 = var1.getlocal(5);
         PyObject var10000 = var7._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var7 = var1.getlocal(0).__call__(var2, var1.getlocal(4)._mul(Py.newInteger(10)._pow(var1.getlocal(5))));
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(204);
            var7 = var1.getlocal(0).__call__(var2, var1.getlocal(4), Py.newInteger(10)._pow(var1.getlocal(5).__neg__()));
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject limit_denominator$6(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("Closest Fraction to self with denominator at most max_denominator.\n\n        >>> Fraction('3.141592653589793').limit_denominator(10)\n        Fraction(22, 7)\n        >>> Fraction('3.141592653589793').limit_denominator(100)\n        Fraction(311, 99)\n        >>> Fraction(4321, 8765).limit_denominator(10000)\n        Fraction(4321, 8765)\n\n        ");
      var1.setline(238);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(239);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("max_denominator should be at least 1")));
      } else {
         var1.setline(240);
         var3 = var1.getlocal(0).__getattr__("_denominator");
         var10000 = var3._le(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(241);
            var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(243);
            PyTuple var4 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1), Py.newInteger(1), Py.newInteger(0)});
            PyObject[] var5 = Py.unpackSequence(var4, 4);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(5, var6);
            var6 = null;
            var4 = null;
            var1.setline(244);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator")});
            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;

            PyObject var7;
            while(true) {
               var1.setline(245);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(246);
               var7 = var1.getlocal(6)._floordiv(var1.getlocal(7));
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(247);
               var7 = var1.getlocal(3)._add(var1.getlocal(8)._mul(var1.getlocal(5)));
               var1.setlocal(9, var7);
               var4 = null;
               var1.setline(248);
               var7 = var1.getlocal(9);
               var10000 = var7._gt(var1.getlocal(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(250);
               var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)._add(var1.getlocal(8)._mul(var1.getlocal(4))), var1.getlocal(9)});
               var5 = Py.unpackSequence(var4, 4);
               var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[3];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(251);
               var4 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)._sub(var1.getlocal(8)._mul(var1.getlocal(7)))});
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(253);
            var7 = var1.getlocal(1)._sub(var1.getlocal(3))._floordiv(var1.getlocal(5));
            var1.setlocal(10, var7);
            var4 = null;
            var1.setline(254);
            var7 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(2)._add(var1.getlocal(10)._mul(var1.getlocal(4))), var1.getlocal(3)._add(var1.getlocal(10)._mul(var1.getlocal(5))));
            var1.setlocal(11, var7);
            var4 = null;
            var1.setline(255);
            var7 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(4), var1.getlocal(5));
            var1.setlocal(12, var7);
            var4 = null;
            var1.setline(256);
            var7 = var1.getglobal("abs").__call__(var2, var1.getlocal(12)._sub(var1.getlocal(0)));
            var10000 = var7._le(var1.getglobal("abs").__call__(var2, var1.getlocal(11)._sub(var1.getlocal(0))));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(257);
               var3 = var1.getlocal(12);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(259);
               var3 = var1.getlocal(11);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject numerator$7(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject var3 = var1.getlocal(0).__getattr__("_numerator");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject denominator$8(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyObject var3 = var1.getlocal(0).__getattr__("_denominator");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$9(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyString.fromInterned("repr(self)");
      var1.setline(271);
      PyObject var3 = PyString.fromInterned("Fraction(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$10(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString.fromInterned("str(self)");
      var1.setline(275);
      PyObject var3 = var1.getlocal(0).__getattr__("_denominator");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(276);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_numerator"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(278);
         var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator")}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _operator_fallbacks$11(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(0, 1);
      var1.setline(359);
      PyString.fromInterned("Generates forward and reverse operators given a purely-rational\n        operator and a function from the operator module.\n\n        Use this like:\n        __op__, __rop__ = _operator_fallbacks(just_rational_op, operator.op)\n\n        In general, we want to implement the arithmetic operations so\n        that mixed-mode operations either call an implementation whose\n        author knew about the types of both arguments, or convert both\n        to the nearest built in type and do the operation there. In\n        Fraction, that means that we define __add__ and __radd__ as:\n\n            def __add__(self, other):\n                # Both types have numerators/denominator attributes,\n                # so do the operation directly\n                if isinstance(other, (int, long, Fraction)):\n                    return Fraction(self.numerator * other.denominator +\n                                    other.numerator * self.denominator,\n                                    self.denominator * other.denominator)\n                # float and complex don't have those operations, but we\n                # know about those types, so special case them.\n                elif isinstance(other, float):\n                    return float(self) + other\n                elif isinstance(other, complex):\n                    return complex(self) + other\n                # Let the other type take over.\n                return NotImplemented\n\n            def __radd__(self, other):\n                # radd handles more types than add because there's\n                # nothing left to fall back to.\n                if isinstance(other, Rational):\n                    return Fraction(self.numerator * other.denominator +\n                                    other.numerator * self.denominator,\n                                    self.denominator * other.denominator)\n                elif isinstance(other, Real):\n                    return float(other) + float(self)\n                elif isinstance(other, Complex):\n                    return complex(other) + complex(self)\n                return NotImplemented\n\n\n        There are 5 different cases for a mixed-type addition on\n        Fraction. I'll refer to all of the above code that doesn't\n        refer to Fraction, float, or complex as \"boilerplate\". 'r'\n        will be an instance of Fraction, which is a subtype of\n        Rational (r : Fraction <: Rational), and b : B <:\n        Complex. The first three involve 'r + b':\n\n            1. If B <: Fraction, int, float, or complex, we handle\n               that specially, and all is well.\n            2. If Fraction falls back to the boilerplate code, and it\n               were to return a value from __add__, we'd miss the\n               possibility that B defines a more intelligent __radd__,\n               so the boilerplate should return NotImplemented from\n               __add__. In particular, we don't handle Rational\n               here, even though we could get an exact answer, in case\n               the other type wants to do something special.\n            3. If B <: Fraction, Python tries B.__radd__ before\n               Fraction.__add__. This is ok, because it was\n               implemented with knowledge of Fraction, so it can\n               handle those instances before delegating to Real or\n               Complex.\n\n        The next two situations describe 'b + r'. We assume that b\n        didn't know about Fraction in its implementation, and that it\n        uses similar boilerplate code:\n\n            4. If B <: Rational, then __radd_ converts both to the\n               builtin rational type (hey look, that's us) and\n               proceeds.\n            5. Otherwise, __radd__ tries to find the nearest common\n               base ABC, and fall back to its builtin type. Since this\n               class doesn't subclass a concrete type, there's no\n               implementation to fall back to, so we need to try as\n               hard as possible to return an actual value, or the user\n               will get a TypeError.\n\n        ");
      var1.setline(360);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = forward$12;
      var3 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(369);
      PyObject var5 = PyString.fromInterned("__")._add(var1.getderef(0).__getattr__("__name__"))._add(PyString.fromInterned("__"));
      var1.getlocal(2).__setattr__("__name__", var5);
      var3 = null;
      var1.setline(370);
      var5 = var1.getderef(1).__getattr__("__doc__");
      var1.getlocal(2).__setattr__("__doc__", var5);
      var3 = null;
      var1.setline(372);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = reverse$13;
      var3 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(382);
      var5 = PyString.fromInterned("__r")._add(var1.getderef(0).__getattr__("__name__"))._add(PyString.fromInterned("__"));
      var1.getlocal(3).__setattr__("__name__", var5);
      var3 = null;
      var1.setline(383);
      var5 = var1.getderef(1).__getattr__("__doc__");
      var1.getlocal(3).__setattr__("__doc__", var5);
      var3 = null;
      var1.setline(385);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject forward$12(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long"), var1.getglobal("Fraction")}))).__nonzero__()) {
         var1.setline(362);
         var3 = var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(363);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
            var1.setline(364);
            var3 = var1.getderef(1).__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0)), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(365);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("complex")).__nonzero__()) {
               var1.setline(366);
               var3 = var1.getderef(1).__call__(var2, var1.getglobal("complex").__call__(var2, var1.getlocal(0)), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(368);
               var3 = var1.getglobal("NotImplemented");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject reverse$13(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(375);
         var3 = var1.getderef(0).__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(376);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Real")).__nonzero__()) {
            var1.setline(377);
            var3 = var1.getderef(1).__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(1)), var1.getglobal("float").__call__(var2, var1.getlocal(0)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(378);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Complex")).__nonzero__()) {
               var1.setline(379);
               var3 = var1.getderef(1).__call__(var2, var1.getglobal("complex").__call__(var2, var1.getlocal(1)), var1.getglobal("complex").__call__(var2, var1.getlocal(0)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(381);
               var3 = var1.getglobal("NotImplemented");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _add$14(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyString.fromInterned("a + b");
      var1.setline(389);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("numerator")._mul(var1.getlocal(1).__getattr__("denominator"))._add(var1.getlocal(1).__getattr__("numerator")._mul(var1.getlocal(0).__getattr__("denominator"))), var1.getlocal(0).__getattr__("denominator")._mul(var1.getlocal(1).__getattr__("denominator")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _sub$15(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyString.fromInterned("a - b");
      var1.setline(397);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("numerator")._mul(var1.getlocal(1).__getattr__("denominator"))._sub(var1.getlocal(1).__getattr__("numerator")._mul(var1.getlocal(0).__getattr__("denominator"))), var1.getlocal(0).__getattr__("denominator")._mul(var1.getlocal(1).__getattr__("denominator")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _mul$16(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyString.fromInterned("a * b");
      var1.setline(405);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("numerator")._mul(var1.getlocal(1).__getattr__("numerator")), var1.getlocal(0).__getattr__("denominator")._mul(var1.getlocal(1).__getattr__("denominator")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _div$17(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("a / b");
      var1.setline(411);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("numerator")._mul(var1.getlocal(1).__getattr__("denominator")), var1.getlocal(0).__getattr__("denominator")._mul(var1.getlocal(1).__getattr__("numerator")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __floordiv__$18(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyString.fromInterned("a // b");
      var1.setline(420);
      PyObject var3 = var1.getlocal(0)._truediv(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(421);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(425);
         var3 = var1.getlocal(2).__getattr__("numerator")._floordiv(var1.getlocal(2).__getattr__("denominator"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(427);
         var3 = var1.getglobal("math").__getattr__("floor").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __rfloordiv__$19(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyString.fromInterned("a // b");
      var1.setline(432);
      PyObject var3 = var1.getlocal(1)._truediv(var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(433);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(437);
         var3 = var1.getlocal(2).__getattr__("numerator")._floordiv(var1.getlocal(2).__getattr__("denominator"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(439);
         var3 = var1.getglobal("math").__getattr__("floor").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __mod__$20(PyFrame var1, ThreadState var2) {
      var1.setline(442);
      PyString.fromInterned("a % b");
      var1.setline(443);
      PyObject var3 = var1.getlocal(0)._floordiv(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(444);
      var3 = var1.getlocal(0)._sub(var1.getlocal(1)._mul(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __rmod__$21(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyString.fromInterned("a % b");
      var1.setline(448);
      PyObject var3 = var1.getlocal(1)._floordiv(var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(449);
      var3 = var1.getlocal(1)._sub(var1.getlocal(0)._mul(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __pow__$22(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyString.fromInterned("a ** b\n\n        If b is not an integer, the result will be a float or complex\n        since roots are generally irrational. If b is an integer, the\n        result will be rational.\n\n        ");
      var1.setline(459);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(460);
         var3 = var1.getlocal(1).__getattr__("denominator");
         PyObject var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(461);
            var3 = var1.getlocal(1).__getattr__("numerator");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(462);
            var3 = var1.getlocal(2);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(463);
               var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("_numerator")._pow(var1.getlocal(2)), var1.getlocal(0).__getattr__("_denominator")._pow(var1.getlocal(2)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(466);
               var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("_denominator")._pow(var1.getlocal(2).__neg__()), var1.getlocal(0).__getattr__("_numerator")._pow(var1.getlocal(2).__neg__()));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(471);
            var3 = var1.getglobal("float").__call__(var2, var1.getlocal(0))._pow(var1.getglobal("float").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(473);
         var3 = var1.getglobal("float").__call__(var2, var1.getlocal(0))._pow(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __rpow__$23(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyString.fromInterned("a ** b");
      var1.setline(477);
      PyObject var3 = var1.getlocal(0).__getattr__("_denominator");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_numerator");
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(479);
         var3 = var1.getlocal(1)._pow(var1.getlocal(0).__getattr__("_numerator"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(481);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
            var1.setline(482);
            var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(1).__getattr__("numerator"), var1.getlocal(1).__getattr__("denominator"))._pow(var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(484);
            PyObject var4 = var1.getlocal(0).__getattr__("_denominator");
            var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(485);
               var3 = var1.getlocal(1)._pow(var1.getlocal(0).__getattr__("_numerator"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(487);
               var3 = var1.getlocal(1)._pow(var1.getglobal("float").__call__(var2, var1.getlocal(0)));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __pos__$24(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyString.fromInterned("+a: Coerces a subclass instance to Fraction");
      var1.setline(491);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __neg__$25(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyString.fromInterned("-a");
      var1.setline(495);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getlocal(0).__getattr__("_numerator").__neg__(), var1.getlocal(0).__getattr__("_denominator"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __abs__$26(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned("abs(a)");
      var1.setline(499);
      PyObject var3 = var1.getglobal("Fraction").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(0).__getattr__("_numerator")), var1.getlocal(0).__getattr__("_denominator"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __trunc__$27(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      PyString.fromInterned("trunc(a)");
      var1.setline(503);
      PyObject var3 = var1.getlocal(0).__getattr__("_numerator");
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(504);
         var3 = var1.getlocal(0).__getattr__("_numerator").__neg__()._floordiv(var1.getlocal(0).__getattr__("_denominator")).__neg__();
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(506);
         var3 = var1.getlocal(0).__getattr__("_numerator")._floordiv(var1.getlocal(0).__getattr__("_denominator"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __hash__$28(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyString.fromInterned("hash(self)\n\n        Tricky because values that are exactly representable as a\n        float must have the same hash as that float.\n\n        ");
      var1.setline(516);
      PyObject var3 = var1.getlocal(0).__getattr__("_denominator");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(518);
         var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("_numerator"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(520);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(var1.getglobal("float").__call__(var2, var1.getlocal(0)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(521);
            var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(525);
            var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator")})));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __eq__$29(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyString.fromInterned("a == b");
      var1.setline(529);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(530);
         var3 = var1.getlocal(0).__getattr__("_numerator");
         var10000 = var3._eq(var1.getlocal(1).__getattr__("numerator"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_denominator");
            var10000 = var3._eq(var1.getlocal(1).__getattr__("denominator"));
            var3 = null;
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(532);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Complex"));
         PyObject var4;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(1).__getattr__("imag");
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(533);
            var4 = var1.getlocal(1).__getattr__("real");
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(534);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
            var1.setline(535);
            var10000 = var1.getglobal("math").__getattr__("isnan").__call__(var2, var1.getlocal(1));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("math").__getattr__("isinf").__call__(var2, var1.getlocal(1));
            }

            if (var10000.__nonzero__()) {
               var1.setline(538);
               PyFloat var5 = Py.newFloat(0.0);
               var10000 = var5._eq(var1.getlocal(1));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(540);
               var4 = var1.getlocal(0);
               var10000 = var4._eq(var1.getlocal(0).__getattr__("from_float").__call__(var2, var1.getlocal(1)));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(544);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _richcmp$30(PyFrame var1, ThreadState var2) {
      var1.setline(555);
      PyString.fromInterned("Helper for comparison operators, for internal use only.\n\n        Implement comparison between a Rational instance `self`, and\n        either another Rational instance or a float `other`.  If\n        `other` is not a Rational instance or a float, return\n        NotImplemented. `op` should be one of the six standard\n        comparison operators.\n\n        ");
      var1.setline(557);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Rational")).__nonzero__()) {
         var1.setline(558);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("_numerator")._mul(var1.getlocal(1).__getattr__("denominator")), var1.getlocal(0).__getattr__("_denominator")._mul(var1.getlocal(1).__getattr__("numerator")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(562);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("complex")).__nonzero__()) {
            var1.setline(563);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no ordering relation is defined for complex numbers")));
         } else {
            var1.setline(564);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
               var1.setline(565);
               PyObject var10000 = var1.getglobal("math").__getattr__("isnan").__call__(var2, var1.getlocal(1));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("math").__getattr__("isinf").__call__(var2, var1.getlocal(1));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(566);
                  var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)Py.newFloat(0.0), (PyObject)var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(568);
                  var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("from_float").__call__(var2, var1.getlocal(1)));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(570);
               var3 = var1.getglobal("NotImplemented");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __lt__$31(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      PyString.fromInterned("a < b");
      var1.setline(574);
      PyObject var3 = var1.getlocal(0).__getattr__("_richcmp").__call__(var2, var1.getlocal(1), var1.getglobal("operator").__getattr__("lt"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __gt__$32(PyFrame var1, ThreadState var2) {
      var1.setline(577);
      PyString.fromInterned("a > b");
      var1.setline(578);
      PyObject var3 = var1.getlocal(0).__getattr__("_richcmp").__call__(var2, var1.getlocal(1), var1.getglobal("operator").__getattr__("gt"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __le__$33(PyFrame var1, ThreadState var2) {
      var1.setline(581);
      PyString.fromInterned("a <= b");
      var1.setline(582);
      PyObject var3 = var1.getlocal(0).__getattr__("_richcmp").__call__(var2, var1.getlocal(1), var1.getglobal("operator").__getattr__("le"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ge__$34(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyString.fromInterned("a >= b");
      var1.setline(586);
      PyObject var3 = var1.getlocal(0).__getattr__("_richcmp").__call__(var2, var1.getlocal(1), var1.getglobal("operator").__getattr__("ge"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$35(PyFrame var1, ThreadState var2) {
      var1.setline(589);
      PyString.fromInterned("a != 0");
      var1.setline(590);
      PyObject var3 = var1.getlocal(0).__getattr__("_numerator");
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __reduce__$36(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0))})});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __copy__$37(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("Fraction"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(599);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(600);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __deepcopy__$38(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("Fraction"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(604);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(605);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_numerator"), var1.getlocal(0).__getattr__("_denominator"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fractions$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"a", "b"};
      gcd$1 = Py.newCode(2, var2, var1, "gcd", 18, false, false, self, 1, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      Fraction$2 = Py.newCode(0, var2, var1, "Fraction", 44, false, false, self, 2, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cls", "numerator", "denominator", "self", "value", "m", "denom", "decimal", "scale", "exp", "g"};
      __new__$3 = Py.newCode(3, var2, var1, "__new__", 68, false, false, self, 3, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "f"};
      from_float$4 = Py.newCode(2, var2, var1, "from_float", 168, false, false, self, 4, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "dec", "Decimal", "sign", "digits", "exp"};
      from_decimal$5 = Py.newCode(2, var2, var1, "from_decimal", 184, false, false, self, 5, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "max_denominator", "p0", "q0", "p1", "q1", "n", "d", "a", "q2", "k", "bound1", "bound2"};
      limit_denominator$6 = Py.newCode(2, var2, var1, "limit_denominator", 206, false, false, self, 6, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      numerator$7 = Py.newCode(1, var2, var1, "numerator", 261, false, false, self, 7, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      denominator$8 = Py.newCode(1, var2, var1, "denominator", 265, false, false, self, 8, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __repr__$9 = Py.newCode(1, var2, var1, "__repr__", 269, false, false, self, 9, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __str__$10 = Py.newCode(1, var2, var1, "__str__", 273, false, false, self, 10, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"monomorphic_operator", "fallback_operator", "forward", "reverse"};
      String[] var10001 = var2;
      fractions$py var10007 = self;
      var2 = new String[]{"fallback_operator", "monomorphic_operator"};
      _operator_fallbacks$11 = Py.newCode(2, var10001, var1, "_operator_fallbacks", 280, false, false, var10007, 11, var2, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"monomorphic_operator", "fallback_operator"};
      forward$12 = Py.newCode(2, var10001, var1, "forward", 360, false, false, var10007, 12, (String[])null, var2, 0, 12289);
      var2 = new String[]{"b", "a"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"monomorphic_operator", "fallback_operator"};
      reverse$13 = Py.newCode(2, var10001, var1, "reverse", 372, false, false, var10007, 13, (String[])null, var2, 0, 12289);
      var2 = new String[]{"a", "b"};
      _add$14 = Py.newCode(2, var2, var1, "_add", 387, false, false, self, 14, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      _sub$15 = Py.newCode(2, var2, var1, "_sub", 395, false, false, self, 15, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      _mul$16 = Py.newCode(2, var2, var1, "_mul", 403, false, false, self, 16, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      _div$17 = Py.newCode(2, var2, var1, "_div", 409, false, false, self, 17, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b", "div"};
      __floordiv__$18 = Py.newCode(2, var2, var1, "__floordiv__", 417, false, false, self, 18, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"b", "a", "div"};
      __rfloordiv__$19 = Py.newCode(2, var2, var1, "__rfloordiv__", 429, false, false, self, 19, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b", "div"};
      __mod__$20 = Py.newCode(2, var2, var1, "__mod__", 441, false, false, self, 20, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"b", "a", "div"};
      __rmod__$21 = Py.newCode(2, var2, var1, "__rmod__", 446, false, false, self, 21, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b", "power"};
      __pow__$22 = Py.newCode(2, var2, var1, "__pow__", 451, false, false, self, 22, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"b", "a"};
      __rpow__$23 = Py.newCode(2, var2, var1, "__rpow__", 475, false, false, self, 23, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      __pos__$24 = Py.newCode(1, var2, var1, "__pos__", 489, false, false, self, 24, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      __neg__$25 = Py.newCode(1, var2, var1, "__neg__", 493, false, false, self, 25, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      __abs__$26 = Py.newCode(1, var2, var1, "__abs__", 497, false, false, self, 26, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      __trunc__$27 = Py.newCode(1, var2, var1, "__trunc__", 501, false, false, self, 27, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __hash__$28 = Py.newCode(1, var2, var1, "__hash__", 508, false, false, self, 28, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      __eq__$29 = Py.newCode(2, var2, var1, "__eq__", 527, false, false, self, 29, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "op"};
      _richcmp$30 = Py.newCode(3, var2, var1, "_richcmp", 546, false, false, self, 30, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      __lt__$31 = Py.newCode(2, var2, var1, "__lt__", 572, false, false, self, 31, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      __gt__$32 = Py.newCode(2, var2, var1, "__gt__", 576, false, false, self, 32, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      __le__$33 = Py.newCode(2, var2, var1, "__le__", 580, false, false, self, 33, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a", "b"};
      __ge__$34 = Py.newCode(2, var2, var1, "__ge__", 584, false, false, self, 34, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"a"};
      __nonzero__$35 = Py.newCode(1, var2, var1, "__nonzero__", 588, false, false, self, 35, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$36 = Py.newCode(1, var2, var1, "__reduce__", 594, false, false, self, 36, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __copy__$37 = Py.newCode(1, var2, var1, "__copy__", 597, false, false, self, 37, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "memo"};
      __deepcopy__$38 = Py.newCode(2, var2, var1, "__deepcopy__", 602, false, false, self, 38, (String[])null, (String[])null, 0, 12289);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fractions$py("fractions$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fractions$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.gcd$1(var2, var3);
         case 2:
            return this.Fraction$2(var2, var3);
         case 3:
            return this.__new__$3(var2, var3);
         case 4:
            return this.from_float$4(var2, var3);
         case 5:
            return this.from_decimal$5(var2, var3);
         case 6:
            return this.limit_denominator$6(var2, var3);
         case 7:
            return this.numerator$7(var2, var3);
         case 8:
            return this.denominator$8(var2, var3);
         case 9:
            return this.__repr__$9(var2, var3);
         case 10:
            return this.__str__$10(var2, var3);
         case 11:
            return this._operator_fallbacks$11(var2, var3);
         case 12:
            return this.forward$12(var2, var3);
         case 13:
            return this.reverse$13(var2, var3);
         case 14:
            return this._add$14(var2, var3);
         case 15:
            return this._sub$15(var2, var3);
         case 16:
            return this._mul$16(var2, var3);
         case 17:
            return this._div$17(var2, var3);
         case 18:
            return this.__floordiv__$18(var2, var3);
         case 19:
            return this.__rfloordiv__$19(var2, var3);
         case 20:
            return this.__mod__$20(var2, var3);
         case 21:
            return this.__rmod__$21(var2, var3);
         case 22:
            return this.__pow__$22(var2, var3);
         case 23:
            return this.__rpow__$23(var2, var3);
         case 24:
            return this.__pos__$24(var2, var3);
         case 25:
            return this.__neg__$25(var2, var3);
         case 26:
            return this.__abs__$26(var2, var3);
         case 27:
            return this.__trunc__$27(var2, var3);
         case 28:
            return this.__hash__$28(var2, var3);
         case 29:
            return this.__eq__$29(var2, var3);
         case 30:
            return this._richcmp$30(var2, var3);
         case 31:
            return this.__lt__$31(var2, var3);
         case 32:
            return this.__gt__$32(var2, var3);
         case 33:
            return this.__le__$33(var2, var3);
         case 34:
            return this.__ge__$34(var2, var3);
         case 35:
            return this.__nonzero__$35(var2, var3);
         case 36:
            return this.__reduce__$36(var2, var3);
         case 37:
            return this.__copy__$37(var2, var3);
         case 38:
            return this.__deepcopy__$38(var2, var3);
         default:
            return null;
      }
   }
}

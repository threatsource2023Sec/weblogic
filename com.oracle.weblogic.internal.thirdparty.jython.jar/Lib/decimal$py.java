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
@Filename("decimal.py")
public class decimal$py extends PyFunctionTable implements PyRunnable {
   static decimal$py self;
   static final PyCode f$0;
   static final PyCode f$1;
   static final PyCode DecimalException$2;
   static final PyCode handle$3;
   static final PyCode Clamped$4;
   static final PyCode InvalidOperation$5;
   static final PyCode handle$6;
   static final PyCode ConversionSyntax$7;
   static final PyCode handle$8;
   static final PyCode DivisionByZero$9;
   static final PyCode handle$10;
   static final PyCode DivisionImpossible$11;
   static final PyCode handle$12;
   static final PyCode DivisionUndefined$13;
   static final PyCode handle$14;
   static final PyCode Inexact$15;
   static final PyCode InvalidContext$16;
   static final PyCode handle$17;
   static final PyCode Rounded$18;
   static final PyCode Subnormal$19;
   static final PyCode Overflow$20;
   static final PyCode handle$21;
   static final PyCode Underflow$22;
   static final PyCode MockThreading$23;
   static final PyCode local$24;
   static final PyCode setcontext$25;
   static final PyCode getcontext$26;
   static final PyCode getcontext$27;
   static final PyCode setcontext$28;
   static final PyCode localcontext$29;
   static final PyCode Decimal$30;
   static final PyCode __new__$31;
   static final PyCode from_float$32;
   static final PyCode _isnan$33;
   static final PyCode _isinfinity$34;
   static final PyCode _check_nans$35;
   static final PyCode _compare_check_nans$36;
   static final PyCode __nonzero__$37;
   static final PyCode _cmp$38;
   static final PyCode __eq__$39;
   static final PyCode __ne__$40;
   static final PyCode __lt__$41;
   static final PyCode __le__$42;
   static final PyCode __gt__$43;
   static final PyCode __ge__$44;
   static final PyCode compare$45;
   static final PyCode __hash__$46;
   static final PyCode as_tuple$47;
   static final PyCode __repr__$48;
   static final PyCode __str__$49;
   static final PyCode to_eng_string$50;
   static final PyCode __neg__$51;
   static final PyCode __pos__$52;
   static final PyCode __abs__$53;
   static final PyCode __add__$54;
   static final PyCode __sub__$55;
   static final PyCode __rsub__$56;
   static final PyCode __mul__$57;
   static final PyCode __truediv__$58;
   static final PyCode _divide$59;
   static final PyCode __rtruediv__$60;
   static final PyCode __divmod__$61;
   static final PyCode __rdivmod__$62;
   static final PyCode __mod__$63;
   static final PyCode __rmod__$64;
   static final PyCode remainder_near$65;
   static final PyCode __floordiv__$66;
   static final PyCode __rfloordiv__$67;
   static final PyCode __float__$68;
   static final PyCode __int__$69;
   static final PyCode real$70;
   static final PyCode imag$71;
   static final PyCode conjugate$72;
   static final PyCode __complex__$73;
   static final PyCode __long__$74;
   static final PyCode _fix_nan$75;
   static final PyCode _fix$76;
   static final PyCode _round_down$77;
   static final PyCode _round_up$78;
   static final PyCode _round_half_up$79;
   static final PyCode _round_half_down$80;
   static final PyCode _round_half_even$81;
   static final PyCode _round_ceiling$82;
   static final PyCode _round_floor$83;
   static final PyCode _round_05up$84;
   static final PyCode fma$85;
   static final PyCode _power_modulo$86;
   static final PyCode _power_exact$87;
   static final PyCode __pow__$88;
   static final PyCode __rpow__$89;
   static final PyCode normalize$90;
   static final PyCode quantize$91;
   static final PyCode same_quantum$92;
   static final PyCode _rescale$93;
   static final PyCode _round$94;
   static final PyCode to_integral_exact$95;
   static final PyCode to_integral_value$96;
   static final PyCode sqrt$97;
   static final PyCode max$98;
   static final PyCode min$99;
   static final PyCode _isinteger$100;
   static final PyCode _iseven$101;
   static final PyCode adjusted$102;
   static final PyCode canonical$103;
   static final PyCode compare_signal$104;
   static final PyCode compare_total$105;
   static final PyCode compare_total_mag$106;
   static final PyCode copy_abs$107;
   static final PyCode copy_negate$108;
   static final PyCode copy_sign$109;
   static final PyCode exp$110;
   static final PyCode is_canonical$111;
   static final PyCode is_finite$112;
   static final PyCode is_infinite$113;
   static final PyCode is_nan$114;
   static final PyCode is_normal$115;
   static final PyCode is_qnan$116;
   static final PyCode is_signed$117;
   static final PyCode is_snan$118;
   static final PyCode is_subnormal$119;
   static final PyCode is_zero$120;
   static final PyCode _ln_exp_bound$121;
   static final PyCode ln$122;
   static final PyCode _log10_exp_bound$123;
   static final PyCode log10$124;
   static final PyCode logb$125;
   static final PyCode _islogical$126;
   static final PyCode _fill_logical$127;
   static final PyCode logical_and$128;
   static final PyCode logical_invert$129;
   static final PyCode logical_or$130;
   static final PyCode logical_xor$131;
   static final PyCode max_mag$132;
   static final PyCode min_mag$133;
   static final PyCode next_minus$134;
   static final PyCode next_plus$135;
   static final PyCode next_toward$136;
   static final PyCode number_class$137;
   static final PyCode radix$138;
   static final PyCode rotate$139;
   static final PyCode scaleb$140;
   static final PyCode shift$141;
   static final PyCode __reduce__$142;
   static final PyCode __copy__$143;
   static final PyCode __deepcopy__$144;
   static final PyCode __format__$145;
   static final PyCode __tojava__$146;
   static final PyCode _dec_from_triple$147;
   static final PyCode _ContextManager$148;
   static final PyCode __init__$149;
   static final PyCode __enter__$150;
   static final PyCode __exit__$151;
   static final PyCode Context$152;
   static final PyCode __init__$153;
   static final PyCode f$154;
   static final PyCode f$155;
   static final PyCode __repr__$156;
   static final PyCode clear_flags$157;
   static final PyCode _shallow_copy$158;
   static final PyCode copy$159;
   static final PyCode _raise_error$160;
   static final PyCode _ignore_all_flags$161;
   static final PyCode _ignore_flags$162;
   static final PyCode _regard_flags$163;
   static final PyCode Etiny$164;
   static final PyCode Etop$165;
   static final PyCode _set_rounding$166;
   static final PyCode create_decimal$167;
   static final PyCode create_decimal_from_float$168;
   static final PyCode abs$169;
   static final PyCode add$170;
   static final PyCode _apply$171;
   static final PyCode canonical$172;
   static final PyCode compare$173;
   static final PyCode compare_signal$174;
   static final PyCode compare_total$175;
   static final PyCode compare_total_mag$176;
   static final PyCode copy_abs$177;
   static final PyCode copy_decimal$178;
   static final PyCode copy_negate$179;
   static final PyCode copy_sign$180;
   static final PyCode divide$181;
   static final PyCode divide_int$182;
   static final PyCode divmod$183;
   static final PyCode exp$184;
   static final PyCode fma$185;
   static final PyCode is_canonical$186;
   static final PyCode is_finite$187;
   static final PyCode is_infinite$188;
   static final PyCode is_nan$189;
   static final PyCode is_normal$190;
   static final PyCode is_qnan$191;
   static final PyCode is_signed$192;
   static final PyCode is_snan$193;
   static final PyCode is_subnormal$194;
   static final PyCode is_zero$195;
   static final PyCode ln$196;
   static final PyCode log10$197;
   static final PyCode logb$198;
   static final PyCode logical_and$199;
   static final PyCode logical_invert$200;
   static final PyCode logical_or$201;
   static final PyCode logical_xor$202;
   static final PyCode max$203;
   static final PyCode max_mag$204;
   static final PyCode min$205;
   static final PyCode min_mag$206;
   static final PyCode minus$207;
   static final PyCode multiply$208;
   static final PyCode next_minus$209;
   static final PyCode next_plus$210;
   static final PyCode next_toward$211;
   static final PyCode normalize$212;
   static final PyCode number_class$213;
   static final PyCode plus$214;
   static final PyCode power$215;
   static final PyCode quantize$216;
   static final PyCode radix$217;
   static final PyCode remainder$218;
   static final PyCode remainder_near$219;
   static final PyCode rotate$220;
   static final PyCode same_quantum$221;
   static final PyCode scaleb$222;
   static final PyCode shift$223;
   static final PyCode sqrt$224;
   static final PyCode subtract$225;
   static final PyCode to_eng_string$226;
   static final PyCode to_sci_string$227;
   static final PyCode to_integral_exact$228;
   static final PyCode to_integral_value$229;
   static final PyCode _WorkRep$230;
   static final PyCode __init__$231;
   static final PyCode __repr__$232;
   static final PyCode _normalize$233;
   static final PyCode _nbits$234;
   static final PyCode _sqrt_nearest$235;
   static final PyCode _rshift_nearest$236;
   static final PyCode _div_nearest$237;
   static final PyCode _ilog$238;
   static final PyCode _dlog10$239;
   static final PyCode _dlog$240;
   static final PyCode _Log10Memoize$241;
   static final PyCode __init__$242;
   static final PyCode getdigits$243;
   static final PyCode _iexp$244;
   static final PyCode _dexp$245;
   static final PyCode _dpower$246;
   static final PyCode _log10_lb$247;
   static final PyCode _convert_other$248;
   static final PyCode _parse_format_specifier$249;
   static final PyCode _format_align$250;
   static final PyCode _group_lengths$251;
   static final PyCode _insert_thousands_sep$252;
   static final PyCode _format_sign$253;
   static final PyCode _format_number$254;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThis is a Py2.3 implementation of decimal floating point arithmetic based on\nthe General Decimal Arithmetic Specification:\n\n    www2.hursley.ibm.com/decimal/decarith.html\n\nand IEEE standard 854-1987:\n\n    www.cs.berkeley.edu/~ejr/projects/754/private/drafts/854-1987/dir.html\n\nDecimal floating point has finite precision with arbitrarily large bounds.\n\nThe purpose of this module is to support arithmetic using familiar\n\"schoolhouse\" rules and to avoid some of the tricky representation\nissues associated with binary floating point.  The package is especially\nuseful for financial applications or for contexts where users have\nexpectations that are at odds with binary floating point (for instance,\nin binary floating point, 1.00 % 0.1 gives 0.09999999999999995 instead\nof the expected Decimal('0.00') returned by decimal floating point).\n\nHere are some examples of using the decimal module:\n\n>>> from decimal import *\n>>> setcontext(ExtendedContext)\n>>> Decimal(0)\nDecimal('0')\n>>> Decimal('1')\nDecimal('1')\n>>> Decimal('-.0123')\nDecimal('-0.0123')\n>>> Decimal(123456)\nDecimal('123456')\n>>> Decimal('123.45e12345678901234567890')\nDecimal('1.2345E+12345678901234567892')\n>>> Decimal('1.33') + Decimal('1.27')\nDecimal('2.60')\n>>> Decimal('12.34') + Decimal('3.87') - Decimal('18.41')\nDecimal('-2.20')\n>>> dig = Decimal(1)\n>>> print dig / Decimal(3)\n0.333333333\n>>> getcontext().prec = 18\n>>> print dig / Decimal(3)\n0.333333333333333333\n>>> print dig.sqrt()\n1\n>>> print Decimal(3).sqrt()\n1.73205080756887729\n>>> print Decimal(3) ** 123\n4.85192780976896427E+58\n>>> inf = Decimal(1) / Decimal(0)\n>>> print inf\nInfinity\n>>> neginf = Decimal(-1) / Decimal(0)\n>>> print neginf\n-Infinity\n>>> print neginf + inf\nNaN\n>>> print neginf * inf\n-Infinity\n>>> print dig / 0\nInfinity\n>>> getcontext().traps[DivisionByZero] = 1\n>>> print dig / 0\nTraceback (most recent call last):\n  ...\n  ...\n  ...\nDivisionByZero: x / 0\n>>> c = Context()\n>>> c.traps[InvalidOperation] = 0\n>>> print c.flags[InvalidOperation]\n0\n>>> c.divide(Decimal(0), Decimal(0))\nDecimal('NaN')\n>>> c.traps[InvalidOperation] = 1\n>>> print c.flags[InvalidOperation]\n1\n>>> c.flags[InvalidOperation] = 0\n>>> print c.flags[InvalidOperation]\n0\n>>> print c.divide(Decimal(0), Decimal(0))\nTraceback (most recent call last):\n  ...\n  ...\n  ...\nInvalidOperation: 0 / 0\n>>> print c.flags[InvalidOperation]\n1\n>>> c.flags[InvalidOperation] = 0\n>>> c.traps[InvalidOperation] = 0\n>>> print c.divide(Decimal(0), Decimal(0))\nNaN\n>>> print c.flags[InvalidOperation]\n1\n>>>\n"));
      var1.setline(116);
      PyString.fromInterned("\nThis is a Py2.3 implementation of decimal floating point arithmetic based on\nthe General Decimal Arithmetic Specification:\n\n    www2.hursley.ibm.com/decimal/decarith.html\n\nand IEEE standard 854-1987:\n\n    www.cs.berkeley.edu/~ejr/projects/754/private/drafts/854-1987/dir.html\n\nDecimal floating point has finite precision with arbitrarily large bounds.\n\nThe purpose of this module is to support arithmetic using familiar\n\"schoolhouse\" rules and to avoid some of the tricky representation\nissues associated with binary floating point.  The package is especially\nuseful for financial applications or for contexts where users have\nexpectations that are at odds with binary floating point (for instance,\nin binary floating point, 1.00 % 0.1 gives 0.09999999999999995 instead\nof the expected Decimal('0.00') returned by decimal floating point).\n\nHere are some examples of using the decimal module:\n\n>>> from decimal import *\n>>> setcontext(ExtendedContext)\n>>> Decimal(0)\nDecimal('0')\n>>> Decimal('1')\nDecimal('1')\n>>> Decimal('-.0123')\nDecimal('-0.0123')\n>>> Decimal(123456)\nDecimal('123456')\n>>> Decimal('123.45e12345678901234567890')\nDecimal('1.2345E+12345678901234567892')\n>>> Decimal('1.33') + Decimal('1.27')\nDecimal('2.60')\n>>> Decimal('12.34') + Decimal('3.87') - Decimal('18.41')\nDecimal('-2.20')\n>>> dig = Decimal(1)\n>>> print dig / Decimal(3)\n0.333333333\n>>> getcontext().prec = 18\n>>> print dig / Decimal(3)\n0.333333333333333333\n>>> print dig.sqrt()\n1\n>>> print Decimal(3).sqrt()\n1.73205080756887729\n>>> print Decimal(3) ** 123\n4.85192780976896427E+58\n>>> inf = Decimal(1) / Decimal(0)\n>>> print inf\nInfinity\n>>> neginf = Decimal(-1) / Decimal(0)\n>>> print neginf\n-Infinity\n>>> print neginf + inf\nNaN\n>>> print neginf * inf\n-Infinity\n>>> print dig / 0\nInfinity\n>>> getcontext().traps[DivisionByZero] = 1\n>>> print dig / 0\nTraceback (most recent call last):\n  ...\n  ...\n  ...\nDivisionByZero: x / 0\n>>> c = Context()\n>>> c.traps[InvalidOperation] = 0\n>>> print c.flags[InvalidOperation]\n0\n>>> c.divide(Decimal(0), Decimal(0))\nDecimal('NaN')\n>>> c.traps[InvalidOperation] = 1\n>>> print c.flags[InvalidOperation]\n1\n>>> c.flags[InvalidOperation] = 0\n>>> print c.flags[InvalidOperation]\n0\n>>> print c.divide(Decimal(0), Decimal(0))\nTraceback (most recent call last):\n  ...\n  ...\n  ...\nInvalidOperation: 0 / 0\n>>> print c.flags[InvalidOperation]\n1\n>>> c.flags[InvalidOperation] = 0\n>>> c.traps[InvalidOperation] = 0\n>>> print c.divide(Decimal(0), Decimal(0))\nNaN\n>>> print c.flags[InvalidOperation]\n1\n>>>\n");
      var1.setline(118);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Decimal"), PyString.fromInterned("Context"), PyString.fromInterned("DefaultContext"), PyString.fromInterned("BasicContext"), PyString.fromInterned("ExtendedContext"), PyString.fromInterned("DecimalException"), PyString.fromInterned("Clamped"), PyString.fromInterned("InvalidOperation"), PyString.fromInterned("DivisionByZero"), PyString.fromInterned("Inexact"), PyString.fromInterned("Rounded"), PyString.fromInterned("Subnormal"), PyString.fromInterned("Overflow"), PyString.fromInterned("Underflow"), PyString.fromInterned("ROUND_DOWN"), PyString.fromInterned("ROUND_HALF_UP"), PyString.fromInterned("ROUND_HALF_EVEN"), PyString.fromInterned("ROUND_CEILING"), PyString.fromInterned("ROUND_FLOOR"), PyString.fromInterned("ROUND_UP"), PyString.fromInterned("ROUND_HALF_DOWN"), PyString.fromInterned("ROUND_05UP"), PyString.fromInterned("setcontext"), PyString.fromInterned("getcontext"), PyString.fromInterned("localcontext")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(137);
      PyString var11 = PyString.fromInterned("1.70");
      var1.setlocal("__version__", var11);
      var3 = null;
      var1.setline(139);
      PyObject var12 = imp.importOneAs("copy", var1, -1);
      var1.setlocal("_copy", var12);
      var3 = null;
      var1.setline(140);
      var12 = imp.importOneAs("math", var1, -1);
      var1.setlocal("_math", var12);
      var3 = null;
      var1.setline(141);
      var12 = imp.importOneAs("numbers", var1, -1);
      var1.setlocal("_numbers", var12);
      var3 = null;

      PyObject[] var4;
      PyFunction var13;
      PyObject var14;
      PyException var15;
      String[] var16;
      PyObject[] var17;
      try {
         var1.setline(144);
         var16 = new String[]{"namedtuple"};
         var17 = imp.importFrom("collections", var16, var1, -1);
         var14 = var17[0];
         var1.setlocal("_namedtuple", var14);
         var4 = null;
         var1.setline(145);
         var12 = var1.getname("_namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DecimalTuple"), (PyObject)PyString.fromInterned("sign digits exponent"));
         var1.setlocal("DecimalTuple", var12);
         var3 = null;
      } catch (Throwable var10) {
         var15 = Py.setException(var10, var1);
         if (!var15.match(var1.getname("ImportError"))) {
            throw var15;
         }

         var1.setline(147);
         var1.setline(147);
         var4 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var4, f$1);
         var1.setlocal("DecimalTuple", var13);
         var4 = null;
      }

      var1.setline(150);
      var11 = PyString.fromInterned("ROUND_DOWN");
      var1.setlocal("ROUND_DOWN", var11);
      var3 = null;
      var1.setline(151);
      var11 = PyString.fromInterned("ROUND_HALF_UP");
      var1.setlocal("ROUND_HALF_UP", var11);
      var3 = null;
      var1.setline(152);
      var11 = PyString.fromInterned("ROUND_HALF_EVEN");
      var1.setlocal("ROUND_HALF_EVEN", var11);
      var3 = null;
      var1.setline(153);
      var11 = PyString.fromInterned("ROUND_CEILING");
      var1.setlocal("ROUND_CEILING", var11);
      var3 = null;
      var1.setline(154);
      var11 = PyString.fromInterned("ROUND_FLOOR");
      var1.setlocal("ROUND_FLOOR", var11);
      var3 = null;
      var1.setline(155);
      var11 = PyString.fromInterned("ROUND_UP");
      var1.setlocal("ROUND_UP", var11);
      var3 = null;
      var1.setline(156);
      var11 = PyString.fromInterned("ROUND_HALF_DOWN");
      var1.setlocal("ROUND_HALF_DOWN", var11);
      var3 = null;
      var1.setline(157);
      var11 = PyString.fromInterned("ROUND_05UP");
      var1.setlocal("ROUND_05UP", var11);
      var3 = null;
      var1.setline(161);
      var17 = new PyObject[]{var1.getname("ArithmeticError")};
      var14 = Py.makeClass("DecimalException", var17, DecimalException$2);
      var1.setlocal("DecimalException", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(184);
      var17 = new PyObject[]{var1.getname("DecimalException")};
      var14 = Py.makeClass("Clamped", var17, Clamped$4);
      var1.setlocal("Clamped", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(196);
      var17 = new PyObject[]{var1.getname("DecimalException")};
      var14 = Py.makeClass("InvalidOperation", var17, InvalidOperation$5);
      var1.setlocal("InvalidOperation", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(225);
      var17 = new PyObject[]{var1.getname("InvalidOperation")};
      var14 = Py.makeClass("ConversionSyntax", var17, ConversionSyntax$7);
      var1.setlocal("ConversionSyntax", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(235);
      var17 = new PyObject[]{var1.getname("DecimalException"), var1.getname("ZeroDivisionError")};
      var14 = Py.makeClass("DivisionByZero", var17, DivisionByZero$9);
      var1.setlocal("DivisionByZero", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(251);
      var17 = new PyObject[]{var1.getname("InvalidOperation")};
      var14 = Py.makeClass("DivisionImpossible", var17, DivisionImpossible$11);
      var1.setlocal("DivisionImpossible", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(262);
      var17 = new PyObject[]{var1.getname("InvalidOperation"), var1.getname("ZeroDivisionError")};
      var14 = Py.makeClass("DivisionUndefined", var17, DivisionUndefined$13);
      var1.setlocal("DivisionUndefined", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(273);
      var17 = new PyObject[]{var1.getname("DecimalException")};
      var14 = Py.makeClass("Inexact", var17, Inexact$15);
      var1.setlocal("Inexact", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(285);
      var17 = new PyObject[]{var1.getname("InvalidOperation")};
      var14 = Py.makeClass("InvalidContext", var17, InvalidContext$16);
      var1.setlocal("InvalidContext", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(299);
      var17 = new PyObject[]{var1.getname("DecimalException")};
      var14 = Py.makeClass("Rounded", var17, Rounded$18);
      var1.setlocal("Rounded", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(311);
      var17 = new PyObject[]{var1.getname("DecimalException")};
      var14 = Py.makeClass("Subnormal", var17, Subnormal$19);
      var1.setlocal("Subnormal", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(322);
      var17 = new PyObject[]{var1.getname("Inexact"), var1.getname("Rounded")};
      var14 = Py.makeClass("Overflow", var17, Overflow$20);
      var1.setlocal("Overflow", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(360);
      var17 = new PyObject[]{var1.getname("Inexact"), var1.getname("Rounded"), var1.getname("Subnormal")};
      var14 = Py.makeClass("Underflow", var17, Underflow$22);
      var1.setlocal("Underflow", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(376);
      var3 = new PyList(new PyObject[]{var1.getname("Clamped"), var1.getname("DivisionByZero"), var1.getname("Inexact"), var1.getname("Overflow"), var1.getname("Rounded"), var1.getname("Underflow"), var1.getname("InvalidOperation"), var1.getname("Subnormal")});
      var1.setlocal("_signals", var3);
      var3 = null;
      var1.setline(380);
      PyDictionary var18 = new PyDictionary(new PyObject[]{var1.getname("ConversionSyntax"), var1.getname("InvalidOperation"), var1.getname("DivisionImpossible"), var1.getname("InvalidOperation"), var1.getname("DivisionUndefined"), var1.getname("InvalidOperation"), var1.getname("InvalidContext"), var1.getname("InvalidOperation")});
      var1.setlocal("_condition_map", var18);
      var3 = null;

      PyObject var5;
      try {
         var1.setline(394);
         var12 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var12);
         var3 = null;
      } catch (Throwable var9) {
         var15 = Py.setException(var9, var1);
         if (!var15.match(var1.getname("ImportError"))) {
            throw var15;
         }

         var1.setline(397);
         var14 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var14);
         var4 = null;
         var1.setline(398);
         var4 = new PyObject[]{var1.getname("object")};
         var5 = Py.makeClass("MockThreading", var4, MockThreading$23);
         var1.setlocal("MockThreading", var5);
         var5 = null;
         Arrays.fill(var4, (Object)null);
         var1.setline(401);
         var14 = var1.getname("MockThreading").__call__(var2);
         var1.setlocal("threading", var14);
         var4 = null;
         var1.setline(402);
         var1.dellocal("sys");
         var1.dellocal("MockThreading");
      }

      try {
         var1.setline(405);
         var16 = new String[]{"Object", "Float", "Double"};
         var17 = imp.importFrom("java.lang", var16, var1, -1);
         var14 = var17[0];
         var1.setlocal("Object", var14);
         var4 = null;
         var14 = var17[1];
         var1.setlocal("Float", var14);
         var4 = null;
         var14 = var17[2];
         var1.setlocal("Double", var14);
         var4 = null;
         var1.setline(406);
         var16 = new String[]{"BigDecimal"};
         var17 = imp.importFrom("java.math", var16, var1, -1);
         var14 = var17[0];
         var1.setlocal("BigDecimal", var14);
         var4 = null;
         var1.setline(407);
         var16 = new String[]{"Py"};
         var17 = imp.importFrom("org.python.core", var16, var1, -1);
         var14 = var17[0];
         var1.setlocal("Py", var14);
         var4 = null;
      } catch (Throwable var8) {
         var15 = Py.setException(var8, var1);
         if (!var15.match(var1.getname("ImportError"))) {
            throw var15;
         }

         var1.setline(410);
      }

      label107: {
         try {
            var1.setline(413);
            var1.getname("threading").__getattr__("local");
         } catch (Throwable var7) {
            var15 = Py.setException(var7, var1);
            if (var15.match(var1.getname("AttributeError"))) {
               var1.setline(419);
               if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("threading").__getattr__("currentThread").__call__(var2), (PyObject)PyString.fromInterned("__decimal_context__")).__nonzero__()) {
                  var1.setline(420);
                  var1.getname("threading").__getattr__("currentThread").__call__(var2).__delattr__("__decimal_context__");
               }

               var1.setline(422);
               var4 = Py.EmptyObjects;
               var13 = new PyFunction(var1.f_globals, var4, setcontext$25, PyString.fromInterned("Set this thread's context to context."));
               var1.setlocal("setcontext", var13);
               var4 = null;
               var1.setline(429);
               var4 = Py.EmptyObjects;
               var13 = new PyFunction(var1.f_globals, var4, getcontext$26, PyString.fromInterned("Returns this thread's context.\n\n        If this thread does not yet have a context, returns\n        a new context and sets this thread's context.\n        New contexts are copies of DefaultContext.\n        "));
               var1.setlocal("getcontext", var13);
               var4 = null;
               break label107;
            }

            throw var15;
         }

         var1.setline(445);
         var14 = var1.getname("threading").__getattr__("local").__call__(var2);
         var1.setlocal("local", var14);
         var4 = null;
         var1.setline(446);
         if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("local"), (PyObject)PyString.fromInterned("__decimal_context__")).__nonzero__()) {
            var1.setline(447);
            var1.getname("local").__delattr__("__decimal_context__");
         }

         var1.setline(449);
         var4 = new PyObject[]{var1.getname("local")};
         var13 = new PyFunction(var1.f_globals, var4, getcontext$27, PyString.fromInterned("Returns this thread's context.\n\n        If this thread does not yet have a context, returns\n        a new context and sets this thread's context.\n        New contexts are copies of DefaultContext.\n        "));
         var1.setlocal("getcontext", var13);
         var4 = null;
         var1.setline(463);
         var4 = new PyObject[]{var1.getname("local")};
         var13 = new PyFunction(var1.f_globals, var4, setcontext$28, PyString.fromInterned("Set this thread's context to context."));
         var1.setlocal("setcontext", var13);
         var4 = null;
         var1.setline(470);
         var1.dellocal("threading");
         var1.dellocal("local");
      }

      var1.setline(472);
      var17 = new PyObject[]{var1.getname("None")};
      PyFunction var19 = new PyFunction(var1.f_globals, var17, localcontext$29, PyString.fromInterned("Return a context manager for a copy of the supplied context\n\n    Uses a copy of the current context if no context is specified\n    The returned context manager creates a local decimal context\n    in a with statement:\n        def sin(x):\n             with localcontext() as ctx:\n                 ctx.prec += 2\n                 # Rest of sin calculation algorithm\n                 # uses a precision 2 greater than normal\n             return +s  # Convert result to normal precision\n\n         def sin(x):\n             with localcontext(ExtendedContext):\n                 # Rest of sin calculation algorithm\n                 # uses the Extended Context from the\n                 # General Decimal Arithmetic Specification\n             return +s  # Convert result to normal context\n\n    >>> setcontext(DefaultContext)\n    >>> print getcontext().prec\n    28\n    >>> with localcontext():\n    ...     ctx = getcontext()\n    ...     ctx.prec += 2\n    ...     print ctx.prec\n    ...\n    30\n    >>> with localcontext(ExtendedContext):\n    ...     print getcontext().prec\n    ...\n    9\n    >>> print getcontext().prec\n    28\n    "));
      var1.setlocal("localcontext", var19);
      var3 = null;
      var1.setline(514);
      var17 = new PyObject[]{var1.getname("object")};
      var14 = Py.makeClass("Decimal", var17, Decimal$30);
      var1.setlocal("Decimal", var14);
      var4 = null;
      Arrays.fill(var17, (Object)null);
      var1.setline(3702);
      var17 = new PyObject[]{var1.getname("False")};
      var19 = new PyFunction(var1.f_globals, var17, _dec_from_triple$147, PyString.fromInterned("Create a decimal instance directly, without any validation,\n    normalization (e.g. removal of leading zeros) or argument\n    conversion.\n\n    This function is for *internal use only*.\n    "));
      var1.setlocal("_dec_from_triple", var19);
      var3 = null;
      var1.setline(3721);
      var1.getname("_numbers").__getattr__("Number").__getattr__("register").__call__(var2, var1.getname("Decimal"));
      var1.setline(3728);
      PyList var10000 = new PyList();
      var12 = var10000.__getattr__("append");
      var1.setlocal("_[3728_22]", var12);
      var3 = null;
      var1.setline(3728);
      var12 = var1.getname("Decimal").__getattr__("__dict__").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(3728);
         var14 = var12.__iternext__();
         if (var14 == null) {
            var1.setline(3728);
            var1.dellocal("_[3728_22]");
            var3 = var10000;
            var1.setlocal("rounding_functions", var3);
            var3 = null;
            var1.setline(3730);
            var12 = var1.getname("rounding_functions").__iter__();

            while(true) {
               var1.setline(3730);
               var14 = var12.__iternext__();
               if (var14 == null) {
                  var1.setline(3736);
                  var1.dellocal("name");
                  var1.dellocal("val");
                  var1.dellocal("globalname");
                  var1.dellocal("rounding_functions");
                  var1.setline(3738);
                  var17 = new PyObject[]{var1.getname("object")};
                  var14 = Py.makeClass("_ContextManager", var17, _ContextManager$148);
                  var1.setlocal("_ContextManager", var14);
                  var4 = null;
                  Arrays.fill(var17, (Object)null);
                  var1.setline(3753);
                  var17 = new PyObject[]{var1.getname("object")};
                  var14 = Py.makeClass("Context", var17, Context$152);
                  var1.setlocal("Context", var14);
                  var4 = null;
                  Arrays.fill(var17, (Object)null);
                  var1.setline(5408);
                  var17 = new PyObject[]{var1.getname("object")};
                  var14 = Py.makeClass("_WorkRep", var17, _WorkRep$230);
                  var1.setlocal("_WorkRep", var14);
                  var4 = null;
                  Arrays.fill(var17, (Object)null);
                  var1.setline(5436);
                  var17 = new PyObject[]{Py.newInteger(0)};
                  var19 = new PyFunction(var1.f_globals, var17, _normalize$233, PyString.fromInterned("Normalizes op1, op2 to have the same exp and length of coefficient.\n\n    Done during addition.\n    "));
                  var1.setlocal("_normalize", var19);
                  var3 = null;
                  var1.setline(5471);
                  var17 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("0"), Py.newInteger(4), PyString.fromInterned("1"), Py.newInteger(3), PyString.fromInterned("2"), Py.newInteger(2), PyString.fromInterned("3"), Py.newInteger(2), PyString.fromInterned("4"), Py.newInteger(1), PyString.fromInterned("5"), Py.newInteger(1), PyString.fromInterned("6"), Py.newInteger(1), PyString.fromInterned("7"), Py.newInteger(1), PyString.fromInterned("8"), Py.newInteger(0), PyString.fromInterned("9"), Py.newInteger(0), PyString.fromInterned("a"), Py.newInteger(0), PyString.fromInterned("b"), Py.newInteger(0), PyString.fromInterned("c"), Py.newInteger(0), PyString.fromInterned("d"), Py.newInteger(0), PyString.fromInterned("e"), Py.newInteger(0), PyString.fromInterned("f"), Py.newInteger(0)})};
                  var19 = new PyFunction(var1.f_globals, var17, _nbits$234, PyString.fromInterned("Number of bits in binary representation of the positive integer n,\n    or 0 if n == 0.\n    "));
                  var1.setlocal("_nbits", var19);
                  var3 = null;
                  var1.setline(5484);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _sqrt_nearest$235, PyString.fromInterned("Closest integer to the square root of the positive integer n.  a is\n    an initial approximation to the square root.  Any positive integer\n    will do for a, but the closer a is to the square root of n the\n    faster convergence will be.\n\n    "));
                  var1.setlocal("_sqrt_nearest", var19);
                  var3 = null;
                  var1.setline(5499);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _rshift_nearest$236, PyString.fromInterned("Given an integer x and a nonnegative integer shift, return closest\n    integer to x / 2**shift; use round-to-even in case of a tie.\n\n    "));
                  var1.setlocal("_rshift_nearest", var19);
                  var3 = null;
                  var1.setline(5507);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _div_nearest$237, PyString.fromInterned("Closest integer to a/b, a and b positive integers; rounds to even\n    in the case of a tie.\n\n    "));
                  var1.setlocal("_div_nearest", var19);
                  var3 = null;
                  var1.setline(5515);
                  var17 = new PyObject[]{Py.newInteger(8)};
                  var19 = new PyFunction(var1.f_globals, var17, _ilog$238, PyString.fromInterned("Integer approximation to M*log(x/M), with absolute error boundable\n    in terms only of x/M.\n\n    Given positive integers x and M, return an integer approximation to\n    M * log(x/M).  For L = 8 and 0.1 <= x/M <= 10 the difference\n    between the approximation and the exact result is at most 22.  For\n    L = 8 and 1.0 <= x/M <= 10.0 the difference is at most 15.  In\n    both cases these are upper bounds on the error; it will usually be\n    much smaller."));
                  var1.setlocal("_ilog", var19);
                  var3 = null;
                  var1.setline(5563);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _dlog10$239, PyString.fromInterned("Given integers c, e and p with c > 0, p >= 0, compute an integer\n    approximation to 10**p * log10(c*10**e), with an absolute error of\n    at most 1.  Assumes that c*10**e is not exactly 1."));
                  var1.setlocal("_dlog10", var19);
                  var3 = null;
                  var1.setline(5597);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _dlog$240, PyString.fromInterned("Given integers c, e and p with c > 0, compute an integer\n    approximation to 10**p * log(c*10**e), with an absolute error of\n    at most 1.  Assumes that c*10**e is not exactly 1."));
                  var1.setlocal("_dlog", var19);
                  var3 = null;
                  var1.setline(5641);
                  var17 = new PyObject[]{var1.getname("object")};
                  var14 = Py.makeClass("_Log10Memoize", var17, _Log10Memoize$241);
                  var1.setlocal("_Log10Memoize", var14);
                  var4 = null;
                  Arrays.fill(var17, (Object)null);
                  var1.setline(5676);
                  var12 = var1.getname("_Log10Memoize").__call__(var2).__getattr__("getdigits");
                  var1.setlocal("_log10_digits", var12);
                  var3 = null;
                  var1.setline(5678);
                  var17 = new PyObject[]{Py.newInteger(8)};
                  var19 = new PyFunction(var1.f_globals, var17, _iexp$244, PyString.fromInterned("Given integers x and M, M > 0, such that x/M is small in absolute\n    value, compute an integer approximation to M*exp(x/M).  For 0 <=\n    x/M <= 2.4, the absolute error in the result is bounded by 60 (and\n    is usually much smaller)."));
                  var1.setlocal("_iexp", var19);
                  var3 = null;
                  var1.setline(5715);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _dexp$245, PyString.fromInterned("Compute an approximation to exp(c*10**e), with p decimal places of\n    precision.\n\n    Returns integers d, f such that:\n\n      10**(p-1) <= d <= 10**p, and\n      (d-1)*10**f < exp(c*10**e) < (d+1)*10**f\n\n    In other words, d*10**f is an approximation to exp(c*10**e) with p\n    digits of precision, and with an error in d of at most 1.  This is\n    almost, but not quite, the same as the error being < 1ulp: when d\n    = 10**(p-1) the error could be up to 10 ulp."));
                  var1.setlocal("_dexp", var19);
                  var3 = null;
                  var1.setline(5751);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _dpower$246, PyString.fromInterned("Given integers xc, xe, yc and ye representing Decimals x = xc*10**xe and\n    y = yc*10**ye, compute x**y.  Returns a pair of integers (c, e) such that:\n\n      10**(p-1) <= c <= 10**p, and\n      (c-1)*10**e < x**y < (c+1)*10**e\n\n    in other words, c*10**e is an approximation to x**y with p digits\n    of precision, and with an error in c of at most 1.  (This is\n    almost, but not quite, the same as the error being < 1ulp: when c\n    == 10**(p-1) we can only guarantee error < 10ulp.)\n\n    We assume that: x is positive and not equal to 1, and y is nonzero.\n    "));
                  var1.setlocal("_dpower", var19);
                  var3 = null;
                  var1.setline(5793);
                  var17 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("1"), Py.newInteger(100), PyString.fromInterned("2"), Py.newInteger(70), PyString.fromInterned("3"), Py.newInteger(53), PyString.fromInterned("4"), Py.newInteger(40), PyString.fromInterned("5"), Py.newInteger(31), PyString.fromInterned("6"), Py.newInteger(23), PyString.fromInterned("7"), Py.newInteger(16), PyString.fromInterned("8"), Py.newInteger(10), PyString.fromInterned("9"), Py.newInteger(5)})};
                  var19 = new PyFunction(var1.f_globals, var17, _log10_lb$247, PyString.fromInterned("Compute a lower bound for 100*log10(c) for a positive integer c."));
                  var1.setlocal("_log10_lb", var19);
                  var3 = null;
                  var1.setline(5804);
                  var17 = new PyObject[]{var1.getname("False"), var1.getname("False")};
                  var19 = new PyFunction(var1.f_globals, var17, _convert_other$248, PyString.fromInterned("Convert other to Decimal.\n\n    Verifies that it's ok to use in an implicit construction.\n    If allow_float is true, allow conversion from float;  this\n    is used in the comparison methods (__eq__ and friends).\n\n    "));
                  var1.setlocal("_convert_other", var19);
                  var3 = null;
                  var1.setline(5828);
                  PyObject var22 = var1.getname("Context");
                  var17 = new PyObject[]{Py.newInteger(28), var1.getname("ROUND_HALF_EVEN"), new PyList(new PyObject[]{var1.getname("DivisionByZero"), var1.getname("Overflow"), var1.getname("InvalidOperation")}), new PyList(Py.EmptyObjects), Py.newInteger(999999999), Py.newInteger(-999999999), Py.newInteger(1)};
                  String[] var20 = new String[]{"prec", "rounding", "traps", "flags", "Emax", "Emin", "capitals"};
                  var22 = var22.__call__(var2, var17, var20);
                  var3 = null;
                  var12 = var22;
                  var1.setlocal("DefaultContext", var12);
                  var3 = null;
                  var1.setline(5842);
                  var22 = var1.getname("Context");
                  var17 = new PyObject[]{Py.newInteger(9), var1.getname("ROUND_HALF_UP"), new PyList(new PyObject[]{var1.getname("DivisionByZero"), var1.getname("Overflow"), var1.getname("InvalidOperation"), var1.getname("Clamped"), var1.getname("Underflow")}), new PyList(Py.EmptyObjects)};
                  var20 = new String[]{"prec", "rounding", "traps", "flags"};
                  var22 = var22.__call__(var2, var17, var20);
                  var3 = null;
                  var12 = var22;
                  var1.setlocal("BasicContext", var12);
                  var3 = null;
                  var1.setline(5848);
                  var22 = var1.getname("Context");
                  var17 = new PyObject[]{Py.newInteger(9), var1.getname("ROUND_HALF_EVEN"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)};
                  var20 = new String[]{"prec", "rounding", "traps", "flags"};
                  var22 = var22.__call__(var2, var17, var20);
                  var3 = null;
                  var12 = var22;
                  var1.setlocal("ExtendedContext", var12);
                  var3 = null;
                  var1.setline(5869);
                  var12 = imp.importOne("re", var1, -1);
                  var1.setlocal("re", var12);
                  var3 = null;
                  var1.setline(5870);
                  var12 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        # A numeric string consists of:\n#    \\s*\n    (?P<sign>[-+])?              # an optional sign, followed by either...\n    (\n        (?=\\d|\\.\\d)              # ...a number (with at least one digit)\n        (?P<int>\\d*)             # having a (possibly empty) integer part\n        (\\.(?P<frac>\\d*))?       # followed by an optional fractional part\n        (E(?P<exp>[-+]?\\d+))?    # followed by an optional exponent, or...\n    |\n        Inf(inity)?              # ...an infinity, or...\n    |\n        (?P<signal>s)?           # ...an (optionally signaling)\n        NaN                      # NaN\n        (?P<diag>\\d*)            # with (possibly empty) diagnostic info.\n    )\n#    \\s*\n    \\Z\n"), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("IGNORECASE"))._or(var1.getname("re").__getattr__("UNICODE"))).__getattr__("match");
                  var1.setlocal("_parser", var12);
                  var3 = null;
                  var1.setline(5889);
                  var12 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0*$")).__getattr__("match");
                  var1.setlocal("_all_zeros", var12);
                  var3 = null;
                  var1.setline(5890);
                  var12 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("50*$")).__getattr__("match");
                  var1.setlocal("_exact_half", var12);
                  var3 = null;
                  var1.setline(5901);
                  var12 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\A\n(?:\n   (?P<fill>.)?\n   (?P<align>[<>=^])\n)?\n(?P<sign>[-+ ])?\n(?P<zeropad>0)?\n(?P<minimumwidth>(?!0)\\d+)?\n(?P<thousands_sep>,)?\n(?:\\.(?P<precision>0|(?!0)\\d+))?\n(?P<type>[eEfFgGn%])?\n\\Z\n"), (PyObject)var1.getname("re").__getattr__("VERBOSE"));
                  var1.setlocal("_parse_format_specifier_regex", var12);
                  var3 = null;
                  var1.setline(5915);
                  var1.dellocal("re");

                  try {
                     var1.setline(5921);
                     var12 = imp.importOneAs("locale", var1, -1);
                     var1.setlocal("_locale", var12);
                     var3 = null;
                  } catch (Throwable var6) {
                     var15 = Py.setException(var6, var1);
                     if (!var15.match(var1.getname("ImportError"))) {
                        throw var15;
                     }

                     var1.setline(5923);
                  }

                  var1.setline(5925);
                  var17 = new PyObject[]{var1.getname("None")};
                  var19 = new PyFunction(var1.f_globals, var17, _parse_format_specifier$249, PyString.fromInterned("Parse and validate a format specifier.\n\n    Turns a standard numeric format specifier into a dict, with the\n    following entries:\n\n      fill: fill character to pad field to minimum width\n      align: alignment type, either '<', '>', '=' or '^'\n      sign: either '+', '-' or ' '\n      minimumwidth: nonnegative integer giving minimum width\n      zeropad: boolean, indicating whether to pad with zeros\n      thousands_sep: string to use as thousands separator, or ''\n      grouping: grouping for thousands separators, in format\n        used by localeconv\n      decimal_point: string to use for decimal point\n      precision: nonnegative integer giving precision, or None\n      type: one of the characters 'eEfFgG%', or None\n      unicode: boolean (always True for Python 3.x)\n\n    "));
                  var1.setlocal("_parse_format_specifier", var19);
                  var3 = null;
                  var1.setline(6009);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _format_align$250, PyString.fromInterned("Given an unpadded, non-aligned numeric string 'body' and sign\n    string 'sign', add padding and aligment conforming to the given\n    format specifier dictionary 'spec' (as produced by\n    parse_format_specifier).\n\n    Also converts result to unicode if necessary.\n\n    "));
                  var1.setlocal("_format_align", var19);
                  var3 = null;
                  var1.setline(6042);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _group_lengths$251, PyString.fromInterned("Convert a localeconv-style grouping into a (possibly infinite)\n    iterable of integers representing group lengths.\n\n    "));
                  var1.setlocal("_group_lengths", var19);
                  var3 = null;
                  var1.setline(6065);
                  var17 = new PyObject[]{Py.newInteger(1)};
                  var19 = new PyFunction(var1.f_globals, var17, _insert_thousands_sep$252, PyString.fromInterned("Insert thousands separators into a digit string.\n\n    spec is a dictionary whose keys should include 'thousands_sep' and\n    'grouping'; typically it's the result of parsing the format\n    specifier using _parse_format_specifier.\n\n    The min_width keyword argument gives the minimum length of the\n    result, which will be padded on the left with zeros if necessary.\n\n    If necessary, the zero padding adds an extra '0' on the left to\n    avoid a leading thousands separator.  For example, inserting\n    commas every three digits in '123456', with min_width=8, gives\n    '0,123,456', even though that has length 9.\n\n    "));
                  var1.setlocal("_insert_thousands_sep", var19);
                  var3 = null;
                  var1.setline(6102);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _format_sign$253, PyString.fromInterned("Determine sign character."));
                  var1.setlocal("_format_sign", var19);
                  var3 = null;
                  var1.setline(6112);
                  var17 = Py.EmptyObjects;
                  var19 = new PyFunction(var1.f_globals, var17, _format_number$254, PyString.fromInterned("Format a number, given the following data:\n\n    is_negative: true if the number is negative, else false\n    intpart: string of digits that must appear before the decimal point\n    fracpart: string of digits that must come after the point\n    exp: exponent, as an integer\n    spec: dictionary resulting from parsing the format specifier\n\n    This function uses the information in spec to:\n      insert separators (decimal separator and thousands separators)\n      format the sign\n      format the exponent\n      add trailing '%' for the '%' type\n      zero-pad if necessary\n      fill and align if necessary\n    "));
                  var1.setlocal("_format_number", var19);
                  var3 = null;
                  var1.setline(6153);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Inf"));
                  var1.setlocal("_Infinity", var12);
                  var3 = null;
                  var1.setline(6154);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-Inf"));
                  var1.setlocal("_NegativeInfinity", var12);
                  var3 = null;
                  var1.setline(6155);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NaN"));
                  var1.setlocal("_NaN", var12);
                  var3 = null;
                  var1.setline(6156);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal("_Zero", var12);
                  var3 = null;
                  var1.setline(6157);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal("_One", var12);
                  var3 = null;
                  var1.setline(6158);
                  var12 = var1.getname("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1));
                  var1.setlocal("_NegativeOne", var12);
                  var3 = null;
                  var1.setline(6161);
                  PyTuple var21 = new PyTuple(new PyObject[]{var1.getname("_Infinity"), var1.getname("_NegativeInfinity")});
                  var1.setlocal("_SignedInfinity", var21);
                  var3 = null;
                  var1.setline(6165);
                  var12 = var1.getname("__name__");
                  var22 = var12._eq(PyString.fromInterned("__main__"));
                  var3 = null;
                  if (var22.__nonzero__()) {
                     var1.setline(6166);
                     var12 = imp.importOne("doctest", var1, -1);
                     var1.setlocal("doctest", var12);
                     var3 = null;
                     var12 = imp.importOne("sys", var1, -1);
                     var1.setlocal("sys", var12);
                     var3 = null;
                     var1.setline(6167);
                     var1.getname("doctest").__getattr__("testmod").__call__(var2, var1.getname("sys").__getattr__("modules").__getitem__(var1.getname("__name__")));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("name", var14);
               var1.setline(3732);
               var5 = var1.getname("name").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__getattr__("upper").__call__(var2);
               var1.setlocal("globalname", var5);
               var5 = null;
               var1.setline(3733);
               var5 = var1.getname("globals").__call__(var2).__getitem__(var1.getname("globalname"));
               var1.setlocal("val", var5);
               var5 = null;
               var1.setline(3734);
               var5 = var1.getname("name");
               var1.getname("Decimal").__getattr__("_pick_rounding_function").__setitem__(var1.getname("val"), var5);
               var5 = null;
            }
         }

         var1.setlocal("name", var14);
         var1.setline(3729);
         if (var1.getname("name").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_round_")).__nonzero__()) {
            var1.setline(3728);
            var1.getname("_[3728_22]").__call__(var2, var1.getname("name"));
         }
      }
   }

   public PyObject f$1(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DecimalException$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base exception class.\n\n    Used exceptions derive from this.\n    If an exception derives from another exception besides this (such as\n    Underflow (Inexact, Rounded, Subnormal) that indicates that it is only\n    called if the others are present.  This isn't actually used for\n    anything, though.\n\n    handle  -- Called when context._raise_error is called and the\n               trap_enabler is not set.  First argument is self, second is the\n               context.  More arguments can be given, those being after\n               the explanation in _raise_error (For example,\n               context._raise_error(NewError, '(-x)!', self._sign) would\n               call NewError().handle(context, self._sign).)\n\n    To define a new exception, it should be sufficient to have it derive\n    from DecimalException.\n    "));
      var1.setline(179);
      PyString.fromInterned("Base exception class.\n\n    Used exceptions derive from this.\n    If an exception derives from another exception besides this (such as\n    Underflow (Inexact, Rounded, Subnormal) that indicates that it is only\n    called if the others are present.  This isn't actually used for\n    anything, though.\n\n    handle  -- Called when context._raise_error is called and the\n               trap_enabler is not set.  First argument is self, second is the\n               context.  More arguments can be given, those being after\n               the explanation in _raise_error (For example,\n               context._raise_error(NewError, '(-x)!', self._sign) would\n               call NewError().handle(context, self._sign).)\n\n    To define a new exception, it should be sufficient to have it derive\n    from DecimalException.\n    ");
      var1.setline(180);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$3, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$3(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Clamped$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exponent of a 0 changed to fit bounds.\n\n    This occurs and signals clamped if the exponent of a result has been\n    altered in order to fit the constraints of a specific concrete\n    representation.  This may occur when the exponent of a zero result would\n    be outside the bounds of a representation, or when a large normal\n    number would have an encoded exponent that cannot be represented.  In\n    this latter case, the exponent is reduced to fit and the corresponding\n    number of zero digits are appended to the coefficient (\"fold-down\").\n    "));
      var1.setline(194);
      PyString.fromInterned("Exponent of a 0 changed to fit bounds.\n\n    This occurs and signals clamped if the exponent of a result has been\n    altered in order to fit the constraints of a specific concrete\n    representation.  This may occur when the exponent of a zero result would\n    be outside the bounds of a representation, or when a large normal\n    number would have an encoded exponent that cannot be represented.  In\n    this latter case, the exponent is reduced to fit and the corresponding\n    number of zero digits are appended to the coefficient (\"fold-down\").\n    ");
      return var1.getf_locals();
   }

   public PyObject InvalidOperation$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An invalid operation was performed.\n\n    Various bad things cause this:\n\n    Something creates a signaling NaN\n    -INF + INF\n    0 * (+-)INF\n    (+-)INF / (+-)INF\n    x % 0\n    (+-)INF % x\n    x._rescale( non-integer )\n    sqrt(-x) , x > 0\n    0 ** 0\n    x ** (non-integer)\n    x ** (+-)INF\n    An operand is invalid\n\n    The result of the operation after these is a quiet positive NaN,\n    except when the cause is a signaling NaN, in which case the result is\n    also a quiet NaN, but with the original sign, and an optional\n    diagnostic information.\n    "));
      var1.setline(218);
      PyString.fromInterned("An invalid operation was performed.\n\n    Various bad things cause this:\n\n    Something creates a signaling NaN\n    -INF + INF\n    0 * (+-)INF\n    (+-)INF / (+-)INF\n    x % 0\n    (+-)INF % x\n    x._rescale( non-integer )\n    sqrt(-x) , x > 0\n    0 ** 0\n    x ** (non-integer)\n    x ** (+-)INF\n    An operand is invalid\n\n    The result of the operation after these is a quiet positive NaN,\n    except when the cause is a signaling NaN, in which case the result is\n    also a quiet NaN, but with the original sign, and an optional\n    diagnostic information.\n    ");
      var1.setline(219);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$6, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$6(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(221);
         var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("_sign"), var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("_int"), PyString.fromInterned("n"), var1.getglobal("True"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(222);
         var3 = var1.getlocal(3).__getattr__("_fix_nan").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(223);
         var3 = var1.getglobal("_NaN");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ConversionSyntax$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Trying to convert badly formed string.\n\n    This occurs and signals invalid-operation if an string is being\n    converted to a number and it does not conform to the numeric string\n    syntax.  The result is [0,qNaN].\n    "));
      var1.setline(231);
      PyString.fromInterned("Trying to convert badly formed string.\n\n    This occurs and signals invalid-operation if an string is being\n    converted to a number and it does not conform to the numeric string\n    syntax.  The result is [0,qNaN].\n    ");
      var1.setline(232);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$8, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$8(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyObject var3 = var1.getglobal("_NaN");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DivisionByZero$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Division by 0.\n\n    This occurs and signals division-by-zero if division of a finite number\n    by zero was attempted (during a divide-integer or divide operation, or a\n    power operation with negative right-hand operand), and the dividend was\n    not zero.\n\n    The result of the operation is [sign,inf], where sign is the exclusive\n    or of the signs of the operands for divide, or is 1 for an odd power of\n    -0, for power.\n    "));
      var1.setline(246);
      PyString.fromInterned("Division by 0.\n\n    This occurs and signals division-by-zero if division of a finite number\n    by zero was attempted (during a divide-integer or divide operation, or a\n    power operation with negative right-hand operand), and the dividend was\n    not zero.\n\n    The result of the operation is [sign,inf], where sign is the exclusive\n    or of the signs of the operands for divide, or is 1 for an odd power of\n    -0, for power.\n    ");
      var1.setline(248);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$10, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$10(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DivisionImpossible$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Cannot perform the division adequately.\n\n    This occurs and signals invalid-operation if the integer result of a\n    divide-integer or remainder operation had too many digits (would be\n    longer than precision).  The result is [0,qNaN].\n    "));
      var1.setline(257);
      PyString.fromInterned("Cannot perform the division adequately.\n\n    This occurs and signals invalid-operation if the integer result of a\n    divide-integer or remainder operation had too many digits (would be\n    longer than precision).  The result is [0,qNaN].\n    ");
      var1.setline(259);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$12, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$12(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var3 = var1.getglobal("_NaN");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DivisionUndefined$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Undefined result of division.\n\n    This occurs and signals invalid-operation if division by zero was\n    attempted (during a divide-integer, divide, or remainder operation), and\n    the dividend is also zero.  The result is [0,qNaN].\n    "));
      var1.setline(268);
      PyString.fromInterned("Undefined result of division.\n\n    This occurs and signals invalid-operation if division by zero was\n    attempted (during a divide-integer, divide, or remainder operation), and\n    the dividend is also zero.  The result is [0,qNaN].\n    ");
      var1.setline(270);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$14, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$14(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getglobal("_NaN");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Inexact$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Had to round, losing information.\n\n    This occurs and signals inexact whenever the result of an operation is\n    not exact (that is, it needed to be rounded and any discarded digits\n    were non-zero), or if an overflow or underflow condition occurs.  The\n    result in all cases is unchanged.\n\n    The inexact signal may be tested (or trapped) to determine if a given\n    operation (or sequence of operations) was inexact.\n    "));
      var1.setline(283);
      PyString.fromInterned("Had to round, losing information.\n\n    This occurs and signals inexact whenever the result of an operation is\n    not exact (that is, it needed to be rounded and any discarded digits\n    were non-zero), or if an overflow or underflow condition occurs.  The\n    result in all cases is unchanged.\n\n    The inexact signal may be tested (or trapped) to determine if a given\n    operation (or sequence of operations) was inexact.\n    ");
      return var1.getf_locals();
   }

   public PyObject InvalidContext$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Invalid context.  Unknown rounding, for example.\n\n    This occurs and signals invalid-operation if an invalid context was\n    detected during an operation.  This can occur if contexts are not checked\n    on creation and either the precision exceeds the capability of the\n    underlying concrete representation or an unknown or unsupported rounding\n    was specified.  These aspects of the context need only be checked when\n    the values are required to be used.  The result is [0,qNaN].\n    "));
      var1.setline(294);
      PyString.fromInterned("Invalid context.  Unknown rounding, for example.\n\n    This occurs and signals invalid-operation if an invalid context was\n    detected during an operation.  This can occur if contexts are not checked\n    on creation and either the precision exceeds the capability of the\n    underlying concrete representation or an unknown or unsupported rounding\n    was specified.  These aspects of the context need only be checked when\n    the values are required to be used.  The result is [0,qNaN].\n    ");
      var1.setline(296);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$17, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$17(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyObject var3 = var1.getglobal("_NaN");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Rounded$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Number got rounded (not  necessarily changed during rounding).\n\n    This occurs and signals rounded whenever the result of an operation is\n    rounded (that is, some zero or non-zero digits were discarded from the\n    coefficient), or if an overflow or underflow condition occurs.  The\n    result in all cases is unchanged.\n\n    The rounded signal may be tested (or trapped) to determine if a given\n    operation (or sequence of operations) caused a loss of precision.\n    "));
      var1.setline(309);
      PyString.fromInterned("Number got rounded (not  necessarily changed during rounding).\n\n    This occurs and signals rounded whenever the result of an operation is\n    rounded (that is, some zero or non-zero digits were discarded from the\n    coefficient), or if an overflow or underflow condition occurs.  The\n    result in all cases is unchanged.\n\n    The rounded signal may be tested (or trapped) to determine if a given\n    operation (or sequence of operations) caused a loss of precision.\n    ");
      return var1.getf_locals();
   }

   public PyObject Subnormal$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exponent < Emin before rounding.\n\n    This occurs and signals subnormal whenever the result of a conversion or\n    operation is subnormal (that is, its adjusted exponent is less than\n    Emin, before any rounding).  The result in all cases is unchanged.\n\n    The subnormal signal may be tested (or trapped) to determine if a given\n    or operation (or sequence of operations) yielded a subnormal result.\n    "));
      var1.setline(320);
      PyString.fromInterned("Exponent < Emin before rounding.\n\n    This occurs and signals subnormal whenever the result of a conversion or\n    operation is subnormal (that is, its adjusted exponent is less than\n    Emin, before any rounding).  The result in all cases is unchanged.\n\n    The subnormal signal may be tested (or trapped) to determine if a given\n    or operation (or sequence of operations) yielded a subnormal result.\n    ");
      return var1.getf_locals();
   }

   public PyObject Overflow$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Numerical overflow.\n\n    This occurs and signals overflow if the adjusted exponent of a result\n    (from a conversion or from an operation that is not an attempt to divide\n    by zero), after rounding, would be greater than the largest value that\n    can be handled by the implementation (the value Emax).\n\n    The result depends on the rounding mode:\n\n    For round-half-up and round-half-even (and for round-half-down and\n    round-up, if implemented), the result of the operation is [sign,inf],\n    where sign is the sign of the intermediate result.  For round-down, the\n    result is the largest finite number that can be represented in the\n    current precision, with the sign of the intermediate result.  For\n    round-ceiling, the result is the same as for round-down if the sign of\n    the intermediate result is 1, or is [0,inf] otherwise.  For round-floor,\n    the result is the same as for round-down if the sign of the intermediate\n    result is 0, or is [1,inf] otherwise.  In all cases, Inexact and Rounded\n    will also be raised.\n    "));
      var1.setline(342);
      PyString.fromInterned("Numerical overflow.\n\n    This occurs and signals overflow if the adjusted exponent of a result\n    (from a conversion or from an operation that is not an attempt to divide\n    by zero), after rounding, would be greater than the largest value that\n    can be handled by the implementation (the value Emax).\n\n    The result depends on the rounding mode:\n\n    For round-half-up and round-half-even (and for round-half-down and\n    round-up, if implemented), the result of the operation is [sign,inf],\n    where sign is the sign of the intermediate result.  For round-down, the\n    result is the largest finite number that can be represented in the\n    current precision, with the sign of the intermediate result.  For\n    round-ceiling, the result is the same as for round-down if the sign of\n    the intermediate result is 1, or is [0,inf] otherwise.  For round-floor,\n    the result is the same as for round-down if the sign of the intermediate\n    result is 0, or is [1,inf] otherwise.  In all cases, Inexact and Rounded\n    will also be raised.\n    ");
      var1.setline(344);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$21, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$21(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyObject var3 = var1.getlocal(1).__getattr__("rounding");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ROUND_HALF_UP"), var1.getglobal("ROUND_HALF_EVEN"), var1.getglobal("ROUND_HALF_DOWN"), var1.getglobal("ROUND_UP")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(347);
         var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(348);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(349);
            var4 = var1.getlocal(1).__getattr__("rounding");
            var10000 = var4._eq(var1.getglobal("ROUND_CEILING"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(350);
               var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(351);
               var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(2), PyString.fromInterned("9")._mul(var1.getlocal(1).__getattr__("prec")), var1.getlocal(1).__getattr__("Emax")._sub(var1.getlocal(1).__getattr__("prec"))._add(Py.newInteger(1)));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(353);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(354);
               var4 = var1.getlocal(1).__getattr__("rounding");
               var10000 = var4._eq(var1.getglobal("ROUND_FLOOR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(355);
                  var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(356);
                  var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(2), PyString.fromInterned("9")._mul(var1.getlocal(1).__getattr__("prec")), var1.getlocal(1).__getattr__("Emax")._sub(var1.getlocal(1).__getattr__("prec"))._add(Py.newInteger(1)));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject Underflow$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Numerical underflow with result rounded to 0.\n\n    This occurs and signals underflow if a result is inexact and the\n    adjusted exponent of the result would be smaller (more negative) than\n    the smallest value that can be handled by the implementation (the value\n    Emin).  That is, the result is both inexact and subnormal.\n\n    The result after an underflow will be a subnormal number rounded, if\n    necessary, so that its exponent is not less than Etiny.  This may result\n    in 0 with the sign of the intermediate result and an exponent of Etiny.\n\n    In all cases, Inexact, Rounded, and Subnormal will also be raised.\n    "));
      var1.setline(373);
      PyString.fromInterned("Numerical underflow with result rounded to 0.\n\n    This occurs and signals underflow if a result is inexact and the\n    adjusted exponent of the result would be smaller (more negative) than\n    the smallest value that can be handled by the implementation (the value\n    Emin).  That is, the result is both inexact and subnormal.\n\n    The result after an underflow will be a subnormal number rounded, if\n    necessary, so that its exponent is not less than Etiny.  This may result\n    in 0 with the sign of the intermediate result and an exponent of Etiny.\n\n    In all cases, Inexact, Rounded, and Subnormal will also be raised.\n    ");
      return var1.getf_locals();
   }

   public PyObject MockThreading$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(399);
      PyObject[] var3 = new PyObject[]{var1.getname("sys")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, local$24, (PyObject)null);
      var1.setlocal("local", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject local$24(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = var1.getlocal(1).__getattr__("modules").__getitem__(var1.getglobal("__name__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setcontext$25(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("Set this thread's context to context.");
      var1.setline(424);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("DefaultContext"), var1.getglobal("BasicContext"), var1.getglobal("ExtendedContext")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(425);
         var3 = var1.getlocal(0).__getattr__("copy").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(426);
         var1.getlocal(0).__getattr__("clear_flags").__call__(var2);
      }

      var1.setline(427);
      var3 = var1.getlocal(0);
      var1.getglobal("threading").__getattr__("currentThread").__call__(var2).__setattr__("__decimal_context__", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getcontext$26(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyString.fromInterned("Returns this thread's context.\n\n        If this thread does not yet have a context, returns\n        a new context and sets this thread's context.\n        New contexts are copies of DefaultContext.\n        ");

      PyObject var3;
      try {
         var1.setline(437);
         var3 = var1.getglobal("threading").__getattr__("currentThread").__call__(var2).__getattr__("__decimal_context__");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(439);
            PyObject var5 = var1.getglobal("Context").__call__(var2);
            var1.setlocal(0, var5);
            var5 = null;
            var1.setline(440);
            var5 = var1.getlocal(0);
            var1.getglobal("threading").__getattr__("currentThread").__call__(var2).__setattr__("__decimal_context__", var5);
            var5 = null;
            var1.setline(441);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject getcontext$27(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyString.fromInterned("Returns this thread's context.\n\n        If this thread does not yet have a context, returns\n        a new context and sets this thread's context.\n        New contexts are copies of DefaultContext.\n        ");

      PyObject var3;
      try {
         var1.setline(457);
         var3 = var1.getlocal(0).__getattr__("__decimal_context__");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(459);
            PyObject var5 = var1.getglobal("Context").__call__(var2);
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(460);
            var5 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("__decimal_context__", var5);
            var5 = null;
            var1.setline(461);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject setcontext$28(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyString.fromInterned("Set this thread's context to context.");
      var1.setline(465);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("DefaultContext"), var1.getglobal("BasicContext"), var1.getglobal("ExtendedContext")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(466);
         var3 = var1.getlocal(0).__getattr__("copy").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(467);
         var1.getlocal(0).__getattr__("clear_flags").__call__(var2);
      }

      var1.setline(468);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__setattr__("__decimal_context__", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject localcontext$29(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      PyString.fromInterned("Return a context manager for a copy of the supplied context\n\n    Uses a copy of the current context if no context is specified\n    The returned context manager creates a local decimal context\n    in a with statement:\n        def sin(x):\n             with localcontext() as ctx:\n                 ctx.prec += 2\n                 # Rest of sin calculation algorithm\n                 # uses a precision 2 greater than normal\n             return +s  # Convert result to normal precision\n\n         def sin(x):\n             with localcontext(ExtendedContext):\n                 # Rest of sin calculation algorithm\n                 # uses the Extended Context from the\n                 # General Decimal Arithmetic Specification\n             return +s  # Convert result to normal context\n\n    >>> setcontext(DefaultContext)\n    >>> print getcontext().prec\n    28\n    >>> with localcontext():\n    ...     ctx = getcontext()\n    ...     ctx.prec += 2\n    ...     print ctx.prec\n    ...\n    30\n    >>> with localcontext(ExtendedContext):\n    ...     print getcontext().prec\n    ...\n    9\n    >>> print getcontext().prec\n    28\n    ");
      var1.setline(508);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(508);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(509);
      var3 = var1.getglobal("_ContextManager").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Decimal$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Floating point class for decimal arithmetic."));
      var1.setline(515);
      PyString.fromInterned("Floating point class for decimal arithmetic.");
      var1.setline(517);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_exp"), PyString.fromInterned("_int"), PyString.fromInterned("_sign"), PyString.fromInterned("_is_special")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(523);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("0"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$31, PyString.fromInterned("Create a decimal point instance.\n\n        >>> Decimal('3.14')              # string input\n        Decimal('3.14')\n        >>> Decimal((0, (3, 1, 4), -2))  # tuple (sign, digit_tuple, exponent)\n        Decimal('3.14')\n        >>> Decimal(314)                 # int or long\n        Decimal('314')\n        >>> Decimal(Decimal(314))        # another decimal instance\n        Decimal('314')\n        >>> Decimal('  3.14  \\n')        # leading and trailing whitespace okay\n        Decimal('3.14')\n        "));
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(670);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, from_float$32, PyString.fromInterned("Converts a float to a decimal number, exactly.\n\n        Note that Decimal.from_float(0.1) is not the same as Decimal('0.1').\n        Since 0.1 is not exactly representable in binary floating point, the\n        value is stored as the nearest representable value which is\n        0x1.999999999999ap-4.  The exact equivalent of the value in decimal\n        is 0.1000000000000000055511151231257827021181583404541015625.\n\n        >>> Decimal.from_float(0.1)\n        Decimal('0.1000000000000000055511151231257827021181583404541015625')\n        >>> Decimal.from_float(float('nan'))\n        Decimal('NaN')\n        >>> Decimal.from_float(float('inf'))\n        Decimal('Infinity')\n        >>> Decimal.from_float(-float('inf'))\n        Decimal('-Infinity')\n        >>> Decimal.from_float(-0.0)\n        Decimal('-0')\n\n        "));
      var1.setlocal("from_float", var5);
      var3 = null;
      var1.setline(706);
      PyObject var6 = var1.getname("classmethod").__call__(var2, var1.getname("from_float"));
      var1.setlocal("from_float", var6);
      var3 = null;
      var1.setline(708);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _isnan$33, PyString.fromInterned("Returns whether the number is not actually one.\n\n        0 if a number\n        1 if NaN\n        2 if sNaN\n        "));
      var1.setlocal("_isnan", var5);
      var3 = null;
      var1.setline(723);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _isinfinity$34, PyString.fromInterned("Returns whether the number is infinite\n\n        0 if finite or not a number\n        1 if +INF\n        -1 if -INF\n        "));
      var1.setlocal("_isinfinity", var5);
      var3 = null;
      var1.setline(736);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _check_nans$35, PyString.fromInterned("Returns whether the number is not actually one.\n\n        if self, other are sNaN, signal\n        if self, other are NaN return nan\n        return 0\n\n        Done before operations.\n        "));
      var1.setlocal("_check_nans", var5);
      var3 = null;
      var1.setline(768);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _compare_check_nans$36, PyString.fromInterned("Version of _check_nans used for the signaling comparisons\n        compare_signal, __le__, __lt__, __ge__, __gt__.\n\n        Signal InvalidOperation if either self or other is a (quiet\n        or signaling) NaN.  Signaling NaNs take precedence over quiet\n        NaNs.\n\n        Return 0 if neither operand is a NaN.\n\n        "));
      var1.setlocal("_compare_check_nans", var5);
      var3 = null;
      var1.setline(801);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __nonzero__$37, PyString.fromInterned("Return True if self is nonzero; otherwise return False.\n\n        NaNs and infinities are considered nonzero.\n        "));
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(808);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _cmp$38, PyString.fromInterned("Compare the two non-NaN decimal instances self and other.\n\n        Returns -1 if self < other, 0 if self == other and 1\n        if self > other.  This routine is for internal use only."));
      var1.setlocal("_cmp", var5);
      var3 = null;
      var1.setline(872);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __eq__$39, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(880);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __ne__$40, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(888);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __lt__$41, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(897);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __le__$42, (PyObject)null);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(906);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __gt__$43, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(915);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __ge__$44, (PyObject)null);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(924);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, compare$45, PyString.fromInterned("Compares one to another.\n\n        -1 => a < b\n        0  => a = b\n        1  => a > b\n        NaN => one is NaN\n        Like __cmp__, but returns Decimal instances.\n        "));
      var1.setlocal("compare", var5);
      var3 = null;
      var1.setline(943);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$46, PyString.fromInterned("x.__hash__() <==> hash(x)"));
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(989);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, as_tuple$47, PyString.fromInterned("Represents the number as a triple tuple.\n\n        To show the internals exactly as they are.\n        "));
      var1.setlocal("as_tuple", var5);
      var3 = null;
      var1.setline(996);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$48, PyString.fromInterned("Represents the number as an instance of Decimal."));
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(1001);
      var4 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __str__$49, PyString.fromInterned("Return string representation of the number in scientific notation.\n\n        Captures all of the information in the underlying representation.\n        "));
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(1053);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, to_eng_string$50, PyString.fromInterned("Convert to engineering-type string.\n\n        Engineering notation has an exponent which is a multiple of 3, so there\n        are up to 3 digits left of the decimal place.\n\n        Same rules for when in exponential and when as a value as in __str__.\n        "));
      var1.setlocal("to_eng_string", var5);
      var3 = null;
      var1.setline(1063);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __neg__$51, PyString.fromInterned("Returns a copy with the sign switched.\n\n        Rounds, if it has reason.\n        "));
      var1.setlocal("__neg__", var5);
      var3 = null;
      var1.setline(1083);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __pos__$52, PyString.fromInterned("Returns a copy, unless it is a sNaN.\n\n        Rounds the number (if more then precision digits)\n        "));
      var1.setlocal("__pos__", var5);
      var3 = null;
      var1.setline(1103);
      var4 = new PyObject[]{var1.getname("True"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __abs__$53, PyString.fromInterned("Returns the absolute value of self.\n\n        If the keyword argument 'round' is false, do not round.  The\n        expression self.__abs__(round=False) is equivalent to\n        self.copy_abs().\n        "));
      var1.setlocal("__abs__", var5);
      var3 = null;
      var1.setline(1125);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __add__$54, PyString.fromInterned("Returns self + other.\n\n        -INF + INF (or the reverse) cause InvalidOperation errors.\n        "));
      var1.setlocal("__add__", var5);
      var3 = null;
      var1.setline(1211);
      var6 = var1.getname("__add__");
      var1.setlocal("__radd__", var6);
      var3 = null;
      var1.setline(1213);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __sub__$55, PyString.fromInterned("Return self - other"));
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(1227);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rsub__$56, PyString.fromInterned("Return other - self"));
      var1.setlocal("__rsub__", var5);
      var3 = null;
      var1.setline(1235);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __mul__$57, PyString.fromInterned("Return self * other.\n\n        (+-) INF * 0 (or its reverse) raise InvalidOperation.\n        "));
      var1.setlocal("__mul__", var5);
      var3 = null;
      var1.setline(1290);
      var6 = var1.getname("__mul__");
      var1.setlocal("__rmul__", var6);
      var3 = null;
      var1.setline(1292);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __truediv__$58, PyString.fromInterned("Return self / other."));
      var1.setlocal("__truediv__", var5);
      var3 = null;
      var1.setline(1351);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _divide$59, PyString.fromInterned("Return (self // other, self % other), to context.prec precision.\n\n        Assumes that neither self nor other is a NaN, that self is not\n        infinite and that other is nonzero.\n        "));
      var1.setlocal("_divide", var5);
      var3 = null;
      var1.setline(1384);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rtruediv__$60, PyString.fromInterned("Swaps self/other and returns __truediv__."));
      var1.setlocal("__rtruediv__", var5);
      var3 = null;
      var1.setline(1391);
      var6 = var1.getname("__truediv__");
      var1.setlocal("__div__", var6);
      var3 = null;
      var1.setline(1392);
      var6 = var1.getname("__rtruediv__");
      var1.setlocal("__rdiv__", var6);
      var3 = null;
      var1.setline(1394);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __divmod__$61, PyString.fromInterned("\n        Return (self // other, self % other)\n        "));
      var1.setlocal("__divmod__", var5);
      var3 = null;
      var1.setline(1430);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rdivmod__$62, PyString.fromInterned("Swaps self/other and returns __divmod__."));
      var1.setlocal("__rdivmod__", var5);
      var3 = null;
      var1.setline(1437);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __mod__$63, PyString.fromInterned("\n        self % other\n        "));
      var1.setlocal("__mod__", var5);
      var3 = null;
      var1.setline(1464);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rmod__$64, PyString.fromInterned("Swaps self/other and returns __mod__."));
      var1.setlocal("__rmod__", var5);
      var3 = null;
      var1.setline(1471);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, remainder_near$65, PyString.fromInterned("\n        Remainder nearest to 0-  abs(remainder-near) <= other/2\n        "));
      var1.setlocal("remainder_near", var5);
      var3 = null;
      var1.setline(1546);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __floordiv__$66, PyString.fromInterned("self // other"));
      var1.setlocal("__floordiv__", var5);
      var3 = null;
      var1.setline(1574);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rfloordiv__$67, PyString.fromInterned("Swaps self/other and returns __floordiv__."));
      var1.setlocal("__rfloordiv__", var5);
      var3 = null;
      var1.setline(1581);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __float__$68, PyString.fromInterned("Float representation."));
      var1.setlocal("__float__", var5);
      var3 = null;
      var1.setline(1591);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __int__$69, PyString.fromInterned("Converts self to an int, truncating if necessary."));
      var1.setlocal("__int__", var5);
      var3 = null;
      var1.setline(1604);
      var6 = var1.getname("__int__");
      var1.setlocal("__trunc__", var6);
      var3 = null;
      var1.setline(1606);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, real$70, (PyObject)null);
      var1.setlocal("real", var5);
      var3 = null;
      var1.setline(1608);
      var6 = var1.getname("property").__call__(var2, var1.getname("real"));
      var1.setlocal("real", var6);
      var3 = null;
      var1.setline(1610);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, imag$71, (PyObject)null);
      var1.setlocal("imag", var5);
      var3 = null;
      var1.setline(1612);
      var6 = var1.getname("property").__call__(var2, var1.getname("imag"));
      var1.setlocal("imag", var6);
      var3 = null;
      var1.setline(1614);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, conjugate$72, (PyObject)null);
      var1.setlocal("conjugate", var5);
      var3 = null;
      var1.setline(1617);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __complex__$73, (PyObject)null);
      var1.setlocal("__complex__", var5);
      var3 = null;
      var1.setline(1620);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __long__$74, PyString.fromInterned("Converts to a long.\n\n        Equivalent to long(int(self))\n        "));
      var1.setlocal("__long__", var5);
      var3 = null;
      var1.setline(1627);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _fix_nan$75, PyString.fromInterned("Decapitate the payload of a NaN to fit the context"));
      var1.setlocal("_fix_nan", var5);
      var3 = null;
      var1.setline(1639);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _fix$76, PyString.fromInterned("Round if it is necessary to keep self within prec precision.\n\n        Rounds and fixes the exponent.  Does not raise on a sNaN.\n\n        Arguments:\n        self - Decimal instance\n        context - context used.\n        "));
      var1.setlocal("_fix", var5);
      var3 = null;
      var1.setline(1731);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_pick_rounding_function", var7);
      var3 = null;
      var1.setline(1743);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_down$77, PyString.fromInterned("Also known as round-towards-0, truncate."));
      var1.setlocal("_round_down", var5);
      var3 = null;
      var1.setline(1750);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_up$78, PyString.fromInterned("Rounds away from 0."));
      var1.setlocal("_round_up", var5);
      var3 = null;
      var1.setline(1754);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_half_up$79, PyString.fromInterned("Rounds 5 up (away from 0)"));
      var1.setlocal("_round_half_up", var5);
      var3 = null;
      var1.setline(1763);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_half_down$80, PyString.fromInterned("Round 5 down"));
      var1.setlocal("_round_half_down", var5);
      var3 = null;
      var1.setline(1770);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_half_even$81, PyString.fromInterned("Round 5 to even, rest to nearest."));
      var1.setlocal("_round_half_even", var5);
      var3 = null;
      var1.setline(1778);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_ceiling$82, PyString.fromInterned("Rounds up (not away from 0 if negative.)"));
      var1.setlocal("_round_ceiling", var5);
      var3 = null;
      var1.setline(1785);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_floor$83, PyString.fromInterned("Rounds down (not towards 0 if negative)"));
      var1.setlocal("_round_floor", var5);
      var3 = null;
      var1.setline(1792);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round_05up$84, PyString.fromInterned("Round down unless digit prec-1 is 0 or 5."));
      var1.setlocal("_round_05up", var5);
      var3 = null;
      var1.setline(1799);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, fma$85, PyString.fromInterned("Fused multiply-add.\n\n        Returns self*other+third with no rounding of the intermediate\n        product self*other.\n\n        self and other are multiplied together, with no rounding of\n        the result.  The third operand is then added to the result,\n        and a single final rounding is performed.\n        "));
      var1.setlocal("fma", var5);
      var3 = null;
      var1.setline(1843);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _power_modulo$86, PyString.fromInterned("Three argument version of __pow__"));
      var1.setlocal("_power_modulo", var5);
      var3 = null;
      var1.setline(1927);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _power_exact$87, PyString.fromInterned("Attempt to compute self**other exactly.\n\n        Given Decimals self and other and an integer p, attempt to\n        compute an exact result for the power self**other, with p\n        digits of precision.  Return None if self**other is not\n        exactly representable in p digits.\n\n        Assumes that elimination of special cases has already been\n        performed: self and other must both be nonspecial; self must\n        be positive and not numerically equal to 1; other must be\n        nonzero.  For efficiency, other._exp should not be too large,\n        so that 10**abs(other._exp) is a feasible calculation."));
      var1.setlocal("_power_exact", var5);
      var3 = null;
      var1.setline(2142);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __pow__$88, PyString.fromInterned("Return self ** other [ % modulo].\n\n        With two arguments, compute self**other.\n\n        With three arguments, compute (self**other) % modulo.  For the\n        three argument form, the following restrictions on the\n        arguments hold:\n\n         - all three arguments must be integral\n         - other must be nonnegative\n         - either self or other (or both) must be nonzero\n         - modulo must be nonzero and must have at most p digits,\n           where p is the context precision.\n\n        If any of these restrictions is violated the InvalidOperation\n        flag is raised.\n\n        The result of pow(self, other, modulo) is identical to the\n        result that would be obtained by computing (self**other) %\n        modulo with unbounded precision, but is computed more\n        efficiently.  It is always exact.\n        "));
      var1.setlocal("__pow__", var5);
      var3 = null;
      var1.setline(2358);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __rpow__$89, PyString.fromInterned("Swaps self/other and returns __pow__."));
      var1.setlocal("__rpow__", var5);
      var3 = null;
      var1.setline(2365);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, normalize$90, PyString.fromInterned("Normalize- strip trailing 0s, change anything equal to 0 to 0e0"));
      var1.setlocal("normalize", var5);
      var3 = null;
      var1.setline(2390);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, quantize$91, PyString.fromInterned("Quantize self so its exponent is the same as that of exp.\n\n        Similar to self._rescale(exp._exp) but with error checking.\n        "));
      var1.setlocal("quantize", var5);
      var3 = null;
      var1.setline(2461);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, same_quantum$92, PyString.fromInterned("Return True if self and other have the same exponent; otherwise\n        return False.\n\n        If either operand is a special value, the following rules are used:\n           * return True if both operands are infinities\n           * return True if both operands are NaNs\n           * otherwise, return False.\n        "));
      var1.setlocal("same_quantum", var5);
      var3 = null;
      var1.setline(2476);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _rescale$93, PyString.fromInterned("Rescale self so that the exponent is exp, either by padding with zeros\n        or by truncating digits, using the given rounding mode.\n\n        Specials are returned without change.  This operation is\n        quiet: it raises no flags, and uses no information from the\n        context.\n\n        exp = exp to scale to (an integer)\n        rounding = rounding mode\n        "));
      var1.setlocal("_rescale", var5);
      var3 = null;
      var1.setline(2510);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _round$94, PyString.fromInterned("Round a nonzero, nonspecial Decimal to a fixed number of\n        significant figures, using the given rounding mode.\n\n        Infinities, NaNs and zeros are returned unaltered.\n\n        This operation is quiet: it raises no flags, and uses no\n        information from the context.\n\n        "));
      var1.setlocal("_round", var5);
      var3 = null;
      var1.setline(2533);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, to_integral_exact$95, PyString.fromInterned("Rounds to a nearby integer.\n\n        If no rounding mode is specified, take the rounding mode from\n        the context.  This method raises the Rounded and Inexact flags\n        when appropriate.\n\n        See also: to_integral_value, which does exactly the same as\n        this method except that it doesn't raise Inexact or Rounded.\n        "));
      var1.setlocal("to_integral_exact", var5);
      var3 = null;
      var1.setline(2562);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, to_integral_value$96, PyString.fromInterned("Rounds to the nearest integer, without raising inexact, rounded."));
      var1.setlocal("to_integral_value", var5);
      var3 = null;
      var1.setline(2579);
      var6 = var1.getname("to_integral_value");
      var1.setlocal("to_integral", var6);
      var3 = null;
      var1.setline(2581);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, sqrt$97, PyString.fromInterned("Return the square root of self."));
      var1.setlocal("sqrt", var5);
      var3 = null;
      var1.setline(2680);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, max$98, PyString.fromInterned("Returns the larger value.\n\n        Like max(self, other) except if one is not a number, returns\n        NaN (and signals if one is sNaN).  Also rounds.\n        "));
      var1.setlocal("max", var5);
      var3 = null;
      var1.setline(2722);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, min$99, PyString.fromInterned("Returns the smaller value.\n\n        Like min(self, other) except if one is not a number, returns\n        NaN (and signals if one is sNaN).  Also rounds.\n        "));
      var1.setlocal("min", var5);
      var3 = null;
      var1.setline(2756);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _isinteger$100, PyString.fromInterned("Returns whether self is an integer"));
      var1.setlocal("_isinteger", var5);
      var3 = null;
      var1.setline(2765);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _iseven$101, PyString.fromInterned("Returns True if self is even.  Assumes self is an integer."));
      var1.setlocal("_iseven", var5);
      var3 = null;
      var1.setline(2771);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, adjusted$102, PyString.fromInterned("Return the adjusted exponent of self"));
      var1.setlocal("adjusted", var5);
      var3 = null;
      var1.setline(2779);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, canonical$103, PyString.fromInterned("Returns the same Decimal object.\n\n        As we do not have different encodings for the same number, the\n        received object already is in its canonical form.\n        "));
      var1.setlocal("canonical", var5);
      var3 = null;
      var1.setline(2787);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, compare_signal$104, PyString.fromInterned("Compares self to the other operand numerically.\n\n        It's pretty much like compare(), but all NaNs signal, with signaling\n        NaNs taking precedence over quiet NaNs.\n        "));
      var1.setlocal("compare_signal", var5);
      var3 = null;
      var1.setline(2799);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, compare_total$105, PyString.fromInterned("Compares self to other using the abstract representations.\n\n        This is not like the standard compare, which use their numerical\n        value. Note that a total ordering is defined for all possible abstract\n        representations.\n        "));
      var1.setlocal("compare_total", var5);
      var3 = null;
      var1.setline(2872);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, compare_total_mag$106, PyString.fromInterned("Compares self to other using abstract repr., ignoring sign.\n\n        Like compare_total, but with operand's sign ignored and assumed to be 0.\n        "));
      var1.setlocal("compare_total_mag", var5);
      var3 = null;
      var1.setline(2883);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy_abs$107, PyString.fromInterned("Returns a copy with the sign set to 0. "));
      var1.setlocal("copy_abs", var5);
      var3 = null;
      var1.setline(2887);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy_negate$108, PyString.fromInterned("Returns a copy with the sign inverted."));
      var1.setlocal("copy_negate", var5);
      var3 = null;
      var1.setline(2894);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy_sign$109, PyString.fromInterned("Returns self with the sign of other."));
      var1.setlocal("copy_sign", var5);
      var3 = null;
      var1.setline(2900);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, exp$110, PyString.fromInterned("Returns e ** self."));
      var1.setlocal("exp", var5);
      var3 = null;
      var1.setline(2975);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_canonical$111, PyString.fromInterned("Return True if self is canonical; otherwise return False.\n\n        Currently, the encoding of a Decimal instance is always\n        canonical, so this method returns True for any Decimal.\n        "));
      var1.setlocal("is_canonical", var5);
      var3 = null;
      var1.setline(2983);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_finite$112, PyString.fromInterned("Return True if self is finite; otherwise return False.\n\n        A Decimal instance is considered finite if it is neither\n        infinite nor a NaN.\n        "));
      var1.setlocal("is_finite", var5);
      var3 = null;
      var1.setline(2991);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_infinite$113, PyString.fromInterned("Return True if self is infinite; otherwise return False."));
      var1.setlocal("is_infinite", var5);
      var3 = null;
      var1.setline(2995);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_nan$114, PyString.fromInterned("Return True if self is a qNaN or sNaN; otherwise return False."));
      var1.setlocal("is_nan", var5);
      var3 = null;
      var1.setline(2999);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, is_normal$115, PyString.fromInterned("Return True if self is a normal number; otherwise return False."));
      var1.setlocal("is_normal", var5);
      var3 = null;
      var1.setline(3007);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_qnan$116, PyString.fromInterned("Return True if self is a quiet NaN; otherwise return False."));
      var1.setlocal("is_qnan", var5);
      var3 = null;
      var1.setline(3011);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_signed$117, PyString.fromInterned("Return True if self is negative; otherwise return False."));
      var1.setlocal("is_signed", var5);
      var3 = null;
      var1.setline(3015);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_snan$118, PyString.fromInterned("Return True if self is a signaling NaN; otherwise return False."));
      var1.setlocal("is_snan", var5);
      var3 = null;
      var1.setline(3019);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, is_subnormal$119, PyString.fromInterned("Return True if self is subnormal; otherwise return False."));
      var1.setlocal("is_subnormal", var5);
      var3 = null;
      var1.setline(3027);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_zero$120, PyString.fromInterned("Return True if self is a zero; otherwise return False."));
      var1.setlocal("is_zero", var5);
      var3 = null;
      var1.setline(3031);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _ln_exp_bound$121, PyString.fromInterned("Compute a lower bound for the adjusted exponent of self.ln().\n        In other words, compute r such that self.ln() >= 10**r.  Assumes\n        that self is finite and positive and that self != 1.\n        "));
      var1.setlocal("_ln_exp_bound", var5);
      var3 = null;
      var1.setline(3056);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, ln$122, PyString.fromInterned("Returns the natural (base e) logarithm of self."));
      var1.setlocal("ln", var5);
      var3 = null;
      var1.setline(3106);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _log10_exp_bound$123, PyString.fromInterned("Compute a lower bound for the adjusted exponent of self.log10().\n        In other words, find r such that self.log10() >= 10**r.\n        Assumes that self is finite and positive and that self != 1.\n        "));
      var1.setlocal("_log10_exp_bound", var5);
      var3 = null;
      var1.setline(3136);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, log10$124, PyString.fromInterned("Returns the base 10 logarithm of self."));
      var1.setlocal("log10", var5);
      var3 = null;
      var1.setline(3187);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, logb$125, PyString.fromInterned(" Returns the exponent of the magnitude of self's MSD.\n\n        The result is the integer which is the exponent of the magnitude\n        of the most significant digit of self (as though it were truncated\n        to a single digit while maintaining the value of that digit and\n        without limiting the resulting exponent).\n        "));
      var1.setlocal("logb", var5);
      var3 = null;
      var1.setline(3217);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _islogical$126, PyString.fromInterned("Return True if self is a logical operand.\n\n        For being logical, it must be a finite number with a sign of 0,\n        an exponent of 0, and a coefficient whose digits must all be\n        either 0 or 1.\n        "));
      var1.setlocal("_islogical", var5);
      var3 = null;
      var1.setline(3231);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _fill_logical$127, (PyObject)null);
      var1.setlocal("_fill_logical", var5);
      var3 = null;
      var1.setline(3244);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, logical_and$128, PyString.fromInterned("Applies an 'and' operation between self and other's digits."));
      var1.setlocal("logical_and", var5);
      var3 = null;
      var1.setline(3261);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, logical_invert$129, PyString.fromInterned("Invert all its digits."));
      var1.setlocal("logical_invert", var5);
      var3 = null;
      var1.setline(3268);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, logical_or$130, PyString.fromInterned("Applies an 'or' operation between self and other's digits."));
      var1.setlocal("logical_or", var5);
      var3 = null;
      var1.setline(3285);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, logical_xor$131, PyString.fromInterned("Applies an 'xor' operation between self and other's digits."));
      var1.setlocal("logical_xor", var5);
      var3 = null;
      var1.setline(3302);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, max_mag$132, PyString.fromInterned("Compares the values numerically with their sign ignored."));
      var1.setlocal("max_mag", var5);
      var3 = null;
      var1.setline(3332);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, min_mag$133, PyString.fromInterned("Compares the values numerically with their sign ignored."));
      var1.setlocal("min_mag", var5);
      var3 = null;
      var1.setline(3362);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, next_minus$134, PyString.fromInterned("Returns the largest representable number smaller than itself."));
      var1.setlocal("next_minus", var5);
      var3 = null;
      var1.setline(3385);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, next_plus$135, PyString.fromInterned("Returns the smallest representable number larger than itself."));
      var1.setlocal("next_plus", var5);
      var3 = null;
      var1.setline(3408);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, next_toward$136, PyString.fromInterned("Returns the number closest to self, in the direction towards other.\n\n        The result is the closest representable number to self\n        (excluding self) that is in the direction towards other,\n        unless both have the same value.  If the two operands are\n        numerically equal, then the result is a copy of self with the\n        sign set to be the same as the sign of other.\n        "));
      var1.setlocal("next_toward", var5);
      var3 = null;
      var1.setline(3454);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, number_class$137, PyString.fromInterned("Returns an indication of the class of self.\n\n        The class is one of the following strings:\n          sNaN\n          NaN\n          -Infinity\n          -Normal\n          -Subnormal\n          -Zero\n          +Zero\n          +Subnormal\n          +Normal\n          +Infinity\n        "));
      var1.setlocal("number_class", var5);
      var3 = null;
      var1.setline(3496);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, radix$138, PyString.fromInterned("Just returns 10, as this is Decimal, :)"));
      var1.setlocal("radix", var5);
      var3 = null;
      var1.setline(3500);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, rotate$139, PyString.fromInterned("Returns a rotated copy of self, value-of-other times."));
      var1.setlocal("rotate", var5);
      var3 = null;
      var1.setline(3533);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, scaleb$140, PyString.fromInterned("Returns self operand after adding the second value to its exp."));
      var1.setlocal("scaleb", var5);
      var3 = null;
      var1.setline(3558);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, shift$141, PyString.fromInterned("Returns a shifted copy of self, value-of-other times."));
      var1.setlocal("shift", var5);
      var3 = null;
      var1.setline(3597);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$142, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      var1.setline(3600);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __copy__$143, (PyObject)null);
      var1.setlocal("__copy__", var5);
      var3 = null;
      var1.setline(3605);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __deepcopy__$144, (PyObject)null);
      var1.setlocal("__deepcopy__", var5);
      var3 = null;
      var1.setline(3612);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, __format__$145, PyString.fromInterned("Format a Decimal instance according to the given specifier.\n\n        The specifier should be a standard format specifier, with the\n        form described in PEP 3101.  Formatting types 'e', 'E', 'f',\n        'F', 'g', 'G', 'n' and '%' are supported.  If the formatting\n        type is omitted it defaults to 'g' or 'G', depending on the\n        value of context.capitals.\n        "));
      var1.setlocal("__format__", var5);
      var3 = null;
      var1.setline(3693);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __tojava__$146, (PyObject)null);
      var1.setlocal("__tojava__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$31(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyString.fromInterned("Create a decimal point instance.\n\n        >>> Decimal('3.14')              # string input\n        Decimal('3.14')\n        >>> Decimal((0, (3, 1, 4), -2))  # tuple (sign, digit_tuple, exponent)\n        Decimal('3.14')\n        >>> Decimal(314)                 # int or long\n        Decimal('314')\n        >>> Decimal(Decimal(314))        # another decimal instance\n        Decimal('314')\n        >>> Decimal('  3.14  \\n')        # leading and trailing whitespace okay\n        Decimal('3.14')\n        ");
      var1.setline(546);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(550);
      PyObject var4;
      PyString var9;
      PyInteger var11;
      PyObject var10000;
      PyObject var10002;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(551);
         var3 = var1.getglobal("_parser").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(552);
         var3 = var1.getlocal(4);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(553);
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(554);
               var3 = var1.getglobal("getcontext").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;
            }

            var1.setline(555);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("ConversionSyntax"), PyString.fromInterned("Invalid literal for Decimal: %r")._mod(var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(558);
            var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sign"));
            var10000 = var4._eq(PyString.fromInterned("-"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(559);
               var11 = Py.newInteger(1);
               var1.getlocal(3).__setattr__((String)"_sign", var11);
               var4 = null;
            } else {
               var1.setline(561);
               var11 = Py.newInteger(0);
               var1.getlocal(3).__setattr__((String)"_sign", var11);
               var4 = null;
            }

            var1.setline(562);
            var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("int"));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(563);
            var4 = var1.getlocal(5);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(565);
               Object var16 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("frac"));
               if (!((PyObject)var16).__nonzero__()) {
                  var16 = PyString.fromInterned("");
               }

               Object var12 = var16;
               var1.setlocal(6, (PyObject)var12);
               var4 = null;
               var1.setline(566);
               var10000 = var1.getglobal("int");
               Object var14 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exp"));
               if (!((PyObject)var14).__nonzero__()) {
                  var14 = PyString.fromInterned("0");
               }

               var4 = var10000.__call__((ThreadState)var2, (PyObject)var14);
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(567);
               var4 = var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(5)._add(var1.getlocal(6))));
               var1.getlocal(3).__setattr__("_int", var4);
               var4 = null;
               var1.setline(568);
               var4 = var1.getlocal(7)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var1.getlocal(3).__setattr__("_exp", var4);
               var4 = null;
               var1.setline(569);
               var4 = var1.getglobal("False");
               var1.getlocal(3).__setattr__("_is_special", var4);
               var4 = null;
            } else {
               var1.setline(571);
               var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("diag"));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(572);
               var4 = var1.getlocal(8);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(574);
                  var10000 = var1.getglobal("str");
                  var10002 = var1.getglobal("int");
                  Object var15 = var1.getlocal(8);
                  if (!((PyObject)var15).__nonzero__()) {
                     var15 = PyString.fromInterned("0");
                  }

                  var4 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var15)).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
                  var1.getlocal(3).__setattr__("_int", var4);
                  var4 = null;
                  var1.setline(575);
                  if (var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("signal")).__nonzero__()) {
                     var1.setline(576);
                     var9 = PyString.fromInterned("N");
                     var1.getlocal(3).__setattr__((String)"_exp", var9);
                     var4 = null;
                  } else {
                     var1.setline(578);
                     var9 = PyString.fromInterned("n");
                     var1.getlocal(3).__setattr__((String)"_exp", var9);
                     var4 = null;
                  }
               } else {
                  var1.setline(581);
                  var9 = PyString.fromInterned("0");
                  var1.getlocal(3).__setattr__((String)"_int", var9);
                  var4 = null;
                  var1.setline(582);
                  var9 = PyString.fromInterned("F");
                  var1.getlocal(3).__setattr__((String)"_exp", var9);
                  var4 = null;
               }

               var1.setline(583);
               var4 = var1.getglobal("True");
               var1.getlocal(3).__setattr__("_is_special", var4);
               var4 = null;
            }

            var1.setline(584);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(587);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
            var1.setline(588);
            var4 = var1.getlocal(1);
            var10000 = var4._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(589);
               var11 = Py.newInteger(0);
               var1.getlocal(3).__setattr__((String)"_sign", var11);
               var4 = null;
            } else {
               var1.setline(591);
               var11 = Py.newInteger(1);
               var1.getlocal(3).__setattr__((String)"_sign", var11);
               var4 = null;
            }

            var1.setline(592);
            var11 = Py.newInteger(0);
            var1.getlocal(3).__setattr__((String)"_exp", var11);
            var4 = null;
            var1.setline(593);
            var4 = var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(1)));
            var1.getlocal(3).__setattr__("_int", var4);
            var4 = null;
            var1.setline(594);
            var4 = var1.getglobal("False");
            var1.getlocal(3).__setattr__("_is_special", var4);
            var4 = null;
            var1.setline(595);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(598);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Decimal")).__nonzero__()) {
               var1.setline(599);
               var4 = var1.getlocal(1).__getattr__("_exp");
               var1.getlocal(3).__setattr__("_exp", var4);
               var4 = null;
               var1.setline(600);
               var4 = var1.getlocal(1).__getattr__("_sign");
               var1.getlocal(3).__setattr__("_sign", var4);
               var4 = null;
               var1.setline(601);
               var4 = var1.getlocal(1).__getattr__("_int");
               var1.getlocal(3).__setattr__("_int", var4);
               var4 = null;
               var1.setline(602);
               var4 = var1.getlocal(1).__getattr__("_is_special");
               var1.getlocal(3).__setattr__("_is_special", var4);
               var4 = null;
               var1.setline(603);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(606);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_WorkRep")).__nonzero__()) {
                  var1.setline(607);
                  var4 = var1.getlocal(1).__getattr__("sign");
                  var1.getlocal(3).__setattr__("_sign", var4);
                  var4 = null;
                  var1.setline(608);
                  var4 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("int"));
                  var1.getlocal(3).__setattr__("_int", var4);
                  var4 = null;
                  var1.setline(609);
                  var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("exp"));
                  var1.getlocal(3).__setattr__("_exp", var4);
                  var4 = null;
                  var1.setline(610);
                  var4 = var1.getglobal("False");
                  var1.getlocal(3).__setattr__("_is_special", var4);
                  var4 = null;
                  var1.setline(611);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(614);
                  if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
                     var1.setline(615);
                     var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                     var10000 = var4._ne(Py.newInteger(3));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(616);
                        throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid tuple size in creation of Decimal from list or tuple.  The list or tuple should have exactly three elements.")));
                     } else {
                        var1.setline(620);
                        var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(0)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")})));
                        if (var10000.__nonzero__()) {
                           var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                           var10000 = var4._in(new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1)}));
                           var4 = null;
                        }

                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(621);
                           throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid sign.  The first value in the tuple should be an integer; either 0 for a positive number or 1 for a negative number.")));
                        } else {
                           var1.setline(624);
                           var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                           var1.getlocal(3).__setattr__("_sign", var4);
                           var4 = null;
                           var1.setline(625);
                           var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                           var10000 = var4._eq(PyString.fromInterned("F"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(627);
                              var9 = PyString.fromInterned("0");
                              var1.getlocal(3).__setattr__((String)"_int", var9);
                              var4 = null;
                              var1.setline(628);
                              var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                              var1.getlocal(3).__setattr__("_exp", var4);
                              var4 = null;
                              var1.setline(629);
                              var4 = var1.getglobal("True");
                              var1.getlocal(3).__setattr__("_is_special", var4);
                              var4 = null;
                           } else {
                              var1.setline(632);
                              PyList var10 = new PyList(Py.EmptyObjects);
                              var1.setlocal(9, var10);
                              var4 = null;
                              var1.setline(633);
                              var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__iter__();

                              while(true) {
                                 var1.setline(633);
                                 PyObject var5 = var4.__iternext__();
                                 if (var5 == null) {
                                    var1.setline(642);
                                    var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                                    var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("n"), PyString.fromInterned("N")}));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(644);
                                       var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(9)));
                                       var1.getlocal(3).__setattr__("_int", var4);
                                       var4 = null;
                                       var1.setline(645);
                                       var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                                       var1.getlocal(3).__setattr__("_exp", var4);
                                       var4 = null;
                                       var1.setline(646);
                                       var4 = var1.getglobal("True");
                                       var1.getlocal(3).__setattr__("_is_special", var4);
                                       var4 = null;
                                    } else {
                                       var1.setline(647);
                                       if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(2)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
                                          var1.setline(653);
                                          throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The third value in the tuple must be an integer, or one of the strings 'F', 'n', 'N'.")));
                                       }

                                       var1.setline(649);
                                       var10000 = PyString.fromInterned("").__getattr__("join");
                                       var10002 = var1.getglobal("map");
                                       PyObject var10004 = var1.getglobal("str");
                                       Object var10005 = var1.getlocal(9);
                                       if (!((PyObject)var10005).__nonzero__()) {
                                          var10005 = new PyList(new PyObject[]{Py.newInteger(0)});
                                       }

                                       var4 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)var10005));
                                       var1.getlocal(3).__setattr__("_int", var4);
                                       var4 = null;
                                       var1.setline(650);
                                       var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                                       var1.getlocal(3).__setattr__("_exp", var4);
                                       var4 = null;
                                       var1.setline(651);
                                       var4 = var1.getglobal("False");
                                       var1.getlocal(3).__setattr__("_is_special", var4);
                                       var4 = null;
                                    }
                                    break;
                                 }

                                 var1.setlocal(10, var5);
                                 var1.setline(634);
                                 var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")})));
                                 PyInteger var6;
                                 PyObject var8;
                                 if (var10000.__nonzero__()) {
                                    var6 = Py.newInteger(0);
                                    PyObject var10001 = var1.getlocal(10);
                                    PyInteger var13 = var6;
                                    var8 = var10001;
                                    PyObject var7;
                                    if ((var7 = var13._le(var10001)).__nonzero__()) {
                                       var7 = var8._le(Py.newInteger(9));
                                    }

                                    var10000 = var7;
                                    var6 = null;
                                 }

                                 if (!var10000.__nonzero__()) {
                                    var1.setline(639);
                                    throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The second value in the tuple must be composed of integers in the range 0 through 9.")));
                                 }

                                 var1.setline(636);
                                 var10000 = var1.getlocal(9);
                                 if (!var10000.__nonzero__()) {
                                    var8 = var1.getlocal(10);
                                    var10000 = var8._ne(Py.newInteger(0));
                                    var6 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(637);
                                    var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(10));
                                 }
                              }
                           }

                           var1.setline(656);
                           var3 = var1.getlocal(3);
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }
                  } else {
                     var1.setline(658);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
                        var1.setline(659);
                        var4 = var1.getglobal("Decimal").__getattr__("from_float").__call__(var2, var1.getlocal(1));
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(660);
                        var4 = var1.getlocal(1).__getattr__("_exp");
                        var1.getlocal(3).__setattr__("_exp", var4);
                        var4 = null;
                        var1.setline(661);
                        var4 = var1.getlocal(1).__getattr__("_sign");
                        var1.getlocal(3).__setattr__("_sign", var4);
                        var4 = null;
                        var1.setline(662);
                        var4 = var1.getlocal(1).__getattr__("_int");
                        var1.getlocal(3).__setattr__("_int", var4);
                        var4 = null;
                        var1.setline(663);
                        var4 = var1.getlocal(1).__getattr__("_is_special");
                        var1.getlocal(3).__setattr__("_is_special", var4);
                        var4 = null;
                        var1.setline(664);
                        var3 = var1.getlocal(3);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(666);
                        throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert %r to Decimal")._mod(var1.getlocal(1))));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject from_float$32(PyFrame var1, ThreadState var2) {
      var1.setline(690);
      PyString.fromInterned("Converts a float to a decimal number, exactly.\n\n        Note that Decimal.from_float(0.1) is not the same as Decimal('0.1').\n        Since 0.1 is not exactly representable in binary floating point, the\n        value is stored as the nearest representable value which is\n        0x1.999999999999ap-4.  The exact equivalent of the value in decimal\n        is 0.1000000000000000055511151231257827021181583404541015625.\n\n        >>> Decimal.from_float(0.1)\n        Decimal('0.1000000000000000055511151231257827021181583404541015625')\n        >>> Decimal.from_float(float('nan'))\n        Decimal('NaN')\n        >>> Decimal.from_float(float('inf'))\n        Decimal('Infinity')\n        >>> Decimal.from_float(-float('inf'))\n        Decimal('-Infinity')\n        >>> Decimal.from_float(-0.0)\n        Decimal('-0')\n\n        ");
      var1.setline(691);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(692);
         var3 = var1.getlocal(0).__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(693);
         PyObject var10000 = var1.getglobal("_math").__getattr__("isinf").__call__(var2, var1.getlocal(1));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("_math").__getattr__("isnan").__call__(var2, var1.getlocal(1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(694);
            var3 = var1.getlocal(0).__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(695);
            PyObject var4 = var1.getglobal("_math").__getattr__("copysign").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0), (PyObject)var1.getlocal(1));
            var10000 = var4._eq(Py.newFloat(1.0));
            var4 = null;
            PyInteger var7;
            if (var10000.__nonzero__()) {
               var1.setline(696);
               var7 = Py.newInteger(0);
               var1.setlocal(2, var7);
               var4 = null;
            } else {
               var1.setline(698);
               var7 = Py.newInteger(1);
               var1.setlocal(2, var7);
               var4 = null;
            }

            var1.setline(699);
            var4 = var1.getglobal("abs").__call__(var2, var1.getlocal(1)).__getattr__("as_integer_ratio").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(700);
            var4 = var1.getlocal(4).__getattr__("bit_length").__call__(var2)._sub(Py.newInteger(1));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(701);
            var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(2), var1.getglobal("str").__call__(var2, var1.getlocal(3)._mul(Py.newInteger(5)._pow(var1.getlocal(5)))), var1.getlocal(5).__neg__());
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(702);
            var4 = var1.getlocal(0);
            var10000 = var4._is(var1.getglobal("Decimal"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(703);
               var3 = var1.getlocal(6);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(705);
               var3 = var1.getlocal(0).__call__(var2, var1.getlocal(6));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _isnan$33(PyFrame var1, ThreadState var2) {
      var1.setline(714);
      PyString.fromInterned("Returns whether the number is not actually one.\n\n        0 if a number\n        1 if NaN\n        2 if sNaN\n        ");
      var1.setline(715);
      PyInteger var5;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(716);
         PyObject var3 = var1.getlocal(0).__getattr__("_exp");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(717);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(718);
            var5 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(719);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("N"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(720);
            var5 = Py.newInteger(2);
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(721);
      var5 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _isinfinity$34(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyString.fromInterned("Returns whether the number is infinite\n\n        0 if finite or not a number\n        1 if +INF\n        -1 if -INF\n        ");
      var1.setline(730);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._eq(PyString.fromInterned("F"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(731);
         if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
            var1.setline(732);
            var4 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(733);
            var4 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var4;
         }
      } else {
         var1.setline(734);
         var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _check_nans$35(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      PyString.fromInterned("Returns whether the number is not actually one.\n\n        if self, other are sNaN, signal\n        if self, other are NaN return nan\n        return 0\n\n        Done before operations.\n        ");
      var1.setline(746);
      PyObject var3 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(747);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(748);
         var3 = var1.getglobal("False");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(750);
         var3 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(752);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
      }

      if (var10000.__nonzero__()) {
         var1.setline(753);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(754);
            var3 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(756);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(757);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(759);
            PyObject var4 = var1.getlocal(4);
            var10000 = var4._eq(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(760);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(762);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(763);
                  var3 = var1.getlocal(0).__getattr__("_fix_nan").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(765);
                  var3 = var1.getlocal(1).__getattr__("_fix_nan").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      } else {
         var1.setline(766);
         PyInteger var5 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _compare_check_nans$36(PyFrame var1, ThreadState var2) {
      var1.setline(778);
      PyString.fromInterned("Version of _check_nans used for the signaling comparisons\n        compare_signal, __le__, __lt__, __ge__, __gt__.\n\n        Signal InvalidOperation if either self or other is a (quiet\n        or signaling) NaN.  Signaling NaNs take precedence over quiet\n        NaNs.\n\n        Return 0 if neither operand is a NaN.\n\n        ");
      var1.setline(779);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(780);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(782);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      if (var10000.__nonzero__()) {
         var1.setline(783);
         if (var1.getlocal(0).__getattr__("is_snan").__call__(var2).__nonzero__()) {
            var1.setline(784);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("comparison involving sNaN"), (PyObject)var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(787);
         if (var1.getlocal(1).__getattr__("is_snan").__call__(var2).__nonzero__()) {
            var1.setline(788);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("comparison involving sNaN"), (PyObject)var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(791);
         if (var1.getlocal(0).__getattr__("is_qnan").__call__(var2).__nonzero__()) {
            var1.setline(792);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("comparison involving NaN"), (PyObject)var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(795);
         if (var1.getlocal(1).__getattr__("is_qnan").__call__(var2).__nonzero__()) {
            var1.setline(796);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("comparison involving NaN"), (PyObject)var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(799);
      PyInteger var4 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __nonzero__$37(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyString.fromInterned("Return True if self is nonzero; otherwise return False.\n\n        NaNs and infinities are considered nonzero.\n        ");
      var1.setline(806);
      PyObject var10000 = var1.getlocal(0).__getattr__("_is_special");
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_int");
         var10000 = var3._ne(PyString.fromInterned("0"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _cmp$38(PyFrame var1, ThreadState var2) {
      var1.setline(812);
      PyString.fromInterned("Compare the two non-NaN decimal instances self and other.\n\n        Returns -1 if self < other, 0 if self == other and 1\n        if self > other.  This routine is for internal use only.");
      var1.setline(814);
      PyObject var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var3;
      PyObject var4;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(815);
         var3 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(816);
         var3 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(817);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(818);
            var5 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(819);
            var4 = var1.getlocal(2);
            var10000 = var4._lt(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(820);
               var5 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(822);
               var5 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var5;
            }
         }
      } else {
         var1.setline(825);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(826);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(827);
               var5 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(829);
               var3 = Py.newInteger(-1)._pow(var1.getlocal(1).__getattr__("_sign")).__neg__();
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(830);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(831);
               var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(834);
               var4 = var1.getlocal(1).__getattr__("_sign");
               var10000 = var4._lt(var1.getlocal(0).__getattr__("_sign"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(835);
                  var5 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(836);
                  var4 = var1.getlocal(0).__getattr__("_sign");
                  var10000 = var4._lt(var1.getlocal(1).__getattr__("_sign"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(837);
                     var5 = Py.newInteger(1);
                     var1.f_lasti = -1;
                     return var5;
                  } else {
                     var1.setline(839);
                     var4 = var1.getlocal(0).__getattr__("adjusted").__call__(var2);
                     var1.setlocal(4, var4);
                     var4 = null;
                     var1.setline(840);
                     var4 = var1.getlocal(1).__getattr__("adjusted").__call__(var2);
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(841);
                     var4 = var1.getlocal(4);
                     var10000 = var4._eq(var1.getlocal(5));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(842);
                        var4 = var1.getlocal(0).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(1).__getattr__("_exp"))));
                        var1.setlocal(6, var4);
                        var4 = null;
                        var1.setline(843);
                        var4 = var1.getlocal(1).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(1).__getattr__("_exp")._sub(var1.getlocal(0).__getattr__("_exp"))));
                        var1.setlocal(7, var4);
                        var4 = null;
                        var1.setline(844);
                        var4 = var1.getlocal(6);
                        var10000 = var4._eq(var1.getlocal(7));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(845);
                           var5 = Py.newInteger(0);
                           var1.f_lasti = -1;
                           return var5;
                        } else {
                           var1.setline(846);
                           var4 = var1.getlocal(6);
                           var10000 = var4._lt(var1.getlocal(7));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(847);
                              var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign")).__neg__();
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(849);
                              var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign"));
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     } else {
                        var1.setline(850);
                        var4 = var1.getlocal(4);
                        var10000 = var4._gt(var1.getlocal(5));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(851);
                           var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign"));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(853);
                           var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign")).__neg__();
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject __eq__$39(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(874);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(875);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(876);
         if (var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
            var1.setline(877);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(878);
            PyObject var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._eq(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject __ne__$40(PyFrame var1, ThreadState var2) {
      var1.setline(881);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(882);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(883);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(884);
         if (var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
            var1.setline(885);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(886);
            PyObject var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._ne(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject __lt__$41(PyFrame var1, ThreadState var2) {
      var1.setline(889);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(890);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(891);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(892);
         PyObject var6 = var1.getlocal(0).__getattr__("_compare_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(893);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(894);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(895);
            var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._lt(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject __le__$42(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(899);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(900);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(901);
         PyObject var6 = var1.getlocal(0).__getattr__("_compare_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(902);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(903);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(904);
            var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._le(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject __gt__$43(PyFrame var1, ThreadState var2) {
      var1.setline(907);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(908);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(909);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(910);
         PyObject var6 = var1.getlocal(0).__getattr__("_compare_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(911);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(912);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(913);
            var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._gt(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject __ge__$44(PyFrame var1, ThreadState var2) {
      var1.setline(916);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"allow_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(917);
      var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(918);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(919);
         PyObject var6 = var1.getlocal(0).__getattr__("_compare_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(920);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(921);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(922);
            var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
            var10000 = var6._ge(Py.newInteger(0));
            var4 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject compare$45(PyFrame var1, ThreadState var2) {
      var1.setline(932);
      PyString.fromInterned("Compares one to another.\n\n        -1 => a < b\n        0  => a = b\n        1  => a > b\n        NaN => one is NaN\n        Like __cmp__, but returns Decimal instances.\n        ");
      var1.setline(933);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(936);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_is_special");
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(937);
         var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(938);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(939);
            var5 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(941);
      var5 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __hash__$46(PyFrame var1, ThreadState var2) {
      var1.setline(944);
      PyString.fromInterned("x.__hash__() <==> hash(x)");
      var1.setline(956);
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(957);
         if (var1.getlocal(0).__getattr__("is_snan").__call__(var2).__nonzero__()) {
            var1.setline(958);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot hash a signaling NaN value.")));
         } else {
            var1.setline(959);
            PyInteger var5;
            if (var1.getlocal(0).__getattr__("is_nan").__call__(var2).__nonzero__()) {
               var1.setline(961);
               var5 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(965);
               if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
                  var1.setline(966);
                  var5 = Py.newInteger(-271828);
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(968);
                  var5 = Py.newInteger(314159);
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      } else {
         var1.setline(974);
         PyObject var4 = var1.getglobal("float").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(975);
         var4 = var1.getglobal("Decimal").__getattr__("from_float").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var4._eq(var1.getlocal(0));
         var4 = null;
         PyObject var3;
         if (var10000.__nonzero__()) {
            var1.setline(976);
            var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(978);
            if (var1.getlocal(0).__getattr__("_isinteger").__call__(var2).__nonzero__()) {
               var1.setline(980);
               var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("long").__call__(var2, var1.getlocal(0).__getattr__("to_integral_value").__call__(var2)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(985);
               var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_sign"), var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))), var1.getlocal(0).__getattr__("_int").__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"))})));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject as_tuple$47(PyFrame var1, ThreadState var2) {
      var1.setline(993);
      PyString.fromInterned("Represents the number as a triple tuple.\n\n        To show the internals exactly as they are.\n        ");
      var1.setline(994);
      PyObject var3 = var1.getglobal("DecimalTuple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(0).__getattr__("_int"))), var1.getlocal(0).__getattr__("_exp"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$48(PyFrame var1, ThreadState var2) {
      var1.setline(997);
      PyString.fromInterned("Represents the number as an instance of Decimal.");
      var1.setline(999);
      PyObject var3 = PyString.fromInterned("Decimal('%s')")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$49(PyFrame var1, ThreadState var2) {
      var1.setline(1005);
      PyString.fromInterned("Return string representation of the number in scientific notation.\n\n        Captures all of the information in the underlying representation.\n        ");
      var1.setline(1007);
      PyObject var3 = (new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("-")})).__getitem__(var1.getlocal(0).__getattr__("_sign"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1008);
      PyObject var10000;
      PyObject var4;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(1009);
         var3 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var3._eq(PyString.fromInterned("F"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1010);
            var3 = var1.getlocal(3)._add(PyString.fromInterned("Infinity"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1011);
            var4 = var1.getlocal(0).__getattr__("_exp");
            var10000 = var4._eq(PyString.fromInterned("n"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1012);
               var3 = var1.getlocal(3)._add(PyString.fromInterned("NaN"))._add(var1.getlocal(0).__getattr__("_int"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1014);
               var3 = var1.getlocal(3)._add(PyString.fromInterned("sNaN"))._add(var1.getlocal(0).__getattr__("_int"));
               var1.f_lasti = -1;
               return var3;
            }
         }
      } else {
         var1.setline(1017);
         var4 = var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1022);
         var4 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var4._le(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(4);
            var10000 = var4._gt(Py.newInteger(-6));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1024);
            var4 = var1.getlocal(4);
            var1.setlocal(5, var4);
            var4 = null;
         } else {
            var1.setline(1025);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(1027);
               PyInteger var5 = Py.newInteger(1);
               var1.setlocal(5, var5);
               var4 = null;
            } else {
               var1.setline(1028);
               var4 = var1.getlocal(0).__getattr__("_int");
               var10000 = var4._eq(PyString.fromInterned("0"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1030);
                  var4 = var1.getlocal(4)._add(Py.newInteger(1))._mod(Py.newInteger(3))._sub(Py.newInteger(1));
                  var1.setlocal(5, var4);
                  var4 = null;
               } else {
                  var1.setline(1033);
                  var4 = var1.getlocal(4)._sub(Py.newInteger(1))._mod(Py.newInteger(3))._add(Py.newInteger(1));
                  var1.setlocal(5, var4);
                  var4 = null;
               }
            }
         }

         var1.setline(1035);
         var4 = var1.getlocal(5);
         var10000 = var4._le(Py.newInteger(0));
         var4 = null;
         PyString var6;
         if (var10000.__nonzero__()) {
            var1.setline(1036);
            var6 = PyString.fromInterned("0");
            var1.setlocal(6, var6);
            var4 = null;
            var1.setline(1037);
            var4 = PyString.fromInterned(".")._add(PyString.fromInterned("0")._mul(var1.getlocal(5).__neg__()))._add(var1.getlocal(0).__getattr__("_int"));
            var1.setlocal(7, var4);
            var4 = null;
         } else {
            var1.setline(1038);
            var4 = var1.getlocal(5);
            var10000 = var4._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1039);
               var4 = var1.getlocal(0).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(1040);
               var6 = PyString.fromInterned("");
               var1.setlocal(7, var6);
               var4 = null;
            } else {
               var1.setline(1042);
               var4 = var1.getlocal(0).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(1043);
               var4 = PyString.fromInterned(".")._add(var1.getlocal(0).__getattr__("_int").__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null));
               var1.setlocal(7, var4);
               var4 = null;
            }
         }

         var1.setline(1044);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(var1.getlocal(5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1045);
            var6 = PyString.fromInterned("");
            var1.setlocal(8, var6);
            var4 = null;
         } else {
            var1.setline(1047);
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1048);
               var4 = var1.getglobal("getcontext").__call__(var2);
               var1.setlocal(2, var4);
               var4 = null;
            }

            var1.setline(1049);
            var4 = (new PyList(new PyObject[]{PyString.fromInterned("e"), PyString.fromInterned("E")})).__getitem__(var1.getlocal(2).__getattr__("capitals"))._add(PyString.fromInterned("%+d")._mod(var1.getlocal(4)._sub(var1.getlocal(5))));
            var1.setlocal(8, var4);
            var4 = null;
         }

         var1.setline(1051);
         var3 = var1.getlocal(3)._add(var1.getlocal(6))._add(var1.getlocal(7))._add(var1.getlocal(8));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject to_eng_string$50(PyFrame var1, ThreadState var2) {
      var1.setline(1060);
      PyString.fromInterned("Convert to engineering-type string.\n\n        Engineering notation has an exponent which is a multiple of 3, so there\n        are up to 3 digits left of the decimal place.\n\n        Same rules for when in exponential and when as a value as in __str__.\n        ");
      var1.setline(1061);
      PyObject var10000 = var1.getlocal(0).__getattr__("__str__");
      PyObject[] var3 = new PyObject[]{var1.getglobal("True"), var1.getlocal(1)};
      String[] var4 = new String[]{"eng", "context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __neg__$51(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      PyString.fromInterned("Returns a copy with the sign switched.\n\n        Rounds, if it has reason.\n        ");
      var1.setline(1068);
      PyObject var10000;
      String[] var4;
      PyObject var5;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(1069);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(1070);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1071);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(1073);
      PyObject var6;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(1075);
         var6 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2);
         var1.setlocal(2, var6);
         var4 = null;
      } else {
         var1.setline(1077);
         var6 = var1.getlocal(0).__getattr__("copy_negate").__call__(var2);
         var1.setlocal(2, var6);
         var4 = null;
      }

      var1.setline(1079);
      var6 = var1.getlocal(1);
      var10000 = var6._is(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1080);
         var6 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var6);
         var4 = null;
      }

      var1.setline(1081);
      var5 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __pos__$52(PyFrame var1, ThreadState var2) {
      var1.setline(1087);
      PyString.fromInterned("Returns a copy, unless it is a sNaN.\n\n        Rounds the number (if more then precision digits)\n        ");
      var1.setline(1088);
      PyObject var10000;
      String[] var4;
      PyObject var5;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(1089);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(1090);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1091);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(1093);
      PyObject var6;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(1095);
         var6 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2);
         var1.setlocal(2, var6);
         var4 = null;
      } else {
         var1.setline(1097);
         var6 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var6);
         var4 = null;
      }

      var1.setline(1099);
      var6 = var1.getlocal(1);
      var10000 = var6._is(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1100);
         var6 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var6);
         var4 = null;
      }

      var1.setline(1101);
      var5 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __abs__$53(PyFrame var1, ThreadState var2) {
      var1.setline(1109);
      PyString.fromInterned("Returns the absolute value of self.\n\n        If the keyword argument 'round' is false, do not round.  The\n        expression self.__abs__(round=False) is equivalent to\n        self.copy_abs().\n        ");
      var1.setline(1110);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1111);
         var3 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1113);
         PyObject var10000;
         PyObject[] var4;
         String[] var5;
         PyObject var6;
         if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
            var1.setline(1114);
            var10000 = var1.getlocal(0).__getattr__("_check_nans");
            var4 = new PyObject[]{var1.getlocal(2)};
            var5 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var4, var5);
            var4 = null;
            var6 = var10000;
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(1115);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(1116);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1118);
         if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
            var1.setline(1119);
            var10000 = var1.getlocal(0).__getattr__("__neg__");
            var4 = new PyObject[]{var1.getlocal(2)};
            var5 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var4, var5);
            var4 = null;
            var6 = var10000;
            var1.setlocal(3, var6);
            var4 = null;
         } else {
            var1.setline(1121);
            var10000 = var1.getlocal(0).__getattr__("__pos__");
            var4 = new PyObject[]{var1.getlocal(2)};
            var5 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var4, var5);
            var4 = null;
            var6 = var10000;
            var1.setlocal(3, var6);
            var4 = null;
         }

         var1.setline(1123);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __add__$54(PyFrame var1, ThreadState var2) {
      var1.setline(1129);
      PyString.fromInterned("Returns self + other.\n\n        -INF + INF (or the reverse) cause InvalidOperation errors.\n        ");
      var1.setline(1130);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1131);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1132);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1134);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1135);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1137);
         var10000 = var1.getlocal(0).__getattr__("_is_special");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_is_special");
         }

         if (var10000.__nonzero__()) {
            var1.setline(1138);
            var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1139);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(1140);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1142);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1144);
               var4 = var1.getlocal(0).__getattr__("_sign");
               var10000 = var4._ne(var1.getlocal(1).__getattr__("_sign"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1145);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("-INF + INF"));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1146);
               var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1147);
            if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1148);
               var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1150);
         var4 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("_exp"), var1.getlocal(1).__getattr__("_exp"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1151);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(1152);
         var4 = var1.getlocal(2).__getattr__("rounding");
         var10000 = var4._eq(var1.getglobal("ROUND_FLOOR"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("_sign");
            var10000 = var4._ne(var1.getlocal(1).__getattr__("_sign"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1154);
            var7 = Py.newInteger(1);
            var1.setlocal(5, var7);
            var4 = null;
         }

         var1.setline(1156);
         var10000 = var1.getlocal(0).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1157);
            var4 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(1).__getattr__("_sign"));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(1158);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(1159);
               var7 = Py.newInteger(1);
               var1.setlocal(6, var7);
               var4 = null;
            }

            var1.setline(1160);
            var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(4));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1161);
            var4 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1162);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1163);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(1164);
               var4 = var1.getglobal("max").__call__(var2, var1.getlocal(4), var1.getlocal(1).__getattr__("_exp")._sub(var1.getlocal(2).__getattr__("prec"))._sub(Py.newInteger(1)));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1165);
               var4 = var1.getlocal(1).__getattr__("_rescale").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("rounding"));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(1166);
               var4 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(1167);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1168);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1169);
                  var4 = var1.getglobal("max").__call__(var2, var1.getlocal(4), var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(2).__getattr__("prec"))._sub(Py.newInteger(1)));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1170);
                  var4 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("rounding"));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1171);
                  var4 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1172);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1174);
                  var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(1175);
                  var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
                  var1.setlocal(8, var4);
                  var4 = null;
                  var1.setline(1176);
                  var4 = var1.getglobal("_normalize").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(2).__getattr__("prec"));
                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(7, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1178);
                  var4 = var1.getglobal("_WorkRep").__call__(var2);
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(1179);
                  var4 = var1.getlocal(7).__getattr__("sign");
                  var10000 = var4._ne(var1.getlocal(8).__getattr__("sign"));
                  var4 = null;
                  PyTuple var8;
                  if (var10000.__nonzero__()) {
                     var1.setline(1181);
                     var4 = var1.getlocal(7).__getattr__("int");
                     var10000 = var4._eq(var1.getlocal(8).__getattr__("int"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1182);
                        var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(4));
                        var1.setlocal(3, var4);
                        var4 = null;
                        var1.setline(1183);
                        var4 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                        var1.setlocal(3, var4);
                        var4 = null;
                        var1.setline(1184);
                        var3 = var1.getlocal(3);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(1185);
                     var4 = var1.getlocal(7).__getattr__("int");
                     var10000 = var4._lt(var1.getlocal(8).__getattr__("int"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1186);
                        var8 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(7)});
                        var5 = Py.unpackSequence(var8, 2);
                        var6 = var5[0];
                        var1.setlocal(7, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(8, var6);
                        var6 = null;
                        var4 = null;
                     }

                     var1.setline(1188);
                     var4 = var1.getlocal(7).__getattr__("sign");
                     var10000 = var4._eq(Py.newInteger(1));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1189);
                        var7 = Py.newInteger(1);
                        var1.getlocal(9).__setattr__((String)"sign", var7);
                        var4 = null;
                        var1.setline(1190);
                        var8 = new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("sign"), var1.getlocal(7).__getattr__("sign")});
                        var5 = Py.unpackSequence(var8, 2);
                        var6 = var5[0];
                        var1.getlocal(7).__setattr__("sign", var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.getlocal(8).__setattr__("sign", var6);
                        var6 = null;
                        var4 = null;
                     } else {
                        var1.setline(1192);
                        var7 = Py.newInteger(0);
                        var1.getlocal(9).__setattr__((String)"sign", var7);
                        var4 = null;
                     }
                  } else {
                     var1.setline(1194);
                     var4 = var1.getlocal(7).__getattr__("sign");
                     var10000 = var4._eq(Py.newInteger(1));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1195);
                        var7 = Py.newInteger(1);
                        var1.getlocal(9).__setattr__((String)"sign", var7);
                        var4 = null;
                        var1.setline(1196);
                        var8 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)});
                        var5 = Py.unpackSequence(var8, 2);
                        var6 = var5[0];
                        var1.getlocal(7).__setattr__("sign", var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.getlocal(8).__setattr__("sign", var6);
                        var6 = null;
                        var4 = null;
                     } else {
                        var1.setline(1198);
                        var7 = Py.newInteger(0);
                        var1.getlocal(9).__setattr__((String)"sign", var7);
                        var4 = null;
                     }
                  }

                  var1.setline(1201);
                  var4 = var1.getlocal(8).__getattr__("sign");
                  var10000 = var4._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1202);
                     var4 = var1.getlocal(7).__getattr__("int")._add(var1.getlocal(8).__getattr__("int"));
                     var1.getlocal(9).__setattr__("int", var4);
                     var4 = null;
                  } else {
                     var1.setline(1204);
                     var4 = var1.getlocal(7).__getattr__("int")._sub(var1.getlocal(8).__getattr__("int"));
                     var1.getlocal(9).__setattr__("int", var4);
                     var4 = null;
                  }

                  var1.setline(1206);
                  var4 = var1.getlocal(7).__getattr__("exp");
                  var1.getlocal(9).__setattr__("exp", var4);
                  var4 = null;
                  var1.setline(1207);
                  var4 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(9));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1208);
                  var4 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1209);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __sub__$55(PyFrame var1, ThreadState var2) {
      var1.setline(1214);
      PyString.fromInterned("Return self - other");
      var1.setline(1215);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1216);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1217);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1219);
         var10000 = var1.getlocal(0).__getattr__("_is_special");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_is_special");
         }

         PyObject[] var4;
         String[] var5;
         if (var10000.__nonzero__()) {
            var1.setline(1220);
            var10000 = var1.getlocal(0).__getattr__("_check_nans");
            var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
            var5 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var4, var5);
            var4 = null;
            PyObject var6 = var10000;
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(1221);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(1222);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1225);
         var10000 = var1.getlocal(0).__getattr__("__add__");
         var4 = new PyObject[]{var1.getlocal(1).__getattr__("copy_negate").__call__(var2), var1.getlocal(2)};
         var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __rsub__$56(PyFrame var1, ThreadState var2) {
      var1.setline(1228);
      PyString.fromInterned("Return other - self");
      var1.setline(1229);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1230);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1231);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1233);
         var10000 = var1.getlocal(1).__getattr__("__sub__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __mul__$57(PyFrame var1, ThreadState var2) {
      var1.setline(1239);
      PyString.fromInterned("Return self * other.\n\n        (+-) INF * 0 (or its reverse) raise InvalidOperation.\n        ");
      var1.setline(1240);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1241);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1242);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1244);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1245);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1247);
         var4 = var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1249);
         var10000 = var1.getlocal(0).__getattr__("_is_special");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_is_special");
         }

         if (var10000.__nonzero__()) {
            var1.setline(1250);
            var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1251);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1252);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1254);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1255);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1256);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("(+-)INF * 0"));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1257);
               var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1259);
            if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1260);
               if (var1.getlocal(0).__not__().__nonzero__()) {
                  var1.setline(1261);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("0 * (+-)INF"));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1262);
               var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1264);
         var4 = var1.getlocal(0).__getattr__("_exp")._add(var1.getlocal(1).__getattr__("_exp"));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(1267);
         var10000 = var1.getlocal(0).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1268);
            var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(5));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1270);
            var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1271);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1274);
            var4 = var1.getlocal(0).__getattr__("_int");
            var10000 = var4._eq(PyString.fromInterned("1"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1275);
               var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getattr__("_int"), var1.getlocal(5));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1276);
               var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1277);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1278);
               var4 = var1.getlocal(1).__getattr__("_int");
               var10000 = var4._eq(PyString.fromInterned("1"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1279);
                  var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("_int"), var1.getlocal(5));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1280);
                  var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1281);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1283);
                  var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(1284);
                  var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(1286);
                  var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(3), var1.getglobal("str").__call__(var2, var1.getlocal(6).__getattr__("int")._mul(var1.getlocal(7).__getattr__("int"))), var1.getlocal(5));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1287);
                  var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1289);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __truediv__$58(PyFrame var1, ThreadState var2) {
      var1.setline(1293);
      PyString.fromInterned("Return self / other.");
      var1.setline(1294);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1295);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1296);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1298);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1299);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1301);
         var4 = var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1303);
         var10000 = var1.getlocal(0).__getattr__("_is_special");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_is_special");
         }

         if (var10000.__nonzero__()) {
            var1.setline(1304);
            var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1305);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1306);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1308);
            var10000 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
            }

            if (var10000.__nonzero__()) {
               var1.setline(1309);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("(+-)INF/(+-)INF"));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1311);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1312);
               var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1314);
            if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1315);
               var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("Clamped"), (PyObject)PyString.fromInterned("Division by infinity"));
               var1.setline(1316);
               var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(2).__getattr__("Etiny").__call__(var2));
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1319);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(1320);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(1321);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionUndefined"), (PyObject)PyString.fromInterned("0 / 0"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1322);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("DivisionByZero"), (PyObject)PyString.fromInterned("x / 0"), (PyObject)var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(1324);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(1325);
               var4 = var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(1).__getattr__("_exp"));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1326);
               PyInteger var7 = Py.newInteger(0);
               var1.setlocal(6, var7);
               var4 = null;
            } else {
               var1.setline(1329);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("_int"))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))._add(var1.getlocal(2).__getattr__("prec"))._add(Py.newInteger(1));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(1330);
               var4 = var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(1).__getattr__("_exp"))._sub(var1.getlocal(7));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1331);
               var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(1332);
               var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(1333);
               var4 = var1.getlocal(7);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               PyObject[] var5;
               PyObject var6;
               if (var10000.__nonzero__()) {
                  var1.setline(1334);
                  var4 = var1.getglobal("divmod").__call__(var2, var1.getlocal(8).__getattr__("int")._mul(Py.newInteger(10)._pow(var1.getlocal(7))), var1.getlocal(9).__getattr__("int"));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var4 = null;
               } else {
                  var1.setline(1336);
                  var4 = var1.getglobal("divmod").__call__(var2, var1.getlocal(8).__getattr__("int"), var1.getlocal(9).__getattr__("int")._mul(Py.newInteger(10)._pow(var1.getlocal(7).__neg__())));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(1337);
               if (var1.getlocal(10).__nonzero__()) {
                  var1.setline(1339);
                  var4 = var1.getlocal(6)._mod(Py.newInteger(5));
                  var10000 = var4._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1340);
                     var4 = var1.getlocal(6);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(6, var4);
                  }
               } else {
                  var1.setline(1343);
                  var4 = var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(1).__getattr__("_exp"));
                  var1.setlocal(11, var4);
                  var4 = null;

                  while(true) {
                     var1.setline(1344);
                     var4 = var1.getlocal(5);
                     var10000 = var4._lt(var1.getlocal(11));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(6)._mod(Py.newInteger(10));
                        var10000 = var4._eq(Py.newInteger(0));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(1345);
                     var4 = var1.getlocal(6);
                     var4 = var4._ifloordiv(Py.newInteger(10));
                     var1.setlocal(6, var4);
                     var1.setline(1346);
                     var4 = var1.getlocal(5);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(5, var4);
                  }
               }
            }

            var1.setline(1348);
            var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(3), var1.getglobal("str").__call__(var2, var1.getlocal(6)), var1.getlocal(5));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1349);
            var3 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _divide$59(PyFrame var1, ThreadState var2) {
      var1.setline(1356);
      PyString.fromInterned("Return (self // other, self % other), to context.prec precision.\n\n        Assumes that neither self nor other is a NaN, that self is not\n        infinite and that other is nonzero.\n        ");
      var1.setline(1357);
      PyObject var3 = var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1358);
      if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
         var1.setline(1359);
         var3 = var1.getlocal(0).__getattr__("_exp");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(1361);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("_exp"), var1.getlocal(1).__getattr__("_exp"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1363);
      var3 = var1.getlocal(0).__getattr__("adjusted").__call__(var2)._sub(var1.getlocal(1).__getattr__("adjusted").__call__(var2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1364);
      PyObject var10000 = var1.getlocal(0).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._le(Py.newInteger(-2));
            var3 = null;
         }
      }

      PyTuple var8;
      if (var10000.__nonzero__()) {
         var1.setline(1365);
         var8 = new PyTuple(new PyObject[]{var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0)), var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("rounding"))});
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(1367);
         PyObject var4 = var1.getlocal(5);
         var10000 = var4._le(var1.getlocal(2).__getattr__("prec"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1368);
            var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(1369);
            var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(1370);
            var4 = var1.getlocal(6).__getattr__("exp");
            var10000 = var4._ge(var1.getlocal(7).__getattr__("exp"));
            var4 = null;
            PyObject var5;
            PyObject var6;
            String var9;
            if (var10000.__nonzero__()) {
               var1.setline(1371);
               var10000 = var1.getlocal(6);
               var9 = "int";
               var5 = var10000;
               var6 = var5.__getattr__(var9);
               var6 = var6._imul(Py.newInteger(10)._pow(var1.getlocal(6).__getattr__("exp")._sub(var1.getlocal(7).__getattr__("exp"))));
               var5.__setattr__(var9, var6);
            } else {
               var1.setline(1373);
               var10000 = var1.getlocal(7);
               var9 = "int";
               var5 = var10000;
               var6 = var5.__getattr__(var9);
               var6 = var6._imul(Py.newInteger(10)._pow(var1.getlocal(7).__getattr__("exp")._sub(var1.getlocal(6).__getattr__("exp"))));
               var5.__setattr__(var9, var6);
            }

            var1.setline(1374);
            var4 = var1.getglobal("divmod").__call__(var2, var1.getlocal(6).__getattr__("int"), var1.getlocal(7).__getattr__("int"));
            PyObject[] var7 = Py.unpackSequence(var4, 2);
            var6 = var7[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var7[1];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
            var1.setline(1375);
            var4 = var1.getlocal(8);
            var10000 = var4._lt(Py.newInteger(10)._pow(var1.getlocal(2).__getattr__("prec")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1376);
               var8 = new PyTuple(new PyObject[]{var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(8)), (PyObject)Py.newInteger(0)), var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getglobal("str").__call__(var2, var1.getlocal(9)), var1.getlocal(4))});
               var1.f_lasti = -1;
               return var8;
            }
         }

         var1.setline(1380);
         var4 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionImpossible"), (PyObject)PyString.fromInterned("quotient too large in //, % or divmod"));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(1382);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(10)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject __rtruediv__$60(PyFrame var1, ThreadState var2) {
      var1.setline(1385);
      PyString.fromInterned("Swaps self/other and returns __truediv__.");
      var1.setline(1386);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1387);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1388);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1389);
         var10000 = var1.getlocal(1).__getattr__("__truediv__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __divmod__$61(PyFrame var1, ThreadState var2) {
      var1.setline(1397);
      PyString.fromInterned("\n        Return (self // other, self % other)\n        ");
      var1.setline(1398);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1399);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1400);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1402);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1403);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1405);
         var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1406);
         PyTuple var7;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1407);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(1409);
            var4 = var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1410);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1411);
               if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(1412);
                  var4 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("divmod(INF, INF)"));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1413);
                  var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(3)});
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(1415);
                  var7 = new PyTuple(new PyObject[]{var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(4)), var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("INF % x"))});
                  var1.f_lasti = -1;
                  return var7;
               }
            } else {
               var1.setline(1418);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1419);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(1420);
                     var4 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionUndefined"), (PyObject)PyString.fromInterned("divmod(0, 0)"));
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(1421);
                     var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(3)});
                     var1.f_lasti = -1;
                     return var7;
                  } else {
                     var1.setline(1423);
                     var7 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("DivisionByZero"), (PyObject)PyString.fromInterned("x // 0"), (PyObject)var1.getlocal(4)), var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("x % 0"))});
                     var1.f_lasti = -1;
                     return var7;
                  }
               } else {
                  var1.setline(1426);
                  var4 = var1.getlocal(0).__getattr__("_divide").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1427);
                  var4 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(1428);
                  var7 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
                  var1.f_lasti = -1;
                  return var7;
               }
            }
         }
      }
   }

   public PyObject __rdivmod__$62(PyFrame var1, ThreadState var2) {
      var1.setline(1431);
      PyString.fromInterned("Swaps self/other and returns __divmod__.");
      var1.setline(1432);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1433);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1434);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1435);
         var10000 = var1.getlocal(1).__getattr__("__divmod__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __mod__$63(PyFrame var1, ThreadState var2) {
      var1.setline(1440);
      PyString.fromInterned("\n        self % other\n        ");
      var1.setline(1441);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1442);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1443);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1445);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1446);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1448);
         var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1449);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1450);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1452);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1453);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("INF % x"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1454);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1455);
                  if (var1.getlocal(0).__nonzero__()) {
                     var1.setline(1456);
                     var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("x % 0"));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1458);
                     var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionUndefined"), (PyObject)PyString.fromInterned("0 % 0"));
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(1460);
                  var4 = var1.getlocal(0).__getattr__("_divide").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getitem__(Py.newInteger(1));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1461);
                  var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1462);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __rmod__$64(PyFrame var1, ThreadState var2) {
      var1.setline(1465);
      PyString.fromInterned("Swaps self/other and returns __mod__.");
      var1.setline(1466);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1467);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1468);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1469);
         var10000 = var1.getlocal(1).__getattr__("__mod__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject remainder_near$65(PyFrame var1, ThreadState var2) {
      var1.setline(1474);
      PyString.fromInterned("\n        Remainder nearest to 0-  abs(remainder-near) <= other/2\n        ");
      var1.setline(1475);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1476);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1478);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1480);
      var3 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1481);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1482);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1485);
         if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
            var1.setline(1486);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("remainder_near(infinity, x)"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1490);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(1491);
               if (var1.getlocal(0).__nonzero__()) {
                  var1.setline(1492);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("remainder_near(x, 0)"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1495);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionUndefined"), (PyObject)PyString.fromInterned("remainder_near(0, 0)"));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1499);
               PyObject var8;
               if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(1500);
                  var8 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                  var1.setlocal(3, var8);
                  var4 = null;
                  var1.setline(1501);
                  var3 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1504);
                  var8 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("_exp"), var1.getlocal(1).__getattr__("_exp"));
                  var1.setlocal(4, var8);
                  var4 = null;
                  var1.setline(1505);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(1506);
                     var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(4));
                     var1.setlocal(3, var8);
                     var4 = null;
                     var1.setline(1507);
                     var3 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1510);
                     var8 = var1.getlocal(0).__getattr__("adjusted").__call__(var2)._sub(var1.getlocal(1).__getattr__("adjusted").__call__(var2));
                     var1.setlocal(5, var8);
                     var4 = null;
                     var1.setline(1511);
                     var8 = var1.getlocal(5);
                     var10000 = var8._ge(var1.getlocal(2).__getattr__("prec")._add(Py.newInteger(1)));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1513);
                        var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("DivisionImpossible"));
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(1514);
                        var8 = var1.getlocal(5);
                        var10000 = var8._le(Py.newInteger(-2));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1516);
                           var8 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("rounding"));
                           var1.setlocal(3, var8);
                           var4 = null;
                           var1.setline(1517);
                           var3 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(1520);
                           var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                           var1.setlocal(6, var8);
                           var4 = null;
                           var1.setline(1521);
                           var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
                           var1.setlocal(7, var8);
                           var4 = null;
                           var1.setline(1522);
                           var8 = var1.getlocal(6).__getattr__("exp");
                           var10000 = var8._ge(var1.getlocal(7).__getattr__("exp"));
                           var4 = null;
                           PyObject var5;
                           PyObject var6;
                           String var10;
                           if (var10000.__nonzero__()) {
                              var1.setline(1523);
                              var10000 = var1.getlocal(6);
                              var10 = "int";
                              var5 = var10000;
                              var6 = var5.__getattr__(var10);
                              var6 = var6._imul(Py.newInteger(10)._pow(var1.getlocal(6).__getattr__("exp")._sub(var1.getlocal(7).__getattr__("exp"))));
                              var5.__setattr__(var10, var6);
                           } else {
                              var1.setline(1525);
                              var10000 = var1.getlocal(7);
                              var10 = "int";
                              var5 = var10000;
                              var6 = var5.__getattr__(var10);
                              var6 = var6._imul(Py.newInteger(10)._pow(var1.getlocal(7).__getattr__("exp")._sub(var1.getlocal(6).__getattr__("exp"))));
                              var5.__setattr__(var10, var6);
                           }

                           var1.setline(1526);
                           var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(6).__getattr__("int"), var1.getlocal(7).__getattr__("int"));
                           PyObject[] var9 = Py.unpackSequence(var8, 2);
                           var6 = var9[0];
                           var1.setlocal(8, var6);
                           var6 = null;
                           var6 = var9[1];
                           var1.setlocal(9, var6);
                           var6 = null;
                           var4 = null;
                           var1.setline(1530);
                           var8 = Py.newInteger(2)._mul(var1.getlocal(9))._add(var1.getlocal(8)._and(Py.newInteger(1)));
                           var10000 = var8._gt(var1.getlocal(7).__getattr__("int"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1531);
                              var8 = var1.getlocal(9);
                              var8 = var8._isub(var1.getlocal(7).__getattr__("int"));
                              var1.setlocal(9, var8);
                              var1.setline(1532);
                              var8 = var1.getlocal(8);
                              var8 = var8._iadd(Py.newInteger(1));
                              var1.setlocal(8, var8);
                           }

                           var1.setline(1534);
                           var8 = var1.getlocal(8);
                           var10000 = var8._ge(Py.newInteger(10)._pow(var1.getlocal(2).__getattr__("prec")));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1535);
                              var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("DivisionImpossible"));
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(1538);
                              var8 = var1.getlocal(0).__getattr__("_sign");
                              var1.setlocal(10, var8);
                              var4 = null;
                              var1.setline(1539);
                              var8 = var1.getlocal(9);
                              var10000 = var8._lt(Py.newInteger(0));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(1540);
                                 var8 = Py.newInteger(1)._sub(var1.getlocal(10));
                                 var1.setlocal(10, var8);
                                 var4 = null;
                                 var1.setline(1541);
                                 var8 = var1.getlocal(9).__neg__();
                                 var1.setlocal(9, var8);
                                 var4 = null;
                              }

                              var1.setline(1543);
                              var8 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(10), var1.getglobal("str").__call__(var2, var1.getlocal(9)), var1.getlocal(4));
                              var1.setlocal(3, var8);
                              var4 = null;
                              var1.setline(1544);
                              var3 = var1.getlocal(3).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject __floordiv__$66(PyFrame var1, ThreadState var2) {
      var1.setline(1547);
      PyString.fromInterned("self // other");
      var1.setline(1548);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1549);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1550);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1552);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1553);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1555);
         var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1556);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1557);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1559);
            if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(1560);
               if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(1561);
                  var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("INF // INF"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1563);
                  var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign")));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1565);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1566);
                  if (var1.getlocal(0).__nonzero__()) {
                     var1.setline(1567);
                     var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("DivisionByZero"), (PyObject)PyString.fromInterned("x // 0"), (PyObject)var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign")));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1570);
                     var3 = var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("DivisionUndefined"), (PyObject)PyString.fromInterned("0 // 0"));
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(1572);
                  var3 = var1.getlocal(0).__getattr__("_divide").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getitem__(Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __rfloordiv__$67(PyFrame var1, ThreadState var2) {
      var1.setline(1575);
      PyString.fromInterned("Swaps self/other and returns __floordiv__.");
      var1.setline(1576);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1577);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1578);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1579);
         var10000 = var1.getlocal(1).__getattr__("__floordiv__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __float__$68(PyFrame var1, ThreadState var2) {
      var1.setline(1582);
      PyString.fromInterned("Float representation.");
      var1.setline(1583);
      PyString var3;
      PyObject var4;
      if (var1.getlocal(0).__getattr__("_isnan").__call__(var2).__nonzero__()) {
         var1.setline(1584);
         if (var1.getlocal(0).__getattr__("is_snan").__call__(var2).__nonzero__()) {
            var1.setline(1585);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot convert signaling NaN to float")));
         }

         var1.setline(1586);
         var1.setline(1586);
         var3 = var1.getlocal(0).__getattr__("_sign").__nonzero__() ? PyString.fromInterned("-nan") : PyString.fromInterned("nan");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1588);
         var4 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1589);
      var4 = var1.getglobal("float").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __int__$69(PyFrame var1, ThreadState var2) {
      var1.setline(1592);
      PyString.fromInterned("Converts self to an int, truncating if necessary.");
      var1.setline(1593);
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(1594);
         if (var1.getlocal(0).__getattr__("_isnan").__call__(var2).__nonzero__()) {
            var1.setline(1595);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot convert NaN to integer")));
         }

         var1.setline(1596);
         if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
            var1.setline(1597);
            throw Py.makeException(var1.getglobal("OverflowError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot convert infinity to integer")));
         }
      }

      var1.setline(1598);
      PyObject var3 = Py.newInteger(-1)._pow(var1.getlocal(0).__getattr__("_sign"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1599);
      var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1600);
         var3 = var1.getlocal(1)._mul(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("_int")))._mul(Py.newInteger(10)._pow(var1.getlocal(0).__getattr__("_exp")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1602);
         var10000 = var1.getlocal(1);
         PyObject var10001 = var1.getglobal("int");
         Object var10003 = var1.getlocal(0).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(0).__getattr__("_exp"), (PyObject)null);
         if (!((PyObject)var10003).__nonzero__()) {
            var10003 = PyString.fromInterned("0");
         }

         var3 = var10000._mul(var10001.__call__((ThreadState)var2, (PyObject)var10003));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject real$70(PyFrame var1, ThreadState var2) {
      var1.setline(1607);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject imag$71(PyFrame var1, ThreadState var2) {
      var1.setline(1611);
      PyObject var3 = var1.getglobal("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject conjugate$72(PyFrame var1, ThreadState var2) {
      var1.setline(1615);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __complex__$73(PyFrame var1, ThreadState var2) {
      var1.setline(1618);
      PyObject var3 = var1.getglobal("complex").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __long__$74(PyFrame var1, ThreadState var2) {
      var1.setline(1624);
      PyString.fromInterned("Converts to a long.\n\n        Equivalent to long(int(self))\n        ");
      var1.setline(1625);
      PyObject var3 = var1.getglobal("long").__call__(var2, var1.getlocal(0).__getattr__("__int__").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _fix_nan$75(PyFrame var1, ThreadState var2) {
      var1.setline(1628);
      PyString.fromInterned("Decapitate the payload of a NaN to fit the context");
      var1.setline(1629);
      PyObject var3 = var1.getlocal(0).__getattr__("_int");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1633);
      var3 = var1.getlocal(1).__getattr__("prec")._sub(var1.getlocal(1).__getattr__("_clamp"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1634);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1635);
         var3 = var1.getlocal(2).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(3)), (PyObject)null, (PyObject)null).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1636);
         var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(2), var1.getlocal(0).__getattr__("_exp"), var1.getglobal("True"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1637);
         var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _fix$76(PyFrame var1, ThreadState var2) {
      var1.setline(1647);
      PyString.fromInterned("Round if it is necessary to keep self within prec precision.\n\n        Rounds and fixes the exponent.  Does not raise on a sNaN.\n\n        Arguments:\n        self - Decimal instance\n        context - context used.\n        ");
      var1.setline(1649);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(1650);
         if (var1.getlocal(0).__getattr__("_isnan").__call__(var2).__nonzero__()) {
            var1.setline(1652);
            var3 = var1.getlocal(0).__getattr__("_fix_nan").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1655);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1659);
         PyObject var4 = var1.getlocal(1).__getattr__("Etiny").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1660);
         var4 = var1.getlocal(1).__getattr__("Etop").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1661);
         PyObject var10000;
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(1662);
            var4 = (new PyList(new PyObject[]{var1.getlocal(1).__getattr__("Emax"), var1.getlocal(3)})).__getitem__(var1.getlocal(1).__getattr__("_clamp"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1663);
            var4 = var1.getglobal("min").__call__(var2, var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("_exp"), var1.getlocal(2)), var1.getlocal(4));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(1664);
            var4 = var1.getlocal(5);
            var10000 = var4._ne(var1.getlocal(0).__getattr__("_exp"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1665);
               var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Clamped"));
               var1.setline(1666);
               var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(5));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1668);
               var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(1672);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._add(var1.getlocal(0).__getattr__("_exp"))._sub(var1.getlocal(1).__getattr__("prec"));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(1673);
            var4 = var1.getlocal(6);
            var10000 = var4._gt(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1675);
               var4 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("Overflow"), (PyObject)PyString.fromInterned("above Emax"), (PyObject)var1.getlocal(0).__getattr__("_sign"));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(1676);
               var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
               var1.setline(1677);
               var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
               var1.setline(1678);
               var3 = var1.getlocal(7);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1680);
               var4 = var1.getlocal(6);
               var10000 = var4._lt(var1.getlocal(2));
               var4 = null;
               var4 = var10000;
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(1681);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1682);
                  var4 = var1.getlocal(2);
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(1685);
               var4 = var1.getlocal(0).__getattr__("_exp");
               var10000 = var4._lt(var1.getlocal(6));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1686);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._add(var1.getlocal(0).__getattr__("_exp"))._sub(var1.getlocal(6));
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(1687);
                  var4 = var1.getlocal(9);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1688);
                     var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(6)._sub(Py.newInteger(1)));
                     var1.setlocal(0, var4);
                     var4 = null;
                     var1.setline(1689);
                     PyInteger var5 = Py.newInteger(0);
                     var1.setlocal(9, var5);
                     var4 = null;
                  }

                  var1.setline(1690);
                  var4 = var1.getlocal(0).__getattr__("_pick_rounding_function").__getitem__(var1.getlocal(1).__getattr__("rounding"));
                  var1.setlocal(10, var4);
                  var4 = null;
                  var1.setline(1691);
                  var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(10)).__call__(var2, var1.getlocal(9));
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(1692);
                  Object var7 = var1.getlocal(0).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
                  if (!((PyObject)var7).__nonzero__()) {
                     var7 = PyString.fromInterned("0");
                  }

                  Object var6 = var7;
                  var1.setlocal(12, (PyObject)var6);
                  var4 = null;
                  var1.setline(1693);
                  var4 = var1.getlocal(11);
                  var10000 = var4._gt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1694);
                     var4 = var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(12))._add(Py.newInteger(1)));
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(1695);
                     var4 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
                     var10000 = var4._gt(var1.getlocal(1).__getattr__("prec"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1696);
                        var4 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                        var1.setlocal(12, var4);
                        var4 = null;
                        var1.setline(1697);
                        var4 = var1.getlocal(6);
                        var4 = var4._iadd(Py.newInteger(1));
                        var1.setlocal(6, var4);
                     }
                  }

                  var1.setline(1700);
                  var4 = var1.getlocal(6);
                  var10000 = var4._gt(var1.getlocal(3));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1701);
                     var4 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("Overflow"), (PyObject)PyString.fromInterned("above Emax"), (PyObject)var1.getlocal(0).__getattr__("_sign"));
                     var1.setlocal(7, var4);
                     var4 = null;
                  } else {
                     var1.setline(1703);
                     var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(12), var1.getlocal(6));
                     var1.setlocal(7, var4);
                     var4 = null;
                  }

                  var1.setline(1707);
                  var10000 = var1.getlocal(11);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(8);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1708);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Underflow"));
                  }

                  var1.setline(1709);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1710);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Subnormal"));
                  }

                  var1.setline(1711);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(1712);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
                  }

                  var1.setline(1713);
                  var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
                  var1.setline(1714);
                  if (var1.getlocal(7).__not__().__nonzero__()) {
                     var1.setline(1716);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Clamped"));
                  }

                  var1.setline(1717);
                  var3 = var1.getlocal(7);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1719);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1720);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Subnormal"));
                  }

                  var1.setline(1723);
                  var4 = var1.getlocal(1).__getattr__("_clamp");
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("_exp");
                     var10000 = var4._gt(var1.getlocal(3));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1724);
                     var1.getlocal(1).__getattr__("_raise_error").__call__(var2, var1.getglobal("Clamped"));
                     var1.setline(1725);
                     var4 = var1.getlocal(0).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(3))));
                     var1.setlocal(13, var4);
                     var4 = null;
                     var1.setline(1726);
                     var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(13), var1.getlocal(3));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1729);
                     var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject _round_down$77(PyFrame var1, ThreadState var2) {
      var1.setline(1744);
      PyString.fromInterned("Also known as round-towards-0, truncate.");
      var1.setline(1745);
      PyInteger var3;
      if (var1.getglobal("_all_zeros").__call__(var2, var1.getlocal(0).__getattr__("_int"), var1.getlocal(1)).__nonzero__()) {
         var1.setline(1746);
         var3 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1748);
         var3 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _round_up$78(PyFrame var1, ThreadState var2) {
      var1.setline(1751);
      PyString.fromInterned("Rounds away from 0.");
      var1.setline(1752);
      PyObject var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _round_half_up$79(PyFrame var1, ThreadState var2) {
      var1.setline(1755);
      PyString.fromInterned("Rounds 5 up (away from 0)");
      var1.setline(1756);
      PyObject var3 = var1.getlocal(0).__getattr__("_int").__getitem__(var1.getlocal(1));
      PyObject var10000 = var3._in(PyString.fromInterned("56789"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(1757);
         var4 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1758);
         if (var1.getglobal("_all_zeros").__call__(var2, var1.getlocal(0).__getattr__("_int"), var1.getlocal(1)).__nonzero__()) {
            var1.setline(1759);
            var4 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(1761);
            var4 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject _round_half_down$80(PyFrame var1, ThreadState var2) {
      var1.setline(1764);
      PyString.fromInterned("Round 5 down");
      var1.setline(1765);
      if (var1.getglobal("_exact_half").__call__(var2, var1.getlocal(0).__getattr__("_int"), var1.getlocal(1)).__nonzero__()) {
         var1.setline(1766);
         PyInteger var4 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1768);
         PyObject var3 = var1.getlocal(0).__getattr__("_round_half_up").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _round_half_even$81(PyFrame var1, ThreadState var2) {
      var1.setline(1771);
      PyString.fromInterned("Round 5 to even, rest to nearest.");
      var1.setline(1772);
      PyObject var10000 = var1.getglobal("_exact_half").__call__(var2, var1.getlocal(0).__getattr__("_int"), var1.getlocal(1));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_int").__getitem__(var1.getlocal(1)._sub(Py.newInteger(1)));
            var10000 = var3._in(PyString.fromInterned("02468"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1774);
         PyInteger var4 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1776);
         var3 = var1.getlocal(0).__getattr__("_round_half_up").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _round_ceiling$82(PyFrame var1, ThreadState var2) {
      var1.setline(1779);
      PyString.fromInterned("Rounds up (not away from 0 if negative.)");
      var1.setline(1780);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
         var1.setline(1781);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1783);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1)).__neg__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _round_floor$83(PyFrame var1, ThreadState var2) {
      var1.setline(1786);
      PyString.fromInterned("Rounds down (not towards 0 if negative)");
      var1.setline(1787);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_sign").__not__().__nonzero__()) {
         var1.setline(1788);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1790);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1)).__neg__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _round_05up$84(PyFrame var1, ThreadState var2) {
      var1.setline(1793);
      PyString.fromInterned("Round down unless digit prec-1 is 0 or 5.");
      var1.setline(1794);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_int").__getitem__(var1.getlocal(1)._sub(Py.newInteger(1)));
         var10000 = var3._notin(PyString.fromInterned("05"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1795);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1797);
         var3 = var1.getlocal(0).__getattr__("_round_down").__call__(var2, var1.getlocal(1)).__neg__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject fma$85(PyFrame var1, ThreadState var2) {
      var1.setline(1808);
      PyString.fromInterned("Fused multiply-add.\n\n        Returns self*other+third with no rounding of the intermediate\n        product self*other.\n\n        self and other are multiplied together, with no rounding of\n        the result.  The third operand is then added to the result,\n        and a single final rounding is performed.\n        ");
      var1.setline(1810);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(1814);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(1815);
         var6 = var1.getlocal(3);
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1816);
            var6 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(3, var6);
            var3 = null;
         }

         var1.setline(1817);
         var6 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var6._eq(PyString.fromInterned("N"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1818);
            var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(0));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(1819);
         var7 = var1.getlocal(1).__getattr__("_exp");
         var10000 = var7._eq(PyString.fromInterned("N"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1820);
            var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(1));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(1821);
         var7 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var7._eq(PyString.fromInterned("n"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1822);
            var7 = var1.getlocal(0);
            var1.setlocal(4, var7);
            var4 = null;
         } else {
            var1.setline(1823);
            var7 = var1.getlocal(1).__getattr__("_exp");
            var10000 = var7._eq(PyString.fromInterned("n"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1824);
               var7 = var1.getlocal(1);
               var1.setlocal(4, var7);
               var4 = null;
            } else {
               var1.setline(1825);
               var7 = var1.getlocal(0).__getattr__("_exp");
               var10000 = var7._eq(PyString.fromInterned("F"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1826);
                  if (var1.getlocal(1).__not__().__nonzero__()) {
                     var1.setline(1827);
                     var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("INF * 0 in fma"));
                     var1.f_lasti = -1;
                     return var6;
                  }

                  var1.setline(1829);
                  var7 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign")));
                  var1.setlocal(4, var7);
                  var4 = null;
               } else {
                  var1.setline(1830);
                  var7 = var1.getlocal(1).__getattr__("_exp");
                  var10000 = var7._eq(PyString.fromInterned("F"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1831);
                     if (var1.getlocal(0).__not__().__nonzero__()) {
                        var1.setline(1832);
                        var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("0 * INF in fma"));
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setline(1834);
                     var7 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign")));
                     var1.setlocal(4, var7);
                     var4 = null;
                  }
               }
            }
         }
      } else {
         var1.setline(1836);
         var7 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign")._xor(var1.getlocal(1).__getattr__("_sign")), var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("_int"))._mul(var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("_int")))), var1.getlocal(0).__getattr__("_exp")._add(var1.getlocal(1).__getattr__("_exp")));
         var1.setlocal(4, var7);
         var4 = null;
      }

      var1.setline(1840);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getglobal("True")};
      String[] var5 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var8, var5);
      var4 = null;
      var7 = var10000;
      var1.setlocal(2, var7);
      var4 = null;
      var1.setline(1841);
      var6 = var1.getlocal(4).__getattr__("__add__").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _power_modulo$86(PyFrame var1, ThreadState var2) {
      var1.setline(1844);
      PyString.fromInterned("Three argument version of __pow__");
      var1.setline(1849);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(1850);
      var10000 = var1.getglobal("_convert_other");
      var3 = new PyObject[]{var1.getlocal(2), var1.getglobal("True")};
      var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var7 = var10000;
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(1852);
      var7 = var1.getlocal(3);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1853);
         var7 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(1857);
      var7 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(1858);
      var7 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(1859);
      var7 = var1.getlocal(2).__getattr__("_isnan").__call__(var2);
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(1860);
      var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(5);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(6);
         }
      }

      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(1861);
         var7 = var1.getlocal(4);
         var10000 = var7._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1862);
            var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(0));
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(1864);
            var8 = var1.getlocal(5);
            var10000 = var8._eq(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1865);
               var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(1));
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(1867);
               var8 = var1.getlocal(6);
               var10000 = var8._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1868);
                  var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sNaN"), (PyObject)var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(1870);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(1871);
                     var7 = var1.getlocal(0).__getattr__("_fix_nan").__call__(var2, var1.getlocal(3));
                     var1.f_lasti = -1;
                     return var7;
                  } else {
                     var1.setline(1872);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(1873);
                        var7 = var1.getlocal(1).__getattr__("_fix_nan").__call__(var2, var1.getlocal(3));
                        var1.f_lasti = -1;
                        return var7;
                     } else {
                        var1.setline(1874);
                        var7 = var1.getlocal(2).__getattr__("_fix_nan").__call__(var2, var1.getlocal(3));
                        var1.f_lasti = -1;
                        return var7;
                     }
                  }
               }
            }
         }
      } else {
         var1.setline(1877);
         var10000 = var1.getlocal(0).__getattr__("_isinteger").__call__(var2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_isinteger").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__getattr__("_isinteger").__call__(var2);
            }
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(1880);
            var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("pow() 3rd argument not allowed unless all arguments are integers"));
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(1883);
            var8 = var1.getlocal(1);
            var10000 = var8._lt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1884);
               var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("pow() 2nd argument cannot be negative when 3rd argument specified"));
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(1887);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(1888);
                  var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("pow() 3rd argument cannot be 0"));
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(1893);
                  var8 = var1.getlocal(2).__getattr__("adjusted").__call__(var2);
                  var10000 = var8._ge(var1.getlocal(3).__getattr__("prec"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1894);
                     var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("insufficient precision: pow() 3rd argument must not have more than precision digits"));
                     var1.f_lasti = -1;
                     return var7;
                  } else {
                     var1.setline(1901);
                     var10000 = var1.getlocal(1).__not__();
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(0).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1902);
                        var7 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("at least one of pow() 1st argument and 2nd argument must be nonzero ;0**0 is not defined"));
                        var1.f_lasti = -1;
                        return var7;
                     } else {
                        var1.setline(1908);
                        if (var1.getlocal(1).__getattr__("_iseven").__call__(var2).__nonzero__()) {
                           var1.setline(1909);
                           PyInteger var9 = Py.newInteger(0);
                           var1.setlocal(7, var9);
                           var4 = null;
                        } else {
                           var1.setline(1911);
                           var8 = var1.getlocal(0).__getattr__("_sign");
                           var1.setlocal(7, var8);
                           var4 = null;
                        }

                        var1.setline(1915);
                        var8 = var1.getglobal("abs").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(2)));
                        var1.setlocal(2, var8);
                        var4 = null;
                        var1.setline(1916);
                        var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0).__getattr__("to_integral_value").__call__(var2));
                        var1.setlocal(8, var8);
                        var4 = null;
                        var1.setline(1917);
                        var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1).__getattr__("to_integral_value").__call__(var2));
                        var1.setlocal(9, var8);
                        var4 = null;
                        var1.setline(1920);
                        var8 = var1.getlocal(8).__getattr__("int")._mod(var1.getlocal(2))._mul(var1.getglobal("pow").__call__((ThreadState)var2, Py.newInteger(10), (PyObject)var1.getlocal(8).__getattr__("exp"), (PyObject)var1.getlocal(2)))._mod(var1.getlocal(2));
                        var1.setlocal(8, var8);
                        var4 = null;
                        var1.setline(1921);
                        var8 = var1.getglobal("xrange").__call__(var2, var1.getlocal(9).__getattr__("exp")).__iter__();

                        while(true) {
                           var1.setline(1921);
                           PyObject var5 = var8.__iternext__();
                           if (var5 == null) {
                              var1.setline(1923);
                              var8 = var1.getglobal("pow").__call__(var2, var1.getlocal(8), var1.getlocal(9).__getattr__("int"), var1.getlocal(2));
                              var1.setlocal(8, var8);
                              var4 = null;
                              var1.setline(1925);
                              var7 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(8)), (PyObject)Py.newInteger(0));
                              var1.f_lasti = -1;
                              return var7;
                           }

                           var1.setlocal(10, var5);
                           var1.setline(1922);
                           PyObject var6 = var1.getglobal("pow").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)Py.newInteger(10), (PyObject)var1.getlocal(2));
                           var1.setlocal(8, var6);
                           var6 = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _power_exact$87(PyFrame var1, ThreadState var2) {
      var1.setline(1939);
      PyString.fromInterned("Attempt to compute self**other exactly.\n\n        Given Decimals self and other and an integer p, attempt to\n        compute an exact result for the power self**other, with p\n        digits of precision.  Return None if self**other is not\n        exactly representable in p digits.\n\n        Assumes that elimination of special cases has already been\n        performed: self and other must both be nonspecial; self must\n        be positive and not numerically equal to 1; other must be\n        nonzero.  For efficiency, other._exp should not be too large,\n        so that 10**abs(other._exp) is a feasible calculation.");
      var1.setline(1986);
      PyObject var3 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1987);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("int"), var1.getlocal(3).__getattr__("exp")});
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(1988);
         var3 = var1.getlocal(4)._mod(Py.newInteger(10));
         PyObject var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1992);
            var3 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(1993);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("int"), var1.getlocal(6).__getattr__("exp")});
            var4 = Py.unpackSequence(var7, 2);
            var5 = var4[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;

            while(true) {
               var1.setline(1994);
               var3 = var1.getlocal(7)._mod(Py.newInteger(10));
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(2000);
                  var3 = var1.getlocal(4);
                  var10000 = var3._eq(Py.newInteger(1));
                  var3 = null;
                  PyObject var8;
                  PyInteger var11;
                  if (var10000.__nonzero__()) {
                     var1.setline(2001);
                     var3 = var1.getlocal(5);
                     var3 = var3._imul(var1.getlocal(7));
                     var1.setlocal(5, var3);

                     while(true) {
                        var1.setline(2003);
                        var3 = var1.getlocal(5)._mod(Py.newInteger(10));
                        var10000 = var3._eq(Py.newInteger(0));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(2006);
                           var3 = var1.getlocal(8);
                           var10000 = var3._lt(Py.newInteger(0));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2007);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(2008);
                              var8 = var1.getlocal(5)._mul(Py.newInteger(10)._pow(var1.getlocal(8)));
                              var1.setlocal(9, var8);
                              var4 = null;
                              var1.setline(2009);
                              var8 = var1.getlocal(6).__getattr__("sign");
                              var10000 = var8._eq(Py.newInteger(1));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2010);
                                 var8 = var1.getlocal(9).__neg__();
                                 var1.setlocal(9, var8);
                                 var4 = null;
                              }

                              var1.setline(2012);
                              var10000 = var1.getlocal(1).__getattr__("_isinteger").__call__(var2);
                              if (var10000.__nonzero__()) {
                                 var8 = var1.getlocal(1).__getattr__("_sign");
                                 var10000 = var8._eq(Py.newInteger(0));
                                 var4 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(2013);
                                 var8 = var1.getlocal(0).__getattr__("_exp")._mul(var1.getglobal("int").__call__(var2, var1.getlocal(1)));
                                 var1.setlocal(10, var8);
                                 var4 = null;
                                 var1.setline(2014);
                                 var8 = var1.getglobal("min").__call__(var2, var1.getlocal(9)._sub(var1.getlocal(10)), var1.getlocal(2)._sub(Py.newInteger(1)));
                                 var1.setlocal(11, var8);
                                 var4 = null;
                              } else {
                                 var1.setline(2016);
                                 var11 = Py.newInteger(0);
                                 var1.setlocal(11, var11);
                                 var4 = null;
                              }

                              var1.setline(2017);
                              var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1")._add(PyString.fromInterned("0")._mul(var1.getlocal(11))), (PyObject)var1.getlocal(9)._sub(var1.getlocal(11)));
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }

                        var1.setline(2004);
                        var3 = var1.getlocal(5);
                        var3 = var3._ifloordiv(Py.newInteger(10));
                        var1.setlocal(5, var3);
                        var1.setline(2005);
                        var3 = var1.getlocal(8);
                        var3 = var3._iadd(Py.newInteger(1));
                        var1.setlocal(8, var3);
                     }
                  } else {
                     var1.setline(2021);
                     var8 = var1.getlocal(6).__getattr__("sign");
                     var10000 = var8._eq(Py.newInteger(1));
                     var4 = null;
                     PyObject var6;
                     PyObject[] var10;
                     if (var10000.__nonzero__()) {
                        var1.setline(2022);
                        var8 = var1.getlocal(4)._mod(Py.newInteger(10));
                        var1.setlocal(12, var8);
                        var4 = null;
                        var1.setline(2023);
                        var8 = var1.getlocal(12);
                        var10000 = var8._in(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(4), Py.newInteger(6), Py.newInteger(8)}));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2025);
                           var8 = var1.getlocal(4)._and(var1.getlocal(4).__neg__());
                           var10000 = var8._ne(var1.getlocal(4));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2026);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2028);
                           var8 = var1.getglobal("_nbits").__call__(var2, var1.getlocal(4))._sub(Py.newInteger(1));
                           var1.setlocal(13, var8);
                           var4 = null;
                           var1.setline(2030);
                           var8 = var1.getlocal(8);
                           var10000 = var8._ge(Py.newInteger(0));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2031);
                              var8 = var1.getlocal(7)._mul(Py.newInteger(10)._pow(var1.getlocal(8)));
                              var1.setlocal(14, var8);
                              var4 = null;
                              var1.setline(2032);
                              var8 = var1.getlocal(13)._mul(var1.getlocal(14));
                              var1.setlocal(13, var8);
                              var4 = null;
                              var1.setline(2033);
                              var8 = var1.getlocal(5)._mul(var1.getlocal(14));
                              var1.setlocal(5, var8);
                              var4 = null;
                           } else {
                              var1.setline(2035);
                              var8 = Py.newInteger(10)._pow(var1.getlocal(8).__neg__());
                              var1.setlocal(15, var8);
                              var4 = null;
                              var1.setline(2036);
                              var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(13)._mul(var1.getlocal(7)), var1.getlocal(15));
                              var10 = Py.unpackSequence(var8, 2);
                              var6 = var10[0];
                              var1.setlocal(13, var6);
                              var6 = null;
                              var6 = var10[1];
                              var1.setlocal(16, var6);
                              var6 = null;
                              var4 = null;
                              var1.setline(2037);
                              if (var1.getlocal(16).__nonzero__()) {
                                 var1.setline(2038);
                                 var3 = var1.getglobal("None");
                                 var1.f_lasti = -1;
                                 return var3;
                              }

                              var1.setline(2039);
                              var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(5)._mul(var1.getlocal(7)), var1.getlocal(15));
                              var10 = Py.unpackSequence(var8, 2);
                              var6 = var10[0];
                              var1.setlocal(5, var6);
                              var6 = null;
                              var6 = var10[1];
                              var1.setlocal(16, var6);
                              var6 = null;
                              var4 = null;
                              var1.setline(2040);
                              if (var1.getlocal(16).__nonzero__()) {
                                 var1.setline(2041);
                                 var3 = var1.getglobal("None");
                                 var1.f_lasti = -1;
                                 return var3;
                              }
                           }

                           var1.setline(2043);
                           var8 = var1.getlocal(13)._mul(Py.newInteger(65));
                           var10000 = var8._ge(var1.getlocal(2)._mul(Py.newInteger(93)));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2044);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2045);
                           var8 = Py.newInteger(5)._pow(var1.getlocal(13));
                           var1.setlocal(4, var8);
                           var4 = null;
                        } else {
                           var1.setline(2047);
                           var8 = var1.getlocal(12);
                           var10000 = var8._eq(Py.newInteger(5));
                           var4 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(2073);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2050);
                           var8 = var1.getglobal("_nbits").__call__(var2, var1.getlocal(4))._mul(Py.newInteger(28))._floordiv(Py.newInteger(65));
                           var1.setlocal(13, var8);
                           var4 = null;
                           var1.setline(2051);
                           var8 = var1.getglobal("divmod").__call__(var2, Py.newInteger(5)._pow(var1.getlocal(13)), var1.getlocal(4));
                           var10 = Py.unpackSequence(var8, 2);
                           var6 = var10[0];
                           var1.setlocal(4, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(16, var6);
                           var6 = null;
                           var4 = null;
                           var1.setline(2052);
                           if (var1.getlocal(16).__nonzero__()) {
                              var1.setline(2053);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           while(true) {
                              var1.setline(2054);
                              var8 = var1.getlocal(4)._mod(Py.newInteger(5));
                              var10000 = var8._eq(Py.newInteger(0));
                              var4 = null;
                              if (!var10000.__nonzero__()) {
                                 var1.setline(2057);
                                 var8 = var1.getlocal(8);
                                 var10000 = var8._ge(Py.newInteger(0));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2058);
                                    var8 = var1.getlocal(7)._mul(Py.newInteger(10)._pow(var1.getlocal(8)));
                                    var1.setlocal(17, var8);
                                    var4 = null;
                                    var1.setline(2059);
                                    var8 = var1.getlocal(13)._mul(var1.getlocal(17));
                                    var1.setlocal(13, var8);
                                    var4 = null;
                                    var1.setline(2060);
                                    var8 = var1.getlocal(5)._mul(var1.getlocal(17));
                                    var1.setlocal(5, var8);
                                    var4 = null;
                                 } else {
                                    var1.setline(2062);
                                    var8 = Py.newInteger(10)._pow(var1.getlocal(8).__neg__());
                                    var1.setlocal(15, var8);
                                    var4 = null;
                                    var1.setline(2063);
                                    var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(13)._mul(var1.getlocal(7)), var1.getlocal(15));
                                    var10 = Py.unpackSequence(var8, 2);
                                    var6 = var10[0];
                                    var1.setlocal(13, var6);
                                    var6 = null;
                                    var6 = var10[1];
                                    var1.setlocal(16, var6);
                                    var6 = null;
                                    var4 = null;
                                    var1.setline(2064);
                                    if (var1.getlocal(16).__nonzero__()) {
                                       var1.setline(2065);
                                       var3 = var1.getglobal("None");
                                       var1.f_lasti = -1;
                                       return var3;
                                    }

                                    var1.setline(2066);
                                    var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(5)._mul(var1.getlocal(7)), var1.getlocal(15));
                                    var10 = Py.unpackSequence(var8, 2);
                                    var6 = var10[0];
                                    var1.setlocal(5, var6);
                                    var6 = null;
                                    var6 = var10[1];
                                    var1.setlocal(16, var6);
                                    var6 = null;
                                    var4 = null;
                                    var1.setline(2067);
                                    if (var1.getlocal(16).__nonzero__()) {
                                       var1.setline(2068);
                                       var3 = var1.getglobal("None");
                                       var1.f_lasti = -1;
                                       return var3;
                                    }
                                 }

                                 var1.setline(2069);
                                 var8 = var1.getlocal(13)._mul(Py.newInteger(3));
                                 var10000 = var8._ge(var1.getlocal(2)._mul(Py.newInteger(10)));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2070);
                                    var3 = var1.getglobal("None");
                                    var1.f_lasti = -1;
                                    return var3;
                                 }

                                 var1.setline(2071);
                                 var8 = Py.newInteger(2)._pow(var1.getlocal(13));
                                 var1.setlocal(4, var8);
                                 var4 = null;
                                 break;
                              }

                              var1.setline(2055);
                              var8 = var1.getlocal(4);
                              var8 = var8._ifloordiv(Py.newInteger(5));
                              var1.setlocal(4, var8);
                              var1.setline(2056);
                              var8 = var1.getlocal(13);
                              var8 = var8._isub(Py.newInteger(1));
                              var1.setlocal(13, var8);
                           }
                        }

                        var1.setline(2075);
                        var8 = var1.getlocal(4);
                        var10000 = var8._ge(Py.newInteger(10)._pow(var1.getlocal(2)));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2076);
                           var3 = var1.getglobal("None");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(2077);
                           var8 = var1.getlocal(13).__neg__()._sub(var1.getlocal(5));
                           var1.setlocal(5, var8);
                           var4 = null;
                           var1.setline(2078);
                           var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(4)), (PyObject)var1.getlocal(5));
                           var1.f_lasti = -1;
                           return var3;
                        }
                     } else {
                        var1.setline(2081);
                        var8 = var1.getlocal(8);
                        var10000 = var8._ge(Py.newInteger(0));
                        var4 = null;
                        PyTuple var9;
                        if (var10000.__nonzero__()) {
                           var1.setline(2082);
                           var9 = new PyTuple(new PyObject[]{var1.getlocal(7)._mul(Py.newInteger(10)._pow(var1.getlocal(8))), Py.newInteger(1)});
                           var10 = Py.unpackSequence(var9, 2);
                           var6 = var10[0];
                           var1.setlocal(18, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(19, var6);
                           var6 = null;
                           var4 = null;
                        } else {
                           var1.setline(2084);
                           var8 = var1.getlocal(5);
                           var10000 = var8._ne(Py.newInteger(0));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var8 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(7)._mul(var1.getlocal(5)))));
                              var10000 = var8._le(var1.getlocal(8).__neg__());
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(2085);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2086);
                           var8 = var1.getglobal("_nbits").__call__(var2, var1.getlocal(4));
                           var1.setlocal(20, var8);
                           var4 = null;
                           var1.setline(2087);
                           var8 = var1.getlocal(4);
                           var10000 = var8._ne(Py.newInteger(1));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var8 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(7))._mul(var1.getlocal(20))));
                              var10000 = var8._le(var1.getlocal(8).__neg__());
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(2088);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2089);
                           var9 = new PyTuple(new PyObject[]{var1.getlocal(7), Py.newInteger(10)._pow(var1.getlocal(8).__neg__())});
                           var10 = Py.unpackSequence(var9, 2);
                           var6 = var10[0];
                           var1.setlocal(18, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(19, var6);
                           var6 = null;
                           var4 = null;

                           label196:
                           while(true) {
                              var1.setline(2090);
                              var8 = var1.getlocal(18)._mod(Py.newInteger(2));
                              PyObject var10001 = var1.getlocal(19)._mod(Py.newInteger(2));
                              var10000 = var8;
                              var8 = var10001;
                              if ((var5 = var10000._eq(var10001)).__nonzero__()) {
                                 var5 = var8._eq(Py.newInteger(0));
                              }

                              var4 = null;
                              if (!var5.__nonzero__()) {
                                 while(true) {
                                    var1.setline(2093);
                                    var8 = var1.getlocal(18)._mod(Py.newInteger(5));
                                    var10001 = var1.getlocal(19)._mod(Py.newInteger(5));
                                    var10000 = var8;
                                    var8 = var10001;
                                    if ((var5 = var10000._eq(var10001)).__nonzero__()) {
                                       var5 = var8._eq(Py.newInteger(0));
                                    }

                                    var4 = null;
                                    if (!var5.__nonzero__()) {
                                       break label196;
                                    }

                                    var1.setline(2094);
                                    var8 = var1.getlocal(18);
                                    var8 = var8._ifloordiv(Py.newInteger(5));
                                    var1.setlocal(18, var8);
                                    var1.setline(2095);
                                    var8 = var1.getlocal(19);
                                    var8 = var8._ifloordiv(Py.newInteger(5));
                                    var1.setlocal(19, var8);
                                 }
                              }

                              var1.setline(2091);
                              var8 = var1.getlocal(18);
                              var8 = var8._ifloordiv(Py.newInteger(2));
                              var1.setlocal(18, var8);
                              var1.setline(2092);
                              var8 = var1.getlocal(19);
                              var8 = var8._ifloordiv(Py.newInteger(2));
                              var1.setlocal(19, var8);
                           }
                        }

                        var1.setline(2098);
                        var8 = var1.getlocal(19);
                        var10000 = var8._gt(Py.newInteger(1));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2100);
                           var8 = var1.getlocal(4);
                           var10000 = var8._ne(Py.newInteger(1));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var8 = var1.getlocal(20);
                              var10000 = var8._le(var1.getlocal(19));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(2101);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2103);
                           var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(5), var1.getlocal(19));
                           var10 = Py.unpackSequence(var8, 2);
                           var6 = var10[0];
                           var1.setlocal(5, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(21, var6);
                           var6 = null;
                           var4 = null;
                           var1.setline(2104);
                           var8 = var1.getlocal(21);
                           var10000 = var8._ne(Py.newInteger(0));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2105);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2108);
                           var8 = Py.newLong("1")._lshift(var1.getglobal("_nbits").__call__(var2, var1.getlocal(4)).__neg__()._floordiv(var1.getlocal(19)).__neg__());
                           var1.setlocal(22, var8);
                           var4 = null;

                           while(true) {
                              var1.setline(2109);
                              if (!var1.getglobal("True").__nonzero__()) {
                                 break;
                              }

                              var1.setline(2110);
                              var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(4), var1.getlocal(22)._pow(var1.getlocal(19)._sub(Py.newInteger(1))));
                              var10 = Py.unpackSequence(var8, 2);
                              var6 = var10[0];
                              var1.setlocal(23, var6);
                              var6 = null;
                              var6 = var10[1];
                              var1.setlocal(24, var6);
                              var6 = null;
                              var4 = null;
                              var1.setline(2111);
                              var8 = var1.getlocal(22);
                              var10000 = var8._le(var1.getlocal(23));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 break;
                              }

                              var1.setline(2114);
                              var8 = var1.getlocal(22)._mul(var1.getlocal(19)._sub(Py.newInteger(1)))._add(var1.getlocal(23))._floordiv(var1.getlocal(19));
                              var1.setlocal(22, var8);
                              var4 = null;
                           }

                           var1.setline(2115);
                           var8 = var1.getlocal(22);
                           var10000 = var8._eq(var1.getlocal(23));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var8 = var1.getlocal(24);
                              var10000 = var8._eq(Py.newInteger(0));
                              var4 = null;
                           }

                           if (var10000.__not__().__nonzero__()) {
                              var1.setline(2116);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(2117);
                           var8 = var1.getlocal(22);
                           var1.setlocal(4, var8);
                           var4 = null;
                        }

                        var1.setline(2124);
                        var8 = var1.getlocal(4);
                        var10000 = var8._gt(Py.newInteger(1));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var8 = var1.getlocal(18);
                           var10000 = var8._gt(var1.getlocal(2)._mul(Py.newInteger(100))._floordiv(var1.getglobal("_log10_lb").__call__(var2, var1.getlocal(4))));
                           var4 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(2125);
                           var3 = var1.getglobal("None");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(2126);
                           var8 = var1.getlocal(4)._pow(var1.getlocal(18));
                           var1.setlocal(4, var8);
                           var4 = null;
                           var1.setline(2127);
                           var8 = var1.getlocal(5);
                           var8 = var8._imul(var1.getlocal(18));
                           var1.setlocal(5, var8);
                           var1.setline(2128);
                           var8 = var1.getlocal(4);
                           var10000 = var8._gt(Py.newInteger(10)._pow(var1.getlocal(2)));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2129);
                              var3 = var1.getglobal("None");
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(2134);
                              var8 = var1.getglobal("str").__call__(var2, var1.getlocal(4));
                              var1.setlocal(25, var8);
                              var4 = null;
                              var1.setline(2135);
                              var10000 = var1.getlocal(1).__getattr__("_isinteger").__call__(var2);
                              if (var10000.__nonzero__()) {
                                 var8 = var1.getlocal(1).__getattr__("_sign");
                                 var10000 = var8._eq(Py.newInteger(0));
                                 var4 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(2136);
                                 var8 = var1.getlocal(0).__getattr__("_exp")._mul(var1.getglobal("int").__call__(var2, var1.getlocal(1)));
                                 var1.setlocal(10, var8);
                                 var4 = null;
                                 var1.setline(2137);
                                 var8 = var1.getglobal("min").__call__(var2, var1.getlocal(5)._sub(var1.getlocal(10)), var1.getlocal(2)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(25))));
                                 var1.setlocal(11, var8);
                                 var4 = null;
                              } else {
                                 var1.setline(2139);
                                 var11 = Py.newInteger(0);
                                 var1.setlocal(11, var11);
                                 var4 = null;
                              }

                              var1.setline(2140);
                              var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getlocal(25)._add(PyString.fromInterned("0")._mul(var1.getlocal(11))), (PyObject)var1.getlocal(5)._sub(var1.getlocal(11)));
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }

               var1.setline(1995);
               var3 = var1.getlocal(7);
               var3 = var3._ifloordiv(Py.newInteger(10));
               var1.setlocal(7, var3);
               var1.setline(1996);
               var3 = var1.getlocal(8);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(8, var3);
            }
         }

         var1.setline(1989);
         var3 = var1.getlocal(4);
         var3 = var3._ifloordiv(Py.newInteger(10));
         var1.setlocal(4, var3);
         var1.setline(1990);
         var3 = var1.getlocal(5);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(5, var3);
      }
   }

   public PyObject __pow__$88(PyFrame var1, ThreadState var2) {
      var1.setline(2164);
      PyString.fromInterned("Return self ** other [ % modulo].\n\n        With two arguments, compute self**other.\n\n        With three arguments, compute (self**other) % modulo.  For the\n        three argument form, the following restrictions on the\n        arguments hold:\n\n         - all three arguments must be integral\n         - other must be nonnegative\n         - either self or other (or both) must be nonzero\n         - modulo must be nonzero and must have at most p digits,\n           where p is the context precision.\n\n        If any of these restrictions is violated the InvalidOperation\n        flag is raised.\n\n        The result of pow(self, other, modulo) is identical to the\n        result that would be obtained by computing (self**other) %\n        modulo with unbounded precision, but is computed more\n        efficiently.  It is always exact.\n        ");
      var1.setline(2166);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2167);
         var3 = var1.getlocal(0).__getattr__("_power_modulo").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2169);
         PyObject var4 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(2170);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("NotImplemented"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2171);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2173);
            var4 = var1.getlocal(3);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2174);
               var4 = var1.getglobal("getcontext").__call__(var2);
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(2177);
            var4 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(2178);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(2179);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2182);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(2183);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(2184);
                     var3 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("0 ** 0"));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(2186);
                     var3 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(2189);
                  PyInteger var8 = Py.newInteger(0);
                  var1.setlocal(5, var8);
                  var4 = null;
                  var1.setline(2190);
                  var4 = var1.getlocal(0).__getattr__("_sign");
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2191);
                     if (var1.getlocal(1).__getattr__("_isinteger").__call__(var2).__nonzero__()) {
                        var1.setline(2192);
                        if (var1.getlocal(1).__getattr__("_iseven").__call__(var2).__not__().__nonzero__()) {
                           var1.setline(2193);
                           var8 = Py.newInteger(1);
                           var1.setlocal(5, var8);
                           var4 = null;
                        }
                     } else {
                        var1.setline(2197);
                        if (var1.getlocal(0).__nonzero__()) {
                           var1.setline(2198);
                           var3 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("x ** y with x negative and y not an integer"));
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }

                     var1.setline(2201);
                     var4 = var1.getlocal(0).__getattr__("copy_negate").__call__(var2);
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(2204);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(2205);
                     var4 = var1.getlocal(1).__getattr__("_sign");
                     var10000 = var4._eq(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2206);
                        var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0));
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(2208);
                        var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(5));
                        var1.f_lasti = -1;
                        return var3;
                     }
                  } else {
                     var1.setline(2211);
                     if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                        var1.setline(2212);
                        var4 = var1.getlocal(1).__getattr__("_sign");
                        var10000 = var4._eq(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2213);
                           var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(5));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(2215);
                           var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0));
                           var1.f_lasti = -1;
                           return var3;
                        }
                     } else {
                        var1.setline(2220);
                        var4 = var1.getlocal(0);
                        var10000 = var4._eq(var1.getglobal("_One"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2221);
                           if (var1.getlocal(1).__getattr__("_isinteger").__call__(var2).__nonzero__()) {
                              var1.setline(2226);
                              var4 = var1.getlocal(1).__getattr__("_sign");
                              var10000 = var4._eq(Py.newInteger(1));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2227);
                                 var8 = Py.newInteger(0);
                                 var1.setlocal(6, var8);
                                 var4 = null;
                              } else {
                                 var1.setline(2228);
                                 var4 = var1.getlocal(1);
                                 var10000 = var4._gt(var1.getlocal(3).__getattr__("prec"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2229);
                                    var4 = var1.getlocal(3).__getattr__("prec");
                                    var1.setlocal(6, var4);
                                    var4 = null;
                                 } else {
                                    var1.setline(2231);
                                    var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
                                    var1.setlocal(6, var4);
                                    var4 = null;
                                 }
                              }

                              var1.setline(2233);
                              var4 = var1.getlocal(0).__getattr__("_exp")._mul(var1.getlocal(6));
                              var1.setlocal(7, var4);
                              var4 = null;
                              var1.setline(2234);
                              var4 = var1.getlocal(7);
                              var10000 = var4._lt(Py.newInteger(1)._sub(var1.getlocal(3).__getattr__("prec")));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2235);
                                 var4 = Py.newInteger(1)._sub(var1.getlocal(3).__getattr__("prec"));
                                 var1.setlocal(7, var4);
                                 var4 = null;
                                 var1.setline(2236);
                                 var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
                              }
                           } else {
                              var1.setline(2238);
                              var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
                              var1.setline(2239);
                              var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
                              var1.setline(2240);
                              var4 = Py.newInteger(1)._sub(var1.getlocal(3).__getattr__("prec"));
                              var1.setlocal(7, var4);
                              var4 = null;
                           }

                           var1.setline(2242);
                           var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(5), PyString.fromInterned("1")._add(PyString.fromInterned("0")._mul(var1.getlocal(7).__neg__())), var1.getlocal(7));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(2245);
                           var4 = var1.getlocal(0).__getattr__("adjusted").__call__(var2);
                           var1.setlocal(8, var4);
                           var4 = null;
                           var1.setline(2249);
                           PyObject var6;
                           if (var1.getlocal(1).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                              var1.setline(2250);
                              var6 = var1.getlocal(1).__getattr__("_sign");
                              var10000 = var6._eq(Py.newInteger(0));
                              var6 = null;
                              var4 = var10000;
                              var6 = var1.getlocal(8);
                              var10000 = var6._lt(Py.newInteger(0));
                              var6 = null;
                              var10000 = var4._eq(var10000);
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2251);
                                 var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0));
                                 var1.f_lasti = -1;
                                 return var3;
                              } else {
                                 var1.setline(2253);
                                 var3 = var1.getglobal("_SignedInfinity").__getitem__(var1.getlocal(5));
                                 var1.f_lasti = -1;
                                 return var3;
                              }
                           } else {
                              var1.setline(2257);
                              var4 = var1.getglobal("None");
                              var1.setlocal(4, var4);
                              var4 = null;
                              var1.setline(2258);
                              var4 = var1.getglobal("False");
                              var1.setlocal(9, var4);
                              var4 = null;
                              var1.setline(2265);
                              var4 = var1.getlocal(0).__getattr__("_log10_exp_bound").__call__(var2)._add(var1.getlocal(1).__getattr__("adjusted").__call__(var2));
                              var1.setlocal(10, var4);
                              var4 = null;
                              var1.setline(2266);
                              var6 = var1.getlocal(8);
                              var10000 = var6._ge(Py.newInteger(0));
                              var6 = null;
                              var4 = var10000;
                              var6 = var1.getlocal(1).__getattr__("_sign");
                              var10000 = var6._eq(Py.newInteger(0));
                              var6 = null;
                              var10000 = var4._eq(var10000);
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2269);
                                 var4 = var1.getlocal(10);
                                 var10000 = var4._ge(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3).__getattr__("Emax"))));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2270);
                                    var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(3).__getattr__("Emax")._add(Py.newInteger(1)));
                                    var1.setlocal(4, var4);
                                    var4 = null;
                                 }
                              } else {
                                 var1.setline(2274);
                                 var4 = var1.getlocal(3).__getattr__("Etiny").__call__(var2);
                                 var1.setlocal(11, var4);
                                 var4 = null;
                                 var1.setline(2275);
                                 var4 = var1.getlocal(10);
                                 var10000 = var4._ge(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(11).__neg__())));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2276);
                                    var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(11)._sub(Py.newInteger(1)));
                                    var1.setlocal(4, var4);
                                    var4 = null;
                                 }
                              }

                              var1.setline(2279);
                              var4 = var1.getlocal(4);
                              var10000 = var4._is(var1.getglobal("None"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2280);
                                 var4 = var1.getlocal(0).__getattr__("_power_exact").__call__(var2, var1.getlocal(1), var1.getlocal(3).__getattr__("prec")._add(Py.newInteger(1)));
                                 var1.setlocal(4, var4);
                                 var4 = null;
                                 var1.setline(2281);
                                 var4 = var1.getlocal(4);
                                 var10000 = var4._isnot(var1.getglobal("None"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2282);
                                    var4 = var1.getlocal(5);
                                    var10000 = var4._eq(Py.newInteger(1));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(2283);
                                       var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getlocal(4).__getattr__("_int"), (PyObject)var1.getlocal(4).__getattr__("_exp"));
                                       var1.setlocal(4, var4);
                                       var4 = null;
                                    }

                                    var1.setline(2284);
                                    var4 = var1.getglobal("True");
                                    var1.setlocal(9, var4);
                                    var4 = null;
                                 }
                              }

                              var1.setline(2287);
                              var4 = var1.getlocal(4);
                              var10000 = var4._is(var1.getglobal("None"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2288);
                                 var4 = var1.getlocal(3).__getattr__("prec");
                                 var1.setlocal(12, var4);
                                 var4 = null;
                                 var1.setline(2289);
                                 var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                                 var1.setlocal(13, var4);
                                 var4 = null;
                                 var1.setline(2290);
                                 PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(13).__getattr__("int"), var1.getlocal(13).__getattr__("exp")});
                                 PyObject[] var5 = Py.unpackSequence(var10, 2);
                                 var6 = var5[0];
                                 var1.setlocal(14, var6);
                                 var6 = null;
                                 var6 = var5[1];
                                 var1.setlocal(15, var6);
                                 var6 = null;
                                 var4 = null;
                                 var1.setline(2291);
                                 var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(1));
                                 var1.setlocal(16, var4);
                                 var4 = null;
                                 var1.setline(2292);
                                 var10 = new PyTuple(new PyObject[]{var1.getlocal(16).__getattr__("int"), var1.getlocal(16).__getattr__("exp")});
                                 var5 = Py.unpackSequence(var10, 2);
                                 var6 = var5[0];
                                 var1.setlocal(17, var6);
                                 var6 = null;
                                 var6 = var5[1];
                                 var1.setlocal(18, var6);
                                 var6 = null;
                                 var4 = null;
                                 var1.setline(2293);
                                 var4 = var1.getlocal(16).__getattr__("sign");
                                 var10000 = var4._eq(Py.newInteger(1));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2294);
                                    var4 = var1.getlocal(17).__neg__();
                                    var1.setlocal(17, var4);
                                    var4 = null;
                                 }

                                 var1.setline(2298);
                                 var8 = Py.newInteger(3);
                                 var1.setlocal(19, var8);
                                 var4 = null;

                                 while(true) {
                                    var1.setline(2299);
                                    if (!var1.getglobal("True").__nonzero__()) {
                                       break;
                                    }

                                    var1.setline(2300);
                                    var10000 = var1.getglobal("_dpower");
                                    PyObject[] var11 = new PyObject[]{var1.getlocal(14), var1.getlocal(15), var1.getlocal(17), var1.getlocal(18), var1.getlocal(12)._add(var1.getlocal(19))};
                                    var4 = var10000.__call__(var2, var11);
                                    var5 = Py.unpackSequence(var4, 2);
                                    var6 = var5[0];
                                    var1.setlocal(20, var6);
                                    var6 = null;
                                    var6 = var5[1];
                                    var1.setlocal(7, var6);
                                    var6 = null;
                                    var4 = null;
                                    var1.setline(2301);
                                    if (var1.getlocal(20)._mod(Py.newInteger(5)._mul(Py.newInteger(10)._pow(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(20)))._sub(var1.getlocal(12))._sub(Py.newInteger(1))))).__nonzero__()) {
                                       break;
                                    }

                                    var1.setline(2303);
                                    var4 = var1.getlocal(19);
                                    var4 = var4._iadd(Py.newInteger(3));
                                    var1.setlocal(19, var4);
                                 }

                                 var1.setline(2305);
                                 var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(5), var1.getglobal("str").__call__(var2, var1.getlocal(20)), var1.getlocal(7));
                                 var1.setlocal(4, var4);
                                 var4 = null;
                              }

                              var1.setline(2320);
                              var10000 = var1.getlocal(9);
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(1).__getattr__("_isinteger").__call__(var2).__not__();
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(2323);
                                 var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("_int"));
                                 var10000 = var4._le(var1.getlocal(3).__getattr__("prec"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(2324);
                                    var4 = var1.getlocal(3).__getattr__("prec")._add(Py.newInteger(1))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("_int")));
                                    var1.setlocal(21, var4);
                                    var4 = null;
                                    var1.setline(2325);
                                    var4 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(4).__getattr__("_sign"), var1.getlocal(4).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(21))), var1.getlocal(4).__getattr__("_exp")._sub(var1.getlocal(21)));
                                    var1.setlocal(4, var4);
                                    var4 = null;
                                 }

                                 var1.setline(2329);
                                 var4 = var1.getlocal(3).__getattr__("copy").__call__(var2);
                                 var1.setlocal(22, var4);
                                 var4 = null;
                                 var1.setline(2330);
                                 var1.getlocal(22).__getattr__("clear_flags").__call__(var2);
                                 var1.setline(2331);
                                 var4 = var1.getglobal("_signals").__iter__();

                                 label138:
                                 while(true) {
                                    var1.setline(2331);
                                    PyObject var7 = var4.__iternext__();
                                    if (var7 == null) {
                                       var1.setline(2335);
                                       var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(22));
                                       var1.setlocal(4, var4);
                                       var4 = null;
                                       var1.setline(2338);
                                       var1.getlocal(22).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
                                       var1.setline(2339);
                                       if (var1.getlocal(22).__getattr__("flags").__getitem__(var1.getglobal("Subnormal")).__nonzero__()) {
                                          var1.setline(2340);
                                          var1.getlocal(22).__getattr__("_raise_error").__call__(var2, var1.getglobal("Underflow"));
                                       }

                                       var1.setline(2347);
                                       if (var1.getlocal(22).__getattr__("flags").__getitem__(var1.getglobal("Overflow")).__nonzero__()) {
                                          var1.setline(2348);
                                          var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("Overflow"), (PyObject)PyString.fromInterned("above Emax"), (PyObject)var1.getlocal(4).__getattr__("_sign"));
                                       }

                                       var1.setline(2349);
                                       var4 = (new PyTuple(new PyObject[]{var1.getglobal("Underflow"), var1.getglobal("Subnormal"), var1.getglobal("Inexact"), var1.getglobal("Rounded"), var1.getglobal("Clamped")})).__iter__();

                                       while(true) {
                                          var1.setline(2349);
                                          var7 = var4.__iternext__();
                                          if (var7 == null) {
                                             break label138;
                                          }

                                          var1.setlocal(23, var7);
                                          var1.setline(2350);
                                          if (var1.getlocal(22).__getattr__("flags").__getitem__(var1.getlocal(23)).__nonzero__()) {
                                             var1.setline(2351);
                                             var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getlocal(23));
                                          }
                                       }
                                    }

                                    var1.setlocal(23, var7);
                                    var1.setline(2332);
                                    PyInteger var9 = Py.newInteger(0);
                                    var1.getlocal(22).__getattr__("traps").__setitem__((PyObject)var1.getlocal(23), var9);
                                    var6 = null;
                                 }
                              } else {
                                 var1.setline(2354);
                                 var4 = var1.getlocal(4).__getattr__("_fix").__call__(var2, var1.getlocal(3));
                                 var1.setlocal(4, var4);
                                 var4 = null;
                              }

                              var1.setline(2356);
                              var3 = var1.getlocal(4);
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject __rpow__$89(PyFrame var1, ThreadState var2) {
      var1.setline(2359);
      PyString.fromInterned("Swaps self/other and returns __pow__.");
      var1.setline(2360);
      PyObject var3 = var1.getglobal("_convert_other").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2361);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2362);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2363);
         var10000 = var1.getlocal(1).__getattr__("__pow__");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject normalize$90(PyFrame var1, ThreadState var2) {
      var1.setline(2366);
      PyString.fromInterned("Normalize- strip trailing 0s, change anything equal to 0 to 0e0");
      var1.setline(2368);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2369);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2371);
      String[] var4;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2372);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2373);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2374);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(2376);
      PyObject var6 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var6);
      var4 = null;
      var1.setline(2377);
      if (var1.getlocal(3).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
         var1.setline(2378);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2380);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(2381);
            var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(3).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2382);
            var6 = (new PyList(new PyObject[]{var1.getlocal(1).__getattr__("Emax"), var1.getlocal(1).__getattr__("Etop").__call__(var2)})).__getitem__(var1.getlocal(1).__getattr__("_clamp"));
            var1.setlocal(4, var6);
            var4 = null;
            var1.setline(2383);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("_int"));
            var1.setlocal(5, var6);
            var4 = null;
            var1.setline(2384);
            var6 = var1.getlocal(3).__getattr__("_exp");
            var1.setlocal(6, var6);
            var4 = null;

            while(true) {
               var1.setline(2385);
               var6 = var1.getlocal(3).__getattr__("_int").__getitem__(var1.getlocal(5)._sub(Py.newInteger(1)));
               var10000 = var6._eq(PyString.fromInterned("0"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(6);
                  var10000 = var6._lt(var1.getlocal(4));
                  var4 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(2388);
                  var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(3).__getattr__("_sign"), var1.getlocal(3).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null), var1.getlocal(6));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(2386);
               var6 = var1.getlocal(6);
               var6 = var6._iadd(Py.newInteger(1));
               var1.setlocal(6, var6);
               var1.setline(2387);
               var6 = var1.getlocal(5);
               var6 = var6._isub(Py.newInteger(1));
               var1.setlocal(5, var6);
            }
         }
      }
   }

   public PyObject quantize$91(PyFrame var1, ThreadState var2) {
      var1.setline(2394);
      PyString.fromInterned("Quantize self so its exponent is the same as that of exp.\n\n        Similar to self._rescale(exp._exp) but with error checking.\n        ");
      var1.setline(2395);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(2397);
      var6 = var1.getlocal(3);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2398);
         var6 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(2399);
      var6 = var1.getlocal(2);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2400);
         var6 = var1.getlocal(3).__getattr__("rounding");
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(2402);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      if (var10000.__nonzero__()) {
         var1.setline(2403);
         var6 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(2404);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(2405);
            var6 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(2407);
         var10000 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(2408);
            var10000 = var1.getlocal(1).__getattr__("_isinfinity").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            }

            if (var10000.__nonzero__()) {
               var1.setline(2409);
               var6 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var6;
            }

            var1.setline(2410);
            var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("quantize with one INF"));
            var1.f_lasti = -1;
            return var6;
         }
      }

      var1.setline(2414);
      PyObject var7;
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(2415);
         var7 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(1).__getattr__("_exp"), var1.getlocal(2));
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(2417);
         var7 = var1.getlocal(5).__getattr__("_exp");
         var10000 = var7._gt(var1.getlocal(0).__getattr__("_exp"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2418);
            var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
            var1.setline(2419);
            var7 = var1.getlocal(5);
            var10000 = var7._ne(var1.getlocal(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2420);
               var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
            }
         }

         var1.setline(2421);
         var6 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(2424);
         var7 = var1.getlocal(3).__getattr__("Etiny").__call__(var2);
         PyObject var10001 = var1.getlocal(1).__getattr__("_exp");
         var10000 = var7;
         var7 = var10001;
         PyObject var5;
         if ((var5 = var10000._le(var10001)).__nonzero__()) {
            var5 = var7._le(var1.getlocal(3).__getattr__("Emax"));
         }

         var4 = null;
         if (var5.__not__().__nonzero__()) {
            var1.setline(2425);
            var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("target exponent out of bounds in quantize"));
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(2428);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(2429);
               var7 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(1).__getattr__("_exp"));
               var1.setlocal(5, var7);
               var4 = null;
               var1.setline(2430);
               var6 = var1.getlocal(5).__getattr__("_fix").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(2432);
               var7 = var1.getlocal(0).__getattr__("adjusted").__call__(var2);
               var1.setlocal(6, var7);
               var4 = null;
               var1.setline(2433);
               var7 = var1.getlocal(6);
               var10000 = var7._gt(var1.getlocal(3).__getattr__("Emax"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2434);
                  var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("exponent of quantize result too large for current context"));
                  var1.f_lasti = -1;
                  return var6;
               } else {
                  var1.setline(2436);
                  var7 = var1.getlocal(6)._sub(var1.getlocal(1).__getattr__("_exp"))._add(Py.newInteger(1));
                  var10000 = var7._gt(var1.getlocal(3).__getattr__("prec"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2437);
                     var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("quantize result has too many digits for current context"));
                     var1.f_lasti = -1;
                     return var6;
                  } else {
                     var1.setline(2440);
                     var7 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(1).__getattr__("_exp"), var1.getlocal(2));
                     var1.setlocal(5, var7);
                     var4 = null;
                     var1.setline(2441);
                     var7 = var1.getlocal(5).__getattr__("adjusted").__call__(var2);
                     var10000 = var7._gt(var1.getlocal(3).__getattr__("Emax"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2442);
                        var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("exponent of quantize result too large for current context"));
                        var1.f_lasti = -1;
                        return var6;
                     } else {
                        var1.setline(2444);
                        var7 = var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("_int"));
                        var10000 = var7._gt(var1.getlocal(3).__getattr__("prec"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(2445);
                           var6 = var1.getlocal(3).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("quantize result has too many digits for current context"));
                           var1.f_lasti = -1;
                           return var6;
                        } else {
                           var1.setline(2449);
                           var10000 = var1.getlocal(5);
                           if (var10000.__nonzero__()) {
                              var7 = var1.getlocal(5).__getattr__("adjusted").__call__(var2);
                              var10000 = var7._lt(var1.getlocal(3).__getattr__("Emin"));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(2450);
                              var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Subnormal"));
                           }

                           var1.setline(2451);
                           var7 = var1.getlocal(5).__getattr__("_exp");
                           var10000 = var7._gt(var1.getlocal(0).__getattr__("_exp"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(2452);
                              var7 = var1.getlocal(5);
                              var10000 = var7._ne(var1.getlocal(0));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2453);
                                 var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
                              }

                              var1.setline(2454);
                              var1.getlocal(3).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
                           }

                           var1.setline(2458);
                           var7 = var1.getlocal(5).__getattr__("_fix").__call__(var2, var1.getlocal(3));
                           var1.setlocal(5, var7);
                           var4 = null;
                           var1.setline(2459);
                           var6 = var1.getlocal(5);
                           var1.f_lasti = -1;
                           return var6;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject same_quantum$92(PyFrame var1, ThreadState var2) {
      var1.setline(2469);
      PyString.fromInterned("Return True if self and other have the same exponent; otherwise\n        return False.\n\n        If either operand is a special value, the following rules are used:\n           * return True if both operands are infinities\n           * return True if both operands are NaNs\n           * otherwise, return False.\n        ");
      var1.setline(2470);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2471);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      if (var10000.__nonzero__()) {
         var1.setline(2472);
         var10000 = var1.getlocal(0).__getattr__("is_nan").__call__(var2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("is_nan").__call__(var2);
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("is_infinite").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("is_infinite").__call__(var2);
            }
         }

         var5 = var10000;
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(2474);
         PyObject var6 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var6._eq(var1.getlocal(1).__getattr__("_exp"));
         var4 = null;
         var5 = var10000;
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _rescale$93(PyFrame var1, ThreadState var2) {
      var1.setline(2486);
      PyString.fromInterned("Rescale self so that the exponent is exp, either by padding with zeros\n        or by truncating digits, using the given rounding mode.\n\n        Specials are returned without change.  This operation is\n        quiet: it raises no flags, and uses no information from the\n        context.\n\n        exp = exp to scale to (an integer)\n        rounding = rounding mode\n        ");
      var1.setline(2487);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2488);
         var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2489);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(2490);
            var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2492);
            PyObject var4 = var1.getlocal(0).__getattr__("_exp");
            PyObject var10000 = var4._ge(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2494);
               var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(0).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(0).__getattr__("_exp")._sub(var1.getlocal(1)))), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2499);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._add(var1.getlocal(0).__getattr__("_exp"))._sub(var1.getlocal(1));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2500);
               var4 = var1.getlocal(3);
               var10000 = var4._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2501);
                  var4 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(1)._sub(Py.newInteger(1)));
                  var1.setlocal(0, var4);
                  var4 = null;
                  var1.setline(2502);
                  PyInteger var5 = Py.newInteger(0);
                  var1.setlocal(3, var5);
                  var4 = null;
               }

               var1.setline(2503);
               var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("_pick_rounding_function").__getitem__(var1.getlocal(2)));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(2504);
               var4 = var1.getlocal(4).__call__(var2, var1.getlocal(3));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(2505);
               Object var7 = var1.getlocal(0).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
               if (!((PyObject)var7).__nonzero__()) {
                  var7 = PyString.fromInterned("0");
               }

               Object var6 = var7;
               var1.setlocal(6, (PyObject)var6);
               var4 = null;
               var1.setline(2506);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2507);
                  var4 = var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(6))._add(Py.newInteger(1)));
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(2508);
               var3 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(6), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _round$94(PyFrame var1, ThreadState var2) {
      var1.setline(2519);
      PyString.fromInterned("Round a nonzero, nonspecial Decimal to a fixed number of\n        significant figures, using the given rounding mode.\n\n        Infinities, NaNs and zeros are returned unaltered.\n\n        This operation is quiet: it raises no flags, and uses no\n        information from the context.\n\n        ");
      var1.setline(2520);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2521);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument should be at least 1 in _round")));
      } else {
         var1.setline(2522);
         var10000 = var1.getlocal(0).__getattr__("_is_special");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(2523);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2524);
            PyObject var4 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(0).__getattr__("adjusted").__call__(var2)._add(Py.newInteger(1))._sub(var1.getlocal(1)), var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(2529);
            var4 = var1.getlocal(3).__getattr__("adjusted").__call__(var2);
            var10000 = var4._ne(var1.getlocal(0).__getattr__("adjusted").__call__(var2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2530);
               var4 = var1.getlocal(3).__getattr__("_rescale").__call__(var2, var1.getlocal(3).__getattr__("adjusted").__call__(var2)._add(Py.newInteger(1))._sub(var1.getlocal(1)), var1.getlocal(2));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(2531);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject to_integral_exact$95(PyFrame var1, ThreadState var2) {
      var1.setline(2542);
      PyString.fromInterned("Rounds to a nearby integer.\n\n        If no rounding mode is specified, take the rounding mode from\n        the context.  This method raises the Rounded and Inexact flags\n        when appropriate.\n\n        See also: to_integral_value, which does exactly the same as\n        this method except that it doesn't raise Inexact or Rounded.\n        ");
      var1.setline(2543);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2544);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
         String[] var6 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var5, var6);
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2545);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(2546);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2547);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(2548);
         PyObject var4 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2549);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2550);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(2551);
               var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)Py.newInteger(0));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2552);
               var4 = var1.getlocal(2);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2553);
                  var4 = var1.getglobal("getcontext").__call__(var2);
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(2554);
               var4 = var1.getlocal(1);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2555);
                  var4 = var1.getlocal(2).__getattr__("rounding");
                  var1.setlocal(1, var4);
                  var4 = null;
               }

               var1.setline(2556);
               var4 = var1.getlocal(0).__getattr__("_rescale").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2557);
               var4 = var1.getlocal(3);
               var10000 = var4._ne(var1.getlocal(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2558);
                  var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
               }

               var1.setline(2559);
               var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
               var1.setline(2560);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject to_integral_value$96(PyFrame var1, ThreadState var2) {
      var1.setline(2563);
      PyString.fromInterned("Rounds to the nearest integer, without raising inexact, rounded.");
      var1.setline(2564);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2565);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(2566);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2567);
         var3 = var1.getlocal(2).__getattr__("rounding");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2568);
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2569);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var6 = new PyObject[]{var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var6, var5);
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2570);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(2571);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2572);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(2573);
         PyObject var4 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2574);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2576);
            var3 = var1.getlocal(0).__getattr__("_rescale").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject sqrt$97(PyFrame var1, ThreadState var2) {
      var1.setline(2582);
      PyString.fromInterned("Return the square root of self.");
      var1.setline(2583);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2584);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2586);
      String[] var4;
      PyObject var8;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2587);
         var10000 = var1.getlocal(0).__getattr__("_check_nans");
         PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var7, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2588);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2589);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(2591);
         var10000 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         if (var10000.__nonzero__()) {
            var8 = var1.getlocal(0).__getattr__("_sign");
            var10000 = var8._eq(Py.newInteger(0));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(2592);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(2594);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(2596);
         var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_sign"), (PyObject)PyString.fromInterned("0"), (PyObject)var1.getlocal(0).__getattr__("_exp")._floordiv(Py.newInteger(2)));
         var1.setlocal(2, var8);
         var4 = null;
         var1.setline(2597);
         var3 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2599);
         var8 = var1.getlocal(0).__getattr__("_sign");
         var10000 = var8._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2600);
            var3 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("sqrt(-x), x > 0"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2622);
            var8 = var1.getlocal(1).__getattr__("prec")._add(Py.newInteger(1));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(2628);
            var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(2629);
            var8 = var1.getlocal(4).__getattr__("exp")._rshift(Py.newInteger(1));
            var1.setlocal(5, var8);
            var4 = null;
            var1.setline(2630);
            if (var1.getlocal(4).__getattr__("exp")._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(2631);
               var8 = var1.getlocal(4).__getattr__("int")._mul(Py.newInteger(10));
               var1.setlocal(6, var8);
               var4 = null;
               var1.setline(2632);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._rshift(Py.newInteger(1))._add(Py.newInteger(1));
               var1.setlocal(7, var8);
               var4 = null;
            } else {
               var1.setline(2634);
               var8 = var1.getlocal(4).__getattr__("int");
               var1.setlocal(6, var8);
               var4 = null;
               var1.setline(2635);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._add(Py.newInteger(1))._rshift(Py.newInteger(1));
               var1.setlocal(7, var8);
               var4 = null;
            }

            var1.setline(2638);
            var8 = var1.getlocal(3)._sub(var1.getlocal(7));
            var1.setlocal(8, var8);
            var4 = null;
            var1.setline(2639);
            var8 = var1.getlocal(8);
            var10000 = var8._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2640);
               var8 = var1.getlocal(6);
               var8 = var8._imul(Py.newInteger(100)._pow(var1.getlocal(8)));
               var1.setlocal(6, var8);
               var1.setline(2641);
               var8 = var1.getglobal("True");
               var1.setlocal(9, var8);
               var4 = null;
            } else {
               var1.setline(2643);
               var8 = var1.getglobal("divmod").__call__(var2, var1.getlocal(6), Py.newInteger(100)._pow(var1.getlocal(8).__neg__()));
               PyObject[] var5 = Py.unpackSequence(var8, 2);
               PyObject var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var4 = null;
               var1.setline(2644);
               var8 = var1.getlocal(10).__not__();
               var1.setlocal(9, var8);
               var4 = null;
            }

            var1.setline(2645);
            var8 = var1.getlocal(5);
            var8 = var8._isub(var1.getlocal(8));
            var1.setlocal(5, var8);
            var1.setline(2648);
            var8 = Py.newInteger(10)._pow(var1.getlocal(3));
            var1.setlocal(11, var8);
            var4 = null;

            while(true) {
               var1.setline(2649);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(2650);
               var8 = var1.getlocal(6)._floordiv(var1.getlocal(11));
               var1.setlocal(12, var8);
               var4 = null;
               var1.setline(2651);
               var8 = var1.getlocal(11);
               var10000 = var8._le(var1.getlocal(12));
               var4 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(2654);
               var8 = var1.getlocal(11)._add(var1.getlocal(12))._rshift(Py.newInteger(1));
               var1.setlocal(11, var8);
               var4 = null;
            }

            var1.setline(2655);
            var10000 = var1.getlocal(9);
            if (var10000.__nonzero__()) {
               var8 = var1.getlocal(11)._mul(var1.getlocal(11));
               var10000 = var8._eq(var1.getlocal(6));
               var4 = null;
            }

            var8 = var10000;
            var1.setlocal(9, var8);
            var4 = null;
            var1.setline(2657);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(2659);
               var8 = var1.getlocal(8);
               var10000 = var8._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2661);
                  var8 = var1.getlocal(11);
                  var8 = var8._ifloordiv(Py.newInteger(10)._pow(var1.getlocal(8)));
                  var1.setlocal(11, var8);
               } else {
                  var1.setline(2663);
                  var8 = var1.getlocal(11);
                  var8 = var8._imul(Py.newInteger(10)._pow(var1.getlocal(8).__neg__()));
                  var1.setlocal(11, var8);
               }

               var1.setline(2664);
               var8 = var1.getlocal(5);
               var8 = var8._iadd(var1.getlocal(8));
               var1.setlocal(5, var8);
            } else {
               var1.setline(2667);
               var8 = var1.getlocal(11)._mod(Py.newInteger(5));
               var10000 = var8._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2668);
                  var8 = var1.getlocal(11);
                  var8 = var8._iadd(Py.newInteger(1));
                  var1.setlocal(11, var8);
               }
            }

            var1.setline(2670);
            var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(11)), (PyObject)var1.getlocal(5));
            var1.setlocal(2, var8);
            var4 = null;
            var1.setline(2673);
            var8 = var1.getlocal(1).__getattr__("_shallow_copy").__call__(var2);
            var1.setlocal(1, var8);
            var4 = null;
            var1.setline(2674);
            var8 = var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_HALF_EVEN"));
            var1.setlocal(13, var8);
            var4 = null;
            var1.setline(2675);
            var8 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var8);
            var4 = null;
            var1.setline(2676);
            var8 = var1.getlocal(13);
            var1.getlocal(1).__setattr__("rounding", var8);
            var4 = null;
            var1.setline(2678);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject max$98(PyFrame var1, ThreadState var2) {
      var1.setline(2685);
      PyString.fromInterned("Returns the larger value.\n\n        Like max(self, other) except if one is not a number, returns\n        NaN (and signals if one is sNaN).  Also rounds.\n        ");
      var1.setline(2686);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2688);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2689);
         var5 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(2691);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(2694);
         var5 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(2695);
         var5 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(2696);
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(2697);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._eq(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(2698);
               var5 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(2699);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(4);
               var10000 = var6._eq(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(2700);
               var5 = var1.getlocal(1).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(2701);
            var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(2703);
      var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var6);
      var4 = null;
      var1.setline(2704);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2713);
         var6 = var1.getlocal(0).__getattr__("compare_total").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var6);
         var4 = null;
      }

      var1.setline(2715);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(-1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2716);
         var6 = var1.getlocal(1);
         var1.setlocal(6, var6);
         var4 = null;
      } else {
         var1.setline(2718);
         var6 = var1.getlocal(0);
         var1.setlocal(6, var6);
         var4 = null;
      }

      var1.setline(2720);
      var5 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject min$99(PyFrame var1, ThreadState var2) {
      var1.setline(2727);
      PyString.fromInterned("Returns the smaller value.\n\n        Like min(self, other) except if one is not a number, returns\n        NaN (and signals if one is sNaN).  Also rounds.\n        ");
      var1.setline(2728);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2730);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2731);
         var5 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(2733);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(2736);
         var5 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(2737);
         var5 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(2738);
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(2739);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._eq(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(2740);
               var5 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(2741);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(4);
               var10000 = var6._eq(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(2742);
               var5 = var1.getlocal(1).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(2743);
            var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(2745);
      var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var6);
      var4 = null;
      var1.setline(2746);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2747);
         var6 = var1.getlocal(0).__getattr__("compare_total").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var6);
         var4 = null;
      }

      var1.setline(2749);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(-1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2750);
         var6 = var1.getlocal(0);
         var1.setlocal(6, var6);
         var4 = null;
      } else {
         var1.setline(2752);
         var6 = var1.getlocal(1);
         var1.setlocal(6, var6);
         var4 = null;
      }

      var1.setline(2754);
      var5 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _isinteger$100(PyFrame var1, ThreadState var2) {
      var1.setline(2757);
      PyString.fromInterned("Returns whether self is an integer");
      var1.setline(2758);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(2759);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2760);
         PyObject var4 = var1.getlocal(0).__getattr__("_exp");
         PyObject var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2761);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2762);
            var4 = var1.getlocal(0).__getattr__("_int").__getslice__(var1.getlocal(0).__getattr__("_exp"), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(2763);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(PyString.fromInterned("0")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1))));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _iseven$101(PyFrame var1, ThreadState var2) {
      var1.setline(2766);
      PyString.fromInterned("Returns True if self is even.  Assumes self is an integer.");
      var1.setline(2767);
      PyObject var10000 = var1.getlocal(0).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2768);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2769);
         PyObject var4 = var1.getlocal(0).__getattr__("_int").__getitem__(Py.newInteger(-1)._add(var1.getlocal(0).__getattr__("_exp")));
         var10000 = var4._in(PyString.fromInterned("02468"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject adjusted$102(PyFrame var1, ThreadState var2) {
      var1.setline(2772);
      PyString.fromInterned("Return the adjusted exponent of self");

      try {
         var1.setline(2774);
         PyObject var6 = var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))._sub(Py.newInteger(1));
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("TypeError"))) {
            var1.setline(2777);
            PyInteger var3 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject canonical$103(PyFrame var1, ThreadState var2) {
      var1.setline(2784);
      PyString.fromInterned("Returns the same Decimal object.\n\n        As we do not have different encodings for the same number, the\n        received object already is in its canonical form.\n        ");
      var1.setline(2785);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compare_signal$104(PyFrame var1, ThreadState var2) {
      var1.setline(2792);
      PyString.fromInterned("Compares self to the other operand numerically.\n\n        It's pretty much like compare(), but all NaNs signal, with signaling\n        NaNs taking precedence over quiet NaNs.\n        ");
      var1.setline(2793);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(2794);
      var6 = var1.getlocal(0).__getattr__("_compare_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(2795);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(2796);
         var6 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(2797);
         var10000 = var1.getlocal(0).__getattr__("compare");
         PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var7, var5);
         var4 = null;
         var6 = var10000;
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject compare_total$105(PyFrame var1, ThreadState var2) {
      var1.setline(2805);
      PyString.fromInterned("Compares self to other using the abstract representations.\n\n        This is not like the standard compare, which use their numerical\n        value. Note that a total ordering is defined for all possible abstract\n        representations.\n        ");
      var1.setline(2806);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2809);
      var10000 = var1.getlocal(0).__getattr__("_sign");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_sign").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(2810);
         var5 = var1.getglobal("_NegativeOne");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(2811);
         var10000 = var1.getlocal(0).__getattr__("_sign").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("_sign");
         }

         if (var10000.__nonzero__()) {
            var1.setline(2812);
            var5 = var1.getglobal("_One");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(2813);
            PyObject var6 = var1.getlocal(0).__getattr__("_sign");
            var1.setlocal(2, var6);
            var4 = null;
            var1.setline(2816);
            var6 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(2817);
            var6 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
            var1.setlocal(4, var6);
            var4 = null;
            var1.setline(2818);
            var10000 = var1.getlocal(3);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(4);
            }

            if (var10000.__nonzero__()) {
               var1.setline(2819);
               var6 = var1.getlocal(3);
               var10000 = var6._eq(var1.getlocal(4));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2821);
                  PyTuple var7 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")), var1.getlocal(0).__getattr__("_int")});
                  var1.setlocal(5, var7);
                  var4 = null;
                  var1.setline(2822);
                  var7 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("_int")), var1.getlocal(1).__getattr__("_int")});
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(2823);
                  var6 = var1.getlocal(5);
                  var10000 = var6._lt(var1.getlocal(6));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2824);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(2825);
                        var5 = var1.getglobal("_One");
                        var1.f_lasti = -1;
                        return var5;
                     }

                     var1.setline(2827);
                     var5 = var1.getglobal("_NegativeOne");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2828);
                  var6 = var1.getlocal(5);
                  var10000 = var6._gt(var1.getlocal(6));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2829);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(2830);
                        var5 = var1.getglobal("_NegativeOne");
                        var1.f_lasti = -1;
                        return var5;
                     }

                     var1.setline(2832);
                     var5 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2833);
                  var5 = var1.getglobal("_Zero");
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setline(2835);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(2836);
                  var6 = var1.getlocal(3);
                  var10000 = var6._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2837);
                     var5 = var1.getglobal("_NegativeOne");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2838);
                  var6 = var1.getlocal(4);
                  var10000 = var6._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2839);
                     var5 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2840);
                  var6 = var1.getlocal(3);
                  var10000 = var6._eq(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2841);
                     var5 = var1.getglobal("_NegativeOne");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2842);
                  var6 = var1.getlocal(4);
                  var10000 = var6._eq(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2843);
                     var5 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var5;
                  }
               } else {
                  var1.setline(2845);
                  var6 = var1.getlocal(3);
                  var10000 = var6._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2846);
                     var5 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2847);
                  var6 = var1.getlocal(4);
                  var10000 = var6._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2848);
                     var5 = var1.getglobal("_NegativeOne");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2849);
                  var6 = var1.getlocal(3);
                  var10000 = var6._eq(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2850);
                     var5 = var1.getglobal("_One");
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(2851);
                  var6 = var1.getlocal(4);
                  var10000 = var6._eq(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2852);
                     var5 = var1.getglobal("_NegativeOne");
                     var1.f_lasti = -1;
                     return var5;
                  }
               }
            }

            var1.setline(2854);
            var6 = var1.getlocal(0);
            var10000 = var6._lt(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2855);
               var5 = var1.getglobal("_NegativeOne");
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(2856);
               var6 = var1.getlocal(0);
               var10000 = var6._gt(var1.getlocal(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2857);
                  var5 = var1.getglobal("_One");
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(2859);
                  var6 = var1.getlocal(0).__getattr__("_exp");
                  var10000 = var6._lt(var1.getlocal(1).__getattr__("_exp"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2860);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(2861);
                        var5 = var1.getglobal("_One");
                        var1.f_lasti = -1;
                        return var5;
                     } else {
                        var1.setline(2863);
                        var5 = var1.getglobal("_NegativeOne");
                        var1.f_lasti = -1;
                        return var5;
                     }
                  } else {
                     var1.setline(2864);
                     var6 = var1.getlocal(0).__getattr__("_exp");
                     var10000 = var6._gt(var1.getlocal(1).__getattr__("_exp"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2865);
                        if (var1.getlocal(2).__nonzero__()) {
                           var1.setline(2866);
                           var5 = var1.getglobal("_NegativeOne");
                           var1.f_lasti = -1;
                           return var5;
                        } else {
                           var1.setline(2868);
                           var5 = var1.getglobal("_One");
                           var1.f_lasti = -1;
                           return var5;
                        }
                     } else {
                        var1.setline(2869);
                        var5 = var1.getglobal("_Zero");
                        var1.f_lasti = -1;
                        return var5;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject compare_total_mag$106(PyFrame var1, ThreadState var2) {
      var1.setline(2876);
      PyString.fromInterned("Compares self to other using abstract repr., ignoring sign.\n\n        Like compare_total, but with operand's sign ignored and assumed to be 0.\n        ");
      var1.setline(2877);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2879);
      var5 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(2880);
      var5 = var1.getlocal(1).__getattr__("copy_abs").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(2881);
      var5 = var1.getlocal(2).__getattr__("compare_total").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_abs$107(PyFrame var1, ThreadState var2) {
      var1.setline(2884);
      PyString.fromInterned("Returns a copy with the sign set to 0. ");
      var1.setline(2885);
      PyObject var3 = var1.getglobal("_dec_from_triple").__call__(var2, Py.newInteger(0), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp"), var1.getlocal(0).__getattr__("_is_special"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy_negate$108(PyFrame var1, ThreadState var2) {
      var1.setline(2888);
      PyString.fromInterned("Returns a copy with the sign inverted.");
      var1.setline(2889);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
         var1.setline(2890);
         var3 = var1.getglobal("_dec_from_triple").__call__(var2, Py.newInteger(0), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp"), var1.getlocal(0).__getattr__("_is_special"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2892);
         var3 = var1.getglobal("_dec_from_triple").__call__(var2, Py.newInteger(1), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp"), var1.getlocal(0).__getattr__("_is_special"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject copy_sign$109(PyFrame var1, ThreadState var2) {
      var1.setline(2895);
      PyString.fromInterned("Returns self with the sign of other.");
      var1.setline(2896);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2897);
      var5 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(1).__getattr__("_sign"), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp"), var1.getlocal(0).__getattr__("_is_special"));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject exp$110(PyFrame var1, ThreadState var2) {
      var1.setline(2901);
      PyString.fromInterned("Returns e ** self.");
      var1.setline(2903);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2904);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2907);
      var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2908);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(2909);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2912);
         PyObject var8 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         var10000 = var8._eq(Py.newInteger(-1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2913);
            var3 = var1.getglobal("_Zero");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2916);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(2917);
               var3 = var1.getglobal("_One");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2920);
               var8 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
               var10000 = var8._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2921);
                  var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(2927);
                  var8 = var1.getlocal(1).__getattr__("prec");
                  var1.setlocal(3, var8);
                  var4 = null;
                  var1.setline(2928);
                  var8 = var1.getlocal(0).__getattr__("adjusted").__call__(var2);
                  var1.setlocal(4, var8);
                  var4 = null;
                  var1.setline(2935);
                  var8 = var1.getlocal(0).__getattr__("_sign");
                  var10000 = var8._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(4);
                     var10000 = var8._gt(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("Emax")._add(Py.newInteger(1))._mul(Py.newInteger(3)))));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(2937);
                     var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(1).__getattr__("Emax")._add(Py.newInteger(1)));
                     var1.setlocal(2, var8);
                     var4 = null;
                  } else {
                     var1.setline(2938);
                     var8 = var1.getlocal(0).__getattr__("_sign");
                     var10000 = var8._eq(Py.newInteger(1));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var8 = var1.getlocal(4);
                        var10000 = var8._gt(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("Etiny").__call__(var2).__neg__()._add(Py.newInteger(1))._mul(Py.newInteger(3)))));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(2940);
                        var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(1).__getattr__("Etiny").__call__(var2)._sub(Py.newInteger(1)));
                        var1.setlocal(2, var8);
                        var4 = null;
                     } else {
                        var1.setline(2941);
                        var8 = var1.getlocal(0).__getattr__("_sign");
                        var10000 = var8._eq(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var8 = var1.getlocal(4);
                           var10000 = var8._lt(var1.getlocal(3).__neg__());
                           var4 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(2943);
                           var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1")._add(PyString.fromInterned("0")._mul(var1.getlocal(3)._sub(Py.newInteger(1))))._add(PyString.fromInterned("1")), (PyObject)var1.getlocal(3).__neg__());
                           var1.setlocal(2, var8);
                           var4 = null;
                        } else {
                           var1.setline(2944);
                           var8 = var1.getlocal(0).__getattr__("_sign");
                           var10000 = var8._eq(Py.newInteger(1));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var8 = var1.getlocal(4);
                              var10000 = var8._lt(var1.getlocal(3).__neg__()._sub(Py.newInteger(1)));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(2946);
                              var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("9")._mul(var1.getlocal(3)._add(Py.newInteger(1))), (PyObject)var1.getlocal(3).__neg__()._sub(Py.newInteger(1)));
                              var1.setlocal(2, var8);
                              var4 = null;
                           } else {
                              var1.setline(2949);
                              var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                              var1.setlocal(5, var8);
                              var4 = null;
                              var1.setline(2950);
                              PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("int"), var1.getlocal(5).__getattr__("exp")});
                              PyObject[] var5 = Py.unpackSequence(var9, 2);
                              PyObject var6 = var5[0];
                              var1.setlocal(6, var6);
                              var6 = null;
                              var6 = var5[1];
                              var1.setlocal(7, var6);
                              var6 = null;
                              var4 = null;
                              var1.setline(2951);
                              var8 = var1.getlocal(5).__getattr__("sign");
                              var10000 = var8._eq(Py.newInteger(1));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(2952);
                                 var8 = var1.getlocal(6).__neg__();
                                 var1.setlocal(6, var8);
                                 var4 = null;
                              }

                              var1.setline(2957);
                              PyInteger var10 = Py.newInteger(3);
                              var1.setlocal(8, var10);
                              var4 = null;

                              while(true) {
                                 var1.setline(2958);
                                 if (!var1.getglobal("True").__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(2959);
                                 var8 = var1.getglobal("_dexp").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(3)._add(var1.getlocal(8)));
                                 var5 = Py.unpackSequence(var8, 2);
                                 var6 = var5[0];
                                 var1.setlocal(9, var6);
                                 var6 = null;
                                 var6 = var5[1];
                                 var1.setlocal(10, var6);
                                 var6 = null;
                                 var4 = null;
                                 var1.setline(2960);
                                 if (var1.getlocal(9)._mod(Py.newInteger(5)._mul(Py.newInteger(10)._pow(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(9)))._sub(var1.getlocal(3))._sub(Py.newInteger(1))))).__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(2962);
                                 var8 = var1.getlocal(8);
                                 var8 = var8._iadd(Py.newInteger(3));
                                 var1.setlocal(8, var8);
                              }

                              var1.setline(2964);
                              var8 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(9)), (PyObject)var1.getlocal(10));
                              var1.setlocal(2, var8);
                              var4 = null;
                           }
                        }
                     }
                  }

                  var1.setline(2968);
                  var8 = var1.getlocal(1).__getattr__("_shallow_copy").__call__(var2);
                  var1.setlocal(1, var8);
                  var4 = null;
                  var1.setline(2969);
                  var8 = var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_HALF_EVEN"));
                  var1.setlocal(11, var8);
                  var4 = null;
                  var1.setline(2970);
                  var8 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
                  var1.setlocal(2, var8);
                  var4 = null;
                  var1.setline(2971);
                  var8 = var1.getlocal(11);
                  var1.getlocal(1).__setattr__("rounding", var8);
                  var4 = null;
                  var1.setline(2973);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject is_canonical$111(PyFrame var1, ThreadState var2) {
      var1.setline(2980);
      PyString.fromInterned("Return True if self is canonical; otherwise return False.\n\n        Currently, the encoding of a Decimal instance is always\n        canonical, so this method returns True for any Decimal.\n        ");
      var1.setline(2981);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_finite$112(PyFrame var1, ThreadState var2) {
      var1.setline(2988);
      PyString.fromInterned("Return True if self is finite; otherwise return False.\n\n        A Decimal instance is considered finite if it is neither\n        infinite nor a NaN.\n        ");
      var1.setline(2989);
      PyObject var3 = var1.getlocal(0).__getattr__("_is_special").__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_infinite$113(PyFrame var1, ThreadState var2) {
      var1.setline(2992);
      PyString.fromInterned("Return True if self is infinite; otherwise return False.");
      var1.setline(2993);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._eq(PyString.fromInterned("F"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_nan$114(PyFrame var1, ThreadState var2) {
      var1.setline(2996);
      PyString.fromInterned("Return True if self is a qNaN or sNaN; otherwise return False.");
      var1.setline(2997);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("n"), PyString.fromInterned("N")}));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_normal$115(PyFrame var1, ThreadState var2) {
      var1.setline(3000);
      PyString.fromInterned("Return True if self is a normal number; otherwise return False.");
      var1.setline(3001);
      PyObject var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__not__();
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(3002);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3003);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3004);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(3005);
         var4 = var1.getlocal(1).__getattr__("Emin");
         var10000 = var4._le(var1.getlocal(0).__getattr__("adjusted").__call__(var2));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject is_qnan$116(PyFrame var1, ThreadState var2) {
      var1.setline(3008);
      PyString.fromInterned("Return True if self is a quiet NaN; otherwise return False.");
      var1.setline(3009);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._eq(PyString.fromInterned("n"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_signed$117(PyFrame var1, ThreadState var2) {
      var1.setline(3012);
      PyString.fromInterned("Return True if self is negative; otherwise return False.");
      var1.setline(3013);
      PyObject var3 = var1.getlocal(0).__getattr__("_sign");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_snan$118(PyFrame var1, ThreadState var2) {
      var1.setline(3016);
      PyString.fromInterned("Return True if self is a signaling NaN; otherwise return False.");
      var1.setline(3017);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp");
      PyObject var10000 = var3._eq(PyString.fromInterned("N"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_subnormal$119(PyFrame var1, ThreadState var2) {
      var1.setline(3020);
      PyString.fromInterned("Return True if self is subnormal; otherwise return False.");
      var1.setline(3021);
      PyObject var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__not__();
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(3022);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3023);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3024);
            var4 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(3025);
         var4 = var1.getlocal(0).__getattr__("adjusted").__call__(var2);
         var10000 = var4._lt(var1.getlocal(1).__getattr__("Emin"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject is_zero$120(PyFrame var1, ThreadState var2) {
      var1.setline(3028);
      PyString.fromInterned("Return True if self is a zero; otherwise return False.");
      var1.setline(3029);
      PyObject var10000 = var1.getlocal(0).__getattr__("_is_special").__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_int");
         var10000 = var3._eq(PyString.fromInterned("0"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ln_exp_bound$121(PyFrame var1, ThreadState var2) {
      var1.setline(3035);
      PyString.fromInterned("Compute a lower bound for the adjusted exponent of self.ln().\n        In other words, compute r such that self.ln() >= 10**r.  Assumes\n        that self is finite and positive and that self != 1.\n        ");
      var1.setline(3038);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))._sub(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3039);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3041);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)._mul(Py.newInteger(23))._floordiv(Py.newInteger(10))))._sub(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3042);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._le(Py.newInteger(-2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3044);
            var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, Py.newInteger(-1)._sub(var1.getlocal(1))._mul(Py.newInteger(23))._floordiv(Py.newInteger(10))))._sub(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3045);
            var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(3046);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("int"), var1.getlocal(2).__getattr__("exp")});
            PyObject[] var5 = Py.unpackSequence(var7, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(3047);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3049);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(3)._sub(Py.newInteger(10)._pow(var1.getlocal(4).__neg__())));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(3050);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(3));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(3051);
               var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var4 = var1.getlocal(5);
               PyObject var10001 = var4._lt(var1.getlocal(6));
               var4 = null;
               var3 = var10000._sub(var10001);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3053);
               var3 = var1.getlocal(4)._add(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, Py.newInteger(10)._pow(var1.getlocal(4).__neg__())._sub(var1.getlocal(3)))))._sub(Py.newInteger(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject ln$122(PyFrame var1, ThreadState var2) {
      var1.setline(3057);
      PyString.fromInterned("Returns the natural (base e) logarithm of self.");
      var1.setline(3059);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3060);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3063);
      var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3064);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(3065);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3068);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(3069);
            var3 = var1.getglobal("_NegativeInfinity");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3072);
            PyObject var8 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            var10000 = var8._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3073);
               var3 = var1.getglobal("_Infinity");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3076);
               var8 = var1.getlocal(0);
               var10000 = var8._eq(var1.getglobal("_One"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3077);
                  var3 = var1.getglobal("_Zero");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3080);
                  var8 = var1.getlocal(0).__getattr__("_sign");
                  var10000 = var8._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(3081);
                     var3 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("ln of a negative value"));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(3085);
                     var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                     var1.setlocal(3, var8);
                     var4 = null;
                     var1.setline(3086);
                     PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("int"), var1.getlocal(3).__getattr__("exp")});
                     PyObject[] var5 = Py.unpackSequence(var9, 2);
                     PyObject var6 = var5[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var4 = null;
                     var1.setline(3087);
                     var8 = var1.getlocal(1).__getattr__("prec");
                     var1.setlocal(6, var8);
                     var4 = null;
                     var1.setline(3091);
                     var8 = var1.getlocal(6)._sub(var1.getlocal(0).__getattr__("_ln_exp_bound").__call__(var2))._add(Py.newInteger(2));
                     var1.setlocal(7, var8);
                     var4 = null;

                     while(true) {
                        var1.setline(3092);
                        if (!var1.getglobal("True").__nonzero__()) {
                           break;
                        }

                        var1.setline(3093);
                        var8 = var1.getglobal("_dlog").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(7));
                        var1.setlocal(8, var8);
                        var4 = null;
                        var1.setline(3095);
                        if (var1.getlocal(8)._mod(Py.newInteger(5)._mul(Py.newInteger(10)._pow(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(8))))._sub(var1.getlocal(6))._sub(Py.newInteger(1))))).__nonzero__()) {
                           break;
                        }

                        var1.setline(3097);
                        var8 = var1.getlocal(7);
                        var8 = var8._iadd(Py.newInteger(3));
                        var1.setlocal(7, var8);
                     }

                     var1.setline(3098);
                     var10000 = var1.getglobal("_dec_from_triple");
                     PyObject var10002 = var1.getglobal("int");
                     var8 = var1.getlocal(8);
                     PyObject var10004 = var8._lt(Py.newInteger(0));
                     var4 = null;
                     var8 = var10000.__call__(var2, var10002.__call__(var2, var10004), var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(8))), var1.getlocal(7).__neg__());
                     var1.setlocal(2, var8);
                     var4 = null;
                     var1.setline(3100);
                     var8 = var1.getlocal(1).__getattr__("_shallow_copy").__call__(var2);
                     var1.setlocal(1, var8);
                     var4 = null;
                     var1.setline(3101);
                     var8 = var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_HALF_EVEN"));
                     var1.setlocal(9, var8);
                     var4 = null;
                     var1.setline(3102);
                     var8 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
                     var1.setlocal(2, var8);
                     var4 = null;
                     var1.setline(3103);
                     var8 = var1.getlocal(9);
                     var1.getlocal(1).__setattr__("rounding", var8);
                     var4 = null;
                     var1.setline(3104);
                     var3 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject _log10_exp_bound$123(PyFrame var1, ThreadState var2) {
      var1.setline(3110);
      PyString.fromInterned("Compute a lower bound for the adjusted exponent of self.log10().\n        In other words, find r such that self.log10() >= 10**r.\n        Assumes that self is finite and positive and that self != 1.\n        ");
      var1.setline(3118);
      PyObject var3 = var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))._sub(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3119);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3121);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)))._sub(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3122);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._le(Py.newInteger(-2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3124);
            var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, Py.newInteger(-1)._sub(var1.getlocal(1))))._sub(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3125);
            var4 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(3126);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("int"), var1.getlocal(2).__getattr__("exp")});
            PyObject[] var5 = Py.unpackSequence(var7, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(3127);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
            PyObject var10001;
            if (var10000.__nonzero__()) {
               var1.setline(3129);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(3)._sub(Py.newInteger(10)._pow(var1.getlocal(4).__neg__())));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(3130);
               var4 = var1.getglobal("str").__call__(var2, Py.newInteger(231)._mul(var1.getlocal(3)));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(3131);
               var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var4 = var1.getlocal(5);
               var10001 = var4._lt(var1.getlocal(6));
               var4 = null;
               var3 = var10000._sub(var10001)._add(Py.newInteger(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3133);
               var4 = var1.getglobal("str").__call__(var2, Py.newInteger(10)._pow(var1.getlocal(4).__neg__())._sub(var1.getlocal(3)));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(3134);
               var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._add(var1.getlocal(4));
               var4 = var1.getlocal(5);
               var10001 = var4._lt(PyString.fromInterned("231"));
               var4 = null;
               var3 = var10000._sub(var10001)._sub(Py.newInteger(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject log10$124(PyFrame var1, ThreadState var2) {
      var1.setline(3137);
      PyString.fromInterned("Returns the base 10 logarithm of self.");
      var1.setline(3139);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3140);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3143);
      var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3144);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(3145);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3148);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(3149);
            var3 = var1.getglobal("_NegativeInfinity");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3152);
            PyObject var8 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            var10000 = var8._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3153);
               var3 = var1.getglobal("_Infinity");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3156);
               var8 = var1.getlocal(0).__getattr__("_sign");
               var10000 = var8._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3157);
                  var3 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("InvalidOperation"), (PyObject)PyString.fromInterned("log10 of a negative value"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3161);
                  var8 = var1.getlocal(0).__getattr__("_int").__getitem__(Py.newInteger(0));
                  var10000 = var8._eq(PyString.fromInterned("1"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(0).__getattr__("_int").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                     var10000 = var8._eq(PyString.fromInterned("0")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"))._sub(Py.newInteger(1))));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(3163);
                     var8 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))._sub(Py.newInteger(1)));
                     var1.setlocal(2, var8);
                     var4 = null;
                  } else {
                     var1.setline(3166);
                     var8 = var1.getglobal("_WorkRep").__call__(var2, var1.getlocal(0));
                     var1.setlocal(3, var8);
                     var4 = null;
                     var1.setline(3167);
                     PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("int"), var1.getlocal(3).__getattr__("exp")});
                     PyObject[] var5 = Py.unpackSequence(var9, 2);
                     PyObject var6 = var5[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var4 = null;
                     var1.setline(3168);
                     var8 = var1.getlocal(1).__getattr__("prec");
                     var1.setlocal(6, var8);
                     var4 = null;
                     var1.setline(3172);
                     var8 = var1.getlocal(6)._sub(var1.getlocal(0).__getattr__("_log10_exp_bound").__call__(var2))._add(Py.newInteger(2));
                     var1.setlocal(7, var8);
                     var4 = null;

                     while(true) {
                        var1.setline(3173);
                        if (!var1.getglobal("True").__nonzero__()) {
                           break;
                        }

                        var1.setline(3174);
                        var8 = var1.getglobal("_dlog10").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(7));
                        var1.setlocal(8, var8);
                        var4 = null;
                        var1.setline(3176);
                        if (var1.getlocal(8)._mod(Py.newInteger(5)._mul(Py.newInteger(10)._pow(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(8))))._sub(var1.getlocal(6))._sub(Py.newInteger(1))))).__nonzero__()) {
                           break;
                        }

                        var1.setline(3178);
                        var8 = var1.getlocal(7);
                        var8 = var8._iadd(Py.newInteger(3));
                        var1.setlocal(7, var8);
                     }

                     var1.setline(3179);
                     var10000 = var1.getglobal("_dec_from_triple");
                     PyObject var10002 = var1.getglobal("int");
                     var8 = var1.getlocal(8);
                     PyObject var10004 = var8._lt(Py.newInteger(0));
                     var4 = null;
                     var8 = var10000.__call__(var2, var10002.__call__(var2, var10004), var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(8))), var1.getlocal(7).__neg__());
                     var1.setlocal(2, var8);
                     var4 = null;
                  }

                  var1.setline(3181);
                  var8 = var1.getlocal(1).__getattr__("_shallow_copy").__call__(var2);
                  var1.setlocal(1, var8);
                  var4 = null;
                  var1.setline(3182);
                  var8 = var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_HALF_EVEN"));
                  var1.setlocal(9, var8);
                  var4 = null;
                  var1.setline(3183);
                  var8 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
                  var1.setlocal(2, var8);
                  var4 = null;
                  var1.setline(3184);
                  var8 = var1.getlocal(9);
                  var1.getlocal(1).__setattr__("rounding", var8);
                  var4 = null;
                  var1.setline(3185);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject logb$125(PyFrame var1, ThreadState var2) {
      var1.setline(3194);
      PyString.fromInterned(" Returns the exponent of the magnitude of self's MSD.\n\n        The result is the integer which is the exponent of the magnitude\n        of the most significant digit of self (as though it were truncated\n        to a single digit while maintaining the value of that digit and\n        without limiting the resulting exponent).\n        ");
      var1.setline(3196);
      PyObject var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(3197);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(3198);
         var5 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(3200);
         PyObject var6 = var1.getlocal(1);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3201);
            var6 = var1.getglobal("getcontext").__call__(var2);
            var1.setlocal(1, var6);
            var4 = null;
         }

         var1.setline(3204);
         if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
            var1.setline(3205);
            var5 = var1.getglobal("_Infinity");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(3208);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(3209);
               var5 = var1.getlocal(1).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("DivisionByZero"), (PyObject)PyString.fromInterned("logb(0)"), (PyObject)Py.newInteger(1));
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(3214);
               var6 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0).__getattr__("adjusted").__call__(var2));
               var1.setlocal(2, var6);
               var4 = null;
               var1.setline(3215);
               var5 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var5;
            }
         }
      }
   }

   public PyObject _islogical$126(PyFrame var1, ThreadState var2) {
      var1.setline(3223);
      PyString.fromInterned("Return True if self is a logical operand.\n\n        For being logical, it must be a finite number with a sign of 0,\n        an exponent of 0, and a coefficient whose digits must all be\n        either 0 or 1.\n        ");
      var1.setline(3224);
      PyObject var3 = var1.getlocal(0).__getattr__("_sign");
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_exp");
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(3225);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3226);
         PyObject var4 = var1.getlocal(0).__getattr__("_int").__iter__();

         do {
            var1.setline(3226);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(3229);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(1, var5);
            var1.setline(3227);
            PyObject var6 = var1.getlocal(1);
            var10000 = var6._notin(PyString.fromInterned("01"));
            var6 = null;
         } while(!var10000.__nonzero__());

         var1.setline(3228);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _fill_logical$127(PyFrame var1, ThreadState var2) {
      var1.setline(3232);
      PyObject var3 = var1.getlocal(1).__getattr__("prec")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3233);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3234);
         var3 = PyString.fromInterned("0")._mul(var1.getlocal(4))._add(var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(3235);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3236);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(1).__getattr__("prec").__neg__(), (PyObject)null, (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(3237);
      var3 = var1.getlocal(1).__getattr__("prec")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3238);
      var3 = var1.getlocal(4);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3239);
         var3 = PyString.fromInterned("0")._mul(var1.getlocal(4))._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(3240);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3241);
            var3 = var1.getlocal(3).__getslice__(var1.getlocal(1).__getattr__("prec").__neg__(), (PyObject)null, (PyObject)null);
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(3242);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject logical_and$128(PyFrame var1, ThreadState var2) {
      var1.setline(3245);
      PyString.fromInterned("Applies an 'and' operation between self and other's digits.");
      var1.setline(3246);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3247);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3249);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var8, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3251);
      var10000 = var1.getlocal(0).__getattr__("_islogical").__call__(var2).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_islogical").__call__(var2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(3252);
         var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3255);
         PyObject var9 = var1.getlocal(0).__getattr__("_fill_logical").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("_int"), var1.getlocal(1).__getattr__("_int"));
         PyObject[] var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(3258);
         var10000 = PyString.fromInterned("").__getattr__("join");
         PyList var10002 = new PyList();
         var9 = var10002.__getattr__("append");
         var1.setlocal(6, var9);
         var4 = null;
         var1.setline(3258);
         var9 = var1.getglobal("zip").__call__(var2, var1.getlocal(3), var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(3258);
            PyObject var10 = var9.__iternext__();
            if (var10 == null) {
               var1.setline(3258);
               var1.dellocal(6);
               var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(5, var9);
               var4 = null;
               var1.setline(3259);
               var10000 = var1.getglobal("_dec_from_triple");
               PyInteger var12 = Py.newInteger(0);
               Object var10003 = var1.getlocal(5).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
               if (!((PyObject)var10003).__nonzero__()) {
                  var10003 = PyString.fromInterned("0");
               }

               var3 = var10000.__call__((ThreadState)var2, var12, (PyObject)var10003, (PyObject)Py.newInteger(0));
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var11 = Py.unpackSequence(var10, 2);
            PyObject var7 = var11[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var11[1];
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(3258);
            var1.getlocal(6).__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(7))._and(var1.getglobal("int").__call__(var2, var1.getlocal(8)))));
         }
      }
   }

   public PyObject logical_invert$129(PyFrame var1, ThreadState var2) {
      var1.setline(3262);
      PyString.fromInterned("Invert all its digits.");
      var1.setline(3263);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3264);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3265);
      var3 = var1.getlocal(0).__getattr__("logical_xor").__call__(var2, var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1")._mul(var1.getlocal(1).__getattr__("prec")), (PyObject)Py.newInteger(0)), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject logical_or$130(PyFrame var1, ThreadState var2) {
      var1.setline(3269);
      PyString.fromInterned("Applies an 'or' operation between self and other's digits.");
      var1.setline(3270);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3271);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3273);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var8, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3275);
      var10000 = var1.getlocal(0).__getattr__("_islogical").__call__(var2).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_islogical").__call__(var2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(3276);
         var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3279);
         PyObject var9 = var1.getlocal(0).__getattr__("_fill_logical").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("_int"), var1.getlocal(1).__getattr__("_int"));
         PyObject[] var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(3282);
         var10000 = PyString.fromInterned("").__getattr__("join");
         PyList var10002 = new PyList();
         var9 = var10002.__getattr__("append");
         var1.setlocal(6, var9);
         var4 = null;
         var1.setline(3282);
         var9 = var1.getglobal("zip").__call__(var2, var1.getlocal(3), var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(3282);
            PyObject var10 = var9.__iternext__();
            if (var10 == null) {
               var1.setline(3282);
               var1.dellocal(6);
               var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(5, var9);
               var4 = null;
               var1.setline(3283);
               var10000 = var1.getglobal("_dec_from_triple");
               PyInteger var12 = Py.newInteger(0);
               Object var10003 = var1.getlocal(5).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
               if (!((PyObject)var10003).__nonzero__()) {
                  var10003 = PyString.fromInterned("0");
               }

               var3 = var10000.__call__((ThreadState)var2, var12, (PyObject)var10003, (PyObject)Py.newInteger(0));
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var11 = Py.unpackSequence(var10, 2);
            PyObject var7 = var11[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var11[1];
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(3282);
            var1.getlocal(6).__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(7))._or(var1.getglobal("int").__call__(var2, var1.getlocal(8)))));
         }
      }
   }

   public PyObject logical_xor$131(PyFrame var1, ThreadState var2) {
      var1.setline(3286);
      PyString.fromInterned("Applies an 'xor' operation between self and other's digits.");
      var1.setline(3287);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3288);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3290);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var8, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3292);
      var10000 = var1.getlocal(0).__getattr__("_islogical").__call__(var2).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_islogical").__call__(var2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(3293);
         var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3296);
         PyObject var9 = var1.getlocal(0).__getattr__("_fill_logical").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("_int"), var1.getlocal(1).__getattr__("_int"));
         PyObject[] var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(3299);
         var10000 = PyString.fromInterned("").__getattr__("join");
         PyList var10002 = new PyList();
         var9 = var10002.__getattr__("append");
         var1.setlocal(6, var9);
         var4 = null;
         var1.setline(3299);
         var9 = var1.getglobal("zip").__call__(var2, var1.getlocal(3), var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(3299);
            PyObject var10 = var9.__iternext__();
            if (var10 == null) {
               var1.setline(3299);
               var1.dellocal(6);
               var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(5, var9);
               var4 = null;
               var1.setline(3300);
               var10000 = var1.getglobal("_dec_from_triple");
               PyInteger var12 = Py.newInteger(0);
               Object var10003 = var1.getlocal(5).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
               if (!((PyObject)var10003).__nonzero__()) {
                  var10003 = PyString.fromInterned("0");
               }

               var3 = var10000.__call__((ThreadState)var2, var12, (PyObject)var10003, (PyObject)Py.newInteger(0));
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var11 = Py.unpackSequence(var10, 2);
            PyObject var7 = var11[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var11[1];
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(3299);
            var1.getlocal(6).__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(7))._xor(var1.getglobal("int").__call__(var2, var1.getlocal(8)))));
         }
      }
   }

   public PyObject max_mag$132(PyFrame var1, ThreadState var2) {
      var1.setline(3303);
      PyString.fromInterned("Compares the values numerically with their sign ignored.");
      var1.setline(3304);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(3306);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3307);
         var5 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(3309);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(3312);
         var5 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(3313);
         var5 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(3314);
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(3315);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._eq(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(3316);
               var5 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(3317);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(4);
               var10000 = var6._eq(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(3318);
               var5 = var1.getlocal(1).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(3319);
            var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(3321);
      var6 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2).__getattr__("_cmp").__call__(var2, var1.getlocal(1).__getattr__("copy_abs").__call__(var2));
      var1.setlocal(5, var6);
      var4 = null;
      var1.setline(3322);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3323);
         var6 = var1.getlocal(0).__getattr__("compare_total").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var6);
         var4 = null;
      }

      var1.setline(3325);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(-1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3326);
         var6 = var1.getlocal(1);
         var1.setlocal(6, var6);
         var4 = null;
      } else {
         var1.setline(3328);
         var6 = var1.getlocal(0);
         var1.setlocal(6, var6);
         var4 = null;
      }

      var1.setline(3330);
      var5 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject min_mag$133(PyFrame var1, ThreadState var2) {
      var1.setline(3333);
      PyString.fromInterned("Compares the values numerically with their sign ignored.");
      var1.setline(3334);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(3336);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3337);
         var5 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(3339);
      var10000 = var1.getlocal(0).__getattr__("_is_special");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("_is_special");
      }

      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(3342);
         var5 = var1.getlocal(0).__getattr__("_isnan").__call__(var2);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(3343);
         var5 = var1.getlocal(1).__getattr__("_isnan").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(3344);
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(3345);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._eq(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(3346);
               var5 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(3347);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(4);
               var10000 = var6._eq(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(3348);
               var5 = var1.getlocal(1).__getattr__("_fix").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(3349);
            var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(3351);
      var6 = var1.getlocal(0).__getattr__("copy_abs").__call__(var2).__getattr__("_cmp").__call__(var2, var1.getlocal(1).__getattr__("copy_abs").__call__(var2));
      var1.setlocal(5, var6);
      var4 = null;
      var1.setline(3352);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3353);
         var6 = var1.getlocal(0).__getattr__("compare_total").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var6);
         var4 = null;
      }

      var1.setline(3355);
      var6 = var1.getlocal(5);
      var10000 = var6._eq(Py.newInteger(-1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3356);
         var6 = var1.getlocal(0);
         var1.setlocal(6, var6);
         var4 = null;
      } else {
         var1.setline(3358);
         var6 = var1.getlocal(1);
         var1.setlocal(6, var6);
         var4 = null;
      }

      var1.setline(3360);
      var5 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject next_minus$134(PyFrame var1, ThreadState var2) {
      var1.setline(3363);
      PyString.fromInterned("Returns the largest representable number smaller than itself.");
      var1.setline(3364);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3365);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3367);
      var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3368);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(3369);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3371);
         PyObject var6 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         var10000 = var6._eq(Py.newInteger(-1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3372);
            var3 = var1.getglobal("_NegativeInfinity");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3373);
            var6 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            var10000 = var6._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3374);
               var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("9")._mul(var1.getlocal(1).__getattr__("prec")), (PyObject)var1.getlocal(1).__getattr__("Etop").__call__(var2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3376);
               var6 = var1.getlocal(1).__getattr__("copy").__call__(var2);
               var1.setlocal(1, var6);
               var4 = null;
               var1.setline(3377);
               var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_FLOOR"));
               var1.setline(3378);
               var1.getlocal(1).__getattr__("_ignore_all_flags").__call__(var2);
               var1.setline(3379);
               var6 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var6);
               var4 = null;
               var1.setline(3380);
               var6 = var1.getlocal(3);
               var10000 = var6._ne(var1.getlocal(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3381);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3382);
                  var3 = var1.getlocal(0).__getattr__("__sub__").__call__(var2, var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(1).__getattr__("Etiny").__call__(var2)._sub(Py.newInteger(1))), var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject next_plus$135(PyFrame var1, ThreadState var2) {
      var1.setline(3386);
      PyString.fromInterned("Returns the smallest representable number larger than itself.");
      var1.setline(3387);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3388);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3390);
      var10000 = var1.getlocal(0).__getattr__("_check_nans");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3391);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(3392);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3394);
         PyObject var6 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
         var10000 = var6._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3395);
            var3 = var1.getglobal("_Infinity");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3396);
            var6 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            var10000 = var6._eq(Py.newInteger(-1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3397);
               var3 = var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)PyString.fromInterned("9")._mul(var1.getlocal(1).__getattr__("prec")), (PyObject)var1.getlocal(1).__getattr__("Etop").__call__(var2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3399);
               var6 = var1.getlocal(1).__getattr__("copy").__call__(var2);
               var1.setlocal(1, var6);
               var4 = null;
               var1.setline(3400);
               var1.getlocal(1).__getattr__("_set_rounding").__call__(var2, var1.getglobal("ROUND_CEILING"));
               var1.setline(3401);
               var1.getlocal(1).__getattr__("_ignore_all_flags").__call__(var2);
               var1.setline(3402);
               var6 = var1.getlocal(0).__getattr__("_fix").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var6);
               var4 = null;
               var1.setline(3403);
               var6 = var1.getlocal(3);
               var10000 = var6._ne(var1.getlocal(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3404);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3405);
                  var3 = var1.getlocal(0).__getattr__("__add__").__call__(var2, var1.getglobal("_dec_from_triple").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("1"), (PyObject)var1.getlocal(1).__getattr__("Etiny").__call__(var2)._sub(Py.newInteger(1))), var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject next_toward$136(PyFrame var1, ThreadState var2) {
      var1.setline(3416);
      PyString.fromInterned("Returns the number closest to self, in the direction towards other.\n\n        The result is the closest representable number to self\n        (excluding self) that is in the direction towards other,\n        unless both have the same value.  If the two operands are\n        numerically equal, then the result is a copy of self with the\n        sign set to be the same as the sign of other.\n        ");
      var1.setline(3417);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(3419);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3420);
         var5 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(3422);
      var5 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(3423);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(3424);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(3426);
         PyObject var6 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(3427);
         var6 = var1.getlocal(4);
         var10000 = var6._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3428);
            var5 = var1.getlocal(0).__getattr__("copy_sign").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(3430);
            var6 = var1.getlocal(4);
            var10000 = var6._eq(Py.newInteger(-1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3431);
               var6 = var1.getlocal(0).__getattr__("next_plus").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var6);
               var4 = null;
            } else {
               var1.setline(3433);
               var6 = var1.getlocal(0).__getattr__("next_minus").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var6);
               var4 = null;
            }

            var1.setline(3436);
            if (var1.getlocal(3).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
               var1.setline(3437);
               var1.getlocal(2).__getattr__("_raise_error").__call__((ThreadState)var2, var1.getglobal("Overflow"), (PyObject)PyString.fromInterned("Infinite result from next_toward"), (PyObject)var1.getlocal(3).__getattr__("_sign"));
               var1.setline(3440);
               var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
               var1.setline(3441);
               var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
            } else {
               var1.setline(3442);
               var6 = var1.getlocal(3).__getattr__("adjusted").__call__(var2);
               var10000 = var6._lt(var1.getlocal(2).__getattr__("Emin"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3443);
                  var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Underflow"));
                  var1.setline(3444);
                  var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Subnormal"));
                  var1.setline(3445);
                  var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Inexact"));
                  var1.setline(3446);
                  var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Rounded"));
                  var1.setline(3449);
                  if (var1.getlocal(3).__not__().__nonzero__()) {
                     var1.setline(3450);
                     var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("Clamped"));
                  }
               }
            }

            var1.setline(3452);
            var5 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject number_class$137(PyFrame var1, ThreadState var2) {
      var1.setline(3468);
      PyString.fromInterned("Returns an indication of the class of self.\n\n        The class is one of the following strings:\n          sNaN\n          NaN\n          -Infinity\n          -Normal\n          -Subnormal\n          -Zero\n          +Zero\n          +Subnormal\n          +Normal\n          +Infinity\n        ");
      var1.setline(3469);
      PyString var3;
      if (var1.getlocal(0).__getattr__("is_snan").__call__(var2).__nonzero__()) {
         var1.setline(3470);
         var3 = PyString.fromInterned("sNaN");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3471);
         if (var1.getlocal(0).__getattr__("is_qnan").__call__(var2).__nonzero__()) {
            var1.setline(3472);
            var3 = PyString.fromInterned("NaN");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3473);
            PyObject var4 = var1.getlocal(0).__getattr__("_isinfinity").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(3474);
            var4 = var1.getlocal(2);
            PyObject var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3475);
               var3 = PyString.fromInterned("+Infinity");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3476);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(Py.newInteger(-1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3477);
                  var3 = PyString.fromInterned("-Infinity");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3478);
                  if (var1.getlocal(0).__getattr__("is_zero").__call__(var2).__nonzero__()) {
                     var1.setline(3479);
                     if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
                        var1.setline(3480);
                        var3 = PyString.fromInterned("-Zero");
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(3482);
                        var3 = PyString.fromInterned("+Zero");
                        var1.f_lasti = -1;
                        return var3;
                     }
                  } else {
                     var1.setline(3483);
                     var4 = var1.getlocal(1);
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(3484);
                        var4 = var1.getglobal("getcontext").__call__(var2);
                        var1.setlocal(1, var4);
                        var4 = null;
                     }

                     var1.setline(3485);
                     var10000 = var1.getlocal(0).__getattr__("is_subnormal");
                     PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
                     String[] var5 = new String[]{"context"};
                     var10000 = var10000.__call__(var2, var6, var5);
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(3486);
                        if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
                           var1.setline(3487);
                           var3 = PyString.fromInterned("-Subnormal");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(3489);
                           var3 = PyString.fromInterned("+Subnormal");
                           var1.f_lasti = -1;
                           return var3;
                        }
                     } else {
                        var1.setline(3491);
                        if (var1.getlocal(0).__getattr__("_sign").__nonzero__()) {
                           var1.setline(3492);
                           var3 = PyString.fromInterned("-Normal");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(3494);
                           var3 = PyString.fromInterned("+Normal");
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject radix$138(PyFrame var1, ThreadState var2) {
      var1.setline(3497);
      PyString.fromInterned("Just returns 10, as this is Decimal, :)");
      var1.setline(3498);
      PyObject var3 = var1.getglobal("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(10));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rotate$139(PyFrame var1, ThreadState var2) {
      var1.setline(3501);
      PyString.fromInterned("Returns a rotated copy of self, value-of-other times.");
      var1.setline(3502);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3503);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3505);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3507);
      var3 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3508);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(3509);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3511);
         PyObject var7 = var1.getlocal(1).__getattr__("_exp");
         var10000 = var7._ne(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3512);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3513);
            var7 = var1.getlocal(2).__getattr__("prec").__neg__();
            PyObject var10001 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var10000 = var7;
            var7 = var10001;
            PyObject var5;
            if ((var5 = var10000._le(var10001)).__nonzero__()) {
               var5 = var7._le(var1.getlocal(2).__getattr__("prec"));
            }

            var4 = null;
            if (var5.__not__().__nonzero__()) {
               var1.setline(3514);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3516);
               if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(3517);
                  var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3520);
                  var7 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
                  var1.setlocal(4, var7);
                  var4 = null;
                  var1.setline(3521);
                  var7 = var1.getlocal(0).__getattr__("_int");
                  var1.setlocal(5, var7);
                  var4 = null;
                  var1.setline(3522);
                  var7 = var1.getlocal(2).__getattr__("prec")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(3523);
                  var7 = var1.getlocal(6);
                  var10000 = var7._gt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(3524);
                     var7 = PyString.fromInterned("0")._mul(var1.getlocal(6))._add(var1.getlocal(5));
                     var1.setlocal(5, var7);
                     var4 = null;
                  } else {
                     var1.setline(3525);
                     var7 = var1.getlocal(6);
                     var10000 = var7._lt(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(3526);
                        var7 = var1.getlocal(5).__getslice__(var1.getlocal(6).__neg__(), (PyObject)null, (PyObject)null);
                        var1.setlocal(5, var7);
                        var4 = null;
                     }
                  }

                  var1.setline(3529);
                  var7 = var1.getlocal(5).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null)._add(var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null));
                  var1.setlocal(7, var7);
                  var4 = null;
                  var1.setline(3530);
                  var10000 = var1.getglobal("_dec_from_triple");
                  PyObject var10002 = var1.getlocal(0).__getattr__("_sign");
                  Object var10003 = var1.getlocal(7).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
                  if (!((PyObject)var10003).__nonzero__()) {
                     var10003 = PyString.fromInterned("0");
                  }

                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)var1.getlocal(0).__getattr__("_exp"));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject scaleb$140(PyFrame var1, ThreadState var2) {
      var1.setline(3534);
      PyString.fromInterned("Returns self operand after adding the second value to its exp.");
      var1.setline(3535);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3536);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3538);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3540);
      var3 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3541);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(3542);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3544);
         PyObject var7 = var1.getlocal(1).__getattr__("_exp");
         var10000 = var7._ne(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3545);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3546);
            var7 = Py.newInteger(-2)._mul(var1.getlocal(2).__getattr__("Emax")._add(var1.getlocal(2).__getattr__("prec")));
            var1.setlocal(4, var7);
            var4 = null;
            var1.setline(3547);
            var7 = Py.newInteger(2)._mul(var1.getlocal(2).__getattr__("Emax")._add(var1.getlocal(2).__getattr__("prec")));
            var1.setlocal(5, var7);
            var4 = null;
            var1.setline(3548);
            var7 = var1.getlocal(4);
            PyObject var10001 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var10000 = var7;
            var7 = var10001;
            PyObject var5;
            if ((var5 = var10000._le(var10001)).__nonzero__()) {
               var5 = var7._le(var1.getlocal(5));
            }

            var4 = null;
            if (var5.__not__().__nonzero__()) {
               var1.setline(3549);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3551);
               if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(3552);
                  var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3554);
                  var7 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("int").__call__(var2, var1.getlocal(1))));
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(3555);
                  var7 = var1.getlocal(6).__getattr__("_fix").__call__(var2, var1.getlocal(2));
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(3556);
                  var3 = var1.getlocal(6);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject shift$141(PyFrame var1, ThreadState var2) {
      var1.setline(3559);
      PyString.fromInterned("Returns a shifted copy of self, value-of-other times.");
      var1.setline(3560);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3561);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3563);
      var10000 = var1.getglobal("_convert_other");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3565);
      var3 = var1.getlocal(0).__getattr__("_check_nans").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3566);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(3567);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3569);
         PyObject var7 = var1.getlocal(1).__getattr__("_exp");
         var10000 = var7._ne(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3570);
            var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3571);
            var7 = var1.getlocal(2).__getattr__("prec").__neg__();
            PyObject var10001 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var10000 = var7;
            var7 = var10001;
            PyObject var5;
            if ((var5 = var10000._le(var10001)).__nonzero__()) {
               var5 = var7._le(var1.getlocal(2).__getattr__("prec"));
            }

            var4 = null;
            if (var5.__not__().__nonzero__()) {
               var1.setline(3572);
               var3 = var1.getlocal(2).__getattr__("_raise_error").__call__(var2, var1.getglobal("InvalidOperation"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3574);
               if (var1.getlocal(0).__getattr__("_isinfinity").__call__(var2).__nonzero__()) {
                  var1.setline(3575);
                  var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(3578);
                  var7 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
                  var1.setlocal(4, var7);
                  var4 = null;
                  var1.setline(3579);
                  var7 = var1.getlocal(0).__getattr__("_int");
                  var1.setlocal(5, var7);
                  var4 = null;
                  var1.setline(3580);
                  var7 = var1.getlocal(2).__getattr__("prec")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(3581);
                  var7 = var1.getlocal(6);
                  var10000 = var7._gt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(3582);
                     var7 = PyString.fromInterned("0")._mul(var1.getlocal(6))._add(var1.getlocal(5));
                     var1.setlocal(5, var7);
                     var4 = null;
                  } else {
                     var1.setline(3583);
                     var7 = var1.getlocal(6);
                     var10000 = var7._lt(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(3584);
                        var7 = var1.getlocal(5).__getslice__(var1.getlocal(6).__neg__(), (PyObject)null, (PyObject)null);
                        var1.setlocal(5, var7);
                        var4 = null;
                     }
                  }

                  var1.setline(3587);
                  var7 = var1.getlocal(4);
                  var10000 = var7._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(3588);
                     var7 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
                     var1.setlocal(7, var7);
                     var4 = null;
                  } else {
                     var1.setline(3590);
                     var7 = var1.getlocal(5)._add(PyString.fromInterned("0")._mul(var1.getlocal(4)));
                     var1.setlocal(7, var7);
                     var4 = null;
                     var1.setline(3591);
                     var7 = var1.getlocal(7).__getslice__(var1.getlocal(2).__getattr__("prec").__neg__(), (PyObject)null, (PyObject)null);
                     var1.setlocal(7, var7);
                     var4 = null;
                  }

                  var1.setline(3593);
                  var10000 = var1.getglobal("_dec_from_triple");
                  PyObject var10002 = var1.getlocal(0).__getattr__("_sign");
                  Object var10003 = var1.getlocal(7).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
                  if (!((PyObject)var10003).__nonzero__()) {
                     var10003 = PyString.fromInterned("0");
                  }

                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)var1.getlocal(0).__getattr__("_exp"));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __reduce__$142(PyFrame var1, ThreadState var2) {
      var1.setline(3598);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0))})});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __copy__$143(PyFrame var1, ThreadState var2) {
      var1.setline(3601);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._is(var1.getglobal("Decimal"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3602);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3603);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __deepcopy__$144(PyFrame var1, ThreadState var2) {
      var1.setline(3606);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._is(var1.getglobal("Decimal"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3607);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3608);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __format__$145(PyFrame var1, ThreadState var2) {
      var1.setline(3620);
      PyString.fromInterned("Format a Decimal instance according to the given specifier.\n\n        The specifier should be a standard format specifier, with the\n        form described in PEP 3101.  Formatting types 'e', 'E', 'f',\n        'F', 'g', 'G', 'n' and '%' are supported.  If the formatting\n        type is omitted it defaults to 'g' or 'G', depending on the\n        value of context.capitals.\n        ");
      var1.setline(3627);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3628);
         var3 = var1.getglobal("getcontext").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(3630);
      var10000 = var1.getglobal("_parse_format_specifier");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(3)};
      String[] var4 = new String[]{"_localeconv"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3633);
      if (var1.getlocal(0).__getattr__("_is_special").__nonzero__()) {
         var1.setline(3634);
         var3 = var1.getglobal("_format_sign").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(3635);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("copy_abs").__call__(var2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(3636);
         var3 = var1.getglobal("_format_align").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3639);
         PyObject var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3640);
            var6 = (new PyList(new PyObject[]{PyString.fromInterned("g"), PyString.fromInterned("G")})).__getitem__(var1.getlocal(2).__getattr__("capitals"));
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("type"), var6);
            var4 = null;
         }

         var1.setline(3643);
         var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
         var10000 = var6._eq(PyString.fromInterned("%"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3644);
            var6 = var1.getglobal("_dec_from_triple").__call__(var2, var1.getlocal(0).__getattr__("_sign"), var1.getlocal(0).__getattr__("_int"), var1.getlocal(0).__getattr__("_exp")._add(Py.newInteger(2)));
            var1.setlocal(0, var6);
            var4 = null;
         }

         var1.setline(3647);
         var6 = var1.getlocal(2).__getattr__("rounding");
         var1.setlocal(7, var6);
         var4 = null;
         var1.setline(3648);
         var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("precision"));
         var1.setlocal(8, var6);
         var4 = null;
         var1.setline(3649);
         var6 = var1.getlocal(8);
         var10000 = var6._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3650);
            var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
            var10000 = var6._in(PyString.fromInterned("eE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3651);
               var6 = var1.getlocal(0).__getattr__("_round").__call__(var2, var1.getlocal(8)._add(Py.newInteger(1)), var1.getlocal(7));
               var1.setlocal(0, var6);
               var4 = null;
            } else {
               var1.setline(3652);
               var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
               var10000 = var6._in(PyString.fromInterned("fF%"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3653);
                  var6 = var1.getlocal(0).__getattr__("_rescale").__call__(var2, var1.getlocal(8).__neg__(), var1.getlocal(7));
                  var1.setlocal(0, var6);
                  var4 = null;
               } else {
                  var1.setline(3654);
                  var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
                  var10000 = var6._in(PyString.fromInterned("gG"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int"));
                     var10000 = var6._gt(var1.getlocal(8));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(3655);
                     var6 = var1.getlocal(0).__getattr__("_round").__call__(var2, var1.getlocal(8), var1.getlocal(7));
                     var1.setlocal(0, var6);
                     var4 = null;
                  }
               }
            }
         }

         var1.setline(3658);
         var10000 = var1.getlocal(0).__not__();
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(0).__getattr__("_exp");
            var10000 = var6._gt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
               var10000 = var6._in(PyString.fromInterned("fF%"));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(3659);
            var6 = var1.getlocal(0).__getattr__("_rescale").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(7));
            var1.setlocal(0, var6);
            var4 = null;
         }

         var1.setline(3662);
         var6 = var1.getlocal(0).__getattr__("_exp")._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")));
         var1.setlocal(9, var6);
         var4 = null;
         var1.setline(3663);
         var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
         var10000 = var6._in(PyString.fromInterned("eE"));
         var4 = null;
         PyInteger var7;
         if (var10000.__nonzero__()) {
            var1.setline(3664);
            var10000 = var1.getlocal(0).__not__();
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(8);
               var10000 = var6._isnot(var1.getglobal("None"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(3665);
               var6 = Py.newInteger(1)._sub(var1.getlocal(8));
               var1.setlocal(10, var6);
               var4 = null;
            } else {
               var1.setline(3667);
               var7 = Py.newInteger(1);
               var1.setlocal(10, var7);
               var4 = null;
            }
         } else {
            var1.setline(3668);
            var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
            var10000 = var6._in(PyString.fromInterned("fF%"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3669);
               var6 = var1.getlocal(9);
               var1.setlocal(10, var6);
               var4 = null;
            } else {
               var1.setline(3670);
               var6 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
               var10000 = var6._in(PyString.fromInterned("gG"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(3671);
                  var6 = var1.getlocal(0).__getattr__("_exp");
                  var10000 = var6._le(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(9);
                     var10000 = var6._gt(Py.newInteger(-6));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(3672);
                     var6 = var1.getlocal(9);
                     var1.setlocal(10, var6);
                     var4 = null;
                  } else {
                     var1.setline(3674);
                     var7 = Py.newInteger(1);
                     var1.setlocal(10, var7);
                     var4 = null;
                  }
               }
            }
         }

         var1.setline(3677);
         var6 = var1.getlocal(10);
         var10000 = var6._lt(Py.newInteger(0));
         var4 = null;
         PyString var8;
         if (var10000.__nonzero__()) {
            var1.setline(3678);
            var8 = PyString.fromInterned("0");
            var1.setlocal(11, var8);
            var4 = null;
            var1.setline(3679);
            var6 = PyString.fromInterned("0")._mul(var1.getlocal(10).__neg__())._add(var1.getlocal(0).__getattr__("_int"));
            var1.setlocal(12, var6);
            var4 = null;
         } else {
            var1.setline(3680);
            var6 = var1.getlocal(10);
            var10000 = var6._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3681);
               var6 = var1.getlocal(0).__getattr__("_int")._add(PyString.fromInterned("0")._mul(var1.getlocal(10)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_int")))));
               var1.setlocal(11, var6);
               var4 = null;
               var1.setline(3682);
               var8 = PyString.fromInterned("");
               var1.setlocal(12, var8);
               var4 = null;
            } else {
               var1.setline(3684);
               Object var11 = var1.getlocal(0).__getattr__("_int").__getslice__((PyObject)null, var1.getlocal(10), (PyObject)null);
               if (!((PyObject)var11).__nonzero__()) {
                  var11 = PyString.fromInterned("0");
               }

               Object var9 = var11;
               var1.setlocal(11, (PyObject)var9);
               var4 = null;
               var1.setline(3685);
               var6 = var1.getlocal(0).__getattr__("_int").__getslice__(var1.getlocal(10), (PyObject)null, (PyObject)null);
               var1.setlocal(12, var6);
               var4 = null;
            }
         }

         var1.setline(3686);
         var6 = var1.getlocal(9)._sub(var1.getlocal(10));
         var1.setlocal(13, var6);
         var4 = null;
         var1.setline(3690);
         var10000 = var1.getglobal("_format_number");
         PyObject[] var10 = new PyObject[]{var1.getlocal(0).__getattr__("_sign"), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(4)};
         var3 = var10000.__call__(var2, var10);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __tojava__$146(PyFrame var1, ThreadState var2) {
      var1.setline(3694);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("Float"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3695);
         var3 = var1.getglobal("Float").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3696);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("Double"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(3697);
            var3 = var1.getglobal("Double").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3698);
            var4 = var1.getlocal(1);
            var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("BigDecimal"), var1.getglobal("Object")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(3699);
               var3 = var1.getglobal("BigDecimal").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(3700);
               var3 = var1.getglobal("Py").__getattr__("NoConversion");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _dec_from_triple$147(PyFrame var1, ThreadState var2) {
      var1.setline(3708);
      PyString.fromInterned("Create a decimal instance directly, without any validation,\n    normalization (e.g. removal of leading zeros) or argument\n    conversion.\n\n    This function is for *internal use only*.\n    ");
      var1.setline(3710);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("Decimal"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3711);
      var3 = var1.getlocal(0);
      var1.getlocal(4).__setattr__("_sign", var3);
      var3 = null;
      var1.setline(3712);
      var3 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("_int", var3);
      var3 = null;
      var1.setline(3713);
      var3 = var1.getlocal(2);
      var1.getlocal(4).__setattr__("_exp", var3);
      var3 = null;
      var1.setline(3714);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("_is_special", var3);
      var3 = null;
      var1.setline(3716);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ContextManager$148(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Context manager class to support localcontext().\n\n      Sets a copy of the supplied context in __enter__() and restores\n      the previous decimal context in __exit__()\n    "));
      var1.setline(3743);
      PyString.fromInterned("Context manager class to support localcontext().\n\n      Sets a copy of the supplied context in __enter__() and restores\n      the previous decimal context in __exit__()\n    ");
      var1.setline(3744);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$149, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(3746);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$150, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(3750);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$151, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$149(PyFrame var1, ThreadState var2) {
      var1.setline(3745);
      PyObject var3 = var1.getlocal(1).__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("new_context", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$150(PyFrame var1, ThreadState var2) {
      var1.setline(3747);
      PyObject var3 = var1.getglobal("getcontext").__call__(var2);
      var1.getlocal(0).__setattr__("saved_context", var3);
      var3 = null;
      var1.setline(3748);
      var1.getglobal("setcontext").__call__(var2, var1.getlocal(0).__getattr__("new_context"));
      var1.setline(3749);
      var3 = var1.getlocal(0).__getattr__("new_context");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$151(PyFrame var1, ThreadState var2) {
      var1.setline(3751);
      var1.getglobal("setcontext").__call__(var2, var1.getlocal(0).__getattr__("saved_context"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Context$152(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Contains the context for a Decimal instance.\n\n    Contains:\n    prec - precision (for use in rounding, division, square roots..)\n    rounding - rounding type (how you round)\n    traps - If traps[exception] = 1, then the exception is\n                    raised when it is caused.  Otherwise, a value is\n                    substituted in.\n    flags  - When an exception is caused, flags[exception] is set.\n             (Whether or not the trap_enabler is set)\n             Should be reset by user of Decimal instance.\n    Emin -   Minimum exponent\n    Emax -   Maximum exponent\n    capitals -      If 1, 1*10^1 is printed as 1E+1.\n                    If 0, printed as 1e1\n    _clamp - If 1, change exponents if too high (Default 0)\n    "));
      var1.setline(3770);
      PyString.fromInterned("Contains the context for a Decimal instance.\n\n    Contains:\n    prec - precision (for use in rounding, division, square roots..)\n    rounding - rounding type (how you round)\n    traps - If traps[exception] = 1, then the exception is\n                    raised when it is caused.  Otherwise, a value is\n                    substituted in.\n    flags  - When an exception is caused, flags[exception] is set.\n             (Whether or not the trap_enabler is set)\n             Should be reset by user of Decimal instance.\n    Emin -   Minimum exponent\n    Emax -   Maximum exponent\n    capitals -      If 1, 1*10^1 is printed as 1E+1.\n                    If 0, printed as 1e1\n    _clamp - If 1, change exponents if too high (Default 0)\n    ");
      var1.setline(3772);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$153, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(3810);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$156, PyString.fromInterned("Show the current context."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(3822);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_flags$157, PyString.fromInterned("Reset all flags to zero"));
      var1.setlocal("clear_flags", var4);
      var3 = null;
      var1.setline(3827);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _shallow_copy$158, PyString.fromInterned("Returns a shallow copy from self."));
      var1.setlocal("_shallow_copy", var4);
      var3 = null;
      var1.setline(3834);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$159, PyString.fromInterned("Returns a deep copy from self."));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(3840);
      PyObject var5 = var1.getname("copy");
      var1.setlocal("__copy__", var5);
      var3 = null;
      var1.setline(3842);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _raise_error$160, PyString.fromInterned("Handles an error\n\n        If the flag is in _ignored_flags, returns the default response.\n        Otherwise, it sets the flag, then, if the corresponding\n        trap_enabler is set, it reraises the exception.  Otherwise, it returns\n        the default value after setting the flag.\n        "));
      var1.setlocal("_raise_error", var4);
      var3 = null;
      var1.setline(3864);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ignore_all_flags$161, PyString.fromInterned("Ignore all flags, if they are raised"));
      var1.setlocal("_ignore_all_flags", var4);
      var3 = null;
      var1.setline(3868);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ignore_flags$162, PyString.fromInterned("Ignore the flags, if they are raised"));
      var1.setlocal("_ignore_flags", var4);
      var3 = null;
      var1.setline(3875);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _regard_flags$163, PyString.fromInterned("Stop ignoring the flags, if they are raised"));
      var1.setlocal("_regard_flags", var4);
      var3 = null;
      var1.setline(3883);
      var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(3885);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, Etiny$164, PyString.fromInterned("Returns Etiny (= Emin - prec + 1)"));
      var1.setlocal("Etiny", var4);
      var3 = null;
      var1.setline(3889);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, Etop$165, PyString.fromInterned("Returns maximum exponent (= Emax - prec + 1)"));
      var1.setlocal("Etop", var4);
      var3 = null;
      var1.setline(3893);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_rounding$166, PyString.fromInterned("Sets the rounding type.\n\n        Sets the rounding type, and returns the current (previous)\n        rounding type.  Often used like:\n\n        context = context.copy()\n        # so you don't change the calling context\n        # if an error occurs in the middle.\n        rounding = context._set_rounding(ROUND_UP)\n        val = self.__sub__(other, context=context)\n        context._set_rounding(rounding)\n\n        This will make it round up for that operation.\n        "));
      var1.setlocal("_set_rounding", var4);
      var3 = null;
      var1.setline(3912);
      var3 = new PyObject[]{PyString.fromInterned("0")};
      var4 = new PyFunction(var1.f_globals, var3, create_decimal$167, PyString.fromInterned("Creates a new Decimal instance but using self as context.\n\n        This method implements the to-number operation of the\n        IBM Decimal specification."));
      var1.setlocal("create_decimal", var4);
      var3 = null;
      var1.setline(3929);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, create_decimal_from_float$168, PyString.fromInterned("Creates a new Decimal instance from a float but rounding using self\n        as the context.\n\n        >>> context = Context(prec=5, rounding=ROUND_DOWN)\n        >>> context.create_decimal_from_float(3.1415926535897932)\n        Decimal('3.1415')\n        >>> context = Context(prec=5, traps=[Inexact])\n        >>> context.create_decimal_from_float(3.1415926535897932)\n        Traceback (most recent call last):\n            ...\n        Inexact: None\n\n        "));
      var1.setlocal("create_decimal_from_float", var4);
      var3 = null;
      var1.setline(3947);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, abs$169, PyString.fromInterned("Returns the absolute value of the operand.\n\n        If the operand is negative, the result is the same as using the minus\n        operation on the operand.  Otherwise, the result is the same as using\n        the plus operation on the operand.\n\n        >>> ExtendedContext.abs(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.abs(Decimal('-100'))\n        Decimal('100')\n        >>> ExtendedContext.abs(Decimal('101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.abs(Decimal('-101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.abs(-1)\n        Decimal('1')\n        "));
      var1.setlocal("abs", var4);
      var3 = null;
      var1.setline(3968);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$170, PyString.fromInterned("Return the sum of the two operands.\n\n        >>> ExtendedContext.add(Decimal('12'), Decimal('7.00'))\n        Decimal('19.00')\n        >>> ExtendedContext.add(Decimal('1E+2'), Decimal('1.01E+4'))\n        Decimal('1.02E+4')\n        >>> ExtendedContext.add(1, Decimal(2))\n        Decimal('3')\n        >>> ExtendedContext.add(Decimal(8), 5)\n        Decimal('13')\n        >>> ExtendedContext.add(5, 5)\n        Decimal('10')\n        "));
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(3989);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _apply$171, (PyObject)null);
      var1.setlocal("_apply", var4);
      var3 = null;
      var1.setline(3992);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, canonical$172, PyString.fromInterned("Returns the same Decimal object.\n\n        As we do not have different encodings for the same number, the\n        received object already is in its canonical form.\n\n        >>> ExtendedContext.canonical(Decimal('2.50'))\n        Decimal('2.50')\n        "));
      var1.setlocal("canonical", var4);
      var3 = null;
      var1.setline(4003);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare$173, PyString.fromInterned("Compares values numerically.\n\n        If the signs of the operands differ, a value representing each operand\n        ('-1' if the operand is less than zero, '0' if the operand is zero or\n        negative zero, or '1' if the operand is greater than zero) is used in\n        place of that operand for the comparison instead of the actual\n        operand.\n\n        The comparison is then effected by subtracting the second operand from\n        the first and then returning a value according to the result of the\n        subtraction: '-1' if the result is less than zero, '0' if the result is\n        zero or negative zero, or '1' if the result is greater than zero.\n\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.1'))\n        Decimal('0')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.10'))\n        Decimal('0')\n        >>> ExtendedContext.compare(Decimal('3'), Decimal('2.1'))\n        Decimal('1')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('-3'))\n        Decimal('1')\n        >>> ExtendedContext.compare(Decimal('-3'), Decimal('2.1'))\n        Decimal('-1')\n        >>> ExtendedContext.compare(1, 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare(Decimal(1), 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare(1, Decimal(2))\n        Decimal('-1')\n        "));
      var1.setlocal("compare", var4);
      var3 = null;
      var1.setline(4039);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare_signal$174, PyString.fromInterned("Compares the values of the two operands numerically.\n\n        It's pretty much like compare(), but all NaNs signal, with signaling\n        NaNs taking precedence over quiet NaNs.\n\n        >>> c = ExtendedContext\n        >>> c.compare_signal(Decimal('2.1'), Decimal('3'))\n        Decimal('-1')\n        >>> c.compare_signal(Decimal('2.1'), Decimal('2.1'))\n        Decimal('0')\n        >>> c.flags[InvalidOperation] = 0\n        >>> print c.flags[InvalidOperation]\n        0\n        >>> c.compare_signal(Decimal('NaN'), Decimal('2.1'))\n        Decimal('NaN')\n        >>> print c.flags[InvalidOperation]\n        1\n        >>> c.flags[InvalidOperation] = 0\n        >>> print c.flags[InvalidOperation]\n        0\n        >>> c.compare_signal(Decimal('sNaN'), Decimal('2.1'))\n        Decimal('NaN')\n        >>> print c.flags[InvalidOperation]\n        1\n        >>> c.compare_signal(-1, 2)\n        Decimal('-1')\n        >>> c.compare_signal(Decimal(-1), 2)\n        Decimal('-1')\n        >>> c.compare_signal(-1, Decimal(2))\n        Decimal('-1')\n        "));
      var1.setlocal("compare_signal", var4);
      var3 = null;
      var1.setline(4074);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare_total$175, PyString.fromInterned("Compares two operands using their abstract representation.\n\n        This is not like the standard compare, which use their numerical\n        value. Note that a total ordering is defined for all possible abstract\n        representations.\n\n        >>> ExtendedContext.compare_total(Decimal('12.73'), Decimal('127.9'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('-127'),  Decimal('12'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.3'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.30'))\n        Decimal('0')\n        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('12.300'))\n        Decimal('1')\n        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('NaN'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(1, 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal(1), 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(1, Decimal(2))\n        Decimal('-1')\n        "));
      var1.setlocal("compare_total", var4);
      var3 = null;
      var1.setline(4103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare_total_mag$176, PyString.fromInterned("Compares two operands using their abstract representation ignoring sign.\n\n        Like compare_total, but with operand's sign ignored and assumed to be 0.\n        "));
      var1.setlocal("compare_total_mag", var4);
      var3 = null;
      var1.setline(4111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy_abs$177, PyString.fromInterned("Returns a copy of the operand with the sign set to 0.\n\n        >>> ExtendedContext.copy_abs(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.copy_abs(Decimal('-100'))\n        Decimal('100')\n        >>> ExtendedContext.copy_abs(-1)\n        Decimal('1')\n        "));
      var1.setlocal("copy_abs", var4);
      var3 = null;
      var1.setline(4124);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy_decimal$178, PyString.fromInterned("Returns a copy of the decimal object.\n\n        >>> ExtendedContext.copy_decimal(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.copy_decimal(Decimal('-1.00'))\n        Decimal('-1.00')\n        >>> ExtendedContext.copy_decimal(1)\n        Decimal('1')\n        "));
      var1.setlocal("copy_decimal", var4);
      var3 = null;
      var1.setline(4137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy_negate$179, PyString.fromInterned("Returns a copy of the operand with the sign inverted.\n\n        >>> ExtendedContext.copy_negate(Decimal('101.5'))\n        Decimal('-101.5')\n        >>> ExtendedContext.copy_negate(Decimal('-101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.copy_negate(1)\n        Decimal('-1')\n        "));
      var1.setlocal("copy_negate", var4);
      var3 = null;
      var1.setline(4150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy_sign$180, PyString.fromInterned("Copies the second operand's sign to the first one.\n\n        In detail, it returns a copy of the first operand with the sign\n        equal to the sign of the second operand.\n\n        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('7.33'))\n        Decimal('1.50')\n        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('7.33'))\n        Decimal('1.50')\n        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('-7.33'))\n        Decimal('-1.50')\n        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('-7.33'))\n        Decimal('-1.50')\n        >>> ExtendedContext.copy_sign(1, -2)\n        Decimal('-1')\n        >>> ExtendedContext.copy_sign(Decimal(1), -2)\n        Decimal('-1')\n        >>> ExtendedContext.copy_sign(1, Decimal(-2))\n        Decimal('-1')\n        "));
      var1.setlocal("copy_sign", var4);
      var3 = null;
      var1.setline(4174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, divide$181, PyString.fromInterned("Decimal division in a specified context.\n\n        >>> ExtendedContext.divide(Decimal('1'), Decimal('3'))\n        Decimal('0.333333333')\n        >>> ExtendedContext.divide(Decimal('2'), Decimal('3'))\n        Decimal('0.666666667')\n        >>> ExtendedContext.divide(Decimal('5'), Decimal('2'))\n        Decimal('2.5')\n        >>> ExtendedContext.divide(Decimal('1'), Decimal('10'))\n        Decimal('0.1')\n        >>> ExtendedContext.divide(Decimal('12'), Decimal('12'))\n        Decimal('1')\n        >>> ExtendedContext.divide(Decimal('8.00'), Decimal('2'))\n        Decimal('4.00')\n        >>> ExtendedContext.divide(Decimal('2.400'), Decimal('2.0'))\n        Decimal('1.20')\n        >>> ExtendedContext.divide(Decimal('1000'), Decimal('100'))\n        Decimal('10')\n        >>> ExtendedContext.divide(Decimal('1000'), Decimal('1'))\n        Decimal('1000')\n        >>> ExtendedContext.divide(Decimal('2.40E+6'), Decimal('2'))\n        Decimal('1.20E+6')\n        >>> ExtendedContext.divide(5, 5)\n        Decimal('1')\n        >>> ExtendedContext.divide(Decimal(5), 5)\n        Decimal('1')\n        >>> ExtendedContext.divide(5, Decimal(5))\n        Decimal('1')\n        "));
      var1.setlocal("divide", var4);
      var3 = null;
      var1.setline(4211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, divide_int$182, PyString.fromInterned("Divides two numbers and returns the integer part of the result.\n\n        >>> ExtendedContext.divide_int(Decimal('2'), Decimal('3'))\n        Decimal('0')\n        >>> ExtendedContext.divide_int(Decimal('10'), Decimal('3'))\n        Decimal('3')\n        >>> ExtendedContext.divide_int(Decimal('1'), Decimal('0.3'))\n        Decimal('3')\n        >>> ExtendedContext.divide_int(10, 3)\n        Decimal('3')\n        >>> ExtendedContext.divide_int(Decimal(10), 3)\n        Decimal('3')\n        >>> ExtendedContext.divide_int(10, Decimal(3))\n        Decimal('3')\n        "));
      var1.setlocal("divide_int", var4);
      var3 = null;
      var1.setline(4234);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, divmod$183, PyString.fromInterned("Return (a // b, a % b).\n\n        >>> ExtendedContext.divmod(Decimal(8), Decimal(3))\n        (Decimal('2'), Decimal('2'))\n        >>> ExtendedContext.divmod(Decimal(8), Decimal(4))\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(8, 4)\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(Decimal(8), 4)\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(8, Decimal(4))\n        (Decimal('2'), Decimal('0'))\n        "));
      var1.setlocal("divmod", var4);
      var3 = null;
      var1.setline(4255);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exp$184, PyString.fromInterned("Returns e ** a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.exp(Decimal('-Infinity'))\n        Decimal('0')\n        >>> c.exp(Decimal('-1'))\n        Decimal('0.367879441')\n        >>> c.exp(Decimal('0'))\n        Decimal('1')\n        >>> c.exp(Decimal('1'))\n        Decimal('2.71828183')\n        >>> c.exp(Decimal('0.693147181'))\n        Decimal('2.00000000')\n        >>> c.exp(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.exp(10)\n        Decimal('22026.4658')\n        "));
      var1.setlocal("exp", var4);
      var3 = null;
      var1.setline(4279);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fma$185, PyString.fromInterned("Returns a multiplied by b, plus c.\n\n        The first two operands are multiplied together, using multiply,\n        the third operand is then added to the result of that\n        multiplication, using add, all with only one final rounding.\n\n        >>> ExtendedContext.fma(Decimal('3'), Decimal('5'), Decimal('7'))\n        Decimal('22')\n        >>> ExtendedContext.fma(Decimal('3'), Decimal('-5'), Decimal('7'))\n        Decimal('-8')\n        >>> ExtendedContext.fma(Decimal('888565290'), Decimal('1557.96930'), Decimal('-86087.7578'))\n        Decimal('1.38435736E+12')\n        >>> ExtendedContext.fma(1, 3, 4)\n        Decimal('7')\n        >>> ExtendedContext.fma(1, Decimal(3), 4)\n        Decimal('7')\n        >>> ExtendedContext.fma(1, 3, Decimal(4))\n        Decimal('7')\n        "));
      var1.setlocal("fma", var4);
      var3 = null;
      var1.setline(4302);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_canonical$186, PyString.fromInterned("Return True if the operand is canonical; otherwise return False.\n\n        Currently, the encoding of a Decimal instance is always\n        canonical, so this method returns True for any Decimal.\n\n        >>> ExtendedContext.is_canonical(Decimal('2.50'))\n        True\n        "));
      var1.setlocal("is_canonical", var4);
      var3 = null;
      var1.setline(4313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_finite$187, PyString.fromInterned("Return True if the operand is finite; otherwise return False.\n\n        A Decimal instance is considered finite if it is neither\n        infinite nor a NaN.\n\n        >>> ExtendedContext.is_finite(Decimal('2.50'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('-0.3'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('0'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('Inf'))\n        False\n        >>> ExtendedContext.is_finite(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_finite(1)\n        True\n        "));
      var1.setlocal("is_finite", var4);
      var3 = null;
      var1.setline(4335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_infinite$188, PyString.fromInterned("Return True if the operand is infinite; otherwise return False.\n\n        >>> ExtendedContext.is_infinite(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_infinite(Decimal('-Inf'))\n        True\n        >>> ExtendedContext.is_infinite(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_infinite(1)\n        False\n        "));
      var1.setlocal("is_infinite", var4);
      var3 = null;
      var1.setline(4350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_nan$189, PyString.fromInterned("Return True if the operand is a qNaN or sNaN;\n        otherwise return False.\n\n        >>> ExtendedContext.is_nan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_nan(Decimal('NaN'))\n        True\n        >>> ExtendedContext.is_nan(Decimal('-sNaN'))\n        True\n        >>> ExtendedContext.is_nan(1)\n        False\n        "));
      var1.setlocal("is_nan", var4);
      var3 = null;
      var1.setline(4366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_normal$190, PyString.fromInterned("Return True if the operand is a normal number;\n        otherwise return False.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.is_normal(Decimal('2.50'))\n        True\n        >>> c.is_normal(Decimal('0.1E-999'))\n        False\n        >>> c.is_normal(Decimal('0.00'))\n        False\n        >>> c.is_normal(Decimal('-Inf'))\n        False\n        >>> c.is_normal(Decimal('NaN'))\n        False\n        >>> c.is_normal(1)\n        True\n        "));
      var1.setlocal("is_normal", var4);
      var3 = null;
      var1.setline(4389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_qnan$191, PyString.fromInterned("Return True if the operand is a quiet NaN; otherwise return False.\n\n        >>> ExtendedContext.is_qnan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_qnan(Decimal('NaN'))\n        True\n        >>> ExtendedContext.is_qnan(Decimal('sNaN'))\n        False\n        >>> ExtendedContext.is_qnan(1)\n        False\n        "));
      var1.setlocal("is_qnan", var4);
      var3 = null;
      var1.setline(4404);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_signed$192, PyString.fromInterned("Return True if the operand is negative; otherwise return False.\n\n        >>> ExtendedContext.is_signed(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_signed(Decimal('-12'))\n        True\n        >>> ExtendedContext.is_signed(Decimal('-0'))\n        True\n        >>> ExtendedContext.is_signed(8)\n        False\n        >>> ExtendedContext.is_signed(-8)\n        True\n        "));
      var1.setlocal("is_signed", var4);
      var3 = null;
      var1.setline(4421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_snan$193, PyString.fromInterned("Return True if the operand is a signaling NaN;\n        otherwise return False.\n\n        >>> ExtendedContext.is_snan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_snan(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_snan(Decimal('sNaN'))\n        True\n        >>> ExtendedContext.is_snan(1)\n        False\n        "));
      var1.setlocal("is_snan", var4);
      var3 = null;
      var1.setline(4437);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_subnormal$194, PyString.fromInterned("Return True if the operand is subnormal; otherwise return False.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.is_subnormal(Decimal('2.50'))\n        False\n        >>> c.is_subnormal(Decimal('0.1E-999'))\n        True\n        >>> c.is_subnormal(Decimal('0.00'))\n        False\n        >>> c.is_subnormal(Decimal('-Inf'))\n        False\n        >>> c.is_subnormal(Decimal('NaN'))\n        False\n        >>> c.is_subnormal(1)\n        False\n        "));
      var1.setlocal("is_subnormal", var4);
      var3 = null;
      var1.setline(4459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_zero$195, PyString.fromInterned("Return True if the operand is a zero; otherwise return False.\n\n        >>> ExtendedContext.is_zero(Decimal('0'))\n        True\n        >>> ExtendedContext.is_zero(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_zero(Decimal('-0E+2'))\n        True\n        >>> ExtendedContext.is_zero(1)\n        False\n        >>> ExtendedContext.is_zero(0)\n        True\n        "));
      var1.setlocal("is_zero", var4);
      var3 = null;
      var1.setline(4476);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ln$196, PyString.fromInterned("Returns the natural (base e) logarithm of the operand.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.ln(Decimal('0'))\n        Decimal('-Infinity')\n        >>> c.ln(Decimal('1.000'))\n        Decimal('0')\n        >>> c.ln(Decimal('2.71828183'))\n        Decimal('1.00000000')\n        >>> c.ln(Decimal('10'))\n        Decimal('2.30258509')\n        >>> c.ln(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.ln(1)\n        Decimal('0')\n        "));
      var1.setlocal("ln", var4);
      var3 = null;
      var1.setline(4498);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log10$197, PyString.fromInterned("Returns the base 10 logarithm of the operand.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.log10(Decimal('0'))\n        Decimal('-Infinity')\n        >>> c.log10(Decimal('0.001'))\n        Decimal('-3')\n        >>> c.log10(Decimal('1.000'))\n        Decimal('0')\n        >>> c.log10(Decimal('2'))\n        Decimal('0.301029996')\n        >>> c.log10(Decimal('10'))\n        Decimal('1')\n        >>> c.log10(Decimal('70'))\n        Decimal('1.84509804')\n        >>> c.log10(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.log10(0)\n        Decimal('-Infinity')\n        >>> c.log10(1)\n        Decimal('0')\n        "));
      var1.setlocal("log10", var4);
      var3 = null;
      var1.setline(4526);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, logb$198, PyString.fromInterned(" Returns the exponent of the magnitude of the operand's MSD.\n\n        The result is the integer which is the exponent of the magnitude\n        of the most significant digit of the operand (as though the\n        operand were truncated to a single digit while maintaining the\n        value of that digit and without limiting the resulting exponent).\n\n        >>> ExtendedContext.logb(Decimal('250'))\n        Decimal('2')\n        >>> ExtendedContext.logb(Decimal('2.50'))\n        Decimal('0')\n        >>> ExtendedContext.logb(Decimal('0.03'))\n        Decimal('-2')\n        >>> ExtendedContext.logb(Decimal('0'))\n        Decimal('-Infinity')\n        >>> ExtendedContext.logb(1)\n        Decimal('0')\n        >>> ExtendedContext.logb(10)\n        Decimal('1')\n        >>> ExtendedContext.logb(100)\n        Decimal('2')\n        "));
      var1.setlocal("logb", var4);
      var3 = null;
      var1.setline(4552);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, logical_and$199, PyString.fromInterned("Applies the logical operation 'and' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_and(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('0'), Decimal('1'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('1'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('1'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_and(Decimal('1100'), Decimal('1010'))\n        Decimal('1000')\n        >>> ExtendedContext.logical_and(Decimal('1111'), Decimal('10'))\n        Decimal('10')\n        >>> ExtendedContext.logical_and(110, 1101)\n        Decimal('100')\n        >>> ExtendedContext.logical_and(Decimal(110), 1101)\n        Decimal('100')\n        >>> ExtendedContext.logical_and(110, Decimal(1101))\n        Decimal('100')\n        "));
      var1.setlocal("logical_and", var4);
      var3 = null;
      var1.setline(4579);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, logical_invert$200, PyString.fromInterned("Invert all the digits in the operand.\n\n        The operand must be a logical number.\n\n        >>> ExtendedContext.logical_invert(Decimal('0'))\n        Decimal('111111111')\n        >>> ExtendedContext.logical_invert(Decimal('1'))\n        Decimal('111111110')\n        >>> ExtendedContext.logical_invert(Decimal('111111111'))\n        Decimal('0')\n        >>> ExtendedContext.logical_invert(Decimal('101010101'))\n        Decimal('10101010')\n        >>> ExtendedContext.logical_invert(1101)\n        Decimal('111110010')\n        "));
      var1.setlocal("logical_invert", var4);
      var3 = null;
      var1.setline(4598);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, logical_or$201, PyString.fromInterned("Applies the logical operation 'or' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_or(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_or(Decimal('0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1'), Decimal('0'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1100'), Decimal('1010'))\n        Decimal('1110')\n        >>> ExtendedContext.logical_or(Decimal('1110'), Decimal('10'))\n        Decimal('1110')\n        >>> ExtendedContext.logical_or(110, 1101)\n        Decimal('1111')\n        >>> ExtendedContext.logical_or(Decimal(110), 1101)\n        Decimal('1111')\n        >>> ExtendedContext.logical_or(110, Decimal(1101))\n        Decimal('1111')\n        "));
      var1.setlocal("logical_or", var4);
      var3 = null;
      var1.setline(4625);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, logical_xor$202, PyString.fromInterned("Applies the logical operation 'xor' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_xor(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_xor(Decimal('0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_xor(Decimal('1'), Decimal('0'))\n        Decimal('1')\n        >>> ExtendedContext.logical_xor(Decimal('1'), Decimal('1'))\n        Decimal('0')\n        >>> ExtendedContext.logical_xor(Decimal('1100'), Decimal('1010'))\n        Decimal('110')\n        >>> ExtendedContext.logical_xor(Decimal('1111'), Decimal('10'))\n        Decimal('1101')\n        >>> ExtendedContext.logical_xor(110, 1101)\n        Decimal('1011')\n        >>> ExtendedContext.logical_xor(Decimal(110), 1101)\n        Decimal('1011')\n        >>> ExtendedContext.logical_xor(110, Decimal(1101))\n        Decimal('1011')\n        "));
      var1.setlocal("logical_xor", var4);
      var3 = null;
      var1.setline(4652);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, max$203, PyString.fromInterned("max compares two values numerically and returns the maximum.\n\n        If either operand is a NaN then the general rules apply.\n        Otherwise, the operands are compared as though by the compare\n        operation.  If they are numerically equal then the left-hand operand\n        is chosen as the result.  Otherwise the maximum (closer to positive\n        infinity) of the two operands is chosen as the result.\n\n        >>> ExtendedContext.max(Decimal('3'), Decimal('2'))\n        Decimal('3')\n        >>> ExtendedContext.max(Decimal('-10'), Decimal('3'))\n        Decimal('3')\n        >>> ExtendedContext.max(Decimal('1.0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.max(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.max(1, 2)\n        Decimal('2')\n        >>> ExtendedContext.max(Decimal(1), 2)\n        Decimal('2')\n        >>> ExtendedContext.max(1, Decimal(2))\n        Decimal('2')\n        "));
      var1.setlocal("max", var4);
      var3 = null;
      var1.setline(4679);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, max_mag$204, PyString.fromInterned("Compares the values numerically with their sign ignored.\n\n        >>> ExtendedContext.max_mag(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.max_mag(Decimal('7'), Decimal('-10'))\n        Decimal('-10')\n        >>> ExtendedContext.max_mag(1, -2)\n        Decimal('-2')\n        >>> ExtendedContext.max_mag(Decimal(1), -2)\n        Decimal('-2')\n        >>> ExtendedContext.max_mag(1, Decimal(-2))\n        Decimal('-2')\n        "));
      var1.setlocal("max_mag", var4);
      var3 = null;
      var1.setline(4696);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, min$205, PyString.fromInterned("min compares two values numerically and returns the minimum.\n\n        If either operand is a NaN then the general rules apply.\n        Otherwise, the operands are compared as though by the compare\n        operation.  If they are numerically equal then the left-hand operand\n        is chosen as the result.  Otherwise the minimum (closer to negative\n        infinity) of the two operands is chosen as the result.\n\n        >>> ExtendedContext.min(Decimal('3'), Decimal('2'))\n        Decimal('2')\n        >>> ExtendedContext.min(Decimal('-10'), Decimal('3'))\n        Decimal('-10')\n        >>> ExtendedContext.min(Decimal('1.0'), Decimal('1'))\n        Decimal('1.0')\n        >>> ExtendedContext.min(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.min(1, 2)\n        Decimal('1')\n        >>> ExtendedContext.min(Decimal(1), 2)\n        Decimal('1')\n        >>> ExtendedContext.min(1, Decimal(29))\n        Decimal('1')\n        "));
      var1.setlocal("min", var4);
      var3 = null;
      var1.setline(4723);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, min_mag$206, PyString.fromInterned("Compares the values numerically with their sign ignored.\n\n        >>> ExtendedContext.min_mag(Decimal('3'), Decimal('-2'))\n        Decimal('-2')\n        >>> ExtendedContext.min_mag(Decimal('-3'), Decimal('NaN'))\n        Decimal('-3')\n        >>> ExtendedContext.min_mag(1, -2)\n        Decimal('1')\n        >>> ExtendedContext.min_mag(Decimal(1), -2)\n        Decimal('1')\n        >>> ExtendedContext.min_mag(1, Decimal(-2))\n        Decimal('1')\n        "));
      var1.setlocal("min_mag", var4);
      var3 = null;
      var1.setline(4740);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, minus$207, PyString.fromInterned("Minus corresponds to unary prefix minus in Python.\n\n        The operation is evaluated using the same rules as subtract; the\n        operation minus(a) is calculated as subtract('0', a) where the '0'\n        has the same exponent as the operand.\n\n        >>> ExtendedContext.minus(Decimal('1.3'))\n        Decimal('-1.3')\n        >>> ExtendedContext.minus(Decimal('-1.3'))\n        Decimal('1.3')\n        >>> ExtendedContext.minus(1)\n        Decimal('-1')\n        "));
      var1.setlocal("minus", var4);
      var3 = null;
      var1.setline(4757);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, multiply$208, PyString.fromInterned("multiply multiplies two operands.\n\n        If either operand is a special value then the general rules apply.\n        Otherwise, the operands are multiplied together\n        ('long multiplication'), resulting in a number which may be as long as\n        the sum of the lengths of the two operands.\n\n        >>> ExtendedContext.multiply(Decimal('1.20'), Decimal('3'))\n        Decimal('3.60')\n        >>> ExtendedContext.multiply(Decimal('7'), Decimal('3'))\n        Decimal('21')\n        >>> ExtendedContext.multiply(Decimal('0.9'), Decimal('0.8'))\n        Decimal('0.72')\n        >>> ExtendedContext.multiply(Decimal('0.9'), Decimal('-0'))\n        Decimal('-0.0')\n        >>> ExtendedContext.multiply(Decimal('654321'), Decimal('654321'))\n        Decimal('4.28135971E+11')\n        >>> ExtendedContext.multiply(7, 7)\n        Decimal('49')\n        >>> ExtendedContext.multiply(Decimal(7), 7)\n        Decimal('49')\n        >>> ExtendedContext.multiply(7, Decimal(7))\n        Decimal('49')\n        "));
      var1.setlocal("multiply", var4);
      var3 = null;
      var1.setline(4789);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next_minus$209, PyString.fromInterned("Returns the largest representable number smaller than a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> ExtendedContext.next_minus(Decimal('1'))\n        Decimal('0.999999999')\n        >>> c.next_minus(Decimal('1E-1007'))\n        Decimal('0E-1007')\n        >>> ExtendedContext.next_minus(Decimal('-1.00000003'))\n        Decimal('-1.00000004')\n        >>> c.next_minus(Decimal('Infinity'))\n        Decimal('9.99999999E+999')\n        >>> c.next_minus(1)\n        Decimal('0.999999999')\n        "));
      var1.setlocal("next_minus", var4);
      var3 = null;
      var1.setline(4809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next_plus$210, PyString.fromInterned("Returns the smallest representable number larger than a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> ExtendedContext.next_plus(Decimal('1'))\n        Decimal('1.00000001')\n        >>> c.next_plus(Decimal('-1E-1007'))\n        Decimal('-0E-1007')\n        >>> ExtendedContext.next_plus(Decimal('-1.00000003'))\n        Decimal('-1.00000002')\n        >>> c.next_plus(Decimal('-Infinity'))\n        Decimal('-9.99999999E+999')\n        >>> c.next_plus(1)\n        Decimal('1.00000001')\n        "));
      var1.setlocal("next_plus", var4);
      var3 = null;
      var1.setline(4829);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next_toward$211, PyString.fromInterned("Returns the number closest to a, in direction towards b.\n\n        The result is the closest representable number from the first\n        operand (but not the first operand) that is in the direction\n        towards the second operand, unless the operands have the same\n        value.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.next_toward(Decimal('1'), Decimal('2'))\n        Decimal('1.00000001')\n        >>> c.next_toward(Decimal('-1E-1007'), Decimal('1'))\n        Decimal('-0E-1007')\n        >>> c.next_toward(Decimal('-1.00000003'), Decimal('0'))\n        Decimal('-1.00000002')\n        >>> c.next_toward(Decimal('1'), Decimal('0'))\n        Decimal('0.999999999')\n        >>> c.next_toward(Decimal('1E-1007'), Decimal('-100'))\n        Decimal('0E-1007')\n        >>> c.next_toward(Decimal('-1.00000003'), Decimal('-10'))\n        Decimal('-1.00000004')\n        >>> c.next_toward(Decimal('0.00'), Decimal('-0.0000'))\n        Decimal('-0.00')\n        >>> c.next_toward(0, 1)\n        Decimal('1E-1007')\n        >>> c.next_toward(Decimal(0), 1)\n        Decimal('1E-1007')\n        >>> c.next_toward(0, Decimal(1))\n        Decimal('1E-1007')\n        "));
      var1.setlocal("next_toward", var4);
      var3 = null;
      var1.setline(4864);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, normalize$212, PyString.fromInterned("normalize reduces an operand to its simplest form.\n\n        Essentially a plus operation with all trailing zeros removed from the\n        result.\n\n        >>> ExtendedContext.normalize(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.normalize(Decimal('-2.0'))\n        Decimal('-2')\n        >>> ExtendedContext.normalize(Decimal('1.200'))\n        Decimal('1.2')\n        >>> ExtendedContext.normalize(Decimal('-120'))\n        Decimal('-1.2E+2')\n        >>> ExtendedContext.normalize(Decimal('120.00'))\n        Decimal('1.2E+2')\n        >>> ExtendedContext.normalize(Decimal('0.00'))\n        Decimal('0')\n        >>> ExtendedContext.normalize(6)\n        Decimal('6')\n        "));
      var1.setlocal("normalize", var4);
      var3 = null;
      var1.setline(4888);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, number_class$213, PyString.fromInterned("Returns an indication of the class of the operand.\n\n        The class is one of the following strings:\n          -sNaN\n          -NaN\n          -Infinity\n          -Normal\n          -Subnormal\n          -Zero\n          +Zero\n          +Subnormal\n          +Normal\n          +Infinity\n\n        >>> c = Context(ExtendedContext)\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.number_class(Decimal('Infinity'))\n        '+Infinity'\n        >>> c.number_class(Decimal('1E-10'))\n        '+Normal'\n        >>> c.number_class(Decimal('2.50'))\n        '+Normal'\n        >>> c.number_class(Decimal('0.1E-999'))\n        '+Subnormal'\n        >>> c.number_class(Decimal('0'))\n        '+Zero'\n        >>> c.number_class(Decimal('-0'))\n        '-Zero'\n        >>> c.number_class(Decimal('-0.1E-999'))\n        '-Subnormal'\n        >>> c.number_class(Decimal('-1E-10'))\n        '-Normal'\n        >>> c.number_class(Decimal('-2.50'))\n        '-Normal'\n        >>> c.number_class(Decimal('-Infinity'))\n        '-Infinity'\n        >>> c.number_class(Decimal('NaN'))\n        'NaN'\n        >>> c.number_class(Decimal('-NaN'))\n        'NaN'\n        >>> c.number_class(Decimal('sNaN'))\n        'sNaN'\n        >>> c.number_class(123)\n        '+Normal'\n        "));
      var1.setlocal("number_class", var4);
      var3 = null;
      var1.setline(4938);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, plus$214, PyString.fromInterned("Plus corresponds to unary prefix plus in Python.\n\n        The operation is evaluated using the same rules as add; the\n        operation plus(a) is calculated as add('0', a) where the '0'\n        has the same exponent as the operand.\n\n        >>> ExtendedContext.plus(Decimal('1.3'))\n        Decimal('1.3')\n        >>> ExtendedContext.plus(Decimal('-1.3'))\n        Decimal('-1.3')\n        >>> ExtendedContext.plus(-1)\n        Decimal('-1')\n        "));
      var1.setlocal("plus", var4);
      var3 = null;
      var1.setline(4955);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, power$215, PyString.fromInterned("Raises a to the power of b, to modulo if given.\n\n        With two arguments, compute a**b.  If a is negative then b\n        must be integral.  The result will be inexact unless b is\n        integral and the result is finite and can be expressed exactly\n        in 'precision' digits.\n\n        With three arguments, compute (a**b) % modulo.  For the\n        three argument form, the following restrictions on the\n        arguments hold:\n\n         - all three arguments must be integral\n         - b must be nonnegative\n         - at least one of a or b must be nonzero\n         - modulo must be nonzero and have at most 'precision' digits\n\n        The result of pow(a, b, modulo) is identical to the result\n        that would be obtained by computing (a**b) % modulo with\n        unbounded precision, but is computed more efficiently.  It is\n        always exact.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.power(Decimal('2'), Decimal('3'))\n        Decimal('8')\n        >>> c.power(Decimal('-2'), Decimal('3'))\n        Decimal('-8')\n        >>> c.power(Decimal('2'), Decimal('-3'))\n        Decimal('0.125')\n        >>> c.power(Decimal('1.7'), Decimal('8'))\n        Decimal('69.7575744')\n        >>> c.power(Decimal('10'), Decimal('0.301029996'))\n        Decimal('2.00000000')\n        >>> c.power(Decimal('Infinity'), Decimal('-1'))\n        Decimal('0')\n        >>> c.power(Decimal('Infinity'), Decimal('0'))\n        Decimal('1')\n        >>> c.power(Decimal('Infinity'), Decimal('1'))\n        Decimal('Infinity')\n        >>> c.power(Decimal('-Infinity'), Decimal('-1'))\n        Decimal('-0')\n        >>> c.power(Decimal('-Infinity'), Decimal('0'))\n        Decimal('1')\n        >>> c.power(Decimal('-Infinity'), Decimal('1'))\n        Decimal('-Infinity')\n        >>> c.power(Decimal('-Infinity'), Decimal('2'))\n        Decimal('Infinity')\n        >>> c.power(Decimal('0'), Decimal('0'))\n        Decimal('NaN')\n\n        >>> c.power(Decimal('3'), Decimal('7'), Decimal('16'))\n        Decimal('11')\n        >>> c.power(Decimal('-3'), Decimal('7'), Decimal('16'))\n        Decimal('-11')\n        >>> c.power(Decimal('-3'), Decimal('8'), Decimal('16'))\n        Decimal('1')\n        >>> c.power(Decimal('3'), Decimal('7'), Decimal('-16'))\n        Decimal('11')\n        >>> c.power(Decimal('23E12345'), Decimal('67E189'), Decimal('123456789'))\n        Decimal('11729830')\n        >>> c.power(Decimal('-0'), Decimal('17'), Decimal('1729'))\n        Decimal('-0')\n        >>> c.power(Decimal('-23'), Decimal('0'), Decimal('65537'))\n        Decimal('1')\n        >>> ExtendedContext.power(7, 7)\n        Decimal('823543')\n        >>> ExtendedContext.power(Decimal(7), 7)\n        Decimal('823543')\n        >>> ExtendedContext.power(7, Decimal(7), 2)\n        Decimal('1')\n        "));
      var1.setlocal("power", var4);
      var3 = null;
      var1.setline(5035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, quantize$216, PyString.fromInterned("Returns a value equal to 'a' (rounded), having the exponent of 'b'.\n\n        The coefficient of the result is derived from that of the left-hand\n        operand.  It may be rounded using the current rounding setting (if the\n        exponent is being increased), multiplied by a positive power of ten (if\n        the exponent is being decreased), or is unchanged (if the exponent is\n        already equal to that of the right-hand operand).\n\n        Unlike other operations, if the length of the coefficient after the\n        quantize operation would be greater than precision then an Invalid\n        operation condition is raised.  This guarantees that, unless there is\n        an error condition, the exponent of the result of a quantize is always\n        equal to that of the right-hand operand.\n\n        Also unlike other operations, quantize will never raise Underflow, even\n        if the result is subnormal and inexact.\n\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.001'))\n        Decimal('2.170')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.01'))\n        Decimal('2.17')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.1'))\n        Decimal('2.2')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('1e+0'))\n        Decimal('2')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('1e+1'))\n        Decimal('0E+1')\n        >>> ExtendedContext.quantize(Decimal('-Inf'), Decimal('Infinity'))\n        Decimal('-Infinity')\n        >>> ExtendedContext.quantize(Decimal('2'), Decimal('Infinity'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('-0.1'), Decimal('1'))\n        Decimal('-0')\n        >>> ExtendedContext.quantize(Decimal('-0'), Decimal('1e+5'))\n        Decimal('-0E+5')\n        >>> ExtendedContext.quantize(Decimal('+35236450.6'), Decimal('1e-2'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('-35236450.6'), Decimal('1e-2'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e-1'))\n        Decimal('217.0')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e-0'))\n        Decimal('217')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e+1'))\n        Decimal('2.2E+2')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e+2'))\n        Decimal('2E+2')\n        >>> ExtendedContext.quantize(1, 2)\n        Decimal('1')\n        >>> ExtendedContext.quantize(Decimal(1), 2)\n        Decimal('1')\n        >>> ExtendedContext.quantize(1, Decimal(2))\n        Decimal('1')\n        "));
      var1.setlocal("quantize", var4);
      var3 = null;
      var1.setline(5093);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, radix$217, PyString.fromInterned("Just returns 10, as this is Decimal, :)\n\n        >>> ExtendedContext.radix()\n        Decimal('10')\n        "));
      var1.setlocal("radix", var4);
      var3 = null;
      var1.setline(5101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remainder$218, PyString.fromInterned("Returns the remainder from integer division.\n\n        The result is the residue of the dividend after the operation of\n        calculating integer division as described for divide-integer, rounded\n        to precision digits if necessary.  The sign of the result, if\n        non-zero, is the same as that of the original dividend.\n\n        This operation will fail under the same conditions as integer division\n        (that is, if integer division on the same two operands would fail, the\n        remainder cannot be calculated).\n\n        >>> ExtendedContext.remainder(Decimal('2.1'), Decimal('3'))\n        Decimal('2.1')\n        >>> ExtendedContext.remainder(Decimal('10'), Decimal('3'))\n        Decimal('1')\n        >>> ExtendedContext.remainder(Decimal('-10'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.remainder(Decimal('10.2'), Decimal('1'))\n        Decimal('0.2')\n        >>> ExtendedContext.remainder(Decimal('10'), Decimal('0.3'))\n        Decimal('0.1')\n        >>> ExtendedContext.remainder(Decimal('3.6'), Decimal('1.3'))\n        Decimal('1.0')\n        >>> ExtendedContext.remainder(22, 6)\n        Decimal('4')\n        >>> ExtendedContext.remainder(Decimal(22), 6)\n        Decimal('4')\n        >>> ExtendedContext.remainder(22, Decimal(6))\n        Decimal('4')\n        "));
      var1.setlocal("remainder", var4);
      var3 = null;
      var1.setline(5139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remainder_near$219, PyString.fromInterned("Returns to be \"a - b * n\", where n is the integer nearest the exact\n        value of \"x / b\" (if two integers are equally near then the even one\n        is chosen).  If the result is equal to 0 then its sign will be the\n        sign of a.\n\n        This operation will fail under the same conditions as integer division\n        (that is, if integer division on the same two operands would fail, the\n        remainder cannot be calculated).\n\n        >>> ExtendedContext.remainder_near(Decimal('2.1'), Decimal('3'))\n        Decimal('-0.9')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('6'))\n        Decimal('-2')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('3'))\n        Decimal('1')\n        >>> ExtendedContext.remainder_near(Decimal('-10'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.remainder_near(Decimal('10.2'), Decimal('1'))\n        Decimal('0.2')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('0.3'))\n        Decimal('0.1')\n        >>> ExtendedContext.remainder_near(Decimal('3.6'), Decimal('1.3'))\n        Decimal('-0.3')\n        >>> ExtendedContext.remainder_near(3, 11)\n        Decimal('3')\n        >>> ExtendedContext.remainder_near(Decimal(3), 11)\n        Decimal('3')\n        >>> ExtendedContext.remainder_near(3, Decimal(11))\n        Decimal('3')\n        "));
      var1.setlocal("remainder_near", var4);
      var3 = null;
      var1.setline(5173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rotate$220, PyString.fromInterned("Returns a rotated copy of a, b times.\n\n        The coefficient of the result is a rotated copy of the digits in\n        the coefficient of the first operand.  The number of places of\n        rotation is taken from the absolute value of the second operand,\n        with the rotation being to the left if the second operand is\n        positive or to the right otherwise.\n\n        >>> ExtendedContext.rotate(Decimal('34'), Decimal('8'))\n        Decimal('400000003')\n        >>> ExtendedContext.rotate(Decimal('12'), Decimal('9'))\n        Decimal('12')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('-2'))\n        Decimal('891234567')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('0'))\n        Decimal('123456789')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('+2'))\n        Decimal('345678912')\n        >>> ExtendedContext.rotate(1333333, 1)\n        Decimal('13333330')\n        >>> ExtendedContext.rotate(Decimal(1333333), 1)\n        Decimal('13333330')\n        >>> ExtendedContext.rotate(1333333, Decimal(1))\n        Decimal('13333330')\n        "));
      var1.setlocal("rotate", var4);
      var3 = null;
      var1.setline(5202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, same_quantum$221, PyString.fromInterned("Returns True if the two operands have the same exponent.\n\n        The result is never affected by either the sign or the coefficient of\n        either operand.\n\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('0.001'))\n        False\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('0.01'))\n        True\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('1'))\n        False\n        >>> ExtendedContext.same_quantum(Decimal('Inf'), Decimal('-Inf'))\n        True\n        >>> ExtendedContext.same_quantum(10000, -1)\n        True\n        >>> ExtendedContext.same_quantum(Decimal(10000), -1)\n        True\n        >>> ExtendedContext.same_quantum(10000, Decimal(-1))\n        True\n        "));
      var1.setlocal("same_quantum", var4);
      var3 = null;
      var1.setline(5226);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, scaleb$222, PyString.fromInterned("Returns the first operand after adding the second value its exp.\n\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('-2'))\n        Decimal('0.0750')\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('0'))\n        Decimal('7.50')\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('3'))\n        Decimal('7.50E+3')\n        >>> ExtendedContext.scaleb(1, 4)\n        Decimal('1E+4')\n        >>> ExtendedContext.scaleb(Decimal(1), 4)\n        Decimal('1E+4')\n        >>> ExtendedContext.scaleb(1, Decimal(4))\n        Decimal('1E+4')\n        "));
      var1.setlocal("scaleb", var4);
      var3 = null;
      var1.setline(5245);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shift$223, PyString.fromInterned("Returns a shifted copy of a, b times.\n\n        The coefficient of the result is a shifted copy of the digits\n        in the coefficient of the first operand.  The number of places\n        to shift is taken from the absolute value of the second operand,\n        with the shift being to the left if the second operand is\n        positive or to the right otherwise.  Digits shifted into the\n        coefficient are zeros.\n\n        >>> ExtendedContext.shift(Decimal('34'), Decimal('8'))\n        Decimal('400000000')\n        >>> ExtendedContext.shift(Decimal('12'), Decimal('9'))\n        Decimal('0')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('-2'))\n        Decimal('1234567')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('0'))\n        Decimal('123456789')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('+2'))\n        Decimal('345678900')\n        >>> ExtendedContext.shift(88888888, 2)\n        Decimal('888888800')\n        >>> ExtendedContext.shift(Decimal(88888888), 2)\n        Decimal('888888800')\n        >>> ExtendedContext.shift(88888888, Decimal(2))\n        Decimal('888888800')\n        "));
      var1.setlocal("shift", var4);
      var3 = null;
      var1.setline(5275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sqrt$224, PyString.fromInterned("Square root of a non-negative number to context precision.\n\n        If the result must be inexact, it is rounded using the round-half-even\n        algorithm.\n\n        >>> ExtendedContext.sqrt(Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.sqrt(Decimal('-0'))\n        Decimal('-0')\n        >>> ExtendedContext.sqrt(Decimal('0.39'))\n        Decimal('0.624499800')\n        >>> ExtendedContext.sqrt(Decimal('100'))\n        Decimal('10')\n        >>> ExtendedContext.sqrt(Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.sqrt(Decimal('1.0'))\n        Decimal('1.0')\n        >>> ExtendedContext.sqrt(Decimal('1.00'))\n        Decimal('1.0')\n        >>> ExtendedContext.sqrt(Decimal('7'))\n        Decimal('2.64575131')\n        >>> ExtendedContext.sqrt(Decimal('10'))\n        Decimal('3.16227766')\n        >>> ExtendedContext.sqrt(2)\n        Decimal('1.41421356')\n        >>> ExtendedContext.prec\n        9\n        "));
      var1.setlocal("sqrt", var4);
      var3 = null;
      var1.setline(5307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, subtract$225, PyString.fromInterned("Return the difference between the two operands.\n\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('1.07'))\n        Decimal('0.23')\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('1.30'))\n        Decimal('0.00')\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('2.07'))\n        Decimal('-0.77')\n        >>> ExtendedContext.subtract(8, 5)\n        Decimal('3')\n        >>> ExtendedContext.subtract(Decimal(8), 5)\n        Decimal('3')\n        >>> ExtendedContext.subtract(8, Decimal(5))\n        Decimal('3')\n        "));
      var1.setlocal("subtract", var4);
      var3 = null;
      var1.setline(5330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, to_eng_string$226, PyString.fromInterned("Converts a number to a string, using scientific notation.\n\n        The operation is not affected by the context.\n        "));
      var1.setlocal("to_eng_string", var4);
      var3 = null;
      var1.setline(5338);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, to_sci_string$227, PyString.fromInterned("Converts a number to a string, using scientific notation.\n\n        The operation is not affected by the context.\n        "));
      var1.setlocal("to_sci_string", var4);
      var3 = null;
      var1.setline(5346);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, to_integral_exact$228, PyString.fromInterned("Rounds to an integer.\n\n        When the operand has a negative exponent, the result is the same\n        as using the quantize() operation using the given operand as the\n        left-hand-operand, 1E+0 as the right-hand-operand, and the precision\n        of the operand as the precision setting; Inexact and Rounded flags\n        are allowed in this operation.  The rounding mode is taken from the\n        context.\n\n        >>> ExtendedContext.to_integral_exact(Decimal('2.1'))\n        Decimal('2')\n        >>> ExtendedContext.to_integral_exact(Decimal('100'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_exact(Decimal('100.0'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_exact(Decimal('101.5'))\n        Decimal('102')\n        >>> ExtendedContext.to_integral_exact(Decimal('-101.5'))\n        Decimal('-102')\n        >>> ExtendedContext.to_integral_exact(Decimal('10E+5'))\n        Decimal('1.0E+6')\n        >>> ExtendedContext.to_integral_exact(Decimal('7.89E+77'))\n        Decimal('7.89E+77')\n        >>> ExtendedContext.to_integral_exact(Decimal('-Inf'))\n        Decimal('-Infinity')\n        "));
      var1.setlocal("to_integral_exact", var4);
      var3 = null;
      var1.setline(5376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, to_integral_value$229, PyString.fromInterned("Rounds to an integer.\n\n        When the operand has a negative exponent, the result is the same\n        as using the quantize() operation using the given operand as the\n        left-hand-operand, 1E+0 as the right-hand-operand, and the precision\n        of the operand as the precision setting, except that no flags will\n        be set.  The rounding mode is taken from the context.\n\n        >>> ExtendedContext.to_integral_value(Decimal('2.1'))\n        Decimal('2')\n        >>> ExtendedContext.to_integral_value(Decimal('100'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_value(Decimal('100.0'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_value(Decimal('101.5'))\n        Decimal('102')\n        >>> ExtendedContext.to_integral_value(Decimal('-101.5'))\n        Decimal('-102')\n        >>> ExtendedContext.to_integral_value(Decimal('10E+5'))\n        Decimal('1.0E+6')\n        >>> ExtendedContext.to_integral_value(Decimal('7.89E+77'))\n        Decimal('7.89E+77')\n        >>> ExtendedContext.to_integral_value(Decimal('-Inf'))\n        Decimal('-Infinity')\n        "));
      var1.setlocal("to_integral_value", var4);
      var3 = null;
      var1.setline(5406);
      var5 = var1.getname("to_integral_value");
      var1.setlocal("to_integral", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$153(PyFrame var1, ThreadState var2) {
      var1.to_cell(4, 0);
      var1.to_cell(3, 1);

      PyException var3;
      PyObject var6;
      try {
         var1.setline(3780);
         var6 = var1.getglobal("DefaultContext");
         var1.setlocal(10, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("NameError"))) {
            throw var3;
         }

         var1.setline(3782);
      }

      var1.setline(3784);
      var1.setline(3784);
      var6 = var1.getlocal(1);
      PyObject var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(1) : var1.getlocal(10).__getattr__("prec");
      var1.getlocal(0).__setattr__("prec", var6);
      var3 = null;
      var1.setline(3785);
      var1.setline(3785);
      var6 = var1.getlocal(2);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(2) : var1.getlocal(10).__getattr__("rounding");
      var1.getlocal(0).__setattr__("rounding", var6);
      var3 = null;
      var1.setline(3786);
      var1.setline(3786);
      var6 = var1.getlocal(5);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(5) : var1.getlocal(10).__getattr__("Emin");
      var1.getlocal(0).__setattr__("Emin", var6);
      var3 = null;
      var1.setline(3787);
      var1.setline(3787);
      var6 = var1.getlocal(6);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(6) : var1.getlocal(10).__getattr__("Emax");
      var1.getlocal(0).__setattr__("Emax", var6);
      var3 = null;
      var1.setline(3788);
      var1.setline(3788);
      var6 = var1.getlocal(7);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(7) : var1.getlocal(10).__getattr__("capitals");
      var1.getlocal(0).__setattr__("capitals", var6);
      var3 = null;
      var1.setline(3789);
      var1.setline(3789);
      var6 = var1.getlocal(8);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      var6 = var10000.__nonzero__() ? var1.getlocal(8) : var1.getlocal(10).__getattr__("_clamp");
      var1.getlocal(0).__setattr__("_clamp", var6);
      var3 = null;
      var1.setline(3791);
      var6 = var1.getlocal(9);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3792);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_ignored_flags", var8);
         var3 = null;
      } else {
         var1.setline(3794);
         var6 = var1.getlocal(9);
         var1.getlocal(0).__setattr__("_ignored_flags", var6);
         var3 = null;
      }

      var1.setline(3796);
      var6 = var1.getderef(1);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      PyObject[] var4;
      PyFunction var7;
      PyObject[] var9;
      PyObject var10002;
      PyObject var10004;
      PyCode var10006;
      if (var10000.__nonzero__()) {
         var1.setline(3797);
         var6 = var1.getlocal(10).__getattr__("traps").__getattr__("copy").__call__(var2);
         var1.getlocal(0).__setattr__("traps", var6);
         var3 = null;
      } else {
         var1.setline(3798);
         if (var1.getglobal("isinstance").__call__(var2, var1.getderef(1), var1.getglobal("dict")).__not__().__nonzero__()) {
            var1.setline(3799);
            var10000 = var1.getglobal("dict");
            var1.setline(3799);
            var10004 = var1.f_globals;
            var9 = Py.EmptyObjects;
            var10006 = f$154;
            var4 = new PyObject[]{var1.getclosure(1)};
            var7 = new PyFunction(var10004, var9, var10006, (PyObject)null, var4);
            var10002 = var7.__call__(var2, var1.getglobal("_signals").__iter__());
            Arrays.fill(var9, (Object)null);
            var6 = var10000.__call__(var2, var10002);
            var1.getlocal(0).__setattr__("traps", var6);
            var3 = null;
         } else {
            var1.setline(3801);
            var6 = var1.getderef(1);
            var1.getlocal(0).__setattr__("traps", var6);
            var3 = null;
         }
      }

      var1.setline(3803);
      var6 = var1.getderef(0);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3804);
         var6 = var1.getglobal("dict").__getattr__("fromkeys").__call__((ThreadState)var2, (PyObject)var1.getglobal("_signals"), (PyObject)Py.newInteger(0));
         var1.getlocal(0).__setattr__("flags", var6);
         var3 = null;
      } else {
         var1.setline(3805);
         if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0), var1.getglobal("dict")).__not__().__nonzero__()) {
            var1.setline(3806);
            var10000 = var1.getglobal("dict");
            var1.setline(3806);
            var10004 = var1.f_globals;
            var9 = Py.EmptyObjects;
            var10006 = f$155;
            var4 = new PyObject[]{var1.getclosure(0)};
            var7 = new PyFunction(var10004, var9, var10006, (PyObject)null, var4);
            var10002 = var7.__call__(var2, var1.getglobal("_signals").__iter__());
            Arrays.fill(var9, (Object)null);
            var6 = var10000.__call__(var2, var10002);
            var1.getlocal(0).__setattr__("flags", var6);
            var3 = null;
         } else {
            var1.setline(3808);
            var6 = var1.getderef(0);
            var1.getlocal(0).__setattr__("flags", var6);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$154(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(3799);
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

            var8 = (PyObject)var10000;
      }

      var1.setline(3799);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(3799);
         var1.setline(3799);
         PyObject[] var7 = new PyObject[]{var1.getlocal(1), null};
         var8 = var1.getglobal("int");
         PyObject var6 = var1.getlocal(1);
         PyObject var10002 = var6._in(var1.getderef(0));
         var6 = null;
         var7[1] = var8.__call__(var2, var10002);
         PyTuple var9 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[8];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject f$155(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(3806);
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

            var8 = (PyObject)var10000;
      }

      var1.setline(3806);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(3806);
         var1.setline(3806);
         PyObject[] var7 = new PyObject[]{var1.getlocal(1), null};
         var8 = var1.getglobal("int");
         PyObject var6 = var1.getlocal(1);
         PyObject var10002 = var6._in(var1.getderef(0));
         var6 = null;
         var7[1] = var8.__call__(var2, var10002);
         PyTuple var9 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[8];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject __repr__$156(PyFrame var1, ThreadState var2) {
      var1.setline(3811);
      PyString.fromInterned("Show the current context.");
      var1.setline(3812);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3813);
      var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("Context(prec=%(prec)d, rounding=%(rounding)s, Emin=%(Emin)d, Emax=%(Emax)d, capitals=%(capitals)d")._mod(var1.getglobal("vars").__call__(var2, var1.getlocal(0))));
      var1.setline(3816);
      PyList var10000 = new PyList();
      PyObject var7 = var10000.__getattr__("append");
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(3816);
      var7 = var1.getlocal(0).__getattr__("flags").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(3816);
         PyObject var4 = var7.__iternext__();
         PyObject[] var5;
         PyObject var6;
         if (var4 == null) {
            var1.setline(3816);
            var1.dellocal(3);
            var3 = var10000;
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(3817);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("flags=[")._add(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(2)))._add(PyString.fromInterned("]")));
            var1.setline(3818);
            var10000 = new PyList();
            var7 = var10000.__getattr__("append");
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(3818);
            var7 = var1.getlocal(0).__getattr__("traps").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(3818);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(3818);
                  var1.dellocal(6);
                  var3 = var10000;
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(3819);
                  var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("traps=[")._add(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(2)))._add(PyString.fromInterned("]")));
                  var1.setline(3820);
                  var7 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned(")"));
                  var1.f_lasti = -1;
                  return var7;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(7, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(3818);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(3818);
                  var1.getlocal(6).__call__(var2, var1.getlocal(7).__getattr__("__name__"));
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(3816);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(3816);
            var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("__name__"));
         }
      }
   }

   public PyObject clear_flags$157(PyFrame var1, ThreadState var2) {
      var1.setline(3823);
      PyString.fromInterned("Reset all flags to zero");
      var1.setline(3824);
      PyObject var3 = var1.getlocal(0).__getattr__("flags").__iter__();

      while(true) {
         var1.setline(3824);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(3825);
         PyInteger var5 = Py.newInteger(0);
         var1.getlocal(0).__getattr__("flags").__setitem__((PyObject)var1.getlocal(1), var5);
         var5 = null;
      }
   }

   public PyObject _shallow_copy$158(PyFrame var1, ThreadState var2) {
      var1.setline(3828);
      PyString.fromInterned("Returns a shallow copy from self.");
      var1.setline(3829);
      PyObject var10000 = var1.getglobal("Context");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("prec"), var1.getlocal(0).__getattr__("rounding"), var1.getlocal(0).__getattr__("traps"), var1.getlocal(0).__getattr__("flags"), var1.getlocal(0).__getattr__("Emin"), var1.getlocal(0).__getattr__("Emax"), var1.getlocal(0).__getattr__("capitals"), var1.getlocal(0).__getattr__("_clamp"), var1.getlocal(0).__getattr__("_ignored_flags")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(3832);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject copy$159(PyFrame var1, ThreadState var2) {
      var1.setline(3835);
      PyString.fromInterned("Returns a deep copy from self.");
      var1.setline(3836);
      PyObject var10000 = var1.getglobal("Context");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("prec"), var1.getlocal(0).__getattr__("rounding"), var1.getlocal(0).__getattr__("traps").__getattr__("copy").__call__(var2), var1.getlocal(0).__getattr__("flags").__getattr__("copy").__call__(var2), var1.getlocal(0).__getattr__("Emin"), var1.getlocal(0).__getattr__("Emax"), var1.getlocal(0).__getattr__("capitals"), var1.getlocal(0).__getattr__("_clamp"), var1.getlocal(0).__getattr__("_ignored_flags")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(3839);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _raise_error$160(PyFrame var1, ThreadState var2) {
      var1.setline(3849);
      PyString.fromInterned("Handles an error\n\n        If the flag is in _ignored_flags, returns the default response.\n        Otherwise, it sets the flag, then, if the corresponding\n        trap_enabler is set, it reraises the exception.  Otherwise, it returns\n        the default value after setting the flag.\n        ");
      var1.setline(3850);
      PyObject var3 = var1.getglobal("_condition_map").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3851);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_ignored_flags"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3853);
         var10000 = var1.getlocal(4).__call__(var2).__getattr__("handle");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0)};
         String[] var8 = new String[0];
         var10000 = var10000._callextra(var6, var8, var1.getlocal(3), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3855);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("flags").__setitem__((PyObject)var1.getlocal(4), var4);
         var4 = null;
         var1.setline(3856);
         if (var1.getlocal(0).__getattr__("traps").__getitem__(var1.getlocal(4)).__not__().__nonzero__()) {
            var1.setline(3858);
            var10000 = var1.getlocal(1).__call__(var2).__getattr__("handle");
            PyObject[] var7 = new PyObject[]{var1.getlocal(0)};
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3862);
            throw Py.makeException(var1.getlocal(4).__call__(var2, var1.getlocal(2)));
         }
      }
   }

   public PyObject _ignore_all_flags$161(PyFrame var1, ThreadState var2) {
      var1.setline(3865);
      PyString.fromInterned("Ignore all flags, if they are raised");
      var1.setline(3866);
      PyObject var10000 = var1.getlocal(0).__getattr__("_ignore_flags");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getglobal("_signals"), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _ignore_flags$162(PyFrame var1, ThreadState var2) {
      var1.setline(3869);
      PyString.fromInterned("Ignore the flags, if they are raised");
      var1.setline(3872);
      PyObject var3 = var1.getlocal(0).__getattr__("_ignored_flags")._add(var1.getglobal("list").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("_ignored_flags", var3);
      var3 = null;
      var1.setline(3873);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _regard_flags$163(PyFrame var1, ThreadState var2) {
      var1.setline(3876);
      PyString.fromInterned("Stop ignoring the flags, if they are raised");
      var1.setline(3877);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(0)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("list")})));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(3878);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(3879);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(3879);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(3880);
         var1.getlocal(0).__getattr__("_ignored_flags").__getattr__("remove").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject Etiny$164(PyFrame var1, ThreadState var2) {
      var1.setline(3886);
      PyString.fromInterned("Returns Etiny (= Emin - prec + 1)");
      var1.setline(3887);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("Emin")._sub(var1.getlocal(0).__getattr__("prec"))._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Etop$165(PyFrame var1, ThreadState var2) {
      var1.setline(3890);
      PyString.fromInterned("Returns maximum exponent (= Emax - prec + 1)");
      var1.setline(3891);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("Emax")._sub(var1.getlocal(0).__getattr__("prec"))._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_rounding$166(PyFrame var1, ThreadState var2) {
      var1.setline(3907);
      PyString.fromInterned("Sets the rounding type.\n\n        Sets the rounding type, and returns the current (previous)\n        rounding type.  Often used like:\n\n        context = context.copy()\n        # so you don't change the calling context\n        # if an error occurs in the middle.\n        rounding = context._set_rounding(ROUND_UP)\n        val = self.__sub__(other, context=context)\n        context._set_rounding(rounding)\n\n        This will make it round up for that operation.\n        ");
      var1.setline(3908);
      PyObject var3 = var1.getlocal(0).__getattr__("rounding");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3909);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("rounding", var3);
      var3 = null;
      var1.setline(3910);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject create_decimal$167(PyFrame var1, ThreadState var2) {
      var1.setline(3916);
      PyString.fromInterned("Creates a new Decimal instance but using self as context.\n\n        This method implements the to-number operation of the\n        IBM Decimal specification.");
      var1.setline(3918);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(1).__getattr__("strip").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(3919);
         var3 = var1.getlocal(0).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("ConversionSyntax"), (PyObject)PyString.fromInterned("no trailing or leading whitespace is permitted."));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(3923);
         var10000 = var1.getglobal("Decimal");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(0)};
         String[] var5 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         PyObject var6 = var10000;
         var1.setlocal(2, var6);
         var4 = null;
         var1.setline(3924);
         var10000 = var1.getlocal(2).__getattr__("_isnan").__call__(var2);
         if (var10000.__nonzero__()) {
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("_int"));
            var10000 = var6._gt(var1.getlocal(0).__getattr__("prec")._sub(var1.getlocal(0).__getattr__("_clamp")));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(3925);
            var3 = var1.getlocal(0).__getattr__("_raise_error").__call__((ThreadState)var2, (PyObject)var1.getglobal("ConversionSyntax"), (PyObject)PyString.fromInterned("diagnostic info too long in NaN"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(3927);
            var3 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject create_decimal_from_float$168(PyFrame var1, ThreadState var2) {
      var1.setline(3942);
      PyString.fromInterned("Creates a new Decimal instance from a float but rounding using self\n        as the context.\n\n        >>> context = Context(prec=5, rounding=ROUND_DOWN)\n        >>> context.create_decimal_from_float(3.1415926535897932)\n        Decimal('3.1415')\n        >>> context = Context(prec=5, traps=[Inexact])\n        >>> context.create_decimal_from_float(3.1415926535897932)\n        Traceback (most recent call last):\n            ...\n        Inexact: None\n\n        ");
      var1.setline(3943);
      PyObject var3 = var1.getglobal("Decimal").__getattr__("from_float").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3944);
      var3 = var1.getlocal(2).__getattr__("_fix").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject abs$169(PyFrame var1, ThreadState var2) {
      var1.setline(3964);
      PyString.fromInterned("Returns the absolute value of the operand.\n\n        If the operand is negative, the result is the same as using the minus\n        operation on the operand.  Otherwise, the result is the same as using\n        the plus operation on the operand.\n\n        >>> ExtendedContext.abs(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.abs(Decimal('-100'))\n        Decimal('100')\n        >>> ExtendedContext.abs(Decimal('101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.abs(Decimal('-101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.abs(-1)\n        Decimal('1')\n        ");
      var1.setline(3965);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(3966);
      var10000 = var1.getlocal(1).__getattr__("__abs__");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add$170(PyFrame var1, ThreadState var2) {
      var1.setline(3981);
      PyString.fromInterned("Return the sum of the two operands.\n\n        >>> ExtendedContext.add(Decimal('12'), Decimal('7.00'))\n        Decimal('19.00')\n        >>> ExtendedContext.add(Decimal('1E+2'), Decimal('1.01E+4'))\n        Decimal('1.02E+4')\n        >>> ExtendedContext.add(1, Decimal(2))\n        Decimal('3')\n        >>> ExtendedContext.add(Decimal(8), 5)\n        Decimal('13')\n        >>> ExtendedContext.add(5, 5)\n        Decimal('10')\n        ");
      var1.setline(3982);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(3983);
      var10000 = var1.getlocal(1).__getattr__("__add__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(3984);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3985);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(3987);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _apply$171(PyFrame var1, ThreadState var2) {
      var1.setline(3990);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("_fix").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject canonical$172(PyFrame var1, ThreadState var2) {
      var1.setline(4000);
      PyString.fromInterned("Returns the same Decimal object.\n\n        As we do not have different encodings for the same number, the\n        received object already is in its canonical form.\n\n        >>> ExtendedContext.canonical(Decimal('2.50'))\n        Decimal('2.50')\n        ");
      var1.setline(4001);
      PyObject var10000 = var1.getlocal(1).__getattr__("canonical");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject compare$173(PyFrame var1, ThreadState var2) {
      var1.setline(4035);
      PyString.fromInterned("Compares values numerically.\n\n        If the signs of the operands differ, a value representing each operand\n        ('-1' if the operand is less than zero, '0' if the operand is zero or\n        negative zero, or '1' if the operand is greater than zero) is used in\n        place of that operand for the comparison instead of the actual\n        operand.\n\n        The comparison is then effected by subtracting the second operand from\n        the first and then returning a value according to the result of the\n        subtraction: '-1' if the result is less than zero, '0' if the result is\n        zero or negative zero, or '1' if the result is greater than zero.\n\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.1'))\n        Decimal('0')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.10'))\n        Decimal('0')\n        >>> ExtendedContext.compare(Decimal('3'), Decimal('2.1'))\n        Decimal('1')\n        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('-3'))\n        Decimal('1')\n        >>> ExtendedContext.compare(Decimal('-3'), Decimal('2.1'))\n        Decimal('-1')\n        >>> ExtendedContext.compare(1, 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare(Decimal(1), 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare(1, Decimal(2))\n        Decimal('-1')\n        ");
      var1.setline(4036);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4037);
      var10000 = var1.getlocal(1).__getattr__("compare");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject compare_signal$174(PyFrame var1, ThreadState var2) {
      var1.setline(4070);
      PyString.fromInterned("Compares the values of the two operands numerically.\n\n        It's pretty much like compare(), but all NaNs signal, with signaling\n        NaNs taking precedence over quiet NaNs.\n\n        >>> c = ExtendedContext\n        >>> c.compare_signal(Decimal('2.1'), Decimal('3'))\n        Decimal('-1')\n        >>> c.compare_signal(Decimal('2.1'), Decimal('2.1'))\n        Decimal('0')\n        >>> c.flags[InvalidOperation] = 0\n        >>> print c.flags[InvalidOperation]\n        0\n        >>> c.compare_signal(Decimal('NaN'), Decimal('2.1'))\n        Decimal('NaN')\n        >>> print c.flags[InvalidOperation]\n        1\n        >>> c.flags[InvalidOperation] = 0\n        >>> print c.flags[InvalidOperation]\n        0\n        >>> c.compare_signal(Decimal('sNaN'), Decimal('2.1'))\n        Decimal('NaN')\n        >>> print c.flags[InvalidOperation]\n        1\n        >>> c.compare_signal(-1, 2)\n        Decimal('-1')\n        >>> c.compare_signal(Decimal(-1), 2)\n        Decimal('-1')\n        >>> c.compare_signal(-1, Decimal(2))\n        Decimal('-1')\n        ");
      var1.setline(4071);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4072);
      var10000 = var1.getlocal(1).__getattr__("compare_signal");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject compare_total$175(PyFrame var1, ThreadState var2) {
      var1.setline(4099);
      PyString.fromInterned("Compares two operands using their abstract representation.\n\n        This is not like the standard compare, which use their numerical\n        value. Note that a total ordering is defined for all possible abstract\n        representations.\n\n        >>> ExtendedContext.compare_total(Decimal('12.73'), Decimal('127.9'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('-127'),  Decimal('12'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.3'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.30'))\n        Decimal('0')\n        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('12.300'))\n        Decimal('1')\n        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('NaN'))\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(1, 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(Decimal(1), 2)\n        Decimal('-1')\n        >>> ExtendedContext.compare_total(1, Decimal(2))\n        Decimal('-1')\n        ");
      var1.setline(4100);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4101);
      var5 = var1.getlocal(1).__getattr__("compare_total").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject compare_total_mag$176(PyFrame var1, ThreadState var2) {
      var1.setline(4107);
      PyString.fromInterned("Compares two operands using their abstract representation ignoring sign.\n\n        Like compare_total, but with operand's sign ignored and assumed to be 0.\n        ");
      var1.setline(4108);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4109);
      var5 = var1.getlocal(1).__getattr__("compare_total_mag").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_abs$177(PyFrame var1, ThreadState var2) {
      var1.setline(4120);
      PyString.fromInterned("Returns a copy of the operand with the sign set to 0.\n\n        >>> ExtendedContext.copy_abs(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.copy_abs(Decimal('-100'))\n        Decimal('100')\n        >>> ExtendedContext.copy_abs(-1)\n        Decimal('1')\n        ");
      var1.setline(4121);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4122);
      var5 = var1.getlocal(1).__getattr__("copy_abs").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_decimal$178(PyFrame var1, ThreadState var2) {
      var1.setline(4133);
      PyString.fromInterned("Returns a copy of the decimal object.\n\n        >>> ExtendedContext.copy_decimal(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.copy_decimal(Decimal('-1.00'))\n        Decimal('-1.00')\n        >>> ExtendedContext.copy_decimal(1)\n        Decimal('1')\n        ");
      var1.setline(4134);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4135);
      var5 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_negate$179(PyFrame var1, ThreadState var2) {
      var1.setline(4146);
      PyString.fromInterned("Returns a copy of the operand with the sign inverted.\n\n        >>> ExtendedContext.copy_negate(Decimal('101.5'))\n        Decimal('-101.5')\n        >>> ExtendedContext.copy_negate(Decimal('-101.5'))\n        Decimal('101.5')\n        >>> ExtendedContext.copy_negate(1)\n        Decimal('-1')\n        ");
      var1.setline(4147);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4148);
      var5 = var1.getlocal(1).__getattr__("copy_negate").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_sign$180(PyFrame var1, ThreadState var2) {
      var1.setline(4170);
      PyString.fromInterned("Copies the second operand's sign to the first one.\n\n        In detail, it returns a copy of the first operand with the sign\n        equal to the sign of the second operand.\n\n        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('7.33'))\n        Decimal('1.50')\n        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('7.33'))\n        Decimal('1.50')\n        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('-7.33'))\n        Decimal('-1.50')\n        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('-7.33'))\n        Decimal('-1.50')\n        >>> ExtendedContext.copy_sign(1, -2)\n        Decimal('-1')\n        >>> ExtendedContext.copy_sign(Decimal(1), -2)\n        Decimal('-1')\n        >>> ExtendedContext.copy_sign(1, Decimal(-2))\n        Decimal('-1')\n        ");
      var1.setline(4171);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4172);
      var5 = var1.getlocal(1).__getattr__("copy_sign").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject divide$181(PyFrame var1, ThreadState var2) {
      var1.setline(4203);
      PyString.fromInterned("Decimal division in a specified context.\n\n        >>> ExtendedContext.divide(Decimal('1'), Decimal('3'))\n        Decimal('0.333333333')\n        >>> ExtendedContext.divide(Decimal('2'), Decimal('3'))\n        Decimal('0.666666667')\n        >>> ExtendedContext.divide(Decimal('5'), Decimal('2'))\n        Decimal('2.5')\n        >>> ExtendedContext.divide(Decimal('1'), Decimal('10'))\n        Decimal('0.1')\n        >>> ExtendedContext.divide(Decimal('12'), Decimal('12'))\n        Decimal('1')\n        >>> ExtendedContext.divide(Decimal('8.00'), Decimal('2'))\n        Decimal('4.00')\n        >>> ExtendedContext.divide(Decimal('2.400'), Decimal('2.0'))\n        Decimal('1.20')\n        >>> ExtendedContext.divide(Decimal('1000'), Decimal('100'))\n        Decimal('10')\n        >>> ExtendedContext.divide(Decimal('1000'), Decimal('1'))\n        Decimal('1000')\n        >>> ExtendedContext.divide(Decimal('2.40E+6'), Decimal('2'))\n        Decimal('1.20E+6')\n        >>> ExtendedContext.divide(5, 5)\n        Decimal('1')\n        >>> ExtendedContext.divide(Decimal(5), 5)\n        Decimal('1')\n        >>> ExtendedContext.divide(5, Decimal(5))\n        Decimal('1')\n        ");
      var1.setline(4204);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4205);
      var10000 = var1.getlocal(1).__getattr__("__div__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(4206);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(4207);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(4209);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject divide_int$182(PyFrame var1, ThreadState var2) {
      var1.setline(4226);
      PyString.fromInterned("Divides two numbers and returns the integer part of the result.\n\n        >>> ExtendedContext.divide_int(Decimal('2'), Decimal('3'))\n        Decimal('0')\n        >>> ExtendedContext.divide_int(Decimal('10'), Decimal('3'))\n        Decimal('3')\n        >>> ExtendedContext.divide_int(Decimal('1'), Decimal('0.3'))\n        Decimal('3')\n        >>> ExtendedContext.divide_int(10, 3)\n        Decimal('3')\n        >>> ExtendedContext.divide_int(Decimal(10), 3)\n        Decimal('3')\n        >>> ExtendedContext.divide_int(10, Decimal(3))\n        Decimal('3')\n        ");
      var1.setline(4227);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4228);
      var10000 = var1.getlocal(1).__getattr__("__floordiv__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(4229);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(4230);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(4232);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject divmod$183(PyFrame var1, ThreadState var2) {
      var1.setline(4247);
      PyString.fromInterned("Return (a // b, a % b).\n\n        >>> ExtendedContext.divmod(Decimal(8), Decimal(3))\n        (Decimal('2'), Decimal('2'))\n        >>> ExtendedContext.divmod(Decimal(8), Decimal(4))\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(8, 4)\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(Decimal(8), 4)\n        (Decimal('2'), Decimal('0'))\n        >>> ExtendedContext.divmod(8, Decimal(4))\n        (Decimal('2'), Decimal('0'))\n        ");
      var1.setline(4248);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4249);
      var10000 = var1.getlocal(1).__getattr__("__divmod__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(4250);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(4251);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(4253);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject exp$184(PyFrame var1, ThreadState var2) {
      var1.setline(4275);
      PyString.fromInterned("Returns e ** a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.exp(Decimal('-Infinity'))\n        Decimal('0')\n        >>> c.exp(Decimal('-1'))\n        Decimal('0.367879441')\n        >>> c.exp(Decimal('0'))\n        Decimal('1')\n        >>> c.exp(Decimal('1'))\n        Decimal('2.71828183')\n        >>> c.exp(Decimal('0.693147181'))\n        Decimal('2.00000000')\n        >>> c.exp(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.exp(10)\n        Decimal('22026.4658')\n        ");
      var1.setline(4276);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4277);
      var10000 = var1.getlocal(1).__getattr__("exp");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject fma$185(PyFrame var1, ThreadState var2) {
      var1.setline(4298);
      PyString.fromInterned("Returns a multiplied by b, plus c.\n\n        The first two operands are multiplied together, using multiply,\n        the third operand is then added to the result of that\n        multiplication, using add, all with only one final rounding.\n\n        >>> ExtendedContext.fma(Decimal('3'), Decimal('5'), Decimal('7'))\n        Decimal('22')\n        >>> ExtendedContext.fma(Decimal('3'), Decimal('-5'), Decimal('7'))\n        Decimal('-8')\n        >>> ExtendedContext.fma(Decimal('888565290'), Decimal('1557.96930'), Decimal('-86087.7578'))\n        Decimal('1.38435736E+12')\n        >>> ExtendedContext.fma(1, 3, 4)\n        Decimal('7')\n        >>> ExtendedContext.fma(1, Decimal(3), 4)\n        Decimal('7')\n        >>> ExtendedContext.fma(1, 3, Decimal(4))\n        Decimal('7')\n        ");
      var1.setline(4299);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4300);
      var10000 = var1.getlocal(1).__getattr__("fma");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_canonical$186(PyFrame var1, ThreadState var2) {
      var1.setline(4310);
      PyString.fromInterned("Return True if the operand is canonical; otherwise return False.\n\n        Currently, the encoding of a Decimal instance is always\n        canonical, so this method returns True for any Decimal.\n\n        >>> ExtendedContext.is_canonical(Decimal('2.50'))\n        True\n        ");
      var1.setline(4311);
      PyObject var3 = var1.getlocal(1).__getattr__("is_canonical").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_finite$187(PyFrame var1, ThreadState var2) {
      var1.setline(4331);
      PyString.fromInterned("Return True if the operand is finite; otherwise return False.\n\n        A Decimal instance is considered finite if it is neither\n        infinite nor a NaN.\n\n        >>> ExtendedContext.is_finite(Decimal('2.50'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('-0.3'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('0'))\n        True\n        >>> ExtendedContext.is_finite(Decimal('Inf'))\n        False\n        >>> ExtendedContext.is_finite(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_finite(1)\n        True\n        ");
      var1.setline(4332);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4333);
      var5 = var1.getlocal(1).__getattr__("is_finite").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_infinite$188(PyFrame var1, ThreadState var2) {
      var1.setline(4346);
      PyString.fromInterned("Return True if the operand is infinite; otherwise return False.\n\n        >>> ExtendedContext.is_infinite(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_infinite(Decimal('-Inf'))\n        True\n        >>> ExtendedContext.is_infinite(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_infinite(1)\n        False\n        ");
      var1.setline(4347);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4348);
      var5 = var1.getlocal(1).__getattr__("is_infinite").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_nan$189(PyFrame var1, ThreadState var2) {
      var1.setline(4362);
      PyString.fromInterned("Return True if the operand is a qNaN or sNaN;\n        otherwise return False.\n\n        >>> ExtendedContext.is_nan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_nan(Decimal('NaN'))\n        True\n        >>> ExtendedContext.is_nan(Decimal('-sNaN'))\n        True\n        >>> ExtendedContext.is_nan(1)\n        False\n        ");
      var1.setline(4363);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4364);
      var5 = var1.getlocal(1).__getattr__("is_nan").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_normal$190(PyFrame var1, ThreadState var2) {
      var1.setline(4385);
      PyString.fromInterned("Return True if the operand is a normal number;\n        otherwise return False.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.is_normal(Decimal('2.50'))\n        True\n        >>> c.is_normal(Decimal('0.1E-999'))\n        False\n        >>> c.is_normal(Decimal('0.00'))\n        False\n        >>> c.is_normal(Decimal('-Inf'))\n        False\n        >>> c.is_normal(Decimal('NaN'))\n        False\n        >>> c.is_normal(1)\n        True\n        ");
      var1.setline(4386);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4387);
      var10000 = var1.getlocal(1).__getattr__("is_normal");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_qnan$191(PyFrame var1, ThreadState var2) {
      var1.setline(4400);
      PyString.fromInterned("Return True if the operand is a quiet NaN; otherwise return False.\n\n        >>> ExtendedContext.is_qnan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_qnan(Decimal('NaN'))\n        True\n        >>> ExtendedContext.is_qnan(Decimal('sNaN'))\n        False\n        >>> ExtendedContext.is_qnan(1)\n        False\n        ");
      var1.setline(4401);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4402);
      var5 = var1.getlocal(1).__getattr__("is_qnan").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_signed$192(PyFrame var1, ThreadState var2) {
      var1.setline(4417);
      PyString.fromInterned("Return True if the operand is negative; otherwise return False.\n\n        >>> ExtendedContext.is_signed(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_signed(Decimal('-12'))\n        True\n        >>> ExtendedContext.is_signed(Decimal('-0'))\n        True\n        >>> ExtendedContext.is_signed(8)\n        False\n        >>> ExtendedContext.is_signed(-8)\n        True\n        ");
      var1.setline(4418);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4419);
      var5 = var1.getlocal(1).__getattr__("is_signed").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_snan$193(PyFrame var1, ThreadState var2) {
      var1.setline(4433);
      PyString.fromInterned("Return True if the operand is a signaling NaN;\n        otherwise return False.\n\n        >>> ExtendedContext.is_snan(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_snan(Decimal('NaN'))\n        False\n        >>> ExtendedContext.is_snan(Decimal('sNaN'))\n        True\n        >>> ExtendedContext.is_snan(1)\n        False\n        ");
      var1.setline(4434);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4435);
      var5 = var1.getlocal(1).__getattr__("is_snan").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_subnormal$194(PyFrame var1, ThreadState var2) {
      var1.setline(4455);
      PyString.fromInterned("Return True if the operand is subnormal; otherwise return False.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.is_subnormal(Decimal('2.50'))\n        False\n        >>> c.is_subnormal(Decimal('0.1E-999'))\n        True\n        >>> c.is_subnormal(Decimal('0.00'))\n        False\n        >>> c.is_subnormal(Decimal('-Inf'))\n        False\n        >>> c.is_subnormal(Decimal('NaN'))\n        False\n        >>> c.is_subnormal(1)\n        False\n        ");
      var1.setline(4456);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4457);
      var10000 = var1.getlocal(1).__getattr__("is_subnormal");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject is_zero$195(PyFrame var1, ThreadState var2) {
      var1.setline(4472);
      PyString.fromInterned("Return True if the operand is a zero; otherwise return False.\n\n        >>> ExtendedContext.is_zero(Decimal('0'))\n        True\n        >>> ExtendedContext.is_zero(Decimal('2.50'))\n        False\n        >>> ExtendedContext.is_zero(Decimal('-0E+2'))\n        True\n        >>> ExtendedContext.is_zero(1)\n        False\n        >>> ExtendedContext.is_zero(0)\n        True\n        ");
      var1.setline(4473);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4474);
      var5 = var1.getlocal(1).__getattr__("is_zero").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject ln$196(PyFrame var1, ThreadState var2) {
      var1.setline(4494);
      PyString.fromInterned("Returns the natural (base e) logarithm of the operand.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.ln(Decimal('0'))\n        Decimal('-Infinity')\n        >>> c.ln(Decimal('1.000'))\n        Decimal('0')\n        >>> c.ln(Decimal('2.71828183'))\n        Decimal('1.00000000')\n        >>> c.ln(Decimal('10'))\n        Decimal('2.30258509')\n        >>> c.ln(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.ln(1)\n        Decimal('0')\n        ");
      var1.setline(4495);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4496);
      var10000 = var1.getlocal(1).__getattr__("ln");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject log10$197(PyFrame var1, ThreadState var2) {
      var1.setline(4522);
      PyString.fromInterned("Returns the base 10 logarithm of the operand.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.log10(Decimal('0'))\n        Decimal('-Infinity')\n        >>> c.log10(Decimal('0.001'))\n        Decimal('-3')\n        >>> c.log10(Decimal('1.000'))\n        Decimal('0')\n        >>> c.log10(Decimal('2'))\n        Decimal('0.301029996')\n        >>> c.log10(Decimal('10'))\n        Decimal('1')\n        >>> c.log10(Decimal('70'))\n        Decimal('1.84509804')\n        >>> c.log10(Decimal('+Infinity'))\n        Decimal('Infinity')\n        >>> c.log10(0)\n        Decimal('-Infinity')\n        >>> c.log10(1)\n        Decimal('0')\n        ");
      var1.setline(4523);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4524);
      var10000 = var1.getlocal(1).__getattr__("log10");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject logb$198(PyFrame var1, ThreadState var2) {
      var1.setline(4548);
      PyString.fromInterned(" Returns the exponent of the magnitude of the operand's MSD.\n\n        The result is the integer which is the exponent of the magnitude\n        of the most significant digit of the operand (as though the\n        operand were truncated to a single digit while maintaining the\n        value of that digit and without limiting the resulting exponent).\n\n        >>> ExtendedContext.logb(Decimal('250'))\n        Decimal('2')\n        >>> ExtendedContext.logb(Decimal('2.50'))\n        Decimal('0')\n        >>> ExtendedContext.logb(Decimal('0.03'))\n        Decimal('-2')\n        >>> ExtendedContext.logb(Decimal('0'))\n        Decimal('-Infinity')\n        >>> ExtendedContext.logb(1)\n        Decimal('0')\n        >>> ExtendedContext.logb(10)\n        Decimal('1')\n        >>> ExtendedContext.logb(100)\n        Decimal('2')\n        ");
      var1.setline(4549);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4550);
      var10000 = var1.getlocal(1).__getattr__("logb");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject logical_and$199(PyFrame var1, ThreadState var2) {
      var1.setline(4575);
      PyString.fromInterned("Applies the logical operation 'and' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_and(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('0'), Decimal('1'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('1'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_and(Decimal('1'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_and(Decimal('1100'), Decimal('1010'))\n        Decimal('1000')\n        >>> ExtendedContext.logical_and(Decimal('1111'), Decimal('10'))\n        Decimal('10')\n        >>> ExtendedContext.logical_and(110, 1101)\n        Decimal('100')\n        >>> ExtendedContext.logical_and(Decimal(110), 1101)\n        Decimal('100')\n        >>> ExtendedContext.logical_and(110, Decimal(1101))\n        Decimal('100')\n        ");
      var1.setline(4576);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4577);
      var10000 = var1.getlocal(1).__getattr__("logical_and");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject logical_invert$200(PyFrame var1, ThreadState var2) {
      var1.setline(4594);
      PyString.fromInterned("Invert all the digits in the operand.\n\n        The operand must be a logical number.\n\n        >>> ExtendedContext.logical_invert(Decimal('0'))\n        Decimal('111111111')\n        >>> ExtendedContext.logical_invert(Decimal('1'))\n        Decimal('111111110')\n        >>> ExtendedContext.logical_invert(Decimal('111111111'))\n        Decimal('0')\n        >>> ExtendedContext.logical_invert(Decimal('101010101'))\n        Decimal('10101010')\n        >>> ExtendedContext.logical_invert(1101)\n        Decimal('111110010')\n        ");
      var1.setline(4595);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4596);
      var10000 = var1.getlocal(1).__getattr__("logical_invert");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject logical_or$201(PyFrame var1, ThreadState var2) {
      var1.setline(4621);
      PyString.fromInterned("Applies the logical operation 'or' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_or(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_or(Decimal('0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1'), Decimal('0'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_or(Decimal('1100'), Decimal('1010'))\n        Decimal('1110')\n        >>> ExtendedContext.logical_or(Decimal('1110'), Decimal('10'))\n        Decimal('1110')\n        >>> ExtendedContext.logical_or(110, 1101)\n        Decimal('1111')\n        >>> ExtendedContext.logical_or(Decimal(110), 1101)\n        Decimal('1111')\n        >>> ExtendedContext.logical_or(110, Decimal(1101))\n        Decimal('1111')\n        ");
      var1.setline(4622);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4623);
      var10000 = var1.getlocal(1).__getattr__("logical_or");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject logical_xor$202(PyFrame var1, ThreadState var2) {
      var1.setline(4648);
      PyString.fromInterned("Applies the logical operation 'xor' between each operand's digits.\n\n        The operands must be both logical numbers.\n\n        >>> ExtendedContext.logical_xor(Decimal('0'), Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.logical_xor(Decimal('0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.logical_xor(Decimal('1'), Decimal('0'))\n        Decimal('1')\n        >>> ExtendedContext.logical_xor(Decimal('1'), Decimal('1'))\n        Decimal('0')\n        >>> ExtendedContext.logical_xor(Decimal('1100'), Decimal('1010'))\n        Decimal('110')\n        >>> ExtendedContext.logical_xor(Decimal('1111'), Decimal('10'))\n        Decimal('1101')\n        >>> ExtendedContext.logical_xor(110, 1101)\n        Decimal('1011')\n        >>> ExtendedContext.logical_xor(Decimal(110), 1101)\n        Decimal('1011')\n        >>> ExtendedContext.logical_xor(110, Decimal(1101))\n        Decimal('1011')\n        ");
      var1.setline(4649);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4650);
      var10000 = var1.getlocal(1).__getattr__("logical_xor");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject max$203(PyFrame var1, ThreadState var2) {
      var1.setline(4675);
      PyString.fromInterned("max compares two values numerically and returns the maximum.\n\n        If either operand is a NaN then the general rules apply.\n        Otherwise, the operands are compared as though by the compare\n        operation.  If they are numerically equal then the left-hand operand\n        is chosen as the result.  Otherwise the maximum (closer to positive\n        infinity) of the two operands is chosen as the result.\n\n        >>> ExtendedContext.max(Decimal('3'), Decimal('2'))\n        Decimal('3')\n        >>> ExtendedContext.max(Decimal('-10'), Decimal('3'))\n        Decimal('3')\n        >>> ExtendedContext.max(Decimal('1.0'), Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.max(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.max(1, 2)\n        Decimal('2')\n        >>> ExtendedContext.max(Decimal(1), 2)\n        Decimal('2')\n        >>> ExtendedContext.max(1, Decimal(2))\n        Decimal('2')\n        ");
      var1.setline(4676);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4677);
      var10000 = var1.getlocal(1).__getattr__("max");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject max_mag$204(PyFrame var1, ThreadState var2) {
      var1.setline(4692);
      PyString.fromInterned("Compares the values numerically with their sign ignored.\n\n        >>> ExtendedContext.max_mag(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.max_mag(Decimal('7'), Decimal('-10'))\n        Decimal('-10')\n        >>> ExtendedContext.max_mag(1, -2)\n        Decimal('-2')\n        >>> ExtendedContext.max_mag(Decimal(1), -2)\n        Decimal('-2')\n        >>> ExtendedContext.max_mag(1, Decimal(-2))\n        Decimal('-2')\n        ");
      var1.setline(4693);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4694);
      var10000 = var1.getlocal(1).__getattr__("max_mag");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject min$205(PyFrame var1, ThreadState var2) {
      var1.setline(4719);
      PyString.fromInterned("min compares two values numerically and returns the minimum.\n\n        If either operand is a NaN then the general rules apply.\n        Otherwise, the operands are compared as though by the compare\n        operation.  If they are numerically equal then the left-hand operand\n        is chosen as the result.  Otherwise the minimum (closer to negative\n        infinity) of the two operands is chosen as the result.\n\n        >>> ExtendedContext.min(Decimal('3'), Decimal('2'))\n        Decimal('2')\n        >>> ExtendedContext.min(Decimal('-10'), Decimal('3'))\n        Decimal('-10')\n        >>> ExtendedContext.min(Decimal('1.0'), Decimal('1'))\n        Decimal('1.0')\n        >>> ExtendedContext.min(Decimal('7'), Decimal('NaN'))\n        Decimal('7')\n        >>> ExtendedContext.min(1, 2)\n        Decimal('1')\n        >>> ExtendedContext.min(Decimal(1), 2)\n        Decimal('1')\n        >>> ExtendedContext.min(1, Decimal(29))\n        Decimal('1')\n        ");
      var1.setline(4720);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4721);
      var10000 = var1.getlocal(1).__getattr__("min");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject min_mag$206(PyFrame var1, ThreadState var2) {
      var1.setline(4736);
      PyString.fromInterned("Compares the values numerically with their sign ignored.\n\n        >>> ExtendedContext.min_mag(Decimal('3'), Decimal('-2'))\n        Decimal('-2')\n        >>> ExtendedContext.min_mag(Decimal('-3'), Decimal('NaN'))\n        Decimal('-3')\n        >>> ExtendedContext.min_mag(1, -2)\n        Decimal('1')\n        >>> ExtendedContext.min_mag(Decimal(1), -2)\n        Decimal('1')\n        >>> ExtendedContext.min_mag(1, Decimal(-2))\n        Decimal('1')\n        ");
      var1.setline(4737);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4738);
      var10000 = var1.getlocal(1).__getattr__("min_mag");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject minus$207(PyFrame var1, ThreadState var2) {
      var1.setline(4753);
      PyString.fromInterned("Minus corresponds to unary prefix minus in Python.\n\n        The operation is evaluated using the same rules as subtract; the\n        operation minus(a) is calculated as subtract('0', a) where the '0'\n        has the same exponent as the operand.\n\n        >>> ExtendedContext.minus(Decimal('1.3'))\n        Decimal('-1.3')\n        >>> ExtendedContext.minus(Decimal('-1.3'))\n        Decimal('1.3')\n        >>> ExtendedContext.minus(1)\n        Decimal('-1')\n        ");
      var1.setline(4754);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4755);
      var10000 = var1.getlocal(1).__getattr__("__neg__");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject multiply$208(PyFrame var1, ThreadState var2) {
      var1.setline(4781);
      PyString.fromInterned("multiply multiplies two operands.\n\n        If either operand is a special value then the general rules apply.\n        Otherwise, the operands are multiplied together\n        ('long multiplication'), resulting in a number which may be as long as\n        the sum of the lengths of the two operands.\n\n        >>> ExtendedContext.multiply(Decimal('1.20'), Decimal('3'))\n        Decimal('3.60')\n        >>> ExtendedContext.multiply(Decimal('7'), Decimal('3'))\n        Decimal('21')\n        >>> ExtendedContext.multiply(Decimal('0.9'), Decimal('0.8'))\n        Decimal('0.72')\n        >>> ExtendedContext.multiply(Decimal('0.9'), Decimal('-0'))\n        Decimal('-0.0')\n        >>> ExtendedContext.multiply(Decimal('654321'), Decimal('654321'))\n        Decimal('4.28135971E+11')\n        >>> ExtendedContext.multiply(7, 7)\n        Decimal('49')\n        >>> ExtendedContext.multiply(Decimal(7), 7)\n        Decimal('49')\n        >>> ExtendedContext.multiply(7, Decimal(7))\n        Decimal('49')\n        ");
      var1.setline(4782);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4783);
      var10000 = var1.getlocal(1).__getattr__("__mul__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(4784);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(4785);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(4787);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject next_minus$209(PyFrame var1, ThreadState var2) {
      var1.setline(4805);
      PyString.fromInterned("Returns the largest representable number smaller than a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> ExtendedContext.next_minus(Decimal('1'))\n        Decimal('0.999999999')\n        >>> c.next_minus(Decimal('1E-1007'))\n        Decimal('0E-1007')\n        >>> ExtendedContext.next_minus(Decimal('-1.00000003'))\n        Decimal('-1.00000004')\n        >>> c.next_minus(Decimal('Infinity'))\n        Decimal('9.99999999E+999')\n        >>> c.next_minus(1)\n        Decimal('0.999999999')\n        ");
      var1.setline(4806);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4807);
      var10000 = var1.getlocal(1).__getattr__("next_minus");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject next_plus$210(PyFrame var1, ThreadState var2) {
      var1.setline(4825);
      PyString.fromInterned("Returns the smallest representable number larger than a.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> ExtendedContext.next_plus(Decimal('1'))\n        Decimal('1.00000001')\n        >>> c.next_plus(Decimal('-1E-1007'))\n        Decimal('-0E-1007')\n        >>> ExtendedContext.next_plus(Decimal('-1.00000003'))\n        Decimal('-1.00000002')\n        >>> c.next_plus(Decimal('-Infinity'))\n        Decimal('-9.99999999E+999')\n        >>> c.next_plus(1)\n        Decimal('1.00000001')\n        ");
      var1.setline(4826);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4827);
      var10000 = var1.getlocal(1).__getattr__("next_plus");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject next_toward$211(PyFrame var1, ThreadState var2) {
      var1.setline(4860);
      PyString.fromInterned("Returns the number closest to a, in direction towards b.\n\n        The result is the closest representable number from the first\n        operand (but not the first operand) that is in the direction\n        towards the second operand, unless the operands have the same\n        value.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.next_toward(Decimal('1'), Decimal('2'))\n        Decimal('1.00000001')\n        >>> c.next_toward(Decimal('-1E-1007'), Decimal('1'))\n        Decimal('-0E-1007')\n        >>> c.next_toward(Decimal('-1.00000003'), Decimal('0'))\n        Decimal('-1.00000002')\n        >>> c.next_toward(Decimal('1'), Decimal('0'))\n        Decimal('0.999999999')\n        >>> c.next_toward(Decimal('1E-1007'), Decimal('-100'))\n        Decimal('0E-1007')\n        >>> c.next_toward(Decimal('-1.00000003'), Decimal('-10'))\n        Decimal('-1.00000004')\n        >>> c.next_toward(Decimal('0.00'), Decimal('-0.0000'))\n        Decimal('-0.00')\n        >>> c.next_toward(0, 1)\n        Decimal('1E-1007')\n        >>> c.next_toward(Decimal(0), 1)\n        Decimal('1E-1007')\n        >>> c.next_toward(0, Decimal(1))\n        Decimal('1E-1007')\n        ");
      var1.setline(4861);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4862);
      var10000 = var1.getlocal(1).__getattr__("next_toward");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject normalize$212(PyFrame var1, ThreadState var2) {
      var1.setline(4884);
      PyString.fromInterned("normalize reduces an operand to its simplest form.\n\n        Essentially a plus operation with all trailing zeros removed from the\n        result.\n\n        >>> ExtendedContext.normalize(Decimal('2.1'))\n        Decimal('2.1')\n        >>> ExtendedContext.normalize(Decimal('-2.0'))\n        Decimal('-2')\n        >>> ExtendedContext.normalize(Decimal('1.200'))\n        Decimal('1.2')\n        >>> ExtendedContext.normalize(Decimal('-120'))\n        Decimal('-1.2E+2')\n        >>> ExtendedContext.normalize(Decimal('120.00'))\n        Decimal('1.2E+2')\n        >>> ExtendedContext.normalize(Decimal('0.00'))\n        Decimal('0')\n        >>> ExtendedContext.normalize(6)\n        Decimal('6')\n        ");
      var1.setline(4885);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4886);
      var10000 = var1.getlocal(1).__getattr__("normalize");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject number_class$213(PyFrame var1, ThreadState var2) {
      var1.setline(4934);
      PyString.fromInterned("Returns an indication of the class of the operand.\n\n        The class is one of the following strings:\n          -sNaN\n          -NaN\n          -Infinity\n          -Normal\n          -Subnormal\n          -Zero\n          +Zero\n          +Subnormal\n          +Normal\n          +Infinity\n\n        >>> c = Context(ExtendedContext)\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.number_class(Decimal('Infinity'))\n        '+Infinity'\n        >>> c.number_class(Decimal('1E-10'))\n        '+Normal'\n        >>> c.number_class(Decimal('2.50'))\n        '+Normal'\n        >>> c.number_class(Decimal('0.1E-999'))\n        '+Subnormal'\n        >>> c.number_class(Decimal('0'))\n        '+Zero'\n        >>> c.number_class(Decimal('-0'))\n        '-Zero'\n        >>> c.number_class(Decimal('-0.1E-999'))\n        '-Subnormal'\n        >>> c.number_class(Decimal('-1E-10'))\n        '-Normal'\n        >>> c.number_class(Decimal('-2.50'))\n        '-Normal'\n        >>> c.number_class(Decimal('-Infinity'))\n        '-Infinity'\n        >>> c.number_class(Decimal('NaN'))\n        'NaN'\n        >>> c.number_class(Decimal('-NaN'))\n        'NaN'\n        >>> c.number_class(Decimal('sNaN'))\n        'sNaN'\n        >>> c.number_class(123)\n        '+Normal'\n        ");
      var1.setline(4935);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4936);
      var10000 = var1.getlocal(1).__getattr__("number_class");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject plus$214(PyFrame var1, ThreadState var2) {
      var1.setline(4951);
      PyString.fromInterned("Plus corresponds to unary prefix plus in Python.\n\n        The operation is evaluated using the same rules as add; the\n        operation plus(a) is calculated as add('0', a) where the '0'\n        has the same exponent as the operand.\n\n        >>> ExtendedContext.plus(Decimal('1.3'))\n        Decimal('1.3')\n        >>> ExtendedContext.plus(Decimal('-1.3'))\n        Decimal('-1.3')\n        >>> ExtendedContext.plus(-1)\n        Decimal('-1')\n        ");
      var1.setline(4952);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(4953);
      var10000 = var1.getlocal(1).__getattr__("__pos__");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject power$215(PyFrame var1, ThreadState var2) {
      var1.setline(5027);
      PyString.fromInterned("Raises a to the power of b, to modulo if given.\n\n        With two arguments, compute a**b.  If a is negative then b\n        must be integral.  The result will be inexact unless b is\n        integral and the result is finite and can be expressed exactly\n        in 'precision' digits.\n\n        With three arguments, compute (a**b) % modulo.  For the\n        three argument form, the following restrictions on the\n        arguments hold:\n\n         - all three arguments must be integral\n         - b must be nonnegative\n         - at least one of a or b must be nonzero\n         - modulo must be nonzero and have at most 'precision' digits\n\n        The result of pow(a, b, modulo) is identical to the result\n        that would be obtained by computing (a**b) % modulo with\n        unbounded precision, but is computed more efficiently.  It is\n        always exact.\n\n        >>> c = ExtendedContext.copy()\n        >>> c.Emin = -999\n        >>> c.Emax = 999\n        >>> c.power(Decimal('2'), Decimal('3'))\n        Decimal('8')\n        >>> c.power(Decimal('-2'), Decimal('3'))\n        Decimal('-8')\n        >>> c.power(Decimal('2'), Decimal('-3'))\n        Decimal('0.125')\n        >>> c.power(Decimal('1.7'), Decimal('8'))\n        Decimal('69.7575744')\n        >>> c.power(Decimal('10'), Decimal('0.301029996'))\n        Decimal('2.00000000')\n        >>> c.power(Decimal('Infinity'), Decimal('-1'))\n        Decimal('0')\n        >>> c.power(Decimal('Infinity'), Decimal('0'))\n        Decimal('1')\n        >>> c.power(Decimal('Infinity'), Decimal('1'))\n        Decimal('Infinity')\n        >>> c.power(Decimal('-Infinity'), Decimal('-1'))\n        Decimal('-0')\n        >>> c.power(Decimal('-Infinity'), Decimal('0'))\n        Decimal('1')\n        >>> c.power(Decimal('-Infinity'), Decimal('1'))\n        Decimal('-Infinity')\n        >>> c.power(Decimal('-Infinity'), Decimal('2'))\n        Decimal('Infinity')\n        >>> c.power(Decimal('0'), Decimal('0'))\n        Decimal('NaN')\n\n        >>> c.power(Decimal('3'), Decimal('7'), Decimal('16'))\n        Decimal('11')\n        >>> c.power(Decimal('-3'), Decimal('7'), Decimal('16'))\n        Decimal('-11')\n        >>> c.power(Decimal('-3'), Decimal('8'), Decimal('16'))\n        Decimal('1')\n        >>> c.power(Decimal('3'), Decimal('7'), Decimal('-16'))\n        Decimal('11')\n        >>> c.power(Decimal('23E12345'), Decimal('67E189'), Decimal('123456789'))\n        Decimal('11729830')\n        >>> c.power(Decimal('-0'), Decimal('17'), Decimal('1729'))\n        Decimal('-0')\n        >>> c.power(Decimal('-23'), Decimal('0'), Decimal('65537'))\n        Decimal('1')\n        >>> ExtendedContext.power(7, 7)\n        Decimal('823543')\n        >>> ExtendedContext.power(Decimal(7), 7)\n        Decimal('823543')\n        >>> ExtendedContext.power(7, Decimal(7), 2)\n        Decimal('1')\n        ");
      var1.setline(5028);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5029);
      var10000 = var1.getlocal(1).__getattr__("__pow__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(5030);
      var5 = var1.getlocal(4);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5031);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(5033);
         var5 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject quantize$216(PyFrame var1, ThreadState var2) {
      var1.setline(5089);
      PyString.fromInterned("Returns a value equal to 'a' (rounded), having the exponent of 'b'.\n\n        The coefficient of the result is derived from that of the left-hand\n        operand.  It may be rounded using the current rounding setting (if the\n        exponent is being increased), multiplied by a positive power of ten (if\n        the exponent is being decreased), or is unchanged (if the exponent is\n        already equal to that of the right-hand operand).\n\n        Unlike other operations, if the length of the coefficient after the\n        quantize operation would be greater than precision then an Invalid\n        operation condition is raised.  This guarantees that, unless there is\n        an error condition, the exponent of the result of a quantize is always\n        equal to that of the right-hand operand.\n\n        Also unlike other operations, quantize will never raise Underflow, even\n        if the result is subnormal and inexact.\n\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.001'))\n        Decimal('2.170')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.01'))\n        Decimal('2.17')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('0.1'))\n        Decimal('2.2')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('1e+0'))\n        Decimal('2')\n        >>> ExtendedContext.quantize(Decimal('2.17'), Decimal('1e+1'))\n        Decimal('0E+1')\n        >>> ExtendedContext.quantize(Decimal('-Inf'), Decimal('Infinity'))\n        Decimal('-Infinity')\n        >>> ExtendedContext.quantize(Decimal('2'), Decimal('Infinity'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('-0.1'), Decimal('1'))\n        Decimal('-0')\n        >>> ExtendedContext.quantize(Decimal('-0'), Decimal('1e+5'))\n        Decimal('-0E+5')\n        >>> ExtendedContext.quantize(Decimal('+35236450.6'), Decimal('1e-2'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('-35236450.6'), Decimal('1e-2'))\n        Decimal('NaN')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e-1'))\n        Decimal('217.0')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e-0'))\n        Decimal('217')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e+1'))\n        Decimal('2.2E+2')\n        >>> ExtendedContext.quantize(Decimal('217'), Decimal('1e+2'))\n        Decimal('2E+2')\n        >>> ExtendedContext.quantize(1, 2)\n        Decimal('1')\n        >>> ExtendedContext.quantize(Decimal(1), 2)\n        Decimal('1')\n        >>> ExtendedContext.quantize(1, Decimal(2))\n        Decimal('1')\n        ");
      var1.setline(5090);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5091);
      var10000 = var1.getlocal(1).__getattr__("quantize");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject radix$217(PyFrame var1, ThreadState var2) {
      var1.setline(5098);
      PyString.fromInterned("Just returns 10, as this is Decimal, :)\n\n        >>> ExtendedContext.radix()\n        Decimal('10')\n        ");
      var1.setline(5099);
      PyObject var3 = var1.getglobal("Decimal").__call__((ThreadState)var2, (PyObject)Py.newInteger(10));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remainder$218(PyFrame var1, ThreadState var2) {
      var1.setline(5131);
      PyString.fromInterned("Returns the remainder from integer division.\n\n        The result is the residue of the dividend after the operation of\n        calculating integer division as described for divide-integer, rounded\n        to precision digits if necessary.  The sign of the result, if\n        non-zero, is the same as that of the original dividend.\n\n        This operation will fail under the same conditions as integer division\n        (that is, if integer division on the same two operands would fail, the\n        remainder cannot be calculated).\n\n        >>> ExtendedContext.remainder(Decimal('2.1'), Decimal('3'))\n        Decimal('2.1')\n        >>> ExtendedContext.remainder(Decimal('10'), Decimal('3'))\n        Decimal('1')\n        >>> ExtendedContext.remainder(Decimal('-10'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.remainder(Decimal('10.2'), Decimal('1'))\n        Decimal('0.2')\n        >>> ExtendedContext.remainder(Decimal('10'), Decimal('0.3'))\n        Decimal('0.1')\n        >>> ExtendedContext.remainder(Decimal('3.6'), Decimal('1.3'))\n        Decimal('1.0')\n        >>> ExtendedContext.remainder(22, 6)\n        Decimal('4')\n        >>> ExtendedContext.remainder(Decimal(22), 6)\n        Decimal('4')\n        >>> ExtendedContext.remainder(22, Decimal(6))\n        Decimal('4')\n        ");
      var1.setline(5132);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5133);
      var10000 = var1.getlocal(1).__getattr__("__mod__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(5134);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5135);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(5137);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject remainder_near$219(PyFrame var1, ThreadState var2) {
      var1.setline(5169);
      PyString.fromInterned("Returns to be \"a - b * n\", where n is the integer nearest the exact\n        value of \"x / b\" (if two integers are equally near then the even one\n        is chosen).  If the result is equal to 0 then its sign will be the\n        sign of a.\n\n        This operation will fail under the same conditions as integer division\n        (that is, if integer division on the same two operands would fail, the\n        remainder cannot be calculated).\n\n        >>> ExtendedContext.remainder_near(Decimal('2.1'), Decimal('3'))\n        Decimal('-0.9')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('6'))\n        Decimal('-2')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('3'))\n        Decimal('1')\n        >>> ExtendedContext.remainder_near(Decimal('-10'), Decimal('3'))\n        Decimal('-1')\n        >>> ExtendedContext.remainder_near(Decimal('10.2'), Decimal('1'))\n        Decimal('0.2')\n        >>> ExtendedContext.remainder_near(Decimal('10'), Decimal('0.3'))\n        Decimal('0.1')\n        >>> ExtendedContext.remainder_near(Decimal('3.6'), Decimal('1.3'))\n        Decimal('-0.3')\n        >>> ExtendedContext.remainder_near(3, 11)\n        Decimal('3')\n        >>> ExtendedContext.remainder_near(Decimal(3), 11)\n        Decimal('3')\n        >>> ExtendedContext.remainder_near(3, Decimal(11))\n        Decimal('3')\n        ");
      var1.setline(5170);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5171);
      var10000 = var1.getlocal(1).__getattr__("remainder_near");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rotate$220(PyFrame var1, ThreadState var2) {
      var1.setline(5198);
      PyString.fromInterned("Returns a rotated copy of a, b times.\n\n        The coefficient of the result is a rotated copy of the digits in\n        the coefficient of the first operand.  The number of places of\n        rotation is taken from the absolute value of the second operand,\n        with the rotation being to the left if the second operand is\n        positive or to the right otherwise.\n\n        >>> ExtendedContext.rotate(Decimal('34'), Decimal('8'))\n        Decimal('400000003')\n        >>> ExtendedContext.rotate(Decimal('12'), Decimal('9'))\n        Decimal('12')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('-2'))\n        Decimal('891234567')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('0'))\n        Decimal('123456789')\n        >>> ExtendedContext.rotate(Decimal('123456789'), Decimal('+2'))\n        Decimal('345678912')\n        >>> ExtendedContext.rotate(1333333, 1)\n        Decimal('13333330')\n        >>> ExtendedContext.rotate(Decimal(1333333), 1)\n        Decimal('13333330')\n        >>> ExtendedContext.rotate(1333333, Decimal(1))\n        Decimal('13333330')\n        ");
      var1.setline(5199);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5200);
      var10000 = var1.getlocal(1).__getattr__("rotate");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject same_quantum$221(PyFrame var1, ThreadState var2) {
      var1.setline(5222);
      PyString.fromInterned("Returns True if the two operands have the same exponent.\n\n        The result is never affected by either the sign or the coefficient of\n        either operand.\n\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('0.001'))\n        False\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('0.01'))\n        True\n        >>> ExtendedContext.same_quantum(Decimal('2.17'), Decimal('1'))\n        False\n        >>> ExtendedContext.same_quantum(Decimal('Inf'), Decimal('-Inf'))\n        True\n        >>> ExtendedContext.same_quantum(10000, -1)\n        True\n        >>> ExtendedContext.same_quantum(Decimal(10000), -1)\n        True\n        >>> ExtendedContext.same_quantum(10000, Decimal(-1))\n        True\n        ");
      var1.setline(5223);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5224);
      var5 = var1.getlocal(1).__getattr__("same_quantum").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject scaleb$222(PyFrame var1, ThreadState var2) {
      var1.setline(5241);
      PyString.fromInterned("Returns the first operand after adding the second value its exp.\n\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('-2'))\n        Decimal('0.0750')\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('0'))\n        Decimal('7.50')\n        >>> ExtendedContext.scaleb(Decimal('7.50'), Decimal('3'))\n        Decimal('7.50E+3')\n        >>> ExtendedContext.scaleb(1, 4)\n        Decimal('1E+4')\n        >>> ExtendedContext.scaleb(Decimal(1), 4)\n        Decimal('1E+4')\n        >>> ExtendedContext.scaleb(1, Decimal(4))\n        Decimal('1E+4')\n        ");
      var1.setline(5242);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5243);
      var10000 = var1.getlocal(1).__getattr__("scaleb");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject shift$223(PyFrame var1, ThreadState var2) {
      var1.setline(5271);
      PyString.fromInterned("Returns a shifted copy of a, b times.\n\n        The coefficient of the result is a shifted copy of the digits\n        in the coefficient of the first operand.  The number of places\n        to shift is taken from the absolute value of the second operand,\n        with the shift being to the left if the second operand is\n        positive or to the right otherwise.  Digits shifted into the\n        coefficient are zeros.\n\n        >>> ExtendedContext.shift(Decimal('34'), Decimal('8'))\n        Decimal('400000000')\n        >>> ExtendedContext.shift(Decimal('12'), Decimal('9'))\n        Decimal('0')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('-2'))\n        Decimal('1234567')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('0'))\n        Decimal('123456789')\n        >>> ExtendedContext.shift(Decimal('123456789'), Decimal('+2'))\n        Decimal('345678900')\n        >>> ExtendedContext.shift(88888888, 2)\n        Decimal('888888800')\n        >>> ExtendedContext.shift(Decimal(88888888), 2)\n        Decimal('888888800')\n        >>> ExtendedContext.shift(88888888, Decimal(2))\n        Decimal('888888800')\n        ");
      var1.setline(5272);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5273);
      var10000 = var1.getlocal(1).__getattr__("shift");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject sqrt$224(PyFrame var1, ThreadState var2) {
      var1.setline(5303);
      PyString.fromInterned("Square root of a non-negative number to context precision.\n\n        If the result must be inexact, it is rounded using the round-half-even\n        algorithm.\n\n        >>> ExtendedContext.sqrt(Decimal('0'))\n        Decimal('0')\n        >>> ExtendedContext.sqrt(Decimal('-0'))\n        Decimal('-0')\n        >>> ExtendedContext.sqrt(Decimal('0.39'))\n        Decimal('0.624499800')\n        >>> ExtendedContext.sqrt(Decimal('100'))\n        Decimal('10')\n        >>> ExtendedContext.sqrt(Decimal('1'))\n        Decimal('1')\n        >>> ExtendedContext.sqrt(Decimal('1.0'))\n        Decimal('1.0')\n        >>> ExtendedContext.sqrt(Decimal('1.00'))\n        Decimal('1.0')\n        >>> ExtendedContext.sqrt(Decimal('7'))\n        Decimal('2.64575131')\n        >>> ExtendedContext.sqrt(Decimal('10'))\n        Decimal('3.16227766')\n        >>> ExtendedContext.sqrt(2)\n        Decimal('1.41421356')\n        >>> ExtendedContext.prec\n        9\n        ");
      var1.setline(5304);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5305);
      var10000 = var1.getlocal(1).__getattr__("sqrt");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject subtract$225(PyFrame var1, ThreadState var2) {
      var1.setline(5322);
      PyString.fromInterned("Return the difference between the two operands.\n\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('1.07'))\n        Decimal('0.23')\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('1.30'))\n        Decimal('0.00')\n        >>> ExtendedContext.subtract(Decimal('1.3'), Decimal('2.07'))\n        Decimal('-0.77')\n        >>> ExtendedContext.subtract(8, 5)\n        Decimal('3')\n        >>> ExtendedContext.subtract(Decimal(8), 5)\n        Decimal('3')\n        >>> ExtendedContext.subtract(8, Decimal(5))\n        Decimal('3')\n        ");
      var1.setline(5323);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5324);
      var10000 = var1.getlocal(1).__getattr__("__sub__");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(5325);
      var5 = var1.getlocal(3);
      var10000 = var5._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5326);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(2))));
      } else {
         var1.setline(5328);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject to_eng_string$226(PyFrame var1, ThreadState var2) {
      var1.setline(5334);
      PyString.fromInterned("Converts a number to a string, using scientific notation.\n\n        The operation is not affected by the context.\n        ");
      var1.setline(5335);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5336);
      var10000 = var1.getlocal(1).__getattr__("to_eng_string");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject to_sci_string$227(PyFrame var1, ThreadState var2) {
      var1.setline(5342);
      PyString.fromInterned("Converts a number to a string, using scientific notation.\n\n        The operation is not affected by the context.\n        ");
      var1.setline(5343);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5344);
      var10000 = var1.getlocal(1).__getattr__("__str__");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject to_integral_exact$228(PyFrame var1, ThreadState var2) {
      var1.setline(5372);
      PyString.fromInterned("Rounds to an integer.\n\n        When the operand has a negative exponent, the result is the same\n        as using the quantize() operation using the given operand as the\n        left-hand-operand, 1E+0 as the right-hand-operand, and the precision\n        of the operand as the precision setting; Inexact and Rounded flags\n        are allowed in this operation.  The rounding mode is taken from the\n        context.\n\n        >>> ExtendedContext.to_integral_exact(Decimal('2.1'))\n        Decimal('2')\n        >>> ExtendedContext.to_integral_exact(Decimal('100'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_exact(Decimal('100.0'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_exact(Decimal('101.5'))\n        Decimal('102')\n        >>> ExtendedContext.to_integral_exact(Decimal('-101.5'))\n        Decimal('-102')\n        >>> ExtendedContext.to_integral_exact(Decimal('10E+5'))\n        Decimal('1.0E+6')\n        >>> ExtendedContext.to_integral_exact(Decimal('7.89E+77'))\n        Decimal('7.89E+77')\n        >>> ExtendedContext.to_integral_exact(Decimal('-Inf'))\n        Decimal('-Infinity')\n        ");
      var1.setline(5373);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5374);
      var10000 = var1.getlocal(1).__getattr__("to_integral_exact");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject to_integral_value$229(PyFrame var1, ThreadState var2) {
      var1.setline(5401);
      PyString.fromInterned("Rounds to an integer.\n\n        When the operand has a negative exponent, the result is the same\n        as using the quantize() operation using the given operand as the\n        left-hand-operand, 1E+0 as the right-hand-operand, and the precision\n        of the operand as the precision setting, except that no flags will\n        be set.  The rounding mode is taken from the context.\n\n        >>> ExtendedContext.to_integral_value(Decimal('2.1'))\n        Decimal('2')\n        >>> ExtendedContext.to_integral_value(Decimal('100'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_value(Decimal('100.0'))\n        Decimal('100')\n        >>> ExtendedContext.to_integral_value(Decimal('101.5'))\n        Decimal('102')\n        >>> ExtendedContext.to_integral_value(Decimal('-101.5'))\n        Decimal('-102')\n        >>> ExtendedContext.to_integral_value(Decimal('10E+5'))\n        Decimal('1.0E+6')\n        >>> ExtendedContext.to_integral_value(Decimal('7.89E+77'))\n        Decimal('7.89E+77')\n        >>> ExtendedContext.to_integral_value(Decimal('-Inf'))\n        Decimal('-Infinity')\n        ");
      var1.setline(5402);
      PyObject var10000 = var1.getglobal("_convert_other");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"raiseit"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(5403);
      var10000 = var1.getlocal(1).__getattr__("to_integral_value");
      var3 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _WorkRep$230(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(5409);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("sign"), PyString.fromInterned("int"), PyString.fromInterned("exp")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(5414);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$231, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(5429);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$232, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(5432);
      PyObject var6 = var1.getname("__repr__");
      var1.setlocal("__str__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$231(PyFrame var1, ThreadState var2) {
      var1.setline(5415);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5416);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("sign", var3);
         var3 = null;
         var1.setline(5417);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"int", var4);
         var3 = null;
         var1.setline(5418);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("exp", var3);
         var3 = null;
      } else {
         var1.setline(5419);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Decimal")).__nonzero__()) {
            var1.setline(5420);
            var3 = var1.getlocal(1).__getattr__("_sign");
            var1.getlocal(0).__setattr__("sign", var3);
            var3 = null;
            var1.setline(5421);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("_int"));
            var1.getlocal(0).__setattr__("int", var3);
            var3 = null;
            var1.setline(5422);
            var3 = var1.getlocal(1).__getattr__("_exp");
            var1.getlocal(0).__setattr__("exp", var3);
            var3 = null;
         } else {
            var1.setline(5425);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var1.getlocal(0).__setattr__("sign", var3);
            var3 = null;
            var1.setline(5426);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.getlocal(0).__setattr__("int", var3);
            var3 = null;
            var1.setline(5427);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.getlocal(0).__setattr__("exp", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$232(PyFrame var1, ThreadState var2) {
      var1.setline(5430);
      PyObject var3 = PyString.fromInterned("(%r, %r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("sign"), var1.getlocal(0).__getattr__("int"), var1.getlocal(0).__getattr__("exp")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _normalize$233(PyFrame var1, ThreadState var2) {
      var1.setline(5440);
      PyString.fromInterned("Normalizes op1, op2 to have the same exp and length of coefficient.\n\n    Done during addition.\n    ");
      var1.setline(5441);
      PyObject var3 = var1.getlocal(0).__getattr__("exp");
      PyObject var10000 = var3._lt(var1.getlocal(1).__getattr__("exp"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5442);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(5443);
         var3 = var1.getlocal(0);
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(5445);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(5446);
         var3 = var1.getlocal(1);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(5453);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3).__getattr__("int")));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(5454);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4).__getattr__("int")));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(5455);
      var3 = var1.getlocal(3).__getattr__("exp")._add(var1.getglobal("min").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)var1.getlocal(5)._sub(var1.getlocal(2))._sub(Py.newInteger(2))));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(5456);
      var3 = var1.getlocal(6)._add(var1.getlocal(4).__getattr__("exp"))._sub(Py.newInteger(1));
      var10000 = var3._lt(var1.getlocal(7));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5457);
         PyInteger var6 = Py.newInteger(1);
         var1.getlocal(4).__setattr__((String)"int", var6);
         var3 = null;
         var1.setline(5458);
         var3 = var1.getlocal(7);
         var1.getlocal(4).__setattr__("exp", var3);
         var3 = null;
      }

      var1.setline(5460);
      var10000 = var1.getlocal(3);
      String var7 = "int";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var7);
      var5 = var5._imul(Py.newInteger(10)._pow(var1.getlocal(3).__getattr__("exp")._sub(var1.getlocal(4).__getattr__("exp"))));
      var4.__setattr__(var7, var5);
      var1.setline(5461);
      var3 = var1.getlocal(4).__getattr__("exp");
      var1.getlocal(3).__setattr__("exp", var3);
      var3 = null;
      var1.setline(5462);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject _nbits$234(PyFrame var1, ThreadState var2) {
      var1.setline(5478);
      PyString.fromInterned("Number of bits in binary representation of the positive integer n,\n    or 0 if n == 0.\n    ");
      var1.setline(5479);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5480);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The argument to _nbits should be nonnegative.")));
      } else {
         var1.setline(5481);
         var3 = PyString.fromInterned("%x")._mod(var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(5482);
         var3 = Py.newInteger(4)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._sub(var1.getlocal(1).__getitem__(var1.getlocal(2).__getitem__(Py.newInteger(0))));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _sqrt_nearest$235(PyFrame var1, ThreadState var2) {
      var1.setline(5490);
      PyString.fromInterned("Closest integer to the square root of the positive integer n.  a is\n    an initial approximation to the square root.  Any positive integer\n    will do for a, but the closer a is to the square root of n the\n    faster convergence will be.\n\n    ");
      var1.setline(5491);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._le(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(5492);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Both arguments to _sqrt_nearest should be positive.")));
      } else {
         var1.setline(5494);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(2, var6);
         var3 = null;

         while(true) {
            var1.setline(5495);
            var3 = var1.getlocal(1);
            var10000 = var3._ne(var1.getlocal(2));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(5497);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(5496);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)._sub(var1.getlocal(0).__neg__()._floordiv(var1.getlocal(1)))._rshift(Py.newInteger(1))});
            PyObject[] var4 = Py.unpackSequence(var7, 2);
            PyObject var5 = var4[0];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(1, var5);
            var5 = null;
            var3 = null;
         }
      }
   }

   public PyObject _rshift_nearest$236(PyFrame var1, ThreadState var2) {
      var1.setline(5503);
      PyString.fromInterned("Given an integer x and a nonnegative integer shift, return closest\n    integer to x / 2**shift; use round-to-even in case of a tie.\n\n    ");
      var1.setline(5504);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newLong("1")._lshift(var1.getlocal(1)), var1.getlocal(0)._rshift(var1.getlocal(1))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(5505);
      PyObject var10000 = var1.getlocal(3);
      PyObject var6 = Py.newInteger(2)._mul(var1.getlocal(0)._and(var1.getlocal(2)._sub(Py.newInteger(1))))._add(var1.getlocal(3)._and(Py.newInteger(1)));
      PyObject var10001 = var6._gt(var1.getlocal(2));
      var3 = null;
      var6 = var10000._add(var10001);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _div_nearest$237(PyFrame var1, ThreadState var2) {
      var1.setline(5511);
      PyString.fromInterned("Closest integer to a/b, a and b positive integers; rounds to even\n    in the case of a tie.\n\n    ");
      var1.setline(5512);
      PyObject var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(5513);
      PyObject var10000 = var1.getlocal(2);
      var3 = Py.newInteger(2)._mul(var1.getlocal(3))._add(var1.getlocal(2)._and(Py.newInteger(1)));
      PyObject var10001 = var3._gt(var1.getlocal(1));
      var3 = null;
      var3 = var10000._add(var10001);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ilog$238(PyFrame var1, ThreadState var2) {
      var1.setline(5524);
      PyString.fromInterned("Integer approximation to M*log(x/M), with absolute error boundable\n    in terms only of x/M.\n\n    Given positive integers x and M, return an integer approximation to\n    M * log(x/M).  For L = 8 and 0.1 <= x/M <= 10 the difference\n    between the approximation and the exact result is at most 22.  For\n    L = 8 and 1.0 <= x/M <= 10.0 the difference is at most 15.  In\n    both cases these are upper bounds on the error; it will usually be\n    much smaller.");
      var1.setline(5545);
      PyObject var3 = var1.getlocal(0)._sub(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(5547);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(4, var6);
      var3 = null;

      while(true) {
         var1.setline(5548);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._le(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("long").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(3)))._lshift(var1.getlocal(2)._sub(var1.getlocal(4)));
            var10000 = var3._ge(var1.getlocal(1));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(4);
            var10000 = var3._gt(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(3))._rshift(var1.getlocal(4)._sub(var1.getlocal(2)));
               var10000 = var3._ge(var1.getlocal(1));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            var1.setline(5555);
            var3 = var1.getglobal("int").__call__(var2, Py.newInteger(-10)._mul(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))))._floordiv(Py.newInteger(3)._mul(var1.getlocal(2)))).__neg__();
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(5556);
            var3 = var1.getglobal("_rshift_nearest").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(5557);
            var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(5558);
            var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getlocal(5)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)).__iter__();

            while(true) {
               var1.setline(5558);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(5561);
                  var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(7)._mul(var1.getlocal(3)), var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(8, var4);
               var1.setline(5559);
               PyObject var5 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(1), var1.getlocal(8))._sub(var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(6)._mul(var1.getlocal(7)), var1.getlocal(1)));
               var1.setlocal(7, var5);
               var5 = null;
            }
         }

         var1.setline(5550);
         var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getglobal("long").__call__(var2, var1.getlocal(1)._mul(var1.getlocal(3)))._lshift(Py.newInteger(1)), var1.getlocal(1)._add(var1.getglobal("_sqrt_nearest").__call__(var2, var1.getlocal(1)._mul(var1.getlocal(1)._add(var1.getglobal("_rshift_nearest").__call__(var2, var1.getlocal(3), var1.getlocal(4)))), var1.getlocal(1))));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(5552);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(4, var3);
      }
   }

   public PyObject _dlog10$239(PyFrame var1, ThreadState var2) {
      var1.setline(5566);
      PyString.fromInterned("Given integers c, e and p with c > 0, p >= 0, compute an integer\n    approximation to 10**p * log10(c*10**e), with an absolute error of\n    at most 1.  Assumes that c*10**e is not exactly 1.");
      var1.setline(5570);
      PyObject var3 = var1.getlocal(2);
      var3 = var3._iadd(Py.newInteger(2));
      var1.setlocal(2, var3);
      var1.setline(5576);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(5577);
      PyObject var10000 = var3 = var1.getlocal(1)._add(var1.getlocal(3));
      PyObject var10001 = var3._ge(Py.newInteger(1));
      var3 = null;
      var3 = var10000._sub(var10001);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(5579);
      var3 = var1.getlocal(2);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5580);
         var3 = Py.newInteger(10)._pow(var1.getlocal(2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(5581);
         var3 = var1.getlocal(1)._add(var1.getlocal(2))._sub(var1.getlocal(4));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(5582);
         var3 = var1.getlocal(6);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5583);
            var3 = var1.getlocal(0);
            var3 = var3._imul(Py.newInteger(10)._pow(var1.getlocal(6)));
            var1.setlocal(0, var3);
         } else {
            var1.setline(5585);
            var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(0), Py.newInteger(10)._pow(var1.getlocal(6).__neg__()));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(5587);
         var3 = var1.getglobal("_ilog").__call__(var2, var1.getlocal(0), var1.getlocal(5));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(5588);
         var3 = var1.getglobal("_log10_digits").__call__(var2, var1.getlocal(2));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(5589);
         var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(7)._mul(var1.getlocal(5)), var1.getlocal(8));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(5590);
         var3 = var1.getlocal(4)._mul(var1.getlocal(5));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(5592);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(7, var4);
         var3 = null;
         var1.setline(5593);
         var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(4), Py.newInteger(10)._pow(var1.getlocal(2).__neg__()));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(5595);
      var3 = var1.getglobal("_div_nearest").__call__((ThreadState)var2, (PyObject)var1.getlocal(9)._add(var1.getlocal(7)), (PyObject)Py.newInteger(100));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _dlog$240(PyFrame var1, ThreadState var2) {
      var1.setline(5600);
      PyString.fromInterned("Given integers c, e and p with c > 0, compute an integer\n    approximation to 10**p * log(c*10**e), with an absolute error of\n    at most 1.  Assumes that c*10**e is not exactly 1.");
      var1.setline(5604);
      PyObject var3 = var1.getlocal(2);
      var3 = var3._iadd(Py.newInteger(2));
      var1.setlocal(2, var3);
      var1.setline(5609);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(5610);
      PyObject var10000 = var3 = var1.getlocal(1)._add(var1.getlocal(3));
      PyObject var10001 = var3._ge(Py.newInteger(1));
      var3 = null;
      var3 = var10000._sub(var10001);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(5613);
      var3 = var1.getlocal(2);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(5614);
         var3 = var1.getlocal(1)._add(var1.getlocal(2))._sub(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(5615);
         var3 = var1.getlocal(5);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5616);
            var3 = var1.getlocal(0);
            var3 = var3._imul(Py.newInteger(10)._pow(var1.getlocal(5)));
            var1.setlocal(0, var3);
         } else {
            var1.setline(5618);
            var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(0), Py.newInteger(10)._pow(var1.getlocal(5).__neg__()));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(5621);
         var3 = var1.getglobal("_ilog").__call__(var2, var1.getlocal(0), Py.newInteger(10)._pow(var1.getlocal(2)));
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(5624);
         var4 = Py.newInteger(0);
         var1.setlocal(6, var4);
         var3 = null;
      }

      var1.setline(5627);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(5628);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(4))))._sub(Py.newInteger(1));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(5629);
         var3 = var1.getlocal(2)._add(var1.getlocal(7));
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5632);
            var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(4)._mul(var1.getglobal("_log10_digits").__call__(var2, var1.getlocal(2)._add(var1.getlocal(7)))), Py.newInteger(10)._pow(var1.getlocal(7)));
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(5634);
            var4 = Py.newInteger(0);
            var1.setlocal(8, var4);
            var3 = null;
         }
      } else {
         var1.setline(5636);
         var4 = Py.newInteger(0);
         var1.setlocal(8, var4);
         var3 = null;
      }

      var1.setline(5639);
      var3 = var1.getglobal("_div_nearest").__call__((ThreadState)var2, (PyObject)var1.getlocal(8)._add(var1.getlocal(6)), (PyObject)Py.newInteger(100));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _Log10Memoize$241(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to compute, store, and allow retrieval of, digits of the\n    constant log(10) = 2.302585....  This constant is needed by\n    Decimal.ln, Decimal.log10, Decimal.exp and Decimal.__pow__."));
      var1.setline(5644);
      PyString.fromInterned("Class to compute, store, and allow retrieval of, digits of the\n    constant log(10) = 2.302585....  This constant is needed by\n    Decimal.ln, Decimal.log10, Decimal.exp and Decimal.__pow__.");
      var1.setline(5645);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$242, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(5648);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdigits$243, PyString.fromInterned("Given an integer p >= 0, return floor(10**p)*log(10).\n\n        For example, self.getdigits(3) returns 2302.\n        "));
      var1.setlocal("getdigits", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$242(PyFrame var1, ThreadState var2) {
      var1.setline(5646);
      PyString var3 = PyString.fromInterned("23025850929940456840179914546843642076011014886");
      var1.getlocal(0).__setattr__((String)"digits", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getdigits$243(PyFrame var1, ThreadState var2) {
      var1.setline(5652);
      PyString.fromInterned("Given an integer p >= 0, return floor(10**p)*log(10).\n\n        For example, self.getdigits(3) returns 2302.\n        ");
      var1.setline(5657);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5658);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("p should be nonnegative")));
      } else {
         var1.setline(5660);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("digits")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5663);
            PyInteger var4 = Py.newInteger(3);
            var1.setlocal(2, var4);
            var3 = null;

            while(true) {
               var1.setline(5664);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(5666);
               var3 = Py.newInteger(10)._pow(var1.getlocal(1)._add(var1.getlocal(2))._add(Py.newInteger(2)));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(5667);
               var3 = var1.getglobal("str").__call__(var2, var1.getglobal("_div_nearest").__call__((ThreadState)var2, (PyObject)var1.getglobal("_ilog").__call__(var2, Py.newInteger(10)._mul(var1.getlocal(3)), var1.getlocal(3)), (PyObject)Py.newInteger(100)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(5668);
               var3 = var1.getlocal(4).__getslice__(var1.getlocal(2).__neg__(), (PyObject)null, (PyObject)null);
               var10000 = var3._ne(PyString.fromInterned("0")._mul(var1.getlocal(2)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(5670);
               var3 = var1.getlocal(2);
               var3 = var3._iadd(Py.newInteger(3));
               var1.setlocal(2, var3);
            }

            var1.setline(5673);
            var3 = var1.getlocal(4).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.getlocal(0).__setattr__("digits", var3);
            var3 = null;
         }

         var1.setline(5674);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("digits").__getslice__((PyObject)null, var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _iexp$244(PyFrame var1, ThreadState var2) {
      var1.setline(5682);
      PyString.fromInterned("Given integers x and M, M > 0, such that x/M is small in absolute\n    value, compute an integer approximation to M*exp(x/M).  For 0 <=\n    x/M <= 2.4, the absolute error in the result is bounded by 60 (and\n    is usually much smaller).");
      var1.setline(5699);
      PyObject var3 = var1.getglobal("_nbits").__call__(var2, var1.getglobal("long").__call__(var2, var1.getlocal(0))._lshift(var1.getlocal(2))._floordiv(var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(5702);
      var3 = var1.getglobal("int").__call__(var2, Py.newInteger(-10)._mul(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))))._floordiv(Py.newInteger(3)._mul(var1.getlocal(2)))).__neg__();
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(5703);
      var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(0), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(5704);
      var3 = var1.getglobal("long").__call__(var2, var1.getlocal(1))._lshift(var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(5705);
      var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getlocal(4)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)).__iter__();

      while(true) {
         var1.setline(5705);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(5709);
            var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getlocal(3)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(-1)).__iter__();

            while(true) {
               var1.setline(5709);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(5713);
                  var3 = var1.getlocal(1)._add(var1.getlocal(5));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(8, var4);
               var1.setline(5710);
               var5 = var1.getglobal("long").__call__(var2, var1.getlocal(1))._lshift(var1.getlocal(8)._add(Py.newInteger(2)));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(5711);
               var5 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(5)._mul(var1.getlocal(5)._add(var1.getlocal(6))), var1.getlocal(6));
               var1.setlocal(5, var5);
               var5 = null;
            }
         }

         var1.setlocal(7, var4);
         var1.setline(5706);
         var5 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(0)._mul(var1.getlocal(6)._add(var1.getlocal(5))), var1.getlocal(6)._mul(var1.getlocal(7)));
         var1.setlocal(5, var5);
         var5 = null;
      }
   }

   public PyObject _dexp$245(PyFrame var1, ThreadState var2) {
      var1.setline(5727);
      PyString.fromInterned("Compute an approximation to exp(c*10**e), with p decimal places of\n    precision.\n\n    Returns integers d, f such that:\n\n      10**(p-1) <= d <= 10**p, and\n      (d-1)*10**f < exp(c*10**e) < (d+1)*10**f\n\n    In other words, d*10**f is an approximation to exp(c*10**e) with p\n    digits of precision, and with an error in d of at most 1.  This is\n    almost, but not quite, the same as the error being < 1ulp: when d\n    = 10**(p-1) the error could be up to 10 ulp.");
      var1.setline(5730);
      PyObject var3 = var1.getlocal(2);
      var3 = var3._iadd(Py.newInteger(2));
      var1.setlocal(2, var3);
      var1.setline(5733);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0))))._sub(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(5734);
      var3 = var1.getlocal(2)._add(var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(5738);
      var3 = var1.getlocal(1)._add(var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(5739);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5740);
         var3 = var1.getlocal(0)._mul(Py.newInteger(10)._pow(var1.getlocal(5)));
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(5742);
         var3 = var1.getlocal(0)._floordiv(Py.newInteger(10)._pow(var1.getlocal(5).__neg__()));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(5743);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(6), var1.getglobal("_log10_digits").__call__(var2, var1.getlocal(4)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(5746);
      var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(8), Py.newInteger(10)._pow(var1.getlocal(3)));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(5749);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("_div_nearest").__call__((ThreadState)var2, (PyObject)var1.getglobal("_iexp").__call__(var2, var1.getlocal(8), Py.newInteger(10)._pow(var1.getlocal(2))), (PyObject)Py.newInteger(1000)), var1.getlocal(7)._sub(var1.getlocal(2))._add(Py.newInteger(3))});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _dpower$246(PyFrame var1, ThreadState var2) {
      var1.setline(5764);
      PyString.fromInterned("Given integers xc, xe, yc and ye representing Decimals x = xc*10**xe and\n    y = yc*10**ye, compute x**y.  Returns a pair of integers (c, e) such that:\n\n      10**(p-1) <= c <= 10**p, and\n      (c-1)*10**e < x**y < (c+1)*10**e\n\n    in other words, c*10**e is an approximation to x**y with p digits\n    of precision, and with an error in c of at most 1.  (This is\n    almost, but not quite, the same as the error being < 1ulp: when c\n    == 10**(p-1) we can only guarantee error < 10ulp.)\n\n    We assume that: x is positive and not equal to 1, and y is nonzero.\n    ");
      var1.setline(5767);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(2))))._add(var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(5770);
      var3 = var1.getglobal("_dlog").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(4)._add(var1.getlocal(5))._add(Py.newInteger(1)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(5773);
      var3 = var1.getlocal(3)._sub(var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(5774);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5775);
         var3 = var1.getlocal(6)._mul(var1.getlocal(2))._mul(Py.newInteger(10)._pow(var1.getlocal(7)));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(5777);
         var3 = var1.getglobal("_div_nearest").__call__(var2, var1.getlocal(6)._mul(var1.getlocal(2)), Py.newInteger(10)._pow(var1.getlocal(7).__neg__()));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(5779);
      var3 = var1.getlocal(8);
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(5782);
         var5 = var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)))._add(var1.getlocal(1));
         var10000 = var5._ge(Py.newInteger(1));
         var5 = null;
         var3 = var10000;
         var5 = var1.getlocal(2);
         var10000 = var5._gt(Py.newInteger(0));
         var5 = null;
         var10000 = var3._eq(var10000);
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5783);
            var6 = new PyTuple(new PyObject[]{Py.newInteger(10)._pow(var1.getlocal(4)._sub(Py.newInteger(1)))._add(Py.newInteger(1)), Py.newInteger(1)._sub(var1.getlocal(4))});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(5785);
            var6 = new PyTuple(new PyObject[]{Py.newInteger(10)._pow(var1.getlocal(4))._sub(Py.newInteger(1)), var1.getlocal(4).__neg__()});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
         }
      } else {
         var1.setline(5787);
         var3 = var1.getglobal("_dexp").__call__(var2, var1.getlocal(8), var1.getlocal(4)._add(Py.newInteger(1)).__neg__(), var1.getlocal(4)._add(Py.newInteger(1)));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
         var1.setline(5788);
         var3 = var1.getglobal("_div_nearest").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)Py.newInteger(10));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(5789);
         var3 = var1.getlocal(10);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(10, var3);
      }

      var1.setline(5791);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _log10_lb$247(PyFrame var1, ThreadState var2) {
      var1.setline(5796);
      PyString.fromInterned("Compute a lower bound for 100*log10(c) for a positive integer c.");
      var1.setline(5797);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5798);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The argument to _log10_lb should be nonnegative.")));
      } else {
         var1.setline(5799);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(5800);
         var3 = Py.newInteger(100)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._sub(var1.getlocal(1).__getitem__(var1.getlocal(2).__getitem__(Py.newInteger(0))));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _convert_other$248(PyFrame var1, ThreadState var2) {
      var1.setline(5811);
      PyString.fromInterned("Convert other to Decimal.\n\n    Verifies that it's ok to use in an implicit construction.\n    If allow_float is true, allow conversion from float;  this\n    is used in the comparison methods (__eq__ and friends).\n\n    ");
      var1.setline(5812);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Decimal")).__nonzero__()) {
         var1.setline(5813);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(5814);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
            var1.setline(5815);
            var3 = var1.getglobal("Decimal").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(5816);
            PyObject var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("float"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(5817);
               var3 = var1.getglobal("Decimal").__getattr__("from_float").__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(5819);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(5820);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Unable to convert %s to Decimal")._mod(var1.getlocal(0))));
               } else {
                  var1.setline(5821);
                  var3 = var1.getglobal("NotImplemented");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _parse_format_specifier$249(PyFrame var1, ThreadState var2) {
      var1.setline(5944);
      PyString.fromInterned("Parse and validate a format specifier.\n\n    Turns a standard numeric format specifier into a dict, with the\n    following entries:\n\n      fill: fill character to pad field to minimum width\n      align: alignment type, either '<', '>', '=' or '^'\n      sign: either '+', '-' or ' '\n      minimumwidth: nonnegative integer giving minimum width\n      zeropad: boolean, indicating whether to pad with zeros\n      thousands_sep: string to use as thousands separator, or ''\n      grouping: grouping for thousands separators, in format\n        used by localeconv\n      decimal_point: string to use for decimal point\n      precision: nonnegative integer giving precision, or None\n      type: one of the characters 'eEfFgG%', or None\n      unicode: boolean (always True for Python 3.x)\n\n    ");
      var1.setline(5945);
      PyObject var3 = var1.getglobal("_parse_format_specifier_regex").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(5946);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(5947);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid format specifier: ")._add(var1.getlocal(0))));
      } else {
         var1.setline(5950);
         var3 = var1.getlocal(2).__getattr__("groupdict").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(5954);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("fill"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(5955);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("align"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(5956);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("zeropad"));
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         var3 = var10000;
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("zeropad"), var3);
         var3 = null;
         var1.setline(5957);
         if (var1.getlocal(3).__getitem__(PyString.fromInterned("zeropad")).__nonzero__()) {
            var1.setline(5958);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(5959);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Fill character conflicts with '0' in format specifier: ")._add(var1.getlocal(0))));
            }

            var1.setline(5961);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(5962);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Alignment conflicts with '0' in format specifier: ")._add(var1.getlocal(0))));
            }
         }

         var1.setline(5964);
         Object var8 = var1.getlocal(4);
         if (!((PyObject)var8).__nonzero__()) {
            var8 = PyString.fromInterned(" ");
         }

         Object var4 = var8;
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("fill"), (PyObject)var4);
         var3 = null;
         var1.setline(5968);
         var8 = var1.getlocal(5);
         if (!((PyObject)var8).__nonzero__()) {
            var8 = PyString.fromInterned(">");
         }

         var4 = var8;
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("align"), (PyObject)var4);
         var3 = null;
         var1.setline(5971);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("sign"));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyString var5;
         if (var10000.__nonzero__()) {
            var1.setline(5972);
            var5 = PyString.fromInterned("-");
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("sign"), var5);
            var3 = null;
         }

         var1.setline(5975);
         var10000 = var1.getglobal("int");
         Object var10002 = var1.getlocal(3).__getitem__(PyString.fromInterned("minimumwidth"));
         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = PyString.fromInterned("0");
         }

         var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("minimumwidth"), var3);
         var3 = null;
         var1.setline(5976);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("precision"));
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5977);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(PyString.fromInterned("precision")));
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("precision"), var3);
            var3 = null;
         }

         var1.setline(5981);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("precision"));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5982);
            var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("type"));
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("type"));
               var10000 = var3._in(PyString.fromInterned("gG"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(5983);
               PyInteger var6 = Py.newInteger(1);
               var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("precision"), var6);
               var3 = null;
            }
         }

         var1.setline(5987);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("type"));
         var10000 = var3._eq(PyString.fromInterned("n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(5989);
            var5 = PyString.fromInterned("g");
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("type"), var5);
            var3 = null;
            var1.setline(5990);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(5991);
               var3 = var1.getglobal("_locale").__getattr__("localeconv").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(5992);
            var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("thousands_sep"));
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(5993);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Explicit thousands separator conflicts with 'n' type in format specifier: ")._add(var1.getlocal(0))));
            }

            var1.setline(5995);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("thousands_sep"));
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("thousands_sep"), var3);
            var3 = null;
            var1.setline(5996);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("grouping"));
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("grouping"), var3);
            var3 = null;
            var1.setline(5997);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("decimal_point"));
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("decimal_point"), var3);
            var3 = null;
         } else {
            var1.setline(5999);
            var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("thousands_sep"));
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(6000);
               var5 = PyString.fromInterned("");
               var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("thousands_sep"), var5);
               var3 = null;
            }

            var1.setline(6001);
            PyList var7 = new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(0)});
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("grouping"), var7);
            var3 = null;
            var1.setline(6002);
            var5 = PyString.fromInterned(".");
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("decimal_point"), var5);
            var3 = null;
         }

         var1.setline(6005);
         var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode"));
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("unicode"), var3);
         var3 = null;
         var1.setline(6007);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _format_align$250(PyFrame var1, ThreadState var2) {
      var1.setline(6017);
      PyString.fromInterned("Given an unpadded, non-aligned numeric string 'body' and sign\n    string 'sign', add padding and aligment conforming to the given\n    format specifier dictionary 'spec' (as produced by\n    parse_format_specifier).\n\n    Also converts result to unicode if necessary.\n\n    ");
      var1.setline(6019);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("minimumwidth"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(6020);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("fill"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(6021);
      var3 = var1.getlocal(4)._mul(var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0)))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1))));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(6023);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("align"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(6024);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._eq(PyString.fromInterned("<"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(6025);
         var3 = var1.getlocal(0)._add(var1.getlocal(1))._add(var1.getlocal(5));
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(6026);
         var3 = var1.getlocal(6);
         var10000 = var3._eq(PyString.fromInterned(">"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(6027);
            var3 = var1.getlocal(5)._add(var1.getlocal(0))._add(var1.getlocal(1));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(6028);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(PyString.fromInterned("="));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(6029);
               var3 = var1.getlocal(0)._add(var1.getlocal(5))._add(var1.getlocal(1));
               var1.setlocal(7, var3);
               var3 = null;
            } else {
               var1.setline(6030);
               var3 = var1.getlocal(6);
               var10000 = var3._eq(PyString.fromInterned("^"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(6034);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unrecognised alignment field")));
               }

               var1.setline(6031);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._floordiv(Py.newInteger(2));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(6032);
               var3 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null)._add(var1.getlocal(0))._add(var1.getlocal(1))._add(var1.getlocal(5).__getslice__(var1.getlocal(8), (PyObject)null, (PyObject)null));
               var1.setlocal(7, var3);
               var3 = null;
            }
         }
      }

      var1.setline(6037);
      if (var1.getlocal(2).__getitem__(PyString.fromInterned("unicode")).__nonzero__()) {
         var1.setline(6038);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(7));
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(6040);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _group_lengths$251(PyFrame var1, ThreadState var2) {
      var1.setline(6046);
      PyString.fromInterned("Convert a localeconv-style grouping into a (possibly infinite)\n    iterable of integers representing group lengths.\n\n    ");
      var1.setline(6055);
      String[] var3 = new String[]{"chain", "repeat"};
      PyObject[] var5 = imp.importFrom("itertools", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(6056);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(6057);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(6058);
         var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
         PyObject var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var4._ge(Py.newInteger(2));
            var4 = null;
         }

         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(6059);
            var6 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(2).__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(-2))));
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(6060);
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
            var10000 = var4._eq(var1.getglobal("_locale").__getattr__("CHAR_MAX"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(6061);
               var6 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(6063);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unrecognised format for grouping")));
            }
         }
      }
   }

   public PyObject _insert_thousands_sep$252(PyFrame var1, ThreadState var2) {
      var1.setline(6080);
      PyString.fromInterned("Insert thousands separators into a digit string.\n\n    spec is a dictionary whose keys should include 'thousands_sep' and\n    'grouping'; typically it's the result of parsing the format\n    specifier using _parse_format_specifier.\n\n    The min_width keyword argument gives the minimum length of the\n    result, which will be padded on the left with zeros if necessary.\n\n    If necessary, the zero padding adds an extra '0' on the left to\n    avoid a leading thousands separator.  For example, inserting\n    commas every three digits in '123456', with min_width=8, gives\n    '0,123,456', even though that has length 9.\n\n    ");
      var1.setline(6082);
      PyObject var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("thousands_sep"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(6083);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("grouping"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(6085);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(6086);
      var3 = var1.getglobal("_group_lengths").__call__(var2, var1.getlocal(4)).__iter__();

      while(true) {
         var1.setline(6086);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(6098);
            var5 = var1.getglobal("max").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(6099);
            var1.getlocal(5).__getattr__("append").__call__(var2, PyString.fromInterned("0")._mul(var1.getlocal(6)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0))))._add(var1.getlocal(0).__getslice__(var1.getlocal(6).__neg__(), (PyObject)null, (PyObject)null)));
         } else {
            var1.setlocal(6, var4);
            var1.setline(6087);
            var5 = var1.getlocal(6);
            PyObject var10000 = var5._le(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(6088);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("group length should be positive")));
            }

            var1.setline(6090);
            var5 = var1.getglobal("min").__call__(var2, var1.getglobal("max").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1)), var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(6091);
            var1.getlocal(5).__getattr__("append").__call__(var2, PyString.fromInterned("0")._mul(var1.getlocal(6)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0))))._add(var1.getlocal(0).__getslice__(var1.getlocal(6).__neg__(), (PyObject)null, (PyObject)null)));
            var1.setline(6092);
            var5 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6).__neg__(), (PyObject)null);
            var1.setlocal(0, var5);
            var5 = null;
            var1.setline(6093);
            var5 = var1.getlocal(2);
            var5 = var5._isub(var1.getlocal(6));
            var1.setlocal(2, var5);
            var1.setline(6094);
            var10000 = var1.getlocal(0).__not__();
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2);
               var10000 = var5._le(Py.newInteger(0));
               var5 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(6096);
               var5 = var1.getlocal(2);
               var5 = var5._isub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
               var1.setlocal(2, var5);
               continue;
            }
         }

         var1.setline(6100);
         var3 = var1.getlocal(3).__getattr__("join").__call__(var2, var1.getglobal("reversed").__call__(var2, var1.getlocal(5)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _format_sign$253(PyFrame var1, ThreadState var2) {
      var1.setline(6103);
      PyString.fromInterned("Determine sign character.");
      var1.setline(6105);
      PyString var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(6106);
         var3 = PyString.fromInterned("-");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(6107);
         PyObject var4 = var1.getlocal(1).__getitem__(PyString.fromInterned("sign"));
         PyObject var10000 = var4._in(PyString.fromInterned(" +"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(6108);
            PyObject var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("sign"));
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(6110);
            var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _format_number$254(PyFrame var1, ThreadState var2) {
      var1.setline(6128);
      PyString.fromInterned("Format a number, given the following data:\n\n    is_negative: true if the number is negative, else false\n    intpart: string of digits that must appear before the decimal point\n    fracpart: string of digits that must come after the point\n    exp: exponent, as an integer\n    spec: dictionary resulting from parsing the format specifier\n\n    This function uses the information in spec to:\n      insert separators (decimal separator and thousands separators)\n      format the sign\n      format the exponent\n      add trailing '%' for the '%' type\n      zero-pad if necessary\n      fill and align if necessary\n    ");
      var1.setline(6130);
      PyObject var3 = var1.getglobal("_format_sign").__call__(var2, var1.getlocal(0), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(6132);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(6133);
         var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("decimal_point"))._add(var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(6135);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
         var10000 = var3._in(PyString.fromInterned("eE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(6136);
         var3 = (new PyDictionary(new PyObject[]{PyString.fromInterned("E"), PyString.fromInterned("E"), PyString.fromInterned("e"), PyString.fromInterned("e"), PyString.fromInterned("G"), PyString.fromInterned("E"), PyString.fromInterned("g"), PyString.fromInterned("e")})).__getitem__(var1.getlocal(4).__getitem__(PyString.fromInterned("type")));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(6137);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("{0}{1:+}").__getattr__("format").__call__(var2, var1.getlocal(6), var1.getlocal(3)));
         var1.setlocal(2, var3);
      }

      var1.setline(6138);
      var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("type"));
      var10000 = var3._eq(PyString.fromInterned("%"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(6139);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("%"));
         var1.setlocal(2, var3);
      }

      var1.setline(6141);
      if (var1.getlocal(4).__getitem__(PyString.fromInterned("zeropad")).__nonzero__()) {
         var1.setline(6142);
         var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("minimumwidth"))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(6144);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(7, var4);
         var3 = null;
      }

      var1.setline(6145);
      var3 = var1.getglobal("_insert_thousands_sep").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(7));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(6147);
      var3 = var1.getglobal("_format_align").__call__(var2, var1.getlocal(5), var1.getlocal(1)._add(var1.getlocal(2)), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public decimal$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args"};
      f$1 = Py.newCode(1, var2, var1, "<lambda>", 147, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DecimalException$2 = Py.newCode(0, var2, var1, "DecimalException", 161, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args"};
      handle$3 = Py.newCode(3, var2, var1, "handle", 180, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Clamped$4 = Py.newCode(0, var2, var1, "Clamped", 184, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidOperation$5 = Py.newCode(0, var2, var1, "InvalidOperation", 196, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args", "ans"};
      handle$6 = Py.newCode(3, var2, var1, "handle", 219, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConversionSyntax$7 = Py.newCode(0, var2, var1, "ConversionSyntax", 225, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args"};
      handle$8 = Py.newCode(3, var2, var1, "handle", 232, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DivisionByZero$9 = Py.newCode(0, var2, var1, "DivisionByZero", 235, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "sign", "args"};
      handle$10 = Py.newCode(4, var2, var1, "handle", 248, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DivisionImpossible$11 = Py.newCode(0, var2, var1, "DivisionImpossible", 251, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args"};
      handle$12 = Py.newCode(3, var2, var1, "handle", 259, true, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DivisionUndefined$13 = Py.newCode(0, var2, var1, "DivisionUndefined", 262, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args"};
      handle$14 = Py.newCode(3, var2, var1, "handle", 270, true, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Inexact$15 = Py.newCode(0, var2, var1, "Inexact", 273, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidContext$16 = Py.newCode(0, var2, var1, "InvalidContext", 285, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "args"};
      handle$17 = Py.newCode(3, var2, var1, "handle", 296, true, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Rounded$18 = Py.newCode(0, var2, var1, "Rounded", 299, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Subnormal$19 = Py.newCode(0, var2, var1, "Subnormal", 311, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Overflow$20 = Py.newCode(0, var2, var1, "Overflow", 322, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "sign", "args"};
      handle$21 = Py.newCode(4, var2, var1, "handle", 344, true, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Underflow$22 = Py.newCode(0, var2, var1, "Underflow", 360, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MockThreading$23 = Py.newCode(0, var2, var1, "MockThreading", 398, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sys"};
      local$24 = Py.newCode(2, var2, var1, "local", 399, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context"};
      setcontext$25 = Py.newCode(1, var2, var1, "setcontext", 422, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context"};
      getcontext$26 = Py.newCode(0, var2, var1, "getcontext", 429, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_local", "context"};
      getcontext$27 = Py.newCode(1, var2, var1, "getcontext", 449, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context", "_local"};
      setcontext$28 = Py.newCode(2, var2, var1, "setcontext", 463, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ctx"};
      localcontext$29 = Py.newCode(1, var2, var1, "localcontext", 472, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Decimal$30 = Py.newCode(0, var2, var1, "Decimal", 514, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "value", "context", "self", "m", "intpart", "fracpart", "exp", "diag", "digits", "digit"};
      __new__$31 = Py.newCode(3, var2, var1, "__new__", 523, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "f", "sign", "n", "d", "k", "result"};
      from_float$32 = Py.newCode(2, var2, var1, "from_float", 670, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exp"};
      _isnan$33 = Py.newCode(1, var2, var1, "_isnan", 708, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _isinfinity$34 = Py.newCode(1, var2, var1, "_isinfinity", 723, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "self_is_nan", "other_is_nan"};
      _check_nans$35 = Py.newCode(3, var2, var1, "_check_nans", 736, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      _compare_check_nans$36 = Py.newCode(3, var2, var1, "_compare_check_nans", 768, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __nonzero__$37 = Py.newCode(1, var2, var1, "__nonzero__", 801, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "self_inf", "other_inf", "self_adjusted", "other_adjusted", "self_padded", "other_padded"};
      _cmp$38 = Py.newCode(2, var2, var1, "_cmp", 808, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __eq__$39 = Py.newCode(3, var2, var1, "__eq__", 872, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __ne__$40 = Py.newCode(3, var2, var1, "__ne__", 880, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __lt__$41 = Py.newCode(3, var2, var1, "__lt__", 888, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __le__$42 = Py.newCode(3, var2, var1, "__le__", 897, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __gt__$43 = Py.newCode(3, var2, var1, "__gt__", 906, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __ge__$44 = Py.newCode(3, var2, var1, "__ge__", 915, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      compare$45 = Py.newCode(3, var2, var1, "compare", 924, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "self_as_float"};
      __hash__$46 = Py.newCode(1, var2, var1, "__hash__", 943, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      as_tuple$47 = Py.newCode(1, var2, var1, "as_tuple", 989, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$48 = Py.newCode(1, var2, var1, "__repr__", 996, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eng", "context", "sign", "leftdigits", "dotplace", "intpart", "fracpart", "exp"};
      __str__$49 = Py.newCode(3, var2, var1, "__str__", 1001, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      to_eng_string$50 = Py.newCode(2, var2, var1, "to_eng_string", 1053, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans"};
      __neg__$51 = Py.newCode(2, var2, var1, "__neg__", 1063, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans"};
      __pos__$52 = Py.newCode(2, var2, var1, "__pos__", 1083, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "round", "context", "ans"};
      __abs__$53 = Py.newCode(3, var2, var1, "__abs__", 1103, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "exp", "negativezero", "sign", "op1", "op2", "result"};
      __add__$54 = Py.newCode(3, var2, var1, "__add__", 1125, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __sub__$55 = Py.newCode(3, var2, var1, "__sub__", 1213, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rsub__$56 = Py.newCode(3, var2, var1, "__rsub__", 1227, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "resultsign", "ans", "resultexp", "op1", "op2"};
      __mul__$57 = Py.newCode(3, var2, var1, "__mul__", 1235, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sign", "ans", "exp", "coeff", "shift", "op1", "op2", "remainder", "ideal_exp"};
      __truediv__$58 = Py.newCode(3, var2, var1, "__truediv__", 1292, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sign", "ideal_exp", "expdiff", "op1", "op2", "q", "r", "ans"};
      _divide$59 = Py.newCode(3, var2, var1, "_divide", 1351, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rtruediv__$60 = Py.newCode(3, var2, var1, "__rtruediv__", 1384, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "sign", "quotient", "remainder"};
      __divmod__$61 = Py.newCode(3, var2, var1, "__divmod__", 1394, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rdivmod__$62 = Py.newCode(3, var2, var1, "__rdivmod__", 1430, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "remainder"};
      __mod__$63 = Py.newCode(3, var2, var1, "__mod__", 1437, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rmod__$64 = Py.newCode(3, var2, var1, "__rmod__", 1464, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "ideal_exponent", "expdiff", "op1", "op2", "q", "r", "sign"};
      remainder_near$65 = Py.newCode(3, var2, var1, "remainder_near", 1471, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      __floordiv__$66 = Py.newCode(3, var2, var1, "__floordiv__", 1546, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rfloordiv__$67 = Py.newCode(3, var2, var1, "__rfloordiv__", 1574, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      __float__$68 = Py.newCode(1, var2, var1, "__float__", 1581, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      __int__$69 = Py.newCode(1, var2, var1, "__int__", 1591, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      real$70 = Py.newCode(1, var2, var1, "real", 1606, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      imag$71 = Py.newCode(1, var2, var1, "imag", 1610, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      conjugate$72 = Py.newCode(1, var2, var1, "conjugate", 1614, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __complex__$73 = Py.newCode(1, var2, var1, "__complex__", 1617, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __long__$74 = Py.newCode(1, var2, var1, "__long__", 1620, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "payload", "max_payload_len"};
      _fix_nan$75 = Py.newCode(2, var2, var1, "_fix_nan", 1627, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "Etiny", "Etop", "exp_max", "new_exp", "exp_min", "ans", "self_is_subnormal", "digits", "rounding_method", "changed", "coeff", "self_padded"};
      _fix$76 = Py.newCode(2, var2, var1, "_fix", 1639, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_down$77 = Py.newCode(2, var2, var1, "_round_down", 1743, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_up$78 = Py.newCode(2, var2, var1, "_round_up", 1750, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_half_up$79 = Py.newCode(2, var2, var1, "_round_half_up", 1754, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_half_down$80 = Py.newCode(2, var2, var1, "_round_half_down", 1763, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_half_even$81 = Py.newCode(2, var2, var1, "_round_half_even", 1770, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_ceiling$82 = Py.newCode(2, var2, var1, "_round_ceiling", 1778, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_floor$83 = Py.newCode(2, var2, var1, "_round_floor", 1785, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prec"};
      _round_05up$84 = Py.newCode(2, var2, var1, "_round_05up", 1792, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "third", "context", "product"};
      fma$85 = Py.newCode(4, var2, var1, "fma", 1799, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "modulo", "context", "self_is_nan", "other_is_nan", "modulo_is_nan", "sign", "base", "exponent", "i"};
      _power_modulo$86 = Py.newCode(4, var2, var1, "_power_modulo", 1843, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "p", "x", "xc", "xe", "y", "yc", "ye", "exponent", "ideal_exponent", "zeros", "last_digit", "e", "y_as_int", "ten_pow", "remainder", "y_as_integer", "m", "n", "xc_bits", "rem", "a", "q", "r", "str_xc"};
      _power_exact$87 = Py.newCode(3, var2, var1, "_power_exact", 1927, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "modulo", "context", "ans", "result_sign", "multiplier", "exp", "self_adj", "exact", "bound", "Etiny", "p", "x", "xc", "xe", "y", "yc", "ye", "extra", "coeff", "expdiff", "newcontext", "exception"};
      __pow__$88 = Py.newCode(4, var2, var1, "__pow__", 2142, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context"};
      __rpow__$89 = Py.newCode(3, var2, var1, "__rpow__", 2358, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "dup", "exp_max", "end", "exp"};
      normalize$90 = Py.newCode(2, var2, var1, "normalize", 2365, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exp", "rounding", "context", "watchexp", "ans", "self_adjusted"};
      quantize$91 = Py.newCode(5, var2, var1, "quantize", 2390, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      same_quantum$92 = Py.newCode(2, var2, var1, "same_quantum", 2461, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exp", "rounding", "digits", "this_function", "changed", "coeff"};
      _rescale$93 = Py.newCode(3, var2, var1, "_rescale", 2476, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "places", "rounding", "ans"};
      _round$94 = Py.newCode(3, var2, var1, "_round", 2510, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rounding", "context", "ans"};
      to_integral_exact$95 = Py.newCode(3, var2, var1, "to_integral_exact", 2533, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rounding", "context", "ans"};
      to_integral_value$96 = Py.newCode(3, var2, var1, "to_integral_value", 2562, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "prec", "op", "e", "c", "l", "shift", "exact", "remainder", "n", "q", "rounding"};
      sqrt$97 = Py.newCode(2, var2, var1, "sqrt", 2581, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sn", "on", "c", "ans"};
      max$98 = Py.newCode(3, var2, var1, "max", 2680, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sn", "on", "c", "ans"};
      min$99 = Py.newCode(3, var2, var1, "min", 2722, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rest"};
      _isinteger$100 = Py.newCode(1, var2, var1, "_isinteger", 2756, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _iseven$101 = Py.newCode(1, var2, var1, "_iseven", 2765, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      adjusted$102 = Py.newCode(1, var2, var1, "adjusted", 2771, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      canonical$103 = Py.newCode(2, var2, var1, "canonical", 2779, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans"};
      compare_signal$104 = Py.newCode(3, var2, var1, "compare_signal", 2787, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "sign", "self_nan", "other_nan", "self_key", "other_key"};
      compare_total$105 = Py.newCode(2, var2, var1, "compare_total", 2799, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "s", "o"};
      compare_total_mag$106 = Py.newCode(2, var2, var1, "compare_total_mag", 2872, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy_abs$107 = Py.newCode(1, var2, var1, "copy_abs", 2883, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy_negate$108 = Py.newCode(1, var2, var1, "copy_negate", 2887, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      copy_sign$109 = Py.newCode(2, var2, var1, "copy_sign", 2894, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "p", "adj", "op", "c", "e", "extra", "coeff", "exp", "rounding"};
      exp$110 = Py.newCode(2, var2, var1, "exp", 2900, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_canonical$111 = Py.newCode(1, var2, var1, "is_canonical", 2975, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_finite$112 = Py.newCode(1, var2, var1, "is_finite", 2983, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_infinite$113 = Py.newCode(1, var2, var1, "is_infinite", 2991, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_nan$114 = Py.newCode(1, var2, var1, "is_nan", 2995, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      is_normal$115 = Py.newCode(2, var2, var1, "is_normal", 2999, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_qnan$116 = Py.newCode(1, var2, var1, "is_qnan", 3007, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_signed$117 = Py.newCode(1, var2, var1, "is_signed", 3011, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_snan$118 = Py.newCode(1, var2, var1, "is_snan", 3015, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      is_subnormal$119 = Py.newCode(2, var2, var1, "is_subnormal", 3019, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_zero$120 = Py.newCode(1, var2, var1, "is_zero", 3027, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "adj", "op", "c", "e", "num", "den"};
      _ln_exp_bound$121 = Py.newCode(1, var2, var1, "_ln_exp_bound", 3031, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "op", "c", "e", "p", "places", "coeff", "rounding"};
      ln$122 = Py.newCode(2, var2, var1, "ln", 3056, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "adj", "op", "c", "e", "num", "den"};
      _log10_exp_bound$123 = Py.newCode(1, var2, var1, "_log10_exp_bound", 3106, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "op", "c", "e", "p", "places", "coeff", "rounding"};
      log10$124 = Py.newCode(2, var2, var1, "log10", 3136, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans"};
      logb$125 = Py.newCode(2, var2, var1, "logb", 3187, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dig"};
      _islogical$126 = Py.newCode(1, var2, var1, "_islogical", 3217, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "opa", "opb", "dif"};
      _fill_logical$127 = Py.newCode(4, var2, var1, "_fill_logical", 3231, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "opa", "opb", "result", "_[3258_26]", "a", "b"};
      logical_and$128 = Py.newCode(3, var2, var1, "logical_and", 3244, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      logical_invert$129 = Py.newCode(2, var2, var1, "logical_invert", 3261, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "opa", "opb", "result", "_[3282_26]", "a", "b"};
      logical_or$130 = Py.newCode(3, var2, var1, "logical_or", 3268, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "opa", "opb", "result", "_[3299_26]", "a", "b"};
      logical_xor$131 = Py.newCode(3, var2, var1, "logical_xor", 3285, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sn", "on", "c", "ans"};
      max_mag$132 = Py.newCode(3, var2, var1, "max_mag", 3302, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "sn", "on", "c", "ans"};
      min_mag$133 = Py.newCode(3, var2, var1, "min_mag", 3332, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "new_self"};
      next_minus$134 = Py.newCode(2, var2, var1, "next_minus", 3362, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "ans", "new_self"};
      next_plus$135 = Py.newCode(2, var2, var1, "next_plus", 3385, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "comparison"};
      next_toward$136 = Py.newCode(3, var2, var1, "next_toward", 3408, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "inf"};
      number_class$137 = Py.newCode(2, var2, var1, "number_class", 3454, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      radix$138 = Py.newCode(1, var2, var1, "radix", 3496, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "torot", "rotdig", "topad", "rotated"};
      rotate$139 = Py.newCode(3, var2, var1, "rotate", 3500, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "liminf", "limsup", "d"};
      scaleb$140 = Py.newCode(3, var2, var1, "scaleb", 3533, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "context", "ans", "torot", "rotdig", "topad", "shifted"};
      shift$141 = Py.newCode(3, var2, var1, "shift", 3558, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __reduce__$142 = Py.newCode(1, var2, var1, "__reduce__", 3597, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __copy__$143 = Py.newCode(1, var2, var1, "__copy__", 3600, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "memo"};
      __deepcopy__$144 = Py.newCode(2, var2, var1, "__deepcopy__", 3605, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "specifier", "context", "_localeconv", "spec", "sign", "body", "rounding", "precision", "leftdigits", "dotplace", "intpart", "fracpart", "exp"};
      __format__$145 = Py.newCode(4, var2, var1, "__format__", 3612, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "java_class"};
      __tojava__$146 = Py.newCode(2, var2, var1, "__tojava__", 3693, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sign", "coefficient", "exponent", "special", "self"};
      _dec_from_triple$147 = Py.newCode(4, var2, var1, "_dec_from_triple", 3702, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ContextManager$148 = Py.newCode(0, var2, var1, "_ContextManager", 3738, false, false, self, 148, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "new_context"};
      __init__$149 = Py.newCode(2, var2, var1, "__init__", 3744, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$150 = Py.newCode(1, var2, var1, "__enter__", 3746, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t", "v", "tb"};
      __exit__$151 = Py.newCode(4, var2, var1, "__exit__", 3750, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Context$152 = Py.newCode(0, var2, var1, "Context", 3753, false, false, self, 152, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "prec", "rounding", "traps", "flags", "Emin", "Emax", "capitals", "_clamp", "_ignored_flags", "dc", "_(3799_30)", "_(3806_30)"};
      String[] var10001 = var2;
      decimal$py var10007 = self;
      var2 = new String[]{"flags", "traps"};
      __init__$153 = Py.newCode(10, var10001, var1, "__init__", 3772, false, false, var10007, 153, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "s"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"traps"};
      f$154 = Py.newCode(1, var10001, var1, "<genexpr>", 3799, false, false, var10007, 154, (String[])null, var2, 0, 4129);
      var2 = new String[]{"_(x)", "s"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"flags"};
      f$155 = Py.newCode(1, var10001, var1, "<genexpr>", 3806, false, false, var10007, 155, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "s", "names", "_[3816_17]", "f", "v", "_[3818_17]", "t"};
      __repr__$156 = Py.newCode(1, var2, var1, "__repr__", 3810, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      clear_flags$157 = Py.newCode(1, var2, var1, "clear_flags", 3822, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nc"};
      _shallow_copy$158 = Py.newCode(1, var2, var1, "_shallow_copy", 3827, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nc"};
      copy$159 = Py.newCode(1, var2, var1, "copy", 3834, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "condition", "explanation", "args", "error"};
      _raise_error$160 = Py.newCode(4, var2, var1, "_raise_error", 3842, true, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _ignore_all_flags$161 = Py.newCode(1, var2, var1, "_ignore_all_flags", 3864, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flags"};
      _ignore_flags$162 = Py.newCode(2, var2, var1, "_ignore_flags", 3868, true, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flags", "flag"};
      _regard_flags$163 = Py.newCode(2, var2, var1, "_regard_flags", 3875, true, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      Etiny$164 = Py.newCode(1, var2, var1, "Etiny", 3885, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      Etop$165 = Py.newCode(1, var2, var1, "Etop", 3889, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "rounding"};
      _set_rounding$166 = Py.newCode(2, var2, var1, "_set_rounding", 3893, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "num", "d"};
      create_decimal$167 = Py.newCode(2, var2, var1, "create_decimal", 3912, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "d"};
      create_decimal_from_float$168 = Py.newCode(2, var2, var1, "create_decimal_from_float", 3929, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      abs$169 = Py.newCode(2, var2, var1, "abs", 3947, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      add$170 = Py.newCode(3, var2, var1, "add", 3968, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      _apply$171 = Py.newCode(2, var2, var1, "_apply", 3989, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      canonical$172 = Py.newCode(2, var2, var1, "canonical", 3992, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      compare$173 = Py.newCode(3, var2, var1, "compare", 4003, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      compare_signal$174 = Py.newCode(3, var2, var1, "compare_signal", 4039, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      compare_total$175 = Py.newCode(3, var2, var1, "compare_total", 4074, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      compare_total_mag$176 = Py.newCode(3, var2, var1, "compare_total_mag", 4103, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      copy_abs$177 = Py.newCode(2, var2, var1, "copy_abs", 4111, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      copy_decimal$178 = Py.newCode(2, var2, var1, "copy_decimal", 4124, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      copy_negate$179 = Py.newCode(2, var2, var1, "copy_negate", 4137, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      copy_sign$180 = Py.newCode(3, var2, var1, "copy_sign", 4150, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      divide$181 = Py.newCode(3, var2, var1, "divide", 4174, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      divide_int$182 = Py.newCode(3, var2, var1, "divide_int", 4211, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      divmod$183 = Py.newCode(3, var2, var1, "divmod", 4234, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      exp$184 = Py.newCode(2, var2, var1, "exp", 4255, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "c"};
      fma$185 = Py.newCode(4, var2, var1, "fma", 4279, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_canonical$186 = Py.newCode(2, var2, var1, "is_canonical", 4302, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_finite$187 = Py.newCode(2, var2, var1, "is_finite", 4313, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_infinite$188 = Py.newCode(2, var2, var1, "is_infinite", 4335, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_nan$189 = Py.newCode(2, var2, var1, "is_nan", 4350, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_normal$190 = Py.newCode(2, var2, var1, "is_normal", 4366, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_qnan$191 = Py.newCode(2, var2, var1, "is_qnan", 4389, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_signed$192 = Py.newCode(2, var2, var1, "is_signed", 4404, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_snan$193 = Py.newCode(2, var2, var1, "is_snan", 4421, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_subnormal$194 = Py.newCode(2, var2, var1, "is_subnormal", 4437, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      is_zero$195 = Py.newCode(2, var2, var1, "is_zero", 4459, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      ln$196 = Py.newCode(2, var2, var1, "ln", 4476, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      log10$197 = Py.newCode(2, var2, var1, "log10", 4498, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      logb$198 = Py.newCode(2, var2, var1, "logb", 4526, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      logical_and$199 = Py.newCode(3, var2, var1, "logical_and", 4552, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      logical_invert$200 = Py.newCode(2, var2, var1, "logical_invert", 4579, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      logical_or$201 = Py.newCode(3, var2, var1, "logical_or", 4598, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      logical_xor$202 = Py.newCode(3, var2, var1, "logical_xor", 4625, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      max$203 = Py.newCode(3, var2, var1, "max", 4652, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      max_mag$204 = Py.newCode(3, var2, var1, "max_mag", 4679, false, false, self, 204, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      min$205 = Py.newCode(3, var2, var1, "min", 4696, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      min_mag$206 = Py.newCode(3, var2, var1, "min_mag", 4723, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      minus$207 = Py.newCode(2, var2, var1, "minus", 4740, false, false, self, 207, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      multiply$208 = Py.newCode(3, var2, var1, "multiply", 4757, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      next_minus$209 = Py.newCode(2, var2, var1, "next_minus", 4789, false, false, self, 209, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      next_plus$210 = Py.newCode(2, var2, var1, "next_plus", 4809, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      next_toward$211 = Py.newCode(3, var2, var1, "next_toward", 4829, false, false, self, 211, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      normalize$212 = Py.newCode(2, var2, var1, "normalize", 4864, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      number_class$213 = Py.newCode(2, var2, var1, "number_class", 4888, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      plus$214 = Py.newCode(2, var2, var1, "plus", 4938, false, false, self, 214, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "modulo", "r"};
      power$215 = Py.newCode(4, var2, var1, "power", 4955, false, false, self, 215, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      quantize$216 = Py.newCode(3, var2, var1, "quantize", 5035, false, false, self, 216, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      radix$217 = Py.newCode(1, var2, var1, "radix", 5093, false, false, self, 217, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      remainder$218 = Py.newCode(3, var2, var1, "remainder", 5101, false, false, self, 218, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      remainder_near$219 = Py.newCode(3, var2, var1, "remainder_near", 5139, false, false, self, 219, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      rotate$220 = Py.newCode(3, var2, var1, "rotate", 5173, false, false, self, 220, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      same_quantum$221 = Py.newCode(3, var2, var1, "same_quantum", 5202, false, false, self, 221, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      scaleb$222 = Py.newCode(3, var2, var1, "scaleb", 5226, false, false, self, 222, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      shift$223 = Py.newCode(3, var2, var1, "shift", 5245, false, false, self, 223, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      sqrt$224 = Py.newCode(2, var2, var1, "sqrt", 5275, false, false, self, 224, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "r"};
      subtract$225 = Py.newCode(3, var2, var1, "subtract", 5307, false, false, self, 225, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      to_eng_string$226 = Py.newCode(2, var2, var1, "to_eng_string", 5330, false, false, self, 226, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      to_sci_string$227 = Py.newCode(2, var2, var1, "to_sci_string", 5338, false, false, self, 227, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      to_integral_exact$228 = Py.newCode(2, var2, var1, "to_integral_exact", 5346, false, false, self, 228, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      to_integral_value$229 = Py.newCode(2, var2, var1, "to_integral_value", 5376, false, false, self, 229, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _WorkRep$230 = Py.newCode(0, var2, var1, "_WorkRep", 5408, false, false, self, 230, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$231 = Py.newCode(2, var2, var1, "__init__", 5414, false, false, self, 231, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$232 = Py.newCode(1, var2, var1, "__repr__", 5429, false, false, self, 232, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"op1", "op2", "prec", "tmp", "other", "tmp_len", "other_len", "exp"};
      _normalize$233 = Py.newCode(3, var2, var1, "_normalize", 5436, false, false, self, 233, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "correction", "hex_n"};
      _nbits$234 = Py.newCode(2, var2, var1, "_nbits", 5471, false, false, self, 234, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "a", "b"};
      _sqrt_nearest$235 = Py.newCode(2, var2, var1, "_sqrt_nearest", 5484, false, false, self, 235, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "shift", "b", "q"};
      _rshift_nearest$236 = Py.newCode(2, var2, var1, "_rshift_nearest", 5499, false, false, self, 236, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "q", "r"};
      _div_nearest$237 = Py.newCode(2, var2, var1, "_div_nearest", 5507, false, false, self, 237, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "M", "L", "y", "R", "T", "yshift", "w", "k"};
      _ilog$238 = Py.newCode(3, var2, var1, "_ilog", 5515, false, false, self, 238, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "e", "p", "l", "f", "M", "k", "log_d", "log_10", "log_tenpower"};
      _dlog10$239 = Py.newCode(3, var2, var1, "_dlog10", 5563, false, false, self, 239, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "e", "p", "l", "f", "k", "log_d", "extra", "f_log_ten"};
      _dlog$240 = Py.newCode(3, var2, var1, "_dlog", 5597, false, false, self, 240, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Log10Memoize$241 = Py.newCode(0, var2, var1, "_Log10Memoize", 5641, false, false, self, 241, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$242 = Py.newCode(1, var2, var1, "__init__", 5645, false, false, self, 242, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "p", "extra", "M", "digits"};
      getdigits$243 = Py.newCode(2, var2, var1, "getdigits", 5648, false, false, self, 243, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "M", "L", "R", "T", "y", "Mshift", "i", "k"};
      _iexp$244 = Py.newCode(3, var2, var1, "_iexp", 5678, false, false, self, 244, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "e", "p", "extra", "q", "shift", "cshift", "quot", "rem"};
      _dexp$245 = Py.newCode(3, var2, var1, "_dexp", 5715, false, false, self, 245, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"xc", "xe", "yc", "ye", "p", "b", "lxc", "shift", "pc", "coeff", "exp"};
      _dpower$246 = Py.newCode(5, var2, var1, "_dpower", 5751, false, false, self, 246, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "correction", "str_c"};
      _log10_lb$247 = Py.newCode(2, var2, var1, "_log10_lb", 5793, false, false, self, 247, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"other", "raiseit", "allow_float"};
      _convert_other$248 = Py.newCode(3, var2, var1, "_convert_other", 5804, false, false, self, 248, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"format_spec", "_localeconv", "m", "format_dict", "fill", "align"};
      _parse_format_specifier$249 = Py.newCode(2, var2, var1, "_parse_format_specifier", 5925, false, false, self, 249, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sign", "body", "spec", "minimumwidth", "fill", "padding", "align", "result", "half"};
      _format_align$250 = Py.newCode(3, var2, var1, "_format_align", 6009, false, false, self, 250, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"grouping", "chain", "repeat"};
      _group_lengths$251 = Py.newCode(1, var2, var1, "_group_lengths", 6042, false, false, self, 251, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"digits", "spec", "min_width", "sep", "grouping", "groups", "l"};
      _insert_thousands_sep$252 = Py.newCode(3, var2, var1, "_insert_thousands_sep", 6065, false, false, self, 252, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"is_negative", "spec"};
      _format_sign$253 = Py.newCode(2, var2, var1, "_format_sign", 6102, false, false, self, 253, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"is_negative", "intpart", "fracpart", "exp", "spec", "sign", "echar", "min_width"};
      _format_number$254 = Py.newCode(5, var2, var1, "_format_number", 6112, false, false, self, 254, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new decimal$py("decimal$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(decimal$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.f$1(var2, var3);
         case 2:
            return this.DecimalException$2(var2, var3);
         case 3:
            return this.handle$3(var2, var3);
         case 4:
            return this.Clamped$4(var2, var3);
         case 5:
            return this.InvalidOperation$5(var2, var3);
         case 6:
            return this.handle$6(var2, var3);
         case 7:
            return this.ConversionSyntax$7(var2, var3);
         case 8:
            return this.handle$8(var2, var3);
         case 9:
            return this.DivisionByZero$9(var2, var3);
         case 10:
            return this.handle$10(var2, var3);
         case 11:
            return this.DivisionImpossible$11(var2, var3);
         case 12:
            return this.handle$12(var2, var3);
         case 13:
            return this.DivisionUndefined$13(var2, var3);
         case 14:
            return this.handle$14(var2, var3);
         case 15:
            return this.Inexact$15(var2, var3);
         case 16:
            return this.InvalidContext$16(var2, var3);
         case 17:
            return this.handle$17(var2, var3);
         case 18:
            return this.Rounded$18(var2, var3);
         case 19:
            return this.Subnormal$19(var2, var3);
         case 20:
            return this.Overflow$20(var2, var3);
         case 21:
            return this.handle$21(var2, var3);
         case 22:
            return this.Underflow$22(var2, var3);
         case 23:
            return this.MockThreading$23(var2, var3);
         case 24:
            return this.local$24(var2, var3);
         case 25:
            return this.setcontext$25(var2, var3);
         case 26:
            return this.getcontext$26(var2, var3);
         case 27:
            return this.getcontext$27(var2, var3);
         case 28:
            return this.setcontext$28(var2, var3);
         case 29:
            return this.localcontext$29(var2, var3);
         case 30:
            return this.Decimal$30(var2, var3);
         case 31:
            return this.__new__$31(var2, var3);
         case 32:
            return this.from_float$32(var2, var3);
         case 33:
            return this._isnan$33(var2, var3);
         case 34:
            return this._isinfinity$34(var2, var3);
         case 35:
            return this._check_nans$35(var2, var3);
         case 36:
            return this._compare_check_nans$36(var2, var3);
         case 37:
            return this.__nonzero__$37(var2, var3);
         case 38:
            return this._cmp$38(var2, var3);
         case 39:
            return this.__eq__$39(var2, var3);
         case 40:
            return this.__ne__$40(var2, var3);
         case 41:
            return this.__lt__$41(var2, var3);
         case 42:
            return this.__le__$42(var2, var3);
         case 43:
            return this.__gt__$43(var2, var3);
         case 44:
            return this.__ge__$44(var2, var3);
         case 45:
            return this.compare$45(var2, var3);
         case 46:
            return this.__hash__$46(var2, var3);
         case 47:
            return this.as_tuple$47(var2, var3);
         case 48:
            return this.__repr__$48(var2, var3);
         case 49:
            return this.__str__$49(var2, var3);
         case 50:
            return this.to_eng_string$50(var2, var3);
         case 51:
            return this.__neg__$51(var2, var3);
         case 52:
            return this.__pos__$52(var2, var3);
         case 53:
            return this.__abs__$53(var2, var3);
         case 54:
            return this.__add__$54(var2, var3);
         case 55:
            return this.__sub__$55(var2, var3);
         case 56:
            return this.__rsub__$56(var2, var3);
         case 57:
            return this.__mul__$57(var2, var3);
         case 58:
            return this.__truediv__$58(var2, var3);
         case 59:
            return this._divide$59(var2, var3);
         case 60:
            return this.__rtruediv__$60(var2, var3);
         case 61:
            return this.__divmod__$61(var2, var3);
         case 62:
            return this.__rdivmod__$62(var2, var3);
         case 63:
            return this.__mod__$63(var2, var3);
         case 64:
            return this.__rmod__$64(var2, var3);
         case 65:
            return this.remainder_near$65(var2, var3);
         case 66:
            return this.__floordiv__$66(var2, var3);
         case 67:
            return this.__rfloordiv__$67(var2, var3);
         case 68:
            return this.__float__$68(var2, var3);
         case 69:
            return this.__int__$69(var2, var3);
         case 70:
            return this.real$70(var2, var3);
         case 71:
            return this.imag$71(var2, var3);
         case 72:
            return this.conjugate$72(var2, var3);
         case 73:
            return this.__complex__$73(var2, var3);
         case 74:
            return this.__long__$74(var2, var3);
         case 75:
            return this._fix_nan$75(var2, var3);
         case 76:
            return this._fix$76(var2, var3);
         case 77:
            return this._round_down$77(var2, var3);
         case 78:
            return this._round_up$78(var2, var3);
         case 79:
            return this._round_half_up$79(var2, var3);
         case 80:
            return this._round_half_down$80(var2, var3);
         case 81:
            return this._round_half_even$81(var2, var3);
         case 82:
            return this._round_ceiling$82(var2, var3);
         case 83:
            return this._round_floor$83(var2, var3);
         case 84:
            return this._round_05up$84(var2, var3);
         case 85:
            return this.fma$85(var2, var3);
         case 86:
            return this._power_modulo$86(var2, var3);
         case 87:
            return this._power_exact$87(var2, var3);
         case 88:
            return this.__pow__$88(var2, var3);
         case 89:
            return this.__rpow__$89(var2, var3);
         case 90:
            return this.normalize$90(var2, var3);
         case 91:
            return this.quantize$91(var2, var3);
         case 92:
            return this.same_quantum$92(var2, var3);
         case 93:
            return this._rescale$93(var2, var3);
         case 94:
            return this._round$94(var2, var3);
         case 95:
            return this.to_integral_exact$95(var2, var3);
         case 96:
            return this.to_integral_value$96(var2, var3);
         case 97:
            return this.sqrt$97(var2, var3);
         case 98:
            return this.max$98(var2, var3);
         case 99:
            return this.min$99(var2, var3);
         case 100:
            return this._isinteger$100(var2, var3);
         case 101:
            return this._iseven$101(var2, var3);
         case 102:
            return this.adjusted$102(var2, var3);
         case 103:
            return this.canonical$103(var2, var3);
         case 104:
            return this.compare_signal$104(var2, var3);
         case 105:
            return this.compare_total$105(var2, var3);
         case 106:
            return this.compare_total_mag$106(var2, var3);
         case 107:
            return this.copy_abs$107(var2, var3);
         case 108:
            return this.copy_negate$108(var2, var3);
         case 109:
            return this.copy_sign$109(var2, var3);
         case 110:
            return this.exp$110(var2, var3);
         case 111:
            return this.is_canonical$111(var2, var3);
         case 112:
            return this.is_finite$112(var2, var3);
         case 113:
            return this.is_infinite$113(var2, var3);
         case 114:
            return this.is_nan$114(var2, var3);
         case 115:
            return this.is_normal$115(var2, var3);
         case 116:
            return this.is_qnan$116(var2, var3);
         case 117:
            return this.is_signed$117(var2, var3);
         case 118:
            return this.is_snan$118(var2, var3);
         case 119:
            return this.is_subnormal$119(var2, var3);
         case 120:
            return this.is_zero$120(var2, var3);
         case 121:
            return this._ln_exp_bound$121(var2, var3);
         case 122:
            return this.ln$122(var2, var3);
         case 123:
            return this._log10_exp_bound$123(var2, var3);
         case 124:
            return this.log10$124(var2, var3);
         case 125:
            return this.logb$125(var2, var3);
         case 126:
            return this._islogical$126(var2, var3);
         case 127:
            return this._fill_logical$127(var2, var3);
         case 128:
            return this.logical_and$128(var2, var3);
         case 129:
            return this.logical_invert$129(var2, var3);
         case 130:
            return this.logical_or$130(var2, var3);
         case 131:
            return this.logical_xor$131(var2, var3);
         case 132:
            return this.max_mag$132(var2, var3);
         case 133:
            return this.min_mag$133(var2, var3);
         case 134:
            return this.next_minus$134(var2, var3);
         case 135:
            return this.next_plus$135(var2, var3);
         case 136:
            return this.next_toward$136(var2, var3);
         case 137:
            return this.number_class$137(var2, var3);
         case 138:
            return this.radix$138(var2, var3);
         case 139:
            return this.rotate$139(var2, var3);
         case 140:
            return this.scaleb$140(var2, var3);
         case 141:
            return this.shift$141(var2, var3);
         case 142:
            return this.__reduce__$142(var2, var3);
         case 143:
            return this.__copy__$143(var2, var3);
         case 144:
            return this.__deepcopy__$144(var2, var3);
         case 145:
            return this.__format__$145(var2, var3);
         case 146:
            return this.__tojava__$146(var2, var3);
         case 147:
            return this._dec_from_triple$147(var2, var3);
         case 148:
            return this._ContextManager$148(var2, var3);
         case 149:
            return this.__init__$149(var2, var3);
         case 150:
            return this.__enter__$150(var2, var3);
         case 151:
            return this.__exit__$151(var2, var3);
         case 152:
            return this.Context$152(var2, var3);
         case 153:
            return this.__init__$153(var2, var3);
         case 154:
            return this.f$154(var2, var3);
         case 155:
            return this.f$155(var2, var3);
         case 156:
            return this.__repr__$156(var2, var3);
         case 157:
            return this.clear_flags$157(var2, var3);
         case 158:
            return this._shallow_copy$158(var2, var3);
         case 159:
            return this.copy$159(var2, var3);
         case 160:
            return this._raise_error$160(var2, var3);
         case 161:
            return this._ignore_all_flags$161(var2, var3);
         case 162:
            return this._ignore_flags$162(var2, var3);
         case 163:
            return this._regard_flags$163(var2, var3);
         case 164:
            return this.Etiny$164(var2, var3);
         case 165:
            return this.Etop$165(var2, var3);
         case 166:
            return this._set_rounding$166(var2, var3);
         case 167:
            return this.create_decimal$167(var2, var3);
         case 168:
            return this.create_decimal_from_float$168(var2, var3);
         case 169:
            return this.abs$169(var2, var3);
         case 170:
            return this.add$170(var2, var3);
         case 171:
            return this._apply$171(var2, var3);
         case 172:
            return this.canonical$172(var2, var3);
         case 173:
            return this.compare$173(var2, var3);
         case 174:
            return this.compare_signal$174(var2, var3);
         case 175:
            return this.compare_total$175(var2, var3);
         case 176:
            return this.compare_total_mag$176(var2, var3);
         case 177:
            return this.copy_abs$177(var2, var3);
         case 178:
            return this.copy_decimal$178(var2, var3);
         case 179:
            return this.copy_negate$179(var2, var3);
         case 180:
            return this.copy_sign$180(var2, var3);
         case 181:
            return this.divide$181(var2, var3);
         case 182:
            return this.divide_int$182(var2, var3);
         case 183:
            return this.divmod$183(var2, var3);
         case 184:
            return this.exp$184(var2, var3);
         case 185:
            return this.fma$185(var2, var3);
         case 186:
            return this.is_canonical$186(var2, var3);
         case 187:
            return this.is_finite$187(var2, var3);
         case 188:
            return this.is_infinite$188(var2, var3);
         case 189:
            return this.is_nan$189(var2, var3);
         case 190:
            return this.is_normal$190(var2, var3);
         case 191:
            return this.is_qnan$191(var2, var3);
         case 192:
            return this.is_signed$192(var2, var3);
         case 193:
            return this.is_snan$193(var2, var3);
         case 194:
            return this.is_subnormal$194(var2, var3);
         case 195:
            return this.is_zero$195(var2, var3);
         case 196:
            return this.ln$196(var2, var3);
         case 197:
            return this.log10$197(var2, var3);
         case 198:
            return this.logb$198(var2, var3);
         case 199:
            return this.logical_and$199(var2, var3);
         case 200:
            return this.logical_invert$200(var2, var3);
         case 201:
            return this.logical_or$201(var2, var3);
         case 202:
            return this.logical_xor$202(var2, var3);
         case 203:
            return this.max$203(var2, var3);
         case 204:
            return this.max_mag$204(var2, var3);
         case 205:
            return this.min$205(var2, var3);
         case 206:
            return this.min_mag$206(var2, var3);
         case 207:
            return this.minus$207(var2, var3);
         case 208:
            return this.multiply$208(var2, var3);
         case 209:
            return this.next_minus$209(var2, var3);
         case 210:
            return this.next_plus$210(var2, var3);
         case 211:
            return this.next_toward$211(var2, var3);
         case 212:
            return this.normalize$212(var2, var3);
         case 213:
            return this.number_class$213(var2, var3);
         case 214:
            return this.plus$214(var2, var3);
         case 215:
            return this.power$215(var2, var3);
         case 216:
            return this.quantize$216(var2, var3);
         case 217:
            return this.radix$217(var2, var3);
         case 218:
            return this.remainder$218(var2, var3);
         case 219:
            return this.remainder_near$219(var2, var3);
         case 220:
            return this.rotate$220(var2, var3);
         case 221:
            return this.same_quantum$221(var2, var3);
         case 222:
            return this.scaleb$222(var2, var3);
         case 223:
            return this.shift$223(var2, var3);
         case 224:
            return this.sqrt$224(var2, var3);
         case 225:
            return this.subtract$225(var2, var3);
         case 226:
            return this.to_eng_string$226(var2, var3);
         case 227:
            return this.to_sci_string$227(var2, var3);
         case 228:
            return this.to_integral_exact$228(var2, var3);
         case 229:
            return this.to_integral_value$229(var2, var3);
         case 230:
            return this._WorkRep$230(var2, var3);
         case 231:
            return this.__init__$231(var2, var3);
         case 232:
            return this.__repr__$232(var2, var3);
         case 233:
            return this._normalize$233(var2, var3);
         case 234:
            return this._nbits$234(var2, var3);
         case 235:
            return this._sqrt_nearest$235(var2, var3);
         case 236:
            return this._rshift_nearest$236(var2, var3);
         case 237:
            return this._div_nearest$237(var2, var3);
         case 238:
            return this._ilog$238(var2, var3);
         case 239:
            return this._dlog10$239(var2, var3);
         case 240:
            return this._dlog$240(var2, var3);
         case 241:
            return this._Log10Memoize$241(var2, var3);
         case 242:
            return this.__init__$242(var2, var3);
         case 243:
            return this.getdigits$243(var2, var3);
         case 244:
            return this._iexp$244(var2, var3);
         case 245:
            return this._dexp$245(var2, var3);
         case 246:
            return this._dpower$246(var2, var3);
         case 247:
            return this._log10_lb$247(var2, var3);
         case 248:
            return this._convert_other$248(var2, var3);
         case 249:
            return this._parse_format_specifier$249(var2, var3);
         case 250:
            return this._format_align$250(var2, var3);
         case 251:
            return this._group_lengths$251(var2, var3);
         case 252:
            return this._insert_thousands_sep$252(var2, var3);
         case 253:
            return this._format_sign$253(var2, var3);
         case 254:
            return this._format_number$254(var2, var3);
         default:
            return null;
      }
   }
}

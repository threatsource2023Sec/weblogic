package org.python.core;

import java.io.Serializable;
import java.math.BigDecimal;
import org.python.core.stringlib.FloatFormatter;
import org.python.core.stringlib.InternalFormat;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.modules.math;

@Untraversable
@ExposedType(
   name = "float",
   doc = "float(x) -> floating point number\n\nConvert a string or number to a floating point number, if possible."
)
public class PyFloat extends PyObject {
   public static final PyType TYPE;
   static final InternalFormat.Spec SPEC_REPR;
   static final InternalFormat.Spec SPEC_STR;
   static final PyFloat ZERO;
   static final PyFloat ONE;
   static final PyFloat NAN;
   private final double value;
   private static double INT_LONG_BOUNDARY;
   public static volatile Format double_format;
   public static volatile Format float_format;

   public double getValue() {
      return this.value;
   }

   public PyFloat(PyType subtype, double v) {
      super(subtype);
      this.value = v;
   }

   public PyFloat(double v) {
      this(TYPE, v);
   }

   public PyFloat(float v) {
      this((double)v);
   }

   @ExposedNew
   public static PyObject float_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("float", args, keywords, new String[]{"x"}, 0);
      PyObject x = ap.getPyObject(0, (PyObject)null);
      if (x == null) {
         return (PyObject)(new_.for_type == subtype ? ZERO : new PyFloatDerived(subtype, 0.0));
      } else {
         PyFloat floatObject = null;

         try {
            floatObject = x.__float__();
         } catch (PyException var9) {
            if (var9.match(Py.AttributeError)) {
               throw Py.TypeError("float() argument must be a string or a number");
            }

            throw var9;
         }

         return (PyObject)(new_.for_type == subtype ? floatObject : new PyFloatDerived(subtype, floatObject.getValue()));
      }
   }

   public PyObject getReal() {
      return this.float___float__();
   }

   public PyObject getImag() {
      return ZERO;
   }

   public static PyObject float_fromhex(PyType type, PyObject o) {
      String message = "invalid hexadecimal floating-point string";
      boolean negative = false;
      PyString s = o.__str__();
      String value = s.getString().trim().toLowerCase();
      if (value.length() == 0) {
         throw Py.ValueError(message);
      } else if (!value.equals("nan") && !value.equals("-nan") && !value.equals("+nan")) {
         if (!value.equals("inf") && !value.equals("infinity") && !value.equals("+inf") && !value.equals("+infinity")) {
            if (!value.equals("-inf") && !value.equals("-infinity")) {
               if (value.charAt(0) == '-') {
                  value = value.substring(1);
                  negative = true;
               } else if (value.charAt(0) == '+') {
                  value = value.substring(1);
               }

               if (value.length() == 0) {
                  throw Py.ValueError(message);
               } else {
                  if (!value.startsWith("0x") && !value.startsWith("0X")) {
                     value = "0x" + value;
                  }

                  if (negative) {
                     value = "-" + value;
                  }

                  if (value.indexOf(112) == -1) {
                     value = value + "p0";
                  }

                  try {
                     double d = Double.parseDouble(value);
                     if (Double.isInfinite(d)) {
                        throw Py.OverflowError("hexadecimal value too large to represent as a float");
                     } else {
                        return new PyFloat(d);
                     }
                  } catch (NumberFormatException var9) {
                     throw Py.ValueError(message);
                  }
               }
            } else {
               return new PyFloat(Double.NEGATIVE_INFINITY);
            }
         } else {
            return new PyFloat(Double.POSITIVE_INFINITY);
         }
      } else {
         return NAN;
      }
   }

   private String pyHexString(Double f) {
      String java_hex = Double.toHexString(this.getValue());
      if (java_hex.equals("Infinity")) {
         return "inf";
      } else if (java_hex.equals("-Infinity")) {
         return "-inf";
      } else if (java_hex.equals("NaN")) {
         return "nan";
      } else if (java_hex.equals("0x0.0p0")) {
         return "0x0.0p+0";
      } else if (java_hex.equals("-0x0.0p0")) {
         return "-0x0.0p+0";
      } else {
         int len = java_hex.length();
         boolean start_exponent = false;
         StringBuilder py_hex = new StringBuilder(len + 1);
         int padding = f > 0.0 ? 17 : 18;

         for(int i = 0; i < len; ++i) {
            char c = java_hex.charAt(i);
            if (c != 'p') {
               if (start_exponent) {
                  if (c != '-') {
                     py_hex.append('+');
                  }

                  start_exponent = false;
               }
            } else {
               for(int pad = i; pad < padding; ++pad) {
                  py_hex.append('0');
               }

               start_exponent = true;
            }

            py_hex.append(c);
         }

         return py_hex.toString();
      }
   }

   public PyObject float_hex() {
      return new PyString(this.pyHexString(this.getValue()));
   }

   public boolean isFinite() {
      return !Double.isInfinite(this.getValue()) && !Double.isNaN(this.getValue());
   }

   public String toString() {
      return this.__str__().toString();
   }

   public PyString __str__() {
      return this.float___str__();
   }

   final PyString float___str__() {
      return Py.newString(this.formatDouble(SPEC_STR));
   }

   public PyString __repr__() {
      return this.float___repr__();
   }

   final PyString float___repr__() {
      return Py.newString(this.formatDouble(SPEC_REPR));
   }

   private String formatDouble(InternalFormat.Spec spec) {
      FloatFormatter f = new FloatFormatter(spec);
      return f.format(this.value).getResult();
   }

   public int hashCode() {
      return this.float___hash__();
   }

   final int float___hash__() {
      double value = this.getValue();
      if (Double.isInfinite(value)) {
         return value < 0.0 ? -271828 : 314159;
      } else if (Double.isNaN(value)) {
         return 0;
      } else {
         double intPart = Math.floor(value);
         double fractPart = value - intPart;
         if (fractPart == 0.0) {
            return intPart <= 2.147483647E9 && intPart >= -2.147483648E9 ? (int)value : this.__long__().hashCode();
         } else {
            long v = Double.doubleToLongBits(this.getValue());
            return (int)v ^ (int)(v >> 32);
         }
      }
   }

   public boolean __nonzero__() {
      return this.float___nonzero__();
   }

   final boolean float___nonzero__() {
      return this.getValue() != 0.0;
   }

   public Object __tojava__(Class c) {
      if (c != Double.TYPE && c != Number.class && c != Double.class && c != Object.class && c != Serializable.class) {
         return c != Float.TYPE && c != Float.class ? super.__tojava__(c) : new Float(this.getValue());
      } else {
         return new Double(this.getValue());
      }
   }

   public PyObject __eq__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.False : null;
   }

   public PyObject __ne__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.True : null;
   }

   public PyObject __gt__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.False : null;
   }

   public PyObject __ge__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.False : null;
   }

   public PyObject __lt__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.False : null;
   }

   public PyObject __le__(PyObject other) {
      return Double.isNaN(this.getValue()) ? Py.False : null;
   }

   public int __cmp__(PyObject other) {
      return this.float___cmp__(other);
   }

   final int float___cmp__(PyObject other) {
      double i = this.getValue();
      double j;
      if (other instanceof PyFloat) {
         j = ((PyFloat)other).getValue();
      } else if (!this.isFinite()) {
         if (!(other instanceof PyInteger) && !(other instanceof PyLong)) {
            return -2;
         }

         j = 0.0;
      } else {
         if (!(other instanceof PyInteger)) {
            if (other instanceof PyLong) {
               BigDecimal v = new BigDecimal(this.getValue());
               BigDecimal w = new BigDecimal(((PyLong)other).getValue());
               return v.compareTo(w);
            }

            return -2;
         }

         j = (double)((PyInteger)other).getValue();
      }

      if (i < j) {
         return -1;
      } else if (i > j) {
         return 1;
      } else if (i == j) {
         return 0;
      } else {
         return Double.isNaN(i) ? (Double.isNaN(j) ? 1 : -1) : 1;
      }
   }

   public Object __coerce_ex__(PyObject other) {
      return this.float___coerce_ex__(other);
   }

   final PyObject float___coerce__(PyObject other) {
      return this.adaptToCoerceTuple(this.float___coerce_ex__(other));
   }

   final Object float___coerce_ex__(PyObject other) {
      if (other instanceof PyFloat) {
         return other;
      } else if (other instanceof PyInteger) {
         return new PyFloat((double)((PyInteger)other).getValue());
      } else {
         return other instanceof PyLong ? new PyFloat(((PyLong)other).doubleValue()) : Py.None;
      }
   }

   private static boolean canCoerce(PyObject other) {
      return other instanceof PyFloat || other instanceof PyInteger || other instanceof PyLong;
   }

   private static double coerce(PyObject other) {
      if (other instanceof PyFloat) {
         return ((PyFloat)other).getValue();
      } else if (other instanceof PyInteger) {
         return (double)((PyInteger)other).getValue();
      } else if (other instanceof PyLong) {
         return ((PyLong)other).doubleValue();
      } else {
         throw Py.TypeError("xxx");
      }
   }

   public PyObject __add__(PyObject right) {
      return this.float___add__(right);
   }

   final PyObject float___add__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         return new PyFloat(this.getValue() + rightv);
      }
   }

   public PyObject __radd__(PyObject left) {
      return this.float___radd__(left);
   }

   final PyObject float___radd__(PyObject left) {
      return this.__add__(left);
   }

   public PyObject __sub__(PyObject right) {
      return this.float___sub__(right);
   }

   final PyObject float___sub__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         return new PyFloat(this.getValue() - rightv);
      }
   }

   public PyObject __rsub__(PyObject left) {
      return this.float___rsub__(left);
   }

   final PyObject float___rsub__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         double leftv = coerce(left);
         return new PyFloat(leftv - this.getValue());
      }
   }

   public PyObject __mul__(PyObject right) {
      return this.float___mul__(right);
   }

   final PyObject float___mul__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         return new PyFloat(this.getValue() * rightv);
      }
   }

   public PyObject __rmul__(PyObject left) {
      return this.float___rmul__(left);
   }

   final PyObject float___rmul__(PyObject left) {
      return this.__mul__(left);
   }

   public PyObject __div__(PyObject right) {
      return this.float___div__(right);
   }

   final PyObject float___div__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         if (Options.division_warning >= 2) {
            Py.warning(Py.DeprecationWarning, "classic float division");
         }

         double rightv = coerce(right);
         if (rightv == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(this.getValue() / rightv);
         }
      }
   }

   public PyObject __rdiv__(PyObject left) {
      return this.float___rdiv__(left);
   }

   final PyObject float___rdiv__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         if (Options.division_warning >= 2) {
            Py.warning(Py.DeprecationWarning, "classic float division");
         }

         double leftv = coerce(left);
         if (this.getValue() == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(leftv / this.getValue());
         }
      }
   }

   public PyObject __floordiv__(PyObject right) {
      return this.float___floordiv__(right);
   }

   final PyObject float___floordiv__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         if (rightv == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(Math.floor(this.getValue() / rightv));
         }
      }
   }

   public PyObject __rfloordiv__(PyObject left) {
      return this.float___rfloordiv__(left);
   }

   final PyObject float___rfloordiv__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         double leftv = coerce(left);
         if (this.getValue() == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(Math.floor(leftv / this.getValue()));
         }
      }
   }

   public PyObject __truediv__(PyObject right) {
      return this.float___truediv__(right);
   }

   final PyObject float___truediv__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         if (rightv == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(this.getValue() / rightv);
         }
      }
   }

   public PyObject __rtruediv__(PyObject left) {
      return this.float___rtruediv__(left);
   }

   final PyObject float___rtruediv__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         double leftv = coerce(left);
         if (this.getValue() == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            return new PyFloat(leftv / this.getValue());
         }
      }
   }

   private static double modulo(double x, double y) {
      if (y == 0.0) {
         throw Py.ZeroDivisionError("float modulo");
      } else {
         double z = x % y;
         if (z == 0.0) {
            return Math.copySign(z, y);
         } else {
            return z > 0.0 == y > 0.0 ? z : z + y;
         }
      }
   }

   public PyObject __mod__(PyObject right) {
      return this.float___mod__(right);
   }

   final PyObject float___mod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         return new PyFloat(modulo(this.getValue(), rightv));
      }
   }

   public PyObject __rmod__(PyObject left) {
      return this.float___rmod__(left);
   }

   final PyObject float___rmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         double leftv = coerce(left);
         return new PyFloat(modulo(leftv, this.getValue()));
      }
   }

   public PyObject __divmod__(PyObject right) {
      return this.float___divmod__(right);
   }

   final PyObject float___divmod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         double rightv = coerce(right);
         if (rightv == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            double z = Math.floor(this.getValue() / rightv);
            return new PyTuple(new PyObject[]{new PyFloat(z), new PyFloat(this.getValue() - z * rightv)});
         }
      }
   }

   public PyObject __rdivmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         double leftv = coerce(left);
         if (this.getValue() == 0.0) {
            throw Py.ZeroDivisionError("float division");
         } else {
            double z = Math.floor(leftv / this.getValue());
            return new PyTuple(new PyObject[]{new PyFloat(z), new PyFloat(leftv - z * this.getValue())});
         }
      }
   }

   final PyObject float___rdivmod__(PyObject left) {
      return this.__rdivmod__(left);
   }

   public PyObject __pow__(PyObject right, PyObject modulo) {
      return this.float___pow__(right, modulo);
   }

   final PyObject float___pow__(PyObject right, PyObject modulo) {
      if (!canCoerce(right)) {
         return null;
      } else {
         modulo = modulo == Py.None ? null : modulo;
         if (modulo != null) {
            throw Py.TypeError("pow() 3rd argument not allowed unless all arguments are integers");
         } else {
            return _pow(this.getValue(), coerce(right));
         }
      }
   }

   final PyObject float___rpow__(PyObject left) {
      return this.__rpow__(left);
   }

   public PyObject __rpow__(PyObject left) {
      return !canCoerce(left) ? null : _pow(coerce(left), this.getValue());
   }

   private static PyFloat _pow(double v, double w) {
      if (w == 0.0) {
         return ONE;
      } else if (Double.isNaN(v)) {
         return NAN;
      } else if (Double.isNaN(w)) {
         return v == 1.0 ? ONE : NAN;
      } else {
         if (Double.isInfinite(w)) {
            if (v == 1.0 || v == -1.0) {
               return ONE;
            }
         } else if (v == 0.0) {
            if (w < 0.0) {
               throw Py.ZeroDivisionError("0.0 cannot be raised to a negative power");
            }
         } else if (!Double.isInfinite(v) && v < 0.0 && w != Math.floor(w)) {
            throw Py.ValueError("negative number cannot be raised to a fractional power");
         }

         return new PyFloat(Math.pow(v, w));
      }
   }

   public PyObject __neg__() {
      return this.float___neg__();
   }

   final PyObject float___neg__() {
      return new PyFloat(-this.getValue());
   }

   public PyObject __pos__() {
      return this.float___pos__();
   }

   final PyObject float___pos__() {
      return this.float___float__();
   }

   public PyObject __invert__() {
      throw Py.TypeError("bad operand type for unary ~");
   }

   public PyObject __abs__() {
      return this.float___abs__();
   }

   final PyObject float___abs__() {
      return new PyFloat(Math.abs(this.getValue()));
   }

   public PyObject __int__() {
      return this.float___int__();
   }

   final PyObject float___int__() {
      double v = this.getValue();
      return (PyObject)(v < INT_LONG_BOUNDARY && v > -(INT_LONG_BOUNDARY + 1.0) ? new PyInteger((int)v) : this.__long__());
   }

   public PyObject __long__() {
      return this.float___long__();
   }

   final PyObject float___long__() {
      return new PyLong(this.getValue());
   }

   public PyFloat __float__() {
      return this.float___float__();
   }

   final PyFloat float___float__() {
      return this.getType() == TYPE ? this : Py.newFloat(this.getValue());
   }

   public PyObject __trunc__() {
      return this.float___trunc__();
   }

   final PyObject float___trunc__() {
      if (Double.isNaN(this.value)) {
         throw Py.ValueError("cannot convert float NaN to integer");
      } else if (Double.isInfinite(this.value)) {
         throw Py.OverflowError("cannot convert float infinity to integer");
      } else if (this.value < 2.147483647E9) {
         return new PyInteger((int)this.value);
      } else if (this.value < 9.223372036854776E18) {
         return new PyLong((long)this.value);
      } else {
         BigDecimal d = new BigDecimal(this.value);
         return new PyLong(d.toBigInteger());
      }
   }

   public PyObject conjugate() {
      return this.float_conjugate();
   }

   final PyObject float_conjugate() {
      return this;
   }

   public boolean is_integer() {
      return this.float_is_integer();
   }

   final boolean float_is_integer() {
      if (Double.isInfinite(this.value)) {
         return false;
      } else {
         return Math.floor(this.value) == this.value;
      }
   }

   public PyComplex __complex__() {
      return new PyComplex(this.getValue(), 0.0);
   }

   final PyTuple float___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyFloat(this.getValue())});
   }

   public PyTuple __getnewargs__() {
      return this.float___getnewargs__();
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.float___format__(formatSpec);
   }

   final PyObject float___format__(PyObject formatSpec) {
      InternalFormat.Spec spec = InternalFormat.fromText(formatSpec, "__format__");
      FloatFormatter f = prepareFormatter(spec);
      if (f != null) {
         f.setBytes(!(formatSpec instanceof PyUnicode));
         f.format(this.value);
         return f.pad().getPyResult();
      } else {
         throw InternalFormat.Formatter.unknownFormat(spec.type, "float");
      }
   }

   static FloatFormatter prepareFormatter(InternalFormat.Spec spec) {
      switch (spec.type) {
         case 'n':
            if (spec.grouping) {
               throw InternalFormat.Formatter.notAllowed("Grouping", "float", spec.type);
            }
         case '%':
         case 'E':
         case 'F':
         case 'G':
         case 'e':
         case 'f':
         case 'g':
         case '\uffff':
            if (spec.alternate) {
               throw FloatFormatter.alternateFormNotAllowed("float");
            }

            spec = spec.withDefaults(InternalFormat.Spec.NUMERIC);
            return new FloatFormatter(spec);
         default:
            return null;
      }
   }

   public PyTuple as_integer_ratio() {
      if (Double.isInfinite(this.value)) {
         throw Py.OverflowError("Cannot pass infinity to float.as_integer_ratio.");
      } else if (Double.isNaN(this.value)) {
         throw Py.ValueError("Cannot pass NaN to float.as_integer_ratio.");
      } else {
         PyTuple frexp = math.frexp(this.value);
         double float_part = (Double)frexp.get(0);
         int exponent = (Integer)frexp.get(1);

         for(int i = 0; i < 300 && float_part != Math.floor(float_part); ++i) {
            float_part *= 2.0;
            --exponent;
         }

         PyLong numerator = new PyLong(float_part);
         PyLong denominator = new PyLong(1L);
         PyLong py_exponent = new PyLong((long)Math.abs(exponent));
         py_exponent = (PyLong)denominator.__lshift__(py_exponent);
         if (exponent > 0) {
            numerator = new PyLong(numerator.getValue().multiply(py_exponent.getValue()));
         } else {
            denominator = py_exponent;
         }

         return new PyTuple(new PyObject[]{numerator, denominator});
      }
   }

   public double asDouble() {
      return this.getValue();
   }

   public boolean isNumberType() {
      return true;
   }

   public static String float___getformat__(PyType type, String typestr) {
      if ("double".equals(typestr)) {
         return double_format.format();
      } else if ("float".equals(typestr)) {
         return float_format.format();
      } else {
         throw Py.ValueError("__getformat__() argument 1 must be 'double' or 'float'");
      }
   }

   public static void float___setformat__(PyType type, String typestr, String format) {
      Format new_format = null;
      if (!"double".equals(typestr) && !"float".equals(typestr)) {
         throw Py.ValueError("__setformat__() argument 1 must be 'double' or 'float'");
      } else if (PyFloat.Format.LE.format().equals(format)) {
         throw Py.ValueError(String.format("can only set %s format to 'unknown' or the detected platform value", typestr));
      } else {
         if (PyFloat.Format.BE.format().equals(format)) {
            new_format = PyFloat.Format.BE;
         } else {
            if (!PyFloat.Format.UNKNOWN.format().equals(format)) {
               throw Py.ValueError("__setformat__() argument 2 must be 'unknown', 'IEEE, little-endian' or 'IEEE, big-endian'");
            }

            new_format = PyFloat.Format.UNKNOWN;
         }

         if (new_format != null) {
            if ("double".equals(typestr)) {
               double_format = new_format;
            } else {
               float_format = new_format;
            }
         }

      }
   }

   static {
      PyType.addBuilder(PyFloat.class, new PyExposer());
      TYPE = PyType.fromClass(PyFloat.class);
      SPEC_REPR = InternalFormat.fromText(" >r");
      SPEC_STR = InternalFormat.Spec.NUMERIC;
      ZERO = new PyFloat(0.0);
      ONE = new PyFloat(1.0);
      NAN = new PyFloat(Double.NaN);
      INT_LONG_BOUNDARY = 2.147483648E9;
      double_format = PyFloat.Format.BE;
      float_format = PyFloat.Format.BE;
   }

   public static enum Format {
      UNKNOWN("unknown"),
      BE("IEEE, big-endian"),
      LE("IEEE, little-endian");

      private final String format;

      private Format(String format) {
         this.format = format;
      }

      public String format() {
         return this.format;
      }
   }

   private static class float_fromhex_exposer extends PyBuiltinClassMethodNarrow {
      public float_fromhex_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "float.fromhex(string) -> float\n\nCreate a floating-point number from a hexadecimal string.\n>>> float.fromhex('0x1.ffffp10')\n2047.984375\n>>> float.fromhex('-0x1p-1074')\n-4.9406564584124654e-324";
      }

      public float_fromhex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.fromhex(string) -> float\n\nCreate a floating-point number from a hexadecimal string.\n>>> float.fromhex('0x1.ffffp10')\n2047.984375\n>>> float.fromhex('-0x1p-1074')\n-4.9406564584124654e-324";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float_fromhex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return PyFloat.float_fromhex((PyType)this.self, var1);
      }
   }

   private static class float_hex_exposer extends PyBuiltinMethodNarrow {
      public float_hex_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "float.hex() -> string\n\nReturn a hexadecimal representation of a floating-point number.\n>>> (-0.1).hex()\n'-0x1.999999999999ap-4'\n>>> 3.14159.hex()\n'0x1.921f9f01b866ep+1'";
      }

      public float_hex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.hex() -> string\n\nReturn a hexadecimal representation of a floating-point number.\n>>> (-0.1).hex()\n'-0x1.999999999999ap-4'\n>>> 3.14159.hex()\n'0x1.921f9f01b866ep+1'";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float_hex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float_hex();
      }
   }

   private static class float___str___exposer extends PyBuiltinMethodNarrow {
      public float___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public float___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___str__();
      }
   }

   private static class float___repr___exposer extends PyBuiltinMethodNarrow {
      public float___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public float___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___repr__();
      }
   }

   private static class float___hash___exposer extends PyBuiltinMethodNarrow {
      public float___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public float___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyFloat)this.self).float___hash__());
      }
   }

   private static class float___nonzero___exposer extends PyBuiltinMethodNarrow {
      public float___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public float___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFloat)this.self).float___nonzero__());
      }
   }

   private static class float___cmp___exposer extends PyBuiltinMethodNarrow {
      public float___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public float___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyFloat)this.self).float___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("float.__cmp__(x,y) requires y to be 'float', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class float___coerce___exposer extends PyBuiltinMethodNarrow {
      public float___coerce___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public float___coerce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___coerce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFloat)this.self).float___coerce__(var1);
      }
   }

   private static class float___add___exposer extends PyBuiltinMethodNarrow {
      public float___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public float___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___radd___exposer extends PyBuiltinMethodNarrow {
      public float___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public float___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___sub___exposer extends PyBuiltinMethodNarrow {
      public float___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public float___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rsub___exposer extends PyBuiltinMethodNarrow {
      public float___rsub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public float___rsub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rsub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rsub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___mul___exposer extends PyBuiltinMethodNarrow {
      public float___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public float___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rmul___exposer extends PyBuiltinMethodNarrow {
      public float___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public float___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___div___exposer extends PyBuiltinMethodNarrow {
      public float___div___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public float___div___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___div___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___div__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rdiv___exposer extends PyBuiltinMethodNarrow {
      public float___rdiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public float___rdiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rdiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rdiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___floordiv___exposer extends PyBuiltinMethodNarrow {
      public float___floordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public float___floordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___floordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___floordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rfloordiv___exposer extends PyBuiltinMethodNarrow {
      public float___rfloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public float___rfloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rfloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rfloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___truediv___exposer extends PyBuiltinMethodNarrow {
      public float___truediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public float___truediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___truediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___truediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rtruediv___exposer extends PyBuiltinMethodNarrow {
      public float___rtruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public float___rtruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rtruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rtruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___mod___exposer extends PyBuiltinMethodNarrow {
      public float___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public float___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___mod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rmod___exposer extends PyBuiltinMethodNarrow {
      public float___rmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public float___rmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___divmod___exposer extends PyBuiltinMethodNarrow {
      public float___divmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public float___divmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___divmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___divmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rdivmod___exposer extends PyBuiltinMethodNarrow {
      public float___rdivmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public float___rdivmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rdivmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rdivmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___pow___exposer extends PyBuiltinMethodNarrow {
      public float___pow___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public float___pow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___pow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         PyObject var10000 = ((PyFloat)this.self).float___pow__(var1, var2);
         return var10000 == null ? Py.NotImplemented : var10000;
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___pow__(var1, (PyObject)null);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___rpow___exposer extends PyBuiltinMethodNarrow {
      public float___rpow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public float___rpow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___rpow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFloat)this.self).float___rpow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class float___neg___exposer extends PyBuiltinMethodNarrow {
      public float___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__neg__() <==> -x";
      }

      public float___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__neg__() <==> -x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___neg__();
      }
   }

   private static class float___pos___exposer extends PyBuiltinMethodNarrow {
      public float___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__pos__() <==> +x";
      }

      public float___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pos__() <==> +x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___pos__();
      }
   }

   private static class float___abs___exposer extends PyBuiltinMethodNarrow {
      public float___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public float___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___abs__();
      }
   }

   private static class float___int___exposer extends PyBuiltinMethodNarrow {
      public float___int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__int__() <==> int(x)";
      }

      public float___int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__int__() <==> int(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___int__();
      }
   }

   private static class float___long___exposer extends PyBuiltinMethodNarrow {
      public float___long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__long__() <==> long(x)";
      }

      public float___long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__long__() <==> long(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___long__();
      }
   }

   private static class float___float___exposer extends PyBuiltinMethodNarrow {
      public float___float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__float__() <==> float(x)";
      }

      public float___float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__float__() <==> float(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___float__();
      }
   }

   private static class float___trunc___exposer extends PyBuiltinMethodNarrow {
      public float___trunc___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns the Integral closest to x between 0 and x.";
      }

      public float___trunc___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns the Integral closest to x between 0 and x.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___trunc___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___trunc__();
      }
   }

   private static class float_conjugate_exposer extends PyBuiltinMethodNarrow {
      public float_conjugate_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns self, the complex conjugate of any float.";
      }

      public float_conjugate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns self, the complex conjugate of any float.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float_conjugate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float_conjugate();
      }
   }

   private static class float_is_integer_exposer extends PyBuiltinMethodNarrow {
      public float_is_integer_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns True if the float is an integer.";
      }

      public float_is_integer_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns True if the float is an integer.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float_is_integer_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFloat)this.self).float_is_integer());
      }
   }

   private static class float___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public float___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public float___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).float___getnewargs__();
      }
   }

   private static class float___format___exposer extends PyBuiltinMethodNarrow {
      public float___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "float.__format__(format_spec) -> string\n\nFormats the float according to format_spec.";
      }

      public float___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.__format__(format_spec) -> string\n\nFormats the float according to format_spec.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFloat)this.self).float___format__(var1);
      }
   }

   private static class as_integer_ratio_exposer extends PyBuiltinMethodNarrow {
      public as_integer_ratio_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "float.as_integer_ratio() -> (int, int)\n\nReturns a pair of integers, whose ratio is exactly equal to the original\nfloat and with a positive denominator.\nRaises OverflowError on infinities and a ValueError on NaNs.\n\n>>> (10.0).as_integer_ratio()\n(10, 1)\n>>> (0.0).as_integer_ratio()\n(0, 1)\n>>> (-.25).as_integer_ratio()\n(-1, 4)";
      }

      public as_integer_ratio_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.as_integer_ratio() -> (int, int)\n\nReturns a pair of integers, whose ratio is exactly equal to the original\nfloat and with a positive denominator.\nRaises OverflowError on infinities and a ValueError on NaNs.\n\n>>> (10.0).as_integer_ratio()\n(10, 1)\n>>> (0.0).as_integer_ratio()\n(0, 1)\n>>> (-.25).as_integer_ratio()\n(-1, 4)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new as_integer_ratio_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFloat)this.self).as_integer_ratio();
      }
   }

   private static class float___getformat___exposer extends PyBuiltinClassMethodNarrow {
      public float___getformat___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "float.__getformat__(typestr) -> string\n\nYou probably don't want to use this function.  It exists mainly to be\nused in Python's test suite.\n\ntypestr must be 'double' or 'float'.  This function returns whichever of\n'unknown', 'IEEE, big-endian' or 'IEEE, little-endian' best describes the\nformat of floating point numbers used by the C type named by typestr.";
      }

      public float___getformat___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.__getformat__(typestr) -> string\n\nYou probably don't want to use this function.  It exists mainly to be\nused in Python's test suite.\n\ntypestr must be 'double' or 'float'.  This function returns whichever of\n'unknown', 'IEEE, big-endian' or 'IEEE, little-endian' best describes the\nformat of floating point numbers used by the C type named by typestr.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___getformat___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = PyFloat.float___getformat__((PyType)this.self, var1.asString());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class float___setformat___exposer extends PyBuiltinClassMethodNarrow {
      public float___setformat___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "float.__setformat__(typestr, fmt) -> None\n\nYou probably don't want to use this function.  It exists mainly to be\nused in Python's test suite.\n\ntypestr must be 'double' or 'float'.  fmt must be one of 'unknown',\n'IEEE, big-endian' or 'IEEE, little-endian', and in addition can only be\none of the latter two if it appears to match the underlying C reality.\n\nOverrides the automatic determination of C-level floating point type.\nThis affects how floats are converted to and from binary strings.";
      }

      public float___setformat___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "float.__setformat__(typestr, fmt) -> None\n\nYou probably don't want to use this function.  It exists mainly to be\nused in Python's test suite.\n\ntypestr must be 'double' or 'float'.  fmt must be one of 'unknown',\n'IEEE, big-endian' or 'IEEE, little-endian', and in addition can only be\none of the latter two if it appears to match the underlying C reality.\n\nOverrides the automatic determination of C-level floating point type.\nThis affects how floats are converted to and from binary strings.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new float___setformat___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         PyFloat.float___setformat__((PyType)this.self, var1.asString(), var2.asString());
         return Py.None;
      }
   }

   private static class real_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public real_descriptor() {
         super("real", PyObject.class, "the real part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFloat)var1).getReal();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class imag_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public imag_descriptor() {
         super("imag", PyObject.class, "the imaginary part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFloat)var1).getImag();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyFloat.float_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new float_fromhex_exposer("fromhex"), new float_hex_exposer("hex"), new float___str___exposer("__str__"), new float___repr___exposer("__repr__"), new float___hash___exposer("__hash__"), new float___nonzero___exposer("__nonzero__"), new float___cmp___exposer("__cmp__"), new float___coerce___exposer("__coerce__"), new float___add___exposer("__add__"), new float___radd___exposer("__radd__"), new float___sub___exposer("__sub__"), new float___rsub___exposer("__rsub__"), new float___mul___exposer("__mul__"), new float___rmul___exposer("__rmul__"), new float___div___exposer("__div__"), new float___rdiv___exposer("__rdiv__"), new float___floordiv___exposer("__floordiv__"), new float___rfloordiv___exposer("__rfloordiv__"), new float___truediv___exposer("__truediv__"), new float___rtruediv___exposer("__rtruediv__"), new float___mod___exposer("__mod__"), new float___rmod___exposer("__rmod__"), new float___divmod___exposer("__divmod__"), new float___rdivmod___exposer("__rdivmod__"), new float___pow___exposer("__pow__"), new float___rpow___exposer("__rpow__"), new float___neg___exposer("__neg__"), new float___pos___exposer("__pos__"), new float___abs___exposer("__abs__"), new float___int___exposer("__int__"), new float___long___exposer("__long__"), new float___float___exposer("__float__"), new float___trunc___exposer("__trunc__"), new float_conjugate_exposer("conjugate"), new float_is_integer_exposer("is_integer"), new float___getnewargs___exposer("__getnewargs__"), new float___format___exposer("__format__"), new as_integer_ratio_exposer("as_integer_ratio"), new float___getformat___exposer("__getformat__"), new float___setformat___exposer("__setformat__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new real_descriptor(), new imag_descriptor()};
         super("float", PyFloat.class, Object.class, (boolean)1, "float(x) -> floating point number\n\nConvert a string or number to a floating point number, if possible.", var1, var2, new exposed___new__());
      }
   }
}

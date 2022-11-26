package org.python.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.python.core.stringlib.FloatFormatter;
import org.python.core.stringlib.IntegerFormatter;
import org.python.core.stringlib.InternalFormat;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "long",
   doc = "long(x[, base]) -> integer\n\nConvert a string or number to a long integer, if possible.  A floating\npoint argument will be truncated towards zero (this does not include a\nstring representation of a floating point number!)  When converting a\nstring, use the optional base.  It is an error to supply a base when\nconverting a non-string."
)
public class PyLong extends PyObject {
   public static final PyType TYPE;
   public static final BigInteger MIN_LONG;
   public static final BigInteger MAX_LONG;
   public static final BigInteger MAX_ULONG;
   /** @deprecated */
   @Deprecated
   public static final BigInteger minLong;
   /** @deprecated */
   @Deprecated
   public static final BigInteger maxLong;
   /** @deprecated */
   @Deprecated
   public static final BigInteger maxULong;
   private final BigInteger value;

   public BigInteger getValue() {
      return this.value;
   }

   public PyLong(PyType subType, BigInteger v) {
      super(subType);
      this.value = v;
   }

   public PyLong(BigInteger v) {
      this(TYPE, v);
   }

   public PyLong(double v) {
      this(toBigInteger(v));
   }

   public PyLong(long v) {
      this(BigInteger.valueOf(v));
   }

   public PyLong(String s) {
      this(new BigInteger(s));
   }

   @ExposedNew
   public static PyObject long___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (new_.for_type != subtype) {
         return longSubtypeNew(new_, init, subtype, args, keywords);
      } else {
         ArgParser ap = new ArgParser("long", args, keywords, new String[]{"x", "base"}, 0);
         PyObject x = ap.getPyObject(0, (PyObject)null);
         if (x != null && x.getJavaProxy() instanceof BigInteger) {
            return new PyLong((BigInteger)x.getJavaProxy());
         } else {
            int base = ap.getInt(1, -909);
            if (x == null) {
               return new PyLong(0L);
            } else if (base == -909) {
               return asPyLong(x);
            } else if (!(x instanceof PyString)) {
               throw Py.TypeError("long: can't convert non-string with explicit base");
            } else {
               return ((PyString)x).atol(base);
            }
         }
      }
   }

   private static PyObject asPyLong(PyObject x) {
      try {
         return x.__long__();
      } catch (PyException var4) {
         if (!var4.match(Py.AttributeError)) {
            throw var4;
         } else {
            try {
               PyObject integral = x.invoke("__trunc__");
               return convertIntegralToLong(integral);
            } catch (PyException var3) {
               if (!var3.match(Py.AttributeError)) {
                  throw var3;
               } else {
                  throw Py.TypeError(String.format("long() argument must be a string or a number, not '%.200s'", x.getType().fastGetName()));
               }
            }
         }
      }
   }

   private static PyObject convertIntegralToLong(PyObject integral) {
      if (!(integral instanceof PyInteger) && !(integral instanceof PyLong)) {
         PyObject i = integral.invoke("__int__");
         if (!(i instanceof PyInteger) && !(i instanceof PyLong)) {
            throw Py.TypeError(String.format("__trunc__ returned non-Integral (type %.200s)", integral.getType().fastGetName()));
         } else {
            return i;
         }
      } else {
         return integral;
      }
   }

   private static PyObject longSubtypeNew(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      PyObject tmp = long___new__(new_, init, TYPE, args, keywords);
      if (tmp instanceof PyInteger) {
         int intValue = ((PyInteger)tmp).getValue();
         return new PyLongDerived(subtype, BigInteger.valueOf((long)intValue));
      } else {
         return new PyLongDerived(subtype, ((PyLong)tmp).getValue());
      }
   }

   private static BigInteger toBigInteger(double value) {
      if (Double.isInfinite(value)) {
         throw Py.OverflowError("cannot convert float infinity to long");
      } else if (Double.isNaN(value)) {
         throw Py.ValueError("cannot convert float NaN to integer");
      } else {
         return (new BigDecimal(value)).toBigInteger();
      }
   }

   public PyObject getReal() {
      return this.long___long__();
   }

   public PyObject getImag() {
      return Py.newLong(0);
   }

   public PyObject getNumerator() {
      return this.long___long__();
   }

   public PyObject getDenominator() {
      return Py.newLong(1);
   }

   public String toString() {
      return this.long_toString();
   }

   final String long_toString() {
      return this.getValue().toString() + "L";
   }

   public int hashCode() {
      return this.long___hash__();
   }

   final int long___hash__() {
      return this.getValue().hashCode();
   }

   public boolean __nonzero__() {
      return this.long___nonzero__();
   }

   public boolean long___nonzero__() {
      return !this.getValue().equals(BigInteger.ZERO);
   }

   public double doubleValue() {
      double v = this.getValue().doubleValue();
      if (Double.isInfinite(v)) {
         throw Py.OverflowError("long int too large to convert to float");
      } else {
         return v;
      }
   }

   private static final double scaledDoubleValue(BigInteger val, int[] exp) {
      double x = 0.0;
      int signum = val.signum();
      byte[] digits;
      if (signum >= 0) {
         digits = val.toByteArray();
      } else {
         digits = val.negate().toByteArray();
      }

      int count = 8;
      int i = 0;
      if (digits[0] == 0) {
         ++i;
         ++count;
      }

      for(count = count <= digits.length ? count : digits.length; i < count; ++i) {
         x = x * 256.0 + (double)(digits[i] & 255);
      }

      exp[0] = digits.length - i;
      return (double)signum * x;
   }

   public double scaledDoubleValue(int[] exp) {
      return scaledDoubleValue(this.getValue(), exp);
   }

   public long getLong(long min, long max) {
      return this.getLong(min, max, "long int too large to convert");
   }

   public long getLong(long min, long max, String overflowMsg) {
      if (this.getValue().compareTo(MAX_LONG) <= 0 && this.getValue().compareTo(MIN_LONG) >= 0) {
         long v = this.getValue().longValue();
         if (v >= min && v <= max) {
            return v;
         }
      }

      throw Py.OverflowError(overflowMsg);
   }

   public long asLong(int index) {
      return this.asLong();
   }

   public int asInt(int index) {
      return (int)this.getLong(-2147483648L, 2147483647L, "long int too large to convert to int");
   }

   public int asInt() {
      return (int)this.getLong(-2147483648L, 2147483647L, "long int too large to convert to int");
   }

   public long asLong() {
      return this.getLong(Long.MIN_VALUE, Long.MAX_VALUE, "long too big to convert");
   }

   public Object __tojava__(Class c) {
      try {
         if (c != Boolean.TYPE && c != Boolean.class) {
            if (c != Byte.TYPE && c != Byte.class) {
               if (c != Short.TYPE && c != Short.class) {
                  if (c != Integer.TYPE && c != Integer.class) {
                     if (c != Long.TYPE && c != Long.class) {
                        if (c != Float.TYPE && c != Double.TYPE && c != Float.class && c != Double.class) {
                           if (c == BigInteger.class || c == Number.class || c == Object.class || c == Serializable.class) {
                              return this.getValue();
                           } else {
                              return super.__tojava__(c);
                           }
                        } else {
                           return this.__float__().__tojava__(c);
                        }
                     } else {
                        return new Long(this.getLong(Long.MIN_VALUE, Long.MAX_VALUE));
                     }
                  } else {
                     return new Integer((int)this.getLong(-2147483648L, 2147483647L));
                  }
               } else {
                  return new Short((short)((int)this.getLong(-32768L, 32767L)));
               }
            } else {
               return new Byte((byte)((int)this.getLong(-128L, 127L)));
            }
         } else {
            return new Boolean(!this.getValue().equals(BigInteger.ZERO));
         }
      } catch (PyException var3) {
         return Py.NoConversion;
      }
   }

   public int __cmp__(PyObject other) {
      return this.long___cmp__(other);
   }

   final int long___cmp__(PyObject other) {
      return !canCoerce(other) ? -2 : this.getValue().compareTo(coerce(other));
   }

   public Object __coerce_ex__(PyObject other) {
      return this.long___coerce_ex__(other);
   }

   final PyObject long___coerce__(PyObject other) {
      return this.adaptToCoerceTuple(this.long___coerce_ex__(other));
   }

   final Object long___coerce_ex__(PyObject other) {
      if (other instanceof PyLong) {
         return other;
      } else {
         return other instanceof PyInteger ? Py.newLong(((PyInteger)other).getValue()) : Py.None;
      }
   }

   private static final boolean canCoerce(PyObject other) {
      return other instanceof PyLong || other instanceof PyInteger;
   }

   private static final BigInteger coerce(PyObject other) {
      if (other instanceof PyLong) {
         return ((PyLong)other).getValue();
      } else if (other instanceof PyInteger) {
         return BigInteger.valueOf((long)((PyInteger)other).getValue());
      } else {
         throw Py.TypeError("xxx");
      }
   }

   public PyObject __add__(PyObject right) {
      return this.long___add__(right);
   }

   final PyObject long___add__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.getValue().add(coerce(right)));
   }

   public PyObject __radd__(PyObject left) {
      return this.long___radd__(left);
   }

   final PyObject long___radd__(PyObject left) {
      return this.__add__(left);
   }

   public PyObject __sub__(PyObject right) {
      return this.long___sub__(right);
   }

   final PyObject long___sub__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.getValue().subtract(coerce(right)));
   }

   public PyObject __rsub__(PyObject left) {
      return this.long___rsub__(left);
   }

   final PyObject long___rsub__(PyObject left) {
      return Py.newLong(coerce(left).subtract(this.getValue()));
   }

   public PyObject __mul__(PyObject right) {
      return this.long___mul__(right);
   }

   final PyObject long___mul__(PyObject right) {
      if (right instanceof PySequence) {
         return ((PySequence)right).repeat(coerceInt(this));
      } else {
         return !canCoerce(right) ? null : Py.newLong(this.getValue().multiply(coerce(right)));
      }
   }

   public PyObject __rmul__(PyObject left) {
      return this.long___rmul__(left);
   }

   final PyObject long___rmul__(PyObject left) {
      if (left instanceof PySequence) {
         return ((PySequence)left).repeat(coerceInt(this));
      } else {
         return !canCoerce(left) ? null : Py.newLong(coerce(left).multiply(this.getValue()));
      }
   }

   private BigInteger divide(BigInteger x, BigInteger y) {
      BigInteger zero = BigInteger.valueOf(0L);
      if (y.equals(zero)) {
         throw Py.ZeroDivisionError("long division or modulo");
      } else {
         if (y.compareTo(zero) < 0) {
            if (x.compareTo(zero) > 0) {
               return x.subtract(y).subtract(BigInteger.valueOf(1L)).divide(y);
            }
         } else if (x.compareTo(zero) < 0) {
            return x.subtract(y).add(BigInteger.valueOf(1L)).divide(y);
         }

         return x.divide(y);
      }
   }

   public PyObject __div__(PyObject right) {
      return this.long___div__(right);
   }

   final PyObject long___div__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         if (Options.division_warning > 0) {
            Py.warning(Py.DeprecationWarning, "classic long division");
         }

         return Py.newLong(this.divide(this.getValue(), coerce(right)));
      }
   }

   public PyObject __rdiv__(PyObject left) {
      return this.long___rdiv__(left);
   }

   final PyObject long___rdiv__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         if (Options.division_warning > 0) {
            Py.warning(Py.DeprecationWarning, "classic long division");
         }

         return Py.newLong(this.divide(coerce(left), this.getValue()));
      }
   }

   public PyObject __floordiv__(PyObject right) {
      return this.long___floordiv__(right);
   }

   final PyObject long___floordiv__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.divide(this.getValue(), coerce(right)));
   }

   public PyObject __rfloordiv__(PyObject left) {
      return this.long___rfloordiv__(left);
   }

   final PyObject long___rfloordiv__(PyObject left) {
      return !canCoerce(left) ? null : Py.newLong(this.divide(coerce(left), this.getValue()));
   }

   private static final PyFloat true_divide(BigInteger a, BigInteger b) {
      int[] ae = new int[1];
      int[] be = new int[1];
      double ad = scaledDoubleValue(a, ae);
      double bd = scaledDoubleValue(b, be);
      if (bd == 0.0) {
         throw Py.ZeroDivisionError("long division or modulo");
      } else {
         ad /= bd;
         int aexp = ae[0] - be[0];
         if (aexp > 268435455) {
            throw Py.OverflowError("long/long too large for a float");
         } else if (aexp < -268435455) {
            return PyFloat.ZERO;
         } else {
            ad *= Math.pow(2.0, (double)(aexp * 8));
            if (Double.isInfinite(ad)) {
               throw Py.OverflowError("long/long too large for a float");
            } else {
               return new PyFloat(ad);
            }
         }
      }
   }

   public PyObject __truediv__(PyObject right) {
      return this.long___truediv__(right);
   }

   final PyObject long___truediv__(PyObject right) {
      return !canCoerce(right) ? null : true_divide(this.getValue(), coerce(right));
   }

   public PyObject __rtruediv__(PyObject left) {
      return this.long___rtruediv__(left);
   }

   final PyObject long___rtruediv__(PyObject left) {
      return !canCoerce(left) ? null : true_divide(coerce(left), this.getValue());
   }

   private BigInteger modulo(BigInteger x, BigInteger y, BigInteger xdivy) {
      return x.subtract(xdivy.multiply(y));
   }

   public PyObject __mod__(PyObject right) {
      return this.long___mod__(right);
   }

   final PyObject long___mod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         BigInteger rightv = coerce(right);
         return Py.newLong(this.modulo(this.getValue(), rightv, this.divide(this.getValue(), rightv)));
      }
   }

   public PyObject __rmod__(PyObject left) {
      return this.long___rmod__(left);
   }

   final PyObject long___rmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         BigInteger leftv = coerce(left);
         return Py.newLong(this.modulo(leftv, this.getValue(), this.divide(leftv, this.getValue())));
      }
   }

   public PyObject __divmod__(PyObject right) {
      return this.long___divmod__(right);
   }

   final PyObject long___divmod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         BigInteger rightv = coerce(right);
         BigInteger xdivy = this.divide(this.getValue(), rightv);
         return new PyTuple(new PyObject[]{Py.newLong(xdivy), Py.newLong(this.modulo(this.getValue(), rightv, xdivy))});
      }
   }

   public PyObject __rdivmod__(PyObject left) {
      return this.long___rdivmod__(left);
   }

   final PyObject long___rdivmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         BigInteger leftv = coerce(left);
         BigInteger xdivy = this.divide(leftv, this.getValue());
         return new PyTuple(new PyObject[]{Py.newLong(xdivy), Py.newLong(this.modulo(leftv, this.getValue(), xdivy))});
      }
   }

   public PyObject __pow__(PyObject right, PyObject modulo) {
      return this.long___pow__(right, modulo);
   }

   final PyObject long___pow__(PyObject right, PyObject modulo) {
      if (!canCoerce(right)) {
         return null;
      } else {
         modulo = modulo == Py.None ? null : modulo;
         return modulo != null && !canCoerce(modulo) ? null : _pow(this.getValue(), coerce(right), modulo, this, right);
      }
   }

   public PyObject __rpow__(PyObject left) {
      return this.long___rpow__(left);
   }

   final PyObject long___rpow__(PyObject left) {
      return !canCoerce(left) ? null : _pow(coerce(left), this.getValue(), (PyObject)null, left, this);
   }

   public static PyObject _pow(BigInteger value, BigInteger y, PyObject modulo, PyObject left, PyObject right) {
      if (y.compareTo(BigInteger.ZERO) < 0) {
         if (value.compareTo(BigInteger.ZERO) != 0) {
            return left.__float__().__pow__(right, modulo);
         } else {
            throw Py.ZeroDivisionError("zero to a negative power");
         }
      } else if (modulo == null) {
         return Py.newLong(value.pow(y.intValue()));
      } else {
         BigInteger z = coerce(modulo);
         if (z.equals(BigInteger.ZERO)) {
            throw Py.ValueError("pow(x, y, z) with z == 0");
         } else if (z.abs().equals(BigInteger.ONE)) {
            return Py.newLong(0);
         } else if (z.compareTo(BigInteger.valueOf(0L)) <= 0) {
            y = value.modPow(y, z.negate());
            return y.compareTo(BigInteger.valueOf(0L)) > 0 ? Py.newLong(z.add(y)) : Py.newLong(y);
         } else {
            return Py.newLong(value.modPow(y, z));
         }
      }
   }

   private static final int coerceInt(PyObject other) {
      if (other instanceof PyLong) {
         return ((PyLong)other).asInt();
      } else if (other instanceof PyInteger) {
         return ((PyInteger)other).getValue();
      } else {
         throw Py.TypeError("xxx");
      }
   }

   public PyObject __lshift__(PyObject right) {
      return this.long___lshift__(right);
   }

   final PyObject long___lshift__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerceInt(right);
         if (rightv < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            return Py.newLong(this.getValue().shiftLeft(rightv));
         }
      }
   }

   final PyObject long___rlshift__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else if (this.getValue().intValue() < 0) {
         throw Py.ValueError("negative shift count");
      } else {
         return Py.newLong(coerce(left).shiftLeft(coerceInt(this)));
      }
   }

   public PyObject __rshift__(PyObject right) {
      return this.long___rshift__(right);
   }

   final PyObject long___rshift__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerceInt(right);
         if (rightv < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            return Py.newLong(this.getValue().shiftRight(rightv));
         }
      }
   }

   final PyObject long___rrshift__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else if (this.getValue().intValue() < 0) {
         throw Py.ValueError("negative shift count");
      } else {
         return Py.newLong(coerce(left).shiftRight(coerceInt(this)));
      }
   }

   public PyObject __and__(PyObject right) {
      return this.long___and__(right);
   }

   final PyObject long___and__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.getValue().and(coerce(right)));
   }

   public PyObject __rand__(PyObject left) {
      return this.long___rand__(left);
   }

   final PyObject long___rand__(PyObject left) {
      return !canCoerce(left) ? null : Py.newLong(coerce(left).and(this.getValue()));
   }

   public PyObject __xor__(PyObject right) {
      return this.long___xor__(right);
   }

   final PyObject long___xor__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.getValue().xor(coerce(right)));
   }

   public PyObject __rxor__(PyObject left) {
      return this.long___rxor__(left);
   }

   final PyObject long___rxor__(PyObject left) {
      return !canCoerce(left) ? null : Py.newLong(coerce(left).xor(this.getValue()));
   }

   public PyObject __or__(PyObject right) {
      return this.long___or__(right);
   }

   final PyObject long___or__(PyObject right) {
      return !canCoerce(right) ? null : Py.newLong(this.getValue().or(coerce(right)));
   }

   public PyObject __ror__(PyObject left) {
      return this.long___ror__(left);
   }

   final PyObject long___ror__(PyObject left) {
      return !canCoerce(left) ? null : Py.newLong(coerce(left).or(this.getValue()));
   }

   public PyObject __neg__() {
      return this.long___neg__();
   }

   final PyObject long___neg__() {
      return Py.newLong(this.getValue().negate());
   }

   public PyObject __pos__() {
      return this.long___pos__();
   }

   final PyObject long___pos__() {
      return this.long___long__();
   }

   public PyObject __abs__() {
      return this.long___abs__();
   }

   final PyObject long___abs__() {
      return this.getValue().signum() == -1 ? this.long___neg__() : this.long___long__();
   }

   public PyObject __invert__() {
      return this.long___invert__();
   }

   final PyObject long___invert__() {
      return Py.newLong(this.getValue().not());
   }

   public PyObject __int__() {
      return this.long___int__();
   }

   final PyObject long___int__() {
      return (PyObject)(this.getValue().compareTo(PyInteger.MAX_INT) <= 0 && this.getValue().compareTo(PyInteger.MIN_INT) >= 0 ? Py.newInteger(this.getValue().intValue()) : this.long___long__());
   }

   public PyObject __long__() {
      return this.long___long__();
   }

   final PyObject long___long__() {
      return this.getType() == TYPE ? this : Py.newLong(this.getValue());
   }

   public PyFloat __float__() {
      return this.long___float__();
   }

   final PyFloat long___float__() {
      return new PyFloat(this.doubleValue());
   }

   public PyComplex __complex__() {
      return this.long___complex__();
   }

   final PyComplex long___complex__() {
      return new PyComplex(this.doubleValue(), 0.0);
   }

   public PyObject __trunc__() {
      return this.long___trunc__();
   }

   final PyObject long___trunc__() {
      return this;
   }

   public PyObject conjugate() {
      return this.long_conjugate();
   }

   final PyObject long_conjugate() {
      return this;
   }

   public PyString __oct__() {
      return this.long___oct__();
   }

   final PyString long___oct__() {
      return this.formatImpl(IntegerFormatter.OCT);
   }

   public PyString __hex__() {
      return this.long___hex__();
   }

   final PyString long___hex__() {
      return this.formatImpl(IntegerFormatter.HEX);
   }

   private PyString formatImpl(InternalFormat.Spec spec) {
      IntegerFormatter f = new IntegerFormatter.Traditional(spec);
      f.format(this.value).append('L');
      return new PyString(f.getResult());
   }

   public PyString long___str__() {
      return Py.newString(this.getValue().toString());
   }

   public PyString __str__() {
      return this.long___str__();
   }

   public PyUnicode __unicode__() {
      return new PyUnicode(this.getValue().toString());
   }

   final PyTuple long___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyLong(this.getValue())});
   }

   public PyTuple __getnewargs__() {
      return this.long___getnewargs__();
   }

   public PyObject __index__() {
      return this.long___index__();
   }

   final PyObject long___index__() {
      return this;
   }

   public int bit_length() {
      return this.long_bit_length();
   }

   final int long_bit_length() {
      BigInteger v = this.value;
      if (v.compareTo(BigInteger.ZERO) == -1) {
         v = v.negate();
      }

      return v.bitLength();
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.long___format__(formatSpec);
   }

   final PyObject long___format__(PyObject formatSpec) {
      InternalFormat.Spec spec = InternalFormat.fromText(formatSpec, "__format__");
      IntegerFormatter fi = PyInteger.prepareFormatter(spec);
      Object f;
      if (fi != null) {
         fi.setBytes(!(formatSpec instanceof PyUnicode));
         fi.format(this.value);
         f = fi;
      } else {
         FloatFormatter ff = PyFloat.prepareFormatter(spec);
         if (ff == null) {
            throw InternalFormat.Formatter.unknownFormat(spec.type, "integer");
         }

         ff.setBytes(!(formatSpec instanceof PyUnicode));
         ff.format(this.value.doubleValue());
         f = ff;
      }

      return ((InternalFormat.Formatter)f).pad().getPyResult();
   }

   public boolean isIndex() {
      return true;
   }

   public int asIndex(PyObject err) {
      boolean tooLow = this.getValue().compareTo(PyInteger.MIN_INT) < 0;
      boolean tooHigh = this.getValue().compareTo(PyInteger.MAX_INT) > 0;
      if (!tooLow && !tooHigh) {
         return (int)this.getValue().longValue();
      } else if (err != null) {
         throw new PyException(err, "cannot fit 'long' into an index-sized integer");
      } else {
         return tooLow ? Integer.MIN_VALUE : Integer.MAX_VALUE;
      }
   }

   public boolean isMappingType() {
      return false;
   }

   public boolean isNumberType() {
      return true;
   }

   public boolean isSequenceType() {
      return false;
   }

   static {
      PyType.addBuilder(PyLong.class, new PyExposer());
      TYPE = PyType.fromClass(PyLong.class);
      MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
      MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
      MAX_ULONG = BigInteger.valueOf(1L).shiftLeft(64).subtract(BigInteger.valueOf(1L));
      minLong = MIN_LONG;
      maxLong = MAX_LONG;
      maxULong = MAX_ULONG;
   }

   private static class long_toString_exposer extends PyBuiltinMethodNarrow {
      public long_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public long_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyLong)this.self).long_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class long___hash___exposer extends PyBuiltinMethodNarrow {
      public long___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public long___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyLong)this.self).long___hash__());
      }
   }

   private static class long___nonzero___exposer extends PyBuiltinMethodNarrow {
      public long___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public long___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyLong)this.self).long___nonzero__());
      }
   }

   private static class long___cmp___exposer extends PyBuiltinMethodNarrow {
      public long___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public long___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyLong)this.self).long___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("long.__cmp__(x,y) requires y to be 'long', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class long___coerce___exposer extends PyBuiltinMethodNarrow {
      public long___coerce___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public long___coerce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___coerce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyLong)this.self).long___coerce__(var1);
      }
   }

   private static class long___add___exposer extends PyBuiltinMethodNarrow {
      public long___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public long___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___radd___exposer extends PyBuiltinMethodNarrow {
      public long___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public long___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___sub___exposer extends PyBuiltinMethodNarrow {
      public long___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public long___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rsub___exposer extends PyBuiltinMethodNarrow {
      public long___rsub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public long___rsub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rsub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rsub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___mul___exposer extends PyBuiltinMethodNarrow {
      public long___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public long___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rmul___exposer extends PyBuiltinMethodNarrow {
      public long___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public long___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___div___exposer extends PyBuiltinMethodNarrow {
      public long___div___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public long___div___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___div___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___div__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rdiv___exposer extends PyBuiltinMethodNarrow {
      public long___rdiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public long___rdiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rdiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rdiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___floordiv___exposer extends PyBuiltinMethodNarrow {
      public long___floordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public long___floordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___floordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___floordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rfloordiv___exposer extends PyBuiltinMethodNarrow {
      public long___rfloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public long___rfloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rfloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rfloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___truediv___exposer extends PyBuiltinMethodNarrow {
      public long___truediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public long___truediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___truediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___truediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rtruediv___exposer extends PyBuiltinMethodNarrow {
      public long___rtruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public long___rtruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rtruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rtruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___mod___exposer extends PyBuiltinMethodNarrow {
      public long___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public long___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___mod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rmod___exposer extends PyBuiltinMethodNarrow {
      public long___rmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public long___rmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___divmod___exposer extends PyBuiltinMethodNarrow {
      public long___divmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public long___divmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___divmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___divmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rdivmod___exposer extends PyBuiltinMethodNarrow {
      public long___rdivmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public long___rdivmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rdivmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rdivmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___pow___exposer extends PyBuiltinMethodNarrow {
      public long___pow___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public long___pow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___pow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         PyObject var10000 = ((PyLong)this.self).long___pow__(var1, var2);
         return var10000 == null ? Py.NotImplemented : var10000;
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___pow__(var1, (PyObject)null);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rpow___exposer extends PyBuiltinMethodNarrow {
      public long___rpow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public long___rpow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rpow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rpow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___lshift___exposer extends PyBuiltinMethodNarrow {
      public long___lshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lshift__(y) <==> x<<y";
      }

      public long___lshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lshift__(y) <==> x<<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___lshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___lshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rlshift___exposer extends PyBuiltinMethodNarrow {
      public long___rlshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rlshift__(y) <==> y<<x";
      }

      public long___rlshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rlshift__(y) <==> y<<x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rlshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rlshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rshift___exposer extends PyBuiltinMethodNarrow {
      public long___rshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rshift__(y) <==> x>>y";
      }

      public long___rshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rshift__(y) <==> x>>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rrshift___exposer extends PyBuiltinMethodNarrow {
      public long___rrshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rrshift__(y) <==> y>>x";
      }

      public long___rrshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rrshift__(y) <==> y>>x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rrshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rrshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___and___exposer extends PyBuiltinMethodNarrow {
      public long___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public long___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rand___exposer extends PyBuiltinMethodNarrow {
      public long___rand___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rand__(y) <==> y&x";
      }

      public long___rand___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rand__(y) <==> y&x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rand___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rand__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___xor___exposer extends PyBuiltinMethodNarrow {
      public long___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public long___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___rxor___exposer extends PyBuiltinMethodNarrow {
      public long___rxor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rxor__(y) <==> y^x";
      }

      public long___rxor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rxor__(y) <==> y^x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___rxor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___rxor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___or___exposer extends PyBuiltinMethodNarrow {
      public long___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public long___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___ror___exposer extends PyBuiltinMethodNarrow {
      public long___ror___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ror__(y) <==> y|x";
      }

      public long___ror___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ror__(y) <==> y|x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___ror___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyLong)this.self).long___ror__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class long___neg___exposer extends PyBuiltinMethodNarrow {
      public long___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__neg__() <==> -x";
      }

      public long___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__neg__() <==> -x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___neg__();
      }
   }

   private static class long___pos___exposer extends PyBuiltinMethodNarrow {
      public long___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__pos__() <==> +x";
      }

      public long___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pos__() <==> +x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___pos__();
      }
   }

   private static class long___abs___exposer extends PyBuiltinMethodNarrow {
      public long___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public long___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___abs__();
      }
   }

   private static class long___invert___exposer extends PyBuiltinMethodNarrow {
      public long___invert___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__invert__() <==> ~x";
      }

      public long___invert___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__invert__() <==> ~x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___invert___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___invert__();
      }
   }

   private static class long___int___exposer extends PyBuiltinMethodNarrow {
      public long___int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__int__() <==> int(x)";
      }

      public long___int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__int__() <==> int(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___int__();
      }
   }

   private static class long___long___exposer extends PyBuiltinMethodNarrow {
      public long___long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__long__() <==> long(x)";
      }

      public long___long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__long__() <==> long(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___long__();
      }
   }

   private static class long___float___exposer extends PyBuiltinMethodNarrow {
      public long___float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__float__() <==> float(x)";
      }

      public long___float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__float__() <==> float(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___float__();
      }
   }

   private static class long___trunc___exposer extends PyBuiltinMethodNarrow {
      public long___trunc___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Truncating an Integral returns itself.";
      }

      public long___trunc___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Truncating an Integral returns itself.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___trunc___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___trunc__();
      }
   }

   private static class long_conjugate_exposer extends PyBuiltinMethodNarrow {
      public long_conjugate_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns self, the complex conjugate of any long.";
      }

      public long_conjugate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns self, the complex conjugate of any long.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long_conjugate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long_conjugate();
      }
   }

   private static class long___oct___exposer extends PyBuiltinMethodNarrow {
      public long___oct___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__oct__() <==> oct(x)";
      }

      public long___oct___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__oct__() <==> oct(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___oct___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___oct__();
      }
   }

   private static class long___hex___exposer extends PyBuiltinMethodNarrow {
      public long___hex___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hex__() <==> hex(x)";
      }

      public long___hex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hex__() <==> hex(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___hex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___hex__();
      }
   }

   private static class long___str___exposer extends PyBuiltinMethodNarrow {
      public long___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public long___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___str__();
      }
   }

   private static class long___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public long___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public long___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___getnewargs__();
      }
   }

   private static class long___index___exposer extends PyBuiltinMethodNarrow {
      public long___index___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x[y:z] <==> x[y.__index__():z.__index__()]";
      }

      public long___index___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x[y:z] <==> x[y.__index__():z.__index__()]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___index___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyLong)this.self).long___index__();
      }
   }

   private static class long_bit_length_exposer extends PyBuiltinMethodNarrow {
      public long_bit_length_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "long.bit_length() -> int or long\n\nNumber of bits necessary to represent self in binary.\n>>> bin(37L)\n'0b100101'\n>>> (37L).bit_length()\n6";
      }

      public long_bit_length_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "long.bit_length() -> int or long\n\nNumber of bits necessary to represent self in binary.\n>>> bin(37L)\n'0b100101'\n>>> (37L).bit_length()\n6";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long_bit_length_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyLong)this.self).long_bit_length());
      }
   }

   private static class long___format___exposer extends PyBuiltinMethodNarrow {
      public long___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public long___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new long___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyLong)this.self).long___format__(var1);
      }
   }

   private static class real_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public real_descriptor() {
         super("real", PyObject.class, "the real part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyLong)var1).getReal();
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
         return ((PyLong)var1).getImag();
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

   private static class numerator_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public numerator_descriptor() {
         super("numerator", PyObject.class, "the numerator of a rational number in lowest terms");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyLong)var1).getNumerator();
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

   private static class denominator_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public denominator_descriptor() {
         super("denominator", PyObject.class, "the denominator of a rational number in lowest terms");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyLong)var1).getDenominator();
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
         return PyLong.long___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new long_toString_exposer("__repr__"), new long___hash___exposer("__hash__"), new long___nonzero___exposer("__nonzero__"), new long___cmp___exposer("__cmp__"), new long___coerce___exposer("__coerce__"), new long___add___exposer("__add__"), new long___radd___exposer("__radd__"), new long___sub___exposer("__sub__"), new long___rsub___exposer("__rsub__"), new long___mul___exposer("__mul__"), new long___rmul___exposer("__rmul__"), new long___div___exposer("__div__"), new long___rdiv___exposer("__rdiv__"), new long___floordiv___exposer("__floordiv__"), new long___rfloordiv___exposer("__rfloordiv__"), new long___truediv___exposer("__truediv__"), new long___rtruediv___exposer("__rtruediv__"), new long___mod___exposer("__mod__"), new long___rmod___exposer("__rmod__"), new long___divmod___exposer("__divmod__"), new long___rdivmod___exposer("__rdivmod__"), new long___pow___exposer("__pow__"), new long___rpow___exposer("__rpow__"), new long___lshift___exposer("__lshift__"), new long___rlshift___exposer("__rlshift__"), new long___rshift___exposer("__rshift__"), new long___rrshift___exposer("__rrshift__"), new long___and___exposer("__and__"), new long___rand___exposer("__rand__"), new long___xor___exposer("__xor__"), new long___rxor___exposer("__rxor__"), new long___or___exposer("__or__"), new long___ror___exposer("__ror__"), new long___neg___exposer("__neg__"), new long___pos___exposer("__pos__"), new long___abs___exposer("__abs__"), new long___invert___exposer("__invert__"), new long___int___exposer("__int__"), new long___long___exposer("__long__"), new long___float___exposer("__float__"), new long___trunc___exposer("__trunc__"), new long_conjugate_exposer("conjugate"), new long___oct___exposer("__oct__"), new long___hex___exposer("__hex__"), new long___str___exposer("__str__"), new long___getnewargs___exposer("__getnewargs__"), new long___index___exposer("__index__"), new long_bit_length_exposer("bit_length"), new long___format___exposer("__format__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new real_descriptor(), new imag_descriptor(), new numerator_descriptor(), new denominator_descriptor()};
         super("long", PyLong.class, Object.class, (boolean)1, "long(x[, base]) -> integer\n\nConvert a string or number to a long integer, if possible.  A floating\npoint argument will be truncated towards zero (this does not include a\nstring representation of a floating point number!)  When converting a\nstring, use the optional base.  It is an error to supply a base when\nconverting a non-string.", var1, var2, new exposed___new__());
      }
   }
}

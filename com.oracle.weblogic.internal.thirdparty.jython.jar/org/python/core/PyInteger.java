package org.python.core;

import java.io.Serializable;
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
   name = "int",
   doc = "int(x[, base]) -> integer\n\nConvert a string or number to an integer, if possible.  A floating point\nargument will be truncated towards zero (this does not include a string\nrepresentation of a floating point number!)  When converting a string, use\nthe optional base.  It is an error to supply a base when converting a\nnon-string.  If base is zero, the proper base is guessed based on the\nstring content.  If the argument is outside the integer range a\nlong object will be returned instead."
)
public class PyInteger extends PyObject {
   public static final PyType TYPE;
   public static final BigInteger MIN_INT;
   public static final BigInteger MAX_INT;
   /** @deprecated */
   @Deprecated
   public static final BigInteger minInt;
   /** @deprecated */
   @Deprecated
   public static final BigInteger maxInt;
   private static final String LOOKUP = "0123456789abcdef";
   private final int value;

   public PyInteger(PyType subType, int v) {
      super(subType);
      this.value = v;
   }

   public PyInteger(int v) {
      this(TYPE, v);
   }

   @ExposedNew
   public static PyObject int_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("int", args, keywords, new String[]{"x", "base"}, 0);
      PyObject x = ap.getPyObject(0, (PyObject)null);
      int base = ap.getInt(1, -909);
      if (new_.for_type != subtype) {
         if (x == null) {
            return new PyIntegerDerived(subtype, 0);
         } else if (base == -909) {
            PyObject intOrLong = asPyInteger(x);
            if (intOrLong instanceof PyInteger) {
               return new PyIntegerDerived(subtype, ((PyInteger)intOrLong).getValue());
            } else {
               throw Py.OverflowError("long int too large to convert to int");
            }
         } else if (!(x instanceof PyString)) {
            throw Py.TypeError("int: can't convert non-string with explicit base");
         } else {
            return new PyIntegerDerived(subtype, ((PyString)x).atoi(base));
         }
      } else if (x == null) {
         return Py.Zero;
      } else if (base == -909) {
         if (x instanceof PyBoolean) {
            return coerce(x) == 0 ? Py.Zero : Py.One;
         } else if (x instanceof PyByteArray) {
            PyString xs = new PyString(x.asString());
            return asPyInteger(xs);
         } else {
            return asPyInteger(x);
         }
      } else if (!(x instanceof PyString)) {
         throw Py.TypeError("int: can't convert non-string with explicit base");
      } else {
         try {
            return Py.newInteger(((PyString)x).atoi(base));
         } catch (PyException var9) {
            if (var9.match(Py.OverflowError)) {
               return ((PyString)x).atol(base);
            } else {
               throw var9;
            }
         }
      }
   }

   private static PyObject asPyInteger(PyObject x) throws PyException {
      try {
         return x.__int__();
      } catch (PyException var5) {
         if (!var5.match(Py.AttributeError)) {
            throw var5;
         } else {
            try {
               PyObject integral = x.invoke("__trunc__");
               return convertIntegralToInt(integral);
            } catch (PyException var4) {
               if (!var4.match(Py.AttributeError)) {
                  throw var4;
               } else {
                  String fmt = "int() argument must be a string or a number, not '%.200s'";
                  throw Py.TypeError(String.format(fmt, x));
               }
            }
         }
      }
   }

   private static PyObject convertIntegralToInt(PyObject integral) {
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

   public PyObject getReal() {
      return this.int___int__();
   }

   public PyObject getImag() {
      return Py.newInteger(0);
   }

   public PyObject getNumerator() {
      return this.int___int__();
   }

   public PyObject getDenominator() {
      return Py.newInteger(1);
   }

   public int getValue() {
      return this.value;
   }

   public String toString() {
      return this.int_toString();
   }

   final String int_toString() {
      return Integer.toString(this.getValue());
   }

   public int hashCode() {
      return this.int_hashCode();
   }

   final int int_hashCode() {
      return this.getValue();
   }

   public boolean __nonzero__() {
      return this.int___nonzero__();
   }

   final boolean int___nonzero__() {
      return this.getValue() != 0;
   }

   public Object __tojava__(Class c) {
      if (c != Integer.TYPE && c != Number.class && c != Object.class && c != Integer.class && c != Serializable.class) {
         if (c != Boolean.TYPE && c != Boolean.class) {
            if (c != Byte.TYPE && c != Byte.class) {
               if (c != Short.TYPE && c != Short.class) {
                  if (c != Long.TYPE && c != Long.class) {
                     if (c != Float.TYPE && c != Float.class) {
                        return c != Double.TYPE && c != Double.class ? super.__tojava__(c) : new Double((double)this.getValue());
                     } else {
                        return new Float((float)this.getValue());
                     }
                  } else {
                     return new Long((long)this.getValue());
                  }
               } else {
                  return new Short((short)this.getValue());
               }
            } else {
               return new Byte((byte)this.getValue());
            }
         } else {
            return new Boolean(this.getValue() != 0);
         }
      } else {
         return new Integer(this.getValue());
      }
   }

   public int __cmp__(PyObject other) {
      return this.int___cmp__(other);
   }

   final int int___cmp__(PyObject other) {
      if (!canCoerce(other)) {
         return -2;
      } else {
         int v = coerce(other);
         return this.getValue() < v ? -1 : (this.getValue() > v ? 1 : 0);
      }
   }

   public Object __coerce_ex__(PyObject other) {
      return this.int___coerce_ex__(other);
   }

   final PyObject int___coerce__(PyObject other) {
      return this.adaptToCoerceTuple(this.int___coerce_ex__(other));
   }

   final Object int___coerce_ex__(PyObject other) {
      return other instanceof PyInteger ? other : Py.None;
   }

   private static final boolean canCoerce(PyObject other) {
      return other instanceof PyInteger;
   }

   private static final int coerce(PyObject other) {
      if (other instanceof PyInteger) {
         return ((PyInteger)other).getValue();
      } else {
         throw Py.TypeError("xxx");
      }
   }

   public PyObject __add__(PyObject right) {
      return this.int___add__(right);
   }

   final PyObject int___add__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerce(right);
         int a = this.getValue();
         int x = a + rightv;
         return (PyObject)((x ^ a) < 0 && (x ^ rightv) < 0 ? new PyLong((long)a + (long)rightv) : Py.newInteger(x));
      }
   }

   public PyObject __radd__(PyObject left) {
      return this.int___radd__(left);
   }

   final PyObject int___radd__(PyObject left) {
      return this.__add__(left);
   }

   private static PyObject _sub(int a, int b) {
      int x = a - b;
      return (PyObject)((x ^ a) < 0 && (x ^ ~b) < 0 ? new PyLong((long)a - (long)b) : Py.newInteger(x));
   }

   public PyObject __sub__(PyObject right) {
      return this.int___sub__(right);
   }

   final PyObject int___sub__(PyObject right) {
      return !canCoerce(right) ? null : _sub(this.getValue(), coerce(right));
   }

   public PyObject __rsub__(PyObject left) {
      return this.int___rsub__(left);
   }

   final PyObject int___rsub__(PyObject left) {
      return !canCoerce(left) ? null : _sub(coerce(left), this.getValue());
   }

   public PyObject __mul__(PyObject right) {
      return this.int___mul__(right);
   }

   final PyObject int___mul__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerce(right);
         double x = (double)this.getValue();
         x *= (double)rightv;
         return (PyObject)(x <= 2.147483647E9 && x >= -2.147483648E9 ? Py.newInteger((int)x) : this.__long__().__mul__(right));
      }
   }

   public PyObject __rmul__(PyObject left) {
      return this.int___rmul__(left);
   }

   final PyObject int___rmul__(PyObject left) {
      return this.__mul__(left);
   }

   private static long divide(long x, long y) {
      if (y == 0L) {
         throw Py.ZeroDivisionError("integer division or modulo by zero");
      } else {
         long xdivy = x / y;
         long xmody = x - xdivy * y;
         if (xmody != 0L && (y < 0L && xmody > 0L || y > 0L && xmody < 0L)) {
            long var10000 = xmody + y;
            --xdivy;
         }

         return xdivy;
      }
   }

   public PyObject __div__(PyObject right) {
      return this.int___div__(right);
   }

   final PyObject int___div__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         if (Options.division_warning > 0) {
            Py.warning(Py.DeprecationWarning, "classic int division");
         }

         return Py.newInteger(divide((long)this.getValue(), (long)coerce(right)));
      }
   }

   public PyObject __rdiv__(PyObject left) {
      return this.int___rdiv__(left);
   }

   final PyObject int___rdiv__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         if (Options.division_warning > 0) {
            Py.warning(Py.DeprecationWarning, "classic int division");
         }

         return Py.newInteger(divide((long)coerce(left), (long)this.getValue()));
      }
   }

   public PyObject __floordiv__(PyObject right) {
      return this.int___floordiv__(right);
   }

   final PyObject int___floordiv__(PyObject right) {
      return !canCoerce(right) ? null : Py.newInteger(divide((long)this.getValue(), (long)coerce(right)));
   }

   public PyObject __rfloordiv__(PyObject left) {
      return this.int___rfloordiv__(left);
   }

   final PyObject int___rfloordiv__(PyObject left) {
      return !canCoerce(left) ? null : Py.newInteger(divide((long)coerce(left), (long)this.getValue()));
   }

   public PyObject __truediv__(PyObject right) {
      return this.int___truediv__(right);
   }

   final PyObject int___truediv__(PyObject right) {
      if (right instanceof PyInteger) {
         return this.__float__().__truediv__(right);
      } else {
         return right instanceof PyLong ? this.int___long__().__truediv__(right) : null;
      }
   }

   public PyObject __rtruediv__(PyObject left) {
      return this.int___rtruediv__(left);
   }

   final PyObject int___rtruediv__(PyObject left) {
      if (left instanceof PyInteger) {
         return left.__float__().__truediv__(this);
      } else {
         return left instanceof PyLong ? left.__truediv__(this.int___long__()) : null;
      }
   }

   private static long modulo(long x, long y, long xdivy) {
      return x - xdivy * y;
   }

   public PyObject __mod__(PyObject right) {
      return this.int___mod__(right);
   }

   final PyObject int___mod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerce(right);
         int v = this.getValue();
         return Py.newInteger(modulo((long)v, (long)rightv, divide((long)v, (long)rightv)));
      }
   }

   public PyObject __rmod__(PyObject left) {
      return this.int___rmod__(left);
   }

   final PyObject int___rmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         int leftv = coerce(left);
         int v = this.getValue();
         return Py.newInteger(modulo((long)leftv, (long)v, divide((long)leftv, (long)v)));
      }
   }

   public PyObject __divmod__(PyObject right) {
      return this.int___divmod__(right);
   }

   final PyObject int___divmod__(PyObject right) {
      if (!canCoerce(right)) {
         return null;
      } else {
         int rightv = coerce(right);
         int v = this.getValue();
         long xdivy = divide((long)v, (long)rightv);
         return new PyTuple(new PyObject[]{Py.newInteger(xdivy), Py.newInteger(modulo((long)v, (long)rightv, xdivy))});
      }
   }

   final PyObject int___rdivmod__(PyObject left) {
      if (!canCoerce(left)) {
         return null;
      } else {
         int leftv = coerce(left);
         int v = this.getValue();
         long xdivy = divide((long)leftv, (long)v);
         return new PyTuple(new PyObject[]{Py.newInteger(xdivy), Py.newInteger(modulo((long)leftv, (long)v, xdivy))});
      }
   }

   public PyObject __pow__(PyObject right, PyObject modulo) {
      return this.int___pow__(right, modulo);
   }

   final PyObject int___pow__(PyObject right, PyObject modulo) {
      if (!canCoerce(right)) {
         return null;
      } else {
         modulo = modulo == Py.None ? null : modulo;
         return modulo != null && !canCoerce(modulo) ? null : _pow(this.getValue(), coerce(right), modulo, this, right);
      }
   }

   public PyObject __rpow__(PyObject left) {
      return !canCoerce(left) ? null : _pow(coerce(left), this.getValue(), (PyObject)null, left, this);
   }

   final PyObject int___rpow__(PyObject left) {
      return this.__rpow__(left);
   }

   private static PyObject _pow(int value, int pow, PyObject modulo, PyObject left, PyObject right) {
      int mod = 0;
      long tmp = (long)value;
      boolean neg = false;
      if (tmp < 0L) {
         tmp = -tmp;
         neg = (pow & 1) != 0;
      }

      long result = 1L;
      if (pow < 0) {
         if (value != 0) {
            return left.__float__().__pow__(right, modulo);
         } else {
            throw Py.ZeroDivisionError("0.0 cannot be raised to a negative power");
         }
      } else {
         if (modulo != null) {
            mod = coerce(modulo);
            if (mod == 0) {
               throw Py.ValueError("pow(x, y, z) with z==0");
            }
         }

         while(pow > 0) {
            if ((pow & 1) != 0) {
               result *= tmp;
               if (mod != 0) {
                  result %= (long)mod;
               }

               if (result > 2147483647L) {
                  return left.__long__().__pow__(right, modulo);
               }
            }

            pow >>= 1;
            if (pow == 0) {
               break;
            }

            tmp *= tmp;
            if (mod != 0) {
               tmp %= (long)mod;
            }

            if (tmp > 2147483647L) {
               return left.__long__().__pow__(right, modulo);
            }
         }

         if (neg) {
            result = -result;
         }

         if (mod != 0) {
            result = modulo(result, (long)mod, divide(result, (long)mod));
         }

         return Py.newInteger(result);
      }
   }

   public PyObject __lshift__(PyObject right) {
      return this.int___lshift__(right);
   }

   final PyObject int___lshift__(PyObject right) {
      if (right instanceof PyInteger) {
         int rightv = ((PyInteger)right).getValue();
         if (rightv >= 32) {
            return this.__long__().__lshift__(right);
         } else if (rightv < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            int result = this.getValue() << rightv;
            return (PyObject)(this.getValue() != result >> rightv ? this.__long__().__lshift__(right) : Py.newInteger(result));
         }
      } else {
         return right instanceof PyLong ? this.int___long__().__lshift__(right) : null;
      }
   }

   final PyObject int___rlshift__(PyObject left) {
      if (left instanceof PyInteger) {
         int leftv = ((PyInteger)left).getValue();
         if (this.getValue() >= 32) {
            return left.__long__().__lshift__(this);
         } else if (this.getValue() < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            int result = leftv << this.getValue();
            return (PyObject)(leftv != result >> this.getValue() ? left.__long__().__lshift__(this) : Py.newInteger(result));
         }
      } else {
         return left instanceof PyLong ? left.__rlshift__(this.int___long__()) : null;
      }
   }

   public PyObject __rshift__(PyObject right) {
      return this.int___rshift__(right);
   }

   final PyObject int___rshift__(PyObject right) {
      if (right instanceof PyInteger) {
         int rightv = ((PyInteger)right).getValue();
         if (rightv < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            return rightv >= 32 ? Py.newInteger(this.getValue() < 0 ? -1 : 0) : Py.newInteger(this.getValue() >> rightv);
         }
      } else {
         return right instanceof PyLong ? this.int___long__().__rshift__(right) : null;
      }
   }

   final PyObject int___rrshift__(PyObject left) {
      if (left instanceof PyInteger) {
         int leftv = ((PyInteger)left).getValue();
         if (this.getValue() < 0) {
            throw Py.ValueError("negative shift count");
         } else {
            return this.getValue() >= 32 ? Py.newInteger(leftv < 0 ? -1 : 0) : Py.newInteger(leftv >> this.getValue());
         }
      } else {
         return left instanceof PyLong ? left.__rshift__(this.int___long__()) : null;
      }
   }

   public PyObject __and__(PyObject right) {
      return this.int___and__(right);
   }

   final PyObject int___and__(PyObject right) {
      if (right instanceof PyInteger) {
         int rightv = ((PyInteger)right).getValue();
         return Py.newInteger(this.getValue() & rightv);
      } else {
         return right instanceof PyLong ? this.int___long__().__and__(right) : null;
      }
   }

   final PyObject int___rand__(PyObject left) {
      return this.int___and__(left);
   }

   public PyObject __xor__(PyObject right) {
      return this.int___xor__(right);
   }

   final PyObject int___xor__(PyObject right) {
      if (right instanceof PyInteger) {
         int rightv = ((PyInteger)right).getValue();
         return Py.newInteger(this.getValue() ^ rightv);
      } else {
         return right instanceof PyLong ? this.int___long__().__xor__(right) : null;
      }
   }

   final PyObject int___rxor__(PyObject left) {
      if (left instanceof PyInteger) {
         int leftv = ((PyInteger)left).getValue();
         return Py.newInteger(leftv ^ this.getValue());
      } else {
         return left instanceof PyLong ? left.__rxor__(this.int___long__()) : null;
      }
   }

   public PyObject __or__(PyObject right) {
      return this.int___or__(right);
   }

   final PyObject int___or__(PyObject right) {
      if (right instanceof PyInteger) {
         int rightv = ((PyInteger)right).getValue();
         return Py.newInteger(this.getValue() | rightv);
      } else {
         return right instanceof PyLong ? this.int___long__().__or__(right) : null;
      }
   }

   final PyObject int___ror__(PyObject left) {
      return this.int___or__(left);
   }

   public PyObject __neg__() {
      return this.int___neg__();
   }

   final PyObject int___neg__() {
      long x = (long)this.getValue();
      long result = -x;
      return x < 0L && result == x ? (new PyLong(x)).__neg__() : Py.newInteger(result);
   }

   public PyObject __pos__() {
      return this.int___pos__();
   }

   final PyObject int___pos__() {
      return this.int___int__();
   }

   public PyObject __abs__() {
      return this.int___abs__();
   }

   final PyObject int___abs__() {
      return (PyObject)(this.getValue() < 0 ? this.int___neg__() : this.int___int__());
   }

   public PyObject __invert__() {
      return this.int___invert__();
   }

   final PyObject int___invert__() {
      return Py.newInteger(~this.getValue());
   }

   public PyObject __int__() {
      return this.int___int__();
   }

   final PyInteger int___int__() {
      return this.getType() == TYPE ? this : Py.newInteger(this.getValue());
   }

   public PyObject __long__() {
      return this.int___long__();
   }

   final PyObject int___long__() {
      return new PyLong((long)this.getValue());
   }

   public PyFloat __float__() {
      return this.int___float__();
   }

   final PyFloat int___float__() {
      return new PyFloat((double)this.getValue());
   }

   public PyObject __trunc__() {
      return this.int___trunc__();
   }

   final PyObject int___trunc__() {
      return this;
   }

   public PyObject conjugate() {
      return this.int_conjugate();
   }

   final PyObject int_conjugate() {
      return this;
   }

   public PyComplex __complex__() {
      return new PyComplex((double)this.getValue(), 0.0);
   }

   public PyString __oct__() {
      return this.int___oct__();
   }

   final PyString int___oct__() {
      return this.formatImpl(IntegerFormatter.OCT);
   }

   public PyString __hex__() {
      return this.int___hex__();
   }

   final PyString int___hex__() {
      return this.formatImpl(IntegerFormatter.HEX);
   }

   private PyString formatImpl(InternalFormat.Spec spec) {
      IntegerFormatter f = new IntegerFormatter.Traditional(spec);
      f.format(this.value);
      return new PyString(f.getResult());
   }

   final PyTuple int___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyInteger(this.getValue())});
   }

   public PyTuple __getnewargs__() {
      return this.int___getnewargs__();
   }

   public PyObject __index__() {
      return this.int___index__();
   }

   final PyObject int___index__() {
      return this;
   }

   public int bit_length() {
      return this.int_bit_length();
   }

   final int int_bit_length() {
      int v = this.value;
      if (v < 0) {
         v = -v;
      }

      return BigInteger.valueOf((long)v).bitLength();
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.int___format__(formatSpec);
   }

   final PyObject int___format__(PyObject formatSpec) {
      InternalFormat.Spec spec = InternalFormat.fromText(formatSpec, "__format__");
      IntegerFormatter fi = prepareFormatter(spec);
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
         ff.format((double)this.value);
         f = ff;
      }

      return ((InternalFormat.Formatter)f).pad().getPyResult();
   }

   static IntegerFormatter prepareFormatter(InternalFormat.Spec spec) throws PyException {
      switch (spec.type) {
         case 'c':
            if (InternalFormat.Spec.specified(spec.sign)) {
               throw IntegerFormatter.signNotAllowed("integer", spec.type);
            } else if (spec.alternate) {
               throw IntegerFormatter.alternateFormNotAllowed("integer", spec.type);
            }
         case 'X':
         case 'b':
         case 'n':
         case 'o':
         case 'x':
            if (spec.grouping) {
               throw IntegerFormatter.notAllowed("Grouping", "integer", spec.type);
            }
         case 'd':
         case '\uffff':
            if (InternalFormat.Spec.specified(spec.precision)) {
               throw IntegerFormatter.precisionNotAllowed("integer");
            }

            spec = spec.withDefaults(InternalFormat.Spec.NUMERIC);
            return new IntegerFormatter(spec);
         default:
            return null;
      }
   }

   public boolean isIndex() {
      return true;
   }

   public int asIndex(PyObject err) {
      return this.getValue();
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

   public long asLong(int index) {
      return (long)this.getValue();
   }

   public int asInt(int index) {
      return this.getValue();
   }

   public int asInt() {
      return this.getValue();
   }

   public long asLong() {
      return (long)this.getValue();
   }

   static {
      PyType.addBuilder(PyInteger.class, new PyExposer());
      TYPE = PyType.fromClass(PyInteger.class);
      MIN_INT = BigInteger.valueOf(-2147483648L);
      MAX_INT = BigInteger.valueOf(2147483647L);
      minInt = MIN_INT;
      maxInt = MAX_INT;
   }

   private static class int_toString_exposer extends PyBuiltinMethodNarrow {
      public int_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public int_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyInteger)this.self).int_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class int_hashCode_exposer extends PyBuiltinMethodNarrow {
      public int_hashCode_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public int_hashCode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int_hashCode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyInteger)this.self).int_hashCode());
      }
   }

   private static class int___nonzero___exposer extends PyBuiltinMethodNarrow {
      public int___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public int___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyInteger)this.self).int___nonzero__());
      }
   }

   private static class int___cmp___exposer extends PyBuiltinMethodNarrow {
      public int___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public int___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyInteger)this.self).int___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("int.__cmp__(x,y) requires y to be 'int', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class int___coerce___exposer extends PyBuiltinMethodNarrow {
      public int___coerce___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public int___coerce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___coerce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInteger)this.self).int___coerce__(var1);
      }
   }

   private static class int___add___exposer extends PyBuiltinMethodNarrow {
      public int___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public int___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___radd___exposer extends PyBuiltinMethodNarrow {
      public int___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public int___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___sub___exposer extends PyBuiltinMethodNarrow {
      public int___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public int___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rsub___exposer extends PyBuiltinMethodNarrow {
      public int___rsub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public int___rsub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rsub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rsub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___mul___exposer extends PyBuiltinMethodNarrow {
      public int___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public int___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rmul___exposer extends PyBuiltinMethodNarrow {
      public int___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public int___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___div___exposer extends PyBuiltinMethodNarrow {
      public int___div___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public int___div___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___div___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___div__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rdiv___exposer extends PyBuiltinMethodNarrow {
      public int___rdiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public int___rdiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rdiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rdiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___floordiv___exposer extends PyBuiltinMethodNarrow {
      public int___floordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public int___floordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___floordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___floordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rfloordiv___exposer extends PyBuiltinMethodNarrow {
      public int___rfloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public int___rfloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rfloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rfloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___truediv___exposer extends PyBuiltinMethodNarrow {
      public int___truediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public int___truediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___truediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___truediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rtruediv___exposer extends PyBuiltinMethodNarrow {
      public int___rtruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public int___rtruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rtruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rtruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___mod___exposer extends PyBuiltinMethodNarrow {
      public int___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public int___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___mod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rmod___exposer extends PyBuiltinMethodNarrow {
      public int___rmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public int___rmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___divmod___exposer extends PyBuiltinMethodNarrow {
      public int___divmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public int___divmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___divmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___divmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rdivmod___exposer extends PyBuiltinMethodNarrow {
      public int___rdivmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public int___rdivmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rdivmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rdivmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___pow___exposer extends PyBuiltinMethodNarrow {
      public int___pow___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public int___pow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___pow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         PyObject var10000 = ((PyInteger)this.self).int___pow__(var1, var2);
         return var10000 == null ? Py.NotImplemented : var10000;
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___pow__(var1, (PyObject)null);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rpow___exposer extends PyBuiltinMethodNarrow {
      public int___rpow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public int___rpow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rpow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rpow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___lshift___exposer extends PyBuiltinMethodNarrow {
      public int___lshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lshift__(y) <==> x<<y";
      }

      public int___lshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lshift__(y) <==> x<<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___lshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___lshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rlshift___exposer extends PyBuiltinMethodNarrow {
      public int___rlshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rlshift__(y) <==> y<<x";
      }

      public int___rlshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rlshift__(y) <==> y<<x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rlshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rlshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rshift___exposer extends PyBuiltinMethodNarrow {
      public int___rshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rshift__(y) <==> x>>y";
      }

      public int___rshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rshift__(y) <==> x>>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rrshift___exposer extends PyBuiltinMethodNarrow {
      public int___rrshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rrshift__(y) <==> y>>x";
      }

      public int___rrshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rrshift__(y) <==> y>>x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rrshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rrshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___and___exposer extends PyBuiltinMethodNarrow {
      public int___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public int___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rand___exposer extends PyBuiltinMethodNarrow {
      public int___rand___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rand__(y) <==> y&x";
      }

      public int___rand___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rand__(y) <==> y&x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rand___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rand__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___xor___exposer extends PyBuiltinMethodNarrow {
      public int___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public int___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___rxor___exposer extends PyBuiltinMethodNarrow {
      public int___rxor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rxor__(y) <==> y^x";
      }

      public int___rxor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rxor__(y) <==> y^x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___rxor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___rxor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___or___exposer extends PyBuiltinMethodNarrow {
      public int___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public int___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___ror___exposer extends PyBuiltinMethodNarrow {
      public int___ror___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ror__(y) <==> y|x";
      }

      public int___ror___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ror__(y) <==> y|x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___ror___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInteger)this.self).int___ror__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class int___neg___exposer extends PyBuiltinMethodNarrow {
      public int___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__neg__() <==> -x";
      }

      public int___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__neg__() <==> -x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___neg__();
      }
   }

   private static class int___pos___exposer extends PyBuiltinMethodNarrow {
      public int___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__pos__() <==> +x";
      }

      public int___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pos__() <==> +x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___pos__();
      }
   }

   private static class int___abs___exposer extends PyBuiltinMethodNarrow {
      public int___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public int___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___abs__();
      }
   }

   private static class int___invert___exposer extends PyBuiltinMethodNarrow {
      public int___invert___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__invert__() <==> ~x";
      }

      public int___invert___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__invert__() <==> ~x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___invert___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___invert__();
      }
   }

   private static class int___int___exposer extends PyBuiltinMethodNarrow {
      public int___int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__int__() <==> int(x)";
      }

      public int___int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__int__() <==> int(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___int__();
      }
   }

   private static class int___long___exposer extends PyBuiltinMethodNarrow {
      public int___long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__long__() <==> long(x)";
      }

      public int___long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__long__() <==> long(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___long__();
      }
   }

   private static class int___float___exposer extends PyBuiltinMethodNarrow {
      public int___float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__float__() <==> float(x)";
      }

      public int___float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__float__() <==> float(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___float__();
      }
   }

   private static class int___trunc___exposer extends PyBuiltinMethodNarrow {
      public int___trunc___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Truncating an Integral returns itself.";
      }

      public int___trunc___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Truncating an Integral returns itself.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___trunc___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___trunc__();
      }
   }

   private static class int_conjugate_exposer extends PyBuiltinMethodNarrow {
      public int_conjugate_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns self, the complex conjugate of any int.";
      }

      public int_conjugate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns self, the complex conjugate of any int.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int_conjugate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int_conjugate();
      }
   }

   private static class int___oct___exposer extends PyBuiltinMethodNarrow {
      public int___oct___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__oct__() <==> oct(x)";
      }

      public int___oct___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__oct__() <==> oct(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___oct___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___oct__();
      }
   }

   private static class int___hex___exposer extends PyBuiltinMethodNarrow {
      public int___hex___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hex__() <==> hex(x)";
      }

      public int___hex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hex__() <==> hex(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___hex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___hex__();
      }
   }

   private static class int___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public int___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public int___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___getnewargs__();
      }
   }

   private static class int___index___exposer extends PyBuiltinMethodNarrow {
      public int___index___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x[y:z] <==> x[y.__index__():z.__index__()]";
      }

      public int___index___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x[y:z] <==> x[y.__index__():z.__index__()]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___index___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInteger)this.self).int___index__();
      }
   }

   private static class int_bit_length_exposer extends PyBuiltinMethodNarrow {
      public int_bit_length_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "int.bit_length() -> int\n\nNumber of bits necessary to represent self in binary.\n>>> bin(37)\n'0b100101'\n>>> (37).bit_length()\n6";
      }

      public int_bit_length_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "int.bit_length() -> int\n\nNumber of bits necessary to represent self in binary.\n>>> bin(37)\n'0b100101'\n>>> (37).bit_length()\n6";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int_bit_length_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyInteger)this.self).int_bit_length());
      }
   }

   private static class int___format___exposer extends PyBuiltinMethodNarrow {
      public int___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public int___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new int___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInteger)this.self).int___format__(var1);
      }
   }

   private static class real_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public real_descriptor() {
         super("real", PyObject.class, "the real part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyInteger)var1).getReal();
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
         return ((PyInteger)var1).getImag();
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
         return ((PyInteger)var1).getNumerator();
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
         return ((PyInteger)var1).getDenominator();
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
         return PyInteger.int_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new int_toString_exposer("__str__"), new int_toString_exposer("__repr__"), new int_hashCode_exposer("__hash__"), new int___nonzero___exposer("__nonzero__"), new int___cmp___exposer("__cmp__"), new int___coerce___exposer("__coerce__"), new int___add___exposer("__add__"), new int___radd___exposer("__radd__"), new int___sub___exposer("__sub__"), new int___rsub___exposer("__rsub__"), new int___mul___exposer("__mul__"), new int___rmul___exposer("__rmul__"), new int___div___exposer("__div__"), new int___rdiv___exposer("__rdiv__"), new int___floordiv___exposer("__floordiv__"), new int___rfloordiv___exposer("__rfloordiv__"), new int___truediv___exposer("__truediv__"), new int___rtruediv___exposer("__rtruediv__"), new int___mod___exposer("__mod__"), new int___rmod___exposer("__rmod__"), new int___divmod___exposer("__divmod__"), new int___rdivmod___exposer("__rdivmod__"), new int___pow___exposer("__pow__"), new int___rpow___exposer("__rpow__"), new int___lshift___exposer("__lshift__"), new int___rlshift___exposer("__rlshift__"), new int___rshift___exposer("__rshift__"), new int___rrshift___exposer("__rrshift__"), new int___and___exposer("__and__"), new int___rand___exposer("__rand__"), new int___xor___exposer("__xor__"), new int___rxor___exposer("__rxor__"), new int___or___exposer("__or__"), new int___ror___exposer("__ror__"), new int___neg___exposer("__neg__"), new int___pos___exposer("__pos__"), new int___abs___exposer("__abs__"), new int___invert___exposer("__invert__"), new int___int___exposer("__int__"), new int___long___exposer("__long__"), new int___float___exposer("__float__"), new int___trunc___exposer("__trunc__"), new int_conjugate_exposer("conjugate"), new int___oct___exposer("__oct__"), new int___hex___exposer("__hex__"), new int___getnewargs___exposer("__getnewargs__"), new int___index___exposer("__index__"), new int_bit_length_exposer("bit_length"), new int___format___exposer("__format__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new real_descriptor(), new imag_descriptor(), new numerator_descriptor(), new denominator_descriptor()};
         super("int", PyInteger.class, Object.class, (boolean)1, "int(x[, base]) -> integer\n\nConvert a string or number to an integer, if possible.  A floating point\nargument will be truncated towards zero (this does not include a string\nrepresentation of a floating point number!)  When converting a string, use\nthe optional base.  It is an error to supply a base when converting a\nnon-string.  If base is zero, the proper base is guessed based on the\nstring content.  If the argument is outside the integer range a\nlong object will be returned instead.", var1, var2, new exposed___new__());
      }
   }
}

package org.python.core;

import org.python.core.stringlib.FloatFormatter;
import org.python.core.stringlib.InternalFormat;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "complex",
   doc = "complex(real[, imag]) -> complex number\n\nCreate a complex number from a real part and an optional imaginary part.\nThis is equivalent to (real + imag*1j) where imag defaults to 0."
)
public class PyComplex extends PyObject {
   public static final PyType TYPE;
   static final InternalFormat.Spec SPEC_REPR;
   static final InternalFormat.Spec SPEC_STR;
   static PyComplex J;
   public static final PyComplex Inf;
   public static final PyComplex NaN;
   public double real;
   public double imag;

   public PyComplex(PyType subtype, double r, double i) {
      super(subtype);
      this.real = r;
      this.imag = i;
   }

   public PyComplex(double r, double i) {
      this(TYPE, r, i);
   }

   public PyComplex(double r) {
      this(r, 0.0);
   }

   @ExposedNew
   public static PyObject complex_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("complex", args, keywords, "real", "imag");
      PyObject real = ap.getPyObject(0, Py.Zero);
      PyObject imag = ap.getPyObject(1, (PyObject)null);
      if (((PyObject)real).getType() == TYPE && new_.for_type == subtype && imag == null) {
         return (PyObject)real;
      } else if (real instanceof PyString) {
         if (imag != null) {
            throw Py.TypeError("complex() can't take second arg if first is a string");
         } else {
            return ((PyObject)real).__complex__();
         }
      } else if (imag != null && imag instanceof PyString) {
         throw Py.TypeError("complex() second arg can't be a string");
      } else {
         try {
            real = ((PyObject)real).__complex__();
         } catch (PyException var14) {
            if (!var14.match(Py.AttributeError)) {
               throw var14;
            }
         }

         PyFloat toFloat = null;
         Object complexReal;
         if (real instanceof PyComplex) {
            complexReal = (PyComplex)real;
         } else {
            try {
               toFloat = ((PyObject)real).__float__();
            } catch (PyException var12) {
               if (var12.match(Py.AttributeError)) {
                  throw Py.TypeError("complex() argument must be a string or a number");
               }

               throw var12;
            }

            complexReal = new PyComplex(toFloat.getValue());
         }

         PyComplex complexImag;
         if (imag == null) {
            complexImag = new PyComplex(0.0);
         } else if (imag instanceof PyComplex) {
            complexImag = (PyComplex)imag;
         } else {
            toFloat = null;

            try {
               toFloat = imag.__float__();
            } catch (PyException var13) {
               if (var13.match(Py.AttributeError)) {
                  throw Py.TypeError("complex() argument must be a string or a number");
               }

               throw var13;
            }

            complexImag = new PyComplex(toFloat.getValue());
         }

         ((PyComplex)complexReal).real -= complexImag.imag;
         if (((PyComplex)complexReal).imag == 0.0) {
            ((PyComplex)complexReal).imag = complexImag.real;
         } else {
            ((PyComplex)complexReal).imag += complexImag.real;
         }

         if (new_.for_type != subtype) {
            complexReal = new PyComplexDerived(subtype, ((PyComplex)complexReal).real, ((PyComplex)complexReal).imag);
         }

         return (PyObject)complexReal;
      }
   }

   public final PyFloat getReal() {
      return Py.newFloat(this.real);
   }

   public final PyFloat getImag() {
      return Py.newFloat(this.imag);
   }

   public static String toString(double value) {
      return value == Math.floor(value) && value <= 9.223372036854776E18 && value >= -9.223372036854776E18 ? Long.toString((long)value) : Double.toString(value);
   }

   public String toString() {
      return this.__str__().toString();
   }

   public PyString __str__() {
      return this.complex___str__();
   }

   final PyString complex___str__() {
      return Py.newString(this.formatComplex(SPEC_STR));
   }

   public PyString __repr__() {
      return this.complex___repr__();
   }

   final PyString complex___repr__() {
      return Py.newString(this.formatComplex(SPEC_REPR));
   }

   private String formatComplex(InternalFormat.Spec spec) {
      int size = 2 * FloatFormatter.size(spec) + 3;
      FloatFormatter f = new FloatFormatter(new StringBuilder(size), spec);
      f.setBytes(true);
      f.setMinFracDigits(0);
      if (Double.doubleToLongBits(this.real) == 0L) {
         f.format(this.imag).append('j');
      } else {
         f.append('(').format(this.real).format(this.imag, "+").append("j)");
      }

      return f.pad().getResult();
   }

   public int hashCode() {
      return this.complex___hash__();
   }

   final int complex___hash__() {
      if (this.imag == 0.0) {
         return (new PyFloat(this.real)).hashCode();
      } else {
         long v = Double.doubleToLongBits(this.real) ^ Double.doubleToLongBits(this.imag);
         return (int)v ^ (int)(v >> 32);
      }
   }

   public boolean __nonzero__() {
      return this.complex___nonzero__();
   }

   final boolean complex___nonzero__() {
      return this.real != 0.0 || this.imag != 0.0;
   }

   public int __cmp__(PyObject other) {
      if (!this.canCoerce(other)) {
         return -2;
      } else {
         PyComplex c = this.coerce(other);
         double oreal = c.real;
         double oimag = c.imag;
         if (this.real == oreal && this.imag == oimag) {
            return 0;
         } else if (this.real != oreal) {
            return this.real < oreal ? -1 : 1;
         } else {
            return this.imag < oimag ? -1 : 1;
         }
      }
   }

   public PyObject __eq__(PyObject other) {
      return this.complex___eq__(other);
   }

   final PyObject complex___eq__(PyObject other) {
      switch (this.eq_helper(other)) {
         case 0:
            return Py.False;
         case 1:
            return Py.True;
         default:
            return null;
      }
   }

   private int eq_helper(PyObject other) {
      boolean equal;
      if (other instanceof PyComplex) {
         PyComplex c = (PyComplex)other;
         equal = this.real == c.real && this.imag == c.imag;
      } else if (other instanceof PyFloat) {
         PyFloat f = (PyFloat)other;
         equal = this.imag == 0.0 && this.real == f.getValue();
      } else {
         if (!(other instanceof PyInteger) && !(other instanceof PyLong)) {
            return 2;
         }

         if (this.imag == 0.0) {
            double r = this.real;
            if (!Double.isInfinite(r) && !Double.isNaN(r)) {
               PyFloat f = new PyFloat(r);
               equal = f.float___cmp__(other) == 0;
            } else {
               equal = false;
            }
         } else {
            equal = false;
         }
      }

      return equal ? 1 : 0;
   }

   public PyObject __ne__(PyObject other) {
      return this.complex___ne__(other);
   }

   final PyObject complex___ne__(PyObject other) {
      switch (this.eq_helper(other)) {
         case 0:
            return Py.True;
         case 1:
            return Py.False;
         default:
            return null;
      }
   }

   private PyObject unsupported_comparison(PyObject other) {
      if (!this.canCoerce(other)) {
         return null;
      } else {
         throw Py.TypeError("cannot compare complex numbers using <, <=, >, >=");
      }
   }

   public PyObject __ge__(PyObject other) {
      return this.complex___ge__(other);
   }

   final PyObject complex___ge__(PyObject other) {
      return this.unsupported_comparison(other);
   }

   public PyObject __gt__(PyObject other) {
      return this.complex___gt__(other);
   }

   final PyObject complex___gt__(PyObject other) {
      return this.unsupported_comparison(other);
   }

   public PyObject __le__(PyObject other) {
      return this.complex___le__(other);
   }

   final PyObject complex___le__(PyObject other) {
      return this.unsupported_comparison(other);
   }

   public PyObject __lt__(PyObject other) {
      return this.complex___lt__(other);
   }

   final PyObject complex___lt__(PyObject other) {
      return this.unsupported_comparison(other);
   }

   public Object __coerce_ex__(PyObject other) {
      return this.complex___coerce_ex__(other);
   }

   final PyObject complex___coerce__(PyObject other) {
      return this.adaptToCoerceTuple(this.complex___coerce_ex__(other));
   }

   final PyObject complex___coerce_ex__(PyObject other) {
      if (other instanceof PyComplex) {
         return other;
      } else if (other instanceof PyFloat) {
         return new PyComplex(((PyFloat)other).getValue(), 0.0);
      } else if (other instanceof PyInteger) {
         return new PyComplex((double)((PyInteger)other).getValue(), 0.0);
      } else {
         return (PyObject)(other instanceof PyLong ? new PyComplex(((PyLong)other).doubleValue(), 0.0) : Py.None);
      }
   }

   private final boolean canCoerce(PyObject other) {
      return other instanceof PyComplex || other instanceof PyFloat || other instanceof PyInteger || other instanceof PyLong;
   }

   private final PyComplex coerce(PyObject other) {
      if (other instanceof PyComplex) {
         return (PyComplex)other;
      } else if (other instanceof PyFloat) {
         return new PyComplex(((PyFloat)other).getValue(), 0.0);
      } else if (other instanceof PyInteger) {
         return new PyComplex((double)((PyInteger)other).getValue(), 0.0);
      } else if (other instanceof PyLong) {
         return new PyComplex(((PyLong)other).doubleValue(), 0.0);
      } else {
         throw Py.TypeError("xxx");
      }
   }

   public PyObject __add__(PyObject right) {
      return this.complex___add__(right);
   }

   final PyObject complex___add__(PyObject right) {
      if (!this.canCoerce(right)) {
         return null;
      } else {
         PyComplex c = this.coerce(right);
         return new PyComplex(this.real + c.real, this.imag + c.imag);
      }
   }

   public PyObject __radd__(PyObject left) {
      return this.complex___radd__(left);
   }

   final PyObject complex___radd__(PyObject left) {
      return this.__add__(left);
   }

   private static final PyObject _sub(PyComplex o1, PyComplex o2) {
      return new PyComplex(o1.real - o2.real, o1.imag - o2.imag);
   }

   public PyObject __sub__(PyObject right) {
      return this.complex___sub__(right);
   }

   final PyObject complex___sub__(PyObject right) {
      return !this.canCoerce(right) ? null : _sub(this, this.coerce(right));
   }

   public PyObject __rsub__(PyObject left) {
      return this.complex___rsub__(left);
   }

   final PyObject complex___rsub__(PyObject left) {
      return !this.canCoerce(left) ? null : _sub(this.coerce(left), this);
   }

   private static final PyObject _mul(PyComplex o1, PyComplex o2) {
      return new PyComplex(o1.real * o2.real - o1.imag * o2.imag, o1.real * o2.imag + o1.imag * o2.real);
   }

   public PyObject __mul__(PyObject right) {
      return this.complex___mul__(right);
   }

   final PyObject complex___mul__(PyObject right) {
      return !this.canCoerce(right) ? null : _mul(this, this.coerce(right));
   }

   public PyObject __rmul__(PyObject left) {
      return this.complex___rmul__(left);
   }

   final PyObject complex___rmul__(PyObject left) {
      return !this.canCoerce(left) ? null : _mul(this.coerce(left), this);
   }

   private static final PyObject _div(PyComplex a, PyComplex b) {
      double abs_breal = b.real < 0.0 ? -b.real : b.real;
      double abs_bimag = b.imag < 0.0 ? -b.imag : b.imag;
      double ratio;
      double denom;
      if (abs_breal >= abs_bimag) {
         if (abs_breal == 0.0) {
            throw Py.ZeroDivisionError("complex division");
         } else {
            ratio = b.imag / b.real;
            denom = b.real + b.imag * ratio;
            return new PyComplex((a.real + a.imag * ratio) / denom, (a.imag - a.real * ratio) / denom);
         }
      } else {
         ratio = b.real / b.imag;
         denom = b.real * ratio + b.imag;
         return new PyComplex((a.real * ratio + a.imag) / denom, (a.imag * ratio - a.real) / denom);
      }
   }

   public PyObject __div__(PyObject right) {
      return this.complex___div__(right);
   }

   final PyObject complex___div__(PyObject right) {
      if (!this.canCoerce(right)) {
         return null;
      } else {
         if (Options.division_warning >= 2) {
            Py.warning(Py.DeprecationWarning, "classic complex division");
         }

         return _div(this, this.coerce(right));
      }
   }

   public PyObject __rdiv__(PyObject left) {
      return this.complex___rdiv__(left);
   }

   final PyObject complex___rdiv__(PyObject left) {
      if (!this.canCoerce(left)) {
         return null;
      } else {
         if (Options.division_warning >= 2) {
            Py.warning(Py.DeprecationWarning, "classic complex division");
         }

         return _div(this.coerce(left), this);
      }
   }

   public PyObject __floordiv__(PyObject right) {
      return this.complex___floordiv__(right);
   }

   final PyObject complex___floordiv__(PyObject right) {
      return !this.canCoerce(right) ? null : _divmod(this, this.coerce(right)).__finditem__(0);
   }

   public PyObject __rfloordiv__(PyObject left) {
      return this.complex___rfloordiv__(left);
   }

   final PyObject complex___rfloordiv__(PyObject left) {
      return !this.canCoerce(left) ? null : _divmod(this.coerce(left), this).__finditem__(0);
   }

   public Object __tojava__(Class c) {
      return c.isInstance(this) ? this : Py.NoConversion;
   }

   public PyObject __truediv__(PyObject right) {
      return this.complex___truediv__(right);
   }

   final PyObject complex___truediv__(PyObject right) {
      return !this.canCoerce(right) ? null : _div(this, this.coerce(right));
   }

   public PyObject __rtruediv__(PyObject left) {
      return this.complex___rtruediv__(left);
   }

   final PyObject complex___rtruediv__(PyObject left) {
      return !this.canCoerce(left) ? null : _div(this.coerce(left), this);
   }

   public PyObject __mod__(PyObject right) {
      return this.complex___mod__(right);
   }

   final PyObject complex___mod__(PyObject right) {
      return !this.canCoerce(right) ? null : _mod(this, this.coerce(right));
   }

   public PyObject __rmod__(PyObject left) {
      return this.complex___rmod__(left);
   }

   final PyObject complex___rmod__(PyObject left) {
      return !this.canCoerce(left) ? null : _mod(this.coerce(left), this);
   }

   private static PyObject _mod(PyComplex value, PyComplex right) {
      Py.warning(Py.DeprecationWarning, "complex divmod(), // and % are deprecated");
      PyComplex z = (PyComplex)_div(value, right);
      z.real = Math.floor(z.real);
      z.imag = 0.0;
      return value.__sub__(z.__mul__(right));
   }

   public PyObject __divmod__(PyObject right) {
      return this.complex___divmod__(right);
   }

   final PyObject complex___divmod__(PyObject right) {
      return !this.canCoerce(right) ? null : _divmod(this, this.coerce(right));
   }

   public PyObject __rdivmod__(PyObject left) {
      return this.complex___rdivmod__(left);
   }

   final PyObject complex___rdivmod__(PyObject left) {
      return !this.canCoerce(left) ? null : _divmod(this.coerce(left), this);
   }

   private static PyObject _divmod(PyComplex value, PyComplex right) {
      Py.warning(Py.DeprecationWarning, "complex divmod(), // and % are deprecated");
      PyComplex z = (PyComplex)_div(value, right);
      z.real = Math.floor(z.real);
      z.imag = 0.0;
      return new PyTuple(new PyObject[]{z, value.__sub__(z.__mul__(right))});
   }

   private static PyObject ipow(PyComplex value, int iexp) {
      int pow = iexp;
      if (iexp < 0) {
         pow = -iexp;
      }

      double xr = value.real;
      double xi = value.imag;
      double zr = 1.0;

      double zi;
      double tmp;
      for(zi = 0.0; pow > 0; xr = tmp) {
         if ((pow & 1) != 0) {
            tmp = zr * xr - zi * xi;
            zi = zi * xr + zr * xi;
            zr = tmp;
         }

         pow >>= 1;
         if (pow == 0) {
            break;
         }

         tmp = xr * xr - xi * xi;
         xi = xr * xi * 2.0;
      }

      PyComplex ret = new PyComplex(zr, zi);
      return (PyObject)(iexp < 0 ? (new PyComplex(1.0, 0.0)).__div__(ret) : ret);
   }

   public PyObject __pow__(PyObject right, PyObject modulo) {
      return this.complex___pow__(right, modulo);
   }

   final PyObject complex___pow__(PyObject right, PyObject modulo) {
      if (modulo != null) {
         throw Py.ValueError("complex modulo");
      } else {
         return !this.canCoerce(right) ? null : _pow(this, this.coerce(right));
      }
   }

   public PyObject __rpow__(PyObject left) {
      return this.complex___rpow__(left);
   }

   final PyObject complex___rpow__(PyObject left) {
      return !this.canCoerce(left) ? null : _pow(this.coerce(left), this);
   }

   public static PyObject _pow(PyComplex value, PyComplex right) {
      double xr = value.real;
      double xi = value.imag;
      double yr = right.real;
      double yi = right.imag;
      if (yr == 0.0 && yi == 0.0) {
         return new PyComplex(1.0, 0.0);
      } else if (xr != 0.0 || xi != 0.0 || yi == 0.0 && !(yr < 0.0)) {
         int iexp = (int)yr;
         if (yi == 0.0 && yr == (double)iexp && iexp >= -128 && iexp <= 128) {
            return ipow(value, iexp);
         } else {
            double abs = Math.hypot(xr, xi);
            double len = Math.pow(abs, yr);
            double at = Math.atan2(xi, xr);
            double phase = at * yr;
            if (yi != 0.0) {
               len /= Math.exp(at * yi);
               phase += yi * Math.log(abs);
            }

            return new PyComplex(len * Math.cos(phase), len * Math.sin(phase));
         }
      } else {
         throw Py.ZeroDivisionError("0.0 to a negative or complex power");
      }
   }

   public PyObject __neg__() {
      return this.complex___neg__();
   }

   final PyObject complex___neg__() {
      return new PyComplex(-this.real, -this.imag);
   }

   public PyObject __pos__() {
      return this.complex___pos__();
   }

   final PyObject complex___pos__() {
      return this.getType() == TYPE ? this : new PyComplex(this.real, this.imag);
   }

   public PyObject __invert__() {
      throw Py.TypeError("bad operand type for unary ~");
   }

   public PyObject __abs__() {
      return this.complex___abs__();
   }

   final PyObject complex___abs__() {
      double mag = Math.hypot(this.real, this.imag);
      if (Double.isInfinite(mag) && !Double.isInfinite(this.real) && !Double.isInfinite(this.imag)) {
         throw Py.OverflowError("absolute value too large");
      } else {
         return new PyFloat(mag);
      }
   }

   public PyObject __int__() {
      return this.complex___int__();
   }

   final PyInteger complex___int__() {
      throw Py.TypeError("can't convert complex to int; use e.g. int(abs(z))");
   }

   public PyObject __long__() {
      return this.complex___long__();
   }

   final PyObject complex___long__() {
      throw Py.TypeError("can't convert complex to long; use e.g. long(abs(z))");
   }

   public PyFloat __float__() {
      return this.complex___float__();
   }

   final PyFloat complex___float__() {
      throw Py.TypeError("can't convert complex to float; use e.g. abs(z)");
   }

   public PyComplex __complex__() {
      return new PyComplex(this.real, this.imag);
   }

   public PyComplex conjugate() {
      return this.complex_conjugate();
   }

   final PyComplex complex_conjugate() {
      return new PyComplex(this.real, -this.imag);
   }

   final PyTuple complex___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyFloat(this.real), new PyFloat(this.imag)});
   }

   public PyTuple __getnewargs__() {
      return this.complex___getnewargs__();
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.complex___format__(formatSpec);
   }

   final PyObject complex___format__(PyObject var1) {
      // $FF: Couldn't be decompiled
   }

   private static int checkSpecification(InternalFormat.Spec spec) {
      switch (spec.type) {
         case 'n':
            if (spec.grouping) {
               throw InternalFormat.Formatter.notAllowed("Grouping", "complex", spec.type);
            }
         case 'E':
         case 'F':
         case 'G':
         case 'e':
         case 'f':
         case 'g':
         case '\uffff':
            if (spec.alternate) {
               throw FloatFormatter.alternateFormNotAllowed("complex");
            } else if (spec.fill == '0') {
               throw FloatFormatter.zeroPaddingNotAllowed("complex");
            } else {
               if (spec.align == '=') {
                  throw FloatFormatter.alignmentNotAllowed('=', "complex");
               }

               return spec.type == '\uffff' ? 0 : 1;
            }
         default:
            return 2;
      }
   }

   public boolean isNumberType() {
      return true;
   }

   static {
      PyType.addBuilder(PyComplex.class, new PyExposer());
      TYPE = PyType.fromClass(PyComplex.class);
      SPEC_REPR = InternalFormat.fromText(" >r");
      SPEC_STR = InternalFormat.fromText(" >.12g");
      J = new PyComplex(0.0, 1.0);
      Inf = new PyComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
      NaN = new PyComplex(Double.NaN, Double.NaN);
   }

   private static class complex___str___exposer extends PyBuiltinMethodNarrow {
      public complex___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public complex___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___str__();
      }
   }

   private static class complex___repr___exposer extends PyBuiltinMethodNarrow {
      public complex___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public complex___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___repr__();
      }
   }

   private static class complex___hash___exposer extends PyBuiltinMethodNarrow {
      public complex___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public complex___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyComplex)this.self).complex___hash__());
      }
   }

   private static class complex___nonzero___exposer extends PyBuiltinMethodNarrow {
      public complex___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public complex___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyComplex)this.self).complex___nonzero__());
      }
   }

   private static class complex___eq___exposer extends PyBuiltinMethodNarrow {
      public complex___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public complex___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___ne___exposer extends PyBuiltinMethodNarrow {
      public complex___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public complex___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___ge___exposer extends PyBuiltinMethodNarrow {
      public complex___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public complex___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___gt___exposer extends PyBuiltinMethodNarrow {
      public complex___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public complex___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___le___exposer extends PyBuiltinMethodNarrow {
      public complex___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public complex___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___lt___exposer extends PyBuiltinMethodNarrow {
      public complex___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public complex___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___coerce___exposer extends PyBuiltinMethodNarrow {
      public complex___coerce___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public complex___coerce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__coerce__(y) <==> coerce(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___coerce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyComplex)this.self).complex___coerce__(var1);
      }
   }

   private static class complex___add___exposer extends PyBuiltinMethodNarrow {
      public complex___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public complex___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___radd___exposer extends PyBuiltinMethodNarrow {
      public complex___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public complex___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__radd__(y) <==> y+x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___sub___exposer extends PyBuiltinMethodNarrow {
      public complex___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public complex___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rsub___exposer extends PyBuiltinMethodNarrow {
      public complex___rsub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public complex___rsub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rsub__(y) <==> y-x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rsub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rsub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___mul___exposer extends PyBuiltinMethodNarrow {
      public complex___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public complex___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(y) <==> x*y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rmul___exposer extends PyBuiltinMethodNarrow {
      public complex___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public complex___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(y) <==> y*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___div___exposer extends PyBuiltinMethodNarrow {
      public complex___div___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public complex___div___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__div__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___div___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___div__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rdiv___exposer extends PyBuiltinMethodNarrow {
      public complex___rdiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public complex___rdiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdiv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rdiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rdiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___floordiv___exposer extends PyBuiltinMethodNarrow {
      public complex___floordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public complex___floordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__floordiv__(y) <==> x//y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___floordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___floordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rfloordiv___exposer extends PyBuiltinMethodNarrow {
      public complex___rfloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public complex___rfloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rfloordiv__(y) <==> y//x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rfloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rfloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___truediv___exposer extends PyBuiltinMethodNarrow {
      public complex___truediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public complex___truediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__truediv__(y) <==> x/y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___truediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___truediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rtruediv___exposer extends PyBuiltinMethodNarrow {
      public complex___rtruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public complex___rtruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rtruediv__(y) <==> y/x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rtruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rtruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___mod___exposer extends PyBuiltinMethodNarrow {
      public complex___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public complex___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___mod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rmod___exposer extends PyBuiltinMethodNarrow {
      public complex___rmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public complex___rmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmod__(y) <==> y%x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___divmod___exposer extends PyBuiltinMethodNarrow {
      public complex___divmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public complex___divmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__divmod__(y) <==> divmod(x, y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___divmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___divmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rdivmod___exposer extends PyBuiltinMethodNarrow {
      public complex___rdivmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public complex___rdivmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rdivmod__(y) <==> divmod(y, x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rdivmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rdivmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___pow___exposer extends PyBuiltinMethodNarrow {
      public complex___pow___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public complex___pow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pow__(y[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___pow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         PyObject var10000 = ((PyComplex)this.self).complex___pow__(var1, var2);
         return var10000 == null ? Py.NotImplemented : var10000;
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___pow__(var1, (PyObject)null);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___rpow___exposer extends PyBuiltinMethodNarrow {
      public complex___rpow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public complex___rpow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "y.__rpow__(x[, z]) <==> pow(x, y[, z])";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___rpow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyComplex)this.self).complex___rpow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class complex___neg___exposer extends PyBuiltinMethodNarrow {
      public complex___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__neg__() <==> -x";
      }

      public complex___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__neg__() <==> -x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___neg__();
      }
   }

   private static class complex___pos___exposer extends PyBuiltinMethodNarrow {
      public complex___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__pos__() <==> +x";
      }

      public complex___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pos__() <==> +x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___pos__();
      }
   }

   private static class complex___abs___exposer extends PyBuiltinMethodNarrow {
      public complex___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public complex___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___abs__();
      }
   }

   private static class complex___int___exposer extends PyBuiltinMethodNarrow {
      public complex___int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__int__() <==> int(x)";
      }

      public complex___int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__int__() <==> int(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___int__();
      }
   }

   private static class complex___long___exposer extends PyBuiltinMethodNarrow {
      public complex___long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__long__() <==> long(x)";
      }

      public complex___long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__long__() <==> long(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___long__();
      }
   }

   private static class complex___float___exposer extends PyBuiltinMethodNarrow {
      public complex___float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__float__() <==> float(x)";
      }

      public complex___float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__float__() <==> float(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___float__();
      }
   }

   private static class complex_conjugate_exposer extends PyBuiltinMethodNarrow {
      public complex_conjugate_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "complex.conjugate() -> complex\n\nReturns the complex conjugate of its argument. (3-4j).conjugate() == 3+4j.";
      }

      public complex_conjugate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "complex.conjugate() -> complex\n\nReturns the complex conjugate of its argument. (3-4j).conjugate() == 3+4j.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex_conjugate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex_conjugate();
      }
   }

   private static class complex___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public complex___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public complex___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyComplex)this.self).complex___getnewargs__();
      }
   }

   private static class complex___format___exposer extends PyBuiltinMethodNarrow {
      public complex___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "complex.__format__() -> str\n\nConverts to a string according to format_spec.";
      }

      public complex___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "complex.__format__() -> str\n\nConverts to a string according to format_spec.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new complex___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyComplex)this.self).complex___format__(var1);
      }
   }

   private static class real_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public real_descriptor() {
         super("real", Double.class, "the real part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newFloat(((PyComplex)var1).real);
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
         super("imag", Double.class, "the imaginary part of a complex number");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newFloat(((PyComplex)var1).imag);
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
         return PyComplex.complex_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new complex___str___exposer("__str__"), new complex___repr___exposer("__repr__"), new complex___hash___exposer("__hash__"), new complex___nonzero___exposer("__nonzero__"), new complex___eq___exposer("__eq__"), new complex___ne___exposer("__ne__"), new complex___ge___exposer("__ge__"), new complex___gt___exposer("__gt__"), new complex___le___exposer("__le__"), new complex___lt___exposer("__lt__"), new complex___coerce___exposer("__coerce__"), new complex___add___exposer("__add__"), new complex___radd___exposer("__radd__"), new complex___sub___exposer("__sub__"), new complex___rsub___exposer("__rsub__"), new complex___mul___exposer("__mul__"), new complex___rmul___exposer("__rmul__"), new complex___div___exposer("__div__"), new complex___rdiv___exposer("__rdiv__"), new complex___floordiv___exposer("__floordiv__"), new complex___rfloordiv___exposer("__rfloordiv__"), new complex___truediv___exposer("__truediv__"), new complex___rtruediv___exposer("__rtruediv__"), new complex___mod___exposer("__mod__"), new complex___rmod___exposer("__rmod__"), new complex___divmod___exposer("__divmod__"), new complex___rdivmod___exposer("__rdivmod__"), new complex___pow___exposer("__pow__"), new complex___rpow___exposer("__rpow__"), new complex___neg___exposer("__neg__"), new complex___pos___exposer("__pos__"), new complex___abs___exposer("__abs__"), new complex___int___exposer("__int__"), new complex___long___exposer("__long__"), new complex___float___exposer("__float__"), new complex_conjugate_exposer("conjugate"), new complex___getnewargs___exposer("__getnewargs__"), new complex___format___exposer("__format__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new real_descriptor(), new imag_descriptor()};
         super("complex", PyComplex.class, Object.class, (boolean)1, "complex(real[, imag]) -> complex number\n\nCreate a complex number from a real part and an optional imaginary part.\nThis is equivalent to (real + imag*1j) where imag defaults to 0.", var1, var2, new exposed___new__());
      }
   }
}

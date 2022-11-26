package org.python.core.stringlib;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FloatFormatter extends InternalFormat.Formatter {
   static final RoundingMode ROUND_PY;
   static final int MAX_PRECISION = 1400;
   private int lenPoint;
   private int lenFraction;
   private int lenMarker;
   private int lenExponent;
   private int minFracDigits;
   private static final long SIGN_MASK = Long.MIN_VALUE;
   private static final long EXP_MASK = 9218868437227405312L;

   public FloatFormatter(StringBuilder result, InternalFormat.Spec spec) {
      super(result, spec);
      if (spec.alternate) {
         this.minFracDigits = -1;
      } else if (spec.type != 'r' && spec.type != '\uffff') {
         this.minFracDigits = 0;
      } else {
         this.minFracDigits = 1;
      }

   }

   public FloatFormatter(InternalFormat.Spec spec) {
      this(new StringBuilder(size(spec)), spec);
   }

   public static int size(InternalFormat.Spec spec) {
      return Math.max(spec.width + 1, spec.getPrecision(6) + 11);
   }

   public void setMinFracDigits(int minFracDigits) {
      this.minFracDigits = minFracDigits;
   }

   protected void reset() {
      super.reset();
      this.lenPoint = this.lenFraction = this.lenMarker = this.lenExponent = 0;
   }

   protected int[] sectionLengths() {
      return new int[]{this.lenSign, this.lenWhole, this.lenPoint, this.lenFraction, this.lenMarker, this.lenExponent};
   }

   public FloatFormatter append(char c) {
      super.append(c);
      return this;
   }

   public FloatFormatter append(CharSequence csq) {
      super.append(csq);
      return this;
   }

   public FloatFormatter append(CharSequence csq, int start, int end) throws IndexOutOfBoundsException {
      super.append(csq, start, end);
      return this;
   }

   public FloatFormatter format(double value) {
      return this.format(value, (String)null);
   }

   public FloatFormatter format(double var1, String var3) {
      // $FF: Couldn't be decompiled
   }

   protected void uppercase() {
      int letters = this.indexOfMarker();
      int end = letters + this.lenMarker;

      for(int i = letters; i < end; ++i) {
         char c = this.result.charAt(i);
         this.result.setCharAt(i, Character.toUpperCase(c));
      }

   }

   private boolean signAndSpecialNumber(double value, String positivePrefix) {
      long bits = Double.doubleToRawLongBits(value);
      if (Double.isNaN(value)) {
         bits &= Long.MAX_VALUE;
      }

      if ((bits & Long.MIN_VALUE) != 0L) {
         this.result.append('-');
         this.lenSign = 1;
         bits &= Long.MAX_VALUE;
      } else if (positivePrefix != null) {
         this.result.append(positivePrefix);
         this.lenSign = positivePrefix.length();
      }

      if (bits == 0L) {
         this.result.append('0');
         this.lenWhole = 1;
         return true;
      } else if ((bits & 9218868437227405312L) == 9218868437227405312L) {
         this.result.append((bits & -9218868437227405313L) == 0L ? "inf" : "nan");
         this.lenMarker = 3;
         return true;
      } else {
         return false;
      }
   }

   private void format_e(double value, String positivePrefix, int precision) {
      int exp = 0;
      if (!this.signAndSpecialNumber(value, positivePrefix)) {
         MathContext mc = new MathContext(precision + 1, ROUND_PY);
         BigDecimal vv = new BigDecimal(Math.abs(value), mc);
         String digits = vv.unscaledValue().toString();
         int digitCount = digits.length();
         this.result.append(digits.charAt(0));
         this.lenWhole = 1;
         if (digitCount > 1) {
            this.result.append('.').append(digits.substring(1));
            this.lenPoint = 1;
            this.lenFraction = digitCount - 1;
         }

         exp = this.lenFraction - vv.scale();
      }

      if (this.lenMarker == 0) {
         this.ensurePointAndTrailingZeros(precision);
         this.appendExponent(exp);
      }

   }

   private void format_f(double value, String positivePrefix, int precision) {
      if (!this.signAndSpecialNumber(value, positivePrefix)) {
         BigDecimal vLong = new BigDecimal(Math.abs(value));
         BigDecimal vv = vLong.setScale(precision, ROUND_PY);
         String raw = vv.toPlainString();
         this.result.append(raw);
         if ((this.lenFraction = vv.scale()) > 0) {
            this.lenWhole = this.result.length() - (this.start + this.lenSign + (this.lenPoint = 1) + this.lenFraction);
         } else {
            this.lenWhole = this.result.length() - (this.start + this.lenSign);
         }
      }

      if (this.lenMarker == 0) {
         this.ensurePointAndTrailingZeros(precision);
      }

   }

   private void ensurePointAndTrailingZeros(int n) {
      if (n < this.minFracDigits) {
         n = this.minFracDigits;
      }

      if (this.lenPoint == 0 && (n > 0 || this.minFracDigits < 0)) {
         this.result.append('.');
         this.lenPoint = 1;
      }

      int f = this.lenFraction;
      if (n > f) {
         while(f < n) {
            this.result.append('0');
            ++f;
         }

         this.lenFraction = f;
      }

   }

   private void format_g(double value, String positivePrefix, int precision, int expThresholdAdj) {
      precision = Math.max(1, precision);
      int expThreshold = precision + expThresholdAdj;
      if (this.signAndSpecialNumber(value, positivePrefix)) {
         this.zeroHelper(precision, expThreshold);
      } else {
         MathContext mc = new MathContext(precision, ROUND_PY);
         BigDecimal vv = new BigDecimal(Math.abs(value), mc);
         String pointlessDigits = vv.unscaledValue().toString();
         int exp = pointlessDigits.length() - vv.scale() - 1;
         if (-4 <= exp && exp < expThreshold) {
            this.appendFixed(pointlessDigits, exp, precision);
         } else {
            this.appendExponential(pointlessDigits, exp);
         }
      }

   }

   private void format_r(double value, String positivePrefix) {
      int precision = 17;
      int expThreshold = precision - 1;
      if (this.signAndSpecialNumber(value, positivePrefix)) {
         this.zeroHelper(precision, expThreshold);
      } else {
         StringBuilder pointlessBuffer = new StringBuilder(20);
         int exp = reprDigits(Math.abs(value), precision, pointlessBuffer);
         if (-4 <= exp && exp < expThreshold) {
            this.appendFixed(pointlessBuffer, exp, precision);
         } else {
            this.appendExponential(pointlessBuffer, exp);
         }
      }

   }

   private void zeroHelper(int precision, int expThreshold) {
      if (this.lenMarker == 0) {
         if (this.minFracDigits < 0) {
            this.appendPointAndTrailingZeros(precision - 1);
         } else if (this.lenFraction < this.minFracDigits) {
            this.appendTrailingZeros(this.minFracDigits);
         }

         if (0 >= expThreshold) {
            this.appendExponent(0);
         }
      }

   }

   private void appendFixed(CharSequence digits, int exp, int precision) {
      boolean noTruncate = this.minFracDigits < 0;
      int digitCount = digits.length();
      int w;
      if (exp < 0) {
         this.result.append("0.");
         this.lenWhole = this.lenPoint = 1;

         for(w = -1; w > exp; --w) {
            this.result.append('0');
         }

         this.result.append(digits);
         this.lenFraction = digitCount - exp - 1;
      } else {
         w = exp + 1;
         if (w < digitCount) {
            this.result.append(digits.subSequence(0, w));
            this.lenWhole = w;
            this.result.append('.').append(digits.subSequence(w, digitCount));
            this.lenPoint = 1;
            this.lenFraction = digitCount - w;
         } else {
            this.result.append(digits);

            while(digitCount < w) {
               this.result.append('0');
               ++digitCount;
            }

            this.lenWhole = digitCount;
         }

         if (noTruncate) {
            this.appendPointAndTrailingZeros(precision - digitCount);
         }
      }

      if (!noTruncate) {
         if (this.lenFraction < this.minFracDigits) {
            this.appendTrailingZeros(this.minFracDigits);
         } else {
            this.removeTrailingZeros(this.minFracDigits);
         }
      }

   }

   private void appendExponential(CharSequence digits, int exp) {
      this.result.append(digits.charAt(0));
      this.lenWhole = 1;
      int digitCount = digits.length();
      this.result.append('.').append(digits.subSequence(1, digitCount));
      this.lenPoint = 1;
      this.lenFraction = digitCount - 1;
      if (this.minFracDigits >= 0) {
         this.removeTrailingZeros(0);
      }

      this.appendExponent(exp);
   }

   private static int reprDigits(double value, int maxDigits, StringBuilder buf) {
      String s = Double.toString(value);
      int p = 0;
      int end = s.length();
      int first = 0;
      int point = end;
      char c = 0;
      boolean allZero = true;

      while(p < end) {
         c = s.charAt(p++);
         if (Character.isDigit(c)) {
            if (allZero) {
               if (c != '0') {
                  buf.append(c);
                  allZero = false;
                  first = p;
               }
            } else {
               buf.append(c);
            }
         } else {
            if (c != '.') {
               break;
            }

            point = p;
         }
      }

      int exp;
      if (p < end && c == 'E') {
         assert point == first + 1;

         exp = Integer.parseInt(s.substring(p));
      } else {
         exp = point - first - 1;
         if (exp < 0) {
            ++exp;
         }
      }

      if (buf.length() > maxDigits) {
         int d = buf.charAt(maxDigits);
         buf.setLength(maxDigits);
         if (d >= 53) {
            for(p = maxDigits - 1; p >= 0; --p) {
               d = buf.charAt(p) + 1;
               if (d <= 57) {
                  buf.setCharAt(p, (char)d);
                  break;
               }

               buf.setCharAt(p, '0');
            }

            if (p < 0) {
               buf.setCharAt(0, '1');
               ++exp;
            }
         }
      }

      return exp;
   }

   private void appendTrailingZeros(int n) {
      int f = this.lenFraction;
      if (n > f) {
         if (this.lenPoint == 0) {
            this.result.append('.');
            this.lenPoint = 1;
         }

         while(f < n) {
            this.result.append('0');
            ++f;
         }

         this.lenFraction = f;
      }

   }

   private void appendPointAndTrailingZeros(int n) {
      if (this.lenPoint == 0) {
         this.result.append('.');
         this.lenPoint = 1;
      }

      int f;
      for(f = this.lenFraction; f < n; ++f) {
         this.result.append('0');
      }

      this.lenFraction = f;
   }

   private void removeTrailingZeros(int n) {
      if (this.lenPoint > 0) {
         int f = this.lenFraction;
         if (n == 0 || f > n) {
            int fracStart;
            for(fracStart = this.result.length() - f; f > n && this.result.charAt(fracStart - 1 + f) == '0'; --f) {
            }

            if (f == 0 && this.lenPoint > 0) {
               this.lenPoint = this.lenFraction = 0;
               f = -1;
            } else {
               this.lenFraction = f;
            }

            if (fracStart + f < this.result.length()) {
               this.result.setLength(fracStart + f);
            }
         }
      }

   }

   private void appendExponent(int exp) {
      int marker = this.result.length();
      String e;
      if (exp < 0) {
         e = exp <= -10 ? "e-" : "e-0";
         exp = -exp;
      } else {
         e = exp < 10 ? "e+0" : "e+";
      }

      this.result.append(e).append(exp);
      this.lenMarker = 1;
      this.lenExponent = this.result.length() - marker - 1;
   }

   private int indexOfMarker() {
      return this.start + this.lenSign + this.lenWhole + this.lenPoint + this.lenFraction;
   }

   static {
      ROUND_PY = RoundingMode.HALF_EVEN;
   }
}

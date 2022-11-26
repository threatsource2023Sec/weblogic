package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.python.icu.impl.StandardPlural;
import org.python.icu.text.PluralRules;

public class FormatQuantity1 implements FormatQuantity {
   private int lOptPos = Integer.MAX_VALUE;
   private int lReqPos = 0;
   private int rReqPos = 0;
   private int rOptPos = Integer.MIN_VALUE;
   private long primary;
   private int primaryScale;
   private int primaryPrecision;
   private BigDecimal fallback;
   private int flags;
   private static final int NEGATIVE_FLAG = 1;
   private static final int INFINITY_FLAG = 2;
   private static final int NAN_FLAG = 4;
   private static final long[] POWERS_OF_TEN = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
   static final double LOG_2_OF_TEN = 3.32192809489;

   public int maxRepresentableDigits() {
      return Integer.MAX_VALUE;
   }

   public FormatQuantity1(long input) {
      if (input < 0L) {
         this.setNegative(true);
         input *= -1L;
      }

      this.primary = input;
      this.primaryScale = 0;
      this.primaryPrecision = computePrecision(this.primary);
      this.fallback = null;
   }

   public FormatQuantity1(double input) {
      if (input < 0.0) {
         this.setNegative(true);
         input *= -1.0;
      }

      long ieeeBits = Double.doubleToLongBits(input);
      int exponent = (int)((ieeeBits & 9218868437227405312L) >> 52) - 1023;
      if (exponent >= 52 && exponent <= 63) {
         long mantissa = (ieeeBits & 4503599627370495L) + 4503599627370496L;
         this.primary = mantissa << exponent - 52;
         this.primaryScale = 0;
         this.primaryPrecision = computePrecision(this.primary);
      } else {
         String temp = Double.toString(input);

         try {
            if (temp.length() == 3 && temp.equals("0.0")) {
               this.primary = 0L;
               this.primaryScale = 0;
               this.primaryPrecision = 0;
            } else {
               int rightmostNonzeroDigitIndex;
               if (temp.indexOf(69) != -1) {
                  assert temp.indexOf(46) == 1;

                  rightmostNonzeroDigitIndex = temp.indexOf(69);
                  this.primary = Long.parseLong(temp.charAt(0) + temp.substring(2, rightmostNonzeroDigitIndex));
                  this.primaryScale = Integer.parseInt(temp.substring(rightmostNonzeroDigitIndex + 1)) - (rightmostNonzeroDigitIndex - 1) + 1;
                  this.primaryPrecision = rightmostNonzeroDigitIndex - 1;
               } else if (temp.charAt(0) == '0') {
                  assert temp.indexOf(46) == 1;

                  this.primary = Long.parseLong(temp.substring(2));
                  this.primaryScale = 2 - temp.length();
                  this.primaryPrecision = computePrecision(this.primary);
               } else if (temp.charAt(temp.length() - 1) == '0') {
                  assert temp.indexOf(46) == temp.length() - 2;

                  for(rightmostNonzeroDigitIndex = temp.length() - 3; temp.charAt(rightmostNonzeroDigitIndex) == '0'; --rightmostNonzeroDigitIndex) {
                  }

                  this.primary = Long.parseLong(temp.substring(0, rightmostNonzeroDigitIndex + 1));
                  this.primaryScale = temp.length() - rightmostNonzeroDigitIndex - 3;
                  this.primaryPrecision = rightmostNonzeroDigitIndex + 1;
               } else if (temp.equals("Infinity")) {
                  this.primary = 0L;
                  this.setInfinity(true);
               } else if (temp.equals("NaN")) {
                  this.primary = 0L;
                  this.setNaN(true);
               } else {
                  rightmostNonzeroDigitIndex = temp.indexOf(46);
                  this.primary = Long.parseLong(temp.substring(0, rightmostNonzeroDigitIndex) + temp.substring(rightmostNonzeroDigitIndex + 1));
                  this.primaryScale = rightmostNonzeroDigitIndex - temp.length() + 1;
                  this.primaryPrecision = temp.length() - 1;
               }
            }
         } catch (NumberFormatException var10) {
            this.primary = -1L;
            this.fallback = new BigDecimal(temp);
         }

      }
   }

   public FormatQuantity1(double input, boolean fast) {
      if (input < 0.0) {
         this.setNegative(true);
         input *= -1.0;
      }

      long ieeeBits = Double.doubleToLongBits(input);
      int exponent = (int)((ieeeBits & 9218868437227405312L) >> 52) - 1023;
      long mantissa = (ieeeBits & 4503599627370495L) + 4503599627370496L;
      if (exponent > 63) {
         throw new IllegalArgumentException();
      } else if (exponent >= 52) {
         this.primary = mantissa << exponent - 52;
         this.primaryScale = 0;
         this.primaryPrecision = computePrecision(this.primary);
      } else if (exponent < 0) {
         throw new IllegalArgumentException();
      } else {
         int shift = 52 - exponent;
         this.primary = mantissa >> shift;
         int fractionCount = (int)((double)shift / 3.32192809489);
         long fraction = mantissa - (this.primary << shift) + 1L;
         this.primary *= POWERS_OF_TEN[fractionCount];

         for(int i = 0; i < fractionCount; ++i) {
            long times10 = fraction * 10L;
            long digit = times10 >> shift;

            assert digit >= 0L && digit < 10L;

            this.primary += digit * POWERS_OF_TEN[fractionCount - i - 1];
            fraction = times10 & (1L << shift) - 1L;
         }

         this.primaryScale = -fractionCount;
         this.primaryPrecision = computePrecision(this.primary);
      }
   }

   public FormatQuantity1(BigDecimal decimal) {
      if (decimal.compareTo(BigDecimal.ZERO) < 0) {
         this.setNegative(true);
         decimal = decimal.negate();
      }

      this.primary = -1L;
      if (decimal.compareTo(BigDecimal.ZERO) == 0) {
         this.fallback = BigDecimal.ZERO;
      } else {
         this.fallback = decimal;
      }

   }

   public FormatQuantity1(FormatQuantity1 other) {
      this.copyFrom(other);
   }

   public FormatQuantity1 createCopy() {
      return new FormatQuantity1(this);
   }

   public void copyFrom(FormatQuantity other) {
      FormatQuantity1 _other = (FormatQuantity1)other;
      this.lOptPos = _other.lOptPos;
      this.lReqPos = _other.lReqPos;
      this.rReqPos = _other.rReqPos;
      this.rOptPos = _other.rOptPos;
      this.primary = _other.primary;
      this.primaryScale = _other.primaryScale;
      this.primaryPrecision = _other.primaryPrecision;
      this.fallback = _other.fallback;
      this.flags = _other.flags;
   }

   public long getPositionFingerprint() {
      long fingerprint = 0L;
      fingerprint ^= (long)this.lOptPos;
      fingerprint ^= (long)(this.lReqPos << 16);
      fingerprint ^= (long)this.rReqPos << 32;
      fingerprint ^= (long)this.rOptPos << 48;
      return fingerprint;
   }

   private static int computePrecision(long input) {
      int precision;
      for(precision = 0; input > 0L; ++precision) {
         input /= 10L;
      }

      return precision;
   }

   private void convertToBigDecimal() {
      if (this.primary != -1L) {
         this.fallback = (new BigDecimal(this.primary)).scaleByPowerOfTen(this.primaryScale);
         this.primary = -1L;
      }
   }

   public void setIntegerFractionLength(int minInt, int maxInt, int minFrac, int maxFrac) {
      minInt = Math.max(0, minInt);
      maxInt = Math.max(0, maxInt);
      minFrac = Math.max(0, minFrac);
      maxFrac = Math.max(0, maxFrac);
      if (maxInt < minInt) {
         minInt = maxInt;
      }

      if (maxFrac < minFrac) {
         minFrac = maxFrac;
      }

      if (maxInt == 0 && maxFrac == 0) {
         maxInt = Integer.MAX_VALUE;
         maxFrac = Integer.MAX_VALUE;
      }

      this.lOptPos = maxInt;
      this.lReqPos = minInt;
      this.rReqPos = -minFrac;
      this.rOptPos = -maxFrac;
   }

   public void roundToIncrement(BigDecimal roundingInterval, MathContext mathContext) {
      BigDecimal d = this.primary == -1L ? this.fallback : (new BigDecimal(this.primary)).scaleByPowerOfTen(this.primaryScale);
      if (this.isNegative()) {
         d = d.negate();
      }

      d = d.divide(roundingInterval, 0, mathContext.getRoundingMode()).multiply(roundingInterval);
      if (this.isNegative()) {
         d = d.negate();
      }

      this.fallback = d;
      this.primary = -1L;
   }

   public void roundToMagnitude(int roundingMagnitude, MathContext mathContext) {
      if (roundingMagnitude < -1000) {
         this.roundToInfinity();
      } else {
         if (this.primary == -1L) {
            if (this.isNegative()) {
               this.fallback = this.fallback.negate();
            }

            this.fallback = this.fallback.setScale(-roundingMagnitude, mathContext.getRoundingMode());
            if (this.isNegative()) {
               this.fallback = this.fallback.negate();
            }

            this.fallback = this.fallback.round(mathContext);
         } else {
            int relativeScale = this.primaryScale - roundingMagnitude;
            if (relativeScale < -18) {
               this.primary = 0L;
               this.primaryScale = roundingMagnitude;
               this.primaryPrecision = 0;
            } else if (relativeScale < 0 && this.primary % POWERS_OF_TEN[0 - relativeScale] != 0L) {
               BigDecimal temp = (new BigDecimal(this.primary)).scaleByPowerOfTen(this.primaryScale);
               if (this.isNegative()) {
                  temp = temp.negate();
               }

               temp = temp.setScale(-roundingMagnitude, mathContext.getRoundingMode());
               if (this.isNegative()) {
                  temp = temp.negate();
               }

               temp = temp.scaleByPowerOfTen(-roundingMagnitude);
               this.primary = temp.longValueExact();
               this.primaryScale = roundingMagnitude;
               this.primaryPrecision = computePrecision(this.primary);
            }

            this.primary = (new BigDecimal(this.primary)).round(mathContext).longValueExact();
            this.primaryPrecision = computePrecision(this.primary);
         }

      }
   }

   public void roundToInfinity() {
   }

   public void multiplyBy(BigDecimal multiplicand) {
      this.convertToBigDecimal();
      this.fallback = this.fallback.multiply(multiplicand);
      if (this.fallback.compareTo(BigDecimal.ZERO) < 0) {
         this.setNegative(!this.isNegative());
         this.fallback = this.fallback.negate();
      }

   }

   private void divideBy(BigDecimal divisor, int scale, MathContext mathContext) {
      this.convertToBigDecimal();
      this.fallback = this.fallback.divide(divisor, -scale, mathContext.getRoundingMode());
      if (this.fallback.compareTo(BigDecimal.ZERO) < 0) {
         this.setNegative(!this.isNegative());
         this.fallback = this.fallback.negate();
      }

   }

   public boolean isZero() {
      if (this.primary == -1L) {
         return this.fallback.compareTo(BigDecimal.ZERO) == 0;
      } else {
         return this.primary == 0L;
      }
   }

   public int getMagnitude() throws ArithmeticException {
      int scale = this.primary == -1L ? scaleBigDecimal(this.fallback) : this.primaryScale;
      int precision = this.primary == -1L ? precisionBigDecimal(this.fallback) : this.primaryPrecision;
      if (precision == 0) {
         throw new ArithmeticException("Magnitude is not well-defined for zero");
      } else {
         return scale + precision - 1;
      }
   }

   public void adjustMagnitude(int delta) {
      if (this.primary == -1L) {
         this.fallback = this.fallback.scaleByPowerOfTen(delta);
      } else {
         this.primaryScale = addOrMaxValue(this.primaryScale, delta);
      }

   }

   private static int addOrMaxValue(int a, int b) {
      if (b < 0 && a + b > a) {
         return Integer.MIN_VALUE;
      } else {
         return b > 0 && a + b < a ? Integer.MAX_VALUE : a + b;
      }
   }

   public boolean isNegative() {
      return (this.flags & 1) != 0;
   }

   private void setNegative(boolean isNegative) {
      this.flags = this.flags & -2 | (isNegative ? 1 : 0);
   }

   public boolean isInfinite() {
      return (this.flags & 2) != 0;
   }

   private void setInfinity(boolean isInfinity) {
      this.flags = this.flags & -3 | (isInfinity ? 2 : 0);
   }

   public boolean isNaN() {
      return (this.flags & 4) != 0;
   }

   private void setNaN(boolean isNaN) {
      this.flags = this.flags & -5 | (isNaN ? 4 : 0);
   }

   public double toDouble() {
      double result;
      if (this.primary == -1L) {
         result = this.fallback.doubleValue();
      } else {
         result = (double)this.primary;

         int i;
         for(i = 0; i < this.primaryScale; ++i) {
            result *= 10.0;
         }

         for(i = 0; i > this.primaryScale; --i) {
            result /= 10.0;
         }
      }

      return this.isNegative() ? -result : result;
   }

   public BigDecimal toBigDecimal() {
      BigDecimal result;
      if (this.primary != -1L) {
         result = (new BigDecimal(this.primary)).scaleByPowerOfTen(this.primaryScale);
      } else {
         result = this.fallback;
      }

      return this.isNegative() ? result.negate() : result;
   }

   public StandardPlural getStandardPlural(PluralRules rules) {
      if (rules == null) {
         return StandardPlural.OTHER;
      } else {
         String ruleString = rules.select(this.toDouble());
         return StandardPlural.orOtherFromString(ruleString);
      }
   }

   public double getPluralOperand(PluralRules.Operand operand) {
      return (new PluralRules.FixedDecimal(this.toDouble())).getPluralOperand(operand);
   }

   public boolean hasNextFraction() {
      if (this.rReqPos < 0) {
         return true;
      } else if (this.rOptPos >= 0) {
         return false;
      } else if (this.primary == -1L) {
         return this.fallback.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) > 0;
      } else if (this.primaryScale <= -19) {
         return this.primary > 0L;
      } else if (this.primaryScale < 0) {
         long factor = POWERS_OF_TEN[0 - this.primaryScale];
         return this.primary % factor != 0L;
      } else {
         return false;
      }
   }

   public byte nextFraction() {
      byte returnValue;
      if (this.primary == -1L) {
         BigDecimal temp = this.fallback.multiply(BigDecimal.TEN);
         returnValue = temp.setScale(0, RoundingMode.FLOOR).remainder(BigDecimal.TEN).byteValue();
         this.fallback = this.fallback.setScale(0, RoundingMode.FLOOR).add(temp.remainder(BigDecimal.ONE));
      } else if (this.primaryScale <= -20) {
         ++this.primaryScale;
         returnValue = 0;
      } else if (this.primaryScale < 0) {
         long factor = POWERS_OF_TEN[0 - this.primaryScale - 1];
         long temp1 = this.primary / factor;
         long temp2 = this.primary % factor;
         returnValue = (byte)((int)(temp1 % 10L));
         this.primary = temp1 / 10L * factor + temp2;
         ++this.primaryScale;
         if (temp1 != 0L) {
            --this.primaryPrecision;
         }
      } else {
         returnValue = 0;
      }

      if (this.lOptPos < 0) {
         ++this.lOptPos;
      }

      if (this.lReqPos < 0) {
         ++this.lReqPos;
      }

      if (this.rReqPos < 0) {
         ++this.rReqPos;
      }

      if (this.rOptPos < 0) {
         ++this.rOptPos;
      }

      assert returnValue >= 0;

      return returnValue;
   }

   public boolean hasNextInteger() {
      if (this.lReqPos > 0) {
         return true;
      } else if (this.lOptPos <= 0) {
         return false;
      } else if (this.primary == -1L) {
         return this.fallback.setScale(0, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) > 0;
      } else if (this.primaryScale < -18) {
         return false;
      } else if (this.primaryScale < 0) {
         long factor = POWERS_OF_TEN[0 - this.primaryScale];
         return this.primary % factor != this.primary;
      } else {
         return this.primary != 0L;
      }
   }

   private int integerCount() {
      int digitsRemaining;
      if (this.primary == -1L) {
         digitsRemaining = precisionBigDecimal(this.fallback) + scaleBigDecimal(this.fallback);
      } else {
         digitsRemaining = this.primaryPrecision + this.primaryScale;
      }

      return Math.min(Math.max(digitsRemaining, this.lReqPos), this.lOptPos);
   }

   private int fractionCount() {
      FormatQuantity1 copy = new FormatQuantity1(this);

      int fractionCount;
      for(fractionCount = 0; copy.hasNextFraction(); ++fractionCount) {
         copy.nextFraction();
      }

      return fractionCount;
   }

   public int getUpperDisplayMagnitude() {
      return this.integerCount() - 1;
   }

   public int getLowerDisplayMagnitude() {
      return -this.fractionCount();
   }

   public byte getDigit(int magnitude) {
      FormatQuantity1 copy = new FormatQuantity1(this);
      int p;
      if (magnitude < 0) {
         for(p = -1; p > magnitude; --p) {
            copy.nextFraction();
         }

         return copy.nextFraction();
      } else {
         for(p = 0; p < magnitude; ++p) {
            copy.nextInteger();
         }

         return copy.nextInteger();
      }
   }

   public byte nextInteger() {
      byte returnValue;
      if (this.primary == -1L) {
         returnValue = this.fallback.setScale(0, RoundingMode.FLOOR).remainder(BigDecimal.TEN).byteValue();
         BigDecimal temp = this.fallback.divide(BigDecimal.TEN).setScale(0, RoundingMode.FLOOR);
         this.fallback = this.fallback.remainder(BigDecimal.ONE).add(temp);
      } else if (this.primaryScale < -18) {
         returnValue = 0;
      } else if (this.primaryScale < 0) {
         long factor = POWERS_OF_TEN[0 - this.primaryScale];
         if (this.primary % factor != this.primary) {
            returnValue = (byte)((int)(this.primary / factor % 10L));
            long temp = this.primary / 10L;
            this.primary = temp - temp % factor + this.primary % factor;
            --this.primaryPrecision;
         } else {
            returnValue = 0;
         }
      } else if (this.primaryScale == 0) {
         if (this.primary != 0L) {
            returnValue = (byte)((int)(this.primary % 10L));
            this.primary /= 10L;
            --this.primaryPrecision;
         } else {
            returnValue = 0;
         }
      } else {
         --this.primaryScale;
         returnValue = 0;
      }

      if (this.lOptPos > 0) {
         --this.lOptPos;
      }

      if (this.lReqPos > 0) {
         --this.lReqPos;
      }

      if (this.rReqPos > 0) {
         --this.rReqPos;
      }

      if (this.rOptPos > 0) {
         --this.rOptPos;
      }

      assert returnValue >= 0;

      return returnValue;
   }

   private static int precisionBigDecimal(BigDecimal decimal) {
      return decimal.compareTo(BigDecimal.ZERO) == 0 ? 0 : decimal.precision();
   }

   private static int scaleBigDecimal(BigDecimal decimal) {
      return -decimal.scale();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("<FormatQuantity1 ");
      if (this.primary == -1L) {
         sb.append(this.lOptPos > 1000 ? "max" : this.lOptPos);
         sb.append(":");
         sb.append(this.lReqPos);
         sb.append(":");
         sb.append(this.rReqPos);
         sb.append(":");
         sb.append(this.rOptPos < -1000 ? "min" : this.rOptPos);
         sb.append(" ");
         sb.append(this.fallback.toString());
      } else {
         String digits = Long.toString(this.primary);
         int iDec = digits.length() + this.primaryScale;
         int iLP = iDec - toRange(this.lOptPos, -1000, 1000);
         int iLB = iDec - toRange(this.lReqPos, -1000, 1000);
         int iRB = iDec - toRange(this.rReqPos, -1000, 1000);
         int iRP = iDec - toRange(this.rOptPos, -1000, 1000);
         iDec = Math.max(Math.min(iDec, digits.length() + 1), -1);
         iLP = Math.max(Math.min(iLP, digits.length() + 1), -1);
         iLB = Math.max(Math.min(iLB, digits.length() + 1), -1);
         iRB = Math.max(Math.min(iRB, digits.length() + 1), -1);
         iRP = Math.max(Math.min(iRP, digits.length() + 1), -1);

         for(int i = -1; i <= digits.length() + 1; ++i) {
            if (i == iLP) {
               sb.append('(');
            }

            if (i == iLB) {
               sb.append('[');
            }

            if (i == iDec) {
               sb.append('.');
            }

            if (i == iRB) {
               sb.append(']');
            }

            if (i == iRP) {
               sb.append(')');
            }

            if (i >= 0 && i < digits.length()) {
               sb.append(digits.charAt(i));
            } else {
               sb.append(' ');
            }
         }
      }

      sb.append(">");
      return sb.toString();
   }

   private static int toRange(int i, int lo, int hi) {
      if (i < lo) {
         return lo;
      } else {
         return i > hi ? hi : i;
      }
   }
}

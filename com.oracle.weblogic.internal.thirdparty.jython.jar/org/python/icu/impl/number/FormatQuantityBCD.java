package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.FieldPosition;
import org.python.icu.impl.StandardPlural;
import org.python.icu.text.PluralRules;
import org.python.icu.text.UFieldPosition;

public abstract class FormatQuantityBCD implements FormatQuantity {
   protected int scale;
   protected int precision;
   protected int flags;
   protected static final int NEGATIVE_FLAG = 1;
   protected static final int INFINITY_FLAG = 2;
   protected static final int NAN_FLAG = 4;
   protected double origDouble;
   protected int origDelta;
   protected boolean isApproximate;
   protected int lOptPos = Integer.MAX_VALUE;
   protected int lReqPos = 0;
   protected int rReqPos = 0;
   protected int rOptPos = Integer.MIN_VALUE;
   private static final double[] DOUBLE_MULTIPLIERS = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21};
   /** @deprecated */
   @Deprecated
   public boolean explicitExactDouble = false;

   public void copyFrom(FormatQuantity _other) {
      this.copyBcdFrom(_other);
      FormatQuantityBCD other = (FormatQuantityBCD)_other;
      this.lOptPos = other.lOptPos;
      this.lReqPos = other.lReqPos;
      this.rReqPos = other.rReqPos;
      this.rOptPos = other.rOptPos;
      this.scale = other.scale;
      this.precision = other.precision;
      this.flags = other.flags;
      this.origDouble = other.origDouble;
      this.origDelta = other.origDelta;
      this.isApproximate = other.isApproximate;
   }

   public FormatQuantityBCD clear() {
      this.lOptPos = Integer.MAX_VALUE;
      this.lReqPos = 0;
      this.rReqPos = 0;
      this.rOptPos = Integer.MIN_VALUE;
      this.flags = 0;
      this.setBcdToZero();
      return this;
   }

   public void setIntegerFractionLength(int minInt, int maxInt, int minFrac, int maxFrac) {
      assert minInt >= 0;

      assert maxInt >= minInt;

      assert minFrac >= 0;

      assert maxFrac >= minFrac;

      this.lOptPos = maxInt;
      this.lReqPos = minInt;
      this.rReqPos = -minFrac;
      this.rOptPos = -maxFrac;
   }

   public long getPositionFingerprint() {
      long fingerprint = 0L;
      fingerprint ^= (long)this.lOptPos;
      fingerprint ^= (long)(this.lReqPos << 16);
      fingerprint ^= (long)this.rReqPos << 32;
      fingerprint ^= (long)this.rOptPos << 48;
      return fingerprint;
   }

   public void roundToIncrement(BigDecimal roundingInterval, MathContext mathContext) {
      BigDecimal temp = this.toBigDecimal();
      temp = temp.divide(roundingInterval, 0, mathContext.getRoundingMode()).multiply(roundingInterval).round(mathContext);
      if (temp.signum() == 0) {
         this.setBcdToZero();
      } else {
         this.setToBigDecimal(temp);
      }

   }

   public void multiplyBy(BigDecimal multiplicand) {
      BigDecimal temp = this.toBigDecimal();
      temp = temp.multiply(multiplicand);
      this.setToBigDecimal(temp);
   }

   public int getMagnitude() throws ArithmeticException {
      if (this.precision == 0) {
         throw new ArithmeticException("Magnitude is not well-defined for zero");
      } else {
         return this.scale + this.precision - 1;
      }
   }

   public void adjustMagnitude(int delta) {
      if (this.precision != 0) {
         this.scale += delta;
         this.origDelta += delta;
      }

   }

   public StandardPlural getStandardPlural(PluralRules rules) {
      if (rules == null) {
         return StandardPlural.OTHER;
      } else {
         String ruleString = rules.select(this);
         return StandardPlural.orOtherFromString(ruleString);
      }
   }

   public double getPluralOperand(PluralRules.Operand operand) {
      assert !this.isApproximate;

      switch (operand) {
         case i:
            return (double)this.toLong();
         case f:
            return (double)this.toFractionLong(true);
         case t:
            return (double)this.toFractionLong(false);
         case v:
            return (double)this.fractionCount();
         case w:
            return (double)this.fractionCountWithoutTrailingZeros();
         default:
            return Math.abs(this.toDouble());
      }
   }

   public void populateUFieldPosition(FieldPosition fp) {
      if (fp instanceof UFieldPosition) {
         ((UFieldPosition)fp).setFractionDigits((int)this.getPluralOperand(PluralRules.Operand.v), (long)this.getPluralOperand(PluralRules.Operand.f));
      }

   }

   public int getUpperDisplayMagnitude() {
      assert !this.isApproximate;

      int magnitude = this.scale + this.precision;
      int result = this.lReqPos > magnitude ? this.lReqPos : (this.lOptPos < magnitude ? this.lOptPos : magnitude);
      return result - 1;
   }

   public int getLowerDisplayMagnitude() {
      assert !this.isApproximate;

      int magnitude = this.scale;
      int result = this.rReqPos < magnitude ? this.rReqPos : (this.rOptPos > magnitude ? this.rOptPos : magnitude);
      return result;
   }

   public byte getDigit(int magnitude) {
      assert !this.isApproximate;

      return this.getDigitPos(magnitude - this.scale);
   }

   private int fractionCount() {
      return -this.getLowerDisplayMagnitude();
   }

   private int fractionCountWithoutTrailingZeros() {
      return Math.max(-this.scale, 0);
   }

   public boolean isNegative() {
      return (this.flags & 1) != 0;
   }

   public boolean isInfinite() {
      return (this.flags & 2) != 0;
   }

   public boolean isNaN() {
      return (this.flags & 4) != 0;
   }

   public boolean isZero() {
      return this.precision == 0;
   }

   public FormatQuantity createCopy() {
      if (this instanceof FormatQuantity2) {
         return new FormatQuantity2((FormatQuantity2)this);
      } else if (this instanceof FormatQuantity3) {
         return new FormatQuantity3((FormatQuantity3)this);
      } else if (this instanceof FormatQuantity4) {
         return new FormatQuantity4((FormatQuantity4)this);
      } else {
         throw new IllegalArgumentException("Don't know how to copy " + this.getClass());
      }
   }

   public void setToInt(int n) {
      this.setBcdToZero();
      this.flags = 0;
      if (n < 0) {
         this.flags |= 1;
         n = -n;
      }

      if (n != 0) {
         this._setToInt(n);
         this.compact();
      }

   }

   private void _setToInt(int n) {
      if (n == Integer.MIN_VALUE) {
         this.readLongToBcd(-((long)n));
      } else {
         this.readIntToBcd(n);
      }

   }

   public void setToLong(long n) {
      this.setBcdToZero();
      this.flags = 0;
      if (n < 0L) {
         this.flags |= 1;
         n = -n;
      }

      if (n != 0L) {
         this._setToLong(n);
         this.compact();
      }

   }

   private void _setToLong(long n) {
      if (n == Long.MIN_VALUE) {
         this.readBigIntegerToBcd(BigInteger.valueOf(n).negate());
      } else if (n <= 2147483647L) {
         this.readIntToBcd((int)n);
      } else {
         this.readLongToBcd(n);
      }

   }

   public void setToBigInteger(BigInteger n) {
      this.setBcdToZero();
      this.flags = 0;
      if (n.signum() == -1) {
         this.flags |= 1;
         n = n.negate();
      }

      if (n.signum() != 0) {
         this._setToBigInteger(n);
         this.compact();
      }

   }

   private void _setToBigInteger(BigInteger n) {
      if (n.bitLength() < 32) {
         this.readIntToBcd(n.intValue());
      } else if (n.bitLength() < 64) {
         this.readLongToBcd(n.longValue());
      } else {
         this.readBigIntegerToBcd(n);
      }

   }

   public void setToDouble(double n) {
      this.setBcdToZero();
      this.flags = 0;
      if (Double.compare(n, 0.0) < 0) {
         this.flags |= 1;
         n = -n;
      }

      if (Double.isNaN(n)) {
         this.flags |= 4;
      } else if (Double.isInfinite(n)) {
         this.flags |= 2;
      } else if (n != 0.0) {
         this._setToDoubleFast(n);
         this.compact();
      }

   }

   private void _setToDoubleFast(double n) {
      long ieeeBits = Double.doubleToLongBits(n);
      int exponent = (int)((ieeeBits & 9218868437227405312L) >> 52) - 1023;
      if (exponent <= 52 && (double)((long)n) == n) {
         this._setToLong((long)n);
      } else {
         this.isApproximate = true;
         this.origDouble = n;
         this.origDelta = 0;
         int fracLength = (int)((double)(52 - exponent) / 3.32192809489);
         int i;
         if (fracLength >= 0) {
            for(i = fracLength; i >= 22; i -= 22) {
               n *= 1.0E22;
            }

            n *= DOUBLE_MULTIPLIERS[i];
         } else {
            for(i = fracLength; i <= -22; i += 22) {
               n /= 1.0E22;
            }

            n /= DOUBLE_MULTIPLIERS[-i];
         }

         long result = Math.round(n);
         if (result != 0L) {
            this._setToLong(result);
            this.scale -= fracLength;
         }

      }
   }

   private void convertToAccurateDouble() {
      double n = this.origDouble;

      assert n != 0.0;

      int delta = this.origDelta;
      this.setBcdToZero();
      String temp = Double.toString(n);
      int decimalPos;
      if (temp.indexOf(69) != -1) {
         assert temp.indexOf(46) == 1;

         decimalPos = temp.indexOf(69);
         this._setToLong(Long.parseLong(temp.charAt(0) + temp.substring(2, decimalPos)));
         this.scale += Integer.parseInt(temp.substring(decimalPos + 1)) - (decimalPos - 1) + 1;
      } else if (temp.charAt(0) == '0') {
         assert temp.indexOf(46) == 1;

         this._setToLong(Long.parseLong(temp.substring(2)));
         this.scale += 2 - temp.length();
      } else if (temp.charAt(temp.length() - 1) == '0') {
         assert temp.indexOf(46) == temp.length() - 2;

         assert temp.length() - 2 <= 18;

         this._setToLong(Long.parseLong(temp.substring(0, temp.length() - 2)));
      } else {
         decimalPos = temp.indexOf(46);
         this._setToLong(Long.parseLong(temp.substring(0, decimalPos) + temp.substring(decimalPos + 1)));
         this.scale += decimalPos - temp.length() + 1;
      }

      this.scale += delta;
      this.compact();
      this.explicitExactDouble = true;
   }

   public void setToBigDecimal(BigDecimal n) {
      this.setBcdToZero();
      this.flags = 0;
      if (n.signum() == -1) {
         this.flags |= 1;
         n = n.negate();
      }

      if (n.signum() != 0) {
         this._setToBigDecimal(n);
         this.compact();
      }

   }

   private void _setToBigDecimal(BigDecimal n) {
      int fracLength = n.scale();
      n = n.scaleByPowerOfTen(fracLength);
      BigInteger bi = n.toBigInteger();
      this._setToBigInteger(bi);
      this.scale -= fracLength;
   }

   protected long toLong() {
      long result = 0L;

      for(int magnitude = this.scale + this.precision - 1; magnitude >= 0; --magnitude) {
         result = result * 10L + (long)this.getDigitPos(magnitude - this.scale);
      }

      return result;
   }

   protected long toFractionLong(boolean includeTrailingZeros) {
      long result = 0L;

      for(int magnitude = -1; (magnitude >= this.scale || includeTrailingZeros && magnitude >= this.rReqPos) && magnitude >= this.rOptPos; --magnitude) {
         result = result * 10L + (long)this.getDigitPos(magnitude - this.scale);
      }

      return result;
   }

   public double toDouble() {
      if (this.isApproximate) {
         return this.toDoubleFromOriginal();
      } else if (this.isNaN()) {
         return Double.NaN;
      } else if (this.isInfinite()) {
         return this.isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
      } else {
         long tempLong = 0L;
         int lostDigits = this.precision - Math.min(this.precision, 17);

         for(int shift = this.precision - 1; shift >= lostDigits; --shift) {
            tempLong = tempLong * 10L + (long)this.getDigitPos(shift);
         }

         double result = (double)tempLong;
         int _scale = this.scale + lostDigits;
         int i;
         if (_scale >= 0) {
            for(i = _scale; i >= 22; i -= 22) {
               result *= 1.0E22;
            }

            result *= DOUBLE_MULTIPLIERS[i];
         } else {
            for(i = _scale; i <= -22; i += 22) {
               result /= 1.0E22;
            }

            result /= DOUBLE_MULTIPLIERS[-i];
         }

         if (this.isNegative()) {
            result = -result;
         }

         return result;
      }
   }

   public BigDecimal toBigDecimal() {
      if (this.isApproximate) {
         this.convertToAccurateDouble();
      }

      return this.bcdToBigDecimal();
   }

   protected double toDoubleFromOriginal() {
      double result = this.origDouble;
      int delta = this.origDelta;
      if (delta >= 0) {
         while(true) {
            if (delta < 22) {
               result *= DOUBLE_MULTIPLIERS[delta];
               break;
            }

            result *= 1.0E22;
            delta -= 22;
         }
      } else {
         while(delta <= -22) {
            result /= 1.0E22;
            delta += 22;
         }

         result /= DOUBLE_MULTIPLIERS[-delta];
      }

      if (this.isNegative()) {
         result *= -1.0;
      }

      return result;
   }

   private static int safeSubtract(int a, int b) {
      int diff = a - b;
      if (b < 0 && diff < a) {
         return Integer.MAX_VALUE;
      } else {
         return b > 0 && diff > a ? Integer.MIN_VALUE : diff;
      }
   }

   public void roundToMagnitude(int magnitude, MathContext mathContext) {
      int position = safeSubtract(magnitude, this.scale);
      int _mcPrecision = mathContext.getPrecision();
      if (magnitude == Integer.MAX_VALUE || _mcPrecision > 0 && this.precision - position > _mcPrecision) {
         position = this.precision - _mcPrecision;
      }

      if ((position > 0 || this.isApproximate) && this.precision != 0) {
         byte leadingDigit = this.getDigitPos(safeSubtract(position, 1));
         byte trailingDigit = this.getDigitPos(position);
         int section = 2;
         int p;
         int bubblePos;
         if (!this.isApproximate) {
            if (leadingDigit < 5) {
               section = 1;
            } else if (leadingDigit > 5) {
               section = 3;
            } else {
               for(p = safeSubtract(position, 2); p >= 0; --p) {
                  if (this.getDigitPos(p) != 0) {
                     section = 3;
                     break;
                  }
               }
            }
         } else {
            p = safeSubtract(position, 2);
            bubblePos = Math.max(0, this.precision - 14);
            if (leadingDigit == 0) {
               for(section = -1; p >= bubblePos; --p) {
                  if (this.getDigitPos(p) != 0) {
                     section = 1;
                     break;
                  }
               }
            } else if (leadingDigit == 4) {
               while(p >= bubblePos) {
                  if (this.getDigitPos(p) != 9) {
                     section = 1;
                     break;
                  }

                  --p;
               }
            } else if (leadingDigit == 5) {
               while(p >= bubblePos) {
                  if (this.getDigitPos(p) != 0) {
                     section = 3;
                     break;
                  }

                  --p;
               }
            } else if (leadingDigit == 9) {
               for(section = -2; p >= bubblePos; --p) {
                  if (this.getDigitPos(p) != 9) {
                     section = 3;
                     break;
                  }
               }
            } else if (leadingDigit < 5) {
               section = 1;
            } else {
               section = 3;
            }

            boolean roundsAtMidpoint = RoundingUtils.roundsAtMidpoint(mathContext.getRoundingMode().ordinal());
            if (safeSubtract(position, 1) < this.precision - 14 || roundsAtMidpoint && section == 2 || !roundsAtMidpoint && section < 0) {
               this.convertToAccurateDouble();
               this.roundToMagnitude(magnitude, mathContext);
               return;
            }

            this.isApproximate = false;
            this.origDouble = 0.0;
            this.origDelta = 0;
            if (position <= 0) {
               return;
            }

            if (section == -1) {
               section = 1;
            }

            if (section == -2) {
               section = 3;
            }
         }

         boolean roundDown = RoundingUtils.getRoundingDirection(trailingDigit % 2 == 0, this.isNegative(), section, mathContext.getRoundingMode().ordinal(), this);
         if (position >= this.precision) {
            this.setBcdToZero();
            this.scale = magnitude;
         } else {
            this.shiftRight(position);
         }

         if (!roundDown) {
            if (trailingDigit == 9) {
               for(bubblePos = 0; this.getDigitPos(bubblePos) == 9; ++bubblePos) {
               }

               this.shiftRight(bubblePos);
            }

            byte digit0 = this.getDigitPos(0);

            assert digit0 != 9;

            this.setDigitPos(0, (byte)(digit0 + 1));
            ++this.precision;
         }

         this.compact();
      }

   }

   public void roundToInfinity() {
      if (this.isApproximate) {
         this.convertToAccurateDouble();
      }

   }

   /** @deprecated */
   @Deprecated
   public void appendDigit(byte value, int leadingZeros, boolean appendAsInteger) {
      assert leadingZeros >= 0;

      if (value == 0) {
         if (appendAsInteger && this.precision != 0) {
            this.scale += leadingZeros + 1;
         }

      } else {
         if (this.scale > 0) {
            leadingZeros += this.scale;
            if (appendAsInteger) {
               this.scale = 0;
            }
         }

         this.shiftLeft(leadingZeros + 1);
         this.setDigitPos(0, value);
         if (appendAsInteger) {
            this.scale += leadingZeros + 1;
         }

      }
   }

   protected abstract byte getDigitPos(int var1);

   protected abstract void setDigitPos(int var1, byte var2);

   protected abstract void shiftLeft(int var1);

   protected abstract void shiftRight(int var1);

   protected abstract void setBcdToZero();

   protected abstract void readIntToBcd(int var1);

   protected abstract void readLongToBcd(long var1);

   protected abstract void readBigIntegerToBcd(BigInteger var1);

   protected abstract BigDecimal bcdToBigDecimal();

   protected abstract void copyBcdFrom(FormatQuantity var1);

   protected abstract void compact();
}

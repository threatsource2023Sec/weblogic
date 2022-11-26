package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class FormatQuantity2 extends FormatQuantityBCD {
   private long bcd;

   public int maxRepresentableDigits() {
      return 16;
   }

   public FormatQuantity2(long input) {
      this.setToLong(input);
   }

   public FormatQuantity2(int input) {
      this.setToInt(input);
   }

   public FormatQuantity2(double input) {
      this.setToDouble(input);
   }

   public FormatQuantity2(BigInteger input) {
      this.setToBigInteger(input);
   }

   public FormatQuantity2(BigDecimal input) {
      this.setToBigDecimal(input);
   }

   public FormatQuantity2(FormatQuantity2 other) {
      this.copyFrom(other);
   }

   protected byte getDigitPos(int position) {
      return position >= 0 && position < 16 ? (byte)((int)(this.bcd >>> position * 4 & 15L)) : 0;
   }

   protected void setDigitPos(int position, byte value) {
      assert position >= 0 && position < 16;

      int shift = position * 4;
      this.bcd = this.bcd & ~(15L << shift) | (long)value << shift;
   }

   protected void shiftLeft(int numDigits) {
      assert this.precision + numDigits <= 16;

      this.bcd <<= numDigits * 4;
      this.scale -= numDigits;
      this.precision += numDigits;
   }

   protected void shiftRight(int numDigits) {
      this.bcd >>>= numDigits * 4;
      this.scale += numDigits;
      this.precision -= numDigits;
   }

   protected void setBcdToZero() {
      this.bcd = 0L;
      this.scale = 0;
      this.precision = 0;
      this.isApproximate = false;
      this.origDouble = 0.0;
      this.origDelta = 0;
   }

   protected void readIntToBcd(int n) {
      assert n != 0;

      long result = 0L;

      int i;
      for(i = 16; n != 0; --i) {
         result = (result >>> 4) + ((long)n % 10L << 60);
         n /= 10;
      }

      this.bcd = result >>> i * 4;
      this.scale = 0;
      this.precision = 16 - i;
   }

   protected void readLongToBcd(long n) {
      assert n != 0L;

      long result = 0L;

      int i;
      for(i = 16; n != 0L; --i) {
         result = (result >>> 4) + (n % 10L << 60);
         n /= 10L;
      }

      int adjustment = i > 0 ? i : 0;
      this.bcd = result >>> adjustment * 4;
      this.scale = i < 0 ? -i : 0;
      this.precision = 16 - i;
   }

   protected void readBigIntegerToBcd(BigInteger n) {
      assert n.signum() != 0;

      long result = 0L;

      int i;
      for(i = 16; n.signum() != 0; --i) {
         BigInteger[] temp = n.divideAndRemainder(BigInteger.TEN);
         result = (result >>> 4) + (temp[1].longValue() << 60);
         n = temp[0];
      }

      int adjustment = i > 0 ? i : 0;
      this.bcd = result >>> adjustment * 4;
      this.scale = i < 0 ? -i : 0;
   }

   protected BigDecimal bcdToBigDecimal() {
      long tempLong = 0L;

      for(int shift = this.precision - 1; shift >= 0; --shift) {
         tempLong = tempLong * 10L + (long)this.getDigitPos(shift);
      }

      BigDecimal result = BigDecimal.valueOf(tempLong);
      result = result.scaleByPowerOfTen(this.scale);
      if (this.isNegative()) {
         result = result.negate();
      }

      return result;
   }

   protected void compact() {
      if (this.bcd == 0L) {
         this.scale = 0;
         this.precision = 0;
      } else {
         int delta = Long.numberOfTrailingZeros(this.bcd) / 4;
         this.bcd >>>= delta * 4;
         this.scale += delta;
         this.precision = 16 - Long.numberOfLeadingZeros(this.bcd) / 4;
      }
   }

   protected void copyBcdFrom(FormatQuantity _other) {
      FormatQuantity2 other = (FormatQuantity2)_other;
      this.bcd = other.bcd;
   }

   public String toString() {
      return String.format("<FormatQuantity2 %s:%d:%d:%s %016XE%d>", this.lOptPos > 1000 ? "max" : String.valueOf(this.lOptPos), this.lReqPos, this.rReqPos, this.rOptPos < -1000 ? "min" : String.valueOf(this.rOptPos), this.bcd, this.scale);
   }
}

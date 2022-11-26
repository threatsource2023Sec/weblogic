package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class FormatQuantity3 extends FormatQuantityBCD {
   private byte[] bcd = new byte[100];
   private static final byte[] LONG_MIN_VALUE = new byte[]{8, 0, 8, 5, 7, 7, 4, 5, 8, 6, 3, 0, 2, 7, 3, 3, 2, 2, 9};

   public int maxRepresentableDigits() {
      return Integer.MAX_VALUE;
   }

   public FormatQuantity3(long input) {
      this.setToLong(input);
   }

   public FormatQuantity3(int input) {
      this.setToInt(input);
   }

   public FormatQuantity3(double input) {
      this.setToDouble(input);
   }

   public FormatQuantity3(BigInteger input) {
      this.setToBigInteger(input);
   }

   public FormatQuantity3(BigDecimal input) {
      this.setToBigDecimal(input);
   }

   public FormatQuantity3(FormatQuantity3 other) {
      this.copyFrom(other);
   }

   protected byte getDigitPos(int position) {
      return position >= 0 && position <= this.precision ? this.bcd[position] : 0;
   }

   protected void setDigitPos(int position, byte value) {
      assert position >= 0;

      this.ensureCapacity(position + 1);
      this.bcd[position] = value;
   }

   protected void shiftLeft(int numDigits) {
      this.ensureCapacity(this.precision + numDigits);

      int i;
      for(i = this.precision + numDigits - 1; i >= numDigits; --i) {
         this.bcd[i] = this.bcd[i - numDigits];
      }

      while(i >= 0) {
         this.bcd[i] = 0;
         --i;
      }

      this.scale -= numDigits;
      this.precision += numDigits;
   }

   protected void shiftRight(int numDigits) {
      int i;
      for(i = 0; i < this.precision - numDigits; ++i) {
         this.bcd[i] = this.bcd[i + numDigits];
      }

      while(i < this.precision) {
         this.bcd[i] = 0;
         ++i;
      }

      this.scale += numDigits;
      this.precision -= numDigits;
   }

   protected void setBcdToZero() {
      for(int i = 0; i < this.precision; ++i) {
         this.bcd[i] = 0;
      }

      this.scale = 0;
      this.precision = 0;
      this.isApproximate = false;
      this.origDouble = 0.0;
      this.origDelta = 0;
   }

   protected void readIntToBcd(int n) {
      assert n != 0;

      int i;
      for(i = 0; (long)n != 0L; ++i) {
         this.bcd[i] = (byte)(n % 10);
         n = (int)((long)n / 10L);
      }

      this.scale = 0;
      this.precision = i;
   }

   protected void readLongToBcd(long n) {
      assert n != 0L;

      if (n == Long.MIN_VALUE) {
         System.arraycopy(LONG_MIN_VALUE, 0, this.bcd, 0, LONG_MIN_VALUE.length);
         this.scale = 0;
         this.precision = LONG_MIN_VALUE.length;
      } else {
         int i;
         for(i = 0; n != 0L; ++i) {
            this.bcd[i] = (byte)((int)(n % 10L));
            n /= 10L;
         }

         this.scale = 0;
         this.precision = i;
      }
   }

   protected void readBigIntegerToBcd(BigInteger n) {
      assert n.signum() != 0;

      int i;
      for(i = 0; n.signum() != 0; ++i) {
         BigInteger[] temp = n.divideAndRemainder(BigInteger.TEN);
         this.ensureCapacity(i + 1);
         this.bcd[i] = temp[1].byteValue();
         n = temp[0];
      }

      this.scale = 0;
      this.precision = i;
   }

   protected BigDecimal bcdToBigDecimal() {
      return new BigDecimal(this.toDumbString());
   }

   private String toDumbString() {
      StringBuilder sb = new StringBuilder();
      if (this.isNegative()) {
         sb.append('-');
      }

      if (this.precision == 0) {
         sb.append('0');
         return sb.toString();
      } else {
         for(int i = this.precision - 1; i >= 0; --i) {
            sb.append(this.getDigitPos(i));
         }

         if (this.scale != 0) {
            sb.append('E');
            sb.append(this.scale);
         }

         return sb.toString();
      }
   }

   protected void compact() {
      boolean isZero = true;

      int delta;
      for(delta = 0; delta < this.precision; ++delta) {
         if (this.bcd[delta] != 0) {
            isZero = false;
            break;
         }
      }

      if (isZero) {
         this.scale = 0;
         this.precision = 0;
      } else {
         for(delta = 0; this.bcd[delta] == 0; ++delta) {
         }

         this.shiftRight(delta);

         int leading;
         for(leading = this.precision - 1; leading >= 0 && this.bcd[leading] == 0; --leading) {
         }

         this.precision = leading + 1;
      }
   }

   private void ensureCapacity(int capacity) {
      if (this.bcd.length < capacity) {
         byte[] bcd1 = new byte[capacity * 2];
         System.arraycopy(this.bcd, 0, bcd1, 0, this.bcd.length);
         this.bcd = bcd1;
      }
   }

   protected void copyBcdFrom(FormatQuantity _other) {
      FormatQuantity3 other = (FormatQuantity3)_other;
      System.arraycopy(other.bcd, 0, this.bcd, 0, this.bcd.length);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 30; i >= 0; --i) {
         sb.append(this.bcd[i]);
      }

      return String.format("<FormatQuantity3 %s:%d:%d:%s %s%s%d>", this.lOptPos > 1000 ? "max" : String.valueOf(this.lOptPos), this.lReqPos, this.rReqPos, this.rOptPos < -1000 ? "min" : String.valueOf(this.rOptPos), sb, "E", this.scale);
   }
}

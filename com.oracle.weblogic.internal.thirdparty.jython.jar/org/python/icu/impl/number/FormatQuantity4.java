package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class FormatQuantity4 extends FormatQuantityBCD {
   private byte[] bcdBytes;
   private long bcdLong = 0L;
   private boolean usingBytes = false;

   public int maxRepresentableDigits() {
      return Integer.MAX_VALUE;
   }

   public FormatQuantity4() {
      this.setBcdToZero();
   }

   public FormatQuantity4(long input) {
      this.setToLong(input);
   }

   public FormatQuantity4(int input) {
      this.setToInt(input);
   }

   public FormatQuantity4(double input) {
      this.setToDouble(input);
   }

   public FormatQuantity4(BigInteger input) {
      this.setToBigInteger(input);
   }

   public FormatQuantity4(BigDecimal input) {
      this.setToBigDecimal(input);
   }

   public FormatQuantity4(FormatQuantity4 other) {
      this.copyFrom(other);
   }

   public FormatQuantity4(Number number) {
      if (number instanceof Long) {
         this.setToLong(number.longValue());
      } else if (number instanceof Integer) {
         this.setToInt(number.intValue());
      } else if (number instanceof Double) {
         this.setToDouble(number.doubleValue());
      } else if (number instanceof BigInteger) {
         this.setToBigInteger((BigInteger)number);
      } else if (number instanceof BigDecimal) {
         this.setToBigDecimal((BigDecimal)number);
      } else {
         if (!(number instanceof org.python.icu.math.BigDecimal)) {
            throw new IllegalArgumentException("Number is of an unsupported type: " + number.getClass().getName());
         }

         this.setToBigDecimal(((org.python.icu.math.BigDecimal)number).toBigDecimal());
      }

   }

   protected byte getDigitPos(int position) {
      if (this.usingBytes) {
         return position >= 0 && position <= this.precision ? this.bcdBytes[position] : 0;
      } else {
         return position >= 0 && position < 16 ? (byte)((int)(this.bcdLong >>> position * 4 & 15L)) : 0;
      }
   }

   protected void setDigitPos(int position, byte value) {
      assert position >= 0;

      if (this.usingBytes) {
         this.ensureCapacity(position + 1);
         this.bcdBytes[position] = value;
      } else if (position >= 16) {
         this.switchStorage();
         this.ensureCapacity(position + 1);
         this.bcdBytes[position] = value;
      } else {
         int shift = position * 4;
         this.bcdLong = this.bcdLong & ~(15L << shift) | (long)value << shift;
      }

   }

   protected void shiftLeft(int numDigits) {
      if (!this.usingBytes && this.precision + numDigits > 16) {
         this.switchStorage();
      }

      if (this.usingBytes) {
         this.ensureCapacity(this.precision + numDigits);

         int i;
         for(i = this.precision + numDigits - 1; i >= numDigits; --i) {
            this.bcdBytes[i] = this.bcdBytes[i - numDigits];
         }

         while(i >= 0) {
            this.bcdBytes[i] = 0;
            --i;
         }
      } else {
         this.bcdLong <<= numDigits * 4;
      }

      this.scale -= numDigits;
      this.precision += numDigits;
   }

   protected void shiftRight(int numDigits) {
      if (this.usingBytes) {
         int i;
         for(i = 0; i < this.precision - numDigits; ++i) {
            this.bcdBytes[i] = this.bcdBytes[i + numDigits];
         }

         while(i < this.precision) {
            this.bcdBytes[i] = 0;
            ++i;
         }
      } else {
         this.bcdLong >>>= numDigits * 4;
      }

      this.scale += numDigits;
      this.precision -= numDigits;
   }

   protected void setBcdToZero() {
      if (this.usingBytes) {
         for(int i = 0; i < this.precision; ++i) {
            this.bcdBytes[i] = 0;
         }
      }

      this.usingBytes = false;
      this.bcdLong = 0L;
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

      this.usingBytes = false;
      this.bcdLong = result >>> i * 4;
      this.scale = 0;
      this.precision = 16 - i;
   }

   protected void readLongToBcd(long n) {
      assert n != 0L;

      if (n >= 10000000000000000L) {
         this.ensureCapacity();

         int i;
         for(i = 0; n != 0L; ++i) {
            this.bcdBytes[i] = (byte)((int)(n % 10L));
            n /= 10L;
         }

         this.usingBytes = true;
         this.scale = 0;
         this.precision = i;
      } else {
         long result = 0L;

         int i;
         for(i = 16; n != 0L; --i) {
            result = (result >>> 4) + (n % 10L << 60);
            n /= 10L;
         }

         assert i >= 0;

         this.usingBytes = false;
         this.bcdLong = result >>> i * 4;
         this.scale = 0;
         this.precision = 16 - i;
      }

   }

   protected void readBigIntegerToBcd(BigInteger n) {
      assert n.signum() != 0;

      this.ensureCapacity();

      int i;
      for(i = 0; n.signum() != 0; ++i) {
         BigInteger[] temp = n.divideAndRemainder(BigInteger.TEN);
         this.ensureCapacity(i + 1);
         this.bcdBytes[i] = temp[1].byteValue();
         n = temp[0];
      }

      this.usingBytes = true;
      this.scale = 0;
      this.precision = i;
   }

   protected BigDecimal bcdToBigDecimal() {
      if (this.usingBytes) {
         StringBuilder sb = new StringBuilder();
         if (this.isNegative()) {
            sb.append('-');
         }

         assert this.precision > 0;

         for(int i = this.precision - 1; i >= 0; --i) {
            sb.append(this.getDigitPos(i));
         }

         if (this.scale != 0) {
            sb.append('E');
            sb.append(this.scale);
         }

         return new BigDecimal(sb.toString());
      } else {
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
   }

   protected void compact() {
      int delta;
      if (this.usingBytes) {
         for(delta = 0; delta < this.precision && this.bcdBytes[delta] == 0; ++delta) {
         }

         if (delta == this.precision) {
            this.setBcdToZero();
            return;
         }

         this.shiftRight(delta);

         int leading;
         for(leading = this.precision - 1; leading >= 0 && this.bcdBytes[leading] == 0; --leading) {
         }

         this.precision = leading + 1;
         if (this.precision <= 16) {
            this.switchStorage();
         }
      } else {
         if (this.bcdLong == 0L) {
            this.setBcdToZero();
            return;
         }

         delta = Long.numberOfTrailingZeros(this.bcdLong) / 4;
         this.bcdLong >>>= delta * 4;
         this.scale += delta;
         this.precision = 16 - Long.numberOfLeadingZeros(this.bcdLong) / 4;
      }

   }

   private void ensureCapacity() {
      this.ensureCapacity(40);
   }

   private void ensureCapacity(int capacity) {
      if (capacity != 0) {
         if (this.bcdBytes == null) {
            this.bcdBytes = new byte[capacity];
         } else if (this.bcdBytes.length < capacity) {
            byte[] bcd1 = new byte[capacity * 2];
            System.arraycopy(this.bcdBytes, 0, bcd1, 0, this.bcdBytes.length);
            this.bcdBytes = bcd1;
         }

      }
   }

   private void switchStorage() {
      int i;
      if (this.usingBytes) {
         this.bcdLong = 0L;

         for(i = this.precision - 1; i >= 0; --i) {
            this.bcdLong <<= 4;
            this.bcdLong |= (long)this.bcdBytes[i];
            this.bcdBytes[i] = 0;
         }

         this.usingBytes = false;
      } else {
         this.ensureCapacity();

         for(i = 0; i < this.precision; ++i) {
            this.bcdBytes[i] = (byte)((int)(this.bcdLong & 15L));
            this.bcdLong >>>= 4;
         }

         this.usingBytes = true;
      }

   }

   protected void copyBcdFrom(FormatQuantity _other) {
      FormatQuantity4 other = (FormatQuantity4)_other;
      if (other.usingBytes) {
         this.usingBytes = true;
         this.ensureCapacity(other.precision);
         System.arraycopy(other.bcdBytes, 0, this.bcdBytes, 0, other.precision);
      } else {
         this.usingBytes = false;
         this.bcdLong = other.bcdLong;
      }

   }

   /** @deprecated */
   @Deprecated
   public String checkHealth() {
      int i;
      if (this.usingBytes) {
         if (this.bcdLong != 0L) {
            return "Value in bcdLong but we are in byte mode";
         }

         if (this.precision == 0) {
            return "Zero precision but we are in byte mode";
         }

         if (this.precision > this.bcdBytes.length) {
            return "Precision exceeds length of byte array";
         }

         if (this.getDigitPos(this.precision - 1) == 0) {
            return "Most significant digit is zero in byte mode";
         }

         if (this.getDigitPos(0) == 0) {
            return "Least significant digit is zero in long mode";
         }

         for(i = 0; i < this.precision; ++i) {
            if (this.getDigitPos(i) >= 10) {
               return "Digit exceeding 10 in byte array";
            }

            if (this.getDigitPos(i) < 0) {
               return "Digit below 0 in byte array";
            }
         }

         for(i = this.precision; i < this.bcdBytes.length; ++i) {
            if (this.getDigitPos(i) != 0) {
               return "Nonzero digits outside of range in byte array";
            }
         }
      } else {
         if (this.bcdBytes != null) {
            for(i = 0; i < this.bcdBytes.length; ++i) {
               if (this.bcdBytes[i] != 0) {
                  return "Nonzero digits in byte array but we are in long mode";
               }
            }
         }

         if (this.precision == 0 && this.bcdLong != 0L) {
            return "Value in bcdLong even though precision is zero";
         }

         if (this.precision > 16) {
            return "Precision exceeds length of long";
         }

         if (this.precision != 0 && this.getDigitPos(this.precision - 1) == 0) {
            return "Most significant digit is zero in long mode";
         }

         if (this.precision != 0 && this.getDigitPos(0) == 0) {
            return "Least significant digit is zero in long mode";
         }

         for(i = 0; i < this.precision; ++i) {
            if (this.getDigitPos(i) >= 10) {
               return "Digit exceeding 10 in long";
            }

            if (this.getDigitPos(i) < 0) {
               return "Digit below 0 in long (?!)";
            }
         }

         for(i = this.precision; i < 16; ++i) {
            if (this.getDigitPos(i) != 0) {
               return "Nonzero digits outside of range in long";
            }
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public boolean usingBytes() {
      return this.usingBytes;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.usingBytes) {
         for(int i = this.precision - 1; i >= 0; --i) {
            sb.append(this.bcdBytes[i]);
         }
      } else {
         sb.append(Long.toHexString(this.bcdLong));
      }

      return String.format("<FormatQuantity4 %s:%d:%d:%s %s %s%s%d>", this.lOptPos > 1000 ? "max" : String.valueOf(this.lOptPos), this.lReqPos, this.rReqPos, this.rOptPos < -1000 ? "min" : String.valueOf(this.rOptPos), this.usingBytes ? "bytes" : "long", sb, "E", this.scale);
   }
}

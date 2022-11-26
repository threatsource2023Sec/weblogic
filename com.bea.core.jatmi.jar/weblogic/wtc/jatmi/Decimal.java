package weblogic.wtc.jatmi;

import java.math.BigDecimal;

public class Decimal extends Number implements Comparable {
   public static final int DECSIZE = 16;
   public static final int DECPOSNULL = -1;
   private short dec_exp;
   private short dec_pos;
   private short dec_ndgts;
   private byte[] dec_dgts = new byte[16];
   private static final char[] charVals = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
   private static final long serialVersionUID = -1976832511211908492L;

   public int exponent() {
      return this.dec_exp;
   }

   public int sign() {
      return this.dec_pos;
   }

   public int numDigits() {
      return this.dec_ndgts;
   }

   public byte[] digits() {
      return this.dec_dgts;
   }

   public Decimal() {
      this.setNull();
   }

   public Decimal(int sign, int exponent, int numDigits, byte[] digits) {
      if (sign == -1) {
         this.setNull();
      } else if (sign != 0 && sign != 1) {
         throw new NumberFormatException("Illegal value for sign field, " + sign);
      } else {
         this.dec_pos = (short)sign;
         if (exponent > 63) {
            throw new NumberFormatException("Value would overflow size of decimal");
         } else if (exponent < -64) {
            throw new NumberFormatException("Value would underflow size of Decimal");
         } else {
            this.dec_exp = (short)exponent;
            if (numDigits > 16) {
               throw new NumberFormatException("Number of significant digits too large for a Decimal value");
            } else {
               this.dec_ndgts = (short)numDigits;
               int i = 0;

               while(true) {
                  if (i < this.dec_ndgts && i < digits.length) {
                     if (digits[i] >= 0 && digits[i] <= 99) {
                        this.dec_dgts[i] = digits[i];
                        ++i;
                        continue;
                     }

                     throw new NumberFormatException("Illegal value for input byte[" + i + "], " + digits[i]);
                  }

                  while(i < 16) {
                     this.dec_dgts[i] = 0;
                     ++i;
                  }

                  return;
               }
            }
         }
      }
   }

   public Decimal(double value) throws NumberFormatException {
      this.convertDoubleToDecimal(value, 16);
   }

   public Decimal(float value) throws NumberFormatException {
      this.convertDoubleToDecimal((double)value, 8);
   }

   public Decimal(String str) throws NumberFormatException {
      BigDecimal value = new BigDecimal(str);
      this.convertBigDecimalToDecimal(value, 32);
   }

   public Decimal(long lval) {
      if (lval == Long.MIN_VALUE) {
         this.setNull();
      } else {
         this.convertLongToDecimal(lval);
      }
   }

   public Decimal(int ival) {
      if (ival == Integer.MIN_VALUE) {
         this.setNull();
      } else {
         this.convertLongToDecimal((long)ival);
      }
   }

   public Decimal(short sval) {
      if (sval == Short.MIN_VALUE) {
         this.setNull();
      } else {
         this.convertLongToDecimal((long)sval);
      }
   }

   public Decimal(byte bval) {
      if (bval == -128) {
         this.setNull();
      } else {
         this.convertLongToDecimal((long)bval);
      }
   }

   public Decimal negate() {
      if (this.dec_pos == -1) {
         return new Decimal();
      } else {
         int sign = this.dec_pos == 0 ? 1 : 0;
         return new Decimal(sign, this.dec_exp, this.dec_ndgts, this.dec_dgts);
      }
   }

   private String convertToScientificNotation() {
      char[] buf = new char[40];
      int cindex = 0;
      int bindex = 0;
      int expon = this.dec_exp;
      int ndgts = this.dec_ndgts;
      if (this.dec_pos == 0) {
         buf[cindex++] = '-';
      }

      if (expon <= 0) {
         buf[cindex++] = '0';
         buf[cindex++] = '.';
      } else if (expon < ndgts) {
         cindex = this.convertFromBase100(buf, cindex, bindex, expon);
         buf[cindex++] = '.';
         bindex += expon;
         ndgts -= expon;
         expon = 0;
      } else {
         expon -= ndgts;
      }

      cindex = this.convertFromBase100(buf, cindex, bindex, ndgts);
      if (expon != 0) {
         expon *= 2;
         buf[cindex++] = 'E';
         if (expon < 0) {
            expon = -expon;
            buf[cindex++] = '-';
         }

         if (expon >= 100) {
            buf[cindex++] = '1';
            expon -= 100;
         }

         if (expon >= 10) {
            buf[cindex++] = charVals[expon / 10];
         }

         buf[cindex++] = charVals[expon % 10];
      }

      String sval = new String(buf, 0, cindex);
      return sval;
   }

   public BigDecimal bigDecimalValue() {
      return this.dec_pos == -1 ? new BigDecimal(Double.MIN_VALUE) : new BigDecimal(this.convertToScientificNotation());
   }

   public double doubleValue() {
      return this.dec_pos == -1 ? Double.MIN_VALUE : Double.parseDouble(this.convertToScientificNotation());
   }

   public float floatValue() throws NumberFormatException {
      if (this.dec_pos == -1) {
         return Float.MIN_VALUE;
      } else {
         double dval = this.doubleValue();
         if (!((float)dval > Float.MAX_VALUE) && !((float)dval < -3.4028235E38F)) {
            return (float)dval;
         } else {
            throw new NumberFormatException("Decimal value would cause overflow");
         }
      }
   }

   public long longValue() throws NumberFormatException {
      return this.dec_pos == -1 ? Long.MIN_VALUE : this.convertDecimalToLong();
   }

   public int intValue() throws NumberFormatException {
      if (this.dec_pos == -1) {
         return Integer.MIN_VALUE;
      } else {
         long lval = this.convertDecimalToLong();
         if (lval <= 2147483647L && lval >= -2147483647L) {
            return (int)lval;
         } else {
            throw new NumberFormatException("Decimal value would cause overflow");
         }
      }
   }

   public short shortValue() throws NumberFormatException {
      if (this.dec_pos == -1) {
         return Short.MIN_VALUE;
      } else {
         long lval = this.convertDecimalToLong();
         if (lval <= 32767L && lval >= -32767L) {
            return (short)((int)lval);
         } else {
            throw new NumberFormatException("Decimal value would cause overflow");
         }
      }
   }

   public byte byteValue() throws NumberFormatException {
      if (this.dec_pos == -1) {
         return -128;
      } else {
         long lval = this.convertDecimalToLong();
         if (lval <= 127L && lval >= -127L) {
            return (byte)((int)lval);
         } else {
            throw new NumberFormatException("Decimal value would cause overflow");
         }
      }
   }

   public String toString() throws NumberFormatException {
      BigDecimal b = this.bigDecimalValue();
      return b.toString();
   }

   public static String toString(Decimal dec) throws NumberFormatException {
      BigDecimal b = dec.bigDecimalValue();
      return b.toString();
   }

   public static Decimal valueOf(String s) throws NumberFormatException {
      return new Decimal(s);
   }

   public int compareTo(Object other) throws NumberFormatException {
      return this.compareTo((Decimal)other);
   }

   public int compareTo(Decimal other) throws NumberFormatException {
      int result = 0;
      if (this.dec_pos != -1 && other.dec_pos != -1) {
         if (this.dec_pos != other.dec_pos) {
            result = 1;
         } else if (this.dec_ndgts == 0) {
            result = other.dec_ndgts == 0 ? 0 : -1;
         } else if (other.dec_ndgts == 0) {
            result = 1;
         } else if (this.dec_exp < other.dec_exp) {
            result = -1;
         } else if (this.dec_exp > other.dec_exp) {
            result = 1;
         } else {
            boolean less = this.dec_ndgts < other.dec_ndgts;
            int length = less ? this.dec_ndgts : other.dec_ndgts;

            for(int i = 0; i < length; ++i) {
               if (this.dec_dgts[i] < other.dec_dgts[i]) {
                  result = -1;
                  break;
               }

               if (this.dec_dgts[i] > other.dec_dgts[i]) {
                  result = 1;
                  break;
               }
            }

            if (result == 0 && this.dec_ndgts != other.dec_ndgts) {
               result = less ? -1 : 1;
            }
         }

         return this.dec_pos != 0 ? result : -result;
      } else {
         throw new NumberFormatException("Cannot compare null values.");
      }
   }

   private void setNull() {
      this.dec_pos = -1;
      this.dec_exp = 0;
      this.dec_ndgts = 0;
   }

   private void convertBigDecimalToDecimal(BigDecimal value, int numDigits) throws NumberFormatException {
      byte[] digits = new byte[numDigits];
      int decpt = this.convertBigDecimalToBytes(value, digits, numDigits);
      this.dec_ndgts = this.convertToBase100(digits, numDigits, decpt & 1);
      this.decload(value.signum() > 0, (decpt + 1 & -2) / 2, this.dec_dgts, this.dec_ndgts);
   }

   private void convertDoubleToDecimal(double value, int numDigits) throws NumberFormatException {
      if (!Double.isNaN(value) && !Double.isInfinite(value)) {
         byte[] digits = new byte[numDigits];
         int decpt = this.convertDoubleToBytes(value, digits, numDigits);
         this.dec_ndgts = this.convertToBase100(digits, numDigits, decpt & 1);
         this.decload(value > 0.0, (decpt + 1 & -2) / 2, this.dec_dgts, this.dec_ndgts);
      } else {
         throw new NumberFormatException("Value represents a NaN or Infinity.");
      }
   }

   private int convertToBytes(String sval, byte[] digits, int numDigits) {
      int decPos = false;
      char[] cval = sval.toCharArray();
      int blen;
      if (cval.length > numDigits + 1) {
         blen = cval.length;
      } else {
         blen = numDigits + 1;
      }

      byte[] bval = new byte[blen];
      int target = 0;
      int source = 0;
      int decCnt = 0;
      boolean point = false;
      boolean allZero = true;
      boolean expSeen = false;
      if (cval[0] == '-') {
         source = 1;
      }

      while(source < cval.length) {
         if (cval[source] == '0') {
            if (allZero) {
               if (point) {
                  --decCnt;
               }

               ++source;
               continue;
            }

            if (!point) {
               ++decCnt;
            }
         } else {
            if (cval[source] == '.') {
               ++source;
               point = true;
               if (allZero) {
                  decCnt = 0;
               }
               continue;
            }

            if (cval[source] == 'E') {
               expSeen = true;
               ++source;
               break;
            }

            allZero = false;
            if (!point) {
               ++decCnt;
            }
         }

         bval[target++] = (byte)(cval[source++] - 48);
      }

      int decPos;
      if (allZero) {
         decPos = 1;
      } else {
         decPos = decCnt;
      }

      int i;
      if (expSeen) {
         i = 0;
         boolean minus = false;
         if (cval[source] == '-') {
            ++source;
            minus = true;
         }

         while(source < cval.length) {
            byte bv = (byte)(cval[source++] - 48);
            i = i * 10 + bv;
         }

         if (minus) {
            i = -i;
         }

         decPos += i;
      }

      while(target < numDigits) {
         bval[target++] = 0;
      }

      if (bval[numDigits] > 5) {
         if (bval[numDigits - 1] < 9) {
            ++bval[numDigits - 1];
         } else {
            bval[numDigits - 1] = 0;

            for(i = numDigits - 1; i > 0; --i) {
               if (bval[i - 1] < 9) {
                  ++bval[i - 1];
                  break;
               }

               bval[i - 1] = 0;
            }

            if (i == 0) {
               for(int j = bval.length - 1; j > 0; --j) {
                  bval[j] = bval[j - 1];
               }

               bval[0] = 1;
               ++decPos;
            }
         }
      }

      System.arraycopy(bval, 0, digits, 0, numDigits);
      return decPos;
   }

   private int convertBigDecimalToBytes(BigDecimal value, byte[] digits, int numDigits) {
      String sval = value.toPlainString();
      return this.convertToBytes(sval, digits, numDigits);
   }

   private int convertDoubleToBytes(double value, byte[] digits, int numDigits) {
      String sval;
      if (numDigits == 8) {
         sval = Float.toString((float)value);
      } else {
         sval = Double.toString(value);
      }

      return this.convertToBytes(sval, digits, numDigits);
   }

   private short convertToBase100(byte[] digits, int numDigits, int odd) {
      byte digit = 0;
      int source = 0;

      for(int target = 0; source < numDigits || digit != 0; ++odd) {
         if (source < numDigits) {
            digit += digits[source++];
         }

         if ((odd & 1) != 0) {
            this.dec_dgts[target++] = digit;
            digit = 0;
         } else {
            digit = (byte)(digit * 10);
         }
      }

      return (short)(odd / 2);
   }

   private int convertFromBase100(char[] cval, int cindex, int bindex, int ndgts) {
      while(ndgts > 0) {
         byte b = this.dec_dgts[bindex++];
         cval[cindex++] = charVals[b / 10];
         cval[cindex++] = charVals[b % 10];
         --ndgts;
      }

      return cindex;
   }

   private void decload(boolean pos, int expon, byte[] digits, int ndgts) throws NumberFormatException {
      int index;
      for(index = ndgts - 1; index >= 0 && digits[index] == 0; --ndgts) {
         --index;
      }

      for(index = 0; digits[index] == 0 && ndgts > 0; --expon) {
         ++index;
         --ndgts;
      }

      this.dec_ndgts = (short)ndgts;
      if (ndgts > 0) {
         System.arraycopy(digits, index, this.dec_dgts, 0, ndgts);
         this.dec_exp = (short)expon;
         if (pos) {
            this.dec_pos = 1;
         } else {
            this.dec_pos = 0;
         }
      } else {
         this.dec_exp = 0;
         this.dec_pos = 1;
      }

      if (expon < -64) {
         this.dec_exp = -64;
         throw new NumberFormatException("Value would underflow size of Decimal");
      } else if (expon > 63) {
         this.dec_exp = 63;
         throw new NumberFormatException("Value would overflow size of decimal");
      }
   }

   private void convertLongToDecimal(long l) {
      boolean pos = true;
      if (l < 0L) {
         pos = false;
         l = -l;
      }

      byte[] digits = new byte[10];

      for(int i = 9; i >= 0; --i) {
         digits[i] = (byte)((int)(l % 100L));
         l /= 100L;
      }

      this.decload(pos, 10, digits, 10);
   }

   private long convertDecimalToLong() throws NumberFormatException {
      long lval = 0L;
      if (this.dec_exp > 10) {
         throw new NumberFormatException("Decimal value would cause overflow");
      } else {
         if (this.dec_exp == 10) {
            Decimal maxDec = new Decimal(Long.MAX_VALUE);
            if (this.compareTo(maxDec) > 0) {
               throw new NumberFormatException("Decimal value would cause overflow");
            }

            maxDec.dec_pos = 0;
            if (this.compareTo(maxDec) < 0) {
               throw new NumberFormatException("Decimal value would cause overflow");
            }
         }

         for(int i = 0; i < this.dec_exp; ++i) {
            lval *= 100L;
            if (i < this.dec_ndgts) {
               lval += (long)this.dec_dgts[i];
            }
         }

         return this.dec_pos != 0 ? lval : -lval;
      }
   }

   public int hashCode() {
      int retVal = this.dec_exp | this.dec_pos << 16;
      retVal ^= this.dec_ndgts;
      if (this.dec_dgts != null) {
         int lcv = 0;
         int buildMe = 0;
         byte[] var4 = this.dec_dgts;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            byte b = var4[var6];
            if (lcv % 4 == 0) {
               buildMe |= b;
            } else if (lcv % 4 == 1) {
               buildMe |= b << 8;
            } else if (lcv % 4 == 2) {
               buildMe |= b << 16;
            } else if (lcv % 4 == 3) {
               buildMe |= b << 24;
               retVal ^= buildMe;
               buildMe = 0;
            }

            lcv = (lcv + 1) % 4;
         }

         if (lcv != 0) {
            retVal ^= buildMe;
         }
      }

      return retVal;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof Decimal) {
         Decimal other = (Decimal)obj;
         if (this.dec_pos == other.dec_pos && this.dec_exp == other.dec_exp && this.dec_ndgts == other.dec_ndgts) {
            for(int i = 0; i < this.dec_ndgts; ++i) {
               if (this.dec_dgts[i] != other.dec_dgts[i]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}

package org.python.core.stringlib;

import java.math.BigInteger;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;

public class IntegerFormatter extends InternalFormat.Formatter {
   private static final BigInteger LIMIT_UNICODE = BigInteger.valueOf(1114112L);
   private static final BigInteger LIMIT_BYTE = BigInteger.valueOf(256L);
   private static final String LOOKUP = "0123456789abcdef";
   public static final InternalFormat.Spec BIN = InternalFormat.fromText("#b");
   public static final InternalFormat.Spec OCT = InternalFormat.fromText("#o");
   public static final InternalFormat.Spec HEX = InternalFormat.fromText("#x");

   public IntegerFormatter(StringBuilder result, InternalFormat.Spec spec) {
      super(result, spec);
   }

   public IntegerFormatter(InternalFormat.Spec spec) {
      this(new StringBuilder(34), spec);
   }

   public IntegerFormatter append(char c) {
      super.append(c);
      return this;
   }

   public IntegerFormatter append(CharSequence csq) {
      super.append(csq);
      return this;
   }

   public IntegerFormatter append(CharSequence csq, int start, int end) throws IndexOutOfBoundsException {
      super.append(csq, start, end);
      return this;
   }

   public IntegerFormatter format(BigInteger value) {
      try {
         switch (this.spec.type) {
            case 'X':
               this.format_x(value, true);
               break;
            case 'b':
               this.format_b(value);
               break;
            case 'c':
               this.format_c(value);
               break;
            case 'd':
            case 'i':
            case 'u':
            case '\uffff':
               this.format_d(value);
               break;
            case 'n':
               this.format_d(value);
               break;
            case 'o':
               this.format_o(value);
               break;
            case 'x':
               this.format_x(value, false);
               break;
            default:
               throw unknownFormat(this.spec.type, "long");
         }

         if (this.spec.grouping) {
            this.groupDigits(3, ',');
         }

         return this;
      } catch (OutOfMemoryError var3) {
         throw precisionTooLarge("long");
      }
   }

   void format_d(BigInteger value) {
      String number;
      if (value.signum() < 0) {
         this.negativeSign((String)null);
         number = value.negate().toString();
      } else {
         this.positiveSign((String)null);
         number = value.toString();
      }

      this.appendNumber(number);
   }

   void format_x(BigInteger value, boolean upper) {
      String base = upper ? "0X" : "0x";
      String number;
      if (value.signum() < 0) {
         this.negativeSign(base);
         number = toHexString(value.negate());
      } else {
         this.positiveSign(base);
         number = toHexString(value);
      }

      if (upper) {
         number = number.toUpperCase();
      }

      this.appendNumber(number);
   }

   void format_o(BigInteger value) {
      String base = "0o";
      String number;
      if (value.signum() < 0) {
         this.negativeSign(base);
         number = toOctalString(value.negate());
      } else {
         this.positiveSign(base);
         number = toOctalString(value);
      }

      this.appendNumber(number);
   }

   void format_b(BigInteger value) {
      String base = "0b";
      String number;
      if (value.signum() < 0) {
         this.negativeSign(base);
         number = toBinaryString(value.negate());
      } else {
         this.positiveSign(base);
         number = toBinaryString(value);
      }

      this.appendNumber(number);
   }

   void format_c(BigInteger value) {
      BigInteger limit = this.bytes ? LIMIT_BYTE : LIMIT_UNICODE;
      if (value.signum() >= 0 && value.compareTo(limit) < 0) {
         this.result.appendCodePoint(value.intValue());
      } else {
         throw Py.OverflowError("%c arg not in range(0x" + toHexString(limit) + ")");
      }
   }

   public IntegerFormatter format(int value) {
      try {
         this.setStart();
         switch (this.spec.type) {
            case '%':
            case 'c':
               this.format_c(value);
               break;
            case 'X':
               this.format_x(value, true);
               break;
            case 'b':
               this.format_b(value);
               break;
            case 'd':
            case 'i':
            case 'u':
            case '\uffff':
               this.format_d(value);
               break;
            case 'n':
               this.format_d(value);
               break;
            case 'o':
               this.format_o(value);
               break;
            case 'x':
               this.format_x(value, false);
               break;
            default:
               throw unknownFormat(this.spec.type, "integer");
         }

         if (this.spec.grouping) {
            this.groupDigits(3, ',');
         }

         return this;
      } catch (OutOfMemoryError var3) {
         throw precisionTooLarge("integer");
      }
   }

   void format_d(int value) {
      String number;
      if (value < 0) {
         this.negativeSign((String)null);
         number = Integer.toString(-value);
      } else {
         this.positiveSign((String)null);
         number = Integer.toString(value);
      }

      this.appendNumber(number);
   }

   void format_x(int value, boolean upper) {
      String base = upper ? "0X" : "0x";
      String number;
      if (value < 0) {
         this.negativeSign(base);
         number = Integer.toHexString(-value);
      } else {
         this.positiveSign(base);
         number = Integer.toHexString(value);
      }

      if (upper) {
         number = number.toUpperCase();
      }

      this.appendNumber(number);
   }

   void format_o(int value) {
      String base = "0o";
      String number;
      if (value < 0) {
         this.negativeSign(base);
         number = Integer.toOctalString(-value);
      } else {
         this.positiveSign(base);
         number = Integer.toOctalString(value);
      }

      this.appendNumber(number);
   }

   void format_b(int value) {
      String base = "0b";
      String number;
      if (value < 0) {
         this.negativeSign(base);
         number = Integer.toBinaryString(-value);
      } else {
         this.positiveSign(base);
         number = Integer.toBinaryString(value);
      }

      this.appendNumber(number);
   }

   void format_c(int value) {
      int limit = this.bytes ? 256 : 1114112;
      if (value >= 0 && value < limit) {
         this.result.appendCodePoint(value);
      } else {
         throw Py.OverflowError("%c arg not in range(0x" + Integer.toHexString(limit) + ")");
      }
   }

   final void positiveSign(String base) {
      char sign = this.spec.sign;
      if (InternalFormat.Spec.specified(sign) && sign != '-') {
         this.append(sign);
         this.lenSign = 1;
      }

      if (base != null && this.spec.alternate) {
         this.append(base);
         this.lenSign += base.length();
      }

   }

   final void negativeSign(String base) {
      this.append('-');
      this.lenSign = 1;
      if (base != null && this.spec.alternate) {
         this.append(base);
         this.lenSign += base.length();
      }

   }

   void appendNumber(String number) {
      this.lenWhole = number.length();
      this.append(number);
   }

   private static final String toHexString(BigInteger value) {
      int signum = value.signum();
      if (signum == 0) {
         return "0";
      } else {
         byte[] input = value.abs().toByteArray();
         StringBuilder sb = new StringBuilder(input.length * 2);

         for(int i = 0; i < input.length; ++i) {
            int b = input[i] & 255;
            sb.append("0123456789abcdef".charAt(b >> 4));
            sb.append("0123456789abcdef".charAt(b & 15));
         }

         String result = sb.toString().replaceFirst("^0+(?!$)", "");
         return signum < 0 ? "-" + result : result;
      }
   }

   private static final String toOctalString(BigInteger value) {
      int signum = value.signum();
      if (signum == 0) {
         return "0";
      } else {
         byte[] input = value.abs().toByteArray();
         if (input.length < 3) {
            return value.toString(8);
         } else {
            StringBuilder sb = new StringBuilder(input.length * 3);

            for(int i = input.length - 1; i >= 0; i -= 3) {
               int trip3 = input[i] & 255;
               int trip2 = i - 1 >= 0 ? input[i - 1] & 255 : 0;
               int trip1 = i - 2 >= 0 ? input[i - 2] & 255 : 0;
               int threebytes = trip3 | trip2 << 8 | trip1 << 16;

               for(int j = 0; j < 8; ++j) {
                  sb.append("0123456789abcdef".charAt(threebytes >> j * 3 & 7));
               }
            }

            String result = sb.reverse().toString().replaceFirst("^0+(?!%)", "");
            return signum < 0 ? "-" + result : result;
         }
      }
   }

   private static final String toBinaryString(BigInteger value) {
      int signum = value.signum();
      if (signum == 0) {
         return "0";
      } else {
         byte[] input = value.abs().toByteArray();
         StringBuilder sb = new StringBuilder(value.bitCount());

         for(int i = 0; i < input.length; ++i) {
            int b = input[i] & 255;

            for(int bit = 7; bit >= 0; --bit) {
               sb.append((b >> bit & 1) > 0 ? "1" : "0");
            }
         }

         String result = sb.toString().replaceFirst("^0+(?!$)", "");
         return signum < 0 ? "-" + result : result;
      }
   }

   public static PyString bin(PyObject number) {
      return formatNumber(number, BIN);
   }

   public static PyString formatNumber(PyObject number, InternalFormat.Spec spec) {
      number = number.__index__();
      IntegerFormatter f = new IntegerFormatter(spec);
      if (number instanceof PyInteger) {
         f.format(((PyInteger)number).getValue());
      } else {
         f.format(((PyLong)number).getValue());
      }

      return new PyString(f.getResult());
   }

   public static class Traditional extends IntegerFormatter {
      public Traditional(StringBuilder result, InternalFormat.Spec spec) {
         super(result, spec);
      }

      public Traditional(InternalFormat.Spec spec) {
         this(new StringBuilder(), spec);
      }

      void format_o(BigInteger value) {
         int signum = value.signum();
         String number;
         if (signum < 0) {
            this.negativeSign((String)null);
            number = IntegerFormatter.toOctalString(value.negate());
         } else {
            this.positiveSign((String)null);
            number = IntegerFormatter.toOctalString(value);
         }

         this.appendOctalNumber(number);
      }

      void format_c(BigInteger value) {
         if (value.signum() < 0) {
            throw Py.OverflowError("unsigned byte integer is less than minimum");
         } else {
            BigInteger limit = this.bytes ? IntegerFormatter.LIMIT_BYTE : IntegerFormatter.LIMIT_UNICODE;
            if (value.compareTo(limit) >= 0) {
               throw Py.OverflowError("unsigned byte integer is greater than maximum");
            } else {
               this.result.appendCodePoint(value.intValue());
            }
         }
      }

      void format_o(int value) {
         String number;
         if (value < 0) {
            this.negativeSign((String)null);
            number = Integer.toOctalString(-value);
         } else {
            this.positiveSign((String)null);
            number = Integer.toOctalString(value);
         }

         this.appendOctalNumber(number);
      }

      void format_c(int value) {
         if (value < 0) {
            throw Py.OverflowError("unsigned byte integer is less than minimum");
         } else {
            int limit = this.bytes ? 256 : 1114112;
            if (value >= limit) {
               throw Py.OverflowError("unsigned byte integer is greater than maximum");
            } else {
               this.result.appendCodePoint(value);
            }
         }
      }

      void appendNumber(String number) {
         int p = this.spec.getPrecision(0);

         int n;
         for(n = number.length(); n < p; ++n) {
            this.result.append('0');
         }

         this.lenWhole = n;
         this.append(number);
      }

      void appendOctalNumber(String number) {
         int n = number.length();
         int p = this.spec.getPrecision(0);
         if (this.spec.alternate && number.charAt(0) != '0' && n >= p) {
            p = n + 1;
         }

         while(n < p) {
            this.result.append('0');
            ++n;
         }

         this.lenWhole = n;
         this.append(number);
      }
   }
}

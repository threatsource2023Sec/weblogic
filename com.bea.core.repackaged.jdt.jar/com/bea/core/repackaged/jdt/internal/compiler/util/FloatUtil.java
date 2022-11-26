package com.bea.core.repackaged.jdt.internal.compiler.util;

public class FloatUtil {
   private static final int DOUBLE_FRACTION_WIDTH = 52;
   private static final int DOUBLE_PRECISION = 53;
   private static final int MAX_DOUBLE_EXPONENT = 1023;
   private static final int MIN_NORMALIZED_DOUBLE_EXPONENT = -1022;
   private static final int MIN_UNNORMALIZED_DOUBLE_EXPONENT = -1075;
   private static final int DOUBLE_EXPONENT_BIAS = 1023;
   private static final int DOUBLE_EXPONENT_SHIFT = 52;
   private static final int SINGLE_FRACTION_WIDTH = 23;
   private static final int SINGLE_PRECISION = 24;
   private static final int MAX_SINGLE_EXPONENT = 127;
   private static final int MIN_NORMALIZED_SINGLE_EXPONENT = -126;
   private static final int MIN_UNNORMALIZED_SINGLE_EXPONENT = -150;
   private static final int SINGLE_EXPONENT_BIAS = 127;
   private static final int SINGLE_EXPONENT_SHIFT = 23;

   public static float valueOfHexFloatLiteral(char[] source) {
      long bits = convertHexFloatingPointLiteralToBits(source);
      return Float.intBitsToFloat((int)bits);
   }

   public static double valueOfHexDoubleLiteral(char[] source) {
      long bits = convertHexFloatingPointLiteralToBits(source);
      return Double.longBitsToDouble(bits);
   }

   private static long convertHexFloatingPointLiteralToBits(char[] source) {
      int length = source.length;
      long mantissa = 0L;
      int next = 0;
      char nextChar = source[next];
      if (nextChar != '0') {
         throw new NumberFormatException();
      } else {
         ++next;
         nextChar = source[next];
         if (nextChar != 'X' && nextChar != 'x') {
            throw new NumberFormatException();
         } else {
            ++next;
            int binaryPointPosition = -1;

            while(true) {
               nextChar = source[next];
               switch (nextChar) {
                  case '.':
                     binaryPointPosition = next++;
                     break;
                  case '/':
                  default:
                     int mantissaBits = 0;
                     int leadingDigitPosition = -1;

                     while(true) {
                        nextChar = source[next];
                        int exponent;
                        switch (nextChar) {
                           case '.':
                              binaryPointPosition = next++;
                              continue;
                           case '/':
                           case ':':
                           case ';':
                           case '<':
                           case '=':
                           case '>':
                           case '?':
                           case '@':
                           case 'G':
                           case 'H':
                           case 'I':
                           case 'J':
                           case 'K':
                           case 'L':
                           case 'M':
                           case 'N':
                           case 'O':
                           case 'P':
                           case 'Q':
                           case 'R':
                           case 'S':
                           case 'T':
                           case 'U':
                           case 'V':
                           case 'W':
                           case 'X':
                           case 'Y':
                           case 'Z':
                           case '[':
                           case '\\':
                           case ']':
                           case '^':
                           case '_':
                           case '`':
                           default:
                              if (binaryPointPosition < 0) {
                                 binaryPointPosition = next;
                              }

                              nextChar = source[next];
                              if (nextChar != 'P' && nextChar != 'p') {
                                 throw new NumberFormatException();
                              } else {
                                 ++next;
                                 exponent = 0;
                                 int exponentSign = 1;

                                 label133:
                                 while(next < length) {
                                    nextChar = source[next];
                                    switch (nextChar) {
                                       case '+':
                                          exponentSign = 1;
                                          ++next;
                                          break;
                                       case ',':
                                       case '.':
                                       case '/':
                                       default:
                                          break label133;
                                       case '-':
                                          exponentSign = -1;
                                          ++next;
                                          break;
                                       case '0':
                                       case '1':
                                       case '2':
                                       case '3':
                                       case '4':
                                       case '5':
                                       case '6':
                                       case '7':
                                       case '8':
                                       case '9':
                                          int digit = nextChar - 48;
                                          exponent = exponent * 10 + digit;
                                          ++next;
                                    }
                                 }

                                 boolean doublePrecision = true;
                                 if (next < length) {
                                    nextChar = source[next];
                                    switch (nextChar) {
                                       case 'D':
                                       case 'd':
                                          doublePrecision = true;
                                          ++next;
                                          break;
                                       case 'F':
                                       case 'f':
                                          doublePrecision = false;
                                          ++next;
                                          break;
                                       default:
                                          throw new NumberFormatException();
                                    }
                                 }

                                 if (mantissa == 0L) {
                                    return 0L;
                                 } else {
                                    int scaleFactorCompensation = 0;
                                    long top = mantissa >>> mantissaBits - 4;
                                    if ((top & 8L) == 0L) {
                                       --mantissaBits;
                                       ++scaleFactorCompensation;
                                       if ((top & 4L) == 0L) {
                                          --mantissaBits;
                                          ++scaleFactorCompensation;
                                          if ((top & 2L) == 0L) {
                                             --mantissaBits;
                                             ++scaleFactorCompensation;
                                          }
                                       }
                                    }

                                    long result = 0L;
                                    long fraction;
                                    int scaleFactor;
                                    long lowBit;
                                    long biasedExponent;
                                    int e;
                                    if (doublePrecision) {
                                       if (mantissaBits > 53) {
                                          scaleFactor = mantissaBits - 53;
                                          fraction = mantissa >>> scaleFactor - 1;
                                          lowBit = fraction & 1L;
                                          fraction += lowBit;
                                          fraction >>>= 1;
                                          if ((fraction & 9007199254740992L) != 0L) {
                                             fraction >>>= 1;
                                             --scaleFactorCompensation;
                                          }
                                       } else {
                                          fraction = mantissa << 53 - mantissaBits;
                                       }

                                       scaleFactor = 0;
                                       if (mantissaBits > 0) {
                                          if (leadingDigitPosition < binaryPointPosition) {
                                             scaleFactor = 4 * (binaryPointPosition - leadingDigitPosition);
                                             scaleFactor -= scaleFactorCompensation;
                                          } else {
                                             scaleFactor = -4 * (leadingDigitPosition - binaryPointPosition - 1);
                                             scaleFactor -= scaleFactorCompensation;
                                          }
                                       }

                                       e = exponentSign * exponent + scaleFactor;
                                       if (e - 1 > 1023) {
                                          result = Double.doubleToLongBits(Double.POSITIVE_INFINITY);
                                       } else if (e - 1 >= -1022) {
                                          biasedExponent = (long)(e - 1 + 1023);
                                          result = fraction & -4503599627370497L;
                                          result |= biasedExponent << 52;
                                       } else if (e - 1 > -1075) {
                                          biasedExponent = 0L;
                                          result = fraction >>> -1022 - e + 1;
                                          result |= biasedExponent << 52;
                                       } else {
                                          result = Double.doubleToLongBits(Double.NaN);
                                       }

                                       return result;
                                    } else {
                                       if (mantissaBits > 24) {
                                          scaleFactor = mantissaBits - 24;
                                          fraction = mantissa >>> scaleFactor - 1;
                                          lowBit = fraction & 1L;
                                          fraction += lowBit;
                                          fraction >>>= 1;
                                          if ((fraction & 16777216L) != 0L) {
                                             fraction >>>= 1;
                                             --scaleFactorCompensation;
                                          }
                                       } else {
                                          fraction = mantissa << 24 - mantissaBits;
                                       }

                                       scaleFactor = 0;
                                       if (mantissaBits > 0) {
                                          if (leadingDigitPosition < binaryPointPosition) {
                                             scaleFactor = 4 * (binaryPointPosition - leadingDigitPosition);
                                             scaleFactor -= scaleFactorCompensation;
                                          } else {
                                             scaleFactor = -4 * (leadingDigitPosition - binaryPointPosition - 1);
                                             scaleFactor -= scaleFactorCompensation;
                                          }
                                       }

                                       e = exponentSign * exponent + scaleFactor;
                                       if (e - 1 > 127) {
                                          result = (long)Float.floatToIntBits(Float.POSITIVE_INFINITY);
                                       } else if (e - 1 >= -126) {
                                          biasedExponent = (long)(e - 1 + 127);
                                          result = fraction & -8388609L;
                                          result |= biasedExponent << 23;
                                       } else if (e - 1 > -150) {
                                          biasedExponent = 0L;
                                          result = fraction >>> -126 - e + 1;
                                          result |= biasedExponent << 23;
                                       } else {
                                          result = (long)Float.floatToIntBits(Float.NaN);
                                       }

                                       return result;
                                    }
                                 }
                              }
                           case '0':
                           case '1':
                           case '2':
                           case '3':
                           case '4':
                           case '5':
                           case '6':
                           case '7':
                           case '8':
                           case '9':
                              exponent = nextChar - 48;
                              break;
                           case 'A':
                           case 'B':
                           case 'C':
                           case 'D':
                           case 'E':
                           case 'F':
                              exponent = nextChar - 65 + 10;
                              break;
                           case 'a':
                           case 'b':
                           case 'c':
                           case 'd':
                           case 'e':
                           case 'f':
                              exponent = nextChar - 97 + 10;
                        }

                        if (mantissaBits == 0) {
                           leadingDigitPosition = next;
                           mantissa = (long)exponent;
                           mantissaBits = 4;
                        } else if (mantissaBits < 60) {
                           mantissa <<= 4;
                           mantissa |= (long)exponent;
                           mantissaBits += 4;
                        }

                        ++next;
                     }
                  case '0':
                     ++next;
               }
            }
         }
      }
   }
}

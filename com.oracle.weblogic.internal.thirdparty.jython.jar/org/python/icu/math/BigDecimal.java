package org.python.icu.math;

import java.io.Serializable;
import java.math.BigInteger;
import org.python.icu.lang.UCharacter;

public class BigDecimal extends Number implements Serializable, Comparable {
   public static final BigDecimal ZERO = new BigDecimal(0L);
   public static final BigDecimal ONE = new BigDecimal(1L);
   public static final BigDecimal TEN = new BigDecimal(10);
   public static final int ROUND_CEILING = 2;
   public static final int ROUND_DOWN = 1;
   public static final int ROUND_FLOOR = 3;
   public static final int ROUND_HALF_DOWN = 5;
   public static final int ROUND_HALF_EVEN = 6;
   public static final int ROUND_HALF_UP = 4;
   public static final int ROUND_UNNECESSARY = 7;
   public static final int ROUND_UP = 0;
   private static final byte ispos = 1;
   private static final byte iszero = 0;
   private static final byte isneg = -1;
   private static final int MinExp = -999999999;
   private static final int MaxExp = 999999999;
   private static final int MinArg = -999999999;
   private static final int MaxArg = 999999999;
   private static final MathContext plainMC = new MathContext(0, 0);
   private static final long serialVersionUID = 8245355804974198832L;
   private static byte[] bytecar = new byte[190];
   private static byte[] bytedig = diginit();
   private byte ind;
   private byte form;
   private byte[] mant;
   private int exp;

   public BigDecimal(java.math.BigDecimal bd) {
      this(bd.toString());
   }

   public BigDecimal(BigInteger bi) {
      this(bi.toString(10));
   }

   public BigDecimal(BigInteger bi, int scale) {
      this(bi.toString(10));
      if (scale < 0) {
         throw new NumberFormatException("Negative scale: " + scale);
      } else {
         this.exp = -scale;
      }
   }

   public BigDecimal(char[] inchars) {
      this(inchars, 0, inchars.length);
   }

   public BigDecimal(char[] inchars, int offset, int length) {
      this.form = 0;
      int i = false;
      char si = false;
      boolean eneg = false;
      int k = false;
      int elen = false;
      int j = false;
      char sj = false;
      int dvalue = false;
      int mag = false;
      if (length <= 0) {
         this.bad(inchars);
      }

      this.ind = 1;
      if (inchars[offset] == '-') {
         --length;
         if (length == 0) {
            this.bad(inchars);
         }

         this.ind = -1;
         ++offset;
      } else if (inchars[offset] == '+') {
         --length;
         if (length == 0) {
            this.bad(inchars);
         }

         ++offset;
      }

      boolean exotic = false;
      boolean hadexp = false;
      int d = 0;
      int dotoff = -1;
      int last = -1;
      int $4 = length;

      int i;
      char si;
      int j;
      char sj;
      int dvalue;
      for(i = offset; $4 > 0; ++i) {
         si = inchars[i];
         if (si >= '0' && si <= '9') {
            last = i;
            ++d;
         } else if (si == '.') {
            if (dotoff >= 0) {
               this.bad(inchars);
            }

            dotoff = i - offset;
         } else {
            if (si == 'e' || si == 'E') {
               if (i - offset > length - 2) {
                  this.bad(inchars);
               }

               eneg = false;
               int k;
               if (inchars[i + 1] == '-') {
                  eneg = true;
                  k = i + 2;
               } else if (inchars[i + 1] == '+') {
                  k = i + 2;
               } else {
                  k = i + 1;
               }

               int elen = length - (k - offset);
               if (elen == 0 | elen > 9) {
                  this.bad(inchars);
               }

               int $2 = elen;

               for(j = k; $2 > 0; ++j) {
                  sj = inchars[j];
                  if (sj < '0') {
                     this.bad(inchars);
                  }

                  if (sj > '9') {
                     if (!UCharacter.isDigit(sj)) {
                        this.bad(inchars);
                     }

                     dvalue = UCharacter.digit(sj, 10);
                     if (dvalue < 0) {
                        this.bad(inchars);
                     }
                  } else {
                     dvalue = sj - 48;
                  }

                  this.exp = this.exp * 10 + dvalue;
                  --$2;
               }

               if (eneg) {
                  this.exp = -this.exp;
               }

               hadexp = true;
               break;
            }

            if (!UCharacter.isDigit(si)) {
               this.bad(inchars);
            }

            exotic = true;
            last = i;
            ++d;
         }

         --$4;
      }

      if (d == 0) {
         this.bad(inchars);
      }

      if (dotoff >= 0) {
         this.exp = this.exp + dotoff - d;
      }

      $4 = last - 1;

      for(i = offset; i <= $4; ++i) {
         si = inchars[i];
         if (si == '0') {
            ++offset;
            --dotoff;
            --d;
         } else if (si == '.') {
            ++offset;
            --dotoff;
         } else {
            if (si <= '9' || UCharacter.digit(si, 10) != 0) {
               break;
            }

            ++offset;
            --dotoff;
            --d;
         }
      }

      this.mant = new byte[d];
      j = offset;
      if (exotic) {
         $4 = d;

         for(i = 0; $4 > 0; ++i) {
            if (i == dotoff) {
               ++j;
            }

            sj = inchars[j];
            if (sj <= '9') {
               this.mant[i] = (byte)(sj - 48);
            } else {
               dvalue = UCharacter.digit(sj, 10);
               if (dvalue < 0) {
                  this.bad(inchars);
               }

               this.mant[i] = (byte)dvalue;
            }

            ++j;
            --$4;
         }
      } else {
         $4 = d;

         for(i = 0; $4 > 0; ++i) {
            if (i == dotoff) {
               ++j;
            }

            this.mant[i] = (byte)(inchars[j] - 48);
            ++j;
            --$4;
         }
      }

      if (this.mant[0] == 0) {
         this.ind = 0;
         if (this.exp > 0) {
            this.exp = 0;
         }

         if (hadexp) {
            this.mant = ZERO.mant;
            this.exp = 0;
         }
      } else if (hadexp) {
         this.form = 1;
         int mag = this.exp + this.mant.length - 1;
         if (mag < -999999999 | mag > 999999999) {
            this.bad(inchars);
         }
      }

   }

   public BigDecimal(double num) {
      this((new java.math.BigDecimal(num)).toString());
   }

   public BigDecimal(int num) {
      this.form = 0;
      int i = false;
      if (num <= 9 && num >= -9) {
         if (num == 0) {
            this.mant = ZERO.mant;
            this.ind = 0;
         } else if (num == 1) {
            this.mant = ONE.mant;
            this.ind = 1;
         } else if (num == -1) {
            this.mant = ONE.mant;
            this.ind = -1;
         } else {
            this.mant = new byte[1];
            if (num > 0) {
               this.mant[0] = (byte)num;
               this.ind = 1;
            } else {
               this.mant[0] = (byte)(-num);
               this.ind = -1;
            }
         }

      } else {
         if (num > 0) {
            this.ind = 1;
            num = -num;
         } else {
            this.ind = -1;
         }

         int mun = num;
         int i = 9;

         while(true) {
            mun /= 10;
            if (mun == 0) {
               this.mant = new byte[10 - i];
               i = 10 - i - 1;

               while(true) {
                  this.mant[i] = (byte)(-((byte)(num % 10)));
                  num /= 10;
                  if (num == 0) {
                     return;
                  }

                  --i;
               }
            }

            --i;
         }
      }
   }

   public BigDecimal(long num) {
      this.form = 0;
      int i = false;
      if (num > 0L) {
         this.ind = 1;
         num = -num;
      } else if (num == 0L) {
         this.ind = 0;
      } else {
         this.ind = -1;
      }

      long mun = num;
      int i = 18;

      while(true) {
         mun /= 10L;
         if (mun == 0L) {
            this.mant = new byte[19 - i];
            i = 19 - i - 1;

            while(true) {
               this.mant[i] = (byte)(-((byte)((int)(num % 10L))));
               num /= 10L;
               if (num == 0L) {
                  return;
               }

               --i;
            }
         }

         --i;
      }
   }

   public BigDecimal(String string) {
      this(string.toCharArray(), 0, string.length());
   }

   private BigDecimal() {
      this.form = 0;
   }

   public BigDecimal abs() {
      return this.abs(plainMC);
   }

   public BigDecimal abs(MathContext set) {
      return this.ind == -1 ? this.negate(set) : this.plus(set);
   }

   public BigDecimal add(BigDecimal rhs) {
      return this.add(rhs, plainMC);
   }

   public BigDecimal add(BigDecimal rhs, MathContext set) {
      int newlen = false;
      int tlen = false;
      int mult = false;
      byte[] t = null;
      int ia = false;
      int ib = false;
      int ea = false;
      int eb = false;
      byte ca = false;
      byte cb = false;
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      BigDecimal lhs = this;
      if (this.ind == 0 && set.form != 0) {
         return rhs.plus(set);
      } else if (rhs.ind == 0 && set.form != 0) {
         return this.plus(set);
      } else {
         int reqdig = set.digits;
         if (reqdig > 0) {
            if (this.mant.length > reqdig) {
               lhs = clone(this).round(set);
            }

            if (rhs.mant.length > reqdig) {
               rhs = clone(rhs).round(set);
            }
         }

         BigDecimal res = new BigDecimal();
         byte[] usel = lhs.mant;
         int usellen = lhs.mant.length;
         byte[] user = rhs.mant;
         int userlen = rhs.mant.length;
         int tlen;
         if (lhs.exp == rhs.exp) {
            res.exp = lhs.exp;
         } else {
            int newlen;
            if (lhs.exp > rhs.exp) {
               newlen = usellen + lhs.exp - rhs.exp;
               if (newlen >= userlen + reqdig + 1 && reqdig > 0) {
                  res.mant = usel;
                  res.exp = lhs.exp;
                  res.ind = lhs.ind;
                  if (usellen < reqdig) {
                     res.mant = extend(lhs.mant, reqdig);
                     res.exp -= reqdig - usellen;
                  }

                  return res.finish(set, false);
               }

               res.exp = rhs.exp;
               if (newlen > reqdig + 1 && reqdig > 0) {
                  tlen = newlen - reqdig - 1;
                  userlen -= tlen;
                  res.exp += tlen;
                  newlen = reqdig + 1;
               }

               if (newlen > usellen) {
                  usellen = newlen;
               }
            } else {
               newlen = userlen + rhs.exp - lhs.exp;
               if (newlen >= usellen + reqdig + 1 && reqdig > 0) {
                  res.mant = user;
                  res.exp = rhs.exp;
                  res.ind = rhs.ind;
                  if (userlen < reqdig) {
                     res.mant = extend(rhs.mant, reqdig);
                     res.exp -= reqdig - userlen;
                  }

                  return res.finish(set, false);
               }

               res.exp = lhs.exp;
               if (newlen > reqdig + 1 && reqdig > 0) {
                  tlen = newlen - reqdig - 1;
                  usellen -= tlen;
                  res.exp += tlen;
                  newlen = reqdig + 1;
               }

               if (newlen > userlen) {
                  userlen = newlen;
               }
            }
         }

         if (lhs.ind == 0) {
            res.ind = 1;
         } else {
            res.ind = lhs.ind;
         }

         byte mult;
         if (lhs.ind == -1 == (rhs.ind == -1)) {
            mult = 1;
         } else {
            mult = -1;
            if (rhs.ind != 0) {
               byte[] t;
               if (usellen < userlen | lhs.ind == 0) {
                  t = usel;
                  usel = user;
                  user = t;
                  tlen = usellen;
                  usellen = userlen;
                  userlen = tlen;
                  res.ind = (byte)(-res.ind);
               } else if (usellen <= userlen) {
                  int ia = 0;
                  int ib = 0;
                  int ea = usel.length - 1;
                  int eb = user.length - 1;

                  while(true) {
                     byte ca;
                     if (ia <= ea) {
                        ca = usel[ia];
                     } else {
                        if (ib > eb) {
                           if (set.form != 0) {
                              return ZERO;
                           }
                           break;
                        }

                        ca = 0;
                     }

                     byte cb;
                     if (ib <= eb) {
                        cb = user[ib];
                     } else {
                        cb = 0;
                     }

                     if (ca != cb) {
                        if (ca < cb) {
                           t = usel;
                           usel = user;
                           user = t;
                           tlen = usellen;
                           usellen = userlen;
                           userlen = tlen;
                           res.ind = (byte)(-res.ind);
                        }
                        break;
                     }

                     ++ia;
                     ++ib;
                  }
               }
            }
         }

         res.mant = byteaddsub(usel, usellen, user, userlen, mult, false);
         return res.finish(set, false);
      }
   }

   public int compareTo(BigDecimal rhs) {
      return this.compareTo(rhs, plainMC);
   }

   public int compareTo(BigDecimal rhs, MathContext set) {
      int thislength = false;
      int i = false;
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      if (this.ind == rhs.ind & this.exp == rhs.exp) {
         int thislength = this.mant.length;
         if (thislength < rhs.mant.length) {
            return (byte)(-this.ind);
         }

         if (thislength > rhs.mant.length) {
            return this.ind;
         }

         if (thislength <= set.digits | set.digits == 0) {
            int $6 = thislength;

            for(int i = 0; $6 > 0; ++i) {
               if (this.mant[i] < rhs.mant[i]) {
                  return (byte)(-this.ind);
               }

               if (this.mant[i] > rhs.mant[i]) {
                  return this.ind;
               }

               --$6;
            }

            return 0;
         }
      } else {
         if (this.ind < rhs.ind) {
            return -1;
         }

         if (this.ind > rhs.ind) {
            return 1;
         }
      }

      BigDecimal newrhs = clone(rhs);
      newrhs.ind = (byte)(-newrhs.ind);
      return this.add(newrhs, set).ind;
   }

   public BigDecimal divide(BigDecimal rhs) {
      return this.dodivide('D', rhs, plainMC, -1);
   }

   public BigDecimal divide(BigDecimal rhs, int round) {
      MathContext set = new MathContext(0, 0, false, round);
      return this.dodivide('D', rhs, set, -1);
   }

   public BigDecimal divide(BigDecimal rhs, int scale, int round) {
      if (scale < 0) {
         throw new ArithmeticException("Negative scale: " + scale);
      } else {
         MathContext set = new MathContext(0, 0, false, round);
         return this.dodivide('D', rhs, set, scale);
      }
   }

   public BigDecimal divide(BigDecimal rhs, MathContext set) {
      return this.dodivide('D', rhs, set, -1);
   }

   public BigDecimal divideInteger(BigDecimal rhs) {
      return this.dodivide('I', rhs, plainMC, 0);
   }

   public BigDecimal divideInteger(BigDecimal rhs, MathContext set) {
      return this.dodivide('I', rhs, set, 0);
   }

   public BigDecimal max(BigDecimal rhs) {
      return this.max(rhs, plainMC);
   }

   public BigDecimal max(BigDecimal rhs, MathContext set) {
      return this.compareTo(rhs, set) >= 0 ? this.plus(set) : rhs.plus(set);
   }

   public BigDecimal min(BigDecimal rhs) {
      return this.min(rhs, plainMC);
   }

   public BigDecimal min(BigDecimal rhs, MathContext set) {
      return this.compareTo(rhs, set) <= 0 ? this.plus(set) : rhs.plus(set);
   }

   public BigDecimal multiply(BigDecimal rhs) {
      return this.multiply(rhs, plainMC);
   }

   public BigDecimal multiply(BigDecimal rhs, MathContext set) {
      byte[] multer = null;
      byte[] multand = null;
      int acclen = false;
      int n = false;
      byte mult = false;
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      BigDecimal lhs = this;
      int padding = 0;
      int reqdig = set.digits;
      if (reqdig > 0) {
         if (this.mant.length > reqdig) {
            lhs = clone(this).round(set);
         }

         if (rhs.mant.length > reqdig) {
            rhs = clone(rhs).round(set);
         }
      } else {
         if (this.exp > 0) {
            padding += this.exp;
         }

         if (rhs.exp > 0) {
            padding += rhs.exp;
         }
      }

      byte[] multer;
      byte[] multand;
      if (lhs.mant.length < rhs.mant.length) {
         multer = lhs.mant;
         multand = rhs.mant;
      } else {
         multer = rhs.mant;
         multand = lhs.mant;
      }

      int multandlen = multer.length + multand.length - 1;
      int acclen;
      if (multer[0] * multand[0] > 9) {
         acclen = multandlen + 1;
      } else {
         acclen = multandlen;
      }

      BigDecimal res = new BigDecimal();
      byte[] acc = new byte[acclen];
      int $7 = multer.length;

      for(int n = 0; $7 > 0; ++n) {
         byte mult = multer[n];
         if (mult != 0) {
            acc = byteaddsub(acc, acc.length, multand, multandlen, mult, true);
         }

         --multandlen;
         --$7;
      }

      res.ind = (byte)(lhs.ind * rhs.ind);
      res.exp = lhs.exp + rhs.exp - padding;
      if (padding == 0) {
         res.mant = acc;
      } else {
         res.mant = extend(acc, acc.length + padding);
      }

      return res.finish(set, false);
   }

   public BigDecimal negate() {
      return this.negate(plainMC);
   }

   public BigDecimal negate(MathContext set) {
      if (set.lostDigits) {
         this.checkdigits((BigDecimal)null, set.digits);
      }

      BigDecimal res = clone(this);
      res.ind = (byte)(-res.ind);
      return res.finish(set, false);
   }

   public BigDecimal plus() {
      return this.plus(plainMC);
   }

   public BigDecimal plus(MathContext set) {
      if (set.lostDigits) {
         this.checkdigits((BigDecimal)null, set.digits);
      }

      if (set.form == 0 && this.form == 0) {
         if (this.mant.length <= set.digits) {
            return this;
         }

         if (set.digits == 0) {
            return this;
         }
      }

      return clone(this).finish(set, false);
   }

   public BigDecimal pow(BigDecimal rhs) {
      return this.pow(rhs, plainMC);
   }

   public BigDecimal pow(BigDecimal rhs, MathContext set) {
      int workdigits = false;
      int L = false;
      int i = false;
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      int n = rhs.intcheck(-999999999, 999999999);
      BigDecimal lhs = this;
      int reqdig = set.digits;
      int workdigits;
      if (reqdig == 0) {
         if (rhs.ind == -1) {
            throw new ArithmeticException("Negative power: " + rhs.toString());
         }

         workdigits = 0;
      } else {
         if (rhs.mant.length + rhs.exp > reqdig) {
            throw new ArithmeticException("Too many digits: " + rhs.toString());
         }

         if (this.mant.length > reqdig) {
            lhs = clone(this).round(set);
         }

         int L = rhs.mant.length + rhs.exp;
         workdigits = reqdig + L + 1;
      }

      MathContext workset = new MathContext(workdigits, set.form, false, set.roundingMode);
      BigDecimal res = ONE;
      if (n == 0) {
         return res;
      } else {
         if (n < 0) {
            n = -n;
         }

         boolean seenbit = false;
         int i = 1;

         while(true) {
            n += n;
            if (n < 0) {
               seenbit = true;
               res = res.multiply(lhs, workset);
            }

            if (i == 31) {
               if (rhs.ind < 0) {
                  res = ONE.divide(res, workset);
               }

               return res.finish(set, true);
            }

            if (seenbit) {
               res = res.multiply(res, workset);
            }

            ++i;
         }
      }
   }

   public BigDecimal remainder(BigDecimal rhs) {
      return this.dodivide('R', rhs, plainMC, -1);
   }

   public BigDecimal remainder(BigDecimal rhs, MathContext set) {
      return this.dodivide('R', rhs, set, -1);
   }

   public BigDecimal subtract(BigDecimal rhs) {
      return this.subtract(rhs, plainMC);
   }

   public BigDecimal subtract(BigDecimal rhs, MathContext set) {
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      BigDecimal newrhs = clone(rhs);
      newrhs.ind = (byte)(-newrhs.ind);
      return this.add(newrhs, set);
   }

   public byte byteValueExact() {
      int num = this.intValueExact();
      if (num > 127 | num < -128) {
         throw new ArithmeticException("Conversion overflow: " + this.toString());
      } else {
         return (byte)num;
      }
   }

   public double doubleValue() {
      return Double.valueOf(this.toString());
   }

   public boolean equals(Object obj) {
      int i = false;
      char[] lca = null;
      char[] rca = null;
      if (obj == null) {
         return false;
      } else if (!(obj instanceof BigDecimal)) {
         return false;
      } else {
         BigDecimal rhs = (BigDecimal)obj;
         if (this.ind != rhs.ind) {
            return false;
         } else {
            int $9;
            int i;
            if (this.mant.length == rhs.mant.length & this.exp == rhs.exp & this.form == rhs.form) {
               $9 = this.mant.length;

               for(i = 0; $9 > 0; ++i) {
                  if (this.mant[i] != rhs.mant[i]) {
                     return false;
                  }

                  --$9;
               }
            } else {
               char[] lca = this.layout();
               char[] rca = rhs.layout();
               if (lca.length != rca.length) {
                  return false;
               }

               $9 = lca.length;

               for(i = 0; $9 > 0; ++i) {
                  if (lca[i] != rca[i]) {
                     return false;
                  }

                  --$9;
               }
            }

            return true;
         }
      }
   }

   public float floatValue() {
      return Float.valueOf(this.toString());
   }

   public String format(int before, int after) {
      return this.format(before, after, -1, -1, 1, 4);
   }

   public String format(int before, int after, int explaces, int exdigits, int exformint, int exround) {
      int mag = false;
      int thisafter = false;
      int lead = false;
      byte[] newmant = null;
      int chop = false;
      int need = false;
      int oldexp = false;
      int p = false;
      char[] newa = null;
      int i = false;
      int places = false;
      if (before < -1 | before == 0) {
         this.badarg("format", 1, String.valueOf(before));
      }

      if (after < -1) {
         this.badarg("format", 2, String.valueOf(after));
      }

      if (explaces < -1 | explaces == 0) {
         this.badarg("format", 3, String.valueOf(explaces));
      }

      if (exdigits < -1) {
         this.badarg("format", 4, String.valueOf(explaces));
      }

      if (exformint != 1 && exformint != 2) {
         if (exformint == -1) {
            exformint = 1;
         } else {
            this.badarg("format", 5, String.valueOf(exformint));
         }
      }

      if (exround != 4) {
         try {
            if (exround == -1) {
               exround = 4;
            } else {
               new MathContext(9, 1, false, exround);
            }
         } catch (IllegalArgumentException var21) {
            this.badarg("format", 6, String.valueOf(exround));
         }
      }

      BigDecimal num = clone(this);
      if (exdigits == -1) {
         num.form = 0;
      } else if (num.ind == 0) {
         num.form = 0;
      } else {
         int mag = num.exp + num.mant.length;
         if (mag > exdigits) {
            num.form = (byte)exformint;
         } else if (mag < -5) {
            num.form = (byte)exformint;
         } else {
            num.form = 0;
         }
      }

      if (after >= 0) {
         while(true) {
            int thisafter;
            if (num.form == 0) {
               thisafter = -num.exp;
            } else if (num.form == 1) {
               thisafter = num.mant.length - 1;
            } else {
               int lead = (num.exp + num.mant.length - 1) % 3;
               if (lead < 0) {
                  lead += 3;
               }

               ++lead;
               if (lead >= num.mant.length) {
                  thisafter = 0;
               } else {
                  thisafter = num.mant.length - lead;
               }
            }

            if (thisafter == after) {
               break;
            }

            if (thisafter < after) {
               byte[] newmant = extend(num.mant, num.mant.length + after - thisafter);
               num.mant = newmant;
               num.exp -= after - thisafter;
               if (num.exp < -999999999) {
                  throw new ArithmeticException("Exponent Overflow: " + num.exp);
               }
               break;
            }

            int chop = thisafter - after;
            if (chop > num.mant.length) {
               num.mant = ZERO.mant;
               num.ind = 0;
               num.exp = 0;
            } else {
               int need = num.mant.length - chop;
               int oldexp = num.exp;
               num.round(need, exround);
               if (num.exp - oldexp == chop) {
                  break;
               }
            }
         }
      }

      char[] a = num.layout();
      int $15;
      int p;
      char[] newa;
      int i;
      if (before > 0) {
         $15 = a.length;

         for(p = 0; $15 > 0 && a[p] != '.' && a[p] != 'E'; ++p) {
            --$15;
         }

         if (p > before) {
            this.badarg("format", 1, String.valueOf(before));
         }

         if (p < before) {
            newa = new char[a.length + before - p];
            $15 = before - p;

            for(i = 0; $15 > 0; ++i) {
               newa[i] = ' ';
               --$15;
            }

            System.arraycopy(a, 0, newa, i, a.length);
            a = newa;
         }
      }

      if (explaces > 0) {
         $15 = a.length - 1;

         for(p = a.length - 1; $15 > 0 && a[p] != 'E'; --p) {
            --$15;
         }

         if (p == 0) {
            newa = new char[a.length + explaces + 2];
            System.arraycopy(a, 0, newa, 0, a.length);
            $15 = explaces + 2;

            for(i = a.length; $15 > 0; ++i) {
               newa[i] = ' ';
               --$15;
            }

            a = newa;
         } else {
            int places = a.length - p - 2;
            if (places > explaces) {
               this.badarg("format", 3, String.valueOf(explaces));
            }

            if (places < explaces) {
               newa = new char[a.length + explaces - places];
               System.arraycopy(a, 0, newa, 0, p + 2);
               $15 = explaces - places;

               for(i = p + 2; $15 > 0; ++i) {
                  newa[i] = '0';
                  --$15;
               }

               System.arraycopy(a, p + 2, newa, i, places);
               a = newa;
            }
         }
      }

      return new String(a);
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public int intValue() {
      return this.toBigInteger().intValue();
   }

   public int intValueExact() {
      int useexp = false;
      int i = false;
      int topdig = false;
      if (this.ind == 0) {
         return 0;
      } else {
         int lodigit = this.mant.length - 1;
         int useexp;
         if (this.exp < 0) {
            lodigit += this.exp;
            if (!allzero(this.mant, lodigit + 1)) {
               throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }

            if (lodigit < 0) {
               return 0;
            }

            useexp = 0;
         } else {
            if (this.exp + lodigit > 9) {
               throw new ArithmeticException("Conversion overflow: " + this.toString());
            }

            useexp = this.exp;
         }

         int result = 0;
         int $16 = lodigit + useexp;

         for(int i = 0; i <= $16; ++i) {
            result *= 10;
            if (i <= lodigit) {
               result += this.mant[i];
            }
         }

         if (lodigit + useexp == 9) {
            int topdig = result / 1000000000;
            if (topdig != this.mant[0]) {
               if (result == Integer.MIN_VALUE && this.ind == -1 && this.mant[0] == 2) {
                  return result;
               }

               throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
         }

         if (this.ind == 1) {
            return result;
         } else {
            return -result;
         }
      }
   }

   public long longValue() {
      return this.toBigInteger().longValue();
   }

   public long longValueExact() {
      int cstart = false;
      int useexp = false;
      int i = false;
      long topdig = 0L;
      if (this.ind == 0) {
         return 0L;
      } else {
         int lodigit = this.mant.length - 1;
         int useexp;
         if (this.exp < 0) {
            lodigit += this.exp;
            int cstart;
            if (lodigit < 0) {
               cstart = 0;
            } else {
               cstart = lodigit + 1;
            }

            if (!allzero(this.mant, cstart)) {
               throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }

            if (lodigit < 0) {
               return 0L;
            }

            useexp = 0;
         } else {
            if (this.exp + this.mant.length > 18) {
               throw new ArithmeticException("Conversion overflow: " + this.toString());
            }

            useexp = this.exp;
         }

         long result = 0L;
         int $17 = lodigit + useexp;

         for(int i = 0; i <= $17; ++i) {
            result *= 10L;
            if (i <= lodigit) {
               result += (long)this.mant[i];
            }
         }

         if (lodigit + useexp == 18) {
            topdig = result / 1000000000000000000L;
            if (topdig != (long)this.mant[0]) {
               if (result == Long.MIN_VALUE && this.ind == -1 && this.mant[0] == 9) {
                  return result;
               }

               throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
         }

         if (this.ind == 1) {
            return result;
         } else {
            return -result;
         }
      }
   }

   public BigDecimal movePointLeft(int n) {
      BigDecimal res = clone(this);
      res.exp -= n;
      return res.finish(plainMC, false);
   }

   public BigDecimal movePointRight(int n) {
      BigDecimal res = clone(this);
      res.exp += n;
      return res.finish(plainMC, false);
   }

   public int scale() {
      return this.exp >= 0 ? 0 : -this.exp;
   }

   public BigDecimal setScale(int scale) {
      return this.setScale(scale, 7);
   }

   public BigDecimal setScale(int scale, int round) {
      int padding = false;
      int newlen = false;
      int ourscale = this.scale();
      if (ourscale == scale && this.form == 0) {
         return this;
      } else {
         BigDecimal res = clone(this);
         if (ourscale <= scale) {
            int padding;
            if (ourscale == 0) {
               padding = res.exp + scale;
            } else {
               padding = scale - ourscale;
            }

            res.mant = extend(res.mant, res.mant.length + padding);
            res.exp = -scale;
         } else {
            if (scale < 0) {
               throw new ArithmeticException("Negative scale: " + scale);
            }

            int newlen = res.mant.length - (ourscale - scale);
            res = res.round(newlen, round);
            if (res.exp != -scale) {
               res.mant = extend(res.mant, res.mant.length + 1);
               --res.exp;
            }
         }

         res.form = 0;
         return res;
      }
   }

   public short shortValueExact() {
      int num = this.intValueExact();
      if (num > 32767 | num < -32768) {
         throw new ArithmeticException("Conversion overflow: " + this.toString());
      } else {
         return (short)num;
      }
   }

   public int signum() {
      return this.ind;
   }

   public java.math.BigDecimal toBigDecimal() {
      return new java.math.BigDecimal(this.unscaledValue(), this.scale());
   }

   public BigInteger toBigInteger() {
      BigDecimal res = null;
      int newlen = false;
      byte[] newmant = null;
      if (this.exp >= 0 & this.form == 0) {
         res = this;
      } else if (this.exp >= 0) {
         res = clone(this);
         res.form = 0;
      } else if (-this.exp >= this.mant.length) {
         res = ZERO;
      } else {
         res = clone(this);
         int newlen = res.mant.length + res.exp;
         byte[] newmant = new byte[newlen];
         System.arraycopy(res.mant, 0, newmant, 0, newlen);
         res.mant = newmant;
         res.form = 0;
         res.exp = 0;
      }

      return new BigInteger(new String(res.layout()));
   }

   public BigInteger toBigIntegerExact() {
      if (this.exp < 0 && !allzero(this.mant, this.mant.length + this.exp)) {
         throw new ArithmeticException("Decimal part non-zero: " + this.toString());
      } else {
         return this.toBigInteger();
      }
   }

   public char[] toCharArray() {
      return this.layout();
   }

   public String toString() {
      return new String(this.layout());
   }

   public BigInteger unscaledValue() {
      BigDecimal res = null;
      if (this.exp >= 0) {
         res = this;
      } else {
         res = clone(this);
         res.exp = 0;
      }

      return res.toBigInteger();
   }

   public static BigDecimal valueOf(double dub) {
      return new BigDecimal((new Double(dub)).toString());
   }

   public static BigDecimal valueOf(long lint) {
      return valueOf(lint, 0);
   }

   public static BigDecimal valueOf(long lint, int scale) {
      BigDecimal res = null;
      if (lint == 0L) {
         res = ZERO;
      } else if (lint == 1L) {
         res = ONE;
      } else if (lint == 10L) {
         res = TEN;
      } else {
         res = new BigDecimal(lint);
      }

      if (scale == 0) {
         return res;
      } else if (scale < 0) {
         throw new NumberFormatException("Negative scale: " + scale);
      } else {
         res = clone(res);
         res.exp = -scale;
         return res;
      }
   }

   private char[] layout() {
      int i = false;
      StringBuilder sb = null;
      int euse = false;
      int sig = false;
      char csign = false;
      char[] rec = null;
      int len = false;
      char[] cmant = new char[this.mant.length];
      int $21 = this.mant.length;

      int i;
      for(i = 0; $21 > 0; ++i) {
         cmant[i] = (char)(this.mant[i] + 48);
         --$21;
      }

      char[] rec;
      if (this.form != 0) {
         sb = new StringBuilder(cmant.length + 15);
         if (this.ind == -1) {
            sb.append('-');
         }

         int euse = this.exp + cmant.length - 1;
         if (this.form == 1) {
            sb.append(cmant[0]);
            if (cmant.length > 1) {
               sb.append('.').append(cmant, 1, cmant.length - 1);
            }
         } else {
            int sig = euse % 3;
            if (sig < 0) {
               sig += 3;
            }

            euse -= sig;
            ++sig;
            if (sig >= cmant.length) {
               sb.append(cmant, 0, cmant.length);

               for($21 = sig - cmant.length; $21 > 0; --$21) {
                  sb.append('0');
               }
            } else {
               sb.append(cmant, 0, sig).append('.').append(cmant, sig, cmant.length - sig);
            }
         }

         if (euse != 0) {
            char csign;
            if (euse < 0) {
               csign = '-';
               euse = -euse;
            } else {
               csign = '+';
            }

            sb.append('E').append(csign).append(euse);
         }

         rec = new char[sb.length()];
         $21 = sb.length();
         if (0 != $21) {
            sb.getChars(0, $21, rec, 0);
         }

         return rec;
      } else if (this.exp == 0) {
         if (this.ind >= 0) {
            return cmant;
         } else {
            rec = new char[cmant.length + 1];
            rec[0] = '-';
            System.arraycopy(cmant, 0, rec, 1, cmant.length);
            return rec;
         }
      } else {
         int needsign = this.ind == -1 ? 1 : 0;
         int mag = this.exp + cmant.length;
         int len;
         if (mag < 1) {
            len = needsign + 2 - this.exp;
            rec = new char[len];
            if (needsign != 0) {
               rec[0] = '-';
            }

            rec[needsign] = '0';
            rec[needsign + 1] = '.';
            $21 = -mag;

            for(i = needsign + 2; $21 > 0; ++i) {
               rec[i] = '0';
               --$21;
            }

            System.arraycopy(cmant, 0, rec, needsign + 2 - mag, cmant.length);
            return rec;
         } else if (mag <= cmant.length) {
            len = needsign + 1 + cmant.length;
            rec = new char[len];
            if (needsign != 0) {
               rec[0] = '-';
            }

            System.arraycopy(cmant, 0, rec, needsign, mag);
            rec[needsign + mag] = '.';
            System.arraycopy(cmant, mag, rec, needsign + mag + 1, cmant.length - mag);
            return rec;
         } else {
            len = needsign + mag;
            rec = new char[len];
            if (needsign != 0) {
               rec[0] = '-';
            }

            System.arraycopy(cmant, 0, rec, needsign, cmant.length);
            $21 = mag - cmant.length;

            for(i = needsign + cmant.length; $21 > 0; ++i) {
               rec[i] = '0';
               --$21;
            }

            return rec;
         }
      }
   }

   private int intcheck(int min, int max) {
      int i = this.intValueExact();
      if (i < min | i > max) {
         throw new ArithmeticException("Conversion overflow: " + i);
      } else {
         return i;
      }
   }

   private BigDecimal dodivide(char code, BigDecimal rhs, MathContext set, int scale) {
      int thisdigit = false;
      int i = false;
      byte v2 = false;
      int ba = false;
      int mult = false;
      int start = false;
      int padding = false;
      int d = false;
      byte[] newvar1 = null;
      byte lasthave = false;
      int actdig = false;
      byte[] newmant = null;
      if (set.lostDigits) {
         this.checkdigits(rhs, set.digits);
      }

      BigDecimal lhs = this;
      if (rhs.ind == 0) {
         throw new ArithmeticException("Divide by 0");
      } else if (this.ind == 0) {
         if (set.form != 0) {
            return ZERO;
         } else {
            return scale == -1 ? this : this.setScale(scale);
         }
      } else {
         int reqdig = set.digits;
         if (reqdig > 0) {
            if (this.mant.length > reqdig) {
               lhs = clone(this).round(set);
            }

            if (rhs.mant.length > reqdig) {
               rhs = clone(rhs).round(set);
            }
         } else {
            if (scale == -1) {
               scale = this.scale();
            }

            reqdig = this.mant.length;
            if (scale != -this.exp) {
               reqdig = reqdig + scale + this.exp;
            }

            reqdig = reqdig - (rhs.mant.length - 1) - rhs.exp;
            if (reqdig < this.mant.length) {
               reqdig = this.mant.length;
            }

            if (reqdig < rhs.mant.length) {
               reqdig = rhs.mant.length;
            }
         }

         int newexp = lhs.exp - rhs.exp + lhs.mant.length - rhs.mant.length;
         if (newexp < 0 && code != 'D') {
            return code == 'I' ? ZERO : clone(lhs).finish(set, false);
         } else {
            BigDecimal res = new BigDecimal();
            res.ind = (byte)(lhs.ind * rhs.ind);
            res.exp = newexp;
            res.mant = new byte[reqdig + 1];
            int newlen = reqdig + reqdig + 1;
            byte[] var1 = extend(lhs.mant, newlen);
            int var1len = newlen;
            byte[] var2 = rhs.mant;
            int var2len = newlen;
            int b2b = var2[0] * 10 + 1;
            if (var2.length > 1) {
               b2b += var2[1];
            }

            int have = 0;

            int i;
            label255:
            while(true) {
               int thisdigit = 0;

               while(true) {
                  int $23;
                  int ba;
                  label287: {
                     if (var1len >= var2len) {
                        label272: {
                           if (var1len != var2len) {
                              ba = var1[0] * 10;
                              if (var1len > 1) {
                                 ba += var1[1];
                              }
                              break label287;
                           }

                           $23 = var1len;

                           for(i = 0; $23 > 0; ++i) {
                              byte v2;
                              if (i < var2.length) {
                                 v2 = var2[i];
                              } else {
                                 v2 = 0;
                              }

                              if (var1[i] < v2) {
                                 break label272;
                              }

                              if (var1[i] > v2) {
                                 ba = var1[0];
                                 break label287;
                              }

                              --$23;
                           }

                           ++thisdigit;
                           res.mant[have] = (byte)thisdigit;
                           ++have;
                           var1[0] = 0;
                           break label255;
                        }
                     }

                     if (have != 0 | thisdigit != 0) {
                        res.mant[have] = (byte)thisdigit;
                        ++have;
                        if (have == reqdig + 1 || var1[0] == 0) {
                           break label255;
                        }
                     }

                     if (scale >= 0 && -res.exp > scale || code != 'D' && res.exp <= 0) {
                        break label255;
                     }

                     --res.exp;
                     --var2len;
                     break;
                  }

                  int mult = ba * 10 / b2b;
                  if (mult == 0) {
                     mult = 1;
                  }

                  thisdigit += mult;
                  var1 = byteaddsub(var1, var1len, var2, var2len, -mult, true);
                  if (var1[0] == 0) {
                     $23 = var1len - 2;

                     int start;
                     for(start = 0; start <= $23 && var1[start] == 0; ++start) {
                        --var1len;
                     }

                     if (start != 0) {
                        System.arraycopy(var1, start, var1, 0, var1len);
                     }
                  }
               }
            }

            if (have == 0) {
               have = 1;
            }

            if (code == 'I' | code == 'R') {
               if (have + res.exp > reqdig) {
                  throw new ArithmeticException("Integer overflow");
               }

               if (code == 'R') {
                  if (res.mant[0] == 0) {
                     return clone(lhs).finish(set, false);
                  }

                  if (var1[0] == 0) {
                     return ZERO;
                  }

                  res.ind = lhs.ind;
                  int padding = reqdig + reqdig + 1 - lhs.mant.length;
                  res.exp = res.exp - padding + lhs.exp;
                  int d = var1len;

                  for(i = var1len - 1; i >= 1 && res.exp < lhs.exp & res.exp < rhs.exp && var1[i] == 0; --i) {
                     --d;
                     ++res.exp;
                  }

                  if (d < var1.length) {
                     byte[] newvar1 = new byte[d];
                     System.arraycopy(var1, 0, newvar1, 0, d);
                     var1 = newvar1;
                  }

                  res.mant = var1;
                  return res.finish(set, false);
               }
            } else if (var1[0] != 0) {
               byte lasthave = res.mant[have - 1];
               if (lasthave % 5 == 0) {
                  res.mant[have - 1] = (byte)(lasthave + 1);
               }
            }

            if (scale >= 0) {
               if (have != res.mant.length) {
                  res.exp -= res.mant.length - have;
               }

               int actdig = res.mant.length - (-res.exp - scale);
               res.round(actdig, set.roundingMode);
               if (res.exp != -scale) {
                  res.mant = extend(res.mant, res.mant.length + 1);
                  --res.exp;
               }

               return res.finish(set, true);
            } else {
               if (have == res.mant.length) {
                  res.round(set);
               } else {
                  if (res.mant[0] == 0) {
                     return ZERO;
                  }

                  byte[] newmant = new byte[have];
                  System.arraycopy(res.mant, 0, newmant, 0, have);
                  res.mant = newmant;
               }

               return res.finish(set, true);
            }
         }
      }
   }

   private void bad(char[] s) {
      throw new NumberFormatException("Not a number: " + String.valueOf(s));
   }

   private void badarg(String name, int pos, String value) {
      throw new IllegalArgumentException("Bad argument " + pos + " to " + name + ": " + value);
   }

   private static final byte[] extend(byte[] inarr, int newlen) {
      if (inarr.length == newlen) {
         return inarr;
      } else {
         byte[] newarr = new byte[newlen];
         System.arraycopy(inarr, 0, newarr, 0, inarr.length);
         return newarr;
      }
   }

   private static final byte[] byteaddsub(byte[] a, int avlen, byte[] b, int bvlen, int m, boolean reuse) {
      int op = false;
      int dp90 = false;
      int i = false;
      int alength = a.length;
      int blength = b.length;
      int ap = avlen - 1;
      int bp = bvlen - 1;
      int maxarr = bp;
      if (bp < ap) {
         maxarr = ap;
      }

      byte[] reb = null;
      if (reuse && maxarr + 1 == alength) {
         reb = a;
      }

      if (reb == null) {
         reb = new byte[maxarr + 1];
      }

      boolean quickm = false;
      if (m == 1) {
         quickm = true;
      } else if (m == -1) {
         quickm = true;
      }

      int digit = 0;

      for(int op = maxarr; op >= 0; --op) {
         if (ap >= 0) {
            if (ap < alength) {
               digit += a[ap];
            }

            --ap;
         }

         if (bp >= 0) {
            if (bp < blength) {
               if (quickm) {
                  if (m > 0) {
                     digit += b[bp];
                  } else {
                     digit -= b[bp];
                  }
               } else {
                  digit += b[bp] * m;
               }
            }

            --bp;
         }

         if (digit < 10 && digit >= 0) {
            reb[op] = (byte)digit;
            digit = 0;
         } else {
            int dp90 = digit + 90;
            reb[op] = bytedig[dp90];
            digit = bytecar[dp90];
         }
      }

      if (digit == 0) {
         return reb;
      } else {
         byte[] newarr = null;
         if (reuse && maxarr + 2 == a.length) {
            newarr = a;
         }

         if (newarr == null) {
            newarr = new byte[maxarr + 2];
         }

         newarr[0] = (byte)digit;
         if (maxarr < 10) {
            int $24 = maxarr + 1;

            for(int i = 0; $24 > 0; ++i) {
               newarr[i + 1] = reb[i];
               --$24;
            }
         } else {
            System.arraycopy(reb, 0, newarr, 1, maxarr + 1);
         }

         return newarr;
      }
   }

   private static final byte[] diginit() {
      int op = false;
      int digit = false;
      byte[] work = new byte[190];

      for(int op = 0; op <= 189; ++op) {
         int digit = op - 90;
         if (digit >= 0) {
            work[op] = (byte)(digit % 10);
            bytecar[op] = (byte)(digit / 10);
         } else {
            digit += 100;
            work[op] = (byte)(digit % 10);
            bytecar[op] = (byte)(digit / 10 - 10);
         }
      }

      return work;
   }

   private static final BigDecimal clone(BigDecimal dec) {
      BigDecimal copy = new BigDecimal();
      copy.ind = dec.ind;
      copy.exp = dec.exp;
      copy.form = dec.form;
      copy.mant = dec.mant;
      return copy;
   }

   private void checkdigits(BigDecimal rhs, int dig) {
      if (dig != 0) {
         if (this.mant.length > dig && !allzero(this.mant, dig)) {
            throw new ArithmeticException("Too many digits: " + this.toString());
         } else if (rhs != null) {
            if (rhs.mant.length > dig && !allzero(rhs.mant, dig)) {
               throw new ArithmeticException("Too many digits: " + rhs.toString());
            }
         }
      }
   }

   private BigDecimal round(MathContext set) {
      return this.round(set.digits, set.roundingMode);
   }

   private BigDecimal round(int len, int mode) {
      boolean reuse = false;
      byte first = false;
      byte[] newmant = null;
      int adjust = this.mant.length - len;
      if (adjust <= 0) {
         return this;
      } else {
         this.exp += adjust;
         int sign = this.ind;
         byte[] oldmant = this.mant;
         byte first;
         if (len > 0) {
            this.mant = new byte[len];
            System.arraycopy(oldmant, 0, this.mant, 0, len);
            reuse = true;
            first = oldmant[len];
         } else {
            this.mant = ZERO.mant;
            this.ind = 0;
            reuse = false;
            if (len == 0) {
               first = oldmant[0];
            } else {
               first = 0;
            }
         }

         int increment = 0;
         if (mode == 4) {
            if (first >= 5) {
               increment = sign;
            }
         } else if (mode == 7) {
            if (!allzero(oldmant, len)) {
               throw new ArithmeticException("Rounding necessary");
            }
         } else if (mode == 5) {
            if (first > 5) {
               increment = sign;
            } else if (first == 5 && !allzero(oldmant, len + 1)) {
               increment = sign;
            }
         } else if (mode == 6) {
            if (first > 5) {
               increment = sign;
            } else if (first == 5) {
               if (!allzero(oldmant, len + 1)) {
                  increment = sign;
               } else if (this.mant[this.mant.length - 1] % 2 != 0) {
                  increment = sign;
               }
            }
         } else if (mode != 1) {
            if (mode == 0) {
               if (!allzero(oldmant, len)) {
                  increment = sign;
               }
            } else if (mode == 2) {
               if (sign > 0 && !allzero(oldmant, len)) {
                  increment = sign;
               }
            } else {
               if (mode != 3) {
                  throw new IllegalArgumentException("Bad round value: " + mode);
               }

               if (sign < 0 && !allzero(oldmant, len)) {
                  increment = sign;
               }
            }
         }

         if (increment != 0) {
            if (this.ind == 0) {
               this.mant = ONE.mant;
               this.ind = (byte)increment;
            } else {
               if (this.ind == -1) {
                  increment = -increment;
               }

               byte[] newmant = byteaddsub(this.mant, this.mant.length, ONE.mant, 1, increment, reuse);
               if (newmant.length > this.mant.length) {
                  ++this.exp;
                  System.arraycopy(newmant, 0, this.mant, 0, this.mant.length);
               } else {
                  this.mant = newmant;
               }
            }
         }

         if (this.exp > 999999999) {
            throw new ArithmeticException("Exponent Overflow: " + this.exp);
         } else {
            return this;
         }
      }
   }

   private static final boolean allzero(byte[] array, int start) {
      int i = false;
      if (start < 0) {
         start = 0;
      }

      int $25 = array.length - 1;

      for(int i = start; i <= $25; ++i) {
         if (array[i] != 0) {
            return false;
         }
      }

      return true;
   }

   private BigDecimal finish(MathContext set, boolean strip) {
      int d = false;
      int i = false;
      byte[] newmant = null;
      int mag = false;
      int sig = false;
      if (set.digits != 0 && this.mant.length > set.digits) {
         this.round(set);
      }

      int i;
      byte[] newmant;
      if (strip && set.form != 0) {
         int d = this.mant.length;

         for(i = d - 1; i >= 1 && this.mant[i] == 0; --i) {
            --d;
            ++this.exp;
         }

         if (d < this.mant.length) {
            newmant = new byte[d];
            System.arraycopy(this.mant, 0, newmant, 0, d);
            this.mant = newmant;
         }
      }

      this.form = 0;
      int $26 = this.mant.length;

      for(i = 0; $26 > 0; ++i) {
         if (this.mant[i] != 0) {
            if (i > 0) {
               newmant = new byte[this.mant.length - i];
               System.arraycopy(this.mant, i, newmant, 0, this.mant.length - i);
               this.mant = newmant;
            }

            int mag = this.exp + this.mant.length;
            if (mag > 0) {
               if (mag > set.digits && set.digits != 0) {
                  this.form = (byte)set.form;
               }

               if (mag - 1 <= 999999999) {
                  return this;
               }
            } else if (mag < -5) {
               this.form = (byte)set.form;
            }

            --mag;
            if (mag < -999999999 | mag > 999999999) {
               if (this.form != 2) {
                  throw new ArithmeticException("Exponent Overflow: " + mag);
               }

               int sig = mag % 3;
               if (sig < 0) {
                  sig += 3;
               }

               mag -= sig;
               if (mag < -999999999 || mag > 999999999) {
                  throw new ArithmeticException("Exponent Overflow: " + mag);
               }
            }

            return this;
         }

         --$26;
      }

      this.ind = 0;
      if (set.form != 0) {
         this.exp = 0;
      } else if (this.exp > 0) {
         this.exp = 0;
      } else if (this.exp < -999999999) {
         throw new ArithmeticException("Exponent Overflow: " + this.exp);
      }

      this.mant = ZERO.mant;
      return this;
   }
}

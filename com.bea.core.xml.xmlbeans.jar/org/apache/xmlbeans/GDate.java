package org.apache.xmlbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class GDate implements GDateSpecification, Serializable {
   private static final long serialVersionUID = 1L;
   static final int MAX_YEAR = 292277265;
   static final int MIN_YEAR = -292275295;
   private transient String _canonicalString;
   private transient String _string;
   private int _bits;
   private int _CY;
   private int _M;
   private int _D;
   private int _h;
   private int _m;
   private int _s;
   private BigDecimal _fs;
   private int _tzsign;
   private int _tzh;
   private int _tzm;
   static final BigDecimal _zero = BigDecimal.valueOf(0L);
   static final BigDecimal _one = BigDecimal.valueOf(1L);
   private static final char[] _tensDigit = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
   private static final char[] _onesDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
   private static final TimeZone GMTZONE = TimeZone.getTimeZone("GMT");
   private static final TimeZone[] MINUSZONE = new TimeZone[]{TimeZone.getTimeZone("GMT-00:00"), TimeZone.getTimeZone("GMT-01:00"), TimeZone.getTimeZone("GMT-02:00"), TimeZone.getTimeZone("GMT-03:00"), TimeZone.getTimeZone("GMT-04:00"), TimeZone.getTimeZone("GMT-05:00"), TimeZone.getTimeZone("GMT-06:00"), TimeZone.getTimeZone("GMT-07:00"), TimeZone.getTimeZone("GMT-08:00"), TimeZone.getTimeZone("GMT-09:00"), TimeZone.getTimeZone("GMT-10:00"), TimeZone.getTimeZone("GMT-11:00"), TimeZone.getTimeZone("GMT-12:00"), TimeZone.getTimeZone("GMT-13:00"), TimeZone.getTimeZone("GMT-14:00")};
   private static final TimeZone[] PLUSZONE = new TimeZone[]{TimeZone.getTimeZone("GMT+00:00"), TimeZone.getTimeZone("GMT+01:00"), TimeZone.getTimeZone("GMT+02:00"), TimeZone.getTimeZone("GMT+03:00"), TimeZone.getTimeZone("GMT+04:00"), TimeZone.getTimeZone("GMT+05:00"), TimeZone.getTimeZone("GMT+06:00"), TimeZone.getTimeZone("GMT+07:00"), TimeZone.getTimeZone("GMT+08:00"), TimeZone.getTimeZone("GMT+09:00"), TimeZone.getTimeZone("GMT+10:00"), TimeZone.getTimeZone("GMT+11:00"), TimeZone.getTimeZone("GMT+12:00"), TimeZone.getTimeZone("GMT+13:00"), TimeZone.getTimeZone("GMT+14:00")};

   public GDate(CharSequence string) {
      int len = string.length();

      int start;
      for(start = 0; len > 0 && isSpace(string.charAt(len - 1)); --len) {
      }

      while(start < len && isSpace(string.charAt(start))) {
         ++start;
      }

      int h;
      int value;
      int s;
      if (len - start >= 1 && string.charAt(len - 1) == 'Z') {
         this._bits |= 1;
         --len;
      } else if (len - start >= 6 && string.charAt(len - 3) == ':') {
         label285: {
            switch (string.charAt(len - 6)) {
               case '+':
                  h = 1;
                  break;
               case '-':
                  h = -1;
                  break;
               default:
                  break label285;
            }

            value = twoDigit(string, len - 5);
            s = twoDigit(string, len - 2);
            if (value > 14) {
               throw new IllegalArgumentException("time zone hour must be two digits between -14 and +14");
            }

            if (s > 59) {
               throw new IllegalArgumentException("time zone minute must be two digits between 00 and 59");
            }

            this._bits |= 1;
            this._tzsign = h;
            this._tzh = value;
            this._tzm = s;
            len -= 6;
         }
      }

      if (start < len && (start + 2 >= len || string.charAt(start + 2) != ':')) {
         boolean negyear = false;
         if (start < len && string.charAt(start) == '-') {
            negyear = true;
            ++start;
         }

         value = 0;
         s = -start;
         boolean startsWithZero = start < len && digitVal(string.charAt(start)) == 0;

         while(true) {
            char ch = start < len ? string.charAt(start) : 0;
            if (!isDigit(ch)) {
               s += start;
               if (s > 9) {
                  throw new IllegalArgumentException("year too long (up to 9 digits)");
               }

               if (s >= 4) {
                  this._bits |= 2;
                  this._CY = negyear ? -value : value;
                  if (this._CY == 0) {
                     throw new IllegalArgumentException("year must not be zero");
                  }
               } else if (s > 0) {
                  throw new IllegalArgumentException("year must be four digits (may pad with zeroes, e.g., 0560)");
               }

               if (this._CY > 292277265) {
                  throw new IllegalArgumentException("year value not supported: too big, must be less than 292277265");
               }

               if (this._CY < -292275295) {
                  throw new IllegalArgumentException("year values not supported: too small, must be bigger than -292275295");
               }

               if (ch != '-') {
                  if (negyear && !this.hasYear()) {
                     throw new IllegalArgumentException();
                  }
               } else {
                  ++start;
                  if (len - start >= 2) {
                     value = twoDigit(string, start);
                     if (value >= 1 && value <= 12) {
                        this._bits |= 4;
                        this._M = value;
                        start += 2;
                     }
                  }

                  ch = start < len ? string.charAt(start) : 0;
                  if (ch != '-') {
                     if (!this.hasMonth()) {
                        throw new IllegalArgumentException();
                     }
                  } else {
                     ++start;
                     if (len - start >= 2) {
                        value = twoDigit(string, start);
                        if (value >= 1 && value <= 31) {
                           this._bits |= 8;
                           this._D = value;
                           start += 2;
                        }
                     }

                     if (!this.hasDay()) {
                        if (!this.hasMonth() || this.hasYear()) {
                           throw new IllegalArgumentException();
                        }

                        ch = start < len ? string.charAt(start) : 0;
                        if (ch != '-') {
                           throw new IllegalArgumentException();
                        }

                        ++start;
                     }
                  }
               }
               break;
            }

            if (startsWithZero && start + s >= 4) {
               throw new IllegalArgumentException("year value starting with zero must be 4 or less digits: " + string);
            }

            value = value * 10 + digitVal(ch);
            ++start;
         }
      }

      if (start < len) {
         if (this.hasYear() || this.hasMonth() || this.hasDay()) {
            if (string.charAt(start) != 'T') {
               throw new IllegalArgumentException("date and time must be separated by 'T'");
            }

            ++start;
         }

         if (len < start + 8 || string.charAt(start + 2) != ':' || string.charAt(start + 5) != ':') {
            throw new IllegalArgumentException();
         }

         h = twoDigit(string, start);
         if (h > 24) {
            throw new IllegalArgumentException("hour must be between 00 and 23");
         }

         value = twoDigit(string, start + 3);
         if (value >= 60) {
            throw new IllegalArgumentException("minute must be between 00 and 59");
         }

         s = twoDigit(string, start + 6);
         if (s >= 60) {
            throw new IllegalArgumentException("second must be between 00 and 59");
         }

         start += 8;
         BigDecimal fs = _zero;
         if (start < len) {
            if (string.charAt(start) != '.') {
               throw new IllegalArgumentException();
            }

            if (start + 1 < len) {
               for(int i = start + 1; i < len; ++i) {
                  if (!isDigit(string.charAt(i))) {
                     throw new IllegalArgumentException();
                  }
               }

               try {
                  fs = new BigDecimal(string.subSequence(start, len).toString());
               } catch (Throwable var9) {
                  throw new IllegalArgumentException();
               }
            }
         }

         this._bits |= 16;
         this._h = h;
         this._m = value;
         this._s = s;
         this._fs = fs;
      }

      if (this.hasTime() && this._h == 24) {
         if (this._m != 0 || this._s != 0 || this._fs.compareTo(_zero) != 0) {
            throw new IllegalArgumentException("if hour is 24, minutes, seconds and fraction must be 0");
         }

         if (this.hasDate()) {
            GDateBuilder gdb = new GDateBuilder(this._CY, this._M, this._D, this._h, this._m, this._s, this._fs, this._tzsign, this._tzh, this._tzm);
            gdb.normalize24h();
            this._D = gdb.getDay();
            this._M = gdb.getMonth();
            this._CY = gdb.getYear();
            this._h = 0;
         } else if (this.hasDay()) {
            ++this._D;
            this._h = 0;
         }
      }

      if (!this.isValid()) {
         throw new IllegalArgumentException("invalid date");
      }
   }

   public GDate(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction) {
      this._bits = 30;
      this._CY = year;
      this._M = month;
      this._D = day;
      this._h = hour;
      this._m = minute;
      this._s = second;
      this._fs = fraction == null ? _zero : fraction;
      if (!this.isValid()) {
         throw new IllegalArgumentException();
      }
   }

   public GDate(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction, int tzSign, int tzHour, int tzMinute) {
      this._bits = 31;
      this._CY = year;
      this._M = month;
      this._D = day;
      this._h = hour;
      this._m = minute;
      this._s = second;
      this._fs = fraction == null ? _zero : fraction;
      this._tzsign = tzSign;
      this._tzh = tzHour;
      this._tzm = tzMinute;
      if (!this.isValid()) {
         throw new IllegalArgumentException();
      }
   }

   public GDate(Date date) {
      this((GDateSpecification)(new GDateBuilder(date)));
   }

   public GDate(Calendar calendar) {
      boolean isSetYear = calendar.isSet(1);
      boolean isSetEra = calendar.isSet(0);
      boolean isSetMonth = calendar.isSet(2);
      boolean isSetDay = calendar.isSet(5);
      boolean isSetHourOfDay = calendar.isSet(11);
      boolean isSetHour = calendar.isSet(10);
      boolean isSetAmPm = calendar.isSet(9);
      boolean isSetMinute = calendar.isSet(12);
      boolean isSetSecond = calendar.isSet(13);
      boolean isSetMillis = calendar.isSet(14);
      boolean isSetZone = calendar.isSet(15);
      boolean isSetDst = calendar.isSet(16);
      if (isSetYear) {
         int y = calendar.get(1);
         if (isSetEra && calendar instanceof GregorianCalendar && calendar.get(0) == 0) {
            y = -y;
         }

         this._bits |= 2;
         this._CY = y;
      }

      if (isSetMonth) {
         this._bits |= 4;
         this._M = calendar.get(2) + 1;
      }

      if (isSetDay) {
         this._bits |= 8;
         this._D = calendar.get(5);
      }

      boolean gotTime = false;
      int h = 0;
      int m = 0;
      int s = 0;
      BigDecimal fs = _zero;
      if (isSetHourOfDay) {
         h = calendar.get(11);
         gotTime = true;
      } else if (isSetHour && isSetAmPm) {
         h = calendar.get(10) + calendar.get(9) * 12;
         gotTime = true;
      }

      if (isSetMinute) {
         m = calendar.get(12);
         gotTime = true;
      }

      if (isSetSecond) {
         s = calendar.get(13);
         gotTime = true;
      }

      if (isSetMillis) {
         fs = BigDecimal.valueOf((long)calendar.get(14), 3);
         gotTime = true;
      }

      if (gotTime) {
         this._bits |= 16;
         this._h = h;
         this._m = m;
         this._s = s;
         this._fs = fs;
      }

      if (isSetZone) {
         int zoneOffsetInMilliseconds = calendar.get(15);
         if (isSetDst) {
            zoneOffsetInMilliseconds += calendar.get(16);
         }

         this._bits |= 1;
         if (zoneOffsetInMilliseconds == 0) {
            this._tzsign = 0;
            this._tzh = 0;
            this._tzm = 0;
            TimeZone zone = calendar.getTimeZone();
            String id = zone.getID();
            if (id != null && id.length() > 3) {
               switch (id.charAt(3)) {
                  case '+':
                     this._tzsign = 1;
                     break;
                  case '-':
                     this._tzsign = -1;
               }
            }
         } else {
            this._tzsign = zoneOffsetInMilliseconds < 0 ? -1 : 1;
            zoneOffsetInMilliseconds *= this._tzsign;
            this._tzh = zoneOffsetInMilliseconds / 3600000;
            this._tzm = (zoneOffsetInMilliseconds - this._tzh * 3600000) / '\uea60';
         }
      }

   }

   public GDate(GDateSpecification gdate) {
      if (gdate.hasTimeZone()) {
         this._bits |= 1;
         this._tzsign = gdate.getTimeZoneSign();
         this._tzh = gdate.getTimeZoneHour();
         this._tzm = gdate.getTimeZoneMinute();
      }

      if (gdate.hasTime()) {
         this._bits |= 16;
         this._h = gdate.getHour();
         this._m = gdate.getMinute();
         this._s = gdate.getSecond();
         this._fs = gdate.getFraction();
      }

      if (gdate.hasDay()) {
         this._bits |= 8;
         this._D = gdate.getDay();
      }

      if (gdate.hasMonth()) {
         this._bits |= 4;
         this._M = gdate.getMonth();
      }

      if (gdate.hasYear()) {
         this._bits |= 2;
         this._CY = gdate.getYear();
      }

   }

   static final boolean isDigit(char ch) {
      return (char)(ch - 48) <= '\t';
   }

   static final boolean isSpace(char ch) {
      switch (ch) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            return true;
         default:
            return false;
      }
   }

   static final int digitVal(char ch) {
      return ch - 48;
   }

   private static final int twoDigit(CharSequence str, int index) {
      char ch1 = str.charAt(index);
      char ch2 = str.charAt(index + 1);
      return isDigit(ch1) && isDigit(ch2) ? digitVal(ch1) * 10 + digitVal(ch2) : 100;
   }

   public final boolean isImmutable() {
      return true;
   }

   public int getFlags() {
      return this._bits;
   }

   public final boolean hasTimeZone() {
      return (this._bits & 1) != 0;
   }

   public final boolean hasYear() {
      return (this._bits & 2) != 0;
   }

   public final boolean hasMonth() {
      return (this._bits & 4) != 0;
   }

   public final boolean hasDay() {
      return (this._bits & 8) != 0;
   }

   public final boolean hasTime() {
      return (this._bits & 16) != 0;
   }

   public final boolean hasDate() {
      return (this._bits & 14) == 14;
   }

   public final int getYear() {
      return this._CY;
   }

   public final int getMonth() {
      return this._M;
   }

   public final int getDay() {
      return this._D;
   }

   public final int getHour() {
      return this._h;
   }

   public final int getMinute() {
      return this._m;
   }

   public final int getSecond() {
      return this._s;
   }

   public final BigDecimal getFraction() {
      return this._fs;
   }

   public final int getTimeZoneSign() {
      return this._tzsign;
   }

   public final int getTimeZoneHour() {
      return this._tzh;
   }

   public final int getTimeZoneMinute() {
      return this._tzm;
   }

   public int getMillisecond() {
      return this._fs == null ? 0 : this._fs.setScale(3, 1).unscaledValue().intValue();
   }

   public String canonicalString() {
      this.ensureCanonicalString();
      return this._canonicalString;
   }

   public boolean isValid() {
      return GDateBuilder.isValidGDate(this);
   }

   public int getJulianDate() {
      return GDateBuilder.julianDateForGDate(this);
   }

   public XmlCalendar getCalendar() {
      return new XmlCalendar(this);
   }

   public Date getDate() {
      return GDateBuilder.dateForGDate(this);
   }

   public int compareToGDate(GDateSpecification datespec) {
      return GDateBuilder.compareGDate(this, datespec);
   }

   public int getBuiltinTypeCode() {
      return GDateBuilder.btcForFlags(this._bits);
   }

   public GDate add(GDurationSpecification duration) {
      GDateBuilder builder = new GDateBuilder(this);
      builder.addGDuration(duration);
      return builder.toGDate();
   }

   public GDate subtract(GDurationSpecification duration) {
      GDateBuilder builder = new GDateBuilder(this);
      builder.subtractGDuration(duration);
      return builder.toGDate();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof GDate)) {
         return false;
      } else {
         this.ensureCanonicalString();
         return this._canonicalString.equals(((GDate)obj).canonicalString());
      }
   }

   public int hashCode() {
      this.ensureCanonicalString();
      return this._canonicalString.hashCode();
   }

   private void ensureCanonicalString() {
      if (this._canonicalString == null) {
         boolean needNormalize = this.hasTimeZone() && this.getTimeZoneSign() != 0 && this.hasTime() && this.hasDay() == this.hasMonth() && this.hasDay() == this.hasYear();
         if (!needNormalize && this.getFraction() != null && this.getFraction().scale() > 0) {
            BigInteger bi = this.getFraction().unscaledValue();
            needNormalize = bi.mod(GDateBuilder.TEN).signum() == 0;
         }

         if (!needNormalize) {
            this._canonicalString = this.toString();
         } else {
            GDateBuilder gdb = new GDateBuilder(this);
            gdb.normalize();
            this._canonicalString = gdb.toString();
         }

      }
   }

   public String toString() {
      if (this._string == null) {
         this._string = formatGDate(this);
      }

      return this._string;
   }

   private static final int _padTwoAppend(char[] b, int i, int n) {
      assert n >= 0 && n < 100;

      b[i] = _tensDigit[n];
      b[i + 1] = _onesDigit[n];
      return i + 2;
   }

   private static final int _padFourAppend(char[] b, int i, int n) {
      if (n < 0) {
         b[i++] = '-';
         n = -n;
      }

      if (n >= 10000) {
         String s = Integer.toString(n);
         s.getChars(0, s.length(), b, i);
         return i + s.length();
      } else {
         int q = n / 100;
         int r = n - q * 100;
         b[i] = _tensDigit[q];
         b[i + 1] = _onesDigit[q];
         b[i + 2] = _tensDigit[r];
         b[i + 3] = _onesDigit[r];
         return i + 4;
      }
   }

   static final TimeZone timeZoneForGDate(GDateSpecification date) {
      if (!date.hasTimeZone()) {
         return TimeZone.getDefault();
      } else if (date.getTimeZoneSign() == 0) {
         return GMTZONE;
      } else if (date.getTimeZoneMinute() == 0 && date.getTimeZoneHour() <= 14 && date.getTimeZoneHour() >= 0) {
         return date.getTimeZoneSign() < 0 ? MINUSZONE[date.getTimeZoneHour()] : PLUSZONE[date.getTimeZoneHour()];
      } else {
         char[] zb = new char[9];
         zb[0] = 'G';
         zb[1] = 'M';
         zb[2] = 'T';
         zb[3] = (char)(date.getTimeZoneSign() < 0 ? 45 : 43);
         _padTwoAppend(zb, 4, date.getTimeZoneHour());
         zb[6] = ':';
         _padTwoAppend(zb, 7, date.getTimeZoneMinute());
         return TimeZone.getTimeZone(new String(zb));
      }
   }

   static String formatGDate(GDateSpecification spec) {
      BigDecimal fs = spec.getFraction();
      char[] message = new char[33 + (fs == null ? 0 : fs.scale())];
      int i = 0;
      if (spec.hasYear() || spec.hasMonth() || spec.hasDay()) {
         if (spec.hasYear()) {
            i = _padFourAppend(message, 0, spec.getYear());
         } else {
            message[i++] = '-';
         }

         if (spec.hasMonth() || spec.hasDay()) {
            message[i++] = '-';
            if (spec.hasMonth()) {
               i = _padTwoAppend(message, i, spec.getMonth());
            }

            if (spec.hasDay()) {
               message[i++] = '-';
               i = _padTwoAppend(message, i, spec.getDay());
            }
         }

         if (spec.hasTime()) {
            message[i++] = 'T';
         }
      }

      if (spec.hasTime()) {
         i = _padTwoAppend(message, i, spec.getHour());
         message[i++] = ':';
         i = _padTwoAppend(message, i, spec.getMinute());
         message[i++] = ':';
         i = _padTwoAppend(message, i, spec.getSecond());
         if (fs != _zero) {
            String frac = fs.toString();
            int point = frac.indexOf(46);
            if (point >= 0) {
               frac.getChars(point, frac.length(), message, i);
               i += frac.length() - point;
            }
         }
      }

      if (spec.hasTimeZone()) {
         if (spec.getTimeZoneSign() == 0) {
            message[i++] = 'Z';
         } else {
            message[i++] = (char)(spec.getTimeZoneSign() > 0 ? 43 : 45);
            i = _padTwoAppend(message, i, spec.getTimeZoneHour());
            message[i++] = ':';
            i = _padTwoAppend(message, i, spec.getTimeZoneMinute());
         }
      }

      return new String(message, 0, i);
   }
}

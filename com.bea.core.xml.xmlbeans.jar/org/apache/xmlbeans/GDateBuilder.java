package org.apache.xmlbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class GDateBuilder implements GDateSpecification, Serializable {
   private static final long serialVersionUID = 1L;
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
   static final BigInteger TEN = BigInteger.valueOf(10L);

   public GDateBuilder() {
   }

   public Object clone() {
      return new GDateBuilder(this);
   }

   public GDate toGDate() {
      return new GDate(this);
   }

   public GDateBuilder(GDateSpecification gdate) {
      if (gdate.hasTimeZone()) {
         this.setTimeZone(gdate.getTimeZoneSign(), gdate.getTimeZoneHour(), gdate.getTimeZoneMinute());
      }

      if (gdate.hasTime()) {
         this.setTime(gdate.getHour(), gdate.getMinute(), gdate.getSecond(), gdate.getFraction());
      }

      if (gdate.hasDay()) {
         this.setDay(gdate.getDay());
      }

      if (gdate.hasMonth()) {
         this.setMonth(gdate.getMonth());
      }

      if (gdate.hasYear()) {
         this.setYear(gdate.getYear());
      }

   }

   public GDateBuilder(CharSequence string) {
      this((GDateSpecification)(new GDate(string)));
   }

   public GDateBuilder(Calendar calendar) {
      this((GDateSpecification)(new GDate(calendar)));
   }

   public GDateBuilder(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction) {
      this._bits = 30;
      if (year == 0) {
         throw new IllegalArgumentException();
      } else {
         this._CY = year > 0 ? year : year + 1;
         this._M = month;
         this._D = day;
         this._h = hour;
         this._m = minute;
         this._s = second;
         this._fs = fraction == null ? GDate._zero : fraction;
         if (!this.isValid()) {
            throw new IllegalArgumentException();
         }
      }
   }

   public GDateBuilder(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction, int tzSign, int tzHour, int tzMinute) {
      this._bits = 31;
      if (year == 0) {
         throw new IllegalArgumentException();
      } else {
         this._CY = year > 0 ? year : year + 1;
         this._M = month;
         this._D = day;
         this._h = hour;
         this._m = minute;
         this._s = second;
         this._fs = fraction == null ? GDate._zero : fraction;
         this._tzsign = tzSign;
         this._tzh = tzHour;
         this._tzm = tzMinute;
         if (!this.isValid()) {
            throw new IllegalArgumentException();
         }
      }
   }

   public GDateBuilder(Date date) {
      this.setDate(date);
   }

   public boolean isImmutable() {
      return false;
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
      return this._CY > 0 ? this._CY : this._CY - 1;
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

   public final int getMillisecond() {
      return this._fs != null && this._fs != GDate._zero ? this._fs.setScale(3, 4).unscaledValue().intValue() : 0;
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

   public void setYear(int year) {
      if (year >= -292275295 && year <= 292277265) {
         if (year == 0) {
            throw new IllegalArgumentException("year cannot be 0");
         } else {
            this._bits |= 2;
            this._CY = year > 0 ? year : year + 1;
         }
      } else {
         throw new IllegalArgumentException("year out of range");
      }
   }

   public void setMonth(int month) {
      if (month >= 1 && month <= 12) {
         this._bits |= 4;
         this._M = month;
      } else {
         throw new IllegalArgumentException("month out of range");
      }
   }

   public void setDay(int day) {
      if (day >= 1 && day <= 31) {
         this._bits |= 8;
         this._D = day;
      } else {
         throw new IllegalArgumentException("day out of range");
      }
   }

   public void setTime(int hour, int minute, int second, BigDecimal fraction) {
      if (hour >= 0 && hour <= 24) {
         if (minute >= 0 && minute <= 59) {
            if (second >= 0 && second <= 59) {
               if (fraction != null && (fraction.signum() < 0 || fraction.compareTo(GDate._one) > 1)) {
                  throw new IllegalArgumentException("fraction out of range");
               } else if (hour != 24 || minute == 0 && second == 0 && (fraction == null || GDate._zero.compareTo(fraction) == 0)) {
                  this._bits |= 16;
                  this._h = hour;
                  this._m = minute;
                  this._s = second;
                  this._fs = fraction == null ? GDate._zero : fraction;
               } else {
                  throw new IllegalArgumentException("when hour is 24, min sec and fracton must be 0");
               }
            } else {
               throw new IllegalArgumentException("second out of range");
            }
         } else {
            throw new IllegalArgumentException("minute out of range");
         }
      } else {
         throw new IllegalArgumentException("hour out of range");
      }
   }

   public void setTimeZone(int tzSign, int tzHour, int tzMinute) {
      if ((tzSign != 0 || tzHour != 0 || tzMinute != 0) && (tzSign != -1 && tzSign != 1 || tzHour < 0 || tzMinute < 0 || (tzHour != 14 || tzMinute != 0) && (tzHour >= 14 || tzMinute >= 60))) {
         throw new IllegalArgumentException("time zone out of range (-14:00 to +14:00). (" + (tzSign < 0 ? "-" : "+") + tzHour + ":" + tzMinute + ")");
      } else {
         this._bits |= 1;
         this._tzsign = tzSign;
         this._tzh = tzHour;
         this._tzm = tzMinute;
      }
   }

   public void setTimeZone(int tzTotalMinutes) {
      if (tzTotalMinutes >= -840 && tzTotalMinutes <= 840) {
         int tzSign = tzTotalMinutes < 0 ? -1 : (tzTotalMinutes > 0 ? 1 : 0);
         tzTotalMinutes *= tzSign;
         int tzH = tzTotalMinutes / 60;
         int tzM = tzTotalMinutes - tzH * 60;
         this.setTimeZone(tzSign, tzH, tzM);
      } else {
         throw new IllegalArgumentException("time zone out of range (-840 to 840 minutes). (" + tzTotalMinutes + ")");
      }
   }

   public void clearYear() {
      this._bits &= -3;
      this._CY = 0;
   }

   public void clearMonth() {
      this._bits &= -5;
      this._M = 0;
   }

   public void clearDay() {
      this._bits &= -9;
      this._D = 0;
   }

   public void clearTime() {
      this._bits &= -17;
      this._h = 0;
      this._m = 0;
      this._s = 0;
      this._fs = null;
   }

   public void clearTimeZone() {
      this._bits &= -2;
      this._tzsign = 0;
      this._tzh = 0;
      this._tzm = 0;
   }

   public boolean isValid() {
      return isValidGDate(this);
   }

   static final boolean isValidGDate(GDateSpecification date) {
      if (date.hasYear() && date.getYear() == 0) {
         return false;
      } else if (date.hasMonth() && (date.getMonth() < 1 || date.getMonth() > 12)) {
         return false;
      } else {
         if (date.hasDay()) {
            label132: {
               if (date.getDay() >= 1 && date.getDay() <= 31) {
                  if (date.getDay() <= 28 || !date.hasMonth()) {
                     break label132;
                  }

                  if (date.hasYear()) {
                     if (date.getDay() <= _maxDayInMonthFor(date.getYear() > 0 ? date.getYear() : date.getYear() + 1, date.getMonth())) {
                        break label132;
                     }
                  } else if (date.getDay() <= _maxDayInMonth(date.getMonth())) {
                     break label132;
                  }
               }

               return false;
            }
         }

         if (!date.hasTime() || date.getHour() >= 0 && date.getHour() <= 23 && date.getMinute() >= 0 && date.getMinute() <= 59 && date.getSecond() >= 0 && date.getSecond() <= 59 && date.getFraction().signum() >= 0 && date.getFraction().compareTo(GDate._one) < 0 || date.getHour() == 24 && date.getMinute() == 0 && date.getSecond() == 0 && date.getFraction().compareTo(GDate._zero) == 0) {
            return !date.hasTimeZone() || date.getTimeZoneSign() == 0 && date.getTimeZoneHour() == 0 && date.getTimeZoneMinute() == 0 || (date.getTimeZoneSign() == -1 || date.getTimeZoneSign() == 1) && date.getTimeZoneHour() >= 0 && date.getTimeZoneMinute() >= 0 && (date.getTimeZoneHour() == 14 && date.getTimeZoneMinute() == 0 || date.getTimeZoneHour() < 14 && date.getTimeZoneMinute() < 60);
         } else {
            return false;
         }
      }
   }

   public void normalize() {
      if (this.hasDay() == this.hasMonth() && this.hasDay() == this.hasYear() && this.hasTimeZone() && this.hasTime()) {
         this.normalizeToTimeZone(0, 0, 0);
      } else {
         this._normalizeTimeAndDate();
      }

      if (this.hasTime() && this._fs != null && this._fs.scale() > 0) {
         if (this._fs.signum() == 0) {
            this._fs = GDate._zero;
         } else {
            BigInteger bi = this._fs.unscaledValue();
            String str = bi.toString();

            int lastzero;
            for(lastzero = str.length(); lastzero > 0 && str.charAt(lastzero - 1) == '0'; --lastzero) {
            }

            if (lastzero < str.length()) {
               this._fs = this._fs.setScale(this._fs.scale() - str.length() + lastzero);
            }
         }
      }

   }

   void normalize24h() {
      if (this.hasTime() && this.getHour() == 24) {
         this._normalizeTimeAndDate();
      }
   }

   private void _normalizeTimeAndDate() {
      long carry = 0L;
      if (this.hasTime()) {
         carry = this._normalizeTime();
      }

      if (this.hasDay()) {
         this._D = (int)((long)this._D + carry);
      }

      if (this.hasDate()) {
         this._normalizeDate();
      } else if (this.hasMonth() && (this._M < 1 || this._M > 12)) {
         int temp = this._M;
         this._M = _modulo((long)temp, 1, 13);
         if (this.hasYear()) {
            this._CY += (int)_fQuotient((long)temp, 1, 13);
         }
      }

   }

   public void normalizeToTimeZone(int tzSign, int tzHour, int tzMinute) {
      if (tzSign == 0 && tzHour == 0 && tzMinute == 0 || (tzSign == -1 || tzSign == 1) && tzHour >= 0 && tzMinute >= 0 && (tzHour == 14 && tzMinute == 0 || tzHour < 14 && tzMinute < 60)) {
         if (this.hasTimeZone() && this.hasTime()) {
            if (this.hasDay() == this.hasMonth() && this.hasDay() == this.hasYear()) {
               int hshift = tzSign * tzHour - this._tzsign * this._tzh;
               int mshift = tzSign * tzMinute - this._tzsign * this._tzm;
               this._tzsign = tzSign;
               this._tzh = tzHour;
               this._tzm = tzMinute;
               this.addDuration(1, 0, 0, 0, hshift, mshift, 0, (BigDecimal)null);
            } else {
               throw new IllegalStateException("cannot do date math without a complete date");
            }
         } else {
            throw new IllegalStateException("cannot normalize time zone without both time and timezone");
         }
      } else {
         throw new IllegalArgumentException("time zone must be between -14:00 and +14:00");
      }
   }

   public void normalizeToTimeZone(int tzTotalMinutes) {
      if (tzTotalMinutes >= -840 && tzTotalMinutes <= 840) {
         int tzSign = tzTotalMinutes < 0 ? -1 : (tzTotalMinutes > 0 ? 1 : 0);
         tzTotalMinutes *= tzSign;
         int tzH = tzTotalMinutes / 60;
         int tzM = tzTotalMinutes - tzH * 60;
         this.normalizeToTimeZone(tzSign, tzH, tzM);
      } else {
         throw new IllegalArgumentException("time zone out of range (-840 to 840 minutes). (" + tzTotalMinutes + ")");
      }
   }

   public void addGDuration(GDurationSpecification duration) {
      this.addDuration(duration.getSign(), duration.getYear(), duration.getMonth(), duration.getDay(), duration.getHour(), duration.getMinute(), duration.getSecond(), duration.getFraction());
   }

   public void subtractGDuration(GDurationSpecification duration) {
      this.addDuration(-duration.getSign(), duration.getYear(), duration.getMonth(), duration.getDay(), duration.getHour(), duration.getMinute(), duration.getSecond(), duration.getFraction());
   }

   private void _normalizeDate() {
      if (this._M < 1 || this._M > 12 || this._D < 1 || this._D > _maxDayInMonthFor(this._CY, this._M)) {
         int temp = this._M;
         this._M = _modulo((long)temp, 1, 13);
         this._CY += (int)_fQuotient((long)temp, 1, 13);
         int extradays = this._D - 1;
         this._D = 1;
         this.setJulianDate(this.getJulianDate() + extradays);
      }

   }

   private long _normalizeTime() {
      long carry = 0L;
      if (this._fs != null && (this._fs.signum() < 0 || this._fs.compareTo(GDate._one) >= 0)) {
         BigDecimal bdcarry = this._fs.setScale(0, 3);
         this._fs = this._fs.subtract(bdcarry);
         carry = bdcarry.longValue();
      }

      if (carry != 0L || this._s < 0 || this._s > 59 || this._m < 0 || this._m > 50 || this._h < 0 || this._h > 23) {
         long temp = (long)this._s + carry;
         carry = _fQuotient(temp, 60);
         this._s = _mod(temp, 60, carry);
         temp = (long)this._m + carry;
         carry = _fQuotient(temp, 60);
         this._m = _mod(temp, 60, carry);
         temp = (long)this._h + carry;
         carry = _fQuotient(temp, 24);
         this._h = _mod(temp, 24, carry);
      }

      return carry;
   }

   public void addDuration(int sign, int year, int month, int day, int hour, int minute, int second, BigDecimal fraction) {
      boolean timemath = hour != 0 || minute != 0 || second != 0 || fraction != null && fraction.signum() != 0;
      if (timemath && !this.hasTime()) {
         throw new IllegalStateException("cannot do time math without a complete time");
      } else {
         boolean datemath = this.hasDay() && (day != 0 || timemath);
         if (datemath && !this.hasDate()) {
            throw new IllegalStateException("cannot do date math without a complete date");
         } else {
            if (month != 0 || year != 0) {
               if (this.hasDay()) {
                  this._normalizeDate();
               }

               int temp = this._M + sign * month;
               this._M = _modulo((long)temp, 1, 13);
               this._CY = this._CY + sign * year + (int)_fQuotient((long)temp, 1, 13);
               if (this.hasDay()) {
                  assert this._D >= 1;

                  temp = _maxDayInMonthFor(this._CY, this._M);
                  if (this._D > temp) {
                     this._D = temp;
                  }
               }
            }

            long carry = 0L;
            if (timemath) {
               if (fraction != null && fraction.signum() != 0) {
                  if (this._fs.signum() == 0 && sign == 1) {
                     this._fs = fraction;
                  } else {
                     this._fs = sign == 1 ? this._fs.add(fraction) : this._fs.subtract(fraction);
                  }
               }

               this._s += sign * second;
               this._m += sign * minute;
               this._h += sign * hour;
               carry = this._normalizeTime();
            }

            if (datemath) {
               this._D = (int)((long)this._D + (long)(sign * day) + carry);
               this._normalizeDate();
            }

         }
      }
   }

   private static int _maxDayInMonthFor(int year, int month) {
      if (month != 4 && month != 6 && month != 9 && month != 11) {
         if (month == 2) {
            return _isLeapYear(year) ? 29 : 28;
         } else {
            return 31;
         }
      } else {
         return 30;
      }
   }

   private static int _maxDayInMonth(int month) {
      if (month != 4 && month != 6 && month != 9 && month != 11) {
         return month == 2 ? 29 : 31;
      } else {
         return 30;
      }
   }

   public final int getJulianDate() {
      return julianDateForGDate(this);
   }

   public void setJulianDate(int julianday) {
      if (julianday < 0) {
         throw new IllegalArgumentException("date before year -4713");
      } else {
         int temp = julianday + 68569;
         int qepoc = 4 * temp / 146097;
         temp -= (146097 * qepoc + 3) / 4;
         this._CY = 4000 * (temp + 1) / 1461001;
         temp = temp - 1461 * this._CY / 4 + 31;
         this._M = 80 * temp / 2447;
         this._D = temp - 2447 * this._M / 80;
         temp = this._M / 11;
         this._M = this._M + 2 - 12 * temp;
         this._CY = 100 * (qepoc - 49) + this._CY + temp;
         this._bits |= 14;
      }
   }

   public void setDate(Date date) {
      TimeZone dtz = TimeZone.getDefault();
      int offset = dtz.getOffset(date.getTime());
      int offsetsign = 1;
      if (offset < 0) {
         offsetsign = -1;
         offset = -offset;
      }

      int offsetmin = offset / '\uea60';
      int offsethr = offsetmin / 60;
      offsetmin -= offsethr * 60;
      this.setTimeZone(offsetsign, offsethr, offsetmin);
      int roundedoffset = offsetsign * (offsethr * 60 + offsetmin) * 60 * 1000;
      this.setTime(0, 0, 0, GDate._zero);
      this._bits |= 14;
      this._CY = 1970;
      this._M = 1;
      this._D = 1;
      this.addGDuration(new GDuration(1, 0, 0, 0, 0, 0, 0, BigDecimal.valueOf(date.getTime() + (long)roundedoffset, 3)));
      if (this._fs.signum() == 0) {
         this._fs = GDate._zero;
      }

   }

   public void setGDate(GDateSpecification gdate) {
      this._bits = gdate.getFlags() & 31;
      int year = gdate.getYear();
      this._CY = year > 0 ? year : year + 1;
      this._M = gdate.getMonth();
      this._D = gdate.getDay();
      this._h = gdate.getHour();
      this._m = gdate.getMinute();
      this._s = gdate.getSecond();
      this._fs = gdate.getFraction();
      this._tzsign = gdate.getTimeZoneSign();
      this._tzh = gdate.getTimeZoneHour();
      this._tzm = gdate.getTimeZoneMinute();
   }

   public XmlCalendar getCalendar() {
      return new XmlCalendar(this);
   }

   public Date getDate() {
      return dateForGDate(this);
   }

   static int julianDateForGDate(GDateSpecification date) {
      if (!date.hasDate()) {
         throw new IllegalStateException("cannot do date math without a complete date");
      } else {
         int day = date.getDay();
         int month = date.getMonth();
         int year = date.getYear();
         year = year > 0 ? year : year + 1;
         int result = day - 32075 + 1461 * (year + 4800 + (month - 14) / 12) / 4 + 367 * (month - 2 - (month - 14) / 12 * 12) / 12 - 3 * ((year + 4900 + (month - 14) / 12) / 100) / 4;
         if (result < 0) {
            throw new IllegalStateException("date too far in the past (year allowed to -4713)");
         } else {
            return result;
         }
      }
   }

   static Date dateForGDate(GDateSpecification date) {
      long jDate = (long)julianDateForGDate(date);
      long to1970Date = jDate - 2440588L;
      long to1970Ms = 86400000L * to1970Date;
      to1970Ms += (long)date.getMillisecond();
      to1970Ms += (long)(date.getSecond() * 1000);
      to1970Ms += (long)(date.getMinute() * 60 * 1000);
      to1970Ms += (long)(date.getHour() * 60 * 60 * 1000);
      if (date.hasTimeZone()) {
         to1970Ms -= (long)(date.getTimeZoneMinute() * date.getTimeZoneSign() * 60 * 1000);
         to1970Ms -= (long)(date.getTimeZoneHour() * date.getTimeZoneSign() * 60 * 60 * 1000);
      } else {
         TimeZone def = TimeZone.getDefault();
         int offset = def.getOffset(to1970Ms);
         to1970Ms -= (long)offset;
      }

      return new Date(to1970Ms);
   }

   private static boolean _isLeapYear(int year) {
      return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
   }

   private static final long _fQuotient(long a, int b) {
      return a < 0L == b < 0 ? a / (long)b : -(((long)b - a - 1L) / (long)b);
   }

   private static int _mod(long a, int b, long quotient) {
      return (int)(a - quotient * (long)b);
   }

   private static final int _modulo(long temp, int low, int high) {
      long a = temp - (long)low;
      int b = high - low;
      return _mod(a, b, _fQuotient(a, b)) + low;
   }

   private static final long _fQuotient(long temp, int low, int high) {
      return _fQuotient(temp - (long)low, high - low);
   }

   private void _setToFirstMoment() {
      if (!this.hasYear()) {
         this.setYear(1584);
      }

      if (!this.hasMonth()) {
         this.setMonth(1);
      }

      if (!this.hasDay()) {
         this.setDay(1);
      }

      if (!this.hasTime()) {
         this.setTime(0, 0, 0, GDate._zero);
      }

   }

   public final int compareToGDate(GDateSpecification datespec) {
      return compareGDate(this, datespec);
   }

   static final int compareGDate(GDateSpecification tdate, GDateSpecification datespec) {
      int bitdiff = ((GDateSpecification)tdate).getFlags() ^ ((GDateSpecification)datespec).getFlags();
      int result;
      if ((bitdiff & 31) == 0) {
         if (((GDateSpecification)tdate).hasTimeZone() && (((GDateSpecification)datespec).getTimeZoneHour() != ((GDateSpecification)tdate).getTimeZoneHour() || ((GDateSpecification)datespec).getTimeZoneMinute() != ((GDateSpecification)tdate).getTimeZoneMinute() || ((GDateSpecification)datespec).getTimeZoneSign() != ((GDateSpecification)tdate).getTimeZoneSign())) {
            datespec = new GDateBuilder((GDateSpecification)datespec);
            result = ((GDateSpecification)tdate).getFlags() & 14;
            if (result != 0 && result != 14 || !((GDateSpecification)tdate).hasTime()) {
               ((GDateBuilder)datespec)._setToFirstMoment();
               tdate = new GDateBuilder((GDateSpecification)tdate);
               ((GDateBuilder)tdate)._setToFirstMoment();
            }

            ((GDateBuilder)datespec).normalizeToTimeZone(((GDateSpecification)tdate).getTimeZoneSign(), ((GDateSpecification)tdate).getTimeZoneHour(), ((GDateSpecification)tdate).getTimeZoneMinute());
         }

         return fieldwiseCompare((GDateSpecification)tdate, (GDateSpecification)datespec);
      } else if ((bitdiff & 30) != 0) {
         return 2;
      } else if (!((GDateSpecification)tdate).hasTimeZone()) {
         result = compareGDate((GDateSpecification)datespec, (GDateSpecification)tdate);
         return result == 2 ? 2 : -result;
      } else {
         GDateBuilder pdate = new GDateBuilder((GDateSpecification)tdate);
         if ((((GDateSpecification)tdate).getFlags() & 14) == 12) {
            if (((GDateSpecification)tdate).getDay() == 28 && ((GDateSpecification)tdate).getMonth() == 2) {
               if (((GDateSpecification)datespec).getDay() == 1 && ((GDateSpecification)datespec).getMonth() == 3) {
                  pdate.setDay(29);
               }
            } else if (((GDateSpecification)datespec).getDay() == 28 && ((GDateSpecification)datespec).getMonth() == 2 && ((GDateSpecification)tdate).getDay() == 1 && ((GDateSpecification)tdate).getMonth() == 3) {
               pdate.setMonth(2);
               pdate.setDay(29);
            }
         }

         pdate._setToFirstMoment();
         GDateBuilder qplusdate = new GDateBuilder((GDateSpecification)datespec);
         qplusdate._setToFirstMoment();
         qplusdate.setTimeZone(1, 14, 0);
         qplusdate.normalizeToTimeZone(((GDateSpecification)tdate).getTimeZoneSign(), ((GDateSpecification)tdate).getTimeZoneHour(), ((GDateSpecification)tdate).getTimeZoneMinute());
         if (fieldwiseCompare(pdate, qplusdate) == -1) {
            return -1;
         } else {
            qplusdate.setGDate((GDateSpecification)datespec);
            qplusdate._setToFirstMoment();
            qplusdate.setTimeZone(-1, 14, 0);
            qplusdate.normalizeToTimeZone(((GDateSpecification)tdate).getTimeZoneSign(), ((GDateSpecification)tdate).getTimeZoneHour(), ((GDateSpecification)tdate).getTimeZoneMinute());
            return fieldwiseCompare(pdate, qplusdate) == 1 ? 1 : 2;
         }
      }
   }

   private static int fieldwiseCompare(GDateSpecification tdate, GDateSpecification date) {
      int h;
      int th;
      if (tdate.hasYear()) {
         h = date.getYear();
         th = tdate.getYear();
         if (th < h) {
            return -1;
         }

         if (th > h) {
            return 1;
         }
      }

      if (tdate.hasMonth()) {
         h = date.getMonth();
         th = tdate.getMonth();
         if (th < h) {
            return -1;
         }

         if (th > h) {
            return 1;
         }
      }

      if (tdate.hasDay()) {
         h = date.getDay();
         th = tdate.getDay();
         if (th < h) {
            return -1;
         }

         if (th > h) {
            return 1;
         }
      }

      if (tdate.hasTime()) {
         h = date.getHour();
         th = tdate.getHour();
         if (th < h) {
            return -1;
         } else if (th > h) {
            return 1;
         } else {
            int m = date.getMinute();
            int tm = tdate.getMinute();
            if (tm < m) {
               return -1;
            } else if (tm > m) {
               return 1;
            } else {
               int s = date.getSecond();
               int ts = tdate.getSecond();
               if (ts < s) {
                  return -1;
               } else if (ts > s) {
                  return 1;
               } else {
                  BigDecimal fs = date.getFraction();
                  BigDecimal tfs = tdate.getFraction();
                  return tfs == null && fs == null ? 0 : (tfs == null ? GDate._zero : tfs).compareTo(fs == null ? GDate._zero : fs);
               }
            }
         }
      } else {
         return 0;
      }
   }

   public final int getBuiltinTypeCode() {
      return btcForFlags(this._bits);
   }

   static int btcForFlags(int flags) {
      switch (flags & 30) {
         case 2:
            return 18;
         case 3:
         case 5:
         case 7:
         case 9:
         case 10:
         case 11:
         case 13:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         default:
            return 0;
         case 4:
            return 21;
         case 6:
            return 17;
         case 8:
            return 20;
         case 12:
            return 19;
         case 14:
            return 16;
         case 16:
            return 15;
         case 30:
            return 14;
      }
   }

   public void setBuiltinTypeCode(int typeCode) {
      switch (typeCode) {
         case 14:
            return;
         case 15:
            this.clearYear();
            this.clearMonth();
            this.clearDay();
            return;
         case 16:
            this.clearTime();
            return;
         case 17:
            this.clearDay();
            this.clearTime();
            return;
         case 18:
            this.clearMonth();
            this.clearDay();
            this.clearTime();
            return;
         case 19:
            this.clearYear();
            this.clearTime();
            return;
         case 20:
            this.clearYear();
            this.clearMonth();
            this.clearTime();
            return;
         case 21:
            this.clearYear();
            this.clearDay();
            this.clearTime();
            return;
         default:
            throw new IllegalArgumentException("codeType must be one of SchemaType BTC_  DATE TIME related types.");
      }
   }

   public String canonicalString() {
      boolean needNormalize = this.hasTimeZone() && this.getTimeZoneSign() != 0 && this.hasTime() && this.hasDay() == this.hasMonth() && this.hasDay() == this.hasYear();
      if (!needNormalize && this.getFraction() != null && this.getFraction().scale() > 0) {
         BigInteger bi = this.getFraction().unscaledValue();
         needNormalize = bi.mod(TEN).signum() == 0;
      }

      if (!needNormalize) {
         return this.toString();
      } else {
         GDateBuilder cdate = new GDateBuilder(this);
         cdate.normalize();
         return cdate.toString();
      }
   }

   public final String toString() {
      return GDate.formatGDate(this);
   }
}

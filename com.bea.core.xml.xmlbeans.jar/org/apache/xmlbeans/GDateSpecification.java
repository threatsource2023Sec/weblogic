package org.apache.xmlbeans;

import java.math.BigDecimal;
import java.util.Date;

public interface GDateSpecification {
   int HAS_TIMEZONE = 1;
   int HAS_YEAR = 2;
   int HAS_MONTH = 4;
   int HAS_DAY = 8;
   int HAS_TIME = 16;

   int getFlags();

   boolean isImmutable();

   boolean isValid();

   boolean hasTimeZone();

   boolean hasYear();

   boolean hasMonth();

   boolean hasDay();

   boolean hasTime();

   boolean hasDate();

   int getYear();

   int getMonth();

   int getDay();

   int getHour();

   int getMinute();

   int getSecond();

   int getTimeZoneSign();

   int getTimeZoneHour();

   int getTimeZoneMinute();

   BigDecimal getFraction();

   int getMillisecond();

   int getJulianDate();

   XmlCalendar getCalendar();

   Date getDate();

   int compareToGDate(GDateSpecification var1);

   int getBuiltinTypeCode();

   String canonicalString();

   String toString();
}

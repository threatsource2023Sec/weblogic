package org.python.apache.xerces.impl.dv.xs;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;

public class TimeDV extends AbstractDateTimeDV {
   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      try {
         return this.parse(var1);
      } catch (Exception var4) {
         throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var1, "time"});
      }
   }

   protected AbstractDateTimeDV.DateTimeData parse(String var1) throws SchemaDateTimeException {
      AbstractDateTimeDV.DateTimeData var2 = new AbstractDateTimeDV.DateTimeData(var1, this);
      int var3 = var1.length();
      var2.year = 2000;
      var2.month = 1;
      var2.day = 15;
      this.getTime(var1, 0, var3, var2);
      this.validateDateTime(var2);
      this.saveUnnormalized(var2);
      if (var2.utc != 0 && var2.utc != 90) {
         this.normalize(var2);
         var2.day = 15;
      }

      var2.position = 2;
      return var2;
   }

   protected String dateToString(AbstractDateTimeDV.DateTimeData var1) {
      StringBuffer var2 = new StringBuffer(16);
      this.append(var2, var1.hour, 2);
      var2.append(':');
      this.append(var2, var1.minute, 2);
      var2.append(':');
      this.append(var2, var1.second);
      this.append(var2, (char)var1.utc, 0);
      return var2.toString();
   }

   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData var1) {
      return AbstractDateTimeDV.datatypeFactory.newXMLGregorianCalendar((BigInteger)null, Integer.MIN_VALUE, Integer.MIN_VALUE, var1.unNormHour, var1.unNormMinute, (int)var1.unNormSecond, var1.unNormSecond != 0.0 ? this.getFractionalSecondsAsBigDecimal(var1) : null, var1.hasTimeZone() ? var1.timezoneHr * 60 + var1.timezoneMin : Integer.MIN_VALUE);
   }
}

package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDMonthDayDeserializer;
import weblogic.xml.schema.types.util.XSDMonthDaySerializer;

public final class XSDGMonthDay implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "gMonthDay");
   private static final XSDMonthDayDeserializer deser = new XSDMonthDayDeserializer();
   private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");
   private static final int BASE_YEAR = 1996;

   public static XSDGMonthDay createFromXml(String xml) {
      return new XSDGMonthDay(xml);
   }

   public static XSDGMonthDay createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDGMonthDay(our_cal);
   }

   private XSDGMonthDay(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDGMonthDay(Calendar f) {
      this.javaValue = f;
      this.javaValue.set(1, 1996);
      this.xmlValue = getXml(f);
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return getCanonicalXml(this.javaValue);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public Object getJavaObject() {
      return this.javaValue;
   }

   public Calendar getCalendar() {
      return this.javaValue;
   }

   public int compareTo(XSDGMonthDay aGMonthDay) {
      Calendar thisVal = normalizeCalendar(this.javaValue);
      Calendar anotherVal = normalizeCalendar(aGMonthDay.javaValue);
      int atmp = thisVal.get(6);
      int btmp = anotherVal.get(6);
      return atmp < btmp ? -1 : (atmp == btmp ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDGMonthDay)o);
   }

   private static Calendar normalizeCalendar(Calendar cal) {
      Calendar c = (Calendar)cal.clone();
      c.set(1, 1996);
      c.setTimeZone(UTC_ZONE);
      c.getTimeInMillis();
      return c;
   }

   public static Calendar convertXml(String xsd_monthday) {
      if (xsd_monthday == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_monthday);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_monthday, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_monthday, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_monthday) {
      convertXml(xsd_monthday);
   }

   public static String getXml(Calendar cal) {
      return XSDMonthDaySerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.set(1, 1996);
      cal_utc.setTimeZone(UTC_ZONE);
      return XSDMonthDaySerializer.getString(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDGMonthDay) {
         XSDGMonthDay other = (XSDGMonthDay)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

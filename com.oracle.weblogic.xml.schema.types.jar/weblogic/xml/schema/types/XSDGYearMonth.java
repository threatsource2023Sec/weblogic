package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDYearMonthDeserializer;
import weblogic.xml.schema.types.util.XSDYearMonthSerializer;

public final class XSDGYearMonth implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "gYearMonth");
   private static final XSDYearMonthDeserializer deser = new XSDYearMonthDeserializer();
   private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");

   public static XSDGYearMonth createFromXml(String xml) {
      return new XSDGYearMonth(xml);
   }

   public static XSDGYearMonth createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDGYearMonth(our_cal);
   }

   private XSDGYearMonth(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDGYearMonth(Calendar f) {
      this.javaValue = f;
      this.javaValue.set(5, 10);
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

   public int compareTo(XSDGYearMonth aGYearMonth) {
      Calendar thisVal = normalizeCalendar(this.javaValue);
      Calendar anotherVal = normalizeCalendar(aGYearMonth.javaValue);
      int atmp = thisVal.get(1);
      int btmp = anotherVal.get(1);
      if (atmp < btmp) {
         return -1;
      } else if (atmp > btmp) {
         return 1;
      } else {
         atmp = thisVal.get(2);
         btmp = anotherVal.get(2);
         if (atmp < btmp) {
            return -1;
         } else {
            return atmp > btmp ? 1 : 0;
         }
      }
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDGYearMonth)o);
   }

   private static Calendar normalizeCalendar(Calendar cal) {
      Calendar c = (Calendar)cal.clone();
      c.setTimeZone(UTC_ZONE);
      c.getTimeInMillis();
      return c;
   }

   public static Calendar convertXml(String xsd_yearmonth) {
      if (xsd_yearmonth == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_yearmonth);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_yearmonth, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_yearmonth, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_yearmonth) {
      convertXml(xsd_yearmonth);
   }

   public static String getXml(Calendar cal) {
      return XSDYearMonthSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.setTimeZone(UTC_ZONE);
      cal_utc.set(5, 10);
      return XSDYearMonthSerializer.getString(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDGYearMonth) {
         XSDGYearMonth other = (XSDGYearMonth)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDDateTimeDeserializer;
import weblogic.xml.schema.types.util.XSDDateTimeSerializer;

public final class XSDDateTime implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime");
   private static final XSDDateTimeDeserializer deser = new XSDDateTimeDeserializer();
   private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");

   public static XSDDateTime createFromXml(String xml) {
      return new XSDDateTime(xml);
   }

   public static XSDDateTime createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDDateTime(our_cal);
   }

   private XSDDateTime(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDDateTime(Calendar f) {
      this.javaValue = f;
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

   public int compareTo(XSDDateTime aDateTime) {
      long thisVal = this.javaValue.getTimeInMillis();
      long anotherVal = aDateTime.javaValue.getTimeInMillis();
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDDateTime)o);
   }

   public static Calendar convertXml(String xsd_dateTime) {
      if (xsd_dateTime == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_dateTime);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_dateTime, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_dateTime, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_dateTime) {
      convertXml(xsd_dateTime);
   }

   public static String getXml(Calendar cal) {
      return XSDDateTimeSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.setTimeZone(UTC_ZONE);
      return XSDDateTimeSerializer.getString(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDDateTime) {
         long ourMillis = this.javaValue.getTimeInMillis();
         long theirMillis = ((XSDDateTime)obj).javaValue.getTimeInMillis();
         return ourMillis == theirMillis;
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

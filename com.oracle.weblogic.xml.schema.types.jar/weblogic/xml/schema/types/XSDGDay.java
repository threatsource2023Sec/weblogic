package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDDayDeserializer;
import weblogic.xml.schema.types.util.XSDDaySerializer;

public final class XSDGDay implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "gDay");
   private static final XSDDayDeserializer deser = new XSDDayDeserializer();
   private static final TimeZone utc_zone = TimeZone.getTimeZone("UTC");

   public static XSDGDay createFromXml(String xml) {
      return new XSDGDay(xml);
   }

   public static XSDGDay createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDGDay(our_cal);
   }

   private XSDGDay(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDGDay(Calendar f) {
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

   public int compareTo(XSDGDay aGDay) {
      int thisVal = this.javaValue.get(5);
      int anotherVal = aGDay.javaValue.get(5);
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDGDay)o);
   }

   public static Calendar convertXml(String xsd_gday) {
      if (xsd_gday == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_gday);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_gday, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_gday, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_gday) {
      convertXml(xsd_gday);
   }

   public static String getXml(Calendar cal) {
      return XSDDaySerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.set(11, 12);
      cal_utc.setTimeZone(utc_zone);
      return getXml(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDGDay) {
         XSDGDay other = (XSDGDay)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

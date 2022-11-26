package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDYearDeserializer;
import weblogic.xml.schema.types.util.XSDYearSerializer;

public final class XSDGYear implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "gYear");
   private static final XSDYearDeserializer deser = new XSDYearDeserializer();
   private static final TimeZone utc_zone = TimeZone.getTimeZone("UTC");

   public static XSDGYear createFromXml(String xml) {
      return new XSDGYear(xml);
   }

   public static XSDGYear createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDGYear(our_cal);
   }

   private XSDGYear(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDGYear(Calendar f) {
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

   public int compareTo(XSDGYear aGYear) {
      int thisVal = this.javaValue.get(1);
      int anotherVal = aGYear.javaValue.get(1);
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDGYear)o);
   }

   public static Calendar convertXml(String xsd_gyear) {
      if (xsd_gyear == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_gyear);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_gyear, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_gyear, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_gyear) {
      convertXml(xsd_gyear);
   }

   public static String getXml(Calendar cal) {
      return XSDYearSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.set(6, 10);
      cal_utc.setTimeZone(utc_zone);
      return getXml(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDGYear) {
         XSDGYear other = (XSDGYear)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

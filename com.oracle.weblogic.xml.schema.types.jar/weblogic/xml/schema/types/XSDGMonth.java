package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDMonthDeserializer;
import weblogic.xml.schema.types.util.XSDMonthSerializer;

public final class XSDGMonth implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "gMonth");
   private static final XSDMonthDeserializer deser = new XSDMonthDeserializer();
   private static final TimeZone utc_zone = TimeZone.getTimeZone("UTC");

   public static XSDGMonth createFromXml(String xml) {
      return new XSDGMonth(xml);
   }

   public static XSDGMonth createFromCalendar(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      return new XSDGMonth(our_cal);
   }

   private XSDGMonth(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDGMonth(Calendar f) {
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

   public int compareTo(XSDGMonth aGMonth) {
      int thisVal = this.javaValue.get(2);
      int anotherVal = aGMonth.javaValue.get(2);
      return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDGMonth)o);
   }

   public static Calendar convertXml(String xsd_gmonth) {
      if (xsd_gmonth == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_gmonth);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_gmonth, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_gmonth, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_gmonth) {
      convertXml(xsd_gmonth);
   }

   public static String getXml(Calendar cal) {
      return XSDMonthSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      cal_utc.set(5, 10);
      cal_utc.setTimeZone(utc_zone);
      return getXml(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDGMonth) {
         XSDGMonth other = (XSDGMonth)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

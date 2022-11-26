package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDDateDeserializer;
import weblogic.xml.schema.types.util.XSDDateSerializer;

public final class XSDDate implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "date");
   private static final XSDDateDeserializer deser = new XSDDateDeserializer();
   private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");

   public static XSDDate createFromXml(String xml) {
      return new XSDDate(xml);
   }

   public static XSDDate createFromCalendar(Calendar cal) {
      return new XSDDate(cal);
   }

   private XSDDate(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDDate(Calendar cal) {
      Calendar our_cal = (Calendar)cal.clone();
      fixTime(our_cal);
      this.javaValue = our_cal;
      this.xmlValue = getXml(our_cal);
   }

   private static void fixTime(Calendar c) {
      c.set(11, 12);
      c.set(12, 0);
      c.set(13, 0);
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

   public int compareTo(XSDDate aDate) {
      Calendar thisVal = normalizeCalendar(this.javaValue);
      Calendar anotherVal = normalizeCalendar(aDate.javaValue);
      int atmp = thisVal.get(1);
      int btmp = anotherVal.get(1);
      if (atmp < btmp) {
         return -1;
      } else if (atmp > btmp) {
         return 1;
      } else {
         atmp = thisVal.get(6);
         btmp = anotherVal.get(6);
         if (atmp < btmp) {
            return -1;
         } else {
            return atmp > btmp ? 1 : 0;
         }
      }
   }

   private static Calendar normalizeCalendar(Calendar cal) {
      Calendar c = (Calendar)cal.clone();
      c.setTimeZone(UTC_ZONE);
      c.getTimeInMillis();
      return c;
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDDate)o);
   }

   public static Calendar convertXml(String xsd_date) {
      if (xsd_date == null) {
         throw new NullPointerException();
      } else {
         try {
            return deser.getCalendar(xsd_date);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_date, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_date, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_date) {
      convertXml(xsd_date);
   }

   public static String getXml(Calendar cal) {
      return XSDDateSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      Calendar cal_utc = (Calendar)cal.clone();
      fixTime(cal_utc);
      cal_utc.setTimeZone(UTC_ZONE);
      return XSDDateSerializer.getString(cal_utc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDDate) {
         XSDDate other = (XSDDate)obj;
         return 0 == this.compareTo(other);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}

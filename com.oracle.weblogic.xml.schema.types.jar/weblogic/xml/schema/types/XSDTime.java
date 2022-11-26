package weblogic.xml.schema.types;

import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDTimeDeserializer;
import weblogic.xml.schema.types.util.XSDTimeSerializer;

public final class XSDTime implements XSDBuiltinType, Comparable {
   private final Calendar javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "time");
   private static final XSDTimeDeserializer deser = new XSDTimeDeserializer();
   private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");
   private static final Calendar theDay = Calendar.getInstance();

   public static XSDTime createFromXml(String xml) {
      return new XSDTime(xml);
   }

   public static XSDTime createFromCalendar(Calendar cal) {
      return new XSDTime(cal);
   }

   private XSDTime(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDTime(Calendar cal) {
      Calendar our_cal = (Calendar)theDay.clone();
      copyTime(our_cal, cal);
      this.javaValue = our_cal;
      this.xmlValue = getXml(our_cal);
   }

   private static void copyTime(Calendar dest, Calendar time_src) {
      dest.setTimeZone(time_src.getTimeZone());
      dest.set(theDay.get(1), theDay.get(2), theDay.get(5), time_src.get(11), time_src.get(12), time_src.get(13));
      dest.set(14, time_src.get(14));
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

   public int compareTo(XSDTime aTime) {
      Calendar other = aTime.javaValue;
      Calendar ours = this.javaValue;
      return normalizeAndCompareTimes(ours, other);
   }

   public int compareTo(Object o) {
      return this.compareTo((XSDTime)o);
   }

   public static Calendar convertXml(String xsd_time) {
      if (xsd_time == null) {
         throw new NullPointerException();
      } else {
         try {
            Calendar our_cal = (Calendar)theDay.clone();
            Calendar raw = deser.getCalendar(xsd_time);
            copyTime(our_cal, raw);
            our_cal.getTimeInMillis();
            return our_cal;
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_time, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_time, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validateXml(String xsd_time) {
      convertXml(xsd_time);
   }

   public static String getXml(Calendar cal) {
      return XSDTimeSerializer.getString(cal);
   }

   public static String getCanonicalXml(Calendar cal) {
      XSDTime tmp = createFromCalendar(cal);
      Calendar tc = tmp.javaValue;
      tc.setTimeZone(UTC_ZONE);
      return XSDTimeSerializer.getString(tc);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDTime) {
         Calendar other = ((XSDTime)obj).javaValue;
         Calendar ours = this.javaValue;
         return 0 == normalizeAndCompareTimes(other, ours);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }

   private static int normalizeAndCompareTimes(Calendar a, Calendar b) {
      Calendar na = normalizeCalendar(a);
      Calendar nb = normalizeCalendar(b);
      return compareTimes(na, nb);
   }

   private static int compareTimes(Calendar a, Calendar b) {
      int atmp = a.get(11);
      int btmp = b.get(11);
      if (atmp < btmp) {
         return -1;
      } else if (atmp > btmp) {
         return 1;
      } else {
         atmp = a.get(12);
         btmp = b.get(12);
         if (atmp < btmp) {
            return -1;
         } else if (atmp > btmp) {
            return 1;
         } else {
            atmp = a.get(13);
            btmp = b.get(13);
            if (atmp < btmp) {
               return -1;
            } else if (atmp > btmp) {
               return 1;
            } else {
               atmp = a.get(14);
               btmp = b.get(14);
               if (atmp < btmp) {
                  return -1;
               } else {
                  return atmp > btmp ? 1 : 0;
               }
            }
         }
      }
   }

   private static Calendar normalizeCalendar(Calendar cal) {
      Calendar c = (Calendar)cal.clone();
      c.setTimeZone(UTC_ZONE);
      c.getTimeInMillis();
      return c;
   }

   static {
      theDay.setTimeZone(UTC_ZONE);
      theDay.setTimeInMillis(1000000000000L);
   }
}

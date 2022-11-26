package net.shibboleth.utilities.java.support.xml;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public final class DOMTypeSupport {
   private static DatatypeFactory dataTypeFactory;
   private static Calendar baseline;

   private DOMTypeSupport() {
   }

   public static long dateTimeToLong(@Nonnull String dateTime) {
      String trimmedString = (String)Constraint.isNotNull(StringSupport.trimOrNull(dateTime), "Lexical dateTime may not be null or empty");
      XMLGregorianCalendar calendar = dataTypeFactory.newXMLGregorianCalendar(trimmedString);
      return calendar.toGregorianCalendar().getTimeInMillis();
   }

   public static long durationToLong(String duration) {
      return dataTypeFactory.newDuration(duration).getTimeInMillis(baseline);
   }

   public static long durationToLong(Duration duration) {
      return duration.getTimeInMillis(baseline);
   }

   public static DatatypeFactory getDataTypeFactory() {
      return dataTypeFactory;
   }

   @Nullable
   public static QName getXSIType(@Nullable Element e) {
      if (hasXSIType(e)) {
         Attr attribute = e.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "type");
         String attributeValue = attribute.getTextContent().trim();
         return QNameSupport.constructQName(e, attributeValue);
      } else {
         return null;
      }
   }

   public static boolean hasXSIType(@Nullable Element e) {
      return e != null && e.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "type") != null;
   }

   @Nonnull
   public static String longToDateTime(long dateTime) {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
      calendar.setTimeInMillis(dateTime);
      return dataTypeFactory.newXMLGregorianCalendar(calendar).normalize().toXMLFormat();
   }

   @Nonnull
   public static String longToDuration(long duration) {
      return dataTypeFactory.newDuration(duration).toString();
   }

   static {
      try {
         dataTypeFactory = DatatypeFactory.newInstance();
         baseline = new GregorianCalendar(1696, 9, 1, 0, 0, 0);
      } catch (DatatypeConfigurationException var1) {
         throw new RuntimeException("JVM is required to support XML DatatypeFactory but it does not", var1);
      }
   }
}

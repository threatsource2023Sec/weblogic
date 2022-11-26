package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TimerScheduleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimerScheduleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("timerscheduletype98cctype");

   String getSecond();

   boolean isSetSecond();

   void setSecond(String var1);

   String addNewSecond();

   void unsetSecond();

   String getMinute();

   boolean isSetMinute();

   void setMinute(String var1);

   String addNewMinute();

   void unsetMinute();

   String getHour();

   boolean isSetHour();

   void setHour(String var1);

   String addNewHour();

   void unsetHour();

   String getDayOfMonth();

   boolean isSetDayOfMonth();

   void setDayOfMonth(String var1);

   String addNewDayOfMonth();

   void unsetDayOfMonth();

   String getMonth();

   boolean isSetMonth();

   void setMonth(String var1);

   String addNewMonth();

   void unsetMonth();

   String getDayOfWeek();

   boolean isSetDayOfWeek();

   void setDayOfWeek(String var1);

   String addNewDayOfWeek();

   void unsetDayOfWeek();

   String getYear();

   boolean isSetYear();

   void setYear(String var1);

   String addNewYear();

   void unsetYear();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TimerScheduleType newInstance() {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().newInstance(TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType newInstance(XmlOptions options) {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().newInstance(TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(java.lang.String xmlAsString) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(File file) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(file, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(file, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(URL u) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(u, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(u, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(InputStream is) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(is, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(is, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(Reader r) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(r, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(r, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(XMLStreamReader sr) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(sr, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(sr, TimerScheduleType.type, options);
      }

      public static TimerScheduleType parse(Node node) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(node, TimerScheduleType.type, (XmlOptions)null);
      }

      public static TimerScheduleType parse(Node node, XmlOptions options) throws XmlException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(node, TimerScheduleType.type, options);
      }

      /** @deprecated */
      public static TimerScheduleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(xis, TimerScheduleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimerScheduleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimerScheduleType)XmlBeans.getContextTypeLoader().parse(xis, TimerScheduleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerScheduleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerScheduleType.type, options);
      }

      private Factory() {
      }
   }
}

package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlDateTime;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Calendar;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TimerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("timertypebd80type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   TimerScheduleType getSchedule();

   void setSchedule(TimerScheduleType var1);

   TimerScheduleType addNewSchedule();

   Calendar getStart();

   XmlDateTime xgetStart();

   boolean isSetStart();

   void setStart(Calendar var1);

   void xsetStart(XmlDateTime var1);

   void unsetStart();

   Calendar getEnd();

   XmlDateTime xgetEnd();

   boolean isSetEnd();

   void setEnd(Calendar var1);

   void xsetEnd(XmlDateTime var1);

   void unsetEnd();

   NamedMethodType getTimeoutMethod();

   void setTimeoutMethod(NamedMethodType var1);

   NamedMethodType addNewTimeoutMethod();

   TrueFalseType getPersistent();

   boolean isSetPersistent();

   void setPersistent(TrueFalseType var1);

   TrueFalseType addNewPersistent();

   void unsetPersistent();

   String getTimezone();

   boolean isSetTimezone();

   void setTimezone(String var1);

   String addNewTimezone();

   void unsetTimezone();

   String getInfo();

   boolean isSetInfo();

   void setInfo(String var1);

   String addNewInfo();

   void unsetInfo();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TimerType newInstance() {
         return (TimerType)XmlBeans.getContextTypeLoader().newInstance(TimerType.type, (XmlOptions)null);
      }

      public static TimerType newInstance(XmlOptions options) {
         return (TimerType)XmlBeans.getContextTypeLoader().newInstance(TimerType.type, options);
      }

      public static TimerType parse(java.lang.String xmlAsString) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerType.type, options);
      }

      public static TimerType parse(File file) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(file, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(file, TimerType.type, options);
      }

      public static TimerType parse(URL u) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(u, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(u, TimerType.type, options);
      }

      public static TimerType parse(InputStream is) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(is, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(is, TimerType.type, options);
      }

      public static TimerType parse(Reader r) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(r, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(r, TimerType.type, options);
      }

      public static TimerType parse(XMLStreamReader sr) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(sr, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(sr, TimerType.type, options);
      }

      public static TimerType parse(Node node) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(node, TimerType.type, (XmlOptions)null);
      }

      public static TimerType parse(Node node, XmlOptions options) throws XmlException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(node, TimerType.type, options);
      }

      /** @deprecated */
      public static TimerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(xis, TimerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimerType)XmlBeans.getContextTypeLoader().parse(xis, TimerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerType.type, options);
      }

      private Factory() {
      }
   }
}

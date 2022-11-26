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

public interface SessionConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SessionConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("sessionconfigtype2786type");

   XsdIntegerType getSessionTimeout();

   boolean isSetSessionTimeout();

   void setSessionTimeout(XsdIntegerType var1);

   XsdIntegerType addNewSessionTimeout();

   void unsetSessionTimeout();

   CookieConfigType getCookieConfig();

   boolean isSetCookieConfig();

   void setCookieConfig(CookieConfigType var1);

   CookieConfigType addNewCookieConfig();

   void unsetCookieConfig();

   TrackingModeType[] getTrackingModeArray();

   TrackingModeType getTrackingModeArray(int var1);

   int sizeOfTrackingModeArray();

   void setTrackingModeArray(TrackingModeType[] var1);

   void setTrackingModeArray(int var1, TrackingModeType var2);

   TrackingModeType insertNewTrackingMode(int var1);

   TrackingModeType addNewTrackingMode();

   void removeTrackingMode(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SessionConfigType newInstance() {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().newInstance(SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType newInstance(XmlOptions options) {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().newInstance(SessionConfigType.type, options);
      }

      public static SessionConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(File file) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(file, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(file, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(URL u) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(u, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(u, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(InputStream is) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(is, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(is, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(Reader r) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(r, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(r, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(XMLStreamReader sr) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(sr, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(sr, SessionConfigType.type, options);
      }

      public static SessionConfigType parse(Node node) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(node, SessionConfigType.type, (XmlOptions)null);
      }

      public static SessionConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(node, SessionConfigType.type, options);
      }

      /** @deprecated */
      public static SessionConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(xis, SessionConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SessionConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SessionConfigType)XmlBeans.getContextTypeLoader().parse(xis, SessionConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionConfigType.type, options);
      }

      private Factory() {
      }
   }
}

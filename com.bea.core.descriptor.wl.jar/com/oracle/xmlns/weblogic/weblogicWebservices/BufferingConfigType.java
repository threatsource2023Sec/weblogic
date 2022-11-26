package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface BufferingConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BufferingConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("bufferingconfigtypec34atype");

   boolean getCustomized();

   XmlBoolean xgetCustomized();

   boolean isSetCustomized();

   void setCustomized(boolean var1);

   void xsetCustomized(XmlBoolean var1);

   void unsetCustomized();

   BufferingQueueType getRequestQueue();

   boolean isSetRequestQueue();

   void setRequestQueue(BufferingQueueType var1);

   BufferingQueueType addNewRequestQueue();

   void unsetRequestQueue();

   BufferingQueueType getResponseQueue();

   boolean isSetResponseQueue();

   void setResponseQueue(BufferingQueueType var1);

   BufferingQueueType addNewResponseQueue();

   void unsetResponseQueue();

   int getRetryCount();

   XmlInt xgetRetryCount();

   boolean isSetRetryCount();

   void setRetryCount(int var1);

   void xsetRetryCount(XmlInt var1);

   void unsetRetryCount();

   String getRetryDelay();

   boolean isSetRetryDelay();

   void setRetryDelay(String var1);

   String addNewRetryDelay();

   void unsetRetryDelay();

   public static final class Factory {
      public static BufferingConfigType newInstance() {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().newInstance(BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType newInstance(XmlOptions options) {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().newInstance(BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(File file) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(file, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(file, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(URL u) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(u, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(u, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(InputStream is) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(is, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(is, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(Reader r) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(r, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(r, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(XMLStreamReader sr) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(sr, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(sr, BufferingConfigType.type, options);
      }

      public static BufferingConfigType parse(Node node) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(node, BufferingConfigType.type, (XmlOptions)null);
      }

      public static BufferingConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(node, BufferingConfigType.type, options);
      }

      /** @deprecated */
      public static BufferingConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(xis, BufferingConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BufferingConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BufferingConfigType)XmlBeans.getContextTypeLoader().parse(xis, BufferingConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BufferingConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BufferingConfigType.type, options);
      }

      private Factory() {
      }
   }
}

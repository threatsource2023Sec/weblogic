package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface QueueType extends DestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QueueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("queuetypeb9f7type");

   int getForwardDelay();

   XmlInt xgetForwardDelay();

   boolean isSetForwardDelay();

   void setForwardDelay(int var1);

   void xsetForwardDelay(XmlInt var1);

   void unsetForwardDelay();

   boolean getResetDeliveryCountOnForward();

   XmlBoolean xgetResetDeliveryCountOnForward();

   boolean isSetResetDeliveryCountOnForward();

   void setResetDeliveryCountOnForward(boolean var1);

   void xsetResetDeliveryCountOnForward(XmlBoolean var1);

   void unsetResetDeliveryCountOnForward();

   public static final class Factory {
      public static QueueType newInstance() {
         return (QueueType)XmlBeans.getContextTypeLoader().newInstance(QueueType.type, (XmlOptions)null);
      }

      public static QueueType newInstance(XmlOptions options) {
         return (QueueType)XmlBeans.getContextTypeLoader().newInstance(QueueType.type, options);
      }

      public static QueueType parse(String xmlAsString) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueueType.type, options);
      }

      public static QueueType parse(File file) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(file, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(file, QueueType.type, options);
      }

      public static QueueType parse(URL u) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(u, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(u, QueueType.type, options);
      }

      public static QueueType parse(InputStream is) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(is, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(is, QueueType.type, options);
      }

      public static QueueType parse(Reader r) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(r, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(r, QueueType.type, options);
      }

      public static QueueType parse(XMLStreamReader sr) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(sr, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(sr, QueueType.type, options);
      }

      public static QueueType parse(Node node) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(node, QueueType.type, (XmlOptions)null);
      }

      public static QueueType parse(Node node, XmlOptions options) throws XmlException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(node, QueueType.type, options);
      }

      /** @deprecated */
      public static QueueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(xis, QueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QueueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QueueType)XmlBeans.getContextTypeLoader().parse(xis, QueueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueueType.type, options);
      }

      private Factory() {
      }
   }
}

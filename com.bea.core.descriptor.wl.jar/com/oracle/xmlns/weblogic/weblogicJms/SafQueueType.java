package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface SafQueueType extends SafDestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafQueueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safqueuetype99cctype");

   public static final class Factory {
      public static SafQueueType newInstance() {
         return (SafQueueType)XmlBeans.getContextTypeLoader().newInstance(SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType newInstance(XmlOptions options) {
         return (SafQueueType)XmlBeans.getContextTypeLoader().newInstance(SafQueueType.type, options);
      }

      public static SafQueueType parse(String xmlAsString) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafQueueType.type, options);
      }

      public static SafQueueType parse(File file) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(file, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(file, SafQueueType.type, options);
      }

      public static SafQueueType parse(URL u) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(u, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(u, SafQueueType.type, options);
      }

      public static SafQueueType parse(InputStream is) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(is, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(is, SafQueueType.type, options);
      }

      public static SafQueueType parse(Reader r) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(r, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(r, SafQueueType.type, options);
      }

      public static SafQueueType parse(XMLStreamReader sr) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(sr, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(sr, SafQueueType.type, options);
      }

      public static SafQueueType parse(Node node) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(node, SafQueueType.type, (XmlOptions)null);
      }

      public static SafQueueType parse(Node node, XmlOptions options) throws XmlException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(node, SafQueueType.type, options);
      }

      /** @deprecated */
      public static SafQueueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(xis, SafQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafQueueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafQueueType)XmlBeans.getContextTypeLoader().parse(xis, SafQueueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafQueueType.type, options);
      }

      private Factory() {
      }
   }
}

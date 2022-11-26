package com.sun.java.xml.ns.javaee;

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

public interface MessageDestinationLinkType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationLinkType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("messagedestinationlinktypec8cctype");

   public static final class Factory {
      public static MessageDestinationLinkType newInstance() {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType newInstance(XmlOptions options) {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(File file) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationLinkType.type, options);
      }

      public static MessageDestinationLinkType parse(Node node) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      public static MessageDestinationLinkType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationLinkType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationLinkType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationLinkType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationLinkType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationLinkType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationLinkType.type, options);
      }

      private Factory() {
      }
   }
}

package com.sun.java.xml.ns.j2Ee;

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

public interface MessageDestinationTypeType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("messagedestinationtypetype63f2type");

   public static final class Factory {
      public static MessageDestinationTypeType newInstance() {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType newInstance(XmlOptions options) {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(File file) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationTypeType.type, options);
      }

      public static MessageDestinationTypeType parse(Node node) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      public static MessageDestinationTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationTypeType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationTypeType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationTypeType.type, options);
      }

      private Factory() {
      }
   }
}

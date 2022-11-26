package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MessageDestinationNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("messagedestinationnametype544btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessageDestinationNameType newInstance() {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType newInstance(XmlOptions options) {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(String xmlAsString) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(File file) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationNameType.type, options);
      }

      public static MessageDestinationNameType parse(Node node) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationNameType.type, (XmlOptions)null);
      }

      public static MessageDestinationNameType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationNameType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationNameType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationNameType.type, options);
      }

      private Factory() {
      }
   }
}

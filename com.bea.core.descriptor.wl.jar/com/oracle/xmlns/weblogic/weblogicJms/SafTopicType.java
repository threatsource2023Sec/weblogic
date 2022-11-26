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

public interface SafTopicType extends SafDestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafTopicType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("saftopictype7ceetype");

   public static final class Factory {
      public static SafTopicType newInstance() {
         return (SafTopicType)XmlBeans.getContextTypeLoader().newInstance(SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType newInstance(XmlOptions options) {
         return (SafTopicType)XmlBeans.getContextTypeLoader().newInstance(SafTopicType.type, options);
      }

      public static SafTopicType parse(String xmlAsString) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafTopicType.type, options);
      }

      public static SafTopicType parse(File file) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(file, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(file, SafTopicType.type, options);
      }

      public static SafTopicType parse(URL u) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(u, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(u, SafTopicType.type, options);
      }

      public static SafTopicType parse(InputStream is) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(is, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(is, SafTopicType.type, options);
      }

      public static SafTopicType parse(Reader r) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(r, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(r, SafTopicType.type, options);
      }

      public static SafTopicType parse(XMLStreamReader sr) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(sr, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(sr, SafTopicType.type, options);
      }

      public static SafTopicType parse(Node node) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(node, SafTopicType.type, (XmlOptions)null);
      }

      public static SafTopicType parse(Node node, XmlOptions options) throws XmlException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(node, SafTopicType.type, options);
      }

      /** @deprecated */
      public static SafTopicType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(xis, SafTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafTopicType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafTopicType)XmlBeans.getContextTypeLoader().parse(xis, SafTopicType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafTopicType.type, options);
      }

      private Factory() {
      }
   }
}

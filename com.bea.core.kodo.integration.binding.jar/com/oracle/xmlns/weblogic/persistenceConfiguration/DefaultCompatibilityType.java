package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface DefaultCompatibilityType extends PersistenceCompatibilityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultCompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultcompatibilitytype9648type");

   public static final class Factory {
      public static DefaultCompatibilityType newInstance() {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType newInstance(XmlOptions options) {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(String xmlAsString) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(File file) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(URL u) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(Reader r) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, DefaultCompatibilityType.type, options);
      }

      public static DefaultCompatibilityType parse(Node node) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      public static DefaultCompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, DefaultCompatibilityType.type, options);
      }

      /** @deprecated */
      public static DefaultCompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultCompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, DefaultCompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultCompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}

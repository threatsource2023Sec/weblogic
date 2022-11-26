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

public interface JdatastoreDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdatastoreDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdatastoredictionarytype7ed6type");

   public static final class Factory {
      public static JdatastoreDictionaryType newInstance() {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().newInstance(JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType newInstance(XmlOptions options) {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().newInstance(JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(String xmlAsString) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(File file) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(file, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(file, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(URL u) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(u, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(u, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(is, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(is, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(Reader r) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(r, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(r, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, JdatastoreDictionaryType.type, options);
      }

      public static JdatastoreDictionaryType parse(Node node) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(node, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      public static JdatastoreDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(node, JdatastoreDictionaryType.type, options);
      }

      /** @deprecated */
      public static JdatastoreDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdatastoreDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdatastoreDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, JdatastoreDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdatastoreDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdatastoreDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}

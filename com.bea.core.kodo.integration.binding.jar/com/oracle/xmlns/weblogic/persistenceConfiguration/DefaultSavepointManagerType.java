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

public interface DefaultSavepointManagerType extends SavepointManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultSavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultsavepointmanagertype46c5type");

   public static final class Factory {
      public static DefaultSavepointManagerType newInstance() {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType newInstance(XmlOptions options) {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(String xmlAsString) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(File file) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(URL u) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSavepointManagerType.type, options);
      }

      public static DefaultSavepointManagerType parse(Node node) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      public static DefaultSavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static DefaultSavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultSavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}

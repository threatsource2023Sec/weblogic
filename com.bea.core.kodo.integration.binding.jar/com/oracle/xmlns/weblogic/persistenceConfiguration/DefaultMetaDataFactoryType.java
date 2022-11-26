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

public interface DefaultMetaDataFactoryType extends MetaDataFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultMetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultmetadatafactorytype74a9type");

   public static final class Factory {
      public static DefaultMetaDataFactoryType newInstance() {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType newInstance(XmlOptions options) {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMetaDataFactoryType.type, options);
      }

      public static DefaultMetaDataFactoryType parse(Node node) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static DefaultMetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultMetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

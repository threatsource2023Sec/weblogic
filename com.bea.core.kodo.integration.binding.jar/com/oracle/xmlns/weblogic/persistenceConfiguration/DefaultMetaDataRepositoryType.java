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

public interface DefaultMetaDataRepositoryType extends MetaDataRepositoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultMetaDataRepositoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultmetadatarepositorytype6209type");

   public static final class Factory {
      public static DefaultMetaDataRepositoryType newInstance() {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType newInstance(XmlOptions options) {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(String xmlAsString) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(File file) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(URL u) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(InputStream is) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(Reader r) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMetaDataRepositoryType.type, options);
      }

      public static DefaultMetaDataRepositoryType parse(Node node) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static DefaultMetaDataRepositoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultMetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static DefaultMetaDataRepositoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultMetaDataRepositoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMetaDataRepositoryType.type, options);
      }

      private Factory() {
      }
   }
}

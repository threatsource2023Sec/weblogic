package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface MetaDataRepositoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MetaDataRepositoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("metadatarepositorytype0e95type");

   public static final class Factory {
      public static MetaDataRepositoryType newInstance() {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType newInstance(XmlOptions options) {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(String xmlAsString) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(File file) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(URL u) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(InputStream is) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(Reader r) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(XMLStreamReader sr) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, MetaDataRepositoryType.type, options);
      }

      public static MetaDataRepositoryType parse(Node node) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static MetaDataRepositoryType parse(Node node, XmlOptions options) throws XmlException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, MetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static MetaDataRepositoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MetaDataRepositoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, MetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetaDataRepositoryType.type, options);
      }

      private Factory() {
      }
   }
}

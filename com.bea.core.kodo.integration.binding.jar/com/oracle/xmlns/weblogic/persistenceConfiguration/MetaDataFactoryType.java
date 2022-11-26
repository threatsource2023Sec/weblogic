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

public interface MetaDataFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("metadatafactorytypef39dtype");

   public static final class Factory {
      public static MetaDataFactoryType newInstance() {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType newInstance(XmlOptions options) {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MetaDataFactoryType.type, options);
      }

      public static MetaDataFactoryType parse(Node node) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, MetaDataFactoryType.type, (XmlOptions)null);
      }

      public static MetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, MetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static MetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

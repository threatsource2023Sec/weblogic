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

public interface MappingFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MappingFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("mappingfactorytype9801type");

   public static final class Factory {
      public static MappingFactoryType newInstance() {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType newInstance(XmlOptions options) {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(String xmlAsString) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(File file) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(URL u) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(InputStream is) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(Reader r) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MappingFactoryType.type, options);
      }

      public static MappingFactoryType parse(Node node) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, MappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, MappingFactoryType.type, options);
      }

      /** @deprecated */
      public static MappingFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MappingFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MappingFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

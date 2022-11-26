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

public interface CustomMappingFactoryType extends MappingFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomMappingFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custommappingfactorytype9c6btype");

   public static final class Factory {
      public static CustomMappingFactoryType newInstance() {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType newInstance(XmlOptions options) {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(String xmlAsString) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(File file) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(URL u) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(Reader r) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMappingFactoryType.type, options);
      }

      public static CustomMappingFactoryType parse(Node node) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      public static CustomMappingFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static CustomMappingFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomMappingFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMappingFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

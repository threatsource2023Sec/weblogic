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

public interface DefaultMappingDefaultsType extends MappingDefaultsType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultMappingDefaultsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultmappingdefaultstypef31dtype");

   public static final class Factory {
      public static DefaultMappingDefaultsType newInstance() {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType newInstance(XmlOptions options) {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(String xmlAsString) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(File file) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(URL u) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(InputStream is) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(Reader r) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMappingDefaultsType.type, options);
      }

      public static DefaultMappingDefaultsType parse(Node node) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DefaultMappingDefaultsType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, DefaultMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static DefaultMappingDefaultsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultMappingDefaultsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMappingDefaultsType.type, options);
      }

      private Factory() {
      }
   }
}

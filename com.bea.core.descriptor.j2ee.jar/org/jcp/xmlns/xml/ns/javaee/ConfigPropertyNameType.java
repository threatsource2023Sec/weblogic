package org.jcp.xmlns.xml.ns.javaee;

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

public interface ConfigPropertyNameType extends XsdStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertyNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("configpropertynametypef3d3type");

   public static final class Factory {
      public static ConfigPropertyNameType newInstance() {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType newInstance(XmlOptions options) {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(File file) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(URL u) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyNameType.type, options);
      }

      public static ConfigPropertyNameType parse(Node node) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyNameType.type, options);
      }

      /** @deprecated */
      public static ConfigPropertyNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertyNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertyNameType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyNameType.type, options);
      }

      private Factory() {
      }
   }
}

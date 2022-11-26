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

public interface DefaultSchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultSchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultschemafactorytype63d0type");

   public static final class Factory {
      public static DefaultSchemaFactoryType newInstance() {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType newInstance(XmlOptions options) {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(File file) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSchemaFactoryType.type, options);
      }

      public static DefaultSchemaFactoryType parse(Node node) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static DefaultSchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultSchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

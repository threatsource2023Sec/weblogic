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

public interface DynamicSchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DynamicSchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("dynamicschemafactorytypea94etype");

   public static final class Factory {
      public static DynamicSchemaFactoryType newInstance() {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType newInstance(XmlOptions options) {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(File file) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DynamicSchemaFactoryType.type, options);
      }

      public static DynamicSchemaFactoryType parse(Node node) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      public static DynamicSchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, DynamicSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static DynamicSchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DynamicSchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DynamicSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DynamicSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DynamicSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DynamicSchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

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

public interface SchemaFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("schemafactorytype695ctype");

   public static final class Factory {
      public static SchemaFactoryType newInstance() {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType newInstance(XmlOptions options) {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(File file) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, SchemaFactoryType.type, options);
      }

      public static SchemaFactoryType parse(Node node) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, SchemaFactoryType.type, (XmlOptions)null);
      }

      public static SchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, SchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static SchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, SchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, SchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

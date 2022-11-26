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

public interface SqlFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SqlFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("sqlfactorytype0f81type");

   public static final class Factory {
      public static SqlFactoryType newInstance() {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType newInstance(XmlOptions options) {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(String xmlAsString) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(File file) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(URL u) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(InputStream is) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(Reader r) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, SqlFactoryType.type, options);
      }

      public static SqlFactoryType parse(Node node) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, SqlFactoryType.type, (XmlOptions)null);
      }

      public static SqlFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, SqlFactoryType.type, options);
      }

      /** @deprecated */
      public static SqlFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, SqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SqlFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, SqlFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

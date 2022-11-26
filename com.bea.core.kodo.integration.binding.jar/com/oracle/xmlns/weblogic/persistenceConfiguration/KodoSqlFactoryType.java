package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface KodoSqlFactoryType extends SqlFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoSqlFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodosqlfactorytype0a6dtype");

   String getAdvancedSql();

   XmlString xgetAdvancedSql();

   boolean isNilAdvancedSql();

   boolean isSetAdvancedSql();

   void setAdvancedSql(String var1);

   void xsetAdvancedSql(XmlString var1);

   void setNilAdvancedSql();

   void unsetAdvancedSql();

   public static final class Factory {
      public static KodoSqlFactoryType newInstance() {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType newInstance(XmlOptions options) {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(String xmlAsString) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(File file) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(URL u) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(InputStream is) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(Reader r) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoSqlFactoryType.type, options);
      }

      public static KodoSqlFactoryType parse(Node node) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      public static KodoSqlFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, KodoSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static KodoSqlFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoSqlFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoSqlFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface JdbcConnectionPoolType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdbcConnectionPoolType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("jdbcconnectionpooltype7874type");

   String getDataSourceJndiName();

   XmlString xgetDataSourceJndiName();

   void setDataSourceJndiName(String var1);

   void xsetDataSourceJndiName(XmlString var1);

   ConnectionFactoryType getConnectionFactory();

   void setConnectionFactory(ConnectionFactoryType var1);

   ConnectionFactoryType addNewConnectionFactory();

   ApplicationPoolParamsType getPoolParams();

   boolean isSetPoolParams();

   void setPoolParams(ApplicationPoolParamsType var1);

   ApplicationPoolParamsType addNewPoolParams();

   void unsetPoolParams();

   DriverParamsType getDriverParams();

   boolean isSetDriverParams();

   void setDriverParams(DriverParamsType var1);

   DriverParamsType addNewDriverParams();

   void unsetDriverParams();

   String getAclName();

   XmlString xgetAclName();

   boolean isSetAclName();

   void setAclName(String var1);

   void xsetAclName(XmlString var1);

   void unsetAclName();

   public static final class Factory {
      public static JdbcConnectionPoolType newInstance() {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().newInstance(JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType newInstance(XmlOptions options) {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().newInstance(JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(String xmlAsString) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(File file) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(file, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(file, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(URL u) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(u, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(u, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(InputStream is) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(is, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(is, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(Reader r) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(r, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(r, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(XMLStreamReader sr) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(sr, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(sr, JdbcConnectionPoolType.type, options);
      }

      public static JdbcConnectionPoolType parse(Node node) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(node, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      public static JdbcConnectionPoolType parse(Node node, XmlOptions options) throws XmlException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(node, JdbcConnectionPoolType.type, options);
      }

      /** @deprecated */
      public static JdbcConnectionPoolType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(xis, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdbcConnectionPoolType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdbcConnectionPoolType)XmlBeans.getContextTypeLoader().parse(xis, JdbcConnectionPoolType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcConnectionPoolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcConnectionPoolType.type, options);
      }

      private Factory() {
      }
   }
}

package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface DataSourceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("datasourcetypee5d9type");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   FullyQualifiedClassType getClassName();

   boolean isSetClassName();

   void setClassName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClassName();

   void unsetClassName();

   String getServerName();

   boolean isSetServerName();

   void setServerName(String var1);

   String addNewServerName();

   void unsetServerName();

   XsdIntegerType getPortNumber();

   boolean isSetPortNumber();

   void setPortNumber(XsdIntegerType var1);

   XsdIntegerType addNewPortNumber();

   void unsetPortNumber();

   String getDatabaseName();

   boolean isSetDatabaseName();

   void setDatabaseName(String var1);

   String addNewDatabaseName();

   void unsetDatabaseName();

   JdbcUrlType getUrl();

   boolean isSetUrl();

   void setUrl(JdbcUrlType var1);

   JdbcUrlType addNewUrl();

   void unsetUrl();

   String getUser();

   boolean isSetUser();

   void setUser(String var1);

   String addNewUser();

   void unsetUser();

   String getPassword();

   boolean isSetPassword();

   void setPassword(String var1);

   String addNewPassword();

   void unsetPassword();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   XsdIntegerType getLoginTimeout();

   boolean isSetLoginTimeout();

   void setLoginTimeout(XsdIntegerType var1);

   XsdIntegerType addNewLoginTimeout();

   void unsetLoginTimeout();

   XsdBooleanType getTransactional();

   boolean isSetTransactional();

   void setTransactional(XsdBooleanType var1);

   XsdBooleanType addNewTransactional();

   void unsetTransactional();

   IsolationLevelType.Enum getIsolationLevel();

   IsolationLevelType xgetIsolationLevel();

   boolean isSetIsolationLevel();

   void setIsolationLevel(IsolationLevelType.Enum var1);

   void xsetIsolationLevel(IsolationLevelType var1);

   void unsetIsolationLevel();

   XsdIntegerType getInitialPoolSize();

   boolean isSetInitialPoolSize();

   void setInitialPoolSize(XsdIntegerType var1);

   XsdIntegerType addNewInitialPoolSize();

   void unsetInitialPoolSize();

   XsdIntegerType getMaxPoolSize();

   boolean isSetMaxPoolSize();

   void setMaxPoolSize(XsdIntegerType var1);

   XsdIntegerType addNewMaxPoolSize();

   void unsetMaxPoolSize();

   XsdIntegerType getMinPoolSize();

   boolean isSetMinPoolSize();

   void setMinPoolSize(XsdIntegerType var1);

   XsdIntegerType addNewMinPoolSize();

   void unsetMinPoolSize();

   XsdIntegerType getMaxIdleTime();

   boolean isSetMaxIdleTime();

   void setMaxIdleTime(XsdIntegerType var1);

   XsdIntegerType addNewMaxIdleTime();

   void unsetMaxIdleTime();

   XsdIntegerType getMaxStatements();

   boolean isSetMaxStatements();

   void setMaxStatements(XsdIntegerType var1);

   XsdIntegerType addNewMaxStatements();

   void unsetMaxStatements();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DataSourceType newInstance() {
         return (DataSourceType)XmlBeans.getContextTypeLoader().newInstance(DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType newInstance(XmlOptions options) {
         return (DataSourceType)XmlBeans.getContextTypeLoader().newInstance(DataSourceType.type, options);
      }

      public static DataSourceType parse(java.lang.String xmlAsString) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataSourceType.type, options);
      }

      public static DataSourceType parse(File file) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(file, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(file, DataSourceType.type, options);
      }

      public static DataSourceType parse(URL u) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(u, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(u, DataSourceType.type, options);
      }

      public static DataSourceType parse(InputStream is) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(is, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(is, DataSourceType.type, options);
      }

      public static DataSourceType parse(Reader r) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(r, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(r, DataSourceType.type, options);
      }

      public static DataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DataSourceType.type, options);
      }

      public static DataSourceType parse(Node node) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(node, DataSourceType.type, (XmlOptions)null);
      }

      public static DataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(node, DataSourceType.type, options);
      }

      /** @deprecated */
      public static DataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataSourceType.type, options);
      }

      private Factory() {
      }
   }
}

package org.jcp.xmlns.xml.ns.javaee;

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

public interface ConnectionFactoryResourceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionFactoryResourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("connectionfactoryresourcetype92bdtype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   FullyQualifiedClassType getInterfaceName();

   void setInterfaceName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInterfaceName();

   String getResourceAdapter();

   void setResourceAdapter(String var1);

   String addNewResourceAdapter();

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

   TransactionSupportType getTransactionSupport();

   boolean isSetTransactionSupport();

   void setTransactionSupport(TransactionSupportType var1);

   TransactionSupportType addNewTransactionSupport();

   void unsetTransactionSupport();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectionFactoryResourceType newInstance() {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType newInstance(XmlOptions options) {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(File file) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(URL u) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(Reader r) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryResourceType.type, options);
      }

      public static ConnectionFactoryResourceType parse(Node node) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryResourceType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryResourceType.type, options);
      }

      /** @deprecated */
      public static ConnectionFactoryResourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionFactoryResourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionFactoryResourceType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryResourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryResourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryResourceType.type, options);
      }

      private Factory() {
      }
   }
}

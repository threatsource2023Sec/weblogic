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

public interface JmsConnectionFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsConnectionFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("jmsconnectionfactorytypedfd3type");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   FullyQualifiedClassType getInterfaceName();

   boolean isSetInterfaceName();

   void setInterfaceName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInterfaceName();

   void unsetInterfaceName();

   FullyQualifiedClassType getClassName();

   boolean isSetClassName();

   void setClassName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClassName();

   void unsetClassName();

   String getResourceAdapter();

   boolean isSetResourceAdapter();

   void setResourceAdapter(String var1);

   String addNewResourceAdapter();

   void unsetResourceAdapter();

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

   String getClientId();

   boolean isSetClientId();

   void setClientId(String var1);

   String addNewClientId();

   void unsetClientId();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   XsdBooleanType getTransactional();

   boolean isSetTransactional();

   void setTransactional(XsdBooleanType var1);

   XsdBooleanType addNewTransactional();

   void unsetTransactional();

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

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JmsConnectionFactoryType newInstance() {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType newInstance(XmlOptions options) {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(java.lang.String xmlAsString) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(File file) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(URL u) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(InputStream is) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(Reader r) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(Node node) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, JmsConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static JmsConnectionFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsConnectionFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JmsConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsConnectionFactoryType.type, options);
      }

      private Factory() {
      }
   }
}

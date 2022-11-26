package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface ForeignServerType extends TargetableType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignServerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreignservertype7a1atype");

   ForeignDestinationType[] getForeignDestinationArray();

   ForeignDestinationType getForeignDestinationArray(int var1);

   int sizeOfForeignDestinationArray();

   void setForeignDestinationArray(ForeignDestinationType[] var1);

   void setForeignDestinationArray(int var1, ForeignDestinationType var2);

   ForeignDestinationType insertNewForeignDestination(int var1);

   ForeignDestinationType addNewForeignDestination();

   void removeForeignDestination(int var1);

   ForeignConnectionFactoryType[] getForeignConnectionFactoryArray();

   ForeignConnectionFactoryType getForeignConnectionFactoryArray(int var1);

   int sizeOfForeignConnectionFactoryArray();

   void setForeignConnectionFactoryArray(ForeignConnectionFactoryType[] var1);

   void setForeignConnectionFactoryArray(int var1, ForeignConnectionFactoryType var2);

   ForeignConnectionFactoryType insertNewForeignConnectionFactory(int var1);

   ForeignConnectionFactoryType addNewForeignConnectionFactory();

   void removeForeignConnectionFactory(int var1);

   String getInitialContextFactory();

   XmlString xgetInitialContextFactory();

   boolean isNilInitialContextFactory();

   boolean isSetInitialContextFactory();

   void setInitialContextFactory(String var1);

   void xsetInitialContextFactory(XmlString var1);

   void setNilInitialContextFactory();

   void unsetInitialContextFactory();

   String getConnectionUrl();

   XmlString xgetConnectionUrl();

   boolean isNilConnectionUrl();

   boolean isSetConnectionUrl();

   void setConnectionUrl(String var1);

   void xsetConnectionUrl(XmlString var1);

   void setNilConnectionUrl();

   void unsetConnectionUrl();

   String getJndiPropertiesCredentialEncrypted();

   XmlString xgetJndiPropertiesCredentialEncrypted();

   boolean isNilJndiPropertiesCredentialEncrypted();

   boolean isSetJndiPropertiesCredentialEncrypted();

   void setJndiPropertiesCredentialEncrypted(String var1);

   void xsetJndiPropertiesCredentialEncrypted(XmlString var1);

   void setNilJndiPropertiesCredentialEncrypted();

   void unsetJndiPropertiesCredentialEncrypted();

   PropertyType[] getJndiPropertyArray();

   PropertyType getJndiPropertyArray(int var1);

   int sizeOfJndiPropertyArray();

   void setJndiPropertyArray(PropertyType[] var1);

   void setJndiPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewJndiProperty(int var1);

   PropertyType addNewJndiProperty();

   void removeJndiProperty(int var1);

   public static final class Factory {
      public static ForeignServerType newInstance() {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().newInstance(ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType newInstance(XmlOptions options) {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().newInstance(ForeignServerType.type, options);
      }

      public static ForeignServerType parse(String xmlAsString) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(File file) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(file, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(file, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(URL u) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(u, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(u, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(InputStream is) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(is, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(is, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(Reader r) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(r, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(r, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(sr, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(sr, ForeignServerType.type, options);
      }

      public static ForeignServerType parse(Node node) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(node, ForeignServerType.type, (XmlOptions)null);
      }

      public static ForeignServerType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(node, ForeignServerType.type, options);
      }

      /** @deprecated */
      public static ForeignServerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(xis, ForeignServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignServerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignServerType)XmlBeans.getContextTypeLoader().parse(xis, ForeignServerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignServerType.type, options);
      }

      private Factory() {
      }
   }
}

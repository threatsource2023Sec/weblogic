package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface ClientSafType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientSafType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("clientsaftype7ae0type");

   DefaultPersistentStoreType getPersistentStore();

   boolean isSetPersistentStore();

   void setPersistentStore(DefaultPersistentStoreType var1);

   DefaultPersistentStoreType addNewPersistentStore();

   void unsetPersistentStore();

   DefaultSafAgentType getSafAgent();

   boolean isSetSafAgent();

   void setSafAgent(DefaultSafAgentType var1);

   DefaultSafAgentType addNewSafAgent();

   void unsetSafAgent();

   JmsConnectionFactoryType[] getConnectionFactoryArray();

   JmsConnectionFactoryType getConnectionFactoryArray(int var1);

   int sizeOfConnectionFactoryArray();

   void setConnectionFactoryArray(JmsConnectionFactoryType[] var1);

   void setConnectionFactoryArray(int var1, JmsConnectionFactoryType var2);

   JmsConnectionFactoryType insertNewConnectionFactory(int var1);

   JmsConnectionFactoryType addNewConnectionFactory();

   void removeConnectionFactory(int var1);

   SafImportedDestinationsType[] getSafImportedDestinationsArray();

   SafImportedDestinationsType getSafImportedDestinationsArray(int var1);

   int sizeOfSafImportedDestinationsArray();

   void setSafImportedDestinationsArray(SafImportedDestinationsType[] var1);

   void setSafImportedDestinationsArray(int var1, SafImportedDestinationsType var2);

   SafImportedDestinationsType insertNewSafImportedDestinations(int var1);

   SafImportedDestinationsType addNewSafImportedDestinations();

   void removeSafImportedDestinations(int var1);

   SafRemoteContextType[] getSafRemoteContextArray();

   SafRemoteContextType getSafRemoteContextArray(int var1);

   int sizeOfSafRemoteContextArray();

   void setSafRemoteContextArray(SafRemoteContextType[] var1);

   void setSafRemoteContextArray(int var1, SafRemoteContextType var2);

   SafRemoteContextType insertNewSafRemoteContext(int var1);

   SafRemoteContextType addNewSafRemoteContext();

   void removeSafRemoteContext(int var1);

   SafErrorHandlingType[] getSafErrorHandlingArray();

   SafErrorHandlingType getSafErrorHandlingArray(int var1);

   int sizeOfSafErrorHandlingArray();

   void setSafErrorHandlingArray(SafErrorHandlingType[] var1);

   void setSafErrorHandlingArray(int var1, SafErrorHandlingType var2);

   SafErrorHandlingType insertNewSafErrorHandling(int var1);

   SafErrorHandlingType addNewSafErrorHandling();

   void removeSafErrorHandling(int var1);

   public static final class Factory {
      public static ClientSafType newInstance() {
         return (ClientSafType)XmlBeans.getContextTypeLoader().newInstance(ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType newInstance(XmlOptions options) {
         return (ClientSafType)XmlBeans.getContextTypeLoader().newInstance(ClientSafType.type, options);
      }

      public static ClientSafType parse(String xmlAsString) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientSafType.type, options);
      }

      public static ClientSafType parse(File file) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(file, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(file, ClientSafType.type, options);
      }

      public static ClientSafType parse(URL u) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(u, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(u, ClientSafType.type, options);
      }

      public static ClientSafType parse(InputStream is) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(is, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(is, ClientSafType.type, options);
      }

      public static ClientSafType parse(Reader r) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(r, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(r, ClientSafType.type, options);
      }

      public static ClientSafType parse(XMLStreamReader sr) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(sr, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(sr, ClientSafType.type, options);
      }

      public static ClientSafType parse(Node node) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(node, ClientSafType.type, (XmlOptions)null);
      }

      public static ClientSafType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(node, ClientSafType.type, options);
      }

      /** @deprecated */
      public static ClientSafType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(xis, ClientSafType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientSafType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientSafType)XmlBeans.getContextTypeLoader().parse(xis, ClientSafType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientSafType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientSafType.type, options);
      }

      private Factory() {
      }
   }
}

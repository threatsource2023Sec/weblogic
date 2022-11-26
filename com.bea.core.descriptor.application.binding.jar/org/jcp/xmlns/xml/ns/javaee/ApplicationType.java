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

public interface ApplicationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("applicationtype35edtype");

   String getApplicationName();

   boolean isSetApplicationName();

   void setApplicationName(String var1);

   String addNewApplicationName();

   void unsetApplicationName();

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   GenericBooleanType getInitializeInOrder();

   boolean isSetInitializeInOrder();

   void setInitializeInOrder(GenericBooleanType var1);

   GenericBooleanType addNewInitializeInOrder();

   void unsetInitializeInOrder();

   ModuleType[] getModuleArray();

   ModuleType getModuleArray(int var1);

   int sizeOfModuleArray();

   void setModuleArray(ModuleType[] var1);

   void setModuleArray(int var1, ModuleType var2);

   ModuleType insertNewModule(int var1);

   ModuleType addNewModule();

   void removeModule(int var1);

   SecurityRoleType[] getSecurityRoleArray();

   SecurityRoleType getSecurityRoleArray(int var1);

   int sizeOfSecurityRoleArray();

   void setSecurityRoleArray(SecurityRoleType[] var1);

   void setSecurityRoleArray(int var1, SecurityRoleType var2);

   SecurityRoleType insertNewSecurityRole(int var1);

   SecurityRoleType addNewSecurityRole();

   void removeSecurityRole(int var1);

   PathType getLibraryDirectory();

   boolean isSetLibraryDirectory();

   void setLibraryDirectory(PathType var1);

   PathType addNewLibraryDirectory();

   void unsetLibraryDirectory();

   EnvEntryType[] getEnvEntryArray();

   EnvEntryType getEnvEntryArray(int var1);

   int sizeOfEnvEntryArray();

   void setEnvEntryArray(EnvEntryType[] var1);

   void setEnvEntryArray(int var1, EnvEntryType var2);

   EnvEntryType insertNewEnvEntry(int var1);

   EnvEntryType addNewEnvEntry();

   void removeEnvEntry(int var1);

   EjbRefType[] getEjbRefArray();

   EjbRefType getEjbRefArray(int var1);

   int sizeOfEjbRefArray();

   void setEjbRefArray(EjbRefType[] var1);

   void setEjbRefArray(int var1, EjbRefType var2);

   EjbRefType insertNewEjbRef(int var1);

   EjbRefType addNewEjbRef();

   void removeEjbRef(int var1);

   EjbLocalRefType[] getEjbLocalRefArray();

   EjbLocalRefType getEjbLocalRefArray(int var1);

   int sizeOfEjbLocalRefArray();

   void setEjbLocalRefArray(EjbLocalRefType[] var1);

   void setEjbLocalRefArray(int var1, EjbLocalRefType var2);

   EjbLocalRefType insertNewEjbLocalRef(int var1);

   EjbLocalRefType addNewEjbLocalRef();

   void removeEjbLocalRef(int var1);

   ServiceRefType[] getServiceRefArray();

   ServiceRefType getServiceRefArray(int var1);

   int sizeOfServiceRefArray();

   void setServiceRefArray(ServiceRefType[] var1);

   void setServiceRefArray(int var1, ServiceRefType var2);

   ServiceRefType insertNewServiceRef(int var1);

   ServiceRefType addNewServiceRef();

   void removeServiceRef(int var1);

   ResourceRefType[] getResourceRefArray();

   ResourceRefType getResourceRefArray(int var1);

   int sizeOfResourceRefArray();

   void setResourceRefArray(ResourceRefType[] var1);

   void setResourceRefArray(int var1, ResourceRefType var2);

   ResourceRefType insertNewResourceRef(int var1);

   ResourceRefType addNewResourceRef();

   void removeResourceRef(int var1);

   ResourceEnvRefType[] getResourceEnvRefArray();

   ResourceEnvRefType getResourceEnvRefArray(int var1);

   int sizeOfResourceEnvRefArray();

   void setResourceEnvRefArray(ResourceEnvRefType[] var1);

   void setResourceEnvRefArray(int var1, ResourceEnvRefType var2);

   ResourceEnvRefType insertNewResourceEnvRef(int var1);

   ResourceEnvRefType addNewResourceEnvRef();

   void removeResourceEnvRef(int var1);

   MessageDestinationRefType[] getMessageDestinationRefArray();

   MessageDestinationRefType getMessageDestinationRefArray(int var1);

   int sizeOfMessageDestinationRefArray();

   void setMessageDestinationRefArray(MessageDestinationRefType[] var1);

   void setMessageDestinationRefArray(int var1, MessageDestinationRefType var2);

   MessageDestinationRefType insertNewMessageDestinationRef(int var1);

   MessageDestinationRefType addNewMessageDestinationRef();

   void removeMessageDestinationRef(int var1);

   PersistenceContextRefType[] getPersistenceContextRefArray();

   PersistenceContextRefType getPersistenceContextRefArray(int var1);

   int sizeOfPersistenceContextRefArray();

   void setPersistenceContextRefArray(PersistenceContextRefType[] var1);

   void setPersistenceContextRefArray(int var1, PersistenceContextRefType var2);

   PersistenceContextRefType insertNewPersistenceContextRef(int var1);

   PersistenceContextRefType addNewPersistenceContextRef();

   void removePersistenceContextRef(int var1);

   PersistenceUnitRefType[] getPersistenceUnitRefArray();

   PersistenceUnitRefType getPersistenceUnitRefArray(int var1);

   int sizeOfPersistenceUnitRefArray();

   void setPersistenceUnitRefArray(PersistenceUnitRefType[] var1);

   void setPersistenceUnitRefArray(int var1, PersistenceUnitRefType var2);

   PersistenceUnitRefType insertNewPersistenceUnitRef(int var1);

   PersistenceUnitRefType addNewPersistenceUnitRef();

   void removePersistenceUnitRef(int var1);

   MessageDestinationType[] getMessageDestinationArray();

   MessageDestinationType getMessageDestinationArray(int var1);

   int sizeOfMessageDestinationArray();

   void setMessageDestinationArray(MessageDestinationType[] var1);

   void setMessageDestinationArray(int var1, MessageDestinationType var2);

   MessageDestinationType insertNewMessageDestination(int var1);

   MessageDestinationType addNewMessageDestination();

   void removeMessageDestination(int var1);

   DataSourceType[] getDataSourceArray();

   DataSourceType getDataSourceArray(int var1);

   int sizeOfDataSourceArray();

   void setDataSourceArray(DataSourceType[] var1);

   void setDataSourceArray(int var1, DataSourceType var2);

   DataSourceType insertNewDataSource(int var1);

   DataSourceType addNewDataSource();

   void removeDataSource(int var1);

   JmsConnectionFactoryType[] getJmsConnectionFactoryArray();

   JmsConnectionFactoryType getJmsConnectionFactoryArray(int var1);

   int sizeOfJmsConnectionFactoryArray();

   void setJmsConnectionFactoryArray(JmsConnectionFactoryType[] var1);

   void setJmsConnectionFactoryArray(int var1, JmsConnectionFactoryType var2);

   JmsConnectionFactoryType insertNewJmsConnectionFactory(int var1);

   JmsConnectionFactoryType addNewJmsConnectionFactory();

   void removeJmsConnectionFactory(int var1);

   JmsDestinationType[] getJmsDestinationArray();

   JmsDestinationType getJmsDestinationArray(int var1);

   int sizeOfJmsDestinationArray();

   void setJmsDestinationArray(JmsDestinationType[] var1);

   void setJmsDestinationArray(int var1, JmsDestinationType var2);

   JmsDestinationType insertNewJmsDestination(int var1);

   JmsDestinationType addNewJmsDestination();

   void removeJmsDestination(int var1);

   MailSessionType[] getMailSessionArray();

   MailSessionType getMailSessionArray(int var1);

   int sizeOfMailSessionArray();

   void setMailSessionArray(MailSessionType[] var1);

   void setMailSessionArray(int var1, MailSessionType var2);

   MailSessionType insertNewMailSession(int var1);

   MailSessionType addNewMailSession();

   void removeMailSession(int var1);

   ConnectionFactoryResourceType[] getConnectionFactoryArray();

   ConnectionFactoryResourceType getConnectionFactoryArray(int var1);

   int sizeOfConnectionFactoryArray();

   void setConnectionFactoryArray(ConnectionFactoryResourceType[] var1);

   void setConnectionFactoryArray(int var1, ConnectionFactoryResourceType var2);

   ConnectionFactoryResourceType insertNewConnectionFactory(int var1);

   ConnectionFactoryResourceType addNewConnectionFactory();

   void removeConnectionFactory(int var1);

   AdministeredObjectType[] getAdministeredObjectArray();

   AdministeredObjectType getAdministeredObjectArray(int var1);

   int sizeOfAdministeredObjectArray();

   void setAdministeredObjectArray(AdministeredObjectType[] var1);

   void setAdministeredObjectArray(int var1, AdministeredObjectType var2);

   AdministeredObjectType insertNewAdministeredObject(int var1);

   AdministeredObjectType addNewAdministeredObject();

   void removeAdministeredObject(int var1);

   java.lang.String getVersion();

   DeweyVersionType xgetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(DeweyVersionType var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ApplicationType newInstance() {
         return (ApplicationType)XmlBeans.getContextTypeLoader().newInstance(ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType newInstance(XmlOptions options) {
         return (ApplicationType)XmlBeans.getContextTypeLoader().newInstance(ApplicationType.type, options);
      }

      public static ApplicationType parse(java.lang.String xmlAsString) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationType.type, options);
      }

      public static ApplicationType parse(File file) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(file, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(file, ApplicationType.type, options);
      }

      public static ApplicationType parse(URL u) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(u, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(u, ApplicationType.type, options);
      }

      public static ApplicationType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(is, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(is, ApplicationType.type, options);
      }

      public static ApplicationType parse(Reader r) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(r, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(r, ApplicationType.type, options);
      }

      public static ApplicationType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationType.type, options);
      }

      public static ApplicationType parse(Node node) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(node, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(node, ApplicationType.type, options);
      }

      /** @deprecated */
      public static ApplicationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationType.type, options);
      }

      private Factory() {
      }
   }
}

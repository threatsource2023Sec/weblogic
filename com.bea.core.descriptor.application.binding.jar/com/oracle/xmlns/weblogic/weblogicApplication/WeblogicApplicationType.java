package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicApplicationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicApplicationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("weblogicapplicationtypead28type");

   EjbType getEjb();

   boolean isSetEjb();

   void setEjb(EjbType var1);

   EjbType addNewEjb();

   void unsetEjb();

   XmlType getXml();

   boolean isSetXml();

   void setXml(XmlType var1);

   XmlType addNewXml();

   void unsetXml();

   JdbcConnectionPoolType[] getJdbcConnectionPoolArray();

   JdbcConnectionPoolType getJdbcConnectionPoolArray(int var1);

   int sizeOfJdbcConnectionPoolArray();

   void setJdbcConnectionPoolArray(JdbcConnectionPoolType[] var1);

   void setJdbcConnectionPoolArray(int var1, JdbcConnectionPoolType var2);

   JdbcConnectionPoolType insertNewJdbcConnectionPool(int var1);

   JdbcConnectionPoolType addNewJdbcConnectionPool();

   void removeJdbcConnectionPool(int var1);

   SecurityType getSecurity();

   boolean isSetSecurity();

   void setSecurity(SecurityType var1);

   SecurityType addNewSecurity();

   void unsetSecurity();

   ApplicationParamType[] getApplicationParamArray();

   ApplicationParamType getApplicationParamArray(int var1);

   int sizeOfApplicationParamArray();

   void setApplicationParamArray(ApplicationParamType[] var1);

   void setApplicationParamArray(int var1, ApplicationParamType var2);

   ApplicationParamType insertNewApplicationParam(int var1);

   ApplicationParamType addNewApplicationParam();

   void removeApplicationParam(int var1);

   ClassloaderStructureType getClassloaderStructure();

   boolean isSetClassloaderStructure();

   void setClassloaderStructure(ClassloaderStructureType var1);

   ClassloaderStructureType addNewClassloaderStructure();

   void unsetClassloaderStructure();

   ListenerType[] getListenerArray();

   ListenerType getListenerArray(int var1);

   int sizeOfListenerArray();

   void setListenerArray(ListenerType[] var1);

   void setListenerArray(int var1, ListenerType var2);

   ListenerType insertNewListener(int var1);

   ListenerType addNewListener();

   void removeListener(int var1);

   SingletonServiceType[] getSingletonServiceArray();

   SingletonServiceType getSingletonServiceArray(int var1);

   int sizeOfSingletonServiceArray();

   void setSingletonServiceArray(SingletonServiceType[] var1);

   void setSingletonServiceArray(int var1, SingletonServiceType var2);

   SingletonServiceType insertNewSingletonService(int var1);

   SingletonServiceType addNewSingletonService();

   void removeSingletonService(int var1);

   StartupType[] getStartupArray();

   StartupType getStartupArray(int var1);

   int sizeOfStartupArray();

   void setStartupArray(StartupType[] var1);

   void setStartupArray(int var1, StartupType var2);

   StartupType insertNewStartup(int var1);

   StartupType addNewStartup();

   void removeStartup(int var1);

   ShutdownType[] getShutdownArray();

   ShutdownType getShutdownArray(int var1);

   int sizeOfShutdownArray();

   void setShutdownArray(ShutdownType[] var1);

   void setShutdownArray(int var1, ShutdownType var2);

   ShutdownType insertNewShutdown(int var1);

   ShutdownType addNewShutdown();

   void removeShutdown(int var1);

   WeblogicModuleType[] getModuleArray();

   WeblogicModuleType getModuleArray(int var1);

   int sizeOfModuleArray();

   void setModuleArray(WeblogicModuleType[] var1);

   void setModuleArray(int var1, WeblogicModuleType var2);

   WeblogicModuleType insertNewModule(int var1);

   WeblogicModuleType addNewModule();

   void removeModule(int var1);

   LibraryRefType[] getLibraryRefArray();

   LibraryRefType getLibraryRefArray(int var1);

   int sizeOfLibraryRefArray();

   void setLibraryRefArray(LibraryRefType[] var1);

   void setLibraryRefArray(int var1, LibraryRefType var2);

   LibraryRefType insertNewLibraryRef(int var1);

   LibraryRefType addNewLibraryRef();

   void removeLibraryRef(int var1);

   FairShareRequestClassType[] getFairShareRequestArray();

   FairShareRequestClassType getFairShareRequestArray(int var1);

   int sizeOfFairShareRequestArray();

   void setFairShareRequestArray(FairShareRequestClassType[] var1);

   void setFairShareRequestArray(int var1, FairShareRequestClassType var2);

   FairShareRequestClassType insertNewFairShareRequest(int var1);

   FairShareRequestClassType addNewFairShareRequest();

   void removeFairShareRequest(int var1);

   ResponseTimeRequestClassType[] getResponseTimeRequestArray();

   ResponseTimeRequestClassType getResponseTimeRequestArray(int var1);

   int sizeOfResponseTimeRequestArray();

   void setResponseTimeRequestArray(ResponseTimeRequestClassType[] var1);

   void setResponseTimeRequestArray(int var1, ResponseTimeRequestClassType var2);

   ResponseTimeRequestClassType insertNewResponseTimeRequest(int var1);

   ResponseTimeRequestClassType addNewResponseTimeRequest();

   void removeResponseTimeRequest(int var1);

   ContextRequestClassType[] getContextRequestArray();

   ContextRequestClassType getContextRequestArray(int var1);

   int sizeOfContextRequestArray();

   void setContextRequestArray(ContextRequestClassType[] var1);

   void setContextRequestArray(int var1, ContextRequestClassType var2);

   ContextRequestClassType insertNewContextRequest(int var1);

   ContextRequestClassType addNewContextRequest();

   void removeContextRequest(int var1);

   MaxThreadsConstraintType[] getMaxThreadsConstraintArray();

   MaxThreadsConstraintType getMaxThreadsConstraintArray(int var1);

   int sizeOfMaxThreadsConstraintArray();

   void setMaxThreadsConstraintArray(MaxThreadsConstraintType[] var1);

   void setMaxThreadsConstraintArray(int var1, MaxThreadsConstraintType var2);

   MaxThreadsConstraintType insertNewMaxThreadsConstraint(int var1);

   MaxThreadsConstraintType addNewMaxThreadsConstraint();

   void removeMaxThreadsConstraint(int var1);

   MinThreadsConstraintType[] getMinThreadsConstraintArray();

   MinThreadsConstraintType getMinThreadsConstraintArray(int var1);

   int sizeOfMinThreadsConstraintArray();

   void setMinThreadsConstraintArray(MinThreadsConstraintType[] var1);

   void setMinThreadsConstraintArray(int var1, MinThreadsConstraintType var2);

   MinThreadsConstraintType insertNewMinThreadsConstraint(int var1);

   MinThreadsConstraintType addNewMinThreadsConstraint();

   void removeMinThreadsConstraint(int var1);

   CapacityType[] getCapacityArray();

   CapacityType getCapacityArray(int var1);

   int sizeOfCapacityArray();

   void setCapacityArray(CapacityType[] var1);

   void setCapacityArray(int var1, CapacityType var2);

   CapacityType insertNewCapacity(int var1);

   CapacityType addNewCapacity();

   void removeCapacity(int var1);

   WorkManagerType[] getWorkManagerArray();

   WorkManagerType getWorkManagerArray(int var1);

   int sizeOfWorkManagerArray();

   void setWorkManagerArray(WorkManagerType[] var1);

   void setWorkManagerArray(int var1, WorkManagerType var2);

   WorkManagerType insertNewWorkManager(int var1);

   WorkManagerType addNewWorkManager();

   void removeWorkManager(int var1);

   ManagedExecutorServiceType[] getManagedExecutorServiceArray();

   ManagedExecutorServiceType getManagedExecutorServiceArray(int var1);

   int sizeOfManagedExecutorServiceArray();

   void setManagedExecutorServiceArray(ManagedExecutorServiceType[] var1);

   void setManagedExecutorServiceArray(int var1, ManagedExecutorServiceType var2);

   ManagedExecutorServiceType insertNewManagedExecutorService(int var1);

   ManagedExecutorServiceType addNewManagedExecutorService();

   void removeManagedExecutorService(int var1);

   ManagedScheduledExecutorServiceType[] getManagedScheduledExecutorServiceArray();

   ManagedScheduledExecutorServiceType getManagedScheduledExecutorServiceArray(int var1);

   int sizeOfManagedScheduledExecutorServiceArray();

   void setManagedScheduledExecutorServiceArray(ManagedScheduledExecutorServiceType[] var1);

   void setManagedScheduledExecutorServiceArray(int var1, ManagedScheduledExecutorServiceType var2);

   ManagedScheduledExecutorServiceType insertNewManagedScheduledExecutorService(int var1);

   ManagedScheduledExecutorServiceType addNewManagedScheduledExecutorService();

   void removeManagedScheduledExecutorService(int var1);

   ManagedThreadFactoryType[] getManagedThreadFactoryArray();

   ManagedThreadFactoryType getManagedThreadFactoryArray(int var1);

   int sizeOfManagedThreadFactoryArray();

   void setManagedThreadFactoryArray(ManagedThreadFactoryType[] var1);

   void setManagedThreadFactoryArray(int var1, ManagedThreadFactoryType var2);

   ManagedThreadFactoryType insertNewManagedThreadFactory(int var1);

   ManagedThreadFactoryType addNewManagedThreadFactory();

   void removeManagedThreadFactory(int var1);

   XsdStringType getComponentFactoryClassName();

   boolean isSetComponentFactoryClassName();

   void setComponentFactoryClassName(XsdStringType var1);

   XsdStringType addNewComponentFactoryClassName();

   void unsetComponentFactoryClassName();

   ApplicationAdminModeTriggerType getApplicationAdminModeTrigger();

   boolean isSetApplicationAdminModeTrigger();

   void setApplicationAdminModeTrigger(ApplicationAdminModeTriggerType var1);

   ApplicationAdminModeTriggerType addNewApplicationAdminModeTrigger();

   void unsetApplicationAdminModeTrigger();

   SessionDescriptorType getSessionDescriptor();

   boolean isSetSessionDescriptor();

   void setSessionDescriptor(SessionDescriptorType var1);

   SessionDescriptorType addNewSessionDescriptor();

   void unsetSessionDescriptor();

   LibraryContextRootOverrideType[] getLibraryContextRootOverrideArray();

   LibraryContextRootOverrideType getLibraryContextRootOverrideArray(int var1);

   int sizeOfLibraryContextRootOverrideArray();

   void setLibraryContextRootOverrideArray(LibraryContextRootOverrideType[] var1);

   void setLibraryContextRootOverrideArray(int var1, LibraryContextRootOverrideType var2);

   LibraryContextRootOverrideType insertNewLibraryContextRootOverride(int var1);

   LibraryContextRootOverrideType addNewLibraryContextRootOverride();

   void removeLibraryContextRootOverride(int var1);

   PreferApplicationPackagesType getPreferApplicationPackages();

   boolean isSetPreferApplicationPackages();

   void setPreferApplicationPackages(PreferApplicationPackagesType var1);

   PreferApplicationPackagesType addNewPreferApplicationPackages();

   void unsetPreferApplicationPackages();

   PreferApplicationResourcesType getPreferApplicationResources();

   boolean isSetPreferApplicationResources();

   void setPreferApplicationResources(PreferApplicationResourcesType var1);

   PreferApplicationResourcesType addNewPreferApplicationResources();

   void unsetPreferApplicationResources();

   FastSwapType getFastSwap();

   boolean isSetFastSwap();

   void setFastSwap(FastSwapType var1);

   FastSwapType addNewFastSwap();

   void unsetFastSwap();

   CoherenceClusterRefType getCoherenceClusterRef();

   boolean isSetCoherenceClusterRef();

   void setCoherenceClusterRef(CoherenceClusterRefType var1);

   CoherenceClusterRefType addNewCoherenceClusterRef();

   void unsetCoherenceClusterRef();

   OsgiFrameworkReferenceType getOsgiFrameworkReference();

   boolean isSetOsgiFrameworkReference();

   void setOsgiFrameworkReference(OsgiFrameworkReferenceType var1);

   OsgiFrameworkReferenceType addNewOsgiFrameworkReference();

   void unsetOsgiFrameworkReference();

   ResourceDescriptionType[] getResourceDescriptionArray();

   ResourceDescriptionType getResourceDescriptionArray(int var1);

   int sizeOfResourceDescriptionArray();

   void setResourceDescriptionArray(ResourceDescriptionType[] var1);

   void setResourceDescriptionArray(int var1, ResourceDescriptionType var2);

   ResourceDescriptionType insertNewResourceDescription(int var1);

   ResourceDescriptionType addNewResourceDescription();

   void removeResourceDescription(int var1);

   ResourceEnvDescriptionType[] getResourceEnvDescriptionArray();

   ResourceEnvDescriptionType getResourceEnvDescriptionArray(int var1);

   int sizeOfResourceEnvDescriptionArray();

   void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] var1);

   void setResourceEnvDescriptionArray(int var1, ResourceEnvDescriptionType var2);

   ResourceEnvDescriptionType insertNewResourceEnvDescription(int var1);

   ResourceEnvDescriptionType addNewResourceEnvDescription();

   void removeResourceEnvDescription(int var1);

   EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray();

   EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int var1);

   int sizeOfEjbReferenceDescriptionArray();

   void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] var1);

   void setEjbReferenceDescriptionArray(int var1, EjbReferenceDescriptionType var2);

   EjbReferenceDescriptionType insertNewEjbReferenceDescription(int var1);

   EjbReferenceDescriptionType addNewEjbReferenceDescription();

   void removeEjbReferenceDescription(int var1);

   ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray();

   ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int var1);

   int sizeOfServiceReferenceDescriptionArray();

   void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] var1);

   void setServiceReferenceDescriptionArray(int var1, ServiceReferenceDescriptionType var2);

   ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int var1);

   ServiceReferenceDescriptionType addNewServiceReferenceDescription();

   void removeServiceReferenceDescription(int var1);

   MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray();

   MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int var1);

   int sizeOfMessageDestinationDescriptorArray();

   void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] var1);

   void setMessageDestinationDescriptorArray(int var1, MessageDestinationDescriptorType var2);

   MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int var1);

   MessageDestinationDescriptorType addNewMessageDestinationDescriptor();

   void removeMessageDestinationDescriptor(int var1);

   ClassLoadingType getClassLoading();

   boolean isSetClassLoading();

   void setClassLoading(ClassLoadingType var1);

   ClassLoadingType addNewClassLoading();

   void unsetClassLoading();

   String getReadyRegistration();

   XmlString xgetReadyRegistration();

   boolean isSetReadyRegistration();

   void setReadyRegistration(String var1);

   void xsetReadyRegistration(XmlString var1);

   void unsetReadyRegistration();

   CdiDescriptorType getCdiDescriptor();

   boolean isSetCdiDescriptor();

   void setCdiDescriptor(CdiDescriptorType var1);

   CdiDescriptorType addNewCdiDescriptor();

   void unsetCdiDescriptor();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicApplicationType newInstance() {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType newInstance(XmlOptions options) {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(String xmlAsString) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(File file) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(URL u) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(Reader r) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationType.type, options);
      }

      public static WeblogicApplicationType parse(Node node) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationType.type, options);
      }

      /** @deprecated */
      public static WeblogicApplicationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicApplicationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicApplicationType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationType.type, options);
      }

      private Factory() {
      }
   }
}

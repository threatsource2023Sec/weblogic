package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface WeblogicWebAppType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWebAppType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwebapptypedfd8type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   WeblogicVersionType[] getWeblogicVersionArray();

   WeblogicVersionType getWeblogicVersionArray(int var1);

   int sizeOfWeblogicVersionArray();

   void setWeblogicVersionArray(WeblogicVersionType[] var1);

   void setWeblogicVersionArray(int var1, WeblogicVersionType var2);

   WeblogicVersionType insertNewWeblogicVersion(int var1);

   WeblogicVersionType addNewWeblogicVersion();

   void removeWeblogicVersion(int var1);

   SecurityRoleAssignmentType[] getSecurityRoleAssignmentArray();

   SecurityRoleAssignmentType getSecurityRoleAssignmentArray(int var1);

   int sizeOfSecurityRoleAssignmentArray();

   void setSecurityRoleAssignmentArray(SecurityRoleAssignmentType[] var1);

   void setSecurityRoleAssignmentArray(int var1, SecurityRoleAssignmentType var2);

   SecurityRoleAssignmentType insertNewSecurityRoleAssignment(int var1);

   SecurityRoleAssignmentType addNewSecurityRoleAssignment();

   void removeSecurityRoleAssignment(int var1);

   RunAsRoleAssignmentType[] getRunAsRoleAssignmentArray();

   RunAsRoleAssignmentType getRunAsRoleAssignmentArray(int var1);

   int sizeOfRunAsRoleAssignmentArray();

   void setRunAsRoleAssignmentArray(RunAsRoleAssignmentType[] var1);

   void setRunAsRoleAssignmentArray(int var1, RunAsRoleAssignmentType var2);

   RunAsRoleAssignmentType insertNewRunAsRoleAssignment(int var1);

   RunAsRoleAssignmentType addNewRunAsRoleAssignment();

   void removeRunAsRoleAssignment(int var1);

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

   SessionDescriptorType[] getSessionDescriptorArray();

   SessionDescriptorType getSessionDescriptorArray(int var1);

   int sizeOfSessionDescriptorArray();

   void setSessionDescriptorArray(SessionDescriptorType[] var1);

   void setSessionDescriptorArray(int var1, SessionDescriptorType var2);

   SessionDescriptorType insertNewSessionDescriptor(int var1);

   SessionDescriptorType addNewSessionDescriptor();

   void removeSessionDescriptor(int var1);

   JspDescriptorType[] getJspDescriptorArray();

   JspDescriptorType getJspDescriptorArray(int var1);

   int sizeOfJspDescriptorArray();

   void setJspDescriptorArray(JspDescriptorType[] var1);

   void setJspDescriptorArray(int var1, JspDescriptorType var2);

   JspDescriptorType insertNewJspDescriptor(int var1);

   JspDescriptorType addNewJspDescriptor();

   void removeJspDescriptor(int var1);

   AuthFilterType[] getAuthFilterArray();

   AuthFilterType getAuthFilterArray(int var1);

   int sizeOfAuthFilterArray();

   void setAuthFilterArray(AuthFilterType[] var1);

   void setAuthFilterArray(int var1, AuthFilterType var2);

   AuthFilterType insertNewAuthFilter(int var1);

   AuthFilterType addNewAuthFilter();

   void removeAuthFilter(int var1);

   ContainerDescriptorType[] getContainerDescriptorArray();

   ContainerDescriptorType getContainerDescriptorArray(int var1);

   int sizeOfContainerDescriptorArray();

   void setContainerDescriptorArray(ContainerDescriptorType[] var1);

   void setContainerDescriptorArray(int var1, ContainerDescriptorType var2);

   ContainerDescriptorType insertNewContainerDescriptor(int var1);

   ContainerDescriptorType addNewContainerDescriptor();

   void removeContainerDescriptor(int var1);

   AsyncDescriptorType[] getAsyncDescriptorArray();

   AsyncDescriptorType getAsyncDescriptorArray(int var1);

   int sizeOfAsyncDescriptorArray();

   void setAsyncDescriptorArray(AsyncDescriptorType[] var1);

   void setAsyncDescriptorArray(int var1, AsyncDescriptorType var2);

   AsyncDescriptorType insertNewAsyncDescriptor(int var1);

   AsyncDescriptorType addNewAsyncDescriptor();

   void removeAsyncDescriptor(int var1);

   CharsetParamsType[] getCharsetParamsArray();

   CharsetParamsType getCharsetParamsArray(int var1);

   int sizeOfCharsetParamsArray();

   void setCharsetParamsArray(CharsetParamsType[] var1);

   void setCharsetParamsArray(int var1, CharsetParamsType var2);

   CharsetParamsType insertNewCharsetParams(int var1);

   CharsetParamsType addNewCharsetParams();

   void removeCharsetParams(int var1);

   VirtualDirectoryMappingType[] getVirtualDirectoryMappingArray();

   VirtualDirectoryMappingType getVirtualDirectoryMappingArray(int var1);

   int sizeOfVirtualDirectoryMappingArray();

   void setVirtualDirectoryMappingArray(VirtualDirectoryMappingType[] var1);

   void setVirtualDirectoryMappingArray(int var1, VirtualDirectoryMappingType var2);

   VirtualDirectoryMappingType insertNewVirtualDirectoryMapping(int var1);

   VirtualDirectoryMappingType addNewVirtualDirectoryMapping();

   void removeVirtualDirectoryMapping(int var1);

   UrlMatchMapType[] getUrlMatchMapArray();

   UrlMatchMapType getUrlMatchMapArray(int var1);

   int sizeOfUrlMatchMapArray();

   void setUrlMatchMapArray(UrlMatchMapType[] var1);

   void setUrlMatchMapArray(int var1, UrlMatchMapType var2);

   UrlMatchMapType insertNewUrlMatchMap(int var1);

   UrlMatchMapType addNewUrlMatchMap();

   void removeUrlMatchMap(int var1);

   SecurityPermissionType[] getSecurityPermissionArray();

   SecurityPermissionType getSecurityPermissionArray(int var1);

   int sizeOfSecurityPermissionArray();

   void setSecurityPermissionArray(SecurityPermissionType[] var1);

   void setSecurityPermissionArray(int var1, SecurityPermissionType var2);

   SecurityPermissionType insertNewSecurityPermission(int var1);

   SecurityPermissionType addNewSecurityPermission();

   void removeSecurityPermission(int var1);

   ContextRootType[] getContextRootArray();

   ContextRootType getContextRootArray(int var1);

   int sizeOfContextRootArray();

   void setContextRootArray(ContextRootType[] var1);

   void setContextRootArray(int var1, ContextRootType var2);

   ContextRootType insertNewContextRoot(int var1);

   ContextRootType addNewContextRoot();

   void removeContextRoot(int var1);

   WlDispatchPolicyType[] getWlDispatchPolicyArray();

   WlDispatchPolicyType getWlDispatchPolicyArray(int var1);

   int sizeOfWlDispatchPolicyArray();

   void setWlDispatchPolicyArray(WlDispatchPolicyType[] var1);

   void setWlDispatchPolicyArray(int var1, WlDispatchPolicyType var2);

   WlDispatchPolicyType insertNewWlDispatchPolicy(int var1);

   WlDispatchPolicyType addNewWlDispatchPolicy();

   void removeWlDispatchPolicy(int var1);

   ServletDescriptorType[] getServletDescriptorArray();

   ServletDescriptorType getServletDescriptorArray(int var1);

   int sizeOfServletDescriptorArray();

   void setServletDescriptorArray(ServletDescriptorType[] var1);

   void setServletDescriptorArray(int var1, ServletDescriptorType var2);

   ServletDescriptorType insertNewServletDescriptor(int var1);

   ServletDescriptorType addNewServletDescriptor();

   void removeServletDescriptor(int var1);

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

   XsdStringType[] getComponentFactoryClassNameArray();

   XsdStringType getComponentFactoryClassNameArray(int var1);

   int sizeOfComponentFactoryClassNameArray();

   void setComponentFactoryClassNameArray(XsdStringType[] var1);

   void setComponentFactoryClassNameArray(int var1, XsdStringType var2);

   XsdStringType insertNewComponentFactoryClassName(int var1);

   XsdStringType addNewComponentFactoryClassName();

   void removeComponentFactoryClassName(int var1);

   LoggingType[] getLoggingArray();

   LoggingType getLoggingArray(int var1);

   int sizeOfLoggingArray();

   void setLoggingArray(LoggingType[] var1);

   void setLoggingArray(int var1, LoggingType var2);

   LoggingType insertNewLogging(int var1);

   LoggingType addNewLogging();

   void removeLogging(int var1);

   LibraryRefType[] getLibraryRefArray();

   LibraryRefType getLibraryRefArray(int var1);

   int sizeOfLibraryRefArray();

   void setLibraryRefArray(LibraryRefType[] var1);

   void setLibraryRefArray(int var1, LibraryRefType var2);

   LibraryRefType insertNewLibraryRef(int var1);

   LibraryRefType addNewLibraryRef();

   void removeLibraryRef(int var1);

   JaspicProviderType getJaspicProvider();

   boolean isSetJaspicProvider();

   void setJaspicProvider(JaspicProviderType var1);

   JaspicProviderType addNewJaspicProvider();

   void unsetJaspicProvider();

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

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicWebAppType newInstance() {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType newInstance(XmlOptions options) {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(String xmlAsString) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(File file) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(URL u) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(Reader r) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebAppType.type, options);
      }

      public static WeblogicWebAppType parse(Node node) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebAppType.type, (XmlOptions)null);
      }

      public static WeblogicWebAppType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebAppType.type, options);
      }

      /** @deprecated */
      public static WeblogicWebAppType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebAppType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWebAppType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWebAppType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebAppType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebAppType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebAppType.type, options);
      }

      private Factory() {
      }
   }
}

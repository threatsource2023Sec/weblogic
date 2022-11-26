package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface WebAppType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebAppType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webapptypec495type");

   String[] getModuleNameArray();

   String getModuleNameArray(int var1);

   int sizeOfModuleNameArray();

   void setModuleNameArray(String[] var1);

   void setModuleNameArray(int var1, String var2);

   String insertNewModuleName(int var1);

   String addNewModuleName();

   void removeModuleName(int var1);

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

   EmptyType[] getDistributableArray();

   EmptyType getDistributableArray(int var1);

   int sizeOfDistributableArray();

   void setDistributableArray(EmptyType[] var1);

   void setDistributableArray(int var1, EmptyType var2);

   EmptyType insertNewDistributable(int var1);

   EmptyType addNewDistributable();

   void removeDistributable(int var1);

   ParamValueType[] getContextParamArray();

   ParamValueType getContextParamArray(int var1);

   int sizeOfContextParamArray();

   void setContextParamArray(ParamValueType[] var1);

   void setContextParamArray(int var1, ParamValueType var2);

   ParamValueType insertNewContextParam(int var1);

   ParamValueType addNewContextParam();

   void removeContextParam(int var1);

   FilterType[] getFilterArray();

   FilterType getFilterArray(int var1);

   int sizeOfFilterArray();

   void setFilterArray(FilterType[] var1);

   void setFilterArray(int var1, FilterType var2);

   FilterType insertNewFilter(int var1);

   FilterType addNewFilter();

   void removeFilter(int var1);

   FilterMappingType[] getFilterMappingArray();

   FilterMappingType getFilterMappingArray(int var1);

   int sizeOfFilterMappingArray();

   void setFilterMappingArray(FilterMappingType[] var1);

   void setFilterMappingArray(int var1, FilterMappingType var2);

   FilterMappingType insertNewFilterMapping(int var1);

   FilterMappingType addNewFilterMapping();

   void removeFilterMapping(int var1);

   ListenerType[] getListenerArray();

   ListenerType getListenerArray(int var1);

   int sizeOfListenerArray();

   void setListenerArray(ListenerType[] var1);

   void setListenerArray(int var1, ListenerType var2);

   ListenerType insertNewListener(int var1);

   ListenerType addNewListener();

   void removeListener(int var1);

   ServletType[] getServletArray();

   ServletType getServletArray(int var1);

   int sizeOfServletArray();

   void setServletArray(ServletType[] var1);

   void setServletArray(int var1, ServletType var2);

   ServletType insertNewServlet(int var1);

   ServletType addNewServlet();

   void removeServlet(int var1);

   ServletMappingType[] getServletMappingArray();

   ServletMappingType getServletMappingArray(int var1);

   int sizeOfServletMappingArray();

   void setServletMappingArray(ServletMappingType[] var1);

   void setServletMappingArray(int var1, ServletMappingType var2);

   ServletMappingType insertNewServletMapping(int var1);

   ServletMappingType addNewServletMapping();

   void removeServletMapping(int var1);

   SessionConfigType[] getSessionConfigArray();

   SessionConfigType getSessionConfigArray(int var1);

   int sizeOfSessionConfigArray();

   void setSessionConfigArray(SessionConfigType[] var1);

   void setSessionConfigArray(int var1, SessionConfigType var2);

   SessionConfigType insertNewSessionConfig(int var1);

   SessionConfigType addNewSessionConfig();

   void removeSessionConfig(int var1);

   MimeMappingType[] getMimeMappingArray();

   MimeMappingType getMimeMappingArray(int var1);

   int sizeOfMimeMappingArray();

   void setMimeMappingArray(MimeMappingType[] var1);

   void setMimeMappingArray(int var1, MimeMappingType var2);

   MimeMappingType insertNewMimeMapping(int var1);

   MimeMappingType addNewMimeMapping();

   void removeMimeMapping(int var1);

   WelcomeFileListType[] getWelcomeFileListArray();

   WelcomeFileListType getWelcomeFileListArray(int var1);

   int sizeOfWelcomeFileListArray();

   void setWelcomeFileListArray(WelcomeFileListType[] var1);

   void setWelcomeFileListArray(int var1, WelcomeFileListType var2);

   WelcomeFileListType insertNewWelcomeFileList(int var1);

   WelcomeFileListType addNewWelcomeFileList();

   void removeWelcomeFileList(int var1);

   ErrorPageType[] getErrorPageArray();

   ErrorPageType getErrorPageArray(int var1);

   int sizeOfErrorPageArray();

   void setErrorPageArray(ErrorPageType[] var1);

   void setErrorPageArray(int var1, ErrorPageType var2);

   ErrorPageType insertNewErrorPage(int var1);

   ErrorPageType addNewErrorPage();

   void removeErrorPage(int var1);

   JspConfigType[] getJspConfigArray();

   JspConfigType getJspConfigArray(int var1);

   int sizeOfJspConfigArray();

   void setJspConfigArray(JspConfigType[] var1);

   void setJspConfigArray(int var1, JspConfigType var2);

   JspConfigType insertNewJspConfig(int var1);

   JspConfigType addNewJspConfig();

   void removeJspConfig(int var1);

   SecurityConstraintType[] getSecurityConstraintArray();

   SecurityConstraintType getSecurityConstraintArray(int var1);

   int sizeOfSecurityConstraintArray();

   void setSecurityConstraintArray(SecurityConstraintType[] var1);

   void setSecurityConstraintArray(int var1, SecurityConstraintType var2);

   SecurityConstraintType insertNewSecurityConstraint(int var1);

   SecurityConstraintType addNewSecurityConstraint();

   void removeSecurityConstraint(int var1);

   LoginConfigType[] getLoginConfigArray();

   LoginConfigType getLoginConfigArray(int var1);

   int sizeOfLoginConfigArray();

   void setLoginConfigArray(LoginConfigType[] var1);

   void setLoginConfigArray(int var1, LoginConfigType var2);

   LoginConfigType insertNewLoginConfig(int var1);

   LoginConfigType addNewLoginConfig();

   void removeLoginConfig(int var1);

   SecurityRoleType[] getSecurityRoleArray();

   SecurityRoleType getSecurityRoleArray(int var1);

   int sizeOfSecurityRoleArray();

   void setSecurityRoleArray(SecurityRoleType[] var1);

   void setSecurityRoleArray(int var1, SecurityRoleType var2);

   SecurityRoleType insertNewSecurityRole(int var1);

   SecurityRoleType addNewSecurityRole();

   void removeSecurityRole(int var1);

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

   LifecycleCallbackType[] getPostConstructArray();

   LifecycleCallbackType getPostConstructArray(int var1);

   int sizeOfPostConstructArray();

   void setPostConstructArray(LifecycleCallbackType[] var1);

   void setPostConstructArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPostConstruct(int var1);

   LifecycleCallbackType addNewPostConstruct();

   void removePostConstruct(int var1);

   LifecycleCallbackType[] getPreDestroyArray();

   LifecycleCallbackType getPreDestroyArray(int var1);

   int sizeOfPreDestroyArray();

   void setPreDestroyArray(LifecycleCallbackType[] var1);

   void setPreDestroyArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPreDestroy(int var1);

   LifecycleCallbackType addNewPreDestroy();

   void removePreDestroy(int var1);

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

   MessageDestinationType[] getMessageDestinationArray();

   MessageDestinationType getMessageDestinationArray(int var1);

   int sizeOfMessageDestinationArray();

   void setMessageDestinationArray(MessageDestinationType[] var1);

   void setMessageDestinationArray(int var1, MessageDestinationType var2);

   MessageDestinationType insertNewMessageDestination(int var1);

   MessageDestinationType addNewMessageDestination();

   void removeMessageDestination(int var1);

   LocaleEncodingMappingListType[] getLocaleEncodingMappingListArray();

   LocaleEncodingMappingListType getLocaleEncodingMappingListArray(int var1);

   int sizeOfLocaleEncodingMappingListArray();

   void setLocaleEncodingMappingListArray(LocaleEncodingMappingListType[] var1);

   void setLocaleEncodingMappingListArray(int var1, LocaleEncodingMappingListType var2);

   LocaleEncodingMappingListType insertNewLocaleEncodingMappingList(int var1);

   LocaleEncodingMappingListType addNewLocaleEncodingMappingList();

   void removeLocaleEncodingMappingList(int var1);

   String[] getDefaultContextPathArray();

   String getDefaultContextPathArray(int var1);

   int sizeOfDefaultContextPathArray();

   void setDefaultContextPathArray(String[] var1);

   void setDefaultContextPathArray(int var1, String var2);

   String insertNewDefaultContextPath(int var1);

   String addNewDefaultContextPath();

   void removeDefaultContextPath(int var1);

   String[] getRequestCharacterEncodingArray();

   String getRequestCharacterEncodingArray(int var1);

   int sizeOfRequestCharacterEncodingArray();

   void setRequestCharacterEncodingArray(String[] var1);

   void setRequestCharacterEncodingArray(int var1, String var2);

   String insertNewRequestCharacterEncoding(int var1);

   String addNewRequestCharacterEncoding();

   void removeRequestCharacterEncoding(int var1);

   String[] getResponseCharacterEncodingArray();

   String getResponseCharacterEncodingArray(int var1);

   int sizeOfResponseCharacterEncodingArray();

   void setResponseCharacterEncodingArray(String[] var1);

   void setResponseCharacterEncodingArray(int var1, String var2);

   String insertNewResponseCharacterEncoding(int var1);

   String addNewResponseCharacterEncoding();

   void removeResponseCharacterEncoding(int var1);

   EmptyType[] getDenyUncoveredHttpMethodsArray();

   EmptyType getDenyUncoveredHttpMethodsArray(int var1);

   int sizeOfDenyUncoveredHttpMethodsArray();

   void setDenyUncoveredHttpMethodsArray(EmptyType[] var1);

   void setDenyUncoveredHttpMethodsArray(int var1, EmptyType var2);

   EmptyType insertNewDenyUncoveredHttpMethods(int var1);

   EmptyType addNewDenyUncoveredHttpMethods();

   void removeDenyUncoveredHttpMethods(int var1);

   AbsoluteOrderingType[] getAbsoluteOrderingArray();

   AbsoluteOrderingType getAbsoluteOrderingArray(int var1);

   int sizeOfAbsoluteOrderingArray();

   void setAbsoluteOrderingArray(AbsoluteOrderingType[] var1);

   void setAbsoluteOrderingArray(int var1, AbsoluteOrderingType var2);

   AbsoluteOrderingType insertNewAbsoluteOrdering(int var1);

   AbsoluteOrderingType addNewAbsoluteOrdering();

   void removeAbsoluteOrdering(int var1);

   WebAppVersionType.Enum getVersion();

   WebAppVersionType xgetVersion();

   void setVersion(WebAppVersionType.Enum var1);

   void xsetVersion(WebAppVersionType var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   boolean getMetadataComplete();

   XmlBoolean xgetMetadataComplete();

   boolean isSetMetadataComplete();

   void setMetadataComplete(boolean var1);

   void xsetMetadataComplete(XmlBoolean var1);

   void unsetMetadataComplete();

   public static final class Factory {
      public static WebAppType newInstance() {
         return (WebAppType)XmlBeans.getContextTypeLoader().newInstance(WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType newInstance(XmlOptions options) {
         return (WebAppType)XmlBeans.getContextTypeLoader().newInstance(WebAppType.type, options);
      }

      public static WebAppType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppType.type, options);
      }

      public static WebAppType parse(File file) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(file, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(file, WebAppType.type, options);
      }

      public static WebAppType parse(URL u) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(u, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(u, WebAppType.type, options);
      }

      public static WebAppType parse(InputStream is) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(is, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(is, WebAppType.type, options);
      }

      public static WebAppType parse(Reader r) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(r, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(r, WebAppType.type, options);
      }

      public static WebAppType parse(XMLStreamReader sr) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(sr, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(sr, WebAppType.type, options);
      }

      public static WebAppType parse(Node node) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(node, WebAppType.type, (XmlOptions)null);
      }

      public static WebAppType parse(Node node, XmlOptions options) throws XmlException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(node, WebAppType.type, options);
      }

      /** @deprecated */
      public static WebAppType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(xis, WebAppType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebAppType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebAppType)XmlBeans.getContextTypeLoader().parse(xis, WebAppType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppType.type, options);
      }

      private Factory() {
      }
   }
}

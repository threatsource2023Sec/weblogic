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

public interface SessionBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SessionBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("sessionbeantype03a4type");

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

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   HomeType getHome();

   boolean isSetHome();

   void setHome(HomeType var1);

   HomeType addNewHome();

   void unsetHome();

   RemoteType getRemote();

   boolean isSetRemote();

   void setRemote(RemoteType var1);

   RemoteType addNewRemote();

   void unsetRemote();

   LocalHomeType getLocalHome();

   boolean isSetLocalHome();

   void setLocalHome(LocalHomeType var1);

   LocalHomeType addNewLocalHome();

   void unsetLocalHome();

   LocalType getLocal();

   boolean isSetLocal();

   void setLocal(LocalType var1);

   LocalType addNewLocal();

   void unsetLocal();

   FullyQualifiedClassType[] getBusinessLocalArray();

   FullyQualifiedClassType getBusinessLocalArray(int var1);

   int sizeOfBusinessLocalArray();

   void setBusinessLocalArray(FullyQualifiedClassType[] var1);

   void setBusinessLocalArray(int var1, FullyQualifiedClassType var2);

   FullyQualifiedClassType insertNewBusinessLocal(int var1);

   FullyQualifiedClassType addNewBusinessLocal();

   void removeBusinessLocal(int var1);

   FullyQualifiedClassType[] getBusinessRemoteArray();

   FullyQualifiedClassType getBusinessRemoteArray(int var1);

   int sizeOfBusinessRemoteArray();

   void setBusinessRemoteArray(FullyQualifiedClassType[] var1);

   void setBusinessRemoteArray(int var1, FullyQualifiedClassType var2);

   FullyQualifiedClassType insertNewBusinessRemote(int var1);

   FullyQualifiedClassType addNewBusinessRemote();

   void removeBusinessRemote(int var1);

   EmptyType getLocalBean();

   boolean isSetLocalBean();

   void setLocalBean(EmptyType var1);

   EmptyType addNewLocalBean();

   void unsetLocalBean();

   FullyQualifiedClassType getServiceEndpoint();

   boolean isSetServiceEndpoint();

   void setServiceEndpoint(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceEndpoint();

   void unsetServiceEndpoint();

   EjbClassType getEjbClass();

   boolean isSetEjbClass();

   void setEjbClass(EjbClassType var1);

   EjbClassType addNewEjbClass();

   void unsetEjbClass();

   SessionTypeType getSessionType();

   boolean isSetSessionType();

   void setSessionType(SessionTypeType var1);

   SessionTypeType addNewSessionType();

   void unsetSessionType();

   StatefulTimeoutType getStatefulTimeout();

   boolean isSetStatefulTimeout();

   void setStatefulTimeout(StatefulTimeoutType var1);

   StatefulTimeoutType addNewStatefulTimeout();

   void unsetStatefulTimeout();

   NamedMethodType getTimeoutMethod();

   boolean isSetTimeoutMethod();

   void setTimeoutMethod(NamedMethodType var1);

   NamedMethodType addNewTimeoutMethod();

   void unsetTimeoutMethod();

   TimerType[] getTimerArray();

   TimerType getTimerArray(int var1);

   int sizeOfTimerArray();

   void setTimerArray(TimerType[] var1);

   void setTimerArray(int var1, TimerType var2);

   TimerType insertNewTimer(int var1);

   TimerType addNewTimer();

   void removeTimer(int var1);

   TrueFalseType getInitOnStartup();

   boolean isSetInitOnStartup();

   void setInitOnStartup(TrueFalseType var1);

   TrueFalseType addNewInitOnStartup();

   void unsetInitOnStartup();

   ConcurrencyManagementTypeType getConcurrencyManagementType();

   boolean isSetConcurrencyManagementType();

   void setConcurrencyManagementType(ConcurrencyManagementTypeType var1);

   ConcurrencyManagementTypeType addNewConcurrencyManagementType();

   void unsetConcurrencyManagementType();

   ConcurrentMethodType[] getConcurrentMethodArray();

   ConcurrentMethodType getConcurrentMethodArray(int var1);

   int sizeOfConcurrentMethodArray();

   void setConcurrentMethodArray(ConcurrentMethodType[] var1);

   void setConcurrentMethodArray(int var1, ConcurrentMethodType var2);

   ConcurrentMethodType insertNewConcurrentMethod(int var1);

   ConcurrentMethodType addNewConcurrentMethod();

   void removeConcurrentMethod(int var1);

   DependsOnType getDependsOn();

   boolean isSetDependsOn();

   void setDependsOn(DependsOnType var1);

   DependsOnType addNewDependsOn();

   void unsetDependsOn();

   InitMethodType[] getInitMethodArray();

   InitMethodType getInitMethodArray(int var1);

   int sizeOfInitMethodArray();

   void setInitMethodArray(InitMethodType[] var1);

   void setInitMethodArray(int var1, InitMethodType var2);

   InitMethodType insertNewInitMethod(int var1);

   InitMethodType addNewInitMethod();

   void removeInitMethod(int var1);

   RemoveMethodType[] getRemoveMethodArray();

   RemoveMethodType getRemoveMethodArray(int var1);

   int sizeOfRemoveMethodArray();

   void setRemoveMethodArray(RemoveMethodType[] var1);

   void setRemoveMethodArray(int var1, RemoveMethodType var2);

   RemoveMethodType insertNewRemoveMethod(int var1);

   RemoveMethodType addNewRemoveMethod();

   void removeRemoveMethod(int var1);

   AsyncMethodType[] getAsyncMethodArray();

   AsyncMethodType getAsyncMethodArray(int var1);

   int sizeOfAsyncMethodArray();

   void setAsyncMethodArray(AsyncMethodType[] var1);

   void setAsyncMethodArray(int var1, AsyncMethodType var2);

   AsyncMethodType insertNewAsyncMethod(int var1);

   AsyncMethodType addNewAsyncMethod();

   void removeAsyncMethod(int var1);

   TransactionTypeType getTransactionType();

   boolean isSetTransactionType();

   void setTransactionType(TransactionTypeType var1);

   TransactionTypeType addNewTransactionType();

   void unsetTransactionType();

   NamedMethodType getAfterBeginMethod();

   boolean isSetAfterBeginMethod();

   void setAfterBeginMethod(NamedMethodType var1);

   NamedMethodType addNewAfterBeginMethod();

   void unsetAfterBeginMethod();

   NamedMethodType getBeforeCompletionMethod();

   boolean isSetBeforeCompletionMethod();

   void setBeforeCompletionMethod(NamedMethodType var1);

   NamedMethodType addNewBeforeCompletionMethod();

   void unsetBeforeCompletionMethod();

   NamedMethodType getAfterCompletionMethod();

   boolean isSetAfterCompletionMethod();

   void setAfterCompletionMethod(NamedMethodType var1);

   NamedMethodType addNewAfterCompletionMethod();

   void unsetAfterCompletionMethod();

   AroundInvokeType[] getAroundInvokeArray();

   AroundInvokeType getAroundInvokeArray(int var1);

   int sizeOfAroundInvokeArray();

   void setAroundInvokeArray(AroundInvokeType[] var1);

   void setAroundInvokeArray(int var1, AroundInvokeType var2);

   AroundInvokeType insertNewAroundInvoke(int var1);

   AroundInvokeType addNewAroundInvoke();

   void removeAroundInvoke(int var1);

   AroundTimeoutType[] getAroundTimeoutArray();

   AroundTimeoutType getAroundTimeoutArray(int var1);

   int sizeOfAroundTimeoutArray();

   void setAroundTimeoutArray(AroundTimeoutType[] var1);

   void setAroundTimeoutArray(int var1, AroundTimeoutType var2);

   AroundTimeoutType insertNewAroundTimeout(int var1);

   AroundTimeoutType addNewAroundTimeout();

   void removeAroundTimeout(int var1);

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

   LifecycleCallbackType[] getPostActivateArray();

   LifecycleCallbackType getPostActivateArray(int var1);

   int sizeOfPostActivateArray();

   void setPostActivateArray(LifecycleCallbackType[] var1);

   void setPostActivateArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPostActivate(int var1);

   LifecycleCallbackType addNewPostActivate();

   void removePostActivate(int var1);

   LifecycleCallbackType[] getPrePassivateArray();

   LifecycleCallbackType getPrePassivateArray(int var1);

   int sizeOfPrePassivateArray();

   void setPrePassivateArray(LifecycleCallbackType[] var1);

   void setPrePassivateArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPrePassivate(int var1);

   LifecycleCallbackType addNewPrePassivate();

   void removePrePassivate(int var1);

   SecurityRoleRefType[] getSecurityRoleRefArray();

   SecurityRoleRefType getSecurityRoleRefArray(int var1);

   int sizeOfSecurityRoleRefArray();

   void setSecurityRoleRefArray(SecurityRoleRefType[] var1);

   void setSecurityRoleRefArray(int var1, SecurityRoleRefType var2);

   SecurityRoleRefType insertNewSecurityRoleRef(int var1);

   SecurityRoleRefType addNewSecurityRoleRef();

   void removeSecurityRoleRef(int var1);

   SecurityIdentityType getSecurityIdentity();

   boolean isSetSecurityIdentity();

   void setSecurityIdentity(SecurityIdentityType var1);

   SecurityIdentityType addNewSecurityIdentity();

   void unsetSecurityIdentity();

   boolean getPassivationCapable();

   XmlBoolean xgetPassivationCapable();

   boolean isSetPassivationCapable();

   void setPassivationCapable(boolean var1);

   void xsetPassivationCapable(XmlBoolean var1);

   void unsetPassivationCapable();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SessionBeanType newInstance() {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().newInstance(SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType newInstance(XmlOptions options) {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().newInstance(SessionBeanType.type, options);
      }

      public static SessionBeanType parse(java.lang.String xmlAsString) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(File file) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(file, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(file, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(URL u) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(u, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(u, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(InputStream is) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(is, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(is, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(Reader r) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(r, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(r, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(XMLStreamReader sr) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(sr, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(sr, SessionBeanType.type, options);
      }

      public static SessionBeanType parse(Node node) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(node, SessionBeanType.type, (XmlOptions)null);
      }

      public static SessionBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(node, SessionBeanType.type, options);
      }

      /** @deprecated */
      public static SessionBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(xis, SessionBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SessionBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SessionBeanType)XmlBeans.getContextTypeLoader().parse(xis, SessionBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionBeanType.type, options);
      }

      private Factory() {
      }
   }
}

package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.DescriptionType;
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

public interface WeblogicEjbJarType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicEjbJarType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicejbjartypee09etype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   WeblogicEnterpriseBeanType[] getWeblogicEnterpriseBeanArray();

   WeblogicEnterpriseBeanType getWeblogicEnterpriseBeanArray(int var1);

   int sizeOfWeblogicEnterpriseBeanArray();

   void setWeblogicEnterpriseBeanArray(WeblogicEnterpriseBeanType[] var1);

   void setWeblogicEnterpriseBeanArray(int var1, WeblogicEnterpriseBeanType var2);

   WeblogicEnterpriseBeanType insertNewWeblogicEnterpriseBean(int var1);

   WeblogicEnterpriseBeanType addNewWeblogicEnterpriseBean();

   void removeWeblogicEnterpriseBean(int var1);

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

   SecurityPermissionType getSecurityPermission();

   boolean isSetSecurityPermission();

   void setSecurityPermission(SecurityPermissionType var1);

   SecurityPermissionType addNewSecurityPermission();

   void unsetSecurityPermission();

   TransactionIsolationType[] getTransactionIsolationArray();

   TransactionIsolationType getTransactionIsolationArray(int var1);

   int sizeOfTransactionIsolationArray();

   void setTransactionIsolationArray(TransactionIsolationType[] var1);

   void setTransactionIsolationArray(int var1, TransactionIsolationType var2);

   TransactionIsolationType insertNewTransactionIsolation(int var1);

   TransactionIsolationType addNewTransactionIsolation();

   void removeTransactionIsolation(int var1);

   MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray();

   MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int var1);

   int sizeOfMessageDestinationDescriptorArray();

   void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] var1);

   void setMessageDestinationDescriptorArray(int var1, MessageDestinationDescriptorType var2);

   MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int var1);

   MessageDestinationDescriptorType addNewMessageDestinationDescriptor();

   void removeMessageDestinationDescriptor(int var1);

   IdempotentMethodsType getIdempotentMethods();

   boolean isSetIdempotentMethods();

   void setIdempotentMethods(IdempotentMethodsType var1);

   IdempotentMethodsType addNewIdempotentMethods();

   void unsetIdempotentMethods();

   SkipStateReplicationMethodsType getSkipStateReplicationMethods();

   boolean isSetSkipStateReplicationMethods();

   void setSkipStateReplicationMethods(SkipStateReplicationMethodsType var1);

   SkipStateReplicationMethodsType addNewSkipStateReplicationMethods();

   void unsetSkipStateReplicationMethods();

   RetryMethodsOnRollbackType[] getRetryMethodsOnRollbackArray();

   RetryMethodsOnRollbackType getRetryMethodsOnRollbackArray(int var1);

   int sizeOfRetryMethodsOnRollbackArray();

   void setRetryMethodsOnRollbackArray(RetryMethodsOnRollbackType[] var1);

   void setRetryMethodsOnRollbackArray(int var1, RetryMethodsOnRollbackType var2);

   RetryMethodsOnRollbackType insertNewRetryMethodsOnRollback(int var1);

   RetryMethodsOnRollbackType addNewRetryMethodsOnRollback();

   void removeRetryMethodsOnRollback(int var1);

   TrueFalseType getEnableBeanClassRedeploy();

   boolean isSetEnableBeanClassRedeploy();

   void setEnableBeanClassRedeploy(TrueFalseType var1);

   TrueFalseType addNewEnableBeanClassRedeploy();

   void unsetEnableBeanClassRedeploy();

   TimerImplementationType getTimerImplementation();

   boolean isSetTimerImplementation();

   void setTimerImplementation(TimerImplementationType var1);

   TimerImplementationType addNewTimerImplementation();

   void unsetTimerImplementation();

   DisableWarningType[] getDisableWarningArray();

   DisableWarningType getDisableWarningArray(int var1);

   int sizeOfDisableWarningArray();

   void setDisableWarningArray(DisableWarningType[] var1);

   void setDisableWarningArray(int var1, DisableWarningType var2);

   DisableWarningType insertNewDisableWarning(int var1);

   DisableWarningType addNewDisableWarning();

   void removeDisableWarning(int var1);

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

   WeblogicCompatibilityType getWeblogicCompatibility();

   boolean isSetWeblogicCompatibility();

   void setWeblogicCompatibility(WeblogicCompatibilityType var1);

   WeblogicCompatibilityType addNewWeblogicCompatibility();

   void unsetWeblogicCompatibility();

   CoherenceClusterRefType getCoherenceClusterRef();

   boolean isSetCoherenceClusterRef();

   void setCoherenceClusterRef(CoherenceClusterRefType var1);

   CoherenceClusterRefType addNewCoherenceClusterRef();

   void unsetCoherenceClusterRef();

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
      public static WeblogicEjbJarType newInstance() {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().newInstance(WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType newInstance(XmlOptions options) {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().newInstance(WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(String xmlAsString) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(File file) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(file, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(file, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(URL u) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(u, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(u, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(is, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(is, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(Reader r) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(r, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(r, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEjbJarType.type, options);
      }

      public static WeblogicEjbJarType parse(Node node) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(node, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(node, WeblogicEjbJarType.type, options);
      }

      /** @deprecated */
      public static WeblogicEjbJarType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicEjbJarType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicEjbJarType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEjbJarType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEjbJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEjbJarType.type, options);
      }

      private Factory() {
      }
   }
}

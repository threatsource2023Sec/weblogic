package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationAdminModeTriggerType;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationParamType;
import com.oracle.xmlns.weblogic.weblogicApplication.CapacityType;
import com.oracle.xmlns.weblogic.weblogicApplication.CdiDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplication.ClassLoadingType;
import com.oracle.xmlns.weblogic.weblogicApplication.ClassloaderStructureType;
import com.oracle.xmlns.weblogic.weblogicApplication.CoherenceClusterRefType;
import com.oracle.xmlns.weblogic.weblogicApplication.ContextRequestClassType;
import com.oracle.xmlns.weblogic.weblogicApplication.EjbReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplication.EjbType;
import com.oracle.xmlns.weblogic.weblogicApplication.FairShareRequestClassType;
import com.oracle.xmlns.weblogic.weblogicApplication.FastSwapType;
import com.oracle.xmlns.weblogic.weblogicApplication.JdbcConnectionPoolType;
import com.oracle.xmlns.weblogic.weblogicApplication.LibraryContextRootOverrideType;
import com.oracle.xmlns.weblogic.weblogicApplication.LibraryRefType;
import com.oracle.xmlns.weblogic.weblogicApplication.ListenerType;
import com.oracle.xmlns.weblogic.weblogicApplication.ManagedExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicApplication.ManagedScheduledExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicApplication.ManagedThreadFactoryType;
import com.oracle.xmlns.weblogic.weblogicApplication.MaxThreadsConstraintType;
import com.oracle.xmlns.weblogic.weblogicApplication.MessageDestinationDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplication.MinThreadsConstraintType;
import com.oracle.xmlns.weblogic.weblogicApplication.OsgiFrameworkReferenceType;
import com.oracle.xmlns.weblogic.weblogicApplication.PreferApplicationPackagesType;
import com.oracle.xmlns.weblogic.weblogicApplication.PreferApplicationResourcesType;
import com.oracle.xmlns.weblogic.weblogicApplication.ResourceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplication.ResourceEnvDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplication.ResponseTimeRequestClassType;
import com.oracle.xmlns.weblogic.weblogicApplication.SecurityType;
import com.oracle.xmlns.weblogic.weblogicApplication.ServiceReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplication.SessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplication.ShutdownType;
import com.oracle.xmlns.weblogic.weblogicApplication.SingletonServiceType;
import com.oracle.xmlns.weblogic.weblogicApplication.StartupType;
import com.oracle.xmlns.weblogic.weblogicApplication.WeblogicApplicationType;
import com.oracle.xmlns.weblogic.weblogicApplication.WeblogicModuleType;
import com.oracle.xmlns.weblogic.weblogicApplication.WorkManagerType;
import com.oracle.xmlns.weblogic.weblogicApplication.XmlType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicApplicationTypeImpl extends XmlComplexContentImpl implements WeblogicApplicationType {
   private static final long serialVersionUID = 1L;
   private static final QName EJB$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "ejb");
   private static final QName XML$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xml");
   private static final QName JDBCCONNECTIONPOOL$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "jdbc-connection-pool");
   private static final QName SECURITY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "security");
   private static final QName APPLICATIONPARAM$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "application-param");
   private static final QName CLASSLOADERSTRUCTURE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "classloader-structure");
   private static final QName LISTENER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "listener");
   private static final QName SINGLETONSERVICE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "singleton-service");
   private static final QName STARTUP$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "startup");
   private static final QName SHUTDOWN$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shutdown");
   private static final QName MODULE$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "module");
   private static final QName LIBRARYREF$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "library-ref");
   private static final QName FAIRSHAREREQUEST$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "fair-share-request");
   private static final QName RESPONSETIMEREQUEST$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "response-time-request");
   private static final QName CONTEXTREQUEST$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "context-request");
   private static final QName MAXTHREADSCONSTRAINT$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-threads-constraint");
   private static final QName MINTHREADSCONSTRAINT$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "min-threads-constraint");
   private static final QName CAPACITY$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "capacity");
   private static final QName WORKMANAGER$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "work-manager");
   private static final QName MANAGEDEXECUTORSERVICE$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "managed-executor-service");
   private static final QName MANAGEDSCHEDULEDEXECUTORSERVICE$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "managed-scheduled-executor-service");
   private static final QName MANAGEDTHREADFACTORY$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "managed-thread-factory");
   private static final QName COMPONENTFACTORYCLASSNAME$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "component-factory-class-name");
   private static final QName APPLICATIONADMINMODETRIGGER$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "application-admin-mode-trigger");
   private static final QName SESSIONDESCRIPTOR$48 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "session-descriptor");
   private static final QName LIBRARYCONTEXTROOTOVERRIDE$50 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "library-context-root-override");
   private static final QName PREFERAPPLICATIONPACKAGES$52 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "prefer-application-packages");
   private static final QName PREFERAPPLICATIONRESOURCES$54 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "prefer-application-resources");
   private static final QName FASTSWAP$56 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "fast-swap");
   private static final QName COHERENCECLUSTERREF$58 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "coherence-cluster-ref");
   private static final QName OSGIFRAMEWORKREFERENCE$60 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "osgi-framework-reference");
   private static final QName RESOURCEDESCRIPTION$62 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "resource-description");
   private static final QName RESOURCEENVDESCRIPTION$64 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "resource-env-description");
   private static final QName EJBREFERENCEDESCRIPTION$66 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "ejb-reference-description");
   private static final QName SERVICEREFERENCEDESCRIPTION$68 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "service-reference-description");
   private static final QName MESSAGEDESTINATIONDESCRIPTOR$70 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "message-destination-descriptor");
   private static final QName CLASSLOADING$72 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "class-loading");
   private static final QName READYREGISTRATION$74 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "ready-registration");
   private static final QName CDIDESCRIPTOR$76 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "cdi-descriptor");
   private static final QName VERSION$78 = new QName("", "version");

   public WeblogicApplicationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbType getEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbType target = null;
         target = (EjbType)this.get_store().find_element_user(EJB$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJB$0) != 0;
      }
   }

   public void setEjb(EjbType ejb) {
      this.generatedSetterHelperImpl(ejb, EJB$0, 0, (short)1);
   }

   public EjbType addNewEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbType target = null;
         target = (EjbType)this.get_store().add_element_user(EJB$0);
         return target;
      }
   }

   public void unsetEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJB$0, 0);
      }
   }

   public XmlType getXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlType target = null;
         target = (XmlType)this.get_store().find_element_user(XML$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XML$2) != 0;
      }
   }

   public void setXml(XmlType xml) {
      this.generatedSetterHelperImpl(xml, XML$2, 0, (short)1);
   }

   public XmlType addNewXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlType target = null;
         target = (XmlType)this.get_store().add_element_user(XML$2);
         return target;
      }
   }

   public void unsetXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XML$2, 0);
      }
   }

   public JdbcConnectionPoolType[] getJdbcConnectionPoolArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JDBCCONNECTIONPOOL$4, targetList);
         JdbcConnectionPoolType[] result = new JdbcConnectionPoolType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JdbcConnectionPoolType getJdbcConnectionPoolArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcConnectionPoolType target = null;
         target = (JdbcConnectionPoolType)this.get_store().find_element_user(JDBCCONNECTIONPOOL$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJdbcConnectionPoolArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBCCONNECTIONPOOL$4);
      }
   }

   public void setJdbcConnectionPoolArray(JdbcConnectionPoolType[] jdbcConnectionPoolArray) {
      this.check_orphaned();
      this.arraySetterHelper(jdbcConnectionPoolArray, JDBCCONNECTIONPOOL$4);
   }

   public void setJdbcConnectionPoolArray(int i, JdbcConnectionPoolType jdbcConnectionPool) {
      this.generatedSetterHelperImpl(jdbcConnectionPool, JDBCCONNECTIONPOOL$4, i, (short)2);
   }

   public JdbcConnectionPoolType insertNewJdbcConnectionPool(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcConnectionPoolType target = null;
         target = (JdbcConnectionPoolType)this.get_store().insert_element_user(JDBCCONNECTIONPOOL$4, i);
         return target;
      }
   }

   public JdbcConnectionPoolType addNewJdbcConnectionPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcConnectionPoolType target = null;
         target = (JdbcConnectionPoolType)this.get_store().add_element_user(JDBCCONNECTIONPOOL$4);
         return target;
      }
   }

   public void removeJdbcConnectionPool(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBCCONNECTIONPOOL$4, i);
      }
   }

   public SecurityType getSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityType target = null;
         target = (SecurityType)this.get_store().find_element_user(SECURITY$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITY$6) != 0;
      }
   }

   public void setSecurity(SecurityType security) {
      this.generatedSetterHelperImpl(security, SECURITY$6, 0, (short)1);
   }

   public SecurityType addNewSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityType target = null;
         target = (SecurityType)this.get_store().add_element_user(SECURITY$6);
         return target;
      }
   }

   public void unsetSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITY$6, 0);
      }
   }

   public ApplicationParamType[] getApplicationParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(APPLICATIONPARAM$8, targetList);
         ApplicationParamType[] result = new ApplicationParamType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ApplicationParamType getApplicationParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationParamType target = null;
         target = (ApplicationParamType)this.get_store().find_element_user(APPLICATIONPARAM$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfApplicationParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(APPLICATIONPARAM$8);
      }
   }

   public void setApplicationParamArray(ApplicationParamType[] applicationParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(applicationParamArray, APPLICATIONPARAM$8);
   }

   public void setApplicationParamArray(int i, ApplicationParamType applicationParam) {
      this.generatedSetterHelperImpl(applicationParam, APPLICATIONPARAM$8, i, (short)2);
   }

   public ApplicationParamType insertNewApplicationParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationParamType target = null;
         target = (ApplicationParamType)this.get_store().insert_element_user(APPLICATIONPARAM$8, i);
         return target;
      }
   }

   public ApplicationParamType addNewApplicationParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationParamType target = null;
         target = (ApplicationParamType)this.get_store().add_element_user(APPLICATIONPARAM$8);
         return target;
      }
   }

   public void removeApplicationParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPLICATIONPARAM$8, i);
      }
   }

   public ClassloaderStructureType getClassloaderStructure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassloaderStructureType target = null;
         target = (ClassloaderStructureType)this.get_store().find_element_user(CLASSLOADERSTRUCTURE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClassloaderStructure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSLOADERSTRUCTURE$10) != 0;
      }
   }

   public void setClassloaderStructure(ClassloaderStructureType classloaderStructure) {
      this.generatedSetterHelperImpl(classloaderStructure, CLASSLOADERSTRUCTURE$10, 0, (short)1);
   }

   public ClassloaderStructureType addNewClassloaderStructure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassloaderStructureType target = null;
         target = (ClassloaderStructureType)this.get_store().add_element_user(CLASSLOADERSTRUCTURE$10);
         return target;
      }
   }

   public void unsetClassloaderStructure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSLOADERSTRUCTURE$10, 0);
      }
   }

   public ListenerType[] getListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LISTENER$12, targetList);
         ListenerType[] result = new ListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ListenerType getListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().find_element_user(LISTENER$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENER$12);
      }
   }

   public void setListenerArray(ListenerType[] listenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(listenerArray, LISTENER$12);
   }

   public void setListenerArray(int i, ListenerType listener) {
      this.generatedSetterHelperImpl(listener, LISTENER$12, i, (short)2);
   }

   public ListenerType insertNewListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().insert_element_user(LISTENER$12, i);
         return target;
      }
   }

   public ListenerType addNewListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().add_element_user(LISTENER$12);
         return target;
      }
   }

   public void removeListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENER$12, i);
      }
   }

   public SingletonServiceType[] getSingletonServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SINGLETONSERVICE$14, targetList);
         SingletonServiceType[] result = new SingletonServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SingletonServiceType getSingletonServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonServiceType target = null;
         target = (SingletonServiceType)this.get_store().find_element_user(SINGLETONSERVICE$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSingletonServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONSERVICE$14);
      }
   }

   public void setSingletonServiceArray(SingletonServiceType[] singletonServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(singletonServiceArray, SINGLETONSERVICE$14);
   }

   public void setSingletonServiceArray(int i, SingletonServiceType singletonService) {
      this.generatedSetterHelperImpl(singletonService, SINGLETONSERVICE$14, i, (short)2);
   }

   public SingletonServiceType insertNewSingletonService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonServiceType target = null;
         target = (SingletonServiceType)this.get_store().insert_element_user(SINGLETONSERVICE$14, i);
         return target;
      }
   }

   public SingletonServiceType addNewSingletonService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonServiceType target = null;
         target = (SingletonServiceType)this.get_store().add_element_user(SINGLETONSERVICE$14);
         return target;
      }
   }

   public void removeSingletonService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONSERVICE$14, i);
      }
   }

   public StartupType[] getStartupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STARTUP$16, targetList);
         StartupType[] result = new StartupType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public StartupType getStartupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StartupType target = null;
         target = (StartupType)this.get_store().find_element_user(STARTUP$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStartupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STARTUP$16);
      }
   }

   public void setStartupArray(StartupType[] startupArray) {
      this.check_orphaned();
      this.arraySetterHelper(startupArray, STARTUP$16);
   }

   public void setStartupArray(int i, StartupType startup) {
      this.generatedSetterHelperImpl(startup, STARTUP$16, i, (short)2);
   }

   public StartupType insertNewStartup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StartupType target = null;
         target = (StartupType)this.get_store().insert_element_user(STARTUP$16, i);
         return target;
      }
   }

   public StartupType addNewStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StartupType target = null;
         target = (StartupType)this.get_store().add_element_user(STARTUP$16);
         return target;
      }
   }

   public void removeStartup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STARTUP$16, i);
      }
   }

   public ShutdownType[] getShutdownArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SHUTDOWN$18, targetList);
         ShutdownType[] result = new ShutdownType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ShutdownType getShutdownArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShutdownType target = null;
         target = (ShutdownType)this.get_store().find_element_user(SHUTDOWN$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfShutdownArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHUTDOWN$18);
      }
   }

   public void setShutdownArray(ShutdownType[] shutdownArray) {
      this.check_orphaned();
      this.arraySetterHelper(shutdownArray, SHUTDOWN$18);
   }

   public void setShutdownArray(int i, ShutdownType shutdown) {
      this.generatedSetterHelperImpl(shutdown, SHUTDOWN$18, i, (short)2);
   }

   public ShutdownType insertNewShutdown(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShutdownType target = null;
         target = (ShutdownType)this.get_store().insert_element_user(SHUTDOWN$18, i);
         return target;
      }
   }

   public ShutdownType addNewShutdown() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShutdownType target = null;
         target = (ShutdownType)this.get_store().add_element_user(SHUTDOWN$18);
         return target;
      }
   }

   public void removeShutdown(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHUTDOWN$18, i);
      }
   }

   public WeblogicModuleType[] getModuleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MODULE$20, targetList);
         WeblogicModuleType[] result = new WeblogicModuleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicModuleType getModuleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicModuleType target = null;
         target = (WeblogicModuleType)this.get_store().find_element_user(MODULE$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfModuleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULE$20);
      }
   }

   public void setModuleArray(WeblogicModuleType[] moduleArray) {
      this.check_orphaned();
      this.arraySetterHelper(moduleArray, MODULE$20);
   }

   public void setModuleArray(int i, WeblogicModuleType module) {
      this.generatedSetterHelperImpl(module, MODULE$20, i, (short)2);
   }

   public WeblogicModuleType insertNewModule(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicModuleType target = null;
         target = (WeblogicModuleType)this.get_store().insert_element_user(MODULE$20, i);
         return target;
      }
   }

   public WeblogicModuleType addNewModule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicModuleType target = null;
         target = (WeblogicModuleType)this.get_store().add_element_user(MODULE$20);
         return target;
      }
   }

   public void removeModule(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULE$20, i);
      }
   }

   public LibraryRefType[] getLibraryRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LIBRARYREF$22, targetList);
         LibraryRefType[] result = new LibraryRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LibraryRefType getLibraryRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().find_element_user(LIBRARYREF$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLibraryRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIBRARYREF$22);
      }
   }

   public void setLibraryRefArray(LibraryRefType[] libraryRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(libraryRefArray, LIBRARYREF$22);
   }

   public void setLibraryRefArray(int i, LibraryRefType libraryRef) {
      this.generatedSetterHelperImpl(libraryRef, LIBRARYREF$22, i, (short)2);
   }

   public LibraryRefType insertNewLibraryRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().insert_element_user(LIBRARYREF$22, i);
         return target;
      }
   }

   public LibraryRefType addNewLibraryRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().add_element_user(LIBRARYREF$22);
         return target;
      }
   }

   public void removeLibraryRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIBRARYREF$22, i);
      }
   }

   public FairShareRequestClassType[] getFairShareRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FAIRSHAREREQUEST$24, targetList);
         FairShareRequestClassType[] result = new FairShareRequestClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FairShareRequestClassType getFairShareRequestArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().find_element_user(FAIRSHAREREQUEST$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFairShareRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAIRSHAREREQUEST$24);
      }
   }

   public void setFairShareRequestArray(FairShareRequestClassType[] fairShareRequestArray) {
      this.check_orphaned();
      this.arraySetterHelper(fairShareRequestArray, FAIRSHAREREQUEST$24);
   }

   public void setFairShareRequestArray(int i, FairShareRequestClassType fairShareRequest) {
      this.generatedSetterHelperImpl(fairShareRequest, FAIRSHAREREQUEST$24, i, (short)2);
   }

   public FairShareRequestClassType insertNewFairShareRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().insert_element_user(FAIRSHAREREQUEST$24, i);
         return target;
      }
   }

   public FairShareRequestClassType addNewFairShareRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().add_element_user(FAIRSHAREREQUEST$24);
         return target;
      }
   }

   public void removeFairShareRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIRSHAREREQUEST$24, i);
      }
   }

   public ResponseTimeRequestClassType[] getResponseTimeRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESPONSETIMEREQUEST$26, targetList);
         ResponseTimeRequestClassType[] result = new ResponseTimeRequestClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResponseTimeRequestClassType getResponseTimeRequestArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().find_element_user(RESPONSETIMEREQUEST$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResponseTimeRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPONSETIMEREQUEST$26);
      }
   }

   public void setResponseTimeRequestArray(ResponseTimeRequestClassType[] responseTimeRequestArray) {
      this.check_orphaned();
      this.arraySetterHelper(responseTimeRequestArray, RESPONSETIMEREQUEST$26);
   }

   public void setResponseTimeRequestArray(int i, ResponseTimeRequestClassType responseTimeRequest) {
      this.generatedSetterHelperImpl(responseTimeRequest, RESPONSETIMEREQUEST$26, i, (short)2);
   }

   public ResponseTimeRequestClassType insertNewResponseTimeRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().insert_element_user(RESPONSETIMEREQUEST$26, i);
         return target;
      }
   }

   public ResponseTimeRequestClassType addNewResponseTimeRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().add_element_user(RESPONSETIMEREQUEST$26);
         return target;
      }
   }

   public void removeResponseTimeRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPONSETIMEREQUEST$26, i);
      }
   }

   public ContextRequestClassType[] getContextRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTEXTREQUEST$28, targetList);
         ContextRequestClassType[] result = new ContextRequestClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ContextRequestClassType getContextRequestArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRequestClassType target = null;
         target = (ContextRequestClassType)this.get_store().find_element_user(CONTEXTREQUEST$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContextRequestArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTREQUEST$28);
      }
   }

   public void setContextRequestArray(ContextRequestClassType[] contextRequestArray) {
      this.check_orphaned();
      this.arraySetterHelper(contextRequestArray, CONTEXTREQUEST$28);
   }

   public void setContextRequestArray(int i, ContextRequestClassType contextRequest) {
      this.generatedSetterHelperImpl(contextRequest, CONTEXTREQUEST$28, i, (short)2);
   }

   public ContextRequestClassType insertNewContextRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRequestClassType target = null;
         target = (ContextRequestClassType)this.get_store().insert_element_user(CONTEXTREQUEST$28, i);
         return target;
      }
   }

   public ContextRequestClassType addNewContextRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRequestClassType target = null;
         target = (ContextRequestClassType)this.get_store().add_element_user(CONTEXTREQUEST$28);
         return target;
      }
   }

   public void removeContextRequest(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTREQUEST$28, i);
      }
   }

   public MaxThreadsConstraintType[] getMaxThreadsConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAXTHREADSCONSTRAINT$30, targetList);
         MaxThreadsConstraintType[] result = new MaxThreadsConstraintType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MaxThreadsConstraintType getMaxThreadsConstraintArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxThreadsConstraintType target = null;
         target = (MaxThreadsConstraintType)this.get_store().find_element_user(MAXTHREADSCONSTRAINT$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMaxThreadsConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXTHREADSCONSTRAINT$30);
      }
   }

   public void setMaxThreadsConstraintArray(MaxThreadsConstraintType[] maxThreadsConstraintArray) {
      this.check_orphaned();
      this.arraySetterHelper(maxThreadsConstraintArray, MAXTHREADSCONSTRAINT$30);
   }

   public void setMaxThreadsConstraintArray(int i, MaxThreadsConstraintType maxThreadsConstraint) {
      this.generatedSetterHelperImpl(maxThreadsConstraint, MAXTHREADSCONSTRAINT$30, i, (short)2);
   }

   public MaxThreadsConstraintType insertNewMaxThreadsConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxThreadsConstraintType target = null;
         target = (MaxThreadsConstraintType)this.get_store().insert_element_user(MAXTHREADSCONSTRAINT$30, i);
         return target;
      }
   }

   public MaxThreadsConstraintType addNewMaxThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxThreadsConstraintType target = null;
         target = (MaxThreadsConstraintType)this.get_store().add_element_user(MAXTHREADSCONSTRAINT$30);
         return target;
      }
   }

   public void removeMaxThreadsConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXTHREADSCONSTRAINT$30, i);
      }
   }

   public MinThreadsConstraintType[] getMinThreadsConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MINTHREADSCONSTRAINT$32, targetList);
         MinThreadsConstraintType[] result = new MinThreadsConstraintType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MinThreadsConstraintType getMinThreadsConstraintArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MinThreadsConstraintType target = null;
         target = (MinThreadsConstraintType)this.get_store().find_element_user(MINTHREADSCONSTRAINT$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMinThreadsConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINTHREADSCONSTRAINT$32);
      }
   }

   public void setMinThreadsConstraintArray(MinThreadsConstraintType[] minThreadsConstraintArray) {
      this.check_orphaned();
      this.arraySetterHelper(minThreadsConstraintArray, MINTHREADSCONSTRAINT$32);
   }

   public void setMinThreadsConstraintArray(int i, MinThreadsConstraintType minThreadsConstraint) {
      this.generatedSetterHelperImpl(minThreadsConstraint, MINTHREADSCONSTRAINT$32, i, (short)2);
   }

   public MinThreadsConstraintType insertNewMinThreadsConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MinThreadsConstraintType target = null;
         target = (MinThreadsConstraintType)this.get_store().insert_element_user(MINTHREADSCONSTRAINT$32, i);
         return target;
      }
   }

   public MinThreadsConstraintType addNewMinThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MinThreadsConstraintType target = null;
         target = (MinThreadsConstraintType)this.get_store().add_element_user(MINTHREADSCONSTRAINT$32);
         return target;
      }
   }

   public void removeMinThreadsConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINTHREADSCONSTRAINT$32, i);
      }
   }

   public CapacityType[] getCapacityArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CAPACITY$34, targetList);
         CapacityType[] result = new CapacityType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CapacityType getCapacityArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CapacityType target = null;
         target = (CapacityType)this.get_store().find_element_user(CAPACITY$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCapacityArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITY$34);
      }
   }

   public void setCapacityArray(CapacityType[] capacityArray) {
      this.check_orphaned();
      this.arraySetterHelper(capacityArray, CAPACITY$34);
   }

   public void setCapacityArray(int i, CapacityType capacity) {
      this.generatedSetterHelperImpl(capacity, CAPACITY$34, i, (short)2);
   }

   public CapacityType insertNewCapacity(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CapacityType target = null;
         target = (CapacityType)this.get_store().insert_element_user(CAPACITY$34, i);
         return target;
      }
   }

   public CapacityType addNewCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CapacityType target = null;
         target = (CapacityType)this.get_store().add_element_user(CAPACITY$34);
         return target;
      }
   }

   public void removeCapacity(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CAPACITY$34, i);
      }
   }

   public WorkManagerType[] getWorkManagerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WORKMANAGER$36, targetList);
         WorkManagerType[] result = new WorkManagerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WorkManagerType getWorkManagerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWorkManagerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKMANAGER$36);
      }
   }

   public void setWorkManagerArray(WorkManagerType[] workManagerArray) {
      this.check_orphaned();
      this.arraySetterHelper(workManagerArray, WORKMANAGER$36);
   }

   public void setWorkManagerArray(int i, WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$36, i, (short)2);
   }

   public WorkManagerType insertNewWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().insert_element_user(WORKMANAGER$36, i);
         return target;
      }
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$36);
         return target;
      }
   }

   public void removeWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGER$36, i);
      }
   }

   public ManagedExecutorServiceType[] getManagedExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDEXECUTORSERVICE$38, targetList);
         ManagedExecutorServiceType[] result = new ManagedExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedExecutorServiceType getManagedExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().find_element_user(MANAGEDEXECUTORSERVICE$38, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDEXECUTORSERVICE$38);
      }
   }

   public void setManagedExecutorServiceArray(ManagedExecutorServiceType[] managedExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedExecutorServiceArray, MANAGEDEXECUTORSERVICE$38);
   }

   public void setManagedExecutorServiceArray(int i, ManagedExecutorServiceType managedExecutorService) {
      this.generatedSetterHelperImpl(managedExecutorService, MANAGEDEXECUTORSERVICE$38, i, (short)2);
   }

   public ManagedExecutorServiceType insertNewManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().insert_element_user(MANAGEDEXECUTORSERVICE$38, i);
         return target;
      }
   }

   public ManagedExecutorServiceType addNewManagedExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().add_element_user(MANAGEDEXECUTORSERVICE$38);
         return target;
      }
   }

   public void removeManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDEXECUTORSERVICE$38, i);
      }
   }

   public ManagedScheduledExecutorServiceType[] getManagedScheduledExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDSCHEDULEDEXECUTORSERVICE$40, targetList);
         ManagedScheduledExecutorServiceType[] result = new ManagedScheduledExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedScheduledExecutorServiceType getManagedScheduledExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().find_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedScheduledExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDSCHEDULEDEXECUTORSERVICE$40);
      }
   }

   public void setManagedScheduledExecutorServiceArray(ManagedScheduledExecutorServiceType[] managedScheduledExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedScheduledExecutorServiceArray, MANAGEDSCHEDULEDEXECUTORSERVICE$40);
   }

   public void setManagedScheduledExecutorServiceArray(int i, ManagedScheduledExecutorServiceType managedScheduledExecutorService) {
      this.generatedSetterHelperImpl(managedScheduledExecutorService, MANAGEDSCHEDULEDEXECUTORSERVICE$40, i, (short)2);
   }

   public ManagedScheduledExecutorServiceType insertNewManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().insert_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$40, i);
         return target;
      }
   }

   public ManagedScheduledExecutorServiceType addNewManagedScheduledExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().add_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$40);
         return target;
      }
   }

   public void removeManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDSCHEDULEDEXECUTORSERVICE$40, i);
      }
   }

   public ManagedThreadFactoryType[] getManagedThreadFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDTHREADFACTORY$42, targetList);
         ManagedThreadFactoryType[] result = new ManagedThreadFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedThreadFactoryType getManagedThreadFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().find_element_user(MANAGEDTHREADFACTORY$42, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedThreadFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDTHREADFACTORY$42);
      }
   }

   public void setManagedThreadFactoryArray(ManagedThreadFactoryType[] managedThreadFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedThreadFactoryArray, MANAGEDTHREADFACTORY$42);
   }

   public void setManagedThreadFactoryArray(int i, ManagedThreadFactoryType managedThreadFactory) {
      this.generatedSetterHelperImpl(managedThreadFactory, MANAGEDTHREADFACTORY$42, i, (short)2);
   }

   public ManagedThreadFactoryType insertNewManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().insert_element_user(MANAGEDTHREADFACTORY$42, i);
         return target;
      }
   }

   public ManagedThreadFactoryType addNewManagedThreadFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().add_element_user(MANAGEDTHREADFACTORY$42);
         return target;
      }
   }

   public void removeManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDTHREADFACTORY$42, i);
      }
   }

   public XsdStringType getComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(COMPONENTFACTORYCLASSNAME$44, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPONENTFACTORYCLASSNAME$44) != 0;
      }
   }

   public void setComponentFactoryClassName(XsdStringType componentFactoryClassName) {
      this.generatedSetterHelperImpl(componentFactoryClassName, COMPONENTFACTORYCLASSNAME$44, 0, (short)1);
   }

   public XsdStringType addNewComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(COMPONENTFACTORYCLASSNAME$44);
         return target;
      }
   }

   public void unsetComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPONENTFACTORYCLASSNAME$44, 0);
      }
   }

   public ApplicationAdminModeTriggerType getApplicationAdminModeTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationAdminModeTriggerType target = null;
         target = (ApplicationAdminModeTriggerType)this.get_store().find_element_user(APPLICATIONADMINMODETRIGGER$46, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetApplicationAdminModeTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(APPLICATIONADMINMODETRIGGER$46) != 0;
      }
   }

   public void setApplicationAdminModeTrigger(ApplicationAdminModeTriggerType applicationAdminModeTrigger) {
      this.generatedSetterHelperImpl(applicationAdminModeTrigger, APPLICATIONADMINMODETRIGGER$46, 0, (short)1);
   }

   public ApplicationAdminModeTriggerType addNewApplicationAdminModeTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationAdminModeTriggerType target = null;
         target = (ApplicationAdminModeTriggerType)this.get_store().add_element_user(APPLICATIONADMINMODETRIGGER$46);
         return target;
      }
   }

   public void unsetApplicationAdminModeTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPLICATIONADMINMODETRIGGER$46, 0);
      }
   }

   public SessionDescriptorType getSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionDescriptorType target = null;
         target = (SessionDescriptorType)this.get_store().find_element_user(SESSIONDESCRIPTOR$48, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONDESCRIPTOR$48) != 0;
      }
   }

   public void setSessionDescriptor(SessionDescriptorType sessionDescriptor) {
      this.generatedSetterHelperImpl(sessionDescriptor, SESSIONDESCRIPTOR$48, 0, (short)1);
   }

   public SessionDescriptorType addNewSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionDescriptorType target = null;
         target = (SessionDescriptorType)this.get_store().add_element_user(SESSIONDESCRIPTOR$48);
         return target;
      }
   }

   public void unsetSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONDESCRIPTOR$48, 0);
      }
   }

   public LibraryContextRootOverrideType[] getLibraryContextRootOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LIBRARYCONTEXTROOTOVERRIDE$50, targetList);
         LibraryContextRootOverrideType[] result = new LibraryContextRootOverrideType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LibraryContextRootOverrideType getLibraryContextRootOverrideArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryContextRootOverrideType target = null;
         target = (LibraryContextRootOverrideType)this.get_store().find_element_user(LIBRARYCONTEXTROOTOVERRIDE$50, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLibraryContextRootOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIBRARYCONTEXTROOTOVERRIDE$50);
      }
   }

   public void setLibraryContextRootOverrideArray(LibraryContextRootOverrideType[] libraryContextRootOverrideArray) {
      this.check_orphaned();
      this.arraySetterHelper(libraryContextRootOverrideArray, LIBRARYCONTEXTROOTOVERRIDE$50);
   }

   public void setLibraryContextRootOverrideArray(int i, LibraryContextRootOverrideType libraryContextRootOverride) {
      this.generatedSetterHelperImpl(libraryContextRootOverride, LIBRARYCONTEXTROOTOVERRIDE$50, i, (short)2);
   }

   public LibraryContextRootOverrideType insertNewLibraryContextRootOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryContextRootOverrideType target = null;
         target = (LibraryContextRootOverrideType)this.get_store().insert_element_user(LIBRARYCONTEXTROOTOVERRIDE$50, i);
         return target;
      }
   }

   public LibraryContextRootOverrideType addNewLibraryContextRootOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryContextRootOverrideType target = null;
         target = (LibraryContextRootOverrideType)this.get_store().add_element_user(LIBRARYCONTEXTROOTOVERRIDE$50);
         return target;
      }
   }

   public void removeLibraryContextRootOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIBRARYCONTEXTROOTOVERRIDE$50, i);
      }
   }

   public PreferApplicationPackagesType getPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationPackagesType target = null;
         target = (PreferApplicationPackagesType)this.get_store().find_element_user(PREFERAPPLICATIONPACKAGES$52, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERAPPLICATIONPACKAGES$52) != 0;
      }
   }

   public void setPreferApplicationPackages(PreferApplicationPackagesType preferApplicationPackages) {
      this.generatedSetterHelperImpl(preferApplicationPackages, PREFERAPPLICATIONPACKAGES$52, 0, (short)1);
   }

   public PreferApplicationPackagesType addNewPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationPackagesType target = null;
         target = (PreferApplicationPackagesType)this.get_store().add_element_user(PREFERAPPLICATIONPACKAGES$52);
         return target;
      }
   }

   public void unsetPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERAPPLICATIONPACKAGES$52, 0);
      }
   }

   public PreferApplicationResourcesType getPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationResourcesType target = null;
         target = (PreferApplicationResourcesType)this.get_store().find_element_user(PREFERAPPLICATIONRESOURCES$54, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERAPPLICATIONRESOURCES$54) != 0;
      }
   }

   public void setPreferApplicationResources(PreferApplicationResourcesType preferApplicationResources) {
      this.generatedSetterHelperImpl(preferApplicationResources, PREFERAPPLICATIONRESOURCES$54, 0, (short)1);
   }

   public PreferApplicationResourcesType addNewPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationResourcesType target = null;
         target = (PreferApplicationResourcesType)this.get_store().add_element_user(PREFERAPPLICATIONRESOURCES$54);
         return target;
      }
   }

   public void unsetPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERAPPLICATIONRESOURCES$54, 0);
      }
   }

   public FastSwapType getFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FastSwapType target = null;
         target = (FastSwapType)this.get_store().find_element_user(FASTSWAP$56, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FASTSWAP$56) != 0;
      }
   }

   public void setFastSwap(FastSwapType fastSwap) {
      this.generatedSetterHelperImpl(fastSwap, FASTSWAP$56, 0, (short)1);
   }

   public FastSwapType addNewFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FastSwapType target = null;
         target = (FastSwapType)this.get_store().add_element_user(FASTSWAP$56);
         return target;
      }
   }

   public void unsetFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FASTSWAP$56, 0);
      }
   }

   public CoherenceClusterRefType getCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().find_element_user(COHERENCECLUSTERREF$58, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERREF$58) != 0;
      }
   }

   public void setCoherenceClusterRef(CoherenceClusterRefType coherenceClusterRef) {
      this.generatedSetterHelperImpl(coherenceClusterRef, COHERENCECLUSTERREF$58, 0, (short)1);
   }

   public CoherenceClusterRefType addNewCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().add_element_user(COHERENCECLUSTERREF$58);
         return target;
      }
   }

   public void unsetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERREF$58, 0);
      }
   }

   public OsgiFrameworkReferenceType getOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OsgiFrameworkReferenceType target = null;
         target = (OsgiFrameworkReferenceType)this.get_store().find_element_user(OSGIFRAMEWORKREFERENCE$60, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OSGIFRAMEWORKREFERENCE$60) != 0;
      }
   }

   public void setOsgiFrameworkReference(OsgiFrameworkReferenceType osgiFrameworkReference) {
      this.generatedSetterHelperImpl(osgiFrameworkReference, OSGIFRAMEWORKREFERENCE$60, 0, (short)1);
   }

   public OsgiFrameworkReferenceType addNewOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OsgiFrameworkReferenceType target = null;
         target = (OsgiFrameworkReferenceType)this.get_store().add_element_user(OSGIFRAMEWORKREFERENCE$60);
         return target;
      }
   }

   public void unsetOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OSGIFRAMEWORKREFERENCE$60, 0);
      }
   }

   public ResourceDescriptionType[] getResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEDESCRIPTION$62, targetList);
         ResourceDescriptionType[] result = new ResourceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceDescriptionType getResourceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().find_element_user(RESOURCEDESCRIPTION$62, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEDESCRIPTION$62);
      }
   }

   public void setResourceDescriptionArray(ResourceDescriptionType[] resourceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceDescriptionArray, RESOURCEDESCRIPTION$62);
   }

   public void setResourceDescriptionArray(int i, ResourceDescriptionType resourceDescription) {
      this.generatedSetterHelperImpl(resourceDescription, RESOURCEDESCRIPTION$62, i, (short)2);
   }

   public ResourceDescriptionType insertNewResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().insert_element_user(RESOURCEDESCRIPTION$62, i);
         return target;
      }
   }

   public ResourceDescriptionType addNewResourceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().add_element_user(RESOURCEDESCRIPTION$62);
         return target;
      }
   }

   public void removeResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEDESCRIPTION$62, i);
      }
   }

   public ResourceEnvDescriptionType[] getResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVDESCRIPTION$64, targetList);
         ResourceEnvDescriptionType[] result = new ResourceEnvDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvDescriptionType getResourceEnvDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().find_element_user(RESOURCEENVDESCRIPTION$64, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEENVDESCRIPTION$64);
      }
   }

   public void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] resourceEnvDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvDescriptionArray, RESOURCEENVDESCRIPTION$64);
   }

   public void setResourceEnvDescriptionArray(int i, ResourceEnvDescriptionType resourceEnvDescription) {
      this.generatedSetterHelperImpl(resourceEnvDescription, RESOURCEENVDESCRIPTION$64, i, (short)2);
   }

   public ResourceEnvDescriptionType insertNewResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().insert_element_user(RESOURCEENVDESCRIPTION$64, i);
         return target;
      }
   }

   public ResourceEnvDescriptionType addNewResourceEnvDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().add_element_user(RESOURCEENVDESCRIPTION$64);
         return target;
      }
   }

   public void removeResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVDESCRIPTION$64, i);
      }
   }

   public EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREFERENCEDESCRIPTION$66, targetList);
         EjbReferenceDescriptionType[] result = new EjbReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().find_element_user(EJBREFERENCEDESCRIPTION$66, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREFERENCEDESCRIPTION$66);
      }
   }

   public void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] ejbReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbReferenceDescriptionArray, EJBREFERENCEDESCRIPTION$66);
   }

   public void setEjbReferenceDescriptionArray(int i, EjbReferenceDescriptionType ejbReferenceDescription) {
      this.generatedSetterHelperImpl(ejbReferenceDescription, EJBREFERENCEDESCRIPTION$66, i, (short)2);
   }

   public EjbReferenceDescriptionType insertNewEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().insert_element_user(EJBREFERENCEDESCRIPTION$66, i);
         return target;
      }
   }

   public EjbReferenceDescriptionType addNewEjbReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().add_element_user(EJBREFERENCEDESCRIPTION$66);
         return target;
      }
   }

   public void removeEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREFERENCEDESCRIPTION$66, i);
      }
   }

   public ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREFERENCEDESCRIPTION$68, targetList);
         ServiceReferenceDescriptionType[] result = new ServiceReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().find_element_user(SERVICEREFERENCEDESCRIPTION$68, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREFERENCEDESCRIPTION$68);
      }
   }

   public void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] serviceReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceReferenceDescriptionArray, SERVICEREFERENCEDESCRIPTION$68);
   }

   public void setServiceReferenceDescriptionArray(int i, ServiceReferenceDescriptionType serviceReferenceDescription) {
      this.generatedSetterHelperImpl(serviceReferenceDescription, SERVICEREFERENCEDESCRIPTION$68, i, (short)2);
   }

   public ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().insert_element_user(SERVICEREFERENCEDESCRIPTION$68, i);
         return target;
      }
   }

   public ServiceReferenceDescriptionType addNewServiceReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().add_element_user(SERVICEREFERENCEDESCRIPTION$68);
         return target;
      }
   }

   public void removeServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREFERENCEDESCRIPTION$68, i);
      }
   }

   public MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONDESCRIPTOR$70, targetList);
         MessageDestinationDescriptorType[] result = new MessageDestinationDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().find_element_user(MESSAGEDESTINATIONDESCRIPTOR$70, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONDESCRIPTOR$70);
      }
   }

   public void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] messageDestinationDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationDescriptorArray, MESSAGEDESTINATIONDESCRIPTOR$70);
   }

   public void setMessageDestinationDescriptorArray(int i, MessageDestinationDescriptorType messageDestinationDescriptor) {
      this.generatedSetterHelperImpl(messageDestinationDescriptor, MESSAGEDESTINATIONDESCRIPTOR$70, i, (short)2);
   }

   public MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().insert_element_user(MESSAGEDESTINATIONDESCRIPTOR$70, i);
         return target;
      }
   }

   public MessageDestinationDescriptorType addNewMessageDestinationDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().add_element_user(MESSAGEDESTINATIONDESCRIPTOR$70);
         return target;
      }
   }

   public void removeMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONDESCRIPTOR$70, i);
      }
   }

   public ClassLoadingType getClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassLoadingType target = null;
         target = (ClassLoadingType)this.get_store().find_element_user(CLASSLOADING$72, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSLOADING$72) != 0;
      }
   }

   public void setClassLoading(ClassLoadingType classLoading) {
      this.generatedSetterHelperImpl(classLoading, CLASSLOADING$72, 0, (short)1);
   }

   public ClassLoadingType addNewClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassLoadingType target = null;
         target = (ClassLoadingType)this.get_store().add_element_user(CLASSLOADING$72);
         return target;
      }
   }

   public void unsetClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSLOADING$72, 0);
      }
   }

   public String getReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READYREGISTRATION$74, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(READYREGISTRATION$74, 0);
         return target;
      }
   }

   public boolean isSetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(READYREGISTRATION$74) != 0;
      }
   }

   public void setReadyRegistration(String readyRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READYREGISTRATION$74, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(READYREGISTRATION$74);
         }

         target.setStringValue(readyRegistration);
      }
   }

   public void xsetReadyRegistration(XmlString readyRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(READYREGISTRATION$74, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(READYREGISTRATION$74);
         }

         target.set(readyRegistration);
      }
   }

   public void unsetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(READYREGISTRATION$74, 0);
      }
   }

   public CdiDescriptorType getCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().find_element_user(CDIDESCRIPTOR$76, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CDIDESCRIPTOR$76) != 0;
      }
   }

   public void setCdiDescriptor(CdiDescriptorType cdiDescriptor) {
      this.generatedSetterHelperImpl(cdiDescriptor, CDIDESCRIPTOR$76, 0, (short)1);
   }

   public CdiDescriptorType addNewCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().add_element_user(CDIDESCRIPTOR$76);
         return target;
      }
   }

   public void unsetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CDIDESCRIPTOR$76, 0);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$78);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$78);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$78) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$78);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$78);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$78);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$78);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$78);
      }
   }
}

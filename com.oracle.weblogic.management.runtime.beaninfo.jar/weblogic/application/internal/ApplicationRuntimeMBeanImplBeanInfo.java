package weblogic.application.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ApplicationRuntimeMBean;

public class ApplicationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ApplicationRuntimeMBean.class;

   public ApplicationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ApplicationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.application.internal.ApplicationRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.application.internal");
      String description = (new String("<p>An application represents a Java EE enterprise application packaged in an EAR file or EAR exploded directory. The EAR file or directory contains a set of components such as WAR, EJB, and RAR connector components, each of which can be deployed on one or more targets. A target is a server or a cluster. Modules in the application can have one of the following states:</p>  <ul> <li>UNPREPARED - Indicates that none of the  modules in this application are currently prepared or active.</li> <li>PREPARED -  Indicates that none of the  modules in this application are currently prepared or active.</li> <li>ACTIVATED - Indicates that at least one module in this application is currently active.</li> </ul>  <p>ApplicationRuntimeMBean encapsulates runtime information about a deployed enterprise application.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ApplicationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("ActiveVersionState")) {
         getterName = "getActiveVersionState";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveVersionState", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveVersionState", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this application version is the currently active version.</p>  <p>An application can be the only version currently deployed, or it can have more than one version currently deployed, using the side-by-side deployment feature. If more than one version is deployed, only one version can be active. This attribute specifies the state the current application version is in.</p>  <p>An application can be in an INACTIVE state, which means that it has not been activated yet, or that there is more than one version of the application deployed (using side-by-side deployment) and this version is retiring.</p>  <p>An application can be in ACTIVE_ADMIN state, which means that it is the currently active version for administrative channel requests.</p>  <p>An application can be in ACTIVE state, which means that it is the currently active version for normal (non-administrative) channel requests.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.deploy.version.AppActiveVersionState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>The name of the application.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationVersion")) {
         getterName = "getApplicationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationVersion", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationVersion", currentResult);
         currentResult.setValue("description", "<p>The application's version identifier.</p>  <p>This is particularly useful, when using the side-by-side deployment feature, to differentiate between two different versions of the same application that are deployed at the same time. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassLoaderRuntime")) {
         getterName = "getClassLoaderRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassLoaderRuntime", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassLoaderRuntime", currentResult);
         currentResult.setValue("description", "<p>Get statistics for system-level class loading.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassRedefinitionRuntime")) {
         getterName = "getClassRedefinitionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassRedefinitionRuntime", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassRedefinitionRuntime", currentResult);
         currentResult.setValue("description", "<p>If the class FastSwap feature is enabled for the application, returns the runtime MBean to monitor and control the class FastSwap within the application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceClusterRuntime")) {
         getterName = "getCoherenceClusterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterRuntime", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceClusterRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the Coherence Cluster related runtime MBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (!descriptors.containsKey("ComponentRuntimes")) {
         getterName = "getComponentRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ComponentRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ComponentRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of component runtime instances for each Java EE component (such as an EJB or a web application) that is contained in this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The HealthState MBean for the application. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("HealthStateJMX")) {
         getterName = "getHealthStateJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthStateJMX", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthStateJMX", currentResult);
         currentResult.setValue("description", "<p>The health state for the application. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("KodoPersistenceUnitRuntimes")) {
         getterName = "getKodoPersistenceUnitRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPersistenceUnitRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("KodoPersistenceUnitRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of KodoPersistenceUnitRuntimeMBean objects for this EJB module. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "As of 11.1.2.0, use getPersistenceUnitRuntimes instead ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryRuntimes")) {
         getterName = "getLibraryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of library runtime instances for each Java EE library that is contained in this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedExecutorServiceRuntimes")) {
         getterName = "getManagedExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of managed executor service runtime instances for each application-scoped managed executor service that is associated with this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedScheduledExecutorServiceRuntimes")) {
         getterName = "getManagedScheduledExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedScheduledExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of managed scheduled executor service runtime instances for each application-scoped managed scheduled executor service that is associated with this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedThreadFactoryRuntimes")) {
         getterName = "getManagedThreadFactoryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedThreadFactoryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of managed thread factory runtime instances for each application-scoped managed thread factory that is associated with this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxThreadsConstraintRuntimes")) {
         getterName = "getMaxThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraintRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime MBeans for all MaxThreadsConstraints defined at the application level.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinThreadsConstraintRuntimes")) {
         getterName = "getMinThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime MBeans for all MinThreadsConstraints defined at the application level.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("OptionalPackageRuntimes")) {
         getterName = "getOptionalPackageRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("OptionalPackageRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OptionalPackageRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of optional package runtime instances for each Java EE optional package that is contained in this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("OverallHealthState")) {
         getterName = "getOverallHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthState", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverallHealthState", currentResult);
         currentResult.setValue("description", "<p>The overall health of the application including that of some of the components that report health. Currently, only connector modules report health status and are the only ones considered in the overall health state of the application.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("OverallHealthStateJMX")) {
         getterName = "getOverallHealthStateJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthStateJMX", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverallHealthStateJMX", currentResult);
         currentResult.setValue("description", "<p>The overall health of the application including that of some of the components that report health. Currently, only connector modules report health status and are the only ones considered in the overall health state of the application.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The application's partition.</p>  <p>Returns the partition to which this application is deployed. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PersistenceUnitRuntimes")) {
         getterName = "getPersistenceUnitRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PersistenceUnitRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of PersistenceUnitRuntimeMBean objects for this EAR module. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueryCacheRuntimes")) {
         getterName = "getQueryCacheRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryCacheRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QueryCacheRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns a list of QueryCacheRuntimeMBeans configured for this application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RequestClassRuntimes")) {
         getterName = "getRequestClassRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestClassRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestClassRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime MBeans for all request classes defined at the application level.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of work manager runtime instances for each application-scoped work manager that is associated with this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("WseeRuntimes")) {
         getterName = "getWseeRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeRuntimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service runtime instances that are contained in this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "Use getWseeV2Runtimes from the web app or EJB component instead ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WseeV2Runtimes")) {
         getterName = "getWseeV2Runtimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeV2Runtimes", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeV2Runtimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service runtime instances that are contained at the application scope of this enterprise application. This can happen when javax.xml.ws.Endpoint.publish() is called from within an application lifecycle listener. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EAR")) {
         getterName = "isEAR";
         setterName = null;
         currentResult = new PropertyDescriptor("EAR", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EAR", currentResult);
         currentResult.setValue("description", "<p>Returns true if the application deployment unit is an EAR file. It returns false for WAR, JAR, RAR, etc. deployments. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("Internal")) {
         getterName = "isInternal";
         setterName = null;
         currentResult = new PropertyDescriptor("Internal", ApplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Internal", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this application is an internal application. Such applications are not displayed in the console or persisted in the config.xml. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupWorkManagerRuntime", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentName", (String)null), createParameterDescriptor("wmName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up the WorkManagerRuntimeMBean given the component name and work manager name. If the component name is null then the WorkManagerRuntime is retrieved from the application itself.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WorkManagerRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = ApplicationRuntimeMBean.class.getMethod("lookupWseeV2Runtime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The web service description name of the web service to look up. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service runtime instance that is contained at application scope of this enterprise application. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeV2Runtimes");
      }

      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupMinThreadsConstraintRuntime", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up MinThreadsConstraintRuntime given its name.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MinThreadsConstraintRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupRequestClassRuntime", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up RequestClassRuntime given its name.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "RequestClassRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupMaxThreadsConstraintRuntime", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up MaxThreadsConstraintRuntime given its name.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MaxThreadsConstraintRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = ApplicationRuntimeMBean.class.getMethod("lookupQueryCacheRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cacheName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a QueryCacheRuntimeMBean for the app-scoped query-cache with name 'cacheName'. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "QueryCacheRuntimes");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupManagedThreadFactoryRuntime", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentName", (String)null), createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up the ManagedThreadFactoryRuntimeMBean given the component name and managed thread factory name. If the component name is null then the ManagedThreadFactoryRuntimeMBean is retrieved from the application itself.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedThreadFactoryRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupManagedExecutorServiceRuntime", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentName", (String)null), createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up the ManagedExecutorServiceRuntimeMBean given the component name and managed executor service name. If the component name is null then the ManagedExecutorServiceRuntimeMBean is retrieved from the application itself.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedExecutorServiceRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationRuntimeMBean.class.getMethod("lookupManagedScheduledExecutorServiceRuntime", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentName", (String)null), createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up the ManagedScheduledExecutorServiceRuntimeMBean given the component name and managed scheduled executor service name. If the component name is null then the ManagedScheduledExecutorServiceRuntimeMBean is retrieved from the application itself.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedScheduledExecutorServiceRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationRuntimeMBean.class.getMethod("lookupComponents");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The ComponentRuntimeMBean's contained in this application.</p> ");
         currentResult.setValue("role", "operation");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ApplicationRuntimeMBean.class.getMethod("hasApplicationCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns true if the application has an (EJB) Application Level Cache</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ApplicationRuntimeMBean.class.getMethod("reInitializeApplicationCachesAndPools");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the application has an (EJB) Application Level Cache, then this method will reinitialize the cache and any of its associated pools to their startup time states if possible.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ApplicationRuntimeMBean.class.getMethod("getKodoPersistenceUnitRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("unitName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "As of 11.1.2.0, use getPersistenceUnitRuntime instead ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the KodoPersistenceUnitRuntimeMBean for the EJB with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ApplicationRuntimeMBean.class.getMethod("getPersistenceUnitRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("unitName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the PersistenceUnitRuntimeMBean for the application with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}

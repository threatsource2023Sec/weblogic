package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WeblogicApplicationBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WeblogicApplicationBean.class;

   public WeblogicApplicationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicApplicationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicApplicationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationAdminModeTrigger")) {
         getterName = "getApplicationAdminModeTrigger";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationAdminModeTrigger", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ApplicationAdminModeTrigger", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyApplicationAdminModeTrigger");
         currentResult.setValue("creator", "createApplicationAdminModeTrigger");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationParams")) {
         getterName = "getApplicationParams";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationParams", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ApplicationParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createApplicationParam");
         currentResult.setValue("destroyer", "destroyApplicationParam");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Capacities")) {
         getterName = "getCapacities";
         setterName = null;
         currentResult = new PropertyDescriptor("Capacities", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Capacities", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCapacity");
         currentResult.setValue("destroyer", "destroyCapacity");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CdiDescriptor")) {
         getterName = "getCdiDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("CdiDescriptor", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("CdiDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassLoading")) {
         getterName = "getClassLoading";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassLoading", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ClassLoading", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ClassloaderStructure")) {
         getterName = "getClassloaderStructure";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassloaderStructure", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ClassloaderStructure", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createClassloaderStructure");
         currentResult.setValue("destroyer", "destroyClassloaderStructure");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterRef")) {
         getterName = "getCoherenceClusterRef";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterRef", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterRef", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherenceClusterRef");
         currentResult.setValue("destroyer", "destroyCoherenceClusterRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ComponentFactoryClassName")) {
         getterName = "getComponentFactoryClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setComponentFactoryClassName";
         }

         currentResult = new PropertyDescriptor("ComponentFactoryClassName", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ComponentFactoryClassName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContextRequests")) {
         getterName = "getContextRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextRequests", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ContextRequests", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createContextRequest");
         currentResult.setValue("destroyer", "destroyContextRequest");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Ejb")) {
         getterName = "getEjb";
         setterName = null;
         currentResult = new PropertyDescriptor("Ejb", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Ejb", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyEjb");
         currentResult.setValue("creator", "createEjb");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbReferenceDescriptions")) {
         getterName = "getEjbReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbReferenceDescriptions", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("EjbReferenceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FairShareRequests")) {
         getterName = "getFairShareRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("FairShareRequests", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("FairShareRequests", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFairShareRequest");
         currentResult.setValue("creator", "createFairShareRequest");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FastSwap")) {
         getterName = "getFastSwap";
         setterName = null;
         currentResult = new PropertyDescriptor("FastSwap", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("FastSwap", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFastSwap");
         currentResult.setValue("destroyer", "destroyFastSwap");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCConnectionPools")) {
         getterName = "getJDBCConnectionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCConnectionPools", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("JDBCConnectionPools", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBCConnectionPool");
         currentResult.setValue("destroyer", "destroyJDBCConnectionPool");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryContextRootOverrides")) {
         getterName = "getLibraryContextRootOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryContextRootOverrides", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("LibraryContextRootOverrides", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createLibraryContextRootOverride");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryRefs")) {
         getterName = "getLibraryRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryRefs", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("LibraryRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLibraryRef");
         currentResult.setValue("creator", "createLibraryRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Listeners")) {
         getterName = "getListeners";
         setterName = null;
         currentResult = new PropertyDescriptor("Listeners", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Listeners", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createListener");
         currentResult.setValue("destroyer", "destroyListener");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedExecutorServices")) {
         getterName = "getManagedExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServices", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServices", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedExecutorService");
         currentResult.setValue("creator", "createManagedExecutorService");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedScheduledExecutorServices")) {
         getterName = "getManagedScheduledExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServices", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServices", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorService");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorService");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedThreadFactories")) {
         getterName = "getManagedThreadFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactories", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedThreadFactory");
         currentResult.setValue("creator", "createManagedThreadFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MaxThreadsConstraints")) {
         getterName = "getMaxThreadsConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraints", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraints", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMaxThreadsConstraint");
         currentResult.setValue("destroyer", "destroyMaxThreadsConstraint");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinationDescriptors")) {
         getterName = "getMessageDestinationDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationDescriptors", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("MessageDestinationDescriptors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraints")) {
         getterName = "getMinThreadsConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraints", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("MinThreadsConstraints", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMinThreadsConstraint");
         currentResult.setValue("destroyer", "destroyMinThreadsConstraint");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Modules")) {
         getterName = "getModules";
         setterName = null;
         currentResult = new PropertyDescriptor("Modules", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Modules", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyModule");
         currentResult.setValue("creator", "createModule");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OsgiFrameworkReference")) {
         getterName = "getOsgiFrameworkReference";
         setterName = null;
         currentResult = new PropertyDescriptor("OsgiFrameworkReference", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("OsgiFrameworkReference", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOsgiFrameworkReference");
         currentResult.setValue("creator", "createOsgiFrameworkReference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferApplicationPackages")) {
         getterName = "getPreferApplicationPackages";
         setterName = null;
         currentResult = new PropertyDescriptor("PreferApplicationPackages", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("PreferApplicationPackages", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPreferApplicationPackages");
         currentResult.setValue("creator", "createPreferApplicationPackages");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferApplicationResources")) {
         getterName = "getPreferApplicationResources";
         setterName = null;
         currentResult = new PropertyDescriptor("PreferApplicationResources", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("PreferApplicationResources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPreferApplicationResources");
         currentResult.setValue("creator", "createPreferApplicationResources");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ReadyRegistration")) {
         getterName = "getReadyRegistration";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadyRegistration", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ReadyRegistration", currentResult);
         currentResult.setValue("description", "Gets the Ready App \"ready-registration\" attribute ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ResourceDescriptions")) {
         getterName = "getResourceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDescriptions", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ResourceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvDescriptions")) {
         getterName = "getResourceEnvDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvDescriptions", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ResourceEnvDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeRequests")) {
         getterName = "getResponseTimeRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeRequests", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ResponseTimeRequests", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResponseTimeRequest");
         currentResult.setValue("destroyer", "destroyResponseTimeRequest");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Security")) {
         getterName = "getSecurity";
         setterName = null;
         currentResult = new PropertyDescriptor("Security", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Security", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSecurity");
         currentResult.setValue("destroyer", "destroySecurity");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceReferenceDescriptions")) {
         getterName = "getServiceReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceReferenceDescriptions", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("ServiceReferenceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionDescriptor")) {
         getterName = "getSessionDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionDescriptor", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("SessionDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Shutdowns")) {
         getterName = "getShutdowns";
         setterName = null;
         currentResult = new PropertyDescriptor("Shutdowns", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Shutdowns", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createShutdown");
         currentResult.setValue("destroyer", "destroyShutdown");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingletonServices")) {
         getterName = "getSingletonServices";
         setterName = null;
         currentResult = new PropertyDescriptor("SingletonServices", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("SingletonServices", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSingletonService");
         currentResult.setValue("destroyer", "destroySingletonService");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Startups")) {
         getterName = "getStartups";
         setterName = null;
         currentResult = new PropertyDescriptor("Startups", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Startups", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStartup");
         currentResult.setValue("destroyer", "destroyStartup");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManagers")) {
         getterName = "getWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagers", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("WorkManagers", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWorkManager");
         currentResult.setValue("creator", "createWorkManager");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Xml")) {
         getterName = "getXml";
         setterName = null;
         currentResult = new PropertyDescriptor("Xml", WeblogicApplicationBean.class, getterName, setterName);
         descriptors.put("Xml", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createXml");
         currentResult.setValue("destroyer", "destroyXml");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicApplicationBean.class.getMethod("createEjb");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Ejb");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyEjb", EjbBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Ejb");
      }

      mth = WeblogicApplicationBean.class.getMethod("createXml");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Xml");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyXml", XmlBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Xml");
      }

      mth = WeblogicApplicationBean.class.getMethod("createJDBCConnectionPool");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCConnectionPools");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyJDBCConnectionPool", JDBCConnectionPoolBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCConnectionPools");
      }

      mth = WeblogicApplicationBean.class.getMethod("createSecurity");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Security");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroySecurity", SecurityBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Security");
      }

      mth = WeblogicApplicationBean.class.getMethod("createApplicationParam");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationParams");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyApplicationParam", ApplicationParamBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationParams");
      }

      mth = WeblogicApplicationBean.class.getMethod("createClassloaderStructure");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClassloaderStructure");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyClassloaderStructure", ClassloaderStructureBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClassloaderStructure");
      }

      mth = WeblogicApplicationBean.class.getMethod("createListener");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Listeners");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyListener", ListenerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Listeners");
      }

      mth = WeblogicApplicationBean.class.getMethod("createSingletonService");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingletonServices");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroySingletonService", SingletonServiceBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingletonServices");
      }

      mth = WeblogicApplicationBean.class.getMethod("createStartup");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Startups");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyStartup", StartupBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Startups");
      }

      mth = WeblogicApplicationBean.class.getMethod("createShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Shutdowns");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyShutdown", ShutdownBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Shutdowns");
      }

      mth = WeblogicApplicationBean.class.getMethod("createModule");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Modules");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyModule", WeblogicModuleBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Modules");
      }

      mth = WeblogicApplicationBean.class.getMethod("createLibraryRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LibraryRefs");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyLibraryRef", LibraryRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LibraryRefs");
      }

      mth = WeblogicApplicationBean.class.getMethod("createFairShareRequest");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FairShareRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyFairShareRequest", FairShareRequestClassBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FairShareRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("createResponseTimeRequest");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResponseTimeRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyResponseTimeRequest", ResponseTimeRequestClassBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResponseTimeRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("createContextRequest");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ContextRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyContextRequest", ContextRequestClassBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ContextRequests");
      }

      mth = WeblogicApplicationBean.class.getMethod("createMaxThreadsConstraint");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MaxThreadsConstraints");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyMaxThreadsConstraint", MaxThreadsConstraintBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MaxThreadsConstraints");
      }

      mth = WeblogicApplicationBean.class.getMethod("createMinThreadsConstraint");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MinThreadsConstraints");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyMinThreadsConstraint", MinThreadsConstraintBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MinThreadsConstraints");
      }

      mth = WeblogicApplicationBean.class.getMethod("createCapacity");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Capacities");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyCapacity", CapacityBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Capacities");
      }

      mth = WeblogicApplicationBean.class.getMethod("createWorkManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManagers");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyWorkManager", WorkManagerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManagers");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("createManagedExecutorService");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("destroyManagedExecutorService", ManagedExecutorServiceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("createManagedScheduledExecutorService");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("destroyManagedScheduledExecutorService", ManagedScheduledExecutorServiceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("createManagedThreadFactory");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicApplicationBean.class.getMethod("destroyManagedThreadFactory", ManagedThreadFactoryBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WeblogicApplicationBean.class.getMethod("createApplicationAdminModeTrigger");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationAdminModeTrigger");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyApplicationAdminModeTrigger");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationAdminModeTrigger");
      }

      mth = WeblogicApplicationBean.class.getMethod("createLibraryContextRootOverride");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LibraryContextRootOverrides");
      }

      mth = WeblogicApplicationBean.class.getMethod("createPreferApplicationPackages");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreferApplicationPackages");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyPreferApplicationPackages");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreferApplicationPackages");
      }

      mth = WeblogicApplicationBean.class.getMethod("createPreferApplicationResources");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreferApplicationResources");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyPreferApplicationResources");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreferApplicationResources");
      }

      mth = WeblogicApplicationBean.class.getMethod("createFastSwap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FastSwap");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyFastSwap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FastSwap");
      }

      mth = WeblogicApplicationBean.class.getMethod("createCoherenceClusterRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterRef");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyCoherenceClusterRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterRef");
      }

      mth = WeblogicApplicationBean.class.getMethod("createOsgiFrameworkReference");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OsgiFrameworkReference");
      }

      mth = WeblogicApplicationBean.class.getMethod("destroyOsgiFrameworkReference");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OsgiFrameworkReference");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicApplicationBean.class.getMethod("convertToWeblogicEnvironmentBean");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("transient", Boolean.TRUE);
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

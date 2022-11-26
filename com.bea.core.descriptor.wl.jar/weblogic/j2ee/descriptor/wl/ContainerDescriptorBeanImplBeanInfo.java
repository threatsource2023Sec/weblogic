package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ContainerDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ContainerDescriptorBean.class;

   public ContainerDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ContainerDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ContainerDescriptorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ContainerDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CheckAuthOnForward")) {
         getterName = "getCheckAuthOnForward";
         setterName = null;
         currentResult = new PropertyDescriptor("CheckAuthOnForward", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("CheckAuthOnForward", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCheckAuthOnForward");
         currentResult.setValue("destroyer", "destroyCheckAuthOnForward");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassLoading")) {
         getterName = "getClassLoading";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassLoading", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ClassLoading", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("DefaultMimeType")) {
         getterName = "getDefaultMimeType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMimeType";
         }

         currentResult = new PropertyDescriptor("DefaultMimeType", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("DefaultMimeType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailDeployOnFilterInitError")) {
         getterName = "getFailDeployOnFilterInitError";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailDeployOnFilterInitError";
         }

         currentResult = new PropertyDescriptor("FailDeployOnFilterInitError", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("FailDeployOnFilterInitError", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("GzipCompression")) {
         getterName = "getGzipCompression";
         setterName = null;
         currentResult = new PropertyDescriptor("GzipCompression", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("GzipCompression", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyGzipCompression");
         currentResult.setValue("creator", "createGzipCompression");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexDirectorySortBy")) {
         getterName = "getIndexDirectorySortBy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexDirectorySortBy";
         }

         currentResult = new PropertyDescriptor("IndexDirectorySortBy", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("IndexDirectorySortBy", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NAME");
         currentResult.setValue("legalValues", new Object[]{"NAME", "LAST_MODIFIED", "SIZE"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LangtagRevision")) {
         getterName = "getLangtagRevision";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLangtagRevision";
         }

         currentResult = new PropertyDescriptor("LangtagRevision", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("LangtagRevision", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"3066", "5646"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumNativeFileSize")) {
         getterName = "getMinimumNativeFileSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinimumNativeFileSize";
         }

         currentResult = new PropertyDescriptor("MinimumNativeFileSize", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("MinimumNativeFileSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Long(4096L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferApplicationPackages")) {
         getterName = "getPreferApplicationPackages";
         setterName = null;
         currentResult = new PropertyDescriptor("PreferApplicationPackages", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("PreferApplicationPackages", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferApplicationResources")) {
         getterName = "getPreferApplicationResources";
         setterName = null;
         currentResult = new PropertyDescriptor("PreferApplicationResources", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("PreferApplicationResources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedirectContent")) {
         getterName = "getRedirectContent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedirectContent";
         }

         currentResult = new PropertyDescriptor("RedirectContent", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RedirectContent", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedirectContentType")) {
         getterName = "getRedirectContentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedirectContentType";
         }

         currentResult = new PropertyDescriptor("RedirectContentType", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RedirectContentType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefererValidation")) {
         getterName = "getRefererValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefererValidation";
         }

         currentResult = new PropertyDescriptor("RefererValidation", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RefererValidation", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LENIENT");
         currentResult.setValue("legalValues", new Object[]{"NONE", "LENIENT", "STRICT"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceReloadCheckSecs")) {
         getterName = "getResourceReloadCheckSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceReloadCheckSecs";
         }

         currentResult = new PropertyDescriptor("ResourceReloadCheckSecs", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ResourceReloadCheckSecs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restProductionModeDefault", new Integer(-1));
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletReloadCheckSecs")) {
         getterName = "getServletReloadCheckSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletReloadCheckSecs";
         }

         currentResult = new PropertyDescriptor("ServletReloadCheckSecs", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ServletReloadCheckSecs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restProductionModeDefault", new Integer(-1));
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleThreadedServletPoolSize")) {
         getterName = "getSingleThreadedServletPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingleThreadedServletPoolSize";
         }

         currentResult = new PropertyDescriptor("SingleThreadedServletPoolSize", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("SingleThreadedServletPoolSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("deprecated", "since WebLogic 9.0 release, since SingleThreadModel has been deprecated since Servlet 2.4 ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TempDir")) {
         getterName = "getTempDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTempDir";
         }

         currentResult = new PropertyDescriptor("TempDir", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("TempDir", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AccessLoggingDisabled")) {
         getterName = "isAccessLoggingDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessLoggingDisabled";
         }

         currentResult = new PropertyDescriptor("AccessLoggingDisabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("AccessLoggingDisabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowAllRoles")) {
         getterName = "isAllowAllRoles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowAllRoles";
         }

         currentResult = new PropertyDescriptor("AllowAllRoles", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("AllowAllRoles", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertProxyEnabled")) {
         getterName = "isClientCertProxyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertProxyEnabled";
         }

         currentResult = new PropertyDescriptor("ClientCertProxyEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ClientCertProxyEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContainerInitializerEnabled")) {
         getterName = "isContainerInitializerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContainerInitializerEnabled";
         }

         currentResult = new PropertyDescriptor("ContainerInitializerEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ContainerInitializerEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisableImplicitServletMappings")) {
         getterName = "isDisableImplicitServletMappings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisableImplicitServletMappings";
         }

         currentResult = new PropertyDescriptor("DisableImplicitServletMappings", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("DisableImplicitServletMappings", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FilterDispatchedRequestsEnabled")) {
         getterName = "isFilterDispatchedRequestsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFilterDispatchedRequestsEnabled";
         }

         currentResult = new PropertyDescriptor("FilterDispatchedRequestsEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("FilterDispatchedRequestsEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexDirectoryEnabled")) {
         getterName = "isIndexDirectoryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexDirectoryEnabled";
         }

         currentResult = new PropertyDescriptor("IndexDirectoryEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("IndexDirectoryEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NativeIOEnabled")) {
         getterName = "isNativeIOEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNativeIOEnabled";
         }

         currentResult = new PropertyDescriptor("NativeIOEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("NativeIOEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OptimisticSerialization")) {
         getterName = "isOptimisticSerialization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimisticSerialization";
         }

         currentResult = new PropertyDescriptor("OptimisticSerialization", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("OptimisticSerialization", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferForwardQueryString")) {
         getterName = "isPreferForwardQueryString";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreferForwardQueryString";
         }

         currentResult = new PropertyDescriptor("PreferForwardQueryString", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("PreferForwardQueryString", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreferWebInfClasses")) {
         getterName = "isPreferWebInfClasses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreferWebInfClasses";
         }

         currentResult = new PropertyDescriptor("PreferWebInfClasses", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("PreferWebInfClasses", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedirectWithAbsoluteUrl")) {
         getterName = "isRedirectWithAbsoluteUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedirectWithAbsoluteUrl";
         }

         currentResult = new PropertyDescriptor("RedirectWithAbsoluteUrl", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RedirectWithAbsoluteUrl", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReloginEnabled")) {
         getterName = "isReloginEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReloginEnabled";
         }

         currentResult = new PropertyDescriptor("ReloginEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ReloginEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequireAdminTraffic")) {
         getterName = "isRequireAdminTraffic";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequireAdminTraffic";
         }

         currentResult = new PropertyDescriptor("RequireAdminTraffic", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RequireAdminTraffic", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetainOriginalURL")) {
         getterName = "isRetainOriginalURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetainOriginalURL";
         }

         currentResult = new PropertyDescriptor("RetainOriginalURL", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("RetainOriginalURL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SaveSessionsEnabled")) {
         getterName = "isSaveSessionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSaveSessionsEnabled";
         }

         currentResult = new PropertyDescriptor("SaveSessionsEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("SaveSessionsEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SendPermanentRedirects")) {
         getterName = "isSendPermanentRedirects";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSendPermanentRedirects";
         }

         currentResult = new PropertyDescriptor("SendPermanentRedirects", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("SendPermanentRedirects", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionMonitoringEnabled")) {
         getterName = "isSessionMonitoringEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionMonitoringEnabled";
         }

         currentResult = new PropertyDescriptor("SessionMonitoringEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("SessionMonitoringEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShowArchivedRealPathEnabled")) {
         getterName = "isShowArchivedRealPathEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShowArchivedRealPathEnabled";
         }

         currentResult = new PropertyDescriptor("ShowArchivedRealPathEnabled", ContainerDescriptorBean.class, getterName, setterName);
         descriptors.put("ShowArchivedRealPathEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ContainerDescriptorBean.class.getMethod("createCheckAuthOnForward");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CheckAuthOnForward");
      }

      mth = ContainerDescriptorBean.class.getMethod("destroyCheckAuthOnForward", EmptyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CheckAuthOnForward");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ContainerDescriptorBean.class.getMethod("createGzipCompression");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "GzipCompression");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ContainerDescriptorBean.class.getMethod("destroyGzipCompression");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "GzipCompression");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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

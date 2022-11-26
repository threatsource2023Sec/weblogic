package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class SessionDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = SessionDescriptorBean.class;

   public SessionDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SessionDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SessionDescriptorBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SessionDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AuthCookieIdLength")) {
         getterName = "getAuthCookieIdLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthCookieIdLength";
         }

         currentResult = new PropertyDescriptor("AuthCookieIdLength", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("AuthCookieIdLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("legalMin", new Integer(8));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CacheSize")) {
         getterName = "getCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheSize";
         }

         currentResult = new PropertyDescriptor("CacheSize", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1024));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieComment")) {
         getterName = "getCookieComment";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieComment";
         }

         currentResult = new PropertyDescriptor("CookieComment", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieComment", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieDomain")) {
         getterName = "getCookieDomain";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieDomain";
         }

         currentResult = new PropertyDescriptor("CookieDomain", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieDomain", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieMaxAgeSecs")) {
         getterName = "getCookieMaxAgeSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieMaxAgeSecs";
         }

         currentResult = new PropertyDescriptor("CookieMaxAgeSecs", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieMaxAgeSecs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieName")) {
         getterName = "getCookieName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieName";
         }

         currentResult = new PropertyDescriptor("CookieName", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "JSESSIONID");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookiePath")) {
         getterName = "getCookiePath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookiePath";
         }

         currentResult = new PropertyDescriptor("CookiePath", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookiePath", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "/");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdLength")) {
         getterName = "getIdLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdLength";
         }

         currentResult = new PropertyDescriptor("IdLength", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("IdLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(52));
         currentResult.setValue("legalMin", new Integer(8));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvalidationIntervalSecs")) {
         getterName = "getInvalidationIntervalSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInvalidationIntervalSecs";
         }

         currentResult = new PropertyDescriptor("InvalidationIntervalSecs", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("InvalidationIntervalSecs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JdbcColumnNameMaxInactiveInterval")) {
         getterName = "getJdbcColumnNameMaxInactiveInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJdbcColumnNameMaxInactiveInterval";
         }

         currentResult = new PropertyDescriptor("JdbcColumnNameMaxInactiveInterval", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("JdbcColumnNameMaxInactiveInterval", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "wl_max_inactive_interval");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxInMemorySessions")) {
         getterName = "getMaxInMemorySessions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxInMemorySessions";
         }

         currentResult = new PropertyDescriptor("MaxInMemorySessions", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("MaxInMemorySessions", currentResult);
         currentResult.setValue("description", "A -ve value indicates unlimited in-memory sessions ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxSavePostSize")) {
         getterName = "getMaxSavePostSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxSavePostSize";
         }

         currentResult = new PropertyDescriptor("MaxSavePostSize", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("MaxSavePostSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(4096));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MonitoringAttributeName")) {
         getterName = "getMonitoringAttributeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonitoringAttributeName";
         }

         currentResult = new PropertyDescriptor("MonitoringAttributeName", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("MonitoringAttributeName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentAsyncQueueTimeout")) {
         getterName = "getPersistentAsyncQueueTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentAsyncQueueTimeout";
         }

         currentResult = new PropertyDescriptor("PersistentAsyncQueueTimeout", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentAsyncQueueTimeout", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentDataSourceJNDIName")) {
         getterName = "getPersistentDataSourceJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentDataSourceJNDIName";
         }

         currentResult = new PropertyDescriptor("PersistentDataSourceJNDIName", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentDataSourceJNDIName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentSessionFlushInterval")) {
         getterName = "getPersistentSessionFlushInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentSessionFlushInterval";
         }

         currentResult = new PropertyDescriptor("PersistentSessionFlushInterval", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentSessionFlushInterval", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(180));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentSessionFlushThreshold")) {
         getterName = "getPersistentSessionFlushThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentSessionFlushThreshold";
         }

         currentResult = new PropertyDescriptor("PersistentSessionFlushThreshold", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentSessionFlushThreshold", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStoreCookieName")) {
         getterName = "getPersistentStoreCookieName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStoreCookieName";
         }

         currentResult = new PropertyDescriptor("PersistentStoreCookieName", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentStoreCookieName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "WLCOOKIE");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStoreDir")) {
         getterName = "getPersistentStoreDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStoreDir";
         }

         currentResult = new PropertyDescriptor("PersistentStoreDir", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentStoreDir", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "session_db");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStorePool")) {
         getterName = "getPersistentStorePool";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStorePool";
         }

         currentResult = new PropertyDescriptor("PersistentStorePool", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentStorePool", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStoreTable")) {
         getterName = "getPersistentStoreTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStoreTable";
         }

         currentResult = new PropertyDescriptor("PersistentStoreTable", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentStoreTable", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "wl_servlet_sessions");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStoreType")) {
         getterName = "getPersistentStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStoreType";
         }

         currentResult = new PropertyDescriptor("PersistentStoreType", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("PersistentStoreType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "memory");
         currentResult.setValue("legalValues", new Object[]{"memory", "replicated", "replicated_if_clustered", "file", "jdbc", "cookie", "async-replicated", "async-replicated-if-clustered", "async-jdbc", "coherence-web"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SavePostTimeoutIntervalSecs")) {
         getterName = "getSavePostTimeoutIntervalSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSavePostTimeoutIntervalSecs";
         }

         currentResult = new PropertyDescriptor("SavePostTimeoutIntervalSecs", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("SavePostTimeoutIntervalSecs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SavePostTimeoutSecs")) {
         getterName = "getSavePostTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSavePostTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("SavePostTimeoutSecs", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("SavePostTimeoutSecs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(40));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutSecs")) {
         getterName = "getTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("TimeoutSecs", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("TimeoutSecs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieHttpOnly")) {
         getterName = "isCookieHttpOnly";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieHttpOnly";
         }

         currentResult = new PropertyDescriptor("CookieHttpOnly", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieHttpOnly", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookieSecure")) {
         getterName = "isCookieSecure";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieSecure";
         }

         currentResult = new PropertyDescriptor("CookieSecure", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookieSecure", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookiesEnabled")) {
         getterName = "isCookiesEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookiesEnabled";
         }

         currentResult = new PropertyDescriptor("CookiesEnabled", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("CookiesEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugEnabled")) {
         getterName = "isDebugEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugEnabled";
         }

         currentResult = new PropertyDescriptor("DebugEnabled", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("DebugEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncodeSessionIdInQueryParams")) {
         getterName = "isEncodeSessionIdInQueryParams";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncodeSessionIdInQueryParams";
         }

         currentResult = new PropertyDescriptor("EncodeSessionIdInQueryParams", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("EncodeSessionIdInQueryParams", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpProxyCachingOfCookies")) {
         getterName = "isHttpProxyCachingOfCookies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpProxyCachingOfCookies";
         }

         currentResult = new PropertyDescriptor("HttpProxyCachingOfCookies", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("HttpProxyCachingOfCookies", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvalidateOnRelogin")) {
         getterName = "isInvalidateOnRelogin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInvalidateOnRelogin";
         }

         currentResult = new PropertyDescriptor("InvalidateOnRelogin", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("InvalidateOnRelogin", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SharingEnabled")) {
         getterName = "isSharingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSharingEnabled";
         }

         currentResult = new PropertyDescriptor("SharingEnabled", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("SharingEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrackingEnabled")) {
         getterName = "isTrackingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrackingEnabled";
         }

         currentResult = new PropertyDescriptor("TrackingEnabled", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("TrackingEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UrlRewritingEnabled")) {
         getterName = "isUrlRewritingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUrlRewritingEnabled";
         }

         currentResult = new PropertyDescriptor("UrlRewritingEnabled", SessionDescriptorBean.class, getterName, setterName);
         descriptors.put("UrlRewritingEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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

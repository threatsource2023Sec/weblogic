package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class AdminConsoleMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AdminConsoleMBean.class;

   public AdminConsoleMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AdminConsoleMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.AdminConsoleMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("AdminConsoleMBean is a console specific MBean to configure weblogic administration console attributes. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.AdminConsoleMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CookieName")) {
         getterName = "getCookieName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookieName";
         }

         currentResult = new PropertyDescriptor("CookieName", AdminConsoleMBean.class, getterName, setterName);
         descriptors.put("CookieName", currentResult);
         currentResult.setValue("description", "<p>Returns the Cookie Name used by the Administration Console. </p> ");
         setPropertyDescriptorDefault(currentResult, "ADMINCONSOLESESSION");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinThreads")) {
         getterName = "getMinThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinThreads";
         }

         currentResult = new PropertyDescriptor("MinThreads", AdminConsoleMBean.class, getterName, setterName);
         descriptors.put("MinThreads", currentResult);
         currentResult.setValue("description", "<p>Specify the minimum number of threads that should be dedicated to the Administration Console. These threads are used to interact with managed servers in parallel for improved responsiveness in large domains. 5 is the smallest accepted number.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(101));
         currentResult.setValue("legalMin", new Integer(5));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("SSOLogoutURL")) {
         getterName = "getSSOLogoutURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSOLogoutURL";
         }

         currentResult = new PropertyDescriptor("SSOLogoutURL", AdminConsoleMBean.class, getterName, setterName);
         descriptors.put("SSOLogoutURL", currentResult);
         currentResult.setValue("description", "<p>Returns the log out URL for the Administration Console in a single sign-on (SSO) environment. Only needed when you use the Console with an SSO provider that requires a log out URL so that it can clean up its SSO session state. See the SSO provider documentation for details on the URL value</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionTimeout")) {
         getterName = "getSessionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionTimeout";
         }

         currentResult = new PropertyDescriptor("SessionTimeout", AdminConsoleMBean.class, getterName, setterName);
         descriptors.put("SessionTimeout", currentResult);
         currentResult.setValue("description", "<p>Returns Session Timeout value (in seconds) for Administration Console. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ProtectedCookieEnabled")) {
         getterName = "isProtectedCookieEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProtectedCookieEnabled";
         }

         currentResult = new PropertyDescriptor("ProtectedCookieEnabled", AdminConsoleMBean.class, getterName, setterName);
         descriptors.put("ProtectedCookieEnabled", currentResult);
         currentResult.setValue("description", "<p>Set to true if the Administration Console's session cookie is protected so that it is only visible to the Console. This may prevent use of this shared cookie in other applications.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
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

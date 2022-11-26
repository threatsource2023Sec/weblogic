package weblogic.console.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ConsoleRuntimeMBean;

public class ConsoleRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConsoleRuntimeMBean.class;

   public ConsoleRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConsoleRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.console.internal.ConsoleRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.1.0");
      beanDescriptor.setValue("package", "weblogic.console.internal");
      String description = (new String("<p>Runtime services for the console.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConsoleRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("HomePageURL")) {
         getterName = "getHomePageURL";
         setterName = null;
         currentResult = new PropertyDescriptor("HomePageURL", ConsoleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HomePageURL", currentResult);
         currentResult.setValue("description", "Gets the URL for the console's home page ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("Enabled", ConsoleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "Is the console enabled? ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getDefaultPageURL", String[].class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("context", "Identifies the object the page should display or manage.  Most pages display or manage WLS mbeans (such as a server or cluster).  For them, call \"getObjectNameContext\", passing in the JMX object name, to get the context to pass to this method.  See the console programmers' guide for more information on contexts. "), createParameterDescriptor("perspective", "Specifies which kind of default page to return (e.g. configuration or monitoring).  Pass in null to get the default page for the object.  See the console programmers' guide for more information on perspectives. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if context is null or empty")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URL for the default page of an object.  You can optionally specify the kind of default page (e.g. configuration or monitoring).  For example, use this method to get a WLS cluster's default page's URL.  Use this method when possible since it isolates the caller from the specific console page labels.  This method does not check whether the page exists. To find out, use the URL and see if it works. ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getObjectNameContext")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.5.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getDefaultPageURLs", String[][].class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("contexts", "An array of contexts identifying the object each page should display or manage. "), createParameterDescriptor("perspective", "Specifies which kind of default page to return for all of the pages. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if contexts is null, or if there  is a problem with the information for any of the pages  (e.g. contexts[2] is null or an empty string).   The returned array of URLs parallels the contexts array.  For example, return[1] contains the result for contexts[1].")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.5.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URLs for the default pages for a set of objects.  This method works exactly like getDefaultPageURL, except that it lets you get the URLS for a set of pages in one JMX call (to increase network performance). ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDefaultPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.5.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.5.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getDefaultPageURLs", String[][].class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("contexts", "An array of contexts identifying the object each page should display or manage. "), createParameterDescriptor("perspectives", "An array of perspectives identifying which kind of default page to return for each object. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if contexts or perspectives is null,  contexts and perspectives don't have the same length, or if there is a  problem with the information for any of the pages  (e.g. contexts[2] is null or an empty string).   The contexts and perspecitives arrays are parallel arrays specifying  each desired page.  They must be the same length.  For example, contexts[1] and perspectives[1] are used to indicate  the information needed to compute the URL for the second page.   Similarly, the returned array of URLs parallels the contexts and  perspectives arrays.  For example, return[1] contains the result  for contexts[1]/perspectives[1].")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.5.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URLs for the default pages for a set of objects.  This method works exactly like getDefaultPageURL, except that it lets you get the URLS for a set of pages in one JMX call (to increase network performance). ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDefaultPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.5.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getSpecificPageURL", String.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("page", "The portal page label of the desired console page. "), createParameterDescriptor("context", "Identifies the object the page should display or manage.  Most pages display or manage WLS mbeans (such as a server or cluster).  For them, call \"getObjectNameContext\", passing in the JMX object name, to get the context to pass to this method.  See the console programmers' guide for more information on contexts. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if page is null or empty")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URL for a specific console page.  For example, use this method to get a WLS server's SSL configuration page's URL or the servers table page's URL.  Note: WLS reserves the right to change its portal page names in future releases.  Therefore, customers are advised to use \"getDefaultPageURL\" if possible so that they'll be isolated from these kinds of changes.  This method does not check whether the page exists. To find out, use the URL and see if it works. ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDefaultPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.5.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getSpecificPageURLs", String.class, String[][].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("page", "The portal page label of the desired console pages. "), createParameterDescriptor("contexts", "An array of contexts identifying the object each page should display or manage. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if page is null or empty,  if contexts is null, or if there is a problem with the information  for any of the pages (e.g. pages[2] is null or an empty string).   The returned array of URLs parallels the contexts array.  For  example, return[1] contains the result for pages[1]/contexts[1].")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.5.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URLs for a set of specific console page.  This method works exactly like getSpecificPageURL, except that it lets you get the URLS for a set of pages in one JMX call (to increase network performance). ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSpecificPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.5.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.5.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getSpecificPageURLs", String[].class, String[][].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("pages", "An array of portal page labels of the desired console pages. "), createParameterDescriptor("contexts", "An array of contexts identifying the object each page should display or manage. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if pages or contexts is null,  pages and contexts don't have the same length, or if there is a  problem with the information for any of the pages  (e.g. pages[2] is null or an empty string).   The pages and contexts arrays are parallel arrays specifying  each desired page.  They must be the same length.  For example, pages[1] and contexts[1] are used to indicate  the information needed to compute the URL for the second page.   Similarly, the returned array of URLs parallels the pages  and contexts and pages arrays.  For example, return[1] contains  the result for pages[1]/contexts[1].")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.5.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the URLs for a set of specific console pages.  This method works exactly like getSpecificPageURL, except that it lets you get the URLS for a set of pages in one JMX call (to increase network performance). ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSpecificPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.5.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = ConsoleRuntimeMBean.class.getMethod("getObjectNameContext", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("objectName", "the JMX object name of a WLS mbean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if objectName is null or empty")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets a context for a WLS mbean.  This method does not check whether the mbean exists. The results of this method are usually passed into getDefaultPageURL or getSpecificPageURL. ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDefaultPageURL"), BeanInfoHelper.encodeEntities("#getSpecificPageURL")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.1.0");
         }
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

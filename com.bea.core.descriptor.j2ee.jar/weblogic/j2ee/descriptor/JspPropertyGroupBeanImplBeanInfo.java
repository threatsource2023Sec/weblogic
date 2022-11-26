package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class JspPropertyGroupBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JspPropertyGroupBean.class;

   public JspPropertyGroupBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JspPropertyGroupBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.JspPropertyGroupBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.JspPropertyGroupBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Buffer")) {
         getterName = "getBuffer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBuffer";
         }

         currentResult = new PropertyDescriptor("Buffer", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("Buffer", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultContentType")) {
         getterName = "getDefaultContentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultContentType";
         }

         currentResult = new PropertyDescriptor("DefaultContentType", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("DefaultContentType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Descriptions")) {
         getterName = "getDescriptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescriptions";
         }

         currentResult = new PropertyDescriptor("Descriptions", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisplayNames")) {
         getterName = "getDisplayNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisplayNames";
         }

         currentResult = new PropertyDescriptor("DisplayNames", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("DisplayNames", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Icons")) {
         getterName = "getIcons";
         setterName = null;
         currentResult = new PropertyDescriptor("Icons", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("Icons", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createIcon");
         currentResult.setValue("destroyer", "destroyIcon");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncludeCodas")) {
         getterName = "getIncludeCodas";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncludeCodas";
         }

         currentResult = new PropertyDescriptor("IncludeCodas", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("IncludeCodas", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncludePreludes")) {
         getterName = "getIncludePreludes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncludePreludes";
         }

         currentResult = new PropertyDescriptor("IncludePreludes", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("IncludePreludes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PageEncoding")) {
         getterName = "getPageEncoding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPageEncoding";
         }

         currentResult = new PropertyDescriptor("PageEncoding", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("PageEncoding", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UrlPatterns")) {
         getterName = "getUrlPatterns";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUrlPatterns";
         }

         currentResult = new PropertyDescriptor("UrlPatterns", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("UrlPatterns", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeferredSyntaxAllowedAsLiteral")) {
         getterName = "isDeferredSyntaxAllowedAsLiteral";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeferredSyntaxAllowedAsLiteral";
         }

         currentResult = new PropertyDescriptor("DeferredSyntaxAllowedAsLiteral", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("DeferredSyntaxAllowedAsLiteral", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ElIgnored")) {
         getterName = "isElIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setElIgnored";
         }

         currentResult = new PropertyDescriptor("ElIgnored", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("ElIgnored", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorOnUndeclaredNamespace")) {
         getterName = "isErrorOnUndeclaredNamespace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setErrorOnUndeclaredNamespace";
         }

         currentResult = new PropertyDescriptor("ErrorOnUndeclaredNamespace", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("ErrorOnUndeclaredNamespace", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IsXml")) {
         getterName = "isIsXml";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIsXml";
         }

         currentResult = new PropertyDescriptor("IsXml", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("IsXml", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ScriptingInvalid")) {
         getterName = "isScriptingInvalid";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setScriptingInvalid";
         }

         currentResult = new PropertyDescriptor("ScriptingInvalid", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("ScriptingInvalid", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimDirectiveWhitespaces")) {
         getterName = "isTrimDirectiveWhitespaces";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimDirectiveWhitespaces";
         }

         currentResult = new PropertyDescriptor("TrimDirectiveWhitespaces", JspPropertyGroupBean.class, getterName, setterName);
         descriptors.put("TrimDirectiveWhitespaces", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("keyComponent", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JspPropertyGroupBean.class.getMethod("createIcon");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icons");
      }

      mth = JspPropertyGroupBean.class.getMethod("destroyIcon", IconBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icons");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JspPropertyGroupBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = JspPropertyGroupBean.class.getMethod("removeDescription", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = JspPropertyGroupBean.class.getMethod("addDisplayName", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisplayNames");
      }

      mth = JspPropertyGroupBean.class.getMethod("removeDisplayName", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisplayNames");
      }

      mth = JspPropertyGroupBean.class.getMethod("addUrlPattern", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "UrlPatterns");
      }

      mth = JspPropertyGroupBean.class.getMethod("removeUrlPattern", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "UrlPatterns");
      }

      mth = JspPropertyGroupBean.class.getMethod("addIncludePrelude", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "IncludePreludes");
      }

      mth = JspPropertyGroupBean.class.getMethod("removeIncludePrelude", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "IncludePreludes");
      }

      mth = JspPropertyGroupBean.class.getMethod("addIncludeCoda", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "IncludeCodas");
      }

      mth = JspPropertyGroupBean.class.getMethod("removeIncludeCoda", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "IncludeCodas");
      }

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

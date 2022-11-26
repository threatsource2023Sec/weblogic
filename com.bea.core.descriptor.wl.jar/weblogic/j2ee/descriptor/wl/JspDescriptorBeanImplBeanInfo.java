package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class JspDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JspDescriptorBean.class;

   public JspDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JspDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JspDescriptorBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JspDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompilerSourceVM")) {
         getterName = "getCompilerSourceVM";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompilerSourceVM";
         }

         currentResult = new PropertyDescriptor("CompilerSourceVM", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("CompilerSourceVM", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompilerTargetVM")) {
         getterName = "getCompilerTargetVM";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompilerTargetVM";
         }

         currentResult = new PropertyDescriptor("CompilerTargetVM", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("CompilerTargetVM", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DefaultFileName")) {
         getterName = "getDefaultFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultFileName";
         }

         currentResult = new PropertyDescriptor("DefaultFileName", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("DefaultFileName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Encoding")) {
         getterName = "getEncoding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncoding";
         }

         currentResult = new PropertyDescriptor("Encoding", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Encoding", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExpressionInterceptor")) {
         getterName = "getExpressionInterceptor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpressionInterceptor";
         }

         currentResult = new PropertyDescriptor("ExpressionInterceptor", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("ExpressionInterceptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PackagePrefix")) {
         getterName = "getPackagePrefix";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPackagePrefix";
         }

         currentResult = new PropertyDescriptor("PackagePrefix", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("PackagePrefix", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PageCheckSeconds")) {
         getterName = "getPageCheckSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPageCheckSeconds";
         }

         currentResult = new PropertyDescriptor("PageCheckSeconds", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("PageCheckSeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restProductionModeDefault", new Integer(-1));
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceProviderClass")) {
         getterName = "getResourceProviderClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceProviderClass";
         }

         currentResult = new PropertyDescriptor("ResourceProviderClass", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("ResourceProviderClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuperClass")) {
         getterName = "getSuperClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSuperClass";
         }

         currentResult = new PropertyDescriptor("SuperClass", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("SuperClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkingDir")) {
         getterName = "getWorkingDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkingDir";
         }

         currentResult = new PropertyDescriptor("WorkingDir", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("WorkingDir", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BackwardCompatible")) {
         getterName = "isBackwardCompatible";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBackwardCompatible";
         }

         currentResult = new PropertyDescriptor("BackwardCompatible", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("BackwardCompatible", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompressHtmlTemplate")) {
         getterName = "isCompressHtmlTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompressHtmlTemplate";
         }

         currentResult = new PropertyDescriptor("CompressHtmlTemplate", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("CompressHtmlTemplate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Debug")) {
         getterName = "isDebug";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebug";
         }

         currentResult = new PropertyDescriptor("Debug", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Debug", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EL22BackwardCompatible")) {
         getterName = "isEL22BackwardCompatible";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEL22BackwardCompatible";
         }

         currentResult = new PropertyDescriptor("EL22BackwardCompatible", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("EL22BackwardCompatible", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ExactMapping")) {
         getterName = "isExactMapping";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExactMapping";
         }

         currentResult = new PropertyDescriptor("ExactMapping", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("ExactMapping", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Keepgenerated")) {
         getterName = "isKeepgenerated";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepgenerated";
         }

         currentResult = new PropertyDescriptor("Keepgenerated", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Keepgenerated", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OptimizeJavaExpression")) {
         getterName = "isOptimizeJavaExpression";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimizeJavaExpression";
         }

         currentResult = new PropertyDescriptor("OptimizeJavaExpression", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("OptimizeJavaExpression", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Precompile")) {
         getterName = "isPrecompile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrecompile";
         }

         currentResult = new PropertyDescriptor("Precompile", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Precompile", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrecompileContinue")) {
         getterName = "isPrecompileContinue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrecompileContinue";
         }

         currentResult = new PropertyDescriptor("PrecompileContinue", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("PrecompileContinue", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrintNulls")) {
         getterName = "isPrintNulls";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrintNulls";
         }

         currentResult = new PropertyDescriptor("PrintNulls", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("PrintNulls", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RtexprvalueJspParamName")) {
         getterName = "isRtexprvalueJspParamName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRtexprvalueJspParamName";
         }

         currentResult = new PropertyDescriptor("RtexprvalueJspParamName", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("RtexprvalueJspParamName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StrictJspDocumentValidation")) {
         getterName = "isStrictJspDocumentValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStrictJspDocumentValidation";
         }

         currentResult = new PropertyDescriptor("StrictJspDocumentValidation", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("StrictJspDocumentValidation", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StrictStaleCheck")) {
         getterName = "isStrictStaleCheck";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStrictStaleCheck";
         }

         currentResult = new PropertyDescriptor("StrictStaleCheck", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("StrictStaleCheck", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Verbose")) {
         getterName = "isVerbose";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerbose";
         }

         currentResult = new PropertyDescriptor("Verbose", JspDescriptorBean.class, getterName, setterName);
         descriptors.put("Verbose", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restProductionModeDefault", new Boolean(false));
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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

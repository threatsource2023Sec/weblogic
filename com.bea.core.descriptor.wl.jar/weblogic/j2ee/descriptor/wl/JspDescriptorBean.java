package weblogic.j2ee.descriptor.wl;

public interface JspDescriptorBean {
   boolean isKeepgenerated();

   void setKeepgenerated(boolean var1);

   String getPackagePrefix();

   void setPackagePrefix(String var1);

   String getSuperClass();

   void setSuperClass(String var1);

   int getPageCheckSeconds();

   void setPageCheckSeconds(int var1);

   boolean isPageCheckSecondsSet();

   boolean isPrecompile();

   void setPrecompile(boolean var1);

   boolean isPrecompileContinue();

   void setPrecompileContinue(boolean var1);

   boolean isVerbose();

   void setVerbose(boolean var1);

   String getWorkingDir();

   void setWorkingDir(String var1);

   boolean isPrintNulls();

   void setPrintNulls(boolean var1);

   boolean isBackwardCompatible();

   void setBackwardCompatible(boolean var1);

   String getEncoding();

   void setEncoding(String var1);

   boolean isExactMapping();

   void setExactMapping(boolean var1);

   String getDefaultFileName();

   void setDefaultFileName(String var1);

   boolean isRtexprvalueJspParamName();

   void setRtexprvalueJspParamName(boolean var1);

   boolean isDebug();

   void setDebug(boolean var1);

   String getId();

   void setId(String var1);

   boolean isCompressHtmlTemplate();

   void setCompressHtmlTemplate(boolean var1);

   boolean isOptimizeJavaExpression();

   void setOptimizeJavaExpression(boolean var1);

   String getResourceProviderClass();

   void setResourceProviderClass(String var1);

   boolean isStrictStaleCheck();

   void setStrictStaleCheck(boolean var1);

   boolean isStrictJspDocumentValidation();

   void setStrictJspDocumentValidation(boolean var1);

   String getExpressionInterceptor();

   void setExpressionInterceptor(String var1);

   boolean isEL22BackwardCompatible();

   void setEL22BackwardCompatible(boolean var1);

   String getCompilerSourceVM();

   void setCompilerSourceVM(String var1);

   String getCompilerTargetVM();

   void setCompilerTargetVM(String var1);
}

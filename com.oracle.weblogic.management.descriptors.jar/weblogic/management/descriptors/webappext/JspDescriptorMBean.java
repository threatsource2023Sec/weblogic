package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface JspDescriptorMBean extends WebElementMBean {
   void setDefaultFileName(String var1);

   String getDefaultFileName();

   void setCompileCommand(String var1);

   String getCompileCommand();

   void setCompilerClass(String var1);

   String getCompilerClass();

   void setCompileFlags(String var1);

   String getCompileFlags();

   void setWorkingDir(String var1);

   String getWorkingDir();

   void setVerbose(boolean var1);

   boolean isVerbose();

   void setKeepgenerated(boolean var1);

   boolean isKeepgenerated();

   void setPageCheckSeconds(int var1);

   int getPageCheckSeconds();

   void setEncoding(String var1);

   String getEncoding();

   void setPackagePrefix(String var1);

   String getPackagePrefix();

   void setNoTryBlocks(boolean var1);

   boolean isNoTryBlocks();

   void setPrecompile(boolean var1);

   boolean isPrecompile();

   void setPrecompileContinue(boolean var1);

   boolean isPrecompileContinue();

   void setCompilerSupportsEncoding(boolean var1);

   boolean getCompilerSupportsEncoding();

   void setSuperclass(String var1);

   String getSuperclass();

   void setExactMapping(boolean var1);

   boolean isExactMapping();

   void setDebugEnabled(boolean var1);

   boolean isDebugEnabled();

   void setBackwardCompatible(boolean var1);

   boolean isBackwardCompatible();

   void setPrintNulls(boolean var1);

   boolean getPrintNulls();

   void setJspServlet(String var1);

   String getJspServlet();

   void setJspPrecompiler(String var1);

   String getJspPrecompiler();
}

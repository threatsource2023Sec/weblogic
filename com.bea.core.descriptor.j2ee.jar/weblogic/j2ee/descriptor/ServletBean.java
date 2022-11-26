package weblogic.j2ee.descriptor;

public interface ServletBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getServletName();

   void setServletName(String var1);

   String getServletClass();

   void setServletClass(String var1);

   boolean isServletClassSet();

   String getJspFile();

   void setJspFile(String var1);

   ParamValueBean[] getInitParams();

   ParamValueBean createInitParam();

   void destroyInitParam(ParamValueBean var1);

   ParamValueBean lookupInitParam(String var1);

   ParamValueBean createInitParam(String var1);

   String getLoadOnStartup();

   void setLoadOnStartup(String var1);

   boolean isLoadOnStartupSet();

   RunAsBean getRunAs();

   RunAsBean createRunAs();

   void destroyRunAs(RunAsBean var1);

   boolean isRunAsSet();

   SecurityRoleRefBean[] getSecurityRoleRefs();

   SecurityRoleRefBean createSecurityRoleRef();

   void destroySecurityRoleRef(SecurityRoleRefBean var1);

   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isAsyncSupported();

   void setAsyncSupported(boolean var1);

   boolean isAsyncSupportedSet();

   MultipartConfigBean getMultipartConfig();

   MultipartConfigBean createMultipartConfig();

   String getId();

   void setId(String var1);
}

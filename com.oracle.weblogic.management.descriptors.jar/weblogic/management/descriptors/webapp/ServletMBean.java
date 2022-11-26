package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webappext.ServletDescriptorMBean;

public interface ServletMBean extends WebElementMBean {
   UIMBean getUIData();

   void setUIData(UIMBean var1);

   String getServletName();

   void setServletName(String var1);

   String getServletCode();

   void setServletCode(String var1);

   String getServletClass();

   void setServletClass(String var1);

   String getJspFile();

   void setJspFile(String var1);

   ParameterMBean[] getInitParams();

   void setInitParams(ParameterMBean[] var1);

   void addInitParam(ParameterMBean var1);

   void removeInitParam(ParameterMBean var1);

   int getLoadOnStartup();

   void setLoadOnStartup(int var1);

   SecurityRoleRefMBean[] getSecurityRoleRefs();

   void setSecurityRoleRefs(SecurityRoleRefMBean[] var1);

   void addSecurityRoleRef(SecurityRoleRefMBean var1);

   void removeSecurityRoleRef(SecurityRoleRefMBean var1);

   void setServletDescriptor(ServletDescriptorMBean var1);

   ServletDescriptorMBean getServletDescriptor();

   RunAsMBean getRunAs();

   void setRunAs(RunAsMBean var1);

   /** @deprecated */
   @Deprecated
   String getInitAs();

   /** @deprecated */
   @Deprecated
   void setInitAs(String var1);

   /** @deprecated */
   @Deprecated
   String getDestroyAs();

   /** @deprecated */
   @Deprecated
   void setDestroyAs(String var1);
}

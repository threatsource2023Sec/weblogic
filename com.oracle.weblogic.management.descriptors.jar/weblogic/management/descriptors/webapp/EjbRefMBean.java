package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface EjbRefMBean extends WebElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBRefName();

   void setEJBRefName(String var1);

   String getEJBRefType();

   void setEJBRefType(String var1);

   String getHomeInterfaceName();

   void setHomeInterfaceName(String var1);

   String getRemoteInterfaceName();

   void setRemoteInterfaceName(String var1);

   String getEJBLinkName();

   void setEJBLinkName(String var1);

   String getRunAs();

   void setRunAs(String var1);

   boolean isLocalLink();
}

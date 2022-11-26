package weblogic.ejb.spi;

import java.util.Collection;

public interface DeploymentInfo {
   Collection getBeanInfos();

   BeanInfo getBeanInfo(String var1);

   Collection getSessionBeanInfos();

   String getSecurityRealmName();

   String getModuleName();
}

package weblogic.servlet.spi;

import weblogic.management.DeploymentException;

public interface WlsApplicationSecurity extends ApplicationSecurity {
   void deployPolicy(String var1, String var2, String[] var3) throws DeploymentException;

   boolean isCustomRolesEnabled();
}

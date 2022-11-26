package weblogic.servlet.spi;

import javax.servlet.http.HttpServletRequest;
import weblogic.management.DeploymentException;
import weblogic.security.jacc.PolicyContextHandlerData;

public interface JaccApplicationSecurity extends ApplicationSecurity {
   void deployRoleLink(String var1, String var2, String var3) throws DeploymentException;

   void deployRole(String var1, String var2, String var3) throws DeploymentException;

   PolicyContextHandlerData createContextHandlerData(HttpServletRequest var1);

   boolean checkTransport(String var1, String var2);

   String getContextID();
}

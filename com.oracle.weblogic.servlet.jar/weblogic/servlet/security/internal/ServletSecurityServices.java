package weblogic.servlet.security.internal;

import java.security.Principal;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.servlet.spi.SubjectHandle;

public interface ServletSecurityServices {
   ApplicationServices createApplicationSecurity(String var1, AppDeploymentMBean var2, String var3);

   String getDefaultRealmName();

   boolean isCompatibilitySecMode(int var1);

   boolean isApplicationSecMode(int var1);

   boolean isExternallyDefinedSecMode(int var1);

   boolean isJACCEnabled();

   void addToPrivateCredentials(SubjectHandle var1, Object var2);

   ServletCallbackHandler createCallbackHandler(String var1, Object var2, HttpServletRequest var3, HttpServletResponse var4);

   PolicyContextHandlerData createContextHandlerData(HttpServletRequest var1);

   Principal[] getPrincipals(SubjectHandle var1);

   Subject toSubject(SubjectHandle var1);

   SubjectHandle toSubjectHandle(Subject var1);

   public interface ApplicationServices {
      int getRoleMappingBehavior();

      String getSecurityModelType();

      boolean isFullDelegation();

      void destroyServletAuthenticationFilters(Filter[] var1);

      Filter[] getServletAuthenticationFilters(ServletContext var1) throws DeploymentException;

      Map getAssertionsEncodingMap();

      Map[] getAssertionsEncodingPrecedence();

      boolean doesTokenTypeRequireBase64Decoding(String var1);

      boolean doesTokenRequireBase64Decoding(Object var1);

      void startDeployment() throws DeploymentException;

      void endRoleAndPolicyDeployments() throws DeploymentException;

      void deployRole(String var1, String[] var2, String var3, String var4) throws DeploymentException;

      void undeployAllPolicies() throws DeploymentException;

      void undeployAllRoles() throws DeploymentException;

      boolean isSubjectInRole(String var1, SubjectHandle var2, String var3, String var4, HttpServletRequest var5, HttpServletResponse var6);

      boolean hasPermission(String var1, String var2, String var3, String var4, SubjectHandle var5, HttpServletRequest var6, HttpServletResponse var7);

      void deployUncheckedPolicy(String var1, String var2, String var3, String var4) throws DeploymentException;

      void deployExcludedPolicy(String var1, String var2, String var3, String var4) throws DeploymentException;

      void deployPolicy(String var1, String var2, String[] var3, String var4, String var5) throws DeploymentException;

      SubjectHandle assertIdentity(String var1, Object var2, HttpServletRequest var3, HttpServletResponse var4) throws LoginException;

      SubjectHandle authenticate(CallbackHandler var1, HttpServletRequest var2, HttpServletResponse var3) throws LoginException;

      SubjectHandle authenticate(CallbackHandler var1) throws LoginException;

      SubjectHandle impersonateIdentity(String var1, HttpServletRequest var2, HttpServletResponse var3) throws LoginException;
   }
}

package weblogic.servlet.spi;

import java.security.Permission;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;

public interface ApplicationSecurity {
   SubjectHandle impersonate(String var1, String var2, HttpServletRequest var3, HttpServletResponse var4) throws LoginException;

   SubjectHandle authenticateAndSaveCredential(String var1, Object var2, String var3, HttpServletRequest var4, HttpServletResponse var5) throws LoginException;

   SubjectHandle authenticateAndSaveCredential(String var1, char[] var2, String var3) throws LoginException;

   SubjectHandle authenticate(CallbackHandler var1, String var2, HttpServletRequest var3, HttpServletResponse var4) throws LoginException;

   SubjectHandle assertIdentity(String var1, Object var2, HttpServletRequest var3, HttpServletResponse var4) throws LoginException;

   Map getAssertionsEncodingMap();

   Map[] getAssertionsEncodingPrecedence();

   boolean doesTokenTypeRequireBase64Decoding(String var1);

   boolean doesTokenRequireBase64Decoding(Object var1);

   boolean isCompatibilitySecMode();

   boolean isApplicationSecMode();

   boolean isExternallyDefinedSecMode();

   Filter[] getServletAuthenticationFilters(ServletContext var1) throws DeploymentException;

   void destroyServletAuthenticationFilters(Filter[] var1);

   boolean isFullSecurityDelegationRequired();

   void deployUncheckedPolicy(String var1, String var2) throws DeploymentException;

   void deployUncheckedPolicy(Permission var1) throws DeploymentException;

   void deployExcludedPolicy(String var1, String var2) throws DeploymentException;

   void deployRole(String var1, String[] var2) throws DeploymentException;

   void startRoleAndPolicyDeployments() throws DeploymentException;

   void endRoleAndPolicyDeployments(Map var1) throws DeploymentException;

   void unregisterPolicies() throws DeploymentException;

   void unregisterRoles() throws DeploymentException;

   boolean isSubjectInRole(SubjectHandle var1, String var2, HttpServletRequest var3, HttpServletResponse var4, String var5);

   boolean hasPermission(SubjectHandle var1, HttpServletRequest var2, HttpServletResponse var3, String var4);

   boolean isRequestSigned(HttpServletRequest var1);

   void populateSubject(Subject var1, SubjectHandle var2);

   SubjectHandle toSubjectHandle(Subject var1);
}

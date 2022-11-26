package weblogic.servlet.security.internal;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WlsApplicationSecurity;

public class WLSSecurity extends AbstractAppSecurity implements WlsApplicationSecurity {
   private final boolean isFullDelegation;
   private final boolean customRolesEnabled;
   private final String applicationId;
   private final String contextPath;

   public WLSSecurity(ServletSecurityServices securityServices, AppDeploymentMBean mbean, String contextPath, String applicationId, String realmName) throws DeploymentException {
      super(securityServices, mbean, contextPath, realmName);
      this.contextPath = contextPath;
      this.applicationId = applicationId;
      this.customRolesEnabled = this.hasCustomRoles(this.getApplicationServices().getSecurityModelType());
      this.isFullDelegation = this.getApplicationServices().isFullDelegation();
   }

   private boolean hasCustomRoles(String model) {
      return model.equals("CustomRoles") || model.equals("CustomRolesAndPolicies");
   }

   public boolean isFullSecurityDelegationRequired() {
      return this.isFullDelegation;
   }

   public boolean isCustomRolesEnabled() {
      return this.customRolesEnabled;
   }

   public void deployUncheckedPolicy(String resourceId, String method) throws DeploymentException {
      this.getApplicationServices().deployUncheckedPolicy(resourceId, method, this.applicationId, this.contextPath);
   }

   public void deployExcludedPolicy(String resourceId, String method) throws DeploymentException {
      this.getApplicationServices().deployExcludedPolicy(resourceId, method, this.applicationId, this.contextPath);
   }

   public void deployPolicy(String resourceId, String method, String[] roles) throws DeploymentException {
      this.getApplicationServices().deployPolicy(resourceId, method, roles, this.applicationId, this.contextPath);
   }

   public void deployRole(String roleName, String[] mappings) throws DeploymentException {
      this.getApplicationServices().deployRole(roleName, mappings, this.applicationId, this.contextPath);
   }

   public void startRoleAndPolicyDeployments() throws DeploymentException {
      this.getApplicationServices().startDeployment();
   }

   public void endRoleAndPolicyDeployments(Map roleMappings) throws DeploymentException {
      this.getApplicationServices().endRoleAndPolicyDeployments();
   }

   public void unregisterPolicies() throws DeploymentException {
      this.getApplicationServices().undeployAllPolicies();
   }

   public void unregisterRoles() throws DeploymentException {
      this.getApplicationServices().undeployAllRoles();
   }

   public boolean isSubjectInRole(SubjectHandle subject, String roleName, HttpServletRequest request, HttpServletResponse response, String servletName) {
      return this.getApplicationServices().isSubjectInRole(roleName, subject, this.applicationId, this.contextPath, request, response);
   }

   public boolean hasPermission(SubjectHandle subject, HttpServletRequest request, HttpServletResponse response, String relativeRequestPath) {
      return this.getApplicationServices().hasPermission(request.getMethod(), relativeRequestPath, this.applicationId, this.contextPath, subject, request, response);
   }
}

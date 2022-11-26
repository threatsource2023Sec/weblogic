package weblogic.servlet.internal.dd.compliance;

import java.util.HashSet;
import java.util.Set;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.UserDataConstraintBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;
import weblogic.utils.ErrorCollectionException;

public class SecurityConstraintComplianceChecker extends BaseComplianceChecker {
   private Set resourceNames;

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      WebAppBean web = info.getWebAppBean();
      SecurityConstraintBean[] constraints = web.getSecurityConstraints();

      for(int i = 0; i < constraints.length; ++i) {
         this.checkSecurityConstraint(constraints[i], info);
      }

   }

   private void checkSecurityConstraint(SecurityConstraintBean constraint, DeploymentInfo info) throws ErrorCollectionException {
      String[] displayNames = constraint.getDisplayNames();
      String displayName = null;
      if (displayNames != null && displayNames.length > 0) {
         displayName = displayNames[0];
      }

      WebResourceCollectionBean[] collection = constraint.getWebResourceCollections();
      if (collection != null) {
         for(int i = 0; i < collection.length; ++i) {
            this.checkResourceCollection(collection[i]);
         }
      }

      UserDataConstraintBean data = constraint.getUserDataConstraint();
      if (data != null) {
         String transport = data.getTransportGuarantee();
         if (!isTransportGuaranteeValid(transport)) {
            this.addDescriptorError(this.fmt.INVALID_TRANSPORT_GUARANTEE(transport));
         }
      }

      AuthConstraintBean auth = constraint.getAuthConstraint();
      if (auth != null) {
         SecurityRoleBean[] sr = info.getWebAppBean().getSecurityRoles();
         String[] roleNames = null;
         if (sr != null) {
            roleNames = new String[sr.length];

            for(int j = 0; j < sr.length; ++j) {
               roleNames[j] = sr[j].getRoleName();
            }
         }

         String[] roles = auth.getRoleNames();
         if (roles != null) {
            for(int i = 0; i < roles.length; ++i) {
               String roleName = roles[i];
               if (roleName != null && "*".equals(roleName)) {
                  this.update("info : Since '*' is specified, all roles will be given access to the resource " + (displayName != null ? ": " + displayName : ""));
               } else {
                  boolean foundRole = false;

                  for(int j = 0; j < roleNames.length; ++j) {
                     if (roleNames[j].equals(roleName)) {
                        foundRole = true;
                        break;
                     }
                  }

                  if (!foundRole) {
                     this.addDescriptorError(this.fmt.NO_SECURITY_ROLE_FOR_AUTH(roleName));
                  }
               }
            }
         }
      }

      this.checkForExceptions();
   }

   private void checkResourceCollection(WebResourceCollectionBean collection) throws ErrorCollectionException {
      String resourceName = collection.getWebResourceName();
      String[] patterns = collection.getUrlPatterns();
      if (!this.addResourceName(resourceName)) {
         this.addDescriptorError(this.fmt.DUPLICATE_RESOURCE_NAME(resourceName));
      }

      if (patterns != null) {
         for(int i = 0; i < patterns.length; ++i) {
            this.validateURLPattern(resourceName, patterns[i]);
         }
      }

      this.checkForExceptions();
   }

   private boolean addResourceName(String resourceName) {
      if (this.resourceNames == null) {
         this.resourceNames = new HashSet();
      }

      return this.resourceNames.add(resourceName);
   }

   private static boolean isTransportGuaranteeValid(String val) {
      return "NONE".equals(val) || "INTEGRAL".equals(val) || "CONFIDENTIAL".equals(val);
   }

   private void validateURLPattern(String resourceName, String pattern) {
      if (pattern == null || pattern.length() == 0) {
         this.addDescriptorError(this.fmt.ILLEGAL_URL_PATTERN(resourceName));
      }

   }
}

package weblogic.security.service;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Properties;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.GroupReaderMBean;
import weblogic.management.security.authentication.UserReaderMBean;
import weblogic.management.security.authorization.RoleListerMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public class DeploymentUtils implements DeploymentValidator {
   private boolean supportsUserExists = false;
   private boolean supportsGroupExists = false;
   private boolean supportsRoleExists = false;
   private boolean allSupportUserExists = true;
   private boolean allSupportGroupExists = true;
   private boolean allSupportRoleExists = true;
   private UserReaderMBean[] userReaderMBeans;
   private GroupReaderMBean[] groupReaderMBeans;
   private RoleListerMBean[] roleListerMBeans;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DeploymentUtils(String realmName, AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      this.initialize(realmName);
   }

   public DeploymentValidationResult doesPrincipalExist(String principalName) {
      if (!this.supportsUserExists && !this.supportsGroupExists) {
         return DeploymentValidationResult.UNKNOWN;
      } else {
         DeploymentValidationResult result = this.doesUserExist(principalName);
         return result == DeploymentValidationResult.EXISTS ? result : this.doesGroupExist(principalName);
      }
   }

   public DeploymentValidationResult doesRoleExist(String roleName, Resource resource) {
      if (!this.supportsRoleExists) {
         return DeploymentValidationResult.UNKNOWN;
      } else {
         String resId = null;
         if (resource != null) {
            resId = resource.toString();
         }

         return this.doesRoleExist(roleName, resId);
      }
   }

   public DeploymentValidationResult doesUserExist(String userName) {
      if (!this.supportsUserExists) {
         return DeploymentValidationResult.UNKNOWN;
      } else {
         try {
            for(int i = 0; i < this.userReaderMBeans.length; ++i) {
               boolean found = this.userReaderMBeans[i].userExists(userName);
               if (found) {
                  return DeploymentValidationResult.EXISTS;
               }
            }
         } catch (Exception var4) {
            this.logDeploymentValidationProblem("userExists() - " + var4.toString());
            return DeploymentValidationResult.UNKNOWN;
         }

         return !this.allSupportUserExists ? DeploymentValidationResult.UNKNOWN : DeploymentValidationResult.NOT_EXISTS;
      }
   }

   public DeploymentValidationResult doesGroupExist(String groupName) {
      if (!this.supportsGroupExists) {
         return DeploymentValidationResult.UNKNOWN;
      } else {
         try {
            for(int i = 0; i < this.groupReaderMBeans.length; ++i) {
               boolean found = this.groupReaderMBeans[i].groupExists(groupName);
               if (found) {
                  return DeploymentValidationResult.EXISTS;
               }
            }
         } catch (Exception var4) {
            this.logDeploymentValidationProblem("groupExists() - " + var4.toString());
            return DeploymentValidationResult.UNKNOWN;
         }

         return !this.allSupportGroupExists ? DeploymentValidationResult.UNKNOWN : DeploymentValidationResult.NOT_EXISTS;
      }
   }

   public DeploymentValidationResult doesRoleExist(String roleName, String resourceId) {
      if (!this.supportsRoleExists) {
         return DeploymentValidationResult.UNKNOWN;
      } else {
         try {
            for(int i = 0; i < this.roleListerMBeans.length; ++i) {
               Properties props = this.roleListerMBeans[i].getRoleScopedByResource(resourceId, roleName);
               if (props != null) {
                  return DeploymentValidationResult.EXISTS;
               }
            }
         } catch (Exception var5) {
            this.logDeploymentValidationProblem("getRoleScopedByResource() - " + var5.toString());
            return DeploymentValidationResult.UNKNOWN;
         }

         return !this.allSupportRoleExists ? DeploymentValidationResult.UNKNOWN : DeploymentValidationResult.NOT_EXISTS;
      }
   }

   private void initialize(String realmName) {
      RealmMBean realmMBean = this.getRealm(realmName);
      if (realmMBean == null) {
         this.logDeploymentValidationProblem("No Realm Found");
      } else {
         ProviderMBean[] atnMBeans = realmMBean.getAuthenticationProviders();
         if (atnMBeans != null && atnMBeans.length != 0) {
            ProviderMBean[] roleMBeans = realmMBean.getRoleMappers();
            if (roleMBeans != null && roleMBeans.length != 0) {
               ProviderMBean[] atnOnlyMBeans = this.getAuthenticators(atnMBeans);
               if (atnOnlyMBeans != null && atnOnlyMBeans.length != 0) {
                  this.supportsUserExists = this.determineUserReader(atnOnlyMBeans);
                  this.supportsGroupExists = this.determineGroupReader(atnOnlyMBeans);
                  this.supportsRoleExists = this.determineRoleLister(roleMBeans);
               } else {
                  this.logDeploymentValidationProblem("No Authenticator MBeans");
               }
            } else {
               this.logDeploymentValidationProblem("No Role Mapping Provider MBeans");
            }
         } else {
            this.logDeploymentValidationProblem("No Authentication Provider MBeans");
         }
      }
   }

   private RealmMBean getRealm(String realmName) {
      try {
         SecurityConfigurationMBean secConfig = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration();
         RealmMBean realmMBean = null;
         if (realmName != null && realmName.length() != 0 && !realmName.equals(SecurityServiceManager.getContextSensitiveRealmName())) {
            realmMBean = secConfig.lookupRealm(realmName);
         } else {
            realmMBean = secConfig.getDefaultRealm();
         }

         return realmMBean;
      } catch (Exception var4) {
         this.logDeploymentValidationProblem("Unable to obtain RealmMBean - " + var4.toString());
         return null;
      }
   }

   private ProviderMBean[] getAuthenticators(ProviderMBean[] atnMBeans) {
      ArrayList providers = new ArrayList();

      for(int i = 0; i < atnMBeans.length; ++i) {
         if (atnMBeans[i] instanceof AuthenticatorMBean) {
            providers.add(atnMBeans[i]);
         }
      }

      if (providers.isEmpty()) {
         return null;
      } else {
         return (ProviderMBean[])((ProviderMBean[])providers.toArray(new ProviderMBean[providers.size()]));
      }
   }

   private boolean determineUserReader(ProviderMBean[] atnMBeans) {
      ArrayList providers = new ArrayList();

      for(int i = 0; i < atnMBeans.length; ++i) {
         if (atnMBeans[i] instanceof UserReaderMBean) {
            providers.add(atnMBeans[i]);
         } else {
            this.allSupportUserExists = false;
         }
      }

      boolean found = !providers.isEmpty();
      if (found) {
         this.userReaderMBeans = (UserReaderMBean[])((UserReaderMBean[])providers.toArray(new UserReaderMBean[providers.size()]));
      }

      return found;
   }

   private boolean determineGroupReader(ProviderMBean[] atnMBeans) {
      ArrayList providers = new ArrayList();

      for(int i = 0; i < atnMBeans.length; ++i) {
         if (atnMBeans[i] instanceof GroupReaderMBean) {
            providers.add(atnMBeans[i]);
         } else {
            this.allSupportGroupExists = false;
         }
      }

      boolean found = !providers.isEmpty();
      if (found) {
         this.groupReaderMBeans = (GroupReaderMBean[])((GroupReaderMBean[])providers.toArray(new GroupReaderMBean[providers.size()]));
      }

      return found;
   }

   private boolean determineRoleLister(ProviderMBean[] roleMBeans) {
      ArrayList providers = new ArrayList();

      for(int i = 0; i < roleMBeans.length; ++i) {
         if (roleMBeans[i] instanceof RoleListerMBean) {
            providers.add(roleMBeans[i]);
         } else {
            this.allSupportRoleExists = false;
         }
      }

      boolean found = !providers.isEmpty();
      if (found) {
         this.roleListerMBeans = (RoleListerMBean[])((RoleListerMBean[])providers.toArray(new RoleListerMBean[providers.size()]));
      }

      return found;
   }

   private void logDeploymentValidationProblem(String msg) {
      SecurityLogger.logDeploymentValidationProblem(msg);
   }
}

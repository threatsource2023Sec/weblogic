package weblogic.application.internal.flow;

import java.security.Policy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyContextException;
import weblogic.application.internal.FlowContext;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.wl.ApplicationSecurityRoleAssignmentBean;
import weblogic.management.DeploymentException;
import weblogic.security.jacc.RoleMapper;
import weblogic.security.jacc.RoleMapperFactory;

public final class JACCPolicyConfigurationFlow extends BaseFlow {
   private final boolean useJACC;

   public JACCPolicyConfigurationFlow(FlowContext appCtx) {
      super(appCtx);
      this.useJACC = appCtx.getSecurityProvider().isJACCEnabled();
   }

   public void prepare() throws DeploymentException {
      if (this.useJACC) {
         this.handleRoleMapping();
      }
   }

   public void activate() throws DeploymentException {
      if (this.useJACC) {
         PolicyConfiguration[] configurations = this.appCtx.getJACCPolicyConfigurations();
         this.linkPolicyConfigurations(configurations);
         this.commitPolicyConfigurations(configurations);
         this.refreshPolicy(configurations);
      }
   }

   public void unprepare() {
      if (this.useJACC) {
         this.refreshPolicy(this.appCtx.getJACCPolicyConfigurations());
      }
   }

   private void handleRoleMapping() throws DeploymentException {
      Map roleMappings = this.processRoleMappings();
      if (roleMappings != null) {
         RoleMapperFactory rmf;
         try {
            rmf = RoleMapperFactory.getRoleMapperFactory();
         } catch (ClassNotFoundException var4) {
            throw new DeploymentException(var4);
         } catch (PolicyContextException var5) {
            throw new DeploymentException(var5);
         }

         RoleMapper roleMapper = rmf.getRoleMapper(this.appCtx.getApplicationId(), false);
         roleMapper.addAppRolesToPrincipalMap(roleMappings);
      }

   }

   private void refreshPolicy(PolicyConfiguration[] pc) {
      if (pc.length > 0) {
         Policy.getPolicy().refresh();
      }

   }

   private void linkPolicyConfigurations(PolicyConfiguration[] pc) throws DeploymentException {
      if (pc.length != 1) {
         for(int i = pc.length - 1; i > 0; --i) {
            try {
               pc[i].linkConfiguration(pc[i - 1]);
            } catch (PolicyContextException var4) {
               throw new DeploymentException(var4);
            }
         }

      }
   }

   private void commitPolicyConfigurations(PolicyConfiguration[] pc) throws DeploymentException {
      for(int i = 0; i < pc.length; ++i) {
         try {
            pc[i].commit();
         } catch (PolicyContextException var4) {
            throw new DeploymentException(var4);
         }
      }

   }

   private String[] getSecurityRoleNames() {
      String[] result = null;
      ApplicationBean dd = this.appCtx.getApplicationDD();
      if (dd == null) {
         return null;
      } else {
         SecurityRoleBean[] roles = dd.getSecurityRoles();
         if (roles != null && roles.length != 0) {
            result = new String[roles.length];

            for(int i = 0; i < roles.length; ++i) {
               result[i] = roles[i].getRoleName();
            }
         }

         return result;
      }
   }

   private Map processRoleMappings() throws DeploymentException {
      String[] roles = this.getSecurityRoleNames();
      Map roleToPrincipalsMapping = this.getRoleToPrincipalsMapping();
      if (roleToPrincipalsMapping == null) {
         return null;
      } else {
         if (roles == null || roles.length == 0) {
            roles = (String[])((String[])roleToPrincipalsMapping.keySet().toArray(new String[roleToPrincipalsMapping.size()]));
         }

         if (roles != null && roles.length != 0) {
            Map rtn = new HashMap(roles.length);
            Collection rolesWithoutMapping = new HashSet();

            for(int i = 0; i < roles.length; ++i) {
               String roleName = roles[i];
               String[] principals = (String[])((String[])roleToPrincipalsMapping.get(roleName));
               if (principals == null) {
                  rolesWithoutMapping.add(roleName);
               } else {
                  rtn.put(roleName, principals);
               }
            }

            if (!rolesWithoutMapping.isEmpty()) {
               throw new DeploymentException("Cannot find a role mapping for the following roles: " + rolesWithoutMapping);
            } else {
               return rtn;
            }
         } else {
            return null;
         }
      }
   }

   private Map getRoleToPrincipalsMapping() {
      if (this.appCtx.getWLApplicationDD() == null) {
         return null;
      } else if (this.appCtx.getWLApplicationDD().getSecurity() == null) {
         return null;
      } else {
         ApplicationSecurityRoleAssignmentBean[] securityAssignments = this.appCtx.getWLApplicationDD().getSecurity().getSecurityRoleAssignments();
         if (securityAssignments != null && securityAssignments.length != 0) {
            Map rtn = new HashMap();

            for(int i = 0; i < securityAssignments.length; ++i) {
               String[] principalNames = securityAssignments[i].getPrincipalNames();
               if (principalNames != null && principalNames.length > 0) {
                  rtn.put(securityAssignments[i].getRoleName(), principalNames);
               }
            }

            return rtn;
         } else {
            return null;
         }
      }
   }
}

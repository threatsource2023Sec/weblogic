package weblogic.connector.security;

import java.net.URL;
import java.security.PermissionCollection;
import java.util.List;
import weblogic.application.utils.EarUtils;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.SecurityPermissionInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceException;
import weblogic.security.service.SupplementalPolicyObject;

public class SecurityPermissions {
   public static void unSetSecurityPermissions(AuthenticatedSubject kernelId, RAInfo raInfo) {
      URL url = raInfo.getURL();
      SecurityHelperFactory.getInstance().removePolicies(kernelId, url);
   }

   public static void setSecurityPermissions(AuthenticatedSubject kernelId, RAInfo raInfo) throws RAException {
      if (System.getSecurityManager() != null) {
         URL url = raInfo.getURL();
         PermissionCollection permissions = null;

         try {
            permissions = EarUtils.getPermissions(raInfo.getPermissionsBean());
            String[] deployCodeBases = new String[]{raInfo.getURL().getPath()};
            List securityPermissionsList = raInfo.getSecurityPermissions();
            String[] ddGrant = null;
            if (securityPermissionsList != null && securityPermissionsList.size() > 0) {
               ddGrant = new String[securityPermissionsList.size()];

               for(int i = 0; i < ddGrant.length; ++i) {
                  ddGrant[i] = ((SecurityPermissionInfo)securityPermissionsList.get(i)).getSpec();
                  if (ddGrant[i] != null && ddGrant[i].trim().length() == 0) {
                     ddGrant[i] = null;
                  }
               }
            }

            SupplementalPolicyObject.registerSEPermissions(kernelId, deployCodeBases, permissions, ddGrant, "ra.xml", "CONNECTOR", "EE_CONNECTOR");
         } catch (SecurityServiceException var8) {
            throw new RAException(var8);
         }
      }
   }
}

package weblogic.security.jaspic;

import com.bea.common.security.service.PrincipalValidationService;
import java.security.AccessController;
import java.util.Set;
import javax.security.auth.login.LoginException;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.annotation.Secure;

@Service
@Secure
public class SecurityServicesImpl implements SecurityServices {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final PrincipalAuthenticator pa = getPrincipalAuthenticatorFromCSS();
   private static final PrincipalValidationService pvs = getPrincipalValidationServiceFromCSS();

   private SecurityServicesImpl() {
   }

   private PrincipalAuthenticator getPrincipalAuthenticator() {
      return pa;
   }

   private PrincipalValidationService getPrincipalValidationService() {
      return pvs;
   }

   public AuthenticatedSubject authenticate(String username, char[] password) throws LoginException {
      SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
      return this.getPrincipalAuthenticator().authenticate(handler);
   }

   public AuthenticatedSubject impersonate(String username) throws LoginException {
      return this.getPrincipalAuthenticator().impersonateIdentity(username);
   }

   public void signPrincipals(Set principals) {
      this.getPrincipalValidationService().sign(principals);
   }

   public boolean isAdminUser(AuthenticatedSubject as) {
      return SubjectUtils.isUserAnAdministrator(as) || SubjectUtils.doesUserHaveAnyAdminRoles(as);
   }

   private static PrincipalAuthenticator getPrincipalAuthenticatorFromCSS() {
      return SecurityServiceManager.getPrincipalAuthenticator(kernelId, SecurityServiceManager.getContextSensitiveRealmName());
   }

   private static PrincipalValidationService getPrincipalValidationServiceFromCSS() {
      try {
         return SecurityServiceManager.getPrincipalValidationService(kernelId);
      } catch (Exception var1) {
         throw new IllegalStateException(var1);
      }
   }
}

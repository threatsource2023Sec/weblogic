package weblogic.rmi.client.facades;

import java.security.AccessController;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityManager;
import weblogic.security.subject.SubjectManager;

@Service
@Rank(-1)
public class RmiClientSecurityFacadeDelegateImpl implements RmiClientSecurityFacadeDelegate {
   private static final AuthenticatedSubject kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());

   public boolean doIsKernelIdentity(AuthenticatedSubject subject) {
      return subject == kernelIdentity;
   }

   public AuthenticatedSubject doGetCurrentSubject(AuthenticatedSubject kernelId) {
      return SecurityManager.getCurrentSubject(kernelId);
   }

   public boolean doIsUserAnonymous(AuthenticatedSubject subject) {
      return SubjectUtils.isUserAnonymous(subject);
   }

   public AuthenticatedSubject doGetAnonymousSubject() {
      return SubjectUtils.getAnonymousSubject();
   }

   public String doGetUsername(AuthenticatedSubject subject) {
      return SubjectUtils.getUsername(subject);
   }
}

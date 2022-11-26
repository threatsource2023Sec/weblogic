package weblogic.coherence.service.internal.security;

import com.tangosol.io.DefaultSerializer;
import com.tangosol.net.Service;
import com.tangosol.net.security.IdentityTransformer;
import com.tangosol.util.ExternalizableHelper;
import java.security.AccessController;
import javax.security.auth.Subject;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class CoherenceIdentityTransformer implements IdentityTransformer {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DefaultSerializer serializer = new DefaultSerializer();

   public Object transformIdentity(Subject subject, Service service) throws SecurityException {
      AuthenticatedSubject anon = SubjectUtils.getAnonymousSubject();
      AuthenticatedSubject current = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      return current.equals(anon) ? null : ExternalizableHelper.toBinary(SecurityServiceManager.getCurrentSubject(KERNEL_ID), this.serializer);
   }
}

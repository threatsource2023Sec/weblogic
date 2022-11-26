package weblogic.persistence;

import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.persistence.spi.PersistenceProviderResolverService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class PersistenceRegistrationService extends AbstractServerService {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean CIC_SCOPED_EM_DISABLED = Boolean.getBoolean("weblogic.persistence.DisableCICScopedEM");

   public void start() throws ServiceFailureException {
      if (!CIC_SCOPED_EM_DISABLED) {
         ComponentInvocationContextManager.getInstance(KERNEL_ID).addInvocationContextChangeListener(CICScopedEMProvider.getInstance());
      }

      PersistenceProviderResolverService pprs = new PersistenceProviderResolverService();
      pprs.postConstruct();
   }
}

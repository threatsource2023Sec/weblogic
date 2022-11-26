package weblogic.messaging.interception;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.Environment;
import weblogic.messaging.interception.interfaces.InterceptionService;
import weblogic.messaging.interception.internal.InterceptionServiceImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class MessageInterceptionService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   public static final String MessageInterceptionService_JNDIName = "weblogic.MessageInterception";
   private static InterceptionService interceptionService = InterceptionServiceImpl.getInterceptionService();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static InterceptionService getSingleton() {
      return interceptionService;
   }

   public void start() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(false);
         final Context nonReplicatedCtx = env.getInitialContext();

         try {
            SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws NamingException {
                  nonReplicatedCtx.bind("weblogic.MessageInterception", MessageInterceptionService.interceptionService);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            throw new NamingException(var4.toString());
         }
      } catch (NamingException var5) {
         throw new ServiceFailureException(MIExceptionLogger.logSetupJNDIExceptionLoggable("weblogic.MessageInterception").getMessage(), var5);
      }

      MILogger.logStartMessageInterceptionService();
   }
}

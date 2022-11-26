package weblogic.coherence.service.internal.security;

import com.tangosol.net.ClusterPermission;
import com.tangosol.net.security.Authorizer;
import com.tangosol.util.ClassHelper;
import java.security.AccessControlException;
import java.security.AccessController;
import javax.security.auth.Subject;
import weblogic.coherence.service.internal.coherenceLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class CoherenceAuthorizer implements Authorizer {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SERVICE = "service";
   private static final String CACHE = "cache";
   private static final String CACHE_PARTITION_NAME_DELIMITER = "/";
   private Subject coherenceKernel;
   private AuthorizationManager am;

   public CoherenceAuthorizer() {
      this(false);
   }

   public CoherenceAuthorizer(boolean isFrameworkEnabled) {
      this.coherenceKernel = null;
      this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.AUTHORIZE);
      if (isFrameworkEnabled) {
         try {
            Class klass = Class.forName("weblogic.cacheprovider.coherence.SecurityHelper");
            Object[] args = new Object[]{KERNEL_ID};
            this.coherenceKernel = (Subject)ClassHelper.invoke(klass, (Object)null, "getCoherenceKernel", args);
         } catch (Exception var4) {
            coherenceLogger.logErrorCoherenceInitializing(var4);
         }
      }

   }

   public Subject authorize(Subject subject, ClusterPermission permission) {
      String category = permission.getCacheName() == null ? "service" : "cache";
      String name = category.equals("service") ? permission.getServiceName() : this.getCacheNameForAuthorization(permission.getCacheName());
      if (!this.am.isAccessAllowed(SecurityServiceManager.getCurrentSubject(KERNEL_ID), this.createGridResource(category, name, permission), new ResourceIDDContextWrapper())) {
         throw new AccessControlException("Insufficient rights to perform the operation", permission);
      } else {
         return this.coherenceKernel;
      }
   }

   private Resource createGridResource(String category, String name, ClusterPermission permission) {
      try {
         Class klass = Class.forName("weblogic.cacheprovider.coherence.security.GridResource");
         Object[] args = new Object[]{permission.getClusterName(), category, name, permission.getActions()};
         return (Resource)ClassHelper.newInstance(klass, args);
      } catch (Exception var6) {
         throw new AccessControlException("Unable to create GridResource", permission);
      }
   }

   private String getCacheNameForAuthorization(String cacheName) {
      ComponentInvocationContext currentContext = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String currentPartitionName = currentContext.getPartitionName();
      return currentContext.isGlobalRuntime() ? cacheName : currentPartitionName + "/" + cacheName;
   }
}

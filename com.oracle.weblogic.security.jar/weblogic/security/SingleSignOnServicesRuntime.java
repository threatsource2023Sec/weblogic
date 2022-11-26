package weblogic.security;

import com.bea.common.security.service.SAML2PublishException;
import com.bea.common.security.service.SAML2Service;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SingleSignOnServicesRuntimeMBean;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.utils.LocatorUtilities;

public final class SingleSignOnServicesRuntime extends RuntimeMBeanDelegate implements SingleSignOnServicesRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public SingleSignOnServicesRuntime() throws ManagementException {
      super(SingleSignOnServicesRuntime.SecurityRuntimeAccessService.runtimeAccess.getServerName(), SingleSignOnServicesRuntime.SecurityRuntimeAccessService.runtimeAccess.getServerRuntime(), true, SingleSignOnServicesRuntime.SecurityRuntimeAccessService.runtimeAccess.getServer().getSingleSignOnServices());
      ((ServerRuntimeSecurityAccess)SingleSignOnServicesRuntime.SecurityRuntimeAccessService.runtimeAccess.getServerRuntime()).setSingleSignOnServicesRuntime(this);
   }

   public void publish(String fileName) throws InvalidParameterException {
      try {
         this.publish(fileName, false);
      } catch (InvalidParameterException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new InvalidParameterException(var4.getMessage(), var4);
      }
   }

   public void publish(String fileName, boolean prohibitOverwrite) throws InvalidParameterException, CreateException, AlreadyExistsException {
      try {
         SAML2Service ss = (SAML2Service)SecurityServiceManager.getSecurityService(kernelId, SingleSignOnServicesRuntime.SecurityRuntimeAccessService.runtimeAccess.getDomain().getSecurityConfiguration().getDefaultRealm().getName(), ServiceType.SAML2_SSO);
         if (ss == null) {
            throw new CreateException("SAML2Service Unavailable");
         } else {
            ss.publish(fileName, prohibitOverwrite);
         }
      } catch (IllegalArgumentException var4) {
         throw new InvalidParameterException(var4.getMessage(), var4);
      } catch (SAML2PublishException var5) {
         if (var5 instanceof SAML2PublishException.OverwriteProhibitedException) {
            throw new AlreadyExistsException(var5.getMessage(), var5);
         } else if (var5 instanceof SAML2PublishException.FileCreateException) {
            throw new InvalidParameterException(var5.getMessage(), var5);
         } else {
            throw new CreateException(var5.getMessage(), var5);
         }
      }
   }

   private static final class SecurityRuntimeAccessService {
      private static final SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
   }
}

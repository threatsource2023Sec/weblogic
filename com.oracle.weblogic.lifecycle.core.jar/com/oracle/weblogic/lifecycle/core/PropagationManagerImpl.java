package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.config.PropagationManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.security.auth.login.FailedLoginException;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.EditSessionConfigurationManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityServiceRuntimeException;
import weblogic.utils.annotation.AdminServer;
import weblogic.utils.annotation.Secure;

@Service
@Secure
@AdminServer
public class PropagationManagerImpl implements PropagationManager {
   private static final AuthenticatedSubject KERNEL_ID;
   private Logger logger = Logger.getLogger("LifeCycle");
   @Inject
   private EditSessionConfigurationManagerService editSessionConfigurationManagerService;

   private PropagationManagerImpl() {
   }

   public boolean isEnabled() {
      boolean isAdmin = ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer();
      return isAdmin;
   }

   public void propagate(File configFile) {
      if (this.isEnabled()) {
         this.propagateToManagedServersFromWLS(configFile);
      }

   }

   private EditAccess getEditMBSC() throws Exception {
      EditAccess namedEditAccess = this.editSessionConfigurationManagerService.createEditSession(this.getEditSession(), "LCM Propagation Manager");
      return namedEditAccess;
   }

   private String getEditSession() throws LifecycleException {
      return "PropagationManager" + ThreadLocalRandom.current().nextLong();
   }

   private void propagateToManagedServersFromWLS(final File source) {
      if (KERNEL_ID != null) {
         this.logger.fine("The save method was called on LifecycleDocument");

         try {
            runAsLcmUser(KERNEL_ID, new PrivilegedAction() {
               public Void run() {
                  EditAccess edit = null;

                  try {
                     edit = PropagationManagerImpl.this.getEditMBSC();
                     edit.startEdit(PropagationManagerImpl.minutesToMillis(1), PropagationManagerImpl.minutesToMillis(2));
                     PropagationManagerImpl.this.copyToPending(source, edit.getEditSessionName());
                     long activateTimeout = LifecycleUtils.getPropagationActivateTimeout();
                     edit.activateChangesAndWaitForCompletion(activateTimeout);
                  } catch (Exception var12) {
                     PropagationManagerImpl.this.logger.log(Level.WARNING, "LifecycleDocument changes propagation failed", var12);
                  } finally {
                     if (edit != null) {
                        try {
                           PropagationManagerImpl.this.editSessionConfigurationManagerService.destroyEditSession(edit);
                        } catch (Exception var11) {
                           PropagationManagerImpl.this.logger.log(Level.WARNING, "Error destroying edit session", var11);
                        }
                     }

                     return null;
                  }
               }
            });
         } catch (Exception var3) {
            if (var3 instanceof FailedLoginException) {
               this.logger.log(Level.FINE, "LifecycleDocument changes propagation failed, LCM user is not present.");
            } else {
               this.logger.log(Level.WARNING, "LifecycleDocument changes propagation failed", var3);
            }
         }

      }
   }

   private static Object runAsLcmUser(AuthenticatedSubject kernelId, PrivilegedAction action) throws Exception {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelId, SecurityServiceManager.getContextSensitiveRealmName());
      AuthenticatedSubject lcmUser = pa.impersonateIdentity("LCMUser");
      return SecurityServiceManager.runAs(kernelId, lcmUser, action);
   }

   private void copyToPending(File source, String editSessionName) throws IOException {
      EditDirectoryManager edm = EditDirectoryManager.getDomainDirectoryManager(editSessionName);
      File target = new File(edm.getPendingFilePath(source.getName()));
      File pendingDir = target.getParentFile();
      if (!pendingDir.exists()) {
         pendingDir.mkdir();
      }

      copyFile(source, target);
      File magic = new File(target.getAbsolutePath() + ".changed");
      copyFile(source, magic);
   }

   static void copyFile(File from, File to) throws IOException {
      Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS);
   }

   private static int minutesToMillis(int minutes) {
      return minutes * '\uea60';
   }

   static {
      AuthenticatedSubject notFinal = null;

      try {
         notFinal = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      } catch (SecurityServiceRuntimeException var5) {
         notFinal = null;
      }

      KERNEL_ID = notFinal;
   }
}

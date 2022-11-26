package weblogic.management.mbeanservers.edit.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.EditSessionConfigurationManagerService;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

public class PortablePartitionUtils {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static EditAccess initEditSession(String editSessionName) throws IllegalArgumentException, ManagementException, ServiceFailureException {
      EditSessionConfigurationManagerService editService = (EditSessionConfigurationManagerService)GlobalServiceLocator.getServiceLocator().getService(EditSessionConfigurationManagerService.class, new Annotation[0]);
      return editService.createEditSession(editSessionName, "");
   }

   public static DomainMBean startEdit(EditAccess editAccess) throws IllegalArgumentException, ManagementException, ServiceFailureException {
      if (editAccess == null) {
         throw new IllegalArgumentException("EditAccess is null and not initialized for startEdit");
      } else {
         if (editAccess.isMergeNeeded()) {
            editAccess.resolve(true, Long.MAX_VALUE);
         }

         return editAccess.startEdit(-1, -1);
      }
   }

   public static void endEditSession(EditAccess editAccess, Boolean force) throws ServiceFailureException, ManagementException {
      DomainRuntimeServiceMBean domainRuntimeService = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      EditSessionConfigurationManagerMBean eSCManager = domainRuntimeService.getDomainRuntime().getEditSessionConfigurationManager();
      EditSessionConfigurationRuntimeMBean eSCRB = eSCManager.lookupEditSessionConfiguration(editAccess.getEditSessionName());
      if (eSCRB == null) {
         throw new IllegalArgumentException("Edit session of name " + editAccess.getEditSessionName() + " is not created");
      } else {
         if (eSCRB != null) {
            if (force) {
               eSCManager.forceDestroyEditSessionConfiguration(eSCRB);
            } else {
               eSCManager.destroyEditSessionConfiguration(eSCRB);
            }
         }

      }
   }

   public static boolean activate(EditAccess editAccess) throws Exception {
      if (editAccess == null) {
         throw new IllegalArgumentException("EditAccess is null and not initialized for activate of edit Session");
      } else {
         ActivateTask actTsk = editAccess.activateChangesAndWaitForCompletion(Long.MAX_VALUE);
         if (actTsk.getError() != null) {
            undoUnactivatedChanges(editAccess);
            throw actTsk.getError();
         } else {
            return actTsk.getState() == 4;
         }
      }
   }

   public static void undoUnactivatedChanges(EditAccess editAccess) throws Exception {
      if (editAccess != null && editAccess.isEditor()) {
         editAccess.undoUnactivatedChanges();
         editAccess.cancelEdit();
      }

   }
}

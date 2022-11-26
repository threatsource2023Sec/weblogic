package weblogic.deploy.internal.adminserver;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.common.Debug;
import weblogic.management.EditSessionConfigurationManager;
import weblogic.management.EditSessionTool;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditChangesValidationException;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.EditNotEditorException;
import weblogic.management.provider.EditSaveChangesFailedException;
import weblogic.management.provider.EditSessionLifecycleListener;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StackTraceUtils;

public final class EditAccessHelper {
   private static final int MAX_EDIT_SESSION_DURATION = 120000;
   private static final int MAX_WAIT_TIME_TO_ACQUIRE_EDIT_SESSION = 0;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final EditSessionConfigurationManager esCfgSvc;
   private final EditSessionTool esToolSvc;
   private final EditAccess delegate;
   private long editStartTime;
   private boolean editSessionStartedByDeployment;
   private boolean editSessionCreatedByDeployment;

   private EditAccessHelper(EditSessionConfigurationManager esCfgSvc, EditSessionTool esToolSvc, String editSessionName) {
      this.esCfgSvc = esCfgSvc;
      this.esToolSvc = esToolSvc;
      EditAccess ea = esCfgSvc.lookupEditSession(editSessionName);
      if (ea == null) {
         String description = this.getClass().getSimpleName();

         try {
            ea = esCfgSvc.createEditSession(editSessionName, description);
         } catch (Throwable var7) {
            throw new RuntimeException(var7);
         }

         if (ea != null) {
            this.editSessionCreatedByDeployment = true;
            this.registerEditSessionListenerForDestroy();
         }
      }

      this.delegate = ea;
      this.editStartTime = this.delegate.getEditorStartTime();
   }

   private void registerEditSessionListenerForDestroy() {
      EditSessionLifecycleListener listener = new EditSessionLifecycleListener() {
         public void onActivateCompleted(EditAccess editAccess, ActivateTask activateTask) {
            try {
               if (editAccess != EditAccessHelper.this.delegate) {
                  return;
               }

               if (EditAccessHelper.this.editSessionCreatedByDeployment && !EditAccessHelper.this.delegate.isDefault()) {
                  EditAccessHelper.this.esCfgSvc.destroyEditSession(EditAccessHelper.this.delegate);
               }
            } catch (ManagementException var4) {
               Debug.deploymentLogger.debug("Deployment failed to destroy edit session after activate" + EditAccessHelper.this.delegate);
            }

         }
      };
      this.esCfgSvc.registerSessionLifecycleListener(listener);
   }

   private void registerEditSessionListenerForPopContext() {
      EditSessionLifecycleListener listener = new EditSessionLifecycleListener() {
         public void onActivateCompleted(EditAccess editAccess, ActivateTask activateTask) {
            if (editAccess == EditAccessHelper.this.delegate) {
               if (EditAccessHelper.this.editSessionStartedByDeployment && EditAccessHelper.this.esToolSvc.getEditContext() == EditAccessHelper.this.delegate) {
                  EditAccessHelper.this.esToolSvc.popEditContext(EditAccessHelper.kernelId);
               }

            }
         }
      };
      this.esCfgSvc.registerSessionLifecycleListener(listener);
   }

   public static EditAccessHelper getInstance(AuthenticatedSubject subject) {
      return getInstance(kernelId, (EditSessionConfigurationManager)GlobalServiceLocator.getServiceLocator().getService(EditSessionConfigurationManager.class, new Annotation[0]), (EditSessionTool)GlobalServiceLocator.getServiceLocator().getService(EditSessionTool.class, new Annotation[0]));
   }

   static EditAccessHelper getInstance(AuthenticatedSubject subject, EditSessionConfigurationManager esCfgSvc, EditSessionTool esToolSvc) {
      SecurityServiceManager.checkKernelIdentity(subject);
      return new EditAccessHelper(esCfgSvc, esToolSvc, "default");
   }

   public static EditAccessHelper getInstance(AuthenticatedSubject subject, String editSessionName) {
      return getInstance(subject, editSessionName, (EditSessionConfigurationManager)GlobalServiceLocator.getServiceLocator().getService(EditSessionConfigurationManager.class, new Annotation[0]), (EditSessionTool)GlobalServiceLocator.getServiceLocator().getService(EditSessionTool.class, new Annotation[0]));
   }

   static EditAccessHelper getInstance(AuthenticatedSubject subject, String editSessionName, EditSessionConfigurationManager esCfgSvc, EditSessionTool esToolSvc) {
      SecurityServiceManager.checkKernelIdentity(subject);
      return new EditAccessHelper(esCfgSvc, esToolSvc, editSessionName);
   }

   public final boolean isEditorExclusive() {
      return this.delegate.isEditorExclusive();
   }

   public final long getEditorExpirationTime() {
      return this.delegate.getEditorExpirationTime();
   }

   public final boolean isPendingChange() {
      return this.delegate.isPendingChange();
   }

   public final DomainMBean startEditSession(boolean inExclusiveMode) throws ManagementException {
      try {
         DomainMBean domain = this.delegate.startEdit(0, 120000, inExclusiveMode);
         this.editSessionStartedByDeployment = true;
         this.editStartTime = this.delegate.getEditorStartTime();
         this.esToolSvc.pushEditContext(kernelId, this.delegate);
         this.registerEditSessionListenerForPopContext();
         return domain;
      } catch (EditWaitTimedOutException var4) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Failed to get an edit session lock: " + var4.toString());
         }

         throw var4;
      } catch (EditFailedException var5) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Failed to get an edit session: " + var5.toString());
         }

         throw var5;
      }
   }

   public final void saveEditSessionChanges() throws ManagementException {
      try {
         this.delegate.saveChanges();
      } catch (EditNotEditorException var2) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Tried to save an edit session when not holding the edit lock: " + var2.toString());
         }

         throw var2;
      } catch (EditSaveChangesFailedException var3) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Attempt to save changes of an edit session failed: " + var3.toString());
         }

         throw var3;
      } catch (EditChangesValidationException var4) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Validation when attempting to save edit session changes failed: " + var4.toString());
         }

         throw var4;
      }
   }

   public final void activateEditSessionChanges(long timeout) throws ManagementException {
      try {
         this.delegate.activateChanges(timeout);
      } catch (EditNotEditorException var4) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Tried to activate an edit session when not holding the edit lock: " + var4.toString());
         }

         throw var4;
      } catch (EditFailedException var5) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Attempt to activate changes of an edit session failed: " + var5.toString());
         }

         throw var5;
      }
   }

   public final void stopEditSession(AuthenticatedSubject authenticatedSubject) throws ManagementException {
      if (this.isCurrentEditor(authenticatedSubject)) {
         Object obj = SecurityServiceManager.runAs(kernelId, authenticatedSubject, new PrivilegedAction() {
            public Object run() {
               Object toReturn = null;

               try {
                  if (EditAccessHelper.this.editStartTime == EditAccessHelper.this.delegate.getEditorStartTime()) {
                     EditAccessHelper.this.delegate.stopEdit();
                  }

                  EditAccessHelper.this.editStartTime = 0L;
                  if (EditAccessHelper.this.editSessionStartedByDeployment && EditAccessHelper.this.esToolSvc.getEditContext() == EditAccessHelper.this.delegate) {
                     EditAccessHelper.this.esToolSvc.popEditContext(EditAccessHelper.kernelId);
                  }
               } catch (EditNotEditorException var3) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Tried to stop an edit session when not holding the edit lock: " + var3.toString());
                  }

                  toReturn = var3;
               } catch (EditFailedException var4) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Edit session failure when attempting to stop an edit session: " + var4.toString());
                  }

                  toReturn = var4;
               }

               return toReturn;
            }
         });
         if (obj instanceof ManagementException) {
            throw (ManagementException)obj;
         }
      }
   }

   public final void cancelActivateSession(AuthenticatedSubject subject) throws ManagementException {
      if (this.isCurrentEditor(subject)) {
         Object obj = SecurityServiceManager.runAs(kernelId, subject, new PrivilegedAction() {
            public Object run() {
               Object toReturn = null;

               try {
                  EditAccessHelper.this.delegate.cancelActivate();
               } catch (Throwable var3) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Edit session failure when attempting to cancel activate session: " + var3.toString());
                  }

                  if (var3 instanceof ManagementException) {
                     toReturn = (ManagementException)var3;
                  } else {
                     toReturn = new ManagementException(var3.getMessage(), var3);
                  }
               }

               return toReturn;
            }
         });
         if (obj instanceof ManagementException) {
            throw (ManagementException)obj;
         }
      }
   }

   public final void cancelEditSession(AuthenticatedSubject authenticatedSubject) {
      SecurityServiceManager.runAs(kernelId, authenticatedSubject, new PrivilegedAction() {
         public Object run() {
            Object toReturn = null;

            try {
               if (EditAccessHelper.this.editStartTime == EditAccessHelper.this.delegate.getEditorStartTime()) {
                  EditAccessHelper.this.delegate.cancelEdit();
               }

               EditAccessHelper.this.editStartTime = 0L;
            } catch (EditFailedException var3) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Edit session failure when attempting  to cancel an edit session: " + var3.toString());
               }
            }

            return toReturn;
         }
      });
   }

   EditAccess getDelegate() {
      return this.delegate;
   }

   public final void undoUnactivatedChanges(AuthenticatedSubject authenticatedSubject) throws ManagementException {
      Object obj = SecurityServiceManager.runAs(kernelId, authenticatedSubject, new PrivilegedAction() {
         public Object run() {
            Object toReturn = null;

            try {
               EditAccessHelper.this.delegate.undoUnactivatedChanges();
            } catch (EditFailedException var3) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Edit session failure when attempting to undo unactivated changes of an edit session: " + var3.toString());
               }

               toReturn = var3;
            } catch (EditNotEditorException var4) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Edit session failure when attempting to undo unactivated changes of an edit session: " + var4.toString());
               }

               toReturn = var4;
            }

            return toReturn;
         }
      });
      if (obj instanceof ManagementException) {
         throw (ManagementException)obj;
      }
   }

   public final boolean isCurrentEditor(AuthenticatedSubject subject) {
      String currentEditor = this.delegate.getEditor();
      if (currentEditor == null) {
         return false;
      } else {
         return subject != null && currentEditor.equals(SubjectUtils.getUsername(subject));
      }
   }

   public final String getCurrentEditor() {
      return this.delegate.getEditor();
   }

   private final DomainMBean getEditDomainBean() {
      try {
         return this.delegate.getDomainBean();
      } catch (EditNotEditorException var2) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Edit session failure when attempting to retrieve the domain mbean: " + var2.toString());
         }
      } catch (EditFailedException var3) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Edit session failure when attempting to retrieve the domain mbean: " + var3.toString());
         }
      }

      return null;
   }

   public final DomainMBean getEditDomainBean(AuthenticatedSubject authenticatedSubject) {
      return this.isCurrentEditor(authenticatedSubject) ? this.getEditDomainBean() : null;
   }

   public void rollback(final AppDeploymentMBean createdApp, final DeploymentBeanFactory beanFactory, final AuthenticatedSubject initiator) {
      SecurityServiceManager.runAs(kernelId, initiator, new PrivilegedAction() {
         public Object run() {
            Object toReturn = null;
            DomainMBean editableDomainMBean = EditAccessHelper.this.getEditDomainBean(initiator);
            if (editableDomainMBean == null) {
               return toReturn;
            } else {
               try {
                  beanFactory.setEditableDomain(editableDomainMBean, true);
                  beanFactory.removeMBean(createdApp);
               } catch (ManagementException var7) {
                  Debug.deploymentLogger.debug("Failed to remove mbeans for failed deployment of : " + createdApp.getName() + " due to " + StackTraceUtils.throwable2StackTrace(var7));
               } finally {
                  beanFactory.resetEditableDomain();
               }

               return toReturn;
            }
         }
      });
   }

   public String getEditSessionName() {
      return this.delegate.getEditSessionName();
   }

   public String getPartitionName() {
      return this.delegate.getPartitionName();
   }

   void nonConfigTaskCompleted(long taskId, boolean success) {
      this.delegate.nonConfigTaskCompleted(taskId, success);
   }
}

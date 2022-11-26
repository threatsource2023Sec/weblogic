package weblogic.management.provider;

public abstract class EditSessionLifecycleListener {
   public void onEditSessionCreated(EditAccess editAccess) {
   }

   public void onEditSessionDestroyed(EditAccess editAccess) {
   }

   public void onEditSessionStarted(EditAccess editAccess) {
   }

   public void onUndoUnsavedChanges(EditAccess editAccess) {
   }

   public void onUndoUnactivatedChanges(EditAccess editAccess) {
   }

   public void onConfigurationReloaded(EditAccess editAccess) {
   }

   public void onActivateCompleted(EditAccess editAccess, ActivateTask activateTask) {
   }

   public void onActivateStarted(EditAccess editAccess, ActivateTask activateTask) {
   }
}

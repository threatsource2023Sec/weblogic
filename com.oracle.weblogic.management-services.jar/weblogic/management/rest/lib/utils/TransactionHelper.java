package weblogic.management.rest.lib.utils;

import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.utils.MessageUtil;

public class TransactionHelper {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(TransactionHelper.class);
   private ConfigurationManagerWrapper cm;
   private HttpServletRequest request;

   public ConfigurationManagerWrapper getConfigurationManager() {
      return this.cm;
   }

   public HttpServletRequest getRequest() {
      return this.request;
   }

   protected TransactionHelper(ConfigurationManagerWrapper cm, HttpServletRequest request) {
      this.cm = cm;
      this.request = request;
   }

   public void beginConfigurationTransaction() throws Exception {
      this.getConfigurationManager().startEdit(0, -1, true);
   }

   public void stopConfigurationTransaction() {
      try {
         this.abortConfigurationTransaction();
      } catch (Throwable var2) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("abortConfigurationTransaction exception", var2);
         }
      }

   }

   public void abortConfigurationTransaction() throws Exception {
      if (this.inOurTransaction()) {
         this.getConfigurationManager().undoUnactivatedChanges();
         this.getConfigurationManager().stopEdit();
      }
   }

   public void commitConfigurationTransaction() throws Exception {
      if (!this.inOurTransaction()) {
         throw new IllegalStateException(MessageUtil.formatter(this.request).msgNotInConfigurationTransaction());
      } else {
         this.activate();
      }
   }

   public boolean isConfigLocked() throws Exception {
      String lockOwner = this.getConfigurationManager().getCurrentEditor();
      if (lockOwner == null) {
         return false;
      } else {
         return !this.getConfigurationManager().isCurrentEditorExpired();
      }
   }

   public void activate() throws Exception {
      this.getConfigurationManager().save();
      ActivationTaskWrapper task1 = this.getConfigurationManager().resolve(true, -1L);
      task1.waitForTaskCompletion();
      Exception e = task1.getError();
      if (e != null) {
         throw e;
      } else {
         ActivationTaskWrapper task2 = this.getConfigurationManager().activate(-1L);
         task2.waitForTaskCompletion();
         e = task2.getError();
         if (e != null) {
            throw e;
         }
      }
   }

   public boolean inOurTransaction() throws Exception {
      return this.getConfigurationManager().isEditor() && !this.getConfigurationManager().isCurrentEditorExpired();
   }
}

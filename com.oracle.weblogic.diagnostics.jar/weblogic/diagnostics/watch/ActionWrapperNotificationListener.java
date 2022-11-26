package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.Notification;
import com.oracle.weblogic.diagnostics.watch.actions.Action;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFLogActionBean;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;
import weblogic.diagnostics.descriptor.WLDFScriptActionBean;
import weblogic.diagnostics.watch.actions.LogActionConfig;
import weblogic.diagnostics.watch.actions.ScriptActionConfig;
import weblogic.diagnostics.watch.actions.WLDFActionConfigWrapper;

final class ActionWrapperNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static DebugLogger debugActionLogger = DebugLogger.getDebugLogger("DebugDiagnosticActionWrapper");
   private Action actionDelegate;
   private WLDFNotificationBean actionBean;

   public ActionWrapperNotificationListener(WLDFNotificationBean actionBean, Action delegate, WatchManagerStatisticsImpl statistics) throws InvalidNotificationException, NotificationCreateException {
      super(actionBean, statistics);
      if (delegate == null) {
         throw new NullPointerException("Delegate action is null");
      } else {
         this.actionDelegate = delegate;
         this.actionBean = actionBean;
         if (actionBean.isEnabled()) {
            this.setEnabled();
         } else {
            this.setDisabled();
         }

         if (debugActionLogger.isDebugEnabled()) {
            debugActionLogger.debug("DEBUG ENABLED for action " + actionBean.getName());
         }

      }
   }

   private ActionConfigBean createActionConfigBean(WLDFActionBean actionBean) {
      try {
         return WLDFActionsFactory.convertToActionConfig(actionBean);
      } catch (Exception var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public void processWatchNotification(Notification n) {
      if (this.isEnabled()) {
         ActionContext actionContext = this.createActionContext(n);
         this.actionDelegate.execute(actionContext);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Notification " + this.getNotificationName() + " is not enabled");
      }

   }

   public void cancel() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Notification " + this.getNotificationName() + " execution canceled.");
      }

      this.actionDelegate.cancel();
   }

   public void reset() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ActionWrapperNotificationListener.reset: " + this.getNotificationName());
      }

      this.actionDelegate.reset();
   }

   private ActionContext createActionContext(Notification n) {
      ActionConfigBean actionConfig = this.createActionConfigBean();
      ActionContext actionContext = new ActionContext(actionConfig);
      List keyList = new ArrayList(n.keyList());
      Iterator var5 = keyList.iterator();

      while(var5.hasNext()) {
         Object key = var5.next();
         if (key == null) {
            if (debugActionLogger.isDebugEnabled()) {
               debugActionLogger.debug("++++++++ KEY IS NULL, Action " + this.actionBean.getName() + " key list: " + keyList.toString() + ", notification impl: " + n.getClass().getName());
            }
         } else {
            Object value = n.getValue(key);
            if (value == null && debugActionLogger.isDebugEnabled()) {
               debugActionLogger.debug("++++++++ VALUE IS NULL for key " + key + ", action " + this.actionBean.getName() + ", notification impl: " + n.getClass().getName());
            }

            actionContext.addWatchData(key.toString(), value == null ? "" : value);
         }
      }

      return actionContext;
   }

   private ActionConfigBean createActionConfigBean() {
      if (this.actionBean instanceof WLDFActionBean) {
         return this.createActionConfigBean((WLDFActionBean)this.actionBean);
      } else if (this.actionBean instanceof WLDFScriptActionBean) {
         return new ScriptActionConfig((WLDFScriptActionBean)this.actionBean);
      } else if (this.actionBean instanceof WLDFLogActionBean) {
         return new LogActionConfig((WLDFLogActionBean)this.actionBean);
      } else if (this.actionBean instanceof WLDFNotificationBean) {
         return new WLDFActionConfigWrapper(this.actionBean);
      } else {
         throw new IllegalArgumentException(this.actionBean.getClass().getName());
      }
   }
}

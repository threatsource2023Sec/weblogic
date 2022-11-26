package weblogic.management.scripting;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;
import weblogic.management.scripting.utils.WLSTInterpreter;

public class EditListener implements RemoteNotificationListener {
   private WLScriptContext ctx;

   public EditListener(WLScriptContext context) {
      this.ctx = context;
   }

   public void handleNotification(Notification notification, Object handback) {
      if (this.ctx != null) {
         WLSTInterpreter interp = this.ctx.getWLSTInterpreter();
         if (this.ctx.isEditSessionInProgress) {
            AttributeChangeNotification notif = (AttributeChangeNotification)notification;
            String newUser = null;
            if (notif.getNewValue() != null) {
               newUser = (String)notif.getNewValue();
               if (!newUser.equals(new String(this.ctx.username_bytes)) || notif.getNewValue() == null) {
                  this.ctx.resetEditSession();
                  this.ctx.println(this.ctx.getWLSTMsgFormatter().getEditSessionTerminated());
                  interp.exec("updatePrompt()");
               }
            }

         }
      }
   }
}

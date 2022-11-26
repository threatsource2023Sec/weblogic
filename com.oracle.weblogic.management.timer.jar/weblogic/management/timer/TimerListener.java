package weblogic.management.timer;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class TimerListener implements Serializable {
   private NotificationListener listener;
   private NotificationFilter filter;
   private Object handback;
   private AuthenticatedSubject subject;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public TimerListener() {
   }

   public TimerListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      this.listener = listener;
      this.filter = filter;
      this.handback = handback;
      this.subject = SecurityServiceManager.getCurrentSubject(kernelId);
   }

   public NotificationListener getListener() {
      return this.listener;
   }

   public void deliverNotification(final Notification tn) {
      final NotificationFilter ffilter = this.filter;
      final Object fhandback = this.handback;
      final NotificationListener flistener = this.listener;
      SecurityServiceManager.runAsForUserCode(kernelId, this.subject, new PrivilegedAction() {
         public Object run() {
            if (ffilter == null || ffilter.isNotificationEnabled(tn)) {
               flistener.handleNotification(tn, fhandback);
            }

            return null;
         }
      });
   }
}

package weblogic.security.jms;

import com.bea.common.security.jms.RobustJMSRemoteCommitProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.jms.JMSException;
import org.apache.openjpa.event.RemoteCommitEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class WebLogicJMSRemoteCommitProvider extends RobustJMSRemoteCommitProvider {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private AuthenticatedSubject subject;
   private static final String consumerClosedException = "ConsumerClosedException";

   public void broadcast(final RemoteCommitEvent event) {
      this.connect();
      AuthenticatedSubject subject = this.getCurrentSubject();
      subject.doAs(kernelId, new PrivilegedAction() {
         public Object run() {
            WebLogicJMSRemoteCommitProvider.super.doBroadcast(event);
            return null;
         }
      });
   }

   public void close() {
      AuthenticatedSubject subject = this.getCurrentSubject();
      subject.doAs(kernelId, new PrivilegedAction() {
         public Object run() {
            WebLogicJMSRemoteCommitProvider.super.close();
            return null;
         }
      });
   }

   protected void afterConnected() {
      this.setCurrentSubject(SecurityServiceManager.getCurrentSubject(kernelId));
   }

   private synchronized AuthenticatedSubject getCurrentSubject() {
      return this.subject == null ? SecurityServiceManager.getCurrentSubject(kernelId) : this.subject;
   }

   private synchronized void setCurrentSubject(AuthenticatedSubject _subject) {
      this.subject = _subject;
   }

   public void onException(final JMSException ex) {
      if (ex != null && ex.getClass().getSimpleName().equals("ConsumerClosedException")) {
         this.close();
      } else {
         AuthenticatedSubject subject = this.getCurrentSubject();
         subject.doAs(kernelId, new PrivilegedAction() {
            public Object run() {
               WebLogicJMSRemoteCommitProvider.super.onException(ex);
               return null;
            }
         });
      }

   }
}

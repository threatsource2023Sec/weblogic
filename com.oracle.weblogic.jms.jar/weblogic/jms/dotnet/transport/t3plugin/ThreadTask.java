package weblogic.jms.dotnet.transport.t3plugin;

import java.security.AccessController;
import weblogic.jms.dotnet.transport.TransportExecutable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

class ThreadTask implements Runnable {
   final TransportExecutable task;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AbstractSubject ANONYMOUS_ID = SubjectManager.getSubjectManager().getAnonymousSubject();

   ThreadTask(TransportExecutable task) {
      this.task = task;
   }

   public void run() {
      try {
         SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, ANONYMOUS_ID);
         this.task.execute();
      } finally {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
      }

   }
}

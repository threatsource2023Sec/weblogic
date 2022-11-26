package weblogic.messaging.kernel.internal;

import java.security.AccessControlException;
import java.security.AccessController;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

class SecurityHelper {
   private static final AbstractSubject kernelID = getKernelIdentity();

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   static AbstractSubject getCurrentSubject() {
      return SubjectManager.getSubjectManager().getCurrentSubject(kernelID);
   }

   static String getSubjectName(AbstractSubject subject) {
      return SubjectManager.getSubjectManager().getSubjectName(subject);
   }

   static String getCurrentSubjectName() {
      return getSubjectName(getCurrentSubject());
   }
}

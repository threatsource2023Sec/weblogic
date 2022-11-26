package weblogic.ejb.container.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.kernel.ThreadLocalStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Service
public class CallerSubjectStack implements FastThreadLocalMarker {
   private static final ThreadLocalStack THREAD_SUBJECT = new ThreadLocalStack(false);

   static AuthenticatedSubject getCurrentSubject() {
      AuthenticatedSubject subject = null;
      Object obj = THREAD_SUBJECT.get();
      if (obj != null && obj instanceof AuthenticatedSubject) {
         subject = (AuthenticatedSubject)obj;
      }

      return subject;
   }

   static void pushSubject(AuthenticatedSubject subject) {
      THREAD_SUBJECT.push(subject);
   }

   static AuthenticatedSubject popSubject() {
      AuthenticatedSubject subject = null;
      Object obj = THREAD_SUBJECT.pop();
      if (obj != null && obj instanceof AuthenticatedSubject) {
         subject = (AuthenticatedSubject)obj;
      }

      return subject;
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}

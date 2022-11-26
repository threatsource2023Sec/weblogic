package weblogic.security.subject;

import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import javax.security.auth.Subject;

public final class SubjectProxy implements AbstractSubject {
   public static SubjectProxy ANON = new SubjectProxy(new Subject());
   private final Subject subject;

   public SubjectProxy(Subject subject) {
      this.subject = subject;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public Set getPrincipals() {
      return this.subject.getPrincipals();
   }

   public Set getPublicCredentials() {
      return this.subject.getPublicCredentials();
   }

   public Set getPrivateCredentials(AbstractSubject kernelId) {
      return this.subject.getPrivateCredentials();
   }

   public Set getPrincipals(Class c) {
      return this.subject.getPrincipals(c);
   }

   public Set getPublicCredentials(Class c) {
      return this.subject.getPublicCredentials(c);
   }

   public Set getPrivateCredentials(AbstractSubject kernelId, Class c) {
      return this.subject.getPrivateCredentials(c);
   }

   public boolean isReadOnly() {
      return this.subject.isReadOnly();
   }

   public void setReadOnly(AbstractSubject kernelId) {
      this.subject.setReadOnly();
   }

   public Object doAs(AbstractSubject kernelId, PrivilegedAction action) {
      if (action == null) {
         throw new SecurityException("Null PrivilegedAction");
      } else {
         SubjectManager.getSubjectManager().pushSubject(kernelId, this);
         Object actionResult = null;

         try {
            actionResult = action.run();
         } finally {
            SubjectManager.getSubjectManager().popSubject(kernelId);
         }

         return actionResult;
      }
   }

   public Object doAs(AbstractSubject kernelId, PrivilegedExceptionAction action) throws PrivilegedActionException {
      if (action == null) {
         throw new SecurityException("Null PrivilegedActionException");
      } else {
         SubjectManager.getSubjectManager().pushSubject(kernelId, this);
         Object actionResult = null;

         try {
            actionResult = action.run();
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new PrivilegedActionException(var10);
         } finally {
            SubjectManager.getSubjectManager().popSubject(kernelId);
         }

         return actionResult;
      }
   }

   public String toString() {
      return "SubjectProxy[" + System.identityHashCode(this.subject) + "]";
   }
}

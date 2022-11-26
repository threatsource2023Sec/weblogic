package weblogic.security.subject;

import java.util.EmptyStackException;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.ThreadLocalStack;

public class DelegatingSubjectStack implements SubjectStack {
   private static final ThreadLocalStack threadSubject = new ThreadLocalStack(true);
   private SubjectStackDelegate delegate;

   public AbstractSubject getCurrentSubject(AbstractSubject kernelIdentity) {
      SubjectManager.getSubjectManager().checkKernelIdentity(kernelIdentity);
      AbstractSubject obj = (AbstractSubject)threadSubject.get();
      if (this.delegate != null) {
         obj = this.delegate.getCurrentSubject(kernelIdentity, obj);
      }

      return obj;
   }

   public AbstractSubject getCurrentSubject(AbstractSubject kernelIdentity, AuditableThread auditableThread) {
      SubjectManager.getSubjectManager().checkKernelIdentity(kernelIdentity);
      AbstractSubject obj = (AbstractSubject)threadSubject.get(auditableThread);
      if (this.delegate != null) {
         obj = this.delegate.getCurrentSubject(kernelIdentity, obj);
      }

      return obj;
   }

   public void pushSubject(AbstractSubject kernelIdentity, AbstractSubject userIdentity) {
      if (userIdentity == null) {
         throw new IllegalArgumentException("Illegal null Subject passed as a parameter.");
      } else {
         SubjectManager.getSubjectManager().checkKernelIdentity(kernelIdentity);
         if (this.delegate != null) {
            this.delegate.pushSubject(kernelIdentity, userIdentity);
         }

         threadSubject.push(userIdentity);
      }
   }

   public void popSubject(AbstractSubject kernelIdentity) {
      SubjectManager.getSubjectManager().checkKernelIdentity(kernelIdentity);
      Object o = null;

      try {
         o = threadSubject.popAndPeek();
      } catch (EmptyStackException var4) {
      }

      if (this.delegate != null) {
         this.delegate.popSubject(kernelIdentity, (AbstractSubject)o);
      }

   }

   public int getSize() {
      return threadSubject.getSize();
   }

   public void addDelegate(SubjectStackDelegate delegate) {
      if (this.delegate != null) {
         this.delegate.addDelegate(delegate);
      } else {
         this.delegate = delegate;
      }

   }
}

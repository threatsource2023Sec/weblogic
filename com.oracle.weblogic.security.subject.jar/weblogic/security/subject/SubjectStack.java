package weblogic.security.subject;

import weblogic.kernel.AuditableThread;

public interface SubjectStack {
   AbstractSubject getCurrentSubject(AbstractSubject var1);

   AbstractSubject getCurrentSubject(AbstractSubject var1, AuditableThread var2);

   void pushSubject(AbstractSubject var1, AbstractSubject var2);

   void popSubject(AbstractSubject var1);

   int getSize();
}

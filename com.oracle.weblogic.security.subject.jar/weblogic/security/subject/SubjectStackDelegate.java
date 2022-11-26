package weblogic.security.subject;

public interface SubjectStackDelegate {
   AbstractSubject getCurrentSubject(AbstractSubject var1, AbstractSubject var2);

   void pushSubject(AbstractSubject var1, AbstractSubject var2);

   void popSubject(AbstractSubject var1, AbstractSubject var2);

   void addDelegate(SubjectStackDelegate var1);
}

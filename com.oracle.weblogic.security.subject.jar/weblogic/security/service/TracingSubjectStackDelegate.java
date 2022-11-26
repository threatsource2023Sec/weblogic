package weblogic.security.service;

import java.io.PrintWriter;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectStackDelegate;

public final class TracingSubjectStackDelegate implements SubjectStackDelegate {
   private final boolean isTracingPushes = true;
   private final boolean isTracingPops = true;
   private final boolean isTracingCallers = false;
   private final boolean isTracingSubjects = true;
   private final PrintWriter traceWriter;
   private SubjectStackDelegate delegate;

   public TracingSubjectStackDelegate() {
      this.traceWriter = new PrintWriter(System.out, true);
      this.delegate = null;
   }

   public AbstractSubject getCurrentSubject(AbstractSubject kernelIdentity, AbstractSubject current) {
      return current;
   }

   public synchronized void pushSubject(AbstractSubject kernelIdentity, AbstractSubject userIdentity) {
      this.println("Pushing " + SubjectUtils.getPrincipalNames((AuthenticatedSubject)userIdentity));
   }

   public synchronized void popSubject(AbstractSubject kernelIdentity, AbstractSubject newUserIdentoty) {
      this.println("Popping Subject, new Subject is" + SubjectUtils.getPrincipalNames((AuthenticatedSubject)newUserIdentoty));
   }

   private void traceCallers(String op) {
      Throwable t = new Throwable("Tracing SubjectStack callers...");
      this.println("===============================================================");
      this.println(op);
      this.println("===============================================================");
      t.printStackTrace(this.traceWriter);
   }

   private void println(String s) {
      this.traceWriter.println(Thread.currentThread().getName() + ": " + s);
   }

   public void addDelegate(SubjectStackDelegate delegate) {
      if (this.delegate != null) {
         this.delegate.addDelegate(delegate);
      } else {
         this.delegate = delegate;
      }

   }
}

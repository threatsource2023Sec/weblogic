package com.bea.core.security.managers.internal;

import com.bea.core.security.managers.NotSupportedException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.kernel.AuditableThread;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class WLSSubjectManagerImpl extends SubjectManager {
   private final WLSStackService stackService = new WLSStackService();
   private static final Principal KERNEL_PRINCIPAL = new Principal() {
      public String getName() {
         return "<WLS Kernel>";
      }
   };
   private static final AbstractSubject KERNEL_ID;

   protected AbstractSubject createAbstractSubject(Subject subject) {
      throw new NotSupportedException();
   }

   protected AbstractSubject getKernelIdentity() {
      return KERNEL_ID;
   }

   public AbstractSubject getCurrentSubject(AbstractSubject kernelID) {
      AbstractSubject currentIdentity = this.stackService.peekIdentity();
      return (AbstractSubject)(currentIdentity == null ? new AuthenticatedSubject(new Subject()) : currentIdentity);
   }

   public AbstractSubject getCurrentSubject(AbstractSubject kernelID, AuditableThread auditableThread) {
      throw new NotSupportedException();
   }

   public int getSize() {
      return this.stackService.getSize();
   }

   public void popSubject(AbstractSubject kernelIdentity) {
      this.stackService.popIdentity();
   }

   public void pushSubject(AbstractSubject kernelIdentity, AbstractSubject userIdentity) {
      if (userIdentity != null) {
         this.stackService.pushIdentity(userIdentity);
      }
   }

   public AbstractSubject getAnonymousSubject() {
      return AuthenticatedSubject.ANON;
   }

   public String toString() {
      return "WLSSubjectManagerImpl(" + System.identityHashCode(this) + ")";
   }

   static {
      Set principals = new HashSet();
      principals.add(KERNEL_PRINCIPAL);
      KERNEL_ID = new AuthenticatedSubject(true, principals);
   }
}

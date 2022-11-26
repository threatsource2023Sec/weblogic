package weblogic.connector.security.layer;

import javax.resource.spi.work.SecurityContext;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextLifecycleListener;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class SecurityContextImpl extends SecurityContext implements WorkContextWrapper {
   private static final long serialVersionUID = -4079977347788107655L;
   private WorkContextImpl contextImpl;

   public SecurityContextImpl(SecurityContext context, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      this.contextImpl = new WorkContextImpl(context, adapterLayer, "SecurityContext", kernelId);
   }

   public Class getOriginalClass() {
      return this.contextImpl.getOriginalClass();
   }

   public WorkContext getOriginalWorkContext() {
      return this.contextImpl.getOriginalWorkContext();
   }

   public WorkContextLifecycleListener getOriginalWorkContextLifecycleListener() {
      return this.contextImpl.getOriginalWorkContextLifecycleListener();
   }

   public boolean supportWorkContextLifecycleListener() {
      return this.contextImpl.supportWorkContextLifecycleListener();
   }

   public String getDescription() {
      return this.contextImpl.getDescription();
   }

   public String getName() {
      return this.contextImpl.getName();
   }

   public void contextSetupComplete() {
      this.contextImpl.contextSetupComplete();
   }

   public void contextSetupFailed(String errorCode) {
      this.contextImpl.contextSetupFailed(errorCode);
   }

   public boolean equals(Object other) {
      return this == other;
   }

   public int hashCode() {
      return this.contextImpl.hashCode();
   }

   public String toString() {
      return this.contextImpl.toString();
   }

   public SecurityContext getOriginalSecurityContext() {
      return (SecurityContext)this.getOriginalWorkContext();
   }

   public void setupSecurityContext(CallbackHandler handler, Subject executionSubject, Subject serviceSubject) {
      this.contextImpl.push();

      try {
         this.getOriginalSecurityContext().setupSecurityContext(handler, executionSubject, serviceSubject);
      } finally {
         this.contextImpl.pop();
      }

   }
}

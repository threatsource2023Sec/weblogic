package weblogic.connector.security.layer;

import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextLifecycleListener;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WorkContextImpl extends SecureBaseImpl implements WorkContextWrapper {
   private static final long serialVersionUID = -7598740383370802988L;
   Class originalClass;

   public WorkContextImpl(WorkContext wc, SubjectStack adapterLayer, String type, AuthenticatedSubject kernelId) {
      super(wc, adapterLayer, type, kernelId);
   }

   public WorkContext getOriginalWorkContext() {
      return (WorkContext)this.getSourceObj();
   }

   public boolean supportWorkContextLifecycleListener() {
      return this.getSourceObj() instanceof WorkContextLifecycleListener;
   }

   public WorkContextLifecycleListener getOriginalWorkContextLifecycleListener() {
      return (WorkContextLifecycleListener)this.getSourceObj();
   }

   public Class getOriginalClass() {
      if (this.originalClass != null) {
         return this.originalClass;
      } else {
         this.push();

         Class var1;
         try {
            this.originalClass = this.getOriginalWorkContext().getClass();
            var1 = this.originalClass;
         } finally {
            this.pop();
         }

         return var1;
      }
   }

   public String getDescription() {
      this.push();

      String var1;
      try {
         var1 = this.getOriginalWorkContext().getDescription();
      } finally {
         this.pop();
      }

      return var1;
   }

   public String getName() {
      this.push();

      String var1;
      try {
         var1 = this.getOriginalWorkContext().getName();
      } finally {
         this.pop();
      }

      return var1;
   }

   public void contextSetupComplete() {
      this.push();

      try {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextImpl: call contextSetupComplete on WorkContextLifecycleListener " + this);
         }

         this.getOriginalWorkContextLifecycleListener().contextSetupComplete();
      } catch (Throwable var5) {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextImpl: unexpected exception when call contextSetupComplete on " + this + "; ignored.", var5);
         }

         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WorkContextLifecycleListener.contextSetupComplete", var5);
      } finally {
         this.pop();
      }

   }

   public void contextSetupFailed(String errorCode) {
      this.push();

      try {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextImpl: call contextSetupFailed on WorkContextLifecycleListener " + this + " with errorCode:" + errorCode);
         }

         this.getOriginalWorkContextLifecycleListener().contextSetupFailed(errorCode);
      } catch (Throwable var6) {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextImpl: unexpected exception when call contextSetupFailed on " + this + "; ignored.", var6);
         }

         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WorkContextLifecycleListener.contextSetupFailed", var6);
      } finally {
         this.pop();
      }

   }

   final void rejectIllegalUpdate() {
      throw new IllegalStateException("Context's value must not be changed in wrapper layer");
   }

   public boolean equals(Object other) {
      return this == other;
   }
}

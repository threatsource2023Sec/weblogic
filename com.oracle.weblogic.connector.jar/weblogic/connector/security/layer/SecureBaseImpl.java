package weblogic.connector.security.layer;

import weblogic.connector.common.Debug;
import weblogic.connector.security.SecurityHelperFactory;
import weblogic.connector.security.SubjectStack;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;

class SecureBaseImpl {
   protected Object myObj;
   protected AuthenticatedSubject kernelId;
   protected SubjectStack adapterLayer;

   SecureBaseImpl(Object obj, SubjectStack adapterLayer, String type, AuthenticatedSubject kernelId) {
      if (KernelStatus.isServer() && !SecurityHelperFactory.getInstance().isKernelIdentity(kernelId)) {
         throw new SecurityException("KernelId is required to instantiate SecureBaseImpl class, Subject '" + (kernelId == null ? "<null>" : kernelId.toString()) + "' is not the kernel identity");
      } else if (adapterLayer == null) {
         throw new AssertionError("Attempt to execute outside the context of the Connector container");
      } else {
         if (obj == null) {
            Debug.throwAssertionError(type + " == null");
         }

         this.myObj = obj;
         this.adapterLayer = adapterLayer;
         this.kernelId = kernelId;
      }
   }

   protected final void push() {
      this.adapterLayer.pushSubject(this.kernelId);
   }

   protected final void pushWorkSubject(AuthenticatedSubject callerSubject) {
      this.adapterLayer.pushWorkSubject(this.kernelId, callerSubject);
   }

   protected final void pushGivenSubject(AuthenticatedSubject subject) {
      this.adapterLayer.pushGivenSubject(this.kernelId, subject);
   }

   protected final void pop() {
      this.adapterLayer.popSubject(this.kernelId);
   }

   public SubjectStack getSubjectStack() {
      return this.adapterLayer;
   }

   public AuthenticatedSubject getKernelId() {
      return this.kernelId;
   }

   public boolean equals(Object other) {
      this.push();

      boolean var2;
      try {
         var2 = this.myObj.equals(other);
      } finally {
         this.pop();
      }

      return var2;
   }

   public int hashCode() {
      this.push();

      int var1;
      try {
         var1 = this.myObj.hashCode();
      } finally {
         this.pop();
      }

      return var1;
   }

   public String toString() {
      this.push();

      String var1;
      try {
         var1 = this.myObj.toString();
      } finally {
         this.pop();
      }

      return var1;
   }

   public Object getSourceObj() {
      return this.myObj;
   }
}

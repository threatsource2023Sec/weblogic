package weblogic.connector.security.layer;

import javax.resource.NotSupportedException;
import javax.resource.spi.work.ExecutionContext;
import javax.transaction.xa.Xid;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class ExecutionContextImpl extends ExecutionContext {
   SecureBaseImpl secureBase;

   public ExecutionContextImpl(ExecutionContext ctx, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      this.secureBase = new SecureBaseImpl(ctx, adapterLayer, "ExecutionContext", kernelId);
   }

   public long getTransactionTimeout() {
      this.secureBase.push();

      long var1;
      try {
         var1 = ((ExecutionContext)this.secureBase.myObj).getTransactionTimeout();
      } finally {
         this.secureBase.pop();
      }

      return var1;
   }

   public Xid getXid() {
      this.secureBase.push();

      XidImpl var2;
      try {
         Xid xid = ((ExecutionContext)this.secureBase.myObj).getXid();
         if (xid != null) {
            var2 = new XidImpl(xid, this.secureBase.adapterLayer, this.secureBase.kernelId);
            return var2;
         }

         var2 = null;
      } finally {
         this.secureBase.pop();
      }

      return var2;
   }

   public void setTransactionTimeout(long timeout) throws NotSupportedException {
      this.secureBase.push();

      try {
         ((ExecutionContext)this.secureBase.myObj).setTransactionTimeout(timeout);
      } finally {
         this.secureBase.pop();
      }

   }

   public void setXid(Xid xid) {
      this.secureBase.push();

      try {
         ((ExecutionContext)this.secureBase.myObj).setXid(xid);
      } finally {
         this.secureBase.pop();
      }

   }

   public boolean equals(Object other) {
      this.secureBase.push();

      boolean var2;
      try {
         var2 = this.secureBase.myObj.equals(other);
      } finally {
         this.secureBase.pop();
      }

      return var2;
   }

   public int hashCode() {
      this.secureBase.push();

      int var1;
      try {
         var1 = this.secureBase.myObj.hashCode();
      } finally {
         this.secureBase.pop();
      }

      return var1;
   }

   public String toString() {
      this.secureBase.push();

      String var1;
      try {
         var1 = this.secureBase.myObj.toString();
      } finally {
         this.secureBase.pop();
      }

      return var1;
   }
}

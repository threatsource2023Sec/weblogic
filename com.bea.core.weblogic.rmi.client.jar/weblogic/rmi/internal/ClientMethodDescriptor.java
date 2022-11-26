package weblogic.rmi.internal;

import java.io.Serializable;
import java.util.Arrays;

public final class ClientMethodDescriptor implements Serializable {
   private String signature;
   static final long serialVersionUID = -8420159570635889915L;
   private final boolean transactional;
   private final boolean oneway;
   private final boolean onewayTransactionalRequest;
   private final boolean idempotent;
   private int timeOut;
   private final boolean asynchronousResult;
   private final short[] marshalParameters;
   private final String remoteExceptionWrapperClassName;

   private static boolean equals(Object o1, Object o2) {
      return o1 == null ? o2 == null : o1.equals(o2);
   }

   public ClientMethodDescriptor(String signature, boolean transactional, boolean oneway, boolean onewayTransactionalRequest, boolean idempotent, int mSecs) {
      this(signature, transactional, oneway, onewayTransactionalRequest, idempotent, mSecs, (short[])null);
   }

   public ClientMethodDescriptor(String signature, boolean transactional, boolean oneway, boolean onewayTransactionalRequest, boolean idempotent, int mSecs, short[] marshalParameters) {
      this(signature, transactional, oneway, onewayTransactionalRequest, idempotent, mSecs, (short[])null, (String)null);
   }

   public ClientMethodDescriptor(String signature, boolean transactional, boolean oneway, boolean onewayTransactionalRequest, boolean idempotent, int mSecs, short[] marshalParameters, String remoteExceptionWrapperClassName) {
      this(signature, transactional, oneway, onewayTransactionalRequest, idempotent, mSecs, false, (short[])null, (String)null);
   }

   public ClientMethodDescriptor(String signature, boolean transactional, boolean oneway, boolean onewayTransactionalRequest, boolean idempotent, int mSecs, boolean asynchronousResult, short[] marshalParameters, String remoteExceptionWrapperClassName) {
      this.signature = signature;
      this.transactional = transactional;
      this.oneway = oneway;
      this.onewayTransactionalRequest = onewayTransactionalRequest;
      this.idempotent = idempotent;
      this.timeOut = mSecs;
      this.asynchronousResult = asynchronousResult;
      this.marshalParameters = marshalParameters;
      this.remoteExceptionWrapperClassName = remoteExceptionWrapperClassName;
   }

   public String getSignature() {
      return this.signature;
   }

   public boolean isTransactional() {
      return this.transactional;
   }

   public boolean isOneway() {
      return this.oneway;
   }

   int getTimeOut() {
      return this.timeOut;
   }

   public void setTimeOut(int timeOut) {
      this.timeOut = timeOut;
   }

   public boolean isIdempotent() {
      return this.idempotent;
   }

   boolean isOnewayTransactionalRequest() {
      return this.onewayTransactionalRequest;
   }

   public boolean getAsynchronousResult() {
      return this.asynchronousResult;
   }

   public String getRemoteExceptionWrapperClassName() {
      return this.remoteExceptionWrapperClassName;
   }

   public String toString() {
      return super.toString() + " - signature: '" + this.signature + "', oneway: '" + this.oneway + "', transactional: '" + this.transactional + "', oneway transactional request: '" + this.onewayTransactionalRequest + "', idempotent: '" + this.idempotent + "', asynchronousResult: '" + this.asynchronousResult + "', timeout: '" + this.timeOut + "'";
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ClientMethodDescriptor)) {
         return false;
      } else {
         ClientMethodDescriptor other = (ClientMethodDescriptor)o;
         return equals(other.signature, this.signature) && other.oneway == this.oneway && other.transactional == this.transactional && other.onewayTransactionalRequest == this.onewayTransactionalRequest && other.idempotent == this.idempotent && other.asynchronousResult == this.asynchronousResult && other.timeOut == this.timeOut && Arrays.equals(other.marshalParameters, this.marshalParameters);
      }
   }

   public int hashCode() {
      return this.signature != null ? this.signature.hashCode() : super.hashCode();
   }

   void internSignature() {
      this.signature = this.signature.intern();
   }

   public ClientMethodDescriptor createWithAsync(String signature) {
      return new ClientMethodDescriptor(signature, this.transactional, this.oneway, this.onewayTransactionalRequest, this.idempotent, this.timeOut, true, this.marshalParameters, this.remoteExceptionWrapperClassName);
   }
}

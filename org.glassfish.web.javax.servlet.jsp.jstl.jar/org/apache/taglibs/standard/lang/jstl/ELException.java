package org.apache.taglibs.standard.lang.jstl;

public class ELException extends Exception {
   Throwable mRootCause;

   public ELException() {
   }

   public ELException(String pMessage) {
      super(pMessage);
   }

   public ELException(Throwable pRootCause) {
      this.mRootCause = pRootCause;
   }

   public ELException(String pMessage, Throwable pRootCause) {
      super(pMessage);
      this.mRootCause = pRootCause;
   }

   public Throwable getRootCause() {
      return this.mRootCause;
   }

   public String toString() {
      if (this.getMessage() == null) {
         return this.mRootCause.toString();
      } else {
         return this.mRootCause == null ? this.getMessage() : this.getMessage() + ": " + this.mRootCause;
      }
   }
}

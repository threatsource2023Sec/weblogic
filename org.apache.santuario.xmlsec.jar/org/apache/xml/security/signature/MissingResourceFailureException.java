package org.apache.xml.security.signature;

public class MissingResourceFailureException extends XMLSignatureException {
   private static final long serialVersionUID = 1L;
   private Reference uninitializedReference;

   public MissingResourceFailureException(Reference reference, String msgID) {
      super(msgID);
      this.uninitializedReference = reference;
   }

   /** @deprecated */
   @Deprecated
   public MissingResourceFailureException(String msgID, Reference reference) {
      this(reference, msgID);
   }

   public MissingResourceFailureException(Reference reference, String msgID, Object[] exArgs) {
      super(msgID, exArgs);
      this.uninitializedReference = reference;
   }

   /** @deprecated */
   @Deprecated
   public MissingResourceFailureException(String msgID, Object[] exArgs, Reference reference) {
      this(reference, msgID, exArgs);
   }

   public MissingResourceFailureException(Exception originalException, Reference reference, String msgID) {
      super(originalException, msgID);
      this.uninitializedReference = reference;
   }

   /** @deprecated */
   @Deprecated
   public MissingResourceFailureException(String msgID, Exception originalException, Reference reference) {
      this(originalException, reference, msgID);
   }

   public MissingResourceFailureException(Exception originalException, Reference reference, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
      this.uninitializedReference = reference;
   }

   /** @deprecated */
   @Deprecated
   public MissingResourceFailureException(String msgID, Object[] exArgs, Exception originalException, Reference reference) {
      this(originalException, reference, msgID, exArgs);
   }

   public void setReference(Reference reference) {
      this.uninitializedReference = reference;
   }

   public Reference getReference() {
      return this.uninitializedReference;
   }
}

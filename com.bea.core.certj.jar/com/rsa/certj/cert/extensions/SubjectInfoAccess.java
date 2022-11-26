package com.rsa.certj.cert.extensions;

/** @deprecated */
public final class SubjectInfoAccess extends InfoAccess {
   /** @deprecated */
   public static final byte[] ID_AD_CA_REPOSITORY = new byte[]{43, 6, 1, 5, 5, 7, 48, 5};
   /** @deprecated */
   public static final byte[] ID_AD_TIME_STAMPING = new byte[]{43, 6, 1, 5, 5, 7, 48, 3};

   /** @deprecated */
   public SubjectInfoAccess() {
      super(125, false, SUBJECT_INFO_OID, "SubjectInfoAccess");
   }

   /** @deprecated */
   public Object clone() {
      SubjectInfoAccess var1 = new SubjectInfoAccess();
      super.copyValues(var1);
      return var1;
   }
}

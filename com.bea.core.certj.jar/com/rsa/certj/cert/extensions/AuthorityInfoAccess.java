package com.rsa.certj.cert.extensions;

import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public final class AuthorityInfoAccess extends InfoAccess {
   /** @deprecated */
   public static final byte[] ID_AD_OCSP = new byte[]{43, 6, 1, 5, 5, 7, 48, 1};

   /** @deprecated */
   public AuthorityInfoAccess() {
      super(100, false, AUTHORITY_INFO_OID, "AuthorityInfoAccess");
   }

   /** @deprecated */
   public AuthorityInfoAccess(byte[] var1, int var2, int var3, GeneralName var4, boolean var5) throws CertificateException {
      super(100, var5, AUTHORITY_INFO_OID, "AuthorityInfoAccess");
      this.addAccessDescription(var1, var2, var3, var4);
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      AuthorityInfoAccess var1 = new AuthorityInfoAccess();
      super.copyValues(var1);
      return var1;
   }
}

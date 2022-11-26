package com.rsa.certj.spi.revocation;

/** @deprecated */
public final class CertRevocationInfo {
   /** @deprecated */
   public static final int CERT_NOT_REVOKED = 0;
   /** @deprecated */
   public static final int CERT_REVOKED = 1;
   /** @deprecated */
   public static final int CERT_REVOCATION_UNKNOWN = 2;
   /** @deprecated */
   public static final int CRE_NONE = 0;
   /** @deprecated */
   public static final int CRE_CRL = 1;
   /** @deprecated */
   public static final int CRE_OCSP = 2;
   private int status;
   private int evidenceType;
   private Object evidence;

   /** @deprecated */
   public CertRevocationInfo() {
      this.status = 2;
      this.evidenceType = 0;
      this.evidence = null;
   }

   /** @deprecated */
   public CertRevocationInfo(int var1, int var2, Object var3) {
      this.status = var1;
      this.evidenceType = var2;
      this.evidence = var3;
   }

   /** @deprecated */
   public String toString() {
      String var1;
      switch (this.status) {
         case 0:
            var1 = "CERT_NOT_REVOKED";
            break;
         case 1:
            var1 = "CERT_REVOKED";
            break;
         case 2:
            var1 = "CERT_REVOCATION_UNKNOWN";
            break;
         default:
            var1 = "???";
      }

      String var2;
      String var3;
      switch (this.evidenceType) {
         case 0:
            var2 = "CRE_NONE";
            var3 = "null";
            break;
         case 1:
            var2 = "CRE_CRL";
            var3 = this.evidence.toString();
            break;
         case 2:
            var2 = "CRE_OCSP";
            var3 = this.evidence.toString();
            break;
         default:
            var2 = "???";
            var3 = "???";
      }

      return "{status=" + var1 + ",evidenceType=" + var2 + ",evidence=" + var3 + "}";
   }

   /** @deprecated */
   public void setStatus(int var1) {
      this.status = var1;
   }

   /** @deprecated */
   public void setType(int var1) {
      this.evidenceType = var1;
   }

   /** @deprecated */
   public void setEvidence(Object var1) {
      this.evidence = var1;
   }

   /** @deprecated */
   public int getStatus() {
      return this.status;
   }

   /** @deprecated */
   public int getType() {
      return this.evidenceType;
   }

   /** @deprecated */
   public Object getEvidence() {
      return this.evidence;
   }
}

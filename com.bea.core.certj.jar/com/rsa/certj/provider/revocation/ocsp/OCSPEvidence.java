package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.X509V3Extensions;
import java.util.Date;

/** @deprecated */
public final class OCSPEvidence {
   /** @deprecated */
   public static final int NONCE_IGNORED = 1;
   private int flags;
   private Date producedAt;
   private Date thisUpdate;
   private Date nextUpdate;
   private X509V3Extensions responseExtensions;
   private OCSPRevocationInfo revocationInfo;

   /** @deprecated */
   public OCSPEvidence(int var1, Date var2, Date var3, Date var4, X509V3Extensions var5, OCSPRevocationInfo var6) throws InvalidParameterException {
      if (var2 == null) {
         throw new InvalidParameterException("producedAtTime == null");
      } else if (var3 == null) {
         throw new InvalidParameterException("thisUpdateTime == null");
      } else {
         this.flags = var1;
         this.producedAt = var2;
         this.thisUpdate = var3;
         this.nextUpdate = var4;
         this.responseExtensions = var5;
         this.revocationInfo = var6;
      }
   }

   /** @deprecated */
   public String toString() {
      return "{flags=" + Integer.toHexString(this.flags) + ",producedAt=" + this.producedAt + ",thisUpdate=" + this.thisUpdate + ",nextUpdate=" + this.nextUpdate + ",responseExtensions=" + this.responseExtensions + ",revocationInfo=" + this.revocationInfo + "}";
   }

   /** @deprecated */
   public int getFlags() {
      return this.flags;
   }

   /** @deprecated */
   public void setFlags(int var1) {
      this.flags = var1;
   }

   /** @deprecated */
   public Date getProducedAt() {
      return this.producedAt;
   }

   /** @deprecated */
   public Date getThisUpdate() {
      return this.thisUpdate;
   }

   /** @deprecated */
   public Date getNextUpdate() {
      return this.nextUpdate;
   }

   /** @deprecated */
   public X509V3Extensions getResponseExtensions() {
      return this.responseExtensions;
   }

   /** @deprecated */
   public OCSPRevocationInfo getRevocationInfo() {
      return this.revocationInfo;
   }
}

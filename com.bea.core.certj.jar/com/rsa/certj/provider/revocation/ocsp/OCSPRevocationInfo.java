package com.rsa.certj.provider.revocation.ocsp;

import java.util.Date;

/** @deprecated */
public final class OCSPRevocationInfo implements Cloneable {
   private int reasonCode;
   private Date revocationTime;

   /** @deprecated */
   public OCSPRevocationInfo(long var1) {
      this.reasonCode = -1;
      this.revocationTime = new Date(var1);
   }

   /** @deprecated */
   public OCSPRevocationInfo(int var1, Date var2) {
      this.reasonCode = var1;
      this.revocationTime = var2 == null ? null : new Date(var2.getTime());
   }

   /** @deprecated */
   public OCSPRevocationInfo(int var1, long var2) {
      this.reasonCode = var1;
      this.revocationTime = new Date(var2);
   }

   /** @deprecated */
   public Object clone(OCSPRevocationInfo var1) {
      return var1 == null ? null : new OCSPRevocationInfo(var1.reasonCode, var1.revocationTime);
   }

   /** @deprecated */
   public int getReasonCode() {
      return this.reasonCode;
   }

   /** @deprecated */
   public Date getRevocationTime() {
      return new Date(this.revocationTime.getTime());
   }

   /** @deprecated */
   public void setReasonCode(int var1) {
      this.reasonCode = var1;
   }
}

package com.rsa.certj.spi.pki;

/** @deprecated */
public class PKITransportException extends PKIException {
   private PKIStatusInfo statusInfo;

   /** @deprecated */
   public PKITransportException(String var1) {
      this(var1, (PKIStatusInfo)null);
   }

   /** @deprecated */
   public PKITransportException(Exception var1) {
      super(var1);
   }

   /** @deprecated */
   public PKITransportException(String var1, Exception var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public PKITransportException(String var1, PKIStatusInfo var2) {
      super(var1);
      this.statusInfo = var2;
   }

   /** @deprecated */
   public PKIStatusInfo getStatusInfo() {
      return this.statusInfo;
   }
}

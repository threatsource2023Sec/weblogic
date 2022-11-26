package com.rsa.certj.spi.pki;

import com.rsa.certj.spi.path.CertPathCtx;

/** @deprecated */
public final class ProtectInfoPublicKey extends ProtectInfo {
   private CertPathCtx certPathCtx;

   /** @deprecated */
   public ProtectInfoPublicKey(CertPathCtx var1) {
      this.certPathCtx = var1;
   }

   /** @deprecated */
   public CertPathCtx getCertPathCtx() {
      return this.certPathCtx;
   }
}

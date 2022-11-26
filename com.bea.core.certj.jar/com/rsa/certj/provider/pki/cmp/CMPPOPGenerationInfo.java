package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.spi.pki.POPGenerationInfo;

abstract class CMPPOPGenerationInfo extends POPGenerationInfo {
   private int popType;

   /** @deprecated */
   protected CMPPOPGenerationInfo(int var1) {
      this.popType = var1;
   }

   /** @deprecated */
   protected int getPopType() {
      return this.popType;
   }
}

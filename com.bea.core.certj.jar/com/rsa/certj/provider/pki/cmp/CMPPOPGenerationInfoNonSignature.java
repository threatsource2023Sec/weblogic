package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.InvalidParameterException;

/** @deprecated */
public abstract class CMPPOPGenerationInfoNonSignature extends CMPPOPGenerationInfo {
   /** @deprecated */
   public static final int POP_METHOD_ENCRYPT_CERT = 2;
   private static final int[] VALID_METHODS = new int[]{2};
   private int method = -1;

   /** @deprecated */
   protected CMPPOPGenerationInfoNonSignature(int var1, int var2) throws InvalidParameterException {
      super(var1);
      switch (var1) {
         case 2:
         case 3:
            boolean var3 = false;

            for(int var4 = 0; var4 < VALID_METHODS.length; ++var4) {
               if (var2 == VALID_METHODS[var4]) {
                  var3 = true;
                  break;
               }
            }

            if (!var3) {
               throw new InvalidParameterException("CMPPOPGenerationInfoNonSignature.CMPPOPGenerationInfoNonSignature: method(" + var2 + ") is not valid.");
            } else {
               this.method = var2;
               return;
            }
         default:
            throw new InvalidParameterException("CMPPOPGenerationInfoNonSignature.CMPPOPGenerationInfoNonSignature: popType(" + var1 + ") is not valid.");
      }
   }

   /** @deprecated */
   protected int getMethod() {
      return this.method;
   }
}

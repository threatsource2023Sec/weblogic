package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.extensions.GeneralName;

/** @deprecated */
public final class CMPPOPGenerationInfoSignature extends CMPPOPGenerationInfo {
   private static final int AUTH_BY_SENDER_NAME = 0;
   private static final int AUTH_BY_PBM = 1;
   private static final int DEFAULT_PBM_ITERATION_COUNT = 1024;
   private int type;
   private String signatureAlgorithm;
   private GeneralName sender;
   private char[] sharedSecret;
   private byte[] salt;
   private int iterationCount;

   boolean authBySenderName() {
      return this.type == 0;
   }

   boolean authByPBM() {
      return this.type == 1;
   }

   private CMPPOPGenerationInfoSignature(String var1) throws InvalidParameterException {
      super(1);
      if (var1 == null) {
         throw new InvalidParameterException("CMPPOPGenerationInfoSignature.CMPPOPGenerationInfoSignature:signatureAlgorithm should not be null.");
      } else {
         this.signatureAlgorithm = var1;
      }
   }

   /** @deprecated */
   public CMPPOPGenerationInfoSignature(String var1, GeneralName var2) throws InvalidParameterException {
      this(var1);
      if (var2 == null) {
         throw new InvalidParameterException("CMPPOPGenerationInfoSignature.CMPPOPGenerationInfoSignature: sender should not be null.");
      } else {
         this.type = 0;
         this.sender = var2;
      }
   }

   /** @deprecated */
   public CMPPOPGenerationInfoSignature(String var1, char[] var2, byte[] var3, int var4) throws InvalidParameterException {
      this(var1);
      if (var2 != null && var3 != null) {
         if (var4 <= 0) {
            var4 = 1024;
         }

         this.type = 1;
         this.sharedSecret = var2;
         this.salt = var3;
         this.iterationCount = var4;
      } else {
         throw new InvalidParameterException("CMPPOPGenerationInfoSignature.CMPPOPGenerationInfoSignature: none of algorithm, sharedSecret and salt should be null.");
      }
   }

   String getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   GeneralName getSender() throws CMPException {
      if (this.type != 0) {
         throw new CMPException("CMPPOPGenerationInfoSignature.getSignatureAlgorithm: this object represents information for a pop using PBM.");
      } else {
         return this.sender;
      }
   }

   char[] getSharedSecret() throws CMPException {
      if (this.type != 1) {
         throw new CMPException("CMPPOPGenerationInfoSignature.getSignatureAlgorithm: this object represents information for a pop using an already authenticated sender name.");
      } else {
         return this.sharedSecret;
      }
   }

   byte[] getSalt() throws CMPException {
      if (this.type != 1) {
         throw new CMPException("CMPPOPGenerationInfoSignature.getSignatureAlgorithm: this object represents information for a pop using an already authenticated sender name.");
      } else {
         return this.salt;
      }
   }

   int getIterationCount() throws CMPException {
      if (this.type != 1) {
         throw new CMPException("CMPPOPGenerationInfoSignature.getSignatureAlgorithm: this object represents information for a pop using an already authenticated sender name.");
      } else {
         return this.iterationCount;
      }
   }
}

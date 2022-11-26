package org.python.bouncycastle.jcajce.provider.symmetric.util;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class BCPBEKey implements PBEKey {
   String algorithm;
   ASN1ObjectIdentifier oid;
   int type;
   int digest;
   int keySize;
   int ivSize;
   CipherParameters param;
   PBEKeySpec pbeKeySpec;
   boolean tryWrong = false;

   public BCPBEKey(String var1, ASN1ObjectIdentifier var2, int var3, int var4, int var5, int var6, PBEKeySpec var7, CipherParameters var8) {
      this.algorithm = var1;
      this.oid = var2;
      this.type = var3;
      this.digest = var4;
      this.keySize = var5;
      this.ivSize = var6;
      this.pbeKeySpec = var7;
      this.param = var8;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "RAW";
   }

   public byte[] getEncoded() {
      if (this.param != null) {
         KeyParameter var1;
         if (this.param instanceof ParametersWithIV) {
            var1 = (KeyParameter)((ParametersWithIV)this.param).getParameters();
         } else {
            var1 = (KeyParameter)this.param;
         }

         return var1.getKey();
      } else if (this.type == 2) {
         return PBEParametersGenerator.PKCS12PasswordToBytes(this.pbeKeySpec.getPassword());
      } else {
         return this.type == 5 ? PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.pbeKeySpec.getPassword()) : PBEParametersGenerator.PKCS5PasswordToBytes(this.pbeKeySpec.getPassword());
      }
   }

   int getType() {
      return this.type;
   }

   int getDigest() {
      return this.digest;
   }

   int getKeySize() {
      return this.keySize;
   }

   public int getIvSize() {
      return this.ivSize;
   }

   public CipherParameters getParam() {
      return this.param;
   }

   public char[] getPassword() {
      return this.pbeKeySpec.getPassword();
   }

   public byte[] getSalt() {
      return this.pbeKeySpec.getSalt();
   }

   public int getIterationCount() {
      return this.pbeKeySpec.getIterationCount();
   }

   public ASN1ObjectIdentifier getOID() {
      return this.oid;
   }

   public void setTryWrongPKCS12Zero(boolean var1) {
      this.tryWrong = var1;
   }

   boolean shouldTryWrongPKCS12() {
      return this.tryWrong;
   }
}

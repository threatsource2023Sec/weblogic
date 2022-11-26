package org.python.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.jcajce.PKCS12Key;
import org.python.bouncycastle.jcajce.PKCS12KeyWithParameters;

public class BaseStreamCipher extends BaseWrapCipher implements PBE {
   private Class[] availableSpecs;
   private StreamCipher cipher;
   private int keySizeInBits;
   private int digest;
   private ParametersWithIV ivParam;
   private int ivLength;
   private PBEParameterSpec pbeSpec;
   private String pbeAlgorithm;

   protected BaseStreamCipher(StreamCipher var1, int var2) {
      this(var1, var2, -1, -1);
   }

   protected BaseStreamCipher(StreamCipher var1, int var2, int var3, int var4) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
      this.ivLength = 0;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.cipher = var1;
      this.ivLength = var2;
      this.keySizeInBits = var3;
      this.digest = var4;
   }

   protected int engineGetBlockSize() {
      return 0;
   }

   protected byte[] engineGetIV() {
      return this.ivParam != null ? this.ivParam.getIV() : null;
   }

   protected int engineGetKeySize(Key var1) {
      return var1.getEncoded().length * 8;
   }

   protected int engineGetOutputSize(int var1) {
      return var1;
   }

   protected AlgorithmParameters engineGetParameters() {
      if (this.engineParams == null && this.pbeSpec != null) {
         try {
            AlgorithmParameters var1 = this.createParametersInstance(this.pbeAlgorithm);
            var1.init(this.pbeSpec);
            return var1;
         } catch (Exception var2) {
            return null;
         }
      } else {
         return this.engineParams;
      }
   }

   protected void engineSetMode(String var1) throws NoSuchAlgorithmException {
      if (!var1.equalsIgnoreCase("ECB")) {
         throw new NoSuchAlgorithmException("can't support mode " + var1);
      }
   }

   protected void engineSetPadding(String var1) throws NoSuchPaddingException {
      if (!var1.equalsIgnoreCase("NoPadding")) {
         throw new NoSuchPaddingException("Padding " + var1 + " unknown.");
      }
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.engineParams = null;
      if (!(var2 instanceof SecretKey)) {
         throw new InvalidKeyException("Key for algorithm " + var2.getAlgorithm() + " not suitable for symmetric enryption.");
      } else {
         Object var6;
         if (var2 instanceof PKCS12Key) {
            PKCS12Key var5 = (PKCS12Key)var2;
            this.pbeSpec = (PBEParameterSpec)var3;
            if (var5 instanceof PKCS12KeyWithParameters && this.pbeSpec == null) {
               this.pbeSpec = new PBEParameterSpec(((PKCS12KeyWithParameters)var5).getSalt(), ((PKCS12KeyWithParameters)var5).getIterationCount());
            }

            var6 = PBE.Util.makePBEParameters(var5.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
         } else if (var2 instanceof BCPBEKey) {
            BCPBEKey var9 = (BCPBEKey)var2;
            if (var9.getOID() != null) {
               this.pbeAlgorithm = var9.getOID().getId();
            } else {
               this.pbeAlgorithm = var9.getAlgorithm();
            }

            if (var9.getParam() != null) {
               var6 = var9.getParam();
               this.pbeSpec = new PBEParameterSpec(var9.getSalt(), var9.getIterationCount());
            } else {
               if (!(var3 instanceof PBEParameterSpec)) {
                  throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
               }

               var6 = PBE.Util.makePBEParameters(var9, var3, this.cipher.getAlgorithmName());
               this.pbeSpec = (PBEParameterSpec)var3;
            }

            if (var9.getIvSize() != 0) {
               this.ivParam = (ParametersWithIV)var6;
            }
         } else if (var3 == null) {
            if (this.digest > 0) {
               throw new InvalidKeyException("Algorithm requires a PBE key");
            }

            var6 = new KeyParameter(var2.getEncoded());
         } else {
            if (!(var3 instanceof IvParameterSpec)) {
               throw new InvalidAlgorithmParameterException("unknown parameter type.");
            }

            var6 = new ParametersWithIV(new KeyParameter(var2.getEncoded()), ((IvParameterSpec)var3).getIV());
            this.ivParam = (ParametersWithIV)var6;
         }

         if (this.ivLength != 0 && !(var6 instanceof ParametersWithIV)) {
            SecureRandom var10 = var4;
            if (var4 == null) {
               var10 = new SecureRandom();
            }

            if (var1 != 1 && var1 != 3) {
               throw new InvalidAlgorithmParameterException("no IV set when one expected");
            }

            byte[] var7 = new byte[this.ivLength];
            var10.nextBytes(var7);
            var6 = new ParametersWithIV((CipherParameters)var6, var7);
            this.ivParam = (ParametersWithIV)var6;
         }

         try {
            switch (var1) {
               case 1:
               case 3:
                  this.cipher.init(true, (CipherParameters)var6);
                  break;
               case 2:
               case 4:
                  this.cipher.init(false, (CipherParameters)var6);
                  break;
               default:
                  throw new InvalidParameterException("unknown opmode " + var1 + " passed");
            }

         } catch (Exception var8) {
            throw new InvalidKeyException(var8.getMessage());
         }
      }
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      AlgorithmParameterSpec var5 = null;
      if (var3 != null) {
         int var6 = 0;

         while(var6 != this.availableSpecs.length) {
            try {
               var5 = var3.getParameterSpec(this.availableSpecs[var6]);
               break;
            } catch (Exception var8) {
               ++var6;
            }
         }

         if (var5 == null) {
            throw new InvalidAlgorithmParameterException("can't handle parameter " + var3.toString());
         }
      }

      this.engineInit(var1, var2, var5, var4);
      this.engineParams = var3;
   }

   protected void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      try {
         this.engineInit(var1, var2, (AlgorithmParameterSpec)null, var3);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new InvalidKeyException(var5.getMessage());
      }
   }

   protected byte[] engineUpdate(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      this.cipher.processBytes(var1, var2, var3, var4, 0);
      return var4;
   }

   protected int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException {
      if (var5 + var3 > var4.length) {
         throw new ShortBufferException("output buffer too short for input.");
      } else {
         try {
            this.cipher.processBytes(var1, var2, var3, var4, var5);
            return var3;
         } catch (DataLengthException var7) {
            throw new IllegalStateException(var7.getMessage());
         }
      }
   }

   protected byte[] engineDoFinal(byte[] var1, int var2, int var3) {
      if (var3 != 0) {
         byte[] var4 = this.engineUpdate(var1, var2, var3);
         this.cipher.reset();
         return var4;
      } else {
         this.cipher.reset();
         return new byte[0];
      }
   }

   protected int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException {
      if (var5 + var3 > var4.length) {
         throw new ShortBufferException("output buffer too short for input.");
      } else {
         if (var3 != 0) {
            this.cipher.processBytes(var1, var2, var3, var4, var5);
         }

         this.cipher.reset();
         return var3;
      }
   }
}

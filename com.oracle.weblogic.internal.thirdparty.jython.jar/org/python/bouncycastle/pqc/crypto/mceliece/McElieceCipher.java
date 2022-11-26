package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.pqc.crypto.MessageEncryptor;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.python.bouncycastle.pqc.math.linearalgebra.Vector;

public class McElieceCipher implements MessageEncryptor {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
   private SecureRandom sr;
   private int n;
   private int k;
   private int t;
   public int maxPlainTextSize;
   public int cipherTextSize;
   private McElieceKeyParameters key;
   private boolean forEncryption;

   public void init(boolean var1, CipherParameters var2) {
      this.forEncryption = var1;
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.sr = var3.getRandom();
            this.key = (McEliecePublicKeyParameters)var3.getParameters();
            this.initCipherEncrypt((McEliecePublicKeyParameters)this.key);
         } else {
            this.sr = new SecureRandom();
            this.key = (McEliecePublicKeyParameters)var2;
            this.initCipherEncrypt((McEliecePublicKeyParameters)this.key);
         }
      } else {
         this.key = (McEliecePrivateKeyParameters)var2;
         this.initCipherDecrypt((McEliecePrivateKeyParameters)this.key);
      }

   }

   public int getKeySize(McElieceKeyParameters var1) {
      if (var1 instanceof McEliecePublicKeyParameters) {
         return ((McEliecePublicKeyParameters)var1).getN();
      } else if (var1 instanceof McEliecePrivateKeyParameters) {
         return ((McEliecePrivateKeyParameters)var1).getN();
      } else {
         throw new IllegalArgumentException("unsupported type");
      }
   }

   public void initCipherEncrypt(McEliecePublicKeyParameters var1) {
      this.sr = this.sr != null ? this.sr : new SecureRandom();
      this.n = var1.getN();
      this.k = var1.getK();
      this.t = var1.getT();
      this.cipherTextSize = this.n >> 3;
      this.maxPlainTextSize = this.k >> 3;
   }

   public void initCipherDecrypt(McEliecePrivateKeyParameters var1) {
      this.n = var1.getN();
      this.k = var1.getK();
      this.maxPlainTextSize = this.k >> 3;
      this.cipherTextSize = this.n >> 3;
   }

   public byte[] messageEncrypt(byte[] var1) {
      if (!this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         GF2Vector var2 = this.computeMessageRepresentative(var1);
         GF2Vector var3 = new GF2Vector(this.n, this.t, this.sr);
         GF2Matrix var4 = ((McEliecePublicKeyParameters)this.key).getG();
         Vector var5 = var4.leftMultiply((Vector)var2);
         GF2Vector var6 = (GF2Vector)var5.add(var3);
         return var6.getEncoded();
      }
   }

   private GF2Vector computeMessageRepresentative(byte[] var1) {
      byte[] var2 = new byte[this.maxPlainTextSize + ((this.k & 7) != 0 ? 1 : 0)];
      System.arraycopy(var1, 0, var2, 0, var1.length);
      var2[var1.length] = 1;
      return GF2Vector.OS2VP(this.k, var2);
   }

   public byte[] messageDecrypt(byte[] var1) throws InvalidCipherTextException {
      if (this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         GF2Vector var2 = GF2Vector.OS2VP(this.n, var1);
         McEliecePrivateKeyParameters var3 = (McEliecePrivateKeyParameters)this.key;
         GF2mField var4 = var3.getField();
         PolynomialGF2mSmallM var5 = var3.getGoppaPoly();
         GF2Matrix var6 = var3.getSInv();
         Permutation var7 = var3.getP1();
         Permutation var8 = var3.getP2();
         GF2Matrix var9 = var3.getH();
         PolynomialGF2mSmallM[] var10 = var3.getQInv();
         Permutation var11 = var7.rightMultiply(var8);
         Permutation var12 = var11.computeInverse();
         GF2Vector var13 = (GF2Vector)var2.multiply(var12);
         GF2Vector var14 = (GF2Vector)var9.rightMultiply((Vector)var13);
         GF2Vector var15 = GoppaCode.syndromeDecode(var14, var4, var5, var10);
         GF2Vector var16 = (GF2Vector)var13.add(var15);
         var16 = (GF2Vector)var16.multiply(var7);
         var15 = (GF2Vector)var15.multiply(var11);
         GF2Vector var17 = var16.extractRightVector(this.k);
         GF2Vector var18 = (GF2Vector)var6.leftMultiply((Vector)var17);
         return this.computeMessage(var18);
      }
   }

   private byte[] computeMessage(GF2Vector var1) throws InvalidCipherTextException {
      byte[] var2 = var1.getEncoded();

      int var3;
      for(var3 = var2.length - 1; var3 >= 0 && var2[var3] == 0; --var3) {
      }

      if (var3 >= 0 && var2[var3] == 1) {
         byte[] var4 = new byte[var3];
         System.arraycopy(var2, 0, var4, 0, var3);
         return var4;
      } else {
         throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
      }
   }
}

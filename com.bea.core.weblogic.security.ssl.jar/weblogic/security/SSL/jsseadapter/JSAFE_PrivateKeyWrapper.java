package weblogic.security.SSL.jsseadapter;

import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.logging.Level;

class JSAFE_PrivateKeyWrapper implements RSAPrivateKey, RSAPrivateCrtKey {
   private JSAFE_PrivateKey key;
   BigInteger crtCoefficient;
   BigInteger primeExponentP;
   BigInteger primeExponentQ;
   BigInteger primeP;
   BigInteger primeQ;
   BigInteger publicExponent;
   BigInteger modulus;

   public BigInteger getCrtCoefficient() {
      return this.crtCoefficient;
   }

   public BigInteger getPrimeExponentP() {
      return this.primeExponentP;
   }

   public BigInteger getPrimeExponentQ() {
      return this.primeExponentQ;
   }

   public BigInteger getPrimeP() {
      return this.primeP;
   }

   public BigInteger getPrimeQ() {
      return this.primeQ;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   private byte[] ensurePositiveUnsigned(byte[] valueIn) {
      if ((valueIn[0] & 128) == 0) {
         return valueIn;
      } else {
         byte[] newValue = new byte[valueIn.length + 1];
         newValue[0] = 0;
         System.arraycopy(valueIn, 0, newValue, 1, valueIn.length);
         return newValue;
      }
   }

   private void initFromKey() throws JSAFE_UnimplementedException {
      byte[][] keyData = this.key.getKeyData("RSAPrivateKeyCRT");
      this.modulus = new BigInteger(this.ensurePositiveUnsigned(keyData[0]));
      this.publicExponent = new BigInteger(this.ensurePositiveUnsigned(keyData[1]));
      this.primeP = new BigInteger(this.ensurePositiveUnsigned(keyData[3]));
      this.primeQ = new BigInteger(this.ensurePositiveUnsigned(keyData[4]));
      this.primeExponentP = new BigInteger(this.ensurePositiveUnsigned(keyData[5]));
      this.primeExponentQ = new BigInteger(this.ensurePositiveUnsigned(keyData[6]));
      this.crtCoefficient = new BigInteger(this.ensurePositiveUnsigned(keyData[7]));
   }

   public boolean equals(Object obj) {
      return obj == this || obj instanceof JSAFE_PrivateKeyWrapper && this.key.equals(((JSAFE_PrivateKeyWrapper)obj).key);
   }

   public int hashCode() {
      int hashcode = this.crtCoefficient.hashCode();
      hashcode = 31 * hashcode + this.primeExponentP.hashCode();
      hashcode = 31 * hashcode + this.primeExponentQ.hashCode();
      hashcode = 31 * hashcode + this.primeP.hashCode();
      hashcode = 31 * hashcode + this.primeQ.hashCode();
      hashcode = 31 * hashcode + this.publicExponent.hashCode();
      hashcode = 31 * hashcode + this.modulus.hashCode();
      return hashcode;
   }

   public JSAFE_PrivateKeyWrapper(byte[] PKCS8EncodedPrivateKey) {
      try {
         this.key = JSAFE_PrivateKey.getInstance(PKCS8EncodedPrivateKey, 0, "Java");
         this.initFromKey();
      } catch (JSAFE_UnimplementedException var3) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "Error instantiating PrivateKey object: ", var3.getMessage());
         }

         throw new IllegalStateException(var3.getMessage());
      }
   }

   public JSAFE_PrivateKeyWrapper(BigInteger n, BigInteger e, BigInteger d, BigInteger p, BigInteger q, BigInteger dp, BigInteger dq, BigInteger qinv) {
      try {
         this.key = JSAFE_PrivateKey.getInstance("RSA", "Java");
         this.key.setKeyData(new byte[][]{n.toByteArray(), e.toByteArray(), d.toByteArray(), p.toByteArray(), q.toByteArray(), dp.toByteArray(), dq.toByteArray(), qinv.toByteArray()});
         this.initFromKey();
      } catch (JSAFE_UnimplementedException var10) {
         throw new IllegalStateException(var10.getMessage());
      } catch (JSAFE_InvalidKeyException var11) {
         throw new IllegalStateException(var11.getMessage());
      } catch (JSAFE_InvalidParameterException var12) {
         throw new IllegalStateException(var12.getMessage());
      }
   }

   public JSAFE_PrivateKeyWrapper(JSAFE_PrivateKey key) {
      this.key = key;

      try {
         this.initFromKey();
      } catch (JSAFE_UnimplementedException var3) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "Error setting PrivateKey object: ", var3.getMessage());
         }

         throw new IllegalStateException(var3.getMessage());
      }
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPrivateExponent() {
      byte[][] keyData = this.key.getKeyData();
      return new BigInteger(keyData[1]);
   }

   public JSAFE_PrivateKey getKey() {
      return this.key;
   }

   public String getAlgorithm() {
      return "RSA";
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      try {
         return this.key.getKeyData("RSAPrivateKeyBER")[0];
      } catch (JSAFE_UnimplementedException var2) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "Error getting encoded value of the PrivateKey object: ", var2.getMessage());
         }

         throw new IllegalStateException();
      }
   }
}

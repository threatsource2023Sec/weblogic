package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.util.Strings;

public class BCRSAPublicKey implements RSAPublicKey {
   private static final AlgorithmIdentifier DEFAULT_ALGORITHM_IDENTIFIER;
   static final long serialVersionUID = 2675817738516720772L;
   private BigInteger modulus;
   private BigInteger publicExponent;
   private transient AlgorithmIdentifier algorithmIdentifier;

   BCRSAPublicKey(RSAKeyParameters var1) {
      this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getExponent();
   }

   BCRSAPublicKey(RSAPublicKeySpec var1) {
      this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getPublicExponent();
   }

   BCRSAPublicKey(RSAPublicKey var1) {
      this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getPublicExponent();
   }

   BCRSAPublicKey(SubjectPublicKeyInfo var1) {
      this.populateFromPublicKeyInfo(var1);
   }

   private void populateFromPublicKeyInfo(SubjectPublicKeyInfo var1) {
      try {
         org.python.bouncycastle.asn1.pkcs.RSAPublicKey var2 = org.python.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(var1.parsePublicKey());
         this.algorithmIdentifier = var1.getAlgorithm();
         this.modulus = var2.getModulus();
         this.publicExponent = var2.getPublicExponent();
      } catch (IOException var3) {
         throw new IllegalArgumentException("invalid info structure in RSA public key");
      }
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }

   public String getAlgorithm() {
      return "RSA";
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      return KeyUtil.getEncodedSubjectPublicKeyInfo(this.algorithmIdentifier, (ASN1Encodable)(new org.python.bouncycastle.asn1.pkcs.RSAPublicKey(this.getModulus(), this.getPublicExponent())));
   }

   public int hashCode() {
      return this.getModulus().hashCode() ^ this.getPublicExponent().hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof RSAPublicKey)) {
         return false;
      } else {
         RSAPublicKey var2 = (RSAPublicKey)var1;
         return this.getModulus().equals(var2.getModulus()) && this.getPublicExponent().equals(var2.getPublicExponent());
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("RSA Public Key").append(var2);
      var1.append("            modulus: ").append(this.getModulus().toString(16)).append(var2);
      var1.append("    public exponent: ").append(this.getPublicExponent().toString(16)).append(var2);
      return var1.toString();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();

      try {
         this.algorithmIdentifier = AlgorithmIdentifier.getInstance(var1.readObject());
      } catch (Exception var3) {
         this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      if (!this.algorithmIdentifier.equals(DEFAULT_ALGORITHM_IDENTIFIER)) {
         var1.writeObject(this.algorithmIdentifier.getEncoded());
      }

   }

   static {
      DEFAULT_ALGORITHM_IDENTIFIER = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
   }
}

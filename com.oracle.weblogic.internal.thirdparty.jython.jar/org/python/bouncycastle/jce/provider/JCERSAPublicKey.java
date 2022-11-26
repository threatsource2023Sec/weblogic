package org.python.bouncycastle.jce.provider;

import java.io.IOException;
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

public class JCERSAPublicKey implements RSAPublicKey {
   static final long serialVersionUID = 2675817738516720772L;
   private BigInteger modulus;
   private BigInteger publicExponent;

   JCERSAPublicKey(RSAKeyParameters var1) {
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getExponent();
   }

   JCERSAPublicKey(RSAPublicKeySpec var1) {
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getPublicExponent();
   }

   JCERSAPublicKey(RSAPublicKey var1) {
      this.modulus = var1.getModulus();
      this.publicExponent = var1.getPublicExponent();
   }

   JCERSAPublicKey(SubjectPublicKeyInfo var1) {
      try {
         org.python.bouncycastle.asn1.pkcs.RSAPublicKey var2 = org.python.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(var1.parsePublicKey());
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
      return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), (ASN1Encodable)(new org.python.bouncycastle.asn1.pkcs.RSAPublicKey(this.getModulus(), this.getPublicExponent())));
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
}

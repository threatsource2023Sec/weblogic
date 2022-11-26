package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class JCERSAPrivateKey implements RSAPrivateKey, PKCS12BagAttributeCarrier {
   static final long serialVersionUID = 5110188922551353628L;
   private static BigInteger ZERO = BigInteger.valueOf(0L);
   protected BigInteger modulus;
   protected BigInteger privateExponent;
   private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected JCERSAPrivateKey() {
   }

   JCERSAPrivateKey(RSAKeyParameters var1) {
      this.modulus = var1.getModulus();
      this.privateExponent = var1.getExponent();
   }

   JCERSAPrivateKey(RSAPrivateKeySpec var1) {
      this.modulus = var1.getModulus();
      this.privateExponent = var1.getPrivateExponent();
   }

   JCERSAPrivateKey(RSAPrivateKey var1) {
      this.modulus = var1.getModulus();
      this.privateExponent = var1.getPrivateExponent();
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPrivateExponent() {
      return this.privateExponent;
   }

   public String getAlgorithm() {
      return "RSA";
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new org.python.bouncycastle.asn1.pkcs.RSAPrivateKey(this.getModulus(), ZERO, this.getPrivateExponent(), ZERO, ZERO, ZERO, ZERO, ZERO));
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof RSAPrivateKey)) {
         return false;
      } else if (var1 == this) {
         return true;
      } else {
         RSAPrivateKey var2 = (RSAPrivateKey)var1;
         return this.getModulus().equals(var2.getModulus()) && this.getPrivateExponent().equals(var2.getPrivateExponent());
      }
   }

   public int hashCode() {
      return this.getModulus().hashCode() ^ this.getPrivateExponent().hashCode();
   }

   public void setBagAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }

   public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      this.modulus = (BigInteger)var1.readObject();
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier.readObject(var1);
      this.privateExponent = (BigInteger)var1.readObject();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeObject(this.modulus);
      this.attrCarrier.writeObject(var1);
      var1.writeObject(this.privateExponent);
   }
}

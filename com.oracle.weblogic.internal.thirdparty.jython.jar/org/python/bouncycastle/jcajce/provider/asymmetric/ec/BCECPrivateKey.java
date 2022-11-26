package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCECPrivateKey implements ECPrivateKey, org.python.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
   static final long serialVersionUID = 994553197664784084L;
   private String algorithm = "EC";
   private boolean withCompression;
   private transient BigInteger d;
   private transient ECParameterSpec ecSpec;
   private transient ProviderConfiguration configuration;
   private transient DERBitString publicKey;
   private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected BCECPrivateKey() {
   }

   public BCECPrivateKey(ECPrivateKey var1, ProviderConfiguration var2) {
      this.d = var1.getS();
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
      this.configuration = var2;
   }

   public BCECPrivateKey(String var1, ECPrivateKeySpec var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.d = var2.getD();
      if (var2.getParams() != null) {
         ECCurve var4 = var2.getParams().getCurve();
         EllipticCurve var5 = EC5Util.convertCurve(var4, var2.getParams().getSeed());
         this.ecSpec = EC5Util.convertSpec(var5, var2.getParams());
      } else {
         this.ecSpec = null;
      }

      this.configuration = var3;
   }

   public BCECPrivateKey(String var1, java.security.spec.ECPrivateKeySpec var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.d = var2.getS();
      this.ecSpec = var2.getParams();
      this.configuration = var3;
   }

   public BCECPrivateKey(String var1, BCECPrivateKey var2) {
      this.algorithm = var1;
      this.d = var2.d;
      this.ecSpec = var2.ecSpec;
      this.withCompression = var2.withCompression;
      this.attrCarrier = var2.attrCarrier;
      this.publicKey = var2.publicKey;
      this.configuration = var2.configuration;
   }

   public BCECPrivateKey(String var1, ECPrivateKeyParameters var2, BCECPublicKey var3, ECParameterSpec var4, ProviderConfiguration var5) {
      ECDomainParameters var6 = var2.getParameters();
      this.algorithm = var1;
      this.d = var2.getD();
      this.configuration = var5;
      if (var4 == null) {
         EllipticCurve var7 = EC5Util.convertCurve(var6.getCurve(), var6.getSeed());
         this.ecSpec = new ECParameterSpec(var7, new ECPoint(var6.getG().getAffineXCoord().toBigInteger(), var6.getG().getAffineYCoord().toBigInteger()), var6.getN(), var6.getH().intValue());
      } else {
         this.ecSpec = var4;
      }

      this.publicKey = this.getPublicKeyDetails(var3);
   }

   public BCECPrivateKey(String var1, ECPrivateKeyParameters var2, BCECPublicKey var3, org.python.bouncycastle.jce.spec.ECParameterSpec var4, ProviderConfiguration var5) {
      ECDomainParameters var6 = var2.getParameters();
      this.algorithm = var1;
      this.d = var2.getD();
      this.configuration = var5;
      EllipticCurve var7;
      if (var4 == null) {
         var7 = EC5Util.convertCurve(var6.getCurve(), var6.getSeed());
         this.ecSpec = new ECParameterSpec(var7, new ECPoint(var6.getG().getAffineXCoord().toBigInteger(), var6.getG().getAffineYCoord().toBigInteger()), var6.getN(), var6.getH().intValue());
      } else {
         var7 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = EC5Util.convertSpec(var7, var4);
      }

      try {
         this.publicKey = this.getPublicKeyDetails(var3);
      } catch (Exception var8) {
         this.publicKey = null;
      }

   }

   public BCECPrivateKey(String var1, ECPrivateKeyParameters var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.d = var2.getD();
      this.ecSpec = null;
      this.configuration = var3;
   }

   BCECPrivateKey(String var1, PrivateKeyInfo var2, ProviderConfiguration var3) throws IOException {
      this.algorithm = var1;
      this.configuration = var3;
      this.populateFromPrivKeyInfo(var2);
   }

   private void populateFromPrivKeyInfo(PrivateKeyInfo var1) throws IOException {
      X962Parameters var2 = X962Parameters.getInstance(var1.getPrivateKeyAlgorithm().getParameters());
      ECCurve var3 = EC5Util.getCurve(this.configuration, var2);
      this.ecSpec = EC5Util.convertToSpec(var2, var3);
      ASN1Encodable var4 = var1.parsePrivateKey();
      if (var4 instanceof ASN1Integer) {
         ASN1Integer var5 = ASN1Integer.getInstance(var4);
         this.d = var5.getValue();
      } else {
         org.python.bouncycastle.asn1.sec.ECPrivateKey var6 = org.python.bouncycastle.asn1.sec.ECPrivateKey.getInstance(var4);
         this.d = var6.getKey();
         this.publicKey = var6.getPublicKey();
      }

   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      X962Parameters var1 = ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
      int var2;
      if (this.ecSpec == null) {
         var2 = ECUtil.getOrderBitLength(this.configuration, (BigInteger)null, this.getS());
      } else {
         var2 = ECUtil.getOrderBitLength(this.configuration, this.ecSpec.getOrder(), this.getS());
      }

      org.python.bouncycastle.asn1.sec.ECPrivateKey var3;
      if (this.publicKey != null) {
         var3 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var2, this.getS(), this.publicKey, var1);
      } else {
         var3 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var2, this.getS(), var1);
      }

      try {
         PrivateKeyInfo var4 = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var1), var3);
         return var4.getEncoded("DER");
      } catch (IOException var6) {
         return null;
      }
   }

   public ECParameterSpec getParams() {
      return this.ecSpec;
   }

   public org.python.bouncycastle.jce.spec.ECParameterSpec getParameters() {
      return this.ecSpec == null ? null : EC5Util.convertSpec(this.ecSpec, this.withCompression);
   }

   org.python.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
      return this.ecSpec != null ? EC5Util.convertSpec(this.ecSpec, this.withCompression) : this.configuration.getEcImplicitlyCa();
   }

   public BigInteger getS() {
      return this.d;
   }

   public BigInteger getD() {
      return this.d;
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

   public void setPointFormat(String var1) {
      this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(var1);
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof BCECPrivateKey)) {
         return false;
      } else {
         BCECPrivateKey var2 = (BCECPrivateKey)var1;
         return this.getD().equals(var2.getD()) && this.engineGetSpec().equals(var2.engineGetSpec());
      }
   }

   public int hashCode() {
      return this.getD().hashCode() ^ this.engineGetSpec().hashCode();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("EC Private Key").append(var2);
      var1.append("             S: ").append(this.d.toString(16)).append(var2);
      return var1.toString();
   }

   private DERBitString getPublicKeyDetails(BCECPublicKey var1) {
      try {
         SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var1.getEncoded()));
         return var2.getPublicKeyData();
      } catch (IOException var3) {
         return null;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      byte[] var2 = (byte[])((byte[])var1.readObject());
      this.configuration = BouncyCastleProvider.CONFIGURATION;
      this.populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.getEncoded());
   }
}

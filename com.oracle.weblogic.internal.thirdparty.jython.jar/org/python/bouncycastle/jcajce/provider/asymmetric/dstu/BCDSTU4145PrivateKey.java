package org.python.bouncycastle.jcajce.provider.asymmetric.dstu;

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
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.ua.DSTU4145NamedCurves;
import org.python.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCDSTU4145PrivateKey implements ECPrivateKey, org.python.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
   static final long serialVersionUID = 7245981689601667138L;
   private String algorithm = "DSTU4145";
   private boolean withCompression;
   private transient BigInteger d;
   private transient ECParameterSpec ecSpec;
   private transient DERBitString publicKey;
   private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected BCDSTU4145PrivateKey() {
   }

   public BCDSTU4145PrivateKey(ECPrivateKey var1) {
      this.d = var1.getS();
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
   }

   public BCDSTU4145PrivateKey(ECPrivateKeySpec var1) {
      this.d = var1.getD();
      if (var1.getParams() != null) {
         ECCurve var2 = var1.getParams().getCurve();
         EllipticCurve var3 = EC5Util.convertCurve(var2, var1.getParams().getSeed());
         this.ecSpec = EC5Util.convertSpec(var3, var1.getParams());
      } else {
         this.ecSpec = null;
      }

   }

   public BCDSTU4145PrivateKey(java.security.spec.ECPrivateKeySpec var1) {
      this.d = var1.getS();
      this.ecSpec = var1.getParams();
   }

   public BCDSTU4145PrivateKey(BCDSTU4145PrivateKey var1) {
      this.d = var1.d;
      this.ecSpec = var1.ecSpec;
      this.withCompression = var1.withCompression;
      this.attrCarrier = var1.attrCarrier;
      this.publicKey = var1.publicKey;
   }

   public BCDSTU4145PrivateKey(String var1, ECPrivateKeyParameters var2, BCDSTU4145PublicKey var3, ECParameterSpec var4) {
      ECDomainParameters var5 = var2.getParameters();
      this.algorithm = var1;
      this.d = var2.getD();
      if (var4 == null) {
         EllipticCurve var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
         this.ecSpec = new ECParameterSpec(var6, new ECPoint(var5.getG().getAffineXCoord().toBigInteger(), var5.getG().getAffineYCoord().toBigInteger()), var5.getN(), var5.getH().intValue());
      } else {
         this.ecSpec = var4;
      }

      this.publicKey = this.getPublicKeyDetails(var3);
   }

   public BCDSTU4145PrivateKey(String var1, ECPrivateKeyParameters var2, BCDSTU4145PublicKey var3, org.python.bouncycastle.jce.spec.ECParameterSpec var4) {
      ECDomainParameters var5 = var2.getParameters();
      this.algorithm = var1;
      this.d = var2.getD();
      EllipticCurve var6;
      if (var4 == null) {
         var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
         this.ecSpec = new ECParameterSpec(var6, new ECPoint(var5.getG().getAffineXCoord().toBigInteger(), var5.getG().getAffineYCoord().toBigInteger()), var5.getN(), var5.getH().intValue());
      } else {
         var6 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = new ECParameterSpec(var6, new ECPoint(var4.getG().getAffineXCoord().toBigInteger(), var4.getG().getAffineYCoord().toBigInteger()), var4.getN(), var4.getH().intValue());
      }

      this.publicKey = this.getPublicKeyDetails(var3);
   }

   public BCDSTU4145PrivateKey(String var1, ECPrivateKeyParameters var2) {
      this.algorithm = var1;
      this.d = var2.getD();
      this.ecSpec = null;
   }

   BCDSTU4145PrivateKey(PrivateKeyInfo var1) throws IOException {
      this.populateFromPrivKeyInfo(var1);
   }

   private void populateFromPrivKeyInfo(PrivateKeyInfo var1) throws IOException {
      X962Parameters var2 = new X962Parameters((ASN1Primitive)var1.getPrivateKeyAlgorithm().getParameters());
      if (var2.isNamedCurve()) {
         ASN1ObjectIdentifier var3 = ASN1ObjectIdentifier.getInstance(var2.getParameters());
         X9ECParameters var4 = ECUtil.getNamedCurveByOid(var3);
         if (var4 == null) {
            ECDomainParameters var5 = DSTU4145NamedCurves.getByOID(var3);
            EllipticCurve var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
            this.ecSpec = new ECNamedCurveSpec(var3.getId(), var6, new ECPoint(var5.getG().getAffineXCoord().toBigInteger(), var5.getG().getAffineYCoord().toBigInteger()), var5.getN(), var5.getH());
         } else {
            EllipticCurve var12 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
            this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(var3), var12, new ECPoint(var4.getG().getAffineXCoord().toBigInteger(), var4.getG().getAffineYCoord().toBigInteger()), var4.getN(), var4.getH());
         }
      } else if (var2.isImplicitlyCA()) {
         this.ecSpec = null;
      } else {
         X9ECParameters var7 = X9ECParameters.getInstance(var2.getParameters());
         EllipticCurve var9 = EC5Util.convertCurve(var7.getCurve(), var7.getSeed());
         this.ecSpec = new ECParameterSpec(var9, new ECPoint(var7.getG().getAffineXCoord().toBigInteger(), var7.getG().getAffineYCoord().toBigInteger()), var7.getN(), var7.getH().intValue());
      }

      ASN1Encodable var8 = var1.parsePrivateKey();
      if (var8 instanceof ASN1Integer) {
         ASN1Integer var10 = ASN1Integer.getInstance(var8);
         this.d = var10.getValue();
      } else {
         org.python.bouncycastle.asn1.sec.ECPrivateKey var11 = org.python.bouncycastle.asn1.sec.ECPrivateKey.getInstance(var8);
         this.d = var11.getKey();
         this.publicKey = var11.getPublicKey();
      }

   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      X962Parameters var2;
      int var3;
      if (this.ecSpec instanceof ECNamedCurveSpec) {
         ASN1ObjectIdentifier var1 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
         if (var1 == null) {
            var1 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
         }

         var2 = new X962Parameters(var1);
         var3 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, this.ecSpec.getOrder(), this.getS());
      } else if (this.ecSpec == null) {
         var2 = new X962Parameters(DERNull.INSTANCE);
         var3 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, (BigInteger)null, this.getS());
      } else {
         ECCurve var7 = EC5Util.convertCurve(this.ecSpec.getCurve());
         X9ECParameters var4 = new X9ECParameters(var7, EC5Util.convertPoint(var7, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
         var2 = new X962Parameters(var4);
         var3 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, this.ecSpec.getOrder(), this.getS());
      }

      org.python.bouncycastle.asn1.sec.ECPrivateKey var9;
      if (this.publicKey != null) {
         var9 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var3, this.getS(), this.publicKey, var2);
      } else {
         var9 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var3, this.getS(), var2);
      }

      try {
         PrivateKeyInfo var8;
         if (this.algorithm.equals("DSTU4145")) {
            var8 = new PrivateKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, var2.toASN1Primitive()), var9.toASN1Primitive());
         } else {
            var8 = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var2.toASN1Primitive()), var9.toASN1Primitive());
         }

         return var8.getEncoded("DER");
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
      return this.ecSpec != null ? EC5Util.convertSpec(this.ecSpec, this.withCompression) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
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
      if (!(var1 instanceof BCDSTU4145PrivateKey)) {
         return false;
      } else {
         BCDSTU4145PrivateKey var2 = (BCDSTU4145PrivateKey)var1;
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

   private DERBitString getPublicKeyDetails(BCDSTU4145PublicKey var1) {
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
      this.populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.getEncoded());
   }
}

package org.python.bouncycastle.jce.provider;

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
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.sec.ECPrivateKeyStructure;
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
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class JCEECPrivateKey implements ECPrivateKey, org.python.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
   private String algorithm = "EC";
   private BigInteger d;
   private ECParameterSpec ecSpec;
   private boolean withCompression;
   private DERBitString publicKey;
   private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected JCEECPrivateKey() {
   }

   public JCEECPrivateKey(ECPrivateKey var1) {
      this.d = var1.getS();
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
   }

   public JCEECPrivateKey(String var1, ECPrivateKeySpec var2) {
      this.algorithm = var1;
      this.d = var2.getD();
      if (var2.getParams() != null) {
         ECCurve var3 = var2.getParams().getCurve();
         EllipticCurve var4 = EC5Util.convertCurve(var3, var2.getParams().getSeed());
         this.ecSpec = EC5Util.convertSpec(var4, var2.getParams());
      } else {
         this.ecSpec = null;
      }

   }

   public JCEECPrivateKey(String var1, java.security.spec.ECPrivateKeySpec var2) {
      this.algorithm = var1;
      this.d = var2.getS();
      this.ecSpec = var2.getParams();
   }

   public JCEECPrivateKey(String var1, JCEECPrivateKey var2) {
      this.algorithm = var1;
      this.d = var2.d;
      this.ecSpec = var2.ecSpec;
      this.withCompression = var2.withCompression;
      this.attrCarrier = var2.attrCarrier;
      this.publicKey = var2.publicKey;
   }

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2, JCEECPublicKey var3, ECParameterSpec var4) {
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

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2, JCEECPublicKey var3, org.python.bouncycastle.jce.spec.ECParameterSpec var4) {
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

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2) {
      this.algorithm = var1;
      this.d = var2.getD();
      this.ecSpec = null;
   }

   JCEECPrivateKey(PrivateKeyInfo var1) throws IOException {
      this.populateFromPrivKeyInfo(var1);
   }

   private void populateFromPrivKeyInfo(PrivateKeyInfo var1) throws IOException {
      X962Parameters var2 = new X962Parameters((ASN1Primitive)var1.getPrivateKeyAlgorithm().getParameters());
      if (var2.isNamedCurve()) {
         ASN1ObjectIdentifier var3 = ASN1ObjectIdentifier.getInstance(var2.getParameters());
         X9ECParameters var4 = ECUtil.getNamedCurveByOid(var3);
         if (var4 == null) {
            ECDomainParameters var5 = ECGOST3410NamedCurves.getByOID(var3);
            EllipticCurve var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
            this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(var3), var6, new ECPoint(var5.getG().getAffineXCoord().toBigInteger(), var5.getG().getAffineYCoord().toBigInteger()), var5.getN(), var5.getH());
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
         ECPrivateKeyStructure var11 = new ECPrivateKeyStructure((ASN1Sequence)var8);
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
      if (this.ecSpec instanceof ECNamedCurveSpec) {
         ASN1ObjectIdentifier var1 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
         if (var1 == null) {
            var1 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
         }

         var2 = new X962Parameters(var1);
      } else if (this.ecSpec == null) {
         var2 = new X962Parameters(DERNull.INSTANCE);
      } else {
         ECCurve var6 = EC5Util.convertCurve(this.ecSpec.getCurve());
         X9ECParameters var3 = new X9ECParameters(var6, EC5Util.convertPoint(var6, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
         var2 = new X962Parameters(var3);
      }

      ECPrivateKeyStructure var8;
      if (this.publicKey != null) {
         var8 = new ECPrivateKeyStructure(this.getS(), this.publicKey, var2);
      } else {
         var8 = new ECPrivateKeyStructure(this.getS(), var2);
      }

      try {
         PrivateKeyInfo var7;
         if (this.algorithm.equals("ECGOST3410")) {
            var7 = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, var2.toASN1Primitive()), var8.toASN1Primitive());
         } else {
            var7 = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var2.toASN1Primitive()), var8.toASN1Primitive());
         }

         return var7.getEncoded("DER");
      } catch (IOException var5) {
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
      if (!(var1 instanceof JCEECPrivateKey)) {
         return false;
      } else {
         JCEECPrivateKey var2 = (JCEECPrivateKey)var1;
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

   private DERBitString getPublicKeyDetails(JCEECPublicKey var1) {
      try {
         SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var1.getEncoded()));
         return var2.getPublicKeyData();
      } catch (IOException var3) {
         return null;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      byte[] var2 = (byte[])((byte[])var1.readObject());
      this.populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
      this.algorithm = (String)var1.readObject();
      this.withCompression = var1.readBoolean();
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier.readObject(var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeObject(this.getEncoded());
      var1.writeObject(this.algorithm);
      var1.writeBoolean(this.withCompression);
      this.attrCarrier.writeObject(var1);
   }
}

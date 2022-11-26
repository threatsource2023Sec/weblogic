package org.python.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.ua.DSTU4145BinaryField;
import org.python.bouncycastle.asn1.ua.DSTU4145ECBinary;
import org.python.bouncycastle.asn1.ua.DSTU4145NamedCurves;
import org.python.bouncycastle.asn1.ua.DSTU4145Params;
import org.python.bouncycastle.asn1.ua.DSTU4145PointEncoder;
import org.python.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCDSTU4145PublicKey implements ECPublicKey, org.python.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
   static final long serialVersionUID = 7026240464295649314L;
   private String algorithm = "DSTU4145";
   private boolean withCompression;
   private transient ECPublicKeyParameters ecPublicKey;
   private transient ECParameterSpec ecSpec;
   private transient DSTU4145Params dstuParams;

   public BCDSTU4145PublicKey(BCDSTU4145PublicKey var1) {
      this.ecPublicKey = var1.ecPublicKey;
      this.ecSpec = var1.ecSpec;
      this.withCompression = var1.withCompression;
      this.dstuParams = var1.dstuParams;
   }

   public BCDSTU4145PublicKey(ECPublicKeySpec var1) {
      this.ecSpec = var1.getParams();
      this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, var1.getW(), false), EC5Util.getDomainParameters((ProviderConfiguration)null, this.ecSpec));
   }

   public BCDSTU4145PublicKey(org.python.bouncycastle.jce.spec.ECPublicKeySpec var1, ProviderConfiguration var2) {
      if (var1.getParams() != null) {
         ECCurve var3 = var1.getParams().getCurve();
         EllipticCurve var4 = EC5Util.convertCurve(var3, var1.getParams().getSeed());
         this.ecPublicKey = new ECPublicKeyParameters(var1.getQ(), ECUtil.getDomainParameters(var2, var1.getParams()));
         this.ecSpec = EC5Util.convertSpec(var4, var1.getParams());
      } else {
         org.python.bouncycastle.jce.spec.ECParameterSpec var5 = var2.getEcImplicitlyCa();
         this.ecPublicKey = new ECPublicKeyParameters(var5.getCurve().createPoint(var1.getQ().getAffineXCoord().toBigInteger(), var1.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(var2, (ECParameterSpec)null));
         this.ecSpec = null;
      }

   }

   public BCDSTU4145PublicKey(String var1, ECPublicKeyParameters var2, ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      this.ecPublicKey = var2;
      if (var3 == null) {
         EllipticCurve var5 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = this.createSpec(var5, var4);
      } else {
         this.ecSpec = var3;
      }

   }

   public BCDSTU4145PublicKey(String var1, ECPublicKeyParameters var2, org.python.bouncycastle.jce.spec.ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      EllipticCurve var5;
      if (var3 == null) {
         var5 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = this.createSpec(var5, var4);
      } else {
         var5 = EC5Util.convertCurve(var3.getCurve(), var3.getSeed());
         this.ecSpec = EC5Util.convertSpec(var5, var3);
      }

      this.ecPublicKey = var2;
   }

   public BCDSTU4145PublicKey(String var1, ECPublicKeyParameters var2) {
      this.algorithm = var1;
      this.ecPublicKey = var2;
      this.ecSpec = null;
   }

   private ECParameterSpec createSpec(EllipticCurve var1, ECDomainParameters var2) {
      return new ECParameterSpec(var1, new ECPoint(var2.getG().getAffineXCoord().toBigInteger(), var2.getG().getAffineYCoord().toBigInteger()), var2.getN(), var2.getH().intValue());
   }

   BCDSTU4145PublicKey(SubjectPublicKeyInfo var1) {
      this.populateFromPubKeyInfo(var1);
   }

   private void reverseBytes(byte[] var1) {
      for(int var2 = 0; var2 < var1.length / 2; ++var2) {
         byte var3 = var1[var2];
         var1[var2] = var1[var1.length - 1 - var2];
         var1[var1.length - 1 - var2] = var3;
      }

   }

   private void populateFromPubKeyInfo(SubjectPublicKeyInfo var1) {
      DERBitString var2 = var1.getPublicKeyData();
      this.algorithm = "DSTU4145";

      ASN1OctetString var3;
      try {
         var3 = (ASN1OctetString)ASN1Primitive.fromByteArray(var2.getBytes());
      } catch (IOException var11) {
         throw new IllegalArgumentException("error recovering public key");
      }

      byte[] var4 = var3.getOctets();
      if (var1.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le)) {
         this.reverseBytes(var4);
      }

      this.dstuParams = DSTU4145Params.getInstance((ASN1Sequence)var1.getAlgorithm().getParameters());
      Object var5 = null;
      if (this.dstuParams.isNamedCurve()) {
         ASN1ObjectIdentifier var6 = this.dstuParams.getNamedCurve();
         ECDomainParameters var7 = DSTU4145NamedCurves.getByOID(var6);
         var5 = new ECNamedCurveParameterSpec(var6.getId(), var7.getCurve(), var7.getG(), var7.getN(), var7.getH(), var7.getSeed());
      } else {
         DSTU4145ECBinary var12 = this.dstuParams.getECBinary();
         byte[] var14 = var12.getB();
         if (var1.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le)) {
            this.reverseBytes(var14);
         }

         DSTU4145BinaryField var8 = var12.getField();
         ECCurve.F2m var9 = new ECCurve.F2m(var8.getM(), var8.getK1(), var8.getK2(), var8.getK3(), var12.getA(), new BigInteger(1, var14));
         byte[] var10 = var12.getG();
         if (var1.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le)) {
            this.reverseBytes(var10);
         }

         var5 = new org.python.bouncycastle.jce.spec.ECParameterSpec(var9, DSTU4145PointEncoder.decodePoint(var9, var10), var12.getN());
      }

      ECCurve var13 = ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getCurve();
      EllipticCurve var15 = EC5Util.convertCurve(var13, ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getSeed());
      if (this.dstuParams.isNamedCurve()) {
         this.ecSpec = new ECNamedCurveSpec(this.dstuParams.getNamedCurve().getId(), var15, new ECPoint(((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getG().getAffineXCoord().toBigInteger(), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getG().getAffineYCoord().toBigInteger()), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getN(), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getH());
      } else {
         this.ecSpec = new ECParameterSpec(var15, new ECPoint(((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getG().getAffineXCoord().toBigInteger(), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getG().getAffineYCoord().toBigInteger()), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getN(), ((org.python.bouncycastle.jce.spec.ECParameterSpec)var5).getH().intValue());
      }

      this.ecPublicKey = new ECPublicKeyParameters(DSTU4145PointEncoder.decodePoint(var13, var4), EC5Util.getDomainParameters((ProviderConfiguration)null, this.ecSpec));
   }

   public byte[] getSbox() {
      return null != this.dstuParams ? this.dstuParams.getDKE() : DSTU4145Params.getDefaultDKE();
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      Object var1;
      if (this.dstuParams != null) {
         var1 = this.dstuParams;
      } else if (this.ecSpec instanceof ECNamedCurveSpec) {
         var1 = new DSTU4145Params(new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName()));
      } else {
         ECCurve var2 = EC5Util.convertCurve(this.ecSpec.getCurve());
         X9ECParameters var3 = new X9ECParameters(var2, EC5Util.convertPoint(var2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
         var1 = new X962Parameters(var3);
      }

      byte[] var6 = DSTU4145PointEncoder.encodePoint(this.ecPublicKey.getQ());

      SubjectPublicKeyInfo var4;
      try {
         var4 = new SubjectPublicKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, (ASN1Encodable)var1), new DEROctetString(var6));
      } catch (IOException var5) {
         return null;
      }

      return KeyUtil.getEncodedSubjectPublicKeyInfo(var4);
   }

   public ECParameterSpec getParams() {
      return this.ecSpec;
   }

   public org.python.bouncycastle.jce.spec.ECParameterSpec getParameters() {
      return this.ecSpec == null ? null : EC5Util.convertSpec(this.ecSpec, this.withCompression);
   }

   public ECPoint getW() {
      org.python.bouncycastle.math.ec.ECPoint var1 = this.ecPublicKey.getQ();
      return new ECPoint(var1.getAffineXCoord().toBigInteger(), var1.getAffineYCoord().toBigInteger());
   }

   public org.python.bouncycastle.math.ec.ECPoint getQ() {
      org.python.bouncycastle.math.ec.ECPoint var1 = this.ecPublicKey.getQ();
      return this.ecSpec == null ? var1.getDetachedPoint() : var1;
   }

   ECPublicKeyParameters engineGetKeyParameters() {
      return this.ecPublicKey;
   }

   org.python.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
      return this.ecSpec != null ? EC5Util.convertSpec(this.ecSpec, this.withCompression) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("EC Public Key").append(var2);
      var1.append("            X: ").append(this.getQ().getAffineXCoord().toBigInteger().toString(16)).append(var2);
      var1.append("            Y: ").append(this.getQ().getAffineYCoord().toBigInteger().toString(16)).append(var2);
      return var1.toString();
   }

   public void setPointFormat(String var1) {
      this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(var1);
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof BCDSTU4145PublicKey)) {
         return false;
      } else {
         BCDSTU4145PublicKey var2 = (BCDSTU4145PublicKey)var1;
         return this.ecPublicKey.getQ().equals(var2.ecPublicKey.getQ()) && this.engineGetSpec().equals(var2.engineGetSpec());
      }
   }

   public int hashCode() {
      return this.ecPublicKey.getQ().hashCode() ^ this.engineGetSpec().hashCode();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      byte[] var2 = (byte[])((byte[])var1.readObject());
      this.populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.getEncoded());
   }
}

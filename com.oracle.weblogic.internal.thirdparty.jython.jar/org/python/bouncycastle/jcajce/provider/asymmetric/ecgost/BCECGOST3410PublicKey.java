package org.python.bouncycastle.jcajce.provider.asymmetric.ecgost;

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
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.python.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
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
import org.python.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCECGOST3410PublicKey implements ECPublicKey, org.python.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
   static final long serialVersionUID = 7026240464295649314L;
   private String algorithm = "ECGOST3410";
   private boolean withCompression;
   private transient ECPublicKeyParameters ecPublicKey;
   private transient ECParameterSpec ecSpec;
   private transient GOST3410PublicKeyAlgParameters gostParams;

   public BCECGOST3410PublicKey(BCECGOST3410PublicKey var1) {
      this.ecPublicKey = var1.ecPublicKey;
      this.ecSpec = var1.ecSpec;
      this.withCompression = var1.withCompression;
      this.gostParams = var1.gostParams;
   }

   public BCECGOST3410PublicKey(ECPublicKeySpec var1) {
      this.ecSpec = var1.getParams();
      this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, var1.getW(), false), EC5Util.getDomainParameters((ProviderConfiguration)null, var1.getParams()));
   }

   public BCECGOST3410PublicKey(org.python.bouncycastle.jce.spec.ECPublicKeySpec var1, ProviderConfiguration var2) {
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

   public BCECGOST3410PublicKey(String var1, ECPublicKeyParameters var2, ECParameterSpec var3) {
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

   public BCECGOST3410PublicKey(String var1, ECPublicKeyParameters var2, org.python.bouncycastle.jce.spec.ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      this.ecPublicKey = var2;
      EllipticCurve var5;
      if (var3 == null) {
         var5 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = this.createSpec(var5, var4);
      } else {
         var5 = EC5Util.convertCurve(var3.getCurve(), var3.getSeed());
         this.ecSpec = EC5Util.convertSpec(var5, var3);
      }

   }

   public BCECGOST3410PublicKey(String var1, ECPublicKeyParameters var2) {
      this.algorithm = var1;
      this.ecPublicKey = var2;
      this.ecSpec = null;
   }

   private ECParameterSpec createSpec(EllipticCurve var1, ECDomainParameters var2) {
      return new ECParameterSpec(var1, new ECPoint(var2.getG().getAffineXCoord().toBigInteger(), var2.getG().getAffineYCoord().toBigInteger()), var2.getN(), var2.getH().intValue());
   }

   public BCECGOST3410PublicKey(ECPublicKey var1) {
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
      this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, var1.getW(), false), EC5Util.getDomainParameters((ProviderConfiguration)null, var1.getParams()));
   }

   BCECGOST3410PublicKey(SubjectPublicKeyInfo var1) {
      this.populateFromPubKeyInfo(var1);
   }

   private void populateFromPubKeyInfo(SubjectPublicKeyInfo var1) {
      DERBitString var2 = var1.getPublicKeyData();
      this.algorithm = "ECGOST3410";

      ASN1OctetString var3;
      try {
         var3 = (ASN1OctetString)ASN1Primitive.fromByteArray(var2.getBytes());
      } catch (IOException var10) {
         throw new IllegalArgumentException("error recovering public key");
      }

      byte[] var4 = var3.getOctets();
      byte[] var5 = new byte[32];
      byte[] var6 = new byte[32];

      int var7;
      for(var7 = 0; var7 != var5.length; ++var7) {
         var5[var7] = var4[31 - var7];
      }

      for(var7 = 0; var7 != var6.length; ++var7) {
         var6[var7] = var4[63 - var7];
      }

      this.gostParams = GOST3410PublicKeyAlgParameters.getInstance(var1.getAlgorithm().getParameters());
      ECNamedCurveParameterSpec var11 = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()));
      ECCurve var8 = var11.getCurve();
      EllipticCurve var9 = EC5Util.convertCurve(var8, var11.getSeed());
      this.ecPublicKey = new ECPublicKeyParameters(var8.createPoint(new BigInteger(1, var5), new BigInteger(1, var6)), ECUtil.getDomainParameters((ProviderConfiguration)null, (org.python.bouncycastle.jce.spec.ECParameterSpec)var11));
      this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()), var9, new ECPoint(var11.getG().getAffineXCoord().toBigInteger(), var11.getG().getAffineYCoord().toBigInteger()), var11.getN(), var11.getH());
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      Object var1;
      if (this.gostParams != null) {
         var1 = this.gostParams;
      } else if (this.ecSpec instanceof ECNamedCurveSpec) {
         var1 = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)this.ecSpec).getName()), CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet);
      } else {
         ECCurve var2 = EC5Util.convertCurve(this.ecSpec.getCurve());
         X9ECParameters var3 = new X9ECParameters(var2, EC5Util.convertPoint(var2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
         var1 = new X962Parameters(var3);
      }

      BigInteger var8 = this.ecPublicKey.getQ().getAffineXCoord().toBigInteger();
      BigInteger var9 = this.ecPublicKey.getQ().getAffineYCoord().toBigInteger();
      byte[] var4 = new byte[64];
      this.extractBytes(var4, 0, var8);
      this.extractBytes(var4, 32, var9);

      SubjectPublicKeyInfo var5;
      try {
         var5 = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, (ASN1Encodable)var1), new DEROctetString(var4));
      } catch (IOException var7) {
         return null;
      }

      return KeyUtil.getEncodedSubjectPublicKeyInfo(var5);
   }

   private void extractBytes(byte[] var1, int var2, BigInteger var3) {
      byte[] var4 = var3.toByteArray();
      if (var4.length < 32) {
         byte[] var5 = new byte[32];
         System.arraycopy(var4, 0, var5, var5.length - var4.length, var4.length);
         var4 = var5;
      }

      for(int var6 = 0; var6 != 32; ++var6) {
         var1[var2 + var6] = var4[var4.length - 1 - var6];
      }

   }

   public ECParameterSpec getParams() {
      return this.ecSpec;
   }

   public org.python.bouncycastle.jce.spec.ECParameterSpec getParameters() {
      return this.ecSpec == null ? null : EC5Util.convertSpec(this.ecSpec, this.withCompression);
   }

   public ECPoint getW() {
      return new ECPoint(this.ecPublicKey.getQ().getAffineXCoord().toBigInteger(), this.ecPublicKey.getQ().getAffineYCoord().toBigInteger());
   }

   public org.python.bouncycastle.math.ec.ECPoint getQ() {
      return this.ecSpec == null ? this.ecPublicKey.getQ().getDetachedPoint() : this.ecPublicKey.getQ();
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
      org.python.bouncycastle.math.ec.ECPoint var3 = this.ecPublicKey.getQ();
      var1.append("EC Public Key").append(var2);
      var1.append("            X: ").append(var3.getAffineXCoord().toBigInteger().toString(16)).append(var2);
      var1.append("            Y: ").append(var3.getAffineYCoord().toBigInteger().toString(16)).append(var2);
      return var1.toString();
   }

   public void setPointFormat(String var1) {
      this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(var1);
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof BCECGOST3410PublicKey)) {
         return false;
      } else {
         BCECGOST3410PublicKey var2 = (BCECGOST3410PublicKey)var1;
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

   public GOST3410PublicKeyAlgParameters getGostParams() {
      return this.gostParams;
   }
}

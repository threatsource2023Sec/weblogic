package org.python.bouncycastle.jcajce.provider.asymmetric.ecgost;

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
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.python.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCECGOST3410PrivateKey implements ECPrivateKey, org.python.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
   static final long serialVersionUID = 7245981689601667138L;
   private String algorithm = "ECGOST3410";
   private boolean withCompression;
   private transient GOST3410PublicKeyAlgParameters gostParams;
   private transient BigInteger d;
   private transient ECParameterSpec ecSpec;
   private transient DERBitString publicKey;
   private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected BCECGOST3410PrivateKey() {
   }

   public BCECGOST3410PrivateKey(ECPrivateKey var1) {
      this.d = var1.getS();
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
   }

   public BCECGOST3410PrivateKey(ECPrivateKeySpec var1) {
      this.d = var1.getD();
      if (var1.getParams() != null) {
         ECCurve var2 = var1.getParams().getCurve();
         EllipticCurve var3 = EC5Util.convertCurve(var2, var1.getParams().getSeed());
         this.ecSpec = EC5Util.convertSpec(var3, var1.getParams());
      } else {
         this.ecSpec = null;
      }

   }

   public BCECGOST3410PrivateKey(java.security.spec.ECPrivateKeySpec var1) {
      this.d = var1.getS();
      this.ecSpec = var1.getParams();
   }

   public BCECGOST3410PrivateKey(BCECGOST3410PrivateKey var1) {
      this.d = var1.d;
      this.ecSpec = var1.ecSpec;
      this.withCompression = var1.withCompression;
      this.attrCarrier = var1.attrCarrier;
      this.publicKey = var1.publicKey;
      this.gostParams = var1.gostParams;
   }

   public BCECGOST3410PrivateKey(String var1, ECPrivateKeyParameters var2, BCECGOST3410PublicKey var3, ECParameterSpec var4) {
      ECDomainParameters var5 = var2.getParameters();
      this.algorithm = var1;
      this.d = var2.getD();
      if (var4 == null) {
         EllipticCurve var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
         this.ecSpec = new ECParameterSpec(var6, new ECPoint(var5.getG().getAffineXCoord().toBigInteger(), var5.getG().getAffineYCoord().toBigInteger()), var5.getN(), var5.getH().intValue());
      } else {
         this.ecSpec = var4;
      }

      this.gostParams = var3.getGostParams();
      this.publicKey = this.getPublicKeyDetails(var3);
   }

   public BCECGOST3410PrivateKey(String var1, ECPrivateKeyParameters var2, BCECGOST3410PublicKey var3, org.python.bouncycastle.jce.spec.ECParameterSpec var4) {
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

      this.gostParams = var3.getGostParams();
      this.publicKey = this.getPublicKeyDetails(var3);
   }

   public BCECGOST3410PrivateKey(String var1, ECPrivateKeyParameters var2) {
      this.algorithm = var1;
      this.d = var2.getD();
      this.ecSpec = null;
   }

   BCECGOST3410PrivateKey(PrivateKeyInfo var1) throws IOException {
      this.populateFromPrivKeyInfo(var1);
   }

   private void populateFromPrivKeyInfo(PrivateKeyInfo var1) throws IOException {
      ASN1Primitive var2 = var1.getPrivateKeyAlgorithm().getParameters().toASN1Primitive();
      EllipticCurve var5;
      if (!(var2 instanceof ASN1Sequence) || ASN1Sequence.getInstance(var2).size() != 2 && ASN1Sequence.getInstance(var2).size() != 3) {
         X962Parameters var10 = X962Parameters.getInstance(var1.getPrivateKeyAlgorithm().getParameters());
         if (var10.isNamedCurve()) {
            ASN1ObjectIdentifier var11 = ASN1ObjectIdentifier.getInstance(var10.getParameters());
            X9ECParameters var14 = ECUtil.getNamedCurveByOid(var11);
            if (var14 == null) {
               ECDomainParameters var16 = ECGOST3410NamedCurves.getByOID(var11);
               EllipticCurve var19 = EC5Util.convertCurve(var16.getCurve(), var16.getSeed());
               this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(var11), var19, new ECPoint(var16.getG().getAffineXCoord().toBigInteger(), var16.getG().getAffineYCoord().toBigInteger()), var16.getN(), var16.getH());
            } else {
               EllipticCurve var18 = EC5Util.convertCurve(var14.getCurve(), var14.getSeed());
               this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(var11), var18, new ECPoint(var14.getG().getAffineXCoord().toBigInteger(), var14.getG().getAffineYCoord().toBigInteger()), var14.getN(), var14.getH());
            }
         } else if (var10.isImplicitlyCA()) {
            this.ecSpec = null;
         } else {
            X9ECParameters var12 = X9ECParameters.getInstance(var10.getParameters());
            var5 = EC5Util.convertCurve(var12.getCurve(), var12.getSeed());
            this.ecSpec = new ECParameterSpec(var5, new ECPoint(var12.getG().getAffineXCoord().toBigInteger(), var12.getG().getAffineYCoord().toBigInteger()), var12.getN(), var12.getH().intValue());
         }

         ASN1Encodable var13 = var1.parsePrivateKey();
         if (var13 instanceof ASN1Integer) {
            ASN1Integer var15 = ASN1Integer.getInstance(var13);
            this.d = var15.getValue();
         } else {
            org.python.bouncycastle.asn1.sec.ECPrivateKey var17 = org.python.bouncycastle.asn1.sec.ECPrivateKey.getInstance(var13);
            this.d = var17.getKey();
            this.publicKey = var17.getPublicKey();
         }
      } else {
         this.gostParams = GOST3410PublicKeyAlgParameters.getInstance(var1.getPrivateKeyAlgorithm().getParameters());
         ECNamedCurveParameterSpec var3 = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()));
         ECCurve var4 = var3.getCurve();
         var5 = EC5Util.convertCurve(var4, var3.getSeed());
         this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()), var5, new ECPoint(var3.getG().getAffineXCoord().toBigInteger(), var3.getG().getAffineYCoord().toBigInteger()), var3.getN(), var3.getH());
         ASN1Encodable var6 = var1.parsePrivateKey();
         if (var6 instanceof ASN1Integer) {
            this.d = ASN1Integer.getInstance(var6).getPositiveValue();
         } else {
            byte[] var7 = ASN1OctetString.getInstance(var6).getOctets();
            byte[] var8 = new byte[var7.length];

            for(int var9 = 0; var9 != var7.length; ++var9) {
               var8[var9] = var7[var7.length - 1 - var9];
            }

            this.d = new BigInteger(1, var8);
         }
      }

   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      if (this.gostParams != null) {
         byte[] var8 = new byte[32];
         this.extractBytes(var8, 0, this.getS());

         try {
            PrivateKeyInfo var9 = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, this.gostParams), new DEROctetString(var8));
            return var9.getEncoded("DER");
         } catch (IOException var6) {
            return null;
         }
      } else {
         X962Parameters var1;
         int var2;
         if (this.ecSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier var3 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
            if (var3 == null) {
               var3 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
            }

            var1 = new X962Parameters(var3);
            var2 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, this.ecSpec.getOrder(), this.getS());
         } else if (this.ecSpec == null) {
            var1 = new X962Parameters(DERNull.INSTANCE);
            var2 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, (BigInteger)null, this.getS());
         } else {
            ECCurve var10 = EC5Util.convertCurve(this.ecSpec.getCurve());
            X9ECParameters var4 = new X9ECParameters(var10, EC5Util.convertPoint(var10, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
            var1 = new X962Parameters(var4);
            var2 = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, this.ecSpec.getOrder(), this.getS());
         }

         org.python.bouncycastle.asn1.sec.ECPrivateKey var11;
         if (this.publicKey != null) {
            var11 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var2, this.getS(), this.publicKey, var1);
         } else {
            var11 = new org.python.bouncycastle.asn1.sec.ECPrivateKey(var2, this.getS(), var1);
         }

         try {
            PrivateKeyInfo var12 = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, var1.toASN1Primitive()), var11.toASN1Primitive());
            return var12.getEncoded("DER");
         } catch (IOException var7) {
            return null;
         }
      }
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
      if (!(var1 instanceof BCECGOST3410PrivateKey)) {
         return false;
      } else {
         BCECGOST3410PrivateKey var2 = (BCECGOST3410PrivateKey)var1;
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

   private DERBitString getPublicKeyDetails(BCECGOST3410PublicKey var1) {
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

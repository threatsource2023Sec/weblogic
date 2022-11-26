package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.python.bouncycastle.asn1.ASN1Encodable;
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
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.asn1.x9.X9ECPoint;
import org.python.bouncycastle.asn1.x9.X9IntegerConverter;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.Strings;

public class JCEECPublicKey implements ECPublicKey, org.python.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
   private String algorithm = "EC";
   private ECPoint q;
   private ECParameterSpec ecSpec;
   private boolean withCompression;
   private GOST3410PublicKeyAlgParameters gostParams;

   public JCEECPublicKey(String var1, JCEECPublicKey var2) {
      this.algorithm = var1;
      this.q = var2.q;
      this.ecSpec = var2.ecSpec;
      this.withCompression = var2.withCompression;
      this.gostParams = var2.gostParams;
   }

   public JCEECPublicKey(String var1, ECPublicKeySpec var2) {
      this.algorithm = var1;
      this.ecSpec = var2.getParams();
      this.q = EC5Util.convertPoint(this.ecSpec, var2.getW(), false);
   }

   public JCEECPublicKey(String var1, org.python.bouncycastle.jce.spec.ECPublicKeySpec var2) {
      this.algorithm = var1;
      this.q = var2.getQ();
      if (var2.getParams() != null) {
         ECCurve var3 = var2.getParams().getCurve();
         EllipticCurve var4 = EC5Util.convertCurve(var3, var2.getParams().getSeed());
         this.ecSpec = EC5Util.convertSpec(var4, var2.getParams());
      } else {
         if (this.q.getCurve() == null) {
            org.python.bouncycastle.jce.spec.ECParameterSpec var5 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            this.q = var5.getCurve().createPoint(this.q.getAffineXCoord().toBigInteger(), this.q.getAffineYCoord().toBigInteger(), false);
         }

         this.ecSpec = null;
      }

   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2, ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      this.q = var2.getQ();
      if (var3 == null) {
         EllipticCurve var5 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = this.createSpec(var5, var4);
      } else {
         this.ecSpec = var3;
      }

   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2, org.python.bouncycastle.jce.spec.ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      this.q = var2.getQ();
      EllipticCurve var5;
      if (var3 == null) {
         var5 = EC5Util.convertCurve(var4.getCurve(), var4.getSeed());
         this.ecSpec = this.createSpec(var5, var4);
      } else {
         var5 = EC5Util.convertCurve(var3.getCurve(), var3.getSeed());
         this.ecSpec = EC5Util.convertSpec(var5, var3);
      }

   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2) {
      this.algorithm = var1;
      this.q = var2.getQ();
      this.ecSpec = null;
   }

   private ECParameterSpec createSpec(EllipticCurve var1, ECDomainParameters var2) {
      return new ECParameterSpec(var1, new java.security.spec.ECPoint(var2.getG().getAffineXCoord().toBigInteger(), var2.getG().getAffineYCoord().toBigInteger()), var2.getN(), var2.getH().intValue());
   }

   public JCEECPublicKey(ECPublicKey var1) {
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
      this.q = EC5Util.convertPoint(this.ecSpec, var1.getW(), false);
   }

   JCEECPublicKey(SubjectPublicKeyInfo var1) {
      this.populateFromPubKeyInfo(var1);
   }

   private void populateFromPubKeyInfo(SubjectPublicKeyInfo var1) {
      byte[] var6;
      if (var1.getAlgorithmId().getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3410_2001)) {
         DERBitString var2 = var1.getPublicKeyData();
         this.algorithm = "ECGOST3410";

         ASN1OctetString var3;
         try {
            var3 = (ASN1OctetString)ASN1Primitive.fromByteArray(var2.getBytes());
         } catch (IOException var11) {
            throw new IllegalArgumentException("error recovering public key");
         }

         byte[] var4 = var3.getOctets();
         byte[] var5 = new byte[32];
         var6 = new byte[32];

         int var7;
         for(var7 = 0; var7 != var5.length; ++var7) {
            var5[var7] = var4[31 - var7];
         }

         for(var7 = 0; var7 != var6.length; ++var7) {
            var6[var7] = var4[63 - var7];
         }

         this.gostParams = new GOST3410PublicKeyAlgParameters((ASN1Sequence)var1.getAlgorithmId().getParameters());
         ECNamedCurveParameterSpec var20 = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()));
         ECCurve var8 = var20.getCurve();
         EllipticCurve var9 = EC5Util.convertCurve(var8, var20.getSeed());
         this.q = var8.createPoint(new BigInteger(1, var5), new BigInteger(1, var6), false);
         this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()), var9, new java.security.spec.ECPoint(var20.getG().getAffineXCoord().toBigInteger(), var20.getG().getAffineYCoord().toBigInteger()), var20.getN(), var20.getH());
      } else {
         X962Parameters var12 = new X962Parameters((ASN1Primitive)var1.getAlgorithmId().getParameters());
         ECCurve var13;
         EllipticCurve var14;
         if (var12.isNamedCurve()) {
            ASN1ObjectIdentifier var15 = (ASN1ObjectIdentifier)var12.getParameters();
            X9ECParameters var18 = ECUtil.getNamedCurveByOid(var15);
            var13 = var18.getCurve();
            var14 = EC5Util.convertCurve(var13, var18.getSeed());
            this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(var15), var14, new java.security.spec.ECPoint(var18.getG().getAffineXCoord().toBigInteger(), var18.getG().getAffineYCoord().toBigInteger()), var18.getN(), var18.getH());
         } else if (var12.isImplicitlyCA()) {
            this.ecSpec = null;
            var13 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve();
         } else {
            X9ECParameters var16 = X9ECParameters.getInstance(var12.getParameters());
            var13 = var16.getCurve();
            var14 = EC5Util.convertCurve(var13, var16.getSeed());
            this.ecSpec = new ECParameterSpec(var14, new java.security.spec.ECPoint(var16.getG().getAffineXCoord().toBigInteger(), var16.getG().getAffineYCoord().toBigInteger()), var16.getN(), var16.getH().intValue());
         }

         DERBitString var17 = var1.getPublicKeyData();
         var6 = var17.getBytes();
         Object var21 = new DEROctetString(var6);
         if (var6[0] == 4 && var6[1] == var6.length - 2 && (var6[2] == 2 || var6[2] == 3)) {
            int var19 = (new X9IntegerConverter()).getByteLength(var13);
            if (var19 >= var6.length - 3) {
               try {
                  var21 = (ASN1OctetString)ASN1Primitive.fromByteArray(var6);
               } catch (IOException var10) {
                  throw new IllegalArgumentException("error recovering public key");
               }
            }
         }

         X9ECPoint var22 = new X9ECPoint(var13, (ASN1OctetString)var21);
         this.q = var22.getPoint();
      }

   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      ECCurve var2;
      X9ECParameters var3;
      SubjectPublicKeyInfo var5;
      if (this.algorithm.equals("ECGOST3410")) {
         Object var1;
         if (this.gostParams != null) {
            var1 = this.gostParams;
         } else if (this.ecSpec instanceof ECNamedCurveSpec) {
            var1 = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)this.ecSpec).getName()), CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet);
         } else {
            var2 = EC5Util.convertCurve(this.ecSpec.getCurve());
            var3 = new X9ECParameters(var2, EC5Util.convertPoint(var2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
            var1 = new X962Parameters(var3);
         }

         BigInteger var8 = this.q.getAffineXCoord().toBigInteger();
         BigInteger var11 = this.q.getAffineYCoord().toBigInteger();
         byte[] var4 = new byte[64];
         this.extractBytes(var4, 0, var8);
         this.extractBytes(var4, 32, var11);

         try {
            var5 = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, (ASN1Encodable)var1), new DEROctetString(var4));
         } catch (IOException var7) {
            return null;
         }
      } else {
         X962Parameters var9;
         if (this.ecSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier var10 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
            if (var10 == null) {
               var10 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
            }

            var9 = new X962Parameters(var10);
         } else if (this.ecSpec == null) {
            var9 = new X962Parameters(DERNull.INSTANCE);
         } else {
            var2 = EC5Util.convertCurve(this.ecSpec.getCurve());
            var3 = new X9ECParameters(var2, EC5Util.convertPoint(var2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long)this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed());
            var9 = new X962Parameters(var3);
         }

         var2 = this.engineGetQ().getCurve();
         ASN1OctetString var12 = (ASN1OctetString)(new X9ECPoint(var2.createPoint(this.getQ().getAffineXCoord().toBigInteger(), this.getQ().getAffineYCoord().toBigInteger(), this.withCompression))).toASN1Primitive();
         var5 = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var9), var12.getOctets());
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

   public java.security.spec.ECPoint getW() {
      return new java.security.spec.ECPoint(this.q.getAffineXCoord().toBigInteger(), this.q.getAffineYCoord().toBigInteger());
   }

   public ECPoint getQ() {
      return this.ecSpec == null ? this.q.getDetachedPoint() : this.q;
   }

   public ECPoint engineGetQ() {
      return this.q;
   }

   org.python.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
      return this.ecSpec != null ? EC5Util.convertSpec(this.ecSpec, this.withCompression) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("EC Public Key").append(var2);
      var1.append("            X: ").append(this.q.getAffineXCoord().toBigInteger().toString(16)).append(var2);
      var1.append("            Y: ").append(this.q.getAffineYCoord().toBigInteger().toString(16)).append(var2);
      return var1.toString();
   }

   public void setPointFormat(String var1) {
      this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(var1);
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof JCEECPublicKey)) {
         return false;
      } else {
         JCEECPublicKey var2 = (JCEECPublicKey)var1;
         return this.engineGetQ().equals(var2.engineGetQ()) && this.engineGetSpec().equals(var2.engineGetSpec());
      }
   }

   public int hashCode() {
      return this.engineGetQ().hashCode() ^ this.engineGetSpec().hashCode();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      byte[] var2 = (byte[])((byte[])var1.readObject());
      this.populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
      this.algorithm = (String)var1.readObject();
      this.withCompression = var1.readBoolean();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeObject(this.getEncoded());
      var1.writeObject(this.algorithm);
      var1.writeBoolean(this.withCompression);
   }
}

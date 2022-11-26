package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECPoint;
import org.python.bouncycastle.asn1.x9.X9IntegerConverter;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jce.interfaces.ECPointEncoder;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.util.Strings;

public class BCECPublicKey implements ECPublicKey, org.python.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
   static final long serialVersionUID = 2422789860422731812L;
   private String algorithm = "EC";
   private boolean withCompression;
   private transient ECPublicKeyParameters ecPublicKey;
   private transient ECParameterSpec ecSpec;
   private transient ProviderConfiguration configuration;

   public BCECPublicKey(String var1, BCECPublicKey var2) {
      this.algorithm = var1;
      this.ecPublicKey = var2.ecPublicKey;
      this.ecSpec = var2.ecSpec;
      this.withCompression = var2.withCompression;
      this.configuration = var2.configuration;
   }

   public BCECPublicKey(String var1, ECPublicKeySpec var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.ecSpec = var2.getParams();
      this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, var2.getW(), false), EC5Util.getDomainParameters(var3, var2.getParams()));
      this.configuration = var3;
   }

   public BCECPublicKey(String var1, org.python.bouncycastle.jce.spec.ECPublicKeySpec var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      if (var2.getParams() != null) {
         ECCurve var4 = var2.getParams().getCurve();
         EllipticCurve var5 = EC5Util.convertCurve(var4, var2.getParams().getSeed());
         this.ecPublicKey = new ECPublicKeyParameters(var2.getQ(), ECUtil.getDomainParameters(var3, var2.getParams()));
         this.ecSpec = EC5Util.convertSpec(var5, var2.getParams());
      } else {
         org.python.bouncycastle.jce.spec.ECParameterSpec var6 = var3.getEcImplicitlyCa();
         this.ecPublicKey = new ECPublicKeyParameters(var6.getCurve().createPoint(var2.getQ().getAffineXCoord().toBigInteger(), var2.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(var3, (ECParameterSpec)null));
         this.ecSpec = null;
      }

      this.configuration = var3;
   }

   public BCECPublicKey(String var1, ECPublicKeyParameters var2, ECParameterSpec var3, ProviderConfiguration var4) {
      ECDomainParameters var5 = var2.getParameters();
      this.algorithm = var1;
      this.ecPublicKey = var2;
      if (var3 == null) {
         EllipticCurve var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
         this.ecSpec = this.createSpec(var6, var5);
      } else {
         this.ecSpec = var3;
      }

      this.configuration = var4;
   }

   public BCECPublicKey(String var1, ECPublicKeyParameters var2, org.python.bouncycastle.jce.spec.ECParameterSpec var3, ProviderConfiguration var4) {
      ECDomainParameters var5 = var2.getParameters();
      this.algorithm = var1;
      EllipticCurve var6;
      if (var3 == null) {
         var6 = EC5Util.convertCurve(var5.getCurve(), var5.getSeed());
         this.ecSpec = this.createSpec(var6, var5);
      } else {
         var6 = EC5Util.convertCurve(var3.getCurve(), var3.getSeed());
         this.ecSpec = EC5Util.convertSpec(var6, var3);
      }

      this.ecPublicKey = var2;
      this.configuration = var4;
   }

   public BCECPublicKey(String var1, ECPublicKeyParameters var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.ecPublicKey = var2;
      this.ecSpec = null;
      this.configuration = var3;
   }

   public BCECPublicKey(ECPublicKey var1, ProviderConfiguration var2) {
      this.algorithm = var1.getAlgorithm();
      this.ecSpec = var1.getParams();
      this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, var1.getW(), false), EC5Util.getDomainParameters(var2, var1.getParams()));
   }

   BCECPublicKey(String var1, SubjectPublicKeyInfo var2, ProviderConfiguration var3) {
      this.algorithm = var1;
      this.configuration = var3;
      this.populateFromPubKeyInfo(var2);
   }

   private ECParameterSpec createSpec(EllipticCurve var1, ECDomainParameters var2) {
      return new ECParameterSpec(var1, new ECPoint(var2.getG().getAffineXCoord().toBigInteger(), var2.getG().getAffineYCoord().toBigInteger()), var2.getN(), var2.getH().intValue());
   }

   private void populateFromPubKeyInfo(SubjectPublicKeyInfo var1) {
      X962Parameters var2 = X962Parameters.getInstance(var1.getAlgorithm().getParameters());
      ECCurve var3 = EC5Util.getCurve(this.configuration, var2);
      this.ecSpec = EC5Util.convertToSpec(var2, var3);
      DERBitString var4 = var1.getPublicKeyData();
      byte[] var5 = var4.getBytes();
      Object var6 = new DEROctetString(var5);
      if (var5[0] == 4 && var5[1] == var5.length - 2 && (var5[2] == 2 || var5[2] == 3)) {
         int var7 = (new X9IntegerConverter()).getByteLength(var3);
         if (var7 >= var5.length - 3) {
            try {
               var6 = (ASN1OctetString)ASN1Primitive.fromByteArray(var5);
            } catch (IOException var9) {
               throw new IllegalArgumentException("error recovering public key");
            }
         }
      }

      X9ECPoint var10 = new X9ECPoint(var3, (ASN1OctetString)var6);
      this.ecPublicKey = new ECPublicKeyParameters(var10.getPoint(), ECUtil.getDomainParameters(this.configuration, var2));
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      X962Parameters var1 = ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
      ASN1OctetString var2 = ASN1OctetString.getInstance((new X9ECPoint(this.ecPublicKey.getQ(), this.withCompression)).toASN1Primitive());
      SubjectPublicKeyInfo var3 = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var1), var2.getOctets());
      return KeyUtil.getEncodedSubjectPublicKeyInfo(var3);
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
      return this.ecSpec != null ? EC5Util.convertSpec(this.ecSpec, this.withCompression) : this.configuration.getEcImplicitlyCa();
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
      if (!(var1 instanceof BCECPublicKey)) {
         return false;
      } else {
         BCECPublicKey var2 = (BCECPublicKey)var1;
         return this.ecPublicKey.getQ().equals(var2.ecPublicKey.getQ()) && this.engineGetSpec().equals(var2.engineGetSpec());
      }
   }

   public int hashCode() {
      return this.ecPublicKey.getQ().hashCode() ^ this.engineGetSpec().hashCode();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      byte[] var2 = (byte[])((byte[])var1.readObject());
      this.configuration = BouncyCastleProvider.CONFIGURATION;
      this.populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.getEncoded());
   }
}

package org.python.bouncycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.interfaces.GOST3410Params;
import org.python.bouncycastle.jce.interfaces.GOST3410PrivateKey;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.python.bouncycastle.jce.spec.GOST3410PrivateKeySpec;
import org.python.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class BCGOST3410PrivateKey implements GOST3410PrivateKey, PKCS12BagAttributeCarrier {
   static final long serialVersionUID = 8581661527592305464L;
   private BigInteger x;
   private transient GOST3410Params gost3410Spec;
   private transient PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected BCGOST3410PrivateKey() {
   }

   BCGOST3410PrivateKey(GOST3410PrivateKey var1) {
      this.x = var1.getX();
      this.gost3410Spec = var1.getParameters();
   }

   BCGOST3410PrivateKey(GOST3410PrivateKeySpec var1) {
      this.x = var1.getX();
      this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(var1.getP(), var1.getQ(), var1.getA()));
   }

   BCGOST3410PrivateKey(PrivateKeyInfo var1) throws IOException {
      GOST3410PublicKeyAlgParameters var2 = new GOST3410PublicKeyAlgParameters((ASN1Sequence)var1.getAlgorithmId().getParameters());
      ASN1OctetString var3 = ASN1OctetString.getInstance(var1.parsePrivateKey());
      byte[] var4 = var3.getOctets();
      byte[] var5 = new byte[var4.length];

      for(int var6 = 0; var6 != var4.length; ++var6) {
         var5[var6] = var4[var4.length - 1 - var6];
      }

      this.x = new BigInteger(1, var5);
      this.gost3410Spec = GOST3410ParameterSpec.fromPublicKeyAlg(var2);
   }

   BCGOST3410PrivateKey(GOST3410PrivateKeyParameters var1, GOST3410ParameterSpec var2) {
      this.x = var1.getX();
      this.gost3410Spec = var2;
      if (var2 == null) {
         throw new IllegalArgumentException("spec is null");
      }
   }

   public String getAlgorithm() {
      return "GOST3410";
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      byte[] var1 = this.getX().toByteArray();
      byte[] var2;
      if (var1[0] == 0) {
         var2 = new byte[var1.length - 1];
      } else {
         var2 = new byte[var1.length];
      }

      for(int var3 = 0; var3 != var2.length; ++var3) {
         var2[var3] = var1[var1.length - 1 - var3];
      }

      try {
         PrivateKeyInfo var4;
         if (this.gost3410Spec instanceof GOST3410ParameterSpec) {
            var4 = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.gost3410Spec.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.gost3410Spec.getDigestParamSetOID()))), new DEROctetString(var2));
         } else {
            var4 = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94), new DEROctetString(var2));
         }

         return var4.getEncoded("DER");
      } catch (IOException var5) {
         return null;
      }
   }

   public GOST3410Params getParameters() {
      return this.gost3410Spec;
   }

   public BigInteger getX() {
      return this.x;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GOST3410PrivateKey)) {
         return false;
      } else {
         GOST3410PrivateKey var2 = (GOST3410PrivateKey)var1;
         return this.getX().equals(var2.getX()) && this.getParameters().getPublicKeyParameters().equals(var2.getParameters().getPublicKeyParameters()) && this.getParameters().getDigestParamSetOID().equals(var2.getParameters().getDigestParamSetOID()) && this.compareObj(this.getParameters().getEncryptionParamSetOID(), var2.getParameters().getEncryptionParamSetOID());
      }
   }

   private boolean compareObj(Object var1, Object var2) {
      if (var1 == var2) {
         return true;
      } else {
         return var1 == null ? false : var1.equals(var2);
      }
   }

   public int hashCode() {
      return this.getX().hashCode() ^ this.gost3410Spec.hashCode();
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
      var1.defaultReadObject();
      String var2 = (String)var1.readObject();
      if (var2 != null) {
         this.gost3410Spec = new GOST3410ParameterSpec(var2, (String)var1.readObject(), (String)var1.readObject());
      } else {
         this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec((BigInteger)var1.readObject(), (BigInteger)var1.readObject(), (BigInteger)var1.readObject()));
         var1.readObject();
         var1.readObject();
      }

      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      if (this.gost3410Spec.getPublicKeyParamSetOID() != null) {
         var1.writeObject(this.gost3410Spec.getPublicKeyParamSetOID());
         var1.writeObject(this.gost3410Spec.getDigestParamSetOID());
         var1.writeObject(this.gost3410Spec.getEncryptionParamSetOID());
      } else {
         var1.writeObject((Object)null);
         var1.writeObject(this.gost3410Spec.getPublicKeyParameters().getP());
         var1.writeObject(this.gost3410Spec.getPublicKeyParameters().getQ());
         var1.writeObject(this.gost3410Spec.getPublicKeyParameters().getA());
         var1.writeObject(this.gost3410Spec.getDigestParamSetOID());
         var1.writeObject(this.gost3410Spec.getEncryptionParamSetOID());
      }

   }
}

package org.python.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.oiw.ElGamalParameter;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.python.bouncycastle.jce.spec.ElGamalPrivateKeySpec;

public class BCElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {
   static final long serialVersionUID = 4819350091141529678L;
   private BigInteger x;
   private transient ElGamalParameterSpec elSpec;
   private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected BCElGamalPrivateKey() {
   }

   BCElGamalPrivateKey(ElGamalPrivateKey var1) {
      this.x = var1.getX();
      this.elSpec = var1.getParameters();
   }

   BCElGamalPrivateKey(DHPrivateKey var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getParams().getP(), var1.getParams().getG());
   }

   BCElGamalPrivateKey(ElGamalPrivateKeySpec var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getParams().getP(), var1.getParams().getG());
   }

   BCElGamalPrivateKey(DHPrivateKeySpec var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getP(), var1.getG());
   }

   BCElGamalPrivateKey(PrivateKeyInfo var1) throws IOException {
      ElGamalParameter var2 = ElGamalParameter.getInstance(var1.getPrivateKeyAlgorithm().getParameters());
      ASN1Integer var3 = ASN1Integer.getInstance(var1.parsePrivateKey());
      this.x = var3.getValue();
      this.elSpec = new ElGamalParameterSpec(var2.getP(), var2.getG());
   }

   BCElGamalPrivateKey(ElGamalPrivateKeyParameters var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getParameters().getP(), var1.getParameters().getG());
   }

   public String getAlgorithm() {
      return "ElGamal";
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getEncoded() {
      try {
         PrivateKeyInfo var1 = new PrivateKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(this.elSpec.getP(), this.elSpec.getG())), new ASN1Integer(this.getX()));
         return var1.getEncoded("DER");
      } catch (IOException var2) {
         return null;
      }
   }

   public ElGamalParameterSpec getParameters() {
      return this.elSpec;
   }

   public DHParameterSpec getParams() {
      return new DHParameterSpec(this.elSpec.getP(), this.elSpec.getG());
   }

   public BigInteger getX() {
      return this.x;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof DHPrivateKey)) {
         return false;
      } else {
         DHPrivateKey var2 = (DHPrivateKey)var1;
         return this.getX().equals(var2.getX()) && this.getParams().getG().equals(var2.getParams().getG()) && this.getParams().getP().equals(var2.getParams().getP()) && this.getParams().getL() == var2.getParams().getL();
      }
   }

   public int hashCode() {
      return this.getX().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getL();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.elSpec = new ElGamalParameterSpec((BigInteger)var1.readObject(), (BigInteger)var1.readObject());
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.elSpec.getP());
      var1.writeObject(this.elSpec.getG());
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
}

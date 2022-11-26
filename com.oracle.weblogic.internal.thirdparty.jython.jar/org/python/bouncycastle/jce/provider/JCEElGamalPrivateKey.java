package org.python.bouncycastle.jce.provider;

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
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.python.bouncycastle.jce.spec.ElGamalPrivateKeySpec;

public class JCEElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {
   static final long serialVersionUID = 4819350091141529678L;
   BigInteger x;
   ElGamalParameterSpec elSpec;
   private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

   protected JCEElGamalPrivateKey() {
   }

   JCEElGamalPrivateKey(ElGamalPrivateKey var1) {
      this.x = var1.getX();
      this.elSpec = var1.getParameters();
   }

   JCEElGamalPrivateKey(DHPrivateKey var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getParams().getP(), var1.getParams().getG());
   }

   JCEElGamalPrivateKey(ElGamalPrivateKeySpec var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getParams().getP(), var1.getParams().getG());
   }

   JCEElGamalPrivateKey(DHPrivateKeySpec var1) {
      this.x = var1.getX();
      this.elSpec = new ElGamalParameterSpec(var1.getP(), var1.getG());
   }

   JCEElGamalPrivateKey(PrivateKeyInfo var1) throws IOException {
      ElGamalParameter var2 = ElGamalParameter.getInstance(var1.getPrivateKeyAlgorithm().getParameters());
      ASN1Integer var3 = ASN1Integer.getInstance(var1.parsePrivateKey());
      this.x = var3.getValue();
      this.elSpec = new ElGamalParameterSpec(var2.getP(), var2.getG());
   }

   JCEElGamalPrivateKey(ElGamalPrivateKeyParameters var1) {
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
      return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(this.elSpec.getP(), this.elSpec.getG())), new ASN1Integer(this.getX()));
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

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      this.x = (BigInteger)var1.readObject();
      this.elSpec = new ElGamalParameterSpec((BigInteger)var1.readObject(), (BigInteger)var1.readObject());
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeObject(this.getX());
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

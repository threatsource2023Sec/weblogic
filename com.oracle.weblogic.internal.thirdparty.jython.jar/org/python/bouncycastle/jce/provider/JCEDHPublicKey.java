package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.pkcs.DHParameter;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.DHDomainParameters;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class JCEDHPublicKey implements DHPublicKey {
   static final long serialVersionUID = -216691575254424324L;
   private BigInteger y;
   private DHParameterSpec dhSpec;
   private SubjectPublicKeyInfo info;

   JCEDHPublicKey(DHPublicKeySpec var1) {
      this.y = var1.getY();
      this.dhSpec = new DHParameterSpec(var1.getP(), var1.getG());
   }

   JCEDHPublicKey(DHPublicKey var1) {
      this.y = var1.getY();
      this.dhSpec = var1.getParams();
   }

   JCEDHPublicKey(DHPublicKeyParameters var1) {
      this.y = var1.getY();
      this.dhSpec = new DHParameterSpec(var1.getParameters().getP(), var1.getParameters().getG(), var1.getParameters().getL());
   }

   JCEDHPublicKey(BigInteger var1, DHParameterSpec var2) {
      this.y = var1;
      this.dhSpec = var2;
   }

   JCEDHPublicKey(SubjectPublicKeyInfo var1) {
      this.info = var1;

      ASN1Integer var2;
      try {
         var2 = (ASN1Integer)var1.parsePublicKey();
      } catch (IOException var6) {
         throw new IllegalArgumentException("invalid info structure in DH public key");
      }

      this.y = var2.getValue();
      ASN1Sequence var3 = ASN1Sequence.getInstance(var1.getAlgorithmId().getParameters());
      ASN1ObjectIdentifier var4 = var1.getAlgorithmId().getAlgorithm();
      if (!var4.equals(PKCSObjectIdentifiers.dhKeyAgreement) && !this.isPKCSParam(var3)) {
         if (!var4.equals(X9ObjectIdentifiers.dhpublicnumber)) {
            throw new IllegalArgumentException("unknown algorithm type: " + var4);
         }

         DHDomainParameters var7 = DHDomainParameters.getInstance(var3);
         this.dhSpec = new DHParameterSpec(var7.getP().getValue(), var7.getG().getValue());
      } else {
         DHParameter var5 = DHParameter.getInstance(var3);
         if (var5.getL() != null) {
            this.dhSpec = new DHParameterSpec(var5.getP(), var5.getG(), var5.getL().intValue());
         } else {
            this.dhSpec = new DHParameterSpec(var5.getP(), var5.getG());
         }
      }

   }

   public String getAlgorithm() {
      return "DH";
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      return this.info != null ? KeyUtil.getEncodedSubjectPublicKeyInfo(this.info) : KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), (ASN1Encodable)(new ASN1Integer(this.y)));
   }

   public DHParameterSpec getParams() {
      return this.dhSpec;
   }

   public BigInteger getY() {
      return this.y;
   }

   private boolean isPKCSParam(ASN1Sequence var1) {
      if (var1.size() == 2) {
         return true;
      } else if (var1.size() > 3) {
         return false;
      } else {
         ASN1Integer var2 = ASN1Integer.getInstance(var1.getObjectAt(2));
         ASN1Integer var3 = ASN1Integer.getInstance(var1.getObjectAt(0));
         return var2.getValue().compareTo(BigInteger.valueOf((long)var3.getValue().bitLength())) <= 0;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      this.y = (BigInteger)var1.readObject();
      this.dhSpec = new DHParameterSpec((BigInteger)var1.readObject(), (BigInteger)var1.readObject(), var1.readInt());
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeObject(this.getY());
      var1.writeObject(this.dhSpec.getP());
      var1.writeObject(this.dhSpec.getG());
      var1.writeInt(this.dhSpec.getL());
   }
}

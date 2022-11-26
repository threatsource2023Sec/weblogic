package org.python.bouncycastle.jcajce.provider.asymmetric.dh;

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
import org.python.bouncycastle.asn1.x9.DomainParameters;
import org.python.bouncycastle.asn1.x9.ValidationParams;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.crypto.params.DHValidationParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCDHPublicKey implements DHPublicKey {
   static final long serialVersionUID = -216691575254424324L;
   private BigInteger y;
   private transient DHPublicKeyParameters dhPublicKey;
   private transient DHParameterSpec dhSpec;
   private transient SubjectPublicKeyInfo info;

   BCDHPublicKey(DHPublicKeySpec var1) {
      this.y = var1.getY();
      this.dhSpec = new DHParameterSpec(var1.getP(), var1.getG());
      this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(var1.getP(), var1.getG()));
   }

   BCDHPublicKey(DHPublicKey var1) {
      this.y = var1.getY();
      this.dhSpec = var1.getParams();
      this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
   }

   BCDHPublicKey(DHPublicKeyParameters var1) {
      this.y = var1.getY();
      this.dhSpec = new DHParameterSpec(var1.getParameters().getP(), var1.getParameters().getG(), var1.getParameters().getL());
      this.dhPublicKey = var1;
   }

   BCDHPublicKey(BigInteger var1, DHParameterSpec var2) {
      this.y = var1;
      this.dhSpec = var2;
      this.dhPublicKey = new DHPublicKeyParameters(var1, new DHParameters(var2.getP(), var2.getG()));
   }

   public BCDHPublicKey(SubjectPublicKeyInfo var1) {
      this.info = var1;

      ASN1Integer var2;
      try {
         var2 = (ASN1Integer)var1.parsePublicKey();
      } catch (IOException var7) {
         throw new IllegalArgumentException("invalid info structure in DH public key");
      }

      this.y = var2.getValue();
      ASN1Sequence var3 = ASN1Sequence.getInstance(var1.getAlgorithm().getParameters());
      ASN1ObjectIdentifier var4 = var1.getAlgorithm().getAlgorithm();
      if (!var4.equals(PKCSObjectIdentifiers.dhKeyAgreement) && !this.isPKCSParam(var3)) {
         if (!var4.equals(X9ObjectIdentifiers.dhpublicnumber)) {
            throw new IllegalArgumentException("unknown algorithm type: " + var4);
         }

         DomainParameters var8 = DomainParameters.getInstance(var3);
         this.dhSpec = new DHParameterSpec(var8.getP(), var8.getG());
         ValidationParams var6 = var8.getValidationParams();
         if (var6 != null) {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(var8.getP(), var8.getG(), var8.getQ(), var8.getJ(), new DHValidationParameters(var6.getSeed(), var6.getPgenCounter().intValue())));
         } else {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(var8.getP(), var8.getG(), var8.getQ(), var8.getJ(), (DHValidationParameters)null));
         }
      } else {
         DHParameter var5 = DHParameter.getInstance(var3);
         if (var5.getL() != null) {
            this.dhSpec = new DHParameterSpec(var5.getP(), var5.getG(), var5.getL().intValue());
         } else {
            this.dhSpec = new DHParameterSpec(var5.getP(), var5.getG());
         }

         this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
      }

   }

   public String getAlgorithm() {
      return "DH";
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getEncoded() {
      return this.info != null ? KeyUtil.getEncodedSubjectPublicKeyInfo(this.info) : KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, (new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())).toASN1Primitive()), (ASN1Encodable)(new ASN1Integer(this.y)));
   }

   public DHParameterSpec getParams() {
      return this.dhSpec;
   }

   public BigInteger getY() {
      return this.y;
   }

   public DHPublicKeyParameters engineGetKeyParameters() {
      return this.dhPublicKey;
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

   public int hashCode() {
      return this.getY().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getL();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof DHPublicKey)) {
         return false;
      } else {
         DHPublicKey var2 = (DHPublicKey)var1;
         return this.getY().equals(var2.getY()) && this.getParams().getG().equals(var2.getParams().getG()) && this.getParams().getP().equals(var2.getParams().getP()) && this.getParams().getL() == var2.getParams().getL();
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.dhSpec = new DHParameterSpec((BigInteger)var1.readObject(), (BigInteger)var1.readObject(), var1.readInt());
      this.info = null;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.dhSpec.getP());
      var1.writeObject(this.dhSpec.getG());
      var1.writeInt(this.dhSpec.getL());
   }
}

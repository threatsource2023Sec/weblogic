package org.python.bouncycastle.asn1.cmc;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.GeneralName;

public class GetCert extends ASN1Object {
   private final GeneralName issuerName;
   private final BigInteger serialNumber;

   private GetCert(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("incorrect sequence size");
      } else {
         this.issuerName = GeneralName.getInstance(var1.getObjectAt(0));
         this.serialNumber = ASN1Integer.getInstance(var1.getObjectAt(1)).getValue();
      }
   }

   public GetCert(GeneralName var1, BigInteger var2) {
      this.issuerName = var1;
      this.serialNumber = var2;
   }

   public static GetCert getInstance(Object var0) {
      if (var0 instanceof GetCert) {
         return (GetCert)var0;
      } else {
         return var0 != null ? new GetCert(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public GeneralName getIssuerName() {
      return this.issuerName;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.issuerName);
      var1.add(new ASN1Integer(this.serialNumber));
      return new DERSequence(var1);
   }
}

package org.python.bouncycastle.asn1.smime;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERSequence;

public class SMIMECapabilityVector {
   private ASN1EncodableVector capabilities = new ASN1EncodableVector();

   public void addCapability(ASN1ObjectIdentifier var1) {
      this.capabilities.add(new DERSequence(var1));
   }

   public void addCapability(ASN1ObjectIdentifier var1, int var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      var3.add(new ASN1Integer((long)var2));
      this.capabilities.add(new DERSequence(var3));
   }

   public void addCapability(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      var3.add(var2);
      this.capabilities.add(new DERSequence(var3));
   }

   public ASN1EncodableVector toASN1EncodableVector() {
      return this.capabilities;
   }
}

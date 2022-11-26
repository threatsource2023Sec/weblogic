package org.python.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class PolicyMappings extends ASN1Object {
   ASN1Sequence seq = null;

   public static PolicyMappings getInstance(Object var0) {
      if (var0 instanceof PolicyMappings) {
         return (PolicyMappings)var0;
      } else {
         return var0 != null ? new PolicyMappings(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private PolicyMappings(ASN1Sequence var1) {
      this.seq = var1;
   }

   /** @deprecated */
   public PolicyMappings(Hashtable var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      Enumeration var3 = var1.keys();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         String var5 = (String)var1.get(var4);
         ASN1EncodableVector var6 = new ASN1EncodableVector();
         var6.add(new ASN1ObjectIdentifier(var4));
         var6.add(new ASN1ObjectIdentifier(var5));
         var2.add(new DERSequence(var6));
      }

      this.seq = new DERSequence(var2);
   }

   public PolicyMappings(CertPolicyId var1, CertPolicyId var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      var3.add(var2);
      this.seq = new DERSequence(new DERSequence(var3));
   }

   public PolicyMappings(CertPolicyId[] var1, CertPolicyId[] var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();

      for(int var4 = 0; var4 != var1.length; ++var4) {
         ASN1EncodableVector var5 = new ASN1EncodableVector();
         var5.add(var1[var4]);
         var5.add(var2[var4]);
         var3.add(new DERSequence(var5));
      }

      this.seq = new DERSequence(var3);
   }

   public ASN1Primitive toASN1Primitive() {
      return this.seq;
   }
}

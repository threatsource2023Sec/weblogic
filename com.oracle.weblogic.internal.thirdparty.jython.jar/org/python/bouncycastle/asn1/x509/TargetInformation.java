package org.python.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class TargetInformation extends ASN1Object {
   private ASN1Sequence targets;

   public static TargetInformation getInstance(Object var0) {
      if (var0 instanceof TargetInformation) {
         return (TargetInformation)var0;
      } else {
         return var0 != null ? new TargetInformation(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private TargetInformation(ASN1Sequence var1) {
      this.targets = var1;
   }

   public Targets[] getTargetsObjects() {
      Targets[] var1 = new Targets[this.targets.size()];
      int var2 = 0;

      for(Enumeration var3 = this.targets.getObjects(); var3.hasMoreElements(); var1[var2++] = Targets.getInstance(var3.nextElement())) {
      }

      return var1;
   }

   public TargetInformation(Targets var1) {
      this.targets = new DERSequence(var1);
   }

   public TargetInformation(Target[] var1) {
      this(new Targets(var1));
   }

   public ASN1Primitive toASN1Primitive() {
      return this.targets;
   }
}

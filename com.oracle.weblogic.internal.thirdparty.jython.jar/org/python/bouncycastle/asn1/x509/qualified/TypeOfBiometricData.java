package org.python.bouncycastle.asn1.x509.qualified;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;

public class TypeOfBiometricData extends ASN1Object implements ASN1Choice {
   public static final int PICTURE = 0;
   public static final int HANDWRITTEN_SIGNATURE = 1;
   ASN1Encodable obj;

   public static TypeOfBiometricData getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof TypeOfBiometricData)) {
         if (var0 instanceof ASN1Integer) {
            ASN1Integer var3 = ASN1Integer.getInstance(var0);
            int var2 = var3.getValue().intValue();
            return new TypeOfBiometricData(var2);
         } else if (var0 instanceof ASN1ObjectIdentifier) {
            ASN1ObjectIdentifier var1 = ASN1ObjectIdentifier.getInstance(var0);
            return new TypeOfBiometricData(var1);
         } else {
            throw new IllegalArgumentException("unknown object in getInstance");
         }
      } else {
         return (TypeOfBiometricData)var0;
      }
   }

   public TypeOfBiometricData(int var1) {
      if (var1 != 0 && var1 != 1) {
         throw new IllegalArgumentException("unknow PredefinedBiometricType : " + var1);
      } else {
         this.obj = new ASN1Integer((long)var1);
      }
   }

   public TypeOfBiometricData(ASN1ObjectIdentifier var1) {
      this.obj = var1;
   }

   public boolean isPredefined() {
      return this.obj instanceof ASN1Integer;
   }

   public int getPredefinedBiometricType() {
      return ((ASN1Integer)this.obj).getValue().intValue();
   }

   public ASN1ObjectIdentifier getBiometricDataOid() {
      return (ASN1ObjectIdentifier)this.obj;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.obj.toASN1Primitive();
   }
}

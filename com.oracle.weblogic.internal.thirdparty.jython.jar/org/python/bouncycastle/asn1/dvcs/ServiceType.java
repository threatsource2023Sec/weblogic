package org.python.bouncycastle.asn1.dvcs;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Enumerated;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;

public class ServiceType extends ASN1Object {
   public static final ServiceType CPD = new ServiceType(1);
   public static final ServiceType VSD = new ServiceType(2);
   public static final ServiceType VPKC = new ServiceType(3);
   public static final ServiceType CCPD = new ServiceType(4);
   private ASN1Enumerated value;

   public ServiceType(int var1) {
      this.value = new ASN1Enumerated(var1);
   }

   private ServiceType(ASN1Enumerated var1) {
      this.value = var1;
   }

   public static ServiceType getInstance(Object var0) {
      if (var0 instanceof ServiceType) {
         return (ServiceType)var0;
      } else {
         return var0 != null ? new ServiceType(ASN1Enumerated.getInstance(var0)) : null;
      }
   }

   public static ServiceType getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Enumerated.getInstance(var0, var1));
   }

   public BigInteger getValue() {
      return this.value.getValue();
   }

   public ASN1Primitive toASN1Primitive() {
      return this.value;
   }

   public String toString() {
      int var1 = this.value.getValue().intValue();
      return "" + var1 + (var1 == CPD.getValue().intValue() ? "(CPD)" : (var1 == VSD.getValue().intValue() ? "(VSD)" : (var1 == VPKC.getValue().intValue() ? "(VPKC)" : (var1 == CCPD.getValue().intValue() ? "(CCPD)" : "?"))));
   }
}

package org.python.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Hashtable;
import org.python.bouncycastle.asn1.ASN1Enumerated;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.util.Integers;

public class CRLReason extends ASN1Object {
   /** @deprecated */
   public static final int UNSPECIFIED = 0;
   /** @deprecated */
   public static final int KEY_COMPROMISE = 1;
   /** @deprecated */
   public static final int CA_COMPROMISE = 2;
   /** @deprecated */
   public static final int AFFILIATION_CHANGED = 3;
   /** @deprecated */
   public static final int SUPERSEDED = 4;
   /** @deprecated */
   public static final int CESSATION_OF_OPERATION = 5;
   /** @deprecated */
   public static final int CERTIFICATE_HOLD = 6;
   /** @deprecated */
   public static final int REMOVE_FROM_CRL = 8;
   /** @deprecated */
   public static final int PRIVILEGE_WITHDRAWN = 9;
   /** @deprecated */
   public static final int AA_COMPROMISE = 10;
   public static final int unspecified = 0;
   public static final int keyCompromise = 1;
   public static final int cACompromise = 2;
   public static final int affiliationChanged = 3;
   public static final int superseded = 4;
   public static final int cessationOfOperation = 5;
   public static final int certificateHold = 6;
   public static final int removeFromCRL = 8;
   public static final int privilegeWithdrawn = 9;
   public static final int aACompromise = 10;
   private static final String[] reasonString = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
   private static final Hashtable table = new Hashtable();
   private ASN1Enumerated value;

   public static CRLReason getInstance(Object var0) {
      if (var0 instanceof CRLReason) {
         return (CRLReason)var0;
      } else {
         return var0 != null ? lookup(ASN1Enumerated.getInstance(var0).getValue().intValue()) : null;
      }
   }

   private CRLReason(int var1) {
      this.value = new ASN1Enumerated(var1);
   }

   public String toString() {
      int var1 = this.getValue().intValue();
      String var2;
      if (var1 >= 0 && var1 <= 10) {
         var2 = reasonString[var1];
      } else {
         var2 = "invalid";
      }

      return "CRLReason: " + var2;
   }

   public BigInteger getValue() {
      return this.value.getValue();
   }

   public ASN1Primitive toASN1Primitive() {
      return this.value;
   }

   public static CRLReason lookup(int var0) {
      Integer var1 = Integers.valueOf(var0);
      if (!table.containsKey(var1)) {
         table.put(var1, new CRLReason(var0));
      }

      return (CRLReason)table.get(var1);
   }
}

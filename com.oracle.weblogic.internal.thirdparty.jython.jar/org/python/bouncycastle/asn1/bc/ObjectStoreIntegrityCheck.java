package org.python.bouncycastle.asn1.bc;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;

public class ObjectStoreIntegrityCheck extends ASN1Object implements ASN1Choice {
   public static final int PBKD_MAC_CHECK = 0;
   private final int type;
   private final ASN1Object integrityCheck;

   public ObjectStoreIntegrityCheck(PbkdMacIntegrityCheck var1) {
      this((ASN1Encodable)var1);
   }

   private ObjectStoreIntegrityCheck(ASN1Encodable var1) {
      if (!(var1 instanceof ASN1Sequence) && !(var1 instanceof PbkdMacIntegrityCheck)) {
         throw new IllegalArgumentException("Unknown check object in integrity check.");
      } else {
         this.type = 0;
         this.integrityCheck = PbkdMacIntegrityCheck.getInstance(var1);
      }
   }

   public static ObjectStoreIntegrityCheck getInstance(Object var0) {
      if (var0 instanceof ObjectStoreIntegrityCheck) {
         return (ObjectStoreIntegrityCheck)var0;
      } else if (var0 instanceof byte[]) {
         try {
            return new ObjectStoreIntegrityCheck(ASN1Primitive.fromByteArray((byte[])((byte[])var0)));
         } catch (IOException var2) {
            throw new IllegalArgumentException("Unable to parse integrity check details.");
         }
      } else {
         return var0 != null ? new ObjectStoreIntegrityCheck((ASN1Encodable)((ASN1Encodable)var0)) : null;
      }
   }

   public int getType() {
      return this.type;
   }

   public ASN1Object getIntegrityCheck() {
      return this.integrityCheck;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.integrityCheck.toASN1Primitive();
   }
}

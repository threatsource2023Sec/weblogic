package org.python.bouncycastle.asn1.cryptopro;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.util.Arrays;

public class GOST28147Parameters extends ASN1Object {
   private ASN1OctetString iv;
   private ASN1ObjectIdentifier paramSet;

   public static GOST28147Parameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static GOST28147Parameters getInstance(Object var0) {
      if (var0 instanceof GOST28147Parameters) {
         return (GOST28147Parameters)var0;
      } else {
         return var0 != null ? new GOST28147Parameters(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   /** @deprecated */
   public GOST28147Parameters(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      this.iv = (ASN1OctetString)var2.nextElement();
      this.paramSet = (ASN1ObjectIdentifier)var2.nextElement();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.iv);
      var1.add(this.paramSet);
      return new DERSequence(var1);
   }

   public ASN1ObjectIdentifier getEncryptionParamSet() {
      return this.paramSet;
   }

   public byte[] getIV() {
      return Arrays.clone(this.iv.getOctets());
   }
}

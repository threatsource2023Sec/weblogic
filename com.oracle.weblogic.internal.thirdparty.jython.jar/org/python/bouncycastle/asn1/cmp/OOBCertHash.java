package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.crmf.CertId;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash extends ASN1Object {
   private AlgorithmIdentifier hashAlg;
   private CertId certId;
   private DERBitString hashVal;

   private OOBCertHash(ASN1Sequence var1) {
      int var2 = var1.size() - 1;
      this.hashVal = DERBitString.getInstance(var1.getObjectAt(var2--));

      for(int var3 = var2; var3 >= 0; --var3) {
         ASN1TaggedObject var4 = (ASN1TaggedObject)var1.getObjectAt(var3);
         if (var4.getTagNo() == 0) {
            this.hashAlg = AlgorithmIdentifier.getInstance(var4, true);
         } else {
            this.certId = CertId.getInstance(var4, true);
         }
      }

   }

   public static OOBCertHash getInstance(Object var0) {
      if (var0 instanceof OOBCertHash) {
         return (OOBCertHash)var0;
      } else {
         return var0 != null ? new OOBCertHash(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public OOBCertHash(AlgorithmIdentifier var1, CertId var2, byte[] var3) {
      this(var1, var2, new DERBitString(var3));
   }

   public OOBCertHash(AlgorithmIdentifier var1, CertId var2, DERBitString var3) {
      this.hashAlg = var1;
      this.certId = var2;
      this.hashVal = var3;
   }

   public AlgorithmIdentifier getHashAlg() {
      return this.hashAlg;
   }

   public CertId getCertId() {
      return this.certId;
   }

   public DERBitString getHashVal() {
      return this.hashVal;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      this.addOptional(var1, 0, this.hashAlg);
      this.addOptional(var1, 1, this.certId);
      var1.add(this.hashVal);
      return new DERSequence(var1);
   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if (var3 != null) {
         var1.add(new DERTaggedObject(true, var2, var3));
      }

   }
}

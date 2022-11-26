package org.python.bouncycastle.asn1.pkcs;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class RSAESOAEPparams extends ASN1Object {
   private AlgorithmIdentifier hashAlgorithm;
   private AlgorithmIdentifier maskGenAlgorithm;
   private AlgorithmIdentifier pSourceAlgorithm;
   public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM;
   public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION;
   public static final AlgorithmIdentifier DEFAULT_P_SOURCE_ALGORITHM;

   public static RSAESOAEPparams getInstance(Object var0) {
      if (var0 instanceof RSAESOAEPparams) {
         return (RSAESOAEPparams)var0;
      } else {
         return var0 != null ? new RSAESOAEPparams(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public RSAESOAEPparams() {
      this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
      this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
      this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;
   }

   public RSAESOAEPparams(AlgorithmIdentifier var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3) {
      this.hashAlgorithm = var1;
      this.maskGenAlgorithm = var2;
      this.pSourceAlgorithm = var3;
   }

   /** @deprecated */
   public RSAESOAEPparams(ASN1Sequence var1) {
      this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
      this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
      this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;

      for(int var2 = 0; var2 != var1.size(); ++var2) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var1.getObjectAt(var2);
         switch (var3.getTagNo()) {
            case 0:
               this.hashAlgorithm = AlgorithmIdentifier.getInstance(var3, true);
               break;
            case 1:
               this.maskGenAlgorithm = AlgorithmIdentifier.getInstance(var3, true);
               break;
            case 2:
               this.pSourceAlgorithm = AlgorithmIdentifier.getInstance(var3, true);
               break;
            default:
               throw new IllegalArgumentException("unknown tag");
         }
      }

   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public AlgorithmIdentifier getMaskGenAlgorithm() {
      return this.maskGenAlgorithm;
   }

   public AlgorithmIdentifier getPSourceAlgorithm() {
      return this.pSourceAlgorithm;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
         var1.add(new DERTaggedObject(true, 0, this.hashAlgorithm));
      }

      if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
         var1.add(new DERTaggedObject(true, 1, this.maskGenAlgorithm));
      }

      if (!this.pSourceAlgorithm.equals(DEFAULT_P_SOURCE_ALGORITHM)) {
         var1.add(new DERTaggedObject(true, 2, this.pSourceAlgorithm));
      }

      return new DERSequence(var1);
   }

   static {
      DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
      DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
      DEFAULT_P_SOURCE_ALGORITHM = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(new byte[0]));
   }
}

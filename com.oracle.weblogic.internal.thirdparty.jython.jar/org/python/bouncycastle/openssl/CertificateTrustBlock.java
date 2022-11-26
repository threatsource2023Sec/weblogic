package org.python.bouncycastle.openssl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.DERUTF8String;

public class CertificateTrustBlock {
   private ASN1Sequence uses;
   private ASN1Sequence prohibitions;
   private String alias;

   public CertificateTrustBlock(Set var1) {
      this((String)null, var1, (Set)null);
   }

   public CertificateTrustBlock(String var1, Set var2) {
      this(var1, var2, (Set)null);
   }

   public CertificateTrustBlock(String var1, Set var2, Set var3) {
      this.alias = var1;
      this.uses = this.toSequence(var2);
      this.prohibitions = this.toSequence(var3);
   }

   CertificateTrustBlock(byte[] var1) {
      ASN1Sequence var2 = ASN1Sequence.getInstance(var1);
      Enumeration var3 = var2.getObjects();

      while(var3.hasMoreElements()) {
         ASN1Encodable var4 = (ASN1Encodable)var3.nextElement();
         if (var4 instanceof ASN1Sequence) {
            this.uses = ASN1Sequence.getInstance(var4);
         } else if (var4 instanceof ASN1TaggedObject) {
            this.prohibitions = ASN1Sequence.getInstance((ASN1TaggedObject)var4, false);
         } else if (var4 instanceof DERUTF8String) {
            this.alias = DERUTF8String.getInstance(var4).getString();
         }
      }

   }

   public String getAlias() {
      return this.alias;
   }

   public Set getUses() {
      return this.toSet(this.uses);
   }

   public Set getProhibitions() {
      return this.toSet(this.prohibitions);
   }

   private Set toSet(ASN1Sequence var1) {
      if (var1 == null) {
         return Collections.EMPTY_SET;
      } else {
         HashSet var2 = new HashSet(var1.size());
         Enumeration var3 = var1.getObjects();

         while(var3.hasMoreElements()) {
            var2.add(ASN1ObjectIdentifier.getInstance(var3.nextElement()));
         }

         return var2;
      }
   }

   private ASN1Sequence toSequence(Set var1) {
      if (var1 != null && !var1.isEmpty()) {
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            var2.add((ASN1Encodable)var3.next());
         }

         return new DERSequence(var2);
      } else {
         return null;
      }
   }

   ASN1Sequence toASN1Sequence() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.uses != null) {
         var1.add(this.uses);
      }

      if (this.prohibitions != null) {
         var1.add(new DERTaggedObject(false, 0, this.prohibitions));
      }

      if (this.alias != null) {
         var1.add(new DERUTF8String(this.alias));
      }

      return new DERSequence(var1);
   }
}

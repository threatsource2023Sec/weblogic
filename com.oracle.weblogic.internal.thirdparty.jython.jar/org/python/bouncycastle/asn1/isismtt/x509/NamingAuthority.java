package org.python.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1String;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.python.bouncycastle.asn1.x500.DirectoryString;

public class NamingAuthority extends ASN1Object {
   public static final ASN1ObjectIdentifier id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
   private ASN1ObjectIdentifier namingAuthorityId;
   private String namingAuthorityUrl;
   private DirectoryString namingAuthorityText;

   public static NamingAuthority getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof NamingAuthority)) {
         if (var0 instanceof ASN1Sequence) {
            return new NamingAuthority((ASN1Sequence)var0);
         } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
         }
      } else {
         return (NamingAuthority)var0;
      }
   }

   public static NamingAuthority getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   private NamingAuthority(ASN1Sequence var1) {
      if (var1.size() > 3) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         Enumeration var2 = var1.getObjects();
         ASN1Encodable var3;
         if (var2.hasMoreElements()) {
            var3 = (ASN1Encodable)var2.nextElement();
            if (var3 instanceof ASN1ObjectIdentifier) {
               this.namingAuthorityId = (ASN1ObjectIdentifier)var3;
            } else if (var3 instanceof DERIA5String) {
               this.namingAuthorityUrl = DERIA5String.getInstance(var3).getString();
            } else {
               if (!(var3 instanceof ASN1String)) {
                  throw new IllegalArgumentException("Bad object encountered: " + var3.getClass());
               }

               this.namingAuthorityText = DirectoryString.getInstance(var3);
            }
         }

         if (var2.hasMoreElements()) {
            var3 = (ASN1Encodable)var2.nextElement();
            if (var3 instanceof DERIA5String) {
               this.namingAuthorityUrl = DERIA5String.getInstance(var3).getString();
            } else {
               if (!(var3 instanceof ASN1String)) {
                  throw new IllegalArgumentException("Bad object encountered: " + var3.getClass());
               }

               this.namingAuthorityText = DirectoryString.getInstance(var3);
            }
         }

         if (var2.hasMoreElements()) {
            var3 = (ASN1Encodable)var2.nextElement();
            if (!(var3 instanceof ASN1String)) {
               throw new IllegalArgumentException("Bad object encountered: " + var3.getClass());
            }

            this.namingAuthorityText = DirectoryString.getInstance(var3);
         }

      }
   }

   public ASN1ObjectIdentifier getNamingAuthorityId() {
      return this.namingAuthorityId;
   }

   public DirectoryString getNamingAuthorityText() {
      return this.namingAuthorityText;
   }

   public String getNamingAuthorityUrl() {
      return this.namingAuthorityUrl;
   }

   public NamingAuthority(ASN1ObjectIdentifier var1, String var2, DirectoryString var3) {
      this.namingAuthorityId = var1;
      this.namingAuthorityUrl = var2;
      this.namingAuthorityText = var3;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.namingAuthorityId != null) {
         var1.add(this.namingAuthorityId);
      }

      if (this.namingAuthorityUrl != null) {
         var1.add(new DERIA5String(this.namingAuthorityUrl, true));
      }

      if (this.namingAuthorityText != null) {
         var1.add(this.namingAuthorityText);
      }

      return new DERSequence(var1);
   }

   static {
      id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern = new ASN1ObjectIdentifier(ISISMTTObjectIdentifiers.id_isismtt_at_namingAuthorities + ".1");
   }
}

package org.python.bouncycastle.asn1.x500;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1String;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBMPString;
import org.python.bouncycastle.asn1.DERPrintableString;
import org.python.bouncycastle.asn1.DERT61String;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.DERUniversalString;

public class DirectoryString extends ASN1Object implements ASN1Choice, ASN1String {
   private ASN1String string;

   public static DirectoryString getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof DirectoryString)) {
         if (var0 instanceof DERT61String) {
            return new DirectoryString((DERT61String)var0);
         } else if (var0 instanceof DERPrintableString) {
            return new DirectoryString((DERPrintableString)var0);
         } else if (var0 instanceof DERUniversalString) {
            return new DirectoryString((DERUniversalString)var0);
         } else if (var0 instanceof DERUTF8String) {
            return new DirectoryString((DERUTF8String)var0);
         } else if (var0 instanceof DERBMPString) {
            return new DirectoryString((DERBMPString)var0);
         } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
         }
      } else {
         return (DirectoryString)var0;
      }
   }

   public static DirectoryString getInstance(ASN1TaggedObject var0, boolean var1) {
      if (!var1) {
         throw new IllegalArgumentException("choice item must be explicitly tagged");
      } else {
         return getInstance(var0.getObject());
      }
   }

   private DirectoryString(DERT61String var1) {
      this.string = var1;
   }

   private DirectoryString(DERPrintableString var1) {
      this.string = var1;
   }

   private DirectoryString(DERUniversalString var1) {
      this.string = var1;
   }

   private DirectoryString(DERUTF8String var1) {
      this.string = var1;
   }

   private DirectoryString(DERBMPString var1) {
      this.string = var1;
   }

   public DirectoryString(String var1) {
      this.string = new DERUTF8String(var1);
   }

   public String getString() {
      return this.string.getString();
   }

   public String toString() {
      return this.string.getString();
   }

   public ASN1Primitive toASN1Primitive() {
      return ((ASN1Encodable)this.string).toASN1Primitive();
   }
}

package org.python.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.DERUTF8String;

public class IetfAttrSyntax extends ASN1Object {
   public static final int VALUE_OCTETS = 1;
   public static final int VALUE_OID = 2;
   public static final int VALUE_UTF8 = 3;
   GeneralNames policyAuthority = null;
   Vector values = new Vector();
   int valueChoice = -1;

   public static IetfAttrSyntax getInstance(Object var0) {
      if (var0 instanceof IetfAttrSyntax) {
         return (IetfAttrSyntax)var0;
      } else {
         return var0 != null ? new IetfAttrSyntax(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private IetfAttrSyntax(ASN1Sequence var1) {
      int var2 = 0;
      if (var1.getObjectAt(0) instanceof ASN1TaggedObject) {
         this.policyAuthority = GeneralNames.getInstance((ASN1TaggedObject)var1.getObjectAt(0), false);
         ++var2;
      } else if (var1.size() == 2) {
         this.policyAuthority = GeneralNames.getInstance(var1.getObjectAt(0));
         ++var2;
      }

      if (!(var1.getObjectAt(var2) instanceof ASN1Sequence)) {
         throw new IllegalArgumentException("Non-IetfAttrSyntax encoding");
      } else {
         var1 = (ASN1Sequence)var1.getObjectAt(var2);
         Enumeration var3 = var1.getObjects();

         while(var3.hasMoreElements()) {
            ASN1Primitive var4 = (ASN1Primitive)var3.nextElement();
            byte var5;
            if (var4 instanceof ASN1ObjectIdentifier) {
               var5 = 2;
            } else if (var4 instanceof DERUTF8String) {
               var5 = 3;
            } else {
               if (!(var4 instanceof DEROctetString)) {
                  throw new IllegalArgumentException("Bad value type encoding IetfAttrSyntax");
               }

               var5 = 1;
            }

            if (this.valueChoice < 0) {
               this.valueChoice = var5;
            }

            if (var5 != this.valueChoice) {
               throw new IllegalArgumentException("Mix of value types in IetfAttrSyntax");
            }

            this.values.addElement(var4);
         }

      }
   }

   public GeneralNames getPolicyAuthority() {
      return this.policyAuthority;
   }

   public int getValueType() {
      return this.valueChoice;
   }

   public Object[] getValues() {
      int var2;
      if (this.getValueType() == 1) {
         ASN1OctetString[] var4 = new ASN1OctetString[this.values.size()];

         for(var2 = 0; var2 != var4.length; ++var2) {
            var4[var2] = (ASN1OctetString)this.values.elementAt(var2);
         }

         return var4;
      } else if (this.getValueType() == 2) {
         ASN1ObjectIdentifier[] var3 = new ASN1ObjectIdentifier[this.values.size()];

         for(var2 = 0; var2 != var3.length; ++var2) {
            var3[var2] = (ASN1ObjectIdentifier)this.values.elementAt(var2);
         }

         return var3;
      } else {
         DERUTF8String[] var1 = new DERUTF8String[this.values.size()];

         for(var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (DERUTF8String)this.values.elementAt(var2);
         }

         return var1;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.policyAuthority != null) {
         var1.add(new DERTaggedObject(0, this.policyAuthority));
      }

      ASN1EncodableVector var2 = new ASN1EncodableVector();
      Enumeration var3 = this.values.elements();

      while(var3.hasMoreElements()) {
         var2.add((ASN1Encodable)var3.nextElement());
      }

      var1.add(new DERSequence(var2));
      return new DERSequence(var1);
   }
}

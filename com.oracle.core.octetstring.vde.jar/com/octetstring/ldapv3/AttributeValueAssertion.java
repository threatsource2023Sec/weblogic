package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class AttributeValueAssertion implements ASN1Object {
   protected OctetString attributeDesc;
   protected OctetString assertionValue;

   public AttributeValueAssertion() {
   }

   public AttributeValueAssertion(OctetString attributeDesc, OctetString assertionValue) {
      if (attributeDesc == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributeDesc = attributeDesc;
         if (assertionValue == null) {
            throw new IllegalArgumentException();
         } else {
            this.assertionValue = assertionValue;
         }
      }
   }

   public AttributeValueAssertion(AttributeValueAssertion value) {
      this.attributeDesc = value.getAttributeDesc();
      this.assertionValue = value.getAssertionValue();
   }

   public OctetString getAttributeDesc() {
      return this.attributeDesc;
   }

   public void setAttributeDesc(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributeDesc = val;
      }
   }

   public OctetString getAssertionValue() {
      return this.assertionValue;
   }

   public void setAssertionValue(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.assertionValue = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("attributeDesc ").append(this.attributeDesc.toString());
      buf.append(", assertionValue ").append(this.assertionValue.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.attributeDesc.print(out, newindent, "attributeDesc ", ",", flags);
      this.assertionValue.print(out, newindent, "assertionValue ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

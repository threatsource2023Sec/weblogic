package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class AddRequest implements ASN1Object {
   protected OctetString entry;
   protected AttributeList attributes;

   public AddRequest() {
   }

   public AddRequest(OctetString entry, AttributeList attributes) {
      if (entry == null) {
         throw new IllegalArgumentException();
      } else {
         this.entry = entry;
         if (attributes == null) {
            throw new IllegalArgumentException();
         } else {
            this.attributes = attributes;
         }
      }
   }

   public AddRequest(AddRequest value) {
      this.entry = value.getEntry();
      this.attributes = value.getAttributes();
   }

   public OctetString getEntry() {
      return this.entry;
   }

   public void setEntry(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.entry = val;
      }
   }

   public AttributeList getAttributes() {
      return this.attributes;
   }

   public void setAttributes(AttributeList val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributes = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("entry ").append(this.entry.toString());
      buf.append(", attributes ").append(this.attributes.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.entry.print(out, newindent, "entry ", ",", flags);
      this.attributes.print(out, newindent, "attributes ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

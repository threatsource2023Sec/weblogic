package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class CompareRequest implements ASN1Object {
   protected OctetString entry;
   protected AttributeValueAssertion ava;

   public CompareRequest() {
   }

   public CompareRequest(OctetString entry, AttributeValueAssertion ava) {
      if (entry == null) {
         throw new IllegalArgumentException();
      } else {
         this.entry = entry;
         if (ava == null) {
            throw new IllegalArgumentException();
         } else {
            this.ava = ava;
         }
      }
   }

   public CompareRequest(CompareRequest value) {
      this.entry = value.getEntry();
      this.ava = value.getAva();
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

   public AttributeValueAssertion getAva() {
      return this.ava;
   }

   public void setAva(AttributeValueAssertion val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.ava = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("entry ").append(this.entry.toString());
      buf.append(", ava ").append(this.ava.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.entry.print(out, newindent, "entry ", ",", flags);
      this.ava.print(out, newindent, "ava ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

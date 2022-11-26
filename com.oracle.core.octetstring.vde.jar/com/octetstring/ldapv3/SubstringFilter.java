package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SubstringFilter implements ASN1Object {
   protected OctetString type;
   protected SubstringFilter_substrings substrings;

   public SubstringFilter() {
   }

   public SubstringFilter(OctetString type, SubstringFilter_substrings substrings) {
      if (type == null) {
         throw new IllegalArgumentException();
      } else {
         this.type = type;
         if (substrings == null) {
            throw new IllegalArgumentException();
         } else {
            this.substrings = substrings;
         }
      }
   }

   public SubstringFilter(SubstringFilter value) {
      this.type = value.getType();
      this.substrings = value.getSubstrings();
   }

   public OctetString getType() {
      return this.type;
   }

   public void setType(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.type = val;
      }
   }

   public SubstringFilter_substrings getSubstrings() {
      return this.substrings;
   }

   public void setSubstrings(SubstringFilter_substrings val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.substrings = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("type ").append(this.type.toString());
      buf.append(", substrings ").append(this.substrings.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.type.print(out, newindent, "type ", ",", flags);
      this.substrings.print(out, newindent, "substrings ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class AttributeList_Seq implements ASN1Object {
   protected OctetString type;
   protected AttributeList_Seq_vals vals;

   public AttributeList_Seq() {
   }

   public AttributeList_Seq(OctetString type, AttributeList_Seq_vals vals) {
      if (type == null) {
         throw new IllegalArgumentException();
      } else {
         this.type = type;
         if (vals == null) {
            throw new IllegalArgumentException();
         } else {
            this.vals = vals;
         }
      }
   }

   public AttributeList_Seq(AttributeList_Seq value) {
      this.type = value.getType();
      this.vals = value.getVals();
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

   public AttributeList_Seq_vals getVals() {
      return this.vals;
   }

   public void setVals(AttributeList_Seq_vals val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.vals = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("type ").append(this.type.toString());
      buf.append(", vals ").append(this.vals.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.type.print(out, newindent, "type ", ",", flags);
      this.vals.print(out, newindent, "vals ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

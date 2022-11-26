package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SortResult implements ASN1Object {
   protected Int8 sortResult;
   protected OctetString attributeType;

   public SortResult() {
   }

   public SortResult(Int8 sortResult, OctetString attributeType) {
      if (sortResult == null) {
         throw new IllegalArgumentException();
      } else {
         this.sortResult = sortResult;
         this.attributeType = attributeType;
      }
   }

   public SortResult(SortResult value) {
      this.sortResult = value.getSortResult();
      this.attributeType = value.getAttributeType();
   }

   public Int8 getSortResult() {
      return this.sortResult;
   }

   public void setSortResult(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.sortResult = val;
      }
   }

   public OctetString getAttributeType() {
      return this.attributeType;
   }

   public void setAttributeType(OctetString val) {
      this.attributeType = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("sortResult ").append(this.sortResult.toString());
      if (this.attributeType != null) {
         buf.append(", attributeType ").append(this.attributeType.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.sortResult.print(out, newindent, "sortResult ", this.attributeType != null ? "," : "", flags);
      if (this.attributeType != null) {
         this.attributeType.print(out, newindent, "attributeType ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

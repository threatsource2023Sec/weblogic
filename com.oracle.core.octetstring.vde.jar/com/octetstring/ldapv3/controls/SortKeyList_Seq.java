package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SortKeyList_Seq implements ASN1Object {
   protected OctetString attributeType;
   protected OctetString orderingRule;
   protected Bool reverseOrder;

   public SortKeyList_Seq() {
   }

   public SortKeyList_Seq(OctetString attributeType, OctetString orderingRule, Bool reverseOrder) {
      if (attributeType == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributeType = attributeType;
         this.orderingRule = orderingRule;
         this.reverseOrder = reverseOrder;
      }
   }

   public SortKeyList_Seq(SortKeyList_Seq value) {
      this.attributeType = value.getAttributeType();
      this.orderingRule = value.getOrderingRule();
      this.reverseOrder = value.getReverseOrder();
   }

   public OctetString getAttributeType() {
      return this.attributeType;
   }

   public void setAttributeType(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributeType = val;
      }
   }

   public OctetString getOrderingRule() {
      return this.orderingRule;
   }

   public void setOrderingRule(OctetString val) {
      this.orderingRule = val;
   }

   public Bool getReverseOrder() {
      return this.reverseOrder;
   }

   public void setReverseOrder(Bool val) {
      this.reverseOrder = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("attributeType ").append(this.attributeType.toString());
      if (this.orderingRule != null) {
         buf.append(", orderingRule ").append(this.orderingRule.toString());
      }

      if (this.reverseOrder != null) {
         buf.append(", reverseOrder ").append(this.reverseOrder.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.attributeType.print(out, newindent, "attributeType ", this.orderingRule == null && this.reverseOrder == null ? "" : ",", flags);
      if (this.orderingRule != null) {
         this.orderingRule.print(out, newindent, "orderingRule ", this.reverseOrder != null ? "," : "", flags);
      }

      if (this.reverseOrder != null) {
         this.reverseOrder.print(out, newindent, "reverseOrder ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

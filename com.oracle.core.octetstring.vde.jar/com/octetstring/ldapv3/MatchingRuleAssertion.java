package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class MatchingRuleAssertion implements ASN1Object {
   protected OctetString matchingRule;
   protected OctetString type;
   protected OctetString matchValue;
   protected Bool dnAttributes;

   public MatchingRuleAssertion() {
   }

   public MatchingRuleAssertion(OctetString matchingRule, OctetString type, OctetString matchValue, Bool dnAttributes) {
      this.matchingRule = matchingRule;
      this.type = type;
      if (matchValue == null) {
         throw new IllegalArgumentException();
      } else {
         this.matchValue = matchValue;
         this.dnAttributes = dnAttributes;
      }
   }

   public MatchingRuleAssertion(MatchingRuleAssertion value) {
      this.matchingRule = value.getMatchingRule();
      this.type = value.getType();
      this.matchValue = value.getMatchValue();
      this.dnAttributes = value.getDnAttributes();
   }

   public OctetString getMatchingRule() {
      return this.matchingRule;
   }

   public void setMatchingRule(OctetString val) {
      this.matchingRule = val;
   }

   public OctetString getType() {
      return this.type;
   }

   public void setType(OctetString val) {
      this.type = val;
   }

   public OctetString getMatchValue() {
      return this.matchValue;
   }

   public void setMatchValue(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.matchValue = val;
      }
   }

   public Bool getDnAttributes() {
      return this.dnAttributes;
   }

   public void setDnAttributes(Bool val) {
      this.dnAttributes = val;
   }

   public String toString() {
      String comma = "";
      StringBuffer buf = new StringBuffer("{ ");
      if (this.matchingRule != null) {
         buf.append("matchingRule ").append(this.matchingRule.toString());
         comma = ", ";
      }

      if (this.type != null) {
         buf.append(comma).append(" type ").append(this.type.toString());
         comma = ", ";
      }

      buf.append(comma).append(" matchValue ").append(this.matchValue.toString());
      if (this.dnAttributes != null) {
         buf.append(", dnAttributes ").append(this.dnAttributes.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      if (this.matchingRule != null) {
         this.matchingRule.print(out, newindent, "matchingRule ", ",", flags);
      }

      if (this.type != null) {
         this.type.print(out, newindent, "type ", ",", flags);
      }

      this.matchValue.print(out, newindent, "matchValue ", this.dnAttributes != null ? "," : "", flags);
      if (this.dnAttributes != null) {
         this.dnAttributes.print(out, newindent, "dnAttributes ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

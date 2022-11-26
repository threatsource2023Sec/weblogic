package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class Filter implements ASN1Object {
   protected byte selector;
   protected ASN1Object value;
   public static final byte AND_SELECTED = 0;
   public static final byte OR_SELECTED = 1;
   public static final byte NOT_SELECTED = 2;
   public static final byte EQUALITYMATCH_SELECTED = 3;
   public static final byte SUBSTRINGS_SELECTED = 4;
   public static final byte GREATEROREQUAL_SELECTED = 5;
   public static final byte LESSOREQUAL_SELECTED = 6;
   public static final byte PRESENT_SELECTED = 7;
   public static final byte APPROXMATCH_SELECTED = 8;
   public static final byte EXTENSIBLEMATCH_SELECTED = 9;

   public Filter() {
      this.selector = -1;
   }

   public Filter(byte selector, ASN1Object value) {
      this.selector = selector;
      this.value = value;
   }

   public Filter(Filter value) {
      this.selector = value.getSelector();
      if (this.selector >= 0) {
         this.value = value.getValue();
      }

   }

   public byte getSelector() {
      return this.selector;
   }

   public ASN1Object getValue() {
      return this.value;
   }

   public Filter_and getAnd() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return (Filter_and)this.value;
      }
   }

   public void setAnd(Filter_and val) {
      this.selector = 0;
      this.value = val;
   }

   public Filter_or getOr() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return (Filter_or)this.value;
      }
   }

   public void setOr(Filter_or val) {
      this.selector = 1;
      this.value = val;
   }

   public Filter getNot() {
      if (this.selector != 2) {
         throw new IllegalStateException();
      } else {
         return (Filter)this.value;
      }
   }

   public void setNot(Filter val) {
      this.selector = 2;
      this.value = val;
   }

   public AttributeValueAssertion getEqualityMatch() {
      if (this.selector != 3) {
         throw new IllegalStateException();
      } else {
         return (AttributeValueAssertion)this.value;
      }
   }

   public void setEqualityMatch(AttributeValueAssertion val) {
      this.selector = 3;
      this.value = val;
   }

   public SubstringFilter getSubstrings() {
      if (this.selector != 4) {
         throw new IllegalStateException();
      } else {
         return (SubstringFilter)this.value;
      }
   }

   public void setSubstrings(SubstringFilter val) {
      this.selector = 4;
      this.value = val;
   }

   public AttributeValueAssertion getGreaterOrEqual() {
      if (this.selector != 5) {
         throw new IllegalStateException();
      } else {
         return (AttributeValueAssertion)this.value;
      }
   }

   public void setGreaterOrEqual(AttributeValueAssertion val) {
      this.selector = 5;
      this.value = val;
   }

   public AttributeValueAssertion getLessOrEqual() {
      if (this.selector != 6) {
         throw new IllegalStateException();
      } else {
         return (AttributeValueAssertion)this.value;
      }
   }

   public void setLessOrEqual(AttributeValueAssertion val) {
      this.selector = 6;
      this.value = val;
   }

   public OctetString getPresent() {
      if (this.selector != 7) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setPresent(OctetString val) {
      this.selector = 7;
      this.value = val;
   }

   public AttributeValueAssertion getApproxMatch() {
      if (this.selector != 8) {
         throw new IllegalStateException();
      } else {
         return (AttributeValueAssertion)this.value;
      }
   }

   public void setApproxMatch(AttributeValueAssertion val) {
      this.selector = 8;
      this.value = val;
   }

   public MatchingRuleAssertion getExtensibleMatch() {
      if (this.selector != 9) {
         throw new IllegalStateException();
      } else {
         return (MatchingRuleAssertion)this.value;
      }
   }

   public void setExtensibleMatch(MatchingRuleAssertion val) {
      this.selector = 9;
      this.value = val;
   }

   public String toString() {
      switch (this.selector) {
         case 0:
            return "and: " + this.value.toString();
         case 1:
            return "or: " + this.value.toString();
         case 2:
            return "not: " + this.value.toString();
         case 3:
            return "equalityMatch: " + this.value.toString();
         case 4:
            return "substrings: " + this.value.toString();
         case 5:
            return "greaterOrEqual: " + this.value.toString();
         case 6:
            return "lessOrEqual: " + this.value.toString();
         case 7:
            return "present: " + this.value.toString();
         case 8:
            return "approxMatch: " + this.value.toString();
         case 9:
            return "extensibleMatch: " + this.value.toString();
         default:
            return "UNDEFINED";
      }
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      switch (this.selector) {
         case 0:
            this.value.print(out, indent, prefix + "and: ", suffix, flags);
            break;
         case 1:
            this.value.print(out, indent, prefix + "or: ", suffix, flags);
            break;
         case 2:
            this.value.print(out, indent, prefix + "not: ", suffix, flags);
            break;
         case 3:
            this.value.print(out, indent, prefix + "equalityMatch: ", suffix, flags);
            break;
         case 4:
            this.value.print(out, indent, prefix + "substrings: ", suffix, flags);
            break;
         case 5:
            this.value.print(out, indent, prefix + "greaterOrEqual: ", suffix, flags);
            break;
         case 6:
            this.value.print(out, indent, prefix + "lessOrEqual: ", suffix, flags);
            break;
         case 7:
            this.value.print(out, indent, prefix + "present: ", suffix, flags);
            break;
         case 8:
            this.value.print(out, indent, prefix + "approxMatch: ", suffix, flags);
            break;
         case 9:
            this.value.print(out, indent, prefix + "extensibleMatch: ", suffix, flags);
            break;
         default:
            out.println(indent + prefix + "UNDEFINED" + suffix);
      }

   }
}

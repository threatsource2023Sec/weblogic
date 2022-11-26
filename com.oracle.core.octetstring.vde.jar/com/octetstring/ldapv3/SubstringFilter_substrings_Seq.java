package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SubstringFilter_substrings_Seq implements ASN1Object {
   protected byte selector;
   protected ASN1Object value;
   public static final byte INITIAL_SELECTED = 0;
   public static final byte ANY_SELECTED = 1;
   public static final byte FINAL__SELECTED = 2;

   public SubstringFilter_substrings_Seq() {
      this.selector = -1;
   }

   public SubstringFilter_substrings_Seq(byte selector, ASN1Object value) {
      this.selector = selector;
      this.value = value;
   }

   public SubstringFilter_substrings_Seq(SubstringFilter_substrings_Seq value) {
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

   public OctetString getInitial() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setInitial(OctetString val) {
      this.selector = 0;
      this.value = val;
   }

   public OctetString getAny() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setAny(OctetString val) {
      this.selector = 1;
      this.value = val;
   }

   public OctetString getFinal_() {
      if (this.selector != 2) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setFinal_(OctetString val) {
      this.selector = 2;
      this.value = val;
   }

   public String toString() {
      switch (this.selector) {
         case 0:
            return "initial: " + this.value.toString();
         case 1:
            return "any: " + this.value.toString();
         case 2:
            return "final_: " + this.value.toString();
         default:
            return "UNDEFINED";
      }
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      switch (this.selector) {
         case 0:
            this.value.print(out, indent, prefix + "initial: ", suffix, flags);
            break;
         case 1:
            this.value.print(out, indent, prefix + "any: ", suffix, flags);
            break;
         case 2:
            this.value.print(out, indent, prefix + "final_: ", suffix, flags);
            break;
         default:
            out.println(indent + prefix + "UNDEFINED" + suffix);
      }

   }
}

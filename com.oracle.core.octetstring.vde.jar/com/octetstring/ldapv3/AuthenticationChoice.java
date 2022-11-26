package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class AuthenticationChoice implements ASN1Object {
   protected byte selector;
   protected ASN1Object value;
   public static final byte SIMPLE_SELECTED = 0;
   public static final byte SASL_SELECTED = 1;

   public AuthenticationChoice() {
      this.selector = -1;
   }

   public AuthenticationChoice(byte selector, ASN1Object value) {
      this.selector = selector;
      this.value = value;
   }

   public AuthenticationChoice(AuthenticationChoice value) {
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

   public OctetString getSimple() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setSimple(OctetString val) {
      this.selector = 0;
      this.value = val;
   }

   public SaslCredentials getSasl() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return (SaslCredentials)this.value;
      }
   }

   public void setSasl(SaslCredentials val) {
      this.selector = 1;
      this.value = val;
   }

   public String toString() {
      switch (this.selector) {
         case 0:
            return "simple: " + this.value.toString();
         case 1:
            return "sasl: " + this.value.toString();
         default:
            return "UNDEFINED";
      }
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      switch (this.selector) {
         case 0:
            this.value.print(out, indent, prefix + "simple: ", suffix, flags);
            break;
         case 1:
            this.value.print(out, indent, prefix + "sasl: ", suffix, flags);
            break;
         default:
            out.println(indent + prefix + "UNDEFINED" + suffix);
      }

   }
}

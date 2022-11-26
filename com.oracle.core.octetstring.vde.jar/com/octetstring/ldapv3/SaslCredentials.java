package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SaslCredentials implements ASN1Object {
   protected OctetString mechanism;
   protected OctetString credentials;

   public SaslCredentials() {
   }

   public SaslCredentials(OctetString mechanism, OctetString credentials) {
      if (mechanism == null) {
         throw new IllegalArgumentException();
      } else {
         this.mechanism = mechanism;
         this.credentials = credentials;
      }
   }

   public SaslCredentials(SaslCredentials value) {
      this.mechanism = value.getMechanism();
      this.credentials = value.getCredentials();
   }

   public OctetString getMechanism() {
      return this.mechanism;
   }

   public void setMechanism(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.mechanism = val;
      }
   }

   public OctetString getCredentials() {
      return this.credentials;
   }

   public void setCredentials(OctetString val) {
      this.credentials = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("mechanism ").append(this.mechanism.toString());
      if (this.credentials != null) {
         buf.append(", credentials ").append(this.credentials.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.mechanism.print(out, newindent, "mechanism ", this.credentials != null ? "," : "", flags);
      if (this.credentials != null) {
         this.credentials.print(out, newindent, "credentials ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class ExtendedRequest implements ASN1Object {
   protected OctetString requestName;
   protected OctetString requestValue;

   public ExtendedRequest() {
   }

   public ExtendedRequest(OctetString requestName, OctetString requestValue) {
      if (requestName == null) {
         throw new IllegalArgumentException();
      } else {
         this.requestName = requestName;
         this.requestValue = requestValue;
      }
   }

   public ExtendedRequest(ExtendedRequest value) {
      this.requestName = value.getRequestName();
      this.requestValue = value.getRequestValue();
   }

   public OctetString getRequestName() {
      return this.requestName;
   }

   public void setRequestName(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.requestName = val;
      }
   }

   public OctetString getRequestValue() {
      return this.requestValue;
   }

   public void setRequestValue(OctetString val) {
      this.requestValue = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("requestName ").append(this.requestName.toString());
      if (this.requestValue != null) {
         buf.append(", requestValue ").append(this.requestValue.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.requestName.print(out, newindent, "requestName ", this.requestValue != null ? "," : "", flags);
      if (this.requestValue != null) {
         this.requestValue.print(out, newindent, "requestValue ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int32;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class VLVControlValue implements ASN1Object {
   protected Int32 size;
   protected OctetString cookie;

   public VLVControlValue() {
   }

   public VLVControlValue(Int32 size, OctetString cookie) {
      if (size == null) {
         throw new IllegalArgumentException();
      } else {
         this.size = size;
         if (cookie == null) {
            throw new IllegalArgumentException();
         } else {
            this.cookie = cookie;
         }
      }
   }

   public VLVControlValue(VLVControlValue value) {
      this.size = value.getSize();
      this.cookie = value.getCookie();
   }

   public Int32 getSize() {
      return this.size;
   }

   public void setSize(Int32 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.size = val;
      }
   }

   public OctetString getCookie() {
      return this.cookie;
   }

   public void setCookie(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.cookie = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("size ").append(this.size.toString());
      buf.append(", cookie ").append(this.cookie.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.size.print(out, newindent, "size ", ",", flags);
      this.cookie.print(out, newindent, "cookie ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

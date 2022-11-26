package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class BindRequest implements ASN1Object {
   protected Int8 version;
   protected OctetString name;
   protected AuthenticationChoice authentication;

   public BindRequest() {
   }

   public BindRequest(Int8 version, OctetString name, AuthenticationChoice authentication) {
      if (version == null) {
         throw new IllegalArgumentException();
      } else {
         this.version = version;
         if (name == null) {
            throw new IllegalArgumentException();
         } else {
            this.name = name;
            if (authentication == null) {
               throw new IllegalArgumentException();
            } else {
               this.authentication = authentication;
            }
         }
      }
   }

   public BindRequest(BindRequest value) {
      this.version = value.getVersion();
      this.name = value.getName();
      this.authentication = value.getAuthentication();
   }

   public Int8 getVersion() {
      return this.version;
   }

   public void setVersion(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.version = val;
      }
   }

   public OctetString getName() {
      return this.name;
   }

   public void setName(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.name = val;
      }
   }

   public AuthenticationChoice getAuthentication() {
      return this.authentication;
   }

   public void setAuthentication(AuthenticationChoice val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.authentication = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("version ").append(this.version.toString());
      buf.append(", name ").append(this.name.toString());
      buf.append(", authentication ").append(this.authentication.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.version.print(out, newindent, "version ", ",", flags);
      this.name.print(out, newindent, "name ", ",", flags);
      this.authentication.print(out, newindent, "authentication ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

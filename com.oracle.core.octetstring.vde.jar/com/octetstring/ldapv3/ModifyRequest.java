package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class ModifyRequest implements ASN1Object {
   protected OctetString object;
   protected ModifyRequest_modification modification;

   public ModifyRequest() {
   }

   public ModifyRequest(OctetString object, ModifyRequest_modification modification) {
      if (object == null) {
         throw new IllegalArgumentException();
      } else {
         this.object = object;
         if (modification == null) {
            throw new IllegalArgumentException();
         } else {
            this.modification = modification;
         }
      }
   }

   public ModifyRequest(ModifyRequest value) {
      this.object = value.getObject();
      this.modification = value.getModification();
   }

   public OctetString getObject() {
      return this.object;
   }

   public void setObject(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.object = val;
      }
   }

   public ModifyRequest_modification getModification() {
      return this.modification;
   }

   public void setModification(ModifyRequest_modification val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.modification = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("object ").append(this.object.toString());
      buf.append(", modification ").append(this.modification.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.object.print(out, newindent, "object ", ",", flags);
      this.modification.print(out, newindent, "modification ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

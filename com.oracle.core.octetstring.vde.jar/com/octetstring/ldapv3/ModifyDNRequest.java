package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class ModifyDNRequest implements ASN1Object {
   protected OctetString entry;
   protected OctetString newrdn;
   protected Bool deleteoldrdn;
   protected OctetString newSuperior;

   public ModifyDNRequest() {
   }

   public ModifyDNRequest(OctetString entry, OctetString newrdn, Bool deleteoldrdn, OctetString newSuperior) {
      if (entry == null) {
         throw new IllegalArgumentException();
      } else {
         this.entry = entry;
         if (newrdn == null) {
            throw new IllegalArgumentException();
         } else {
            this.newrdn = newrdn;
            if (deleteoldrdn == null) {
               throw new IllegalArgumentException();
            } else {
               this.deleteoldrdn = deleteoldrdn;
               this.newSuperior = newSuperior;
            }
         }
      }
   }

   public ModifyDNRequest(ModifyDNRequest value) {
      this.entry = value.getEntry();
      this.newrdn = value.getNewrdn();
      this.deleteoldrdn = value.getDeleteoldrdn();
      this.newSuperior = value.getNewSuperior();
   }

   public OctetString getEntry() {
      return this.entry;
   }

   public void setEntry(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.entry = val;
      }
   }

   public OctetString getNewrdn() {
      return this.newrdn;
   }

   public void setNewrdn(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.newrdn = val;
      }
   }

   public Bool getDeleteoldrdn() {
      return this.deleteoldrdn;
   }

   public void setDeleteoldrdn(Bool val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.deleteoldrdn = val;
      }
   }

   public OctetString getNewSuperior() {
      return this.newSuperior;
   }

   public void setNewSuperior(OctetString val) {
      this.newSuperior = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("entry ").append(this.entry.toString());
      buf.append(", newrdn ").append(this.newrdn.toString());
      buf.append(", deleteoldrdn ").append(this.deleteoldrdn.toString());
      if (this.newSuperior != null) {
         buf.append(", newSuperior ").append(this.newSuperior.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.entry.print(out, newindent, "entry ", ",", flags);
      this.newrdn.print(out, newindent, "newrdn ", ",", flags);
      this.deleteoldrdn.print(out, newindent, "deleteoldrdn ", this.newSuperior != null ? "," : "", flags);
      if (this.newSuperior != null) {
         this.newSuperior.print(out, newindent, "newSuperior ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

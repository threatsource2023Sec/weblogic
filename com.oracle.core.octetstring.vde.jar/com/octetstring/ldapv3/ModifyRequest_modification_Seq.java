package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int8;
import java.io.PrintWriter;

public class ModifyRequest_modification_Seq implements ASN1Object {
   protected Int8 operation;
   protected AttributeTypeAndValues modification;

   public ModifyRequest_modification_Seq() {
   }

   public ModifyRequest_modification_Seq(Int8 operation, AttributeTypeAndValues modification) {
      if (operation == null) {
         throw new IllegalArgumentException();
      } else {
         this.operation = operation;
         if (modification == null) {
            throw new IllegalArgumentException();
         } else {
            this.modification = modification;
         }
      }
   }

   public ModifyRequest_modification_Seq(ModifyRequest_modification_Seq value) {
      this.operation = value.getOperation();
      this.modification = value.getModification();
   }

   public Int8 getOperation() {
      return this.operation;
   }

   public void setOperation(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.operation = val;
      }
   }

   public AttributeTypeAndValues getModification() {
      return this.modification;
   }

   public void setModification(AttributeTypeAndValues val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.modification = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("operation ").append(this.operation.toString());
      buf.append(", modification ").append(this.modification.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.operation.print(out, newindent, "operation ", ",", flags);
      this.modification.print(out, newindent, "modification ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.Int64;
import java.io.PrintWriter;

public class PersistentSearch implements ASN1Object {
   protected Int64 changeTypes;
   protected Bool changesOnly;
   protected Bool returnECs;

   public PersistentSearch() {
   }

   public PersistentSearch(Int64 changeTypes, Bool changesOnly, Bool returnECs) {
      if (changeTypes == null) {
         throw new IllegalArgumentException();
      } else {
         this.changeTypes = changeTypes;
         if (changesOnly == null) {
            throw new IllegalArgumentException();
         } else {
            this.changesOnly = changesOnly;
            if (returnECs == null) {
               throw new IllegalArgumentException();
            } else {
               this.returnECs = returnECs;
            }
         }
      }
   }

   public PersistentSearch(PersistentSearch value) {
      this.changeTypes = value.getChangeTypes();
      this.changesOnly = value.getChangesOnly();
      this.returnECs = value.getReturnECs();
   }

   public Int64 getChangeTypes() {
      return this.changeTypes;
   }

   public void setChangeTypes(Int64 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.changeTypes = val;
      }
   }

   public Bool getChangesOnly() {
      return this.changesOnly;
   }

   public void setChangesOnly(Bool val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.changesOnly = val;
      }
   }

   public Bool getReturnECs() {
      return this.returnECs;
   }

   public void setReturnECs(Bool val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.returnECs = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("changeTypes ").append(this.changeTypes.toString());
      buf.append(", changesOnly ").append(this.changesOnly.toString());
      buf.append(", returnECs ").append(this.returnECs.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.changeTypes.print(out, newindent, "changeTypes ", ",", flags);
      this.changesOnly.print(out, newindent, "changesOnly ", ",", flags);
      this.returnECs.print(out, newindent, "returnECs ", "", flags);
      out.println(indent + "}" + suffix);
   }
}

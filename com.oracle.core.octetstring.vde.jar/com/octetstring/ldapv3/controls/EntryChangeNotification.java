package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int64;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class EntryChangeNotification implements ASN1Object {
   protected Int8 changeType;
   protected OctetString previousDN;
   protected Int64 changeNumber;

   public EntryChangeNotification() {
   }

   public EntryChangeNotification(Int8 changeType, OctetString previousDN, Int64 changeNumber) {
      if (changeType == null) {
         throw new IllegalArgumentException();
      } else {
         this.changeType = changeType;
         this.previousDN = previousDN;
         this.changeNumber = changeNumber;
      }
   }

   public EntryChangeNotification(EntryChangeNotification value) {
      this.changeType = value.getChangeType();
      this.previousDN = value.getPreviousDN();
      this.changeNumber = value.getChangeNumber();
   }

   public Int8 getChangeType() {
      return this.changeType;
   }

   public void setChangeType(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.changeType = val;
      }
   }

   public OctetString getPreviousDN() {
      return this.previousDN;
   }

   public void setPreviousDN(OctetString val) {
      this.previousDN = val;
   }

   public Int64 getChangeNumber() {
      return this.changeNumber;
   }

   public void setChangeNumber(Int64 val) {
      this.changeNumber = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("changeType ").append(this.changeType.toString());
      if (this.previousDN != null) {
         buf.append(", previousDN ").append(this.previousDN.toString());
      }

      if (this.changeNumber != null) {
         buf.append(", changeNumber ").append(this.changeNumber.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.changeType.print(out, newindent, "changeType ", this.previousDN == null && this.changeNumber == null ? "" : ",", flags);
      if (this.previousDN != null) {
         this.previousDN.print(out, newindent, "previousDN ", this.changeNumber != null ? "," : "", flags);
      }

      if (this.changeNumber != null) {
         this.changeNumber.print(out, newindent, "changeNumber ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int32;
import java.io.PrintWriter;

public class LDAPMessage implements ASN1Object {
   protected Int32 messageID;
   protected LDAPMessage_protocolOp protocolOp;
   protected Controls controls;

   public LDAPMessage() {
   }

   public LDAPMessage(Int32 messageID, LDAPMessage_protocolOp protocolOp, Controls controls) {
      if (messageID == null) {
         throw new IllegalArgumentException();
      } else {
         this.messageID = messageID;
         if (protocolOp == null) {
            throw new IllegalArgumentException();
         } else {
            this.protocolOp = protocolOp;
            this.controls = controls;
         }
      }
   }

   public LDAPMessage(LDAPMessage value) {
      this.messageID = value.getMessageID();
      this.protocolOp = value.getProtocolOp();
      this.controls = value.getControls();
   }

   public Int32 getMessageID() {
      return this.messageID;
   }

   public void setMessageID(Int32 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.messageID = val;
      }
   }

   public LDAPMessage_protocolOp getProtocolOp() {
      return this.protocolOp;
   }

   public void setProtocolOp(LDAPMessage_protocolOp val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.protocolOp = val;
      }
   }

   public Controls getControls() {
      return this.controls;
   }

   public void setControls(Controls val) {
      this.controls = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("messageID ").append(this.messageID.toString());
      buf.append(", protocolOp ").append(this.protocolOp.toString());
      if (this.controls != null) {
         buf.append(", controls ").append(this.controls.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.messageID.print(out, newindent, "messageID ", ",", flags);
      this.protocolOp.print(out, newindent, "protocolOp ", this.controls != null ? "," : "", flags);
      if (this.controls != null) {
         this.controls.print(out, newindent, "controls ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

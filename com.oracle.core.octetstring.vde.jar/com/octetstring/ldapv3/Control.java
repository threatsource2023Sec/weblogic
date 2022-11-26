package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class Control implements ASN1Object {
   protected OctetString controlType;
   protected Bool criticality;
   protected OctetString controlValue;

   public Control() {
   }

   public Control(OctetString controlType, Bool criticality, OctetString controlValue) {
      if (controlType == null) {
         throw new IllegalArgumentException();
      } else {
         this.controlType = controlType;
         this.criticality = criticality;
         this.controlValue = controlValue;
      }
   }

   public Control(Control value) {
      this.controlType = value.getControlType();
      this.criticality = value.getCriticality();
      this.controlValue = value.getControlValue();
   }

   public OctetString getControlType() {
      return this.controlType;
   }

   public void setControlType(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.controlType = val;
      }
   }

   public Bool getCriticality() {
      return this.criticality;
   }

   public void setCriticality(Bool val) {
      this.criticality = val;
   }

   public OctetString getControlValue() {
      return this.controlValue;
   }

   public void setControlValue(OctetString val) {
      this.controlValue = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("controlType ").append(this.controlType.toString());
      if (this.criticality != null) {
         buf.append(", criticality ").append(this.criticality.toString());
      }

      if (this.controlValue != null) {
         buf.append(", controlValue ").append(this.controlValue.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.controlType.print(out, newindent, "controlType ", this.criticality == null && this.controlValue == null ? "" : ",", flags);
      if (this.criticality != null) {
         this.criticality.print(out, newindent, "criticality ", this.controlValue != null ? "," : "", flags);
      }

      if (this.controlValue != null) {
         this.controlValue.print(out, newindent, "controlValue ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}

package com.asn1c.core;

import java.io.PrintWriter;

public class CharacterString implements ASN1Object, Comparable {
   protected Identification identification;
   protected DataValue dataValue;

   public CharacterString() {
      this((Identification)null, (DataValue)null);
   }

   public CharacterString(Identification var1, DataValue var2) {
      this.identification = var1;
      this.dataValue = var2;
   }

   public CharacterString(CharacterString var1) {
      this.identification = new Identification(var1.identification);
      this.dataValue = new DataValue(var1.dataValue);
   }

   public void setValue(Identification var1, DataValue var2) {
      this.identification = var1;
      this.dataValue = var2;
   }

   public void setValue(CharacterString var1) {
      this.identification = new Identification(var1.identification);
      this.dataValue = new DataValue(var1.dataValue);
   }

   public Identification getIdentification() {
      return this.identification;
   }

   public void setIdentification(Identification var1) {
      this.identification = var1;
   }

   public DataValue getDataValue() {
      return this.dataValue;
   }

   public void setDataValue(DataValue var1) {
      this.dataValue = var1;
   }

   public int compareTo(Object var1) {
      CharacterString var2 = (CharacterString)var1;
      int var3 = this.identification.compareTo(var2.identification);
      return var3 != 0 ? var3 : this.dataValue.compareTo(var2.dataValue);
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + "{");
      this.identification.print(var1, var2 + "    ", "identification ", ",", var5);
      this.dataValue.print(var1, var2 + "    ", "data-value ", "", var5);
      var1.println(var2 + "}" + var4);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("{ identification = ");
      var1.append(this.identification);
      var1.append(", data-value = ");
      var1.append(this.dataValue);
      var1.append(" }");
      return var1.toString();
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }
}

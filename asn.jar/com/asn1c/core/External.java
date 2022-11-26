package com.asn1c.core;

import java.io.PrintWriter;

public class External implements ASN1Object, Comparable {
   protected Identification identification;
   protected ObjectDescriptor dataValueDescriptor;
   protected DataValue dataValue;

   public External() {
      this.identification = null;
      this.dataValueDescriptor = null;
      this.dataValue = null;
   }

   public External(Identification var1, ObjectDescriptor var2, DataValue var3) {
      int var4 = var1.getSelector();
      if (var4 != 0 && var4 != 4 && var4 != 5) {
         this.identification = var1;
         this.dataValueDescriptor = var2;
         this.dataValue = var3;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public External(External var1) {
      this.identification = var1.identification;
      this.dataValueDescriptor = var1.dataValueDescriptor;
      this.dataValue = var1.dataValue;
   }

   public void setValue(Identification var1, ObjectDescriptor var2, DataValue var3) {
      int var4 = var1.getSelector();
      if (var4 != 0 && var4 != 4 && var4 != 5) {
         this.identification = var1;
         this.dataValueDescriptor = var2;
         this.dataValue = var3;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void setValue(External var1) {
      this.identification = new Identification(var1.identification);
      if (var1.dataValueDescriptor != null) {
         this.dataValueDescriptor = new ObjectDescriptor(var1.dataValueDescriptor);
      } else {
         this.dataValueDescriptor = null;
      }

      this.dataValue = new DataValue(var1.dataValue);
   }

   public int compareTo(Object var1) {
      External var2 = (External)var1;
      int var3 = this.identification.compareTo(var2.identification);
      if (var3 != 0) {
         return var3;
      } else if (this.dataValueDescriptor != null && var2.dataValueDescriptor == null) {
         return 1;
      } else if (this.dataValueDescriptor == null && var2.dataValueDescriptor != null) {
         return -1;
      } else {
         if (this.dataValueDescriptor != null && var2.dataValueDescriptor != null) {
            var3 = this.dataValueDescriptor.compareTo(var2.dataValueDescriptor);
            if (var3 != 0) {
               return var3;
            }
         }

         return this.dataValue.compareTo(var2.dataValue);
      }
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + "{");
      this.identification.print(var1, var2 + "    ", "identification ", ",", var5);
      if (this.dataValueDescriptor != null) {
         this.dataValueDescriptor.print(var1, var2 + "    ", "data-value-descriptor ", ",", var5);
      }

      this.dataValue.print(var1, var2 + "    ", "data-value ", ",", var5);
      var1.println(var2 + "}" + var4);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("{ identification = ");
      var1.append(this.identification);
      if (this.dataValueDescriptor != null) {
         var1.append(", dataValueDescriptor = ");
         var1.append(this.dataValueDescriptor);
      }

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

   public Identification getIdentification() {
      return this.identification;
   }

   public void setIdentification(Identification var1) {
      int var2 = var1.getSelector();
      if (var2 != 0 && var2 != 4 && var2 != 5) {
         this.identification = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ObjectDescriptor getDataValueDescriptor() {
      return this.dataValueDescriptor;
   }

   public void setDataValueDescriptor(ObjectDescriptor var1) {
      this.dataValueDescriptor = var1;
   }

   public DataValue getDataValue() {
      return this.dataValue;
   }

   public void setDataValue(DataValue var1) {
      this.dataValue = var1;
   }
}

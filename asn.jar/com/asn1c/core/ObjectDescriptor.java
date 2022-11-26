package com.asn1c.core;

import java.io.PrintWriter;

public class ObjectDescriptor implements ASN1Object, Comparable {
   protected String objectDescriptor;

   public ObjectDescriptor() {
      this.objectDescriptor = null;
   }

   public ObjectDescriptor(String var1) {
      this.objectDescriptor = var1;
   }

   public ObjectDescriptor(String16 var1) {
      this.objectDescriptor = var1.toString();
   }

   public ObjectDescriptor(ObjectDescriptor var1) {
      this.objectDescriptor = var1.objectDescriptor;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      int var7 = this.objectDescriptor.length();
      StringBuffer var9 = new StringBuffer(var7 + 2);
      boolean var10 = false;

      for(int var6 = 0; var6 < var7; ++var6) {
         char var8 = this.objectDescriptor.charAt(var6);
         if (var8 >= ' ' && var8 < 127) {
            if (!var10) {
               if (var6 > 0) {
                  var9.append(',');
               }

               var9.append(" \"");
               var10 = true;
            }

            if (var8 == '"') {
               var9.append(var8);
            }

            var9.append(var8);
         } else if (var8 < 128) {
            if (var10) {
               var9.append("\"");
               var10 = false;
            }

            if (var6 > 0) {
               var9.append(',');
            }

            var9.append(" { ").append(var8 / 16).append(", ").append(var8 & 15).append(" }");
         } else {
            if (var10) {
               var9.append("\"");
               var10 = false;
            }

            if (var6 > 0) {
               var9.append(',');
            }

            var9.append(" { 0, 0, ").append(var8 / 16).append(", ").append(var8 & 15).append(" }");
         }
      }

      if (var10) {
         var9.append("\"");
      }

      var1.println(var2 + var3 + "{" + var9.toString() + " }" + var4);
   }

   public String toString() {
      return this.objectDescriptor;
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public String getValue() {
      return this.objectDescriptor;
   }

   public void setValue(String var1) {
      this.objectDescriptor = var1;
   }

   public void setValue(String16 var1) {
      this.objectDescriptor = var1.toString();
   }

   public void setValue(ObjectDescriptor var1) {
      this.objectDescriptor = var1.objectDescriptor;
   }

   public int compareTo(Object var1) {
      ObjectDescriptor var2 = (ObjectDescriptor)var1;
      return this.objectDescriptor.compareTo(var2.objectDescriptor);
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }
}

package org.python.objectweb.asm.commons;

class SerialVersionUIDAdder$Item implements Comparable {
   final String name;
   final int access;
   final String desc;

   SerialVersionUIDAdder$Item(String var1, int var2, String var3) {
      this.name = var1;
      this.access = var2;
      this.desc = var3;
   }

   public int compareTo(SerialVersionUIDAdder$Item var1) {
      int var2 = this.name.compareTo(var1.name);
      if (var2 == 0) {
         var2 = this.desc.compareTo(var1.desc);
      }

      return var2;
   }

   public boolean equals(Object var1) {
      if (var1 instanceof SerialVersionUIDAdder$Item) {
         return this.compareTo((SerialVersionUIDAdder$Item)var1) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (this.name + this.desc).hashCode();
   }
}

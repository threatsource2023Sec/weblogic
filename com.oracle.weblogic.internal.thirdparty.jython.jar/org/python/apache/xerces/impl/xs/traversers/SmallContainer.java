package org.python.apache.xerces.impl.xs.traversers;

class SmallContainer extends Container {
   String[] keys;

   SmallContainer(int var1) {
      this.keys = new String[var1];
      this.values = new OneAttr[var1];
   }

   void put(String var1, OneAttr var2) {
      this.keys[this.pos] = var1;
      this.values[this.pos++] = var2;
   }

   OneAttr get(String var1) {
      for(int var2 = 0; var2 < this.pos; ++var2) {
         if (this.keys[var2].equals(var1)) {
            return this.values[var2];
         }
      }

      return null;
   }
}

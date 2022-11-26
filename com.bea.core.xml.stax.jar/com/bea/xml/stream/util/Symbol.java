package com.bea.xml.stream.util;

class Symbol {
   String name;
   String value;
   int depth;

   Symbol(String name, String value, int depth) {
      this.name = name;
      this.value = value;
      this.depth = depth;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public int getDepth() {
      return this.depth;
   }

   public String toString() {
      return "[" + this.depth + "][" + this.name + "][" + this.value + "]";
   }
}

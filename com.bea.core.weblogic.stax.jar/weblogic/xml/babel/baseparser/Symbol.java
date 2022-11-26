package weblogic.xml.babel.baseparser;

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

   public String toString() {
      return "[" + this.depth + "][" + this.name + "][" + this.value + "]";
   }
}

package org.python.compiler;

public class SymInfo {
   public int flags;
   public int locals_index;
   public int env_index;

   public SymInfo(int flags) {
      this.flags = flags;
   }

   public SymInfo(int flags, int locals_index) {
      this.flags = flags;
      this.locals_index = locals_index;
   }

   public String toString() {
      return "SymInfo[" + this.flags + " " + this.locals_index + " " + this.env_index + "]";
   }
}

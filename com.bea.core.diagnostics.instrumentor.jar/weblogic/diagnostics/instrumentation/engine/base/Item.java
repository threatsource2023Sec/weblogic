package weblogic.diagnostics.instrumentation.engine.base;

import java.io.DataOutputStream;
import java.io.IOException;

class Item implements Comparable {
   private String name;
   private int access;
   private String desc;

   Item(String name, int access, String desc) {
      this.name = name;
      this.access = access;
      this.desc = desc;
   }

   public int compareTo(Object o) {
      Item other = (Item)o;
      int retVal = this.name.compareTo(other.name);
      if (retVal == 0) {
         retVal = this.desc.compareTo(other.desc);
      }

      return retVal;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof Item)) {
         return false;
      } else {
         Item other = (Item)obj;
         return this.access == other.access && this.name.equals(other.name) && this.desc.equals(other.desc);
      }
   }

   public int hashCode() {
      return this.access + 7 * this.name.hashCode() + 17 * this.desc.hashCode();
   }

   void write(DataOutputStream dos, boolean dotted) throws IOException {
      dos.writeUTF(this.name);
      dos.writeInt(this.access);
      if (dotted) {
         dos.writeUTF(this.desc.replace('/', '.'));
      } else {
         dos.writeUTF(this.desc);
      }

   }
}

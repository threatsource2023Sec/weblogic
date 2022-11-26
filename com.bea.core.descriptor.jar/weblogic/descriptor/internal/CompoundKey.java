package weblogic.descriptor.internal;

import java.util.Arrays;

public class CompoundKey {
   private final Object[] components;

   public CompoundKey(Object[] components) {
      this.components = components;
   }

   public int hashCode() {
      int hashCode = 1;

      for(int i = 0; i < this.components.length; ++i) {
         Object component = this.components[i];
         hashCode = 31 * hashCode + (component == null ? 0 : component.hashCode());
      }

      return hashCode;
   }

   public String toString() {
      String buf = "[CompoundKey: ";

      for(int i = 0; i < this.components.length; ++i) {
         if (this.components[i] != null) {
            buf = buf + this.components[i].toString();
         }
      }

      return buf + "]";
   }

   public boolean equals(Object other) {
      return !(other instanceof CompoundKey) ? false : Arrays.equals(this.components, ((CompoundKey)other).components);
   }
}

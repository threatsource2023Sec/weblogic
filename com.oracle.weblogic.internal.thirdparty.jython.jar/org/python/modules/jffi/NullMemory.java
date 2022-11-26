package org.python.modules.jffi;

public class NullMemory extends InvalidMemory implements DirectMemory {
   static final NullMemory INSTANCE = new NullMemory();

   public NullMemory() {
      super("NULL pointer access");
   }

   public long getAddress() {
      return 0L;
   }

   public boolean isNull() {
      return true;
   }

   public final boolean isDirect() {
      return true;
   }

   public boolean equals(Object obj) {
      return obj instanceof DirectMemory && ((DirectMemory)obj).getAddress() == 0L;
   }

   public int hashCode() {
      return 0;
   }
}

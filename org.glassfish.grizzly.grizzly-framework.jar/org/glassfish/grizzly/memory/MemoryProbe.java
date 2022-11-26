package org.glassfish.grizzly.memory;

public interface MemoryProbe {
   void onBufferAllocateEvent(int var1);

   void onBufferAllocateFromPoolEvent(int var1);

   void onBufferReleaseToPoolEvent(int var1);

   public static class Adapter implements MemoryProbe {
      public void onBufferAllocateEvent(int size) {
      }

      public void onBufferAllocateFromPoolEvent(int size) {
      }

      public void onBufferReleaseToPoolEvent(int size) {
      }
   }
}

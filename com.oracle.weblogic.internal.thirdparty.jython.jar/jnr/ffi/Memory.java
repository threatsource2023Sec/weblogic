package jnr.ffi;

public final class Memory {
   private Memory() {
   }

   public static Pointer allocate(Runtime runtime, int size) {
      return runtime.getMemoryManager().allocate(size);
   }

   public static Pointer allocate(Runtime runtime, NativeType type) {
      return runtime.getMemoryManager().allocate(runtime.findType(type).size());
   }

   public static Pointer allocate(Runtime runtime, Type type) {
      return runtime.getMemoryManager().allocate(type.size());
   }

   public static Pointer allocate(Runtime runtime, TypeAlias type) {
      return runtime.getMemoryManager().allocate(runtime.findType(type).size());
   }

   public static Pointer allocateDirect(Runtime runtime, int size) {
      return runtime.getMemoryManager().allocateDirect(size);
   }

   public static Pointer allocateDirect(Runtime runtime, NativeType type) {
      return runtime.getMemoryManager().allocateDirect(runtime.findType(type).size());
   }

   public static Pointer allocateDirect(Runtime runtime, TypeAlias type) {
      return runtime.getMemoryManager().allocateDirect(runtime.findType(type).size());
   }

   public static Pointer allocateDirect(Runtime runtime, int size, boolean clear) {
      return runtime.getMemoryManager().allocateDirect(size, clear);
   }

   public static Pointer allocateTemporary(Runtime runtime, NativeType type) {
      return runtime.getMemoryManager().allocateTemporary(runtime.findType(type).size(), true);
   }

   public static Pointer allocateTemporary(Runtime runtime, TypeAlias type) {
      return runtime.getMemoryManager().allocateTemporary(runtime.findType(type).size(), true);
   }

   public static Pointer allocateTemporary(Runtime runtime, NativeType type, boolean clear) {
      return runtime.getMemoryManager().allocateTemporary(runtime.findType(type).size(), clear);
   }
}

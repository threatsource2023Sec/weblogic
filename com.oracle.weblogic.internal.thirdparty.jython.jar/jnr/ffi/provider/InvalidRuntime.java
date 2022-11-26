package jnr.ffi.provider;

import java.nio.ByteOrder;
import jnr.ffi.NativeType;
import jnr.ffi.ObjectReferenceManager;
import jnr.ffi.Runtime;
import jnr.ffi.Type;
import jnr.ffi.TypeAlias;

class InvalidRuntime extends Runtime {
   private final String message;
   private final Throwable cause;

   InvalidRuntime(String message, Throwable cause) {
      this.message = message;
      this.cause = cause;
   }

   public Type findType(NativeType type) {
      throw this.newLoadError();
   }

   public Type findType(TypeAlias type) {
      throw this.newLoadError();
   }

   public MemoryManager getMemoryManager() {
      throw this.newLoadError();
   }

   public ClosureManager getClosureManager() {
      throw this.newLoadError();
   }

   public ObjectReferenceManager newObjectReferenceManager() {
      throw this.newLoadError();
   }

   public int getLastError() {
      throw this.newLoadError();
   }

   public void setLastError(int error) {
      throw this.newLoadError();
   }

   public long addressMask() {
      throw this.newLoadError();
   }

   public int addressSize() {
      throw this.newLoadError();
   }

   public int longSize() {
      throw this.newLoadError();
   }

   public ByteOrder byteOrder() {
      throw this.newLoadError();
   }

   public boolean isCompatible(Runtime other) {
      throw this.newLoadError();
   }

   private UnsatisfiedLinkError newLoadError() {
      UnsatisfiedLinkError error = new UnsatisfiedLinkError(this.message);
      error.initCause(this.cause);
      throw error;
   }
}

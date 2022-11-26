package jnr.ffi.provider;

import jnr.ffi.NativeType;
import jnr.ffi.Type;

public final class BadType extends Type {
   private final String typeName;

   public BadType(String typeName) {
      this.typeName = typeName;
   }

   public final int alignment() {
      throw new RuntimeException("invalid type: " + this.typeName);
   }

   public final int size() {
      throw new RuntimeException("invalid type: " + this.typeName);
   }

   public NativeType getNativeType() {
      throw new RuntimeException("invalid type: " + this.typeName);
   }
}

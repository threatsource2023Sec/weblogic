package jnr.posix;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.ToNativeContext;

public final class HANDLE {
   public static final long INVALID_HANDLE_VALUE = -1L;
   private final Pointer pointer;
   public static final DataConverter Converter = new DataConverter() {
      public Pointer toNative(HANDLE value, ToNativeContext context) {
         return value != null ? value.pointer : null;
      }

      public HANDLE fromNative(Pointer nativeValue, FromNativeContext context) {
         return nativeValue != null ? new HANDLE(nativeValue) : null;
      }

      public Class nativeType() {
         return Pointer.class;
      }
   };

   public HANDLE(Pointer pointer) {
      this.pointer = pointer;
   }

   public final Pointer toPointer() {
      return this.pointer;
   }

   public final boolean isValid() {
      return this.pointer.address() != (-1L & Runtime.getSystemRuntime().addressMask());
   }

   public static HANDLE valueOf(Pointer value) {
      return new HANDLE(value);
   }

   public static HANDLE valueOf(long value) {
      return new HANDLE(Runtime.getSystemRuntime().getMemoryManager().newPointer(value));
   }
}

package jnr.posix;

import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.posix.util.WindowsHelpers;

public final class WString {
   static final Runtime runtime = Runtime.getSystemRuntime();
   private final byte[] bytes;
   public static final ToNativeConverter Converter = new ToNativeConverter() {
      public Pointer toNative(WString value, ToNativeContext context) {
         if (value == null) {
            return null;
         } else {
            Pointer memory = Memory.allocateDirect(WString.runtime, value.bytes.length + 1, true);
            memory.put(0L, (byte[])value.bytes, 0, value.bytes.length);
            return memory;
         }
      }

      public Class nativeType() {
         return Pointer.class;
      }
   };

   WString(String string) {
      this.bytes = WindowsHelpers.toWString(string);
   }

   private WString(byte[] bytes) {
      this.bytes = bytes;
   }

   public static WString path(String path) {
      return new WString(path(path, false));
   }

   public static byte[] path(String path, boolean longPathExtensionNeeded) {
      if (longPathExtensionNeeded && path.length() > 240) {
         if (path.startsWith("//")) {
            path = "//?/UNC/" + path.substring(2);
         } else if (path.startsWith("\\\\")) {
            path = "\\\\?\\UNC\\" + path.substring(2);
         } else if (WindowsHelpers.isDriveLetterPath(path)) {
            if (path.contains("/")) {
               path = "//?/" + path;
            } else {
               path = "\\\\?\\" + path;
            }
         }
      }

      return WindowsHelpers.toWPath(path);
   }
}

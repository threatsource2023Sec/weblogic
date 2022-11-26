package org.jboss.classfilewriter;

public class AccessFlag {
   public static final int PUBLIC = 1;
   public static final int PRIVATE = 2;
   public static final int PROTECTED = 4;
   public static final int STATIC = 8;
   public static final int FINAL = 16;
   public static final int SYNCHRONIZED = 32;
   public static final int VOLATILE = 64;
   public static final int BRIDGE = 64;
   public static final int TRANSIENT = 128;
   public static final int VARARGS = 128;
   public static final int NATIVE = 256;
   public static final int INTERFACE = 512;
   public static final int ABSTRACT = 1024;
   public static final int STRICT = 2048;
   public static final int SYNTHETIC = 4096;
   public static final int ANNOTATION = 8192;
   public static final int ENUM = 16384;
   public static final int SUPER = 32;

   public static int of(int... modifiers) {
      int val = 0;
      int[] var2 = modifiers;
      int var3 = modifiers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int m = var2[var4];
         val |= m;
      }

      return val;
   }

   private AccessFlag() {
   }
}

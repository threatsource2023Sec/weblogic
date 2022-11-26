package com.kenai.jffi;

public final class NativeMethod {
   final long function;
   final String name;
   final String signature;

   public NativeMethod(long address, String name, String signature) {
      this.function = address;
      this.name = name;
      this.signature = signature;
   }
}

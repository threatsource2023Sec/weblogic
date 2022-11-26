package org.python.sizeof;

final class MurmurHash3 {
   private MurmurHash3() {
   }

   public static int hash(int k) {
      k ^= k >>> 16;
      k *= -2048144789;
      k ^= k >>> 13;
      k *= -1028477387;
      k ^= k >>> 16;
      return k;
   }

   public static long hash(long k) {
      k ^= k >>> 33;
      k *= -49064778989728563L;
      k ^= k >>> 33;
      k *= -4265267296055464877L;
      k ^= k >>> 33;
      return k;
   }
}

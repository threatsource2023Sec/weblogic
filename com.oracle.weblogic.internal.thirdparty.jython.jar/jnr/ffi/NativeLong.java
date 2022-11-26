package jnr.ffi;

public final class NativeLong extends Number implements Comparable {
   private static final NativeLong ZERO = new NativeLong(0);
   private static final NativeLong ONE = new NativeLong(1);
   private static final NativeLong MINUS_ONE = new NativeLong(-1);
   private final long value;

   public NativeLong(long value) {
      this.value = value;
   }

   public NativeLong(int value) {
      this.value = (long)value;
   }

   public final int intValue() {
      return (int)this.value;
   }

   public final long longValue() {
      return this.value;
   }

   public final float floatValue() {
      return (float)this.value;
   }

   public final double doubleValue() {
      return (double)this.value;
   }

   public final int hashCode() {
      return (int)(this.value ^ this.value >>> 32);
   }

   public final boolean equals(Object obj) {
      return obj instanceof NativeLong && this.value == ((NativeLong)obj).value;
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public final int compareTo(NativeLong other) {
      return this.value < other.value ? -1 : (this.value > other.value ? 1 : 0);
   }

   private static NativeLong _valueOf(long value) {
      return value >= -128L && value <= 127L ? NativeLong.Cache.cache[128 + (int)value] : new NativeLong(value);
   }

   private static NativeLong _valueOf(int value) {
      return value >= -128 && value <= 127 ? NativeLong.Cache.cache[128 + value] : new NativeLong(value);
   }

   public static NativeLong valueOf(long value) {
      return value == 0L ? ZERO : (value == 1L ? ONE : (value == -1L ? MINUS_ONE : _valueOf(value)));
   }

   public static NativeLong valueOf(int value) {
      return value == 0 ? ZERO : (value == 1 ? ONE : (value == -1 ? MINUS_ONE : _valueOf(value)));
   }

   private static final class Cache {
      static final NativeLong[] cache = new NativeLong[256];

      static {
         for(int i = 0; i < cache.length; ++i) {
            cache[i] = new NativeLong(i - 128);
         }

         cache[128] = NativeLong.ZERO;
         cache[129] = NativeLong.ONE;
         cache[127] = NativeLong.MINUS_ONE;
      }
   }
}

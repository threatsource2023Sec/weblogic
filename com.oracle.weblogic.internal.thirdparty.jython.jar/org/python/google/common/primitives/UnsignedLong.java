package org.python.google.common.primitives;

import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true
)
public final class UnsignedLong extends Number implements Comparable, Serializable {
   private static final long UNSIGNED_MASK = Long.MAX_VALUE;
   public static final UnsignedLong ZERO = new UnsignedLong(0L);
   public static final UnsignedLong ONE = new UnsignedLong(1L);
   public static final UnsignedLong MAX_VALUE = new UnsignedLong(-1L);
   private final long value;

   private UnsignedLong(long value) {
      this.value = value;
   }

   public static UnsignedLong fromLongBits(long bits) {
      return new UnsignedLong(bits);
   }

   @CanIgnoreReturnValue
   public static UnsignedLong valueOf(long value) {
      Preconditions.checkArgument(value >= 0L, "value (%s) is outside the range for an unsigned long value", value);
      return fromLongBits(value);
   }

   @CanIgnoreReturnValue
   public static UnsignedLong valueOf(BigInteger value) {
      Preconditions.checkNotNull(value);
      Preconditions.checkArgument(value.signum() >= 0 && value.bitLength() <= 64, "value (%s) is outside the range for an unsigned long value", (Object)value);
      return fromLongBits(value.longValue());
   }

   @CanIgnoreReturnValue
   public static UnsignedLong valueOf(String string) {
      return valueOf(string, 10);
   }

   @CanIgnoreReturnValue
   public static UnsignedLong valueOf(String string, int radix) {
      return fromLongBits(UnsignedLongs.parseUnsignedLong(string, radix));
   }

   public UnsignedLong plus(UnsignedLong val) {
      return fromLongBits(this.value + ((UnsignedLong)Preconditions.checkNotNull(val)).value);
   }

   public UnsignedLong minus(UnsignedLong val) {
      return fromLongBits(this.value - ((UnsignedLong)Preconditions.checkNotNull(val)).value);
   }

   public UnsignedLong times(UnsignedLong val) {
      return fromLongBits(this.value * ((UnsignedLong)Preconditions.checkNotNull(val)).value);
   }

   public UnsignedLong dividedBy(UnsignedLong val) {
      return fromLongBits(UnsignedLongs.divide(this.value, ((UnsignedLong)Preconditions.checkNotNull(val)).value));
   }

   public UnsignedLong mod(UnsignedLong val) {
      return fromLongBits(UnsignedLongs.remainder(this.value, ((UnsignedLong)Preconditions.checkNotNull(val)).value));
   }

   public int intValue() {
      return (int)this.value;
   }

   public long longValue() {
      return this.value;
   }

   public float floatValue() {
      float fValue = (float)(this.value & Long.MAX_VALUE);
      if (this.value < 0L) {
         fValue += 9.223372E18F;
      }

      return fValue;
   }

   public double doubleValue() {
      double dValue = (double)(this.value & Long.MAX_VALUE);
      if (this.value < 0L) {
         dValue += 9.223372036854776E18;
      }

      return dValue;
   }

   public BigInteger bigIntegerValue() {
      BigInteger bigInt = BigInteger.valueOf(this.value & Long.MAX_VALUE);
      if (this.value < 0L) {
         bigInt = bigInt.setBit(63);
      }

      return bigInt;
   }

   public int compareTo(UnsignedLong o) {
      Preconditions.checkNotNull(o);
      return UnsignedLongs.compare(this.value, o.value);
   }

   public int hashCode() {
      return Longs.hashCode(this.value);
   }

   public boolean equals(@Nullable Object obj) {
      if (obj instanceof UnsignedLong) {
         UnsignedLong other = (UnsignedLong)obj;
         return this.value == other.value;
      } else {
         return false;
      }
   }

   public String toString() {
      return UnsignedLongs.toString(this.value);
   }

   public String toString(int radix) {
      return UnsignedLongs.toString(this.value, radix);
   }
}

package org.python.google.common.primitives;

import java.math.BigInteger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public final class UnsignedInteger extends Number implements Comparable {
   public static final UnsignedInteger ZERO = fromIntBits(0);
   public static final UnsignedInteger ONE = fromIntBits(1);
   public static final UnsignedInteger MAX_VALUE = fromIntBits(-1);
   private final int value;

   private UnsignedInteger(int value) {
      this.value = value & -1;
   }

   public static UnsignedInteger fromIntBits(int bits) {
      return new UnsignedInteger(bits);
   }

   public static UnsignedInteger valueOf(long value) {
      Preconditions.checkArgument((value & 4294967295L) == value, "value (%s) is outside the range for an unsigned integer value", value);
      return fromIntBits((int)value);
   }

   public static UnsignedInteger valueOf(BigInteger value) {
      Preconditions.checkNotNull(value);
      Preconditions.checkArgument(value.signum() >= 0 && value.bitLength() <= 32, "value (%s) is outside the range for an unsigned integer value", (Object)value);
      return fromIntBits(value.intValue());
   }

   public static UnsignedInteger valueOf(String string) {
      return valueOf(string, 10);
   }

   public static UnsignedInteger valueOf(String string, int radix) {
      return fromIntBits(UnsignedInts.parseUnsignedInt(string, radix));
   }

   public UnsignedInteger plus(UnsignedInteger val) {
      return fromIntBits(this.value + ((UnsignedInteger)Preconditions.checkNotNull(val)).value);
   }

   public UnsignedInteger minus(UnsignedInteger val) {
      return fromIntBits(this.value - ((UnsignedInteger)Preconditions.checkNotNull(val)).value);
   }

   @GwtIncompatible
   public UnsignedInteger times(UnsignedInteger val) {
      return fromIntBits(this.value * ((UnsignedInteger)Preconditions.checkNotNull(val)).value);
   }

   public UnsignedInteger dividedBy(UnsignedInteger val) {
      return fromIntBits(UnsignedInts.divide(this.value, ((UnsignedInteger)Preconditions.checkNotNull(val)).value));
   }

   public UnsignedInteger mod(UnsignedInteger val) {
      return fromIntBits(UnsignedInts.remainder(this.value, ((UnsignedInteger)Preconditions.checkNotNull(val)).value));
   }

   public int intValue() {
      return this.value;
   }

   public long longValue() {
      return UnsignedInts.toLong(this.value);
   }

   public float floatValue() {
      return (float)this.longValue();
   }

   public double doubleValue() {
      return (double)this.longValue();
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf(this.longValue());
   }

   public int compareTo(UnsignedInteger other) {
      Preconditions.checkNotNull(other);
      return UnsignedInts.compare(this.value, other.value);
   }

   public int hashCode() {
      return this.value;
   }

   public boolean equals(@Nullable Object obj) {
      if (obj instanceof UnsignedInteger) {
         UnsignedInteger other = (UnsignedInteger)obj;
         return this.value == other.value;
      } else {
         return false;
      }
   }

   public String toString() {
      return this.toString(10);
   }

   public String toString(int radix) {
      return UnsignedInts.toString(this.value, radix);
   }
}

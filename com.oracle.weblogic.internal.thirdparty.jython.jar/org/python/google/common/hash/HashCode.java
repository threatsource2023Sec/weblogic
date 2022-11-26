package org.python.google.common.hash;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Ints;
import org.python.google.common.primitives.UnsignedInts;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public abstract class HashCode {
   private static final char[] hexDigits = "0123456789abcdef".toCharArray();

   HashCode() {
   }

   public abstract int bits();

   public abstract int asInt();

   public abstract long asLong();

   public abstract long padToLong();

   public abstract byte[] asBytes();

   @CanIgnoreReturnValue
   public int writeBytesTo(byte[] dest, int offset, int maxLength) {
      maxLength = Ints.min(maxLength, this.bits() / 8);
      Preconditions.checkPositionIndexes(offset, offset + maxLength, dest.length);
      this.writeBytesToImpl(dest, offset, maxLength);
      return maxLength;
   }

   abstract void writeBytesToImpl(byte[] var1, int var2, int var3);

   byte[] getBytesInternal() {
      return this.asBytes();
   }

   abstract boolean equalsSameBits(HashCode var1);

   public static HashCode fromInt(int hash) {
      return new IntHashCode(hash);
   }

   public static HashCode fromLong(long hash) {
      return new LongHashCode(hash);
   }

   public static HashCode fromBytes(byte[] bytes) {
      Preconditions.checkArgument(bytes.length >= 1, "A HashCode must contain at least 1 byte.");
      return fromBytesNoCopy((byte[])bytes.clone());
   }

   static HashCode fromBytesNoCopy(byte[] bytes) {
      return new BytesHashCode(bytes);
   }

   public static HashCode fromString(String string) {
      Preconditions.checkArgument(string.length() >= 2, "input string (%s) must have at least 2 characters", (Object)string);
      Preconditions.checkArgument(string.length() % 2 == 0, "input string (%s) must have an even number of characters", (Object)string);
      byte[] bytes = new byte[string.length() / 2];

      for(int i = 0; i < string.length(); i += 2) {
         int ch1 = decode(string.charAt(i)) << 4;
         int ch2 = decode(string.charAt(i + 1));
         bytes[i / 2] = (byte)(ch1 + ch2);
      }

      return fromBytesNoCopy(bytes);
   }

   private static int decode(char ch) {
      if (ch >= '0' && ch <= '9') {
         return ch - 48;
      } else if (ch >= 'a' && ch <= 'f') {
         return ch - 97 + 10;
      } else {
         throw new IllegalArgumentException("Illegal hexadecimal character: " + ch);
      }
   }

   public final boolean equals(@Nullable Object object) {
      if (!(object instanceof HashCode)) {
         return false;
      } else {
         HashCode that = (HashCode)object;
         return this.bits() == that.bits() && this.equalsSameBits(that);
      }
   }

   public final int hashCode() {
      if (this.bits() >= 32) {
         return this.asInt();
      } else {
         byte[] bytes = this.getBytesInternal();
         int val = bytes[0] & 255;

         for(int i = 1; i < bytes.length; ++i) {
            val |= (bytes[i] & 255) << i * 8;
         }

         return val;
      }
   }

   public final String toString() {
      byte[] bytes = this.getBytesInternal();
      StringBuilder sb = new StringBuilder(2 * bytes.length);
      byte[] var3 = bytes;
      int var4 = bytes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte b = var3[var5];
         sb.append(hexDigits[b >> 4 & 15]).append(hexDigits[b & 15]);
      }

      return sb.toString();
   }

   private static final class BytesHashCode extends HashCode implements Serializable {
      final byte[] bytes;
      private static final long serialVersionUID = 0L;

      BytesHashCode(byte[] bytes) {
         this.bytes = (byte[])Preconditions.checkNotNull(bytes);
      }

      public int bits() {
         return this.bytes.length * 8;
      }

      public byte[] asBytes() {
         return (byte[])this.bytes.clone();
      }

      public int asInt() {
         Preconditions.checkState(this.bytes.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.bytes.length);
         return this.bytes[0] & 255 | (this.bytes[1] & 255) << 8 | (this.bytes[2] & 255) << 16 | (this.bytes[3] & 255) << 24;
      }

      public long asLong() {
         Preconditions.checkState(this.bytes.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.bytes.length);
         return this.padToLong();
      }

      public long padToLong() {
         long retVal = (long)(this.bytes[0] & 255);

         for(int i = 1; i < Math.min(this.bytes.length, 8); ++i) {
            retVal |= ((long)this.bytes[i] & 255L) << i * 8;
         }

         return retVal;
      }

      void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
         System.arraycopy(this.bytes, 0, dest, offset, maxLength);
      }

      byte[] getBytesInternal() {
         return this.bytes;
      }

      boolean equalsSameBits(HashCode that) {
         if (this.bytes.length != that.getBytesInternal().length) {
            return false;
         } else {
            boolean areEqual = true;

            for(int i = 0; i < this.bytes.length; ++i) {
               areEqual &= this.bytes[i] == that.getBytesInternal()[i];
            }

            return areEqual;
         }
      }
   }

   private static final class LongHashCode extends HashCode implements Serializable {
      final long hash;
      private static final long serialVersionUID = 0L;

      LongHashCode(long hash) {
         this.hash = hash;
      }

      public int bits() {
         return 64;
      }

      public byte[] asBytes() {
         return new byte[]{(byte)((int)this.hash), (byte)((int)(this.hash >> 8)), (byte)((int)(this.hash >> 16)), (byte)((int)(this.hash >> 24)), (byte)((int)(this.hash >> 32)), (byte)((int)(this.hash >> 40)), (byte)((int)(this.hash >> 48)), (byte)((int)(this.hash >> 56))};
      }

      public int asInt() {
         return (int)this.hash;
      }

      public long asLong() {
         return this.hash;
      }

      public long padToLong() {
         return this.hash;
      }

      void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
         for(int i = 0; i < maxLength; ++i) {
            dest[offset + i] = (byte)((int)(this.hash >> i * 8));
         }

      }

      boolean equalsSameBits(HashCode that) {
         return this.hash == that.asLong();
      }
   }

   private static final class IntHashCode extends HashCode implements Serializable {
      final int hash;
      private static final long serialVersionUID = 0L;

      IntHashCode(int hash) {
         this.hash = hash;
      }

      public int bits() {
         return 32;
      }

      public byte[] asBytes() {
         return new byte[]{(byte)this.hash, (byte)(this.hash >> 8), (byte)(this.hash >> 16), (byte)(this.hash >> 24)};
      }

      public int asInt() {
         return this.hash;
      }

      public long asLong() {
         throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
      }

      public long padToLong() {
         return UnsignedInts.toLong(this.hash);
      }

      void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
         for(int i = 0; i < maxLength; ++i) {
            dest[offset + i] = (byte)(this.hash >> i * 8);
         }

      }

      boolean equalsSameBits(HashCode that) {
         return this.hash == that.asInt();
      }
   }
}

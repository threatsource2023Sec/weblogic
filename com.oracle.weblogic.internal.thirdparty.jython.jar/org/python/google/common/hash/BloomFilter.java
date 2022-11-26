package org.python.google.common.hash;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.math.DoubleMath;
import org.python.google.common.primitives.SignedBytes;
import org.python.google.common.primitives.UnsignedBytes;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public final class BloomFilter implements Predicate, Serializable {
   private final BloomFilterStrategies.BitArray bits;
   private final int numHashFunctions;
   private final Funnel funnel;
   private final Strategy strategy;

   private BloomFilter(BloomFilterStrategies.BitArray bits, int numHashFunctions, Funnel funnel, Strategy strategy) {
      Preconditions.checkArgument(numHashFunctions > 0, "numHashFunctions (%s) must be > 0", numHashFunctions);
      Preconditions.checkArgument(numHashFunctions <= 255, "numHashFunctions (%s) must be <= 255", numHashFunctions);
      this.bits = (BloomFilterStrategies.BitArray)Preconditions.checkNotNull(bits);
      this.numHashFunctions = numHashFunctions;
      this.funnel = (Funnel)Preconditions.checkNotNull(funnel);
      this.strategy = (Strategy)Preconditions.checkNotNull(strategy);
   }

   public BloomFilter copy() {
      return new BloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
   }

   public boolean mightContain(Object object) {
      return this.strategy.mightContain(object, this.funnel, this.numHashFunctions, this.bits);
   }

   /** @deprecated */
   @Deprecated
   public boolean apply(Object input) {
      return this.mightContain(input);
   }

   @CanIgnoreReturnValue
   public boolean put(Object object) {
      return this.strategy.put(object, this.funnel, this.numHashFunctions, this.bits);
   }

   public double expectedFpp() {
      return Math.pow((double)this.bits.bitCount() / (double)this.bitSize(), (double)this.numHashFunctions);
   }

   public long approximateElementCount() {
      long bitSize = this.bits.bitSize();
      long bitCount = this.bits.bitCount();
      double fractionOfBitsSet = (double)bitCount / (double)bitSize;
      return DoubleMath.roundToLong(-Math.log1p(-fractionOfBitsSet) * (double)bitSize / (double)this.numHashFunctions, RoundingMode.HALF_UP);
   }

   @VisibleForTesting
   long bitSize() {
      return this.bits.bitSize();
   }

   public boolean isCompatible(BloomFilter that) {
      Preconditions.checkNotNull(that);
      return this != that && this.numHashFunctions == that.numHashFunctions && this.bitSize() == that.bitSize() && this.strategy.equals(that.strategy) && this.funnel.equals(that.funnel);
   }

   public void putAll(BloomFilter that) {
      Preconditions.checkNotNull(that);
      Preconditions.checkArgument(this != that, "Cannot combine a BloomFilter with itself.");
      Preconditions.checkArgument(this.numHashFunctions == that.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, that.numHashFunctions);
      Preconditions.checkArgument(this.bitSize() == that.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), that.bitSize());
      Preconditions.checkArgument(this.strategy.equals(that.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, that.strategy);
      Preconditions.checkArgument(this.funnel.equals(that.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, that.funnel);
      this.bits.putAll(that.bits);
   }

   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof BloomFilter)) {
         return false;
      } else {
         BloomFilter that = (BloomFilter)object;
         return this.numHashFunctions == that.numHashFunctions && this.funnel.equals(that.funnel) && this.bits.equals(that.bits) && this.strategy.equals(that.strategy);
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.numHashFunctions, this.funnel, this.strategy, this.bits);
   }

   public static BloomFilter create(Funnel funnel, int expectedInsertions, double fpp) {
      return create(funnel, (long)expectedInsertions, fpp);
   }

   public static BloomFilter create(Funnel funnel, long expectedInsertions, double fpp) {
      return create(funnel, expectedInsertions, fpp, BloomFilterStrategies.MURMUR128_MITZ_64);
   }

   @VisibleForTesting
   static BloomFilter create(Funnel funnel, long expectedInsertions, double fpp, Strategy strategy) {
      Preconditions.checkNotNull(funnel);
      Preconditions.checkArgument(expectedInsertions >= 0L, "Expected insertions (%s) must be >= 0", expectedInsertions);
      Preconditions.checkArgument(fpp > 0.0, "False positive probability (%s) must be > 0.0", (Object)fpp);
      Preconditions.checkArgument(fpp < 1.0, "False positive probability (%s) must be < 1.0", (Object)fpp);
      Preconditions.checkNotNull(strategy);
      if (expectedInsertions == 0L) {
         expectedInsertions = 1L;
      }

      long numBits = optimalNumOfBits(expectedInsertions, fpp);
      int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);

      try {
         return new BloomFilter(new BloomFilterStrategies.BitArray(numBits), numHashFunctions, funnel, strategy);
      } catch (IllegalArgumentException var10) {
         throw new IllegalArgumentException("Could not create BloomFilter of " + numBits + " bits", var10);
      }
   }

   public static BloomFilter create(Funnel funnel, int expectedInsertions) {
      return create(funnel, (long)expectedInsertions);
   }

   public static BloomFilter create(Funnel funnel, long expectedInsertions) {
      return create(funnel, expectedInsertions, 0.03);
   }

   @VisibleForTesting
   static int optimalNumOfHashFunctions(long n, long m) {
      return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0)));
   }

   @VisibleForTesting
   static long optimalNumOfBits(long n, double p) {
      if (p == 0.0) {
         p = Double.MIN_VALUE;
      }

      return (long)((double)(-n) * Math.log(p) / (Math.log(2.0) * Math.log(2.0)));
   }

   private Object writeReplace() {
      return new SerialForm(this);
   }

   public void writeTo(OutputStream out) throws IOException {
      DataOutputStream dout = new DataOutputStream(out);
      dout.writeByte(SignedBytes.checkedCast((long)this.strategy.ordinal()));
      dout.writeByte(UnsignedBytes.checkedCast((long)this.numHashFunctions));
      dout.writeInt(this.bits.data.length);
      long[] var3 = this.bits.data;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         long value = var3[var5];
         dout.writeLong(value);
      }

   }

   public static BloomFilter readFrom(InputStream in, Funnel funnel) throws IOException {
      Preconditions.checkNotNull(in, "InputStream");
      Preconditions.checkNotNull(funnel, "Funnel");
      int strategyOrdinal = -1;
      int numHashFunctions = -1;
      int dataLength = -1;

      try {
         DataInputStream din = new DataInputStream(in);
         strategyOrdinal = din.readByte();
         numHashFunctions = UnsignedBytes.toInt(din.readByte());
         dataLength = din.readInt();
         Strategy strategy = BloomFilterStrategies.values()[strategyOrdinal];
         long[] data = new long[dataLength];

         for(int i = 0; i < data.length; ++i) {
            data[i] = din.readLong();
         }

         return new BloomFilter(new BloomFilterStrategies.BitArray(data), numHashFunctions, funnel, strategy);
      } catch (RuntimeException var9) {
         String message = "Unable to deserialize BloomFilter from InputStream. strategyOrdinal: " + strategyOrdinal + " numHashFunctions: " + numHashFunctions + " dataLength: " + dataLength;
         throw new IOException(message, var9);
      }
   }

   // $FF: synthetic method
   BloomFilter(BloomFilterStrategies.BitArray x0, int x1, Funnel x2, Strategy x3, Object x4) {
      this(x0, x1, x2, x3);
   }

   private static class SerialForm implements Serializable {
      final long[] data;
      final int numHashFunctions;
      final Funnel funnel;
      final Strategy strategy;
      private static final long serialVersionUID = 1L;

      SerialForm(BloomFilter bf) {
         this.data = bf.bits.data;
         this.numHashFunctions = bf.numHashFunctions;
         this.funnel = bf.funnel;
         this.strategy = bf.strategy;
      }

      Object readResolve() {
         return new BloomFilter(new BloomFilterStrategies.BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
      }
   }

   interface Strategy extends Serializable {
      boolean put(Object var1, Funnel var2, int var3, BloomFilterStrategies.BitArray var4);

      boolean mightContain(Object var1, Funnel var2, int var3, BloomFilterStrategies.BitArray var4);

      int ordinal();
   }
}

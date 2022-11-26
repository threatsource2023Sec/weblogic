package org.python.bouncycastle.util.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import org.python.bouncycastle.util.Pack;
import org.python.bouncycastle.util.encoders.Hex;

public class FixedSecureRandom extends SecureRandom {
   private static java.math.BigInteger REGULAR = new java.math.BigInteger("01020304ffffffff0506070811111111", 16);
   private static java.math.BigInteger ANDROID = new java.math.BigInteger("1111111105060708ffffffff01020304", 16);
   private static java.math.BigInteger CLASSPATH = new java.math.BigInteger("3020104ffffffff05060708111111", 16);
   private static final boolean isAndroidStyle;
   private static final boolean isClasspathStyle;
   private static final boolean isRegularStyle;
   private byte[] _data;
   private int _index;

   public FixedSecureRandom(byte[] var1) {
      this(new Source[]{new Data(var1)});
   }

   public FixedSecureRandom(byte[][] var1) {
      this((Source[])buildDataArray(var1));
   }

   private static Data[] buildDataArray(byte[][] var0) {
      Data[] var1 = new Data[var0.length];

      for(int var2 = 0; var2 != var0.length; ++var2) {
         var1[var2] = new Data(var0[var2]);
      }

      return var1;
   }

   public FixedSecureRandom(Source[] var1) {
      super((SecureRandomSpi)null, new DummyProvider());
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      int var3;
      byte[] var4;
      int var5;
      int var6;
      if (isRegularStyle) {
         if (isClasspathStyle) {
            for(var3 = 0; var3 != var1.length; ++var3) {
               try {
                  if (!(var1[var3] instanceof BigInteger)) {
                     var2.write(var1[var3].data);
                  } else {
                     var4 = var1[var3].data;
                     var5 = var4.length - var4.length % 4;

                     for(var6 = var4.length - var5 - 1; var6 >= 0; --var6) {
                        var2.write(var4[var6]);
                     }

                     for(var6 = var4.length - var5; var6 < var4.length; var6 += 4) {
                        var2.write(var4, var6, 4);
                     }
                  }
               } catch (IOException var9) {
                  throw new IllegalArgumentException("can't save value source.");
               }
            }
         } else {
            for(var3 = 0; var3 != var1.length; ++var3) {
               try {
                  var2.write(var1[var3].data);
               } catch (IOException var7) {
                  throw new IllegalArgumentException("can't save value source.");
               }
            }
         }
      } else {
         if (!isAndroidStyle) {
            throw new IllegalStateException("Unrecognized BigInteger implementation");
         }

         for(var3 = 0; var3 != var1.length; ++var3) {
            try {
               if (!(var1[var3] instanceof BigInteger)) {
                  var2.write(var1[var3].data);
               } else {
                  var4 = var1[var3].data;
                  var5 = var4.length - var4.length % 4;

                  for(var6 = 0; var6 < var5; var6 += 4) {
                     var2.write(var4, var4.length - (var6 + 4), 4);
                  }

                  if (var4.length - var5 != 0) {
                     for(var6 = 0; var6 != 4 - (var4.length - var5); ++var6) {
                        var2.write(0);
                     }
                  }

                  for(var6 = 0; var6 != var4.length - var5; ++var6) {
                     var2.write(var4[var5 + var6]);
                  }
               }
            } catch (IOException var8) {
               throw new IllegalArgumentException("can't save value source.");
            }
         }
      }

      this._data = var2.toByteArray();
   }

   public void nextBytes(byte[] var1) {
      System.arraycopy(this._data, this._index, var1, 0, var1.length);
      this._index += var1.length;
   }

   public byte[] generateSeed(int var1) {
      byte[] var2 = new byte[var1];
      this.nextBytes(var2);
      return var2;
   }

   public int nextInt() {
      int var1 = 0;
      var1 |= this.nextValue() << 24;
      var1 |= this.nextValue() << 16;
      var1 |= this.nextValue() << 8;
      var1 |= this.nextValue();
      return var1;
   }

   public long nextLong() {
      long var1 = 0L;
      var1 |= (long)this.nextValue() << 56;
      var1 |= (long)this.nextValue() << 48;
      var1 |= (long)this.nextValue() << 40;
      var1 |= (long)this.nextValue() << 32;
      var1 |= (long)this.nextValue() << 24;
      var1 |= (long)this.nextValue() << 16;
      var1 |= (long)this.nextValue() << 8;
      var1 |= (long)this.nextValue();
      return var1;
   }

   public boolean isExhausted() {
      return this._index == this._data.length;
   }

   private int nextValue() {
      return this._data[this._index++] & 255;
   }

   private static byte[] expandToBitLength(int var0, byte[] var1) {
      if ((var0 + 7) / 8 > var1.length) {
         byte[] var4 = new byte[(var0 + 7) / 8];
         System.arraycopy(var1, 0, var4, var4.length - var1.length, var1.length);
         if (isAndroidStyle && var0 % 8 != 0) {
            int var3 = Pack.bigEndianToInt(var4, 0);
            Pack.intToBigEndian(var3 << 8 - var0 % 8, var4, 0);
         }

         return var4;
      } else {
         if (isAndroidStyle && var0 < var1.length * 8 && var0 % 8 != 0) {
            int var2 = Pack.bigEndianToInt(var1, 0);
            Pack.intToBigEndian(var2 << 8 - var0 % 8, var1, 0);
         }

         return var1;
      }
   }

   static {
      java.math.BigInteger var0 = new java.math.BigInteger(128, new RandomChecker());
      java.math.BigInteger var1 = new java.math.BigInteger(120, new RandomChecker());
      isAndroidStyle = var0.equals(ANDROID);
      isRegularStyle = var0.equals(REGULAR);
      isClasspathStyle = var1.equals(CLASSPATH);
   }

   public static class BigInteger extends Source {
      public BigInteger(byte[] var1) {
         super(var1);
      }

      public BigInteger(int var1, byte[] var2) {
         super(FixedSecureRandom.expandToBitLength(var1, var2));
      }

      public BigInteger(String var1) {
         this(Hex.decode(var1));
      }

      public BigInteger(int var1, String var2) {
         super(FixedSecureRandom.expandToBitLength(var1, Hex.decode(var2)));
      }
   }

   public static class Data extends Source {
      public Data(byte[] var1) {
         super(var1);
      }
   }

   private static class DummyProvider extends Provider {
      DummyProvider() {
         super("BCFIPS_FIXED_RNG", 1.0, "BCFIPS Fixed Secure Random Provider");
      }
   }

   private static class RandomChecker extends SecureRandom {
      byte[] data = Hex.decode("01020304ffffffff0506070811111111");
      int index = 0;

      RandomChecker() {
         super((SecureRandomSpi)null, new DummyProvider());
      }

      public void nextBytes(byte[] var1) {
         System.arraycopy(this.data, this.index, var1, 0, var1.length);
         this.index += var1.length;
      }
   }

   public static class Source {
      byte[] data;

      Source(byte[] var1) {
         this.data = var1;
      }
   }
}

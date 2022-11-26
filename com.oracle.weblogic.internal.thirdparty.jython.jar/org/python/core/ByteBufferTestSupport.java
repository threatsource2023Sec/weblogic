package org.python.core;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.junit.Assert;

public class ByteBufferTestSupport {
   protected static byte[] sliceBytes(byte[] b, int start, int length, int stride) {
      return sliceBytes(b, 1, start, length, stride);
   }

   protected static byte[] sliceBytes(byte[] b, int itemsize, int start, int length, int stride) {
      byte[] a = new byte[length];
      int i = 0;

      for(int j = start; i < length; j += stride) {
         for(int k = 0; k < itemsize; ++k) {
            a[i + k] = b[j + k];
         }

         ++i;
      }

      return a;
   }

   static void assertBytesEqual(String message, byte[] expected, ByteBuffer bb) {
      byte[] actual = new byte[expected.length];
      bb.get(actual);
      assertBytesEqual(message, expected, actual);
   }

   static void assertBytesEqual(String message, byte[] expected, ByteBuffer bb, int stride) {
      assertBytesEqual(message, expected, 0, expected.length, (ByteBuffer)bb, stride);
   }

   static void assertBytesEqual(String message, byte[] expected, int expectedStart, int n, ByteBuffer bb, int stride) {
      int p = bb.position();
      byte[] actual = new byte[n];

      for(int k = 0; k < n; p += stride) {
         actual[k] = bb.get(p);
         ++k;
      }

      assertBytesEqual(message, expected, expectedStart, n, (byte[])actual, 0);
   }

   static void assertBytesEqual(String message, byte[] expected, byte[] actual) {
      Assert.assertEquals(message + " (array size)", (long)expected.length, (long)actual.length);
      assertBytesEqual(message, expected, 0, expected.length, actual, 0, 1);
   }

   static void assertBytesEqual(String message, byte[] expected, byte[] actual, int actualStart) {
      assertBytesEqual(message, expected, 0, expected.length, actual, actualStart, 1);
   }

   protected static void assertBytesEqual(String message, byte[] expected, int expectedStart, int n, byte[] actual, int actualStart) {
      assertBytesEqual(message, expected, expectedStart, n, actual, actualStart, 1);
   }

   static void assertBytesEqual(String message, byte[] expected, int expectedStart, int n, byte[] actual, int actualStart, int stride) {
      if (actualStart < 0) {
         Assert.fail(message + " (start<0 in result)");
      } else if (expectedStart < 0) {
         Assert.fail(message + " (start<0 in expected result): bug in test?");
      } else if (actualStart + (n - 1) * stride + 1 > actual.length) {
         Assert.fail(message + " (result too short)");
      } else if (expectedStart + n > expected.length) {
         Assert.fail(message + " (expected result too short): bug in test?");
      } else {
         int i = actualStart;
         int jLimit = expectedStart + n;

         int j;
         for(j = expectedStart; j < jLimit && actual[i] == expected[j]; ++j) {
            i += stride;
         }

         if (j < jLimit) {
            byte[] a = Arrays.copyOfRange(actual, actualStart, actualStart + n);
            byte[] e = Arrays.copyOfRange(expected, expectedStart, expectedStart + n);
            System.out.println("  expected:" + Arrays.toString(e));
            System.out.println("    actual:" + Arrays.toString(a));
            System.out.println("  _actual_:" + Arrays.toString(actual));
            Assert.fail(message + " (byte at " + j + ")");
         }
      }

   }

   static void assertIntsEqual(String message, int[] expected, int[] actual, int offset) {
      int n = expected.length;
      if (offset < 0) {
         Assert.fail(message + " (offset<0)");
      } else if (offset + n > actual.length) {
         Assert.fail(message + " (too short)");
      } else {
         int i = offset;

         int j;
         for(j = 0; j < n && actual[i++] == expected[j]; ++j) {
         }

         if (j < n) {
            System.out.println("  expected:" + Arrays.toString(expected));
            System.out.println("    actual:" + Arrays.toString(actual));
            Assert.fail(message + " (int at " + j + ")");
         }
      }

   }

   protected static void assertIntsEqual(String message, int[] expected, int[] actual) {
      int n = expected.length;
      Assert.assertEquals(message, (long)n, (long)actual.length);

      int j;
      for(j = 0; j < n && actual[j] == expected[j]; ++j) {
      }

      if (j < n) {
         System.out.println("  expected:" + Arrays.toString(expected));
         System.out.println("    actual:" + Arrays.toString(actual));
         Assert.fail(message + " (int at " + j + ")");
      }

   }

   static void checkReadCorrect(byte[] a, byte[] b, byte[] c, int t, int n, int u, int s, int p) {
      Assert.assertEquals("Storage size differs from reference", (long)a.length, (long)b.length);

      for(int k = 0; k < b.length; ++k) {
         if (a[k] != b[k]) {
            Assert.fail("Stored data changed during read");
         }
      }

      checkEqualInSlice(b, c, t, n, u, s, p);
   }

   static void checkWriteCorrect(byte[] a, byte[] b, byte[] c, int t, int n, int u, int s, int p) {
      Assert.assertEquals("Stored size has changed", (long)a.length, (long)b.length);
      checkEqualInSlice(b, c, t, n, u, s, p);
      checkUnchangedElsewhere(a, b, n, u, s, p);
   }

   static void checkEqualInSlice(byte[] b, byte[] c, int t, int n, int u, int s, int p) {
      checkSliceArgs(b, c, t, n, u, s, p);

      for(int i = 0; i < n; ++i) {
         int ci = t + i * u;
         int bi = s + i * p;

         for(int j = 0; j < u; ++ci) {
            if (c[ci] != b[bi]) {
               Assert.fail(String.format("contiguous data at %d not equal to buffer at %d", ci, bi));
            }

            ++j;
            ++bi;
         }
      }

   }

   static void checkUnchangedElsewhere(byte[] a, byte[] b, int n, int u, int s, int p) {
      Assert.assertEquals("Stored size has changed", (long)a.length, (long)b.length);
      Assert.assertFalse("Unit size exceeds spacing", u > p && u + p > 0);
      String bufferChangedAt = "buffer changed at %d (outside slice)";
      int high;
      int low;
      int absp;
      if (n < 1) {
         high = 0;
         low = 0;
         absp = 0;
      } else if (p >= 0) {
         absp = p;
         low = s;
         high = s + (n - 1) * p + u;
      } else {
         absp = -p;
         low = s + (n - 1) * p;
         high = s + u;
      }

      int k;
      for(k = 0; k < n - 1; ++k) {
         int bi = low + k * absp + u;

         for(int j = u; j < absp; ++bi) {
            if (b[bi] != a[bi]) {
               Assert.fail(String.format(bufferChangedAt, bi));
            }

            ++j;
         }
      }

      for(k = 0; k < low; ++k) {
         if (b[k] != a[k]) {
            Assert.fail(String.format(bufferChangedAt, k));
         }
      }

      for(k = Math.max(high, 0); k < b.length; ++k) {
         if (b[k] != a[k]) {
            Assert.fail(String.format(bufferChangedAt, k));
         }
      }

   }

   private static void checkSliceArgs(byte[] b, byte[] c, int t, int n, int u, int s, int p) {
      Assert.assertFalse("Slice data less than n units", c.length < t + n * u);
      Assert.assertFalse("Slice data exceeds destination", n * u > b.length);
      Assert.assertFalse("Unit size exceeds spacing", u > p && u + p > 0);
   }

   protected static class ByteMaterial {
      final int length;
      byte[] bytes;
      int[] ints;
      String string;

      public ByteMaterial(int[] a) {
         this.ints = (int[])a.clone();
         this.length = this.replicate();
      }

      public ByteMaterial(String s) {
         this.ints = new int[s.length()];

         for(int i = 0; i < this.ints.length; ++i) {
            this.ints[i] = 255 & s.charAt(i);
         }

         this.length = this.replicate();
      }

      public ByteMaterial(byte[] b) {
         this.ints = new int[b.length];

         for(int i = 0; i < this.ints.length; ++i) {
            this.ints[i] = 255 & b[i];
         }

         this.length = this.replicate();
      }

      public ByteMaterial(int start, int count, int inc) {
         this.ints = new int[count];
         int x = start;

         for(int i = 0; i < count; ++i) {
            this.ints[i] = x;
            x = x + inc & 255;
         }

         this.length = this.replicate();
      }

      private int replicate() {
         int n = this.ints.length;
         this.bytes = new byte[n];
         StringBuilder sb = new StringBuilder(n);

         for(int i = 0; i < n; ++i) {
            int x = this.ints[i];
            this.bytes[i] = (byte)x;
            sb.appendCodePoint(x);
         }

         this.string = sb.toString();
         return n;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder(100);
         sb.append("byte[").append(this.length).append("]={ ");

         for(int i = 0; i < this.length; ++i) {
            if (i > 0) {
               sb.append(", ");
            }

            if (i >= 5) {
               sb.append(" ...");
               break;
            }

            sb.append(this.ints[i]);
         }

         sb.append(" }");
         return sb.toString();
      }

      byte[] getBytes() {
         return (byte[])this.bytes.clone();
      }

      ByteBuffer getBuffer() {
         return ByteBuffer.wrap(this.getBytes());
      }

      ByteMaterial slice(int start, int length, int stride) {
         return new ByteMaterial(ByteBufferTestSupport.sliceBytes(this.bytes, 1, start, length, stride));
      }

      ByteMaterial slice(int itemsize, int start, int length, int stride) {
         return new ByteMaterial(ByteBufferTestSupport.sliceBytes(this.bytes, itemsize, start, length, stride));
      }
   }
}

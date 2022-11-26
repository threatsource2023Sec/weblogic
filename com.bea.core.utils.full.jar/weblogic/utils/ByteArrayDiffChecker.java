package weblogic.utils;

public final class ByteArrayDiffChecker {
   private ByteArrayDiffChecker() {
   }

   public static ByteArrayDiff diffByteArrays(byte[] from, byte[] to) {
      assert to != null;

      ByteArrayDiff diffs = new ByteArrayDiff(to.length);
      if (from != null) {
         boolean inDiff = false;
         int startOffset = 0;
         int N = Math.min(from.length, to.length);

         for(int i = 0; i < N; ++i) {
            if (inDiff) {
               if (from[i] == to[i]) {
                  inDiff = false;
                  addDiff(diffs, to, startOffset, i - startOffset);
               }
            } else if (from[i] != to[i]) {
               inDiff = true;
               startOffset = i;
            }
         }

         if (inDiff) {
            addDiff(diffs, to, startOffset, N - startOffset);
         }

         if (diffs.isEmpty() && from.length == to.length) {
            return null;
         }

         if (to.length > from.length) {
            assert N == from.length;

            addDiff(diffs, to, N, to.length - N);
         }
      } else {
         addDiff(diffs, to, 0, to.length);
      }

      return diffs;
   }

   private static void addDiff(ByteArrayDiff diff, byte[] src, int start, int changeLength) {
      byte[] change = new byte[changeLength];
      System.arraycopy(src, start, change, 0, changeLength);
      diff.addDiff(start, change);
   }

   private static boolean diffUnitTests() {
      byte[] a = new byte[]{1, 2, 3, 4, 5};
      byte[] b = new byte[]{1, 2, 3, 4, 5};
      byte[] c = new byte[]{1, 2, 3, 4, 3};
      byte[] d = new byte[]{2, 2, 3, 4, 6};
      byte[] e = new byte[]{2, 2, 3, 4, 5, 6};
      byte[] f = new byte[]{2, 2, 3, 4, 6, 6};
      byte[] g = new byte[]{2, 2, 2};
      ByteArrayDiff bd = diffByteArrays(a, a);
      if (bd == null) {
         Debug.say("** Test passed when arrays are the same.");
         bd = diffByteArrays(a, b);
         if (bd == null) {
            Debug.say("** Test passed when arrays are identical.");
            bd = diffByteArrays(a, c);
            if (bd == null) {
               Debug.say("** Test FAILED on one letter change");
               return false;
            } else {
               bd.setDumpByteArrays(true);
               Debug.say("** PASSED on one letter change with diff: " + bd);
               bd = diffByteArrays(a, d);
               if (bd == null) {
                  Debug.say("** Test FAILED on two letter change");
                  return false;
               } else {
                  bd.setDumpByteArrays(true);
                  Debug.say("** PASSED on two letter change with diff: " + bd);
                  bd = diffByteArrays(a, e);
                  if (bd == null) {
                     Debug.say("** Test FAILED on two letter change");
                     return false;
                  } else {
                     bd.setDumpByteArrays(true);
                     Debug.say("** PASSED on two letter change with diff: " + bd);
                     bd = diffByteArrays(a, f);
                     if (bd == null) {
                        Debug.say("** Test FAILED on two letter change");
                        return false;
                     } else {
                        bd.setDumpByteArrays(true);
                        Debug.say("** PASSED on two letter change with diff: " + bd);
                        bd = diffByteArrays(a, g);
                        if (bd == null) {
                           Debug.say("** Test FAILED on two letter change");
                           return false;
                        } else {
                           bd.setDumpByteArrays(true);
                           Debug.say("** PASSED on two letter change with diff: " + bd);
                           return true;
                        }
                     }
                  }
               }
            }
         } else {
            Debug.say("** FAILED when arrays are identical: " + bd);
            return false;
         }
      } else {
         Debug.say("** FAILED when arrays are the same: " + bd);
         return false;
      }
   }

   private static void dumpByteArray(byte[] b) {
      Debug.say("Dumping byte array: ");
      StringBuffer sb = new StringBuffer(200);

      for(int i = 0; i < b.length; ++i) {
         sb.append(" " + b[i]);
      }

      Debug.say("** Contents: " + sb.toString());
   }

   private static void applyDiffs(byte[] a, byte[] b) {
      ByteArrayDiff bd = diffByteArrays(a, b);
      dumpByteArray(bd.applyDiff(a));
   }

   private static void applyDiffUnitTests() {
      byte[] a = new byte[]{1, 2, 3, 4, 5};
      byte[] b = new byte[]{2, 2, 3, 3, 5};
      applyDiffs(a, b);
      byte[] c = new byte[]{1, 2, 3, 4, 5};
      byte[] d = new byte[]{2, 2, 3, 3, 5, 6};
      applyDiffs(c, d);
      byte[] e = new byte[]{1, 2, 3, 4, 5};
      byte[] f = new byte[]{1, 2, 4};
      applyDiffs(e, f);
      byte[] g = null;
      byte[] h = new byte[]{1, 2, 4};
      applyDiffs((byte[])g, h);
   }

   public static void main(String[] argv) {
      if (!diffUnitTests()) {
         Debug.say("** diffUnitTests failed.");
      }

      applyDiffUnitTests();
   }
}

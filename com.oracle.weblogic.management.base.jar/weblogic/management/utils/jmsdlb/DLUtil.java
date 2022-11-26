package weblogic.management.utils.jmsdlb;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;

public class DLUtil {
   private static boolean testlogger = false;
   private static boolean overridelogging = false;
   private final DLLogger logger;

   public DLUtil() {
      if (testlogger) {
         this.logger = new SimpleLogger(System.out);
      } else {
         this.logger = new DLBDebugLogger();
      }

   }

   public DLLogger getLogger() {
      return this.logger;
   }

   public static void setIsTestLogger(boolean set) {
      testlogger = set;
   }

   public static class BitUtility {
      public static final int MAX_FLAGS = 31;
      public static final int MIN_ID = 1;
      public static final int MAX_ID = 32;
      private static int[] flags = new int[31];
      private static int[] allSet = new int[31];

      public static boolean isMaskSet(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int check = false;

         for(int i = 0; i < size; ++i) {
            int check = (i >= orig.length ? 0 : orig[i]) & (i >= mask.length ? 0 : mask[i]);
            if (check > 0) {
               return true;
            }
         }

         return false;
      }

      public static int[] and(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int[] ret = new int[size];

         for(int i = 0; i < size; ++i) {
            ret[i] = (i >= orig.length ? 0 : orig[i]) & (i >= mask.length ? 0 : mask[i]);
         }

         return ret;
      }

      public static int[] or(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int[] ret = new int[size];

         for(int i = 0; i < size; ++i) {
            ret[i] = (i >= orig.length ? 0 : orig[i]) | (i >= mask.length ? 0 : mask[i]);
         }

         return ret;
      }

      public static int[] not(int[] value) {
         int[] ret = new int[value.length];

         for(int i = 0; i < value.length; ++i) {
            ret[i] = ~value[i];
         }

         return ret;
      }

      public static int[] add(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int[] ret = new int[size];

         for(int i = 0; i < size; ++i) {
            ret[i] = (i >= orig.length ? 0 : orig[i]) + (i >= mask.length ? 0 : mask[i]);
         }

         return ret;
      }

      public static int[] xor(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int[] ret = new int[size];

         for(int i = 0; i < size; ++i) {
            ret[i] = (i >= orig.length ? 0 : orig[i]) ^ (i >= mask.length ? 0 : mask[i]);
         }

         return ret;
      }

      public static int[] subtract(int[] orig, int[] mask) {
         int size = Math.max(orig.length, mask.length);
         int[] ret = new int[size];

         for(int i = 0; i < size; ++i) {
            ret[i] = (i >= orig.length ? 0 : orig[i]) - (i >= mask.length ? 0 : mask[i]);
         }

         return ret;
      }

      public static int min(int[] list) {
         int lowest = list[0];
         int[] var2 = list;
         int var3 = list.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int l = var2[var4];
            if (l < lowest) {
               lowest = l;
            }
         }

         return lowest;
      }

      public static int count(boolean[] list) {
         int cnt = 0;

         for(int i = 0; i < list.length; ++i) {
            if (list[i]) {
               ++cnt;
            }
         }

         return cnt;
      }

      public static int max(int[] list) {
         int highest = list[0];
         int[] var2 = list;
         int var3 = list.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int l = var2[var4];
            if (l > highest) {
               highest = l;
            }
         }

         return highest;
      }

      public static int[] multiply(int[] list1, int[] list2) {
         int[] list = new int[list1.length < list2.length ? list2.length : list1.length];
         int end = list1.length > list2.length ? list2.length : list1.length;

         for(int i = 0; i < end; ++i) {
            list[i] = list1[i] * list2[i];
         }

         return list;
      }

      public static int[] multiply(int[] list1, int val) {
         int[] list = new int[list1.length];

         for(int i = 0; i < list.length; ++i) {
            list[i] = list1[i] * val;
         }

         return list;
      }

      public static int[] divide(int[] list1, int val) {
         int[] list = new int[list1.length];

         for(int i = 0; i < list.length; ++i) {
            list[i] = list1[i] / val;
         }

         return list;
      }

      public static int[] divide(int[] list1, int[] list2) {
         int[] list = new int[list1.length < list2.length ? list2.length : list1.length];
         int end = list1.length > list2.length ? list2.length : list1.length;

         for(int i = 0; i < end; ++i) {
            list[i] = list1[i] / list2[i];
         }

         return list;
      }

      public static int[] add(int[] list1, int val) {
         int[] list = new int[list1.length];

         for(int i = 0; i < list.length; ++i) {
            list[i] += val;
         }

         return list;
      }

      public static int range(int[] list) {
         int highest = list[0];
         int lowest = list[0];
         int[] var3 = list;
         int var4 = list.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int l = var3[var5];
            if (l > highest) {
               highest = l;
            } else if (l < lowest) {
               lowest = l;
            }
         }

         return highest - lowest;
      }

      public static int sum(int[] list) {
         int sum = 0;
         int[] var2 = list;
         int var3 = list.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int l = var2[var4];
            sum += l;
         }

         return sum;
      }

      public static double sum(double[] list) {
         double sum = 0.0;
         double[] var3 = list;
         int var4 = list.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            double l = var3[var5];
            sum += l;
         }

         return sum;
      }

      public static double average(int[] list) {
         double sum = (double)sum(list);
         return sum / (double)list.length;
      }

      public static double average(double[] list) {
         double sum = sum(list);
         return sum / (double)list.length;
      }

      public static double mean(int[] list) {
         Arrays.sort(list);
         int index = list.length / 2;
         boolean isOdd = list.length % 2 != 0;
         return isOdd ? (double)list[index] : (double)(list[index - 1] + list[index]) / 2.0;
      }

      public static double mean(double[] list) {
         Arrays.sort(list);
         int index = list.length / 2;
         boolean isOdd = list.length % 2 != 0;
         return isOdd ? list[index] : (list[index - 1] + list[index]) / 2.0;
      }

      public static int[] sort(int[] list) {
         int[] returnArray = Arrays.copyOf(list, list.length);
         Arrays.sort(returnArray);
         return returnArray;
      }

      public static double[] sort(double[] list) {
         double[] returnArray = Arrays.copyOf(list, list.length);
         Arrays.sort(returnArray);
         return returnArray;
      }

      public static String toString(int[] array) {
         StringBuilder sb = new StringBuilder();
         sb.append("[ ");
         int[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int o = var2[var4];
            sb.append(o);
            sb.append(" ");
         }

         sb.append("]");
         return sb.toString();
      }

      public static String toString(int[][] array) {
         StringBuffer sb = new StringBuffer();
         sb.append("{ \n");
         int[][] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int[] o = var2[var4];
            sb.append("\t");
            sb.append(Arrays.toString(o));
         }

         sb.append("\n}");
         return sb.toString();
      }

      public static String toString(double[] array) {
         StringBuilder sb = new StringBuilder();
         sb.append("[ ");
         double[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            double o = var2[var4];
            sb.append(o);
            sb.append(" ");
         }

         sb.append("]");
         return sb.toString();
      }

      public static String toString(double[][] array) {
         StringBuilder sb = new StringBuilder();
         sb.append("{ \n");
         double[][] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            double[] o = var2[var4];
            sb.append("\t");
            sb.append(Arrays.toString(o));
         }

         sb.append("\n}");
         return sb.toString();
      }

      public static String toString(Object[] array) {
         StringBuilder sb = new StringBuilder();
         sb.append("[ ");
         Object[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            sb.append(o);
            sb.append(" ");
         }

         sb.append("]");
         return sb.toString();
      }

      public static String toMaskString(int val) {
         StringBuilder sb = new StringBuilder();
         sb.append('[');

         for(int i = flags.length - 1; i >= 0; --i) {
            char c = (val & flags[i]) > 0 ? 49 : 48;
            sb.append((char)c);
         }

         sb.append(']');
         return sb.toString();
      }

      public static String toMaskString(int[] val) {
         StringBuilder sb = new StringBuilder();
         sb.append('{');
         int[] var2 = val;
         int var3 = val.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int v = var2[var4];
            sb.append(toMaskString(v));
         }

         sb.append('}');
         return sb.toString();
      }

      public static String toSimpleString(int[] val) {
         StringBuilder sb = new StringBuilder();
         int i = 1;
         int[] var3 = val;
         int var4 = val.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int v = var3[var5];
            sb.append('[');
            sb.append(toSimpleString(i, v));
            sb.append("] ");
            i += 31;
         }

         return sb.toString();
      }

      public static String toSimpleString(int val) {
         return toSimpleString(1, val);
      }

      private static String toSimpleString(int index, int val) {
         StringBuilder sb = new StringBuilder();
         sb.append('|');
         int i = 0;

         for(int j = index; i < flags.length; ++j) {
            String c = (val & flags[i]) > 0 ? String.valueOf(j) : "-";
            sb.append(c);
            sb.append(' ');
            ++i;
         }

         sb.append('|');
         return sb.toString();
      }

      public static double calcWeight(int value, float[] weights) {
         return calcWeight(value, (float[])weights, (boolean[])null);
      }

      public static double calcWeight(int value, float[] weights, boolean[] ignore) {
         double weight = 0.0;
         int i = 0;
         float[] var6 = weights;
         int var7 = weights.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            float w = var6[var8];
            if ((ignore == null || !ignore[i]) && (value & flags[i]) > 0) {
               weight += (double)w;
            }
         }

         return weight;
      }

      public static long calcWeight(int value, int[] weights) {
         return calcWeight(value, (int[])weights, (boolean[])null);
      }

      public static long calcWeight(int value, int[] weights, boolean[] ignore) {
         long weight = 0L;
         int i = 0;
         int[] var6 = weights;
         int var7 = weights.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int w = var6[var8];
            if ((ignore == null || !ignore[i]) && (value & flags[i]) > 0) {
               weight += (long)w;
            }
         }

         return weight;
      }

      public static boolean isFlagSet(int value, int flagID) {
         return (value & flags[flagID - 1]) > 0;
      }

      public static int countOfSetBits(int i) {
         i -= i >> 1 & 1431655765;
         i = (i & 858993459) + (i >> 2 & 858993459);
         return (i + (i >> 4) & 252645135) * 16843009 >> 24;
      }

      public static int countOfSetBits(int[] num) {
         int ret = 0;
         int[] var2 = num;
         int var3 = num.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int i = var2[var4];
            ret += countOfSetBits(i);
         }

         return ret;
      }

      public static int getBitFlag(int flagID) {
         if (flagID > 31) {
            throw new IndexOutOfBoundsException("Too Large of an id for a single flag:" + flagID);
         } else {
            return flags[flagID - 1];
         }
      }

      public static int getAllTrue(int maxFields) {
         return allSet[maxFields];
      }

      public static int[] getAllSetFlags(int value) {
         if (value <= 0) {
            return null;
         } else {
            int fieldLen = value / 31;
            int fieldVal = value % 31;
            if (fieldVal != 0) {
               ++fieldLen;
            }

            int[] retInt = new int[fieldLen];

            for(int i = 0; i < fieldLen - 1; ++i) {
               retInt[i] = 31;
            }

            retInt[fieldLen - 1] = fieldVal;
            return retInt;
         }
      }

      public static int[] getBitFlags(int value) {
         if (value <= 0) {
            return null;
         } else {
            int fieldLen = value / 31;
            int fieldVal = value % 31;
            if (fieldVal != 0) {
               ++fieldLen;
            }

            int[] retInt = new int[fieldLen];
            retInt[fieldLen - 1] = fieldVal != 0 ? getBitFlag(fieldVal) : getBitFlag(31);
            return retInt;
         }
      }

      public static int[] copy(int[] toArray) {
         return Arrays.copyOf(toArray, toArray.length);
      }

      public static boolean[] copy(boolean[] toArray) {
         return Arrays.copyOf(toArray, toArray.length);
      }

      public static float[] copy(float[] toArray) {
         return Arrays.copyOf(toArray, toArray.length);
      }

      public static double[] copy(double[] toArray) {
         return Arrays.copyOf(toArray, toArray.length);
      }

      public static int[][] copy(int[][] toArray) {
         int[][] tmp = (int[][])Arrays.copyOf(toArray, toArray.length);

         for(int i = 0; i < tmp.length; ++i) {
            tmp[i] = copy(toArray[i]);
         }

         return tmp;
      }

      public static boolean compare(int[][] toArray, int[][] fromArray) {
         if (toArray.length != fromArray.length) {
            return false;
         } else {
            for(int i = 0; i < toArray.length; ++i) {
               if (!compare(toArray[i], fromArray[i])) {
                  return false;
               }
            }

            return true;
         }
      }

      public static boolean compare(int[] toArray, int[] fromArray) {
         if (toArray.length != fromArray.length) {
            return false;
         } else {
            for(int i = 0; i < toArray.length; ++i) {
               if (toArray[i] != fromArray[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      public static boolean compare(double[] toArray, double[] fromArray, int round) {
         if (toArray.length != fromArray.length) {
            return false;
         } else {
            for(int i = 0; i < toArray.length; ++i) {
               if (round == -1 && toArray[i] != fromArray[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      public static float round2DP(float foo, int points) {
         if (points == 0) {
            return (float)((int)foo);
         } else {
            int multiply = (int)Math.pow(10.0, (double)points);
            int tmp = (int)(foo * (float)multiply);
            return (float)tmp / (float)multiply;
         }
      }

      public static double round2DP(double foo, int points) {
         if (points == 0) {
            return (double)((long)foo);
         } else {
            long multiply = (long)Math.pow(10.0, (double)points);
            long tmp = (long)(foo * (double)multiply);
            return (double)tmp / (double)multiply;
         }
      }

      static {
         int position = 0;
         int bitflag = 1;
         int value = 0;

         for(int i = 0; i < flags.length; ++i) {
            flags[i] = bitflag << position;
            value += flags[i];
            allSet[i] = value;
            ++position;
         }

      }
   }

   private static class SimpleLogger implements DLLogger {
      private static final boolean debug;
      private static final boolean debugfinest;
      final PrintStream ps;
      SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

      public SimpleLogger(PrintStream ps) {
         this.ps = ps;
      }

      public boolean isDebugEnabled() {
         return debug;
      }

      public boolean isDebugFineEnabled() {
         return debug;
      }

      public boolean isDebugFinestEnabled() {
         return debugfinest;
      }

      public void debug(String msg) {
         this.ps.println(this.format.format(new Date()) + " DLB: DEBUG:" + msg);
      }

      public void fine(String msg) {
         this.ps.println(this.format.format(new Date()) + " DLB: DEBUG_FINE:" + msg);
      }

      public void finest(String msg) {
         this.ps.println(this.format.format(new Date()) + " DLB: DEBUG_FINEST:" + msg);
      }

      public void debug(String msg, Throwable thr) {
         this.debug(msg);
         thr.printStackTrace(this.ps);
      }

      public void assertCheck(boolean assertExpress, String message) throws AssertionError {
         if (!assertExpress) {
            this.debug("Assertion Failed: " + message);
            throw new AssertionError(message);
         }
      }

      static {
         debug = DLUtil.overridelogging || Boolean.getBoolean("weblogic.management.utils.jmsdlb.debug");
         debugfinest = DLUtil.overridelogging || Boolean.getBoolean("weblogic.management.utils.jmsdlb.debugfinest");
      }
   }

   private static class DLBDebugLogger implements DLLogger {
      private final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDLB");
      private final DebugLogger FINE_LOGGER = DebugLogger.getDebugLogger("DebugDLBFine");
      private final DebugLogger FINEST_LOGGER = DebugLogger.getDebugLogger("DebugDLBFinest");
      private final boolean debug;
      private final boolean debug_fine;
      private final boolean debug_finest;

      public DLBDebugLogger() {
         this.debug = this.DEBUG_LOGGER.isDebugEnabled();
         this.debug_fine = this.FINE_LOGGER.isDebugEnabled();
         this.debug_finest = this.FINE_LOGGER.isDebugEnabled();
      }

      public boolean isDebugEnabled() {
         return this.debug;
      }

      public boolean isDebugFineEnabled() {
         return this.debug_fine;
      }

      public boolean isDebugFinestEnabled() {
         return this.debug_finest;
      }

      public void debug(String msg) {
         this.DEBUG_LOGGER.debug(msg);
      }

      public void fine(String msg) {
         this.FINE_LOGGER.debug(msg);
      }

      public void finest(String msg) {
         this.FINEST_LOGGER.debug(msg);
      }

      public void debug(String msg, Throwable thr) {
         this.DEBUG_LOGGER.debug(msg, thr);
      }

      public void assertCheck(boolean assertExpress, String message) throws AssertionError {
         if (!assertExpress) {
            this.debug("Assertion Failed: " + message);
            throw new AssertionError(message);
         }
      }
   }

   public interface DLLogger {
      boolean isDebugEnabled();

      boolean isDebugFineEnabled();

      boolean isDebugFinestEnabled();

      void debug(String var1);

      void fine(String var1);

      void finest(String var1);

      void debug(String var1, Throwable var2);

      void assertCheck(boolean var1, String var2) throws AssertionError;
   }
}

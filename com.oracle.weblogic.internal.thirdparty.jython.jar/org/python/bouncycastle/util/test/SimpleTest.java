package org.python.bouncycastle.util.test;

import java.io.PrintStream;
import org.python.bouncycastle.util.Arrays;

public abstract class SimpleTest implements Test {
   public abstract String getName();

   private TestResult success() {
      return SimpleTestResult.successful(this, "Okay");
   }

   protected void fail(String var1) {
      throw new TestFailedException(SimpleTestResult.failed(this, var1));
   }

   protected void isTrue(boolean var1) {
      if (!var1) {
         throw new TestFailedException(SimpleTestResult.failed(this, "no message"));
      }
   }

   protected void isTrue(String var1, boolean var2) {
      if (!var2) {
         throw new TestFailedException(SimpleTestResult.failed(this, var1));
      }
   }

   protected void isEquals(Object var1, Object var2) {
      if (!var1.equals(var2)) {
         throw new TestFailedException(SimpleTestResult.failed(this, "no message"));
      }
   }

   protected void isEquals(int var1, int var2) {
      if (var1 != var2) {
         throw new TestFailedException(SimpleTestResult.failed(this, "no message"));
      }
   }

   protected void isEquals(String var1, boolean var2, boolean var3) {
      if (var2 != var3) {
         throw new TestFailedException(SimpleTestResult.failed(this, var1));
      }
   }

   protected void isEquals(String var1, long var2, long var4) {
      if (var2 != var4) {
         throw new TestFailedException(SimpleTestResult.failed(this, var1));
      }
   }

   protected void isEquals(String var1, Object var2, Object var3) {
      if (var2 != null || var3 != null) {
         if (var2 == null) {
            throw new TestFailedException(SimpleTestResult.failed(this, var1));
         } else if (var3 == null) {
            throw new TestFailedException(SimpleTestResult.failed(this, var1));
         } else if (!var2.equals(var3)) {
            throw new TestFailedException(SimpleTestResult.failed(this, var1));
         }
      }
   }

   protected boolean areEqual(byte[][] var1, byte[][] var2) {
      if (var1 == null && var2 == null) {
         return true;
      } else if (var1 != null && var2 != null) {
         if (var1.length != var2.length) {
            return false;
         } else {
            for(int var3 = 0; var3 < var1.length; ++var3) {
               if (!this.areEqual(var1[var3], var2[var3])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected void fail(String var1, Throwable var2) {
      throw new TestFailedException(SimpleTestResult.failed(this, var1, var2));
   }

   protected void fail(String var1, Object var2, Object var3) {
      throw new TestFailedException(SimpleTestResult.failed(this, var1, var2, var3));
   }

   protected boolean areEqual(byte[] var1, byte[] var2) {
      return Arrays.areEqual(var1, var2);
   }

   public TestResult perform() {
      try {
         this.performTest();
         return this.success();
      } catch (TestFailedException var2) {
         return var2.getResult();
      } catch (Exception var3) {
         return SimpleTestResult.failed(this, "Exception: " + var3, var3);
      }
   }

   protected static void runTest(Test var0) {
      runTest(var0, System.out);
   }

   protected static void runTest(Test var0, PrintStream var1) {
      TestResult var2 = var0.perform();
      var1.println(var2.toString());
      if (var2.getException() != null) {
         var2.getException().printStackTrace(var1);
      }

   }

   public abstract void performTest() throws Exception;
}

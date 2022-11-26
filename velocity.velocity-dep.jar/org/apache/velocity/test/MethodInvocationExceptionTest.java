package org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;

public class MethodInvocationExceptionTest extends TestCase {
   public MethodInvocationExceptionTest() {
      super("MethodInvocationExceptionTest");

      try {
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup MethodInvocationExceptionTest : " + var2);
         System.exit(1);
      }

   }

   public static Test suite() {
      return new MethodInvocationExceptionTest();
   }

   public void runTest() {
      String template = "$woogie.doException() boing!";
      VelocityContext vc = new VelocityContext();
      vc.put("woogie", this);
      StringWriter w = new StringWriter();

      Throwable t;
      try {
         Velocity.evaluate(vc, w, "test", (String)template);
         Assert.fail("No exception thrown");
      } catch (MethodInvocationException var12) {
         System.out.println("Caught MIE (good!) :");
         System.out.println("  reference = " + var12.getReferenceName());
         System.out.println("  method    = " + var12.getMethodName());
         t = var12.getWrappedThrowable();
         System.out.println("  throwable = " + t);
         if (t instanceof Exception) {
            System.out.println("  exception = " + ((Exception)t).getMessage());
         }
      } catch (Exception var13) {
         Assert.fail("Wrong exception thrown, first test." + var13);
         var13.printStackTrace();
      }

      template = "$woogie.foo boing!";

      try {
         Velocity.evaluate(vc, w, "test", (String)template);
         Assert.fail("No exception thrown, second test.");
      } catch (MethodInvocationException var10) {
         System.out.println("Caught MIE (good!) :");
         System.out.println("  reference = " + var10.getReferenceName());
         System.out.println("  method    = " + var10.getMethodName());
         t = var10.getWrappedThrowable();
         System.out.println("  throwable = " + t);
         if (t instanceof Exception) {
            System.out.println("  exception = " + ((Exception)t).getMessage());
         }
      } catch (Exception var11) {
         Assert.fail("Wrong exception thrown, second test");
      }

      template = "$woogie.Foo boing!";

      try {
         Velocity.evaluate(vc, w, "test", (String)template);
         Assert.fail("No exception thrown, third test.");
      } catch (MethodInvocationException var8) {
         System.out.println("Caught MIE (good!) :");
         System.out.println("  reference = " + var8.getReferenceName());
         System.out.println("  method    = " + var8.getMethodName());
         t = var8.getWrappedThrowable();
         System.out.println("  throwable = " + t);
         if (t instanceof Exception) {
            System.out.println("  exception = " + ((Exception)t).getMessage());
         }
      } catch (Exception var9) {
         Assert.fail("Wrong exception thrown, third test");
      }

      template = "#set($woogie.foo = 'lala') boing!";

      try {
         Velocity.evaluate(vc, w, "test", (String)template);
         Assert.fail("No exception thrown, set test.");
      } catch (MethodInvocationException var6) {
         System.out.println("Caught MIE (good!) :");
         System.out.println("  reference = " + var6.getReferenceName());
         System.out.println("  method    = " + var6.getMethodName());
         t = var6.getWrappedThrowable();
         System.out.println("  throwable = " + t);
         if (t instanceof Exception) {
            System.out.println("  exception = " + ((Exception)t).getMessage());
         }
      } catch (Exception var7) {
         Assert.fail("Wrong exception thrown, set test");
      }

   }

   public void doException() throws Exception {
      throw new NullPointerException();
   }

   public void getFoo() throws Exception {
      throw new Exception("Hello from getFoo()");
   }

   public void setFoo(String foo) throws Exception {
      throw new Exception("Hello from setFoo()");
   }
}

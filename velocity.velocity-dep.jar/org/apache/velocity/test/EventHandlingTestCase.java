package org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.MethodExceptionEventHandler;
import org.apache.velocity.app.event.NullSetEventHandler;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogSystem;

public class EventHandlingTestCase extends TestCase implements ReferenceInsertionEventHandler, NullSetEventHandler, MethodExceptionEventHandler, LogSystem {
   private String logString = null;
   private boolean exceptionSwitch = true;
   private static String NO_REFERENCE_VALUE = "<no reference value>";
   private static String REFERENCE_VALUE = "<reference value>";

   public EventHandlingTestCase() {
      super("EventHandlingTestCase");

      try {
         Velocity.setProperty("runtime.log.logsystem", this);
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup event handling test : " + var2);
         System.exit(1);
      }

   }

   public void init(RuntimeServices rs) {
   }

   public static Test suite() {
      return new EventHandlingTestCase();
   }

   public void runTest() {
      VelocityContext inner = new VelocityContext();
      EventCartridge ec = new EventCartridge();
      ec.addEventHandler(this);
      ec.attachToContext(inner);
      VelocityContext context = new VelocityContext(inner);
      context.put("name", "Velocity");

      try {
         String s = "$name";
         StringWriter w = new StringWriter();
         Velocity.evaluate(context, w, "mystring", (String)s);
         if (!w.toString().equals(REFERENCE_VALUE)) {
            Assert.fail("Reference insertion test 1");
         }

         s = "$floobie";
         w = new StringWriter();
         Velocity.evaluate(context, w, "mystring", (String)s);
         if (!w.toString().equals(NO_REFERENCE_VALUE)) {
            Assert.fail("Reference insertion test 2");
         }

         s = "#set($settest = $NotAReference)";
         w = new StringWriter();
         this.logString = null;
         Velocity.evaluate(context, w, "mystring", (String)s);
         if (this.logString != null) {
            Assert.fail("NullSetEventHandler test 1");
         }

         s = "#set($logthis = $NotAReference)";
         w = new StringWriter();
         this.logString = null;
         Velocity.evaluate(context, w, "mystring", (String)s);
         if (this.logString == null) {
            Assert.fail("NullSetEventHandler test 1");
         }

         this.exceptionSwitch = true;
         context.put("this", this);
         s = " $this.throwException()";
         w = new StringWriter();

         try {
            Velocity.evaluate(context, w, "mystring", (String)s);
         } catch (MethodInvocationException var10) {
            Assert.fail("MethodExceptionEvent test 1");
         } catch (Exception var11) {
            Assert.fail("MethodExceptionEvent test 1");
         }

         this.exceptionSwitch = false;
         s = " $this.throwException()";
         w = new StringWriter();

         try {
            Velocity.evaluate(context, w, "mystring", (String)s);
            Assert.fail("MethodExceptionEvent test 2");
         } catch (MethodInvocationException var8) {
         } catch (Exception var9) {
            Assert.fail("MethodExceptionEvent test 2");
         }
      } catch (ParseErrorException var12) {
         Assert.fail("ParseErrorException" + var12);
      } catch (MethodInvocationException var13) {
         Assert.fail("MethodInvocationException" + var13);
      } catch (Exception var14) {
         Assert.fail("Exception" + var14);
      }

   }

   public void throwException() throws Exception {
      throw new Exception("Hello from throwException()");
   }

   public Object referenceInsert(String reference, Object value) {
      String s = null;
      if (value != null) {
         s = REFERENCE_VALUE;
      } else if (reference.equals("$floobie")) {
         s = NO_REFERENCE_VALUE;
      }

      return s;
   }

   public boolean shouldLogOnNullSet(String lhs, String rhs) {
      return !lhs.equals("$settest");
   }

   public Object methodException(Class claz, String method, Exception e) throws Exception {
      if (this.exceptionSwitch && method.equals("throwException")) {
         return "handler";
      } else {
         throw e;
      }
   }

   public void logVelocityMessage(int level, String message) {
      this.logString = message;
   }
}

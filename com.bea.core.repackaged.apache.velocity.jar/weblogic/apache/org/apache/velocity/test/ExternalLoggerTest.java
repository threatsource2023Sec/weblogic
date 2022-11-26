package weblogic.apache.org.apache.velocity.test;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import weblogic.apache.org.apache.velocity.app.VelocityEngine;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.log.LogSystem;

public class ExternalLoggerTest extends TestCase implements LogSystem {
   private String logString = null;
   private VelocityEngine ve = null;

   public ExternalLoggerTest() {
      super("LoggerTest");

      try {
         this.ve = new VelocityEngine();
         this.ve.setProperty("runtime.log.logsystem", this);
         this.ve.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup LoggerTest : " + var2);
         System.exit(1);
      }

   }

   public void init(RuntimeServices rs) {
   }

   public static Test suite() {
      return new ExternalLoggerTest();
   }

   public void runTest() {
      this.logString = null;
      String testString = "This is a test.";
      this.ve.warn(testString);
      if (this.logString == null || !this.logString.equals("  [warn] " + testString)) {
         Assert.fail("Didn't recieve log message.");
      }

   }

   public void logVelocityMessage(int level, String message) {
      String out = "";
      switch (level) {
         case 0:
            out = " [debug] ";
            break;
         case 1:
            out = "  [info] ";
            break;
         case 2:
            out = "  [warn] ";
            break;
         case 3:
            out = " [error] ";
            break;
         default:
            out = " [unknown] ";
      }

      this.logString = out + message;
   }
}

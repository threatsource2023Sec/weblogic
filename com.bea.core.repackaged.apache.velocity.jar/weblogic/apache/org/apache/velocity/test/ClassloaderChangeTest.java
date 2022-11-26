package weblogic.apache.org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.VelocityEngine;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.log.LogSystem;

public class ClassloaderChangeTest extends TestCase implements LogSystem {
   private VelocityEngine ve = null;
   private boolean sawCacheDump = false;
   private static String OUTPUT = "Hello From Foo";

   public ClassloaderChangeTest() {
      super("ClassloaderChangeTest");

      try {
         this.ve = new VelocityEngine();
         this.ve.setProperty("runtime.log.logsystem", this);
         this.ve.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup ClassloaderChnageTest : " + var2);
         System.exit(1);
      }

   }

   public void init(RuntimeServices rs) {
   }

   public static Test suite() {
      return new ClassloaderChangeTest();
   }

   public void runTest() {
      this.sawCacheDump = false;

      try {
         VelocityContext vc = new VelocityContext();
         Object foo = null;
         TestClassloader cl = new TestClassloader();
         Class fooclass = cl.loadClass("Foo");
         foo = fooclass.newInstance();
         vc.put("foo", foo);
         StringWriter writer = new StringWriter();
         this.ve.evaluate(vc, writer, "test", (String)"$foo.doIt()");
         if (!writer.toString().equals(OUTPUT)) {
            Assert.fail("Output from doIt() incorrect");
         }

         cl = new TestClassloader();
         fooclass = cl.loadClass("Foo");
         foo = fooclass.newInstance();
         vc.put("foo", foo);
         writer = new StringWriter();
         this.ve.evaluate(vc, writer, "test", (String)"$foo.doIt()");
         if (!writer.toString().equals(OUTPUT)) {
            Assert.fail("Output from doIt() incorrect");
         }
      } catch (Exception var6) {
         System.out.println("ClassloaderChangeTest : " + var6);
      }

      if (!this.sawCacheDump) {
         Assert.fail("Didn't see introspector cache dump.");
      }

   }

   public void logVelocityMessage(int level, String message) {
      if (message.equals("Introspector : detected classloader change. Dumping cache.")) {
         this.sawCacheDump = true;
      }

   }
}

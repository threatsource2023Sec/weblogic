package org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocimacroTestCase extends TestCase {
   private String template1 = "#macro(foo $a)$a#end #macro(bar $b)#foo($b)#end #foreach($i in [1..3])#bar($i)#end";
   private String result1 = "  123";

   public VelocimacroTestCase() {
      super("VelocimacroTestCase");

      try {
         Velocity.setProperty("velocimacro.permissions.allow.inline.local.scope", Boolean.TRUE);
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup VelocimacroTestCase!");
         System.exit(1);
      }

   }

   public static Test suite() {
      return new VelocimacroTestCase();
   }

   public void runTest() {
      VelocityContext context = new VelocityContext();

      try {
         StringWriter writer = new StringWriter();
         Velocity.evaluate(context, writer, "vm_chain1", (String)this.template1);
         String out = writer.toString();
         if (!this.result1.equals(out)) {
            Assert.fail("output incorrect.");
         }
      } catch (Exception var4) {
         Assert.fail(var4.getMessage());
      }

   }
}

package weblogic.apache.org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.Velocity;

public class VelocityAppTestCase extends BaseTestCase implements TemplateTestBase {
   private StringWriter compare1 = new StringWriter();
   private String input1 = "My name is $name -> $Floog";
   private String result1 = "My name is jason -> floogie woogie";

   public VelocityAppTestCase() {
      super("VelocityAppTestCase");

      try {
         Velocity.setProperty("file.resource.loader.path", "../test/templates");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup VelocityAppTestCase!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new VelocityAppTestCase();
   }

   public void runTest() {
      VelocityContext context = new VelocityContext();
      context.put("name", "jason");
      context.put("Floog", "floogie woogie");

      try {
         Velocity.evaluate(context, this.compare1, "evaltest", (String)this.input1);
         if (!this.result1.equals(this.compare1.toString())) {
            Assert.fail("Output incorrect.");
         }
      } catch (Exception var3) {
         Assert.fail(var3.getMessage());
      }

   }
}

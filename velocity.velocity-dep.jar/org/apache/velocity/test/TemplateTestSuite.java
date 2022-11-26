package org.apache.velocity.test;

import java.io.FileInputStream;
import java.util.Properties;
import junit.framework.TestSuite;
import org.apache.velocity.app.Velocity;

public class TemplateTestSuite extends TestSuite implements TemplateTestBase {
   private Properties testProperties;

   public TemplateTestSuite() {
      try {
         Velocity.setProperty("file.resource.loader.path", "../test/templates");
         Velocity.setProperty("runtime.log.error.stacktrace", "true");
         Velocity.setProperty("runtime.log.warn.stacktrace", "true");
         Velocity.setProperty("runtime.log.info.stacktrace", "true");
         Velocity.init();
         this.testProperties = new Properties();
         this.testProperties.load(new FileInputStream("../test/templates/templates.properties"));
      } catch (Exception var2) {
         System.err.println("Cannot setup TemplateTestSuite!");
         var2.printStackTrace();
         System.exit(1);
      }

      this.addTemplateTestCases();
   }

   private void addTemplateTestCases() {
      int i = 1;

      while(true) {
         String template = this.testProperties.getProperty(getTemplateTestKey(i));
         if (template == null) {
            return;
         }

         System.out.println("Adding TemplateTestCase : " + template);
         this.addTest(new TemplateTestCase(template));
         ++i;
      }
   }

   private static final String getTemplateTestKey(int nbr) {
      return "test.template." + nbr;
   }
}

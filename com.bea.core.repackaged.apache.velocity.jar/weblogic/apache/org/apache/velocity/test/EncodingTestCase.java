package weblogic.apache.org.apache.velocity.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.Velocity;

public class EncodingTestCase extends BaseTestCase implements TemplateTestBase {
   public EncodingTestCase() {
      super("EncodingTestCase");

      try {
         Velocity.setProperty("file.resource.loader.path", "../test/templates");
         Velocity.setProperty("input.encoding", "UTF-8");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup EncodingTestCase!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new EncodingTestCase();
   }

   public void runTest() {
      VelocityContext context = new VelocityContext();

      try {
         BaseTestCase.assureResultsDirectoryExists("../test/templates/results");
         Template template = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "encodingtest", "vm"), "UTF-8");
         FileOutputStream fos = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "encodingtest", "res"));
         Writer writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
         template.merge(context, writer);
         writer.flush();
         writer.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "encodingtest", "res", "cmp")) {
            Assert.fail("Output 1 incorrect.");
         }

         template = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "encodingtest2", "vm"), "UTF-8");
         fos = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "encodingtest2", "res"));
         writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
         template.merge(context, writer);
         writer.flush();
         writer.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "encodingtest2", "res", "cmp")) {
            Assert.fail("Output 2 incorrect.");
         }

         template = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "encodingtest3", "vm"), "GBK");
         fos = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "encodingtest3", "res"));
         writer = new BufferedWriter(new OutputStreamWriter(fos, "GBK"));
         template.merge(context, writer);
         writer.flush();
         writer.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "encodingtest3", "res", "cmp")) {
            Assert.fail("Output 3 incorrect.");
         }

         template = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "encodingtest_KOI8-R", "vm"), "KOI8-R");
         fos = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "encodingtest_KOI8-R", "res"));
         writer = new BufferedWriter(new OutputStreamWriter(fos, "KOI8-R"));
         template.merge(context, writer);
         writer.flush();
         writer.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "encodingtest_KOI8-R", "res", "cmp")) {
            Assert.fail("Output 4 incorrect.");
         }
      } catch (Exception var5) {
         Assert.fail(var5.getMessage());
      }

   }
}

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
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;

public class ClasspathResourceTest extends BaseTestCase {
   private static final String TMPL_FILE_EXT = "vm";
   private static final String CMP_FILE_EXT = "cmp";
   private static final String RESULT_FILE_EXT = "res";
   private static final String RESULTS_DIR = "../test/cpload/results";
   private static final String COMPARE_DIR = "../test/cpload/compare";

   public ClasspathResourceTest() {
      super("ClasspathResourceTest");

      try {
         BaseTestCase.assureResultsDirectoryExists("../test/cpload/results");
         Velocity.setProperty("resource.loader", "classpath");
         Velocity.addProperty("classpath.resource.loader.class", "weblogic.apache.org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
         Velocity.setProperty("classpath.resource.loader.cache", "false");
         Velocity.setProperty("classpath.resource.loader.modificationCheckInterval", "2");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup ClasspathResourceTest!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new ClasspathResourceTest();
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/cpload/results");
         Template template1 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "template/test1", "vm"));
         Template template2 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "template/test2", "vm"));
         FileOutputStream fos1 = new FileOutputStream(BaseTestCase.getFileName("../test/cpload/results", "test1", "res"));
         FileOutputStream fos2 = new FileOutputStream(BaseTestCase.getFileName("../test/cpload/results", "test2", "res"));
         Writer writer1 = new BufferedWriter(new OutputStreamWriter(fos1));
         Writer writer2 = new BufferedWriter(new OutputStreamWriter(fos2));
         VelocityContext context = new VelocityContext();
         template1.merge(context, writer1);
         writer1.flush();
         writer1.close();
         template2.merge(context, writer2);
         writer2.flush();
         writer2.close();
         if (!this.isMatch("../test/cpload/results", "../test/cpload/compare", "test1", "res", "cmp") || !this.isMatch("../test/cpload/results", "../test/cpload/compare", "test2", "res", "cmp")) {
            Assert.fail("Output is incorrect!");
         }
      } catch (Exception var8) {
         Assert.fail(var8.getMessage());
      }

   }
}

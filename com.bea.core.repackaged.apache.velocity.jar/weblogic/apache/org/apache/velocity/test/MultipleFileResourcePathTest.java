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

public class MultipleFileResourcePathTest extends BaseTestCase {
   private static final String TMPL_FILE_EXT = "vm";
   private static final String CMP_FILE_EXT = "cmp";
   private static final String RESULT_FILE_EXT = "res";
   private static final String FILE_RESOURCE_LOADER_PATH1 = "../test/multi/path1";
   private static final String FILE_RESOURCE_LOADER_PATH2 = "../test/multi/path2";
   private static final String RESULTS_DIR = "../test/multi/results";
   private static final String COMPARE_DIR = "../test/multi/compare";

   MultipleFileResourcePathTest() {
      super("MultipleFileResourcePathTest");

      try {
         BaseTestCase.assureResultsDirectoryExists("../test/multi/results");
         Velocity.addProperty("file.resource.loader.path", "../test/multi/path1");
         Velocity.addProperty("file.resource.loader.path", "../test/multi/path2");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup MultipleFileResourcePathTest!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new MultipleFileResourcePathTest();
   }

   public void runTest() {
      try {
         Template template1 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "path1", "vm"));
         Template template2 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "path2", "vm"));
         FileOutputStream fos1 = new FileOutputStream(BaseTestCase.getFileName("../test/multi/results", "path1", "res"));
         FileOutputStream fos2 = new FileOutputStream(BaseTestCase.getFileName("../test/multi/results", "path2", "res"));
         Writer writer1 = new BufferedWriter(new OutputStreamWriter(fos1));
         Writer writer2 = new BufferedWriter(new OutputStreamWriter(fos2));
         VelocityContext context = new VelocityContext();
         template1.merge(context, writer1);
         writer1.flush();
         writer1.close();
         template2.merge(context, writer2);
         writer2.flush();
         writer2.close();
         if (!this.isMatch("../test/multi/results", "../test/multi/compare", "path1", "res", "cmp") || !this.isMatch("../test/multi/results", "../test/multi/compare", "path2", "res", "cmp")) {
            Assert.fail("Output incorrect.");
         }
      } catch (Exception var8) {
         Assert.fail(var8.getMessage());
      }

   }
}

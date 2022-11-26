package org.apache.velocity.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import junit.framework.Assert;
import junit.framework.Test;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class MultiLoaderTestCase extends BaseTestCase {
   private static final String TMPL_FILE_EXT = "vm";
   private static final String CMP_FILE_EXT = "cmp";
   private static final String RESULT_FILE_EXT = "res";
   private static final String RESULTS_DIR = "../test/multiloader/results";
   private static final String FILE_RESOURCE_LOADER_PATH = "../test/multiloader";
   private static final String COMPARE_DIR = "../test/multiloader/compare";

   public MultiLoaderTestCase() {
      super("MultiLoaderTestCase");

      try {
         BaseTestCase.assureResultsDirectoryExists("../test/multiloader/results");
         Velocity.setProperty("resource.loader", "file");
         Velocity.setProperty("file.resource.loader.path", "../test/multiloader");
         Velocity.addProperty("resource.loader", "classpath");
         Velocity.addProperty("resource.loader", "jar");
         Velocity.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
         Velocity.setProperty("classpath.resource.loader.cache", "false");
         Velocity.setProperty("classpath.resource.loader.modificationCheckInterval", "2");
         Velocity.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
         Velocity.setProperty("jar.resource.loader.path", "jar:file:../test/multiloader/test2.jar");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup MultiLoaderTestCase!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new MultiLoaderTestCase();
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/multiloader/results");
         Template template1 = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "path1", "vm"));
         Template template2 = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "template/test1", "vm"));
         Template template3 = Velocity.getTemplate(BaseTestCase.getFileName((String)null, "template/test2", "vm"));
         FileOutputStream fos1 = new FileOutputStream(BaseTestCase.getFileName("../test/multiloader/results", "path1", "res"));
         FileOutputStream fos2 = new FileOutputStream(BaseTestCase.getFileName("../test/multiloader/results", "test2", "res"));
         FileOutputStream fos3 = new FileOutputStream(BaseTestCase.getFileName("../test/multiloader/results", "test3", "res"));
         Writer writer1 = new BufferedWriter(new OutputStreamWriter(fos1));
         Writer writer2 = new BufferedWriter(new OutputStreamWriter(fos2));
         Writer writer3 = new BufferedWriter(new OutputStreamWriter(fos3));
         VelocityContext context = new VelocityContext();
         template1.merge(context, writer1);
         writer1.flush();
         writer1.close();
         template2.merge(context, writer2);
         writer2.flush();
         writer2.close();
         template3.merge(context, writer3);
         writer3.flush();
         writer3.close();
         if (!this.isMatch("../test/multiloader/results", "../test/multiloader/compare", "path1", "res", "cmp")) {
            Assert.fail("Output incorrect for FileResourceLoader test.");
         }

         if (!this.isMatch("../test/multiloader/results", "../test/multiloader/compare", "test2", "res", "cmp")) {
            Assert.fail("Output incorrect for ClasspathResourceLoader test.");
         }

         if (!this.isMatch("../test/multiloader/results", "../test/multiloader/compare", "test3", "res", "cmp")) {
            Assert.fail("Output incorrect for JarResourceLoader test.");
         }
      } catch (Exception var11) {
         Assert.fail(var11.getMessage());
      }

   }
}

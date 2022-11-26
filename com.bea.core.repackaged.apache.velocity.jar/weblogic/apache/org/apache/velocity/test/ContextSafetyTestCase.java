package weblogic.apache.org.apache.velocity.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.Velocity;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;

public class ContextSafetyTestCase extends BaseTestCase implements TemplateTestBase {
   public ContextSafetyTestCase() {
      super("ContextSafetyTestCase");

      try {
         Velocity.setProperty("file.resource.loader.path", "../test/templates");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup ContextSafetyTestCase!");
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static Test suite() {
      return new ContextSafetyTestCase();
   }

   public void runTest() {
      Vector v = new Vector();
      v.addElement(new String("vector hello 1"));
      v.addElement(new String("vector hello 2"));
      v.addElement(new String("vector hello 3"));
      String[] strArray = new String[]{"array hello 1", "array hello 2", "array hello 3"};
      VelocityContext context = new VelocityContext();

      try {
         BaseTestCase.assureResultsDirectoryExists("../test/templates/results");
         Template template = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "context_safety", "vm"));
         FileOutputStream fos1 = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "context_safety1", "res"));
         FileOutputStream fos2 = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "context_safety2", "res"));
         Writer writer1 = new BufferedWriter(new OutputStreamWriter(fos1));
         Writer writer2 = new BufferedWriter(new OutputStreamWriter(fos2));
         context.put("vector", v);
         template.merge(context, writer1);
         writer1.flush();
         writer1.close();
         context.put("vector", strArray);
         template.merge(context, writer2);
         writer2.flush();
         writer2.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "context_safety1", "res", "cmp") || !this.isMatch("../test/templates/results", "../test/templates/compare", "context_safety2", "res", "cmp")) {
            Assert.fail("Output incorrect.");
         }
      } catch (Exception var9) {
         Assert.fail(var9.getMessage());
      }

   }
}

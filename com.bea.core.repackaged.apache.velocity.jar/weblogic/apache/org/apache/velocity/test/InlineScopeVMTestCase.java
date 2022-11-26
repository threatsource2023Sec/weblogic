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

public class InlineScopeVMTestCase extends BaseTestCase implements TemplateTestBase {
   private static final String TEST_CASE_NAME = "InlineScopeVMTestCase";

   InlineScopeVMTestCase() {
      super("InlineScopeVMTestCase");

      try {
         Velocity.setProperty("velocimacro.permissions.allow.inline.to.replace.global", "true");
         Velocity.setProperty("velocimacro.permissions.allow.inline.local.scope", "true");
         Velocity.setProperty("file.resource.loader.path", "../test/templates");
         Velocity.init();
      } catch (Exception var2) {
         System.err.println("Cannot setup InlineScopeVMTestCase");
         System.exit(1);
      }

   }

   public static Test suite() {
      return new InlineScopeVMTestCase();
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/templates/results");
         Template template2 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "vm_test2", "vm"));
         Template template1 = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, "vm_test1", "vm"));
         FileOutputStream fos1 = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "vm_test1", "res"));
         FileOutputStream fos2 = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", "vm_test2", "res"));
         Writer writer1 = new BufferedWriter(new OutputStreamWriter(fos1));
         Writer writer2 = new BufferedWriter(new OutputStreamWriter(fos2));
         VelocityContext context = new VelocityContext();
         template1.merge(context, writer1);
         writer1.flush();
         writer1.close();
         template2.merge(context, writer2);
         writer2.flush();
         writer2.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", "vm_test1", "res", "cmp") || !this.isMatch("../test/templates/results", "../test/templates/compare", "vm_test2", "res", "cmp")) {
            Assert.fail("Output incorrect.");
         }
      } catch (Exception var8) {
         Assert.fail(var8.getMessage());
      }

   }
}

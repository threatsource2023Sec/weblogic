package weblogic.apache.org.apache.velocity.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.FieldMethodizer;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;
import weblogic.apache.org.apache.velocity.test.provider.BoolObj;
import weblogic.apache.org.apache.velocity.test.provider.TestProvider;

public class TemplateTestCase extends BaseTestCase implements TemplateTestBase {
   protected String baseFileName;
   private TestProvider provider;
   private ArrayList al;
   private Hashtable h;
   private VelocityContext context;
   private VelocityContext context1;
   private VelocityContext context2;
   private Vector vec;

   public TemplateTestCase(String baseFileName) {
      super(BaseTestCase.getTestCaseName(baseFileName));
      this.baseFileName = baseFileName;
   }

   public static Test suite() {
      return new TemplateTestSuite();
   }

   protected void setUp() {
      this.provider = new TestProvider();
      this.al = this.provider.getCustomers();
      this.h = new Hashtable();
      this.h.put("Bar", "this is from a hashtable!");
      this.h.put("Foo", "this is from a hashtable too!");
      this.vec = new Vector();
      this.vec.addElement(new String("string1"));
      this.vec.addElement(new String("string2"));
      this.context2 = new VelocityContext();
      this.context1 = new VelocityContext(this.context2);
      this.context = new VelocityContext(this.context1);
      this.context.put("provider", this.provider);
      this.context1.put("name", "jason");
      this.context2.put("providers", this.provider.getCustomers2());
      this.context.put("list", this.al);
      this.context1.put("hashtable", this.h);
      this.context2.put("hashmap", new HashMap());
      this.context2.put("search", this.provider.getSearch());
      this.context.put("relatedSearches", this.provider.getRelSearches());
      this.context1.put("searchResults", this.provider.getRelSearches());
      this.context2.put("stringarray", this.provider.getArray());
      this.context.put("vector", this.vec);
      this.context.put("mystring", new String());
      this.context.put("runtime", new FieldMethodizer("weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton"));
      this.context.put("fmprov", new FieldMethodizer(this.provider));
      this.context.put("Floog", "floogie woogie");
      this.context.put("boolobj", new BoolObj());
      Object[] oarr = new Object[]{"a", "b", "c", "d"};
      int[] intarr = new int[]{10, 20, 30, 40, 50};
      this.context.put("collection", this.vec);
      this.context2.put("iterator", this.vec.iterator());
      this.context1.put("map", this.h);
      this.context.put("obarr", oarr);
      this.context.put("enumerator", this.vec.elements());
      this.context.put("intarr", intarr);
   }

   public void runTest() {
      try {
         Template template = RuntimeSingleton.getTemplate(BaseTestCase.getFileName((String)null, this.baseFileName, "vm"));
         BaseTestCase.assureResultsDirectoryExists("../test/templates/results");
         FileOutputStream fos = new FileOutputStream(BaseTestCase.getFileName("../test/templates/results", this.baseFileName, "res"));
         Writer writer = new BufferedWriter(new OutputStreamWriter(fos));
         template.merge(this.context, writer);
         writer.flush();
         writer.close();
         if (!this.isMatch("../test/templates/results", "../test/templates/compare", this.baseFileName, "res", "cmp")) {
            Assert.fail("Processed template did not match expected output");
         }
      } catch (Exception var4) {
         System.out.println("EXCEPTION : " + var4);
         Assert.fail(var4.getMessage());
      }

   }
}

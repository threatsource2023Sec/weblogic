package weblogic.apache.org.apache.velocity.test;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.runtime.configuration.Configuration;

/** @deprecated */
public class ConfigurationTestCase extends BaseTestCase {
   private static final String COMPARE_DIR = "../test/configuration/compare";
   private static final String RESULTS_DIR = "../test/configuration/results";
   private static final String TEST_CONFIG = "../test/configuration/test.config";

   public ConfigurationTestCase() {
      super("ConfigurationTestCase");
   }

   public static Test suite() {
      return new ConfigurationTestCase();
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/configuration/results");
         Configuration c = new Configuration("../test/configuration/test.config");
         FileWriter result = new FileWriter(BaseTestCase.getFileName("../test/configuration/results", "output", "res"));
         this.message(result, "Testing order of keys ...");
         this.showIterator(result, c.getKeys());
         this.message(result, "Testing retrieval of CSV values ...");
         this.showVector(result, c.getVector("resource.loader"));
         this.message(result, "Testing subset(prefix).getKeys() ...");
         Configuration subset = c.subset("file.resource.loader");
         this.showIterator(result, subset.getKeys());
         this.message(result, "Testing getVector(prefix) ...");
         this.showVector(result, subset.getVector("path"));
         this.message(result, "Testing getString(key) ...");
         result.write(c.getString("config.string.value"));
         result.write("\n\n");
         this.message(result, "Testing getBoolean(key) ...");
         result.write((new Boolean(c.getBoolean("config.boolean.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getByte(key) ...");
         result.write((new Byte(c.getByte("config.byte.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getShort(key) ...");
         result.write((new Short(c.getShort("config.short.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getInt(key) ...");
         result.write((new Integer(c.getInt("config.int.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getLong(key) ...");
         result.write((new Long(c.getLong("config.long.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getFloat(key) ...");
         result.write((new Float(c.getFloat("config.float.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing getDouble(key) ...");
         result.write((new Double(c.getDouble("config.double.value"))).toString());
         result.write("\n\n");
         this.message(result, "Testing escaped-comma scalar...");
         result.write(c.getString("escape.comma1"));
         result.write("\n\n");
         this.message(result, "Testing escaped-comma vector...");
         this.showVector(result, c.getVector("escape.comma2"));
         result.write("\n\n");
         result.flush();
         result.close();
         if (!this.isMatch("../test/configuration/results", "../test/configuration/compare", "output", "res", "cmp")) {
            Assert.fail("Output incorrect.");
         }
      } catch (Exception var4) {
         System.err.println("Cannot setup ConfigurationTestCase!");
         var4.printStackTrace();
         System.exit(1);
      }

   }

   private void showIterator(FileWriter result, Iterator i) throws Exception {
      while(i.hasNext()) {
         result.write((String)i.next());
         result.write("\n");
      }

      result.write("\n");
   }

   private void showVector(FileWriter result, Vector v) throws Exception {
      for(int j = 0; j < v.size(); ++j) {
         result.write((String)v.get(j));
         result.write("\n");
      }

      result.write("\n");
   }

   private void message(FileWriter result, String message) throws Exception {
      result.write("--------------------------------------------------\n");
      result.write(message + "\n");
      result.write("--------------------------------------------------\n");
      result.write("\n");
   }
}

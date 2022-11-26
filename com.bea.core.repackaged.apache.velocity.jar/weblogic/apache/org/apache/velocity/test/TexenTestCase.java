package weblogic.apache.org.apache.velocity.test;

import junit.framework.Assert;
import junit.framework.Test;

public class TexenTestCase extends BaseTestCase {
   private static final String RESULTS_DIR = "../test/texen/results";
   private static final String COMPARE_DIR = "../test/texen/compare";

   public TexenTestCase() {
      super("TexenTestCase");
   }

   public static Test suite() {
      return new TexenTestCase();
   }

   protected void setUp() {
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/texen/results");
         if (!this.isMatch("../test/texen/results", "../test/texen/compare", "TurbineWeather", "java", "java") || !this.isMatch("../test/texen/results", "../test/texen/compare", "TurbineWeatherService", "java", "java") || !this.isMatch("../test/texen/results", "../test/texen/compare", "WeatherService", "java", "java") || !this.isMatch("../test/texen/results", "../test/texen/compare", "book", "txt", "txt") || !this.isMatch("../test/texen/results", "../test/texen/compare", "Test", "txt", "txt")) {
            Assert.fail("Output is incorrect!");
         }
      } catch (Exception var2) {
      }

   }
}

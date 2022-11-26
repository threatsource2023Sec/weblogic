package org.apache.velocity.test;

import junit.framework.Assert;
import junit.framework.Test;

public class TexenClasspathTestCase extends BaseTestCase {
   private static final String RESULTS_DIR = "../test/texen-classpath/results";
   private static final String COMPARE_DIR = "../test/texen-classpath/compare";

   public TexenClasspathTestCase() {
      super("TexenClasspathTestCase");
   }

   public static Test suite() {
      return new TexenClasspathTestCase();
   }

   protected void setUp() {
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/texen-classpath/results");
         if (!this.isMatch("../test/texen-classpath/results", "../test/texen-classpath/compare", "TurbineWeather", "java", "java") || !this.isMatch("../test/texen-classpath/results", "../test/texen-classpath/compare", "TurbineWeatherService", "java", "java") || !this.isMatch("../test/texen-classpath/results", "../test/texen-classpath/compare", "WeatherService", "java", "java") || !this.isMatch("../test/texen-classpath/results", "../test/texen-classpath/compare", "book", "txt", "txt") || !this.isMatch("../test/texen-classpath/results", "../test/texen-classpath/compare", "Test", "txt", "txt")) {
            Assert.fail("Output is incorrect!");
         }
      } catch (Exception var2) {
      }

   }
}

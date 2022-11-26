package org.apache.velocity.test;

import junit.framework.Assert;
import junit.framework.Test;

public class AnakiaTestCase extends BaseTestCase {
   private static final String COMPARE_DIR = "../test/anakia/compare";
   private static final String RESULTS_DIR = "../test/anakia/results";
   private static final String FILE_EXT = ".html";

   public AnakiaTestCase() {
      super("AnakiaTestCase");
   }

   public static Test suite() {
      return new AnakiaTestCase();
   }

   public void runTest() {
      try {
         BaseTestCase.assureResultsDirectoryExists("../test/anakia/results");
         if (!this.isMatch("../test/anakia/results", "../test/anakia/compare", "index", ".html", ".html")) {
            Assert.fail("Output is incorrect!");
         } else {
            System.out.println("Passed!");
         }
      } catch (Exception var2) {
      }

   }
}

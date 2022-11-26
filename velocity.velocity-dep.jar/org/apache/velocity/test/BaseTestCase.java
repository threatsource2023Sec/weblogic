package org.apache.velocity.test;

import java.io.File;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.util.StringUtils;

public class BaseTestCase extends TestCase {
   private Perl5Util perl = new Perl5Util();

   public BaseTestCase(String name) {
      super(name);
   }

   protected static String getFileName(String dir, String base, String ext) {
      StringBuffer buf = new StringBuffer();
      if (dir != null) {
         buf.append(dir).append('/');
      }

      buf.append(base).append('.').append(ext);
      return buf.toString();
   }

   protected static void assureResultsDirectoryExists(String resultsDirectory) {
      File dir = new File(resultsDirectory);
      if (!dir.exists()) {
         RuntimeSingleton.info("Template results directory does not exist");
         if (dir.mkdirs()) {
            RuntimeSingleton.info("Created template results directory");
         } else {
            String errMsg = "Unable to create template results directory";
            RuntimeSingleton.warn(errMsg);
            Assert.fail(errMsg);
         }
      }

   }

   protected String normalizeNewlines(String source) {
      return this.perl.substitute("s/\r[\n]/\n/g", source);
   }

   protected boolean isMatch(String resultsDir, String compareDir, String baseFileName, String resultExt, String compareExt) throws Exception {
      String result = StringUtils.fileContentsToString(getFileName(resultsDir, baseFileName, resultExt));
      String compare = StringUtils.fileContentsToString(getFileName(compareDir, baseFileName, compareExt));
      return this.normalizeNewlines(result).equals(this.normalizeNewlines(compare));
   }

   protected static final String getTestCaseName(String s) {
      StringBuffer name = new StringBuffer();
      name.append(Character.toTitleCase(s.charAt(0)));
      name.append(s.substring(1, s.length()).toLowerCase());
      return name.toString();
   }
}

package weblogic.apache.org.apache.oro.io;

import weblogic.apache.org.apache.oro.text.PatternCache;
import weblogic.apache.org.apache.oro.text.PatternCacheLRU;
import weblogic.apache.org.apache.oro.text.awk.AwkCompiler;
import weblogic.apache.org.apache.oro.text.awk.AwkMatcher;
import weblogic.apache.org.apache.oro.text.regex.PatternMatcher;

public class AwkFilenameFilter extends RegexFilenameFilter {
   private static final PatternMatcher __MATCHER = new AwkMatcher();
   private static final PatternCache __CACHE = new PatternCacheLRU(new AwkCompiler());

   public AwkFilenameFilter() {
      super(__CACHE, __MATCHER);
   }

   public AwkFilenameFilter(String var1) {
      super(__CACHE, __MATCHER, var1);
   }

   public AwkFilenameFilter(String var1, int var2) {
      super(__CACHE, __MATCHER, var1, var2);
   }
}

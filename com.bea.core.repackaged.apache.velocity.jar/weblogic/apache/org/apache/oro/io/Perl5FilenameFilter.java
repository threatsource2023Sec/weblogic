package weblogic.apache.org.apache.oro.io;

import weblogic.apache.org.apache.oro.text.PatternCache;
import weblogic.apache.org.apache.oro.text.PatternCacheLRU;
import weblogic.apache.org.apache.oro.text.regex.PatternMatcher;
import weblogic.apache.org.apache.oro.text.regex.Perl5Matcher;

public class Perl5FilenameFilter extends RegexFilenameFilter {
   private static final PatternMatcher __MATCHER = new Perl5Matcher();
   private static final PatternCache __CACHE = new PatternCacheLRU();

   public Perl5FilenameFilter() {
      super(__CACHE, __MATCHER);
   }

   public Perl5FilenameFilter(String var1) {
      super(__CACHE, __MATCHER, var1);
   }

   public Perl5FilenameFilter(String var1, int var2) {
      super(__CACHE, __MATCHER, var1, var2);
   }
}

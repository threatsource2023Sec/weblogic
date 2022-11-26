package weblogic.apache.org.apache.oro.io;

import weblogic.apache.org.apache.oro.text.GlobCompiler;
import weblogic.apache.org.apache.oro.text.PatternCache;
import weblogic.apache.org.apache.oro.text.PatternCacheLRU;
import weblogic.apache.org.apache.oro.text.regex.PatternMatcher;
import weblogic.apache.org.apache.oro.text.regex.Perl5Matcher;

public class GlobFilenameFilter extends RegexFilenameFilter {
   private static final PatternMatcher __MATCHER = new Perl5Matcher();
   private static final PatternCache __CACHE = new PatternCacheLRU(new GlobCompiler());

   public GlobFilenameFilter() {
      super(__CACHE, __MATCHER);
   }

   public GlobFilenameFilter(String var1) {
      super(__CACHE, __MATCHER, var1);
   }

   public GlobFilenameFilter(String var1, int var2) {
      super(__CACHE, __MATCHER, var1, var2);
   }
}

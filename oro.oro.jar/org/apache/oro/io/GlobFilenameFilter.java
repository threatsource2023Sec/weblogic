package org.apache.oro.io;

import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.PatternCache;
import org.apache.oro.text.PatternCacheLRU;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Matcher;

public class GlobFilenameFilter extends RegexFilenameFilter {
   private static final PatternMatcher __MATCHER = new Perl5Matcher();
   private static final PatternCache __CACHE = new PatternCacheLRU(new GlobCompiler());

   public GlobFilenameFilter(String var1, int var2) {
      super(__CACHE, __MATCHER, var1, var2);
   }

   public GlobFilenameFilter(String var1) {
      super(__CACHE, __MATCHER, var1);
   }

   public GlobFilenameFilter() {
      super(__CACHE, __MATCHER);
   }
}

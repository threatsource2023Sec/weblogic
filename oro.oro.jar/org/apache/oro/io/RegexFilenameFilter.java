package org.apache.oro.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import org.apache.oro.text.MalformedCachePatternException;
import org.apache.oro.text.PatternCache;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;

public abstract class RegexFilenameFilter implements FilenameFilter, FileFilter {
   PatternCache _cache;
   PatternMatcher _matcher;
   Pattern _pattern;

   RegexFilenameFilter(PatternCache var1, PatternMatcher var2, String var3) {
      this._cache = var1;
      this._matcher = var2;
      this.setFilterExpression(var3);
   }

   RegexFilenameFilter(PatternCache var1, PatternMatcher var2, String var3, int var4) {
      this._cache = var1;
      this._matcher = var2;
      this.setFilterExpression(var3, var4);
   }

   RegexFilenameFilter(PatternCache var1, PatternMatcher var2) {
      this(var1, var2, "");
   }

   public void setFilterExpression(String var1) throws MalformedCachePatternException {
      this._pattern = this._cache.getPattern(var1);
   }

   public void setFilterExpression(String var1, int var2) throws MalformedCachePatternException {
      this._pattern = this._cache.getPattern(var1, var2);
   }

   public boolean accept(File var1, String var2) {
      synchronized(this._matcher) {
         return this._matcher.matches(var2, this._pattern);
      }
   }

   public boolean accept(File var1) {
      synchronized(this._matcher) {
         return this._matcher.matches(var1.getName(), this._pattern);
      }
   }
}

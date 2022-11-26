package org.python.google.common.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@Beta
@GwtIncompatible
public final class PatternFilenameFilter implements FilenameFilter {
   private final Pattern pattern;

   public PatternFilenameFilter(String patternStr) {
      this(Pattern.compile(patternStr));
   }

   public PatternFilenameFilter(Pattern pattern) {
      this.pattern = (Pattern)Preconditions.checkNotNull(pattern);
   }

   public boolean accept(@Nullable File dir, String fileName) {
      return this.pattern.matcher(fileName).matches();
   }
}

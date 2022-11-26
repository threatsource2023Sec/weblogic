package org.apache.oro.text;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.util.CacheRandom;

public final class PatternCacheRandom extends GenericPatternCache {
   public PatternCacheRandom(int var1, PatternCompiler var2) {
      super(new CacheRandom(var1), var2);
   }

   public PatternCacheRandom(PatternCompiler var1) {
      this(20, var1);
   }

   public PatternCacheRandom(int var1) {
      this(var1, new Perl5Compiler());
   }

   public PatternCacheRandom() {
      this(20);
   }
}

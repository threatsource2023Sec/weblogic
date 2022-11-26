package weblogic.apache.org.apache.oro.text;

import weblogic.apache.org.apache.oro.text.regex.PatternCompiler;
import weblogic.apache.org.apache.oro.text.regex.Perl5Compiler;
import weblogic.apache.org.apache.oro.util.CacheRandom;

public final class PatternCacheRandom extends GenericPatternCache {
   public PatternCacheRandom() {
      this(20);
   }

   public PatternCacheRandom(int var1) {
      this(var1, new Perl5Compiler());
   }

   public PatternCacheRandom(int var1, PatternCompiler var2) {
      super(new CacheRandom(var1), var2);
   }

   public PatternCacheRandom(PatternCompiler var1) {
      this(20, var1);
   }
}

package org.apache.oro.text;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.util.CacheLRU;

public final class PatternCacheLRU extends GenericPatternCache {
   public PatternCacheLRU(int var1, PatternCompiler var2) {
      super(new CacheLRU(var1), var2);
   }

   public PatternCacheLRU(PatternCompiler var1) {
      this(20, var1);
   }

   public PatternCacheLRU(int var1) {
      this(var1, new Perl5Compiler());
   }

   public PatternCacheLRU() {
      this(20);
   }
}

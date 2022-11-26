package org.apache.oro.text;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.util.CacheFIFO2;

public final class PatternCacheFIFO2 extends GenericPatternCache {
   public PatternCacheFIFO2(int var1, PatternCompiler var2) {
      super(new CacheFIFO2(var1), var2);
   }

   public PatternCacheFIFO2(PatternCompiler var1) {
      this(20, var1);
   }

   public PatternCacheFIFO2(int var1) {
      this(var1, new Perl5Compiler());
   }

   public PatternCacheFIFO2() {
      this(20);
   }
}

package weblogic.apache.org.apache.oro.text;

import weblogic.apache.org.apache.oro.text.regex.PatternCompiler;
import weblogic.apache.org.apache.oro.text.regex.Perl5Compiler;
import weblogic.apache.org.apache.oro.util.CacheFIFO;

public final class PatternCacheFIFO extends GenericPatternCache {
   public PatternCacheFIFO() {
      this(20);
   }

   public PatternCacheFIFO(int var1) {
      this(var1, new Perl5Compiler());
   }

   public PatternCacheFIFO(int var1, PatternCompiler var2) {
      super(new CacheFIFO(var1), var2);
   }

   public PatternCacheFIFO(PatternCompiler var1) {
      this(20, var1);
   }
}

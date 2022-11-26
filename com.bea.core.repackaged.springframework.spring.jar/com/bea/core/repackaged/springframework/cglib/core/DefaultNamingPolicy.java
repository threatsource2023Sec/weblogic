package com.bea.core.repackaged.springframework.cglib.core;

public class DefaultNamingPolicy implements NamingPolicy {
   public static final DefaultNamingPolicy INSTANCE = new DefaultNamingPolicy();
   private static final boolean STRESS_HASH_CODE = Boolean.getBoolean("com.bea.core.repackaged.springframework.cglib.test.stressHashCodes");

   public String getClassName(String prefix, String source, Object key, Predicate names) {
      if (prefix == null) {
         prefix = "com.bea.core.repackaged.springframework.cglib.empty.Object";
      } else if (prefix.startsWith("java")) {
         prefix = "$" + prefix;
      }

      String base = prefix + "$$" + source.substring(source.lastIndexOf(46) + 1) + this.getTag() + "$$" + Integer.toHexString(STRESS_HASH_CODE ? 0 : key.hashCode());
      String attempt = base;

      for(int index = 2; names.evaluate(attempt); attempt = base + "_" + index++) {
      }

      return attempt;
   }

   protected String getTag() {
      return "ByCGLIB";
   }

   public int hashCode() {
      return this.getTag().hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof DefaultNamingPolicy && ((DefaultNamingPolicy)o).getTag().equals(this.getTag());
   }
}

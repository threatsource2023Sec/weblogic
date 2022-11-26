package javax.security.jacc;

class URLPattern implements Comparable {
   private static String DEFAULT_PATTERN = "/";
   public static final int PT_DEFAULT = 0;
   public static final int PT_EXTENSION = 1;
   public static final int PT_PREFIX = 2;
   public static final int PT_EXACT = 3;
   private int patternType = -1;
   private final String pattern;

   public URLPattern() {
      this.pattern = DEFAULT_PATTERN;
      this.patternType = 0;
   }

   public URLPattern(String pattern) {
      if (pattern == null) {
         this.pattern = DEFAULT_PATTERN;
         this.patternType = 0;
      } else {
         this.pattern = pattern;
      }

   }

   public int patternType() {
      if (this.patternType < 0) {
         if (this.pattern.startsWith("*.")) {
            this.patternType = 1;
         } else if (this.pattern.startsWith("/") && this.pattern.endsWith("/*")) {
            this.patternType = 2;
         } else if (DEFAULT_PATTERN.equals(this.pattern)) {
            this.patternType = 0;
         } else {
            this.patternType = 3;
         }
      }

      return this.patternType;
   }

   public int compareTo(URLPattern that) {
      int refPatternType = this.patternType();
      int result = refPatternType - that.patternType();
      if (result == 0) {
         if (refPatternType != 2 && refPatternType != 3) {
            result = this.pattern.compareTo(that.pattern);
         } else {
            result = this.getPatternDepth() - that.getPatternDepth();
            if (result == 0) {
               result = this.pattern.compareTo(that.pattern);
            }
         }
      }

      return result > 0 ? 1 : (result < 0 ? -1 : 0);
   }

   public boolean implies(URLPattern that) {
      if (that == null) {
         that = new URLPattern((String)null);
      }

      String thatPattern = that.pattern;
      String thisPattern = this.pattern;
      if (thisPattern.equals(thatPattern)) {
         return true;
      } else {
         int slash;
         if (thisPattern.startsWith("/") && thisPattern.endsWith("/*")) {
            thisPattern = thisPattern.substring(0, thisPattern.length() - 2);
            slash = thisPattern.length();
            if (slash == 0) {
               return true;
            } else {
               return thatPattern.startsWith(thisPattern) && (thatPattern.length() == slash || thatPattern.substring(slash).startsWith("/"));
            }
         } else if (thisPattern.startsWith("*.")) {
            slash = thatPattern.lastIndexOf(47);
            int period = thatPattern.lastIndexOf(46);
            return slash >= 0 && period > slash && thatPattern.endsWith(thisPattern.substring(1));
         } else {
            return thisPattern.equals(DEFAULT_PATTERN);
         }
      }
   }

   public boolean equals(Object obj) {
      return !(obj instanceof URLPattern) ? false : this.pattern.equals(((URLPattern)obj).pattern);
   }

   public String toString() {
      return this.pattern;
   }

   public int getPatternDepth() {
      int i = 0;
      int depth = 1;

      while(i >= 0) {
         i = this.pattern.indexOf("/", i);
         if (i >= 0) {
            if (i == 0 && depth != 1) {
               throw new IllegalArgumentException("// in pattern");
            }

            ++i;
         }
      }

      return depth;
   }
}

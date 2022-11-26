package weblogic.application.archive.collage.parser;

public class Pattern {
   protected String pattern;
   protected boolean isInclude;

   private Pattern(boolean isInclude) {
      this.isInclude = isInclude;
   }

   protected static Pattern newExcludePattern() {
      return new Pattern(false);
   }

   protected static Pattern newIncludePattern() {
      return new Pattern(true);
   }

   public void init(String contents) {
      this.pattern = contents;
   }

   public String toString() {
      return super.toString() + " , " + (this.isInclude ? "include: " : "exclude: " + this.pattern);
   }
}

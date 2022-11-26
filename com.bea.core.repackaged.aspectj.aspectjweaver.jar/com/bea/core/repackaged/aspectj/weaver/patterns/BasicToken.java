package com.bea.core.repackaged.aspectj.weaver.patterns;

public final class BasicToken implements IToken {
   private String value;
   private boolean isIdentifier;
   private String literalKind;
   private int start;
   private int end;

   public static BasicToken makeOperator(String value, int start, int end) {
      return new BasicToken(value.intern(), false, (String)null, start, end);
   }

   public static BasicToken makeIdentifier(String value, int start, int end) {
      return new BasicToken(value, true, (String)null, start, end);
   }

   public static BasicToken makeLiteral(String value, String kind, int start, int end) {
      return new BasicToken(value, false, kind.intern(), start, end);
   }

   private BasicToken(String value, boolean isIdentifier, String literalKind, int start, int end) {
      this.value = value;
      this.isIdentifier = isIdentifier;
      this.literalKind = literalKind;
      this.start = start;
      this.end = end;
   }

   public int getStart() {
      return this.start;
   }

   public int getEnd() {
      return this.end;
   }

   public String getFileName() {
      return "unknown";
   }

   public String getString() {
      return this.value;
   }

   public boolean isIdentifier() {
      return this.isIdentifier;
   }

   public Pointcut maybeGetParsedPointcut() {
      return null;
   }

   public String toString() {
      String s;
      if (this.isIdentifier) {
         s = this.value;
      } else {
         s = "'" + this.value + "'";
      }

      return s + "@" + this.start + ":" + this.end;
   }

   public String getLiteralKind() {
      return this.literalKind;
   }
}

package weblogic.entitlement.parser;

public class ParseException extends Exception {
   private int exprIndex = -1;

   public ParseException() {
   }

   public ParseException(String message) {
      super(message);
   }

   public ParseException(String message, int exprIndex) {
      super(message);
      this.exprIndex = exprIndex;
   }

   public int getExprIndex() {
      return this.exprIndex;
   }
}

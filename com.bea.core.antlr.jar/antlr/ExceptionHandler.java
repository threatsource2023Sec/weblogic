package antlr;

class ExceptionHandler {
   protected Token exceptionTypeAndName;
   protected Token action;

   public ExceptionHandler(Token var1, Token var2) {
      this.exceptionTypeAndName = var1;
      this.action = var2;
   }
}

package antlr;

import antlr.collections.impl.Vector;

class ExceptionSpec {
   protected Token label;
   protected Vector handlers;

   public ExceptionSpec(Token var1) {
      this.label = var1;
      this.handlers = new Vector();
   }

   public void addHandler(ExceptionHandler var1) {
      this.handlers.appendElement(var1);
   }
}

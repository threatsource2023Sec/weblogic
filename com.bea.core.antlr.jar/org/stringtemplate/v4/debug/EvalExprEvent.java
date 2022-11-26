package org.stringtemplate.v4.debug;

import org.stringtemplate.v4.InstanceScope;

public class EvalExprEvent extends InterpEvent {
   public final int exprStartChar;
   public final int exprStopChar;
   public final String expr;

   public EvalExprEvent(InstanceScope scope, int start, int stop, int exprStartChar, int exprStopChar) {
      super(scope, start, stop);
      this.exprStartChar = exprStartChar;
      this.exprStopChar = exprStopChar;
      if (exprStartChar >= 0 && exprStopChar >= 0) {
         this.expr = scope.st.impl.template.substring(exprStartChar, exprStopChar + 1);
      } else {
         this.expr = "";
      }

   }

   public String toString() {
      return this.getClass().getSimpleName() + "{" + "self=" + this.scope.st + ", expr='" + this.expr + '\'' + ", exprStartChar=" + this.exprStartChar + ", exprStopChar=" + this.exprStopChar + ", start=" + this.outputStartChar + ", stop=" + this.outputStopChar + '}';
   }
}

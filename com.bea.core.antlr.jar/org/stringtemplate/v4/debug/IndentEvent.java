package org.stringtemplate.v4.debug;

import org.stringtemplate.v4.InstanceScope;

public class IndentEvent extends EvalExprEvent {
   public IndentEvent(InstanceScope scope, int start, int stop, int exprStartChar, int exprStopChar) {
      super(scope, start, stop, exprStartChar, exprStopChar);
   }
}

package org.stringtemplate.v4.debug;

import org.stringtemplate.v4.InstanceScope;

public class EvalTemplateEvent extends InterpEvent {
   public EvalTemplateEvent(InstanceScope scope, int exprStartChar, int exprStopChar) {
      super(scope, exprStartChar, exprStopChar);
   }
}

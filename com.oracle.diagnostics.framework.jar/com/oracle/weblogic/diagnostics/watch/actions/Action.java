package com.oracle.weblogic.diagnostics.watch.actions;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Action {
   void execute(ActionContext var1);

   void cancel();

   void reset();
}

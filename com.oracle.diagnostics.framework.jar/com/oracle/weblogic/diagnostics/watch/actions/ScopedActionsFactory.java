package com.oracle.weblogic.diagnostics.watch.actions;

public interface ScopedActionsFactory {
   String[] getActionTypes();

   Action getAction(String var1);

   ActionConfigBean getActionConfig(String var1);

   void destroy();
}

package com.oracle.weblogic.diagnostics.watch.actions;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ActionConfigBean {
   String getName();

   void setName(String var1);

   boolean isEnabled();

   void setEnabled(boolean var1);

   int getTimeout();

   void setTimeout(int var1);
}

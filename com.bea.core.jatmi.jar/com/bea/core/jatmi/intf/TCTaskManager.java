package com.bea.core.jatmi.intf;

import weblogic.wtc.jatmi.TPException;

public interface TCTaskManager {
   void initialize() throws TPException;

   void shutdown(int var1);

   void schedule(TCTask var1);
}

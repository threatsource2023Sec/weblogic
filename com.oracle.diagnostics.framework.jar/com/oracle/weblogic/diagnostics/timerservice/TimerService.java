package com.oracle.weblogic.diagnostics.timerservice;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface TimerService {
   void registerListener(TimerListener var1);

   boolean unregisterListener(TimerListener var1);
}

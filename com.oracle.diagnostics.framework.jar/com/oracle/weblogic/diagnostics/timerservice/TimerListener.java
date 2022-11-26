package com.oracle.weblogic.diagnostics.timerservice;

public interface TimerListener {
   int getFrequency();

   void timerExpired();
}

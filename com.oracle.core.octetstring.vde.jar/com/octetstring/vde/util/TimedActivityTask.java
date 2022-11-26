package com.octetstring.vde.util;

public interface TimedActivityTask {
   void runTask();

   int getHour();

   int getMinute();

   boolean hasRun();

   void setRun(boolean var1);
}

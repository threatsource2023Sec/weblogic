package com.oracle.weblogic.diagnostics.watch;

import java.util.logging.Level;

public interface WatchInfo {
   String getName();

   boolean isEnabled();

   boolean isInAlarmState();

   String getState();

   Level getLogLevel();

   int getAlarmResetPeriod();

   String getAlarmResetType();

   String getExpression();

   String getType();
}

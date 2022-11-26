package com.oracle.jrockit.jfr;

import java.net.URI;

public interface EventInfo {
   int getId();

   URI getURI();

   String getPath();

   String getName();

   String getDescription();

   boolean isEnabled();

   boolean hasStackTrace();

   boolean hasThread();

   boolean hasStartTime();

   boolean isStackTraceEnabled();

   boolean isRequestable();

   boolean isTimed();

   long getThreshold();

   long getPeriod();
}

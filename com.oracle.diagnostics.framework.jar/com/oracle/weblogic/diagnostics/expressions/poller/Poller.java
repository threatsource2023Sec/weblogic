package com.oracle.weblogic.diagnostics.expressions.poller;

import java.util.Collection;

public interface Poller extends Iterable {
   String getKey();

   void startPolling();

   boolean isPolling();

   void stopPolling();

   int getFrequency();

   void reset();

   Collection getResolvedValues();
}

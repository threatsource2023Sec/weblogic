package org.apache.openjpa.kernel;

public interface SavepointManager {
   boolean supportsIncrementalFlush();

   OpenJPASavepoint newSavepoint(String var1, Broker var2);
}

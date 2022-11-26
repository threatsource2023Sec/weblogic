package com.solarmetric.profile;

import org.apache.openjpa.lib.util.Closeable;

public interface ProfilingInterface extends Closeable {
   void setProfilingAgent(ProfilingAgent var1);

   void init();

   void run();

   void close();
}

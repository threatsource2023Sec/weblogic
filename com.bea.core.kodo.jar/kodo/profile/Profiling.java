package kodo.profile;

import com.solarmetric.profile.ProfilingInterface;
import java.util.Collection;

public interface Profiling {
   KodoProfilingAgent getProfilingAgent();

   ProfilingInterface getProfilingInterface();

   void initProfiling(KodoProfilingAgent var1);

   Collection getPlugins();
}

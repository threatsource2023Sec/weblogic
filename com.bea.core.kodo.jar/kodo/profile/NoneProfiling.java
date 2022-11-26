package kodo.profile;

import com.solarmetric.profile.ProfilingInterface;
import java.util.Collection;

public class NoneProfiling implements Profiling {
   public KodoProfilingAgent getProfilingAgent() {
      return null;
   }

   public ProfilingInterface getProfilingInterface() {
      return null;
   }

   public void initProfiling(KodoProfilingAgent agent) {
   }

   public Collection getPlugins() {
      return null;
   }
}

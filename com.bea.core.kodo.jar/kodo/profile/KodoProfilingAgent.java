package kodo.profile;

import com.solarmetric.profile.ProfilingAgent;
import org.apache.openjpa.meta.ClassMetaData;

public interface KodoProfilingAgent extends ProfilingAgent {
   ProfilingClassMetaData getMetaData(String var1);

   ProfilingClassMetaData registerMetaData(ClassMetaData var1);
}

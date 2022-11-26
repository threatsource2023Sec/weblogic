package kodo.profile;

import com.solarmetric.profile.ExecutionContextNameProvider;
import com.solarmetric.profile.ProfilingInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import kodo.kernel.ExecutionContextNameProviderValue;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public abstract class AbstractProfiling implements Profiling, Configurable {
   private static final Localizer _loc = Localizer.forPackage(AbstractProfiling.class);
   protected OpenJPAConfiguration conf;

   public void setConfiguration(Configuration conf) {
      this.conf = (OpenJPAConfiguration)conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public abstract KodoProfilingAgent getProfilingAgent();

   public void initProfiling(KodoProfilingAgent agent) {
      ProfilingInterface profilingInterface = this.getProfilingInterface();
      if (profilingInterface != null) {
         profilingInterface.setProfilingAgent(agent);
         profilingInterface.init();
         profilingInterface.run();
      }

   }

   public void closeProfiling() {
      ProfilingInterface profilingInterface = this.getProfilingInterface();
      if (profilingInterface != null) {
         profilingInterface.close();
      }

   }

   public Collection getPlugins() {
      ProfilingInterface profilingInterface = this.getProfilingInterface();
      if (profilingInterface != null) {
         Collection c = new ArrayList(1);
         c.add(profilingInterface);
         return c;
      } else {
         return Collections.EMPTY_SET;
      }
   }

   public abstract ProfilingInterface getProfilingInterface();

   public void setStackStyle(String stackStyle) {
      ExecutionContextNameProvider helper = ExecutionContextNameProviderValue.getExecutionContextNameProvider(this.conf);
      if (helper instanceof KodoExecutionContextNameProviderImpl) {
         ((KodoExecutionContextNameProviderImpl)helper).setStackStyle(stackStyle);
      } else {
         throw new UserException(_loc.get("no-setStackStyle"));
      }
   }
}

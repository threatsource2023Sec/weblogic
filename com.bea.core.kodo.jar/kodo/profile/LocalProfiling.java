package kodo.profile;

import com.solarmetric.profile.ProfilingInterface;

public class LocalProfiling extends AbstractProfiling {
   private KodoProfilingAgent agent = null;

   public void startConfiguration() {
      super.startConfiguration();
      this.agent = new KodoProfilingAgentImpl(this.conf);
   }

   public KodoProfilingAgent getProfilingAgent() {
      return this.agent;
   }

   public ProfilingInterface getProfilingInterface() {
      return null;
   }
}

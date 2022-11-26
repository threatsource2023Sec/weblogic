package com.solarmetric.profile;

import java.util.EventObject;

public class ProfilingAgentEvent extends EventObject {
   private Node node;

   public ProfilingAgentEvent(ProfilingAgent profilingAgent, Node node) {
      super(profilingAgent);
      this.node = node;
   }

   public ProfilingAgent getProfilingAgent() {
      return (ProfilingAgent)this.getSource();
   }

   public Node getNode() {
      return this.node;
   }
}

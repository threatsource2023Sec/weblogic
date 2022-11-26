package monfox.toolkit.snmp.agent.x.sub.service;

import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMibView;

public class AgentXAccessPolicy extends SnmpAccessPolicy {
   public AgentXAccessPolicy(boolean var1) {
      super("", !var1, (SnmpMibView)null);
   }
}

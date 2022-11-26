package com.oracle.cmm.agent.internal;

import com.oracle.cmm.agent.CMMAgent;
import com.oracle.cmm.agent.CMMAgentGenerator;

public class DefaultCMMAgentGenerator implements CMMAgentGenerator {
   public CMMAgent createCMMAgent() {
      return new CMMAgentImpl();
   }
}

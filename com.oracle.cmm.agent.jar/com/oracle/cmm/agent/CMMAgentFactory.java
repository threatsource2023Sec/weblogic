package com.oracle.cmm.agent;

import com.oracle.cmm.agent.internal.CMMAgentFactoryImpl;

public abstract class CMMAgentFactory {
   private static final CMMAgentFactory INSTANCE = new CMMAgentFactoryImpl();

   public static CMMAgentFactory getInstance() {
      return INSTANCE;
   }

   public abstract CMMAgent findOrCreateAgent();

   public abstract CMMAgent findOrCreateAgent(CMMAgentGenerator var1);
}

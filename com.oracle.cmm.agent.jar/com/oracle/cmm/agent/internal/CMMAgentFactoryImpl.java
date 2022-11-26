package com.oracle.cmm.agent.internal;

import com.oracle.cmm.agent.CMMAgent;
import com.oracle.cmm.agent.CMMAgentFactory;
import com.oracle.cmm.agent.CMMAgentGenerator;
import java.util.Iterator;
import java.util.ServiceLoader;

public class CMMAgentFactoryImpl extends CMMAgentFactory {
   private static final CMMAgentGenerator DEFAULT_GENERATOR = new DefaultCMMAgentGenerator();

   public CMMAgent findOrCreateAgent() {
      return this.internalFindOrCreateAgent(this.getGeneratorFromMETAINF());
   }

   public CMMAgent findOrCreateAgent(CMMAgentGenerator generator) {
      if (generator == null) {
         throw new IllegalArgumentException("gnerator is null in findOrCreateAgent method of CMMAgentFactoryImpl");
      } else {
         return this.internalFindOrCreateAgent(generator);
      }
   }

   private CMMAgentGenerator getGeneratorFromMETAINF() {
      ServiceLoader loader = ServiceLoader.load(CMMAgentGenerator.class);
      Iterator i$ = loader.iterator();
      if (i$.hasNext()) {
         CMMAgentGenerator generator = (CMMAgentGenerator)i$.next();
         return generator;
      } else {
         return DEFAULT_GENERATOR;
      }
   }

   private CMMAgent internalFindOrCreateAgent(CMMAgentGenerator generator) {
      return generator.createCMMAgent();
   }
}

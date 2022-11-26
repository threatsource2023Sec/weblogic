package weblogic.management.patching.agent;

import java.util.logging.Logger;

public class ZdtUpdateApplicationPlugin extends ZdtAgentPlugin {
   ZdtUpdateApplicationPlugin(Logger nmLogger) {
      super(nmLogger);
   }

   protected ZdtAgent createZdtAgent(ZdtAgentRequest request, ZdtAgentOutputHandler outputHandler) {
      return new ZdtUpdateApplicationAgent(request, outputHandler, this.nmProvider.getDomainDirectory());
   }
}

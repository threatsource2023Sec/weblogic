package weblogic.management.patching.agent;

import java.util.logging.Logger;

public class ZdtUpdateOracleHomePlugin extends ZdtAgentPlugin {
   ZdtUpdateOracleHomePlugin(Logger nmLogger) {
      super(nmLogger);
   }

   protected ZdtAgent createZdtAgent(ZdtAgentRequest request, ZdtAgentOutputHandler outputHandler) {
      return new ZdtUpdateOracleHomeAgent(request, outputHandler);
   }
}

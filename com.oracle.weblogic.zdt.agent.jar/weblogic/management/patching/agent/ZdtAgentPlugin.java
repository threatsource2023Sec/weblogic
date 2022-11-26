package weblogic.management.patching.agent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.plugin.InvocationPlugin;
import weblogic.nodemanager.plugin.Provider;

public abstract class ZdtAgentPlugin implements InvocationPlugin {
   protected Logger nmLogger;
   protected Provider nmProvider;

   protected ZdtAgentPlugin(Logger nmLogger) {
      this.nmLogger = nmLogger;
   }

   public void init(Provider provider) {
      this.nmProvider = provider;
   }

   protected abstract ZdtAgent createZdtAgent(ZdtAgentRequest var1, ZdtAgentOutputHandler var2);

   public void invocationRequest(String[] command, OutputStream out) throws IOException {
      StringBuffer sb = new StringBuffer("ZdtAgentPlugin in jar: " + this.getClass().getProtectionDomain().getCodeSource().getLocation() + "\nreceived command: ");
      String[] var4 = command;
      int var5 = command.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String part = var4[var6];
         sb.append(part);
         sb.append("\n");
      }

      if (this.nmLogger.isLoggable(Level.FINEST)) {
         this.nmLogger.finest(sb.toString());
      }

      ZdtAgentInputOutput io = new ZdtAgentInputOutput();
      ZdtAgentBufferedOutputHandler zdtOutput = null;

      try {
         ZdtAgentRequest zdtAgentRequest = new ZdtAgentRequest(command);
         zdtAgentRequest.setDomainDirectory(this.nmProvider.getDomainDirectory().getPath());
         zdtOutput = new ZdtAgentBufferedOutputHandler(zdtAgentRequest);
         ZdtAgent zdtAgent = this.createZdtAgent(zdtAgentRequest, zdtOutput);
         zdtAgent.execRequest();
         io.success();
      } catch (Exception var8) {
         if (this.nmLogger.isLoggable(Level.FINEST)) {
            this.nmLogger.log(Level.FINEST, "Caught error executing zdtAgent request", var8);
         }

         io.failure(var8);
      }

      if (zdtOutput != null) {
         io.setMessages(zdtOutput.getMessages());
      }

      io.writeResponse(out);
   }
}

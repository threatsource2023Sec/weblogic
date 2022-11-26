package weblogic.jms.safclient.agent.internal;

import weblogic.jms.forwarder.RuntimeHandler;

public final class RuntimeHandlerImpl implements RuntimeHandler {
   private String agentName;
   private String destinationName;
   private String remoteContextName;

   public RuntimeHandlerImpl(String paramAgentName, String paramDestinationName, String paramRemoteContextName) {
      this.agentName = paramAgentName;
      this.destinationName = paramDestinationName;
      this.remoteContextName = paramRemoteContextName;
   }

   public void disconnected(Exception exception) {
      System.out.println("Agent \"" + this.agentName + "\" lost the connection to " + this.remoteContextName + " while processing messages for " + this.destinationName);
      Throwable cause = exception;

      for(int level = 0; cause != null; cause = ((Throwable)cause).getCause()) {
         System.out.println("Stack level " + level++);
         ((Throwable)cause).printStackTrace();
      }

      System.out.println("disconnect stack trace finished");
   }

   public void connected() {
      System.out.println("Agent \"" + this.agentName + "\" got connected to " + this.remoteContextName + " while processing messages for " + this.destinationName);
   }
}

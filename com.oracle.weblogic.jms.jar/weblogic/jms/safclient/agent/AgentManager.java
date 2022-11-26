package weblogic.jms.safclient.agent;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import org.w3c.dom.Document;
import weblogic.jms.safclient.MessageMigrator;
import weblogic.jms.safclient.admin.ConfigurationUtils;
import weblogic.jms.safclient.agent.internal.Agent;
import weblogic.jms.safclient.agent.internal.RemoteContext;
import weblogic.jms.safclient.jndi.ContextImpl;

public final class AgentManager {
   private static final String SEPARATOR = "!";
   private Agent agent;
   private HashMap remoteContextMap = new HashMap();
   private HashMap errorHandlerMap = new HashMap();
   private ContextImpl context;
   private static int managerSequenceNumber;
   private static Object managerSequenceNumberLock = new Object();
   private static int agentNumber;
   private static Object numberLock = new Object();
   private static final String AGENT_COMPONENT = "ClientSAFAgent";

   public static String constructDestinationName(String agentName, String destName) {
      return agentName + "!" + destName;
   }

   private static String getAgentName() {
      if (MessageMigrator.revertBugFix) {
         int currentNum;
         synchronized(numberLock) {
            currentNum = agentNumber++;
         }

         return "ClientSAFAgent" + currentNum;
      } else {
         return "ClientSAFAgent0";
      }
   }

   public AgentManager(Document config, ContextImpl paramContext, char[] key, File rootDirectory) throws JMSException {
      boolean failed = true;

      try {
         this.context = paramContext;
         String agentName = getAgentName();
         this.agent = new Agent(agentName, rootDirectory);
         ConfigurationUtils.doAgent(config, this.agent);
         ConfigurationUtils.doRemoteContexts(config, this.agent, this.remoteContextMap, key);
         ConfigurationUtils.doErrorHandlers(config, this.errorHandlerMap);
         this.agent.initialize();
         ConfigurationUtils.doImportedDestinationGroup(config, this.remoteContextMap, this.errorHandlerMap, this.agent, this.context);
         ConfigurationUtils.resolveErrorDestinations(this.errorHandlerMap, this.context);
         failed = false;
      } finally {
         if (failed) {
            try {
               this.shutdown();
            } catch (Throwable var12) {
            }
         }

      }

   }

   public void shutdown() throws JMSException {
      synchronized(this.remoteContextMap) {
         Iterator it = this.remoteContextMap.values().iterator();

         while(true) {
            if (!it.hasNext()) {
               this.remoteContextMap.clear();
               break;
            }

            RemoteContext remoteContext = (RemoteContext)it.next();
            remoteContext.shutdown();
         }
      }

      synchronized(this.errorHandlerMap) {
         this.errorHandlerMap.clear();
      }

      if (this.agent != null) {
         this.agent.shutdown();
         this.agent = null;
      }

   }

   public DestinationImpl getDestination(String groupName, String destinationName) {
      return this.context.getDestination(groupName, destinationName);
   }

   public static String getManagerSequence() {
      int currentNum;
      synchronized(managerSequenceNumberLock) {
         currentNum = managerSequenceNumber++;
      }

      return "." + currentNum;
   }
}

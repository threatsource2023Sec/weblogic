package com.bea.diagnostics.notifications;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentConfig;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;

public final class SNMPNotificationService extends NotificationServiceAdapter implements NotificationConstants {
   private Map keyMap;
   private SNMPAgent agent;
   private String trapName;
   private SNMPNotificationCustomizer customizer;
   private String agentName;

   SNMPNotificationService(String serviceName, String trapName, SNMPAgent agent, Map varToKeyMap, SNMPNotificationCustomizer customizer) throws InvalidNotificationException, NotificationCreateException {
      super(serviceName);
      this.trapName = trapName;
      this.agent = agent;
      this.keyMap = varToKeyMap;
      this.customizer = customizer;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created snmp notification " + this);
      }

   }

   SNMPNotificationService(String serviceName, String trapName, String agentName, Map varToKeyMap, SNMPNotificationCustomizer customizer) throws InvalidNotificationException, NotificationCreateException {
      super(serviceName);
      this.trapName = trapName;
      this.agentName = agentName;
      this.keyMap = varToKeyMap;
      this.customizer = customizer;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created snmp notification " + this);
      }

   }

   public String getType() {
      return "SNMP";
   }

   public void send(Notification n) throws NotificationPropagationException {
      if (this.isEnabled()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Handling SNMP notification for service " + this.getName());
            debugLogger.debug("Notification: " + n);
         }

         try {
            Object varBindings;
            if (this.customizer != null) {
               varBindings = this.customizer.processNotification(n);
            } else {
               varBindings = new LinkedList();
               Iterator keyIterator;
               Object dataKey;
               if (this.keyMap != null) {
                  keyIterator = this.keyMap.keySet().iterator();

                  while(keyIterator.hasNext()) {
                     dataKey = keyIterator.next();
                     Object dataKey = this.keyMap.get(dataKey);
                     ((List)varBindings).add(new Object[]{dataKey, n.getValue(dataKey)});
                  }
               } else {
                  keyIterator = n.keyList().iterator();

                  while(keyIterator.hasNext()) {
                     dataKey = keyIterator.next();
                     ((List)varBindings).add(new Object[]{dataKey.toString(), n.getValue(dataKey)});
                  }
               }
            }

            if (varBindings != null) {
               if (this.agent == null) {
                  throw new NotificationPropagationException(NotificationsTextTextFormatter.getInstance().getSNMPAgentUnavailableText(n.toString()));
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Sending SNMP trap via agent");
               }

               SNMPNotificationManager notifier = this.agent.getSNMPAgentToolkit().getSNMPNotificationManager();
               String notifyGroup = this.agent.getNotifyGroup();
               String notifName = this.trapName;
               notifier.sendNotification(notifyGroup, notifName, (List)varBindings);
            }
         } catch (Throwable var6) {
            throw new NotificationPropagationException(var6);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Sent SNMP notification: " + n.toString());
         }

      }
   }

   public void destroy() {
      super.destroy();
      this.agent = null;
      this.keyMap = null;
      this.customizer = null;
      this.trapName = null;
   }

   public String getSnmpAgentName() {
      return this.agentName;
   }

   public void setSnmpAgent(SNMPAgentConfig agentConfig) {
      this.agent = agentConfig.getSNMPAgent();
   }

   public void setAgent(SNMPAgent agent) {
      this.agent = agent;
   }

   public SNMPAgent getAgent() {
      return this.agent;
   }
}

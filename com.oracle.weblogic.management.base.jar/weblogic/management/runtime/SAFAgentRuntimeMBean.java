package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.messaging.saf.SAFException;

public interface SAFAgentRuntimeMBean extends SAFStatisticsCommonMBean, HealthFeedback {
   HealthState getHealthState();

   SAFRemoteEndpointRuntimeMBean[] getRemoteEndpoints();

   long getRemoteEndpointsCurrentCount();

   long getRemoteEndpointsHighCount();

   long getRemoteEndpointsTotalCount();

   void pauseIncoming() throws SAFException;

   void resumeIncoming() throws SAFException;

   boolean isPausedForIncoming();

   void pauseForwarding() throws SAFException;

   void resumeForwarding() throws SAFException;

   boolean isPausedForForwarding();

   void pauseReceiving() throws SAFException;

   void resumeReceiving() throws SAFException;

   boolean isPausedForReceiving();

   SAFConversationRuntimeMBean[] getConversations();

   long getConversationsCurrentCount();

   long getConversationsHighCount();

   long getConversationsTotalCount();

   LogRuntimeMBean getLogRuntime();

   void setLogRuntime(LogRuntimeMBean var1);
}

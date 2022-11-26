package weblogic.jms.saf;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.management.runtime.SAFConversationRuntimeMBean;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.messaging.saf.SAFException;
import weblogic.store.common.PartitionNameUtils;

public class SAFAgentRuntimeMBeanAggregator extends RuntimeMBeanDelegate implements SAFAgentRuntimeMBean {
   private final SAFAgentRuntimeMBean delegate1;
   private SAFAgentRuntimeMBean delegate2;
   private String decoratedName;
   private LogRuntimeMBean logRuntime;

   public SAFAgentRuntimeMBeanAggregator(String name, SAFAgentRuntimeMBean delegate) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(name), true);
      this.decoratedName = name;
      this.delegate1 = delegate;
   }

   public String getDecoratedName() {
      return this.decoratedName;
   }

   SAFAgentRuntimeMBeanImpl getJMSSAFAgentRuntime() {
      return (SAFAgentRuntimeMBeanImpl)this.delegate1;
   }

   public void setDelegate2(SAFAgentRuntimeMBean delegate) {
      this.delegate2 = delegate;
   }

   public HealthState getHealthState() {
      return this.delegate1.getHealthState();
   }

   public SAFRemoteEndpointRuntimeMBean[] getRemoteEndpoints() {
      HashSet ret = new HashSet();
      ret.addAll(Arrays.asList(this.delegate1.getRemoteEndpoints()));
      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         ret.addAll(Arrays.asList(tmp.getRemoteEndpoints()));
      }

      return (SAFRemoteEndpointRuntimeMBean[])((SAFRemoteEndpointRuntimeMBean[])ret.toArray(new SAFRemoteEndpointRuntimeMBean[ret.size()]));
   }

   public long getRemoteEndpointsCurrentCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getRemoteEndpointsCurrentCount() + (tmp == null ? 0L : tmp.getRemoteEndpointsCurrentCount());
   }

   public long getRemoteEndpointsHighCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getRemoteEndpointsHighCount() + (tmp == null ? 0L : tmp.getRemoteEndpointsHighCount());
   }

   public long getRemoteEndpointsTotalCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getRemoteEndpointsTotalCount() + (tmp == null ? 0L : tmp.getRemoteEndpointsTotalCount());
   }

   public void pauseIncoming() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.pauseIncoming();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.pauseIncoming();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.pauseIncoming();
      }
   }

   public void resumeIncoming() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.resumeIncoming();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.resumeIncoming();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.resumeIncoming();
      }
   }

   public boolean isPausedForIncoming() {
      return this.delegate1.isPausedForIncoming();
   }

   public void pauseForwarding() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.pauseForwarding();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.pauseForwarding();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.pauseForwarding();
      }
   }

   public void resumeForwarding() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.resumeForwarding();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.resumeForwarding();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.resumeForwarding();
      }
   }

   public boolean isPausedForForwarding() {
      return this.delegate1.isPausedForForwarding();
   }

   public void pauseReceiving() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.pauseReceiving();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.pauseReceiving();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.pauseReceiving();
      }
   }

   public void resumeReceiving() throws SAFException {
      boolean var5 = false;

      try {
         var5 = true;
         this.delegate1.resumeReceiving();
         var5 = false;
      } finally {
         if (var5) {
            SAFAgentRuntimeMBean tmp = this.delegate2;
            if (tmp == null) {
               return;
            }

            tmp.resumeReceiving();
         }
      }

      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         tmp.resumeReceiving();
      }
   }

   public boolean isPausedForReceiving() {
      return this.delegate1.isPausedForReceiving();
   }

   public SAFConversationRuntimeMBean[] getConversations() {
      HashSet ret = new HashSet();
      SAFAgentRuntimeMBean tmp = this.delegate2;
      if (tmp != null) {
         ret.addAll(Arrays.asList(tmp.getConversations()));
      }

      return (SAFConversationRuntimeMBean[])((SAFConversationRuntimeMBean[])ret.toArray(new SAFConversationRuntimeMBean[ret.size()]));
   }

   public long getConversationsCurrentCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getConversationsCurrentCount() + (tmp == null ? 0L : tmp.getConversationsCurrentCount());
   }

   public long getConversationsHighCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getConversationsHighCount() + (tmp == null ? 0L : tmp.getConversationsHighCount());
   }

   public long getConversationsTotalCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getConversationsTotalCount() + (tmp == null ? 0L : tmp.getConversationsTotalCount());
   }

   public long getMessagesCurrentCount() {
      return this.delegate1.getMessagesCurrentCount();
   }

   public long getMessagesPendingCount() {
      return this.delegate1.getMessagesPendingCount();
   }

   public long getMessagesHighCount() {
      return this.delegate1.getMessagesHighCount();
   }

   public long getMessagesReceivedCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getMessagesReceivedCount() + (tmp == null ? 0L : tmp.getMessagesReceivedCount());
   }

   public long getMessagesThresholdTime() {
      return this.delegate1.getMessagesThresholdTime();
   }

   public long getBytesPendingCount() {
      return this.delegate1.getBytesPendingCount();
   }

   public long getBytesCurrentCount() {
      return this.delegate1.getBytesCurrentCount();
   }

   public long getBytesHighCount() {
      return this.delegate1.getBytesHighCount();
   }

   public long getBytesReceivedCount() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getBytesReceivedCount() + (tmp == null ? 0L : tmp.getBytesReceivedCount());
   }

   public long getBytesThresholdTime() {
      return this.delegate1.getBytesThresholdTime();
   }

   public long getFailedMessagesTotal() {
      SAFAgentRuntimeMBean tmp = this.delegate2;
      return this.delegate1.getFailedMessagesTotal() + (tmp == null ? 0L : tmp.getFailedMessagesTotal());
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public void setLogRuntime(LogRuntimeMBean logRuntime) {
      this.logRuntime = logRuntime;
   }
}

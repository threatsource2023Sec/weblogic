package weblogic.cluster;

import java.io.IOException;
import java.util.BitSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rmi.spi.HostID;
import weblogic.work.ComponentWorkAdapter;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class ClusterMessageReceiver {
   private static final boolean DEBUG = false;
   private static final int CACHE_SIZE = 3;
   private HostID memberID;
   private MulticastSessionId multicastSessionId;
   private IncomingMessage[] cache;
   protected long currentSeqNum;
   private boolean recoveryInProgress;
   private boolean outOfSync;
   private boolean retryEnabled;
   private WorkManager workManager;
   private ConcurrentHashMap groupMessageStatistics;

   ClusterMessageReceiver(HostID memberID, MulticastSessionId multicastSessionId) {
      this(memberID, multicastSessionId, WorkManagerFactory.getInstance().getDefault());
   }

   ClusterMessageReceiver(HostID memberID, MulticastSessionId multicastSessionId, WorkManager workManager) {
      this.groupMessageStatistics = new ConcurrentHashMap();
      this.memberID = memberID;
      this.multicastSessionId = multicastSessionId;
      this.workManager = workManager;
      this.clear();
   }

   synchronized void dispatch(long seqNum, int fragNum, int size, int offset, boolean isRecover, boolean retryEnabled, byte[] payload) {
      boolean isInActive = PartitionAwareSenderManager.theOne().isMulticastSessionInactive(this.multicastSessionId);
      if (!isInActive) {
         this.retryEnabled = retryEnabled;
         this.processFragment(seqNum, fragNum, size, offset, isRecover, payload);
         ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
         ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_CLUSTER_MESSAGE_RECEIVER_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var13 = null;

         try {
            for(final GroupMessage message = this.getNextMessage(); message != null; message = this.getNextMessage()) {
               final HostID finalid = this.memberID;
               String finalMsgClassName = message.getClass().getName();
               final GroupMessageStat messageStat = (GroupMessageStat)this.groupMessageStatistics.get(message.getClass().getName());
               if (messageStat == null) {
                  messageStat = new GroupMessageStat(finalMsgClassName);
                  this.groupMessageStatistics.put(finalMsgClassName, messageStat);
               }

               messageStat.start();
               WorkAdapter req = new ComponentWorkAdapter() {
                  private HostID id = finalid;
                  private GroupMessage msg = message;
                  private ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();

                  public void run() {
                     long t0 = System.nanoTime();
                     this.msg.execute(this.id);
                     long elapsed = System.nanoTime() - t0;
                     messageStat.finish(elapsed);
                  }

                  public ComponentInvocationContext getComponentInvocationContext() {
                     return this.cic;
                  }

                  public String toString() {
                     return "Dispatch Multicast Msg Fragment";
                  }
               };
               this.workManager.schedule(req);
            }
         } catch (Throwable var28) {
            var13 = var28;
            throw var28;
         } finally {
            if (mic != null) {
               if (var13 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var27) {
                     var13.addSuppressed(var27);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }

   private void processFragment(long seqNum, int fragNum, int size, int offset, boolean isRecover, byte[] payload) {
      if (ClusterFragmentsDebugLogger.isDebugEnabled()) {
         ClusterFragmentsDebugLogger.debug("Received fragment memberID:" + this.memberID + " senderID:" + this.multicastSessionId + " seqNum:" + seqNum + "fragNum:" + fragNum + " containing " + payload.length + " out of " + size + " bytes");
         ClusterFragmentsDebugLogger.debug("currentSeqNum: " + this.currentSeqNum);
      }

      if (seqNum >= this.currentSeqNum) {
         if (seqNum == this.currentSeqNum) {
            if (isRecover && !this.recoveryInProgress) {
               ++this.currentSeqNum;
               return;
            }
         } else if (seqNum > this.currentSeqNum) {
            if (isRecover) {
               this.reportLostMessages(seqNum - this.currentSeqNum);
               this.currentSeqNum = seqNum;
               this.recoveryInProgress = true;
               this.setInSync();
            } else if (this.retryEnabled) {
               this.setOutOfSync();
               if (seqNum >= this.currentSeqNum + 3L) {
                  return;
               }
            } else if (seqNum >= this.currentSeqNum + 3L) {
               long newSeqNum = seqNum - 3L + 1L;
               this.reportLostMessages(newSeqNum - this.currentSeqNum);
               this.currentSeqNum = newSeqNum;
            }
         }

         IncomingMessage im = this.cache[(int)(seqNum % 3L)];
         im.processFragment(seqNum, fragNum, size, offset, payload);
      }
   }

   private GroupMessage getNextMessage() {
      byte[] serializedMessage = null;
      if (this.retryEnabled) {
         serializedMessage = this.cache[(int)(this.currentSeqNum % 3L)].getMessage(this.currentSeqNum);
         if (serializedMessage != null) {
            this.recoveryInProgress = false;
            this.setInSync();
            ++this.currentSeqNum;
         } else {
            this.resetOutOfSync();
         }
      } else {
         for(long i = this.currentSeqNum; i < this.currentSeqNum + 3L; ++i) {
            serializedMessage = this.cache[(int)(i % 3L)].getMessage(i);
            if (serializedMessage != null) {
               if (i > this.currentSeqNum) {
                  this.reportLostMessages(i - this.currentSeqNum);
               }

               this.currentSeqNum = i + 1L;
               break;
            }
         }
      }

      if (serializedMessage != null) {
         try {
            WLObjectInputStream ois = ClusterMessagesManager.getInputStream(serializedMessage);
            GroupMessage message = (GroupMessage)ois.readObjectWL();
            if (ClusterDebugLogger.isDebugEnabled()) {
               ClusterDebugLogger.debug("Received memberID:" + this.memberID + " senderID:" + this.multicastSessionId + " seqNum:" + (this.currentSeqNum - 1L) + " message:" + message);
            }

            return message;
         } catch (IOException var4) {
            if (ClusterMessagesManager.theOne().isUnicastMessagingMode()) {
               ClusterExtensionLogger.logUnicastReceiveError(var4);
            } else {
               ClusterLogger.logMulticastReceiveError(var4);
            }

            return null;
         } catch (ClassNotFoundException var5) {
            if (ClusterMessagesManager.theOne().isUnicastMessagingMode()) {
               ClusterExtensionLogger.logUnicastReceiveError(var5);
            } else {
               ClusterLogger.logMulticastReceiveError(var5);
            }

            return null;
         }
      } else {
         return null;
      }
   }

   synchronized void processLastSeqNum(long lastSeqNum) {
      boolean isInActive = PartitionAwareSenderManager.theOne().isMulticastSessionInactive(this.multicastSessionId);
      if (!isInActive) {
         if (lastSeqNum >= this.currentSeqNum) {
            this.setOutOfSync();
         }

      }
   }

   long getCurrentSeqNum() {
      return this.currentSeqNum;
   }

   protected void setOutOfSync() {
      if (!this.outOfSync) {
         ClusterMessagesManager.theOne().incrementResendRequestsCount();
         this.outOfSync = true;
         IncomingMessage im = this.cache[(int)(this.currentSeqNum % 3L)];
         int nextFragNum = im.nextFragNum(this.currentSeqNum);
         ClusterMessagesManager.theOne().addItem(new NAKHBI(this.memberID, this.multicastSessionId, this.currentSeqNum, nextFragNum));
      }

   }

   private void resetOutOfSync() {
      if (this.outOfSync) {
         IncomingMessage im = this.cache[(int)(this.currentSeqNum % 3L)];
         int nextFragNum = im.nextFragNum(this.currentSeqNum);
         ClusterMessagesManager.theOne().replaceItem(new NAKHBI(this.memberID, this.multicastSessionId, this.currentSeqNum, nextFragNum));
      }

   }

   private void setInSync() {
      if (this.outOfSync) {
         this.outOfSync = false;
         ClusterMessagesManager.theOne().removeItem(new NAKHBI(this.memberID, this.multicastSessionId, 0L, 0));
      }

   }

   void setInSync(long currentSeqNum) {
      this.currentSeqNum = currentSeqNum;
      this.setInSync();
   }

   synchronized void shutdown() {
      if (this.outOfSync) {
         ClusterMessagesManager.theOne().removeItem(new NAKHBI(this.memberID, this.multicastSessionId, 0L, 0));
      }

      this.clear();
   }

   private void reportLostMessages(long numLost) {
      if (this.currentSeqNum > 0L) {
         ClusterMessagesManager.theOne().incrementMulticastMessagesLostCount(numLost);
         if (ClusterService.getClusterServiceInternal().isUnicastMessagingModeEnabled()) {
            ClusterExtensionLogger.logLostUnicastMessages(numLost);
         } else {
            ClusterLogger.logLostMulticastMessages(numLost);
         }
      }

   }

   synchronized void clear() {
      this.cache = new IncomingMessage[3];

      for(int i = 0; i < 3; ++i) {
         this.cache[i] = new IncomingMessage();
      }

      this.currentSeqNum = 0L;
      this.recoveryInProgress = false;
      this.outOfSync = false;
      this.groupMessageStatistics.clear();
   }

   void dumpDiagnosticImageData(XMLStreamWriter xsw) throws XMLStreamException, IOException {
      xsw.writeStartElement("MulticastReceiver");
      xsw.writeAttribute("MemberID", this.memberID.toString());
      xsw.writeAttribute("SenderNum", String.valueOf(this.multicastSessionId));
      Iterator var2 = this.groupMessageStatistics.values().iterator();

      while(var2.hasNext()) {
         GroupMessageStat stat = (GroupMessageStat)var2.next();
         stat.dumpDiagnosticImageData(xsw);
      }

      xsw.writeEndElement();
   }

   private static class GroupMessageStat {
      private String name;
      private AtomicLong totalScheduled;
      private AtomicLong totalExecutionTimeNanos;
      private AtomicLong totalExecuted;
      private long minExecutionTimeNanos = Long.MAX_VALUE;
      private long maxExecutionTimeNanos = -1L;
      private long lastExecutionTimeNanos = -1L;

      public GroupMessageStat(String name) {
         this.name = name;
         this.totalScheduled = new AtomicLong();
         this.totalExecutionTimeNanos = new AtomicLong();
         this.totalExecuted = new AtomicLong();
      }

      public String getName() {
         return this.name;
      }

      public long getTotalScheduled() {
         return this.totalScheduled.get();
      }

      public long getTotalExecutionTimeNanos() {
         return this.totalExecutionTimeNanos.get();
      }

      public long getTotalExecuted() {
         return this.totalExecuted.get();
      }

      public double getAverageExecutionTimeNanos() {
         long tExecuted = this.getTotalExecuted();
         long tElapsedNanos = this.getTotalExecutionTimeNanos();
         return tExecuted != 0L ? (double)tElapsedNanos / (double)tExecuted : 0.0;
      }

      public long getPendingCount() {
         return this.getTotalScheduled() - this.getTotalExecuted();
      }

      public void start() {
         this.totalScheduled.addAndGet(1L);
      }

      public void finish(long elapsedNanos) {
         this.totalExecutionTimeNanos.addAndGet(elapsedNanos);
         this.totalExecuted.addAndGet(1L);
         if (elapsedNanos < this.minExecutionTimeNanos) {
            this.minExecutionTimeNanos = elapsedNanos;
         }

         if (elapsedNanos > this.maxExecutionTimeNanos) {
            this.maxExecutionTimeNanos = elapsedNanos;
         }

         this.lastExecutionTimeNanos = elapsedNanos;
      }

      void dumpDiagnosticImageData(XMLStreamWriter xsw) throws XMLStreamException, IOException {
         xsw.writeStartElement("GroupMessageStat");
         xsw.writeAttribute("Name", this.name);
         xsw.writeAttribute("TotalScheduled", String.valueOf(this.totalScheduled));
         xsw.writeAttribute("TotalExecuted", String.valueOf(this.totalExecuted));
         xsw.writeAttribute("AverageExecutionTimeNanos", String.valueOf(this.getAverageExecutionTimeNanos()));
         xsw.writeAttribute("MinExecutionTimeNanos", String.valueOf(this.minExecutionTimeNanos));
         xsw.writeAttribute("MaxExecutionTimeNanos", String.valueOf(this.maxExecutionTimeNanos));
         xsw.writeAttribute("LastExecutionTimeNanos", String.valueOf(this.lastExecutionTimeNanos));
         xsw.writeEndElement();
      }
   }

   private class IncomingMessage {
      private static final int INVALID_SEQNUM = -1;
      private long currentSeqNum = -1L;
      private int numFragments;
      private long numFragmentsReceived;
      private byte[] serializedMessage;
      private BitSet fragmentsReceived;

      IncomingMessage() {
      }

      void processFragment(long seqNum, int fragNum, int size, int offset, byte[] payload) {
         if (this.currentSeqNum != seqNum) {
            this.currentSeqNum = seqNum;
            this.numFragments = -1;
            this.numFragmentsReceived = 0L;
            this.serializedMessage = new byte[size];
            this.fragmentsReceived = new BitSet();
         }

         if (!this.fragmentsReceived.get(fragNum)) {
            System.arraycopy(payload, 0, this.serializedMessage, offset, payload.length);
            this.fragmentsReceived.set(fragNum);
            ++this.numFragmentsReceived;
            if (offset + payload.length >= size) {
               this.numFragments = fragNum + 1;
            }
         }

      }

      byte[] getMessage(long seqNum) {
         return seqNum == this.currentSeqNum && this.numFragmentsReceived == (long)this.numFragments ? this.serializedMessage : null;
      }

      int nextFragNum(long seqNum) {
         if (seqNum != this.currentSeqNum) {
            return 0;
         } else {
            int i;
            for(i = 0; i < this.fragmentsReceived.length() && this.fragmentsReceived.get(i); ++i) {
            }

            return i;
         }
      }
   }
}

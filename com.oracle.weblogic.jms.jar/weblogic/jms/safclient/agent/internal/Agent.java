package weblogic.jms.safclient.agent.internal;

import java.io.File;
import java.util.HashMap;
import javax.jms.JMSException;
import weblogic.jms.safclient.MessageMigrator;
import weblogic.jms.safclient.agent.AgentManager;
import weblogic.jms.safclient.store.StoreUtils;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaPolicy;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class Agent {
   public static final String AGENT_WM_PREFIX = "client.SAF.";
   public static final String NORMAL_WM_NAME = ".System";
   public static final String LIMITED_WM_NAME = ".Limited";
   private static final String ASYNC_WM_NAME = ".AsyncPush";
   public static final int LIMITED_WM_NUM_THREADS = 8;
   private static final String QUOTA_NAME = "client.SAF.Quota";
   public static final String AGENT_TM_PREFIX = "client.SAF.";
   public static final String DIRECT_TIMER_EXT = ".direct";
   private String name;
   private File rootDirectory;
   private WorkManager workManager;
   private WorkManager limitedWorkManager;
   private WorkManager asyncPushWorkManager;
   private String limitedTimerManagerName;
   private String directTimerManagerName;
   private long bytesMaximum = -1L;
   private int messagesMaximum = -1;
   private long messageBufferSize = -1L;
   private int maximumMessageSize = Integer.MAX_VALUE;
   private long defaultRetryDelayBase = 20000L;
   private long defaultRetryDelayMaximum = 180000L;
   private double defaultRetryDelayMultiplier = 1.0;
   private int windowSize = 10;
   private boolean loggingEnabled = true;
   private int windowInterval = 0;
   private HashMap destinations = new HashMap();
   private Kernel kernel;
   private Quota quota;

   public Agent(String paramName, File paramRootDirectory) {
      this.name = paramName;
      this.rootDirectory = paramRootDirectory;
   }

   public String getName() {
      return this.name;
   }

   public void setBytesMaximum(long paramBytesMaximum) {
      this.bytesMaximum = paramBytesMaximum;
   }

   public void setMessagesMaximum(long paramMessagesMaximum) {
      this.messagesMaximum = (int)paramMessagesMaximum;
   }

   public void setMessageBufferSize(long paramMessagesBufferSize) {
      this.messageBufferSize = paramMessagesBufferSize;
   }

   public void setMaximumMessageSize(int paramMaximumMessageSize) {
      this.maximumMessageSize = paramMaximumMessageSize;
   }

   public WorkManager getAsyncPushWorkManager() {
      return this.asyncPushWorkManager;
   }

   public long getDefaultRetryDelayBase() {
      return this.defaultRetryDelayBase;
   }

   public void setDefaultRetryDelayBase(long defaultRetryDelayBase) {
      this.defaultRetryDelayBase = defaultRetryDelayBase;
   }

   public long getDefaultRetryDelayMaximum() {
      return this.defaultRetryDelayMaximum;
   }

   public void setDefaultRetryDelayMaximum(long defaultRetryDelayMaximum) {
      this.defaultRetryDelayMaximum = defaultRetryDelayMaximum;
   }

   public double getDefaultRetryDelayMultiplier() {
      return this.defaultRetryDelayMultiplier;
   }

   public void setDefaultRetryDelayMultiplier(double defaultRetryDelayMultiplier) {
      this.defaultRetryDelayMultiplier = defaultRetryDelayMultiplier;
   }

   public void setLoggingEnabled(boolean paramLoggingEnabled) {
      this.loggingEnabled = paramLoggingEnabled;
   }

   public boolean isLoggingEnabled() {
      return this.loggingEnabled;
   }

   public int getWindowSize() {
      return this.windowSize;
   }

   public void setWindowSize(int paramWindowSize) {
      this.windowSize = paramWindowSize;
   }

   public int getWindowInterval() {
      return this.windowInterval;
   }

   public void setWindowInterval(int paramWindowInterval) {
      this.windowInterval = paramWindowInterval;
   }

   public void initialize() throws JMSException {
      File pagingDirectoryFile = new File(this.rootDirectory, "paging");
      if (!pagingDirectoryFile.exists() && !pagingDirectoryFile.mkdirs()) {
         throw new JMSException("Unable to create paging directory " + pagingDirectoryFile.getAbsolutePath());
      } else if (!pagingDirectoryFile.isDirectory()) {
         throw new JMSException("The file " + pagingDirectoryFile.getAbsolutePath() + " must be a directory, it will be used for the paging store");
      } else {
         PersistentStoreXA pStore = StoreUtils.getStore(this.rootDirectory);
         if (pStore == null) {
            throw new JMSException("Could not find default store");
         } else {
            HashMap props = new HashMap(5);
            WorkManagerFactory workManagerFactory = WorkManagerFactory.getInstance();
            if (MessageMigrator.revertBugFix) {
               this.workManager = workManagerFactory.findOrCreate("client.SAF." + this.name + ".System", 100, 1, -1);
               this.limitedWorkManager = workManagerFactory.findOrCreate("client.SAF." + this.name + ".Limited", 1, 8);
               this.asyncPushWorkManager = WorkManagerFactory.getInstance().findOrCreate("client.SAF." + this.name + ".AsyncPush", 100, 1, -1);
            } else {
               String seq = AgentManager.getManagerSequence();
               this.workManager = workManagerFactory.findOrCreate("client.SAF." + this.name + seq + ".System", 100, 1, -1);
               this.limitedWorkManager = workManagerFactory.findOrCreate("client.SAF." + this.name + seq + ".Limited", 1, 8);
               this.asyncPushWorkManager = WorkManagerFactory.getInstance().findOrCreate("client.SAF." + this.name + seq + ".AsyncPush", 100, 1, -1);
               this.limitedTimerManagerName = "client.SAF." + this.name + seq;
               this.directTimerManagerName = "client.SAF." + this.name + seq + ".direct";
               props.put("LimitedTimerManagerName", this.limitedTimerManagerName);
               props.put("DirectTimerManagerName", this.directTimerManagerName);
            }

            props.put("WorkManager", this.workManager);
            props.put("LimitedWorkManager", this.limitedWorkManager);
            props.put("PagingDirectory", pagingDirectoryFile.getAbsolutePath());

            try {
               this.kernel = new KernelImpl(this.name, props);
               this.kernel.setProperty("Store", pStore);
               this.kernel.open();
            } catch (KernelException var8) {
               throw new weblogic.jms.common.JMSException(var8);
            }

            try {
               this.kernel.setProperty("MessageBufferSize", new Long(this.messageBufferSize < 0L ? Long.MAX_VALUE : this.messageBufferSize));
               this.kernel.setProperty("MaximumMessageSize", new Integer(this.maximumMessageSize < 0 ? Integer.MAX_VALUE : this.maximumMessageSize));
            } catch (KernelException var7) {
               throw new weblogic.jms.common.JMSException(var7);
            }

            try {
               this.quota = this.kernel.createQuota("client.SAF.Quota");
            } catch (KernelException var6) {
               throw new weblogic.jms.common.JMSException(var6);
            }

            this.quota.setPolicy(QuotaPolicy.FIFO);
            this.quota.setBytesMaximum(this.bytesMaximum < 0L ? Long.MAX_VALUE : this.bytesMaximum);
            this.quota.setMessagesMaximum(this.messagesMaximum < 0 ? Integer.MAX_VALUE : this.messagesMaximum);
         }
      }
   }

   public PersistentStoreXA getPersistentStore() {
      return (PersistentStoreXA)this.kernel.getProperty("Store");
   }

   public Queue addConfiguredDestination(String name) throws weblogic.jms.common.JMSException {
      Queue queue = null;
      HashMap properties = new HashMap();
      properties.put("Durable", new Boolean(true));
      properties.put("Quota", this.quota);
      properties.put("MaximumMessageSize", new Integer(this.maximumMessageSize < 0 ? Integer.MAX_VALUE : this.maximumMessageSize));
      boolean var15 = false;

      try {
         var15 = true;
         queue = this.kernel.findQueue(name);
         if (queue == null) {
            try {
               queue = this.kernel.createQueue(name, properties);
            } catch (KernelException var20) {
               throw new weblogic.jms.common.JMSException(var20);
            }
         } else {
            try {
               queue.setProperties(properties);
            } catch (KernelException var19) {
               throw new weblogic.jms.common.JMSException(var19);
            }
         }

         try {
            queue.resume(16384);
            var15 = false;
         } catch (KernelException var18) {
            queue = null;
            throw new weblogic.jms.common.JMSException(var18);
         }
      } finally {
         if (var15) {
            if (queue != null) {
               synchronized(this.destinations) {
                  this.destinations.put(name, queue);
               }
            }

         }
      }

      if (queue != null) {
         synchronized(this.destinations) {
            this.destinations.put(name, queue);
         }
      }

      return queue;
   }

   public synchronized void shutdown() throws JMSException {
      try {
         if (this.kernel != null) {
            this.kernel.close();
         }

      } catch (KernelException var2) {
         throw new weblogic.jms.common.JMSException(var2);
      }
   }
}

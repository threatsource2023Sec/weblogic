package weblogic.socket;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import weblogic.kernel.ExecuteThread;
import weblogic.kernel.ExecuteThreadManager;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.KernelMBean;
import weblogic.rjvm.HeartbeatMonitor;
import weblogic.socket.internal.SocketEnvironment;
import weblogic.socket.utils.DynaQueue;
import weblogic.socket.utils.QueueFullException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.concurrent.Latch;
import weblogic.work.WorkManagerFactory;

final class JavaSocketMuxer extends ServerSocketMuxer {
   private static final boolean ASSERT = false;
   private static final int QUEUE_BLOCK_SIZE = 25;
   private static final int SOCKET_WAIT_TIMEOUT = 2000;
   private static final String CLIENT_SOCKET_READERS_QUEUE_NAME = "weblogic.JavaSocketReaders";
   private final Latch warningLock = new Latch();
   private final DynaQueue sockQueue = new DynaQueue("SockMuxQ", 25);
   private int numSocketReaders = 0;
   private int maxSocketReaders = -1;
   private int curSoTimeoutMillis = -1;
   private ExecuteThreadManager socketReaderQueue = null;
   private ExecuteThreadManager clientExecuteQueue;
   private int numClientSocketReaders = 0;
   private static final int MIN_CLIENT_EXECUTE_THREAD_COUNT = 0;
   private static final int MAX_CLIENT_EXECUTE_THREAD_COUNT = 15;
   private static final boolean jsse = SocketEnvironment.getSocketEnvironment().isJSSE();
   private static final int MAX_SLEEP_SUM = 1000;
   private static final int SLEEP_MULTIPLE = 100;
   private int prevNum = 0;
   private int curNum = 1;
   private KernelMBean config = Kernel.getConfig();

   protected JavaSocketMuxer() throws IOException {
      this.curSoTimeoutMillis = this.config.getSocketReaderTimeoutMaxMillis();
      this.init();
   }

   private void init() {
      ExecuteThreadManager queue = Kernel.getExecuteThreadManager("weblogic.socket.Muxer");
      if (queue != null && queue.getName().equalsIgnoreCase("weblogic.socket.Muxer")) {
         this.socketReaderQueue = queue;
         this.maxSocketReaders = queue.getExecuteThreadCount();
         SocketLogger.logAllocSocketReaders(this.maxSocketReaders);

         for(int i = 0; i < this.maxSocketReaders; ++i) {
            Kernel.execute(new SocketReaderRequest(), "weblogic.socket.Muxer");
         }
      }

   }

   private int getMaxSocketReaders() {
      if (this.maxSocketReaders == -1) {
         int percent = this.config.getThreadPoolPercentSocketReaders();
         int rawNum = percent * this.config.getThreadPoolSize() / 100;
         this.maxSocketReaders = Math.max(2, rawNum);
      }

      return this.maxSocketReaders;
   }

   public void read(MuxableSocket ms) {
      if (this.initiateIO(ms.getSocketInfo())) {
         this.internalRead(ms, false);
      }
   }

   public void read(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();
         this.read(ms);
      }

   }

   private void internalRead(MuxableSocket ms, Boolean previousReadTimeout) {
      try {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("internalRead for: " + ms.getSocketInfo());
         }

         if (ms instanceof ClientSSLFilterImpl && jsse) {
            if (previousReadTimeout) {
               int sleepTime = this.calculateSleepTime() * 100;

               try {
                  Thread.sleep(0L, sleepTime);
               } catch (InterruptedException var5) {
               }
            } else {
               this.resetSleepTime();
            }
         }

         this.sockQueue.put(ms);
      } catch (QueueFullException var6) {
         SocketLogger.logSocketQueueFull(var6);
         this.closeSocket(ms);
      }

   }

   private int calculateSleepTime() {
      if (this.curNum >= 1000) {
         return 1000;
      } else {
         this.curNum += this.prevNum;
         this.prevNum = this.curNum - this.prevNum;
         return this.curNum >= 1000 ? 1000 : this.curNum;
      }
   }

   private void resetSleepTime() {
      this.prevNum = 0;
      this.curNum = 1;
   }

   protected void handleReadTimeout(MuxableSocket ms) {
      this.internalRead(ms, true);
   }

   protected void readCompleted(MuxableSocket ms) {
      this.completeIO(ms, ms.getSocketInfo());
   }

   public void register(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();
         ms.setSocketInfo(new SocketInfo(ms));
      }

      synchronized(this.sockets) {
         super.register(muxableSockets);
         if (this.socketReaderQueue == null) {
            int numSockets = this.getNumSockets();

            while(numSockets > this.numSocketReaders + this.numClientSocketReaders) {
               if (this.numSocketReaders < this.getMaxSocketReaders()) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new SocketReaderRequest());
                  ++this.numSocketReaders;
                  if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                     SocketLogger.logDebug("Starting socket reader: '" + this.numSocketReaders + "', sockets: '" + numSockets + "'");
                  }
               } else {
                  if (Kernel.isServer() || !this.createClientThread()) {
                     if (this.warningLock.tryLock()) {
                        SocketLogger.logSocketConfig(numSockets, this.getMaxSocketReaders());
                     }
                     break;
                  }

                  Kernel.execute(new SocketReaderRequest(), "weblogic.JavaSocketReaders");
               }
            }

         }
      }
   }

   public void register(MuxableSocket ms) throws IOException {
      ms.setSocketInfo(new SocketInfo(ms));
      synchronized(this.sockets) {
         super.register(ms);
         if (this.socketReaderQueue == null) {
            int numSockets = this.getNumSockets();
            if (numSockets > this.numSocketReaders + this.numClientSocketReaders) {
               if (this.numSocketReaders < this.getMaxSocketReaders()) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new SocketReaderRequest());
                  ++this.numSocketReaders;
                  if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                     SocketLogger.logDebug("Starting socket reader: '" + this.numSocketReaders + "', sockets: '" + numSockets + "'");
                  }
               } else if (!Kernel.isServer() && this.createClientThread()) {
                  Kernel.execute(new SocketReaderRequest(), "weblogic.JavaSocketReaders");
               } else if (this.warningLock.tryLock()) {
                  SocketLogger.logSocketConfig(numSockets, this.getMaxSocketReaders());
               }
            }

         }
      }
   }

   private boolean createClientThread() {
      if (this.numClientSocketReaders == 15) {
         return false;
      } else {
         if (this.clientExecuteQueue == null) {
            this.createClientExecuteQueue();
         }

         if (++this.numClientSocketReaders > this.clientExecuteQueue.getExecuteThreadCount()) {
            this.clientExecuteQueue.setThreadCount(this.numClientSocketReaders);
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
               SocketLogger.logDebug("Created thread in extra client execute queue, total number of extra client threads: " + this.numClientSocketReaders);
            }
         }

         return true;
      }
   }

   private void createClientExecuteQueue() {
      Kernel.addExecuteQueue("weblogic.JavaSocketReaders", 0, 0, 15);
      this.clientExecuteQueue = Kernel.getExecuteThreadManager("weblogic.JavaSocketReaders");
   }

   private boolean shouldBreakProcessSockets(boolean clientExecuteThread) {
      if (this.socketReaderQueue != null) {
         return false;
      } else {
         synchronized(this.sockets) {
            if (this.numSocketReaders + this.numClientSocketReaders > this.getNumSockets()) {
               if (clientExecuteThread) {
                  --this.numClientSocketReaders;
               } else {
                  --this.numSocketReaders;
               }

               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebug("Decrementing socket reader: " + this.numSocketReaders + ", client socket reader: " + this.numClientSocketReaders + ", sockets: " + this.getNumSockets());
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   private boolean isClientExecuteThread() {
      return !Kernel.isServer() && ((ExecuteThread)Thread.currentThread()).getExecuteThreadManager().getName().equalsIgnoreCase("weblogic.JavaSocketReaders");
   }

   protected void processSockets() {
      boolean clientExecuteThread = this.isClientExecuteThread();

      while(!this.shouldBreakProcessSockets(clientExecuteThread)) {
         MuxableSocket ms = null;
         SocketInfo info = null;

         try {
            for(ms = (MuxableSocket)this.sockQueue.get(); ms == null; ms = (MuxableSocket)this.sockQueue.getW(2000)) {
               if (this.shouldBreakProcessSockets(clientExecuteThread)) {
                  return;
               }
            }

            info = ms.getSocketInfo();
            ms.setSoTimeout(this.getSoTimeout());
            this.readReadySocket(ms, info, (long)this.getSoTimeout());
         } catch (ThreadDeath var5) {
            if (Kernel.isServer()) {
               if (!Kernel.isIntentionalShutdown()) {
                  SocketLogger.logThreadDeath(var5);
               }

               throw var5;
            }

            if (ms != null && !ms.getSocketInfo().isCloseOnly()) {
               this.internalRead(ms, false);
            }
         } catch (Throwable var6) {
            this.deliverHasException(info.getMuxableSocket(), var6);
         }
      }

   }

   private int getSoTimeout() {
      return this.curSoTimeoutMillis;
   }

   private void updateSoTimeout() {
      int nsockets = this.getNumSockets();
      int nreaders = this.numSocketReaders;
      int minTimeoutMillis = this.config.getSocketReaderTimeoutMinMillis();
      int maxTimeoutMillis = this.config.getSocketReaderTimeoutMaxMillis();
      if (nreaders != 0 && nsockets != 0) {
         int period = HeartbeatMonitor.periodLengthMillis();
         this.curSoTimeoutMillis = period * nreaders / nsockets;
         this.curSoTimeoutMillis = Math.min(this.curSoTimeoutMillis, maxTimeoutMillis);
         this.curSoTimeoutMillis = Math.max(this.curSoTimeoutMillis, minTimeoutMillis);
      } else {
         this.curSoTimeoutMillis = maxTimeoutMillis;
      }

   }

   protected TimerListener createTimeoutTrigger() {
      return new JavaTimerListenerImpl();
   }

   protected class JavaTimerListenerImpl extends SocketMuxer.TimerListenerImpl {
      protected JavaTimerListenerImpl() {
         super();
      }

      public void timerExpired(Timer timer) {
         super.timerExpired(timer);
         JavaSocketMuxer.this.updateSoTimeout();
      }
   }
}

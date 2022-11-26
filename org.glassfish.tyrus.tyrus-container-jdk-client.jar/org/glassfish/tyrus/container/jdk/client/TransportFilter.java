package org.glassfish.tyrus.container.jdk.client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.ThreadPoolConfig;
import org.glassfish.tyrus.spi.CompletionHandler;

class TransportFilter extends Filter {
   private static final Logger LOGGER = Logger.getLogger(TransportFilter.class.getName());
   private static final int DEFAULT_CONNECTION_CLOSE_WAIT = 30;
   private static final AtomicInteger openedConnections = new AtomicInteger(0);
   private static final ScheduledExecutorService connectionCloseScheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
      public Thread newThread(Runnable r) {
         Thread thread = new Thread(r);
         thread.setName("tyrus-jdk-container-idle-timeout");
         thread.setDaemon(true);
         return thread;
      }
   });
   private static volatile AsynchronousChannelGroup channelGroup;
   private static volatile ScheduledFuture closeWaitTask;
   private static volatile ThreadPoolConfig currentThreadPoolConfig;
   private static volatile Integer currentContainerIdleTimeout;
   private final int inputBufferSize;
   private final ThreadPoolConfig threadPoolConfig;
   private final Integer containerIdleTimeout;
   private volatile AsynchronousSocketChannel socketChannel;

   TransportFilter(int inputBufferSize, ThreadPoolConfig threadPoolConfig, Integer containerIdleTimeout) {
      super((Filter)null);
      this.inputBufferSize = inputBufferSize;
      this.threadPoolConfig = threadPoolConfig;
      this.containerIdleTimeout = containerIdleTimeout;
   }

   void write(ByteBuffer data, final CompletionHandler completionHandler) {
      this.socketChannel.write(data, data, new java.nio.channels.CompletionHandler() {
         public void completed(Integer result, ByteBuffer buffer) {
            if (buffer.hasRemaining()) {
               TransportFilter.this.write(buffer, completionHandler);
            } else {
               completionHandler.completed(buffer);
            }
         }

         public void failed(Throwable exc, ByteBuffer buffer) {
            completionHandler.failed(exc);
         }
      });
   }

   synchronized void close() {
      if (this.socketChannel.isOpen()) {
         try {
            this.socketChannel.close();
         } catch (IOException var3) {
            LOGGER.log(Level.INFO, "Could not close a connection", var3);
         }

         Class var1 = TransportFilter.class;
         synchronized(TransportFilter.class) {
            openedConnections.decrementAndGet();
            if (openedConnections.get() == 0 && channelGroup != null) {
               this.scheduleClose();
            }
         }

         this.upstreamFilter = null;
      }
   }

   void startSsl() {
      this.onSslHandshakeCompleted();
   }

   public void handleConnect(SocketAddress serverAddress, Filter upstreamFilter) {
      this.upstreamFilter = upstreamFilter;

      try {
         Class var3 = TransportFilter.class;
         synchronized(TransportFilter.class) {
            this.updateThreadPoolConfig();
            this.initializeChannelGroup();
            this.socketChannel = AsynchronousSocketChannel.open(channelGroup);
            openedConnections.incrementAndGet();
         }
      } catch (IOException var7) {
         this.onError(var7);
         return;
      }

      try {
         this.socketChannel.connect(serverAddress, (Object)null, new java.nio.channels.CompletionHandler() {
            public void completed(Void result, Void nothing) {
               ByteBuffer inputBuffer = ByteBuffer.allocate(TransportFilter.this.inputBufferSize);
               TransportFilter.this.onConnect();
               TransportFilter.this._read(inputBuffer);
            }

            public void failed(Throwable exc, Void nothing) {
               TransportFilter.this.onError(exc);

               try {
                  TransportFilter.this.socketChannel.close();
               } catch (IOException var4) {
                  TransportFilter.LOGGER.log(Level.FINE, "Could not close connection", exc.getMessage());
               }

            }
         });
      } catch (UnsupportedAddressTypeException | UnresolvedAddressException var5) {
         this.onError(var5);
      }

   }

   private void updateThreadPoolConfig() {
      if (openedConnections.get() == 0) {
         Integer closeWait = this.containerIdleTimeout == null ? 30 : this.containerIdleTimeout;
         if (!this.threadPoolConfig.equals(currentThreadPoolConfig) || !closeWait.equals(currentContainerIdleTimeout)) {
            currentThreadPoolConfig = this.threadPoolConfig;
            currentContainerIdleTimeout = closeWait;
            if (channelGroup == null) {
               return;
            }

            closeWaitTask.cancel(true);
            closeWaitTask = null;
            channelGroup.shutdown();
            channelGroup = null;
         }

      }
   }

   private void initializeChannelGroup() throws IOException {
      if (closeWaitTask != null) {
         closeWaitTask.cancel(true);
         closeWaitTask = null;
      }

      if (channelGroup == null) {
         ThreadFactory threadFactory = this.threadPoolConfig.getThreadFactory();
         if (threadFactory == null) {
            threadFactory = new TransportThreadFactory(this.threadPoolConfig);
         }

         QueuingExecutor executor;
         if (this.threadPoolConfig.getQueue() != null) {
            executor = new QueuingExecutor(this.threadPoolConfig.getCorePoolSize(), this.threadPoolConfig.getMaxPoolSize(), this.threadPoolConfig.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS, this.threadPoolConfig.getQueue(), false, (ThreadFactory)threadFactory);
         } else {
            int taskQueueLimit = this.threadPoolConfig.getQueueLimit();
            if (taskQueueLimit == -1) {
               taskQueueLimit = Integer.MAX_VALUE;
            }

            executor = new QueuingExecutor(this.threadPoolConfig.getCorePoolSize(), this.threadPoolConfig.getMaxPoolSize(), this.threadPoolConfig.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS, new LinkedBlockingDeque(taskQueueLimit), true, (ThreadFactory)threadFactory);
         }

         channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executor, this.threadPoolConfig.getCorePoolSize());
      }

   }

   private void _read(final ByteBuffer inputBuffer) {
      if (this.socketChannel.isOpen()) {
         this.socketChannel.read(inputBuffer, (Object)null, new java.nio.channels.CompletionHandler() {
            public void completed(Integer bytesRead, Void result) {
               if (bytesRead == -1) {
                  Filter upstreamFilter = TransportFilter.this.upstreamFilter;
                  if (upstreamFilter != null) {
                     TransportFilter.this.close();
                     upstreamFilter.onConnectionClosed();
                  }

               } else {
                  inputBuffer.flip();
                  TransportFilter.this.onRead(inputBuffer);
                  inputBuffer.compact();
                  TransportFilter.this._read(inputBuffer);
               }
            }

            public void failed(Throwable exc, Void result) {
               if (!(exc instanceof AsynchronousCloseException)) {
                  TransportFilter.this.onError(exc);
               }
            }
         });
      }
   }

   private void scheduleClose() {
      closeWaitTask = connectionCloseScheduler.schedule(new Runnable() {
         public void run() {
            Class var1 = TransportFilter.class;
            synchronized(TransportFilter.class) {
               if (TransportFilter.closeWaitTask != null) {
                  TransportFilter.channelGroup.shutdown();
                  TransportFilter.channelGroup = null;
                  TransportFilter.closeWaitTask = null;
               }
            }
         }
      }, (long)currentContainerIdleTimeout, TimeUnit.SECONDS);
   }

   private static class QueuingExecutor extends ThreadPoolExecutor {
      private final Queue taskQueue;
      private final boolean threadSafeQueue;

      QueuingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, Queue taskQueue, boolean threadSafeQueue, ThreadFactory threadFactory) {
         super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new HandOffQueue(taskQueue, threadSafeQueue), threadFactory);
         this.taskQueue = taskQueue;
         this.threadSafeQueue = threadSafeQueue;
      }

      public void execute(Runnable task) {
         try {
            super.execute(task);
         } catch (RejectedExecutionException var8) {
            RejectedExecutionException e = var8;
            if (this.isShutdown()) {
               throw new RejectedExecutionException("The thread pool executor has been shut down", var8);
            }

            if (this.threadSafeQueue) {
               if (!this.taskQueue.offer(task)) {
                  throw new RejectedExecutionException("A limit of Tyrus client thread pool queue has been reached.", var8);
               }
            } else {
               synchronized(this.taskQueue) {
                  if (!this.taskQueue.offer(task)) {
                     throw new RejectedExecutionException("A limit of Tyrus client thread pool queue has been reached.", e);
                  }
               }
            }

            if (this.getActiveCount() < this.getMaximumPoolSize()) {
               Runnable dequeuedTask;
               if (this.threadSafeQueue) {
                  dequeuedTask = (Runnable)this.taskQueue.poll();
               } else {
                  synchronized(this.taskQueue) {
                     dequeuedTask = (Runnable)this.taskQueue.poll();
                  }
               }

               if (dequeuedTask != null) {
                  this.execute(dequeuedTask);
               }
            }
         }

      }

      private static class HandOffQueue extends SynchronousQueue {
         private static final long serialVersionUID = -1607064661828834847L;
         private final Queue taskQueue;
         private final boolean threadSafeQueue;

         private HandOffQueue(Queue taskQueue, boolean threadSafeQueue) {
            this.taskQueue = taskQueue;
            this.threadSafeQueue = threadSafeQueue;
         }

         public Runnable take() throws InterruptedException {
            Runnable task;
            if (this.threadSafeQueue) {
               task = (Runnable)this.taskQueue.poll();
            } else {
               synchronized(this.taskQueue) {
                  task = (Runnable)this.taskQueue.poll();
               }
            }

            return task != null ? task : (Runnable)super.take();
         }

         public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
            Runnable task;
            if (this.threadSafeQueue) {
               task = (Runnable)this.taskQueue.poll();
            } else {
               synchronized(this.taskQueue) {
                  task = (Runnable)this.taskQueue.poll();
               }
            }

            return task != null ? task : (Runnable)super.poll(timeout, unit);
         }

         // $FF: synthetic method
         HandOffQueue(Queue x0, boolean x1, Object x2) {
            this(x0, x1);
         }
      }
   }

   private static class TransportThreadFactory implements ThreadFactory {
      private static final String THREAD_NAME_BASE = " tyrus-jdk-client-";
      private static final AtomicInteger threadCounter = new AtomicInteger(0);
      private final ThreadPoolConfig threadPoolConfig;

      TransportThreadFactory(ThreadPoolConfig threadPoolConfig) {
         this.threadPoolConfig = threadPoolConfig;
      }

      public Thread newThread(Runnable r) {
         final Thread thread = new Thread(r);
         thread.setName(" tyrus-jdk-client-" + threadCounter.incrementAndGet());
         thread.setPriority(this.threadPoolConfig.getPriority());
         thread.setDaemon(this.threadPoolConfig.isDaemon());

         try {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Void run() {
                  if (TransportThreadFactory.this.threadPoolConfig.getInitialClassLoader() == null) {
                     thread.setContextClassLoader(this.getClass().getClassLoader());
                  } else {
                     thread.setContextClassLoader(TransportThreadFactory.this.threadPoolConfig.getInitialClassLoader());
                  }

                  return null;
               }
            });
         } catch (Throwable var4) {
            TransportFilter.LOGGER.log(Level.WARNING, "Cannot set thread context class loader.", var4);
         }

         return thread;
      }
   }
}

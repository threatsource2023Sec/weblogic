package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOStrategy;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.StateHolder;

public final class SelectorRunner implements Runnable {
   private static final Logger LOGGER = Grizzly.logger(SelectorRunner.class);
   private static final String THREAD_MARKER = " SelectorRunner";
   private final NIOTransport transport;
   private final AtomicReference stateHolder;
   private final Queue pendingTasks;
   private Queue currentPostponedTasks;
   private final Queue evenPostponedTasks;
   private final Queue oddPostponedTasks;
   private volatile int dumbVolatile = 1;
   private Selector selector;
   private Thread selectorRunnerThread;
   private boolean isResume;
   private int lastSelectedKeysCount;
   private Set readyKeySet;
   private Iterator iterator;
   private SelectionKey key = null;
   private int keyReadyOps;
   private final AtomicBoolean selectorWakeupFlag = new AtomicBoolean();
   private final AtomicInteger runnerThreadActivityCounter = new AtomicInteger();
   volatile boolean hasPendingTasks;
   private long lastSpinTimestamp;
   private int emptySpinCounter;
   private final Map spinnedSelectorsHistory = new WeakHashMap();

   public static SelectorRunner create(NIOTransport transport) throws IOException {
      return new SelectorRunner(transport, Selectors.newSelector(transport.getSelectorProvider()));
   }

   private SelectorRunner(NIOTransport transport, Selector selector) {
      this.transport = transport;
      this.selector = selector;
      this.stateHolder = new AtomicReference(Transport.State.STOPPED);
      this.pendingTasks = new ConcurrentLinkedQueue();
      this.evenPostponedTasks = new ArrayDeque();
      this.oddPostponedTasks = new ArrayDeque();
      this.currentPostponedTasks = this.evenPostponedTasks;
   }

   void addPendingTask(SelectorHandlerTask task) {
      this.pendingTasks.offer(task);
      this.hasPendingTasks = true;
      this.wakeupSelector();
   }

   private void wakeupSelector() {
      Selector localSelector = this.getSelector();
      if (localSelector != null && this.selectorWakeupFlag.compareAndSet(false, true)) {
         try {
            localSelector.wakeup();
         } catch (Exception var3) {
            LOGGER.log(Level.FINE, "Error during selector wakeup", var3);
         }
      }

   }

   public NIOTransport getTransport() {
      return this.transport;
   }

   public Selector getSelector() {
      return this.dumbVolatile != 0 ? this.selector : null;
   }

   void setSelector(Selector selector) {
      this.selector = selector;
      ++this.dumbVolatile;
   }

   private void setRunnerThread(Thread runnerThread) {
      this.selectorRunnerThread = runnerThread;
      ++this.dumbVolatile;
   }

   public Thread getRunnerThread() {
      return this.dumbVolatile != 0 ? this.selectorRunnerThread : null;
   }

   public Transport.State getState() {
      return (Transport.State)this.stateHolder.get();
   }

   public void postpone() {
      assert this.selectorRunnerThread != null;

      this.removeThreadNameMarker(this.selectorRunnerThread);
      Threads.setService(false);
      this.runnerThreadActivityCounter.compareAndSet(1, 0);
      this.selectorRunnerThread = null;
      this.isResume = true;
      ++this.dumbVolatile;
   }

   public synchronized void start() {
      if (!this.stateHolder.compareAndSet(Transport.State.STOPPED, Transport.State.STARTING)) {
         LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SELECTOR_RUNNER_NOT_IN_STOPPED_STATE_EXCEPTION());
      } else {
         this.transport.getKernelThreadPool().execute(this);
      }
   }

   public synchronized void stop() {
      this.stateHolder.set(Transport.State.STOPPING);
      this.wakeupSelector();
      if (this.runnerThreadActivityCounter.compareAndSet(0, -1)) {
         this.shutdownSelector();
      }

   }

   private void shutdownSelector() {
      Selector localSelector = this.getSelector();
      if (localSelector != null) {
         try {
            SelectionKey[] keys = new SelectionKey[0];

            while(true) {
               try {
                  keys = (SelectionKey[])localSelector.keys().toArray(keys);
                  break;
               } catch (ConcurrentModificationException var17) {
               }
            }

            SelectionKey[] var3 = keys;
            int var4 = keys.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               SelectionKey selectionKey = var3[var5];
               Connection connection = this.transport.getSelectionKeyHandler().getConnectionForKey(selectionKey);
               connection.terminateSilently();
            }
         } catch (ClosedSelectorException var18) {
         } finally {
            try {
               localSelector.close();
            } catch (Exception var16) {
            }

         }
      }

      this.abortTasksInQueue(this.pendingTasks);
      this.abortTasksInQueue(this.evenPostponedTasks);
      this.abortTasksInQueue(this.oddPostponedTasks);
   }

   public void run() {
      if (this.runnerThreadActivityCounter.compareAndSet(0, 1)) {
         Thread currentThread = Thread.currentThread();

         try {
            if (!this.isResume) {
               if (!this.stateHolder.compareAndSet(Transport.State.STARTING, Transport.State.STARTED)) {
                  return;
               }

               this.addThreadNameMarker(currentThread);
            }

            this.setRunnerThread(currentThread);
            Threads.setService(true);
            StateHolder transportStateHolder = this.transport.getState();
            boolean isSkipping = false;

            while(!isSkipping && !this.isStop()) {
               if (transportStateHolder.getState() != Transport.State.PAUSED) {
                  isSkipping = !this.doSelect();
               } else {
                  try {
                     transportStateHolder.notifyWhenStateIsNotEqual(Transport.State.PAUSED, (CompletionHandler)null).get(5000L, TimeUnit.MILLISECONDS);
                  } catch (Exception var8) {
                  }
               }
            }

         } finally {
            this.runnerThreadActivityCounter.compareAndSet(1, 0);
            if (this.isStop()) {
               this.stateHolder.set(Transport.State.STOPPED);
               this.setRunnerThread((Thread)null);
               if (this.runnerThreadActivityCounter.compareAndSet(0, -1)) {
                  this.shutdownSelector();
               }
            }

            this.removeThreadNameMarker(currentThread);
            Threads.setService(false);
         }
      }
   }

   protected boolean doSelect() {
      SelectorHandler selectorHandler = this.transport.getSelectorHandler();

      try {
         if (this.isResume) {
            this.isResume = false;
            if (this.readyKeySet != null) {
               if (this.keyReadyOps != 0 && !this.iterateKeyEvents()) {
                  return false;
               }

               if (!this.iterateKeys()) {
                  return false;
               }

               this.readyKeySet.clear();
            }
         }

         this.lastSelectedKeysCount = 0;
         if (!selectorHandler.preSelect(this)) {
            return false;
         }

         this.readyKeySet = selectorHandler.select(this);
         this.selectorWakeupFlag.set(false);
         if (this.stateHolder.get() == Transport.State.STOPPING) {
            return true;
         }

         this.lastSelectedKeysCount = this.readyKeySet.size();
         if (this.lastSelectedKeysCount != 0) {
            this.iterator = this.readyKeySet.iterator();
            if (!this.iterateKeys()) {
               return false;
            }

            this.readyKeySet.clear();
         }

         this.readyKeySet = null;
         this.iterator = null;
         selectorHandler.postSelect(this);
      } catch (ClosedSelectorException var3) {
         if (this.isRunning() && selectorHandler.onSelectorClosed(this)) {
            return true;
         }

         this.dropConnectionDueToException(this.key, "Selector was unexpectedly closed", var3, Level.SEVERE, Level.FINE);
      } catch (Exception var4) {
         this.dropConnectionDueToException(this.key, "doSelect exception", var4, Level.SEVERE, Level.FINE);
      } catch (Throwable var5) {
         LOGGER.log(Level.SEVERE, "doSelect exception", var5);
         this.transport.notifyTransportError(var5);
      }

      return true;
   }

   private boolean iterateKeys() {
      Iterator it = this.iterator;

      while(it.hasNext()) {
         try {
            this.key = (SelectionKey)it.next();
            this.keyReadyOps = this.key.readyOps();
            if (!this.iterateKeyEvents()) {
               return false;
            }
         } catch (IOException var3) {
            this.keyReadyOps = 0;
            this.dropConnectionDueToException(this.key, "Unexpected IOException. Channel " + this.key.channel() + " will be closed.", var3, Level.WARNING, Level.FINE);
         } catch (CancelledKeyException var4) {
            this.keyReadyOps = 0;
            this.dropConnectionDueToException(this.key, "Unexpected CancelledKeyException. Channel " + this.key.channel() + " will be closed.", var4, Level.FINE, Level.FINE);
         }
      }

      return true;
   }

   private boolean iterateKeyEvents() throws IOException {
      SelectionKey keyLocal = this.key;
      SelectionKeyHandler selectionKeyHandler = this.transport.getSelectionKeyHandler();
      IOStrategy ioStrategy = this.transport.getIOStrategy();
      IOEvent[] ioEvents = selectionKeyHandler.getIOEvents(this.keyReadyOps);
      NIOConnection connection = selectionKeyHandler.getConnectionForKey(keyLocal);
      IOEvent[] var6 = ioEvents;
      int var7 = ioEvents.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         IOEvent ioEvent = var6[var8];
         NIOConnection.notifyIOEventReady(connection, ioEvent);
         int interest = ioEvent.getSelectionKeyInterest();
         this.keyReadyOps &= ~interest;
         if (selectionKeyHandler.onProcessInterest(keyLocal, interest) && !ioStrategy.executeIoEvent(connection, ioEvent)) {
            return false;
         }
      }

      return true;
   }

   public Queue getPendingTasks() {
      this.hasPendingTasks = false;
      return this.pendingTasks;
   }

   public Queue getPostponedTasks() {
      return this.currentPostponedTasks;
   }

   public Queue obtainPostponedTasks() {
      Queue tasksToReturn = this.currentPostponedTasks;
      this.currentPostponedTasks = this.currentPostponedTasks == this.evenPostponedTasks ? this.oddPostponedTasks : this.evenPostponedTasks;
      return tasksToReturn;
   }

   boolean isStop() {
      Transport.State state = (Transport.State)this.stateHolder.get();
      return state == Transport.State.STOPPED || state == Transport.State.STOPPING;
   }

   private boolean isRunning() {
      return this.stateHolder.get() == Transport.State.STARTED;
   }

   private void dropConnectionDueToException(SelectionKey key, String description, Exception e, Level runLogLevel, Level stoppedLogLevel) {
      if (this.isRunning()) {
         LOGGER.log(runLogLevel, description, e);
         if (key != null) {
            try {
               Connection connection = this.transport.getSelectionKeyHandler().getConnectionForKey(key);
               if (connection != null) {
                  connection.closeSilently();
               } else {
                  SelectableChannel channel = key.channel();
                  this.transport.getSelectionKeyHandler().cancel(key);
                  channel.close();
               }
            } catch (IOException var8) {
               LOGGER.log(Level.FINE, "IOException during cancelling key", var8);
            }
         }

         this.transport.notifyTransportError(e);
      } else {
         LOGGER.log(stoppedLogLevel, description, e);
      }

   }

   public int getLastSelectedKeysCount() {
      return this.lastSelectedKeysCount;
   }

   protected final void switchToNewSelector() throws IOException {
      Selector oldSelector = this.selector;
      Selector newSelector = Selectors.newSelector(this.transport.getSelectorProvider());
      Set keys = oldSelector.keys();
      SelectionKeyHandler selectionKeyHandler = this.transport.getSelectionKeyHandler();
      Iterator var5 = keys.iterator();

      while(var5.hasNext()) {
         SelectionKey selectionKey = (SelectionKey)var5.next();
         if (selectionKey.isValid()) {
            try {
               NIOConnection nioConnection = selectionKeyHandler.getConnectionForKey(selectionKey);
               SelectionKey newSelectionKey = selectionKey.channel().register(newSelector, selectionKey.interestOps(), selectionKey.attachment());
               nioConnection.onSelectionKeyUpdated(newSelectionKey);
            } catch (Exception var10) {
               LOGGER.log(Level.FINE, "Error switching channel to a new selector", var10);
            }
         }
      }

      this.setSelector(newSelector);

      try {
         oldSelector.close();
      } catch (Exception var9) {
      }

   }

   private void abortTasksInQueue(Queue taskQueue) {
      SelectorHandlerTask task;
      while((task = (SelectorHandlerTask)taskQueue.poll()) != null) {
         try {
            task.cancel();
         } catch (Exception var4) {
         }
      }

   }

   final void resetSpinCounter() {
      this.emptySpinCounter = 0;
   }

   final int incSpinCounter() {
      if (this.emptySpinCounter++ == 0) {
         this.lastSpinTimestamp = System.nanoTime();
      } else if (this.emptySpinCounter == 1000) {
         long deltatime = System.nanoTime() - this.lastSpinTimestamp;
         int contspinspersec = (int)(1000000000000L / deltatime);
         this.emptySpinCounter = 0;
         return contspinspersec;
      }

      return 0;
   }

   final SelectionKey checkIfSpinnedKey(SelectionKey key) {
      if (!key.isValid() && key.channel().isOpen() && this.spinnedSelectorsHistory.containsKey(key.selector())) {
         SelectionKey newKey = key.channel().keyFor(this.getSelector());
         newKey.attach(key.attachment());
         return newKey;
      } else {
         return key;
      }
   }

   final void workaroundSelectorSpin() throws IOException {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Workaround selector spin. selector={0}", this.getSelector());
      }

      this.spinnedSelectorsHistory.put(this.getSelector(), System.currentTimeMillis());
      this.switchToNewSelector();
   }

   final void checkSelectorSpin(boolean hasSelectedKeys, int spinRateThreshold) throws IOException {
      if (hasSelectedKeys) {
         this.resetSpinCounter();
      } else {
         long sr = (long)this.incSpinCounter();
         if (sr > (long)spinRateThreshold) {
            this.workaroundSelectorSpin();
         }
      }

   }

   private void addThreadNameMarker(Thread currentThread) {
      String name = currentThread.getName();
      if (!name.endsWith(" SelectorRunner")) {
         currentThread.setName(name + " SelectorRunner");
      }

   }

   private void removeThreadNameMarker(Thread currentThread) {
      String name = currentThread.getName();
      if (name.endsWith(" SelectorRunner")) {
         currentThread.setName(name.substring(0, name.length() - " SelectorRunner".length()));
      }

   }
}

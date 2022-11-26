package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.utils.Futures;

public class DefaultSelectorHandler implements SelectorHandler {
   private static final long DEFAULT_SELECT_TIMEOUT_MILLIS = 30000L;
   private static final Logger logger = Grizzly.logger(DefaultSelectorHandler.class);
   public static final boolean IS_WORKAROUND_SELECTOR_SPIN = Boolean.getBoolean(DefaultSelectorHandler.class.getName() + ".force-selector-spin-detection") || System.getProperty("os.name").equalsIgnoreCase("linux");
   protected final long selectTimeout;
   private static final int SPIN_RATE_THRESHOLD = 2000;

   public DefaultSelectorHandler() {
      this(30000L, TimeUnit.MILLISECONDS);
   }

   public DefaultSelectorHandler(long selectTimeout, TimeUnit timeunit) {
      this.selectTimeout = TimeUnit.MILLISECONDS.convert(selectTimeout, timeunit);
   }

   public long getSelectTimeout() {
      return this.selectTimeout;
   }

   public boolean preSelect(SelectorRunner selectorRunner) throws IOException {
      return this.processPendingTasks(selectorRunner);
   }

   public Set select(SelectorRunner selectorRunner) throws IOException {
      Selector selector = selectorRunner.getSelector();
      boolean hasPostponedTasks = !selectorRunner.getPostponedTasks().isEmpty();
      if (!hasPostponedTasks) {
         selector.select(this.selectTimeout);
      } else {
         selector.selectNow();
      }

      Set selectedKeys = selector.selectedKeys();
      if (IS_WORKAROUND_SELECTOR_SPIN) {
         selectorRunner.checkSelectorSpin(!selectedKeys.isEmpty() || hasPostponedTasks, 2000);
      }

      return selectedKeys;
   }

   public void postSelect(SelectorRunner selectorRunner) throws IOException {
   }

   public void registerKeyInterest(SelectorRunner selectorRunner, SelectionKey key, int interest) throws IOException {
      if (isSelectorRunnerThread(selectorRunner)) {
         registerKey0(key, interest);
      } else {
         selectorRunner.addPendingTask(new RegisterKeyTask(key, interest));
      }

   }

   private static void registerKey0(SelectionKey selectionKey, int interest) {
      if (selectionKey.isValid()) {
         int currentOps = selectionKey.interestOps();
         if ((currentOps & interest) != interest) {
            selectionKey.interestOps(currentOps | interest);
         }
      }

   }

   public void deregisterKeyInterest(SelectorRunner selectorRunner, SelectionKey key, int interest) throws IOException {
      if (key.isValid()) {
         int currentOps = key.interestOps();
         if ((currentOps & interest) != 0) {
            key.interestOps(currentOps & ~interest);
         }
      }

   }

   public void registerChannel(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment) throws IOException {
      FutureImpl future = SafeFutureImpl.create();
      this.registerChannelAsync(selectorRunner, channel, interest, attachment, Futures.toCompletionHandler(future));

      try {
         future.get(this.selectTimeout, TimeUnit.MILLISECONDS);
      } catch (Exception var7) {
         throw new IOException(var7.getMessage());
      }
   }

   public void registerChannelAsync(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment, CompletionHandler completionHandler) {
      if (isSelectorRunnerThread(selectorRunner)) {
         registerChannel0(selectorRunner, channel, interest, attachment, completionHandler);
      } else {
         this.addPendingTask(selectorRunner, new RegisterChannelOperation(channel, interest, attachment, completionHandler));
      }

   }

   public void deregisterChannel(SelectorRunner selectorRunner, SelectableChannel channel) throws IOException {
      FutureImpl future = SafeFutureImpl.create();
      this.deregisterChannelAsync(selectorRunner, channel, Futures.toCompletionHandler(future));

      try {
         future.get(this.selectTimeout, TimeUnit.MILLISECONDS);
      } catch (Exception var5) {
         throw new IOException(var5.getMessage());
      }
   }

   public void deregisterChannelAsync(SelectorRunner selectorRunner, SelectableChannel channel, CompletionHandler completionHandler) {
      if (isSelectorRunnerThread(selectorRunner)) {
         deregisterChannel0(selectorRunner, channel, completionHandler);
      } else {
         this.addPendingTask(selectorRunner, new DeregisterChannelOperation(channel, completionHandler));
      }

   }

   public void execute(SelectorRunner selectorRunner, SelectorHandler.Task task, CompletionHandler completionHandler) {
      if (isSelectorRunnerThread(selectorRunner)) {
         try {
            task.run();
            if (completionHandler != null) {
               completionHandler.completed(task);
            }
         } catch (Exception var5) {
            if (completionHandler != null) {
               completionHandler.failed(var5);
            }
         }
      } else {
         this.addPendingTask(selectorRunner, new RunnableTask(task, completionHandler));
      }

   }

   public void enque(SelectorRunner selectorRunner, SelectorHandler.Task task, CompletionHandler completionHandler) {
      if (isSelectorRunnerThread(selectorRunner)) {
         Queue postponedTasks = selectorRunner.getPostponedTasks();
         postponedTasks.offer(new RunnableTask(task, completionHandler));
      } else {
         this.addPendingTask(selectorRunner, new RunnableTask(task, completionHandler));
      }

   }

   private void addPendingTask(SelectorRunner selectorRunner, SelectorHandlerTask task) {
      if (selectorRunner == null) {
         task.cancel();
      } else {
         selectorRunner.addPendingTask(task);
         if (selectorRunner.isStop() && selectorRunner.getPendingTasks().remove(task)) {
            task.cancel();
         }

      }
   }

   private boolean processPendingTasks(SelectorRunner selectorRunner) throws IOException {
      return this.processPendingTaskQueue(selectorRunner, selectorRunner.obtainPostponedTasks()) && (!selectorRunner.hasPendingTasks || this.processPendingTaskQueue(selectorRunner, selectorRunner.getPendingTasks()));
   }

   private boolean processPendingTaskQueue(SelectorRunner selectorRunner, Queue selectorHandlerTasks) throws IOException {
      while(true) {
         SelectorHandlerTask selectorHandlerTask;
         if ((selectorHandlerTask = (SelectorHandlerTask)selectorHandlerTasks.poll()) != null) {
            if (selectorHandlerTask.run(selectorRunner)) {
               continue;
            }

            return false;
         }

         return true;
      }
   }

   private static void registerChannel0(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment, CompletionHandler completionHandler) {
      try {
         if (channel.isOpen()) {
            Selector selector = selectorRunner.getSelector();
            SelectionKey key = channel.keyFor(selector);
            if (key != null && !key.isValid()) {
               Queue postponedTasks = selectorRunner.getPostponedTasks();
               RegisterChannelOperation operation = new RegisterChannelOperation(channel, interest, attachment, completionHandler);
               postponedTasks.add(operation);
            } else {
               SelectionKey registeredSelectionKey = channel.register(selector, interest, attachment);
               selectorRunner.getTransport().getSelectionKeyHandler().onKeyRegistered(registeredSelectionKey);
               RegisterChannelResult result = new RegisterChannelResult(selectorRunner, registeredSelectionKey, channel);
               if (completionHandler != null) {
                  completionHandler.completed(result);
               }
            }
         } else {
            failChannelRegistration(completionHandler, new ClosedChannelException());
         }
      } catch (IOException var9) {
         failChannelRegistration(completionHandler, var9);
      }

   }

   private static void failChannelRegistration(CompletionHandler completionHandler, Throwable error) {
      if (completionHandler != null) {
         completionHandler.failed(error);
      }

   }

   private static void deregisterChannel0(SelectorRunner selectorRunner, SelectableChannel channel, CompletionHandler completionHandler) {
      try {
         Object error;
         if (channel.isOpen()) {
            Selector selector = selectorRunner.getSelector();
            SelectionKey key = channel.keyFor(selector);
            if (key != null) {
               selectorRunner.getTransport().getSelectionKeyHandler().cancel(key);
               selectorRunner.getTransport().getSelectionKeyHandler().onKeyDeregistered(key);
               RegisterChannelResult result = new RegisterChannelResult(selectorRunner, key, channel);
               if (completionHandler != null) {
                  completionHandler.completed(result);
               }

               return;
            }

            error = new IllegalStateException("Channel is not registered");
         } else {
            error = new ClosedChannelException();
         }

         Futures.notifyFailure((FutureImpl)null, completionHandler, (Throwable)error);
      } catch (IOException var7) {
         Futures.notifyFailure((FutureImpl)null, completionHandler, var7);
      }

   }

   public boolean onSelectorClosed(SelectorRunner selectorRunner) {
      try {
         selectorRunner.workaroundSelectorSpin();
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private static boolean isSelectorRunnerThread(SelectorRunner selectorRunner) {
      return selectorRunner != null && Thread.currentThread() == selectorRunner.getRunnerThread();
   }

   protected static final class DeregisterChannelOperation implements SelectorHandlerTask {
      private final SelectableChannel channel;
      private final CompletionHandler completionHandler;

      private DeregisterChannelOperation(SelectableChannel channel, CompletionHandler completionHandler) {
         this.channel = channel;
         this.completionHandler = completionHandler;
      }

      public boolean run(SelectorRunner selectorRunner) throws IOException {
         DefaultSelectorHandler.deregisterChannel0(selectorRunner, this.channel, this.completionHandler);
         return true;
      }

      public void cancel() {
         if (this.completionHandler != null) {
            this.completionHandler.failed(new IOException("Selector is closed"));
         }

      }

      // $FF: synthetic method
      DeregisterChannelOperation(SelectableChannel x0, CompletionHandler x1, Object x2) {
         this(x0, x1);
      }
   }

   protected static final class RunnableTask implements SelectorHandlerTask {
      private final SelectorHandler.Task task;
      private final CompletionHandler completionHandler;

      private RunnableTask(SelectorHandler.Task task, CompletionHandler completionHandler) {
         this.task = task;
         this.completionHandler = completionHandler;
      }

      public boolean run(SelectorRunner selectorRunner) throws IOException {
         boolean continueExecution = true;

         try {
            continueExecution = this.task.run();
            if (this.completionHandler != null) {
               this.completionHandler.completed(this.task);
            }
         } catch (Throwable var4) {
            DefaultSelectorHandler.logger.log(Level.FINEST, "doExecutePendiongIO failed.", var4);
            if (this.completionHandler != null) {
               this.completionHandler.failed(var4);
            }
         }

         return continueExecution;
      }

      public void cancel() {
         if (this.completionHandler != null) {
            this.completionHandler.failed(new IOException("Selector is closed"));
         }

      }

      // $FF: synthetic method
      RunnableTask(SelectorHandler.Task x0, CompletionHandler x1, Object x2) {
         this(x0, x1);
      }
   }

   protected static final class RegisterChannelOperation implements SelectorHandlerTask {
      private final SelectableChannel channel;
      private final int interest;
      private final Object attachment;
      private final CompletionHandler completionHandler;

      private RegisterChannelOperation(SelectableChannel channel, int interest, Object attachment, CompletionHandler completionHandler) {
         this.channel = channel;
         this.interest = interest;
         this.attachment = attachment;
         this.completionHandler = completionHandler;
      }

      public boolean run(SelectorRunner selectorRunner) throws IOException {
         DefaultSelectorHandler.registerChannel0(selectorRunner, this.channel, this.interest, this.attachment, this.completionHandler);
         return true;
      }

      public void cancel() {
         if (this.completionHandler != null) {
            this.completionHandler.failed(new IOException("Selector is closed"));
         }

      }

      // $FF: synthetic method
      RegisterChannelOperation(SelectableChannel x0, int x1, Object x2, CompletionHandler x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   protected static final class RegisterKeyTask implements SelectorHandlerTask {
      private final SelectionKey selectionKey;
      private final int interest;

      public RegisterKeyTask(SelectionKey selectionKey, int interest) {
         this.selectionKey = selectionKey;
         this.interest = interest;
      }

      public boolean run(SelectorRunner selectorRunner) throws IOException {
         SelectionKey localSelectionKey = this.selectionKey;
         if (DefaultSelectorHandler.IS_WORKAROUND_SELECTOR_SPIN) {
            localSelectionKey = selectorRunner.checkIfSpinnedKey(this.selectionKey);
         }

         DefaultSelectorHandler.registerKey0(localSelectionKey, this.interest);
         return true;
      }

      public void cancel() {
      }
   }
}

package org.glassfish.tyrus.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.MessageHandler;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class InputStreamBuffer {
   private final ReentrantLock lock = new ReentrantLock();
   private final Condition condition;
   private final List bufferedFragments;
   private final ExecutorService executorService;
   private static final Logger LOGGER = Logger.getLogger(InputStreamBuffer.class.getName());
   private volatile boolean receivedLast;
   private volatile BufferedInputStream inputStream;
   private volatile MessageHandler.Whole messageHandler;
   private volatile int bufferSize;
   private volatile int currentlyBuffered;
   private volatile boolean sessionClosed;

   public InputStreamBuffer(ExecutorService executorService) {
      this.condition = this.lock.newCondition();
      this.bufferedFragments = new ArrayList();
      this.receivedLast = false;
      this.inputStream = null;
      this.sessionClosed = false;
      this.executorService = executorService;
      this.currentlyBuffered = 0;
   }

   public int getNextByte() throws IOException {
      this.lock.lock();

      int var3;
      try {
         byte var10;
         if (this.bufferedFragments.isEmpty()) {
            if (this.receivedLast) {
               this.inputStream = null;
               this.currentlyBuffered = 0;
               var10 = -1;
               return var10;
            }

            this.checkClosedSession();

            boolean interrupted;
            do {
               interrupted = false;

               try {
                  this.condition.await();
                  this.checkClosedSession();
               } catch (InterruptedException var7) {
                  interrupted = true;
               }
            } while(interrupted);
         }

         if (this.bufferedFragments.size() == 1 && !((ByteBuffer)this.bufferedFragments.get(0)).hasRemaining() && this.receivedLast) {
            this.inputStream = null;
            this.currentlyBuffered = 0;
            var10 = -1;
            return var10;
         }

         ByteBuffer firstBuffer = (ByteBuffer)this.bufferedFragments.get(0);
         byte result = firstBuffer.get();
         if (!firstBuffer.hasRemaining()) {
            this.bufferedFragments.remove(0);
         }

         var3 = result & 255;
      } finally {
         this.lock.unlock();
      }

      return var3;
   }

   public void finishReading() {
      this.bufferedFragments.clear();
      this.inputStream = null;
   }

   public void appendMessagePart(ByteBuffer message, boolean last) {
      this.lock.lock();

      try {
         this.currentlyBuffered += message.remaining();
         if (this.currentlyBuffered > this.bufferSize) {
            MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
            LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
            this.receivedLast = true;
            throw messageTooBigException;
         }

         this.bufferedFragments.add(message);
         this.receivedLast = last;
         this.condition.signalAll();
      } finally {
         this.lock.unlock();
      }

      if (this.inputStream == null) {
         this.inputStream = new BufferedInputStream(this);
         this.executorService.execute(new Runnable() {
            public void run() {
               InputStreamBuffer.this.messageHandler.onMessage(InputStreamBuffer.this.inputStream);
            }
         });
      }

   }

   public void setMessageHandler(MessageHandler.Whole messageHandler) {
      this.messageHandler = messageHandler;
   }

   public void resetBuffer(int bufferSize) {
      this.bufferSize = bufferSize;
      this.currentlyBuffered = 0;
      this.bufferedFragments.clear();
   }

   void onSessionClosed() {
      this.sessionClosed = true;
      this.lock.lock();

      try {
         this.condition.signalAll();
      } finally {
         this.lock.unlock();
      }

   }

   private void checkClosedSession() throws IOException {
      if (this.sessionClosed) {
         throw new IOException("Websocket session has been closed.");
      }
   }
}

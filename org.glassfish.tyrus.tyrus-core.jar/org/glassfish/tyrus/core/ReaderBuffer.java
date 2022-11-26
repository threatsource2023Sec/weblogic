package org.glassfish.tyrus.core;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.MessageHandler;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class ReaderBuffer {
   private final AtomicBoolean buffering = new AtomicBoolean(true);
   private final ExecutorService executorService;
   private final ReentrantLock lock = new ReentrantLock();
   private final Condition condition;
   private static final Logger LOGGER = Logger.getLogger(ReaderBuffer.class.getName());
   private volatile boolean receivedLast;
   private volatile int bufferSize;
   private volatile int currentlyBuffered;
   private volatile StringBuffer buffer;
   private volatile BufferedStringReader reader;
   private volatile MessageHandler.Whole messageHandler;
   private volatile boolean sessionClosed;

   public ReaderBuffer(ExecutorService executorService) {
      this.condition = this.lock.newCondition();
      this.receivedLast = false;
      this.reader = null;
      this.sessionClosed = false;
      this.buffer = new StringBuffer();
      this.executorService = executorService;
      this.currentlyBuffered = 0;
   }

   public char[] getNextChars(int number) throws IOException {
      this.lock.lock();

      char[] var4;
      try {
         if (this.buffer.length() == 0) {
            if (this.receivedLast) {
               this.reader = null;
               this.buffering.set(true);
               this.currentlyBuffered = 0;
               Object var11 = null;
               return (char[])var11;
            }

            this.checkClosedSession();

            boolean interrupted;
            do {
               interrupted = false;

               try {
                  this.condition.await();
                  this.checkClosedSession();
               } catch (InterruptedException var8) {
                  interrupted = true;
               }
            } while(interrupted);
         }

         int size = number > this.buffer.length() ? this.buffer.length() : number;
         char[] result = new char[size];
         this.buffer.getChars(0, size, result, 0);
         this.buffer.delete(0, size);
         var4 = result;
      } finally {
         this.lock.unlock();
      }

      return var4;
   }

   public void finishReading() {
      this.buffer = new StringBuffer();
      this.reader = null;
   }

   public void appendMessagePart(String message, boolean last) {
      this.lock.lock();

      try {
         this.currentlyBuffered += message.length();
         if (this.currentlyBuffered <= this.bufferSize) {
            this.buffer.append(message);
         } else if (this.buffering.get()) {
            this.buffering.set(false);
            MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
            LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
            this.receivedLast = true;
            throw messageTooBigException;
         }

         this.receivedLast = last;
         this.condition.signalAll();
      } finally {
         this.lock.unlock();
      }

      if (this.reader == null) {
         this.reader = new BufferedStringReader(this);
         this.executorService.execute(new Runnable() {
            public void run() {
               ReaderBuffer.this.messageHandler.onMessage(ReaderBuffer.this.reader);
            }
         });
      }

   }

   public void setMessageHandler(MessageHandler.Whole messageHandler) {
      this.messageHandler = messageHandler;
   }

   public void resetBuffer(int bufferSize) {
      this.bufferSize = bufferSize;
      this.buffering.set(true);
      this.currentlyBuffered = 0;
      this.buffer.delete(0, this.buffer.length());
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

package weblogic.protocol;

import java.io.IOException;
import weblogic.utils.UnsyncCircularQueue;

public class AsyncMessageSenderImpl implements AsyncMessageSender, MessageSenderStatistics {
   private final MessageSender sender;
   private final WritingState writingState = new WritingState();
   private long messagesSent = 0L;
   private long bytesSent = 0L;

   public AsyncMessageSenderImpl(MessageSender sender) {
      this.sender = sender;
   }

   public void send(AsyncOutgoingMessage msg) throws IOException {
      if (this.canSendMsg(msg)) {
         this.sendOutMsg(msg);
      }

   }

   private final boolean canSendMsg(AsyncOutgoingMessage msg) {
      return this.writingState.sendNow(msg);
   }

   private final void sendOutMsg(AsyncOutgoingMessage msg) throws IOException {
      try {
         long bytesNow = 0L;
         boolean sentMine = false;

         AsyncOutgoingMessage towrite;
         while((towrite = this.writingState.continueSending(bytesNow >= (long)MAX_QUEUED_SEND_SIZE && sentMine)) != null) {
            if (towrite == msg) {
               sentMine = true;
            }

            ++this.messagesSent;
            bytesNow += (long)towrite.getLength();
            this.sender.send(towrite);
         }

         this.bytesSent += bytesNow;
      } catch (IOException var6) {
         this.writingState.gotIOException();
         this.writingState.cancelIO();
         throw var6;
      }
   }

   public final void cancelIO() {
      this.writingState.cancelIO();
   }

   public final long getMessagesSentCount() {
      return this.messagesSent;
   }

   public final long getBytesSentCount() {
      return this.bytesSent;
   }

   private static final class WritingState {
      private final UnsyncCircularQueue sendQueue;
      private int state;

      private WritingState() {
         this.sendQueue = new UnsyncCircularQueue(32);
         this.state = 0;
      }

      private final synchronized boolean sendNow(AsyncOutgoingMessage mbuf) {
         switch (this.state) {
            case 0:
               this.state = 1;
               mbuf.enqueue();
               this.sendQueue.put(mbuf);
               return true;
            case 1:
            case 3:
            case 4:
               mbuf.enqueue();
               this.sendQueue.put(mbuf);
               return false;
            case 2:
               this.state = 3;
               mbuf.enqueue();
               this.sendQueue.put(mbuf);

               while(this.state == 3) {
                  try {
                     this.wait();
                  } catch (InterruptedException var3) {
                  }
               }

               return this.state != 4;
            default:
               throw new AssertionError("Invalid writing state: " + this.state);
         }
      }

      private final synchronized AsyncOutgoingMessage continueSending(boolean relieveMe) {
         if (relieveMe) {
            switch (this.state) {
               case 1:
                  this.state = 2;
               case 2:
                  break;
               case 3:
                  this.state = 1;
                  this.notify();
                  return null;
               default:
                  throw new AssertionError("Invalid writing state: " + this.state);
            }
         }

         return this.getNextMessage();
      }

      private final AsyncOutgoingMessage getNextMessage() {
         AsyncOutgoingMessage msg = (AsyncOutgoingMessage)this.sendQueue.get();
         if (msg == null) {
            this.state = 0;
            return null;
         } else {
            return msg;
         }
      }

      private final synchronized void cancelIO() {
         for(AsyncOutgoingMessage msg = (AsyncOutgoingMessage)this.sendQueue.get(); msg != null; msg = (AsyncOutgoingMessage)this.sendQueue.get()) {
            msg.cleanup();
         }

      }

      private final synchronized boolean empty() {
         if (this.sendQueue.empty()) {
            this.state = 0;
            return true;
         } else {
            return false;
         }
      }

      private final synchronized void gotIOException() {
         this.state = 4;
         this.notify();
      }

      // $FF: synthetic method
      WritingState(Object x0) {
         this();
      }
   }
}

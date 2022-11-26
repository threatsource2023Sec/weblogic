package netscape.ldap;

import java.io.Serializable;
import java.util.Vector;

class LDAPMessageQueue implements Serializable {
   static final long serialVersionUID = -7163312406176592278L;
   private Vector m_messageQueue = new Vector(1);
   private Vector m_requestList = new Vector(1);
   private LDAPException m_exception;
   private boolean m_asynchOp;
   private boolean m_timeConstrained;

   LDAPMessageQueue(boolean var1) {
      this.m_asynchOp = var1;
   }

   boolean isAsynchOp() {
      return this.m_asynchOp;
   }

   synchronized void waitFirstMessage() throws LDAPException {
      while(this.m_requestList.size() != 0 && this.m_exception == null && this.m_messageQueue.size() == 0) {
         this.waitForMessage();
      }

      if (this.m_exception != null) {
         LDAPException var1 = this.m_exception;
         this.m_exception = null;
         throw var1;
      }
   }

   synchronized LDAPMessage nextMessage() throws LDAPException {
      while(this.m_requestList.size() != 0 && this.m_exception == null && this.m_messageQueue.size() == 0) {
         this.waitForMessage();
      }

      if (this.m_exception != null) {
         LDAPException var2 = this.m_exception;
         this.m_exception = null;
         throw var2;
      } else if (this.m_requestList.size() == 0) {
         return null;
      } else {
         LDAPMessage var1 = (LDAPMessage)this.m_messageQueue.elementAt(0);
         this.m_messageQueue.removeElementAt(0);
         if (var1 instanceof LDAPResponse) {
            this.removeRequest(var1.getMessageID());
         }

         return var1;
      }
   }

   synchronized LDAPResponse completeRequest() throws LDAPException {
      while(true) {
         if (this.m_requestList.size() != 0 && this.m_exception == null && this.m_messageQueue.size() == 0) {
            this.waitForMessage();
         } else {
            if (this.m_exception != null) {
               LDAPException var3 = this.m_exception;
               this.m_exception = null;
               throw var3;
            }

            if (this.m_requestList.size() == 0) {
               return null;
            }

            for(int var1 = this.m_messageQueue.size() - 1; var1 >= 0; --var1) {
               LDAPMessage var2 = (LDAPMessage)this.m_messageQueue.elementAt(var1);
               if (var2 instanceof LDAPResponse) {
                  this.m_messageQueue.removeElementAt(var1);
                  return (LDAPResponse)var2;
               }
            }

            this.waitForMessage();
         }
      }
   }

   private synchronized void waitForMessage() throws LDAPException {
      if (!this.m_timeConstrained) {
         try {
            this.wait();
         } catch (InterruptedException var8) {
            throw new LDAPInterruptedException("Interrupted LDAP operation");
         }
      } else {
         long var1 = Long.MAX_VALUE;
         long var3 = System.currentTimeMillis();

         for(int var5 = 0; var5 < this.m_requestList.size(); ++var5) {
            RequestEntry var6 = (RequestEntry)this.m_requestList.elementAt(var5);
            if (var6.timeToComplete <= var3) {
               var6.connection.abandon(var6.id);
               throw new LDAPException("Time to complete operation exceeded", 85);
            }

            if (var6.timeToComplete < var1) {
               var1 = var6.timeToComplete;
            }
         }

         long var10 = var1 == Long.MAX_VALUE ? 0L : var1 - var3;

         try {
            this.m_timeConstrained = var10 != 0L;
            this.wait(var10);
         } catch (InterruptedException var9) {
            throw new LDAPInterruptedException("Interrupted LDAP operation");
         }
      }
   }

   void merge(LDAPMessageQueue var1) {
      Thread.yield();
      synchronized(this) {
         synchronized(var1) {
            int var4;
            for(var4 = 0; var4 < var1.m_messageQueue.size(); ++var4) {
               this.m_messageQueue.addElement(var1.m_messageQueue.elementAt(var4));
            }

            if (var1.m_exception != null) {
               this.m_exception = var1.m_exception;
            }

            var4 = 0;

            while(true) {
               if (var4 >= var1.m_requestList.size()) {
                  var1.reset();
                  this.notifyAll();
                  break;
               }

               RequestEntry var5 = (RequestEntry)var1.m_requestList.elementAt(var4);
               this.m_requestList.addElement(var5);
               var5.connThread.changeListener(var5.id, this);
               ++var4;
            }
         }

         this.notifyAll();
      }
   }

   synchronized Vector getAllMessages() {
      Vector var1 = this.m_messageQueue;
      this.m_messageQueue = new Vector(1);
      return var1;
   }

   synchronized void addMessage(LDAPMessage var1) {
      this.m_messageQueue.addElement(var1);
      if (this.isAsynchOp() && var1.getType() == 1 && ((LDAPResponse)var1).getResultCode() == 0) {
         this.getConnection(var1.getMessageID()).setBound(true);
      }

      this.notifyAll();
   }

   synchronized void setException(LDAPConnThread var1, LDAPException var2) {
      this.m_exception = var2;
      this.removeAllRequests(var1);
      this.notifyAll();
   }

   boolean isMessageReceived() {
      return this.m_messageQueue.size() != 0;
   }

   public int getMessageCount() {
      return this.m_messageQueue.size();
   }

   private int removeAllMessages(int var1) {
      int var2 = 0;

      for(int var3 = this.m_messageQueue.size() - 1; var3 >= 0; --var3) {
         LDAPMessage var4 = (LDAPMessage)this.m_messageQueue.elementAt(var3);
         if (var4.getMessageID() == var1) {
            this.m_messageQueue.removeElementAt(var3);
            ++var2;
         }
      }

      return var2;
   }

   void reset() {
      this.m_exception = null;
      this.m_messageQueue.removeAllElements();
      this.m_requestList.removeAllElements();
      this.m_timeConstrained = false;
   }

   synchronized LDAPConnection getConnection(int var1) {
      for(int var2 = 0; var2 < this.m_requestList.size(); ++var2) {
         RequestEntry var3 = (RequestEntry)this.m_requestList.elementAt(var2);
         if (var1 == var3.id) {
            return var3.connection;
         }
      }

      return null;
   }

   synchronized LDAPConnThread getConnThread(int var1) {
      for(int var2 = 0; var2 < this.m_requestList.size(); ++var2) {
         RequestEntry var3 = (RequestEntry)this.m_requestList.elementAt(var2);
         if (var1 == var3.id) {
            return var3.connThread;
         }
      }

      return null;
   }

   synchronized int getMessageID() {
      int var1 = this.m_requestList.size();
      if (var1 == 0) {
         return -1;
      } else {
         RequestEntry var2 = (RequestEntry)this.m_requestList.elementAt(var1 - 1);
         return var2.id;
      }
   }

   synchronized int[] getMessageIDs() {
      int[] var1 = new int[this.m_requestList.size()];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         RequestEntry var3 = (RequestEntry)this.m_requestList.elementAt(var2);
         var1[var2] = var3.id;
      }

      return var1;
   }

   synchronized void addRequest(int var1, LDAPConnection var2, LDAPConnThread var3, int var4) {
      this.m_requestList.addElement(new RequestEntry(var1, var2, var3, var4));
      if (var4 != 0) {
         this.m_timeConstrained = true;
      }

      this.notifyAll();
   }

   public int getRequestCount() {
      return this.m_requestList.size();
   }

   synchronized boolean removeRequest(int var1) {
      for(int var2 = 0; var2 < this.m_requestList.size(); ++var2) {
         RequestEntry var3 = (RequestEntry)this.m_requestList.elementAt(var2);
         if (var1 == var3.id) {
            this.m_requestList.removeElementAt(var2);
            this.removeAllMessages(var1);
            this.notifyAll();
            return true;
         }
      }

      return false;
   }

   synchronized int removeAllRequests(LDAPConnThread var1) {
      int var2 = 0;

      for(int var3 = this.m_requestList.size() - 1; var3 >= 0; --var3) {
         RequestEntry var4 = (RequestEntry)this.m_requestList.elementAt(var3);
         if (var1 == var4.connThread) {
            this.m_requestList.removeElementAt(var3);
            ++var2;
            this.removeAllMessages(var4.id);
         }
      }

      this.notifyAll();
      return var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LDAPMessageQueue:");
      var1.append(" requestIDs={");

      for(int var2 = 0; var2 < this.m_requestList.size(); ++var2) {
         if (var2 > 0) {
            var1.append(",");
         }

         var1.append(((RequestEntry)this.m_requestList.elementAt(var2)).id);
      }

      var1.append("} messageCount=" + this.m_messageQueue.size());
      return var1.toString();
   }

   private static class RequestEntry {
      int id;
      LDAPConnection connection;
      LDAPConnThread connThread;
      long timeToComplete;

      RequestEntry(int var1, LDAPConnection var2, LDAPConnThread var3, int var4) {
         this.id = var1;
         this.connection = var2;
         this.connThread = var3;
         this.timeToComplete = var4 == 0 ? Long.MAX_VALUE : System.currentTimeMillis() + (long)var4;
      }
   }
}

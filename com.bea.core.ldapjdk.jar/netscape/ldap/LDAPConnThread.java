package netscape.ldap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.client.JDAPBERTagDecoder;
import netscape.ldap.client.opers.JDAPAbandonRequest;
import netscape.ldap.client.opers.JDAPProtocolOp;
import netscape.ldap.client.opers.JDAPUnbindRequest;

class LDAPConnThread implements Runnable {
   private static final int MAXMSGID = Integer.MAX_VALUE;
   private static final int BACKLOG_CHKCNT = 50;
   private static transient int m_highMsgId;
   private transient InputStream m_serverInput;
   private transient InputStream m_origServerInput;
   private transient OutputStream m_serverOutput;
   private transient OutputStream m_origServerOutput;
   private transient Hashtable m_requests = new Hashtable();
   private transient Hashtable m_messages = null;
   private transient Vector m_registered = new Vector();
   private transient LDAPCache m_cache = null;
   private transient Thread m_thread = null;
   private transient Object m_sendRequestLock = new Object();
   private transient LDAPConnSetupMgr m_connMgr = null;
   private transient Object m_traceOutput = null;
   private transient int m_backlogCheckCounter = 50;
   private transient boolean m_bound;
   private static transient int m_nextId;
   private transient int m_id;
   static SimpleDateFormat m_timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");

   public LDAPConnThread(LDAPConnSetupMgr var1, LDAPCache var2, Object var3) {
      this.m_connMgr = var1;
      this.setCache(var2);
      this.setTraceOutput(var3);
   }

   synchronized void connect(LDAPConnection var1) throws LDAPException {
      if (this.m_thread == null) {
         try {
            this.m_connMgr.openConnection();
            this.m_serverInput = new BufferedInputStream(this.m_connMgr.getSocket().getInputStream());
            this.m_serverOutput = new BufferedOutputStream(this.m_connMgr.getSocket().getOutputStream());
            this.register(var1);
         } catch (IOException var4) {
            throw new LDAPException("failed to connect to server " + this.m_connMgr.getHost(), 91);
         }

         this.m_id = m_nextId++;
         String var2 = this.m_connMgr.getLDAPUrl().getServerUrl();
         if (this.m_traceOutput != null) {
            StringBuffer var3 = new StringBuffer(" Connected to ");
            var3.append(var2);
            this.logTraceMessage(var3);
         }

         String var5 = "LDAPConnThread-" + this.m_id + " " + var2;
         this.m_thread = new Thread(this, var5);
         this.m_thread.setDaemon(true);
         this.m_thread.start();
      }
   }

   public synchronized String toString() {
      return this.m_thread != null ? this.m_thread.getName() : "LDAPConnThread-" + this.m_id + " <disconnected>";
   }

   void layerSocket(LDAPTLSSocketFactory var1) throws Exception {
      synchronized(this.m_sendRequestLock) {
         try {
            this.m_connMgr.layerSocket(var1);
            this.setInputStream(this.m_connMgr.getSocket().getInputStream());
            this.setOutputStream(this.m_connMgr.getSocket().getOutputStream());
         } catch (Exception var5) {
            this.m_serverInput = this.m_origServerInput;
            this.m_serverOutput = this.m_origServerOutput;
            throw var5;
         }

      }
   }

   void setBound(boolean var1) {
      this.m_bound = var1;
   }

   boolean isBound() {
      return this.m_thread != null && this.m_bound;
   }

   InputStream getInputStream() {
      return this.m_serverInput;
   }

   void setInputStream(InputStream var1) {
      this.m_serverInput = var1;
   }

   OutputStream getOutputStream() {
      return this.m_serverOutput;
   }

   void setOutputStream(OutputStream var1) {
      this.m_serverOutput = var1;
   }

   int getRequestCount() {
      return this.m_requests.size();
   }

   void setTraceOutput(Object var1) {
      synchronized(this.m_sendRequestLock) {
         if (var1 == null) {
            this.m_traceOutput = null;
         } else if (var1 instanceof OutputStream) {
            this.m_traceOutput = new PrintWriter((OutputStream)var1);
         } else if (var1 instanceof LDAPTraceWriter) {
            this.m_traceOutput = var1;
         }

      }
   }

   void logTraceMessage(StringBuffer var1) {
      String var2 = m_timeFormat.format(new Date());
      StringBuffer var3 = new StringBuffer(var2);
      var3.append(" ldc=");
      var3.append(this.m_id);
      synchronized(this.m_sendRequestLock) {
         if (this.m_traceOutput instanceof PrintWriter) {
            PrintWriter var5 = (PrintWriter)this.m_traceOutput;
            var5.print(var3);
            var5.println(var1);
            var5.flush();
         } else if (this.m_traceOutput instanceof LDAPTraceWriter) {
            var3.append(var1);
            ((LDAPTraceWriter)this.m_traceOutput).write(var3.toString());
         }

      }
   }

   synchronized void setCache(LDAPCache var1) {
      this.m_cache = var1;
      this.m_messages = this.m_cache != null ? new Hashtable() : null;
   }

   private int allocateId() {
      synchronized(this.m_sendRequestLock) {
         m_highMsgId = (m_highMsgId + 1) % Integer.MAX_VALUE;
         return m_highMsgId;
      }
   }

   void sendRequest(LDAPConnection var1, JDAPProtocolOp var2, LDAPMessageQueue var3, LDAPConstraints var4) throws LDAPException {
      if (this.m_thread == null) {
         throw new LDAPException("Not connected to a server", 81);
      } else {
         LDAPMessage var5 = new LDAPMessage(this.allocateId(), var2, var4.getServerControls());
         if (var3 != null) {
            this.m_requests.put(new Integer(var5.getMessageID()), var3);
            this.resultRetrieved();
            var3.addRequest(var5.getMessageID(), var1, this, var4.getTimeLimit());
         }

         if (!this.sendRequest(var5, false)) {
            throw new LDAPException("Server or network error", 81);
         }
      }
   }

   private boolean sendRequest(LDAPMessage var1, boolean var2) {
      synchronized(this.m_sendRequestLock) {
         try {
            if (this.m_traceOutput != null) {
               this.logTraceMessage(var1.toTraceString());
            }

            var1.write(this.m_serverOutput);
            this.m_serverOutput.flush();
            boolean var10000 = true;
            return var10000;
         } catch (IOException var6) {
            if (!var2) {
               this.networkError(var6);
            }
         } catch (NullPointerException var7) {
            if (!var2 && this.m_thread != null) {
               throw var7;
            }
         }

         return false;
      }
   }

   private void sendUnbindRequest(LDAPControl[] var1) {
      LDAPMessage var2 = new LDAPMessage(this.allocateId(), new JDAPUnbindRequest(), var1);
      this.sendRequest(var2, true);
   }

   private void sendAbandonRequest(int var1, LDAPControl[] var2) {
      LDAPMessage var3 = new LDAPMessage(this.allocateId(), new JDAPAbandonRequest(var1), var2);
      this.sendRequest(var3, true);
   }

   public synchronized void register(LDAPConnection var1) {
      if (!this.m_registered.contains(var1)) {
         this.m_registered.addElement(var1);
      }

   }

   int getClientCount() {
      return this.m_registered.size();
   }

   boolean isConnected() {
      return this.m_thread != null;
   }

   synchronized void deregister(LDAPConnection var1) {
      if (this.m_thread != null) {
         this.m_registered.removeElement(var1);
         if (this.m_registered.size() == 0) {
            Thread var2 = this.m_thread;
            this.m_thread = null;

            try {
               this.sendUnbindRequest(var1.getConstraints().getServerControls());

               try {
                  var2.interrupt();
                  this.wait(500L);
               } catch (InterruptedException var8) {
               }
            } catch (Exception var9) {
               LDAPConnection.printDebug(var9.toString());
            } finally {
               this.cleanUp((LDAPException)null);
            }
         }

      }
   }

   private void cleanUp(LDAPException var1) {
      this.resultRetrieved();

      try {
         this.m_serverOutput.close();
      } catch (Exception var50) {
      } finally {
         this.m_serverOutput = null;
      }

      try {
         this.m_serverInput.close();
      } catch (Exception var48) {
      } finally {
         this.m_serverInput = null;
      }

      if (this.m_origServerInput != null) {
         try {
            this.m_origServerInput.close();
         } catch (Exception var46) {
         } finally {
            this.m_origServerInput = null;
         }
      }

      if (this.m_origServerOutput != null) {
         try {
            this.m_origServerOutput.close();
         } catch (Exception var44) {
         } finally {
            this.m_origServerOutput = null;
         }
      }

      if (var1 != null) {
         this.m_connMgr.invalidateConnection();
      } else {
         this.m_connMgr.closeConnection();
      }

      Enumeration var2 = this.m_requests.elements();

      while(var2.hasMoreElements()) {
         try {
            LDAPMessageQueue var3 = (LDAPMessageQueue)var2.nextElement();
            if (var1 != null) {
               var3.setException(this, var1);
            } else {
               var3.removeAllRequests(this);
            }
         } catch (Exception var43) {
         }
      }

      this.m_requests.clear();
      if (this.m_messages != null) {
         this.m_messages.clear();
      }

      this.m_bound = false;
   }

   private void checkBacklog() throws InterruptedException {
      label46:
      while(this.m_requests.size() != 0) {
         Enumeration var1 = this.m_requests.elements();

         LDAPSearchListener var3;
         int var4;
         do {
            if (!var1.hasMoreElements()) {
               synchronized(this) {
                  this.wait(3000L);
                  continue label46;
               }
            }

            LDAPMessageQueue var2 = (LDAPMessageQueue)var1.nextElement();
            if (!(var2 instanceof LDAPSearchListener)) {
               return;
            }

            var3 = (LDAPSearchListener)var2;
            if (var3.getSearchConstraints() == null) {
               return;
            }

            var4 = var3.getSearchConstraints().getMaxBacklog();
            int var5 = var3.getSearchConstraints().getBatchSize();
            if (var4 == 0) {
               return;
            }

            if (!var3.isAsynchOp() && var5 == 0) {
               return;
            }
         } while(var3.getMessageCount() >= var4);

         return;
      }

   }

   synchronized void resultRetrieved() {
      this.notifyAll();
   }

   public void run() {
      LDAPMessage var1 = null;
      JDAPBERTagDecoder var2 = new JDAPBERTagDecoder();
      int[] var3 = new int[1];

      try {
         while(Thread.currentThread() == this.m_thread) {
            try {
               if (--this.m_backlogCheckCounter <= 0) {
                  this.m_backlogCheckCounter = 50;
                  this.checkBacklog();
               }

               var3[0] = 0;
               BERElement var4 = BERElement.getElement(var2, this.m_serverInput, var3);
               var1 = LDAPMessage.parseMessage(var4);
               if (this.m_traceOutput != null) {
                  this.logTraceMessage(var1.toTraceString());
               }

               this.processResponse(var1, var3[0]);
               Thread.yield();
            } catch (Exception var8) {
               if (Thread.currentThread() == this.m_thread) {
                  this.networkError(var8);
               }
            }
         }
      } finally {
         this.resultRetrieved();
      }

   }

   private void processResponse(LDAPMessage var1, int var2) {
      Integer var3 = new Integer(var1.getMessageID());
      LDAPMessageQueue var4 = (LDAPMessageQueue)this.m_requests.get(var3);
      if (var4 != null) {
         if (this.m_cache != null && var4 instanceof LDAPSearchListener) {
            this.cacheSearchResult((LDAPSearchListener)var4, var1, var2);
         }

         var4.addMessage(var1);
         if (var1 instanceof LDAPResponse) {
            this.m_requests.remove(var3);
            if (this.m_requests.size() == 0) {
               this.m_backlogCheckCounter = 50;
            }

            if (var1 instanceof LDAPExtendedResponse) {
               LDAPExtendedResponse var5 = (LDAPExtendedResponse)var1;
               String var6 = var5.getID();
               if (var5.getResultCode() == 0 && var6 != null && var6.equals("1.3.6.1.4.1.1466.20037")) {
                  this.changeIOStreams();
               }
            }
         }

      }
   }

   private void changeIOStreams() {
      this.m_origServerInput = this.m_serverInput;
      this.m_origServerOutput = this.m_serverOutput;
      this.m_serverInput = null;
      this.m_serverOutput = null;

      while(this.m_serverInput == null || this.m_serverOutput == null) {
         try {
            if (Thread.currentThread() != this.m_thread) {
               return;
            }

            Thread.sleep(200L);
         } catch (InterruptedException var2) {
         }
      }

   }

   private synchronized void cacheSearchResult(LDAPSearchListener var1, LDAPMessage var2, int var3) {
      Integer var4 = new Integer(var2.getMessageID());
      Long var5 = var1.getKey();
      Vector var6 = null;
      if (this.m_cache != null && var5 != null) {
         if (var2 instanceof LDAPSearchResult) {
            var6 = (Vector)this.m_messages.get(var4);
            if (var6 == null) {
               this.m_messages.put(var4, var6 = new Vector());
               var6.addElement(new Long(0L));
            }

            if ((Long)var6.firstElement() == -1L) {
               return;
            }

            long var7 = (Long)var6.firstElement() + (long)var3;
            if (var7 > this.m_cache.getSize()) {
               var6.removeAllElements();
               var6.addElement(new Long(-1L));
               return;
            }

            var6.setElementAt(new Long(var7), 0);
            var6.addElement(((LDAPSearchResult)var2).getEntry());
         } else if (var2 instanceof LDAPSearchResultReference) {
            var6 = (Vector)this.m_messages.get(var4);
            if (var6 == null) {
               this.m_messages.put(var4, var6 = new Vector());
            } else {
               var6.removeAllElements();
            }

            var6.addElement(new Long(-1L));
         } else if (var2 instanceof LDAPResponse) {
            boolean var9 = ((LDAPResponse)var2).getResultCode() > 0;
            var6 = (Vector)this.m_messages.remove(var4);
            if (!var9) {
               if (var6 == null) {
                  var6 = new Vector();
                  var6.addElement(new Long(0L));
               }

               if ((Long)var6.firstElement() != -1L) {
                  this.m_cache.addEntry(var5, var6);
               }
            }
         }

      }
   }

   void abandon(int var1, LDAPControl[] var2) {
      if (this.m_thread != null) {
         LDAPMessageQueue var3 = (LDAPMessageQueue)this.m_requests.remove(new Integer(var1));
         if (this.m_messages != null) {
            this.m_messages.remove(new Integer(var1));
         }

         if (var3 != null) {
            var3.removeRequest(var1);
         }

         this.resultRetrieved();
         this.sendAbandonRequest(var1, var2);
      }
   }

   LDAPMessageQueue changeListener(int var1, LDAPMessageQueue var2) {
      if (this.m_thread == null) {
         var2.setException(this, new LDAPException("Server or network error", 81));
         return null;
      } else {
         return (LDAPMessageQueue)this.m_requests.put(new Integer(var1), var2);
      }
   }

   private synchronized void networkError(Exception var1) {
      if (this.m_thread != null) {
         this.m_thread = null;
         this.cleanUp(new LDAPException("Server or network error", 81));
      }
   }
}

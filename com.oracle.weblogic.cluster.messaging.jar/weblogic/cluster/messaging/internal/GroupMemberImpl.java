package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.glassfish.hk2.api.ServiceHandle;
import weblogic.cluster.InboundListener;
import weblogic.server.GlobalServiceLocator;

public final class GroupMemberImpl implements GroupMember {
   private static final boolean DEBUG;
   private static final int RETRY_COUNT = 2;
   private final long startTime;
   private final ServerConfigurationInformation configuration;
   private volatile long lastArrivalTime;
   private final Object lastArrivalTimeLock = new Object();
   private Connection connection = null;
   private ReentrantLock lock = new ReentrantLock(true);
   private ServiceHandle inboundListenerServiceHandle;
   ArrayList connectionList = new ArrayList();
   private static final int FIRST_LOCK_ACQUIRE_TIME_MILLIS;
   private static final int SECOND_LOCK_ACQUIRE_TIME_MILLIS;

   private static int initProperty(String s, int defaultValue) {
      try {
         return Integer.getInteger(s, defaultValue);
      } catch (SecurityException var3) {
         return defaultValue;
      }
   }

   public GroupMemberImpl(ServerConfigurationInformation localServerConfiguration, long startTime) {
      this.configuration = localServerConfiguration;
      this.startTime = startTime;
   }

   public GroupMemberImpl(ServerConfigurationInformation info) {
      this.configuration = info;
      this.startTime = 0L;
   }

   public ServerConfigurationInformation getConfiguration() {
      return this.configuration;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public void send(Message message) throws IOException {
      boolean locked = false;

      try {
         locked = this.acquireLock(false);
         if (!locked) {
            throw new IOException("Timed out");
         }

         this.sendMessage(message);
      } finally {
         if (locked) {
            this.unLock();
         }

      }

   }

   private void sendMessage(Message message) throws IOException {
      IOException exception = null;
      int i = 0;

      while(true) {
         if (i < 2) {
            try {
               if (this.connection == null || this.connection.isDead()) {
                  ConnectionManager manager = Environment.getConnectionManager();
                  this.connection = manager.createConnection(this.configuration);
               }

               if (this.connection != null && !this.connection.isDead()) {
                  if (DEBUG) {
                     debug("Send message to " + this.configuration.getServerName() + ". Retry count " + i);
                  }

                  this.connection.send(message);
               }

               Iterator conns = this.connectionList.iterator();

               while(conns.hasNext()) {
                  Connection c = (Connection)conns.next();
                  if (!c.isDead()) {
                     if (DEBUG) {
                        debug("Send message to " + c.getServerId());
                     }

                     c.send(message);
                  } else if (DEBUG) {
                     debug("Connection for duplicate member: " + c.getServerId() + " is dead.  Unable to deliver message");
                  }
               }

               return;
            } catch (RejectConnectionException var6) {
               exception = var6;
               if (DEBUG) {
                  debug("Failed to send message to " + this.configuration.getServerName() + "> Rejected connection exception:" + var6);
               }
            } catch (IOException var7) {
               exception = var7;
               if (DEBUG) {
                  debug("Failed to send message to " + this.configuration.getServerName() + "> Exception:" + var7);
               }

               ++i;
               continue;
            }
         }

         throw exception;
      }
   }

   public void receive(Message message, Connection connection) {
      if (DEBUG) {
         debug("received message: " + message + " from " + connection.getConfiguration().getServerName());
      }

      if (DEBUG) {
         debug("local group: " + GroupManagerImpl.getInstance().getLocalGroup());
      }

      GroupManagerImpl.getInstance().getLocalGroup().forward(message, connection);
   }

   public void setLastMessageArrivalTime(long arrivalTime) {
      if (this.lastArrivalTime == 0L && !this.isInboundListenerStarted()) {
         if (DEBUG) {
            debug("InboundService has not started listening. skip updating LAT.");
         }
      } else {
         synchronized(this.lastArrivalTimeLock) {
            if (this.lastArrivalTime < arrivalTime) {
               this.lastArrivalTime = arrivalTime;
            }
         }
      }

   }

   public long getLastArrivalTime() {
      return this.lastArrivalTime;
   }

   private static void debug(String s) {
      Environment.getLogService().debug("[GroupMember] " + s);
   }

   public int compareTo(Object other) {
      assert other instanceof GroupMemberImpl;

      GroupMemberImpl otherInfo = (GroupMemberImpl)other;
      return this.configuration.compareTo(otherInfo.configuration);
   }

   public boolean equals(Object other) {
      if (!(other instanceof GroupMemberImpl)) {
         return false;
      } else {
         GroupMemberImpl otherInfo = (GroupMemberImpl)other;
         return this.configuration.equals(otherInfo.configuration);
      }
   }

   public int hashCode() {
      return this.configuration.hashCode();
   }

   public String toString() {
      return this.configuration.getServerName();
   }

   public boolean addConnection(Connection conn) {
      if (conn == this.connection) {
         return true;
      } else {
         if (DEBUG) {
            debug("Adding connection to " + this + " with conn: " + conn + " and serverId: " + conn.getServerId());
         }

         boolean locked = false;
         boolean result = false;

         try {
            locked = this.acquireLock(true);
            if (locked) {
               if (this.connection == null) {
                  this.connection = conn;
                  result = true;
               } else if (this.connection.getServerId() == null && conn.getServerId() != null) {
                  this.connection.close();
                  this.connection = conn;
                  result = true;
               } else if (this.connection.getServerId() != null && conn.getServerId() != null && !this.connection.getServerId().equals(conn.getServerId())) {
                  Iterator conns = this.connectionList.iterator();
                  boolean found = false;

                  while(conns.hasNext()) {
                     Connection c = (Connection)conns.next();
                     if (c.getServerId() != null && c.getServerId().equals(conn.getServerId())) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     if (DEBUG) {
                        debug("adding connection to the connectionList with serverId: " + conn.getServerId());
                     }

                     this.connectionList.add(conn);
                  }
               }
            }
         } finally {
            if (locked) {
               this.unLock();
            }

         }

         return result;
      }
   }

   private boolean acquireLock(boolean failFast) {
      boolean acquiredLock = false;

      try {
         acquiredLock = this.lock.tryLock((long)FIRST_LOCK_ACQUIRE_TIME_MILLIS, TimeUnit.MILLISECONDS);
         if (!acquiredLock) {
            if (failFast) {
               return acquiredLock;
            }

            int queueLength = this.lock.getQueueLength();
            if (queueLength > 0) {
               if (DEBUG) {
                  debug("Unable to acquirelock to write to " + this.configuration.getServerName() + ".Total threads waiting to acquire lock=" + queueLength);
               }
            } else {
               acquiredLock = this.lock.tryLock((long)SECOND_LOCK_ACQUIRE_TIME_MILLIS, TimeUnit.MILLISECONDS);
            }
         }
      } catch (InterruptedException var4) {
      }

      return acquiredLock;
   }

   private void unLock() {
      if (this.lock.isHeldByCurrentThread()) {
         this.lock.unlock();
      }

   }

   private boolean isInboundListenerStarted() {
      if (this.inboundListenerServiceHandle == null) {
         this.inboundListenerServiceHandle = GlobalServiceLocator.getServiceLocator().getServiceHandle(InboundListener.class, new Annotation[0]);
      }

      if (this.inboundListenerServiceHandle != null && this.inboundListenerServiceHandle.isActive()) {
         InboundListener listenerService = (InboundListener)this.inboundListenerServiceHandle.getService();
         return listenerService.isStarted();
      } else {
         return false;
      }
   }

   static {
      DEBUG = Environment.DEBUG;
      FIRST_LOCK_ACQUIRE_TIME_MILLIS = initProperty("weblogic.unicast.sendTimeoutMillis", 1000);
      SECOND_LOCK_ACQUIRE_TIME_MILLIS = initProperty("weblogic.unicast.sendBackoffTimeoutMillis", 10000);
   }
}
